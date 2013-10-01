/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import com.zimbra.cs.mailbox.MailServiceException;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.mailbox.MailboxOperation;
import com.zimbra.cs.redolog.RedoLogInput;
import com.zimbra.cs.redolog.RedoLogOutput;

public class SaveWiki extends SaveDocument {

    private String mWikiword;

    public SaveWiki() {
        mOperation = MailboxOperation.SaveWiki;
    }

    public SaveWiki(int mailboxId, String digest, int msgSize, int folderId) {
        super(mailboxId, digest, msgSize, folderId, 0);
        mOperation = MailboxOperation.SaveWiki;
    }

    public String getWikiword() {
        return mWikiword;
    }

    public void setWikiword(String w) {
        mWikiword = w;
    }

    @Override protected void serializeData(RedoLogOutput out) throws IOException {
        out.writeUTF(mWikiword);
        super.serializeData(out);
    }

    @Override protected void deserializeData(RedoLogInput in) throws IOException {
        mWikiword = in.readUTF();
        super.deserializeData(in);
    }

    @Override public void redo() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxById(getMailboxId());
        try {
            mbox.createWiki(getOperationContext(), getFolderId(), mWikiword, getAuthor(), getDescription(), getAdditionalDataStream());
        } catch (MailServiceException e) {
            if (e.getCode() == MailServiceException.ALREADY_EXISTS) {
                mLog.info("Wiki " + getMessageId() + " is already in mailbox " + mbox.getId());
                return;
            } else {
                throw e;
            }
        }
    }
}
