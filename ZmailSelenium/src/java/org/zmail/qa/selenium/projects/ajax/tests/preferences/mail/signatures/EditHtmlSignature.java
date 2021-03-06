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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import org.zmail.qa.selenium.framework.items.SignatureItem;

import org.zmail.qa.selenium.framework.ui.Action;

import org.zmail.qa.selenium.framework.util.HarnessException;

import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.XmlStringUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.signature.FormSignatureNew;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.signature.PageSignature;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.signature.FormSignatureNew.Field;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.signature.PageSignature.Locators;



public class EditHtmlSignature extends AjaxCommonTest {
	String sigName = "signame" + ZmailSeleniumProperties.getUniqueString();
	String bodyHTML = "text<strong>bold"+ ZmailSeleniumProperties.getUniqueString() + "</strong>text";
	String contentHTML = XmlStringUtil.escapeXml("<html>" + "<head></head>"
			+ "<body>" + bodyHTML + "</body>" + "</html>");

	public EditHtmlSignature() throws HarnessException {
		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = null;

	}

	/**
	 * Added @beforeClass because after logged in ,when we try to create
	 * signature through soap, it doesn't shows in(GUI) 'Pref/signatures' unless and
	 * until we refresh browser.
	 * 
	 * @throws HarnessException
	 */
	@BeforeClass(groups = { "always" })
	public void CreateHtmlSignature() throws HarnessException {
		System.out.println(this.sigName);
		ZmailAccount.AccountZWC().authenticate(SOAP_DESTINATION_HOST_TYPE.SERVER);
		ZmailAccount.AccountZWC().soapSend(
				"<CreateSignatureRequest xmlns='urn:zmailAccount'>"
				+ "<signature name='" + this.sigName + "' >"
				+ "<content type='text/html'>'" + this.contentHTML
				+ "'</content>" + "</signature>"
				+ "</CreateSignatureRequest>");
	}

	/**
	 * Test case : Create html signature through soap then Edit and verify
	 * edited html signature through soap
	 * 
	 * @throws HarnessException
	 */

	@Test(description = "Edit signature through GUI and verify through soap", groups = { "smoke" })
	public void EditHtmlSignature_01() throws HarnessException {

		String sigEditName = "editsigname"+ ZmailSeleniumProperties.getUniqueString();
		String editbodyHTML = "edittextbold"+ ZmailSeleniumProperties.getUniqueString() + "text";

		// HTML Signature is created
		SignatureItem signature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), this.sigName);
		ZAssert.assertEquals(signature.getName(), this.sigName,"verified Html Signature name ");

		// Click on Mail/signature
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK,TreeItem.MailSignatures);
		SleepUtil.sleepSmall();

		PageSignature pagesig = new PageSignature(app);

		//Select created signature signature 
		pagesig.zClick(Locators.zSignatureListView);
		app.zPageSignature.zClick("//td[contains(text(),'"+signature.getName()+"')]");

		//Verify Body contents
		String signaturebodytext = pagesig.zGetHtmlSignatureBody();
		ZAssert.assertStringContains(signaturebodytext, this.bodyHTML,"Verify the html signature body");

		FormSignatureNew signew = new FormSignatureNew(app);

		// Edit signame and sigbody
		signew.zFillField(Field.SignatureName, sigEditName);
		signew.zFillField(Field.SignatureHtmlBody, editbodyHTML);
		signew.zSubmit();

		SignatureItem editsignature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), sigEditName);

		//Verify signature name and body contents
		ZAssert.assertEquals(editsignature.getName(),sigEditName,"Verify Edited signature name");
		ZAssert.assertStringContains(editsignature.dBodyHtmlText,editbodyHTML,"Verify Edited Html signature body");
		ZAssert.assertStringDoesNotContain(editsignature.getName(), this.sigName, "Verify after edit 1st signature  does not present");

	}

}
