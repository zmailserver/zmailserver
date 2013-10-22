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
package org.zmail.qa.selenium.projects.admin.ui;

import org.zmail.qa.selenium.framework.ui.AbsApplication;
import org.zmail.qa.selenium.framework.ui.AbsPage;
import org.zmail.qa.selenium.framework.ui.AbsTab;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;

/**
 * This class defines the Downloads page (click on "Downloads" in the header)
 * @author Matt Rhoades
 *
 */
public class PageDownloads extends AbsTab {

	public static class Locators {
		
		public static final String TOOLS_AND_MIGRATION_ICON="css=div.ImgToolsAndMigration";
		public static final String DOWNLOADS="css=div[id^='zti__AppAdmin__magHV__download'][id$='div']";
		public static final String HOME="Home";
		public static final String TOOLS_AND_MIGRATION="Tools and Migration";
		public static final String DOWNLOAD="Downloads";
		public static final String IndexHtmlTitleLocator = "css=title:contains('Downloads')";
	}
	
	public PageDownloads(AbsApplication application) {
		super(application);

		logger.info("new " + myPageName());

	}

	@Override
	public void zNavigateTo() throws HarnessException {


		if ( zIsActive() ) {
			// This page is already active.
			
			return;
		}

		// Click on Tools and Migration -> Downloads
		zClickAt(Locators.TOOLS_AND_MIGRATION_ICON,"");
		if(sIsElementPresent(Locators.DOWNLOADS));
		sClickAt(Locators.DOWNLOADS, "");
		
		zWaitForActive();
	}

	@Override
	public boolean zIsActive() throws HarnessException {

		// Make sure the Admin Console is loaded in the browser
		if ( !MyApplication.zIsLoaded() )
			throw new HarnessException("Admin Console application is not active!");


		boolean present = sIsElementPresent("css=span:contains('" + Locators.TOOLS_AND_MIGRATION + "')");
		if ( !present ) {
			return (false);
		}

		boolean visible = zIsVisiblePerPosition("css=span:contains('" + Locators.TOOLS_AND_MIGRATION + "')", 0, 0);
		if ( !visible ) {
			logger.debug("isActive() visible = "+ visible);
			return (false);
		}

		return (true);

	}


	@Override
	public AbsPage zListItem(Action action, String item) throws HarnessException {
		throw new HarnessException(myPageName() + " does not contain lists");
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String item) throws HarnessException {
		throw new HarnessException(myPageName() + " does not contain lists");
	}

	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption, String item) throws HarnessException {
		throw new HarnessException(myPageName() + " does not contain lists");
	}

	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		throw new HarnessException(myPageName() + " does not contain a toolbar");
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {
		throw new HarnessException(myPageName() + " does not contain a toolbar");
	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	
	/**
	 * Open http://server.org/zmail/downloads/index.html
	 * @throws HarnessException 
	 */
	public void zOpenIndexHTML() throws HarnessException {

		String base = ZmailSeleniumProperties.getBaseURL();
		String path = "/zmail/downloads/index.html";
		String id = ZmailSeleniumProperties.getUniqueString();
		
		this.sOpenWindow(base + path, id);
		this.zSelectWindow(id);
		SleepUtil.sleepLong();
		
		// Make sure the page is active
		if ( !this.sIsElementPresent(Locators.IndexHtmlTitleLocator) )
			throw new HarnessException("index.html never became active/focused");
		
	}
	

	public boolean zVerifyHeader (String header) throws HarnessException {
		if(this.sIsElementPresent("css=span:contains('" + header + "')"))
			return true;
		return false;
	}


}
