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
package org.zmail.qa.selenium.projects.ajax.tests.tasks.tags;

import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogTag;

public class UnTagTask extends AjaxCommonTest {

	public UnTagTask() {
		logger.info("New " + UnTagTask.class.getCanonicalName());

		// All tests start at the Briefcase page
		super.startingPage = app.zPageTasks;

		super.startingAccountPreferences = null;
	}

	@Test(description = "Remove a tag from a Document using Toolbar -> Tag -> Remove Tag", groups = { "smoke" })
	public void UnTagTask_01() throws HarnessException {
		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);

		String subject = "task"+ ZmailSeleniumProperties.getUniqueString();
		app.zGetActiveAccount().soapSend(
				"<CreateTaskRequest xmlns='urn:zmailMail'>"
				+		"<m >"
				+			"<inv>"
				+				"<comp name='" + subject + "'>"
				+					"<or a='"+ app.zGetActiveAccount().EmailAddress + "'/>"
				+				"</comp>" 
				+			"</inv>"
				+			"<su>" + subject + "</su>"
				+			"<mp ct='text/plain'>"
				+				"<content>content" + ZmailSeleniumProperties.getUniqueString() + "</content>"
				+			"</mp>"
				+		"</m>"
				+	"</CreateTaskRequest>");

		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(),subject);
		ZAssert.assertNotNull(task, "Verify the task is created");

		// Refresh the tasks view
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		// Select the item
		app.zPageTasks.zListItem(Action.A_LEFTCLICK, subject);

		// Create a tag using GUI
		String tagName = "tag" + ZmailSeleniumProperties.getUniqueString();

		// Click on New Tag and check for active
		DialogTag dialogtag = (DialogTag)app.zPageTasks.zToolbarPressPulldown(Button.B_TAG, Button.O_TAG_NEWTAG);
		ZAssert.assertNotNull(dialogtag, "Verify that the Create New Tag dialog is active");
		ZAssert.assertTrue(dialogtag.zIsActive(), "Verify that the Create New Tag dialog is active");

		//Fill Name  and Press OK button
		dialogtag.zSetTagName(tagName);
		dialogtag.zClickButton(Button.B_OK);

		// Make sure the tag was created on the server (get the tag ID)
		app.zGetActiveAccount().soapSend("<GetTagRequest xmlns='urn:zmailMail'/>");;
		String tagID = app.zGetActiveAccount().soapSelectValue("//mail:GetTagResponse//mail:tag[@name='"+ tagName +"']", "id");

		// Verify tagged task name
		app.zGetActiveAccount()
		.soapSend("<SearchRequest xmlns='urn:zmailMail' types='task'>"
				+ "<query>tag:"
				+ tagName
				+ "</query>"
				+ "</SearchRequest>");

		String name = app.zGetActiveAccount().soapSelectValue(
				"//mail:SearchResponse//mail:task", "name");

		ZAssert.assertEquals(name, subject,	"Verify tagged task name");

		// Make sure the tag was applied to the task
		app.zGetActiveAccount()
		.soapSend("<SearchRequest xmlns='urn:zmailMail' types='task'>"
				+ "<query>" + subject + "</query>" + "</SearchRequest>");

		String id = app.zGetActiveAccount().soapSelectValue(
				"//mail:SearchResponse//mail:task", "t");

		ZAssert.assertEquals(id, tagID,"Verify the tag was attached to the task");

		// refresh briefcase page
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		// Click on tagged document
		app.zPageTasks.zListItem(Action.A_LEFTCLICK, subject);

		// Click Remove Tag
		app.zPageTasks.zToolbarPressPulldown(Button.B_TAG, Button.O_TAG_REMOVETAG);

		app.zGetActiveAccount()
		.soapSend("<SearchRequest xmlns='urn:zmailMail' types='task'>"
				+ "<query>" + subject + "</query>" + "</SearchRequest>");

		id = app.zGetActiveAccount().soapSelectValue("//mail:SearchResponse//mail:task", "t");

		ZAssert.assertStringDoesNotContain(id,tagID, "Verify that the tag is removed from the message");		
	}
}
