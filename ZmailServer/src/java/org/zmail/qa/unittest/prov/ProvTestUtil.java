/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.qa.unittest.prov;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.zmail.common.account.Key;
import org.zmail.common.account.ProvisioningConstants;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.Key.CacheEntryBy;
import org.zmail.common.account.Key.CalendarResourceBy;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.DynamicGroup;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.IDNUtil;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.UCService;
import org.zmail.cs.account.Zimlet;
import org.zmail.cs.account.Provisioning.CacheEntry;
import org.zmail.cs.ldap.LdapConstants;
import org.zmail.qa.unittest.TestUtil;
import org.zmail.soap.admin.type.CacheEntryType;
import org.zmail.soap.admin.type.DataSourceType;

public abstract class ProvTestUtil {
    public static final String DEFAULT_UC_PROVIDER = "mitel";

    // accounts, crs, groups, coses, servers, zimlets
    private List<NamedEntry> createdEntries = new ArrayList<NamedEntry>();

    // data sources, identities, signatures, need to delete these before deleting accounts
    private List<NamedEntry> createdAccountSubordinates = new ArrayList<NamedEntry>();

    // domains, will be deleted after domain-ed entries are deleted
    private List<NamedEntry> createdDomains = new ArrayList<NamedEntry>();

    protected Provisioning prov;

    protected ProvTestUtil(Provisioning prov) {
        this.prov = prov;
    }

    public void deleteAllEntries() throws Exception {
        for (NamedEntry entry : createdAccountSubordinates) {
            deleteEntry(entry);
        }
        createdAccountSubordinates.clear();

        for (NamedEntry entry : createdEntries) {
            deleteEntry(entry);
        }
        createdEntries.clear();

        for (NamedEntry entry : createdDomains) {
            deleteEntry(entry);
        }
        createdDomains.clear();
    }

    private void deleteEntry(NamedEntry entry) throws Exception {
        if (entry instanceof Account) {
            prov.deleteAccount(entry.getId());
        } else if (entry instanceof CalendarResource) {
            prov.deleteCalendarResource(entry.getId());
        } else if (entry instanceof Cos) {
            prov.deleteCos(entry.getId());
        } else if (entry instanceof Group) {
            prov.deleteGroup(entry.getId());
        } else if (entry instanceof Domain) {
            prov.deleteDomain(entry.getId());
        } else if (entry instanceof Server) {
            prov.deleteServer(entry.getId());
        } else if (entry instanceof UCService) {
            prov.deleteUCService(entry.getId());
        } else if (entry instanceof Zimlet) {
            prov.deleteZimlet(entry.getName());
        } else {
            fail();
        }
    }

    private void flushCache(CacheEntryType type, CacheEntryBy by, String key)
    throws Exception {
        prov.flushCache(type, new CacheEntry[]{new CacheEntry(by, key)});
    }

    public String getSystemDefaultDomainName() throws Exception {
        Config config = prov.getConfig(Provisioning.A_zmailDefaultDomainName);
        String domainName = config.getAttr(Provisioning.A_zmailDefaultDomainName, null);
        assertFalse(Strings.isNullOrEmpty(domainName));
        return domainName;
    }

    public Domain createDomain(String domainName) throws Exception {
        return createDomain(domainName, null);
    }

    public Domain createDomain(String domainName, Map<String, Object> attrs)
    throws Exception {
        if (attrs == null) {
            attrs = new HashMap<String, Object>();
        }

        Domain domain = prov.get(Key.DomainBy.name, domainName);
        assertNull(domain);
        domain = prov.createDomain(domainName, attrs);
        assertNotNull(domain);

        flushCache(CacheEntryType.domain, CacheEntryBy.id, domain.getId());
        domain = prov.get(Key.DomainBy.name, domainName);
        assertNotNull(domain);
        assertEquals(IDNUtil.toAsciiDomainName(domainName).toLowerCase(),
                domain.getName().toLowerCase());

        createdDomains.add(domain);
        return domain;
    }

    public void deleteDomain(Domain domain) throws Exception {
        String domainId = domain.getId();
        prov.deleteDomain(domainId);
        domain = prov.get(Key.DomainBy.id, domainId);
        assertNull(domain);
    }

    public Account createAccount(String acctName, String password, Map<String, Object> attrs)
    throws Exception {
        Account acct = prov.get(AccountBy.name, acctName);
        assertNull(acct);

        if (password == null) {
            password = "test123";
        }
        acct = prov.createAccount(acctName, password, attrs);
        assertNotNull(acct);

        flushCache(CacheEntryType.account, CacheEntryBy.id, acct.getId());
        acct = prov.get(AccountBy.name, acctName);
        assertNotNull(acct);
        assertEquals(acctName.toLowerCase(), acct.getName().toLowerCase());

        createdEntries.add(acct);
        return acct;
    }

