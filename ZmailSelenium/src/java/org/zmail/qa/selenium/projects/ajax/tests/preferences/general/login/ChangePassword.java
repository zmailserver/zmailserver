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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.general.login;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.SeparateWindowChangePassword;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;


public class ChangePassword extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public ChangePassword() {
		logger.info("New "+ ChangePassword.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPagePreferences;

		// Add a preference so the account is 'dirty' after changing password
		super.startingAccountPreferences = new HashMap<String, String>() {{
					put("zmailPrefGroupMailBy", "message");
				}};
			
		
	}
	
	@Test(	description = "Change the account password",
			groups = { "functional" })
	public void ChangePassword_01() throws HarnessException {
		
		String password = "password"+ ZmailSeleniumProperties.getUniqueString();
		
		// Go to "General"
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.General);
		
		// Determine the status of the checkbox
		SeparateWindowChangePassword dialog = (SeparateWindowChangePassword)app.zPagePreferences.zToolbarPressButton(Button.B_CHANGE_PASSWORD);
		ZAssert.assertNotNull(dialog, "Verify the dialog was created");
		
		dialog.zSetOldPassword(app.zGetActiveAccount().Password);
		dialog.zSetNewPassword(password);
		dialog.zSetConfirmPassword(password);
		
		dialog.zClickButton(Button.B_SAVE);
		dialog.zCloseWindow();
				
		// Confirm that the new password is in use
		// by getting a new token
		app.zGetActiveAccount().Password = password;
		app.zGetActiveAccount().soapSend(
					"<AuthRequest xmlns='urn:zmailAccount'>"
				+		"<account by='name'>"+ app.zGetActiveAccount().EmailAddress +"</account>"
				+		"<password>"+ app.zGetActiveAccount().Password +"</password>"
				+	"</AuthRequest>");
		String token = app.zGetActiveAccount().soapSelectValue("//acct:AuthResponse//acct:authToken", null);
		ZAssert.assertGreaterThan(token.trim().length(), 0, "Verify the token is returned");
		
		app.zGetActiveAccount().authenticate();
	}



}
