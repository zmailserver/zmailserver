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
package org.zmail.qa.selenium.projects.ajax.tests.calendar.folders;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.ui.Shortcut;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DialogCreateFolder;

public class CreateCalendar extends AjaxCommonTest {


	public CreateCalendar() {
		logger.info("New " + CreateCalendar.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageCalendar;
		super.startingAccountPreferences = null;
	}


	@Test(	description = "Create a new calendar by clicking 'new folder' on folder tree", 
			groups = { "sanity" })
	public void CreateCalendar_01() throws HarnessException {

		
		// Set the new calendar name
		String calendarname = "calendar" + ZmailSeleniumProperties.getUniqueString();

		
		// Click on the "New Calendar" button in the calendar tree
		DialogCreateFolder dialog = (DialogCreateFolder) app.zTreeCalendar.zPressButton(Button.B_TREE_NEWFOLDER);

		// Fill out the dialog
		dialog.zEnterFolderName(calendarname);
		dialog.zClickButton(Button.B_OK);

		
		// Make sure the folder was created on the ZCS server
		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), calendarname);
		ZAssert.assertNotNull(folder, "Verify the new folder is found");
		ZAssert.assertEquals(folder.getName(), calendarname, "Verify the server and client folder names match");
		
	}

	@Test(	description = "Create a new calendar using keyboard shortcuts", 
			groups = { "functional" })
	public void CreateCalendar_02() throws HarnessException {

		
		// Set the new calendar name
		String calendarname = "calendar" + ZmailSeleniumProperties.getUniqueString();


		// Click on the "New Calendar" button in the calendar tree
		DialogCreateFolder dialog = (DialogCreateFolder) app.zPageCalendar.zKeyboardShortcut(Shortcut.S_NEWCALENDAR);

		// Fill out the dialog
		dialog.zEnterFolderName(calendarname);
		dialog.zClickButton(Button.B_OK);

		
		// Make sure the folder was created on the ZCS server
		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), calendarname);
		ZAssert.assertNotNull(folder, "Verify the new folder is found");
		ZAssert.assertEquals(folder.getName(), calendarname, "Verify the server and client folder names match");

	}

	@Test(	description = "Create a new folder using context menu from root folder", 
			groups = { "functional" })
	public void CreateCalendar_03() throws HarnessException {

		// Set the new calendar name
		String calendarname = "calendar" + ZmailSeleniumProperties.getUniqueString();

		// Determine the calendar folder
		FolderItem root = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.UserRoot);
		
		
		// Click on the "New Calendar" button in the calendar tree
		DialogCreateFolder dialog = (DialogCreateFolder) app.zTreeCalendar.zTreeItem(Action.A_RIGHTCLICK, Button.O_NEW_CALENDAR, root);

		// Fill out the dialog
		dialog.zEnterFolderName(calendarname);
		dialog.zClickButton(Button.B_OK);

		
		// Make sure the folder was created on the ZCS server
		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), calendarname);
		ZAssert.assertNotNull(folder, "Verify the new folder is found");
		ZAssert.assertEquals(folder.getName(), calendarname, "Verify the server and client folder names match");
		
	}

	@Test(	description = "Create a new folder using mail app New -> New Folder", 
			groups = { "functional" })
	public void CreateCalendar_04() throws HarnessException {

		// Set the new calendar name
		String calendarname = "calendar" + ZmailSeleniumProperties.getUniqueString();

		// Create a new folder in the inbox
		// using the context menu + New Folder
		DialogCreateFolder dialog = (DialogCreateFolder) app.zPageCalendar.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_CALENDAR);
		ZAssert.assertNotNull(dialog, "Verify the new dialog opened");

		// Fill out the form with the basic details
		dialog.zEnterFolderName(calendarname);
		dialog.zClickButton(Button.B_OK);

		
		// Make sure the folder was created on the ZCS server
		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), calendarname);
		ZAssert.assertNotNull(folder, "Verify the new folder is found");
		ZAssert.assertEquals(folder.getName(), calendarname, "Verify the server and client folder names match");

	}


}
