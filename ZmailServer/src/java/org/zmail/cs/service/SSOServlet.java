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

package org.zmail.cs.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AccessManager;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.auth.AuthContext;
import org.zmail.cs.httpclient.URLUtil;
import org.zmail.cs.service.authenticator.SSOAuthenticator.ZmailPrincipal;
import org.zmail.cs.servlet.ZmailServlet;

public abstract class SSOServlet extends ZmailServlet {
    private final static String IGNORE_LOGIN_URL = "?ignoreLoginURL=1";
    
    protected abstract boolean redirectToRelativeURL();
    
    public void init() throws ServletException {
        String name = getServletName();
        ZmailLog.account.info("Servlet " + name + " starting up");
        super.init();
    }

    public void destroy() {
        String name = getServletName();
        ZmailLog.account.info("Servlet " + name + " shutting down");
        super.destroy();
    }
    
    protected AuthToken authorize(HttpServletRequest req, AuthContext.Protocol proto, 
            ZmailPrincipal principal, boolean isAdminRequest) 
    throws ServiceException {
        
        Map<String, Object> authCtxt = new HashMap<String, Object>();
        authCtxt.put(AuthContext.AC_ORIGINATING_CLIENT_IP, ZmailServlet.getOrigIp(req));
        authCtxt.put(AuthContext.AC_REMOTE_IP, ZmailServlet.getClientIp(req));
        authCtxt.put(AuthContext.AC_ACCOUNT_NAME_PASSEDIN, principal.getName());
        authCtxt.put(AuthContext.AC_USER_AGENT, req.getHeader("User-Agent"));
        
        Provisioning prov = Provisioning.getInstance();
        Account acct = principal.getAccount();
        
        ZmailLog.addAccountNameToContext(acct.getName());
        
        prov.ssoAuthAccount(acct, proto, authCtxt); 
        
        if (isAdminRequest) {
            if (!AccessManager.getInstance().isAdequateAdminAccount(acct)) {
                throw ServiceException.PERM_DENIED("not an admin account");
            }
        }
        
        AuthToken authToken = AuthProvider.getAuthToken(acct, isAdminRequest);
        
        /*
        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", authType, "account", acct.getName(), "admin", isAdminRequest+""}));
        */
        
        return authToken;
    }
    
    protected boolean isOnAdminPort(HttpServletRequest req) throws ServiceException {
        int adminPort = Provisioning.getInstance().getLocalServer().getAdminPort();
        return req.getLocalPort() == adminPort;
    }
    
    protected boolean isFromZCO(HttpServletRequest req) throws ServiceException {
        final String UA_ZCO = "Zmail-ZCO";
        String userAgent = req.getHeader("User-Agent");
        return userAgent.contains(UA_ZCO);
    }
    
    protected void setAuthTokenCookieAndReturn(HttpServletRequest req, HttpServletResponse resp, 
            AuthToken authToken) 
    throws IOException, ServiceException {
        
        boolean isAdmin = AuthToken.isAnyAdmin(authToken);
        boolean secureCookie = isProtocolSecure(req.getScheme());
        authToken.encode(resp, isAdmin, secureCookie);
        resp.setContentLength(0);
    }
    
    private String getRedirectURL(HttpServletRequest req, Server server, boolean isAdmin) 
    throws ServiceException, MalformedURLException {
        boolean relative = redirectToRelativeURL();
        
        String redirectUrl;
        if (isAdmin) {
            redirectUrl = getAdminURL(server, relative);
        } else {
            redirectUrl = getMailURL(server, relative);
        }
        
        if (!relative) {
            URL url = new URL(redirectUrl);
         
            // replace host of the URL to the host the request was sent to
            String reqHost = req.getServerName();
            String host = url.getHost();
            
            if (!reqHost.equalsIgnoreCase(host)) {
                URL destUrl = new URL(url.getProtocol(), reqHost, url.getPort(), url.getFile());
                redirectUrl = destUrl.toString();
            }
        }
        
        return redirectUrl;
    }
    
    private String appendIgnoreLoginURL(String redirectUrl) {
        if (!redirectUrl.endsWith("/")) {
            redirectUrl = redirectUrl + "/";
        }
        return redirectUrl + IGNORE_LOGIN_URL;
    }
    
    protected void setAuthTokenCookieAndRedirect(HttpServletRequest req, HttpServletResponse resp, 
            Account acct, AuthToken authToken) 
    throws IOException, ServiceException {
        
        boolean isAdmin = AuthToken.isAnyAdmin(authToken);
        boolean secureCookie = isProtocolSecure(req.getScheme());
        authToken.encode(resp, isAdmin, secureCookie);

        Provisioning prov = Provisioning.getInstance();
        Server server = prov.getServer(acct);
        
        String redirectUrl = getRedirectURL(req, server, isAdmin);
        
        // always append the ignore loginURL query so we do not get into a redirect loop.
        redirectUrl = appendIgnoreLoginURL(redirectUrl);
        
        boolean relative = redirectToRelativeURL();
        if (!relative) {
            URL url = new URL(redirectUrl);
            boolean isRedirectProtocolSecure = isProtocolSecure(url.getProtocol());
            
            if (secureCookie && !isRedirectProtocolSecure) {
                throw ServiceException.INVALID_REQUEST(
                        "cannot redirect to non-secure protocol: " + redirectUrl, null);
            }
        }
        
        ZmailLog.account.debug("SSOServlet - redirecting (with auth token) to: " + redirectUrl);
        resp.sendRedirect(redirectUrl);
    }
    
    // Redirect to the specified error page without Zmail auth token cookie.
    // The default error page is the webapp's regular entry page where user can
    // enter his username/password.
    protected void redirectToErrorPage(HttpServletRequest req, HttpServletResponse resp, 
            boolean isAdminRequest, String errorUrl) 
    throws IOException, ServiceException {
        String redirectUrl;
        
        if (errorUrl == null) {
            Server server = Provisioning.getInstance().getLocalServer();
            redirectUrl = getRedirectURL(req, server, isAdminRequest);
            
            // always append the ignore loginURL query so we do not get into a redirect loop.
            redirectUrl = appendIgnoreLoginURL(redirectUrl);
            
        } else {
            redirectUrl = errorUrl;
        }
        
        ZmailLog.account.debug("SSOServlet - redirecting to: " + redirectUrl);
        resp.sendRedirect(redirectUrl);
    }
    
    
    private boolean isProtocolSecure(String protocol) {
        return URLUtil.PROTO_HTTPS.equalsIgnoreCase(protocol);
    }
    
    private String getMailURL(Server server, boolean relative) throws ServiceException {
        String serviceUrl = server.getMailURL();
        
        if (relative) {
            return serviceUrl;
        } else {
            return URLUtil.getServiceURL(server, serviceUrl, true);
        }
    }
    
    private String getAdminURL(Server server, boolean relative) throws ServiceException {
        String serviceUrl = server.getAdminURL();
        
        if (relative) {
            return serviceUrl;
        } else {
            return URLUtil.getAdminURL(server, serviceUrl, true);
        }
    }
}
