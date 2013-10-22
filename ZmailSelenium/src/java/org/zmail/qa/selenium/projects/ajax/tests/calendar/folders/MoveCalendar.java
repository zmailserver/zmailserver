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
package org.zmail.qa.selenium.projects.ajax.tests.calendar.folders;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogMove;


public class MoveCalendar extends AjaxCommonTest {

	public MoveCalendar() {
		logger.info("New "+ MoveCalendar.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageCalendar;
		super.startingAccountPreferences = null;
		
	}
	
	@Test(	description = "Move a calendar - Right click, Move",
			groups = { "smoke" })
	public void MoveCalendar_01() throws HarnessException {
		
		FolderItem root = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.UserRoot);
		ZAssert.assertNotNull(root, "Verify the inbox is available");
		
		
		// Create two subfolders in the inbox
		// One folder to move
		// Another folder to move into
		String name1 = "folder" + ZmailSeleniumProperties.getUniqueString();
		String name2 = "folder" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+	  	"<folder name='"+ name1 +"' l='"+ root.getId() +"' view='appointment'/>"
				+	"</CreateFolderRequest>");

		FolderItem subfolder1 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name1);
		ZAssert.assertNotNull(subfolder1, "Verify the first subfolder is available");
		
		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+	  	"<folder name='"+ name2 +"' l='"+ root.getId() +"' view='appointment'/>"
				+	"</CreateFolderRequest>");

		FolderItem subfolder2 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name2);
		ZAssert.assertNotNull(subfolder2, "Verify the second subfolder is available");
		
		
		
		// Click on Get Mail to refresh the folder list
		app.zPageCalendar.zToolbarPressButton(Button.B_REFRESH);

		// Move the folder using context menu
		DialogMove dialog = (DialogMove)app.zTreeCalendar.zTreeItem(Action.A_RIGHTCLICK, Button.B_MOVE, subfolder1);
		dialog.zClickTreeFolder(subfolder2);
		dialog.zClickButton(Button.B_OK);
		
		
		
		// Verify the folder is now in the other subfolder
		subfolder1 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name1);
		ZAssert.assertNotNull(subfolder1, "Verify the subfolder is again available");
		ZAssert.assertEquals(subfolder2.getId(), subfolder1.getParentId(), "Verify the subfolder's parent is now the other subfolder");

		
	}

	


}
