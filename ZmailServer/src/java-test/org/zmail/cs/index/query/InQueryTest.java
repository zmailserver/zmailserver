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
package org.zmail.cs.index.query;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxTestUtil;
import org.zmail.cs.service.util.ItemId;

/**
 * Unit test for {@link InQuery}.
 *
 * @author ysasaki
 */
public final class InQueryTest {

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
    public void inAnyFolder() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);

        Query query = InQuery.create(mbox, new ItemId(MockProvisioning.DEFAULT_ACCOUNT_ID, 1), null, true);
        Assert.assertEquals("Q(UNDER:ANY_FOLDER)", query.toString());

        query = InQuery.create(mbox, new ItemId(MockProvisioning.DEFAULT_ACCOUNT_ID, 1), null, false);
        Assert.assertEquals("Q(IN:USER_ROOT)", query.toString());

        query = InQuery.create(mbox, new ItemId("1-1-1", 1), null, true);
        Assert.assertEquals("Q(UNDER:1-1-1:1)", query.toString());

        query = InQuery.create(mbox, new ItemId("1-1-1", 1), null, false);
        Assert.assertEquals("Q(IN:1-1-1:1)", query.toString());
    }

}
