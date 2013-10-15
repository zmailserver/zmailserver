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
package org.zmail.qa.selenium.projects.ajax.tests.tasks.mountpoints;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShare;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShareRevoke;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DialogEditFolder;

public class DeleteShare extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public DeleteShare() {
		logger.info("New "+ DeleteShare.class.getCanonicalName());

		super.startingPage = app.zPageTasks;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefTasksReadingPaneLocation", "bottom");
				put("zmailPrefShowSelectionCheckbox", "TRUE");
			}
		};

	}


	@Test(	description = "Share and Revoke folder ",groups = { "smoke" })
	public void DeleteShare_01() throws HarnessException {

		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);

		// Create the subTaskList
		String name = "taskList" + ZmailSeleniumProperties.getUniqueString();

		app.zGetActiveAccount()
		.soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+ "<folder name='" + name + "' l='"
				+ taskFolder.getId() + "'/>"
				+ "</CreateFolderRequest>");

		FolderItem subTaskList = FolderItem.importFromSOAP(app.zGetActiveAccount(), name);
		ZAssert.assertNotNull(subTaskList, "Verify the subfolder is available");

		// Refresh the tasks view
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		// Right click on folder, select "Share"
		DialogShare dialog = (DialogShare) app.zTreeTasks.zTreeItem(Action.A_RIGHTCLICK, Button.B_SHARE, subTaskList);
		ZAssert.assertNotNull(dialog, "Verify the sharing dialog pops up");

		// Use defaults for all options
		dialog.zSetEmailAddress(ZmailAccount.AccountA().EmailAddress);

		// Send it
		dialog.zClickButton(Button.B_OK);

		// Make sure that AccountA now has the share
		ZmailAccount.AccountA().soapSend(
				"<GetShareInfoRequest xmlns='urn:zmailAccount'>"
				+ "<grantee type='usr'/>" + "<owner by='name'>"
				+ app.zGetActiveAccount().EmailAddress + "</owner>"
				+ "</GetShareInfoRequest>");

		String ownerEmail = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Tasks/"	+ name + "']", "ownerEmail");
		ZAssert.assertEquals(ownerEmail, app.zGetActiveAccount().EmailAddress,
		"Verify the owner of the shared folder");

		String rights = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Tasks/"	+ name + "']", "rights");
		ZAssert.assertEquals(rights, "r", "Verify the rights are 'read only'");

		String granteeType = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Tasks/"+ name + "']", "granteeType");
		ZAssert.assertEquals(granteeType, "usr","Verify the grantee type is 'user'");		

		//Need to do Refresh by clicking on getmail button to see folder in the list 
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		//Edit
		//Right click folder, click Edit Properties
		DialogEditFolder editdialog = (DialogEditFolder)app.zTreeTasks.zTreeItem(Action.A_RIGHTCLICK, Button.B_TREE_EDIT, subTaskList);
		ZAssert.assertNotNull(editdialog, "Verify the sharing dialog pops up");

		//Click Revoke link on Edit properties dialog
		DialogShareRevoke sharedialog = (DialogShareRevoke)editdialog.zClickButton(Button.O_REVOKE_LINK);		
		ZAssert.assertTrue(sharedialog.zIsActive(), "Verify that the Share dialog is active ");
		
		//click Yes
		sharedialog.zClickButton(Button.B_YES);
		
		//Verify Edit properties  dialog is active
		ZAssert.assertTrue(editdialog.zIsActive(), "Verify that the Edit Folder Properties dialog is active ");

		//click ok button from edit Folder properties dialog
		editdialog.zClickButton(Button.B_OK);		

		ZmailAccount.AccountA().soapSend(
				"<GetShareInfoRequest xmlns='urn:zmailAccount'>"
				+		"<grantee type='usr'/>"
				+		"<owner by='name'>"+ app.zGetActiveAccount().EmailAddress +"</owner>"
				+	"</GetShareInfoRequest>");

		Element[] nodes = ZmailAccount.AccountA().soapSelectNodes("//acct:GetShareInfoResponse//acct:share[@folderPath='/Tasks/"+ name +"']");

		ZAssert.assertEquals(nodes.length, 0, "Verify the shared folder no longer exists in the share information (no nodes returned)");

	}

}
