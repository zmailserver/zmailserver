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
package org.zmail.qa.selenium.projects.ajax.tests.mail.mountpoints;

import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShareRevoke;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DialogEditFolder;

public class DeleteShare extends PrefGroupMailByMessageTest{
	
	
	
	public DeleteShare() {
		logger.info("New "+ DeleteShare.class.getCanonicalName());

		

	}


	@Test(	description = "Delete (Revoke) an existing share",
			groups = { "smoke" })
	public void DeleteShare_01() throws HarnessException {

		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Inbox);
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();


		// Create a subfolder in Inbox
		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername +"' l='" + inbox.getId() +"'/>"
				+	"</CreateFolderRequest>");

		FolderItem subfolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);
		ZAssert.assertNotNull(subfolder, "Verify the new owner folder exists");
		
		app.zGetActiveAccount().soapSend(
					"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ subfolder.getId() +"' op='grant'>"
				+			"<grant d='" + ZmailAccount.AccountA().EmailAddress + "' gt='usr' perm='r'/>"
				+		"</action>"
				+	"</FolderActionRequest>");


		// Make sure that AccountA now has the share
		ZmailAccount.AccountA().soapSend(
					"<GetShareInfoRequest xmlns='urn:zmailAccount'>"
				+		"<grantee type='usr'/>"
				+		"<owner by='name'>"+ app.zGetActiveAccount().EmailAddress +"</owner>"
				+	"</GetShareInfoRequest>");

		String ownerEmail = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Inbox/"+ foldername +"']", "ownerEmail");
		ZAssert.assertEquals(ownerEmail, app.zGetActiveAccount().EmailAddress, "Verify the owner of the shared folder");

		String rights = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Inbox/"+ foldername +"']", "rights");
		ZAssert.assertEquals(rights, "r", "Verify the rights are 'read only'");

		String granteeType = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Inbox/"+ foldername +"']", "granteeType");
		ZAssert.assertEquals(granteeType, "usr", "Verify the grantee type is 'user'");

		

		//Need to do Refresh by clicking on getmail button to see folder in the list 
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);


		// Delete
		// 1. Right click, edit folder properties
		// 2. Click on "delete" next to share
		

		//Right click folder, click Edit Properties
		DialogEditFolder editdialog = (DialogEditFolder)app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_TREE_EDIT, subfolder);
		ZAssert.assertNotNull(editdialog, "Verify the edit dialog pops up");

		//Click Edit link on Edit properties dialog
		DialogShareRevoke sharedialog = (DialogShareRevoke)editdialog.zClickButton(Button.O_REVOKE_LINK);
		ZAssert.assertTrue(sharedialog.zIsActive(), "Verify that the share dialog pops up");

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

		
		Element[] nodes = ZmailAccount.AccountA().soapSelectNodes("//acct:GetShareInfoResponse//acct:share[@folderPath='/Inbox/"+ foldername +"']");
		
		ZAssert.assertEquals(nodes.length, 0, "Verify the shared folder no longer exists in the share information (no nodes returned)");
		
	}

}
