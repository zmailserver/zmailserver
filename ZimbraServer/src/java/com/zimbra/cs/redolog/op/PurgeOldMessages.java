/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

/*
 * Created on 2005. 4. 4.
 */
package com.zimbra.cs.redolog.op;

import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.mailbox.MailboxOperation;
import com.zimbra.cs.redolog.RedoLogInput;
import com.zimbra.cs.redolog.RedoLogOutput;

/**
 * Purge old messages.  The arguments to this operation are mailbox ID and
 * operation timestamp, both of which are managed by the superclass.  See
 * Mailbox.purgeMessages() for more info.
 */
public class PurgeOldMessages extends RedoableOp {

    public PurgeOldMessages() {
        super(MailboxOperation.PurgeOldMessages);
    }

    public PurgeOldMessages(int mailboxId) {
        this();
        setMailboxId(mailboxId);
    }

    @Override protected String getPrintableData() {
        // no members to print
        return null;
    }

    @Override protected void serializeData(RedoLogOutput out) {
        // no members to serialize
    }

    @Override protected void deserializeData(RedoLogInput in) {
        // no members to deserialize
    }

    @Override public boolean isDeleteOp() {
        return true;
    }

    @Override public void redo() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxById(getMailboxId());
        mbox.purgeMessages(getOperationContext());
    }
}
