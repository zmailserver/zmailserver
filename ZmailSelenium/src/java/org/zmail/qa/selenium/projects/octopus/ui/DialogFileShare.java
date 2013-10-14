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
package com.zimbra.qa.selenium.projects.octopus.ui;

import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.HarnessException;

public class DialogFileShare extends AbsDialog {
	public static class Locators {
		public static final Locators zStopSharingBtn = new Locators(
				"css=div[class=share-buttons]>button:contains(Stop Sharing)");
		public static final Locators zCloseBtn = new Locators(
				"css=div[id=activity-stream-pane-menu-bar]>span[id=activity-stream-pane-close-button]");

		public final String locator;

		private Locators(String locator) {
			this.locator = locator;
		}
	}

	public DialogFileShare(AbsApplication application, AbsTab page) {
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

		if (button == Button.B_STOP_SHARING) {

			locator = Locators.zStopSharingBtn.locator;

		} else if (button == Button.B_CLOSE) {

			locator = Locators.zCloseBtn.locator;

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

		String locator = "css=div[id='activity-stream-pane-menu-bar']";

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
}
