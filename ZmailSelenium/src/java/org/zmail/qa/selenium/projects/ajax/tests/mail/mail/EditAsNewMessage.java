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
package org.zmail.qa.selenium.projects.ajax.tests.mail.mail;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;


public class EditAsNewMessage extends PrefGroupMailByMessageTest {

	
	public EditAsNewMessage() {
		logger.info("New "+ EditAsNewMessage.class.getCanonicalName());
		
	}
	
	
	
	@Test(	description = "'Edit as new' message, using 'Actions -> Edit as New' toolbar button",
			groups = { "smoke" })
	public void EditAsNewMessage_01() throws HarnessException {
		
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
	

		// Send a message to the account
		ZmailAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>" +
						"<m>" +
							"<e t='t' a='"+ ZmailAccount.AccountB().EmailAddress +"'/>" +
							"<e t='c' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>content"+ ZmailSeleniumProperties.getUniqueString() +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");
		
		// Get the mail item for the new message
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");
		
		

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
				
		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);
		
		// Click redirect
		FormMailNew form = (FormMailNew)app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.O_EDIT_AS_NEW);
		form.zSubmit();
		

		// Verify the redirected message is received
		MailItem original = MailItem.importFromSOAP(ZmailAccount.AccountB(), "subject:("+subject+") from:("+ZmailAccount.AccountA().EmailAddress+")");
		ZAssert.assertNotNull(original, "Verify the original message from Account A is received by Account B");
		
		MailItem resent = MailItem.importFromSOAP(ZmailAccount.AccountB(), "subject:("+subject+") from:("+app.zGetActiveAccount().EmailAddress+")");
		ZAssert.assertNotNull(resent, "Verify the 'edit as new' message from the test account is received by Account B");



	}

	
	@Test(	description = "'Edit as new' message, using 'Right Click' -> 'Edit as new'",
			groups = { "functional" })
	public void EditAsNewMessage_02() throws HarnessException {
		
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
	

		// Send a message to the account
		ZmailAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>" +
						"<m>" +
							"<e t='t' a='"+ ZmailAccount.AccountB().EmailAddress +"'/>" +
							"<e t='c' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>content"+ ZmailSeleniumProperties.getUniqueString() +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");
		
		// Get the mail item for the new message
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");
		
		

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Click redirect
		FormMailNew form = (FormMailNew)app.zPageMail.zListItem(Action.A_RIGHTCLICK, Button.O_EDIT_AS_NEW, mail.dSubject);
		form.zSubmit();
		

		// Verify the redirected message is received
		MailItem original = MailItem.importFromSOAP(ZmailAccount.AccountB(), "subject:("+subject+") from:("+ZmailAccount.AccountA().EmailAddress+")");
		ZAssert.assertNotNull(original, "Verify the original message from Account A is received by Account B");
		
		MailItem resent = MailItem.importFromSOAP(ZmailAccount.AccountB(), "subject:("+subject+") from:("+app.zGetActiveAccount().EmailAddress+")");
		ZAssert.assertNotNull(resent, "Verify the 'edit as new' message from the test account is received by Account B");


	}




}
