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
package com.zimbra.cs.mailbox.alerts;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.mailbox.CalendarItem;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.mailbox.ScheduledTask;
import com.zimbra.cs.mailbox.calendar.Invite;

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
        if (ZimbraLog.scheduler.isDebugEnabled())
            ZimbraLog.scheduler.debug("Running task %s", this);
        Mailbox mbox = MailboxManager.getInstance().getMailboxById(getMailboxId());
        if (mbox == null) {
            ZimbraLog.scheduler.error("Mailbox with id %s does not exist", getMailboxId());
            return null;
        }
        Integer calItemId = new Integer(getProperty(CAL_ITEM_ID_PROP_NAME));
        CalendarItem calItem;
        try {
            calItem = mbox.getCalendarItemById(null, calItemId);
        } catch (ServiceException e) {
            ZimbraLog.scheduler.warn("Calendar item with id %s does not exist", calItemId);
            return null;
        }
        if (calItem.inTrash()) {
            ZimbraLog.scheduler.debug("Calendar item with id %s is in Trash", calItemId);
            return null;
        }
        Integer invId = new Integer(getProperty(INV_ID_PROP_NAME));
        Integer compNum = new Integer(getProperty(COMP_NUM_PROP_NAME));
        Invite invite = calItem.getInvite(invId, compNum);
        if (invite == null) {
            ZimbraLog.scheduler.warn("Invite with id %s and comp num %s does not exist", invId, compNum);
            return null;
        }
        sendReminder(calItem, invite);
        return calItem;
    }

    protected abstract void sendReminder(CalendarItem calItem, Invite invite) throws Exception;
}
