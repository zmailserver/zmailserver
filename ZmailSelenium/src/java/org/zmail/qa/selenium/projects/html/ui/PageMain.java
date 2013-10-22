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
package com.zimbra.qa.selenium.projects.html.ui;

import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.ui.DialogError.DialogErrorID;


/**
 * @author Matt Rhoades
 *
 */
public class PageMain extends AbsTab {

	public static class Locators {
				
		public static final String zLogoffButton		= "css=a[href='/?loginOp=logout']";
		
		public static final String zAppbarMail			= "id=TAB_MAIL";
		public static final String zAppbarContact		= "id=TAB_ADDRESSBOOK";
		public static final String zAppbarCal			= "id=TAB_CALENDAR";
		public static final String zAppbarTasks			= "id=TAB_TASKS";
		public static final String zAppbarBriefcase		= "id=TAB_BRIEFCASES";
		public static final String zAppbarPreferences	= "id=TAB_OPTIONS";
		public static final String zAppbarCompose		= "id=TAB_COMPOSE";

		// For Social tab, see Zimlet classes
		
	}
	
	
	public PageMain(AbsApplication application) {
		super(application);
		
		logger.info("new " + PageMain.class.getCanonicalName());

	}
	
	public AbsToaster zGetToaster() throws HarnessException {
		throw new HarnessException("implement me!");
	}
	
	public AbsDialog zGetErrorDialog(DialogErrorID zimbra) throws HarnessException {
		throw new HarnessException("implement me!");
	}



	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsPage#isActive()
	 */
	@Override
	public boolean zIsActive() throws HarnessException {

		// Look for the Logout button 
		// check if zimlet + minical loaded
		boolean present = sIsElementPresent(Locators.zLogoffButton);			
		
		if ( !present ) {
			logger.debug("isActive() present = "+ present);
			return (false);
		}
		
		logger.debug("isActive() = "+ true);
		return (true);

	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsPage#myPageName()
	 */
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsPage#navigateTo()
	 */
	@Override
	public void zNavigateTo() throws HarnessException {


		if ( zIsActive() ) {
			// This page is already active
			return;
		}
			
		// 1. Logout
		// 2. Login as the default account
		if ( !((AppHtmlClient)MyApplication).zPageLogin.zIsActive() ) {
			((AppHtmlClient)MyApplication).zPageLogin.zNavigateTo();
		}
		((AppHtmlClient)MyApplication).zPageLogin.zLogin(ZimbraAccount.AccountHTML());
		zWaitForActive();
		
	}

	/**
	 * Click the logout button
	 * @throws HarnessException
	 */
	public void zLogout() throws HarnessException {
		logger.debug("logout()");

		tracer.trace("Logout of the "+ MyApplication.myApplicationName());

		zNavigateTo();

		if ( !sIsElementPresent(Locators.zLogoffButton) ) {
			throw new HarnessException("The logoff button is not present " + Locators.zLogoffButton);
		}

		// Click on logout
		sClick(Locators.zLogoffButton);

		sWaitForPageToLoad();
		((AppHtmlClient)MyApplication).zPageLogin.zWaitForActive();

		((AppHtmlClient)MyApplication).zSetActiveAcount(null);

		// TODO: maybe handle this in the pulldown of PageLogin.zLogin() ?
		// Force the html client to load
		this.sOpen(ZimbraSeleniumProperties.getBaseURL());

	}

	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {

		// Q. Should the tabs or help or logout be processed here?
		// A. I don't think those are considered "toolbars", so don't handle here for now (Matt)
		throw new HarnessException("Main page does not have a Toolbar");
		
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {
		throw new HarnessException("Main page does not have a Toolbar");
	}

	@Override
	public AbsPage zListItem(Action action, String item) throws HarnessException {
		throw new HarnessException("Main page does not have lists");
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String item) throws HarnessException {
		throw new HarnessException("Main page does not have lists");
	}

	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption ,String item)
			throws HarnessException {
		throw new HarnessException("Main page does not have lists");
	}	
	/**
	 * Close any extra compose tabs
	 */
	public void zCloseComposeTabs() throws HarnessException {
		
		String locator = "//td[contains(@id,'ztb_appChooser_item_')]//div[contains(@id,'zb__App__tab_COMPOSE')]";
		if ( sIsElementPresent(locator) ) {
			logger.debug("Found compose tabs");
			
			// String xpath = "//td[contains(@id,'ztb_appChooser_item_')]//div[contains(@id,'zb__App__tab_COMPOSE')]";
			// int count = this.sGetXpathCount(xpath);
			String css = "css=td[id^='ztb_appChooser_item_'] div[id^='zb__App__tab_COMPOSE']";
			int count = this.sGetCssCount(css);
			for (int i = 1; i <= count; i++) {
				// locator = xpath + "//td[contains(@id,'_left_icon')]["+ i +"]";
				locator = css + " td[id$='_left_icon']:nth-of-type("+ i +")";
				if ( !sIsElementPresent(locator) ) 
					throw new HarnessException("Unable to find compose tab close icon "+ locator);
				this.zClick(locator);
			}
		}
	}

	

}
