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
/**
 * 
 */
package org.zmail.qa.selenium.projects.ajax.ui.mail;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.projects.ajax.ui.AppAjaxClient;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShare;
import org.zmail.qa.selenium.projects.ajax.ui.DialogShareRevoke;

/**
 * Represents a "Create New Folder" dialog box
 * 
 * Lots of methods not yet implemented. See
 * https://bugzilla.zmail.com/show_bug.cgi?id=55923
 * <p>
 * 
 * @author Matt Rhoades
 * 
 */
public class DialogEditFolder extends AbsDialog {

	public static class Locators {

		public static final String zEditPropertiesDialogId = "ZmFolderPropsDialog";
		public static final String zEditPropertiesDialogDropDown = "css=div.DwtDialogBody div.ImgSelectPullDownArrow";
		public static final String zGrayColorId = "//td[contains(@id,'_title') and contains(text(),'Gray')]";
		public static final String zBlueColorId = "//td[contains(@id,'_title') and contains(text(),'Blue')]";
		public static final String zCyanColorId = "//td[contains(@id,'_title') and contains(text(),'Cyan')]";
		public static final String zGreenColorId = "//td[contains(@id,'_title') and contains(text(),'Green')]";

		public static final String zPurpleColorId = "//td[contains(@id,'_title') and contains(text(),'Purple')]";
		public static final String zRedColorId = "//td[contains(@id,'_title') and contains(text(),'Red')]";
		public static final String zYellowColorId = "//td[contains(@id,'_title') and contains(text(),'Yellow')]";
		public static final String zPinkColorId = "//td[contains(@id,'_title') and contains(text(),'Pink')]";
		public static final String zOrangeColorId = "//td[contains(@id,'_title') and contains(text(),'Orange')]";

	}

	public DialogEditFolder(AbsApplication application, AbsTab tab) {
		super(application, tab);
		logger.info("new " + DialogEditFolder.class.getCanonicalName());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see framework.ui.AbsDialog#myPageName()
	 */
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		logger.info(myPageName() + " zIsActive()");

		logger.info(myPageName() + " zIsActive()");

		String locator = "class=" + Locators.zEditPropertiesDialogId;

		if (!this.sIsElementPresent(locator)) {
			return (false); // Not even present
		}

		if (!this.zIsVisiblePerPosition(locator, 0, 0)) {
			return (false); // Not visible per position
		}

		// Yes, visible
		logger.info(myPageName() + " zIsActive() = true");
		return (true);
	}

	@Override
	public AbsPage zClickButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zClickButton(" + button + ")");

		tracer.trace("Click dialog button " + button);

		AbsPage page = null;
		String locator = null;

		if (button == Button.B_OK) {

			locator = "//div[@class='" + Locators.zEditPropertiesDialogId+ "']//div[contains(@id,'_buttons')]//td[text()='OK']";

		} else if (button == Button.B_CANCEL) {

			locator = "//div[@class='" + Locators.zEditPropertiesDialogId+ "']//div[contains(@id,'_buttons')]//td[text()='Cancel']";

		}else if (button == Button.B_SHARE) {

			locator = "//div[@class='" + Locators.zEditPropertiesDialogId+ "']//div[contains(@id,'_buttons')]//td[text()=''Add Share...']";

		} else if (button == Button.O_RESEND_LINK) {
			
			throw new HarnessException("implement me");
			
		} else if (button == Button.O_REVOKE_LINK) {
			
			locator = "//div[@class='" + Locators.zEditPropertiesDialogId+ "']//div[contains(@id,'_content')]//div/fieldset/div/table/tbody/tr/td/a[contains(text(),'Revoke')]";
			page = new DialogShareRevoke(MyApplication,((AppAjaxClient) MyApplication).zPageMail);
			
			// Click the link
			this.sClick(locator);
			this.zWaitForBusyOverlay();

			// Wait for the Edit dialog to appear
			page.zWaitForActive();
			this.zWaitForBusyOverlay();

			// Done
			return (page);

		} else if (button == Button.O_EDIT_LINK) {

			locator = "//div[@class='" + Locators.zEditPropertiesDialogId+ "']//div[contains(@id,'_content')]//div/fieldset/div/table/tbody/tr/td/a[contains(text(),'Edit')]";
			page = new DialogShare(MyApplication,((AppAjaxClient) MyApplication).zPageMail);
			
			// Click the link
			this.sClick(locator);
			this.zWaitForBusyOverlay();

			// Wait for the Edit dialog to appear
			page.zWaitForActive();
			this.zWaitForBusyOverlay();

			// Done
			return (page);
			
		} else {
			throw new HarnessException("Button " + button + " not implemented");
		}

		
		// Make sure the locator was set
		if (locator == null) {
			throw new HarnessException("Button " + button + " not implemented");
		}
		
