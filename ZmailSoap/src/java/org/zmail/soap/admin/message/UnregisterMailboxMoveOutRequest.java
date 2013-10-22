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

import com.zimbra.common.soap.BackupConstants;
import com.zimbra.soap.admin.type.MailboxMoveSpec;

/**
 * @zm-api-command-network-edition
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description This request is invoked by move destination server against move source server to
 * indicate the completion of mailbox move.  This request is also invoked to reset the state after a mailbox move that
 * died unexpectedly, such as when the destination server crashed.
 * <br />
 * <br />
 * NO_SUCH_MOVE_OUT fault is returned if there is no move-out in progress.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=BackupConstants.E_UNREGISTER_MAILBOX_MOVE_OUT_REQUEST)
public class UnregisterMailboxMoveOutRequest {

    /**
     * @zm-api-field-description Details of Mailbox Move
     */
    @XmlElement(name=BackupConstants.E_ACCOUNT /* account */, required=true)
    private MailboxMoveSpec account;

    private UnregisterMailboxMoveOutRequest() {
    }

    private UnregisterMailboxMoveOutRequest(MailboxMoveSpec account) {
        setAccount(account);
    }

    public static UnregisterMailboxMoveOutRequest create(MailboxMoveSpec account) {
        return new UnregisterMailboxMoveOutRequest(account);
    }

    public void setAccount(MailboxMoveSpec account) { this.account = account; }
    public MailboxMoveSpec getAccount() { return account; }

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
