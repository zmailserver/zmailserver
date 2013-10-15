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
package org.zmail.qa.selenium.projects.octopus.ui;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.HarnessException;

public class DialogMove extends AbsDialog {
	public static class Locators {
		public static final Locators zMoveItemPageListView = new Locators(
		// "css=div[class=sc-view]
		// div[id=move-item-page-list-view]>div[class^=sc-view
		// sc-list-item-view]>div[class=sc-outline]>label
				"css=div[id=move-item-page-list-view]");

		public final String locator;

		private Locators(String locator) {
			this.locator = locator;
		}
	}

	public DialogMove(AbsApplication application, AbsTab page) {
		super(application, page);

		logger.info("new " + DialogMove.class.getCanonicalName());
	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public AbsPage zClickButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zClickButton(" + button + ")");

		AbsPage page = null;
		String locator = null;

		if (button == Button.B_NEW) {

			locator = "css=div[id='ChooseFolderDialog_buttons'] td[id^='New_'] td[id$='_title']";

		} else if (button == Button.B_OK) {

			locator = "css=div[id='ChooseFolderDialog_buttons'] td[id^='OK_'] td[id$='_title']";

		} else if (button == Button.B_CANCEL) {

			locator = "css=div[id='ChooseFolderDialog_buttons'] td[id^='Cancel_'] td[id$='_title']";

		} else {
			throw new HarnessException("Button " + button + " not implemented");
		}

		// Default behavior, click the locator
		//

		this.zClick(locator);

		this.zWaitForBusyOverlay();

		return (page);
	}

	@Override
	public String zGetDisplayedText(String locator) throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean zIsActive() throws HarnessException {

		logger.info(myPageName() + " zIsActive()");

		String locator = "css=div[id='" + "']";

		if (!this.sIsElementPresent(locator)) {
			return (false); // Not even present
		}

		if (!this.zIsVisiblePerPosition(locator, 0, 0)) {
			return (false); // Not visible per position
		}

		// Yes, visible
		logger.info(myPageName() + " zIsActive() = true");
		return (true);

	}

	/**
	 * Left-Click on a folder in the tree
	 * 
	 * @param folder
	 * @throws HarnessException
	 */
	public void zDoubleClickTreeFolder(String folderName)
			throws HarnessException {

		logger.info(myPageName() + " zClickTreeFolder(" + folderName + ")");

		if (folderName == null)

			throw new HarnessException("folder must not be null");
		String locator = Locators.zMoveItemPageListView.locator
				+ " label:contains(" + folderName + ")";

		if (this.zWaitForElementPresent(locator, "3000")) {
			sClickAt(locator, "");
			this.zWaitForElementDeleted(locator, "3000");
			if (this.sIsElementPresent(locator))
				sClickAt(locator, "");

		} else {
			throw new HarnessException(locator + " not present");
		}

		this.zWaitForBusyOverlay(); // This method call seems to be missing from
		// the briefcase function

	}
}
