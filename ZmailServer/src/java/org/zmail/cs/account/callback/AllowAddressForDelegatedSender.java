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
import java.util.Set;

import com.google.common.collect.Sets;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AliasedEntry;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Group;

/**
 * Ensure values for zmailPrefAllowAddressForDelegatedSender must be either the entry's
 * primary address or one of the aliases.
 *
 */
public class AllowAddressForDelegatedSender extends AttributeCallback {

    @Override
    public void preModify(CallbackContext context, String attrName, Object attrValue,
            Map attrsToModify, Entry entry)
    throws ServiceException {
        
        if (context.isDoneAndSetIfNot(AllowAddressForDelegatedSender.class)) {
            return;
        }
        
        Set<String> allowedValues;
        if (entry == null) {
            allowedValues = Sets.newHashSet();
            String entryName = context.getCreatingEntryName();
            if (entryName != null) {
                allowedValues.add(entryName);
            }
        } else {
            if (!(entry instanceof Account) && !(entry instanceof Group)) {
                return;
            }
            allowedValues = ((AliasedEntry) entry).getAllAddrsSet();
        }
        
        Object replace = attrsToModify.get(attrName);
        Object add = attrsToModify.get("+" + attrName);
        
        // can't replace and add in one command
        Set<String> values = getMultiValueSet((replace != null) ? replace : add);
        for (String value : values) {
            if (!StringUtil.isNullOrEmpty(value) && !allowedValues.contains(value)) {
                throw ServiceException.INVALID_REQUEST("value is not one of the addresses of the entry: " +
                        value, null);
            }
        }
    }
    
    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }


}
