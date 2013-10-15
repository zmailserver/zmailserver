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
package org.zmail.qa.selenium.projects.ajax.tests.login;

import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.framework.util.staf.StafServicePROCESS;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;



public class Login extends AjaxCommonTest {
	
	public Login() {
		logger.info("New "+ Login.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageLogin;
		super.startingAccountPreferences = null;
		
	}
	
	@Test(	description = "Login to the Ajax Client",
			groups = { "functional" })
	public void Login01() throws HarnessException {
		
		// Login
		app.zPageLogin.zLogin(ZmailAccount.AccountZWC());
		
		// Verify main page becomes active
		ZAssert.assertTrue(app.zPageMain.zIsActive(), "Verify that the account is logged in");
		
	}

	@Test(	description = "Login to the Ajax Client, with a mounted folder",
			groups = { "functional" })
	public void Login02() throws HarnessException {
		
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String mountpointname = "mountpoint" + ZmailSeleniumProperties.getUniqueString();
		
		FolderItem inbox = FolderItem.importFromSOAP(ZmailAccount.AccountA(), FolderItem.SystemFolder.Inbox);
		ZAssert.assertNotNull(inbox, "Verify other account's inbox exists");
		
		// Create a folder to share
		ZmailAccount.AccountA().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername + "' l='" + inbox.getId() + "'/>"
				+	"</CreateFolderRequest>");
		
		FolderItem folder = FolderItem.importFromSOAP(ZmailAccount.AccountA(), foldername);
		ZAssert.assertNotNull(folder, "Verify other account's folder is created");

		// Share it
		ZmailAccount.AccountA().soapSend(
					"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ folder.getId() +"' op='grant'>"
				+			"<grant d='"+ ZmailAccount.AccountZWC().EmailAddress +"' gt='usr' perm='r'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		
		

		
		////
		// For some reason, the message doesn't appear if we use AddMsgRequest, so
		// instead use SendMsgRequest from AccountB() and move the message.
		////
		
		// Add a message to it
		ZmailAccount.AccountB().soapSend(
					"<SendMsgRequest xmlns='urn:zmailMail'>"
				+		"<m>"
				+			"<e t='t' a='"+ ZmailAccount.AccountA().EmailAddress +"'/>"
				+			"<su>"+ subject +"</su>"
				+			"<mp ct='text/plain'>"
				+				"<content>"+ "body" + ZmailSeleniumProperties.getUniqueString() +"</content>"
				+			"</mp>"
				+		"</m>"
				+	"</SendMsgRequest>");
		
		MailItem mail = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ subject +")");
		ZAssert.assertNotNull(mail, "Verify other account's mail is created");

		ZmailAccount.AccountA().soapSend(
						"<MsgActionRequest xmlns='urn:zmailMail'>" 
					+		"<action id='"+ mail.getId() +"' op='move' l='"+ folder.getId() +"'/>"
					+	"</MsgActionRequest>");
		
		// Mount it
		ZmailAccount.AccountZWC().soapSend(
					"<CreateMountpointRequest xmlns='urn:zmailMail'>"
				+		"<link l='1' name='"+ mountpointname +"'  rid='"+ folder.getId() +"' zid='"+ ZmailAccount.AccountA().ZmailId +"'/>"
				+	"</CreateMountpointRequest>");
		
		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(ZmailAccount.AccountZWC(), mountpointname);
		ZAssert.assertNotNull(mountpoint, "Verify active account's mountpoint is created");
		
		// Login
		app.zPageLogin.zLogin(ZmailAccount.AccountZWC());
		
		// Verify main page becomes active
		ZAssert.assertTrue(app.zPageMain.zIsActive(), "Verify that the account is logged in");
		
	}

	@Bugs(	ids = "59847")
	@Test(	description = "Login to the Ajax Client, with a mounted folder of a deleted account",
			groups = { "functional" })
	public void Login03() throws HarnessException {
		
		// Create Account2
		ZmailAccount account = new ZmailAccount();
		account.provision();
		account.authenticate();
		
		
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
		String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
		String mountpointname = "mountpoint" + ZmailSeleniumProperties.getUniqueString();
		
		FolderItem inbox = FolderItem.importFromSOAP(account, FolderItem.SystemFolder.Inbox);
		ZAssert.assertNotNull(inbox, "Verify other account's inbox exists");
		
		// Create a folder to share
		account.soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + foldername + "' l='" + inbox.getId() + "'/>"
				+	"</CreateFolderRequest>");
		
		FolderItem folder = FolderItem.importFromSOAP(account, foldername);
		ZAssert.assertNotNull(folder, "Verify other account's folder is created");

