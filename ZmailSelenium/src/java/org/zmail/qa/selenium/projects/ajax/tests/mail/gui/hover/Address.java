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
package org.zmail.qa.selenium.projects.ajax.tests.mail.gui.hover;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.PrefGroupMailByMessageTest;
import org.zmail.qa.selenium.projects.ajax.ui.TooltipContact;
import org.zmail.qa.selenium.projects.ajax.ui.mail.DisplayMail;


public class Address extends PrefGroupMailByMessageTest {

	
	public Address() {
		logger.info("New "+ Address.class.getCanonicalName());
		
	}
	
	@Test(	description = "Hover over GAL address",
			groups = { "functional" })
	public void Address_01() throws HarnessException {
		
		//-- Data Setup
		
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
		
		// AccountA (in the GAL) sends a message to the test account
		ZmailAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<su>"+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>body" + ZmailSeleniumProperties.getUniqueString() +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");
		


		//-- GUI steps
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail display = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		// Hover over the From field
		display.zHoverOver(DisplayMail.Field.From);

		
		
		//-- GUI Verification
		
		TooltipContact tooltip = new TooltipContact(app.zPageMail);
		tooltip.zWaitForActive();
		
		ZAssert.assertTrue(tooltip.zIsActive(), "Verify the tooltip shows");
		
		
	}

	@Test(	description = "Hover over External (non-GAL) address",
			groups = { "functional" })
	public void Address_02() throws HarnessException {
		
		//-- Data Setup
		
		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
		FolderItem folder = FolderItem.importFromSOAP(app.zGetActiveAccount(), FolderItem.SystemFolder.Inbox);
		
		// AccountA (in the GAL) sends a message to the test account
		app.zGetActiveAccount().soapSend(
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
		


		//-- GUI steps
		
		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the message so that it shows in the reading pane
		DisplayMail display = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		// Hover over the From field
		display.zHoverOver(DisplayMail.Field.From);

		
		
		//-- GUI Verification
		
		TooltipContact tooltip = new TooltipContact(app.zPageMail);
		tooltip.zWaitForActive();
		
		ZAssert.assertTrue(tooltip.zIsActive(), "Verify the tooltip shows");
		
	}


}
