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
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.AccountServiceException.AuthFailedServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.auth.AuthContext;
import org.zmail.cs.service.authenticator.SSOAuthenticator.ZmailPrincipal;

public class SpnegoAuthServlet extends SSOServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ZmailLog.clearContext();
        addRemoteIpToLoggingContext(req);
        addUAToLoggingContext(req);
        
        boolean isAdminRequest = false;
        boolean isFromZCO = false;
        
        try {
            isAdminRequest = isOnAdminPort(req);
            isFromZCO = isFromZCO(req);
            
            Principal principal = req.getUserPrincipal();
            
            if (principal == null) {
                throw AuthFailedServiceException.AUTH_FAILED("no principal");
            }
            
            if (!(principal instanceof ZmailPrincipal)) {
                throw AuthFailedServiceException.AUTH_FAILED(principal.getName(), "not ZmailPrincipal", (Throwable)null);
            }
            
            ZmailPrincipal zmailPrincipal = (ZmailPrincipal)principal;   
            AuthToken authToken = authorize(req, AuthContext.Protocol.spnego, zmailPrincipal, isAdminRequest);
                
            if (isFromZCO) {
                setAuthTokenCookieAndReturn(req, resp, authToken);
            } else {
                setAuthTokenCookieAndRedirect(req, resp, zmailPrincipal.getAccount(), authToken);
            }
            
        } catch (ServiceException e) {
            if (e instanceof AuthFailedServiceException) {
                AuthFailedServiceException afe = (AuthFailedServiceException)e;
                ZmailLog.account.info("spnego auth failed: " + afe.getMessage() + afe.getReason(", %s"));
            } else {
                ZmailLog.account.info("spnego auth failed: " + e.getMessage());
            }
            ZmailLog.account.debug("spnego auth failed", e);
            
            if (isFromZCO) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            } else {
                try {
                    redirectToErrorPage(req, resp, isAdminRequest, 
                            Provisioning.getInstance().getConfig().getSpnegoAuthErrorURL());
                } catch (ServiceException se) {
                    ZmailLog.account.info("failed to redirect to error page: " + se.getMessage());
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
                }
            }
        }
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected boolean redirectToRelativeURL() {
        return true;
    }
}
