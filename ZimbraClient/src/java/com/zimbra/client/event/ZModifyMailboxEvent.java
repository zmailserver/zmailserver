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

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.HeaderConstants;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.client.ToZJSONObject;
import com.zimbra.client.ZJSONObject;
import org.json.JSONException;

public class ZModifyMailboxEvent implements ZModifyEvent, ToZJSONObject {

    protected Element mMailboxEl;

    public ZModifyMailboxEvent(Element e) {
        mMailboxEl = e;
    }

    /**
     * @param defaultValue value to return if unchanged
     * @return new size, or defaultValue if unchanged
     * @throws ServiceException on error
     */
    public long getSize(long defaultValue) throws ServiceException {
        return mMailboxEl.getAttributeLong(MailConstants.A_SIZE, defaultValue);
    }

    public String getOwner(String defaultId) {
        return mMailboxEl.getAttribute(HeaderConstants.A_ACCOUNT_ID, defaultId);
    }

    public ZJSONObject toZJSONObject() throws JSONException {
        try {
            ZJSONObject zjo = new ZJSONObject();
            if (getSize(-1) != -1) zjo.put("size", getSize(-1));
            if (getOwner(null) != null) zjo.put("owner", getOwner(null));
            return zjo;
        } catch (ServiceException se) {
            throw new JSONException(se);
        }
    }

    public String toString() {
        return String.format("[ZModifyMailboxEvent]"); // TODO
    }

    public String dump() {
        return ZJSONObject.toString(this);
    }
}
