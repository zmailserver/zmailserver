/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.SharedByteArrayInputStream;

import junit.framework.TestCase;

import org.zmail.client.ZEmailAddress;
import org.zmail.client.ZMailbox;
import org.zmail.client.ZMailbox.ZOutgoingMessage;
import org.zmail.client.ZMailbox.ZOutgoingMessage.MessagePart;
import org.zmail.client.ZMessage;
import org.zmail.client.ZMessage.ZMimePart;
import org.zmail.common.mime.MimeConstants;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.StringUtil;
import org.zmail.common.zmime.ZMimeMessage;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.db.DbOutOfOffice;
import org.zmail.cs.db.DbPool;
import org.zmail.cs.db.DbPool.DbConnection;
import org.zmail.cs.ldap.LdapConstants;
import org.zmail.cs.ldap.LdapUtil;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.util.JMSession;


public class TestNotification
extends TestCase {

    private static String RECIPIENT_NAME = "user1";
    private static String TAPPED_NAME = "user1";
    private static String INTERCEPTOR_NAME = "user2";
    private static String INTERCEPTOR2_NAME = "user3";

    private static String[] ALL_TEST_USERS = { "user1", "user2", "user3" };

    private static String NAME_PREFIX = TestNotification.class.getSimpleName();

    private boolean mOriginalReplyEnabled;
    private String mOriginalReply;
    private boolean mOriginalNotificationEnabled;
    private String mOriginalNotificationAddress;
    private String mOriginalNotificationSubject;
    private String mOriginalNotificationBody;
    private String[] mOriginalInterceptAddresses;
    private String mOriginalInterceptSendHeadersOnly;
    private String mOriginalSaveToSent;
    private final boolean mIsServerTest = false;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        cleanUp();

        Account account = TestUtil.getAccount(RECIPIENT_NAME);
        mOriginalReplyEnabled = account.getBooleanAttr(Provisioning.A_zmailPrefOutOfOfficeReplyEnabled, false);
        mOriginalReply = account.getAttr(Provisioning.A_zmailPrefOutOfOfficeReply, "");
        mOriginalNotificationEnabled = account.getBooleanAttr(Provisioning.A_zmailPrefNewMailNotificationEnabled, false);
        mOriginalNotificationAddress = account.getAttr(Provisioning.A_zmailPrefNewMailNotificationAddress, "");
        mOriginalNotificationSubject = account.getAttr(Provisioning.A_zmailNewMailNotificationSubject, "");
        mOriginalNotificationBody = account.getAttr(Provisioning.A_zmailNewMailNotificationBody, "");
        mOriginalInterceptAddresses = account.getMultiAttr(Provisioning.A_zmailInterceptAddress);
        mOriginalInterceptSendHeadersOnly = account.getAttr(Provisioning.A_zmailInterceptSendHeadersOnly, "");
        mOriginalSaveToSent = account.getAttr(Provisioning.A_zmailPrefSaveToSent, "");
    }

    public void testIntercept()
    throws Exception {
        // Turn on legal intercept for recipient account
        String interceptorAddress = TestUtil.getAddress(INTERCEPTOR_NAME);
        TestUtil.setAccountAttr(TAPPED_NAME, Provisioning.A_zmailInterceptAddress, interceptorAddress);
        TestUtil.setAccountAttr(TAPPED_NAME, Provisioning.A_zmailInterceptSendHeadersOnly, LdapConstants.LDAP_FALSE);

        // Send message to recipient account and make sure it's intercepted
        ZMailbox interceptorMbox = TestUtil.getZMailbox(INTERCEPTOR_NAME);
        ZMailbox tappedMbox = TestUtil.getZMailbox(TAPPED_NAME);
        String tappedAddress = TestUtil.getAddress(TAPPED_NAME);
        String subject = NAME_PREFIX + " testIntercept-receive";
        TestUtil.addMessageLmtp(subject, tappedAddress, interceptorAddress);

        ZMessage tappedMsg = TestUtil.getMessage(tappedMbox, "subject:\"" + subject + "\"");
        ZMessage interceptMsg = TestUtil.waitForMessage(interceptorMbox, "subject:\"" + subject + "\"");
        verifyInterceptMessage(interceptMsg, "add message", "Inbox", Integer.toString(Mailbox.ID_FOLDER_INBOX));
        compareContent(tappedMbox, tappedMsg, interceptorMbox, interceptMsg);

        // Confirm that saving a draft is intercepted.  The first draft calls Mailbox.addMessage().
        ZOutgoingMessage outgoing = new ZOutgoingMessage();
        List<ZEmailAddress> addresses = new ArrayList<ZEmailAddress>();
        addresses.add(new ZEmailAddress(TestUtil.getAddress(INTERCEPTOR_NAME),
            null, null, ZEmailAddress.EMAIL_TYPE_TO));
        outgoing.setAddresses(addresses);
        subject = NAME_PREFIX + " testIntercept-draft-1";
        outgoing.setSubject(subject);
        outgoing.setMessagePart(new MessagePart("text/plain", "I always feel like somebody's watching me."));
        tappedMbox.saveDraft(outgoing, null, Integer.toString(Mailbox.ID_FOLDER_DRAFTS));
        tappedMsg = TestUtil.getMessage(tappedMbox, "in:drafts subject:\"" + subject + "\"");
        interceptMsg = TestUtil.waitForMessage(interceptorMbox, "subject:\"" + subject + "\"");
        verifyInterceptMessage(interceptMsg, "add message", "Drafts", Integer.toString(Mailbox.ID_FOLDER_DRAFTS));
        compareContent(tappedMbox, tappedMsg, interceptorMbox, interceptMsg);

        // Save draft again.  This time Mailbox.saveDraft() gets called.
        ZMessage draft = TestUtil.getMessage(tappedMbox, "in:drafts subject:\"" + subject + "\"");
        subject = NAME_PREFIX + " testIntercept-draft-2";
        outgoing.setSubject(subject);
        tappedMbox.saveDraft(outgoing, draft.getId(), null);
        tappedMsg = TestUtil.getMessage(tappedMbox, "in:drafts subject:\"" + subject + "\"");
        interceptMsg = TestUtil.waitForMessage(interceptorMbox, "subject:\"" + subject + "\"");
        verifyInterceptMessage(interceptMsg, "save draft", "Drafts", Integer.toString(Mailbox.ID_FOLDER_DRAFTS));
        compareContent(tappedMbox, tappedMsg, interceptorMbox, interceptMsg);

        // Send message with save-to-sent turned on.
        TestUtil.setAccountAttr(TAPPED_NAME, Provisioning.A_zmailPrefSaveToSent, LdapConstants.LDAP_TRUE);
        subject = NAME_PREFIX + " testIntercept-send-1";
        TestUtil.sendMessage(tappedMbox, INTERCEPTOR_NAME, subject);
        tappedMsg = TestUtil.waitForMessage(tappedMbox, "in:sent subject:\"" + subject + "\"");
        interceptMsg = TestUtil.waitForMessage(interceptorMbox, "subject:intercepted subject:\"" + subject + "\"");
        verifyInterceptMessage(interceptMsg, "add message", "Sent", Integer.toString(Mailbox.ID_FOLDER_SENT));
        compareContent(tappedMbox, tappedMsg, interceptorMbox, interceptMsg);

        // Send message with save-to-sent turned off.
        TestUtil.setAccountAttr(TAPPED_NAME, Provisioning.A_zmailPrefSaveToSent, LdapConstants.LDAP_FALSE);
        subject = NAME_PREFIX + " testIntercept-send-2";
        TestUtil.sendMessage(tappedMbox, INTERCEPTOR_NAME, subject);
        interceptMsg = TestUtil.waitForMessage(interceptorMbox, "subject:intercepted subject:\"" + subject + "\"");
        verifyInterceptMessage(interceptMsg, "send message", "none", "none");

        // Check intercepting headers only.
        TestUtil.setAccountAttr(TAPPED_NAME, Provisioning.A_zmailInterceptSendHeadersOnly, LdapConstants.LDAP_TRUE);
        subject = NAME_PREFIX + " testIntercept-headers-only";
        TestUtil.sendMessage(interceptorMbox, TAPPED_NAME, subject);
        tappedMsg = TestUtil.waitForMessage(tappedMbox, "in:inbox subject:\"" + subject + "\"");
        interceptMsg = TestUtil.waitForMessage(interceptorMbox, "subject:intercepted subject:\"" + subject + "\"");
        verifyInterceptMessage(interceptMsg, "add message", "Inbox", Integer.toString(Mailbox.ID_FOLDER_INBOX));
        compareContent(tappedMbox, tappedMsg, interceptorMbox, interceptMsg);
    }

    /**
     * Confirms that legal intercept works with multiple interceptor addresses (bug 30961).
     */
    public void testInterceptMultiValue()
    throws Exception {
        // Turn on legal intercept for recipient account.
        String interceptor1Address = TestUtil.getAddress(INTERCEPTOR_NAME);
        String interceptor2Address = TestUtil.getAddress(INTERCEPTOR2_NAME);
        String[] interceptorAddresses = new String[] { interceptor1Address, interceptor2Address };

        TestUtil.setAccountAttr(TAPPED_NAME, Provisioning.A_zmailInterceptAddress, interceptorAddresses);
        TestUtil.setAccountAttr(TAPPED_NAME, Provisioning.A_zmailInterceptSendHeadersOnly, LdapConstants.LDAP_FALSE);

        // Send message to recipient account.
        ZMailbox tappedMbox = TestUtil.getZMailbox(TAPPED_NAME);
        String tappedAddress = TestUtil.getAddress(TAPPED_NAME);
        String subject = NAME_PREFIX + " testIntercept-receive";
        TestUtil.addMessageLmtp(subject, tappedAddress, interceptor1Address);

        // Make sure both interceptor accounts intercepted it.
        ZMailbox interceptor1Mbox = TestUtil.getZMailbox(INTERCEPTOR_NAME);
        ZMailbox interceptor2Mbox = TestUtil.getZMailbox(INTERCEPTOR2_NAME);
        ZMessage tappedMsg = TestUtil.getMessage(tappedMbox, "subject:\"" + subject + "\"");
        ZMessage interceptMsg1 = TestUtil.waitForMessage(interceptor1Mbox, "subject:\"" + subject + "\"");
        ZMessage interceptMsg2 = TestUtil.waitForMessage(interceptor2Mbox, "subject:\"" + subject + "\"");
        verifyInterceptMessage(interceptMsg1, "add message", "Inbox", Integer.toString(Mailbox.ID_FOLDER_INBOX));
        verifyInterceptMessage(interceptMsg2, "add message", "Inbox", Integer.toString(Mailbox.ID_FOLDER_INBOX));
        compareContent(tappedMbox, tappedMsg, interceptor1Mbox, interceptMsg1);
        compareContent(tappedMbox, tappedMsg, interceptor2Mbox, interceptMsg2);
    }

    /**
     * Verifies the structure of the intercept message.
     */
    private void verifyInterceptMessage(ZMessage interceptMsg, String operation, String folderName, String folderId)
    throws Exception {
        // Check structure
        ZMimePart part = interceptMsg.getMimeStructure();
        assertEquals(MimeConstants.CT_MULTIPART_MIXED, part.getContentType());
        List<ZMimePart> children = part.getChildren();
        assertEquals(2, children.size());

        // Check body
        ZMimePart bodyPart = children.get(0);
        assertEquals(MimeConstants.CT_TEXT_PLAIN, bodyPart.getContentType());
        String body = bodyPart.getContent();
        String context = "Unexpected body: \n" + body;
        assertTrue(context, body.contains("Intercepted message for " + RECIPIENT_NAME));
        assertTrue(context, body.contains("Operation=" + operation));
        assertTrue(context, body.contains("folder=" + folderName));
        assertTrue(context, body.contains("folder ID=" + folderId));

        // Compare to original message
        ZMimePart interceptedPart = children.get(1);
        assertEquals(MimeConstants.CT_MESSAGE_RFC822, interceptedPart.getContentType());
    }

    /**
     * Confirm that the message attached to the intercept message matches the original.
     */
    private void compareContent(ZMailbox tappedMbox, ZMessage tappedMsg, ZMailbox interceptorMbox, ZMessage interceptMsg)
    throws Exception {
        String relativeUrl = String.format("?id=%s&part=2", interceptMsg.getId());
        InputStream in = interceptorMbox.getRESTResource(relativeUrl);
        String interceptedMsgContent = new String(ByteUtil.getContent(in, -1)).trim();
        String tappedMsgContent = TestUtil.getContent(tappedMbox, tappedMsg.getId()).trim();

        Account account = TestUtil.getAccount(TAPPED_NAME);

        // Compare headers
        MimeMessage tappedMimeMsg = new ZMimeMessage(JMSession.getSession(), new SharedByteArrayInputStream(tappedMsgContent.getBytes()));
        MimeMessage interceptedMimeMsg = new ZMimeMessage(JMSession.getSession(), new SharedByteArrayInputStream(interceptedMsgContent.getBytes()));

        boolean headersOnly = account.getBooleanAttr(Provisioning.A_zmailInterceptSendHeadersOnly, false);
        Set<String> tappedHeaderLines = getHeaderLines(tappedMimeMsg);
        Set<String> interceptedHeaderLines = getHeaderLines(interceptedMimeMsg);
        tappedHeaderLines.removeAll(getHeaderLines(interceptedMimeMsg));
        interceptedHeaderLines.removeAll(getHeaderLines(tappedMimeMsg));
        String context = "Unexpected headers found.  tapped: " +
            StringUtil.join(",", tappedHeaderLines) + ".  intercepted: " +
            StringUtil.join(",", interceptedHeaderLines) + ".";
        assertTrue(context, tappedHeaderLines.size() == 0 && interceptedHeaderLines.size() == 0);

        // Compare body
        if (headersOnly) {
            String interceptedBody = new String(ByteUtil.getContent(interceptedMimeMsg.getInputStream(), 0));
            if (interceptedBody != null) {
                interceptedBody = interceptedBody.trim();
            }
            assertTrue("Unexpected body: '" + interceptedBody + "'", interceptedBody == null || interceptedBody.length() == 0);
        } else {
            TestUtil.assertMessageContains(tappedMsgContent, interceptedMsgContent);
        }
    }

    private Set<String> getHeaderLines(MimeMessage msg)
    throws MessagingException {
        Set<String> headerLines = new HashSet<String>();
        Enumeration<?> e = msg.getAllHeaderLines();
        while (e.hasMoreElements()) {
            headerLines.add((String) e.nextElement());
        }
        return headerLines;
    }

    @Override
    public void tearDown()
    throws Exception {
        cleanUp();

        // Revert to original values for out-of-office and notification
        Account account = TestUtil.getAccount(RECIPIENT_NAME);

        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailPrefOutOfOfficeReplyEnabled,
            LdapUtil.getLdapBooleanString(mOriginalReplyEnabled));
        attrs.put(Provisioning.A_zmailPrefOutOfOfficeReply, mOriginalReply);
        attrs.put(Provisioning.A_zmailPrefNewMailNotificationEnabled,
            LdapUtil.getLdapBooleanString(mOriginalNotificationEnabled));
        attrs.put(Provisioning.A_zmailPrefNewMailNotificationAddress, mOriginalNotificationAddress);
        attrs.put(Provisioning.A_zmailNewMailNotificationSubject, mOriginalNotificationSubject);
        attrs.put(Provisioning.A_zmailNewMailNotificationBody, mOriginalNotificationBody);
        if (mOriginalInterceptAddresses != null && mOriginalInterceptAddresses.length == 0) {
            attrs.put(Provisioning.A_zmailInterceptAddress, "");
        } else {
            attrs.put(Provisioning.A_zmailInterceptAddress, mOriginalInterceptAddresses);
        }
        attrs.put(Provisioning.A_zmailInterceptSendHeadersOnly, mOriginalInterceptSendHeadersOnly);
        attrs.put(Provisioning.A_zmailPrefSaveToSent, mOriginalSaveToSent);
        Provisioning.getInstance().modifyAttrs(account, attrs);

        super.tearDown();
    }

    /**
     * Deletes rows from the <tt>out_of_office</tt> table and cleans up data
     * created by the test.
     */
    private void cleanUp()
    throws Exception {
        if (mIsServerTest) {
            DbConnection conn = DbPool.getConnection();
            Mailbox mbox = TestUtil.getMailbox(RECIPIENT_NAME);
            DbOutOfOffice.clear(conn, mbox);
            conn.commit();
            DbPool.quietClose(conn);
        }

        // Clean up temporary data
        for (String userName : ALL_TEST_USERS) {
            TestUtil.deleteTestData(userName, NAME_PREFIX);
        }
    }

    public static void main(String[] args)
    throws Exception {
        TestUtil.cliSetup();
        TestUtil.runTest(TestNotification.class);
    }
}
