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
package org.zmail.qa.selenium.projects.octopus.tests.myfiles.files;

import org.testng.annotations.*;
import org.zmail.qa.selenium.framework.items.FileItem;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;
import org.zmail.qa.selenium.projects.octopus.ui.DisplayFilePreview;
import org.zmail.qa.selenium.projects.octopus.ui.PageMyFiles;

public class MarkAsFavorite extends OctopusCommonTest {

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

	public MarkAsFavorite() {
		logger.info("New " + MarkAsFavorite.class.getCanonicalName());

		// test starts at the My Files tab
		super.startingPage = app.zPageMyFiles;
		super.startingAccountPreferences = null;
	}

	@Test(description = "Mark file as Favorite using Context menu - verify favorite icon becomes enabled in the preview panel", groups = { "smoke" })
	public void MarkAsFavorite_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/putty.log";

		FileItem file = new FileItem(filePath);
		String fileName = file.getName();

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+ "<doc l='" + briefcaseRootFolder.getId() + "'><upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		_fileAttached = true;
		_fileId = account.soapSelectValue(
				"//mail:SaveDocumentResponse//mail:doc", "id");

		// click on the My Files tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		// Verify file exists in My Files view
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementPresent(
				PageMyFiles.Locators.zMyFilesListViewItems.locator
						+ ":contains(" + fileName + ")", "3000"),
				"Verify file appears in My Files view");

		// mark file as favorite using drop down menu
		app.zPageMyFiles.zToolbarPressPulldown(Button.B_MY_FILES_LIST_ITEM,
				Button.O_FAVORITE, fileName);

		// Verify Watch icon becomes enabled
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementPresent(
				DisplayFilePreview.Locators.zFileWatchIcon.locator
						+ " span[class^=watched-icon]", "3000"),
				"Verify the favorite icon becomes enabled in the preview panel");

		// Verify the file was added to the Favorites using SOAP
		account.soapSend("<GetWatchingItemsRequest xmlns='urn:zmailMail'>"
				+ "</GetWatchingItemsRequest>");

		ZAssert.assertTrue(account.soapMatch(
				"//mail:GetWatchingItemsResponse//mail:item", "id", _fileId),
				"Verify file is added to Favorites");
	}

	@Test(description = "Mark file as Favorite / Not Favorite using Context menu - verify watch icon becomes enabled / disabled in the preview panel", groups = { "functional" })
	public void MarkAsFavorite_02() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/testtextfile.txt";

		FileItem file = new FileItem(filePath);
		String fileName = file.getName();

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+ "<doc l='" + briefcaseRootFolder.getId() + "'><upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		_fileAttached = true;
		_fileId = account.soapSelectValue(
				"//mail:SaveDocumentResponse//mail:doc", "id");

		// click on the My Files tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		// mark file as favorite using drop down menu
		app.zPageMyFiles.zToolbarPressPulldown(Button.B_MY_FILES_LIST_ITEM,
				Button.O_FAVORITE, fileName);

		// Verify Watch icon becomes enabled
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementPresent(
				DisplayFilePreview.Locators.zFileWatchIcon.locator
						+ " span[class^=watched-icon]", "3000"),
				"Verify the favorite icon becomes enabled in the preview panel");

		// Verify the file was added to the Favorites using SOAP
		account.soapSend("<GetWatchingItemsRequest xmlns='urn:zmailMail'>"
				+ "</GetWatchingItemsRequest>");

		ZAssert.assertTrue(account.soapMatch(
				"//mail:GetWatchingItemsResponse//mail:item", "id", _fileId),
				"Verify file is added to Favorites");

		// mark file as Not Favorite using drop down menu
		app.zPageMyFiles.zToolbarPressPulldown(Button.B_MY_FILES_LIST_ITEM,
				Button.O_NOT_FAVORITE, fileName);

		// Verify Watch icon becomes disabled
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementPresent(
				DisplayFilePreview.Locators.zFileWatchIcon.locator
						+ " span[class^=unwatched-icon]", "3000"),
				"Verify the favorite icon becomes disabled in the preview panel");
	}

	@Test(description = "Mark file as Favorite / Not Favorite clicking on watch icon - verify watch icon becomes enabled / disabled in the preview panel", groups = { "functional" })
	public void MarkAsFavorite_03() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/testtextfile.txt";

		FileItem file = new FileItem(filePath);
		String fileName = file.getName();

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to My Files through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+ "<doc l='" + briefcaseRootFolder.getId() + "'><upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		_fileAttached = true;
		_fileId = account.soapSelectValue(
				"//mail:SaveDocumentResponse//mail:doc", "id");

		// click on the My Files tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		// Verify file exists in My Files view
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementPresent(
				PageMyFiles.Locators.zMyFilesListViewItems.locator
						+ ":contains(" + fileName + ")", "3000"),
				"Verify file appears in My Files view");

		// Select file in the list view
		DisplayFilePreview filePreview = (DisplayFilePreview) app.zPageMyFiles
				.zListItem(Action.A_LEFTCLICK, fileName);

		// Verify File image icon becomes enabled
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementPresent(
				DisplayFilePreview.Locators.zFileImageIcon.locator, "3000"),
				"Verify the file image icon becomes enabled in the preview panel toolbar");

		// mark file as favorite clicking on watch icon
		filePreview.zPressButton(Button.B_WATCH);

		// Verify Watch icon becomes enabled
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementPresent(
				DisplayFilePreview.Locators.zFileWatchIcon.locator
						+ " span[class^=watched-icon]", "3000"),
				"Verify the favorite icon becomes enabled in the preview panel");

		// Verify the file was added to the Favorites using SOAP
		account.soapSend("<GetWatchingItemsRequest xmlns='urn:zmailMail'>"
				+ "</GetWatchingItemsRequest>");

		ZAssert.assertTrue(account.soapMatch(
				"//mail:GetWatchingItemsResponse//mail:item", "id", _fileId),
				"Verify file is added to Favorites");

		// Select file
		filePreview = (DisplayFilePreview) app.zPageMyFiles.zListItem(
				Action.A_LEFTCLICK, fileName);

		// Verify File image icon becomes enabled
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementPresent(
				DisplayFilePreview.Locators.zFileImageIcon.locator, "3000"),
				"Verify the file image icon becomes enabled in the preview panel toolbar");

		// unmark file as favorite clicking on unwatch icon
		filePreview.zPressButton(Button.B_UNWATCH);

		// Verify Watch icon becomes disabled
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementPresent(
				DisplayFilePreview.Locators.zFileWatchIcon.locator
						+ " span[class^=unwatched-icon]", "3000"),
				"Verify the favorite icon becomes disabled in the preview panel");
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
			// ZmailAccount account = app.zGetActiveAccount();
			// FolderItem item =
			// FolderItem.importFromSOAP(account,SystemFolder.Briefcase);
			// account.soapSend("<GetFolderRequest xmlns='urn:zmailMail'><folder l='1' recursive='0'/>"
			// + "</GetFolderRequest>");
			// account.soapSend("<GetFolderRequest xmlns='urn:zmailMail' requestId='folders' depth='1' tr='true' view='document'><folder l='"
			// + item.getId() + "'/></GetFolderRequest>");
			// account.soapSend("<GetActivityStreamRequest xmlns='urn:zmailMail' id='16'/>");
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
