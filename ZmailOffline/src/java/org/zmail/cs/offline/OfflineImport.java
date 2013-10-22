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
package com.zimbra.cs.offline;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.DataSource.DataImport;
import com.zimbra.cs.account.offline.OfflineDataSource;
import com.zimbra.cs.datasource.imap.ImapSync;
import com.zimbra.cs.offline.ab.gab.GabImport;
import com.zimbra.cs.offline.ab.yab.YabImport;
import com.zimbra.cs.offline.ab.yc.YContactImport;

public class OfflineImport implements DataImport {
    private final OfflineDataSource ds;
    private final DataImport di;
    private final String key;
    private final long interval;

    public static final int IMAP_INTERVAL =
        OfflineLC.zdesktop_imap_fullsync_interval.intValue();

    public static final int CONTACTS_INTERVAL =
        OfflineLC.zdesktop_contacts_fullsync_interval.intValue();

    public static final int CALENDAR_INTERVAL =
        OfflineLC.zdesktop_calendar_fullsync_interval.intValue();

    private static final Map<String, Long> lastFullSyncTime =
         new LinkedHashMap<String, Long>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry e) {
                return size() > 256;
            }
        };

    public static OfflineImport imapImport(OfflineDataSource ds)
        throws ServiceException {
        return new OfflineImport(ds,
            ds.isYahoo() ? new YMailImapSync(ds) : new ImapSync(ds),
            "imap", IMAP_INTERVAL);
    }

    public static OfflineImport gabImport(OfflineDataSource ds)
        throws ServiceException {
        return new OfflineImport(ds, new GabImport(ds), "gab", CONTACTS_INTERVAL);
    }

    public static OfflineImport yabImport(OfflineDataSource ds)
        throws ServiceException {
        return new OfflineImport(ds, new YabImport(ds), "yab", CONTACTS_INTERVAL);
    }
    
    public static OfflineImport ycImport(OfflineDataSource ds)
        throws ServiceException {
        return new OfflineImport(ds, new YContactImport(ds), "ycontact", CONTACTS_INTERVAL);
    }

    public static OfflineImport gcalImport(OfflineDataSource ds)
        throws ServiceException {
        return new OfflineImport(
            ds, new OfflineCalDavDataImport(ds, "gmail.com"), "caldav", CALENDAR_INTERVAL);
    }

    public static OfflineImport ycalImport(OfflineDataSource ds)
        throws ServiceException {
        return new OfflineImport(
            ds, new OfflineCalDavDataImport(ds, "yahoo.com"), "caldav", CALENDAR_INTERVAL);
    }

    public OfflineImport(OfflineDataSource ds, DataImport di,
                         String type, int intervalMins) {
        this.ds = ds;
        this.di = di;
        key = type + "-" + ds.getId();
        interval = intervalMins * 60000;
    }

    public void test() throws ServiceException {
        di.test();
    }

    public void importData(List<Integer> folderIds, boolean fullSync)
        throws ServiceException {
        di.importData(folderIds, checkSyncInterval(fullSync));
    }

    // Force full sync if more than interval ms have elapsed since the last
    // full sync.
    private boolean checkSyncInterval(boolean fullSync) {
        long currentTime = System.currentTimeMillis();
        synchronized (lastFullSyncTime) {
            Long time = lastFullSyncTime.get(key);
            if (time != null && currentTime - time > interval) {
                OfflineLog.offline.debug(
                    "Forcing full sync of data source %s since more than %d minutes have elapsed since last full sync",
                    ds.getName(), interval / 60000);
                fullSync = true;
            }
            if (time == null || fullSync) {
                // Update last full sync time
                lastFullSyncTime.put(key, currentTime);
            }
        }
        return fullSync;
    }
}
