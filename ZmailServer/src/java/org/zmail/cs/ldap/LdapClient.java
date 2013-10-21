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
package org.zmail.cs.ldap;

import com.google.common.collect.Lists;
import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.ldap.LdapServerConfig.ExternalLdapConfig;
import org.zmail.cs.ldap.LdapServerConfig.GenericLdapConfig;
import org.zmail.cs.ldap.ZSearchScope.ZSearchScopeFactory;
import org.zmail.cs.ldap.unboundid.InMemoryLdapServer;
import org.zmail.cs.ldap.unboundid.UBIDLdapClient;
import org.zmail.cs.util.Zmail;

/**
 * @author pshao
 */
public abstract class LdapClient {
    
    private static LdapClient ldapClient;
    private static boolean ALWAYS_USE_MASTER = false;
    
     static synchronized LdapClient getInstance() {
        if (ldapClient == null) {
            
            if (InMemoryLdapServer.isOn()) {
                try {
                    InMemoryLdapServer.start(InMemoryLdapServer.ZIMBRA_LDAP_SERVER, 
                            new InMemoryLdapServer.ServerConfig(
                            Lists.newArrayList(LdapConstants.ATTR_dc + "=" + InMemoryLdapServer.UNITTEST_BASE_DOMAIN_SEGMENT)));
                } catch (Exception e) {
                    ZmailLog.system.error("could not start InMemoryLdapServer", e);
                }
            }
            
            String className = LC.zmail_class_ldap_client.value();
            if (className != null && !className.equals("")) {
                try {
                    ldapClient = (LdapClient) Class.forName(className).newInstance();
                } catch (Exception e) {
                    ZmailLog.system.error("could not instantiate LDAP client '" + className + 
                            "'; defaulting to JNDI LDAP SDK", e);
                }
            }
            if (ldapClient == null) {
                ldapClient = new UBIDLdapClient();
            }
            
            try {
                ldapClient.init(ALWAYS_USE_MASTER);
            } catch (LdapException e) {
                Zmail.halt("failed to initialize LDAP client", e);
            }
        }
        return ldapClient;
    }
     
    private static synchronized void unsetInstance() {
        ldapClient = null;
    }
    
    
    public static synchronized void masterOnly() {
        ALWAYS_USE_MASTER = true;
        
        if (ldapClient != null) {
            // already initialized
            ldapClient.alwaysUseMaster();
        }
    }
    
    public static void initialize() {
        LdapClient.getInstance();
    }
    
    // called from unittest only
    public static void shutdown() {
        LdapClient.getInstance().terminate();
        unsetInstance();
    }
    
    public static ZLdapContext toZLdapContext(
            org.zmail.cs.account.Provisioning prov, ILdapContext ldapContext) {
        
        if (!(getInstance() instanceof UBIDLdapClient)) {
            Zmail.halt("LdapClient instance is not UBIDLdapClient", 
                    ServiceException.FAILURE("internal error, wrong ldap context instance", null));
        }
        
        // just a safety check, this should really not happen at this point
        if (ldapContext != null && !(ldapContext instanceof ZLdapContext)) {
            Zmail.halt("ILdapContext instance is not ZLdapContext", 
                    ServiceException.FAILURE("internal error, wrong ldap context instance", null));
        }
        
        return (ZLdapContext)ldapContext;
    }
    
    
    /*
     * ========================================================
     * static methods just to short-hand the getInstance() call
     * ========================================================
     */
    public static void waitForLdapServer() {
        getInstance().waitForLdapServerImpl();
    }
    
    public static ZLdapContext getContext(LdapUsage usage) throws ServiceException {
        return getContext(LdapServerType.REPLICA, usage);
    }
    
    public static ZLdapContext getContext(LdapServerType serverType, LdapUsage usage) 
    throws ServiceException {
        return getInstance().getContextImpl(serverType, usage);
    }
    
