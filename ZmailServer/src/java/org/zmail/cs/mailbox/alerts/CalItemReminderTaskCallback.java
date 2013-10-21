/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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

import org.zmail.common.util.ScheduledTaskCallback;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.ScheduledTaskResult;

import java.util.concurrent.Callable;

/**
 * @author vmahajan
 */
public class CalItemReminderTaskCallback implements ScheduledTaskCallback<ScheduledTaskResult> {

    public void afterTaskRun(Callable<ScheduledTaskResult> task, ScheduledTaskResult lastResult) {
        if (lastResult == null)
            return;
        if (task instanceof CalItemReminderTaskBase) {
            ZmailLog.scheduler.debug("afterTaskRun() for %s", task);
            CalItemReminderService.scheduleNextReminders((CalendarItem) lastResult,
                    task instanceof CalItemEmailReminderTask, task instanceof CalItemSmsReminderTask);
        }
    }
}
