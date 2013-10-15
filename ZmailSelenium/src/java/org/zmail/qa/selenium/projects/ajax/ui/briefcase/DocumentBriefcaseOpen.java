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
package org.zmail.qa.selenium.projects.ajax.ui.briefcase;

import java.util.List;

import org.openqa.selenium.WebElement;

import org.zmail.qa.selenium.framework.core.ClientSessionFactory;
import org.zmail.qa.selenium.framework.items.DocumentItem;
import org.zmail.qa.selenium.framework.items.IItem;
import org.zmail.qa.selenium.framework.ui.AbsApplication;
import org.zmail.qa.selenium.framework.ui.AbsDisplay;
import org.zmail.qa.selenium.framework.ui.AbsPage;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;

public class DocumentBriefcaseOpen extends AbsDisplay {

	public static class Locators {
		public static final String zFrame = "css=iframe[id='DWT9']";
		public static final String zSaveAndCloseIconBtn = "//*[@id='DWT8_left_icon']";
		public static final String zBodyField = "css=body";
		public static final String zDocumentBodyField = "css=td[class='ZhAppContent'] div[id='zdocument']";
		public static final String zFileBodyField = "//html/body";
		public static final String zNameField = "css=[class=DwtInputField] [input$=]";
		public static final String zDocumentNameField = "css=[class=TbTop] b";
	}

	public String pageTitle;
	public String pageText;

	public DocumentBriefcaseOpen(AbsApplication application) {
		super(application);
		logger.info("new " + DocumentBriefcaseOpen.class.getCanonicalName());
	}

	public DocumentBriefcaseOpen(AbsApplication application, IItem item) {
		super(application);
		pageTitle = item.getName();
		
		if (item instanceof DocumentItem)
			pageText = ((DocumentItem) item).getDocText();

		logger.info("new " + DocumentBriefcaseOpen.class.getCanonicalName());
	}

	@Override
	public String myPageName() {
		return this.getClass().getName();
	}

	public String retriveFileText() throws HarnessException {
		String text = sGetText(Locators.zFileBodyField);

		return text;
	}

	public String retriveDocumentText() throws HarnessException {
		// ClientSessionFactory.session().selenium().selectFrame(Locators.zFrame);
		String text = sGetText(Locators.zDocumentBodyField);
		// if (zIsVisiblePerPosition(Locators.zDocumentBodyField, 0, 0)) {
		// text = zGetHtml(Locators.zBodyField);
		// text = sGetText(Locators.zBodyField);
		// text = sGetText(Locators.zDocumentBodyField);
		// }
		return text;
	}

	public String retriveDocumentName() throws HarnessException {
		String text = null;
		if(ZmailSeleniumProperties.isWebDriver()) {
			WebElement we = getElement(Locators.zDocumentNameField);
			if(we !=null)
			text = we.getText();
		}
		else if(ZmailSeleniumProperties.isWebDriverBackedSelenium())
			text = webDriverBackedSelenium().getText(Locators.zDocumentNameField);
		else
			text = sGetText(Locators.zDocumentNameField);

		return text;
	}

	public void typeDocumentName(String text) throws HarnessException {
		if (sIsElementPresent(Locators.zNameField))
			sType(Locators.zNameField, text);
	}

	public void zFill(IItem item) throws HarnessException {
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		zWaitForWindow(pageTitle);

		List<String> windows = sGetAllWindowNames();
		for (String window : windows) {
			if(window.indexOf(pageTitle.split("\\.")[0])!=-1){
				pageTitle = window;
			}
		}
		zSelectWindow(pageTitle);

		if (pageText != null)
		zWaitForElementPresent("css=td[class='ZhAppContent'] div:contains('"
				+ pageText + "')");

		return true;
	}

	@Override
	public AbsPage zPressButton(Button button) throws HarnessException {
		// TODO Auto-generated method stub
		return null;
	}
}
