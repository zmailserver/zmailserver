/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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

package org.zmail.soap.admin.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.zmail.common.soap.AdminConstants;

@XmlAccessorType(XmlAccessType.NONE)
public class VolumeIdAndProgress {

    /**
     * @zm-api-field-tag volumeId
     * @zm-api-field-description volumeId
     */
    @XmlAttribute(name=AdminConstants.A_VOLUME_ID, required=true)
    private final String volumeId;

    /**
     * @zm-api-field-tag progress
     * @zm-api-field-description progress
     */
    @XmlAttribute(name=AdminConstants.A_PROGRESS, required=true)
    private final String progress;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private VolumeIdAndProgress() {
        this((String) null, (String) null);
    }

    public VolumeIdAndProgress(String volumeId, String progress) {
        this.volumeId = volumeId;
        this.progress = progress;
    }

    public String getVolumeId() { return volumeId; }
    public String getProgress() { return progress; }
}
