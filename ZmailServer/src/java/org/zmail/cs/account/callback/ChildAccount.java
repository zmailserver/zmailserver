/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
 
public class ChildAccount extends AttributeCallback {
    
    @Override
    public void preModify(CallbackContext context, String attrName, Object value,
            Map attrsToModify, Entry entry) 
    throws ServiceException {

        /*
         * This callback is for both zmailPrefChildVisibleAccount and zmailChildAccount, and it handles
         * both in one shot.  If we've been called just return.
         */ 
        if (context.isDoneAndSetIfNot(ChildAccount.class)) {
            return;
        }
        
        // the +/- has been striped off from attrName but we need that info, it is in attrsToModify
        
        MultiValueMod visibleChildrenMod = multiValueMod(attrsToModify, Provisioning.A_zmailPrefChildVisibleAccount);
        MultiValueMod allChildrenMod = multiValueMod(attrsToModify, Provisioning.A_zmailChildAccount);

        Set<String> visibleChildren = newValuesToBe(visibleChildrenMod, entry, Provisioning.A_zmailPrefChildVisibleAccount);
        Set<String> allChildren = newValuesToBe(allChildrenMod, entry, Provisioning.A_zmailChildAccount);

        //if child account has already been deleted, let it go
        if (allChildren != null && !allChildren.contains(value)) {
            return;
        }

        if (allChildrenMod != null && allChildrenMod.deleting()) {
            attrsToModify.put(Provisioning.A_zmailPrefChildVisibleAccount, "");
        } else {
            Set<String> vidsToRemove = new HashSet<String>();
            for (String vid : visibleChildren) {
                if (!allChildren.contains(vid)) {
                    /*
                     * if the request is removing children but not updating the visible children, 
                     * we remove the visible children that are no longer a child.
                     * otherwise, throw exception if the mod results into a situation where a 
                     * visible child is not one of the children.
                     */ 
                    if (allChildrenMod!=null && allChildrenMod.removing() && visibleChildrenMod==null)
                        vidsToRemove.add(vid);
                    else
                        throw ServiceException.INVALID_REQUEST("visible child id " + vid + " is not one of " + Provisioning.A_zmailChildAccount, null);
                }
            }

            if (vidsToRemove.size() > 0)
                attrsToModify.put("-" + Provisioning.A_zmailPrefChildVisibleAccount, vidsToRemove.toArray(new String[vidsToRemove.size()]));
        }

        // check circular relationship
        if (entry instanceof Account) {
            Provisioning prov = Provisioning.getInstance();
            Account parentAcct = (Account)entry;
            String parentId = parentAcct.getId();
            for (String childId : allChildren) {
                Account childAcct = prov.get(AccountBy.id, childId);
                if (childAcct == null)
                    throw AccountServiceException.NO_SUCH_ACCOUNT(childId);

                String[] children = childAcct.getChildAccount();
                for (String child : children) {
                    if (child.equals(parentId))
                        throw ServiceException.INVALID_REQUEST(
                                "child account " + childId + "(" + childAcct.getName() + ")"  +
                                " is parent of the parent account " + parentId + "(" + parentAcct.getName() + ")", 
                                null);
                }
            }
        }
    }

    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }
}

