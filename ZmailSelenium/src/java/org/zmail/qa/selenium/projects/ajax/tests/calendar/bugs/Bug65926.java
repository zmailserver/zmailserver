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
package org.zmail.qa.selenium.projects.ajax.tests.calendar.bugs;

import java.util.Calendar;
import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.AppointmentItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.CalendarWorkWeekTest;
import org.zmail.qa.selenium.projects.ajax.ui.calendar.FormApptNew;

public class Bug65926 extends CalendarWorkWeekTest {

	public Bug65926() {
		logger.info("New "+ Bug65926.class.getCanonicalName());
		super.startingPage = app.zPageCalendar;
		this.startingAccountPreferences.put("zmailFeatureGalEnabled", "FALSE");
	}

	
	@Test(	
			description = "Create a basic appointment this zmailFeatureGalEnabled=FALSE",
			groups = { "functional" }	
		)
	public void Bug65926_01() throws HarnessException {
		
		// Modify the test account to disable GAL
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<ModifyAccountRequest xmlns='urn:zmailAdmin'>"
			+		"<id>"+ app.zGetActiveAccount().ZmailId +"</id>"
			+		"<a n='zmailFeatureGalEnabled'>FALSE</a>"
			+	"</ModifyAccountRequest>");

		// Logout and login to pick up the changes
		app.zPageLogin.zNavigateTo();
		this.startingPage.zNavigateTo();

		// Create appointment
		AppointmentItem appt = new AppointmentItem();
		Calendar now = this.calendarWeekDayUTC;
		appt.setSubject("appointment" + ZmailSeleniumProperties.getUniqueString());
		appt.setContent("content" + ZmailSeleniumProperties.getUniqueString());
		appt.setStartTime(new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0));
		appt.setEndTime(new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0));
	
		// Open the new mail form
		FormApptNew apptForm = (FormApptNew) app.zPageCalendar.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(apptForm, "Verify the new form opened");

		// Fill out the form with the data
		apptForm.zFill(appt);

		// Send the message
		apptForm.zSubmit();
			
		// Verify the new appointment exists on the server
		AppointmentItem actual = AppointmentItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ appt.getSubject() +")", appt.getStartTime().addDays(-7), appt.getEndTime().addDays(7));
		ZAssert.assertNotNull(actual, "Verify the new appointment is created");
		ZAssert.assertEquals(actual.getSubject(), appt.getSubject(), "Subject: Verify the appointment data");
	}
	
	
	@Test(	
			description = "Create a basic appointment this GAL features disabled",
			groups = { "functional" }	
		)
	public void Bug65926_02() throws HarnessException {
		
		// Modify the test account to disable GAL
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<ModifyAccountRequest xmlns='urn:zmailAdmin'>"
			+		"<id>"+ app.zGetActiveAccount().ZmailId +"</id>"
			+		"<a n='zmailFeatureGalAutoCompleteEnabled'>FALSE</a>"
			+		"<a n='zmailFeatureGalEnabled'>FALSE</a>"
			+		"<a n='zmailFeatureGalSyncEnabled'>FALSE</a>"
			+	"</ModifyAccountRequest>");

		// Logout and login to pick up the changes
		app.zPageLogin.zNavigateTo();
		this.startingPage.zNavigateTo();

		// Create appointment
		AppointmentItem appt = new AppointmentItem();
		Calendar now = this.calendarWeekDayUTC;
		appt.setSubject("appointment" + ZmailSeleniumProperties.getUniqueString());
		appt.setContent("content" + ZmailSeleniumProperties.getUniqueString());
		appt.setStartTime(new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0));
		appt.setEndTime(new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0));
	
		// Open the new mail form
		FormApptNew apptForm = (FormApptNew) app.zPageCalendar.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(apptForm, "Verify the new form opened");

		// Fill out the form with the data
		apptForm.zFill(appt);

		// Send the message
		apptForm.zSubmit();
			
		// Verify the new appointment exists on the server
		AppointmentItem actual = AppointmentItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ appt.getSubject() +")", appt.getStartTime().addDays(-7), appt.getEndTime().addDays(7));
		ZAssert.assertNotNull(actual, "Verify the new appointment is created");
		ZAssert.assertEquals(actual.getSubject(), appt.getSubject(), "Subject: Verify the appointment data");
	}
	
	
}
