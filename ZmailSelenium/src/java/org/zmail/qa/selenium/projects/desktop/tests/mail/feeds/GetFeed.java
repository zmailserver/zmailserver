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
package com.zimbra.qa.selenium.projects.desktop.tests.mail.feeds;

import java.net.*;
import java.util.*;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.desktop.core.AjaxCommonTest;

public class GetFeed extends AjaxCommonTest {

	public GetFeed() {
		logger.info("New "+ GetFeed.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = 6578132883123088454L;
		{
		    put("zimbraPrefGroupMailBy", "message");
		    put("zimbraPrefReadingPaneLocation", "bottom");
		}};
	}


	@Test(	description = "Verify a feed appears in the folder tree",
			groups = { "functional" })
	public void GetFeed_01() throws HarnessException, MalformedURLException {

		FolderItem root = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.UserRoot);
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);

		String foldername = "folder" + ZimbraSeleniumProperties.getUniqueString();
		URL feed = new URL("http", "rss.news.yahoo.com", 80, "/rss/topstories");

		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zimbraMail'>" +
					"<folder name='"+ foldername +"' l='"+ root.getId() +"' url='"+ feed.toString() +"'/>" +
				"</CreateFolderRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // Click on the "Inbox" to refresh
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, inbox);

		// Verify the feed exists
		// Get all the messages in the inbox
		List<FolderItem> folders = app.zTreeMail.zListGetFolders();
		ZAssert.assertNotNull(folders, "Verify the folder list exists");

		// Make sure the message appears in the list
		FolderItem found = null;
		for (FolderItem f : folders) {
			logger.info("Subject: looking for "+ foldername +" found: "+ f.getName());
			if ( f.getName().contains(foldername) ) {
				found = f;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the feed is in the folder tree");

		
	}


	@Test(	description = "Reload a feed",
			groups = { "functional" })
	public void GetFeed_02() throws HarnessException, MalformedURLException {

		FolderItem root = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.UserRoot);
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);

		String foldername = "folder" + ZimbraSeleniumProperties.getUniqueString();
		URL url = new URL("http", "rss.news.yahoo.com", 80, "/rss/topstories");

		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zimbraMail'>" +
					"<folder name='"+ foldername +"' l='"+ root.getId() +"' url='"+ url.toString() +"'/>" +
				"</CreateFolderRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      FolderItem feed = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);

		// Click on the "Inbox" to refresh
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, inbox);

		// Click on the "Feed"
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, feed);


		// Click on the "Reload Feed" button
		app.zPageMail.zToolbarPressButton(Button.B_LOADFEED);
		
		
		// Get the list of items
		List<MailItem> messages = app.zPageMail.zListGetMessages();
		ZAssert.assertGreaterThan(messages.size(), 0, "Verify that RSS items exist in the list");
		
		
	}


}
