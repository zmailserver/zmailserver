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


import java.util.List;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.desktop.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.desktop.ui.addressbook.*;
import com.zimbra.qa.selenium.projects.desktop.ui.addressbook.FormContactGroupNew.Toolbar;


public class CreateContactGroup extends AjaxCommonTest  {

	public CreateContactGroup() {
		logger.info("New "+ CreateContactGroup.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage = app.zPageAddressbook;

		// Make sure we are using an account with conversation view
		super.startingAccountPreferences = null;		
	}
		
	
   private void _verification(ContactGroupItem group) throws HarnessException {
      //verify toasted message 'group created'  
      String expectedMsg ="Group Created";
      ZAssert.assertStringContains(app.zPageMain.zGetToaster().zGetToastMessage(),
            expectedMsg , "Verify toast message '" + expectedMsg + "'");

      //verify group name is displayed             
      List<ContactItem> contacts = app.zPageAddressbook.zListGetContacts();
      boolean isFileAsEqual=false;
      for (ContactItem ci : contacts) {
         if (ci.fileAs.equals(group.fileAs)) {
            isFileAsEqual = true;   
            break;
         }
      }

      ZAssert.assertTrue(isFileAsEqual, "Verify contact fileAs (" + group.fileAs + ") existed ");

      //verify location is System folder "Contacts"
      ZAssert.assertEquals(app.zPageAddressbook.sGetText("css=td.companyFolder"), SystemFolder.Contacts.getName(), "Verify location (folder) is " + SystemFolder.Contacts.getName());
   }
   
   
   @Test(   description = "Create a basic contact group",
         groups = { "sanity" })
   public void GroupOfNewEmail() throws HarnessException {        

      //Create random contact group data 
       ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());

      //open contact group form
      FormContactGroupNew formGroup = (FormContactGroupNew)app.zPageAddressbook.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_CONTACTGROUP);

      //fill in group name and email addresses
      formGroup.zFill(group);

      //click Save
      formGroup.zSubmit(); 

      //verification
      _verification(group);
   }

   @Test(   description = "Create a contact group with existing contacts",
         groups = { "functional" })
   public void GroupOfExistingContact() throws HarnessException {       
      //Create random contact group data 
       ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());

      //open contact group form
      FormContactGroupNew formGroup = (FormContactGroupNew)app.zPageAddressbook.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_CONTACTGROUP);

      //fill in group name
      formGroup.sType(FormContactGroupNew.Locators.zGroupnameField, group.groupName);     

      //create contacts
		ContactItem contact = ContactItem.createContactItem(app.zGetActiveAccount());
      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageAddressbook.zWaitForDesktopLoadingSpinner(5000);

      //select contacts option
      formGroup.select(app, FormContactGroupNew.Locators.zSearchDropdown,  FormContactGroupNew.SELECT_OPTION_TEXT_CONTACTS);

      //find email from existing contacts
      formGroup.sType(FormContactGroupNew.Locators.zFindField, contact.email);

      //click Find
      formGroup.zClick(FormContactGroupNew.Locators.zSearchButton);
      app.zPageAddressbook.zWaitForBusyOverlay();      

      //add all to the email list
      formGroup.zClick(FormContactGroupNew.Locators.zAddAllButton);

      //TODO: verify email add to the email area      

      //click Save
      formGroup.zSubmit(); 

      //verification
      _verification(group);

   }

   @Test(   description = "Create a contact group with GAL + existing contacts + new emails",
         groups = { "functional" })
   public void GroupOfGAL_ExistingContact_sNewEmail() throws HarnessException {        
      //Create random contact group data 
       ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());

      //open contact group form
      FormContactGroupNew formGroup = (FormContactGroupNew)app.zPageAddressbook.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_CONTACTGROUP);

      //fill in group name and email addresses
      formGroup.zFill(group);

      //select GAL option
      formGroup.select(app, FormContactGroupNew.Locators.zSearchDropdown,  FormContactGroupNew.SELECT_OPTION_TEXT_GAL);

      //find email from GAL
      formGroup.sType(FormContactGroupNew.Locators.zFindField, ZimbraAccount.AccountB().EmailAddress);

      //click Find
      formGroup.zClick(FormContactGroupNew.Locators.zSearchButton);
      app.zPageAddressbook.zWaitForBusyOverlay();      

      //add all to the email list
      formGroup.zClick(FormContactGroupNew.Locators.zAddAllButton);

      //create contacts
		ContactItem contact = ContactItem.createContactItem(app.zGetActiveAccount());
      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageAddressbook.zWaitForDesktopLoadingSpinner(5000);

      //select contacts option
      formGroup.select(app, FormContactGroupNew.Locators.zSearchDropdown,  FormContactGroupNew.SELECT_OPTION_TEXT_CONTACTS);

      //find email from existing contacts
      formGroup.sType(FormContactGroupNew.Locators.zFindField, contact.email);

      //click Find
      formGroup.zClick(FormContactGroupNew.Locators.zSearchButton);
      app.zPageAddressbook.zWaitForBusyOverlay();      

      //add all to the email list
      formGroup.zClick(FormContactGroupNew.Locators.zAddAllButton);

      //TODO: verify email add to the email area      

      //click Save
      formGroup.zSubmit(); 

      //verification
      _verification(group);

      //TODO: verified all selected emails in the group

   }

   @Test(   description = "Check disabled buttons in contact group's new form",
         groups = { "functional" })
   public void VerifyButtonsDisable() throws HarnessException {         
      //open contact group form
      FormContactGroupNew formGroup = (FormContactGroupNew)app.zPageAddressbook.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_CONTACTGROUP);

      //verify Save, Delete All, and Add (email) buttons disabled    
      ZAssert.assertFalse(formGroup.sIsVisible(Toolbar.SAVE), "Verify contact button Save disabled ");
      ZAssert.assertFalse(formGroup.sIsVisible(FormContactGroupNew.Locators.zDeleteAllButton), "Verify contact button Delete All disabled ");
      ZAssert.assertFalse(formGroup.sIsVisible(FormContactGroupNew.Locators.zAddNewButton), "Verify contact button Add disabled ");

   }

}
