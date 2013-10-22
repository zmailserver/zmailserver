/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import com.zimbra.client.ToZJSONObject;
import com.zimbra.client.ZFolder;
import com.zimbra.client.ZJSONObject;
import com.zimbra.client.ZTag;
import org.json.JSONException;

import java.util.List;

public class ZRefreshEvent implements ToZJSONObject {

    private long mSize;
    private ZFolder mUserRoot;
    private List<ZTag> mTags;

    public ZRefreshEvent(long size, ZFolder userRoot, List<ZTag> tags) {
    	mSize = size;
    	mUserRoot = userRoot;
    	mTags = tags;
    }

    /**
     * @return size of mailbox in bytes
     */
    public long getSize() {
        return mSize;
    }

    /**
     * return the root user folder
     * @return user root folder
     */
    public ZFolder getUserRoot() {
        return mUserRoot;
    }

    public List<ZTag> getTags() {
        return mTags;
    }
    
    public ZJSONObject toZJSONObject() throws JSONException {
        ZJSONObject zjo = new ZJSONObject();
        zjo.put("size", getSize());
    	zjo.put("userRoot", mUserRoot);
    	zjo.put("tags", mTags);
    	return zjo;
    }

    public String toString() {
        return "[ZRefreshEvent]";
    }

    public String dump() {
        return ZJSONObject.toString(this);
    }
}
