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
package org.zmail.qa.unittest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import junit.framework.TestCase;

import org.zmail.common.util.CliUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.ZAttrProvisioning.GalMode;
import org.zmail.cs.gal.GalGroupMembers;
import org.zmail.cs.ldap.LdapUtil;
import org.zmail.qa.unittest.prov.soap.GalTestUtil;

/*
 * To run this test:
 * zmsoap -v -z RunUnitTestsRequest/test=org.zmail.qa.unittest.TestGalGroupMembers
 *
 */

public class TestGalGroupMembers extends TestCase {
    
    private static final String ZIMBRA_DOMAIN = "zmail.galgrouptest";
    private static final String ZIMBRA_GROUP = "zmail-group";
    
    private static final String EXTERNAL_DOMAIN = "external.galgrouptest";
    private static final String EXTERNAL_GROUP = "external-group";
    
    private static final String USER = "user";
        
    //////////////
    // TODO: remove once ZmailSuite supports JUnit 4 annotations.
    private static boolean initialized = false;
    private static boolean allDone = false;
    
    public void setUp() throws Exception {
        if (!initialized) {
            init();
            initialized = true;
        }
    }
    
    public void tearDown() throws Exception {
        if (allDone) {
            cleanup();
            initialized = false;
        }
    }
    //////////////
    
    enum ZmailGroupMembers {
        ZIMBRA_GROUP_MEMBER_ACCOUNT_1(true),
        ZIMBRA_GROUP_MEMBER_ACCOUNT_2(true),
        NON_ZIMBRA_ADDR_1(false),
        NON_ZIMBRA_ADDR_2(false);
        
        private boolean isZmailAccount;
        
        private ZmailGroupMembers(boolean isZmailAccount) {
            this.isZmailAccount = isZmailAccount;
        }
        
        private boolean isZmailAccount() {
            return isZmailAccount;
        }
        
        private String getAddress() {
            String localPart = toString().toLowerCase();
            return isZmailAccount ? TestUtil.getAddress(localPart, ZIMBRA_DOMAIN) :
                TestUtil.getAddress(localPart, "somedomain.com");
        }
        
        static String[] getAllMembersAsArray() {
            String[] members = new String[ZmailGroupMembers.values().length];
            for (ZmailGroupMembers member : ZmailGroupMembers.values()) {
                members[member.ordinal()] = member.getAddress();
            }
            
            return members;
        }
        
        static void assertEquals(Set<String> addrs) {
            Assert.assertEquals(ZmailGroupMembers.values().length, addrs.size());
            
            for (ZmailGroupMembers member : ZmailGroupMembers.values()) {
                Assert.assertTrue(addrs.contains(member.getAddress()));
                
                // verify that addrs should do case-insensitive comparison
                Assert.assertTrue(addrs.contains(member.getAddress().toUpperCase()));
                Assert.assertTrue(addrs.contains(member.getAddress().toLowerCase()));
            }
        }
    }

    enum ExternalGroupMembers {
        MEMBER_1,
        MEMBER_2,
        MEMBER_3,
        MEMBER_4;
        
        private String getAddress() {
            String localPart = toString().toLowerCase();
            return TestUtil.getAddress(localPart, EXTERNAL_DOMAIN);
        }
        
        static String[] getAllMembersAsArray() {
            String[] members = new String[ExternalGroupMembers.values().length];
            for (ExternalGroupMembers member : ExternalGroupMembers.values()) {
                members[member.ordinal()] = member.getAddress();
            }
            
            return members;
        }
        
        static void assertEquals(Set<String> addrs) {
            Assert.assertEquals(ExternalGroupMembers.values().length, addrs.size());
            
            for (ExternalGroupMembers member : ExternalGroupMembers.values()) {
                Assert.assertTrue(addrs.contains(member.getAddress()));
                
                // verify that addrs should do case-insensitive comparison
                Assert.assertTrue(addrs.contains(member.getAddress().toUpperCase()));
                Assert.assertTrue(addrs.contains(member.getAddress().toLowerCase()));
            }
        }
    }
    
    private static void setupZmailDomain() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        
        // create the zmail domain
        if (prov.get(Key.DomainBy.name, ZIMBRA_DOMAIN) == null) {
            ZmailLog.test.info("Creating domain " + ZIMBRA_DOMAIN);
            Domain domain = prov.createDomain(ZIMBRA_DOMAIN, new HashMap<String, Object>());
            
            // configure external GAL
            Map<String, Object> attrs = new HashMap<String, Object>();
            domain.setGalMode(GalMode.both, attrs);
            domain.addGalLdapURL("ldap://localhost:389", attrs);
            domain.setGalLdapBindDn("cn=config", attrs);
            domain.setGalLdapBindPassword("zmail");
            domain.setGalLdapSearchBase(LdapUtil.domainToDN(EXTERNAL_DOMAIN));
            domain.setGalAutoCompleteLdapFilter("zmailAccountAutoComplete");
            domain.setGalLdapFilter("zmailAccounts");
            
            prov.modifyAttrs(domain, attrs);
        }
        
        // create the test user
        String userAddr = TestUtil.getAddress(USER, ZIMBRA_DOMAIN);
        if (prov.get(AccountBy.name, userAddr) == null) {
            prov.createAccount(userAddr, "test123", null);
        }
        
