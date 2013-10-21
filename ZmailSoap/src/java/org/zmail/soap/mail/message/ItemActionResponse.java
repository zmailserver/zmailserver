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
import org.zmail.soap.mail.type.IdAndOperation;
import org.zmail.soap.json.jackson.annotate.ZmailUniqueElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_ITEM_ACTION_RESPONSE)
public class ItemActionResponse {

    /**
     * @zm-api-field-description The <b>&lt;action></b> element in the response always contains the same id list that
     * the client sent in the request.  In particular, IDs that were ignored due to constraints are included in the
     * id list.
     */
    @ZmailUniqueElement
    @XmlElement(name=MailConstants.E_ACTION /* action */, required=true)
    private final IdAndOperation action;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private ItemActionResponse() {
        this((IdAndOperation) null);
    }

    public ItemActionResponse(IdAndOperation action) {
        this.action = action;
    }

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
