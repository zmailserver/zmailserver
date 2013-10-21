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
package org.zmail.qa.unittest.prov.ldap;

import java.util.HashMap;
import java.util.Map;

import org.junit.*;
import static org.junit.Assert.*;

import org.zmail.common.account.ZAttrProvisioning;
import org.zmail.common.localconfig.LC;
import org.zmail.common.mailbox.ContactConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.GalContact;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Provisioning.SearchGalResult;
import org.zmail.cs.account.gal.GalOp;
import org.zmail.cs.gal.GalSearchConfig;
import org.zmail.cs.gal.GalSearchParams;
import org.zmail.qa.unittest.prov.Names;
import org.zmail.soap.type.GalSearchType;

public class TestLdapProvGal extends LdapTest {
    private static LdapProvTestUtil provUtil;
    private static Provisioning prov;
    private static Domain domain;
    
    @BeforeClass
    public static void init() throws Exception {
        provUtil = new LdapProvTestUtil();
        prov = provUtil.getProv();
        domain = provUtil.createDomain(baseDomainName());
    }
    
    @AfterClass
    public static void cleanup() throws Exception {
        Cleanup.deleteAll(baseDomainName());
    }
    
    private Account createAccount(String localPart) throws Exception {
        return createAccount(localPart, null);
    }
    
    private Account createAccount(String localPart, Map<String, Object> attrs) throws Exception {
        return provUtil.createAccount(localPart, domain, attrs);
    }
    
    private void deleteAccount(Account acct) throws Exception {
        provUtil.deleteAccount(acct);
    }
    
    @Test
    public void checkGalConfig() throws Exception {
        String ACCT_NAME_LOCALPART = Names.makeAccountNameLocalPart(genAcctNameLocalPart());
        Account acct = createAccount(ACCT_NAME_LOCALPART);
        
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailGalMode, Provisioning.GalMode.ldap.name());
        attrs.put(Provisioning.A_zmailGalLdapURL, "ldap://" + LC.zmail_server_hostname.value() + ":389");
        attrs.put(Provisioning.A_zmailGalLdapFilter, "(mail=*%s*)");
        
        attrs.put(Provisioning.A_zmailGalLdapBindDn, "cn=config");
        attrs.put(Provisioning.A_zmailGalLdapBindPassword, "zmail");
        
        String query = "checkGalConfig";
        int limit = 0;
        GalOp galOp = GalOp.search;
        prov.checkGalConfig(attrs, query, limit, galOp);
        
        deleteAccount(acct);
    }
    
    @Test
    public void searchGal() throws Exception {
        SKIP_FOR_INMEM_LDAP_SERVER(SkipTestReason.DN_SUBTREE_MATCH_FILTER);
        
        int NUM_ACCTS = 10;
        int SIZE_LIMIT = 5;
        
        Account user = null;
        for (int i = 0; i < NUM_ACCTS; i++) {
            String ACCT_NAME_LOCALPART = Names.makeAccountNameLocalPart(genAcctNameLocalPart(String.valueOf(i)));
            Account acct = createAccount(ACCT_NAME_LOCALPART);
            
            // use the first one as the user
            if (user == null) {
                user = acct;
            }
        }
        
        GalSearchType type = GalSearchType.account;
        String query = "searchGal";
        
        GalSearchParams params = new GalSearchParams(user);
        SearchGalResult result = SearchGalResult.newSearchGalResult(null);
        
        params.setOp(GalOp.search);
        params.setType(type);
        params.createSearchConfig(GalSearchConfig.GalType.zmail);
        params.setQuery(query);
        params.setLimit(SIZE_LIMIT);
        params.setGalResult(result);
        prov.searchGal(params);
        
        assertEquals(SIZE_LIMIT, result.getMatches().size());
        assertTrue(result.getHadMore());
        
        result = prov.searchGal(domain, query, type, 0, null);
        assertEquals(NUM_ACCTS, result.getMatches().size());
        assertFalse(result.getHadMore());
    }

    @Test
    @Ignore
    public void searchGalADGroupMember() throws Exception {
        String DOMAIN_NAME = genDomainSegmentName() + "." + baseDomainName();
        
        Map<String, Object> domainAttrs = new HashMap<String, Object>();
        domainAttrs.put(Provisioning.A_zmailGalMode, ZAttrProvisioning.GalMode.ldap.name());
        
        // replace XXX to real VMware AD credentials before checking in.
        // NEVER check in the crdentials
        domainAttrs.put(Provisioning.A_zmailGalLdapURL, "ldap://XXX.vmware.com:3268");  
        domainAttrs.put(Provisioning.A_zmailGalLdapBindDn, "XXX@vmware.com");
        domainAttrs.put(Provisioning.A_zmailGalLdapBindPassword, "XXX");
        
        domainAttrs.put(Provisioning.A_zmailGalLdapFilter, "ad");
        domainAttrs.put(Provisioning.A_zmailGalLdapGroupHandlerClass, "org.zmail.cs.gal.ADGalGroupHandler");
        Domain galDomain = provUtil.createDomain(DOMAIN_NAME, domainAttrs);
        
        String ACCT_NAME_LOCALPART = Names.makeAccountNameLocalPart("searchGalADGroupMember");
        Account user = provUtil.createAccount(ACCT_NAME_LOCALPART, galDomain, null);
        
        GalSearchType type = GalSearchType.group;
        String query = "server-pm@vmware.com";
        
        GalSearchParams params = new GalSearchParams(user);
        SearchGalResult result = SearchGalResult.newSearchGalResult(null);
        
        params.setOp(GalOp.search);
        params.setType(type);
        params.setFetchGroupMembers(true);  // has to be set before calling createSearchConfig
        params.createSearchConfig(GalSearchConfig.GalType.ldap);
        params.setQuery(query);
        params.setGalResult(result);
                
        prov.searchGal(params);
        assertEquals(1, result.getMatches().size());
        GalContact galContact = result.getMatches().get(0);
        assertTrue(galContact.isGroup());
        Map<String, Object> galEntryAttrs = galContact.getAttrs();
        Object members = galEntryAttrs.get(ContactConstants.A_member);
        assertTrue(members instanceof String[]);
        assertEquals(6, ((String[]) members).length);
    }
}
