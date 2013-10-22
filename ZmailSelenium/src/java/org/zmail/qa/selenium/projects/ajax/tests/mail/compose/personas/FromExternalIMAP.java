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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.compose.personas;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;


public class FromExternalIMAP extends PrefGroupMailByMessageTest {

	public FromExternalIMAP() {
		logger.info("New "+ FromExternalIMAP.class.getCanonicalName());
		
	}
	
	@Test(	description = "Compose message from - External IMAP",
			groups = { "smoke" })
	public void FromExternalIMAP_01() throws HarnessException {
		
		
		// Create the external data source on the same server
		ZimbraAccount external = new ZimbraAccount();
		external.provision();
		external.authenticate();
		

		// Create the folder to put the data source
		String foldername = "external" + ZimbraSeleniumProperties.getUniqueString();
		
		app.zGetActiveAccount().soapSend(
				"<CreateFolderRequest xmlns='urn:zimbraMail'>" +
                	"<folder name='"+ foldername +"' l='1'/>" +
                "</CreateFolderRequest>");

		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), foldername);
		ZAssert.assertNotNull(folder, "Verify the subfolder is available");
		
		// Create the data source
		String datasourcename = "datasource" + ZimbraSeleniumProperties.getUniqueString();
		String datasourceHost = ZimbraSeleniumProperties.getStringProperty("server.host");
		String datasourceImapPort = ZimbraSeleniumProperties.getStringProperty("server.imap.port");
		String datasourceImapType = ZimbraSeleniumProperties.getStringProperty("server.imap.type");
		
		app.zGetActiveAccount().soapSend(
				"<CreateDataSourceRequest xmlns='urn:zimbraMail'>"
			+		"<imap name='"+ datasourcename +"' l='"+ folder.getId() +"' isEnabled='true' "
			+			"port='"+ datasourceImapPort +"' host='"+ datasourceHost +"' connectionType='"+ datasourceImapType +"' leaveOnServer='true' "
			+			"username='"+ external.EmailAddress +"' password='"+ external.Password +"' />"
			+	"</CreateDataSourceRequest>");

		
		
		// Need to logout/login to get the new folder
		app.zPageLogin.zNavigateTo();
		startingPage.zNavigateTo();
		

		
		// Create the message data to be sent
		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();
		
		
		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");
		
		// Fill out the form with the data
		mailform.zFillField(Field.From, external.EmailAddress);
		mailform.zFillField(Field.To, ZimbraAccount.AccountA().EmailAddress);
		mailform.zFillField(Field.Subject, subject);
		mailform.zFillField(Field.Body, "content" + ZimbraSeleniumProperties.getUniqueString());
		
		// Send the message
		mailform.zSubmit();

		
		
		// Verify the message shows as from the alias
		ZimbraAccount.AccountA().soapSend(
					"<SearchRequest types='message' xmlns='urn:zimbraMail'>"
			+			"<query>subject:("+ subject +")</query>"
			+		"</SearchRequest>");
		String id = ZimbraAccount.AccountA().soapSelectValue("//mail:m", "id");

		ZimbraAccount.AccountA().soapSend(
					"<GetMsgRequest xmlns='urn:zimbraMail'>"
			+			"<m id='"+ id +"' html='1'/>"
			+		"</GetMsgRequest>");

		/**
		 * Since we are using Zimbra to Zimbra to set up the external
		 * account, the MTA knows both accounts and allows the
		 * Zimbra user to send from the External account directly,
		 * without the OBO.
		 * 
		 * Due to limitations in the WDC (5/22/2012), external
		 * accounts cannot be set up with third party servers.
		 * (Maybe a stand alone Zimbra server may be used.)
		 * 
		 * If this test case is executed with a third party
		 * server, you should see:
		 * From: Zimbra
		 * OBO: External
		 * 
		 */
		
		// Verify From: alias
		String address = ZimbraAccount.AccountA().soapSelectValue("//mail:e[@t='f']", "a");
		ZAssert.assertEquals(address, app.zGetActiveAccount().EmailAddress, "In the Zimbra-Zimbra config, verify the from is the Zimbra email address");

		
	}	




}
