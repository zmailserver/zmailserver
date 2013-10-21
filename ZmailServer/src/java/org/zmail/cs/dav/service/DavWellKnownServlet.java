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
package org.zmail.cs.dav.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zmail.common.util.ZmailLog;
import org.zmail.cs.dav.DavProtocol;
import org.zmail.cs.servlet.ZmailServlet;

@SuppressWarnings("serial")
public class DavWellKnownServlet extends ZmailServlet {
    
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ZmailLog.clearContext();
        addRemoteIpToLoggingContext(req);
        ZmailLog.addUserAgentToContext(req.getHeader(DavProtocol.HEADER_USER_AGENT));
        String path = req.getPathInfo();
        if (path.equalsIgnoreCase("/caldav") || path.equalsIgnoreCase("/carddav")) {
            resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            resp.setHeader("Location", req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + DavServlet.DAV_PATH);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }    
    
}
