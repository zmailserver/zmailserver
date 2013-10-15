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
package org.zmail.cs.offline.ab.gab;

import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.offline.OfflineDataSource;
import org.zmail.cs.datasource.DataSourceManager;
import org.zmail.cs.mailbox.SyncExceptionHandler;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.offline.OfflineLog;
import org.zmail.cs.offline.ab.LocalData;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Log;

import java.util.List;

public class GabImport implements DataSource.DataImport {
    private final OfflineDataSource ds;
    private SyncSession session;

    private static final Log LOG = OfflineLog.gab;

    private static final String ERROR = "Google address book synchronization failed";

    public GabImport(DataSource ds) {
        this.ds = (OfflineDataSource) ds;
    }

    public void test() throws ServiceException {
        session = new SyncSession(ds);
    }

    public void importData(List<Integer> folderIds, boolean fullSync)
        throws ServiceException {
         // Only sync contacts if full sync or there are local contact changes
        if (!fullSync && !new LocalData(ds).hasLocalChanges()) {
            return;
        }
        LOG.info("Importing contacts for data source '%s'", ds.getName());
        DataSourceManager.getInstance().getMailbox(ds).beginTrackingSync();
        if (session == null) {
            session = new SyncSession(ds);
        }
        try {
            session.sync();
        } catch (Exception e) {
            if (!SyncExceptionHandler.isRecoverableException(null, 1, ERROR, e)) {
                ds.reportError(Mailbox.ID_FOLDER_CONTACTS, ERROR, e);
            }
        }
        LOG.info("Finished importing contacts for data source '%s'", ds.getName());
    }
}
