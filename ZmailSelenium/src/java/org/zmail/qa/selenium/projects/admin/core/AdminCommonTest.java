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
package org.zmail.qa.selenium.projects.admin.core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.thoughtworks.selenium.SeleniumException;
import org.zmail.qa.selenium.framework.core.ClientSessionFactory;
import org.zmail.qa.selenium.framework.ui.AbsTab;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailAdminAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.admin.ui.AppAdminConsole;


/**
 * Common definitions for all Admin Console test cases
 * @author Matt Rhoades
 *
 */
public class AdminCommonTest {
	protected static Logger logger = LogManager.getLogger(AdminCommonTest.class);
	
	/**
	 * Helper field.  admin = ZmailAdminAccount.GlobalAdmin()
	 */
	protected final ZmailAdminAccount gAdmin = ZmailAdminAccount.AdminConsoleAdmin();
	

	/**
	 * The AdminConsole application object
	 */
	protected AppAdminConsole app = null;

	/**
	 * BeforeMethod variables
	 * startingPage = the starting page before the test method starts
	 * startingAccount = the account to log in as
	 */
	protected AbsTab startingPage = null;
	protected ZmailAdminAccount startingAccount = null;
	
	protected AdminCommonTest() {
		logger.info("New "+ AdminCommonTest.class.getCanonicalName());
		
		app = new AppAdminConsole();
		
		startingPage = app.zPageMain;
		startingAccount = gAdmin;
		
	}
	
	/**
	 * Global BeforeSuite
	 * 
	 * 1. Make sure the selenium server is available
	 * 
	 * @throws HarnessException
	 */
	@BeforeSuite( groups = { "always" } )
	public void commonTestBeforeSuite() throws HarnessException {
		
		logger.info("commonTestBeforeSuite");
				
		try
		{
			ZmailSeleniumProperties.setAppType(ZmailSeleniumProperties.AppType.ADMIN);

			// Use 30 second timeout for opening the browser
			String timeout = ZmailSeleniumProperties.getStringProperty("selenium.maxpageload.msec", "30000");

			ClientSessionFactory.session().selenium().start();
			ClientSessionFactory.session().selenium().windowMaximize();
			ClientSessionFactory.session().selenium().windowFocus();
			ClientSessionFactory.session().selenium().allowNativeXpath("true");
			ClientSessionFactory.session().selenium().setTimeout("30000");
			ClientSessionFactory.session().selenium().open(ZmailSeleniumProperties.getBaseURL());
			
		} catch (SeleniumException e) {
			logger.error("Unable to open admin app.  Is a valid cert installed?", e);
			throw e;
		}

		
	}
	
	/**
	 * Global BeforeClass
	 * 
	 * @throws HarnessException
	 */
	@BeforeClass( groups = { "always" } )
	public void commonTestBeforeClass() throws HarnessException {
		logger.info("commonTestBeforeClass");

	}

	/**
	 * Global BeforeMethod
	 * 
	 * 1. For all tests, make sure the CommonTest.startingPage is active
	 * 2. For all tests, make sure the logged in user is 
	 * 
	 * @throws HarnessException
	 */
	@BeforeMethod( groups = { "always" } )
	public void commonTestBeforeMethod() throws HarnessException {
		logger.info("commonTestBeforeMethod: start");
		
		// If a startinAccount is defined, then make sure we are authenticated as that user
		if ( startingAccount != null ) {
			logger.debug("commonTestBeforeMethod: startingAccount is defined");
			
			if ( !startingAccount.equals(app.zGetActiveAccount())) {
				
				if ( app.zPageMain.zIsActive() )
					app.zPageMain.logout();
				app.zPageLogin.login(startingAccount);
				
			}
			
			// Confirm
			if ( !startingAccount.equals(app.zGetActiveAccount())) {
				throw new HarnessException("Unable to authenticate as "+ startingAccount.EmailAddress);
			}
		}

		// If a startingPage is defined, then make sure we are on that page
		if ( startingPage != null ) {
			logger.debug("commonTestBeforeMethod: startingPage is defined");
			
			// If the starting page is not active, navigate to it
			if ( !startingPage.zIsActive() ) {
				startingPage.zNavigateTo();
			}
			
			// Confirm that the page is active
			if ( !startingPage.zIsActive() ) {
				throw new HarnessException("Unable to navigate to "+ startingPage.myPageName());
			}
			
		}
		

		logger.info("commonTestBeforeMethod: finish");

	}

	/**
	 * Global AfterSuite
	 * 
	 * @throws HarnessException
	 */
	@AfterSuite( groups = { "always" } )
	public void commonTestAfterSuite() throws HarnessException {	
		logger.info("commonTestAfterSuite: start");
		
		ClientSessionFactory.session().selenium().stop();

		logger.info("commonTestAfterSuite: finish");

	}
	
	/**
	 * Global AfterClass
	 * 
	 * @throws HarnessException
	 */
	@AfterClass( groups = { "always" } )
	public void commonTestAfterClass() throws HarnessException {
		logger.info("commonTestAfterClass: start");
		
		logger.info("commonTestAfterClass: finish");
	}

	/**
	 * Global AfterMethod
	 * 
	 * @throws HarnessException
	 */
	@AfterMethod( groups = { "always" } )
	public void commonTestAfterMethod() throws HarnessException {
		logger.info("commonTestAfterMethod: start");
		
		logger.info("commonTestAfterMethod: finish");
	}

}
