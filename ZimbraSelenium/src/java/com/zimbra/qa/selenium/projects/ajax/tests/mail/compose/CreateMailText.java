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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.compose;

import org.testng.annotations.*;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class CreateMailText extends PrefGroupMailByMessageTest {

	public CreateMailText() {
		logger.info("New "+ CreateMailText.class.getCanonicalName());
		
		
		
		super.startingAccountPreferences.put("zimbraPrefComposeFormat", "text");
		
	}
	
	@Test(	description = "Send a mail using Text editor",
			groups = { "sanity" })
	public void CreateMailText_01() throws HarnessException {
		
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZimbraAccount.AccountA()));
		mail.dSubject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZimbraSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFill(mail);
		
		// Send the message
		mailform.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		MailItem received = MailItem.importFromSOAP(ZimbraAccount.AccountA(), "subject:("+ mail.dSubject +")");

      logger.debug("===========received is: " + received);
      logger.debug("===========app is: " + app);
		// TODO: add checks for TO, Subject, Body
		ZAssert.assertEquals(received.dFromRecipient.dEmailAddress, app.zGetActiveAccount().EmailAddress, "Verify the from field is correct");
		ZAssert.assertEquals(received.dToRecipients.get(0).dEmailAddress, ZimbraAccount.AccountA().EmailAddress, "Verify the to field is correct");
		ZAssert.assertEquals(received.dSubject, mail.dSubject, "Verify the subject field is correct");
		ZAssert.assertStringContains(received.dBodyText, mail.dBodyText, "Verify the body field is correct");
		
	}

	
	@DataProvider(name = "DataProvideNewMessageShortcuts")
	public Object[][] DataProvideNewMessageShortcuts() {
	  return new Object[][] {
			  new Object[] { Shortcut.S_NEWITEM, Shortcut.S_NEWITEM.getKeys() },
			  new Object[] { Shortcut.S_NEWMESSAGE, Shortcut.S_NEWMESSAGE.getKeys() },
			  new Object[] { Shortcut.S_NEWMESSAGE2, Shortcut.S_NEWMESSAGE2.getKeys() }
	  };
	}
	
	@Test(	description = "Send a mail using Text editor using keyboard shortcuts",
			groups = { "functional" },
			dataProvider = "DataProvideNewMessageShortcuts")
	public void CreateMailText_02(Shortcut shortcut, String keys) throws HarnessException {
		
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZimbraAccount.AccountA()));
		mail.dSubject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZimbraSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		//FormMailNew mailform = (FormMailNew) app.zPageMail.zKeyboardShortcut(shortcut);
		//ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		/* TODO: ... debugging to be removed */
		FormMailNew mailform = new FormMailNew(app);
		String key = shortcut.getKeys();
		String keyCode = "";
		if(key.equals("n")){
		    keyCode = "78";
		}else if(key.equals("nm")){
		    keyCode = "78,77";
		}else if(key.equals("c")){
		    keyCode = "67";
		}
		mailform.zKeyDown(keyCode);
		mailform.zWaitForActive();
		boolean present = mailform.zWaitForElementPresent("css=textarea[id*='DWT'][class='DwtHtmlEditorTextArea']","30000");
		ZAssert.assertTrue(present, "Verify the new form opened");
		
		
		// Send the message
		mailform.zFill(mail);
		mailform.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		// From the receipient end, make sure the message is received
		MailItem received = MailItem.importFromSOAP(ZimbraAccount.AccountA(), "subject:("+ mail.dSubject +")");

		ZAssert.assertNotNull(received, "Verify the message is received");
		
	}

	@Test(	description = "Send a mail with CC",
			groups = { "functional" })
	public void CreateMailText_03() throws HarnessException {
		
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZimbraAccount.AccountA(), RecipientItem.RecipientType.To));
		mail.dCcRecipients.add(new RecipientItem(ZimbraAccount.AccountB(), RecipientItem.RecipientType.Cc));
		mail.dSubject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZimbraSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFill(mail);
				
		// Send the message
		mailform.zSubmit();
				
		MailItem sent = MailItem.importFromSOAP(app.zGetActiveAccount(), "in:sent subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(sent, "Verify the message is in the sent folder");
		
		StringBuilder to = new StringBuilder();
		for (RecipientItem r: sent.dToRecipients) {
			to.append(r.dEmailAddress).append(",");
		}
		ZAssert.assertStringContains(to.toString(), ZimbraAccount.AccountA().EmailAddress, "Verify TO contains AccountA");
		
		StringBuilder cc = new StringBuilder();
		for (RecipientItem r: sent.dCcRecipients) {
			cc.append(r.dEmailAddress).append(",");
		}
		ZAssert.assertStringContains(cc.toString(), ZimbraAccount.AccountB().EmailAddress, "Verify CC contains AccountB");

		MailItem toReceived = MailItem.importFromSOAP(ZimbraAccount.AccountA(), "subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(toReceived, "Verify the TO recipient receives the message");
		
		MailItem ccReceived = MailItem.importFromSOAP(ZimbraAccount.AccountB(), "subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(ccReceived, "Verify the CC recipient receives the message");
		
		
	}

	@Test(	description = "Send a mail with BCC",
			groups = { "functional" })
	public void CreateMailText_04() throws HarnessException {
		
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZimbraAccount.AccountA(), RecipientItem.RecipientType.To));
		mail.dBccRecipients.add(new RecipientItem(ZimbraAccount.AccountB(), RecipientItem.RecipientType.Bcc));
		mail.dSubject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZimbraSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFill(mail);
		
		// Send the message
		mailform.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
				
		MailItem sent = MailItem.importFromSOAP(app.zGetActiveAccount(), "in:sent subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(sent, "Verify the message is in the sent folder");
		
		StringBuilder to = new StringBuilder();
		for (RecipientItem r: sent.dToRecipients) {
			to.append(r.dEmailAddress).append(",");
		}
		ZAssert.assertStringContains(to.toString(), ZimbraAccount.AccountA().EmailAddress, "Verify TO contains AccountA");
		
		MailItem toReceived = MailItem.importFromSOAP(ZimbraAccount.AccountA(), "subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(toReceived, "Verify the TO recipient receives the message");
		
		MailItem bccReceived = MailItem.importFromSOAP(ZimbraAccount.AccountB(), "subject:("+ mail.dSubject +")");
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

	@Test(	description = "Send a mail with different priorities high/normal/low",
			groups = { "functional" },
			dataProvider = "DataProvidePriorities")
	public void CreateMailText_05(Button option, String verify) throws HarnessException {
		
		// option: Button.B_PRIORITY_HIGH/NORMAL/LOW
		// verify: the f field in the GetMsgResponse
		
		// Create the message data to be sent
		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		String body = "body" + ZimbraSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Change the priority
		mailform.zToolbarPressPulldown(Button.B_PRIORITY, option);
		
		// Fill out the rest of the form
		mailform.zFillField(Field.To, ZimbraAccount.AccountA().EmailAddress);
		mailform.zFillField(Field.Subject, subject);
		mailform.zFillField(Field.Body, body);
		
		// Send the message
		mailform.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		MailItem received = MailItem.importFromSOAP(ZimbraAccount.AccountA(), "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received");
		
		ZAssert.assertStringContains(received.getFlags(), verify, "Verify the correct priority was sent");
		
		
	}


}
