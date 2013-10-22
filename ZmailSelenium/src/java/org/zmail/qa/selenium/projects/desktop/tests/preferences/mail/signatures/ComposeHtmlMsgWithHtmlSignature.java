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

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.RecipientItem;
import org.zmail.qa.selenium.framework.items.SignatureItem;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.OperatingSystem;
import org.zmail.qa.selenium.framework.util.XmlStringUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.OperatingSystem.OsType;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.mail.FormMailNew;


public class ComposeHtmlMsgWithHtmlSignature extends AjaxCommonTest {
   String sigName = "signame" + ZmailSeleniumProperties.getUniqueString();
   String sigCore = "bold" + ZmailSeleniumProperties.getUniqueString();
   String sigBody = "Signature<strong>"+ sigCore + "</strong>Signature";
   String contentHTML = XmlStringUtil.escapeXml("<html>" + "<head></head>"
         + "<body>" + sigBody + "</body>" + "</html>");

   @SuppressWarnings("serial")
   public ComposeHtmlMsgWithHtmlSignature() {
      super.startingPage = app.zPageMail;
      super.startingAccountPreferences = new HashMap<String, String>() {
         {
            put("zmailPrefComposeFormat", "html");
         }
      };
   }

   private void _createSignature(ZmailAccount account) throws HarnessException {
      account.authenticate(SOAP_DESTINATION_HOST_TYPE.SERVER);
      account.soapSend(
            "<CreateSignatureRequest xmlns='urn:zmailAccount'>"
            + "<signature name='" + this.sigName + "' >"
            + "<content type='text/html'>'" + this.contentHTML
            + "'</content>" + "</signature>"
            + "</CreateSignatureRequest>");

      // Refresh is needed by synching ZD to ZCS, then reload the page by logging out,
      // then relaunch ZD
      GeneralUtility.syncDesktopToZcsWithSoap(account);
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      app.zPageLogin.zNavigateTo();
      super.startingPage.zNavigateTo();
   }

   /**
    * Test case : Create html signature through soap
    * Compose html message and add html signature 
    * Send mail to self and verify signature through soap. 
    * @throws HarnessException
    */
   @Test(description = " Compose Html Msg with html signature and Verify signature thropugh soap", groups = { "functional" })
   public void ComposeHtmlMsgWithHtmlSignature_01() throws HarnessException {

      _createSignature(app.zGetActiveAccount());

      // Signature is created
      SignatureItem signature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), this.sigName);
      ZAssert.assertEquals(signature.getName(), this.sigName,"verified Text Signature is created");

      // Create the message data to be sent
      MailItem mail = new MailItem();
      mail.dToRecipients.add(new RecipientItem(app.zGetActiveAccount()));
      mail.dSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
      mail.dBodyHtml = "body <strong>bold"+ ZmailSeleniumProperties.getUniqueString()+"</strong>body";

      // Open the new mail form
      FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
      ZAssert.assertNotNull(mailform, "Verify the new form opened");

      // Fill out the form with the data
      mailform.zFill(mail, sigCore);

      //click Signature drop down and add signature
      app.zPageMail.zToolbarPressPulldown(Button.B_SIGNATURE,Button.O_ADD_SIGNATURE,this.sigName);

      // Add signature     
      // app.zPageMail.zClick("css=td[id*='_title']td:contains('"+ this.sigName + "')");

      // Send the message
      mailform.zSubmit();

      // Please don't change this to GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount())
      // because in linux when switching the iframe to top application, it selects the browser toolbar
      // and it will cause syncDesktop through SOAP request to fail.
      app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

      app.zGetActiveAccount().soapSend(
            "<SearchRequest xmlns='urn:zmailMail' types='message'>"
            + "<query>in:inbox subject:(" + mail.dSubject + ")</query>" + "</SearchRequest>");

      String id = app.zGetActiveAccount().soapSelectValue("//mail:SearchResponse/mail:m", "id");

      app.zGetActiveAccount().soapSend(
            "<GetMsgRequest xmlns='urn:zmailMail'>" + "<m id='" + id
            + "' html='1'/>" + "</GetMsgRequest>");
      Element getMsgResponse = app.zGetActiveAccount().soapSelectNode("//mail:GetMsgResponse", 1);
      MailItem received = MailItem.importFromSOAP(getMsgResponse);

      // Verify TO, Subject,html Body,html signature

      ZAssert.assertEquals(received.dFromRecipient.dEmailAddress, app.zGetActiveAccount().EmailAddress,"Verify the from field is correct");
      ZAssert.assertEquals(received.dToRecipients.get(0).dEmailAddress,app.zGetActiveAccount().EmailAddress,"Verify the to field is correct");
      ZAssert.assertEquals(received.dSubject, mail.dSubject,"Verify the subject field is correct");
      ZAssert.assertStringContains(received.dBodyHtml, mail.dBodyHtml,"Verify the body content is correct");
      ZAssert.assertStringContains(received.dBodyHtml, this.sigBody,"Verify the signature is correct");

   }

   @AfterMethod(alwaysRun=true)
   public void cleanup() throws HarnessException {
      // To close the selected browser toolbar dropdownlist due to switching the iframe
      // to top application in linux, typing <ESC> keys few times on the page is required
      if (OperatingSystem.getOSType() == OsType.LINUX ||
            OperatingSystem.getOSType() == OsType.MAC) {
         app.zPageMain.zKeyboardTypeString("<ESC>");
         app.zPageMain.zKeyboardTypeString("<ESC>");
         app.zPageMain.zKeyboardTypeString("<ESC>");
         app.zPageMain.zKeyboardTypeString("<ESC>");
      }
   }
}
