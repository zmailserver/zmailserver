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

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZLdapContext;

public class BUG_46883 extends UpgradeOp {

    @Override
    void doUpgrade() throws ServiceException {
        ZLdapContext zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
        try {
            doAllCos(zlc);
        } finally {
            LdapClient.closeContext(zlc);
        }
    }
    
    private void doEntry(ZLdapContext zlc, Entry entry, String entryName) throws ServiceException {
        
        String attrName = Provisioning.A_zmailContactRankingTableSize;
        String oldValue = "40";
        String newValue = "200";
        
        String curVal = entry.getAttr(attrName);
        printer.print("Checking " + entryName + ": " + "current value of " + attrName + " is " + curVal);
        
        if (curVal != null && !oldValue.equals(curVal)) {
            printer.println(" => not updating ");
            return;
        }
        
        Map<String, Object> attrValues = new HashMap<String, Object>();
        attrValues.put(attrName, newValue);   
        try {
            printer.println(" => updating to " + newValue);
            modifyAttrs(zlc, entry, attrValues);
        } catch (ServiceException e) {
            // log the exception and continue
            printer.println("Caught ServiceException while modifying " + entryName);
            printer.printStackTrace(e);
        }

    }
    
    private void doAllCos(ZLdapContext zlc) throws ServiceException {
        List<Cos> coses = prov.getAllCos();
        
        for (Cos cos : coses) {
            String name = "cos " + cos.getName();
            doEntry(zlc, cos, name);
        }
    }
}
