/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013 VMware, Inc.
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
package org.zmail.qa.selenium.projects.ajax.tests.tasks.mountpoints;


import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderMountpointItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DialogEditFolder;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DialogEditFolder.FolderColor;


public class ChangeColorMountpoint extends PrefGroupMailByMessageTest {

	
	
	@SuppressWarnings("serial")
	public ChangeColorMountpoint() {
		logger.info("New " + ChangeColorMountpoint.class.getCanonicalName());

		super.startingPage = app.zPageTasks;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefReadingPaneLocation", "bottom");
				put("zmailPrefShowSelectionCheckbox", "TRUE");
				
			}
		};

	}
	
	@Test(	description = "Edit a tasklist, change the color (Context menu -> Edit)",
			groups = { "functional" })
	public void ChangeColorMountpoint_01() throws HarnessException {
		
		ZmailAccount Owner = (new ZmailAccount()).provision().authenticate();

		// Owner creates a folder, shares it with current user
		String ownerFoldername = "ownerfolder"+ ZmailSeleniumProperties.getUniqueString();

		
		FolderItem ownerTask = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);
		ZAssert.assertNotNull(ownerTask, "Verify the new owner folder exists");

		Owner.soapSend("<CreateFolderRequest xmlns='urn:zmailMail'>"
				+ "<folder name='" + ownerFoldername + "' l='"
				+ ownerTask.getId() + "'/>" + "</CreateFolderRequest>");

		FolderItem ownerFolder = FolderItem.importFromSOAP(Owner,ownerFoldername);
		ZAssert.assertNotNull(ownerFolder,"Verify the new owner folder exists");

		Owner.soapSend("<FolderActionRequest xmlns='urn:zmailMail'>"
				+ "<action id='" + ownerFolder.getId() + "' op='grant'>"
				+ "<grant d='" + app.zGetActiveAccount().EmailAddress
				+ "' gt='usr' perm='r'/>" + "</action>"
				+ "</FolderActionRequest>");

		// Current user creates the mountpoint that points to the share
		String mountpointFoldername = "mountpoint"+ ZmailSeleniumProperties.getUniqueString();
		app.zGetActiveAccount().soapSend(
				"<CreateMountpointRequest xmlns='urn:zmailMail'>"
						+ "<link l='1' name='" + mountpointFoldername
						+ "' view='task' rid='" + ownerFolder.getId()
						+ "' zid='" + Owner.ZmailId + "'/>"
						+ "</CreateMountpointRequest>");

		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(
				app.zGetActiveAccount(), mountpointFoldername);
		ZAssert.assertNotNull(mountpoint, "Verify the subfolder is available");

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Rename the folder using context menu
		DialogEditFolder dialog = 
			(DialogEditFolder) app.zTreeTasks.zTreeItem(Action.A_RIGHTCLICK, Button.B_TREE_EDIT, mountpoint);
		ZAssert.assertNotNull(dialog, "Verify the dialog opened");

		// Change the color, click OK
		dialog.zSetNewColor(FolderColor.Gray);
		dialog.zClickButton(Button.B_OK);

		// Check the color
		app.zGetActiveAccount().soapSend("<GetFolderRequest xmlns='urn:zmailMail'/>");

		String color = app.zGetActiveAccount().soapSelectValue("//mail:link[@name='" + mountpoint.getName() + "']", "color");
		ZAssert.assertEquals(color, "8", "Verify the color of the folder is set to gray (8)");
		
	}	

}
