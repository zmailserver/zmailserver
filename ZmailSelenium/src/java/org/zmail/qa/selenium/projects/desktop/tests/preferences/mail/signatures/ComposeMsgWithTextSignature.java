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
package com.zimbra.qa.selenium.projects.desktop.tests.preferences.mail.signatures;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.MailItem;
import com.zimbra.qa.selenium.framework.items.RecipientItem;
import com.zimbra.qa.selenium.framework.items.SignatureItem;

import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;

import com.zimbra.qa.selenium.framework.util.GeneralUtility;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount.SOAP_DESTINATION_HOST_TYPE;
import com.zimbra.qa.selenium.projects.desktop.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.desktop.ui.mail.FormMailNew;

public class ComposeMsgWithTextSignature extends AjaxCommonTest {
   String sigName = "signame" + ZimbraSeleniumProperties.getUniqueString();
   String sigBody = "sigbody" + ZimbraSeleniumProperties.getUniqueString();

   @SuppressWarnings("serial")
   public ComposeMsgWithTextSignature() {
      super.startingPage = app.zPageMail;
      super.startingAccountPreferences = new HashMap<String, String>() {
         {
            put("zimbraPrefComposeFormat", "text");
         }
      };
   }

   private void _createSignature(ZimbraAccount account) throws HarnessException {
      account.authenticate(
            SOAP_DESTINATION_HOST_TYPE.SERVER);
      account.soapSend(
            "<CreateSignatureRequest xmlns='urn:zimbraAccount'>"
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
    * Test case : Create signature through soap Compose text message and add
    * text signature Send mail to self and verify signature through soap.
    * 
    * @throws HarnessException
    */
   @Test(description = " Compose Msg with text signature and Verify signature thropugh soap", groups = { "functional" })
   public void ComposeMsgWithTextSignature_01() throws HarnessException {

      _createSignature(app.zGetActiveAccount());

      // Signature is created
      SignatureItem signature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), this.sigName);
      ZAssert.assertEquals(signature.getName(), this.sigName,"verified Text Signature is created");

      // Create the message data to be sent
      MailItem mail = new MailItem();
      mail.dToRecipients.add(new RecipientItem(app.zGetActiveAccount()));
      mail.dSubject = "subject" + ZimbraSeleniumProperties.getUniqueString();
      mail.dBodyText = "body" + ZimbraSeleniumProperties.getUniqueString();

      // Open the new mail form
      FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
      ZAssert.assertNotNull(mailform, "Verify the new form opened");

      // Fill out the form with the data
      mailform.zFill(mail);
       //click Signature drop down and add signature
      app.zPageMail.zToolbarPressPulldown(Button.B_SIGNATURE,Button.O_ADD_SIGNATURE,this.sigName);
   
      // Send the message
      mailform.zSubmit();
      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      MailItem received = MailItem.importFromSOAP(app.zGetActiveAccount(),"in:inbox subject:(" + mail.dSubject + ")");

      logger.debug("===========received is: " + received);
      logger.debug("===========app is: " + app);
      
      //Verify TO, Subject, Body,Signature

      ZAssert.assertEquals(received.dFromRecipient.dEmailAddress, app.zGetActiveAccount().EmailAddress,"Verify the from field is correct");
      ZAssert.assertEquals(received.dToRecipients.get(0).dEmailAddress,app.zGetActiveAccount().EmailAddress,"Verify the to field is correct");
      ZAssert.assertEquals(received.dSubject, mail.dSubject,"Verify the subject field is correct");
      ZAssert.assertStringContains(received.dBodyText, mail.dBodyText,"Verify the body content is correct");
      ZAssert.assertStringContains(received.dBodyText, this.sigBody,"Verify the signature is correct");

   }
}
