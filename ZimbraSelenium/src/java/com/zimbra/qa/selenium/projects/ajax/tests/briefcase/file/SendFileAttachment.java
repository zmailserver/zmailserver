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

import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.items.FileItem;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.FeatureBriefcaseTest;
import com.zimbra.qa.selenium.projects.ajax.ui.DialogWarning;
import com.zimbra.qa.selenium.projects.ajax.ui.briefcase.PageBriefcase;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.FormMailNew;

public class SendFileAttachment extends FeatureBriefcaseTest {

	public SendFileAttachment() throws HarnessException {
		logger.info("New " + SendFileAttachment.class.getCanonicalName());

		super.startingPage = app.zPageBriefcase;

		if(ZimbraSeleniumProperties.zimbraGetVersionString().contains("FOSS")){
		    super.startingAccountPreferences.put("zimbraPrefShowSelectionCheckbox","TRUE");
		}
		
		super.startingAccountPreferences.put("zimbraPrefBriefcaseReadingPaneLocation", "bottom");				
	}

	@Test(description = "Upload file through RestUtil - click Send as attachment, Cancel & verify through GUI", groups = { "functional" })
	public void SendFileAttachment_01() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZimbraSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/structure.jpg";

		FileItem fileItem = new FileItem(filePath);

		String fileName = fileItem.getName();

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zimbraMail'>"
				+ "<doc l='" + briefcaseFolder.getId() + "'><upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		SleepUtil.sleepVerySmall();

		// Click on uploaded file
		if(ZimbraSeleniumProperties.zimbraGetVersionString().contains(
    			"FOSS")){
		    app.zPageBriefcase.zListItem(Action.A_BRIEFCASE_CHECKBOX, fileItem);

		}else{
		    app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);
		}
		
		// Click on Send as attachment
		FormMailNew mailform;
		if (ZimbraSeleniumProperties.zimbraGetVersionString().contains("7.1."))
			mailform = (FormMailNew) app.zPageBriefcase.zToolbarPressPulldown(
					Button.B_SEND, Button.O_SEND_AS_ATTACHMENT, fileItem);
		else
			mailform = (FormMailNew) app.zPageBriefcase.zToolbarPressPulldown(
					Button.B_ACTIONS, Button.O_SEND_AS_ATTACHMENT, fileItem);

		// Verify the new mail form has attachment
		ZAssert.assertTrue(app.zPageBriefcase
				.sIsElementPresent(PageBriefcase.Locators.zAttachmentText.locator + ":contains("
						+ fileName + ")"), "Verify the attachment text");

		// Cancel the message
		// A warning dialog should appear regarding losing changes
		DialogWarning warningDlg = (DialogWarning) mailform
				.zToolbarPressButton(Button.B_CANCEL);

		ZAssert.assertNotNull(warningDlg, "Verify the dialog is returned");

		// Dismiss the dialog
		warningDlg.zClickButton(Button.B_NO);

		warningDlg.zWaitForClose(); // Make sure the dialog is dismissed

		// delete file upon test completion
		app.zPageBriefcase.deleteFileByName(fileItem.getName());
	}

	@Test(description = "Send File as attachment using Right Click Context Menu & verify through GUI", groups = { "functional" })
	public void SendFileAttachment_02() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZimbraSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/structure.jpg";

		FileItem fileItem = new FileItem(filePath);

		String fileName = fileItem.getName();

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zimbraMail'>"
				+ "<doc l='" + briefcaseFolder.getId() + "'><upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		// SleepUtil.sleepVerySmall();

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		SleepUtil.sleepVerySmall();

		// Click on uploaded file
		if(ZimbraSeleniumProperties.zimbraGetVersionString().contains(
    			"FOSS")){
		    app.zPageBriefcase.zListItem(Action.A_BRIEFCASE_CHECKBOX, fileItem);

		}else{
		    app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);
		}
		
		// Click on Send as attachment using Right Click Context Menu
		FormMailNew mailform = (FormMailNew) app.zPageBriefcase.zListItem(
				Action.A_RIGHTCLICK, Button.O_SEND_AS_ATTACHMENT, fileItem);

		// Verify the new mail form has attachment
		ZAssert.assertTrue(app.zPageBriefcase
				.zWaitForElementPresent(PageBriefcase.Locators.zAttachmentText.locator + ":contains("
						+ fileName + ")"), "Verify the attachment text");

		// Cancel the message
		// A warning dialog should appear regarding losing changes
		DialogWarning warningDlg = (DialogWarning) mailform
				.zToolbarPressButton(Button.B_CANCEL);

		ZAssert.assertNotNull(warningDlg, "Verify the dialog is returned");

		// Dismiss the dialog clicking No
		// warningDlg.zClickButton(Button.B_NO);
		app.zPageBriefcase.zClick("//div[@id='YesNoCancel']//td[contains(@id,'No_')]//td[contains(@id,'_title')]");


		warningDlg.zWaitForClose(); // Make sure the dialog is dismissed

		// delete file upon test completion
		app.zPageBriefcase.deleteFileByName(fileItem.getName());
	}
}
