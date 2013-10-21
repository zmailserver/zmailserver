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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.OctopusXmlConstants;
import org.zmail.soap.mail.type.IdAndOperation;
import org.zmail.soap.json.jackson.annotate.ZmailUniqueElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=OctopusXmlConstants.E_DOCUMENT_ACTION_RESPONSE)
public class DocumentActionResponse {

    /**
     * @zm-api-field-description Details of action
     */
    @ZmailUniqueElement
    @XmlElement(name=MailConstants.E_ACTION /* action */, required=true)
    private IdAndOperation action;

    @SuppressWarnings("unused")
    private DocumentActionResponse() {
    }

    public DocumentActionResponse(IdAndOperation action) {
        setAction(action);
    }

    public static DocumentActionResponse create (IdAndOperation action) {
        return new DocumentActionResponse(action);
    }

    public void setAction(IdAndOperation action) { this.action = action; }
    public IdAndOperation getAction() { return action; }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("action", action);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
