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
package org.zmail.qa.unittest.prov.ldap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.common.account.ZAttrProvisioning.AutoProvAuthMech;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.AutoProvisionThread;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Provisioning.DirectoryEntryVisitor;
import org.zmail.cs.account.Provisioning.EagerAutoProvisionScheduler;
import org.zmail.cs.account.auth.AuthContext;
import org.zmail.cs.account.auth.AuthMechanism.AuthMech;
import org.zmail.cs.account.ldap.AutoProvisionListener;
import org.zmail.cs.account.ldap.LdapProv;
import org.zmail.cs.ldap.LdapUtil;
import org.zmail.qa.unittest.TestUtil;
import org.zmail.qa.unittest.prov.AutoProvisionTestUtil;
import org.zmail.soap.type.AutoProvPrincipalBy;

public class TestAutoProvision extends LdapTest {
    private static LdapProvTestUtil provUtil;
    private static LdapProv prov;
    private static Domain extDomain;
    private static String extDomainDn;

    @BeforeClass
    public static void init() throws Exception {
        provUtil = new LdapProvTestUtil();
        prov = provUtil.getProv();
        extDomain = provUtil.createDomain("external." + baseDomainName());
        extDomainDn = LdapUtil.domainToDN(extDomain.getName());
    }

    @AfterClass
    public static void cleanup() throws Exception {
        Cleanup.deleteAll(baseDomainName());
    }

    private String getZmailDomainName(String testName) {
        return testName + "." + baseDomainName();
    }

    private Domain createZmailDomain(String testName, Map<String, Object> zmailDomainAttrs)
            throws Exception {
        return provUtil.createDomain(getZmailDomainName(testName), zmailDomainAttrs);
    }

    private String createExternalAcctEntry(String localPart) throws Exception {
        return createExternalAcctEntry(localPart, null);
    }

    private String createExternalAcctEntry(String localPart, Map<String, Object> attrs)
            throws Exception {
        return createExternalAcctEntry(localPart, null, attrs);
    }

    private String createExternalAcctEntry(String localPart, String externalPassword,
            Map<String, Object> attrs) throws Exception {
        String extAcctName = TestUtil.getAddress(localPart, extDomain.getName());

        Map<String, Object> extAcctAttrs = attrs == null ? new HashMap<String, Object>() : attrs;

        extAcctAttrs.put(Provisioning.A_displayName, "display name");
        extAcctAttrs.put(Provisioning.A_sn, "last name");
        Account extAcct = prov.createAccount(extAcctName, externalPassword, extAcctAttrs);
        return extAcctName;
    }

    private String externalEntryDN(String externalEntryLocalPart) {
        return String.format("uid=%s,ou=people,%s", externalEntryLocalPart, extDomainDn);
    }

    /* ================
     * LAZY mode tests
     * ================
     */

