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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.mail.signatures;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.SignatureItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;

import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;

import org.zmail.qa.selenium.framework.util.XmlStringUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew.Field;

public class ForwardMsgWithHtmlSignature extends AjaxCommonTest {
	String sigName = "signame" + ZmailSeleniumProperties.getUniqueString();
	String sigBody = "signature<b>bold"+ ZmailSeleniumProperties.getUniqueString() + "</b>signature";
	String contentHTMLSig = XmlStringUtil.escapeXml("<html>" + "<head></head>"
			+ "<body>" + sigBody + "</body>" + "</html>");

	@SuppressWarnings("serial")
	public ForwardMsgWithHtmlSignature() {
		super.startingPage = app.zPageMail;
		super.startingAccountPreferences = new HashMap<String, String>() {
			{
				put("zmailPrefComposeFormat", "html");
				put("zmailPrefGroupMailBy", "message");
			}
		};
	}

	@BeforeClass(groups = { "always" })
	public void CreateSignature() throws HarnessException {
		System.out.println(this.sigName);
		ZmailAccount.AccountZWC().authenticate(
				SOAP_DESTINATION_HOST_TYPE.SERVER);
		ZmailAccount.AccountZWC().soapSend(
				"<CreateSignatureRequest xmlns='urn:zmailAccount'>"
				+ "<signature name='" + this.sigName + "' >"
				+ "<content type='text/html'>'" + this.contentHTMLSig
				+ "'</content>" + "</signature>"
				+ "</CreateSignatureRequest>");


	}

	/**
	 * Test case : Forward Msg with html signature and Verify signature through soap
	 * Create signature through soap 
	 * Send message with html signature through soap
	 * Fwd same message to another account say (accountB())
	 * Verify html signature in forwarded msg through soap
	 * @throws HarnessException
	 */
	@Test(description = "Forward Msg with html signature and Verify html signature through soap", groups = { "functional" })
	public void ForwardMsgWithHtmlSignature_01() throws HarnessException {

		SignatureItem signature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), this.sigName);
		logger.info(signature.dBodyHtmlText);
		FolderItem inboxFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(),SystemFolder.Inbox);
		ZAssert.assertEquals(signature.getName(), this.sigName,"verified Text Signature is created");

		String subject = "subject"+ ZmailSeleniumProperties.getUniqueString();
		String bodyText = "text" + ZmailSeleniumProperties.getUniqueString();
		String bodyHTML = "text <b>bold"+ ZmailSeleniumProperties.getUniqueString() +"</b> text";
		String contentHTML = XmlStringUtil.escapeXml("<html>" + "<head></head>"
				+ "<body>" + bodyHTML + "<br></br>" + "</body>" + "</html>");
		String signatureContent = XmlStringUtil.escapeXml("<html>"
				+ "<head></head>" + "<body>" + signature.dBodyHtmlText
				+ "</body>" + "</html>");



		// Send a message to the account with html signature
		ZmailAccount.AccountZWC().soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
				"<m>" +
				"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
				"<su>"+ subject +"</su>" +
				"<mp ct='multipart/alternative'>" +
				"<mp ct='text/plain'>" +
				"<content>"+ bodyText+"</content>" +
				"</mp>" +
				"<mp ct='text/html'>" +
				"<content>"+contentHTML+signatureContent+"\n</content>" +
				"</mp>" +
				"</mp>" +
				"</m>" +
		"</SendMsgRequest>");


		// Get the mail item for the new message
		MailItem mail = MailItem.importFromSOAP(ZmailAccount.AccountZWC(),"in:inbox subject:(" + subject + ")");

		// Click Get Mail button
		app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);
		app.zTreeMail.zTreeItem(Action.A_LEFTCLICK, inboxFolder);

		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

		// Forward the item
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_FORWARD);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");

		// Fill out the form with the data
		mailform.zFillField(Field.To, ZmailAccount.AccountB().EmailAddress);

		// Send the message
		mailform.zSubmit();

		ZmailAccount.AccountB().soapSend(
				"<SearchRequest xmlns='urn:zmailMail' types='message'>"
				+ "<query>in:inbox subject:(" + mail.dSubject + ")</query>" + "</SearchRequest>");

		String id = ZmailAccount.AccountB().soapSelectValue("//mail:SearchResponse/mail:m", "id");

		ZmailAccount.AccountB().soapSend(
				"<GetMsgRequest xmlns='urn:zmailMail'>" + "<m id='" + id
				+ "' html='1'/>" + "</GetMsgRequest>");
		Element getMsgResponse = ZmailAccount.AccountB().soapSelectNode("//mail:GetMsgResponse", 1);

		MailItem received = MailItem.importFromSOAP(getMsgResponse);

		logger.debug("===========received is: " + received);
		logger.debug("===========app is: " + app);

		//Verify TO, Fwd'ed Subject, HtmlBody,HtmlSignature
		ZAssert.assertStringContains(received.dSubject, "Fwd", "Verify the subject field contains the 'Fwd' prefix");
		ZAssert.assertEquals(received.dFromRecipient.dEmailAddress, app.zGetActiveAccount().EmailAddress,"Verify the from field is correct");
		ZAssert.assertEquals(received.dToRecipients.get(0).dEmailAddress,ZmailAccount.AccountB().EmailAddress,"Verify the to field is correct");
		ZAssert.assertStringContains(received.dBodyHtml.toLowerCase(), bodyHTML,"Verify html body content is correct");
		ZAssert.assertStringContains(received.dBodyHtml.toLowerCase(), this.sigBody,"Verify html signature is correct");

	}
}

