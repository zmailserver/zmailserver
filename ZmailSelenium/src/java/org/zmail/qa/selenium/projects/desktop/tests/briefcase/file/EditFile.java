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

import java.util.EnumMap;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.FileItem;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.IItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.HtmlElement;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.briefcase.PageBriefcase;

public class EditFile extends AjaxCommonTest {

	public EditFile() {
		logger.info("New " + EditFile.class.getCanonicalName());

		super.startingPage = app.zPageBriefcase;

		super.startingAccountPreferences = null;
	}

	@Test(description = "Upload file through RestUtil - edit name & verify through GUI", groups = { "smoke" })
	public void EditFile_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/putty.log";

		IItem fileItem = new FileItem(filePath);

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend(

		"<SaveDocumentRequest xmlns='urn:zmailMail'>" +

		"<doc l='" + briefcaseFolder.getId() + "'>" +

		"<upload id='" + attachmentId + "'/>" +

		"</doc>" +

		"</SaveDocumentRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on created document
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);

		// Right click on document, select Rename
		app.zPageBriefcase.zListItem(Action.A_RIGHTCLICK, Button.B_RENAME,
				fileItem);

		String fileName2 = "renameFile"
				+ ZmailSeleniumProperties.getUniqueString();

		app.zPageBriefcase.rename(fileName2);

		// Verify document name through GUI
		ZAssert.assertTrue(app.zPageBriefcase
				.waitForPresentInListView(fileName2),
				"Verify new file name through GUI");
	}

	@Test(description = "Upload file, edit name - verify the content remains the same", groups = { "functional" })
	public void EditFile_02() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/putty.log";

		FileItem fileItem = new FileItem(filePath);

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+ "<doc l='" + briefcaseFolder.getId() + "'><upload id='"
				+ attachmentId + "'/>" + "</doc></SaveDocumentRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Retrieve file text through RestUtil
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("fmt", PageBriefcase.Response.Format.NATIVE.getFormat());

		String text = app.zPageBriefcase.displayFile(fileItem.getName(), hm)
				.get(PageBriefcase.Response.ResponsePart.BODY);

		// Right click on document, select Rename
		app.zPageBriefcase.zListItem(Action.A_RIGHTCLICK, Button.B_RENAME,
				fileItem);

		String fileName2 = "renameFile"
				+ ZmailSeleniumProperties.getUniqueString();

		app.zPageBriefcase.rename(fileName2);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // Verify document name through GUI
		ZAssert.assertTrue(app.zPageBriefcase
				.waitForPresentInListView(fileName2),
				"Verify new file name through GUI");

		// Display file through RestUtil
		EnumMap<PageBriefcase.Response.ResponsePart, String> response = app.zPageBriefcase
				.displayFile(fileName2, hm);

		HtmlElement element = HtmlElement.clean(response
				.get(PageBriefcase.Response.ResponsePart.BODY));
		HtmlElement.evaluate(element, "//body", null, Pattern.compile(".*"
				+ text + ".*"), 1);

		ZAssert.assertEquals(response
				.get(PageBriefcase.Response.ResponsePart.BODY), text,
				"Verify document content through GUI");
	}

	@Test(description = "Upload file through RestUtil - Verify 'Edit' toolbar button is disabled", groups = { "functional" })
	public void EditFile_03() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/putty.log";

		IItem fileItem = new FileItem(filePath);

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend(

		"<SaveDocumentRequest xmlns='urn:zmailMail'>" +

		"<doc l='" + briefcaseFolder.getId() + "'>" +

		"<upload id='" + attachmentId + "'/>" +

		"</doc>" +

		"</SaveDocumentRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on created document
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);

		// Verify 'Edit' tool-bar button is disabled
		ZAssert.assertTrue(app.zPageBriefcase
				.isOptionDisabled(PageBriefcase.Locators.zEditFileIconBtn),
				"Verify 'Edit' toolbar button is disabled");

		// delete file upon test completion
		app.zPageBriefcase.deleteFileByName(fileItem.getName());

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);
	}

	@Test(description = "Upload file through RestUtil - Verify 'Edit' context menu is disabled", groups = { "functional" })
	public void EditFile_04() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/putty.log";

		IItem fileItem = new FileItem(filePath);

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend(

		"<SaveDocumentRequest xmlns='urn:zmailMail'>" +

		"<doc l='" + briefcaseFolder.getId() + "'>" +

		"<upload id='" + attachmentId + "'/>" +

		"</doc>" +

		"</SaveDocumentRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Right Click on created document
		app.zPageBriefcase.zListItem(Action.A_RIGHTCLICK, fileItem);

		// Verify 'Edit' context menu is disabled
		ZAssert.assertTrue(app.zPageBriefcase
				.isOptionDisabled(PageBriefcase.Locators.zEditFileMenuItem),
				"Verify 'Edit' context menu is disabled");

		// delete file upon test completion
		app.zPageBriefcase.deleteFileByName(fileItem.getName());

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);
	}

	@AfterMethod(alwaysRun=true)
   public void editFileAfterMethod() throws HarnessException {
	// This step is necessary because next test may be uploading the same
      // file
      // if account is not reset, ZCS will be confused, and the next
      // uploaded file
      // will be deleted per previous command.
      ZmailAccount.ResetAccountZDC();
   }
}
