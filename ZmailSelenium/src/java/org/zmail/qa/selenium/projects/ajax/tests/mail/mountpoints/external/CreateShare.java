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
package org.zmail.qa.selenium.projects.ajax.tests.mail.mountpoints.external;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShare;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShare.ShareWith;


public class CreateShare extends PrefGroupMailByMessageTest {

	public CreateShare() {
		logger.info("New "+ CreateShare.class.getCanonicalName());
		
		
	}
	
	@Test(	description = "Share a folder - External",
			groups = { "smoke" })
	public void CreateShare_01() throws HarnessException {
		
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Inbox);
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
		String externalEmail = ZmailSeleniumProperties.getStringProperty("external.yahoo.account");

		// Create a subfolder in Inbox
		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername +"' l='" + inbox.getId() +"'/>"
				+	"</CreateFolderRequest>");
		String folderid = app.zGetActiveAccount().soapSelectValue("//mail:folder", "id");

		//Need to do Refresh by clicking on getmail button to see folder in the list 
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Make sure the folder was created on the server
		FolderItem subfolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);
		ZAssert.assertNotNull(subfolder, "Verify the folder exists on the server");


		// Right click on folder, select "Share"
		DialogShare dialog = (DialogShare)app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_SHARE, subfolder);
		ZAssert.assertNotNull(dialog, "Verify the sharing dialog pops up");

		// Use external and set the email
		dialog.zSetShareWith(ShareWith.ExternalGuests);
		dialog.zSetEmailAddress(externalEmail);
		
		// Send it
		dialog.zClickButton(Button.B_OK);
		
		// Verify the account has shared the folder
		app.zGetActiveAccount().soapSend(
					"<GetFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder l='" + folderid + "'/>"
				+	"</GetFolderRequest>");
		
		/** Example response:
		 *     <GetFolderResponse xmlns="urn:zmailMail">
		 *           <folder f="i" rev="2" i4next="258" i4ms="2" ms="4" n="0" activesyncdisabled="0" l="2" id="257" s="0" name="folder13379798458754" uuid="a4d8c530-d8f5-46e2-9798-c87c86968c82" luuid="9dce7c49-ec67-4315-868f-bbf090605034">
		 *             <acl guestGrantExpiry="1345755322480">
		 *               <grant zid="zmailexternal@yahoo.com" gt="guest" pw="" perm="r"/>
		 *             </acl>
 		 *          </folder>
		 *         </GetFolderResponse>
		 * 
		 **/

		String zid = app.zGetActiveAccount().soapSelectValue("//mail:grant", "zid");
		ZAssert.assertEquals(zid, externalEmail, "Verify the zid of the shared folder is set to the external address");
		
		String gt = app.zGetActiveAccount().soapSelectValue("//mail:grant", "gt");
		ZAssert.assertEquals(gt, "guest", "Verify the gt of the shared folder is guest");

		String pw = app.zGetActiveAccount().soapSelectValue("//mail:grant", "pw");
		ZAssert.assertEquals(pw, "", "Verify the default pw is blank");

		String perm = app.zGetActiveAccount().soapSelectValue("//mail:grant", "perm");
		ZAssert.assertEquals(perm, "r", "Verify the perm of the shared folder is 'r'");

	}

	

	

}
