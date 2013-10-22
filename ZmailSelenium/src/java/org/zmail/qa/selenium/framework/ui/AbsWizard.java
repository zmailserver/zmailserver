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
package org.zmail.qa.selenium.framework.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.zmail.qa.selenium.framework.items.IItem;
import org.zmail.qa.selenium.framework.util.HarnessException;


/**
 * This class defines an abstract Zmail Admin Console Application "Wizard"
 * 
 * Examples: "New Account" Wizard, "New COS" Wizard
 * 
 * 
 * @author Matt Rhoades
 *
 */
/**
 * A <code>AbsWizard</code> object represents a "wizard widget", 
 * such as a create account, create folder, new tag, etc.
 * <p>
 * Wizards usually display in a panel and include one or more steps
 * to create an object.
 * <p>
 * 
 * @see <a href="http://wiki.zmail.com/wiki/Testing:_Selenium:_ZmailSelenium_Overview#Folder_Page">Create a new folder</a>
 * @author Matt Rhoades
 *
 */
public abstract class AbsWizard extends AbsPage {
	protected static Logger logger = LogManager.getLogger(AbsWizard.class);
	public static class Locators {
	public static final String CANCEL_BUTTON = "zdlg__NEW_ACCT_button1_title";
	public static final String HELP_BUTTON = "zdlg__NEW_ACCT_button10_title";
	public static final String PREVIOUS_BUTTON = "zdlg__NEW_ACCT_button11_title";
	public static final String NEXT_BUTTON = "_button12_title";
	public static final String FINISH_BUTTON = "_button13_title";
	public static final String ACCOUNT_DIALOG="ACCT";
	public static final String DL_DIALOG="DL";
	public static final String DOMAIN_DIALOG="DOMAIN";
	public static final String COS_DIALOG="COS";
	public static final String RESOURCE_DIALOG="RES";
	}
	
	
	

	public enum WizardButton {
		Help, Cancel, Next, Previous, Finish
	}
	
	/**
	 * A pointer to the page that created this object
	 */
	protected AbsTab MyPage = null;
	
	/**
	 * Create a new wizard from the specified page
	 * @param page
	 */
	public AbsWizard(AbsTab page) {
		super(page.MyApplication);
		
		logger.info("new "+ AbsWizard.class.getName());
		MyPage = page;
	}
	
	
	/**
	 * Fill out this wizard with the specified Item data
	 * @param item
	 * @throws HarnessException
	 */
	public abstract IItem zCompleteWizard(IItem item) throws HarnessException;
	
	public void clickHelp(String dialogName) throws HarnessException {
		clickWizardButton(WizardButton.Help,dialogName);
	}
	
	public void clickCancel(String dialogName) throws HarnessException {
		clickWizardButton(WizardButton.Cancel,dialogName);
	}
	
	public void clickPrevious(String dialogName) throws HarnessException {
		clickWizardButton(WizardButton.Previous,dialogName);
	}

	public void clickNext(String dialogName) throws HarnessException {
		clickWizardButton(WizardButton.Next,dialogName);
	}

	public void clickFinish(String dialogName) throws HarnessException {
		clickWizardButton(WizardButton.Finish,dialogName);
	}
	
	protected void clickWizardButton(WizardButton button, String dialogName) throws HarnessException {

		// TODO: If possible, define in the abstract class
		String buttonPath = null;
		
		// Check if the button is enabled
		// throw HarnessException if not enabled
		// Click on the button
		switch(button) {
		case Finish : 
				buttonPath="css=td[id$='_" +  dialogName + Locators.FINISH_BUTTON + "']";
				break;
		case Next:
				buttonPath="css=td[id$='_" +  dialogName + Locators.NEXT_BUTTON + "']";
				break;				
		case Previous:
				buttonPath="css=td[id$='_" +  dialogName + Locators.PREVIOUS_BUTTON + "']";
				break;
		case Cancel:
				buttonPath="css=td[id$='_" +  dialogName + Locators.CANCEL_BUTTON + "']";
				break;
		case Help:
				buttonPath="css=td[id$='_" +  dialogName + Locators.HELP_BUTTON + "']";
			}
		
		if ( buttonPath == null ) {
			throw new HarnessException("buttonPath was null for "+ button);
		}
		
		if(sIsElementPresent(buttonPath))
			zClickAt(buttonPath,"");

	}

}
