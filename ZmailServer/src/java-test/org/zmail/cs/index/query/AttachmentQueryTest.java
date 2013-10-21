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
package org.zmail.cs.index.query;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.index.LuceneQueryOperation;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxTestUtil;

/**
 * Unit test for {@link AttachmentQuery}.
 *
 * @author ysasaki
 */
public final class AttachmentQueryTest {

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
    public void toQueryString() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        AttachmentQuery query = new AttachmentQuery("any");
        Assert.assertEquals("(attachment:any)", ((LuceneQueryOperation) query.compile(mbox, true)).toQueryString());
    }

    @Test
    public void notAny() throws Exception {
        AttachmentQuery query = new AttachmentQuery("any");
        query.setModifier(Query.Modifier.MINUS);
        Assert.assertEquals("Q(attachment:none)", query.toString());
    }

}
