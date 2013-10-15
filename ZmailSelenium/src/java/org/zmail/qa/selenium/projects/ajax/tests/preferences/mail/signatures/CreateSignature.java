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

import org.testng.annotations.Test;


import org.zmail.qa.selenium.framework.core.ClientSessionFactory;
import org.zmail.qa.selenium.framework.items.SignatureItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.signature.FormSignatureNew;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.signature.FormSignatureNew.Field;


public class CreateSignature extends AjaxCommonTest {
	public CreateSignature() {
		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = null;
	}

	@Test(description = "Create Simple text signature through GUI", groups = { "sanity" })
	public void CreateBasicTextSignature() throws HarnessException {

		String sigName = "signame" + ZmailSeleniumProperties.getUniqueString();
		String sigBody = "sigbody" + ZmailSeleniumProperties.getUniqueString();

		// click on signature from left pane
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK,TreeItem.MailSignatures);

		//Click on New signature button
		FormSignatureNew signew =(FormSignatureNew) app.zPageSignature.zToolbarPressButton(Button.B_NEW);

		// Fill Signature Name and body
		signew.zFillField(Field.SignatureName, sigName);
		signew.zFillField(Field.SignatureBody, sigBody);
		signew.zSubmit();

		SignatureItem signature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), sigName);
		
		//Verify signature name and body content	
		ZAssert.assertEquals(signature.getName(),sigName,"Verify signature Name");
		ZAssert.assertEquals(signature.dBodyText,sigBody,"Verify Text signature body");
	}


	@Test(description = "Create Simple Html signature through GUI", groups = { "sanity" })
	public void CreateBasicHtmlSignature() throws HarnessException {

		String sigName = "signame" + ZmailSeleniumProperties.getUniqueString();
		String sigBody = "sigbody" + ZmailSeleniumProperties.getUniqueString();

		// click on signature from left pane
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK,TreeItem.MailSignatures);

		//Click on New signature button
		FormSignatureNew signew =(FormSignatureNew)app.zPageSignature.zToolbarPressButton(Button.B_NEW);
		
		//select html format from drop down
		signew.zSelectFormat("html");
		//Reason:With "?dev=1&debug=0", Tinymce editor in HTML mode takes more time to load 
		//if(ClientSessionFactory.session().selenium().getEval("window.tinyMCE").equalsIgnoreCase("null")){
			SleepUtil.sleepVeryLong();
		//}
		// Fill Signature Name and body
		signew.zFillField(Field.SignatureName, sigName);
		signew.zFillField(Field.SignatureHtmlBody, sigBody);
		signew.zSubmit();

		SignatureItem signature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), sigName);
		
		//Verify signature name and body contents
		ZAssert.assertEquals(signature.getName(),sigName,"Verify signature Name");
		ZAssert.assertStringContains(signature.dBodyHtmlText, sigBody, "Verify Html signature body");
		//ZAssert.assertEquals(signature.dBodyHtmlText,sigBody,"Verify Html signature body");


	}
}
