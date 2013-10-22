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
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.MailConstants;
import com.zimbra.soap.mail.type.ChatSummary;
import com.zimbra.soap.mail.type.MessageSummary;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_GET_MSG_METADATA_RESPONSE)
public class GetMsgMetadataResponse {

    /**
     * @zm-api-field-description Message metadata
     */
    @XmlElements({
        @XmlElement(name=MailConstants.E_CHAT /* chat */, type=ChatSummary.class),
        @XmlElement(name=MailConstants.E_MSG /* m */, type=MessageSummary.class)
    })
    private List<MessageSummary> messages = Lists.newArrayList();

    public GetMsgMetadataResponse() {
    }

    public void setMessages(Iterable <MessageSummary> messages) {
        this.messages.clear();
        if (messages != null) {
            Iterables.addAll(this.messages,messages);
        }
    }

    public void addMessage(MessageSummary message) {
        this.messages.add(message);
    }

    public List<MessageSummary> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("messages", messages);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
