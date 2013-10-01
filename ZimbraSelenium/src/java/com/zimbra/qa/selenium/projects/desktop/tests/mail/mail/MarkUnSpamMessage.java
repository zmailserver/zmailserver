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
package com.zimbra.qa.selenium.projects.desktop.tests.mail.mail;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.desktop.core.AjaxCommonTest;


public class MarkUnSpamMessage extends AjaxCommonTest {

	
	@SuppressWarnings("serial")
	public MarkUnSpamMessage() {
		logger.info("New "+ MarkUnSpamMessage.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String , String>() {{
				    put("zimbraPrefGroupMailBy", "message");
				}};
		
	}
	
	@Test(	description = "Mark a message as not spam, using 'Not Spam' toolbar button",
			groups = { "smoke" })
	public void MarkUnSpamMessage_01() throws HarnessException {
		
		String subject = "subject"+ ZimbraSeleniumProperties.getUniqueString();
		
		// Get the junk and inbox folder
		FolderItem junk = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Junk);
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);


		// Add a message to the account's junk folder
		app.zGetActiveAccount().soapSend(
				"<AddMsgRequest xmlns='urn:zimbraMail'>" +
            		"<m l='"+ junk.getId() +"'>" +
                		"<content>From: foo@foo.com\n" +
"To: foo@foo.com \n" +
"Subject: "+ subject +"\n" +
"MIME-Version: 1.0 \n" +
"Content-Type: text/plain; charset=utf-8 \n" +
"Content-Transfer-Encoding: 7bit\n" +
"\n" +
"simple text string in the body\n" +
"</content>" +
                	"</m>" +
            	"</AddMsgRequest>");
		
		// Get the mail item for the new message
		// Need 'is:anywhere' to include the spam folder
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "is:anywhere subject:("+ subject +")");
		

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
				
		// Go to the Junk folder
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, junk);
		
		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);
		
		// Click spam
		app.zPageMail.zToolbarPressButton(Button.B_RESPORTNOTSPAM);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		
		// Get the mail item for the new message
		// Need 'is:anywhere' to include the spam folder
		mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "is:anywhere subject:("+ subject +")");
		ZAssert.assertNotNull(mail, "Make sure the mail is found");

		ZAssert.assertEquals(mail.dFolderId, inbox.getId(), "Verify the message is in the inbox folder");
				
	}


}
