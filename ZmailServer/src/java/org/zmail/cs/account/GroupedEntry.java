/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.account;

import org.zmail.common.service.ServiceException;

public interface GroupedEntry {
    
    /**
     * returns all addresses of this entry that can be identified as a member in a 
     * static group.
     * 
     * @return
     */
    public String[] getAllAddrsAsGroupMember() throws ServiceException;
}
