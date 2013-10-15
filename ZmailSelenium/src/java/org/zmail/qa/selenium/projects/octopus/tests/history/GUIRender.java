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
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;
import org.zmail.qa.selenium.projects.octopus.ui.PageHistory;

public class GUIRender extends OctopusCommonTest {

	public GUIRender() {
		logger.info("New " + GUIRender.class.getCanonicalName());

		// test starts at the History tab
		super.startingPage = app.zPageHistory;
		super.startingAccountPreferences = null;
	}
	
	/*
	 * @Test(description = "Verify GUI elements on the page", groups = ("debug"))
	public void VerifyTextDisplayed() throws HarnessException {
		// Click on History tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_HISTORY);
    
		//Verify the header History is displayed
		ZAssert.assertEquals(app.zPageHistory.sGetText(HISTORY_HEADER_VIEW_LOCATOR),
				           "History",
				           "Verify History header is displayed") ;
		
		
		//Verify the Refine panel and text are displayed in the rendered order
		String filterViewText = app.zPageHistory.sGetText(HISTORY_FILTER_VIEW_LOCATOR);
		for (int i=0; i <HISTORY_FILTER_VIEW_TEXT.length; i++) {		
		     ZAssert.assertTrue(filterViewText.trim().startsWith(HISTORY_FILTER_VIEW_TEXT[i]),
		                   "Verify " + HISTORY_FILTER_VIEW_TEXT[i] + " is displayed") ;
		     
		     filterViewText = filterViewText.substring(filterViewText.indexOf(HISTORY_FILTER_VIEW_TEXT[i]) + HISTORY_FILTER_VIEW_TEXT[i].length());						
		}		
		
	}
	*/
	
}
