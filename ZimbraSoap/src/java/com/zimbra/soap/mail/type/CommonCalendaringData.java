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

@XmlAccessorType(XmlAccessType.NONE)
public abstract class CommonCalendaringData
extends InstanceDataAttrs {

    /**
     * @zm-api-field-tag
     * @zm-api-field-description
     */
    @XmlAttribute(name="x_uid", required=true)
    private final String xUid;

    /**
     * @zm-api-field-tag icalendar-uid
     * @zm-api-field-description iCalendar UID
     */
    @XmlAttribute(name=MailConstants.A_UID /* uid */, required=true)
    private final String uid;

    /**
     * @zm-api-field-tag flags
     * @zm-api-field-description Flags
     */
    @XmlAttribute(name=MailConstants.A_FLAGS /* f */, required=false)
    private String flags;

    /**
     * @zm-api-field-tag tags
     * @zm-api-field-description Tags - Comma separated list of integers.  DEPRECATED - use "tn" instead
     */
    @Deprecated
    @XmlAttribute(name=MailConstants.A_TAGS /* t */, required=false)
    private String tags;

    /**
     * @zm-api-field-tag tag-names
     * @zm-api-field-description Comma-separated list of tag names
     */
    @XmlAttribute(name=MailConstants.A_TAG_NAMES /* tn */, required=false)
    private String tagNames;

    /**
     * @zm-api-field-tag folder-id
     * @zm-api-field-description Folder ID
     */
    @XmlAttribute(name=MailConstants.A_FOLDER /* l */, required=false)
    private String folderId;

    /**
     * @zm-api-field-tag size
     * @zm-api-field-description Size
     */
    @XmlAttribute(name=MailConstants.A_SIZE /* s */, required=false)
    private Long size;

    /**
     * @zm-api-field-tag change-date
     * @zm-api-field-description Modified date in seconds
     */
    @XmlAttribute(name=MailConstants.A_CHANGE_DATE /* md */, required=false)
    private Long changeDate;

    /**
     * @zm-api-field-tag modified-sequence
     * @zm-api-field-description Modified sequence
     */
    @XmlAttribute(name=MailConstants.A_MODIFIED_SEQUENCE /* ms */, required=false)
    private Integer modifiedSequence;

    /**
     * @zm-api-field-tag revision
     * @zm-api-field-description Revision
     */
    @XmlAttribute(name=MailConstants.A_REVISION /* rev */, required=false)
    private Integer revision;

    /**
     * @zm-api-field-tag id
     * @zm-api-field-description ID
     */
    @XmlAttribute(name=MailConstants.A_ID /* id */, required=false)
    private String id;

    /**
     * no-argument constructor wanted by JAXB
     */
    protected CommonCalendaringData() {
        this((String) null, (String) null);
    }

    public CommonCalendaringData(String xUid, String uid) {
        this.xUid = xUid;
        this.uid = uid;
    }

    public void setFlags(String flags) { this.flags = flags; }
    @Deprecated
    public void setTags(String tags) { this.tags = tags; }
    public void setTagNames(String tagNames) { this.tagNames = tagNames; }
    public void setFolderId(String folderId) { this.folderId = folderId; }
    public void setSize(Long size) { this.size = size; }
    public void setChangeDate(Long changeDate) { this.changeDate = changeDate; }
    public void setModifiedSequence(Integer modifiedSequence) {
        this.modifiedSequence = modifiedSequence;
    }
    public void setRevision(Integer revision) { this.revision = revision; }
    public void setId(String id) { this.id = id; }
    public String getXUid() { return xUid; }
    public String getUid() { return uid; }
    public String getFlags() { return flags; }
    @Deprecated
    public String getTags() { return tags; }
    public String getTagNames() { return tagNames; }
    public String getFolderId() { return folderId; }
    public Long getSize() { return size; }
    public Long getChangeDate() { return changeDate; }
    public Integer getModifiedSequence() { return modifiedSequence; }
    public Integer getRevision() { return revision; }
    public String getId() { return id; }

    @Override
    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        helper = super.addToStringInfo(helper);
        return helper
            .add("xUid", xUid)
            .add("uid", uid)
            .add("flags", flags)
            .add("tags", tags)
            .add("tagNames", tagNames)
            .add("folderId", folderId)
            .add("size", size)
            .add("changeDate", changeDate)
            .add("modifiedSequence", modifiedSequence)
            .add("revision", revision)
            .add("id", id);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
