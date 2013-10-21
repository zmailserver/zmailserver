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
package org.zmail.cs.account.callback;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.AutoProvisionThread;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.util.Zmail;

public class AutoProvPollingInterval extends AttributeCallback {

    @Override
    public void preModify(CallbackContext context, String attrName, Object attrValue,
            Map attrsToModify, Entry entry)
    throws ServiceException {
    }
    
    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
        if (!Provisioning.A_zmailAutoProvPollingInterval.equalsIgnoreCase(attrName)) {
            return;
        }
        
        // do not run this callback unless inside the server
        if (!Zmail.started()) {
            return;
        }
        
        try {
            if (entry instanceof Server) {
                // sanity check, this should not happen because ModifyServer is 
                // proxied to the the right server
                if (!((Server) entry).isLocalServer()) {
                    return;
                }
            }
        } catch (ServiceException e) {
            ZmailLog.misc.warn("unable to validate server", e);
            return;
        }
        
        try {
            AutoProvisionThread.switchAutoProvThreadIfNecessary();
        } catch (ServiceException e) {
            ZmailLog.autoprov.error("unable to switch auto provisioning thread", e);
        }
    }

}
