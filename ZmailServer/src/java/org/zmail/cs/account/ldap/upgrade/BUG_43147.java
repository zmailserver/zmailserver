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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.ldap.IAttributes;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.SearchLdapOptions;
import org.zmail.cs.ldap.ZLdapContext;

public class BUG_43147 extends UpgradeOp {

    @Override
    void doUpgrade() throws ServiceException {
        
        Set<String> galSyncAcctIds = getAllGalSyncAcctIds();
        
        ZLdapContext zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
        try {
            upgradeGalSyncAcct(zlc, galSyncAcctIds);
        } finally {
            LdapClient.closeContext(zlc);
        }
    }
    
    private static class Bug43147Visitor extends SearchLdapOptions.SearchLdapVisitor {
        
        private UpgradeOp upgradeOp;
        private Set<String> galSyncAcctIds;
        
        Bug43147Visitor(UpgradeOp upgradeOp, Set<String> galSyncAcctIds) {
            this.upgradeOp = upgradeOp;
            this.galSyncAcctIds = galSyncAcctIds;
        }
        
        @Override
        public void visit(String dn, Map<String, Object> attrs, IAttributes ldapAttrs) {
            upgradeOp.printer.println("Domain " + attrs.get(Provisioning.A_zmailDomainName));
                
            Object values = attrs.get(Provisioning.A_zmailGalAccountId);
            if (values instanceof String) {
                upgradeOp.printer.println(" GAL sync account " + (String)values);
                galSyncAcctIds.add((String)values);
            } else if (values instanceof String[]) {
                for (String value : (String[])values) {
                    upgradeOp.printer.println(" GAL sync account " + value);
                   galSyncAcctIds.add(value);
                }
            }
        }
    }
    
    private Set<String> getAllGalSyncAcctIds() throws ServiceException {
        Set<String> galSyncAcctIds = new HashSet<String>();
        
        Bug43147Visitor visitor = new Bug43147Visitor(this, galSyncAcctIds);
        
        String query = "(&(objectClass=zmailDomain)(zmailGalAccountId=*))";
        String bases[] = prov.getDIT().getSearchBases(Provisioning.SD_DOMAIN_FLAG);
        String attrs[] = new String[] {Provisioning.A_zmailDomainName,
                                       Provisioning.A_zmailGalAccountId};
        
        for (String base : bases) {
            prov.searchLdapOnMaster(base, query, attrs, visitor);
        }
        
        return galSyncAcctIds;
    }
    
    private void upgradeGalSyncAcct(ZLdapContext zlc, Set<String> galSyncAcctIds) throws ServiceException {
        printer.println();
        printer.println("Upgrading zmailContactMaxNumEntries on GAL sync accounts ...");
        printer.println();
        
        HashMap<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailContactMaxNumEntries, "0");
        
        // got all GAL sync account ids, upgrade them
        for (String id : galSyncAcctIds) {
            Account acct = prov.getAccountById(id);
            if (acct != null) {
                // upgrade only when the gal sync account does not have zmailContactMaxNumEntries 
                // set on the account entry
                String curValue = acct.getAttr(Provisioning.A_zmailContactMaxNumEntries, false);
                
                if (curValue == null) {
                    try {
                        printer.println("Account: " + acct.getId() + "(" + acct.getName() + ") - "+ "modifying zmailContactMaxNumEntries to 0");
                        modifyAttrs(zlc, acct, attrs);
                    } catch (ServiceException e) {
                        printer.println("Caught ServiceException while modifying GAL sync account entry " + acct.getName());
                        printer.printStackTrace(e);
                    }
                } else
                    printer.println("Account: " + acct.getId() + "(" + acct.getName() + ") - "+ "already has value " + curValue + ", skipping");
            } else {
                printer.println("Account: " + id + " - no such account");
            }
        }

    }

}
