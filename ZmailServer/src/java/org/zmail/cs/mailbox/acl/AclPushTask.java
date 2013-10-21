/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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
package org.zmail.cs.mailbox.acl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import com.google.common.collect.Multimap;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.ShareInfoData;
import org.zmail.cs.db.DbPendingAclPush;
import org.zmail.cs.mailbox.ACL;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.util.ZmailApplication;

/**
 * This task publishes shared item updates to LDAP to enable centralized discovery of shares, e.g.
 * to discover all shares accessible to a particular account.
 */
public class AclPushTask extends TimerTask {

    private static boolean supported;

    static {
         supported = ZmailApplication.getInstance().supports(AclPushTask.class);
    }

    @Override
    public void run() {
        doWork();
    }

    public static synchronized void doWork() {
        if (!supported)
            return;
        ZmailLog.misc.debug("Starting pending ACL push");
        try {
            Date now = new Date();
            Multimap<Integer, Integer> mboxIdToItemIds = DbPendingAclPush.getEntries(now);

            for (int mboxId : mboxIdToItemIds.keySet()) {
                Mailbox mbox;
                try {
                    mbox = MailboxManager.getInstance().getMailboxById(mboxId);
                } catch (ServiceException e) {
                    ZmailLog.misc.info("Exception occurred while getting mailbox for id %s during ACL push", mboxId, e);
                    continue;
                }
                Collection<Integer> itemIds = mboxIdToItemIds.get(mboxId);
                MailItem[] items = null;
                try {
                    items = mbox.getItemById(null, itemIds, MailItem.Type.UNKNOWN);
                } catch (MailServiceException.NoSuchItemException e) {
                    // one or more folders no longer exist
                    if (itemIds.size() > 1) {
                        List<MailItem> itemList = new ArrayList<MailItem>();
                        for (int itemId : itemIds) {
                            try {
                                itemList.add(mbox.getItemById(null, itemId, MailItem.Type.UNKNOWN));
                            } catch (MailServiceException.NoSuchItemException ignored) {
                            }
                        }
                        items = itemList.toArray(new MailItem[itemList.size()]);
                    }
                }

                Account account = mbox.getAccount();
                String[] existingSharedItems = account.getSharedItem();
                Set<String> updatedSharedItems = new HashSet<String>();

                for (String sharedItem : existingSharedItems) {
                    ShareInfoData shareData = AclPushSerializer.deserialize(sharedItem);
                    if (!itemIds.contains(shareData.getItemId())) {
                        updatedSharedItems.add(sharedItem);
                    }
                }

                if (items != null) {
                    for (MailItem item : items) {
                        if (item == null) {
                            continue;
                        }
                        // for now push the Folder grants to LDAP
                        if (!(item instanceof Folder)) {
                            continue;
                        }
                        ACL acl = item.getACL();
                        if (acl == null) {
                            continue;
                        }
                        for (ACL.Grant grant : acl.getGrants()) {
                            updatedSharedItems.add(AclPushSerializer.serialize(item, grant));
                        }
                    }
                }

                account.setSharedItem(updatedSharedItems.toArray(new String[updatedSharedItems.size()]));
            }

            DbPendingAclPush.deleteEntries(now);
        } catch (Throwable t) {  //don't let exceptions kill the timer
            ZmailLog.misc.warn("Error during ACL push task", t);
        }
        ZmailLog.misc.debug("Finished pending ACL push");
    }
}
