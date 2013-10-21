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
package org.zmail.qa.unittest.prov;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import org.zmail.common.account.ZAttrProvisioning.AutoProvAuthMech;
import org.zmail.common.account.ZAttrProvisioning.AutoProvMode;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ldap.AutoProvisionListener;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZMutableEntry;

public class AutoProvisionTestUtil {

    /**
     * A AutoProvisionListener that marks entry "provisioned" in the external directory
     *
     */
    public static class MarkEntryProvisionedListener implements AutoProvisionListener {
        private static final String PROVED_INDICATOR_ATTR = Provisioning.A_zmailNotes;
        private static final String PROVED_NOTE = "PROVISIONED IN ZIMBRA";
        public static final String NOT_PROVED_FILTER = "(!(" + PROVED_INDICATOR_ATTR + "=" + PROVED_NOTE + "))";
        
        public MarkEntryProvisionedListener() {
        }
        
        @Override
        public void postCreate(Domain domain, Account acct, String externalDN) {
            Map<String, Object> attrs = Maps.newHashMap();
            attrs.put(PROVED_INDICATOR_ATTR, PROVED_NOTE);
            try {
                modifyExternalAcctEntry(externalDN, attrs);
            } catch (Exception e) {
                fail();
            }
        }
        
        private void modifyExternalAcctEntry(String externalDN, Map<String, Object> extAcctAttrs) 
        throws Exception {
            ZLdapContext zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UNITTEST);
            try {
                ZMutableEntry entry = LdapClient.createMutableEntry();
                entry.mapToAttrs(extAcctAttrs);
                zlc.replaceAttributes(externalDN, entry.getAttributes());
            } finally {
                LdapClient.closeContext(zlc);
            }
        }
    }

    public static Map<String, Object> commonZmailDomainAttrs() {
        Map<String, Object> zmailDomainAttrs = new HashMap<String, Object>();
        
        StringUtil.addToMultiMap(zmailDomainAttrs, Provisioning.A_zmailAutoProvAuthMech, AutoProvAuthMech.LDAP.name());
        StringUtil.addToMultiMap(zmailDomainAttrs, Provisioning.A_zmailAutoProvMode, AutoProvMode.LAZY.name());
        StringUtil.addToMultiMap(zmailDomainAttrs, Provisioning.A_zmailAutoProvMode, AutoProvMode.MANUAL.name());
        StringUtil.addToMultiMap(zmailDomainAttrs, Provisioning.A_zmailAutoProvMode, AutoProvMode.EAGER.name());
        
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapURL, "ldap://localhost:389");
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapAdminBindDn, "cn=config");
        zmailDomainAttrs.put(Provisioning.A_zmailAutoProvLdapAdminBindPassword, "zmail");
    
        StringUtil.addToMultiMap(zmailDomainAttrs, Provisioning.A_zmailAutoProvAttrMap, "sn=displayName");
        StringUtil.addToMultiMap(zmailDomainAttrs, Provisioning.A_zmailAutoProvAttrMap, "displayName=sn");
                
        return zmailDomainAttrs;
    }

    public static void verifyAcctAutoProvisioned(Account acct) throws Exception {
        AutoProvisionTestUtil.verifyAcctAutoProvisioned(acct, null);
    }

    public static void verifyAcctAutoProvisioned(Account acct, String expectedAcctName) 
    throws Exception {
        assertNotNull(acct);
        if (expectedAcctName != null) {
            assertEquals(expectedAcctName, acct.getName());
        }
        AutoProvisionTestUtil.verifyAttrMapping(acct);
    }

    static void verifyAttrMapping(Account acct) throws Exception {
        assertEquals("last name", acct.getAttr(Provisioning.A_displayName));
        assertEquals("display name", acct.getAttr(Provisioning.A_sn));
    }

}
