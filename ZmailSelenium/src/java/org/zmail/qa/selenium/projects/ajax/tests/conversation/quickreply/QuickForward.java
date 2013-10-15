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
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByConversationTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.*;


public class QuickForward extends PrefGroupMailByConversationTest {

	public QuickForward() {
		logger.info("New "+ QuickForward.class.getCanonicalName());

	}
	
	@Test(	description = "Quick Reply (Forward) a conversation (1 message, 1 recipient)",
			groups = { "smoke" })
	public void QuickForward_01() throws HarnessException {
		
		ZmailAccount destination = new ZmailAccount();
		destination.provision();
		destination.authenticate();
		
		ZmailAccount account1 = new ZmailAccount();
		account1.provision();
		account1.authenticate();
		

		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String content = "content" + ZmailSeleniumProperties.getUniqueString();
		String forward = "quickforward" + ZmailSeleniumProperties.getUniqueString();
		
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
				
		// Quick Forward
		FormMailNew form = (FormMailNew)messages.get(0).zPressButton(Button.B_QUICK_REPLY_FORWARD);
		form.zFillField(FormMailNew.Field.To, destination.EmailAddress);
		form.zFillField(FormMailNew.Field.Body, forward);
		form.zToolbarPressButton(Button.B_SEND);
	

		
		MailItem mailItem;

		// Verify message is received by the destination
		mailItem = MailItem.importFromSOAP(destination, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailItem, "Verify the message is in the sent folder");

		// Verify message is not received by the sender
		mailItem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailItem, "Verify the message is received by the original sender");

	}

	@Test(	description = "Quick Reply (forward) a conversation (1 message, 2 recipients)",
			groups = { "functional" })
	public void QuickForward_02() throws HarnessException {
		
		ZmailAccount destination1 = new ZmailAccount();
		destination1.provision();
		destination1.authenticate();
		
		ZmailAccount destination2 = new ZmailAccount();
		destination2.provision();
		destination2.authenticate();
		
		ZmailAccount account1 = new ZmailAccount();
		account1.provision();
		account1.authenticate();
		
		ZmailAccount account2 = new ZmailAccount();
		account2.provision();
		account2.authenticate();


		
		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String content = "content" + ZmailSeleniumProperties.getUniqueString();
		String forward = "quickforward" + ZmailSeleniumProperties.getUniqueString();
		
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
				
		// Quick Forward
		FormMailNew form = (FormMailNew)messages.get(0).zPressButton(Button.B_QUICK_REPLY_FORWARD);
		form.zFillField(FormMailNew.Field.To, destination1.EmailAddress + ";" + destination2.EmailAddress);
		form.zFillField(FormMailNew.Field.Body, forward);
		form.zToolbarPressButton(Button.B_SEND);
	

		MailItem mailItem;

		// Verify message is received by the destination
		mailItem = MailItem.importFromSOAP(destination1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailItem, "Verify the message is in the sent folder");

		mailItem = MailItem.importFromSOAP(destination2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailItem, "Verify the message is in the sent folder");

		// Verify message is not received by the sender
		mailItem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailItem, "Verify the message is received by the original sender");

		mailItem = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailItem, "Verify the message is received by the original sender");

	}

	@Test(	description = "Quick Reply (forward) a conversation (1 message, 1 recipient, 1 CC, 1 BCC)",
			groups = { "functional" })
	public void QuickForward_03() throws HarnessException {
		
		ZmailAccount destination = new ZmailAccount();
		destination.provision();
		destination.authenticate();
		
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
		String forward = "quickforward" + ZmailSeleniumProperties.getUniqueString();
		
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
				
		// Quick Forward
		FormMailNew form = (FormMailNew)messages.get(0).zPressButton(Button.B_QUICK_REPLY_FORWARD);
		form.zFillField(FormMailNew.Field.To, destination.EmailAddress);
		form.zFillField(FormMailNew.Field.Body, forward);
		form.zToolbarPressButton(Button.B_SEND);
	

		MailItem mailItem;

		// Verify message is received by the destination
		mailItem = MailItem.importFromSOAP(destination, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailItem, "Verify the message is in the sent folder");


		// Verify message is not received by the sender
		mailItem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailItem, "Verify the message is received by the original sender");

		mailItem = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailItem, "Verify the message is received by the original sender");

		mailItem = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailItem, "Verify the message is received by the original sender");

		mailItem = MailItem.importFromSOAP(account4, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailItem, "Verify the message is received by the original sender");


	}

	
	@Test(	description = "Quick Forward two a 3 message conversation - first message",
			groups = { "functional" })
	public void QuickForward_10() throws HarnessException {
		
		ZmailAccount destination = new ZmailAccount();
		destination.provision();
		destination.authenticate();
		

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
		String forward = "quickforward" + ZmailSeleniumProperties.getUniqueString();
		
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
				
		// Quick Forward
		FormMailNew form = (FormMailNew)messages.get(0).zPressButton(Button.B_QUICK_REPLY_FORWARD);
		form.zFillField(FormMailNew.Field.To, destination.EmailAddress);
		form.zFillField(FormMailNew.Field.Body, forward);
		form.zToolbarPressButton(Button.B_SEND);
	

		MailItem mailitem;
		
		// Verify message is received by destination
		mailitem = MailItem.importFromSOAP(destination, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by account3");

		// Verify message is not received by account1, account2, nor account3
		mailitem = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is received by account3");

		mailitem = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is not received by account2 and account1");

		mailitem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is not received by account2 and account1");


	}

	@Test(	description = "Quick Forward two a 3 message conversation - middle message",
			groups = { "functional" })
	public void QuickForward_11() throws HarnessException {
		
		ZmailAccount destination = new ZmailAccount();
		destination.provision();
		destination.authenticate();
		
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
		String forward = "quickforward" + ZmailSeleniumProperties.getUniqueString();
		
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
				
		// Quick Forward
		FormMailNew form = (FormMailNew)messages.get(0).zPressButton(Button.B_QUICK_REPLY_FORWARD);
		form.zFillField(FormMailNew.Field.To, destination.EmailAddress);
		form.zFillField(FormMailNew.Field.Body, forward);
		form.zToolbarPressButton(Button.B_SEND);
	

		MailItem mailitem;
		
		// Verify message is received by destination
		mailitem = MailItem.importFromSOAP(destination, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by account3");

		// Verify message is not received by account1, account2, nor account3
		mailitem = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is received by account3");

		mailitem = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is not received by account2 and account1");

		mailitem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is not received by account2 and account1");

	}

	@Test(	description = "Quick Forward two a 3 message conversation - last message",
			groups = { "functional" })
	public void QuickForward_12() throws HarnessException {
		
		ZmailAccount destination = new ZmailAccount();
		destination.provision();
		destination.authenticate();
		
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
		String forward = "quickforward" + ZmailSeleniumProperties.getUniqueString();
		
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
				
		// Quick Forward
		FormMailNew form = (FormMailNew)messages.get(0).zPressButton(Button.B_QUICK_REPLY_FORWARD);
		form.zFillField(FormMailNew.Field.To, destination.EmailAddress);
		form.zFillField(FormMailNew.Field.Body, forward);
		form.zToolbarPressButton(Button.B_SEND);
	

		MailItem mailitem;
		
		// Verify message is received by destination
		mailitem = MailItem.importFromSOAP(destination, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNotNull(mailitem, "Verify message is received by account3");

		// Verify message is not received by account1, account2, nor account3
		mailitem = MailItem.importFromSOAP(account3, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is received by account3");

		mailitem = MailItem.importFromSOAP(account2, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is not received by account2 and account1");

		mailitem = MailItem.importFromSOAP(account1, "subject:("+ subject +") from:("+ app.zGetActiveAccount().EmailAddress +")");
		ZAssert.assertNull(mailitem, "Verify message is not received by account2 and account1");


	}



}
