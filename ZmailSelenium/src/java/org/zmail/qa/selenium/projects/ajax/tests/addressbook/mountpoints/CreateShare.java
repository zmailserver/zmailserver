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
package org.zmail.qa.selenium.projects.ajax.tests.addressbook.mountpoints;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShare;


public class CreateShare extends AjaxCommonTest  {

	public CreateShare() {
		logger.info("New "+ CreateShare.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageAddressbook;
		super.startingAccountPreferences = null;

	}
	
	@Test(	description = "Share an addressbook - Viewer",
			groups = { "smoke" })
	public void CreateShare_01() throws HarnessException {
		
		String addressbookname = "addressbook" + ZmailSeleniumProperties.getUniqueString();


		// Create an addressbook
		app.zGetActiveAccount().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='" + addressbookname +"' l='1' view='contact'/>"
				+	"</CreateFolderRequest>");

		// Make sure the folder was created on the server
		FolderItem addressbook = FolderItem.importFromSOAP(app.zGetActiveAccount(), addressbookname);
		ZAssert.assertNotNull(addressbook, "Verify the folder exists on the server");

		
		
		//Need to do Refresh by clicking on getmail button to see folder in the list 
		app.zPageAddressbook.zToolbarPressButton(Button.B_REFRESH);
		

		// Right click on folder, select "Share"
		DialogShare dialog = (DialogShare)app.zTreeContacts.zTreeItem(Action.A_RIGHTCLICK, Button.B_SHARE, addressbook);
		ZAssert.assertNotNull(dialog, "Verify the sharing dialog pops up");

		// Use defaults for all options
		dialog.zSetEmailAddress(ZmailAccount.AccountA().EmailAddress);
		
		// Send it
		dialog.zClickButton(Button.B_OK);
		
		// Make sure that AccountA now has the share
		ZmailAccount.AccountA().soapSend(
					"<GetShareInfoRequest xmlns='urn:zmailAccount'>"
				+		"<grantee type='usr'/>"
				+		"<owner by='name'>"+ app.zGetActiveAccount().EmailAddress +"</owner>"
				+	"</GetShareInfoRequest>");
		
		// Example response:
		//    <GetShareInfoResponse xmlns="urn:zmailAccount">
		//		<share granteeId="e3c083c5-102a-416e-bcf4-6d4c59197e20" ownerName="enus13191472607033" granteeDisplayName="enus13191472702505" ownerId="8d5589ff-0548-4562-8d1d-1a4f70e3ca7e" rights="r" folderPath="/folder13191472674374" view="contact" granteeType="usr" ownerEmail="enus13191472607033@testdomain.com" granteeName="enus13191472702505@testdomain.com" folderId="257"/>
	    //	  </GetShareInfoResponse>

		String ownerEmail = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/"+ addressbookname +"']", "ownerEmail");
		ZAssert.assertEquals(ownerEmail, app.zGetActiveAccount().EmailAddress, "Verify the owner of the shared folder");
		
		String rights = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/"+ addressbookname +"']", "rights");
		ZAssert.assertEquals(rights, "r", "Verify the rights are 'read only'");

		String granteeType = ZmailAccount.AccountA().soapSelectValue("//acct:GetShareInfoResponse//acct:share[@folderPath='/"+ addressbookname +"']", "granteeType");
		ZAssert.assertEquals(granteeType, "usr", "Verify the grantee type is 'user'");

	}

	
	

}
