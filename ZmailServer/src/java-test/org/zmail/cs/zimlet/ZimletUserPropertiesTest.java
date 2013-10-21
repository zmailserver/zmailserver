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
package org.zmail.cs.zimlet;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.MailboxTestUtil;

/**
 * Unit test for {@link ZimletUserProperties}
 *
 * @author ysasaki
 */
public final class ZimletUserPropertiesTest {
    @BeforeClass
    public static void init() throws Exception {
        MailboxTestUtil.initServer();
        Provisioning prov = Provisioning.getInstance();
        prov.createAccount("test@zmail.com", "secret", new HashMap<String, Object>());
    }

    @Before
    public void setUp() throws Exception {
        MailboxTestUtil.clearData();
    }

    @Test
    public void save() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        Account account = prov.getAccount(MockProvisioning.DEFAULT_ACCOUNT_ID);
        ZimletUserProperties prop = ZimletUserProperties.getProperties(account);
        prop.setProperty("phone", "123123", "aaaaaaaaaaaa");
        prop.setProperty("phone", "number", "bar");
        prop.saveProperties(account);

        String[] values = account.getZimletUserProperties();
        Arrays.sort(values);
        Assert.assertArrayEquals(new String[] {"phone:123123:aaaaaaaaaaaa", "phone:number:bar"}, values);
    }
}
