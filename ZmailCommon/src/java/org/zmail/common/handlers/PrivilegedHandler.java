/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.common.handlers;

import org.zmail.common.localconfig.LC;
import org.zmail.common.util.NetUtil;

import java.util.Map;

/**
 * This is the class jetty will call back before setuid.
 * We will open sockets on privileged ports if configured to do so.
 * 
 * @author jjzhuang
 *
 */
public class PrivilegedHandler {
	
    private static final String A_zmailLmtpBindPort = "zmailLmtpBindPort";
    private static final String A_zmailLmtpBindAddress = "zmailLmtpBindAddress";
    private static final String A_zmailImapBindPort = "zmailImapBindPort";
    private static final String A_zmailImapBindAddress = "zmailImapBindAddress";
    private static final String A_zmailImapServerEnabled = "zmailImapServerEnabled";
    private static final String A_zmailImapSSLBindPort = "zmailImapSSLBindPort";
    private static final String A_zmailImapSSLBindAddress = "zmailImapSSLBindAddress";
    private static final String A_zmailImapSSLServerEnabled = "zmailImapSSLServerEnabled";
    private static final String A_zmailLmtpServerEnabled = "zmailLmtpServerEnabled";
    
    private static final String A_zmailPop3BindPort = "zmailPop3BindPort";
    private static final String A_zmailPop3BindAddress = "zmailPop3BindAddress";
    private static final String A_zmailPop3ServerEnabled = "zmailPop3ServerEnabled";
    private static final String A_zmailPop3SSLBindPort = "zmailPop3SSLBindPort";
    private static final String A_zmailPop3SSLBindAddress = "zmailPop3SSLBindAddress";
    private static final String A_zmailPop3SSLServerEnabled = "zmailPop3SSLServerEnabled";
    
    private static final String A_zmailSSLExcludeCipherSuites = "zmailSSLExcludeCipherSuites";
    
    private static final String mailboxd_keystore = "mailboxd_keystore";
    private static final String mailboxd_keystore_password = "mailboxd_keystore_password";
    private static final String mailboxd_truststore_password = "mailboxd_truststore_password";
    
    private static final int D_LMTP_BIND_PORT = 7025;
    private static final int D_IMAP_BIND_PORT = 143;
    private static final int D_IMAP_SSL_BIND_PORT = 993;
    private static final int D_POP3_BIND_PORT = 110;
    private static final int D_POP3_SSL_BIND_PORT = 995;

    public static void openPorts(Map<String, Object> attributes) {
        int port;
        String address;
        String[] excludeCiphers;
        try {
        	
        	if (LC.zmail_ssl_enabled.booleanValue()) { //default is true
        		System.setProperty("javax.net.ssl.keyStore", getAttr(attributes, mailboxd_keystore));
        		System.setProperty("javax.net.ssl.keyStorePassword", getAttr(attributes, mailboxd_keystore_password));
        		System.setProperty("javax.net.ssl.trustStorePassword", getAttr(attributes, mailboxd_truststore_password));
        	}
            
            if (getBooleanAttr(attributes, A_zmailPop3ServerEnabled, false)) {
                port = getIntAttr(attributes, A_zmailPop3BindPort, D_POP3_BIND_PORT);
                address = getAttr(attributes, A_zmailPop3BindAddress, null);
                if (LC.nio_pop3_enabled.booleanValue()) {
                    NetUtil.bindNioServerSocket(address, port);
                } else {
                    NetUtil.bindTcpServerSocket(address, port);
                }
            }

            if (getBooleanAttr(attributes, A_zmailPop3SSLServerEnabled, false)) {
                port = getIntAttr(attributes, A_zmailPop3SSLBindPort, D_POP3_SSL_BIND_PORT);
                address = getAttr(attributes, A_zmailPop3SSLBindAddress, null);
                if (LC.nio_pop3_enabled.booleanValue()) {
                    NetUtil.bindNioServerSocket(address, port);
                } else {
                    excludeCiphers = getExcludeCiphers(attributes);
                    NetUtil.bindSslTcpServerSocket(address, port, excludeCiphers);
                }
            }

            if (getBooleanAttr(attributes, A_zmailImapServerEnabled, false)) {
            	port = getIntAttr(attributes, A_zmailImapBindPort, D_IMAP_BIND_PORT);
            	address = getAttr(attributes, A_zmailImapBindAddress, null);
                if (LC.nio_imap_enabled.booleanValue()) {
                    NetUtil.bindNioServerSocket(address, port);
                } else {
                    NetUtil.bindTcpServerSocket(address, port);
                }
            }

            if (getBooleanAttr(attributes, A_zmailImapSSLServerEnabled, false)) {
            	port = getIntAttr(attributes, A_zmailImapSSLBindPort, D_IMAP_SSL_BIND_PORT);
            	address = getAttr(attributes, A_zmailImapSSLBindAddress, null);
            	if (LC.nio_imap_enabled.booleanValue()) {
                    NetUtil.bindNioServerSocket(address, port);
                } else {
                    excludeCiphers = getExcludeCiphers(attributes);
                    NetUtil.bindSslTcpServerSocket(address, port, excludeCiphers);
                }
            }

            if (getBooleanAttr(attributes, A_zmailLmtpServerEnabled, false)) {
            	port = getIntAttr(attributes, A_zmailLmtpBindPort, D_LMTP_BIND_PORT);
            	address = getAttr(attributes, A_zmailLmtpBindAddress, null);
            	NetUtil.bindTcpServerSocket(address, port);
            }
        } catch (Throwable t) {        	
            try {
            	System.err.println("Fatal error: exception while binding to ports");
            	t.printStackTrace(System.err);
            } finally {
                Runtime.getRuntime().halt(1);
            }
        }
	}
	
	private static String getAttr(Map<String, Object> attributes, String name) {
		String val = getAttr(attributes, name, null);
		if (val == null) {
			throw new RuntimeException("missing parameter " + name);
		}
		return val;
	}
	
	private static String getAttr(Map<String, Object> attributes, String name, String defaultValue) {
        Object v = attributes.get(name);
        String s = (String)v;
        if (s != null && s.equals("")) s = null; //null out empty string because jetty-setuid.xml may pass in ""
        return s == null ? defaultValue : s;
	}
	
	private static boolean getBooleanAttr(Map<String, Object> attributes, String name, boolean defaultValue) {
        Object v = attributes.get(name);
        return v == null ? defaultValue : ((Boolean)v).booleanValue();
	}

	private static int getIntAttr(Map<String, Object> attributes, String name, int defaultValue) {
        Object v = attributes.get(name);
        return v == null ? defaultValue : ((Integer)v).intValue();
	}
	
	private static String[] getExcludeCiphers(Map<String, Object> attributes) {
	String ec = getAttr(attributes, A_zmailSSLExcludeCipherSuites, null);
	if (ec != null)
	    return ec.split(" ");
	else
	    return null;
	}
}
