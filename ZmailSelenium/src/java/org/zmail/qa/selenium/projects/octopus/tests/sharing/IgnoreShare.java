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
package com.zimbra.qa.selenium.projects.octopus.tests.sharing;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.OctopusAccount;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.octopus.core.OctopusCommonTest;
import com.zimbra.qa.selenium.projects.octopus.ui.PageSharing;

public class IgnoreShare extends OctopusCommonTest {

	private ZimbraAccount ownerAccount = null;

	public IgnoreShare() {
		logger.info("New " + AcceptShare.class.getCanonicalName());

		// Test starts at the Octopus page
		super.startingPage = app.zPageSharing;
		super.startingAccountPreferences = null;

		ownerAccount = new OctopusAccount();
		ownerAccount.provision();
		ownerAccount.authenticate();
	}

	@Test(description = "Ignore share invitation clicking on Ignore button", groups = { "smoke" })
	public void IgnoreShare_01() throws HarnessException {
		FolderItem ownerBriefcaseRootFolder = FolderItem.importFromSOAP(
				ownerAccount, SystemFolder.Briefcase);

		ZAssert.assertNotNull(ownerBriefcaseRootFolder,
				"Verify the owner Briefcase root folder exists");

		// Owner creates a folder, shares it with current user
		String ownerFoldername = "ownerFolder"
				+ ZimbraSeleniumProperties.getUniqueString();

		// Verify the share folder exists on the server
		FolderItem ownerFolderItem = createFolderViaSoap(ownerAccount, ownerFoldername,ownerBriefcaseRootFolder);

		ZAssert.assertNotNull(ownerFolderItem,
				"Verify the owner share folder exists");

		ZimbraAccount granteeAccount = app.zGetActiveAccount();

		shareFolderViaSoap(ownerAccount, granteeAccount, ownerFolderItem, SHARE_AS_READ);

		SleepUtil.sleepMedium();

		// Open Sharing tab
		PageSharing pageSharing = (PageSharing) app.zPageOctopus
				.zToolbarPressButton(Button.B_TAB_SHARING);

		// Currrent user gets share notification
		granteeAccount.soapSend("<GetShareNotificationsRequest xmlns='urn:zimbraMail'/>");

		ZAssert.assertTrue(pageSharing.zWaitForElementPresent(
				PageSharing.Locators.zShareNotificationListView.locator
				+ ":contains(" + ownerFolderItem.getName()
				+ ")", "9000"),
				"Verify the owner share folder is displayed in the Share Invitation view");

		// click on Ignore button
		pageSharing.zToolbarPressButton(Button.B_IGNORE, ownerFolderItem);

		// Verify the ignored item appears in the Ignored Items View
		ZAssert.assertTrue(pageSharing.zWaitForElementPresent(
				PageSharing.Locators.zIgnoredItemsView.locator + ":contains("
						+ ownerFolderItem.getName() + ")", "5000"),
				"Verify item appears in the Ignored Items View");

		// Click on My Files tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		// Verify the ignored folder doesn't appear in My Files list view
		ZAssert.assertFalse(app.zPageOctopus.zIsItemInCurentListView(ownerFolderItem.getName()),
				"Verify the ignored share folder doesn't appears in My Files list view");
	}
}
