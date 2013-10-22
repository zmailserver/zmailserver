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
package com.zimbra.qa.selenium.projects.ajax.tests.tasks.folders;

import java.util.HashMap;

import org.testng.annotations.*;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.ajax.ui.tasks.DialogCreateTaskFolder;


public class CreateTaskFolder extends AjaxCommonTest {

	private boolean _folderIsCreated = false;
	private String _folderName = null;
	

	@SuppressWarnings("serial")
	public CreateTaskFolder() {
		logger.info("New " + CreateTaskFolder.class.getCanonicalName());

		// test starts at the task tab
		super.startingPage = app.zPageTasks;
		super.startingAccountPreferences = new HashMap<String , String>() {{
			put("zimbraPrefTasksReadingPaneLocation", "bottom");
			put("zimbraPrefShowSelectionCheckbox", "TRUE");
		}};
	}	

	@Test(description = "Create a new tasklist by clicking 'Create a new task' on task folders tree", groups = { "sanity" })
	public void CreateTaskFolder_01() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem taskFolder = FolderItem.importFromSOAP(account,SystemFolder.Tasks);
		
		_folderName = "taskfolder" + ZimbraSeleniumProperties.getUniqueString();
		
		//Create folder
		//DialogCreateTaskFolder createTaskFolderDialog =(DialogCreateTaskFolder)app.zTreeTasks.zPressButton(Button.B_TREE_NEWTASKLIST);
		DialogCreateTaskFolder createTaskFolderDialog =(DialogCreateTaskFolder)app.zTreeTasks.zPressPulldown(Button.B_TREE_FOLDERS_OPTIONS, Button.B_TREE_NEWTASKLIST);
		
		createTaskFolderDialog.zEnterFolderName(_folderName);
		createTaskFolderDialog.zClickButton(Button.B_OK);
		
		_folderIsCreated = true;
		
		SleepUtil.sleepVerySmall();
		
		// refresh task page
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);
				
		// Make sure the task folder was created on the ZCS server
		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(),_folderName);
		ZAssert.assertNotNull(folder, "Verify task folder is created");
		ZAssert.assertEquals(folder.getName(), _folderName,"Verify the server and client folder names match");
	}
	
	@Test(description = "Create a new tasklist using tasks app New -> New Task Folder", groups = { "functional" })
	public void CreateTaskFolder_02() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem taskFolder = FolderItem.importFromSOAP(account,SystemFolder.Tasks);
		
		_folderName = "taskfolder" + ZimbraSeleniumProperties.getUniqueString();
		
		//Create folder
		DialogCreateTaskFolder createTaskFolderDialog =(DialogCreateTaskFolder)app.zPageTasks.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_TASKFOLDER);
		
		createTaskFolderDialog.zEnterFolderName(_folderName);
		createTaskFolderDialog.zClickButton(Button.B_OK);
		
		_folderIsCreated = true;
		
		SleepUtil.sleepVerySmall();
		
		// refresh task page
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);
				
		// Make sure the task folder was created on the ZCS server
		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(),_folderName);
		ZAssert.assertNotNull(folder, "Verify task folder is created");
		ZAssert.assertEquals(folder.getName(), _folderName,"Verify the server and client folder names match");
	}

	@AfterMethod(groups = { "always" })
	public void createFolderTestCleanup() {
		if (_folderIsCreated) {
			try {
				app.zPageTasks.zNavigateTo();
				// Delete it from Email Server
				FolderItem
						.deleteUsingSOAP(app.zGetActiveAccount(), _folderName);
			} catch (Exception e) {
				logger.warn("Failed while removing the folder.", e);
			} finally {
				_folderName = null;
				_folderIsCreated = false;
			}
		}
	}
}
