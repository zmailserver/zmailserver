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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.zmail.common.soap.MailConstants;
import org.zmail.soap.mail.type.RetentionPolicy;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_GET_SYSTEM_RETENTION_POLICY_RESPONSE)
public class GetSystemRetentionPolicyResponse {

    /**
     * @zm-api-field-description System Retention policy
     */
    @XmlElement(name=MailConstants.E_RETENTION_POLICY)
    private RetentionPolicy retentionPolicy;

    /**
     * No-argument constructor for JAXB.
     */
    public GetSystemRetentionPolicyResponse() {
    }

    public GetSystemRetentionPolicyResponse(RetentionPolicy rp) {
        retentionPolicy = rp;
    }

    public RetentionPolicy getRetentionPolicy() {
        return retentionPolicy;
    }
}
