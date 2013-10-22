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

package org.zmail.qa.unittest;

import com.google.common.collect.Maps;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Identity;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Provisioning.CountAccountResult;
import org.zmail.cs.account.Provisioning.RightsDoc;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.Right;
import org.zmail.cs.account.accesscontrol.RightClass;
import org.zmail.cs.account.accesscontrol.RightCommand.AllEffectiveRights;
import org.zmail.cs.account.accesscontrol.RightCommand.EffectiveRights;
import org.zmail.cs.account.auth.AuthContext;
import org.zmail.cs.account.soap.SoapAccountInfo;
import org.zmail.cs.account.soap.SoapProvisioning;
import org.zmail.cs.account.soap.SoapProvisioning.MailboxInfo;
import org.zmail.cs.account.soap.SoapProvisioning.QuotaUsage;
import org.zmail.cs.account.soap.SoapProvisioning.ReIndexInfo;
import org.zmail.cs.account.soap.SoapProvisioning.VerifyIndexResult;
import org.zmail.soap.admin.message.GetLicenseInfoRequest;
import org.zmail.soap.admin.message.GetLicenseInfoResponse;
import org.zmail.soap.admin.message.GetServerNIfsRequest;
import org.zmail.soap.admin.message.GetServerNIfsResponse;
import org.zmail.soap.admin.message.GetVersionInfoRequest;
import org.zmail.soap.admin.message.GetVersionInfoResponse;
import org.zmail.soap.admin.type.CacheEntryType;
import org.zmail.soap.admin.type.LicenseExpirationInfo;
import org.zmail.soap.admin.type.NetworkInformation;
import org.zmail.soap.admin.type.ServerSelector;
import org.zmail.soap.admin.type.VersionInfo;
import junit.framework.TestCase;
import org.zmail.soap.type.TargetBy;

import java.util.List;
import java.util.Map;

/**
 * Primary focus of these tests is to ensure that Jaxb objects work, in
 * particular where SoapProvisioning uses them
 */
public class TestJaxbProvisioning extends TestCase {

    private SoapProvisioning prov = null;

    private final static String testAcctDomainName =
            "jaxb.acct.domain.example.test";
    private final static String testServer = "jaxb.server.example.test";
    private final static String testAcctEmail = "jaxb1@" + testAcctDomainName;
    private final static String testAcctAlias = "alias_4_jaxb1@" + testAcctDomainName;
    private final static String testAcctIdentity = "identity_4_jaxb1@" + testAcctDomainName;
    private final static String testNewAcctEmail = "new_jaxb1@" + testAcctDomainName;
    private final static String testCalResDomain = "jaxb.calr.domain.example.test";
    private final static String testCalRes = "jaxb1@" + testCalResDomain;
    private final static String testNewCalRes = "new_jaxb1@" + testCalResDomain;
    private final static String testCalResDisplayName = "JAXB Test CalResource";
    private final static String testDlDomain = "jaxb.dl.domain.example.test";
    private final static String testDl = "jaxb_dl1@" + testDlDomain;
    private final static String parentDl = "jaxb_parentdl@" + testDlDomain;
    private final static String testDlAlias = "alias_4_jaxb_dl1@" + testDlDomain;
    private final static String testDlNewName = "new_jaxb_dl1@" + testDlDomain;
    private final static String testCosDomain = "jaxb.cos.domain.example.test";
    private final static String testCos = "jaxb_cos@" + testDlDomain;
    private final static String testCosCopy = "jaxb_cos_copy@" + testDlDomain;
    private final static String testNewCos = "new_jaxb_cos@" + testDlDomain;

    public void init() throws Exception {
        oneTimeTearDown();
    }

    public void oneTimeTearDown() {
        ZmailLog.test.info("in TestJaxbProvisioning oneTimeTearDown");
    }

    @Override
    public void setUp() throws Exception {
        prov = TestUtil.newSoapProvisioning();
        tearDown();
    }

