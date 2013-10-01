/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.zimbra.common.mailbox.Color;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.common.soap.SoapFaultException;
import com.zimbra.common.util.Pair;
import com.zimbra.common.util.StringUtil;
import com.zimbra.cs.mailbox.ChangeTrackingMailbox.TracelessContext;
import com.zimbra.cs.mailbox.util.TagUtil;
import com.zimbra.cs.mime.ParsedContact;
import com.zimbra.cs.offline.Offline;
import com.zimbra.cs.offline.OfflineLC;
import com.zimbra.cs.offline.OfflineLog;
import com.zimbra.cs.offline.OfflineSyncManager;
import com.zimbra.cs.service.UserServlet;
import com.zimbra.cs.service.mail.Sync;
import com.zimbra.cs.service.offline.OfflineDialogAction;
import com.zimbra.cs.session.PendingModifications.Change;

public class DeltaSync {

    private static final TracelessContext sContext = new TracelessContext();

    private final ZcsMailbox ombx;
    private final MailboxSync mMailboxSync;
    private final Set<Integer> mSyncRenames = new HashSet<Integer>();
    private InitialSync isync;
    private static final Set<Integer> LAST_SYNC_FOLDERS = new HashSet<Integer>();

    static {
        LAST_SYNC_FOLDERS.add(Mailbox.ID_FOLDER_TRASH);
        LAST_SYNC_FOLDERS.add(Mailbox.ID_FOLDER_SPAM);
    }

    DeltaSync(ZcsMailbox mbox) {
        ombx = mbox;
        mMailboxSync = ombx.getMailboxSync();
    }

    DeltaSync(InitialSync initial) {
        ombx = initial.getMailbox();
        mMailboxSync = ombx.getMailboxSync();
        isync = initial;
    }

    ZcsMailbox getMailbox() {
        return ombx;
    }

    private InitialSync getInitialSync() {
        if (isync == null)
            isync = new InitialSync(this);
        return isync;
    }

    private TagSync getTagSync() {
        return ombx.getTagSync();
    }

    public static void sync(ZcsMailbox ombx) throws ServiceException {
        new DeltaSync(ombx).sync();
    }

    void sync() throws ServiceException {
        String oldToken = mMailboxSync.getSyncToken();
        assert (oldToken != null);

        Element response = null;
        String newToken;
        // keep delta sync'ing until the server tells us the delta sync is complete ("more=0")
        do {
            Element request = new Element.XMLElement(MailConstants.SYNC_REQUEST).addAttribute(MailConstants.A_TOKEN,
                    oldToken).addAttribute(MailConstants.A_TYPED_DELETES, true);
            try {
                response = ombx.sendRequestWithNotification(request);
            } catch (SoapFaultException e) {
                if (MailServiceException.MUST_RESYNC.equals(e.getCode())) {
                    OfflineLog.offline.warn("sync token is too old, must resync");
                    this.ombx.cancelCurrentTask();
                    OfflineSyncManager.getInstance().registerDialog(this.ombx.getAccountId(),
                            new Pair<String, String>(OfflineDialogAction.DIALOG_TYPE_RESYNC,
                                    OfflineDialogAction.DIALOG_RESYNC_MSG));
                    throw OfflineServiceException.MUST_RESYNC();
                } else {
                    throw e;
                }
            }

            newToken = response.getAttribute(MailConstants.A_TOKEN);

            if (StringUtil.equal(newToken, oldToken) && !response.getAttributeBool(MailConstants.A_QUERY_MORE, false)) {
                OfflineLog.offline.debug("skipping delta sync [token " + oldToken + "] unchanged");
            } else {
                OfflineLog.offline.debug("starting delta sync [token " + oldToken + ']');
                deltaSync(response);
                SyncExceptionHandler.checkIOExceptionRate(ombx);
                // update the stored sync progress and loop again with new token if sync was incomplete
                mMailboxSync.recordSyncComplete(newToken);
                oldToken = newToken;
                OfflineLog.offline.debug("ending delta sync [token " + newToken + ']');
            }
        } while (response.getAttributeBool(MailConstants.A_QUERY_MORE, false));
    }

