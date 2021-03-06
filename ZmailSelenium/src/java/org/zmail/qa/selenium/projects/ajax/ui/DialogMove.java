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
package org.zmail.qa.selenium.projects.ajax.ui;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties.AppType;
import org.zmail.qa.selenium.projects.ajax.ui.addressbook.PageAddressbook;
import org.zmail.qa.selenium.projects.ajax.ui.briefcase.PageBriefcase;
import org.zmail.qa.selenium.projects.ajax.ui.calendar.PageCalendar;
import org.zmail.qa.selenium.projects.ajax.ui.mail.PageMail;
import org.zmail.qa.selenium.projects.ajax.ui.tasks.PageTasks;

public class DialogMove extends AbsDialog {
	public static class Locators {

		// TODO:  See https://bugzilla.zmail.com/show_bug.cgi?id=54173
		public static final String zDialogId			= "ChooseFolderDialog";
		public static final String zTitleId	 			= "ChooseFolderDialog_title";
		public static final String zDialogContentId		= "ChooseFolderDialog_content";
		// TODO: Tree
		public static final String zDialogInputId		= "ChooseFolderDialog_inputDivId";
		public static final String zDialogInputLocator	= "css=div[id='"+ zDialogId +"'] div[id='"+ zDialogInputId +"'] > div > input";
		public static final String zDialogButtonsId		= "ChooseFolderDialog_buttons";

	}

	public DialogMove(AbsApplication application,AbsTab page) {
		super(application,page);
		
		logger.info("new "+ DialogMove.class.getCanonicalName());
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

		// Make sure the locator was set
		if (locator == null) {
			throw new HarnessException("Button " + button + " not implemented");
		}

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

		String locator = "css=div[id='"+ Locators.zDialogId +"']";
		
		if ( !this.sIsElementPresent(locator) ) {
			return (false); // Not even present
		}
		
		if ( !this.zIsVisiblePerPosition(locator, 0, 0) ) {
			return (false);	// Not visible per position
		}
	
		// Yes, visible
		logger.info(myPageName() + " zIsActive() = true");
		return (true);

	}
	
	/**
	 * Enter text into the move message dialog folder name field
	 * @param folder
	 */
	public void zEnterFolderName(String folder) throws HarnessException {
		String locator = "css=div[id='ChooseFolderDialog_inputDivId']>div>input";
		
		if ( !this.sIsElementPresent(locator) )
			throw new HarnessException("unable to find folder name field "+ locator);
		
		this.zClick(locator);
		zKeyboard.zTypeCharacters(folder);
		
		// SleepUtil.sleepSmall();
		this.zWaitForBusyOverlay(); 
			
	}


	/**
	 * Left-Click on a folder in the tree
	 * @param folder
	 * @throws HarnessException
	 */
	public void zClickTreeFolder(FolderItem folder) throws HarnessException {

		logger.info(myPageName() + " zClickTreeFolder(" + folder + ")");

		if (folder == null)

			throw new HarnessException("folder must not be null");

		String locator = null;

		if (MyTab instanceof PageMail) {

		   if (ZmailSeleniumProperties.getAppType() == AppType.DESKTOP) {
		      locator = "css=div[id='" + Locators.zDialogId+ "'] td[id^='zti__" +
		            MyApplication.zGetActiveAccount().EmailAddress +
		            ":ZmChooseFolderDialog_Mail__'][id$=':" +
		            folder.getId() +
		            "_textCell']"; 
		   } else {
		      locator = "css=div[id='" + Locators.zDialogId+ "'] td[id='zti__ZmChooseFolderDialog_Mail__"+ folder.getId() + "_textCell']";
		   }

		} else if (MyTab instanceof PageAddressbook) {

			   if (ZmailSeleniumProperties.getAppType() == AppType.DESKTOP) {
			      locator = "css=div[id='" + Locators.zDialogId
	            + "'] td[id^='zti__" + MyApplication.zGetActiveAccount().EmailAddress +
	            ":ZmChooseFolderDialog_Contacts__'][id$=':"
	            + folder.getId() + "_textCell']";
			   } else {
	   			locator = "css=div[id='" + Locators.zDialogId
	   			+ "'] td[id='zti__ZmChooseFolderDialog_Contacts__"
	   			+ folder.getId() + "_textCell']";
			   }

		} else if (MyTab instanceof PageCalendar) {

			locator = String.format(
					"css=div[id='%s'] td[id='zti__ZmChooseFolderDialog_Calendar__%s_textCell']", 
					Locators.zDialogId, 
					folder.getId());

		}else if (MyTab instanceof PageTasks){
			if (ZmailSeleniumProperties.getAppType() == AppType.DESKTOP) {
			   locator = "css=div[id='" + Locators.zDialogId + "'] td[id^='zti__" +
			         MyApplication.zGetActiveAccount().EmailAddress +
			         ":ZmChooseFolderDialog_Tasks__'][id$='" +
			         folder.getId() +
			         "_textCell']";
			} else {
			   locator = "css=div[id='" + Locators.zDialogId + "'] td[id='zti__ZmChooseFolderDialog_Tasks__"+ folder.getId() + "_textCell']";
			}

		}else if (MyTab instanceof PageBriefcase ) {
			if (ZmailSeleniumProperties.getAppType() == AppType.DESKTOP) {
			   locator = "css=div[id='" + Locators.zDialogId + "'] td[id='zti__"+
			         MyApplication.zGetActiveAccount().EmailAddress +
			         ":ZmChooseFolderDialog_Briefcase__'][id$='" +
			         folder.getId() +
			         "_textCell']";
			} else {
			   locator = "css=div[id='" + Locators.zDialogId + "'] td[id='zti__ZmChooseFolderDialog_Briefcase__"+ folder.getId() + "_textCell']";
			}

		} else {
			throw new HarnessException("Unknown app type!");
		}

		// For some reason, the text doesn't get entered on the first try

		this.zClick(locator);

		this.zWaitForBusyOverlay(); // This method call seems to be missing from
		// the briefcase function

	}
}
