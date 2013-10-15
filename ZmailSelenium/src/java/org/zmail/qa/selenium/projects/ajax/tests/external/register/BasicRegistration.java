/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012, 2013 VMware, Inc.
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
package org.zmail.qa.selenium.projects.ajax.tests.external.register;

import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;



public class BasicRegistration extends AjaxCommonTest {
	
	public BasicRegistration() {
		logger.info("New "+ BasicRegistration.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageLogin;
		super.startingAccountPreferences = null;
		
	}
	
	@Test(	description = "Register as and external user",
			groups = { "smoke" })
	public void BasicRegistration_01() throws HarnessException {
		
		
		
		//-- Data Setup
		
		
		ZmailExternalAccount external = new ZmailExternalAccount();
		external.setEmailAddress("external" + ZmailSeleniumProperties.getUniqueString() + "@example.com");
		
		FolderItem inbox = FolderItem.importFromSOAP(ZmailAccount.AccountZWC(), FolderItem.SystemFolder.Inbox);
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();

		// Create a subfolder in Inbox
		ZmailAccount.AccountZWC().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername +"' l='" + inbox.getId() +"' view='message'/>"
				+	"</CreateFolderRequest>");
		String folderid = ZmailAccount.AccountZWC().soapSelectValue("//mail:folder", "id");

		// Share the subfolder
		ZmailAccount.AccountZWC().soapSend(
					"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ folderid +"' op='grant'>"
				+			"<grant d='"+ external.EmailAddress +"' inh='1' gt='guest' pw='' perm='r'/>"
				+		"</action>"
				+	"</FolderActionRequest>");

		// Send the notification
		ZmailAccount.AccountZWC().soapSend(
					"<SendShareNotificationRequest xmlns='urn:zmailMail'>"
				+		"<item id='"+ folderid +"'/>"
				+		"<e a='"+ external.EmailAddress +"'/>"
				+		"<notes/>"
				+	"</SendShareNotificationRequest>");


		// Parse the URL From the sent message
		ZmailAccount.AccountZWC().soapSend(
					"<SearchRequest xmlns='urn:zmailMail' types='message'>"
				+		"<query>in:sent "+ external.EmailAddress +"</query>"
				+	"</SearchRequest>");
		String messageid = ZmailAccount.AccountZWC().soapSelectValue("//mail:m", "id");
		
		ZmailAccount.AccountZWC().soapSend(
					"<GetMsgRequest xmlns='urn:zmailMail'>"
				+		"<m id='"+ messageid +"' html='1'/>"
				+	"</GetMsgRequest>");
		
		// Based on the content of the sent message, the URL's can be determined
		Element response = ZmailAccount.AccountZWC().soapSelectNode("//mail:GetMsgResponse", 1);
		external.setURL(response);
		
		
		
		//-- GUI Actions
		
		
		// Navigate to the registration page
		app.zPageExternalRegistration.zSetURL(external.getRegistrationURL());
		app.zPageExternalRegistration.zNavigateTo();
		app.zPageExternalRegistration.zLogin(external);

		
		
		//-- Verification
		
		
		// After logging in, make sure the page appears correctly
		app.zPageExternalMain.zWaitForActive();
		boolean loaded = app.zPageExternalMain.zIsActive();
		ZAssert.assertTrue(loaded, "Verify that the main page became active");
		
		
	}


}
