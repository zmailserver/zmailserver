/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.qa.selenium.projects.octopus.tests.history;

import org.testng.annotations.*;

import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.octopus.ui.PageHistory.*;

public class RefineFavorite extends HistoryCommonTest {
 
	
	public RefineFavorite() {
		super();
		logger.info("New " + RefineFavorite.class.getCanonicalName());
	}

	 @BeforeMethod(groups= ("always")) 
	 public void setup()
	 throws HarnessException {
	    super.setup();
	 }
				
			
	@Test(description = "Verify check 'favorite' checkbox with favorite action", groups = { "smoke" })
	public void RefineCheckFavorite() throws HarnessException {
						
        // verify favorite text present on GUI
		verifyCheckAction(Locators.zHistoryFilterFavorites.locator, 
				GetText.favorite(fileName));											

		
	}
	
	@Test(description = "Verify uncheck 'favorite' checkbox with favorite action", groups = { "functional" })
	public void RefineUnCheckFavorite() throws HarnessException {

	
		// verify favorite text not present
		verifyUnCheckAction(Locators.zHistoryFilterFavorites.locator, 
				GetText.favorite(fileName));											

	}
	
	@Test(description = "Verify check 'favorite' checkbox with non favorite action", groups = { "smoke" })
	public void RefineCheckNonFavorite() throws HarnessException {
		        
		// verify non favorite text present
		verifyCheckAction(Locators.zHistoryFilterFavorites.locator, 
				GetText.favorite(fileName));											

	}
	

	@Test(description = "Verify uncheck 'favorite' checkbox with non favorite action", groups = { "functional" })
	public void RefineUnCheckNonFavorite() throws HarnessException {
		
		// verify non favorite text not present
		verifyUnCheckAction(Locators.zHistoryFilterFavorites.locator, 
				GetText.favorite(fileName));											

	}
		


	
}
