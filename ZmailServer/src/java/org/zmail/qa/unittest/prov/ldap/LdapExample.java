/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.qa.unittest.prov.ldap;

import org.zmail.common.localconfig.LC;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZLdapFilter;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.ldap.ZMutableEntry;
import org.zmail.cs.ldap.ZSearchControls;
import org.zmail.cs.ldap.ZSearchResultEntry;
import org.zmail.cs.ldap.ZSearchResultEnumeration;
import org.zmail.cs.ldap.ZSearchScope;
import org.zmail.cs.ldap.LdapException.LdapSizeLimitExceededException;
import org.zmail.cs.ldap.LdapServerConfig.GenericLdapConfig;
import org.zmail.cs.ldap.ZLdapFilterFactory.FilterId;

public class LdapExample {
    
    private GenericLdapConfig getLdapConfig() {
        String ldapUrl = "ldapi:///";
        boolean startTLSEnabled = false;
        String bindDN = "cn=config";
        String bindPassword = LC.ldap_root_password.value();
        
        return new GenericLdapConfig(ldapUrl, startTLSEnabled, bindDN, bindPassword);
    }
    
    public void getAttributes() throws Exception {
        
        GenericLdapConfig ldapConfig = getLdapConfig();
        
        ZLdapContext zlc = null;
        try {
            zlc = LdapClient.getContext(ldapConfig, LdapUsage.SEARCH);
            
            /*
             * get attributes zmailId, cn and description on DN "cn=default,cn=cos,cn=zmail"
             */
            ZAttributes attrs = zlc.getAttributes("cn=default,cn=cos,cn=zmail", new String[]{"zmailId", "cn", "description"});
            String zmailId = attrs.getAttrString("zmailId");
                        
            /*
             * get all attributes on DN "cn=default,cn=cos,cn=zmail"
             */
            ZAttributes allAttrs = zlc.getAttributes("cn=default,cn=cos,cn=zmail", null);
            
        } finally {
            // Note: this is important!! 
            LdapClient.closeContext(zlc);
        }
    }
    
    public void search() throws Exception {
        String base = "cn=servers,cn=zmail";
        String filter = "(objectClass=zmailServer)";
        String returnAttrs[] = new String[]{"objectClass", "cn"};
        
        ZLdapFilter zFilter = ZLdapFilterFactory.getInstance().fromFilterString(FilterId.ZMCONFIGD, filter);
        
        ZSearchControls searchControls = ZSearchControls.createSearchControls(
                ZSearchScope.SEARCH_SCOPE_SUBTREE, ZSearchControls.SIZE_UNLIMITED, 
                returnAttrs);
        
        GenericLdapConfig ldapConfig = getLdapConfig();
        
        ZLdapContext zlc = null;
        try {
            zlc = LdapClient.getContext(ldapConfig, LdapUsage.SEARCH);
            
            ZSearchResultEnumeration ne = zlc.searchDir(base, zFilter, searchControls);
            while (ne.hasMore()) {
                ZSearchResultEntry entry = ne.next();
                
                String dn = entry.getDN();
                
                ZAttributes attrs = entry.getAttributes();
                String cn = attrs.getAttrString("cn");
                String[] objectClasses = attrs.getMultiAttrString("objectClass");
                
                System.out.println("dn = " + dn);
                System.out.println("cn = " + cn);
                for (String objectClass : objectClasses) {
                    System.out.println("objetClass = " + objectClass);
                }
            }
            ne.close();
            
        } catch (LdapSizeLimitExceededException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Note: this is important!! 
            LdapClient.closeContext(zlc);
        }
    }
    
    public void createEntry() throws Exception {
        // dn of entry to create 
        String dn = "uid=user1,ou=people,dc=test,dc=com";
        
        GenericLdapConfig ldapConfig = getLdapConfig();
        
        ZLdapContext zlc = null;
        try {
            zlc = LdapClient.getContext(ldapConfig, LdapUsage.ADD);
            
            ZMutableEntry entry = LdapClient.createMutableEntry();
            entry.setDN(dn);
            
            entry.addAttr("objectClass", "inetOrgPerson");
            entry.addAttr("objectClass", "zmailAccount");
            entry.addAttr("objectClass", "amavisAccount");

            entry.setAttr("uid", "user1");
            entry.setAttr("cn", "user1");
            entry.setAttr("sn", "lastname");
            entry.setAttr("zmailAccountStatus", "active");
            entry.setAttr("zmailId", "ba6198a3-bb49-4425-94b0-d4e9354e87b5");
            entry.addAttr("mail", "user1@trest.com");
            entry.addAttr("mail", "user-one@test.com");
            
            zlc.createEntry(entry);
        } finally {
            // Note: this is important!! 
            LdapClient.closeContext(zlc);
        }
    }
    
    public void deleteEntry() throws Exception {
        String dn = "uid=user1,ou=people,dc=test,dc=com";
        
        GenericLdapConfig ldapConfig = getLdapConfig();
        
        ZLdapContext zlc = null;
        try {
            zlc = LdapClient.getContext(ldapConfig, LdapUsage.DELETE);
            zlc.deleteEntry(dn);
        } finally {
            // Note: this is important!! 
            LdapClient.closeContext(zlc);
        }
    }
    
    public void modifyEntry() throws Exception {
        String dn = "cn=config,cn=zmail";
        GenericLdapConfig ldapConfig = getLdapConfig();
        
        ZLdapContext zlc = null;
        try {
            zlc = LdapClient.getContext(ldapConfig, LdapUsage.MOD);
            
            ZMutableEntry entry = LdapClient.createMutableEntry();
            entry.setAttr("description", "so this gets modified");
            zlc.replaceAttributes(dn, entry.getAttributes());
        } finally {
            // Note: this is important!! 
            LdapClient.closeContext(zlc);
        }
    }
    
    public static void main(String[] args) throws Exception {
        // only needs to be called once per JVM  
        LdapClient.initialize();
        
        LdapExample test = new LdapExample();
        
        test.getAttributes();
        test.search();
        test.createEntry();
        test.deleteEntry();
        test.modifyEntry();
    }
}
