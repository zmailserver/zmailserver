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
package org.zmail.qa.selenium.projects.desktop.tests.calendar.appointments;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.AppointmentItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.calendar.FormApptNew;


public class CreateAppointment extends AjaxCommonTest {


	public CreateAppointment() {
		logger.info("New "+ CreateAppointment.class.getCanonicalName());

		// All tests start at the Calendar page
		super.startingPage = app.zPageCalendar;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = null;


	}

	@Test(	description = "Create a basic appointment",
			groups = { "sanity" })
			public void CreateAppointment_01() throws HarnessException {

		// Create the message data to be sent
		AppointmentItem appt = new AppointmentItem();
		appt.setSubject("appointment" + ZmailSeleniumProperties.getUniqueString());
		appt.setContent("content" + ZmailSeleniumProperties.getUniqueString());
		appt.setStartTime(new ZDate(2014, 12, 25, 12, 0, 0));
		appt.setEndTime(new ZDate(2014, 12, 25, 14, 0, 0));


		// Open the new mail form
		FormApptNew apptForm = (FormApptNew) app.zPageCalendar.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(apptForm, "Verify the new form opened");

		// Fill out the form with the data
		apptForm.zFill(appt);

		// Send the message
		apptForm.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		AppointmentItem actual = AppointmentItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ appt.getSubject() +")", appt.getStartTime().addDays(-7), appt.getEndTime().addDays(7));
		ZAssert.assertNotNull(actual, "Verify the new appointment is created");

		ZAssert.assertEquals(actual.getSubject(), appt.getSubject(), "Subject: Verify the appointment data");
		
	}



}
