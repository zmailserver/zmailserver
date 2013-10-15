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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.zimlets;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;


public class GetZimlets extends AjaxCommonTest {

	public GetZimlets() {
		
		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = null;
	}


	@Test(
			description = "View the number of zimlets in the default list",
			groups = { "functional" }
			)
	public void GetZimlets_01() throws HarnessException {

		
		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Zimlets);

		// The locator to the table
		String tablelocator = "css=div[id='ZmPrefZimletListView']";
		
		// The locator to the rows (not including the header)
		String locator = tablelocator + " div[id$='__rows']";

		// Get how many rows there are
		int count = app.zPagePreferences.sGetCssCount(locator + ">div[id^='zli__']");
		
		// IronMaiden: 5 zimlets - LinkedIn, Phone, Search Highlighter, Webex, Zmail Social
		// IronMaiden: Bug 50123: 3 zimlets - Phone, Search Highlighter, Webex, Y-Emoticons
		if(ZmailSeleniumProperties.zmailGetVersionString().contains("FOSS")){
		    ZAssert.assertEquals(count, 4, "Verify 4 zimlets are shown in the preferences page");
		}else{
		    ZAssert.assertEquals(count, 8, "Verify 8 zimlets are shown in the preferences page");
		}
	}
	
	// IronMaiden: Bug 50123: 3 zimlets - Phone, Search Highlighter, Webex
	@Bugs(ids = "50123")
	@Test(
			description = "Verify the LinkedIn table text",
			groups = { "deprecated" }
			)
	public void GetZimlets_02() throws HarnessException {

		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Zimlets);
		
		// The locator to the rows (not including the header)
		String locator = "css=div[id='ZmPrefZimletListView'] div[id$='__rows']";

		String name = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_linkedin__na']");
		String description = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_linkedin__ds']");

		ZAssert.assertEquals(name, "LinkedIn", "Verify the LinkedIn entry name");
		ZAssert.assertEquals(description, "Hooks on to email Zimlet; shows LinkedIn search result for a given email.", "Verify the LinkedIn description");		
		
	}

	@Test(
			description = "Verify the Phone table text",
			groups = { "functional" }
			)
	public void GetZimlets_03() throws HarnessException {

		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Zimlets);

		// The locator to the table
		String locator = "css=div[id='ZmPrefZimletListView'] div[id$='__rows']";

		String name = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_phone__na']");
		String description = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_phone__ds']");
		
		ZAssert.assertEquals(name, "Phone", "Verify the Phone entry exists");
		ZAssert.assertEquals(description, "Highlights phone numbers to enable Skype calls.", "Verify the Phone description");
		
		
	}

	
	@Test(
			description = "Verify the Search Highlighter table text",
			groups = { "functional" }
			)
	public void GetZimlets_04() throws HarnessException {

		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Zimlets);

		// The locator to the table
		String locator = "css=div[id='ZmPrefZimletListView'] div[id$='__rows']";

		String name = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_srchhighlighter__na']");
		String description = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_srchhighlighter__ds']");
		
		ZAssert.assertEquals(name, "Search Highlighter", "Verify the Search Highlighter entry exists");
		ZAssert.assertEquals(description, "After a mail search, this Zimlet highlights Search terms with yellow color.", "Verify the Search Highlighter description");
		
		
	}


	@Test(
			description = "Verify the WebEx table text",
			groups = { "functional" }
			)
	public void GetZimlets_05() throws HarnessException {

		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Zimlets);

		// The locator to the table
		String locator = "css=div[id='ZmPrefZimletListView'] div[id$='__rows']";

		String name = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_webex__na']");
		String description = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_webex__ds']");
		
		ZAssert.assertEquals(name, "WebEx", "Verify the WebEx entry exists");
		ZAssert.assertEquals(description, "Easily schedule, start or join WebEx meetings.", "Verify the WebEx description");
		
		
	}

	// IronMaiden: Bug 50123: 3 zimlets - Phone, Search Highlighter, Webex
	@Bugs(ids = "50123")
	@Test(
			description = "Verify the Zmail Social table text",
			groups = { "deprecated" }
			)
	public void GetZimlets_06() throws HarnessException {

		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Zimlets);

		// The locator to the table
		String locator = "css=div[id='ZmPrefZimletListView'] div[id$='__rows']";

		String name = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_social__na']");
		String description = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_social__ds']");
		
		ZAssert.assertEquals(name, "Zmail Social", "Verify the Zmail Social entry exists");
		ZAssert.assertEquals(description, "Access social services like Twitter, Facebook, Digg and TweetMeme.", "Verify the Zmail Social description");
		
		
	}

	@Test(
			description = "Verify the Y-Emoticons table text",
			groups = { "functional" }
			)
	public void GetZimlets_07() throws HarnessException {

		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Zimlets);

		// The locator to the table
		String locator = "css=div[id='ZmPrefZimletListView'] div[id$='__rows']";

		String name = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_ymemoticons__na']");
		String description = app.zPagePreferences.sGetText(locator + " td[id$='__org_zmail_ymemoticons__ds']");
		
		ZAssert.assertEquals(name, "Yahoo! Emoticons", "Verify the Y Emoticons entry exists");
		ZAssert.assertEquals(description, "Displays Yahoo! Emoticons images in email messages.", "Verify the Y Emoticons description");
		
		
	}



}
