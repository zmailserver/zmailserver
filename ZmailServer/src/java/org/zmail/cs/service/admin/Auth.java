/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

/*
 * Created on May 26, 2004
 */
package org.zmail.cs.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException.AuthFailedServiceException;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.Key.DomainBy;
import org.zmail.cs.account.ZmailAuthToken;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.auth.AuthContext;
import org.zmail.cs.account.auth.AuthMechanism.AuthMech;
import org.zmail.cs.session.Session;
import org.zmail.cs.service.AuthProvider;
import org.zmail.cs.util.AccountUtil;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailCookie;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.SoapEngine;
import org.zmail.soap.SoapServlet;
import org.zmail.soap.ZmailSoapContext;

public class Auth extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        AuthToken at = null;
        Account acct = null;

        Provisioning prov = Provisioning.getInstance();

        Element authTokenEl = request.getOptionalElement(AdminConstants.E_AUTH_TOKEN);
        if (authTokenEl != null) {
            // authtoken admin auth is only supported by Yahoo auth provider, not the default Zmail auth provider
            try {
                at = AuthProvider.getAuthToken(request, new HashMap<String, Object>());
                if (at == null)
                    throw ServiceException.AUTH_EXPIRED();

                org.zmail.cs.service.account.Auth.addAccountToLogContextByAuthToken(prov, at);

                if (at.isExpired())
                    throw ServiceException.AUTH_EXPIRED();

                // make sure that the authenticated account is active and has not been deleted/disabled since the last request
                acct = prov.get(AccountBy.id, at.getAccountId(), at);
                if (acct == null || !acct.getAccountStatus(prov).equals(Provisioning.ACCOUNT_STATUS_ACTIVE))
                    throw ServiceException.AUTH_EXPIRED();

                // make sure the authenticated account is an admin account
                checkAdmin(acct);
            } catch (AuthTokenException e) {
                throw ServiceException.AUTH_REQUIRED();
            }

        } else {
            /*
             * only one of  
             *     <name>...</name>
             * or 
             *     <account by="name|id|foreignPrincipal">...</account>
             * can/must be specified    
             */
            String name = request.getAttribute(AdminConstants.E_NAME, null);
            Element acctEl = request.getOptionalElement(AccountConstants.E_ACCOUNT);
            if (name != null && acctEl != null)
                throw ServiceException.INVALID_REQUEST("only one of <name> or <account> can be specified", null);
            if (name == null && acctEl == null)
                throw ServiceException.INVALID_REQUEST("missing <name> or <account>", null);

            String password = request.getAttribute(AdminConstants.E_PASSWORD);
            Element virtualHostEl = request.getOptionalElement(AccountConstants.E_VIRTUAL_HOST);
            String virtualHost = virtualHostEl == null ? null : virtualHostEl.getText().toLowerCase();

            String valuePassedIn;
            AccountBy by;
            String value;
            if (name != null) {
                valuePassedIn = name;
                by = AccountBy.name;
            } else {
                valuePassedIn = acctEl.getText();
                String byStr = acctEl.getAttribute(AccountConstants.A_BY, AccountBy.name.name());
                by = AccountBy.fromString(byStr);
            }
            value = valuePassedIn;

            try {

                if (by == AccountBy.name && value.indexOf("@") == -1) {
                    // first try to get by adminName, which resolves the account under cn=admins,cn=zmail
                    // and does not need a domain 
                    acct = prov.get(AccountBy.adminName, value, zsc.getAuthToken());

                    // not found, try applying virtual host name
                    if (acct == null) {
                        if (virtualHost != null) {
                            Domain d = prov.get(Key.DomainBy.virtualHostname, virtualHost);
                            if (d != null)
                                value = value + "@" + d.getName();
                        }                    
                    } 
                }

                if (acct == null)
                    acct = prov.get(by, value);

                if (acct == null)
                    throw AuthFailedServiceException.AUTH_FAILED(value, valuePassedIn, "account not found");

                AccountUtil.addAccountToLogContext(prov, acct.getId(), ZmailLog.C_NAME, ZmailLog.C_ID, null);

                ZmailLog.security.info(ZmailLog.encodeAttrs(
                        new String[] {"cmd", "AdminAuth","account", value})); 

                Map<String, Object> authCtxt = new HashMap<String, Object>();
                authCtxt.put(AuthContext.AC_ORIGINATING_CLIENT_IP, context.get(SoapEngine.ORIG_REQUEST_IP));
                authCtxt.put(AuthContext.AC_REMOTE_IP, context.get(SoapEngine.SOAP_REQUEST_IP));
                authCtxt.put(AuthContext.AC_ACCOUNT_NAME_PASSEDIN, valuePassedIn);
                authCtxt.put(AuthContext.AC_USER_AGENT, zsc.getUserAgent());
                authCtxt.put(AuthContext.AC_AS_ADMIN, Boolean.TRUE);
                prov.authAccount(acct, password, AuthContext.Protocol.soap, authCtxt);
                checkAdmin(acct);
                AuthMech authedByMech = (AuthMech) authCtxt.get(AuthContext.AC_AUTHED_BY_MECH);
                at = AuthProvider.getAuthToken(acct, true, authedByMech);

            } catch (ServiceException se) {
                ZmailLog.security.warn(ZmailLog.encodeAttrs(
                        new String[] {"cmd", "AdminAuth","account", value, "error", se.getMessage()}));    
                throw se;
            }
        }

        return doResponse(request, at, zsc, context, acct);
    }

    private AuthToken dummyYCCTokenTestNeverCallMe(Element authTokenEl) 
    throws ServiceException, AuthTokenException  {
        String atType = authTokenEl.getAttribute(AdminConstants.A_TYPE);
        if ("YAHOO_CALENDAR_AUTH_PROVIDER".equals(atType)) {
            for (Element a : authTokenEl.listElements(AdminConstants.E_A)) {
                String name = a.getAttribute(AdminConstants.A_N);
                String value = a.getText();
                if ("ADMIN_AUTH_KEY".equals(name) &&
                        "1210713456+dDedin1lO8d1_j8Kl.vl".equals(value)) {
                    Account acct = Provisioning.getInstance().get(AccountBy.name, "admin@phoebe.mac");
                    return new ZmailAuthToken(acct, true, null);
                }
            }
        }
        return null;
    }

    private void checkAdmin(Account acct) throws ServiceException {
        boolean isDomainAdmin = acct.getBooleanAttr(Provisioning.A_zmailIsDomainAdminAccount, false);
        boolean isAdmin= acct.getBooleanAttr(Provisioning.A_zmailIsAdminAccount, false);            
        boolean isDelegatedAdmin= acct.getBooleanAttr(Provisioning.A_zmailIsDelegatedAdminAccount, false);            
        boolean ok = (isDomainAdmin || isAdmin || isDelegatedAdmin);
        if (!ok) 
            throw ServiceException.PERM_DENIED("not an admin account");
    }

    private Element doResponse(Element request, AuthToken at, ZmailSoapContext zsc, 
            Map<String, Object> context, Account acct) throws ServiceException {
        Element response = zsc.createElement(AdminConstants.AUTH_RESPONSE);
        at.encodeAuthResp(response, true);

        /* 
         * bug 67078
         * also return auth token cookie in http header
         */
        HttpServletRequest httpReq = (HttpServletRequest)context.get(SoapServlet.SERVLET_REQUEST);
        HttpServletResponse httpResp = (HttpServletResponse)context.get(SoapServlet.SERVLET_RESPONSE);
        boolean rememberMe = request.getAttributeBool(AdminConstants.A_PERSIST_AUTH_TOKEN_COOKIE, false);
        at.encode(httpResp, true, ZmailCookie.secureCookie(httpReq), rememberMe);
        
        response.addAttribute(AdminConstants.E_LIFETIME, at.getExpires() - System.currentTimeMillis(), Element.Disposition.CONTENT);

        Session session = updateAuthenticatedAccount(zsc, at, context, true);
        if (session != null) {
            ZmailSoapContext.encodeSession(response, session.getSessionId(), session.getSessionType());
        }
        return response;
    }

    @Override
    public boolean needsAuth(Map<String, Object> context) {
        // can't require auth on auth request
        return false;
    }

    @Override
    public boolean needsAdminAuth(Map<String, Object> context) {
        // can't require auth on auth request
        return false;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.ALLOW_ALL_ADMINS);
    }
}
