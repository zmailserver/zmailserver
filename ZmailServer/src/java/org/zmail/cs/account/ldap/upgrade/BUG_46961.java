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
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Provisioning;

public class BUG_46961 extends UpgradeOp {

    @Override
    void doUpgrade() throws ServiceException {
        Config config = prov.getConfig();
        
        String oldValue = "displayName,cn=fullName";
        String newValue = "displayName,cn=fullName,fullName2,fullName3,fullName4,fullName5,fullName6,fullName7,fullName8,fullName9,fullName10";
        
        String[] curValues = config.getMultiAttr(Provisioning.A_zmailGalLdapAttrMap);
         
        for (String value : curValues) {
            if (value.equalsIgnoreCase(oldValue)) {
                Map<String, Object> attr = new HashMap<String, Object>();
                attr.put("-" + Provisioning.A_zmailGalLdapAttrMap, oldValue);
                attr.put("+" + Provisioning.A_zmailGalLdapAttrMap, newValue);
                
                printer.println("Modifying " + Provisioning.A_zmailGalLdapAttrMap + " on global config:");
                printer.println("    removing value: " + oldValue);
                printer.println("    adding value: " + newValue);
                prov.modifyAttrs(config, attr);
                
            }
        }
    }

}
