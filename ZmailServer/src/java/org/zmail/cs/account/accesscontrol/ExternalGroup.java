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
package org.zmail.cs.account.accesscontrol;

import java.util.List;

import org.zmail.common.account.Key.DomainBy;
import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Constants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.EntryCacheDataKey;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Provisioning.GroupMembership;
import org.zmail.cs.account.Provisioning.MemberOf;
import org.zmail.cs.account.accesscontrol.ZmailACE.ExternalGroupInfo;
import org.zmail.cs.account.cache.NamedEntryCache;
import org.zmail.cs.account.grouphandler.ADGroupHandler;
import org.zmail.cs.account.grouphandler.GroupHandler;
import org.zmail.cs.account.ldap.LdapProv;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapConstants;
import org.zmail.cs.ldap.LdapException;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.LdapUtil;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZSearchResultEntry;
import org.zmail.cs.ldap.LdapServerConfig.ExternalLdapConfig;
import org.zmail.cs.ldap.ZLdapFilterFactory.FilterId;

public class ExternalGroup extends NamedEntry {
    
    private static final NamedEntryCache<ExternalGroup> CACHE =
        new NamedEntryCache<ExternalGroup>(
                LC.ldap_cache_group_maxsize.intValue(),
                LC.ldap_cache_group_maxage.intValue() * Constants.MILLIS_PER_MINUTE);
    
    private String dn;
    private GroupHandler groupHandler;
    private String zmailDomainId;
    
    /*
     * id:   {zmail domain id}:{external group name}
     * name: {zmail domain name}:{external group name}
     */
    ExternalGroup(String dn, String id, String name, String zmailDomainId,
            ZAttributes attrs, GroupHandler groupHandler, Provisioning prov) 
    throws LdapException {
        super(name, id, attrs.getAttrs(), null, prov);
        this.dn = dn;
        this.groupHandler = groupHandler;
        this.zmailDomainId = zmailDomainId;
    }
    
    public String getDN() {
        return dn;
    }
    
    public String getZmailDomainId() {
        return zmailDomainId;
    }
    
    boolean inGroup(Account acct, boolean asAdmin) throws ServiceException {
        return groupHandler.inDelegatedAdminGroup(this, acct, asAdmin);
    }
    
    private static GroupHandler getGroupHandler(Domain domain) {
        String className = domain.getExternalGroupHandlerClass();
        return GroupHandler.getHandler(className);
    }
    
    private static ExternalGroup makeExternalGroup(Domain domain, 
            GroupHandler groupHandler, String extGroupName, String dn, 
            ZAttributes attrs) throws ServiceException {
        String id = ExternalGroupInfo.encode(domain.getId(), extGroupName);
        String name = ExternalGroupInfo.encode(domain.getName(), extGroupName);
        
        ExternalGroup extGroup = new ExternalGroup(
                dn, id, name, domain.getId(), attrs, groupHandler, LdapProv.getInst());
        return extGroup;
    }
    
    /*
     * domainBy: id when extGroupGrantee is obtained in fron persisted ZmailACE   
     *           name when extGroupGrantee is provided to zmprov or SOAP. 
     *         
     */
    static ExternalGroup get(/* AuthToken authToken, */ DomainBy domainBy, 
            String extGroupGrantee, boolean asAdmin) throws ServiceException {
        ExternalGroup group = null;
        
        if (DomainBy.name == domainBy) {
            group = CACHE.getByName(extGroupGrantee);
        } else {
            group = CACHE.getById(extGroupGrantee);
        }
        
        if (group != null) {
            return group;
        }
        
        group = searchGroup(domainBy, extGroupGrantee, asAdmin);
        
        if (group != null) {
            CACHE.put(group);
        }
        
        return group;
    }
    
    private static ExternalGroup searchGroup(DomainBy domainBy, String extGroupGrantee,
            boolean asAdmin) throws ServiceException {
        LdapProv prov = LdapProv.getInst();
        
        ExternalGroupInfo extGrpInfo = ExternalGroupInfo.parse(extGroupGrantee);
        String zmailDomain = extGrpInfo.getZmailDmain();
        String extGroupName = extGrpInfo.getExternalGroupName();
        
        Domain domain = prov.get(domainBy, zmailDomain);
        if (domain == null) {
            throw AccountServiceException.NO_SUCH_DOMAIN(zmailDomain);
        }
        
        String searchBase = domain.getExternalGroupLdapSearchBase();
        String filterTemplate = domain.getExternalGroupLdapSearchFilter();
        
        if (searchBase == null) {
            searchBase = LdapConstants.DN_ROOT_DSE;
        }
        String searchFilter = LdapUtil.computeDn(extGroupName, filterTemplate);
        
        GroupHandler groupHandler = getGroupHandler(domain);
        
        ZLdapContext zlc = null;
        try {
            zlc = groupHandler.getExternalDelegatedAdminGroupsLdapContext(domain, asAdmin);
            
            ZSearchResultEntry entry = prov.getHelper().searchForEntry(
                    searchBase, FilterId.EXTERNAL_GROUP, searchFilter, zlc, new String[]{"mail"});
            
            if (entry != null) {
                return makeExternalGroup(domain, groupHandler, extGroupName, 
                        entry.getDN(), entry.getAttributes());
            } else {
                return null;
            }
        } finally {
            LdapClient.closeContext(zlc);
        }
    }

}
