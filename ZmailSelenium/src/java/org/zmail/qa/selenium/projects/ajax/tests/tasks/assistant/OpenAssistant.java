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
package org.zmail.qa.selenium.projects.ajax.tests.tasks.assistant;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.ui.Shortcut;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogAssistant;

public class OpenAssistant extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public OpenAssistant() {
		logger.info("New " + OpenAssistant.class.getCanonicalName());
		
		super.startingPage = app.zPageTasks;
		super.startingAccountPreferences = new HashMap<String , String>() {{
			put("zmailPrefTasksReadingPaneLocation", "bottom");
		}};

	}
/**
 * Test case: Open the assistant
 * 1.Go to Tasks
 * 2.Press '`' or backquote
 * Result:- Zmail Assistant dialog should get open
 * @throws HarnessException
 */
	@Test(description = "Open the assistant", groups = { "deprecated" })
	public void OpenAssistant_01() throws HarnessException {

		// Click Get Mail button
		app.zPageTasks.zToolbarPressButton(Button.B_REFRESH);

		DialogAssistant assistant = (DialogAssistant) app.zPageTasks.zKeyboardShortcut(Shortcut.S_ASSISTANT);
		assistant.zClickButton(Button.B_CANCEL);
	}

}
