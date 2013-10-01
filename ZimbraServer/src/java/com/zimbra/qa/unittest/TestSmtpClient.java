/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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

package com.zimbra.qa.unittest;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import junit.framework.TestCase;

import com.zimbra.client.ZMailbox;
import com.zimbra.client.ZMessage;
import com.zimbra.client.ZMessage.ZMimePart;
import com.zimbra.common.mime.shim.JavaMailInternetAddress;
import com.zimbra.common.zmime.ZMimeMessage;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.mailclient.smtp.SmtpConfig;
import com.zimbra.cs.mailclient.smtp.SmtpConnection;
import com.zimbra.cs.util.JMSession;

public class TestSmtpClient extends TestCase {

    private static final String USER_NAME = "user1";
    private static final String USER2_NAME = "user2";
    private static final String NAME_PREFIX = TestSmtpClient.class.getSimpleName();

    private final String mHost;
    private final int mPort;

    public TestSmtpClient() throws Exception {
        mHost = TestUtil.getServerAttr(Provisioning.A_zimbraSmtpHostname);
        mPort = Integer.parseInt(TestUtil.getServerAttr(Provisioning.A_zimbraSmtpPort));
    }

    @Override
    public void setUp() throws Exception {
        cleanUp();
    }

    public void testSimple() throws Exception {
        String sender = USER_NAME;
        String[] recipients = { USER_NAME };
        sendAndVerify(sender, recipients, "1 line", "1 line", "1 line\r\n");
        sendAndVerify(sender, recipients, "1 line crlf", "1 line crlf\r\n", "1 line crlf");
        sendAndVerify(sender, recipients, "2 lines", "line1\r\nline2", "line1\r\nline2");
        sendAndVerify(sender, recipients, "2 lines crlf", "line1\r\nline2\r\n", "line1\r\nline2");
    }

    public void testTwoRecipients() throws Exception {
        String sender = USER_NAME;
        String[] recipients = { USER_NAME, USER2_NAME };
        sendAndVerify(sender, recipients, "2 recipients", "2 recipients\r\n", "2 recipients\r\n");
    }

    public void testTransparency() throws Exception {
        String sender = USER_NAME;
        String[] recipients = { USER_NAME };
        sendAndVerify(sender, recipients, "transparency1", "..line1\r\n", "..line1\r\n");
        sendAndVerify(sender, recipients, "transparency2", "line1\r\n.line2\r\n..line3\r\n...line4\r\n", "line1\r\n.line2\r\n..line3\r\n...line4\r\n");
    }

    public void testMimeMessage() throws Exception {
        // Assemble the message.
        MimeMessage mm = new ZMimeMessage(JMSession.getSession());
        InternetAddress addr = new JavaMailInternetAddress(TestUtil.getAddress(USER_NAME));
        mm.setFrom(addr);
        mm.setRecipient(RecipientType.TO, addr);
        String subject = NAME_PREFIX + " testMimeMessage";
        mm.setSubject(subject);
        mm.setText("testMimeMessage");
        mm.saveChanges();

        // Initialize SMTP client.
        SmtpConfig config = new SmtpConfig(mHost, mPort, "localhost");
        SmtpConnection conn = new SmtpConnection(config);
        conn.sendMessage(mm);

        // Make sure it arrived.
        ZMailbox mbox = TestUtil.getZMailbox(USER_NAME);
        TestUtil.waitForMessage(mbox, "in:inbox subject:\"" + subject + "\"");

        // Send the same message to a different envelope recipient.
        conn.sendMessage(addr.getAddress(), new String[] { TestUtil.getAddress(USER2_NAME) }, mm);
        mbox = TestUtil.getZMailbox(USER2_NAME);
        TestUtil.waitForMessage(mbox, "in:inbox subject:\"" + subject + "\"");
    }

    private void sendAndVerify(String sender, String[] recipients, String subject, String body, String expectedBody) throws Exception {
        // Fix up user names and subject.
        if (sender.indexOf("@") < 0) {
            sender = TestUtil.getAddress(sender);
        }
        for (int i = 0; i < recipients.length; i++) {
            if (recipients[i].indexOf("@") < 0) {
                recipients[i] = TestUtil.getAddress(recipients[i]);
            }
        }
        if (subject.indexOf(NAME_PREFIX) < 0) {
            subject = NAME_PREFIX + " " + subject;
        }

        // Send.
        String content = new MessageBuilder().withFrom(sender).withToRecipient(recipients[0]).withSubject(subject).withBody(body).create();
        SmtpConfig config = new SmtpConfig(mHost, mPort, "localhost");
        SmtpConnection smtp = new SmtpConnection(config);
        smtp.sendMessage(sender, recipients, content);

        // Verify.
        for (String recipient : recipients) {
            ZMailbox mbox = TestUtil.getZMailbox(recipient);
            ZMessage msg = TestUtil.waitForMessage(mbox, "in:inbox subject:\"" + subject + "\"");
            String currentBody = getBodyContent(msg.getMimeStructure());
            TestUtil.assertMessageContains(currentBody, expectedBody);
        }
    }

    private String getBodyContent(ZMimePart part) {
        if (part.isBody()) {
            return part.getContent();
        }
        for (ZMimePart child : part.getChildren()) {
            String content = getBodyContent(child);
            if (content != null) {
                return content;
            }
        }
        return null;
    }

    @Override
    public void tearDown() throws Exception {
        cleanUp();
    }

    public void cleanUp() throws Exception {
        TestUtil.deleteTestData(USER_NAME, NAME_PREFIX);
        TestUtil.deleteTestData(USER2_NAME, NAME_PREFIX);
    }

    public static void main(String[] args) throws Exception {
        TestUtil.cliSetup();
        TestUtil.runTest(TestSmtpClient.class);
    }
}
