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
package com.zimbra.qa.selenium.projects.octopus.tests.history;

import org.testng.annotations.*;

import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.octopus.ui.PageHistory.*;

public class RefineComment extends HistoryCommonTest {
 
	
	public RefineComment() {
		super();
		logger.info("New " + RefineComment.class.getCanonicalName());
	}

    @BeforeMethod(groups= ("always")) 
    public void setup()
    throws HarnessException {
    	super.setup();
    }
			
	@Test(description = "Verify check 'comment' checkbox", groups = { "functional" })
	public void RefineCheckComment() throws HarnessException {
    		
       // verify check action for 'comment' 
	   verifyCheckAction(Locators.zHistoryFilterComment.locator,
				GetText.comment(fileName));
		
	}

	@Test(description = "Verify uncheck 'comment' checkbox", groups = { "smoke" })
	public void RefineUnCheckComment() throws HarnessException {
			
       // verify uncheck action for 'comment' 
	   verifyCheckUnCheckAction(Locators.zHistoryFilterComment.locator,
				GetText.comment(fileName));
		
	}

	//TODO add test case for delete comment (bug #70800)

}
