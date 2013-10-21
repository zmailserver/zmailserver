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

package org.zmail.soap.mail.message;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.zmail.common.soap.MailConstants;
import org.zmail.soap.mail.type.CalendarItemInfo;
import org.zmail.soap.mail.type.ChatSummary;
import org.zmail.soap.mail.type.CommonDocumentInfo;
import org.zmail.soap.mail.type.ContactInfo;
import org.zmail.soap.mail.type.ConversationSummary;
import org.zmail.soap.mail.type.DocumentInfo;
import org.zmail.soap.mail.type.Folder;
import org.zmail.soap.mail.type.MessageSummary;
import org.zmail.soap.mail.type.NoteInfo;
import org.zmail.soap.mail.type.SyncDeletedInfo;
import org.zmail.soap.mail.type.TagInfo;
import org.zmail.soap.mail.type.TaskItemInfo;
import org.zmail.soap.type.ZmBoolean;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_SYNC_RESPONSE)
@XmlType(propOrder = {"deleted", "items"})
public class SyncResponse {

    /**
     * @zm-api-field-tag change-date
     * @zm-api-field-description Change date
     */
    @XmlAttribute(name=MailConstants.A_CHANGE_DATE /* md */, required=true)
    private long changeDate;

    /**
     * @zm-api-field-tag new-sync-token
     * @zm-api-field-description New sync token
     */
    @XmlAttribute(name=MailConstants.A_TOKEN /* token */, required=false)
    private String token;

    /**
     * @zm-api-field-tag size
     * @zm-api-field-description Size
     */
    @XmlAttribute(name=MailConstants.A_SIZE /* s */, required=false)
    private Long size;

    /**
     * @zm-api-field-tag more-flag
     * @zm-api-field-description If set, the response does <b>not</b> bring the client completely up to date.
     * <br />
     * More changes are still queued, and another SyncRequest (using the new returned token) is necessary.
     */
    @XmlAttribute(name=MailConstants.A_QUERY_MORE /* more */, required=false)
    private ZmBoolean more;

    /**
     * @zm-api-field-description Information on deletes
     */
    @XmlElement(name=MailConstants.E_DELETED /* deleted */, required=false)
    private SyncDeletedInfo deleted;

    /**
     * @zm-api-field-description Item information
     */
    @XmlElements({
        @XmlElement(name=MailConstants.E_FOLDER /* folder */, type=Folder.class),
        @XmlElement(name=MailConstants.E_TAG /* tag */, type=TagInfo.class),
        @XmlElement(name=MailConstants.E_NOTE /* note */, type=NoteInfo.class),
        @XmlElement(name=MailConstants.E_CONTACT /* cn */, type=ContactInfo.class),
        @XmlElement(name=MailConstants.E_APPOINTMENT /* appt */, type=CalendarItemInfo.class),
        @XmlElement(name=MailConstants.E_TASK /* task */, type=TaskItemInfo.class),
        @XmlElement(name=MailConstants.E_CONV /* c */, type=ConversationSummary.class),
        @XmlElement(name=MailConstants.E_WIKIWORD /* w */, type=CommonDocumentInfo.class),
        @XmlElement(name=MailConstants.E_DOC /* doc */, type=DocumentInfo.class),
        @XmlElement(name=MailConstants.E_MSG /* m */, type=MessageSummary.class),
        @XmlElement(name=MailConstants.E_CHAT /* chat */, type=ChatSummary.class)
    })
    private List<Object> items = Lists.newArrayList();

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private SyncResponse() {
    }

    public SyncResponse(long changeDate) {
        this.changeDate = changeDate;
    }

    public void setToken(String token) { this.token = token; }
    public void setSize(Long size) { this.size = size; }
    public void setMore(Boolean more) { this.more = ZmBoolean.fromBool(more); }
    public void setDeleted(SyncDeletedInfo deleted) { this.deleted = deleted; }
    public void setItems(Iterable <Object> items) {
        this.items.clear();
        if (items != null) {
            Iterables.addAll(this.items,items);
        }
    }

    public SyncResponse addItem(Object item) {
        this.items.add(item);
        return this;
    }

    public long getChangeDate() { return changeDate; }
    public String getToken() { return token; }
    public Long getSize() { return size; }
    public Boolean getMore() { return ZmBoolean.toBool(more); }
    public SyncDeletedInfo getDeleted() { return deleted; }
    public List<Object> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("changeDate", changeDate)
            .add("token", token)
            .add("size", size)
            .add("more", more)
            .add("deleted", deleted)
            .add("items", items);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
