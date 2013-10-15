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
package org.zmail.qa.selenium.projects.ajax.tests.conversation.mountpoints.viewer;

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByConversationTest;


public class GetConversation extends PrefGroupMailByConversationTest {

	
	public GetConversation() {
		logger.info("New "+ GetConversation.class.getCanonicalName());
		
		
		

		
		


	}
	
	@Test(	description = "View a conversation in a mountpoint",
			groups = { "functional" })
	public void GetMail_01() throws HarnessException {
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
				+			"<grant d='"+ app.zGetActiveAccount().EmailAddress +"' gt='usr' perm='r'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		
		// Add a message to it
		ZmailAccount.AccountA().soapSend(
					"<AddMsgRequest xmlns='urn:zmailMail'>"
        		+		"<m l='"+ folder.getId() +"' >"
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

		// Get all the messages in the inbox
		List<MailItem> messages = app.zPageMail.zListGetMessages();
		ZAssert.assertNotNull(messages, "Verify the message list exists");

		// Make sure the message appears in the list
		MailItem found = null;
		for (MailItem m : messages) {
			logger.info("Subject: looking for "+ subject +" found: "+ m.gSubject);
			if ( subject.equals(m.gSubject) ) {
				found = m;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the message is in the inbox");

		
	}

	@Test(	description = "View the contents of a conversation in a mountpoint",
			groups = { "functional" })
	public void GetMail_02() throws HarnessException {
		
		
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String fragment1 = "content" + ZmailSeleniumProperties.getUniqueString();
		String fragment2 = "content" + ZmailSeleniumProperties.getUniqueString();
		
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
				+			"<grant d='"+ app.zGetActiveAccount().EmailAddress +"' gt='usr' perm='r'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		
		// Add a message to it
		ZmailAccount.AccountA().soapSend(
					"<AddMsgRequest xmlns='urn:zmailMail'>"
        		+		"<m l='"+ folder.getId() +"' >"
            	+			"<content>From: "+ ZmailAccount.AccountA().EmailAddress +"\n"
            	+				"To: "+ app.zGetActiveAccount().EmailAddress +"\n"
            	+				"Cc: "+ ZmailAccount.AccountB().EmailAddress +"\n"
            	+				"Subject: RE: "+ subject +"\n"
            	+				"MIME-Version: 1.0 \n"
            	+				"Content-Type: text/plain; charset=utf-8 \n"
            	+				"Content-Transfer-Encoding: 7bit\n"
            	+				"\n"
            	+				fragment1 +"\n"
            	+			"</content>"
            	+		"</m>"
				+	"</AddMsgRequest>");

		// Add a message to it
		ZmailAccount.AccountA().soapSend(
					"<AddMsgRequest xmlns='urn:zmailMail'>"
        		+		"<m l='"+ folder.getId() +"' >"
            	+			"<content>From: "+ ZmailAccount.AccountA().EmailAddress +"\n"
            	+				"To: "+ app.zGetActiveAccount().EmailAddress +"\n"
            	+				"Cc: "+ ZmailAccount.AccountB().EmailAddress +"\n"
            	+				"Subject: RE: "+ subject +"\n"
            	+				"MIME-Version: 1.0 \n"
            	+				"Content-Type: text/plain; charset=utf-8 \n"
            	+				"Content-Transfer-Encoding: 7bit\n"
            	+				"\n"
            	+				fragment2 +"\n"
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
		
		// Expand the item
		app.zPageMail.zListItem(Action.A_MAIL_EXPANDCONVERSATION, subject);

		// Verify the list shows: 1 conversation with 2 messages
		
		List<MailItem> items = app.zPageMail.zListGetMessages();
		ZAssert.assertNotNull(items, "Verify the conversation list exists");

		boolean found = false;
		for (MailItem c : items) {
			logger.info("Subject: looking for "+ subject +" found: "+ c.gSubject);
			if ( subject.equals(c.gSubject) ) {
				found = true;
				break;
			}
		}
		ZAssert.assertTrue(found, "Verify the conversation is in the inbox");
		
		int count = 0;
		for (MailItem m : items) {
			logger.info("Subject: looking for "+ fragment1 +" or "+ fragment2 +" found: "+ m.gFragment);

			if ( m instanceof ConversationItem ) {

				ConversationItem c = (ConversationItem)m;
				
				if ( !c.gIsConvExpanded ) {
					// Not a conversation member
					continue;
				}
				
					
				if ( fragment1.equals(c.gFragment) ) {
					logger.info("Subject: Found "+ fragment1);
					count++;
				}
				if ( fragment2.equals(c.gFragment) ) {
					logger.info("Subject: Found "+ fragment2);
					count++;
				}
				
			}
				
		}
		ZAssert.assertEquals(count, 2, "Verify two messages in the conversation");

		
	}


}
