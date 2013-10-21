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
package org.zmail.cs.gal;

public class GalFilter {
    static final String DEFAULT_SYNC_FILTER = 
        "(&(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)(objectclass=zmailGroup))(!(&(objectclass=zmailCalendarResource)(!(zmailAccountStatus=active)))))";

    static enum NamedFilter {
        zmailAccounts,
        zmailResources,
        zmailGroups,
        
        zmailAccountAutoComplete,
        zmailResourceAutoComplete,
        zmailGroupAutoComplete,
        
        zmailAccountSync,
        zmailResourceSync,
        zmailGroupSync,
        
        zmailAutoComplete,
        zmailSearch,
        zmailSync;
    };
    
}
