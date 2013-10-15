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
package org.zmail.qa.selenium.projects.ajax.tests.mail.sort.messages;

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;


public class SortByReceived extends PrefGroupMailByMessageTest {

	
	public SortByReceived() {
		logger.info("New "+ SortByReceived.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefReadingPaneLocation", "bottom");
	}
	
	@Test(	description = "Sort a list of messages by received (oldest -> newest)",
			groups = { "functional" })
	public void SortByReceived_01() throws HarnessException {
		
		// Create the message data
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
		String subjectA = "subjectA" + ZmailSeleniumProperties.getUniqueString(); 
		String subjectB = "subjectB" + ZmailSeleniumProperties.getUniqueString(); 
		
		ZmailAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>"
				+		"<m>"
				+			"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>"
				+			"<su>"+ subjectA +"</su>"
				+			"<mp ct='text/plain'>"
				+				"<content>simple text</content>"
				+			"</mp>"
				+		"</m>"
				+	"</SendMsgRequest>");

		SleepUtil.sleep(5000);
		
		ZmailAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>"
			+		"<m>"
			+			"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>"
			+			"<su>"+ subjectB +"</su>"
			+			"<mp ct='text/plain'>"
			+				"<content>simple text</content>"
			+			"</mp>"
			+		"</m>"
			+	"</SendMsgRequest>");


	


		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Click on Inbox
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, inbox);
		
		// First, sort by subject to clear the order
		app.zPageMail.zToolbarPressButton(Button.B_MAIL_LIST_SORTBY_FLAGGED);

		// Now, click on "subject"
		app.zPageMail.zToolbarPressButton(Button.B_MAIL_LIST_SORTBY_RECEIVED);
				
		// Get all the messages in the inbox
		app.zGetActiveAccount().soapSend(
				"<GetPrefsRequest xmlns='urn:zmailAccount'>"
    		+		"<pref name='zmailPrefSortOrder'/>"
			+	"</GetPrefsRequest>");
	
		
		List<MailItem> messages = app.zPageMail.zListGetMessages();
		ZAssert.assertNotNull(messages, "Verify the message list exists");

		MailItem itemB = null;
		for (MailItem m : messages) {
			if ( subjectB.equals(m.gSubject) ) {
				itemB = m;
			}
			if ( subjectA.equals(m.gSubject) ) {
				ZAssert.assertNotNull(itemB, "Item A is in the list.  Verify Item B has already been found.");
			}
		}
		
		ZAssert.assertNotNull(itemB, "Verify Item B was found.");

		
	}


	@Test(	description = "Sort a list of messages by received (newest -> oldest)",
			groups = { "functional" })
	public void SortByReceived_02() throws HarnessException {
		
		// Create the message data
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
		String subjectA = "subjectA" + ZmailSeleniumProperties.getUniqueString(); 
		String subjectB = "subjectB" + ZmailSeleniumProperties.getUniqueString(); 
		
		ZmailAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>"
			+		"<m>"
			+			"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>"
			+			"<su>"+ subjectA +"</su>"
			+			"<mp ct='text/plain'>"
			+				"<content>simple text</content>"
			+			"</mp>"
			+		"</m>"
			+	"</SendMsgRequest>");

	SleepUtil.sleep(5000);
	
	ZmailAccount.AccountA().soapSend(
			"<SendMsgRequest xmlns='urn:zmailMail'>"
		+		"<m>"
		+			"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>"
		+			"<su>"+ subjectB +"</su>"
		+			"<mp ct='text/plain'>"
		+				"<content>simple text</content>"
		+			"</mp>"
		+		"</m>"
		+	"</SendMsgRequest>");


	


		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Click on Inbox
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, inbox);
		
		// First, sort by subject to clear the order
		app.zPageMail.zToolbarPressButton(Button.B_MAIL_LIST_SORTBY_FLAGGED);

		// Now, click on "subject"
		app.zPageMail.zToolbarPressButton(Button.B_MAIL_LIST_SORTBY_RECEIVED);
				
		// Now, click on "subject" to reverse the order
		app.zPageMail.zToolbarPressButton(Button.B_MAIL_LIST_SORTBY_RECEIVED);
				
		// Get all the messages in the inbox
		app.zGetActiveAccount().soapSend(
				"<GetPrefsRequest xmlns='urn:zmailAccount'>"
    		+		"<pref name='zmailPrefSortOrder'/>"
			+	"</GetPrefsRequest>");
	
		
		List<MailItem> messages = app.zPageMail.zListGetMessages();
		ZAssert.assertNotNull(messages, "Verify the message list exists");

		MailItem itemA = null;
		for (MailItem m : messages) {
			if ( subjectA.equals(m.gSubject) ) {
				itemA = m;
			}
			if ( subjectB.equals(m.gSubject) ) {
				ZAssert.assertNotNull(itemA, "Item B is in the list.  Verify Item A has already been found.");
			}
		}
		
		ZAssert.assertNotNull(itemA, "Verify Item A was found.");


		
	}


}
