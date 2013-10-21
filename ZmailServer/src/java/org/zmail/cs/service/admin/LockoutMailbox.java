/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2013 VMware, Inc.
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

import java.util.Map;

import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.Key.CalendarResourceBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.soap.ZmailSoapContext;

public class LockoutMailbox extends AdminDocumentHandler {

    protected void checkRights(ZmailSoapContext lc, Map<String, Object> context, Account account)
    throws ServiceException {
        Provisioning prov = Provisioning.getInstance();

        if (account.isCalendarResource()) {
            CalendarResource cr = prov.get(CalendarResourceBy.id, account.getId());
            checkCalendarResourceRight(lc, cr, Admin.R_moveCalendarResourceMailbox);
        } else {
            checkAccountRight(lc, account, Admin.R_moveAccountMailbox);
        }
        Server localServer = prov.getLocalServer();
        checkRight(lc, context, localServer, Admin.R_moveMailboxFromServer);
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = super.getZmailSoapContext(context);
        String email = request.getElement(AdminConstants.E_ACCOUNT).getAttribute(AdminConstants.A_NAME);

        Provisioning prov = Provisioning.getInstance();
        Account account = prov.get(AccountBy.name, email);
        if (account == null) {
            throw ServiceException.FAILURE("Account " + email + " not found", null);
        }
        // Reload account to pick up any changes made by other servers.
        prov.reload(account);

        // check rights
        checkRights(zsc, context, account);

        String method = request.getAttribute(AdminConstants.A_OPERATION, AdminConstants.A_START);

        if (method.equalsIgnoreCase(AdminConstants.A_START)) {
            MailboxManager.getInstance().lockoutMailbox(account.getId());
        } else if (method.equalsIgnoreCase(AdminConstants.A_END)) {
            MailboxManager.getInstance().undoLockout(account.getId());
        } else {
            throw ServiceException.FAILURE("Unknown lockout method " + method, null);
        }

        return zsc.createElement(AdminConstants.LOCKOUT_MAILBOX_RESPONSE);
    }
}
