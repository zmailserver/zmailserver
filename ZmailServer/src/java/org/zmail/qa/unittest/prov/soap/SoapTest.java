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

import java.io.IOException;

import org.junit.BeforeClass;

import org.zmail.common.localconfig.KnownKey;
import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.SoapHttpTransport;
import org.zmail.common.soap.SoapProtocol;
import org.zmail.common.soap.SoapTransport;
import org.zmail.common.soap.Element.XMLElement;
import org.zmail.common.soap.SoapHttpTransport.HttpDebugListener;
import org.zmail.common.util.CliUtil;
import org.zmail.cs.account.accesscontrol.RightManager;
import org.zmail.cs.ldap.unboundid.InMemoryLdapServer;
import org.zmail.qa.unittest.TestUtil;
import org.zmail.qa.unittest.prov.LocalconfigTestUtil;
import org.zmail.qa.unittest.prov.ProvTest;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.account.message.AuthRequest;
import org.zmail.soap.account.message.AuthResponse;
import org.zmail.soap.admin.message.ReloadLocalConfigRequest;
import org.zmail.soap.admin.message.ReloadLocalConfigResponse;

public class SoapTest extends ProvTest {
    private static boolean JSON = false;
    
    private static final String SOAP_TEST_BASE_DOMAIN = "soaptest";
    
    private static String PASSWORD = "test123";
    private static HttpDebugListener soapDebugListener;
    
    @BeforeClass
    public static void beforeClass() throws Exception {
        CliUtil.toolSetup(); // init ssl stuff
        soapDebugListener = new SoapDebugListener();
        
        // init rights
        RightManager.getInstance();
    }
    
    static String baseDomainName() {
        StackTraceElement [] s = new RuntimeException().getStackTrace();
        return s[1].getClassName().toLowerCase() + "." + 
                SOAP_TEST_BASE_DOMAIN + "." + InMemoryLdapServer.UNITTEST_BASE_DOMAIN_SEGMENT;
    }
    
    static SoapTransport authUser(String acctName) throws Exception {
        return authUser(acctName, PASSWORD);
    }
    
    static SoapTransport authUser(String acctName, String password) throws Exception {
        org.zmail.soap.type.AccountSelector acct = 
            new org.zmail.soap.type.AccountSelector(org.zmail.soap.type.AccountBy.name, acctName);
        
        SoapHttpTransport transport = new SoapHttpTransport(TestUtil.getSoapUrl());
        transport.setHttpDebugListener(soapDebugListener);
        
        AuthRequest req = new AuthRequest(acct, password);
        AuthResponse resp = invokeJaxb(transport, req);
        transport.setAuthToken(resp.getAuthToken());
        return transport;
    }
    
    static SoapTransport authAdmin(String acctName) throws Exception {
        return authAdmin(acctName, PASSWORD);
    }
    
    static SoapTransport authAdmin(String acctName, String password) throws Exception {
        SoapHttpTransport transport = new SoapHttpTransport(TestUtil.getAdminSoapUrl());
        transport.setHttpDebugListener(soapDebugListener);
        
        org.zmail.soap.admin.message.AuthRequest req = new org.zmail.soap.admin.message.AuthRequest(acctName, password);
        org.zmail.soap.admin.message.AuthResponse resp = invokeJaxb(transport, req);
        transport.setAuthToken(resp.getAuthToken());
        return transport;
    }
    
    static SoapTransport authZmailAdmin() throws Exception {
        return authAdmin(LC.zmail_ldap_user.value(), LC.zmail_ldap_password.value());
    }
    
    static void modifyLocalconfigAndReload(SoapTransport transport, KnownKey key, String value) 
    throws Exception {
        LocalconfigTestUtil.modifyLocalConfig(key, value);
        
        // reload LC on server
        ReloadLocalConfigRequest req = new ReloadLocalConfigRequest();
        ReloadLocalConfigResponse resp = invokeJaxb(transport, req);
    }
    
    static <T> T invokeJaxb(SoapTransport transport, Object jaxbObject)
    throws ServiceException, IOException {
        return (T) invokeJaxb(transport, jaxbObject,
                JSON ? SoapProtocol.SoapJS : SoapProtocol.Soap12);
    }
    
    static <T> T invokeJaxb(SoapTransport transport, Object jaxbObject, SoapProtocol proto)
    throws ServiceException, IOException {
        Element req = JaxbUtil.jaxbToElement(jaxbObject, proto.getFactory());
        
        Element res = transport.invoke(req);
        return (T) JaxbUtil.elementToJaxb(res);
    }
    
    static <T> T invokeJaxbOnTargetAccount(SoapTransport transport, Object jaxbObject,
            String targetAcctId) 
    throws Exception {
        String oldTarget = transport.getTargetAcctId();
        try {
            transport.setTargetAcctId(targetAcctId);
            return (T) invokeJaxb(transport, jaxbObject);
        } finally {
            transport.setTargetAcctId(oldTarget);
        }
    }
}
