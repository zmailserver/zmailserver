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
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.filter.RuleManager;
 
public class MailSieveScript extends AttributeCallback {

    /**
     * check to make sure zmailMailHost points to a valid server zmailServiceHostname
     */
    @SuppressWarnings("unchecked")
    @Override
    public void preModify(CallbackContext context, String attrName, Object value,
            Map attrsToModify, Entry entry) 
    throws ServiceException {

        singleValueMod(attrName, value);
        
        if (!(entry instanceof Account)) 
            return;
        
        Account acct = (Account)entry;
        
        if (!Provisioning.onLocalServer(acct))
            return;
        
        // clear it from the in memory parsed filter rule cache
        RuleManager.clearCachedRules(acct);
    }

    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }
}
