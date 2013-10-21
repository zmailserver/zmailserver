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
package org.zmail.qa.unittest.prov.soap;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.ZAttrProvisioning.AutoProvAuthMech;
import org.zmail.common.account.ZAttrProvisioning.AutoProvMode;
import org.zmail.common.httpclient.HttpClientUtil;
import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.SoapHttpTransport;
import org.zmail.common.soap.SoapTransport;
import org.zmail.common.util.Constants;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.PreAuthKey;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.auth.AuthMechanism.AuthMech;
import org.zmail.cs.ldap.LdapException;
import org.zmail.cs.ldap.LdapUtil;
import org.zmail.qa.QA.Bug;
import org.zmail.qa.unittest.TestPreAuthServlet;
import org.zmail.qa.unittest.TestUtil;
import org.zmail.qa.unittest.prov.AutoProvisionTestUtil;
import org.zmail.qa.unittest.prov.Verify;
import org.zmail.soap.admin.message.AutoProvAccountRequest;
import org.zmail.soap.admin.message.AutoProvAccountResponse;
import org.zmail.soap.admin.message.AutoProvTaskControlRequest;
import org.zmail.soap.admin.message.AutoProvTaskControlResponse;
import org.zmail.soap.admin.message.AutoProvTaskControlRequest.Action;
import org.zmail.soap.admin.message.AutoProvTaskControlResponse.Status;
import org.zmail.soap.admin.type.AccountInfo;
import org.zmail.soap.admin.type.CountObjectsType;
import org.zmail.soap.admin.type.DomainSelector;
import org.zmail.soap.admin.type.PrincipalSelector;
import org.zmail.soap.type.AutoProvPrincipalBy;

public class TestAutoProvision extends SoapTest {
    
    private static SoapProvTestUtil provUtil;
    private static Provisioning prov;
    private static Domain extDomain;
    private static String extDomainDn;
    private static String extDomainAdminBindDn = LC.zmail_ldap_userdn.value();
    private static String extDomainAdminBindPassword = LC.zmail_ldap_password.value();
    
    private static String DEFAULT_AUTOPROV_INITIAL_SLEEP_MS = String.valueOf(5 * Constants.MILLIS_PER_MINUTE);
    private static String DEFAULT_AUTOPROV_POLLING_INTERVAL = "15m";
    
    @BeforeClass
    public static void init() throws Exception {
        provUtil = new SoapProvTestUtil();
        prov = provUtil.getProv();
        extDomain = provUtil.createDomain("external." + baseDomainName());
        extDomainDn = LdapUtil.domainToDN(extDomain.getName());
        
        revertAllToDefault();
    }
    
    @AfterClass
    public static void cleanup() throws Exception {
        Cleanup.deleteAll(baseDomainName());
        revertAllToDefault();
    }
    
