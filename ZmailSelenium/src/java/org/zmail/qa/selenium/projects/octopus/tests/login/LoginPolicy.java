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
package org.zmail.qa.selenium.projects.octopus.tests.login;

import java.util.ArrayList;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;
import org.zmail.qa.selenium.projects.octopus.ui.PageLogin;
import org.zmail.qa.selenium.projects.octopus.ui.PageLogin.Locators;

public class LoginPolicy extends OctopusCommonTest
{

	public LoginPolicy()
	{
		super.startingPage= app.zPageLogin;
		//	super.startingAccountPreferences=null;
	}

	@Test(description="Check if user is forced to change password if required preferances are set", groups={"smoke"})
	public void VerifyForceChangePassword() throws HarnessException
	{
		//Create new account
		ZmailAccount acct = new ZmailAccount();

		//Set preferences for password must change policy
		acct.setPref("zmailPasswordMustChange", "TRUE");
		acct.provision();

		//Enter login details and click login button
		app.zPageLogin.zSetLoginName(acct.EmailAddress);
		app.zPageLogin.zSetLoginPassword(acct.Password);
		app.zPageLogin.sClick(Locators.zBtnLogin);

		//Verify if new password and confirm password fields are present.
		ZAssert.assertTrue(app.zPageLogin.sIsElementPresent(PageLogin.Locators.zInputNewPassword),"Verify if input field for new password is present");
		ZAssert.assertTrue(app.zPageLogin.sIsElementPresent(PageLogin.Locators.zInputConfirmPassword),"Verify if input field for Confirm Password password is present");

	}

	@Test(description="Verify if error is displayed to user when account status is Not active.", groups={"smoke1"})
	public void verifyErrorDisplayedForInactiveAccounts()throws HarnessException
	{
		//Create array list of various account status.
		String [] accountStatus ={"closed","locked","pending","maintenance"};
		
		for (String status : accountStatus) {


			//Create new account
			ZmailAccount acct = new ZmailAccount();

			//Set account status
			acct.setPref("zmailAccountStatus", status);
			acct.provision();

			//Enter login details and click login button
			app.zPageLogin.zSetLoginName(acct.EmailAddress);
			app.zPageLogin.zSetLoginPassword(acct.Password);
			app.zPageLogin.sClick(Locators.zBtnLogin);
			
			app.zPageOctopus.zWaitForElementPresent(Locators.zLoginErrorPanel);

			boolean isErrorPresent =app.zPageLogin.sIsElementPresent(Locators.zLoginErrorPanel);
          // Verify the required condition.  
			ZAssert.assertTrue(isErrorPresent, "Verify if error message is displayed for " + status+"account");

		}
	}

}
