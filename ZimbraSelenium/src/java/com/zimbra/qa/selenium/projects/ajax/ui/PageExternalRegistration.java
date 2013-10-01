/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package com.zimbra.qa.selenium.projects.ajax.ui;

import java.net.URI;

import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.framework.util.performance.PerfMetrics;


/**
 * This class extends the Login Page, but tracks the external registration URL.  
 * example, https://zqa-062.eng.vmware.com/service/extuserprov/?p=0_46059ce585e90f5d2d5...12e636f6d3b
 * @author Matt Rhoades
 *
 */
public class PageExternalRegistration extends PageLogin {


	/**
	 * The URL to register at.
	 * example, https://zqa-062.eng.vmware.com/service/extuserprov/?p=0_46059ce585e90f5d2d5...12e636f6d3b
	 */
	protected ZimbraURI MyUrl = null;
	
	

	public PageExternalRegistration(AbsApplication application) {
		super(application);

		logger.info("new " + PageExternalRegistration.class.getCanonicalName());

	}

	@Override
	public boolean zIsActive() throws HarnessException {

		if ( !super.zIsActive() ) {
			return (false);
		}

		String buttonText = this.sGetAttribute(Locators.zBtnLogin + "@value");
		if ( ! ("Register".equals(buttonText)) ) { // TOOD: I18N
			logger.debug("button text does not equal 'Register': "+ buttonText);
			return (false);
		}
		
		logger.debug("isActive() = " + true);
		return (true);
	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}
	
	public void zSetURL(URI uri) throws HarnessException {
		
		// Add the code coverage and perf metrics to the URI
		this.MyUrl = new ZimbraURI(uri);
		
		if ( CodeCoverage.getInstance().isEnabled() ) {
			this.MyUrl.addQuery(CodeCoverage.getInstance().getQueryMap());
		}
		
		if ( PerfMetrics.getInstance().Enabled ) {
			this.MyUrl.addQuery(PerfMetrics.getInstance().getQueryMap());
		}

	}

	@Override
	public void zNavigateTo() throws HarnessException {

		if ( zIsActive() ) {
			// This page is already active.
			return;
		}


		// Logout
		if ( ((AppAjaxClient)MyApplication).zPageMain.zIsActive() ) {
			((AppAjaxClient)MyApplication).zPageMain.zLogout();
		}
		
		// Open MyURL
		// TODO: need to add any URL's (i.e. code coverage)
		this.sOpen(this.MyUrl.toString());

		zWaitForActive();

	}



	/**
	 * Register as the specified account
	 * @param account
	 * @throws HarnessException
	 */
	public void zLogin(ZimbraAccount account) throws HarnessException {
		logger.debug("zLogin(ZimbraAccount account)" + account.EmailAddress);

		tracer.trace("Login to the "+ MyApplication.myApplicationName() +" using user/password "+ account.EmailAddress +"/"+ account.Password);

		zNavigateTo();

		zSetLoginName(account.EmailAddress);
		zSetLoginPassword(account.Password);
		zSetLoginPasswordConfirm(account.Password);

		// Click the Login button
		sClick(Locators.zBtnLogin);

		// Wait for the app to load
		sWaitForPageToLoad();
		((AppAjaxClient)MyApplication).zPageExternalMain.zWaitForActive();

		((AppAjaxClient)MyApplication).zSetActiveAcount(account);

	}
	
	/**
	 * For external user registration,
	 * instead of login name, this is display name
	 */
	public void zSetLoginName(String name) throws HarnessException {
		if ( name == null ) {
			throw new HarnessException("Name is null");
		}
		String locator = "css=input#displayname";
		if ( !this.sIsElementPresent(locator) ) {
			throw new HarnessException("Display field does not exist "+ locator);
		}
		sType(locator, name);
	}

	/**
	 * For external user registration,
	 * there is an extra 'confirm password' field
	 */
	public void zSetLoginPasswordConfirm(String password) throws HarnessException {
		if ( password == null ) {
			throw new HarnessException("Password is null");
		}
		String locator = "css=input#password2";
		if ( !this.sIsElementPresent(locator) ) {
			throw new HarnessException("Confirm field does not exist "+ locator);
		}
		sType(locator, password);
	}






}
