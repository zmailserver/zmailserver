/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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
package org.zmail.cs.account.accesscontrol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Provisioning;

/*
 * aux class for collecting ACLs on all groups the perspective target entry
 * is a direct/indirect member of. 
 */

public class GroupACLs {
    private NamedEntry target;
    
    // Set of zmailId of groups the account target is a direct member of
    private Set<String> directGroupsOfAccountTarget;
    
    private Set<ZmailACE> aclsOnGroupTargetsAllowedNotDelegable = new HashSet<ZmailACE>();
    private Set<ZmailACE> aclsOnGroupTargetsAllowedDelegable = new HashSet<ZmailACE>();
    private Set<ZmailACE> aclsOnGroupTargetsDenied = new HashSet<ZmailACE>();
    
    GroupACLs(Entry target) throws ServiceException {
        if (target instanceof Account) {
            Account acctTarget = (Account)target;
            this.target = acctTarget;
            directGroupsOfAccountTarget = Provisioning.getInstance().getDirectDistributionLists(acctTarget);
        } else if (target instanceof  DistributionList) {
            DistributionList groupTarget = (DistributionList)target;
            this.target = groupTarget;
        } else {
            throw ServiceException.FAILURE("internal error", null);
        }
    }
    
    private boolean applies(Group grantedOn, ZmailACE ace) {
        if (!ace.disinheritSubGroups()) {
            return true;
        }
        
        /*
         * grant does not apply to sub groups
         */
        if (target instanceof Account) {
            return directGroupsOfAccountTarget.contains(grantedOn.getId());
        } else {
            // DistributionList
            return target.getId().equals(grantedOn.getId());
        }
    }
    
    void collectACL(Group grantedOn, boolean skipPositiveGrants) 
    throws ServiceException {
        
        Set<ZmailACE> allowedNotDelegable = ACLUtil.getAllowedNotDelegableACEs(grantedOn);
        Set<ZmailACE> allowedDelegable = ACLUtil.getAllowedDelegableACEs(grantedOn);
        Set<ZmailACE> denied = ACLUtil.getDeniedACEs(grantedOn);
        
        if (allowedNotDelegable != null && !skipPositiveGrants) {
            for (ZmailACE ace : allowedNotDelegable) {
                if (applies(grantedOn, ace)) {
                    aclsOnGroupTargetsAllowedNotDelegable.add(ace);
                }
            }
        }
        
        if (allowedDelegable != null && !skipPositiveGrants) {
            for (ZmailACE ace : allowedDelegable) {
                if (applies(grantedOn, ace)) {
                    aclsOnGroupTargetsAllowedDelegable.add(ace);
                }
            }
        }
        
        if (denied != null) {
            for (ZmailACE ace : denied) {
                if (applies(grantedOn, ace)) {
                    aclsOnGroupTargetsDenied.add(ace);
                }
            }
        }
    }
    
    /*
     * put all denied and allowed grants into one list, as if they are granted 
     * on the same entry.   We put denied in the front, followed by allowed and 
     * delegable, followed by allowed but not delegable, so it is consistent with 
     * ZmailACL.getAllACEs
     */
    List<ZmailACE> getAllACLs() {
        if (!aclsOnGroupTargetsAllowedNotDelegable.isEmpty() ||
            !aclsOnGroupTargetsAllowedDelegable.isEmpty() ||   
            !aclsOnGroupTargetsDenied.isEmpty()) {
                
            List<ZmailACE> aclsOnGroupTargets = new ArrayList<ZmailACE>();
            aclsOnGroupTargets.addAll(aclsOnGroupTargetsDenied);
            aclsOnGroupTargets.addAll(aclsOnGroupTargetsAllowedDelegable);
            aclsOnGroupTargets.addAll(aclsOnGroupTargetsAllowedNotDelegable);
                
            return aclsOnGroupTargets;
        } else {
            return null;
        }
    }
}
