/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.qa.selenium.projects.octopus.tests.myfiles.comments;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FileItem;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.OctopusAccount;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;
import org.zmail.qa.selenium.projects.octopus.ui.DialogFileShare;
import org.zmail.qa.selenium.projects.octopus.ui.DisplayFileComments;
import org.zmail.qa.selenium.projects.octopus.ui.DisplayFilePreview;
import org.zmail.qa.selenium.projects.octopus.ui.PageMyFiles;

public class AddComment extends OctopusCommonTest {

	private boolean _folderIsCreated = false;
	private String _folderName = null;
	private boolean _fileAttached = false;
	private String _fileId = null;
	private ZmailAccount grantee=null;

	@BeforeMethod(groups = { "always" })
	public void testReset() {
		_folderName = null;
		_folderIsCreated = false;
		_fileId = null;
		_fileAttached = false;
	}

	public AddComment() {
		logger.info("New " + AddComment.class.getCanonicalName());

		// test starts at the My Files tab
		super.startingPage = app.zPageMyFiles;
		super.startingAccountPreferences = null;

		grantee = new OctopusAccount();
		grantee.provision();
		grantee.authenticate();
	}

	@Test(description = "Add file comments - verify comments text in the file Comments through SOAP", groups = { "functional" })
	public void AddComment_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem rootFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/testbitmapfile.bmp";

		FileItem fileItem = new FileItem(filePath);

		String fileName = fileItem.getName();

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to My Files through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+ "<doc l='" + rootFolder.getId() + "'><upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		_fileAttached = true;
		_fileId = account.soapSelectValue(
				"//mail:SaveDocumentResponse//mail:doc", "id");

		String name = account.soapSelectValue(
				"//mail:SaveDocumentResponse//mail:doc", "name");

		// verify the file is uploaded
		ZAssert.assertEquals(fileName, name, "Verify file is uploaded");

		String comment = "Comment" + ZmailSeleniumProperties.getUniqueString();

		// Add comments to the file using SOAP
		account.soapSend("<AddCommentRequest xmlns='urn:zmailMail'> <comment parentId='"
				+ _fileId + "' text='" + comment + "'/></AddCommentRequest>");

		// Get file comments through SOAP
		account.soapSend("<GetCommentsRequest  xmlns='urn:zmailMail'> <comment parentId='"
				+ _fileId + "'/></GetCommentsRequest>");

