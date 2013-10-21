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
package org.zmail.cs.service.mail;

import javax.mail.internet.MimeMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Maps;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxTestUtil;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.cs.util.JMSession;
import org.zmail.soap.ZmailSoapContext;

public class SaveDraftTest {

    @BeforeClass
    public static void init() throws Exception {
        MailboxTestUtil.initServer();
        Provisioning prov = Provisioning.getInstance();

        prov.createAccount("test@zmail.com", "secret", Maps.<String, Object>newHashMap());
    }

    @Before
    public void setUp() throws Exception {
        MailboxTestUtil.clearData();
    }

    private static String nCopiesOf(char c, int copies) {
        StringBuilder sb = new StringBuilder(copies);
        for (int i = 0; i < copies; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    // string lengths should be greater than both MessageCache.MESSAGE_CACHE_DISK_STREAMING_THRESHOLD
    //   and LC.zmail_blob_input_stream_buffer_size_kb * 1024
    static final String ORIGINAL_CONTENT = nCopiesOf('a', 8192);
    static final String MODIFIED_CONTENT = nCopiesOf('b', 8192);

    @Test
    public void deleteRace() throws Exception {
        Account acct = Provisioning.getInstance().getAccountByName("test@zmail.com");

        // create a draft via SOAP
        Element request = new Element.JSONElement(MailConstants.SAVE_DRAFT_REQUEST);
        Element m = request.addElement(MailConstants.E_MSG).addAttribute(MailConstants.E_SUBJECT, "dinner appt");
        m.addUniqueElement(MailConstants.E_MIMEPART).addAttribute(MailConstants.A_CONTENT_TYPE, "text/plain").addAttribute(MailConstants.E_CONTENT, ORIGINAL_CONTENT);

        Element response = new SaveDraft() {
            @Override
            protected Element generateResponse(ZmailSoapContext zsc, ItemIdFormatter ifmt, OperationContext octxt, Mailbox mbox, Message msg) {
                // trigger the failure case by deleting the draft before it's serialized out
                try {
                    mbox.delete(null, msg.getId(), MailItem.Type.MESSAGE);
                } catch (Exception e) {
                    return null;
                }
                return super.generateResponse(zsc, ifmt, octxt, mbox, msg);
            }
        }.handle(request, ServiceTestUtil.getRequestContext(acct));

        // make sure the response has no <m> element
        Assert.assertNull("picked up delete", response.getOptionalElement(MailConstants.E_MSG));
    }

    @Test
    public void updateRace() throws Exception {
        Account acct = Provisioning.getInstance().getAccountByName("test@zmail.com");

        // create a draft via SOAP
        Element request = new Element.JSONElement(MailConstants.SAVE_DRAFT_REQUEST);
        Element m = request.addElement(MailConstants.E_MSG).addAttribute(MailConstants.E_SUBJECT, "dinner appt");
        m.addUniqueElement(MailConstants.E_MIMEPART).addAttribute(MailConstants.A_CONTENT_TYPE, "text/plain").addAttribute(MailConstants.E_CONTENT, ORIGINAL_CONTENT);

        Element response = new SaveDraft() {
            @Override
            protected Element generateResponse(ZmailSoapContext zsc, ItemIdFormatter ifmt, OperationContext octxt, Mailbox mbox, Message msg) {
                // trigger the failure case by re-saving the draft before it's serialized out
                try {
                    msg = (Message) msg.snapshotItem();

                    MimeMessage mm = new MimeMessage(JMSession.getSession());
                    mm.setText(MODIFIED_CONTENT);
                    mm.saveChanges();
                    mbox.saveDraft(null, new ParsedMessage(mm, false), msg.getId());
                } catch (Exception e) {
                    return null;
                }
                return super.generateResponse(zsc, ifmt, octxt, mbox, msg);
            }
        }.handle(request, ServiceTestUtil.getRequestContext(acct));

        // make sure the response has the correct message content
        Assert.assertEquals("picked up modified content", MODIFIED_CONTENT, response.getElement(MailConstants.E_MSG).getElement(MailConstants.E_MIMEPART).getAttribute(MailConstants.E_CONTENT));
    }

}
