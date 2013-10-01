/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.account.offline;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.security.auth.login.LoginException;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.sun.mail.smtp.SMTPTransport;
import com.zimbra.client.ZGetInfoResult;
import com.zimbra.client.ZIdentity;
import com.zimbra.client.ZMailbox;
import com.zimbra.common.account.Key;
import com.zimbra.common.account.Key.AccountBy;
import com.zimbra.common.account.Key.ShareLocatorBy;
import com.zimbra.common.account.Key.UCServiceBy;
import com.zimbra.common.account.ProvisioningConstants;
import com.zimbra.common.auth.ZAuthToken;
import com.zimbra.common.localconfig.LC;
import com.zimbra.common.net.TrustManagers;
import com.zimbra.common.service.RemoteServiceException;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.util.Constants;
import com.zimbra.common.util.DateUtil;
import com.zimbra.common.util.Pair;
import com.zimbra.common.util.StringUtil;
import com.zimbra.common.util.SystemUtil;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.AttributeManager;
import com.zimbra.cs.account.CalendarResource;
import com.zimbra.cs.account.Config;
import com.zimbra.cs.account.Cos;
import com.zimbra.cs.account.DataSource;
import com.zimbra.cs.account.DistributionList;
import com.zimbra.cs.account.Domain;
import com.zimbra.cs.account.Entry;
import com.zimbra.cs.account.GlobalGrant;
import com.zimbra.cs.account.IDNUtil;
import com.zimbra.cs.account.Identity;
import com.zimbra.cs.account.NamedEntry;
import com.zimbra.cs.account.UCService;
import com.zimbra.cs.account.NamedEntry.Visitor;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.Server;
import com.zimbra.cs.account.ShareLocator;
import com.zimbra.cs.account.Signature;
import com.zimbra.cs.account.XMPPComponent;
import com.zimbra.cs.account.Zimlet;
import com.zimbra.cs.account.auth.AuthContext;
import com.zimbra.cs.account.cache.NamedEntryCache;
import com.zimbra.cs.account.callback.CallbackContext;
import com.zimbra.cs.datasource.DataSourceManager;
import com.zimbra.cs.datasource.SyncErrorManager;
import com.zimbra.cs.datasource.imap.ImapSync;
import com.zimbra.cs.db.DbOfflineDirectory;
import com.zimbra.cs.db.DbOfflineDirectory.GranterEntry;
import com.zimbra.cs.db.DbPool;
import com.zimbra.cs.mailbox.DesktopMailbox;
import com.zimbra.cs.mailbox.Folder;
import com.zimbra.cs.mailbox.LocalJMSession;
import com.zimbra.cs.mailbox.MailItem;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.mailbox.Mountpoint;
import com.zimbra.cs.mailbox.OfflineMailboxManager;
import com.zimbra.cs.mailbox.OfflineServiceException;
import com.zimbra.cs.mailbox.YContactSync;
import com.zimbra.cs.mailbox.ZcsMailbox;
import com.zimbra.cs.mailclient.smtp.SmtpTransport;
import com.zimbra.cs.mime.MimeTypeInfo;
import com.zimbra.cs.offline.Offline;
import com.zimbra.cs.offline.OfflineLC;
import com.zimbra.cs.offline.OfflineLog;
import com.zimbra.cs.offline.OfflineSyncManager;
import com.zimbra.cs.offline.ab.gab.GDataServiceException;
import com.zimbra.cs.offline.common.OfflineConstants;
import com.zimbra.cs.offline.util.OfflineYAuth;
import com.zimbra.cs.offline.util.yc.oauth.OAuthManager;
import com.zimbra.cs.util.yauth.AuthenticationException;
import com.zimbra.cs.util.yauth.MetadataTokenStore;
import com.zimbra.soap.SoapEngine;
import com.zimbra.soap.ZimbraSoapContext;
import com.zimbra.soap.admin.type.CacheEntryType;
import com.zimbra.soap.admin.type.DataSourceType;

public class OfflineProvisioning extends Provisioning implements OfflineConstants {

    public static final String A_offlineClientId = "offlineClientId";
    public static final String A_offlineDn = "offlineDn";
    public static final String A_offlineModifiedAttrs = "offlineModifiedAttrs";
    public static final String A_offlineDeletedIdentity = "offlineDeletedIdentity";
    public static final String A_offlineDeletedDataSource = "offlineDeletedDataSource";
    public static final String A_offlineDeletedSignature = "offlineDeletedSignature";
    public static final String A_offlineMountpointProxyAccountId = "offlineMountpointProxyAccountId";
    public static final String A_offlineAmbiguousGranter = "offlineAmbiguousGranter";
    public static final String A_zimbraPrefMailtoHandlerEnabled = "zimbraPrefMailtoHandlerEnabled";
    public static final String A_zimbraPrefMailtoAccountId = "zimbraPrefMailtoAccountId";
    public static final String A_zimbraPrefShareContactsInAutoComplete = "zimbraPrefShareContactsInAutoComplete";
    public static final String A_zimbraPrefNotebookSyncEnabled = "zimbraPrefNotebookSyncEnabled";
    public static final String A_zimbraPrefOfflineZimletSyncAccountId = "zimbraPrefOfflineZimletSyncAccountId";
    public static final String A_zimbraPrefOfflineBackupInterval = "zimbraPrefOfflineBackupInterval";
    public static final String A_zimbraPrefOfflineBackupAccountId = "zimbraPrefOfflineBackupAccountId";
    public static final String A_zimbraPrefOfflineBackupPath = "zimbraPrefOfflineBackupPath";
    public static final String A_zimbraPrefOfflineBackupKeep = "zimbraPrefOfflineBackupKeep";
    public static final String A_offlineBackupLastSuccess = "offlineBackupLastSuccess";
    public static final String A_zimbraPrefOfflineUpdateChannel = "zimbraPrefOfflineUpdateChannel";
    public static final String A_zimbraPrefOfflineAttrProxyMode = "zimbraPrefOfflineAttrProxyMode";
    public static final String A_zimbraPrefOfflineHttpProxyHost = "zimbraPrefOfflineHttpProxyHost";
    public static final String A_zimbraPrefOfflineHttpProxyPort = "zimbraPrefOfflineHttpProxyPort";
    public static final String A_zimbraPrefOfflineHttpProxyUsername = "zimbraPrefOfflineHttpProxyUsername";
    public static final String A_zimbraPrefOfflineHttpProxyPassword = "zimbraPrefOfflineHttpProxyPassword";
    public static final String A_zimbraPrefOfflineSocksProxyHost = "zimbraPrefOfflineSocksProxyHost";
    public static final String A_zimbraPrefOfflineSocksProxyPort = "zimbraPrefOfflineSocksProxyPort";
    public static final String A_zimbraPrefOfflineSocksProxyUsername = "zimbraPrefOfflineSocksProxyUsername";
    public static final String A_zimbraPrefOfflineSocksProxyPassword = "zimbraPrefOfflineSocksProxyPassword";

    public static final String A_offlineYContactTmpID = "offlineYahooContactTmpId";
    public static final String A_offlineYContactToken = "offlineYahooContactToken";
    public static final String A_offlineYContactTokenSecret = "offlineYahooContactTokenSecret";
    public static final String A_offlineYContactTokenTimestamp = "offlineYahooContactTokenTimestamp";
    public static final String A_offlineYContactTokenSessionHandle = "offlineYahooContactTokenSessionHandle";
    public static final String A_offlineYContactGuid = "offlineYahooContactGuid";
    public static final String A_offlineYContactVerifier = "offlineYahooContactVerifier";
    public static final String A_offlineUsingGalAccountId = "offlineUsingGalAccountId";
    public static final String A_offlineGalRetryEnabled = "offlineGalRetryEnabled";

    private static final String CHN_BETA = "beta";

    public enum EntryType {
        ACCOUNT("acct"), DATASOURCE("dsrc", true), IDENTITY("idnt", true),
        SIGNATURE("sig", true), COS("cos"), CONFIG("conf"), ZIMLET("zmlt"),
        GAL("gal");

        private String mAbbr;
        private boolean mLeafEntry;

        private EntryType(String abbr)                { mAbbr = abbr; }
        private EntryType(String abbr, boolean leaf)  { mAbbr = abbr;  mLeafEntry = leaf; }

        public boolean isLeafEntry()  { return mLeafEntry; }

        @Override
        public String toString()      { return mAbbr; }

        public static EntryType typeForEntry(Entry e) {
            if (e instanceof Account)          return ACCOUNT;
            else if (e instanceof Identity)    return IDENTITY;
            else if (e instanceof DataSource)  return DATASOURCE;
            else if (e instanceof Signature)   return SIGNATURE;
            else if (e instanceof Cos)         return COS;
            else if (e instanceof Config)      return CONFIG;
            else if (e instanceof Zimlet)      return ZIMLET;
            else if (e instanceof OfflineDomainGal)      return GAL;
            else                               return null;
        }
    }

    public static OfflineProvisioning getOfflineInstance() {
        return (OfflineProvisioning) getInstance();
    }

    private static String appId = OfflineLC.zdesktop_app_id.value();

    private static String encryptData(String clear) throws ServiceException {
        if (appId == null)
            return clear;
        return DataSource.encryptData(appId, clear);
    }

    private static String decryptData(String crypt) throws ServiceException {
        if (appId == null)
            return crypt;
        return DataSource.decryptData(appId, crypt);
    }

    private final OfflineConfig mLocalConfig;
    private final Server mLocalServer;
    private final Cos mDefaultCos;
    private final List<MimeTypeInfo> mMimeTypes;
    private final Map<String, Zimlet> mZimlets;
    private final NamedEntryCache<Account> mAccountCache;
    private final NamedEntryCache<Account> mGranterCache;
    private final Map<String, Server> mSyncServerCache;
    private final Map<String, OfflineDomainGal> domainGals;
    private Account zimbraAdminAccount;
    private List<String> cachedAccountIds = new CopyOnWriteArrayList<String>();
    private volatile boolean mHasDirtyAccounts = true;

    public OfflineProvisioning() {
        mLocalConfig  = OfflineConfig.instantiate(this);
        mLocalServer  = OfflineLocalServer.instantiate(mLocalConfig, this);
        mDefaultCos   = OfflineCos.instantiate(this);
        mMimeTypes    = OfflineMimeType.instantiateAll();
        mZimlets      = OfflineZimlet.instantiateAll(this);
        domainGals = OfflineDomainGal.instantiateAll(this);
        mSyncServerCache = new HashMap<String, Server>();
        mAccountCache = new NamedEntryCache<Account>(64, LC.ldap_cache_account_maxage.intValue() * Constants.MILLIS_PER_MINUTE);
        mGranterCache = new NamedEntryCache<Account>(64, LC.ldap_cache_account_maxage.intValue() * Constants.MILLIS_PER_MINUTE);
        DbPool.disableUsageWarning();
    }

    @VisibleForTesting
    protected OfflineProvisioning(boolean extend) {
        mLocalConfig = null;
        mLocalServer = null;
        mDefaultCos = null;
        mMimeTypes = null;
        mZimlets = null;
        domainGals = null;
        mSyncServerCache = null;
        mAccountCache = null;
        mGranterCache = null;
    }

    //load cachedAccountIds from directory following orders in offlineAccountsOrder
    private void loadAccountIdsInOrder() {
        Account localAccount;
        try {
            localAccount = getLocalAccount();
            Iterable<String> idsInOrder = Splitter.on(",").split(localAccount.getAttr(A_offlineAccountsOrder, ""));
            List<String> directoryIds = DbOfflineDirectory.listAllDirectoryEntries(EntryType.ACCOUNT);
            List<String> ids = new ArrayList<String>();
            for (String id : idsInOrder) {
                if (directoryIds.contains(id)) {
                    ids.add(id);
                }
            }
            this.cachedAccountIds.addAll(ids);
        } catch (ServiceException e) {
            OfflineLog.offline.warn("load account Ids to cache failed.", e);
        }
    }

    public ZMailbox newZMailbox(OfflineAccount account, String serviceUri) throws ServiceException {
        ZMailbox.Options options;
        String uri = Offline.getServerURI(account, serviceUri);
        ZAuthToken authToken = OfflineSyncManager.getInstance().lookupAuthToken(account);
        if (authToken != null) {
            options = new ZMailbox.Options(authToken, uri);
        } else {
            options = new ZMailbox.Options(account.getAttr(Provisioning.A_mail), AccountBy.name, account.getAttr(A_offlineRemotePassword), uri);
        }
        options.setDebugListener(new Offline.OfflineDebugListener(account));
        return newZMailbox(options);
    }

    private ZMailbox newZMailbox(String email, String password, Map<String, Object> attrs, String serviceUri) throws ServiceException {
        String uri = Offline.getServerURI((String)attrs.get(A_offlineRemoteServerUri), serviceUri);
        ZMailbox.Options options = new ZMailbox.Options(email, AccountBy.name, password, uri);
        options.setDebugListener(new Offline.OfflineDebugListener());
        return newZMailbox(options);
    }

    private ZMailbox newZMailbox(ZMailbox.Options options) throws ServiceException {
        options.setNoSession(true);
        options.setUserAgent(OfflineLC.zdesktop_name.value(), OfflineLC.getFullVersion());
        options.setTimeout(OfflineLC.zdesktop_request_timeout.intValue());
        options.setRetryCount(1);
        ZMailbox zmbox = ZMailbox.getMailbox(options);
//        if (options.getAuthToken() == null) //it was auth by password
//        OfflineSyncManager.getInstance().authSuccess(options.getAccount(), options.getPassword(), zmbox.getAuthResult().getAuthToken(), zmbox.getAuthResult().getExpires());
        return zmbox;
    }

    @Override
    public void modifyAttrs(Entry e, Map<String, ? extends Object> attrs, boolean checkImmutable) throws ServiceException {
        modifyAttrs(e, attrs, checkImmutable, true);
    }

    @Override
    public void modifyAttrs(Entry e, Map<String, ? extends Object> attrs, boolean checkImmutable, boolean allowCallback) throws ServiceException {
        modifyAttrs(e, attrs, checkImmutable, allowCallback, e instanceof Account && (isZcsAccount((Account)e) || isLocalAccount((Account)e)));
    }

    void modifyAttrs(Entry e, Map<String, ? extends Object> attrs, boolean checkImmutable, boolean allowCallback, boolean markChanged) throws ServiceException {
        modifyAttrs(e, attrs, checkImmutable, allowCallback, markChanged, false);
    }

