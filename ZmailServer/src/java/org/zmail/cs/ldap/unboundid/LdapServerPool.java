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
package com.zimbra.cs.ldap.unboundid;

import java.util.ArrayList;
import java.util.List;
import javax.net.SocketFactory;

import com.unboundid.ldap.sdk.FailoverServerSet;
import com.unboundid.ldap.sdk.LDAPConnectionOptions;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPURL;
import com.unboundid.ldap.sdk.ServerSet;
import com.unboundid.ldap.sdk.SingleServerSet;

import com.zimbra.cs.ldap.LdapServerConfig;
import com.zimbra.cs.ldap.LdapConnType;
import com.zimbra.cs.ldap.LdapException;

/**
 * Represent a list of LDAP servers.  ZCS will attempt to establish 
 * connections in the order they are provided for failover. 
 * If the first server is unavailable, then it will attempt to connect 
 * to the second, then to the third, etc.
 * 
 * @author pshao
 *
 */
public class LdapServerPool {
    private static final String DUMMY_LDAPI_HOST = "dummy_host";
    private static final int DUMMY_LDAPI_PORT = 1; // SingleServerSet rquires port to be (port > 0) && (port < 65536)
    
    List<LDAPURL> urls;
    String rawUrls; // for logging, space separated URLs
    LdapConnType connType;
    LDAPConnectionOptions connOpts;
    
    ServerSet serverSet;

    public LdapServerPool(LdapServerConfig config) throws LdapException {
        rawUrls = config.getLdapURL();
        
        urls = new ArrayList<LDAPURL>();
        
        String[] ldapUrls = config.getLdapURL().split(" ");
        
        // if connection is ldapi, make sure only one url is configured
        if (config.getConnType() == LdapConnType.LDAPI) {
            if (ldapUrls.length > 1) {
                throw LdapException.INVALID_CONFIG(
                        "can only specify one url for ldapi connection: " + rawUrls, null);
            }
        }
        
        for (String ldapUrl : ldapUrls) {
            try {
                LDAPURL url = new LDAPURL(ldapUrl);
                
                if (LdapConnType.isLDAPI(url.getScheme())) {
                    // make sure host and port are *not* specified
                    if (url.hostProvided() || url.portProvided()) {
                        throw LdapException.INVALID_CONFIG(
                                "host and port must not be specified with ldapi url: " + ldapUrl, null);
                    }
                    
                    /*
                     * ldapi URL does not have host/port, but unboundid SingleServerSet
                     * requires host/port must not be null - even for ldapi.
                     * 
                     * Set dummy host/port to make SingleServerSet happy
                     */
                    url = new LDAPURL(url.getScheme(), DUMMY_LDAPI_HOST, DUMMY_LDAPI_PORT,
                            url.getBaseDN(), url.getAttributes(), url.getScope(), url.getFilter());    
                }
                
                urls.add(url);
            } catch (LDAPException e) {
                throw LdapException.INVALID_CONFIG(e);
            }
        }
        
        this.connType = config.getConnType();
        this.connOpts = LdapConnUtil.getConnectionOptions(config);
        
        SocketFactory socketFactory = 
            LdapConnUtil.getSocketFactory(this.connType, config.sslAllowUntrustedCerts());
        
        this.serverSet = createServerSet(socketFactory);
    }
    
    public List<LDAPURL> getUrls() {
        return urls;
    }
    
    // for logging only
    public String getRawUrls() {
        return rawUrls;
    }
    
    public LdapConnType getConnectionType() {
        return connType;
    }
    
    public ServerSet getServerSet() {
        return serverSet;
    }
    
    private ServerSet createServerSet(SocketFactory socketFactory) {
        
        if (urls.size() == 1) {
            LDAPURL url = urls.get(0);
            if (socketFactory == null) {
                return new SingleServerSet(url.getHost(), url.getPort(), connOpts);
            } else {
                return new SingleServerSet(url.getHost(), url.getPort(), socketFactory, connOpts);
            }
        } else {
            int numUrls = urls.size();
            
            final String[] hosts = new String[numUrls];
            final int[]    ports = new int[numUrls];
            
            for (int i=0; i < numUrls; i++) {
                LDAPURL url = urls.get(i);
                hosts[i] = url.getHost();
                ports[i] = url.getPort();
            }
            
            if (socketFactory == null) {
                return new FailoverServerSet(hosts, ports, connOpts);
            } else {
                return new FailoverServerSet(hosts, ports, socketFactory, connOpts);
            }
        }
    }
}
