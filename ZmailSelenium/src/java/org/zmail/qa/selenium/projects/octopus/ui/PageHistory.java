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
package com.zimbra.qa.selenium.projects.octopus.ui;

import java.util.ArrayList;

import com.zimbra.qa.selenium.framework.items.HistoryItem;
import com.zimbra.qa.selenium.framework.items.IItem;
import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.ui.AbsPage;
import com.zimbra.qa.selenium.framework.ui.AbsTab;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.projects.octopus.core.CommonConstants;
import com.zimbra.qa.selenium.projects.octopus.ui.DialogError.DialogErrorID;


public class PageHistory extends AbsTab {
	public interface CONSTANTS extends CommonConstants{
		String VIEW_LOCATOR = "css=div.octopus-updates-view";
		String HEADER_VIEW_LOCATOR = VIEW_LOCATOR + " div#my-updates-header-view";
		String FILTER_VIEW_LOCATOR = VIEW_LOCATOR + " div.my-updates-filter-view";

		String[] FILTER_VIEW_TEXT = {"Refine",
				"Activity Type",
				"all types",
				"favorites",
				"comment",
				"sharing",
		"new version"};
		String[] checkboxes = {
				PageHistory.Locators.zHistoryFilterAllTypes.locator,
				PageHistory.Locators.zHistoryFilterFavorites.locator,
				PageHistory.Locators.zHistoryFilterComment.locator,
				PageHistory.Locators.zHistoryFilterSharing.locator,
				PageHistory.Locators.zHistoryFilterNewVersion.locator,
				PageHistory.Locators.zHistoryFilterRename.locator
		};

		//parallel array with checkboxes[]
		String[] historyRegexps = {
				"*",
				PageHistory.GetText.REGEXP.FAVORITE,
				PageHistory.GetText.REGEXP.COMMENT,
				PageHistory.GetText.REGEXP.SHARE,
				PageHistory.GetText.REGEXP.NEWVERSION,
				PageHistory.GetText.REGEXP.RENAME
		};

		// text displayed in Global History
		String YOU = "You";

		String NEW_VERSION_PREFIX = " created version 1 of file ";
		String NEW_VERSION_POSTFIX= ".";

		String FAVORITE_PREFIX  = " marked file ";
		String FAVORITE_POSTFIX = " as favorite.";

		String UNFAVORITE_PREFIX  = " marked file ";
		String UNFAVORITE_POSTFIX = " as not favorite.";

		String COMMENT_PREFIX = " added a comment on file ";
		String COMMENT_POSTFIX= ".";

		String RENAME_PREFIX = " renamed file ";
		String RENAME_FOLDER_PREFIX = " renamed folder ";


		String RENAME_MIDFIX = " as ";
		String RENAME_POSTFIX= ".";

		String MOVE_PREFIX=" moved file ";
		String MOVE_POSTFIX=" from folder ";
		String MOVE_PREFIX_FOLDER=" moved folder ";

		String DELETE_PREFIX=" deleted file ";
		String DELETE_POSTFIX=" from folder My Files un-delete";

		String SHARE_PREFIX=" enabled public link to file ";
		String SHARE_POSTFIX=" Copy Link";

		String CREATE_FOLDER_PREFIX=" created folder ";
		String CREATE_FOLDER_POSTFIX="new folder";

		String DELETE_FOLDER_PREFIX=" deleted folder ";
		String DELETE_FOLDER_POSTFIX=" from folder My Files un-delete";
	}

	public static boolean notSetup= true;
	public static class Locators {
		public static final Locators zTabHistory = new Locators(
				"css=div.octopus-tab-label:contains(History)");
		public static final Locators zTabHistorySelected = new Locators(
				"css=div[class^=octopus-tab sc-collection-item sel]>div.octopus-tab-label:contains(History)");
		public static final Locators zHistoryView = new Locators(
				"css=div[id=octopus-updates-view]");
		public static final Locators zHistoryHeader = new Locators(
				"css=div[id=my-updates-header-view]");
		public static final Locators zHistoryItemsView = new Locators(
				"css=div[id=my-updates-view]");
		public static final Locators zHistoryItemRow = new Locators(
				"css=div[class=activity-stream-pane-list-item]");
		public static final Locators zHistoryFilterView = new Locators(
				"css=div[class=my-updates-filter-view]");
		public static final Locators zHistoryFilterAllTypes = new Locators(
				"css=input[id=filter_alltypes]");
		public static final Locators zHistoryFilterFavorites = new Locators(
				"css=input[id=filter_watched]");
		public static final Locators zHistoryFilterComment = new Locators(
				"css=input[id=filter_comment]");
		public static final Locators zHistoryFilterSharing = new Locators(
				"css=input[id=filter_sharing]");
		public static final Locators zHistoryFilterNewVersion = new Locators(
				"css=input[id=filter_new]");
		public static final Locators zHistoryFilterRename = new Locators(
				"css=input[id=filter_rename]");
		public static final Locators zHistoryFolderLink=new Locators(
				"css=[class='activity-item-container'] [class='action to-folder']");
		public static final Locators zHistoryVersionLink=new Locators(
				"css=[class='activity-item-container'] [class='action version']");

