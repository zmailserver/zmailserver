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
package org.zmail.qa.selenium.projects.ajax.tests.zimlets.social.gui.features;

import java.util.HashMap;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;


public class ZmailZimletAvailableZimlets extends AjaxCommonTest {

	
	@SuppressWarnings("serial")
	public ZmailZimletAvailableZimlets() {
		logger.info("New "+ ZmailZimletAvailableZimlets.class.getCanonicalName());
		
		// All tests start at the login page
		super.startingPage = app.zPageSocial;

		// Make sure we are using an account with message view
		super.startingAccountPreferences = new HashMap<String, String>() {{
				    				    
					// Only mail is enabled
				    put("zmailFeatureMailEnabled", "FALSE");
				    put("zmailFeatureContactsEnabled", "FALSE");
				    put("zmailFeatureCalendarEnabled", "FALSE");
				    put("zmailFeatureTasksEnabled", "FALSE");
				    put("zmailFeatureBriefcasesEnabled", "FALSE");

				    // https://bugzilla.zmail.com/show_bug.cgi?id=62161#c3
				    // put("zmailFeatureOptionsEnabled", "FALSE");
				    
				    put("zmailZimletAvailableZimlets", "+org_zmail_social");

				}};


	}
	

	/**
	 * See http://bugzilla.zmail.com/show_bug.cgi?id=61982 - WONTFIX
	 * @throws HarnessException
	 */
	@Bugs(ids = "50123")
	@Test(	description = "Load the client with just Social enabled",
			groups = { "deprecated" })
	public void ZmailZimletAvailableZimlets_01() throws HarnessException {
		
		ZAssert.assertTrue(app.zPageSocial.zIsActive(), "Verify the social page is active");

		
	}


}
