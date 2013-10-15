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
package org.zmail.qa.selenium.projects.ajax.tests.mail.compose.addresspicker;


import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.RecipientItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormAddressPicker;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;


public class AddBcc extends PrefGroupMailByMessageTest {

	public AddBcc() {
		logger.info("New "+ AddBcc.class.getCanonicalName());
		
		
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");

		
	}
	
	@Test(	description = "Select a 'Bcc' address in the addresspicker",
			groups = { "functional" })
	public void AddBcc_01() throws HarnessException {
		
		// The account must exist before the picker is opened
		// Log it to initialize it
		logger.info("Will add the following account to the Bcc:" + ZmailAccount.AccountB().EmailAddress);
		
		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZmailAccount.AccountA()));
		mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		mail.dBodyText = "body" + ZmailSeleniumProperties.getUniqueString();
		
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		mailform.zFill(mail);

		// Open the addresspicker by clicking To
		
		FormAddressPicker pickerform = (FormAddressPicker)mailform.zToolbarPressButton(Button.B_CC);
		ZAssert.assertTrue(pickerform.zIsActive(), "Verify the address picker opened corectly");
		
		pickerform.zFillField(FormAddressPicker.Field.Search, ZmailAccount.AccountB().EmailAddress);
		pickerform.zToolbarPressButton(Button.B_SEARCH);
		pickerform.zToolbarPressButton(Button.B_BCC);
		pickerform.zSubmit();
		
		// Addresspicker should now be closed

		// Send the message
		mailform.zSubmit();

		MailItem sent = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(sent, "Verify the message appears in the sent folder");
		ZAssert.assertEquals(sent.dBccRecipients.get(0).dEmailAddress, ZmailAccount.AccountB().EmailAddress, "Verify the 'cc' field is correct");		
		
	}

	


}
