/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
 * Created on 2005. 4. 27.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.zmail.cs.httpclient;

import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Provisioning.MailMode;
import org.zmail.cs.account.Server;
import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;

/**
 * @author jhahm
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class URLUtil {

    public static final String PROTO_HTTP  = "http";
    public static final String PROTO_HTTPS = "https";
    
    private static int DEFAULT_HTTP_PORT = 80;
    private static int DEFAULT_HTTPS_PORT = 443;
    
    /**
     * Return the URL where SOAP service is available for given store server.
     * 
     * @see getMailURL()
     */
    public static String getSoapURL(Server server, boolean preferSSL) throws ServiceException {
        return URLUtil.getServiceURL(server, AccountConstants.USER_SERVICE_URI, preferSSL);
    }
    
    public static String getSoapPublicURL(Server server, Domain domain, boolean preferSSL) throws ServiceException {
        return URLUtil.getPublicURLForDomain(server, domain, AccountConstants.USER_SERVICE_URI, preferSSL);  
    }
    
    /**
     * Returns absolute URL with scheme, host, and port for admin app on server.
     * Admin app only runs over SSL.
     * @param server
     * @param path what follows port number; begins with slash
     * @return
     */
    public static String getAdminURL(Server server, String path) {
        String hostname = server.getAttr(Provisioning.A_zmailServiceHostname);
        int port = server.getIntAttr(Provisioning.A_zmailAdminPort, 0);
        StringBuffer sb = new StringBuffer(128);
        sb.append(LC.zmail_admin_service_scheme.value()).append(hostname).append(":").append(port).append(path);
        return sb.toString();
    }
    
    /**
     * Returns absolute URL with scheme, host, and port for admin app on server.
     * Admin app only runs over SSL.
     * @param server
     * @param path what follows port number; begins with slash
     * @checkPort verify if the port is valid
     * @return
     */
    public static String getAdminURL(Server server, String path, boolean checkPort) throws ServiceException {
        String hostname = server.getAttr(Provisioning.A_zmailServiceHostname);
        int port = server.getIntAttr(Provisioning.A_zmailAdminPort, 0);
        if (checkPort && port <= 0)
            throw ServiceException.FAILURE("server " + server.getName() + " does not have admin port enabled", null);
        StringBuffer sb = new StringBuffer(128);
        sb.append(LC.zmail_admin_service_scheme.value()).append(hostname).append(":").append(port).append(path);
        return sb.toString();
    }    


    /**
     * Returns absolute URL with scheme, host, and port for admin app on server.
     * Admin app only runs over SSL. Uses port from localconfig.
     * @param server hostname
     * @return
     */
    public static String getAdminURL(String hostname) {
        int port = (int) LC.zmail_admin_service_port.longValue();
        StringBuffer sb = new StringBuffer(128);
        sb.append(LC.zmail_admin_service_scheme.value()).append(hostname).append(":").append(port).append(AdminConstants.ADMIN_SERVICE_URI);
        return sb.toString();
    }
    
    /**
     * Returns absolute URL with scheme, host, and port for admin app on server.
     * Admin app only runs over SSL.
     * @param server
     * @param path what follows port number; begins with slash
     * @return
     */
    public static String getAdminURL(Server server) {
        return getAdminURL(server, AdminConstants.ADMIN_SERVICE_URI);
    }
    
    /**
     * Utility method to translate zmailMtaAuthHost -> zmailMtaAuthURL.
     * 
     * Not the best place for this method, but do not want to pollute
     * Provisioning with utility methods either.
     */
    public static String getMtaAuthURL(String authHost) throws ServiceException {
        for (Server server : Provisioning.getInstance().getAllServers()) {
            String serviceName = server.getAttr(Provisioning.A_zmailServiceHostname, null);
            if (authHost.equalsIgnoreCase(serviceName)) {
                return URLUtil.getSoapURL(server, true);
            }
        }
        throw ServiceException.INVALID_REQUEST("specified " + Provisioning.A_zmailMtaAuthHost + " does not correspond to a valid service hostname: " + authHost, null);
    }
   
    /**
     * Returns absolute public URL with scheme, host, and port for mail app on server.
     * 
     * @param server
     * @param domain
     * @param path what follows port number; begins with slash
     * @param preferSSL if both SSL and and non-SSL are available, whether to prefer SSL 
     * @return desired URL
     */
    public static String getPublicURLForDomain(Server server, Domain domain, String path, boolean preferSSL) throws ServiceException {
        String publicURLForDomain = getPublicURLForDomain(domain, path);
        if (publicURLForDomain != null)
            return publicURLForDomain;
        
        // fallback to server setting if domain is not configured with public service hostname
        return URLUtil.getServiceURL(server, path, preferSSL);
    }
    
    private static String getPublicURLForDomain(Domain domain, String path) {
        if (domain == null)
            return null;
        
        String hostname = domain.getAttr(Provisioning.A_zmailPublicServiceHostname, null);
        if (hostname == null)
            return null;
        
        String proto = domain.getAttr(Provisioning.A_zmailPublicServiceProtocol, PROTO_HTTP);
        
        int defaultPort = PROTO_HTTP.equals(proto) ? DEFAULT_HTTP_PORT : DEFAULT_HTTPS_PORT;
        int port = domain.getIntAttr(Provisioning.A_zmailPublicServicePort, defaultPort);
        
        boolean printPort = ((PROTO_HTTP.equals(proto) && port != DEFAULT_HTTP_PORT) ||
                             (PROTO_HTTPS.equals(proto) && port != DEFAULT_HTTPS_PORT));
        
        StringBuilder buf = new StringBuilder();
        buf.append(proto).append("://").append(hostname);
        if (printPort)
            buf.append(":").append(port);
        buf.append(path);
        return buf.toString();
    }
    
    public static String getServiceURL(Server server, String path, boolean useSSL) throws ServiceException {
        
        String hostname = server.getAttr(Provisioning.A_zmailServiceHostname);
        if (hostname == null)
            throw ServiceException.INVALID_REQUEST("server " + server.getName() + " does not have " + Provisioning.A_zmailServiceHostname, null);
        
    	String modeString = server.getAttr(Provisioning.A_zmailMailMode, null);
    	if (modeString == null)
    		throw ServiceException.INVALID_REQUEST("server " + server.getName() + " does not have " + Provisioning.A_zmailMailMode + " set, maybe it is not a store server?", null);
        MailMode mailMode = Provisioning.MailMode.fromString(modeString);
        	
    	String proto;
    	int port;
    	if ((mailMode != MailMode.http && useSSL) || mailMode == MailMode.https) {
    	    proto = PROTO_HTTPS;
        	port = server.getIntAttr(Provisioning.A_zmailMailSSLPort, DEFAULT_HTTPS_PORT);
    	} else {
    	    proto = PROTO_HTTP;
        	port = server.getIntAttr(Provisioning.A_zmailMailPort, DEFAULT_HTTP_PORT);
    	}

    	StringBuilder buf = new StringBuilder();
    	buf.append(proto).append("://").append(hostname);
        buf.append(":").append(port);
        buf.append(path);
    	return buf.toString();
    }
    
    public static boolean reverseProxiedMode(Server server) throws ServiceException {
        String referMode = server.getAttr(Provisioning.A_zmailMailReferMode, "wronghost");
        return Provisioning.MAIL_REFER_MODE_REVERSE_PROXIED.equals(referMode);
    }
}
