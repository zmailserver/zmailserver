/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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

/*
 * Created on Oct 26, 2005
 *
 */
package org.zmail.cs.extension;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.servlet.ZmailServlet;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;

/**
 * Registers {@link ExtensionHttpHandler} for extensions and dispatches HTTP requests to them.
 * This servlet accepts requests sent to /service/extension/&lt;ext name>/&lt;handler path> URI,
 * where &lt;ext name>/&lt;handler path> is used to locate the handler whose {@link ExtensionHttpHandler#getPath() }
 * has the same path.
 * 
 * @author kchen
 *
 */
public class ExtensionDispatcherServlet extends ZmailServlet {
    private static Map sHandlers = Collections.synchronizedMap(new HashMap());
    public static final String EXTENSION_PATH = "/service/extension";
    
    /**
     * Registers an HTTP handler for the extension.
     * @param ext the extension to which the handler belongs
     * @param handler
     * @throws ServiceException
     */
    public static void register(ZmailExtension ext, ExtensionHttpHandler handler) throws ServiceException {
        handler.init(ext);
        String name = handler.getPath();
        synchronized (sHandlers) {
            if (sHandlers.containsKey(name))
                throw ServiceException.FAILURE("HTTP handler already registered: " + name, null);
            sHandlers.put(name, handler);
            ZmailLog.extensions.info("registered handler at " + name);
        }
    }
    
    /**
     * Gets the handler for handling HTTP requests for an extension.
     * 
     * @param path path in request URI in the form of <ext name>/<handler path>/*
     * @return
     */
    public static ExtensionHttpHandler getHandler(String path) {
        ExtensionHttpHandler handler = null;
        int slash = -1;
        do {
            handler = (ExtensionHttpHandler) sHandlers.get(path);
            if (handler == null) {
                slash = path.lastIndexOf('/');
                if (slash != -1) {
                    path = path.substring(0, slash);
                }
            }
        } while (handler == null && slash > 0);
        return handler;
    }
    
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ZmailLog.clearContext();
        try {
            ExtensionHttpHandler handler = getHandler(req);
            String method = req.getMethod();
            if ("OPTIONS".equals(method)) {
            	handler.doOptions(req, resp);
            } else if ("GET".equals(method)) {
                handler.doGet(req, resp);
            } else if ("POST".equals(method)) {
                handler.doPost(req, resp);
            } else {
                throw new ServletException("request method " + method + " not supported");
            }
        } catch (ServiceException e) {
            throw new ServletException("failed to handle GET request", e);
        }
    }
    
    /**
     * Calls the destroy method for handlers belonging to the extension, and unregisters them.
     * @param ext
     */
    public static void unregister(ZmailExtension ext) {
        synchronized(sHandlers) {
            for (Iterator it = sHandlers.keySet().iterator(); it.hasNext(); ) {
                String path = (String) it.next();
                if (path.startsWith("/" + ext.getName())) {
                    ExtensionHttpHandler handler = (ExtensionHttpHandler) sHandlers.get(path);
                    try {
                        handler.destroy();
                    } catch (Exception e) {
                        ZmailLog.extensions.warn("Error in destroy for handler " + handler.getClass(), e);
                    } finally {
                        it.remove();
                    }
                }
            }
        }
    }
       
    private ExtensionHttpHandler getHandler(HttpServletRequest req) throws ServiceException {
        String uri = req.getRequestURI();
        int pos = uri.indexOf(EXTENSION_PATH);
        String extPath = uri.substring(pos + EXTENSION_PATH.length());
        if (extPath.length() == 0)
            throw ServiceException.INVALID_REQUEST("Invalid request: " + uri, null);
        ZmailLog.extensions.debug("getting handler registered at " + extPath);
        ExtensionHttpHandler handler = getHandler(extPath);
        if (handler == null)
            throw ServiceException.FAILURE("Extension HTTP handler not found at " + extPath, null);
        if (handler.hideFromDefaultPorts()) {
        	Server server = Provisioning.getInstance().getLocalServer();
        	int port = req.getLocalPort();
        	int mailPort = server.getIntAttr(Provisioning.A_zmailMailPort, 0);
        	int mailSslPort = server.getIntAttr(Provisioning.A_zmailMailSSLPort, 0);
        	if (port == mailPort || port == mailSslPort)
        		throw ServiceException.FAILURE("extension not supported on this port", null);
        }
        return handler;
    }
}
