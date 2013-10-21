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
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Pair;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Provisioning;

public class BUG_47934 extends UpgradeOp {

    @Override
    void doUpgrade() throws ServiceException {
        upgradeZmailGalLdapFilterDef();
        upgradeZmailGalLdapAttrMap();
    }
    
    void upgradeZmailGalLdapFilterDef() throws ServiceException {
        Config config = prov.getConfig();
 
        
        Pair[] values = {
            new Pair<String, String>(
                    "zmailAccounts:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))", 
                    "zmailAccounts:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(zmailPhoneticFirstName=*%s*)(zmailPhoneticLastName=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))"),
           
            new Pair<String, String>(
                    "zmailAccountAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))",
                    "zmailAccountAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(zmailPhoneticFirstName=%s*)(zmailPhoneticLastName=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))"),
                    
            new Pair<String, String>(
                    "zmailAccountSync:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))", 
                    "zmailAccountSync:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(zmailPhoneticFirstName=*%s*)(zmailPhoneticLastName=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))"),    
        
            new Pair<String, String>(
                    "zmailAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)))", 
                    "zmailAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(zmailPhoneticFirstName=%s*)(zmailPhoneticLastName=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)))"),
            
            new Pair<String, String>(
                    "zmailSearch:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)))", 
                    "zmailSearch:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(zmailPhoneticFirstName=*%s*)(zmailPhoneticLastName=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)))")
            
        };
        
        Set<String> curValues = config.getMultiAttrSet(Provisioning.A_zmailGalLdapFilterDef);
         
        Map<String, Object> attrs = new HashMap<String, Object>();
        for (Pair<String, String> change : values) {
            String oldValue = change.getFirst();
            String newValue = change.getSecond();
            
            if (curValues.contains(oldValue)) {
                StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalLdapFilterDef, oldValue);
                StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalLdapFilterDef, newValue);
            }
        }
        
        modifyAttrs(config, attrs);
    }
    
    void upgradeZmailGalLdapAttrMap() throws ServiceException {
        Config config = prov.getConfig();
        
        String[] values = {
          "zmailPhoneticCompany,ms-DS-Phonetic-Company-Name=phoneticCompany",
          "zmailPhoneticFirstName,ms-DS-Phonetic-First-Name=phoneticFirstName",
          "zmailPhoneticLastName,ms-DS-Phonetic-Last-Name=phoneticLastName"
        };
         
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put("+" + Provisioning.A_zmailGalLdapAttrMap, values);

        modifyAttrs(config, attrs);
    }

}
