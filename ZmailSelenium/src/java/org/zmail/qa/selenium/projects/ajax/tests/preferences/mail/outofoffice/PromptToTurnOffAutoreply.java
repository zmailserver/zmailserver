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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.mail.outofoffice;

import java.util.HashMap;
import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.AppAjaxClient;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.DialogOOOAlert;

public class PromptToTurnOffAutoreply extends AjaxCommonTest {
	final String autoreplyMessage = "OOO";

	public PromptToTurnOffAutoreply() {
		logger.info("New " + PromptToTurnOffAutoreply.class.getCanonicalName());

		// test starts in the Mail tab
		super.startingPage = app.zPageMail;

		// use an account with OOO auto-reply enabled
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;
			{
				put("zmailPrefOutOfOfficeReplyEnabled", "TRUE");
				put("zmailPrefOutOfOfficeReply", autoreplyMessage);
			}
		};
	}

	@Bugs(ids = "51990")
	@Test(description = "Enable auto-reply message - Verify after login  alert dialog promts to turn off auto-reply", groups = { "functional" })
	public void PromptToTurnOffAutoreply_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		if (ZmailSeleniumProperties.zmailGetVersionString().contains("8.0.")) {
			DialogOOOAlert alert = new DialogOOOAlert(app,
					((AppAjaxClient) app).zPageMail);

			ZAssert.assertTrue(alert.zIsActive(),
					"Verify turn off auto-reply alert dialog is displayed");

			alert.zCheckboxSet(true);
			
			alert.zClickButton(Button.B_NO);
		}

		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();

		// Send message to self
		account.soapSend("<SendMsgRequest xmlns='urn:zmailMail'>" + "<m>"
				+ "<e t='t' a='" + account.EmailAddress + "'/>" + "<su>"
				+ subject + "</su>" + "<mp ct='text/plain'>"
				+ "<content>content"
				+ ZmailSeleniumProperties.getUniqueString() + "</content>"
				+ "</mp>" + "</m>" + "</SendMsgRequest>");

		// Click on Mail tab
		app.zPageMain.zClickAt("id=zb__App__Mail_title", "0,0");

		// Click Get Mail button to view folder in list
		// app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		SleepUtil.sleepMedium();

		MailItem mailItem = MailItem.importFromSOAP(account, "in:inbox "
				+ autoreplyMessage);

		ZAssert.assertTrue(mailItem.dBodyText.contains(autoreplyMessage),
				"Verify auto-reply message is received");
	}
}
