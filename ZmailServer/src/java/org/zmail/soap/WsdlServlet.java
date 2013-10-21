/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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

package org.zmail.soap;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.zmail.common.mime.MimeConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.LogFactory;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.httpclient.URLUtil;
import org.zmail.cs.servlet.ZmailServlet;
import org.zmail.soap.util.WsdlGenerator;
import org.zmail.soap.util.WsdlServiceInfo;

/**
 * The wsdl service servlet - serves up files comprising Zmail's WSDL definition
 */
public class WsdlServlet extends ZmailServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -2727046288266393825L;

    @Override
    public void init() throws ServletException {
        LogFactory.init();

        String name = getServletName();
        ZmailLog.soap.info("Servlet " + name + " starting up");
        super.init();
    }

    @Override
    public void destroy() {
        String name = getServletName();
        ZmailLog.soap.info("Servlet " + name + " shutting down");
        super.destroy();
    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp)
    throws javax.servlet.ServletException, IOException {
        ZmailLog.clearContext();
        try {
            addRemoteIpToLoggingContext(req);
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.length() == 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (pathInfo.startsWith("/")) {
                pathInfo = pathInfo.substring(1);
            }
            ZmailLog.soap.debug("WSDL SERVLET Received a GET pathInfo=" + pathInfo);
            if (pathInfo.matches("^[a-zA-Z]+\\.xsd$")) {
                InputStream is = JaxbUtil.class.getResourceAsStream(pathInfo);
                if (is == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                resp.setContentType(MimeConstants.CT_TEXT_XML);
                ByteUtil.copy(is, true /* closeIn */, resp.getOutputStream(), false /* closeOut */);
            } else {
                if (!WsdlGenerator.handleRequestForWsdl(pathInfo, resp.getOutputStream(),
                        getSoapURL(), getSoapAdminURL())) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } finally {
            ZmailLog.clearContext();
        }
    }
    
    private static String getSoapAdminURL() {
        try {
            return URLUtil.getAdminURL(Provisioning.getInstance().getLocalServer());
        } catch (ServiceException e) {
            return WsdlServiceInfo.localhostSoapAdminHttpsURL;
        }
    }

    private static String getSoapURL() {
        try {
            return URLUtil.getServiceURL(Provisioning.getInstance().getLocalServer(),
                    AccountConstants.USER_SERVICE_URI, true);
        } catch (ServiceException e) {
            return WsdlServiceInfo.localhostSoapHttpURL;
        }
    }


}
