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

package com.zimbra.soap.admin.type;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.NONE)
public class CalendarResourceInfo extends AdminObjectInfo {

    /**
     * no-argument constructor wanted by JAXB
     */
    private CalendarResourceInfo() {
        super(null, null, null);
    }

    public CalendarResourceInfo(String id, String name) {
        super(id, name, null);
    }

    public CalendarResourceInfo(String id, String name, Collection <Attr> attrs) {
        super(id, name, attrs);
    }
}
