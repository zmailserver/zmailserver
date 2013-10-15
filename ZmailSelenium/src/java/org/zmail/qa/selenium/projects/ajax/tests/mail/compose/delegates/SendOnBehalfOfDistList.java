/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.qa.selenium.projects.ajax.tests.mail.compose.delegates;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class SendOnBehalfOfDistList extends PrefGroupMailByMessageTest {


	public SendOnBehalfOfDistList() {
		logger.info("New "+ SendOnBehalfOfDistList.class.getCanonicalName());
		
		
		

		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		
	}
	
	@Test(	description = "Send On Behalf Of Distribution List",
			groups = { "smoke" })
	public void SendOnBehalfOfDistList_01() throws HarnessException {
		
		//-- Data Setup
		
		// Mail data
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
		
		// The grantor
		ZmailDistributionList list = new ZmailDistributionList();
		list.provision();

		// Add a member
		list.addMember(ZmailAccount.AccountA());
		
		// Grant send rights
		list.grantRight(app.zGetActiveAccount(), "sendOnBehalfOfDistList");

				
		// Login to load the rights
		app.zPageLogin.zNavigateTo();
		this.startingPage.zNavigateTo();
		
		
		//-- GUI Steps
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFillField(Field.To, ZmailAccount.AccountA().EmailAddress);
		mailform.zFillField(Field.Subject, subject);
		mailform.zFillField(Field.Body, "body" + ZmailSeleniumProperties.getUniqueString());
		mailform.zFillField(Field.From, list.EmailAddress);	
		mailform.zSubmit();
	

		
		//-- Data verification
		
		ZmailAccount.AccountA().soapSend(
				"<SearchRequest xmlns='urn:zmailMail' types='message'>"
			+		"<query>subject:("+ subject +")</query>"
			+	"</SearchRequest>");
		String id = ZmailAccount.AccountA().soapSelectValue("//mail:m", "id");

		ZmailAccount.AccountA().soapSend(
				"<GetMsgRequest xmlns='urn:zmailMail' >"
			+		"<m id='"+ id +"'/>"
			+	"</GetMsgRequest>");


		// Verify From: grantor
		String from = ZmailAccount.AccountA().soapSelectValue("//mail:e[@t='f']", "a");
		ZAssert.assertEquals(from, list.EmailAddress, "Verify From: grantor");
		
		// Verify Sender: active account
		String sender = ZmailAccount.AccountA().soapSelectValue("//mail:e[@t='s']", "a");
		ZAssert.assertEquals(sender, app.zGetActiveAccount().EmailAddress, "Verify Sender: active account");
		
	}

	
}
