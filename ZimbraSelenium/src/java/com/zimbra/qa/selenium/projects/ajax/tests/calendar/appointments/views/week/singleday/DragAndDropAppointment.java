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
package com.zimbra.qa.selenium.projects.ajax.tests.calendar.appointments.views.week.singleday;

import java.util.Calendar;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.*;


public class DragAndDropAppointment extends CalendarWorkWeekTest {

	
	public DragAndDropAppointment() {
		logger.info("New "+ DragAndDropAppointment.class.getCanonicalName());
		
		// All tests start at the Calendar page
		super.startingPage = app.zPageCalendar;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = -2913827779459595178L;
		{
		    put("zimbraPrefCalendarInitialView", "week");
		}};
	}
	
	@Test(	description = "Drag and Drop a appointment from calendar to different calendar in week view",
			groups = { "smoke" })
	public void DragAndDropAppointment_01() throws HarnessException {

		String foldername = "folder"+ ZimbraSeleniumProperties.getUniqueString();

		// Create a calendar to move the appointment into
		//
		FolderItem rootFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.UserRoot);
		
		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zimbraMail'>" +
						"<folder name='" + foldername +"' l='"+ rootFolder.getId() +"' view='appointment'/>" +
					"</CreateFolderRequest>");
		FolderItem subcalendarFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);
		
		//Refresh view after folder creation
	        app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		// Creating objects for appointment data
		String tz = ZTimeZone.TimeZoneEST.getID();
		String apptSubject = ZimbraSeleniumProperties.getUniqueString();
		String apptBody = ZimbraSeleniumProperties.getUniqueString();
		
		// Absolute dates in UTC zone
		Calendar now = this.calendarWeekDayUTC;
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);
		
        app.zGetActiveAccount().soapSend(
    			"<CreateAppointmentRequest xmlns='urn:zimbraMail'>"
    		+		"<m>"
    		+			"<inv method='REQUEST' type='event' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"
    		+				"<s d='"+ startUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>"
    		+				"<e d='"+ endUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>"
    		+				"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" 
    		+			"</inv>" 
    		+			"<mp content-type='text/plain'>" 
    		+				"<content>" + apptBody + "</content>" 
    		+			"</mp>"
    		+			"<su>" + apptSubject + "</su>" 
    		+		"</m>" 
    		+	"</CreateAppointmentRequest>");
        String apptId = app.zGetActiveAccount().soapSelectValue("//mail:CreateAppointmentResponse", "apptId");
        
        
        
        //-- GUI actions
        
		// Click Refresh		
        app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		
		// Select the item
		app.zPageCalendar.zDragAndDrop(
					"css=div[id^='zli__CLW__"+ apptId +"'] td.appt_name", // <div id="zli__CLWW__263_DWT114" .../>
					"css=td[id='zti__main_Calendar__"+ subcalendarFolder.getId() + "_textCell']"); // <div id="zti__main_Calendar__273_textCell" .../>
		
		
		
		
		//-- Server verification
		AppointmentItem newAppointment = AppointmentItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ apptSubject +")");

		ZAssert.assertEquals(newAppointment.getFolder(), subcalendarFolder.getId(), "Verify the appointment moved folders");
		
	}

	@Test(	description = "Drag and Drop a appointment from one time to a different time in week view",
			groups = { "smoke" })
	public void DragAndDropAppointment_02() throws HarnessException {


		// We need to create two appointments to make a locator to drag and drop to.
		// Steps:
		// 1. Create appointment1
		// 2. Create appointment2 an hour later
		// 3. Drag and drop appointment1 to appointment2
		// 4. Verify appointment1 happens at appointment2 time
		
		// Creating objects for appointment data
		String tz = ZTimeZone.TimeZoneEST.getID();
		String apptSubject = ZimbraSeleniumProperties.getUniqueString();
		String apptBody = ZimbraSeleniumProperties.getUniqueString();
		
		// Absolute dates in UTC zone
		Calendar now = this.calendarWeekDayUTC;
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 13, 0, 0);
		
        app.zGetActiveAccount().soapSend(
    			"<CreateAppointmentRequest xmlns='urn:zimbraMail'>"
    		+		"<m>"
    		+			"<inv method='REQUEST' type='event' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"
    		+				"<s d='"+ startUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>"
    		+				"<e d='"+ endUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>"
    		+				"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" 
    		+			"</inv>" 
    		+			"<mp content-type='text/plain'>" 
    		+				"<content>" + apptBody + "</content>" 
    		+			"</mp>"
    		+			"<su>" + apptSubject + "</su>" 
    		+		"</m>" 
    		+	"</CreateAppointmentRequest>");
        String apptId = app.zGetActiveAccount().soapSelectValue("//mail:CreateAppointmentResponse", "apptId");
        
        app.zGetActiveAccount().soapSend(
        		"<GetAppointmentRequest id='"+ apptId + "' xmlns='urn:zimbraMail'/>");
        String s = app.zGetActiveAccount().soapSelectValue("//mail:s", "d");
        String e = app.zGetActiveAccount().soapSelectValue("//mail:e", "d");

        //Refresh view after Appointment creation
        app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		String otherSubject = ZimbraSeleniumProperties.getUniqueString();
		ZDate otherStartUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);
		ZDate otherEndUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 15, 0, 0);
        app.zGetActiveAccount().soapSend(
    			"<CreateAppointmentRequest xmlns='urn:zimbraMail'>"
    		+		"<m>"
    		+			"<inv method='REQUEST' type='event' fb='B' transp='O' allDay='0' name='"+ otherSubject +"'>"
    		+				"<s d='"+ otherStartUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>"
    		+				"<e d='"+ otherEndUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>"
    		+				"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" 
    		+			"</inv>" 
    		+			"<mp content-type='text/plain'>" 
    		+				"<content>" + ZimbraSeleniumProperties.getUniqueString() + "</content>" 
    		+			"</mp>"
    		+			"<su>" + otherSubject + "</su>" 
    		+		"</m>" 
    		+	"</CreateAppointmentRequest>");
        String otherApptId = app.zGetActiveAccount().soapSelectValue("//mail:CreateAppointmentResponse", "apptId");

        
        //-- GUI actions
        
		// Click Refresh		
        app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		
		// drag and drop the item
        	String sourceLocator = "css=div[id^='zli__CLW__"+ apptId +"'] td.appt_name";
        	String destinationLocator = "css=div[id^='zli__CLW__"+ otherApptId +"'] td.appt_name";

        	app.zPageCalendar.zDragAndDropBy(sourceLocator,destinationLocator,0,10);
        		
		//-- Server verification
		
		// Make sure the time has changed
		// (It is difficult to know for certain what time is correct.  For
		// now, just make sure it was moved somewhere.)
        app.zGetActiveAccount().soapSend(
        		"<GetAppointmentRequest id='"+ apptId + "' xmlns='urn:zimbraMail'/>");
        String s1 = app.zGetActiveAccount().soapSelectValue("//mail:s", "d");
        String e2 = app.zGetActiveAccount().soapSelectValue("//mail:e", "d");

		ZAssert.assertStringDoesNotContain(s1, s, "Verify the start time changed");
		ZAssert.assertStringDoesNotContain(e2, e, "Verify the end time changed");
		
	}

}
