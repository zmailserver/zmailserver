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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxUpgrade;
import org.zmail.cs.mailbox.MigrateToDocuments;
import org.zmail.cs.service.mail.CalendarUtils;
import org.zmail.soap.ZmailSoapContext;

public class MigrateAccount extends AdminDocumentHandler {

    private static final String[] TARGET_ACCOUNT_PATH = new String[] { AdminConstants.E_MIGRATE, AdminConstants.A_ID };

    @Override
    protected String[] getProxiedAccountPath() {
        return TARGET_ACCOUNT_PATH;
    }

    private static enum Action {
        bug72174,
        bug78254,
        contactGroup,
        wiki;
        
        private static Action fromString(String str) throws ServiceException{
            try {
                return Action.valueOf(str);
            } catch (IllegalArgumentException e) {
                throw ServiceException.INVALID_REQUEST("invalid action " + str, e);
            }
        }
    };
    
    @Override
    public boolean domainAuthSufficient(Map<String, Object> context) {
        return false;
    }
    
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Element migrate = request.getElement(AdminConstants.E_MIGRATE);
        Action action = Action.fromString(migrate.getAttribute(AdminConstants.A_ACTION));
        
        String id = migrate.getAttribute(AdminConstants.A_ID);
        
        Provisioning prov = Provisioning.getInstance();
        Account account = prov.getAccountById(id);
        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(id);
        
        // perhaps create new right for migrateAccount action
        checkAdminLoginAsRight(zsc, prov, account);

        switch (action) {
            case contactGroup:
                migrateContactGroup(account);
                break;
            case wiki:
                migrateWiki(account);
                break;
            case bug72174:
                migrateCalendar(account);
                break;
            case bug78254:
                migrateFlagsAndTags(account);
                break;
            default: 
                throw ServiceException.INVALID_REQUEST("unsupported action " + action.name(), null);
        }

        return zsc.createElement(AdminConstants.MIGRATE_ACCOUNT_RESPONSE);
    }
    
    private void migrateContactGroup(Account account) throws ServiceException {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account, false);
        if (mbox == null) {
            throw ServiceException.INVALID_REQUEST("no mailbox", null);
        }
        
        MailboxUpgrade.migrateContactGroups(mbox);
    }
    
    private void migrateFlagsAndTags(Account account) throws ServiceException {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account, false);
        if (mbox == null) {
            throw ServiceException.INVALID_REQUEST("no mailbox", null);
        }
        MailboxUpgrade.migrateFlagsAndTags(mbox);
    }
    
    private void migrateWiki(Account account) throws ServiceException {
        MigrateToDocuments toDoc = new MigrateToDocuments();
        toDoc.handleAccount(account);
    }
    
    private void migrateCalendar(Account account) throws ServiceException {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account, false);
        if (mbox == null) {
            throw ServiceException.INVALID_REQUEST("no mailbox", null);
        }
        
        CalendarUtils.migrateAppointmentsAndTasks(mbox);
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_adminLoginAs);
    }

}
