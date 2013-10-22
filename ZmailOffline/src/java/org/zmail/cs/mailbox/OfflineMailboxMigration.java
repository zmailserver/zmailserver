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
package org.zmail.cs.mailbox;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.offline.OfflineAccount;
import org.zmail.cs.account.offline.OfflineDataSource;
import org.zmail.cs.account.offline.OfflineProvisioning;
import org.zmail.cs.datasource.imap.ImapFolder;
import org.zmail.cs.db.DbDataSource;
import org.zmail.cs.db.DbImapFolder;
import org.zmail.cs.db.DbDataSource.DataSourceItem;
import org.zmail.cs.index.SortBy;

class OfflineMailboxMigration {
    /*
     * In public beta5 build 1418 we had a bug 33763 that pushed caldav created calendar folder to IMAP server.
     * If then the mailbox is reset or is synced to a new install, IMAP code will sync down that folder as a mailbox,
     * causing caldav code to skip sync of any calendar items.
     *
     * Solution:
     * 1. If we see the folder is still a calendar folder, find the IMAP_FOLDER mapping of this folder and change mapped
     *    local ID to a bogus number, which will cause imap code to delete the remote corresponding folder.
     *
     * 2. If we see the folder is a mail folder, do same as 1, but in addition also change its view from message to
     *    appointment.
     */
    static void doMigrationV2(DesktopMailbox mbox) throws ServiceException {
        if (!(mbox instanceof DataSourceMailbox))
            return;

        OfflineAccount account = mbox.getOfflineAccount();
        if (!account.isDataSourceAccount())
            return;

        OfflineDataSource ds = (OfflineDataSource)OfflineProvisioning.getOfflineInstance().getDataSource(account);
        if (!ds.isYahoo() && !ds.isGmail())
            return;

        Collection<DataSourceItem> dsItems = DbDataSource.getAllMappings(ds);
        List<MailItem> folders = mbox.getItemList(null, MailItem.Type.FOLDER, Mailbox.ID_FOLDER_USER_ROOT);
        Set<Integer> folderIds = new HashSet<Integer>(folders.size());
        for (MailItem mi: folders) {
            folderIds.add(mi.getId());
        }
        for (DataSourceItem dsi : dsItems) {
            if (folderIds.contains(dsi.itemId) && dsi.remoteId != null &&
                    (dsi.remoteId.toLowerCase().startsWith("/dav/") ||
                            dsi.remoteId.toLowerCase().startsWith("/calendar/dav/"))) {
                fixFolder(mbox, ds, dsi.itemId);
            }
        }
    }

    private static void fixFolder(Mailbox mbox, DataSource ds, int folderId) throws ServiceException {
        Folder folder = mbox.getFolderById(folderId);
        if (folder.getDefaultView() != MailItem.Type.APPOINTMENT) {
            mbox.setFolderDefaultView(null, folderId, MailItem.Type.APPOINTMENT);
        }
        if ((folder.getFlagBitmask() & Flag.BITMASK_CHECKED) == 0) {
            mbox.alterTag(null, folderId, MailItem.Type.FOLDER, Flag.FlagInfo.CHECKED, true, null);
        }
        if ((folder.getFlagBitmask() & Flag.BITMASK_SYNC) != 0) {
            mbox.alterTag(null, folderId, MailItem.Type.FOLDER, Flag.FlagInfo.SYNC, false, null);
        }
        if ((folder.getFlagBitmask() & Flag.BITMASK_SYNCFOLDER) != 0) {
            mbox.alterTag(null, folderId, MailItem.Type.FOLDER, Flag.FlagInfo.SYNCFOLDER, false, null);
        }
        ImapFolder imapFolder = DbImapFolder.getImapFolders(mbox, ds).getByItemId(folderId);
        if (imapFolder != null) {
            DbImapFolder.deleteImapFolder(mbox, ds, imapFolder);
            DbImapFolder.createImapFolder(mbox, ds, -imapFolder.getItemId(), imapFolder.getLocalPath(),
                    imapFolder.getRemoteId(), imapFolder.getUidValidity());
        }
    }

    static void doMigrationV3(DesktopMailbox mbox) throws ServiceException {
        if (mbox instanceof DataSourceMailbox) {
            OfflineAccount account = mbox.getOfflineAccount();
            if (account.isDataSourceAccount()) {
                OfflineDataSource ds = (OfflineDataSource)
                OfflineProvisioning.getOfflineInstance().getDataSource(account);
                checkMappings(ds, ds.getContactSyncDataSource(), MailItem.Type.CONTACT, "contact");
                checkMappings(ds, ds.getCalendarSyncDataSource(), MailItem.Type.APPOINTMENT, "appointment");
            }
        }
    }

    private static void checkMappings(OfflineDataSource fromDs, OfflineDataSource toDs,
            MailItem.Type type, String typeName) throws ServiceException {
        if (toDs == null) return;
        Mailbox mbox = fromDs.getMailbox();
        ZmailLog.datasource.info("Migrating offline mailbox %s db mappings", typeName);
        for (MailItem item : mbox.getItemList(null, type)) {
            // Move contact mappings to new contact data source
            DataSourceItem dsi = DbDataSource.getMapping(fromDs, item.getId());
            if (dsi.remoteId != null) {
                ZmailLog.datasource.debug(
                    "Moving db mapping for %s item id %d from data source '%s' to '%s'",
                    typeName, item.getId(), fromDs.getName(), toDs.getName());
                DbDataSource.deleteMapping(fromDs, item.getId());
                DbDataSource.addMapping(toDs, dsi);
            }
        }
        for (Folder folder : mbox.getFolderList(null, SortBy.NONE)) {
            if (folder.getDefaultView() == type) {
                DataSourceItem dsi = DbDataSource.getMapping(fromDs, folder.getId());
                if (dsi.remoteId != null) {
                    ZmailLog.datasource.debug(
                        "Moving db mapping for %s folder '%s' from data source '%s' to '%s'",
                        typeName, folder.getName(), fromDs.getName(), toDs.getName());
                    DbDataSource.deleteMapping(fromDs, folder.getId());
                    DbDataSource.addMapping(toDs, dsi);
                }
            }
        }
        ZmailLog.datasource.info("Done migrating offline mailbox %s db mappings", typeName);
    }
}
