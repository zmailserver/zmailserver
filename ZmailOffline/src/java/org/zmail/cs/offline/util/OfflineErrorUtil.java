/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.offline.util;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.mailbox.DesktopMailbox;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.SyncExceptionHandler;
import org.zmail.cs.mailclient.CommandFailedException;

public class OfflineErrorUtil {

    public static void reportError(DesktopMailbox mbox, int itemId, String error, Exception e) {
        String data = "";
        try {
            Message msg = mbox.getMessageById(null, itemId);
            Folder folder = mbox.getFolderById(null, msg.getFolderId());
            data = "Local folder: " + folder.getPath() + "\n";
        } catch (ServiceException ex) {
        }
        if (e instanceof CommandFailedException) {
            String req = ((CommandFailedException) e).getRequest();
            if (req != null) {
                data += "Failed request: " + req;
            }
        }
        try {
            SyncExceptionHandler.saveFailureReport(mbox, itemId, error, data, 0, e);
        } catch (ServiceException x) {
            // Ignore
        }
    }
}
