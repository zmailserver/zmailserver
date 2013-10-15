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
package org.zmail.qa.selenium.projects.desktop.tests.addressbook.contacts;


import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.*;

public class TagContact extends AjaxCommonTest  {
	public TagContact() {
		logger.info("New "+ TagContact.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage = app.zPageAddressbook;

		super.startingAccountPreferences = null;		
		
	}
	
	@Test(	description = "Tag a contact",
			groups = { "smoke" })
	public void TagContact_01() throws HarnessException {

		String firstName = "first" + ZmailSeleniumProperties.getUniqueString();		
		String lastName = "last" + ZmailSeleniumProperties.getUniqueString();
	    String email = "email" +  ZmailSeleniumProperties.getUniqueString() + "@zmail.com";
		//default value for file as is last, first
		String fileAs = lastName + ", " + firstName;
	
        app.zGetActiveAccount().soapSend(
                "<CreateContactRequest xmlns='urn:zmailMail'>" +
                "<cn fileAsStr='" + fileAs + "' >" +
                "<a n='firstName'>" + firstName +"</a>" +
                "<a n='lastName'>" + lastName +"</a>" +
                "<a n='email'>" + email + "</a>" +               
                "</cn>" +            
                "</CreateContactRequest>");

        
        ContactItem contactItem = ContactItem.importFromSOAP(app.zGetActiveAccount(), "FIELD[lastname]:" + lastName + "");
        
        // Refresh the view, to pick up the new contact
        FolderItem contactFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), "Contacts");
        GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
        app.zTreeContacts.zTreeItem(Action.A_LEFTCLICK, contactFolder);
                 
        // Select the item
        app.zPageAddressbook.zListItem(Action.A_LEFTCLICK, contactItem.fileAs); // contactItem.fileAs);

	    String tagName = "tag"+ ZmailSeleniumProperties.getUniqueString();
		
		// Click new tag
		DialogTag dialogTag = (DialogTag) app.zPageAddressbook.zToolbarPressPulldown(Button.B_TAG, Button.O_TAG_NEWTAG);
		dialogTag.zSetTagName(tagName);
		dialogTag.zClickButton(Button.B_OK);		
				
	
		// Make sure the tag was created on the server (get the tag ID)
		app.zGetActiveAccount().soapSend("<GetTagRequest xmlns='urn:zmailMail'/>");;
		String tagID = app.zGetActiveAccount().soapSelectValue("//mail:GetTagResponse//mail:tag[@name='"+ tagName +"']", "id");

		// Make sure the tag was applied to the contact
		app.zGetActiveAccount().soapSend(
					"<GetContactsRequest xmlns='urn:zmailMail'>" +
						"<cn id='"+ contactItem.getId() +"'/>" +
					"</GetContactsRequest>");
		
		String contactTags = app.zGetActiveAccount().soapSelectValue("//mail:GetContactsResponse//mail:cn", "t");
		 
		ZAssert.assertEquals(contactTags, tagID, "Verify the tag appears on the contact id=" +  contactItem.getId());
		
		//verify toasted message '1 contact tagged ...'
        Toaster toast = app.zPageMain.zGetToaster();
        String toastMsg = toast.zGetToastMessage();
        ZAssert.assertStringContains(toastMsg, "1 contact tagged \"" + tagName + "\"", "Verify toast message '" + "1 contact tagged \"" + tagName + "\"'" );
 
  
   	}
	
	@Test(   description = "Tag a local contact",
	      groups = { "smoke" })
	public void TagLocalContact() throws HarnessException {

	   String firstName = "first" + ZmailSeleniumProperties.getUniqueString();      
	   String lastName = "last" + ZmailSeleniumProperties.getUniqueString();
	   String email = "email" +  ZmailSeleniumProperties.getUniqueString() + "@zmail.com";
	   //default value for file as is last, first
	   String fileAs = lastName + ", " + firstName;

	   app.zGetActiveAccount().soapSend(
	         "<CreateContactRequest xmlns='urn:zmailMail'>" +
	         "<cn fileAsStr='" + fileAs + "' >" +
	         "<a n='firstName'>" + firstName +"</a>" +
	         "<a n='lastName'>" + lastName +"</a>" +
	         "<a n='email'>" + email + "</a>" +               
	         "</cn>" +            
	         "</CreateContactRequest>",
	         SOAP_DESTINATION_HOST_TYPE.CLIENT,
	         ZmailAccount.clientAccountName);

	        
	   ContactItem contactItem = ContactItem.importFromSOAP(
	         app.zGetActiveAccount(),
	         "FIELD[lastname]:" + lastName + "",
	         SOAP_DESTINATION_HOST_TYPE.CLIENT,
	         ZmailAccount.clientAccountName);

	   // Refresh the view, to pick up the new contact
	   FolderItem contactFolder = FolderItem.importFromSOAP(
	         app.zGetActiveAccount(),
	         "Contacts",
	         SOAP_DESTINATION_HOST_TYPE.CLIENT,
	         ZmailAccount.clientAccountName);

	   app.zTreeContacts.zTreeItem(Action.A_LEFTCLICK, contactFolder);

	   // Select the item
	   app.zPageAddressbook.zListItem(Action.A_LEFTCLICK, contactItem.fileAs); // contactItem.fileAs);

	   String tagName = "tag"+ ZmailSeleniumProperties.getUniqueString();

	   // Click new tag
	   DialogTag dialogTag = (DialogTag) app.zPageAddressbook.zToolbarPressPulldown(
	         Button.B_TAG, Button.O_TAG_NEWTAG);
	   dialogTag.zSetTagName(tagName);
	   dialogTag.zClickButton(Button.B_OK);      

	   //verify toasted message '1 contact tagged ...'
      Toaster toast = app.zPageMain.zGetToaster();
      String toastMsg = toast.zGetToastMessage();
      ZAssert.assertStringContains(toastMsg,
            "1 contact tagged \"" + tagName + "\"",
            "Verify toast message '" + "1 contact tagged \"" + tagName + "\"'" );

      // Make sure the tag was created on the server (get the tag ID)
	   app.zGetActiveAccount().soapSend("<GetTagRequest xmlns='urn:zmailMail'/>",
	         SOAP_DESTINATION_HOST_TYPE.CLIENT, ZmailAccount.clientAccountName);
	   String tagID = app.zGetActiveAccount().soapSelectValue("//mail:GetTagResponse//mail:tag[@name='"+ tagName +"']", "id");

	   // Make sure the tag was applied to the contact
	   app.zGetActiveAccount().soapSend(
	         "<GetContactsRequest xmlns='urn:zmailMail'>" +
	         "<cn id='"+ contactItem.getId() +"'/>" +
	         "</GetContactsRequest>",
	         SOAP_DESTINATION_HOST_TYPE.CLIENT,
	         ZmailAccount.clientAccountName);

	   String contactTags = app.zGetActiveAccount().soapSelectValue(
	         "//mail:GetContactsResponse//mail:cn",
	         "t");

	   ZAssert.assertEquals(contactTags, tagID,
	         "Verify the tag appears on the contact id=" +  contactItem.getId());

	}
  	
}

