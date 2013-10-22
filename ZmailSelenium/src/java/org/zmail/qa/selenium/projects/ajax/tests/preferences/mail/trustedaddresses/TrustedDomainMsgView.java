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
package com.zimbra.qa.selenium.projects.ajax.tests.preferences.mail.trustedaddresses;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.items.MailItem;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.LmtpInject;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCommonTest;
//import com.zimbra.qa.selenium.projects.ajax.ui.preferences.trustedaddresses.DisplayTrustedAddress;

public class TrustedDomainMsgView extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public TrustedDomainMsgView() throws HarnessException {
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zimbraPrefGroupMailBy", "message");
				put("zimbraPrefMessageViewHtmlPreferred", "TRUE");
				put("zimbraPrefMailTrustedSenderList", "testdoamin.com");
			}
		};
	}

	/**
	 * TestCase : Trusted Domain with message view
	 * 1.Set domain in Preference/Mail/Trusted Addresses
	 * Verify it through soap(GetPrefsRequest)
	 * 2.In message View Inject mail with external image
	 * 3.Verify To,From,Subject through soap 
	 * 4.Click on same mail
	 * 5.Yellow color Warning Msg Info bar should not present for trusted domain
	 * @throws HarnessException
	 */
	@Test(description = "Verify Display Image link in Trusted doamin for message view", groups = { "smoke" })
	public void TrustedDomainMsgView_01() throws HarnessException {

		final String subject = "TestTrustedAddress";
		final String from = "admintest@testdoamin.com";
		final String to = "admin@testdoamin.com";
		final String mimeFolder = ZimbraSeleniumProperties.getBaseDirectory()
				+ "/data/public/mime/ExternalImg.txt";

		//Verify domain through soap- GetPrefsRequest
		String PrefMailTrustedAddr = ZimbraAccount.AccountZWC().getPreference(
				"zimbraPrefMailTrustedSenderList");
		ZAssert.assertTrue(PrefMailTrustedAddr.equals("testdoamin.com"),
				"Verify doamin is present /Pref/TrustedAddr");

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

		//DisplayTrustedAddress actual = new DisplayTrustedAddress(app);

		// Verify Warning info bar with other links

		ZAssert
				.assertFalse(app.zPageMail.zHasWDDLinks(),
						"Verify Warning icon ,Display Image and Domain link  does not present");

	}

}
