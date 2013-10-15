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
package org.zmail.qa.selenium.projects.octopus.tests.login.performance;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.ui.PageLogin.Locators;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;

public class ZmMyFilesApp extends OctopusCommonTest {

	public ZmMyFilesApp() {
		logger.info("New " + ZmMyFilesApp.class.getCanonicalName());

		// test starts at the My Files tab
		super.startingPage = app.zPageLogin;
		super.startingAccountPreferences = null;
	}


	@Test(	description = "Measure the time to load the ajax client",
			groups = { "performance" })
	public void ZmMyFilesApp_01() throws HarnessException, IOException {
		
		/**
		 * Load all the sample files into the account
		 */
		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(ZmailAccount.AccountZWC(), SystemFolder.Briefcase);
		String filepath = ZmailSeleniumProperties.getBaseDirectory() + "/data/public/Files/Basic01";
		
		File directory = new File(filepath);
		ZAssert.assertTrue(directory.exists(), "Verify the sample files exist");
		
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; i++) {
			
			String aid = ZmailAccount.AccountZWC().uploadFile(files[i].getCanonicalPath());
			
			ZmailAccount.AccountZWC().soapSend(
					"<SaveDocumentRequest xmlns='urn:zmailMail'>" +
						"<doc l='" + briefcaseRootFolder.getId() + "'>" +
							"<upload id='" + aid + "'/>" +
						"</doc>" +
					"</SaveDocumentRequest>");

		}
		

		app.zPageLogin.zNavigateTo();

		app.zPageLogin.zSetLoginName(ZmailAccount.AccountZWC().EmailAddress);
		app.zPageLogin.zSetLoginPassword(ZmailAccount.AccountZWC().Password);

		// PerfToken token = PerfMetrics.startTimestamp(PerfKey.ZmMailApp, "Login to the ajax client (mail app)");

		// Click the Login button
		app.zPageLogin.sClick(Locators.zBtnLogin);

		// PerfMetrics.waitTimestamp(token);
				
		// Wait for the app to load
		app.zPageOctopus.zWaitForActive();

		
		// Add perf checks here
		throw new HarnessException("Implement perf verification here.  See http://bugzilla.zmail.com/show_bug.cgi?id=65989");
	}
	
	@Test(	description = "Measure the time to load the ajax client with 100 folders",
		groups = { "performance" })
	public void ZmMyFilesApp_02() throws HarnessException {
			
			FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(ZmailAccount.AccountZWC(), SystemFolder.Briefcase);
			ZAssert.assertNotNull(briefcaseRootFolder, "Verify the Briefcase root folder is available");


			// Create 100 sub-folders

			String foldername = null;
			
			for (int i = 0; i < 100; i++) {
				
				foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
				
				ZmailAccount.AccountZWC().soapSend(
						"<CreateFolderRequest xmlns='urn:zmailMail'>" +
							"<folder name='" + foldername + "' l='" + briefcaseRootFolder.getId() + "' view='document'/>" +
						"</CreateFolderRequest>");
			}
			
			FolderItem folder = FolderItem.importFromSOAP(ZmailAccount.AccountZWC(), foldername);
			ZAssert.assertNotNull(folder, "Verify the subfolder is available");

			
			
			app.zPageLogin.zNavigateTo();

			app.zPageLogin.zSetLoginName(ZmailAccount.AccountZWC().EmailAddress);
			app.zPageLogin.zSetLoginPassword(ZmailAccount.AccountZWC().Password);

			// PerfToken token = PerfMetrics.startTimestamp(PerfKey.ZmMailApp, "Login to the ajax client (mail app)");

			// Click the Login button
			app.zPageLogin.sClick(Locators.zBtnLogin);

			// PerfMetrics.waitTimestamp(token);
					
			// Wait for the app to load
			app.zPageOctopus.zWaitForActive();

			
			// Add perf checks here
			throw new HarnessException("Implement perf verification here.  See http://bugzilla.zmail.com/show_bug.cgi?id=65989");
			
			
	}
	
}

	
