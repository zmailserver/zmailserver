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

import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxOperation;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.redolog.RedoLogInput;
import org.zmail.cs.redolog.RedoLogOutput;

public class SaveChat extends CreateChat {
    
    private int mImapId;           // new IMAP id for this message
    
    public SaveChat() {
        mOperation = MailboxOperation.SaveChat;
    }
    
    public SaveChat(int mailboxId, int chatId, String digest, int msgSize, int folderId, int flags, String[] tags) {
        super(mailboxId, digest, msgSize, folderId, flags, tags);
        mOperation = MailboxOperation.SaveChat;
        setMessageId(chatId);
    }

    public int getImapId() {
        return mImapId;
    }

    public void setImapId(int imapId) {
        mImapId = imapId;
    }

    @Override
    protected String getPrintableData() {
        return super.getPrintableData() + ",imap=" + mImapId;
    }
    
    @Override
    protected void serializeData(RedoLogOutput out) throws IOException {
        out.writeInt(mImapId);
        super.serializeData(out);
    }

    @Override
    protected void deserializeData(RedoLogInput in) throws IOException {
        mImapId = in.readInt();
        super.deserializeData(in);
    }
    
    @Override
    public void redo() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxById(getMailboxId());

        ParsedMessage pm = new ParsedMessage(getMessageBody(), getTimestamp(), mbox.attachmentsIndexingEnabled());
        mbox.updateChat(getOperationContext(), pm, getMessageId());
    }
}
