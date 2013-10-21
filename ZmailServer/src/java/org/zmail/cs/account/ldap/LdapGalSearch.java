/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.account.ldap;

import java.io.IOException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.security.auth.login.LoginException;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.DateUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.GalContact;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Provisioning.SearchGalResult;
import org.zmail.cs.account.gal.GalOp;
import org.zmail.cs.account.gal.GalParams;
import org.zmail.cs.account.gal.GalUtil;
import org.zmail.cs.account.krb5.Krb5Login;
import org.zmail.cs.gal.GalSearchConfig;
import org.zmail.cs.gal.GalSearchParams;
import org.zmail.cs.ldap.LdapServerConfig.ExternalLdapConfig;
import org.zmail.cs.ldap.LdapConstants;
import org.zmail.cs.ldap.LdapException.LdapEntryNotFoundException;
import org.zmail.cs.ldap.LdapException.LdapSizeLimitExceededException;
import org.zmail.cs.ldap.LdapTODO.*;
import org.zmail.cs.ldap.ZLdapFilterFactory.FilterId;
import org.zmail.cs.ldap.IAttributes;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapTODO;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.LdapUtil;
import org.zmail.cs.ldap.SearchLdapOptions;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.ldap.ZSearchScope;

public class LdapGalSearch {
    
    
    public static SearchGalResult searchLdapGal(
            GalParams.ExternalGalParams galParams, 
            GalOp galOp,
            String n,
            int maxResults,
            LdapGalMapRules rules,
            String token,
            GalContact.Visitor visitor) throws ServiceException {
        
        String url[] = galParams.url();
        String base = galParams.searchBase();
        String filter = galParams.filter();
    
        SearchGalResult result = SearchGalResult.newSearchGalResult(visitor);
        String tokenize = GalUtil.tokenizeKey(galParams, galOp);
        result.setTokenizeKey(tokenize);
    
        if (url == null || url.length == 0 || base == null || filter == null) {
            if (url == null || url.length == 0)
                ZmailLog.gal.warn("searchLdapGal url is null");
            if (base == null)
                ZmailLog.gal.warn("searchLdapGal base is null");
            if (filter == null)
                ZmailLog.gal.warn("searchLdapGal queryExpr is null");
            return result;
        }
    
        if (filter.indexOf("(") == -1) {
            String queryExpr = GalSearchConfig.getFilterDef(filter);
            if (queryExpr != null)
                filter = queryExpr;
        }
        String query = GalUtil.expandFilter(tokenize, filter, n, token);
        
        String authMech = galParams.credential().getAuthMech();
        if (authMech.equals(Provisioning.LDAP_AM_KERBEROS5))
            searchLdapGalKrb5(galParams, galOp, query, maxResults, rules, token, result);
        else    
            searchLdapGal(galParams, galOp, query, maxResults, rules, token, result);
        return result;
    }
    
    private static void searchLdapGalKrb5(
            GalParams.ExternalGalParams galParams,
            GalOp galOp,
            String query, 
            int maxResults,
            LdapGalMapRules rules,
            String token,
            SearchGalResult result) throws ServiceException {
        
        try {
            LdapGalCredential credential = galParams.credential();
            Krb5Login.performAs(credential.getKrb5Principal(), credential.getKrb5Keytab(),
                    new SearchGalAction(galParams, galOp, query, maxResults, rules, token, result));
        } catch (LoginException le) {
            throw ServiceException.FAILURE("login failed, unable to search GAL", le);
        } catch (PrivilegedActionException pae) {
            // e.getException() should be an instance of NamingException,
            // as only "checked" exceptions will be wrapped in a PrivilegedActionException.
            Exception e = pae.getException();
            if (e instanceof ServiceException)
                throw (ServiceException)e;
            else // huh?
                throw ServiceException.FAILURE("caught exception, unable to search GAL", e); 
        }
    }
    
    static class SearchGalAction implements PrivilegedExceptionAction {
        
        GalParams.ExternalGalParams galParams;
        GalOp galOp;
        String query;
        int maxResults;
        LdapGalMapRules rules;
        String token;
        SearchGalResult result;
        
        SearchGalAction(GalParams.ExternalGalParams arg_galParams,
                        GalOp arg_galOp,
                        String arg_query, 
                        int arg_maxResults,
                        LdapGalMapRules arg_rules,
                        String arg_token,
                        SearchGalResult arg_result) {
            galParams = arg_galParams;
            galOp = arg_galOp;
            query = arg_query;
            maxResults = arg_maxResults;
            rules = arg_rules;
            token = arg_token;
            result = arg_result;
        }
            
        public Object run() throws ServiceException {
            searchLdapGal(galParams, galOp, query, maxResults, rules, token, result);
            return null;
        }
    }
    
