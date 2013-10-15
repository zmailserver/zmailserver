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
package org.zmail.qa.selenium.projects.octopus.tests.search;

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.IOctListViewItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;

public class RenameFile extends OctopusCommonTest {

	public RenameFile() {
		logger.info("New " + RenameFile.class.getCanonicalName());

		// test starts at the Search tab
		super.startingPage = app.zPageSearch;
		super.startingAccountPreferences = null;
		
	}

	@Test(
			description = "Rename a file from the search results", 
			groups = { "smoke" })
	public void RenameFile_01() throws HarnessException {

		String extension = ".txt";
		String name1 = "filename"+ ZmailSeleniumProperties.getUniqueString();
		String name2 = "filename"+ ZmailSeleniumProperties.getUniqueString();
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/documents/doc01/plaintext.txt";
	
		

		// Upload file to server through RestUtil
		String attachmentId = app.zGetActiveAccount().uploadFile(filePath);

		// Save uploaded file through SOAP
		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Briefcase);

		app.zGetActiveAccount().soapSend(
					"<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+		"<doc name='"+ name1 + extension +"' l='" + briefcaseRootFolder.getId() + "'>"
				+			"<upload id='" + attachmentId + "'/>"
				+		"</doc>"
				+	"</SaveDocumentRequest>");
		String documentId = app.zGetActiveAccount().soapSelectValue("//mail:doc", "id");


		
		// Sync up
//		app.zPageOctopus.zToolbarPressButton(Button.B_GETMAIL);


		// Search for the message
		app.zPageSearch.zExecuteSearchQuery(name1);
		
		// Get all the messages in the view
		// Verify the uploaded file exists
		IOctListViewItem found = null;
		List<IOctListViewItem> items = app.zPageSearch.zGetListViewItems();
		for (IOctListViewItem item : items) {
			if ( item.getListViewName().equalsIgnoreCase(name1 + extension) ) {
				// found it
				found = item;
				break;
			}
		}
				
		ZAssert.assertNotNull(found, "Verify the item is found in the list after searching");

		
		// Share the file
		app.zPageSearch.zToolbarPressPulldown(Button.B_MY_FILES_LIST_ITEM, 
														Button.O_RENAME, 
														found.getListViewName());
		app.zPageSearch.rename(name2);

		
		// Verify the document is renamed
		app.zGetActiveAccount().soapSend(
				"<GetItemRequest xmlns='urn:zmailMail'>"
			+		"<item id='"+ documentId +"'/>"
			+	"</GetItemRequest>");

		String n = app.zGetActiveAccount().soapSelectValue("//mail:doc", "name");
		
		ZAssert.assertEquals(n, name2 + extension, "Verify the file is renamed to the new filename ");
		

	}


}
