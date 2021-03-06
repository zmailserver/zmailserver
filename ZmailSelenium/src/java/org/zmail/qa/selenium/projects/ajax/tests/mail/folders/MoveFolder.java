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
package org.zmail.qa.selenium.projects.ajax.tests.mail.folders;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogMove;


public class MoveFolder extends PrefGroupMailByMessageTest {

	public MoveFolder() {
		logger.info("New "+ MoveFolder.class.getCanonicalName());
		
		
		
		
		
	}
	
	@Test(	description = "Move a folder - Right click, Move",
			groups = { "smoke" })
	public void MoveFolder_01() throws HarnessException {
		
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
		ZAssert.assertNotNull(inbox, "Verify the inbox is available");
		
		
		// Create two subfolders in the inbox
		// One folder to move
		// Another folder to move into
		String name1 = "folder" + ZmailSeleniumProperties.getUniqueString();
		String name2 = "folder" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ name1 +"' l='"+ inbox.getId() +"'/>" +
                "</CreateFolderRequest>");

		FolderItem subfolder1 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name1);
		ZAssert.assertNotNull(subfolder1, "Verify the first subfolder is available");
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ name2 +"' l='"+ inbox.getId() +"'/>" +
                "</CreateFolderRequest>");

		FolderItem subfolder2 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name2);
		ZAssert.assertNotNull(subfolder2, "Verify the second subfolder is available");
		
		
		// Click on Get Mail to refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Move the folder using context menu
		DialogMove dialog = (DialogMove)app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_MOVE, subfolder1);
		dialog.zClickTreeFolder(subfolder2);
		dialog.zClickButton(Button.B_OK);
		
		// Verify the folder is now in the other subfolder
		subfolder1 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name1);
		ZAssert.assertNotNull(subfolder1, "Verify the subfolder is again available");
		ZAssert.assertEquals(subfolder2.getId(), subfolder1.getParentId(), "Verify the subfolder's parent is now the other subfolder");

		
	}

	


}
