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
package org.zmail.qa.selenium.projects.ajax.tests.calendar.performance;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.AppointmentItem;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.framework.util.performance.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;


public class ZmCalendarApp_ViewMonth_Appointment1 extends AjaxCommonTest {

	
	public ZmCalendarApp_ViewMonth_Appointment1() throws HarnessException {
		logger.info("New "+ ZmCalendarApp_ViewMonth_Appointment1.class.getCanonicalName());
		
		super.startingPage = app.zPageMail;
		
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = 3038458962443347843L;
			{
				put("zmailPrefCalendarInitialView", "month");
			}};


	}
	
	
	@Test(	description = "Measure the time to load the Calendar, month view, initial load",
			groups = { "performance" })
	public void ZmCalendarApp_01() throws HarnessException {
		
		// Create an appointment
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
		AppointmentItem.createAppointmentSingleDay(
				app.zGetActiveAccount(),
				Calendar.getInstance(),
				60,
				null,
				subject,
				"content" + ZmailSeleniumProperties.getUniqueString(),
				"location" + ZmailSeleniumProperties.getUniqueString(),
				null);


		// Start the perf token
		PerfToken token = PerfMetrics.startTimestamp(PerfKey.ZmCalendarApp, "Load the calendar app, month view, initial load");

		// Go to calendar
		app.zPageCalendar.zNavigateTo();

		PerfMetrics.waitTimestamp(token);
				
		// Wait for the app to load
		app.zPageCalendar.zWaitForActive();
		

	}


	@Test(	description = "Measure the time to load the Calendar, month view, 1 appointment",
			groups = { "performance" })
	public void ZmCalendarApp_02() throws HarnessException {
		
		// Create an appointment
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
		AppointmentItem.createAppointmentSingleDay(
				app.zGetActiveAccount(),
				Calendar.getInstance(),
				60,
				null,
				subject,
				"content" + ZmailSeleniumProperties.getUniqueString(),
				"location" + ZmailSeleniumProperties.getUniqueString(),
				null);


		// Start the perf token
		PerfToken token = PerfMetrics.startTimestamp(PerfKey.ZmCalendarApp, "Load the calendar app, month view, 1 appointment");

		// Go to calendar
		app.zPageCalendar.zNavigateTo();

		PerfMetrics.waitTimestamp(token);
				
		// Wait for the app to load
		app.zPageCalendar.zWaitForActive();
		

	}

	@Test(	description = "Measure the time to load the Calendar, month view, 100 appointment",
			groups = { "performance" })
	public void ZmCalendarApp_03() throws HarnessException {
		
		// What is today?
		Calendar today = Calendar.getInstance();

		// Import 100 appointments using Calendar.ics and REST
		String filename = ZmailSeleniumProperties.getBaseDirectory() + "/data/public/ics/calendar05/Calendar.ics";
		File file = null;

		// Modify the ICS in two ways:
		// 1. Make the current account the organizer
		// 2. Make the dates equal to this month
		file = RestUtil.FileUtils.replaceInFile("user@domain.com", app.zGetActiveAccount().EmailAddress, new File(filename));
		file = RestUtil.FileUtils.replaceInFile("201111", (new SimpleDateFormat("yyyyMM")).format(today.getTime()), file);

		RestUtil rest = new RestUtil();
		rest.setAuthentication(app.zGetActiveAccount());
		rest.setPath("/service/home/~/Calendar");
		rest.setQueryParameter("fmt", "ics");
		rest.setUploadFile(file);
		rest.doPost();


		// Start the perf token
		PerfToken token = PerfMetrics.startTimestamp(PerfKey.ZmCalendarApp, "Load the calendar app, month view, 100 appointment");

		// Go to calendar
		app.zPageCalendar.zNavigateTo();

		PerfMetrics.waitTimestamp(token);
				
		// Wait for the app to load
		app.zPageCalendar.zWaitForActive();
		

	}


}
