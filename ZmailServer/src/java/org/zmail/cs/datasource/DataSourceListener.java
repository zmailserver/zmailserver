/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailboxListener;
import org.zmail.cs.session.PendingModifications.Change;
import org.zmail.cs.session.PendingModifications.ModificationKey;

public class DataSourceListener extends MailboxListener {

    public static final ImmutableSet<MailItem.Type> ITEMTYPES = ImmutableSet.of(
            MailItem.Type.FOLDER
    );

    @Override
    public void notify(ChangeNotification notification) {
        if (notification.mods.deleted != null) {
            for (Map.Entry<ModificationKey, Change> entry : notification.mods.deleted.entrySet()) {
                MailItem.Type type = (MailItem.Type) entry.getValue().what;
                if (type == MailItem.Type.FOLDER) {
                    Folder oldFolder = (Folder) entry.getValue().preModifyObj;
                    if (oldFolder == null) {
                        ZmailLog.datasource.warn("Cannot determine the old folder name for %s.", entry.getKey());
                        continue;
                    }
                    try {
                        ZmailLog.datasource.info("Deleting datasources that reference %s.", oldFolder.getPath());
                        Account account = oldFolder.getAccount();
                        List<DataSource> datasources = account.getAllDataSources();
                        for (DataSource datasource : datasources) {
                            if (datasource.getFolderId() == oldFolder.getId()) {
                                ZmailLog.datasource.debug("Deleting datasource %s.", datasource.getName());
                                account.deleteDataSource(datasource.getId());
                            }
                        }
                    } catch (ServiceException e) {
                        ZmailLog.datasource.warn("Could not delete datasources for folder.", oldFolder.getPath());
                    }
                }
            }
        }
    }

    @Override
    public Set<MailItem.Type> registerForItemTypes() {
        return ITEMTYPES;
    }
}
