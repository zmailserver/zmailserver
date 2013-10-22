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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.bugs;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.core.Bugs;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import com.zimbra.qa.selenium.projects.ajax.tests.mail.mail.GetMail;

public class Bug57468 extends PrefGroupMailByMessageTest {

	/**
	 * @throws HarnessException
	 */
	@AfterClass( groups = { "always" } )
	public void bug57468AfterClass() throws HarnessException {
		logger.info("bug57468AfterClass: start");
		
		// Since we collapsed the folder tree, it may cause problems for other tests
		// Rest the ZWC user
		ZimbraAccount.ResetAccountZWC();
		
		logger.info("bug57468AfterClass: finish");
	}

	public Bug57468() {
		logger.info("New "+ GetMail.class.getCanonicalName());
		
		
		

	}

	@Bugs( ids = "57468")
	@Test(	description = "Verify collapsed folders remain collapsed when getting mail",
			groups = { "functional" })
	public void Bug57468_01() throws HarnessException {

		// Create a subfolder in Inbox
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
		ZAssert.assertNotNull(inbox, "Verify the inbox is available");

		String foldername = "folder" + ZimbraSeleniumProperties.getUniqueString();
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zimbraMail'>" +
                	"<folder name='"+ foldername +"' l='"+ inbox.getId() +"'/>" +
                "</CreateFolderRequest>");

		
		// Click Get Mail button to refresh the tree
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, inbox);

		
		// Collapse the inbox
		app.zTreeMail.zTreeItem(Action.A_TREE_COLLAPSE, inbox);

		
		
		// Send a message to the test account
		ZimbraAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ "subject" + ZimbraSeleniumProperties.getUniqueString() +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>content" + ZimbraSeleniumProperties.getUniqueString() +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");
		
		
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		
		// Verify the inbox remains collapsed
		List<FolderItem> folders = app.zTreeMail.zListGetFolders();
		FolderItem found = null;
		for (FolderItem f : folders) {
			if ( f.getId().equals(inbox.getId()) ) {
				found = f;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the inbox is in the folder tree");
		
		// Collapse the inbox if it is currently expanded
		ZAssert.assertFalse(found.gGetIsExpanded(), "Verify that the inbox is not expanded");
		
		
	}

}
