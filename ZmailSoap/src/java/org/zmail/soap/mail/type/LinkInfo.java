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

package com.zimbra.soap.mail.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.google.common.base.Objects;
import com.zimbra.common.soap.MailConstants;

@XmlAccessorType(XmlAccessType.NONE)
public class LinkInfo {

    /**
     * @zm-api-field-tag shared-item-id
     * @zm-api-field-description Shared item ID
     */
    @XmlAttribute(name=MailConstants.A_ID /* id */, required=true)
    private final String id;

    /**
     * @zm-api-field-tag uuid
     * @zm-api-field-description Item's UUID - a globally unique identifier
     */
    @XmlAttribute(name=MailConstants.A_UUID /* uuid */, required=true)
    private final String uuid;

    /**
     * @zm-api-field-tag item-name
     * @zm-api-field-description Item name
     */
    @XmlAttribute(name=MailConstants.A_NAME /* name */, required=true)
    private final String name;

    /**
     * @zm-api-field-tag item-type
     * @zm-api-field-description Item type
     */
    @XmlAttribute(name=MailConstants.A_DEFAULT_VIEW /* view */, required=true)
    private final String defaultView;

    /**
     * @zm-api-field-tag permissions-granted
     * @zm-api-field-description Permissions granted
     */
    @XmlAttribute(name=MailConstants.A_RIGHTS /* perm */, required=false)
    private final String rights;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private LinkInfo() {
        this((String) null, (String) null, (String) null, (String) null, (String) null);
    }

    public LinkInfo(String id, String uuid, String name, String defaultView, String rights) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.defaultView = defaultView;
        this.rights = rights;
    }

    public String getId() { return id; }
    public String getUuid() { return uuid; }
    public String getName() { return name; }
    public String getDefaultView() { return defaultView; }
    public String getRights() { return rights; }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("id", id)
            .add("uuid", uuid)
            .add("name", name)
            .add("defaultView", defaultView)
            .add("rights", rights);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
