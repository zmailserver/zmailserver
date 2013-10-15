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
package org.zmail.qa.selenium.projects.ajax.tests.calendar.meetings.resources;

import java.util.Calendar;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.AppointmentItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.CalendarWorkWeekTest;
import org.zmail.qa.selenium.projects.ajax.ui.calendar.FormApptNew;

public class CreateMeetingWithLocation extends CalendarWorkWeekTest {

	public CreateMeetingWithLocation() {
		logger.info("New "+ CreateMeetingWithLocation.class.getCanonicalName());
		super.startingPage = app.zPageCalendar;
	}
	
	@Bugs(ids = "69132")
	@Test(description = "Create simple meeting with location resource",
			groups = { "sanity" })
	public void CreateMeetingWithSingleLocation_01() throws HarnessException {
		
		
		//-- Data Setup
		
		
		// Create appointment data
		AppointmentItem appt = new AppointmentItem();
		ZmailResource location = new ZmailResource(ZmailResource.Type.LOCATION);
		
		String apptSubject, apptAttendee1, apptLocation1, apptContent;
		Calendar now = this.calendarWeekDayUTC;
		apptSubject = "appointment" + ZmailSeleniumProperties.getUniqueString();
		apptAttendee1 = ZmailAccount.AccountA().EmailAddress;
		apptLocation1 = location.EmailAddress;
		apptContent = "content" + ZmailSeleniumProperties.getUniqueString();
		
		appt.setSubject(apptSubject);
		appt.setAttendees(apptAttendee1);
		appt.setLocation(apptLocation1);
		appt.setStartTime(new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0));
		appt.setEndTime(new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 13, 0, 0));
		appt.setContent(apptContent);
	
		
		
		//-- GUI Actions
		
		
		// Compose appointment and send it to invitee
		FormApptNew apptForm = (FormApptNew) app.zPageCalendar.zToolbarPressButton(Button.B_NEW);
		apptForm.zFill(appt);
		apptForm.zSubmit();
		
		
		// Because the response from the resource may
		// take some time, make sure the response is
		// received in the inbox before proceeding
		for (int i = 0; i < 10; i++) {
			
			app.zGetActiveAccount().soapSend(
						"<SearchRequest xmlns='urn:zmailMail' types='message'>"
					+		"<query>in:inbox subject:(aa"+ apptSubject +")</query>"
					+	"</SearchRequest>");
			
			String id = app.zGetActiveAccount().soapSelectValue("//mail:m", "id");
			if ( id != null ) {
				// found it
				break;
			}
			
			SleepUtil.sleep(1000);
		}
		
		
		
		//-- Verification
		
		
		// Verify appointment exists on the server
		AppointmentItem actual = AppointmentItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ appt.getSubject() +")", appt.getStartTime().addDays(-7), appt.getEndTime().addDays(7));
		ZAssert.assertNotNull(actual, "Verify the new appointment is created");
		ZAssert.assertEquals(actual.getSubject(), appt.getSubject(), "Subject: Verify the appointment data");
		ZAssert.assertEquals(actual.getAttendees(), appt.getAttendees(), "Attendees: Verify the appointment data");
		ZAssert.assertEquals(actual.getLocation(), appt.getLocation(), "Location: Verify the appointment data");
		ZAssert.assertEquals(actual.getContent(), appt.getContent(), "Content: Verify the appointment data");
		
		// Verify location free/busy status shows as psts=AC	
		String locationStatus = app.zGetActiveAccount().soapSelectValue("//mail:at[@a='"+ apptLocation1 +"']", "ptst");
		ZAssert.assertEquals(locationStatus, "AC", "Verify that the location status shows as 'ACCEPTED'");
		
        
	}
	
	@Bugs(ids = "69132")
	@Test(description = "Create simple meeting with two location resource",
			groups = { "functional" })
	public void CreateMeetingWithMultiLocation_02() throws HarnessException {
		
		// Create appointment data
		AppointmentItem appt = new AppointmentItem();
		ZmailResource location1 = new ZmailResource(ZmailResource.Type.LOCATION);
		ZmailResource location2 = new ZmailResource(ZmailResource.Type.LOCATION);		
		
		String apptSubject, apptAttendee1, apptLocation, apptContent;
		Calendar now = this.calendarWeekDayUTC;
		apptSubject = ZmailSeleniumProperties.getUniqueString();
		apptAttendee1 = ZmailAccount.AccountA().EmailAddress;
		apptLocation = location1.EmailAddress + " " + location2.EmailAddress;
		apptContent = ZmailSeleniumProperties.getUniqueString();
		
		appt.setSubject(apptSubject);
		appt.setAttendees(apptAttendee1);
		appt.setLocation(apptLocation);
		appt.setStartTime(new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 13, 0, 0));
		appt.setEndTime(new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0));
		appt.setContent(apptContent);
	
		// Compose appointment and send it to invitee
		FormApptNew apptForm = (FormApptNew) app.zPageCalendar.zToolbarPressButton(Button.B_NEW);
		apptForm.zFill(appt);
		apptForm.zSubmit();
		
		// Because the response from the resource may
		// take some time, make sure the response is
		// received in the inbox before proceeding
		for (int i = 0; i < 10; i++) {
			
			app.zGetActiveAccount().soapSend(
						"<SearchRequest xmlns='urn:zmailMail' types='message'>"
					+		"<query>in:inbox subject:(aa"+ apptSubject +") from:("+ location1.EmailAddress +")</query>"
					+	"</SearchRequest>");
			
			String id1 = app.zGetActiveAccount().soapSelectValue("//mail:m", "id");

			app.zGetActiveAccount().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='message'>"
				+		"<query>in:inbox subject:(aa"+ apptSubject +") from:("+ location2.EmailAddress +")</query>"
				+	"</SearchRequest>");
		
			String id2 = app.zGetActiveAccount().soapSelectValue("//mail:m", "id");

			if ( (id1 != null) && (id2 != null) ) {
				// found it
				break;
			}
			
			SleepUtil.sleep(1000);
		}

		// Verify appointment exists on the server
		AppointmentItem actual = AppointmentItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ appt.getSubject() +")", appt.getStartTime().addDays(-7), appt.getEndTime().addDays(7));
		ZAssert.assertNotNull(actual, "Verify the new appointment is created");
		ZAssert.assertEquals(actual.getLocation().replace(";", ""), appt.getLocation(), "Location: Verify the appointment data");
		
		// Verify both location free/busy status
		String locationStatus1 = app.zGetActiveAccount().soapSelectValue("//mail:at[@a='"+ location1.EmailAddress +"']", "ptst");
		String locationStatus2 = app.zGetActiveAccount().soapSelectValue("//mail:at[@a='"+ location2.EmailAddress +"']", "ptst");
		ZAssert.assertEquals(locationStatus1, "AC", "Verify that the location1 status shows as 'ACCEPTED'");
		ZAssert.assertEquals(locationStatus2, "AC", "Verify that the location2 status shows as 'ACCEPTED'");
		
		
	}
	
	@Test(description = "Create simple meeting with floating location resource",
			groups = { "functional" })
	public void CreateMeetingWithFloatingLocation_03() throws HarnessException {
		
		// Create appointment data
		AppointmentItem appt = new AppointmentItem();
		
		String apptSubject, apptAttendee1, apptLocation, apptContent;
		Calendar now = this.calendarWeekDayUTC;
		apptSubject = ZmailSeleniumProperties.getUniqueString();
		apptAttendee1 = ZmailAccount.AccountA().EmailAddress;
		apptLocation = ZmailSeleniumProperties.getUniqueString() + " " + ZmailSeleniumProperties.getUniqueString();
		apptContent = ZmailSeleniumProperties.getUniqueString();
		
		appt.setSubject(apptSubject);
		appt.setAttendees(apptAttendee1);
		appt.setLocation(apptLocation);
		appt.setStartTime(new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0));
		appt.setEndTime(new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 15, 0, 0));
		appt.setContent(apptContent);
	
		// Compose appointment and send it to invitee
		FormApptNew apptForm = (FormApptNew) app.zPageCalendar.zToolbarPressButton(Button.B_NEW);
		apptForm.zFill(appt);
		apptForm.zSubmit();
		
		// Verify appointment exists on the server
		AppointmentItem actual = AppointmentItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ appt.getSubject() +")", appt.getStartTime().addDays(-7), appt.getEndTime().addDays(7));
		ZAssert.assertNotNull(actual, "Verify the new appointment is created");
		
	}

}
