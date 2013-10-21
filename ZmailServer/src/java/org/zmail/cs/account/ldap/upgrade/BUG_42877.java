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

public class BUG_42877 extends UpgradeOp {

    @Override
    void doUpgrade() throws ServiceException {
        Config config = prov.getConfig();
        
        String[] values = 
        {
         "facsimileTelephoneNumber,fax=workFax",
         "homeTelephoneNumber,homePhone=homePhone",
         "mobileTelephoneNumber,mobile=mobilePhone",
         "pagerTelephoneNumber,pager=pager"
        };
         
        Map<String, Object> attr = new HashMap<String, Object>();
        attr.put("+" + Provisioning.A_zmailGalLdapAttrMap, values);

        printer.println("Adding workFax, homePhone, mobilePhone, pager attr maps to global config " + Provisioning.A_zmailGalLdapAttrMap);
        prov.modifyAttrs(config, attr);
    }

}
