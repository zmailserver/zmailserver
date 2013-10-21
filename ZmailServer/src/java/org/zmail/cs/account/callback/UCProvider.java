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
package org.zmail.cs.account.callback;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;

public class UCProvider extends AttributeCallback {
    @Override
    public void preModify(CallbackContext context, String attrName,
            Object attrValue, Map attrsToModify, Entry entry)
    throws ServiceException {
        SingleValueMod mod = singleValueMod(attrsToModify, attrName);
        if (mod.unsetting()) {
            return;
        }
        
        String newValue = mod.value();
        String allowedValue = Provisioning.getInstance().getConfig().getUCProviderEnabled();
        
        if (allowedValue == null) {
            throw ServiceException.INVALID_REQUEST("no " + Provisioning.A_zmailUCProviderEnabled +
                    " is configured on global config",  null);
        }
        
        if (!allowedValue.equals(newValue)) {
            throw ServiceException.INVALID_REQUEST("UC provider " + newValue + " is not allowed " + 
                    " by " + Provisioning.A_zmailUCProviderEnabled, null);
        } 
        
    }
    
    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
        // TODO Auto-generated method stub
        
    }

}
