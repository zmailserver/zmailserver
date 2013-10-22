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
package com.zimbra.qa.selenium.projects.admin.ui;

import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.ui.AbsPage;
import com.zimbra.qa.selenium.framework.ui.AbsTab;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.*;


/**
 * This class defines the login page
 * @author Matt Rhoades
 *
 */
public class PageLogin extends AbsTab {
	
	public static class Locators {
		
		public static final String zLoginDialog = "css=div[class='ZaLoginDialog']";
		public static final String zLoginUserName = "ZLoginUserName";
		public static final String zLoginPassword = "ZLoginPassword";
		public static final String zLoginButtonContainer = "ZLoginButton";
		public static final String zLoginLicenseContainer = "ZLoginLicenseContainer";
		
	}

	/**
	 * An object that controls the Admin Console Login Page
	 */
	public PageLogin(AbsApplication application) {
		super(application);
		
		logger.info("new " + myPageName());
	}
	
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	/**
	 * If the "Login" button is visible, assume the LoginPage is active
	 */
	public boolean zIsActive() throws HarnessException {

		// Make sure the application is loaded first
		if ( !MyApplication.zIsLoaded() )
			throw new HarnessException("Admin Console application is not active!");


		// Look for the login button. 
		boolean present = sIsElementPresent(Locators.zLoginButtonContainer);
		if ( !present ) {
			logger.debug("isActive() present = "+ present);
			return (false);
		}
		
		boolean visible = zIsVisiblePerPosition(Locators.zLoginButtonContainer, 0 , 0);
		if ( !visible ) {
			logger.debug("isActive() visible = "+ visible);
			return (false);
		}
		
		logger.debug("isActive() = "+ true);
		return (true);
	}
		
	@Override
	public void zNavigateTo() throws HarnessException {
		
		if ( zIsActive() ) {
			// This page is already active.
			return;
		}
		
		
		// Logout
		if ( ((AppAdminConsole)MyApplication).zPageMain.zIsActive() ) {
			((AppAdminConsole)MyApplication).zPageMain.logout();
		}
		
		zWaitForActive();
	}


	/**
	 * Login as the GlobalAdmin
	 * @throws HarnessException
	 */
	public void login() throws HarnessException {
		logger.debug("login()");

		login(ZimbraAdminAccount.AdminConsoleAdmin());
	}
	
	/**
	 * Login as the specified account
	 * @param account
	 * @throws HarnessException
	 */
	public void login(ZimbraAccount account) throws HarnessException {
		logger.debug("login(ZimbraAccount account)" + account.EmailAddress);

		zNavigateTo();
		
		// Fill out the form
		fillLoginFormFields(account);
		
		// Click the Login button
		sClick(Locators.zLoginButtonContainer);

		// Wait for the app to load
		// sWaitForPageToLoad();
		((AppAdminConsole)MyApplication).zPageMain.zWaitForActive();
		
		((AppAdminConsole)MyApplication).zSetActiveAcount(account);
		
		SleepUtil.sleep(10000);
	}
	
	/**
	 * Fill the form with the specified user
	 * @throws HarnessException
	 */
	public void fillLoginFormFields(ZimbraAccount account) throws HarnessException {
		logger.debug("fillFields(ZimbraAccount account)" + account.EmailAddress);
		
		if ( !zIsActive() )
			throw new HarnessException("LoginPage is not active");
		
		sType(Locators.zLoginUserName, account.EmailAddress);
		sType(Locators.zLoginPassword, account.Password);
	}

	@Override
	public AbsPage zListItem(Action action, String item)
			throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String item)
			throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption ,String item)
			throws HarnessException {
		// TODO Auto-generated method stub
		return null;	
	}
	
	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option)
			throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}



}
