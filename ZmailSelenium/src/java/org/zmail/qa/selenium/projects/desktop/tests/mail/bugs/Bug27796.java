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
import java.util.HashMap;

import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.mail.DisplayMail;
import org.zmail.qa.selenium.projects.desktop.ui.mail.DisplayMail.Field;


public class Bug27796 extends AjaxCommonTest {


	
	@SuppressWarnings("serial")
	public Bug27796() {
		logger.info("New "+ Bug27796.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {{
			put("zmailPrefGroupMailBy", "message");
			put("zmailPrefMessageViewHtmlPreferred", "TRUE");
		}};


	}

	@Test(	description = "Verify bug 25624",
			groups = { "functional" })
	public void Bug_27796() throws HarnessException {

		String subject = "subject13001430504374";

		String MimeFolder = ZmailSeleniumProperties.getBaseDirectory() + "/data/private/mime/Bugs/Bug27796";
		LmtpInject.injectFile(ZmailAccount.AccountZDC().EmailAddress, new File(MimeFolder));

		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail display = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		String body = display.zGetMailProperty(Field.Body);
		ZAssert.assertStringContains(body, "I realized that may be the best place to post this question", "Verify the message content is not blank");
		ZAssert.assertStringContains(body, "http://twiki.corp.yahoo.com/view/Devel/DevelRandom", "Verify the message content contains the footer");

	}



}
