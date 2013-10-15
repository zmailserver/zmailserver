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

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.AutocompleteEntry;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class Spanish extends PrefGroupMailByMessageTest {


	
	
	public Spanish() {
		logger.info("New "+ Spanish.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		super.startingAccountPreferences.put("zmailPrefGalAutoCompleteEnabled", "TRUE");
	
	}
	
	@Bugs(ids = "48736")
	@Test(	description = "Autocomplete using Spanish characters in the name - local contact",
			groups = { "functional" })
	public void AutoComplete_01() throws HarnessException {
		
		// Create a contact
		ZmailAccount contact = new ZmailAccount();
		contact.provision();
		contact.authenticate();

		// String firstname = "Ñáéíóúñ";
		String firstname = "Ñéál";
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, firstname);
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(contact.EmailAddress) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(contact, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}

	@Bugs(ids = "48736")
	@Test(	description = "Autocomplete using Spanish characters in the name - GAL contact",
			groups = { "functional" })
	public void AutoComplete_02() throws HarnessException {
		
		// Create a contact
		ZmailAccount contact = new ZmailAccount();
		contact.setPref("givenName", "Ñáéíóúñ" + ZmailSeleniumProperties.getUniqueString());
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, contact.getPref("givenName"));
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(contact.EmailAddress) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(contact, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}


	@Bugs(ids = "65065")
	@Test(	description = "Autocomplete using Spanish characters in the name - local contact",
			groups = { "functional" })
	public void AutoComplete_03() throws HarnessException {
		
		// Create a contact
		ZmailAccount contact = new ZmailAccount();
		contact.provision();
		contact.authenticate();

		// String firstname = "Ñáéíóúñ";
		String firstname = "Ñéál";
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, firstname.substring(0, 3)); // Autocomplete on Ñéá 
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(contact.EmailAddress) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(contact, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}

}
