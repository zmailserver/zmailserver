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

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShare;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShare.ShareRole;


public class CreateShare extends PrefGroupMailByMessageTest {

	public CreateShare() {
		logger.info("New "+ CreateShare.class.getCanonicalName());
		
		
	}
	
	@Test(	description = "Share a folder - Viewer",
			groups = { "smoke" })
	public void CreateShare_01() throws HarnessException {
		
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Inbox);
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();


		// Create a subfolder in Inbox
		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername +"' l='" + inbox.getId() +"'/>"
				+	"</CreateFolderRequest>");

		//Need to do Refresh by clicking on getmail button to see folder in the list 
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Make sure the folder was created on the server
		FolderItem subfolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);
		ZAssert.assertNotNull(subfolder, "Verify the folder exists on the server");


		// Right click on folder, select "Share"
		DialogShare dialog = (DialogShare)app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_SHARE, subfolder);
		ZAssert.assertNotNull(dialog, "Verify the sharing dialog pops up");

		// Use defaults for all options
		dialog.zSetEmailAddress(ZmailAccount.AccountA().EmailAddress);
		
		// Send it
		dialog.zClickButton(Button.B_OK);
		
		// Make sure that AccountA now has the share
		ZmailAccount.AccountA().soapSend(
					"<GetShareInfoRequest xmlns='urn:zmailAccount'>"
				+		"<grantee type='usr'/>"
				+		"<owner by='name'>"+ app.zGetActiveAccount().EmailAddress +"</owner>"
				+	"</GetShareInfoRequest>");
		
		// Example response:
		//	    <GetShareInfoResponse xmlns="urn:zmailAccount">
		//	      <share granteeId="0136d047-b771-49c0-a735-12183f3ca654" ownerName="enus12986828702967" granteeDisplayName="enus12986828648903" ownerId="4000b6a8-56bc-4910-ae3e-77528a5d5b18" rights="r" folderPath="/Inbox/folder12986828702964" mid="257" granteeType="usr" ownerEmail="enus12986828702967@testdomain.com" granteeName="enus12986828648903@testdomain.com" folderId="257"/>
		//	    </GetShareInfoResponse>

		String ownerEmail = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Inbox/"+ foldername +"']", "ownerEmail");
		ZAssert.assertEquals(ownerEmail, app.zGetActiveAccount().EmailAddress, "Verify the owner of the shared folder");
		
		String rights = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Inbox/"+ foldername +"']", "rights");
		ZAssert.assertEquals(rights, "r", "Verify the rights are 'read only'");

		String granteeType = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Inbox/"+ foldername +"']", "granteeType");
		ZAssert.assertEquals(granteeType, "usr", "Verify the grantee type is 'user'");

	}

	
	@Test(	description = "Share a folder - Manager",
			groups = { "smoke" })
	public void CreateShare_02() throws HarnessException {
		
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Inbox);
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();


		// Create a subfolder in Inbox
		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername +"' l='" + inbox.getId() +"'/>"
				+	"</CreateFolderRequest>");

		//Need to do Refresh by clicking on getmail button to see folder in the list 
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Make sure the folder was created on the server
		FolderItem subfolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);
		ZAssert.assertNotNull(subfolder, "Verify the folder exists on the server");


		// Right click on folder, select "Share"
		DialogShare dialog = (DialogShare)app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_SHARE, subfolder);
		ZAssert.assertNotNull(dialog, "Verify the sharing dialog pops up");

		// Use defaults for all options
		dialog.zSetEmailAddress(ZmailAccount.AccountA().EmailAddress);
		dialog.zSetRole(ShareRole.Manager);
		
		// Send it
		dialog.zClickButton(Button.B_OK);
		
		// Make sure that AccountA now has the share
		ZmailAccount.AccountA().soapSend(
					"<GetShareInfoRequest xmlns='urn:zmailAccount'>"
				+		"<grantee type='usr'/>"
				+		"<owner by='name'>"+ app.zGetActiveAccount().EmailAddress +"</owner>"
				+	"</GetShareInfoRequest>");
		
		// Example response:
		//	    <GetShareInfoResponse xmlns="urn:zmailAccount">
		//	      <share granteeId="0136d047-b771-49c0-a735-12183f3ca654" ownerName="enus12986828702967" granteeDisplayName="enus12986828648903" ownerId="4000b6a8-56bc-4910-ae3e-77528a5d5b18" rights="r" folderPath="/Inbox/folder12986828702964" mid="257" granteeType="usr" ownerEmail="enus12986828702967@testdomain.com" granteeName="enus12986828648903@testdomain.com" folderId="257"/>
		//	    </GetShareInfoResponse>

		String ownerEmail = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Inbox/"+ foldername +"']", "ownerEmail");
		ZAssert.assertEquals(ownerEmail, app.zGetActiveAccount().EmailAddress, "Verify the owner of the shared folder");
		
		String rights = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Inbox/"+ foldername +"']", "rights");
		// The order of the rights may not be consistent, check each one
		// ZAssert.assertEquals(rights, "rwidx", "Verify the rights are 'Manager'");
		ZAssert.assertStringContains(rights, "r", "Verify the rights are 'Manager'");
		ZAssert.assertStringContains(rights, "w", "Verify the rights are 'Manager'");
		ZAssert.assertStringContains(rights, "i", "Verify the rights are 'Manager'");
		ZAssert.assertStringContains(rights, "d", "Verify the rights are 'Manager'");
		ZAssert.assertStringContains(rights, "x", "Verify the rights are 'Manager'");
		
		String granteeType = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/Inbox/"+ foldername +"']", "granteeType");
		ZAssert.assertEquals(granteeType, "usr", "Verify the grantee type is 'user'");

	}



	

}
