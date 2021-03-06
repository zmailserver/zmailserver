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
package org.zmail.qa.selenium.projects.ajax.tests.mail.compose.autocomplete;

import java.util.List;

import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.*;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class AutoCompleteSharedContacts extends PrefGroupMailByMessageTest {


	private ZmailAccount Owner = null;
	private FolderItem OwnerFolder = null;
	private ZmailAccount Contact = null;
	private String ContactFirstName = null;
	private String ContactLastName = null;
	
	public AutoCompleteSharedContacts() {
		logger.info("New "+ AutoCompleteSharedContacts.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		super.startingAccountPreferences.put("zmailPrefSharedAddrBookAutoCompleteEnabled", "TRUE");
	
	}
	
	@BeforeMethod(alwaysRun = true)
	/**
	 * Create the shared contacts folder
	 */
	public void CreateSharedContacts() throws HarnessException {
		
		Contact = new ZmailAccount();
		Contact.provision();
		Contact.authenticate();
		
		Owner = new ZmailAccount();
		Owner.provision();
		Owner.authenticate();
		
		
		ContactFirstName = "Jayden" + ZmailSeleniumProperties.getUniqueString();
		ContactLastName = "Brown" + ZmailSeleniumProperties.getUniqueString();
		String ownerFolderName = "folder" + ZmailSeleniumProperties.getUniqueString();
		String mountpointName = "mountpoint" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Create a folder to share
		Owner.soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + ownerFolderName + "' view='contact' l='" + FolderItem.importFromSOAP(Owner, FolderItem.SystemFolder.Contacts).getId() + "'/>"
				+	"</CreateFolderRequest>");
		
		OwnerFolder = FolderItem.importFromSOAP(Owner, ownerFolderName);
		
		// Share it
		Owner.soapSend(
					"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ OwnerFolder.getId() +"' op='grant'>"
				+			"<grant d='"+ app.zGetActiveAccount().EmailAddress +"' gt='usr' perm='r'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		
		// Add a contact to it
		Owner.soapSend(
				"<CreateContactRequest xmlns='urn:zmailMail'>"
			+		"<cn l='"+ OwnerFolder.getId() +"'>"
			+			"<a n='firstName'>"+ ContactFirstName +"</a>"
			+			"<a n='lastName'>"+ ContactLastName +"</a>"
			+			"<a n='email'>"+ Contact.EmailAddress +"</a>"
			+		"</cn>"
			+	"</CreateContactRequest>");
	
		
		// Mount it
		app.zGetActiveAccount().soapSend(
					"<CreateMountpointRequest xmlns='urn:zmailMail'>"
				+		"<link l='1' name='"+ mountpointName +"'  rid='"+ OwnerFolder.getId() +"' zid='"+ Owner.ZmailId +"'/>"
				+	"</CreateMountpointRequest>");
		
		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointName);
		ZAssert.assertNotNull(mountpoint, "Verify the mountpoint exists");

		
		
	}

	
	@Test(	description = "Autocomplete using a Shared Contact - First Name",
			groups = { "functional" })
	public void AutoCompleteSharedContacts_01() throws HarnessException {
		
		
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, ContactFirstName);
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(Contact.EmailAddress) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(Contact, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}

	@Test(	description = "Autocomplete using the Shared Contacts - Partial First Name",
			groups = { "functional" })
	public void AutoCompleteSharedContacts_02() throws HarnessException {
		
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, ContactFirstName.substring(0, 5));
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(Contact.EmailAddress) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(Contact, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}

	@Test(	description = "Autocomplete using a Shared Contact - Last Name",
			groups = { "functional" })
	public void AutoCompleteSharedContacts_03() throws HarnessException {
		
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, ContactLastName);
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(Contact.EmailAddress) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(Contact, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}

	@Test(	description = "Autocomplete using a Shared Contact - Partial Last Name",
			groups = { "functional" })
	public void AutoCompleteSharedContacts_04() throws HarnessException {
		
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, ContactLastName.substring(0, 5));
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(Contact.EmailAddress) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(Contact, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}

	@Test(	description = "Autocomplete using a Shared Contact - Full Name",
			groups = { "functional" })
	public void AutoCompleteSharedContacts_05() throws HarnessException {
		
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, ContactFirstName + " " + ContactLastName);
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(Contact.EmailAddress) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(Contact, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}

	@Test(	description = "Autocomplete using a Shared Contact - First Name and Last Initial",
			groups = { "functional" })
	public void AutoCompleteSharedContacts_07() throws HarnessException {
		
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, ContactFirstName + " " + ContactLastName.substring(0, 1));
		AutocompleteEntry found = null;
		for (AutocompleteEntry entry : entries) {
			if ( entry.getAddress().contains(Contact.EmailAddress) ) {
				found = entry;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the autocomplete entry exists in the returned list");
		mailform.zAutocompleteSelectItem(found);
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(Contact, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}


	@Test(	description = "Autocomplete using a Shared Contact - Multiple Matches",
			groups = { "functional" })
	public void AutoCompleteSharedContacts_08() throws HarnessException {
		int count = 3;
		
		String firstname = "William" + ZmailSeleniumProperties.getUniqueString();
		for (int i = 0; i < count; i++) {
			
			// Create a contact
			ZmailAccount contact = new ZmailAccount();
			contact.provision();
			contact.authenticate();

			String lastname = "Jones" + ZmailSeleniumProperties.getUniqueString();
			
			Owner.soapSend(
						"<CreateContactRequest xmlns='urn:zmailMail'>"
					+		"<cn l='"+ OwnerFolder.getId() +"'>"
					+			"<a n='firstName'>"+ firstname +"</a>"
					+			"<a n='lastName'>"+ lastname +"</a>"
					+			"<a n='email'>"+ contact.EmailAddress +"</a>"
					+		"</cn>"
					+	"</CreateContactRequest>");
			
		}
		
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, firstname);
		
		ZAssert.assertEquals(entries.size(), count, "Verify the correct number of results were returned");
		
		mailform.zAutocompleteSelectItem(entries.get(1));
		
		// Send the message
		mailform.zSubmit();

		
	}

	@Test(	description = "Autocomplete using a Shared Contact - No Matches",
			groups = { "functional" })
	public void AutoCompleteSharedContacts_09() throws HarnessException {
		
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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, "nomatchstring");
		
		ZAssert.assertEquals(entries.size(), 0, "Verify zero results were returned");
				
		DialogWarning dialog = (DialogWarning)mailform.zToolbarPressButton(Button.B_CANCEL);
		if ( dialog.zIsActive() ) {
			dialog.zClickButton(Button.B_NO);
		}
		
	}


}
