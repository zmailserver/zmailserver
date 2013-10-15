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
package org.zmail.qa.selenium.projects.octopus.tests.myfiles.folders;

import org.testng.annotations.*;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;

public class CreateFolder extends OctopusCommonTest {

	private boolean _folderIsCreated = false;
	private String _folderName = null;

	public CreateFolder() {
		logger.info("New " + CreateFolder.class.getCanonicalName());

		// test starts at the My Files tab
		super.startingPage = app.zPageMyFiles;
		super.startingAccountPreferences = null;
	}

	@Test(description = "Create a new folder using drop down list option", groups = { "sanity" })
	public void CreateFolder_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Make sure size of the client and server subfolders match
		int serversize = briefcaseRootFolder.getSubfolders().size();
		int clientsize = app.zPageOctopus.zGetListViewFolderItems().size();
		
		ZAssert.assertEquals(clientsize, serversize,
				"Verify size of the client and server subfolders match");

		// Set the new folder name
		// _folderName = "folder" + ZmailSeleniumProperties.getUniqueString();

		app.zPageMyFiles.zToolbarPressPulldown(Button.B_MY_FILES,
				Button.O_NEW_FOLDER);

		// refresh Octopus page
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		SleepUtil.sleepSmall();

		// Make sure the folder was created on client
		int newsize = app.zPageOctopus.zGetListViewFolderItems().size();
		ZAssert.assertTrue(newsize > clientsize,
				"Verify the new folder created");

		// Make sure the new size of the client and server subfolders match
		briefcaseRootFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);
		serversize = briefcaseRootFolder.getSubfolders().size();
		ZAssert.assertEquals(newsize, serversize,
				"Verify size of the client and server subfolders match");
	}

	@AfterMethod(groups = { "always" })
	public void createFolderTestCleanup() {
		if (_folderIsCreated) {
			try {
				// Delete it from Server
				FolderItem
						.deleteUsingSOAP(app.zGetActiveAccount(), _folderName);
			} catch (Exception e) {
				logger.info("Failed while removing the folder.", e);
			} finally {
				_folderName = null;
				_folderIsCreated = false;
			}
		}
	}
}
