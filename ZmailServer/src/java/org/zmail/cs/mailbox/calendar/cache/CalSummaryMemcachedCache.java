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

package org.zmail.cs.mailbox.calendar.cache;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.util.memcached.BigByteArrayMemcachedMap;
import org.zmail.common.util.memcached.ByteArraySerializer;
import org.zmail.common.util.memcached.ZmailMemcachedClient;
import org.zmail.cs.index.SortBy;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Metadata;
import org.zmail.cs.memcached.MemcachedConnector;
import org.zmail.cs.session.PendingModifications;
import org.zmail.cs.session.PendingModifications.Change;
import org.zmail.cs.session.PendingModifications.ModificationKey;

public class CalSummaryMemcachedCache {

    private BigByteArrayMemcachedMap<CalSummaryKey, CalendarData> mMemcachedLookup;

    CalSummaryMemcachedCache() {
        ZmailMemcachedClient memcachedClient = MemcachedConnector.getClient();
        CalSummarySerializer serializer = new CalSummarySerializer();
        mMemcachedLookup = new BigByteArrayMemcachedMap<CalSummaryKey, CalendarData>(memcachedClient, serializer);
    }

    private static class CalSummarySerializer implements ByteArraySerializer<CalendarData> {
        CalSummarySerializer() { }

        @Override
        public byte[] serialize(CalendarData value) {
            try {
                return value.encodeMetadata().toString().getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                ZmailLog.calendar.warn("Unable to serialize data for calendar summary cache", e);
                return null;
            }
        }

        @Override
        public CalendarData deserialize(byte[] bytes) throws ServiceException {
            if (bytes != null) {
                String encoded;
                try {
                    encoded = new String(bytes, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    ZmailLog.calendar.warn("Unable to deserialize data for calendar summary cache", e);
                    return null;
                }
                Metadata meta = new Metadata(encoded);
                return new CalendarData(meta);
            } else {
                return null;
            }
        }
    }

    public CalendarData getForRange(CalSummaryKey key, long rangeStart, long rangeEnd)
    throws ServiceException {
        CalendarData calData = mMemcachedLookup.get(key);
        if (calData != null && rangeStart >= calData.getRangeStart() && rangeEnd <= calData.getRangeEnd())
            return calData.getSubRange(rangeStart, rangeEnd);
        else
            return null;
    }

    public void put(CalSummaryKey key, CalendarData calData) throws ServiceException {
        mMemcachedLookup.put(key, calData);
    }

    void purgeMailbox(Mailbox mbox) throws ServiceException {
        String accountId = mbox.getAccountId();
        List<Folder> folders = mbox.getCalendarFolders(null, SortBy.NONE);
        List<CalSummaryKey> keys = new ArrayList<CalSummaryKey>(folders.size());
        for (Folder folder : folders) {
            CalSummaryKey key = new CalSummaryKey(accountId, folder.getId());
            keys.add(key);
        }
        mMemcachedLookup.removeMulti(keys);
    }

    void notifyCommittedChanges(PendingModifications mods, int changeId) {
        Set<CalSummaryKey> keysToInvalidate = new HashSet<CalSummaryKey>();
        if (mods.modified != null) {
            for (Map.Entry<ModificationKey, Change> entry : mods.modified.entrySet()) {
                Change change = entry.getValue();
                Object whatChanged = change.what;
                if (whatChanged instanceof Folder) {
                    Folder folder = (Folder) whatChanged;
                    MailItem.Type viewType = folder.getDefaultView();
                    if (viewType == MailItem.Type.APPOINTMENT || viewType == MailItem.Type.TASK) {
                        CalSummaryKey key = new CalSummaryKey(folder.getMailbox().getAccountId(), folder.getId());
                        keysToInvalidate.add(key);
                    }
                }
            }
        }
        if (mods.deleted != null) {
            for (Map.Entry<ModificationKey, Change> entry : mods.deleted.entrySet()) {
                MailItem.Type type = (MailItem.Type) entry.getValue().what;
                if (type == MailItem.Type.FOLDER) {
                    // We only have item id.  Assume it's a folder id and issue a delete.
                    String acctId = entry.getKey().getAccountId();
                    if (acctId == null)
                        continue;  // just to be safe
                    CalSummaryKey key = new CalSummaryKey(acctId, entry.getKey().getItemId());
                    keysToInvalidate.add(key);
                }
                // Let's not worry about hard deletes of invite/reply emails.  It has no practical benefit.
            }
        }
        try {
            mMemcachedLookup.removeMulti(keysToInvalidate);
        } catch (ServiceException e) {
            ZmailLog.calendar.warn("Unable to notify ctag info cache.  Some cached data may become stale.", e);
        }
    }
}
