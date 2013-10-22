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
package org.zmail.qa.selenium.projects.desktop.ui.tasks;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties.AppType;
import org.zmail.qa.selenium.projects.desktop.ui.*;



/**
 * @author Matt Rhoades
 *
 */
public class TreeTasks extends AbsTree {

	public static class Locators {
		public static final String ztih__main_Tasks__ZIMLET_ID = "ztih__main_Tasks__ZIMLET";
		public static final String ztih__main_Mail__ZIMLET_nodeCell_ID = "ztih__main_Mail__ZIMLET_nodeCell";
		public static final String zNewTagIcon = "//td[contains(@class,'overviewHeader-Text FakeAnchor')]/div[contains(@class,'ImgNewTag')]";
		public static final String zTagsHeader = "//td[contains(@id,'ztih__main_Tasks__TAG_textCell')]";
		public static final String zDeleteTreeMenuItem = "//div[contains(@class,'ZMenuItem')]//tbody//td[contains(@id,'_left_icon')]/div[contains(@class,'ImgDelete')]";
		public static final String zRenameTreeMenuItem = "//div[contains(@class,'ZMenuItem')]//tbody//td[contains(@id,'_left_icon')]/div[contains(@class,'ImgRename')]";
	}
	
	
	
		
	
	public TreeTasks(AbsApplication application) {
		super(application);
		logger.info("new " + TreeTasks.class.getCanonicalName());
	}
	


