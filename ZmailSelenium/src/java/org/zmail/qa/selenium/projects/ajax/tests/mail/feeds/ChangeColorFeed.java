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
package org.zmail.qa.selenium.projects.ajax.tests.mail.feeds;

import java.net.*;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DialogEditFolder;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DialogEditFolder.FolderColor;

public class ChangeColorFeed extends PrefGroupMailByMessageTest {

	public ChangeColorFeed() {
		logger.info("New " + ChangeColorFeed.class.getCanonicalName());

		
		
		

	}

	@Test(
			description = "Edit a feedfolder, change the color (Context menu -> Edit)", 
			groups = { "functional" })
	public void ChangeColorFeed_01() throws HarnessException, MalformedURLException {

		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
		ZAssert.assertNotNull(inbox, "Verify the inbox is available");

		// Create a subfolder in Inbox
		String feedname = "feed" + ZmailSeleniumProperties.getUniqueString();
		URL feedurl = new URL("http", "rss.news.yahoo.com", 80, "/rss/topstories");

		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='"+ feedname +"' l='"+ inbox.getId() +"' url='"+ feedurl.toString() +"'/>"
				+	"</CreateFolderRequest>");

		FolderItem feed = FolderItem.importFromSOAP(app.zGetActiveAccount(), feedname);
		ZAssert.assertNotNull(feed, "Verify the subfolder is available");

		
		// Click on Get Mail to refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Rename the folder using context menu
		DialogEditFolder dialog = (DialogEditFolder) app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_TREE_EDIT, feed);
		ZAssert.assertNotNull(dialog, "Verify the dialog opened");

		// Change the color, click OK
		dialog.zSetNewColor(FolderColor.Gray);
		dialog.zClickButton(Button.B_OK);

		// Check the color
		app.zGetActiveAccount().soapSend(
				"<GetFolderRequest xmlns='urn:zmailMail'>"
			+		"<folder id='" + feed.getId() + "'/>"
			+	"</GetFolderRequest>");

		String color = app.zGetActiveAccount().soapSelectValue("//mail:folder[@name='" + feed.getName() + "']", "color");
		ZAssert.assertEquals(color, "8", "Verify the color of the folder is set to gray (8)");
	}

}
