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
package org.zmail.qa.selenium.projects.desktop.tests.mail.folders;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;


public class DeleteFolder extends AjaxCommonTest {

	public DeleteFolder() {
		logger.info("New "+ DeleteFolder.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = null;
		
	}
	
	@Test(	description = "Delete a folder - Right click, Delete",
			groups = { "smoke" })
	public void DeleteFolder_01() throws HarnessException {
		
		FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
		ZAssert.assertNotNull(inbox, "Verify the inbox is available");
		
		FolderItem trash = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Trash);
		FolderItem trashZD = FolderItem.importFromSOAP(app.zGetActiveAccount(),
		      SystemFolder.Trash,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            app.zGetActiveAccount().EmailAddress);
		ZAssert.assertNotNull(trash, "Verify the trash is available");
		
		// Create the subfolder
		String name = "folder" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ name +"' l='"+ inbox.getId() +"'/>" +
                "</CreateFolderRequest>");

		// Click on Get Mail to refresh the folder list
      app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

      FolderItem subfolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),
		      name);
		FolderItem desktopSubfolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),
		      name,
		      SOAP_DESTINATION_HOST_TYPE.CLIENT,
		      app.zGetActiveAccount().EmailAddress);
		
		ZAssert.assertNotNull(subfolder, "Verify the subfolder is available in ZCS server");
		ZAssert.assertNotNull(desktopSubfolder, "Verify the subfolder is available in ZD server");

		// Delete the folder using context menu
		app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_DELETE, desktopSubfolder);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

		// Verify the folder is now in the trash
		FolderItem deletedSubfolderZCS = FolderItem.importFromSOAP(app.zGetActiveAccount(),
		      name);
		FolderItem deletedSubfolderZD = FolderItem.importFromSOAP(app.zGetActiveAccount(),
		      name,
		      SOAP_DESTINATION_HOST_TYPE.CLIENT,
		      app.zGetActiveAccount().EmailAddress);
		ZAssert.assertNotNull(deletedSubfolderZCS,
		      "Verify the subfolder is still available in ZCS Server");
		ZAssert.assertEquals(trash.getId(),
		      deletedSubfolderZCS.getParentId(),
		      "Verify the subfolder's parent is now the trash folder ID in ZCS server");

		ZAssert.assertNotNull(deletedSubfolderZD,
		      "Verify the subfolder is still available in ZD client");
		ZAssert.assertEquals(trashZD.getId(),
		      deletedSubfolderZD.getParentId(),
            "Verify the subfolder's parent is now the trash folder ID in ZD client");
		
	}	

	@Test(   description = "Deleting the folder where the Trash already has a folder with the same name",
	      groups = { "functional" })
   public void DeleteFolder_02() throws HarnessException {

	   FolderItem inbox = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Inbox);
	   ZAssert.assertNotNull(inbox, "Verify the inbox is available");

	   FolderItem trash = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Trash);
	   FolderItem trashZD = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            SystemFolder.Trash,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            app.zGetActiveAccount().EmailAddress);
	   ZAssert.assertNotNull(trash, "Verify the trash is available");

	   // Create the subfolder
	   String name = "folder" + ZmailSeleniumProperties.getUniqueString();

	   app.zGetActiveAccount().soapSend(
	         "<CreateFolderRequest xmlns='urn:zmailMail'>" +
	         "<folder name='"+ name +"' l='"+ inbox.getId() +"'/>" +
	         "</CreateFolderRequest>");

	   // Click on Get Mail to refresh the folder list
      app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

	   FolderItem subfolder1 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name);
	   FolderItem subfolder1ZD = FolderItem.importFromSOAP(app.zGetActiveAccount(),
	         name,
	         SOAP_DESTINATION_HOST_TYPE.CLIENT,
	         app.zGetActiveAccount().EmailAddress);
	   ZAssert.assertNotNull(subfolder1, "Verify the subfolder is available");

	   // Delete the folder using context menu
	   app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_DELETE, subfolder1ZD);

	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

      subfolder1 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name);
      subfolder1ZD = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            name,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            app.zGetActiveAccount().EmailAddress);

	   // Create the same subfolder again under the same parent
	   app.zGetActiveAccount().soapSend(
	         "<CreateFolderRequest xmlns='urn:zmailMail'>" +
	         "<folder name='"+ name +"' l='"+ inbox.getId() +"'/>" +
	         "</CreateFolderRequest>");

	   // Click on Get Mail to refresh the folder list
      app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

      FolderItem subfolder2 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name);
	   FolderItem subfolder2ZD = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            name,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            app.zGetActiveAccount().EmailAddress);
	   ZAssert.assertNotNull(subfolder2, "Verify the subfolder is available");

	   // Delete the folder using context menu
	   app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_DELETE, subfolder2ZD);

	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMain.zWaitForDesktopLoadingSpinner(5000);

      subfolder2 = FolderItem.importFromSOAP(app.zGetActiveAccount(), name + "_");
      subfolder2ZD = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            name + "_",
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            app.zGetActiveAccount().EmailAddress);

	   // Verify the folder is now in the trash
	   ZAssert.assertNotNull(subfolder1, "Verify the subfolder1 is still available in ZCS");
	   ZAssert.assertNotNull(subfolder2, "Verify the subfolder2 is still available in ZCS");

	   ZAssert.assertEquals(trash.getId(), subfolder1.getParentId(), "Verify the subfolder1's parent is now the trash folder ID in ZCS");
	   ZAssert.assertEquals(trash.getId(), subfolder2.getParentId(), "Verify the subfolder2's parent is now the trash folder ID in ZCS");
	   ZAssert.assertEquals(name, subfolder1.getName(), "Verify the subfolder1's name in ZCS");
	   ZAssert.assertEquals(name + "_", subfolder2.getName(), "Verify the subfolder2's name in ZCS");

	   ZAssert.assertNotNull(subfolder1ZD, "Verify the subfolder1 is still available in ZD");
	   ZAssert.assertNotNull(subfolder2ZD, "Verify the subfolder2 is still available in ZD");

	   ZAssert.assertEquals(trashZD.getId(), subfolder1ZD.getParentId(), "Verify the subfolder1's parent is now the trash folder ID in ZD");
	   ZAssert.assertEquals(trashZD.getId(), subfolder2ZD.getParentId(), "Verify the subfolder2's parent is now the trash folder ID in ZD");
	   ZAssert.assertEquals(name, subfolder1ZD.getName(), "Verify the subfolder1's name in ZD");
	   ZAssert.assertEquals(name + "_", subfolder2ZD.getName(), "Verify the subfolder2's name in ZD");
	}

   @Test(   description = "Delete local mail folder - Right click, Delete",
         groups = { "smoke" })
   public void DeleteLocalMailFolder() throws HarnessException {
      String folderName = "folder" + ZmailSeleniumProperties.getUniqueString();

      FolderItem folderItem = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            SystemFolder.UserRoot,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      app.zGetActiveAccount().soapSend(
            "<CreateFolderRequest xmlns='urn:zmailMail'>" +
            "<folder name='" + folderName +"' l='"+ folderItem.getId() +"'/>" +
            "</CreateFolderRequest>",
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      FolderItem desktopFolder = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      relogin();

      // Delete the folder using context menu
      app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_DELETE, desktopFolder);

      // Verify the folder is now in the trash
      FolderItem deletedFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);
      FolderItem trashFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            SystemFolder.Trash,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      ZAssert.assertNotNull(deletedFolder, "Verify the folder is still available");
      ZAssert.assertEquals(trashFolder.getId(), deletedFolder.getParentId(),
            "Verify the folder's parent ID is correct");

   }

   @Test(   description = "Delete local mail subfolder - Right click, Delete",
         groups = { "smoke" })
   public void DeleteLocalMailSubfolder() throws HarnessException {
      String folderName = "folder" + ZmailSeleniumProperties.getUniqueString();
      String subfolderName = "subfolder" + ZmailSeleniumProperties.getUniqueString();

      FolderItem folderItem = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            SystemFolder.UserRoot,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      app.zGetActiveAccount().soapSend(
            "<CreateFolderRequest xmlns='urn:zmailMail'>" +
            "<folder name='" + folderName +"' l='"+ folderItem.getId() +"'/>" +
            "</CreateFolderRequest>",
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      FolderItem desktopFolder = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      app.zGetActiveAccount().soapSend(
            "<CreateFolderRequest xmlns='urn:zmailMail'>" +
            "<folder name='" + subfolderName +"' l='"+ desktopFolder.getId() +"'/>" +
            "</CreateFolderRequest>",
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      FolderItem desktopSubfolder = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), subfolderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      relogin();
      app.zTreeMail.zExpandAll();

      // Delete the folder using context menu
      app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_DELETE, desktopSubfolder);

      // Verify the folder is now in the trash
      FolderItem deletedFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            subfolderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);
      FolderItem trashFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            SystemFolder.Trash,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      ZAssert.assertNotNull(deletedFolder, "Verify the folder is still available");
      ZAssert.assertEquals(trashFolder.getId(), deletedFolder.getParentId(),
            "Verify the folder's parent ID is correct");

      // Part of cleanup, delete the left-over parent folder through SOAP
      FolderItem.deleteUsingSOAP(app.zGetActiveAccount(),
            folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);
   }

   @Test(   description = "Delete local mail folder where local trash can already" +
   		" has a folder with the same name",
         groups = { "functional" })
   public void DeleteDuplicatedNamedLocalFolder() throws HarnessException {
      String folderName = "folder" + ZmailSeleniumProperties.getUniqueString();

      FolderItem folderItem = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            SystemFolder.UserRoot,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      // Create and Delete the 1st folder
      app.zGetActiveAccount().soapSend(
            "<CreateFolderRequest xmlns='urn:zmailMail'>" +
            "<folder name='" + folderName +"' l='"+ folderItem.getId() +"'/>" +
            "</CreateFolderRequest>",
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      FolderItem desktopFolder1 = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      relogin();

      // Delete the folder using context menu
      app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_DELETE, desktopFolder1);

      // Create and Delete the 2nd folder
      app.zGetActiveAccount().soapSend(
            "<CreateFolderRequest xmlns='urn:zmailMail'>" +
            "<folder name='" + folderName +"' l='"+ folderItem.getId() +"'/>" +
            "</CreateFolderRequest>",
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      FolderItem desktopFolder2 = FolderItem.importFromSOAP(app
            .zGetActiveAccount(), folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      relogin();

      // Delete the folder using context menu
      app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_DELETE, desktopFolder2);

      // Verify the folder is now in the trash
      FolderItem deletedFolder1 = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            folderName,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      FolderItem deletedFolder2 = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            folderName + "_",
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      FolderItem trashFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),
            SystemFolder.Trash,
            SOAP_DESTINATION_HOST_TYPE.CLIENT,
            ZmailAccount.clientAccountName);

      ZAssert.assertNotNull(deletedFolder1, "Verify the folder1 is still available");
      ZAssert.assertEquals(trashFolder.getId(), deletedFolder1.getParentId(),
            "Verify the folder1's parent ID is correct");

      ZAssert.assertNotNull(deletedFolder2, "Verify the folder2 is still available");
      ZAssert.assertEquals(trashFolder.getId(), deletedFolder2.getParentId(),
            "Verify the folder2's parent ID is correct");

   }
}
