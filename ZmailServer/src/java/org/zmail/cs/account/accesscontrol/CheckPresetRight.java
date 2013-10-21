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
package org.zmail.cs.account.accesscontrol;

import java.util.List;

import org.zmail.common.account.Key.DomainBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Log;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.AccessManager.ViaGrant;
import org.zmail.cs.account.Provisioning.GroupMembership;
import org.zmail.cs.account.accesscontrol.PermissionCache.CachedPermission;
import org.zmail.cs.account.accesscontrol.Rights.Admin;

/**
 * Check if grantee is allowed for rightNeeded on target entry.
 */ 

public class CheckPresetRight extends CheckRight {
    private static final Log sLog = ZmailLog.acl;

    private Account mGranteeAcct;
    private ViaGrant mVia;
    
    // derived from input or aux vars
    private GroupMembership mGranteeGroups;
    private SeenRight mSeenRight;
    
    private static class SeenRight {
        private boolean mSeen;
        
        void setSeenRight() {
            mSeen = true;
        }
        
        boolean seenRight() {
            return mSeen;
        }
    }
    
    /**
     * Check if grantee is allowed for rightNeeded on target entry.
     * 
     * @param grantee
     * @param target
     * @param rightNeeded
     * @param canDelegateNeeded if we are checking for "can delegate" the right
     * @param via if not null, will be populated with the grant info via which the 
     *        result was determined.
     * @return Boolean.TRUE if allowed, 
     *         Boolean.FALSE if denied, 
     *         null if there is no grant applicable to the rightNeeded.
     * @throws ServiceException
     */
    public static Boolean check(Account grantee, Entry target, 
            Right rightNeeded, boolean canDelegateNeeded, ViaGrant via) 
    throws ServiceException {
        
        CachedPermission cached = PermissionCache.cacheGet(grantee, target, 
                rightNeeded, canDelegateNeeded);
        
        Boolean allowed;
        
        if (cached == CachedPermission.NOT_CACHED) {
            CheckPresetRight checker = new CheckPresetRight(grantee, target, 
                    rightNeeded, canDelegateNeeded, via);
            allowed = checker.checkRight();
            PermissionCache.cachePut(grantee, target, rightNeeded, canDelegateNeeded, allowed);
        } else {
            allowed = cached.getResult();
        }
        
        if (sLog.isDebugEnabled()) {
            sLog.debug("check ACL: " + (allowed==null ? "no matching ACL" : allowed) + 
                    "(target=" + target.getLabel() + ", grantee=" + grantee.getName() + 
                    ", right=" + rightNeeded.getName() + 
                    ", canDelegateNeeded=" + canDelegateNeeded + ")");
        }
        
        return allowed;
    }

    private CheckPresetRight(Account grantee, Entry target, 
            Right rightNeeded, boolean canDelegateNeeded, ViaGrant via) 
    throws ServiceException {
        super(target, rightNeeded, canDelegateNeeded);
        
        mGranteeAcct = grantee;
        mVia = via;
        mTargetType = TargetType.getTargetType(mTarget);
        mSeenRight = new SeenRight();
    }
    
    private GroupMembership getGranteeGroups() throws ServiceException {
        if (mGranteeGroups == null) {
            // get all groups(static and dynamic) the grantee belongs
            // get only admin groups if the right is an admin right
            boolean adminGroupsOnly = !mRightNeeded.isUserRight();
            mGranteeGroups = mProv.getGroupMembership(mGranteeAcct, adminGroupsOnly);
        }
        return mGranteeGroups;
    }
    
    private boolean matchesGroupGrantee(ZmailACE ace) throws ServiceException {
        if (getGranteeGroups().groupIds().contains(ace.getGrantee())) {
            return true;
        } else if (ace.getGranteeType() == GranteeType.GT_EXT_GROUP) {
            return ace.matchesGrantee(mGranteeAcct, !mRightNeeded.isUserRight());
        } else {
            return false;
        }
    }
            
