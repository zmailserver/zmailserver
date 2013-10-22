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
package com.zimbra.qa.selenium.projects.ajax.ui.briefcase;

import java.util.*;

import org.openqa.selenium.By;
import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.ui.*;

/**
 * @author zimbra
 * 
 */

public class TreeBriefcase extends AbsTree {

	public static class Locators {
		public static final String briefcaseListView = "css=[id='zl__BDLV']";
		public static final String briefcaseTreeView = "css=[id*=zti__main_Briefcase__";
		public static final String zNewTagTreeMenuItem = "css=td[id^=NEW_TAG__][id$=_title]";
		public static final String zNewFolderTreeMenuItem = "//div[contains(@id,NEW_BRIEFCASE)]//tr[contains(@id,POPUP_NEW_BRIEFCASE)]//td[contains(text(),'New Folder')]";
		public static final String zRenameTagTreeMenuItem = "css=td[id$=_left_icon]>[class=ImgRename]";
		public static final String zDeleteTreeMenuItem = "css=td[id^=DELETE_WITHOUT_SHORTCUT][id$=_title]";
		public static final String zEditPropertiesTreeMenuItem = "css=div[id=EDIT_PROPS] tr[id=POPUP_EDIT_PROPS]:contains('Edit Properties')";
	}

	public TreeBriefcase(AbsApplication application) {
		super(application);
		logger.info("new " + TreeBriefcase.class.getCanonicalName());
	}

	@Override
	public AbsPage zTreeItem(Action action, Button option, IItem item)
			throws HarnessException {
		// Validate the arguments
		if ((action == null) || (option == null) || (item == null)) {
			throw new HarnessException(
					"zTreeItem(Action, Button, IItem): Must define an action, option, and item");
		}

		logger.info(myPageName() + " zTreeItem(" + action + ", " + option
				+ ", " + item.getName() + ")");

		tracer.trace("Click " + action + " then " + option + " on item "
				+ item.getName());

		AbsPage page = null;
		String actionLocator = null;
		String optionLocator = null;

		if (item instanceof TagItem) {
			actionLocator = "css=td[id^=zti__main_Briefcase__]:contains(" + ((TagItem) item).getName()
				+ ")";
		} else if (item instanceof FolderItem) {
			actionLocator = "css=td[id^=zti__main_Briefcase__"
					+ ((FolderItem) item).getId() + "_textCell]";
		} else {
			throw new HarnessException("Must use IItem as argument, but was "
					+ item.getClass());
		}
		
		zWaitForElementVisible(actionLocator);	

		if (action == Action.A_RIGHTCLICK) {
			this.zRightClickAt(actionLocator, "0,0");
		} else {
			throw new HarnessException("implement me! " + action
					+ ": not implemented");
		}

		if (option == Button.B_TREE_NEWTAG) {

			optionLocator = Locators.zNewTagTreeMenuItem;

			page = new DialogTag(MyApplication,
					((AppAjaxClient) MyApplication).zPageBriefcase);

		} else if (option == Button.B_TREE_RENAMETAG) {

			optionLocator = Locators.zRenameTagTreeMenuItem;

			page = new DialogRenameTag(MyApplication,
					((AppAjaxClient) MyApplication).zPageBriefcase);
		} else if (option == Button.B_TREE_DELETE) {

			optionLocator = Locators.zDeleteTreeMenuItem;

			if (item instanceof TagItem) {
				page = new DialogWarning(
						DialogWarning.DialogWarningID.DeleteTagWarningMessage,
						MyApplication,
						((AppAjaxClient) MyApplication).zPageBriefcase);
			}
		} else if (option == Button.B_TREE_NEWFOLDER) {

			optionLocator = Locators.zNewFolderTreeMenuItem;

			page = new DialogCreateBriefcaseFolder(MyApplication,
					((AppAjaxClient) MyApplication).zPageBriefcase);

		} else if (option == Button.B_TREE_EDIT_PROPERTIES) {

			optionLocator = Locators.zEditPropertiesTreeMenuItem;

			page = new DialogEditProperties(MyApplication,
					((AppAjaxClient) MyApplication).zPageBriefcase);

		} else {
			throw new HarnessException("button " + option
					+ " not yet implemented");
		}

		this.zWaitForBusyOverlay();
		
		zWaitForElementVisible(optionLocator);		
		
		// Default behavior. Click the locator
		zClickAt(optionLocator,"");
 
		// If there is a busy overlay, wait for that to finish
		this.zWaitForBusyOverlay();

		if (page != null) {
			// Wait for the page to become active, if it was specified
			page.zWaitForActive();
		}
		return (page);
	}

