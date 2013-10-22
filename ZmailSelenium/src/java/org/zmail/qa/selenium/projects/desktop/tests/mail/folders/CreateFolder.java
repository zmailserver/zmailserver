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
package com.zimbra.qa.selenium.projects.desktop.tests.mail.folders;

import java.net.MalformedURLException;
import java.net.URL;

import org.testng.annotations.*;

import com.zimbra.qa.selenium.framework.items.DesktopAccountItem;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount.SOAP_DESTINATION_HOST_TYPE;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties.AppType;
import com.zimbra.qa.selenium.projects.desktop.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.desktop.ui.PageMain;
import com.zimbra.qa.selenium.projects.desktop.ui.mail.DialogCreateFolder;

public class CreateFolder extends AjaxCommonTest {

	private boolean _folderIsCreated = false;
	private String _folderName = null;
	private SOAP_DESTINATION_HOST_TYPE _soapDestination = null;
	private String _accountName = null;

	public CreateFolder() {
		logger.info("New " + CreateFolder.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = null;
	}

	@BeforeMethod(alwaysRun = true)
	public void setParameters() {
		_soapDestination = ZimbraSeleniumProperties.getAppType() == AppType.DESKTOP ? SOAP_DESTINATION_HOST_TYPE.CLIENT
				: SOAP_DESTINATION_HOST_TYPE.SERVER;
	}

	@Test(description = "Create a new folder using keyboard shortcuts", groups = { "functional" })
	public void CreateFolder_02() throws HarnessException {
		Shortcut shortcut = Shortcut.S_NEWFOLDER;

		// Set the new folder name
		_folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();
		DialogCreateFolder dialog = (DialogCreateFolder) app.zPageMail
				.zKeyboardShortcut(shortcut);
		ZAssert.assertNotNull(dialog, "Verify the new dialog opened");

		// Fill out the form with the basic details
		// TODO: does a folder in the tree need to be selected?
		dialog.zEnterFolderName(_folderName);
		dialog.zClickButton(Button.B_OK);

	   // Force-sync
      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

      // Make sure the folder was created on the Desktop Server
      FolderItem desktopFolder = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), _folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            app.zGetActiveAccount().EmailAddress);
	  ZAssert.assertNotNull(desktopFolder, "Verify the folder is created on ZD Client");
      ZAssert.assertEquals(desktopFolder.getName(), _folderName,
      "Verify the server and client folder names match");

