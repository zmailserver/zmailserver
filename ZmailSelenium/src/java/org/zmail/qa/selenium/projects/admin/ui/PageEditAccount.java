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


public class PageEditAccount extends AbsTab {

	public static final String ztab__DOAMIN_EDIT__DWT192 = "xpath=//*[@id='ztab__DOAMIN_EDIT__DWT192']";
	public static final String ztab__DOAMIN_EDIT__DWT192_classAttr = "xpath=(//*[@id='ztab__DOAMIN_EDIT__DWT192'])@class";

	public PageEditAccount(AbsApplication application) {
		super(application);
		
		logger.info("new " + myPageName());

	}

	@Override
	public boolean zIsActive() throws HarnessException {

		// Make sure the Admin Console is loaded in the browser
		if ( !MyApplication.zIsLoaded() )
			throw new HarnessException("Admin Console application is not active!");

		
		boolean present = sIsElementPresent(ztab__DOAMIN_EDIT__DWT192);
		if ( !present ) {
			return (false);
		}
		
		String attrs = sGetAttribute(ztab__DOAMIN_EDIT__DWT192_classAttr);
		if ( !attrs.contains("ZSelected") ) {
			return (false);
		}

		return (true);
		
	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public void zNavigateTo() throws HarnessException {
		throw new HarnessException("implement me");
	}

	@Override
	public AbsPage zListItem(Action action, String item)
			throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String item)
			throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption ,String item)
			throws HarnessException {
		// TODO Auto-generated method stub
		return null;	
	}
	
	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option)
			throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}

}
