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
package org.zmail.cs.account.gal;

import java.util.Map;

import org.zmail.common.account.ProvisioningConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ldap.LdapGalCredential;
import org.zmail.cs.gal.GalSearchConfig;
import org.zmail.cs.gal.ZmailGalSearchBase;
import org.zmail.cs.ldap.LdapConnType;

public abstract class GalParams {
    
    int mPageSize;
    String mTokenizeAutoCompleteKey;
    String mTokenizeSearchKey;
    
    GalParams(Entry ldapEntry, GalOp galOp) throws ServiceException {
        
        String pageSize = null;
        if (galOp == GalOp.sync) {
            pageSize = ldapEntry.getAttr(Provisioning.A_zmailGalSyncLdapPageSize);
            
            if (pageSize == null)
                pageSize = ldapEntry.getAttr(Provisioning.A_zmailGalLdapPageSize);
        } else {
            pageSize = ldapEntry.getAttr(Provisioning.A_zmailGalLdapPageSize);
        }
        
        setPageSize(pageSize);
        
        mTokenizeAutoCompleteKey = ldapEntry.getAttr(Provisioning.A_zmailGalTokenizeAutoCompleteKey);
        mTokenizeSearchKey = ldapEntry.getAttr(Provisioning.A_zmailGalTokenizeSearchKey);
        
    }
    
    GalParams(Map attrs, GalOp galOp) {
        String pageSize = null;
        if (galOp == GalOp.sync) {
            pageSize = (String)attrs.get(Provisioning.A_zmailGalSyncLdapPageSize);
            
            if (pageSize == null)
                pageSize = (String)attrs.get(Provisioning.A_zmailGalLdapPageSize);
        } else {
            pageSize = (String)attrs.get(Provisioning.A_zmailGalLdapPageSize);
        }
        
        setPageSize(pageSize);
        
        mTokenizeAutoCompleteKey = (String)attrs.get(Provisioning.A_zmailGalTokenizeAutoCompleteKey);
        mTokenizeSearchKey = (String)attrs.get(Provisioning.A_zmailGalTokenizeSearchKey);
    }
    
    private void setPageSize(String pageSize) {
        if (pageSize == null)
            pageSize = "1000";
        
        try {
            mPageSize = Integer.parseInt(pageSize);
        } catch (NumberFormatException e) {
            mPageSize = 0;
        }
        
    }
    
    public int pageSize() { return mPageSize; }
    public String tokenizeAutoCompleteKey() { return mTokenizeAutoCompleteKey; }
    public String tokenizeSearchKey() { return mTokenizeSearchKey; } 
    
    /*
     * ZmailGalParams
     *
     */
    public static class ZmailGalParams extends GalParams {
        String mSearchBase;
        
        public ZmailGalParams(Domain domain, GalOp galOp) throws ServiceException {
            super(domain, galOp); 
            mSearchBase = ZmailGalSearchBase.getSearchBase(domain, galOp);
        }
        
        public String searchBase() { return mSearchBase; }
    }
    
    public static class ExternalGalParams extends GalParams {
        String mUrl[];
        boolean mRequireStartTLS;
        String mSearchBase;
        String mFilter;
        LdapGalCredential mCredential;
        
