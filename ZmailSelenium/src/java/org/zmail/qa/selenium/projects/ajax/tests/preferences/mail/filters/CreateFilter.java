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
package org.zmail.qa.selenium.projects.ajax.tests.preferences.mail.filters;

import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.ajax.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.DialogEditFilter;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.DialogEditFilter.ConditionConstraint;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.DialogEditFilter.ConditionType;
import org.zmail.qa.selenium.projects.ajax.ui.preferences.TreePreferences.TreeItem;


public class CreateFilter extends AjaxCommonTest {

	public CreateFilter() {
		
		super.startingPage = app.zPagePreferences;
		super.startingAccountPreferences = null;
		
	}


	@Test(
			description = "Create a basic filter",
			groups = { "smoke" }
			)
	public void CreateFilter_01() throws HarnessException {

		String filterName = "filter"+ ZmailSeleniumProperties.getUniqueString();
		String conditionValue = "contains"+ ZmailSeleniumProperties.getUniqueString();

		
		// Navigate to preferences -> mail -> composing
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.MailFilters);

		// See https://bugzilla.zmail.com/show_bug.cgi?id=62323
		// **
		
		// Click "Add New"
		DialogEditFilter dialog = (DialogEditFilter)app.zPagePreferences.zToolbarPressButton(Button.B_NEW_FILTER);
		
		// Give a name
		dialog.zSetFilterName(filterName);
		
		// Give a criteria
		dialog.zAddFilterCriteria(ConditionType.Subject, ConditionConstraint.Contains, conditionValue);
		
		// Give an action (if necessary)
		// dialog.zAddFilterAction();

		// Save
		dialog.zClickButton(Button.B_OK);
		
				
		
		// Verify the filter is created
		app.zGetActiveAccount().soapSend(
						"<GetFilterRulesRequest xmlns='urn:zmailMail'/>");
		
		Element[] rules = app.zGetActiveAccount().soapSelectNodes("//mail:GetFilterRulesResponse//mail:filterRule[@name='" + filterName +"']");
		ZAssert.assertEquals(rules.length, 1, "Verify the rule exists in the server");
		
	}
}