      // Make sure the folder was created on the ZCS server
      FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(),
		      _folderName);
	  ZAssert.assertNotNull(folder, "Verify the new folder was created");
      ZAssert.assertEquals(folder.getName(), _folderName,
				"Verify the server and client folder names match");
	}

	@Test(description = "Create a new folder using context menu from root folder", groups = { "functional" })
	public void CreateFolder_03() throws HarnessException {
		_folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();
		FolderItem folderItem = FolderItem.importFromSOAP(app
				.zGetActiveAccount(), FolderItem.SystemFolder.UserRoot,
				_soapDestination, app.zGetActiveAccount().EmailAddress);
		DialogCreateFolder createFolderDialog = (DialogCreateFolder) app.zPageMail
				.zListItem(Action.A_RIGHTCLICK, Button.B_TREE_NEWFOLDER,
						folderItem);
		createFolderDialog.zEnterFolderName(_folderName);
		createFolderDialog.zClickButton(Button.B_OK);
		_folderIsCreated = true;

		// Force-sync
		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

		// Make sure the folder was created on the Desktop Server
		FolderItem desktopFolder = FolderItem.importFromSOAP(app
		      .zGetActiveAccount(), _folderName,
		      SOAP_DESTINATION_HOST_TYPE.CLIENT,
		      app.zGetActiveAccount().EmailAddress);
		ZAssert.assertNotNull(desktopFolder, "Verify the folder is created on ZD Client");
		ZAssert.assertEquals(desktopFolder.getName(), _folderName,
		"Verify the server and client folder names match");

		// Make sure the folder was created on the ZCS server
		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), _folderName);
		ZAssert.assertNotNull(folder, "Verify the new form opened");
		ZAssert.assertEquals(folder.getName(), _folderName,
				"Verify the server and client folder names match");
	}

	@Test(description = "Create a new folder using mail app New -> New Folder", groups = { "sanity" })
	public void CreateFolder_04() throws HarnessException {
		// Set the new folder name
	   _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

		// Create a new folder in the inbox
		// using the context menu + New Folder
		DialogCreateFolder dialog = (DialogCreateFolder) app.zPageMail.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_FOLDER);
		ZAssert.assertNotNull(dialog, "Verify the new dialog opened");

		// Fill out the form with the basic details
		// TODO: does a folder in the tree need to be selected?
		dialog.zEnterFolderName(_folderName);
		dialog.zClickButton(Button.B_OK);
		_folderIsCreated = true;

      // Force-sync
      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

      // Make sure the folder was created on the Desktop Server
      FolderItem desktopFolder = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), _folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            app.zGetActiveAccount().EmailAddress);
      ZAssert.assertNotNull(desktopFolder, "Verify the folder is created on ZD Client");
      ZAssert.assertEquals(desktopFolder.getName(), _folderName,
            "Verify the server and client folder names match");

      // Make sure the folder was created on the ZCS server
	  FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(),_folderName);
	  ZAssert.assertNotNull(folder, "Verify the new folder was created");
	  ZAssert.assertEquals(folder.getName(), _folderName,
	        "Verify the server and client folder names match");
	}


	@Test(description = "Create a new folder for RSS/ATOM feeds", groups = { "functional" })
	public void CreateRSSFolder_05() throws HarnessException {
	   Shortcut shortcut = Shortcut.S_NEWFOLDER;

	   // get feeds about latest builds for QA
	   String rssUrl="http://zqa-099.eng.vmware.com:8080/rssLatest";

	   // Set the new folder name
	   _folderName = "folderRSS" + ZimbraSeleniumProperties.getUniqueString();
	   DialogCreateFolder dialog = (DialogCreateFolder) app.zPageMail.zKeyboardShortcut(shortcut);
	   ZAssert.assertNotNull(dialog, "Verify the new dialog opened");

	   // Fill out the form with the basic details
	   // TODO: does a folder in the tree need to be selected?
	   dialog.zEnterFolderName(_folderName);
	   dialog.zClickSubscribeFeed(true);
	   try {
	      dialog.zEnterFeedURL(new URL(rssUrl));
	   } catch (MalformedURLException e) {
	      throw new HarnessException("Unable to build URL", e);
	   }
	   dialog.zClickButton(Button.B_OK);

	   // Force-sync
	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
	   app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

	   //Make sure the folder was created on the Desktop Server
	   FolderItem desktopFolder = FolderItem.importFromSOAP(app
	         .zGetActiveAccount(), _folderName,
	         SOAP_DESTINATION_HOST_TYPE.CLIENT,
	         app.zGetActiveAccount().EmailAddress);
	   ZAssert.assertNotNull(desktopFolder, "Verify the new RSS folder got created");

	   //Make sure the folder was created on the Desktop Server from UI perspective
	   String folderLocator = PageMain.Locators.rssFolders.replace("<FOLDER_NAME>", _folderName);
	   ZAssert.assertEquals(app.zPageMail.sIsElementPresent(folderLocator), true,
	         "Verify RSS folder is created with the correct name");

	   // Make sure the folder was created on the ZCS server
	   FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), _folderName);
	   ZAssert.assertNotNull(folder, "Verify the new RSS folder was created");
	   ZAssert.assertEquals(folder.getName(), _folderName, "Verify the server and client RSS folder names match");
	   app.zGetActiveAccount().soapSend(
	         "<GetFolderRequest xmlns='urn:zimbraMail'>"
	         +		"<folder id='" + folder.getId() + "'/>"
				+	"</GetFolderRequest>");
	   String url = app.zGetActiveAccount().soapSelectValue("//mail:folder[@name='" + folder.getName() + "']", "url");

	   // Only RSS folder has url attribute , so if its equal , it asserts the RSS folder creation
	   ZAssert.assertEquals(url, rssUrl, "Verify the url of the rss folder correct");
	}

	private void _nonZimbraAccountSetup(ZimbraAccount account) throws HarnessException {
	   account.authenticateToMailClientHost();
	   app.zPageLogin.zLogin(account);
      super.startingPage.zNavigateTo();

      app.zTreeMail.zExpandAll();
      app.zPageMain.zWaitForDesktopLoadingSpinner(5000);
	}


	@Test(description = "Create Inbox's subfolder for IMAP Zimbra Account through ZD", groups = { "smoke" })
	public void CreateInboxSubfolderImapZimbraAccountThroughZD()
	throws HarnessException {
	   app.zPageLogin.zNavigateTo();
	   app.zPageLogin.zRemoveAccount();
	   ZimbraAccount zcsAccount = ZimbraAccount.AccountZDC();

	   DesktopAccountItem accountItem = app.zPageAddNewAccount.zAddZimbraImapAccountThruUI(
	         AjaxCommonTest.defaultAccountName,
	         ZimbraAccount.AccountZDC().EmailAddress,
            ZimbraAccount.AccountZDC().Password,
            ZimbraSeleniumProperties.getStringProperty("server.host", "localhost"),
            true,
            "465");

	   ZimbraAccount account = new ZimbraAccount(accountItem.emailAddress,
	         accountItem.password);
	   _nonZimbraAccountSetup(account);

	   _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

      FolderItem folderItem = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), FolderItem.SystemFolder.Inbox,
            _soapDestination, app.zGetActiveAccount().EmailAddress);

      DialogCreateFolder createFolderDialog = (DialogCreateFolder)
            app.zPageMail.zListItem(
                  Action.A_RIGHTCLICK,
                  Button.B_TREE_NEWFOLDER,
                  folderItem);

      createFolderDialog.zEnterFolderName(_folderName);
      createFolderDialog.zClickButton(Button.B_OK);

      _folderIsCreated = true;

      // Force-sync
      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

      // Make sure the folder was created on the Desktop Server
      FolderItem desktopFolder = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), _folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            app.zGetActiveAccount().EmailAddress);

      ZAssert.assertNotNull(desktopFolder, "Verify the folder is created on ZD Client");
      ZAssert.assertEquals(desktopFolder.getName(), _folderName,
      "Verify the server and client folder names match");

      // Make sure the folder was created on the ZCS server
      FolderItem folder = FolderItem.importFromSOAP(zcsAccount,
            _folderName);

      // The reason why not using folderItem variable as it has been created above is
      // because the ID in ZD might be different than ID in ZCS, thus capturing different object
      // specifally for ZCS server (for example ID in 8.x is different than ID in 7.x and before)
      FolderItem zcsParentFolderItem = FolderItem.importFromSOAP(zcsAccount,
            FolderItem.SystemFolder.Inbox);

      ZAssert.assertNotNull(folder, "Verify the folder is created on ZCS server");
      ZAssert.assertEquals(folder.getName(), _folderName,
            "Verify the server and client folder names match");
      ZAssert.assertEquals(folder.getParentId(), zcsParentFolderItem.getId(),
            "Verify the parent folder ID on ZCS server matches");
      ZAssert.assertEquals(folder.getParentFolder(zcsAccount,
            SOAP_DESTINATION_HOST_TYPE.SERVER, null).getName(),
            zcsParentFolderItem.getName(),
            "Verify the parent folder on ZCS server matches");
	}

	@Test(description = "Create Inbox's subfolder for POP Zimbra Account through ZD", groups = { "smoke" })
	public void CreateInboxSubfolderPopZimbraAccountThroughZD()
	throws HarnessException {
	   app.zPageLogin.zNavigateTo();
	   app.zPageLogin.zRemoveAccount();
	   ZimbraAccount zcsAccount = ZimbraAccount.AccountZDC();

	   DesktopAccountItem accountItem = app.zPageAddNewAccount.zAddZimbraPopAccountThruUI(
	         AjaxCommonTest.defaultAccountName,
	         ZimbraAccount.AccountZDC().EmailAddress,
	         ZimbraAccount.AccountZDC().Password,
	         ZimbraSeleniumProperties.getStringProperty("server.host", "localhost"),
	         true,
	         "465");

	   ZimbraAccount account = new ZimbraAccount(accountItem.emailAddress,
	         accountItem.password);
	   _nonZimbraAccountSetup(account);

	   _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

	   FolderItem folderItem = FolderItem.importFromSOAP(app
	         .zGetActiveAccount(), FolderItem.SystemFolder.Inbox,
	         _soapDestination, app.zGetActiveAccount().EmailAddress);

	   DialogCreateFolder createFolderDialog = (DialogCreateFolder)
	         app.zPageMail.zListItem(
	                  Action.A_RIGHTCLICK,
	                  Button.B_TREE_NEWFOLDER,
	                  folderItem);

	   createFolderDialog.zEnterFolderName(_folderName);
	   createFolderDialog.zClickButton(Button.B_OK);

	   _folderIsCreated = true;

	   // Force-sync
	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
	   app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

	   // Make sure the folder was created on the Desktop Server
	   FolderItem desktopFolder = FolderItem.importFromSOAP(app
	         .zGetActiveAccount(), _folderName,
	         SOAP_DESTINATION_HOST_TYPE.CLIENT,
	         app.zGetActiveAccount().EmailAddress);

	   ZAssert.assertNotNull(desktopFolder, "Verify the folder is created on ZD Client");
	   ZAssert.assertEquals(desktopFolder.getName(), _folderName,
	         "Verify the server and client folder names match");

	   // Make sure the folder was created on the ZCS server
	   FolderItem folder = null;

	   try {
	      folder = FolderItem.importFromSOAP(zcsAccount,
	            _folderName);
	   } catch (HarnessException e) {
	      // This is expected because the verification is to get no folder
	      // from import SOAP
	   }

	   ZAssert.assertNull(folder, "Verify the folder in ZCS server is not created");
	}

	@Test(description = "Create mail folder for IMAP Zimbra Account through ZD", groups = { "smoke" })
	public void CreateMailFolderImapZimbraAccountThroughZD()
	throws HarnessException {
	   app.zPageLogin.zNavigateTo();
	   app.zPageLogin.zRemoveAccount();
	   ZimbraAccount zcsAccount = ZimbraAccount.AccountZDC();

	   DesktopAccountItem accountItem = app.zPageAddNewAccount.zAddZimbraImapAccountThruUI(
	         AjaxCommonTest.defaultAccountName,
	         ZimbraAccount.AccountZDC().EmailAddress,
	         ZimbraAccount.AccountZDC().Password,
	         ZimbraSeleniumProperties.getStringProperty("server.host", "localhost"),
	         true,
	         "465");

	   ZimbraAccount account = new ZimbraAccount(accountItem.emailAddress,
	         accountItem.password);
	   _nonZimbraAccountSetup(account);

	   _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

	   FolderItem folderItem = FolderItem.importFromSOAP(app
	         .zGetActiveAccount(), FolderItem.SystemFolder.UserRoot,
	         _soapDestination, app.zGetActiveAccount().EmailAddress);

	   DialogCreateFolder createFolderDialog = (DialogCreateFolder)
	         app.zPageMail.zListItem(
	               Action.A_RIGHTCLICK,
	               Button.B_TREE_NEWFOLDER,
	               folderItem);

	   createFolderDialog.zEnterFolderName(_folderName);
	   createFolderDialog.zClickButton(Button.B_OK);

	   _folderIsCreated = true;

	   // Force-sync
	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
	   app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

	   // Make sure the folder was created on the Desktop Server
	   FolderItem desktopFolder = FolderItem.importFromSOAP(app
	         .zGetActiveAccount(), _folderName,
	         SOAP_DESTINATION_HOST_TYPE.CLIENT,
	         app.zGetActiveAccount().EmailAddress);

	   ZAssert.assertNotNull(desktopFolder, "Verify the folder is created on ZD Client");
	   ZAssert.assertEquals(desktopFolder.getName(), _folderName,
	         "Verify the server and client folder names match");

	   // Make sure the folder was created on the ZCS server
	   FolderItem folder = FolderItem.importFromSOAP(zcsAccount,
	         _folderName);

	   // The reason why not using folderItem variable as it has been created above is
	   // because the ID in ZD might be different than ID in ZCS, thus capturing different object
	   // specifally for ZCS server (for example ID in 8.x is different than ID in 7.x and before)
	   FolderItem zcsParentFolderItem = FolderItem.importFromSOAP(zcsAccount,
            FolderItem.SystemFolder.UserRoot);

	   ZAssert.assertNotNull(folder, "Verify the new form opened");
	   ZAssert.assertEquals(folder.getName(), _folderName,
	         "Verify the server and client folder names match");
	   ZAssert.assertEquals(folder.getParentId(), zcsParentFolderItem.getId(),
	         "Verify the parent folder ID on ZCS server matches");
	   ZAssert.assertEquals(folder.getParentFolder(zcsAccount,
	         SOAP_DESTINATION_HOST_TYPE.SERVER, null).getName(),
	         zcsParentFolderItem.getName(),
            "Verify the parent folder on ZCS server matches");
	}

	@Test(description = "Create mail folder for POP Zimbra Account through ZD", groups = { "smoke" })
	public void CreateMailFolderPopZimbraAccountThroughZD()
	throws HarnessException {
	   app.zPageLogin.zNavigateTo();
	   app.zPageLogin.zRemoveAccount();
	   ZimbraAccount zcsAccount = ZimbraAccount.AccountZDC();

	   DesktopAccountItem accountItem = app.zPageAddNewAccount.zAddZimbraPopAccountThruUI(
	         AjaxCommonTest.defaultAccountName,
	         ZimbraAccount.AccountZDC().EmailAddress,
	         ZimbraAccount.AccountZDC().Password,
	         ZimbraSeleniumProperties.getStringProperty("server.host", "localhost"),
	         true,
	         "465");

	   ZimbraAccount account = new ZimbraAccount(accountItem.emailAddress,
	         accountItem.password);
	   _nonZimbraAccountSetup(account);

	   _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

	   FolderItem folderItem = FolderItem.importFromSOAP(app
	         .zGetActiveAccount(), FolderItem.SystemFolder.UserRoot,
	         _soapDestination, app.zGetActiveAccount().EmailAddress);

	   DialogCreateFolder createFolderDialog = (DialogCreateFolder)
	         app.zPageMail.zListItem(
	               Action.A_RIGHTCLICK,
	               Button.B_TREE_NEWFOLDER,
	               folderItem);

	   createFolderDialog.zEnterFolderName(_folderName);
	   createFolderDialog.zClickButton(Button.B_OK);

	   _folderIsCreated = true;

	   // Force-sync
	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
	   app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

	   // Make sure the folder was created on the Desktop Server
	   FolderItem desktopFolder = FolderItem.importFromSOAP(app
	         .zGetActiveAccount(), _folderName,
	         SOAP_DESTINATION_HOST_TYPE.CLIENT,
	         app.zGetActiveAccount().EmailAddress);

	   ZAssert.assertNotNull(desktopFolder, "Verify the folder is created on ZD Client");
	   ZAssert.assertEquals(desktopFolder.getName(), _folderName,
	         "Verify the server and client folder names match");

	   // Make sure the folder was created on the ZCS server
	   FolderItem folder = null;

	   try {
	      folder = FolderItem.importFromSOAP(zcsAccount,
	            _folderName);
	   } catch (HarnessException e) {
	      // This is expected because the verification is to get no folder
	      // from import SOAP
	   }

	   ZAssert.assertNull(folder, "Verify the folder in ZCS server is not created");
	}

	@Test(description = "Create Inbox's subfolder for IMAP Zimbra Account through ZCS", groups = { "smoke" })
	public void CreateInboxSubfolderImapZimbraAccountThroughZCS()
	throws HarnessException {
	   app.zPageLogin.zNavigateTo();
	   app.zPageLogin.zRemoveAccount();
	   ZimbraAccount zcsAccount = ZimbraAccount.AccountZDC();

	   DesktopAccountItem accountItem = app.zPageAddNewAccount.zAddZimbraImapAccountThruUI(
	         AjaxCommonTest.defaultAccountName,
	         ZimbraAccount.AccountZDC().EmailAddress,
	         ZimbraAccount.AccountZDC().Password,
	         ZimbraSeleniumProperties.getStringProperty("server.host", "localhost"),
	         true,
	         "465");

	   ZimbraAccount account = new ZimbraAccount(accountItem.emailAddress,
	         accountItem.password);
	   _nonZimbraAccountSetup(account);

	   _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

	   FolderItem inbox = FolderItem.importFromSOAP(zcsAccount,
	         SystemFolder.Inbox);
	   FolderItem inboxZD = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), FolderItem.SystemFolder.Inbox,
            _soapDestination, app.zGetActiveAccount().EmailAddress);

	   zcsAccount.soapSend(
            "<CreateFolderRequest xmlns='urn:zimbraMail'>" +
            "<folder name='"+ _folderName + "' l='"+ inbox.getId() + "'/>" +
            "</CreateFolderRequest>");

	   // Make sure the folder was created on the ZCS server
	   FolderItem folder = FolderItem.importFromSOAP(zcsAccount,
	         _folderName);

	   FolderItem parentFolder = folder.getParentFolder(zcsAccount,
	         SOAP_DESTINATION_HOST_TYPE.SERVER,
	         null);

	   ZAssert.assertNotNull(folder, "Verify the folder is created on ZCS server");
	   ZAssert.assertEquals(parentFolder.getName(), inbox.getName(),
	         "Verify parent folder's name on ZCS server.");
	   ZAssert.assertEquals(parentFolder.getId(), inbox.getId(),
            "Verify parent folder's ID on ZCS server.");
	   _folderIsCreated = true;

	   // Force-sync
	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
	   app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

	   // Make sure the folder was created on the Desktop Server
	   FolderItem desktopFolder = FolderItem.importFromSOAP(app
	         .zGetActiveAccount(), _folderName,
	         SOAP_DESTINATION_HOST_TYPE.CLIENT,
	         app.zGetActiveAccount().EmailAddress);
	   FolderItem desktopFolderParent = desktopFolder.getParentFolder(app.zGetActiveAccount(),
            _soapDestination, app.zGetActiveAccount().EmailAddress);

	   ZAssert.assertNotNull(desktopFolder, "Verify the folder is created on ZD Client");
	   ZAssert.assertEquals(desktopFolder.getName(), _folderName,
	         "Verify the server and client folder names match");
	   ZAssert.assertEquals(desktopFolderParent.getName(),
	         inboxZD.getName(), "Verify the parent folder on ZD server matches");
	}

	@Test(description = "Create Inbox's subfolder for POP Zimbra Account through ZCS", groups = { "smoke" })
   public void CreateInboxSubfolderPopZimbraAccountThroughZCS()
   throws HarnessException {
      app.zPageLogin.zNavigateTo();
      app.zPageLogin.zRemoveAccount();
      ZimbraAccount zcsAccount = ZimbraAccount.AccountZDC();

      DesktopAccountItem accountItem = app.zPageAddNewAccount.zAddZimbraPopAccountThruUI(
            AjaxCommonTest.defaultAccountName,
            ZimbraAccount.AccountZDC().EmailAddress,
            ZimbraAccount.AccountZDC().Password,
            ZimbraSeleniumProperties.getStringProperty("server.host", "localhost"),
            true,
            "465");

      ZimbraAccount account = new ZimbraAccount(accountItem.emailAddress,
            accountItem.password);
      _nonZimbraAccountSetup(account);

      _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

      FolderItem inbox = FolderItem.importFromSOAP(zcsAccount,
            SystemFolder.Inbox);

      zcsAccount.soapSend(
            "<CreateFolderRequest xmlns='urn:zimbraMail'>" +
            "<folder name='"+ _folderName + "' l='"+ inbox.getId() + "'/>" +
            "</CreateFolderRequest>");

      // Make sure the folder was created on the ZCS server
      FolderItem folder = FolderItem.importFromSOAP(zcsAccount,
            _folderName);

      FolderItem parentFolder = folder.getParentFolder(zcsAccount,
            SOAP_DESTINATION_HOST_TYPE.SERVER,
            null);

      ZAssert.assertNotNull(folder, "Verify the folder is created on ZCS server");
      ZAssert.assertEquals(parentFolder.getName(), inbox.getName(),
            "Verify parent folder's name on ZCS server.");
      ZAssert.assertEquals(parentFolder.getId(), inbox.getId(),
            "Verify parent folder's ID on ZCS server.");
      _folderIsCreated = true;

      // Force-sync
      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

      // Make sure the folder is not created on the ZD server
      FolderItem desktopFolder = null;

      try {
         // Make sure the folder was not created on the Desktop Server
         desktopFolder = FolderItem.importFromSOAP(app
               .zGetActiveAccount(), _folderName,
               SOAP_DESTINATION_HOST_TYPE.CLIENT,
               app.zGetActiveAccount().EmailAddress);
      } catch (HarnessException e) {
         // This is expected because the verification is to get no folder
         // from import SOAP
      }

      ZAssert.assertNull(desktopFolder, "Verify the folder in ZD server is not created");
   }

	@Test(description = "Create mail folder for IMAP Zimbra Account through ZCS", groups = { "smoke" })
	public void CreateMailFolderImapZimbraAccountThroughZCS()
	throws HarnessException {
	   app.zPageLogin.zNavigateTo();
	   app.zPageLogin.zRemoveAccount();
	   ZimbraAccount zcsAccount = ZimbraAccount.AccountZDC();

	   DesktopAccountItem accountItem = app.zPageAddNewAccount.zAddZimbraImapAccountThruUI(
	         AjaxCommonTest.defaultAccountName,
	         ZimbraAccount.AccountZDC().EmailAddress,
	         ZimbraAccount.AccountZDC().Password,
	         ZimbraSeleniumProperties.getStringProperty("server.host", "localhost"),
	         true,
	         "465");

	   ZimbraAccount account = new ZimbraAccount(accountItem.emailAddress,
	         accountItem.password);
	   _nonZimbraAccountSetup(account);

	   _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

	   FolderItem userRoot = FolderItem.importFromSOAP(zcsAccount,
	         SystemFolder.UserRoot);
	   FolderItem userRootZD = FolderItem.importFromSOAP(app
	         .zGetActiveAccount(), FolderItem.SystemFolder.UserRoot,
	         _soapDestination, app.zGetActiveAccount().EmailAddress);

	   zcsAccount.soapSend(
	         "<CreateFolderRequest xmlns='urn:zimbraMail'>" +
	         "<folder name='"+ _folderName + "' l='"+ userRoot.getId() + "'/>" +
	         "</CreateFolderRequest>");

	   // Make sure the folder was created on the ZCS server
	   FolderItem folder = FolderItem.importFromSOAP(zcsAccount,
	         _folderName);

	   FolderItem parentFolder = folder.getParentFolder(zcsAccount,
            SOAP_DESTINATION_HOST_TYPE.SERVER,
            null);

	   ZAssert.assertNotNull(folder, "Verify the folder is created on ZCS server");
	   ZAssert.assertEquals(parentFolder.getName(), userRoot.getName(),
            "Verify parent folder's name on ZCS server.");
	   ZAssert.assertEquals(parentFolder.getId(), userRoot.getId(),
            "Verify parent folder's ID on ZCS server.");
	   _folderIsCreated = true;

	   // Force-sync
	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
	   app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

	   // Make sure the folder was created on the Desktop Server
	   FolderItem desktopFolder = FolderItem.importFromSOAP(app
	         .zGetActiveAccount(), _folderName,
	         SOAP_DESTINATION_HOST_TYPE.CLIENT,
	         app.zGetActiveAccount().EmailAddress);
	   FolderItem desktopFolderParent = desktopFolder.getParentFolder(app.zGetActiveAccount(),
	         _soapDestination, app.zGetActiveAccount().EmailAddress);

	   ZAssert.assertNotNull(desktopFolder, "Verify the folder is created on ZD Client");
	   ZAssert.assertEquals(desktopFolder.getName(), _folderName,
	         "Verify the server and client folder names match");
	         ZAssert.assertEquals(desktopFolderParent.getName(),
            userRootZD.getName(), "Verify the parent folder on ZD server matches");
	}

   @Test(description = "Create mail folder for POP Zimbra Account through ZCS", groups = { "smoke" })
   public void CreateMailFolderPopZimbraAccountThroughZCS()
   throws HarnessException {
      app.zPageLogin.zNavigateTo();
      app.zPageLogin.zRemoveAccount();
      ZimbraAccount zcsAccount = ZimbraAccount.AccountZDC();

      DesktopAccountItem accountItem = app.zPageAddNewAccount.zAddZimbraPopAccountThruUI(
            AjaxCommonTest.defaultAccountName,
            ZimbraAccount.AccountZDC().EmailAddress,
            ZimbraAccount.AccountZDC().Password,
            ZimbraSeleniumProperties.getStringProperty("server.host", "localhost"),
            true,
            "465");

      ZimbraAccount account = new ZimbraAccount(accountItem.emailAddress,
            accountItem.password);
      _nonZimbraAccountSetup(account);

      _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

      FolderItem userRoot = FolderItem.importFromSOAP(zcsAccount,
            SystemFolder.UserRoot);

      zcsAccount.soapSend(
            "<CreateFolderRequest xmlns='urn:zimbraMail'>" +
            "<folder name='"+ _folderName + "' l='"+ userRoot.getId() + "'/>" +
            "</CreateFolderRequest>");

      // Make sure the folder was created on the ZCS server
      FolderItem folder = FolderItem.importFromSOAP(zcsAccount,
            _folderName);

      FolderItem parentFolder = folder.getParentFolder(zcsAccount,
            SOAP_DESTINATION_HOST_TYPE.SERVER,
            null);

      ZAssert.assertNotNull(folder, "Verify the folder is created on ZCS server");
      ZAssert.assertEquals(parentFolder.getName(), userRoot.getName(),
            "Verify parent folder's name on ZCS server.");
      ZAssert.assertEquals(parentFolder.getId(), userRoot.getId(),
            "Verify parent folder's ID on ZCS server.");
      _folderIsCreated = true;

      // Force-sync
      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

      // Make sure the folder is not created on the Desktop Server
      FolderItem desktopFolder = null;

      try {
         desktopFolder = FolderItem.importFromSOAP(app
               .zGetActiveAccount(), _folderName,
               SOAP_DESTINATION_HOST_TYPE.CLIENT,
               app.zGetActiveAccount().EmailAddress);
      } catch (HarnessException e) {
         // This is expected because the verification is to get no folder
         // from import SOAP
      }

      ZAssert.assertNull(desktopFolder, "Verify the folder in ZD server is not created");
   }

   @Test(description = "Create a new local folder using context menu", groups = { "smoke" })
   public void createLocalMailFolderThroughContextMenu() throws HarnessException {
      _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

      FolderItem folderItem = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            SystemFolder.UserRoot,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);

      DialogCreateFolder createFolderDialog = (DialogCreateFolder) app.zPageMail
            .zListItem(Action.A_RIGHTCLICK, Button.B_TREE_NEWFOLDER,
                  folderItem);
      createFolderDialog.zEnterFolderName(_folderName);
      createFolderDialog.zClickButton(Button.B_OK);
      _folderIsCreated = true;
      _accountName = ZimbraAccount.clientAccountName;

      // Make sure the folder was created on the Desktop Server
      FolderItem desktopFolder = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), _folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);
      FolderItem desktopFolderParent = desktopFolder.getParentFolder(app.zGetActiveAccount(),
            _soapDestination, ZimbraAccount.clientAccountName);

      ZAssert.assertNotNull(desktopFolder, "Verify the folder is created on ZD Client's Local Folders");
      ZAssert.assertEquals(desktopFolder.getName(), _folderName,
            "Verify the server and client folder names match");
      ZAssert.assertEquals(desktopFolderParent.getName(),
            folderItem.getName(), "Verify the parent folder on ZD server matches");

   }

   @Test(description = "Create a new local subfolder using context menu", groups = { "smoke" })
   public void createLocalMailSubfolderThroughContextMenu() throws HarnessException {
      _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

      String subfolderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

      // Create a folder under local to create the subfolder
      //
      FolderItem rootLocalFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            SystemFolder.UserRoot, SOAP_DESTINATION_HOST_TYPE.CLIENT, ZimbraAccount.clientAccountName);

      app.zGetActiveAccount().soapSend(
            "<CreateFolderRequest xmlns='urn:zimbraMail'>" +
            "<folder name='" + _folderName +"' l='"+ rootLocalFolder.getId() +"'/>" +
            "</CreateFolderRequest>",
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);

      // Make sure the folder was created on the Desktop Server
      FolderItem parentFolderItem = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), _folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);

      _folderIsCreated = true;
      _accountName = ZimbraAccount.clientAccountName;

      DialogCreateFolder createFolderDialog = (DialogCreateFolder) app.zPageMail
            .zListItem(Action.A_RIGHTCLICK, Button.B_TREE_NEWFOLDER,
            parentFolderItem);
      createFolderDialog.zEnterFolderName(subfolderName);
      createFolderDialog.zClickButton(Button.B_OK);

      // Make sure the folder was created on the Desktop Server
      FolderItem desktopFolder = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), subfolderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);
      FolderItem desktopFolderParent = desktopFolder.getParentFolder(app.zGetActiveAccount(),
            _soapDestination, ZimbraAccount.clientAccountName);

      ZAssert.assertNotNull(desktopFolder, "Verify the subfolder is created on ZD Client's Local Folders");
      ZAssert.assertEquals(desktopFolder.getName(), subfolderName,
            "Verify the back-end sub folder names match");
      ZAssert.assertEquals(desktopFolderParent.getName(),
            parentFolderItem.getName(), "Verify the parent folder on ZD server matches");

   }

   @Test(description = "Create a new local folder through SOAP to ZD Client", groups = { "smoke" })
   public void createLocalMailFolderThroughSOAP() throws HarnessException {
      _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

      FolderItem folderItem = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            SystemFolder.UserRoot,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);

      app.zGetActiveAccount().soapSend(
            "<CreateFolderRequest xmlns='urn:zimbraMail'>" +
            "<folder name='" + _folderName +"' l='"+ folderItem.getId() +"'/>" +
            "</CreateFolderRequest>",
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);

      _folderIsCreated = true;
      _accountName = ZimbraAccount.clientAccountName;

      // Make sure the folder was created on the Desktop Server
      FolderItem desktopFolder = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), _folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);
      FolderItem desktopFolderParent = desktopFolder.getParentFolder(app.zGetActiveAccount(),
            _soapDestination, ZimbraAccount.clientAccountName);

      ZAssert.assertNotNull(desktopFolder, "Verify the folder is created on ZD Client's Local Folders");
      ZAssert.assertEquals(desktopFolder.getName(), _folderName,
            "Verify the server and client folder names match");
      ZAssert.assertEquals(desktopFolderParent.getName(),
            folderItem.getName(), "Verify the parent folder on ZD server matches");

   }

   @Test(description = "Create a new local subfolder using SOAP request to ZD client", groups = { "smoke" })
   public void createLocalMailSubfolderThroughSOAP() throws HarnessException {
      _folderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

      String subfolderName = "folder" + ZimbraSeleniumProperties.getUniqueString();

      // Create a folder under local to create the subfolder
      //
      FolderItem rootLocalFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            SystemFolder.UserRoot, SOAP_DESTINATION_HOST_TYPE.CLIENT, ZimbraAccount.clientAccountName);

      app.zGetActiveAccount().soapSend(
            "<CreateFolderRequest xmlns='urn:zimbraMail'>" +
            "<folder name='" + _folderName +"' l='"+ rootLocalFolder.getId() +"'/>" +
            "</CreateFolderRequest>",
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);

      // Make sure the folder was created on the Desktop Server
      FolderItem parentFolderItem = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), _folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);

      _folderIsCreated = true;
      _accountName = ZimbraAccount.clientAccountName;

      app.zGetActiveAccount().soapSend(
            "<CreateFolderRequest xmlns='urn:zimbraMail'>" +
            "<folder name='" + subfolderName +"' l='"+ parentFolderItem.getId() +"'/>" +
            "</CreateFolderRequest>",
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);

      // Make sure the folder was created on the Desktop Server
      FolderItem desktopFolder = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), subfolderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZimbraAccount.clientAccountName);
      FolderItem desktopFolderParent = desktopFolder.getParentFolder(app.zGetActiveAccount(),
            _soapDestination, ZimbraAccount.clientAccountName);

      ZAssert.assertNotNull(desktopFolder, "Verify the subfolder is created on ZD Client's Local Folders");
      ZAssert.assertEquals(desktopFolder.getName(), subfolderName,
            "Verify the back-end sub folder names match");
      ZAssert.assertEquals(desktopFolderParent.getName(),
            parentFolderItem.getName(), "Verify the parent folder on ZD server matches");

   }

   @AfterMethod(groups = { "always" })
	public void createFolderTestCleanup() throws HarnessException {
		if (_folderIsCreated) {
			try {

			   app.zPageMail.zNavigateTo();

			   if (_accountName == null) {
			      FolderItem.deleteUsingSOAP(app.zGetActiveAccount(),
			            _folderName, _soapDestination, defaultAccountName);
			   } else {
			      FolderItem.deleteUsingSOAP(app.zGetActiveAccount(),
                     _folderName, _soapDestination, _accountName);
			   }

			} catch (Exception e) {

			   logger.warn("Failed while removing the folder.", e);

			} finally {

			   // Logout to reflect the changes
		      app.zPageLogin.zNavigateTo();

		      _folderName = null;
				_folderIsCreated = false;
				_accountName = null;

			}
		}
	}

}