    private void deltaSync(Element response) throws ServiceException {
        boolean isInitSyncDone = mMailboxSync.isInitialSyncComplete();

        // make sure to handle deletes first, as tags can reuse ids
        Set<Integer> foldersToDelete = null;
        Element delement = response.getOptionalElement(MailConstants.E_DELETED);
        if (delement != null) {
            delement.detach();
            if (isInitSyncDone)
                foldersToDelete = processLeafDeletes(delement);
        }

        // sync down metadata changes and note items that need to be downloaded in full
        Map<Integer, List<Integer>> messages = new HashMap<Integer, List<Integer>>();
        Map<Integer, List<Integer>> modmsgs = new HashMap<Integer, List<Integer>>();
        Map<Integer, List<Integer>> chats = new HashMap<Integer, List<Integer>>();
        Map<Integer, List<Integer>> modchats = new HashMap<Integer, List<Integer>>();
        Map<Integer, Integer> deltamsgs = new HashMap<Integer, Integer>();
        Map<Integer, Integer> deltachats = new HashMap<Integer, Integer>();
        Multimap<Integer, String> contacts = ArrayListMultimap.create(); //<folderId, contactId>
        Map<Integer, Integer> appts = new HashMap<Integer, Integer>();
        Map<Integer, Integer> tasks = new HashMap<Integer, Integer>();
        List<Integer> documents = new ArrayList<Integer>();
        List<Element> lastToSyncItems = new ArrayList<Element>();

        for (Element change : response.listElements()) {
            OfflineSyncManager.getInstance().continueOK();
            int id = (int) change.getAttributeLong(MailConstants.A_ID);
            String type = change.getName();

            if (type.equals(MailConstants.E_TAG)) {
                syncTag(change);
                continue;
            }

            int folderId = (id == Mailbox.ID_FOLDER_ROOT ? Mailbox.ID_FOLDER_ROOT : (int) change
                    .getAttributeLong(MailConstants.A_FOLDER));
            if (!isInitSyncDone && getFolder(folderId) == null) {
                continue;
            }

            // for bug 32238, sync trash folder last
            if (LAST_SYNC_FOLDERS.contains(folderId)) {
                lastToSyncItems.add(change);
                continue;
            }

            prepareSync(messages, modmsgs, chats, modchats, deltamsgs, deltachats, contacts, appts, tasks, documents,
                    change, folderId);
        }

        syncItems(isInitSyncDone, foldersToDelete, messages, modmsgs, chats, modchats, deltamsgs, deltachats, contacts,
                appts, tasks, documents);

        messages.clear();
        modmsgs.clear();
        chats.clear();
        modchats.clear();
        deltamsgs.clear();
        deltachats.clear();
        contacts.clear();
        appts.clear();
        tasks.clear();
        documents.clear();

        for (Element change : lastToSyncItems) {
            OfflineSyncManager.getInstance().continueOK();
            int id = (int) change.getAttributeLong(MailConstants.A_ID);
            int folderId = (id == Mailbox.ID_FOLDER_ROOT ? Mailbox.ID_FOLDER_ROOT
                    : (int) change.getAttributeLong(MailConstants.A_FOLDER));
            prepareSync(messages, modmsgs, chats, modchats, deltamsgs, deltachats, contacts, appts, tasks, documents,
                    change, folderId);
        }

        syncItems(isInitSyncDone, foldersToDelete, messages, modmsgs, chats, modchats, deltamsgs, deltachats, contacts,
                appts, tasks, documents);
    }

    private void syncItems(boolean isInitSyncDone, Set<Integer> foldersToDelete, Map<Integer, List<Integer>> messages,
            Map<Integer, List<Integer>> modmsgs, Map<Integer, List<Integer>> chats,
            Map<Integer, List<Integer>> modchats, Map<Integer, Integer> deltamsgs, Map<Integer, Integer> deltachats,
            Multimap<Integer, String> contacts, Map<Integer, Integer> appts, Map<Integer, Integer> tasks,
            List<Integer> documents) throws ServiceException {
        // for messages, chats, and contacts that are created or had their content modified, fetch new content
        if (OfflineLC.zdesktop_sync_messages.booleanValue() && !deltamsgs.isEmpty()) {
            Element request = new Element.XMLElement(MailConstants.GET_MSG_METADATA_REQUEST);
            request.addElement(MailConstants.E_MSG).addAttribute(MailConstants.A_IDS,
                    StringUtil.join(",", deltamsgs.keySet()));
            for (Element elt : ombx.sendRequest(request).listElements()) {
                syncMessage(elt, deltamsgs.get((int) elt.getAttributeLong(MailConstants.A_ID)), MailItem.Type.MESSAGE);
            }
        }

        // sync appointments before messages so that new invite messages can be linked to appointments
        if (OfflineLC.zdesktop_sync_appointments.booleanValue() && !appts.isEmpty()) {
            for (Map.Entry<Integer, Integer> entry : appts.entrySet())
                getInitialSync().syncCalendarItem(entry.getKey(), entry.getValue(), true);
        }

        if (OfflineLC.zdesktop_sync_tasks.booleanValue() && !tasks.isEmpty()) {
            for (Map.Entry<Integer, Integer> entry : tasks.entrySet())
                getInitialSync().syncCalendarItem(entry.getKey(), entry.getValue(), false);
        }

        if (OfflineLC.zdesktop_sync_messages.booleanValue()) {
            for (int folderId : messages.keySet()) {
                getInitialSync().syncMessagelikeItems(messages.get(folderId), folderId, MailItem.Type.MESSAGE, false, true);
            }
            for (int folderId: modmsgs.keySet()) {
                getInitialSync().syncMessagelikeItems(modmsgs.get(folderId), folderId, MailItem.Type.MESSAGE, true, true);
            }
        }

        if (OfflineLC.zdesktop_sync_chats.booleanValue() && !deltachats.isEmpty()) {
            Element request = new Element.XMLElement(MailConstants.GET_MSG_METADATA_REQUEST);
            request.addElement(MailConstants.E_MSG).addAttribute(MailConstants.A_IDS,
                    StringUtil.join(",", deltachats.keySet()));
            for (Element elt : ombx.sendRequest(request).listElements()) {
                syncMessage(elt, deltachats.get((int) elt.getAttributeLong(MailConstants.A_ID)), MailItem.Type.CHAT);
            }
        }

        if (OfflineLC.zdesktop_sync_chats.booleanValue()) {
            for (int folderId : chats.keySet()) {
                getInitialSync().syncMessagelikeItems(chats.get(folderId), folderId, MailItem.Type.CHAT, false, true);
            }
            for (int folderId : modchats.keySet()) {
                getInitialSync().syncMessagelikeItems(modchats.get(folderId), folderId, MailItem.Type.CHAT, true, true);
            }
        }

        if (OfflineLC.zdesktop_sync_contacts.booleanValue() && !contacts.isEmpty()) {
            for (Integer folderId: contacts.keySet()) {
                getInitialSync().syncContacts(contacts.get(folderId), folderId);
            }
        }

        if (isInitSyncDone && OfflineLC.zdesktop_sync_documents.booleanValue() && !documents.isEmpty())
            try {
                syncDocuments(documents);
            } catch (Exception t) {
                OfflineLog.offline.error("delta: error syncing Documents.", t);
            }

        // delete any deleted folders, starting from the bottom of the tree
        if (isInitSyncDone && foldersToDelete != null && !foldersToDelete.isEmpty()) {
            ombx.lock.lock();
            try {
                List<Folder> folders = ombx.getFolderById(sContext, Mailbox.ID_FOLDER_ROOT).getSubfolderHierarchy();
                Collections.reverse(folders);
                for (Folder folder : folders) {
                    if (foldersToDelete.remove(folder.getId()))
                        processFolderDelete(folder);
                }
            } finally {
                ombx.lock.release();
            }
        }
    }

