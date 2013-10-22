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
package org.zmail.qa.selenium.projects.desktop.tests.mail.compose;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.DesktopAccountItem;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.RecipientItem;
import org.zmail.qa.selenium.framework.items.DesktopAccountItem.SECURITY_TYPE;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.ui.Shortcut;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.accounts.FormAddPopAccount;
import org.zmail.qa.selenium.projects.desktop.ui.accounts.PageAddNewAccount.DROP_DOWN_OPTION;
import org.zmail.qa.selenium.projects.desktop.ui.mail.DisplayMail;
import org.zmail.qa.selenium.projects.desktop.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.desktop.ui.mail.FormMailNew.Field;

public class CreateMailText extends AjaxCommonTest {

   private boolean _externalAccountTest = false;
   private String _emailSubjectCreated = null;

   @SuppressWarnings("serial")
	public CreateMailText() {
		logger.info("New "+ CreateMailText.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = new HashMap<String , String>() {{
				    put("zmailPrefComposeFormat", "text");
				    put("zmailPrefReadingPaneLocation", "bottom");
				}};
		
	}
	
	@Test(	description = "Send a mail using Text editor",
			groups = { "sanity" })
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
		
		// Send the message
		mailform.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

		MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ mail.dSubject +")");

      logger.debug("===========received is: " + received);
      logger.debug("===========app is: " + app);
		// TODO: add checks for TO, Subject, Body
		ZAssert.assertEquals(received.dFromRecipient.dEmailAddress, app.zGetActiveAccount().EmailAddress, "Verify the from field is correct");
		ZAssert.assertEquals(received.dToRecipients.get(0).dEmailAddress, ZmailAccount.AccountA().EmailAddress, "Verify the to field is correct");
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
		mail.dToRecipients.add(new RecipientItem(ZmailAccount.AccountA()));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zKeyboardShortcut(shortcut);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Send the message
		mailform.zFill(mail);
		mailform.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

