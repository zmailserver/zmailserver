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
package org.zmail.cs.gal;

import org.zmail.common.account.ZAttrProvisioning.GalMode;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.gal.GalOp;
import org.zmail.cs.account.gal.GalUtil;
import org.zmail.cs.account.ldap.LdapGalMapRules;
import org.zmail.cs.account.ldap.entry.LdapDomain;
import org.zmail.cs.gal.GalFilter.NamedFilter;
import org.zmail.soap.type.GalSearchType;

public class GalSearchConfig {
    
	public enum GalType {
	    zmail,
	    ldap;
        
	    public static GalType fromString(String s) throws ServiceException {
		try {
		    return GalType.valueOf(s);
		} catch (IllegalArgumentException e) {
		    throw ServiceException.INVALID_REQUEST("unknown gal type: " + s, e);
		}
	    }
	}
    
	public static GalSearchConfig create(Domain domain, GalOp op, GalType type, GalSearchType stype) 
	throws ServiceException {
		switch (type) {
		case zmail:
			return new ZmailConfig(domain, op, stype);
		case ldap:
			return new LdapConfig(domain, op);
		}
		return null;
	}
	
	public static String fixupExternalGalSearchBase(String searchBase) {
	    if (searchBase == null || "ROOT".equals(searchBase)) {
	        return "";
	    } else {
	        return searchBase;
	    }
	}
	   
	public static GalSearchConfig create(DataSource ds) throws ServiceException {
		return new DataSourceConfig(ds);
	}

	private static class ZmailConfig extends GalSearchConfig {
		public ZmailConfig(Domain domain, GalOp op, GalSearchType stype) throws ServiceException {
			loadZmailConfig(domain, op, stype);
		}
	}
	private static class LdapConfig extends GalSearchConfig {
		public LdapConfig(Domain domain, GalOp op) throws ServiceException {
			loadConfig(domain, op);
		}
	}
	
	private static class DataSourceConfig extends GalSearchConfig {
		public DataSourceConfig(DataSource ds) throws ServiceException {
			mGalType = GalType.fromString(ds.getAttr(Provisioning.A_zmailGalType));
			Domain domain = Provisioning.getInstance().getDomain(ds.getAccount());
			if (mGalType == GalType.zmail) {
				loadZmailConfig(domain, GalOp.sync, GalSearchType.all);
			} else {
				loadConfig(domain, GalOp.sync);
				if (mUrl.length == 0 || mFilter == null)
					loadConfig(domain, GalOp.search);
				String[] url = ds.getMultiAttr(Provisioning.A_zmailGalSyncLdapURL);
				if (url != null && url.length > 0)
					mUrl = url;
				mFilter = ds.getAttr(Provisioning.A_zmailGalSyncLdapFilter, mFilter);
				mSearchBase = ds.getAttr(Provisioning.A_zmailGalSyncLdapSearchBase, mSearchBase);
				mStartTlsEnabled = ds.getBooleanAttr(Provisioning.A_zmailGalSyncLdapStartTlsEnabled, mStartTlsEnabled);
				mAuthMech = ds.getAttr(Provisioning.A_zmailGalSyncLdapAuthMech, mAuthMech);
				mBindDn = ds.getAttr(Provisioning.A_zmailGalSyncLdapBindDn, mBindDn);
				mBindPassword = ds.getAttr(Provisioning.A_zmailGalSyncLdapBindPassword, mBindPassword);
				mKerberosPrincipal = ds.getAttr(Provisioning.A_zmailGalSyncLdapKerberos5Principal, mKerberosPrincipal);
				mKerberosKeytab = ds.getAttr(Provisioning.A_zmailGalSyncLdapKerberos5Keytab, mKerberosKeytab);
				mTimestampFormat = ds.getAttr(Provisioning.A_zmailGalSyncTimestampFormat, mTimestampFormat);
				mPageSize = ds.getIntAttr(Provisioning.A_zmailGalSyncLdapPageSize, mPageSize);
			}
			String[] attrs = ds.getMultiAttr(Provisioning.A_zmailGalLdapAttrMap);
			String[] valueMap = ds.getMultiAttr(Provisioning.A_zmailGalLdapValueMap);
			String groupHandlerClass = ds.getAttr(Provisioning.A_zmailGalLdapGroupHandlerClass);
			if (attrs.length > 0 || valueMap.length > 0 || groupHandlerClass != null) {
			    if (attrs.length == 0)
			        attrs = domain.getMultiAttr(Provisioning.A_zmailGalLdapAttrMap);
			    if (valueMap.length == 0)
			        valueMap = domain.getMultiAttr(Provisioning.A_zmailGalLdapValueMap);
			    if (groupHandlerClass == null)
			        groupHandlerClass = domain.getAttr(Provisioning.A_zmailGalLdapGroupHandlerClass);
				mRules = new LdapGalMapRules(attrs, valueMap, groupHandlerClass);
			}
			
			if (StringUtil.isNullOrEmpty(mFilter))
			    throw ServiceException.INVALID_REQUEST("missing GAL filter", null);
			
			mFilter = GalUtil.expandFilter(null, mFilter, "", null);
		}
	}
	
