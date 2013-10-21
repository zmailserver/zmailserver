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

package org.zmail.cs.account.callback;

import java.util.Map;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.db.DbOutOfOffice;
import org.zmail.cs.db.DbPool;
import org.zmail.cs.db.DbPool.DbConnection;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.Notification;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;

public class OutOfOfficeCallback extends AttributeCallback {

    @Override 
    public void preModify(CallbackContext context, String attrName, Object value,
            Map attrsToModify, Entry entry) {
    }

    @Override 
    public void postModify(CallbackContext context, String attrName, Entry entry) {
        
        if (context.isDoneAndSetIfNot(OutOfOfficeCallback.class)) {
            return;
        }
        
        if (!context.isCreate()) {
            ZmailLog.misc.info("need to reset vacation info");
            if (entry instanceof Account) {
                handleOutOfOffice((Account)entry);
            }
        }
    }

    private void handleOutOfOffice(Account account) {
        try {
            Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account);

            DbConnection conn = null;
            try {
                // clear the OOF database for this account
                conn = DbPool.getConnection(mbox);
                DbOutOfOffice.clear(conn, mbox);
                conn.commit();
                ZmailLog.misc.info("reset vacation info");

                // Convenient place to prune old data, until we determine that this
                //  needs to be a separate scheduled process.
                // TODO: only prune once a day?
                long interval = account.getTimeInterval(Provisioning.A_zmailPrefOutOfOfficeCacheDuration, Notification.DEFAULT_OUT_OF_OFFICE_CACHE_DURATION_MILLIS);
                DbOutOfOffice.prune(conn, interval);
                conn.commit();
            } catch (ServiceException e) {
                DbPool.quietRollback(conn);
            } finally {
                DbPool.quietClose(conn);
            }
        } catch (ServiceException e) {
            ZmailLog.misc.warn("error handling out-of-office", e);
        }
    }
}
