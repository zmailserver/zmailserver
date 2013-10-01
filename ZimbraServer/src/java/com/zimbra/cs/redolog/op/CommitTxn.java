/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
 * Created on 2004. 7. 22.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.zimbra.cs.redolog.op;

import java.io.IOException;

import com.zimbra.cs.mailbox.MailboxOperation;
import com.zimbra.cs.redolog.RedoCommitCallback;
import com.zimbra.cs.redolog.RedoLogInput;
import com.zimbra.cs.redolog.RedoLogOutput;

/**
 * @author jhahm
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CommitTxn extends ControlOp {

    private MailboxOperation mTxnOpCode;

    public CommitTxn() {
        super(MailboxOperation.CommitTxn);
	}

    public CommitTxn(RedoableOp changeEntry) {
    	super(MailboxOperation.CommitTxn, changeEntry.getTransactionId());
        setMailboxId(changeEntry.getMailboxId());
        mTxnOpCode = changeEntry.getOperation();
        mCommitCallback = changeEntry.mCommitCallback;
    }

    public MailboxOperation getTxnOpCode() {
        return mTxnOpCode;
    }

    protected String getPrintableData() {
        StringBuffer sb = new StringBuffer("txnType=");
        sb.append(mTxnOpCode.name());
        return sb.toString();
    }

    protected void serializeData(RedoLogOutput out) throws IOException {
        out.writeInt(mTxnOpCode.getCode());
    }

    protected void deserializeData(RedoLogInput in) throws IOException {
        mTxnOpCode = MailboxOperation.fromInt(in.readInt());
    }

    /**
     * Returns the callback object that was passed in at transaction start time.
     * @return
     */
    public RedoCommitCallback getCallback() {
        return mCommitCallback;
    }
}
