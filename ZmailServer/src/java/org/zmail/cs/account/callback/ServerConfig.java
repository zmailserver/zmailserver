/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.cs.db.DbPool;
import org.zmail.cs.mailbox.MessageCache;
import org.zmail.cs.store.BlobInputStream;
import org.zmail.cs.store.StoreManager;
import org.zmail.cs.util.JMSession;
import org.zmail.cs.util.Zmail;

/**
 * Central place for updating server attributes that we cache in memory.
 */
public class ServerConfig extends AttributeCallback {

    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
        
        // do not run this callback unless inside the server
        if (!Zmail.started())
            return;
        
        try {
            if (attrName.equals(Provisioning.A_zmailMailUncompressedCacheMaxBytes) ||
                attrName.equals(Provisioning.A_zmailMailUncompressedCacheMaxFiles) ||
                attrName.equals(Provisioning.A_zmailMailFileDescriptorCacheSize)) {
                BlobInputStream.getFileDescriptorCache().loadSettings();
            } else if (attrName.equals(Provisioning.A_zmailMailDiskStreamingThreshold)) {
                StoreManager.loadSettings();
            } else if (attrName.equals(Provisioning.A_zmailMessageCacheSize)) {
                MessageCache.loadSettings();
            } else if (attrName.equals(Provisioning.A_zmailSmtpHostname)) {
                JMSession.resetSmtpHosts();
            } else if (attrName.equals(Provisioning.A_zmailDatabaseSlowSqlThreshold)) {
                DbPool.loadSettings();
            }
        } catch (ServiceException e) {
            ZmailLog.account.warn("Unable to update %s.", attrName, e);
        }
    }

    @Override
    public void preModify(CallbackContext context, String attrName, Object attrValue,
            Map attrsToModify, Entry entry)
    throws ServiceException {
    }

}
