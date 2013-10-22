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
package com.zimbra.qa.selenium.projects.ajax.tests.preferences.quickcommands;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.core.Bugs;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxQuickCommandTest;
import com.zimbra.qa.selenium.projects.ajax.ui.preferences.DialogEditQuickCommand;
import com.zimbra.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;


public class EditQuickCommand extends AjaxQuickCommandTest {

	public EditQuickCommand() {
		
		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = null;
	}
	


	@Bugs(ids = "71389")	// Hold off on GUI implementation of Quick Commands in 8.X
	@Test(
			description = "Edit a basic Quick Command",
			groups = { "deprecated" }
			)
	public void EditQuickCommand_01() throws HarnessException {
		
		
		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.QuickCommands);

		// Select the quick command
		String locator = "css=div[id='zl__QCV__rows'] div[id^='zli__QCV__'] td[id$='_na']:contains('"+ this.getQuickCommand01().getName() +"')";
		ZAssert.assertTrue(app.zTreePreferences.sIsElementPresent(locator), "Verify quick command 1 is in the list");
		app.zTreePreferences.zClickAt(locator, "");
		app.zTreePreferences.zWaitForBusyOverlay();

		// Click "Edit"
		DialogEditQuickCommand dialog = (DialogEditQuickCommand)app.zPagePreferences.zToolbarPressButton(Button.B_EDIT_QUICK_COMMAND);

		// TODO: modify the quick command
		dialog.zClickButton(Button.B_OK);
		
		// Get the quick commands from the server.  Verify the edited quick command is there.
		throw new HarnessException("implement me!");
		
	}
}