    @Override
    public void tearDown() throws Exception {
        ZmailLog.test.debug("in TestJaxbProvisioning tearDown");
        if (prov == null)
            prov = TestUtil.newSoapProvisioning();
        TestUtil.deleteAccount(testAcctEmail);
        TestUtil.deleteAccount(testNewAcctEmail);
        deleteCalendarResourceIfExists(testCalRes);
        deleteDlIfExists(testDl);
        deleteDlIfExists(parentDl);
        deleteDlIfExists(testDlNewName);
        deleteCosIfExists(testCos);
        deleteCosIfExists(testNewCos);
        deleteCosIfExists(testCosCopy);
        deleteDomainIfExists(testAcctDomainName);
        deleteDomainIfExists(testCalResDomain);
        deleteDomainIfExists(testDlDomain);
        deleteDomainIfExists(testCosDomain);
        deleteServerIfExists(testServer);
    }

    public void deleteDomainIfExists(String name) {
        try {
            ZmailLog.test.debug(
                    "Deleting domain " + name);
            Domain res = prov.get(Key.DomainBy.name, name);
            if (res != null)
                prov.deleteDomain(res.getId());
        } catch (Exception ex) {
            ZmailLog.test.error("Problem deleting domain " + name, ex);
        }
    }

    public void deleteServerIfExists(String name) {
        try {
            ZmailLog.test.debug(
                    "Deleting server " + name);
            Server res = prov.get(Key.ServerBy.name, name);
            if (res != null)
                prov.deleteServer(res.getId());
        } catch (Exception ex) {
            ZmailLog.test.error("Problem deleting server " + name, ex);
        }
    }

    public void deleteCalendarResourceIfExists(String name) {
        try {
            ZmailLog.test.debug(
                    "Deleting CalendarResource " + name);
            CalendarResource res = prov.get(Key.CalendarResourceBy.name, name);
            if (res != null)
                prov.deleteDomain(res.getId());
        } catch (Exception ex) {
            ZmailLog.test.error("Problem deleting Calendar Resource " +
                    name, ex);
        }
    }

    public void deleteDlIfExists(String name) {
        try {
            ZmailLog.test.debug(
                    "Deleting DL " + name);
            DistributionList res = prov.get(Key.DistributionListBy.name, name);
            if (res != null)
                prov.deleteDistributionList(res.getId());
        } catch (Exception ex) {
            ZmailLog.test.error("Problem deleting Distribution List " + name, ex);
        }
    }

    public void deleteCosIfExists(String name) {
        try {
            ZmailLog.test.debug("Deleting COS " + name);
            Cos res = prov.get(Key.CosBy.name, name);
            if (res != null)
                prov.deleteCos(res.getId());
        } catch (Exception ex) {
            ZmailLog.test.error("Problem deleting Cos " + name, ex);
        }
    }

    public Domain ensureDomainExists(String name)
    throws Exception {
        Domain dom = prov.get(Key.DomainBy.name, name);
        if (dom == null) {
            ZmailLog.test.debug("ensureDomainExists didn't exist - creating new domain=" + name);
            dom = prov.createDomain(name, null);
        }
        if (dom == null) {
            ZmailLog.test.debug("ensureDomainExists returning null!!!");
        } else {
            ZmailLog.test.debug("ensureDomainExists Returning=" + dom.getName() + " Id=" + dom.getId());
        }
        return dom;
    }

    public Account ensureAccountExists(String name)
    throws Exception {
        String domainName = name.substring(name.indexOf('@') + 1);
        ensureDomainExists(domainName);
        Account acct = prov.get(AccountBy.name, name);
        if (acct == null)
            acct = TestUtil.createAccount(name);
        if (acct == null) {
            ZmailLog.test.debug("ensureAccountExists returning null!!!");
        } else {
            ZmailLog.test.debug("ensureAccountExists Returning Account=" + acct.getName() + " Id=" + acct.getId());
        }
        return acct;
    }

    public Account ensureMailboxExists(String name)
    throws Exception {
        Account acct = ensureAccountExists(name);
        if (acct == null) {
            ZmailLog.test.debug("ensureMailboxExists returning null!!!");
        } else {
            // The act of getting a mailbox is sufficient to create it if the associated account exists.
            // Note that prov.getAccount() USED TO implicitly created a mailbox even though it was not really
            // supposed to and this routine used to rely on that.
            MailboxInfo mboxInfo = prov.getMailbox(acct);
            ZmailLog.test.debug("ensureMailboxExists Returning Mailbox=" + mboxInfo.getMailboxId() +
                    " Account=" + acct.getName() + " Id=" + acct.getId());
        }
        return acct;
    }

