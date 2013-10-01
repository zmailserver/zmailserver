/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.mailbox.MailboxOperation;
import com.zimbra.cs.redolog.RedoLogInput;
import com.zimbra.cs.redolog.RedoLogOutput;

/**
 * @since 2004. 12. 13.
 */
public class RenameItem extends RedoableOp {

    protected int mId;
    protected MailItem.Type type;
    protected int mFolderId;
    protected String mName;
    protected Long mDate;

    public RenameItem() {
        super(MailboxOperation.RenameItem);
        mId = mFolderId = UNKNOWN_ID;
        type = MailItem.Type.UNKNOWN;
    }

    public RenameItem(int mailboxId, int id, MailItem.Type type, String name, int folderId) {
        this(mailboxId, id, type, name, folderId, null);
    }

        public RenameItem(int mailboxId, int id, MailItem.Type type, String name, int folderId, Long date) {
        this();
        setMailboxId(mailboxId);
        mId = id;
        this.type = type;
        mFolderId = folderId;
        mName = name != null ? name : "";
        mDate = date;
    }

    @Override
    protected String getPrintableData() {
        return "id=" + mId + ", type=" + type + ", name=" + mName + ",parent=" + mFolderId + ", date=" + mDate;
    }

    @Override
    protected void serializeData(RedoLogOutput out) throws IOException {
        out.writeInt(mId);
        out.writeInt(mFolderId);
        out.writeUTF(mName);
        out.writeByte(type.toByte());

        // mDate added in 1.40
        out.writeBoolean(mDate != null);
        if (mDate != null) {
            out.writeLong(mDate);
        }
    }

    @Override
    protected void deserializeData(RedoLogInput in) throws IOException {
        mId = in.readInt();
        mFolderId = in.readInt();
        mName = in.readUTF();
        type = MailItem.Type.of(in.readByte());
        if (getVersion().atLeast(1, 40)) {
            if (in.readBoolean()) {
                mDate = in.readLong();
            }
        }
    }

    @Override
    public void redo() throws Exception {
        Mailbox mailbox = MailboxManager.getInstance().getMailboxById(getMailboxId());
        mailbox.rename(getOperationContext(), mId, type, mName, mFolderId, mDate);
    }
}
