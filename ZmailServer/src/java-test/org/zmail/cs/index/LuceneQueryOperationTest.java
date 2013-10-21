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
package org.zmail.cs.index;

import java.util.EnumSet;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.common.soap.SoapProtocol;
import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.DeliveryOptions;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxTestUtil;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mime.ParsedMessage;

/**
 * Unit test for {@link LuceneQueryOperation}.
 *
 * @author ysasaki
 */
public final class LuceneQueryOperationTest {

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
    public void notClause() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions dopt = new DeliveryOptions().setFolderId(Mailbox.ID_FOLDER_INBOX);
        mbox.addMessage(null, new ParsedMessage("From: test1@zmail.com".getBytes(), false), dopt, null);
        Message msg2 = mbox.addMessage(null, new ParsedMessage("From: test2@zmail.com".getBytes(), false), dopt, null);
        Message msg3 = mbox.addMessage(null, new ParsedMessage("From: test3@zmail.com".getBytes(), false), dopt, null);
        MailboxTestUtil.index(mbox);

        SearchParams params = new SearchParams();
        params.setQueryString("-from:test1@zmail.com");
        params.setTypes(EnumSet.of(MailItem.Type.MESSAGE));
        params.setSortBy(SortBy.NONE);
        ZmailQuery query = new ZmailQuery(new OperationContext(mbox), SoapProtocol.Soap12, mbox, params);
        ZmailQueryResults results = query.execute();
        Assert.assertTrue(results.hasNext());
        Assert.assertEquals(msg2.getId(), results.getNext().getItemId());
        Assert.assertTrue(results.hasNext());
        Assert.assertEquals(msg3.getId(), results.getNext().getItemId());
        Assert.assertFalse(results.hasNext());
        results.close();
    }

    @Test
    public void notClauses() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions dopt = new DeliveryOptions().setFolderId(Mailbox.ID_FOLDER_INBOX);
        mbox.addMessage(null, new ParsedMessage("From: test1@zmail.com".getBytes(), false), dopt, null);
        Message msg2 = mbox.addMessage(null, new ParsedMessage("From: test2@zmail.com".getBytes(), false), dopt, null);
        Message msg3 = mbox.addMessage(null, new ParsedMessage("From: test3@zmail.com".getBytes(), false), dopt, null);
        MailboxTestUtil.index(mbox);

        SearchParams params = new SearchParams();
        params.setQueryString("-from:(test1 zmail.com)");
        params.setTypes(EnumSet.of(MailItem.Type.MESSAGE));
        params.setSortBy(SortBy.NONE);
        ZmailQuery query = new ZmailQuery(new OperationContext(mbox), SoapProtocol.Soap12, mbox, params);
        ZmailQueryResults results = query.execute();
        Assert.assertTrue(results.hasNext());
        Assert.assertEquals(msg2.getId(), results.getNext().getItemId());
        Assert.assertTrue(results.hasNext());
        Assert.assertEquals(msg3.getId(), results.getNext().getItemId());
        Assert.assertFalse(results.hasNext());
        results.close();
    }

    @Test
    public void andClauses() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions dopt = new DeliveryOptions().setFolderId(Mailbox.ID_FOLDER_INBOX);
        Message msg1 = mbox.addMessage(null, new ParsedMessage("From: test1@zmail.com".getBytes(), false), dopt, null);
        mbox.addMessage(null, new ParsedMessage("From: test2@zmail.com".getBytes(), false), dopt, null);
        mbox.addMessage(null, new ParsedMessage("From: test3@zmail.com".getBytes(), false), dopt, null);
        MailboxTestUtil.index(mbox);

        SearchParams params = new SearchParams();
        params.setQueryString("from:test1 from:zmail.com -from:vmware.com");
        params.setTypes(EnumSet.of(MailItem.Type.MESSAGE));
        params.setSortBy(SortBy.NONE);
        ZmailQuery query = new ZmailQuery(new OperationContext(mbox), SoapProtocol.Soap12, mbox, params);
        ZmailQueryResults results = query.execute();
        Assert.assertTrue(results.hasNext());
        Assert.assertEquals(msg1.getId(), results.getNext().getItemId());
        Assert.assertFalse(results.hasNext());
        results.close();
    }

    @Test
    public void subjectQuery() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions dopt = new DeliveryOptions().setFolderId(Mailbox.ID_FOLDER_INBOX);
        Message msg = mbox.addMessage(null, new ParsedMessage("Subject: one two three".getBytes(), false), dopt, null);
        MailboxTestUtil.index(mbox);

        // phrase query
        SearchParams params = new SearchParams();
        params.setQueryString("subject:\"one two three\"");
        params.setTypes(EnumSet.of(MailItem.Type.MESSAGE));
        params.setSortBy(SortBy.NONE);
        ZmailQuery query = new ZmailQuery(new OperationContext(mbox), SoapProtocol.Soap12, mbox, params);
        ZmailQueryResults results = query.execute();
        Assert.assertTrue(results.hasNext());
        Assert.assertEquals(msg.getId(), results.getNext().getItemId());
        results.close();

        // verify subject is not repeated during index
        params = new SearchParams();
        params.setQueryString("subject:\"three one\"");
        params.setTypes(EnumSet.of(MailItem.Type.MESSAGE));
        params.setSortBy(SortBy.NONE);
        query = new ZmailQuery(new OperationContext(mbox), SoapProtocol.Soap12, mbox, params);
        results = query.execute();
        Assert.assertFalse(results.hasNext());
        results.close();
    }

}