    public DistributionList ensureDlExists(String name)
    throws Exception {
        String domainName = name.substring(name.indexOf('@') + 1);
        ensureDomainExists(domainName);
        DistributionList dl = prov.get(Key.DistributionListBy.name, name);
        if (dl == null)
            dl = prov.createDistributionList(name, null);
        return dl;
    }

    public Cos ensureCosExists(String name)
    throws Exception {
        String domainName = name.substring(name.indexOf('@') + 1);
        ensureDomainExists(domainName);
        Cos cos = prov.get(Key.CosBy.name, name);
        if (cos == null)
            cos = prov.createCos(name, null);
        return cos;
    }

    public void testConfig() throws Exception {
        ZmailLog.test.debug("Starting testConfig");
        Config cfg = prov.getConfig();
        assertNotNull("Config" , cfg);
        cfg = prov.getConfig("zmail_user");
        assertNotNull("Config" , cfg);
    }

    public void testServer() throws Exception {
        ZmailLog.test.debug("Starting testServer");
        Domain dom = ensureDomainExists(testServer);
        assertNotNull("Domain for " + testAcctDomainName, dom);
        Server svr = prov.createServer(testServer, null);
        assertNotNull("Server for " + testServer, svr);
        svr = prov.get(Key.ServerBy.id, svr.getId());
        List <Server> svrs = prov.getAllServers();
        assertNotNull("All Servers" , svrs);
        assertTrue("Number of Servers objects=" + svrs.size() +
                " should be >=1", svrs.size() >= 1);
        prov.deleteServer(svr.getId());
   }

    public void testAccount() throws Exception {
        ZmailLog.test.debug("Starting testAccount");
        Domain dom = ensureDomainExists(testAcctDomainName);
        assertNotNull("Domain for " + testAcctDomainName, dom);
        Account acct = prov.createAccount(testAcctEmail,
                TestUtil.DEFAULT_PASSWORD, null);
        prov.authAccount(acct, TestUtil.DEFAULT_PASSWORD,
                AuthContext.Protocol.test);
        assertNotNull("Account for " + testAcctEmail, acct);
        prov.changePassword(acct, TestUtil.DEFAULT_PASSWORD, "DelTA4Pa555");
        prov.checkPasswordStrength(acct, "2ndDelTA4Pa555");
        prov.setPassword(acct, "2ndDelTA4Pa555");
        prov.renameAccount(acct.getId(), testNewAcctEmail);
        prov.addAlias(acct, testAcctAlias);
        prov.removeAlias(acct, testAcctAlias);
        acct = prov.get(AccountBy.name, testNewAcctEmail);
        assertNotNull("Account for " + testNewAcctEmail, acct);
        SoapAccountInfo acctInfo = prov.getAccountInfo(AccountBy.id, acct.getId());
        assertNotNull("SoapAccountInfo for " + testNewAcctEmail, acctInfo);
        acct = prov.get(AccountBy.name, testNewAcctEmail, true);
        assertNotNull("2nd Account for " + testNewAcctEmail, acct);
        List <Account> adminAccts = prov.getAllAdminAccounts(true);
        assertNotNull("Admin Accounts" , adminAccts);
        assertTrue("Number of Admin Account objects=" + adminAccts.size() +
                " should be >=1", adminAccts.size() >= 1);
        List <Account> accts = prov.getAllAccounts(dom);
        assertNotNull("All Accounts" , accts);
        assertTrue("Number of Account objects=" + accts.size() +
                " should be >=1", accts.size() >= 1);
        List <Domain> domains = prov.getAllDomains(false);
        assertNotNull("All Domains" , domains);
        assertTrue("Number of Domain objects=" + domains.size() +
                " should be >=1", domains.size() >= 1);
        dom = prov.get(Key.DomainBy.id, dom.getId());
        assertNotNull("Domain got by id" , dom);
        CountAccountResult res = prov.countAccount(dom);
        assertNotNull("CountAccountResult", res);
        dom = prov.getDomainInfo(Key.DomainBy.id, dom.getId());
        assertNotNull("DomainInfo got by id" , dom);

        prov.deleteAccount(acct.getId());
   }

