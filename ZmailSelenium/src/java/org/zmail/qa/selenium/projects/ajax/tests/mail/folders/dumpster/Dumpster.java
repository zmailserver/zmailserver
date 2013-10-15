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
package org.zmail.qa.selenium.projects.ajax.tests.mail.folders.dumpster;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.*;
import org.zmail.qa.selenium.projects.ajax.ui.FormRecoverDeletedItems.Field;


public class Dumpster extends PrefGroupMailByMessageTest {

	public Dumpster() {
		logger.info("New "+ Dumpster.class.getCanonicalName());

		
		
		super.startingAccountPreferences.put("zmailDumpsterEnabled", "TRUE");


	}

	@Test(
			description = "Verify the Trash folder's context menu does not contain dumpster",
			groups = { "functional" })
	public void RecoverItems_01() throws HarnessException {

		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String body = "body" + ZmailSeleniumProperties.getUniqueString();
		String foldername = "subfolder" + ZmailSeleniumProperties.getUniqueString();

		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Inbox);
		FolderItem trash = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Trash);

		// Create a subfolder (to recover the dumpster item to)
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
					"<folder name='" + foldername +"' l='"+ inbox.getId() +"'/>" +
				"</CreateFolderRequest>");
		FolderItem subfolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);

		// Add an message 
		app.zGetActiveAccount().soapSend(
					"<AddMsgRequest xmlns='urn:zmailMail'>" 
				+		"<m l='" + inbox.getId() + "'>"
				+			"<content>"
				+				"From: foo@foo.com\n" + "To: foo@foo.com \n"
				+				"Subject: " + subject + "\n" + "MIME-Version: 1.0 \n"
				+				"Content-Type: text/plain; charset=utf-8 \n"
				+				"Content-Transfer-Encoding: 7bit\n" 
				+				"\n"
				+				body +" \n"
				+				"\n"
				+				"\n"
				+			"</content>"
				+		"</m>"
				+	"</AddMsgRequest>");
		
		String messageID = app.zGetActiveAccount().soapSelectValue("//mail:AddMsgResponse//mail:m", "id");
		ZAssert.assertNotNull(messageID, "Verify the messageID exists");
		
		// https://bugzilla.zmail.com/show_bug.cgi?id=62029
		// Workaround: search for the message before deleting
		app.zGetActiveAccount().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='message'>"
				+		"<query>subject:("+ subject +")</query>"
				+	"</SearchRequest>");
		
		
		// Delete the message, putting it in the dumpster
		app.zGetActiveAccount().soapSend(
				"<MsgActionRequest xmlns='urn:zmailMail'>" 
			+		"<action id='"+ messageID +"' op='delete'/>"
			+	"</MsgActionRequest>");
		

		// Click on Get Mail to refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		
		// Right click on Trash, select "Recover Deleted Items"
		FormRecoverDeletedItems form = 
			(FormRecoverDeletedItems) app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_RECOVER_DELETED_ITEMS, trash);
		ZAssert.assertNotNull(form, "Verify the 'recover deleted items' dialog pops up");

		// Search for the message
		form.zFillField(Field.Search, body);
		form.zToolbarPressButton(Button.B_SEARCH);

		// Click on the message
		form.zListItem(Action.A_LEFTCLICK, subject);
		
		// Click "Recover To" subfolder
		DialogMove dialog = (DialogMove) form.zToolbarPressButton(Button.B_RECOVER_TO);
		dialog.zClickTreeFolder(subfolder);
		dialog.zClickButton(Button.B_OK);

		// Dismiss the 'Recover deleted items' dialog
		form.zToolbarPressButton(Button.B_CLOSE);

		// Verify the message is back
		// Work around for https://bugzilla.zmail.com/show_bug.cgi?id=62101#c1
		// Search by folder ID rather than by subject
		MailItem message = MailItem.importFromSOAP(app.zGetActiveAccount(), "inid:"+ subfolder.getId());
		ZAssert.assertNotNull(message, "Verify the message is returned to the mailbox");


		

	}
}