	public AbsPage zTreeItem(Action action, IItem item, boolean isRowAdded)
			throws HarnessException {

		tracer.trace("Click " + action + " on folder " + item.getName());

		String treeItemLocator = Locators.briefcaseTreeView + "]";

		AbsPage page = zTreeItem(action, item);

		zWaitForElementPresent(treeItemLocator);

		String listItemLocator = "css=div[id^=zl__BDLV][class=DwtListView-Rows]";

		if (isRowAdded)
			listItemLocator += ">div[id^=zli__BDLV][class^=Row]";

		zWaitForElementPresent(listItemLocator);

		return page;
	}

	@Override
	public AbsPage zTreeItem(Action action, IItem item) throws HarnessException {
		// Validate the arguments
		if ((action == null) || (item == null)) {
			throw new HarnessException("zTreeItem(Action, IItem): Must define an action, and item");
		}
		logger.info(myPageName() + " zTreeItem(" + action + ", "
				+ item.getName() + ")");

		tracer.trace("Click " + action + " on item " + item.getName());

		AbsPage page = null;
		String locator = null;

		if (item instanceof TagItem) {
			locator = "css=td[id^=zti__main_Briefcase__]:contains(" + ((TagItem) item).getName()
				+ ")";
		} else if (item instanceof FolderItem) {
			locator = Locators.briefcaseTreeView + ((FolderItem) item).getId()
					+ "_imageCell]";

		} else if (item instanceof LinkItem) {
			page = new DialogFindShares(MyApplication,
					((AppAjaxClient) MyApplication).zPageBriefcase);
			if (ZimbraSeleniumProperties.isWebDriver()) {
				clickBy(By.id("ztih__main_Briefcase__BRIEFCASE"),
					By.linkText("Find Shares..."));
				return page;
			}else{
				locator = "css=div[id=ztih__main_Briefcase__BRIEFCASE] a[id$=_addshare_link]";
				page = new DialogFindShares(MyApplication,
					((AppAjaxClient) MyApplication).zPageBriefcase);
			}
			
			if (!this.sIsElementPresent(locator)) {
				throw new HarnessException("Unable to locate link in the tree "
						+ locator);
			}

			this.sClickAt(locator, "0,0");

			zWaitForBusyOverlay();

			return page;

		} else {
			throw new HarnessException("Must use IItem as argument, but was "
					+ item.getClass());
		}

		if (action == Action.A_LEFTCLICK) {

			zWaitForBusyOverlay();

			// FALL THROUGH
		} else if (action == Action.A_RIGHTCLICK) {

			if (!this.sIsElementPresent(locator))
				throw new HarnessException(
						"Unable to locate folder in the tree " + locator);

			// Select the folder
			this.zRightClickAt(locator, "0,0");

			// return a context menu
			return (new ContextMenu(MyApplication));
		} else {
			throw new HarnessException("Action " + action
					+ " not yet implemented");
		}

		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Unable to locate folder in the tree "
					+ locator);

		// Default behavior. Click the locator
		zClickAt(locator, "0,0");

		// If there is a busy overlay, wait for that to finish
		zWaitForBusyOverlay();
		
