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
package org.zmail.qa.selenium.projects.desktop.tests.mail.mail;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.*;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.mail.DisplayMail;
import org.zmail.qa.selenium.projects.desktop.ui.mail.DisplayMail.Field;


public class ViewMail extends AjaxCommonTest {

	
	@SuppressWarnings("serial")
	public ViewMail() {
		logger.info("New "+ ViewMail.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {{
		      put("zmailPrefGroupMailBy", "message");
				put("zmailPrefMessageViewHtmlPreferred", "TRUE");
				}};


	}
	
	@Bugs(	ids = "57047" )
	@Test(	description = "Receive a mail with Sender: specified",
			groups = { "functional" })
	public void ViewMail_01() throws HarnessException {
		
		final String subject = "subject12996131112962";
		final String from = "from12996131112962@example.com";
		final String sender = "sender12996131112962@example.com";
		final String mimeFolder = ZmailSeleniumProperties.getBaseDirectory() + "/data/public/mime/email00";

		// Inject the example message(s)
		LmtpInject.injectFile(app.zGetActiveAccount().EmailAddress, new File(mimeFolder));
		
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertNotNull(mail, "Verify message is received");
		ZAssert.assertEquals(from, mail.dFromRecipient.dEmailAddress, "Verify the from matches");
		ZAssert.assertEquals(sender, mail.dSenderRecipient.dEmailAddress, "Verify the sender matches");
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		// Verify the To, From, Subject, Body
		ZAssert.assertEquals(	actual.zGetMailProperty(Field.OnBehalfOf), from, "Verify the On-Behalf-Of matches the 'From:' header");
		ZAssert.assertEquals(	actual.zGetMailProperty(Field.From), sender, "Verify the From matches the 'Sender:' header");
		

		
	}

	@Test(	description = "Receive a mail with Reply-To: specified",
			groups = { "functional" })
	public void ViewMail_02() throws HarnessException {
		
		final String subject = "subject13016959916873";
		final String from = "from13016959916873@example.com";
		final String replyto = "replyto13016959916873@example.com";

		final String mimeFolder = ZmailSeleniumProperties.getBaseDirectory() + "/data/public/mime/email00";

		// Inject the example message(s)
		LmtpInject.injectFile(app.zGetActiveAccount().EmailAddress, new File(mimeFolder));

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageMail.zWaitForDesktopLoadingSpinner(5000);
		
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertNotNull(mail, "Verify message is received");
		ZAssert.assertEquals(from, mail.dFromRecipient.dEmailAddress, "Verify the from matches");
		ZAssert.assertEquals(replyto, mail.dReplyToRecipient.dEmailAddress, "Verify the Reply-To matches");
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		// Verify the To, From, Subject, Body
		ZAssert.assertEquals(	actual.zGetMailProperty(Field.ReplyTo), replyto, "Verify the Reply-To matches the 'Reply-To:' header");
		ZAssert.assertEquals(	actual.zGetMailProperty(Field.From), from, "Verify the From matches the 'From:' header");

	}

	@AfterMethod(alwaysRun=true)
	public void resetAccountAfterTest() {
	   // This is necessary to reset the account in case the next test is trying to
	   // inject the same emails, the next tests will fail.
	   ZmailAccount.ResetAccountZDC();
	}

}
