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
import org.zmail.soap.mail.type.ConversationSpec;
import org.zmail.soap.json.jackson.annotate.ZmailUniqueElement;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required false
 * @zm-api-command-description Get Conversation
 * <br />
 * GetConvRequest gets information about the 1 conversation named by id's value.
 * It will return exactly 1 conversation element.
 * <br />
 * <br />
 * If fetch="1|all" is included, the full expanded message structure is inlined for the first (or for all) messages
 * in the conversation.  If fetch="{item-id}", only the message with the given {item-id} is expanded inline.
 * <br />
 * if headers are requested, any matching headers are inlined into the response (not available when raw="1")
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_GET_CONV_REQUEST)
public class GetConvRequest {

    /**
     * @zm-api-field-description Conversation specification
     */
    @XmlElement(name=MailConstants.E_CONV /* c */, required=true)
    @ZmailUniqueElement
    private final ConversationSpec conversation;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private GetConvRequest() {
        this((ConversationSpec) null);
    }

    public GetConvRequest(ConversationSpec conversation) {
        this.conversation = conversation;
    }

    public ConversationSpec getConversation() { return conversation; }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("conversation", conversation)
            .toString();
    }
}
