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
package org.zmail.cs.mailbox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.primitives.Ints;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Constants;
import org.zmail.common.util.UUIDUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.offline.OfflineAccount;
import org.zmail.cs.account.offline.OfflineProvisioning;
import org.zmail.cs.db.DbMailItem;
import org.zmail.cs.db.DbMailbox;
import org.zmail.cs.db.DbOfflineMailbox;
import org.zmail.cs.db.DbPool.DbConnection;
import org.zmail.cs.mailbox.MailServiceException.NoSuchItemException;
import org.zmail.cs.mailbox.util.TypedIdList;
import org.zmail.cs.offline.OfflineLog;
import org.zmail.cs.offline.OfflineSyncManager;
import org.zmail.cs.offline.util.OfflineYAuth;
import org.zmail.cs.redolog.op.DeleteItem;
import org.zmail.cs.redolog.op.DeleteMailbox;
import org.zmail.cs.session.PendingModifications;
import org.zmail.cs.session.PendingModifications.Change;
import org.zmail.cs.session.PendingModifications.ModificationKey;
import org.zmail.cs.store.MailboxBlob;
import org.zmail.cs.store.StoreManager;
import org.zmail.cs.store.StoreManager.StoreFeature;
import org.zmail.cs.util.SpoolingCache;
import org.zmail.cs.util.Zmail;
import org.zmail.cs.util.ZmailApplication;

public abstract class SyncMailbox extends DesktopMailbox {
    static final String DELETING_MID_SUFFIX = ":delete";
    static final long OPTIMIZE_INTERVAL = 48 * Constants.MILLIS_PER_HOUR;
    private String accountName;
    private final AtomicBoolean isDeleting = new AtomicBoolean(false);

    private Timer timer;
    private TimerTask currentTask;

    final Object syncLock = new Object();
    private final AtomicBoolean isSyncRunning = new AtomicBoolean(false);
    private final boolean isGalAcct;
    private final boolean isMPAcct;
    private long lastOptimizeTime = 0;
    private static final AtomicLong lastGC = new AtomicLong();
    private final Set<Long> syncedIds = new HashSet<Long>();

    public int getSyncCount() {
        return syncedIds.size();
    }

    public void recordItemSync(long itemId) {
        syncedIds.add(itemId); //using set rather than a counter since various call sites may touch the same item more than once
    }

    public void resetSyncCounter() {
        syncedIds.clear();
    }

    public SyncMailbox(MailboxData data) throws ServiceException {
        super(data);

        OfflineAccount account = (OfflineAccount)getAccount();
        OfflineProvisioning provisioning = OfflineProvisioning.getOfflineInstance();

        if (account.isDataSourceAccount())
            accountName = account.getAttr(OfflineProvisioning.A_offlineDataSourceName);
        else
            accountName = account.getName();
        isGalAcct = provisioning.isGalAccount(account);
        isMPAcct = provisioning.isMountpointAccount(account);
    }

    @Override
    protected void initialize() throws ServiceException {
        lock.lock();
        try {
            super.initialize();

            Folder userRoot = getFolderById(ID_FOLDER_USER_ROOT);

            Folder.create(ID_FOLDER_FAILURE, UUIDUtil.generateUUID(), this, userRoot, FAILURE_PATH,
                Folder.FOLDER_IS_IMMUTABLE, MailItem.Type.MESSAGE, 0,
                MailItem.DEFAULT_COLOR_RGB, null, null, null);
            Folder.create(ID_FOLDER_OUTBOX, UUIDUtil.generateUUID(), this, userRoot, OUTBOX_PATH,
                Folder.FOLDER_IS_IMMUTABLE, MailItem.Type.MESSAGE, 0,
                MailItem.DEFAULT_COLOR_RGB, null, null, null);
        } finally {
            lock.release();
        }
    }

    @Override
    boolean open() throws ServiceException {
        if (super.open()) {
            initSyncTimer();
            return true;
        }
        return false;
    }

    boolean lockMailboxToSync() {
        if (isDeleting() || !OfflineSyncManager.getInstance().isServiceActive(false)) {
            return false;
        }
        return isSyncRunning.compareAndSet(false, true);
    }

    void unlockMailbox() {
        assert isSyncRunning.get() == true;
        isSyncRunning.set(false);
    }

    public boolean isDeleting() {
        return isDeleting.get();
    }

    public String getAccountName() {
        return accountName;
    }

