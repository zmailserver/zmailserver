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
package org.zmail.qa.selenium.projects.ajax.tests.calendar.appointments.views.workweek.recurring;

import java.awt.event.KeyEvent;
import java.util.Calendar;

import org.testng.annotations.*;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.CalendarWorkWeekTest;
import org.zmail.qa.selenium.projects.ajax.ui.*;
import org.zmail.qa.selenium.projects.ajax.ui.calendar.*;

@SuppressWarnings("unused")
public class DeleteInstance extends CalendarWorkWeekTest {
	
	public DeleteInstance() {
		logger.info("New "+ DeleteInstance.class.getCanonicalName());
		super.startingPage = app.zPageCalendar;
	}
	
	
	@Bugs(ids = "69132")
	@Test(	
			description = "Delete instance of recurring appointment (every month) using toolbar button", 
			groups = { "functional" } )
	public void DeleteInstance_04() throws HarnessException {
		
		//-- Data Setup
		
		
		
		// Appointment data
		String tz, apptSubject, apptBody;
		tz = ZTimeZone.TimeZoneEST.getID();
		apptSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		apptBody = "body" + ZmailSeleniumProperties.getUniqueString();
		
		// Absolute dates in UTC zone
		Calendar now = this.calendarWeekDayUTC;
		ZDate startTime = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endTime   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);
		
