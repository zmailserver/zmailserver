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

import org.junit.*;
import static org.junit.Assert.*;

import org.zmail.cs.ldap.LdapUtil;

public class TestLdapUtil extends LdapTest {

    @Test
    public void testAuthDN() {
        
        assertEquals("schemers@example.zmail.com", 
                LdapUtil.computeDn("schemers@example.zmail.com", null));
        
        assertEquals("schemers@example.zmail.com",
                LdapUtil.computeDn("schemers@example.zmail.com", ""));
        
        assertEquals("WTF",
                LdapUtil.computeDn("schemers@example.zmail.com", "WTF"));
        
        assertEquals("schemers@example.zmail.com",
                LdapUtil.computeDn("schemers@example.zmail.com", "%n"));
        
        assertEquals("schemers",
                LdapUtil.computeDn("schemers@example.zmail.com", "%u"));
        
        assertEquals("example.zmail.com",
                LdapUtil.computeDn("schemers@example.zmail.com", "%d"));
        
        assertEquals("dc=example,dc=zmail,dc=com",
                LdapUtil.computeDn("schemers@example.zmail.com", "%D"));
        
        assertEquals("uid=schemers,ou=people,dc=example,dc=zmail,dc=com",
                LdapUtil.computeDn("schemers@example.zmail.com", "uid=%u,ou=people,%D"));
        
        assertEquals("n(schemers@example.zmail.com)u(schemers)d(example.zmail.com)D(dc=example,dc=zmail,dc=com)(%)",
                LdapUtil.computeDn("schemers@example.zmail.com", "n(%n)u(%u)d(%d)D(%D)(%%)"));
    }
    
    @Test
    @Ignore  // only for experiment
    public void rdnUBID() throws Exception {
        // com.unboundid.ldap.sdk.RDN rdn = new com.unboundid.ldap.sdk.RDN("cn", "foo+/+/ \u4e2d\u6587");
        String rawValue = "## ,+\"\\<>;\u4e2d\u6587---createIdentity ";
        
        com.unboundid.ldap.sdk.RDN rdn = new com.unboundid.ldap.sdk.RDN("cn", rawValue);
        String minStr = rdn.toMinimallyEncodedString();
        String rdnStr = rdn.toNormalizedString();
        System.out.println(minStr);
        System.out.println(rdnStr);
        
        
        
        String escapedValue = com.unboundid.ldap.sdk.Filter.encodeValue(rawValue);
        System.out.println(escapedValue);
        
        /*
        String raw = "(&(objectclass=zmailIdentity)(zmailPrefIdentityName=## ,+\"\\<>;\u4e2d\u6587---createIdentity ))";
        String escaped = com.unboundid.ldap.sdk.Filter.encodeValue(raw);
        System.out.println(escaped);
        */
        
        /*
        com.unboundid.ldap.sdk.Filter filter = 
            com.unboundid.ldap.sdk.Filter.create("(&(objectclass=zmailIdentity)(zmailPrefIdentityName=## ,+\"\\<>;\u4e2d\u6587---createIdentity ))");
        String norm = filter.toNormalizedString();
        System.out.println(norm);
        */
        /*
        String rdn = "cn=foo, bar";
        String norm = com.unboundid.ldap.sdk.RDN.normalize(rdn);
        com.unboundid.ldap.sdk.RDN RDN = new com.unboundid.ldap.sdk.RDN(norm);
        String min = RDN.toMinimallyEncodedString();
        System.out.println(rdn);
        System.out.println(norm);
        System.out.println(min);
        */
        
    }
    
    @Test
    public void escapeSearchFilterArg() {
        assertEquals("\\(", LdapUtil.escapeSearchFilterArg("("));
        assertEquals("\\)", LdapUtil.escapeSearchFilterArg(")"));
        assertEquals("\\*", LdapUtil.escapeSearchFilterArg("*"));
        assertEquals("\\\\", LdapUtil.escapeSearchFilterArg("\\"));
    }
}