    public void testMailbox() throws Exception {
        ZmailLog.test.debug("Starting testMailbox");
        Domain dom = ensureDomainExists(testAcctDomainName);
        assertNotNull("Domain for " + testAcctDomainName, dom);
        Account acct = prov.createAccount(testAcctEmail,
                TestUtil.DEFAULT_PASSWORD, null);
        acct = ensureMailboxExists(testAcctEmail);
        assertNotNull("Account for " + testAcctEmail, acct);
        MailboxInfo mbx = prov.getMailbox(acct);
        assertNotNull("MailboxInfo for Account=" + testAcctEmail, mbx);
        prov.deleteAccount(acct.getId());
   }

    public void testCos() throws Exception {
        ZmailLog.test.debug("Starting testCos");
        Domain dom = ensureDomainExists(testCosDomain);
        assertNotNull("Domain for " + testCosDomain, dom);
        Cos cos = prov.createCos(testCos, null);
        assertNotNull("Cos for " + testCos, cos);
        prov.renameCos(cos.getId(), testNewCos);
        prov.copyCos(cos.getId(), testCosCopy);
        List <Cos> cosList = prov.getAllCos();
        assertNotNull("All Cos" , cosList);
        assertTrue("Number of Cos objects=" + cosList.size() +
                " should be >=1", cosList.size() >= 1);
        prov.deleteCos(cos.getId());
        cos = prov.get(Key.CosBy.name, testCosCopy);
        prov.deleteCos(cos.getId());
   }

    public void testDistributionList() throws Exception {
        ZmailLog.test.debug("Starting testDistributionList");
        Domain dom = ensureDomainExists(testDlDomain);
        assertNotNull("Domain for " + testDlDomain, dom);
        deleteDlIfExists(testDl);
        deleteDlIfExists(parentDl);
        DistributionList dl = prov.createDistributionList(testDl, null);
        assertNotNull("DistributionList for " + testDl, dl);
        prov.renameDistributionList(dl.getId(), testDlNewName);
        prov.addAlias(dl, testDlAlias);
        dl = prov.get(Key.DistributionListBy.name, testDlAlias);
        prov.removeAlias(dl, testDlAlias);
        String[] members = { "one@example.com",
                "two@example.test", "three@example.net" };
        String[] rmMembers = { "two@example.test", "three@example.net" };
        prov.addMembers(dl, members);
        prov.removeMembers(dl, rmMembers);

        // DL Membership test
        DistributionList dadDl = prov.createDistributionList(parentDl, null);
        assertNotNull("DistributionList for " + parentDl, dadDl);
        String[] dlMembers = { "one@example.com", testDlNewName };
        prov.addMembers(dadDl, dlMembers);
        Map <String, String> via = Maps.newHashMap();
        List <DistributionList> containingDls =
            prov.getDistributionLists(dl, false, via);
        assertEquals("Number of DLs a DL is a member of", 1,
                containingDls.size());

        // Account Membership test
        Account acct = ensureMailboxExists(testAcctEmail);
        String[] dlAcctMembers = { testAcctEmail };
        prov.addMembers(dadDl, dlAcctMembers);
        containingDls =
            prov.getDistributionLists(acct, false, via);
        assertEquals("Number of DLs an acct is a member of", 1,
                containingDls.size());

        List <DistributionList> dls = prov.getAllDistributionLists(dom);
        assertNotNull("All DLs" , dls);
        assertTrue("Number of DL objects=" + dls.size() +
                " should be >=2", dls.size() >= 2);
        prov.deleteDistributionList(dadDl.getId());
        prov.deleteDistributionList(dl.getId());
   }

