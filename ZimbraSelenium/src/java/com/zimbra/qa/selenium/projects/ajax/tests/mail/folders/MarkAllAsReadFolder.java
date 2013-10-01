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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.folders;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;


public class MarkAllAsReadFolder extends PrefGroupMailByMessageTest {

	public MarkAllAsReadFolder() {
		logger.info("New "+ MarkAllAsReadFolder.class.getCanonicalName());

		
		
		

	}

	@Test(	description = "Mark all messages as read in folder (context menu)",
			groups = { "smoke" })
			public void MarkAllAsReadFolder_01() throws HarnessException {

		String foldername = "folder" + ZimbraSeleniumProperties.getUniqueString();
		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();

		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Inbox);

		// Create a subfolder in Inbox
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zimbraMail'>" +
				"<folder name='" + foldername +"' l='" + inbox.getId() +"'/>" +
		"</CreateFolderRequest>");

		// Make sure the folder was created on the server
		FolderItem subfolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);
		ZAssert.assertNotNull(subfolder, "Verify the folder exists on the server");

		// Add an unread message (f=u) to the new subfolder
		app.zGetActiveAccount().soapSend(
				"<AddMsgRequest xmlns='urn:zimbraMail'>" +
				"<m l='"+ subfolder.getId() +"' f='u'>" +
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

		// Click on Get Mail to refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		// Right click on folder, select "Mark all as read"
		app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_TREE_FOLDER_MARKASREAD, subfolder);


		// Make sure the folder was created on the server
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");
		ZAssert.assertNotNull(mail, "Verify the message exists");
		ZAssert.assertStringDoesNotContain(mail.getFlags(), "u", "Verify the mail flags does not contain (u)nread");

	}

}
