/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
import java.util.ArrayList;

import org.junit.*;

import static org.junit.Assert.*;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.ACL;
import org.zmail.client.ZAce;
import org.zmail.client.ZFolder;
import org.zmail.client.ZGrant;
import org.zmail.client.ZMailbox;
import org.zmail.qa.QA.Bug;
import org.zmail.qa.unittest.TestUtil;

public class TestACLUserRights extends SoapTest {

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
    
    private Account createUserAccount(String localPart) throws Exception {
        return provUtil.createAccount(localPart, domain);
    }
    
    @Test
    @Bug(bug=42146)
    public void testFallbackToFolderRight() throws Exception {
        
        // grantees
        Account allowed = createUserAccount("allowed");
        Account denied = createUserAccount("denied");
        Account noAclButHasFolderGrant = createUserAccount("noAclButHasFolderGrant");
        Account noAclAndNoFolderGrant = createUserAccount("noAclAndNoFolderGrant");
        
        // owner
        Account owner = createUserAccount("owner");
        
        ZMailbox ownerMbox = TestUtil.getZMailbox(owner.getName());
        
        // grant account right
        ZAce aceAllow = new ZAce(ZAce.GranteeType.usr, allowed.getId(), allowed.getName(), "invite", false, null);
        ownerMbox.grantRight(aceAllow);
        ZAce aceDeny = new ZAce(ZAce.GranteeType.usr, denied.getId(), denied.getName(), "invite", true, null);
        ownerMbox.grantRight(aceDeny);
        
        // grant folder right
        String folderPath = "/Calendar";
        short rights = ACL.RIGHT_READ | ACL.RIGHT_WRITE | ACL.RIGHT_INSERT | ACL.RIGHT_DELETE;
        String rightsStr = ACL.rightsToString(rights);
        ZFolder folder = ownerMbox.getFolder(folderPath);
        ownerMbox.modifyFolderGrant(folder.getId(), ZGrant.GranteeType.usr, denied.getName(), rightsStr, null);
        ownerMbox.modifyFolderGrant(folder.getId(), ZGrant.GranteeType.usr, noAclButHasFolderGrant.getName(), rightsStr, null);
        
        // check permission
        List<String> rightsToCheck = new ArrayList<String>();
        rightsToCheck.add("invite");
        boolean result;
            
        result = TestUtil.getZMailbox(allowed.getName()).checkRights(owner.getName(), rightsToCheck);
        assertTrue(result);
         
        result = TestUtil.getZMailbox(denied.getName()).checkRights(owner.getName(), rightsToCheck);
        assertTrue(result);
        
        result = TestUtil.getZMailbox(noAclButHasFolderGrant.getName()).checkRights(owner.getName(), rightsToCheck);
        assertTrue(result);
        
        result = TestUtil.getZMailbox(noAclAndNoFolderGrant.getName()).checkRights(owner.getName(), rightsToCheck);
        assertFalse(result);
    }

}
