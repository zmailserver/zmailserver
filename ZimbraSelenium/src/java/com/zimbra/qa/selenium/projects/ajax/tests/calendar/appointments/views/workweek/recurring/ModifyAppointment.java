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
package com.zimbra.qa.selenium.projects.ajax.tests.calendar.appointments.views.workweek.recurring;

import java.util.Calendar;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.AppointmentItem;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZDate;
import com.zimbra.qa.selenium.framework.util.ZTimeZone;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.CalendarWorkWeekTest;
import com.zimbra.qa.selenium.projects.ajax.ui.calendar.FormApptNew;

public class ModifyAppointment extends CalendarWorkWeekTest {
	
	public ModifyAppointment() {
		logger.info("New " + ModifyAppointment.class.getCanonicalName());
		super.startingPage = app.zPageCalendar;
	}

	@Test(
			description = "Modify appointment with subject & body and verify it", 
			groups = { "implement" }) // smoke
	public void ModifyAppointment_01() throws HarnessException {

		// Creating object for appointment data
		AppointmentItem appt = new AppointmentItem();
		String tz, apptSubject, apptBody, editApptSubject, editApptBody;
		tz = ZTimeZone.TimeZoneEST.getID();
		apptSubject = ZimbraSeleniumProperties.getUniqueString();
		apptBody = ZimbraSeleniumProperties.getUniqueString();
		editApptSubject = ZimbraSeleniumProperties.getUniqueString();
        editApptBody = ZimbraSeleniumProperties.getUniqueString();
		
		// Absolute dates in UTC zone
		Calendar now = this.calendarWeekDayUTC;
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);
		
        app.zGetActiveAccount().soapSend(
                          "<CreateAppointmentRequest xmlns='urn:zimbraMail'>" +
                               "<m>"+
                               "<inv method='REQUEST' type='event' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"+
                               "<s d='"+ startUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
                               "<e d='"+ endUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
                               "<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
                               "</inv>" +
                               "<mp content-type='text/plain'>" +
                               "<content>"+ apptBody +"</content>" +
                               "</mp>" +
                               "<su>"+ apptSubject +"</su>" +
                               "</m>" +
                         "</CreateAppointmentRequest>");

        String apptId = app.zGetActiveAccount().soapSelectValue("//mail:CreateAppointmentResponse", "apptId");
    
        // Switch to work week view
        app.zPageCalendar.zToolbarPressPulldown(Button.B_LISTVIEW, Button.O_LISTVIEW_WORKWEEK);
        app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

        // Open appointment & modify subject, body and save it
        app.zPageCalendar.zListItem(Action.A_DOUBLECLICK, apptSubject);
        SleepUtil.sleepMedium();
        FormApptNew apptForm = new FormApptNew(app);
        appt.setSubject(editApptSubject);
        appt.setContent(editApptBody);
        apptForm.zFill(appt);
        apptForm.zToolbarPressButton(Button.B_SAVEANDCLOSE);
        
        // Use GetAppointmentRequest to verify the changes are saved
        app.zGetActiveAccount().soapSend("<GetAppointmentRequest  xmlns='urn:zimbraMail' id='"+ apptId +"'/>");
        app.zGetActiveAccount().soapMatch("//mail:GetAppointmentResponse//mail:comp", "name", editApptSubject);
        app.zGetActiveAccount().soapMatch(
				"//mail:GetAppointmentResponse//mail:content", null,
				editApptBody);
	}
}