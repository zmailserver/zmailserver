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
import org.testng.annotations.Test;
import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.items.SignatureItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.XmlStringUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.ZmailAccount.SOAP_DESTINATION_HOST_TYPE;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.mail.FormMailNew;


public class ReplyMsgWithHtmlSignature extends AjaxCommonTest {
   String sigName = "signame" + ZmailSeleniumProperties.getUniqueString();
   String sigBody = "Signature<strong>bold"+ ZmailSeleniumProperties.getUniqueString() + "</strong>Signature";
   String contentHTMLSig = XmlStringUtil.escapeXml("<html>" + "<head></head>"
         + "<body>" + sigBody + "</body>" + "</html>");

   @SuppressWarnings("serial")
   public ReplyMsgWithHtmlSignature() {
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
            + "<content type='text/html'>'" + this.contentHTMLSig
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
    * Test case : Reply Msg with html signature and Verify signature through soap
    * Create signature through soap 
    * Send message with html signature through soap
    * Reply same message.
    * Verify html signature in Replied msg through soap
    * @throws HarnessException
    */
   @Test(description = " Reply Msg with html signature and Verify signature through soap", groups = { "functional" })
   public void ReplyMsgWithHtmlSignature_01() throws HarnessException {
      _createSignature(app.zGetActiveAccount());

      //Verify Signature
      SignatureItem signature = SignatureItem.importFromSOAP(app.zGetActiveAccount(), this.sigName);
      ZAssert.assertEquals(signature.getName(), this.sigName,"verified Text Signature is created");

      String subject = "subject" + ZmailSeleniumProperties.getUniqueString();
      String bodyText = "text" + ZmailSeleniumProperties.getUniqueString();
      String bodyHTML = "text <strong>bold"
         + ZmailSeleniumProperties.getUniqueString() + "</strong> text";
      String contentHTML = XmlStringUtil.escapeXml("<html>" + "<head></head>"
            + "<body>" + bodyHTML + "<br></br>" + "</body>" + "</html>");
      String signatureContent = XmlStringUtil.escapeXml("<html>"
            + "<head></head>" + "<body>" + signature.dBodyHtmlText
            + "</body>" + "</html>");



      // Send a message to the account
      app.zGetActiveAccount().soapSend(
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

      SleepUtil.sleepSmall();

      // Get the mail item for the new message
      MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(),"in:inbox subject:(" + subject + ")");

      // Click Get Mail button
      app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

      // Select the item
      app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

      // Forward the item
      FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_REPLY);
      ZAssert.assertNotNull(mailform, "Verify the new form opened");

      SleepUtil.sleepSmall();

      // Send the message
      mailform.zSubmit();
      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      app.zGetActiveAccount().soapSend(
            "<SearchRequest xmlns='urn:zmailMail' types='message'>"
            + "<query>in:inbox subject:(" +mail.dSubject+ ")</query>" + "</SearchRequest>");

      String id = app.zGetActiveAccount().soapSelectValue("//mail:SearchResponse/mail:m", "id");

      app.zGetActiveAccount().soapSend(
            "<GetMsgRequest xmlns='urn:zmailMail'>" + "<m id='" + id
            + "' html='1'/>" + "</GetMsgRequest>");
      Element getMsgResponse = app.zGetActiveAccount().soapSelectNode("//mail:GetMsgResponse", 1);
      MailItem received = MailItem.importFromSOAP(getMsgResponse);

      logger.debug("===========received is: " + received);
      logger.debug("===========app is: " + app);

      //Verify TO, Reply'ed Subject, HtmlBody,HtmlSignature
      ZAssert.assertStringContains(received.dSubject, "Re", "Verify the subject field contains the 'Re' prefix");
      ZAssert.assertEquals(received.dFromRecipient.dEmailAddress, app.zGetActiveAccount().EmailAddress,"Verify the from field is correct");
      ZAssert.assertEquals(received.dToRecipients.get(0).dEmailAddress,app.zGetActiveAccount().EmailAddress,"Verify the to field is correct");
      ZAssert.assertStringContains(received.dBodyHtml, bodyHTML,"Verify the body content is correct");
      ZAssert.assertStringContains(received.dBodyHtml, this.sigBody,"Verify the signature is correct");

   }
}
