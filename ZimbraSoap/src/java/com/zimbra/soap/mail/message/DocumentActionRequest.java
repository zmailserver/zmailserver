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

package com.zimbra.soap.mail.message;

import com.google.common.base.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.MailConstants;
import com.zimbra.common.soap.OctopusXmlConstants;
import com.zimbra.soap.mail.type.DocumentActionSelector;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required false
 * @zm-api-command-description Document Action
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=OctopusXmlConstants.E_DOCUMENT_ACTION_REQUEST)
public class DocumentActionRequest {

    /**
     * @zm-api-field-description Document action selector
     * <br />
     * Document specific operations : <b>watch|!watch|grant|!grant</b>
     */
    @XmlElement(name=MailConstants.E_ACTION /* action */, required=true)
    private DocumentActionSelector action;

    private DocumentActionRequest() {
    }

    private DocumentActionRequest(DocumentActionSelector action) {
        setAction(action);
    }

    public static DocumentActionRequest create(DocumentActionSelector action) {
        return new DocumentActionRequest(action);
    }

    public void setAction(DocumentActionSelector action) { this.action = action; }
    public DocumentActionSelector getAction() { return action; }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("action", action);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