    @Override
    public void deleteMailbox(DeleteBlobs deleteBlobs) throws ServiceException {
        if (!isDeleting.compareAndSet(false, true)) {
            return;
        }
        lock.lock();
        try {
            cancelCurrentTask();
            beginMaintenance(); // mailbox maintenance will cause sync to stop when writing
        } finally {
            lock.release();
        }
        synchronized (syncLock) { // wait for any hot sync thread to unwind
            endMaintenance(true);
        }
        try {
            resetSyncStatus();
        } catch (ServiceException x) {
            if (!x.getCode().equals(AccountServiceException.NO_SUCH_ACCOUNT)) {
                OfflineLog.offline.warn(x);
            }
        }

        MailboxManager mm = MailboxManager.getInstance();

        synchronized (mm) {
            unhookMailboxForDeletion();
            mm.markMailboxDeleted(this); // to remove from cache
        }
        deleteThisMailbox(deleteBlobs);
    }

    private String unhookMailboxForDeletion() throws ServiceException {
        String accountId = getAccountId();
        boolean success = false;

        if (accountId.endsWith(DELETING_MID_SUFFIX)) {
            return accountId;
        }
        accountId = accountId + ":" + getId() + DELETING_MID_SUFFIX;
        try {
            beginTransaction("replaceAccountId", null);
            DbOfflineMailbox.replaceAccountId(this, accountId);
            success = true;
            return accountId;
        } finally {
            endTransaction(success);
        }
    }

    void deleteThisMailbox(DeleteBlobs deleteBlobs) throws ServiceException {
        OfflineLog.offline.info("deleting mailbox %s %s (%s)", getId(), getAccountId(), getAccountName());
        DeleteMailbox redoRecorder = new DeleteMailbox(getId());

        StoreManager sm = StoreManager.getInstance();
        boolean deleteStore = deleteBlobs == DeleteBlobs.ALWAYS || (deleteBlobs == DeleteBlobs.UNLESS_CENTRALIZED && !sm.supports(StoreFeature.CENTRALIZED));
        SpoolingCache<MailboxBlob.MailboxBlobInfo> blobs = null;

        boolean success = false;

        lock.lock();
        try {

            try {
                beginTransaction("deleteMailbox", null, redoRecorder);
                redoRecorder.log();

                if (deleteStore && !sm.supports(StoreManager.StoreFeature.BULK_DELETE)) {
                    blobs = DbMailItem.getAllBlobs(this);
                }

                DbConnection conn = getOperationConnection();

                synchronized (MailboxManager.getInstance()) {
                    DbMailbox.deleteMailbox(conn, this);
                }
                DbMailbox.clearMailboxContent(conn, this);

                success = true;
            } catch (Exception e) {
                ZmailLog.store.warn("Unable to delete mailbox data", e);
            } finally {
                endTransaction(success);
            }

            try {
                index.deleteIndex();
            } catch (Exception e) {
                ZmailLog.store.warn("Unable to delete index data", e);
            }

            if (deleteStore) {
                try {
                    sm.deleteStore(this, blobs);
                } catch (Exception e) {
                    ZmailLog.store.warn("Unable to delete message data", e);
                }
            }
        } finally {
            lock.release();

            if (blobs != null) {
                blobs.cleanup();
            }
        }

        OfflineLog.offline.info("mailbox %s (%s) deleted", getAccountId(), getAccountName());
    }

    void resetSyncStatus() throws ServiceException {
        OfflineSyncManager.getInstance().resetStatus(getAccount());
        ((OfflineAccount)getAccount()).resetLastSyncTimestamp();
        OfflineYAuth.deleteRawAuthManager(this);
    }

    public void cancelCurrentTask() {
        lock.lock();
        try {
            if (currentTask != null) {
                currentTask.cancel();
            }
            currentTask = null;
        } finally {
            lock.release();
        }
    }

    protected void initSyncTimer() throws ServiceException {
        lock.lock();
        try {
            if (isGalAcct || isMPAcct) {
                return;
            }
            cancelCurrentTask();
            currentTask = new TimerTask() {
                @Override
                public void run() {
                    boolean doGC;
                    long now;

                    if (ZmailApplication.getInstance().isShutdown()) {
                        cancelCurrentTask();
                        return;
                    }
                    try {
                        syncOnTimer();
                        now = System.currentTimeMillis();
                        if (lastOptimizeTime == 0) {
                            lastOptimizeTime = now - OPTIMIZE_INTERVAL + 30 * Constants.MILLIS_PER_MINUTE;
                        } else if (now - lastOptimizeTime > OPTIMIZE_INTERVAL) {
                            optimize(0);
                            lastOptimizeTime = now;
                        }
                    } catch (Throwable e) { // don't let exceptions kill the timer
                        if (e instanceof OutOfMemoryError) {
                            Zmail.halt("caught out of memory error", e);
                        } else if (OfflineSyncManager.getInstance().isServiceActive(false)) {
                            OfflineLog.offline.warn("caught exception in timer ", e);
                        }
                    }
                    synchronized (lastGC) {
                        now = System.currentTimeMillis();
                        doGC = now - lastGC.get() > 5 * 60 * Constants.MILLIS_PER_SECOND;
                        if (doGC) {
                            System.gc();
                            lastGC.set(now);
                        }
                    }
                }
            };

        timer = new Timer("sync-mbox-" + getAccount().getName());
        timer.schedule(currentTask, 10 * Constants.MILLIS_PER_SECOND,
            5 * Constants.MILLIS_PER_SECOND);
        } finally {
            lock.release();
        }
    }

