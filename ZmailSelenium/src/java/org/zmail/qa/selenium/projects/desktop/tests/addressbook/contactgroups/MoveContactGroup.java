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
package org.zmail.qa.selenium.projects.desktop.tests.addressbook.contactgroups;


import java.util.List;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.*;


public class MoveContactGroup extends AjaxCommonTest  {
	public MoveContactGroup() {
		logger.info("New "+ MoveContactGroup.class.getCanonicalName());

		// All tests start at the Address page
		super.startingPage = app.zPageAddressbook;
		super.startingAccountPreferences = null;		

	}

   private void _moveAndVerify(FolderItem folder, ContactGroupItem group, DialogMove dialogContactMove) throws HarnessException {
      //enter the moved folder
      dialogContactMove.zClickTreeFolder(folder);
      dialogContactMove.zClickButton(Button.B_OK);

      //verify toasted message 1 contact group moved to target folder
      String expectedMsg = "1 contact group moved to";
      String toastMessage = app.zPageMain.zGetToaster().zGetToastMessage();
      ZAssert.assertStringContains(toastMessage, expectedMsg , "Verify toast message '" + expectedMsg + "'");
      ZAssert.assertStringContains(toastMessage,folder.getName() , "Verify toast message '" + folder.getName() + "'");

      //verify moved contact group not displayed
      List<ContactItem> contacts = app.zPageAddressbook.zListGetContacts(); 

      boolean isFileAsEqual=false;
      for (ContactItem ci : contacts) {
         if (ci.fileAs.equals(group.fileAs)) {
            isFileAsEqual = true;    
            break;
         }
      }

      ZAssert.assertFalse(isFileAsEqual, "Verify contact group fileAs (" + group.fileAs + ") not displayed in folder Contacts");

      //verify moved contact displayed in target folder
      // refresh target folder
      app.zTreeContacts.zTreeItem(Action.A_LEFTCLICK, folder);

      contacts = app.zPageAddressbook.zListGetContacts(); 

      isFileAsEqual=false;
      for (ContactItem ci : contacts) {
         if (ci.fileAs.equals(group.fileAs)) {
            isFileAsEqual = true;    
            break;
         }
      }

      ZAssert.assertTrue(isFileAsEqual, "Verify contact group fileAs (" + group.fileAs + ") not displayed in folder " + folder.getName());

   }

   @Test(   description = "Move a contact group to folder Emailed Contacts by click Move on toolbar",
         groups = { "smoke" })
   public void MoveToEmailedContactsClickMoveOnToolbar() throws HarnessException {

      FolderItem emailedContacts = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.EmailedContacts);

      // Create a contact group via Soap then select
      ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app, Action.A_LEFTCLICK);

      //click Move icon on toolbar
      DialogMove dialogContactMove = (DialogMove) app.zPageAddressbook.zToolbarPressButton(Button.B_MOVE);

      //move group to different folder
      _moveAndVerify(emailedContacts, group, dialogContactMove);    

   }

   @Test(   description = "Move a contact group to folder Trash by click Move on toolbar",
         groups = { "functional" })
   public void MoveToTrashClickMoveOnToolbar() throws HarnessException {

      FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Trash);

      // Create a contact group via Soap then select
      ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app, Action.A_LEFTCLICK);

      //click Move icon on toolbar
      DialogMove dialogContactMove = (DialogMove) app.zPageAddressbook.zToolbarPressButton(Button.B_MOVE);

      //move group to different folder
      _moveAndVerify(folder, group, dialogContactMove);    

   }

   @Test(   description = "Move a contact group to folder Emailed Contacts by click Move on Context menu",
         groups = { "functional" })
   public void MoveToEmailedContactsClickMoveOnContextmenu() throws HarnessException {

      FolderItem emailedContacts = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.EmailedContacts);

      // Create a contact group via Soap then select
      ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app, Action.A_LEFTCLICK);

      //click Move icon on context menu
      DialogMove dialogContactMove = (DialogMove) app.zPageAddressbook.zListItem(Action.A_RIGHTCLICK, Button.B_MOVE, group.fileAs);

      //move group to different folder
      _moveAndVerify(emailedContacts, group, dialogContactMove);    

   }

   @Test(   description = "Move a contact group to folder Emailed Contacts with shortcut m",
         groups = { "functional" })
   public void MoveToEmailedContactsClickShortcutm() throws HarnessException {

      FolderItem emailedContacts = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.EmailedContacts);

      // Create a contact group via Soap then select
      ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app, Action.A_LEFTCLICK);

      //click shortcut m
      DialogMove dialogContactMove = (DialogMove) app.zPageAddressbook.zKeyboardShortcut(Shortcut.S_MOVE);

      //move group to different folder
      _moveAndVerify(emailedContacts, group, dialogContactMove);    

   }

}
