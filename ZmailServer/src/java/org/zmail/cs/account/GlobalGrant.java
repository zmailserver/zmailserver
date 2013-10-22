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

public class GlobalGrant extends Entry {
    
    public GlobalGrant(Map<String, Object> attrs, Provisioning provisioning) {
        super(attrs, null, provisioning);
        resetData();
    }
    
    @Override
    public EntryType getEntryType() {
        return EntryType.GLOBALGRANT;
    }
    
    @Override
    public String getLabel() {
        return "globalacltarget";
    }
}
