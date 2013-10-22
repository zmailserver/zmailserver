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
package com.zimbra.qa.selenium.projects.ajax.tests.briefcase.folders;

import org.testng.annotations.Test;

import com.zimbra.common.soap.Element;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.FeatureBriefcaseTest;
import com.zimbra.qa.selenium.projects.ajax.ui.briefcase.DialogEditProperties;

public class EditProperties extends FeatureBriefcaseTest {

	public EditProperties() {
		logger.info("New " + EditProperties.class.getCanonicalName());

		// All tests start at the Briefcase page
		super.startingPage = app.zPageBriefcase;		
	}

	@Test(description = "Edit Properties - Rename folder using context menu", groups = { "functional" })
	public void EditProperties_01() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		ZAssert.assertNotNull(briefcaseRootFolder,
				"Verify the Briefcase root folder is available");

		// Create the sub-folder
		String subFolderName = "folder"
				+ ZimbraSeleniumProperties.getUniqueString();

		account.soapSend("<CreateFolderRequest xmlns='urn:zimbraMail'>"
				+ "<folder name='" + subFolderName + "' l='"
				+ briefcaseRootFolder.getId() + "'/>"
				+ "</CreateFolderRequest>");

		// Verify the sub-folder exists on the server
				FolderItem subFolder = FolderItem
						.importFromSOAP(account, subFolderName);
				ZAssert.assertNotNull(subFolder, "Verify the subfolder is available");

		// refresh the Briefcase tree folder list
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseRootFolder,
				false);


		// Rename the folder using context menu
		DialogEditProperties dialog = (DialogEditProperties)app.zTreeBriefcase.zTreeItem(Action.A_RIGHTCLICK, Button.B_TREE_EDIT_PROPERTIES, subFolder);
		ZAssert.assertNotNull(dialog, "Verify the dialog opened");
		
		// Set the name, click OK
		String subFolderName2 = "renamedfolder" + ZimbraSeleniumProperties.getUniqueString();
		
		dialog.zSetNewName(subFolderName2);
		
		dialog.zClickButton(Button.B_OK);

		// Get all the folders and verify the new name appears and the old name disappears
				account.soapSend("<GetFolderRequest xmlns = 'urn:zimbraMail'/>");
				
				Element[] eFolder1 = account.soapSelectNodes("//mail:folder[@name='"+ subFolderName +"']");
				ZAssert.assertEquals(eFolder1.length, 0, "Verify the old folder name no longer exists");
				
				Element[] eFolder2 = app.zGetActiveAccount().soapSelectNodes("//mail:folder[@name='"+ subFolderName2 +"']");
				ZAssert.assertEquals(eFolder2.length, 1, "Verify the new folder name exists");
	
		}
}
