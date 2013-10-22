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
package org.zmail.qa.selenium.projects.ajax.tests.addressbook.bugs;


import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.addressbook.DialogNewContactGroup;

public class Bug44132_ManipulateContactGroupFromContactContextMenu extends AjaxCommonTest  {

	public Bug44132_ManipulateContactGroupFromContactContextMenu() {
		logger.info("New "+ Bug44132_ManipulateContactGroupFromContactContextMenu.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage = app.zPageAddressbook;

		// Enable user preference checkboxes
		super.startingAccountPreferences = new HashMap<String , String>() {
			private static final long serialVersionUID = 8504391696323008278L;
		{
			   
		    	put("zmailPrefShowSelectionCheckbox", "TRUE");		         
		   }};			
		
	}
	
	@Test(	description = "Create a new contact group by right click on existing contact",
			groups = { "smoke" })
	public void CreateContactGroupWith1Contact() throws HarnessException {			
		
		//-- Data
		
		// Create a contact
		ContactItem contact = ContactItem.createContactItem(app.zGetActiveAccount());
		
		// The contact group name
		String groupname = "group" + ZmailSeleniumProperties.getUniqueString();
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();
		
		// Right click on the contact
		DialogNewContactGroup dialog = (DialogNewContactGroup) app.zPageAddressbook.zListItem(
				Action.A_RIGHTCLICK, 
				Button.B_CONTACTGROUP, 
				Button.O_NEW_CONTACTGROUP, 
				contact.firstName);
		
		dialog.zEnterGroupName(groupname);
		dialog.zClickButton(Button.B_OK);
		
		
		
		//-- Verification
		
		// Verify the contact group is created
		ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), groupname);
		ZAssert.assertNotNull(actual,  "Verify the contact group is created");
		
