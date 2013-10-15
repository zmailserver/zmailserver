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
package org.zmail.qa.selenium.projects.octopus.tests.login;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;
import org.zmail.qa.selenium.projects.octopus.ui.PageLogin;
import org.zmail.qa.selenium.projects.octopus.ui.PageOctopus.Locators;

public class BasicLogin extends OctopusCommonTest {

	public BasicLogin() {
		logger.info("New " + BasicLogin.class.getCanonicalName());

		// All tests start at the Octopus page
		super.startingPage = app.zPageOctopus;
		super.startingAccountPreferences = null;
	}

	@Test(description = "Login to the Octopus client", groups = { "functional" })
	public void BasicLogin01() throws HarnessException {
		// Login
		if (!startingPage.zIsActive())
			app.zPageLogin.zLogin(ZmailAccount.AccountZMC());

		app.zPageOctopus.zWaitForActive();

		// Verify main page becomes active
		ZAssert.assertTrue(app.zPageOctopus.zIsActive(),
		"Verify that the account is logged in");
	}

	@Test(description = "Upon Login to the Octopus client verify tabs are present", groups = { "sanity" })
	public void BasicLogin02() throws HarnessException {
		// Login
		if (!startingPage.zIsActive())
			app.zPageLogin.zLogin(ZmailAccount.AccountZMC());

		app.zPageOctopus.zWaitForActive();

		// Verify My Files tab is present
		ZAssert.assertTrue(app.zPageOctopus
				.sIsElementPresent(Locators.zTabMyFiles.locator),
		"Verify My Files tab is present");

		// Verify Sharing tab is present
		ZAssert.assertTrue(app.zPageOctopus
				.sIsElementPresent(Locators.zTabSharing.locator),
		"Verify Sharing tab is present");

		// Verify Favorites tab is present
		ZAssert.assertTrue(app.zPageOctopus
				.sIsElementPresent(Locators.zTabFavorites.locator),
		"Verify Favorites tab is present");

		// Verify History tab is present
		ZAssert.assertTrue(app.zPageOctopus
				.sIsElementPresent(Locators.zTabHistory.locator),
		"Verify History tab is present");

		// Verify Trash tab is present
		ZAssert.assertTrue(app.zPageOctopus
				.sIsElementPresent(Locators.zTabTrash.locator),
		"Verify Trash tab is present");

		// Verify Search tab is present
		ZAssert.assertTrue(app.zPageOctopus
				.sIsElementPresent(Locators.zTabSearch.locator),
		"Verify Search tab is present");

	}
	
	@Test(description="Verify if Error is displayed when incorrect password is entered",groups={"smoke"})
	public void IncorrectPassword()throws HarnessException
	{
		//Create an account
		ZmailAccount a = ZmailAccount.AccountZMC();
		
		//Logout current account
		app.zPageOctopus.zLogout();
	
		//Enter correct email address
		app.zPageLogin.zSetLoginName(a.EmailAddress);
		
		//Enter incorrect password.
		app.zPageLogin.zSetLoginPassword("Incorrect");
		
		//Click login button
		app.zPageLogin.sClick(PageLogin.Locators.zBtnLogin);
		
		//Check if error panel is present or not.
		Boolean isErrorPresent=app.zPageLogin.sIsElementPresent(PageLogin.Locators.zLoginErrorPanel);
	   
		//Assert result
		ZAssert.assertTrue(isErrorPresent, "Check if error message displayed when incorrect password is entered.");
	}
	
	@Test(description="Verify if Error is displayed when incorrect Username is entered",groups={"smoke"})
	public void IncorrectUsername()throws HarnessException
	{
		//Logout current account
		app.zPageOctopus.zLogout();
	
		//Enter incorrect email address
		app.zPageLogin.zSetLoginName("EmailAddress");
		
		//Enter incorrect password.
		app.zPageLogin.zSetLoginPassword("test123");
		
		//Click login button
		app.zPageLogin.sClick(PageLogin.Locators.zBtnLogin);
		
		//Check if error panel is present or not.
		Boolean isErrorPresent=app.zPageLogin.sIsElementPresent(PageLogin.Locators.zLoginErrorPanel);
	   
		//Assert result
		ZAssert.assertTrue(isErrorPresent, "Check if error message displayed when incorrect Username is entered.");
	}
}