    private void prepareSync(Map<Integer, List<Integer>> messages, Map<Integer, List<Integer>> modmsgs,
            Map<Integer, List<Integer>> chats, Map<Integer, List<Integer>> modchats, Map<Integer, Integer> deltamsgs,
            Map<Integer, Integer> deltachats, Multimap<Integer, String> contacts, Map<Integer, Integer> appts,
            Map<Integer, Integer> tasks, List<Integer> documents, Element change, int folderId) throws ServiceException {
        int id = (int) change.getAttributeLong(MailConstants.A_ID);
        String uuid = change.getAttribute(MailConstants.A_UUID);
        String type = change.getName();
        boolean create = (change.getAttribute(MailConstants.A_FLAGS, null) == null);
        if (type.equals(MailConstants.E_MSG)) {
            if (!OfflineLC.zdesktop_sync_messages.booleanValue())
                return;
            if (OfflineSyncManager.getInstance().isInSkipList(id)) {
                OfflineLog.offline.warn("Skipped message id=%d per zdesktop_sync_skip_idlist", id);
                return;
            }

            if (ombx.isPendingDelete(sContext, id, MailItem.Type.MESSAGE))
                return;

            if (create) {
                triageNewMessage(id, folderId, change, messages, modmsgs, deltamsgs);
            } else {
                syncMessage(change, folderId, MailItem.Type.MESSAGE);
            }
        } else if (type.equals(MailConstants.E_CHAT)) {
            if (!OfflineLC.zdesktop_sync_chats.booleanValue())
                return;
            if (OfflineSyncManager.getInstance().isInSkipList(id)) {
                OfflineLog.offline.warn("Skipped chat id=%d per zdesktop_sync_skip_idlist", id);
                return;
            }

            if (ombx.isPendingDelete(sContext, id, MailItem.Type.CHAT))
                return;

            if (create) {
                triageNewMessage(id, folderId, change, chats, modchats, deltachats);
            } else {
                syncMessage(change, folderId, MailItem.Type.CHAT);
            }
        } else if (type.equals(MailConstants.E_CONTACT)) {
            if (!OfflineLC.zdesktop_sync_contacts.booleanValue())
                return;
            if (OfflineSyncManager.getInstance().isInSkipList(id)) {
                OfflineLog.offline.warn("Skipped contact id=%d per zdesktop_sync_skip_idlist", id);
                return;
            }

            if (ombx.isPendingDelete(sContext, id, MailItem.Type.CONTACT))
                return;

            if (create) {
                contacts.put(folderId, id+"");
            } else {
                syncContact(change, folderId);
            }
        } else if (type.equals(MailConstants.E_APPOINTMENT)) {
            if (!OfflineLC.zdesktop_sync_appointments.booleanValue())
                return;
            if (OfflineSyncManager.getInstance().isInSkipList(id)) {
                OfflineLog.offline.warn("Skipped appointment id=%d per zdesktop_sync_skip_idlist", id);
                return;
            }

            if (ombx.isPendingDelete(sContext, id, MailItem.Type.APPOINTMENT))
                return;

            appts.put(id, folderId);
        } else if (type.equals(MailConstants.E_TASK)) {
            if (!OfflineLC.zdesktop_sync_tasks.booleanValue())
                return;
            if (OfflineSyncManager.getInstance().isInSkipList(id)) {
                OfflineLog.offline.warn("Skipped task id=%d per zdesktop_sync_skip_idlist", id);
                return;
            }

            if (ombx.isPendingDelete(sContext, id, MailItem.Type.TASK))
                return;

            tasks.put(id, folderId);
        } else if (type.equals(MailConstants.E_DOC)) {
            if (!OfflineLC.zdesktop_sync_documents.booleanValue()
                    || !ombx.getRemoteServerVersion().isAtLeast(InitialSync.sMinDocumentSyncVersion))
                return;
            if (OfflineSyncManager.getInstance().isInSkipList(id)) {
                OfflineLog.offline.warn("Skipped document id=%d per zdesktop_sync_skip_idlist", id);
                return;
            }

            if (ombx.isPendingDelete(sContext, id, MailItem.Type.UNKNOWN))
                return;

            documents.add(id);
        } else if (type.equals(MailConstants.E_WIKIWORD)) {
            return;
        } else if (InitialSync.KNOWN_FOLDER_TYPES.contains(type)) {
            // can't tell new folders from modified ones, so might as well go through the initial sync process
            syncContainer(change, id, uuid);
        }
    }

