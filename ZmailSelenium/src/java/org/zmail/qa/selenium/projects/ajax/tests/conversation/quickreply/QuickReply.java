/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.qa.selenium.projects.ajax.tests.conversation.quickreply;

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByConversationTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.*;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail.Field;


public class QuickReply extends PrefGroupMailByConversationTest {

	public QuickReply() {
		logger.info("New "+ QuickReply.class.getCanonicalName());

	}
	
	@Test(	description = "Quick Reply to a conversation (1 message, 1 recipient)",
			groups = { "smoke" })
	public void QuickReply_01() throws HarnessException {
		
		ZmailAccount account1 = new ZmailAccount();
		account1.provision();
		account1.authenticate();
		
		
		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String content = "content" + ZmailSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZmailSeleniumProperties.getUniqueString();
		
		account1.soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>"+ content +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Select the conversation
		DisplayConversation display = (DisplayConversation)app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		// Get the first mesage
		List<DisplayConversationMessage> messages = display.zListGetMessages();
				
		// Quick Reply
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_REPLY);
		messages.get(0).zFillField(Field.Body, reply);
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		// Verify message in Sent
		MailItem sent = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(sent, "Verify the message is in the sent folder");

		// Verify message is Received by sender
		MailItem received = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(received, "Verify the message is received by the original sender");

	}

	@Test(	description = "Quick Reply to a conversation (1 message, 2 recipients)",
			groups = { "functional" })
	public void QuickReply_02() throws HarnessException {
		
		ZmailAccount account1 = new ZmailAccount();
		account1.provision();
		account1.authenticate();
		
		ZmailAccount account2 = new ZmailAccount();
		account2.provision();
		account2.authenticate();


		
		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String content = "content" + ZmailSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZmailSeleniumProperties.getUniqueString();
		
		account1.soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>" +
						"<m>" +
							"<e t='t' a='"+ account2.EmailAddress +"'/>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>"+ content +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Select the conversation
		DisplayConversation display = (DisplayConversation)app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		// Get the first mesage
		List<DisplayConversationMessage> messages = display.zListGetMessages();
				
		// Quick Reply
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_REPLY);
		messages.get(0).zFillField(Field.Body, reply);
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		// Verify message in Sent
		MailItem sent = MailItem.importFromSOAP(app.zGetActiveAccount(), "in:sent subject:("+ subject +")");
		ZAssert.assertNotNull(sent, "Verify the message is in the sent folder");

		// Verify message is NOT Received by active account
		MailItem active = MailItem.importFromSOAP(app.zGetActiveAccount(), "in:inbox subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(active, "Verify the message is NOT received by the active account");

		// Verify message is Received by sender
		MailItem sender = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(sender, "Verify the message is received by the original sender");

		// Verify message is NOT Received by To
		MailItem to = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(to, "Verify the message is NOT received by the original To");

	}

	@Test(	description = "Quick Reply to a conversation (1 message, 1 recipient, 1 CC, 1 BCC)",
			groups = { "functional" })
	public void QuickReply_03() throws HarnessException {
		
		ZmailAccount account1 = new ZmailAccount();
		account1.provision();
		account1.authenticate();
		
		ZmailAccount account2 = new ZmailAccount();
		account2.provision();
		account2.authenticate();

		ZmailAccount account3 = new ZmailAccount();
		account3.provision();
		account3.authenticate();

		ZmailAccount account4 = new ZmailAccount();
		account4.provision();
		account4.authenticate();

		
		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String content = "content" + ZmailSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZmailSeleniumProperties.getUniqueString();
		
		account1.soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>" +
						"<m>" +
							"<e t='t' a='"+ account2.EmailAddress +"'/>" +
							"<e t='c' a='"+ account3.EmailAddress +"'/>" +
							"<e t='b' a='"+ account4.EmailAddress +"'/>" +
							"<e t='b' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>"+ content +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Select the conversation
		DisplayConversation display = (DisplayConversation)app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		// Get the first mesage
		List<DisplayConversationMessage> messages = display.zListGetMessages();
				
		// Quick Reply
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_REPLY);
		messages.get(0).zFillField(Field.Body, reply);
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		// Verify message in Sent
		MailItem sent = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +") in:sent");
		ZAssert.assertNotNull(sent, "Verify the message is in the sent folder");

		// Verify message is Received by sender
		MailItem received = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(received, "Verify the message is received by the original sender");

		// Verify message is NOT Received by To
		MailItem to = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(to, "Verify the message is NOT received by the original To");

		// Verify message is NOT Received by Cc
		MailItem cc = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(cc, "Verify the message is NOT received by the original Cc");

		// Verify message is NOT Received by Bcc
		MailItem bcc = MailItem.importFromSOAP(account4, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(bcc, "Verify the message is NOT received by the original Bcc");

	}

	
	@Test(	description = "Quick Reply two a 3 message conversation - first message",
			groups = { "functional" })
	public void QuickReply_10() throws HarnessException {
		
		ZmailAccount account1 = new ZmailAccount();
		account1.provision();
		account1.authenticate();
		
		ZmailAccount account2 = new ZmailAccount();
		account2.provision();
		account2.authenticate();
		
		ZmailAccount account3 = new ZmailAccount();
		account3.provision();
		account3.authenticate();
		

		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String content1 = "onecontent" + ZmailSeleniumProperties.getUniqueString();
		String content2 = "twocontent" + ZmailSeleniumProperties.getUniqueString();
		String content3 = "threecontent" + ZmailSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZmailSeleniumProperties.getUniqueString();
		
		account1.soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content1 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account2.soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content2 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account3.soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content3 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Select the conversation
		DisplayConversation display = (DisplayConversation)app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		// Get the first mesage
		List<DisplayConversationMessage> messages = display.zListGetMessages();
				
		// Quick Reply
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_REPLY);
		messages.get(0).zFillField(Field.Body, reply);
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		MailItem mailitem;
		
		// Verify message is received by account3
		mailitem = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by account3");

		// Verify message is not received by account2 and account1
		mailitem = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is not received by account2 and account1");

		mailitem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is not received by account2 and account1");

	}

	@Test(	description = "Quick Reply two a 3 message conversation - middle message",
			groups = { "functional" })
	public void QuickReply_11() throws HarnessException {
		
		ZmailAccount account1 = new ZmailAccount();
		account1.provision();
		account1.authenticate();
		
		ZmailAccount account2 = new ZmailAccount();
		account2.provision();
		account2.authenticate();
		
		ZmailAccount account3 = new ZmailAccount();
		account3.provision();
		account3.authenticate();
		

		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String content1 = "onecontent" + ZmailSeleniumProperties.getUniqueString();
		String content2 = "twocontent" + ZmailSeleniumProperties.getUniqueString();
		String content3 = "threecontent" + ZmailSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZmailSeleniumProperties.getUniqueString();
		
		account1.soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content1 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account2.soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content2 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account3.soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content3 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Select the conversation
		DisplayConversation display = (DisplayConversation)app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		// Get the first mesage
		List<DisplayConversationMessage> messages = display.zListGetMessages();
				
		// Quick Reply
		messages.get(1).zPressButton(Button.B_QUICK_REPLY_REPLY);
		messages.get(1).zFillField(Field.Body, reply);
		messages.get(1).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		MailItem mailitem;
		
		// Verify message is received by account2
		mailitem = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by account2");

		// Verify message is not received by account3 and account1
		mailitem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is NOT received by account3 and account1");

		mailitem = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is NOT received by account3 and account1");

	}

	@Test(	description = "Quick Reply two a 3 message conversation - last message",
			groups = { "functional" })
	public void QuickReply_12() throws HarnessException {
		
		ZmailAccount account1 = new ZmailAccount();
		account1.provision();
		account1.authenticate();
		
		ZmailAccount account2 = new ZmailAccount();
		account2.provision();
		account2.authenticate();
		
		ZmailAccount account3 = new ZmailAccount();
		account3.provision();
		account3.authenticate();
		

		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String content1 = "onecontent" + ZmailSeleniumProperties.getUniqueString();
		String content2 = "twocontent" + ZmailSeleniumProperties.getUniqueString();
		String content3 = "threecontent" + ZmailSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZmailSeleniumProperties.getUniqueString();
		
		account1.soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content1 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account2.soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content2 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account3.soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content3 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Select the conversation
		DisplayConversation display = (DisplayConversation)app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		// Get the first mesage
		List<DisplayConversationMessage> messages = display.zListGetMessages();
				
		// Quick Reply
		messages.get(2).zPressButton(Button.B_QUICK_REPLY_REPLY);
		messages.get(2).zFillField(Field.Body, reply);
		messages.get(2).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		MailItem mailitem;
		
		// Verify message is received by account1
		mailitem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by account1");

		// Verify message is not received by account2 and account3
		mailitem = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is NOT received by account2 and account3");

		mailitem = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is NOT received by account2 and account3");

	}


}
