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
package org.zmail.qa.selenium.projects.admin.tests.cos;

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAdminAccount;
import org.zmail.qa.selenium.projects.admin.core.AdminCommonTest;
import org.zmail.qa.selenium.projects.admin.items.AccountItem;
import org.zmail.qa.selenium.projects.admin.items.CosItem;
import org.zmail.qa.selenium.projects.admin.ui.DialogForDeleteOperation;

public class DeleteCos extends AdminCommonTest {
	public DeleteCos() {
		logger.info("New " + DeleteCos.class.getCanonicalName());

		//All tests starts at "Cos" page
		super.startingPage=app.zPageManageCOS;
	}

	/**
	 * Testcase : Verify delete cos operation -- Search list view.
	 * Steps :
	 * 1. Create a cos using SOAP.
	 * 2. Search cos created in Step-1.
	 * 3. Select delete from gear box menu
	 * 4. Verify cos is deleted using SOAP
	 * @throws HarnessException
	 */
	@Test(	description = "Verify delete cos operation -- Manage cos view",
			groups = { "smoke" })
			public void DeleteCos_01() throws HarnessException {

		// Create a new cos in the Admin Console using SOAP
		CosItem cos = new CosItem();
		String cosName=cos.getName();

		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<CreateCosRequest xmlns='urn:zmailAdmin'>"
				+			"<name>" + cosName + "</name>"
				+		"</CreateCosRequest>");

		// Enter the search string to find the account
		app.zPageSearchResults.zAddSearchQuery(cosName);

		// Click search
		app.zPageSearchResults.zToolbarPressButton(Button.B_SEARCH);

		// Click on cos to be deleted.
		app.zPageSearchResults.zListItem(Action.A_LEFTCLICK, cos.getName());

