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
package org.zmail.qa.selenium.projects.ajax.tests.mail.compose.personas;

import org.testng.annotations.*;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class FromAlias extends PrefGroupMailByMessageTest {

	private String AliasEmailAddress = null;
	private String AliasFromDisplay = null;

	public FromAlias() {
		logger.info("New "+ FromAlias.class.getCanonicalName());
		
		super.startingAccountPreferences.put("zmailPrefComposeFormat", "text");
		
	}
	
	@BeforeMethod( groups = { "always" } )
	public void addAliasToActiveAccount() throws HarnessException {
		
		AliasFromDisplay = "alias" + ZmailSeleniumProperties.getUniqueString();
		AliasEmailAddress = AliasFromDisplay + 
					"@" +
					ZmailSeleniumProperties.getStringProperty("testdomain", "testdomain.com");
		
		String identity = "identity" + ZmailSeleniumProperties.getUniqueString();
		
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<AddAccountAliasRequest xmlns='urn:zmailAdmin'>"
			+		"<id>"+ app.zGetActiveAccount().ZmailId +"</id>"
			+		"<alias>"+ AliasEmailAddress +"</alias>"
			+	"</AddAccountAliasRequest>");
		
		app.zGetActiveAccount().soapSend(
				" <CreateIdentityRequest xmlns='urn:zmailAccount'>"
			+		"<identity name='"+ identity +"'>"
			+			"<a name='zmailPrefIdentityName'>"+ identity +"</a>"
			+			"<a name='zmailPrefFromDisplay'>"+ AliasFromDisplay +"</a>"
			+			"<a name='zmailPrefFromAddress'>"+ AliasEmailAddress +"</a>"
			+			"<a name='zmailPrefReplyToEnabled'>FALSE</a>"
			+			"<a name='zmailPrefReplyToDisplay'/>"
			+			"<a name='zmailPrefDefaultSignatureId'/>"
			+			"<a name='zmailPrefForwardReplySignatureId'/>"
			+			"<a name='zmailPrefWhenSentToEnabled'>FALSE</a>"
			+			"<a name='zmailPrefWhenInFoldersEnabled'>FALSE</a>"
			+		"</identity>"
			+	"</CreateIdentityRequest>");
		
		// Logout and login to pick up the changes
		app.zPageLogin.zNavigateTo();
		this.startingPage.zNavigateTo();
		
	}

	@Test(	description = "Send a mail using an alias as From",
			groups = { "functional" })
	public void FromAlias_01() throws HarnessException {
		
		
		
		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFillField(Field.From, AliasEmailAddress);
		mailform.zFillField(Field.To, ZmailAccount.AccountA().EmailAddress);
		mailform.zFillField(Field.Subject, subject);
		mailform.zFillField(Field.Body, "content" + ZmailSeleniumProperties.getUniqueString());
		
		// Send the message
		mailform.zSubmit();

		
		
		// Verify the message shows as from the alias
		ZmailAccount.AccountA().soapSend(
					"<SearchRequest types='message' xmlns='urn:zmailMail'>"
			+			"<query>subject:("+ subject +")</query>"
			+		"</SearchRequest>");
		String id = ZmailAccount.AccountA().soapSelectValue("//mail:m", "id");

		ZmailAccount.AccountA().soapSend(
					"<GetMsgRequest xmlns='urn:zmailMail'>"
			+			"<m id='"+ id +"' html='1'/>"
			+		"</GetMsgRequest>");

		// Verify From: alias
		String address = ZmailAccount.AccountA().soapSelectValue("//mail:e[@t='f']", "a");
		ZAssert.assertEquals(address, AliasEmailAddress, "Verify the from is the alias email address");
		
		// Verify no headers contain active account
		Element[] nodes = ZmailAccount.AccountA().soapSelectNodes("//mail:e");
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
