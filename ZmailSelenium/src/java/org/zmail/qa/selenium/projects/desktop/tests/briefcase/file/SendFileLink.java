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

import java.util.HashMap;

import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.FileItem;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.briefcase.DialogConfirm;
import org.zmail.qa.selenium.projects.desktop.ui.DialogWarning;
import org.zmail.qa.selenium.projects.desktop.ui.mail.FormMailNew;

public class SendFileLink extends AjaxCommonTest {

	@SuppressWarnings("serial")
   public SendFileLink() {
		logger.info("New " + SendFileLink.class.getCanonicalName());

		super.startingPage = app.zPageBriefcase;

		super.startingAccountPreferences = new HashMap<String , String>() {{
         put("zmailPrefComposeFormat", "html");
     }};
	}

	@Test(description = "Upload file through RestUtil - click Send Link, Cancel & verify through GUI", groups = { "functional" })
	public void SendFileLink_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
		+ "/data/public/other/testexcelfile.xls";
		
		FileItem fileItem = new FileItem(filePath);

		String fileName = fileItem.getName();

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+ "<doc l='" + briefcaseFolder.getId() + "'><upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on created document
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);

		// Click on Send Link
		DialogConfirm confDlg = (DialogConfirm) app.zPageBriefcase
				.zToolbarPressPulldown(Button.B_SEND, Button.O_SEND_LINK);
		
		// Click Yes on confirmation dialog
		FormMailNew mailform = (FormMailNew) confDlg.zClickButton(Button.B_YES);

		// Verify the new mail form is opened
		ZAssert.assertTrue(mailform.zIsActive(), "Verify the new form opened");
		
		// Verify link
		ZAssert.assertTrue(mailform.zWaitForIframeText(
				FormMailNew.Locators.zLinkText, fileName),
				"Verify the link text");

      // Cancel the message
      // A warning dialog should appear regarding losing changes
      DialogWarning warningDlg = (DialogWarning) mailform
            .zToolbarPressButton(Button.B_CANCEL);

		// temporary: check if dialog exists since it was implemented recently
      // on send link
      if (warningDlg.zIsActive()) {
         // Dismiss the dialog
         warningDlg.zClickButton(Button.B_NO);

         // Make sure the dialog is dismissed
         warningDlg.zWaitForClose();
      }

      // delete file upon test completion
      app.zPageBriefcase.deleteFileByName(fileItem.getName());
      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);
	}

	@Test(description = "Send File link using Right Click Context Menu & verify through GUI", groups = { "functional" })
	public void SendFileLink_02() throws HarnessException {
	   ZmailAccount account = app.zGetActiveAccount();

	   FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
	         SystemFolder.Briefcase);

	   // Create file item
	   String filePath = ZmailSeleniumProperties.getBaseDirectory()
	         + "/data/public/other/testexcelfile.xls";

	   FileItem fileItem = new FileItem(filePath);

	   String fileName = fileItem.getName();

	   // Upload file to server through RestUtil
	   String attachmentId = account.uploadFile(filePath);

	   // Save uploaded file to briefcase through SOAP
	   account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
	         + "<doc l='" + briefcaseFolder.getId() + "'><upload id='"
	         + attachmentId + "'/></doc></SaveDocumentRequest>");

	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // refresh briefcase page
	   app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

	   SleepUtil.sleepVerySmall();

	   // Click on uploaded file
	   app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);

	   // Click on Send Link using Right Click Context Menu
	   DialogConfirm confDlg = (DialogConfirm) app.zPageBriefcase.zListItem(
	         Action.A_RIGHTCLICK, Button.O_SEND_LINK, fileItem);

	   // Click Yes on confirmation dialog
	   FormMailNew mailform = (FormMailNew) confDlg.zClickButton(Button.B_YES);

	   // Verify the new mail form is opened
	   ZAssert.assertTrue(mailform.zIsActive(), "Verify the new form opened");

	   // Verify link
	   ZAssert.assertTrue(mailform.zWaitForIframeText(
	         FormMailNew.Locators.zLinkText, fileName),
	         "Verify the link text");

	   // Cancel the message
	   // A warning dialog should appear regarding losing changes
	   DialogWarning warningDlg = (DialogWarning) mailform
	         .zToolbarPressButton(Button.B_CANCEL);

	   // temporary: check if dialog exists since it was implemented recently
	   // on send link
	   if (warningDlg.zIsActive()) {
	      // Dismiss the dialog
	      warningDlg.zClickButton(Button.B_NO);

	      // Make sure the dialog is dismissed
	      warningDlg.zWaitForClose();
	   }

	   // delete file upon test completion
	   app.zPageBriefcase.deleteFileByName(fileItem.getName());
	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);
	}

}