		// Click on Delete button
		DialogForDeleteOperation dialog = (DialogForDeleteOperation) app.zPageSearchResults.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_DELETE);

		// Click Yes in Confirmation dialog
		dialog.zClickButton(Button.B_YES);

		// Click Ok on "Delete Items" dialog
		dialog.zClickButton(Button.B_OK);

		// Get the list of displayed accounts
		List<AccountItem> accounts = app.zPageSearchResults.zListGetAccounts();
		ZAssert.assertNotNull(accounts, "Verify the cos list is returned");

		AccountItem found = null;
		for (AccountItem a : accounts) {
			logger.info("Looking for cos "+ cosName + " found: "+ a.getGEmailAddress());
			if ( cosName.equals(a.getGEmailAddress()) ) {
				found = a;
				break;
			}
		}
		ZAssert.assertNull(found, "Verify the cos is deleted successfully");
	}
	
	/**
	 * Testcase : Verify delete cos operation -- Search list view/Right click menu.
	 * Steps :
	 * 1. Create a cos using SOAP.
	 * 2. Search cos created in Step-1.
	 * 3. Select delete from right click menu
	 * 4. Verify cos is deleted using SOAP
	 * @throws HarnessException
	 */
	@Test(	description = "Verify delete cos operation -- Search list view/Right click menu",
			groups = { "functional" })
			public void DeleteCos_02() throws HarnessException {

		// Create a new cos in the Admin Console using SOAP
		CosItem cos = new CosItem();
		String cosName=cos.getName();

		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<CreateCosRequest xmlns='urn:zmailAdmin'>"
				+			"<name>" + cosName + "</name>"
				+		"</CreateCosRequest>");

		// Enter the search string to find the account
		app.zPageSearchResults.zAddSearchQuery(cosName);

		// Click search
		app.zPageSearchResults.zToolbarPressButton(Button.B_SEARCH);

		// Right Click on cos to be deleted.
		app.zPageSearchResults.zListItem(Action.A_RIGHTCLICK, cos.getName());

		// Click on Delete button
		DialogForDeleteOperation dialog = (DialogForDeleteOperation) app.zPageSearchResults.zToolbarPressButton(Button.B_TREE_DELETE);

		// Click Yes in Confirmation dialog
		dialog.zClickButton(Button.B_YES);

		// Click Ok on "Delete Items" dialog
		dialog.zClickButton(Button.B_OK);

		// Get the list of displayed accounts
		List<AccountItem> accounts = app.zPageSearchResults.zListGetAccounts();
		ZAssert.assertNotNull(accounts, "Verify the cos list is returned");

		AccountItem found = null;
		for (AccountItem a : accounts) {
			logger.info("Looking for cos "+ cosName + " found: "+ a.getGEmailAddress());
			if ( cosName.equals(a.getGEmailAddress()) ) {
				found = a;
				break;
			}
		}
		ZAssert.assertNull(found, "Verify the cos is deleted successfully");
	}
	
	/**
	 * Testcase : Verify delete cos operation -- Search list view.
	 * Steps :
	 * 1. Create a cos using SOAP.
	 * 2. Search cos created in Step-1.
	 * 3. Select delete from gear box menu
	 * 4. Verify cos is deleted using SOAP
	 * @throws HarnessException
	 */
	@Test(	description = "Verify delete cos operation -- Manage cos view",
			groups = { "functional" })
			public void DeleteCos_03() throws HarnessException {

		// Create a new cos in the Admin Console using SOAP
		CosItem cos = new CosItem();
		String cosName=cos.getName();

		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<CreateCosRequest xmlns='urn:zmailAdmin'>"
				+			"<name>" + cosName + "</name>"
				+		"</CreateCosRequest>");

		// Enter the search string to find the account
		app.zPageSearchResults.zAddSearchQuery(cosName);

		// Click search
		app.zPageSearchResults.zToolbarPressButton(Button.B_SEARCH);

		// Click on cos to be deleted.
		app.zPageSearchResults.zListItem(Action.A_LEFTCLICK, cos.getName());

		// Click on Delete button
		DialogForDeleteOperation dialog = (DialogForDeleteOperation) app.zPageSearchResults.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_DELETE);

		// Click Yes in Confirmation dialog
		dialog.zClickButton(Button.B_YES);

		// Click Ok on "Delete Items" dialog
		dialog.zClickButton(Button.B_OK);

		// Get the list of displayed accounts
		List<AccountItem> accounts = app.zPageSearchResults.zListGetAccounts();
		ZAssert.assertNotNull(accounts, "Verify the cos list is returned");

		AccountItem found = null;
		for (AccountItem a : accounts) {
			logger.info("Looking for cos "+ cosName + " found: "+ a.getGEmailAddress());
			if ( cosName.equals(a.getGEmailAddress()) ) {
				found = a;
				break;
			}
		}
		ZAssert.assertNull(found, "Verify the cos is deleted successfully");
	}
	
	/**
	 * Testcase : Verify delete cos operation -- Search list view/Right click menu.
	 * Steps :
	 * 1. Create a cos using SOAP.
	 * 2. Search cos created in Step-1.
	 * 3. Select delete from right click menu
	 * 4. Verify cos is deleted using SOAP
	 * @throws HarnessException
	 */
	@Test(	description = "Verify delete cos operation -- Search list view/Right click menu",
			groups = { "functional" })
			public void DeleteCos_04() throws HarnessException {

		// Create a new cos in the Admin Console using SOAP
		CosItem cos = new CosItem();
		String cosName=cos.getName();

		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<CreateCosRequest xmlns='urn:zmailAdmin'>"
				+			"<name>" + cosName + "</name>"
				+		"</CreateCosRequest>");

		// Enter the search string to find the account
		app.zPageSearchResults.zAddSearchQuery(cosName);

		// Click search
		app.zPageSearchResults.zToolbarPressButton(Button.B_SEARCH);

		// Right Click on cos to be deleted.
		app.zPageSearchResults.zListItem(Action.A_RIGHTCLICK, cos.getName());

		// Click on Delete button
		DialogForDeleteOperation dialog = (DialogForDeleteOperation) app.zPageSearchResults.zToolbarPressButton(Button.B_TREE_DELETE);

		// Click Yes in Confirmation dialog
		dialog.zClickButton(Button.B_YES);

		// Click Ok on "Delete Items" dialog
		dialog.zClickButton(Button.B_OK);

		// Get the list of displayed accounts
		List<AccountItem> accounts = app.zPageSearchResults.zListGetAccounts();
		ZAssert.assertNotNull(accounts, "Verify the cos list is returned");

		AccountItem found = null;
		for (AccountItem a : accounts) {
			logger.info("Looking for cos "+ cosName + " found: "+ a.getGEmailAddress());
			if ( cosName.equals(a.getGEmailAddress()) ) {
				found = a;
				break;
			}
		}
		ZAssert.assertNull(found, "Verify the cos is deleted successfully");
	}


}
