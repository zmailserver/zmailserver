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
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DialogEditFolder;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DialogEditFolder.FolderColor;

public class ChangeColorCalendar extends AjaxCommonTest {

	public ChangeColorCalendar() {
		logger.info("New " + ChangeColorCalendar.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageCalendar;
		super.startingAccountPreferences = null;

	}

	@Test(	description = "Edit a Calendar, change the color (Context menu -> Edit)", 
			groups = { "functional" })
	public void ChangeColorCalendar_01() throws HarnessException {

		FolderItem root = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.UserRoot);
		ZAssert.assertNotNull(root, "Verify the inbox is available");

		// Create the subfolder
		String name = "calendar" + ZmailSeleniumProperties.getUniqueString();

		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + name + "' l='" + root.getId() + "' view='appointment'/>"
				+	"</CreateFolderRequest>");

		FolderItem calendar = FolderItem.importFromSOAP(app .zGetActiveAccount(), name);
		ZAssert.assertNotNull(calendar, "Verify the subfolder is available");

		// Click on Get Mail to refresh the folder list
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		// Rename the folder using context menu
		DialogEditFolder dialog = (DialogEditFolder) app.zTreeCalendar.zTreeItem(Action.A_RIGHTCLICK, Button.B_TREE_EDIT, calendar);
		ZAssert.assertNotNull(dialog, "Verify the dialog opened");

		// Change the color, click OK
		dialog.zSetNewColor(FolderColor.Green);
		dialog.zClickButton(Button.B_OK);

		// Check the color
		app.zGetActiveAccount().soapSend(
				"<GetFolderRequest xmlns='urn:zmailMail'>"
			+		"<folder id='" + calendar.getId() + "'/>"
			+	"</GetFolderRequest>");

		String color = app.zGetActiveAccount().soapSelectValue("//mail:folder[@name='" + calendar.getName() + "']", "color");
		ZAssert.assertEquals(color, "3", "Verify the color of the folder is set to gray (8)");
	}

}
