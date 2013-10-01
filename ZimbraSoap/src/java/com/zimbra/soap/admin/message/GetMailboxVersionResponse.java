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

import com.google.common.base.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zimbra.common.soap.BackupConstants;
import com.zimbra.soap.admin.type.MailboxVersionInfo;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=BackupConstants.E_GET_MAILBOX_VERSION_RESPONSE)
@XmlType(propOrder = {})
public class GetMailboxVersionResponse {

    /**
     * @zm-api-field-description Mailbox Version information
     */
    @XmlElement(name=BackupConstants.E_ACCOUNT /* account */, required=true)
    private MailboxVersionInfo account;

    private GetMailboxVersionResponse() {
    }

    private GetMailboxVersionResponse(MailboxVersionInfo account) {
        setAccount(account);
    }

    public static GetMailboxVersionResponse create(MailboxVersionInfo account) {
        return new GetMailboxVersionResponse(account);
    }

    public void setAccount(MailboxVersionInfo account) { this.account = account; }
    public MailboxVersionInfo getAccount() { return account; }

    public Objects.ToStringHelper addToStringInfo(
                Objects.ToStringHelper helper) {
        return helper
            .add("account", account);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this))
                .toString();
    }
}
