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
package org.zmail.cs.datasource.imap;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

final class SyncState {
    private final Mailbox mbox;
    private final Folder inboxFolder;
    private final Map<Integer, FolderSyncState> folders;
    private boolean hasRemoteInboxChanges;
    private int lastChangeId;
    private MessageChanges inboxChanges;

    SyncState(Mailbox mbox) throws ServiceException {
        this.mbox = mbox;
        inboxFolder = mbox.getFolderById(null, Mailbox.ID_FOLDER_INBOX);
        folders = Collections.synchronizedMap(new HashMap<Integer, FolderSyncState>());
    }

    public FolderSyncState getFolderSyncState(int folderId) {
        return folders.get(folderId);
    }

    public FolderSyncState putFolderSyncState(int folderId, FolderSyncState folderSyncState) {
        return folders.put(folderId, folderSyncState);
    }

    public FolderSyncState removeFolderSyncState(int folderId) {
        return folders.remove(folderId);
    }

    public void setHasRemoteInboxChanges(boolean hasChanges) {
        hasRemoteInboxChanges = hasChanges;
    }

    public void resetHasChanges() {
        hasRemoteInboxChanges = false;
        lastChangeId = mbox.getLastChangeID();
    }

    public boolean checkAndResetHasChanges(DataSource ds) throws ServiceException {
        //ZmailLog.datasource.debug("checkAndResetHasChanges: lastChangeId = %d, changeId = %d",
        //    lastChangeId, mbox.getLastChangeID());
        // Always true if there are remote INBOX changes or this is the
        // first time we've checked
        if (hasRemoteInboxChanges || lastChangeId <= 0) {
            ZmailLog.datasource.debug("Forcing sync due to remote INBOX changes");
            hasRemoteInboxChanges = false;
            return true;
        }
        // No changes if last change id hasn't changed
        if (mbox.getLastChangeID() == lastChangeId) {
            return false;
        }
        // Otherwise check if there have been any folder changes or
        // changes to INBOX since the last time we checked
        FolderChanges fc = FolderChanges.getChanges(ds, lastChangeId);
        if (fc.hasChanges()) {
            ZmailLog.datasource.debug("Forcing sync due to local folder changes: %s", fc);
            lastChangeId = fc.getLastChangeId();
            return true;
        }
        inboxChanges = MessageChanges.getChanges(ds, inboxFolder, lastChangeId);
        lastChangeId = Math.min(inboxChanges.getLastChangeId(), fc.getLastChangeId());
        if (inboxChanges.hasChanges()) {
            ZmailLog.datasource.debug("Forcing sync due to local INBOX changes: %s", inboxChanges);
            return true;
        }
        return false;
    }

    public MessageChanges getInboxChanges() {
        return inboxChanges;
    }
}
