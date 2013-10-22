/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */

package org.zmail.cs.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import org.zmail.common.localconfig.DebugConfig;
import org.zmail.common.util.L10nUtil;
import org.zmail.common.util.StringUtil;
import org.apache.commons.codec.binary.Hex;

import org.zmail.client.ZFolder;
import org.zmail.client.ZMailbox;
import org.zmail.client.ZMountpoint;
import org.zmail.common.account.ProvisioningConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.BlobMetaData;
import org.zmail.common.util.Log;
import org.zmail.common.util.LogFactory;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.account.AuthTokenKey;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.GuestAccount;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.SearchAccountsOptions;
import org.zmail.cs.account.ShareInfoData;
import org.zmail.cs.account.ZmailAuthToken;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.mailbox.ACL;
import org.zmail.cs.mailbox.Flag;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.Mountpoint;
import org.zmail.cs.mailbox.acl.AclPushSerializer;
import org.zmail.cs.servlet.ZmailServlet;
import org.zmail.cs.util.AccountUtil;
import org.zmail.soap.mail.message.FolderActionRequest;
import org.zmail.soap.mail.type.FolderActionSelector;

public class ExternalUserProvServlet extends ZmailServlet {

    private static final Log logger = LogFactory.getLog(ExternalUserProvServlet.class);

    @Override
    public void init() throws ServletException {
        String name = getServletName();
        ZmailLog.account.info("Servlet " + name + " starting up");
        super.init();
    }

