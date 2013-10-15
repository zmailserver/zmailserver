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
package org.zmail.qa.selenium.projects.ajax.tests.mail.mountpoints;


import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShareAccept;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail;


public class CreateMountpoint extends PrefGroupMailByMessageTest {

	
	
	public CreateMountpoint() {
		logger.info("New "+ CreateMountpoint.class.getCanonicalName());
		
		
	}
	
	@Test(	description = "Receive an invitation to a shared folder, accept it.",
			groups = { "smoke" })
	public void CreateMountpoint_01() throws HarnessException {
		
		ZmailAccount Owner = (new ZmailAccount()).provision().authenticate();

		// Owner creates a folder, shares it with current user, and sends invitation
		String ownerFoldername = "ownerfolder"+ ZmailSeleniumProperties.getUniqueString();
		
		FolderItem ownerInbox = FolderItem.importFromSOAP(Owner, FolderItem.SystemFolder.Inbox);
		ZAssert.assertNotNull(ownerInbox, "Verify the new owner folder exists");

		Owner.soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + ownerFoldername +"' l='" + ownerInbox.getId() +"'/>"
				+	"</CreateFolderRequest>");
		
		FolderItem ownerFolder = FolderItem.importFromSOAP(Owner, ownerFoldername);
		ZAssert.assertNotNull(ownerFolder, "Verify the new owner folder exists");
		
		Owner.soapSend(
					"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ ownerFolder.getId() +"' op='grant'>"
				+			"<grant d='" + app.zGetActiveAccount().EmailAddress + "' gt='usr' perm='r'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		

		String shareMessageSubject = "shared"+ ZmailSeleniumProperties.getUniqueString();
		String shareElement = String.format(
					"<share xmlns='urn:zmailShare' version='0.1' action='new' >"
				+		"<grantee id='%s' email='%s' name='%s' />"
				+		"<grantor id='%s' email='%s' name='%s' />"
				+		"<link id='%s' name='%s' view='message' perm='r' />"
				+		"<notes/>"
				+	"</share>",
					app.zGetActiveAccount().ZmailId, app.zGetActiveAccount().EmailAddress, app.zGetActiveAccount().EmailAddress,
					Owner.ZmailId, Owner.EmailAddress, Owner.EmailAddress,
					ownerFolder.getId(), ownerFolder.getName());
					
		Owner.soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>"
				+		"<m>"
				+			"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>"
				+			"<su>"+ shareMessageSubject +"</su>"
				+			"<mp ct='multipart/alternative'>"
				+				"<mp ct='text/plain'>"
				+					"<content>shared</content>"
				+				"</mp>"
				+				"<mp ct='xml/x-zmail-share'>"
				+					"<content>"+ XmlStringUtil.escapeXml(shareElement) +"</content>"
				+				"</mp>"
				+			"</mp>"
				+		"</m>"
				+	"</SendMsgRequest>");



		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Inbox);

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Click the inbox
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, inbox);
				
		// Select the item
		DisplayMail display = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, shareMessageSubject);
		
		// Verify that the A/D buttons are displayed
		ZAssert.assertTrue(display.zHasShareADButtons(), "Verify that the Accept/Decline share buttons are present");
		
		// Accept the share, which opens a dialog
		DialogShareAccept dialog = (DialogShareAccept)display.zPressButton(Button.B_ACCEPT_SHARE);
		ZAssert.assertNotNull(dialog, "Verify that the accept share dialog opens");
		
		// Click OK on the dialog
		dialog.zClickButton(Button.B_YES);
		
		FolderItem found = null;
		
		// Verify that the new mountpoint is present
		logger.info("Looking for mountpoint containing text: "+ ownerFoldername);

		List<FolderItem> folders = app.zTreeMail.zListGetFolders();
		for (FolderItem f : folders) {
			if ( f.getName().contains(ownerFoldername) ) {
				logger.info("Found folder item: "+ f.getName());
				found = f;
				break;
			}
		}
		
		ZAssert.assertNotNull(found, "Verify the mountpoint is in the folder list");
		
	}

	
	

	

}
