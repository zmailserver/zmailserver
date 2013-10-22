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
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.XmlStringUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.signature.FormSignatureNew;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.signature.PageSignature;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.signature.PageSignature.Locators;

public class DeleteHtmlSignature extends AjaxCommonTest {

	String sigHtmlName = "signame" + ZmailSeleniumProperties.getUniqueString();
	String bodyHTML = "text<strong>bold"+ ZmailSeleniumProperties.getUniqueString() + "</strong>text";
	String contentHTML = XmlStringUtil.escapeXml("<html>" + "<head></head>"
			+ "<body>" + bodyHTML + "</body>" + "</html>");

	public DeleteHtmlSignature() throws HarnessException {
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

		ZmailAccount.AccountZWC().authenticate(SOAP_DESTINATION_HOST_TYPE.SERVER);
		ZmailAccount.AccountZWC().soapSend(
				"<CreateSignatureRequest xmlns='urn:zmailAccount'>"
				+ "<signature name='" + this.sigHtmlName + "' >"
				+ "<content type='text/html'>'" + this.contentHTML
				+ "'</content>" + "</signature>"
				+ "</CreateSignatureRequest>");
	}
	/**
	 * Test case :Create Html signature through soap then delete and verify signature through GUI
	 * @Steps:
	 * Create Html signature through soap
	 * Delete signature using delete button.
	 * Verify signature doesn't exist from soap
	 * @throws HarnessException
	 */
	@Test(description = "Delete Html signature using Delete button and verify through soap", groups = { "smoke" })
	public void DeletetHtmlSignature_01() throws HarnessException {

		// Click on Mail/signature
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK,TreeItem.MailSignatures);

		//Verify HTML Signature is created
		SignatureItem signature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), this.sigHtmlName);
		ZAssert.assertEquals(signature.getName(), this.sigHtmlName,"verified Html Signature name ");		

		PageSignature pagesig = new PageSignature(app);
		FormSignatureNew signew = new FormSignatureNew(app);

		//Select created signature signature 
		pagesig.zClick(Locators.zSignatureListView);
		app.zPageSignature.zClick("//td[contains(text(),'"+signature.getName()+"')]");	

		//click Delete button
		app.zPageSignature.zToolbarPressButton(Button.B_DELETE);
		//click Save
		signew.zSubmit();

		// To check whether deleted signature is exist
		app.zGetActiveAccount().soapSend("<GetSignaturesRequest xmlns='urn:zmailAccount'/>");

		String signame = app.zGetActiveAccount().soapSelectValue("//acct:signature[@name='" + this.sigHtmlName + "']","name");
		ZAssert.assertNull(signame, "Verify  signature is deleted");

	}

}
