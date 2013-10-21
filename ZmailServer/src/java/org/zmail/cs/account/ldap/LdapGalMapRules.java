/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.account.ldap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.mailbox.ContactConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.grouphandler.GroupHandler;
import org.zmail.cs.ldap.IAttributes;
import org.zmail.cs.ldap.ILdapContext;


/*
 * maps LDAP attrs into contact attrs. 
 */
public class LdapGalMapRules {
    
    private List<LdapGalMapRule> mRules;
    private List<String> mLdapAttrs;
    private Set<String> mBinaryLdapAttrs; // attrs need to be set in JNDI "java.naming.ldap.attributes.binary" environment property
    private Map<String, LdapGalValueMap> mValueMaps;
    private GroupHandler mGroupHandler;
    private boolean mFetchGroupMembers;
    private boolean mNeedSMIMECerts;
    private static final String OLD_DEFAULT_GROUPHANDLER = "org.zmail.cs.gal.ADGalGroupHandler";
    private static final String CURRENT_DEFAULT_GROUPHANDLER = "org.zmail.cs.account.grouphandler.ADGroupHandler";

    public LdapGalMapRules(String[] rules, String[] valueMaps, String groupHandlerClass) {
        init(rules, valueMaps, groupHandlerClass);
    }
    
    public LdapGalMapRules(Config config, boolean isZmailGal) {
        init(config, isZmailGal);
    }
    
    public LdapGalMapRules(Domain domain, boolean isZmailGal) {
        init(domain, isZmailGal);
    }

    private void init(Entry entry, boolean isZmailGal) {
        String groupHanlderClass = null;
        if (!isZmailGal)
            groupHanlderClass = entry.getAttr(Provisioning.A_zmailGalLdapGroupHandlerClass);
        
        init(entry.getMultiAttr(Provisioning.A_zmailGalLdapAttrMap),
             entry.getMultiAttr(Provisioning.A_zmailGalLdapValueMap), 
             groupHanlderClass);
    }
    
    private void init(String[] rules, String[] valueMaps, String groupHandlerClass) {
        if (valueMaps !=  null) {
            mValueMaps = new HashMap<String, LdapGalValueMap>(valueMaps.length);
            for (String valueMap : valueMaps) {
                LdapGalValueMap vMap = new LdapGalValueMap(valueMap);
                mValueMaps.put(vMap.getFieldName(), vMap);
            }
        }
        
        mRules = new ArrayList<LdapGalMapRule>(rules.length);
        mLdapAttrs = new ArrayList<String>();
        mBinaryLdapAttrs = new HashSet<String>();
        for (String rule: rules)
            add(rule);
        // load the correct default group handler class (bug 78755)
        if (StringUtil.equal(groupHandlerClass, OLD_DEFAULT_GROUPHANDLER)) {
            groupHandlerClass = CURRENT_DEFAULT_GROUPHANDLER;
        }
        mGroupHandler = GroupHandler.getHandler(groupHandlerClass);
        ZmailLog.gal.debug("groupHandlerClass=" + groupHandlerClass + ", handler instantiated=" + mGroupHandler.getClass().getCanonicalName());
    }
    
    public void setFetchGroupMembers(boolean fetchGroupMembers) {
        mFetchGroupMembers = fetchGroupMembers;
    }
    
    public void setNeedSMIMECerts(boolean needSMIMECerts) {
        mNeedSMIMECerts = needSMIMECerts;
    }
    
    public String[] getLdapAttrs() {
        return mLdapAttrs.toArray(new String[mLdapAttrs.size()]);
    }
    
    // attrs need to be set in JNDI "java.naming.ldap.attributes.binary" environment property
    public Set<String> getBinaryLdapAttrs() {
        return mBinaryLdapAttrs;
    }
    
    public Map<String, Object> apply(ILdapContext ldapContext, String searchBase, String entryDN, IAttributes ldapAttrs) {
         
        HashMap<String,Object> contactAttrs = new HashMap<String, Object>();        
        for (LdapGalMapRule rule: mRules) {
        	if (!mNeedSMIMECerts && rule.isSMIMECertificate()) {
        		continue;
        	}
            rule.apply(ldapAttrs, contactAttrs);
        }
        
        if (mGroupHandler.isGroup(ldapAttrs)) {
            try {
                if (mFetchGroupMembers) {
                    contactAttrs.put(ContactConstants.A_member, mGroupHandler.getMembers(ldapContext, searchBase, entryDN, ldapAttrs));
                } else {
                    // for internal LDAP, all members are on the DL entry and have been fetched/mapped
                    // delete it.
                    contactAttrs.remove(ContactConstants.A_member);
                }
                contactAttrs.put(ContactConstants.A_type, ContactConstants.TYPE_GROUP);
            } catch (ServiceException e) {
                ZmailLog.gal.warn("unable to retrieve group members ", e);
            }
        }
        
        return contactAttrs;
    }
    
    public void add(String rule) {
        LdapGalMapRule lgmr = new LdapGalMapRule(rule, mValueMaps);
        mRules.add(lgmr);
        for (String ldapattr: lgmr.getLdapAttrs()) {
            mLdapAttrs.add(ldapattr);
            
            if (lgmr.isBinary()) {
                mBinaryLdapAttrs.add(ldapattr);
            }
        }
    }
}
