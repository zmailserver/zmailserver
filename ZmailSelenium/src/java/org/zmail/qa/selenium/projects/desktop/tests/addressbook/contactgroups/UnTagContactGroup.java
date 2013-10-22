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


import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.ContactGroupItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;



public class UnTagContactGroup extends AjaxCommonTest  {
	public UnTagContactGroup() {
		logger.info("New "+ UnTagContactGroup.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage = app.zPageAddressbook;

		super.startingAccountPreferences = null;		
		
	}

	//verify a contact group is untaged via UI & soap
	private void VerifyContactGroupUntag(ContactGroupItem group, String tagName) throws HarnessException {
		String expectedMsg = "Tag \"" + tagName + "\" removed from 1 contact group";
		
		ZAssert.assertStringContains(app.zPageMain.zGetToaster().zGetToastMessage(),
		        expectedMsg , "Verify toast message '" + expectedMsg + "'");
 
	    GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

	    app.zGetActiveAccount().soapSend(
				"<GetContactsRequest xmlns='urn:zmailMail'>" +
					"<cn id='"+ group.getId() +"'/>" +
				"</GetContactsRequest>");
	    String contactTag = app.zGetActiveAccount().soapSelectValue("//mail:GetContactsResponse//mail:cn", "t");

	    ZAssert.assertNull(contactTag, "Verify that the tag is removed from the contact");
    }

	//get tag id via soap
	private String GetTagid(String tagName) throws HarnessException{
			
	   // Create a tag via soap
		app.zGetActiveAccount().soapSend(
				"<CreateTagRequest xmlns='urn:zmailMail'>" +
               	"<tag name='"+ tagName +"' color='1' />" +
               "</CreateTagRequest>");
		String tagid = app.zGetActiveAccount().soapSelectValue("//mail:CreateTagResponse/mail:tag", "id");

		return tagid;
	}
	
	@Test(	description = "Untag a contact group by click Tag->Remove tag on toolbar ",
			groups = { "smoke" })
	public void UnTagContactGroup_01() throws HarnessException {
		String tagName = "tag"+ ZmailSeleniumProperties.getUniqueString();	
		String tagid = GetTagid(tagName);
		
		// Create a contact group via Soap then select
		ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app, Action.A_LEFTCLICK, tagid);
	  
    	// Untag it by click Tag->Remove Tag on toolbar 
		app.zPageAddressbook.zToolbarPressPulldown(Button.B_TAG, Button.O_TAG_REMOVETAG);
    
        // Verify contact group untagged
		VerifyContactGroupUntag(group, tagName);
	 
   	}
	

	@Test(	description = "Untag a contact group by click Tag->Remove tag on Context Menu",
			groups = { "functional" })
	public void UnTagContactGroup_02() throws HarnessException {
		String tagName = "tag"+ ZmailSeleniumProperties.getUniqueString();	
		String tagid = GetTagid(tagName);
		
		// Create a contact group via Soap then select
		ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app, Action.A_LEFTCLICK, tagid);
	  
    	// Untag it by click Tag->Remove Tag on context menu
		app.zPageAddressbook.zListItem(Action.A_RIGHTCLICK, Button.B_TAG, Button.O_TAG_REMOVETAG , group.fileAs);

	     
        // Verify contact group untagged
		VerifyContactGroupUntag(group,tagName);
	 
   	}

}

