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

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Identity;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.generated.UserRights;

public final class ACLUtil {
    private static final String ACL_CACHE_KEY = "ENTRY.ACL_CACHE";

    private ACLUtil() {
    }

    /**
     * Returns all ACEs granted on the entry.
     *
     * @param entry the entry on which rights are granted
     * @return all ACEs granted on the entry.
     */
    public static List<ZmailACE> getAllACEs(Entry entry) throws ServiceException {
        ZmailACL acl = getACL(entry);
        return acl != null ? acl.getAllACEs() : null;
    }

    public static Set<ZmailACE> getAllowedNotDelegableACEs(Entry entry) 
    throws ServiceException {
        ZmailACL acl = getACL(entry);
        return acl != null ? acl.getAllowedNotDelegableACEs() : null;
    }

    public static Set<ZmailACE> getAllowedDelegableACEs(Entry entry) 
    throws ServiceException {
        ZmailACL acl = getACL(entry);
        return acl != null ? acl.getAllowedDelegableACEs() : null;
    }

    public static Set<ZmailACE> getDeniedACEs(Entry entry) throws 
    ServiceException {
        ZmailACL acl = getACL(entry);
        return acl != null ? acl.getDeniedACEs() : null;
    }

    /**
     * Returns a Set of ACEs with the specified rights granted on the entry.
     *
     * @param entry the entry on which rights are granted
     * @param rights rights of interest
     * @return a Set of ACEs with the specified rights granted on the entry.
     */
    public static List<ZmailACE> getACEs(Entry entry, Set<? extends Right> rights) 
    throws ServiceException {
        ZmailACL acl = getACL(entry);
        return acl != null ? acl.getACEs(rights) : null;
    }

    private static Multimap<Right, Entry> getGrantedRights(Account grantee, Set<String> fetchAttrs)
            throws ServiceException {
        SearchGrants search = new SearchGrants(grantee.getProvisioning(), EnumSet.of(TargetType.account),
                new RightBearer.Grantee(grantee, false).getIdAndGroupIds());
        search.addFetchAttribute(fetchAttrs);
        Set<SearchGrants.GrantsOnTarget> results = search.doSearch().getResults();
        Multimap<Right, Entry> map = HashMultimap.create();
        for (SearchGrants.GrantsOnTarget grants : results) {
            ZmailACL acl = grants.getAcl();
            for (ZmailACE ace : acl.getAllACEs()) {
                if (ace.getGrantee().equals(grantee.getId())) {
                    map.put(ace.getRight(), grants.getTargetEntry());
                }
            }
        }
        return map;
    }

    /**
     * Returns {@link UserRights#R_sendOnBehalfOf} rights granted to the grantee.
     */
    public static List<Identity> getSendOnBehalfOf(Account grantee) throws ServiceException {
        Multimap<Right, Entry> rights = getGrantedRights(grantee, Collections.singleton(Provisioning.A_displayName));
        ImmutableList.Builder<Identity> result = ImmutableList.<Identity>builder();
        for (Entry entry : rights.get(UserRights.R_sendOnBehalfOf)) {
            Account grantor = (Account) entry;
            String mail = grantor.getName();
            String name = Objects.firstNonNull(grantor.getDisplayName(), mail);
            Map<String, Object> attrs = ImmutableMap.<String, Object>builder()
                .put(Provisioning.A_zmailPrefIdentityId, grantor.getId())
                .put(Provisioning.A_zmailPrefIdentityName, name)
                .put(Provisioning.A_zmailPrefFromDisplay, name)
                .put(Provisioning.A_zmailPrefFromAddress, mail)
                .put(Provisioning.A_objectClass, AttributeClass.OC_zmailAclTarget)
                .build();
            result.add(new Identity(grantee, name, grantor.getId(), attrs, grantee.getProvisioning()));
        }
        return result.build();
    }


    /**
     * Grant rights on a target entry.
     */
    public static List<ZmailACE> grantRight(Provisioning prov, Entry target, Set<ZmailACE> aces)
    throws ServiceException {
        for (ZmailACE ace : aces) {
            ZmailACE.validate(ace);
        }
        ZmailACL acl = getACL(target);
        List<ZmailACE> granted = null;

        if (acl == null) {
            acl = new ZmailACL(aces);
            granted = acl.getAllACEs();
        } else {
            // Make a copy so we don't interfere with others that are using the acl.
            // This instance of acl will never be used in any AccessManager code path.
            // It only lives within this method for serialization.
            // serialize will erase the cached ZmailACL object on the target object.
            // The new ACL will be loaded when it is needed.
            acl = acl.clone();
            granted = acl.grantAccess(aces);
        }

        serialize(prov, target, acl);

        PermissionCache.invalidateCache(target);

        return granted;
    }

    /**
     * Revoke(remove) rights from a target entry.
     * If a right was not previously granted on the target, NO error is thrown.
     * @return a Set of grants that are actually revoked by this call
     */
    public static List<ZmailACE> revokeRight(Provisioning prov, Entry target, Set<ZmailACE> aces)
    throws ServiceException {
        ZmailACL acl = getACL(target);
        if (acl == null) {
            return new ArrayList<ZmailACE>(); // return empty list
        }
        // Make a copy so we don't interfere with others that are using the acl.
        // This instance of acl will never be used in any AccessManager code path.
        // It only lives within this method for serialization.
        // serialize will erase the cached ZmailACL object on the target object.
        // The new ACL will be loaded when it is needed.
        acl = acl.clone();
        List<ZmailACE> revoked = acl.revokeAccess(aces);
        serialize(prov, target, acl);

        PermissionCache.invalidateCache(target);

        return revoked;
    }

    /**
     * Persists grants in LDAP
     */
    private static void serialize(Provisioning prov, Entry entry, ZmailACL acl)
    throws ServiceException {
        // modifyAttrs will erase cached ACL and permission cache on the target
        prov.modifyAttrs(entry, Collections.singletonMap(Provisioning.A_zmailACE, acl.serialize()));
    }

    /**
     * Get cached grants, if not in cache, load from LDAP.
     *
     * @param entry
     * @return
     * @throws ServiceException
     */
    static ZmailACL getACL(Entry entry) throws ServiceException {
        ZmailACL acl = (ZmailACL) entry.getCachedData(ACL_CACHE_KEY);
        if (acl != null) {
            return acl;
        } else {
            String[] aces = entry.getMultiAttr(Provisioning.A_zmailACE);
            if (aces.length == 0) {
                return null;
            } else {
                acl = new ZmailACL(aces, TargetType.getTargetType(entry), entry.getLabel());
                entry.setCachedData(ACL_CACHE_KEY, acl);
            }
        }
        return acl;
    }

}
