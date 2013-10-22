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

package com.zimbra.soap.admin.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.AdminConstants;
import com.zimbra.soap.admin.type.CalendarResourceInfo;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_RENAME_CALENDAR_RESOURCE_RESPONSE)
public class RenameCalendarResourceResponse {

    /**
     * @zm-api-field-description Information about calendar resource
     */
    @XmlElement(name=AdminConstants.E_CALENDAR_RESOURCE, required=true)
    private final CalendarResourceInfo calResource;

    /**
     * no-argument constructor wanted by JAXB
     */
     @SuppressWarnings("unused")
    private RenameCalendarResourceResponse() {
        this((CalendarResourceInfo)null);
    }

    public RenameCalendarResourceResponse(CalendarResourceInfo calResource) {
        this.calResource = calResource;
    }

    public CalendarResourceInfo getCalResource() { return calResource; }
}
