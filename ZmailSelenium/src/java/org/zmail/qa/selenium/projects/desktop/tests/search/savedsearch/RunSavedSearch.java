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
package org.zmail.qa.selenium.projects.desktop.tests.search.savedsearch;

import java.util.*;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;


//TODO: add more in ContactItem.java

public class RunSavedSearch extends AjaxCommonTest  {

	@SuppressWarnings("serial")
	public RunSavedSearch() {
		logger.info("New "+ RunSavedSearch.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage = app.zPageMail;

		// Make sure we are using an account with conversation view
		super.startingAccountPreferences = new HashMap<String, String>() {{
			put("zmailPrefGroupMailBy", "message");
		}};
		
	}
	
	
	@Test(	description = "Run a saved search",
			groups = { "smoke" })
	public void RunSavedSearch_01() throws HarnessException {				
				
			
		// Create the message data to be sent
		String name = "search" + ZmailSeleniumProperties.getUniqueString();
		String subject1 = "subject" + ZmailSeleniumProperties.getUniqueString();
		String subject2 = "subject" + ZmailSeleniumProperties.getUniqueString();
		String query = "subject:(" + subject1 + ")";
		

		// Send two messages with different subjects to the account
		ZmailAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>"+ subject1 +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>content1"+ ZmailSeleniumProperties.getUniqueString() +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");
		
		ZmailAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>"+ subject2 +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>content1"+ ZmailSeleniumProperties.getUniqueString() +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");
		

		// Create the saved search
		app.zGetActiveAccount().soapSend(
				"<CreateSearchFolderRequest xmlns='urn:zmailMail'>" +
					"<search name='"+ name +"' query='"+ query +"' l='1'/>" +
				"</CreateSearchFolderRequest>");
		
		// Get the item
		SavedSearchFolderItem item = SavedSearchFolderItem.importFromSOAP(app.zGetActiveAccount(), name);
		
		// Refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Left click on the search
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, item);

		// Verify the correct messages appear
		List<MailItem> messages = app.zPageMail.zListGetMessages();
		ZAssert.assertNotNull(messages, "Verify the message list exists");

		MailItem found1 = null;
		MailItem found2 = null;
		for (MailItem m : messages) {
			logger.info("Subject: looking for "+ subject1 +" found: "+ m.gSubject);
			if ( subject1.equals(m.gSubject) ) {
				found1 = m;
				break;
			}
			logger.info("Subject: looking for "+ subject2 +" found: "+ m.gSubject);
			if ( subject2.equals(m.gSubject) ) {
				found2 = m;
				break;
			}
		}
		ZAssert.assertNotNull(found1, "Verify the matched message exists in the inbox");
		ZAssert.assertNull(found2, "Verify the un-match message does not exist in the inbox");

	}
}
