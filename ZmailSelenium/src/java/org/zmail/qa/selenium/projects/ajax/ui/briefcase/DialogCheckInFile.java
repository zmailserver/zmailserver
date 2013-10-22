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
package com.zimbra.qa.selenium.projects.ajax.ui.briefcase;

import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.HarnessException;

/**
 * Represents a "Check In File to Briefcase" dialog box
 * <p>
 */
public class DialogCheckInFile extends AbsDialog {
	public static class Locators {
		public static final String zDialogClass = "css=div.DwtDialog:contains(Check In File to Briefcase)";
		public static final String zTitleCLass =  "DwtDialogTitle";
		public static final String zTitle	 	= "Check In File to Briefcase";
		public static final String zDialogButtonsClass = "DwtDialogButtonBar";
		public static final String zDialogContentClassId = "DwtDialogBody";
	}

	public DialogCheckInFile(AbsApplication application,AbsTab page) {
		super(application,page);		
		logger.info("new "+ DialogCheckInFile.class.getCanonicalName());
	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public boolean zIsActive() throws HarnessException {

		logger.info(myPageName() + " zIsActive()");

		String locator = Locators.zDialogClass;
		
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
	
	@Override
	public AbsPage zClickButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zClickButton(" + button + ")");
		tracer.trace("Click dialog button " + button);
		
		String locator = null;

		if (button == Button.B_CANCEL) {
			/*
			locator = Locators.zDialogClass
			+ " div[class='" + Locators.zDialogButtonsClass
			+ "'] td[class=ZWidgetTitle]:contains(Cancel)";		
			*/
			locator = "//div[@class='DwtDialog']"
					+ "//*[contains(@class,'ZWidgetTitle') and contains(text(),'Cancel')]";

		} else {
			throw new HarnessException("Button " + button + " not implemented");
		}

		// Default behavior, click the locator
		
		// Make sure the locator was set
	
		// Make sure the locator exists
		if (!this.sIsVisible(locator)) {
			throw new HarnessException("Button " + button + " locator "
					+ locator + " not visible!");
		}
		
		// if(zIsActive())
		// zGetDisplayedText("css=div[class=" + Locators.zDialogContentClassId +
		// "]");
		
		this.zClickAt(locator,"0,0");

		this.zWaitForBusyOverlay();

		return (null);
	}

	/**
	 * Enter text into the Check In File to Briefcase dialog Notes field
	 * @param notes
	 */
	public void zEnterNotes(String notes) throws HarnessException {
		logger.info(myPageName() + " zEnterNotes(" + notes + ")");

		tracer.trace("Enter notes in text field " + notes);

		if (notes == null)
			throw new HarnessException("notes must not be null");
		
		String locator = "css=div[class=" + Locators.zDialogContentClassId
		+ "] textarea[id$='notes']";
		
		if (!this.sIsElementPresent(locator))
			throw new HarnessException("unable to find body field " + locator);
		
		this.sFocus(locator);
		this.zClickAt(locator,"0,0");
		this.sType(locator, notes);
		
		this.zWaitForBusyOverlay(); 			
	}	
	
	@Override
	public String zGetDisplayedText(String locator) throws HarnessException {
		logger.info(myPageName() + " zGetDisplayedText(" + locator + ")");

		if (locator == null)
			throw new HarnessException("locator was null");

		return (this.sGetText(locator));
	}
}
