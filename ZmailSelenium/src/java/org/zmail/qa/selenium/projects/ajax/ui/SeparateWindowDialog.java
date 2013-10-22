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
package org.zmail.qa.selenium.projects.ajax.ui;

import org.zmail.qa.selenium.framework.ui.AbsApplication;
import org.zmail.qa.selenium.framework.ui.AbsPage;
import org.zmail.qa.selenium.framework.ui.AbsSeparateWindow;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.projects.ajax.ui.DialogWarning.DialogWarningID;
import org.zmail.qa.selenium.projects.ajax.ui.mail.FormMailNew;

public class SeparateWindowDialog extends AbsSeparateWindow {

	protected String MyDivId = null;

	public SeparateWindowDialog(DialogWarningID dialogId, AbsApplication application, AbsSeparateWindow window) {
		super(application);
		
		// Remember which div this object is pointing at
		/*
		 * Example:
		 * <div id="YesNoCancel" style="position: absolute; overflow: visible; left: 229px; top: 208px; z-index: 700;" class="DwtDialog" parentid="z_shell">
		 *   <div class="DwtDialog WindowOuterContainer">
		 *   ...
		 *   </div>
		 * </div>
		 */
		MyDivId = dialogId.Id;
				
		logger.info("new " + SeparateWindowDialog.class.getCanonicalName());

	}

	public SeparateWindowDialog(AbsApplication application) {
		super(application);
	}

	@Override
	public String myPageName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void zSetWindowTitle(String title) throws HarnessException {
		DialogWindowTitle = title;
	}
	
	public void zSetWindowID(String id) throws HarnessException {
		this.DialogWindowID = id;
	}
	
	public AbsPage zClickButton(Button button) throws HarnessException {
		if ( button == null )
			throw new HarnessException("button cannot be null");

		String locator = null;
		AbsPage page = null; 		// Does this ever result in a page being returned?

		// See http://bugzilla.zmail.com/show_bug.cgi?id=54560
		// Need unique id's for the buttons
		String buttonsTableLocator = "css=div[id='"+ MyDivId +"'] div[id$='_buttons']";

		if ( button == Button.B_YES ) {

			locator = buttonsTableLocator + " td[id^='Yes_'] td[id$='_title']";

			if(MyDivId.contains("css=div[class=DwtConfirmDialog]")){
				page = 	new FormMailNew(this.MyApplication);
			}


		} else if ( button == Button.B_NO ) {

			locator = buttonsTableLocator + " td[id^='No_'] td[id$='_title']";

		} else if ( button == Button.B_CANCEL ) {

			locator = buttonsTableLocator + " td[id^='Cancel_'] td[id$='_title']";

		} else if (button == Button.B_OK) {

			locator = buttonsTableLocator + " td[id^='OK_'] td[id$='_title']";

		} else {
			throw new HarnessException("no logic defined for button "+ button);
		}

		if ( locator == null ) {
			throw new HarnessException("locator was null for button "+ button);
		}

		// Default behavior, process the locator by clicking on it
		//

		// Click it
		zClickAt(locator,"0,0");

		return (page);
	}


}
