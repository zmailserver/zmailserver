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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.calendar;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;


public class ZmailPrefCalendarAlwaysShowMiniCalTRUE extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public ZmailPrefCalendarAlwaysShowMiniCalTRUE() {
		
		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefCalendarAlwaysShowMiniCal", "FALSE");
			}
		};
	}

	@Bugs(ids = "78547")
	@Test(
			description = "Set zmailPrefCalendarAlwaysShowMiniCal to 'TRUE'",
			groups = { "functional" }
			)
	public void ZmailPrefCalendarAlwaysShowMiniCalFALSE_01() throws HarnessException {
		
		// Navigate to preferences -> calendar
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Calendar);

		
		// Click checkbox for zmailPrefCalendarAlwaysShowMiniCal
		logger.info("Click checkbox for zmailPrefCalendarAlwaysShowMiniCal");
		app.zPagePreferences.zCheckboxSet("css=input[id$='_CAL_ALWAYS_SHOW_MINI_CAL']", true);
		
		// Not sure why, but sleep is required here
		SleepUtil.sleepLong();

		// Click save
		app.zPagePreferences.zToolbarPressButton(Button.B_SAVE);		
		

		// Verify the preference is set to false
		app.zGetActiveAccount().soapSend(
						"<GetPrefsRequest xmlns='urn:zmailAccount'>"
				+			"<pref name='zmailPrefCalendarAlwaysShowMiniCal'/>"
				+		"</GetPrefsRequest>");
		
		String value = app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zmailPrefCalendarAlwaysShowMiniCal']", null);
		ZAssert.assertEquals(value, "TRUE", "Verify zmailPrefCalendarAlwaysShowMiniCal was changed to 'TRUE'");
		
	}
}
