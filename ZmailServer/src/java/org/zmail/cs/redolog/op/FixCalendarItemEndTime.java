/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.redolog.op;

import java.io.IOException;

import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxOperation;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.redolog.RedoLogInput;
import org.zmail.cs.redolog.RedoLogOutput;

public class FixCalendarItemEndTime extends RedoableOp {

    private int mId;

    public FixCalendarItemEndTime()  {
        super(MailboxOperation.FixCalendarItemEndTime);
    }

    public FixCalendarItemEndTime(int mailboxId, int itemId) {
        this();
        setMailboxId(mailboxId);
        mId = itemId;
    }

    @Override protected String getPrintableData() {
        StringBuilder sb = new StringBuilder("id=");
        sb.append(mId);
        return sb.toString();
    }

    @Override protected void serializeData(RedoLogOutput out) throws IOException {
        out.writeInt(mId);
    }

    @Override protected void deserializeData(RedoLogInput in) throws IOException {
        mId = in.readInt();
    }

    @Override public void redo() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxById(getMailboxId());
        OperationContext octxt = getOperationContext();
        CalendarItem calItem = mbox.getCalendarItemById(octxt, mId);
        if (calItem != null)
            mbox.fixCalendarItemEndTime(octxt, calItem);
    }
}