    public Account createAccount(String acctName, String password) throws Exception {
        return createAccount(acctName, password, (Map) null);
    }

    public Account createAccount(String acctName) throws Exception {
        return createAccount(acctName, (String) null, (Map) null);
    }

    public Account createAccount(String acctName, Map<String, Object> attrs) throws Exception {
        return createAccount(acctName, (String) null, attrs);
    }

    public Account createAccount(String localPart, Domain domain) throws Exception {
        return createAccount(localPart, domain, (Map) null);
    }

    public Account createExternalAccount(String localPart, Domain domain) throws Exception {
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put(Provisioning.A_zmailIsExternalVirtualAccount, ProvisioningConstants.TRUE);
        return createAccount(localPart, domain, attrs);
    }

    public Account createAccount(String localPart, Domain domain, Map<String, Object> attrs)
    throws Exception {
        String acctName = TestUtil.getAddress(localPart, domain.getName());
        return createAccount(acctName, attrs);
    }

    public void deleteAccount(Account acct) throws Exception {
        String acctId = acct.getId();
        prov.deleteAccount(acctId);
        acct = prov.get(AccountBy.id, acctId);
        assertNull(acct);

        // flush cache, make sure it is deleted from ldap
        flushCache(CacheEntryType.account, CacheEntryBy.id, acctId);
        acct = prov.get(AccountBy.id, acctId);
        assertNull(acct);
    }

    public Account createGlobalAdmin(String acctName, String password) throws Exception {
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put(Provisioning.A_zmailIsAdminAccount, ProvisioningConstants.TRUE);
        return createAccount(acctName, password, attrs);
    }

    public Account createGlobalAdmin(String localPart, Domain domain, String password)
    throws Exception {
        String acctName = TestUtil.getAddress(localPart, domain.getName());
        return createGlobalAdmin(acctName, password);
    }

    public Account createGlobalAdmin(String localPart, Domain domain) throws Exception {
        String acctName = TestUtil.getAddress(localPart, domain.getName());
        return createGlobalAdmin(acctName, (String) null);
    }

    public Account createGlobalAdmin(String acctName) throws Exception {
        return createGlobalAdmin(acctName, (String) null);
    }

    public Account createDelegatedAdmin(String acctName, String password) throws Exception {
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put(Provisioning.A_zmailIsDelegatedAdminAccount, ProvisioningConstants.TRUE);
        return createAccount(acctName, password, attrs);
    }

    public Account createDelegatedAdmin(String acctName) throws Exception {
        return createDelegatedAdmin(acctName, (String) null);
    }

    public Account createDelegatedAdmin(String localPart, Domain domain, String password) throws Exception {
        String acctName = TestUtil.getAddress(localPart, domain.getName());
        return createDelegatedAdmin(acctName, password);
    }

    public Account createDelegatedAdmin(String localPart, Domain domain) throws Exception {
        return createDelegatedAdmin(localPart, domain, (String) null);
    }

    public Account createSystemAccount(String acctName) throws Exception {
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put(Provisioning.A_zmailIsSystemAccount, ProvisioningConstants.TRUE);
        return createAccount(acctName, attrs);
    }

    public Account createSystemAccount(String localPart, Domain domain) throws Exception {
        String acctName = TestUtil.getAddress(localPart, domain.getName());
        return createSystemAccount(acctName);
    }

    public Account createSystemResource(String acctName) throws Exception {
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put(Provisioning.A_zmailIsSystemResource, ProvisioningConstants.TRUE);
        return createAccount(acctName, attrs);
    }

    public Account createSystemResource(String localPart, Domain domain) throws Exception {
        String acctName = TestUtil.getAddress(localPart, domain.getName());
        return createSystemResource(acctName);
    }

    public CalendarResource createCalendarResource(String localPart, Domain domain,
            Map<String, Object> attrs)
    throws Exception {
        if (attrs == null) {
            attrs = new HashMap<String, Object>();
            attrs.put(Provisioning.A_displayName, localPart);
            attrs.put(Provisioning.A_zmailCalResType, Provisioning.CalResType.Equipment.name());
        }

        String crName = TestUtil.getAddress(localPart, domain.getName());
        CalendarResource cr = prov.get(CalendarResourceBy.name, crName);
        assertNull(cr);

        cr = prov.createCalendarResource(crName, "test123", attrs);
        assertNotNull(cr);

        flushCache(CacheEntryType.account, CacheEntryBy.id, cr.getId());
        cr = prov.get(CalendarResourceBy.name, crName);
        assertNotNull(cr);
        assertEquals(crName.toLowerCase(), cr.getName().toLowerCase());

        createdEntries.add(cr);
        return cr;
    }

