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

package org.zmail.cs.datasource.offline;

import org.zmail.soap.admin.type.DataSourceType;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.DataSource.DataImport;
import org.zmail.cs.account.offline.OfflineDataSource;
import org.zmail.cs.datasource.DataSourceManager;
import org.zmail.cs.mailbox.Flag;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.offline.GMailImport;
import org.zmail.cs.offline.OfflineImport;
import org.zmail.cs.offline.YMailImport;
import org.zmail.cs.offline.ab.yc.YContactImport;


public class OfflineDataSourceManager extends DataSourceManager {

    @Override
    public boolean isSyncCapable(DataSource ds, Folder folder) {
        if (ds.isSyncInboxOnly())
            return folder.getId() == Mailbox.ID_FOLDER_INBOX;
        return (folder.getFlagBitmask() & Flag.BITMASK_SYNCFOLDER) != 0;
    }

    @Override
    public boolean isSyncEnabled(DataSource ds, Folder folder) {
        if (ds.isSyncInboxOnly() && folder.getId() != Mailbox.ID_FOLDER_INBOX) {
            return false;
        }
        int bits = folder.getFlagBitmask();
        return (bits & Flag.BITMASK_SYNCFOLDER) != 0 && (bits & Flag.BITMASK_SYNC) != 0;
    }

    @Override
    public DataImport getDataImport(DataSource ds) throws ServiceException {
        if (ds instanceof OfflineDataSource) {
            OfflineDataSource ods = (OfflineDataSource) ds;
            if (ds.getType() == DataSourceType.imap) {
                if (ods.isYahoo()) {
                    return new YMailImport(ods);
                } else if (ods.isGmail()) {
                    return new GMailImport(ods);
                } else {
                    return OfflineImport.imapImport(ods);
                }
            } else if (ds.getType() == DataSourceType.yab) {
//                return new YabImport(ods);
                return new YContactImport(ods);
            }
        }
        return super.getDataImport(ds);
    }
}
