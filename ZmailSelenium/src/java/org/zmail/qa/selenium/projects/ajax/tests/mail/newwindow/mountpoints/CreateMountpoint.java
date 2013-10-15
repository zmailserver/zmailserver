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
package org.zmail.qa.selenium.projects.ajax.tests.mail.newwindow.mountpoints;


import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.XmlStringUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.staf.Stafpostqueue;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.SeparateWindowDialog;
import org.zmail.qa.selenium.projects.ajax.ui.mail.SeparateWindowDisplayMail;


public class CreateMountpoint extends PrefGroupMailByMessageTest {

	
	
	public CreateMountpoint() {
		logger.info("New "+ CreateMountpoint.class.getCanonicalName());
		
		
	}
	
	@Test(	description = "Receive an invitation to a shared folder, accept it - in a separate window",
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




		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		app.zPageMail.zListItem(Action.A_LEFTCLICK, shareMessageSubject);

		
		
		SeparateWindowDisplayMail window = null;
		
		try {
			
			// Choose Actions -> Launch in Window
			window = (SeparateWindowDisplayMail)app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_LAUNCH_IN_SEPARATE_WINDOW);
			
			window.zSetWindowTitle(shareMessageSubject);
			window.zWaitForActive();		// Make sure the window is there
			
			ZAssert.assertTrue(window.zIsActive(), "Verify the window is active");
			
			// Verify that the A/D buttons are displayed
			ZAssert.assertTrue(window.zHasShareADButtons(), "Verify that the Accept/Decline share buttons are present");
			
			// Accept the share, which opens a dialog
			SeparateWindowDialog dialog = (SeparateWindowDialog)window.zPressButton(Button.B_ACCEPT_SHARE);
			ZAssert.assertNotNull(dialog, "Verify that the accept share dialog opens");
			
			// Click OK on the dialog
			dialog.zClickButton(Button.B_YES);

			// The dialog will send a message, so wait for delivery
			Stafpostqueue sp = new Stafpostqueue();
			sp.waitForPostqueue();

		} finally {
			
			// Make sure to close the window
			if ( window != null ) {
				window.zCloseWindow();
				window = null;
			}
			
		}
		
		
		
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
