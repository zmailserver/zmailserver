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
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.XmlStringUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShareAccept;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail;

public class CreateMountpoint extends AjaxCommonTest{
	
	
	@SuppressWarnings("serial")
	public CreateMountpoint() {
		logger.info("New "+ CreateMountpoint.class.getCanonicalName());
		super.startingPage = app.zPageTasks;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefReadingPaneLocation", "bottom");
				put("zmailPrefShowSelectionCheckbox", "TRUE");
				put("zmailPrefGroupMailBy", "message");
			}
		};		
		
	}
	
	@Test(	description = "Receive an invitation to a shared folder, accept it.",
			groups = { "smoke" })
	public void CreateMountpoint_01() throws HarnessException {
		
		ZmailAccount Owner = (new ZmailAccount()).provision().authenticate();

		FolderItem ownerTask = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);

		// Create the subTaskList
		String ownerFoldername = "ownertaskList" + ZmailSeleniumProperties.getUniqueString();
		
		// Owner creates a folder, shares it with current user, and sends invitation
		Owner.soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + ownerFoldername +"' l='" + ownerTask.getId() +"'/>"
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
				+		"<link id='%s' name='%s' view='task' perm='r' />"
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
		app.zPageMail.zNavigateTo();

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
		
				
		// Verify that the new mountpoint is present
		FolderItem folder = FolderItem.importFromSOAP(Owner,ownerFoldername);
		logger.info("Looking for mountpoint containing text: "+ folder.getName());
		
		ZAssert.assertNotNull(folder, "Verify the mountpoint is in the folder list");
		ZAssert.assertEquals(folder.getName(), ownerFoldername,"Verify the server and client folder names match");
		
	}


}
