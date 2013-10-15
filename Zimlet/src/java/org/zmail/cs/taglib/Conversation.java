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

import java.util.List;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.OperationContext;

public class Conversation extends Message {
    private static final long serialVersionUID = -2306183433671648674L;

    String mIndex;

    public void setIndex(String val) {
        mIndex = val;
    }

    public String getIndex() {
        return mIndex;
    }

    public String getContentStart(Account acct, OperationContext octxt) throws ZmailTagException, ServiceException {
        if (mId == null) {
            throw ZmailTagException.MISSING_ATTR("id");
        }
        if (mField == null) {
            throw ZmailTagException.MISSING_ATTR("field");
        }
        if (mIndex == null) {
            throw ZmailTagException.MISSING_ATTR("index");
        }
        int cid = Integer.parseInt(mId);
        int index = Integer.parseInt(mIndex);
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(acct.getId());
        List<org.zmail.cs.mailbox.Message> msgs = mbox.getMessagesByConversation(octxt, cid);
        return getMessageContent(msgs.get(index));
    }
}
