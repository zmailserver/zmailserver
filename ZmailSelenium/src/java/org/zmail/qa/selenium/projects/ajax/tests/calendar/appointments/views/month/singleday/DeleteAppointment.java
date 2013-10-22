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
package com.zimbra.qa.selenium.projects.ajax.tests.calendar.appointments.views.month.singleday;

import java.util.*;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.core.Bugs;
import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.ajax.ui.calendar.*;


public class DeleteAppointment extends AjaxCommonTest {

	
	@SuppressWarnings("serial")
	public DeleteAppointment() {
		logger.info("New "+ DeleteAppointment.class.getCanonicalName());
		
		// All tests start at the Calendar page
		super.startingPage = app.zPageCalendar;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {{
		    put("zimbraPrefCalendarInitialView", "month");
		}};


	}
	
	@Bugs(ids = "69132")
	@Test(	description = "Delete a basic appointment in the month view",
			groups = { "functional" })
	public void DeleteAppointment_01() throws HarnessException {
		
		// Create the appointment on the server
		// Create the message data to be sent
		String subject = "appointment" + ZimbraSeleniumProperties.getUniqueString();
		
		AppointmentItem.createAppointmentSingleDay(
				app.zGetActiveAccount(),
				Calendar.getInstance(),
				120,
				null,
				subject,
				"content" + ZimbraSeleniumProperties.getUniqueString(),
				"location" + ZimbraSeleniumProperties.getUniqueString(),
				null);

		
		// Refresh the calendar
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		// Select the appointment
		app.zPageCalendar.zListItem(Action.A_LEFTCLICK, subject);
		
		// Click the "delete" button
		DialogConfirmDeleteAppointment dialog = (DialogConfirmDeleteAppointment)app.zPageCalendar.zToolbarPressButton(Button.B_DELETE);

		// Send the notification immediately
		dialog.zClickButton(Button.B_YES);

		
		
		// Verify that the appointment is in the trash now
		FolderItem trash = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Trash);
		AppointmentItem deleted = AppointmentItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +") is:anywhere");
		ZAssert.assertNotNull(deleted, "Verify the deleted appointment exists");
		ZAssert.assertEquals(deleted.getFolder(), trash.getId(), "Verify the deleted appointment exists");
	    
	}


}
