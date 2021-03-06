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
package org.zmail.qa.selenium.projects.admin.tests.accounts;

import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAdminAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.admin.core.AdminCommonTest;
import org.zmail.qa.selenium.projects.admin.items.AccountItem;
import org.zmail.qa.selenium.projects.admin.ui.FormEditAccount;
import org.zmail.qa.selenium.projects.admin.ui.PageMain;

public class EditAccount extends AdminCommonTest {
	public EditAccount() {
		logger.info("New "+ EditAccount.class.getCanonicalName());

		// All tests start at the "Accounts" page
		super.startingPage = app.zPageManageAccounts;

	}

	/**
	 * Testcase : Edit account name  - Manage Account View
	 * Steps :
	 * 1. Create an account using SOAP.
	 * 2. Go to Manage Account View
	 * 3. Select an Account.
	 * 4. Edit an account using edit button in Gear box menu.
	 * 5. Verify account is edited using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Edit Account name  - Manage Account View",
			groups = { "smoke" })
			public void EditAccount_01() throws HarnessException {

		// Create a new account in the Admin Console using SOAP
		AccountItem account = new AccountItem("email" + ZmailSeleniumProperties.getUniqueString(),ZmailSeleniumProperties.getStringProperty("testdomain"));
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<CreateAccountRequest xmlns='urn:zmailAdmin'>"
				+			"<name>" + account.getEmailAddress() + "</name>"
				+			"<password>test123</password>"
				+		"</CreateAccountRequest>");

		// Refresh the account list
		app.zPageManageAccounts.sClickAt(PageMain.Locators.REFRESH_BUTTON, "");
		
		// Click on account to be Edited.
		app.zPageManageAccounts.zListItem(Action.A_LEFTCLICK, account.getEmailAddress());
		
		// Click on Edit button
		FormEditAccount form = (FormEditAccount) app.zPageManageAccounts.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_EDIT);
		
		//Click on General Information tab.
		form.zClickTreeItem(FormEditAccount.TreeItem.GENERAL_INFORMATION);

		//Edit the name.
		String editedName = "editedAccount_" + ZmailSeleniumProperties.getUniqueString();
		form.setName(editedName);
		
		//Submit the form.
		form.zSubmit();
		
		// Verify the account exists in the ZCS
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<GetAccountRequest xmlns='urn:zmailAdmin'>"
				+			"<account by='name'>"+ editedName+"@"+account.getDomainName() +"</account>"
				+		"</GetAccountRequest>");
		Element response = ZmailAdminAccount.AdminConsoleAdmin().soapSelectNode("//admin:GetAccountResponse/admin:account", 1);
		ZAssert.assertNotNull(response, "Verify the account is edited successfully");
	}
	
	/**
	 * Testcase : Edit account name -- right click 
	 * Steps :
	 * 1. Create an account using SOAP.
	 * 2. Edit the account name using UI Right Click.
	 * 3. Verify account name is changed using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Edit account name -- right click",
			groups = { "functional" })
			public void EditAccount_02() throws HarnessException {

		// Create a new account in the Admin Console using SOAP
		AccountItem account = new AccountItem("email" + ZmailSeleniumProperties.getUniqueString(),ZmailSeleniumProperties.getStringProperty("testdomain"));
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<CreateAccountRequest xmlns='urn:zmailAdmin'>"
				+			"<name>" + account.getEmailAddress() + "</name>"
				+			"<password>test123</password>"
				+		"</CreateAccountRequest>");

		// Refresh the account list
		app.zPageManageAccounts.sClickAt(PageMain.Locators.REFRESH_BUTTON, "");
		
		// Right Click on account to be Edited.
		app.zPageManageAccounts.zListItem(Action.A_RIGHTCLICK, account.getEmailAddress());
		
		// Click on Edit button
		FormEditAccount form = (FormEditAccount) app.zPageManageAccounts.zToolbarPressButton(Button.B_TREE_EDIT);
		
		//Click on General Information tab.
		form.zClickTreeItem(FormEditAccount.TreeItem.GENERAL_INFORMATION);

		//Edit the name.
		String editedName = "editedAccount_" + ZmailSeleniumProperties.getUniqueString();
		form.setName(editedName);
		
		//Submit the form.
		form.zSubmit();
		
		// Verify the account exists in the ZCS
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<GetAccountRequest xmlns='urn:zmailAdmin'>"
				+			"<account by='name'>"+ editedName+"@"+account.getDomainName() +"</account>"
				+		"</GetAccountRequest>");
		Element response = ZmailAdminAccount.AdminConsoleAdmin().soapSelectNode("//admin:GetAccountResponse/admin:account", 1);
		ZAssert.assertNotNull(response, "Verify the account is edited successfully");
	}

}
