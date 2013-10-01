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
package com.zimbra.qa.selenium.projects.desktop.tests.addressbook.contactgroups;

import java.awt.event.KeyEvent;
import java.util.List;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.desktop.core.AjaxCommonTest;


public class DeleteContactGroup extends AjaxCommonTest  {
	public DeleteContactGroup() {
		logger.info("New "+ DeleteContactGroup.class.getCanonicalName());

		// All tests start at the Address page
		super.startingPage = app.zPageAddressbook;

		super.startingAccountPreferences = null;		

	}

   private void _verifyContactGroupDeleted(ContactGroupItem group  ) throws HarnessException {
      //verify toasted message 1 contact group moved to Trash
      String expectedMsg = "1 contact group moved to Trash";
      ZAssert.assertStringContains(app.zPageMain.zGetToaster().zGetToastMessage(),
            expectedMsg , "Verify toast message '" + expectedMsg + "'");

      //verify deleted contact group not displayed
      List<ContactItem> contacts = app.zPageAddressbook.zListGetContacts(); 

      boolean isFileAsEqual=false;
      for (ContactItem ci : contacts) {
         if (ci.fileAs.equals(group.groupName)) {
            isFileAsEqual = true;    
            break;
         }
      }

      ZAssert.assertFalse(isFileAsEqual, "Verify contact group " + group.groupName + " deleted");        

      FolderItem trash = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Trash);

      //verify deleted contact displayed in trash folder
      // refresh Trash folder
      app.zTreeContacts.zTreeItem(Action.A_LEFTCLICK, trash);

      contacts = app.zPageAddressbook.zListGetContacts(); 

      isFileAsEqual=false;
      for (ContactItem ci : contacts) {
         if (ci.fileAs.equals(group.groupName)) {
            isFileAsEqual = true;    
            break;
         }
      }