    public boolean deleteMsginFolder(long cutoffTime, Folder folder, Set<Folder> visible)
        throws ServiceException {

            if (folder == null)
                return false;

            if (visible != null && visible.isEmpty())
                return false;
            boolean isVisible = visible == null || visible.remove(folder);

            List<Folder> subfolders = folder.getSubfolders(null);
            if (!isVisible && subfolders.isEmpty())
                return false;

            if (isVisible && folder.getType() == MailItem.Type.FOLDER) {
                if (folder.getId() != Mailbox.ID_FOLDER_TAGS) {
                    TypedIdList idlist;
                    boolean success = false;
                    synchronized (this) {
                        try {
                            beginTransaction("listMessageItemsforgivenDate", getOperationContext());
                            idlist = DbMailItem.listItems(folder, cutoffTime, MailItem.Type.MESSAGE, true, true);
                            success = true;
                        } finally {
                            endTransaction(success);
                        }
                    }

                    List<Integer> items = idlist.getAllIds();
                    if (items != null && !items.isEmpty()) {
                        for (int id : items) {
                            MailItem item;
                            success = false;
                            DeleteItem redoRecorder = new DeleteItem(this.getId(), Ints.toArray(items), MailItem.Type.MESSAGE, getOperationTargetConstraint());
                            synchronized (this) {
                                try {
                                    beginTransaction("delete", getOperationContext(), redoRecorder);
                                    try {
                                        item = getItemById(id, MailItem.Type.UNKNOWN);
                                        item.delete(false);
                                    } catch (NoSuchItemException nsie) {
                                        continue;
                                    }
                                    success = true;
                                }finally {
                                    endTransaction(success);
                                }
                            }
                        }
                    }
                }
            }

            if (isVisible && visible != null && visible.isEmpty())
                return true;

            for (Folder subfolder : subfolders) {
                if (subfolder != null)
                    isVisible |= deleteMsginFolder(cutoffTime, subfolder, visible);
            }
            return isVisible;
    }

    protected abstract void syncOnTimer();

    public abstract void sync(boolean isOnRequest, boolean isDebugTraceOn) throws ServiceException;

    public abstract boolean isAutoSyncDisabled();

    @Override
    void snapshotCounts() throws ServiceException {
        // do the normal persisting of folder/tag counts
        super.snapshotCounts();

        boolean outboxed = false;

        PendingModifications pms = getPendingModifications();
        if (pms == null || !pms.hasNotifications())
            return;

        if (pms.created != null) {
            for (MailItem item : pms.created.values()) {
                if ((item.getId() >= FIRST_USER_ID || item instanceof Tag) && item.getFolderId() != ID_FOLDER_FAILURE) {
                    itemCreated(item);
                    trackChangeNew(item);
                    if (item.getFolderId() == ID_FOLDER_OUTBOX) {
                        outboxed = true;
                    }
                }
            }
        }

        if (pms.modified != null) {
            for (Change change : pms.modified.values()) {
                if (!(change.what instanceof MailItem))
                    continue;
                MailItem item = (MailItem) change.what;
                if ((item.getId() >= FIRST_USER_ID || item instanceof Tag) && item.getFolderId() != ID_FOLDER_FAILURE) {
                    trackChangeModified(item, change.why);
                    if (item.getFolderId() == ID_FOLDER_OUTBOX) {
                        outboxed = true;
                    }
                }
            }
        }
        if (pms.deleted != null) {
            for (ModificationKey key : pms.deleted.keySet()) {
                if (key.getItemId() >= FIRST_USER_ID) {
                    trackChangeDeleted();
                }
            }
        }
        if (outboxed) {
            OutboxTracker.invalidate(this);
        }
    }

    void trackChangeNew(MailItem item) throws ServiceException {}

    void trackChangeModified(MailItem item, int changeMask) throws ServiceException {}

    void trackChangeDeleted() throws ServiceException {}

    void itemCreated(MailItem item) throws ServiceException {}
}
