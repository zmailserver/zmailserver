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
package org.zmail.qa.selenium.projects.desktop.ui;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.HarnessException;

/**
 * Represents a "Rename Tag" dialog box
 * <p>
 * @author Matt Rhoades
 *
 */
public class DialogRenameTag extends AbsDialog {

	public static class Locators {
		//see https://bugzilla.zmail.com/show_bug.cgi?id=57458
		public static final String zRenameTagDialogId	= "RenameTagDialog";
		public static final String zNewTagNameFieldId	= "RenameTagDialog_name";
		public static final String zButtonsId 		= "RenameTagDialog_buttons";
	}
	
	
	public DialogRenameTag(AbsApplication application, AbsTab tab) {
		super(application, tab);
		logger.info("new " + DialogRenameTag.class.getCanonicalName());

	}
	
	public void zSetNewName(String name) throws HarnessException {
		logger.info(myPageName() + " zSetNewName("+ name +")");

	    //TODO: Bug 59270, 57458
		//String locator = "//input[@id='"+ Locators.zNewTagNameFieldId +"']";
		String locator = "css=div[class='DwtDialog WindowOuterContainer'] input[class='Field']";

		// Make sure the locator exists
		if ( !this.sIsElementPresent(locator) ) {
			throw new HarnessException("Rename locator "+ locator +" is not present");
		}		
		this.sType(locator, name);		
	}
	
	
	@Override
	public AbsPage zClickButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zClickButton("+ button +")");

		String locator = null;
		
		if ( button == Button.B_OK ) {
			
			//locator =  "//div[@id='"+ Locators.zRenameTagDialogId +"']//div[@id='"+ Locators.zButtonsId +"']//td[text()='OK']";
		   locator = "css=div[class='DwtDialog WindowOuterContainer'] td[class='ZWidgetTitle']:contains('OK')";
					
		} else if ( button == Button.B_CANCEL ) {
			
			locator =  "implement me";

		} else {
			throw new HarnessException("Button "+ button +" not implemented");
		}
		
		// Default behavior, click the locator
		//
		
		// Make sure the locator was set
		if ( locator == null ) {
			throw new HarnessException("Button "+ button +" not implemented");
		}
		
		// Make sure the locator exists
		if ( !this.sIsElementPresent(locator) ) {
			throw new HarnessException("Button "+ button +" locator "+ locator +" not present!");
		}
		
		this.zClick(locator);
		
		return (null);
	}

	@Override
	public String zGetDisplayedText(String locator) throws HarnessException {
		
		// Need to implement for:
		
		// "Rename Tag: tagname"
		// "New name:"
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
		logger.info(myPageName() + " zIsActive()");

		//TODO: Bug 59270, 57458
		//String locator = "id="+ Locators.zRenameTagDialogId;
		String locator = "css=td[class='DwtDialogTitle']:contains('Rename')";
		
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



}
