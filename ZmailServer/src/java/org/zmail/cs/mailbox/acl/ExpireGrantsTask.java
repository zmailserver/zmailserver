/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.mailbox.acl;

import com.google.common.collect.Sets;
import org.zmail.client.ZMailbox;
import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.GuestAccount;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.ACL;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.ScheduledTask;
import org.zmail.cs.mailbox.ScheduledTaskManager;
import org.zmail.cs.service.AuthProvider;
import org.zmail.cs.util.AccountUtil;
import org.zmail.soap.mail.message.SendShareNotificationRequest;
import org.zmail.soap.mail.type.EmailAddrInfo;
import org.zmail.soap.type.Id;

import java.util.List;

public class ExpireGrantsTask extends ScheduledTask {

    static final String TASK_NAME_PREFIX = "expireGrantsTask";
    static final String ITEM_ID_PROP_NAME = "itemId";

    /**
     * Returns the task name.
     */
    @Override
    public String getName() {
        return getTaskName(getProperty(ITEM_ID_PROP_NAME));
    }

    private static String getTaskName(String itemIdStr) {
        return TASK_NAME_PREFIX + itemIdStr;
    }

    private static String getTaskName(int itemId) {
        return getTaskName(Integer.toString(itemId));
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return returns the item for which this task was run if we may want to schedule another instance of this
     *         task (at the next grant expiry); o/w null
     * @throws Exception if unable to compute a result
     * @see ExpireGrantsTaskCallback
     */
    public MailItem call() throws Exception {
        int itemId = Integer.valueOf(getProperty(ITEM_ID_PROP_NAME));
        Mailbox mbox = MailboxManager.getInstance().getMailboxById(getMailboxId());
        MailItem item;
        try {
            item = mbox.getItemById(null, itemId, MailItem.Type.UNKNOWN);
        } catch (MailServiceException.NoSuchItemException e) {
            // item seems to have been deleted; no problem
            return null;
        }
        ACL acl = item.getACL();
        if (acl == null) {
            return null;
        }
        ZMailbox zMbox = getZMailbox(mbox);
        List<ACL.Grant> grants = acl.getGrants();
        long now = System.currentTimeMillis();
        boolean aGrantWithExpiryExists = false;
        for (ACL.Grant grant : grants) {
            long expiry = grant.getEffectiveExpiry(acl);
            if (expiry == 0) {
                continue;
            }
            aGrantWithExpiryExists = true;
            if (now > expiry) {
                String granteeId;
                switch (grant.getGranteeType()) {
                    case ACL.GRANTEE_PUBLIC:
                        granteeId = GuestAccount.GUID_PUBLIC;
                        break;
                    case ACL.GRANTEE_AUTHUSER:
                        granteeId = GuestAccount.GUID_AUTHUSER;
                        break;
                    default:
                        granteeId = grant.getGranteeId();
                }
                try {
                    String address = getGranteeAddress(grant);
                    if (address != null) {
                        sendGrantExpiryNotification(zMbox, itemId, address);
                    }
                } finally {
                    mbox.revokeAccess(null, true, itemId, granteeId);
                }
            }
        }
        return aGrantWithExpiryExists ? item : null;
    }

    private static void sendGrantExpiryNotification(ZMailbox zMbox, int itemId, String address)
            throws ServiceException {
        SendShareNotificationRequest req = new SendShareNotificationRequest();
        req.setEmailAddresses(Sets.newHashSet(new EmailAddrInfo(address)));
        req.setAction(SendShareNotificationRequest.Action.expire);
        req.setItem(new Id(Integer.toString(itemId)));
        zMbox.invokeJaxb(req);
    }

    private static String getGranteeAddress(ACL.Grant grant) throws ServiceException {
        switch (grant.getGranteeType()) {
            case ACL.GRANTEE_USER:
                Account granteeAcct = Provisioning.getInstance().get(Key.AccountBy.id, grant.getGranteeId());
                if (granteeAcct != null) {
                    return granteeAcct.getName();
                }
                break;
            case ACL.GRANTEE_GUEST:
                return grant.getGranteeId();
            default:
                return null;
        }
        return null;
    }

    private static ZMailbox getZMailbox(Mailbox mbox) throws ServiceException {
        Account account = mbox.getAccount();
        ZMailbox.Options options = new ZMailbox.Options();
        options.setNoSession(true);
        options.setAuthToken(AuthProvider.getAuthToken(account).toZAuthToken());
        options.setUri(AccountUtil.getSoapUri(account));
        return new ZMailbox(options);
    }

    static void cancel(int mailboxId, int itemId) throws ServiceException {
        ScheduledTaskManager.cancel(ExpireGrantsTask.class.getName(), getTaskName(itemId), mailboxId, false);
    }
}
