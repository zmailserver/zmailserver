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

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.XmlStringUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.mail.FormMailNew;


public class ReplyAllMailHtml extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public ReplyAllMailHtml() {
		logger.info("New "+ ReplyAllMailHtml.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = new HashMap<String, String>() {{
				    put("zmailPrefComposeFormat", "html");
				}};

		
	}
	
	@Test(	description = "Reply All to an html mail using html editor",
			groups = { "smoke" })
	public void replyAllHtmlMail() throws HarnessException {
		
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
		String bodyText = "text" + ZmailSeleniumProperties.getUniqueString();
		String bodyHTML = "text <strong>bold"+ ZmailSeleniumProperties.getUniqueString() +"</strong> text";
		String contentHTML = XmlStringUtil.escapeXml(
				"<html>" +
					"<head></head>" +
					"<body>"+ bodyHTML +"</body>" +
				"</html>");


		// Send a message to the account
		ZmailAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='multipart/alternative'>" +
								"<mp ct='text/plain'>" +
									"<content>"+ bodyText +"</content>" +
								"</mp>" +
								"<mp ct='text/html'>" +
									"<content>"+ contentHTML +"</content>" +
								"</mp>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");
		
		// Get the mail item for the new message
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");
		

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
						
		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);
		
		// Reply to the item
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_REPLYALL);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Send the message
		mailform.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		// From the receiving end, verify the message details
		// Need 'in:inbox' to seprate the message from the sent message
      MailItem received = MailItem.importFromSOAP(ZmailAccount.AccountA(), "in:inbox subject:("+ mail.dSubject +")");

		ZAssert.assertEquals(received.dFromRecipient.dEmailAddress, app.zGetActiveAccount().EmailAddress, "Verify the from field is correct");
		ZAssert.assertEquals(received.dToRecipients.get(0).dEmailAddress, ZmailAccount.AccountA().EmailAddress, "Verify the to field is correct");
		ZAssert.assertStringContains(received.dSubject, mail.dSubject, "Verify the subject field is correct");
		ZAssert.assertStringContains(received.dSubject, "Re", "Verify the subject field contains the 'Re' prefix");
		
	}



}
