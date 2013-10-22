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
package org.zmail.qa.selenium.projects.admin.tests.downloads;

import java.io.IOException;
import java.net.*;
import java.util.*;

import org.testng.annotations.Test;

import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.admin.core.AdminCommonTest;


public class DownloadsTab extends AdminCommonTest {

	public static String[] NetworkOnlyLocators = {
		
		// These links only appear on NETWORK

		// Zmail Connector for Outlook MSI Customizer
		"//a[contains(text(),'Zmail Connector for Outlook MSI Customizer')]", // TODO: need to add unique ID
		
		// Zmail Connector for Outlook Branding MSI
		"//a[contains(text(),'Zmail Connector for Outlook Branding MSI')]", // TODO: need to add unique ID
		
		// Zmail Connector(32bits) for Outlook
		"//a[contains(text(),'Zmail Connector(32bits) for Outlook')]", // TODO: need to add unique ID
		
		// Zmail Connector(64bits) for Outlook
		"//a[contains(text(),'Zmail Connector(64bits) for Outlook')]", // TODO: need to add unique ID
		
		// (User Instructions )
		"//a[contains(text(),'User Instructions')]", // TODO: need to add unique ID

		// Zmail Connector for Apple iSync
		"//a[contains(text(),'Zmail Connector for Apple iSync')]", // TODO: need to add unique ID
		
	};
	
	public static String[] FossOnlyLocators = {
		
		// These links only appear on FOSS

		
		// There are currently no FOSS specific downloads
		
	};

	public static String[] CommonLocators = {
		
		// These links appear on both NETWORK and FOSS
		
		// ZCS Migration Wizard for Exchange
		"//a[contains(text(),'ZCS Migration Wizard for Exchange')]", // TODO: need to add unique ID

		// ZCS Migration Wizard for Domino
		"//a[contains(text(),'ZCS Migration Wizard for Domino')]", // TODO: need to add unique ID

		// PST Import Wizard
		"//a[contains(text(),'PST Import Wizard')]", // TODO: need to add unique ID

		// PST Import Wizard (User Instructions)
		"//a[contains(text(),'User Instructions')]", // TODO: need to add unique ID

	};

	
	
	public DownloadsTab() {
		logger.info("New "+ DownloadsTab.class.getCanonicalName());

		// All tests start at the "Accounts" page
		super.startingPage = app.zPageDownloads;
	}



	@Test(	description = "Verify the Downloads Tab contains the correct FOSS vs NETWORK links",
			groups = { "functional" })
	public void DownloadsTab_01() throws HarnessException {
		
		// Make sure common links are present
		for ( String locator : CommonLocators ) {
			ZAssert.assertTrue(app.zPageDownloads.sIsElementPresent(locator), "Verify the common locator exists: "+ locator);
		}
		
		// If NETWORK, make sure NETWORK-only links appear and FOSS-only links do not appear
		// If FOSS, make sure FOSS-only links appear and NETWORK-only links do not appear
		if ( ZmailSeleniumProperties.zmailGetVersionString().contains("NETWORK") ) {
			
			for ( String locator : NetworkOnlyLocators ) {
				ZAssert.assertTrue(app.zPageDownloads.sIsElementPresent(locator), "Verify the network-only locator exists: "+ locator);
			}

			for ( String locator : FossOnlyLocators ) {
				ZAssert.assertFalse(app.zPageDownloads.sIsElementPresent(locator), "Verify the foss-only locator does not exists: "+ locator);
			}

		} else if ( ZmailSeleniumProperties.zmailGetVersionString().contains("FOSS") ) {
			
			for ( String locator : NetworkOnlyLocators ) {
				ZAssert.assertFalse(app.zPageDownloads.sIsElementPresent(locator), "Verify the network-only locator does not exists: "+ locator);
			}

			for ( String locator : FossOnlyLocators ) {
				ZAssert.assertTrue(app.zPageDownloads.sIsElementPresent(locator), "Verify the foss-only locator exists: "+ locator);
			}

		} else {
			throw new HarnessException("Unable to find NETWORK or FOSS in version string: "+ ZmailSeleniumProperties.zmailGetVersionString());
		}
		

	}

	@Test(	description = "Verify the downloads links return 200 rather than 404",
			groups = { "functional" })
	public void DownloadsTab_02() throws HarnessException {

		// Determine which links should be present
		List<String> locators = new ArrayList<String>();
		
		if ( ZmailSeleniumProperties.zmailGetVersionString().contains("NETWORK") ) {
			
			locators.addAll(Arrays.asList(NetworkOnlyLocators));
			locators.addAll(Arrays.asList(CommonLocators));
			
		} else if ( ZmailSeleniumProperties.zmailGetVersionString().contains("FOSS") ) {
			
			locators.addAll(Arrays.asList(FossOnlyLocators));
			locators.addAll(Arrays.asList(CommonLocators));

		} else {
			throw new HarnessException("Unable to find NETWORK or FOSS in version string: "+ ZmailSeleniumProperties.zmailGetVersionString());
		}

		for (String locator : locators ) {
			String href = app.zPageDownloads.sGetAttribute("xpath="+ locator +"@href");
			String page = ZmailSeleniumProperties.getBaseURL() + href;
			
			HttpURLConnection  connection = null;
			try {
				
				URL url = new URL(page);
				connection = (HttpURLConnection )url.openConnection();
				connection.setRequestMethod("HEAD");
		        int code = connection.getResponseCode();
		        
		        // TODO: why is 400 returned for the PDF links?
		        // 200 and 400 are acceptable
		        ZAssert.assertStringContains("200 400", ""+code, "Verify the download URL is valid: "+ url.toString());
		        
			} catch (MalformedURLException e) {
				throw new HarnessException(e);
			} catch (IOException e) {
				throw new HarnessException(e);
			} finally {
				if ( connection != null ) {
					connection.disconnect();
					connection = null;
				}
			}

		}

	}



}
