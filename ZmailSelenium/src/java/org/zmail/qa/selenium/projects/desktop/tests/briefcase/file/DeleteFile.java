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
package org.zmail.qa.selenium.projects.desktop.tests.briefcase.file;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.FileItem;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.ui.Shortcut;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.Toaster;
import org.zmail.qa.selenium.projects.desktop.ui.briefcase.DialogConfirm;

public class DeleteFile extends AjaxCommonTest {

	public DeleteFile() {
		logger.info("New " + DeleteFile.class.getCanonicalName());

		super.startingPage = app.zPageBriefcase;

	}

	@Test(description = "Upload file through RestUtil - delete & verify through GUI", groups = { "smoke" })
	public void DeleteFile_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/putty.log";

		FileItem fileItem = new FileItem(filePath);

		String fileName = fileItem.getName();

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend(

		"<SaveDocumentRequest xmlns='urn:zmailMail'>" +

		"<doc l='" + briefcaseFolder.getId() + "'>" +

		"<upload id='" + attachmentId + "'/>" +

		"</doc>" +

		"</SaveDocumentRequest>");

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on created document
		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);

		// Click on Delete document icon in toolbar
		DialogConfirm deleteConfirm = (DialogConfirm) app.zPageBriefcase
				.zToolbarPressButton(Button.B_DELETE, fileItem);

		// Click OK on Confirmation dialog
		deleteConfirm.zClickButton(Button.B_YES);

		Toaster toast = app.zPageMain.zGetToaster();
		String toastMsg = toast.zGetToastMessage();
		ZAssert.assertStringContains(toastMsg, "1 file moved to Trash",
		      "Verify toast message" );

	   // refresh briefcase page
		app.zTreeBriefcase
				.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, false);

		// Verify document was deleted
		ZAssert.assertFalse(app.zPageBriefcase.isPresentInListView(fileName),
				"Verify document was deleted through GUI");
	}

	@Test(description = "Upload file through RestUtil - delete using Delete Key & check trash", groups = { "functional" })
	public void DeleteFile_02() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		FolderItem trashFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Trash);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/putty.log";

		FileItem fileItem = new FileItem(filePath);

		String fileName = fileItem.getName();
		
		Shortcut shortcut = Shortcut.S_DELETE;

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'><doc l='"
				+ briefcaseFolder.getId() + "'>" + "<upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		String docId = account.soapSelectValue(
				"//mail:SaveDocumentResponse//mail:doc", "id");

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on created document
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);

		// Click the Delete keyboard shortcut
		DialogConfirm deleteConfirm = (DialogConfirm) app.zPageBriefcase
				.zKeyboardShortcut(shortcut);

		// Click OK on Confirmation dialog
		deleteConfirm.zClickButton(Button.B_YES);

	   Toaster toast = app.zPageMain.zGetToaster();
	   String toastMsg = toast.zGetToastMessage();
	   ZAssert.assertStringContains(toastMsg, "1 file moved to Trash",
	         "Verify toast message" );

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

		// refresh briefcase page
		app.zTreeBriefcase
				.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, false);

		// Verify file was deleted from the list
		ZAssert.assertFalse(app.zPageBriefcase.isPresentInListView(fileName),
				"Verify file was deleted through GUI");
		
		// Verify document moved to Trash
		account
				.soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
						+ "<query>in:"
						+ trashFolder.getName()
						+ " "
						+ fileName
						+ "</query>" + "</SearchRequest>");

		String id = account.soapSelectValue("//mail:SearchResponse//mail:doc",
				"id");
		
		ZAssert.assertEquals(id, docId,
				"Verify the file was moved to the trash folder: "
				+ fileName + " id: " + id);		
	}
	
	@Test(description = "Upload file through RestUtil - delete using <Backspace> Key & check trash", groups = { "functional" })
	public void DeleteFile_03() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		FolderItem trashFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Trash);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/putty.log";

		FileItem fileItem = new FileItem(filePath);

		String fileName = fileItem.getName();
		
		Shortcut shortcut = Shortcut.S_BACKSPACE;

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'><doc l='"
				+ briefcaseFolder.getId() + "'>" + "<upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		String docId = account.soapSelectValue(
				"//mail:SaveDocumentResponse//mail:doc", "id");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on created document
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);

		// Click the Backspace keyboard shortcut
		DialogConfirm deleteConfirm = (DialogConfirm) app.zPageBriefcase
				.zKeyboardShortcut(shortcut);

		// Click OK on Confirmation dialog
		deleteConfirm.zClickButton(Button.B_YES);

      Toaster toast = app.zPageMain.zGetToaster();
      String toastMsg = toast.zGetToastMessage();
      ZAssert.assertStringContains(toastMsg, "1 file moved to Trash",
            "Verify toast message" );

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // refresh briefcase page
		app.zTreeBriefcase
				.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, false);

		// Verify file was deleted from the list
		ZAssert.assertFalse(app.zPageBriefcase.isPresentInListView(fileName),
				"Verify file was deleted through GUI");
		
		// Verify document moved to Trash
		account
				.soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
						+ "<query>in:"
						+ trashFolder.getName()
						+ " "
						+ fileName
						+ "</query>" + "</SearchRequest>");

		String id = account.soapSelectValue("//mail:SearchResponse//mail:doc",
				"id");
		
		ZAssert.assertEquals(id, docId,
				"Verify the file was moved to the trash folder: "
				+ fileName + " id: " + id);		
	}

	  @Test(description = "Upload file through RestUtil - delete using Right Click context menu", groups = { "functional" })
	  public void DeleteFile_04() throws HarnessException {
	     ZmailAccount account = app.zGetActiveAccount();

	     FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
	           SystemFolder.Briefcase);

	     // Create file item
	     String filePath = ZmailSeleniumProperties.getBaseDirectory()
	           + "/data/public/other/putty.log";

	     FileItem fileItem = new FileItem(filePath);

	     String fileName = fileItem.getName();

	     // Upload file to server through RestUtil
	     String attachmentId = account.uploadFile(filePath);

	     // Save uploaded file to briefcase through SOAP
	     account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'><doc l='"
	           + briefcaseFolder.getId() + "'>" + "<upload id='"
	           + attachmentId + "'/></doc></SaveDocumentRequest>");

	     // String docId = account.soapSelectValue("//mail:SaveDocumentResponse//mail:doc", "id");

	     // refresh briefcase page
	     app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

	     SleepUtil.sleepVerySmall();

	     // Click on created file
	     GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
	     app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);

	     // Delete File using Right Click Context Menu
	     DialogConfirm deleteConfirm = (DialogConfirm) app.zPageBriefcase
	           .zListItem(Action.A_RIGHTCLICK, Button.O_DELETE, fileItem);

	     // Click OK on Confirmation dialog
	     deleteConfirm.zClickButton(Button.B_YES);

	     Toaster toast = app.zPageMain.zGetToaster();
	     String toastMsg = toast.zGetToastMessage();
	     ZAssert.assertStringContains(toastMsg, "1 file moved to Trash",
	           "Verify toast message" );

	     // refresh briefcase page
	     app.zTreeBriefcase
	           .zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, false);

	     // Verify file was deleted from the list
	     ZAssert.assertFalse(app.zPageBriefcase.isPresentInListView(fileName),
	           "Verify file was deleted through GUI");      
	  }

	  @AfterMethod(alwaysRun=true)
	  public void deleteFileAfterMethod() throws HarnessException {
	     // This step is necessary because next test may be uploading the same
	     // file
	     // if account is not reset, ZCS will be confused, and the next
	     // uploaded file
	     // will be deleted per previous command.
	     ZmailAccount.ResetAccountZDC();
	  }
}
