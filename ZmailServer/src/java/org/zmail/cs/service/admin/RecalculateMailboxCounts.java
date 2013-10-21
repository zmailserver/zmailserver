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
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.soap.ZmailSoapContext;

public final class RecalculateMailboxCounts extends AdminDocumentHandler {

    private static final String[] TARGET_ACCOUNT_PATH = new String[] {
        AdminConstants.E_MAILBOX, AdminConstants.A_ACCOUNTID
    };

    @Override
    protected String[] getProxiedAccountPath() {
        return TARGET_ACCOUNT_PATH;
    }

    @Override
    protected Element proxyIfNecessary(Element request, Map<String, Object> context) throws ServiceException {
        // if we've explicitly been told to execute here, don't proxy
        ZmailSoapContext zsc = getZmailSoapContext(context);
        if (zsc.getProxyTarget() != null)
            return null;

        // check whether we need to proxy to the home server of a target account
        Provisioning prov = Provisioning.getInstance();
        String[] xpath = getProxiedAccountPath();
        String acctId = (xpath != null ? getXPath(request, xpath) : null);
        if (acctId != null) {
            Account acct = getAccount(prov, AccountBy.id, acctId, zsc.getAuthToken());
            if (acct != null && !Provisioning.onLocalServer(acct))
                return proxyRequest(request, context, acctId);
        }

        return null;
    }

    @Override
    public boolean domainAuthSufficient(Map<String, Object> context) {
        return true;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);

        Element mreq = request.getElement(AdminConstants.E_MAILBOX);
        String accountId = mreq.getAttribute(AdminConstants.A_ACCOUNTID);

        Provisioning prov = Provisioning.getInstance();
        Account account = prov.get(AccountBy.id, accountId, zsc.getAuthToken());
        if (account == null) {
            throw AccountServiceException.NO_SUCH_ACCOUNT(accountId);
        }
        checkAdminLoginAsRight(zsc, prov, account);

        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account, false);
        if (mbox == null) {
            throw MailServiceException.NO_SUCH_MBOX(accountId);
        }

        mbox.recalculateFolderAndTagCounts();

        Element response = zsc.createElement(AdminConstants.RECALCULATE_MAILBOX_COUNTS_RESPONSE);
        response.addElement(AdminConstants.E_MAILBOX)
            .addAttribute(AdminConstants.A_ACCOUNTID, accountId)
            .addAttribute(AdminConstants.A_QUOTA_USED, mbox.getSize());
        return response;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_adminLoginAs);
        relatedRights.add(Admin.R_adminLoginCalendarResourceAs);
        notes.add(AdminRightCheckPoint.Notes.ADMIN_LOGIN_AS);
    }

}