    @Test
    public void lazyModeGetExternalEntryByDNTemplate() throws Exception {
        String testName = getTestName();

        String extAcctLocalPart = testName;
        String extAcctName = createExternalAcctEntry(extAcctLocalPart);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        // uid=bydntemplate,ou=people,dc=external,dc=com,dc=zmail,dc=qa,dc=unittest,dc=testldapprovautoprovision
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapBindDn, "uid=%u,ou=people," + extDomainDn);
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        String loginName = extAcctLocalPart;
        Account acct = prov.autoProvAccountLazy(zmailDomain, loginName, null, AutoProvAuthMech.LDAP);
        AutoProvisionTestUtil.verifyAcctAutoProvisioned(acct);
    }

    @Test
    public void lazyModeGetExternalEntryBySearch() throws Exception {
        String testName = getTestName();

        String extAcctLocalPart = testName;
        String extAcctName = createExternalAcctEntry(extAcctLocalPart);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        String loginName = extAcctLocalPart;
        Account acct = prov.autoProvAccountLazy(zmailDomain, loginName, null, AutoProvAuthMech.LDAP);
        AutoProvisionTestUtil.verifyAcctAutoProvisioned(acct);
    }

    public static class TestListener implements AutoProvisionListener {
        private static TestListener instance;

        private Account acct;
        private String externalDN;

        public TestListener() {
            assertNull(instance);
            instance = this; // remember the instance created from auto provision engine
        }

        @Override
        public void postCreate(Domain domain, Account acct, String externalDN) {
            // rememebr the acct and external DN for verification
            this.acct = acct;
            this.externalDN = externalDN;

        }

        private static TestListener getInstance() {
            return instance;
        }
    }

    @Test
    public void lazyModeListener() throws Exception {
        String testName = getTestName();

        String extAcctLocalPart = testName;
        String extAcctName = createExternalAcctEntry(extAcctLocalPart);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");

        // org.zmail.qa.unittest.ldap.TestAutoProvision$TestListener
        String className = TestListener.class.getName();
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvListenerClass, className);
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        String loginName = extAcctLocalPart;
        Account acct = prov.autoProvAccountLazy(zmailDomain, loginName, null, AutoProvAuthMech.LDAP);
        AutoProvisionTestUtil.verifyAcctAutoProvisioned(acct);

        TestListener listener = TestListener.getInstance();
        assertNotNull(listener);
        assertEquals(acct.getId(), listener.acct.getId());
        assertEquals(externalEntryDN(extAcctLocalPart).toLowerCase(), listener.externalDN);
    }

    @Test
    public void lazyModeAccountLocalpartMap() throws Exception {
        String testName = getTestName();

        String extAcctLocalPart = testName;
        String zmailAcctLocalpart = "myzmailname";
        Map<String, Object> acctAttrs = new HashMap<String, Object>();
        acctAttrs.put(Provisioning.A_description, zmailAcctLocalpart); // going to be the local part of the zimrba account name
        String extAcctName = createExternalAcctEntry(extAcctLocalPart, acctAttrs);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvAccountNameMap, Provisioning.A_description);
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        String loginName = extAcctLocalPart;
        Account acct = prov.autoProvAccountLazy(zmailDomain, loginName, null, AutoProvAuthMech.LDAP);
        AutoProvisionTestUtil.verifyAcctAutoProvisioned(acct, zmailAcctLocalpart + "@" + zmailDomain.getName());
    }

    @Test
    public void lazyModeAutoProvNotEnabledByAuthMech() throws Exception {
        String testName = getTestName();

        String extAcctLocalPart = testName;
        String extAcctName = createExternalAcctEntry(extAcctLocalPart);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapBindDn, "uid=%u,ou=people," + extDomainDn);
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        String loginName = extAcctLocalPart;
        Account acct = prov.autoProvAccountLazy(zmailDomain, loginName, null, AutoProvAuthMech.PREAUTH);
        assertNull(acct);
    }

    @Test
    public void lazyModeExternalLdapAuth() throws Exception {
        String testName = getTestName();

        String externalPassword = "test456";
        String extAcctLocalPart = testName;
        String extAcctName = createExternalAcctEntry(extAcctLocalPart, externalPassword, null);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        // setup auto prov
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");
        // setup external LDAP auth
        zmailDomainAttrs.put(Provisioning.A_zmailAuthMech, AuthMech.ldap.name());
        zmailDomainAttrs.put(Provisioning.A_zmailAuthLdapURL, "ldap://localhost:389");
        zmailDomainAttrs.put(Provisioning.A_zmailAuthLdapBindDn, "uid=%u,ou=people," + extDomainDn);
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        // try auto provisioning with bad password
        String loginName = extAcctLocalPart;
        Account acct = prov.autoProvAccountLazy(zmailDomain, loginName, externalPassword + "bad", null);
        assertNull(acct);

        // try again with correct password
        acct = prov.autoProvAccountLazy(zmailDomain, loginName, externalPassword, null);
        AutoProvisionTestUtil.verifyAcctAutoProvisioned(acct);
    }

    /* ==================
     * MANUAL mode tests
     * ==================
     */

    @Test
    public void manualModeByName() throws Exception {
        String testName = getTestName();

        String externalPassword = "test456";
        String extAcctLocalPart = testName;
        String extAcctName = createExternalAcctEntry(extAcctLocalPart, externalPassword, null);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        // setup auto prov
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        String principal = extAcctLocalPart;
        String password = "test123";
        Account acct = prov.autoProvAccountManual(zmailDomain, AutoProvPrincipalBy.name,
                principal, password);
        AutoProvisionTestUtil.verifyAcctAutoProvisioned(acct);

        // make sure the provided password, instead of the external password is used
        prov.authAccount(acct, password, AuthContext.Protocol.test);

        boolean caughtException = false;
        try {
            prov.authAccount(acct, externalPassword, AuthContext.Protocol.test);
        } catch (ServiceException e) {
            if (AccountServiceException.AUTH_FAILED.equals(e.getCode())) {
                caughtException = true;
            }
        }
        assertTrue(caughtException);
    }

    @Test
    public void manualModeByDN() throws Exception {
        String testName = getTestName();

        String extAcctLocalPart = testName;
        String zmailAcctLocalpart = "myzmailname";
        Map<String, Object> acctAttrs = new HashMap<String, Object>();
        acctAttrs.put(Provisioning.A_description, zmailAcctLocalpart); // going to be the local part of the zimrba account name
        String extAcctName = createExternalAcctEntry(extAcctLocalPart, acctAttrs);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        // setup auto prov
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvAccountNameMap, Provisioning.A_description);
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        String principal = externalEntryDN(extAcctLocalPart);
        String password = null;
        Account acct = prov.autoProvAccountManual(zmailDomain, AutoProvPrincipalBy.dn,
                principal, password);
        AutoProvisionTestUtil.verifyAcctAutoProvisioned(acct, zmailAcctLocalpart + "@" + zmailDomain.getName());
    }

    @Test
    public void searchAutoProvDirectoryByName() throws Exception {
        String testName = getTestName();

        String externalPassword = "test456";
        String extAcctLocalPart1 = testName + "_1";
        String extAcctLocalPart2 = testName + "_2";
        String extAcctLocalPart3 = testName + "_3";
        createExternalAcctEntry(extAcctLocalPart1, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart2, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart3, externalPassword, null);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        // setup auto prov
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        final Set<String> entriesFound = new HashSet<String>();
        DirectoryEntryVisitor visitor = new DirectoryEntryVisitor() {
            @Override
            public void visit(String dn, Map<String, Object> attrs) {
                entriesFound.add(dn);
            }
        };

        prov.searchAutoProvDirectory(zmailDomain, null, extAcctLocalPart2,
                null, 0, visitor);

        assertEquals(1, entriesFound.size());
        assertTrue(entriesFound.contains(externalEntryDN(extAcctLocalPart2).toLowerCase()));
    }

    @Test
    public void searchAutoProvDirectoryByProvidedFilter() throws Exception {
        String testName = getTestName();

        String externalPassword = "test456";
        String extAcctLocalPart1 = testName + "_1";
        String extAcctLocalPart2 = testName + "_2";
        String extAcctLocalPart3 = testName + "_3";
        createExternalAcctEntry(extAcctLocalPart1, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart2, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart3, externalPassword, null);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        // setup auto prov - no need
        // zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        // zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        final Set<String> entriesFound = new HashSet<String>();
        DirectoryEntryVisitor visitor = new DirectoryEntryVisitor() {
            @Override
            public void visit(String dn, Map<String, Object> attrs) {
                entriesFound.add(dn);
            }
        };

        prov.searchAutoProvDirectory(zmailDomain,
                String.format("(mail=%s*)", testName),
                null, null, 0, visitor);

        assertEquals(3, entriesFound.size());
        assertTrue(entriesFound.contains(externalEntryDN(extAcctLocalPart1).toLowerCase()));
        assertTrue(entriesFound.contains(externalEntryDN(extAcctLocalPart2).toLowerCase()));
        assertTrue(entriesFound.contains(externalEntryDN(extAcctLocalPart3).toLowerCase()));
    }

    @Test
    public void searchAutoProvDirectoryByConfiguredSearchFilter() throws Exception {
        String testName = getTestName();

        String externalPassword = "test456";
        String extAcctLocalPart1 = testName + "_1";
        String extAcctLocalPart2 = testName + "_2";
        String extAcctLocalPart3 = testName + "_3";
        createExternalAcctEntry(extAcctLocalPart1, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart2, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart3, externalPassword, null);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        // setup auto prov
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter,
                "(&(uid=%u)(mail=searchAutoProvDirectoryByConfiguredSearchFilter*))");
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        final Set<String> entriesFound = new HashSet<String>();
        DirectoryEntryVisitor visitor = new DirectoryEntryVisitor() {
            @Override
            public void visit(String dn, Map<String, Object> attrs) {
                entriesFound.add(dn);
            }
        };

        prov.searchAutoProvDirectory(zmailDomain, null, null,
                null, 0, visitor);

        assertEquals(3, entriesFound.size());
        assertTrue(entriesFound.contains(externalEntryDN(extAcctLocalPart1).toLowerCase()));
        assertTrue(entriesFound.contains(externalEntryDN(extAcctLocalPart2).toLowerCase()));
        assertTrue(entriesFound.contains(externalEntryDN(extAcctLocalPart3).toLowerCase()));
    }

    /* =================
     * EAGER mode tests
     * =================
     */
    @Test
    public void eagerMode() throws Exception {
        String testName = getTestName();

        final String externalPassword = "test456";

        int totalAccts = 4;
        String extAcctLocalPart1 = testName + "_1";
        String extAcctLocalPart2 = "abc";
        String extAcctLocalPart3 = testName + "_3";
        String extAcctLocalPart4 = testName + "_4";
        String extAcctLocalPart5 = testName + "_5";
        createExternalAcctEntry(extAcctLocalPart1, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart2, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart3, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart4, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart5, externalPassword, null);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        // setup auto prov
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs
                .put(Provisioning.A_zmailAutoProvLdapSearchFilter,
                        "(&(uid=%u)(mail=eagerMode*)" + AutoProvisionTestUtil.MarkEntryProvisionedListener.NOT_PROVED_FILTER + ")");
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvAccountNameMap, Provisioning.A_uid);

        // set batch size to a smaller number then num account matching the filter, 
        // so we hit the TOO_MANY_SEARCH_RESULTS (bug 66605)
        int batchSize = totalAccts - 1;
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvBatchSize, Integer.valueOf(batchSize).toString());
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvListenerClass,
                AutoProvisionTestUtil.MarkEntryProvisionedListener.class.getName());
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        // schedule the domain on local server
        prov.getLocalServer().addAutoProvScheduledDomains(zmailDomain.getName());

        EagerAutoProvisionScheduler scheduler = new EagerAutoProvisionScheduler() {
            @Override
            public boolean isShutDownRequested() {
                return false;
            }
        };

        prov.autoProvAccountEager(scheduler);

        List<Account> zmailAccts = prov.getAllAccounts(zmailDomain);
        assertEquals(batchSize, zmailAccts.size());

        // do it again, this time the 5th account should be provisioned
        prov.autoProvAccountEager(scheduler);
        zmailAccts = prov.getAllAccounts(zmailDomain);
        assertEquals(totalAccts, zmailAccts.size());

        Set<String> acctNames = new HashSet<String>();
        for (Account acct : zmailAccts) {
            acctNames.add(acct.getName());
        }

        assertTrue(acctNames.contains(TestUtil.getAddress(extAcctLocalPart1, zmailDomain.getName()).toLowerCase()));
        assertTrue(acctNames.contains(TestUtil.getAddress(extAcctLocalPart3, zmailDomain.getName()).toLowerCase()));
        assertTrue(acctNames.contains(TestUtil.getAddress(extAcctLocalPart4, zmailDomain.getName()).toLowerCase()));
        assertTrue(acctNames.contains(TestUtil.getAddress(extAcctLocalPart5, zmailDomain.getName()).toLowerCase()));

        // clear scheduled domains on the local server
        prov.getLocalServer().unsetAutoProvScheduledDomains();
    }

    private static class MockAutoProvisionThread extends AutoProvisionThread {

        @Override
        protected long getInitialDelay() {
            return 1L;
        }
    }

    @Test
    public void eagerModeTimer() throws Exception {
        Config config = prov.getConfig();
        config.setAutoProvPollingInterval("1000ms");
        String testName = getTestName();

        final String externalPassword = "test456";

        int totalAccts = 4;
        int batchSize = 1;
        String extAcctLocalPart1 = testName + "_11";
        String extAcctLocalPart2 = "zzz";
        String extAcctLocalPart3 = testName + "_13";
        String extAcctLocalPart4 = testName + "_14";
        String extAcctLocalPart5 = testName + "_15";
        createExternalAcctEntry(extAcctLocalPart1, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart2, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart3, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart4, externalPassword, null);
        createExternalAcctEntry(extAcctLocalPart5, externalPassword, null);

        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        // setup auto prov
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs
                .put(Provisioning.A_zmailAutoProvLdapSearchFilter,
                        "(&(uid=%u)(mail=eagerMode*)" + AutoProvisionTestUtil.MarkEntryProvisionedListener.NOT_PROVED_FILTER + ")");
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvAccountNameMap, Provisioning.A_uid);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvBatchSize, "" + batchSize);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvListenerClass,
                AutoProvisionTestUtil.MarkEntryProvisionedListener.class.getName());
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);

        // schedule the domain on local server
        prov.getLocalServer().addAutoProvScheduledDomains(zmailDomain.getName());

        new MockAutoProvisionThread().start();

        Thread.sleep(10000); //wait for eager thread to finish

        List<Account> zmailAccts = prov.getAllAccounts(zmailDomain);
        assertEquals(totalAccts, zmailAccts.size());

        Set<String> acctNames = new HashSet<String>();
        for (Account acct : zmailAccts) {
            acctNames.add(acct.getName());
        }

        assertTrue(acctNames.contains(TestUtil.getAddress(extAcctLocalPart1, zmailDomain.getName()).toLowerCase()));
        assertTrue(acctNames.contains(TestUtil.getAddress(extAcctLocalPart3, zmailDomain.getName()).toLowerCase()));
        assertTrue(acctNames.contains(TestUtil.getAddress(extAcctLocalPart4, zmailDomain.getName()).toLowerCase()));
        assertTrue(acctNames.contains(TestUtil.getAddress(extAcctLocalPart5, zmailDomain.getName()).toLowerCase()));

        // clear scheduled domains on the local server
        prov.getLocalServer().unsetAutoProvScheduledDomains();
    }
}
