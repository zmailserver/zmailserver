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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.sharing;

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.PagePreferences.ShareItem;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;


public class ShowMyShares extends AjaxCommonTest {

	
	
	public ShowMyShares() {
		
		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = null;
		
		
	}


	@Test(
			description = "View the sharing preference page",
			groups = { "functional" }
			)
	public void ShowMyShares_01() throws HarnessException {

		//*** Test Data
		ZmailAccount delegate = new ZmailAccount();
		delegate.provision();
		delegate.authenticate();

		
		String foldername = "folder"+ ZmailSeleniumProperties.getUniqueString();
		
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Inbox);
		ZAssert.assertNotNull(inbox, "Verify the new owner folder exists");

		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername +"' l='" + inbox.getId() +"'/>"
				+	"</CreateFolderRequest>");
		
		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);
		ZAssert.assertNotNull(folder, "Verify the new owner folder exists");
		
		app.zGetActiveAccount().soapSend(
					"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ folder.getId() +"' op='grant'>"
				+			"<grant d='" + delegate.EmailAddress + "' gt='usr' perm='r'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		

		
		
		//*** Test Steps
		
		// Refresh
		app.zPageMain.zToolbarPressButton(Button.B_REFRESH);
		
		// Navigate to preferences -> notifications
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Sharing);


		
		
		//*** Test Verification
		ShareItem found = null;
		List<ShareItem> items = app.zPagePreferences.zSharesGetSharedByMe();
		for (ShareItem item : items) {
			if (delegate.EmailAddress.contains(item.with)) {
				found = item;
				break;
			}
		}
		

		ZAssert.assertNotNull(found, "verify the shared item appears in the list");
		ZAssert.assertStringContains(found.item, foldername, "Verify the owner foldername");

		
	}
}
