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
package com.zimbra.qa.selenium.projects.ajax.ui.preferences.signature;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.zimbra.qa.selenium.framework.items.IItem;
import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.ui.AbsForm;
import com.zimbra.qa.selenium.framework.ui.AbsPage;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.ui.I18N;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.ajax.ui.AppAjaxClient;
import com.zimbra.qa.selenium.projects.ajax.ui.DialogWarning;

public class FormSignatureNew extends AbsForm {

	public FormSignatureNew(AbsApplication application) {
		super(application);
		// TODO Auto-generated constructor stub
	}

	public static class Locators {

		public static final String zsignatureNameLabel = "css=input[id*='_SIG_NAME']";
		public static final String signatureBody = "css=textarea[id*='TEXTAREA_SIGNATURE']";
		public static final String zToolbarSaveID = "zb__PREF__SAVE_title";
		public static final String zToolbarCancelID = "zb__PREF__CANCEL_title";
		// TODO: Need to I18N these locators
		public static final String formatAsText = "//td[contains(@id,'_title') and contains (text(),'"+I18N.FORMAT_AS_PLAIN_TEXT+"')]";
		public static final String formatAsHtml = "//td[contains(@id,'_title') and contains (text(),'"+I18N.FORMAT_AS_HTML_TEXT+"')]";
		public static final String zFrame = "css=iframe[id='TEXTAREA_SIGNATURE_ifr']";
		public static final String zHtmlBodyField = "css=body";
	}

	public static class Field {

		public static final Field SignatureName = new Field("SignatureName");
		public static final Field SignatureBody = new Field("SignatureBody");
		public static final Field SignatureHtmlBody = new Field("SignatureHtmlBody");

		private String field;

		private Field(String name) {
			field = name;
		}

		@Override
		public String toString() {
			return (field);
		}

	}

	@Override
	public String myPageName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void zFill(IItem item) throws HarnessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void zSubmit() throws HarnessException {
		// TODO Auto-generated method stub
		zToolbarPressButton(Button.B_SAVE);
		this.zWaitForBusyOverlay();
	}

	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButton(" + button + ")");

		tracer.trace("Click button " + button);

		if (button == null)
			throw new HarnessException("Button cannot be null!");

		// Default behavior variables
		//
		String locator = null; // If set, this will be clicked
		AbsPage page = null; // If set, this page will be returned

		// Based on the button specified, take the appropriate action(s)
		//

		if (button == Button.B_SAVE) {

			locator = "id=" + Locators.zToolbarSaveID;
			page = null;

		} else if (button == Button.B_CANCEL) {

			locator = "id=" + Locators.zToolbarCancelID;
			page = new DialogWarning(DialogWarning.DialogWarningID.SaveSignatureChangeMessage, this.MyApplication, ((AppAjaxClient)this.MyApplication).zPageSignature);
			

		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		if (locator == null) {
			throw new HarnessException("locator was null for button " + button);
		}

		// Default behavior, process the locator by clicking on it
		//

		// Make sure the button exists
		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Button is not present locator="
					+ locator + " button=" + button);

		// Click it
		this.zClickAt(locator,"");

		return (page);
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Fill in the form field with the specified text
	 * 
	 * @param field
	 * @param value
	 * @throws HarnessException
	 */
	public void zFillField(Field field, String value) throws HarnessException {
		tracer.trace("Set " + field + " to " + value);

		String locator = null;

		if (field == Field.SignatureName) {
		   locator = Locators.zsignatureNameLabel;

		} else if (field == Field.SignatureBody) {
			locator = Locators.signatureBody;
			this.sFocus(locator);
			this.zClickAt(locator,"");
			zKeyboard.zTypeCharacters(value);

			if (!(sGetValue(locator).equalsIgnoreCase(value))) {
				this.sFocus(locator);
				this.zClickAt(locator,"");
				sType(locator, value);
			}
			return;

		} else if (field == Field.SignatureHtmlBody) {
			//locator = Locators.zHtmlBodyField;
		    if(ZimbraSeleniumProperties.isWebDriver()){
			sClickAt("//div[contains(@class,'ZmHtmlEditor')]","");
			zTypeFormattedText("css=iframe[id*=ifr]", value);					
		    }else{
			locator = "css=body[id='tinymce']";
			try{
				sSelectFrame(Locators.zFrame);
				this.sFocus(locator);
				this.zClickAt(locator,"");
				Robot zRobot;
				try {
					zRobot = new Robot();
					zRobot.keyPress(KeyEvent.VK_CONTROL);
					zRobot.keyPress(KeyEvent.VK_A);
					zRobot.keyRelease(KeyEvent.VK_CONTROL);
					zRobot.keyRelease(KeyEvent.VK_A);				
				} catch (AWTException e) {
					e.printStackTrace();
				}
				this.zKeyboard.zTypeCharacters(value);
			}finally{
				sSelectFrame("relative=top");
			}
		    }
		return;

		} else {

			throw new HarnessException("not implemented for field " + field);
		}

		// Make sure the button exists
		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Field is not present field=" + field
					+ " locator=" + locator);

		// Enter text
		if(ZimbraSeleniumProperties.isWebDriver()){
		    this.zClickAt(locator,"");
		    clearField(locator);
		    sType(locator, value);
		}else{
		    this.sFocus(locator);
		    this.zClickAt(locator,"");
		    zKeyboard.zTypeCharacters(value);

		    if (!(sGetValue(locator).equalsIgnoreCase(value))) {
			this.sFocus(locator);
			this.zClickAt(locator,"");
			sType(locator, value);
		    }
		}
		this.zWaitForBusyOverlay();

	}

	public void zSelectFormat(String format) throws HarnessException {
		if (format.equals("html")) {
			boolean isExists = this.sIsElementPresent(Locators.formatAsText);
			if (isExists) {
				this.zClick(Locators.formatAsText);

			} else {
				this.zClick(Locators.formatAsHtml);
			}
			this.zClick(Locators.formatAsHtml);
		}

	}

}
