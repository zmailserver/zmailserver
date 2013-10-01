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
package com.zimbra.qa.selenium.projects.desktop.tests.tasks;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.GeneralUtility;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.desktop.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.desktop.ui.Toaster;
import com.zimbra.qa.selenium.projects.desktop.ui.tasks.FormTaskNew;
import com.zimbra.qa.selenium.projects.desktop.ui.tasks.FormTaskNew.Field;

public class CreateTask extends AjaxCommonTest {

   @SuppressWarnings("serial")
   public CreateTask() {
		// logger.info("New " + CreateDocument.class.getCanonicalName());
		logger.info("New " + CreateTask.class.getCanonicalName());

		super.startingPage = app.zPageTasks;

      super.startingAccountPreferences = new HashMap<String , String>() {{
         put("zimbraPrefComposeFormat", "html");
      }};
	}

	@Test(	description = "Create Simple task through GUI - verify through GUI",
			groups = { "sanity" })
	public void CreateTask_01() throws HarnessException {

		String subject = "task" + ZimbraSeleniumProperties.getUniqueString();
		String body = "taskbody" + ZimbraSeleniumProperties.getUniqueString();

		// Click NEW button
		FormTaskNew taskNew = (FormTaskNew) app.zPageTasks.zToolbarPressButton(Button.B_NEW);
		
		// Fill out the resulting form
		taskNew.zFillField(Field.Subject, subject);
		taskNew.zFillField(Field.Body, body);
		taskNew.zSubmit();

		// Verifying the toaster message
		Toaster toast = app.zPageMain.zGetToaster();
	   String toastMsg = toast.zGetToastMessage();
	   ZAssert.assertStringContains(toastMsg, "Task Saved", "Verify toast message: Task Saved");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		ZAssert.assertNotNull(app.zPageTasks.findTask(subject),
		      "Verify the new task is in the task list");
	}

}
