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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.db.DbMailbox;
import org.zmail.cs.db.DbPool;
import org.zmail.cs.db.DbPool.DbConnection;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.session.AdminSession;
import org.zmail.cs.session.Session;
import org.zmail.soap.ZmailSoapContext;

public class GetMailboxStats extends AdminDocumentHandler {
    private static final String GET_MAILBOX_STATS_CACHE_KEY = "GetMailboxStats";

    @Override public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);

        Server localServer = Provisioning.getInstance().getLocalServer();
        checkRight(zsc, context, localServer, Admin.R_getMailboxStats);

        MailboxStats stats = null;

        AdminSession session = (AdminSession) getSession(zsc, Session.Type.ADMIN);
        if (session != null) {
            MailboxStats cachedStats = (MailboxStats) session.getData(GET_MAILBOX_STATS_CACHE_KEY);
            if (cachedStats == null) {
                stats = doStats();
                session.setData(GET_MAILBOX_STATS_CACHE_KEY, stats);
            } else {
                stats = cachedStats;
            }
        } else {
            stats = doStats();
        }

        Element response = zsc.createElement(AdminConstants.GET_MAILBOX_STATS_RESPONSE);
        Element statsElem = response.addElement(AdminConstants.E_STATS);
        statsElem.addAttribute(AdminConstants.A_NUM_MBOXES, stats.mNumMboxes);
        statsElem.addAttribute(AdminConstants.A_TOTAL_SIZE, stats.mTotalSize);
        return response;
    }

    private static class MailboxStats {
        long mNumMboxes = 0;
        long mTotalSize = 0;
    }

    private MailboxStats doStats() throws ServiceException {
        List<Mailbox.MailboxData> mailboxes = doSearch();
        MailboxStats stats = new MailboxStats();

        for (Mailbox.MailboxData m : mailboxes) {
            stats.mNumMboxes++;
            stats.mTotalSize += m.size;
        }

        return stats;
    }

    private List<Mailbox.MailboxData> doSearch() throws ServiceException {
        List<Mailbox.MailboxData> result = null;
        DbConnection conn = null;
        try {
            conn = DbPool.getConnection();
            result = DbMailbox.getMailboxRawData(conn);
        } finally {
            DbPool.quietClose(conn);
        }
        return result;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getMailboxStats);
    }
}
