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
package org.zmail.qa.selenium.projects.ajax.ui.mail;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.HarnessException;


/**
 * Represents a "New Tag", "Rename Tag" dialog box
 * <p>
 * @author Matt Rhoades
 *
 */
public class DialogTag extends AbsDialog {

	public static class Locators {
	
		// TODO:  See https://bugzilla.zmail.com/show_bug.cgi?id=54173
		public static final String zTagDialogId			= "CreateTagDialog";
		
		public static final String zTitleId	 			= "CreateTagDialog_title";

		public static final String zTagNameFieldId		= "CreateTagDialog_name";

		public static final String zTagColorPulldownId 	= "ZmTagColorMenu_dropdown";
		
		public static final String zButtonsId 			= "CreateTagDialog_buttons";
		public static final String zButtonOkId 			= "DWT178_title";
		public static final String zButtonCancelId 		= "DWT179_title";


	}
	
	
	public DialogTag(AbsApplication application, AbsTab tab) {
		super(application, tab);
		
		logger.info("new "+ DialogTag.class.getCanonicalName());
	}
	
	public void zSetTagName(String name) throws HarnessException {
		logger.info(myPageName() + " zSetTagName("+ name +")");

		tracer.trace("Set tag name "+ name);

		String locator = "id="+ Locators.zTagNameFieldId;
		
		// Make sure the locator exists
		if ( !this.sIsElementPresent(locator) ) {
			throw new HarnessException("Tag name locator "+ locator +" is not present");
		}
		
		this.sType(locator, name);
		
	}
	
	public void zSetTagColor(String color) throws HarnessException {
		logger.info(myPageName() + " zSetTagColor("+ color +")");

		tracer.trace("Set tag color "+ color);

		throw new HarnessException("implement me!");
		
	}
	
	@Override
	public AbsPage zClickButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zClickButton("+ button +")");

		tracer.trace("Click dialog button "+ button);

		AbsPage page = null;
		String locator = null;
		
		if ( button == Button.B_OK ) {
			
			// TODO: L10N this
			locator =  "//div[@id='"+ Locators.zTagDialogId +"']//div[@id='"+ Locators.zButtonsId +"']//td[text()='OK']";
			
		} else if ( button == Button.B_CANCEL ) {
			
			// TODO: L10N this
			locator =  "//div[@id='"+ Locators.zTagDialogId +"']//div[@id='"+ Locators.zButtonsId +"']//td[text()='Cancel']";

		} else {
			throw new HarnessException("Button "+ button +" not implemented");
		}
		
		// Default behavior, click the locator
		//
		
		// Make sure the locator was set
		if ( locator == null ) {
			throw new HarnessException("Button "+ button +" not implemented");
		}
		
		this.zClick(locator);
		
		this.zWaitForBusyOverlay();
	
		return (page);
	}

	@Override
	public String zGetDisplayedText(String locator) throws HarnessException {
		
		// Need to implement for:
		
		// "Create New Tag"
		// "Tag name:"
		// "Blue", "Cyan", ..., "Orange", "More colors ..." (Tag color pulldown)
		// OK
		// Cancel
		
		throw new HarnessException("implement me");
	}


	/* (non-Javadoc)
	 * @see framework.ui.AbsDialog#myPageName()
	 */
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		return ( this.sIsElementPresent(Locators.zTagDialogId) );
	}



}
