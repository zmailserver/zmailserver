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
package org.zmail.cs.datasource;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zmail.common.calendar.ICalTimeZone;
import org.zmail.common.calendar.ZCalendar;
import org.zmail.common.calendar.ZCalendar.ZComponent;
import org.zmail.common.calendar.ZCalendar.ZProperty;
import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.dav.DavElements;
import org.zmail.cs.dav.DavException;
import org.zmail.cs.dav.client.CalDavClient;
import org.zmail.cs.dav.client.CalDavClient.Appointment;
import org.zmail.cs.dav.client.DavObject;
import org.zmail.cs.dav.client.DavRequest;
import org.zmail.cs.db.DbDataSource;
import org.zmail.cs.db.DbDataSource.DataSourceItem;
import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.Flag;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.MailServiceException.NoSuchItemException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Mailbox.SetCalendarItemData;
import org.zmail.cs.mailbox.Metadata;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.calendar.Invite;
import org.zmail.soap.type.DataSource.ConnectionType;

public class CalDavDataImport extends MailItemImport {

    private static final String METADATA_KEY_TYPE = "t";
    private static final String METADATA_TYPE_FOLDER = "f";
    private static final String METADATA_TYPE_APPOINTMENT = "a";
    private static final String METADATA_KEY_ETAG = "e";
    private static final String METADATA_KEY_CTAG = "c";
    private static final int DEFAULT_FOLDER_FLAGS = Flag.BITMASK_CHECKED;

    private CalDavClient mClient;

    private static class CalendarFolder {
        public int id;
        public Folder folder;
        public boolean ctagMatched;
        public CalendarFolder(int fid) { id = fid; }
    }

    public CalDavDataImport(DataSource ds) throws ServiceException {
        super(ds);
    }

    @Override
    public void importData(List<Integer> folderIds, boolean fullSync) throws ServiceException {
        ArrayList<CalendarFolder> folders = new ArrayList<CalendarFolder>();
        try {
            mbox.beginTrackingSync();
            if (folderIds != null)
                for (int fid : folderIds)
                    folders.add(new CalendarFolder(fid));
            else
                folders = syncFolders();
            OperationContext octxt = new OperationContext(mbox);
            for (CalendarFolder f : folders) {
                f.folder = mbox.getFolderById(octxt, f.id);
                if (f.folder.getDefaultView() == MailItem.Type.APPOINTMENT) {
                    sync(octxt, f);
                }
            }
        } catch (DavException e) {
            throw ServiceException.FAILURE("error importing CalDAV data", e);
        } catch (IOException e) {
            throw ServiceException.FAILURE("error importing CalDAV data", e);
        }
    }

    @Override
    public void test() throws ServiceException {
        mClient = new CalDavClient(getTargetUrl());
        mClient.setAppName(getAppName());
        mClient.setCredential(getUsername(), getDecryptedPassword());
        mClient.setDebugEnabled(dataSource.isDebugTraceEnabled());
        try {
            mClient.login(getDefaultPrincipalUrl());
        } catch (Exception x) {
            throw ServiceException.FAILURE(x.getMessage(), x);
        }
    }

    protected String getUsername() {
        return getDataSource().getUsername();
    }

    protected String getDecryptedPassword() throws ServiceException {
        return getDataSource().getDecryptedPassword();
    }

    protected byte getDefaultColor() { return 0; }

    protected String getDefaultPrincipalUrl() {
        DataSource ds = getDataSource();
        String attrs[] = ds.getMultiAttr(Provisioning.A_zmailDataSourceAttribute);
        for (String a : attrs) {
            if (a.startsWith("p:")) {
                return a.substring(2).replaceAll("_USERNAME_", getUsername());
            }
        }
        return null;
    }

    protected String getTargetUrl() {
        DataSource ds = getDataSource();
        ConnectionType ctype = ds.getConnectionType();
        StringBuilder url = new StringBuilder();

        switch (ctype) {
        case ssl:
            url.append("https://");
            break;
        case cleartext:
        default:
            url.append("http://");
        break;
        }
        url.append(ds.getHost()).append(":").append(ds.getPort());
        return url.toString();
    }

