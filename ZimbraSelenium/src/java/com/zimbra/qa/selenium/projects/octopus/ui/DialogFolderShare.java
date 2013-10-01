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
import com.zimbra.qa.selenium.projects.octopus.ui.PageSharing.Locators;

public class DialogFolderShare extends AbsDialog {
	public static class Locators {
		public static final Locators zShareBtn = new Locators(
				"css=div[class=share-buttons]>button:contains(Share)");
		public static final Locators zCancelBtn = new Locators(
				"css=div[class=share-buttons]>button:contains(Cancel)");
		public static final Locators zLeaveThisSharedFolderBtn = new Locators(
				"css=div[class=share-buttons] span[class^=customLink]:contains(Leave this shared folder)");
		public static final Locators zViewInput = new Locators(
				"css=div[class=octopus-share-item-view]>div[class=permission-input] input[id=DWT1]");
		public static final Locators zViewAndEditInput = new Locators(
				"css=div[class=octopus-share-item-view]>div[class=permission-input] input[id=DWT2]");
		public static final Locators zViewEditAndShareInput = new Locators(
				"css=div[class=octopus-share-item-view]>div[class=permission-input] input[id=DWT3]");
		public static final Locators zShowMessageLink = new Locators(
				"css=div[class=octopus-share-item-view]>div[class=permission-label info-message] span[class^=customLink]>span:contains(Show message)");
		public static final Locators zMessageInput = new Locators(
				"css=div[class=octopus-share-item-view]>div[class=permission-input] textarea[class=field]");
		public static final Locators zShareInfoTitle = new Locators(
				"css=div[class=ShareInfo]>div[class=ShareInfoTitle]");
		public static final Locators zShareInfoAddrBubble = new Locators(
				"css=div[class=ShareInfo] div[class='GranteeName addrBubble']");
		public static final Locators zShareInfoExpandAddrBubble = new Locators(
				"css=div[class=ShareInfo]>div[class=ShareInfoTitle]:contains(\u25BA)");
		public static final Locators zShareInfoCollapseAddrBubble = new Locators(
				"css=div[class=ShareInfo]>div[class=ShareInfoTitle]:contains(\u25BC)");
		public static final Locators zInavlidEmailFormatMessage = new Locators(
				"css=div[class=' ShareDialogContent'] span div[class='permission-label info-message']:contains('invalid email addresses')");
		public static final Locators zSharingFailedToastMessage=new Locators(
				"css=*[id='octopus-status-msg']:contains('Sharing of folder')");
		public static final Locators zHideMessageLink = new Locators(
				"css=div[class=octopus-share-item-view]>div[class=permission-label info-message] span[class^=customLink]>span:contains(Hide message)");
		public static final Locators zPermissionLabel = new Locators(
				"css=[class='octopus-share-item-view'] [class='permission-label']");
		public static final Locators zExpandArrow = new Locators(
				"css=[class='ShareInfo'] span");
		public static final Locators zSharePermission = new Locators(
				"css=[class='ShareInfoFieldLabel']");
				
		public final String locator;

		private Locators(String locator) {
			this.locator = locator;
		}
	}

	public DialogFolderShare(AbsApplication application, AbsTab page) {
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

		if (button == Button.B_SHARE) {

			locator = Locators.zShareBtn.locator;

		} else if (button == Button.B_CANCEL) {

			locator = Locators.zCancelBtn.locator;
		} else if (button == Button.B_SHOW_MESSAGE) {

			locator = Locators.zShowMessageLink.locator;
		} else if (button == Button.B_LEAVE_THIS_SHARED_FOLDER) {

			locator = Locators.zLeaveThisSharedFolderBtn.locator;
		} else if (button == Button.B_EXPAND) {

			locator = Locators.zShareInfoExpandAddrBubble.locator;
		} else if (button == Button.B_COLLAPSE) {

			locator = Locators.zShareInfoCollapseAddrBubble.locator;
		} else {
			throw new HarnessException("Button " + button + " not implemented");
		}

		// Default behavior, click the locator
		//
		// Make sure the locator exists
		if (!this.sIsElementPresent(locator))
			throw new HarnessException("locator is not present: " + locator
					+ " button=" + button);

		this.zClick(locator);

		this.zWaitForBusyOverlay();

		return (page);
	}

	public void zClick(Locators field) throws HarnessException {

		String locator = field.locator;

		// Check if the locator is present
		if (!sIsElementPresent(locator)) {
			logger.info("zClick(" + locator + ") element is not present");
			throw new HarnessException("zClick(" + locator
					+ ") element is not present");

		}

		this.sMouseDown(locator);
		this.sMouseUp(locator);

		logger.info("zClick(" + locator + ")");
	}

	public String zRetrieveText(String locator) throws HarnessException {
		String text = sGetEval("var x = selenium.browserbot.findElementOrNull('"
				+ locator + "');if(x!=null){x.value;}");

		return text;
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

	/**
	 * Left-Click on a folder in the tree
	 * 
	 * @param folder
	 * @throws HarnessException
	 */
	public void zTypeInput(Locators field, String name) throws HarnessException {

		logger.info(myPageName() + " Click on " + field + ")");

		if (field == null)
			throw new HarnessException("folder must not be null");

		String locator = field.locator;

		if (this.zWaitForElementPresent(locator, "3000")) {
			sClickAt(locator, "");
		} else {
			throw new HarnessException(locator + " not present");
		}

		sType(locator, name);

		zKeyEvent(locator, "13", "keyup");
		zKeyEvent(locator, "13", "keydown");
	}
}
