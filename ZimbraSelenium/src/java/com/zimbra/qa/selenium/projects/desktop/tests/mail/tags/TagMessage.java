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
package com.zimbra.qa.selenium.projects.desktop.tests.mail.tags;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.desktop.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.desktop.ui.DialogTag;

public class TagMessage extends AjaxCommonTest {

	@SuppressWarnings("serial")
	public TagMessage() {
		logger.info("New " + TagMessage.class.getCanonicalName());

		// All tests start at the login page
		super.startingPage = app.zPageMail;

		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zimbraPrefGroupMailBy", "message");
			}
		};

	}

	@Test(description = "Tag a message using Toolbar -> Tag -> New Tag", groups = { "smoke" })
	public void TagMessage_01() throws HarnessException {

		String subject = "subject" + ZimbraSeleniumProperties.getUniqueString();

		// Add a message to the mailbox
		FolderItem inboxFolder = FolderItem.importFromSOAP(app
				.zGetActiveAccount(), SystemFolder.Inbox);
		app.zGetActiveAccount().soapSend(
				"<AddMsgRequest xmlns='urn:zimbraMail'>" + "<m l='"
						+ inboxFolder.getId() + "'>"
						+ "<content>From: foo@foo.com\n" + "To: foo@foo.com \n"
						+ "Subject: " + subject + "\n" + "MIME-Version: 1.0 \n"
						+ "Content-Type: text/plain; charset=utf-8 \n"
						+ "Content-Transfer-Encoding: 7bit\n" + "\n"
						+ "simple text string in the body\n" + "</content>"
						+ "</m>" + "</AddMsgRequest>");

		// Get the message data from SOAP
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(),
				"subject:(" + subject + ")");

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

		String tagName = "tag" + ZimbraSeleniumProperties.getUniqueString();

		// Click new tag
		DialogTag dialogTag = (DialogTag) app.zPageMail.zToolbarPressPulldown(
				Button.B_TAG, Button.O_TAG_NEWTAG);
		dialogTag.zSetTagName(tagName);
		dialogTag.zClickButton(Button.B_OK);

		// Make sure the tag was created on the server (get the tag ID)
		app.zGetActiveAccount().soapSend(
				"<GetTagRequest xmlns='urn:zimbraMail'/>");
		;
		String tagID = app.zGetActiveAccount().soapSelectValue(
				"//mail:GetTagResponse//mail:tag[@name='" + tagName + "']",
				"id");

		// Make sure the tag was applied to the message
		app.zGetActiveAccount().soapSend(
				"<GetMsgRequest xmlns='urn:zimbraMail'>" + "<m id='"
						+ mail.getId() + "'/>" + "</GetMsgRequest>");
		String mailTags = app.zGetActiveAccount().soapSelectValue(
				"//mail:GetMsgResponse//mail:m", "t");

		ZAssert.assertEquals(mailTags, tagID,
				"Verify the tag appears on the message");

	}

}
