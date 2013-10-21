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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.junit.*;
import static org.junit.Assert.*;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.ldap.IAttributes;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapConstants;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.SearchLdapOptions;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZLdapFilter;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.ldap.ZSearchControls;
import org.zmail.cs.ldap.ZSearchResultEntry;
import org.zmail.cs.ldap.ZSearchResultEnumeration;
import org.zmail.cs.ldap.ZSearchScope;
import org.zmail.cs.ldap.LdapException.LdapSizeLimitExceededException;

public class TestLdapZLdapContext extends LdapTest {
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
    
    @Test
    public void searchPaged() throws Exception {
        int SIZE_LIMIT = 5;
        
        String base = LdapConstants.DN_ROOT_DSE;
        ZLdapFilter filter = ZLdapFilterFactory.getInstance().anyEntry();
        String returnAttrs[] = new String[]{"objectClass"};
        
        final List<String> result = new ArrayList<String>();
        
        SearchLdapOptions.SearchLdapVisitor visitor = new SearchLdapOptions.SearchLdapVisitor() {
            @Override
            public void visit(String dn, Map<String, Object> attrs, IAttributes ldapAttrs) {
                result.add(dn);
            }
        };
        
        SearchLdapOptions searchOptions = new SearchLdapOptions(
                base, filter, returnAttrs, SIZE_LIMIT, null, 
                ZSearchScope.SEARCH_SCOPE_SUBTREE, visitor);
        
        boolean caughtException = false;
        
        ZLdapContext zlc = null;
        try {
            zlc = LdapClient.getContext(LdapUsage.UNITTEST);
            zlc.searchPaged(searchOptions);
        } catch (LdapSizeLimitExceededException e) {
            caughtException = true;
        } finally {
            LdapClient.closeContext(zlc);
        }
        
        assertTrue(caughtException);
        assertEquals(SIZE_LIMIT, result.size());
    }
    
    @Test
    public void searchDir() throws Exception {
        int SIZE_LIMIT = 5;
        
        String base = LdapConstants.DN_ROOT_DSE;
        ZLdapFilter filter = ZLdapFilterFactory.getInstance().anyEntry();
        String returnAttrs[] = new String[]{"objectClass"};
        
        ZSearchControls searchControls = ZSearchControls.createSearchControls(
                ZSearchScope.SEARCH_SCOPE_SUBTREE, 
                SIZE_LIMIT, returnAttrs);
        
        int numFound = 0;
        boolean caughtException = false;
        
        ZLdapContext zlc = null;
        try {
            zlc = LdapClient.getContext(LdapUsage.UNITTEST);
            
            ZSearchResultEnumeration ne = zlc.searchDir(base, filter, searchControls);
            while (ne.hasMore()) {
                ZSearchResultEntry sr = ne.next();
                numFound++;
            }
            ne.close();
            
        } catch (LdapSizeLimitExceededException e) {
            caughtException = true;
        } finally {
            LdapClient.closeContext(zlc);
        }
        
        assertTrue(caughtException);
        
        /*
        // unboundid does not return entries if LdapSizeLimitExceededException
        // is thrown,  See commons on ZLdapContext.searchDir().
        if (testConfig != TestLdap.TestConfig.UBID) {
            assertEquals(SIZE_LIMIT, numFound);
        }
        */
    }
}
