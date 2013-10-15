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
package org.zmail.qa.selenium.projects.desktop.tests.tasks;


import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.TaskItem;
import org.zmail.qa.selenium.framework.ui.AbsDialog;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.tasks.FormTaskNew;
import org.zmail.qa.selenium.projects.desktop.ui.tasks.FormTaskNew.Field;

public class CancelTask extends AjaxCommonTest {
	@SuppressWarnings("serial")
   public CancelTask() {
		logger.info("New " + CancelTask.class.getCanonicalName());

		super.startingPage = app.zPageTasks;

		super.startingAccountPreferences = new HashMap<String , String>() {{
         put("zmailPrefComposeFormat", "html");
      }};
	}
	/**
	 * Test Case: CancelTask_01
	 * Open new Task 
	 * Enter Subject and body
	 * Press Cancel button
	 * Waring dialog should pop up and press no
	 * Task should not show in list 
	 * @throws HarnessException
	 */
	@Test(description = "Cancel composing of new task through GUI", groups = { "functional" })
	public void CancelTask_01() throws HarnessException {

		String subject = "task" + ZmailSeleniumProperties.getUniqueString();
		String body = "taskbody" + ZmailSeleniumProperties.getUniqueString();
		
		//Click NEW button
		FormTaskNew taskNew = (FormTaskNew) app.zPageTasks.zToolbarPressButton(Button.B_NEW);
		
		//Fill out resulting form
		taskNew.zFillField(Field.Subject, subject);
		taskNew.zFillField(Field.Body, body);
		
		//Click Cancel , to cancel the compose
		AbsDialog warning = (AbsDialog) taskNew.zToolbarPressButton(Button.B_CANCEL);
		ZAssert.assertNotNull(warning, "Verify the dialog is returned");

		//Click No button of warning dialog
		warning.zClickButton(Button.B_NO);

		List<TaskItem> tasks = app.zPageTasks.zGetTasks();

		TaskItem found = null;
		for (TaskItem t : tasks) {
			logger.info("Subject: looking for " + subject + " found: "
					+ t.gSubject);
			if (subject.equals(t.gSubject)) {
				found = t;
				break;
			}
		}

		ZAssert.assertNull(found, "Verify the task is no longer present in task list");

	}
}