      ZAssert.assertTrue(isFileAsEqual, "Verify contact group (" + group.groupName + ") displayed in Trash folder");

   }

   @Test(   description = "Delete a contact group by click Delete button on toolbar",
         groups = { "smoke" })
   public void DeleteContactGroupByClickDeleteOnToolbar() throws HarnessException {

      // Create a contact group via Soap then select
      ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app,Action.A_LEFTCLICK);

      //delete contact group by click Delete button on toolbar
      app.zPageAddressbook.zToolbarPressButton(Button.B_DELETE);

      //verify contact group deleted
      _verifyContactGroupDeleted(group);

   }

   @Test(   description = "Delete a contact group by click Delete on Context Menu",
         groups = { "functional" })
   public void DeleteContactGroupByClickDeleteOnContextMenu() throws HarnessException {

      // Create a contact group via Soap then select
      ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app,Action.A_LEFTCLICK);

      //delete contact group by click Delete on Context menu
      app.zPageAddressbook.zListItem(Action.A_RIGHTCLICK, Button.B_DELETE, group.fileAs);

      //verify contact group deleted
      _verifyContactGroupDeleted(group);

   }

   @Test(   description = "Delete a contact group selected by checkbox by click Delete button on toolbar",
         groups = { "functional" })
   public void DeleteContactGroupSelectedWithCheckbox() throws HarnessException {

      // Create a contact group via Soap then select
      ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app,Action.A_CHECKBOX);

      //delete contact group by click Delete button on toolbar
      app.zPageAddressbook.zToolbarPressButton(Button.B_DELETE);

      //verify contact group deleted
      _verifyContactGroupDeleted(group);

   }

   @Test(   description = "Delete a contact group use shortcut Del",
         groups = { "functional" })
   public void DeleteContactGroupUseShortcutDel() throws HarnessException {

      // Create a contact group via Soap then select
      ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app,Action.A_LEFTCLICK);

      //delete contact group by click shortcut Del
      app.zPageAddressbook.zKeyboardKeyEvent(KeyEvent.VK_DELETE);

      //verify contact group deleted
      _verifyContactGroupDeleted(group);           
   }
   
   @Test(   description = "Delete a contact group use shortcut backspace",
         groups = { "functional" })
   public void DeleteContactGroupUseShortcutBackspace() throws HarnessException {

      // Create a contact group via Soap then select
      ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app,Action.A_LEFTCLICK);

      //delete contact group by click shortcut backspace
      app.zPageAddressbook.zKeyboardKeyEvent(KeyEvent.VK_BACK_SPACE);

      //verify contact group deleted
      _verifyContactGroupDeleted(group);           
   }

   @Test(   description = "Delete multiple contact groups at once",
         groups = { "functional" })
   public void DeleteMultipleContactGroups() throws HarnessException {

      // Create a contact group via Soap
       ContactGroupItem group1 = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());

      group1.setId(app.zGetActiveAccount().soapSelectValue("//mail:CreateContactResponse/mail:cn", "id"));
      String[] dlist = app.zGetActiveAccount().soapSelectValue("//mail:CreateContactResponse/mail:cn/mail:a[@n='dlist']", null).split(","); //a[2]   
      for (int i=0; i<dlist.length; i++) {
         group1.addDListMember(dlist[i]);
      }

      // Create a contact group via Soap
      ContactGroupItem group2 = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());

      group2.setId(app.zGetActiveAccount().soapSelectValue("//mail:CreateContactResponse/mail:cn", "id"));
      String[] dlist2 = app.zGetActiveAccount().soapSelectValue("//mail:CreateContactResponse/mail:cn/mail:a[@n='dlist']", null).split(","); //a[2]   
      for (int i=0; i<dlist2.length; i++) {
         group2.addDListMember(dlist2[i]);
      }

      // Create a contact group via Soap
      ContactGroupItem group3 = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());

      group3.setId(app.zGetActiveAccount().soapSelectValue("//mail:CreateContactResponse/mail:cn", "id"));
      String[] dlist3 = app.zGetActiveAccount().soapSelectValue("//mail:CreateContactResponse/mail:cn/mail:a[@n='dlist']", null).split(","); //a[2]   
      for (int i=0; i<dlist3.length; i++) {
         group3.addDListMember(dlist[i]);
      }

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageAddressbook.zWaitForDesktopLoadingSpinner(5000);

      // Refresh the view, to pick up the new contact groups
      FolderItem contactFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), "Contacts");         
      app.zTreeContacts.zTreeItem(Action.A_LEFTCLICK, contactFolder);

      // Select the items
      app.zPageAddressbook.zListItem(Action.A_CHECKBOX, group1.fileAs);           
      app.zPageAddressbook.zListItem(Action.A_CHECKBOX, group2.fileAs);        
      app.zPageAddressbook.zListItem(Action.A_CHECKBOX, group3.fileAs);

      //delete multiple contact groups by click Delete button on toolbar
      app.zPageAddressbook.zToolbarPressButton(Button.B_DELETE);

      //verify toasted message 3 contacts moved to Trash
      String expectedMsg = "3 contacts moved to Trash";
      ZAssert.assertStringContains(app.zPageMain.zGetToaster().zGetToastMessage(),
            expectedMsg , "Verify toast message '" + expectedMsg + "'");

      //verify deleted contact group not displayed
      List<ContactItem> contacts = app.zPageAddressbook.zListGetContacts(); 

      int count=0;
      for (ContactItem ci : contacts) {
         if (ci.fileAs.equals(group1.groupName) ||
               ci.fileAs.equals(group2.groupName) ||
               ci.fileAs.equals(group3.groupName)
         ) {
            count++;          
         }
      }

      ZAssert.assertTrue(count==0, "Verify contact groups " + group1.groupName + "," + group2.groupName + "," +  group3.groupName + " deleted");                  
   }

   @Test(   description = "Delete contact + contact group at once",
         groups = { "functional" })
   public void DeleteMixOfContactAndGroup() throws HarnessException {

      // Create a contact group via Soap
       ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());

      group.setId(app.zGetActiveAccount().soapSelectValue("//mail:CreateContactResponse/mail:cn", "id"));
      String[] dlist = app.zGetActiveAccount().soapSelectValue("//mail:CreateContactResponse/mail:cn/mail:a[@n='dlist']", null).split(","); //a[2]   
      for (int i=0; i<dlist.length; i++) {
         group.addDListMember(dlist[i]);
      }

      // Create a contact via Soap
		ContactItem contactItem = ContactItem.createContactItem(app.zGetActiveAccount());
      contactItem.setId(app.zGetActiveAccount().soapSelectValue("//mail:CreateContactResponse/mail:cn", "id"));

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageAddressbook.zWaitForDesktopLoadingSpinner(5000);

      // Refresh the view, to pick up the newly created ones
      FolderItem contactFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), "Contacts");         
      app.zTreeContacts.zTreeItem(Action.A_LEFTCLICK, contactFolder);

      // Select the items
      app.zPageAddressbook.zListItem(Action.A_CHECKBOX, group.fileAs);            
      app.zPageAddressbook.zListItem(Action.A_CHECKBOX, contactItem.fileAs);        

      //delete contact + group by click Delete button on toolbar
      app.zPageAddressbook.zToolbarPressButton(Button.B_DELETE);

      //verify toasted message 2 contacts moved to Trash
      String expectedMsg = "2 contacts moved to Trash";
      ZAssert.assertStringContains(app.zPageMain.zGetToaster().zGetToastMessage(),
            expectedMsg , "Verify toast message '" + expectedMsg + "'");

      //verify deleted contact + group not displayed
      List<ContactItem> contacts = app.zPageAddressbook.zListGetContacts(); 

      int count=0;
      for (ContactItem ci : contacts) {
         if (ci.fileAs.equals(group.groupName) ||         
               ci.fileAs.equals(contactItem.fileAs)
         ) {
            count++;          
         }
      }

      ZAssert.assertTrue(count==0, "Verify contact + group " + contactItem.fileAs  + "," +  group.groupName + " deleted");

   }

}