    protected String getAppName() {
        return "ZCS";
    }

    private CalDavClient getClient() throws ServiceException, IOException, DavException {
        if (mClient == null) {
            mClient = new CalDavClient(getTargetUrl());
            mClient.setAppName(getAppName());
            mClient.setCredential(getUsername(), getDecryptedPassword());
            mClient.setDebugEnabled(dataSource.isDebugTraceEnabled());
            mClient.login(getDefaultPrincipalUrl());
        }
        return mClient;
    }

    private enum Status { created, deleted, modified };
    private static class RemoteItem {
        Status status;
    }
    private static class RemoteCalendarItem extends RemoteItem {
        public RemoteCalendarItem(String h, String e) { href = h; etag = e; }
        String href;
        String etag;
        int itemId;
    }

    /**
     * @throws ServiceException subclasses may throw an error
     */
    protected int getRootFolderId(DataSource ds) throws ServiceException {
        return ds.getFolderId();
    }

    protected HashMap<String,DataSourceItem> getAllFolderMappings(DataSource ds) throws ServiceException {
        Collection<DataSourceItem> allFolders = DbDataSource.getAllMappingsInFolder(ds, getRootFolderId(ds));
        HashMap<String,DataSourceItem> folders = new HashMap<String,DataSourceItem>();
        for (DataSourceItem f : allFolders)
            if (f.remoteId != null)
                folders.put(f.remoteId, f);
        return folders;
    }

