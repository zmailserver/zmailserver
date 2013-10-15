/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
 * Created on Jul 30, 2010
 */
package org.zmail.cs.service.offline;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.account.offline.OfflineAccount;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.OfflineMailboxManager;
import org.zmail.cs.offline.OfflineLog;
import org.zmail.cs.service.admin.DeleteAccount;
import org.zmail.soap.ZmailSoapContext;

public class OfflineDeleteAccount extends DeleteAccount {

    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        String id = request.getAttribute(AdminConstants.E_ID);

        // Confirm that the account exists and that the mailbox is located
        // on the current host
        Account account = prov.get(AccountBy.id, id, zsc.getAuthToken());
        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(id);

        OfflineLog.offline.debug("delete account request received for acct %s",account.getName());
        checkAccountRight(zsc, account, Admin.R_deleteAccount);        
        if (account instanceof OfflineAccount)
        {
            if (((OfflineAccount)account).isDisabledDueToError()) {
                OfflineLog.offline.debug("deleting bad mailbox");
                OfflineMailboxManager omgr = (OfflineMailboxManager) MailboxManager.getInstance();
                omgr.purgeBadMailboxByAccountId(account.getId());
            } else {
                Mailbox mbox = Provisioning.onLocalServer(account) ? 
                        MailboxManager.getInstance().getMailboxByAccount(account, false) : null;
                if (mbox != null) {
                    OfflineLog.offline.debug("deleting mailbox");
                    mbox.deleteMailbox();
                }
            }
        }
        prov.deleteAccount(id);
        ZmailLog.security.info(ZmailLog.encodeAttrs(
            new String[] {"cmd", "DeleteAccount","name", account.getName(), "id", account.getId()}));

        Element response = zsc.createElement(AdminConstants.DELETE_ACCOUNT_RESPONSE);
        return response;
    }

}
