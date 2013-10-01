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
package com.zimbra.qa.selenium.projects.ajax.tests.briefcase.file;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.FileItem;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.ui.Shortcut;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.ajax.core.FeatureBriefcaseTest;
import com.zimbra.qa.selenium.projects.ajax.ui.*;

import org.testng.annotations.AfterMethod;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;

public class MoveFile extends FeatureBriefcaseTest {

	public MoveFile() throws HarnessException {
		logger.info("New " + MoveFile.class.getCanonicalName());

		super.startingPage = app.zPageBriefcase;

		if(ZimbraSeleniumProperties.zimbraGetVersionString().contains("FOSS")){
		    super.startingAccountPreferences.put("zimbraPrefShowSelectionCheckbox","TRUE");
		}
	
		super.startingAccountPreferences.put("zimbraPrefBriefcaseReadingPaneLocation", "bottom");				
			
		// Make sure we are using an account with message view
		// super.startingAccountPreferences.put("zimbraPrefGroupMailBy", "message");
	}

	@Test(description = "Upload file through RestUtil - move & verify through GUI", groups = { "smoke" })
	public void MoveFile_01() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem folderItem = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		String briefcaseFolderId = folderItem.getId();

		String name = "subFolder" + ZimbraSeleniumProperties.getUniqueString();

		// Create a subfolder to move the file into i.e. Briefcase/subfolder
		account.soapSend("<CreateFolderRequest xmlns='urn:zimbraMail'>"
				+ "<folder name='" + name + "' l='" + briefcaseFolderId + "'/>"
				+ "</CreateFolderRequest>");

