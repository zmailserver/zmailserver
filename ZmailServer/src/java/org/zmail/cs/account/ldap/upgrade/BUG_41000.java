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

public class BUG_41000 extends UpgradeOp {

    @Override
    void doUpgrade() throws ServiceException {
        Config config = prov.getConfig();
        
        String[] value = 
        {
         "zmailAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)))",
         "zmailSearch:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)))"
        };
         
        Map<String, Object> attr = new HashMap<String, Object>();
        attr.put("+" + Provisioning.A_zmailGalLdapFilterDef, value);
        
        printer.println("Adding zmailAutoComplete and zmailSearch filters to global config " + Provisioning.A_zmailGalLdapFilterDef);
        prov.modifyAttrs(config, attr);
    }
    

}
