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
package org.zmail.cs.service.mail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import org.zmail.common.account.Key;
import org.zmail.common.mailbox.Color;
import org.zmail.common.mailbox.ContactConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.SoapProtocol;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxTestUtil;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mime.ParsedContact;
import org.zmail.cs.service.AuthProvider;
import org.zmail.soap.SoapEngine;
import org.zmail.soap.ZmailSoapContext;

public class SyncTest {
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
    public void tags() throws Exception {
        Account acct = Provisioning.getInstance().get(Key.AccountBy.name, "test@zmail.com");
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(acct);

        mbox.beginTrackingSync();

        // create one tag implicitly and one explicitly
        ParsedContact pc = new ParsedContact(ImmutableMap.of(ContactConstants.A_firstName, "Bob", ContactConstants.A_lastName, "Smith"));
        mbox.createContact(null, pc, Mailbox.ID_FOLDER_CONTACTS, new String[] { "bar" }).getId();
        int tagId = mbox.createTag(null, "foo", new Color((byte) 4)).getId();

        Element request = new Element.XMLElement(MailConstants.SYNC_REQUEST);

        Map<String, Object> context = new HashMap<String, Object>();
        context.put(SoapEngine.ZIMBRA_CONTEXT, new ZmailSoapContext(AuthProvider.getAuthToken(acct), acct.getId(), SoapProtocol.Soap12, SoapProtocol.Soap12));
        Element response = new Sync().handle(request, context);
        String token = response.getAttribute(MailConstants.A_TOKEN);

        // check that only the explicitly-created tag is returned in the sync response
        Element tagFolder = null;
        for (Element hidden : response.getElement(MailConstants.E_FOLDER).listElements(MailConstants.E_FOLDER)) {
            if (hidden.getAttribute(MailConstants.A_NAME).equals("Tags")) {
                tagFolder = hidden;
                break;
            }
        }
        Assert.assertNotNull("could not find Tags folder in initial sync response", tagFolder);
        Assert.assertEquals("1 tag listed", 1, tagFolder.listElements(MailConstants.E_TAG).size());
        Element t = tagFolder.listElements(MailConstants.E_TAG).get(0);
        Assert.assertEquals("listed tag named 'foo'", "foo", t.getAttribute(MailConstants.A_NAME));
        Assert.assertEquals("correct color for tag 'foo'", 4, t.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR));

        // change tag color and re-sync
        mbox.setColor(null, tagId, MailItem.Type.TAG, (byte) 6);
//        mbox.purge(MailItem.Type.TAG);
        request = new Element.XMLElement(MailConstants.SYNC_REQUEST).addAttribute(MailConstants.A_TOKEN, token);

        response = new Sync().handle(request, context);
        Assert.assertFalse("sync token change after tag color", token.equals(response.getAttribute(MailConstants.A_TOKEN)));
        token = response.getAttribute(MailConstants.A_TOKEN);

        // make sure the modified tag is returned in the sync response
        t = response.getOptionalElement(MailConstants.E_TAG);
        Assert.assertNotNull("modified tag missing", t);
        Assert.assertEquals("new color on serialized tag", 6, t.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR));
    }
}
