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
package org.zmail.qa.selenium.projects.ajax.tests.addressbook.contactgroups;


import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.ContactGroupItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogWarning;
import org.zmail.qa.selenium.projects.ajax.ui.addressbook.*;
import org.zmail.qa.selenium.projects.ajax.ui.addressbook.FormContactGroupNew.Field;



public class EditContactGroup extends AjaxCommonTest  {
	public EditContactGroup() {
		logger.info("New "+ EditContactGroup.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage =  app.zPageAddressbook;

		// Make sure we are using an account with conversation view
		super.startingAccountPreferences = null;		
		
	}
   
	
	
	
	
	@Test(	description = "Edit a contact group by click Edit on Toolbar button",
			groups = { "smoke" })
	public void EditContactGroup_01() throws HarnessException {

		//--  Data
		
		// A new group name
		String newname = "edit" + ZmailSeleniumProperties.getUniqueString();
		
		// Create a contact group
		ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();
		
		// Select the contact group
		app.zPageAddressbook.zListItem(Action.A_LEFTCLICK, group.getName());
		
		//Click Edit on Toolbar button	
        FormContactGroupNew formContactGroupNew = (FormContactGroupNew) app.zPageAddressbook.zToolbarPressButton(Button.B_EDIT);
        
        // CHange the group name
        formContactGroupNew.zFillField(Field.GroupName, newname);
        formContactGroupNew.zSubmit();
        
        
        //-- Verification
        
        ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), "#nickname:"+ newname);
        ZAssert.assertNotNull(actual, "Verify the contact group with new name exists");
        

	}

	@Test(	description = "Edit a contact group by click Edit Group on Context Menu ",
			groups = { "functional" })
	public void EditContactGroup_02() throws HarnessException {

		//--  Data
		
		// A new group name
		String newname = "edit" + ZmailSeleniumProperties.getUniqueString();
		
		// Create a contact group
		ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();
		
		// Right click -> Edit	
        FormContactGroupNew formContactGroupNew = (FormContactGroupNew) app.zPageAddressbook.zListItem(Action.A_RIGHTCLICK, Button.B_EDIT, group.getName());        
        
        // CHange the group name
        formContactGroupNew.zFillField(Field.GroupName, newname);
        formContactGroupNew.zSubmit();
        
        
        //-- Verification
        
        ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), "#nickname:"+ newname);
        ZAssert.assertNotNull(actual, "Verify the contact group with new name exists");
        

	}


	@Test(	description = "Edit a contact group by double click on the contact group  ",
			groups = { "functional"})
	public void EditContactGroup_03() throws HarnessException {

		//--  Data
		
		// A new group name
		String newname = "edit" + ZmailSeleniumProperties.getUniqueString();
		
		// Create a contact group
		ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();
		
		// Double click	
        FormContactGroupNew formContactGroupNew = (FormContactGroupNew) app.zPageAddressbook.zListItem(Action.A_DOUBLECLICK, group.getName());        
        
        // CHange the group name
        formContactGroupNew.zFillField(Field.GroupName, newname);
        formContactGroupNew.zSubmit();
        
        
        //-- Verification
        
        ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), "#nickname:"+ newname);
        ZAssert.assertNotNull(actual, "Verify the contact group with new name exists");
        

	}

	@Test(	description = "Cancel Editing a contact group by click Close",
			groups = { "functional" })
	public void EditContactGroup_04() throws HarnessException {

		//--  Data
		
		// Create a contact group
		ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();
		
		// Select the contact group
		app.zPageAddressbook.zListItem(Action.A_LEFTCLICK, group.getName());
		
		//Click Edit on Toolbar button	
        FormContactGroupNew formContactGroupNew = (FormContactGroupNew) app.zPageAddressbook.zToolbarPressButton(Button.B_EDIT);
        
        // CHange the group name
        formContactGroupNew.zToolbarPressButton(Button.B_CLOSE);
        
        
        //-- Verification
        
        // Verify the form closed
        ZAssert.assertFalse(formContactGroupNew.zIsActive(), "Verify the form closed after clicking CLOSE");

        // Verify the contact still exists
        ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), "#nickname:"+ group.getName());
        ZAssert.assertNotNull(actual, "Verify the contact group with new name exists");
        
	}

	@Test(	description = "Cancel an edited contact group by click Close, then click No",
			groups = { "functional" })
	public void EditContactGroup_05() throws HarnessException {

		//--  Data
		
		// A new group name
		String newname = "edit" + ZmailSeleniumProperties.getUniqueString();
		
		// Create a contact group
		ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();
		
		// Select the contact group
		app.zPageAddressbook.zListItem(Action.A_LEFTCLICK, group.getName());
		
		//Click Edit on Toolbar button	
        FormContactGroupNew formContactGroupNew = (FormContactGroupNew) app.zPageAddressbook.zToolbarPressButton(Button.B_EDIT);
        
        // CHange the group name
        formContactGroupNew.zFillField(Field.GroupName, newname);
        DialogWarning dialog = (DialogWarning) formContactGroupNew.zToolbarPressButton(Button.B_CLOSE);
        
        // Wait for the dialog to appear
        dialog.zWaitForActive();
        
        // Click No: Don't save changes
        dialog.zClickButton(Button.B_NO);
        
        
        //-- Verification
        
        // Verify the form closed
        ZAssert.assertFalse(formContactGroupNew.zIsActive(), "Verify the form closed after clicking CLOSE");

        ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), "#nickname:"+ group.getName());
        ZAssert.assertNotNull(actual, "Verify the contact group with new name exists");
        
        actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), "#nickname:"+ newname);
        ZAssert.assertNull(actual, "Verify the contact group with new name exists");
        

	}

	@Test(	description = "Cancel an edited contact by click Close, then click Cancel",
			groups = { "functional", "matt" })
	public void EditContactGroup_06() throws HarnessException {

		//--  Data
		
		// A new group name
		String newname = "edit" + ZmailSeleniumProperties.getUniqueString();
		
		// Create a contact group
		ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();
		
		// Select the contact group
		app.zPageAddressbook.zListItem(Action.A_LEFTCLICK, group.getName());
		
		//Click Edit on Toolbar button	
        FormContactGroupNew formContactGroupNew = (FormContactGroupNew) app.zPageAddressbook.zToolbarPressButton(Button.B_EDIT);
        
        // CHange the group name
        formContactGroupNew.zFillField(Field.GroupName, newname);
        DialogWarning dialog = (DialogWarning) formContactGroupNew.zToolbarPressButton(Button.B_CLOSE);
        
        // Wait for the dialog to appear
        dialog.zWaitForActive();
        
        // Click Cancel: Save changes?
        dialog.zClickButton(Button.B_CANCEL);
        
        // Click Save
        formContactGroupNew.zToolbarPressButton(Button.B_SAVE);
        
        
        
        //-- Verification
        
        ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), "#nickname:"+ newname);
        ZAssert.assertNotNull(actual, "Verify the contact group with new name exists");
        

     }

	@Test(	description = "Cancel an edited contact by click Close, then click Yes",
			groups = { "functional", "matt"})
	public void EditContactGroup_07() throws HarnessException {

		//--  Data
		
		// A new group name
		String newname = "edit" + ZmailSeleniumProperties.getUniqueString();
		
		// Create a contact group
		ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();
		
		// Select the contact group
		app.zPageAddressbook.zListItem(Action.A_LEFTCLICK, group.getName());
		
		//Click Edit on Toolbar button	
        FormContactGroupNew formContactGroupNew = (FormContactGroupNew) app.zPageAddressbook.zToolbarPressButton(Button.B_EDIT);
        
        // CHange the group name
        formContactGroupNew.zFillField(Field.GroupName, newname);
        DialogWarning dialog = (DialogWarning) formContactGroupNew.zToolbarPressButton(Button.B_CLOSE);
        
        // Wait for the dialog to appear
        dialog.zWaitForActive();
        
        // Click Yes: Don't save changes
        dialog.zClickButton(Button.B_YES);
        
        
        
        //-- Verification
        
        ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), "#nickname:"+ newname);
        ZAssert.assertNotNull(actual, "Verify the contact group with new name exists");
        

	}

} 

