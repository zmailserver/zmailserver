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
package com.zimbra.cs.gal;

public class GalFilter {
    static final String DEFAULT_SYNC_FILTER = 
        "(&(|(objectclass=zimbraAccount)(objectclass=zimbraDistributionList)(objectclass=zimbraGroup))(!(&(objectclass=zimbraCalendarResource)(!(zimbraAccountStatus=active)))))";

    static enum NamedFilter {
        zimbraAccounts,
        zimbraResources,
        zimbraGroups,
        
        zimbraAccountAutoComplete,
        zimbraResourceAutoComplete,
        zimbraGroupAutoComplete,
        
        zimbraAccountSync,
        zimbraResourceSync,
        zimbraGroupSync,
        
        zimbraAutoComplete,
        zimbraSearch,
        zimbraSync;
    };
    
}
