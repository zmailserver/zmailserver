/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Entry.EntryType;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZLdapContext;

public class BUG_68891 extends UpgradeOp {
    private static final String ATTR_NAME = Provisioning.A_zmailGalLdapAttrMap;
    private static final String[] VALUES = {
        "zmailDistributionListSubscriptionPolicy=zmailDistributionListSubscriptionPolicy",
        "zmailDistributionListUnsubscriptionPolicy=zmailDistributionListUnsubscriptionPolicy"
    };

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
        return new Description(this, 
                new String[] {ATTR_NAME}, 
                new EntryType[] {EntryType.GLOBALCONFIG},
                null, 
                Arrays.deepToString(VALUES), 
                String.format("Add values %s on %s", Arrays.deepToString(VALUES), ATTR_NAME));
    }

    private void doEntry(ZLdapContext zlc, Entry entry) throws ServiceException {
        String entryName = entry.getLabel();
        
        printer.println();
        printer.println("------------------------------");
        printer.println("Checking " + ATTR_NAME + " on " + entryName);
        
        Set<String> curValues = entry.getMultiAttrSet(ATTR_NAME);
        
        Map<String, Object> attrs = new HashMap<String, Object>();
        for (String value : VALUES) {
            if (!curValues.contains(value)) {
                StringUtil.addToMultiMap(attrs, "+" + ATTR_NAME, value);
            }
        }
        modifyAttrs(entry, attrs);
    }
    
    private void doGlobalConfig(ZLdapContext zlc) throws ServiceException {
        doEntry(zlc, prov.getConfig());
    }
    
}
