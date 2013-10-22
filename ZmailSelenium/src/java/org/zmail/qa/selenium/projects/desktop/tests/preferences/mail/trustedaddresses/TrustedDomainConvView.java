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
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.MailItem;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.LmtpInject;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.preferences.trustedaddresses.DisplayTrustedAddress;

public class TrustedDomainConvView extends AjaxCommonTest {


   @SuppressWarnings("serial")
   public TrustedDomainConvView() throws HarnessException {
      super.startingPage = app.zPageMail;

      // Make sure we are using an account with message view
      super.startingAccountPreferences = new HashMap<String, String>() {
         {
            put("zmailPrefGroupMailBy", "conversation");
            put("zmailPrefMessageViewHtmlPreferred", "TRUE");
         }
      };
   }

   @SuppressWarnings("serial")
   private void _setUserLevelPreference(ZmailAccount account) throws HarnessException {
      // This is necessary for ZDesktop because the other preferences are set on desktop client,
      // while this preference has to be set on the user level
      Map<String, String> userLevelPreferences = new HashMap<String, String>() {
         {
            put("zmailPrefMailTrustedSenderList", "testdoamin.com");
         }
      };

      account.modifyPreferences(userLevelPreferences);

      // Refresh is needed by synching ZD to ZCS, then reload the page by logging out,
      // then relaunch ZD
      GeneralUtility.syncDesktopToZcsWithSoap(account);
      app.zPageMail.zWaitForDesktopLoadingSpinner(5000);

      app.zPageLogin.zNavigateTo();
      super.startingPage.zNavigateTo();
   }

   /**
    * TestCase : Trusted Domain with conversation view
    * 1.Set domain in Preference/Mail/Trusted Addresses and verify it through soap(GetPrefsRequest)
    * 2.In conversation View Inject mail with external image
    * 3.Verify To,From,Subject through soap 
    * 4.Click on same mail
    * 5.Yellow color Warning msg Info bar should not present for trusted domain
    * @throws HarnessException
    */
   @Test(description = "Verify Display Image link in Trusted doamin for conversation view", groups = { "smoke" })
   public void TrustedDomainConvView_01() throws HarnessException {
      _setUserLevelPreference(app.zGetActiveAccount());

      final String subject = "TestTrustedAddress";
      final String from = "admintest@testdoamin.com";
      final String to = "admin@testdoamin.com";
      final String mimeFolder = ZmailSeleniumProperties.getBaseDirectory()
            + "/data/public/mime/ExternalImg.txt";

      //Verify domain through soap- GetPrefsRequest
      String PrefMailTrustedAddr = app.zGetActiveAccount().getPreference(
            "zmailPrefMailTrustedSenderList");
      ZAssert.assertTrue(PrefMailTrustedAddr.equals("testdoamin.com"),
            "Verify doamin is present /Pref/TrustedAddr");
      
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

      // Verify Warning info bar with other links

      ZAssert
            .assertFalse(actual.zHasWDDLinks("conversation"),
                  "Verify Warning icon ,Display Image and Domain link  does not present");

   }

   @AfterMethod(alwaysRun=true)
   public void cleanUp() {
      ZmailAccount.ResetAccountZDC();
   }

}
