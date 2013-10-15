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
package org.zmail.qa.selenium.projects.ajax.tests.calendar.meetings.attendee.invitations.conversation;

import java.util.*;

import org.testng.annotations.Test;
import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.*;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;

public class AcceptMeeting extends PrefGroupMailByConversationTest {

	public AcceptMeeting() {
		logger.info("New "+ AcceptMeeting.class.getCanonicalName());
		super.startingPage = app.zPageMail;
	}

	/**
	 * ZmailAccount.AccountA() sends a two-hour appointment to app.zGetActiveAccount()
	 * with subject and start time
	 * @param subject
	 * @param start
	 * @throws HarnessException 
	 */
	private void SendCreateAppointmentRequest(String subject, ZDate start) throws HarnessException {
				
		ZmailAccount.AccountA().soapSend(
				"<CreateAppointmentRequest xmlns='urn:zmailMail'>"
				+		"<m>"
				+			"<inv method='REQUEST' type='event' status='CONF' draft='0' class='PUB' fb='B' transp='O' allDay='0' name='"+ subject +"'>"
				+				"<s d='"+ start.toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<e d='"+ start.addHours(2).toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<or a='"+ ZmailAccount.AccountA().EmailAddress +"'/>"
				+				"<at role='REQ' ptst='NE' rsvp='1' a='" + app.zGetActiveAccount().EmailAddress + "'/>"
				+			"</inv>"
				+			"<e a='"+ app.zGetActiveAccount().EmailAddress +"' t='t'/>"
				+			"<su>"+ subject +"</su>"
				+			"<mp content-type='text/plain'>"
				+				"<content>content</content>"
				+			"</mp>"
				+		"</m>"
				+	"</CreateAppointmentRequest>");        

	}
	
	@Bugs(ids = "69132")
	@Test(
			description = "Accept a meeting using Accept button from invitation message", 
			groups = { "smoke" })
	public void AcceptMeeting_01() throws HarnessException {

		// ------------------------ Test data ------------------------------------

		String apptSubject = "appointment" + ZmailSeleniumProperties.getUniqueString();

		Calendar now = Calendar.getInstance();
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);



		// --------------- Creating invitation (organizer) ----------------------------

		ZmailAccount.AccountA().soapSend(
				"<CreateAppointmentRequest xmlns='urn:zmailMail'>"
				+		"<m>"
				+			"<inv method='REQUEST' type='event' status='CONF' draft='0' class='PUB' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"
				+				"<s d='"+ startUTC.toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<e d='"+ endUTC.toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<or a='"+ ZmailAccount.AccountA().EmailAddress +"'/>"
				+				"<at role='REQ' ptst='NE' rsvp='1' a='" + app.zGetActiveAccount().EmailAddress + "'/>"
				+			"</inv>"
				+			"<e a='"+ app.zGetActiveAccount().EmailAddress +"' t='t'/>"
				+			"<su>"+ apptSubject +"</su>"
				+			"<mp content-type='text/plain'>"
				+				"<content>content</content>"
				+			"</mp>"
				+		"</m>"
				+	"</CreateAppointmentRequest>");



		// --------------- Login to attendee & accept invitation ----------------------------------------------------

		// Refresh the view
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the invitation
		DisplayMail display = (DisplayMail)app.zPageMail.zListItem(Action.A_LEFTCLICK, apptSubject);
		
		// Click Accept
		display.zPressButton(Button.B_ACCEPT);



		// ---------------- Verification at organizer & invitee side both -------------------------------------       


		// --- Check that the organizer shows the attendee as "ACCEPT" ---

