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
package com.zimbra.qa.selenium.projects.admin.tests.aliases;

import java.util.List;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAdminAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.admin.core.AdminCommonTest;
import com.zimbra.qa.selenium.projects.admin.items.AccountItem;
import com.zimbra.qa.selenium.projects.admin.items.AliasItem;
import com.zimbra.qa.selenium.projects.admin.ui.DialogForDeleteOperation;
import com.zimbra.qa.selenium.projects.admin.ui.PageMain;

public class DeleteAlias extends AdminCommonTest {

	public DeleteAlias() {
		logger.info("New " + DeleteAlias.class.getCanonicalName());

		//All test starts at alias page
		super.startingPage=app.zPageManageAliases;		
	}
	
	/**
	 * Testcase : Verify delete alias operation  -- Manage alias View
	 * Steps :
	 * 1. Create an alias using SOAP.
	 * 2. Go to Manage alias View.
	 * 3. Select an alias.
	 * 4. Delete an alias using delete button in Gear box menu.
	 * 5. Verify alias is deleted using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Verify delete alias operation  -- Manage alias View",
			groups = { "smoke" })
			public void DeleteAlias_01() throws HarnessException {

		AccountItem target = new AccountItem("email" + ZimbraSeleniumProperties.getUniqueString(),ZimbraSeleniumProperties.getStringProperty("testdomain"));
		AccountItem.createUsingSOAP(target);


		// Create a new account in the Admin Console using SOAP
		AliasItem alias = new AliasItem();
		String aliasEmailAddress=alias.getEmailAddress();
		ZimbraAdminAccount.AdminConsoleAdmin().soapSend(
				"<AddAccountAliasRequest xmlns='urn:zimbraAdmin'>"
				+			"<id>" + target.getID() + "</id>"
				+			"<alias>" + aliasEmailAddress + "</alias>"
				+		"</AddAccountAliasRequest>");

		// Refresh the account list
		app.zPageManageAliases.sClickAt(PageMain.Locators.REFRESH_BUTTON, "");

		// Click on alias to be deleted.
		app.zPageManageAliases.zListItem(Action.A_LEFTCLICK, alias.getEmailAddress());

		// Click on Delete button
		DialogForDeleteOperation dialog = (DialogForDeleteOperation) app.zPageManageAliases.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_DELETE);

		// Click Yes in Confirmation dialog
		dialog.zClickButton(Button.B_YES);

		// Click Ok on "Delete Items" dialog
		dialog.zClickButton(Button.B_OK);

		// Get the list of displayed accounts
		List<AccountItem> accounts = app.zPageManageAliases.zListGetAccounts();
		ZAssert.assertNotNull(accounts, "Verify the alias list is returned");

		AccountItem found = null;
		for (AccountItem a : accounts) {
			logger.info("Looking for alias "+ aliasEmailAddress + " found: "+ a.getGEmailAddress());
			if ( aliasEmailAddress.equals(a.getGEmailAddress()) ) {
				found = a;
				break;
			}
		}
		ZAssert.assertNull(found, "Verify alias is deleted successfully");
	}
	
	/**
	 *
	 * Testcase : Verify delete alias operation-- Manage alias View/Right Click Menu
	 * Steps :
	 * 1. Create an alias using SOAP.
	 * 2. Go to Manage alias View.
	 * 3. Right Click on an alias.
	 * 4. Delete an alias using delete button in right click menu.
	 * 5. Verify alias is deleted using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Verify delete alias operation-- Manage alias View/Right Click Menu",
			groups = { "functional" })
			public void DeleteAlias_02() throws HarnessException {

		AccountItem target = new AccountItem("email" + ZimbraSeleniumProperties.getUniqueString(),ZimbraSeleniumProperties.getStringProperty("testdomain"));
		AccountItem.createUsingSOAP(target);


		// Create a new account in the Admin Console using SOAP
		AliasItem alias = new AliasItem();
		String aliasEmailAddress=alias.getEmailAddress();
		ZimbraAdminAccount.AdminConsoleAdmin().soapSend(
				"<AddAccountAliasRequest xmlns='urn:zimbraAdmin'>"
				+			"<id>" + target.getID() + "</id>"
				+			"<alias>" + aliasEmailAddress + "</alias>"
				+		"</AddAccountAliasRequest>");

		// Refresh the account list
		app.zPageManageAliases.sClickAt(PageMain.Locators.REFRESH_BUTTON, "");

		// Click on alias to be deleted.
		app.zPageManageAliases.zListItem(Action.A_RIGHTCLICK, alias.getEmailAddress());

		// Click on Delete button
		DialogForDeleteOperation dialog = (DialogForDeleteOperation) app.zPageManageAliases.zToolbarPressButton(Button.B_TREE_DELETE);

		// Click Yes in Confirmation dialog
		dialog.zClickButton(Button.B_YES);

		// Click Ok on "Delete Items" dialog
		dialog.zClickButton(Button.B_OK);

		// Get the list of displayed accounts
		List<AccountItem> accounts = app.zPageManageAliases.zListGetAccounts();
		ZAssert.assertNotNull(accounts, "Verify the alias list is returned");

		AccountItem found = null;
		for (AccountItem a : accounts) {
			logger.info("Looking for alias "+ aliasEmailAddress + " found: "+ a.getGEmailAddress());
			if ( aliasEmailAddress.equals(a.getGEmailAddress()) ) {
				found = a;
				break;
			}
		}
		ZAssert.assertNull(found, "Verify alias is deleted successfully");
	}


	/**
	 * Testcase : Verify delete alias operation - Search list view.
	 * Steps :
	 * 1. Create an alias using SOAP.
	 * 2. Search created alias.
	 * 3. Select the alias from gear box menu and select delete.
	 * 4. Verify account is deleted using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Verify delete alias operation - Search list view",
			groups = { "functional" })
			public void DeleteAlias_03() throws HarnessException {

		AccountItem target = new AccountItem("email" + ZimbraSeleniumProperties.getUniqueString(),ZimbraSeleniumProperties.getStringProperty("testdomain"));
		AccountItem.createUsingSOAP(target);


		// Create a new account in the Admin Console using SOAP
		AliasItem alias = new AliasItem();
		String aliasEmailAddress=alias.getEmailAddress();
		ZimbraAdminAccount.AdminConsoleAdmin().soapSend(
				"<AddAccountAliasRequest xmlns='urn:zimbraAdmin'>"
				+			"<id>" + target.getID() + "</id>"
				+			"<alias>" + aliasEmailAddress + "</alias>"
				+		"</AddAccountAliasRequest>");

		// Enter the search string to find the alias
		app.zPageSearchResults.zAddSearchQuery(aliasEmailAddress);

		// Click search
		app.zPageSearchResults.zToolbarPressButton(Button.B_SEARCH);

		// Click on alias to be deleted.
		app.zPageSearchResults.zListItem(Action.A_LEFTCLICK, alias.getEmailAddress());

		// Click on Delete button
		DialogForDeleteOperation dialog = (DialogForDeleteOperation) app.zPageSearchResults.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_DELETE);

		// Click Yes in Confirmation dialog
		dialog.zClickButton(Button.B_YES);

		// Click Ok on "Delete Items" dialog
		dialog.zClickButton(Button.B_OK);

		// Get the list of displayed accounts
		List<AccountItem> accounts = app.zPageSearchResults.zListGetAccounts();
		ZAssert.assertNotNull(accounts, "Verify the alias list is returned");

		AccountItem found = null;
		for (AccountItem a : accounts) {
			logger.info("Looking for alias "+ aliasEmailAddress + " found: "+ a.getGEmailAddress());
			if ( aliasEmailAddress.equals(a.getGEmailAddress()) ) {
				found = a;
				break;
			}
		}
		ZAssert.assertNull(found, "Verify alias is deleted successfully");
	}
	
	/**
	 *
	 * Testcase : Verify delete alias operation - Search list view/Right Click menu.
	 * Steps :
	 * 1. Create an alias using SOAP.
	 * 2. Search created alias.
	 * 3. Select the alias from gear box menu and select delete.
	 * 4. Verify account is deleted using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Verify delete alias operation - Search list view/Right Click menu.",
			groups = { "functional" })
			public void DeleteAlias_04() throws HarnessException {

		AccountItem target = new AccountItem("email" + ZimbraSeleniumProperties.getUniqueString(),ZimbraSeleniumProperties.getStringProperty("testdomain"));
		AccountItem.createUsingSOAP(target);


		// Create a new account in the Admin Console using SOAP
		AliasItem alias = new AliasItem();
		String aliasEmailAddress=alias.getEmailAddress();
		ZimbraAdminAccount.AdminConsoleAdmin().soapSend(
				"<AddAccountAliasRequest xmlns='urn:zimbraAdmin'>"
				+			"<id>" + target.getID() + "</id>"
				+			"<alias>" + aliasEmailAddress + "</alias>"
				+		"</AddAccountAliasRequest>");

		// Enter the search string to find the alias
		app.zPageSearchResults.zAddSearchQuery(aliasEmailAddress);

		// Click search
		app.zPageSearchResults.zToolbarPressButton(Button.B_SEARCH);

		// Right Click on alias to be deleted.
		app.zPageSearchResults.zListItem(Action.A_RIGHTCLICK, alias.getEmailAddress());

		// Click on Delete button
		DialogForDeleteOperation dialog = (DialogForDeleteOperation) app.zPageSearchResults.zToolbarPressButton(Button.B_TREE_DELETE);

		// Click Yes in Confirmation dialog
		dialog.zClickButton(Button.B_YES);

		// Click Ok on "Delete Items" dialog
		dialog.zClickButton(Button.B_OK);

		// Get the list of displayed accounts
		List<AccountItem> accounts = app.zPageSearchResults.zListGetAccounts();
		ZAssert.assertNotNull(accounts, "Verify the alias list is returned");

		AccountItem found = null;
		for (AccountItem a : accounts) {
			logger.info("Looking for alias "+ aliasEmailAddress + " found: "+ a.getGEmailAddress());
			if ( aliasEmailAddress.equals(a.getGEmailAddress()) ) {
				found = a;
				break;
			}
		}
		ZAssert.assertNull(found, "Verify alias is deleted successfully");
	}

}
