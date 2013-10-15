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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.addressbook;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.RecipientItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;

public class ZmailPrefAutoAddAddressEnabledFalse extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public ZmailPrefAutoAddAddressEnabledFalse() {
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{				
				put("zmailPrefAutoAddAddressEnabled", "FALSE");
			}
		};
	}

	/**
	 * Test case : Opt-out Add New Contacts To emailed contact
	 * Verify receiver' addresses of out-going mails not added to "Emailed Contacts" folder automatically 
	 * @throws HarnessException
	 */
	@Test(description = " send message to 1 receiver, the address should not be added into Emailed Contact", groups = { "smoke" })
	public void SendEmailTo1Receiver() throws HarnessException {

		ZmailAccount receiver = new ZmailAccount();
		receiver.provision();
		receiver.authenticate();
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(receiver));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFill(mail);
		
		// Send the message
		mailform.zSubmit();

		app.zGetActiveAccount().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='contact'>"
				+		"<query>"+ receiver.EmailAddress +"</query>"
				+	"</SearchRequest>");
		
		Element[] nodes = app.zGetActiveAccount().soapSelectNodes("//mail:cn");
		ZAssert.assertEquals(nodes.length, 0, "Verify that no contact is created in the mailbox");

	}


	/**
	 * Test case : Opt-out Add New Contacts To emailed contact
	 * Verify receiver' addresses of out-going mails not added to "Emailed Contacts" folder automatically 
	 * @throws HarnessException
	 */
	@Test(description = " send message to 2 receivers, the address should not be added into Emailed Contact", groups = { "functional" })
	public void SendEmailTo2Receivers() throws HarnessException {

		ZmailAccount receiver1 = new ZmailAccount();
		receiver1.provision();
		receiver1.authenticate();
		
		ZmailAccount receiver2 = new ZmailAccount();
		receiver2.provision();
		receiver2.authenticate();
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(receiver1));
		mail.dToRecipients.add(new RecipientItem(receiver2));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFill(mail);
		
		// Send the message
		mailform.zSubmit();

		app.zGetActiveAccount().soapSend(
				"<SearchRequest xmlns='urn:zmailMail' types='contact'>"
			+		"<query>"+ receiver1.EmailAddress +"</query>"
			+	"</SearchRequest>");

		Element[] nodes1 = app.zGetActiveAccount().soapSelectNodes("//mail:cn");
		ZAssert.assertEquals(nodes1.length, 0, "Verify that no contact is created in the mailbox");

		app.zGetActiveAccount().soapSend(
				"<SearchRequest xmlns='urn:zmailMail' types='contact'>"
			+		"<query>"+ receiver2.EmailAddress +"</query>"
			+	"</SearchRequest>");

		Element[] nodes2 = app.zGetActiveAccount().soapSelectNodes("//mail:cn");
		ZAssert.assertEquals(nodes2.length, 0, "Verify that no contact is created in the mailbox");

	}

	
}
