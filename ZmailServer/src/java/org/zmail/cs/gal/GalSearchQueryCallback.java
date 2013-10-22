/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cs.gal;

public interface GalSearchQueryCallback {

    /**
     * 
     * @return extra query to be ANDed with the query for GAL sync account search
     */
    public String getMailboxSearchQuery();
    
    /**
     * 
     * @return extra query to be ANDed with the query for Zimbra GAL LDAP search
     */
    public String getZimbraLdapSearchQuery();
}
