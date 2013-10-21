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
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.zmail.common.account.ProvisioningConstants;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.ZAttrProvisioning.AccountStatus;
import org.zmail.common.auth.ZAuthToken;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.HeaderConstants;
import org.zmail.common.soap.SoapFaultException;
import org.zmail.common.soap.SoapHttpTransport;
import org.zmail.common.soap.SoapProtocol;
import org.zmail.common.soap.SoapTransport;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.ZmailCookie;
import org.zmail.common.util.ZmailHttpConnectionManager;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.service.ZmailOAuthProvider;
import org.zmail.qa.unittest.TestUtil;
import org.zmail.qa.unittest.prov.Verify;
import org.zmail.qa.unittest.prov.soap.SoapDebugListener.Level;
import org.zmail.soap.account.message.AuthRequest;
import org.zmail.soap.account.message.AuthResponse;
import org.zmail.soap.account.message.GetInfoRequest;
import org.zmail.soap.account.message.GetInfoResponse;
import org.zmail.soap.account.type.Attr;
import org.zmail.soap.account.type.AuthToken;
import org.zmail.soap.admin.message.ClearCookieRequest;
import org.zmail.soap.admin.message.ClearCookieResponse;
import org.zmail.soap.admin.message.NoOpRequest;
import org.zmail.soap.admin.message.NoOpResponse;
import org.zmail.soap.admin.type.CookieSpec;
import org.zmail.soap.type.AccountSelector;

public class TestAuth extends SoapTest {
    
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
    
    private String getAuthToken(String acctName, boolean isAdmin) throws Exception {
        SoapTransport transport;
        if (isAdmin) {
            transport = authAdmin(acctName);
        } else {
            transport = authUser(acctName);
        }
        return transport.getAuthToken().getValue();
    }
    
    /**
     * a SoapTransport that puts auth token in cookie, not SOAP header
     */
    private static class AuthTokenInCookieTransport extends SoapHttpTransport {

        private boolean isAdmin;
        private String authTokenForCookie;
        private boolean voidAuthTokenOnExpired;
        
        private AuthTokenInCookieTransport(String authTokenForCookie, boolean isAdmin) {
            this(authTokenForCookie, isAdmin, false, null);
        }
        
        private AuthTokenInCookieTransport(String authTokenForCookie, boolean isAdmin,
                boolean voidAuthTokenOnExpired, SoapDebugListener debugListener) {
            super(null);
            this.isAdmin = isAdmin;
            this.authTokenForCookie = authTokenForCookie;
            this.voidAuthTokenOnExpired = voidAuthTokenOnExpired;
            setHttpDebugListener(
                    debugListener == null ? new SoapDebugListener(Level.ALL) : debugListener);
        }
        