    @SuppressWarnings("unchecked")
    synchronized void modifyAttrs(Entry e, Map<String, ? extends Object> attrs, boolean checkImmutable, boolean allowCallback, boolean markChanged, boolean skipAttrMgr) throws ServiceException {
        EntryType etype = EntryType.typeForEntry(e);
        if (etype == null)
            throw OfflineServiceException.UNSUPPORTED("modifyAttrs(" + e.getClass().getSimpleName() + ")");
        else if (etype == EntryType.IDENTITY)
            throw ServiceException.INVALID_REQUEST("must use Provisioning.modifyIdentity() instead", null);
        else if (etype == EntryType.DATASOURCE)
            throw ServiceException.INVALID_REQUEST("must use Provisioning.modifyDataSource() instead", null);
        else if (etype == EntryType.SIGNATURE)
            throw ServiceException.INVALID_REQUEST("must use Provisioning.modifySignature() instead", null);

        if (allowCallback && e instanceof Account && isLocalAccount((Account)e) && attrs.containsKey(A_offlineAccountsOrder))
            ((Map<String, Object>)attrs).put(A_offlineAccountsOrder, promoteAccount((String)attrs.get(A_offlineAccountsOrder)));

        // only tracking changes on account entries
        markChanged &= e instanceof OfflineAccount;

        Map<String, ? extends Object> old = e.getAttrs();
        List<String> modattrs = new ArrayList<String>();
        if (markChanged) {
            attrs.remove(A_offlineModifiedAttrs);

            for (String attr : attrs.keySet()) {
                if (attr.startsWith("-") || attr.startsWith("+"))
                    attr = attr.substring(1);
                if (!modattrs.contains(attr) && !attr.toLowerCase().startsWith("offline") && !OfflineProvisioning.sOfflineAttributes.contains(attr))
                    modattrs.add(attr);
            }
            if (!modattrs.isEmpty()) {
                Map<String, Object> replacement = new HashMap<String, Object>(attrs.size() + 1);
                replacement.putAll(attrs);
                replacement.put('+' + A_offlineModifiedAttrs, modattrs.toArray(new String[modattrs.size()]));
                attrs = replacement;
            }
        }

        CallbackContext callbackContext = null;
        if (!skipAttrMgr) {
            callbackContext = new CallbackContext(CallbackContext.Op.MODIFY);
            AttributeManager.getInstance().preModify(attrs, e, callbackContext, checkImmutable, allowCallback);
        }

        boolean isAccountSetup = attrs.remove(A_offlineAccountSetup) != null;

        if (isAccountSetup && etype == EntryType.ACCOUNT)
            revalidateRemoteLogin((OfflineAccount)e, attrs);

        if (etype == EntryType.CONFIG) {
            DbOfflineDirectory.modifyDirectoryEntry(etype, A_offlineDn, "config", attrs, false);
        } else {
            DbOfflineDirectory.modifyDirectoryEntry(etype, A_zimbraId, e.getAttr(A_zimbraId), attrs, markChanged);
            mHasDirtyAccounts |= markChanged;
        }
        reload(e);
        if (!skipAttrMgr)
            AttributeManager.getInstance().postModify(attrs, e, callbackContext, allowCallback);

        long newTime = 0;
        long oldTime = 0;
        boolean syncEverything = false;
        String syncEmailoption = (String) attrs.get(OfflineConstants.A_offlinesyncEmailDate);

        if (syncEmailoption != null) {
            try {
                final ZcsMailbox mbox = (ZcsMailbox) MailboxManager.getInstance().getMailboxByAccount((OfflineAccount)e);
                //get the new time
                switch (SyncMsgOptions.getOption(syncEmailoption)) {
                case SYNCEVERYTHING:
                    if (!StringUtil.equalIgnoreCase((String)old.get(OfflineConstants.A_offlinesyncEmailDate), (String)attrs.get(OfflineConstants.A_offlinesyncEmailDate))) {
                        syncEverything = true;
                    }
                    break;
                case SYNCTOFIXEDDATE:
                    if (!StringUtil.equalIgnoreCase((String)old.get(OfflineConstants.A_offlinesyncEmailDate), (String)attrs.get(OfflineConstants.A_offlinesyncEmailDate)) ||
                            !StringUtil.equalIgnoreCase((String)old.get(OfflineConstants.A_offlinesyncFixedDate), (String)attrs.get(OfflineConstants.A_offlinesyncFixedDate))) {
                        newTime = DateUtil.getFixedDateSecs((String)attrs.get(OfflineConstants.A_offlinesyncFixedDate));
                    }
                    break;
                case SYNCTORELATIVEDATE:
                    if (!StringUtil.equalIgnoreCase((String)old.get(OfflineConstants.A_offlinesyncEmailDate), (String)attrs.get(OfflineConstants.A_offlinesyncEmailDate)) ||
                            !StringUtil.equalIgnoreCase((String)old.get(OfflineConstants.A_offlinesyncRelativeDate), (String)attrs.get(OfflineConstants.A_offlinesyncRelativeDate)) ||
                            !StringUtil.equalIgnoreCase((String)old.get(OfflineConstants.A_offlinesyncFieldName), (String)attrs.get(OfflineConstants.A_offlinesyncFieldName))) {
                        newTime = DateUtil.getRelativeDateSecs((String)attrs.get(OfflineConstants.A_offlinesyncRelativeDate) ,
                                    (String)attrs.get(OfflineConstants.A_offlinesyncFieldName));
                    }
                    break;
                }

                if( newTime > 0) {
                    //syncmail date is updated, get the old date/time
                    switch (SyncMsgOptions.getOption((String) old.get(OfflineConstants.A_offlinesyncEmailDate))) {
                    case SYNCTOFIXEDDATE:
                        oldTime = DateUtil.getFixedDateSecs((String)old.get(OfflineConstants.A_offlinesyncFixedDate));
                        break;
                    case SYNCTORELATIVEDATE:
                        oldTime = DateUtil.getRelativeDateSecs((String)old.get(OfflineConstants.A_offlinesyncRelativeDate) ,
                                (String)old.get(OfflineConstants.A_offlinesyncFieldName));
                        break;
                    }
                }

                if (newTime > oldTime) {
                    //reset the runInitSync variable if set to true
                    if (mbox.isRunInitSync()) {
                        mbox.setRunInitSync(false);
                    }
                } else if (syncEverything || (newTime < oldTime)) {
                    if (!mbox.isRunInitSync()) {
                        mbox.setRunInitSync(true);
                    }
                }
            } catch (NumberFormatException x) {
                OfflineLog.offline.warn("unable to parse syncEmailDate", x);
            }
        }
    }

    public void setAccountAttribute(Account account, String key, Object value) throws ServiceException {
        setAccountAttribute(account, key, value, false);
    }

    /*
     * Special way to set a single Account attribute that doesn't mark the account dirty.
     */
    public void setAccountAttribute(Account account, String key, Object value, boolean skipAttrMgr) throws ServiceException {
        Map<String, Object> attrs = new HashMap<String, Object>(1);
        attrs.put(key, value);
        modifyAttrs(account, attrs, false, true, false, skipAttrMgr);
    }

    /*
     * Special way to set a single attribute of a DataSource that doesn't mark the account dirty.
     */
    public void setDataSourceAttribute(DataSource ds, String key, Object value) throws ServiceException {
        Map<String, Object> attrs = new HashMap<String, Object>(1);
        attrs.put(key, value);
        modifyDataSource(ds.getAccount(), ds.getId(), attrs, false);
    }

    private void acceptSSLCertAlias(String sslCertAlias) throws ServiceException {
    try {
            TrustManagers.customTrustManager().acceptCertificates(sslCertAlias);
        } catch (GeneralSecurityException x) {
            throw RemoteServiceException.SSLCERT_NOT_ACCEPTED(x.getMessage(), x);
        }
    }

    private void revalidateRemoteLogin(OfflineAccount acct, Map<String, ? extends Object> changes) throws ServiceException {
        String password = acct.getAttr(A_offlineRemotePassword);
        String baseUri = acct.getAttr(A_offlineRemoteServerUri);

        boolean hasChange = false;
        for (Map.Entry<String, ? extends Object> change : changes.entrySet()) {
            String name = change.getKey();
            if (name.startsWith("-"))
                continue;
            else if (name.startsWith("+"))
                name = name.substring(1);

            if (name.equalsIgnoreCase(A_offlineRemotePassword)) {
                String newPassword = (String)change.getValue();
                hasChange |= !password.equals(newPassword);
                password = newPassword;
            } else if (name.equalsIgnoreCase(A_offlineRemoteServerUri)) {
                String newBaseUri = (String)change.getValue();
                hasChange |= !baseUri.equals(newBaseUri);
                baseUri = newBaseUri;
            }
        }

        String sslCertAlias = (String)changes.remove(OfflineConstants.A_offlineSslCertAlias);
        if (sslCertAlias != null)
            acceptSSLCertAlias(sslCertAlias);

        // fetch the mailbox; this will throw an exception if the username/password/URI are incorrect
        ZMailbox.Options options = new ZMailbox.Options(acct.getAttr(Provisioning.A_mail), AccountBy.name, password, Offline.getServerURI(baseUri, AccountConstants.USER_SERVICE_URI));
        newZMailbox(options);
        OfflineSyncManager.getInstance().clearErrorCode(acct);
    }

    @Override
    public synchronized void reload(Entry e) throws ServiceException {
        if (e instanceof OfflineLocalServer)
            return; //no need to reload

        EntryType etype = EntryType.typeForEntry(e);
        if (etype == null)
            throw OfflineServiceException.UNSUPPORTED("reload(" + e.getClass().getSimpleName() + ")");

        Map<String,Object> attrs;
        if (etype == EntryType.IDENTITY && e instanceof OfflineIdentity) {
            attrs = DbOfflineDirectory.readDirectoryLeaf(etype, ((OfflineIdentity) e).getAccount(), A_zimbraId, e.getAttr(A_zimbraPrefIdentityId));
            ((OfflineIdentity) e).setName(e.getAttr(A_zimbraPrefIdentityName));
        } else if (etype == EntryType.DATASOURCE && e instanceof OfflineDataSource) {
            attrs = DbOfflineDirectory.readDirectoryLeaf(etype, ((OfflineDataSource) e).getAccount(), A_zimbraId, e.getAttr(A_zimbraDataSourceId));
            ((OfflineDataSource) e).setName(e.getAttr(A_zimbraDataSourceName));
            ((OfflineDataSource) e).setServiceName(e.getAttr(Provisioning.A_zimbraDataSourceDomain));
        } else if (etype == EntryType.SIGNATURE && e instanceof OfflineSignature) {
            attrs = DbOfflineDirectory.readDirectoryLeaf(etype, ((OfflineSignature) e).getAccount(), A_zimbraId, e.getAttr(A_zimbraSignatureId));
            ((OfflineSignature) e).setName(e.getAttr(A_zimbraSignatureName));
        } else if (etype == EntryType.CONFIG) {
            attrs = OfflineConfig.instantiate(this).getAttrs();
        } else if (etype == EntryType.COS && e instanceof OfflineCos) {
            attrs = OfflineCos.instantiate(this).getAttrs();
        } else {
            attrs = DbOfflineDirectory.readDirectoryEntry(etype, A_zimbraId, e.getAttr(A_zimbraId));
        }
        if (attrs == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(e.getAttr(A_mail));
        e.setAttrs(attrs);
    }

    @Override
    public synchronized boolean inDistributionList(Account acct, String zimbraId) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("inDistributionList");
    }

    @Override
    public synchronized Set<String> getDistributionLists(Account acct) throws ServiceException {
        return null;
    }
    
    @Override
    public Set<String> getDirectDistributionLists(Account acct)
            throws ServiceException {
        return null;
    }