    // put everything back, in case the test did not run through previously
    private static void revertAllToDefault() throws Exception {
        
        SoapTransport transport = authZmailAdmin();
        modifyLocalconfigAndReload(transport, LC.autoprov_initial_sleep_ms, DEFAULT_AUTOPROV_INITIAL_SLEEP_MS);
        
        Server localServer = prov.getLocalServer();
        localServer.setAutoProvPollingInterval(DEFAULT_AUTOPROV_POLLING_INTERVAL);
        localServer.unsetAutoProvScheduledDomains();
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
    
    private Map<String, Object> commonZmailDomainAttrs() {
        return AutoProvisionTestUtil.commonZmailDomainAttrs();
    }
    
    private void verifyAcctAutoProvisioned(Account acct, String expectedAcctName) 
    throws Exception {
        AutoProvisionTestUtil.verifyAcctAutoProvisioned(
                acct, expectedAcctName);
    }
    
    private String getAuthTokenAcctId(String authToken) throws Exception {
        Map attrs = AuthToken.getInfo(authToken);
        String zmailId = (String) attrs.get("id");  // hardcode id here, C_ID in ZmailAuthToken is private
        assertNotNull(zmailId);
        return zmailId;
    }
    
    
    /* ========================
     * SOAP and servlets tests
     * ========================
     */
    
    /*
     * Note: need to restart server each time before re-run.  Otherwise server would still 
     * have the account created in previous run cached.
     */
    @Test
    public void authRequestByPassword() throws Exception {
        String testName = getTestName();
        
        String externalPassword = "test456";
        String extAcctLocalPart = testName;
        String extAcctName = createExternalAcctEntry(extAcctLocalPart, externalPassword, null);
        
        Map<String, Object> zmailDomainAttrs = commonZmailDomainAttrs();
        // setup auto prov
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");
        // setup external LDAP auth
        zmailDomainAttrs.put(Provisioning.A_zmailAuthMech, AuthMech.ldap.name());
        zmailDomainAttrs.put(Provisioning.A_zmailAuthLdapURL, "ldap://localhost:389");
        zmailDomainAttrs.put(Provisioning.A_zmailAuthLdapBindDn, "uid=%u,ou=people," + extDomainDn);
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);
        
        String loginName = extAcctLocalPart + "@" + zmailDomain.getName();
        
        // make the soap request
        SoapHttpTransport transport = new SoapHttpTransport(TestUtil.getSoapUrl());
        
        Element request = Element.create(transport.getRequestProtocol(), AccountConstants.AUTH_REQUEST);
        request.addElement(AccountConstants.E_ACCOUNT).addAttribute(AccountConstants.A_BY, AccountBy.name.name()).setText(loginName);
        request.addElement(AccountConstants.E_PASSWORD).setText(externalPassword);
        
        Element response = transport.invoke(request);
        
        String encodedAuthToken = response.getElement(AccountConstants.E_AUTH_TOKEN).getText();
        assertNotNull(encodedAuthToken);
        String acctId = getAuthTokenAcctId(encodedAuthToken);
        Account acct = prov.get(AccountBy.id, acctId);
        verifyAcctAutoProvisioned(acct, loginName.toLowerCase());
    }
  

    /*
     * Note: need to restart server each time before re-run.  Otherwise server would still 
     * have the account created in previous run cached.
     */
    @Test
    public void authRequestByPreauth() throws Exception {
        String testName = getTestName();
        
        String externalPassword = "test456";
        String extAcctLocalPart = testName;
        String extAcctName = createExternalAcctEntry(extAcctLocalPart, externalPassword, null);
        
        Map<String, Object> zmailDomainAttrs = commonZmailDomainAttrs();
        // setup auto prov
        // commonZmailDomainAttrs added only LDAP, add preauth here
        StringUtil.addToMultiMap(zmailDomainAttrs, Provisioning.A_zmailAutoProvAuthMech, AutoProvAuthMech.PREAUTH.name());
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");
        // setup external LDAP auth
        zmailDomainAttrs.put(Provisioning.A_zmailAuthMech, AuthMech.ldap.name());
        zmailDomainAttrs.put(Provisioning.A_zmailAuthLdapURL, "ldap://localhost:389");
        zmailDomainAttrs.put(Provisioning.A_zmailAuthLdapBindDn, "uid=%u,ou=people," + extDomainDn);
        // setup preauth
        String preAuthKey = PreAuthKey.generateRandomPreAuthKey();
        zmailDomainAttrs.put(Provisioning.A_zmailPreAuthKey, preAuthKey);
        
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);
        
        String loginName = extAcctLocalPart + "@" + zmailDomain.getName();
        
        // preauth data
        HashMap<String,String> params = new HashMap<String,String>();
        String authBy = "name";
        long timestamp = System.currentTimeMillis();
        long expires = 0;
        
        params.put("account", loginName);
        params.put("by", authBy);
        params.put("timestamp", timestamp+"");
        params.put("expires", expires+"");

        String preAuth = PreAuthKey.computePreAuth(params, preAuthKey);
        
            
        // make the soap request
        SoapHttpTransport transport = new SoapHttpTransport(TestUtil.getSoapUrl());
        
