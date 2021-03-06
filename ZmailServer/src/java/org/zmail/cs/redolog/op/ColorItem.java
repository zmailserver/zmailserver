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
package org.zmail.cs.redolog.op;

import java.io.IOException;
import java.util.Arrays;

import org.zmail.common.mailbox.Color;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxOperation;
import org.zmail.cs.redolog.RedoLogInput;
import org.zmail.cs.redolog.RedoLogOutput;

/**
 * @since Sep 19, 2005
 */
public class ColorItem extends RedoableOp {

    private int[] mIds;
    private MailItem.Type type;
    private long mColor;

    public ColorItem() {
        super(MailboxOperation.ColorItem);
    }

    public ColorItem(int mailboxId, int[] ids, MailItem.Type type, Color color) {
        this();
        setMailboxId(mailboxId);
        mIds = ids;
        this.type = type;
        mColor = color.getValue();
    }

    @Override
    protected String getPrintableData() {
        StringBuffer sb = new StringBuffer("id=");
        sb.append(Arrays.toString(mIds)).append(", color=").append(mColor);
        return sb.toString();
    }

    @Override
    protected void serializeData(RedoLogOutput out) throws IOException {
        out.writeInt(-1);
        out.writeByte(type.toByte());
        // mColor from byte to long in Version 1.27
        out.writeLong(mColor);
        out.writeInt(mIds == null ? 0 : mIds.length);
        if (mIds != null) {
            for (int i = 0; i < mIds.length; i++)
                out.writeInt(mIds[i]);
        }
    }

    @Override
    protected void deserializeData(RedoLogInput in) throws IOException {
        int id = in.readInt();
        if (id > 0)
            mIds = new int[] { id };
        type = MailItem.Type.of(in.readByte());
        if (getVersion().atLeast(1, 27))
            mColor = in.readLong();
        else
            mColor = in.readByte();
        if (id <= 0) {
            mIds = new int[in.readInt()];
            for (int i = 0; i < mIds.length; i++)
                mIds[i] = in.readInt();
        }
    }

    @Override
    public void redo() throws Exception {
        int mboxId = getMailboxId();
        Mailbox mailbox = MailboxManager.getInstance().getMailboxById(mboxId);
        mailbox.setColor(getOperationContext(), mIds, type, Color.fromMetadata(mColor));
    }
}
