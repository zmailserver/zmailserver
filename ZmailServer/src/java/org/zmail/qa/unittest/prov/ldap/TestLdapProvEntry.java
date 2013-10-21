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

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.*;
import static org.junit.Assert.*;

import org.zmail.common.account.Key;
import org.zmail.common.account.ZAttrProvisioning;
import org.zmail.common.util.Constants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.EntryCacheDataKey;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.ldap.LdapObjectClass;
import org.zmail.cs.ldap.LdapConstants;
import org.zmail.cs.ldap.unboundid.InMemoryLdapServer;
import org.zmail.qa.unittest.TestUtil;
import org.zmail.qa.unittest.prov.BinaryLdapData;

public class TestLdapProvEntry extends LdapTest {
    private static final String DOMAIN_NAME = "\u4e2d\u6587" + "." + baseDomainName();  // an IDN domain name
    private static final String ACCTNAME_LOCAL_PART = "test-ldap-prov-entry";
    
    private static final BinaryLdapData.Content binaryData = BinaryLdapData.Content.generateContent(1024);
    
    private static LdapProvTestUtil provUtil;
    private static Provisioning prov;
    private static Domain domain;
    private static Cos cos;
    private static Server server;
    private static Account account;
    private static Account entry;
    
    @BeforeClass
    public static void init() throws Exception {
        provUtil = new LdapProvTestUtil();
        prov = provUtil.getProv();
        
        domain = provUtil.createDomain(DOMAIN_NAME, new HashMap<String, Object>());
        cos = prov.get(Key.CosBy.name, Provisioning.DEFAULT_COS_NAME);
        server = prov.getLocalServer();
        
        BinaryLdapData.Content content = BinaryLdapData.Content.generateContent(1024);
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_userSMIMECertificate, binaryData.getString());
        
