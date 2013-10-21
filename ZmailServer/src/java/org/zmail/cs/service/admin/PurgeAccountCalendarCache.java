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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.Key.CalendarResourceBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.calendar.cache.CalendarCacheManager;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.soap.ZmailSoapContext;

public class PurgeAccountCalendarCache extends AdminDocumentHandler {

    private static final String[] TARGET_ACCOUNT_PATH = new String[] { AdminConstants.A_ID };
    @Override protected String[] getProxiedAccountPath()  { return TARGET_ACCOUNT_PATH; }

    /**
     * must be careful and only allow renames from/to domains a domain admin can see
     */
    @Override public boolean domainAuthSufficient(Map<String, Object> context) {
        return true;
    }

    @Override public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        // allow only system admin for now
        checkRight(zsc, context, null, AdminRight.PR_SYSTEM_ADMIN_ONLY);
        
        Provisioning prov = Provisioning.getInstance();

        String id = request.getAttribute(AdminConstants.A_ID);
        Account account = prov.get(AccountBy.id, id, zsc.getAuthToken());
        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(id);
        
        if (!Provisioning.onLocalServer(account))
            throw ServiceException.WRONG_HOST(account.getAttr(Provisioning.A_zmailMailHost), null);

        CalendarCacheManager calCache = CalendarCacheManager.getInstance();
        ZmailLog.calendar.info("Purging calendar cache for account " + account.getName());
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account, false);
        if (mbox != null)
            calCache.purgeMailbox(mbox);
        Element response = zsc.createElement(AdminConstants.PURGE_ACCOUNT_CALENDAR_CACHE_RESPONSE);
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.SYSTEM_ADMINS_ONLY);
    }
}
