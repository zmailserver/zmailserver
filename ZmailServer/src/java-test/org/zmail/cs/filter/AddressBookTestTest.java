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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.common.mailbox.ContactConstants;
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
import org.zmail.cs.mime.ParsedContact;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.service.util.ItemId;

/**
 * Unit test for {@link AddressBookTest}.
 *
 * @author ysasaki
 */
public final class AddressBookTestTest {

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
    public void filter() throws Exception {
        Account account = Provisioning.getInstance().getAccount(MockProvisioning.DEFAULT_ACCOUNT_ID);
        RuleManager.clearCachedRules(account);
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account);
        mbox.createContact(null, new ParsedContact(Collections.<String, Object>singletonMap(
                ContactConstants.A_email, "test1@zmail.com")), Mailbox.ID_FOLDER_CONTACTS, null);

        account.setMailSieveScript("if addressbook :in \"From\" { tag \"Priority\"; }");
        List<ItemId> ids = RuleManager.applyRulesToIncomingMessage(new OperationContext(mbox), mbox,
                new ParsedMessage("From: test1@zmail.com".getBytes(), false), 0, account.getName(),
                new DeliveryContext(), Mailbox.ID_FOLDER_INBOX, true);
        Assert.assertEquals(1, ids.size());
        Message msg = mbox.getMessageById(null, ids.get(0).getId());
        Assert.assertEquals("Priority", ArrayUtil.getFirstElement(msg.getTags()));
    }

}
