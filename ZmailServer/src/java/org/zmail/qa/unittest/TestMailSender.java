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
package org.zmail.qa.unittest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.SharedByteArrayInputStream;

import junit.framework.TestCase;

import org.zmail.common.mime.shim.JavaMailInternetAddress;
import org.zmail.common.service.ServiceException.Argument;
import org.zmail.common.zmime.ZMimeMessage;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.mailbox.MailSender;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mime.Mime.FixedMimeMessage;
import org.zmail.cs.util.JMSession;

public class TestMailSender
extends TestCase {

    static final int TEST_SMTP_PORT = 6025;
    private static final String NAME_PREFIX = TestMailSender.class.getSimpleName();
    private static final String SENDER_NAME = "user1";
    private static final String RECIPIENT_NAME = "user2";
    private String mOriginalSmtpPort = null;
    private String mOriginalSmtpSendPartial;
    private String mOriginalAllowAnyFrom;

    @Override
    public void setUp()
    throws Exception {
        mOriginalSmtpPort = Provisioning.getInstance().getLocalServer().getSmtpPortAsString();
        mOriginalSmtpSendPartial = TestUtil.getServerAttr(Provisioning.A_zmailSmtpSendPartial);
        mOriginalAllowAnyFrom = TestUtil.getAccountAttr(SENDER_NAME, Provisioning.A_zmailAllowAnyFromAddress);
    }

    public void testRejectRecipient()
    throws Exception {
        String errorMsg = "Sender address rejected: User unknown in relay recipient table";
        String bogusAddress = TestUtil.getAddress("bogus");
        startDummySmtpServer(bogusAddress, errorMsg);
        Server server = Provisioning.getInstance().getLocalServer();
        server.setSmtpPort(TEST_SMTP_PORT);

        String content = TestUtil.getTestMessage(NAME_PREFIX + " testRejectSender", bogusAddress, SENDER_NAME, null);
        MimeMessage msg = new ZMimeMessage(JMSession.getSession(), new SharedByteArrayInputStream(content.getBytes()));
        Mailbox mbox = TestUtil.getMailbox(SENDER_NAME);

        // Test reject first recipient, get partial send value from LDAP.
        boolean sendFailed = false;
        server.setSmtpSendPartial(false);
        try {
            mbox.getMailSender().sendMimeMessage(null, mbox, msg);
        } catch (MailServiceException e) {
            validateException(e, MailServiceException.SEND_ABORTED_ADDRESS_FAILURE, bogusAddress, errorMsg);
            sendFailed = true;
        }
        assertTrue(sendFailed);

        // Test reject first recipient, set partial send value explicitly.
        startDummySmtpServer(bogusAddress, errorMsg);
        sendFailed = false;
        server.setSmtpSendPartial(true);
        MailSender sender = mbox.getMailSender().setSendPartial(false);

        try {
            sender.sendMimeMessage(null, mbox, msg);
        } catch (MailServiceException e) {
            validateException(e, MailServiceException.SEND_ABORTED_ADDRESS_FAILURE, bogusAddress, errorMsg);
            sendFailed = true;
        }
        assertTrue(sendFailed);

        // Test reject second recipient, get partial send value from LDAP.
        startDummySmtpServer(bogusAddress, errorMsg);
        sendFailed = false;
        String validAddress = TestUtil.getAddress(RECIPIENT_NAME);
        InternetAddress[] recipients = new InternetAddress[2];
        recipients[0] = new JavaMailInternetAddress(validAddress);
        recipients[1] = new JavaMailInternetAddress(bogusAddress);
        msg.setRecipients(MimeMessage.RecipientType.TO, recipients);
        server.setSmtpSendPartial(false);
        try {
            mbox.getMailSender().sendMimeMessage(null, mbox, msg);
        } catch (MailServiceException e) {
            validateException(e, MailServiceException.SEND_ABORTED_ADDRESS_FAILURE, bogusAddress, errorMsg);
            sendFailed = true;
        }
        assertTrue(sendFailed);

        // Test partial send, get value from LDAP.
        startDummySmtpServer(bogusAddress, errorMsg);
        server.setSmtpSendPartial(true);
        sendFailed = false;
        try {
            mbox.getMailSender().sendMimeMessage(null, mbox, msg);
        } catch (MailServiceException e) {
            validateException(e, MailServiceException.SEND_PARTIAL_ADDRESS_FAILURE, bogusAddress, null);
            sendFailed = true;
        }
        assertTrue(sendFailed);

        // Test partial send, specify value explicitly.
        server.setSmtpSendPartial(false);
        startDummySmtpServer(bogusAddress, errorMsg);
        sendFailed = false;
        sender = mbox.getMailSender().setSendPartial(true);
        try {
            sender.sendMimeMessage(null, mbox, msg);
        } catch (MailServiceException e) {
            // Don't check error message.  JavaMail does not give us the SMTP protocol error in the
            // partial send case.
            validateException(e, MailServiceException.SEND_PARTIAL_ADDRESS_FAILURE, bogusAddress, null);
            sendFailed = true;
        }
        assertTrue(sendFailed);
    }

    public void testRestrictEnvelopeSender()
    throws Exception {
        Server server = Provisioning.getInstance().getLocalServer();
        server.setSmtpPort(TEST_SMTP_PORT);

        Mailbox mbox = TestUtil.getMailbox(SENDER_NAME);
        Account account = mbox.getAccount();

        // Create a message with a different From header value.
        String from = TestUtil.getAddress("testRestrictEnvelopeSender");
        String subject = NAME_PREFIX + " testRestrictEnvelopeSender";
        MessageBuilder builder = new MessageBuilder().withFrom(from).withToRecipient(RECIPIENT_NAME)
            .withSubject(subject).withBody("Who are you?");
        String content = builder.create();
        MimeMessage msg = new FixedMimeMessage(JMSession.getSession(), new SharedByteArrayInputStream(content.getBytes()));

        account.setSmtpRestrictEnvelopeFrom(true);

        // Restrict envelope sender, disallow custom from.
        account.setAllowAnyFromAddress(false);
        DummySmtpServer smtp = startDummySmtpServer(null, null);
        mbox.getMailSender().sendMimeMessage(null, mbox, msg);
        assertEquals(account.getName(), smtp.getMailFrom());
        // Test contains to handle personal name
        assertTrue(getHeaderValue(smtp.getDataLines(), "From").contains(account.getName()));

        // Restrict envelope sender, allow custom from.
        msg = new FixedMimeMessage(JMSession.getSession(), new SharedByteArrayInputStream(content.getBytes()));
        account.setAllowAnyFromAddress(true);
        smtp = startDummySmtpServer(null, null);
        mbox.getMailSender().sendMimeMessage(null, mbox, msg);
        assertEquals(account.getName(), smtp.getMailFrom());
        assertEquals(from, getHeaderValue(smtp.getDataLines(), "From"));

        account.setSmtpRestrictEnvelopeFrom(false);

        // Don't restrict envelope sender, disallow custom from.
        account.setAllowAnyFromAddress(false);
        smtp = startDummySmtpServer(null, null);
        mbox.getMailSender().sendMimeMessage(null, mbox, msg);
        assertEquals(account.getName(), smtp.getMailFrom());
        assertTrue(getHeaderValue(smtp.getDataLines(), "From").contains(account.getName()));

        // Don't restrict envelope sender, allow custom from.
        msg = new FixedMimeMessage(JMSession.getSession(), new SharedByteArrayInputStream(content.getBytes()));
        account.setAllowAnyFromAddress(true);
        smtp = startDummySmtpServer(null, null);
        mbox.getMailSender().sendMimeMessage(null, mbox, msg);
        assertEquals(from, smtp.getMailFrom());
        assertEquals(from, getHeaderValue(smtp.getDataLines(), "From"));
    }

    private String getHeaderValue(List<String> dataLines, String headerName) {
        if (dataLines == null) {
            return null;
        }

        Pattern pat = Pattern.compile(headerName + ":\\s+(.*)");
        for (String line : dataLines) {
            Matcher m = pat.matcher(line);
            if (m.matches()) {
                return m.group(1);
            }
        }
        return null;
    }

    private DummySmtpServer startDummySmtpServer(String rejectedRecipient, String errorMsg) {
        DummySmtpServer smtp = new DummySmtpServer(TEST_SMTP_PORT);
        smtp.setRejectedRecipient(rejectedRecipient, errorMsg);
        Thread smtpServerThread = new Thread(smtp);
        smtpServerThread.start();
        return smtp;
    }

    private void validateException(MailServiceException e, String expectedCode, String invalidRecipient, String errorSubstring) {
        assertEquals(expectedCode, e.getCode());
        if (errorSubstring != null) {
            assertTrue("Error did not contain '" + errorSubstring + "': " + e.getMessage(), e.getMessage().contains(errorSubstring));
        }

        boolean foundRecipient = false;
        for (Argument arg : e.getArgs()) {
            if (arg.name.equals("invalid")) {
                assertEquals(invalidRecipient, arg.value);
                foundRecipient = true;
            }
        }
        assertTrue(foundRecipient);
    }

    @Override
    public void tearDown()
    throws Exception {
        cleanUp();
        TestUtil.setServerAttr(Provisioning.A_zmailSmtpPort, mOriginalSmtpPort);
        TestUtil.setServerAttr(Provisioning.A_zmailSmtpSendPartial, mOriginalSmtpSendPartial);
        TestUtil.setAccountAttr(SENDER_NAME, Provisioning.A_zmailAllowAnyFromAddress, mOriginalAllowAnyFrom);
    }
    private void cleanUp()
    throws Exception {
        TestUtil.deleteTestData(SENDER_NAME, NAME_PREFIX);
        TestUtil.deleteTestData(RECIPIENT_NAME, NAME_PREFIX);
    }

    public static void main(String[] args)
    throws Exception {
        // Simply starts the test SMTP server for ad-hoc testing.  Doesn't
        // run unit tests, since they need to run on the server side.
        DummySmtpServer smtp = new DummySmtpServer(TEST_SMTP_PORT);
        if (args.length >= 2) {
            smtp.setRejectedRecipient(args[0], args[1]);
        }
        Thread thread = new Thread(smtp, DummySmtpServer.class.getSimpleName());
        thread.setDaemon(true);
        thread.start();
    }
}
