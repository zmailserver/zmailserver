/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import org.zmail.common.httpclient.HttpClientUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.PreAuthKey;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.Server;
import org.zmail.cs.ldap.LdapConstants;

import junit.framework.TestCase;

public class TestPreAuthServlet extends TestCase {

    String setUpDomain() throws Exception {
        String domainName = TestUtil.getDomain();
        Domain domain = Provisioning.getInstance().get(Key.DomainBy.name, domainName);
        String preAuthKey = PreAuthKey.generateRandomPreAuthKey();
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailPreAuthKey, preAuthKey);
        Provisioning.getInstance().modifyAttrs(domain, attrs);
        return preAuthKey;
    }
    
    public static String genPreAuthUrl(String preAuthKey, String user, boolean admin, boolean shouldFail) throws Exception {
        
        HashMap<String,String> params = new HashMap<String,String>();
        String acctName = TestUtil.getAddress(user);
        String authBy = "name";
        long timestamp = System.currentTimeMillis();
        long expires = 0;
        
        params.put("account", acctName);
        params.put("by", authBy);
        params.put("timestamp", timestamp+"");
        params.put("expires", expires+"");
        
        if (admin)
            params.put("admin", "1");
        
        String preAuth = PreAuthKey.computePreAuth(params, preAuthKey);
        
        StringBuffer url = new StringBuffer("/service/preauth?");
        url.append("account=" + acctName);
        url.append("&by=" + authBy);
        if (shouldFail) {
            // if doing negative testing, mess up with the timestamp so
            // it won't be computed to the same value at the server and 
            // the preauth will fail
            long timestampBad = timestamp + 10;
            url.append("&timestamp=" + timestampBad);
        } else    
            url.append("&timestamp=" + timestamp);
        url.append("&expires=" + expires);
        url.append("&preauth=" + preAuth);
        
        if (admin)
            url.append("&admin=1");
        
        return url.toString();
    }
    
    void doPreAuthServletRequest(String preAuthUrl, boolean admin) throws Exception{
        Server localServer = Provisioning.getInstance().getLocalServer();
        
        String protoHostPort;
        if (admin)
            protoHostPort = "https://localhost:" + localServer.getIntAttr(Provisioning.A_zmailAdminPort, 0);
        else
            protoHostPort = "http://localhost:" + localServer.getIntAttr(Provisioning.A_zmailMailPort, 0);
        
        String url = protoHostPort + preAuthUrl;
        
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        
        try {
            int respCode = HttpClientUtil.executeMethod(client, method);
            int statusCode = method.getStatusCode();
            String statusLine = method.getStatusLine().toString();
            
            System.out.println("respCode=" + respCode);
            System.out.println("statusCode=" + statusCode);
            System.out.println("statusLine=" + statusLine);
            
            /*
            System.out.println("Headers");
            Header[] respHeaders = method.getResponseHeaders();
            for (int i=0; i < respHeaders.length; i++) {
                String header = respHeaders[i].toString();
                System.out.println(header);
            }
            
            String respBody = method.getResponseBodyAsString();
            // System.out.println("respBody=" + respBody);
            */
            
        } catch (HttpException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            method.releaseConnection();
        }
    }
    
    private void doPreAuth(String userLocalPart, boolean admin, boolean shouldFail) throws Exception {
        String preAuthKey = setUpDomain();
        
        String preAuthUrl = genPreAuthUrl(preAuthKey, userLocalPart, admin, shouldFail);
        
        System.out.println("preAuthKey=" + preAuthKey);
        System.out.println("preAuth=" + preAuthUrl);
        
        doPreAuthServletRequest(preAuthUrl, admin);
    }
    
    public void testPreAuthServlet() throws Exception {
        doPreAuth("user1", false, false);
        doPreAuth("admin", true, false);
        doPreAuth("domainadmin", true, false);
        
        /*
        // test refer mde == always
        Provisioning prov = Provisioning.getInstance();
        Server server = prov.getLocalServer();
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailMailReferMode, "always");
        prov.modifyAttrs(server, attrs);
        
        doPreAuth("user1", false, false);
        doPreAuth("admin", true, false);
        doPreAuth("domainadmin", true, false);
        
        // set refer mode back
        attrs.put(Provisioning.A_zmailMailReferMode, "wronghost");
        prov.modifyAttrs(server, attrs);
        */
    }
    
    private Account dumpLockoutAttrs(String user) throws Exception {
        Account acct = Provisioning.getInstance().get(AccountBy.name, user);
        
        System.out.println();
        System.out.println(Provisioning.A_zmailAccountStatus + ": " + acct.getAttr(Provisioning.A_zmailAccountStatus));
        System.out.println(Provisioning.A_zmailPasswordLockoutLockedTime + ": " + acct.getAttr(Provisioning.A_zmailPasswordLockoutLockedTime));

        System.out.println(Provisioning.A_zmailPasswordLockoutFailureTime + ": ");
        String[] failureTime = acct.getMultiAttr(Provisioning.A_zmailPasswordLockoutFailureTime);
        for (String ft : failureTime)
            System.out.println("    " + ft);
        
        return acct;
    }
    
    public void disable_testPreAuthLockout() throws Exception {
        String user = "user4";
        Account acct = TestUtil.getAccount(user);
        
        Provisioning prov = Provisioning.getInstance();
        
        Map<String, Object> attrs = new HashMap<String, Object>();
        
        int lockoutAfterNumFailures = 3;
        
        // setup lockout config attrs
        attrs.put(Provisioning.A_zmailPasswordLockoutEnabled, LdapConstants.LDAP_TRUE);
        attrs.put(Provisioning.A_zmailPasswordLockoutDuration, "1m");
        attrs.put(Provisioning.A_zmailPasswordLockoutMaxFailures, lockoutAfterNumFailures+"");
        attrs.put(Provisioning.A_zmailPasswordLockoutFailureLifetime, "30s");
        
        // put the account in active mode, clean all lockout attrs that might have been set 
        // in previous test
        attrs.put(Provisioning.A_zmailAccountStatus, "active");
        attrs.put(Provisioning.A_zmailPasswordLockoutLockedTime, "");
        attrs.put(Provisioning.A_zmailPasswordLockoutFailureTime, "");
        
        prov.modifyAttrs(acct, attrs);
        
        System.out.println("Before the test:");
        dumpLockoutAttrs(user);
        System.out.println();
        
        // the account should be locked out at the last iteration
        for (int i=0; i<=lockoutAfterNumFailures; i++) {
            System.out.println("======================");
            System.out.println("Iteration: " + i);
            
            doPreAuth(user, false, true);
            Account a = dumpLockoutAttrs(user);
            System.out.println("\n\n");
            
            if (i >= lockoutAfterNumFailures-1)
                assertEquals("lockout", a.getAttr(Provisioning.A_zmailAccountStatus));
            else
                assertEquals("active", a.getAttr(Provisioning.A_zmailAccountStatus));
            
            // sleep two seconds
            Thread.sleep(2000);
        }
        
        /*
        zmailPasswordLockoutDuration: 3m
        zmailPasswordLockoutEnabled: TRUE
        zmailPasswordLockoutFailureLifetime: 1m
        zmailPasswordLockoutMaxFailures: 5

        <attr id="378" name="zmailPasswordLockoutEnabled" type="boolean" cardinality="single" optionalIn="account,cos" flags="accountInherited,domainAdminModifiable">
        <attr id="379" name="zmailPasswordLockoutDuration" type="duration" cardinality="single" optionalIn="account,cos" flags="accountInherited,domainAdminModifiable">
        <attr id="380" name="zmailPasswordLockoutMaxFailures" type="integer" cardinality="single" optionalIn="account,cos" flags="accountInherited,domainAdminModifiable">
        <attr id="381" name="zmailPasswordLockoutFailureLifetime" type="duration" cardinality="single" optionalIn="account,cos" flags="accountInherited,domainAdminModifiable">
        <attr id="382" name="zmailPasswordLockoutLockedTime" type="gentime" cardinality="single" optionalIn="account" flags="domainAdminModifiable">
        <attr id="383" name="zmailPasswordLockoutFailureTime" type="gentime" cardinality="multi" optionalIn="account" flags="domainAdminModifiable">
        */
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        TestUtil.cliSetup();
        try {
            TestUtil.runTest(TestPreAuthServlet.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