    private Boolean checkRight() throws ServiceException {
        
        if (!mRightNeeded.isPresetRight()) {
            throw ServiceException.INVALID_REQUEST("RightChecker.canDo can only check preset right, right " + 
                    mRightNeeded.getName() + " is a " + mRightNeeded.getRightType() + " right",  null);
        }
        
        boolean adminRight = !mRightNeeded.isUserRight();
        
        Domain granteeDomain = null; 
        
        if (adminRight) {
            // if the grantee is no longer legitimate, e.g. not an admin any more, ignore all his grants
            if (!RightBearer.isValidGranteeForAdminRights(GranteeType.GT_USER, mGranteeAcct)) {
                return null;
            }
            
            granteeDomain = mProv.getDomain(mGranteeAcct);
            // if we ever get here, the grantee must have a domain
            if (granteeDomain == null) {
                throw ServiceException.FAILURE("internal error, cannot find domain for " + 
                        mGranteeAcct.getName(), null);
            }
            
            // should only come from granting/revoking check
            if (mRightNeeded == Admin.R_crossDomainAdmin) {
                return CrossDomain.checkCrossDomainAdminRight(
                        mProv, granteeDomain, mTarget, mCanDelegateNeeded);
            }
        }

        
        Boolean result = null;
        
        // check grants explicitly granted on the target entry
        // we don't return the target entry itself in TargetIterator because if 
        // target is a dl, we need to know if the dl returned from TargetIterator 
        // is the target itself or one of the groups the target is in.  So we check 
        // the actual target separately
        List<ZmailACE> acl = ACLUtil.getAllACEs(mTarget);
        if (acl != null) {
            result = checkTarget(acl, false);
            if (result != null) {
                return result;
            }
        }
        
        //
        // if the target is a domain-ed entry, get the domain of the target.
        // It is needed for checking the cross domain right.
        //
        Domain targetDomain = TargetType.getTargetDomain(mProv, mTarget);
        
        // group target is only supported for admin rights
        boolean expandTargetGroups = CheckRight.allowGroupTarget(mRightNeeded);
        
        // check grants granted on entries from which the target entry can inherit from
        TargetIterator iter = TargetIterator.getTargetIeterator(mProv, mTarget, expandTargetGroups);
        Entry grantedOn;
        
        GroupACLs groupACLs = null;
        
        while ((grantedOn = iter.next()) != null) {
            acl = ACLUtil.getAllACEs(grantedOn);
            
            if (grantedOn instanceof Group) {
                if (acl == null) {
                    continue;
                }
                
                boolean skipPositiveGrants = false;
                if (adminRight) {
                    skipPositiveGrants = !CrossDomain.crossDomainOK(mProv, mGranteeAcct, granteeDomain, 
                        targetDomain, (Group)grantedOn);
                }
                
                // don't check yet, collect all acls on all target groups
                if (groupACLs == null) {
                    groupACLs = new GroupACLs(mTarget);
                }
                groupACLs.collectACL((Group)grantedOn, skipPositiveGrants);
                
            } else {
                // end of group targets, put all collected denied and allowed grants into one 
                // list, as if they are granted on the same entry, then check.  
                // We put denied in the front, so it is consistent with ZmailACL.getAllACEs
                if (groupACLs != null) {
                    List<ZmailACE> aclsOnGroupTargets = groupACLs.getAllACLs();
                    if (aclsOnGroupTargets != null) {
                        result = checkTarget(aclsOnGroupTargets, false);
                    }
                    if (result != null) { 
                        return result;
                    }
                    
                    // set groupACLs to null, we are done with group targets
                    groupACLs = null;
                }
                
                // didn't encounter any group grantedOn, or none of them matches, just check this grantedOn entry
                if (acl == null) {
                    continue;
                }
                
                boolean subDomain = (mTargetType == TargetType.domain && (grantedOn instanceof Domain));
                result = checkTarget(acl, subDomain);
                if (result != null) {
                    return result;
                }
            }
        }
        
        if (mSeenRight.seenRight()) {
            return Boolean.FALSE;
        } else {
            return null;
        }
    }
    
