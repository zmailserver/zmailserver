/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.GuestAccount;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ldap.LdapProv;
import org.zmail.cs.ldap.IAttributes;
import org.zmail.cs.ldap.SearchLdapOptions.SearchLdapVisitor;
import org.zmail.soap.type.TargetBy;

/**
 * @author pshao
 */
public final class SearchGrants {
    private final Provisioning prov;
    private final Set<TargetType> targetTypes;
    private final Set<String> granteeIds;
    
    private final Account acct;
    private final Set<Right> rights;
    private final boolean onMaster;
    
    private final Set<String> fetchAttrs = Sets.newHashSet(
            Provisioning.A_cn, 
            Provisioning.A_zmailId,
            Provisioning.A_objectClass, 
            Provisioning.A_zmailACE);

    SearchGrants(Provisioning prov, Set<TargetType> targetTypes, Set<String> granteeIds) {
        this.prov = prov;
        this.targetTypes = targetTypes;
        this.granteeIds = granteeIds;
        this.acct = null;
        this.rights = null;
        this.onMaster = true;
    }
    
    /*
     * search for rights applied to the acct
     */
    SearchGrants(Provisioning prov, Set<TargetType> targetTypes, Account acct, 
            Set<Right> rights, boolean onMaster) {
        this.prov = prov;
        this.targetTypes = targetTypes;
        this.granteeIds = null;
        this.acct = acct;
        this.rights = rights;
        this.onMaster = onMaster;
    }

    void addFetchAttribute(String attr) {
        fetchAttrs.add(attr);
    }

    void addFetchAttribute(Set<String> attrs) {
        fetchAttrs.addAll(attrs);
    }

    static final class GrantsOnTarget {
        private Entry targetEntry;
        private ZmailACL acl;

        private GrantsOnTarget(Entry targetEntry, ZmailACL acl) {
            this.targetEntry = targetEntry;
            this.acl = acl;
        }

        Entry getTargetEntry() {
            return targetEntry;
        }

        ZmailACL getAcl() {
            return acl;
        }
    }

    static final class SearchGrantsResults {
        private final Provisioning prov;

        // map of raw(in ldap data form, quick way for staging grants found in search visitor,
        // because we don't want to do much processing in the visitor while taking a 
        // ldap connection) search results
        //    key: target id (or name if zimlet)
        //    value: grants on this target
        private final Map<String, GrantsOnTargetRaw> rawResults = 
            new HashMap<String, GrantsOnTargetRaw>();

        // results in the form usable by callers
        private Set<GrantsOnTarget> results;

        SearchGrantsResults(Provisioning prov) {
            this.prov = prov;
        }

        private void addResult(GrantsOnTargetRaw result) {
            rawResults.put(result.getTargetId(), result);
        }

        /**
         * Returns a map of target entry and ZmailACL object on the target.
         */
        Set<GrantsOnTarget> getResults() throws ServiceException {
            return getResults(false);
        }

        Set<GrantsOnTarget> getResults(boolean needFullDL) throws ServiceException {
            if (results == null) {
                results = new HashSet<GrantsOnTarget>();
                for (GrantsOnTargetRaw grants : rawResults.values()) {
                    results.add(getGrants(prov, grants, needFullDL));
                }
            }
            return results;
        }

        /**
         * Converts a {@link SearchGrantsResults} to {@code <Entry, ZmailACL>} pair.
         */
        private GrantsOnTarget getGrants(Provisioning prov, GrantsOnTargetRaw sgr, boolean needFullDL) 
        throws ServiceException {
            TargetType tt;
            if (sgr.objectClass.contains(AttributeClass.OC_zmailCalendarResource)) {
                tt = TargetType.calresource;
            } else if (sgr.objectClass.contains(AttributeClass.OC_zmailAccount)) {
                tt = TargetType.account;
            } else if (sgr.objectClass.contains(AttributeClass.OC_zmailCOS)) {
                tt = TargetType.cos;
            } else if (sgr.objectClass.contains(AttributeClass.OC_zmailDistributionList)) {
                tt = TargetType.dl;
            } else if (sgr.objectClass.contains(AttributeClass.OC_zmailGroup)) {
                tt = TargetType.group;    
            } else if (sgr.objectClass.contains(AttributeClass.OC_zmailDomain)) {
                tt = TargetType.domain;
            } else if (sgr.objectClass.contains(AttributeClass.OC_zmailServer)) {
                tt = TargetType.server;
            } else if (sgr.objectClass.contains(AttributeClass.OC_zmailUCService)) {
                tt = TargetType.ucservice;    
            } else if (sgr.objectClass.contains(AttributeClass.OC_zmailXMPPComponent)) {
                tt = TargetType.xmppcomponent;
            } else if (sgr.objectClass.contains(AttributeClass.OC_zmailZimletEntry)) {
                tt = TargetType.zimlet;
            } else if (sgr.objectClass.contains(AttributeClass.OC_zmailGlobalConfig)) {
                tt = TargetType.config;
            } else if (sgr.objectClass.contains(AttributeClass.OC_zmailAclTarget)) {
                tt = TargetType.global;
            } else {
                throw ServiceException.FAILURE(
                        "cannot determine target type from SearchGrantResult. " + sgr, null);
            }
            Entry entry = null;
            try {
                if (tt == TargetType.zimlet) {
                    entry = TargetType.lookupTarget(prov, tt, TargetBy.name, sgr.cn);
                } else {
                    entry = TargetType.lookupTarget(prov, tt, TargetBy.id, sgr.zmailId, needFullDL, true);
                }
                if (entry == null) {
                    ZmailLog.acl.warn("canot find target by id %s", sgr.zmailId);
                    throw ServiceException.FAILURE("canot find target by id " + sgr.zmailId + ". " + sgr, null);
                }
                ZmailACL acl = new ZmailACL(sgr.zmailACE, tt, entry.getLabel());
                return new GrantsOnTarget(entry, acl);
            } catch (ServiceException e) {
                throw ServiceException.FAILURE("canot find target by id " + sgr.zmailId + ". " + sgr, null);
            }
        }
    }