    @Override
    public void destroy() {
        String name = getServletName();
        ZmailLog.account.info("Servlet " + name + " shutting down");
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param = req.getParameter("p");
        if (param == null) {
            throw new ServletException("request missing param");
        }
        Map<Object, Object> tokenMap = validatePrelimToken(param);
        String ownerId = (String) tokenMap.get("aid");
        String folderId = (String) tokenMap.get("fid");
        String extUserEmail = (String) tokenMap.get("email");

        Provisioning prov = Provisioning.getInstance();
        Account grantee;
        try {
            Account owner = prov.getAccountById(ownerId);
            Domain domain = prov.getDomain(owner);
            grantee = prov.getAccountByName(mapExtEmailToAcctName(extUserEmail, domain));
            if (grantee == null) {
                // external virtual account not created yet
                if (prov.isOctopus() && DebugConfig.skipVirtualAccountRegistrationPage) {
                    // provision using 'null' password and display name
                    // UI will ask the user to set these post provisioning
                    provisionVirtualAccountAndRedirect(req, resp, null, null, ownerId, extUserEmail);
                } else {
                    resp.addCookie(new Cookie("ZM_PRELIM_AUTH_TOKEN", param));
                    req.setAttribute("extuseremail", extUserEmail);
                    RequestDispatcher dispatcher =
                            getServletContext().getContext("/zmail").getRequestDispatcher("/public/extuserprov.jsp");
                    dispatcher.forward(req, resp);
                }
            } else {
                // create a new mountpoint in the external user's mailbox if not already created

                String[] sharedItems = owner.getSharedItem();
                int sharedFolderId = Integer.valueOf(folderId);
                String sharedFolderPath = null;
                MailItem.Type sharedFolderView = null;
                for (String sharedItem : sharedItems) {
                    ShareInfoData sid = AclPushSerializer.deserialize(sharedItem);
                    if (sid.getItemId() == sharedFolderId && extUserEmail.equalsIgnoreCase(sid.getGranteeId())) {
                        sharedFolderPath = sid.getPath();
                        sharedFolderView = sid.getFolderDefaultViewCode();
                        break;
                    }
                }
                if (sharedFolderPath == null) {
                    throw new ServletException("share not found");
                }
                String mountpointName = getMountpointName(owner, grantee, sharedFolderPath);

                ZMailbox.Options options = new ZMailbox.Options();
                options.setNoSession(true);
                options.setAuthToken(AuthProvider.getAuthToken(grantee).toZAuthToken());
                options.setUri(AccountUtil.getSoapUri(grantee));
                ZMailbox zMailbox = new ZMailbox(options);
                ZMountpoint zMtpt = null;
                try {
                    zMtpt = zMailbox.createMountpoint(
                            String.valueOf(getMptParentFolderId(sharedFolderView, prov)), mountpointName,
                            ZFolder.View.fromString(sharedFolderView.toString()), ZFolder.Color.DEFAULTCOLOR, null,
                            ZMailbox.OwnerBy.BY_ID, ownerId, ZMailbox.SharedItemBy.BY_ID, folderId, false);
                } catch (ServiceException e) {
                    logger.debug("Error in attempting to create mountpoint. Probably it already exists.", e);
                }
                if (zMtpt != null) {
                    if (sharedFolderView == MailItem.Type.APPOINTMENT) {
                        // make sure that the mountpoint is checked in the UI by default
                        FolderActionSelector actionSelector = new FolderActionSelector(zMtpt.getId(), "check");
                        FolderActionRequest actionRequest = new FolderActionRequest(actionSelector);
                        try {
                            zMailbox.invokeJaxb(actionRequest);
                        } catch (ServiceException e) {
                            logger.warn("Error in invoking check action on calendar mountpoint", e);
                        }
                    }
                    HashSet<MailItem.Type> types = new HashSet<MailItem.Type>();
                    types.add(sharedFolderView);
                    enableAppFeatures(grantee, types);
                }

                // check if the external user is already logged-in
                String zAuthTokenCookie = null;
                javax.servlet.http.Cookie cookies[] = req.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("ZM_AUTH_TOKEN")) {
                            zAuthTokenCookie = cookie.getValue();
                            break;
                        }
                    }
                }
                AuthToken zAuthToken = null;
                if (zAuthTokenCookie != null) {
                    try {
                        zAuthToken = AuthProvider.getAuthToken(zAuthTokenCookie);
                    } catch (AuthTokenException ignored) {
                        // auth token is not valid
                    }
                }
                if (zAuthToken != null && !zAuthToken.isExpired() && grantee.getId().equals(zAuthToken.getAccountId())) {
                    // external virtual account already logged-in
                    resp.sendRedirect("/");
                } else if (prov.isOctopus() && !grantee.isVirtualAccountInitialPasswordSet() &&
                        DebugConfig.skipVirtualAccountRegistrationPage) {
                    // seems like the virtual user did not set his password during his last visit, after an account was
                    // provisioned for him
                    setCookieAndRedirect(req, resp, grantee);
                } else {
                    req.setAttribute("virtualacctdomain", domain.getName());
                    RequestDispatcher dispatcher =
                            getServletContext().getContext("/zmail").getRequestDispatcher("/public/login.jsp");
                    dispatcher.forward(req, resp);
                }
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    private static String getMountpointName(Account owner, Account grantee, String sharedFolderPath)
            throws ServiceException {
        if (sharedFolderPath.startsWith("/")) {
            sharedFolderPath = sharedFolderPath.substring(1);
        }
        int index = sharedFolderPath.indexOf('/');
        if (index != -1) {
            // exclude the top level folder name, such as "Briefcase"
            sharedFolderPath = sharedFolderPath.substring(index + 1);
        }
        return L10nUtil.getMessage(L10nUtil.MsgKey.shareNameDefault, grantee.getLocale(),
                getDisplayName(owner), sharedFolderPath.replace("/", " "));
    }

    private static String getDisplayName(Account owner) {
        return owner.getDisplayName() != null ? owner.getDisplayName() : owner.getName();
    }

    private static String mapExtEmailToAcctName(String extUserEmail, Domain domain) {
        return extUserEmail.replace("@", ".") + "@" + domain.getName();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String displayName = req.getParameter("displayname");
        String password = req.getParameter("password");

        String prelimToken = null;
        javax.servlet.http.Cookie cookies[] = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ZM_PRELIM_AUTH_TOKEN")) {
                    prelimToken = cookie.getValue();
                    break;
                }
            }
        }
        if (prelimToken == null) {
            throw new ServletException("unauthorized request");
        }
        Map<Object, Object> tokenMap = validatePrelimToken(prelimToken);
        String ownerId = (String) tokenMap.get("aid");
