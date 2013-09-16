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
package com.zimbra.qa.selenium.projects.admin.tests.downloads;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.admin.core.AdminCommonTest;


public class DownloadsIndex extends AdminCommonTest {

	public DownloadsIndex() {
		logger.info("New "+ DownloadsIndex.class.getCanonicalName());

		// All tests start at the "Accounts" page
		super.startingPage = app.zPageDownloads;
	}



	@Test(	description = "Verify the Downloads Index opens",
			groups = { "functional" })
	public void DownloadsTab_01() throws HarnessException {


		String windowTitle = "Zimbra Collaboration Suite :: Downloads";

		try {
			
			// Open a new window pointing at http://server.com/zimbra/downloads/index.html
			app.zPageDownloads.zOpenIndexHTML();
		
			// This method throws an exception if the page doesn't open
			app.zPageDownloads.zSeparateWindowFocus(windowTitle);

			// If we get here (i.e. no exception thrown), then pass
			ZAssert.assertTrue(true, "Verify that the page opened correctly");
			
		} catch (Exception e) {
			
			throw new HarnessException(e);
			
		} finally {
			
			app.zPageDownloads.zSeparateWindowClose(windowTitle);
			
		}
	}

	@Test(	description = "Verify the Downloads Tab contains the correct FOSS vs NETWORK links",
			groups = { "functional" })
	public void DownloadsTab_02() throws HarnessException {

		throw new HarnessException("implement me.  See http://bugzilla.zimbra.com/show_bug.cgi?id=58973");

	}

	@Test(	description = "Verify the downloads links return 200 rather than 404",
			groups = { "functional" })
	public void DownloadsTab_03() throws HarnessException {

		throw new HarnessException("implement me.  See http://bugzilla.zimbra.com/show_bug.cgi?id=58973");

	}



}
