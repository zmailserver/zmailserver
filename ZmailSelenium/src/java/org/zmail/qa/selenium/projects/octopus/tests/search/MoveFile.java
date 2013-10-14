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
package com.zimbra.qa.selenium.projects.octopus.tests.search;

import java.util.List;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.IOctListViewItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.octopus.core.OctopusCommonTest;
import com.zimbra.qa.selenium.projects.octopus.ui.DialogMove;

public class MoveFile extends OctopusCommonTest {

	public MoveFile() {
		logger.info("New " + MoveFile.class.getCanonicalName());

		// test starts at the Search tab
		super.startingPage = app.zPageSearch;
		super.startingAccountPreferences = null;
		
	}

	@Test(
			description = "Move a file from the search results", 
			groups = { "smoke" })
	public void MoveFile_01() throws HarnessException {

		String filename = "filename"+ ZimbraSeleniumProperties.getUniqueString() +".txt";
		String filePath = ZimbraSeleniumProperties.getBaseDirectory()
				+ "/data/public/documents/doc01/plaintext.txt";
		String subFolderName = "subFolder" + ZimbraSeleniumProperties.getUniqueString();

		
		// Save uploaded file through SOAP
		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Briefcase);

		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zimbraMail'>"
				+		"<folder name='" + subFolderName + "' l='" + briefcaseRootFolder.getId() + "' view='document'/>"
				+	"</CreateFolderRequest>");

		// Verify the sub-folder exists on the server
		FolderItem subFolderItem = FolderItem.importFromSOAP(app.zGetActiveAccount(), subFolderName);
		ZAssert.assertNotNull(subFolderItem, "Verify the subfolder is available");

		// Upload file to server through RestUtil
		String attachmentId = app.zGetActiveAccount().uploadFile(filePath);


		app.zGetActiveAccount().soapSend(
					"<SaveDocumentRequest xmlns='urn:zimbraMail'>"
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

		
		// Share the file
		DialogMove chooseFolder = (DialogMove) app.zPageSearch.zToolbarPressPulldown(
													Button.B_MY_FILES_LIST_ITEM,
													Button.O_MOVE, 
													found.getListViewName());


		// Double click to choose folder
		chooseFolder.zDoubleClickTreeFolder(subFolderName);
		app.zPageSearch.zWaitForBusyOverlayOctopus();
		

		
		// Verify the document is shared
		app.zGetActiveAccount().soapSend(
				"<GetItemRequest xmlns='urn:zimbraMail'>"
			+		"<item id='"+ documentId +"'/>"
			+	"</GetItemRequest>");

		String id = app.zGetActiveAccount().soapSelectValue("//mail:doc[@id='"+ documentId +"']", "l");
				
		
		ZAssert.assertEquals(id, subFolderItem.getId(), "Verify the document is in the subfolder");
		

	}


}
