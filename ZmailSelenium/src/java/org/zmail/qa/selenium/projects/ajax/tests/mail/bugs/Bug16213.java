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
package org.zmail.qa.selenium.projects.ajax.tests.mail.bugs;

import java.io.File;
import java.util.HashMap;
import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.LmtpInject;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail.Field;


public class Bug16213 extends AjaxCommonTest {


	

	@SuppressWarnings("serial")
	public Bug16213() {
		logger.info("New "+ Bug16213.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {{
			put("zmailPrefGroupMailBy", "message");
			put("zmailPrefMessageViewHtmlPreferred", "TRUE");
		}};


	}
	
	@Test(	description = "Verify bug 16213 - Message display should show From=Unknown",
			groups = { "functional" })
	public void Bug_16213MV() throws HarnessException {

		String subject = "Encoding test";
		String from = "Unknown";

		String MimeFolder = ZmailSeleniumProperties.getBaseDirectory() + "/data/private/mime/Bugs/Bug16213";
		LmtpInject.injectFile(ZmailAccount.AccountZWC().EmailAddress, new File(MimeFolder));

		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		DisplayMail display = (DisplayMail)app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		ZAssert.assertEquals(display.zGetMailProperty(Field.From), from, "Verify the default string for 'From' is 'Unknown'");

	}

}
