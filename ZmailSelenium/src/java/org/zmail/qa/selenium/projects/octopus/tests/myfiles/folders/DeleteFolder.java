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
package com.zimbra.qa.selenium.projects.octopus.tests.myfiles.folders;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.octopus.core.OctopusCommonTest;
import com.zimbra.qa.selenium.projects.octopus.ui.PageMyFiles;

public class DeleteFolder extends OctopusCommonTest {

	public DeleteFolder() {
		logger.info("New " + DeleteFolder.class.getCanonicalName());

		// All tests start at the Briefcase page
		super.startingPage = app.zPageMyFiles;
		super.startingAccountPreferences = null;

	}

	@Test(description = "Delete a folder through SOAP - verify deleted folder in the Trash tab", groups = { "smoke" })
	public void DeleteFolder_01() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		ZAssert.assertNotNull(briefcaseRootFolder,
				"Verify the Briefcase root folder is available");

		FolderItem trash = FolderItem.importFromSOAP(account,
				SystemFolder.Trash);
		ZAssert.assertNotNull(trash, "Verify the trash is available");

		// Create the sub-folder
		String subFolderName = "folder"
				+ ZimbraSeleniumProperties.getUniqueString();

		account.soapSend("<CreateFolderRequest xmlns='urn:zimbraMail'>"
				+ "<folder name='" + subFolderName + "' l='"
				+ briefcaseRootFolder.getId() + "' view='document'/>"
				+ "</CreateFolderRequest>");

		// Verify the sub-folder exists on the server
		FolderItem subFolder = FolderItem
				.importFromSOAP(account, subFolderName);
		ZAssert.assertNotNull(subFolder, "Verify the subfolder is available");

		// delete folder using SOAP
		app.zPageOctopus.deleteItemUsingSOAP(subFolder.getId(), account);

		// Verify the folder is now in the trash
		ZAssert.assertTrue(
				app.zPageOctopus.zIsFolderChild(subFolder, trash.getName()),
				"Verify the deleted folder moved to the trash");

		ZAssert.assertTrue(
				app.zPageOctopus.zIsFolderParent(trash, subFolderName),
				"Verify the subfolder's parent id matches trash folder id");
	}

	@Test(description = "Delete a sub-folder using right click context menu", groups = { "sanity" })
	public void DeleteFolder_02() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		ZAssert.assertNotNull(briefcaseRootFolder,
				"Verify the Briefcase root folder is available");

		FolderItem trash = FolderItem.importFromSOAP(account,
				SystemFolder.Trash);
		ZAssert.assertNotNull(trash, "Verify the trash is available");

		// Create the sub-folder
		String subFolderName = "folder"
				+ ZimbraSeleniumProperties.getUniqueString();

		account.soapSend("<CreateFolderRequest xmlns='urn:zimbraMail'>"
				+ "<folder name='" + subFolderName + "' l='"
				+ briefcaseRootFolder.getId() + "' view='document'/>"
				+ "</CreateFolderRequest>");

		// Verify the sub-folder exists on the server
		FolderItem subFolder = FolderItem
				.importFromSOAP(account, subFolderName);
		ZAssert.assertNotNull(subFolder, "Verify the subfolder is available");

		// click on My Files tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		SleepUtil.sleepVerySmall();

		// Delete the folder using drop down list option
		app.zPageMyFiles.zToolbarPressPulldown(Button.B_MY_FILES_LIST_ITEM,
				Button.O_DELETE, subFolderName);

		// Verify the deleted folder disappears from My Files tab
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementDeleted(
				PageMyFiles.Locators.zMyFilesListView.locator + ":contains("
						+ subFolderName + ")", "3000"),
				"Verify the deleted file disappears from My Files tab");

		// Verify the folder is now in the trash
		ZAssert.assertTrue(
				app.zPageOctopus.zIsFolderChild(subFolder, trash.getName()),
				"Verify the deleted folder moved to the trash");

		ZAssert.assertTrue(
				app.zPageOctopus.zIsFolderParent(trash, subFolderName),
				"Verify the subfolder's parent id matches trash folder id");
	}

}
