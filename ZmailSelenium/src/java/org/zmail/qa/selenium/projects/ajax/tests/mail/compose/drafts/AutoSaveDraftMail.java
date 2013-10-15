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

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class AutoSaveDraftMail extends PrefGroupMailByMessageTest {

	public static final int PrefAutoSaveDraftInterval = 10; // seconds

	public AutoSaveDraftMail() {
		logger.info("New "+ AutoSaveDraftMail.class.getCanonicalName());

		
		
		super.startingAccountPreferences.put("zmailPrefAutoSaveDraftInterval", ""+ PrefAutoSaveDraftInterval +"s");
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");

	}

	@Bugs(ids = "66393")
	@Test(	description = "Auto save a basic draft (subject only)",
			groups = { "smoke" })
	public void AutoSaveDraftMail_01() throws HarnessException {


		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String body = "body" + ZmailSeleniumProperties.getUniqueString();


		FormMailNew mailform = null;

		try {

			// Open the new mail form
			mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
			ZAssert.assertNotNull(mailform, "Verify the new form opened");

			// Fill out the form with the data
			mailform.zFillField(Field.Subject, subject);
			mailform.zFillField(Field.Body, body);

			// Wait for twice the amount
			SleepUtil.sleep(PrefAutoSaveDraftInterval * 2 * 1000);

			// Get the message from the server
			FolderItem draftsFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Drafts);
			MailItem draft = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");

			// Verify the draft data matches
			ZAssert.assertEquals(draft.dSubject, subject, "Verify the subject field is correct");
			ZAssert.assertEquals(draft.dFolderId, draftsFolder.getId(), "Verify the draft is saved in the drafts folder");

		} finally {

			if ( (mailform != null) && (mailform.zIsActive()) ) {
				// Close out the draft
				mailform.zToolbarPressButton(Button.B_SAVE_DRAFT);
				mailform.zToolbarPressButton(Button.B_CANCEL);
			}
		}



	}
}
