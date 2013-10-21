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

package org.zmail.cs.service.authenticator;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.security.SpnegoLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.SpnegoUserIdentity;
import org.eclipse.jetty.security.SpnegoUserPrincipal;
import org.eclipse.jetty.security.UserAuthentication;

import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.ZAttrProvisioning.AutoProvAuthMech;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.GuestAccount;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.AccountServiceException.AuthFailedServiceException;
import org.zmail.cs.account.krb5.Krb5Principal;

public class SpnegoAuthenticator extends SSOAuthenticator {

    private SpnegoLoginService spnegoUserRealm;
    
    public SpnegoAuthenticator(HttpServletRequest req, HttpServletResponse resp, SpnegoLoginService spnegoUserRealm) {
        super(req, resp);
        this.spnegoUserRealm = spnegoUserRealm;
    }
    
    @Override
    public String getAuthType() {
        return "Spnego";
    }
    
    @Override
    public ZmailPrincipal authenticate() throws ServiceException {
        Request request = (req instanceof Request) ? (Request)req : null;
        
        if (request == null) {
            throw ServiceException.FAILURE("not supported", null);
        }
        return getPrincipal(request);
    }
    
    private ZmailPrincipal getPrincipal(Request request) throws ServiceException {
    	ZmailPrincipal principal = null;
        
        try {
            principal = authenticate(spnegoUserRealm, request, resp);
            
            // comment out the above and uncomment the line below for quick testing 
            // non-spenogo related issues, like redirect.
            // principal = MockSpnegoUser.getMockPrincipal();  
        } catch (IOException e) {
            throw AuthFailedServiceException.AUTH_FAILED("spnego authenticate failed", e); 
        }
        
        if (principal == null) {
            throw AuthFailedServiceException.AUTH_FAILED("spnego authenticate failed", (Throwable)null); 
        }
        
        return principal;
    }
    
    
    private Account getAccountByPrincipal(Principal principal) throws ServiceException {
        
        String krb5Name = principal.getName();
        
        Provisioning prov = Provisioning.getInstance();
        Account acct = prov.get(AccountBy.krb5Principal, krb5Name);
        
        if (acct == null) {
            Domain domain = Krb5Principal.getDomainByKrb5Principal(krb5Name);
            if (domain != null) {
                acct = prov.autoProvAccountLazy(domain, krb5Name, null, AutoProvAuthMech.SPNEGO);
            }
        }
        return acct;
    }
    
    /* =========================================================
     * 
     * Based on org.eclipse.jetty.security.SpnegoAuthenticator
     * 
     * =========================================================
     */
    private ZmailPrincipal authenticate(LoginService realm, Request request, HttpServletResponse response) 
    throws ServiceException, IOException {
        Principal user = null;
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        /*
         * if the header is null then we need to challenge...this is after the error page check
         */
        if (header == null) {
            sendChallenge(realm,request,response);
            throw SSOAuthenticatorServiceException.SENT_CHALLENGE();
            
        } else if (header != null && header.startsWith(HttpHeaders.NEGOTIATE)) {           
            /*
             * we have gotten a negotiate header to try and authenticate
             */
            
            // skip over "Negotiate "
            String token = header.substring(10);
            
            UserIdentity identity = realm.login(null, token);
            if (identity == null) {
                throw AuthFailedServiceException.AUTH_FAILED("SpengoAuthenticator: unable to login", (Throwable)null);
            }
            user = identity.getUserPrincipal();
            
            if (user != null) {
                ZmailLog.account.debug("SpengoAuthenticator: obtained principal: " + user.getName());
                
                Account acct = getAccountByPrincipal(user);
                ZmailPrincipal zmailPrincipal = new ZmailPrincipal(user.getName(), acct);
                String clientName = ((SpnegoUserPrincipal)user).getName();
                String role = clientName.substring(clientName.indexOf('@') + 1);                
                SpnegoUserIdentity spnegoUserIdentity = new SpnegoUserIdentity(identity.getSubject(), zmailPrincipal, Arrays.asList(role));     
                Authentication authentication = new UserAuthentication(getAuthType(), spnegoUserIdentity);
                request.setAuthentication(authentication);
                response.addHeader(HttpHeaders.WWW_AUTHENTICATE, HttpHeaders.NEGOTIATE + " " + ((SpnegoUserPrincipal)user).getToken());                
                
                return zmailPrincipal;
            }
            else {
                /*
                 * no user was returned from the authentication which means something failed
                 * so process error logic
                 */
                ZmailLog.account.debug("SpengoAuthenticator: no user found, authentication failed");
                throw AuthFailedServiceException.AUTH_FAILED("SpengoAuthenticator: no user found, authentication failed", (Throwable)null);
            }
        } else {
            /*
             * the header was not null, but we didn't get a negotiate so process error logic
             */
            throw AuthFailedServiceException.AUTH_FAILED(
                    "SpengoAuthenticator: authentication failed, unknown header (browser is likely misconfigured for SPNEGO)", (Throwable)null);
        }        
    }
    
    public void sendChallenge(LoginService realm, Request request, HttpServletResponse response) throws IOException {
        ZmailLog.account.debug("SpengoAuthenticator: sending challenge");
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, HttpHeaders.NEGOTIATE);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
    

    private static class MockSpnegoUser implements Principal {
        String name;
        String token;
        
        private static ZmailPrincipal getMockPrincipal() throws IOException {
            Principal principal = new MockSpnegoUser("spnego@SPNEGO.LOCAL", "blah");
            ZmailPrincipal zmailPrincipal = null;
			try {
				zmailPrincipal = new ZmailPrincipal(principal.getName(), GuestAccount.ANONYMOUS_ACCT);
			} catch (ServiceException e) {
			}
            return zmailPrincipal;
        }
        
        MockSpnegoUser(String name, String token) {
            this.name = name;
            this.token = token;
        }
        
        @Override
        public String getName() {
            return name;
        }
        
        public String getToken() {
            return token;
        }
        
    }

}
