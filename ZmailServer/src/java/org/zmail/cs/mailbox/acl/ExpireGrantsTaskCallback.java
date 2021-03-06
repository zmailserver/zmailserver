/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.cs.mailbox.acl;

import org.zmail.common.util.ScheduledTaskCallback;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.ScheduledTaskResult;

import java.util.concurrent.Callable;

/**
 * After the execution of a {@link ExpireGrantsTask} for a mail item, we need to schedule the next
 * {@link ExpireGrantsTask} for that item.
 */
public class ExpireGrantsTaskCallback implements ScheduledTaskCallback<ScheduledTaskResult> {

    public void afterTaskRun(Callable<ScheduledTaskResult> task, ScheduledTaskResult lastResult) {
        if (lastResult == null) {
            return;
        }
        if (task instanceof ExpireGrantsTask) {
            ZmailLog.scheduler.debug("afterTaskRun() for %s", task);
            ShareExpirationListener.scheduleExpireAccessOpIfReq((MailItem) lastResult);
        }
    }
}
