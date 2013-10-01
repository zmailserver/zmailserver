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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.items.FolderMountpointItem;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.OctopusAccount;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.octopus.core.OctopusCommonTest;
import com.zimbra.qa.selenium.projects.octopus.ui.DialogFolderShare;
import com.zimbra.qa.selenium.projects.octopus.ui.PageMyFiles;

public class RemoveMountpoint extends OctopusCommonTest {

	private ZimbraAccount ownerAccount = null;
	private boolean _folderIsCreated = false;
	private String _folderName = null;
	private boolean _fileAttached = false;
	private String _fileId = null;

	@BeforeMethod(groups = { "always" })
	public void testReset() {
		_folderName = null;
		_folderIsCreated = false;
		_fileId = null;
		_fileAttached = false;
	}

	public RemoveMountpoint() {
		logger.info("New " + RemoveMountpoint.class.getCanonicalName());

		// Test starts at the Octopus page
		super.startingPage = app.zPageSharing;
		super.startingAccountPreferences = null;

		ownerAccount = new OctopusAccount();
		ownerAccount.provision();
		ownerAccount.authenticate();
	}

	@Test(description = "Remove a mountpoint to a shared folder using pull down list", groups = { "functional" })
	public void RemoveMountpoint_01() throws HarnessException {
		FolderItem ownerBriefcaseRootFolder = FolderItem.importFromSOAP(
				ownerAccount, SystemFolder.Briefcase);

		ZAssert.assertNotNull(ownerBriefcaseRootFolder,
				"Verify the owner Briefcase root folder exists");

		// Owner creates a folder, shares it with current user
		String ownerFoldername = "ownerFolder"
				+ ZimbraSeleniumProperties.getUniqueString();

		// Verify the share folder exists on the server
		FolderItem ownerFolder =  createFolderViaSoap(ownerAccount, ownerFoldername,ownerBriefcaseRootFolder);

		ZAssert.assertNotNull(ownerFolder,"Verify the owner share folder exists");

		ZimbraAccount currentGranteeAccount = app.zGetActiveAccount();

		shareFolderViaSoap(ownerAccount, currentGranteeAccount, ownerFolder, SHARE_AS_ADMIN);

		// Currrent user gets share notification
		String getShareNotifcationRequest = "<GetShareNotificationsRequest xmlns='urn:zimbraMail'/>";

		app.zPageOctopus.waitForResponse(currentGranteeAccount, getShareNotifcationRequest, ownerFoldername, 5);

		currentGranteeAccount.soapSend(getShareNotifcationRequest);

		// Current user creates the mountpoint that points to the share
		FolderItem currentAccountRootFolder = FolderItem.importFromSOAP(
				currentGranteeAccount, SystemFolder.Briefcase);

		String folderMountpointName = "mountpoint"
				+ ZimbraSeleniumProperties.getUniqueString();

		mountRequestViaSoap(ownerAccount, currentGranteeAccount, ownerFolder, currentAccountRootFolder, folderMountpointName);

		// Verify the mountpoint exists on the server
		FolderMountpointItem folderMountpointItem = FolderMountpointItem
				.importFromSOAP(currentGranteeAccount, folderMountpointName);

		ZAssert.assertNotNull(folderMountpointItem,"Verify the mountpoint is available");

		// Open My Files page
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		SleepUtil.sleepVerySmall();

		// Select Share option from the Context menu
		DialogFolderShare dialogShare = (DialogFolderShare) app.zPageMyFiles
				.zToolbarPressPulldown(Button.B_MY_FILES_LIST_ITEM,
						Button.O_FOLDER_SHARE, folderMountpointName);

		// Click on Leave This shared folder button
		dialogShare.zClickButton(Button.B_LEAVE_THIS_SHARED_FOLDER);

		// Verify the mountpoint folder disappears from My Files tab
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementDeleted(
				PageMyFiles.Locators.zMyFilesListView.locator + ":contains("
						+ folderMountpointItem.getName() + ")", "3000"),
				"Verify mountpoint folder disappears from My Files tab");
	}

	@Test(description = "Remove a mountpoint to a shared folder - verify it appears in the Ignored list", groups = { "functional" })
	public void RemoveMountpoint_02() throws HarnessException {
		FolderItem ownerBriefcaseRootFolder = FolderItem.importFromSOAP(
				ownerAccount, SystemFolder.Briefcase);

		ZAssert.assertNotNull(ownerBriefcaseRootFolder,
				"Verify the owner Briefcase root folder exists");

		// Owner creates a folder, shares it with current user
		String ownerFoldername = "ownerFolder"
				+ ZimbraSeleniumProperties.getUniqueString();

		// Verify the share folder exists on the server
		FolderItem ownerFolder = createFolderViaSoap(ownerAccount, ownerFoldername,ownerBriefcaseRootFolder);

		ZAssert.assertNotNull(ownerFolder,
				"Verify the owner share folder exists");

		ZimbraAccount currentGranteeAccount = app.zGetActiveAccount();

		shareFolderViaSoap(ownerAccount, currentGranteeAccount, ownerFolder, SHARE_AS_ADMIN);

		// Current user creates the mountpoint that points to the share
		FolderItem currentAccountRootFolder = FolderItem.importFromSOAP(
				currentGranteeAccount, SystemFolder.Briefcase);

		mountRequestViaSoap(ownerAccount, currentGranteeAccount, ownerFolder, currentAccountRootFolder, ownerFoldername);

		// Verify the mountpoint exists on the server
		FolderMountpointItem folderMountpointItem = FolderMountpointItem
				.importFromSOAP(currentGranteeAccount, ownerFoldername);
		ZAssert.assertNotNull(folderMountpointItem,
				"Verify the mountpoint is available");

		// click on Sharing tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_SHARING);

		// Open My Files page
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		SleepUtil.sleepVerySmall();

		// Select Share option from the Context menu
		DialogFolderShare dialogShare = (DialogFolderShare) app.zPageMyFiles
				.zToolbarPressPulldown(Button.B_MY_FILES_LIST_ITEM,
						Button.O_FOLDER_SHARE, ownerFoldername);

		SleepUtil.sleepSmall();

		// Click on Leave This shared folder button
		dialogShare.zClickButton(Button.B_LEAVE_THIS_SHARED_FOLDER);

		// Verify the mountpoint folder disappears from My Files tab
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementDeleted(
				PageMyFiles.Locators.zMyFilesListView.locator + ":contains("
						+ ownerFoldername + ")", "5000"),
				"Verify mountpoint folder disappears from My Files tab");

		// click on Sharing tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_SHARING);

		// Verify the removed mount point appears in the Ignored Items List View
		// temporary disable assertion until feature is implemented
		/*
		 * ZAssert.assertTrue(app.zPageSharing.zWaitForElementPresent(
		 * PageSharing.Locators.zIgnoredItemsView.locator + ":contains(" +
		 * folderMountpointItem.getName() + ")", "5000"),
		 * "Verify removed mount point appears in the Ignored Items List View");
		 */
	}

	@AfterMethod(groups = { "always" })
	public void testCleanup() {
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
		try {
			// Refresh view
			// ZimbraAccount account = app.zGetActiveAccount();
			// FolderItem item =
			// FolderItem.importFromSOAP(account,SystemFolder.Briefcase);
			// account.soapSend("<GetFolderRequest xmlns='urn:zimbraMail'><folder l='1' recursive='0'/>"
			// + "</GetFolderRequest>");
			// account.soapSend("<GetFolderRequest xmlns='urn:zimbraMail' requestId='folders' depth='1' tr='true' view='document'><folder l='"
			// + item.getId() + "'/></GetFolderRequest>");
			// account.soapSend("<GetActivityStreamRequest xmlns='urn:zimbraMail' id='16'/>");
			// app.zGetActiveAccount().accountIsDirty = true;
			// app.zPageOctopus.sRefresh();

			// Empty trash
			app.zPageTrash.emptyTrashUsingSOAP(app.zGetActiveAccount());

			app.zPageOctopus.zLogout();
		} catch (Exception e) {
			logger.info("Failed while emptying Trash", e);
		}
	}
}
