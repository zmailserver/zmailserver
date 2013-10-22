/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

package com.zimbra.client.event;

import com.zimbra.common.service.ServiceException;
import com.zimbra.client.ZItem;
import com.zimbra.client.ZSearchFolder;

public class ZCreateSearchFolderEvent implements ZCreateItemEvent {

    protected ZSearchFolder mSearchFolder;

    public ZCreateSearchFolderEvent(ZSearchFolder searchFolder) throws ServiceException {
        mSearchFolder = searchFolder;
    }

    /**
     * @return id of created search folder.
     * @throws com.zimbra.common.service.ServiceException
     */
    public String getId() throws ServiceException {
        return mSearchFolder.getId();
    }

    public ZItem getItem() throws ServiceException {
        return mSearchFolder;
    }

    public ZSearchFolder getSearchFolder() {
        return mSearchFolder;
    }
    
    public String toString() {
    	return mSearchFolder.toString();
    }
}
