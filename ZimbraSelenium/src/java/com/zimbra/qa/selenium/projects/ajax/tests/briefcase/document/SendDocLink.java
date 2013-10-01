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
package com.zimbra.qa.selenium.projects.ajax.tests.briefcase.document;

import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.items.DocumentItem;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.GeneralUtility;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.XmlStringUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.core.FeatureBriefcaseTest;
import com.zimbra.qa.selenium.projects.ajax.ui.DialogWarning;
import com.zimbra.qa.selenium.projects.ajax.ui.briefcase.DialogConfirm;
import com.zimbra.qa.selenium.projects.ajax.ui.mail.FormMailNew;

public class SendDocLink extends FeatureBriefcaseTest {

	public SendDocLink() {
		logger.info("New " + SendDocLink.class.getCanonicalName());

		super.startingPage = app.zPageBriefcase;

		super.startingAccountPreferences.put("zimbraPrefComposeFormat", "html");
		super.startingAccountPreferences.put("zimbraPrefBriefcaseReadingPaneLocation", "bottom");				
	}

	@Test(description = "Create document through SOAP - click Send Link, Cancel & verify through GUI", groups = { "functional" })
	public void SendDocLink_01() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create document item
		DocumentItem docItem = new DocumentItem();

		String docName = docItem.getName();
		String docText = docItem.getDocText();

		// Create document using SOAP
		String contentHTML = XmlStringUtil.escapeXml("<html>" + "<body>"
				+ docText + "</body>" + "</html>");

		account
				.soapSend("<SaveDocumentRequest requestId='0' xmlns='urn:zimbraMail'>"
						+ "<doc name='"
						+ docName
						+ "' l='"
						+ briefcaseFolder.getId()
						+ "' ct='application/x-zimbra-doc'>"
						+ "<content>"
						+ contentHTML
						+ "</content>"
						+ "</doc>"
						+ "</SaveDocumentRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		SleepUtil.sleepVerySmall();

		// Click on created document
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, docItem);

		// Click on Send Link
		DialogConfirm confDlg;
		if (ZimbraSeleniumProperties.zimbraGetVersionString().contains("7.1."))
			confDlg = (DialogConfirm) app.zPageBriefcase
			.zToolbarPressPulldown(Button.B_SEND, Button.O_SEND_LINK, docItem);
		else
			confDlg = (DialogConfirm) app.zPageBriefcase.zToolbarPressPulldown(
					Button.B_ACTIONS, Button.O_SEND_LINK, docItem);
	
		// Click Yes on confirmation dialog
		FormMailNew mailform = (FormMailNew) confDlg.zClickButton(Button.B_YES);

		// Verify the new mail form is opened
		ZAssert.assertTrue(mailform.zIsActive(), "Verify the new form opened");

		// Verify link
		boolean visible = mailform.zWaitForIframeText(
				"css=iframe[id*=_content_ifr]", docName);
		ZAssert.assertTrue(visible,"Verify the link text");

		// Cancel the message
		// A warning dialog should appear regarding losing changes
		DialogWarning warningDlg = (DialogWarning) mailform
				.zToolbarPressButton(Button.B_CANCEL);

		// temporary: check if dialog exists since it was implemented recently on send link 
		if (warningDlg.zIsActive()) {
			// Dismiss the dialog
			warningDlg.zClickButton(Button.B_NO);

			// Make sure the dialog is dismissed
			warningDlg.zWaitForClose();
		}

		// delete document upon test completion
		app.zPageBriefcase.deleteFileByName(docItem.getName());
	}

	@Test(description = "Send document link using Right Click Context Menu & verify through GUI", groups = { "functional" })
	public void SendDocLink_02() throws HarnessException {
		ZimbraAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create document item
		DocumentItem docItem = new DocumentItem();

		String docName = docItem.getName();
		String docText = docItem.getDocText();

		// Create document using SOAP
		String contentHTML = XmlStringUtil.escapeXml("<html>" + "<body>"
				+ docText + "</body>" + "</html>");

		account
				.soapSend("<SaveDocumentRequest requestId='0' xmlns='urn:zimbraMail'>"
						+ "<doc name='"
						+ docName
						+ "' l='"
						+ briefcaseFolder.getId()
						+ "' ct='application/x-zimbra-doc'>"
						+ "<content>"
						+ contentHTML
						+ "</content>"
						+ "</doc>"
						+ "</SaveDocumentRequest>");

		//SleepUtil.sleepVerySmall();
		
		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		SleepUtil.sleepVerySmall();

		// Click on created document
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, docItem);

		// Click on Send Link using Right Click Context Menu
		DialogConfirm confDlg = (DialogConfirm) app.zPageBriefcase.zListItem(
				Action.A_RIGHTCLICK, Button.O_SEND_LINK, docItem);

		// Click Yes on confirmation dialog
		FormMailNew mailform = (FormMailNew) confDlg.zClickButton(Button.B_YES);

		// Verify the new mail form is opened
		ZAssert.assertTrue(mailform.zIsActive(), "Verify the new form opened");

		// Verify link
		ZAssert.assertTrue(mailform.zWaitForIframeText(
				"css=iframe[id*=_content_ifr]", docName),
				"Verify the link text");

		// Cancel the message
		// A warning dialog should appear regarding losing changes
		DialogWarning warningDlg = (DialogWarning) mailform
				.zToolbarPressButton(Button.B_CANCEL);

		// temporary: check if dialog exists since it was implemented recently on send link 
		if (warningDlg.zIsActive()) {
			// Dismiss the dialog
			warningDlg.zClickButton(Button.B_NO);

			// Make sure the dialog is dismissed
			warningDlg.zWaitForClose();
		}

		// delete document upon test completion
		app.zPageBriefcase.deleteFileByName(docItem.getName());
	}

}
