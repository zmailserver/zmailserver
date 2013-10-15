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
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.account.offline.OfflineAccount;
import org.zmail.cs.account.offline.OfflineProvisioning;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.OfflineMailboxManager;
import org.zmail.cs.offline.OfflineSyncManager;
import org.zmail.cs.offline.common.OfflineConstants;
import org.zmail.cs.service.admin.DeleteMailbox;
import org.zmail.soap.ZmailSoapContext;

public class OfflineDeleteMailbox extends DeleteMailbox {

    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);

        Element mreq = request.getElement(AdminConstants.E_MAILBOX);
        String accountId = mreq.getAttribute(AdminConstants.A_ACCOUNTID);
        
        OfflineProvisioning prov = OfflineProvisioning.getOfflineInstance();
        Account account = prov.get(AccountBy.id, accountId, zsc.getAuthToken());
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

        //test if the mbox is fetchable; if it is not (e.g. due to corrupt db files) then we want to force-remove it from mgr so next req makes new db files. 
        try {
            MailboxManager.getInstance().getMailboxByAccountId(accountId, false);
            return super.handle(request, context);
        }
        catch (Exception e) {
            ZmailLog.account.warn("DeleteMailbox: failed to retrieve mailbox due to exception ",e);
            OfflineMailboxManager omgr = (OfflineMailboxManager) MailboxManager.getInstance();
            omgr.purgeBadMailboxByAccountId(accountId);
            if (account instanceof OfflineAccount) {
                ((OfflineAccount) account).setDisabledDueToError(false);
                OfflineSyncManager.getInstance().clearErrorCode(account);
            }
            Map<String,Object> attrs = account.getAttrs();
            attrs.remove(OfflineConstants.A_offlineSyncStatusErrorCode);
            attrs.remove(OfflineConstants.A_offlineSyncStatusErrorMsg);
            attrs.remove(OfflineConstants.A_offlineSyncStatusException);
            account.setAttrs(attrs);
        }
        
        String idString = "<no mailbox for account " + accountId + ">";
        ZmailLog.security.info(ZmailLog.encodeAttrs(
            new String[] {"cmd", "DeleteMailbox","id", idString}));
        
        Element response = zsc.createElement(AdminConstants.DELETE_MAILBOX_RESPONSE);
        return response;
    }

}
