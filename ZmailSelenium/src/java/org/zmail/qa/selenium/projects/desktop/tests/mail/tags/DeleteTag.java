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
package org.zmail.qa.selenium.projects.desktop.tests.mail.tags;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.TagItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.DialogWarning;


public class DeleteTag extends AjaxCommonTest {

	public DeleteTag() {
		logger.info("New "+ DeleteTag.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = null;
		
	}
	
	@Test(	description = "Delete a tag - Right click, Delete",
			groups = { "smoke" })
	public void DeleteTag_01() throws HarnessException {
		
		

		
		// Create the tag to delete
		String name = "tag" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateTagRequest xmlns='urn:zmailMail'>" +
                	"<tag name='"+ name +"' color='1' />" +
                "</CreateTagRequest>");

		TagItem tag = TagItem.importFromSOAP(app.zGetActiveAccount(), name);
		ZAssert.assertNotNull(tag, "Verify the tag was created");
		
		
		// Click on Get Mail to refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Delete the tag using context menu
		DialogWarning dialog = (DialogWarning) app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_DELETE, tag);
		ZAssert.assertNotNull(dialog, "Verify the warning dialog opened");
		
		
		// Click "Yes" to confirm
		dialog.zClickButton(Button.B_YES);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		// To check whether deleted tag is exist
		app.zGetActiveAccount().soapSend("<GetTagRequest xmlns='urn:zmailMail'/>");

		String tagname = app.zGetActiveAccount().soapSelectValue("//mail:GetTagResponse//mail:tag[@name='" + name + "']","name");
		ZAssert.assertNull(tagname, "Verify the tag is deleted");


		
	}

	


}
