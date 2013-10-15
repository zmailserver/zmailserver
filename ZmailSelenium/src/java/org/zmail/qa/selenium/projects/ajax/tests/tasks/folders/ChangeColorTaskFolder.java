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
package org.zmail.qa.selenium.projects.ajax.tests.tasks.folders;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DialogEditFolder;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DialogEditFolder.FolderColor;


public class ChangeColorTaskFolder extends AjaxCommonTest {
	@SuppressWarnings("serial")
	public ChangeColorTaskFolder() {
		logger.info("New " + ChangeColorTaskFolder.class.getCanonicalName());

		// test starts at the task tab
		super.startingPage = app.zPageTasks;
		super.startingAccountPreferences = new HashMap<String , String>() {{
			put("zmailPrefTasksReadingPaneLocation", "bottom");
			put("zmailPrefShowSelectionCheckbox", "TRUE");
		}};
	}	

	
	
	@Test(description = "Edit a folder, change the color (Context menu -> Edit)", groups = { "functional" })
	public void ChangeColorTaskFolder_01() throws HarnessException {
		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);
		ZAssert.assertNotNull(taskFolder, "Verify the task is available");
		
		// Create the subTaskList
		String name = "taskList" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ name +"' l='"+ taskFolder.getId() +"'/>" +
                "</CreateFolderRequest>");

		FolderItem subTaskList = FolderItem.importFromSOAP(app.zGetActiveAccount(), name);
		ZAssert.assertNotNull(subTaskList, "Verify the subfolder is available");
		
		// Refresh the tasks view
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		// Edit the folder using context menu		
		DialogEditFolder dialog = (DialogEditFolder) app.zTreeTasks.zTreeItem(
				Action.A_RIGHTCLICK, Button.B_TREE_EDIT, subTaskList);
		ZAssert.assertNotNull(dialog, "Verify the dialog opened");
		
		// Change the color, click OK
		dialog.zSetNewColor(FolderColor.Blue);
		dialog.zClickButton(Button.B_OK);

		// Check the color
		app.zGetActiveAccount().soapSend(
				"<GetFolderRequest xmlns='urn:zmailMail'>"
			+		"<folder id='" + subTaskList.getId() + "'/>"
			+	"</GetFolderRequest>");

		String color = app.zGetActiveAccount().soapSelectValue("//mail:folder[@name='" + subTaskList.getName() + "']", "color");
		ZAssert.assertEquals(color, "1", "Verify the color of the folder is set to blue (1)");
	}
	
}