		return (page);
	}

	@Override
	public AbsPage zPressButton(Button button) throws HarnessException {
		tracer.trace("Click " + button);

		if (button == null)
			throw new HarnessException("Button cannot be null");

		AbsPage page = null;
		String locator = null;

		if (button == Button.B_TREE_NEWBRIEFCASE) {

			locator = "css=div[id=ztih__main_Briefcase__BRIEFCASE] div[class^=ImgNewFolder ZWidget]";
			page = new DialogCreateBriefcaseFolder(MyApplication,
					((AppAjaxClient) MyApplication).zPageBriefcase);

			if (!this.sIsElementPresent(locator)) {
				throw new HarnessException(
						"Unable to locate folder in the tree " + locator);
			}

			this.zClickAt(locator, "0,0");

			zWaitForBusyOverlay();

			return page;

		} else if (button == Button.B_TREE_NEWTAG) {

			locator = "css=div[class^=ImgNewTag ZWidget]";

			if (!this.sIsElementPresent(locator)) {
				throw new HarnessException("Unable to locate folder in tree "
						+ locator);
			}
			page = new DialogTag(MyApplication,
					((AppAjaxClient) MyApplication).zPageBriefcase);
		} else if (button == Button.B_TREE_BRIEFCASE_EXPANDCOLLAPSE) {

			locator = null;
			page = null;

			// TODO: implement me

			// FALL THROUGH
		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		if (locator == null) {
			throw new HarnessException("locator was null for button " + button);
		}

		// Default behavior, process the locator by clicking on it
		//

		// Click it
		this.zClickAt(locator, "0,0");

		// If the app is busy, wait for that to finish
		zWaitForBusyOverlay();

		// If page was specified, make sure it is active
		if (page != null) {

			// This function (default) throws an exception if never active
			page.zWaitForActive();
		}
		return (page);
	}

	public AbsPage zPressPulldown(Button pulldown, Button option)
			throws HarnessException {
		logger.info(myPageName() + " zPressPulldown(" + pulldown + ", "
				+ option + ")");

		tracer.trace("Click " + pulldown + " then " + option);

		if (pulldown == null)
			throw new HarnessException("Pulldown cannot be null");

		if (option == null)
			throw new HarnessException("Option cannot be null");

		AbsPage page = null;
		String pulldownLocator = null;
		String optionLocator = null;

		if (pulldown == Button.B_TREE_FOLDERS_OPTIONS) {

			pulldownLocator = "css=div[id='ztih__main_Briefcase__BRIEFCASE_div'] td[id='ztih__main_Briefcase__BRIEFCASE_optCell'] div[class=ImgContextMenu]";

			if (option == Button.B_TREE_NEWFOLDER) {

				optionLocator = "css=div[id='ZmActionMenu_briefcase_BRIEFCASE'] div[id='NEW_BRIEFCASE'] td[id$='_title']";
				page = new DialogCreateBriefcaseFolder(MyApplication,
						((AppAjaxClient) MyApplication).zPageBriefcase);

			} else {
				throw new HarnessException("Pulldown/Option " + pulldown + "/"
						+ option + " not implemented");
			}

			// FALL THROUGH

		} else if (pulldown == Button.B_TREE_TAGS_OPTIONS) {

			pulldownLocator = "css=div[id='zov__main_Briefcase'] td[id='ztih__main_Briefcase__TAG_optCell'] div[class=ImgContextMenu]";

			if (option == Button.B_TREE_NEWTAG) {

				optionLocator = "css=div[id='ZmActionMenu_briefcase_TAG'] div[id='NEW_TAG'] td[id$='_title']";
				page = new DialogTag(MyApplication,
						((AppAjaxClient) MyApplication).zPageBriefcase);

			} else {
				throw new HarnessException("Pulldown/Option " + pulldown + "/"
						+ option + " not implemented");
			}

			// FALL THROUGH

		} else {
			throw new HarnessException("Pulldown/Option " + pulldown + "/"
					+ option + " not implemented");
		}

		// Default behavior
		if (pulldownLocator != null) {

			// Make sure the locator exists
			if (!this.sIsElementPresent(pulldownLocator)) {
				throw new HarnessException("Button " + pulldown + " option "
						+ option + " pulldownLocator " + pulldownLocator
						+ " not present!");
			}

			// 8.0 change ... need zClickAt()
			// this.zClick(pulldownLocator);
			this.zClickAt(pulldownLocator, "0,0");

			// If the app is busy, wait for it to become active
			zWaitForBusyOverlay();

			if (optionLocator != null) {

				// Make sure the locator exists
				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException("Button " + pulldown
							+ " option " + option + " optionLocator "
							+ optionLocator + " not present!");
				}

				// 8.0 change ... need zClickAt()
				// this.zClick(optionLocator);
				this.zClickAt(optionLocator, "0,0");

				// If the app is busy, wait for it to become active
				zWaitForBusyOverlay();
			}

			// If we click on pulldown/option and the page is specified, then
			// wait for the page to go active
			if (page != null) {
				page.zWaitForActive();
			}
		}

		// Return the specified page, or null if not set
		return (page);

	}

	public List<TagItem> zListGetTags() throws HarnessException {

		List<TagItem> items = new ArrayList<TagItem>();

		// TODO: implement me!

		// Return the list of items
		return (items);
	}

	public void zExpandFolders() throws HarnessException {
		throw new HarnessException("implement me!");
	}

	public boolean zIsFoldersExpanded() throws HarnessException {
		throw new HarnessException("implement me!");
	}

	public void zExpandTags() throws HarnessException {
		throw new HarnessException("implement me!");
	}

	public boolean zIsTagsExpanded() throws HarnessException {
		throw new HarnessException("implement me!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see framework.ui.AbsTree#myPageName()
	 */
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public boolean zIsActive() throws HarnessException {

		// Make sure the briefcase page is active
		if (!((AppAjaxClient) MyApplication).zPageBriefcase.zIsActive()) {
			((AppAjaxClient) MyApplication).zPageBriefcase.zNavigateTo();
		}

		boolean loaded = this.sIsElementPresent(Locators.briefcaseListView);
		if (!loaded)
			return (false);

		return (loaded);
	}
}
