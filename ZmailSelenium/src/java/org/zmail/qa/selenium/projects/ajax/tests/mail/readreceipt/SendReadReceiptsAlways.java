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

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.framework.util.staf.Stafpostqueue;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail.Field;


public class SendReadReceiptsAlways extends PrefGroupMailByMessageTest {
	
	public SendReadReceiptsAlways() {
		logger.info("New "+ SendReadReceiptsAlways.class.getCanonicalName());
		
		
		super.startingAccountPreferences.put("zmailPrefMailSendReadReceipts", "always");


	}
	
	@Test(	description = "zmailPrefMailSendReadReceipts=always - verify read receipt is sent",
			groups = { "functional" })
	public void SendReadReceiptsAlways_01() throws HarnessException {
		
		// Create a source account
		ZmailAccount sender = new ZmailAccount();
		sender.provision().authenticate();
		
		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		
		// Send the message from AccountA to the ZWC user
		sender.soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>"
				+		"<m>"
				+			"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>"
				+			"<e t='n' a='"+ sender.EmailAddress +"'/>"
				+			"<su>"+ subject +"</su>"
				+			"<mp ct='text/plain'>"
				+				"<content>"+ "body" + ZmailSeleniumProperties.getUniqueString() +"</content>"
				+			"</mp>"
				+		"</m>"
				+	"</SendMsgRequest>");


		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		// Verify the To, From, Subject, Body
		ZAssert.assertEquals(	actual.zGetMailProperty(Field.Subject), subject, "Verify the subject displays");


		// Make sure all read-receipts are delivered
		Stafpostqueue q = new Stafpostqueue();
		q.waitForPostqueue();
		
		// Verify the sender receives the read receipt
		sender.soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='message'>"
				+		"<query>subject:(Read-Receipt) subject:("+ subject +")</query>"
				+	"</SearchRequest>");
		Element[] nodes = sender.soapSelectNodes("//mail:m");
		ZAssert.assertEquals(nodes.length, 1, "Verify the read receipt is received by the sender");

	}


}
