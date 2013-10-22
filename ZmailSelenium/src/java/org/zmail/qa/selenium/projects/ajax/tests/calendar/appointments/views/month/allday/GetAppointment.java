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
package com.zimbra.qa.selenium.projects.ajax.tests.calendar.appointments.views.month.allday;

import java.util.*;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.core.Bugs;
import com.zimbra.qa.selenium.framework.items.AppointmentItem;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCommonTest;


public class GetAppointment extends AjaxCommonTest {

	
	@SuppressWarnings("serial")
	public GetAppointment() {
		logger.info("New "+ GetAppointment.class.getCanonicalName());
		
		// All tests start at the Calendar page
		super.startingPage = app.zPageCalendar;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {{
		    put("zimbraPrefCalendarInitialView", "month");
		}};


	}
	
	@Bugs(ids = "69132")
	@Test(	description = "View a basic appointment in the month view",
			groups = { "functional" })
	public void GetAppointment_01() throws HarnessException {
		
		// Create an appointment on the server
		String subject = "appointment" + ZimbraSeleniumProperties.getUniqueString();

		AppointmentItem.createAppointmentAllDay(
				app.zGetActiveAccount(),
				Calendar.getInstance(),
				1,
				subject,
				"content" + ZimbraSeleniumProperties.getUniqueString(),
				"location" + ZimbraSeleniumProperties.getUniqueString(),
				null);
		
		

		// Refresh the calendar
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		// Get the list of appointments in the current view
		List<AppointmentItem> items = app.zPageCalendar.zListGetAppointments();
		ZAssert.assertNotNull(items, "Get the list of appointments");
		
		// Verify the appointment is in the view
		AppointmentItem found = null;
		for(AppointmentItem item : items) {
			if ( item.getSubject().equals(subject) ) {
				found = item;
				break;
			}
		}
		
		ZAssert.assertNotNull(found, "Verify the new appointment appears in the view");
	    
	}

	@Bugs(ids = "69132")
	@Test(	description = "View a multi-day appointment in the month view",
			groups = { "functional" })
	public void GetAppointment_02() throws HarnessException {
		
		// Create an appointment on the server
		String subject = "appointment" + ZimbraSeleniumProperties.getUniqueString();

		AppointmentItem.createAppointmentAllDay(
				app.zGetActiveAccount(),
				Calendar.getInstance(),
				2,
				subject,
				"content" + ZimbraSeleniumProperties.getUniqueString(),
				"location" + ZimbraSeleniumProperties.getUniqueString(),
				null);
		
		

		// Refresh the calendar
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		// Verify appointment displayed in month view
		ZAssert.assertEquals(app.zPageCalendar.sIsElementPresent(app.zPageCalendar.zGetAllDayApptLocator(subject)), true, "Verify appointment present in month view");
		
		// Below code works fine for single-day all day but for multi-day all day appointment it fails because it finds empty cell
		
		//List<AppointmentItem> items = app.zPageCalendar.zListGetAppointments();
		//ZAssert.assertNotNull(items, "Get the list of appointments");
		
		// Verify the appointment is in the view
		//AppointmentItem found = null;
		//for(AppointmentItem item : items) {
		//	if ( item.getSubject().equals(subject) ) {
		//		found = item;
		//		break;
		//	}
		//}
		
		//ZAssert.assertNotNull(found, "Verify the new appointment appears in the view");
	    
	}


}