	protected void loadZmailConfig(Domain domain, GalOp op, GalSearchType stype) throws ServiceException {
		
        mRules = new LdapGalMapRules(domain, true);
        mOp = op;
        NamedFilter filterName = null;
        
		switch (op) {
		case sync:
			filterName = 
                (stype == GalSearchType.all) ? NamedFilter.zmailSync :
			    (stype == GalSearchType.resource) ? NamedFilter.zmailResourceSync : 
			    (stype == GalSearchType.group) ? NamedFilter.zmailGroupSync : 
			        NamedFilter.zmailAccountSync;
			break;
		case search:
			filterName = 
                (stype == GalSearchType.all) ? NamedFilter.zmailSearch :
			    (stype == GalSearchType.resource) ? NamedFilter.zmailResources : 
			    (stype == GalSearchType.group) ? NamedFilter.zmailGroups : 
			        NamedFilter.zmailAccounts;
			mTokenizeKey = domain.getAttr(Provisioning.A_zmailGalTokenizeSearchKey, null);
			break;
		case autocomplete:
			filterName = 
                (stype == GalSearchType.all) ? NamedFilter.zmailAutoComplete :
			    (stype == GalSearchType.resource) ? NamedFilter.zmailResourceAutoComplete : 
			    (stype == GalSearchType.group) ? NamedFilter.zmailGroupAutoComplete : 
			        NamedFilter.zmailAccountAutoComplete;
			mTokenizeKey = domain.getAttr(Provisioning.A_zmailGalTokenizeAutoCompleteKey, null);
			break;
		}
		
		String filter = null;
		if (filterName != null) {
		    filter = GalSearchConfig.getFilterDef(filterName);
		}
		
		if (filter == null && op == GalOp.sync) {
		    filter = GalFilter.DEFAULT_SYNC_FILTER;
		}
		
		mAuthMech = Provisioning.LDAP_AM_SIMPLE;
		
		if (filter == null) {
		    filter = "";
		} else if (!filter.startsWith("(")) {
		    filter = "(" + filter + ")";
		}
		
		String dnSubtreeMatchFilter = null;
		String searchBaseRaw = ZmailGalSearchBase.getSearchBaseRaw(domain, op);
		if (ZmailGalSearchBase.PredefinedSearchBase.DOMAIN.name().equals(searchBaseRaw)) {
		    dnSubtreeMatchFilter = ((LdapDomain) domain).getDnSubtreeMatchFilter().toFilterString();
		}
		    
		if (dnSubtreeMatchFilter == null) {
		    dnSubtreeMatchFilter = "";
		}
		mFilter = "(&" + filter + "(!(zmailHideInGal=TRUE))(!(zmailIsSystemResource=TRUE))" + dnSubtreeMatchFilter + ")";

		mSearchBase = ZmailGalSearchBase.getSearchBase(domain, op);
		mGalType = GalType.zmail;
		mTimestampFormat = GalSyncToken.LDAP_GENERALIZED_TIME_FORMAT;
		mPageSize = 1000;
	}
	
