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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.notifications;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;


public class ZmailPrefCalendarReminderEmail extends AjaxCommonTest {

	public ZmailPrefCalendarReminderEmail() {
		
		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = null;
	}


	@Test(
			description = "Set zmailPrefCalendarReminderEmail to a valid Email address'",
			groups = { "functional" }
			)
	public void ZmailPrefCalendarReminderEmail_01() throws HarnessException {
		
		ZmailAccount destination = new ZmailAccount();
		destination.provision().authenticate();
		
		
		
		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Notifications);

		
		// Set the address
		String locator = "css=div[id='ZmNotificationsPage'] input[id='ZmNotificationsPage_EMAIL_input']";
		
		// To activate the Search button, need to focus/click
		app.zPagePreferences.sFocus(locator);
		app.zPagePreferences.zClick(locator);
		app.zPagePreferences.sType(locator, destination.EmailAddress);

		// Click save
		app.zPagePreferences.zToolbarPressButton(Button.B_SAVE);		
		

		// Verify the preference is set to false
		app.zGetActiveAccount().soapSend(
						"<GetPrefsRequest xmlns='urn:zmailAccount'>"
				+			"<pref name='zmailPrefCalendarReminderEmail'/>"
				+		"</GetPrefsRequest>");
		
		String value = app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zmailPrefCalendarReminderEmail']", null);
		ZAssert.assertEquals(value, destination.EmailAddress, "Verify zmailPrefCalendarReminderEmail was changed to "+ destination.EmailAddress);
		
	}
}
