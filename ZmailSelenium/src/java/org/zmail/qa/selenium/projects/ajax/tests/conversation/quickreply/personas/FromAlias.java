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
package org.zmail.qa.selenium.projects.ajax.tests.conversation.quickreply.personas;

import java.util.List;

import org.testng.annotations.*;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.*;
import org.zmail.qa.selenium.projects.ajax.ui.mail.*;


public class FromAlias extends PrefGroupMailByConversationTest {

	public FromAlias() {
		logger.info("New "+ FromAlias.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		
	}
	
	@Bugs(ids = "73698")
	@Test(	description = "Send a quick reply with alias as from",
			groups = { "functional" })
	public void FromAlias_01() throws HarnessException {
		
		
		//-- Data setup
		
		String aliasFromDisplay = "alias" + ZmailSeleniumProperties.getUniqueString();
		String aliasEmailAddress = aliasFromDisplay + 
					"@" +
					ZmailSeleniumProperties.getStringProperty("testdomain", "testdomain.com");
		
		
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<AddAccountAliasRequest xmlns='urn:zmailAdmin'>"
			+		"<id>"+ app.zGetActiveAccount().ZmailId +"</id>"
			+		"<alias>"+ aliasEmailAddress +"</alias>"
			+	"</AddAccountAliasRequest>");
		
		// Modify the from address in the primary identity
		app.zGetActiveAccount().soapSend("<GetIdentitiesRequest xmlns='urn:zmailAccount' />");
		String identity = app.zGetActiveAccount().soapSelectValue("//acct:identity", "id");
		
		app.zGetActiveAccount().soapSend(
				" <ModifyIdentityRequest  xmlns='urn:zmailAccount'>"
			+		"<identity id='"+ identity +"'>"
			+			"<a name='zmailPrefFromDisplay'>"+ aliasFromDisplay +"</a>"
			+			"<a name='zmailPrefFromAddress'>"+ aliasEmailAddress +"</a>"
			+		"</identity>"
			+	"</ModifyIdentityRequest >");
		
		// Send a message to the account to create the conversation
		ZmailAccount account1 = new ZmailAccount();
		account1.provision();
		account1.authenticate();
		
		
		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String content = "content" + ZmailSeleniumProperties.getUniqueString();
		String reply = "quickreply" + ZmailSeleniumProperties.getUniqueString();
		
		account1.soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>"+ content +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");

		
		
		//-- GUI steps
		
		
		// Logout and login to pick up the changes
		app.zPageLogin.zNavigateTo();
		this.startingPage.zNavigateTo();

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		
		// Select the conversation
		DisplayConversation display = (DisplayConversation)app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		
		// Get the first mesage
		List<DisplayConversationMessage> messages = display.zListGetMessages();
				
		// Quick Reply
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_REPLY);
		messages.get(0).zFillField(DisplayMail.Field.Body, reply);
		messages.get(0).zPressButton(Button.B_QUICK_REPLY_SEND);
	

		
		//-- Verification
		
		
		// Verify the message shows as from the alias
		account1.soapSend(
					"<SearchRequest types='message' xmlns='urn:zmailMail'>"
			+			"<query>subject:("+ subject +")</query>"
			+		"</SearchRequest>");
		String id = account1.soapSelectValue("//mail:m", "id");

		account1.soapSend(
					"<GetMsgRequest xmlns='urn:zmailMail'>"
			+			"<m id='"+ id +"' html='1'/>"
			+		"</GetMsgRequest>");

		// Verify From: alias
		String address = account1.soapSelectValue("//mail:e[@t='f']", "a");
		ZAssert.assertEquals(address, aliasEmailAddress, "Verify the from is the alias email address");
		
		// Verify no headers contain active account
		Element[] nodes = account1.soapSelectNodes("//mail:e");
		for (Element e : nodes) {
			String attr = e.getAttribute("a", null);
			if ( attr != null ) {
				ZAssert.assertStringDoesNotContain(
						attr, 
						app.zGetActiveAccount().EmailAddress, 
						"Verify no headers contain the active account email address");
			}
		}

	}


}
