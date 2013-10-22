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
package org.zmail.qa.selenium.projects.ajax.tests.tasks.tags;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties.AppType;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogTag;

public class CreateTag extends AjaxCommonTest {

	public CreateTag() {
		logger.info("New "+ CreateTag.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageTasks;
		super.startingAccountPreferences = null;

	}

	@Test(description = "Create a new tag by clicking 'new tag' on Task page", groups = { "sanity" })
	public void CreateTag_01() throws HarnessException {


		// Set the new tag name
		String name = "tag" + ZmailSeleniumProperties.getUniqueString();

		DialogTag dialog = null;
		if (ZmailSeleniumProperties.getAppType() == AppType.DESKTOP) {
			// TODO: For now, on desktop test, create the folder through New drop down menu,
			// until a way to identify desktop/ajax specific
			// test is decided.
			dialog = (DialogTag)app.zPageTasks.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_TAG);
		} else {
			dialog = (DialogTag)app.zTreeTasks.zPressPulldown(Button.B_TREE_TAGS_OPTIONS, Button.B_TREE_NEWTAG);
		}

		ZAssert.assertNotNull(dialog, "Verify the new tag dialog opened");

		// Fill out the form with the basic details
		dialog.zSubmit(name);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		// Make sure the tag was created on the server
		TagItem tag = app.zPageTasks.zGetTagItem(app.zGetActiveAccount(), name);

		ZAssert.assertNotNull(tag, "Verify the new tag was created");
		ZAssert.assertEquals(tag.getName(), name, "Verify the server and client tag names match");

	}


	@Test(description = "Create a new tag using keyboard shortcuts on Task apge", groups = { "smoke" })
	public void CreateTag_02() throws HarnessException {

		Shortcut shortcut = Shortcut.S_NEWTAG;
		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);

		// Set the new tag name
		String name = "tag" + ZmailSeleniumProperties.getUniqueString();
		
		//Added explicitly boz some time focus does shifted into search input after login
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		DialogTag dialog = (DialogTag)app.zPageTasks.zKeyboardShortcut(shortcut);
		ZAssert.assertNotNull(dialog, "Verify the new dialog opened");

		// Fill out the form with the basic details
		dialog.zSubmit(name);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		//Need to click on Task folder explicitly so that created tag does show in tag list.
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		// Make sure the tag was created on the server
		TagItem tag = app.zPageTasks.zGetTagItem(app.zGetActiveAccount(), name);

		ZAssert.assertNotNull(tag, "Verify the new tag was created");
		ZAssert.assertEquals(tag.getName(), name, "Verify the server and client tag names match");

	}

	@Test(description = "Create a new tag using context menu from a tag", groups = { "smoke" })
	public void CreateTag_03() throws HarnessException {

		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);

		// Set the new tag name
		String name1 = "tag" + ZmailSeleniumProperties.getUniqueString();
		String name2 = "tag" + ZmailSeleniumProperties.getUniqueString();

		// Create a tag to right click on
		app.zGetActiveAccount().soapSend(
				"<CreateTagRequest xmlns='urn:zmailMail'>" +
				"<tag name='"+name2+"' color='1' />" +
		"</CreateTagRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		// Get the tag
		TagItem tag2 = TagItem.importFromSOAP(app.zGetActiveAccount(), name2);

		//Need to click on Task folder explicitly so that created tag does show in tag list.
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		// Create a new tag using the context menu + New Tag
		DialogTag dialog = (DialogTag)app.zTreeTasks.zTreeItem(Action.A_RIGHTCLICK, Button.B_TREE_NEWTAG, tag2);
		ZAssert.assertNotNull(dialog, "Verify the new dialog opened");

		// Fill out the form with the basic details
		dialog.zSubmit(name1);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		// Make sure the tag was created on the server
		TagItem tag1 = app.zPageTasks.zGetTagItem(app.zGetActiveAccount(), name1);
		ZAssert.assertNotNull(tag1, "Verify the new tag was created");

		ZAssert.assertEquals(tag1.getName(), name1, "Verify the server and client tag names match");

	}

	@Test(description = "Create a new tag using task app New -> Tag", groups = { "smoke" })
	public void CreateTag_04() throws HarnessException {

		// Set the new tag name
		String name = "tag" + ZmailSeleniumProperties.getUniqueString();

		// Create a new tag in the task page using the context menu + New tag
		DialogTag dialog = (DialogTag) app.zPageTasks.zToolbarPressPulldown(
				Button.B_NEW, Button.O_NEW_TAG);
		ZAssert.assertNotNull(dialog, "Verify the new dialog opened");

		// Fill out the form with the basic details
		dialog.zSubmit(name);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		// Make sure the task was created on the server
		TagItem tag = app.zPageTasks.zGetTagItem(app.zGetActiveAccount(), name);
		ZAssert.assertNotNull(tag, "Verify the new tag was created");

		ZAssert.assertEquals(tag.getName(), name,
		"Verify the server and client tag names match");

	}


}
