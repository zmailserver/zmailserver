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
package com.zimbra.qa.selenium.projects.ajax.ui.briefcase;

import org.openqa.selenium.WebElement;

import com.zimbra.qa.selenium.framework.items.DocumentItem;
import com.zimbra.qa.selenium.framework.items.IItem;
import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.ui.AbsForm;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.ui.AppAjaxClient;
import com.zimbra.qa.selenium.projects.ajax.ui.briefcase.DocumentBriefcaseNew.Field;

public class DocumentBriefcaseEdit extends AbsForm {

	public static class Locators {
		public static final String zFrame = "css=iframe[id^='DWT'][class='ZDEditor']";
		public static final String zSaveAndCloseIconBtn = "//*[@id='DWT8_left_icon']";
		public static final String zBodyField = "css=body";
		public static final String zNameField = "css=[class=DwtInputField] input";
	}

	private DocumentItem docItem;

	public DocumentItem getDocItem() {
		return docItem;
	}

	public DocumentBriefcaseEdit(AbsApplication application,
			DocumentItem document) {
		super(application);

		docItem = document;

		logger.info("new " + DocumentBriefcaseEdit.class.getCanonicalName());
	}

	@Override
	public String myPageName() {
		return this.getClass().getName();
	}

	public void typeDocumentText(String text) throws HarnessException {
		// ClientSessionFactory.session().selenium().getEval("var x = selenium.browserbot.findElementOrNull(\""+Locators.zFrame+"\");if(x!=null)x=x.contentWindow.document.body;if(browserVersion.isChrome){x.textContent='"+text+"';}else if(browserVersion.isIE){x.innerText='"+text+"';}");
		sSelectFrame(Locators.zFrame);
		logger.info("typing Document Text" + text);
		// SleepUtil.sleepSmall();
		sType(Locators.zBodyField, text);
	}

	public String retriveDocumentText() throws HarnessException {
		// ClientSessionFactory.session().selenium().getEval("var x = selenium.browserbot.findElementOrNull(\""+Locators.zFrame+"\");if(x!=null)x=x.contentWindow.document.body;if(browserVersion.isChrome){x.textContent;}else if(browserVersion.isIE){x.innerText;}");
		sSelectFrame(Locators.zFrame);
		String text = "";
		if (sIsElementPresent(Locators.zBodyField)) {
			text = sGetText(Locators.zBodyField);
		}
		return text;
	}

	public void typeDocumentName(String text) throws HarnessException {
		zSelectWindow(docItem.getName());
		sType(Locators.zNameField, text);
	}

	@Override
	public void zFill(IItem item) throws HarnessException {
	}

	public void zFillField(Field field, String value) throws HarnessException {

		if (field == Field.Name) {

			String nameFieldLocator = Locators.zNameField;

			// Make sure the locator exists
			if (!this.sIsElementPresent(nameFieldLocator))
				throw new HarnessException("Locator is not present: "
						+ nameFieldLocator);

			this.sMouseOver(nameFieldLocator);
			this.sFocus(nameFieldLocator);
			this.zClickAt(nameFieldLocator, "0,0");
			
			if (ZimbraSeleniumProperties.isWebDriver()) {
				this.clearField(nameFieldLocator);
			}
			
			this.sType(nameFieldLocator, value);
			
			logger.info("typed: " + value);

		} else if (field == Field.Body) {

			String iframeLocator = Locators.zFrame;

			// Make sure the locator exists
			if (!this.sIsElementPresent(iframeLocator))
				throw new HarnessException("Locator is not present: "
						+ iframeLocator);
			if (ZimbraSeleniumProperties.isWebDriver()) {
				// String locator = Locators.zBodyField;
				// sSelectFrame(Locators.zFrame);
				// this.sType(locator, value);

				WebElement we = getElement(iframeLocator);
				this.sMouseOver(iframeLocator);
				this.sFocus(iframeLocator);
				this.zClickAt(iframeLocator, "0,0");

				this.executeScript(
						"var bodytext=\""
								+ value
								+ "\";"								
								+ "var iframe_body=arguments[0].contentWindow.document.body;"
								+ "if (navigator.userAgent.indexOf('Firefox')!=-1 || navigator.userAgent.indexOf('Chrome')!=-1){iframe_body.innerHTML=bodytext;}"
								+ "else if(navigator.userAgent.indexOf('MSIE')!=-1){iframe_body.innerHTML=bodytext;}"
								+ "else {iframe_body.innerHTML=bodytext;}", we);

			} else {
				this.sMouseOver(iframeLocator);
				this.sFocus(iframeLocator);
				this.zClickAt(iframeLocator, "0,0");

				this.sGetEval("var bodytext=\""
						+ value
						+ "\";"
						+ "var iframe_locator=\""
						+ iframeLocator
						+ "\";"
						+ "var iframe_body=selenium.browserbot.findElement(iframe_locator).contentWindow.document.body;"
						+ "if (browserVersion.isFirefox || browserVersion.isChrome){iframe_body.textContent=bodytext;}"
						+ "else if(browserVersion.isIE){iframe_body.innerText=bodytext;}"
						+ "else {iframe_body.innerText=bodytext;}");
			}
		} else {
			throw new HarnessException("Not implemented field: " + field);
		}

		this.zWaitForBusyOverlay();
	}

	@Override
	public void zSubmit() throws HarnessException {
		zSelectWindow(docItem.getName());

		logger.info("DocumentBriefcaseEdit.SaveAndClose()");

		// Look for "Save & Close"
		if (!this.sIsElementPresent(Locators.zSaveAndCloseIconBtn))
			throw new HarnessException("Save & Close button is not present "
					+ Locators.zSaveAndCloseIconBtn);

		boolean visible = this.sIsVisible(Locators.zSaveAndCloseIconBtn);
		if (!visible)
			throw new HarnessException("Save & Close button is not visible "
					+ Locators.zSaveAndCloseIconBtn);

		// Click on it
		zClickAt(Locators.zSaveAndCloseIconBtn, "0,0");
		// this.sMouseDown(Locators.zSaveAndCloseIconBtn);
		// this.sMouseUp(Locators.zSaveAndCloseIconBtn);

		// add version notes
		DialogAddVersionNotes dlgAddNotes = new DialogAddVersionNotes(
				MyApplication, ((AppAjaxClient) MyApplication).zPageBriefcase);

		dlgAddNotes.zDismissAddVersionNotesDlg(docItem.getName());
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		logger.info("DocumentBriefcaseEdit.zIsActive()");
		if (docItem != null) {

			zWaitForWindow(docItem.getName());

			zSelectWindow(docItem.getName());

			zWaitForElementPresent("css=div[class='ZDToolBar ZWidget']");

			zWaitForElementPresent("css=iframe[id*='DWT'][class='ZDEditor']");

			zWaitForIframeText("css=iframe[id*='DWT'][class='ZDEditor']",
					docItem.getDocText());

			logger.info("DocumentBriefcaseEdit is Active()");

			return true;
		} else {
			logger.info("DocumentBriefcaseEdit.docItem is null");

			return false;
		}
	}
}
