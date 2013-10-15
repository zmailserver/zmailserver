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
package org.zmail.qa.selenium.projects.ajax.tests.login.mountpoints.folders;

import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;



public class OwnerAccountMaintenance extends AjaxCommonTest {
	
	protected ZmailAccount Owner = null;
	
	public OwnerAccountMaintenance() {
		logger.info("New "+ OwnerAccountMaintenance.class.getCanonicalName());
		
		// Test starts in the mail app
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = null;
		
	}
	
	@BeforeMethod(	description = "Make sure the Owner account exists",
					groups = { "always" } )
	public void CreateOwner() throws HarnessException {
		Owner = new ZmailAccount();
		Owner.provision();
		Owner.authenticate();
	}
	
	
	@Test(	description = "Login to the Ajax Client - with a mountpoint to a 'in-maintenance' account",
			groups = { "functional" })
	public void OwnerAccountMaintenance01() throws HarnessException {
		
		// Data setup
		
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
		String mountpointname = "mountpoint" + ZmailSeleniumProperties.getUniqueString();
		
		FolderItem inbox = FolderItem.importFromSOAP(Owner, FolderItem.SystemFolder.Inbox);
		
		// Create a folder to share
		Owner.soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername + "' l='" + inbox.getId() + "'/>"
				+	"</CreateFolderRequest>");
		
		FolderItem folder = FolderItem.importFromSOAP(Owner, foldername);
		
		Owner.soapSend(
				"<AddMsgRequest xmlns='urn:zmailMail'>"
    		+		"<m l='"+ folder.getId() +"' f='u'>"
        	+			"<content>From: foo@foo.com\n"
        	+				"To: foo@foo.com \n"
        	+				"Subject: "+ subject +"\n"
        	+				"MIME-Version: 1.0 \n"
        	+				"Content-Type: text/plain; charset=utf-8 \n"
        	+				"Content-Transfer-Encoding: 7bit\n"
        	+				"\n"
        	+				"simple text string in the body\n"
        	+			"</content>"
        	+		"</m>"
			+	"</AddMsgRequest>");

		// Share it
		Owner.soapSend(
					"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ folder.getId() +"' op='grant'>"
				+			"<grant d='"+ app.zGetActiveAccount().EmailAddress +"' gt='usr' perm='rwidxa'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		
		
		// Mount it
		app.zGetActiveAccount().soapSend(
					"<CreateMountpointRequest xmlns='urn:zmailMail'>"
				+		"<link l='1' name='"+ mountpointname +"'  rid='"+ folder.getId() +"' zid='"+ Owner.ZmailId +"'/>"
				+	"</CreateMountpointRequest>");
		
		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointname);
		ZAssert.assertNotNull(mountpoint, "Verify the mountpoint was created");
		
		
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// View the folder
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, mountpoint);
		
		// Logout
		ZmailAccount account = app.zGetActiveAccount();
		app.zPageMain.zLogout();
		
		// Make the owner account in maintenance
		ZmailAdminAccount.GlobalAdmin().soapSend(
					"<ModifyAccountRequest xmlns='urn:zmailAdmin'>"
				+		"<id>"+ Owner.ZmailId + "</id>"
				+		"<a n='zmailAccountStatus'>maintenance</a>"
				+	"</ModifyAccountRequest>");
		
		
		// Login
		app.zPageLogin.zLogin(account);
		
		
		// View the folder
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, mountpoint);
		

	}


}
