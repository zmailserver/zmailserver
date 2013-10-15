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

import java.util.*;

import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.*;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class AutoCompleteContactGroup extends PrefGroupMailByMessageTest {


	private ZmailAccount Contact1 = null;
	private String Contact1FirstName = null;
	private String Contact1LastName = null;

	private ZmailAccount Contact2 = null;
	private String Contact2FirstName = null;
	private String Contact2LastName = null;

	private String ContactGroupName = null;

	
	
	public AutoCompleteContactGroup() {
		logger.info("New "+ AutoCompleteContactGroup.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		super.startingAccountPreferences.put("zmailPrefContactsDisableAutocompleteOnContactGroupMembers", "FALSE");
	
	}
	
	@BeforeMethod(alwaysRun = true)
	/**
	 * Create a contact group
	 */
	public void CreateContactGroup() throws HarnessException {
		
		Contact1 = new ZmailAccount();
		Contact1.provision();
		Contact1.authenticate();
		Contact1FirstName = "Alexander" + ZmailSeleniumProperties.getUniqueString();
		Contact1LastName = "Davis" + ZmailSeleniumProperties.getUniqueString();

		Contact2 = new ZmailAccount();
		Contact2.provision();
		Contact2.authenticate();
		Contact2FirstName = "Noah" + ZmailSeleniumProperties.getUniqueString();
		Contact2LastName = "Miller" + ZmailSeleniumProperties.getUniqueString();

		
		ContactGroupName = "Apple" + ZmailSeleniumProperties.getUniqueString();
		
		// Create a contact
		app.zGetActiveAccount().soapSend(
				"<CreateContactRequest xmlns='urn:zmailMail'>"
			+		"<cn >"
			+			"<a n='firstName'>"+ Contact1FirstName +"</a>"
			+			"<a n='lastName'>"+ Contact1LastName +"</a>"
			+			"<a n='email'>"+ Contact1.EmailAddress +"</a>"
			+		"</cn>"
			+	"</CreateContactRequest>");
		String contact1id = app.zGetActiveAccount().soapSelectValue("//mail:cn", "id");
	
		// Create a contact
		app.zGetActiveAccount().soapSend(
				"<CreateContactRequest xmlns='urn:zmailMail'>"
			+		"<cn >"
			+			"<a n='firstName'>"+ Contact2FirstName +"</a>"
			+			"<a n='lastName'>"+ Contact2LastName +"</a>"
			+			"<a n='email'>"+ Contact2.EmailAddress +"</a>"
			+		"</cn>"
			+	"</CreateContactRequest>");
		String contact2id = app.zGetActiveAccount().soapSelectValue("//mail:cn", "id");


		// Create a contact group
		app.zGetActiveAccount().soapSend(
				"<CreateContactRequest xmlns='urn:zmailMail'>"
			+		"<cn >"
			+			"<a n='type'>group</a>"
			+			"<a n='fileAs'>8:" + ContactGroupName + "</a>"
			+			"<a n='nickname'>"+ ContactGroupName +"</a>"
			+			"<m type='C' value='"+ contact1id +"'/>"
			+			"<m type='C' value='"+ contact2id +"'/>"
			+		"</cn>"
			+	"</CreateContactRequest>");

		
		app.zPageMain.zToolbarPressButton(Button.B_REFRESH);

	}

	@Test(	description = "Autocomplete using a Contact Group - Group Name",
			groups = { "functional" })
	public void AutoCompleteContactGroups_01() throws HarnessException {
		
		// Contact group is created in @BeforeMethod CreateContactGroup()
		
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, ContactGroupName);
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(ContactGroupName) ) {
				found = entry;
				break;
			}
		}
		
		

		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(Contact1, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
		received = MailItem.importFromSOAP(Contact2, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");

	}

	@Test(	description = "Autocomplete using the Contacts - Partial First Name",
			groups = { "functional" })
	public void AutoCompleteContactGroups_02() throws HarnessException {
		
		// Message properties
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String body = "body" + ZmailSeleniumProperties.getUniqueString();
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFillField(Field.Subject, subject);
		mailform.zFillField(Field.Body, body);

		// Auto complete a name ... first 5 characters
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, ContactGroupName.substring(0, 5));
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(ContactGroupName) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(Contact1, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
		received = MailItem.importFromSOAP(Contact2, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}


	@Test(	description = "Autocomplete using a Contact - Multiple Matches",
			groups = { "functional" })
	public void AutoCompleteContactGroups_08() throws HarnessException {
		
		// Create 3 contact groups, all starting with groupPrefix
		String groupPrefix = "Exxon";
		
		// Group 1
		String group1name = groupPrefix + ZmailSeleniumProperties.getUniqueString();
		
		ZmailAccount contact1 = new ZmailAccount();
		contact1.provision();
		contact1.authenticate();
		
		app.zGetActiveAccount().soapSend(
				"<CreateContactRequest xmlns='urn:zmailMail'>"
			+		"<cn >"
			+			"<a n='firstName'>Kenneth"+ ZmailSeleniumProperties.getUniqueString() +"</a>"
			+			"<a n='lastName'>Clark"+ ZmailSeleniumProperties.getUniqueString() +"</a>"
			+			"<a n='email'>"+ contact1.EmailAddress +"</a>"
			+		"</cn>"
			+	"</CreateContactRequest>");
		String contact1id = app.zGetActiveAccount().soapSelectValue("//mail:cn", "id");

		app.zGetActiveAccount().soapSend(
				"<CreateContactRequest xmlns='urn:zmailMail'>"
			+		"<cn >"
			+			"<a n='type'>group</a>"
			+			"<a n='fileAs'>8:" + group1name + "</a>"
			+			"<a n='nickname'>"+ group1name +"</a>"
			+			"<m type='C' value='"+ contact1id +"'/>"
			+		"</cn>"
			+	"</CreateContactRequest>");

		// Group 2
		String group2name = groupPrefix + ZmailSeleniumProperties.getUniqueString();
		
		ZmailAccount contact2 = new ZmailAccount();
		contact2.provision();
		contact2.authenticate();
		
		app.zGetActiveAccount().soapSend(
				"<CreateContactRequest xmlns='urn:zmailMail'>"
			+		"<cn >"
			+			"<a n='firstName'>Steven"+ ZmailSeleniumProperties.getUniqueString() +"</a>"
			+			"<a n='lastName'>Rodriguez"+ ZmailSeleniumProperties.getUniqueString() +"</a>"
			+			"<a n='email'>"+ contact2.EmailAddress +"</a>"
			+		"</cn>"
			+	"</CreateContactRequest>");
		String contact2id = app.zGetActiveAccount().soapSelectValue("//mail:cn", "id");

		app.zGetActiveAccount().soapSend(
				"<CreateContactRequest xmlns='urn:zmailMail'>"
			+		"<cn >"
			+			"<a n='type'>group</a>"
			+			"<a n='fileAs'>8:" + group2name + "</a>"
			+			"<a n='nickname'>"+ group2name +"</a>"
			+			"<m type='C' value='"+ contact2id +"'/>"
			+		"</cn>"
			+	"</CreateContactRequest>");

		// Group 3
		String group3name = groupPrefix + ZmailSeleniumProperties.getUniqueString();
		
		ZmailAccount contact3 = new ZmailAccount();
		contact3.provision();
		contact3.authenticate();
		
		app.zGetActiveAccount().soapSend(
				"<CreateContactRequest xmlns='urn:zmailMail'>"
			+		"<cn >"
			+			"<a n='firstName'>Edward"+ ZmailSeleniumProperties.getUniqueString() +"</a>"
			+			"<a n='lastName'>Lewis"+ ZmailSeleniumProperties.getUniqueString() +"</a>"
			+			"<a n='email'>"+ contact3.EmailAddress +"</a>"
			+		"</cn>"
			+	"</CreateContactRequest>");
		String contact3id = app.zGetActiveAccount().soapSelectValue("//mail:cn", "id");

		app.zGetActiveAccount().soapSend(
				"<CreateContactRequest xmlns='urn:zmailMail'>"
			+		"<cn >"
			+			"<a n='type'>group</a>"
			+			"<a n='fileAs'>8:" + group3name + "</a>"
			+			"<a n='nickname'>"+ group3name +"</a>"
			+			"<m type='C' value='"+ contact3id +"'/>"
			+		"</cn>"
			+	"</CreateContactRequest>");

		
		
		// Refresh the app to get the new contacts
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

		// Auto complete a name
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, groupPrefix);
		
		ZAssert.assertEquals(entries.size(), 3, "Verify the correct number of results were returned");
		
		mailform.zAutocompleteSelectItem(entries.get(1));
		
		// Send the message
		mailform.zSubmit();

	}



}