    private ArrayList<CalendarFolder> syncFolders() throws ServiceException, IOException, DavException {
        ArrayList<CalendarFolder> ret = new ArrayList<CalendarFolder>();
        DataSource ds = getDataSource();
        OperationContext octxt = new OperationContext(mbox);
        HashMap<String,DataSourceItem> allFolders = getAllFolderMappings(ds);
        Folder rootFolder = null;
        try {
            rootFolder = mbox.getFolderById(octxt, getRootFolderId(ds));
        } catch (NoSuchItemException e) {
            // folder may be deleted. delete the datasource
            ZmailLog.datasource.info("Folder %d was deleted.  Deleting data source %s.",
                    getRootFolderId(ds), ds.getName());
            mbox.getAccount().deleteDataSource(ds.getId());
            // return empty array
            return new ArrayList<CalendarFolder>(0);
        }
        List<Integer> deleted = new ArrayList<Integer>();
        int lastSync = (int)rootFolder.getLastSyncDate();
        if (lastSync > 0) {
            for (int itemId : mbox.getTombstones(lastSync).getAllIds())
                deleted.add(itemId);
        }
        CalDavClient client = getClient();
        Map<String,DavObject> calendars = client.getCalendars();
        for (String name : calendars.keySet()) {
            DavObject obj = calendars.get(name);
            String ctag = obj.getPropertyText(DavElements.E_GETCTAG);
            String url = obj.getHref();
            DataSourceItem f = allFolders.get(url);
            if (f == null)
                f = new DataSourceItem(0, 0, url, null);
            CalendarFolder cf = new CalendarFolder(f.itemId);
            Folder folder = null;
            if (f.itemId != 0) {
                // check if the folder was deleted
                if (deleted.contains(f.itemId)) {
                    allFolders.remove(url);
                    DbDataSource.deleteMapping(ds, f.itemId);
                    DbDataSource.deleteAllMappingsInFolder(ds, f.itemId);
                    deleteRemoteFolder(url);
                    continue;
                }
                // check if the folder is valid
                try {
                    folder = mbox.getFolderById(octxt, f.itemId);
                } catch (ServiceException se) {
                    if (se.getCode() != MailServiceException.NO_SUCH_FOLDER) {
                        throw se;
                    }
                    f.itemId = 0;
                }
            }
            if (f.itemId == 0) {
                try {
                    // check if we can use the folder
                    folder = mbox.getFolderByName(octxt, rootFolder.getId(), name);
                    if (folder.getDefaultView() != MailItem.Type.APPOINTMENT) {
                        name = name + " (" + getDataSource().getName() + ")";
                        folder = null;
                    }
                } catch (MailServiceException.NoSuchItemException e) {
                }

                if (folder == null) {
                    Folder.FolderOptions fopt = new Folder.FolderOptions();
                    fopt.setDefaultView(MailItem.Type.APPOINTMENT).setFlags(DEFAULT_FOLDER_FLAGS).setColor(getDefaultColor());
                    folder = mbox.createFolder(octxt, name, rootFolder.getId(), fopt);
                }
                f.itemId = folder.getId();
                f.folderId = folder.getFolderId();
                f.md = new Metadata();
                f.md.put(METADATA_KEY_TYPE, METADATA_TYPE_FOLDER);
                if (ctag != null) {
                    f.md.put(METADATA_KEY_CTAG, ctag);
                }
                f.remoteId = url;
                cf.id = f.itemId;
                mbox.setSyncDate(octxt, folder.getId(), mbox.getLastChangeID());
                DbDataSource.addMapping(ds, f);
            } else if (f.md == null) {
                ZmailLog.datasource.warn("syncFolders: empty metadata for item %d", f.itemId);
                f.folderId = folder.getFolderId();
                f.remoteId = url;
                f.md = new Metadata();
                f.md.put(METADATA_KEY_TYPE, METADATA_TYPE_FOLDER);
                if (ctag != null)
                    f.md.put(METADATA_KEY_CTAG, ctag);
                DbDataSource.addMapping(ds, f);
            } else if (ctag != null) {
                String oldctag = f.md.get(METADATA_KEY_CTAG, null);
                if (ctag.equals(oldctag)) {
                    cf.ctagMatched = true;
                } else {
                    f.md.put(METADATA_KEY_CTAG, ctag);
                    DbDataSource.updateMapping(ds, f);
                }
            }
            String fname = folder.getName();
            if (!fname.equals(name)) {
                ZmailLog.datasource.warn("renaming folder %s to %s", fname, name);
                try {
                    mbox.rename(octxt, f.itemId, MailItem.Type.FOLDER, name, folder.getFolderId());
                } catch (ServiceException e) {
                    ZmailLog.datasource.warn("folder rename failed", e);
                }
            }
            allFolders.remove(url);
            ret.add(cf);
        }
        if (!allFolders.isEmpty()) {
            // handle deleted folders
            ArrayList<Integer> fids = new ArrayList<Integer>();
            int[] fidArray = new int[allFolders.size()];
            int i = 0;
            for (DataSourceItem f : allFolders.values()) {
                Folder folder = mbox.getFolderById(octxt, f.itemId);
                if (folder != null && folder.getDefaultView() != MailItem.Type.APPOINTMENT &&
                        folder.getDefaultView() != MailItem.Type.TASK) {
                    continue;
                }
                fids.add(f.itemId);
                fidArray[i++] = f.itemId;
                DbDataSource.deleteAllMappingsInFolder(ds, f.itemId);
            }
            if (!fids.isEmpty()) {
                DbDataSource.deleteMappings(ds, fids);
                try {
                    mbox.delete(octxt, fidArray, MailItem.Type.FOLDER, null);
                } catch (ServiceException e) {
                    ZmailLog.datasource.warn("folder delete failed", e);
                }
            }
        }
        mbox.setSyncDate(octxt, rootFolder.getId(), mbox.getLastChangeID());
        return ret;
    }
    private void deleteRemoteFolder(String url) throws ServiceException, IOException, DavException {
        ZmailLog.datasource.debug("deleteRemoteFolder: deleting remote folder %s", url);
        getClient().sendRequest(DavRequest.DELETE(url));
    }
    private boolean pushDelete(Collection<Integer> itemIds) throws ServiceException {
        DataSource ds = getDataSource();
        boolean deleted = false;
        ArrayList<Integer> toDelete = new ArrayList<Integer>();
        for (int itemId : itemIds) {
            try {
                deleteRemoteItem(DbDataSource.getMapping(ds, itemId));
                toDelete.add(itemId);
            } catch (Exception e) {
                ZmailLog.datasource.warn("pushDelete: can't delete remote item for item "+itemId, e);
            }
        }
        if (toDelete.size() > 0) {
            DbDataSource.deleteMappings(ds, toDelete);
            deleted = true;
        }
        return deleted;
    }
    private void deleteRemoteItem(DataSourceItem item) throws ServiceException, IOException, DavException {
        if (item.itemId <= 0 || item.md == null) {
            ZmailLog.datasource.warn("pushDelete: empty item %d", item.itemId);
            return;
        }
        String type = item.md.get(METADATA_KEY_TYPE, null);
        if (type == null || !type.equals(METADATA_TYPE_APPOINTMENT)) {
            // not a calendar item
            return;
        }
        String uri = item.remoteId;
        if (uri == null) {
            ZmailLog.datasource.warn("pushDelete: empty uri for item %d", item.itemId);
            return;
        }
        if (METADATA_TYPE_FOLDER.equals(type)) {
            ZmailLog.datasource.debug("pushDelete: deleting remote folder %s", uri);
            getClient().sendRequest(DavRequest.DELETE(uri));
        } else if (METADATA_TYPE_APPOINTMENT.equals(type)) {
            ZmailLog.datasource.debug("pushDelete: deleting remote appointment %s", uri);
            getClient().sendRequest(DavRequest.DELETE(uri));
        } else {
            ZmailLog.datasource.warn("pushDelete: unrecognized item type for %d: %s", item.itemId, type);
        }
    }
    private String createTargetUrl(MailItem mitem) throws ServiceException {
        DataSourceItem folder = DbDataSource.getMapping(getDataSource(), mitem.getFolderId());
        String url = folder.remoteId;
        switch (mitem.getType()) {
        case APPOINTMENT:
            url += ((CalendarItem)mitem).getUid() + ".ics";
            break;
        default:
            String name = mitem.getName();
            if (name != null)
                url += name;
            else
                url += mitem.getSubject();
            break;
        }
        return url;
    }
    private void pushModify(MailItem mitem) throws ServiceException, IOException, DavException {
        int itemId = mitem.getId();
        DataSource ds = getDataSource();
        DataSourceItem item = DbDataSource.getMapping(ds, itemId);
        boolean isCreate = false;
        if (item.remoteId == null) {
            // new item
            item.md = new Metadata();
            item.md.put(METADATA_KEY_TYPE, METADATA_TYPE_APPOINTMENT);
            item.remoteId = createTargetUrl(mitem);
            item.folderId = mitem.getFolderId();
            isCreate = true;
        }
        String type = item.md.get(METADATA_KEY_TYPE);
        if (METADATA_TYPE_FOLDER.equals(type)) {
            if (mitem.getType() != MailItem.Type.FOLDER) {
                ZmailLog.datasource.warn("pushModify: item type doesn't match in metadata for item %d", itemId);
                return;
            }
            // detect and push rename
        } else if (METADATA_TYPE_APPOINTMENT.equals(type)) {
            if (mitem.getType() != MailItem.Type.APPOINTMENT) {
                ZmailLog.datasource.warn("pushModify: item type doesn't match in metadata for item %d", itemId);
                return;
            }
            // push modified appt
            ZmailLog.datasource.debug("pushModify: sending appointment %s", item.remoteId);
            String etag = putAppointment((CalendarItem)mitem, item);
            if (etag == null) {
                Appointment appt = mClient.getEtag(item.remoteId);
                etag = appt.etag;
            }

            item.md.put(METADATA_KEY_ETAG, etag);
            if (isCreate) {
                DbDataSource.addMapping(ds, item);
            } else {
                DbDataSource.updateMapping(ds, item);
            }
        } else {
            ZmailLog.datasource.warn("pushModify: unrecognized item type for %d: %s", itemId, type);
            return;
        }
    }
    private String putAppointment(CalendarItem calItem, DataSourceItem dsItem) throws ServiceException, IOException, DavException {
        StringBuilder buf = new StringBuilder();
        ArrayList<String> recipients = new ArrayList<String>();

        buf.append("BEGIN:VCALENDAR\r\n");
        buf.append("VERSION:").append(ZCalendar.sIcalVersion).append("\r\n");
        buf.append("PRODID:").append(ZCalendar.sZmailProdID).append("\r\n");
        Iterator<ICalTimeZone> iter = calItem.getTimeZoneMap().tzIterator();
        while (iter.hasNext()) {
            ICalTimeZone tz = iter.next();
            CharArrayWriter wr = new CharArrayWriter();
            tz.newToVTimeZone().toICalendar(wr, true);
            wr.flush();
            buf.append(wr.toCharArray());
            wr.close();
        }
        boolean appleICalExdateHack = LC.calendar_apple_ical_compatible_canceled_instances.booleanValue();
        ZComponent[] vcomps = Invite.toVComponents(calItem.getInvites(), true, false, appleICalExdateHack);
        if (vcomps != null) {
            CharArrayWriter wr = new CharArrayWriter();
            for (ZComponent vcomp : vcomps) {
                ZProperty organizer = vcomp.getProperty(ZCalendar.ICalTok.ORGANIZER);
                if (organizer != null)
                    organizer.setValue(getUsername());
                vcomp.toICalendar(wr, true);
            }
            wr.flush();
            buf.append(wr.toCharArray());
            wr.close();
        }
        buf.append("END:VCALENDAR\r\n");
        String etag = dsItem.md.get(METADATA_KEY_ETAG, null);
        if (recipients.isEmpty())
            recipients = null;
        Appointment appt = new Appointment(dsItem.remoteId, etag, buf.toString(), recipients);
        return getClient().sendCalendarData(appt);
    }
    private List<RemoteItem> getRemoteItems(Folder folder) throws ServiceException, IOException, DavException {
        ZmailLog.datasource.debug("Refresh folder %s", folder.getPath());
        DataSource ds = getDataSource();
        DataSourceItem item = DbDataSource.getMapping(ds, folder.getId());
        if (item.md == null)
            throw ServiceException.FAILURE("Mapping for folder "+folder.getPath()+" not found", null);

        // CalDAV doesn't provide delete tombstone.  in order to check for deleted appointments
        // we need to cross reference the current result with what we have from last sync
        // and check for any appointment that has disappeared since last sync.
        HashMap<String,DataSourceItem> allItems = new HashMap<String,DataSourceItem>();
        for (DataSourceItem localItem : DbDataSource.getAllMappingsInFolder(getDataSource(), folder.getId()))
            allItems.put(localItem.remoteId, localItem);

        ArrayList<RemoteItem> ret = new ArrayList<RemoteItem>();
        CalDavClient client = getClient();
        Collection<Appointment> appts = client.getEtags(item.remoteId);
        for (Appointment a : appts) {
            ret.add(new RemoteCalendarItem(a.href, a.etag));
            allItems.remove(a.href);
        }
        ArrayList<Integer> deletedIds = new ArrayList<Integer>();
        for (DataSourceItem deletedItem : allItems.values()) {
            // what's left in the collection are previous mapping that has disappeared.
            // we need to delete the appointments that are mapped locally.
            RemoteCalendarItem rci = new RemoteCalendarItem(deletedItem.remoteId, null);
            rci.status = Status.deleted;
            rci.itemId = deletedItem.itemId;
            ret.add(rci);
            deletedIds.add(deletedItem.itemId);
            ZmailLog.datasource.debug("deleting: %d (%s) ", deletedItem.itemId, deletedItem.remoteId);
        }
        if (!deletedIds.isEmpty())
            DbDataSource.deleteMappings(ds, deletedIds);
        return ret;
    }
    private MailItem applyRemoteItem(RemoteItem remoteItem, Folder where) throws ServiceException, IOException {
        if (!(remoteItem instanceof RemoteCalendarItem)) {
            ZmailLog.datasource.warn("applyRemoteItem: not a calendar item: %s", remoteItem);
            return null;
        }
        RemoteCalendarItem item = (RemoteCalendarItem) remoteItem;
        DataSource ds = getDataSource();
        DataSourceItem dsItem = DbDataSource.getReverseMapping(ds, item.href);
        OperationContext octxt = new OperationContext(mbox);
        MailItem mi = null;
        boolean isStale = false;
        boolean isCreate = false;
        if (dsItem.md == null && item.status != Status.deleted) {
            dsItem.md = new Metadata();
            dsItem.md.put(METADATA_KEY_TYPE, METADATA_TYPE_APPOINTMENT);
        }
        if (dsItem.itemId == 0) {
            isStale = true;
            isCreate = true;
        } else {
            String etag = dsItem.md.get(METADATA_KEY_ETAG, null);
            try {
                mi = mbox.getItemById(octxt, dsItem.itemId, MailItem.Type.UNKNOWN);
            } catch (MailServiceException.NoSuchItemException se) {
                ZmailLog.datasource.warn("applyRemoteItem: calendar item not found: ", remoteItem);
            }
            if (item.etag == null) {
                ZmailLog.datasource.warn("No Etag returned for item %s", item.href);
                isStale = true;
            } else if (etag == null) {
                ZmailLog.datasource.warn("Empty etag for item %d", dsItem.itemId);
                isStale = true;
            } else {
                isStale = !item.etag.equals(etag);
            }
            if (mi == null)
                isStale = true;
        }
        if (item.status == Status.deleted) {
            ZmailLog.datasource.debug("Deleting appointment %s", item.href);
            try {
                mi = mbox.getItemById(octxt, item.itemId, MailItem.Type.UNKNOWN);
            } catch (NoSuchItemException se) {
                mi = null;
            }
            try {
                mbox.delete(octxt, item.itemId, MailItem.Type.UNKNOWN);
            } catch (ServiceException se) {
                ZmailLog.datasource.warn("Error deleting remotely deleted item %d (%s)", item.itemId, dsItem.remoteId);
            }
        } else if (isStale) {
            ZmailLog.datasource.debug("Updating stale appointment %s", item.href);
            ZCalendar.ZVCalendar vcalendar;
            SetCalendarItemData main = new SetCalendarItemData();
            SetCalendarItemData exceptions[] = null;
            CalDavClient client = null;
            try {
                client = getClient();
            } catch (DavException e) {
                   throw ServiceException.FAILURE("error creating CalDAV client", e);
            }

            Appointment appt = client.getCalendarData(new Appointment(item.href, item.etag));
            if (appt.data == null) {
                ZmailLog.datasource.warn("No appointment found at "+item.href);
                return null;
            }
            dsItem.md.put(METADATA_KEY_ETAG, appt.etag);

            try {
                vcalendar = ZCalendar.ZCalendarBuilder.build(appt.data);
                List<Invite> invites = Invite.createFromCalendar(mbox.getAccount(), null, vcalendar, true);
                if (invites.size() > 1)
                    exceptions = new SetCalendarItemData[invites.size() - 1];

                int pos = 0;
                boolean first = true;
                for (Invite i : invites) {
                    if (first) {
                        main.invite = i;
                        first = false;
                    } else {
                        SetCalendarItemData scid = new SetCalendarItemData();
                        scid.invite = i;
                        exceptions[pos++] = scid;
                    }
                }
            } catch (Exception e) {
                ZmailLog.datasource.warn("Error parsing appointment ", e);
                return null;
            }
            mi = mbox.setCalendarItem(octxt, where.getId(), 0, null, main, exceptions, null, CalendarItem.NEXT_ALARM_KEEP_CURRENT);
            dsItem.itemId = mi.getId();
            dsItem.folderId = mi.getFolderId();
            if (isCreate) {
                DbDataSource.addMapping(ds, dsItem);
            } else {
                DbDataSource.updateMapping(ds, dsItem);
            }
        } else {
            ZmailLog.datasource.debug("Appointment up to date %s", item.href);
            try {
                mi = mbox.getItemById(octxt, dsItem.itemId, MailItem.Type.UNKNOWN);
            } catch (NoSuchItemException se) {
                // item not found.  delete the mapping so it can be downloaded again if needed.
                ArrayList<Integer> deletedIds = new ArrayList<Integer>();
                deletedIds.add(dsItem.itemId);
                DbDataSource.deleteMappings(ds, deletedIds);
            }
        }
        return mi;
    }

