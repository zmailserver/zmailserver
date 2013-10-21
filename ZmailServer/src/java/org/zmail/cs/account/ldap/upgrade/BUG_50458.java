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

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.ldap.IAttributes;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.SearchLdapOptions;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.ldap.ZMutableEntry;

public class BUG_50458 extends UpgradeOp {

    private static final String VALUE_TO_REMOVE = "syncListener";
    
    @Override
    void doUpgrade() throws ServiceException {
        ZLdapContext zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
        try {
            doDomain(zlc);
        } finally {
            LdapClient.closeContext(zlc);
        }

    }
    
    private void doDomain(ZLdapContext modZlc) {
        String bases[] = prov.getDIT().getSearchBases(Provisioning.SD_DOMAIN_FLAG);
        String query = "(&" + ZLdapFilterFactory.getInstance().allDomains().toFilterString() + 
            "(" + Provisioning.A_zmailPasswordChangeListener + "=" + VALUE_TO_REMOVE + ")"+ ")";
        
        upgrade(modZlc, bases, query);
    }
    
   
    private void upgrade(ZLdapContext modZlc, String bases[], String query) {
        SearchLdapOptions.SearchLdapVisitor visitor = new Bug50458Visitor(this, modZlc);

        String attrs[] = new String[] {Provisioning.A_zmailPasswordChangeListener};
        
        for (String base : bases) {
            try {
                prov.searchLdapOnMaster(base, query, attrs, visitor);
            } catch (ServiceException e) {
                // log and continue
                printer.println("Caught ServiceException while searching " + query + " under base " + base);
                printer.printStackTrace(e);
            }
        }
    }
    
    private static class Bug50458Visitor extends SearchLdapOptions.SearchLdapVisitor {
        private UpgradeOp upgradeOp;
        private ZLdapContext modZlc;
        
        Bug50458Visitor(UpgradeOp upgradeOp, ZLdapContext modZlc) {
            super(false);
            this.upgradeOp = upgradeOp;
            this.modZlc = modZlc;
        }
        
        @Override
        public void visit(String dn, IAttributes ldapAttrs) {
            try {
                doVisit(dn, (ZAttributes) ldapAttrs);
            } catch (ServiceException e) {
                upgradeOp.printer.println("entry skipped, encountered error while processing entry at:" + dn);
                upgradeOp.printer.printStackTrace(e);
            }
        }
        
        public void doVisit(String dn, ZAttributes ldapAttrs) throws ServiceException {
            ZMutableEntry entry = LdapClient.createMutableEntry();
            entry.setAttr(Provisioning.A_zmailPasswordChangeListener, "");
            upgradeOp.replaceAttrs(modZlc, dn, entry);
        }
    }

}
