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
package org.zmail.qa.selenium.projects.ajax.tests.mail.compose;

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.AbsDialog;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.ui.Shortcut;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogWarning;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class CancelComposeHtml extends PrefGroupMailByMessageTest {

	public CancelComposeHtml() {
		logger.info("New "+ CancelComposeHtml.class.getCanonicalName());
		
		
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "html");

		
	}
	
	@Test(	description = "Verify message dialog, when cancel a html draft (body filled)",
			groups = { "functional" })
	public void CancelComposeHtml_01() throws HarnessException {
		
		
		// Create the message data to be sent
		String body = "body" + ZmailSeleniumProperties.getUniqueString();
		

		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertTrue(mailform.zIsActive(), "Verify the new form opened");
		
		
		
		// Fill out the form with the data
		//mailform.zFillField(Field.Body, body);
		
		/* TODO: ... debugging to be moved to mailform class*/
		String bodyLocator = "css=body[id=tinymce]";
		boolean present = mailform.zWaitForElementPresent(bodyLocator, "30000");
		ZAssert.assertTrue(present,"Verify the body field is available");
		mailform.sClickAt(bodyLocator,"");
		mailform.zTypeFormattedText("css=iframe[id*=ifr]", body);
		
		// Cancel the message
		// A warning dialog should appear regarding losing changes
		AbsDialog warning = (AbsDialog)mailform.zToolbarPressButton(Button.B_CANCEL);
		ZAssert.assertNotNull(warning, "Verify the dialog is returned");
		
		// Dismiss the dialog
		warning.zClickButton(Button.B_NO);
		warning.zWaitForClose(); // Make sure the dialog is dismissed
		

	}
	/**
	 * Test Case: Cancel HTML composed mail using keyboard shortcut 'Escape'
	 * 1.Compose Html mail
	 * 2.Press 'Esc' key of keyboard
	 * 3.Verify 'SaveCurrentMessageAsDraft'Warning Dialog
	 * 4.Press No
	 * 5.Verify Message no longer present in inbox
	 * @throws HarnessException
	 */
	
	@Test(	description = "Cancel html composed mail using keyboard shortcut 'Escape'",
			groups = { "functional" })
	public void CancelComposeHtml_02() throws HarnessException {
		
		Shortcut shortcut = Shortcut.S_ESCAPE;
		// Create the message data to be sent
		String body = "body" + ZmailSeleniumProperties.getUniqueString();
		String Subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		

		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertTrue(mailform.zIsActive(), "Verify the new form opened");	
		
		
		// Fill out the form with the data
		mailform.zFillField(Field.Subject, Subject);
		//mailform.zFillField(Field.Body, body);
		
		/* TODO: ... debugging to be moved to mailform class*/
		String bodyLocator = "css=body[id=tinymce]";
		boolean present = mailform.zWaitForElementPresent(bodyLocator, "30000");
		ZAssert.assertTrue(present,"Verify the body field is available");
		mailform.sClickAt(bodyLocator,"");
		mailform.zTypeFormattedText("css=iframe[id*=ifr]", body);
		
		DialogWarning warning =(DialogWarning)app.zPageMail.zKeyboardShortcut(shortcut);
		ZAssert.assertNotNull(warning, "Verify the dialog is opened");
		
		warning.zClickButton(Button.B_NO);
		warning.zWaitForClose(); // Make sure the dialog is dismissed
		
		List<MailItem> messages = app.zPageMail.zListGetMessages();
		
		MailItem found = null;
		for (MailItem m : messages) {
			logger.info("Subject: looking for "+Subject +" found: "+ m.gSubject);
			if ( Subject.equals(m.gSubject) ) {
				found = m;
				break;
			}
		}
		ZAssert.assertNull(found, "Verify the message is no longer in the inbox");
		
	}
}