		this.zClick(locator);

		this.zWaitForBusyOverlay();

		return (page);
	}

	@Override
	public String zGetDisplayedText(String locator) throws HarnessException {
		logger.info(myPageName() + " zGetDisplayedText(" + locator + ")");

		if (locator == null)
			throw new HarnessException("locator was null");

		return (this.sGetText(locator));
	}

	/**
	 * Set the new folder name
	 * 
	 * @param folder
	 */
	public void zSetNewName(String folder) throws HarnessException {
		logger.info(myPageName() + " zEnterFolderName(" + folder + ")");

		tracer.trace("Enter new folder name " + folder);

		if (folder == null)
			throw new HarnessException("folder must not be null");

		String locator = "css=div.DwtDialogBody div input";

		if (!this.sIsElementPresent(locator))
			throw new HarnessException("unable to find folder name field "
					+ locator);

		// For some reason, the text doesn't get entered on the first try
		this.sFocus(locator);
		this.zClick(locator);
		zKeyboard.zTypeCharacters(folder);
		if (!(sGetValue(locator).equalsIgnoreCase(folder))) {
			sType(locator, folder);
		}

		this.zWaitForBusyOverlay();

	}

	public enum FolderColor {
		None, Blue, Cyan, Green, Purple, Red, Yellow, Pink, Gray, Orange, MoreColors
	}

	/**
	 * Set the color pulldown
	 * 
	 * @param folder
	 */
	public void zSetNewColor(FolderColor color) throws HarnessException {
		logger.info(myPageName() + " zEnterFolderColor(" + color + ")");
		String actionLocator = null;
		String optionLocator = null;
		tracer.trace("Enter folder color " + color);

		if (color == null)
			throw new HarnessException("folder must not be null");

		if (color == FolderColor.MoreColors)
			throw new HarnessException("'more colors' - implement me!");
		if (color == FolderColor.Gray) {

			actionLocator = Locators.zEditPropertiesDialogDropDown;
			optionLocator = Locators.zGrayColorId;

			zClick(actionLocator);
			zClick(optionLocator);

		} else if (color == FolderColor.Blue) {

			actionLocator = Locators.zEditPropertiesDialogDropDown;
			optionLocator = Locators.zBlueColorId;

			zClick(actionLocator);
			zClick(optionLocator);

		} else if (color == FolderColor.Cyan) {

			actionLocator = Locators.zEditPropertiesDialogDropDown;
			optionLocator = Locators.zCyanColorId;

			zClick(actionLocator);
			zClick(optionLocator);

		} else if (color == FolderColor.Green) {

			actionLocator = Locators.zEditPropertiesDialogDropDown;
			optionLocator = Locators.zGreenColorId;

			zClick(actionLocator);
			zClick(optionLocator);

		} else if (color == FolderColor.Purple) {

			actionLocator = Locators.zEditPropertiesDialogDropDown;
			optionLocator = Locators.zPurpleColorId;

			zClick(actionLocator);
			zClick(optionLocator);

		} else {
			throw new HarnessException("color " + color
					+ " not yet implemented");
		}

	}

}
