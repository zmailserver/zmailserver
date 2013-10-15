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

public class DeleteFile extends OctopusCommonTest {

	public DeleteFile() {
		logger.info("New " + DeleteFile.class.getCanonicalName());

		// test starts at the Search tab
		super.startingPage = app.zPageSearch;
		super.startingAccountPreferences = null;
		
	}

	@Test(
			description = "Delete a file from the search results", 
			groups = { "smoke" })
	public void DeleteFile_01() throws HarnessException {

		String filename = "filename"+ ZmailSeleniumProperties.getUniqueString() +".txt";
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/documents/doc01/plaintext.txt";
	
		

		// Upload file to server through RestUtil
		String attachmentId = app.zGetActiveAccount().uploadFile(filePath);

		// Save uploaded file through SOAP
		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Briefcase);
		FolderItem trashFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Trash);

		app.zGetActiveAccount().soapSend(
					"<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+		"<doc name='"+ filename +"' l='" + briefcaseRootFolder.getId() + "'>"
				+			"<upload id='" + attachmentId + "'/>"
				+		"</doc>"
				+	"</SaveDocumentRequest>");
		String documentId = app.zGetActiveAccount().soapSelectValue("//mail:doc", "id");


		
		// Sync up
//		app.zPageOctopus.zToolbarPressButton(Button.B_GETMAIL);


		// Search for the message
		app.zPageSearch.zExecuteSearchQuery(filename);
		
		// Get all the messages in the view
		// Verify the uploaded file exists
		IOctListViewItem found = null;
		List<IOctListViewItem> items = app.zPageSearch.zGetListViewItems();
		for (IOctListViewItem item : items) {
			if ( item.getListViewName().equalsIgnoreCase(filename) ) {
				// found it
				found = item;
				break;
			}
		}
				
		ZAssert.assertNotNull(found, "Verify the item is found in the list after searching");

		
		// Delete the file
		app.zPageMyFiles.zToolbarPressPulldown(
									Button.B_MY_FILES_LIST_ITEM,
									Button.O_DELETE, 
									found.getListViewName());
		
		
		// Verify the document is shared
		app.zGetActiveAccount().soapSend(
				"<GetItemRequest xmlns='urn:zmailMail'>"
			+		"<item id='"+ documentId +"'/>"
			+	"</GetItemRequest>");

		String id = app.zGetActiveAccount().soapSelectValue("//mail:doc[@id='"+ documentId +"']", "l");
				
		
		ZAssert.assertEquals(id, trashFolder.getId(), "Verify the folder is moved to trash");
		

	}


}