		// Organizer: Search for the appointment (InvId)
		ZmailAccount.AccountA().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='appointment' calExpandInstStart='"+ startUTC.addDays(-10).toMillis() +"' calExpandInstEnd='"+ endUTC.addDays(10).toMillis() +"'>"
				+		"<query>"+ apptSubject +"</query>"
				+	"</SearchRequest>");
		
		String organizerInvId = ZmailAccount.AccountA().soapSelectValue("//mail:appt", "invId");

		// Get the appointment details
		ZmailAccount.AccountA().soapSend(
					"<GetAppointmentRequest  xmlns='urn:zmailMail' id='"+ organizerInvId +"'/>");
		
		String attendeeStatus = ZmailAccount.AccountA().soapSelectValue("//mail:at[@a='"+ app.zGetActiveAccount().EmailAddress +"']", "ptst");

		// Verify attendee status shows as psts=AC
		ZAssert.assertEquals(attendeeStatus, "AC", "Verify that the attendee shows as 'ACCEPTED'");


		// --- Check that the attendee showing status as "ACCEPT" ---

		// Attendee: Search for the appointment (InvId)
		app.zGetActiveAccount().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='appointment' calExpandInstStart='"+ startUTC.addDays(-10).toMillis() +"' calExpandInstEnd='"+ endUTC.addDays(10).toMillis() +"'>"
				+		"<query>"+ apptSubject +"</query>"
				+	"</SearchRequest>");
		
		String attendeeInvId = app.zGetActiveAccount().soapSelectValue("//mail:appt", "invId");

		// Get the appointment details
		app.zGetActiveAccount().soapSend(
					"<GetAppointmentRequest  xmlns='urn:zmailMail' id='"+ attendeeInvId +"'/>");
		
		String myStatus = app.zGetActiveAccount().soapSelectValue("//mail:at[@a='"+ app.zGetActiveAccount().EmailAddress +"']", "ptst");

		// Verify attendee status shows as psts=AC
		ZAssert.assertEquals(myStatus, "AC", "Verify that the attendee shows as 'ACCEPTED'");

	}

	@Bugs(ids = "69132")
	@Test(
			description = "Accept meeting - Verify organizer gets notification message", 
			groups = { "functional" })
	public void AcceptMeeting_02() throws HarnessException {

		// ------------------------ Test data ------------------------------------

		String apptSubject = "appointment" + ZmailSeleniumProperties.getUniqueString();
		Calendar now = Calendar.getInstance();
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);


		// --------------- Creating invitation (organizer) ----------------------------
		
		this.SendCreateAppointmentRequest(apptSubject, startUTC);

		
		// --------------- Login to attendee & accept invitation ----------------------------------------------------

		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the invitation
		DisplayMail display = (DisplayMail)app.zPageMail.zListItem(Action.A_LEFTCLICK, apptSubject);
		
		// Click Accept
		display.zPressButton(Button.B_ACCEPT);


		// ---------------- Verification at organizer & invitee side both -------------------------------------       


		// --- Check that the organizer shows the attendee as "ACCEPT" ---

		// Organizer: Search for the appointment response
		String inboxId = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Inbox).getId();
		
		ZmailAccount.AccountA().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='message'>"
				+		"<query>inid:"+ inboxId +" subject:("+ apptSubject +")</query>"
				+	"</SearchRequest>");
		
		String messageId = ZmailAccount.AccountA().soapSelectValue("//mail:m", "id");

		// Get the appointment details
		ZmailAccount.AccountA().soapSend(
					"<GetMsgRequest  xmlns='urn:zmailMail'>"
				+		"<m id='"+ messageId +"'/>"
				+	"</GetMsgRequest>");

	}

	@Bugs(ids = "69132")
	@Test(
			description = "Accept meeting using 'Accept -> Notify Organizer'", 
			groups = { "functional" })
	public void AcceptMeeting_03() throws HarnessException {

		// ------------------------ Test data ------------------------------------

		String apptSubject = "appointment" + ZmailSeleniumProperties.getUniqueString();

		Calendar now = Calendar.getInstance();
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);


		// --------------- Creating invitation (organizer) ----------------------------

		ZmailAccount.AccountA().soapSend(
				"<CreateAppointmentRequest xmlns='urn:zmailMail'>"
				+		"<m>"
				+			"<inv method='REQUEST' type='event' status='CONF' draft='0' class='PUB' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"
				+				"<s d='"+ startUTC.toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<e d='"+ endUTC.toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<or a='"+ ZmailAccount.AccountA().EmailAddress +"'/>"
				+				"<at role='REQ' ptst='NE' rsvp='1' a='" + app.zGetActiveAccount().EmailAddress + "'/>"
				+			"</inv>"
				+			"<e a='"+ app.zGetActiveAccount().EmailAddress +"' t='t'/>"
				+			"<su>"+ apptSubject +"</su>"
				+			"<mp content-type='text/plain'>"
				+				"<content>content</content>"
				+			"</mp>"
				+		"</m>"
				+	"</CreateAppointmentRequest>");        



		// --------------- Login to attendee & accept invitation ----------------------------------------------------

		// Refresh the view
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the invitation
		DisplayMail display = (DisplayMail)app.zPageMail.zListItem(Action.A_LEFTCLICK, apptSubject);

		// Click Accept > Notify Organizer
		display.zPressButtonPulldown(Button.B_ACCEPT, Button.O_ACCEPT_NOTIFY_ORGANIZER);

		// ---------------- Verification at organizer & invitee side both -------------------------------------       


		// --- Check that the organizer shows the attendee as "ACCEPT" ---

		// Organizer: Search for the appointment (InvId)
		ZmailAccount.AccountA().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='appointment' calExpandInstStart='"+ startUTC.addDays(-10).toMillis() +"' calExpandInstEnd='"+ endUTC.addDays(10).toMillis() +"'>"
				+		"<query>"+ apptSubject +"</query>"
				+	"</SearchRequest>");
		
		String organizerInvId = ZmailAccount.AccountA().soapSelectValue("//mail:appt", "invId");

		// Get the appointment details
		ZmailAccount.AccountA().soapSend(
					"<GetAppointmentRequest  xmlns='urn:zmailMail' id='"+ organizerInvId +"'/>");
		
		String attendeeStatus = ZmailAccount.AccountA().soapSelectValue("//mail:at[@a='"+ app.zGetActiveAccount().EmailAddress +"']", "ptst");

		// Verify attendee status shows as psts=AC
		ZAssert.assertEquals(attendeeStatus, "AC", "Verify that the attendee shows as 'ACCEPTED'");


		// --- Check that the attendee showing status as "ACCEPT" ---

		// Attendee: Search for the appointment (InvId)
		app.zGetActiveAccount().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='appointment' calExpandInstStart='"+ startUTC.addDays(-10).toMillis() +"' calExpandInstEnd='"+ endUTC.addDays(10).toMillis() +"'>"
				+		"<query>"+ apptSubject +"</query>"
				+	"</SearchRequest>");
		
		String attendeeInvId = app.zGetActiveAccount().soapSelectValue("//mail:appt", "invId");

		// Get the appointment details
		app.zGetActiveAccount().soapSend(
					"<GetAppointmentRequest  xmlns='urn:zmailMail' id='"+ attendeeInvId +"'/>");
		
		String myStatus = app.zGetActiveAccount().soapSelectValue("//mail:at[@a='"+ app.zGetActiveAccount().EmailAddress +"']", "ptst");

		// Verify attendee status shows as psts=AC
		ZAssert.assertEquals(myStatus, "AC", "Verify that the attendee shows as 'ACCEPTED'");

		// Organizer: Search for the appointment response
		String inboxId = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Inbox).getId();
		
		ZmailAccount.AccountA().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='message'>"
				+		"<query>inid:"+ inboxId +" subject:("+ apptSubject +")</query>"
				+	"</SearchRequest>");
		
		String messageId = ZmailAccount.AccountA().soapSelectValue("//mail:m", "id");

		// Get the appointment details
		ZmailAccount.AccountA().soapSend(
					"<GetMsgRequest  xmlns='urn:zmailMail'>"
				+		"<m id='"+ messageId +"'/>"
				+	"</GetMsgRequest>");
		
	}
	
	@Bugs(ids = "69132")
	@Test(
			description = "Accept meeting using 'Accept -> Edit Reply' and verify modified content", 
			groups = { "functional" })
	public void AcceptMeeting_04() throws HarnessException {

		// ------------------------ Test data ------------------------------------

		String apptSubject = "appointment" + ZmailSeleniumProperties.getUniqueString();
		String modifiedBody = "modified" + ZmailSeleniumProperties.getUniqueString();

		Calendar now = Calendar.getInstance();
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);


		// --------------- Creating invitation (organizer) ----------------------------

		ZmailAccount.AccountA().soapSend(
				"<CreateAppointmentRequest xmlns='urn:zmailMail'>"
				+		"<m>"
				+			"<inv method='REQUEST' type='event' status='CONF' draft='0' class='PUB' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"
				+				"<s d='"+ startUTC.toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<e d='"+ endUTC.toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<or a='"+ ZmailAccount.AccountA().EmailAddress +"'/>"
				+				"<at role='REQ' ptst='NE' rsvp='1' a='" + app.zGetActiveAccount().EmailAddress + "'/>"
				+			"</inv>"
				+			"<e a='"+ app.zGetActiveAccount().EmailAddress +"' t='t'/>"
				+			"<su>"+ apptSubject +"</su>"
				+			"<mp content-type='text/plain'>"
				+				"<content>content</content>"
				+			"</mp>"
				+		"</m>"
				+	"</CreateAppointmentRequest>");        



		// --------------- Login to attendee & accept invitation ----------------------------------------------------

		// Refresh the view
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Select the invitation
		DisplayMail display = (DisplayMail)app.zPageMail.zListItem(Action.A_LEFTCLICK, apptSubject);

		// Click Accept -> Edit Reply , which will open a new reply compose
        FormMailNew editReply = (FormMailNew)display.zPressButtonPulldown(Button.B_ACCEPT, Button.O_ACCEPT_EDIT_REPLY);
        editReply.zFillField(Field.Body, modifiedBody);
        editReply.zSubmit();
		
		// ---------------- Verification at organizer & invitee side both -------------------------------------       


		// --- Check that the organizer shows the attendee as "ACCEPT" ---

		// Organizer: Search for the appointment (InvId)
		ZmailAccount.AccountA().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='appointment' calExpandInstStart='"+ startUTC.addDays(-10).toMillis() +"' calExpandInstEnd='"+ endUTC.addDays(10).toMillis() +"'>"
				+		"<query>"+ apptSubject +"</query>"
				+	"</SearchRequest>");
		
		String organizerInvId = ZmailAccount.AccountA().soapSelectValue("//mail:appt", "invId");

		// Get the appointment details
		ZmailAccount.AccountA().soapSend(
					"<GetAppointmentRequest  xmlns='urn:zmailMail' id='"+ organizerInvId +"'/>");
		
		String attendeeStatus = ZmailAccount.AccountA().soapSelectValue("//mail:at[@a='"+ app.zGetActiveAccount().EmailAddress +"']", "ptst");

		// Verify attendee status shows as psts=AC
		ZAssert.assertEquals(attendeeStatus, "AC", "Verify that the attendee shows as 'ACCEPTED'");


		// --- Check that the attendee showing status as "ACCEPT" ---

		// Attendee: Search for the appointment (InvId)
		app.zGetActiveAccount().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='appointment' calExpandInstStart='"+ startUTC.addDays(-10).toMillis() +"' calExpandInstEnd='"+ endUTC.addDays(10).toMillis() +"'>"
				+		"<query>"+ apptSubject +"</query>"
				+	"</SearchRequest>");
		
		String attendeeInvId = app.zGetActiveAccount().soapSelectValue("//mail:appt", "invId");

		// Get the appointment details
		app.zGetActiveAccount().soapSend(
					"<GetAppointmentRequest  xmlns='urn:zmailMail' id='"+ attendeeInvId +"'/>");
		
		String myStatus = app.zGetActiveAccount().soapSelectValue("//mail:at[@a='"+ app.zGetActiveAccount().EmailAddress +"']", "ptst");

		// Verify attendee status shows as psts=AC
		ZAssert.assertEquals(myStatus, "AC", "Verify that the attendee shows as 'ACCEPTED'");

		// Organizer: Search for the appointment response
		String inboxId = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Inbox).getId();
		
		ZmailAccount.AccountA().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='message'>"
				+		"<query>inid:"+ inboxId +" subject:("+ apptSubject +")</query>"
				+	"</SearchRequest>");
		
		// Verify message body content
		String body = ZmailAccount.AccountA().soapSelectValue("//mail:m/mail:fr", null);
		ZAssert.assertStringContains(body, modifiedBody, "Verify modified body value");
		
	}
	
	@Bugs(ids = "69132")
	@Test(
			description = "Accept meeting using 'Accept -> Don't Notify Organizer'", 
			groups = { "functional" })
	public void AcceptMeeting_05() throws HarnessException {

		// ------------------------ Test data ------------------------------------

		String apptSubject = "appointment" + ZmailSeleniumProperties.getUniqueString();

		Calendar now = Calendar.getInstance();
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 14, 0, 0);


		// --------------- Creating invitation (organizer) ----------------------------

		ZmailAccount.AccountA().soapSend(
				"<CreateAppointmentRequest xmlns='urn:zmailMail'>"
				+		"<m>"
				+			"<inv method='REQUEST' type='event' status='CONF' draft='0' class='PUB' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"
				+				"<s d='"+ startUTC.toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<e d='"+ endUTC.toTimeZone(ZTimeZone.TimeZoneEST.getID()).toYYYYMMDDTHHMMSS() +"' tz='"+ ZTimeZone.TimeZoneEST.getID() +"'/>"
				+				"<or a='"+ ZmailAccount.AccountA().EmailAddress +"'/>"
				+				"<at role='REQ' ptst='NE' rsvp='1' a='" + app.zGetActiveAccount().EmailAddress + "'/>"
				+			"</inv>"
				+			"<e a='"+ app.zGetActiveAccount().EmailAddress +"' t='t'/>"
				+			"<su>"+ apptSubject +"</su>"
				+			"<mp content-type='text/plain'>"
				+				"<content>content</content>"
				+			"</mp>"
				+		"</m>"
				+	"</CreateAppointmentRequest>");        



		// --------------- Login to attendee & accept invitation ----------------------------------------------------

		// Refresh the view
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the invitation
		DisplayMail display = (DisplayMail)app.zPageMail.zListItem(Action.A_LEFTCLICK, apptSubject);

		// Click Accept > Don't Notify Organizer
		display.zPressButtonPulldown(Button.B_ACCEPT, Button.O_ACCEPT_DONT_NOTIFY_ORGANIZER);

		// ---------------- Verification at organizer & invitee side both -------------------------------------       


		// --- Check that the organizer shows the attendee as "ACCEPT" ---

		// Organizer: Search for the appointment (InvId)
		ZmailAccount.AccountA().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='appointment' calExpandInstStart='"+ startUTC.addDays(-10).toMillis() +"' calExpandInstEnd='"+ endUTC.addDays(10).toMillis() +"'>"
				+		"<query>"+ apptSubject +"</query>"
				+	"</SearchRequest>");
		
		String organizerInvId = ZmailAccount.AccountA().soapSelectValue("//mail:appt", "invId");

		// Get the appointment details
		ZmailAccount.AccountA().soapSend(
					"<GetAppointmentRequest  xmlns='urn:zmailMail' id='"+ organizerInvId +"'/>");
		
		String attendeeStatus = ZmailAccount.AccountA().soapSelectValue("//mail:at[@a='"+ app.zGetActiveAccount().EmailAddress +"']", "ptst");

		// Verify attendee status shows as psts=NE (bug 65356)
		ZAssert.assertEquals(attendeeStatus, "NE", "Verify that the attendee shows as 'ACCEPTED'");


		// --- Check that the attendee showing status as "ACCEPT" ---

		// Attendee: Search for the appointment (InvId)
		app.zGetActiveAccount().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='appointment' calExpandInstStart='"+ startUTC.addDays(-10).toMillis() +"' calExpandInstEnd='"+ endUTC.addDays(10).toMillis() +"'>"
				+		"<query>"+ apptSubject +"</query>"
				+	"</SearchRequest>");
		
		String attendeeInvId = app.zGetActiveAccount().soapSelectValue("//mail:appt", "invId");

		// Get the appointment details
		app.zGetActiveAccount().soapSend(
					"<GetAppointmentRequest  xmlns='urn:zmailMail' id='"+ attendeeInvId +"'/>");
		
		String myStatus = app.zGetActiveAccount().soapSelectValue("//mail:at[@a='"+ app.zGetActiveAccount().EmailAddress +"']", "ptst");

		// Verify attendee status shows as psts=AC
		ZAssert.assertEquals(myStatus, "AC", "Verify that the attendee shows as 'ACCEPTED'");

		// Organizer: Search for the appointment response
		String inboxId = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Inbox).getId();
		
		ZmailAccount.AccountA().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='message'>"
				+		"<query>inid:"+ inboxId +" subject:("+ apptSubject +")</query>"
				+	"</SearchRequest>");
		
		app.zGetActiveAccount().soapSend(
				"<SearchRequest xmlns='urn:zmailMail' types='message'>"
			+		"<query>inid:"+ inboxId +" subject:("+ apptSubject +")</query>"
			+	"</SearchRequest>");

		Element[] nodes = app.zGetActiveAccount().soapSelectNodes("//mail:m");
		ZAssert.assertEquals(nodes.length, 0, "Verify appointment notification message is not present");
		
	}
}
