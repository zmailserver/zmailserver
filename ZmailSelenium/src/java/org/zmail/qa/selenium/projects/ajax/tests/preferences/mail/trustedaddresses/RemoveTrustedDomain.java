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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.mail.trustedaddresses;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;




public class RemoveTrustedDomain extends AjaxCommonTest {

	// For RemoveTrustedDomain_01
	public String domain1 = "@domain"+ ZmailSeleniumProperties.getUniqueString() + ".com";
	
	
	
	public RemoveTrustedDomain() throws HarnessException {
		
		super.startingPage = app.zPagePreferences;
		
		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = -1475986145425100378L;
			{
				put("zmailPrefMailTrustedSenderList", domain1);
			}
		};
		
	}

	
	@Test(
			description = "Remove a trusted domain",
			groups = { "smoke" }
			)
	public void RemoveTrustedDomain_01() throws HarnessException {

		/* test properties */

		
		/* GUI steps */

		// Navigate to preferences -> mail -> Trusted Addresses
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.MailTrustedAddresses);
		
		// Select the email address
		String locator = "css=td[id$='_LISTVIEW'] td:contains("+ domain1 +")";
		app.zPagePreferences.zClick(locator);
		
		// Click "Remove"
		app.zPagePreferences.zClick("css=td[id$='_REMOVE_BUTTON'] td[id$='_title']");
		
		// Click "Save"
		app.zPagePreferences.zToolbarPressButton(Button.B_SAVE);
		
		// Wait for the ModifyPrefsRequest to complete
		app.zPagePreferences.zWaitForBusyOverlay();
		
		
		/* Test verification */
		
		app.zGetActiveAccount().soapSend(
					"<GetPrefsRequest xmlns='urn:zmailAccount'>"
				+		"<pref name='zmailPrefMailTrustedSenderList'/>"
				+	"</GetPrefsRequest>");

		String found = null;
		Element[] nodes = app.zGetActiveAccount().soapSelectNodes("//acct:pref[@name='zmailPrefMailTrustedSenderList']");
		for (Element e : nodes) {
			if ( e.getText().contains(domain1) ) {
				found = e.getText();
				break;
			}
		}
		ZAssert.assertNull(found, "Verify that the domain is no longer included in the server prefs");
		
	}

}