    private void triageNewMessage(int id, int folderId, Element change, Map<Integer, List<Integer>> messages, Map<Integer, List<Integer>> modmsgs,
            Map<Integer, Integer> deltamsgs) throws ServiceException {
        try {
            Message msg = ombx.getMessageById(sContext, id);
            if (msg.getSavedSequence() != change.getAttributeLong(MailConstants.A_REVISION, -1))
                addToMapByFolderId(id, folderId, modmsgs);
            else if (msg.getModifiedSequence() != change.getAttributeLong(MailConstants.A_MODIFIED_SEQUENCE))
                deltamsgs.put(id, folderId);
            else
                // could be because we are rerunning a failed sync
                OfflineLog.offline.debug("message %d (%s) already in sync", id, msg.getSubject());
        } catch (MailServiceException.NoSuchItemException e) {
            addToMapByFolderId(id, folderId, messages);
        }
    }

    private void addToMapByFolderId(int id, int folderId, Map<Integer, List<Integer>> messages) {
        List<Integer> idList = messages.get(folderId);
        if (idList == null) {
            idList = new ArrayList<Integer>();
            messages.put(folderId, idList);
        }
        idList.add(id);
    }

    private Set<Integer> processLeafDeletes(Element delement) throws ServiceException {
        // sort the deleted items into a bucket of leaf nodes to delete now and a set of folders to delete later
        List<Integer> leafIds = new ArrayList<Integer>(), tagIds = new ArrayList<Integer>();
        Set<Integer> foldersToDelete = new HashSet<Integer>();

        // sort the deleted items into two sets: leaves and folders
        for (Element deltype : delement.listElements()) {
            MailItem.Type type = Sync.typeForElementName(deltype.getName());
            if (type == MailItem.Type.UNKNOWN || type == MailItem.Type.CONVERSATION) {
                continue;
            }
            boolean isTag = type == MailItem.Type.TAG;
            boolean isFolder = InitialSync.KNOWN_FOLDER_TYPES.contains(deltype.getName());
            for (String idStr : deltype.getAttribute(MailConstants.A_IDS).split(",")) {
                Integer id = Integer.valueOf(idStr);
                if (isTag) {
                    id = getTagSync().localTagId(id);
                    if (!TagSync.validateId(id)) {
                        //if it's an overflow still need to delete the mapping
                        getTagSync().removeTagMapping(id);
                        continue;
                    }
                }
                Integer mask = ombx.getChangeMask(sContext, id, type);
                // tag numbering conflict issues: don't delete tags we've created locally
                if (isTag && (mask & Change.CONFLICT) != 0) {
                    continue;
                }
                (isFolder ? foldersToDelete : leafIds).add(id);
                if (isTag) {
                    tagIds.add(id);
                }
            }
        }

        // delete all the leaves now
        int idx = 0, ids[] = new int[leafIds.size()];
        for (int leafId : leafIds) {
            ids[idx++] = leafId;
        }
        ombx.delete(sContext, ids, MailItem.Type.UNKNOWN, null);
        OfflineLog.offline.debug("delta: deleted leaves: " + Arrays.toString(ids));

        // avoid some nasty corner cases caused by tag id reuse
        for (int tagId : tagIds) {
            ombx.removePendingDelete(sContext, tagId, MailItem.Type.TAG);
            getTagSync().removeTagMapping(tagId);
        }
        // save the folder deletes for later
        return (foldersToDelete.isEmpty() ? null : foldersToDelete);
    }

    private void processFolderDelete(Folder folder) throws ServiceException {
        if (!folder.isDeletable()) {
            OfflineLog.offline.warn("delta: cannot delete " + folder.getType() + " (" + folder.getId() + "): " + folder.getName());
            return;
        }

        if (folder.getItemCount() == 0 && !folder.hasSubfolders()) {
            // normal case: contents have already been deleted via processLeafDeletes()
            ombx.delete(sContext, folder.getId(), folder.getType());
            OfflineLog.offline.debug("delta: deleted folder: " + folder.getId());
            return;
        }

        // mark the remote folder for re-creation in order to hold its local contents
        ombx.setChangeMask(sContext, folder.getId(), folder.getType(), Change.CONFLICT);
        // mark all the contents as moved so that they appear in the remote folder
        for (int contained : ombx.listItemIds(sContext, MailItem.Type.UNKNOWN, folder.getId())) {
            int change_mask = ombx.getChangeMask(sContext, contained, MailItem.Type.UNKNOWN);
            if ((change_mask & (Change.FOLDER | Change.CONFLICT)) == 0) {
                ombx.setChangeMask(sContext, contained, MailItem.Type.UNKNOWN, change_mask | Change.FOLDER);
            }
        }
        OfflineLog.offline.debug("delta: queued folder for recreate: " + folder.getId());
    }

    private void syncContainer(Element elt, int id, String uuid) throws ServiceException {
        String type = elt.getName();
        if (type.equalsIgnoreCase(MailConstants.E_SEARCH))
            syncSearchFolder(elt, id, uuid);
        else if (type.equalsIgnoreCase(MailConstants.E_FOLDER) || type.equalsIgnoreCase(MailConstants.E_MOUNT))
            syncFolder(elt, id, uuid, type);
    }

    void syncSearchFolder(Element elt, int id, String uuid) throws ServiceException {
        String rgb = elt.getAttribute(MailConstants.A_RGB, null);
        byte color = (byte) elt.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR);
        Color itemColor = rgb != null ? new Color(rgb) : new Color(color);
        int flags = Flag.toBitmask(elt.getAttribute(MailConstants.A_FLAGS, null));

        int date = (int) (elt.getAttributeLong(MailConstants.A_DATE, -1000) / 1000);

        String query = elt.getAttribute(MailConstants.A_QUERY);
        String searchTypes = elt.getAttribute(MailConstants.A_SEARCH_TYPES);
        String sort = elt.getAttribute(MailConstants.A_SORTBY);

