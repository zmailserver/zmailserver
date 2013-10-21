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
package org.zmail.cs.account;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.common.service.ServiceException;
import org.zmail.common.account.Key.AccountBy;

/**
 * Unit test for {@link ZmailAuthToken}.
 *
 * @author ysasaki
 */
public class ZmailAuthTokenTest {

    @BeforeClass
    public static void init() throws ServiceException {
        MockProvisioning prov = new MockProvisioning();
        prov.createAccount("user1@example.zmail.com", "secret", new HashMap<String, Object>());
        Provisioning.setInstance(prov);
    }

    @Test
    public void test() throws Exception {
        Account a = Provisioning.getInstance().get(AccountBy.name, "user1@example.zmail.com");
        ZmailAuthToken at = new ZmailAuthToken(a);
        long start = System.currentTimeMillis();
        String encoded = at.getEncoded();
        for (int i = 0; i < 1000; i++) {
            new ZmailAuthToken(encoded);
        }
        System.out.println("Encoded 1000 auth-tokens elapsed=" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            ZmailAuthToken.getAuthToken(encoded);
        }
        System.out.println("Decoded 1000 auth-tokens elapsed=" + (System.currentTimeMillis() - start));
    }

}
