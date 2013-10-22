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
package org.zmail.qa.selenium.projects.ajax.tests.mail.performance;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.performance.*;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.LmtpInject;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.*;


public class ZmMailItemHTML extends AjaxCommonTest {

	
	@SuppressWarnings("serial")
	public ZmMailItemHTML() throws HarnessException {
		logger.info("New "+ ZmMailItemHTML.class.getCanonicalName());
		
		super.startingPage = app.zPageMail;

		
		super.startingAccountPreferences = new HashMap<String, String>() {{
				    put("zmailPrefGroupMailBy", "message");
				    put("zmailPrefMessageViewHtmlPreferred", "TRUE");
				}};


	}
	
	
	@Test(	description = "Measure the performance for preview pane, html message, initial load",
			groups = { "performance" })
	public void ZmMailItem_01() throws HarnessException {
		
		String mime = ZmailSeleniumProperties.getBaseDirectory() + "/data/public/mime/email02/mime01.txt";
		String subject = "Subject13155016716713";

		LmtpInject.injectFile(app.zGetActiveAccount().EmailAddress, new File(mime));


		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		PerfToken token = PerfMetrics.startTimestamp(PerfKey.ZmMailItem, "Load preview pane, html message, initial load");

		// Select the message so that it shows in the reading pane
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		PerfMetrics.waitTimestamp(token);

	}

	@Test(	description = "Measure the performance for preview pane, html message, 1 message",
			groups = { "performance" })
	public void ZmMailItem_02() throws HarnessException {
		
		String mime = ZmailSeleniumProperties.getBaseDirectory() + "/data/public/mime/email02/mime01.txt";
		String subject = "Subject13155016716713";

		LmtpInject.injectFile(app.zGetActiveAccount().EmailAddress, new File(mime));


		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		PerfToken token = PerfMetrics.startTimestamp(PerfKey.ZmMailItem, "Load preview pane, html message, 1 message");

		// Select the message so that it shows in the reading pane
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		PerfMetrics.waitTimestamp(token);

	}



}
