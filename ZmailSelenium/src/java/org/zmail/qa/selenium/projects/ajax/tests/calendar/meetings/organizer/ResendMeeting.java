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
package org.zmail.qa.selenium.projects.ajax.tests.calendar.meetings.organizer;

import java.util.Calendar;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.AppointmentItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.CalendarWorkWeekTest;

public class ResendMeeting extends CalendarWorkWeekTest {	
	
	
	public ResendMeeting() {
		logger.info("New "+ ResendMeeting.class.getCanonicalName());
		super.startingPage = app.zPageCalendar;
	}
	
	
	
	
	@Bugs(ids = "21181")
	@Test(description = "Resend a meeting invite using context menu",
			groups = { "smoke" })
	public void ResendMeeting_01() throws HarnessException {
		
		
		//-- Data setup
		
		
		// Creating a meeting
		String tz = ZTimeZone.TimeZoneEST.getID();
		String subject = ZmailSeleniumProperties.getUniqueString();
		
		// Absolute dates in UTC zone
		Calendar now = this.calendarWeekDayUTC;
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);
		
		app.zGetActiveAccount().soapSend(
                "<CreateAppointmentRequest xmlns='urn:zmailMail'>" +
                     "<m>"+
                     	"<inv method='REQUEST' type='event' status='CONF' draft='0' class='PUB' fb='B' transp='O' allDay='0' name='"+ subject +"'>"+
                     		"<s d='"+ startUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
                     		"<e d='"+ endUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
                     		"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
                     		"<at role='REQ' ptst='NE' rsvp='1' a='" + ZmailAccount.AccountA().EmailAddress + "' d='2'/>" + 
                     	"</inv>" +
                     	"<e a='"+ ZmailAccount.AccountA().EmailAddress +"' t='t'/>" +
                     	"<mp content-type='text/plain'>" +
                     		"<content>"+ ZmailSeleniumProperties.getUniqueString() +"</content>" +
                     	"</mp>" +
                     "<su>"+ subject +"</su>" +
                     "</m>" +
               "</CreateAppointmentRequest>");
        
		// Delete the invite from the attendee
		ZmailAccount.AccountA().soapSend(
				"<SearchRequest xmlns='urn:zmailMail' types='message'>"
			+		"<query>subject:("+ subject +")</query>"
			+	"</SearchRequest>");
		String id = ZmailAccount.AccountA().soapSelectValue("//mail:m", "id");
		
		ZmailAccount.AccountA().soapSend(
				"<ItemActionRequest  xmlns='urn:zmailMail'>"
			+		"<action id='"+ id +"' op='delete'/>"
			+	"</ItemActionRequest>");

		
		//-- GUI actions
		
		
        // Refresh the view
        app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);
        
        // Select the appointment
        app.zPageCalendar.zListItem(Action.A_LEFTCLICK, subject);
        
        // Right Click -> Re-invite context menu
       app.zPageCalendar.zListItem(Action.A_RIGHTCLICK, Button.O_REINVITE, subject);

		
		
		//-- Verification
		
		
		// Verify the new invitation appears in the inbox
		ZmailAccount.AccountA().soapSend(
				"<SearchRequest xmlns='urn:zmailMail' types='message'>"
			+		"<query>subject:("+ subject +")</query>"
			+	"</SearchRequest>");
		id = ZmailAccount.AccountA().soapSelectValue("//mail:m", "id");
		ZAssert.assertNotNull(id, "Verify the new invitation appears in the attendee's inbox");
		
		ZmailAccount.AccountA().soapSend(
				"<GetMsgRequest  xmlns='urn:zmailMail'>"
			+		"<m id='"+ id +"'/>"
			+	"</GetMsgRequest>");

		// Verify only one appointment is in the calendar
		AppointmentItem a = AppointmentItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ subject + ")");
		ZAssert.assertNotNull(a, "Verify only one appointment matches in the calendar");


	}
	


}
