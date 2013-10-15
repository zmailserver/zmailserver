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

import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.SeparateWindowFormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class CreateMailText extends PrefGroupMailByMessageTest {

	public CreateMailText() {
		logger.info("New "+ CreateMailText.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		super.startingAccountPreferences.put("zmailPrefComposeInNewWindow", "TRUE");
		
	}
	
	@Test(	description = "Send a mail using Text editor - in a separate window",
			groups = { "smoke" })
	public void CreateMailText_01() throws HarnessException {
		
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZmailAccount.AccountA()));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		SeparateWindowFormMailNew window = null;
		
		try {
			
			window = (SeparateWindowFormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW_IN_NEW_WINDOW);
			
			window.zSetWindowTitle("Compose");
			window.zWaitForActive();		// Make sure the window is there
			/* TODO: ... debugging to be removed */
			window.waitForComposeWindow();
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
			
			// Fill out the form with the data
			window.zFill(mail);
			
			// Send the message
			window.zToolbarPressButton(Button.B_SEND);

			// Window closes automatically
			window = null;

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


		MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ mail.dSubject +")");

		// TODO: add checks for TO, Subject, Body
		ZAssert.assertEquals(received.dFromRecipient.dEmailAddress, app.zGetActiveAccount().EmailAddress, "Verify the from field is correct");
		ZAssert.assertEquals(received.dToRecipients.get(0).dEmailAddress, ZmailAccount.AccountA().EmailAddress, "Verify the to field is correct");
		ZAssert.assertEquals(received.dSubject, mail.dSubject, "Verify the subject field is correct");
		ZAssert.assertStringContains(received.dBodyText, mail.dBodyText, "Verify the body field is correct");
		
	}

	
	@DataProvider(name = "DataProvideNewMessageShortcuts")
	public Object[][] DataProvideNewMessageShortcuts() {
	  return new Object[][] {
			  new Object[] { Shortcut.S_NEWITEM_IN_NEW_WINDOW, Shortcut.S_NEWITEM_IN_NEW_WINDOW.getKeys() },
			  new Object[] { Shortcut.S_NEWMESSAGE_IN_NEW_WINDOW, Shortcut.S_NEWMESSAGE_IN_NEW_WINDOW.getKeys() },
			  new Object[] { Shortcut.S_NEWMESSAGE2_IN_NEW_WINDOW, Shortcut.S_NEWMESSAGE2_IN_NEW_WINDOW.getKeys() }
	  };
	}
	
	@Test(	description = "Send a mail using Text editor using keyboard shortcuts - in separate window",
			groups = { "functional" },
			dataProvider = "DataProvideNewMessageShortcuts")
	public void CreateMailText_02(Shortcut shortcut, String keys) throws HarnessException {
		
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZmailAccount.AccountA()));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();
		
		// Open the new mail form
		SeparateWindowFormMailNew window = null;
		
		try {
			
			window = (SeparateWindowFormMailNew) app.zPageMail.zKeyboardShortcut(shortcut);

			window.zSetWindowTitle("Compose");
			window.zWaitForActive();		// Make sure the window is there
			/* TODO: ... debugging to be removed */
			window.waitForComposeWindow();
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
			
			// Fill out the form with the data
			window.zFill(mail);
			
			// Send the message
			window.zToolbarPressButton(Button.B_SEND);

			// Window closes automatically
			window = null;

		} finally {
			
			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}
			
		}
		


		// From the receipient end, make sure the message is received
		MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ mail.dSubject +")");

		ZAssert.assertNotNull(received, "Verify the message is received");
		
	}

	@Test(	description = "Send a mail with CC - in a separate window",
			groups = { "functional" })
	public void CreateMailText_03() throws HarnessException {
		
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZmailAccount.AccountA(), RecipientItem.RecipientType.To));
		mail.dCcRecipients.add(new RecipientItem(ZmailAccount.AccountB(), RecipientItem.RecipientType.Cc));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();
		
		// Open the new mail form
		SeparateWindowFormMailNew window = null;
		
		try {
			
			window = (SeparateWindowFormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW_IN_NEW_WINDOW);
			
			window.zSetWindowTitle("Compose");
			window.zWaitForActive();		// Make sure the window is there
			/* TODO: ... debugging to be removed */
			window.waitForComposeWindow();
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
			
			// Fill out the form with the data
			window.zFill(mail);
			
			// Send the message
			window.zToolbarPressButton(Button.B_SEND);

			// Window closes automatically
			window = null;

		} finally {
			
			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}
			
		}
		
				
		MailItem sent = MailItem.importFromSOAP(app.zGetActiveAccount(), "in:sent subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(sent, "Verify the message is in the sent folder");
		
		StringBuilder to = new StringBuilder();
		for (RecipientItem r: sent.dToRecipients) {
			to.append(r.dEmailAddress).append(",");
		}
		ZAssert.assertStringContains(to.toString(), ZmailAccount.AccountA().EmailAddress, "Verify TO contains AccountA");
		
		StringBuilder cc = new StringBuilder();
		for (RecipientItem r: sent.dCcRecipients) {
			cc.append(r.dEmailAddress).append(",");
		}
		ZAssert.assertStringContains(cc.toString(), ZmailAccount.AccountB().EmailAddress, "Verify CC contains AccountB");

		MailItem toReceived = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(toReceived, "Verify the TO recipient receives the message");
		
		MailItem ccReceived = MailItem.importFromSOAP(ZmailAccount.AccountB(), "subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(ccReceived, "Verify the CC recipient receives the message");
		
		
	}

	@Test(	description = "Send a mail with BCC",
			groups = { "deprecated" })
	public void CreateMailText_04() throws HarnessException {
		
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZmailAccount.AccountA(), RecipientItem.RecipientType.To));
		mail.dBccRecipients.add(new RecipientItem(ZmailAccount.AccountB(), RecipientItem.RecipientType.Bcc));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		SeparateWindowFormMailNew window = null;
		
		try {
			
			window = (SeparateWindowFormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW_IN_NEW_WINDOW);
			
			window.zSetWindowTitle("Compose");
			window.zWaitForActive();		// Make sure the window is there
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
			
			// Fill out the form with the data
			window.zFill(mail);
			
			// Send the message
			window.zToolbarPressButton(Button.B_SEND);

			// Window closes automatically
			window = null;

		} finally {
			
			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}
			
		}
		
				
				
		MailItem sent = MailItem.importFromSOAP(app.zGetActiveAccount(), "in:sent subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(sent, "Verify the message is in the sent folder");
		
		StringBuilder to = new StringBuilder();
		for (RecipientItem r: sent.dToRecipients) {
			to.append(r.dEmailAddress).append(",");
		}
		ZAssert.assertStringContains(to.toString(), ZmailAccount.AccountA().EmailAddress, "Verify TO contains AccountA");
		
		MailItem toReceived = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(toReceived, "Verify the TO recipient receives the message");
		
		MailItem bccReceived = MailItem.importFromSOAP(ZmailAccount.AccountB(), "subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(bccReceived, "Verify the BCC recipient receives the message");
		
		
	}


	@DataProvider(name = "DataProvidePriorities")
	public Object[][] DataProvidePriorities() {
	  return new Object[][] {
			  new Object[] { Button.O_PRIORITY_HIGH, "!" },
			  new Object[] { Button.O_PRIORITY_NORMAL, "" },
			  new Object[] { Button.O_PRIORITY_LOW, "?" }
	  };
	}

	@Test(	description = "Send a mail with different priorities high/normal/low - in a separate window",
			groups = { "functional" },
			dataProvider = "DataProvidePriorities")
	public void CreateMailText_05(Button option, String verify) throws HarnessException {
		
		// option: Button.B_PRIORITY_HIGH/NORMAL/LOW
		// verify: the f field in the GetMsgResponse
		
		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String body = "body" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		SeparateWindowFormMailNew window = null;
		
		try {
			
			window = (SeparateWindowFormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW_IN_NEW_WINDOW);
			
			window.zSetWindowTitle("Compose");
			window.zWaitForActive();		// Make sure the window is there
			/* TODO: ... debugging to be removed */
			window.waitForComposeWindow();
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
			
			// Fill out the form with the data
			// Change the priority
			window.zToolbarPressPulldown(Button.B_PRIORITY, option);
			
			// Fill out the rest of the form
			window.zFillField(Field.To, ZmailAccount.AccountA().EmailAddress);
			window.zFillField(Field.Subject, subject);
			window.zFillField(Field.Body, body);
			
			// Send the message
			window.zToolbarPressButton(Button.B_SEND);

			// Window closes automatically
			window = null;

		} finally {
			
			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}
			
		}
		

		MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received");
		
		ZAssert.assertStringContains(received.getFlags(), verify, "Verify the correct priority was sent");
		
		
	}


}
