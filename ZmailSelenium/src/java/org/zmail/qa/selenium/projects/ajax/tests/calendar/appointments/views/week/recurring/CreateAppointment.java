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
package org.zmail.qa.selenium.projects.ajax.tests.calendar.appointments.views.week.recurring;

import java.util.Calendar;
import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.AppointmentItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.CalendarWorkWeekTest;
import org.zmail.qa.selenium.projects.ajax.ui.calendar.FormApptNew;

public class CreateAppointment extends CalendarWorkWeekTest {


	public CreateAppointment() {
		logger.info("New "+ CreateAppointment.class.getCanonicalName());
		
		// All tests start at the Calendar page
		super.startingPage = app.zPageCalendar;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = -2913827779459595178L;
		{
		    put("zmailPrefCalendarInitialView", "week");
		}};

	}

	
	@Test(	
			description = "Create basic recurring appointment (every day) in week view", 
			groups = { "smoke" } )
	public void CreateRecurringAppointment_01() throws HarnessException {
		
		//-- Data Setup
		
		// Appointment data
		ZDate startTime, endTime;
		AppointmentItem appt = new AppointmentItem();
		Calendar now = this.calendarWeekDayUTC;
		
		startTime = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		endTime = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);
		
		appt.setSubject("appointment" + ZmailSeleniumProperties.getUniqueString());
		appt.setAttendees(ZmailAccount.AccountA().EmailAddress);
		appt.setContent("content" + ZmailSeleniumProperties.getUniqueString());
		appt.setStartTime(startTime);
		appt.setEndTime(endTime);
		appt.setRecurring("EVERYDAY", "");

		
		//-- GUI steps
		
		
		
		// Create series appointment
		FormApptNew apptForm = (FormApptNew) app.zPageCalendar.zToolbarPressButton(Button.B_NEW);
		apptForm.zFill(appt);
		apptForm.zSubmit();
		
		
		//-- Data Verification
		
		// Verify the new appointment exists on the server
		AppointmentItem actual = AppointmentItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ appt.getSubject() +")", appt.getStartTime().addDays(-7), appt.getEndTime().addDays(7));
		ZAssert.assertNotNull(actual, "Verify the new appointment is created");
		ZAssert.assertEquals(actual.getSubject(), appt.getSubject(), "Subject: Verify the appointment data");
		ZAssert.assertEquals(app.zGetActiveAccount().soapSelectValue("//mail:s", "d"), startTime.toYYYYMMDDTHHMMSS(), "Verify recurring appointment start time and date");
		ZAssert.assertEquals(app.zGetActiveAccount().soapSelectValue("//mail:e", "d"), endTime.toYYYYMMDDTHHMMSS(), "Verify recurring appointment end time and date");
		
//		Move this verification to GetAppointment or ViewAppointment
//		
//		// Open instance and verify corresponding UI
//		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);
//        app.zPageCalendar.zListItem(Action.A_DOUBLECLICK, appt.getSubject());
//        DialogOpenRecurringItem dlgConfirm = new DialogOpenRecurringItem(DialogOpenRecurringItem.Confirmation.OPENRECURRINGITEM, app, ((AppAjaxClient) app).zPageCalendar);
//		dlgConfirm.zClickButton(Button.B_OK);
//		ZAssert.assertEquals(app.zPageCalendar.sIsElementPresent(Locators.RepeatDisabled), true, "Verify 'Every Week' menu item is disabled");
//		SleepUtil.sleepMedium();
//		app.zPageCalendar.zToolbarPressButton(Button.B_CLOSE);
//		
//		// Open entire series and verify corresponding UI
//		app.zPageCalendar.zListItem(Action.A_DOUBLECLICK, appt.getSubject());
//        dlgConfirm = new DialogOpenRecurringItem(DialogOpenRecurringItem.Confirmation.OPENRECURRINGITEM, app, ((AppAjaxClient) app).zPageCalendar);
//        app.zPageCalendar.zCheckRadioButton(Button.B_OPEN_THE_SERIES);
//		dlgConfirm.zClickButton(Button.B_OK);
//		ZAssert.assertEquals(app.zPageCalendar.sIsElementPresent(Locators.RepeatEnabled), true, "Verify 'Every Week' menu item is enabled");
//		SleepUtil.sleepMedium();
//		app.zPageCalendar.zToolbarPressButton(Button.B_CLOSE);
		
	}

}
