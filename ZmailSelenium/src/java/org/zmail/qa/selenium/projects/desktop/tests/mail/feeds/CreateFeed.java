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
package org.zmail.qa.selenium.projects.desktop.tests.mail.feeds;

import java.net.*;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.mail.DialogCreateFolder;

public class CreateFeed extends AjaxCommonTest {

	public CreateFeed() {
		logger.info("New "+ CreateFeed.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = null;
	}


	@Test(	description = "Create a new feed by clicking 'new folder' on folder tree",
			groups = { "functional" })
			public void CreateFeed_01() throws HarnessException, MalformedURLException {

		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
		URL feed = new URL("http", "rss.news.yahoo.com", 80, "/rss/topstories");

		// Click on the "new folder" button
		DialogCreateFolder createFolderDialog = (DialogCreateFolder)app.zTreeMail.zPressButton(Button.B_TREE_NEWFOLDER);

		createFolderDialog.zEnterFolderName(foldername);
		createFolderDialog.zClickSubscribeFeed(true);
		createFolderDialog.zEnterFeedURL(feed);

		createFolderDialog.zClickButton(Button.B_OK);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		// Make sure the folder was created on the ZCS server
		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);
		ZAssert.assertNotNull(folder, "Verify the feed exists on the server");

		ZAssert.assertEquals(folder.getName(), foldername, "Verify the server and client feed names match");

		// getUrl() doesn't seem to be implemented in Helix
		//	   ZAssert.assertEquals(folder.getUrl(), feed.toString(), "Verify the server and client feed URLs match");

	}


}
