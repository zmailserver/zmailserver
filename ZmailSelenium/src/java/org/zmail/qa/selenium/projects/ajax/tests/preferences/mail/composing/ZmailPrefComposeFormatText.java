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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.mail.composing;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;


public class ZmailPrefComposeFormatText extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public ZmailPrefComposeFormatText() {

		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefComposeFormat", "html");
			}
		};
	}

	@Test(
			description = "Set zmailPrefComposeFormat to 'text'",
			groups = { "functional" }
	)
	public void ZmailPrefComposeFormatText_01() throws HarnessException {

		// Navigate to preferences -> mail -> composing
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Mail);

		// Click radio button for compose = html
		// See http://bugzilla.zmail.com/show_bug.cgi?id=62322
		app.zPagePreferences.sClick("css=input[id='COMPOSE_AS_TEXT_input']");

		// Click save
		app.zPagePreferences.zToolbarPressButton(Button.B_SAVE);

		app.zGetActiveAccount().soapSend(
				"<GetPrefsRequest xmlns='urn:zmailAccount'>"
				+			"<pref name='zmailPrefComposeFormat'/>"
				+		"</GetPrefsRequest>");

		String value = app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zmailPrefComposeFormat']", null);
		ZAssert.assertEquals(value, "text", "Verify the preference was changed to 'text'");

	}
}
