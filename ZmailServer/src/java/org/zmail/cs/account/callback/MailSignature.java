/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.cs.account.Identity;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Signature;
import org.zmail.cs.account.callback.CallbackContext.DataKey;
 
public class MailSignature extends AttributeCallback {

    /**
     * check to make sure zmailPrefMailSignature is shorter than the limit
     */
    @Override
    public void preModify(CallbackContext context, String attrName, Object value,
            Map attrsToModify, Entry entry) 
    throws ServiceException {

        SingleValueMod mod = singleValueMod(attrName, value);
        if (mod.unsetting())
            return;
                
        if (entry != null && 
            !((entry instanceof Account)||(entry instanceof Identity)||(entry instanceof Signature))) {
            return;
        }

        long maxLen = -1;
        
        String maxInContext = context.getData(DataKey.MAX_SIGNATURE_LEN);
        if (maxInContext != null) {
            try {
                maxLen = Integer.parseInt(maxInContext);
            } catch (NumberFormatException e) {
                ZmailLog.account.warn("encountered invalid " + 
                        DataKey.MAX_SIGNATURE_LEN.name() + ": " + maxInContext);
            }
        }

        if (maxLen == -1) {
            String maxInAttrsToModify = (String) attrsToModify.get(Provisioning.A_zmailMailSignatureMaxLength);
            if (maxInAttrsToModify != null) {
                try {
                    maxLen = Integer.parseInt(maxInAttrsToModify);
                } catch (NumberFormatException e) {
                    ZmailLog.account.warn("encountered invalid " + 
                            Provisioning.A_zmailMailSignatureMaxLength + ": " +
                            maxInAttrsToModify);
                }
            }
        }

        if (maxLen == -1) {
            if (entry == null) {
                return;
            }
            
            Account account;
            if (entry instanceof Account) {
                account = (Account)entry;
            } else if (entry instanceof Identity) {
                account = ((Identity)entry).getAccount();
            } else if (entry instanceof Signature) {
                account = ((Signature)entry).getAccount();
            } else {
                return;
            }
            
            maxLen = account.getMailSignatureMaxLength();
        }

        // 0 means unlimited
        if (maxLen != 0 && ((String)value).length() > maxLen) {
            throw ServiceException.INVALID_REQUEST(
                    Provisioning.A_zmailPrefMailSignature + 
                    " is longer than the limited value " + maxLen, null);
        }
    }

    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }
}
