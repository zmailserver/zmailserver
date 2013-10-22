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
package com.zimbra.qa.selenium.projects.ajax.tests.addressbook.tags;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.TagItem;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import com.zimbra.qa.selenium.projects.ajax.ui.DialogTag;




public class CreateTag extends PrefGroupMailByMessageTest {

	public CreateTag() {
		logger.info("New "+ CreateTag.class.getCanonicalName());
		
		
		// All tests start at the addressbook page
		super.startingPage = app.zPageAddressbook;
		super.startingAccountPreferences = null;

	}
	
	@Test(	description = "Create a new tag by clicking 'new tag' on folder tree",
			groups = { "sanity" })
	public void CreateTag_01() throws HarnessException {
		
		
		// Set the new tag name
		String name = "tag" + ZimbraSeleniumProperties.getUniqueString();
		
		DialogTag dialog = (DialogTag)app.zTreeContacts.zPressButton(Button.B_TREE_NEWTAG);
		ZAssert.assertNotNull(dialog, "Verify the new dialog opened");
		
		// Fill out the form with the basic details
		dialog.zSetTagName(name);
		dialog.zSubmit();


		// Make sure the tag was created on the server
		TagItem tag = TagItem.importFromSOAP(app.zGetActiveAccount(), name);
		ZAssert.assertNotNull(tag, "Verify the new folder was created");
		
		ZAssert.assertEquals(tag.getName(), name, "Verify the server and client tag names match");
		
	}

	
	
	@Test(	description = "Create a new tag using keyboard shortcuts",
			groups = { "functional" })
	public void CreateTag_02() throws HarnessException {
		
		Shortcut shortcut = Shortcut.S_NEWTAG;
		
		
		
		// Set the new tag name
		String name = "tag" + ZimbraSeleniumProperties.getUniqueString();
		
		DialogTag dialog = (DialogTag)app.zPageAddressbook.zKeyboardShortcut(shortcut);
		ZAssert.assertNotNull(dialog, "Verify the new dialog opened");
		
		// Fill out the form with the basic details
		dialog.zSetTagName(name);
		dialog.zSubmit();


		// Make sure the tag was created on the server
		TagItem tag = TagItem.importFromSOAP(app.zGetActiveAccount(), name);
		ZAssert.assertNotNull(tag, "Verify the new folder was created");
		
		ZAssert.assertEquals(tag.getName(), name, "Verify the server and client tag names match");
		
		
	}

	@Test(	description = "Create a new tag using context menu from a tag",
			groups = { "functional" })
	public void CreateTag_03() throws HarnessException {
		
		// Set the new tag name
		String name1 = "tag" + ZimbraSeleniumProperties.getUniqueString();
		String name2 = "tag" + ZimbraSeleniumProperties.getUniqueString();
		
		// Create a tag to right click on
		app.zGetActiveAccount().soapSend(
				"<CreateTagRequest xmlns='urn:zimbraMail'>" +
            		"<tag name='"+ name2 +"' color='1' />" +
            	"</CreateTagRequest>");


		// Get the tag
		TagItem tag2 = TagItem.importFromSOAP(app.zGetActiveAccount(), name2);

		
		// Refresh to get tag2
		app.zPageAddressbook.zRefresh();
		
		// Create a new tag using the context menu + New Tag
		DialogTag dialog = (DialogTag)app.zTreeContacts.zTreeItem(Action.A_RIGHTCLICK, Button.B_TREE_NEWTAG, tag2);
		ZAssert.assertNotNull(dialog, "Verify the new dialog opened");
		
		// Fill out the form with the basic details
		dialog.zSetTagName(name1);
		dialog.zSubmit();


		// Make sure the folder was created on the server
		TagItem tag1 = TagItem.importFromSOAP(app.zGetActiveAccount(), name1);

		ZAssert.assertNotNull(tag1, "Verify the new tag was created");
		
		ZAssert.assertEquals(tag1.getName(), name1, "Verify the server and client tag names match");
		
	}

	@Test(	description = "Create a new tag using mail app New -> New Tag",
			groups = { "functional" })
	public void CreateTag_04() throws HarnessException {
		
		
		// Set the new folder name
		String name = "tag" + ZimbraSeleniumProperties.getUniqueString();
				
		// Create a new folder in the inbox
		// using the context menu + New Folder
		DialogTag dialog = (DialogTag)app.zPageAddressbook.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_TAG);
		ZAssert.assertNotNull(dialog, "Verify the new dialog opened");
		
		// Fill out the form with the basic details
		dialog.zSetTagName(name);
		dialog.zSubmit();


		// Make sure the folder was created on the server
		TagItem tag = TagItem.importFromSOAP(app.zGetActiveAccount(), name);
		ZAssert.assertNotNull(tag, "Verify the new tag was created");
		
		ZAssert.assertEquals(tag.getName(), name, "Verify the server and client tag names match");
		
	}


}
