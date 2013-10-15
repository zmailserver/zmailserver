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

import org.testng.annotations.Test;


import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderMountpointItem;
import org.zmail.qa.selenium.framework.items.TaskItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;



public class DeleteEditMove extends AjaxCommonTest {
	
	@SuppressWarnings("serial")
	public DeleteEditMove() {
		logger.info("New "+ DeleteEditMove.class.getCanonicalName());
		super.startingPage = app.zPageTasks;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefReadingPaneLocation", "bottom");
				put("zmailPrefTasksReadingPaneLocation", "bottom");
				put("zmailPrefGroupMailBy", "message");
				put("zmailPrefShowSelectionCheckbox", "TRUE");
			}
		};			
		
	}
	
	@Test(	description = "Verify Delete Edit Move button are visible on mountpoint task (manager rights)",
			groups = { "functional" })
	public void DeleteEditMove_01() throws HarnessException {
		
		String foldername = "tasklist" + ZmailSeleniumProperties.getUniqueString();
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String mountpointname = "mountpoint" + ZmailSeleniumProperties.getUniqueString();
		
		FolderItem task = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Tasks );
		
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
		
		
		TaskItem task1 = TaskItem.importFromSOAP(ZmailAccount.AccountA(), subject);
		ZAssert.assertNotNull(task1, "Verify the task added");
		
		// Mount it
		app.zGetActiveAccount().soapSend(
					"<CreateMountpointRequest xmlns='urn:zmailMail'>"
				+		"<link l='1' name='"+ mountpointname +"' view='task' rid='"+ folder.getId() +"' zid='"+ ZmailAccount.AccountA().ZmailId +"'/>"
				+	"</CreateMountpointRequest>");
		
		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointname);

		// Refresh the tasks view
		app.zPageTasks.zToolbarPressButton(Button.B_REFRESH);
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, task);
		
		// Click on the mountpoint
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, mountpoint);

		// Select the item
		app.zPageTasks.zListItem(Action.A_LEFTCLICK, subject);
		
		// Verify delete,edit,move buttons are enable
		ZAssert.assertTrue(app.zPageTasks.sIsElementPresent("css=div[id='zb__TKL-main__DELETE'][style:contains('visible')]"), "Verify Delete button is enable");
		ZAssert.assertTrue(app.zPageTasks.sIsElementPresent("css=div[id='zb__TKL-main__EDIT'][style:contains('visible')]"), "Verify Edit button is enable");
		ZAssert.assertTrue(app.zPageTasks.sIsElementPresent("css=div[id='zb__TKL-main__MOVE_MENU'][style:contains('visible')]"), "Verify Move button is enable");
		
	}
	
	

}
