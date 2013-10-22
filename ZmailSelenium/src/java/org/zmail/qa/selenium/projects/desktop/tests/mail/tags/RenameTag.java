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

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.items.TagItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.*;


public class RenameTag extends AjaxCommonTest {

	public RenameTag() {
		logger.info("New "+ RenameTag.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = null;
		
	}
	
	@Test(	description = "Rename a tag - Right click, Rename",
			groups = { "smoke" })
	public void RenameTag_01() throws HarnessException {
		
		// Create the tag to rename
		String name1 = "tag" + ZmailSeleniumProperties.getUniqueString();
		String name2 = "tag" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateTagRequest xmlns='urn:zmailMail'>" +
                	"<tag name='"+ name1 +"' color='1' />" +
                "</CreateTagRequest>");

		TagItem tag = TagItem.importFromSOAP(app.zGetActiveAccount(), name1);
		ZAssert.assertNotNull(tag, "Verify the tag was created");
		
		
		// Click on Get Mail to refresh the folder list
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Rename the tag using the context menu
		DialogRenameTag dialog = (DialogRenameTag) app.zTreeMail.zTreeItem(Action.A_RIGHTCLICK, Button.B_RENAME, tag);
		ZAssert.assertNotNull(dialog, "Verify the warning dialog opened");
		
		// Set the new name, click OK
		dialog.zSetNewName(name2);
		dialog.zClickButton(Button.B_OK);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

		// Verify the tag is no longer found
		app.zGetActiveAccount().soapSend("<GetTagRequest xmlns='urn:zmailMail'/>");
		
		Element[] eTag1 = app.zGetActiveAccount().soapSelectNodes("//mail:tag[@name='"+ name1 +"']");
		ZAssert.assertEquals(eTag1.length, 0, "Verify the old tag name no longer exists");
		
		Element[] eTag2 = app.zGetActiveAccount().soapSelectNodes("//mail:tag[@name='"+ name2 +"']");
		ZAssert.assertEquals(eTag2.length, 1, "Verify the new tag name exists");

		
	}

	


}
