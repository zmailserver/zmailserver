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

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.ClientSessionFactory;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.AutocompleteEntry;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class AutoCompleteQuickCompleteKeys extends PrefGroupMailByMessageTest {


	private static String FirstName = "James" + ZmailSeleniumProperties.getUniqueString();
	private static String LastName = "Smith" + ZmailSeleniumProperties.getUniqueString();
	private static ZmailAccount SampleAccount = null;
	
	public AutoCompleteQuickCompleteKeys() throws HarnessException {
		logger.info("New "+ AutoCompleteQuickCompleteKeys.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		super.startingAccountPreferences.put("zmailPrefGalAutoCompleteEnabled", "TRUE");
		super.startingAccountPreferences.put("zmailPrefAutoCompleteQuickCompletionOnComma", "TRUE");


	}
	
	@Test(	description = "Type comma (',') to automatically accept autocomplete",
			groups = { "functional" })
	public void AutoCompleteQuickCompleteKeys_01() throws HarnessException {
		
		if ( SampleAccount == null ) {
			
			SampleAccount = new ZmailAccount();
			SampleAccount.setPref("displayName", FirstName + " " + LastName);
			SampleAccount.provision();
			SampleAccount.authenticate();
			
		}


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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, FirstName);
		ZAssert.assertGreaterThan(entries.size(), 0, "Verify some results are returned");
		//app.zPageMail.zKeyboardTypeString(",");
		//workaround
		SleepUtil.sleepSmall();
		mailform.sKeyDown("css=div>input[id^=zv__COMPOSE][id$=_to_control]", "\\188");
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(SampleAccount, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}

	@Test(	description = "Type semicolon (';') to automatically accept autocomplete",
			groups = { "functional" })
	public void AutoCompleteQuickCompleteKeys_02() throws HarnessException {
		
		if ( SampleAccount == null ) {
			
			SampleAccount = new ZmailAccount();
			SampleAccount.setPref("displayName", FirstName + " " + LastName);
			SampleAccount.provision();
			SampleAccount.authenticate();
			
		}


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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, FirstName);
		ZAssert.assertGreaterThan(entries.size(), 0, "Verify some results are returned");
		//app.zPageMail.zKeyboardTypeString(";");
		//workaround
		SleepUtil.sleepSmall();
		mailform.sKeyDown("css=div>input[id^=zv__COMPOSE][id$=_to_control]", "\\59");
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(SampleAccount, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}

	@Test(	description = "Type tab ('	') to automatically accept autocomplete",
			groups = { "functional" })
	public void AutoCompleteQuickCompleteKeys_03() throws HarnessException {
		
		if ( SampleAccount == null ) {
			
			SampleAccount = new ZmailAccount();
			SampleAccount.setPref("displayName", FirstName + " " + LastName);
			SampleAccount.provision();
			SampleAccount.authenticate();
			
		}


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
		List<AutocompleteEntry> entries = mailform.zAutocompleteFillField(Field.To, FirstName);
		ZAssert.assertGreaterThan(entries.size(), 0, "Verify some results are returned");
		//app.zPageMail.zKeyboardTypeString("	"); // Whitespace is 'tab' character
		//workaround
		SleepUtil.sleepSmall();
		mailform.sKeyDown("css=div>input[id^=zv__COMPOSE][id$=_to_control]", "\\9");
		
		// Send the message
		mailform.zSubmit();

		
		// Log into the destination account and make sure the message is received
		MailItem received = MailItem.importFromSOAP(SampleAccount, "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received correctly");
		
	}


}
