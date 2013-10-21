/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.cs.account;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.index.SortBy;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.mailbox.ACL;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.Mountpoint;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import static org.zmail.common.account.ZAttrProvisioning.AccountStatus;

/**
 * This task:
 * - disables an external virtual account when all its accessible shares have been revoked or expired.
 * - deletes an external virtual account after XXX of being disabled.
 */
public class ExternalAccountManagerTask extends TimerTask {

    @Override
    public void run() {
        ZmailLog.misc.info("Starting external virtual account status manager task");

        Provisioning prov = Provisioning.getInstance();
        SearchAccountsOptions searchOpts = new SearchAccountsOptions();
        try {
            searchOpts.setFilter(ZLdapFilterFactory.getInstance().externalAccountsHomedOnServer(
                    prov.getLocalServer().getServiceHostname()));
            List<NamedEntry> accounts = prov.searchDirectory(searchOpts);

            for (NamedEntry ne : accounts) {
                Account account = (Account) ne;
                Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account);
                if (mbox == null) {
                    ZmailLog.misc.error(
                            "Mailbox for external virtual account %s is unexpectedly null", account.getId());
                    continue;
                }
                List<Folder> folders = mbox.getFolderList(null, SortBy.NONE);
                List<Mountpoint> mountpoints = new LinkedList<Mountpoint>();
                for (Folder folder : folders) {
                    if (folder instanceof Mountpoint) {
                        mountpoints.add((Mountpoint) folder);
                    }
                }
                if (mountpoints.isEmpty()) {
                    disableOrDeleteAccount(prov, account, mbox);
                    continue;
                }
                // Get all shares granted to the external user 
                ShareInfoVisitor shareInfoVisitor = new ShareInfoVisitor(mountpoints);
                ShareInfo.Published.get(prov, account, ACL.GRANTEE_GUEST, null, shareInfoVisitor);
                boolean hasValidMountpoint = shareInfoVisitor.getResult();
                
                if (!hasValidMountpoint) {
                    disableOrDeleteAccount(prov, account, mbox);
                } else if (account.getAccountStatus() == AccountStatus.closed) {
                    // re-enable account
                    account.setAccountStatus(AccountStatus.active);
                }
            }
        } catch (ServiceException e) {
            ZmailLog.misc.warn("Error during external virtual account status manager task run", e);
        }
        ZmailLog.misc.info("Finished external virtual account status manager task");
    }

    private static void disableOrDeleteAccount(Provisioning prov, Account account, Mailbox mbox)
            throws ServiceException {
        AccountStatus accountStatus = account.getAccountStatus();
        if (accountStatus == AccountStatus.active) {
            account.setExternalAccountDisabledTime(new Date());
            account.setAccountStatus(AccountStatus.closed);
        } else {
            long disabledAcctLifetime = account.getExternalAccountLifetimeAfterDisabled();
            if (accountStatus == AccountStatus.closed && disabledAcctLifetime != 0) {
                Date timeWhenDisabled = account.getExternalAccountDisabledTime();
                if (timeWhenDisabled != null && 
                        System.currentTimeMillis() - timeWhenDisabled.getTime() > disabledAcctLifetime) {
                    mbox.deleteMailbox();
                    prov.deleteAccount(account.getId());
                }
            }
        }
    }
    
    private static class ShareInfoVisitor implements Provisioning.PublishedShareInfoVisitor {
        
        private List<Mountpoint> mountpoints;
        private boolean result = false;
        
        public ShareInfoVisitor(List<Mountpoint> mountpoints) {
            this.mountpoints = mountpoints;
        }

        @Override
        public void visit(ShareInfoData shareInfoData) throws ServiceException {
            for (Mountpoint mountpoint : mountpoints) {
                if (mountpoint.getOwnerId().equals(shareInfoData.getOwnerAcctId()) &&
                        mountpoint.getRemoteId() == shareInfoData.getItemId()) {
                    result = true;
                    break;
                }
            }
        }
        
        public boolean getResult() {
            return result;
        }
    }
}
