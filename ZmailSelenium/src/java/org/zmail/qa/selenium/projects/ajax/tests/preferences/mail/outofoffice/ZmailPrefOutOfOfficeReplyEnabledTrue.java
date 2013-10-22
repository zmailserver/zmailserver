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
package com.zimbra.qa.selenium.projects.ajax.tests.preferences.mail.outofoffice;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;

public class ZimbraPrefOutOfOfficeReplyEnabledTrue extends AjaxCommonTest {


	public ZimbraPrefOutOfOfficeReplyEnabledTrue() throws HarnessException {
		
		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = -3101848474022410670L;
			{
				put("zimbraPrefOutOfOfficeReplyEnabled", "FALSE");
			}
		};
		
	}

	@Test(
			description = "Enable out of office",
			groups = { "smoke" }
			)
	public void ZimbraPrefOutOfOfficeReplyEnabledTrue_01() throws HarnessException {

		/* test properties */
		final String message = "message" + ZimbraSeleniumProperties.getUniqueString();

	
		/* GUI steps */

		// Navigate to preferences -> mail -> Out of Office
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.MailOutOfOffice);
		
		// Enable the preferences
		app.zPagePreferences.sClick("css=input[id$='VACATION_MSG_ENABLED_input']");
		
		// Add a message
		app.zPagePreferences.sType("css=textarea[id$='_VACATION_MSG']", message);
		
		// Click "Save"
		app.zPagePreferences.zToolbarPressButton(Button.B_SAVE);
		
		// Wait for the ModifyPrefsRequest to complete
		app.zPagePreferences.zWaitForBusyOverlay();
		
		
		/* Test verification */
		
		app.zGetActiveAccount().soapSend(
					"<GetPrefsRequest xmlns='urn:zimbraAccount'>"
				+		"<pref name='zimbraPrefOutOfOfficeReplyEnabled'/>"
				+		"<pref name='zimbraPrefOutOfOfficeReply'/>"
				+		"<pref name='zimbraPrefOutOfOfficeStatusAlertOnLogin'/>"
				+	"</GetPrefsRequest>");

		String zimbraPrefOutOfOfficeReplyEnabled = app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zimbraPrefOutOfOfficeReplyEnabled']", null);
		String zimbraPrefOutOfOfficeReply = app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zimbraPrefOutOfOfficeReply']", null);
		String zimbraPrefOutOfOfficeStatusAlertOnLogin = app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zimbraPrefOutOfOfficeStatusAlertOnLogin']", null);
		
		ZAssert.assertEquals(zimbraPrefOutOfOfficeReplyEnabled, "TRUE", "Verify zimbraPrefOutOfOfficeReplyEnabled is TRUE");
		ZAssert.assertEquals(zimbraPrefOutOfOfficeReply, message, "Verify zimbraPrefOutOfOfficeReply contains the message");
		ZAssert.assertEquals(zimbraPrefOutOfOfficeStatusAlertOnLogin, "TRUE", "Verify zimbraPrefOutOfOfficeStatusAlertOnLogin is TRUE");
		
		
	}

}
