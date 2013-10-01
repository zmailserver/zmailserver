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
package com.zimbra.qa.selenium.projects.octopus.tests.settings;

import org.testng.annotations.*;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.octopus.core.OctopusCommonTest;
import com.zimbra.qa.selenium.projects.octopus.ui.DialogSettings;
import com.zimbra.qa.selenium.projects.octopus.ui.PageMyFiles;

public class AccountSettings extends OctopusCommonTest {

	private boolean _folderIsCreated = false;
	private String _folderName = null;
	private boolean _fileAttached = false;
	private String _fileId = null;

	public AccountSettings() {
		logger.info("New " + AccountSettings.class.getCanonicalName());

		// test starts at the My Files tab
		super.startingPage = app.zPageMyFiles;
		super.startingAccountPreferences = null;
	}

	@BeforeMethod(groups = { "always" })
	public void testReset() {
		_folderName = null;
		_folderIsCreated = false;
		_fileId = null;
		_fileAttached = false;
	}

	@Test(description = "Open Settings dialog - verify UI layout", groups = { "functional" })
	public void AccountSettings_01() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		String deviceName = "device"
				+ ZimbraSeleniumProperties.getUniqueString();

		// Register device through SOAP
		account.soapSend("<RegisterDeviceRequest xmlns='urn:zimbraMail'>"
				+ "<device name='" + deviceName + "'/>"
				+ "</RegisterDeviceRequest>");

		// Get all devices through SOAP
		account.soapSend("<GetAllDevicesRequest xmlns='urn:zimbraMail'/>");

		// Open Settings dialog
		DialogSettings dlg = (DialogSettings) app.zPageOctopus
				.zToolbarPressPulldown(Button.B_USER_NAME, Button.O_SETTINGS);

		// Verify Device name through SOAP
		ZAssert.assertTrue(
				account.soapMatch("//mail:GetAllDevicesResponse/mail:device",
						"name", deviceName), "Verify Device name");

		// Verify User Name is present in the Settings dialog
		ZAssert.assertTrue(app.zPageOctopus
				.sIsElementPresent(DialogSettings.Locators.zUserName.locator),
				"Verify User Name is present");

		// Verify Change Picture is present in the Settings dialog
		ZAssert.assertTrue(
				app.zPageOctopus
				.sIsElementPresent(DialogSettings.Locators.zChangePictureBtn.locator),
				"Verify Change Picture is present");

		// Verify Quota Usage is present in the Settings dialog
		ZAssert.assertTrue(
				app.zPageOctopus
				.sIsElementPresent(DialogSettings.Locators.zQuotaUsage.locator),
				"Verify Quota Usage is present");

		// Verify Change Password is present in the Settings dialog
		ZAssert.assertTrue(
				app.zPageOctopus
				.sIsElementPresent(DialogSettings.Locators.zChangePasswordBtn.locator),
				"Verify Change Password is present");

