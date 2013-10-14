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
package com.zimbra.qa.selenium.projects.octopus.tests.login;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.projects.octopus.core.OctopusCommonTest;
import com.zimbra.qa.selenium.projects.octopus.ui.PageOctopus;

public class BasicLogout extends OctopusCommonTest {

	public BasicLogout() {
		logger.info("New " + BasicLogout.class.getCanonicalName());
		
		// All tests start at the Octopus page
		super.startingPage = app.zPageOctopus;
	}

	@Test(description = "Logout of the Octopus Client", groups = { "sanity" })
	public void BasicLogout01() throws HarnessException {

		ZAssert.assertTrue(app.zPageOctopus
				.sIsElementPresent(PageOctopus.Locators.zUserNamePullDown.locator),
				"Verify Sign Out Button is present on the page");
		
		// Click on Sign out button
		app.zPageOctopus.zToolbarPressPulldown(Button.B_USER_NAME, Button.O_SIGN_OUT);
		
		app.zPageLogin.zWaitForActive();
		
		// Verify login page becomes active
		ZAssert.assertTrue(app.zPageLogin.zIsActive(),
				"Verify that the account is logged out");
	}	
}
