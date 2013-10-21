/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
 * Created on Jun 17, 2004
 */
package org.zmail.cs.service.admin;

import java.util.Map;
import java.util.List;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.ZAttrProvisioning.AccountStatus;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class DeleteAccount extends AdminDocumentHandler {

    private static final String[] TARGET_ACCOUNT_PATH = new String[] { AdminConstants.E_ID };
    protected String[] getProxiedAccountPath()  { return TARGET_ACCOUNT_PATH; }

    /**
     * must be careful and only allow deletes domain admin has access to
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    /**
     * Deletes an account and its mailbox.
     */
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        String id = request.getAttribute(AdminConstants.E_ID);

        // Confirm that the account exists and that the mailbox is located
        // on the current host
        Account account = prov.get(AccountBy.id, id, zsc.getAuthToken());
        if (account == null) {
            throw AccountServiceException.NO_SUCH_ACCOUNT(id);
        }
        
        checkAccountRight(zsc, account, Admin.R_deleteAccount);  
        
        /*
         * bug 69009
         * 
         * We delete the mailbox before deleting the LDAP entry.
         * It's possible that a message delivery or other user action could 
         * cause the mailbox to be recreated between the mailbox delete step 
         * and the LDAP delete step.
         * 
         * To prevent this race condition, put the account in "maintenance" mode 
         * so mail delivery and any user action is blocked.  
         */ 
        prov.modifyAccountStatus(account, AccountStatus.maintenance.name());

        Mailbox mbox = Provisioning.onLocalServer(account) ? 
                MailboxManager.getInstance().getMailboxByAccount(account, false) : null;
                
        if (mbox != null) {
            mbox.deleteMailbox();
        }
        
        prov.deleteAccount(id);
        
        ZmailLog.security.info(ZmailLog.encodeAttrs(
            new String[] {"cmd", "DeleteAccount","name", account.getName(), "id", account.getId()}));

        Element response = zsc.createElement(AdminConstants.DELETE_ACCOUNT_RESPONSE);
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_deleteAccount);
    }
    
}