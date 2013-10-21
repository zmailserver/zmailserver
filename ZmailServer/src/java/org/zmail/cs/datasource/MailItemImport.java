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
package org.zmail.cs.datasource;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.filter.RuleManager;
import org.zmail.cs.mailbox.DeliveryOptions;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.DeliveryContext;
import org.zmail.cs.mailbox.Flag;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.service.util.ItemId;
import org.zmail.soap.type.DataSource.ConnectionType;

import java.io.IOException;
import java.util.List;

public abstract class MailItemImport implements DataSource.DataImport {
    protected final DataSource dataSource;
    protected final Mailbox mbox;

    public MailItemImport(DataSource ds) throws ServiceException {
        dataSource = ds;
        mbox = DataSourceManager.getInstance().getMailbox(ds);
    }

    public void validateDataSource() throws ServiceException {
        DataSource ds = getDataSource();
        if (ds.getHost() == null) {
            throw ServiceException.FAILURE(ds + ": host not set", null);
        }
        if (ds.getPort() == null) {
            throw ServiceException.FAILURE(ds + ": port not set", null);
        }
        if (ds.getConnectionType() == null) {
            throw ServiceException.FAILURE(ds + ": connectionType not set", null);
        }
        if (ds.getUsername() == null) {
            throw ServiceException.FAILURE(ds + ": username not set", null);
        }
    }

    public boolean isOffline() {
        return getDataSource().isOffline();
    }


    public Message addMessage(OperationContext octxt, ParsedMessage pm, int size,
                                 int folderId, int flags, DeliveryContext dc)
        throws ServiceException, IOException {
        Message msg = null;
        switch (folderId) {
        case Mailbox.ID_FOLDER_INBOX:
            try {
                List<ItemId> addedMessageIds = RuleManager.applyRulesToIncomingMessage(octxt, mbox, pm, size,
                    dataSource.getEmailAddress(), dc, Mailbox.ID_FOLDER_INBOX, true);
                Integer newMessageId = getFirstLocalId(addedMessageIds);
                if (newMessageId == null) {
                    return null; // Message was discarded or filed remotely
                } else {
                    msg = mbox.getMessageById(null, newMessageId);
                }
                // Set flags (setting of BITMASK_UNREAD is implicit)
                if (flags != Flag.BITMASK_UNREAD) {
                    // Bug 28275: Cannot set DRAFT flag after message has been created
                    flags &= ~Flag.BITMASK_DRAFT;
                    mbox.setTags(octxt, newMessageId, MailItem.Type.MESSAGE, flags, MailItem.TAG_UNCHANGED);
                }
            } catch (Exception e) {
                ZmailLog.datasource.warn("Error applying filter rules", e);
            }
            break;
        case Mailbox.ID_FOLDER_DRAFTS:
        case Mailbox.ID_FOLDER_SENT:
            flags |= Flag.BITMASK_FROM_ME;
            break;
        }
        if (msg == null) {
            msg = mbox.addMessage(octxt, pm, new DeliveryOptions().setFolderId(folderId).setFlags(flags), null);
        }
        return msg;
    }

    public boolean isSslEnabled() {
        return dataSource.getConnectionType() == ConnectionType.ssl;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Mailbox getMailbox() {
        return mbox;
    }

    public Integer getFirstLocalId(List<ItemId> idList) {
        if (idList == null) {
            return null;
        }
        for (ItemId id : idList) {
            if (id.belongsTo(mbox)) {
                return id.getId();
            }
        }
        return null;
    }

    public void checkIsEnabled() throws ServiceException {
        if (!getDataSource().isManaged()) {
            throw ServiceException.FAILURE(
                "Import aborted because data source has been deleted or disabled", null);
        }
    }
}
