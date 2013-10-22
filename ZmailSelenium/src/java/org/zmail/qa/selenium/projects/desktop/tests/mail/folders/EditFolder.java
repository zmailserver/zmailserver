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
package org.zmail.qa.selenium.projects.desktop.tests.mail.folders;

import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.mail.DialogEditFolder;
import org.zmail.qa.selenium.projects.desktop.ui.mail.DialogEditFolder.FolderColor;

public class EditFolder extends AjaxCommonTest {

	public EditFolder() {
		logger.info("New " + EditFolder.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = null;

	}

	@Test(description = "Edit a folder, change the name (Context menu -> Edit)", groups = { "smoke" })
	public void EditFolder_01() throws HarnessException {

		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(),
				SystemFolder.Inbox);
		ZAssert.assertNotNull(inbox, "Verify the inbox is available");

		// Create the subfolder
		String name1 = "folder" + ZmailSeleniumProperties.getUniqueString();

		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>"
						+ "<folder name='" + name1 + "' l='" + inbox.getId()
						+ "'/>" + "</CreateFolderRequest>");

		// Click on Get Mail to refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		FolderItem subfolder1 = FolderItem.importFromSOAP(
		      app.zGetActiveAccount(),
				name1,
				SOAP_DESTINATION_HOST_TYPE.CLIENT,
				app.zGetActiveAccount().EmailAddress);

		ZAssert.assertNotNull(subfolder1, "Verify the subfolder is available");

		// Rename the folder using context menu
		DialogEditFolder dialog = (DialogEditFolder) app.zTreeMail.zTreeItem(
				Action.A_RIGHTCLICK, Button.B_TREE_EDIT, subfolder1);
		ZAssert.assertNotNull(dialog, "Verify the dialog opened");

		// Set the name, click OK
		String name2 = "folder" + ZmailSeleniumProperties.getUniqueString();
		dialog.zSetNewName(name2);
		dialog.zClickButton(Button.B_OK);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

		// Get all the folders and verify the new name appears and the old name
		// disappears
		app.zGetActiveAccount().soapSend(
				"<GetFolderRequest xmlns = 'urn:zmailMail'/>");

		Element[] eFolder1 = app.zGetActiveAccount().soapSelectNodes(
				"//mail:folder[@name='" + name1 + "']");
		ZAssert.assertEquals(eFolder1.length, 0,
				"Verify the old folder name no longer exists");

		Element[] eFolder2 = app.zGetActiveAccount().soapSelectNodes(
				"//mail:folder[@name='" + name2 + "']");
		ZAssert.assertEquals(eFolder2.length, 1,
				"Verify the new folder name exists");

	}

	@Test(description = "Edit a folder, change the color (Context menu -> Edit)", groups = { "functional" })
	public void EditFolder_02() throws HarnessException {

		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(),
				SystemFolder.Inbox);
		ZAssert.assertNotNull(inbox, "Verify the inbox is available");

		// Create the subfolder
		String name1 = "folder" + ZmailSeleniumProperties.getUniqueString();

		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>"
						+ "<folder name='" + name1 + "' l='" + inbox.getId()
						+ "'/>" + "</CreateFolderRequest>");

		// Click on Get Mail to refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		FolderItem subfolder1 = FolderItem.importFromSOAP(
		      app.zGetActiveAccount(),
		      name1,
		      SOAP_DESTINATION_HOST_TYPE.CLIENT,
		      app.zGetActiveAccount().EmailAddress);
		ZAssert.assertNotNull(subfolder1, "Verify the subfolder is available");


		// Rename the folder using context menu
		DialogEditFolder dialog = (DialogEditFolder) app.zTreeMail.zTreeItem(
				Action.A_RIGHTCLICK, Button.B_TREE_EDIT, subfolder1);
		ZAssert.assertNotNull(dialog, "Verify the dialog opened");

		// Change the color, click OK
		dialog.zSetNewColor(FolderColor.Gray);
		dialog.zClickButton(Button.B_OK);

		// Get all the folders and verify the new name appears and the old name
		// disappears
		FolderItem subfolder2 = FolderItem.importFromSOAP(app
				.zGetActiveAccount(), name1);
		ZAssert.assertNotNull(subfolder2, "Verify the subfolder is available");

		// Check the color

	}

}
