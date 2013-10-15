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
package org.zmail.qa.selenium.projects.ajax.tests.mail.mountpoints.admin;

import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.core.*;
import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.*;


public class DeleteMail extends PrefGroupMailByMessageTest {

	
	public DeleteMail() {
		logger.info("New "+ DeleteMail.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefShowSelectionCheckbox", "TRUE");

	}
	
	@Bugs(ids = "66525, 26103")
	@Test(	description = "Delete a message from a mountpoint folder",
			groups = { "functional" })
	public void DeleteMail_01() throws HarnessException {
		
		
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String mountpointname = "mountpoint" + ZmailSeleniumProperties.getUniqueString();
		
		FolderItem inbox = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Inbox);
		
		// Create a folder to share
		ZmailAccount.AccountA().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername + "' l='" + inbox.getId() + "'/>"
				+	"</CreateFolderRequest>");
		
		FolderItem folder = FolderItem.importFromSOAP(ZmailAccount.AccountA(), foldername);
		
		// Share it
		ZmailAccount.AccountA().soapSend(
					"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ folder.getId() +"' op='grant'>"
				+			"<grant d='"+ app.zGetActiveAccount().EmailAddress +"' gt='usr' perm='rwidxa'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		
		// Add a message to it
		ZmailAccount.AccountA().soapSend(
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
		
		// Mount it
		app.zGetActiveAccount().soapSend(
					"<CreateMountpointRequest xmlns='urn:zmailMail'>"
				+		"<link l='1' name='"+ mountpointname +"'  rid='"+ folder.getId() +"' zid='"+ ZmailAccount.AccountA().ZmailId +"'/>"
				+	"</CreateMountpointRequest>");
		
		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointname);
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Click on the mountpoint
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, mountpoint);

		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		// Click delete
		app.zPageMail.zToolbarPressButton(Button.B_DELETE);

//		// A warning dialog will appear
//		DialogWarning dialog = app.zPageMain.zGetWarningDialog(DialogWarning.DialogWarningID.EmptyFolderWarningMessage);
//		ZAssert.assertNotNull(dialog, "Verify the dialog pops up");
//		dialog.zClickButton(Button.B_OK);

		// Verify the message is now in the local trash
		FolderItem trash = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Trash);
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +") is:anywhere");
		ZAssert.assertNotNull(mail, "Verify the message exists in the mailbox");
		ZAssert.assertEquals(mail.dFolderId, trash.getId(), "Verify the message exists in the local trash folder");
		
		// Verify the message is now in the ownser's trash
		trash = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Trash);
		mail = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ subject +") is:anywhere");
		ZAssert.assertNotNull(mail, "Verify the message exists in the mailbox");
		ZAssert.assertEquals(mail.dFolderId, trash.getId(), "Verify the message exists in the owner's trash folder");

		
	}

	@Bugs(ids = "66525, 26103")
	@Test(	description = "Delete multiple messages from a mountpoint folder",
			groups = { "functional" })
	public void DeleteMail_02() throws HarnessException {
		
		
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
		String subject1 = "subject" + ZmailSeleniumProperties.getUniqueString();
		String subject2 = "subject" + ZmailSeleniumProperties.getUniqueString();
		String mountpointname = "mountpoint" + ZmailSeleniumProperties.getUniqueString();
		
		FolderItem inbox = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Inbox);
		
		// Create a folder to share
		ZmailAccount.AccountA().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername + "' l='" + inbox.getId() + "'/>"
				+	"</CreateFolderRequest>");
		
		FolderItem folder = FolderItem.importFromSOAP(ZmailAccount.AccountA(), foldername);
		
		// Share it
		ZmailAccount.AccountA().soapSend(
					"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ folder.getId() +"' op='grant'>"
				+			"<grant d='"+ app.zGetActiveAccount().EmailAddress +"' gt='usr' perm='rwidxa'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		
		// Add a message to it
		ZmailAccount.AccountA().soapSend(
				"<AddMsgRequest xmlns='urn:zmailMail'>"
    		+		"<m l='"+ folder.getId() +"' f='u'>"
        	+			"<content>From: foo@foo.com\n"
        	+				"To: foo@foo.com \n"
        	+				"Subject: "+ subject1 +"\n"
        	+				"MIME-Version: 1.0 \n"
        	+				"Content-Type: text/plain; charset=utf-8 \n"
        	+				"Content-Transfer-Encoding: 7bit\n"
        	+				"\n"
        	+				"simple text string in the body\n"
        	+			"</content>"
        	+		"</m>"
			+	"</AddMsgRequest>");
	
		ZmailAccount.AccountA().soapSend(
				"<AddMsgRequest xmlns='urn:zmailMail'>"
    		+		"<m l='"+ folder.getId() +"' f='u'>"
        	+			"<content>From: foo@foo.com\n"
        	+				"To: foo@foo.com \n"
        	+				"Subject: "+ subject2 +"\n"
        	+				"MIME-Version: 1.0 \n"
        	+				"Content-Type: text/plain; charset=utf-8 \n"
        	+				"Content-Transfer-Encoding: 7bit\n"
        	+				"\n"
        	+				"simple text string in the body\n"
        	+			"</content>"
        	+		"</m>"
			+	"</AddMsgRequest>");
	
		// Mount it
		app.zGetActiveAccount().soapSend(
					"<CreateMountpointRequest xmlns='urn:zmailMail'>"
				+		"<link l='1' name='"+ mountpointname +"'  rid='"+ folder.getId() +"' zid='"+ ZmailAccount.AccountA().ZmailId +"'/>"
				+	"</CreateMountpointRequest>");
		
		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(app.zGetActiveAccount(), mountpointname);
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Click on the mountpoint
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, mountpoint);

		// Select the item
		app.zPageMail.zListItem(Action.A_MAIL_CHECKBOX, subject1);
		app.zPageMail.zListItem(Action.A_MAIL_CHECKBOX, subject2);

		// Click delete
		app.zPageMail.zToolbarPressButton(Button.B_DELETE);

//		// A warning dialog will appear
//		DialogWarning dialog = app.zPageMain.zGetWarningDialog(DialogWarning.DialogWarningID.EmptyFolderWarningMessage);
//		ZAssert.assertNotNull(dialog, "Verify the dialog pops up");
//		dialog.zClickButton(Button.B_OK);

		// Verify the message is now in the local trash
		FolderItem trash = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Trash);
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject1 +") is:anywhere");
		ZAssert.assertNotNull(mail, "Verify the message exists in the mailbox");
		ZAssert.assertEquals(mail.dFolderId, trash.getId(), "Verify the message exists in the local trash folder");
		mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject2 +") is:anywhere");
		ZAssert.assertNotNull(mail, "Verify the message exists in the mailbox");
		ZAssert.assertEquals(mail.dFolderId, trash.getId(), "Verify the message exists in the local trash folder");
		
		// Verify the message is now in the ownser's trash
		trash = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Trash);
		mail = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ subject1 +") is:anywhere");
		ZAssert.assertNotNull(mail, "Verify the message exists in the mailbox");
		ZAssert.assertEquals(mail.dFolderId, trash.getId(), "Verify the message exists in the owner's trash folder");
		mail = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ subject2 +") is:anywhere");
		ZAssert.assertNotNull(mail, "Verify the message exists in the mailbox");
		ZAssert.assertEquals(mail.dFolderId, trash.getId(), "Verify the message exists in the owner's trash folder");

		
	}


}
