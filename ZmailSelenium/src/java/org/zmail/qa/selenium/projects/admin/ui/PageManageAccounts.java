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
package org.zmail.qa.selenium.projects.admin.ui;

import java.util.ArrayList;
import java.util.List;

import org.zmail.qa.selenium.framework.ui.AbsApplication;
import org.zmail.qa.selenium.framework.ui.AbsPage;
import org.zmail.qa.selenium.framework.ui.AbsTab;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.admin.items.AccountItem;


/**
 * Admin Console -> Manage Accounts -> Accounts
 * @author Matt Rhoades
 *
 */
public class PageManageAccounts extends AbsTab {

	public static class Locators {
		public static final String MANAGE_ACCOUNTS_ICON="css=div.ImgManageAccounts";
		public static final String ACCOUNTS="css=td[id^='zti__AppAdmin__Home__actLstHV']";
		public static final String GEAR_ICON="css=div.ImgConfigure";
		public static final String HOME="Home";
		public static final String MANAGE="Manage";
		public static final String ACCOUNT="Accounts";
		public static final String NEW_MENU="css=div[id='zm__zb_currentApp__MENU_POP'] div[class='ImgNewAccount']";
		public static final String DELETE_BUTTON="css=div[id='zm__zb_currentApp__MENU_POP'] div[class='ImgDelete']";
		public static final String EDIT_BUTTON="css=div[id='zm__zb_currentApp__MENU_POP'] div[class='ImgEdit']";
		public static final String RIGHT_CLICK_MENU_DELETE_BUTTON="css=div[id^='zm__ACLV__MENU_POP'] div[class='ImgDelete']";
		public static final String RIGHT_CLICK_MENU_EDIT_BUTTON="css=div[id^='zm__ACLV__MENU_POP'] div[class='ImgEdit']";
	}