    public CalendarResource createCalendarResource(String localPart, Domain domain)
    throws Exception {
        return createCalendarResource(localPart, domain, null);
    }

    public Group createGroup(String groupName, Map<String, Object> attrs, boolean dynamic)
    throws Exception {
        Group group = prov.getGroup(Key.DistributionListBy.name, groupName);
        assertNull(group);

        if (attrs == null) {
            attrs = new HashMap<String, Object>();
        }

        group = prov.createGroup(groupName, attrs, dynamic);
        assertNotNull(group);

        flushCache(CacheEntryType.group, CacheEntryBy.id, group.getId());
        group = prov.getGroup(Key.DistributionListBy.name, groupName);
        assertNotNull(group);
        assertEquals(groupName.toLowerCase(), group.getName().toLowerCase());

        createdEntries.add(group);
        return group;
    }

    public Group createGroup(String groupName, boolean dynamic)
    throws Exception {
        return createGroup(groupName, (Map<String, Object>) null, dynamic);
    }

    public Group createGroup(String localPart, Domain domain,
            Map<String, Object> attrs, boolean dynamic)
    throws Exception {
        String groupName = TestUtil.getAddress(localPart, domain.getName());
        return createGroup(groupName, attrs, dynamic);
    }

    public Group createGroup(String localPart, Domain domain, boolean dynamic)
    throws Exception {
        return createGroup(localPart, domain, null, dynamic);
    }

    public Group createAdminGroup(String groupName, boolean dynamic)
    throws Exception {
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put(Provisioning.A_zmailIsAdminGroup, ProvisioningConstants.TRUE);
        return createGroup(groupName, attrs, dynamic);
    }

    public Group createAdminGroup(String groupName)
    throws Exception {
        return createAdminGroup(groupName, false);
    }

    public Group createAdminGroup(String localPart, Domain domain, boolean dynamic)
    throws Exception {
        String groupName = TestUtil.getAddress(localPart, domain.getName());
        return createAdminGroup(groupName, dynamic);
    }

    public Group createAdminGroup(String localPart, Domain domain)
    throws Exception {
        String groupName = TestUtil.getAddress(localPart, domain.getName());
        return createAdminGroup(groupName);
    }

    public void deleteGroup(Group group)
    throws Exception {
        String groupId = group.getId();
        prov.deleteGroup(groupId);
        group = prov.get(Key.DistributionListBy.id, groupId);
        assertNull(group);

        // flush cache, make sure it is deleted from ldap
        flushCache(CacheEntryType.group, CacheEntryBy.id, groupId);
        group = prov.get(Key.DistributionListBy.id, groupId);
        assertNull(group);
    }

    public DistributionList createDistributionList(String localPart, Domain domain)
    throws Exception {
        return createDistributionList(localPart, domain, null);
    }

    public DistributionList createDistributionList(String localPart,
            Domain domain, Map<String, Object> attrs) throws Exception {
        return (DistributionList) createGroup(localPart, domain, attrs, false);
    }

    public void deleteDistributionList(DistributionList dl)
    throws Exception {
        String dlId = dl.getId();
        prov.deleteDistributionList(dl.getId());
        dl = prov.get(Key.DistributionListBy.id, dlId);
        assertNull(dl);

        // flush cache, make sure it is deleted from ldap
        flushCache(CacheEntryType.group, CacheEntryBy.id, dlId);
        dl = prov.get(Key.DistributionListBy.id, dlId);
        assertNull(dl);
    }

    public DynamicGroup createDynamicGroup(String localPart,
            Domain domain, Map<String, Object> attrs) throws Exception {
        return (DynamicGroup) createGroup(localPart, domain, attrs, true);
    }

    public DynamicGroup createDynamicGroup(String localPart, Domain domain)
    throws Exception {
        return (DynamicGroup) createGroup(localPart, domain, null, true);
    }

    public void deleteDynamicGroup(DynamicGroup group) throws Exception {
        deleteGroup(group);
    }

    public Cos createCos(String cosName) throws Exception {
        return createCos(cosName, null);
    }

