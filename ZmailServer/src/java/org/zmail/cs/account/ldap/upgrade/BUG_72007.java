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

import org.zmail.common.account.ProvisioningConstants;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Entry.EntryType;

public class BUG_72007 extends UpgradeOp {
    
    private static final String[] ATTRS = new String[] {
            Provisioning.A_zmailIsSystemResource,
            Provisioning.A_zmailIsSystemAccount};

    @Override
    void doUpgrade() throws ServiceException {
        
        Config config = prov.getConfig();
        upgrdeAcount(config.getAttr(Provisioning.A_zmailSpamIsNotSpamAccount));
        upgrdeAcount(config.getAttr(Provisioning.A_zmailSpamIsSpamAccount));
    }
    
    @Override
    Description getDescription() {
        return new Description(this, 
                ATTRS, 
                new EntryType[] {EntryType.ACCOUNT},
                null, 
                ProvisioningConstants.TRUE, 
                String.format("Set %s of %s and %s accounts to %s", 
                        Arrays.deepToString(ATTRS), 
                        Provisioning.A_zmailSpamIsNotSpamAccount,
                        Provisioning.A_zmailSpamIsSpamAccount,
                        ProvisioningConstants.TRUE));
    }
    
    private void upgrdeAcount(String name) throws ServiceException {
        if (name != null) {
            Account acct = prov.get(AccountBy.name, name);
            if (acct != null) {
                Map<String, Object> attrs = new HashMap<String, Object>();
                if (!acct.isIsSystemResource()) {
                    attrs.put(Provisioning.A_zmailIsSystemResource, ProvisioningConstants.TRUE);
                }
                if (!acct.isIsSystemAccount()) {
                    attrs.put(Provisioning.A_zmailIsSystemAccount, ProvisioningConstants.TRUE);
                }
                modifyAttrs(acct, attrs);
            }
        }
    }
}
