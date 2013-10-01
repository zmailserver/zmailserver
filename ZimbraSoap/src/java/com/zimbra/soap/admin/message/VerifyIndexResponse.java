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
import javax.xml.bind.annotation.XmlType;

import com.zimbra.common.soap.AdminConstants;
import com.zimbra.soap.type.ZmBoolean;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_VERIFY_INDEX_RESPONSE)
@XmlType(propOrder = {"status", "message"})
public class VerifyIndexResponse {

    /**
     * @zm-api-field-tag verify-result-status
     * @zm-api-field-description Result status of verification.  Valid values "true" and "false" (Not "1" and "0")
     */
    @XmlElement(name=AdminConstants.E_STATUS /* status */, required=true)
    private final ZmBoolean status;

    /**
     * @zm-api-field-tag verification-output
     * @zm-api-field-description Verification output
     */
    @XmlElement(name=AdminConstants.E_MESSAGE /* message */, required=true)
    private final String message;

    /**
     * no-argument constructor wanted by JAXB
     */
     @SuppressWarnings("unused")
    private VerifyIndexResponse() {
        this(false,(String) null);
    }

    public VerifyIndexResponse(boolean status, String message) {
        this.status = ZmBoolean.fromBool(status);
        this.message = message;
    }

    public boolean isStatus() { return ZmBoolean.toBool(status); }
    public String getMessage() { return message; }
}
