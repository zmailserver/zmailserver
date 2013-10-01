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
package com.zimbra.cs.redolog.op;

import java.io.IOException;

import com.zimbra.cs.mailbox.MailItem;
import com.zimbra.cs.mailbox.MailboxOperation;
import com.zimbra.cs.redolog.RedoLogInput;

public class RecoverItem extends CopyItem {

    public RecoverItem() {
        super();
        mOperation = MailboxOperation.RecoverItem;
        setFromDumpster(true);
    }

    public RecoverItem(int mailboxId, MailItem.Type type, int folderId) {
        super(mailboxId, type, folderId);
        mOperation = MailboxOperation.RecoverItem;
        setFromDumpster(true);
    }

    @Override
    protected void deserializeData(RedoLogInput in) throws IOException {
        super.deserializeData(in);
        setFromDumpster(true);  // shouldn't be necessary, but let's be absolutely sure
    }
}
