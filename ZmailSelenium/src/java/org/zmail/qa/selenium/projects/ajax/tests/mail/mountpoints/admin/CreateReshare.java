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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.mountpoints.admin;

import org.testng.annotations.*;

import com.zimbra.qa.selenium.framework.core.*;
import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.*;
import com.zimbra.qa.selenium.projects.ajax.ui.*;


public class CreateReshare extends PrefGroupMailByMessageTest {


	public CreateReshare() {
		logger.info("New "+ CreateReshare.class.getCanonicalName());
		

	}
	
	/**
	 * 1. Account1 shares a folder with admin rights to Account2
	 * 2. Account2 re-shares the folder with Account3
	 * 3. Verify Account3 can view the folder contents
	 */
	@Bugs(ids = "68760")
	@Test(	description = "Reshare a folder that is shared as admin",
			groups = { "functional" })
	public void CreateReshare_01() throws HarnessException {

		// Create the owner and destination accounts
		ZimbraAccount Owner = new ZimbraAccount();
		Owner.provision();
		Owner.authenticate();

		ZimbraAccount Destination = new ZimbraAccount();
		Destination.provision();
		Destination.authenticate();

		FolderItem ownerInbox = FolderItem.importFromSOAP(Owner, FolderItem.SystemFolder.Inbox);
		String ownerFoldername = "folder" + ZimbraSeleniumProperties.getUniqueString();


		// Owner shares a folder to the test account with admin rights
		//
		Owner.soapSend(
					"<CreateFolderRequest xmlns='urn:zimbraMail'>"
				+		"<folder name='" + ownerFoldername +"' l='" + ownerInbox.getId() +"'/>"
				+	"</CreateFolderRequest>");
		String ownerFolderid = Owner.soapSelectValue("//mail:folder", "id");
		
		Owner.soapSend(
				"<FolderActionRequest xmlns='urn:zimbraMail'>"
			+		"<action id='"+ ownerFolderid +"' op='grant'>"
			+			"<grant d='" + app.zGetActiveAccount().EmailAddress + "' gt='usr' perm='rwidxa'/>"
			+		"</action>"
			+	"</FolderActionRequest>");

		
		// Test account creates a mountpoint
		//
		String mountpointFoldername = "mountpoint"+ ZimbraSeleniumProperties.getUniqueString();
		app.zGetActiveAccount().soapSend(
					"<CreateMountpointRequest xmlns='urn:zimbraMail'>"
				+		"<link l='1' name='"+ mountpointFoldername +"' view='message' rid='"+ ownerFolderid +"' zid='"+ Owner.ZimbraId +"'/>"
				+	"</CreateMountpointRequest>");
		
		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointFoldername);
		ZAssert.assertNotNull(mountpoint, "Verify the subfolder is available");


		// Click Get Mail button to see the new mountpoint
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		DialogShare dialog = (DialogShare)app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_SHARE, mountpoint);
		ZAssert.assertNotNull(dialog, "Verify the sharing dialog pops up");

		// Use defaults for all options
		dialog.zSetEmailAddress(Destination.EmailAddress);
		
		// Send it
		dialog.zClickButton(Button.B_OK);
		
		// Make sure that AccountA now has the share
		Destination.soapSend(
					"<GetShareInfoRequest xmlns='urn:zimbraAccount'>"
				+		"<grantee type='usr'/>"
				+		"<owner by='name'>"+ Owner.EmailAddress +"</owner>"
				+	"</GetShareInfoRequest>");
		
		// Example response:
		//	    <GetShareInfoResponse xmlns="urn:zimbraAccount">
		//	      <share granteeId="0136d047-b771-49c0-a735-12183f3ca654" ownerName="enus12986828702967" granteeDisplayName="enus12986828648903" ownerId="4000b6a8-56bc-4910-ae3e-77528a5d5b18" rights="r" folderPath="/Inbox/folder12986828702964" mid="257" granteeType="usr" ownerEmail="enus12986828702967@testdomain.com" granteeName="enus12986828648903@testdomain.com" folderId="257"/>
		//	    </GetShareInfoResponse>

		String destinationPath = Destination.soapSelectValue("//acct:GetShareInfoResponse//acct:share[@ownerEmail='"+ Owner.EmailAddress +"']", "folderPath");
		ZAssert.assertNotNull(destinationPath, "Verify the share exists");
		ZAssert.assertStringContains(destinationPath, ownerFoldername, "Verify the test account has shared the folder");
		
		// I suppose this verification is correct.  It could show the Owner's email or Owner's foldername.
		// I'm blocked on the actual verification due to bug 68760

	}



	

}
