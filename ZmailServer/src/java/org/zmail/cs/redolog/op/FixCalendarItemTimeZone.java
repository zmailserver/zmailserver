/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import com.zimbra.cs.mailbox.MailboxOperation;
import com.zimbra.cs.redolog.RedoLogInput;
import com.zimbra.cs.redolog.RedoLogOutput;

public class FixCalendarItemTimeZone extends RedoableOp {

    private int mId;
    private long mAfter;
    private String mCountry;  // ISO-3166 two-letter country code, or null for world

    public FixCalendarItemTimeZone() {
        super(MailboxOperation.FixCalendarItemTimeZone);
    }

    public FixCalendarItemTimeZone(int mailboxId, int itemId, long after, String country) {
        this();
        setMailboxId(mailboxId);
        mId = itemId;
        mAfter = after;
        mCountry = country;
    }

    @Override protected String getPrintableData() {
        StringBuilder sb = new StringBuilder("id=");
        sb.append(mId);
        sb.append(", after=").append(mAfter);
        sb.append(", country=").append(mCountry);
        return sb.toString();
    }

    @Override protected void serializeData(RedoLogOutput out) throws IOException {
        out.writeInt(mId);
        out.writeLong(mAfter);
        out.writeUTF(mCountry);
    }

    @Override protected void deserializeData(RedoLogInput in) throws IOException {
        mId = in.readInt();
        mAfter = in.readLong();
        mCountry = in.readUTF();
    }

    @Override public void redo() throws Exception {
        // do nothing; this op has been deprecated
    }
}
