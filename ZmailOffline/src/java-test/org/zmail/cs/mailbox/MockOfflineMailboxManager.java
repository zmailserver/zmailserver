/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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

package org.zmail.cs.mailbox;

import java.util.HashMap;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.Mailbox.MailboxData;

public class MockOfflineMailboxManager extends MailboxManager {

    public enum Type { ZCS, DESKTOP};

    private Type mboxType = Type.DESKTOP;

    public MockOfflineMailboxManager(Type type) throws ServiceException {
        super(true);
        mailboxes = new HashMap<String,DesktopMailbox>();
        mboxType = type;
    }

    @Override
    public Mailbox getMailboxByAccountId(String accountId)
        throws ServiceException {

        DesktopMailbox mbox = mailboxes.get(accountId);
        if (mbox != null)
            return mbox;
        Account account = Provisioning.getInstance().getAccount(accountId);
        switch (mboxType) {
            case DESKTOP :  mbox = new MockDesktopMailbox();
                            break;
            case ZCS     :  MailboxData data = new MailboxData();
                            data.accountId = account.getId();
                            mbox = new MockZcsMailbox(account, data);
                            break;
        }
        mailboxes.put(accountId, mbox);
        return mbox;
    }

    private HashMap<String,DesktopMailbox> mailboxes;
}
