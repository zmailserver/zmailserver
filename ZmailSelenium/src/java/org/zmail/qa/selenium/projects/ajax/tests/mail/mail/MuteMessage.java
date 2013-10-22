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

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;


public class MuteMessage extends PrefGroupMailByMessageTest {

	
	public MuteMessage() {
		logger.info("New "+ MuteMessage.class.getCanonicalName());
		
		
		


		
	}
	
	@Bugs(ids = "38449")
	@Test(	description = "Mute a message (conversation) using Actions -> Mute",
			groups = { "smoke" })
	public void MuteMessage_01() throws HarnessException {
		
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
	

		// Send a message to the account
		ZmailAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
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
		
		// Click "mute"
		app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_MUTE);
		

		// Verify the redirected message is received
		throw new HarnessException("Need to determine how to verify the conversation is muted (from the server) - see bug 38449 and bug 63312");
		
	}

	@Bugs( ids = "65844")
	@Test(	description = "Mute message, using 'Mute' shortcut key",
			groups = { "functional" })
	public void MuteMessage_02() throws HarnessException {
		throw new HarnessException("See bug https://bugzilla.zmail.com/show_bug.cgi?id=65844");
	}
	
	@Test(	description = "Mute message, using 'Right Click' -> 'Mute'",
			groups = { "smoke" })
	public void MuteMessage_03() throws HarnessException {
		
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
	

		// Send a message to the account
		ZmailAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
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
		
		// Click Mute
		app.zPageMail.zListItem(Action.A_RIGHTCLICK, Button.B_MUTE, mail.dSubject);
		

		// Verify the redirected message is received
		throw new HarnessException("Need to determine how to verify the conversation is muted (from the server) - see bug 38449 and bug 63312");


	}




}