    private static void searchLdapGal(
            GalParams.ExternalGalParams galParams,
            GalOp galOp,
            String query, 
            int maxResults,
            LdapGalMapRules rules,
            String token,
            SearchGalResult result) throws ServiceException {
        
        ZLdapContext zlc = null;
        try {
            LdapGalCredential credential = galParams.credential();
            ExternalLdapConfig ldapConfig = new ExternalLdapConfig(
                    galParams.url(), galParams.requireStartTLS(), 
                    credential.getAuthMech(), 
                    credential.getBindDn(), credential.getBindPassword(),
                    rules.getBinaryLdapAttrs(), "external GAL");
            
            zlc = LdapClient.getExternalContext(ldapConfig, LdapUsage.fromGalOpLegacy(galOp));
            searchGal(zlc,
                      GalSearchConfig.GalType.ldap,
                      galParams.pageSize(),
                      galParams.searchBase(), 
                      query, 
                      maxResults,
                      rules,
                      token,
                      result);
        } finally {
            LdapClient.closeContext(zlc);
        }
    }
    
    
    /* =========================================
     * 
     *         Methods for the new GAL
     *   
     * =========================================
     */
    
    public static void galSearch(GalSearchParams params) 
    throws ServiceException {
        String authMech = params.getConfig().getAuthMech();
        if (authMech.equals(Provisioning.LDAP_AM_KERBEROS5))
            galSearchKrb5(params);
        else    
            doGalSearch(params);
    }
    
    private static void doGalSearch(GalSearchParams params) throws ServiceException {
        
        ZLdapContext zlc = null;
        try {
            GalSearchConfig cfg = params.getConfig();
            GalSearchConfig.GalType galType =  params.getConfig().getGalType();

            if (galType == GalSearchConfig.GalType.zmail) {
                zlc = LdapClient.getContext(LdapUsage.fromGalOp(params.getOp()));
            } else {
                ExternalLdapConfig ldapConfig = new ExternalLdapConfig(
                        cfg.getUrl(), cfg.getStartTlsEnabled(), cfg.getAuthMech(),
                        cfg.getBindDn(), cfg.getBindPassword(), cfg.getRules().getBinaryLdapAttrs(), 
                        "external GAL");
                
                zlc = LdapClient.getExternalContext(ldapConfig, LdapUsage.fromGalOp(params.getOp()));
            }
            
            String fetchEntryByDn = params.getSearchEntryByDn();
            if (fetchEntryByDn == null) {
                searchGal(zlc,
                          galType,
                          cfg.getPageSize(),
                          cfg.getSearchBase(),
                          params.generateLdapQuery(),
                          params.getLimit(),
                          cfg.getRules(),
                          params.getSyncToken(),
                          params.getResult());
            } else {
                getGalEntryByDn(zlc, galType, fetchEntryByDn, cfg.getRules(), params.getResult());
            }
        } finally {
            LdapClient.closeContext(zlc);
        }
    }
    
    
    
    private static void galSearchKrb5(GalSearchParams params) throws ServiceException {
        
        try {
            String krb5Principal = params.getConfig().getKerberosPrincipal();
            String krb5Keytab = params.getConfig().getKerberosKeytab();
            Krb5Login.performAs(krb5Principal, krb5Keytab, new GalSearchAction(params));
        } catch (LoginException le) {
            throw ServiceException.FAILURE("login failed, unable to search GAL", le);
        } catch (PrivilegedActionException pae) {
            // e.getException() should be an instance of NamingException,
            // as only "checked" exceptions will be wrapped in a PrivilegedActionException.
            Exception e = pae.getException();
            if (e instanceof ServiceException)
                throw (ServiceException)e;
            else // huh?
                throw ServiceException.FAILURE("caught exception, unable to search GAL", e); 
        }
    }
    
    static class GalSearchAction implements PrivilegedExceptionAction {
        
        GalSearchParams mParams;
        
        GalSearchAction(GalSearchParams params) {
            mParams = params;
        }
            
        public Object run() throws ServiceException, IOException {
            doGalSearch(mParams);
            return null;
        }
    }

    private static class SearhcGalVisitor extends SearchLdapOptions.SearchLdapVisitor {

        private ZLdapContext zlc;
        private GalSearchConfig.GalType galType;
        private String base;
        private LdapGalMapRules rules;
        private SearchGalResult result;
        
        private SearhcGalVisitor(ZLdapContext zlc, GalSearchConfig.GalType galType, 
                String base, LdapGalMapRules rules, SearchGalResult result) {
            super(false);
            
            this.zlc = zlc;
            this.galType = galType;
            this.base = base;
            this.rules = rules;
            this.result = result;
        }
        
