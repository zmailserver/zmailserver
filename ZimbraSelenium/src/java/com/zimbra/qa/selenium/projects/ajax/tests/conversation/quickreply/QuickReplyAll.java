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
package com.zimbra.qa.selenium.projects.ajax.tests.conversation.quickreply;

import java.util.List;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.MailItem;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.PrefGroupMailByConversationTest;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.*;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.DisplayMail.Field;


public class QuickReplyAll extends PrefGroupMailByConversationTest {

	public QuickReplyAll() {
		logger.info("New "+ QuickReplyAll.class.getCanonicalName());

	}
	
	@Test(	description = "Quick Reply to a conversation (1 message, 1 recipient)",
			groups = { "smoke" })
	public void QuickReplyAll_01() throws HarnessException {
		
		ZimbraAccount account1 = new ZimbraAccount();
		account1.provision();
		account1.authenticate();
		
		
		// Create the message data to be sent
		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		String content = "content" + ZimbraSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZimbraSeleniumProperties.getUniqueString();
		
		account1.soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
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
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_REPLY_ALL);
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
	public void QuickReplyAll_02() throws HarnessException {
		
		ZimbraAccount account1 = new ZimbraAccount();
		account1.provision();
		account1.authenticate();
		
		ZimbraAccount account2 = new ZimbraAccount();
		account2.provision();
		account2.authenticate();


		
		// Create the message data to be sent
		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		String content = "content" + ZimbraSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZimbraSeleniumProperties.getUniqueString();
		
		account1.soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
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
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_REPLY_ALL);
		messages.get(0).zFillField(Field.Body, reply);
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		// Verify message in Sent
		MailItem sent = MailItem.importFromSOAP(app.zGetActiveAccount(), "in:sent subject:("+ subject +")");
		ZAssert.assertNotNull(sent, "Verify the message is in the sent folder");

		// Verify message is Received by sender
		MailItem sender = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(sender, "Verify the message is received by the original sender");

		// Verify message is Received by To
		MailItem to = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(to, "Verify the message is received by the original To");

		// Verify message is Received by Cc
		MailItem active = MailItem.importFromSOAP(app.zGetActiveAccount(), "in:inbox subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(active, "Verify the message is NOT received by the active account");

	}