    public void testCalendarResource() throws Exception {
        ZmailLog.test.debug("Starting testCalendarResource");
        deleteCalendarResourceIfExists(testCalRes);
        deleteDomainIfExists(testCalResDomain);
        Domain dom = prov.createDomain(testCalResDomain, null);
        assertNotNull("Domain for " + testAcctDomainName, dom);
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put("displayName", testCalResDisplayName);
        attrs.put ("zmailCalResType", "Location");
        attrs.put("zmailCalResLocationDisplayName", "Harare");
        CalendarResource calRes = prov.createCalendarResource(
                testCalRes, TestUtil.DEFAULT_PASSWORD, attrs);
        assertNotNull("CalendarResource on create", calRes);
        prov.renameCalendarResource(calRes.getId(), testNewCalRes);
        List <CalendarResource> resources = prov.getAllCalendarResources(
                dom, Provisioning.getInstance().getLocalServer());
        assertNotNull("CalendarResource List for getAll", resources);
        assertEquals("CalendarResource list size", 1, resources.size());
        calRes = prov.get(Key.CalendarResourceBy.id, calRes.getId());
        prov.deleteCalendarResource(calRes.getId());
    }

    public void testQuotaUsage() throws Exception {
        ZmailLog.test.debug("Starting testQuotaUsage");
        List <QuotaUsage> quotaUsages = prov.getQuotaUsage(
                Provisioning.getInstance().getLocalServer().getName());
        assertNotNull("QuotaUsage List", quotaUsages);
        assertTrue("Number of QuotaUsage objects=" + quotaUsages.size() +
                " should be >1", quotaUsages.size() > 1);
    }

    // Disabled - getting :
    // SoapFaultException: system failure: server
    //    gren-elliots-macbook-pro.local zmailRemoteManagementPrivateKeyPath
    //    (/opt/zmail/.ssh/zmail_identity) does not exist
    public void DISABLED_testGetServerNIfs() throws Exception {
        ZmailLog.test.debug("Starting testGetServerNIfs");
        Server svr = Provisioning.getInstance().getLocalServer();
        GetServerNIfsRequest req = new GetServerNIfsRequest(
                null, ServerSelector.fromId(svr.getId()));
        GetServerNIfsResponse resp = prov.invokeJaxb(req);
        assertNotNull("GetServerNIfsResponse", resp);
        List <NetworkInformation> nisList = resp.getNetworkInterfaces();
        assertNotNull("NetworkInfomation List", nisList);
    }

    public void testLicenseInfo() throws Exception {
        ZmailLog.test.debug("Starting testLicenseInfo");
        GetLicenseInfoRequest req = new GetLicenseInfoRequest();
        GetLicenseInfoResponse resp = prov.invokeJaxb(req);
        assertNotNull("GetLicensInfoResponse", resp);
        LicenseExpirationInfo expires = resp.getExpiration();
        assertNotNull("Expiration Info", expires);
        assertNotNull("getDate result", expires.getDate());
    }

    public void testVersionInfo() throws Exception {
        ZmailLog.test.debug("Starting testVersionInfo");
        GetVersionInfoRequest req = new GetVersionInfoRequest();
        GetVersionInfoResponse resp = prov.invokeJaxb(req);
        assertNotNull("GetLicensInfoResponse", resp);
        VersionInfo info = resp.getInfo();
        assertNotNull("VersionInfo", info);
        info.getType();  // Don't care whether null or not
        assertNotNull("getVersion result", info.getVersion());
        assertNotNull("getRelease result", info.getRelease());
        assertNotNull("getBuildDate result", info.getBuildDate());
        assertNotNull("getHost result", info.getHost());
        assertNotNull("getMajorVersion result", info.getMajorVersion());
        assertNotNull("getMinorVersion result", info.getMinorVersion());
        assertNotNull("getMicroVersion result", info.getMicroVersion());
        assertNotNull("getPlatform result", info.getPlatform());
        assertNotNull("getBuildDate result", info.getBuildDate());
    }

    public void testIndex() throws Exception {
        ZmailLog.test.debug("Starting testIndex");
        Account acct = ensureMailboxExists(testAcctEmail);
        ReIndexInfo info = prov.reIndex(acct, "start", null, null);
        assertNotNull("ReIndexInfo", info);
        assertNotNull("getStatus result", info.getStatus());
        // Progress can be null.
        // Progress prog = info.getProgress();
        VerifyIndexResult ndxRes = prov.verifyIndex(acct);
        assertNotNull("VerifyIndexResult", ndxRes);
        prov.deleteMailbox(acct.getId());
    }

    public void testMboxCounts() throws Exception {
        ZmailLog.test.debug("Starting testMboxCounts");
        Account acct = ensureMailboxExists(testAcctEmail);
        long quotaUsed = prov.recalculateMailboxCounts(acct);
        assertTrue("quota used=" + quotaUsed + " should be >= =", quotaUsed >= 0);
    }

