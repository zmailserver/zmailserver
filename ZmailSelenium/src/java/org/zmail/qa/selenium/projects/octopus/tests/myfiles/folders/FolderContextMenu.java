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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;
import org.zmail.qa.selenium.projects.octopus.ui.PageOctopus;

public class FolderContextMenu extends OctopusCommonTest {

	private boolean _folderIsCreated = false;
	private String _folderName = null;
	private boolean _fileAttached = false;
	private String _fileId = null;

	public FolderContextMenu() {
		logger.info("New " + FolderContextMenu.class.getCanonicalName());

		// All tests start at the Briefcase page
		super.startingPage = app.zPageMyFiles;
		super.startingAccountPreferences = null;

	}

	@Test(description = "Verify the Context menu items in the Folder drop down menu", groups = { "sanity" })
	public void FolderContextMenu_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		ZAssert.assertNotNull(briefcaseRootFolder,
				"Verify the Briefcase root folder is available");

		// Create the sub-folder
		String subFolderName = "folder"
				+ ZmailSeleniumProperties.getUniqueString();

		account.soapSend("<CreateFolderRequest xmlns='urn:zmailMail'>"
				+ "<folder name='" + subFolderName + "' l='"
				+ briefcaseRootFolder.getId() + "' view='document'/>"
				+ "</CreateFolderRequest>");

		// Verify the sub-folder exists on the server
		FolderItem subFolder = FolderItem
				.importFromSOAP(account, subFolderName);
		ZAssert.assertNotNull(subFolder, "Verify the subfolder is available");

		_folderIsCreated = true;
		_folderName = subFolderName;

		// click on My Files tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		SleepUtil.sleepVerySmall();

		// Click on list item drop down menu
		app.zPageMyFiles.zListItem(Action.A_LEFTCLICK,
				Button.B_MY_FILES_LIST_ITEM, subFolderName);

		// Verify the items in the list folder context menu

		// Verify Share item is present
		ZAssert.assertTrue(
				app.zPageMyFiles.sIsElementPresent(PageOctopus.Locators.zShareItem.locator),
				"Verify Share item is present");

		// Verify Rename item is present
		ZAssert.assertTrue(app.zPageMyFiles
				.sIsElementPresent(PageOctopus.Locators.zRenameItem.locator),
				"Verify Rename item is present");

		// Verify Move item is present
		ZAssert.assertTrue(
				app.zPageMyFiles.sIsElementPresent(PageOctopus.Locators.zMoveItem.locator),
				"Verify Move item is present");

		// Verify Delete item is present
		ZAssert.assertTrue(app.zPageMyFiles
				.sIsElementPresent(PageOctopus.Locators.zDeleteItem.locator),
				"Verify Delete item is present");

		// click on My Files tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);
	}

	@AfterMethod(groups = { "always" })
	public void testCleanup() {
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
		if (_fileAttached && _fileId != null) {
			try {
				// Delete it from Server
				app.zPageOctopus.deleteItemUsingSOAP(_fileId,
						app.zGetActiveAccount());
			} catch (Exception e) {
				logger.info("Failed while deleting the file", e);
			} finally {
				_fileId = null;
				_fileAttached = false;
			}
		}

	}
}