        @Override
        public void visit(String dn, IAttributes ldapAttrs) {
            GalContact lgc = new GalContact(galType, dn, rules.apply(zlc, base, dn, ldapAttrs));
            String mts = (String) lgc.getAttrs().get("modifyTimeStamp");
            result.setToken(LdapUtil.getLaterTimestamp(result.getToken(), mts));
            String cts = (String) lgc.getAttrs().get("createTimeStamp");
            result.setToken(LdapUtil.getLaterTimestamp(result.getToken(), cts));
            try {
                result.addMatch(lgc);
            } catch (ServiceException e) {
                ZmailLog.gal.warn("unable to add GAL match", e);
            }
            ZmailLog.gal.debug("dn=" + dn + ", mts=" + mts + ", cts=" + cts);
        }
        
    }
    
    public static void searchGal(ZLdapContext zlc,
                                 GalSearchConfig.GalType galType,
                                 int pageSize,
                                 String base, 
                                 String query, 
                                 int maxResults,
                                 LdapGalMapRules rules,
                                 String token,
                                 SearchGalResult result) throws ServiceException {

        String tk = token != null && !token.equals("")? token : LdapConstants.EARLIEST_SYNC_TOKEN;
        result.setToken(tk);
        
        String reqAttrs[] = rules.getLdapAttrs();
      
        if (ZmailLog.gal.isDebugEnabled()) {
            StringBuffer returnAttrs = new StringBuffer();
            for (String a: reqAttrs) {
                returnAttrs.append(a + ",");
            }
          
            zlc.debug();
            ZmailLog.gal.debug("searchGal: " +
                    ", page size=" + pageSize + 
                    ", max results=" + maxResults + 
                    ", base=" + base + 
                    ", query=" + query +
                    ", attrs=" + returnAttrs);
        }
      
        SearhcGalVisitor visitor = new SearhcGalVisitor(zlc, galType, base, rules, result);
      
        SearchLdapOptions searchOpts = new SearchLdapOptions(base, 
                ZLdapFilterFactory.getInstance().fromFilterString(FilterId.GAL_SEARCH, query), 
                reqAttrs, maxResults, null, ZSearchScope.SEARCH_SCOPE_SUBTREE, visitor);
        
        searchOpts.setResultPageSize(pageSize);
      
        try {
            zlc.searchPaged(searchOpts);
        } catch (LdapSizeLimitExceededException sle) {
            result.setHadMore(true);
        } catch (ServiceException e) {
            throw ServiceException.FAILURE("unable to search gal", e);
        } finally {
            boolean gotNewToken = true;
            String newToken = result.getToken();
            if (newToken == null || (token != null && token.equals(newToken)) || newToken.equals(LdapConstants.EARLIEST_SYNC_TOKEN))
                gotNewToken = false;
          
            if (gotNewToken) {
                Date parsedToken = DateUtil.parseGeneralizedTime(newToken, false);
                if (parsedToken != null) {
                    long ts = parsedToken.getTime();
                    ts += 1000;
                  
                    // Note, this will "normalize" the token to our standard format
                    // DateUtil.ZIMBRA_LDAP_GENERALIZED_TIME_FORMAT
                    // Whenever we've got a new token, it will be returned in the
                    // normalized format.
                    result.setToken(DateUtil.toGeneralizedTime(new Date(ts)));
                }
                /*
                 * in the rare case when an LDAP implementation does not conform to generalized time and 
                 * we cannot parser the token, just leave it alone.
                 */
            }
        }
    }
    
    public static void getGalEntryByDn(ZLdapContext zlc,
            GalSearchConfig.GalType galType,
            String dn,
            LdapGalMapRules rules,
            SearchGalResult result) throws ServiceException {
      
        String reqAttrs[] = rules.getLdapAttrs();
        
        if (ZmailLog.gal.isDebugEnabled()) {
            StringBuffer returnAttrs = new StringBuffer();
            for (String a: reqAttrs) {
                returnAttrs.append(a + ",");
            }
          
            zlc.debug();
            ZmailLog.gal.debug("getGalEntryByDn: " +
                    ", dn=" + dn +
                    ", attrs=" + returnAttrs);
        }
      
        SearhcGalVisitor visitor = new SearhcGalVisitor(zlc, galType, null, rules, result);
      
        try {
            ZAttributes attrs = zlc.getAttributes(dn, reqAttrs);
            visitor.visit(dn, attrs);
        } catch (LdapEntryNotFoundException e) {
            ZmailLog.gal.debug("getGalEntryByDn: no such dn: " + dn, e);
        } catch (ServiceException e) {
            throw ServiceException.FAILURE("unable to search gal", e);
        }
    }    

}