	private boolean isConfigComplete() {
		return mUrl.length > 0 && mFilter != null && 
			(mAuthMech.equals(Provisioning.LDAP_AM_NONE) ||
			 (mAuthMech.equals(Provisioning.LDAP_AM_SIMPLE) && mBindDn != null && mBindPassword != null) || 
			 (mAuthMech.equals(Provisioning.LDAP_AM_KERBEROS5) && mKerberosKeytab != null && mKerberosPrincipal != null));
	}
	protected void loadConfig(Domain domain, GalOp op) throws ServiceException {
	    
        mRules = new LdapGalMapRules(domain, false);
        mOp = op;
        
        GalMode galMode = domain.getGalMode();
        if (galMode == GalMode.zmail)
        	loadZmailConfig(domain, op, null);
		
        switch (op) {
        case sync:
        	mUrl = domain.getMultiAttr(Provisioning.A_zmailGalSyncLdapURL);
            mFilter = domain.getAttr(Provisioning.A_zmailGalLdapFilter);
            mSearchBase = domain.getAttr(Provisioning.A_zmailGalSyncLdapSearchBase, "");
            mStartTlsEnabled = domain.getBooleanAttr(Provisioning.A_zmailGalSyncLdapStartTlsEnabled, false);
            mAuthMech = domain.getAttr(Provisioning.A_zmailGalSyncLdapAuthMech, Provisioning.LDAP_AM_SIMPLE);
            mBindDn = domain.getAttr(Provisioning.A_zmailGalSyncLdapBindDn);
            mBindPassword = domain.getAttr(Provisioning.A_zmailGalSyncLdapBindPassword);
            mKerberosPrincipal = domain.getAttr(Provisioning.A_zmailGalSyncLdapKerberos5Principal);
            mKerberosKeytab = domain.getAttr(Provisioning.A_zmailGalSyncLdapKerberos5Keytab);
            mTimestampFormat = domain.getAttr(Provisioning.A_zmailGalSyncTimestampFormat, GalSyncToken.LDAP_GENERALIZED_TIME_FORMAT);
			mPageSize = domain.getIntAttr(Provisioning.A_zmailGalSyncLdapPageSize, 1000);
            if (isConfigComplete())
            	break;
        case search:
            mUrl = domain.getMultiAttr(Provisioning.A_zmailGalLdapURL);
            mFilter = domain.getAttr(Provisioning.A_zmailGalLdapFilter);
            mSearchBase = domain.getAttr(Provisioning.A_zmailGalLdapSearchBase, "");
            mStartTlsEnabled = domain.getBooleanAttr(Provisioning.A_zmailGalLdapStartTlsEnabled, false);
            mAuthMech = domain.getAttr(Provisioning.A_zmailGalLdapAuthMech, Provisioning.LDAP_AM_SIMPLE);
            mBindDn = domain.getAttr(Provisioning.A_zmailGalLdapBindDn);
            mBindPassword = domain.getAttr(Provisioning.A_zmailGalLdapBindPassword);
            mKerberosPrincipal = domain.getAttr(Provisioning.A_zmailGalLdapKerberos5Principal);
            mKerberosKeytab = domain.getAttr(Provisioning.A_zmailGalLdapKerberos5Keytab);
            mTokenizeKey = domain.getAttr(Provisioning.A_zmailGalTokenizeSearchKey);
			mPageSize = domain.getIntAttr(Provisioning.A_zmailGalLdapPageSize, 1000);
        	break;
        case autocomplete:
            mUrl = domain.getMultiAttr(Provisioning.A_zmailGalLdapURL);
            mFilter = domain.getAttr(Provisioning.A_zmailGalAutoCompleteLdapFilter);
            mSearchBase = domain.getAttr(Provisioning.A_zmailGalLdapSearchBase, "");
            mStartTlsEnabled = domain.getBooleanAttr(Provisioning.A_zmailGalLdapStartTlsEnabled, false);
            mAuthMech = domain.getAttr(Provisioning.A_zmailGalLdapAuthMech, Provisioning.LDAP_AM_SIMPLE);
            mBindDn = domain.getAttr(Provisioning.A_zmailGalLdapBindDn);
            mBindPassword = domain.getAttr(Provisioning.A_zmailGalLdapBindPassword);
            mKerberosPrincipal = domain.getAttr(Provisioning.A_zmailGalLdapKerberos5Principal);
            mKerberosKeytab = domain.getAttr(Provisioning.A_zmailGalLdapKerberos5Keytab);
            mTokenizeKey = domain.getAttr(Provisioning.A_zmailGalTokenizeAutoCompleteKey);
			mPageSize = domain.getIntAttr(Provisioning.A_zmailGalLdapPageSize, 1000);
        	break;
        }
        if (mFilter != null && mFilter.indexOf("(") == -1)
        	mFilter = GalSearchConfig.getFilterDef(mFilter);
		mGalType = GalType.ldap;
	}
	