		// Verify the contact group contains the contact
		ZAssert.assertContains(
				actual.getMemberList(), 
				new ContactGroupItem.MemberItemContact(contact), 
				"Verify the contact group conatins the contact");
		


	}

	@Test(	description = "D1 Enhancement : Add a contact to an existing group",
			groups = { "smoke", "matt" })
	public void Add1ContactToExistingGroup() throws HarnessException {			
		
		//-- Data
		
		// Create a contact
		ContactItem contact = ContactItem.createContactItem(app.zGetActiveAccount());
		
		// Create a contact group
		ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();
		
		// Right click on the contact -> Group -> Existing Group Name
		app.zPageAddressbook.zListItem(
				Action.A_RIGHTCLICK, 
				Button.B_CONTACTGROUP, 
				group, 
				contact.getName());
		
		
		
		
		//-- Verification
		
		// Verify the contact group is created
		ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), group.getName());
		ZAssert.assertNotNull(actual,  "Verify the contact group is created");
		
		// Verify the contact group contains the contact
		ZAssert.assertContains(
				actual.getMemberList(), 
				new ContactGroupItem.MemberItemContact(contact), 
				"Verify the contact group conatins the contact");
		


									
	}

	@Test(	description = "D1 Enhancement : Add 3 contacts to an existing group",
			groups = { "functional", "matt" })
	public void Add3ContactsToExistingGroup() throws HarnessException {
		
		//-- Data
		
		// Create a contact
		ContactItem contact1 = ContactItem.createContactItem(app.zGetActiveAccount());
		ContactItem contact2 = ContactItem.createContactItem(app.zGetActiveAccount());
		ContactItem contact3 = ContactItem.createContactItem(app.zGetActiveAccount());
		
		// Create a contact group
		ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();

		// Check 3 contact items
	    app.zPageAddressbook.zListItem(Action.A_CHECKBOX, contact1.getName());
	    app.zPageAddressbook.zListItem(Action.A_CHECKBOX, contact2.getName());
	    app.zPageAddressbook.zListItem(Action.A_CHECKBOX, contact3.getName());


		// Right click on one contact -> Group -> Existing Group Name
		app.zPageAddressbook.zListItem(
				Action.A_RIGHTCLICK, 
				Button.B_CONTACTGROUP, 
				group, 
				contact1.getName());
		
		
		
		
		//-- Verification
		
		// Verify the contact group is created
		ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), group.getName());
		ZAssert.assertNotNull(actual,  "Verify the contact group is created");
		
		// Verify the contact group contains the contact
		ZAssert.assertContains(
				actual.getMemberList(), 
				new ContactGroupItem.MemberItemContact(contact1), 
				"Verify the contact group conatins the contact");
		
		ZAssert.assertContains(
				actual.getMemberList(), 
				new ContactGroupItem.MemberItemContact(contact2), 
				"Verify the contact group conatins the contact");
		
		ZAssert.assertContains(
				actual.getMemberList(), 
				new ContactGroupItem.MemberItemContact(contact3), 
				"Verify the contact group conatins the contact");


									

	}
	
	@Test(	description = "D1 Enhancement : Create a contact group with 3 contacts",
			groups = { "functional" })
	public void CreateContactGroupWith3Contacts() throws HarnessException {			
		
		//-- Data
		
		// Create a contact
		ContactItem contact1 = ContactItem.createContactItem(app.zGetActiveAccount());
		ContactItem contact2 = ContactItem.createContactItem(app.zGetActiveAccount());
		ContactItem contact3 = ContactItem.createContactItem(app.zGetActiveAccount());
		
		// Create a contact group
		String groupname = "group"+ ZmailSeleniumProperties.getUniqueString();
		
		

		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();

		// Check 3 contact items
	    app.zPageAddressbook.zListItem(Action.A_CHECKBOX, contact1.getName());
	    app.zPageAddressbook.zListItem(Action.A_CHECKBOX, contact2.getName());
	    app.zPageAddressbook.zListItem(Action.A_CHECKBOX, contact3.getName());


		// Right click on one contact -> Group -> Existing Group Name
	    DialogNewContactGroup dialog = (DialogNewContactGroup) app.zPageAddressbook.zListItem(
				Action.A_RIGHTCLICK, 
				Button.B_CONTACTGROUP, 
				Button.O_NEW_CONTACTGROUP, 
				contact1.getName());

		dialog.zEnterGroupName(groupname);
		dialog.zClickButton(Button.B_OK);

		
		
		//-- Verification
		
		// Verify the contact group is created
		ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), groupname);
		ZAssert.assertNotNull(actual,  "Verify the contact group is created");
		
		// Verify the contact group contains the contact
		ZAssert.assertContains(
				actual.getMemberList(), 
				new ContactGroupItem.MemberItemContact(contact1), 
				"Verify the contact group conatins the contact");
		
		ZAssert.assertContains(
				actual.getMemberList(), 
				new ContactGroupItem.MemberItemContact(contact2), 
				"Verify the contact group conatins the contact");
		
		ZAssert.assertContains(
				actual.getMemberList(), 
				new ContactGroupItem.MemberItemContact(contact3), 
				"Verify the contact group conatins the contact");


									

	}

	@Test(	description = "D1 Enhancement : Create a contact group with 1 contact + 1 group",
			groups = { "functional" })
	public void CreateContactGroupWith1ContactAnd1Group() throws HarnessException {			
		
		//-- Data
		
		// Create a contact
		ContactItem contact = ContactItem.createContactItem(app.zGetActiveAccount());
		
		// Create a contact group
		ContactGroupItem group = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		
		String groupname = "group" + ZmailSeleniumProperties.getUniqueString();
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();

		// Check 3 contact items
	    app.zPageAddressbook.zListItem(Action.A_CHECKBOX, contact.getName());
	    app.zPageAddressbook.zListItem(Action.A_CHECKBOX, group.getName());


		// Right click on one contact -> Group -> Existing Group Name
	    DialogNewContactGroup dialog = (DialogNewContactGroup) app.zPageAddressbook.zListItem(
				Action.A_RIGHTCLICK, 
				Button.B_CONTACTGROUP, 
				Button.O_NEW_CONTACTGROUP, 
				contact.getName());
		
		dialog.zEnterGroupName(groupname);
		dialog.zClickButton(Button.B_OK);

		
		
		
		//-- Verification
		
		// Verify the contact group is created
		ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), groupname);
		ZAssert.assertNotNull(actual,  "Verify the contact group is created");
		
		// Verify the contact group contains the contact
		ZAssert.assertContains(
				actual.getMemberList(), 
				new ContactGroupItem.MemberItemContact(contact), 
				"Verify the contact group conatins the contact");
		
		// The group members will be added to the new group
		for (ContactGroupItem.MemberItem m : group.getMemberList()) {
			ZAssert.assertContains(
					actual.getMemberList(),
					m,
					"Verify the contact group contains the group members");

		}
		


	}		
	

	@Test(	description = "D1 Enhancement : Add 1 contact + 1 group to an existing group",
			groups = { "functional" })
	public void Add1ContactAnd1GroupToExistingGroup() throws HarnessException {
		
		//-- Data
		
		// Create a contact
		ContactItem contact = ContactItem.createContactItem(app.zGetActiveAccount());
		
		// Create a contact group
		ContactGroupItem group1 = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		ContactGroupItem group2 = ContactGroupItem.createContactGroupItem(app.zGetActiveAccount());
		
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();

		// Check 3 contact items
	    app.zPageAddressbook.zListItem(Action.A_CHECKBOX, contact.getName());
	    app.zPageAddressbook.zListItem(Action.A_CHECKBOX, group1.getName());


		// Right click on one contact -> Group -> Existing Group Name
		app.zPageAddressbook.zListItem(
				Action.A_RIGHTCLICK, 
				Button.B_CONTACTGROUP, 
				group2, 
				contact.getName());
		
		
		
		
		//-- Verification
		
		// Verify the contact group is created
		ContactGroupItem actual = ContactGroupItem.importFromSOAP(app.zGetActiveAccount(), group1.getName());
		ZAssert.assertNotNull(actual,  "Verify the contact group is created");
		
		// Verify the contact group contains the contact
		ZAssert.assertContains(
				actual.getMemberList(), 
				new ContactGroupItem.MemberItemContact(contact), 
				"Verify the contact group conatins the contact");
		
		// The group members will be added to the new group
		for (ContactGroupItem.MemberItem m : group1.getMemberList()) {
			ZAssert.assertContains(
					actual.getMemberList(),
					m,
					"Verify the contact group contains the group members");

		}
		



	}
	
}