		dlg.zClickButton(Button.B_CLOSE);
	}

	@Test(description = "Open Settings dialog - verify account Name", groups = { "smoke" })
	public void AccountSettings_02() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		DialogSettings dlg = (DialogSettings) app.zPageOctopus
				.zToolbarPressPulldown(Button.B_USER_NAME, Button.O_SETTINGS);

		// Verify the account user name matches
		ZAssert.assertTrue(app.zPageOctopus
				.sIsElementPresent(DialogSettings.Locators.zUserName.locator
						+ ":contains(" + account.EmailAddress + ")"),
				"Verify the account user name matches");

		dlg.zClickButton(Button.B_CLOSE);
	}

	@Test(description = "Open Settings dialog - verify Devices field", groups = { "smoke" })
	public void AccountSettings_03() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		String deviceName = "device"
				+ ZimbraSeleniumProperties.getUniqueString();

		// Register device through SOAP
		account.soapSend("<RegisterDeviceRequest xmlns='urn:zimbraMail'>"
				+ "<device name='" + deviceName + "'/>"
				+ "</RegisterDeviceRequest>");

		// Get all devices through SOAP
		account.soapSend("<GetAllDevicesRequest xmlns='urn:zimbraMail'/>");

		DialogSettings dlg = (DialogSettings) app.zPageOctopus
				.zToolbarPressPulldown(Button.B_USER_NAME, Button.O_SETTINGS);

		// Verify Device name in the list
		ZAssert.assertTrue(
				app.zPageOctopus
				.zWaitForElementPresent(DialogSettings.Locators.zDevicesListView.locator
						+ ":contains(" + deviceName + ")", "3000"),
				" Verify Device name in the list");

		dlg.zClickButton(Button.B_CLOSE);
	}

	@Test(description = "Click on device Unlink & Wipe button - verify device is disabled", groups = { "functional" })
	public void AccountSettings_04() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		String deviceName = "device"
				+ ZimbraSeleniumProperties.getUniqueString();

		// Register device through SOAP
		account.soapSend("<RegisterDeviceRequest xmlns='urn:zimbraMail'>"
				+ "<device name='" + deviceName + "'/>"
				+ "</RegisterDeviceRequest>");

		// Get all devices through SOAP
		account.soapSend("<GetAllDevicesRequest xmlns='urn:zimbraMail'/>");

		DialogSettings dlg = (DialogSettings) app.zPageOctopus
				.zToolbarPressPulldown(Button.B_USER_NAME, Button.O_SETTINGS);

		// Verify Device name in the list
		ZAssert.assertTrue(
				app.zPageOctopus
				.zWaitForElementPresent(DialogSettings.Locators.zDevicesListView.locator
						+ ":contains(" + deviceName + ")","3000"),
				" Verify Device name in the list");

		dlg.zListItem(Action.A_LEFTCLICK, Button.B_UNLINK_AND_WIPE, deviceName);

		// Verify Device is disabled
		ZAssert.assertTrue(dlg.zIsDeviceDisabled(deviceName),
				"Verify Device is disabled");

		dlg.zClickButton(Button.B_CLOSE);
	}

	@Test(description = "Clicking settings should open the settings dialog", groups = { "smoke" })
	public void CheckSettingsDialog() throws HarnessException
	{
		// Open Settings dialog
		DialogSettings dlg = (DialogSettings) app.zPageOctopus.zToolbarPressPulldown(Button.B_USER_NAME, Button.O_SETTINGS);

		// Verify Settings label is present in the Settings dialog
		ZAssert.assertTrue(app.zPageOctopus.sIsElementPresent(DialogSettings.Locators.zSettingDialogLabel.locator),
				"Verify Settings dialog is opened.");

		dlg.zClickAt(DialogSettings.Locators.zCloseIcon.locator, "0,0");
	}

	@Test(description = "Clicking x icon should close the settings dialog", groups = { "smoke" })
	public void ClickOnCloseIcon() throws HarnessException
	{
		// Open Settings dialog
		DialogSettings dlg = (DialogSettings) app.zPageOctopus.zToolbarPressPulldown(Button.B_USER_NAME, Button.O_SETTINGS);

		// Verify Settings label is present in the Settings dialog
		ZAssert.assertTrue(app.zPageOctopus.sIsElementPresent(DialogSettings.Locators.zSettingDialogLabel.locator),
				"Verify Settings dialog is opened.");

		dlg.zClickAt(DialogSettings.Locators.zCloseIcon.locator, "0,0");

		// Verify Settings dialog is closed.
		ZAssert.assertTrue(app.zPageOctopus.sIsElementPresent(PageMyFiles.Locators.zMyFilesCurrentMenuLabel.locator),
				"Verify Settings dialog is closed.");
	}

	@AfterMethod(groups = { "always" })
	public void testCleanup() {
		if (_folderIsCreated) {
			try {
				// Delete it from Server
				FolderItem
				.deleteUsingSOAP(app.zGetActiveAccount(), _folderName);
			} catch (Exception e) {
				logger.info("Failed while removing the folder.", e);
			} finally {
				_folderName = null;
				_folderIsCreated = false;
			}
		}
		if (_fileAttached && _fileId != null) {
			try {
				// Delete it from Server
				app.zPageOctopus.deleteItemUsingSOAP(_fileId,
						app.zGetActiveAccount());
			} catch (Exception e) {
				logger.info("Failed while deleting the file", e);
			} finally {
				_fileId = null;
				_fileAttached = false;
			}
		}
		try {
			// Refresh view
			// ZimbraAccount account = app.zGetActiveAccount();
			// FolderItem item =
			// FolderItem.importFromSOAP(account,SystemFolder.Briefcase);
			// account.soapSend("<GetFolderRequest xmlns='urn:zimbraMail'><folder l='1' recursive='0'/>"
			// + "</GetFolderRequest>");
			// account.soapSend("<GetFolderRequest xmlns='urn:zimbraMail' requestId='folders' depth='1' tr='true' view='document'><folder l='"
			// + item.getId() + "'/></GetFolderRequest>");
			// account.soapSend("<GetActivityStreamRequest xmlns='urn:zimbraMail' id='16'/>");
			// app.zGetActiveAccount().accountIsDirty = true;
			// app.zPageOctopus.sRefresh();

			// Empty trash
			app.zPageTrash.emptyTrashUsingSOAP(app.zGetActiveAccount());

			app.zPageOctopus.zLogout();
		} catch (Exception e) {
			logger.info("Failed while emptying Trash", e);
		}
	}
}
