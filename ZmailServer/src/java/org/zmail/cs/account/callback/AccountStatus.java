/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013 VMware, Inc.
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

package org.zmail.cs.account.callback;

import java.util.Map;
import java.util.Set;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;

public class AccountStatus extends AttributeCallback {

    /**
     * disable mail delivery if account status is changed to closed
     * reset lockout attributes if account status is changed to active
     */
    @SuppressWarnings("unchecked")
    @Override
    public void preModify(CallbackContext context, String attrName, Object value,
            Map attrsToModify, Entry entry)
    throws ServiceException {

        String status;

        SingleValueMod mod = singleValueMod(attrName, value);
        if (mod.unsetting())
            throw ServiceException.INVALID_REQUEST(Provisioning.A_zmailAccountStatus+" is a required attribute", null);
        else
            status = mod.value();

        if (status.equals(Provisioning.ACCOUNT_STATUS_CLOSED) || status.equals(Provisioning.ACCOUNT_STATUS_PENDING)) {
            attrsToModify.put(Provisioning.A_zmailMailStatus, Provisioning.MAIL_STATUS_DISABLED);
        } else if (attrsToModify.get(Provisioning.A_zmailMailStatus) == null) {
            // the request is not also changing zmailMailStatus, set = zmailMailStatus to enabled
            attrsToModify.put(Provisioning.A_zmailMailStatus, Provisioning.MAIL_STATUS_ENABLED);
        }

        if ((entry instanceof Account) && (status.equals(Provisioning.ACCOUNT_STATUS_ACTIVE))) {
            if (entry.getAttr(Provisioning.A_zmailPasswordLockoutFailureTime, null) != null)
                attrsToModify.put(Provisioning.A_zmailPasswordLockoutFailureTime, "");
            if (entry.getAttr(Provisioning.A_zmailPasswordLockoutLockedTime, null) != null)
                attrsToModify.put(Provisioning.A_zmailPasswordLockoutLockedTime, "");
        }
    }

    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {

        if (context.isDoneAndSetIfNot(AccountStatus.class)) {
            return;
        }

        if (!context.isCreate()) {
            if (entry instanceof Account) {
                try {
                    handleAccountStatusClosed((Account)entry);
                } catch (ServiceException se) {
                    // all exceptions are already swallowed by LdapProvisioning, just to be safe here.
                    ZmailLog.account.warn("unable to remove account address and aliases from all DLs for closed account", se);
                    return;
                }
            }
        }
    }

    private void handleAccountStatusClosed(Account account)  throws ServiceException {
        Provisioning prov = Provisioning.getInstance();
        String status = account.getAccountStatus(prov);

        if (status.equals(Provisioning.ACCOUNT_STATUS_CLOSED)) {
            ZmailLog.misc.info("removing account address and all its aliases from all distribution lists");

            String[] addrToRemove = new String[] {account.getName()};

            Set<String> dlIds = prov.getDistributionLists(account);
            for (String dlId : dlIds) {
                DistributionList dl = prov.get(Key.DistributionListBy.id, dlId);
                if (dl != null) {
                    try {
                        // will remove all members that are aliases of the account too
                        prov.removeMembers(dl, addrToRemove);
                    } catch (ServiceException se) {
                        if (AccountServiceException.NO_SUCH_MEMBER.equals(se.getCode())) {
                            ZmailLog.misc.debug("Member not found in dlist; skipping", se);
                        } else {
                            throw se;
                        }
                    }
                }
            }
        }
    }


}
