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
package org.zmail.qa.selenium.projects.ajax.tests.mail.compose.autocomplete.charsets;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class Cyrillic extends PrefGroupMailByMessageTest {


	
	
	public Cyrillic() {
		logger.info("New "+ Cyrillic.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		super.startingAccountPreferences.put("zmailPrefGalAutoCompleteEnabled", "TRUE");
	
	}
	
	@Bugs(ids = "48736")
	@Test(	description = "Autocomplete using Cyrillic characters in the name - local contact",
			groups = { "functional" })
	public void AutoComplete_01() throws HarnessException {
		
		// Create a contact
		ZmailAccount contact = new ZmailAccount();
		contact.provision();
		contact.authenticate();

		
		// Cyrillic: http://jrgraphix.net/r/Unicode/0400-04FF
		String firstname = "\u0422\u0435\u0441\u0442\u043e\u0432\u0430\u044f";
		String lastname = "Wilson";
		
		app.zGetActiveAccount().soapSend(
					"<CreateContactRequest xmlns='urn:zmailMail'>"
				+		"<cn>"
				+			"<a n='firstName'>"+ firstname +"</a>"
				+			"<a n='lastName'>"+ lastname +"</a>"
				+			"<a n='email'>"+ contact.EmailAddress +"</a>"
				+		"</cn>"
				+	"</CreateContactRequest>");
		
		app.zPageMain.zToolbarPressButton(Button.B_REFRESH);
		
		// Message properties
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String body = "body" + ZmailSeleniumProperties.getUniqueString();
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFillField(Field.Subject, subject);
		mailform.zFillField(Field.Body, body);

		// Set the To field
		// Don't use the autocomplete code, since the Cyrillic will be rejected
		//mailform.zFillField(Field.To, firstname);
		//mailform.zAutocompleteFillField(Field.To, ";");
		//workaround
		mailform.zAutocompleteFillField(Field.To, firstname);
		SleepUtil.sleepSmall();
		mailform.sKeyDown("css=div>input[id^=zv__COMPOSE][id$=_to_control]", "\\59");

		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(contact, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}

	@Bugs(ids = "48736")
	@Test(	description = "Autocomplete using Cyrillic characters in the name - GAL contact",
			groups = { "functional" })
	public void AutoComplete_02() throws HarnessException {
		
		// Create a contact
		ZmailAccount contact = new ZmailAccount();
		contact.setPref("givenName", "\u0422\u0435\u0441\u0442\u043e\u0432\u0430\u044f" + ZmailSeleniumProperties.getUniqueString());
		contact.setPref("sn", "Wilson" + ZmailSeleniumProperties.getUniqueString());
		contact.setPref("displayName", contact.getPref("givenName") + " " + contact.getPref("sn"));
		contact.provision();
		contact.authenticate();
		
		app.zPageMain.zToolbarPressButton(Button.B_REFRESH);
		
		// Message properties
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String body = "body" + ZmailSeleniumProperties.getUniqueString();
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFillField(Field.Subject, subject);
		mailform.zFillField(Field.Body, body);

		// Set the To field
		// Don't use the autocomplete code, since the Cyrillic will be rejected
		//mailform.zFillField(Field.To, contact.getPref("givenName"));
		//mailform.zAutocompleteFillField(Field.To, ";");
		//workaround
		mailform.zAutocompleteFillField(Field.To, contact.getPref("givenName"));
		SleepUtil.sleepSmall();
		mailform.sKeyDown("css=div>input[id^=zv__COMPOSE][id$=_to_control]", "\\59");

		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(contact, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}



}
