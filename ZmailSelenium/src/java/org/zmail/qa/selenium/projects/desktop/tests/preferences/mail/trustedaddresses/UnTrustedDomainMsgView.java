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
package org.zmail.qa.selenium.projects.desktop.tests.preferences.mail.trustedaddresses;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.LmtpInject;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.preferences.trustedaddresses.DisplayTrustedAddress;

public class UnTrustedDomainMsgView extends AjaxCommonTest {

   @SuppressWarnings("serial")
   public UnTrustedDomainMsgView() throws HarnessException {
      super.startingPage = app.zPageMail;

      // Make sure we are using an account with message view
      super.startingAccountPreferences = new HashMap<String, String>() {
         {
            put("zmailPrefGroupMailBy", "message");
            put("zmailPrefMessageViewHtmlPreferred", "TRUE");

         }
      };
   }
/**
 * TestCase : UnTrusted Domain with Message view
 * 1.Don't add any domain in Preference/Mail/Trusted Addresses
 * 2.In Message View Inject mail with external image
 * 3.Verify To,From,Subject through soap
 * 4.Click on same mail
 * 5.Yellow color Warning Msg Info bar should show warning icon with 'Display Image' and Domain  link for untrusted domains.
 * 
 * @throws HarnessException
 */
   @Test(description = "Verify Display Image link in UnTrusted doamin for message view", groups = { "smoke" })
   public void UnTrustedDomainMsgView_01() throws HarnessException {

      final String subject = "TestTrustedAddress";
      final String from = "admintest@testdoamin.com";
      final String to = "admin@testdoamin.com";
      final String mimeFolder = ZmailSeleniumProperties.getBaseDirectory()
            + "/data/public/mime/ExternalImg.txt";

      // Inject the external image message(s)
      LmtpInject.injectFile(app.zGetActiveAccount().EmailAddress, new File(mimeFolder));

      MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(),subject);
      
      ZAssert.assertNotNull(mail, "Verify message is received");
      ZAssert.assertEquals(from, mail.dFromRecipient.dEmailAddress,"Verify the from matches");
      ZAssert.assertEquals(to, mail.dToRecipients.get(0).dEmailAddress,"Verify the to address");

      // Click Get Mail button
      app.zPageMail.zToolbarPressButton(Button.B_GETMAIL);

      // Select the message so that it shows in the reading pane
      app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
      
      DisplayTrustedAddress actual = new DisplayTrustedAddress(app);
      
      //Verify Warning info bar with other links
      //ZAssert.assertTrue(actual.zDisplayImageLink("message").equals(""),"Verify Display Image link is present");
      ZAssert.assertTrue(actual.zHasWDDLinks("message"),"Verify Display Image,Domain link  and warning icon are present");

   }

   @AfterMethod(alwaysRun=true)
   public void cleanUp() {
      ZmailAccount.ResetAccountZDC();
   }
}
