/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.account.ldap.entry;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ldap.LdapProv;
import org.zmail.cs.gal.ZmailGalSearchBase.PredefinedSearchBase;
import org.zmail.cs.ldap.LdapConstants;
import org.zmail.cs.ldap.LdapException;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.cs.ldap.ZLdapFilter;
import org.zmail.cs.ldap.ZLdapFilterFactory;

/**
 * 
 * @author pshao
 *
 */
public class LdapDomain extends Domain implements LdapEntry {

    private String mDn;
    
    public LdapDomain(String dn, ZAttributes attrs, Map<String, Object> defaults, Provisioning prov) 
    throws LdapException {
        super(attrs.getAttrString(Provisioning.A_zmailDomainName), 
                attrs.getAttrString(Provisioning.A_zmailId), 
                attrs.getAttrs(), defaults, prov);
        mDn = dn;
    }

    public String getDN() {
        return mDn;
    }
    
    @Override
    public String getGalSearchBase(String searchBaseRaw) throws ServiceException {
        LdapProv ldapProv = (LdapProv)getProvisioning();
        
        if (searchBaseRaw.equalsIgnoreCase(PredefinedSearchBase.DOMAIN.name())) {
            // dynamic groups are under the cn=groups tree,
            // accounts and Dls are under the people tree
            // We can no longer just search under the people tree because that 
            // will leave dynamic groups out.   We don't want to do two(once under the 
            // people tree, once under the groups tree) LDAP searches either because 
            // that will hurt perf.  
            // As of bug 66001, we now use the dnSubtreeMatch filter 
            // (extension supported by OpenLDAP) to exclude entries in sub domains.
            // See getDnSubtreeMatchFilter().
            return getDN();
            // return ldapProv.getDIT().domainDNToAccountSearchDN(getDN());
        } else if (searchBaseRaw.equalsIgnoreCase(PredefinedSearchBase.SUBDOMAINS.name())) {
            return getDN();
        } else if (searchBaseRaw.equalsIgnoreCase(PredefinedSearchBase.ROOT.name())) {
            return LdapConstants.DN_ROOT_DSE;
        }
        
        // broken by p4 changed 150971, fixed now
        return searchBaseRaw;
    }
    
    public ZLdapFilter getDnSubtreeMatchFilter() throws ServiceException {
        LdapProv ldapProv = (LdapProv)getProvisioning();
        
        return ZLdapFilterFactory.getInstance().dnSubtreeMatch(
                ldapProv.getDIT().domainDNToAccountSearchDN(getDN()),
                ldapProv.getDIT().domainDNToDynamicGroupsBaseDN(getDN()));
    }
}
