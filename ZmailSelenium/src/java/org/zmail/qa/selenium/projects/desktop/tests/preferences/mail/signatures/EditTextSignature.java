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
package org.zmail.qa.selenium.projects.desktop.tests.preferences.mail.signatures;

import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.SignatureItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.preferences.TreePreferences.TreeItem;
import org.zmail.qa.selenium.projects.desktop.ui.preferences.signature.FormSignatureNew;
import org.zmail.qa.selenium.projects.desktop.ui.preferences.signature.PageSignature;
import org.zmail.qa.selenium.projects.desktop.ui.preferences.signature.FormSignatureNew.Field;
import org.zmail.qa.selenium.projects.desktop.ui.preferences.signature.PageSignature.Locators;

public class EditTextSignature extends AjaxCommonTest {
   String sigName = "signame" + ZmailSeleniumProperties.getUniqueString();
   String sigBody = "sigbody" + ZmailSeleniumProperties.getUniqueString();

   public EditTextSignature() throws HarnessException {

      super.startingPage = app.zPagePreferences;
      super.startingAccountPreferences = null;

   }

   private void _createSignature(ZmailAccount account) throws HarnessException {
      account.authenticate(SOAP_DESTINATION_HOST_TYPE.SERVER);
      account.soapSend(
            "<CreateSignatureRequest xmlns='urn:zmailAccount'>"
            + "<signature name='" + this.sigName + "' >"
            + "<content type='text/plain'>" + this.sigBody
            + "</content>" + "</signature>"
            + "</CreateSignatureRequest>");

      // Refresh is needed by synching ZD to ZCS, then reload the page by logging out,
      // then relaunch ZD
      GeneralUtility.syncDesktopToZcsWithSoap(account);
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      app.zPageLogin.zNavigateTo();
      super.startingPage.zNavigateTo();
   }

   /**
    * Test case : 
    * Create signature through soap 
    * Edit it through GUI and Verify edit text signature through soap
    * @throws HarnessException
    */
   @Test(description = " Edit and verify text signature through soap", groups = { "smoke" })
   public void EditTextSignature_01() throws HarnessException {

      _createSignature(app.zGetActiveAccount());

      String sigEditName = "editsigname"+ ZmailSeleniumProperties.getUniqueString();
      String sigEditBody = "editsigbody"+ ZmailSeleniumProperties.getUniqueString();

      //Signature is created
      SignatureItem signature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), this.sigName);
      ZAssert.assertEquals(signature.getName(), this.sigName, "verified Text Signature is created");

      //Click on Mail/signature
      app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK,TreeItem.MailSignatures);
      SleepUtil.sleepSmall();
      FormSignatureNew signew = new FormSignatureNew(app);

      //Select signature which is to be edit
      signew.zClick(Locators.zSignatureListView);
      signew.zClick("//td[contains(text(),'"+signature.getName()+"')]");

      //Verify Body contents
      PageSignature pagesig = new PageSignature(app);
      String signaturebodytext = pagesig.zGetSignatureBodyText();
      ZAssert.assertStringContains(signaturebodytext, this.sigBody,"Verify the text signature body");


      //Edit signame and sigbody
      signew.zFillField(Field.SignatureName, sigEditName);
      signew.zFillField(Field.SignatureBody, sigEditBody);
      signew.zSubmit();

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      SignatureItem editsignature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), sigEditName);

      //Verify Edited signature name and body contents
      ZAssert.assertEquals(editsignature.getName(),sigEditName,"Verify Edited signature name");
      ZAssert.assertEquals(editsignature.dBodyText,sigEditBody,"Verify Edited text signature body");
      ZAssert.assertStringDoesNotContain(editsignature.getName(), this.sigName, "Verify after edit 1st signature  does not present");

   }

}