    private Boolean checkTarget(List<ZmailACE> acl, boolean subDomain) 
    throws ServiceException {
        Boolean result = null;
        
        // if the right is user right, checking for individual match will
        // only check for user grantees, if there are any guest or key grantees
        // (there should *not* be any), they are ignored.
        short adminFlag = (mRightNeeded.isUserRight()? 0 : GranteeFlag.F_ADMIN);
        
        // as an individual: user, guest, key
        result = checkPresetRight(acl, (short)(GranteeFlag.F_INDIVIDUAL | adminFlag), subDomain);
        if (result != null) {
            return result;
        }
        
        // as a group member
        result = checkGroupPresetRight(acl, (short)(GranteeFlag.F_GROUP), subDomain);
        if (result != null) {
            return result;
        }
        
        // if right is an user right, check domain, authed users and public grantees
        if (mRightNeeded.isUserRight()) {
            // as an zmail user in the same domain
            result = checkPresetRight(acl, (short)(GranteeFlag.F_DOMAIN), subDomain);
            if (result != null) {
                return result;
            }
            
            // all authed zmail user
            result = checkPresetRight(acl, (short)(GranteeFlag.F_AUTHUSER), subDomain);
            if (result != null) {
                return result;
            }
            
            // public
            result = checkPresetRight(acl, (short)(GranteeFlag.F_PUBLIC), subDomain);
            if (result != null) {
                return result;
            }
        }
        
        return null;
    }
    
    
    /*
     * checks 
     *     - if the grant matches required granteeFlags
     *     - if the granted right is applicable to the entry on which it is granted
     *     - if the grant is for sub domains, if subDomain is true(if the grant is not a negative grant) 
     *     - if the granted right matches the requested right
     *     
     *     Note: if canDelegateNeeded is true but the granted right is not delegable,
     *           we skip the grant (i.e. by returning false) and let the flow continue 
     *           to check other grants, instead of denying the granting attempt.
     *            
     *           That is, a more relevant executable only grant will *not* take away
     *           the grantable property of a less relevant grantable grant.
     *                 
     * subDomain: whether we want the grant to be for sub domains only
     */
    private boolean matchesPresetRight(ZmailACE ace, short granteeFlags, boolean subDomain) 
    throws ServiceException {
        GranteeType granteeType = ace.getGranteeType();
        if (!granteeType.hasFlags(granteeFlags)) {
            return false;
        }
            
        if (!CheckRight.rightApplicableOnTargetType(mTargetType, mRightNeeded, mCanDelegateNeeded)) {
            return false;
        }
        
        if (mCanDelegateNeeded && ace.canExecuteOnly()) {
            return false;
        }
        
        // negative grants are always effective on sub domains
        if (!ace.deny()) {
            if (subDomain != ace.subDomain()) {
                return false;
            }
        }
            
        Right rightGranted = ace.getRight();
        if ((rightGranted.isPresetRight() && rightGranted == mRightNeeded) ||
             rightGranted.isComboRight() && ((ComboRight)rightGranted).containsPresetRight(mRightNeeded)) {
            return true;
        }
        
        return false;
    }

    
    /**
     * go through each grant in the ACL
     *     - checks if the right/target of the grant matches the right/target of the grant
     *       and 
     *       if the grantee type(specified by granteeFlags) is one of the grantee type 
     *       we are interested in this call.
     *       
     *     - if so marks the right "seen" (so callsite default(only used by user right callsites) 
     *       won't be honored)
     *     - check if the Account (the grantee parameter) matches the grantee of the grant
     *       
     * @param acl
     * @param granteeFlags For admin rights, because of negative grants and the more "specific" 
     *                     grantee takes precedence over the less "specific" grantee, we can't 
     *                     just do a single ZmailACE.match to see if a grantee matches the grant.
     *                     Instead, we need to check more specific grantee types first, then 
     *                     go on the the less specific ones.  granteeFlags specifies the 
     *                     grantee type(s) we are checking for this call.
     *                     e.g. an ACL has:
     *                              adminA deny  rightR  - grant1
     *                              groupG allow rightR  - grant2
     *                              and adminA is in groupG, we want to check grant1 before grant2.
     *                                       
     * @return
     * @throws ServiceException
     */
    private Boolean checkPresetRight(List<ZmailACE> acl, short granteeFlags, boolean subDomain) 
    throws ServiceException {
        Boolean result = null;
        for (ZmailACE ace : acl) {
            if (!matchesPresetRight(ace, granteeFlags, subDomain)) {
                continue;
            }
            
            // if we get here, the right matched, mark it in seenRight.  
            // This is so callsite default will not be honored.
            mSeenRight.setSeenRight();
                
            if (ace.matchesGrantee(mGranteeAcct, !mRightNeeded.isUserRight())) {
                return gotResult(ace);
            }
        }
       
        return result;
    }
    
