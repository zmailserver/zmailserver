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

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail.Field;


public class Bug13911 extends PrefGroupMailByMessageTest {


	
	public Bug13911() {
		logger.info("New "+ Bug13911.class.getCanonicalName());

		
		

		
		


	}

	@Test(	description = "Verify bug 13911",
			groups = { "functional" })
	public void Bug_13911() throws HarnessException {

		
		String subject = "subject13010064065623";
		String bodyBeforeImage = "K\u00e6re alle";
		String bodyAfterImage = "Problemet best\u00E5r";

		String MimeFolder = ZmailSeleniumProperties.getBaseDirectory() + "/data/private/mime/Bugs/Bug13911";
		LmtpInject.injectFile(ZmailAccount.AccountZWC().EmailAddress, new File(MimeFolder));

		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail display = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		String body = display.zGetMailProperty(Field.Body);
		
		ZAssert.assertStringContains(body, bodyBeforeImage, "Verify the text before the image");
		ZAssert.assertStringContains(body, bodyAfterImage, "Verify the text after the image");

	}



}
