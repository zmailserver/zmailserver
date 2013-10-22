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
package org.zmail.qa.selenium.projects.ajax.tests.search.savedsearch;

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.SavedSearchFolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;


//TODO: add more in ContactItem.java

public class DeleteSavedSearch extends AjaxCommonTest  {

	public DeleteSavedSearch() {
		logger.info("New "+ DeleteSavedSearch.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with conversation view
		super.startingAccountPreferences = null;		
		
	}
	
	
	@Test(	description = "Delete a saved search",
			groups = { "smoke" })
	public void DeleteSavedSearch_01() throws HarnessException {				

		// Create the message data to be sent
		String name = "search" + ZmailSeleniumProperties.getUniqueString();
		String query = "subject:(" + ZmailSeleniumProperties.getUniqueString() + ")";

		app.zGetActiveAccount().soapSend(
				"<CreateSearchFolderRequest xmlns='urn:zmailMail'>" +
					"<search name='"+ name +"' query='"+ query +"' l='1'/>" +
				"</CreateSearchFolderRequest>");

		// Get the item
		SavedSearchFolderItem item = SavedSearchFolderItem.importFromSOAP(app.zGetActiveAccount(), name);
		
		// Refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Right click on the search, select delete
		app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_DELETE, item);

		item = SavedSearchFolderItem.importFromSOAP(app.zGetActiveAccount(), name);
		FolderItem trash = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Trash);

		// Verify the saved search exists in the folder tree
		List<SavedSearchFolderItem> searches = app.zTreeMail.zListGetSavedSearches();
		ZAssert.assertNotNull(searches, "Verify the saved search list exists");

		// Make sure the message appears in the list
		SavedSearchFolderItem found = null;
		for (SavedSearchFolderItem s : searches) {
			logger.info("Subject: looking for "+ name +" found: "+ s.getName());
			if ( name.equals(s.getName()) ) {
				found = s;
				break;
			}
		}
		ZAssert.assertNull(found, "Verify the saved search is in the folder tree");
		ZAssert.assertNotNull(item, "Verify the subfolder is again available");
		ZAssert.assertEquals(trash.getId(), item.getParentId(),
		      "Verify the subfolder's parent is now the trash folder ID");
	}
}
