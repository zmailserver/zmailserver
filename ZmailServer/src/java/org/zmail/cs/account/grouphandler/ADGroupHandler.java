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

package org.zmail.cs.account.grouphandler;

import java.util.List;
import java.util.TreeSet;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.EntryCacheDataKey;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.ExternalGroup;
import org.zmail.cs.account.auth.AuthMechanism.AuthMech;
import org.zmail.cs.account.ldap.LdapHelper;
import org.zmail.cs.account.ldap.LdapProv;
import org.zmail.cs.ldap.IAttributes;
import org.zmail.cs.ldap.ILdapContext;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapConstants;
import org.zmail.cs.ldap.LdapUtil;
import org.zmail.cs.ldap.SearchLdapOptions;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZLdapFilter;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.ldap.ZSearchScope;
import org.zmail.cs.ldap.IAttributes.CheckBinary;
import org.zmail.cs.ldap.SearchLdapOptions.SearchLdapVisitor;

public class ADGroupHandler extends GroupHandler {

    private static final String MAIL_ATTR = "mail";
    private static final String MEMBER_OF_ATTR = LdapConstants.ATTR_memberOf;
    
    @Override
    public boolean isGroup(IAttributes ldapAttrs) {
        try {
            List<String> objectclass = ldapAttrs.getMultiAttrStringAsList(
                    Provisioning.A_objectClass, IAttributes.CheckBinary.NOCHECK);
            return objectclass.contains("group");
        } catch (ServiceException e) {
            ZmailLog.gal.warn("unable to get attribute " + Provisioning.A_objectClass, e);
        }
        return false;
    }

    @Override
    public String[] getMembers(ILdapContext ldapContext, String searchBase, 
            String entryDN, IAttributes ldapAttrs) throws ServiceException {
        if (ZmailLog.gal.isDebugEnabled()) {
            try {
                ZmailLog.gal.debug("Fetching members for group " + 
                        ldapAttrs.getAttrString(MAIL_ATTR) + 
                        " [" + entryDN + "]");
            } catch (ServiceException e) {
                ZmailLog.gal.debug("unable to get email address of group " + entryDN, e);
            }
        }
        
        SearchADGroupMembers searcher = new SearchADGroupMembers();
        TreeSet<String> result = searcher.searchLdap(ldapContext, searchBase, entryDN);
        return result.toArray(new String[result.size()]);
    }
    
    
    private static class SearchADGroupMembers extends SearchLdapVisitor {

        TreeSet<String> result = new TreeSet<String>();
        
        SearchADGroupMembers() {
            super(false);
        }
        
        @Override
        public void visit(String dn, IAttributes ldapAttrs) {
            String email;
            try {
                email = ldapAttrs.getAttrString(MAIL_ATTR);
                if (email != null) {
                    result.add(email);
                }
            } catch (ServiceException e) {
                // swallow exceptions and continue
                ZmailLog.gal.warn("unable to get attribute " + MAIL_ATTR + " from search result", e);
            }
        }
        
        private TreeSet<String> searchLdap(ILdapContext zlc, String searchBase, String dnOfGroup) {
            
            ZLdapFilter filter = ZLdapFilterFactory.getInstance().memberOf(dnOfGroup);
            String[] returnAttrs = new String[]{MAIL_ATTR};
            
            try {
                LdapHelper ldapHelper = LdapProv.getInst().getHelper();
                SearchLdapOptions searchOptions = new SearchLdapOptions(searchBase, filter, 
                        returnAttrs, SearchLdapOptions.SIZE_UNLIMITED, null, 
                        ZSearchScope.SEARCH_SCOPE_SUBTREE, this);
                ldapHelper.searchLdap(zlc, searchOptions);
            } catch (ServiceException e) {
                // log and continue
                ZmailLog.gal.warn("unable to search group members", e);
            }
                        
            return result;
        }
    }

