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
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;

public class ZmailFeatureOptionsEnabled extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public ZmailFeatureOptionsEnabled() {
		logger.info("New "+ ZmailFeatureOptionsEnabled.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPagePreferences;

		super.startingAccountPreferences = new HashMap<String, String>() {
			{

				// Only options/Preferences is enabled
				put("zmailFeatureOptionsEnabled", "TRUE");
				put("zmailFeatureTasksEnabled", "FALSE");
				put("zmailFeatureMailEnabled", "FALSE");
				put("zmailFeatureContactsEnabled", "FALSE");
				put("zmailFeatureCalendarEnabled", "FALSE");
				put("zmailFeatureBriefcasesEnabled", "FALSE");
				put("zmailZimletAvailableZimlets", "-org_zmail_social");
				put("zmailPrefAutocompleteAddressBubblesEnabled", "TRUE");

			}
		};

	}
	
	/**
	 * See http://bugzilla.zmail.com/show_bug.cgi?id=62011 - WONTFIX
	 * @throws HarnessException
	 */
	@Bugs(ids="62011")	
	@Test(description = "Load the Preferences tab with just Preferences enabled", groups = { "deprecated" })
	public void ZmailFeatureOptionsEnabled_01() throws HarnessException {
		
		// Go to "General"
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.General);

		// Determine the status of the checkbox
		boolean checked = app.zPagePreferences
				.zGetCheckboxStatus("zmailPrefAutocompleteAddressBubblesEnabled");

		// Since zmailPrefAutocompleteAddressBubblesEnabled is set to TRUE, the
		// Bubble checkbox should be checked
		ZAssert.assertTrue(checked, "Verify if Address Bubbles check box is checked");

	}
}