    private void sync(OperationContext octxt, CalendarFolder cf) throws ServiceException, IOException, DavException {
        Folder syncFolder = cf.folder;
        int lastSync = (int)syncFolder.getLastSyncDate();  // hack alert: caldav import uses sync date field to store sync token
        int currentSync = lastSync;
        boolean allDone = false;
        HashMap<Integer,Integer> modifiedFromRemote = new HashMap<Integer,Integer>();
        ArrayList<Integer> deletedFromRemote = new ArrayList<Integer>();

        // loop through as long as there are un'synced local changes
        while (!allDone) {
            allDone = true;

            if (lastSync > 0) {  // Don't push local changes during initial sync.
                // push local deletion
                List<Integer> deleted = new ArrayList<Integer>();
                for (int itemId : mbox.getTombstones(lastSync).getAllIds()) {
                    if (deletedFromRemote.contains(itemId)) {
                        continue;  // was just deleted from sync
                    }
                    deleted.add(itemId);
                }

                // move to trash is equivalent to delete
                HashSet<Integer> fid = new HashSet<Integer>();
                fid.add(Mailbox.ID_FOLDER_TRASH);
                List<Integer> trashed = mbox.getModifiedItems(octxt, lastSync, MailItem.Type.UNKNOWN, fid).getFirst();
                deleted.addAll(trashed);

                if (!deleted.isEmpty()) {
                    // pushDelete returns true if one or more items were deleted
                    allDone &= !pushDelete(deleted);
                }

                // push local modification
                fid.clear();
                fid.add(syncFolder.getId());
                List<Integer> modified = mbox.getModifiedItems(octxt, lastSync, MailItem.Type.UNKNOWN, fid).getFirst();
                for (int itemId : modified) {
                    MailItem item = mbox.getItemById(octxt, itemId, MailItem.Type.UNKNOWN);
                    if (modifiedFromRemote.containsKey(itemId) &&
                            modifiedFromRemote.get(itemId).equals(item.getModifiedSequence()))
                        continue;  // was just downloaded from remote
                    try {
                        pushModify(item);
                    } catch (Exception e) {
                        ZmailLog.datasource.info("Failed to push item "+item.getId(), e);
                    }
                    allDone = false;
                }
            }

            if (cf.ctagMatched) {
                currentSync = mbox.getLastChangeID();
                break;
            }

            // pull in the changes from the remote server
            List<RemoteItem> remoteItems = getRemoteItems(syncFolder);
            for (RemoteItem item : remoteItems) {
                MailItem localItem = applyRemoteItem(item, syncFolder);
                if (localItem != null) {
                    if (item.status == Status.deleted)
                        deletedFromRemote.add(localItem.getId());
                    else
                        modifiedFromRemote.put(localItem.getId(), localItem.getModifiedSequence());
                }
            }
            currentSync = mbox.getLastChangeID();
            lastSync = currentSync;
        }
        mbox.setSyncDate(octxt, syncFolder.getId(), currentSync);
    }
}
