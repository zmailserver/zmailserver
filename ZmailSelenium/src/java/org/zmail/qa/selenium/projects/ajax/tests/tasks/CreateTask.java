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
package org.zmail.qa.selenium.projects.ajax.tests.tasks;

import java.util.HashMap;
import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.TaskItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.ui.Shortcut;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogWarning;
import org.zmail.qa.selenium.projects.ajax.ui.tasks.FormTaskNew;
import org.zmail.qa.selenium.projects.ajax.ui.tasks.FormTaskNew.Field;

public class CreateTask extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public CreateTask() {
		logger.info("New " + CreateTask.class.getCanonicalName());
		super.startingPage = app.zPageTasks;
		super.startingAccountPreferences = new HashMap<String , String>() {{
			put("zmailPrefShowSelectionCheckbox", "TRUE");
			put("zmailPrefComposeFormat", "text");
			put("zmailPrefGroupMailBy", "message");
			put("zmailPrefTasksReadingPaneLocation", "bottom");
		}};
	}

	@Test(	description = "Create Simple task through GUI - verify through soap",
			groups = { "sanity" })
			public void CreateTask_01() throws HarnessException {

		String subject = "task" + ZmailSeleniumProperties.getUniqueString();
		String body = "taskbody"+ ZmailSeleniumProperties.getUniqueString();

		// Click NEW button
		FormTaskNew taskNew = (FormTaskNew) app.zPageTasks.zToolbarPressButton(Button.B_NEW);

		// Fill out the resulting form
		taskNew.zFillField(Field.Subject, subject);
		taskNew.zFillField(Field.Body, body);
		taskNew.zSubmit();
		SleepUtil.sleepMedium();

		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertEquals(task.getName(), subject, "Verify task subject");
		ZAssert.assertEquals(task.gettaskBody().trim(), body.trim(), "Verify the task body");

	}
	/**
	 * Test Case :Create new task using keyboard shortcut Esc
	 * Open new Task 
	 * Enter Subject and body
	 * Press Escape 'Esc' shortcut 
	 * Waring dialog should pop up and press Yes
	 * Task should show in list 
	 * @throws HarnessException
	 */
	@Test(description = "Create new task using keyboard shortcut Esc- Verify through Soap", groups = { "smoke" })
	public void CreateTask_02() throws HarnessException {

		Shortcut shortcut = Shortcut.S_ESCAPE;
		String subject = "task" + ZmailSeleniumProperties.getUniqueString();
		String body = "taskbody" + ZmailSeleniumProperties.getUniqueString();
		
		//Click NEW button
		FormTaskNew taskNew = (FormTaskNew) app.zPageTasks.zToolbarPressButton(Button.B_NEW);
		
		//Fill out resulting form		
		taskNew.zFillField(Field.Subject, subject);
		taskNew.zFillField(Field.Body, body);
				
		//Click Escape shortcut 'Esc'	
		DialogWarning warning =(DialogWarning)app.zPageTasks.zKeyboardShortcut(shortcut);
		ZAssert.assertNotNull(warning, "Verify the dialog is opened");

		//Click Yes button of warning dialog
		warning.zClickButton(Button.B_YES);

		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertEquals(task.getName(), subject, "Verify task subject");
		ZAssert.assertEquals(task.gettaskBody().trim(), body.trim(), "Verify the task body");

	}
	
	@Test(	description = "Create task using New menu pulldown  - verify through SOAP",	groups = { "smoke" })
	public void CreateTask_03() throws HarnessException {

		String subject = "task" + ZmailSeleniumProperties.getUniqueString();
		String body = "taskbody" + ZmailSeleniumProperties.getUniqueString();

		// Click NEW drop down and click Task
		FormTaskNew taskNew = (FormTaskNew) app.zPageTasks
				.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_TASK);

		// Fill out the resulting form
		taskNew.zFillField(Field.Subject, subject);
		taskNew.zFillField(Field.Body, body);
		taskNew.zSubmit();

		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(),subject);
		ZAssert.assertEquals(task.getName(), subject, "Verify task subject");
		ZAssert.assertEquals(task.gettaskBody().trim(), body.trim(),"Verify the task body");

	}
	/**
	 * Test Case :Create new task using keyboard shortcut NK (New Task)
	 * Open new Task 
	 * Enter Subject and body
	 * Press 'NK' shortcut 
	 * New Task page should get open
	 * Fill required inputs and save it
	 * Task should show in list 
	 * @throws HarnessException
	 */
	@Test(description = "Create new task using keyboard shortcut 'NK'- Verify through Soap", groups = { "smoke" })
	public void CreateTask_04() throws HarnessException {

		Shortcut shortcut = Shortcut.S_NEWTASK;
		String subject = "task" + ZmailSeleniumProperties.getUniqueString();
		String body = "taskbody" + ZmailSeleniumProperties.getUniqueString();
		
		//Click NEW Task shortcut "NK"
		FormTaskNew taskNew = (FormTaskNew) app.zPageTasks.zKeyboardShortcut(shortcut);
		
		//Fill out resulting form		
		taskNew.zFillField(Field.Subject, subject);
		taskNew.zFillField(Field.Body, body);
		taskNew.zSubmit();		

		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertEquals(task.getName(), subject, "Verify task subject");
		ZAssert.assertEquals(task.gettaskBody().trim(), body.trim(), "Verify the task body");

	}
	
	@Test(	description = "Create Tasks, using 'Right Click' Mail subject -> 'Create Task'-Verify through Soap",
			groups = { "smoke" })
	public void CreateTask_05() throws HarnessException {
		
		app.zPageMail.zNavigateTo();
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();	

		// Send a message to the account
		ZmailAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>content"+ ZmailSeleniumProperties.getUniqueString() +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");
		
		// Get the mail item for the new message
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		SleepUtil.sleepMedium();
		
		//Click on subject
		app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);
		
		
		//Right click subject >> select Create Task menu item
		app.zPageMail.zListItem(Action.A_RIGHTCLICK, Button.O_CREATE_TASK, mail.dSubject);
		
		//click save
		app.zPageTasks.zToolbarPressButton(Button.B_SAVE);
		
		//Verify task created.
		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertEquals(task.getName(), subject, "Verify task subject");
	}

}
