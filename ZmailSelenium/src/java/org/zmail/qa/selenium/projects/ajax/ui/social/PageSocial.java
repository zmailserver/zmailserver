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
package org.zmail.qa.selenium.projects.ajax.ui.social;

import org.zmail.qa.selenium.framework.ui.AbsApplication;
import org.zmail.qa.selenium.framework.ui.AbsPage;
import org.zmail.qa.selenium.framework.ui.AbsTab;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.projects.ajax.ui.AppAjaxClient;
import org.zmail.qa.selenium.projects.ajax.ui.PageMain;



/**
 * @author Matt Rhoades
 *
 */
public class PageSocial extends AbsTab {


	public static class Locators {
		public static final String StatusTextAreaLocatorCSS = "css=textarea[id='social_statusTextArea']";
	}


	public PageSocial(AbsApplication application) {
		super(application);

		logger.info("new " + PageSocial.class.getCanonicalName());

	}

	/**
	 * Dismiss the 'welcome' popup if it is showing
	 * @throws HarnessException 
	 */
	private boolean zDismissWelcomeDialog() throws HarnessException {
		// TODO: see https://bugzilla.zmail.com/show_bug.cgi?id=61984
		DialogSocialZimletWelcome dialog = new DialogSocialZimletWelcome(MyApplication, ((AppAjaxClient) MyApplication).zPageSocial);
		if ( dialog.zIsActive() ) {
			dialog.zClickButton(Button.B_OK);
			return (true);
		}
		return (false);
	}
	
	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsPage#isActive()
	 */
	@Override
	public boolean zIsActive() throws HarnessException {

		// Make sure the main page is active
		if ( !((AppAjaxClient)MyApplication).zPageMain.zIsActive() ) {
			((AppAjaxClient)MyApplication).zPageMain.zNavigateTo();
		}

		// Need to rethink just blindly dismissing this dialog - what if the test case needs to verify it?
		this.zDismissWelcomeDialog();
		
		boolean present = this.sIsElementPresent(Locators.StatusTextAreaLocatorCSS);
		if ( !present ) {
			return (false);
		}
		
		boolean visible = this.zIsVisiblePerPosition(Locators.StatusTextAreaLocatorCSS, 0, 0);
		if ( !visible ) {
			return (false);
		}
		
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

		// Check if this page is already active.
		if ( zIsActive() ) {
			return;
		}

		// Make sure we are logged into the Mobile app
		if ( !((AppAjaxClient)MyApplication).zPageMain.zIsActive() ) {
			((AppAjaxClient)MyApplication).zPageMain.zNavigateTo();
		}

		tracer.trace("Navigate to "+ this.myPageName());

		this.zClick(PageMain.Locators.zAppbarSocialLocator);

		this.zWaitForBusyOverlay();

		zWaitForActive();

	}

	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButton("+ button +")");

		tracer.trace("Press the "+ button +" button");

		throw new HarnessException("implement me!");

	}

	@Override
	public AbsPage zListItem(Action action, String item) throws HarnessException {
		throw new HarnessException("implement me!");
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String item) throws HarnessException {
		throw new HarnessException("implement me!");
	}

	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption, String item) throws HarnessException {
		throw new HarnessException("implement me!");
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {
		throw new HarnessException("implement me!");
	}


}
