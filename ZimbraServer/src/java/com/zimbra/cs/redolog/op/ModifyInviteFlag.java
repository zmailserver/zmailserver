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

package com.zimbra.cs.redolog.op;

import java.io.IOException;

import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.mailbox.MailboxOperation;
import com.zimbra.cs.redolog.RedoLogInput;
import com.zimbra.cs.redolog.RedoLogOutput;

/**
 * This class is obsolete.
 */
public class ModifyInviteFlag extends RedoableOp {

    private int mId = UNKNOWN_ID;
    int mCompNum; // component number
    private int mFlag;
    private boolean mAdd; // true to OR the bit in, false to AND it out

    public ModifyInviteFlag() {
        super(MailboxOperation.ModifyInviteFlag);
    }

    public ModifyInviteFlag(int mailboxId, int id, int compNum, int flag, boolean add) {
        this();
        setMailboxId(mailboxId);
        mId = id;
        mCompNum = compNum;
        mFlag = flag;
        mAdd = add;
    }
    
    @Override public void redo() throws Exception {
        MailboxManager.getInstance().getMailboxById(getMailboxId());
//        mbox.modifyInviteFlag(getOperationContext(), mId, mCompNum, mFlag, mAdd);
    }

    @Override protected String getPrintableData() {
        StringBuffer sb = new StringBuffer("id=");
        sb.append(mId).append(", comp=").append(mCompNum);
        sb.append(", flag=").append(mFlag).append(", add=").append(mAdd);
        return sb.toString();
    }

    @Override protected void serializeData(RedoLogOutput out) throws IOException {
        out.writeInt(mId);
        out.writeInt(mCompNum);
        out.writeInt(mFlag);
        out.writeBoolean(mAdd);
    }

    @Override protected void deserializeData(RedoLogInput in) throws IOException {
        mId = in.readInt();
        mCompNum = in.readInt();
        mFlag = in.readInt();
        mAdd  = in.readBoolean();
    }
    
}
