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
package org.zmail.qa.selenium.projects.desktop.ui;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.projects.desktop.ui.mail.FormMailNew;


/**
 * A <code>DialogWarning</code> object represents a "Warning" dialog, such as "Save 
 * current message as draft", etc.
 * <p>
 * During construction, the div ID attribute must be specified, such as "YesNoCancel".
 * <p>
 * @author Matt Rhoades
 *
 */
public class DialogWarning extends AbsDialog {

	public static class DialogWarningID {
		
		public static DialogWarningID SaveCurrentMessageAsDraft = new DialogWarningID("YesNoCancel");

		public static DialogWarningID SaveTaskChangeMessage = new DialogWarningID("YesNoCancel");
		public static DialogWarningID SaveSignatureChangeMessage = new DialogWarningID("YesNoCancel");
		public static DialogWarningID SendLink = new DialogWarningID("css=div[class=DwtConfirmDialog]");
		public static DialogWarningID DeleteTagWarningMessage = new DialogWarningID("YesNoCancel");
		public static DialogWarningID EmptyFolderWarningMessage = new DialogWarningID("OkCancel");

		public static DialogWarningID CancelCreateContact = new DialogWarningID("YesNoCancel");

		protected String Id;
		protected DialogWarningID(String id) {
			Id = id;
		}
	}
	
	protected String MyDivId = null;
	
	
	public DialogWarning(DialogWarningID dialogId, AbsApplication application, AbsTab tab) {
		super(application, tab);
		
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
		
		logger.info("new " + DialogWarning.class.getCanonicalName());

	}
	
	public String zGetWarningTitle() throws HarnessException {
		String locator = "css=div[id='"+ MyDivId +"'] td[id='"+ MyDivId +"_title']";
		return (zGetDisplayedText(locator));
	}
	
	public String zGetWarningContent() throws HarnessException {
		String locator = "css=div[id='"+ MyDivId +"'] div[id='"+ MyDivId +"_content']";
		return (zGetDisplayedText(locator));
	}
	
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public AbsPage zClickButton(Button button) throws HarnessException {
		if ( button == null )
			throw new HarnessException("button cannot be null");
		
		String locator = null;
		AbsPage page = null; 		// Does this ever result in a page being returned?
		

		// See http://bugzilla.zmail.com/show_bug.cgi?id=54560
		// Need unique id's for the buttons
		String buttonsTableLocator = "//div[@id='"+ MyDivId +"']//div[contains(@id, '_buttons')]";
		
		if ( button == Button.B_YES ) {
			if(MyDivId.contains("css=div[class=DwtConfirmDialog]")){
				locator = "css=td[class=ZWidgetTitle]:contains(Yes)";				
				page = 	new FormMailNew(this.MyApplication);
			}else{
				locator = buttonsTableLocator + "//table//table//tr/td[1]/div";
			}
			
		} else if ( button == Button.B_NO ) {

			locator = buttonsTableLocator + "//table//table//tr/td[2]/div";

		} else if ( button == Button.B_CANCEL ) {

			locator = buttonsTableLocator + "//table//table//tr/td[3]/div";

		} else if (button == Button.B_OK) {
			if (MyDivId.contains("ErrorDialog")) {
				locator = buttonsTableLocator + "//table//table//tr/td/div[contains(@id,'ErrorDialog_button2')]";
			} else {
				locator = buttonsTableLocator + "//table//table//tr/td[1]/div";
			}

		} else {
			throw new HarnessException("no logic defined for button "+ button);
		}

		if ( locator == null ) {
			throw new HarnessException("locator was null for button "+ button);
		}
		
		// Default behavior, process the locator by clicking on it
		//
				
		// Click it
		this.zClick(locator);
		
		// If the app is busy, wait for it to become active
		this.zWaitForBusyOverlay();
		
		// If page was specified, make sure it is active
		if ( page != null ) {
			
			// This function (default) throws an exception if never active
			page.zWaitForActive();
			
		}

		return (page);
	}

	@Override
	public String zGetDisplayedText(String locator) throws HarnessException {
		if ( locator == null )
			throw new HarnessException("locator cannot be null");
		
		if ( !this.sIsElementPresent(locator) )
			throw new HarnessException("locator cannot be found");
		
		return (this.sGetText(locator));
		
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		if ( !this.sIsElementPresent(MyDivId) )
			return (false);
		if ( !this.zIsVisiblePerPosition(MyDivId, 225, 650) )
			return (false);
		return (true);
	}

}