    @Override
    public ZLdapContext getExternalDelegatedAdminGroupsLdapContext(Domain domain, boolean asAdmin) 
    throws ServiceException {
        if (!domainAdminAuthMechIsAD(domain, asAdmin)) {
            throw ServiceException.INVALID_REQUEST("domain auth mech must be AD", null);
        }
        
        return super.getExternalDelegatedAdminGroupsLdapContext(domain, asAdmin);
    }
    
    private static boolean domainAdminAuthMechIsAD(Domain domain, boolean asAdmin) {
        return asAdmin ? AuthMech.ad.name().equals(domain.getAuthMechAdmin()) :
                         AuthMech.ad.name().equals(domain.getAuthMech());
    }
    
    /*
     * Check:
     *   - zmailAuthMechAdmin on the domain must be AD
     *   - domain of the account must be the same as the domain in the grant
     *   
     * TODO: pass in auth token and validate that the auth was indeed via AD 
     */
    private boolean legitimateDelegatedAdminAsGroupMember(ExternalGroup group, 
            Account acct, boolean asAdmin) throws ServiceException {
        String zmailDomainId = group.getZmailDomainId();
        Domain domain = Provisioning.getInstance().getDomain(acct);
        
        if (domain == null) {
            return false;
        }
        
        if (!domainAdminAuthMechIsAD(domain, asAdmin)) {
            return false;
        }
        
        if (!domain.getId().equals(zmailDomainId)) {
            return false;
        }
        
        return true;
    }

    @Override
    public boolean inDelegatedAdminGroup(ExternalGroup group, Account acct, boolean asAdmin) 
    throws ServiceException {
        
        if (!legitimateDelegatedAdminAsGroupMember(group, acct, asAdmin)) {
            return false;
        }
        
        // check cache
        @SuppressWarnings("unchecked")
        List<String> groupDNs = (List<String>)
            acct.getCachedData(EntryCacheDataKey.GROUPEDENTRY_EXTERNAL_GROUP_DNS);
        
        if (groupDNs != null) {
            return groupDNs.contains(group.getDN());
        }
        
        // get groups DNs this account belongs to in the external group
        groupDNs = getDelegatedAdminGroups(acct, asAdmin);
                
        acct.setCachedData(EntryCacheDataKey.GROUPEDENTRY_EXTERNAL_GROUP_DNS, groupDNs);
        
        return groupDNs.contains(group.getDN());
    }
    
    private List<String> getDelegatedAdminGroups(Account acct, boolean asAdmin) throws ServiceException {
        LdapProv prov = LdapProv.getInst();
        
        Domain domain = prov.getDomain(acct);
        if (domain == null) {
            throw ServiceException.FAILURE("unable to get domain for account " + 
                    acct.getName(), null);
        }
        
        // try explicit external DN on account first
        String extDN = acct.getAuthLdapExternalDn();
        if (extDN == null) {
            // then try bind DN template on domain
            // note: for AD auth, zmailAuthLdapSearchFilter is not used, so we 
            //       skip that. See LdapProvisioning.externalLdapAuth
            String dnTemplate = domain.getAuthLdapBindDn();
            if (dnTemplate != null) {
                extDN = LdapUtil.computeDn(acct.getName(), dnTemplate);
            }
        }
        
        if (extDN == null) {
            throw ServiceException.FAILURE("unable to get external DN for account " + 
                    acct.getName(), null);
        }
        
        ZLdapContext zlc = null;
        try {
            zlc = getExternalDelegatedAdminGroupsLdapContext(domain, asAdmin);
            
            ZAttributes attrs = prov.getHelper().getAttributes(zlc, extDN, new String[]{MEMBER_OF_ATTR});
            
            return attrs.getMultiAttrStringAsList(MEMBER_OF_ATTR, CheckBinary.NOCHECK);
        } finally {
            LdapClient.closeContext(zlc);
        }
    }
    
}
