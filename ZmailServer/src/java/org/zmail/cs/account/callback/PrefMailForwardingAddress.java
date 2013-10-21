/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.callback.CallbackContext.DataKey;

public class PrefMailForwardingAddress extends AttributeCallback {

    /*
     * for modifying, we can get the max len from entry.getAccount
     * 
     * if the account entry is being created, or if someone is setting it (not a valid supported case) 
     * directly with createAccount, entry will be null, and we cannot do entry.getAccount to get the 
     * max length.  So we pass the max length in the context.
     * 
     * FIXME: currently callback keys are not set in LdapProvisioning because preModify is called before
     *        cos is decided.
     */
    
    @Override
    public void preModify(CallbackContext context, String attrName, Object attrValue,
            Map attrsToModify, Entry entry)
    throws ServiceException {
        
        if (entry != null && !(entry instanceof Account)) {
            return;
        }
        
        SingleValueMod mod = singleValueMod(attrsToModify, attrName);
        if (mod.unsetting())
            return;
        
        int maxLen = -1;
        int maxAddrs = -1;
        
        String maxLenInCtxt = context.getData(DataKey.MAIL_FORWARDING_ADDRESS_MAX_LEN);
        if (maxLenInCtxt != null) {
            try {
                maxLen = Integer.parseInt(maxLenInCtxt);
            } catch (NumberFormatException e) {
                ZmailLog.account.warn("encountered invalid " + 
                        DataKey.MAIL_FORWARDING_ADDRESS_MAX_LEN.name() + ": " + maxLenInCtxt);
            }
        }
        
        String maxAddrsInCtxt = context.getData(DataKey.MAIL_FORWARDING_ADDRESS_MAX_NUM_ADDRS);
        if (maxAddrsInCtxt != null) {
            try {
                maxLen = Integer.parseInt(maxAddrsInCtxt);
            } catch (NumberFormatException e) {
                ZmailLog.account.warn("encountered invalid " + 
                        DataKey.MAIL_FORWARDING_ADDRESS_MAX_NUM_ADDRS.name() + ": " + maxAddrsInCtxt);
            }
        } 
        
        // if we don't have a good max len/addrs value in the context, get it from the entry
        if (entry == null)
            return;
        
        Account account = (Account)entry;
        if (maxLen == -1)
            maxLen = account.getMailForwardingAddressMaxLength();
        
        if (maxAddrs == -1)
            maxAddrs = account.getMailForwardingAddressMaxNumAddrs();
        
        String newValue = mod.value();
        
        if (newValue.length() > maxLen) {
            throw ServiceException.INVALID_REQUEST("value is too long, the limit(" + 
                    Provisioning.A_zmailMailForwardingAddressMaxLength + ") is " + 
                    maxLen, null);
        }
        
        String[] addrs = newValue.split(",");
        if (addrs.length > maxAddrs) {
            throw ServiceException.INVALID_REQUEST("value is too long, the limit(" + 
                    Provisioning.A_zmailMailForwardingAddressMaxNumAddrs + ") is " + 
                    maxAddrs, null);
        }
    }
    
    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }

}
