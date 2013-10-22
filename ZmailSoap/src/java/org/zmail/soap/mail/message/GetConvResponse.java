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
import com.zimbra.soap.mail.type.ConversationInfo;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_GET_CONV_RESPONSE)
public class GetConvResponse {

    /**
     * @zm-api-field-description Conversation information
     */
    @XmlElement(name=MailConstants.E_CONV, required=true)
    private final ConversationInfo conversation;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private GetConvResponse() {
        this((ConversationInfo) null);
    }

    public GetConvResponse(ConversationInfo conversation) {
        this.conversation = conversation;
    }

    public ConversationInfo getConversation() { return conversation; }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("conversation", conversation)
            .toString();
    }
}
