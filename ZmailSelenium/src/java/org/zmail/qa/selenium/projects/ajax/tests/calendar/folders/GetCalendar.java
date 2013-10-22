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
package org.zmail.qa.selenium.projects.ajax.tests.calendar.folders;

import java.util.*;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.*;


public class GetCalendar extends CalendarWorkWeekTest {

	public GetCalendar() {
		logger.info("New "+ GetCalendar.class.getCanonicalName());
		super.startingPage = app.zPageCalendar;
	}
	
	@Test(	description = "Get a calendar (under USER_ROOT)",
			groups = { "smoke" })
	public void GetCalendar_01() throws HarnessException {
		
		FolderItem root = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.UserRoot);
		ZAssert.assertNotNull(root, "Verify the inbox is available");
		
		// Create the subfolder
		String name = "calendar" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ name +"' l='"+ root.getId() +"' view='appointment'/>" +
                "</CreateFolderRequest>");

		FolderItem subfolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), name);
		ZAssert.assertNotNull(subfolder, "Verify the subfolder is available");
		
		
		// Click on Get Mail to refresh the folder list
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		
		// Verify the folder appears in the list
		List<FolderItem> folders = app.zTreeCalendar.zListGetFolders();
		
		FolderItem found = null;
		for (FolderItem f : folders) {
			if ( name.equals(f.getName()) ) {
				found = f;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the calendar was in the tree");

		
	}	

	@Test(	description = "Get a calendar (under subcalendar)",
			groups = { "functional" })
	public void GetCalendar_02() throws HarnessException {
		
		FolderItem root = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.UserRoot);
		ZAssert.assertNotNull(root, "Verify the inbox is available");
		
		// Create a subfolder
		String name1 = "calendar" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ name1 +"' l='"+ root.getId() +"' view='appointment'/>" +
                "</CreateFolderRequest>");

		FolderItem subfolder1 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name1);
		ZAssert.assertNotNull(subfolder1, "Verify the subfolder is available");
		
		// Create a subfolder of the subfolder
		String name2 = "calendar" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ name2 +"' l='"+ subfolder1.getId() +"' view='appointment'/>" +
                "</CreateFolderRequest>");

		FolderItem subfolder2 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name2);
		ZAssert.assertNotNull(subfolder2, "Verify the subfolder is available");
		
		
		// Click on Get Mail to refresh the folder list
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		
		// Verify the folder appears in the list
		List<FolderItem> folders = app.zTreeCalendar.zListGetFolders();
		
		FolderItem found1 = null;
		FolderItem found2 = null;
		for (FolderItem f : folders) {
			if ( name1.equals(f.getName()) ) {
				found1 = f;
			}
			if ( name2.equals(f.getName()) ) {
				found2 = f;
			}
		}
		ZAssert.assertNotNull(found1, "Verify the calendar was in the tree");
		ZAssert.assertNotNull(found2, "Verify the sub-calendar was in the tree");

		
	}	

	@Bugs(ids = "28846")
	@Test(	description = "Get a calendar (under subcalendar) - verify appointments appear",
			groups = { "functional" })
	public void GetCalendar_03() throws HarnessException {
		
		FolderItem root = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.UserRoot);
		ZAssert.assertNotNull(root, "Verify the inbox is available");
		
		// Create a subfolder
		String name1 = "calendar" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ name1 +"' l='"+ root.getId() +"' view='appointment'/>" +
                "</CreateFolderRequest>");

		FolderItem subfolder1 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name1);
		ZAssert.assertNotNull(subfolder1, "Verify the subfolder is available");
		
		app.zGetActiveAccount().soapSend(
				"<FolderActionRequest xmlns='urn:zmailMail'>" +
                	"<action op='check' id='"+ subfolder1.getId() +"'/>" +
                "</FolderActionRequest>");
		
		// Create a subfolder of the subfolder
		String name2 = "calendar" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ name2 +"' l='"+ subfolder1.getId() +"' view='appointment'/>" +
                "</CreateFolderRequest>");

		FolderItem subfolder2 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name2);
		ZAssert.assertNotNull(subfolder2, "Verify the subfolder is available");
		
		app.zGetActiveAccount().soapSend(
				"<FolderActionRequest xmlns='urn:zmailMail'>" +
                	"<action op='check' id='"+ subfolder2.getId() +"'/>" +
                "</FolderActionRequest>");
		
		
		// Create the appointment in the subfolder
		String subject = "appointment" + ZmailSeleniumProperties.getUniqueString();
		String location = "location" + ZmailSeleniumProperties.getUniqueString();
		String content = "content" + ZmailSeleniumProperties.getUniqueString();
		
		// Absolute dates in UTC zone
		Calendar now = this.calendarWeekDayUTC;
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);
		
		// EST timezone string
		String tz = ZTimeZone.TimeZoneEST.getID();

		// Create an appointment in the subcalendar
		app.zGetActiveAccount().soapSend(
					"<CreateAppointmentRequest xmlns='urn:zmailMail'>"
				+		"<m l='"+ subfolder2.getId() +"'>"
				+			"<inv>"
				+				"<comp status='CONF' fb='B' class='PUB' transp='O' allDay='0' name='"+ subject +"' loc='"+ location +"' >"
				+					"<s d='"+ startUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>"
				+					"<e d='"+ endUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>"
				+					"<or a='"+ app.zGetActiveAccount().EmailAddress + "'/>"
				+				"</comp>"
				+			"</inv>"
				+			"<su>"+ subject + "</su>"
				+			"<mp ct='text/plain'>"
				+				"<content>"+ content +"</content>"
				+			"</mp>"
				+		"</m>"
				+	"</CreateAppointmentRequest>");
		

		// Click on Get Mail to refresh the folder list
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		
		//-- Verification
		
	    //verify appt displayed in workweek view
		boolean found = false;
		List<AppointmentItem> items = app.zPageCalendar.zListGetAppointments();
		for (AppointmentItem item : items ) {
			if ( subject.equals(item.getSubject()) ) {
				found = true;
				break;
			}
		}
		
		ZAssert.assertTrue(found, "Verify appt gets displayed in work week view");

		
	}	


}
