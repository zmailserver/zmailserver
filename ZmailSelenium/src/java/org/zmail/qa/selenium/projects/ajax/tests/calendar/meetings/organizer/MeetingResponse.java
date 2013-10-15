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
package org.zmail.qa.selenium.projects.ajax.tests.calendar.meetings.organizer;

import java.util.*;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.*;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail;

public class MeetingResponse extends PrefGroupMailByMessageTest {

	public MeetingResponse() {
		logger.info("New "+ MeetingResponse.class.getCanonicalName());
		super.startingPage = app.zPageMail;

	}
	
	@Test(	description = "View the meeting response - Response = Accept",
			groups = { "implement" })
	public void MeetingResponse_01() throws HarnessException {
		
		// Create the appointment on the server
		// Create the message data to be sent
		String subject = "appointment" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Absolute dates in UTC zone
		Calendar now = Calendar.getInstance();
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);
		
		// EST timezone string
		String tz = ZTimeZone.TimeZoneEST.getID();

		// Create an appointment with AccountA
		app.zGetActiveAccount().soapSend(
					"<CreateAppointmentRequest xmlns='urn:zmailMail'>"
				+		"<m>"
				+			"<inv>"
				+				"<comp status='CONF' fb='B' class='PUB' transp='O' allDay='0' name='"+ subject +"' >"
				+					"<s d='"+ startUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>"
				+					"<e d='"+ endUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>"
				+					"<or a='"+ app.zGetActiveAccount().EmailAddress + "'/>"
				+					"<at role='REQ' ptst='NE' rsvp='1' a='" + ZmailAccount.AccountA().EmailAddress + "'/>"
				+				"</comp>"
				+			"</inv>"
				+			"<su>"+ subject + "</su>"
				+			"<e a='"+ ZmailAccount.AccountA().EmailAddress +"' t='t'/>"
				+			"<mp ct='text/plain'>"
				+				"<content>content</content>"
				+			"</mp>"
				+		"</m>"
				+	"</CreateAppointmentRequest>");
		
		// AccountA gets the invitation
		ZmailAccount.AccountA().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='message'>"
				+		"<query>subject:(" + subject +")</query>"
				+	"</SearchRequest>");
		
		String inviteId = ZmailAccount.AccountA().soapSelectValue("//mail:m", "id");
		String inviteCompNum = ZmailAccount.AccountA().soapSelectValue("//mail:comp", "compNum");
		
		// AccountA accepts
		ZmailAccount.AccountA().soapSend(
					"<SendInviteReplyRequest xmlns='urn:zmailMail' id='"+ inviteId +"' compNum='"+ inviteCompNum +"' verb='ACCEPT' updateOrganizer='TRUE'>"
			+			"<m >"
			+				"<e a='"+ app.zGetActiveAccount().EmailAddress +"' t='t'/>"
			+				"<su>Accept: "+ subject +"</su>"
			+				"<mp ct='text/plain'>"
			+					"<content>content</content>"
			+				"</mp>"
			+			"</m>"
			+		"</SendInviteReplyRequest>");
		
		
		
		// Refresh the inbox to get the reply
		app.zPageMail.zToolbarPressButton(Button.B_REFRESH);
		
		DisplayMail display = (DisplayMail)app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		// Wait for a bit so the message can be rendered
		SleepUtil.sleep(5000);

		throw new HarnessException("add verification that the appointment appears");
	    
	}


}
