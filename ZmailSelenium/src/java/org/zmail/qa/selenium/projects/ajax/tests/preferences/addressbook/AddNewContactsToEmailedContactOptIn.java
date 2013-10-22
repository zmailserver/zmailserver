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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.addressbook;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;

public class AddNewContactsToEmailedContactOptIn extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public AddNewContactsToEmailedContactOptIn() {
		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{				
		 		put("zmailPrefAutoAddAddressEnabled", "TRUE");
			}
		};
	}

	/**
	 * Test case : Verify select checkbox works (e.g make the option changed to opt-out)
	 * @throws HarnessException
	 */
	@Test(description= " select the checkbox to toggle the opt-in option to opt-out ", groups= {"smoke" })
	public void UnSelectAutoAddAddressCheckbox() throws HarnessException {
		// Go to "Addressbook"
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.AddressBook);

		// Verify the status of the checkbox is TRUE
		ZAssert.assertTrue(app.zPagePreferences.zGetCheckboxStatus("zmailPrefAutoAddAddressEnabled"),
				  "Verify if zmailPrefAutoAddAddressEnabled is TRUE, the preference box is checked" );			
	
		// Uncheck the box
		app.zPagePreferences.zCheckboxSet("css=input[id$=_AUTO_ADD_ADDRESS]",false);
			
		// Click save
		app.zPagePreferences.zToolbarPressButton(Button.B_SAVE);		
		
		// Verify the status of the checkbox is FALSE
		// frontend check
		ZAssert.assertFalse(app.zPagePreferences.zGetCheckboxStatus("zmailPrefAutoAddAddressEnabled"),
				  "Verify the preference box is unchecked" );
		
		// backend check
		app.zGetActiveAccount().soapSend(
                   "<GetPrefsRequest xmlns='urn:zmailAccount'>"
                 +     "<pref name='zmailPrefAutoAddAddressEnabled'/>"
                 + "</GetPrefsRequest>");

		ZAssert.assertEquals(app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zmailPrefAutoAddAddressEnabled']", null),
				"FALSE", "Verify zmailPrefAutoAddAddressEnabled is FALSE" );
				
		// Revert to original value for subsequent test cases
		// Check the box
		app.zPagePreferences.zCheckboxSet("css=input[id$=_AUTO_ADD_ADDRESS]",true);
			
		// Click save
		app.zPagePreferences.zToolbarPressButton(Button.B_SAVE);		
		
	}

	
}
