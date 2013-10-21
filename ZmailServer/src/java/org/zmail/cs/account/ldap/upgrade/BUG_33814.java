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
package org.zmail.cs.account.ldap.upgrade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.account.ProvisioningConstants;
import org.zmail.common.account.ZAttrProvisioning;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZLdapContext;

public class BUG_33814 extends UpgradeOp {

    private static final String TLSLEVEL_ENCRYPT = ZAttrProvisioning.MtaTlsSecurityLevel.may.toString(); // we don't support encryp yet, see http://bugzilla.zmail.com/show_bug.cgi?id=33814#c12
    private static final String TLSLEVEL_MAY     = ZAttrProvisioning.MtaTlsSecurityLevel.may.toString();
    private static final String TLSLEVEL_NONE    = ZAttrProvisioning.MtaTlsSecurityLevel.none.toString();
    
    @Override
    void doUpgrade() throws ServiceException {
        ZLdapContext zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
        try {
            doGlobalConfig(zlc);
            doAllServers(zlc);
        } finally {
            LdapClient.closeContext(zlc);
        }

    }
    
    /*
     * On global config:
     *     - set to "may" if both are TRUE
     *     - set to "none" for all other cases, including the cases when
     *       one or both of them are not set
     */
    private void doMtaTlsSecurityLevelOnGlobalConfig(Entry entry, Map<String, Object> attrValues) {
        
        String zmailMtaAuthEnabled      = entry.getAttr(Provisioning.A_zmailMtaAuthEnabled, false);
        String zmailMtaTlsAuthOnly      = entry.getAttr(Provisioning.A_zmailMtaTlsAuthOnly, false);

        String value = null;
        
        if (ProvisioningConstants.TRUE.equals(zmailMtaAuthEnabled)) {
            if (ProvisioningConstants.TRUE.equals(zmailMtaTlsAuthOnly))
                value = TLSLEVEL_ENCRYPT;
            else
                value = TLSLEVEL_MAY;
        } else
            value = TLSLEVEL_NONE;
        
        attrValues.put(Provisioning.A_zmailMtaTlsSecurityLevel, value);
    }
    
    /*
                                           zmailMtaTlsAuthOnly
                                            TRUE        FALSE
           ---------------------------------------------------------------
           zmailMtaAuthEnabled TRUE       may(see *)    may
           zmailMtaAuthEnabled FALSE      none          none

    */
    private void doMtaTlsSecurityLevelOnServer(Entry entry, Map<String, Object> attrValues) {
        
        // value on server entry
        String zmailMtaAuthEnabledOnServer = entry.getAttr(Provisioning.A_zmailMtaAuthEnabled, false);
        String zmailMtaTlsAuthOnlyOnServer = entry.getAttr(Provisioning.A_zmailMtaTlsAuthOnly, false);
        
        // value on server entry, or if not set on server, value on global config 
        String zmailMtaAuthEnabled = entry.getAttr(Provisioning.A_zmailMtaAuthEnabled);
        String zmailMtaTlsAuthOnly = entry.getAttr(Provisioning.A_zmailMtaTlsAuthOnly);

        String value = null;
        
        if (ProvisioningConstants.TRUE.equals(zmailMtaAuthEnabledOnServer)) {
            if (ProvisioningConstants.TRUE.equals(zmailMtaTlsAuthOnly))
                value = TLSLEVEL_ENCRYPT;
            else
                value = TLSLEVEL_MAY;
                
        } else if (ProvisioningConstants.FALSE.equals(zmailMtaAuthEnabledOnServer)) {
            value = TLSLEVEL_NONE;
        } else {
            // zmailMtaAuthEnabled is not set on server
            
            // see what's on global config
            if (ProvisioningConstants.TRUE.equals(zmailMtaAuthEnabled)) {
                if (ProvisioningConstants.TRUE.equals(zmailMtaTlsAuthOnlyOnServer))
                    value = TLSLEVEL_ENCRYPT;
                else if (ProvisioningConstants.FALSE.equals(zmailMtaTlsAuthOnlyOnServer))
                    value = TLSLEVEL_MAY;
                // else zmailMtaTlsAuthOnly is also not set on server, do not 
                // set zmailMtaTlsSecurityLevel and just let it inherit from global config
            } else {
                // zmailMtaAuthEnabled on global config is FALSE or is not set
                // in this case zmailMtaTlsSecurityLevel must be NONE on global config
                // do not set zmailMtaTlsSecurityLevel on server regardless what
                // zmailMtaTlsAuthOnly is(TRUE, FALSE, or not set), 
                // just let it inherit from global config, which will be NONE
            }
        }
            
        if (value != null)
            attrValues.put(Provisioning.A_zmailMtaTlsSecurityLevel, value);
    }
    
