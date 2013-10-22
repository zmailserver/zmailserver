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

import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Pair;
import org.zmail.cs.db.DbOfflineMailbox;
import org.zmail.cs.mailbox.MailServiceException.NoSuchItemException;
import org.zmail.cs.mailbox.util.TypedIdList;
import org.zmail.cs.redolog.op.RedoableOp;
import org.zmail.cs.session.PendingModifications.Change;

public abstract class ChangeTrackingMailbox extends SyncMailbox {

    public static class TracelessContext extends OperationContext {
        public TracelessContext() {
            super((RedoableOp) null);
        }

        public TracelessContext(RedoableOp redo) {
            super(redo);
        }

        @Override
        public boolean isRedo() {
            return false;
        }
    }

    public ChangeTrackingMailbox(MailboxData data) throws ServiceException {
        super(data);
    }

    @Override boolean isTrackingSync() {
        return !(getOperationContext() instanceof TracelessContext);
    }

    @Override public boolean isTrackingImap() {
        return false;
    }

    @Override public boolean checkItemChangeID(int modMetadata, int modContent) {
        return true;
    }

    private long lastChangeTime = -1;

    public boolean anyChangesSince(long since) {
        if (lastChangeTime == -1) {
            //even if no changes since startup there might be some in db
            //do this only first pass; otherwise we try to push on each loop until a local change occurs
            lastChangeTime = 0;
            return true;
        }
        return (lastChangeTime >= since);
    }

    @Override
    void trackChangeNew(MailItem item) throws ServiceException {
        if (!isTrackingSync() || !isPushType(item.getType())) {
            return;
        }
        lastChangeTime = System.currentTimeMillis();
        DbOfflineMailbox.updateChangeRecord(item, Change.CONFLICT);
    }

    @Override
    void trackChangeModified(MailItem item, int changeMask) throws ServiceException {
        if (!isTrackingSync() || !isPushType(item.getType()))
            return;
        lastChangeTime = System.currentTimeMillis();
        int filter = getChangeMaskFilter(item.getType());
        if ((changeMask & filter) != 0)
            DbOfflineMailbox.updateChangeRecord(item, changeMask & filter);
    }

    @Override
    void trackChangeDeleted() throws ServiceException {
        if (!isTrackingSync()) {
            return;
        }
        lastChangeTime = System.currentTimeMillis();
    }

    abstract boolean isPushType(MailItem.Type type);

    abstract int getChangeMaskFilter(MailItem.Type type);

    boolean isPendingDelete(OperationContext octxt, int itemId, MailItem.Type type) throws ServiceException {
        boolean success = false;
        try {
            beginTransaction("isPendingDelete", octxt);

            boolean result = DbOfflineMailbox.isTombstone(this, itemId, type);
            success = true;
            return result;
        } finally {
            endTransaction(success);
        }
    }

    void removePendingDelete(OperationContext octxt, int itemId, MailItem.Type type) throws ServiceException {
        boolean success = false;
        try {
            beginTransaction("removePendingDelete", octxt);

            DbOfflineMailbox.removeTombstone(this, itemId, type);
            success = true;
        } finally {
            endTransaction(success);
        }
    }

    TypedIdList getLocalChanges(OperationContext octxt) throws ServiceException {
        boolean success = false;
        try {
            beginTransaction("getLocalChanges", octxt);

            TypedIdList result = DbOfflineMailbox.getChangedItems(this);
            success = true;
            return result;
        } finally {
            endTransaction(success);
        }
    }

    public Map<Integer, Pair<Integer, Integer>> getChangeMasksAndFolders(OperationContext octxt) throws ServiceException {
        boolean success = false;
        try {
            beginTransaction("getChangeMasksAndFolders", octxt);

            Map<Integer, Pair<Integer, Integer>> result = DbOfflineMailbox.getChangeMasksAndFolders(this);
            success = true;
            return result;
        } finally {
            endTransaction(success);
        }
    }

    Map<Integer, Pair<Integer, Integer>> getChangeMasksAndFlags(OperationContext octxt) throws ServiceException {
        boolean success = false;
        try {
            beginTransaction("getChangeMasksAndFlags", octxt);

            Map<Integer, Pair<Integer, Integer>> result = DbOfflineMailbox.getChangeMasksAndFlags(this);
            success = true;
            return result;
        } finally {
            endTransaction(success);
        }
    }

    List<Pair<Integer, Integer>> getSimpleUnreadChanges(OperationContext octxt, boolean isUnread) throws ServiceException {
        boolean success = false;
        try {
            beginTransaction("getSimpleUnreadChanges", octxt);

            List<Pair<Integer, Integer>> result = DbOfflineMailbox.getSimpleUnreadChanges(this, isUnread);
            success = true;
            return result;
        } finally {
            endTransaction(success);
        }
    }

    Map<Integer, List<Pair<Integer, Integer>>> getFolderMoveChanges(OperationContext octxt) throws ServiceException {
        boolean success = false;
        try {
            beginTransaction("getFolderMoveChanges", octxt);

            Map<Integer, List<Pair<Integer, Integer>>> result = DbOfflineMailbox.getFolderMoveChanges(this);
            success = true;
            return result;
        } finally {
            endTransaction(success);
        }
    }

    Map<Integer, Integer> getItemModSequences(OperationContext octxt, int[] ids) throws ServiceException {
        boolean success = false;
        try {
            beginTransaction("getItemModSequences", octxt);

            Map<Integer, Integer> result = DbOfflineMailbox.getItemModSequences(this, ids);
            success = true;
            return result;
        } finally {
            endTransaction(success);
        }
    }

    Map<Integer, Integer> getItemFolderIds(OperationContext octxt, int[] ids) throws ServiceException {
        boolean success = false;
        try {
            beginTransaction("getItemFolderIds", octxt);

            Map<Integer, Integer> result = DbOfflineMailbox.getItemFolderIds(this, ids);
            success = true;
            return result;
        } finally {
            endTransaction(success);
        }
    }

    public int getChangeMask(OperationContext octxt, int id, MailItem.Type type) throws ServiceException {
        boolean success = false;
        try {
            beginTransaction("getChangeMask", octxt);

            MailItem item = getItemById(id, type);
            int mask = DbOfflineMailbox.getChangeMask(item);
            success = true;
            return mask;
        } catch (NoSuchItemException nsie) {
            return 0;
        } finally {
            endTransaction(success);
        }
    }

    public void setChangeMask(OperationContext octxt, int id, MailItem.Type type, int mask) throws ServiceException {
        //TODO: make this call always non-tracking
        boolean success = false;
        try {
            beginTransaction("setChangeMask", octxt);

            MailItem item = getItemById(id, type);
            DbOfflineMailbox.setChangeMask(item, mask);
            success = true;
        } finally {
            endTransaction(success);
        }
    }

    public void clearTombstones(OperationContext octxt, int token) throws ServiceException {
        boolean success = false;
        try {
            beginTransaction("clearTombstones", octxt);
            DbOfflineMailbox.clearTombstones(this, token);
            success = true;
        } finally {
            endTransaction(success);
        }
    }
}
