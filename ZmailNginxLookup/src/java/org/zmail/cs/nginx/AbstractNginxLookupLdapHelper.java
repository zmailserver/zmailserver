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
package org.zmail.cs.nginx;

import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.ldap.LdapProv;
import org.zmail.cs.ldap.ILdapContext;
import org.zmail.cs.ldap.ZLdapFilter;
import org.zmail.cs.ldap.ZLdapFilterFactory.FilterId;
import org.zmail.cs.nginx.NginxLookupExtension.NginxLookupException;

abstract class AbstractNginxLookupLdapHelper {
    
    LdapProv prov;
    
    AbstractNginxLookupLdapHelper(LdapProv prov) {
        this.prov = prov;
    }
    
    static class SearchDirResult {
        // key of the map is one of the zmailReverseProvyXXXAttribute 
        // value is the attr value of the attribute stored in the corresponding zmailReverseProvyXXXAttribute
        Map<String, String> configuredAttrs; 
        
        // key of the map the ldap attribute name
        // value is ldap attribute value
        Map<String, String> extraAttrs;
    }
    
    abstract ILdapContext getLdapContext() throws ServiceException;
    
    abstract void closeLdapContext(ILdapContext ldapContext);
    
    /**
     * 
     * @param zlc
     * @param returnAttrs
     * @param config
     * @param query                the query, use as is
     * @param searchBaseConfigAttr global config attribute name that contains the search base
     * @return
     * @throws NginxLookupException
     */
    abstract Map<String, Object> searchDir(ILdapContext ldapContext, String[] returnAttrs, 
            Config config, ZLdapFilter filter, String searchBaseConfigAttr) 
    throws NginxLookupException;
    
    /**
     * 
     * @param zlc
     * @param returnAttrs
     * @param config
     * @param queryTemplate
     * @param searchBase
     * @param templateKey
     * @param templateVal
     * @param attrs       key of the map is one of the zmailReverseProvyXXXAttribute
     *                    value of the map is if this attribute is required
     * @param extraAttrs  set of attribute names to return
     * @return
     * @throws NginxLookupException
     */
    abstract SearchDirResult searchDirectory(ILdapContext ldapContext, String[] returnAttrs, 
            Config config, FilterId filterId, String queryTemplate, String searchBase, 
            String templateKey, String templateVal, Map<String, Boolean> attrs, Set<String> extraAttrs) 
    throws NginxLookupException;
    
}