		public final String locator;

		private Locators(String locator) {
			this.locator = locator;
		}
	}

	//Get text displayed in global history
	public static class GetText implements CONSTANTS{
		//TODO: file history text
		public interface REGEXP {
			String NEWVERSION = REGEXP_USER + NEW_VERSION_PREFIX
					+   REGEXP_FILENAME + NEW_VERSION_POSTFIX;

			String   RENAME = REGEXP_USER +  "(" + RENAME_PREFIX +"|" + RENAME_FOLDER_PREFIX + ")"
					+   REGEXP_FILENAME + RENAME_MIDFIX + REGEXP_FILENAME + RENAME_POSTFIX;

			String COMMENT  = REGEXP_USER +  COMMENT_PREFIX
					+   REGEXP_FILENAME + COMMENT_POSTFIX;

			String FAVORITE = REGEXP_USER + FAVORITE_PREFIX
					+   REGEXP_FILENAME + "(" + FAVORITE_POSTFIX + "|" + UNFAVORITE_POSTFIX + ")";

			//TODO: Add regexp connstants for folder name & email
			String   SHARE  = REGEXP_USER + " (granted|revoked) " + "(Read|RW|Admin)" + " access on folder " + REGEXP_FILENAME + " to " + REGEXP_USER+ ".";;


		}


		public static String share(SHARE_PERMISSION permission, String folderName, ZimbraAccount grantee){
			String access="";

			switch (permission) {
			case SHARE_AS_READ: access="Read"; break;
			case SHARE_AS_READWRITE: access="RW"; break;
			case SHARE_AS_ADMIN: access="Admin";
			}

			//TODO veify if grantee.EmailAddress or grantee.username is used
			return YOU + " granted " + access + " access on folder " + folderName + " to " + grantee.EmailAddress + ".";

		}


		public static String revoke(SHARE_PERMISSION permission, String folderName, ZimbraAccount grantee){
			String access="";

			switch (permission) {
			case SHARE_AS_READ: access="Read"; break;
			case SHARE_AS_READWRITE: access="RW"; break;
			case SHARE_AS_ADMIN: access="Admin";
			}

			//TODO veify if grantee.EmailAddress or grantee.username is used
			return YOU + " revoked " + access + " access on folder " + folderName + " to " + grantee.EmailAddress +".";
		}


		public static String rename(String oldName, String newName, String... forFolder) {
			return YOU +  ((forFolder.length ==1)?RENAME_FOLDER_PREFIX:RENAME_PREFIX)
					+   oldName + RENAME_MIDFIX + newName + RENAME_POSTFIX;
		}


		public static String comment(String fileName) {
			return YOU +  COMMENT_PREFIX
					+   fileName + COMMENT_POSTFIX;
		}

		public static String newVersion(String fileName) {
			return YOU +  NEW_VERSION_PREFIX
					+   fileName + NEW_VERSION_POSTFIX;
		}

		public static String newVersion(String fileName, String user) {
			return  user +  NEW_VERSION_PREFIX
					+   fileName + NEW_VERSION_POSTFIX;
		}

		public static String favorite(String fileName) {
			return YOU + FAVORITE_PREFIX
					+   fileName + FAVORITE_POSTFIX;
		}

		public static String favorite(String fileName, String user) {
			return          user + FAVORITE_PREFIX
					+   fileName + FAVORITE_POSTFIX;
		}

		public static String unfavorite(String fileName) {
			return YOU + UNFAVORITE_PREFIX
					+   fileName + UNFAVORITE_POSTFIX;
		}

		public static String unfavorite(String fileName, String user) {
			return          user + UNFAVORITE_PREFIX
					+   fileName + UNFAVORITE_POSTFIX;
		}

		public static String move(String fileName,String rootFolder,String folderName) {
			return YOU +  MOVE_PREFIX
					+   fileName + MOVE_POSTFIX  + rootFolder + " to folder " + folderName +".";
		}

		public static String deleteFile(String fileName) {
			return YOU +  DELETE_PREFIX
					+   fileName + DELETE_POSTFIX;
		}

		public static String shareFile(String fileName) {
			return YOU +  SHARE_PREFIX
					+   fileName + SHARE_POSTFIX;
		}

		public static String createFolder() {
			return YOU + CREATE_FOLDER_PREFIX
					+  CREATE_FOLDER_POSTFIX;
		}

		public static String deleteFolder(String folderName) {
			return YOU + DELETE_FOLDER_PREFIX + folderName
					+ DELETE_FOLDER_POSTFIX;
		}
		public static String moveFolder(String rootFolder,String folderName) {
			return YOU +  MOVE_PREFIX_FOLDER
					+ rootFolder +  MOVE_POSTFIX  + "My Files to folder " + folderName +".";
		}

	}

	public PageHistory(AbsApplication application) {
		super(application);

		logger.info("new " + PageSharing.class.getCanonicalName());

	}

	public Toaster zGetToaster() throws HarnessException {
		return (new Toaster(this.MyApplication));
	}