    /*
     * Like checkPresetRight, but checks group grantees.  Instead of calling ZmailACE.match, 
     * which checks group grants using inDistributionList, we do it the other way around 
     * by obtaining a GroupMembership object that contains all the groups the account is 
     * in that are "eligible"(for adming rights, the group's admin flag must be on) for 
     * the grant.   We check if the grantee of the grant is one of the eligible groups the 
     * account is in.  If the grantee on the zmailACE is an external group, check if 
     * the account is in the external group. 
     *
     * Eligible:
     *   - for admin rights granted to a group, the grant is effective only if the group 
     *     has zmailIsAdminGroup=TRUE.  If the group's zmailIsAdminGroup is set to false 
     *     after a grant is made, the grant is still there on the target entry, but 
     *     becomes useless.
     */
    private Boolean checkGroupPresetRight(List<ZmailACE> acl, short granteeFlags, boolean subDomain) 
    throws ServiceException {
        Boolean result = null;
        
        for (ZmailACE ace : acl) {
            if (!matchesPresetRight(ace, granteeFlags, subDomain)) {
                continue;
            }
            
            // if we get here, the right matched, mark it in seenRight.  
            // This is so callsite default will not be honored.
            mSeenRight.setSeenRight();
            
            if (matchesGroupGrantee(ace)) {
                return gotResult(ace);
            }
        }
        return result;
    }
    
    private Boolean gotResult(ZmailACE ace) throws ServiceException {
        if (ace.deny()) {
            if (sLog.isDebugEnabled()) {
                sLog.debug("Right " + "[" + mRightNeeded.getName() + "]" + 
                        " DENIED to " + mGranteeAcct.getName() + 
                        " via grant: " + ace.dump(false) + " on: " + 
                        ace.getTargetType().getCode() + ace.getTargetName());
            }
            
            if (mVia != null) {
                mVia.setImpl(new ViaGrantImpl(ace.getTargetType(),
                                              ace.getTargetName(),
                                              ace.getGranteeType(),
                                              ace.getGranteeDisplayName(),
                                              ace.getRight(),
                                              ace.deny()));
            }
            
            return Boolean.FALSE;
        } else {
            if (sLog.isDebugEnabled()) {
                sLog.debug("Right " + "[" + mRightNeeded.getName() + "]" + 
                        " ALLOWED to " + mGranteeAcct.getName() + 
                        " via grant: " + ace.dump(false) + " on: " + 
                        ace.getTargetType().getCode() + ace.getTargetName());
            }
            
            if (mVia != null) {
                mVia.setImpl(new ViaGrantImpl(ace.getTargetType(),
                                              ace.getTargetName(),
                                              ace.getGranteeType(),
                                              ace.getGranteeDisplayName(),
                                              ace.getRight(),
                                              ace.deny()));
            }
            
            return Boolean.TRUE;
        }
    }
}
