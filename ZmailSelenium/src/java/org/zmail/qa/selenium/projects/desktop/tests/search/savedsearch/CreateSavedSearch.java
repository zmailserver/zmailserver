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
package org.zmail.qa.selenium.projects.desktop.tests.search.savedsearch;

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.SavedSearchFolderItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.search.DialogSaveSearch;


//TODO: add more in ContactItem.java

public class CreateSavedSearch extends AjaxCommonTest  {

	public CreateSavedSearch() {
		logger.info("New "+ CreateSavedSearch.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with conversation view
		super.startingAccountPreferences = null;		
		
	}

	@Test(	description = "Create a basic saved search",
			groups = { "sanity" })
	public void CreateSavedSearch_01() throws HarnessException {				
				
			
		// Create the message data to be sent
		String name = "search" + ZmailSeleniumProperties.getUniqueString();
		String query = "subject:(" + ZmailSeleniumProperties.getUniqueString() + ")";
		

		// Search for the message
		app.zPageSearch.zAddSearchQuery(query);
		DialogSaveSearch dialog = (DialogSaveSearch)app.zPageSearch.zToolbarPressButton(Button.B_SEARCHSAVE);
		
		// Save the search
		dialog.zEnterSearchName(name);
		dialog.zClickButton(Button.B_OK);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

		//Verify the saved search exists in the server
		SavedSearchFolderItem item = SavedSearchFolderItem.importFromSOAP(app.zGetActiveAccount(), name);
		ZAssert.assertNotNull(item, "Verify the saved search was created correctly");
		
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
		ZAssert.assertNotNull(found, "Verify the saved search is in the folder tree");

	}
}
