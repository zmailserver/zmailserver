/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013 VMware, Inc.
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
package org.zmail.qa.selenium.projects.ajax.tests.mail.compose.autocomplete;

import java.util.List;

import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.*;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class AutoCompleteSearchTerms extends PrefGroupMailByMessageTest {

	
	
	public AutoCompleteSearchTerms() throws HarnessException {
		logger.info("New "+ AutoCompleteGAL.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		super.startingAccountPreferences.put("zmailPrefGalAutoCompleteEnabled", "TRUE");
	
	}
	
	@Bugs(	ids = "46718")
	@Test(	description = "Autocomplete using stop word - IT",
			groups = { "functional" })
	public void AutCompleteSearchTerms_01() throws HarnessException {
		
		// See bug 46718
		String StopWordsFirstName = "It" + ZmailSeleniumProperties.getUniqueString();
		String StopWordsLastName = "Be" + ZmailSeleniumProperties.getUniqueString();


		ZmailAccount StopWordsAccount = new ZmailAccount();
		StopWordsAccount.setPref("givenName", StopWordsFirstName);
		StopWordsAccount.setPref("sn", StopWordsLastName);
		StopWordsAccount.setPref("displayName", StopWordsFirstName + " " + StopWordsLastName);
		StopWordsAccount.provision();
		StopWordsAccount.authenticate();


		// Message properties
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String body = "body" + ZmailSeleniumProperties.getUniqueString();
		
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFillField(Field.Subject, subject);
		mailform.zFillField(Field.Body, body);

		// Auto complete a name
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, "It");
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(StopWordsAccount.EmailAddress) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(StopWordsAccount, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}


	@Bugs(	ids = "46950")
	@Test(	description = "Autocomplete using search term - And",
			groups = { "functional" })
	public void AutCompleteSearchTerms_02() throws HarnessException {
		
		// See bug 46950
		String QueryWordsFirstName = "Andrew" + ZmailSeleniumProperties.getUniqueString();
		String QueryWordsLastName = "Subject" + ZmailSeleniumProperties.getUniqueString();

		ZmailAccount QueryWordsAccount = new ZmailAccount();
		QueryWordsAccount.setPref("givenName", QueryWordsFirstName);
		QueryWordsAccount.setPref("sn", QueryWordsLastName);
		QueryWordsAccount.setPref("displayName", QueryWordsFirstName + " " + QueryWordsLastName);
		QueryWordsAccount.provision();
		QueryWordsAccount.authenticate();


		// Message properties
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String body = "body" + ZmailSeleniumProperties.getUniqueString();
		
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFillField(Field.Subject, subject);
		mailform.zFillField(Field.Body, body);

		// Auto complete a name
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, "And");
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(QueryWordsAccount.EmailAddress) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(QueryWordsAccount, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}


}
