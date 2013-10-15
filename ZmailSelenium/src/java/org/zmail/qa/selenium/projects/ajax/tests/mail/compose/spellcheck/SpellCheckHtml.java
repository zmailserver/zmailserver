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
package org.zmail.qa.selenium.projects.ajax.tests.mail.compose.spellcheck;


import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;

public class SpellCheckHtml extends PrefGroupMailByMessageTest {

	public SpellCheckHtml() {
		logger.info("New "+ SpellCheckHtml.class.getCanonicalName());
		
		
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "html");
		
	}
	
	@Test(	description = "Spell Check an HTML message",
			groups = { "functional" })
	public void SpellCheckHtml_01() throws HarnessException {
		
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFillField(Field.To, ZmailAccount.AccountA().EmailAddress);
		mailform.zFillField(Field.Subject, subject);
		//mailform.zFillField(Field.Body, "write mispeled words here");
		
		/* TODO: ... debugging to be removed */
		String bodyLocator = "css=body[id=tinymce]";
		boolean present = mailform.zWaitForElementPresent(bodyLocator, "30000");
		ZAssert.assertTrue(present,"Verify the body field is available");
		mailform.sClickAt(bodyLocator,"");
		mailform.zTypeFormattedText("css=iframe[id*=ifr]", "write mispeled words here");

		// Send the message
		mailform.zToolbarPressButton(Button.B_SPELL_CHECK);
			
		// Verify the misspelled word is highlighted
		ZAssert.assertTrue(
				mailform.sIsElementPresent("css=span[class='ZM-SPELLCHECK-MISSPELLED']:contains(mispeled)"),
				"Verify the misspelled word is highlighted");

		// Verify the misspelled word is highlighted
		ZAssert.assertFalse(
				mailform.sIsElementPresent("css=span[class='ZM-SPELLCHECK-MISSPELLED']:contains(words)"),
				"Verify the correctly spelled words are not highlighted");

		mailform.zToolbarPressButton(Button.B_SEND);
		
	}

}
