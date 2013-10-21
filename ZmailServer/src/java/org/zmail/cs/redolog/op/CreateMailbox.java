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
 * Created on 2004. 11. 2.
 */
package org.zmail.cs.redolog.op;

import java.io.IOException;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxOperation;
import org.zmail.cs.redolog.MailboxIdConflictException;
import org.zmail.cs.redolog.RedoException;
import org.zmail.cs.redolog.RedoLogInput;
import org.zmail.cs.redolog.RedoLogOutput;

public class CreateMailbox extends RedoableOp {

    private String mAccountId;

    public CreateMailbox() {
        super(MailboxOperation.CreateMailbox);
    }

    public CreateMailbox(String accountId) {
        this();
        mAccountId = accountId;
    }

    @Override protected String getPrintableData() {
        StringBuffer sb = new StringBuffer("account=").append(mAccountId != null ? mAccountId : "");
        return sb.toString();
    }

    @Override protected void serializeData(RedoLogOutput out) throws IOException {
        out.writeUTF(mAccountId);
    }

    @Override protected void deserializeData(RedoLogInput in) throws IOException {
        mAccountId = in.readUTF();
    }

    @Override public void redo() throws Exception {
        int opMboxId = getMailboxId();
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(mAccountId, false);

        if (mbox == null) {
            Account account = Provisioning.getInstance().get(AccountBy.id, mAccountId);
            if (account == null) { 
                throw new RedoException("Account " + mAccountId + " does not exist", this);
            }

            mbox = MailboxManager.getInstance().createMailbox(getOperationContext(), account);
            if (mbox == null) {
                //something went really wrong
                throw new RedoException("unable to create mailbox for accountId " + mAccountId, this);
            }
        }

        int mboxId = mbox.getId();
        if (opMboxId == mboxId) {
            mLog.info("Mailbox " + opMboxId + " for account " + mAccountId + " already exists");
            return;
        } else {
            throw new MailboxIdConflictException(mAccountId, opMboxId, mboxId, this);
        }
    }
}