    @Override
    public synchronized List<DistributionList> getDistributionLists(Account acct, boolean directOnly, Map<String, String> via) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("getDistributionLists");
    }

    @Override
    public synchronized List<DistributionList> getDistributionLists(DistributionList list, boolean directOnly, Map<String, String> via) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("getDistributionLists");
    }

    @Override
    public synchronized boolean healthCheck() {
        try {
            DbOfflineDirectory.readDirectoryEntry(EntryType.CONFIG, A_offlineDn, "config");
            return true;
        } catch (ServiceException e) {
            OfflineLog.offline.info("health check failed", e);
            return false;
        }
    }

    @Override
    public Config getConfig() {
        return mLocalConfig;
    }

    @Override
    public synchronized GlobalGrant getGlobalGrant() throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("getGlobalGrant");
    }

    @Override
    public synchronized List<MimeTypeInfo> getMimeTypes(String name) {
        List<MimeTypeInfo> mimeTypes = new ArrayList<MimeTypeInfo>();
        for (MimeTypeInfo mtinfo : mMimeTypes) {
            for (String type : mtinfo.getMimeTypes()) {
                if (type.equalsIgnoreCase(name))
                    mimeTypes.add(mtinfo);
            }
        }
        return mimeTypes;
    }

    @Override
    public synchronized List<MimeTypeInfo> getAllMimeTypes() {
        return mMimeTypes;
    }

    static final Set<String> sOfflineAttributes = new HashSet<String>(Arrays.asList(
            A_zimbraId,
            A_mail,
            A_uid,
            A_objectClass,
            A_zimbraMailHost,
            A_displayName,
            A_sn,
            A_zimbraAccountStatus,
            A_zimbraFeatureNotebookEnabled,
            A_zimbraPrefNotebookSyncEnabled,
            A_zimbraPrefSkin,
            A_zimbraZimletAvailableZimlets,
            A_zimbraPrefClientType,
            A_zimbraPrefLabel,
            A_zimbraPrefMailPollingInterval,
            A_zimbraChildAccount,
            A_zimbraPrefChildVisibleAccount,
            A_zimbraPrefMailtoHandlerEnabled,
            A_zimbraPrefMailtoAccountId,
            A_zimbraJunkMessagesIndexingEnabled,
            A_zimbraPrefMailToasterEnabled,
            A_zimbraPrefCalendarToasterEnabled,
            A_zimbraPrefShareContactsInAutoComplete,
            A_zimbraMailQuota,
            A_zimbraCreateTimestamp,
            A_zimbraDumpsterEnabled,
            A_zimbraPrefOfflineBackupAccountId,
            A_zimbraPrefOfflineBackupInterval,
            A_zimbraPrefOfflineBackupKeep,
            A_zimbraPrefOfflineBackupPath,
            A_zimbraPrefOfflineZimletSyncAccountId,
            A_zimbraPrefOfflineAttrProxyMode,
            A_zimbraPrefOfflineHttpProxyHost,
            A_zimbraPrefOfflineHttpProxyPort,
            A_zimbraPrefOfflineHttpProxyUsername,
            A_zimbraPrefOfflineHttpProxyPassword,
            A_zimbraPrefOfflineSocksProxyHost,
            A_zimbraPrefOfflineSocksProxyPort,
            A_zimbraPrefOfflineSocksProxyUsername,
            A_zimbraPrefOfflineSocksProxyPassword,
            A_zimbraPrefOfflineUpdateChannel
    ));

    @Override
    public synchronized Account createAccount(String emailAddress, String password, Map<String, Object> attrs) throws ServiceException {
        String dsName = (String)attrs.get(A_offlineDataSourceName);
        Account account;
        if (dsName != null) {
            account = createDataSourceAccount(dsName, emailAddress, password, attrs);
        } else {
            account = createSyncAccount(emailAddress, password, attrs);
        }
        persistAccountsOrder();
        return account;
    }

    @Override
    public synchronized Account restoreAccount(String emailAddress, String password, Map<String, Object> attrs,
            Map<String, Object> origAttrs) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("restoreAccount");
    }

    private OfflineAccount.Version MIN_ZCS_VER = new OfflineAccount.Version("5.0");

    private synchronized Account createSyncAccount(String emailAddress, String password, Map<String, Object> attrs) throws ServiceException {
        if (attrs == null || !(attrs.get(A_offlineRemoteServerUri) instanceof String))
            throw ServiceException.FAILURE("need single offlineRemoteServerUri when creating account: " + emailAddress, null);

        String parts[] = emailAddress.split("@");
        if (parts.length != 2)
            throw ServiceException.INVALID_REQUEST("must be valid email address: " + emailAddress, null);
        String uid = parts[0];

        String sslCertAlias = (String)attrs.remove(OfflineConstants.A_offlineSslCertAlias);
        if (sslCertAlias != null)
            acceptSSLCertAlias(sslCertAlias);

        ZGetInfoResult zgi = newZMailbox(emailAddress, (String)attrs.get(A_offlineRemotePassword), attrs, AccountConstants.USER_SERVICE_URI).getAccountInfo(false);
        OfflineLog.offline.info("Remote Zimbra Server Version: " + zgi.getVersion());
        OfflineAccount.Version remoteVersion = new OfflineAccount.Version(zgi.getVersion());
        if (!remoteVersion.isAtLeast(MIN_ZCS_VER))
            throw ServiceException.FAILURE("Remote server version " + remoteVersion + ", ZCS 5.0 or later required", null);

        attrs.put(A_offlineRemoteServerVersion, zgi.getVersion());
        emailAddress = zgi.getName();

        for (Map.Entry<String,List<String>> zattr : zgi.getAttrs().entrySet())
            for (String value : zattr.getValue())
                addToMap(attrs, zattr.getKey(), value);
        for (Map.Entry<String,List<String>> zpref : zgi.getPrefAttrs().entrySet())
            for (String value : zpref.getValue())
                addToMap(attrs, zpref.getKey(), value);
        attrs.put(A_objectClass, new String[] { "organizationalPerson", "zimbraAccount" } );
        attrs.put(A_zimbraMailHost, "localhost");
        attrs.put(A_uid, uid);
        attrs.put(A_mail, emailAddress);
        attrs.put(A_zimbraId, zgi.getId());
        if (!(attrs.get(A_cn) instanceof String))
            attrs.put(A_cn, attrs.get(A_displayName) instanceof String ? (String) attrs.get(A_displayName) : uid);
        if (!(attrs.get(A_sn) instanceof String))
            attrs.put(A_sn, uid);
        if (!(attrs.get(A_zimbraAccountStatus) instanceof String))
            attrs.put(A_zimbraAccountStatus, ACCOUNT_STATUS_ACTIVE);

        attrs.put(A_offlineFeatureSmtpEnabled, ProvisioningConstants.TRUE);
        attrs.remove(A_zimbraIsAdminAccount);
        attrs.remove(A_zimbraIsDomainAdminAccount);

        String[] skins = mLocalConfig.getMultiAttr(Provisioning.A_zimbraInstalledSkin);
        attrs.put(A_zimbraPrefSkin, skins == null || skins.length == 0 ? "yahoo" : skins[0]);

        attrs.put(A_zimbraPrefMailPollingInterval, OfflineLC.zdesktop_client_poll_interval.value());

        attrs.put(A_zimbraPrefClientType, "advanced");
        attrs.put(A_zimbraPrefAccountTreeOpen , getAllAccounts().size() == 0 ? ProvisioningConstants.TRUE : ProvisioningConstants.FALSE);
        attrs.put(A_zimbraFeatureSharingEnabled, ProvisioningConstants.TRUE);

        attrs.remove(A_zimbraChildAccount);
        attrs.remove(A_zimbraPrefChildVisibleAccount);

        attrs.put(A_zimbraJunkMessagesIndexingEnabled, ProvisioningConstants.TRUE);
        attrs.put(A_zimbraMailQuota, "0");

        Account account = createAccountInternal(emailAddress, zgi.getId(), attrs, true, false);

        try {
            // create identity entries in database
            for (ZIdentity zident : zgi.getIdentities())
                DirectorySync.getInstance().syncIdentity(this, account, zident);
        } catch (ServiceException e) {
            OfflineLog.offline.error("error initializing account " + emailAddress, e);
            deleteAccount(zgi.getId());
            throw e;
        }
        return account;
    }

    public synchronized List<Account> getAllZcsAccounts() throws ServiceException {
        List<Account> accounts = getAllAccounts();
        for (Iterator<Account> i = accounts.iterator(); i.hasNext();) {
            if (!isZcsAccount(i.next()))
                i.remove();
        }
        return accounts;
    }

    public boolean isZcsAccount(Account account) {
        return account.getAttr(A_offlineRemoteServerUri, null) != null;
    }

    public void testDataSource(OfflineDataSource ds) throws ServiceException {
        try {
            DataSourceManager.test(ds);
        } catch (ServiceException x) {
            x.printStackTrace();
            Throwable t = SystemUtil.getInnermostException(x);
            t.printStackTrace();
            if (x instanceof RemoteServiceException)
                throw x;
            if (t instanceof LoginException)
                throw RemoteServiceException.AUTH_FAILURE(t.getMessage(), t);
            if (t instanceof AuthenticationException)
                throw (AuthenticationException)t;
            if (t instanceof com.google.gdata.util.ServiceException)
                GDataServiceException.doFailures((com.google.gdata.util.ServiceException)t);
            RemoteServiceException.doConnectionFailures(ds.getHost() + ":" + ds.getPort(), t);
            RemoteServiceException.doSSLFailures(t.getMessage(), t);
            throw x;
        }

        // No need to test Live/YMail SOAP access, since successful IMAP/POP3
        // connection implies that SOAP access will succeed with same auth
        // credentials.
        boolean isYBizmail = ds.isYBizmail();
        if (ds.needsSmtpAuth() || isYBizmail) {
            OfflineLog.offline.info("SMTP Testing: %s", ds);
            try {
                Session session = isYBizmail ?
                    ds.getYBizmailSession() : LocalJMSession.getSession(ds);
                session.setDebug(true);
                Transport smtp = session.getTransport();
                if (smtp instanceof SmtpTransport) {
                    test((SmtpTransport) smtp, ds.getEmailAddress());
                } else if (smtp instanceof SMTPTransport) {
                    test((SMTPTransport) smtp, ds.getEmailAddress());
                } else {
                    throw new AssertionError();
                }
                OfflineLog.offline.info("SMTP Test Succeeded: %s", ds);
            } catch (Exception e) {
                Throwable t = SystemUtil.getInnermostException(e);
                if (t instanceof AuthenticationFailedException)
                    throw RemoteServiceException.SMTP_AUTH_FAILURE(t.getMessage(), t);
                else if (t instanceof MessagingException && t.getMessage() != null && t.getMessage().startsWith("530"))
                    throw RemoteServiceException.SMTP_AUTH_REQUIRED(t.getMessage(), t);
                RemoteServiceException.doConnectionFailures("SMTP server", t);
                RemoteServiceException.doSSLFailures(t.getMessage(), t);
                throw ServiceException.FAILURE("SMTP connect failure", t);
            }
        }
    }

    //TODO: remove Sun's SMTP client once Zimbra's one gets stabilized
    private void test(SMTPTransport smtp, String mailfrom) throws MessagingException {
        smtp.connect();
        smtp.issueCommand("MAIL FROM:<" + mailfrom + ">", 250);
        smtp.issueCommand("RSET", 250);
        smtp.close();
    }

    private void test(SmtpTransport smtp, String mailfrom) throws MessagingException {
        smtp.connect();
        smtp.mail(mailfrom);
        smtp.rset();
        smtp.close();
    }

    private synchronized Account createDataSourceAccount(String dsName, String emailAddress, String _password, Map<String, Object> dsAttrs) throws ServiceException {
        emailAddress = emailAddress.toLowerCase().trim();
        String parts[] = emailAddress.split("@");
        if (parts.length != 2)
            throw ServiceException.INVALID_REQUEST("must be valid email address: "+emailAddress, null);

        String localPart = parts[0];
        String domain = parts[1];
        domain = IDNUtil.toAsciiDomainName(domain);
        emailAddress = localPart + "@" + domain;
        validEmailAddress(emailAddress);

        //first we need to verify datasource
        String accountLabel = (String)dsAttrs.remove(A_zimbraPrefLabel);
        dsAttrs.remove(A_offlineDataSourceName);
        String dsType = (String)dsAttrs.remove(A_offlineDataSourceType);
        DataSourceType type = DataSourceType.valueOf(dsType);
        String dsid = UUID.randomUUID().toString();
        dsAttrs.put(A_zimbraDataSourceId, dsid);
        String sslCertAlias = (String)dsAttrs.remove(OfflineConstants.A_zimbraDataSourceSslCertAlias);
        if (sslCertAlias != null)
            acceptSSLCertAlias(sslCertAlias);
        encryptPasswords(dsAttrs);

        OfflineDataSource testDs = new OfflineDataSource(getLocalAccount(), type, dsName, dsid, dsAttrs, this);
        testDataSource(testDs);

        String accountId = UUID.randomUUID().toString();

        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(A_offlineDataSourceName, dsName);
        attrs.put(A_zimbraPrefLabel, accountLabel);
        String displayName = (String)dsAttrs.get(A_zimbraPrefFromDisplay);
        if (displayName != null) {
            attrs.put(A_displayName, displayName);
            attrs.put(A_zimbraPrefFromDisplay, displayName);
        }
        String accountFlavor = (String)dsAttrs.get(A_offlineAccountFlavor);
        if (accountFlavor != null)
            attrs.put(A_offlineAccountFlavor, accountFlavor);

        attrs.put(A_objectClass, new String[] { "organizationalPerson", "zimbraAccount" } );
        attrs.put(A_zimbraMailHost, "localhost");
        attrs.put(A_uid, localPart);
        attrs.put(A_mail, emailAddress);
        attrs.put(A_zimbraId, accountId);
        attrs.put(A_cn, localPart);
        attrs.put(A_sn, localPart);

        attrs.put(A_zimbraAccountStatus, ACCOUNT_STATUS_ACTIVE);

        setDefaultAccountAttributes(attrs);

        Object syncEnabled = dsAttrs.get(A_zimbraDataSourceContactSyncEnabled);
        attrs.put(A_zimbraFeatureContactsEnabled, syncEnabled == null ? ProvisioningConstants.FALSE : syncEnabled);
        syncEnabled = dsAttrs.get(A_zimbraDataSourceCalendarSyncEnabled);
        attrs.put(A_zimbraFeatureCalendarEnabled, syncEnabled == null ? ProvisioningConstants.FALSE : syncEnabled);
        syncEnabled = dsAttrs.get(A_zimbraDataSourceTaskSyncEnabled);
        attrs.put(A_zimbraFeatureTasksEnabled, syncEnabled == null ? ProvisioningConstants.FALSE : syncEnabled);
        syncEnabled = dsAttrs.get(A_zimbraDataSourceSmtpEnabled);
        attrs.put(A_offlineFeatureSmtpEnabled, syncEnabled == null ? ProvisioningConstants.TRUE : syncEnabled);

        attrs.put(A_zimbraFeatureBriefcasesEnabled, ProvisioningConstants.FALSE);
        attrs.put(A_zimbraFeatureGalEnabled, ProvisioningConstants.FALSE);
        attrs.put(A_zimbraFeatureIMEnabled, ProvisioningConstants.FALSE);
        attrs.put(A_zimbraFeatureNotebookEnabled, ProvisioningConstants.FALSE);
        attrs.put(A_zimbraPrefAccountTreeOpen , getAllAccounts().size() == 0 ? ProvisioningConstants.TRUE : ProvisioningConstants.FALSE);

        Account account = createAccountInternal(emailAddress, accountId, attrs, false, false);
        OfflineDataSource ds = null;

        try {
            ds = (OfflineDataSource) createDataSource(account, type, dsName, dsAttrs, true, false);
            MailboxManager.getInstance().getMailboxByAccount(account);
            createNotificationMountpoints(accountId);
        } catch (ServiceException e) {
            OfflineLog.offline.warn("failed creating datasource mailbox: " + dsName, e);
            deleteAccount(account.getId());
            throw e;
        }

        // Bug 31998: If Yahoo then copy auth tokens from test data source to
        // new data source and clear original tokens
        if (testDs.isYahoo()) {
            DataSourceManager dsm = DataSourceManager.getInstance();
            Mailbox mbox = dsm.getMailbox(testDs);
            MetadataTokenStore.copyTokens(mbox, dsm.getMailbox(ds));
            MetadataTokenStore.clearTokens(mbox);
            OfflineYAuth.deleteRawAuthManager(mbox);
            // for yahoo contact API (address book)
            OfflineLog.offline.info("sync contacts enabled: " + attrs.get(A_zimbraFeatureContactsEnabled));
            if (Boolean.parseBoolean((String) (attrs.get(A_zimbraFeatureContactsEnabled)))) {
                OAuthManager.persistCredential(account.getId(), (String) dsAttrs.get(A_offlineYContactToken),
                        (String) dsAttrs.get(A_offlineYContactTokenSecret),
                        (String) dsAttrs.get(A_offlineYContactTokenSessionHandle),
                        (String) dsAttrs.get(A_offlineYContactGuid),
                        (String) dsAttrs.get(A_offlineYContactTokenTimestamp),
                        (String) dsAttrs.get(A_offlineYContactVerifier));
                //create mailbox from scratch, don't need migration
                if (OAuthManager.hasOAuthToken(account.getId())) {
                    setDataSourceAttribute(ds, OfflineConstants.A_offlineYContactTokenReady, ProvisioningConstants.TRUE);
                    YContactSync.skipMigration(mbox);
                }
            }
        }
        return account;
    }

    private static void encryptPasswords(Map<String, Object> dsAttrs) throws ServiceException {
        String id = (String) dsAttrs.get(A_zimbraDataSourceId);
        String pass = (String) dsAttrs.get(A_zimbraDataSourcePassword);
        dsAttrs.put(A_zimbraDataSourcePassword, DataSource.encryptData(id, pass));
        String smtpPass = (String) dsAttrs.get(A_zimbraDataSourceSmtpAuthPassword);
        if (smtpPass != null) {
            dsAttrs.put(A_zimbraDataSourceSmtpAuthPassword, DataSource.encryptData(id, smtpPass));
        }
    }

    public synchronized List<Account> getAllDataSourceAccounts() throws ServiceException {
        List<Account> accounts = getAllAccounts();
        for (Iterator<Account> i = accounts.iterator(); i.hasNext();) {
            if (!isDataSourceAccount(i.next()))
                i.remove();
        }
        return accounts;
    }

    public static boolean isDataSourceAccount(Account account) {
        return account.getAttr(A_offlineDataSourceName, null) != null;
    }

    public static String getDataSourceName(Account account) {
        return account.getAttr(A_offlineDataSourceName, null);
    }

    public synchronized DataSource getDataSource(Account account) throws ServiceException {
        if (!isDataSourceAccount(account))
            return null;
        return get(account, Key.DataSourceBy.name, getDataSourceName(account));
    }

    public synchronized List<DataSource> getAllDataSources() throws ServiceException {
        List<Account> accounts = getAllDataSourceAccounts();
        List<DataSource> dataSources = new ArrayList<DataSource>(accounts.size());
        for (Account account : accounts)
            dataSources.add(getDataSource(account));
        return dataSources;
    }

    public boolean isExchangeAccount(Account account) {
        return "Xsync".equals(account.getAttr(A_offlineAccountFlavor, null));
    }

    public String getOfflineGalMailboxName(String domain) {
        return "offline_gal@" + domain + OfflineConstants.GAL_ACCOUNT_SUFFIX;
    }

    public synchronized OfflineAccount createGalAccount(OfflineDomainGal domainGal) throws ServiceException {
        String id = UUID.randomUUID().toString();
        String name = getOfflineGalMailboxName(domainGal.getDomain());

        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(A_objectClass, new String[] { "organizationalPerson", "zimbraAccount" } );
        attrs.put(A_zimbraMailHost, "localhost");
        attrs.put(A_uid, id);
        attrs.put(A_mail, name);
        attrs.put(A_zimbraId, id);
        attrs.put(A_cn, id);
        attrs.put(A_sn, id);
        attrs.put(A_zimbraAccountStatus, ACCOUNT_STATUS_ACTIVE);
        attrs.put(A_offlineGalAccountLastRefresh, "0");
        attrs.put(A_offlineAccountFlavor, "Gal");
        setDefaultAccountAttributes(attrs);

        OfflineAccount galAcct = (OfflineAccount)createAccountInternal(name, id, attrs, true, false);
        setAccountAttribute(galAcct, OfflineConstants.A_offlineGalGroupMembersPopulated, ProvisioningConstants.FALSE);
        setAccountAttribute(galAcct, OfflineConstants.A_offlineGalAccountSyncToken, "");
        setAccountAttribute(galAcct, OfflineConstants.A_offlineGalAccountId, galAcct.getId());

        return galAcct;
    }

    public synchronized void createMountpointAccount(String name, String id, OfflineAccount account) throws ServiceException {
        String granteeId = account.getId();
        DbOfflineDirectory.GranterEntry ge = DbOfflineDirectory.readGranter(name, granteeId);
        if (ge != null && !id.equals(ge.id)) {
            DbOfflineDirectory.deleteGranter(name, ge.id);
            mGranterCache.remove(ge.name, ge.id);
            ge = null;
        }
        if (ge == null) {
            GranterEntry existing = lookupGranter("id", id, null);
            DbOfflineDirectory.createGranterEntry(name, id, granteeId);
            makeGranter(name, id, account, existing != null);
        }
    }

    private DbOfflineDirectory.GranterEntry lookupGranter(String by, String key, String granteeId) throws ServiceException {
        List<DbOfflineDirectory.GranterEntry> ents = DbOfflineDirectory.searchGranter(by, key);
        if (ents.size() > 1) {
            if (granteeId != null) {
                for (DbOfflineDirectory.GranterEntry ge : ents) {
                    if (granteeId.equals(ge.granteeId)) {
                        return ge;
                    }
                }
                OfflineLog.offline.warn("could not find matching grantee %s", granteeId);
            }
            GranterEntry ge = ents.get(0);
            ge.setAmbiguousGranter(true);
            return ge;
        } else {
            return ents.isEmpty() ? null : ents.get(0);
        }
    }

    private OfflineAccount makeGranter(GranterEntry granter) throws ServiceException {
        String granteeId = granter.granteeId;
        OfflineAccount grantee = (OfflineAccount)get(Key.AccountBy.id, granteeId);
        if (grantee == null)
            throw OfflineServiceException.MOUNT_INVALID_GRANTEE();
        return makeGranter(granter.name, granter.id, grantee, granter.isAmbiguousGranter());
    }

    private OfflineAccount makeGranter(String name, String id, OfflineAccount grantee, boolean ambiguous) throws ServiceException {
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(A_objectClass, new String[] { "organizationalPerson", "zimbraAccount" } );
        attrs.put(A_zimbraMailHost, ""); // the right value is set in loadRemoteSyncServer()
        attrs.put(A_uid, id);
        attrs.put(A_mail, name);
        attrs.put(A_zimbraId, id);
        attrs.put(A_cn, id);
        attrs.put(A_sn, id);
        attrs.put(A_zimbraAccountStatus, ACCOUNT_STATUS_ACTIVE);
        attrs.put(A_offlineMountpointProxyAccountId, grantee.getId());
        attrs.put(A_offlineAccountFlavor, "Granter");
        attrs.put(A_offlineAmbiguousGranter, ambiguous ? ProvisioningConstants.TRUE : ProvisioningConstants.FALSE);
        setDefaultAccountAttributes(attrs);

        OfflineAccount acct = new OfflineAccount(name, id, attrs, mDefaultCos.getAccountDefaults(), getLocalAccount(), this);
        mGranterCache.put(acct);
        return acct;
    }

    private static final String LOCAL_ACCOUNT_UID = "local";
    private static final String LOCAL_ACCOUNT_FLAVOR = "Local";
    public static final String LOCAL_ACCOUNT_NAME = LOCAL_ACCOUNT_UID + "@host.local";

    private synchronized Account createLocalAccount() throws ServiceException {
        String clientId = UUID.randomUUID().toString();
        OfflineLog.offline.info("Client ID: %s", clientId);

        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(A_objectClass, new String[] { "organizationalPerson", "zimbraAccount" } );
        attrs.put(A_zimbraMailHost, "localhost");
        attrs.put(A_uid, LOCAL_ACCOUNT_UID);
        attrs.put(A_mail, LOCAL_ACCOUNT_NAME);
        attrs.put(A_zimbraId, LOCAL_ACCOUNT_ID);
        attrs.put(A_cn, LOCAL_ACCOUNT_UID);
        attrs.put(A_sn, LOCAL_ACCOUNT_UID);
        attrs.put(A_offlineClientId, clientId);
        attrs.put(A_offlineAccountFlavor, LOCAL_ACCOUNT_FLAVOR);
        attrs.put(A_zimbraAccountStatus, ACCOUNT_STATUS_ACTIVE);
        attrs.put(A_zimbraPrefAccountTreeOpen , ProvisioningConstants.TRUE);
        attrs.put(A_zimbraPrefCalendarAlwaysShowMiniCal , ProvisioningConstants.TRUE);
        attrs.put(A_zimbraPrefShareContactsInAutoComplete, ProvisioningConstants.TRUE);
        attrs.put(A_zimbraPrefGetMailAction, "update");
        attrs.put(A_zimbraPrefOfflineBackupKeep, "2");
        attrs.put(A_zimbraPrefOfflineBackupAccountId, new String[] {LOCAL_ACCOUNT_ID});
        attrs.put(A_zimbraPrefOfflineBackupInterval, "0");
        setDefaultAccountAttributes(attrs);

        Account account = createAccountInternal(LOCAL_ACCOUNT_NAME, LOCAL_ACCOUNT_ID, attrs, true, false);
        return account;
    }

    private Account localAccount = null;

    public Account getLocalAccount() throws ServiceException {
        if (localAccount == null) {
            synchronized (this) {
                if (localAccount == null) {
                    Account account = get(AccountBy.id, LOCAL_ACCOUNT_ID);
                    if (account == null) {
                        account = createLocalAccount();
                        String uri = "http://127.0.0.1:" + LC.zimbra_admin_service_port.value() + "/desktop/login.jsp?at=" + OfflineLC.zdesktop_installation_key.value();
                        String webappUri = account.getAttr(A_offlineWebappUri, null);
                        if (webappUri == null || !webappUri.equals(uri))
                            setAccountAttribute(account, A_offlineWebappUri, uri);
                        if (OfflineLC.zdesktop_relabel.value().equalsIgnoreCase(CHN_BETA) && !CHN_BETA.equalsIgnoreCase(account.getAttr(A_zimbraPrefOfflineUpdateChannel, null))) {
                            setAccountAttribute(account, A_zimbraPrefOfflineUpdateChannel, CHN_BETA);
                        }
                    }
                    localAccount = account;
                }
            }
        }
        return localAccount;
    }

    public synchronized String getClientId() throws ServiceException {
        Account account = getLocalAccount();
        String clientId = account.getAttr(A_offlineClientId, null);
        if (clientId == null) {
            clientId = UUID.randomUUID().toString();
            OfflineLog.offline.info("Client ID: %s", clientId);
            setAccountAttribute(account, A_offlineClientId, clientId);
        }
        return clientId;
    }

    /**
     * For Gal migration. Before migration, sync token is in zcs account, last refresh is in gal account.
     * @return Map of Account-which-has-gal -> Pair of (syncToken, lastRefresh)
     * @throws ServiceException
     */
    public synchronized Map<Account, Pair<String, String>> getGalAccountTokens() throws ServiceException {
        Map<Account, Pair<String, String>> map = new HashMap<Account, Pair<String, String>>();
        for (Account account : getAllZcsAccounts()) {
            String galAccountId = account.getAttr(OfflineConstants.A_offlineGalAccountId);
            if (!Strings.isNullOrEmpty(galAccountId)) {
                Account galAccount = get(AccountBy.id, galAccountId);
                Pair<String, String> pair = new Pair<String, String>(account.getAttr(OfflineConstants.A_offlineGalAccountSyncToken),
                        galAccount.getAttr(A_offlineGalAccountLastRefresh));
                map.put(account, pair);
            }
        }
        return map;
    }

    public boolean isGalAccount(Account account) {
        return "Gal".equals(account.getAttr(A_offlineAccountFlavor, null));
    }

    public boolean isMountpointAccount(String accountId) throws ServiceException {
        Account acct = getAccountById(accountId);
        return (acct != null && isMountpointAccount(acct));
    }

    public boolean isMountpointAccount(Account account) {
        return account.getAttr(A_offlineMountpointProxyAccountId, null) != null;
    }

    public boolean isLocalAccount(Account account) {
        return account.getId().equals(LOCAL_ACCOUNT_ID);
    }

    private synchronized Account createAccountInternal(String emailAddress, String accountId, Map<String, Object> attrs, boolean initMailbox, boolean skipAttrMgr) throws ServiceException {
        CallbackContext callbackContext = null;
        String flavor = (String)attrs.get(A_offlineAccountFlavor);
        Map<String,Object> immutable = new HashMap<String, Object>();

        for (String attr : AttributeManager.getInstance().getImmutableAttrs()) {
            if (attrs.containsKey(attr))
                immutable.put(attr, attrs.remove(attr));
        }

        if (!skipAttrMgr) {
            callbackContext = new CallbackContext(CallbackContext.Op.CREATE);
            Map<String, Object> validAttrs = new HashMap<String, Object>(attrs);
            //premodify modifies attrs; need a tmp copy in case we need to drop some attrs and try again
            try {
                AttributeManager.getInstance().preModify(validAttrs, null, callbackContext, true);
                attrs = validAttrs;
            } catch (ServiceException e) {
                if (e.getMessage().contains(A_zimbraPrefMailSignature)) {
                    OfflineLog.offline.warn("invalid signature exists in ZCS account. Perhaps server decreased limit after signature was created?");
                    attrs.remove(A_zimbraPrefMailSignature);
                    AttributeManager.getInstance().preModify(attrs, null, callbackContext, true);
                    OfflineLog.offline.debug("attrs otherwise valid; just ignoring signatures for now");
                } else {
                    throw e;
                }
            }

        }

        attrs.putAll(immutable);
        attrs.put(A_offlineAccountFlavor, flavor);
        attrs.put(A_zimbraPrefSearchTreeOpen, ProvisioningConstants.FALSE);
        attrs.put(A_zimbraPrefTagTreeOpen , ProvisioningConstants.FALSE);
        attrs.put(A_zimbraDumpsterEnabled, ProvisioningConstants.FALSE);

        // create account entry in database
        DbOfflineDirectory.createDirectoryEntry(EntryType.ACCOUNT, emailAddress, attrs, false);

        Account acct = new OfflineAccount(emailAddress, accountId, attrs, mDefaultCos.getAccountDefaults(), accountId.equals(LOCAL_ACCOUNT_ID) ? null : getLocalAccount(), this);
        mAccountCache.put(acct);

        if (!skipAttrMgr)
            AttributeManager.getInstance().postModify(attrs, acct, callbackContext);

        if (initMailbox) {
            try {
                MailboxManager.getInstance().getMailboxByAccount(acct);
                createNotificationMountpoints(accountId);
            } catch (ServiceException e) {
                OfflineLog.offline.error("error initializing account "
                    + emailAddress, e);
                mAccountCache.remove(acct);
                deleteAccount(accountId);
                throw e;
            }
        }

        this.cachedAccountIds.add(accountId);

        return acct;
    }

    private void createNotificationMountpoints(String accountId) throws ServiceException {
        if (!accountId.equals(LOCAL_ACCOUNT_ID)) {
            Mailbox mbox = MailboxManager.getInstance().
                getMailboxByAccount(getLocalAccount());

            mbox.createMountpoint(null,
                DesktopMailbox.ID_FOLDER_NOTIFICATIONS, accountId,
                accountId, Mailbox.ID_FOLDER_USER_ROOT, null,
                MailItem.Type.UNKNOWN, 0, MailItem.DEFAULT_COLOR_RGB, false);
        }
    }

    private void setDefaultAccountAttributes(Map<String, Object> attrs) {
        addToMap(attrs, A_zimbraAllowAnyFromAddress, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraAttachmentsBlocked, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraContactMaxNumEntries, "0");

        addToMap(attrs, A_zimbraFeatureAdvancedSearchEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureBriefcasesEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureCalendarEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureChangePasswordEnabled, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraFeatureContactsEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureConversationsEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureFiltersEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureFlaggingEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureGalAutoCompleteEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureGalSyncEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureGalEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureGroupCalendarEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureHtmlComposeEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureIMEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureIdentitiesEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureInitialSearchPreferenceEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureInstantNotify, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureMailEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureMailForwardingEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureMailPriorityEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureNewMailNotificationEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureNotebookEnabled, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraFeatureOutOfOfficeReplyEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeaturePop3DataSourceEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeaturePortalEnabled, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraFeatureSavedSearchesEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureSharingEnabled, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraFeatureSkinChangeEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureTaggingEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureTasksEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureViewInHtmlEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraFeatureVoiceEnabled, ProvisioningConstants.FALSE);

        addToMap(attrs, A_zimbraJunkMessagesIndexingEnabled, ProvisioningConstants.TRUE); //always enable junk index

        addToMap(attrs, A_zimbraMailIdleSessionTimeout, "0");
        addToMap(attrs, A_zimbraMailMessageLifetime, "0");
        addToMap(attrs, A_zimbraMailMinPollingInterval, "2m");
        addToMap(attrs, A_zimbraMailQuota, "0");
        addToMap(attrs, A_zimbraMailSpamLifetime, "30d");
        addToMap(attrs, A_zimbraMailTrashLifetime, "30d");

        addToMap(attrs, A_zimbraPasswordMaxLength, "64");
        addToMap(attrs, A_zimbraPasswordMinLength, "6");
        addToMap(attrs, A_zimbraPasswordMinLowerCaseChars, "0");
        addToMap(attrs, A_zimbraPasswordMinNumericChars, "0");
        addToMap(attrs, A_zimbraPasswordMinPunctuationChars, "0");
        addToMap(attrs, A_zimbraPasswordMinUpperCaseChars, "0");

        //addToMap(attrs, A_zimbraPortalName, "velodrome2");

        addToMap(attrs, A_zimbraPrefAutoAddAddressEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraPrefCalendarAlwaysShowMiniCal, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraPrefCalendarApptReminderWarningTime, "5");
        addToMap(attrs, A_zimbraPrefCalendarFirstDayOfWeek, "0");
        addToMap(attrs, A_zimbraPrefCalendarInitialView, "workWeek");
        addToMap(attrs, A_zimbraPrefCalendarNotifyDelegatedChanges, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraPrefCalendarUseQuickAdd, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraPrefComposeFormat, "text");
        addToMap(attrs, A_zimbraPrefComposeInNewWindow, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraPrefContactsInitialView, "list");
        addToMap(attrs, A_zimbraPrefContactsPerPage, "25");
        addToMap(attrs, A_zimbraPrefDedupeMessagesSentToSelf, "dedupeNone");
        addToMap(attrs, A_zimbraPrefForwardIncludeOriginalText, "includeBody");
        addToMap(attrs, A_zimbraPrefForwardReplyInOriginalFormat, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraPrefForwardReplyPrefixChar, ">");
        addToMap(attrs, A_zimbraPrefGalAutoCompleteEnabled, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraPrefGroupMailBy, "conversation");
        addToMap(attrs, A_zimbraPrefHtmlEditorDefaultFontColor, "#000000");
        addToMap(attrs, A_zimbraPrefHtmlEditorDefaultFontFamily, "Arial");
        addToMap(attrs, A_zimbraPrefHtmlEditorDefaultFontSize, "10pt");
        addToMap(attrs, A_zimbraPrefIMAutoLogin, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraPrefImapSearchFoldersEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraPrefInboxUnreadLifetime, "0");
        addToMap(attrs, A_zimbraPrefIncludeSpamInSearch, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraPrefIncludeTrashInSearch, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraPrefMailInitialSearch, "in:inbox");
        addToMap(attrs, A_zimbraPrefMailItemsPerPage, "50");
        addToMap(attrs, A_zimbraPrefMailPollingInterval, OfflineLC.zdesktop_client_poll_interval.value());
        addToMap(attrs, A_zimbraPrefMailSignatureEnabled, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraPrefMailSignatureStyle, "outlook");
        addToMap(attrs, A_zimbraPrefMessageViewHtmlPreferred, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraPrefReadingPaneEnabled, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraPrefReadingPaneLocation, "right");
        addToMap(attrs, A_zimbraPrefReplyIncludeOriginalText, "includeBody");
        addToMap(attrs, A_zimbraPrefSaveToSent, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraPrefSentLifetime, "0");
        addToMap(attrs, A_zimbraPrefSentMailFolder, "sent");
        addToMap(attrs, A_zimbraPrefShowFragments, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraPrefShowSearchString, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraPrefUseKeyboardShortcuts, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraPrefUseRfc2231, ProvisioningConstants.FALSE);
        addToMap(attrs, A_zimbraPrefUseTimeZoneListInCalendar, ProvisioningConstants.FALSE);

        String[] skins = mLocalConfig.getMultiAttr(Provisioning.A_zimbraInstalledSkin);
        attrs.put(A_zimbraPrefSkin, skins == null || skins.length == 0 ? "yahoo" : skins[0]);

        attrs.put(A_zimbraPrefClientType, "advanced");
        attrs.put(A_zimbraFeatureSharingEnabled, ProvisioningConstants.FALSE);

        addToMap(attrs, A_zimbraIsAdminAccount, ProvisioningConstants.TRUE);
        addToMap(attrs, A_zimbraIsDomainAdminAccount, ProvisioningConstants.TRUE);
    }

    public static String getSanitizedValue(String key, String value) throws ServiceException {
        if (value == null) {
            return null;
        }
        if (key.equalsIgnoreCase(A_offlineRemotePassword)) {
            return encryptData(value);
        }
        return value;
    }

    public static void addToMap(Map<String,Object> attrs, String key, String value) {
        if (value != null && key.equalsIgnoreCase(A_offlineRemotePassword)) {
            try {
                value = decryptData(value);
            } catch (ServiceException x) {
                OfflineLog.offline.warn("Can't decrypt remote password");
            }
        }

        Object existing = attrs.get(key);
        if (existing == null) {
            attrs.put(key, value);
        } else if (existing instanceof String) {
            attrs.put(key, new String[] { (String) existing, value } );
        } else {
            String[] before = (String[]) existing, after = new String[before.length+1];
            System.arraycopy(before, 0, after, 0, before.length);
            after[after.length-1] = value;
            attrs.put(key, after);
        }
    }

    @Override
    public void deleteAccount(String zimbraId) throws ServiceException {
        Account localAccount = getLocalAccount();

        try {
            Folder fldr;
            Mailbox mbox = OfflineMailboxManager.getInstance().getMailboxByAccount(localAccount);
            fldr = mbox.getFolderByName(null, DesktopMailbox.ID_FOLDER_NOTIFICATIONS, zimbraId);
            mbox.delete(null, fldr.getId(), MailItem.Type.MOUNTPOINT);
        } catch (Exception e) {
        }

        DbOfflineDirectory.deleteGranterByGrantee(zimbraId);
        mGranterCache.clear();
        deleteOfflineAccount(zimbraId);
        this.cachedAccountIds.remove(zimbraId);
        //persists new order after updating cache.
        persistAccountsOrder();
        if (localAccount.isFeatureNotebookEnabled() && getAllZcsAccounts().size() == 0)
            localAccount.setFeatureNotebookEnabled(false);
    }

    private synchronized void deleteOfflineAccount(String zimbraId) throws ServiceException {
        DbOfflineDirectory.deleteDirectoryEntry(EntryType.ACCOUNT, zimbraId);
        Account acct = mAccountCache.getById(zimbraId);

        if (acct != null) {
            mAccountCache.remove(acct);
        }
    }

    @Override
    public synchronized void renameAccount(String zimbraId, String newName) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("renameAccount");
    }

    @Override
    public Account get(AccountBy keyType, String key) throws ServiceException {
        return get(false, keyType, key);
    }

    private synchronized Account get(boolean includeSyncStatus, AccountBy keyType, String key) throws ServiceException {
        Account acct = null;
        Map<String,Object> attrs = null;
        DbOfflineDirectory.GranterEntry granter = null;
        if (keyType == AccountBy.id) {
            if ((acct = mAccountCache.getById(key)) == null) {
                if (zimbraAdminAccount != null && key.equals(zimbraAdminAccount.getId())) {
                    acct = zimbraAdminAccount;
                } else {
                    attrs = DbOfflineDirectory.readDirectoryEntry(EntryType.ACCOUNT, A_zimbraId, key);

                    if (attrs == null && (acct = mGranterCache.getById(key)) == null)
                        granter = lookupGranter("id", key, null);
                }
            }
        } else if (keyType == AccountBy.name) {
            if (key.equals(LOCAL_ACCOUNT_NAME))
                return getLocalAccount();
            if ((acct = mAccountCache.getByName(key)) == null) {
                attrs = DbOfflineDirectory.readDirectoryEntry(EntryType.ACCOUNT, A_offlineDn, key);

                if (attrs == null && (acct = mGranterCache.getByName(key)) == null)
                    granter = lookupGranter("name", key, null);
            }
        } else if (keyType == AccountBy.adminName) {
            if ((acct = mAccountCache.getByName(key)) == null && key.equals(LC.zimbra_ldap_user.value()) && (acct = zimbraAdminAccount) == null) {
                attrs = new HashMap<String,Object>(7);
                attrs.put(A_mail, key);
                attrs.put(A_cn, key);
                attrs.put(A_sn, key);
                attrs.put(A_zimbraId, UUID.randomUUID().toString());
                attrs.put(A_zimbraAccountStatus, ACCOUNT_STATUS_ACTIVE);
                attrs.put(A_offlineRemotePassword, LC.zimbra_ldap_password.value());
                attrs.put(A_zimbraIsAdminAccount, ProvisioningConstants.TRUE);
            }
        }

        if (granter != null)
            acct = makeGranter(granter);

        if (acct != null) {
            // don't copy over attrs from OfflineCos, so that account won't cache stale values of COS attrs.
            attrs = acct.getAttrs(false);
        }

        String name = null;
        if (attrs != null)
            name = (String)attrs.get(A_mail);

        if (name == null) { // either attrs == null or A_mail attr is missing...
            if (acct != null && keyType != AccountBy.adminName) // in case account entry is somehow corrupted in DB, delete it
                DbOfflineDirectory.deleteDirectoryEntry(EntryType.ACCOUNT, acct.getId());
            return null;
        }

        if (includeSyncStatus) {
            //There are attributes we don't persist into DB.  This is where we add them:
            if (acct != null) {
                attrs.put(OfflineConstants.A_offlineSyncStatus, OfflineSyncManager.getInstance().getSyncStatus(acct).toString());
                String statusErrorCode = OfflineSyncManager.getInstance().getErrorCode(acct);
                if (statusErrorCode != null)
                    attrs.put(OfflineConstants.A_offlineSyncStatusErrorCode, statusErrorCode);
                String statusErrorMsg = OfflineSyncManager.getInstance().getErrorMsg(acct);
                if (statusErrorMsg != null)
                    attrs.put(OfflineConstants.A_offlineSyncStatusErrorMsg, statusErrorMsg);
                String statusException = OfflineSyncManager.getInstance().getException(acct);
                if (statusException != null)
                    attrs.put(OfflineConstants.A_offlineSyncStatusException, statusException);
            } else {
                attrs.put(OfflineConstants.A_offlineSyncStatus, OfflineConstants.SyncStatus.unknown.toString());
            }
        }

        if (acct != null) {
            acct.setAttrs(attrs);
        } else {
            acct = new OfflineAccount(name, (String) attrs.get(A_zimbraId),
                attrs, mDefaultCos.getAccountDefaults(),
                keyType == AccountBy.id && key.equals(LOCAL_ACCOUNT_ID) ? null :
                getLocalAccount(), this);
            if (keyType == AccountBy.adminName && key.equals(LC.zimbra_ldap_user.value())) {
                zimbraAdminAccount = acct;
            }
            mAccountCache.put(acct);
        }

        String granteeId = (String)attrs.get(A_offlineMountpointProxyAccountId);
        if (granteeId != null) { // is offline mountpoint account..
            loadRemoteSyncServer((OfflineAccount)acct, granteeId);
        }

        return acct;
    }

    public List<String> getAllAccountIds() throws ServiceException {
        if (this.cachedAccountIds.isEmpty()) {
            synchronized (this) {
                if (this.cachedAccountIds.isEmpty()) {
                    this.loadAccountIdsInOrder();
                }
            }
        }
        return Collections.unmodifiableList(this.cachedAccountIds);
    }

    public List<Account> getAllAccounts() throws ServiceException {
        return getAllAccounts(false);
    }

    private synchronized List<Account> getAllAccounts(boolean includeSyncStatus) throws ServiceException {
        List<Account> accts = new ArrayList<Account>();

        for (String zimbraId : getAllAccountIds()) {
            Account acct = get(includeSyncStatus, AccountBy.id, zimbraId);
            if (acct != null && !isLocalAccount(acct) && !isGalAccount(acct) &&
                !isMountpointAccount(acct))
                accts.add(acct);
        }
        return accts;
    }

    @Override
    public List<Account> getAllAdminAccounts() throws ServiceException {
        List<Account> admins = new ArrayList<Account>(1);
        Account acct = get(AccountBy.adminName, LC.zimbra_ldap_user.value());
        if (acct != null)
            admins.add(acct);
        return admins;
    }

    boolean hasDirtyAccounts() {
        return mHasDirtyAccounts;
    }

    synchronized List<Account> listDirtyAccounts() throws ServiceException {
        List<Account> dirty = new ArrayList<Account>();
        for (String zimbraId : DbOfflineDirectory.listAllDirtyEntries(EntryType.ACCOUNT)) {
            if (!zimbraId.equals(LOCAL_ACCOUNT_ID)) {
                Account acct = get(AccountBy.id, zimbraId);
                if (acct != null && isZcsAccount(acct))
                    dirty.add(acct);
            } else {
                try {
                    //if sync is setup for zimlet properties and local is dirty then the sync'd account is effectively dirty too
                    String zimletAcctId = getZimletSyncAccountId();
                    if (zimletAcctId != null && zimletAcctId.length() > 0) {
                        Account zimletAcct = get(AccountBy.id, zimletAcctId);
                        if (zimletAcct != null) {
                            dirty.add(zimletAcct);
                        } else {
                            OfflineLog.offline.debug("zimlet sync account id ["+zimletAcctId+"] does not exist");
                        }
                    }
                } catch (ServiceException se) {
                    //account id might be incorrect; this shouldn't cause all of dir sync to bomb
                    OfflineLog.offline.error("Unable to find/mark account for zimlet sync",se);
                }
            }
        }
        mHasDirtyAccounts = !dirty.isEmpty();
        return dirty;
    }

    synchronized void markAccountClean(Account acct) throws ServiceException {
        DbOfflineDirectory.markEntryClean(EntryType.ACCOUNT, acct);
        reload(acct);
    }

    @Override
    public synchronized void setCOS(Account acct, Cos cos) throws ServiceException {
        if (cos != mDefaultCos)
            throw OfflineServiceException.UNSUPPORTED("setCOS");
    }

    @Override
    public synchronized void modifyAccountStatus(Account acct, String newStatus) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("modifyAccountStatus");
    }

    @Override
    public void authAccount(Account acct, String password, AuthContext.Protocol proto) throws ServiceException {
        String instkey = OfflineLC.zdesktop_installation_key.value();
        if (instkey == null || instkey.startsWith("@") || instkey.equals(password)) {
            ZimbraLog.security.info(ZimbraLog.encodeAttrs(
                new String[] {"cmd", "Auth", "account", acct.getName(), "protocol", proto.toString()}));
        } else {
            ZimbraLog.security.warn(ZimbraLog.encodeAttrs(
                new String[] {"cmd", "Auth","account", acct.getName(), "protocol", proto.toString(), "error", "invalid password"}));
            throw AccountServiceException.INVALID_PASSWORD(password);
        }
    }

    @Override
    public void authAccount(Account acct, String password, AuthContext.Protocol proto, Map<String, Object> context) throws ServiceException {
    authAccount(acct, password, proto);
    }

    @Override
    public synchronized void preAuthAccount(Account acct, String accountName, String accountBy, long timestamp, long expires, String preAuth, Map<String, Object> authCtxt) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("preAuthAccount");
    }

    @Override
    public void ssoAuthAccount(Account acct, AuthContext.Protocol proto, Map<String, Object> authCtxt) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("ssoAuthAccount");
    }

    @Override
    public synchronized void changePassword(Account acct, String currentPassword, String newPassword) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized SetPasswordResult setPassword(Account acct, String newPassword) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void checkPasswordStrength(Account acct, String password) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void addAlias(Account acct, String alias) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("addAlias");
    }

    @Override
    public synchronized void removeAlias(Account acct, String alias) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("removeAlias");
    }

    @Override
    public synchronized Domain createDomain(String name, Map<String, Object> attrs) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("createDomain");
    }

    @Override
    public synchronized Domain get(Key.DomainBy keyType, String key) {
        return null;
    }

    @Override
    public synchronized List<Domain> getAllDomains() {
        return null;
    }

    @Override
    public synchronized void deleteDomain(String zimbraId) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("deleteDomain");
    }

    @Override
    public synchronized Cos createCos(String name, Map<String, Object> attrs) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("createCos");
    }

    @Override
    public synchronized Cos copyCos(String srcCosId, String destCosName) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("copyCos");
    }

    @Override
    public synchronized void renameCos(String zimbraId, String newName) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("renameCos");
    }

    @Override
    public synchronized Cos get(Key.CosBy keyType, String key) throws ServiceException {
        if (keyType == Key.CosBy.id)
            return mDefaultCos.getId().equalsIgnoreCase(key) ? mDefaultCos : null;
        else if (keyType == Key.CosBy.name)
            return mDefaultCos.getName().equalsIgnoreCase(key) ? mDefaultCos : null;
        else
            throw ServiceException.FAILURE("unsupported CosBy value: " + keyType, null);
    }

    @Override
    public synchronized List<Cos> getAllCos() {
        List<Cos> coses = new ArrayList<Cos>(1);
        coses.add(mDefaultCos);
        return coses;
    }

    @Override
    public synchronized void deleteCos(String zimbraId) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("deleteCos");
    }

    @Override
    public Server getLocalServer() {
        return mLocalServer;
    }

    @Override
    public synchronized Server createServer(String name, Map<String, Object> attrs) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("createServer");
    }

    @Override
    public synchronized Server get(Key.ServerBy keyType, String key) throws ServiceException {
        if (key.toLowerCase().startsWith(OfflineConstants.SYNC_SERVER_PREFIX)) {
            synchronized(mSyncServerCache) {
                return mSyncServerCache.get(key.toLowerCase());
            }
        } else if (keyType == Key.ServerBy.id)
            return mLocalServer.getId().equalsIgnoreCase(key) ? mLocalServer : null;
        else if (keyType == Key.ServerBy.name)
            return mLocalServer.getName().equalsIgnoreCase(key) ? mLocalServer : null;
        else if (keyType == Key.ServerBy.serviceHostname)
            return mLocalServer.getAttr(A_zimbraServiceHostname, "localhost").equalsIgnoreCase(key) ? mLocalServer : null;
        else
            throw ServiceException.FAILURE("unsupported ServerBy value: " + keyType, null);
    }

    @Override
    public synchronized List<Server> getAllServers() {
        List<Server> servers = new ArrayList<Server>(1);
        servers.add(mLocalServer);
        return servers;
    }

    @Override
    public synchronized List<Server> getAllServers(String service) {
        if (service == null || service.equalsIgnoreCase("mailbox"))
            return getAllServers();
        return Collections.emptyList();
    }

    @Override
    public synchronized void deleteServer(String zimbraId) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("deleteServer");
    }

    @Override
    public synchronized DistributionList createDistributionList(String listAddress, Map<String, Object> listAttrs) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("createDistributionList");
    }

    @Override
    public synchronized DistributionList get(Key.DistributionListBy keyType, String key) throws ServiceException {
        return null;
    }

    @Override
    public synchronized void deleteDistributionList(String zimbraId) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("deleteDistributionList");
    }

    @Override
    public synchronized void addAlias(DistributionList dl, String alias) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("addAlias");
    }

    @Override
    public synchronized void removeAlias(DistributionList dl, String alias) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("removeAlias");
    }

    @Override
    public synchronized void renameDistributionList(String zimbraId, String newName) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("renameDistributionList");
    }

    @Override
    public synchronized Zimlet getZimlet(String name) {
        return mZimlets.get(name.toLowerCase());
    }

    @Override
    public synchronized List<Zimlet> listAllZimlets() {
        // FIXME: not thread-safe wrt zimlet deletes/creates
        return new ArrayList<Zimlet>(mZimlets.values());
    }

    @Override
    public synchronized Zimlet createZimlet(String name, Map<String, Object> attrs) throws ServiceException {
        name = name.toLowerCase();

        CallbackContext callbackContext = new CallbackContext(CallbackContext.Op.CREATE);
        AttributeManager.getInstance().preModify(attrs, null, callbackContext, true);
        if (!(attrs.get(A_zimbraId) instanceof String))
            attrs.put(A_zimbraId, UUID.randomUUID().toString());
        attrs.put(A_cn, name);
        attrs.put(A_objectClass, "zimbraZimletEntry");
        attrs.put(A_zimbraZimletEnabled, ProvisioningConstants.FALSE);
        attrs.put(A_zimbraZimletIndexingEnabled, attrs.containsKey(A_zimbraZimletKeyword) ? ProvisioningConstants.TRUE : ProvisioningConstants.FALSE);

        DbOfflineDirectory.createDirectoryEntry(EntryType.ZIMLET, name, attrs, false);
        Zimlet zimlet = new OfflineZimlet(name, (String) attrs.get(A_zimbraId), attrs, this);
        mZimlets.put(name, zimlet);
        AttributeManager.getInstance().postModify(attrs, zimlet, callbackContext);
        return zimlet;
    }

    @Override
    public synchronized void deleteZimlet(String name) throws ServiceException {
        name = name.toLowerCase();

        Zimlet zimlet = mZimlets.get(name);
        if (zimlet == null)
            return;
        DbOfflineDirectory.deleteDirectoryEntry(EntryType.ZIMLET, zimlet.getId());
        mZimlets.remove(name);
    }

    @Override
    public synchronized CalendarResource createCalendarResource(String emailAddress, String password, Map<String, Object> attrs) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("createCalendarResource");
    }

    @Override
    public synchronized void deleteCalendarResource(String zimbraId) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("deleteCalendarResource");
    }

    @Override
    public synchronized void renameCalendarResource(String zimbraId, String newName) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("renamerCalendarResource");
    }

    @Override
    public synchronized CalendarResource get(Key.CalendarResourceBy keyType, String key) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<Account> getAllAccounts(Domain d) throws ServiceException {
        if (d == null || d.getAttr(A_zimbraDomainName) == null) {
            return getAllAccounts(true);
        }
        String domainName = d.getAttr(A_zimbraDomainName);
        if (d == null || domainName == null) {
            return getAllAccounts(true);
        }
        return getAllAccounts(domainName);
    }

    private synchronized List<Account> getAllAccounts(String domain) throws ServiceException {
        List<Account> accts = new ArrayList<Account>();
        List<String> ids = DbOfflineDirectory.searchDirectoryEntries(EntryType.ACCOUNT, A_offlineDn, "%@" + domain);
        for (String id : ids) {
            Account acct = get(AccountBy.id, id);
            if (acct != null)
                accts.add(acct);
        }
        return accts;
    }

    public synchronized List<Account> getAllGalAccounts(String domain) throws ServiceException {
        List<Account> accts = new ArrayList<Account>();
        List<String> ids = DbOfflineDirectory.searchDirectoryEntries(EntryType.ACCOUNT, A_mail, "%@" + domain + OfflineConstants.GAL_ACCOUNT_SUFFIX);
        for (String id : ids) {
            Account acct = get(AccountBy.id, id);
            if (acct != null)
                accts.add(acct);
        }
        return accts;
    }

    public synchronized Set<String> getAllAccountsByDomain(String domain) throws ServiceException {
        List<Account> accounts = getAllAccounts(domain);
        Set<String> result = new HashSet<String>();
        for (Account account : accounts) {
            result.add(account.getId());
        }
        return result;
    }

    @Override
    public synchronized void getAllAccounts(Domain d, Visitor visitor) throws ServiceException {
        for (Account acct : getAllAccounts(d))
            visitor.visit(acct);
    }

    @Override
    public synchronized void getAllAccounts(Domain d, Server s, NamedEntry.Visitor visitor) throws ServiceException {
        if (s == null || s.getName().equalsIgnoreCase(mLocalServer.getName()))
            getAllAccounts(d, visitor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized List getAllCalendarResources(Domain d) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void getAllCalendarResources(Domain d, Server s, Visitor visitor) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void getAllCalendarResources(Domain d, Visitor visitor) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized List getAllDistributionLists(Domain d) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("getAllDistributionLists");
    }

    @Override
    public synchronized void addMembers(DistributionList list, String[] members) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("addMembers");
    }

    @Override
    public synchronized void removeMembers(DistributionList list, String[] member) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("removeMembers");
    }

    @Override
    public synchronized Identity createIdentity(Account account, String name, Map<String, Object> attrs) throws ServiceException {
        return createIdentity(account, name, attrs, isZcsAccount(account));
    }

    @Override
    public synchronized Identity restoreIdentity(Account account, String name, Map<String, Object> attrs) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("restoreIdentity");
    }

    synchronized Identity createIdentity(Account account, String name, Map<String, Object> attrs, boolean markChanged) throws ServiceException {
        if (name.equalsIgnoreCase(ProvisioningConstants.DEFAULT_IDENTITY_NAME))
            throw AccountServiceException.IDENTITY_EXISTS(name);

        List<Identity> existing = getAllIdentities(account);
        if (existing.size() >= account.getLongAttr(A_zimbraIdentityMaxNumEntries, 20))
            throw AccountServiceException.TOO_MANY_IDENTITIES();

        attrs.remove(A_offlineModifiedAttrs);

        if (!(attrs.get(A_zimbraPrefIdentityId) instanceof String))
            attrs.put(A_zimbraPrefIdentityId, UUID.randomUUID().toString());
        String identId = (String) attrs.get(A_zimbraPrefIdentityId);
        attrs.put(A_zimbraPrefIdentityName, name);
        attrs.put(A_objectClass, "zimbraIdentity");
        if (markChanged)
            attrs.put(A_offlineModifiedAttrs, A_offlineDn);

        Map<String,Object> immutable = new HashMap<String, Object>();
        for (String attr : AttributeManager.getInstance().getImmutableAttrs())
            if (attrs.containsKey(attr))
                immutable.put(attr, attrs.remove(attr));

        CallbackContext callbackContext = new CallbackContext(CallbackContext.Op.CREATE);
        AttributeManager.getInstance().preModify(attrs, null, callbackContext, true);

        attrs.putAll(immutable);

        DbOfflineDirectory.createDirectoryLeaf(EntryType.IDENTITY, account, name, identId, attrs, markChanged);
        Identity identity = new OfflineIdentity(account, name, attrs, this);
        mHasDirtyAccounts |= markChanged;

        AttributeManager.getInstance().postModify(attrs, identity, callbackContext);
        return identity;
    }

    @Override
    public synchronized void deleteIdentity(Account account, String name) throws ServiceException {
        deleteIdentity(account, name, isZcsAccount(account));
    }

    synchronized void deleteIdentity(Account account, String name, boolean markChanged) throws ServiceException {
        if (name.equalsIgnoreCase(ProvisioningConstants.DEFAULT_IDENTITY_NAME))
            throw ServiceException.INVALID_REQUEST("can't delete default identity", null);

        Identity ident = get(account, Key.IdentityBy.name, name);
        if (ident == null)
            return;

        DbOfflineDirectory.deleteDirectoryLeaf(EntryType.IDENTITY, account, ident.getId(), markChanged);
        reload(account);
        mHasDirtyAccounts |= markChanged;
    }

    @Override
    public synchronized List<Identity> getAllIdentities(Account account) throws ServiceException {
        List<String> names = DbOfflineDirectory.listAllDirectoryLeaves(EntryType.IDENTITY, account);

        List<Identity> identities = new ArrayList<Identity>(names.size() + 1);
        identities.add(getDefaultIdentity(account));
        for (String name : names)
            identities.add(get(account, Key.IdentityBy.name, name));
        return identities;
    }

    @Override
    public synchronized void modifyIdentity(Account account, String name, Map<String, Object> attrs) throws ServiceException {
        modifyIdentity(account, name, attrs, isZcsAccount(account));
    }

    synchronized void modifyIdentity(Account account, String name, Map<String, Object> attrs, boolean markChanged) throws ServiceException {
        if (name.equalsIgnoreCase(ProvisioningConstants.DEFAULT_IDENTITY_NAME)) {
            modifyAttrs(account, attrs, false, true, markChanged);
            return;
        }

        Identity identity = get(account, Key.IdentityBy.name, name);
        if (identity == null)
            throw AccountServiceException.NO_SUCH_IDENTITY(name);

        if (markChanged) {
            attrs.remove(A_offlineModifiedAttrs);

            List<String> modattrs = new ArrayList<String>();
            for (String attr : attrs.keySet()) {
                if (attr.startsWith("-") || attr.startsWith("+"))
                    attr = attr.substring(1);
                if (!modattrs.contains(attr) && !attr.toLowerCase().startsWith("offline") && !OfflineProvisioning.sOfflineAttributes.contains(attr))
                    modattrs.add(attr);
            }
            if (!modattrs.isEmpty())
                attrs.put('+' + A_offlineModifiedAttrs, modattrs.toArray(new String[modattrs.size()]));
        }

        String newName = (String) attrs.get(A_zimbraPrefIdentityName);
        if (newName == null)
            newName = (String) attrs.get('+' + A_zimbraPrefIdentityName);

        CallbackContext callbackContext = new CallbackContext(CallbackContext.Op.MODIFY);
        AttributeManager.getInstance().preModify(attrs, identity, callbackContext, true, true);

        DbOfflineDirectory.modifyDirectoryLeaf(EntryType.IDENTITY, account, A_offlineDn, name, attrs, markChanged, newName);
        reload(identity);
        mHasDirtyAccounts |= markChanged;

        AttributeManager.getInstance().postModify(attrs, identity, callbackContext, true);
    }

    @Override
    public synchronized Identity get(Account account, Key.IdentityBy keyType, String key) throws ServiceException {
        if (key == null) return null;
        Map<String,Object> attrs = null;
        if (keyType == Key.IdentityBy.name) {
            if (key.equalsIgnoreCase(ProvisioningConstants.DEFAULT_IDENTITY_NAME))
                return getDefaultIdentity(account);
            attrs = DbOfflineDirectory.readDirectoryLeaf(EntryType.IDENTITY, account, A_offlineDn, key);
        } else if (keyType == Key.IdentityBy.id) {
            if (key.equalsIgnoreCase(account.getId()))
                return getDefaultIdentity(account);
            attrs = DbOfflineDirectory.readDirectoryLeaf(EntryType.IDENTITY, account, A_zimbraId, key);
        }
        if (attrs == null && keyType == Key.IdentityBy.id) {
            //HACK: try DataSource as well
            DataSource ds = get(account, Key.DataSourceBy.id, key);
            if (ds != null) {
                attrs = ds.getAttrs();
                attrs.put(A_zimbraPrefIdentityId, ds.getId());
                attrs.put(A_zimbraPrefIdentityName, ds.getName());
            }
        }
        if (attrs == null)
            return null;

        return new OfflineIdentity(account, (String) attrs.get(A_zimbraPrefIdentityName), attrs, this);
    }

    @Override
    public synchronized Signature createSignature(Account account, String signatureName, Map<String, Object> attrs) throws ServiceException {
        return createSignature(account, signatureName, attrs, isZcsAccount(account));
    }

    @Override
    public synchronized Signature restoreSignature(Account account, String signatureName, Map<String, Object> attrs) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("restoreSignature");
    }

    synchronized Signature createSignature(Account account, String signatureName, Map<String, Object> attrs, boolean markChanged) throws ServiceException {
        boolean setAsDefault = false;
        List<Signature> existing = getAllSignatures(account);
        int numSigs = existing.size();
        if (numSigs >= account.getLongAttr(A_zimbraSignatureMaxNumEntries, 20))
            throw AccountServiceException.TOO_MANY_SIGNATURES();
        else if (numSigs == 0)
            setAsDefault = true;

        String signatureId = (String)attrs.get(Provisioning.A_zimbraSignatureId);
        if (signatureId == null) {
            signatureId = UUID.randomUUID().toString();
            attrs.put(Provisioning.A_zimbraSignatureId, signatureId);
        }
        attrs.put(Provisioning.A_zimbraSignatureName, signatureName);
        attrs.put(A_objectClass, "zimbraSignature");

        if (markChanged)
            attrs.put(A_offlineModifiedAttrs, A_offlineDn);

        Map<String,Object> immutable = new HashMap<String, Object>();
        for (String attr : AttributeManager.getInstance().getImmutableAttrs())
            if (attrs.containsKey(attr))
                immutable.put(attr, attrs.remove(attr));

        CallbackContext callbackContext = new CallbackContext(CallbackContext.Op.CREATE);
        AttributeManager.getInstance().preModify(attrs, null, callbackContext, true);

        attrs.putAll(immutable);

        DbOfflineDirectory.createDirectoryLeaf(EntryType.SIGNATURE, account, signatureName, signatureId, attrs, markChanged);
        Signature signature = get(account, Key.SignatureBy.id, signatureId);
        mHasDirtyAccounts |= markChanged;

        AttributeManager.getInstance().postModify(attrs, signature, callbackContext);

        if (setAsDefault && markChanged) {
            setDefaultSignature(account, signatureId);
        }

        return signature;
    }

    private void setDefaultSignature(Account acct, String signatureId) throws ServiceException {
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zimbraPrefDefaultSignatureId, signatureId);
        modifyAttrs(acct, attrs, false, true, false); //always let server set default
    }

    private String getDefaultSignature(Account acct) {
        return acct.getAttr(Provisioning.A_zimbraPrefDefaultSignatureId);
    }

    private void removeDefaultSignature(Account acct) throws ServiceException {
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put('-' + Provisioning.A_zimbraPrefDefaultSignatureId, null);
        modifyAttrs(acct, attrs, false, true, false); //always let server set default
    }

    @Override
    public synchronized void modifySignature(Account account, String signatureId, Map<String, Object> attrs) throws ServiceException {
        modifySignature(account, signatureId, attrs, isZcsAccount(account));
    }

    synchronized void modifySignature(Account account, String signatureId, Map<String, Object> attrs, boolean markChanged) throws ServiceException {
        Signature signature = get(account, Key.SignatureBy.id, signatureId);
        if (signature == null)
            throw AccountServiceException.NO_SUCH_SIGNATURE(signatureId);

        if (markChanged) {
            attrs.remove(A_offlineModifiedAttrs);

            List<String> modattrs = new ArrayList<String>();
            for (String attr : attrs.keySet()) {
                if (attr.startsWith("-") || attr.startsWith("+"))
                    attr = attr.substring(1);
                if (!modattrs.contains(attr) && !attr.toLowerCase().startsWith("offline") && !OfflineProvisioning.sOfflineAttributes.contains(attr))
                    modattrs.add(attr);
            }
            if (!modattrs.isEmpty())
                attrs.put('+' + A_offlineModifiedAttrs, modattrs.toArray(new String[modattrs.size()]));
        }

        String newName = (String) attrs.get(A_zimbraSignatureName);
        if (newName!= null) {
            if (newName.equals(signature.getName())) {
                newName = null; //no need to update
            } else if (newName.length() == 0) {
                throw ServiceException.INVALID_REQUEST("empty signature name is not allowed", null); //can't be empty
            }
        }

        CallbackContext callbackContext = new CallbackContext(CallbackContext.Op.MODIFY);
        AttributeManager.getInstance().preModify(attrs, signature, callbackContext, true);

        DbOfflineDirectory.modifyDirectoryLeaf(EntryType.SIGNATURE, account, Provisioning.A_zimbraId, signatureId, attrs, markChanged, newName);
        reload(signature);
        mHasDirtyAccounts |= markChanged;

        AttributeManager.getInstance().postModify(attrs, signature, callbackContext, true);
    }

    @Override
    public synchronized void deleteSignature(Account account, String signatureId) throws ServiceException {
        deleteSignature(account, signatureId, isZcsAccount(account));
    }

    synchronized void deleteSignature(Account account, String signatureId, boolean markChanged) throws ServiceException {
        Signature signature = get(account, Key.SignatureBy.id, signatureId);
        if (signature == null) return;

        DbOfflineDirectory.deleteDirectoryLeaf(EntryType.SIGNATURE, account, signatureId, markChanged);
        reload(account);
        mHasDirtyAccounts |= markChanged;

        if (signatureId.equals(getDefaultSignature(account))) {
            List<String> names = DbOfflineDirectory.listAllDirectoryLeaves(EntryType.SIGNATURE, account);
            if (markChanged) {
                if (names.size() > 0) {
                    setDefaultSignature(account, names.get(0)); //just randomly set to whatever comes next
                } else {
                    removeDefaultSignature(account);
                }
            }
        }
    }

    @Override
    public synchronized List<Signature> getAllSignatures(Account account) throws ServiceException {
        List<String> names = DbOfflineDirectory.listAllDirectoryLeaves(EntryType.SIGNATURE, account);
        List<Signature> signatures = new ArrayList<Signature>(names.size());
        for (String name : names)
            signatures.add(get(account, Key.SignatureBy.name, name));
        return signatures;
    }

    @Override
    public synchronized Signature get(Account account, Key.SignatureBy keyType, String key) throws ServiceException {
        if (key == null) return null;
        Map<String,Object> attrs = null;
        if (keyType == Key.SignatureBy.name) {
            attrs = DbOfflineDirectory.readDirectoryLeaf(EntryType.SIGNATURE, account, A_offlineDn, key);
        } else if (keyType == Key.SignatureBy.id) {
            attrs = DbOfflineDirectory.readDirectoryLeaf(EntryType.SIGNATURE, account, A_zimbraId, key);
        }
        if (attrs == null)
            return null;

        return new OfflineSignature(account, attrs, this);
    }

    private Map<String, List<DataSource>> cachedDataSources = new HashMap<String, List<DataSource>>();

    @Override
    public synchronized DataSource createDataSource(Account account, DataSourceType type, String name, Map<String, Object> attrs) throws ServiceException {
        return createDataSource(account, type, name, attrs, false, isZcsAccount(account));
    }

    @Override
    public synchronized DataSource createDataSource(Account account, DataSourceType type, String name, Map<String, Object> attrs, boolean passwdAlreadyEncrypted) throws ServiceException {
        return createDataSource(account, type, name, attrs, passwdAlreadyEncrypted, isZcsAccount(account));
    }

    @Override
    public synchronized DataSource restoreDataSource(Account account, DataSourceType type, String name, Map<String, Object> attrs) throws ServiceException {
        throw OfflineServiceException.UNSUPPORTED("restoreDataSource");
    }

    synchronized DataSource createDataSource(Account account, DataSourceType type, String name, Map<String, Object> attrs, boolean passwdAlreadyEncrypted, boolean markChanged)
    throws ServiceException {
        List<DataSource> existing = getAllDataSources(account);
        if (existing.size() >= account.getLongAttr(A_zimbraDataSourceMaxNumEntries, 20))
            throw AccountServiceException.TOO_MANY_DATA_SOURCES();

        attrs.remove(A_offlineModifiedAttrs);

        if (!(attrs.get(A_zimbraDataSourceId) instanceof String))
            attrs.put(A_zimbraDataSourceId, UUID.randomUUID().toString());
        String dsid = (String) attrs.get(A_zimbraDataSourceId);
        attrs.put(A_zimbraDataSourceName, name); // must be the same
        attrs.put(A_offlineDataSourceType, type.toString());
        attrs.put(A_objectClass, "zimbraDataSource");
        if (!passwdAlreadyEncrypted && attrs.get(A_zimbraDataSourcePassword) != null)
            attrs.put(A_zimbraDataSourcePassword, DataSource.encryptData(dsid, (String) attrs.get(A_zimbraDataSourcePassword)));
        if (markChanged)
            attrs.put(A_offlineModifiedAttrs, A_offlineDn);

        if (isDataSourceAccount(account))
            attrs.put(A_zimbraDataSourceEnabled, ProvisioningConstants.TRUE);

        //testDataSource(new OfflineDataSource(account, type, name, dsid, attrs));

        Map<String,Object> immutable = new HashMap<String, Object>();
        for (String attr : AttributeManager.getInstance().getImmutableAttrs())
            if (attrs.containsKey(attr))
                immutable.put(attr, attrs.remove(attr));

        CallbackContext callbackContext = new CallbackContext(CallbackContext.Op.CREATE);
        AttributeManager.getInstance().preModify(attrs, null, callbackContext, true);

        attrs.putAll(immutable);

        DbOfflineDirectory.createDirectoryLeaf(EntryType.DATASOURCE, account, name, dsid, attrs, markChanged);
        DataSource ds = new OfflineDataSource(account, type, name, dsid, attrs, this);
        mHasDirtyAccounts |= markChanged;

        AttributeManager.getInstance().postModify(attrs, ds, callbackContext);

        cachedDataSources.remove(account.getId());

        return ds;
    }

    @Override
    public synchronized void deleteDataSource(Account account, String dataSourceId) throws ServiceException {
        deleteDataSource(account, dataSourceId, isZcsAccount(account));
    }

    synchronized void deleteDataSource(Account account, String dataSourceId, boolean markChanged) throws ServiceException {
        DataSource dsrc = get(account, Key.DataSourceBy.id, dataSourceId);
        if (dsrc == null)
            return;
        SyncErrorManager.clearErrors(dsrc);
        DbOfflineDirectory.deleteDirectoryLeaf(EntryType.DATASOURCE, account, dsrc.getId(), markChanged);
        reload(account);
        mHasDirtyAccounts |= markChanged;

        cachedDataSources.remove(account.getId());
        ImapSync.reset(dataSourceId);
    }

    @Override
    public synchronized List<DataSource> getAllDataSources(Account account) throws ServiceException {
        List<DataSource> sources = cachedDataSources.get(account.getId());
        if (sources == null) {
            List<String> names = DbOfflineDirectory.listAllDirectoryLeaves(EntryType.DATASOURCE, account);
            sources = new ArrayList<DataSource>(names.size());
            for (String name : names)
                sources.add(get(account, Key.DataSourceBy.name, name));
            sort(sources);
            cachedDataSources.put(account.getId(), sources);
        }
        for (DataSource ds : sources) {
            ds.getAttrs(false).put(OfflineConstants.A_zimbraDataSourceSyncStatus, OfflineSyncManager.getInstance().getSyncStatus(ds).toString());
            String statusErrorCode = OfflineSyncManager.getInstance().getErrorCode(ds);
            if (statusErrorCode != null)
                ds.getAttrs(false).put(OfflineConstants.A_zimbraDataSourceSyncStatusErrorCode, statusErrorCode);
        }
        return sources;
    }

    private static void sort(List<DataSource> sources) {
        Collections.sort(sources, new Comparator<DataSource>() {
            @Override
            public int compare(DataSource ds1, DataSource ds2) {
                return syncOrder(ds1) - syncOrder(ds2);
            }
        });
    }

    private static int syncOrder(DataSource ds) {
        switch (ds.getType()) {
        case yab:
            return 1;
        case caldav:
            return 2;
        default:
            return 3;
        }
    }

    @Override
    public void modifyDataSource(Account account, String dataSourceId, Map<String, Object> attrs) throws ServiceException {
        modifyDataSource(account, dataSourceId, attrs, isZcsAccount(account));
    }

    void modifyDataSource(Account account, String dataSourceId, Map<String, Object> attrs, boolean markChanged) throws ServiceException {
        DataSource ds = get(account, Key.DataSourceBy.id, dataSourceId);
        if (ds == null)
            throw AccountServiceException.NO_SUCH_DATA_SOURCE(dataSourceId);

        if (markChanged) {
            attrs.remove(A_offlineModifiedAttrs);

            List<String> modattrs = new ArrayList<String>();
            for (String attr : attrs.keySet()) {
                if (attr.startsWith("-") || attr.startsWith("+"))
                    attr = attr.substring(1);
                if (!modattrs.contains(attr) && !attr.toLowerCase().startsWith("offline") && !OfflineProvisioning.sOfflineAttributes.contains(attr))
                    modattrs.add(attr);
            }
            if (!modattrs.isEmpty())
                attrs.put('+' + A_offlineModifiedAttrs, modattrs.toArray(new String[modattrs.size()]));
        }

        String newName = (String) attrs.get(A_zimbraDataSourceName);
        if (newName == null)
            newName = (String) attrs.get('+' + A_zimbraDataSourceName);

        if (attrs.get(A_zimbraDataSourcePassword) instanceof String)
            attrs.put(A_zimbraDataSourcePassword, DataSource.encryptData(dataSourceId, (String) attrs.get(A_zimbraDataSourcePassword)));

        if (attrs.get(A_zimbraDataSourceSmtpAuthPassword) instanceof String)
            attrs.put(A_zimbraDataSourceSmtpAuthPassword, DataSource.encryptData(dataSourceId, (String) attrs.get(A_zimbraDataSourceSmtpAuthPassword)));

        if (isDataSourceAccount(account) && attrs.get(A_zimbraDataSourceHost) != null) {
            String decrypted = null;
            String encrypted = (String)attrs.get(A_zimbraDataSourcePassword);

            if (encrypted == null) {
                encrypted = ds.getAttr(A_zimbraDataSourcePassword);
                attrs.put(A_zimbraDataSourcePassword, encrypted);
                decrypted = ds.getDecryptedPassword();
            } else {
                decrypted = DataSource.decryptData(dataSourceId, encrypted);
            }

            String domain = ds.getAttr(Provisioning.A_zimbraDataSourceDomain);
            if (!"yahoo.com".equals(domain)) {
                String smtpPassword = (String)attrs.get(A_zimbraDataSourceSmtpAuthPassword);

                if (smtpPassword == null) {
                    smtpPassword = ds.getAttr(A_zimbraDataSourceSmtpAuthPassword, null);
                    if (smtpPassword != null)
                        attrs.put(A_zimbraDataSourceSmtpAuthPassword, smtpPassword);
                }
            }

            if (attrs.remove(A_zimbraDataSourceAccountSetup) != null) {
                String sslCertAlias = (String)attrs.remove(
                    OfflineConstants.A_zimbraDataSourceSslCertAlias);
                if (sslCertAlias != null)
                    acceptSSLCertAlias(sslCertAlias);

                if ("yahoo.com".equals(domain)) {
                    // Clear auth token so that it will be regenerated during test...
                    OfflineYAuth.removeToken(ds);
                }
                testDataSource(new OfflineDataSource(
                    account, ds.getType(), ds.getName(), ds.getId(), attrs, this));

                OfflineSyncManager.getInstance().clearErrorCode(ds);

                adjustAccountDisplayName(account, ds, attrs);
            }

            attrs.put(A_zimbraDataSourceEnabled, ProvisioningConstants.TRUE);
        }

        CallbackContext callbackContext = new CallbackContext(CallbackContext.Op.MODIFY);

        synchronized (this) {
            AttributeManager.getInstance().preModify(attrs, ds, callbackContext, true, true);

            DbOfflineDirectory.modifyDirectoryLeaf(EntryType.DATASOURCE, account, A_zimbraId, dataSourceId, attrs, markChanged, newName);
            reload(ds);
            mHasDirtyAccounts |= markChanged;

            AttributeManager.getInstance().postModify(attrs, ds, callbackContext, true);
        }
        if (!isLocalAccount(account) && isDataSourceAccount(account)) {
            setAccountAttribute(account, A_zimbraFeatureCalendarEnabled, ds.getBooleanAttr(A_zimbraDataSourceCalendarSyncEnabled, false) ? ProvisioningConstants.TRUE : ProvisioningConstants.FALSE);
            setAccountAttribute(account, A_zimbraFeatureContactsEnabled, ds.getBooleanAttr(A_zimbraDataSourceContactSyncEnabled, false) ? ProvisioningConstants.TRUE : ProvisioningConstants.FALSE);
        }
        //for yahoo contact sync (address book)
        if (ds.getBooleanAttr(A_zimbraDataSourceContactSyncEnabled, false)) {
            if (ds instanceof OfflineDataSource && ((OfflineDataSource)ds).isYahoo()) {
                if (!OAuthManager.hasOAuthToken(account.getId())) {
                    if (ds.getAttr(A_offlineYContactVerifier) == null) {
                        throw ServiceException.FAILURE("Need Yahoo OAuth verification. Please visit account setup page", null);
                    }
                    OAuthManager.persistCredential(account.getId(), ds.getAttr(A_offlineYContactToken),
                            ds.getAttr(A_offlineYContactTokenSecret),
                            ds.getAttr(A_offlineYContactTokenSessionHandle),
                            ds.getAttr(A_offlineYContactGuid),
                            ds.getAttr(A_offlineYContactTokenTimestamp),
                            ds.getAttr(A_offlineYContactVerifier));
                    if (OAuthManager.hasOAuthToken(account.getId())) {
                        setDataSourceAttribute(ds, OfflineConstants.A_offlineYContactTokenReady, ProvisioningConstants.TRUE);
                        YContactSync.migrateExistingContacts(((OfflineDataSource)ds).getContactSyncDataSource());
                    }
                }
            }
        }
    }

    private void adjustAccountDisplayName(Account account, DataSource ds, Map<String, Object> dsAttrs) throws ServiceException {
        String displayName = (String)dsAttrs.get(A_zimbraPrefFromDisplay);
        String oldDisplayName = account.getAttr(A_displayName);
        Map<String, Object> attrs = new HashMap<String, Object>();
        if (displayName != null && !displayName.equals("")) {
            if (oldDisplayName == null || !displayName.equals(oldDisplayName)) {
                attrs.put(A_displayName, displayName);
                attrs.put(A_zimbraPrefFromDisplay, displayName);
            }
        } else if (oldDisplayName != null) {
            attrs.put('-' + A_displayName, oldDisplayName);
            attrs.put('-' + A_zimbraPrefFromDisplay, oldDisplayName);
        }
        if (attrs.size() > 0)
            modifyAttrs(account, attrs, false, true, false);
    }

    @Override
    public synchronized DataSource get(Account account, Key.DataSourceBy keyType, String key) throws ServiceException {
        List<DataSource> cached = cachedDataSources.get(account.getId());
        if (cached != null) {
            for (DataSource ds : cached) {
                if (keyType == Key.DataSourceBy.name && ds.getName().equals(key) ||
                    keyType == Key.DataSourceBy.id && ds.getId().equals(key))
                    return ds;
            }
            return null;
        }

        Map<String,Object> attrs = null;
        if (keyType == Key.DataSourceBy.name) {
            attrs = DbOfflineDirectory.readDirectoryLeaf(EntryType.DATASOURCE, account, A_offlineDn, key);
        } else if (keyType == Key.DataSourceBy.id) {
            attrs = DbOfflineDirectory.readDirectoryLeaf(EntryType.DATASOURCE, account, A_zimbraId, key);
        }
        if (attrs == null)
            return null;

        String name = (String)attrs.get(A_zimbraDataSourceName);
        if (name == null)
            return null;

        DataSourceType type = DataSourceType.fromString((String) attrs.get(A_offlineDataSourceType));
        return new OfflineDataSource(account, type, name, (String) attrs.get(A_zimbraDataSourceId), attrs, this);
    }

    @Override
    public XMPPComponent createXMPPComponent(String name, Domain domain, Server server, Map<String, Object> attrs) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<XMPPComponent> getAllXMPPComponents() throws ServiceException {
        throw ServiceException.FAILURE("unsupported", null);
    }

    @Override
    public XMPPComponent get(Key.XMPPComponentBy keyType, String key) throws ServiceException {
        throw ServiceException.FAILURE("unsupported", null);
    }

    @Override
    public void deleteXMPPComponent(XMPPComponent comp) throws ServiceException {
        throw ServiceException.FAILURE("unsupported", null);
    }

    @Override
    public void flushCache(CacheEntryType type, CacheEntry[] entries) throws ServiceException {
        //no-op
    }

    private String promoteAccount(String accountId) throws ServiceException {
        this.cachedAccountIds.remove(accountId);
        this.cachedAccountIds.add(0, accountId);
        return Joiner.on(",").join(this.cachedAccountIds);
    }

    private void persistAccountsOrder() throws ServiceException {
        Account localAccount = getLocalAccount();
        Map<String, Object> attrs = new HashMap<String, Object>(1);
        attrs.put(A_offlineAccountsOrder, Joiner.on(",").join(this.getAllAccountIds()));
        modifyAttrs(localAccount, attrs, false, false, false);
    }

    protected void loadRemoteSyncServer(OfflineAccount granter, String granteeId) throws ServiceException {
        Account grantee = get(AccountBy.id, granteeId);
        if (grantee == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(granteeId);
        String uri = grantee.getAttr(OfflineConstants.A_offlineRemoteServerUri);
        if (uri == null) {
            OfflineLog.offline.warn("offline account missing RemoteServerUri attr: " + grantee.getName());
            throw AccountServiceException.INVALID_ATTR_VALUE(OfflineConstants.A_offlineRemoteServerUri + " is null", null);
        }
        String mailHost = (OfflineConstants.SYNC_SERVER_PREFIX + uri).toLowerCase();
        granter.setCachedAttr(A_zimbraMailHost, mailHost);

        Server server;
        synchronized (mSyncServerCache) {
            server = mSyncServerCache.get(mailHost);
        }
        if (server != null)
            return;
        String id = (OfflineConstants.SYNC_SERVER_PREFIX + granteeId).toLowerCase();

        boolean ssl = uri.startsWith("https://");
        String port = ssl ? "443" : "80";
        String host = uri;
        int dslash = uri.indexOf("//");
        if (dslash > 0)
            host = uri.substring(dslash + 2);
        int colon = host.indexOf(':');
        if (colon > 0) {
            port = host.substring(colon + 1);
            host = host.substring(0, colon);
        }

        Map<String, Object> attrs = new HashMap<String, Object>(12);
        attrs.put(Provisioning.A_objectClass, "zimbraServer");
        attrs.put(Provisioning.A_cn, host);
        attrs.put(Provisioning.A_zimbraServiceHostname, host);
        attrs.put(Provisioning.A_zimbraSmtpHostname, host);
        attrs.put(Provisioning.A_zimbraId, id);
        attrs.put("zimbraServiceEnabled", "mailbox");
        attrs.put("zimbraServiceInstalled", "mailbox");
        if (ssl)
            attrs.put(Provisioning.A_zimbraMailSSLPort, port);
        else
            attrs.put(Provisioning.A_zimbraMailPort, port);
        attrs.put(Provisioning.A_zimbraAdminPort, port);
        attrs.put(Provisioning.A_zimbraMailMode, ssl ? "https" : "http");

        server = new Server(mailHost, id, attrs, null, this);
        synchronized(mSyncServerCache) {
            mSyncServerCache.put(mailHost, server);
            mSyncServerCache.put(id, server);
        }
    }

    /**
     * Get auth token for proxying. Only implemented in OfflineProvisioning
     * @param targetAcctId - the account we are proxying to
     * @param originalContext - the original request context. Used internally to ensure proxy token is obtained for correct mountpoint account
     */
    @Override
    public String getProxyAuthToken(String acctId, Map<String, Object> originalContext) throws ServiceException {
        Account account = get(AccountBy.id, acctId);
        if (account != null && (isZcsAccount(account) || isMountpointAccount(account))) {
            String id = isMountpointAccount(account) ? account.getAttr(A_offlineMountpointProxyAccountId) : acctId;
            if (isMountpointAccount(account) && originalContext != null && account.getBooleanAttr(A_offlineAmbiguousGranter, false)) {
                ZimbraSoapContext zsc = (ZimbraSoapContext) originalContext.get(SoapEngine.ZIMBRA_CONTEXT);
                if (zsc != null) {
                    String sourceAcctId = zsc.getRequestedAccountId();
                    if (sourceAcctId != null && !sourceAcctId.equals(id)) {
                        //edge case where more than one grantee for a given granter
                        GranterEntry ge = lookupGranter("id", acctId, sourceAcctId);
                        if (sourceAcctId.equals(ge.granteeId)) {
                            id = sourceAcctId;
                        } else {
                            OfflineLog.offline.warn("grantee mismatch for mountpoint account; proxy will likely fail");
                        }
                    }
                }
            }
            ZcsMailbox ombx = (ZcsMailbox)MailboxManager.getInstance().getMailboxByAccountId(id, false);
            ZAuthToken at = ombx.getAuthToken();
            return at == null ? null : at.getValue();
        } else {
            return null;
        }
    }

    public String getCalendarProxyAuthToken(String requestedAccountId, Map<String, Object> context) throws ServiceException {
        Account acct = getAccountById(requestedAccountId);
        if (acct != null && isMountpointAccount(acct) && acct.getBooleanAttr(OfflineProvisioning.A_offlineAmbiguousGranter, false)) {
            //bug 67569. try to determine which account might have a calendar folder
            //requested account is always the share granter
            //this is a conflict between on-behalf-of and family mailbox, both use accountName SOAP header. should ideally be different
            //have to decide which one of our accts has access to calendar
            //still can have errors if multiple accts have different calendars from same granter; need separate on-behalf-of vs requested acct
            ZimbraSoapContext origZsc = context == null ? null : (ZimbraSoapContext) context.get(SoapEngine.ZIMBRA_CONTEXT);
            if (origZsc != null) {
                if (requestedAccountId.equals(origZsc.getRequestedAccountId())) {
                    List<Account> zcsAccts = getAllZcsAccounts();
                    for (Account zcsAcct : zcsAccts) {
                        ZcsMailbox ombx = (ZcsMailbox) MailboxManager.getInstance().getMailboxByAccount(zcsAcct);
                        List<MailItem> mounts = ombx.getItemList(null, MailItem.Type.MOUNTPOINT);
                        if (mounts != null) {
                            for (MailItem mount : mounts) {
                                if (((Mountpoint) mount).getDefaultView() == MailItem.Type.APPOINTMENT) {
                                    //probably this one.
                                    return getProxyAuthToken(zcsAcct.getId(), null);
                                }
                            }
                        }
                    }

                }
            }
        }
        return getProxyAuthToken(requestedAccountId, context);
    }

    @Override
    public boolean isOfflineProxyServer(Server server) {
        return server.getName().startsWith(OfflineConstants.SYNC_SERVER_PREFIX);
    }

    @Override
    public boolean allowsPingRemote() {
        return false; // in offline don't actively ping remote servers for pending sessions
    }

    public boolean syncZimletProperties(String accountId) throws ServiceException {
        return accountId.equals(getZimletSyncAccountId());
    }

    public String getZimletSyncAccountId() throws ServiceException {
        Account localAcct = getLocalAccount();
        return localAcct.getAttr(A_zimbraPrefOfflineZimletSyncAccountId);
    }

    public Set<String> listAllDomains() throws ServiceException {
        return Collections.unmodifiableSet(domainGals.keySet());
    }

    public OfflineDomainGal getDomainGal(String domain) {
        return domainGals.get(domain.toLowerCase());
    }

    public OfflineDomainGal createDomainGal(String domain, Map<String, Object> attrs) throws ServiceException {
        domain = domain.toLowerCase();
        CallbackContext callbackContext = new CallbackContext(CallbackContext.Op.CREATE);
        AttributeManager.getInstance().preModify(attrs, null, callbackContext, true);
        String zimbraId = UUID.randomUUID().toString();
        attrs.put(A_zimbraId, zimbraId);
        attrs.put(A_cn, domain);
        attrs.put(A_objectClass, "domainGalEntry");
        attrs.put(A_offlineGalRetryEnabled, ProvisioningConstants.TRUE);

        DbOfflineDirectory.createDirectoryEntry(EntryType.GAL, domain, attrs, false);
        OfflineDomainGal gal = new OfflineDomainGal(domain, zimbraId, attrs, this);
        domainGals.put(domain, gal);
        AttributeManager.getInstance().postModify(attrs, gal, callbackContext);
        OfflineLog.offline.debug("created domain %s for offline GAL", domain);
        return gal;
    }

    public void attachAccountToGal(String domain, String accountId) throws ServiceException {
        OfflineDomainGal domainGal = getDomainGal(domain);
        HashMap<String, String> attrs = new HashMap<String, String>();
        attrs.put("+" + OfflineProvisioning.A_offlineUsingGalAccountId, accountId);
        modifyAttrs(domainGal, attrs);
    }

    public void detachAccountFromGal(String domain, String accountId) throws ServiceException {
        OfflineDomainGal domainGal = getDomainGal(domain);
        HashMap<String, String> attrs = new HashMap<String, String>();
        attrs.put("-" + OfflineProvisioning.A_offlineUsingGalAccountId, accountId);
        modifyAttrs(domainGal, attrs);
        String[] accountsUsingGal = getDomainGal(domain).getAttachedToGalAccountIds();
        if (accountsUsingGal == null || accountsUsingGal.length == 0) {
            deleteDomainGal(domain);
        }
    }

    public void assignGalAccountToDomain(OfflineDomainGal domainGal, Account galAccount) throws ServiceException {
        String[] gals = domainGal.getMultiAttr(OfflineConstants.A_offlineGalAccountId);
        if (gals != null && gals.length > 0) {
            throw OfflineServiceException.UNEXPECTED("Before adding gal Account " + galAccount.getName() + "(" + galAccount.getId() + ")"
                    + ", Domain GAL already has linked GAL account(s). " + Arrays.toString(gals));
        }
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put("+" + OfflineConstants.A_offlineGalAccountId, galAccount.getId());
        modifyAttrs(domainGal, attrs);
    }

    public void deleteDomainGal(String domain) throws ServiceException {
        domain = domain.toLowerCase();

        OfflineDomainGal gal = domainGals.get(domain);
        if (gal == null)
            return;
        String galAccount = gal.getGalAccountId();
        DbOfflineDirectory.deleteDirectoryEntry(EntryType.GAL, gal.getId());
        domainGals.remove(domain);
        deleteGalAccount(galAccount);
    }

    public void deleteGalAccount(String galAccountId) throws ServiceException {
        Mailbox mbox = OfflineMailboxManager.getInstance().getMailboxByAccountId(galAccountId);
        if (mbox != null) {
            mbox.deleteMailbox();
        }
        deleteOfflineAccount(galAccountId);
    }

    public Account getGalAccountByAccount(OfflineAccount account) throws ServiceException {
        OfflineDomainGal gal = this.getDomainGal(account.getDomain());
        if (gal != null) {
            return gal.getGalAccount();
        }
        return null;
    }

    @Override
    public ShareLocator get(ShareLocatorBy keyType, String key) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ShareLocator createShareLocator(String id, Map<String, Object> attrs) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteShareLocator(String id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public UCService createUCService(String name, Map<String, Object> attrs)
            throws ServiceException {
        throw new UnsupportedOperationException();    }

    @Override
    public void deleteUCService(String zimbraId) throws ServiceException {
        throw new UnsupportedOperationException();    }

    @Override
    public UCService get(UCServiceBy keyName, String key) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<UCService> getAllUCServices() throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void renameUCService(String zimbraId, String newName) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}
