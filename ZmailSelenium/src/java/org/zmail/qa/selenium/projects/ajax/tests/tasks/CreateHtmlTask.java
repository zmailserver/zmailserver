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

import org.zmail.qa.selenium.framework.core.ClientSessionFactory;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.TaskItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.ui.Shortcut;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.XmlStringUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogWarning;
import org.zmail.qa.selenium.projects.ajax.ui.tasks.FormTaskNew;
import org.zmail.qa.selenium.projects.ajax.ui.tasks.FormTaskNew.Field;

public class CreateHtmlTask extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public CreateHtmlTask() {
		logger.info("New " + CreateHtmlTask.class.getCanonicalName());
		super.startingPage = app.zPageTasks;
		super.startingAccountPreferences = new HashMap<String , String>() {{
			put("zmailPrefGroupMailBy", "message");
			put("zmailPrefComposeFormat", "html");
			put("zmailPrefTasksReadingPaneLocation", "bottom");
			put("zmailPrefShowSelectionCheckbox", "TRUE");
		}};
	}

	@Test(	description = "Create Simple Html task through GUI - verify through soap",
			groups = { "smoke" })
			public void CreateHtmlTask_01() throws HarnessException {

		String subject = "task" + ZmailSeleniumProperties.getUniqueString();
		//String taskHtmlbody = "task<b>bold"+ ZmailSeleniumProperties.getUniqueString() + "</b>task";
		String taskHtmlbody = "body" + ZmailSeleniumProperties.getUniqueString();

		// Click NEW button
		FormTaskNew taskNew = (FormTaskNew) app.zPageTasks.zToolbarPressButton(Button.B_NEW);
		//Pull down Options drop down and select Format As Html option
		//taskNew.zToolbarPressPulldown(Button.B_OPTIONS, Button.O_OPTION_FORMAT_AS_HTML);	
			
		//Reason:With "?dev=1&debug=0", Tinymce editor in HTML mode takes more time to load 
		if(ClientSessionFactory.session().selenium().getEval("window.tinyMCE").equalsIgnoreCase("null")){
			SleepUtil.sleepVeryLong();
		}
		// Fill out the resulting form
		taskNew.zFillField(Field.Subject, subject);
		taskNew.zFillField(Field.HtmlBody, taskHtmlbody);
		taskNew.zSubmit();
		SleepUtil.sleepMedium();

		//Verify the html content of the task body		
		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertEquals(task.getName(), subject, "Verify task subject");
		ZAssert.assertStringContains(task.getHtmlTaskBody().trim().toLowerCase(), taskHtmlbody.trim(), "Verify the html content of task body");

	}
	/**
	 * Test Case :Create new task using keyboard shortcut Esc
	 * Open new Task >> select Options>> Format As Html option
	 * Enter Subject and body
	 * Press Escape 'Esc' shortcut 
	 * Waring dialog should pop up and press Yes
	 * Html Task should show in list 
	 * @throws HarnessException
	 */
	@Test(description = "Create new Html task using keyboard shortcut Esc- Verify through Soap", groups = { "smoke" })
	public void CreateHtmlTask_02() throws HarnessException {

		Shortcut shortcut = Shortcut.S_ESCAPE;
		String subject = "task" + ZmailSeleniumProperties.getUniqueString();
		//String taskHtmlbody = "task<b>bold"+ ZmailSeleniumProperties.getUniqueString() + "</b>task";
		String taskHtmlbody = "body" + ZmailSeleniumProperties.getUniqueString();

		//Click NEW button
		FormTaskNew taskNew = (FormTaskNew) app.zPageTasks.zToolbarPressButton(Button.B_NEW);

		//Pull down Options drop down and select Format As Html option
		//taskNew.zToolbarPressPulldown(Button.B_OPTIONS, Button.O_OPTION_FORMAT_AS_HTML);
		//Reason:With "?dev=1&debug=0", Tinymce editor in HTML mode takes more time to load 
		if(ClientSessionFactory.session().selenium().getEval("window.tinyMCE").equalsIgnoreCase("null")){
			SleepUtil.sleepVeryLong();
		}
		//Fill out resulting form		
		taskNew.zFillField(Field.Subject, subject);
		taskNew.zFillField(Field.HtmlBody, taskHtmlbody);

		//Click Escape shortcut 'Esc'	
		DialogWarning warning =(DialogWarning)app.zPageTasks.zKeyboardShortcut(shortcut);
		ZAssert.assertNotNull(warning, "Verify the dialog is opened");

		//Click Yes button of warning dialog
		warning.zClickButton(Button.B_YES);

		//Verify the html content of the task body		
		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertEquals(task.getName(), subject, "Verify task subject");
		ZAssert.assertStringContains(task.getHtmlTaskBody().trim().toLowerCase(), taskHtmlbody.trim(), "Verify the html content of task body");

	}

	@Test(	description = "Create Html task using New menu pulldown  - verify through SOAP",	groups = { "smoke" })
	public void CreateHtmlTask_03() throws HarnessException {

		String subject = "task" + ZmailSeleniumProperties.getUniqueString();
		//String taskHtmlbody = "task<b>bold"+ ZmailSeleniumProperties.getUniqueString() + "</b>task";
		String taskHtmlbody = "body" + ZmailSeleniumProperties.getUniqueString();

		// Click NEW drop down and click Task
		FormTaskNew taskNew = (FormTaskNew) app.zPageTasks
		.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_TASK);

		//Pull down Options drop down and select Format As Html option
		//taskNew.zToolbarPressPulldown(Button.B_OPTIONS, Button.O_OPTION_FORMAT_AS_HTML);
		//Reason:With "?dev=1&debug=0", Tinymce editor in HTML mode takes more time to load 
		if(ClientSessionFactory.session().selenium().getEval("window.tinyMCE").equalsIgnoreCase("null")){
			SleepUtil.sleepVeryLong();
		}

		// Fill out the resulting form
		taskNew.zFillField(Field.Subject, subject);
		taskNew.zFillField(Field.HtmlBody, taskHtmlbody);
		taskNew.zSubmit();
		SleepUtil.sleepMedium();

		//Verify the html content of the task body		
		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertEquals(task.getName(), subject, "Verify task subject");
		ZAssert.assertStringContains(task.getHtmlTaskBody().trim().toLowerCase(), taskHtmlbody.trim(), "Verify the html content of task body");

	}
	/**
	 * Test Case :Create new Html task using keyboard shortcut NK (New Task)
	 * Go to Task 
	 * Press 'NK' shortcut
	 * New Task page should get open >>Select Options>> Format As Html
	 * Fill required inputs and save it
	 * Html Task should show in list 
	 * @throws HarnessException
	 */
	@Test(description = "Create new Html task using keyboard shortcut 'NK'- Verify through Soap", groups = { "smoke" })
	public void CreateTask_04() throws HarnessException {

		Shortcut shortcut = Shortcut.S_NEWTASK;
		String subject = "task" + ZmailSeleniumProperties.getUniqueString();
		//String taskHtmlbody = "task<b>bold"+ ZmailSeleniumProperties.getUniqueString() + "</b>task";
		String taskHtmlbody = "body" + ZmailSeleniumProperties.getUniqueString();

		//Click NEW Task shortcut "NK"
		FormTaskNew taskNew = (FormTaskNew) app.zPageTasks.zKeyboardShortcut(shortcut);

		//Pull down Options drop down and select Format As Html option
		//taskNew.zToolbarPressPulldown(Button.B_OPTIONS, Button.O_OPTION_FORMAT_AS_HTML);
		//Reason:With "?dev=1&debug=0", Tinymce editor in HTML mode takes more time to load 
		if(ClientSessionFactory.session().selenium().getEval("window.tinyMCE").equalsIgnoreCase("null")){
			SleepUtil.sleepVeryLong();
		}

		// Fill out the resulting form
		taskNew.zFillField(Field.Subject, subject);
		taskNew.zFillField(Field.HtmlBody, taskHtmlbody);
		taskNew.zSubmit();
		SleepUtil.sleepMedium();

		//Verify the html content of the task body		
		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertEquals(task.getName(), subject, "Verify task subject");
		ZAssert.assertStringContains(task.getHtmlTaskBody().trim().toLowerCase(), taskHtmlbody.trim(), "Verify the html content of task body");

	}

	@Test(	description = "Create Html Tasks, using 'Right Click' Html Mail subject -> 'Create Task'-Verify through Soap",
			groups = { "smoke" })
	public void CreateTask_05() throws HarnessException {

		app.zPageMail.zNavigateTo();
		
		FolderItem inboxFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),SystemFolder.Inbox);
		String subject = "task" + ZmailSeleniumProperties.getUniqueString();
		String bodyText = "bodyText" + ZmailSeleniumProperties.getUniqueString();
		//String taskHtmlbody = "task<b>bold"+ ZmailSeleniumProperties.getUniqueString() + "</b>task";
		String taskHtmlbody = "body" + ZmailSeleniumProperties.getUniqueString();
		String contentHTML = XmlStringUtil.escapeXml("<html>"+"<body>"+"<div>"+"<div>"+taskHtmlbody+"</div>"+"</div>"+"</body>"+"</html>");


		// Send a message to the account
		ZmailAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
				"<m>" +
				"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
				"<su>"+ subject +"</su>" +
				"<mp ct='multipart/alternative'>" +
				"<mp ct='text/plain'>" +
				"<content>"+bodyText+"</content>" +
				"</mp>" +
				"<mp ct='text/html'>" +
				"<content>"+contentHTML+"</content>" +
				"</mp>" +
				"</mp>" +
				"</m>" +
		"</SendMsgRequest>");

		// Get the mail item for the new message
		MailItem mail = MailItem.importFromSOAP(ZmailAccount.AccountA(),"subject:(" + subject + ")");

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, inboxFolder);

		SleepUtil.sleepMedium();

		//Click on subject
		app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);


		//Right click subject >> select Create Task menu item
		app.zPageMail.zListItem(Action.A_RIGHTCLICK, Button.O_CREATE_TASK, mail.dSubject);

		//click save
		app.zPageTasks.zToolbarPressButton(Button.B_SAVE);


		//Verify the html content of the task body		
		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);

		ZAssert.assertEquals(task.getName(), subject, "Verify task subject");
		ZAssert.assertStringContains(task.getHtmlTaskBody().trim().toLowerCase(), taskHtmlbody.trim(), "Verify the html content of task body");
	}

}
