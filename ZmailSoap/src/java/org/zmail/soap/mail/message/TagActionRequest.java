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
import org.zmail.soap.mail.type.TagActionSelector;
import org.zmail.soap.json.jackson.annotate.ZmailUniqueElement;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required false
 * @zm-api-command-description Perform an action on a tag
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_TAG_ACTION_REQUEST)
public class TagActionRequest {

    /**
     * @zm-api-field-description Specify action to perform.
     * <br />
     * Caller must supply one of <b>"id"</b> or <b>"tn"</b>
     * <br />
     * Supported operations: <b>"read|!read|color|delete|rename|update|retentionpolicy"</b>
     * <br />
     * If op="update", the caller can specify "name" and/or "color"
     */
    @ZmailUniqueElement
    @XmlElement(name=MailConstants.E_ACTION /* action */, required=true)
    private final TagActionSelector action;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private TagActionRequest() {
        this((TagActionSelector) null);
    }

    public TagActionRequest(TagActionSelector action) {
        this.action = action;
    }

    public TagActionSelector getAction() { return action; }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("action", action)
            .toString();
    }
}
