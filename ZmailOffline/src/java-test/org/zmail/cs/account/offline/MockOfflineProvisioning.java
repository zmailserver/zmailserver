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
package org.zmail.cs.account.offline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.Key.CalendarResourceBy;
import org.zmail.common.account.Key.CosBy;
import org.zmail.common.account.Key.DataSourceBy;
import org.zmail.common.account.Key.DistributionListBy;
import org.zmail.common.account.Key.DomainBy;
import org.zmail.common.account.Key.IdentityBy;
import org.zmail.common.account.Key.ServerBy;
import org.zmail.common.account.Key.SignatureBy;
import org.zmail.common.account.Key.XMPPComponentBy;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.GlobalGrant;
import org.zmail.cs.account.Identity;
import org.zmail.cs.account.NamedEntry.Visitor;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.Signature;
import org.zmail.cs.account.XMPPComponent;
import org.zmail.cs.account.Zimlet;
import org.zmail.cs.account.auth.AuthContext;
import org.zmail.cs.account.auth.AuthContext.Protocol;
import org.zmail.cs.mime.MimeTypeInfo;
import org.zmail.cs.mime.MockMimeTypeInfo;
import org.zmail.cs.mime.handler.UnknownTypeHandler;
import org.zmail.soap.admin.type.CacheEntryType;
import org.zmail.soap.admin.type.DataSourceType;

public final class MockOfflineProvisioning extends OfflineProvisioning {

    private final Map<String, Account> id2account = new HashMap<String, Account>();
    private final Map<String, Account> name2account = new HashMap<String, Account>();
    private final Server localhost;

    public MockOfflineProvisioning() {
        super(true);
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailServiceHostname, "localhost");
        localhost = new Server("localhost", "localhost", attrs, Collections.<String, Object> emptyMap(), this);
    }

    @Override
    public synchronized Account createAccount(String email, String password, Map<String, Object> attrs) throws ServiceException {
        validate(ProvisioningValidator.CREATE_ACCOUNT, email, null, attrs);

        Account account = new OfflineAccount(email, email, attrs, null, null, this);
        try {
            name2account.put(email, account);
            id2account.put(account.getId(), account);
            return account;
        } finally {
            validate(ProvisioningValidator.CREATE_ACCOUNT_SUCCEEDED, email, account);
        }
    }

    @Override
    public Account get(AccountBy keyType, String key) {
        switch (keyType) {
            case name:
                return name2account.get(key);
            case id:
            default:
                return id2account.get(key);
        }
    }

    private final Map<String, List<MimeTypeInfo>> mimeConfig = new HashMap<String, List<MimeTypeInfo>>();

    @Override
    public synchronized List<MimeTypeInfo> getMimeTypes(String mime) {
        List<MimeTypeInfo> result = mimeConfig.get(mime);
        if (result != null) {
            return result;
        } else {
            MockMimeTypeInfo info = new MockMimeTypeInfo();
            info.setHandlerClass(UnknownTypeHandler.class.getName());
            return Collections.<MimeTypeInfo> singletonList(info);
        }
    }

    @Override
    public synchronized List<MimeTypeInfo> getAllMimeTypes() {
        List<MimeTypeInfo> result = new ArrayList<MimeTypeInfo>();
        for (List<MimeTypeInfo> entry : mimeConfig.values()) {
            result.addAll(entry);
        }
        return result;
    }

    public void addMimeType(String mime, MimeTypeInfo info) {
        List<MimeTypeInfo> list = mimeConfig.get(mime);
        if (list == null) {
            list = new ArrayList<MimeTypeInfo>();
            mimeConfig.put(mime, list);
        }
        list.add(info);
    }

    private final Config config = new Config(new HashMap<String, Object>(), this);

    @Override
    public Config getConfig() {
        return config;
    }