		// From the receipient end, make sure the message is received
		MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ mail.dSubject +")");

		ZAssert.assertNotNull(received, "Verify the message is received");
		
	}

	@Test(	description = "Send a mail with CC",
			groups = { "functional" })
	public void CreateMailText_03() throws HarnessException {
		
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZmailAccount.AccountA(), RecipientItem.RecipientType.To));
		mail.dCcRecipients.add(new RecipientItem(ZmailAccount.AccountB(), RecipientItem.RecipientType.Cc));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();

		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFill(mail);
				
		// Send the message
		mailform.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

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
			groups = { "functional" })
	public void CreateMailText_04() throws HarnessException {
		
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZmailAccount.AccountA(), RecipientItem.RecipientType.To));
		mail.dBccRecipients.add(new RecipientItem(ZmailAccount.AccountB(), RecipientItem.RecipientType.Bcc));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFill(mail);
		
		// Send the message
		mailform.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageMail.zWaitForDesktopLoadingSpinner(5000);
				
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

	@Test(	description = "Send a mail with different priorities high/normal/low",
			groups = { "functional" },
			dataProvider = "DataProvidePriorities")
	public void CreateMailText_05(Button option, String verify) throws HarnessException {

		// option: Button.B_PRIORITY_HIGH/NORMAL/LOW
		// verify: the f field in the GetMsgResponse

		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String body = "body" + ZmailSeleniumProperties.getUniqueString();


		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");

		// Change the priority
		mailform.zToolbarPressPulldown(Button.B_PRIORITY, option);

		// Fill out the rest of the form
		mailform.zFillField(Field.To, ZmailAccount.AccountA().EmailAddress);
		mailform.zFillField(Field.Subject, subject);
		mailform.zFillField(Field.Body, body);

		// Send the message
		mailform.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

		MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ subject +")");
		ZAssert.assertNotNull(received, "Verify the message is received");

		ZAssert.assertStringContains(received.getFlags(), verify, "Verify the correct priority was sent");

	}

	@Test(  description = "Send a mail from Yahoo to Gmail",
         groups = { "functional" })
   public void createMailFromYahooToGmail() throws HarnessException {
      _externalAccountTest = true;

      DesktopAccountItem desktopAccountItem = app.zPageAddNewAccount.zAddYahooAccountThruUI();
      DesktopAccountItem destDesktopAccountItem = app.zPageAddNewAccount.zAddGmailAccountThruUI();

      ZmailAccount account = new ZmailAccount(desktopAccountItem.emailAddress,
            desktopAccountItem.password);
      ZmailAccount destAccount = new ZmailAccount(destDesktopAccountItem.emailAddress,
            destDesktopAccountItem.password);
      account.authenticateToMailClientHost();
      destAccount.authenticateToMailClientHost();

      multipleAccountsSetup(account);

      // Create the message data to be sent
      MailItem mail = new MailItem();
      mail.dToRecipients.add(new RecipientItem(destAccount));
      mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
      mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();

      // Open the new mail form
      FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
      ZAssert.assertNotNull(mailform, "Verify the new form opened");

      // Fill out the form with the data
      mailform.zFill(mail);
      
      // Send the message
      mailform.zSubmit();

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      //Switch the main account to be destAccount
      app.zSetActiveAcount(destAccount);

      FolderItem destInboxFolder = FolderItem.importFromSOAP(destAccount,
            SystemFolder.Inbox, SOAP_DESTINATION_HOST_TYPE.CLIENT, destAccount.EmailAddress);
      app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, destInboxFolder);

      app.zPageMail.zSyncAndWaitForNewEmail(mail.dSubject);

      DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

      _emailSubjectCreated = mail.dSubject;

      // Verify the To, From, Subject, Body
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.Subject), mail.dSubject,
            "Verify the subject");
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.From), desktopAccountItem.fullName,
            "Verify the From matches the 'Sender:' header");
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.Body), mail.dBodyText + "<br>",
            "Verify the email body");
   }

	@Test(  description = "Send a mail from Gmail to Yahoo",
         groups = { "functional" })
   public void createMailFromGmailToYahoo() throws HarnessException {
      _externalAccountTest = true;

      DesktopAccountItem desktopAccountItem = app.zPageAddNewAccount.zAddGmailAccountThruUI();
      DesktopAccountItem destDesktopAccountItem = app.zPageAddNewAccount.zAddYahooAccountThruUI();

      ZmailAccount account = new ZmailAccount(desktopAccountItem.emailAddress,
            desktopAccountItem.password);
      ZmailAccount destAccount = new ZmailAccount(destDesktopAccountItem.emailAddress,
            destDesktopAccountItem.password);
      account.authenticateToMailClientHost();
      destAccount.authenticateToMailClientHost();

      multipleAccountsSetup(account);

      // Create the message data to be sent
      MailItem mail = new MailItem();
      mail.dToRecipients.add(new RecipientItem(destAccount));
      mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
      mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();

      // Open the new mail form
      FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
      ZAssert.assertNotNull(mailform, "Verify the new form opened");

      // Fill out the form with the data
      mailform.zFill(mail);
      
      // Send the message
      mailform.zSubmit();

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      //Switch the main account to be destAccount
      app.zSetActiveAcount(destAccount);

      FolderItem destInboxFolder = FolderItem.importFromSOAP(destAccount,
            SystemFolder.Inbox, SOAP_DESTINATION_HOST_TYPE.CLIENT, destAccount.EmailAddress);
      app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, destInboxFolder);

      app.zPageMail.zSyncAndWaitForNewEmail(mail.dSubject);

      DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

      _emailSubjectCreated = mail.dSubject;

      // Verify the To, From, Subject, Body
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.Subject), mail.dSubject,
            "Verify the subject");
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.From), desktopAccountItem.fullName,
            "Verify the From matches the 'Sender:' header");
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.Body), mail.dBodyText + "<br>",
            "Verify the email body");
   }

	@Test(  description = "Send a mail from POP to IMAP",
         groups = { "private" })
   public void createMailFromPopToImap() throws HarnessException {
      _externalAccountTest = true;

      DesktopAccountItem desktopAccountItem = app.zPageAddNewAccount.zAddPopAccountThruUI();
      DesktopAccountItem destDesktopAccountItem = app.zPageAddNewAccount.zAddGmailImapAccountThruUI();

      ZmailAccount account = new ZmailAccount(desktopAccountItem.emailAddress,
            desktopAccountItem.password);
      ZmailAccount destAccount = new ZmailAccount(destDesktopAccountItem.emailAddress,
            destDesktopAccountItem.password);
      account.authenticateToMailClientHost();
      destAccount.authenticateToMailClientHost();

      multipleAccountsSetup(account);

      // Create the message data to be sent
      MailItem mail = new MailItem();
      mail.dToRecipients.add(new RecipientItem(destAccount));
      mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
      mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();

      // Open the new mail form
      FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
      ZAssert.assertNotNull(mailform, "Verify the new form opened");

      // Fill out the form with the data
      mailform.zFill(mail);
      
      // Send the message
      mailform.zSubmit();

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      //Switch the main account to be destAccount
      app.zSetActiveAcount(destAccount);

      FolderItem destInboxFolder = FolderItem.importFromSOAP(destAccount,
            SystemFolder.Inbox, SOAP_DESTINATION_HOST_TYPE.CLIENT, destAccount.EmailAddress);
      app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, destInboxFolder);

      app.zPageMail.zSyncAndWaitForNewEmail(mail.dSubject);

      DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

      _emailSubjectCreated = mail.dSubject;

      // Verify the To, From, Subject, Body
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.Subject), mail.dSubject,
            "Verify the subject");
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.From), desktopAccountItem.fullName,
            "Verify the From matches the 'Sender:' header");
      ZAssert.assertStringContains(actual.zGetMailProperty(DisplayMail.Field.Body), mail.dBodyText,
            "Verify the email body");
   }

   @Test(  description = "Send a mail from IMAP to POP",
         groups = { "private" })
   public void createMailFromImapToPop() throws HarnessException {
      _externalAccountTest = true;

      DesktopAccountItem desktopAccountItem = app.zPageAddNewAccount.zAddGmailImapAccountThruUI();
      //DesktopAccountItem destDesktopAccountItem = app.zPageAddNewAccount.zAddPopAccountThruUI();
      app.zPageAddNewAccount.zNavigateTo();
      DesktopAccountItem destDesktopAccountItem = DesktopAccountItem.generateDesktopPopAccountItem(
            AjaxCommonTest.hotmailUserName2,
            AjaxCommonTest.hotmailUserName2,
            AjaxCommonTest.hotmailPassword2,
            AjaxCommonTest.hotmailPopReceivingServer,
            SECURITY_TYPE.SSL,
            "995",
            AjaxCommonTest.hotmailPopSmtpServer,
            false,
            "25",
            AjaxCommonTest.hotmailUserName2,
            AjaxCommonTest.hotmailPassword2);

      FormAddPopAccount accountForm = (FormAddPopAccount)app.
            zPageAddNewAccount.zDropDownListSelect(DROP_DOWN_OPTION.POP);
      accountForm.zFill(destDesktopAccountItem);
      accountForm.zSubmit();

      ZmailAccount account = new ZmailAccount(desktopAccountItem.emailAddress,
            desktopAccountItem.password);
      ZmailAccount destAccount = new ZmailAccount(destDesktopAccountItem.emailAddress,
            destDesktopAccountItem.password);
      account.authenticateToMailClientHost();
      destAccount.authenticateToMailClientHost();

      multipleAccountsSetup(account);

      // Create the message data to be sent
      MailItem mail = new MailItem();
      mail.dToRecipients.add(new RecipientItem(destAccount));
      mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
      mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();

      // Open the new mail form
      FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
      ZAssert.assertNotNull(mailform, "Verify the new form opened");

      // Fill out the form with the data
      mailform.zFill(mail);
      
      // Send the message
      mailform.zSubmit();

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      //Switch the main account to be destAccount
      app.zSetActiveAcount(destAccount);

      FolderItem destInboxFolder = FolderItem.importFromSOAP(destAccount,
            SystemFolder.Inbox, SOAP_DESTINATION_HOST_TYPE.CLIENT, destAccount.EmailAddress);
      app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, destInboxFolder);

      app.zPageMail.zSyncAndWaitForNewEmail(mail.dSubject);

      DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

      _emailSubjectCreated = mail.dSubject;

      // Verify the To, From, Subject, Body
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.Subject), mail.dSubject,
            "Verify the subject");
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.From), desktopAccountItem.fullName,
            "Verify the From matches the 'Sender:' header");
      ZAssert.assertStringContains(actual.zGetMailProperty(DisplayMail.Field.Body), mail.dBodyText,
            "Verify the email body");
   }

	@Test(  description = "Send a mail from Zmail to Gmail",
         groups = { "functional" })
   public void createMailFromZmailToGmail() throws HarnessException {
	   _externalAccountTest = true;

      DesktopAccountItem destDesktopAccountItem = app.zPageAddNewAccount.zAddGmailAccountThruUI();

      ZmailAccount account = ZmailAccount.AccountZDC();
      ZmailAccount destAccount = new ZmailAccount(destDesktopAccountItem.emailAddress,
            destDesktopAccountItem.password);
      account.authenticateToMailClientHost();
      destAccount.authenticateToMailClientHost();

      multipleAccountsSetup(account);

      // Create the message data to be sent
      MailItem mail = new MailItem();
      mail.dToRecipients.add(new RecipientItem(destAccount));
      mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
      mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();

      // Open the new mail form
      FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
      ZAssert.assertNotNull(mailform, "Verify the new form opened");

      // Fill out the form with the data
      mailform.zFill(mail);
      
      // Send the message
      mailform.zSubmit();

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      //Switch the main account to be destAccount
      app.zSetActiveAcount(destAccount);

      FolderItem destInboxFolder = FolderItem.importFromSOAP(destAccount,
            SystemFolder.Inbox, SOAP_DESTINATION_HOST_TYPE.CLIENT, destAccount.EmailAddress);
      app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, destInboxFolder);

      app.zPageMail.zSyncAndWaitForNewEmail(mail.dSubject);

      DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

      _emailSubjectCreated = mail.dSubject;

      // Verify the To, From, Subject, Body
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.Subject), mail.dSubject,
            "Verify the subject");
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.From), account.EmailAddress,
            "Verify the From matches the 'Sender:' header");
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.Body), mail.dBodyText + "<br>",
            "Verify the email body");
   }

	@Test(  description = "Send a mail from Zmail to Yahoo",
         groups = { "functional" })
   public void createMailFromZmailToYahoo() throws HarnessException {
      _externalAccountTest = true;

      DesktopAccountItem destDesktopAccountItem = app.zPageAddNewAccount.zAddYahooAccountThruUI();

      ZmailAccount account = ZmailAccount.AccountZDC();
      ZmailAccount destAccount = new ZmailAccount(destDesktopAccountItem.emailAddress,
            destDesktopAccountItem.password);
      account.authenticateToMailClientHost();
      destAccount.authenticateToMailClientHost();

      multipleAccountsSetup(account);

      // Create the message data to be sent
      MailItem mail = new MailItem();
      mail.dToRecipients.add(new RecipientItem(destAccount));
      mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
      mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();

      // Open the new mail form
      FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
      ZAssert.assertNotNull(mailform, "Verify the new form opened");

      // Fill out the form with the data
      mailform.zFill(mail);
      
      // Send the message
      mailform.zSubmit();

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      //Switch the main account to be destAccount
      app.zSetActiveAcount(destAccount);

      FolderItem destInboxFolder = FolderItem.importFromSOAP(destAccount,
            SystemFolder.Inbox, SOAP_DESTINATION_HOST_TYPE.CLIENT, destAccount.EmailAddress);
      app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, destInboxFolder);

      app.zPageMail.zSyncAndWaitForNewEmail(mail.dSubject);

      DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

      _emailSubjectCreated = mail.dSubject;

      // Verify the To, From, Subject, Body
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.Subject), mail.dSubject,
            "Verify the subject");
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.From), account.EmailAddress,
            "Verify the From matches the 'Sender:' header");
      ZAssert.assertEquals(   actual.zGetMailProperty(DisplayMail.Field.Body), mail.dBodyText + "<br>",
            "Verify the email body");
   }

	public void multipleAccountsSetup(ZmailAccount mainAccount) throws HarnessException {
      app.zPageLogin.zLogin(mainAccount);
      super.startingPage.zNavigateTo();

      FolderItem inboxFolder = FolderItem.importFromSOAP(mainAccount,
            SystemFolder.Inbox, SOAP_DESTINATION_HOST_TYPE.CLIENT, mainAccount.EmailAddress);

      app.zTreeMail.zExpandAll();
      app.zPageMain.zWaitForDesktopLoadingSpinner(5000);
      app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, inboxFolder);
   }

   @AfterMethod(alwaysRun=true)
   public void cleanUp() throws HarnessException {
      if (_externalAccountTest && _emailSubjectCreated != null) {
         // Select the item
         app.zPageMail.zListItem(Action.A_LEFTCLICK, _emailSubjectCreated);

         // Click delete
         app.zPageMail.zToolbarPressButton(Button.B_DELETE);

         GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
         app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

         _externalAccountTest = false;
         _emailSubjectCreated = null;
         ZmailAccount.ResetAccountZDC();
      }
   }

   
}
