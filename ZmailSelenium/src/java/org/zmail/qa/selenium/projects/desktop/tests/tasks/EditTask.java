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

import java.util.List;

import org.testng.annotations.Test;


import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.TaskItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.tasks.FormTaskNew;
import org.zmail.qa.selenium.projects.desktop.ui.tasks.FormTaskNew.Field;

public class EditTask extends AjaxCommonTest{

	public EditTask() {
		logger.info("Edit " + EditTask.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageTasks;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = null;
	}

	@Test(	description = "Create task through SOAP - edit subject and verify through GUI",groups = { "smoke" })
	public void EditTask_01() throws HarnessException {

		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);


		String subject = "task"+ ZmailSeleniumProperties.getUniqueString();
		String Newsubject = "Edittask"+ ZmailSeleniumProperties.getUniqueString();

		app.zGetActiveAccount().soapSend(
				"<CreateTaskRequest xmlns='urn:zmailMail'>" +
				"<m >" +
				"<inv>" +
				"<comp name='"+ subject +"'>" +
				"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
				"</comp>" +
				"</inv>" +
				"<su>"+ subject +"</su>" +
				"<mp ct='text/plain'>" +
				"<content>content"+ ZmailSeleniumProperties.getUniqueString() +"</content>" +
				"</mp>" +
				"</m>" +
		"</CreateTaskRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertNotNull(task, "Verify the task is created");

		// Refresh the tasks view
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		// Select the item
		app.zPageTasks.zListItem(Action.A_LEFTCLICK, subject);

		// Click edit

		FormTaskNew taskedit = (FormTaskNew) app.zPageTasks.zToolbarPressButton(Button.B_EDIT);

		//Fill new subject in subject field
		taskedit.zFillField(Field.Subject, Newsubject);
		taskedit.zSubmit();

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageTasks.zWaitForDesktopLoadingSpinner(5000);

		// Get the list of tasks in the view
		List<TaskItem> tasks = app.zPageTasks.zGetTasks();
		ZAssert.assertNotNull(tasks, "Verify the list of edited tasks exists");

		// Iterate over the task list, looking for the new task
		TaskItem found = null;
		for (TaskItem t : tasks ) {
			logger.info("Task: looking for "+ Newsubject +" found: "+ t.gSubject);
			if ( Newsubject.equals(t.gSubject) ) {
				// Found it!
				found = t;
			}
		}
		ZAssert.assertNotNull(found, "Verify the Edited task present in the task list");

		// Iterate over the task list, looking for the old task
		TaskItem foundoldtask = null;
		for (TaskItem t : tasks ) {
			logger.info("Task: looking for "+ subject +" foundeditedtask: "+ t.gSubject);
			if ( subject.equals(t.gSubject) ) {
				// Found it!
				foundoldtask = t;
				break;
			}
		}

		ZAssert.assertNull(foundoldtask, "Verify the old task no longer  present in the task list");

	}


}
