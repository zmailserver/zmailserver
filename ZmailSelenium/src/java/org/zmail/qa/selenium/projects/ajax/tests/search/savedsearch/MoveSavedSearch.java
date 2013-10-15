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

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.SavedSearchFolderItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.*;


//TODO: add more in ContactItem.java

public class MoveSavedSearch extends AjaxCommonTest  {

	public MoveSavedSearch() {
		logger.info("New "+ MoveSavedSearch.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with conversation view
		super.startingAccountPreferences = null;		
		
	}
	
	
	@Test(	description = "Move a saved search",
			groups = { "smoke" })
	public void MoveSavedSearch_01() throws HarnessException {				
				
			
		// Create the message data to be moved
		String name1 = "search" + ZmailSeleniumProperties.getUniqueString();
		String name2 = "search" + ZmailSeleniumProperties.getUniqueString();
		String query1 = "subject:(" + ZmailSeleniumProperties.getUniqueString() + ")";
		String query2 = "subject:(" + ZmailSeleniumProperties.getUniqueString() + ")";
		

		app.zGetActiveAccount().soapSend(
				"<CreateSearchFolderRequest xmlns='urn:zmailMail'>" +
					"<search name='"+ name1 +"' query='"+ query1 +"' l='1'/>" +
				"</CreateSearchFolderRequest>");
		SavedSearchFolderItem item1 = SavedSearchFolderItem.importFromSOAP(app.zGetActiveAccount(), name1);

		app.zGetActiveAccount().soapSend(
				"<CreateSearchFolderRequest xmlns='urn:zmailMail'>" +
					"<search name='"+ name2 +"' query='"+ query2 +"' l='1'/>" +
				"</CreateSearchFolderRequest>");
		SavedSearchFolderItem item2 = SavedSearchFolderItem.importFromSOAP(app.zGetActiveAccount(), name2);


		// Refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Right click on the search, select delete
		// TODO: can the folder move dialog be reused?  Or, do we need DialogMoveSavedSearchFolder class?
		DialogMove dialog = (DialogMove) app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_MOVE, item1);
		
		// Rename the search
		dialog.zEnterFolderName(name2);
		dialog.zClickButton(Button.B_OK);


		// Verify the saved search exists under the other saved search
		item1 = SavedSearchFolderItem.importFromSOAP(app.zGetActiveAccount(), name1);
		ZAssert.assertEquals(item1.getParentId(), item2.getId(), "Verify the saved search's parent folder is the other saved search");
		

	}
}