	/* (non-Javadoc)
	 * @see org.zmail.qa.selenium.framework.ui.AbsTree#zPressButton(org.zmail.qa.selenium.framework.ui.Button)
	 */
	@Override
	public AbsPage zPressButton(Button button) throws HarnessException {
		tracer.trace("Press the " + button + " button");

		if (button == null)
			throw new HarnessException("Button cannot be null");

		AbsPage page = null;
		String locator = null;
		if (button == Button.B_TREE_NEWTAG) {

			locator = Locators.zNewTagIcon;

			if (!this.sIsElementPresent(locator)) {
				throw new HarnessException("Unable to locator folder in tree "
						+ locator);
			}
			page = new DialogTag(MyApplication,((AppAjaxClient) MyApplication).zPageTasks);
		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		if (locator == null) {
			throw new HarnessException("locator was null for button " + button);
		}

		// Click it
		this.zClick(locator);

		// If the app is busy, wait for that to finish
		this.zWaitForBusyOverlay();

		// If page was specified, make sure it is active
		if (page != null) {

			// This function (default) throws an exception if never active
			page.zWaitForActive();

		}

		return (page);

	}

	/* (non-Javadoc)
	 * @see framework.ui.AbsTree#zTreeItem(framework.ui.Action, framework.items.FolderItem)
	 */
	public AbsPage zTreeItem(Action action, IItem tasklist) throws HarnessException {
		
		tracer.trace(action +" on folder = "+ tasklist.getName());

		AbsPage page = null;
		String locator = null;
		
		if ( !(tasklist instanceof FolderItem) )
			throw new HarnessException("folder must be of type FolderItem");
		
		// TODO: should be TaskListItem?
		FolderItem f = (FolderItem) tasklist;
		
		if ( action == Action.A_LEFTCLICK ) {
			if (ZmailSeleniumProperties.getAppType() == AppType.DESKTOP) {
			   locator = "css=[id^='zti__" + MyApplication.zGetActiveAccount().EmailAddress +
			         ":main_Tasks__'][id$=':" + f.getId() + "_textCell']";
			} else {
			   locator = "zti__main_Tasks__" + f.getId() + "_textCell";
			}
			
			// FALL THROUGH

		} else if ( action == Action.A_RIGHTCLICK ) {
			
		   if (ZmailSeleniumProperties.getAppType() == AppType.DESKTOP) {
            locator = "css=[id^='zti__" + MyApplication.zGetActiveAccount().EmailAddress +
                  ":main_Tasks__'][id$=':" + f.getId() + "_textCell']";
         } else {
            locator = "zti__main_Tasks__" + f.getId() + "_textCell";
         }

			// Select the folder
			this.zRightClick(locator);
			
			// return a context menu
			return (new ContextMenu(MyApplication));

		} else {
			throw new HarnessException("Action "+ action +" not yet implemented");
		}

		
		if ( locator == null )
			throw new HarnessException("locator is null for action "+ action);
		
		
		// Default behavior.  Click the locator
		zClick(locator);

		// If there is a busy overlay, wait for that to finish
		this.zWaitForBusyOverlay();
		
		if ( page != null ) {
			
			// Wait for the page to become active, if it was specified
			page.zWaitForActive();
		}

		((AppAjaxClient)MyApplication).zPageTasks.zWaitForDesktopLoadingSpinner(5000);

		return (page);

	}
		
	@Override
	public AbsPage zTreeItem(Action action, Button option, IItem tasklist)
			throws HarnessException {
		logger.info(myPageName() + " zListItem(" + action + ", " + option
				+ ", " + tasklist + ")");

		tracer.trace(action + " then " + option + " on task = "
				+ tasklist.getName());

		if (action == null)
			throw new HarnessException("action cannot be null");
		if (option == null)
			throw new HarnessException("button cannot be null");
		if (tasklist == null)
			throw new HarnessException("folder cannot be null");

		AbsPage page = null;
		String actionLocator = null;
		String optionLocator = null;
		// String itemLocator = null;

		// TODO: should be TaskList item?
		if (!(tasklist instanceof TagItem))
			throw new HarnessException("folder must be of type FolderItem");

		TagItem t = (TagItem) tasklist;

		tracer.trace("processing " + t.getName());

		if (action == Action.A_LEFTCLICK) {

			actionLocator = "implement me";

		} else if (action == Action.A_RIGHTCLICK) {

		   actionLocator = "css=[id^='zti__" + MyApplication.zGetActiveAccount().EmailAddress +
		         ":main_Tasks__'] td:contains('" + t.getName() + "')";

		   GeneralUtility.waitForElementPresent(this, actionLocator);
		   // actionLocator= Locators.zTagsHeader;
			this.zRightClick(actionLocator);

			page = new DialogTag(MyApplication,((AppAjaxClient) MyApplication).zPageTasks);

		} else {
			throw new HarnessException("Action " + action+ " not yet implemented");
		}
		if (option == Button.B_TREE_NEWTAG) {
			
			optionLocator = "//td[contains(@id,'_left_icon')]/div[contains(@class,'ImgNewTag')]";

		} else if (option == Button.B_DELETE) {

			optionLocator = Locators.zDeleteTreeMenuItem;

			page = new DialogWarning(
					DialogWarning.DialogWarningID.DeleteTagWarningMessage,
					MyApplication, ((AppAjaxClient) MyApplication).zPageTasks);

		} else if (option == Button.B_RENAME) {

		   optionLocator = Locators.zRenameTreeMenuItem;

		   page = new DialogRenameTag(MyApplication,((AppAjaxClient) MyApplication).zPageTasks);

		} else {
			throw new HarnessException("button " + option
					+ " not yet implemented");
		}
		if (actionLocator == null)
			throw new HarnessException("locator is null for action " + action);
		if (optionLocator == null)
			throw new HarnessException("locator is null for option " + option);

		// Default behavior. Click the locator
		zClick(optionLocator);

		// If there is a busy overlay, wait for that to finish
		this.zWaitForBusyOverlay();

		if (page != null) {

			// Wait for the page to become active, if it was specified
			page.zWaitForActive();
		}

		return (page);
	}




	/* (non-Javadoc)
	 * @see framework.ui.AbsTree#myPageName()
	 */
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public boolean zIsActive() throws HarnessException {

		// Make sure the main page is active
		if ( !((AppAjaxClient)MyApplication).zPageTasks.zIsActive() ) {
			((AppAjaxClient)MyApplication).zPageTasks.zNavigateTo();
		}
		
		// Zimlets seem to be loaded last
		// So, wait for the zimlet div to load
		String locator = Locators.ztih__main_Tasks__ZIMLET_ID;
		
		boolean loaded = this.sIsElementPresent(locator);
		if ( !loaded )
			return (false);
		
		return (loaded);

	}


}
