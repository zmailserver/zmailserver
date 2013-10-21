/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ldap.LdapProv;


public class ForeignPrincipal extends AttributeCallback {
    
    @Override
    public void preModify(CallbackContext context, String attrName, Object value,
            Map attrsToModify, Entry entry) 
    throws ServiceException {
        
        if (entry == null || context.isCreate()) {
            return;
        }
        
        if (!(entry instanceof Account))
            return;
            
        Provisioning prov = Provisioning.getInstance();
        if (!(prov instanceof LdapProv))
            return;
        
        Account acct = (Account)entry;
        ((LdapProv) prov).removeFromCache(acct);
    }
    
    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }
    
}
