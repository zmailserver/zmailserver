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
package com.zimbra.qa.selenium.projects.octopus.tests.sharing;

import java.util.List;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.items.FolderMountpointItem;
import com.zimbra.qa.selenium.framework.items.IOctListViewItem;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.OctopusAccount;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.octopus.core.OctopusCommonTest;
import com.zimbra.qa.selenium.projects.octopus.ui.PageMyFiles;
public class CreateMountpoint extends OctopusCommonTest {

	private ZimbraAccount ownerAccount = null;

	public CreateMountpoint() {
		logger.info("New " + CreateMountpoint.class.getCanonicalName());

		// Test starts at the Octopus page
		super.startingPage = app.zPageSharing;
		super.startingAccountPreferences = null;

		ownerAccount = new OctopusAccount();
		ownerAccount.provision();
		ownerAccount.authenticate();
	}

	@Test(description = "Create a mountpoint to a shared folder using SOAP", groups = { "functional" })
	public void CreateMountpoint_01() throws HarnessException {
		FolderItem ownerBriefcaseRootFolder = FolderItem.importFromSOAP(
				ownerAccount, SystemFolder.Briefcase);

		ZAssert.assertNotNull(ownerBriefcaseRootFolder,
				"Verify the owner Briefcase root folder exists");

		// Owner creates a folder, shares it with current user
		String ownerFoldername = "ownerFolder"
				+ ZimbraSeleniumProperties.getUniqueString();

		// Verify the share folder exists on the server
		FolderItem ownerFolderItem = createFolderViaSoap(ownerAccount, ownerFoldername,ownerBriefcaseRootFolder);

		ZAssert.assertNotNull(ownerFolderItem,"Verify the owner share folder exists");

		ZimbraAccount granteeAccount = app.zGetActiveAccount();

		// Current user creates the mountpoint that points to the share
		FolderItem currentAccountRootFolder = FolderItem.importFromSOAP(
				granteeAccount, SystemFolder.Briefcase);

		String folderMountpointName = "mountpoint"
				+ ZimbraSeleniumProperties.getUniqueString();

		mountFolderViaSoap(ownerAccount, granteeAccount, ownerFolderItem, SHARE_AS_READ, currentAccountRootFolder, folderMountpointName);

		// Verify the mountpoint exists on the server
		FolderMountpointItem folderMountpointItem = FolderMountpointItem
				.importFromSOAP(granteeAccount, folderMountpointName);

		ZAssert.assertNotNull(folderMountpointItem,
				"Verify the mountpoint is available");

		ZAssert.assertEquals(folderMountpointItem.getName(), folderMountpointName,
				"Verify the server mountpoint name");

		// click on My Files tab
		PageMyFiles pageMyFiles = (PageMyFiles) app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		// Verify the mountpoint exists in the list view
		List<IOctListViewItem> items = app.zPageOctopus.zGetListViewItems();

		ZAssert.assertNotNull(items,  "Verify list view is not empty");

		ZAssert.assertTrue(pageMyFiles.zWaitForElementPresent(
				PageMyFiles.Locators.zMyFilesListViewItems.locator
				+ ":contains(" + folderMountpointName
				+ ")", "3000"),
				"Verify the mountpoint folder is displayed in the My Files list view");
	}
}
