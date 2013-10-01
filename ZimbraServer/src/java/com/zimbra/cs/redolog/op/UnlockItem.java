/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.redolog.op;

import com.zimbra.cs.mailbox.MailItem;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxOperation;

public class UnlockItem extends LockItem {

    public UnlockItem() {
        super();
        mOperation = MailboxOperation.UnlockItem;
    }

    public UnlockItem(int mailboxId, int id, MailItem.Type type, String accountId) {
        super(mailboxId, id, type, accountId);
        mOperation = MailboxOperation.UnlockItem;
    }

    @Override
    public void redo() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxById(getMailboxId());
        mbox.unlock(getOperationContext(), id, type, accountId);
    }
}
