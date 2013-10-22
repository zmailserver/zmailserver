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
package org.zmail.qa.selenium.projects.desktop.ui.calendar;

import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.framework.util.staf.Stafpostqueue;




/**
 * The <code>FormMailNew<code> object defines a compose new message view
 * in the Zmail Ajax client.
 * <p>
 * This class can be used to compose a new message.
 * <p>
 * 
 * @author Matt Rhoades
 * @see http://wiki.zmail.com/wiki/Testing:_Selenium:_ZmailSelenium_Overview#Mail_Page
 */
public class FormApptNew extends AbsForm {
	
	/**
	 * Defines Selenium locators for various objects in {@link FormApptNew}
	 */
	public static class Locators {

		public static final String Button_Send			= "css=div[id^='ztb__APPT'] td[id$='_SEND_INVITE_title']";
		public static final String Button_Save			= "css=div[id^='ztb__APPT'] td[id$='_SAVE_title']";
		public static final String Button_SaveAndClose	= "css=div[id^='ztb__APPT'] td[id$='_SAVE_title']";
		public static final String Button_Close			= "css=div[id^='ztb__APPT'] td[id$='_CANCEL_title']";
		
	}

	public static class Field {
	
		public static final Field Subject = new Field("Subject");
		public static final Field Attendees = new Field("Attendees");
		public static final Field Optional = new Field("Optional");
		public static final Field Location = new Field("Location");
		public static final Field Resources = new Field("Resources");
		public static final Field StartDate = new Field("StartDate");
		public static final Field StartTime = new Field("StartTime");
		public static final Field EndDate = new Field("EndDate");
		public static final Field EndTime = new Field("EndTime");
		public static final Field Remdinder = new Field("Remdinder");
		public static final Field Body = new Field("Body");
		
		
		private String field;
		private Field(String name) {
			field = name;
		}
		
		@Override
		public String toString() {
			return (field);
		}

	}
	
	
	/**
	 * Protected constuctor for this object.  Only classes within
	 * this package should create DisplayMail objects.
	 * 
	 * @param application
	 */
	public FormApptNew(AbsApplication application) {
		super(application);
		
		logger.info("new " + FormApptNew.class.getCanonicalName());

	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}
	

	@Override
	public void zSubmit() throws HarnessException {
		logger.info("FormMailNew.submit()");
		
		// Send: if there are attendees
		// Save: If there are no attendees
		
		// If send is visible, click it
		// Otherwise, click Save
		String locator = "css=div[id$=_SEND_INVITE]";
		if ( this.sIsElementPresent(locator) && this.sIsVisible(locator) ) {
			zToolbarPressButton(Button.B_SEND);
		} else {
			zToolbarPressButton(Button.B_SAVE);
		}

		this.zWaitForBusyOverlay();

	}

	/**
	 * Press the toolbar button
	 * @param button
	 * @return
	 * @throws HarnessException
	 */
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButton("+ button +")");
		
		tracer.trace("Click button "+ button);

		if ( button == null )
			throw new HarnessException("Button cannot be null!");
		
		// Fallthrough objects
		AbsPage page = null;
		String locator = null;
		
		if ( button == Button.B_SEND ) {
			
			locator = Locators.Button_Send;
			
			// Click on send
			this.zClick(locator);
			
			this.zWaitForBusyOverlay();
			
			// Wait for the message to be delivered
			Stafpostqueue sp = new Stafpostqueue();
			sp.waitForPostqueue();
			
			return (page);
		
		} else if ( button == Button.B_SAVE ) {

			locator = Locators.Button_Save;
			page = null;
			
			// FALL THROUGH
		
		} else {
			throw new HarnessException("no logic defined for button "+ button);
		}

		// Make sure a locator was set
		if ( locator == null )
			throw new HarnessException("locator was null for button "+ button);

		
		// Default behavior, process the locator by clicking on it
		//
		
		// Click it
		this.zClick(locator);

		// if the app is busy, wait for it to become active again
		this.zWaitForBusyOverlay();
		
		if ( page != null ) {
			
			// Make sure the page becomes active
			page.zWaitForActive();
			
		}
		