        @Override
        public Element invoke(Element document, boolean raw, boolean noSession,
                String requestedAccountId, String changeToken, String tokenType)
                throws ServiceException, IOException {
            String uri = isAdmin ? TestUtil.getAdminSoapUrl() : TestUtil.getSoapUrl();
            HttpClient httpClient = ZmailHttpConnectionManager.getInternalHttpConnMgr().newHttpClient();
            
            ZAuthToken zAuthToken = new ZAuthToken(authTokenForCookie);
            Map<String, String> cookieMap = zAuthToken.cookieMap(isAdmin);
            
            PostMethod method = new PostMethod(uri + "unittest");
            try {
                Element envelope = generateSoapMessage(document, raw, noSession, 
                        requestedAccountId, changeToken, tokenType);
                
                if (voidAuthTokenOnExpired) {
                    Element eCtxt = SoapProtocol.Soap12.getHeader(envelope, HeaderConstants.CONTEXT);
                    Element eAuthTokenControl = eCtxt.addElement(HeaderConstants.E_AUTH_TOKEN_CONTROL);
                    eAuthTokenControl.addAttribute(HeaderConstants.A_VOID_ON_EXPIRED, true);
                }
                String soapMessage = SoapProtocol.toString(envelope, getPrettyPrint());
                method.setRequestEntity(new StringRequestEntity(soapMessage, null, "UTF-8"));
                
                HttpState state = null;
                if (cookieMap != null) {
                    for (Map.Entry<String, String> ck : cookieMap.entrySet()) {
                        if (state == null) {
                            state = new HttpState();
                        }
                        state.addCookie(new Cookie(method.getURI().getHost(), ck.getKey(), ck.getValue(), "/", null, false));
                    }
                }
                
                HttpMethodParams params = method.getParams();
                params.setCookiePolicy(state == null ? CookiePolicy.IGNORE_COOKIES : CookiePolicy.BROWSER_COMPATIBILITY);
                
                if (getHttpDebugListener() != null) {
                    getHttpDebugListener().sendSoapMessage(method, envelope, state);
                }
                
                int respCode = httpClient.executeMethod(null, method, state);
                
                InputStreamReader reader = 
                    new InputStreamReader(method.getResponseBodyAsStream(), SoapProtocol.getCharset());
                String responseStr = ByteUtil.getContent(
                        reader, (int) method.getResponseContentLength(), false);
                Element soapResp = parseSoapResponse(responseStr, false);
                
                if (getHttpDebugListener() != null) {
                    getHttpDebugListener().receiveSoapMessage(method, soapResp);
                }
                
                return soapResp;
            } finally {
                method.releaseConnection();
            }
        }
    }
    
    @Test
    public void soapByCookie() throws Exception {
        boolean isAdmin = false;
        
        String USER_NAME = TestUtil.getAddress("user1");
        String authToken = getAuthToken(USER_NAME, isAdmin);
        
        Element req = Element.create(SoapProtocol.Soap12, AccountConstants.GET_INFO_REQUEST);
        
        SoapTransport transport = new AuthTokenInCookieTransport(authToken, isAdmin);
        Element resp = transport.invoke(req);
        Element eName = resp.getElement(AccountConstants.E_NAME);
        String value = eName.getText();
        assertEquals(USER_NAME, value);
    }
    
    @Test
    public void soapByCookieAdmin() throws Exception {
        boolean isAdmin = true;
        
        String USER_NAME = TestUtil.getAddress("admin");
        String authToken = getAuthToken(USER_NAME, isAdmin);
        
        Element req = Element.create(SoapProtocol.Soap12, AdminConstants.GET_CONFIG_REQUEST);
        req.addElement(AdminConstants.E_A).addAttribute(AdminConstants.A_N, Provisioning.A_cn);
        
        SoapTransport transport = new AuthTokenInCookieTransport(authToken, isAdmin);
        Element resp = transport.invoke(req);
        Element eA = resp.getElement(AdminConstants.E_A);
        String value = eA.getText();
        assertEquals("config", value);
    }
    
    /*
     * debug listener to verify the Expires attribute is set correctly on the response 
     * auth token cookie
     */
    private static class VerifyCookieExpireListener extends SoapDebugListener {
        private String cookieToVerify;
        
        private VerifyCookieExpireListener(String cookieToVerify) {
            super(Level.ALL);
            this.cookieToVerify = cookieToVerify;
        }
        
