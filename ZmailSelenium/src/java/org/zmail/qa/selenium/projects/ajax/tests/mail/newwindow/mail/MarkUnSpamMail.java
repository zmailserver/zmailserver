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

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.SeparateWindowDisplayMail;


public class MarkUnSpamMail extends PrefGroupMailByMessageTest {

	
	public MarkUnSpamMail() {
		logger.info("New "+ MarkUnSpamMail.class.getCanonicalName());
		
		
	}
	
	@Test(	description = "Mark a message as not spam, using 'Not Spam' toolbar button - in a separate window",
			groups = { "smoke" })
	public void MarkUnSpamMail_01() throws HarnessException {
		
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
		
		// Get the junk and inbox folder
		FolderItem junk = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Junk);
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);


		// Add a message to the account's junk folder
		app.zGetActiveAccount().soapSend(
				"<AddMsgRequest xmlns='urn:zmailMail'>" +
            		"<m l='"+ junk.getId() +"'>" +
                		"<content>From: foo@foo.com\n" +
"To: foo@foo.com \n" +
"Subject: "+ subject +"\n" +
"MIME-Version: 1.0 \n" +
"Content-Type: text/plain; charset=utf-8 \n" +
"Content-Transfer-Encoding: 7bit\n" +
"\n" +
"simple text string in the body\n" +
"</content>" +
                	"</m>" +
            	"</AddMsgRequest>");
		

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
				
		// Go to the Junk folder
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, junk);
		
		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		SeparateWindowDisplayMail window = null;

		try {
			
			// Choose Actions -> Launch in Window
			window = (SeparateWindowDisplayMail)app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_LAUNCH_IN_SEPARATE_WINDOW);
			
			window.zSetWindowTitle(subject);
			window.zWaitForActive();		// Make sure the window is there
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
		
			window.zToolbarPressButton(Button.B_RESPORTNOTSPAM);
			
			// Window is closed automatically by the client
			window = null;

		} finally {
			
			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}
			
		}
		
		// Get the mail item for the new message
		// Need 'is:anywhere' to include the spam folder
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "is:anywhere subject:("+ subject +")");
		ZAssert.assertNotNull(mail, "Make sure the mail is found");

		ZAssert.assertEquals(mail.dFolderId, inbox.getId(), "Verify the message is in the inbox folder");
				
	}


}
