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
package org.zmail.qa.selenium.projects.ajax.tests.mail.compose.personas;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class FromExternalPOP extends PrefGroupMailByMessageTest {

	public FromExternalPOP() {
		logger.info("New "+ FromExternalPOP.class.getCanonicalName());
		
	}
	
	@Test(	description = "Compose message from - External POP",
			groups = { "smoke" })
	public void FromExternalPOP_01() throws HarnessException {
		
		
		// Create the external data source on the same server
		ZmailAccount external = new ZmailAccount();
		external.provision();
		external.authenticate();
		

		// Create the folder to put the data source
		String foldername = "external" + ZmailSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zmailMail'>" +
                	"<folder name='"+ foldername +"' l='1'/>" +
                "</CreateFolderRequest>");

		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);
		ZAssert.assertNotNull(folder, "Verify the subfolder is available");
		
		// Create the data source
		String datasourcename = "datasource" + ZmailSeleniumProperties.getUniqueString();
		String datasourceHost = ZmailSeleniumProperties.getStringProperty("server.host");
		String datasourcePopPort = ZmailSeleniumProperties.getStringProperty("server.pop.port");
		String datasourcePopType = ZmailSeleniumProperties.getStringProperty("server.pop.type");
		
		app.zGetActiveAccount().soapSend(
				"<CreateDataSourceRequest xmlns='urn:zmailMail'>"
			+		"<pop3 name='"+ datasourcename +"' l='"+ folder.getId() +"' isEnabled='true' "
			+			"port='"+ datasourcePopPort +"' host='"+ datasourceHost +"' connectionType='"+ datasourcePopType +"' leaveOnServer='true' "
			+			"username='"+ external.EmailAddress +"' password='"+ external.Password +"' />"
			+	"</CreateDataSourceRequest>");

		
		
		// Need to logout/login to get the new folder
		app.zPageLogin.zNavigateTo();
		startingPage.zNavigateTo();
		

		
		// Create the message data to be sent
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFillField(Field.From, external.EmailAddress);
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

		/**
		 * Since we are using Zmail to Zmail to set up the external
		 * account, the MTA knows both accounts and allows the
		 * Zmail user to send from the External account directly,
		 * without the OBO.
		 * 
		 * Due to limitations in the WDC (5/22/2012), external
		 * accounts cannot be set up with third party servers.
		 * (Maybe a stand alone Zmail server may be used.)
		 * 
		 * If this test case is executed with a third party
		 * server, you should see:
		 * From: Zmail
		 * OBO: External
		 * 
		 */
		
		// Verify From: alias
		String address = ZmailAccount.AccountA().soapSelectValue("//mail:e[@t='f']", "a");
		ZAssert.assertEquals(address, app.zGetActiveAccount().EmailAddress, "In the Zmail-Zmail config, verify the from is the Zmail email address");
		
	}	



}
