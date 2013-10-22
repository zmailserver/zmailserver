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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.bugs;

import java.io.File;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.LmtpInject;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.DisplayMail;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.DisplayMail.Field;


public class Bug21415 extends PrefGroupMailByMessageTest {


	
	public Bug21415() {
		logger.info("New "+ Bug21415.class.getCanonicalName());

		
		

		
		


	}

	@Test(	description = "Verify bug 21415",
			groups = { "functional" })
	public void Test21415_01() throws HarnessException {

		String subject1 = "subject12998858731253";
		String beginningContent = "Uso Interno";
		String endingContent = "Esta mensagem";

		String MimeFolder = ZimbraSeleniumProperties.getBaseDirectory() + "/data/private/mime/Bugs/Bug21415";
		LmtpInject.injectFile(ZimbraAccount.AccountZWC().EmailAddress, new File(MimeFolder));

		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail display = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject1);
		
		// Get the body
		String body = display.zGetMailProperty(Field.Body);
		
		// Make sure both the beginning and ending text appear
		ZAssert.assertStringContains(body, beginningContent, "Verify the ending text appears");
		ZAssert.assertStringContains(body, endingContent, "Verify the ending text appears");
		

	}

	@Test(	description = "Verify bug 21415",
			groups = { "functional" })
	public void Test21415_02() throws HarnessException {

		String subject1 = "subject12998912514374";
		String beginningContent = "Change 77406";
		String endingContent = "SkinResources.java";

		String MimeFolder = ZimbraSeleniumProperties.getBaseDirectory() + "/data/private/mime/Bugs/Bug21415";
		LmtpInject.injectFile(ZimbraAccount.AccountZWC().EmailAddress, new File(MimeFolder));

		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail display = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject1);
		
		// Get the body
		String body = display.zGetMailProperty(Field.Body);
		
		// Make sure both the beginning and ending text appear
		ZAssert.assertStringContains(body, beginningContent, "Verify the ending text appears");
		ZAssert.assertStringContains(body, endingContent, "Verify the ending text appears");
		

	}



}