		// Share it
		account.soapSend(
					"<FolderActionRequest xmlns='urn:zmailMail'>"
				+		"<action id='"+ folder.getId() +"' op='grant'>"
				+			"<grant d='"+ ZmailAccount.AccountZWC().EmailAddress +"' gt='usr' perm='r'/>"
				+		"</action>"
				+	"</FolderActionRequest>");
		

		
		// Add a message to it
		account.soapSend(
					"<AddMsgRequest xmlns='urn:zmailMail'>"
        		+		"<m l='"+ folder.getId() +"' >"
            	+			"<content>From: foo@foo.com\n"
            	+				"To: foo@foo.com \n"
            	+				"Subject: "+ subject +"\n"
            	+				"MIME-Version: 1.0 \n"
            	+				"Content-Type: text/plain; charset=utf-8 \n"
            	+				"Content-Transfer-Encoding: 7bit\n"
            	+				"\n"
            	+				"simple text string in the body\n"
            	+			"</content>"
            	+		"</m>"
				+	"</AddMsgRequest>");
		
		MailItem mail = MailItem.importFromSOAP(account, "subject:("+ subject +")");
		ZAssert.assertNotNull(mail, "Verify other account's mail is created");

		
		// Mount it
		ZmailAccount.AccountZWC().soapSend(
					"<CreateMountpointRequest xmlns='urn:zmailMail'>"
				+		"<link l='1' name='"+ mountpointname +"'  rid='"+ folder.getId() +"' zid='"+ account.ZmailId +"'/>"
				+	"</CreateMountpointRequest>");
		
		FolderMountpointItem mountpoint = FolderMountpointItem.importFromSOAP(ZmailAccount.AccountZWC(), mountpointname);
		ZAssert.assertNotNull(mountpoint, "Verify active account's mountpoint is created");
		
		// Delete other account
		ZmailAdminAccount.GlobalAdmin().soapSend(
					"<DeleteAccountRequest xmlns='urn:zmailAdmin'>"
				+		"<id>"+ account.ZmailId +"</id>"
				+	"</DeleteAccountRequest>");
		
		
		// Login
		app.zPageLogin.zLogin(ZmailAccount.AccountZWC());
		
		// Verify main page becomes active
		ZAssert.assertTrue(app.zPageMain.zIsActive(), "Verify that the account is logged in");
		
		
	}

	@DataProvider(name = "DataProvider_zmailMailURL")
	public Object[][] DataProvider_zmailMailURL() {
		  return new Object[][] {
				    new Object[] { "", null },
				    new Object[] { "/", null },
				    new Object[] { "/foobar", null },
				    new Object[] { "/foobar/", null },
				  };
		}

	@Bugs(	ids = "66788")
	@Test(	description = "Change the zmailMailURL and login",
			groups = { "inprogress" },
			dataProvider = "DataProvider_zmailMailURL")
	public void Login04(String zmailMailURLtemp, String notused) throws HarnessException {
		
		String zmailMailURL = null;

		// Need to do a try/finally to make sure the old setting works
		try {
			
			// Get the original zmailMailURL value
			ZmailAdminAccount.GlobalAdmin().soapSend(
						"<GetConfigRequest xmlns='urn:zmailAdmin'>"
					+		"<a n='zmailMailURL'/>"
					+	"</GetConfigRequest>");
			zmailMailURL = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:a[@n='zmailMailURL']", null);
			
			// Change to the new zmailMailURL temp value
			ZmailAdminAccount.GlobalAdmin().soapSend(
					"<ModifyConfigRequest xmlns='urn:zmailAdmin'>"
				+		"<a n='zmailMailURL'>"+ zmailMailURLtemp + "</a>"
				+	"</ModifyConfigRequest>");

			StafServicePROCESS staf = new StafServicePROCESS();
			staf.execute("zmmailboxdctl restart");

			// Wait for the service to come up
			SleepUtil.sleep(60000);
			
			staf.execute("zmcontrol status");

			
			// Open the login page
			// (use the base URL, since leftovers from the previous test may affect the URL)
			app.zPageLogin.sOpen(ZmailSeleniumProperties.getBaseURL());
			
			// Login
			app.zPageLogin.zLogin(ZmailAccount.AccountZWC());		
			
			// Verify main page becomes active
			ZAssert.assertTrue(app.zPageMain.zIsActive(), "Verify that the account is logged in");

			
		} finally {
			
			if ( zmailMailURL != null ) {
				
				// Delete any authToken/SessionID
				app.zPageLogin.sDeleteAllVisibleCookies();
				
				// Change the URL back to the original
				ZmailAdminAccount.GlobalAdmin().soapSend(
						"<ModifyConfigRequest xmlns='urn:zmailAdmin'>"
					+		"<a n='zmailMailURL'>"+ zmailMailURL + "</a>"
					+	"</ModifyConfigRequest>");
				
				StafServicePROCESS staf = new StafServicePROCESS();
				staf.execute("zmmailboxdctl restart");

				// Wait for the service to come up
				SleepUtil.sleep(60000);
				
				staf.execute("zmcontrol status");

				
				// Open the base URL
				app.zPageLogin.sOpen(ZmailSeleniumProperties.getBaseURL());

			}

		}
		
		
		
	}


}