    public static ZLdapContext getContext(LdapServerType serverType, boolean useConnPool,
            LdapUsage usage) 
    throws ServiceException {
        return getInstance().getContextImpl(serverType, useConnPool, usage);
    }
    
    /**
     * For zmconfigd only.  
     * 
     * zmconfigd uses ldapi connection with root LDAP bind DN/password to bind to 
     * Zmail OpenLdap, whereas LdapClient.getContext() methods use LC keys for 
     * the URL/bind DN/password.
     * 
     * Changing LC keys in  zmconfigd is not an option, because it also uses 
     * LdapProvisioing, which uses the LC settings.
     * 
     * We could have zmconfigd call getExternalContext with a ExternalLdapConfig, which 
     * takes URL/bind DN/password from parameters.  But the name "external" is misleading.
     * 
     * This method id just a wrapper around LdapClient.getExternalContext, the sole 
     * purpose is masking out the "external" in method/parameter names.
     * 
     * @param ldapConfig
     * @param usage
     * @return
     * @throws ServiceException
     */
    public static ZLdapContext getContext(GenericLdapConfig ldapConfig,
            LdapUsage usage) 
    throws ServiceException {
        return getInstance().getExternalContextImpl(ldapConfig, usage);
    }
    
    public static ZLdapContext getExternalContext(ExternalLdapConfig ldapConfig,
            LdapUsage usage) 
    throws ServiceException {
        return getInstance().getExternalContextImpl(ldapConfig, usage);
    }
    
    public static void closeContext(ZLdapContext lctxt) {
        if (lctxt != null) {
            lctxt.closeContext();
        }
    }
    
    public static ZMutableEntry createMutableEntry() {
        return getInstance().createMutableEntryImpl();
    }
    
    public static void externalLdapAuthenticate(String urls[], boolean wantStartTLS, 
            String bindDN, String password, String note) 
    throws ServiceException {
        getInstance().externalLdapAuthenticateImpl(urls, wantStartTLS, 
                bindDN, password, note);
    }
    
    /**
     * LDAP authenticate to the Zmail LDAP server.
     * Used when stored password is not SSHA.
     * 
     * @param principal
     * @param password
     * @param note
     * @throws ServiceException
     */
    public static void zmailLdapAuthenticate(String bindDN, String password) 
    throws ServiceException {
        getInstance().zmailLdapAuthenticateImpl(bindDN, password);
    }
    
    /*
     * ========================================================
     * abstract methods
     * ========================================================
     */
    protected void init(boolean alwaysUseMaster) throws LdapException {
        ZSearchScope.init(getSearchScopeFactoryInstance());
        ZLdapFilterFactory.setInstance(getLdapFilterFactoryInstance());
    }
    
    protected abstract void terminate();
    
    protected abstract void alwaysUseMaster();
    
    protected abstract ZSearchScopeFactory getSearchScopeFactoryInstance(); 
    
    protected abstract ZLdapFilterFactory getLdapFilterFactoryInstance() 
    throws LdapException;
    
    protected abstract void waitForLdapServerImpl();
    
    protected abstract ZLdapContext getContextImpl(LdapServerType serverType, LdapUsage usage) 
    throws ServiceException;
    
    protected abstract ZLdapContext getContextImpl(LdapServerType serverType, 
            boolean useConnPool, LdapUsage usage) 
    throws ServiceException;
    
    protected abstract ZLdapContext getExternalContextImpl(ExternalLdapConfig ldapConfig,
            LdapUsage usage) 
    throws ServiceException;
    
    protected abstract ZMutableEntry createMutableEntryImpl();
    
    protected abstract ZSearchControls createSearchControlsImpl(
            ZSearchScope searchScope, int sizeLimit, String[] returnAttrs);
    
    protected abstract void externalLdapAuthenticateImpl(String urls[], 
            boolean wantStartTLS, String bindDN, String password, String note) 
    throws ServiceException;
    
    protected abstract void zmailLdapAuthenticateImpl(String bindDN, String password) 
    throws ServiceException;
}