    public Cos createCos(String cosName, Map<String, Object> attrs)
    throws Exception {
        Cos cos = prov.get(Key.CosBy.name, cosName);
        assertNull(cos);

        if (attrs == null) {
            attrs = new HashMap<String, Object>();
        }

        cos = prov.createCos(cosName, attrs);
        assertNotNull(cos);

        flushCache(CacheEntryType.cos, CacheEntryBy.id, cos.getId());
        cos = prov.get(Key.CosBy.name, cosName);
        assertNotNull(cos);
        assertEquals(cosName.toLowerCase(), cos.getName().toLowerCase());

        createdEntries.add(cos);
        return cos;
    }

    public void deleteCos(Cos cos) throws Exception {
        String codId = cos.getId();
        prov.deleteCos(codId);
        cos = prov.get(Key.CosBy.id, codId);
        assertNull(cos);

        // flush cache, make sure it is deleted from ldap
        flushCache(CacheEntryType.cos, CacheEntryBy.id, codId);
        cos = prov.get(Key.CosBy.id, codId);
        assertNull(cos);
    }

    public Server createServer(String serverName, Map<String, Object> attrs)
    throws Exception {
        if (attrs == null) {
            attrs = new HashMap<String, Object>();
        }
        Server server = prov.get(Key.ServerBy.name, serverName);
        assertNull(server);

        server = prov.createServer(serverName, attrs);
        assertNotNull(server);

        server = prov.get(Key.ServerBy.name, serverName);
        assertNotNull(server);
        assertEquals(serverName.toLowerCase(), server.getName().toLowerCase());

        createdEntries.add(server);
        return server;
    }

    public Server createServer(String serverName) throws Exception {
        return createServer(serverName, null);
    }

    public UCService createUCService(String ucServiceName, Map<String, Object> attrs)
    throws Exception {
        if (attrs == null) {
            attrs = new HashMap<String, Object>();
            attrs.put(Provisioning.A_zmailUCProvider, DEFAULT_UC_PROVIDER);
        }

        UCService ucService = prov.get(Key.UCServiceBy.name, ucServiceName);
        assertNull(ucService);

        ucService = prov.createUCService(ucServiceName, attrs);
        assertNotNull(ucService);

        ucService = prov.get(Key.UCServiceBy.name, ucServiceName);
        assertNotNull(ucService);
        assertEquals(ucServiceName.toLowerCase(), ucService.getName().toLowerCase());

        createdEntries.add(ucService);
        return ucService;
    }

    public UCService createUCService(String serverName) throws Exception {
        return createUCService(serverName, null);
    }

    public void deleteServer(Server server) throws Exception {
        String serverId = server.getId();
        prov.deleteServer(serverId);
        server = prov.get(Key.ServerBy.id, serverId);
        assertNull(server);
    }

    public DataSource createDataSourceRaw(Account acct, String dataSourceName)
    throws Exception {
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailDataSourceEnabled, LdapConstants.LDAP_TRUE);
        attrs.put(Provisioning.A_zmailDataSourceFolderId, "123");
        attrs.put(Provisioning.A_zmailDataSourceConnectionType, "ssl");
        attrs.put(Provisioning.A_zmailDataSourceHost, "zmail.com");
        attrs.put(Provisioning.A_zmailDataSourcePort, "9999");
        DataSource ds = prov.createDataSource(acct, DataSourceType.pop3, dataSourceName, attrs);

        createdAccountSubordinates.add(ds);
        return ds;
    }

    public DataSource createDataSource(Account acct, String dataSourceName)
    throws Exception {
        DataSource dataSource = prov.get(acct, Key.DataSourceBy.name, dataSourceName);
        assertNull(dataSource);

        dataSource = createDataSourceRaw(acct, dataSourceName);
        assertNotNull(dataSource);

        dataSource = prov.get(acct, Key.DataSourceBy.name, dataSourceName);
        assertNotNull(dataSource);
        assertEquals(dataSourceName, dataSource.getName());

        return dataSource;
    }

    public void deleteDataSource(Account acct, DataSource dataSource)
    throws Exception {
        String dataSourceId = dataSource.getId();
        prov.deleteDataSource(acct, dataSourceId);
        dataSource = prov.get(acct, Key.DataSourceBy.id, dataSourceId);
        assertNull(dataSource);
    }

    public Zimlet createZimlet(String zimletName, Map<String, Object> attrs)
    throws Exception {
        Zimlet zimlet = prov.createZimlet(zimletName, attrs);
        createdEntries.add(zimlet);
        return zimlet;
    }
}

