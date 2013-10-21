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
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.ldap.ZMutableEntry;
import org.zmail.cs.ldap.SearchLdapOptions.SearchLdapVisitor;

public class BUG_53745 extends UpgradeOp {
    private static String ATTR_IMPORTEXPORT = Provisioning.A_zmailFeatureImportExportFolderEnabled;
    private static String ATTR_IMPORT = Provisioning.A_zmailFeatureImportFolderEnabled;
    private static String ATTR_EXPORT = Provisioning.A_zmailFeatureExportFolderEnabled;
    
    @Override
    void doUpgrade() throws ServiceException {
        ZLdapContext zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
        try {
            doCos(zlc);
            doAccount(zlc);
        } finally {
            LdapClient.closeContext(zlc);
        }

    }
    
    private static class Bug53745Visitor extends SearchLdapVisitor {
        private UpgradeOp upgradeOp;
        private ZLdapContext modZlc;
        
        Bug53745Visitor(UpgradeOp upgradeOp, ZLdapContext modZlc) {
            super(false);
            this.upgradeOp = upgradeOp;
            this.modZlc = modZlc;
        }
        
        @Override
        public void visit(String dn, IAttributes ldapAttrs) {
            ZMutableEntry entry = LdapClient.createMutableEntry();
            
            try {
                String importExportVal = ldapAttrs.getAttrString(ATTR_IMPORTEXPORT);
                String importVal = ldapAttrs.getAttrString(ATTR_IMPORT);
                String exportVal = ldapAttrs.getAttrString(ATTR_EXPORT);
                
                if (importExportVal != null) {
                    if (importVal == null) {
                        entry.setAttr(ATTR_IMPORT, importExportVal);
                    }
                    
                    if (exportVal == null) {
                        entry.setAttr(ATTR_EXPORT, importExportVal);
                    }
                } 
                
                upgradeOp.replaceAttrs(modZlc, dn, entry);
                
            } catch (ServiceException e) {
                // log and continue
                upgradeOp.printer.println("Caught ServiceException while modifying " + dn);
                upgradeOp.printer.printStackTrace(e);
            }
        }
    }
    
    private void upgrade(ZLdapContext modZlc, String bases[], String query) {
        SearchLdapOptions.SearchLdapVisitor visitor = new Bug53745Visitor(this, modZlc);

        String attrs[] = new String[] {ATTR_IMPORTEXPORT, ATTR_IMPORT, ATTR_EXPORT};
        
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
    
    private String query() {
        return "(" + ATTR_IMPORTEXPORT + "=*)";
    }
    
    private void doCos(ZLdapContext modZlc) {
        String bases[] = prov.getDIT().getSearchBases(Provisioning.SD_COS_FLAG);
        String query = "(&" + ZLdapFilterFactory.getInstance().allCoses().toFilterString() + query() + ")";
        upgrade(modZlc, bases, query);
    }
    
    private void doAccount(ZLdapContext modZlc) {
        String bases[] = prov.getDIT().getSearchBases(Provisioning.SD_ACCOUNT_FLAG);
        String query = "(&" + ZLdapFilterFactory.getInstance().allAccounts().toFilterString() + query() + ")";
        upgrade(modZlc, bases, query);
    }
}
