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

package org.zmail.cs.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zmail.common.soap.Element;
import org.zmail.common.soap.HeaderConstants;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailCookie;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.account.ZmailAuthToken;
import org.zmail.cs.account.auth.AuthMechanism.AuthMech;
import org.zmail.soap.SoapServlet;

public class ZmailAuthProvider extends AuthProvider {

    public static final String ZIMBRA_AUTH_PROVIDER = "zmail";
    
    ZmailAuthProvider() {
        this(ZIMBRA_AUTH_PROVIDER);
    }
    
    protected ZmailAuthProvider(String name) {
        super(name);
    }

    private String getEncodedAuthTokenFromCookie(HttpServletRequest req, boolean isAdminReq) {
        String cookieName = ZmailCookie.authTokenCookieName(isAdminReq);
        String encodedAuthToken = null;
        javax.servlet.http.Cookie cookies[] =  req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(cookieName)) {
                    encodedAuthToken = cookies[i].getValue();
                    break;
                }
            }
        }
        return encodedAuthToken;
    }
        
    @Override
    protected AuthToken authToken(HttpServletRequest req, boolean isAdminReq) 
    throws AuthProviderException, AuthTokenException {
        String encodedAuthToken = getEncodedAuthTokenFromCookie(req, isAdminReq);
        return genAuthToken(encodedAuthToken);
    }

    @Override
    protected AuthToken authToken(Element soapCtxt, Map engineCtxt) 
    throws AuthProviderException, AuthTokenException  {
        String encodedAuthToken = soapCtxt == null ? null : 
            soapCtxt.getAttribute(HeaderConstants.E_AUTH_TOKEN, null);
        
        // check for auth token in engine context if not in soap header  
        if (encodedAuthToken == null) {
            encodedAuthToken = (String) engineCtxt.get(SoapServlet.ZIMBRA_AUTH_TOKEN);
        }
        
        // if still not found, see if it is in the servlet request
        if (encodedAuthToken == null) {
            HttpServletRequest req = (HttpServletRequest) engineCtxt.get(SoapServlet.SERVLET_REQUEST);
            if (req != null) {
                Boolean isAdminReq = (Boolean) engineCtxt.get(SoapServlet.IS_ADMIN_REQUEST);
                if (isAdminReq != null) {
                    // get auth token from cookie only if we can determine if this is an admin request
                    encodedAuthToken = getEncodedAuthTokenFromCookie(req, isAdminReq);
                }
            }
        }
        
        return genAuthToken(encodedAuthToken);
    }
    
    @Override
    protected AuthToken authToken(String encoded) throws AuthProviderException, AuthTokenException {
        return genAuthToken(encoded);
    }
    
    protected AuthToken genAuthToken(String encodedAuthToken) throws AuthProviderException, AuthTokenException {
        if (StringUtil.isNullOrEmpty(encodedAuthToken)) {
            throw AuthProviderException.NO_AUTH_DATA();
        }
        
        return ZmailAuthToken.getAuthToken(encodedAuthToken);
    }
    
    @Override
    protected AuthToken authToken(Account acct) {
        return new ZmailAuthToken(acct);
    }
    
    @Override
    protected AuthToken authToken(Account acct, boolean isAdmin, AuthMech authMech) {
        return new ZmailAuthToken(acct, isAdmin, authMech);
    }
    
    @Override
    protected AuthToken authToken(Account acct, long expires) {
        return new ZmailAuthToken(acct, expires);
    }
    
    @Override
    protected AuthToken authToken(Account acct, long expires, boolean isAdmin, 
            Account adminAcct) {
        return new ZmailAuthToken(acct, expires, isAdmin, adminAcct, null);
    }
    
}