        ombx.lock.lock();
        try {
            // deal with the case where the referenced search folder doesn't exist
            Folder folder = getFolder(id);
            if (folder == null) {
                // if it's been locally deleted but not pushed to the server yet, just return and let the delete happen later
                if (ombx.isPendingDelete(sContext, id, MailItem.Type.SEARCHFOLDER)) {
                    return;
                }
                // resolve any naming conflicts and actually create the folder
                if (resolveFolderConflicts(elt, id, MailItem.Type.SEARCHFOLDER, folder)) {
                    getInitialSync().syncSearchFolder(elt, id, uuid);
                    return;
                } else {
                    folder = getFolder(id);
                }
            }

            // if the search folder was moved/renamed locally, that trumps any changes made remotely
            resolveFolderConflicts(elt, id, MailItem.Type.SEARCHFOLDER, folder);

            int parentId = (int) elt.getAttributeLong(MailConstants.A_FOLDER);
            String name = elt.getAttribute(MailConstants.A_NAME);

            int change_mask = ombx.getChangeMask(sContext, id, MailItem.Type.SEARCHFOLDER);
            ombx.rename(sContext, id, MailItem.Type.SEARCHFOLDER, name, parentId);
            if ((change_mask & Change.QUERY) == 0) {
                ombx.modifySearchFolder(sContext, id, query, searchTypes, sort);
            }
            ombx.syncMetadata(sContext, id, MailItem.Type.SEARCHFOLDER, parentId, flags, null, itemColor);
            ombx.syncDate(sContext, id, MailItem.Type.SEARCHFOLDER, date);

            if (elt.getAttributeBool(InitialSync.A_RELOCATED, false)) {
                ombx.setChangeMask(sContext, id, MailItem.Type.SEARCHFOLDER, change_mask | Change.FOLDER | Change.NAME);
            }
            OfflineLog.offline.debug("delta: updated search folder (" + id + "): " + name);
        } finally {
            ombx.lock.release();
        }
    }

    void syncFolder(Element elt, int id, String uuid, String type) throws ServiceException {
        int flags = Flag.toBitmask(elt.getAttribute(MailConstants.A_FLAGS, null)) & ~Flag.BITMASK_UNREAD;
        String rgb = elt.getAttribute(MailConstants.A_RGB, null);
        byte color = (byte) elt.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR);
        Color itemColor = rgb != null ? new Color(rgb) : new Color(color);
        String url = elt.getAttribute(MailConstants.A_URL, null);
        MailItem.Type itemType = type.equals(MailConstants.E_FOLDER) ? MailItem.Type.FOLDER : MailItem.Type.MOUNTPOINT;

        ACL acl = getInitialSync().parseACL(elt.getOptionalElement(MailConstants.E_ACL));

        int date = (int) (elt.getAttributeLong(MailConstants.A_DATE, -1000) / 1000);

        ombx.lock.lock();
        try {
            // deal with the case where the referenced folder doesn't exist
            Folder folder = getFolder(id);
            if (folder == null) {
                // if it's been locally deleted but not pushed to the server
                // yet, just return and let the delete happen later
                if (ombx.isPendingDelete(sContext, id, itemType))
                    return;
                // resolve any naming conflicts and actually create the folder
                if (resolveFolderConflicts(elt, id, itemType, folder)) {
                    getInitialSync().syncFolder(elt, id, uuid, type);
                    return;
                } else {
                    folder = getFolder(id);
                }
            }

            // if the folder was moved/renamed locally, that trumps any changes made remotely
            resolveFolderConflicts(elt, id, itemType, folder);

            int parentId = (id == Mailbox.ID_FOLDER_ROOT) ? id : (int) elt.getAttributeLong(MailConstants.A_FOLDER);
            String name = (id == Mailbox.ID_FOLDER_ROOT) ? "ROOT" : elt.getAttribute(MailConstants.A_NAME);

            int change_mask = ombx.getChangeMask(sContext, id, MailItem.Type.FOLDER);
            if (id != Mailbox.ID_FOLDER_ROOT) {
                ombx.rename(sContext, id, itemType, name, parentId);
            }
            // XXX: do we need to sync if the folder has perms but the new ACL is empty?
            if ((change_mask & Change.ACL) == 0 && acl != null) {
                ombx.setPermissions(sContext, id, acl);
            }
            if ((change_mask & Change.URL) == 0) {
                ombx.setFolderUrl(sContext, id, url);
            }
            if (itemType == MailItem.Type.FOLDER) {
                // don't care about current feed syncpoint; sync can't be done offline
                ombx.syncMetadata(sContext, id, MailItem.Type.FOLDER, parentId, flags, null, itemColor);
                ombx.syncDate(sContext, id, MailItem.Type.FOLDER, date);
            }

            if (elt.getAttributeBool(InitialSync.A_RELOCATED, false)) {
                ombx.setChangeMask(sContext, id, itemType, change_mask | Change.FOLDER | Change.NAME);
            }
            OfflineLog.offline.debug("delta: updated folder (" + id + "): " + name);
        } finally {
            ombx.lock.release();
        }
    }

    private Folder getFolder(int id) throws ServiceException {
        try {
            return ombx.getFolderById(sContext, id);
        } catch (MailServiceException.NoSuchItemException nsie) {
            return null;
        }
    }

    private boolean resolveFolderConflicts(Element elt, int id, MailItem.Type type, Folder local)
    throws ServiceException {
        int change_mask = (local == null ? 0 : ombx.getChangeMask(sContext, id, type));

        // if the folder was moved/renamed locally, that trumps any changes made remotely
        int parentId = (id == Mailbox.ID_FOLDER_ROOT) ? id : (int) elt.getAttributeLong(MailConstants.A_FOLDER);
        if ((change_mask & Change.FOLDER) != 0) {
            parentId = local.getFolderId();
            elt.addAttribute(MailConstants.A_FOLDER, parentId);
        }

        String name = (id == Mailbox.ID_FOLDER_ROOT) ? "ROOT" : elt.getAttribute(MailConstants.A_NAME);
        if ((change_mask & Change.NAME) != 0 && !mSyncRenames.contains(id)) {
            name = local.getName();
            elt.addAttribute(MailConstants.A_NAME, name);
        } else {
            // handle the off-chance that the server's folder name is invalid
            String validName = MailItem.normalizeItemName(name);
            if (!validName.equals(name)) {
                name = validName;
                elt.addAttribute(MailConstants.A_NAME, name).addAttribute(InitialSync.A_RELOCATED, true);
            }
        }

        // if the parent folder doesn't exist or is of an incompatible type, default to using the top-level user folder as the container
        Folder parent = getFolder(parentId);
        if (parent == null || !parent.canContain(type)) {
            parentId = Mailbox.ID_FOLDER_USER_ROOT;
            parent = getFolder(parentId);
            elt.addAttribute(MailConstants.A_FOLDER, parentId).addAttribute(InitialSync.A_RELOCATED, true);
        }

        Folder conflict = parent.findSubfolder(name);
        if (conflict != null) {
            if (conflict.getId() != id) {
                int conflict_mask = ombx.getChangeMask(sContext, conflict.getId(), conflict.getType());

                String uuid = '{' + UUID.randomUUID().toString() + '}', newName;
                if (name.length() + uuid.length() > MailItem.MAX_NAME_LENGTH)
                    newName = name.substring(0, MailItem.MAX_NAME_LENGTH - uuid.length()) + uuid;
                else
                    newName = name + uuid;

                if (local == null && (conflict_mask & Change.CONFLICT) != 0 &&
                        isCompatibleFolder(conflict, elt, type)) {
                    // if the new and existing folders are identical and being created, try to merge them
                    ombx.renumberItem(sContext, conflict.getId(), type, id);
                    ombx.setChangeMask(sContext, id, type, conflict_mask & ~Change.CONFLICT);
                    return false;
                } else if (!conflict.isMutable() || (conflict_mask & Change.NAME) != 0) {
                    // either the local user also renamed the folder or the folder's immutable, so the local client wins
                    name = newName;
                    elt.addAttribute(MailConstants.A_NAME, name).addAttribute(InitialSync.A_RELOCATED, true);
                } else {
                    // if there's a folder naming conflict within the target folder, usually push the local folder out of the way
                    ombx.rename(null, conflict.getId(), conflict.getType(), newName);
                    if ((conflict_mask & Change.NAME) == 0) {
                        mSyncRenames.add(conflict.getId());
                    }
                }
            } else {
                String viewStr = elt.getAttribute(MailConstants.A_DEFAULT_VIEW, null);
                if (viewStr != null) {
                    MailItem.Type defaultView = MailItem.Type.of(viewStr);
                    if (conflict.getDefaultView() != defaultView) {
                        ombx.syncFolderDefaultView(sContext, id, type, defaultView);
                    }
                }
            }
        }


        // if conflicts have forced us to deviate from the specified sync, update the local store such that these changes are pushed during the next sync
        if (local != null && elt.getAttributeBool(InitialSync.A_RELOCATED, false))
            ombx.rename(null, id, type, name, parentId);

        return true;
    }

    private boolean isCompatibleFolder(Folder folder, Element elt, MailItem.Type type) {
        if (type != folder.getType()) {
            return false;
        }
        if (type == MailItem.Type.FOLDER) {
            MailItem.Type localView = folder.getDefaultView();
            MailItem.Type remoteView = MailItem.Type.of(elt.getAttribute(MailConstants.A_DEFAULT_VIEW, null));
            return localView == remoteView || localView == MailItem.Type.MESSAGE && remoteView == MailItem.Type.UNKNOWN ||
                localView == MailItem.Type.UNKNOWN && remoteView == MailItem.Type.MESSAGE;
            //bug 33871: fileinto auto-created folder can be of UNKNOWN type
        } else {
            return false;
        }
    }

    void syncTag(Element elt) throws ServiceException {
        int id = getTagSync().localTagId(elt);
        String name = elt.getAttribute(MailConstants.A_NAME);
        String rgb = elt.getAttribute(MailConstants.A_RGB, null);
        byte color = (byte) elt.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR);
        Color itemColor = rgb != null ? new Color(rgb) : new Color(color);

        int date = (int) (elt.getAttributeLong(MailConstants.A_DATE) / 1000);

        ombx.lock.lock();
        try {
            Tag tag = null;
            if (id > 0) {
                tag = getTag(id);
            }

            // deal with the case where the referenced tag doesn't exist
            if (tag == null) {
                // if it's been locally deleted but not pushed to the server yet, just return and let the delete happen later
                if (ombx.isPendingDelete(sContext, id, MailItem.Type.TAG)) {
                    return;
                }
                // resolve any naming conflicts and actually create the tag
                if (resolveTagConflicts(elt, id, tag)) {
                    getInitialSync().syncTag(elt);
                    return;
                } else {
                    tag = getTag(getTagSync().localTagId(elt)); //should be mapped now; but id was set to -1 while we tried to map
                    id = tag.getId();
                }
            }

            // if the tag was renamed locally, that trumps any changes made remotely
            resolveTagConflicts(elt, id, tag);

            name = elt.getAttribute(MailConstants.A_NAME);

            int change_mask = ombx.getChangeMask(sContext, id, MailItem.Type.TAG);
            // FIXME: if FOO was renamed BAR and BAR was renamed FOO, this will break
            if ((change_mask & Change.NAME) == 0) {
                ombx.rename(sContext, id, MailItem.Type.TAG, name);
            }
            if ((change_mask & Change.COLOR) == 0) {
                ombx.setColor(sContext, new int[] { id }, MailItem.Type.TAG, itemColor);
            }
            ombx.syncDate(sContext, id, MailItem.Type.TAG, date);

            if (elt.getAttributeBool(InitialSync.A_RELOCATED, false)) {
                ombx.setChangeMask(sContext, id, MailItem.Type.TAG, change_mask | Change.NAME);
            }
            OfflineLog.offline.debug("delta: updated tag (" + id + "): " + name);
        } finally {
            ombx.lock.release();
        }
    }

    private boolean resolveTagConflicts(Element elt, int id, Tag local) throws ServiceException {
        int change_mask = (local == null ? 0 : ombx.getChangeMask(sContext, id, MailItem.Type.TAG));

        String name = elt.getAttribute(MailConstants.A_NAME);
        if ((change_mask & Change.NAME) != 0 && !mSyncRenames.contains(id)) {
            name = local.getName();
            elt.addAttribute(MailConstants.A_NAME, name);
        } else {
            // handle the off-chance that the server's folder name is invalid
            String validName = MailItem.normalizeItemName(name);
            if (!validName.equals(name)) {
                name = validName;
                elt.addAttribute(MailConstants.A_NAME, name).addAttribute(InitialSync.A_RELOCATED, true);
            }
        }

        Tag conflict = getTag(name);
        if (conflict == null) {
            return true;
        }
        int conflict_mask = ombx.getChangeMask(sContext, conflict.getId(), MailItem.Type.TAG);
        if (conflict.getId() == id) {
            // new tag remotely, new tag locally, same name
            if ((conflict_mask & Change.CONFLICT) != 0) {
                ombx.setChangeMask(sContext, conflict.getId(), MailItem.Type.TAG, conflict_mask & ~Change.CONFLICT);
            }
            return false;
        }

        String uuid = '{' + UUID.randomUUID().toString() + '}', newName;
        if (name.length() + uuid.length() > MailItem.MAX_NAME_LENGTH) {
            newName = name.substring(0, MailItem.MAX_NAME_LENGTH - uuid.length()) + uuid;
        } else {
            newName = name + uuid;
        }
        if (local == null && (conflict_mask & Change.CONFLICT) != 0) {
            // if the new and existing tags are identical and being created, try to merge them
            if (getTagSync().isMappingRequired()) {
                getTagSync().mapTag(Integer.valueOf(elt.getAttribute(MailConstants.A_ID)), conflict.getId());
            } else {
                ombx.renumberItem(sContext, conflict.getId(), MailItem.Type.TAG, id);
            }
            ombx.setChangeMask(sContext, conflict.getId(), MailItem.Type.TAG, conflict_mask & ~Change.CONFLICT);
            return false;
        } else if ((conflict_mask & Change.NAME) != 0) {
            // the local user also renamed the tag, so the local client wins
            name = newName;
            elt.addAttribute(MailConstants.A_NAME, name).addAttribute(InitialSync.A_RELOCATED, true);
            if (local != null)
                ombx.rename(null, id, MailItem.Type.TAG, name);
        } else {
            // if there's a naming conflict, usually rename the local tag out of the way
            ombx.rename(null, conflict.getId(), MailItem.Type.TAG, newName);
            mSyncRenames.add(conflict.getId());
        }

        return true;
    }

    private Tag getTag(int id) throws ServiceException {
        return getTag(ombx, id);
    }

    static Tag getTag(Mailbox mbox, int id) throws ServiceException {
        try {
            if (mbox instanceof ZcsMailbox) {
                return ((ZcsMailbox) mbox).getTagById(sContext, id, false);
            }
            return mbox.getTagById(sContext, id);
        } catch (MailServiceException.NoSuchItemException nsie) {
            return null;
        }
    }

    private Tag getTag(String name) throws ServiceException {
        try {
            return ombx.getTagByName(sContext, name);
        } catch (MailServiceException.NoSuchItemException nsie) {
            return null;
        }
    }

    void syncContact(Element elt, int folderId) throws ServiceException {
        int id = (int) elt.getAttributeLong(MailConstants.A_ID);
        ombx.recordItemSync(id);
        Contact cn = null;
        try {
            // make sure that the contact we're delta-syncing actually exists
            cn = ombx.getContactById(sContext, id);
        } catch (MailServiceException.NoSuchItemException nsie) {
            // if it's been locally deleted but not pushed to the server yet, just return and let the delete happen later
            if (ombx.isPendingDelete(sContext, id, MailItem.Type.CONTACT))
                return;
            // before passing off to initial sync, make sure we have the contact's fields
            if (elt.listElements(Element.XMLElement.E_ATTRIBUTE).isEmpty())
                elt = InitialSync.fetchContacts(ombx, id + "").get(0);
            getInitialSync().syncContact(elt, folderId);
            return;
        }

        String rgb = elt.getAttribute(MailConstants.A_RGB, null);
        byte color = (byte) elt.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR);
        Color itemColor = rgb != null ? new Color(rgb) : new Color(color);
        int flags = Flag.toBitmask(elt.getAttribute(MailConstants.A_FLAGS, null));
        String[] tags = TagUtil.parseTags(elt, ombx, sContext);

        boolean hasBlob = false;
        Map<String, String> fields = new HashMap<String, String>();
        for (Element eField : elt.listElements(Element.XMLElement.E_ATTRIBUTE)) {
            if (eField.getAttribute(MailConstants.A_PART, null) != null)
                hasBlob = true;
            else
                fields.put(eField.getAttribute(Element.XMLElement.A_ATTR_NAME), eField.getText());
        }
        assert (fields.isEmpty() || hasBlob == ((flags & Flag.BITMASK_ATTACHED) != 0));

        int date = (int) (elt.getAttributeLong(MailConstants.A_DATE) / 1000);

        byte[] blob = null;
        if (hasBlob) {
            String url = Offline.getServerURI(ombx.getAccount(), UserServlet.SERVLET_PATH + "/~/?fmt=native&id=" + id);
            if (ombx.getOfflineAccount().isDebugTraceEnabled())
                OfflineLog.request.debug("GET " + url);
            try {
                blob = UserServlet.getRemoteContent(ombx.getAuthToken(), url);
            } catch (MailServiceException.NoSuchItemException nsie) {
                OfflineLog.offline.warn("delta: no blob available for contact " + id);
            }
        }
        ParsedContact pc = (fields.isEmpty() ? null : new ParsedContact(fields, blob));

        ombx.lock.lock();
        try {
            int change_mask = ombx.getChangeMask(sContext, id, MailItem.Type.CONTACT);
            if ((change_mask & Change.CONTENT) == 0 && pc != null) {
                ombx.modifyContact(sContext, id, pc);
            }
            ombx.syncMetadata(sContext, id, MailItem.Type.CONTACT, folderId, flags, tags, itemColor);
            ombx.syncDate(sContext, id, MailItem.Type.CONTACT, date);
        } finally {
            ombx.lock.release();
        }
        OfflineLog.offline.debug("delta: updated contact (" + id + "): " + cn.getFileAsString());
    }

    void syncMessage(Element elt, int folderId, MailItem.Type type) throws ServiceException {
        int id = (int) elt.getAttributeLong(MailConstants.A_ID);
        ombx.recordItemSync(id);
        try {
            Message msg = null;
            try {
                // make sure that the message we're delta-syncing actually exists
                msg = ombx.getMessageById(sContext, id);
            } catch (MailServiceException.NoSuchItemException nsie) {
                // if it's been locally deleted but not pushed to the server yet, just return and let the delete happen later
                if (!ombx.isPendingDelete(sContext, id, type)) {
                    getInitialSync().syncMessage(id, folderId, type);
                }
                return;
            }

            String rgb = elt.getAttribute(MailConstants.A_RGB, null);
            byte color = (byte) elt.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR);
            Color itemColor = rgb != null ? new Color(rgb) : new Color(color);
            int flags = Flag.toBitmask(elt.getAttribute(MailConstants.A_FLAGS, null));
            String[] tags = TagUtil.parseTags(elt, ombx, sContext);
            int convId = (int) elt.getAttributeLong(MailConstants.A_CONV_ID);

            int date = (int) (elt.getAttributeLong(MailConstants.A_DATE) / 1000);
            ombx.lock.lock();
            try {
                ombx.setConversationId(sContext, id, convId <= 0 ? -id : convId);
                ombx.syncMetadata(sContext, id, type, folderId, flags, tags, itemColor);
                ombx.syncDate(sContext, id, type, date);
            } catch (MailServiceException.NoSuchItemException nsie) {
                OfflineLog.offline.warn("NoSuchItemException in delta sync. Item [" + id
                        + "] must have been deleted while sync was in progress");
                return;
            } finally {
                ombx.lock.release();
            }
            OfflineLog.offline.debug("delta: updated " + type + " (" + id + "): "
                    + msg.getSubject());
        } catch (Exception x) {
            if (x instanceof ServiceException
                    && ((ServiceException) x).getCode().equals(MailServiceException.NO_SUCH_FOLDER)) {
                // message could be moved during
                OfflineLog.offline.debug("delta: moved" + type + " (" + id + ")");
            } else {
                if (!SyncExceptionHandler.isRecoverableException(ombx, id, "DeltaSync.syncMessage", x)) {
                    SyncExceptionHandler.syncMessageFailed(ombx, id, x);
                }
            }
        }
    }

    void syncDocuments(List<Integer> documents) throws ServiceException {
        StringBuilder query = null;
        if (ombx.getRemoteServerVersion().isAtLeast(InitialSync.sDocumentSyncHistoryVersion)) {
            for (int docId : documents) {
                if (query == null) {
                    query = new StringBuilder("list=");
                } else {
                    query.append(",");
                }
                query.append(docId);
            }
            if (query != null) {
                getInitialSync().syncDocument(query.toString());
            }
        } else {
            for (int docId : documents) {
                if (query == null) {
                    query = new StringBuilder("item:{");
                } else {
                    query.append(",");
                }
                query.append(docId);
            }
            query.append("}");
            Element request = new Element.XMLElement(MailConstants.SEARCH_REQUEST);
            request.addAttribute(MailConstants.A_QUERY_LIMIT, 1024); // XXX pagination
            request.addAttribute(MailConstants.A_TYPES, "document");
            request.addElement(MailConstants.E_QUERY).setText(query.toString());
            if (ombx.getOfflineAccount().isDebugTraceEnabled())
                OfflineLog.response.debug(request);

            Element response = ombx.sendRequest(request);

            if (ombx.getOfflineAccount().isDebugTraceEnabled())
                OfflineLog.response.debug(response);

            for (Element doc : response.listElements(MailConstants.E_DOC))
                getInitialSync().syncDocument(doc);
        }
    }
}
