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
package org.zmail.qa.selenium.projects.octopus.ui;

import org.zmail.qa.selenium.framework.items.IItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.projects.octopus.ui.DialogError.DialogErrorID;

public class PageSearch extends PageOctopus {

	public static class Locators {
		public static final Locators zTabSearch = new Locators(
				"css=div.octopus-tab-label:contains(Search)");
		public static final Locators zTabSearchSelected = new Locators(
				"css=div[class^=octopus-tab sc-collection-item sel]>div.octopus-tab-label:contains(Search)");
		public static final Locators zSearchHeader = new Locators(
				"css=div[id=search-header-view]");
		public static final Locators zSearchView = new Locators(
				"css=div[id=octopus-search-view]");
		public static final Locators zSearchNotificationListView = new Locators(
				"css=ul[id=search-notification-pending-page-list-view]");
		public static final Locators zSearchNotificationListItem = new Locators(
				"css=div[class=search-notification-list-item]");
		public static final Locators zSearchItemsView = new Locators(
				"css=div[id=search-items-view]");
		public static final Locators zSearchItemRow = new Locators(
				"css=div[class=search-item-row]");
		public static final Locators zIgnoredItemsView = new Locators(
				"css=div[id=ignored-items-view]");
		public static final Locators zIgnoredItemRow = new Locators(
				"css=div[class=search-item-row]");

		public final String locator;

		private Locators(String locator) {
			this.locator = locator;
		}
	}

	public PageSearch(AbsApplication application) {
		super(application);

		logger.info("new " + PageSearch.class.getCanonicalName());

	}

	/**
	 * Enter text into the query string field
	 * @param query
	 * @throws HarnessException 
	 */
	public void zAddSearchQuery(String query) throws HarnessException {
		logger.info(myPageName() + " zAddSearchQuery("+ query +")");
		
		tracer.trace("Search for the query "+ query);

		String locator = "css=div[id='octopus-search-field'] input";
		this.sType(locator, query);
		
	}

	/**
	 * Enter text into the query string field
	 * @param query
	 * @throws HarnessException 
	 */
	public void zExecuteSearchQuery(String query) throws HarnessException {
		logger.info(myPageName() + " zExecuteSearchQuery("+ query +")");
		
		// Add the query
		this.zAddSearchQuery(query);
		
		// Type "<Return>"
		String locator = "css=div[id=octopus-search-field] input";

		zKeyEvent(locator, "39", "keydown");
		zKeyEvent(locator, "39", "keydown");
		zKeyEvent(locator, "13", "keydown");

		zWaitForBusyOverlayOctopus();
	}
	

	public Toaster zGetToaster() throws HarnessException {
		return (new Toaster(this.MyApplication));
	}

	public DialogError zGetErrorDialog(DialogErrorID zmail) {
		return (new DialogError(zmail, this.MyApplication, this));
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		// Look for the Sharing tab
		boolean selected = sIsElementPresent(Locators.zTabSearchSelected.locator);

		if (!selected) {
			logger.debug("zIsActive(): " + selected);
			return (false);
		}

		logger.debug("isActive() = " + true);
		return (true);
	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public void zNavigateTo() throws HarnessException {

		if (zIsActive()) {
			// This page is already active
			return;
		}

		// Make sure PageOctopus page is active
		if (!((AppOctopusClient) MyApplication).zPageOctopus.zIsActive()) {
			((AppOctopusClient) MyApplication).zPageOctopus.zNavigateTo();
		}

		String locator = Locators.zTabSearch.locator;

		if (!zWaitForElementPresent(locator, "5000")) {
			throw new HarnessException(locator + " Not Present!");
		}

		// Click on Sharing tab
		zClickAt(locator, "0,0");

		zWaitForBusyOverlay();

		zWaitForActive();
	}

	public AbsPage zToolbarPressButton(Button button, IItem item)
			throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButton(" + button + ")");

		tracer.trace("Press the " + button + " button");

		if (button == null)
			throw new HarnessException("Button cannot be null!");

		// Default behavior variables
		//
		String locator = null; // If set, this will be clicked
		AbsPage page = null; // If set, this page will be returned

		// Based on the button specified, take the appropriate action(s)
		//

		if (button == Button.B_ADD_TO_MY_FILES) {
			// Check if the button is disabled
			locator = Locators.zSearchNotificationListItem.locator
					+ ":contains(" + item.getName()
					+ ") button:contains(Add to My Files)";

		} else if (button == Button.B_IGNORE) {
			// Check if the button is disabled
			locator = Locators.zSearchNotificationListItem.locator
					+ ":contains(" + item.getName()
					+ ") button:contains(Ignore)";

		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		/*
		 * if (locator == null) { throw new
		 * HarnessException("locator was null for button " + button); }
		 */
		// Default behavior, process the locator by clicking on it

		// Make sure the button exists
		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Button is not present locator="
					+ locator + " button=" + button);

		// Click it
		this.zClick(locator);

		// If the app is busy, wait for it to become active
		zWaitForBusyOverlay();

		return (page);
	}

	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		throw new HarnessException("Implement me");
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option)
			throws HarnessException {
		throw new HarnessException("Implement me");
	}

	@Override
	public AbsPage zListItem(Action action, String item)
			throws HarnessException {
		throw new HarnessException("Implement me");
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String item)
			throws HarnessException {
		throw new HarnessException("Implement me");
	}

	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption,
			String item) throws HarnessException {
		throw new HarnessException("Implement me");
	}
}
