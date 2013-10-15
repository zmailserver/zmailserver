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
package org.zmail.qa.selenium.projects.ajax.tests.tasks.mountpoints.manager;



import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderMountpointItem;
import org.zmail.qa.selenium.framework.items.TaskItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;



public class DragAndDropSharedTask extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public DragAndDropSharedTask() {
		logger.info("New "+ DragAndDropSharedTask.class.getCanonicalName());
		super.startingPage = app.zPageTasks;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefReadingPaneLocation", "bottom");
				put("zmailPrefTasksReadingPaneLocation", "bottom");
				put("zmailPrefShowSelectionCheckbox", "TRUE");
			}
		};			

	}

	@Test(	description = "Drag task  from shared folder and drop into local task folder (manager rights)",
			groups = { "functional" })
			public void DragAndDropSharedTaskToLocalFolder() throws HarnessException {

		String foldername = "tasklist" + ZmailSeleniumProperties.getUniqueString();
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String mountpointname = "mountpoint" + ZmailSeleniumProperties.getUniqueString();

		FolderItem task = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Tasks );
		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);

		// Create a folder to share
		ZmailAccount.AccountA().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername + "' l='" + task.getId() + "'/>"
				+	"</CreateFolderRequest>");

		FolderItem folder = FolderItem.importFromSOAP(ZmailAccount.AccountA(), foldername);

		// Share it
		ZmailAccount.AccountA().soapSend(
				"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ folder.getId() +"' op='grant'>"
				+			"<grant d='"+ app.zGetActiveAccount().EmailAddress +"' gt='usr' perm='rwidx'/>"
				+		"</action>"
				+	"</FolderActionRequest>");

		// Add a task to it
		ZmailAccount.AccountA().soapSend(
				"<CreateTaskRequest xmlns='urn:zmailMail'>" +
				"<m l='"+ folder.getId() +"' >" +
				"<inv>" +
				"<comp name='"+ subject +"'>" +
				"<or a='"+ ZmailAccount.AccountA().EmailAddress +"'/>" +
				"</comp>" +
				"</inv>" +
				"<su>"+ subject +"</su>" +
				"<mp ct='text/plain'>" +
				"<content>content"+ ZmailSeleniumProperties.getUniqueString() +"</content>" +
				"</mp>" +
				"</m>" +
		"</CreateTaskRequest>");

		app.zPageTasks.zToolbarPressButton(Button.B_REFRESH);
		SleepUtil.sleepMedium();
	
		TaskItem mountpointsubject = TaskItem.importFromSOAP(ZmailAccount.AccountA(), subject);
		ZAssert.assertNotNull(mountpointsubject, "Verify the task added");

		// Mount it
		app.zGetActiveAccount().soapSend(
				"<CreateMountpointRequest xmlns='urn:zmailMail'>"
				+		"<link l='1' name='"+ mountpointname +"' view='task' rid='"+ folder.getId() +"' zid='"+ ZmailAccount.AccountA().ZmailId +"'/>"
				+	"</CreateMountpointRequest>");

		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointname);

		// Refresh the tasks view
		app.zPageTasks.zToolbarPressButton(Button.B_REFRESH);
		SleepUtil.sleepMedium();
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, task);

		// Click on the mountpoint
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, mountpoint);

		// Select the item
		app.zPageTasks.zListItem(Action.A_LEFTCLICK, subject);

		//Drag and drop task from shared to local task folder
		app.zPageMail.zDragAndDrop(
				"css=td[id$='"+mountpointsubject.getId() +"__su']",
				"css=td[id='zti__main_Tasks__"+ taskFolder.getId() + "_textCell']:contains('"+ taskFolder.getName() + "')");

		// refresh tasks page
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK,mountpoint);

		List<TaskItem> tasks = app.zPageTasks.zGetTasks();
		TaskItem found = null;
		for (TaskItem t : tasks) {
			logger.info("Task: looking for " + subject + " found: "
					+ t.gSubject);
			if (subject.equals(t.gSubject)) {
				// Found it!
				found = t;
				break;
			}
		}

		ZAssert.assertNull(found,"Verify the  task no longer  present in the mounted folder");

		// click on subfolder in tree view
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);
		List<TaskItem> tasks1 = app.zPageTasks.zGetTasks();

		TaskItem movetask = null;
		for (TaskItem t : tasks1) {
			logger.info("Task: looking for " + subject + " found: "
					+ t.gSubject);
			if (subject.equals(t.gSubject)) {
				// Found it!
				movetask = t;
				break;
			}
		}
		ZAssert.assertNotNull(movetask,	"Verify the task is drag and drop to the local folder");
	}


	@Test(	description = "Drag task from local task folder to shared folder(manager rights)",
			groups = { "functional" })
			public void DragAndDropTaskToSharedFolder() throws HarnessException {

		String foldername = "tasklist" + ZmailSeleniumProperties.getUniqueString();
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String mountpointname = "mountpoint" + ZmailSeleniumProperties.getUniqueString();

		FolderItem task = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Tasks );
		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);

		// Create a folder to share
		ZmailAccount.AccountA().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername + "' l='" + task.getId() + "'/>"
				+	"</CreateFolderRequest>");

		FolderItem folder = FolderItem.importFromSOAP(ZmailAccount.AccountA(), foldername);

		// Share it
		ZmailAccount.AccountA().soapSend(
				"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ folder.getId() +"' op='grant'>"
				+			"<grant d='"+ app.zGetActiveAccount().EmailAddress +"' gt='usr' perm='rwidx'/>"
				+		"</action>"
				+	"</FolderActionRequest>");


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

		app.zPageTasks.zToolbarPressButton(Button.B_REFRESH);
		
		TaskItem task1 = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertNotNull(task1, "Verify the task added");

		// Mount it
		app.zGetActiveAccount().soapSend(
				"<CreateMountpointRequest xmlns='urn:zmailMail'>"
				+		"<link l='1' name='"+ mountpointname +"' view='task' rid='"+ folder.getId() +"' zid='"+ ZmailAccount.AccountA().ZmailId +"'/>"
				+	"</CreateMountpointRequest>");

		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointname);

		// Refresh the tasks view
		app.zPageTasks.zToolbarPressButton(Button.B_REFRESH);
		SleepUtil.sleepMedium();
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, task);

		// Select the item
		app.zPageTasks.zListItem(Action.A_LEFTCLICK, subject);

		//Drag and drop task from local to shared task folder
		app.zPageMail.zDragAndDrop(
				"css=td[id$='"+task1.getId() +"__su']",
				"css=td[id='zti__main_Tasks__"+ mountpoint.getId() + "_textCell']:contains('"+ mountpoint.getName() + "')");


		// refresh tasks page
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK,taskFolder);

		List<TaskItem> tasks = app.zPageTasks.zGetTasks();
		TaskItem found = null;
		for (TaskItem t : tasks) {
			logger.info("Task: looking for " + subject + " found: "
					+ t.gSubject);
			if (subject.equals(t.gSubject)) {
				// Found it!
				found = t;
				break;
			}
		}

		ZAssert.assertNull(found,"Verify the  task is no longer  present in the task list");

		// click on subfolder in tree view
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, mountpoint);
		List<TaskItem> tasks1 = app.zPageTasks.zGetTasks();

		TaskItem movetask = null;
		for (TaskItem t : tasks1) {
			logger.info("Task: looking for " + subject + " found: "
					+ t.gSubject);
			if (subject.equals(t.gSubject)) {
				// Found it!
				movetask = t;
				break;
			}
		}
		ZAssert.assertNotNull(movetask,	"Verify the task is Drag and drop to the mounted/shared folder");
	}



}