	protected GalOp mOp;
	protected GalType mGalType;
	protected String[] mUrl;
	protected boolean mStartTlsEnabled;
	protected String mFilter;
	protected String mSearchBase;
	protected String mAuthMech;
	protected String mBindDn;
	protected String mBindPassword;
	protected String mKerberosPrincipal;
	protected String mKerberosKeytab;
	protected String mTimestampFormat;
	protected String mTokenizeKey;
	protected int mPageSize;
	protected LdapGalMapRules mRules;
	
	public GalOp getOp() {
		return mOp;
	}
	public GalType getGalType() {
		return mGalType;
	}
	public String[] getUrl() {
		return mUrl;
	}
	public boolean getStartTlsEnabled() {
		return mStartTlsEnabled;
	}
	public String getFilter() {
		return mFilter;
	}
	public String getSearchBase() {
		return fixupExternalGalSearchBase(mSearchBase);
	}
	public String getAuthMech() {
		return mAuthMech;
	}
	public String getBindDn() {
		return mBindDn;
	}
	public String getBindPassword() {
		return mBindPassword;
	}
	public String getKerberosPrincipal() {
		return mKerberosPrincipal;
	}
	public String getKerberosKeytab() {
		return mKerberosKeytab;
	}
	public int getPageSize() {
		return mPageSize;
	}
	public LdapGalMapRules getRules() {
		return mRules;
	}
	public String getTokenizeKey() {
		return mTokenizeKey;
	}
	public void setGalType(GalType galType) {
		mGalType = galType;
	}
	public void setUrl(String[]  url) {
		mUrl = url;
	}
	public void setStartTlsEnabled(boolean startTlsEnabled) {
		mStartTlsEnabled = startTlsEnabled;
	}
	public void setFilter(String filter) {
		mFilter = filter;
	}
	public void setSearchBase(String searchBase) {
		mSearchBase = searchBase;
	}
	public void setAuthMech(String authMech) {
		mAuthMech = authMech;
	}
	public void setBindDn(String bindDn) {
		mBindDn = bindDn;
	}
	public void setBindPassword(String bindPassword) {
		mBindPassword = bindPassword;
	}
	public void setKerberosPrincipal(String kerberosPrincipal) {
		mKerberosPrincipal = kerberosPrincipal;
	}
	public void setKerberosKeytab(String kerberosKeytab) {
		mKerberosKeytab = kerberosKeytab;
	}
	public void setRules(LdapGalMapRules rules) {
		mRules = rules;
	}
	public void setTokenizeKey(String tokenizeKey) {
		mTokenizeKey = tokenizeKey;
	}

	public static String getFilterDef(NamedFilter filter) throws ServiceException {
	    return getFilterDef(filter.name());
	}
	
    public static String getFilterDef(String name) throws ServiceException {
        String queryExprs[] = Provisioning.getInstance().getConfig().getMultiAttr(Provisioning.A_zmailGalLdapFilterDef);
        String fname = name+":";
        String queryExpr = null;
        for (int i=0; i < queryExprs.length; i++) {
            if (queryExprs[i].startsWith(fname)) {
                queryExpr = queryExprs[i].substring(fname.length());
            }
        }
    
        if (queryExpr == null) {
            ZmailLog.gal.warn("missing filter def " + name + " in " + Provisioning.A_zmailGalLdapFilterDef);
        }
        return queryExpr;
    }

}