        @Override
        public void receiveSoapMessage(PostMethod postMethod, Element envelope) {
            super.receiveSoapMessage(postMethod, envelope);
            
            // verify Max-Age attribute on auth token cookie is set properly
            Map<String, String> cookieAttrMap = Maps.newHashMap();
            
            Header[] headers = postMethod.getResponseHeaders();
            for (Header header : headers) {
                System.out.println(header.toString().trim()); // trim the ending crlf
                
                if (header.getName().equals("Set-Cookie")) {
                    cookieAttrMap.clear();
                    
                    // Set-Cookie: ZM_ADMIN_AUTH_TOKEN=0_2f6bd28...;Path=/;Expires=Fri, 16-Mar-2012 21:23:30 GMT;Secure;HttpOnly
                    String value = header.getValue();
                    String[] attrs = value.split(";");
                    for (String attr : attrs) {
                        String[] kv = attr.split("=");
                        if (kv.length == 2) {
                            cookieAttrMap.put(kv[0], kv[1]);
                        } else if (kv.length == 1) {
                            cookieAttrMap.put(kv[0], "is-present");
                        }
                    }
                    
                    if (cookieAttrMap.get(cookieToVerify) != null) {
                        // found out cookie
                        break;
                    } else {
                        // not the cookie we care
                        cookieAttrMap.clear();
                    }
                }
            }
            
            // done parsing header, verify
            assertNotNull(cookieAttrMap.get(cookieToVerify));
            String expires = cookieAttrMap.get("Expires");
            try {
                Date date = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z").parse(expires);
                int hour = date.getHours();
                int hourNow = new Date(System.currentTimeMillis()).getHours();
                int expectedExpireHour = hourNow + 1; // authTokenLifetime is 1h
                if (expectedExpireHour >= 24) {
                    expectedExpireHour -= 24;
                }
                assertEquals(expectedExpireHour, hour);
            } catch (ParseException e) {
                fail();
            }
        }
    }
    
    @Test
    public void authTokenCookieMaxAge() throws Exception {
        String authTokenLifetime = "1h";  // 1 hour, has to match code in VerifyCookieExpireListener
        
        /*
         * test admin Auth
         */
        Account admin = provUtil.createGlobalAdmin(genAcctNameLocalPart("admin"), domain);
        
        // set the account's auth token lifetime to a short period
        admin.setAdminAuthTokenLifetime(authTokenLifetime);
        
        SoapHttpTransport transportAdmin = new SoapHttpTransport(TestUtil.getAdminSoapUrl());
        transportAdmin.setHttpDebugListener(new VerifyCookieExpireListener(ZmailCookie.COOKIE_ZM_ADMIN_AUTH_TOKEN));
        
        org.zmail.soap.admin.message.AuthRequest reqAdmin = 
            new org.zmail.soap.admin.message.AuthRequest(admin.getName(), "test123");
        reqAdmin.setPersistAuthTokenCookie(Boolean.TRUE);
        org.zmail.soap.admin.message.AuthResponse respAdmin = invokeJaxb(transportAdmin, reqAdmin);
        
        /*
         * test account auth
         */
        Account acct = provUtil.createAccount(genAcctNameLocalPart("user"), domain);
        
        // set the account's auth token lifetime to a short period
        acct.setAuthTokenLifetime(authTokenLifetime);
        
        SoapHttpTransport transportAcct = new SoapHttpTransport(TestUtil.getSoapUrl());
        transportAcct.setHttpDebugListener(new VerifyCookieExpireListener(ZmailCookie.COOKIE_ZM_AUTH_TOKEN));
        
        org.zmail.soap.account.message.AuthRequest reqAcct = 
            new org.zmail.soap.account.message.AuthRequest(AccountSelector.fromName(acct.getName()), "test123");
        reqAcct.setPersistAuthTokenCookie(Boolean.TRUE);
        org.zmail.soap.account.message.AuthResponse respAcct = invokeJaxb(transportAcct, reqAcct);
        
        provUtil.deleteAccount(admin);
        provUtil.deleteAccount(acct);
    }
    