    private void doEntry(ZLdapContext zlc, Entry entry, String entryName, AttributeClass klass) throws ServiceException {
        
        printer.println();
        printer.println("------------------------------");
        printer.println("Checking " + entryName + ": ");
        
        StringBuilder msg = new StringBuilder();
        try {
            Map<String, Object> attrValues = new HashMap<String, Object>();
            
            // old attrs
            // get value on the entry, do not pull in inherited value 
            String zmailMtaAuthEnabled      = entry.getAttr(Provisioning.A_zmailMtaAuthEnabled, false);
            String zmailMtaTlsAuthOnly      = entry.getAttr(Provisioning.A_zmailMtaTlsAuthOnly, false);
            
            printer.println("zmailMtaAuthEnabled: " + zmailMtaAuthEnabled);
            printer.println("zmailMtaTlsAuthOnly: " + zmailMtaTlsAuthOnly);
            printer.println();
            
            // new attrs
            String zmailMtaTlsSecurityLevel = entry.getAttr(Provisioning.A_zmailMtaTlsSecurityLevel, false);
            String zmailMtaSaslAuthEnable   = entry.getAttr(Provisioning.A_zmailMtaSaslAuthEnable, false);
            
            // upgrade zmailMtaTlsSecurityLevel
            // set it only if it does not already have a value
            if (zmailMtaTlsSecurityLevel == null) {
                if (entry instanceof Server)
                    doMtaTlsSecurityLevelOnServer(entry, attrValues);
                else
                    doMtaTlsSecurityLevelOnGlobalConfig(entry, attrValues);
            } else {
                printer.println("Not updating zmailMtaTlsSecurityLevel because there is already a value: " + zmailMtaTlsSecurityLevel);
            }
            
            // upgrade zmailMtaSaslAuthEnable
            // set it only if it does not already have a value
            if (zmailMtaSaslAuthEnable == null) {
                if (zmailMtaAuthEnabled != null) {
                    attrValues.put(Provisioning.A_zmailMtaSaslAuthEnable, zmailMtaAuthEnabled);
                }
            } else {
                printer.println("Not updating zmailMtaSaslAuthEnable because there is already a value: " + zmailMtaSaslAuthEnable);
            }
            
            if (!attrValues.isEmpty()) {
                boolean first = true;
                for (Map.Entry<String, Object> attr : attrValues.entrySet()) {
                    if (!first)
                        msg.append(", ");
                    msg.append(attr.getKey() + "=>" + (String)attr.getValue());
                    first = false;
                }
                
                printer.println("Updating " + entryName + ": " + msg.toString());
                modifyAttrs(zlc, entry, attrValues);
            }
        } catch (ServiceException e) {
            // log the exception and continue
            printer.println("Caught ServiceException while modifying " + entryName + ": " + msg.toString());
            printer.printStackTrace(e);
        }
    }

    private void doGlobalConfig(ZLdapContext zlc) throws ServiceException {
        Config config = prov.getConfig();
        doEntry(zlc, config, "global config", AttributeClass.globalConfig);
    }
    
    private void doAllServers(ZLdapContext zlc) throws ServiceException {
        List<Server> servers = prov.getAllServers();
        
        for (Server server : servers)
            doEntry(zlc, server, "server " + server.getName(), AttributeClass.server);
    }

}
