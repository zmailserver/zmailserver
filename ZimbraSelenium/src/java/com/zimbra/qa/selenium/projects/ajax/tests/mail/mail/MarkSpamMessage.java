/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.qa.selenium.projects.ajax.tests.mail.mail;

import org.testng.annotations.*;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.items.FolderItem.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.*;


public class MarkSpamMessage extends PrefGroupMailByMessageTest {

	
	public MarkSpamMessage() {
		logger.info("New "+ MarkSpamMessage.class.getCanonicalName());
		
		
		super.startingAccountPreferences.put("zimbraPrefShowSelectionCheckbox", "TRUE");
		
	}
	
	@Test(	description = "Mark a message as spam, using 'Spam' toolbar button",
			groups = { "smoke" })
	public void MarkSpamMessage_01() throws HarnessException {
		
		String subject = "subject"+ ZimbraSeleniumProperties.getUniqueString();
		
		// Get the junk folder
		FolderItem junk = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Junk);


		// Send a message to the account
		ZimbraAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>content"+ ZimbraSeleniumProperties.getUniqueString() +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");
		
		// Get the mail item for the new message
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");
		

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
				
		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);
		
		// Click spam
		app.zPageMail.zToolbarPressButton(Button.B_RESPORTSPAM);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		
		// Get the mail item for the new message
		// Need 'is:anywhere' to include the spam folder
		mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "is:anywhere subject:("+ subject +")");
		ZAssert.assertNotNull(mail, "Make sure the mail is found");
		
		ZAssert.assertEquals(mail.dFolderId, junk.getId(), "Verify the message is in the spam folder");
				
	}


	@Test(	description = "Mark a message as spam, using keyboard shortcut (keyboard='ms')",
			groups = { "smoke" })
	public void MarkSpamMessage_02() throws HarnessException {
		
		String subject = "subject"+ ZimbraSeleniumProperties.getUniqueString();
		
		// Get the junk folder
		FolderItem junk = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Junk);


		// Send a message to the account
		ZimbraAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>content"+ ZimbraSeleniumProperties.getUniqueString() +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");
		
		// Get the mail item for the new message
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");
		

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
				
		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);
		
		// Spam the item
		app.zPageMail.zKeyboardShortcut(Shortcut.S_MAIL_MARKSPAM);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		
		// Get the mail item for the new message
		// Need 'is:anywhere' to include the spam folder
		mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "is:anywhere subject:("+ subject +")");
		ZAssert.assertNotNull(mail, "Make sure the mail is found");
		
		ZAssert.assertEquals(mail.dFolderId, junk.getId(), "Verify the message is in the spam folder");
				
	}

	@Test(	description = "Mark multiple messages (3) as spam by select and toolbar delete",
			groups = { "functional" })
	public void MarkSpamMessage_03() throws HarnessException {
		
		FolderItem junk = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Junk);

		// Create the message data to be sent
		String subject1 = "subject"+ ZimbraSeleniumProperties.getUniqueString();
		String subject2 = "subject"+ ZimbraSeleniumProperties.getUniqueString();
		String subject3 = "subject"+ ZimbraSeleniumProperties.getUniqueString();
				
		ZimbraAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>"+ subject1 +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>content"+ ZimbraSeleniumProperties.getUniqueString() +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		ZimbraAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>"+ subject2 +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>content"+ ZimbraSeleniumProperties.getUniqueString() +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		ZimbraAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>"+ subject3 +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>content"+ ZimbraSeleniumProperties.getUniqueString() +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
				
		// Select all three items
		app.zPageMail.zListItem(Action.A_MAIL_CHECKBOX, subject1);
		app.zPageMail.zListItem(Action.A_MAIL_CHECKBOX, subject2);
		app.zPageMail.zListItem(Action.A_MAIL_CHECKBOX, subject3);
		
		// Click toolbar delete button
		app.zPageMail.zToolbarPressButton(Button.B_RESPORTSPAM);
				

		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject1 +") is:anywhere");
		ZAssert.assertEquals(mail.dFolderId, junk.getId(), "Verify the message is in the spam folder");

		mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject2 +") is:anywhere");
		ZAssert.assertEquals(mail.dFolderId, junk.getId(), "Verify the message is in the spam folder");

		mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject3 +") is:anywhere");
		ZAssert.assertEquals(mail.dFolderId, junk.getId(), "Verify the message is in the spam folder");

	}


}
