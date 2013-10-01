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
package com.zimbra.qa.selenium.framework.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.zimbra.qa.selenium.framework.util.HarnessException;

/**
 * A <code>AbsDisplay</code> object represents a read-only "displayed object", 
 * such as a message, contact, appointment (as attendee), document, etc.
 * <p>
 * Displayed objects are usually returned after opening or selecting an
 * object from a list, such as double-clicking on a message from the mail
 * message list view.
 * <p>
 * The Display object allows the test method to access displayed information,
 * such as To addresses, subject, message body, contact field, appointment
 * start time, etc.
 * <p>
 * 
 * @author Matt Rhoades
 *
 */
public abstract class AbsDisplay extends AbsPage {
	protected static Logger logger = LogManager.getLogger(AbsDisplay.class);

	/**
	 * Create this page object that exists in the specified application
	 * @param application
	 */
	public AbsDisplay(AbsApplication application) {
		super(application);
		
		logger.info("new AbsDisplayPage");
	}
		
	
	/**
	 * Return the unique name for this page class
	 * @return
	 */
	public abstract String myPageName();
	
	/**
	 * Click on a Button in the display
	 */
	public abstract AbsPage zPressButton(Button button) throws HarnessException;
	
}
