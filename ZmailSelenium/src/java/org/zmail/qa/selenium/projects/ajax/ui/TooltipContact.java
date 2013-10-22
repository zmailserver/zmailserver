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
package org.zmail.qa.selenium.projects.ajax.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.zmail.qa.selenium.framework.ui.AbsTab;

public class TooltipContact extends Tooltip {
	protected static Logger logger = LogManager.getLogger(TooltipContact.class);

	public static class Locators {
	
	}
	
	public TooltipContact(AbsTab tab) {	
		super(tab);
		
		logger.info("new " + this.getClass().getCanonicalName());
	}
	

	@Override
	public String myPageName() {
		return (this.getClass().getCanonicalName());
	}
	
}