//        String folderId = (String) tokenMap.get("fid");
        String extUserEmail = (String) tokenMap.get("email");

        provisionVirtualAccountAndRedirect(req, resp, displayName, password, ownerId, extUserEmail);
    }

    private static void provisionVirtualAccountAndRedirect(HttpServletRequest req, HttpServletResponse resp,
            String displayName, String password, String grantorId, String extUserEmail)
            throws ServletException {
        Provisioning prov = Provisioning.getInstance();
        try {
            Account owner = prov.getAccountById(grantorId);
            Domain domain = prov.getDomain(owner);
            Account grantee = prov.getAccountByName(mapExtEmailToAcctName(extUserEmail, domain));
            if (grantee != null) {
                throw new ServletException("invalid request: account already exists");
            }

            // search all shares accessible to the external user
            SearchAccountsOptions searchOpts = new SearchAccountsOptions(
                    domain, new String[] {
                            Provisioning.A_zmailId,
                            Provisioning.A_displayName,
                            Provisioning.A_zmailSharedItem });
            // get all groups extUserEmail belongs to
            GuestAccount guestAcct = new GuestAccount(extUserEmail, null);
            List<String> groupIds = prov.getGroupMembership(guestAcct, false).groupIds();
            List<String> grantees = Lists.newArrayList(extUserEmail);
            grantees.addAll(groupIds);
            searchOpts.setFilter(ZLdapFilterFactory.getInstance().accountsByGrants(grantees, false, false));
            List<NamedEntry> accounts = prov.searchDirectory(searchOpts);

            if (accounts.isEmpty()) {
                throw new ServletException("no shares discovered");
            }

            // create external account
            Map<String, Object> attrs = new HashMap<String, Object>();
            attrs.put(Provisioning.A_zmailIsExternalVirtualAccount, ProvisioningConstants.TRUE);
            attrs.put(Provisioning.A_zmailExternalUserMailAddress, extUserEmail);
            attrs.put(Provisioning.A_zmailMailHost, prov.getLocalServer().getServiceHostname());
            if (!StringUtil.isNullOrEmpty(displayName)) {
                attrs.put(Provisioning.A_displayName, displayName);
            }
            attrs.put(Provisioning.A_zmailHideInGal, ProvisioningConstants.TRUE);
            attrs.put(Provisioning.A_zmailMailStatus, Provisioning.MailStatus.disabled.toString());
            if (!StringUtil.isNullOrEmpty(password)) {
                attrs.put(Provisioning.A_zmailVirtualAccountInitialPasswordSet, ProvisioningConstants.TRUE);
            }
            grantee = prov.createAccount(mapExtEmailToAcctName(extUserEmail, domain), password, attrs);

            // create external account mailbox
            Mailbox granteeMbox;
            try {
                granteeMbox = MailboxManager.getInstance().getMailboxByAccount(grantee);
            } catch (ServiceException e) {
                // mailbox creation failed; delete the account also so that it is a clean state before
                // the next attempt
                prov.deleteAccount(grantee.getId());
                throw e;
            }

            // create mountpoints
            Set<MailItem.Type> viewTypes = new HashSet<MailItem.Type>();
            for (NamedEntry ne : accounts) {
                Account account = (Account) ne;
                String[] sharedItems = account.getSharedItem();
                for (String sharedItem : sharedItems) {
                    ShareInfoData shareData = AclPushSerializer.deserialize(sharedItem);
                    if (!granteeMatchesShare(shareData, grantee)) {
                        continue;
                    }
                    String sharedFolderPath = shareData.getPath();
                    String mountpointName = getMountpointName(account, grantee, sharedFolderPath);
                    MailItem.Type viewType = shareData.getFolderDefaultViewCode();
                    Mountpoint mtpt = granteeMbox.createMountpoint(
                            null, getMptParentFolderId(viewType, prov), mountpointName, account.getId(),
                            shareData.getItemId(), shareData.getItemUuid(), viewType, 0, MailItem.DEFAULT_COLOR, false);
                    if (viewType == MailItem.Type.APPOINTMENT) {
                        // make sure that the mountpoint is checked in the UI by default
                        granteeMbox.alterTag(null, mtpt.getId(), mtpt.getType(), Flag.FlagInfo.CHECKED, true, null);
                    }
                    viewTypes.add(viewType);
                }
            }
            enableAppFeatures(grantee, viewTypes);

            setCookieAndRedirect(req, resp, grantee);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private static boolean granteeMatchesShare(ShareInfoData shareData, Account acct) throws ServiceException {
        Provisioning prov = Provisioning.getInstance();
        String grantee = shareData.getGranteeId();
        byte granteeType = shareData.getGranteeTypeCode();
        switch (granteeType) {
            case ACL.GRANTEE_GROUP:    return prov.inACLGroup(acct, grantee);
            case ACL.GRANTEE_GUEST:    return grantee.equalsIgnoreCase(acct.getExternalUserMailAddress());
            default: return false;
        }
    }

    private static void setCookieAndRedirect(HttpServletRequest req, HttpServletResponse resp, Account grantee)
            throws ServiceException, IOException {
        AuthToken authToken = AuthProvider.getAuthToken(grantee);
        authToken.encode(resp, false, req.getScheme().equals("https"));
        resp.sendRedirect("/");
    }

    private static int getMptParentFolderId(MailItem.Type viewType, Provisioning prov) throws ServiceException {
        switch (viewType) {
            case DOCUMENT:
                if (prov.isOctopus()) {
                    return Mailbox.ID_FOLDER_BRIEFCASE;
                }
            default:
                return Mailbox.ID_FOLDER_USER_ROOT;
        }
    }

    private static void enableAppFeatures(Account grantee, Set<MailItem.Type> viewTypes) throws ServiceException {
        Map<String, Object> appFeatureAttrs = new HashMap<String, Object>();
        for (MailItem.Type type : viewTypes) {
            switch (type) {
                case DOCUMENT:
                    appFeatureAttrs.put(Provisioning.A_zmailFeatureBriefcasesEnabled, ProvisioningConstants.TRUE);
                    break;
                case APPOINTMENT:
                    appFeatureAttrs.put(Provisioning.A_zmailFeatureCalendarEnabled, ProvisioningConstants.TRUE);
                    break;
                case CONTACT:
                    appFeatureAttrs.put(Provisioning.A_zmailFeatureContactsEnabled, ProvisioningConstants.TRUE);
                    break;
                case TASK:
                    appFeatureAttrs.put(Provisioning.A_zmailFeatureTasksEnabled, ProvisioningConstants.TRUE);
                    break;
                case MESSAGE:
                    appFeatureAttrs.put(Provisioning.A_zmailFeatureMailEnabled, ProvisioningConstants.TRUE);
                    break;
                default:
                    // we don't care about other types
            }
        }
        grantee.modify(appFeatureAttrs);
    }

    private static Map<Object, Object> validatePrelimToken(String param) throws ServletException {
        int pos = param.indexOf('_');
        if (pos == -1) {
            throw new ServletException("invalid token param");
        }
        String ver = param.substring(0, pos);
        int pos2 = param.indexOf('_', pos + 1);
        if (pos2 == -1) {
            throw new ServletException("invalid token param");
        }
        String hmac = param.substring(pos + 1, pos2);
        String data = param.substring(pos2 + 1);
        Map<Object, Object> map;
        try {
            AuthTokenKey key = AuthTokenKey.getVersion(ver);
            if (key == null) {
                throw new ServletException("unknown key version");
            }
            String computedHmac = ZmailAuthToken.getHmac(data, key.getKey());
            if (!computedHmac.equals(hmac)) {
                throw new ServletException("hmac failure");
            }
            String decoded = new String(Hex.decodeHex(data.toCharArray()));
            map = BlobMetaData.decode(decoded);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        Object expiry = map.get("exp");
        if (expiry != null) {
            // check validity
            if (System.currentTimeMillis() > Long.parseLong((String) expiry)) {
                throw new ServletException("url no longer valid");
            }
        }
        return map;
    }
}
