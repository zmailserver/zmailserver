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
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.*;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail.Field;


public class ReceiveReadReceipt extends PrefGroupMailByMessageTest {

	public ReceiveReadReceipt() {
		logger.info("New "+ ReceiveReadReceipt.class.getCanonicalName());
		
		
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		
	}
	
	@Test(	description = "Receive/view a read receipt",
			groups = { "functional" })
	public void CreateMailText_01() throws HarnessException {
		
		// Data setup
		
		// Send a message requesting a read receipt
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		app.zGetActiveAccount().soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>" 
			+			"<m>"
			+				"<e t='t' a='"+ ZmailAccount.AccountA().EmailAddress +"'/>"
			+				"<e t='f' a='"+ app.zGetActiveAccount().EmailAddress +"'/>"
			+				"<e t='n' a='"+ app.zGetActiveAccount().EmailAddress +"'/>"
			+				"<su>"+ subject +"</su>"
			+				"<mp ct='text/plain'>"
			+					"<content>content" + ZmailSeleniumProperties.getUniqueString() +"</content>" 
			+				"</mp>"
			+			"</m>" 
			+		"</SendMsgRequest>");

		
		MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ subject +")");
		
		// Send the read receipt
		ZmailAccount.AccountA().soapSend("<SendDeliveryReportRequest xmlns='urn:zmailMail' mid='"+ received.getId() +"'/>");
		
		
		
		
		// GUI verification
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		ZAssert.assertEquals(			actual.zGetMailProperty(Field.To),		app.zGetActiveAccount().EmailAddress, "Verify the message is to the test account");
		ZAssert.assertEquals(			actual.zGetMailProperty(Field.From),	ZmailAccount.AccountA().EmailAddress, "Verify the message is from the destination");
		ZAssert.assertStringContains(	actual.zGetMailProperty(Field.Subject), "Read-Receipt", "Verify the message subject contains the correct value");	// TODO: I18N
		ZAssert.assertStringContains(	actual.zGetMailProperty(Field.Body), 	"The message sent on", "Verify the message subject contains the correct value");	// TODO: I18N
		ZAssert.assertStringContains(	actual.zGetMailProperty(Field.Body), 	subject, "Verify the message subject contains the correct value");	// TODO: I18N
		
		
	}

	


}
