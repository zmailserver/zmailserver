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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.mail.signatures;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.RecipientItem;
import org.zmail.qa.selenium.framework.items.SignatureItem;

import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;

import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;

public class ComposeMsgWithTextSignature extends AjaxCommonTest {
	String sigName = "signame" + ZmailSeleniumProperties.getUniqueString();
	String sigBody = "sigbody" + ZmailSeleniumProperties.getUniqueString();

	@SuppressWarnings("serial")
	public ComposeMsgWithTextSignature() {
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefComposeFormat", "text");
			}
		};
	}

	@BeforeClass(groups = { "always" })
	public void CreateSignature() throws HarnessException {
		System.out.println(this.sigName);
		ZmailAccount.AccountZWC().authenticate(
				SOAP_DESTINATION_HOST_TYPE.SERVER);
		ZmailAccount.AccountZWC().soapSend(
				"<CreateSignatureRequest xmlns='urn:zmailAccount'>"
						+ "<signature name='" + this.sigName + "' >"
						+ "<content type='text/plain'>" + this.sigBody
						+ "</content>" + "</signature>"
						+ "</CreateSignatureRequest>");

	}

	/**
	 * Test case : Create signature through soap Compose text message and add
	 * text signature Send mail to self and verify signature through soap.
	 * 
	 * @throws HarnessException
	 */
	@Test(description = " Compose Msg with text signature and Verify signature thropugh soap", groups = { "functional" })
	public void ComposeMsgWithTextSignature_01() throws HarnessException {

		// Signature is created
		SignatureItem signature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), this.sigName);
		ZAssert.assertEquals(signature.getName(), this.sigName,"verified Text Signature is created");

		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZmailAccount.AccountZWC()));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();

		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");

		// Fill out the form with the data
		mailform.zFill(mail);
		 //click Signature drop down and add signature
		app.zPageMail.zToolbarPressPulldown(Button.B_OPTIONS,Button.O_ADD_SIGNATURE,this.sigName);
	
		// Send the message
		mailform.zSubmit();

		MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountZWC(),"in:inbox subject:(" + mail.dSubject + ")");

		logger.debug("===========received is: " + received);
		logger.debug("===========app is: " + app);
		
		//Verify TO, Subject, Body,Signature

		ZAssert.assertEquals(received.dFromRecipient.dEmailAddress, app.zGetActiveAccount().EmailAddress,"Verify the from field is correct");
		ZAssert.assertEquals(received.dToRecipients.get(0).dEmailAddress,ZmailAccount.AccountZWC().EmailAddress,"Verify the to field is correct");
		ZAssert.assertEquals(received.dSubject, mail.dSubject,"Verify the subject field is correct");
		ZAssert.assertStringContains(received.dBodyText, mail.dBodyText,"Verify the body content is correct");
		ZAssert.assertStringContains(received.dBodyText, this.sigBody,"Verify the signature is correct");

	}
}
