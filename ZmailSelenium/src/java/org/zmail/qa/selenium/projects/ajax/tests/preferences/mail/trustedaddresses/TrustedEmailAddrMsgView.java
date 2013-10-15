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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.mail.trustedaddresses;

import java.io.File;
import java.util.HashMap;
import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.LmtpInject;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
//import org.zmail.qa.selenium.projects.ajax.ui.preferences.trustedaddresses.DisplayTrustedAddress;

public class TrustedEmailAddrMsgView extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public TrustedEmailAddrMsgView() throws HarnessException {
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefGroupMailBy", "message");
				put("zmailPrefMessageViewHtmlPreferred", "TRUE");
				put("zmailPrefMailTrustedSenderList", "admintest@testdoamin.com");
			}
		};
	}

	/**
	 * TestCase : Trusted Email address with message view
	 * 1.Set Email address in Preference/Mail/Trusted Addresses
	 * 	Verify email addr through soap- GetPrefsRequest
	 * 2.In message View Inject mail from same email id with external image
	 * 3.Verify To,From,Subject through soap 
	 * 4.Click on same mail
	 * 5.Yellow color Warning Msg Info bar should not present for trusted eamil address.
	 * @throws HarnessException
	 */
	@Test(description = "Verify Display Image link in Trusted Addresses for message view", groups = { "smoke" })
	public void TrustedEmailAddrMsgView_01() throws HarnessException {

		final String subject = "TestTrustedAddress";
		final String from = "admintest@testdoamin.com";
		final String to = "admin@testdoamin.com";
		final String mimeFolder = ZmailSeleniumProperties.getBaseDirectory()
		+ "/data/public/mime/ExternalImg.txt";

		// Verify Email id through soap GetPrefsRequest
		String PrefMailTrustedAddr = ZmailAccount.AccountZWC().getPreference(
				"zmailPrefMailTrustedSenderList");
		ZAssert.assertTrue(PrefMailTrustedAddr
				.equals("admintest@testdoamin.com"),
				"Verify Email address is present /Pref/TrustedAddr");

		// Inject the external image message(s)
		LmtpInject.injectFile(app.zGetActiveAccount().EmailAddress, new File(mimeFolder));

		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(),subject);

		ZAssert.assertNotNull(mail, "Verify message is received");
		ZAssert.assertEquals(from, mail.dFromRecipient.dEmailAddress,"Verify the from matches");
		ZAssert.assertEquals(to, mail.dToRecipients.get(0).dEmailAddress,"Verify the to address");
				
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		// Verify Warning info bar with other links

		ZAssert.assertFalse(app.zPageMail.zHasWDDLinks(),"Verify Warning icon ,Display Image and Domain link  does not present");

	}

}
