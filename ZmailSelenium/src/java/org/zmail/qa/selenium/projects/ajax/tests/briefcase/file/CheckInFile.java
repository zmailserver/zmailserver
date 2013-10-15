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
package org.zmail.qa.selenium.projects.ajax.tests.briefcase.file;

import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.FileItem;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.FeatureBriefcaseTest;
import org.zmail.qa.selenium.projects.ajax.ui.briefcase.DialogCheckInFile;

public class CheckInFile extends FeatureBriefcaseTest {

	public CheckInFile() {
		logger.info("New " + CheckInFile.class.getCanonicalName());

		super.startingPage = app.zPageBriefcase;

		super.startingAccountPreferences.put("zmailPrefBriefcaseReadingPaneLocation", "bottom");
	}

	@Test(description = "Check Out File through SOAP - right click 'Check In' - click 'Cancel'", groups = { "functional" })
	public void CheckInFile_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
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
				+ "<doc l='" + briefcaseFolder.getId() + "'>" + "<upload id='"
				+ attachmentId + "'/>" + "</doc>" + "</SaveDocumentRequest>");

		// account.soapSelectNode("//mail:SaveDocumentResponse", 1);

		// search the uploaded file
		account
				.soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
						+ "<query>"
						+ fileName
						+ "</query>"
						+ "</SearchRequest>");

		// Verify file name through SOAP
		String name = account.soapSelectValue("//mail:doc", "name");
		ZAssert.assertEquals(name, fileName, "Verify file name through SOAP");

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		SleepUtil.sleepVerySmall();
		
		//Verify Lock icon is not present for the uploaded file
		ZAssert.assertFalse(app.zPageBriefcase.isLockIconPresent(file),"Verify Lock icon is not present for the uploaded file");
		
		// Check Out file through SOAP
		String id = account.soapSelectValue("//mail:doc", "id");
		account.soapSend("<ItemActionRequest xmlns='urn:zmailMail'>"
				+ "<action id='" + id + "' op='lock'/>"
				+ "</ItemActionRequest>");

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		SleepUtil.sleepVerySmall();

		//Verify Lock icon is present for the checked out file
		ZAssert.assertTrue(app.zPageBriefcase.isLockIconPresent(file),"Verify Lock icon is present for the checked out file");

		// Right click on the locked file and select Check In File context menu
		DialogCheckInFile dlg = (DialogCheckInFile) app.zPageBriefcase
				.zListItem(Action.A_RIGHTCLICK, Button.O_CHECK_IN_FILE, file);

		// Verify the 'Check In File to Briefcase' dialog is displayed
		ZAssert.assertTrue(dlg.zIsActive(),
				"Verify the 'Check In File to Briefcase' dialog is displayed");

		// Dismiss dialog by clicking on Cancel button
		dlg.zClickButton(Button.B_CANCEL);

		// delete file upon test completion
		app.zPageBriefcase.deleteFileById(id);
	}
	
	@Test(description = "Check Out File through SOAP - right click 'Discard Check Out'", groups = { "functional" })
	public void CheckInFile_02() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
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
				+ "<doc l='" + briefcaseFolder.getId() + "'>" + "<upload id='"
				+ attachmentId + "'/>" + "</doc>" + "</SaveDocumentRequest>");

		// search the uploaded file
		account
				.soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
						+ "<query>"
						+ fileName
						+ "</query>"
						+ "</SearchRequest>");

		// Verify file name through SOAP
		String name = account.soapSelectValue("//mail:doc", "name");
		ZAssert.assertEquals(name, fileName, "Verify file name through SOAP");

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		SleepUtil.sleepVerySmall();
		
		//Verify Lock icon is not present for the uploaded file
		ZAssert.assertFalse(app.zPageBriefcase.isLockIconPresent(file),"Verify Lock icon is not present for the uploaded file");
		
		// Check Out file through SOAP
		String id = account.soapSelectValue("//mail:doc", "id");
		account.soapSend("<ItemActionRequest xmlns='urn:zmailMail'>"
				+ "<action id='" + id + "' op='lock'/>"
				+ "</ItemActionRequest>");

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		SleepUtil.sleepVerySmall();

		//Verify Lock icon is present for the checked out file
		ZAssert.assertTrue(app.zPageBriefcase.isLockIconPresent(file),"Verify Lock icon is present for the checked out file");

		// Right click on the locked file and select Discard Check Out context menu
		app.zPageBriefcase
				.zListItem(Action.A_RIGHTCLICK, Button.O_DISCARD_CHECK_OUT, file);

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		SleepUtil.sleepVerySmall();
		
		//Verify Lock icon is removed for the corresponding file
		ZAssert.assertFalse(app.zPageBriefcase.isLockIconPresent(file),"Verify Lock icon is not present for the uploaded file");
				
		// delete file upon test completion
		app.zPageBriefcase.deleteFileById(id);
	}
}