	@Test(	description = "Quick Reply to a conversation (1 message, 1 recipient, 1 CC, 1 BCC)",
			groups = { "functional" })
	public void QuickReplyAll_03() throws HarnessException {
		
		ZimbraAccount account1 = new ZimbraAccount();
		account1.provision();
		account1.authenticate();
		
		ZimbraAccount account2 = new ZimbraAccount();
		account2.provision();
		account2.authenticate();

		ZimbraAccount account3 = new ZimbraAccount();
		account3.provision();
		account3.authenticate();

		ZimbraAccount account4 = new ZimbraAccount();
		account4.provision();
		account4.authenticate();

		
		// Create the message data to be sent
		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		String content = "content" + ZimbraSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZimbraSeleniumProperties.getUniqueString();
		
		account1.soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
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
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_REPLY_ALL);
		messages.get(0).zFillField(Field.Body, reply);
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		// Verify message in Sent
		MailItem sent = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +") in:sent");
		ZAssert.assertNotNull(sent, "Verify the message is in the sent folder");

		// Verify message is Received by sender
		MailItem received = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(received, "Verify the message is received by the original sender");

		// Verify message is Received by To
		MailItem to = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(to, "Verify the message is received by the original To");

		// Verify message is Received by Cc
		MailItem cc = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(cc, "Verify the message is received by the original Cc");

		// Verify message is Received by Cc
		MailItem bcc = MailItem.importFromSOAP(account4, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(bcc, "Verify the message is NOT received by the original Bcc");

	}

	@Test(	description = "Quick Reply-All to a 3 message conversation - first message",
			groups = { "functional" })
	public void QuickReplyAll_10() throws HarnessException {
		
		ZimbraAccount destination1 = new ZimbraAccount();
		destination1.provision();
		destination1.authenticate();
		
		ZimbraAccount destination2 = new ZimbraAccount();
		destination2.provision();
		destination2.authenticate();
		
		ZimbraAccount account1 = new ZimbraAccount();
		account1.provision();
		account1.authenticate();
		
		ZimbraAccount account2 = new ZimbraAccount();
		account2.provision();
		account2.authenticate();
		
		ZimbraAccount account3 = new ZimbraAccount();
		account3.provision();
		account3.authenticate();
		

		// Create the message data to be sent
		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		String content1 = "onecontent" + ZimbraSeleniumProperties.getUniqueString();
		String content2 = "twocontent" + ZimbraSeleniumProperties.getUniqueString();
		String content3 = "threecontent" + ZimbraSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZimbraSeleniumProperties.getUniqueString();
		
		account1.soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content1 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account2.soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content2 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account3.soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<e t='t' a='"+ destination1.EmailAddress +"'/>" +
						"<e t='c' a='"+ destination2.EmailAddress +"'/>" +
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
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_REPLY_ALL);
		messages.get(0).zFillField(Field.Body, reply);
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		MailItem mailitem;
		
		// Verify message is received by account3, destination1, destination2
		mailitem = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by account3");

		mailitem = MailItem.importFromSOAP(destination1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by destination1");

		mailitem = MailItem.importFromSOAP(destination2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by destination2");

		// Verify message is not received by account2 and account1
		mailitem = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is not received by account2 and account1");

		mailitem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is not received by account2 and account1");

	}

	@Test(	description = "Quick Reply-All to a 3 message conversation - middle message",
			groups = { "functional" })
	public void QuickReplyAll_11() throws HarnessException {
		
		ZimbraAccount destination1 = new ZimbraAccount();
		destination1.provision();
		destination1.authenticate();
		
		ZimbraAccount destination2 = new ZimbraAccount();
		destination2.provision();
		destination2.authenticate();
		
		ZimbraAccount account1 = new ZimbraAccount();
		account1.provision();
		account1.authenticate();
		
		ZimbraAccount account2 = new ZimbraAccount();
		account2.provision();
		account2.authenticate();
		
		ZimbraAccount account3 = new ZimbraAccount();
		account3.provision();
		account3.authenticate();
		

		// Create the message data to be sent
		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		String content1 = "onecontent" + ZimbraSeleniumProperties.getUniqueString();
		String content2 = "twocontent" + ZimbraSeleniumProperties.getUniqueString();
		String content3 = "threecontent" + ZimbraSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZimbraSeleniumProperties.getUniqueString();
		
		account1.soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content1 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account2.soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<e t='t' a='"+ destination1.EmailAddress +"'/>" +
						"<e t='c' a='"+ destination2.EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content2 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account3.soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
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
		messages.get(1).zPressButton(Button.B_QUICK_REPLY_REPLY_ALL);
		messages.get(1).zFillField(Field.Body, reply);
		messages.get(1).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		MailItem mailitem;
		
		// Verify message is received by account2, destination1, destination2
		mailitem = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by account2");

		mailitem = MailItem.importFromSOAP(destination1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by destination1");

		mailitem = MailItem.importFromSOAP(destination2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by destination2");

		// Verify message is not received by account3 and account1
		mailitem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is NOT received by account3 and account1");

		mailitem = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is NOT received by account3 and account1");

	}

	@Test(	description = "Quick Reply-All to a 3 message conversation - last message",
			groups = { "functional" })
	public void QuickReplyAll_12() throws HarnessException {
		
		ZimbraAccount destination1 = new ZimbraAccount();
		destination1.provision();
		destination1.authenticate();
		
		ZimbraAccount destination2 = new ZimbraAccount();
		destination2.provision();
		destination2.authenticate();
		
		ZimbraAccount account1 = new ZimbraAccount();
		account1.provision();
		account1.authenticate();
		
		ZimbraAccount account2 = new ZimbraAccount();
		account2.provision();
		account2.authenticate();
		
		ZimbraAccount account3 = new ZimbraAccount();
		account3.provision();
		account3.authenticate();
		

		// Create the message data to be sent
		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		String content1 = "onecontent" + ZimbraSeleniumProperties.getUniqueString();
		String content2 = "twocontent" + ZimbraSeleniumProperties.getUniqueString();
		String content3 = "threecontent" + ZimbraSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZimbraSeleniumProperties.getUniqueString();
		
		account1.soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<e t='t' a='"+ destination1.EmailAddress +"'/>" +
						"<e t='t' a='"+ destination2.EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content1 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account2.soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>RE: "+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content2 +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		account3.soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
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
		messages.get(2).zPressButton(Button.B_QUICK_REPLY_REPLY_ALL);
		messages.get(2).zFillField(Field.Body, reply);
		messages.get(2).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		MailItem mailitem;
		
		// Verify message is received by account1, destination1, destination1
		mailitem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by account1");

		mailitem = MailItem.importFromSOAP(destination1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by destination1");

		mailitem = MailItem.importFromSOAP(destination2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by destination1");

		// Verify message is not received by account2 and account3
		mailitem = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is NOT received by account2 and account3");

		mailitem = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is NOT received by account2 and account3");

	}

}
