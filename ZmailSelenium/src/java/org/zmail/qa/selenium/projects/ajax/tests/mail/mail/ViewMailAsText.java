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
package org.zmail.qa.selenium.projects.ajax.tests.mail.mail;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail.Field;


public class ViewMailAsText extends AjaxCommonTest {

	boolean injected = false;
	final String mimeFolder = ZmailSeleniumProperties.getBaseDirectory() + "/data/public/mime/email00";
	
	public ViewMailAsText() throws HarnessException {
		logger.info("New "+ ViewMailAsText.class.getCanonicalName());
		
		
		super.startingPage = app.zPageMail;

		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = 3370780885378699878L;
		{
			put("zmailPrefGroupMailBy", "message");
			put("zmailPrefMessageViewHtmlPreferred", "FALSE");
		}};

	}
	
	
	@Test(	description = "zmailPrefMessageViewHtmlPreferred=FALSE: Receive message with text only parts - should be rendered as text",
			groups = { "functional" })
	public void ViewMail_01() throws HarnessException {
		
		final String mimeFile = ZmailSeleniumProperties.getBaseDirectory() + "/data/public/mime/email04/mimeTextOnly.txt";
		final String subject = "subject13214016725788";
		final String content = "The Ming Dynasty";

		LmtpInject.injectFile(app.zGetActiveAccount().EmailAddress, new File(mimeFile));


		
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		// Verify the To, From, Subject, Body
		ZAssert.assertStringContains(	actual.zGetMailProperty(Field.Body), content, "Verify the text content");
		
	}

	@Test(	description = "zmailPrefMessageViewHtmlPreferred=FALSE: Receive message with html only parts - should be rendered as text conversion",
			groups = { "functional" })
	public void ViewMail_02() throws HarnessException {
		
		final String mimeFile = ZmailSeleniumProperties.getBaseDirectory() + "/data/public/mime/email04/mimeHtmlOnly.txt";
		final String subject = "subject13214016672655";
		final String content = "Bold";

		LmtpInject.injectFile(app.zGetActiveAccount().EmailAddress, new File(mimeFile));


		
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		// Verify the To, From, Subject, Body
		ZAssert.assertStringContains(	actual.zGetMailProperty(Field.Body), content, "Verify the text content");
		
		
	}
	
	@Test(	description = "zmailPrefMessageViewHtmlPreferred=FALSE: Receive message with text and html  parts - should be rendered as text",
			groups = { "functional" })
	public void ViewMail_03() throws HarnessException {
		
		final String mimeFile = ZmailSeleniumProperties.getBaseDirectory() + "/data/public/mime/email04/mimeTextAndHtml.txt";
		final String subject = "subject13214016621403";
		final String content = "The Ming Dynasty";

		LmtpInject.injectFile(app.zGetActiveAccount().EmailAddress, new File(mimeFile));


		
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		// Verify the To, From, Subject, Body
		ZAssert.assertStringContains(	actual.zGetMailProperty(Field.Body), content, "Verify the text content");
		
		
	}


}
