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
package com.zimbra.qa.unittest;

import java.util.List;

import junit.framework.TestCase;

import com.zimbra.common.mime.MimeConstants;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.client.ZMailbox;
import com.zimbra.client.ZMessage;
import com.zimbra.client.ZSearchParams;


public class TestIndex extends TestCase {

    private static final String NAME_PREFIX = TestIndex.class.getSimpleName();
    private static final String USER_NAME = "user1";

    private int mOriginalTextLimit;
    
    public void setUp()
    throws Exception {
        mOriginalTextLimit = Integer.parseInt(TestUtil.getServerAttr(Provisioning.A_zimbraAttachmentsIndexedTextLimit));
        cleanUp();
    }
    
    public void testIndexedTextLimit()
    throws Exception {
        // Test text attachment
        StringBuilder body = new StringBuilder();
        for (int i = 1; i < 100; i++) {
            body.append("Walrus walrus walrus walrus walrus walrus walrus.\n");
        }
        body.append("Goo goo goo joob.\n");

        // Test text truncated
        setTextLimit(50);
        String subject = NAME_PREFIX + " text attachment 1";
        String msgId = sendMessage(subject, body.toString().getBytes(), "attachment.txt", MimeConstants.CT_TEXT_PLAIN).getId();
        checkQuery("in:inbox subject:\"" + subject + "\" walrus", msgId);
        checkQuery("in:inbox subject:\"" + subject + "\" joob", null);
        
        // Test HTML truncated
        subject = NAME_PREFIX + " HTML attachment 1";
        String htmlBody = "<html>\n" + body + "</html>";
        msgId = sendMessage(subject, htmlBody.getBytes(), "attachment.html", MimeConstants.CT_TEXT_HTML).getId();
        checkQuery("in:inbox subject:\"" + subject + "\" walrus", msgId);
        checkQuery("in:inbox subject:\"" + subject + "\" joob", null);
        
        // Test text not truncated
        setTextLimit(100000);
        subject = NAME_PREFIX + " text attachment 2";
        msgId = sendMessage(subject, body.toString().getBytes(), "attachment.txt", MimeConstants.CT_TEXT_PLAIN).getId();
        checkQuery("in:inbox subject:\"" + subject + "\" walrus", msgId);
        checkQuery("in:inbox subject:\"" + subject + "\" joob", msgId);

        // Test HTML not truncated
        subject = NAME_PREFIX + " HTML attachment 2";
        msgId = sendMessage(subject, htmlBody.getBytes(), "attachment.html", MimeConstants.CT_TEXT_HTML).getId();
        checkQuery("in:inbox subject:\"" + subject + "\" walrus", msgId);
        checkQuery("in:inbox subject:\"" + subject + "\" joob", msgId);
        
        // Test attached message subject truncated
        subject = NAME_PREFIX + " subject";
        String attachedMsg = TestUtil.getTestMessage("Pigs from a gun", "recipient", "sender", null);
        setTextLimit(4);
        msgId = sendMessage(subject, attachedMsg.getBytes(), "attachment.msg", MimeConstants.CT_MESSAGE_RFC822).getId();
        checkQuery("in:inbox subject:\"" + subject + "\" pigs", msgId);
        checkQuery("in:inbox subject:\"" + subject + "\" gun", null);
    }
    
    /**
     * Verifies the fix to bug 54613.
     */
    public void testFilenameSearch()
    throws Exception {
        ZMailbox mbox = TestUtil.getZMailbox(USER_NAME);
        String filename = NAME_PREFIX + " testFilenameSearch.txt";
        TestUtil.createDocument(mbox, Integer.toString(Mailbox.ID_FOLDER_BRIEFCASE),
            filename, "text/plain", "This is the data for testFilenameSearch.".getBytes());
        assertEquals(0, TestUtil.search(mbox, "filename:Blob*", ZSearchParams.TYPE_DOCUMENT).size());
        assertEquals(1, TestUtil.search(mbox, "filename:\"" + filename + "\"", ZSearchParams.TYPE_DOCUMENT).size());
    }
    
    /**
     * Sends a message with the specified attachment, waits for the message to
     * arrives, and runs a query.
     * @param subject the subject of the message
     * @param attData attachment data 
     * @param attName attachment name
     * @param attContentType attachment content type
     * @param query query to run after message arrives
     * @return <tt>true</tt> if the query returns the message
     */
    private ZMessage sendMessage(String subject, byte[] attData, String attName, String attContentType)
    throws Exception {
        
        // Send message
        ZMailbox mbox = TestUtil.getZMailbox(USER_NAME);
        String attachmentId = mbox.uploadAttachment(attName, attData, attContentType, 5000);
        TestUtil.sendMessage(mbox, USER_NAME, subject, "Cranberry sauce", attachmentId);
        String query = "in:inbox subject:\"" + subject + "\"";
        return TestUtil.waitForMessage(mbox, query);
    }
    
    private void checkQuery(String query, String msgId)
    throws Exception {
        ZMailbox mbox = TestUtil.getZMailbox(USER_NAME);
        List<ZMessage> messages = TestUtil.search(mbox, query);
        if (msgId == null) {
            assertEquals(0, messages.size());
        } else {
            assertEquals(1, messages.size());
            assertEquals(msgId, messages.get(0).getId());
        }
    }
    
    private void setTextLimit(int numBytes)
    throws Exception {
        TestUtil.setServerAttr(Provisioning.A_zimbraAttachmentsIndexedTextLimit, Integer.toString(numBytes));
    }
    
    public void tearDown()
    throws Exception {
        setTextLimit(mOriginalTextLimit);
        cleanUp();
    }
    
    private void cleanUp()
    throws Exception {
        TestUtil.deleteTestData(USER_NAME, NAME_PREFIX);
    }

    public static void main(String[] args)
    throws Exception {
        TestUtil.cliSetup();
        TestUtil.runTest(TestIndex.class);
    }
}
