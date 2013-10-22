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

import com.google.common.base.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.zimbra.common.soap.MailConstants;
import com.zimbra.soap.type.ZmBoolean;

@XmlAccessorType(XmlAccessType.NONE)
public class SharedReminderMount {

    /**
     * @zm-api-field-tag mountpoint-id
     * @zm-api-field-description Mountpoint ID
     */
    @XmlAttribute(name=MailConstants.A_ID /* id */, required=true)
    private final String id;

    /**
     * @zm-api-field-tag show-reminders-flag
     * @zm-api-field-description Set to enable (or unset to disable) reminders for shared appointments/tasks
     */
    @XmlAttribute(name=MailConstants.A_REMINDER /* reminder */, required=false)
    private final ZmBoolean showReminders;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private SharedReminderMount() {
        this((String) null, (Boolean) null);
    }

    public SharedReminderMount(String id, Boolean showReminders) {
        this.id = id;
        this.showReminders = ZmBoolean.fromBool(showReminders);
    }

    public String getId() { return id; }
    public Boolean getShowReminders() { return ZmBoolean.toBool(showReminders); }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("id", id)
            .add("showReminders", showReminders);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