    @Test
    public void clearCookie() throws Exception {
        int authTokenLifetimeMSecs = 2000; // 2 seconds
        int waitMSecs = authTokenLifetimeMSecs + 1000;
        
        Account acct = provUtil.createGlobalAdmin(genAcctNameLocalPart(), domain);
       
        // set the account's auth token lifetime to a short period
        acct.setAdminAuthTokenLifetime(String.valueOf(authTokenLifetimeMSecs) + "ms");
        
        // String authToken = getAuthToken(acct.getName(), true);
        SoapTransport transport = authAdmin(acct.getName());
        
        // wait till the auto token expire
        Thread.sleep(waitMSecs);
        
        // make sure the auth token is indeed expired
        boolean caughtAuthExpired = false;
        try {
            NoOpRequest noOpReq= new NoOpRequest();
            NoOpResponse noOpResp = invokeJaxb(transport, noOpReq);
        } catch (ServiceException e) {
            if (AccountServiceException.AUTH_EXPIRED.equals(e.getCode())) {
                caughtAuthExpired = true;
            }
        }
        assertTrue(caughtAuthExpired);
        
        List<CookieSpec> cookiesToClear = Lists.newArrayList(new CookieSpec(ZmailCookie.COOKIE_ZM_ADMIN_AUTH_TOKEN));
        ClearCookieRequest req = new ClearCookieRequest(cookiesToClear);
        
        /*
         * test the regular path when auto token control is not set
         * (auth token in soap header)
         */
        caughtAuthExpired = false;
        try {
            invokeJaxb(transport, req);
        } catch (ServiceException e) {
            if (AccountServiceException.AUTH_EXPIRED.equals(e.getCode())) {
                caughtAuthExpired = true;
            }
        }
        assertTrue(caughtAuthExpired);
        
        /*
         * test the regular path when auto token control is not set
         * (auth token in cookie)
         */
        String authToken = transport.getAuthToken().getValue();
        SoapTransport authTokenInCookieTransport = new AuthTokenInCookieTransport(authToken, true);
        caughtAuthExpired = false;
        try {
            invokeJaxb(authTokenInCookieTransport, req);
        } catch (ServiceException e) {
            if (AccountServiceException.AUTH_EXPIRED.equals(e.getCode())) {
                caughtAuthExpired = true;
            }
        }
        assertTrue(caughtAuthExpired);
        
        
        /*
         * test the path when auth token control voidOnExpired is true
         */
        
        // debug listener to verify the cookie is cleared
        SoapDebugListener verifyCookieClearedListener = new SoapDebugListener(Level.ALL) {
            @Override
            public void receiveSoapMessage(PostMethod postMethod, Element envelope) {
                super.receiveSoapMessage(postMethod, envelope);
                
                // verify cookies are cleared
                Header[] headers = postMethod.getResponseHeaders();
                boolean cookieCleared = false;
                for (Header header : headers) {
                    if (header.toString().trim().equals(
                            "Set-Cookie: ZM_ADMIN_AUTH_TOKEN=;Path=/;Expires=Thu, 01-Jan-1970 00:00:00 GMT")) {
                        cookieCleared = true;
                    }
                    // System.out.println(header.toString().trim()); // trim the ending crlf
                }
                assertTrue(cookieCleared);
                
            }
        };
        
        authTokenInCookieTransport = new AuthTokenInCookieTransport(authToken, true, true, 
                verifyCookieClearedListener);

        // should NOT get AUTH_EXPIRED
        ClearCookieResponse resp = invokeJaxb(authTokenInCookieTransport, req);
        
        provUtil.deleteAccount(acct);
    }
    
    @Test
    public void accountStatusMaintenance() throws Exception {
        Account acct = provUtil.createAccount(genAcctNameLocalPart(), domain,
                Collections.singletonMap(
                Provisioning.A_zmailAccountStatus, (Object)AccountStatus.maintenance.name()));
        
        String errorCode = null;
        try {
            SoapTransport transport = authUser(acct.getName());
        } catch (SoapFaultException e) {
            errorCode = e.getCode();
        }
        assertEquals(AccountServiceException.MAINTENANCE_MODE, errorCode);
        
        provUtil.deleteAccount(acct);
    }

    @Test
    public void accountStatusMaintenanceAfterAuth() throws Exception {
        Account acct = provUtil.createAccount(genAcctNameLocalPart(), domain);
        
        SoapTransport transport = authUser(acct.getName());
        
        /*
         * change account status to maintenance
         */
        prov.modifyAccountStatus(acct, AccountStatus.maintenance.name());
        
        GetInfoRequest req = new GetInfoRequest();
        
        String errorCode = null;
        try {
            GetInfoResponse resp = invokeJaxb(transport, req);
        } catch (SoapFaultException e) {
            errorCode = e.getCode();
        }
        assertEquals(AccountServiceException.AUTH_EXPIRED, errorCode);
        
        provUtil.deleteAccount(acct);
    }
    
