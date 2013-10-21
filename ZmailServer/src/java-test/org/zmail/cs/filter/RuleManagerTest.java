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
package org.zmail.cs.filter;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.common.util.ArrayUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.DeliveryContext;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxTestUtil;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.service.util.ItemId;

/**
 * Unit test for {@link RuleManager}.
 *
 * @author ysasaki
 */
public final class RuleManagerTest {

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
    public void tagAndFileinto() throws Exception {
        Account account = Provisioning.getInstance().getAccount(MockProvisioning.DEFAULT_ACCOUNT_ID);
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account);

        RuleManager.clearCachedRules(account);
        account.setMailSieveScript("if socialcast { tag \"priority\"; fileinto \"socialcast\"; }");
        List<ItemId> ids = RuleManager.applyRulesToIncomingMessage(new OperationContext(mbox), mbox, new ParsedMessage(
                "From: do-not-reply@socialcast.com\nReply-To: share@socialcast.com\nSubject: test".getBytes(), false),
                0, account.getName(), new DeliveryContext(), Mailbox.ID_FOLDER_INBOX, true);
        Assert.assertEquals(1, ids.size());
        Message msg = mbox.getMessageById(null, ids.get(0).getId());
        Assert.assertEquals("socialcast", mbox.getFolderById(null, msg.getFolderId()).getName());
        Assert.assertEquals("priority", ArrayUtil.getFirstElement(msg.getTags()));

        RuleManager.clearCachedRules(account);
        account.setMailSieveScript("if socialcast { tag \"priority\"; }\n" +
                "if header :contains [\"Subject\"] [\"Zmail\"] { fileinto \"zmail\"; }");
        ids = RuleManager.applyRulesToIncomingMessage(new OperationContext(mbox), mbox, new ParsedMessage(
                "From: do-not-reply@socialcast.com\nReply-To: share@socialcast.com\nSubject: Zmail".getBytes(), false),
                0, account.getName(), new DeliveryContext(), Mailbox.ID_FOLDER_INBOX, true);
        Assert.assertEquals(1, ids.size());
        msg = mbox.getMessageById(null, ids.get(0).getId());
        Assert.assertEquals("zmail", mbox.getFolderById(null, msg.getFolderId()).getName());
        Assert.assertEquals("priority", ArrayUtil.getFirstElement(msg.getTags()));
    }

    @Test
    public void tagAndTag() throws Exception {
        Account account = Provisioning.getInstance().getAccount(MockProvisioning.DEFAULT_ACCOUNT_ID);
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account);

        RuleManager.clearCachedRules(account);
        account.setMailSieveScript("if socialcast { tag \"priority\"; tag \"socialcast\"; }");
        List<ItemId> ids = RuleManager.applyRulesToIncomingMessage(new OperationContext(mbox), mbox, new ParsedMessage(
                "From: do-not-reply@socialcast.com\nReply-To: share@socialcast.com\nSubject: test".getBytes(), false),
                0, account.getName(), new DeliveryContext(), Mailbox.ID_FOLDER_INBOX, true);
        Assert.assertEquals(1, ids.size());
        Message msg = mbox.getMessageById(null, ids.get(0).getId());
        Assert.assertArrayEquals(new String[] { "priority", "socialcast" }, msg.getTags());

        RuleManager.clearCachedRules(account);
        account.setMailSieveScript("if socialcast { tag \"priority\"; }\n" +
                "if header :contains [\"Subject\"] [\"Zmail\"] { tag \"zmail\"; }");
        ids = RuleManager.applyRulesToIncomingMessage(new OperationContext(mbox), mbox, new ParsedMessage(
                "From: do-not-reply@socialcast.com\nReply-To: share@socialcast.com\nSubject: Zmail".getBytes(), false),
                0, account.getName(), new DeliveryContext(), Mailbox.ID_FOLDER_INBOX, true);
        Assert.assertEquals(1, ids.size());
        msg = mbox.getMessageById(null, ids.get(0).getId());
        Assert.assertArrayEquals(new String[] { "priority", "zmail" }, msg.getTags());
    }

}
