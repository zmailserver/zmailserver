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

import junit.framework.Assert;

import mx4j.tools.config.DefaultConfigurationBuilder.New;

import org.testng.annotations.Test;

import org.zmail.qa.QA.Bug;
import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;
import org.zmail.qa.selenium.projects.octopus.ui.PageOctopus.Locators;

public class VerifyUserDetails extends OctopusCommonTest{
	
	public VerifyUserDetails()
	{
logger.info("New " + VerifyUserDetails.class.getCanonicalName());
		
		// All tests start at the Octopus page
		super.startingPage = app.zPageLogin;
	}
	@Bugs(ids="70100")
	@Test(description="Verify user display name when account display name is not set", groups={"functional"})
	public void verifyUserNameDisplayed() throws HarnessException
	{
		//create new account
		ZmailAccount account = new ZmailAccount();
		//Clear Display Name
		account.clearPref("displayName");
		
		account.provision();
		
		account.authenticate();		
		//Login to account
		app.zPageLogin.zLogin(account);
		
		//Assert if Display Name field contains email address if display name is not set.
		
	    ZAssert.assertTrue(app.zPageOctopus.sIsElementPresent(Locators.zUserDisplayName.locator+":contains("+account.EmailAddress+")"),
	    		"Check if user email address is displayed in user display name field if no display name is set");
	  	    
	}

}