        public ExternalGalParams(Entry ldapEntry, GalOp galOp) throws ServiceException {
            super(ldapEntry, galOp);
            
            String startTlsEnabled;
            String authMech;
            String bindDn;
            String bindPassword;
            String krb5Principal;
            String krb5Keytab;
            
            if (galOp == GalOp.sync) {
                mUrl = ldapEntry.getMultiAttr(Provisioning.A_zmailGalSyncLdapURL);
                mSearchBase = ldapEntry.getAttr(Provisioning.A_zmailGalSyncLdapSearchBase);
                mFilter = ldapEntry.getAttr(Provisioning.A_zmailGalSyncLdapFilter);
                
                startTlsEnabled = ldapEntry.getAttr(Provisioning.A_zmailGalSyncLdapStartTlsEnabled);
                authMech = ldapEntry.getAttr(Provisioning.A_zmailGalSyncLdapAuthMech);
                bindDn = ldapEntry.getAttr(Provisioning.A_zmailGalSyncLdapBindDn);
                bindPassword = ldapEntry.getAttr(Provisioning.A_zmailGalSyncLdapBindPassword);
                krb5Principal = ldapEntry.getAttr(Provisioning.A_zmailGalSyncLdapKerberos5Principal);
                krb5Keytab = ldapEntry.getAttr(Provisioning.A_zmailGalSyncLdapKerberos5Keytab);
                
                // fallback to zmailGalLdap attrs is sync specific params are not set
                if (mUrl == null || mUrl.length == 0)
                    mUrl = ldapEntry.getMultiAttr(Provisioning.A_zmailGalLdapURL);
                if (mSearchBase == null)
                    mSearchBase = ldapEntry.getAttr(Provisioning.A_zmailGalLdapSearchBase, "");
                if (mFilter == null)
                    mFilter = ldapEntry.getAttr(Provisioning.A_zmailGalLdapFilter);
                
                if (startTlsEnabled == null)
                    startTlsEnabled = ldapEntry.getAttr(Provisioning.A_zmailGalLdapStartTlsEnabled);
                if (authMech == null)
                    authMech = ldapEntry.getAttr(Provisioning.A_zmailGalLdapAuthMech);
                if (bindDn == null)
                    bindDn = ldapEntry.getAttr(Provisioning.A_zmailGalLdapBindDn);
                if (bindPassword == null)
                    bindPassword = ldapEntry.getAttr(Provisioning.A_zmailGalLdapBindPassword);
                if (krb5Principal == null)
                    krb5Principal = ldapEntry.getAttr(Provisioning.A_zmailGalLdapKerberos5Principal);
                if (krb5Keytab == null)
                    krb5Keytab = ldapEntry.getAttr(Provisioning.A_zmailGalLdapKerberos5Keytab);
                
            } else {
                mUrl = ldapEntry.getMultiAttr(Provisioning.A_zmailGalLdapURL);
                mSearchBase = ldapEntry.getAttr(Provisioning.A_zmailGalLdapSearchBase, "");
                
                if (galOp == GalOp.autocomplete)
                    mFilter = ldapEntry.getAttr(Provisioning.A_zmailGalAutoCompleteLdapFilter);
                else
                    mFilter = ldapEntry.getAttr(Provisioning.A_zmailGalLdapFilter);
            
                startTlsEnabled = ldapEntry.getAttr(Provisioning.A_zmailGalLdapStartTlsEnabled);
                authMech = ldapEntry.getAttr(Provisioning.A_zmailGalLdapAuthMech);
                bindDn = ldapEntry.getAttr(Provisioning.A_zmailGalLdapBindDn);
                bindPassword = ldapEntry.getAttr(Provisioning.A_zmailGalLdapBindPassword);
                krb5Principal = ldapEntry.getAttr(Provisioning.A_zmailGalLdapKerberos5Principal);
                krb5Keytab = ldapEntry.getAttr(Provisioning.A_zmailGalLdapKerberos5Keytab);
            }
            
            boolean startTLS = startTlsEnabled == null ? false : ProvisioningConstants.TRUE.equals(startTlsEnabled);
            mRequireStartTLS = LdapConnType.requireStartTLS(mUrl,  startTLS);
            mCredential = new LdapGalCredential(authMech, bindDn, bindPassword, krb5Principal, krb5Keytab);
        }
        
        
        /*
         * called from Check, where there isn't a domain object
         * 
         * TODO, need admin UI work for zmailGalLdapStartTlsEnabled/zmailGalSyncLdapStartTlsEnabled
         */
        public ExternalGalParams(Map attrs, GalOp galOp) throws ServiceException {
            super(attrs, galOp);
            
            String startTlsEnabled;
            String authMech;
            String bindDn;
            String bindPassword;
            String krb5Principal;
            String krb5Keytab;
            
            if (galOp == GalOp.sync) {
                mUrl = getMultiAttr(attrs, Provisioning.A_zmailGalSyncLdapURL, false);
                mSearchBase = (String)attrs.get(Provisioning.A_zmailGalSyncLdapSearchBase);
                mFilter = (String)attrs.get(Provisioning.A_zmailGalSyncLdapFilter);
                
                startTlsEnabled = (String)attrs.get(Provisioning.A_zmailGalSyncLdapStartTlsEnabled);
                authMech = (String)attrs.get(Provisioning.A_zmailGalSyncLdapAuthMech);
                bindDn = (String)attrs.get(Provisioning.A_zmailGalSyncLdapBindDn);
                bindPassword = (String)attrs.get(Provisioning.A_zmailGalSyncLdapBindPassword);
                krb5Principal = (String)attrs.get(Provisioning.A_zmailGalSyncLdapKerberos5Principal);
                krb5Keytab = (String)attrs.get(Provisioning.A_zmailGalSyncLdapKerberos5Keytab);
                
                // fallback to zmailGalLdap attrs is sync specific params are not set
                if (mUrl == null || mUrl.length == 0)
                    mUrl = getMultiAttr(attrs, Provisioning.A_zmailGalLdapURL, true);
                if (mSearchBase == null)
                    mSearchBase = (String)attrs.get(Provisioning.A_zmailGalLdapSearchBase);
                if (mFilter == null)
                    mFilter = getRequiredAttr(attrs, Provisioning.A_zmailGalLdapFilter);
                
                if (startTlsEnabled == null)
                    startTlsEnabled = (String)attrs.get(Provisioning.A_zmailGalLdapStartTlsEnabled);
                if (authMech == null)
                    authMech = (String)attrs.get(Provisioning.A_zmailGalLdapAuthMech);
                if (bindDn == null)
                    bindDn = (String)attrs.get(Provisioning.A_zmailGalLdapBindDn);
                if (bindPassword == null)
                    bindPassword = (String)attrs.get(Provisioning.A_zmailGalLdapBindPassword);
                if (krb5Principal == null)
                    krb5Principal = (String)attrs.get(Provisioning.A_zmailGalLdapKerberos5Principal);
                if (krb5Keytab == null)
                    krb5Keytab = (String)attrs.get(Provisioning.A_zmailGalLdapKerberos5Keytab);
                
            } else {
                mUrl = getMultiAttr(attrs, Provisioning.A_zmailGalLdapURL, true);
                mSearchBase = (String)attrs.get(Provisioning.A_zmailGalLdapSearchBase);
                
                if (galOp == GalOp.autocomplete)
                    mFilter = getRequiredAttr(attrs, Provisioning.A_zmailGalAutoCompleteLdapFilter);
                else
                    mFilter = getRequiredAttr(attrs, Provisioning.A_zmailGalLdapFilter);
            
                startTlsEnabled = (String)attrs.get(Provisioning.A_zmailGalLdapStartTlsEnabled);
                authMech = (String)attrs.get(Provisioning.A_zmailGalLdapAuthMech);
                bindDn = (String)attrs.get(Provisioning.A_zmailGalLdapBindDn);
                bindPassword = (String)attrs.get(Provisioning.A_zmailGalLdapBindPassword);
                krb5Principal = (String)attrs.get(Provisioning.A_zmailGalLdapKerberos5Principal);
                krb5Keytab = (String)attrs.get(Provisioning.A_zmailGalLdapKerberos5Keytab);
            }
                
            boolean startTLS = startTlsEnabled == null ? false : ProvisioningConstants.TRUE.equals(startTlsEnabled);
            mRequireStartTLS = LdapConnType.requireStartTLS(mUrl,  startTLS);
            mCredential = new LdapGalCredential(authMech, bindDn, bindPassword, krb5Principal, krb5Keytab);
        }
        
        private static String[] getMultiAttr(Map attrs, String name, boolean required) throws ServiceException {
            Object v = attrs.get(name);
            if (v instanceof String) return new String[] {(String)v};
            else if (v instanceof String[]) {
                String value[] = (String[]) v;
                if (value != null && value.length > 0)
                    return value;
            }
            if (required)
                throw ServiceException.INVALID_REQUEST("must specifiy: "+name, null);
            else
                return null;
        }
        
        private static String getRequiredAttr(Map attrs, String name) throws ServiceException {
            String value = (String) attrs.get(name);
            if (value == null)
                throw ServiceException.INVALID_REQUEST("must specifiy: "+name, null);
            return value;
        }
        
        public String[] url() { return mUrl; }
        public boolean requireStartTLS() { return mRequireStartTLS; }
        public String searchBase() { return GalSearchConfig.fixupExternalGalSearchBase(mSearchBase); }
        public String filter() { return mFilter; }
        public LdapGalCredential credential() { return mCredential; }

    }
}
