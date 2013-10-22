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
package com.zimbra.qa.selenium.projects.desktop.tests.calendar.appointments;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.desktop.core.AjaxCommonTest;


public class DeleteAppointment extends AjaxCommonTest {

	
	public DeleteAppointment() {
		logger.info("New "+ DeleteAppointment.class.getCanonicalName());
		
		// All tests start at the Calendar page
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = null;


	}
	
	@Test(	description = "Delete an appointment - Right click -> Delete",
			groups = { "smoke" })
	public void DeleteAppointment_01() throws HarnessException {
		
		// Create the message data to be sent
		String subject = "appointment" + ZimbraSeleniumProperties.getUniqueString();
		String content = "content" + ZimbraSeleniumProperties.getUniqueString();
		ZDate startUTC = new ZDate(2014, 12, 25, 12, 0, 0);
		ZDate endUTC   = new ZDate(2014, 12, 25, 14, 0, 0);

		// Create an appointment
		app.zGetActiveAccount().soapSend(
					"<CreateAppointmentRequest xmlns='urn:zimbraMail'>" +
						"<m>" +
							"<inv>" +
								"<comp status='CONF' fb='B' class='PUB' transp='O' allDay='0' name='"+ subject +"' >" +
									"<s d='"+ startUTC.toYYYYMMDDTHHMMSSZ() +"'/>" +
									"<e d='"+ endUTC.toYYYYMMDDTHHMMSSZ() +"'/>" +
									"<or a='"+ app.zGetActiveAccount().EmailAddress + "'/>" +
								"</comp>" +
							"</inv>" +
							"<su>"+ subject + "</su>" +
							"<mp ct='text/plain'>" +
							"<content>"+ content +"</content>" +
							"</mp>" +
						"</m>" +
					"</CreateAppointmentRequest>");
		

		
	}



}
