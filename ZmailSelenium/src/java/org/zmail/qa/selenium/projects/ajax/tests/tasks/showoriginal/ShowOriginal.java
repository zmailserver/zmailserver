/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.qa.selenium.projects.ajax.tests.tasks.showoriginal;

import java.util.*;
import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.core.*;
import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.*;
import org.zmail.qa.selenium.projects.ajax.ui.*;

public class ShowOriginal extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public ShowOriginal() {
		logger.info("New " + ShowOriginal.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageTasks;

		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefTasksReadingPaneLocation", "bottom");
				put("zmailPrefShowSelectionCheckbox", "TRUE");
			}
		};

	}

	@Bugs(ids = "51017")
	@Test(description = "Show Original Pop Up should Get Open With Proper Content", groups = { "smoke" })
	public void ShowOriginal_01() throws HarnessException {

		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);

		// Create a basic task
		String subject = "task" + ZmailSeleniumProperties.getUniqueString();

		app.zGetActiveAccount().soapSend(
				"<CreateTaskRequest xmlns='urn:zmailMail'>" + "<m >" + "<inv>"
				+ "<comp name='" + subject + "'>" + "<or a='"
				+ app.zGetActiveAccount().EmailAddress + "'/>"
				+ "</comp>" + "</inv>" + "<su>" + subject + "</su>"
				+ "<mp ct='text/plain'>" + "<content>content"
				+ ZmailSeleniumProperties.getUniqueString()
				+ "</content>" + "</mp>" + "</m>"
				+ "</CreateTaskRequest>");

		// Refresh the tasks view
		app.zPageTasks.zToolbarPressButton(Button.B_REFRESH);
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		// Select the item
		app.zPageTasks.zListItem(Action.A_MAIL_CHECKBOX, subject);

		SeparateWindowShowOriginal window = null;

		try {

			// Right click the item, select Show Original
			window = (SeparateWindowShowOriginal) app.zPageTasks.zListItem(Action.A_RIGHTCLICK, Button.O_SHOW_ORIGINAL, subject);
			window.zWaitForActive(); // Make sure the window is there
			SleepUtil.sleepMedium(); 
			ZAssert.assertTrue(window.zIsActive(),"Verify the window is active");

			// Verify show original window with proper content.
			String ShowOrigBody = window.sGetBodyText();
			ZAssert.assertStringContains(ShowOrigBody, subject,"Verify subject in showorig window");
			
			//Close Show Original window
			window.zCloseWindow();
			window = null;

		} finally {

			// Make sure to close the window
			if (window != null) {
				window.zCloseWindow();
				window = null;
			}

		}

	}
}
