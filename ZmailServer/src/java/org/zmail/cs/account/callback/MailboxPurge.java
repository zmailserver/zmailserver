/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.mailbox.PurgeThread;
import org.zmail.cs.util.Zmail;

/**
 * Starts the mailbox purge thread if it is not running and the purge sleep
 * interval is set to a non-zero value.  
 */
public class MailboxPurge extends AttributeCallback {

    @Override
    public void preModify(CallbackContext context, String attrName, Object attrValue, 
            Map attrsToModify, Entry entry) {
    }

    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
        if (!Provisioning.A_zmailMailPurgeSleepInterval.equals(attrName)) {
            return;
        }
        
        // do not run this callback unless inside the server
        if (!Zmail.started())
            return;
        
        Server localServer = null;
        
        try {
            localServer = Provisioning.getInstance().getLocalServer();
        } catch (ServiceException e) {
            ZmailLog.misc.warn("unable to get local server");
            return;
        }
        
        boolean hasMailboxService = localServer.getMultiAttrSet(Provisioning.A_zmailServiceEnabled).contains("mailbox");
        
        if (!hasMailboxService)
            return;
        
        if (entry instanceof Server) {
            Server server = (Server)entry;
            // sanity check, this should not happen because modifyServer is proxied to the the right server
            if (server.getId() != localServer.getId())
                return;
        }

        ZmailLog.purge.info("Mailbox purge interval set to %s.",
            localServer.getAttr(Provisioning.A_zmailMailPurgeSleepInterval, null));
        long interval = localServer.getTimeInterval(Provisioning.A_zmailMailPurgeSleepInterval, 0);
        if (interval > 0 && !PurgeThread.isRunning()) {
            PurgeThread.startup();
        }
        if (interval == 0 && PurgeThread.isRunning()) {
            PurgeThread.shutdown();
        }
    }
}
