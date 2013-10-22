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
package com.zimbra.cs.mailbox;

import com.zimbra.common.account.Key.AccountBy;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.UUIDUtil;
import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.offline.OfflineAccount;
import com.zimbra.cs.db.DbMailbox;
import com.zimbra.cs.mailbox.ChangeTrackingMailbox.TracelessContext;
import com.zimbra.cs.offline.OfflineLC;
import com.zimbra.cs.offline.OfflineLog;
import com.zimbra.cs.redolog.op.CreateFolder;
import com.zimbra.cs.redolog.op.RedoableOp;

public abstract class DesktopMailbox extends Mailbox {

    static DesktopMailbox newMailbox(MailboxData data) throws ServiceException {
        OfflineAccount account = (OfflineAccount)Provisioning.getInstance()
            .get(AccountBy.id, data.accountId);
        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(data.accountId);

        if (account.isLocalAccount())
            return new LocalMailbox(data);

        if (account.isZcsAccount())
            return new ZcsMailbox(data);

        if (account.isExchangeAccount())
            return new ExchangeMailbox(data);

        return new DataSourceMailbox(data);
    }

    public static final String FAILURE_PATH = "Error Reports";
    public static final String NOTIFICATIONS_PATH = "Notification Mountpoints";
    public static final String OUTBOX_PATH = "Outbox";

    public static final int ID_FOLDER_NOTIFICATIONS = 250;
    public static final int ID_FOLDER_FAILURE = 252;
    public static final int ID_FOLDER_OUTBOX = 254;

    private static final String CONFIG_OFFLINE_VERSION = "offline_ver";

    private OfflineMailboxVersion offlineVersion;
    private boolean isOfflineVerCheckComplete;

    public DesktopMailbox(MailboxData data) {
        super(data);
    }

    public OfflineAccount getOfflineAccount() throws ServiceException {
        return (OfflineAccount)getAccount();
    }

    @Override
    protected void initialize() throws ServiceException {
        lock.lock();
        try {
            super.initialize();
            // set the version to CURRENT
            Metadata md = new Metadata();
            offlineVersion = OfflineMailboxVersion.CURRENT();
            offlineVersion.writeToMetadata(md);
            DbMailbox.updateConfig(this, CONFIG_OFFLINE_VERSION, md);
        } finally {
            lock.release();
        }
    }

    @Override
    boolean open() throws ServiceException {
        if (super.open()) {
            ensureSystemFolderExists();
            checkOfflineVersion();
            return true;
        }
        return false;
    }

    void checkOfflineVersion() throws ServiceException {
        lock.lock();
        try {
            if (!isOfflineVerCheckComplete) {
                if (offlineVersion == null) {
                    Metadata md = getConfig(null, CONFIG_OFFLINE_VERSION);
                    offlineVersion = OfflineMailboxVersion.fromMetadata(md);
                }
                if (!offlineVersion.atLeast(3)) {
                    if (!offlineVersion.atLeast(2)) {
                        OfflineMailboxMigration.doMigrationV2(this);
                    }
                    OfflineMailboxMigration.doMigrationV3(this);
                    updateOfflineVersion(OfflineMailboxVersion.CURRENT());
                }
                isOfflineVerCheckComplete = true;
            }
        } finally {
            lock.release();
        }
    }

    private void updateOfflineVersion(OfflineMailboxVersion ver) throws ServiceException {
        lock.lock();
        try {
            offlineVersion = ver;
            Metadata md = getConfig(null, CONFIG_OFFLINE_VERSION);
            if (md == null) {
                md = new Metadata();
            }
            offlineVersion.writeToMetadata(md);
            setConfig(null, CONFIG_OFFLINE_VERSION, md);
        } finally {
            lock.release();
        }
    }

    void ensureSystemFolderExists() throws ServiceException {
        lock.lock();
        try {
            getFolderById(ID_FOLDER_FAILURE);
        } catch (MailServiceException.NoSuchItemException x) {
            Folder.FolderOptions fopt = new Folder.FolderOptions();
            fopt.setAttributes(Folder.FOLDER_IS_IMMUTABLE).setDefaultView(MailItem.Type.MESSAGE);

            CreateFolder redo = new CreateFolder(getId(), FAILURE_PATH, ID_FOLDER_USER_ROOT, fopt);

            redo.setFolderIdAndUuid(ID_FOLDER_FAILURE, UUIDUtil.generateUUID());
            redo.start(System.currentTimeMillis());
            createFolder(new TracelessContext(redo), FAILURE_PATH, ID_FOLDER_USER_ROOT, fopt);
        } finally {
            lock.release();
        }
    }

    @Override
    public boolean dumpsterEnabled() {
        return false;
    }

    @Override
    protected void migrateWikiFolders() throws ServiceException {
        OfflineLog.offline.debug("wiki folder migration skipped");
    }

    @Override
    boolean isChildFolderPermitted(int folderId) {
        return super.isChildFolderPermitted(folderId) && folderId != ID_FOLDER_OUTBOX && folderId != ID_FOLDER_NOTIFICATIONS && folderId != ID_FOLDER_FAILURE;
    }

    @Override
    protected boolean needRedo(OperationContext octxt,  RedoableOp recorder) {
        if (OfflineLC.zdesktop_redolog_enabled.booleanValue()) {
            return super.needRedo(octxt, recorder);
        } else {
            return false;
        }
    }
}
