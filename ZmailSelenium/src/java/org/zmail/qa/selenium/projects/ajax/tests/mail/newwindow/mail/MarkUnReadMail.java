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
import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.SeparateWindowDisplayMail;


public class MarkUnReadMail extends PrefGroupMailByMessageTest {

	
	public MarkUnReadMail() {
		logger.info("New "+ MarkUnReadMail.class.getCanonicalName());
		

	}
	



	@Test(	description = "Mark a message as unread by clicking on it, then using 'mu' hotkeys",
			groups = { "functional" })
	public void MarkUnReadMail_01() throws HarnessException {
		

		// Create the message data to be sent
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
		
		FolderItem inboxFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
		app.zGetActiveAccount().soapSend(
					"<AddMsgRequest xmlns='urn:zmailMail'>"
				+		"<m l='"+ inboxFolder.getId() +"' f=''>"
				+			"<content>"
				+				"From: foo@foo.com\n"
				+				"To: foo@foo.com \n"
				+				"Subject: "+ subject +"\n"
				+				"MIME-Version: 1.0 \n"
				+				"Content-Type: text/plain; charset=utf-8 \n"
				+				"Content-Transfer-Encoding: 7bit\n"
				+				"\n"
				+				"simple text string in the body\n"
				+			"</content>"
				+      	"</m>"
				+	"</AddMsgRequest>");
		
		

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
				
		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		SeparateWindowDisplayMail window = null;
		
		try {
			
			// Choose Actions -> Launch in Window
			window = (SeparateWindowDisplayMail)app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_LAUNCH_IN_SEPARATE_WINDOW);
			
			window.zSetWindowTitle(subject);
			window.zWaitForActive();		// Make sure the window is there
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
			
			window.zKeyboardShortcut(Shortcut.S_MAIL_MARKUNREAD);

		} finally {
			
			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}
			
		}


		// Verify the message is marked read in the server (flags attribute should not contain (u)nread)
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");
		ZAssert.assertStringContains(mail.getFlags(), "u", "Verify the message is marked unread in the server");
		
	}

	@Test(	description = "Mark a message as unread by action menu -> mark read",
			groups = { "functional" })
	public void MarkUnReadMail_02() throws HarnessException {
		
		// Create the message data to be sent
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();

		FolderItem inboxFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
		app.zGetActiveAccount().soapSend(
					"<AddMsgRequest xmlns='urn:zmailMail'>"
				+		"<m l='"+ inboxFolder.getId() +"' f=''>"
				+			"<content>"
				+				"From: foo@foo.com\n"
				+				"To: foo@foo.com \n"
				+				"Subject: "+ subject +"\n"
				+				"MIME-Version: 1.0 \n"
				+				"Content-Type: text/plain; charset=utf-8 \n"
				+				"Content-Transfer-Encoding: 7bit\n"
				+				"\n"
				+				"simple text string in the body\n"
				+			"</content>"
				+      	"</m>"
				+	"</AddMsgRequest>");

		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
				
		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		
		SeparateWindowDisplayMail window = null;
		
		try {
			
			// Choose Actions -> Launch in Window
			window = (SeparateWindowDisplayMail)app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_LAUNCH_IN_SEPARATE_WINDOW);
			
			window.zSetWindowTitle(subject);
			window.zWaitForActive();		// Make sure the window is there
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
			
			window.zToolbarPressPulldown(Button.B_ACTIONS, Button.O_MARK_AS_UNREAD);

		} finally {
			
			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}
			
		}


		// Verify the message is marked read in the server (flags attribute should not contain (u)nread)
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");
		ZAssert.assertStringContains(mail.getFlags(), "u", "Verify the message is marked read in the server");

	}
		
	@Bugs(ids = "64133")
	@Test(	description = "Verify mark unread is enabled in the separate window",
			groups = { "functional" })
	public void MarkUnReadMail_03() throws HarnessException {
		
		// Create the message data to be sent
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();

		FolderItem inboxFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
		app.zGetActiveAccount().soapSend(
					"<AddMsgRequest xmlns='urn:zmailMail'>"
				+		"<m l='"+ inboxFolder.getId() +"' f=''>"
				+			"<content>"
				+				"From: foo@foo.com\n"
				+				"To: foo@foo.com \n"
				+				"Subject: "+ subject +"\n"
				+				"MIME-Version: 1.0 \n"
				+				"Content-Type: text/plain; charset=utf-8 \n"
				+				"Content-Transfer-Encoding: 7bit\n"
				+				"\n"
				+				"simple text string in the body\n"
				+			"</content>"
				+      	"</m>"
				+	"</AddMsgRequest>");

		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
				
		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		
		SeparateWindowDisplayMail window = null;
		
		try {
			
			// Choose Actions -> Launch in Window
			window = (SeparateWindowDisplayMail)app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_LAUNCH_IN_SEPARATE_WINDOW);
			
			window.zSetWindowTitle(subject);
			window.zWaitForActive();		// Make sure the window is there
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
			
			// Verify that the icon is not disabled
			SleepUtil.sleep(10000);
			ZAssert.assertFalse(window.sIsElementPresent("css=div[id$='__MARK_UNREAD'][class~='ZHasText']"), "Verify that the icon is not disabled");

		} finally {
			
			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}
			
		}


	}
		


}
