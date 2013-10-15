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
package org.zmail.qa.selenium.projects.desktop.tests.accounts;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.PageLogin;
import org.zmail.qa.selenium.projects.desktop.ui.accounts.FormAddGmailAccount;
import org.zmail.qa.selenium.projects.desktop.ui.accounts.FormAddImapAccount;
import org.zmail.qa.selenium.projects.desktop.ui.accounts.FormAddPopAccount;
import org.zmail.qa.selenium.projects.desktop.ui.accounts.FormAddYahooAccount;
import org.zmail.qa.selenium.projects.desktop.ui.accounts.FormAddZmailAccount;
import org.zmail.qa.selenium.projects.desktop.ui.accounts.PageAddNewAccount.DROP_DOWN_OPTION;

public class CancelAccount extends AjaxCommonTest {
   public CancelAccount() {
      logger.info("New " + CancelAccount.class.getCanonicalName());
      super.startingPage = app.zPageAddNewAccount;
      super.startingAccountPreferences = null;
   }

   @Test(description="Cancel Zmail Account Creation", groups = { "functional" })
   public void cancelZmailAccountCreation() throws HarnessException {

      FormAddZmailAccount accountForm =
         (FormAddZmailAccount)app.zPageAddNewAccount.zDropDownListSelect(
               DROP_DOWN_OPTION.ZIMBRA);
      accountForm.zCancel();
 
      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();
      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }

   @Test(description="Cancel Gmail Account Creation", groups = { "functional" })
   public void cancelGmailAccountCreation() throws HarnessException {

      FormAddGmailAccount accountForm =
         (FormAddGmailAccount)app.zPageAddNewAccount.zDropDownListSelect(
               DROP_DOWN_OPTION.GMAIL);
      accountForm.zCancel();
 
      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();
      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }

   @Test(description="Cancel Yahoo! Account Creation", groups = { "functional" })
   public void cancelYahooAccountCreation() throws HarnessException {

      FormAddYahooAccount accountForm =
         (FormAddYahooAccount)app.zPageAddNewAccount.zDropDownListSelect(
               DROP_DOWN_OPTION.YAHOO);
      accountForm.zCancel();
 
      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();
      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }

   @Test(description="Cancel IMAP Account Creation", groups = { "functional" })
   public void cancelImapAccountCreation() throws HarnessException {

      FormAddImapAccount accountForm =
         (FormAddImapAccount)app.zPageAddNewAccount.zDropDownListSelect(
               DROP_DOWN_OPTION.IMAP);
      accountForm.zCancel();
 
      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();
      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }

   @Test(description="Cancel MS IMAP Account Creation", groups = { "functional" })
   public void cancelMsImapAccountCreation() throws HarnessException {

      FormAddImapAccount accountForm =
         (FormAddImapAccount)app.zPageAddNewAccount.zDropDownListSelect(
               DROP_DOWN_OPTION.MICROSOFT_EXCHANGE_IMAP);
      accountForm.zCancel();
 
      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();
      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }

   @Test(description="Cancel POP Account Creation", groups = { "functional" })
   public void cancelPopAccountCreation() throws HarnessException {

      FormAddPopAccount accountForm =
         (FormAddPopAccount)app.zPageAddNewAccount.zDropDownListSelect(
               DROP_DOWN_OPTION.POP);
      accountForm.zCancel();
 
      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();
      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }
}
