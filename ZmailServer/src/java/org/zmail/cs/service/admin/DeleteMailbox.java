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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

public class DeleteMailbox extends AdminDocumentHandler {

    private static final String[] TARGET_ACCOUNT_PATH = new String[] { AdminConstants.E_MAILBOX, AdminConstants.A_ACCOUNTID };
    @Override protected String[] getProxiedAccountPath()  { return TARGET_ACCOUNT_PATH; }

    /**
     * must be careful and only allow access to domain if domain admin
     */
    @Override public boolean domainAuthSufficient(Map<String, Object> context) {
        return true;
    }

    @Override public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);

        Element mreq = request.getElement(AdminConstants.E_MAILBOX);
        String accountId = mreq.getAttribute(AdminConstants.A_ACCOUNTID);
        
        Account account = Provisioning.getInstance().get(AccountBy.id, accountId, zsc.getAuthToken());
        if (account == null) {
            // Note: isDomainAdminOnly *always* returns false for pure ACL based AccessManager 
            if (isDomainAdminOnly(zsc)) {
                throw ServiceException.PERM_DENIED("account doesn't exist, unable to determine authorization");
            }
            
            // still need to check right, since we don't have an account, the 
            // last resort is checking the global grant.  Do this for now until 
            // there is complain.
            checkRight(zsc, context, null, Admin.R_deleteAccount); 
            
            ZmailLog.account.warn("DeleteMailbox: account doesn't exist: "+accountId+" (still deleting mailbox)");

        } else {
            checkAccountRight(zsc, account, Admin.R_deleteAccount);   
        }

        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(accountId, false);
        int mailboxId = -1;
        if (mbox != null) {
            mailboxId = mbox.getId();
            mbox.deleteMailbox();
        }
        
        String idString = (mbox == null) ?
            "<no mailbox for account " + accountId + ">" : Integer.toString(mailboxId);
        ZmailLog.security.info(ZmailLog.encodeAttrs(
            new String[] {"cmd", "DeleteMailbox","id", idString}));
        
        Element response = zsc.createElement(AdminConstants.DELETE_MAILBOX_RESPONSE);
        if (mbox != null)
            response.addElement(AdminConstants.E_MAILBOX)
            .addAttribute(AdminConstants.A_MAILBOXID, mailboxId);
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_deleteAccount);
    }
}
