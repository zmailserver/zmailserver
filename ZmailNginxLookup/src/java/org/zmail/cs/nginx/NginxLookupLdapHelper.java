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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.ldap.LdapProv;
import org.zmail.cs.ldap.ILdapContext;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapConstants;
import org.zmail.cs.ldap.LdapException;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZLdapFilter;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.ldap.ZSearchControls;
import org.zmail.cs.ldap.ZSearchResultEntry;
import org.zmail.cs.ldap.ZSearchResultEnumeration;
import org.zmail.cs.ldap.ZSearchScope;
import org.zmail.cs.ldap.ZLdapFilterFactory.FilterId;
import org.zmail.cs.nginx.NginxLookupExtension.EntryNotFoundException;
import org.zmail.cs.nginx.NginxLookupExtension.NginxLookupException;

public class NginxLookupLdapHelper extends AbstractNginxLookupLdapHelper {

    NginxLookupLdapHelper(LdapProv prov) {
        super(prov);
    }

    @Override
    ILdapContext getLdapContext() throws ServiceException {
        return LdapClient.getContext(LdapUsage.NGINX_LOOKUP);
    }

    @Override
    void closeLdapContext(ILdapContext ldapContext) {
        ZLdapContext zlc = LdapClient.toZLdapContext(prov, ldapContext);
        LdapClient.closeContext(zlc);
    }

    @Override
    Map<String, Object> searchDir(ILdapContext ldapContext, String[] returnAttrs,
            Config config, ZLdapFilter filter, String searchBaseConfigAttr)
    throws NginxLookupException {
        
        ZLdapContext zlc = LdapClient.toZLdapContext(prov, ldapContext);
        
        Map<String, Object> attrs = null;
        
        String base  = config.getAttr(searchBaseConfigAttr);
        if (base == null) {
            base = LdapConstants.DN_ROOT_DSE;
        }
        
        ZSearchControls searchControls = ZSearchControls.createSearchControls(
                ZSearchScope.SEARCH_SCOPE_SUBTREE, 1, returnAttrs);
        
        ZSearchResultEnumeration ne = null;
        try {
            try {
                ne = zlc.searchDir(base, filter, searchControls);
                if (!ne.hasMore()) {
                    throw new NginxLookupException("query returned empty result: " + filter.toFilterString());
                }
                ZSearchResultEntry sr = ne.next();
                ZAttributes ldapAttrs = sr.getAttributes();
                attrs = ldapAttrs.getAttrs();
            } finally {
                if (ne != null) {
                    ne.close();
                }
            }
        } catch (ServiceException e) { 
            throw new NginxLookupException("unable to search LDAP", e);
        }
        
        return attrs;
    }

    @Override
    SearchDirResult searchDirectory(ILdapContext ldapContext, String[] returnAttrs,
            Config config, FilterId filterId, String queryTemplate, String searchBase,
            String templateKey, String templateVal, Map<String, Boolean> attrs,
            Set<String> extraAttrs) 
    throws NginxLookupException {
        ZLdapContext zlc = LdapClient.toZLdapContext(prov, ldapContext);
        
        HashMap<String, String> kv = new HashMap<String,String>();
        kv.put(templateKey, ZLdapFilterFactory.getInstance().encodeValue(templateVal));
        
        String query = config.getAttr(queryTemplate);
        String base  = config.getAttr(searchBase);
        if (query == null)
            throw new NginxLookupException("empty attribute: "+queryTemplate);
        
        ZmailLog.nginxlookup.debug("query template attr=" + queryTemplate + ", query template=" + query);
        query = StringUtil.fillTemplate(query, kv);
        ZmailLog.nginxlookup.debug("query=" + query);
        
        if (base == null) {
            base = LdapConstants.DN_ROOT_DSE;
        }
        
        ZSearchControls searchControls = ZSearchControls.createSearchControls(
                ZSearchScope.SEARCH_SCOPE_SUBTREE, 1, returnAttrs);
        
        SearchDirResult sdr = new SearchDirResult();
        
        ZSearchResultEnumeration ne = null;
        try {
            try {
                ne = zlc.searchDir(base, 
                        ZLdapFilterFactory.getInstance().fromFilterString(filterId, query), 
                        searchControls);
                
                if (!ne.hasMore())
                    throw new EntryNotFoundException("query returned empty result: "+query);
                ZSearchResultEntry sr = ne.next();
                
                sdr.configuredAttrs = new HashMap<String, String>();
                lookupAttrs(sdr.configuredAttrs, config, sr, attrs);
                
                sdr.extraAttrs = new HashMap<String, String>();
                if (extraAttrs != null) {
                    ZAttributes ldapAttrs = sr.getAttributes();
                    for (String attr : extraAttrs) {
                        String val = ldapAttrs.getAttrString(attr);
                        if (val != null)
                            sdr.extraAttrs.put(attr, val);
                    }
                }
            } finally {
                if (ne != null) {
                    ne.close();
                }
            }
        } catch (ServiceException e) { 
            throw new NginxLookupException("unable to search LDAP", e);
        }
        
        return sdr;
    }

    private void lookupAttrs(Map<String, String> vals, Config config, ZSearchResultEntry sr, Map<String, Boolean> keys) 
    throws NginxLookupException, LdapException {
        for (Map.Entry<String, Boolean> keyEntry : keys.entrySet()) {
            String key = keyEntry.getKey();
            String val = lookupAttr(config, sr, key, keyEntry.getValue());
            if (val != null)
                vals.put(key, val);
        }
    }
    
    private String lookupAttr(Config config, ZSearchResultEntry sr, String key, Boolean required) 
    throws NginxLookupException, LdapException {
        String val = null;
        String attr = config.getAttr(key);
        if (attr == null && required)
            throw new NginxLookupException("missing attr in config: "+key);
        if (attr != null) {
            val = sr.getAttributes().getAttrString(attr);
            if (val == null && required)
                throw new NginxLookupException("missing attr in search result: "+attr);
        }
        return val;
    }
}
