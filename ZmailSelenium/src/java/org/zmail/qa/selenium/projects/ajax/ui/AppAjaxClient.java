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
package org.zmail.qa.selenium.projects.ajax.ui;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.addressbook.PageAddressbook;
import org.zmail.qa.selenium.projects.ajax.ui.addressbook.TreeContacts;
import org.zmail.qa.selenium.projects.ajax.ui.briefcase.PageBriefcase;
import org.zmail.qa.selenium.projects.ajax.ui.briefcase.TreeBriefcase;
import org.zmail.qa.selenium.projects.ajax.ui.calendar.PageCalendar;
import org.zmail.qa.selenium.projects.ajax.ui.calendar.TreeCalendar;
import org.zmail.qa.selenium.projects.ajax.ui.mail.*;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.PagePreferences;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.signature.PageSignature;
import org.zmail.qa.selenium.projects.ajax.ui.search.PageAdvancedSearch;
import org.zmail.qa.selenium.projects.ajax.ui.search.PageSearch;
import org.zmail.qa.selenium.projects.ajax.ui.social.PageSocial;
import org.zmail.qa.selenium.projects.ajax.ui.tasks.*;


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
	
	// Pages
	public PageLogin					zPageLogin = null;
	public PageExternalRegistration		zPageExternalRegistration = null;
	public PageMain						zPageMain = null;
	public PageExternalMain				zPageExternalMain = null;
	public PageSearch					zPageSearch = null;
	public PageAdvancedSearch			zPageAdvancedSearch = null;
	public PageMail						zPageMail = null;
	public PageBriefcase                zPageBriefcase = null;
	public PageAddressbook              zPageAddressbook = null;
	public PageCalendar					zPageCalendar = null;
	public PageTasks					zPageTasks = null;
	public PagePreferences				zPagePreferences = null;
	public PageSignature				zPageSignature = null;
	
	// Trees
	public TreeMail						zTreeMail = null;
	public TreeContacts					zTreeContacts = null;
	public TreeCalendar					zTreeCalendar = null;
	public TreeTasks					zTreeTasks = null;
	public TreeBriefcase		        zTreeBriefcase = null;
	public TreePreferences				zTreePreferences = null;
	
	// Zimlets
	public PageSocial					zPageSocial = null;

	
	public AppAjaxClient() {
		super();
		
		logger.info("new " + AppAjaxClient.class.getCanonicalName());
		
		
		// Login page
		zPageLogin = new PageLogin(this);
		pages.put(zPageLogin.myPageName(), zPageLogin);
		
		zPageExternalRegistration = new PageExternalRegistration(this);
		pages.put(zPageExternalRegistration.myPageName(), zPageExternalRegistration);
		
		// Main page
		zPageMain = new PageMain(this);
		pages.put(zPageMain.myPageName(), zPageMain);
		
		zPageExternalMain = new PageExternalMain(this);
		pages.put(zPageExternalMain.myPageName(), zPageExternalMain);
		
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
		
		zTreeCalendar = new TreeCalendar(this);
		trees.put(zTreeCalendar.myPageName(), zTreeCalendar);
		
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
		

		// Zimlets
		zPageSocial = new PageSocial(this);
		pages.put(zPageSocial.myPageName(), zPageSocial);
		

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
            this.zPageLogin.zIsActive()) {
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
	 */
	@Override
	protected ZmailAccount zSetActiveAcount(ZmailAccount account) throws HarnessException {
		return (super.zSetActiveAcount(account));
	}

}
