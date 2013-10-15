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
package org.zmail.qa.selenium.projects.desktop.tests.preferences.general.searches;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.ConversationItem;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.RecipientItem;
import org.zmail.qa.selenium.framework.items.RecipientItem.RecipientType;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.preferences.TreePreferences.TreeItem;


public class ZmailPrefIncludeTrashInSearch extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public ZmailPrefIncludeTrashInSearch() {
		logger.info("New "+ ZmailPrefIncludeTrashInSearch.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPagePreferences;

		// Make sure we are using an account with conversation view
		super.startingAccountPreferences = new HashMap<String, String>() {{
				    put("zmailPrefIncludeTrashInSearch", "TRUE");
				    put("zmailPrefGroupMailBy", "conversation");
				}};
	}
	

	@Test(	description = "Verify zmailPrefIncludeTrashInSearch setting when set to TRUE",
			groups = { "functional" })
	public void ZmailPrefIncludeTrashInSearchTrue_01() throws HarnessException {
		

		// Go to "General"
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.General);
		
		// Determine the status of the checkbox
		boolean checked = app.zPagePreferences.zGetCheckboxStatus("zmailPrefIncludeTrashInSearch");
		
		// Since zmailPrefIncludeSpamInSearch is set to TRUE, the checkbox should be checked
		ZAssert.assertTrue(checked, "Verify if zmailPrefIncludeTrashInSearch is TRUE, the preference box is checked" );
		
		// Click cancel to close preferences
		app.zPagePreferences.zNavigateAway(Button.B_NO);
		
	}

	@Test(	description = "Verify when zmailPrefIncludeTrashInSearch=TRUE, that trash is included in search",
			groups = { "functional" })
	public void ZmailPrefIncludeTrashInSearchTrue_02() throws HarnessException {
		
		// Check that the setting is correct
		ZmailPrefIncludeTrashInSearchTrue_01();
		
		String query = "query" + ZmailSeleniumProperties.getUniqueString();
		FolderItem inboxFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),
		      FolderItem.SystemFolder.Inbox);
		
		MailItem message1 = new MailItem();
		message1.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		message1.dFromRecipient = new RecipientItem("foo@example.com", RecipientType.From);
		message1.dToRecipients.add(new RecipientItem("bar@example.com", RecipientType.To));
		message1.dBodyText = query; 
		
		MailItem message2 = new MailItem();
		message2.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		message2.dFromRecipient = new RecipientItem("foo@example.com", RecipientType.From);
		message2.dToRecipients.add(new RecipientItem("bar@example.com", RecipientType.To));
		message2.dBodyText = query; 
		
		
		// Determine the folder ID's for inbox and trash
		app.zGetActiveAccount().soapSend("<GetFolderRequest xmlns = 'urn:zmailMail'/>");
		String inboxId = app.zGetActiveAccount().soapSelectValue("//mail:folder[@name='Inbox']", "id");
		String trashId = app.zGetActiveAccount().soapSelectValue("//mail:folder[@name='Trash']", "id");
		
		
		// Add a message to the inbox
		app.zGetActiveAccount().soapSend(
				"<AddMsgRequest xmlns='urn:zmailMail'>" +
                	"<m l='"+ inboxId +"'>" +
                    	"<content>" + message1.generateMimeString() + "</content>" +
                    "</m>" +
                "</AddMsgRequest>");
		
		
		// Add a message to the trash
		app.zGetActiveAccount().soapSend(
				"<AddMsgRequest xmlns='urn:zmailMail'>" +
                	"<m l='"+ trashId +"'>" +
                    	"<content>" + message2.generateMimeString() + "</content>" +
                    "</m>" +
                "</AddMsgRequest>");

		// Go to mail
		app.zPageMail.zNavigateTo();
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, inboxFolder);
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Search for the query
		app.zPageSearch.zAddSearchQuery(query);
		app.zPageSearch.zToolbarPressButton(Button.B_SEARCH);
		
		// Verify that both messages are in the list
		List<ConversationItem> items = app.zPageMail.zListGetConversations();
		
		boolean found1 = false;
		boolean found2 = false;
		for (ConversationItem c : items) {
			if ( c.gSubject.equals(message1.dSubject) ) {
				found1 = true;
				break;
			}
		}
		for (ConversationItem c : items) {
			if ( c.gSubject.equals(message2.dSubject) ) {
				found2 = true;
				break;
			}
		}
		
		ZAssert.assertTrue(found1, "Verify the message in the inbox is found");
		ZAssert.assertTrue(found2, "Verify the message in the trash is found");
		
		
	}

}
