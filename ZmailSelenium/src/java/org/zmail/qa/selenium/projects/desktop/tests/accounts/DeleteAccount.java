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
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.PageLogin;

public class DeleteAccount extends AjaxCommonTest {
   public DeleteAccount() {
      logger.info("New " + DeleteAccount.class.getCanonicalName());

      super.startingPage = app.zPageAddNewAccount;
      super.startingAccountPreferences = null;
   }

   public enum ACCOUNT_TYPE {
      YAHOO,
      GMAIL,
      ZIMBRA,
      IMAP,
      MS_IMAP
   }

   @Test(description="Delete the Yahoo! account from ZD Client through clicking Delete Button", groups = { "smoke" })
   public void deleteYahooAccountThruClick() throws HarnessException {
      app.zPageAddNewAccount.zAddYahooAccountThruUI();

      String confirmationMessage = app.zPageLogin.zRemoveAccountThroughClick();

      ZAssert.assertEquals(confirmationMessage,
            "Account settings and downloaded data will be deleted. Data on the server will not be affected. OK to proceed?",
            "Verifying delete confirmation message");

      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();

      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }

   @Test(description="Delete the Gmail account from ZD Client through clicking Delete Button", groups = { "smoke" })
   public void deleteGmailAccountThruClick() throws HarnessException {
      app.zPageAddNewAccount.zAddGmailAccountThruUI();

      String confirmationMessage = app.zPageLogin.zRemoveAccountThroughClick();

      ZAssert.assertEquals(confirmationMessage,
            "Account settings and downloaded data will be deleted. Data on the server will not be affected. OK to proceed?",
            "Verifying delete confirmation message");

      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();

      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }

   @Test(description="Delete the Zmail account from ZD Client through clicking Delete Button", groups = { "smoke" })
   public void deleteZmailAccountThruClick() throws HarnessException {
      app.zPageAddNewAccount.zAddZmailAccountThruUI();

      String confirmationMessage = app.zPageLogin.zRemoveAccountThroughClick();

      ZAssert.assertEquals(confirmationMessage,
            "Account settings and downloaded data will be deleted. Data on the server will not be affected. OK to proceed?",
            "Verifying delete confirmation message");

      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();

      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }

   @Test(description="Delete the Yahoo! account from ZD Client through HTTP Post", groups = { "functional" })
   public void deleteYahooAccountThruHttpPost() throws HarnessException {
      app.zPageAddNewAccount.zAddYahooAccountThruUI();

      app.zPageLogin.zLogin(new ZmailAccount(yahooUserName, yahooPassword));
      app.zPageLogin.zNavigateTo();
      
      app.zPageLogin.zRemoveAccount();

      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();

      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }

   @Test(description="Delete the Gmail account from ZD Client through HTTP Post", groups = { "functional" })
   public void deleteGmailAccountThruHttpPost() throws HarnessException {
      app.zPageAddNewAccount.zAddGmailAccountThruUI();

      app.zPageLogin.zLogin(new ZmailAccount(gmailUserName, gmailPassword));
      app.zPageLogin.zNavigateTo();

      app.zPageLogin.zRemoveAccount();

      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();

      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }

   @Test(description="Delete the Zmail account from ZD Client through HTTP Post", groups = { "functional" })
   public void deleteZmailAccountThruHttpPost() throws HarnessException {
      app.zPageAddNewAccount.zAddZmailAccountThruUI();

      app.zPageLogin.zLogin(ZmailAccount.AccountZDC());
      app.zPageLogin.zNavigateTo();

      app.zPageLogin.zRemoveAccount();

      String welcomeMessage = app.zPageLogin.zGetWelcomeMessage();

      ZAssert.assertStringContains(welcomeMessage,
            "Zmail Desktop allows you to access email while you are disconnected from the internet.",
            "Verify welcome message is displayed");

      ZAssert.assertEquals(false,
            app.zPageLogin.sIsElementPresent(PageLogin.Locators.zDisplayedMessage),
            "Added account message is displayed");
   }
}