    /**
     * grants found on a target based on the search criteria.
     */
    private static class GrantsOnTargetRaw {
        private final String cn;
        private final String zmailId;
        private final Set<String> objectClass;
        private final String[] zmailACE;

        private GrantsOnTargetRaw(Map<String, Object> attrs) {
            cn = (String) attrs.get(Provisioning.A_cn);
            zmailId = (String) attrs.get(Provisioning.A_zmailId);
            objectClass = ImmutableSet.copyOf(getMultiAttrString(attrs, Provisioning.A_objectClass));
            zmailACE = getMultiAttrString(attrs, Provisioning.A_zmailACE);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                .add("cn", cn)
                .add("zmailId", zmailId)
                .add("objectClass", objectClass)
                .add("zmailACE", ImmutableList.copyOf(zmailACE))
                .toString();
        }

        private String[] getMultiAttrString(Map<String, Object> attrs, String attrName) {
            Object obj = attrs.get(attrName);
            if (obj instanceof String) {
                return new String[] {(String) obj};
            } else {
                return (String[]) obj;
            }
        }

        private String getTargetId() {
            // urg! zimlet does not have an id, use cn.
            // need to return something for the map key for SearchGrantVisitor.visit
            // id is only used for grants granted on group-ed entries (account, cr, dl)
            // in computeRightsOnGroupShape
            return zmailId != null ? zmailId : cn;
        }
    }

    private static class SearchGrantVisitor extends SearchLdapVisitor {
        private final SearchGrantsResults results;

        SearchGrantVisitor(SearchGrantsResults results) {
            this.results = results;
        }

        @Override
        public void visit(String dn, Map<String, Object> attrs, IAttributes ldapAttrs) {
            results.addResult(new GrantsOnTargetRaw(attrs));
        }
    }

    /**
     * search grants granted to any of the grantees, granted on any of the target types.
     */
    SearchGrantsResults doSearch() throws ServiceException {
       Map<String, Set<String>> basesAndOcs = TargetType.getSearchBasesAndOCs(prov, targetTypes);
       SearchGrantsResults results = new SearchGrantsResults(prov);
       SearchGrantVisitor visitor = new SearchGrantVisitor(results);
       for (Map.Entry<String, Set<String>> entry : basesAndOcs.entrySet()) {
           search(entry.getKey(), entry.getValue(), visitor);
       }
       return results;
    }
    
    private Set<String> getGranteeIds() throws ServiceException {
        if (granteeIds != null) {
            return granteeIds;
        } else {
            Set<String> ids = Sets.newHashSet(new RightBearer.Grantee(acct, false).getIdAndGroupIds());
            ids.add(GuestAccount.GUID_AUTHUSER);
            ids.add(GuestAccount.GUID_PUBLIC);
            String domainId = acct.getDomainId();
            if (domainId != null) {
                ids.add(domainId);
            }
            
            return ids;
        }
    }

    private void search(String base, Set<String> ocs, SearchGrantVisitor visitor) 
    throws ServiceException {
        StringBuilder query = new StringBuilder("(&(|");
        for (String oc : ocs) {
            query.append('(').append(Provisioning.A_objectClass).append('=').append(oc).append(")");
        }
        query.append(")(|");
        
        if (rights == null) {
            for (String granteeId : getGranteeIds()) {
                query.append('(').append(Provisioning.A_zmailACE).append('=').append(granteeId).append("*)");
            }
        } else {
            for (String granteeId : getGranteeIds()) {
                for (Right right : rights) {
                    query.append('(').append(Provisioning.A_zmailACE).append('=').append(granteeId).append("*").append(right.getName()).append(")");
                }
            }
        }
        query.append("))");
        
        if (onMaster) {
            LdapProv.getInst().searchLdapOnMaster(base, query.toString(),
                    fetchAttrs.toArray(new String[fetchAttrs.size()]), visitor);
        } else {
            LdapProv.getInst().searchLdapOnReplica(base, query.toString(),
                    fetchAttrs.toArray(new String[fetchAttrs.size()]), visitor);
        }
    }
}
