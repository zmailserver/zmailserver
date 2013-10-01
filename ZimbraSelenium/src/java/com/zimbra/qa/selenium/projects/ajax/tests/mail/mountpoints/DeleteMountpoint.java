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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.mountpoints;


import java.util.List;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderMountpointItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;


public class DeleteMountpoint extends PrefGroupMailByMessageTest {

	
	
	public DeleteMountpoint() {
		logger.info("New "+ DeleteMountpoint.class.getCanonicalName());
		
		
	}
	
	@Test(	description = "Delete a mountpoint to a shared folder (right click -> Delete)",
			groups = { "smoke" })
	public void DeleteMountpoint_01() throws HarnessException {
		
		ZimbraAccount Owner = (new ZimbraAccount()).provision().authenticate();

		// Owner creates a folder, shares it with current user
		String ownerFoldername = "ownerfolder"+ ZimbraSeleniumProperties.getUniqueString();
		
		FolderItem ownerInbox = FolderItem.importFromSOAP(Owner, FolderItem.SystemFolder.Inbox);
		ZAssert.assertNotNull(ownerInbox, "Verify the new owner folder exists");

		Owner.soapSend(
					"<CreateFolderRequest xmlns='urn:zimbraMail'>"
				+		"<folder name='" + ownerFoldername +"' l='" + ownerInbox.getId() +"'/>"
				+	"</CreateFolderRequest>");
		
		FolderItem ownerFolder = FolderItem.importFromSOAP(Owner, ownerFoldername);
		ZAssert.assertNotNull(ownerFolder, "Verify the new owner folder exists");
		
		Owner.soapSend(
					"<FolderActionRequest xmlns='urn:zimbraMail'>"
				+		"<action id='"+ ownerFolder.getId() +"' op='grant'>"
				+			"<grant d='" + app.zGetActiveAccount().EmailAddress + "' gt='usr' perm='r'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		

		// Current user creates the mountpoint that points to the share
		String mountpointFoldername = "mountpoint"+ ZimbraSeleniumProperties.getUniqueString();
		app.zGetActiveAccount().soapSend(
					"<CreateMountpointRequest xmlns='urn:zimbraMail'>"
				+		"<link l='1' name='"+ mountpointFoldername +"' view='message' rid='"+ ownerFolder.getId() +"' zid='"+ Owner.ZimbraId +"'/>"
				+	"</CreateMountpointRequest>");
		
		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointFoldername);
		ZAssert.assertNotNull(mountpoint, "Verify the subfolder is available");


		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Verify the mountpoint exists
		List<FolderItem> folders = app.zTreeMail.zListGetFolders();
		
		FolderItem found = null;
		for(FolderItem f : folders) {
			if ( mountpointFoldername.equals(f.getName()) ) {
				found = f;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the mountpoint exists in the folder tree");
		
		
		// Delete the folder using context menu
		app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_DELETE, mountpoint);
		
		
		// Verify the folder is now in the trash
		FolderItem trash = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Trash);
		mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointFoldername);
		ZAssert.assertNotNull(mountpoint, "Verify the subfolder is again available");
		ZAssert.assertEquals(trash.getId(), mountpoint.getParentId(), "Verify the subfolder's parent is now the trash folder ID");
		
	}

	
	

	

}
