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
/**
 * 
 */
package org.zmail.qa.selenium.projects.desktop.ui;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailDesktopProperties;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.accounts.PageAddNewAccount;
import org.zmail.qa.selenium.projects.desktop.ui.addressbook.PageAddressbook;
import org.zmail.qa.selenium.projects.desktop.ui.addressbook.TreeContacts;
import org.zmail.qa.selenium.projects.desktop.ui.briefcase.PageBriefcase;
import org.zmail.qa.selenium.projects.desktop.ui.briefcase.TreeBriefcase;
import org.zmail.qa.selenium.projects.desktop.ui.calendar.PageCalendar;
import org.zmail.qa.selenium.projects.desktop.ui.mail.*;
import org.zmail.qa.selenium.projects.desktop.ui.preferences.PagePreferences;
import org.zmail.qa.selenium.projects.desktop.ui.preferences.TreePreferences;
import org.zmail.qa.selenium.projects.desktop.ui.preferences.signature.PageSignature;
import org.zmail.qa.selenium.projects.desktop.ui.search.PageAdvancedSearch;
import org.zmail.qa.selenium.projects.desktop.ui.search.PageSearch;
import org.zmail.qa.selenium.projects.desktop.ui.tasks.*;


/**
 * The <code>AppAjaxClient</code> class defines the Zmail Ajax client.
 * <p>
 * The <code>AppAjaxClient</code> contains all pages, folder trees,
 * dialog boxes, forms, menus for the Ajax client.
 * <p>
 * In {@link AjaxCommonTest}, there is one
 * AppAjaxClient object created per test case class (ensuring 
 * class-level concurrency).  The test case methods can access
 * different application pages and trees, using the object
 * properties.
 * <p>
 * <pre>
 * {@code
 * 
 * // Navigate to the addresbook
 * app.zPageAddressbook.navigateTo();
 * 
 * // Click "New" button to create a new contact
 * app.zPageAddressbook.zToolbarPressButton(Button.B_NEW);
 * 
 * }
 * </pre>
 * <p>
 * 
 * @author Matt Rhoades
 *
 */
public class AppAjaxClient extends AbsApplication {
	
	public PageLogin					zPageLogin = null;
	public PageMain						zPageMain = null;
	public PageSearch					zPageSearch = null;
	public PageAdvancedSearch			zPageAdvancedSearch = null;
	public PageMail						zPageMail = null;
	public PageBriefcase                zPageBriefcase = null;
	public PageAddressbook              zPageAddressbook = null;
	public PageCalendar					zPageCalendar = null;
	public PageTasks					zPageTasks = null;
	public PagePreferences				zPagePreferences = null;
	public PageSignature				zPageSignature = null;
	public PageAddNewAccount            zPageAddNewAccount = null;
	
	public TreeMail						zTreeMail = null;
	public TreeContacts					zTreeContacts = null;
	public TreeTasks					zTreeTasks = null;
	public TreeBriefcase		        zTreeBriefcase = null;
	public TreePreferences				zTreePreferences = null;
	