		FolderItem subFolderItem = FolderItem.importFromSOAP(account, name);

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, folderItem, true);

		// Click on created subfolder		
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, subFolderItem);

		// Create file item
		String filePath = ZimbraSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/putty.log";

		FileItem fileItem = new FileItem(filePath);

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend(

		"<SaveDocumentRequest xmlns='urn:zimbraMail'>" +

		"<doc l='" + folderItem.getId() + "'>" +

		"<upload id='" + attachmentId + "'/>" +

		"</doc>" +

		"</SaveDocumentRequest>");

		// account.soapSelectNode("//mail:SaveDocumentResponse", 1);

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, folderItem, true);

		SleepUtil.sleepVerySmall();
		
		if(ZimbraSeleniumProperties.zimbraGetVersionString().contains(
    			"FOSS")){
		    app.zPageBriefcase.zListItem(Action.A_BRIEFCASE_CHECKBOX, fileItem);

		}else{
		    app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);
		}
		
		// Click on 'Move selected item' icon in toolbar
		if (ZimbraSeleniumProperties.zimbraGetVersionString().contains("7.2.")){
		    DialogMove chooseFolder = (DialogMove) app.zPageBriefcase
			    .zToolbarPressButton(Button.B_MOVE, fileItem);

		    // Choose folder on Confirmation dialog
		    chooseFolder.zClickTreeFolder(subFolderItem);
		    
		    // Click OK on Confirmation dialog
		    chooseFolder.zClickButton(Button.B_OK);
		}else{
		    // Click move -> subfolder
		    app.zPageBriefcase.zToolbarPressPulldown(Button.B_MOVE, subFolderItem);

		}
		
		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, folderItem, false);

		// Verify document was moved from the folder
		ZAssert.assertFalse(app.zPageBriefcase.isPresentInListView(fileItem
				.getName()), "Verify document was moved from the folder");

		// click on subfolder in tree view
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, subFolderItem, true);

		// Verify document was moved to the selected folder
		boolean present = app.zPageBriefcase.isPresentInListView(fileItem
				.getName());

		ZAssert.assertTrue(present,
				"Verify document was moved to the selected folder");
	}

	@Test(description = "Move File using 'm' keyboard shortcut", groups = { "functional" })
	public void MoveFile_02() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		String briefcaseRootFolderId = briefcaseRootFolder.getId();

		Shortcut shortcut = Shortcut.S_MOVE;

		String[] subFolderNames = {
				"subFolderName1" + ZimbraSeleniumProperties.getUniqueString(),
				"subFolderName2" + ZimbraSeleniumProperties.getUniqueString() };

		FolderItem[] subFolders = new FolderItem[subFolderNames.length];

		// Create sub-folders to move the message from/to: Briefcase/sub-folder
		for (int i = 0; i < subFolderNames.length; i++) {
			account.soapSend("<CreateFolderRequest xmlns='urn:zimbraMail'>"
					+ "<folder name='" + subFolderNames[i] + "' l='"
					+ briefcaseRootFolderId + "'/>" + "</CreateFolderRequest>");

			subFolders[i] = FolderItem.importFromSOAP(account,
					subFolderNames[i]);
		}

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseRootFolder,
				true);

		// Create file item
		String filePath = ZimbraSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/putty.log";

		FileItem fileItem = new FileItem(filePath);

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend(

		"<SaveDocumentRequest xmlns='urn:zimbraMail'>" +

		"<doc l='" + subFolders[0].getId() + "'>" +

		"<upload id='" + attachmentId + "'/>" +

		"</doc>" +

		"</SaveDocumentRequest>");

		// account.soapSelectNode("//mail:SaveDocumentResponse", 1);

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseRootFolder,
				true);

		// double-click on sub-folder1 in list view
		app.zPageBriefcase.zListItem(Action.A_DOUBLECLICK, subFolders[0]);

		// click on sub-folder1 in tree view to refresh view
		// app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, subFolders[0],
		// true);

		SleepUtil.sleepVerySmall();

		if(ZimbraSeleniumProperties.zimbraGetVersionString().contains(
    			"FOSS")){
		    app.zPageBriefcase.zListItem(Action.A_BRIEFCASE_CHECKBOX, fileItem);

		}else{
		    app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);
		}
		
		// Click the Move keyboard shortcut
		DialogMove chooseFolder = (DialogMove) app.zPageBriefcase
				.zKeyboardShortcut(shortcut);

		// Choose destination folder and Click OK on Confirmation dialog
		chooseFolder.zClickTreeFolder(subFolders[1]);

		//chooseFolder.zClickButton(Button.B_OK);
		app.zPageBriefcase.zClickAt("css=div[id=ChooseFolderDialog_button2]","0,0");

		// click on sub-folder1 in tree view
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, subFolders[0], false);

		// Verify document is no longer in the sub-folder1
		ZAssert.assertFalse(app.zPageBriefcase.isPresentInListView(fileItem
				.getName()), "Verify document is no longer in the folder: "
				+ subFolders[0].getName());

		SleepUtil.sleepVerySmall();
		// click on sub-folder2 in tree view
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, subFolders[1], true);

		// Verify document was moved to sub-folder2
		ZAssert.assertTrue(app.zPageBriefcase.isPresentInListView(fileItem
				.getName()), "Verify document was moved to the folder: "
				+ subFolders[1].getName());
	}

	@Test(description = "Upload file through RestUtil - move using Right Click Context Menu & verify through GUI", groups = { "functional" })
	public void MoveFile_03() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem folderItem = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		String briefcaseFolderId = folderItem.getId();

		String name = "subFolder" + ZimbraSeleniumProperties.getUniqueString();

		// Create a subfolder to move the message into i.e. Briefcase/subfolder
		account.soapSend("<CreateFolderRequest xmlns='urn:zimbraMail'>"
				+ "<folder name='" + name + "' l='" + briefcaseFolderId + "'/>"
				+ "</CreateFolderRequest>");

		FolderItem subFolderItem = FolderItem.importFromSOAP(account, name);

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, folderItem, true);

		// Click on created subfolder
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, subFolderItem);

		// Create file item
		String filePath = ZimbraSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/putty.log";

		FileItem fileItem = new FileItem(filePath);

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend(

		"<SaveDocumentRequest xmlns='urn:zimbraMail'>" +

		"<doc l='" + folderItem.getId() + "'>" +

		"<upload id='" + attachmentId + "'/>" +

		"</doc>" +

		"</SaveDocumentRequest>");

		// account.soapSelectNode("//mail:SaveDocumentResponse", 1);

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, folderItem, true);

		SleepUtil.sleepVerySmall();
		
		if(ZimbraSeleniumProperties.zimbraGetVersionString().contains(
    			"FOSS")){
		    app.zPageBriefcase.zListItem(Action.A_BRIEFCASE_CHECKBOX, fileItem);

		}else{
		    app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);
		}
		
		// Move using Right Click Context Menu 
		DialogMove chooseFolder = (DialogMove) app.zPageBriefcase
		.zListItem(Action.A_RIGHTCLICK, Button.O_MOVE, fileItem);

		// Click OK on Confirmation dialog
		chooseFolder.zClickTreeFolder(subFolderItem);
		chooseFolder.zClickButton(Button.B_OK);

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, folderItem, false);

		// Verify document was moved from the folder
		ZAssert.assertFalse(app.zPageBriefcase.isPresentInListView(fileItem
				.getName()), "Verify document was moved from the folder");

		// click on subfolder in tree view
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, subFolderItem, true);

		// Verify document was moved to the selected folder
		boolean present = app.zPageBriefcase.isPresentInListView(fileItem
				.getName());

		ZAssert.assertTrue(present,
				"Verify document was moved to the selected folder");
	}

	
	@AfterMethod(groups = { "always" })
	public void afterMethod() throws HarnessException {
		logger.info("Checking for the Move Dialog ...");

		// Check if the "Move Dialog is still open
		DialogMove dialog = new DialogMove(app,
				((AppAjaxClient) app).zPageBriefcase);
		if (dialog.zIsActive()) {
			logger.warn(dialog.myPageName()
					+ " was still active.  Cancelling ...");
			dialog.zClickButton(Button.B_CANCEL);
		}

	}
}
