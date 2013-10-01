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
package com.zimbra.cs.ldap.unboundid;

import com.unboundid.ldap.sdk.Filter;

import com.zimbra.cs.ldap.ZLdapFilter;
import com.zimbra.cs.ldap.ZLdapFilterFactory.FilterId;

public class UBIDLdapFilter extends ZLdapFilter {
    
    private Filter filter;
    
    UBIDLdapFilter(FilterId filterId, Filter filter) {
        super(filterId);
        this.filter = filter;
    }

    @Override
    public void debug() {
        // TODO Auto-generated method stub
        
    }
    
    Filter getNative() {
        return filter;
    }
    
    @Override
    public String toFilterString() {
        // cannot use this one, assertion values are all turned to lower case 
        // return getNative().toNormalizedString();  
        return getNative().toString();
    }
}
