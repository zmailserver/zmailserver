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
package org.zmail.qa.selenium.projects.ajax.tests.mail.compose.drafts;



import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.ui.Shortcut;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogWarning;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class SaveDraftMail extends PrefGroupMailByMessageTest {

	public SaveDraftMail() {
		logger.info("New "+ SaveDraftMail.class.getCanonicalName());
		
		
		
		
	}
	
	@Test(	description = "Save a basic draft (subject only)",
			groups = { "smoke" })
	public void SaveDraftMail_01() throws HarnessException {
		
		
		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFillField(Field.Subject, subject);
		
		// Send the message
		mailform.zToolbarPressButton(Button.B_SAVE_DRAFT);
		mailform.zToolbarPressButton(Button.B_CANCEL);
		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		
		// Get the message from the server
      MailItem draft = MailItem.importFromSOAP(app.zGetActiveAccount(),
            "subject:("+ subject +")");

      FolderItem draftsFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Drafts);
		
		// Verify the draft data matches
		ZAssert.assertEquals(draft.dSubject, subject, "Verify the subject field is correct");
		ZAssert.assertEquals(draft.dFolderId, draftsFolder.getId(), "Verify the draft is saved in the drafts folder");


	}
	/**
	 * Test Case: Save draft using keyboard shortcut 'Escape''
	 * 1.Compose Text mail
	 * 2.Press 'Esc' key of keyboard
	 * 3.Verify 'SaveCurrentMessageAsDraft'Warning Dialog
	 * 4.Press Yes
	 * 5.Verify Message is present in Draft
	 * @throws HarnessException
	 */
	
	@Test(description = "Save draft using keyboard shortcut 'Escape'", groups = { "functional" })
	public void SaveDraftMail_02() throws HarnessException {

		Shortcut shortcut = Shortcut.S_ESCAPE;
		// Create the message data to be sent
		String body = "body" + ZmailSeleniumProperties.getUniqueString();
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();

		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertTrue(mailform.zIsActive(), "Verify the new form opened");

		// Fill out the form with the data
		mailform.zFillField(Field.Subject, subject);
		mailform.zFillField(Field.Body, body);

		DialogWarning warning = (DialogWarning) app.zPageMail.zKeyboardShortcut(shortcut);
		ZAssert.assertNotNull(warning, "Verify the dialog is opened");

		warning.zClickButton(Button.B_YES);
		warning.zWaitForClose(); // Make sure the dialog is dismissed

		// Get the message from the server
		MailItem draft = MailItem.importFromSOAP(app.zGetActiveAccount(),"subject:(" + subject + ")");

		FolderItem draftsFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Drafts);

		// Verify the draft data matches
		ZAssert.assertEquals(draft.dSubject, subject,"Verify the subject field is correct");
		ZAssert.assertEquals(draft.dFolderId, draftsFolder.getId(),"Verify the draft is saved in the drafts folder");

	}

}
