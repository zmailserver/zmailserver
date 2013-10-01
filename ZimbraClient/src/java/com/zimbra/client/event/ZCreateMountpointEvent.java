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
import com.zimbra.client.ZMountpoint;

public class ZCreateMountpointEvent implements ZCreateItemEvent {

    protected ZMountpoint mMountpoint;

    public ZCreateMountpointEvent(ZMountpoint mountpoint) throws ServiceException {
        mMountpoint = mountpoint;
    }

    /**
     * @return id of created mountpoint
     * @throws com.zimbra.common.service.ServiceException
     */
    public String getId() throws ServiceException {
        return mMountpoint.getId();
    }

    public ZItem getItem() throws ServiceException {
        return mMountpoint;
    }

    public ZMountpoint getMountpoint() {
        return mMountpoint;
    }
    
    public String toString() {
    	return mMountpoint.toString();
    }
}
