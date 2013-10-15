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
package org.zmail.qa.selenium.projects.desktop.tests.mail.bugs;

import java.io.File;
import java.util.*;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.ConversationItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.mail.DisplayMail;
import org.zmail.qa.selenium.projects.desktop.ui.mail.DisplayMail.Field;


public class Bug16213 extends AjaxCommonTest {


	

	@SuppressWarnings("serial")
	public Bug16213() {
		logger.info("New "+ Bug16213.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {{
			put("zmailPrefGroupMailBy", "conversation");
			put("zmailPrefMessageViewHtmlPreferred", "TRUE");
			put("zmailPrefReadingPaneLocation", "bottom");
		}};


	}

	@Test(	description = "Verify bug 16213 - Conversation list should show From=<blank>",
			groups = { "functional" })
	public void Bug_16213A() throws HarnessException {

		String subject = "Encoding test";
		String to = "ljk20k00k1je";

		String MimeFolder = ZmailSeleniumProperties.getBaseDirectory() + "/data/private/mime/Bugs/Bug16213";
		LmtpInject.injectFile(ZmailAccount.AccountZDC().EmailAddress, new File(MimeFolder));

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		List<ConversationItem> items = app.zPageMail.zListGetConversations();
		ConversationItem found = null;
		for ( ConversationItem item : items ) {
			if ( item.gSubject.contains(subject) ) {
				found = item;
				break;
			}
		}
		
		ZAssert.assertNotNull(found, "Verify the message exists in the list");
		ZAssert.assertStringDoesNotContain(found.gFrom, to, "Verify the To is not contained in the From");


	}

	@Test(	description = "Verify bug 16213 - Message display should show From=Unknown",
			groups = { "functional" })
	public void Bug_16213B() throws HarnessException {

		String subject = "Encoding test";
		String from = "Unknown";

		String MimeFolder = ZmailSeleniumProperties.getBaseDirectory() + "/data/private/mime/Bugs/Bug16213";
		LmtpInject.injectFile(ZmailAccount.AccountZDC().EmailAddress, new File(MimeFolder));

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		DisplayMail display = (DisplayMail)app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		ZAssert.assertEquals(display.zGetMailProperty(Field.From), from, "Verify the default string for 'From' is 'Unknown'");

	}


}
