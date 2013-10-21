/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.mailbox.alerts;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.ScheduledTask;
import org.zmail.cs.mailbox.calendar.Invite;

/**
 */
public abstract class CalItemReminderTaskBase extends ScheduledTask {

    static final String CAL_ITEM_ID_PROP_NAME = "calItemId";
    static final String INV_ID_PROP_NAME = "invId";
    static final String COMP_NUM_PROP_NAME = "compNum";
    static final String NEXT_INST_START_PROP_NAME = "nextInstStart";

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    public CalendarItem call() throws Exception {
        if (ZmailLog.scheduler.isDebugEnabled())
            ZmailLog.scheduler.debug("Running task %s", this);
        Mailbox mbox = MailboxManager.getInstance().getMailboxById(getMailboxId());
        if (mbox == null) {
            ZmailLog.scheduler.error("Mailbox with id %s does not exist", getMailboxId());
            return null;
        }
        Integer calItemId = new Integer(getProperty(CAL_ITEM_ID_PROP_NAME));
        CalendarItem calItem;
        try {
            calItem = mbox.getCalendarItemById(null, calItemId);
        } catch (ServiceException e) {
            ZmailLog.scheduler.warn("Calendar item with id %s does not exist", calItemId);
            return null;
        }
        if (calItem.inTrash()) {
            ZmailLog.scheduler.debug("Calendar item with id %s is in Trash", calItemId);
            return null;
        }
        Integer invId = new Integer(getProperty(INV_ID_PROP_NAME));
        Integer compNum = new Integer(getProperty(COMP_NUM_PROP_NAME));
        Invite invite = calItem.getInvite(invId, compNum);
        if (invite == null) {
            ZmailLog.scheduler.warn("Invite with id %s and comp num %s does not exist", invId, compNum);
            return null;
        }
        sendReminder(calItem, invite);
        return calItem;
    }

    protected abstract void sendReminder(CalendarItem calItem, Invite invite) throws Exception;
}
