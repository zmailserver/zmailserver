/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import com.google.common.annotations.VisibleForTesting;
import org.zmail.common.localconfig.DebugConfig;
import org.zmail.cs.account.Account;
import org.zmail.cs.datasource.DataSourceListener;
import org.zmail.cs.fb.FreeBusyProvider;
import org.zmail.cs.filter.FilterListener;
import org.zmail.cs.mailbox.MailItem.Type;
import org.zmail.cs.mailbox.acl.AclPushListener;
import org.zmail.cs.mailbox.acl.ShareExpirationListener;
import org.zmail.cs.mailbox.alerts.CalItemReminderService;
import org.zmail.cs.session.PendingModifications;
import org.zmail.cs.util.ZmailApplication;


public abstract class MailboxListener {

    public static class ChangeNotification {
        public Account mailboxAccount;
        public OperationContext ctxt;
        public int lastChangeId;
        public PendingModifications mods;
        public MailboxOperation op;
        public long timestamp;

        public ChangeNotification(Account account, PendingModifications mods, OperationContext ctxt, int lastChangeId,
                MailboxOperation op, long timestamp) {
            this.mailboxAccount = account;
            this.mods = mods;
            this.ctxt = ctxt;
            this.lastChangeId = lastChangeId;
            this.op = op;
            this.timestamp = timestamp;
        }
    }

    /**
     * Listeners will be notified at the end of each <code>Mailbox</code>
     * transaction.  The listener must not throw any Exception in this method.
     * The listener must refrain from making synchronous network operation
     * or other long latency operation within notify method.
     *
     * @param notification
     */
    public abstract void notify(ChangeNotification notification);

    protected static final Set<Type> ALL_ITEM_TYPES = EnumSet.allOf(Type.class);

    /**
     * Listener can indicate specific item types it is interested in,
     * which will reduce the number of notification callbacks.
     *
     * @return Set of item types listener wants to be notified of
     */
    public Set<MailItem.Type> registerForItemTypes() {
        return ALL_ITEM_TYPES;
    }

    private static final HashSet<MailboxListener> sListeners;

    static {
        sListeners = new HashSet<MailboxListener>();
        reset();
    }

    static void reset() {
        sListeners.clear();
        ZmailApplication application = ZmailApplication.getInstance();
        if (application.supports(CalItemReminderService.class) && !DebugConfig.disableCalendarReminderEmail) {
            register(new CalItemReminderService());
        }
        register(new FilterListener());
        register(new MemcachedCacheManager());
        register(new FreeBusyProvider.Listener());
        register(new DataSourceListener());
        register(new ShareStartStopListener());
        if (application.supports(AclPushListener.class)) {
            register(new AclPushListener());
        }
        if (application.supports(ShareExpirationListener.class) && !DebugConfig.disableShareExpirationListener) {
            register(new ShareExpirationListener());
        }
    }

    public static void register(MailboxListener listener) {
        synchronized (sListeners) {
            sListeners.add(listener);
        }
    }

    @VisibleForTesting
    static void unregister(MailboxListener listener) {
        synchronized (sListeners) {
            sListeners.remove(listener);
        }
    }

    public static void notifyListeners(ChangeNotification notification) {
        for (MailboxListener l : sListeners) {
            if (!Collections.disjoint(notification.mods.changedTypes, l.registerForItemTypes())) {
                l.notify(notification);
            }
        }
    }
}
