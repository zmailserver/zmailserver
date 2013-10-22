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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.gui.features;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogError;
import org.zmail.qa.selenium.projects.ajax.ui.DialogError.DialogErrorID;

public class ZmailFeatureOptionsDisabled extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public ZmailFeatureOptionsDisabled() {
		logger.info("New "+ ZmailFeatureOptionsDisabled.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageMail;

		super.startingAccountPreferences = new HashMap<String, String>() {
			{

				// Options/Preferences is disabled
				put("zmailFeatureOptionsEnabled", "FALSE");

			}
		};

	}
	
	/**
	 * See http://bugzilla.zmail.com/show_bug.cgi?id=62011 - WONTFIX
	 * @throws HarnessException
	 */
	@Bugs(ids="63652")	
	@Test(
			description = "Load the app with Preferences tab disabled", 
			groups = { "functional" }
			)
	public void ZmailFeatureOptionsDisabled_01() throws HarnessException {
		
		// Verify that the main app is loaded
		ZAssert.assertTrue(app.zPageMain.zIsActive(), "Verify that the main app is loaded");
		ZAssert.assertTrue(app.zPageMail.zIsActive(), "Verify that the mail app is loaded");

		// Verify bug 63652
		DialogError dialog = app.zPageMain.zGetErrorDialog(DialogErrorID.Zmail);
		ZAssert.assertFalse(dialog.zIsActive(), "Verify that the Permission Denied error dialog is not present");
		
	}
}