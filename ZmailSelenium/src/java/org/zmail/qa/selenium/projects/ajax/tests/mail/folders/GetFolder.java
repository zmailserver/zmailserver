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

import java.util.List;

import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.framework.util.ZmailCharsets.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;


public class GetFolder extends PrefGroupMailByMessageTest {

	public GetFolder() {
		logger.info("New "+ GetFolder.class.getCanonicalName());
		
		
		
		
		
	}
	
	@Test(	description = "Get a folder",
			groups = { "smoke" })
	public void GetFolder_01() throws HarnessException {
		
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
		ZAssert.assertNotNull(inbox, "Verify the inbox is available");
		
		// Create the subfolder
		String name = "folder" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ name +"' l='"+ inbox.getId() +"'/>" +
                "</CreateFolderRequest>");

		FolderItem subfolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), name);
		ZAssert.assertNotNull(subfolder, "Verify the subfolder is available");
		
		
		// Click on Get Mail to refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		
		// Verify the folder appears in the list
		List<FolderItem> folders = app.zTreeMail.zListGetFolders();
		
		FolderItem found = null;
		for (FolderItem f : folders) {
			if ( name.equals(f.getName()) ) {
				found = f;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the folder was in the tree");

		
	}	

	@DataProvider(name = "DataProviderFilenames")
	public Object[][] DataProviderDeleteKeys() throws HarnessException {
		return (ZmailCharsets.getInstance().getSampleTable());
	}


	@Test(
			description = "Get a folder with non-ASCII special characters", 
			groups = { "functional", "matt" },
			dataProvider = "DataProviderFilenames")
	public void GetFolder_02(ZCharset charset, String foldername) throws HarnessException {
		
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
		ZAssert.assertNotNull(inbox, "Verify the inbox is available");
		
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ foldername +"' l='"+ inbox.getId() +"'/>" +
                "</CreateFolderRequest>");

		FolderItem subfolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);
		ZAssert.assertNotNull(subfolder, "Verify the subfolder is available");
		
		
		// Click on Get Mail to refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		
		// Verify the folder appears in the list
		List<FolderItem> folders = app.zTreeMail.zListGetFolders();
		
		FolderItem found = null;
		for (FolderItem f : folders) {
			if ( foldername.equals(f.getName()) ) {
				found = f;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the folder was in the tree");

		
	}	

}