	public DialogError zGetErrorDialog(DialogErrorID zimbra) {
		return (new DialogError(zimbra, this.MyApplication, this));
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		// Look for the Sharing tab
		boolean selected = sIsElementPresent(Locators.zTabHistorySelected.locator);

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

		String locator = Locators.zTabHistory.locator;

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

		throw new HarnessException("No logic defined for: " + button + " :"
				+ item);

	}

	public void zToolbarCheckMark(String locator,boolean check) throws HarnessException {
		logger.info(myPageName() + " zToolbarCheckOption(" + locator + ")");

		tracer.trace(check + " check " + locator + " option");

		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Check box is not present: " + locator);

		//already checked, just return
		if (check && sIsChecked(locator))
			return;

		//already unchecked, just return
		if (!check && !sIsChecked(locator))
			return;



		if (check)	{
			sCheck(locator);
		}
		else
			this.sUncheck(locator);

		// extra actions to trigger js
		sFocus(locator);
		zClick(locator);


		// If the app is busy, wait for it to become active
		zWaitForBusyOverlay();
	}


	public AbsPage zToolbarCheckMark(Button option) throws HarnessException {
		logger.info(myPageName() + " zToolbarCheckOption(" + option + ")");

		tracer.trace("Check the " + option + " option");

		if (option == null)
			throw new HarnessException("Check box cannot be null!");

		// Default behavior variables
		//
		String locator = null; // If set, this will be clicked
		AbsPage page = null; // If set, this page will be returned

		if (option == Button.O_ALL_TYPES) {
			locator = Locators.zHistoryFilterAllTypes.locator;
		} else if (option == Button.O_FAVORITES) {
			locator = Locators.zHistoryFilterFavorites.locator;
		} else if (option == Button.O_COMMENT) {
			locator = Locators.zHistoryFilterComment.locator;
		} else if (option == Button.O_SHARING) {
			locator = Locators.zHistoryFilterSharing.locator;
		} else if (option == Button.O_NEW_VERSION) {
			locator = Locators.zHistoryFilterNewVersion.locator;
		} else if (option == Button.O_RENAME) {
			locator = Locators.zHistoryFilterRename.locator;
		} else {
			throw new HarnessException("no logic defined for check box " + option);
		}

		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Check box is not present: " + locator);

		// Check box
		this.sCheck(locator);

		// If the app is busy, wait for it to become active
		zWaitForBusyOverlay();

		return (page);
	}


	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButton(" + button + ")");

		tracer.trace("Press the " + button + " button");

		if (button == null)
			throw new HarnessException("Check box cannot be null!");

		// Default behavior variables
		//
		String locator = null; // If set, this will be clicked
		AbsPage page = null; // If set, this page will be returned

		if (button == Button.O_ALL_TYPES) {
			locator = Locators.zHistoryFilterAllTypes.locator;
		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		if (!this.sIsElementPresent(locator))
			throw new HarnessException("button is not present: " + locator);

		// Check box
		this.zClick(locator);

		// If the app is busy, wait for it to become active
		zWaitForBusyOverlay();

		return (page);
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option)
			throws HarnessException {
		throw new HarnessException("Implement me");
	}

	// return a list of history items
	public ArrayList<HistoryItem> zListItem()
			throws HarnessException
			{
		ArrayList<HistoryItem> historyItems = new ArrayList<HistoryItem>();

		// Is this necessary?
		this.zWaitForBusyOverlayOctopus();

		String listLocator = "css=div[id='my-updates-view'] div[class='activity-stream-pane-list-item']";
		int count = sGetCssCount(listLocator);
		logger.debug("total items= " + count);
		for (int i = 1; i <= count; i++) {

			String locator;
			String itemLocator = listLocator + ":nth-of-type("+ i +")";

			HistoryItem item = new HistoryItem();

			// Set the locator to the item
			item.setLocator(itemLocator);

			// Get the user
			locator = itemLocator + " span[class='activity-item-body'] span[class*='user']";
			item.setHistoryUser(sGetText(locator));

			// Get the Time
			locator = itemLocator + " span[class='activity-item-time']";
			item.setHistoryTime(sGetText(locator));

			// Get the Comment Text
			locator = itemLocator + " span[class='activity-item-body']";
			item.setHistoryText(sGetText(locator));

			logger.info(item.prettyPrint());

			historyItems.add(item);
		}


		return historyItems;
			}

	///@SuppressWarnings("null")
	public String verifyHistory(String expResult) throws HarnessException
	{
		return isTextPresentInGlobalHistory(expResult).getHistoryText();


	}

	// check if the history text present in global history
	public HistoryItem isTextPresentInGlobalHistory(String historyText)
			throws HarnessException
			{
		// Get global history
		ArrayList<HistoryItem> historyItems= zListItem();


		HistoryItem found = null;
		logger.info("Seek...|" + historyText + "|");

		// Verify history item appears in the activity history
		for ( HistoryItem item : historyItems ) {
			logger.info("|" + item.getHistoryText() + "|");
			// Verify the history is found
			if (item.getHistoryText().equals(historyText)) {
				found = item;
				break;
			}

		}

		return found;
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
