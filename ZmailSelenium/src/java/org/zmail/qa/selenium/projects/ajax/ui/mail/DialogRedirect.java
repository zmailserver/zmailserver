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

import org.zmail.qa.selenium.framework.ui.AbsApplication;
import org.zmail.qa.selenium.framework.ui.AbsDialog;
import org.zmail.qa.selenium.framework.ui.AbsPage;
import org.zmail.qa.selenium.framework.ui.AbsTab;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.staf.Stafpostqueue;

/**
 * Represents a "Specify Message Send Time" dialog box (Send Later)
 * 
 * See https://bugzilla.zmail.com/show_bug.cgi?id=7524
 * See https://bugzilla.zmail.com/show_bug.cgi?id=61935
 * 
 * <p>
 * @author Matt Rhoades
 *
 */
public class DialogRedirect extends AbsDialog {

	
	
	public static class Locators {
		
		// Main dialog locator
		// TODO: need to update this locator https://bugzilla.zmail.com/show_bug.cgi?id=61935
		public static final String RedirectDialogLocator		= "css=div#RedirectDialog";

		// Fields
		public static final String FieldEmailLocator			= "css=input#RedirectDialog_to_control";

		// Buttons
		public static final String ButtonToButtonLocator		= "css=div[id='zb__RedirectDialog__TO'] td[id$='__TO_title]";
		public static final String ButtonOkButtonLocator		= "css=div[id='RedirectDialog_buttons'] td[id^='OK'] td[id$='_title']";
		public static final String ButtonCancelButtonLocator	= "css=div[id='RedirectDialog_buttons'] td[id^='Cancel'] td[id$='_title']";
	}
	
	
	public DialogRedirect(AbsApplication application, AbsTab tab) {
		super(application, tab);
		
		logger.info("new "+ DialogRedirect.class.getCanonicalName());

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
		logger.info(myPageName() + " zIsVisible()");

		String locator = Locators.RedirectDialogLocator;
		
		if ( !this.sIsElementPresent(locator) ) {
			return (false); // Not even present
		}
		
		if ( !this.zIsVisiblePerPosition(locator, 0, 0) ) {
			return (false);	// Not visible per position
		}
	
		// Yes, visible
		logger.info(myPageName() + " zIsVisible() = true");
		return (true);
		
	}

	@Override
	public AbsPage zClickButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zClickButton("+ button +")");

		tracer.trace("Click dialog button "+ button);

		AbsPage page = null;
		String locator = null;
		
		
		if ( button == Button.B_OK ) {

			locator = Locators.ButtonOkButtonLocator;

			this.zClick(locator);

			this.zWaitForBusyOverlay();

			// Check the message queue
			Stafpostqueue sp = new Stafpostqueue();
			sp.waitForPostqueue();

			return (page);

		} else if ( button == Button.B_CANCEL ) {

			locator = Locators.ButtonCancelButtonLocator;

		} else if ( button == Button.B_TO ) {
			
			locator = Locators.ButtonToButtonLocator;

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

		this.zWaitForBusyOverlay();

		return (page);
	}


	@Override
	public String zGetDisplayedText(String locator) throws HarnessException {
		logger.info(myPageName() + " zGetDisplayedText("+ locator +")");
		
		if ( locator == null )
			throw new HarnessException("locator was null");
		
		return (this.sGetText(locator));
	}

	public static class Field {
		
		public static final Field To = new Field("To");		
		
		private String field;
		private Field(String name) {
			field = name;
		}
		
		@Override
		public String toString() {
			return (field);
		}

	}
	


	public void zFillField(Field field, String email) throws HarnessException {
		tracer.trace("Set "+ field +" to "+ email);

		String locator = null;
		
		if ( field == Field.To ) {
			
			locator = Locators.FieldEmailLocator;
			
			// FALL THROUGH
			
		} else {
			throw new HarnessException("Unsupported field: "+ field);
		}
		
		if ( locator == null ) {
			throw new HarnessException("locator was null for field "+ field);
		}
		
		// Default behavior, enter value into locator field
		//
		
		// Make sure the button exists
		if ( !this.sIsElementPresent(locator) )
			throw new HarnessException("Field is not present field="+ field +" locator="+ locator);
		
		// Seems that the client can't handle filling out the new mail form too quickly
		// Click in the "To" fields, etc, to make sure the client is ready
		this.sFocus(locator);
		this.zClick(locator);
		this.zWaitForBusyOverlay();

		// Enter text
		this.sType(locator, email);
		
		this.zWaitForBusyOverlay();
		
	}


}