		// Return the page, if specified
		return (page);

	}
	
	/**
	 * Press the toolbar pulldown and the menu option
	 * @param pulldown
	 * @param option
	 * @return
	 * @throws HarnessException
	 */
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressPulldown("+ pulldown +", "+ option +")");
		
		tracer.trace("Click pulldown "+ pulldown +" then "+ option);

		if ( pulldown == null )
			throw new HarnessException("Pulldown cannot be null!");
		
		if ( option == null )
			throw new HarnessException("Option cannot be null!");

		// Default behavior variables
		//
		String pulldownLocator = null;	// If set, this will be expanded
		String optionLocator = null;	// If set, this will be clicked
		AbsPage page = null;	// If set, this page will be returned
		
		// Based on the button specified, take the appropriate action(s)
		//
		
		if ( pulldown == Button.B_PRIORITY ) {
			
				
		} else {
			throw new HarnessException("no logic defined for pulldown "+ pulldown);
		}

		// Default behavior
		if ( pulldownLocator != null ) {
						
			// Make sure the locator exists
			if ( !this.sIsElementPresent(pulldownLocator) ) {
				throw new HarnessException("Button "+ pulldown +" option "+ option +" pulldownLocator "+ pulldownLocator +" not present!");
			}
			
			this.zClick(pulldownLocator);

			this.zWaitForBusyOverlay();
			
			if ( optionLocator != null ) {

				// Make sure the locator exists
				if ( !this.sIsElementPresent(optionLocator) ) {
					throw new HarnessException("Button "+ pulldown +" option "+ option +" optionLocator "+ optionLocator +" not present!");
				}
				
				this.zClick(optionLocator);

				this.zWaitForBusyOverlay();

			}
			
			// If we click on pulldown/option and the page is specified, then
			// wait for the page to go active
			if ( page != null ) {
				page.zWaitForActive();
			}
			
		}
		
		// Return the specified page, or null if not set
		return (page);
	}
	
	public void zFillField(Field field, ZDate value) throws HarnessException {
		String stringFormat;
		
		if ( field == Field.StartDate || field == Field.EndDate ) {
			stringFormat = value.toMM_DD_YYYY();
		} else if ( field == Field.StartTime || field == Field.EndTime ) {
			stringFormat = value.tohh_mm_aa();
		} else {
			throw new HarnessException("zFillField() not implemented for field: "+ field);
		}
		
		zFillField(field, stringFormat);
	}
	
	/**
	 * Fill in the form field with the specified text
	 * @param field
	 * @param value
	 * @throws HarnessException
	 */
	public void zFillField(Field field, String value) throws HarnessException {
	
		tracer.trace("Set "+ field +" to "+ value);

		String locator = null;
		
		if ( field == Field.Subject ) {
			
			locator = "css=div[id^='APPT_COMPOSE_'] td[id$='_subject'] input";
			
			// FALL THROUGH
			
		} else if ( field == Field.StartDate ) {
			
			locator = "css=input[id$='_startDateField']";
			
			// FALL THROUGH

		} else if ( field == Field.StartTime ) {
			
			locator = "css=td[id$='_startTimeSelect'] td[id$='_timeSelectInput'] input";
			
			// FALL THROUGH

		} else if ( field == Field.EndDate ) {
			
			locator = "css=input[id$='_endDateField']";
			
			// FALL THROUGH

		} else if ( field == Field.EndTime ) {
			
			locator = "css=td[id$='_endTimeSelect'] td[id$='_timeSelectInput'] input";
			
			// FALL THROUGH

		} else if ( field == Field.Body ) {

			int frames = this.sGetXpathCount("//iframe");
			logger.debug("Body: # of frames: "+ frames);

			if ( frames == 0 ) {
				////
				// Text compose
				////
				
				locator = "//textarea[contains(@id,'textarea_')]";
				
				if ( !this.sIsElementPresent(locator))
					throw new HarnessException("Unable to locate compose body");

				
				this.sFocus(locator);
				this.zClick(locator);
				this.zWaitForBusyOverlay();
				this.sType(locator, value);
				
				return;
				
			} else if ( frames == 1 ) {
				////
				// HTML compose
				////
				
				try {
					
					this.sSelectFrame("index=0"); // iframe index is 0 based
					
					locator = "//html//body";
					
					if ( !this.sIsElementPresent(locator))
						throw new HarnessException("Unable to locate compose body");

					this.sFocus(locator);
					this.zClick(locator);
					this.sType(locator, value);
					
				} finally {
					// Make sure to go back to the original iframe
					this.sSelectFrame("relative=top");

				}
				
				// Is this requried?
				this.zWaitForBusyOverlay();

				return;

			} else {
				throw new HarnessException("Compose //iframe count was "+ frames);
			}
			

		} else {
			throw new HarnessException("not implemented for field "+ field);
		}
		
		if ( locator == null ) {
			throw new HarnessException("locator was null for field "+ field);
		}
		
		// Default behavior, enter value into locator field
		//
		
		// Make sure the button exists
		if ( !this.sIsElementPresent(locator) )
			throw new HarnessException("Field is not present field="+ field +" locator="+ locator);
		
		// Enter text
		this.sType(locator, value);
		
		this.zWaitForBusyOverlay();

	}
	
	

	@Override
	public void zFill(IItem item) throws HarnessException {
		logger.info(myPageName() + ".zFill(ZmailItem)");
		logger.info(item.prettyPrint());

		// Make sure the item is a MailItem
		if ( !(item instanceof AppointmentItem) ) {
			throw new HarnessException("Invalid item type - must be AppointmentItem");
		}
		
		// Convert object to MailItem
		AppointmentItem appt = (AppointmentItem) item;
		
		// Fill out the form
		//
		
		// Handle the subject
		if ( appt.getSubject() != null ) {
			
			zFillField(Field.Subject, appt.getSubject());

		}
		
		if ( appt.getStartTime() != null ) {
			
			zFillField(Field.StartDate, appt.getStartTime());
			zFillField(Field.StartTime, appt.getStartTime());

		}
		
		if ( appt.getEndTime() != null ) {
			
			zFillField(Field.EndDate, appt.getEndTime());
			zFillField(Field.EndTime, appt.getEndTime());

		}
		
		if ( appt.getContent() != null ) {
			
			zFillField(Field.Body, appt.getContent());
			
		}
		

		
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		logger.info(myPageName() + " zIsActive()");
		
		// Look for the div
		// See https://bugzilla.zmail.com/show_bug.cgi?id=58477
		String locator = "css=div[id^='ztb__APPT']";
		
		if ( !this.sIsElementPresent(locator) ) {
			return (false);	
		}
		
		if ( !this.zIsVisiblePerPosition(locator, 150, 75) ) {
			return (false);
		}
		
		logger.info(myPageName() + " zIsActive() = true");
		return (true);
	}

}
