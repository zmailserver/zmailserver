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

import java.util.Map;

import org.junit.*;

import static org.junit.Assert.*;

import com.google.common.collect.Maps;
import org.zmail.common.account.ProvisioningConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.soap.SoapProvisioning;
import org.zmail.cs.httpclient.URLUtil;
import org.zmail.qa.unittest.TestUtil;

public class TestSoapProvisioning extends SoapTest {
    
    private static SoapProvTestUtil provUtil;
    private static Provisioning prov;
    private static Domain domain;
    
    @BeforeClass
    public static void init() throws Exception {
        provUtil = new SoapProvTestUtil();
        prov = provUtil.getProv();
        domain = provUtil.createDomain(baseDomainName());
    }
    
    @AfterClass 
    public static void cleanup() throws Exception {
        Cleanup.deleteAll(baseDomainName());
    }

    @Test
    public void isExpired() throws Exception {
        long lifeTimeSecs = 5;  // 5 seconds
        
        String acctName = TestUtil.getAddress("isExpired", domain.getName());
        String password = "test123";
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put(Provisioning.A_zmailIsAdminAccount, ProvisioningConstants.TRUE);
        attrs.put(Provisioning.A_zmailAdminAuthTokenLifetime, String.valueOf(lifeTimeSecs) + "s");
        Account acct = provUtil.createAccount(acctName, password, attrs);
        
        SoapProvisioning soapProv = new SoapProvisioning();
        
        Server server = prov.getLocalServer();
        soapProv.soapSetURI(URLUtil.getAdminURL(server));
        
        assertTrue(soapProv.isExpired());
        
        soapProv.soapAdminAuthenticate(acctName, password);
        
        assertFalse(soapProv.isExpired());
        
        System.out.println("Waiting for " + lifeTimeSecs + " seconds");
        Thread.sleep((lifeTimeSecs+1)*1000);
        
        assertTrue(soapProv.isExpired());
        
        prov.deleteAccount(acct.getId());
    }
}