		app.zGetActiveAccount().soapSend(
				"<CreateAppointmentRequest xmlns='urn:zmailMail'>" +
					"<m>"+
						"<inv method='REQUEST' type='event' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"+
							"<s d='"+ startTime.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
							"<e d='"+ endTime.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
							"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<recur>" +
								"<add>" +
									"<rule freq='MON'>" +
										"<interval ival='1'/>" +
									"</rule>" +
								"</add>" +
							"</recur>" +
						"</inv>" +
						"<mp content-type='text/plain'>" +
							"<content>"+ apptBody +"</content>" +
						"</mp>" +
						"<su>"+ apptSubject +"</su>" +
					"</m>" +
				"</CreateAppointmentRequest>");

        
        //-- GUI actions
        
        
		// Delete instance and verify corresponding UI
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);
		
        app.zPageCalendar.zListItem(Action.A_LEFTCLICK, apptSubject);
        
        DialogWarning dialogSeriesOrInstance = (DialogWarning)app.zPageCalendar.zToolbarPressButton(Button.B_DELETE);
        dialogSeriesOrInstance.zClickButton(Button.B_DELETE_THIS_INSTANCE);
        DialogWarning confirmDelete = (DialogWarning)dialogSeriesOrInstance.zClickButton(Button.B_OK);
        confirmDelete.zClickButton(Button.B_YES);
        
        
        
        //-- Verification
        
		// On the server, verify the appointment is in the trash
		app.zGetActiveAccount().soapSend(
				"<SearchRequest xmlns='urn:zmailMail' types='appointment' calExpandInstStart='"+ startTime.addDays(-7).toMillis() +"' calExpandInstEnd='"+ endTime.addDays(7).toMillis() +"'>"
			+		"<query>is:anywhere "+ apptSubject +"</query>"
			+	"</SearchRequest>");

		// http://bugzilla.zmail.com/show_bug.cgi?id=63412 - "Deleting instance from calendar series does not allow for user restoration from the Trash can"
		// http://bugzilla.zmail.com/show_bug.cgi?id=13527#c4 - "Moving an instance from one cal to other, moves complete series"
		// For now, nothing should be returned in the SearchResponse
		//		
		//		String folderID = app.zGetActiveAccount().soapSelectValue("//mail:appt", "l");
		//		ZAssert.assertEquals(
		//				folderID,
		//				FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Trash).getId(),
		//				"Verify appointment is in the trash folder");

		Element[] appts = app.zGetActiveAccount().soapSelectNodes("//mail:appt");
		ZAssert.assertEquals(appts.length, 0, "Verify the appt element does not exist ... See also bug 63412");

		// Verify the appointment is not in the GUI view
		//ZAssert.assertEquals(app.zPageCalendar.sIsElementPresent(app.zPageCalendar.zGetApptLocator(apptSubject)), false, "Verify instance is deleted from the calendar");
		boolean deleted = app.zPageCalendar.zWaitForElementDeleted(app.zPageCalendar.zGetApptLocator(apptSubject), "5000");
		ZAssert.assertEquals(deleted, true, "Verify instance is deleted from the calendar");

	}
	
	@Bugs(ids = "69132")
	@Test(	
			description = "Delete instance of recurring appointment (every year) using context menu", 
			groups = { "functional" } )
	public void DeleteInstance_05() throws HarnessException {
		
		//-- Data Setup
		
		
		
		// Appointment data
		String tz, apptSubject, apptBody;
		tz = ZTimeZone.TimeZoneEST.getID();
		apptSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		apptBody = "body" + ZmailSeleniumProperties.getUniqueString();
		
		// Absolute dates in UTC zone
		Calendar now = this.calendarWeekDayUTC;
		ZDate startTime = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endTime   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);
		
		app.zGetActiveAccount().soapSend(
				"<CreateAppointmentRequest xmlns='urn:zmailMail'>" +
					"<m>"+
						"<inv method='REQUEST' type='event' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"+
							"<s d='"+ startTime.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
							"<e d='"+ endTime.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
							"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<recur>" +
								"<add>" +
									"<rule freq='YEA'>" +
										"<interval ival='1'/>" +
									"</rule>" +
								"</add>" +
							"</recur>" +
						"</inv>" +
						"<mp content-type='text/plain'>" +
							"<content>"+ apptBody +"</content>" +
						"</mp>" +
						"<su>"+ apptSubject +"</su>" +
					"</m>" +
				"</CreateAppointmentRequest>");

        
        //-- GUI actions
        
        
		// Delete instance and verify corresponding UI
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);
		
        app.zPageCalendar.zListItem(Action.A_LEFTCLICK, apptSubject);
        
        DialogWarning dialogSeriesOrInstance = (DialogWarning)app.zPageCalendar.zListItem(Action.A_RIGHTCLICK, Button.O_DELETE, apptSubject);
        dialogSeriesOrInstance.zClickButton(Button.B_DELETE_THIS_INSTANCE);
        DialogWarning confirmDelete = (DialogWarning)dialogSeriesOrInstance.zClickButton(Button.B_OK);
        confirmDelete.zClickButton(Button.B_YES);
        
        
        
        //-- Verification
        
		// On the server, verify the appointment is in the trash
		app.zGetActiveAccount().soapSend(
				"<SearchRequest xmlns='urn:zmailMail' types='appointment' calExpandInstStart='"+ startTime.addDays(-7).toMillis() +"' calExpandInstEnd='"+ endTime.addDays(7).toMillis() +"'>"
			+		"<query>is:anywhere "+ apptSubject +"</query>"
			+	"</SearchRequest>");

		// http://bugzilla.zmail.com/show_bug.cgi?id=63412 - "Deleting instance from calendar series does not allow for user restoration from the Trash can"
		// http://bugzilla.zmail.com/show_bug.cgi?id=13527#c4 - "Moving an instance from one cal to other, moves complete series"
		// For now, nothing should be returned in the SearchResponse
		//		
		//		String folderID = app.zGetActiveAccount().soapSelectValue("//mail:appt", "l");
		//		ZAssert.assertEquals(
		//				folderID,
		//				FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Trash).getId(),
		//				"Verify appointment is in the trash folder");

		Element[] appts = app.zGetActiveAccount().soapSelectNodes("//mail:appt");
		ZAssert.assertEquals(appts.length, 0, "Verify the appt element does not exist ... See also bug 63412");

		// Verify the appointment is not in the GUI view
		//ZAssert.assertEquals(app.zPageCalendar.sIsElementPresent(app.zPageCalendar.zGetApptLocator(apptSubject)), false, "Verify instance is deleted from the calendar");
		boolean deleted = app.zPageCalendar.zWaitForElementDeleted(app.zPageCalendar.zGetApptLocator(apptSubject), "10000");
		ZAssert.assertEquals(deleted, true, "Verify instance is deleted from the calendar");	

	}
	
	@DataProvider(name = "DataProviderShortcutKeys")
	public Object[][] DataProviderShortcutKeys() {
		return new Object[][] {
				new Object[] { "VK_DELETE", KeyEvent.VK_DELETE },
				new Object[] { "VK_BACK_SPACE", KeyEvent.VK_BACK_SPACE },
		};
	}

	@Bugs(ids = "69132")
	@Test(
			description = "Delete instance of series appointment (every week) using keyboard shortcuts Del & Backspace",
			groups = { "functional" },
			dataProvider = "DataProviderShortcutKeys" )
	public void DeleteInstance_06(String name, int keyEvent) throws HarnessException {
		
		//-- Data Setup
		
		
		
		// Appointment data
		String tz, apptSubject, apptBody;
		tz = ZTimeZone.TimeZoneEST.getID();
		apptSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		apptBody = "body" + ZmailSeleniumProperties.getUniqueString();
		
		// Absolute dates in UTC zone
		Calendar now = this.calendarWeekDayUTC;
		ZDate startTime = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endTime   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);
		
		app.zGetActiveAccount().soapSend(
				"<CreateAppointmentRequest xmlns='urn:zmailMail'>" +
					"<m>"+
						"<inv method='REQUEST' type='event' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"+
							"<s d='"+ startTime.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
							"<e d='"+ endTime.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
							"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<recur>" +
								"<add>" +
									"<rule freq='MON'>" +
										"<interval ival='1'/>" +
									"</rule>" +
								"</add>" +
							"</recur>" +
						"</inv>" +
						"<mp content-type='text/plain'>" +
							"<content>"+ apptBody +"</content>" +
						"</mp>" +
						"<su>"+ apptSubject +"</su>" +
					"</m>" +
				"</CreateAppointmentRequest>");

        
        //-- GUI actions
        
        
		// Delete instance and verify corresponding UI
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);
		
        app.zPageCalendar.zListItem(Action.A_LEFTCLICK, apptSubject);
        
        DialogWarning dialogSeriesOrInstance = (DialogWarning)app.zPageCalendar.zKeyboardKeyEvent(keyEvent);
        dialogSeriesOrInstance.zClickButton(Button.B_DELETE_THIS_INSTANCE);
        DialogWarning confirmDelete = (DialogWarning)dialogSeriesOrInstance.zClickButton(Button.B_OK);
        confirmDelete.zClickButton(Button.B_YES);
        
        
        
        //-- Verification
        
		// On the server, verify the appointment is in the trash
		app.zGetActiveAccount().soapSend(
				"<SearchRequest xmlns='urn:zmailMail' types='appointment' calExpandInstStart='"+ startTime.addDays(-7).toMillis() +"' calExpandInstEnd='"+ endTime.addDays(7).toMillis() +"'>"
			+		"<query>is:anywhere "+ apptSubject +"</query>"
			+	"</SearchRequest>");

		// http://bugzilla.zmail.com/show_bug.cgi?id=63412 - "Deleting instance from calendar series does not allow for user restoration from the Trash can"
		// http://bugzilla.zmail.com/show_bug.cgi?id=13527#c4 - "Moving an instance from one cal to other, moves complete series"
		// For now, nothing should be returned in the SearchResponse
		//		
		//		String folderID = app.zGetActiveAccount().soapSelectValue("//mail:appt", "l");
		//		ZAssert.assertEquals(
		//				folderID,
		//				FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Trash).getId(),
		//				"Verify appointment is in the trash folder");

		Element[] appts = app.zGetActiveAccount().soapSelectNodes("//mail:appt");
		ZAssert.assertEquals(appts.length, 0, "Verify the appt element does not exist ... See also bug 63412");

		// Verify the appointment is not in the GUI view
		//ZAssert.assertEquals(app.zPageCalendar.sIsElementPresent(app.zPageCalendar.zGetApptLocator(apptSubject)), false, "Verify instance is deleted from the calendar");
		boolean deleted = app.zPageCalendar.zWaitForElementDeleted(app.zPageCalendar.zGetApptLocator(apptSubject), "10000");
		ZAssert.assertEquals(deleted, true, "Verify instance is deleted from the calendar");	
        
	}
	
}