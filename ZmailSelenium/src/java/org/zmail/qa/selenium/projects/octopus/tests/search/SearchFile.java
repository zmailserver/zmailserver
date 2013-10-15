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
package org.zmail.qa.selenium.projects.octopus.tests.search;

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.IOctListViewItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;

public class SearchFile extends OctopusCommonTest {

	public SearchFile() {
		logger.info("New " + SearchFile.class.getCanonicalName());

		// test starts at the Search tab
		super.startingPage = app.zPageSearch;
		super.startingAccountPreferences = null;
		
	}

	@Test(
			description = "Search for a file by filename", 
			groups = { "functional" })
	public void SearchFile_01() throws HarnessException {

		String filename = "filename"+ ZmailSeleniumProperties.getUniqueString() +".xls";
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/testexcelfile.xls";
		
		

		// Upload file to server through RestUtil
		String attachmentId = app.zGetActiveAccount().uploadFile(filePath);

		// Save uploaded file through SOAP
		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Briefcase);

		app.zGetActiveAccount().soapSend(
					"<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+		"<doc name='"+ filename +"' l='" + briefcaseRootFolder.getId() + "'>"
				+			"<upload id='" + attachmentId + "'/>"
				+		"</doc>"
				+	"</SaveDocumentRequest>");


		
		// Sync up
//		app.zPageOctopus.zToolbarPressButton(Button.B_GETMAIL);


		// Search for the message
		app.zPageSearch.zExecuteSearchQuery(filename);
		
		// Get all the messages in the view
		// Verify the uploaded file exists
		boolean found = false;
		List<IOctListViewItem> items = app.zPageSearch.zGetListViewItems();
		for (IOctListViewItem item : items) {
			if ( item.getListViewName().equalsIgnoreCase(filename) ) {
				// found it
				found = true;
				break;
			}
		}
				
		ZAssert.assertTrue(found, "Verify the item is found in the list after searching");

	}

	@Test(
			description = "Search for a text file by content", 
			groups = { "smoke" })
	public void SearchFile_02() throws HarnessException {
		

		String filename = "filename"+ ZmailSeleniumProperties.getUniqueString() +".txt";
		String query = "reunification";
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/documents/doc01/plaintext.txt";
		
		

		// Upload file to server through RestUtil
		String attachmentId = app.zGetActiveAccount().uploadFile(filePath);

		// Save uploaded file through SOAP
		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Briefcase);

		app.zGetActiveAccount().soapSend(
					"<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+		"<doc name='"+ filename +"' l='" + briefcaseRootFolder.getId() + "'>"
				+			"<upload id='" + attachmentId + "'/>"
				+		"</doc>"
				+	"</SaveDocumentRequest>");


		
		// Sync up
//		app.zPageOctopus.zToolbarPressButton(Button.B_GETMAIL);


		// Search for the message
		app.zPageSearch.zExecuteSearchQuery(query);
		
		// Get all the messages in the view
		// Verify the uploaded file exists
		boolean found = false;
		List<IOctListViewItem> items = app.zPageSearch.zGetListViewItems();
		for (IOctListViewItem item : items) {
			if ( item.getListViewName().equalsIgnoreCase(filename) ) {
				// found it
				found = true;
				break;
			}
		}
				
		ZAssert.assertTrue(found, "Verify the item is found in the list after searching");

		
	}
	
	@Test(
			description = "Search for a binary (word doc) file by content", 
			groups = { "smoke" })
	public void SearchFile_03() throws HarnessException {
		

		String filename = "filename"+ ZmailSeleniumProperties.getUniqueString() +".docx";
		String query = "reunification";
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/documents/doc01/word2007.docx";
		
		

		// Upload file to server through RestUtil
		String attachmentId = app.zGetActiveAccount().uploadFile(filePath);

		// Save uploaded file through SOAP
		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Briefcase);

		app.zGetActiveAccount().soapSend(
					"<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+		"<doc name='"+ filename +"' l='" + briefcaseRootFolder.getId() + "'>"
				+			"<upload id='" + attachmentId + "'/>"
				+		"</doc>"
				+	"</SaveDocumentRequest>");


		
		// Sync up
//		app.zPageOctopus.zToolbarPressButton(Button.B_GETMAIL);


		// Search for the message
		app.zPageSearch.zExecuteSearchQuery(query);
		
		// Get all the messages in the view
		// Verify the uploaded file exists
		boolean found = false;
		List<IOctListViewItem> items = app.zPageSearch.zGetListViewItems();
		for (IOctListViewItem item : items) {
			if ( item.getListViewName().equalsIgnoreCase(filename) ) {
				// found it
				found = true;
				break;
			}
		}
				
		ZAssert.assertTrue(found, "Verify the item is found in the list after searching");

		

	}
	
	@Test(description = "Search for a file in trash", 
			groups = { "functional" })
	public void SearchFile_04() throws HarnessException {
		
		

		String filename = "filename"+ ZmailSeleniumProperties.getUniqueString() +".txt";
		String query = "reunification";
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


		// Delete the doc
		app.zGetActiveAccount().soapSend(
					"<ItemActionRequest xmlns='urn:zmailMail'>"
				+		"<action op='move' id='"+ documentId +"' l='" + trashFolder.getId() +"'/>"
				+	"</ItemActionRequest>");

		// Sync up
//		app.zPageOctopus.zToolbarPressButton(Button.B_GETMAIL);


		// Search for the message
		app.zPageSearch.zExecuteSearchQuery(query);
		
		// Get all the messages in the view
		// Verify the uploaded file exists
		boolean found = false;
		List<IOctListViewItem> items = app.zPageSearch.zGetListViewItems();
		for (IOctListViewItem item : items) {
			if ( item.getListViewName().equalsIgnoreCase(filename) ) {
				// found it
				found = true;
				break;
			}
		}
				
		ZAssert.assertFalse(found, "Verify the item is not found when query does not include is:anywhere");

		// Search for the message
		app.zPageSearch.zExecuteSearchQuery(query + " is:anywhere");
		
		// Get all the messages in the view
		// Verify the uploaded file exists
		found = false;
		items = app.zPageSearch.zGetListViewItems();
		for (IOctListViewItem item : items) {
			if ( item.getListViewName().equalsIgnoreCase(filename) ) {
				// found it
				found = true;
				break;
			}
		}
				
		ZAssert.assertTrue(found, "Verify the item is found when query includes is:anywhere");


	}

}
