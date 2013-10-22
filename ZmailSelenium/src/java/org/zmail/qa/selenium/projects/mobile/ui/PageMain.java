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
package org.zmail.qa.selenium.projects.mobile.ui;

import org.zmail.qa.selenium.framework.ui.AbsApplication;
import org.zmail.qa.selenium.framework.ui.AbsPage;
import org.zmail.qa.selenium.framework.ui.AbsTab;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;


/**
 * @author Matt Rhoades
 *
 */
public class PageMain extends AbsTab {

	public static class Locators {
	
		public static final String zBtnLogout = "css=[id='_logout']";
		
		public static final String zMainCopyright = "css=div#copyright_notice";
		public static final String zMainCopyrightText = zMainCopyright +" a";
		
		public static final String zAppbar = "css=div#appbar";
		public static final String zAppbarMail = zAppbar +" a#mail";
		public static final String zAppbarContact = zAppbar +" a#contact";
		public static final String zAppbarCal = zAppbar +" a#cal";
		public static final String zAppbarDocs = zAppbar +" a#docs";
		public static final String zAppbarSearch = zAppbar +" a#search";
		
		public static final String zBtnCompose = "//a[@href='zmain?st=newmail']";
		
		public static final String zPreferences = "css=a[href='?st=prefs']";
	
	}
	
	
	public PageMain(AbsApplication application) {
		super(application);
		
		logger.info("new " + PageMain.class.getCanonicalName());

	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsPage#isActive()
	 */
	@Override
	public boolean zIsActive() throws HarnessException {

		// Make sure the Mobile Client is loaded in the browser
		if ( !MyApplication.zIsLoaded() )
			throw new HarnessException("Admin Console application is not active!");
		

		// Look for the Logout button
		boolean present = sIsElementPresent(Locators.zBtnLogout);
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
		if ( !((AppMobileClient)MyApplication).zPageLogin.zIsActive() ) {
			((AppMobileClient)MyApplication).zPageLogin.zNavigateTo();
		}
		((AppMobileClient)MyApplication).zPageLogin.zLogin();

		zWaitForActive();
		
	}

	/**
	 * Click the logout button
	 * @throws HarnessException
	 */
	public void zLogout() throws HarnessException {
		logger.debug("zLogout()");
		
		zNavigateTo();

		if ( !sIsElementPresent(Locators.zBtnLogout) ) {
			throw new HarnessException("The logoff button is not present " + Locators.zBtnLogout);
		}
				
		// Click on logout
		sClick(Locators.zBtnLogout);
				
		((AppMobileClient)MyApplication).zPageLogin.zWaitForActive();
		
		((AppMobileClient)MyApplication).zSetActiveAcount(null);

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
		throw new HarnessException("Mobile page does not have context menu");
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