		// Verify file comments through SOAP
		ZAssert.assertTrue(app.zPageOctopus.zVerifyElementText(account,
				"//mail:GetCommentsResponse//mail:comment", comment),
				"Verify comments text appears in the file Comments");
	}

	@Test(description = "Add file comments - verify account user name in the file Comments window", groups = { "smoke" })
	public void AddComment_02() throws HarnessException {
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

		// Save uploaded file to the root folder through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+ "<doc l='" + briefcaseRootFolder.getId() + "'>"
				+ "<upload id='" + attachmentId + "'/>"
				+ "</doc></SaveDocumentRequest>");

		// account.soapSelectNode("//mail:SaveDocumentResponse", 1);

		_fileAttached = true;
		_fileId = account.soapSelectValue(
				"//mail:SaveDocumentResponse//mail:doc", "id");

		String comment = "Comment" + ZmailSeleniumProperties.getUniqueString();

		// Add comments to the file using SOAP
		account.soapSend("<AddCommentRequest xmlns='urn:zmailMail'> <comment parentId='"
				+ _fileId + "' text='" + comment + "'/></AddCommentRequest>");

		// Click on My Files tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		// Verify file exists in My Files view
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementPresent(
				PageMyFiles.Locators.zMyFilesListViewItems.locator
				+ ":contains(" + fileName + ")", "3000"),
				"Verify file appears in My Files view");

		// Select file in the list view
		DisplayFilePreview filePreview = (DisplayFilePreview) app.zPageMyFiles
				.zListItem(Action.A_LEFTCLICK, fileName);

		// Click on Comments button
		DisplayFileComments fileComments = (DisplayFileComments) filePreview
				.zPressButton(Button.B_COMMENTS);

		// Verify comments text appears in the file Comments view
		ZAssert.assertTrue(app.zPageOctopus.zWaitForElementPresent(
				DisplayFileComments.Locators.zFileCommentsView.locator
				+ ":contains(" + comment + ")", "3000"),
				"Verify comments text appears in the file Comments view");

		// Verify account user name appears in the file Comments view
		ZAssert.assertTrue(app.zPageOctopus.zWaitForElementPresent(
				DisplayFileComments.Locators.zFileCommentsView.locator
				+ ":contains(" + account.EmailAddress.split("@")[0]
						+ ")", "3000"),
				"Verify account user name appears in the file Comments view");

		// Verify Close button in the comments view
		ZAssert.assertTrue(app.zPageOctopus.zWaitForElementPresent(
				DisplayFileComments.Locators.zFileCommentsViewCloseBtn.locator,
				"3000"), "Verify Close button in the Comments view");

		// close Comments view
		fileComments.zPressButton(Button.B_CLOSE);
	}

	@Test(description = "User should able to add comments on publicly shared file", groups = { "smoke" })
	public void AddComment_PubliclyShareFile() throws HarnessException
	{
		ZmailAccount account = app.zGetActiveAccount();
		String fileName=TEXT_FILE;

		_fileAttached = true;
		_fileId=uploadFileViaSoap(account, fileName);

		String name = account.soapSelectValue(
				"//mail:SaveDocumentResponse//mail:doc", "name");

		// verify the file is uploaded
		ZAssert.assertEquals(fileName, name, "Verify file is uploaded");

		//Click on Share publicly option in the file Context menu
		DialogFileShare dialogFileShare = (DialogFileShare) app.zPageMyFiles
				.zToolbarPressPulldown(Button.B_MY_FILES_LIST_ITEM,
						Button.O_FILE_SHARE, fileName);

		//Click on Close button
		dialogFileShare.zClickButton(Button.B_CLOSE);

		//make comment via soap
		String comment = "Comment" + ZmailSeleniumProperties.getUniqueString();
		makeCommentViaSoap(app.zGetActiveAccount(), _fileId, comment);

		// Verify comments text appears in the file Comments view
		ZAssert.assertTrue(app.zPageOctopus.zWaitForElementPresent(
				DisplayFileComments.Locators.zFileCommentsView.locator
				+ ":contains(" + comment + ")", "3000"),
				"Verify comments text appears in the file Comments view");
	}

	@Test(description = "Add comment on file from shared mount-point folder by grantee", groups = { "smoke1" })
	public void AddCommentOnFileUnderSharedMountpoint() throws HarnessException
	{
		//Create current account
		ZmailAccount account = app.zGetActiveAccount();
		String fileName=TEXT_FILE;

		//Create a folder
		FolderItem folder = createFolderViaSoap(account);

		_fileAttached = true;
		_fileId=uploadFileViaSoap(account, fileName,folder);

		//Share folder with grantee with Admin access.
		shareFolderViaSoap(account, grantee, folder, SHARE_AS_ADMIN);

		//Create a mount point of shared folder in grantee's account
		FolderItem granteeBrifcase = FolderItem.importFromSOAP(grantee, SystemFolder.Briefcase);
		String mountPointFolderName = "mountFolderAdmin";
		mountRequestViaSoap(account, grantee, folder, granteeBrifcase, mountPointFolderName);

		// Logout owner
		app.zPageOctopus.zLogout();

		// Login with grantee's Credential's.
		app.zPageLogin.zLogin(grantee);

		// click on mount point folder
		app.zPageMyFiles.zListItem(Action.A_LEFTCLICK, mountPointFolderName);

		//Select file in the list view
		DisplayFilePreview filePreview = (DisplayFilePreview) app.zPageMyFiles.zListItem(Action.A_LEFTCLICK, fileName);

		//Create comment.
		String comment = "GranteeComment" + ZmailSeleniumProperties.getUniqueString();

		//Click on Comments button
		DisplayFileComments fileComments = (DisplayFileComments) filePreview
				.zPressButton(Button.B_COMMENTS);

		//Add Comment
		app.zPageOctopus.sType(DisplayFileComments.Locators.zCommentsTextArea.locator, comment);
		app.zPageOctopus.sClickAt(DisplayFileComments.Locators.zAddCommentButton.locator, "0,0");

		// Verify comments text appears in the file Comments view
		ZAssert.assertTrue(app.zPageOctopus.zWaitForElementPresent(
				DisplayFileComments.Locators.zFileCommentsView.locator
				+ ":contains(" + comment + ")", "3000"),
				"Verify comments text appears in the file Comments view");
	}

	@Test(description = "Commentor pic should be displayed", groups = { "smoke" })
	public void VerifyCommentorPic() throws HarnessException
	{
		ZmailAccount account = app.zGetActiveAccount();
		String fileName=TEXT_FILE;

		_fileAttached = true;
		_fileId=uploadFileViaSoap(account, fileName);

		String name = account.soapSelectValue(
				"//mail:SaveDocumentResponse//mail:doc", "name");

		// verify the file is uploaded
		ZAssert.assertEquals(fileName, name, "Verify file is uploaded");

		// Select file in the list view
		DisplayFilePreview filePreview = (DisplayFilePreview) app.zPageMyFiles.zListItem(Action.A_LEFTCLICK, fileName);

		//make comment via soap
		String comment = "Comment" + ZmailSeleniumProperties.getUniqueString();
		makeCommentViaSoap(app.zGetActiveAccount(), _fileId, comment);

		// Click on Comments button
		DisplayFileComments fileComments = (DisplayFileComments) filePreview
				.zPressButton(Button.B_COMMENTS);

		// Verify comments text appears in the file Comments view
		ZAssert.assertTrue(app.zPageOctopus.zWaitForElementPresent(
				DisplayFileComments.Locators.zFileCommentsView.locator
				+ ":contains(" + comment + ")", "3000"),
				"Verify comments text appears in the file Comments view");

		// Verify user profile appears in the file Comments view
		ZAssert.assertTrue(app.zPageOctopus.sIsElementPresent(DisplayFileComments.Locators.zProfileImage.locator),
				"Verify commentor pic should be displayed");

		//close Comments view
		fileComments.zPressButton(Button.B_CLOSE);
	}

	@AfterMethod(groups = { "always" })
	public void testCleanup() {
		if (_fileAttached && _fileId != null) {
			try {
				// Delete it from Server
				app.zPageOctopus.deleteItemUsingSOAP(_fileId,
						app.zGetActiveAccount());
			} catch (Exception e) {
				logger.info("Failed while deleting the file",e);
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
				logger.info("Failed while removing the folder.",e);
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
