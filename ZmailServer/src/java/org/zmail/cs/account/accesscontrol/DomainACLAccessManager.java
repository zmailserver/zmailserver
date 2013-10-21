/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.account.accesscontrol;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.DomainAccessManager;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.GuestAccount;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.Rights.User;

/*
 * - domain based access manager
 * - support user ACL only, not admin rights.
 */
public class DomainACLAccessManager extends DomainAccessManager {
    
    public DomainACLAccessManager() throws ServiceException {
        // initialize RightManager
        RightManager.getInstance();
    }
    
    public boolean canAccessAccount(AuthToken at, Account target, boolean asAdmin) throws ServiceException {
        if (super.canAccessAccount(at, target, asAdmin))
            return true;
        else
            return canDo(at, target, User.R_loginAs, asAdmin, false);
    }
    
    public boolean canAccessAccount(AuthToken at, Account target) throws ServiceException {
        return canAccessAccount(at, target, true);
    }
    
    public boolean canAccessAccount(Account credentials, Account target, boolean asAdmin) throws ServiceException {
        if (super.canAccessAccount(credentials, target, asAdmin))
            return true;
        else
            return canDo(credentials, target, User.R_loginAs, asAdmin, false);
    }
    
    public boolean canAccessAccount(Account credentials, Account target) throws ServiceException {
        return canAccessAccount(credentials, target, true);
    }
    
    public boolean canDo(Account grantee, Entry target, Right rightNeeded, boolean asAdmin, boolean defaultGrant) {
        try {
            if (grantee == null)
                grantee = GuestAccount.ANONYMOUS_ACCT;

            // 1. always allow self
            if (target instanceof Account) {
                if (((Account)target).getId().equals(grantee.getId()))
                    return true;
            }
            
            // 2. check admin access - if the right being asked for is not loginAs
            if (rightNeeded != Rights.User.R_loginAs) {
                if (target instanceof Account) {
                    if (canAccessAccount(grantee, (Account)target, asAdmin))
                        return true;
                }
            }
            
            // 3. check ACL
            Boolean result = CheckPresetRight.check(grantee, target, rightNeeded, false, null); 
            if (result != null)
                return result.booleanValue();
            else {
                // no ACL, see if there is a configured default 
                Boolean defaultValue = rightNeeded.getDefault();
                if (defaultValue != null)
                    return defaultValue.booleanValue();
                
                // no configured default, return default requested by the callsite
                return defaultGrant;
            }
                
        } catch (ServiceException e) {
            ZmailLog.account.warn("ACL checking failed: " + 
                                   "grantee=" + grantee.getName() + 
                                   ", target=" + target.getLabel() + 
                                   ", right=" + rightNeeded.getName() + 
                                   " => denied", e);
        }
        return false;
    }
    
    public boolean canDo(AuthToken grantee, Entry target, Right rightNeeded, boolean asAdmin, boolean defaultGrant) {
        try {
            Account granteeAcct;
            if (grantee == null)
                granteeAcct = GuestAccount.ANONYMOUS_ACCT;
            else if (grantee.isZmailUser())
                granteeAcct = Provisioning.getInstance().get(Key.AccountBy.id, grantee.getAccountId());
            else
                granteeAcct = new GuestAccount(grantee);
            
            return canDo(granteeAcct, target, rightNeeded, asAdmin, defaultGrant);
        } catch (ServiceException e) {
            ZmailLog.account.warn("ACL checking failed: " +
                                   "grantee=" + grantee.getAccountId() +
                                   ", target=" + target.getLabel() +
                                   ", right=" + rightNeeded.getName() +
                                   " => denied", e);
        }
        
        return false;
    }

    public boolean canDo(String granteeEmail, Entry target, Right rightNeeded, boolean asAdmin, boolean defaultGrant) {
        try {
            Account granteeAcct = null;
            
            if (granteeEmail != null)
                granteeAcct = Provisioning.getInstance().get(Key.AccountBy.name, granteeEmail);
            if (granteeAcct == null)
                granteeAcct = GuestAccount.ANONYMOUS_ACCT;
            
            return canDo(granteeAcct, target, rightNeeded, asAdmin, defaultGrant);
        } catch (ServiceException e) {
            ZmailLog.account.warn("ACL checking failed: " + 
                                   "grantee=" + granteeEmail + 
                                   ", target=" + target.getLabel() + 
                                   ", right=" + rightNeeded.getName() + 
                                   " => denied", e);
        }
        
        return false;
    }


}
