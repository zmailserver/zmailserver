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
package com.zimbra.qa.selenium.projects.ajax.tests.login;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.core.Bugs;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.ajax.ui.PageLogin;



public class LoginScreen extends AjaxCommonTest {

	public LoginScreen() {
		logger.info("New "+ LoginScreen.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageLogin;
		super.startingAccountPreferences = null;

	}

	@Test(	description = "Verify the label text on the ajax client login screen",
			groups = { "smoke" })
	public void LoginScreen01() throws HarnessException {
		
		String username = app.zPageLogin.sGetText(PageLogin.Locators.zDisplayedusername);
		ZAssert.assertEquals(username, app.zGetLocaleString("usernameLabel"), "Verify the displayed label 'username'");
		
		// TODO: add other displayed text

	}
	
	
	@Test(	description = "Verify the copyright on the login screen contains the current year",
			groups = { "functional" })
	public void LoginScreen02() throws HarnessException {
		
		Calendar calendar = new GregorianCalendar();
		String thisYear = "" + calendar.get(Calendar.YEAR);
		
		String copyright = app.zPageLogin.sGetText(PageLogin.Locators.zDisplayedcopyright);
		
		String message = String.format("Verify the copyright (%s) on the login screen contains the current year (%s)", copyright, thisYear);
		ZAssert.assertStringContains(copyright, thisYear, message);
		

	}

	@Test(	description = "Verify initial focus on the login screen should be in username",
			groups = { "functional" })
	public void LoginScreen03() throws HarnessException {
		
		// Get to the login screen
		// TODO: probably need to watch out for previously typed text
		// TODO: probably need to watch out for browser cache
		// TODO: maybe it is better just to reload the URL?
		app.zPageLogin.zNavigateTo();
		
		// Type a unique string into the browser
		String value = "foo" + ZimbraSeleniumProperties.getUniqueString();
		app.zPageLogin.zKeyboardTypeString(value);
		
		// Get the value of the username field
		String actual = app.zPageLogin.sGetValue(PageLogin.Locators.zInputUsername);
		
		// Verify typed text and the actual text match
		ZAssert.assertEquals(actual, value, "Verify the username has initial focus");
		
	}

	@Test(	description = "Verify tab order in the login screen (username, password, enter)",
			groups = { "unstable" })
	public void LoginScreen04() throws HarnessException {
		
		throw new HarnessException("implement me!");
		
	}


	@Bugs(ids = "50457")
	@Test(	description = "Verify 'web client' rather than 'collaboration suite'",
			groups = { "functional" })
	public void LoginScreen05() throws HarnessException {
		

		String title = app.zPageLogin.sGetTitle();
		
		// TODO: Need to I18N
		ZAssert.assertStringContains(title, "Web Client", "Verify 'web client' rather than 'collaboration suite'");
		
	}

}
