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

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogWarning;
import org.zmail.qa.selenium.projects.ajax.ui.DialogWarning.DialogWarningID;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;


public class ZmailPrefCalendarReminderSMS extends AjaxCommonTest {

	protected String sms = null;
	protected String code = null;
	
	
	public ZmailPrefCalendarReminderSMS() {
		
		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = 8123430160111682678L;
			{
				put("zmailFeatureCalendarReminderDeviceEmailEnabled", "TRUE");
			}
		};
		
		// Determine the SMS number
		sms = ZmailSeleniumProperties.getStringProperty("sms.default.number", "6505551212");
		code = ZmailSeleniumProperties.getStringProperty("sms.default.code", "654321");
		
	}


	@Test(
			description = "Send SendVerificationCodeRequest to an SMS address",
			groups = { "functional" }
			)
	public void ZmailPrefCalendarReminderSMS_01() throws HarnessException {
		
		String locator;
		boolean visible;
		
		
		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Notifications);

		// Wait for the page to  be drawn
		SleepUtil.sleep(5000);

		// Set the SMS address
		locator = "css=input[id='ZmNotificationsPage_DEVICE_EMAIL_PHONE_input']";
		
		visible = app.zPagePreferences.zIsVisiblePerPosition(locator, 0, 0);
		ZAssert.assertTrue(visible, "Verify the SMS number field is present");
		
		app.zPagePreferences.sFocus(locator);
		app.zPagePreferences.zClick(locator);
		app.zPagePreferences.zKeyboardTypeString(sms);

		// Click "Send Code"
		locator = "css=td[id='ZmNotificationsPage_DEVICE_EMAIL_PHONE_SEND_CODE_title']";
		app.zPagePreferences.zClick(locator);
		
		
		// Verify the popup is displayed
		DialogWarning dialog = app.zPageMain.zGetWarningDialog(DialogWarningID.SmsVerificationCodeSent);
		dialog.zWaitForActive();
		
		ZAssert.assertTrue(dialog.zIsActive(), "Verify the confirmation dialog appears");
		dialog.zClickButton(Button.B_OK);

		
	}
	
	@Test(
			description = "Send VerifyCodeRequest to an SMS address",
			groups = { "functional" }
			)
	public void ZmailPrefCalendarReminderSMS_02() throws HarnessException {
		
		String locator;		
		boolean visible;

		
		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Notifications);

		// Wait for the page to  be drawn
		SleepUtil.sleep(5000);

		
		// Set the SMS address
		locator = "css=input[id='ZmNotificationsPage_DEVICE_EMAIL_PHONE_input']";
		
		visible = app.zPagePreferences.zIsVisiblePerPosition(locator, 0, 0);
		ZAssert.assertTrue(visible, "Verify the SMS number field is present");
		
		app.zPagePreferences.sFocus(locator);
		app.zPagePreferences.zClick(locator);
		app.zPagePreferences.zKeyboardTypeString(sms);
		
		// Set the code
		locator = "css=input[id='ZmNotificationsPage_DEVICE_EMAIL_CODE_input']";

		visible = app.zPagePreferences.zIsVisiblePerPosition(locator, 0, 0);
		ZAssert.assertTrue(visible, "Verify the Code field is present");

		app.zPagePreferences.sFocus(locator);
		app.zPagePreferences.zClick(locator);
		app.zPagePreferences.zKeyboardTypeString(code);

		
		// Click "Validate Code"
		locator = "css=td[id='ZmNotificationsPage_DEVICE_EMAIL_CODE_VALIDATE_title']";
		app.zPagePreferences.zClick(locator);

		
	}

}
