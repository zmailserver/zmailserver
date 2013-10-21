/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.qa.unittest.prov.soap;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.zmail.common.account.ZAttrProvisioning;
import org.zmail.common.account.Key.GranteeBy;
import org.zmail.common.mailbox.ContactConstants;
import org.zmail.common.soap.SoapTransport;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.GranteeType;
import org.zmail.cs.account.accesscontrol.TargetType;
import org.zmail.qa.unittest.prov.Verify;
import org.zmail.soap.account.message.SearchGalRequest;
import org.zmail.soap.account.message.SearchGalResponse;
import org.zmail.soap.account.type.ContactInfo;
import org.zmail.soap.account.type.MemberOfSelector;
import org.zmail.soap.type.ContactAttr;
import org.zmail.soap.type.TargetBy;

public class TestSearchGalGroups extends SoapTest {
    private static SoapProvTestUtil provUtil;
    private static Provisioning prov;
    private static Domain domain;
    
    @BeforeClass
    public static void init() throws Exception {
        provUtil = new SoapProvTestUtil();
        prov = provUtil.getProv();
        domain = provUtil.createDomain(baseDomainName());
    }
    
    @AfterClass
    public static void cleanup() throws Exception {
        Cleanup.deleteAll(baseDomainName());
    }
    
    /*
     * Verify isOwner and isMember flags are returned correctly.
     */
    private void testSearchGroup(Account acct, Group group, 
            Boolean needIsOwner, MemberOfSelector needIsMember,
            boolean actualIsOwner, boolean actualIsMember) throws Exception {
        SoapTransport transport = authUser(acct.getName());
        
        SearchGalRequest req = new SearchGalRequest();
        req.setName(group.getName());
        req.setNeedIsOwner(needIsOwner);
        req.setNeedIsMember(needIsMember);
        
        SearchGalResponse resp = invokeJaxb(transport, req);
        
        List<String> result = Lists.newArrayList();
        List<String> expected = Lists.newArrayList(
                Verify.makeResultStr(
                        group.getName(), 
                        ContactConstants.TYPE_GROUP,
                        needIsOwner == null ? null : actualIsOwner,
                        needIsMember == null ? null : actualIsMember,
                        ZAttrProvisioning.DistributionListSubscriptionPolicy.ACCEPT.name()));
        
        List<ContactInfo> entries = resp.getContacts();
        for (ContactInfo entry : entries) {
            List<ContactAttr> attrs = entry.getAttrs();
            
            String email = null;
            String type = null;
            String subsPolicy = null;
            
            for (ContactAttr attr : attrs) {
                String key = attr.getKey();
                if (ContactConstants.A_email.equals(key)) {
                    email = attr.getValue();
                } else if (ContactConstants.A_type.equals(key)) {
                    type = attr.getValue();
                } else if (Provisioning.A_zmailDistributionListSubscriptionPolicy.equals(key)) {
                    subsPolicy = attr.getValue();
                }
            }
            
            Boolean isOwner = entry.isOwner();
            Boolean isMember = entry.isMember();
            
            result.add(Verify.makeResultStr(email, type, isOwner, isMember, subsPolicy));
        }
        Verify.verifyEquals(expected, result);
    }
    
    @Test
    public void searchGroup() throws Exception {
        SKIP_FOR_INMEM_LDAP_SERVER(SkipTestReason.DN_SUBTREE_MATCH_FILTER);
        
        Map<String, Object> attrs = Maps.newHashMap();
        attrs.put(Provisioning.A_zmailDistributionListSubscriptionPolicy, 
                ZAttrProvisioning.DistributionListSubscriptionPolicy.ACCEPT.name());
        Group group = provUtil.createGroup(genGroupNameLocalPart(), domain, attrs, false);
        Account acct = provUtil.createAccount(genAcctNameLocalPart(), domain);
        
        // make acct owner of the the group
        String right = Group.GroupOwner.GROUP_OWNER_RIGHT.getName();
        prov.grantRight(TargetType.dl.getCode(), TargetBy.name, group.getName(), 
                GranteeType.GT_USER.getCode(), GranteeBy.name, acct.getName(), null, 
                right, null);
        
        // make acct member of the group
        prov.addGroupMembers(group, new String[]{acct.getName()});
        
        // test with GSA on
        GalTestUtil.enableGalSyncAccount(prov, domain.getName());
        testSearchGroup(acct, group, Boolean.TRUE, MemberOfSelector.all, true, true);
        testSearchGroup(acct, group, Boolean.TRUE, null, true, true);
        testSearchGroup(acct, group, null, MemberOfSelector.all, true, true);
        testSearchGroup(acct, group, null, null, true, true);
        
        // test with GSA off
        GalTestUtil.disableGalSyncAccount(prov, domain.getName());
        testSearchGroup(acct, group, Boolean.TRUE, MemberOfSelector.all, true, true);
        testSearchGroup(acct, group, Boolean.TRUE, null, true, true);
        testSearchGroup(acct, group, null, MemberOfSelector.all, true, true);
        testSearchGroup(acct, group, null, null, true, true);
        
        provUtil.deleteGroup(group);
        provUtil.deleteAccount(acct);
    }
}