//    @Override
//    public List<Zimlet> getObjectTypes() {
//        return Collections.emptyList();
//    }

    @Override
    public void modifyAttrs(Entry entry, Map<String, ? extends Object> attrs, boolean checkImmutable) {
        Map<String, Object> map = entry.getAttrs(false);
        for (Map.Entry<String, ? extends Object> attr : attrs.entrySet()) {
            if (attr.getValue() != null) {
                map.put(attr.getKey(), attr.getValue());
            } else {
                map.remove(attr.getKey());
            }
        }
        entry.setAttrs(map); //needed since OfflineAccount returns new Map, not a reference to internal map..
    }

    @Override
    public Server getLocalServer() {
        return localhost;
    }

    @Override
    public void modifyAttrs(Entry e, Map<String, ? extends Object> attrs, boolean checkImmutable, boolean allowCallback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void reload(Entry e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized boolean inDistributionList(Account acct, String zmailId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Set<String> getDistributionLists(Account acct) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<DistributionList> getDistributionLists(Account acct, boolean directOnly, Map<String, String> via) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<DistributionList> getDistributionLists(DistributionList list, boolean directOnly, Map<String, String> via) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized boolean healthCheck() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized GlobalGrant getGlobalGrant() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Account restoreAccount(String emailAddress, String password, Map<String, Object> attrs,
            Map<String, Object> origAttrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAccount(String zmailId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void renameAccount(String zmailId, String newName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Account> getAllAdminAccounts() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void setCOS(Account acct, Cos cos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void modifyAccountStatus(Account acct, String newStatus) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void authAccount(Account acct, String password, Protocol proto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void authAccount(Account acct, String password, Protocol proto, Map<String, Object> authCtxt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void preAuthAccount(Account acct, String accountName, String accountBy, long timestamp, long expires,
            String preAuth, Map<String, Object> authCtxt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void ssoAuthAccount(Account acct, AuthContext.Protocol proto, Map<String, Object> authCtxt) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void changePassword(Account acct, String currentPassword, String newPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized SetPasswordResult setPassword(Account acct, String newPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void checkPasswordStrength(Account acct, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void addAlias(Account acct, String alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void removeAlias(Account acct, String alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Domain createDomain(String name, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Domain get(DomainBy keyType, String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<Domain> getAllDomains() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void deleteDomain(String zmailId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Cos createCos(String name, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Cos copyCos(String srcCosId, String destCosName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void renameCos(String zmailId, String newName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Cos get(CosBy keyType, String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<Cos> getAllCos() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void deleteCos(String zmailId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Server createServer(String name, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Server get(ServerBy keyName, String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<Server> getAllServers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<Server> getAllServers(String service) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void deleteServer(String zmailId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized DistributionList createDistributionList(String listAddress, Map<String, Object> listAttrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized DistributionList get(DistributionListBy keyType, String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void deleteDistributionList(String zmailId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void addAlias(DistributionList dl, String alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void removeAlias(DistributionList dl, String alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void renameDistributionList(String zmailId, String newName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Zimlet getZimlet(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<Zimlet> listAllZimlets() {
        return Collections.emptyList();
    }

    @Override
    public synchronized Zimlet createZimlet(String name, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void deleteZimlet(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized CalendarResource createCalendarResource(String emailAddress, String password, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void deleteCalendarResource(String zmailId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void renameCalendarResource(String zmailId, String newName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized CalendarResource get(CalendarResourceBy keyType, String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<Account> getAllAccounts(Domain d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void getAllAccounts(Domain d, Visitor visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void getAllAccounts(Domain d, Server s, Visitor visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<?> getAllCalendarResources(Domain d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void getAllCalendarResources(Domain d, Visitor visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void getAllCalendarResources(Domain d, Server s, Visitor visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<?> getAllDistributionLists(Domain d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void addMembers(DistributionList list, String[] members) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void removeMembers(DistributionList list, String[] member) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Identity createIdentity(Account account, String identityName, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Identity restoreIdentity(Account account, String identityName, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void modifyIdentity(Account account, String identityName, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void deleteIdentity(Account account, String identityName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<Identity> getAllIdentities(Account account) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Identity get(Account account, IdentityBy keyType, String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Signature createSignature(Account account, String signatureName, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Signature restoreSignature(Account account, String signatureName, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void modifySignature(Account account, String signatureId, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void deleteSignature(Account account, String signatureId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<Signature> getAllSignatures(Account account) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized Signature get(Account account, SignatureBy keyType, String key) {
        throw new UnsupportedOperationException();
    }

    private final Map<String, DataSource> dataSourcesById = new HashMap<String, DataSource>();
    private final Map<String, DataSource> dataSourcesByName = new HashMap<String, DataSource>();

    @Override
    public synchronized DataSource createDataSource(Account account, DataSourceType type, String dataSourceName, Map<String, Object> attrs) {
        DataSource ds = new OfflineDataSource(account, type, dataSourceName, (String) attrs.get(A_zmailDataSourceId), attrs, this);
        dataSourcesById.put(ds.getId(), ds);
        dataSourcesByName.put(ds.getName(), ds);
        return ds;
    }

    @Override
    public synchronized DataSource createDataSource(Account account, DataSourceType type, String dataSourceName, Map<String, Object> attrs,
            boolean passwdAlreadyEncrypted) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized DataSource restoreDataSource(Account account, DataSourceType type, String dataSourceName, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void modifyDataSource(Account account, String dataSourceId, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void deleteDataSource(Account account, String dataSourceId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized List<DataSource> getAllDataSources(Account account) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized DataSource get(Account account, DataSourceBy keyType, String key) {
        if (keyType == DataSourceBy.id) {
            return dataSourcesById.get(key);
        } else if (keyType == DataSourceBy.name) {
            return dataSourcesByName.get(key);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public XMPPComponent createXMPPComponent(String name, Domain domain, Server server, Map<String, Object> attrs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public XMPPComponent get(XMPPComponentBy keyName, String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<XMPPComponent> getAllXMPPComponents() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteXMPPComponent(XMPPComponent comp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flushCache(CacheEntryType type, CacheEntry[] entries) {
        throw new UnsupportedOperationException();
    }

}
