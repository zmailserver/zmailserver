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
package org.zmail.qa.selenium.projects.ajax.tests.addressbook.contactgroups;


import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.*;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;

public class SendMailToContactGroup extends AjaxCommonTest  {
	public SendMailToContactGroup() {
		logger.info("New "+ SendMailToContactGroup.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage = app.zPageAddressbook;

		super.startingAccountPreferences = null;		
		
	}
	

	@Test(	description = "Right click then click New Email",
			groups = { "smoke" })
	public void NewEmail() throws HarnessException {

		//--  Data
		
		// The message subject
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
		
		// Create a contact group
		String groupName = "group" + ZmailSeleniumProperties.getUniqueString();
		app.zGetActiveAccount().soapSend(
				"<CreateContactRequest xmlns='urn:zmailMail'>" +
					"<cn >" +
						"<a n='type'>group</a>" +
						"<a n='nickname'>" + groupName +"</a>" +
						"<a n='fileAs'>8:" +  groupName +"</a>" +
				        "<m type='I' value='" + ZmailAccount.AccountA().EmailAddress + "' />" +
				        "<m type='I' value='" + ZmailAccount.AccountB().EmailAddress + "' />" +
					"</cn>" +
				"</CreateContactRequest>");
		
		
		
		//-- GUI
		
		// Refresh
		app.zPageAddressbook.zRefresh();
		
		// Right Click -> New Email
        FormMailNew formMailNew = (FormMailNew) app.zPageAddressbook.zListItem(Action.A_RIGHTCLICK, Button.B_NEW, groupName);        

        formMailNew.zFillField(Field.Subject, subject);
        formMailNew.zFillField(Field.Body, "body"+ ZmailSeleniumProperties.getUniqueString());
        formMailNew.zSubmit();
        
        
        //-- Verification
        
        MailItem message1 = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ subject +")");
        ZAssert.assertNotNull(message1, "Verify the message is received by Account A");

        MailItem message2 = MailItem.importFromSOAP(ZmailAccount.AccountA(), "subject:("+ subject +")");
        ZAssert.assertNotNull(message2, "Verify the message is received by Account B");
        

	}
	

	

}

