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

package org.zmail.soap.voice.message;

import com.google.common.base.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.VoiceConstants;
import org.zmail.soap.voice.type.VoiceMsgActionInfo;
import org.zmail.soap.json.jackson.annotate.ZmailUniqueElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=VoiceConstants.E_VOICE_MSG_ACTION_RESPONSE)
public class VoiceMsgActionResponse {

    /**
     * @zm-api-field-description Information on action performed
     */
    @XmlElement(name=MailConstants.E_ACTION /* action */, required=true)
    @ZmailUniqueElement
    private VoiceMsgActionInfo action;

    public VoiceMsgActionResponse() {
    }

    public void setAction(VoiceMsgActionInfo action) { this.action = action; }
    public VoiceMsgActionInfo getAction() { return action; }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("action", action);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