	public PageManageAccounts(AbsApplication application) {
		super(application);

		logger.info("new " + myPageName());

	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsTab#myPageName()
	 */
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsTab#isActive()
	 */
	@Override
	public boolean zIsActive() throws HarnessException {

		// Make sure the Admin Console is loaded in the browser
		if ( !MyApplication.zIsLoaded() )
			throw new HarnessException("Admin Console application is not active!");


		boolean present = sIsElementPresent(Locators.GEAR_ICON);
		if ( !present ) {
			return (false);
		}

		boolean visible = zIsVisiblePerPosition(Locators.GEAR_ICON, 0, 0);
		if ( !visible ) {
			logger.debug("isActive() visible = "+ visible);
			return (false);
		}

		return (true);

	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsTab#navigateTo()
	 */
	@Override
	public void zNavigateTo() throws HarnessException {

		if ( zIsActive() ) {
			// This page is already active.
			return;
		}

		// Click on Manage Accounts -> Accounts
		zClickAt(Locators.MANAGE_ACCOUNTS_ICON,"");
		sIsElementPresent(Locators.ACCOUNTS);
		zClickAt(Locators.ACCOUNTS, "");

		zWaitForActive();

	}

	public void zNavigateTo(String treeItem) {


	}

	@Override
	public AbsPage zListItem(Action action, String item)
	throws HarnessException {
		logger.info(myPageName() + " zListItem("+ action +", "+ item +")");

		tracer.trace(action +" on subject = "+ item);

		AbsPage page = null;
		SleepUtil.sleepSmall();

		// How many items are in the table?
		String rowsLocator = "css=div#zl__ACCT_MANAGE div[id$='__rows'] div[id^='zli__']";
		int count = this.sGetCssCount(rowsLocator);
		logger.debug(myPageName() + " zListGetAccounts: number of accounts: "+ count);

		// Get each conversation's data from the table list
		for (int i = 1; i <= count; i++) {
			final String accountLocator = rowsLocator + ":nth-child("+i+")";
			String locator;

			// Email Address
			locator = accountLocator + " td[id^='account_data_emailaddress']";


			if(this.sIsElementPresent(locator)) 
			{
				if(this.sGetText(locator).trim().equalsIgnoreCase(item)) 
				{
					if(action == Action.A_LEFTCLICK) {
						zClick(locator);
						break;
					} else if(action == Action.A_RIGHTCLICK) {
						zRightClick(locator);
						break;
					}

				}
			}
		}
		return page;
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

		logger.info(myPageName() + " zToolbarPressButton("+ button +")");

		tracer.trace("Press the "+ button +" button");

		if ( button == null )
			throw new HarnessException("Button cannot be null!");


		// Default behavior variables
		//
		String locator = null;			// If set, this will be clicked
		AbsPage page = null;	// If set, this page will be returned

		// Based on the button specified, take the appropriate action(s)
		//

		if ( button == Button.B_NEW ) {

			// New button
			//			locator = Locators.zb__ACLV__NEW_MENU_title;
			locator ="";
			// Create the page
			page = new WizardCreateAccount(this);
			// FALL THROUGH

		} else if(button == Button.B_TREE_DELETE) {

			locator=Locators.RIGHT_CLICK_MENU_DELETE_BUTTON;

			page = new DialogForDeleteOperation(this.MyApplication, null);
		} else if(button == Button.B_EDIT) {

			locator=Locators.EDIT_BUTTON;

			page = new FormEditAccount(this.MyApplication);
		} else if(button == Button.B_TREE_EDIT) {

			locator=Locators.RIGHT_CLICK_MENU_EDIT_BUTTON;

			page = new FormEditAccount(this.MyApplication);
		} 
		else {
			throw new HarnessException("no logic defined for button "+ button);
		}

		if ( locator == null ) {
			throw new HarnessException("locator was null for button "+ button);
		}

		// Default behavior, process the locator by clicking on it
		//
		this.zClickAt(locator,"");



		// If page was specified, make sure it is active
		if ( page != null ) {
			SleepUtil.sleepMedium();
		}

		sMouseOut(locator);
		return (page);


	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButtonWithPulldown("+ pulldown +", "+ option +")");

		tracer.trace("Click pulldown "+ pulldown +" then "+ option);

		if (pulldown == null)
			throw new HarnessException("Pulldown cannot be null!");

		if (option == null)
			throw new HarnessException("Option cannot be null!");


		// Default behavior variables
		String pulldownLocator = null; // If set, this will be expanded
		String optionLocator = null; // If set, this will be clicked
		AbsPage page = null; // If set, this page will be returned

		if (pulldown == Button.B_GEAR_BOX) {
			pulldownLocator = Locators.GEAR_ICON;

			if (option == Button.O_NEW) {

				optionLocator = Locators.NEW_MENU;

				page = new WizardCreateAccount(this);

				// FALL THROUGH

			} else if(option == Button.O_EDIT) {
				optionLocator = Locators.EDIT_BUTTON;

				page = new FormEditAccount(this.MyApplication);

			} else if(option == Button.O_DELETE) {
				optionLocator = Locators.DELETE_BUTTON;

				page = new DialogForDeleteOperation(this.MyApplication,null);

			}

			else {
				throw new HarnessException("no logic defined for pulldown/option " + pulldown + "/" + option);
			}

		} else {
			throw new HarnessException("no logic defined for pulldown/option "
					+ pulldown + "/" + option);
		}

		// Default behavior
		if (pulldownLocator != null) {

			// Make sure the locator exists
			if (!this.sIsElementPresent(pulldownLocator)) {
				throw new HarnessException("Button " + pulldown + " option " + option + " pulldownLocator " + pulldownLocator + " not present!");
			}

			this.zClickAt(pulldownLocator,"");
			SleepUtil.sleepMedium();

			// If the app is busy, wait for it to become active
			//zWaitForBusyOverlay();

			if (optionLocator != null) {

				// Make sure the locator exists
				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException("Button " + pulldown + " option " + option + " optionLocator " + optionLocator + " not present!");
				}

				this.zClickAt(optionLocator,"");

				// If the app is busy, wait for it to become active
				//zWaitForBusyOverlay();
			}

		}

		// Return the specified page, or null if not set
		return (page);

	}

	/**
	 * Return a list of all accounts in the current view
	 * @return
	 * @throws HarnessException 
	 * @throws HarnessException 
	 */
	public List<AccountItem> zListGetAccounts() throws HarnessException {

		List<AccountItem> items = new ArrayList<AccountItem>();

		// Make sure the button exists
		if ( !this.sIsElementPresent("css=div[id='zl__ACCT_MANAGE'] div[id$='__rows']") )
			throw new HarnessException("Account Rows is not present");

		// How many items are in the table?
		String rowsLocator = "//div[@id='zl__ACCT_MANAGE']//div[contains(@id, '__rows')]//div[contains(@id,'zli__')]";
		int count = this.sGetXpathCount(rowsLocator);
		logger.debug(myPageName() + " zListGetAccounts: number of accounts: "+ count);

		// Get each conversation's data from the table list
		for (int i = 1; i <= count; i++) {
			final String accountLocator = rowsLocator + "["+ i +"]";
			String locator;

			AccountItem item = new AccountItem("email" + ZmailSeleniumProperties.getUniqueString(),ZmailSeleniumProperties.getStringProperty("testdomain"));

			// Type (image)
			// ImgAdminUser ImgAccount ImgSystemResource (others?)
			locator = accountLocator + "//td[contains(@id, 'account_data_type_')]//div";
			if ( this.sIsElementPresent(locator) ) {
				item.setGAccountType(this.sGetAttribute("xpath=("+ locator + ")@class"));
			}


			// Email Address
			locator = accountLocator + "//td[contains(@id, 'account_data_emailaddress_')]";
			if ( this.sIsElementPresent(locator) ) {
				item.setGEmailAddress(this.sGetText(locator).trim());
			}

			// Display Name
			// Status
			// Lost Login Time
			// Description


			// Add the new item to the list
			items.add(item);
			logger.info(item.prettyPrint());
		}

		// Return the list of items
		return (items);
	}

	public boolean zVerifyHeader (String header) throws HarnessException {
		if(this.sIsElementPresent("css=span:contains('" + header + "')"))
			return true;
		return false;
	}
}
