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

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.SignatureItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;

public class ForwardMsgWithTextSignature extends AjaxCommonTest {
	String sigName = "signame" + ZmailSeleniumProperties.getUniqueString();
	String sigBody = "sigbody" + ZmailSeleniumProperties.getUniqueString();

	@SuppressWarnings("serial")
	public ForwardMsgWithTextSignature() {
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefComposeFormat", "text");
				put("zmailPrefGroupMailBy", "message");
			}
		};
	}

	@BeforeClass(groups = { "always" })
	public void CreateSignature() throws HarnessException {
		System.out.println(this.sigName);
		ZmailAccount.AccountZWC().authenticate(SOAP_DESTINATION_HOST_TYPE.SERVER);
		ZmailAccount.AccountZWC().soapSend(
				"<CreateSignatureRequest xmlns='urn:zmailAccount'>"
				+ "<signature name='" + this.sigName + "' >"
				+ "<content type='text/plain'>" + this.sigBody
				+ "</content>" + "</signature>"
				+ "</CreateSignatureRequest>");

	}

	/**
	 * Test case : Forward Msg with text signature and Verify signature through soap
	 * Create signature through soap 
	 * Send message with text signature through soap
	 * Fwd same message to another account say (accountB())
	 * Verify signature in forwarded msg through soap
	 * @throws HarnessException
	 */
	@Test(description = " Forward Msg with text signature and Verify signature through soap", groups = { "functional" })
	public void ForwardMsgWithTextSignature_01() throws HarnessException {

		// Signature is created
		FolderItem inboxFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),SystemFolder.Inbox);
		SignatureItem signature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), this.sigName);
		ZAssert.assertEquals(signature.getName(), this.sigName,"verified Text Signature is created");

		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();


		// Send a message to the account(self)
		ZmailAccount.AccountZWC().soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
				"<m>" +
				"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
				"<su>"+ subject +"</su>" +
				"<mp ct='text/plain'>" +
				"<content>content"+ ZmailSeleniumProperties.getUniqueString() + "\n\n"+signature.dBodyText+"\n</content>" +
				"</mp>" +
				"</m>" +
		"</SendMsgRequest>");



		// Get the mail item for the new message
		MailItem mail = MailItem.importFromSOAP(ZmailAccount.AccountZWC(),"in:inbox subject:(" + subject + ")");

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, inboxFolder);

		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

		// Forward the item
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_FORWARD);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");

		// Fill out the form with the data
		mailform.zFillField(Field.To, ZmailAccount.AccountB().EmailAddress);

		// Send the message
		mailform.zSubmit();

		MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountB(), "subject:("+ mail.dSubject +")");
		logger.debug("===========received is: " + received);
		logger.debug("===========app is: " + app);

		//Verify TO, Subject, Body,Signature
		ZAssert.assertStringContains(received.dSubject, "Fwd", "Verify the subject field contains the 'Fwd' prefix");
		ZAssert.assertEquals(received.dFromRecipient.dEmailAddress, app.zGetActiveAccount().EmailAddress,"Verify the from field is correct");
		ZAssert.assertEquals(received.dToRecipients.get(0).dEmailAddress,ZmailAccount.AccountB().EmailAddress,"Verify the to field is correct");
		ZAssert.assertStringContains(received.dBodyText, mail.dBodyText,"Verify the body content is correct");
		ZAssert.assertStringContains(received.dBodyText, this.sigBody,"Verify the signature is correct");

	}
}
