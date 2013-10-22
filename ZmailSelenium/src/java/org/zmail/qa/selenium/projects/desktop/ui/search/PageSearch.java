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
package org.zmail.qa.selenium.projects.desktop.ui.search;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.projects.desktop.ui.AppAjaxClient;


/**
 * @author Matt Rhoades
 *
 */
public class PageSearch extends AbsTab {

	public static class Locators {
		
		public static final String zActiveLocator = "id=zb__Search__SEARCH";
		
		public static final String zSearchInput = "//input[@class='search_input']";
		public static final String zSearchButton = "id=zb__Search__SEARCH_title";
		
	}
	
	
	public PageSearch(AbsApplication application) {
		super(application);
		
		logger.info("new " + PageSearch.class.getCanonicalName());

	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsPage#isActive()
	 */
	@Override
	public boolean zIsActive() throws HarnessException {

		// Make sure the Mobile Client is loaded in the browser
		if ( !MyApplication.zIsLoaded() )
			throw new HarnessException("Application is not active!");
		

		// Look for the Logout button
		boolean present = sIsElementPresent(Locators.zActiveLocator);
		if ( !present ) {
			logger.debug("isActive() present = "+ present);
			return (false);
		}
		
		logger.debug("isActive() = "+ true);
		return (true);

	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsPage#myPageName()
	 */
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	/* (non-Javadoc)
	 * @see projects.admin.ui.AbsPage#navigateTo()
	 */
	@Override
	public void zNavigateTo() throws HarnessException {


		if ( zIsActive() ) {
			// This page is already active
			return;
		}
		

		// If search is not active, then we must not be logged in
		if ( !((AppAjaxClient)MyApplication).zPageMain.zIsActive() ) {
			((AppAjaxClient)MyApplication).zPageMain.zNavigateTo();
		}

		// Nothing more to do to make search appear, since it is always active if the app is active
		tracer.trace("Navigate to "+ this.myPageName());

		zWaitForActive();
		
	}

	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButton("+ button +")");
		
		tracer.trace("Click button "+ button);

		if ( button == null )
			throw new HarnessException("Button cannot be null!");
		
				
		// Default behavior variables
		//
		String locator = null;	// If set, this will be clicked
		AbsPage page = null;	// If set, this page will be returned
		
		// Based on the button specified, take the appropriate action(s)
		//
		
		if ( button == Button.B_SEARCH ) {
			
			locator = "zb__Search__SEARCH_title";
			page = null;
			
			// Make sure the button exists
			if ( !this.sIsElementPresent(locator) )
				throw new HarnessException("Button is not present locator="+ locator +" button="+ button);
			
			// FALL THROUGH
			
		} else if ( button == Button.B_SEARCHSAVE ) {
			
			locator = "zb__Search__SAVE_title";
			page = new DialogSaveSearch(MyApplication, this);
			
			// Make sure the button exists
			if ( !this.sIsElementPresent(locator) )
				throw new HarnessException("Button is not present locator="+ locator +" button="+ button);
			
			// FALL THROUGH
			
		} else if ( button == Button.B_SEARCHADVANCED ) {
			
			locator = "zb__Search__ADV_title";
			page = ((AppAjaxClient)MyApplication).zPageAdvancedSearch;
			
			// Make sure the button exists
			if ( !this.sIsElementPresent(locator) )
				throw new HarnessException("Button is not present locator="+ locator +" button="+ button);
			
			// FALL THROUGH
			
		} else {
			throw new HarnessException("no logic defined for button "+ button);
		}

		if ( locator == null ) {
			throw new HarnessException("locator was null for button "+ button);
		}
		
		// Default behavior, process the locator by clicking on it
		//
		
		// Click it
		this.zClick(locator);
		
		// If the app is busy, wait for it to become active
		this.zWaitForBusyOverlay();
		

		// If page was specified, make sure it is active
		if ( page != null ) {
			
			// This function (default) throws an exception if never active
			page.zWaitForActive();
			
		}
		
		return (page);
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButtonWithPulldown("+ pulldown +", "+ option +")");
		
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
		
		if ( pulldown == Button.B_SEARCHTYPE ) {

			if ( option == Button.O_SEARCHTYPE_ALL ) {

				pulldownLocator = "implement me";
				optionLocator = "implement me";
				page = null;
				
			} else if ( option == Button.O_SEARCHTYPE_EMAIL ) {
				throw new HarnessException("implement me!");
			} else if ( option == Button.O_SEARCHTYPE_CONTACTS ) {
				throw new HarnessException("implement me!");
			} else if ( option == Button.O_SEARCHTYPE_GAL ) {
				throw new HarnessException("implement me!");
			} else if ( option == Button.O_SEARCHTYPE_APPOINTMENTS ) {
				throw new HarnessException("implement me!");
			} else if ( option == Button.O_SEARCHTYPE_TASKS ) {
				throw new HarnessException("implement me!");
			} else if ( option == Button.O_SEARCHTYPE_FILES ) {
				throw new HarnessException("implement me!");
			} else if ( option == Button.O_SEARCHTYPE_INCLUDESHARED ) {
				throw new HarnessException("implement me!");
			} else {
				throw new HarnessException("no logic defined for pulldown/option "+ pulldown +"/"+ option);
			}
			
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

			// If the app is busy, wait for it to become active
			this.zWaitForBusyOverlay();
			
			
			if ( optionLocator != null ) {

				// Make sure the locator exists
				if ( !this.sIsElementPresent(optionLocator) ) {
					throw new HarnessException("Button "+ pulldown +" option "+ option +" optionLocator "+ optionLocator +" not present!");
				}
				
				this.zClick(optionLocator);

				// If the app is busy, wait for it to become active
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

	@Override
	public AbsPage zListItem(Action action, String item) throws HarnessException {
		throw new HarnessException(myPageName() + " does not have a list view");
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String item) throws HarnessException {
		throw new HarnessException(myPageName() + " does not have a list view");
	}

	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption ,String item)  throws HarnessException {
	   throw new HarnessException(myPageName() + " does not have a list view");
    }
		
	/**
	 * Enter text into the query string field
	 * @param query
	 * @throws HarnessException 
	 */
	public void zAddSearchQuery(String query) throws HarnessException {
		logger.info(myPageName() + " zAddSearchQuery("+ query +")");
		
		tracer.trace("Search for the query "+ query);

		
		this.sType(Locators.zSearchInput, query);

	}
	


	

}
