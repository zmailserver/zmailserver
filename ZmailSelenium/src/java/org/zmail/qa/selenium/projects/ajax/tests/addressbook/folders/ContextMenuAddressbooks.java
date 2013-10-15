/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.qa.selenium.projects.ajax.tests.addressbook.folders;

import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;




public class ContextMenuAddressbooks extends AjaxCommonTest {

	public ContextMenuAddressbooks() {
		logger.info("New "+ ContextMenuAddressbooks.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageAddressbook;
		
		// Enable user preference checkboxes
		super.startingAccountPreferences = null;
		
	}

	
	
	

				
	@Test(
			description = "Cannot delete an addressbook system folder- Right click, Delete",
			groups = { "functional" },
			dataProvider = "DataProviderSystemFolders"
			)
	public void SystemFoldersDeleteButtonDisabledFromContextmenu(String name, SystemFolder systemFolder) throws HarnessException {
		
		FolderItem folder= FolderItem.importFromSOAP(app.zGetActiveAccount(), systemFolder);
		ZAssert.assertNotNull(folder, "Verify can get the folder: "+ name);	

		// Right click on Folder 
		app.zTreeContacts.zTreeItem(Action.A_RIGHTCLICK, folder);
		
		
		// Get the context menu
		String divLocator = "css=div#ZmActionMenu_contacts_ADDRBOOK";
		ZAssert.assertTrue(app.zTreeContacts.zIsVisiblePerPosition(divLocator, 0, 0), "Verify the context menu is visible");
		
		
		// Determine if the Edit option is enabled
		String editLocator = divLocator + " div#DELETE_WITHOUT_SHORTCUT.ZDisabled";
		ZAssert.assertTrue(app.zTreeContacts.sIsElementPresent(editLocator), "Verify the Delete Folder option is disabled");

	}	

	// These folders can change color or share only (i.e. has an edit dialog)
	@DataProvider(name = "DataProviderSystemFolders")
	public Object[][] DataProviderSystemFolders() {
	  return new Object[][] {
	    new Object[] { "Contacts", SystemFolder.Contacts },
	    new Object[] { "Emailed Contacts", SystemFolder.EmailedContacts },
//	    new Object[] { "Distribution Lists", SystemFolder.DistributionLists },
	    new Object[] { "Trash", SystemFolder.Trash },
	  };
	}
	
	@Test(
			description = "Verify 'Rename folder' dialog is not present from right click context menu",
			groups = { "functional" },
			dataProvider = "DataProviderSystemFolders"
			)
	public void CannotRenameSystemFolders(String name, SystemFolder systemFolder) throws HarnessException {

		FolderItem folder= FolderItem.importFromSOAP(app.zGetActiveAccount(), systemFolder);
		ZAssert.assertNotNull(folder, "Verify can get the folder: "+ name);	

		// Right click on Folder 
		app.zTreeContacts.zTreeItem(Action.A_RIGHTCLICK, folder);
		
		
		// Get the context menu
		String divLocator = "css=div#ZmActionMenu_contacts_ADDRBOOK";
		ZAssert.assertTrue(app.zTreeContacts.zIsVisiblePerPosition(divLocator, 0, 0), "Verify the context menu is visible");
		
		
		// Determine if the Edit option is enabled
		String editLocator = divLocator + " div#RENAME_FOLDER.ZDisabled";
		ZAssert.assertTrue(app.zTreeContacts.sIsElementPresent(editLocator), "Verify the Rename Folder option is disabled");

		
	}	



}
