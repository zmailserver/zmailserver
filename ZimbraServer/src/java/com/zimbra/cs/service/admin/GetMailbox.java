/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

/*
 * Created on Mar 9, 2005
 */
package com.zimbra.cs.service.admin;

import java.util.List;
import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.common.account.Key.AccountBy;
import com.zimbra.cs.account.accesscontrol.AdminRight;
import com.zimbra.cs.account.accesscontrol.Rights.Admin;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.soap.ZimbraSoapContext;

/**
 * @author dkarp
 */
public class GetMailbox extends AdminDocumentHandler {

    private static final String[] TARGET_ACCOUNT_PATH = new String[] { AdminConstants.E_MAILBOX, AdminConstants.A_ACCOUNTID };
    protected String[] getProxiedAccountPath()  { return TARGET_ACCOUNT_PATH; }

    /**
     * must be careful and only allow access to domain if domain admin
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);

        Element mreq = request.getElement(AdminConstants.E_MAILBOX);
        String accountId = mreq.getAttribute(AdminConstants.A_ACCOUNTID);

        Account account = Provisioning.getInstance().get(AccountBy.id, accountId, zsc.getAuthToken());
        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(accountId);

        checkAccountRight(zsc, account, Admin.R_getMailboxInfo);

        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account);
        Element response = zsc.createElement(AdminConstants.GET_MAILBOX_RESPONSE);
        Element m = response.addElement(AdminConstants.E_MAILBOX);
        m.addAttribute(AdminConstants.A_MAILBOXID, mbox.getId());
        m.addAttribute(AdminConstants.A_SIZE, mbox.getSize());
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getMailboxInfo);
    }
}