        // create accounts in the zmail domain
        for (ZmailGroupMembers member : ZmailGroupMembers.values()) {
            if (member.isZmailAccount()) {
                String addr = member.getAddress();
                if (prov.get(AccountBy.name, addr) == null) {
                    prov.createAccount(addr, "test123", null);
                }
            }
        }
        
        // create zmail group and add members
        String groupAddr = TestUtil.getAddress(ZIMBRA_GROUP, ZIMBRA_DOMAIN);
        DistributionList group = prov.get(Key.DistributionListBy.name, groupAddr);
        if (group == null) {
            group = prov.createDistributionList(groupAddr, new HashMap<String, Object>());
            prov.addMembers(group, ZmailGroupMembers.getAllMembersAsArray());
        }
        
    }
    
    private static void cleanupZmailDomain() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        
        GalTestUtil.disableGalSyncAccount(prov, ZIMBRA_DOMAIN);
        
        // delete the test user
        String userAddr = TestUtil.getAddress(USER, ZIMBRA_DOMAIN);
        Account userAcct = prov.get(AccountBy.name, userAddr);
        if (prov.get(AccountBy.name, userAddr) != null) {
            prov.deleteAccount(userAcct.getId());
        }
        
        for (ZmailGroupMembers member : ZmailGroupMembers.values()) {
            if (member.isZmailAccount()) {
                String addr = member.getAddress();
                Account acct = prov.get(AccountBy.name, addr);
                if (acct != null) {
                    prov.deleteAccount(acct.getId());
                }
            }
        }
        
        String groupAddr = TestUtil.getAddress(ZIMBRA_GROUP, ZIMBRA_DOMAIN);
        DistributionList group = prov.get(Key.DistributionListBy.name, groupAddr);
        if (group != null) {
            prov.deleteDistributionList(group.getId());
        }
        
        Domain domain = prov.get(Key.DomainBy.name, ZIMBRA_DOMAIN);
        if (domain != null) {
            ZmailLog.test.info("Deleting domain " + ZIMBRA_DOMAIN);
            prov.deleteDomain(domain.getId());
        }
        
    }
    
    private static void setupExternalDomain() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        
        // create a domain to simulate entries in external GAL
        if (prov.get(Key.DomainBy.name, EXTERNAL_DOMAIN) == null) {
            ZmailLog.test.info("Creating domain " + EXTERNAL_DOMAIN);
            prov.createDomain(EXTERNAL_DOMAIN, new HashMap<String, Object>());
        }
        
        // create groups in the external domain
        String groupAddr = TestUtil.getAddress(EXTERNAL_GROUP, EXTERNAL_DOMAIN);
        DistributionList group = prov.get(Key.DistributionListBy.name, groupAddr);
        if (group == null) {
            group = prov.createDistributionList(groupAddr, new HashMap<String, Object>());
            prov.addMembers(group, ExternalGroupMembers.getAllMembersAsArray());
        }
    }
    
    private static void cleanupExternalDomain() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        
        String groupAddr = TestUtil.getAddress(EXTERNAL_GROUP, EXTERNAL_DOMAIN);
        DistributionList group = prov.get(Key.DistributionListBy.name, groupAddr);
        if (group != null) {
            prov.deleteDistributionList(group.getId());
        }
        
        Domain domain = prov.get(Key.DomainBy.name, EXTERNAL_DOMAIN);
        if (domain != null) {
            ZmailLog.test.info("Deleting domain " + EXTERNAL_DOMAIN);
            prov.deleteDomain(domain.getId());
        }
    }
    
    @BeforeClass
    public static void init() throws Exception {
        // TestUtil.cliSetup();
        // CliUtil.toolSetup();
        
        setupZmailDomain();
        setupExternalDomain();
    }
    
    @AfterClass
    public static void cleanup() throws Exception {
        cleanupZmailDomain();
        cleanupExternalDomain();
    }
    
    private void doTest() throws Exception {
        String userAddr = TestUtil.getAddress(USER, ZIMBRA_DOMAIN);
        Account acct = Provisioning.getInstance().get(AccountBy.name, userAddr);
        
        Set<String> zmailGroupMembers = GalGroupMembers.getGroupMembers(TestUtil.getAddress(ZIMBRA_GROUP, ZIMBRA_DOMAIN), acct);
        ZmailGroupMembers.assertEquals(zmailGroupMembers);
        
        Set<String> externalGroupMembers = GalGroupMembers.getGroupMembers(TestUtil.getAddress(EXTERNAL_GROUP, EXTERNAL_DOMAIN), acct);
        ExternalGroupMembers.assertEquals(externalGroupMembers);
    }
    
    @Test
    public void testLdapSearch() throws Exception {
        GalTestUtil.disableGalSyncAccount(Provisioning.getInstance(), ZIMBRA_DOMAIN);
        doTest();
    }
    
    @Test
    public void testGSASearch() throws Exception {
        GalTestUtil.enableGalSyncAccount(Provisioning.getInstance(), ZIMBRA_DOMAIN, GalTestUtil.GSAType.both);
        doTest();
    }
    
    // TODO: remove once ZmailSuite supports JUnit 4 annotations. 
    @Test
    public void testLast() throws Exception {
        allDone = true;
    }

}
