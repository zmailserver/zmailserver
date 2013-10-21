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

package org.zmail.cs.account.callback;

import java.util.Map;

import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.Key.DomainBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.EmailUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;

public class AllowFromAddress extends AttributeCallback {

    /**
     * zmailAllowFromAddress may not contain the address of an internal account or a distribution list
     */
    @Override
    public void preModify(CallbackContext context, String attrName, Object value,
            Map attrsToModify, Entry entry) 
    throws ServiceException {

        MultiValueMod mod = multiValueMod(attrsToModify, Provisioning.A_zmailAllowFromAddress);
        if (mod != null && (mod.adding() || mod.replacing())) {
            for (String addr : mod.valuesSet()) {
                checkAddress(addr);
            }
        }
    }

    private void checkAddress(String addr) throws ServiceException {
        Provisioning prov = Provisioning.getInstance();
        String domain = EmailUtil.getValidDomainPart(addr);
        if (domain != null) {  // addresses in non-local domains are allowed
            Domain internalDomain = prov.getDomain(DomainBy.name, domain, true);
            if (internalDomain != null) {
                if (prov.isDistributionList(addr)) {
                    throw ServiceException.INVALID_REQUEST(Provisioning.A_zmailAllowFromAddress +
                            " may not contain a distribution list: " + addr, null);
                }
                Account acct = prov.get(AccountBy.name, addr);
                if (acct != null) {
                    throw ServiceException.INVALID_REQUEST(Provisioning.A_zmailAllowFromAddress +
                            " may not contain an internal account: " + addr, null);
                }
            }
        }
    }
    
    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }
}