    @Test
    public void attrsReturnedInAuthResponse() throws Exception {
        String ATTR_NAME = Provisioning.A_zmailFeatureExternalFeedbackEnabled;
        String ATTR_VALUE = ProvisioningConstants.TRUE;
            
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put(ATTR_NAME, ATTR_VALUE);
        
        Account acct = provUtil.createAccount(genAcctNameLocalPart(), domain, attrs);
        
        SoapHttpTransport transport = new SoapHttpTransport(TestUtil.getSoapUrl());
        transport.setHttpDebugListener(new SoapDebugListener());
        
        org.zmail.soap.type.AccountSelector acctSel = 
            new org.zmail.soap.type.AccountSelector(org.zmail.soap.type.AccountBy.name, acct.getName());
        
        AuthRequest req = new AuthRequest(acctSel, "test123");
        req.addAttr(ATTR_NAME);
        AuthResponse resp = invokeJaxb(transport, req);
        
        Set<String> result = Sets.newHashSet();
        for (Attr attr : resp.getAttrs()) {
            String attrName = attr.getName();
            String attrValue = attr.getValue();
            
            result.add(Verify.makeResultStr(attrName, attrValue));
        }
        Verify.verifyEquals(Sets.newHashSet(Verify.makeResultStr(ATTR_NAME, ATTR_VALUE)), result);
        
        /*
         * test the auth by auth toke npath
         */
        String authTokenStr = resp.getAuthToken();
        AuthToken authToken = new AuthToken(authTokenStr, Boolean.FALSE);
        req = new AuthRequest();
        req.setAuthToken(authToken);
        req.addAttr(ATTR_NAME);
        
        transport = new SoapHttpTransport(TestUtil.getSoapUrl());
        transport.setHttpDebugListener(new SoapDebugListener());
        
        resp = invokeJaxb(transport, req);
        
        result = Sets.newHashSet();
        for (Attr attr : resp.getAttrs()) {
            String attrName = attr.getName();
            String attrValue = attr.getValue();
            
            result.add(Verify.makeResultStr(attrName, attrValue));
        }
        Verify.verifyEquals(Sets.newHashSet(Verify.makeResultStr(ATTR_NAME, ATTR_VALUE)), result);
    }
    
    @Test
    public void OAuth() throws Exception {
        Account acct = provUtil.createAccount(genAcctNameLocalPart(), domain);
        
        SoapHttpTransport transport = new SoapHttpTransport(TestUtil.getSoapUrl());
        
        Element eAuthReq = Element.create(transport.getRequestProtocol(), 
                AccountConstants.AUTH_REQUEST);
        
        // <authtoken>
        Element eAuthToken = eAuthReq.addElement(AccountConstants.E_AUTH_TOKEN);
        eAuthToken.addAttribute(AccountConstants.A_TYPE, "oauth");
        
        String accessToken = "whatever";
        Element eAccessToken = eAuthToken.addElement(AccountConstants.E_A);
        eAccessToken.addAttribute(AccountConstants.A_N, ZmailOAuthProvider.OAUTH_ACCESS_TOKEN);
        eAccessToken.setText(accessToken);
        
        // <account>
        Element eAcct = eAuthReq.addElement(AccountConstants.E_ACCOUNT);
        eAcct.addAttribute(AccountConstants.A_BY, AccountBy.name.name());
        eAcct.setText(acct.getName());
        
        Element eResp = transport.invoke(eAuthReq);
        
        Element eAuthTokenResp = eResp.getElement(AccountConstants.E_AUTH_TOKEN);
        String authToken = eAuthTokenResp.getText();
        assertNotNull(authToken);
        
    }
}


