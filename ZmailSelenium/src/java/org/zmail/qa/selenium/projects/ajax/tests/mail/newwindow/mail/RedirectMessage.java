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
package org.zmail.qa.selenium.projects.ajax.tests.mail.newwindow.mail;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.*;


public class RedirectMessage extends PrefGroupMailByMessageTest {

	
	public RedirectMessage() {
		logger.info("New "+ RedirectMessage.class.getCanonicalName());
		
		
		


		
	}
	
	@Test(	description = "Redirect message, using 'Redirect' toolbar button - in separate window",
			groups = { "smoke" })
	public void RedirectMessage_01() throws HarnessException {
		
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
		
		
		SeparateWindowDisplayMail window = null;
		
		try {
			
			// Choose Actions -> Launch in Window
			window = (SeparateWindowDisplayMail)app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_LAUNCH_IN_SEPARATE_WINDOW);
			
			window.zSetWindowTitle(subject);
			window.zWaitForActive();		// Make sure the window is there
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
			
			// Click redirect
			SeparateWindowDialogRedirect dialog = (SeparateWindowDialogRedirect)window.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_REDIRECT);
			dialog.zFillField(SeparateWindowDialogRedirect.Field.To, ZmailAccount.AccountB().EmailAddress);
			dialog.zClickButton(Button.B_OK);


		} finally {
			
			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}
			
		}

		
		// Verify the redirected message is received
		MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountB(), "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the redirected message is received");
		ZAssert.assertEquals(received.dRedirectedFromRecipient.dEmailAddress, app.zGetActiveAccount().EmailAddress, "Verify the message shows as redirected from the test account");


	}

	@Bugs( ids = "62170")
	@Test(	description = "Redirect message, using 'Redirect' shortcut key  - in separate window",
			groups = { "functional" })
	public void RedirectMessage_02() throws HarnessException {
		throw new HarnessException("See bug https://bugzilla.zmail.com/show_bug.cgi?id=62170");
	}
	



}
