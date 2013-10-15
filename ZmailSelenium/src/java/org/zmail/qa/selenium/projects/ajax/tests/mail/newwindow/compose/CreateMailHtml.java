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
package org.zmail.qa.selenium.projects.ajax.tests.mail.newwindow.compose;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.SeparateWindowFormMailNew;

public class CreateMailHtml extends PrefGroupMailByMessageTest {

	public CreateMailHtml() {
		logger.info("New "+ CreateMailHtml.class.getCanonicalName());

		super.startingAccountPreferences.put("zmailPrefComposeFormat", "html");
		super.startingAccountPreferences.put("zmailPrefComposeInNewWindow", "TRUE");

	}

	@Test(	description = "Send a mail using HTML editor - in a separate window",
			groups = { "sanity" })
			public void CreateMailHtml_01() throws HarnessException {


		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZmailAccount.AccountA()));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		/* TODO: ... debugging to be removed */ 
		//mail.dBodyHtml = "body" + ZmailSeleniumProperties.getUniqueString();


		// Open the new mail form
		SeparateWindowFormMailNew window = null;

		try {

			window = (SeparateWindowFormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW_IN_NEW_WINDOW);

			window.zSetWindowTitle("Compose");
			window.zWaitForActive();		// Make sure the window is there

			window.waitForComposeWindow();
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");

			// Fill out the form with the data
			window.zFill(mail);
			
			/* TODO: ... debugging to be removed */ 
			mail.dBodyHtml = "body" + ZmailSeleniumProperties.getUniqueString();
			window.sSelectWindow("Zmail: Compose");
			String locator = "css=iframe[id*=ifr]";
			window.zWaitForElementPresent(locator, "5000");
			window.sClickAt(locator,"");
			window.zTypeFormattedText(locator, mail.dBodyHtml);
			
			// Send the message
			window.zToolbarPressButton(Button.B_SEND);
			
			if(window.zWaitForWindowClosed("Zmail: Compose")){
			    // Window closes automatically
			    window = null;
			}
		} finally {

			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}

		}

		// Sometimes, the harness is too fast for the client.
		// Since we are composing in a new window, there is no
		// busy overlay to block.
		//
		// Add a loop, while waiting for the message
		//
		for (int i = 0; i < 30; i++) {

			ZmailAccount.AccountA().soapSend(
					"<SearchRequest types='message' xmlns='urn:zmailMail'>"
			+			"<query>subject:("+ mail.dSubject +")</query>"
			+		"</SearchRequest>");
			org.zmail.common.soap.Element node = ZmailAccount.AccountA().soapSelectNode("//mail:m", 1);
			if ( node != null ) {
				// found the message
				break;
			}
			
			SleepUtil.sleep(1000);

		}

		// Can't use importFromSOAP, since that only parses the text part
		// MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ mail.dSubject +")");

		ZmailAccount.AccountA().soapSend(
						"<SearchRequest types='message' xmlns='urn:zmailMail'>"
				+			"<query>subject:("+ mail.dSubject +")</query>"
				+		"</SearchRequest>");
		String id = ZmailAccount.AccountA().soapSelectValue("//mail:m", "id");
		
		ZmailAccount.AccountA().soapSend(
						"<GetMsgRequest xmlns='urn:zmailMail'>"
				+			"<m id='"+ id +"' html='1'/>"
				+		"</GetMsgRequest>");

		String from = ZmailAccount.AccountA().soapSelectValue("//mail:e[@t='f']", "a");
		String to = ZmailAccount.AccountA().soapSelectValue("//mail:e[@t='t']", "a");
		String subject = ZmailAccount.AccountA().soapSelectValue("//mail:su", null);
		String html = ZmailAccount.AccountA().soapSelectValue("//mail:mp[@ct='text/html']//mail:content", null);
		
		ZAssert.assertEquals(from, app.zGetActiveAccount().EmailAddress, "Verify the from field is correct");
		ZAssert.assertEquals(to, ZmailAccount.AccountA().EmailAddress, "Verify the to field is correct");
		ZAssert.assertEquals(subject, mail.dSubject, "Verify the subject field is correct");
		ZAssert.assertStringContains(html, mail.dBodyHtml, "Verify the html content");

	}

}
