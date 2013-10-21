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

package org.zmail.cs.servlet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.security.SpnegoLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.servlet.ServletContextHandler;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AccountServiceException.AuthFailedServiceException;
import org.zmail.cs.service.authenticator.SSOAuthenticator;
import org.zmail.cs.service.authenticator.SpnegoAuthenticator;
import org.zmail.cs.service.authenticator.SSOAuthenticator.SSOAuthenticatorServiceException;

public class SpnegoFilter implements Filter {
    private static final String PARAM_PASS_THRU_ON_FAILURE_URI = "passThruOnFailureUri";
    
    private URI passThruOnFailureUri = null;
    private SpnegoLoginService spnegoUserRealm = null;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String uri = filterConfig.getInitParameter(PARAM_PASS_THRU_ON_FAILURE_URI);
        if (uri != null) {
            try {
                passThruOnFailureUri = new URI(uri);
            } catch (URISyntaxException e) {
                throw new ServletException("Malformed URI: " + uri, e);
            }
            
        }
        spnegoUserRealm = getSpnegoUserRealm(filterConfig);
    }
    
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest hreq = (HttpServletRequest) req;
        HttpServletResponse hresp = (HttpServletResponse) resp;
        
        try {
            try {
                authenticate(hreq, hresp);
            } catch (SSOAuthenticatorServiceException e) {
                if (SSOAuthenticatorServiceException.SENT_CHALLENGE.equals(e.getCode())) {
                    return;
                } else {
                    throw e;
                }
            }  
            chain.doFilter(req, resp);
        } catch (ServiceException e) {
            ZmailServlet.addRemoteIpToLoggingContext(hreq);
            ZmailServlet.addUAToLoggingContext(hreq);
            if (e instanceof AuthFailedServiceException) {
                AuthFailedServiceException afe = (AuthFailedServiceException)e;
                ZmailLog.account.info("spnego auth failed: " + afe.getMessage() + afe.getReason(", %s"));
            } else {
                ZmailLog.account.info("spnego auth failed: " + e.getMessage());
            }
            ZmailLog.account.debug("spnego auth failed", e);
            ZmailLog.clearContext();
            
            if (passThruOnAuthFailure(hreq)) {
                chain.doFilter(req, resp);
            } else {    
                hresp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            }
        }
        
    }
    
    private boolean passThruOnAuthFailure(HttpServletRequest hreq) {
        if (passThruOnFailureUri != null) {
            try {
                URI reqUri = new URI(hreq.getRequestURI());
                return passThruOnFailureUri.equals(reqUri);
            } catch (URISyntaxException e) {
            }
        }
        return false;
    }

    private void authenticate(HttpServletRequest req, HttpServletResponse resp) 
    throws ServiceException {
        if (spnegoUserRealm == null) {
            throw ServiceException.FAILURE("no spnego user realm", null);
        }
        SSOAuthenticator authenticator = new SpnegoAuthenticator(req, resp, spnegoUserRealm);
        authenticator.authenticate();
    }
    
    private SpnegoLoginService getSpnegoUserRealm(FilterConfig filterConfig) {
        // ServletContext servletContext = getServletContext(); 
        ServletContext servletContext = filterConfig.getServletContext();
        if (servletContext instanceof ServletContextHandler.Context) {
        	ServletContextHandler.Context sContext = (ServletContextHandler.Context)servletContext;
            // get the WebAppContext
            ServletContextHandler contextHandler = (ServletContextHandler) sContext.getContextHandler();
            LoginService realm = contextHandler.getSecurityHandler().getLoginService();
            if (realm != null && realm instanceof SpnegoLoginService) {
                ZmailLog.account.debug("Found spnego user realm: [" + realm.getName() + "]");
                return (SpnegoLoginService)realm;
            }
        }
        
        // throw ServiceException.FAILURE("no spnego user realm", null);
        return null;
    }

}