	public AppAjaxClient() {
		super();
		
		logger.info("new " + AppAjaxClient.class.getCanonicalName());
		
		
		// Login page
		
		zPageLogin = new PageLogin(this);
		pages.put(zPageLogin.myPageName(), zPageLogin);
		
		// Main page
		zPageMain = new PageMain(this);
		pages.put(zPageMain.myPageName(), zPageMain);
		
		zPageSearch = new PageSearch(this);
		pages.put(zPageSearch.myPageName(), zPageSearch);
		
		zPageAdvancedSearch = new PageAdvancedSearch(this);
		pages.put(zPageAdvancedSearch.myPageName(), zPageAdvancedSearch);
		
		// Mail page
		zPageMail = new PageMail(this);
		pages.put(zPageMail.myPageName(), zPageMail);
		
		zTreeMail = new TreeMail(this);
		trees.put(zTreeMail.myPageName(), zTreeMail);
		
		//Addressbook page    
		zPageAddressbook = new PageAddressbook(this);
		pages.put(zPageAddressbook.myPageName(), zPageAddressbook);

		zTreeContacts = new TreeContacts(this);
		trees.put(zTreeContacts.myPageName(), zTreeContacts);
		
		// Calendar page
		zPageCalendar = new PageCalendar(this);
		pages.put(zPageCalendar.myPageName(), zPageCalendar);
		
		// PageBriefcase page
		zPageBriefcase = new PageBriefcase(this);
		pages.put(zPageBriefcase.myPageName(), zPageBriefcase);
		
		zTreeBriefcase = new TreeBriefcase(this);
		trees.put(zTreeBriefcase.myPageName(), zTreeBriefcase);
				
		// PageTasks page
		zPageTasks = new PageTasks(this);
		pages.put(zPageTasks.myPageName(), zPageTasks);
		
		zTreeTasks = new TreeTasks(this);
		trees.put(zTreeTasks.myPageName(), zTreeTasks);
		
		// Preferences page
		zPagePreferences = new PagePreferences(this);
		pages.put(zPagePreferences.myPageName(), zPagePreferences);

		zTreePreferences = new TreePreferences(this);
		trees.put(zTreePreferences.myPageName(), zTreePreferences);

		// signature Preferences page
		zPageSignature = new PageSignature(this);
		pages.put(zPageSignature.myPageName(),zPageSignature);

		// Add New Account page
		zPageAddNewAccount = new PageAddNewAccount(this);
		pages.put(zPageAddNewAccount.myPageName(), zPageAddNewAccount);

		// Configure the localization strings
		getL10N().zAddBundlename(I18N.Catalog.I18nMsg);
		getL10N().zAddBundlename(I18N.Catalog.AjxMsg);
		getL10N().zAddBundlename(I18N.Catalog.ZMsg);
		getL10N().zAddBundlename(I18N.Catalog.ZsMsg);
		getL10N().zAddBundlename(I18N.Catalog.ZmMsg);
		
	}
	
	
	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsApplication#isLoaded()
	 */
	@Override
	public boolean zIsLoaded() throws HarnessException {
	   if (this.zPageMain.zIsActive() ||
            this.zPageLogin.zIsActive() ||
            this.zPageAddNewAccount.zIsActive()) {
         return true;
      } else {
         return false;
      }
	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsApplication#myApplicationName()
	 */
	@Override
	public String myApplicationName() {
		return ("Ajax Client");
	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsApplication#myApplicationName()
	 * Set to public instead of protected only for desktop project to allow multiple account switching
	 * in the middle of the tests
	 */
	@Override
	public ZmailAccount zSetActiveAcount(ZmailAccount account) throws HarnessException {
		return (super.zSetActiveAcount(account));
	}

	/**
    * Delete Desktop account through HTTP Post with last account variable defaulted to false
    * @param accountName Account Name to be deleted
    * @param accountId Account ID to be deleted
    * @param accountType Account Type (usually: zmail)
    * @param accountFlavor Account Flavor (usually: Zmail)
    * @throws HarnessException
    */
	public void zDeleteDesktopAccount(String accountName, String accountId,
         String accountType, String accountFlavor) throws HarnessException {
	   zDeleteDesktopAccount(accountName, accountId, accountType, accountFlavor, false);
   }

	/**
    * Delete Desktop account through HTTP Post
    * @param accountName Account Name to be deleted
    * @param accountId Account ID to be deleted
    * @param accountType Account Type (usually: zmail)
    * @param accountFlavor Account Flavor (usually: Zmail)
    * @param lastAccount Is this last account (for wait purpose)
    * @throws HarnessException
    */
   public void zDeleteDesktopAccount(String accountName, String accountId,
         String accountType, String accountFlavor, boolean lastAccount) throws HarnessException {
      String serverScheme = ZmailSeleniumProperties.getStringProperty("server.scheme", "http");
      String serverName = ZmailSeleniumProperties.getStringProperty("desktop.server.host", "localhost");
      ZmailDesktopProperties zdp = ZmailDesktopProperties.getInstance();
      String connectionPort = zdp.getConnectionPort();
      String accountDeleteUrl = new StringBuilder(serverScheme).append("://")
            .append(serverName). append(":")
            .append(connectionPort).append("/")
            .append("zmail/desktop/accsetup.jsp?at=")
            .append(zdp.getSerialNumber()).append("&accountId=")
            .append(accountId).append("&verb=del&accountFlavor=")
            .append(accountFlavor).append("&accountName=")
            .append(accountName).append("&accountType=")
            .append(accountType).toString();//append("&dev=1&scripterrors=1").toString();

      logger.info("accountDeleteUrl: " + accountDeleteUrl);
      GeneralUtility.doHttpPost(accountDeleteUrl);

      zPageLogin.sRefresh();
      GeneralUtility.waitForElementPresent(zPageLogin,
            PageLogin.Locators.zAddNewAccountButton);
      if (lastAccount || !zPageLogin.sIsElementPresent(PageLogin.Locators.zDeleteButton)) {
         ZmailAccount.ResetAccountZDC();
      }
   }
}
