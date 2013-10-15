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
package org.zmail.qa.selenium.projects.ajax.tests.mail.readreceipt;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;


public class RequestReadReceiptText extends PrefGroupMailByMessageTest {

	public RequestReadReceiptText() {
		logger.info("New "+ RequestReadReceiptText.class.getCanonicalName());
		
		
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		
	}
	
	@Test(	description = "Send a text message requesting a read receipt",
			groups = { "functional" })
	public void CreateMailText_01() throws HarnessException {
		
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZmailAccount.AccountA()));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFill(mail);
		
		// Request a read receipt
		mailform.zToolbarPressPulldown(Button.B_OPTIONS, Button.O_OPTION_REQUEST_READ_RECEIPT);
		
		// Send the message
		mailform.zSubmit();


		
		// Verify the message is received with a read receipt request
		MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ mail.dSubject +")");

		ZmailAccount.AccountA().soapSend(
					"<GetMsgRequest  xmlns='urn:zmailMail'>"
				+		"<m id='"+ received.getId() +"'/>"
				+	"</GetMsgRequest>");
		String requestor = ZmailAccount.AccountA().soapSelectValue("//mail:e[@t='n']", "a");

		ZAssert.assertEquals(requestor, app.zGetActiveAccount().EmailAddress, 
				"Verify the received message requests a read receipt from the test account");
		
	}

	


}