    public void testFlushCache() throws Exception {
        ZmailLog.test.debug("Starting testFlushCache");
        ensureDomainExists(testAcctDomainName);
        prov.flushCache(CacheEntryType.domain, null);
    }

    public void testGetAllRights() throws Exception {
        ZmailLog.test.debug("Starting testGetAllRights");
        List<Right> rights = prov.getAllRights("account" /* targetType */,
                true /* expandAllAttrs */, "USER" /* rightClass */);
        assertNotNull("getAllRight returned list", rights);
        assertTrue("Number of rights objects=" + rights.size() +
                " should be > 3", rights.size() > 3);
    }

    public void testGetAllEffectiveRights() throws Exception {
        ZmailLog.test.debug("Starting testGetAllEffectiveRights");
        AllEffectiveRights aer = prov.getAllEffectiveRights(null, null, null,
                false /* expandSetAttrs */, true /* expandGetAttrs */);
        assertNotNull("AllEffectiveRights", aer);
    }

    public void testGetEffectiveRights() throws Exception {
        ZmailLog.test.debug("Starting testGetEffectiveRights");
        EffectiveRights er = prov.getEffectiveRights("account" /* targetType */,
                TargetBy.name /* targetBy */, "admin" /* target */,
                Key.GranteeBy.name /* granteeBy */, "admin" /* grantee */,
                true /* expandSetAttrs */, true /* expandGetAttrs */);
        assertNotNull("EffectiveRights", er);
    }

    public void testGetRightsDoc() throws Exception {
        ZmailLog.test.debug("Starting testGetRightsDoc");
        Map<String, List<RightsDoc>> map = prov.getRightsDoc(null);
        assertTrue("Map size=" + map.size() +
                " should be >= 1", map.size() >= 1);
        String[] pkgs = { "org.zmail.cs.service.admin" };
        map = prov.getRightsDoc(pkgs);
        assertEquals("Map for specified set of pkgs", 1, map.size());
        boolean seenTstRight = false;
        for (String key : map.keySet()) {
            assertEquals("key to map", pkgs[0], key);
            for (RightsDoc rightsDoc : map.get(key)) {
                assertNotNull("rightsDoc cmd name", rightsDoc.getCmd());
                if (rightsDoc.getCmd().equals("AddAccountAliasRequest")) {
                    seenTstRight = true;
                    assertEquals("Notes number", 3, rightsDoc.getNotes().size());
                    assertEquals("Rights number", 3, rightsDoc.getRights().size());
                }
            }
        }
        assertTrue("AddAccountAliasRequest right in report", seenTstRight);
    }

    public void testGetRight() throws Exception {
        ZmailLog.test.debug("Starting testGetRight");
        Right right = prov.getRight("adminConsoleAccountRights", true);
        assertNotNull("Right", right);
        RightClass rightClass = right.getRightClass();
        assertEquals("right RightClass", rightClass, RightClass.ADMIN);
        assertEquals("right Name", "adminConsoleAccountRights", right.getName());
    }

    public void testHealth() throws Exception {
        ZmailLog.test.debug("Starting testHealth");
        assertTrue(prov.healthCheck());
    }

    public void testIdentities() throws Exception {
        ZmailLog.test.debug("Starting testIdentities");
        Account acct = ensureAccountExists(testAcctEmail);
        List<Identity> identities = prov.getAllIdentities(acct);
        assertEquals("Number of identities for new acct", 1, identities.size());
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put("zmailPrefFromAddress", testAcctIdentity);
        Identity newId = prov.createIdentity(acct, "altIdentity", attrs);
        assertNotNull("New identity", newId);
        identities = prov.getAllIdentities(acct);
        assertEquals("Number of identities after add", 2, identities.size());
        prov.deleteIdentity(acct, "altIdentity");
        identities = prov.getAllIdentities(acct);
        assertEquals("Number of identities after delete", 1, identities.size());
    }

    public static void main(String[] args)
    throws Exception {
        TestUtil.cliSetup();
        TestUtil.runTest(TestJaxbProvisioning.class);
    }
}