        account = provUtil.createAccount(ACCTNAME_LOCAL_PART, domain, attrs);
        entry = account;
    }
    
    @AfterClass
    public static void cleanup() throws Exception {
        Cleanup.deleteAll(DOMAIN_NAME);
    }
    
    private void verifyAccountObjectClass(Collection<String> values) {
        if (InMemoryLdapServer.isOn()) {
            /*
             * ubid InMemoryDirectoryServer returns all direct and indirect OCs
             * 
             * inetOrgPerson
             * organizationalPerson
             * person
             * top
             * zmailAccount
             * zmailMailRecipient
             * amavisAccount
             */
            assertEquals(7, values.size());
        } else {
            /*
             * inetOrgPerson
             * zmailAccount
             * amavisAccount
             */
            assertEquals(3, values.size());
        }
    }
    
    @Test
    public void getLabel() throws Exception {
        assertEquals(account.getName(), entry.getLabel());
    }
    
    @Test
    public void getAttrDefault() throws Exception {
        String ATTRNAME_NO_DEFAULT = Provisioning.A_zmailId;
        String ATTRNAME_HAS_DEFAULT = Provisioning.A_zmailFeatureContactsEnabled;
        
        assertNull(entry.getAttrDefault(ATTRNAME_NO_DEFAULT));
        assertEquals(cos.getAttr(ATTRNAME_HAS_DEFAULT), entry.getAttrDefault(ATTRNAME_HAS_DEFAULT));
    }
    
    @Test
    public void getAttr() throws Exception {
        assertEquals(account.getId(), entry.getAttr(Provisioning.A_zmailId));
        assertEquals(account.getName(), entry.getAttr(Provisioning.A_mail));
        assertEquals(account.getName(), entry.getAttr(Provisioning.A_zmailMailDeliveryAddress));
        assertEquals(ACCTNAME_LOCAL_PART, entry.getAttr(Provisioning.A_uid));
        assertEquals(server.getName(), entry.getAttr(Provisioning.A_zmailMailHost));
        assertEquals(ZAttrProvisioning.AccountStatus.active.name(), entry.getAttr(Provisioning.A_zmailAccountStatus));
        assertEquals(ZAttrProvisioning.MailStatus.enabled.name(), entry.getAttr(Provisioning.A_zmailMailStatus));
    }
    
    @Test
    public void getAttrWithWithoutDefaults() throws Exception {
        String ATTR = Provisioning.A_zmailFeatureMailEnabled;
        
        // apply defaults
        String value = entry.getAttr(ATTR, true);
        assertEquals(LdapConstants.LDAP_TRUE, value);
        
        // do not apply defaults
        value = entry.getAttr(ATTR, false);
        assertNull(value);
    }
    
    @Test
    public void getAttrWithProvidedDefaultValue() throws Exception {
        String ATTR = Provisioning.A_zmailACE;
        String DEFAULT_VALUE_PROVIDED = "blah";
        
        // make sure there is no value on the entry or inherited
        String value = entry.getAttr(ATTR, false);
        assertNull(value);
        
        value = entry.getAttr(ATTR, DEFAULT_VALUE_PROVIDED);
        assertEquals(DEFAULT_VALUE_PROVIDED, value);
    }
    
    @Test
    public void getAttrs() throws Exception {
        Map<String, Object> attrs = entry.getAttrs();
        
        Object value = attrs.get(Provisioning.A_objectClass);
        assertTrue(value instanceof String[]);
        List<String> values = Arrays.asList((String[]) value);
        
        verifyAccountObjectClass(values);
        assertTrue(values.contains(LdapObjectClass.ZIMBRA_DEFAULT_PERSON_OC));
        assertTrue(values.contains(AttributeClass.account.getOCName()));
        assertTrue(values.contains("amavisAccount"));
        
        value = attrs.get(Provisioning.A_zmailId);
        assertTrue(value instanceof String);
        assertEquals(entry.getId(), (String) value);
    }
    
    @Test
    public void getAttrsWithWithoutDefaults() throws Exception {
        String ATTR = Provisioning.A_zmailFeatureMailEnabled;
        
        // apply defaults
        Map<String, Object> attrs = entry.getAttrs(true);
        Object value = attrs.get(ATTR);
        assertTrue(value instanceof String);
        assertEquals(LdapConstants.LDAP_TRUE, value);
        
        // do not apply defaults
        attrs = entry.getAttrs(false);
        value = attrs.get(ATTR);
        assertNull(value);
    }
    
    @Test
    public void getUnicodeAttrs() throws Exception {
        Map<String, Object> attrs = entry.getUnicodeAttrs();
        
        Object value = attrs.get(Provisioning.A_zmailMailDeliveryAddress);
        assertTrue(value instanceof String);
        String parts[] = ((String) value).split("@");
        assertEquals(ACCTNAME_LOCAL_PART, parts[0]);
        assertEquals(DOMAIN_NAME, parts[1]);
    }
    
    @Test
    public void getUnicodeAttrsWithWithoutDefaults() throws Exception {
        @SuppressWarnings("unused")
        Map<String, Object> attrs = entry.getUnicodeAttrs(true);
        
        // can't find a good test case, not important.
        
        attrs = entry.getUnicodeAttrs(false);
    }
    
    @Test
    public void getBooleanAttr()  throws Exception {
        String ATTR = Provisioning.A_zmailFeatureMailEnabled;
        
        boolean value = entry.getBooleanAttr(ATTR, false);
        assertTrue(value);  // becasue cos already has a value, default provided here will not be effective
    }
    
    @Test
    public void getBinaryAttr() throws Exception {
        byte[] value = entry.getBinaryAttr(Provisioning.A_userSMIMECertificate);
        assertTrue(binaryData.equals(value));
    }
    
    @Test
    public void getGeneralizedTimeAttr() throws Exception {
        String ATTR = Provisioning.A_zmailCreateTimestamp;
        
        Date now = new Date();
        Date value = entry.getGeneralizedTimeAttr(ATTR, null);
        
        // check roughly the time should be within one minute. i.e. since the beginning of the test
        assertTrue(now.getTime() - value.getTime() < 60000);
    }
    
    @Test
    public void getIntAttr() throws Exception {
        String ATTR = Provisioning.A_zmailContactAutoCompleteMaxResults;
        
        int value = entry.getIntAttr(ATTR, 0);
        assertEquals(20, value);
    }
    
    
    @Test
    public void getLocale() throws Exception {
        Locale locale = entry.getLocale();
        
        assertEquals(Locale.US, locale);
    }
    
    @Test
    public void getLongAttr() throws Exception {
        
    }
    
    @Test
    public void getMultiAttr() throws Exception {
        String[] value = entry.getMultiAttr(Provisioning.A_objectClass);
        
        assertTrue(value instanceof String[]);
        List<String> values = Arrays.asList((String[]) value);
        verifyAccountObjectClass(values);
        assertTrue(values.contains(LdapObjectClass.ZIMBRA_DEFAULT_PERSON_OC));
        assertTrue(values.contains(AttributeClass.account.getOCName()));
        assertTrue(values.contains("amavisAccount"));
    }
    
    @Test
    public void getMultiBinaryAttr() throws Exception {
        List<byte[]> value = entry.getMultiBinaryAttr(Provisioning.A_userSMIMECertificate);
        assertEquals(1, value.size());
        assertTrue(binaryData.equals(value.get(0)));
    }
    
    @Test
    public void getUnicodeMultiAttr() throws Exception {
        String[] value = entry.getUnicodeMultiAttr(Provisioning.A_zmailMailDeliveryAddress);
        assertEquals(1, value.length);
        assertEquals(TestUtil.getAddress(ACCTNAME_LOCAL_PART, DOMAIN_NAME), value[0]);
    }
    
    @Test
    public void getMultiAttrWithWithoutDefaults() throws Exception {
        String ATTR = Provisioning.A_zmailFeatureMailEnabled;
        
        // apply defaults
        String[] value = entry.getMultiAttr(ATTR, true);
        assertEquals(1, value.length);
        assertEquals(LdapConstants.LDAP_TRUE, value[0]);
        
        // do not apply defaults
        value = entry.getMultiAttr(ATTR, false);
        assertEquals(0, value.length);
    }
    
    @Test
    public void getMultiBinaryAttrWithWithoutDefaults() throws Exception {
        // List<byte[]> getMultiBinaryAttr(String name, boolean applyDefaults)
    }
    
    @Test
    public void getMultiAttrSet() throws Exception {
        String ATTR = Provisioning.A_objectClass;
        
        Set<String> values = entry.getMultiAttrSet(ATTR);
        verifyAccountObjectClass(values);
        assertTrue(values.contains(LdapObjectClass.ZIMBRA_DEFAULT_PERSON_OC));
        assertTrue(values.contains(AttributeClass.account.getOCName()));
        assertTrue(values.contains("amavisAccount"));
    }
    
    @Test 
    public void getMultiBinaryAttrSet() throws Exception {
        Set<byte[]> value = entry.getMultiBinaryAttrSet(Provisioning.A_userSMIMECertificate);
        assertEquals(1, value.size());
        for (byte[] val : value) {
            assertTrue(binaryData.equals(val));
        }
    }
    
    @Test 
    public void getTimeInterval() throws Exception {
        String ATTR = Provisioning.A_zmailMailTrashLifetime;
        long value = entry.getTimeInterval(ATTR, 0);
        long expected = 30 * Constants.MILLIS_PER_DAY;
        assertEquals(expected, value);
    }
    
    @Test 
    public void getTimeIntervalSecs() throws Exception {
        String ATTR = Provisioning.A_zmailMailTrashLifetime;
        long value = entry.getTimeIntervalSecs(ATTR, 0);
        long expected = 30 * Constants.SECONDS_PER_DAY;
        assertEquals(expected, value);
    }
    
    @Test 
    public void setCachedDataString() throws Exception {
        String KEY = EntryCacheDataKey.PERMISSION.name();
        String DATA = "123";
        entry.setCachedData(KEY, DATA);
        Object dataCached = entry.getCachedData(KEY);
        assertEquals(DATA, dataCached);
    }
    
    @Test 
    public void setCachedData() throws Exception {
        EntryCacheDataKey KEY = EntryCacheDataKey.PERMISSION;
        String DATA = "abc";
        entry.setCachedData(KEY, DATA);
        Object dataCached = entry.getCachedData(KEY);
        assertEquals(DATA, dataCached);
    }
    
    @Test 
    public void getCachedDataString() throws Exception {
        // tested in setCachedDataString
    }
    
    @Test 
    public void getCachedData() throws Exception {
        // tested in setCachedData
    }

}
