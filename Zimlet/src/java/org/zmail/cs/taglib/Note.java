/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Zimlets
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.taglib;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.OperationContext;

public class Note extends ZmailTag {
    private static final long serialVersionUID = -3525900802675257570L;

    private String mId;

    @Override
    public void setId(String val) {
        mId = val;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public String getContentStart(Account acct, OperationContext octxt) throws ZmailTagException, ServiceException {
        if (mId == null) {
            throw ZmailTagException.MISSING_ATTR("id");
        }
        int mid = Integer.parseInt(mId);
//        String id = acct.getId();
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(id);
        org.zmail.cs.mailbox.Note note = mbox.getNoteById(octxt, mid);

        return note.getText();
    }
}
