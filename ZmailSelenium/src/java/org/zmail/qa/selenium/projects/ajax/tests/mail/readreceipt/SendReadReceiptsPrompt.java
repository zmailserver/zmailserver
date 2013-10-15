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
import org.zmail.qa.selenium.projects.ajax.ui.DialogWarning;
import org.zmail.qa.selenium.projects.ajax.ui.DialogWarning.DialogWarningID;


public class SendReadReceiptsPrompt extends PrefGroupMailByMessageTest {
	
	public SendReadReceiptsPrompt() {
		logger.info("New "+ SendReadReceiptsPrompt.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefMailSendReadReceipts", "prompt");


	}
	
	@Test(	description = "zmailPrefMailSendReadReceipts=prompt - verify prompt, verify receipt is sent",
			groups = { "functional" })
	public void SendReadReceiptsPrompt_01() throws HarnessException {
		
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
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		DialogWarning dialog = (DialogWarning)app.zPageMain.zGetWarningDialog(DialogWarningID.SendReadReceipt);
		ZAssert.assertNotNull(dialog, "Verify the read receipt dialog is popped up");
		
		// Click YES to send
		dialog.zClickButton(Button.B_YES);

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

	@Test(	description = "zmailPrefMailSendReadReceipts=prompt - verify prompt, verify receipt is not sent",
			groups = { "functional" })
	public void SendReadReceiptsPrompt_02() throws HarnessException {

		
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
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		DialogWarning dialog = (DialogWarning)app.zPageMain.zGetWarningDialog(DialogWarningID.SendReadReceipt);
		ZAssert.assertNotNull(dialog, "Verify the read receipt dialog is popped up");
		
		// Click NO to not send
		dialog.zClickButton(Button.B_NO);

		// Make sure all read-receipts are delivered
		Stafpostqueue q = new Stafpostqueue();
		q.waitForPostqueue();
		
		// Verify the sender receives the read receipt
		sender.soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='message'>"
				+		"<query>subject:(Read-Receipt) subject:("+ subject +")</query>"
				+	"</SearchRequest>");
		Element[] nodes = sender.soapSelectNodes("//mail:m");
		ZAssert.assertEquals(nodes.length, 0, "Verify the read receipt is not received by the sender");


	}


}