        Element request = Element.create(transport.getRequestProtocol(), AccountConstants.AUTH_REQUEST);
        request.addElement(AccountConstants.E_ACCOUNT).addAttribute(AccountConstants.A_BY, authBy).setText(loginName);
        Element ePreAuth = request.addElement(AccountConstants.E_PREAUTH).setText(preAuth);
        ePreAuth.addAttribute(AccountConstants.A_TIMESTAMP, timestamp);
        ePreAuth.addAttribute(AccountConstants.A_EXPIRES, expires);
        
        Element response = transport.invoke(request);
        
        String encodedAuthToken = response.getElement(AccountConstants.E_AUTH_TOKEN).getText();
        assertNotNull(encodedAuthToken);
        String acctId = getAuthTokenAcctId(encodedAuthToken);
        Account acct = prov.get(AccountBy.id, acctId);
        verifyAcctAutoProvisioned(acct, loginName.toLowerCase());
    }
    
    /*
     * Note: need to restart server each time before re-run.  Otherwise server would still 
     * have the account created in previous run cached.
     */
    @Test
    @Ignore // need to setup KDC
    public void authRequestByKrb5() throws Exception {
        String testName = getTestName();
        
        String externalPassword = "test456";
        String extAcctLocalPart = testName;
        String extAcctName = createExternalAcctEntry(extAcctLocalPart, externalPassword, null);
        
        String krb5Realm = "MYREALM";
        
        Map<String, Object> zmailDomainAttrs = commonZmailDomainAttrs();
        // setup auto prov
        StringUtil.addToMultiMap(zmailDomainAttrs, Provisioning.A_zmailAutoProvAuthMech, AutoProvAuthMech.KRB5.name());
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");
        // setup auth mech and krb5 realm on domain
        zmailDomainAttrs.put(Provisioning.A_zmailAuthMech, AuthMech.kerberos5.name());
        zmailDomainAttrs.put(Provisioning.A_zmailAuthKerberos5Realm, krb5Realm);
        zmailDomainAttrs.put(Provisioning.A_zmailAuthKerberos5Realm, krb5Realm);
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);
        
        String loginName = extAcctLocalPart + "@" + krb5Realm;
        
        // make the soap request
        SoapHttpTransport transport = new SoapHttpTransport(TestUtil.getSoapUrl());
        
        Element request = Element.create(transport.getRequestProtocol(), AccountConstants.AUTH_REQUEST);
        request.addElement(AccountConstants.E_ACCOUNT).addAttribute(AccountConstants.A_BY, AccountBy.krb5Principal.name()).setText(loginName);
        request.addElement(AccountConstants.E_PASSWORD).setText(externalPassword);
        
        Element response = transport.invoke(request);
        
        String encodedAuthToken = response.getElement(AccountConstants.E_AUTH_TOKEN).getText();
        assertNotNull(encodedAuthToken);
        String acctId = getAuthTokenAcctId(encodedAuthToken);
        Account acct = prov.get(AccountBy.id, acctId);
        verifyAcctAutoProvisioned(acct, loginName.toLowerCase());
    }
    
    /*
     * Note: need to restart server each time before re-run.  Otherwise server would still 
     * have the account created in previous run cached.
     */
    @Test
    public void preauthServlet() throws Exception {
        String testName = getTestName();
                
        String externalPassword = "test456";
        String extAcctLocalPart = testName;
        String extAcctName = createExternalAcctEntry(extAcctLocalPart, externalPassword, null);
        
        Map<String, Object> zmailDomainAttrs = commonZmailDomainAttrs();
        // setup auto prov
        // commonZmailDomainAttrs added only LDAP, add preauth here
        StringUtil.addToMultiMap(zmailDomainAttrs, Provisioning.A_zmailAutoProvAuthMech, AutoProvAuthMech.PREAUTH.name());
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(uid=%u)");
        // setup external LDAP auth
        zmailDomainAttrs.put(Provisioning.A_zmailAuthMech, AuthMech.ldap.name());
        zmailDomainAttrs.put(Provisioning.A_zmailAuthLdapURL, "ldap://localhost:389");
        zmailDomainAttrs.put(Provisioning.A_zmailAuthLdapBindDn, "uid=%u,ou=people," + extDomainDn);
        // setup preauth
        String preAuthKey = PreAuthKey.generateRandomPreAuthKey();
        zmailDomainAttrs.put(Provisioning.A_zmailPreAuthKey, preAuthKey);
        
        Domain zmailDomain = createZmailDomain(testName, zmailDomainAttrs);
        
        String loginName = extAcctLocalPart + "@" + zmailDomain.getName();
        
        // preauth data
        String preAuthUrl = TestPreAuthServlet.genPreAuthUrl(preAuthKey, loginName, false, false);
        
        // do the preauth servlet request
        String url = TestUtil.getBaseUrl() + preAuthUrl;
        
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        
        boolean ok = false;
        try {
            int respCode = HttpClientUtil.executeMethod(client, method);
            int statusCode = method.getStatusCode();
            String statusLine = method.getStatusLine().toString();
            
            ok = (respCode == 200);
            
            /*
            System.out.println("respCode=" + respCode);
            System.out.println("statusCode=" + statusCode);
            System.out.println("statusLine=" + statusLine);
            
            System.out.println("Headers");
            Header[] respHeaders = method.getResponseHeaders();
            for (int i=0; i < respHeaders.length; i++) {
                String header = respHeaders[i].toString();
                System.out.println(header);
            }
            */
            
            /*
            String respBody = method.getResponseBodyAsString();
            System.out.println(respBody);
            */
            
        } catch (HttpException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            method.releaseConnection();
        }
        
        assertTrue(ok);
        
        /*
        String encodedAuthToken = response.getElement(AccountConstants.E_AUTH_TOKEN).getText();
        assertNotNull(encodedAuthToken);
        AuthToken authToken = AuthToken.getAuthToken(encodedAuthToken);
        String acctId = authToken.getAccountId();
        Account acct = prov.get(AccountBy.id, acctId);
        verifyAcctAutoProvisioned(acct, loginName.toLowerCase());
        */
    }
    
    private void verifyAutoProvTask(SoapTransport transport, 
            Action action, Status expectedSatus) 
    throws Exception {
        AutoProvTaskControlRequest req = 
            new AutoProvTaskControlRequest(action);
        AutoProvTaskControlResponse resp = invokeJaxb(transport, req);
        Status status = resp.getStatus();
        
        assertEquals(expectedSatus.name(), status.name());
    }
    
    @Test
    public void attributeCallbackAutoProvScheduledDomains() throws Exception {
        Domain domain1 = createZmailDomain(genDomainSegmentName("1"), null);
        domain1.setAutoProvMode(Provisioning.AutoProvMode.EAGER);
        
        Domain domain2 = createZmailDomain(genDomainSegmentName("2"), null);
        domain2.setAutoProvMode(Provisioning.AutoProvMode.EAGER);
        
        Account admin = provUtil.createGlobalAdmin(genAcctNameLocalPart(), domain1);
        SoapTransport transport = authAdmin(admin.getName());
        
        /*
         * verify auto prov thread is not running at the beginning
         */
        verifyAutoProvTask(transport, Action.status, Status.idle);
        
        /*
         * schedule eager prov for domain1 and domain2 on server 
         */
        Server localServer = prov.getLocalServer();
        localServer.setAutoProvScheduledDomains(new String[]{domain1.getName(), domain2.getName()});
        
        /*
         * verify auto prov thread is now running, because at last one domain is scheduled
         */
        verifyAutoProvTask(transport, Action.status, Status.running);
        
        /*
         * unschedule domain1, verify the auto prov thread is still running
         */
        localServer.removeAutoProvScheduledDomains(domain1.getName());
        verifyAutoProvTask(transport, Action.status, Status.running);
        
        /*
         * unschedule domain2, verify the auto prov thread is not running, because there 
         * is no scheduled domain.
         */
        localServer.removeAutoProvScheduledDomains(domain2.getName());
        verifyAutoProvTask(transport, Action.status, Status.idle);
        
        /*
         * try to schedule a domain not in EAGER mode, verify the attempt fails
         */
        Domain domain3 = createZmailDomain(genDomainSegmentName("3"), null);
        domain3.setAutoProvMode(Provisioning.AutoProvMode.MANUAL);
        String exceptionCaught = null;
        try {
            localServer.addAutoProvScheduledDomains(domain3.getName());
        } catch (ServiceException e) {
            exceptionCaught = e.getCode();
        }
        assertEquals(ServiceException.INVALID_REQUEST, exceptionCaught);
    }
    
    @Test 
    public void nonEagerDomainRemovedFromScheduledDomains() throws Exception {
        Domain domain1 = createZmailDomain(genDomainSegmentName("1"), null);
        domain1.setAutoProvMode(Provisioning.AutoProvMode.EAGER);
        
        Domain domain2 = createZmailDomain(genDomainSegmentName("2"), null);
        domain2.setAutoProvMode(Provisioning.AutoProvMode.EAGER);
        
        Account admin = provUtil.createGlobalAdmin(genAcctNameLocalPart(), domain1);
        SoapTransport transport = authAdmin(admin.getName());
        
        /*
         * verify auto prov thread is not running at the beginning
         */
        verifyAutoProvTask(transport, Action.status, Status.idle);
        
        /*
         * change the initial wait and polling interval to short values so we don't have to
         * wait that long for following tests
         */
        modifyLocalconfigAndReload(transport, LC.autoprov_initial_sleep_ms, "0");
        Server localServer = prov.getLocalServer();
        long sleepInterval = 3 * Constants.MILLIS_PER_SECOND; // 3 seconds
        localServer.setAutoProvPollingInterval("3s");
        
        /*
         * schedule eager prov for domain1 and domain2 on server 
         */
        localServer.setAutoProvScheduledDomains(new String[]{domain1.getName(), domain2.getName()});
        Verify.verifyEquals(Sets.newHashSet(domain1.getName(), domain2.getName()), 
                localServer.getAutoProvScheduledDomains());
        
        /*
         * verify auto prov thread is now running, because at last one domain is scheduled
         */
        verifyAutoProvTask(transport, Action.status, Status.running);
        
        /*
         * change domain1 away from EAGER mode, wait for at least two (in case we missed 
         * the first cycle) sleep cycles, so that the domain is removed from the scheduled 
         * domains when the eager auto prov thread was at work.
         */
        domain1.setAutoProvMode(Provisioning.AutoProvMode.MANUAL);
        Thread.sleep(2 * sleepInterval);
        
        /*
         * verify domain1 is removed from the scheduled domains
         */
        prov.reload(localServer);
        Verify.verifyEquals(Sets.newHashSet(domain2.getName()), localServer.getAutoProvScheduledDomains());
        
        /*
         * verify the thread is still running
         */
        verifyAutoProvTask(transport, Action.status, Status.running);
        
        /*
         * change domain2 away from EAGER mode, wait for at least two (in case we missed 
         * the first cycle) sleep cycles, so that the domain is removed from the scheduled 
         * domains when the eager auto thread was at work.  Also verify that sicne there 
         * is now no scheduled domain, the thread is stopped
         */
        domain2.setAutoProvMode(Provisioning.AutoProvMode.MANUAL);
        Thread.sleep(2 * sleepInterval);
        prov.reload(localServer);
        Verify.verifyEquals(new HashSet<String>(), localServer.getAutoProvScheduledDomains());
        verifyAutoProvTask(transport, Action.status, Status.idle);
        
        /*
         * done test, set the initial wait LC key and polling interval back
         */
        modifyLocalconfigAndReload(transport, LC.autoprov_initial_sleep_ms, DEFAULT_AUTOPROV_INITIAL_SLEEP_MS);
        localServer.setAutoProvPollingInterval(DEFAULT_AUTOPROV_POLLING_INTERVAL);
    }
    
    @Test
    public void attributeCallbackAutoProvPollingInterval() throws Exception {
        Domain domain1 = createZmailDomain(genDomainSegmentName("1"), null);
        domain1.setAutoProvMode(Provisioning.AutoProvMode.EAGER);
        
        Domain domain2 = createZmailDomain(genDomainSegmentName("2"), null);
        domain2.setAutoProvMode(Provisioning.AutoProvMode.EAGER);
        
        Account admin = provUtil.createGlobalAdmin(genAcctNameLocalPart(), domain1);
        SoapTransport transport = authAdmin(admin.getName());
        
        /*
         * verify auto prov thread is not running at the beginning
         */
        verifyAutoProvTask(transport, Action.status, Status.idle);
        
        /*
         * schedule eager prov for domain1 and domain2 on server 
         */
        Server localServer = prov.getLocalServer();
        localServer.setAutoProvScheduledDomains(new String[]{domain1.getName(), domain2.getName()});
        
        /*
         * verify auto prov thread is now running, because at last one domain is scheduled
         */
        verifyAutoProvTask(transport, Action.status, Status.running);
        
        
        /*
         * set polling interval to 0, verify the auth prov thread is not running
         */
        // remember cur value and set it back after the test
        String curPollingInterval = localServer.getAutoProvPollingIntervalAsString();
        localServer.setAutoProvPollingInterval("0");
        verifyAutoProvTask(transport, Action.status, Status.idle);
        
        /*
         * done test, clean all data on the server
         */
        localServer.unsetAutoProvScheduledDomains();
        localServer.setAutoProvPollingInterval(curPollingInterval);
        Verify.verifyEquals(new HashSet<String>(), localServer.getAutoProvScheduledDomains());
        assertEquals(curPollingInterval, localServer.getAutoProvPollingIntervalAsString());
    }
    
    @Test
    public void autoProvTaskControl() throws Exception {
        Domain domain = createZmailDomain(genDomainSegmentName(), null);
        Account admin = provUtil.createGlobalAdmin(genAcctNameLocalPart(), domain);
        SoapTransport transport = authAdmin(admin.getName());
        
        /*
         * verify auto prov thread is not running at the beginning
         */
        verifyAutoProvTask(transport, Action.status, Status.idle);
        
        /*
         * force start the auto prov thread, verify it is started
         */
        verifyAutoProvTask(transport, Action.start, Status.started);
        verifyAutoProvTask(transport, Action.status, Status.running);
        
        /*
         * force stop the auot prov thread, verify it is stopped
         */
        verifyAutoProvTask(transport, Action.stop, Status.stopped);
        verifyAutoProvTask(transport, Action.status, Status.idle);
    }
    
    public static class TestDomainLockListener extends AutoProvisionTestUtil.MarkEntryProvisionedListener {
        private static final long LONGTIME = Constants.MILLIS_PER_DAY;
        private static final int HOLD_IT_AT_THIS_ENTRY = 2;
        
        private int numEntriesAutoProvisioned = 0;
        
        @Override
        public void postCreate(Domain domain, Account acct, String externalDN) {
            super.postCreate(domain, acct, externalDN);
            
            numEntriesAutoProvisioned++;
            if (HOLD_IT_AT_THIS_ENTRY == numEntriesAutoProvisioned) {
                // sleep for long time to hold the auto prov thread.
                // doing this will keep the auto prov thread in the server to have 
                // the domain locked so the eagerModeDomainUnlockedWhenThreadIsStopped 
                // test can verify domain unlocked at the right timing
                try {
                    Thread.sleep(LONGTIME);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Test
    public void eagerModeDomainUnlockedWhenThreadStopped() throws Exception {
        // must be > TestDomainLockListener.HOLD_IT_AT_THIS_ENTRY
        int numAccts = TestDomainLockListener.HOLD_IT_AT_THIS_ENTRY + 2; 
        
        for (int i = 1; i <= numAccts; i++) {
            createExternalAcctEntry("eagerMode-" + i, "test123", null);
        }
        
        Map<String, Object> zmailDomainAttrs = AutoProvisionTestUtil.commonZmailDomainAttrs();
        // setup auto prov
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, 
                "(&(uid=%u)(mail=eagerMode*)" + AutoProvisionTestUtil.MarkEntryProvisionedListener.NOT_PROVED_FILTER + ")");
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvAccountNameMap, Provisioning.A_uid);
        
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvListenerClass, 
                TestDomainLockListener.class.getName());
        Domain zmailDomain = createZmailDomain(genDomainSegmentName(), zmailDomainAttrs);
        
        // create a domain for the admin so the admin account won't interfere with our account counting
        Domain domain = provUtil.createDomain(getZmailDomainName("admin-domain"));
        Account admin = provUtil.createGlobalAdmin(genAcctNameLocalPart(), domain);
        SoapTransport transport = authAdmin(admin.getName());
        
        /*
         * verify the auto prov thread is not running
         */
        verifyAutoProvTask(transport, Action.status, Status.idle);
        
        /*
         * change LC key autpprov_initial_sleep_ms to 0, so we don't need to wait that long
         * (default is 5 mins)
         */
        modifyLocalconfigAndReload(transport, LC.autoprov_initial_sleep_ms, "0");
        
                
        // schedule the domain on local server
        Server localServer = prov.getLocalServer();
        localServer.addAutoProvScheduledDomains(zmailDomain.getName());

        
        /*
         * verify the auto prov thread is running
         */
        verifyAutoProvTask(transport, Action.status, Status.running);
        
        /*
         * let the auto prov thread run for a while, until after the 
         * TestDomainLockListener.HOLD_IT_AT_THIS_ENTRYth account is auto provisioned
         */
        while (true) {
            long numAcctsAutoProvisioned = prov.countObjects(CountObjectsType.account, zmailDomain, null);
            if (numAcctsAutoProvisioned == TestDomainLockListener.HOLD_IT_AT_THIS_ENTRY) {
                break;
            }
            System.out.println(getTestName() + " waiting for 1 second");
            Thread.sleep(Constants.MILLIS_PER_SECOND);
        }
        
        
        /*
         * verify the domain is locked, since the eager auto prov thread should be at work
         */
        prov.reload(zmailDomain);
        assertEquals(localServer.getId(), zmailDomain.getAutoProvLock());
        
        
        /*
         * un-schedule the domain
         */
        localServer.unsetAutoProvScheduledDomains();
        
        /*
         * verify the thread is stopped, and the domain is unlocked
         */
        verifyAutoProvTask(transport, Action.status, Status.idle);
        prov.reload(zmailDomain);
        assertNull(zmailDomain.getAutoProvLock());
        
        /*
         * done test, set the LC key back
         */
        modifyLocalconfigAndReload(transport, LC.autoprov_initial_sleep_ms, DEFAULT_AUTOPROV_INITIAL_SLEEP_MS);
    }
    
    @Test
    @Bug(bug=70720)
    public void errorHandling() throws Exception {
        /*
         * create and setup zmail domain
         */
        Map<String, Object> domainAttrs = Maps.newHashMap();
        domainAttrs.put(Provisioning.A_zmailAutoProvLdapURL, "ldap://localhost:389");
        domainAttrs.put(Provisioning.A_zmailAutoProvLdapAdminBindDn, extDomainAdminBindDn);
        domainAttrs.put(Provisioning.A_zmailAutoProvLdapAdminBindPassword, extDomainAdminBindPassword);
        StringUtil.addToMultiMap(domainAttrs, Provisioning.A_zmailAutoProvMode, AutoProvMode.LAZY.name());
        StringUtil.addToMultiMap(domainAttrs, Provisioning.A_zmailAutoProvMode, AutoProvMode.MANUAL.name());
        domainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(cn=auth*)");
        // domainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "(cn=%n)");
        domainAttrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, extDomainDn);
        domainAttrs.put(Provisioning.A_zmailAutoProvAccountNameMap, "cn");
        domainAttrs.put(Provisioning.A_zmailAutoProvAttrMap, "userPassword=userPassword");
        Domain domain = createZmailDomain(genDomainSegmentName(), domainAttrs);
        
        /*
         * create external accounts
         */
        Map<String, Object> extAcct1Attrs = Maps.newHashMap();
        extAcct1Attrs.put("cn", "authaccount01");
        createExternalAcctEntry("acct1", "test123", extAcct1Attrs);
          
        Map<String, Object> extAcct2Attrs = Maps.newHashMap();
        extAcct2Attrs.put("cn", "authaccount02");
        createExternalAcctEntry("acct2", "test123", extAcct2Attrs);
        
        Map<String, Object> extAcct3Attrs = Maps.newHashMap();
        extAcct3Attrs.put("cn", "authaccount03");
        createExternalAcctEntry("acct3", "test123", extAcct3Attrs);
        
        Map<String, Object> extAcct4Attrs = Maps.newHashMap();
        extAcct4Attrs.put("cn", "authaccount04");
        createExternalAcctEntry("acct4", "test123", extAcct4Attrs);
        
        /*
         * do a manual auto provision
         */
        SoapTransport transport = authZmailAdmin();
        DomainSelector domainSel = new DomainSelector(DomainSelector.DomainBy.name, domain.getName());
        PrincipalSelector principalSel = PrincipalSelector.create(AutoProvPrincipalBy.name, "authaccount04");
        AutoProvAccountRequest req = AutoProvAccountRequest.create(domainSel, principalSel);
        
        boolean caughtException = false;
        try {
            invokeJaxb(transport, req);
        } catch (ServiceException e) {
            String msg = e.getMessage();
            
            if (e.getCode().equals(LdapException.MULTIPLE_ENTRIES_MATCHED) &&
                    msg.contains(String.format("uid=acct1,ou=people,%s", extDomainDn)) && 
                    msg.contains(String.format("uid=acct2,ou=people,%s", extDomainDn)) &&
                    msg.contains(String.format("uid=acct3,ou=people,%s", extDomainDn)) && 
                    msg.contains(String.format("uid=acct4,ou=people,%s", extDomainDn))) {
                caughtException = true;
            }
        }
        assertTrue(caughtException);
        
        /*
         * modify domain to have the correct search filter
         */
        domain.setAutoProvLdapSearchFilter("(cn=%n)");
        
        /*
         * do the manual provision, should succeed this time
         */
        AutoProvAccountResponse resp = invokeJaxb(transport, req);
        AccountInfo acctInfo = resp.getAccount();
        assertEquals(TestUtil.getAddress("authaccount04", domain.getName()), acctInfo.getName());
        
        /*
         * do the same manual provision again, should fail with 
         */
        caughtException = false;
        try {
            invokeJaxb(transport, req);
        } catch (ServiceException e) {
            String msg = e.getMessage();
            
            if (e.getCode().equals(AccountServiceException.ACCOUNT_EXISTS)) {
                caughtException = true;
            }
        }
        assertTrue(caughtException);
        
        /*
        <CreateDomainRequest xmlns="urn:zmailAdmin">
            <name>autoprov44.1330496906457.com</name>
            <a n="zmailAutoProvLdapURL">ldap://zqa-003.eng.vmware.com:389/</a>
            <a n="zmailAutoProvLdapAdminBindDn">administrator@zmailqa.com</a>
            <a n="zmailAutoProvLdapAdminBindPassword">liquidsys</a>
            <a n="zmailAutoProvMode">LAZY</a>
            <a n="zmailAutoProvMode">MANUAL</a>
            <a n="zmailAutoProvLdapSearchFilter">(cn=auth*)</a>
            <a n="zmailAutoProvLdapSearchBase">OU=CommonUsers,DC=zmailqa,DC=com</a>
            <a n="zmailAutoProvAccountNameMap">cn</a>
            <a n="zmailAutoProvAttrMap">userPassword=userPassword</a>
        </CreateDomainRequest>
        
        zmsoap  -z AutoProvAccountRequest domain=bug70720.org.zmail.qa.unittest.prov.soap.testautoprovision.soaptest.unittest @by=name ../principal=authaccount04 @by=name          
        
        this zmsoap yields the following soap:
        
        <AutoProvAccountRequest xmlns="urn:zmailAdmin">
            <domain by="name">bug70720.org.zmail.qa.unittest.prov.soap.testautoprovision.soaptest.unittest</domain>
            <principal by="name">authaccount04</principal>
        </AutoProvAccountRequest>
        */
    }
}
