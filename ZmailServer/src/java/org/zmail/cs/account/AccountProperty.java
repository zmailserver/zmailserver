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
package com.zimbra.cs.account;

import java.util.Map;

import com.zimbra.common.service.ServiceException;

public abstract class AccountProperty extends NamedEntry {
	private final Account mAcct; 
    
    AccountProperty(Account acct, String name, String id, Map<String, Object> attrs, Map<String, Object> defaults, Provisioning prov) {
        super(name, id, attrs, null, prov);
        mAcct = acct;
    }
    
    public String getAccountId() {
        return mAcct.getId();
    }
    
    public Account getAccount() throws ServiceException {
        return mAcct;
    }
    
}
