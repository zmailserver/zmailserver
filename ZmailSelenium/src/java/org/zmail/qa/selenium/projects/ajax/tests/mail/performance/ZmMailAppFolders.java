/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012, 2013 VMware, Inc.
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
package org.zmail.qa.selenium.projects.ajax.tests.mail.performance;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.performance.PerfKey;
import org.zmail.qa.selenium.framework.util.performance.PerfMetrics;
import org.zmail.qa.selenium.framework.util.performance.PerfToken;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.PageLogin.Locators;



public class ZmMailAppFolders extends AjaxCommonTest {
	
	public ZmMailAppFolders() {
		logger.info("New "+ ZmMailAppFolders.class.getCanonicalName());
		
		
		super.startingPage = app.zPageLogin;
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = 7525760124523255182L;
		{
			put("zmailPrefGroupMailBy", "message");
			put("zmailPrefMessageViewHtmlPreferred", "TRUE");
		}};
		
	}
	
	@Test(	description = "Measure the time to load the mail app, message view, 1 folder",
			groups = { "performance" })
	public void ZmMailAppFolder_01() throws HarnessException {

		// Create a folder
		FolderItem root = FolderItem.importFromSOAP(ZmailAccount.AccountZWC(), FolderItem.SystemFolder.UserRoot);
		ZmailAccount.AccountZWC().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
	                	"<folder name='folder"+ ZmailSeleniumProperties.getUniqueString() + "' view='message' l='"+ root.getId() +"'/>" +
	                "</CreateFolderRequest>");


		// Fill out the login page
		app.zPageLogin.zSetLoginName(ZmailAccount.AccountZWC().EmailAddress);
		app.zPageLogin.zSetLoginPassword(ZmailAccount.AccountZWC().Password);

		PerfToken token = PerfMetrics.startTimestamp(PerfKey.ZmMailAppOverviewPanel, "Load the mail app, message view, 1 folder");

		// Click the Login button
		app.zPageLogin.sClick(Locators.zBtnLogin);

		PerfMetrics.waitTimestamp(token);
				
		// Wait for the app to load
		app.zPageMain.zWaitForActive();
		
		
	}

	@Test(	description = "Measure the time to load the mail app, message view, 100 folders",
			groups = { "performance" })
	public void ZmMailAppFolder_02() throws HarnessException {

		// Create 100 folders
		FolderItem root = FolderItem.importFromSOAP(ZmailAccount.AccountZWC(), FolderItem.SystemFolder.UserRoot);
		for (int i = 0; i < 100; i++) {
			ZmailAccount.AccountZWC().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>" +
							"<folder name='folder"+ ZmailSeleniumProperties.getUniqueString() + "' view='message' l='"+ root.getId() +"'/>" +
					"</CreateFolderRequest>");
		}


		// Fill out the login page
		app.zPageLogin.zSetLoginName(ZmailAccount.AccountZWC().EmailAddress);
		app.zPageLogin.zSetLoginPassword(ZmailAccount.AccountZWC().Password);

		PerfToken token = PerfMetrics.startTimestamp(PerfKey.ZmMailAppOverviewPanel, "Load the mail app, message view, 100 folders");

		// Click the Login button
		app.zPageLogin.sClick(Locators.zBtnLogin);

		PerfMetrics.waitTimestamp(token);

		// Wait for the app to load
		app.zPageMain.zWaitForActive();


	}


}
