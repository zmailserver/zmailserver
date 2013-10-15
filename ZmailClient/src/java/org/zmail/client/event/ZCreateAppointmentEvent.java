/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.client.event;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.client.ToZJSONObject;
import org.zmail.client.ZItem;
import org.zmail.client.ZJSONObject;
import org.json.JSONException;

public class ZCreateAppointmentEvent implements ZCreateItemEvent, ToZJSONObject {

    protected Element mApptEl;

    public ZCreateAppointmentEvent(Element e) throws ServiceException {
        mApptEl = e;
    }

    /**
     * @return id
     * @throws org.zmail.common.service.ServiceException on error
     */
    public String getId() throws ServiceException {
        return mApptEl.getAttribute(MailConstants.A_ID);
    }

    public ZJSONObject toZJSONObject() throws JSONException {
        try {
            ZJSONObject zjo = new ZJSONObject();
            zjo.put("id", getId());
            return zjo;
        } catch (ServiceException se) {
            throw new JSONException(se);
        }
    }

    public String toString() {
        try {
            return String.format("[ZCreateAppointmentEvent %s]", getId());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public String dump() {
        return ZJSONObject.toString(this);
    }

    public ZItem getItem() throws ServiceException {
        return null;
    }
}
