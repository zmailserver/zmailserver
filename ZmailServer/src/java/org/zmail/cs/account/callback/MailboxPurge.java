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
package com.zimbra.cs.account.callback;

import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.AttributeCallback;
import com.zimbra.cs.account.Entry;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.Server;
import com.zimbra.cs.mailbox.PurgeThread;
import com.zimbra.cs.util.Zimbra;

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
        if (!Provisioning.A_zimbraMailPurgeSleepInterval.equals(attrName)) {
            return;
        }
        
        // do not run this callback unless inside the server
        if (!Zimbra.started())
            return;
        
        Server localServer = null;
        
        try {
            localServer = Provisioning.getInstance().getLocalServer();
        } catch (ServiceException e) {
            ZimbraLog.misc.warn("unable to get local server");
            return;
        }
        
        boolean hasMailboxService = localServer.getMultiAttrSet(Provisioning.A_zimbraServiceEnabled).contains("mailbox");
        
        if (!hasMailboxService)
            return;
        
        if (entry instanceof Server) {
            Server server = (Server)entry;
            // sanity check, this should not happen because modifyServer is proxied to the the right server
            if (server.getId() != localServer.getId())
                return;
        }

        ZimbraLog.purge.info("Mailbox purge interval set to %s.",
            localServer.getAttr(Provisioning.A_zimbraMailPurgeSleepInterval, null));
        long interval = localServer.getTimeInterval(Provisioning.A_zimbraMailPurgeSleepInterval, 0);
        if (interval > 0 && !PurgeThread.isRunning()) {
            PurgeThread.startup();
        }
        if (interval == 0 && PurgeThread.isRunning()) {
            PurgeThread.shutdown();
        }
    }
}
