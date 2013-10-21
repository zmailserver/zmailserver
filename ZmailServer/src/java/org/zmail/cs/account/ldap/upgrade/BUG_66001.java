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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Pair;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Entry.EntryType;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZLdapContext;

public class BUG_66001 extends UpgradeOp {
    
    private static final String ATTR_NAME = Provisioning.A_zmailGalLdapFilterDef;
    
    private static List<Pair> VALUES = Lists.newArrayList(
            new Pair("zmailAccounts:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(zmailPhoneticFirstName=*%s*)(zmailPhoneticLastName=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))",
                     "zmailAccounts:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(zmailPhoneticFirstName=*%s*)(zmailPhoneticLastName=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)(objectclass=zmailGroup))(!(objectclass=zmailCalendarResource)))"
            ),
            
            new Pair("zmailAccountAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(zmailPhoneticFirstName=%s*)(zmailPhoneticLastName=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))",
                     "zmailAccountAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(zmailPhoneticFirstName=%s*)(zmailPhoneticLastName=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)(objectclass=zmailGroup))(!(objectclass=zmailCalendarResource)))"
            ),
            
            new Pair("zmailAccountSync:(&(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))",
                     "zmailAccountSync:(&(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)(objectclass=zmailGroup))(!(objectclass=zmailCalendarResource)))"
            ),
            
            new Pair("zmailGroups:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(objectclass=zmailDistributionList))",
                     "zmailGroups:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailDistributionList)(objectclass=zmailGroup)))"
            ),
            
            new Pair("zmailGroupAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(objectclass=zmailDistributionList))",
                     "zmailGroupAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailDistributionList)(objectclass=zmailGroup)))"
            ),
            
            new Pair("zmailGroupSync:(objectclass=zmailDistributionList)",
                     "zmailGroupSync:(|(objectclass=zmailDistributionList)(objectclass=zmailGroup))"
            ),
            
            new Pair("zmailAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(zmailPhoneticFirstName=%s*)(zmailPhoneticLastName=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)))",
                     "zmailAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(zmailPhoneticFirstName=%s*)(zmailPhoneticLastName=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)(objectclass=zmailGroup)))"
            ),

            new Pair("zmailSearch:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(zmailPhoneticFirstName=*%s*)(zmailPhoneticLastName=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)))",
                     "zmailSearch:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(zmailPhoneticFirstName=*%s*)(zmailPhoneticLastName=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)(objectclass=zmailGroup)))"
            ),
            
            new Pair("zmailSync:(&(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(&(objectclass=zmailCalendarResource)(!(zmailAccountStatus=active))))(!(zmailHideInGal=TRUE))(!(zmailIsSystemResource=TRUE)))",
                     "zmailSync:(&(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)(objectclass=zmailGroup))(!(&(objectclass=zmailCalendarResource)(!(zmailAccountStatus=active)))))"
            )

            );

    @Override
    void doUpgrade() throws ServiceException {
        
        ZLdapContext zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
        try {
            doGlobalConfig(zlc);
        } finally {
            LdapClient.closeContext(zlc);
        }
    }
    
    @Override
    Description getDescription() {
        StringBuilder oldValues = new StringBuilder();
        StringBuilder newValues = new StringBuilder();
        for (Pair valuePair : VALUES) {
            oldValues.append(valuePair.getFirst() + "\n");
            newValues.append(valuePair.getSecond() + "\n");
        }
        
        return new Description(this, 
                new String[] {ATTR_NAME}, 
                new EntryType[] {EntryType.GLOBALCONFIG},
                oldValues.toString(), 
                newValues.toString(), 
                String.format("Upgrade zmailAccounts, zmailAccountAutoComplete, zmailAccountSync, " +
                        "zmailGroups, zmailGroupAutoComplete, zmailGroupSync" + 
                        "zmailAutoComplete, zmailSearch, zmailSync " + 
                        "GAL filters on %s on global config from " + 
                        "matched old values to the corresponding new value.  ", 
                        ATTR_NAME));
    }

    private void doEntry(ZLdapContext zlc, Entry entry) throws ServiceException {
        String entryName = entry.getLabel();
        
        printer.println();
        printer.println("------------------------------");
        printer.println("Checking " + ATTR_NAME + " on " + entryName);
        
        Set<String> curValues = entry.getMultiAttrSet(ATTR_NAME);
        
        Map<String, Object> attrs = new HashMap<String, Object>();
        for (Pair<String, String> valuePair : VALUES) {
            String oldValue = valuePair.getFirst();
            String newValue = valuePair.getSecond();
            if (curValues.contains(oldValue)) {
                StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalLdapFilterDef, oldValue);
                StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalLdapFilterDef, newValue);
            }
        }
        modifyAttrs(entry, attrs);
    }
    
    private void doGlobalConfig(ZLdapContext zlc) throws ServiceException {
        doEntry(zlc, prov.getConfig());
    }
    
}
