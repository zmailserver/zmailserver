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
package com.zimbra.qa.selenium.projects.ajax.ui;


import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.staf.Stafpostqueue;



/**
 * Represents a "Rename Folder" dialog box
 * <p>
 * @author Matt Rhoades
 *
 */
public class DialogShare extends AbsDialog {

	public static class Locators {
		public static final String zDialogShareId = "ShareDialog";
		public static final String zButtonsId = "ShareDialog_buttons";
	}
	
	
	public DialogShare(AbsApplication application, AbsTab tab) {
		super(application, tab);
	}
	
	public static class ShareWith {
		public static ShareWith InternalUsers	 = new ShareWith("InternalUsers");
		public static ShareWith ExternalGuests	 = new ShareWith("ExternalGuests");
		public static ShareWith Public			 = new ShareWith("Public");
		
		protected String ID;
		protected ShareWith(String id) {
			ID = id;
		}
		public String toString() {
			return (ID);
		}

	}
	
	public void zSetShareWith(ShareWith type) throws HarnessException {
		logger.info(myPageName() + " zSetShareWith("+ type +")");

		String locator = null;
		
		if ( type == ShareWith.InternalUsers ) {
			
			locator = "css=input#ShareWith_user";

		} else if ( type == ShareWith.ExternalGuests ) {
			
			locator = "css=input#ShareWith_external";
			
		} else if ( type == ShareWith.Public ) {
			
			locator = "css=input#ShareWith_public";
			
		} else {
			throw new HarnessException("type = " + type + " not implemented yet");
		}
		
		// Make sure the locator exists
		if ( !this.sIsElementPresent(locator) ) {
			throw new HarnessException("zSetShareWith "+ locator +" is not present");
		}
		
		// check the box
		this.sClick(locator);
		this.zWaitForBusyOverlay();
		
	}
	
	public void zSetEmailAddress(String email) throws HarnessException {
		logger.info(myPageName() + " zSetEmailAddress(" + email + ")");

		String locator = "css=div#ShareDialog_grantee>input";

		// Make sure the locator exists
		if (!this.sIsElementPresent(locator)) {
			throw new HarnessException("zSetEmailAddress " + locator + " is not present");
		}
		
		
		// Seems that the client can't handle filling out the new mail form too quickly
		// Click in the "To" fields, etc, to make sure the client is ready
		this.sFocus(locator);
		this.zClick(locator);
		this.zWaitForBusyOverlay();

		// Instead of sType() use zKeyboard
		this.zKeyboard.zTypeCharacters(email + ";");
		

	}
	
	public static class ShareRole {
		public static ShareRole None	 = new ShareRole("None");
		public static ShareRole Viewer	 = new ShareRole("Viewer");
		public static ShareRole Manager	 = new ShareRole("Manager");
		public static ShareRole Admin	 = new ShareRole("Admin");
		
		protected String ID;
		protected ShareRole(String id) {
			ID = id;
		}
		
		public String toString() {
			return (ID);
		}

	}
	
	public void zSetRole(ShareRole role) throws HarnessException {
		logger.info(myPageName() + " zSetRole("+ role +")");
		String locator =null;
		if(role== ShareRole.Admin){
			locator = "//div[@id='"+ Locators.zDialogShareId +"']//div[contains(@id,'_content')]//div/fieldset/div/table/tbody/tr[4]/td/input[contains(@id,'ShareRole_ADMIN')]";
		}else if (role== ShareRole.Manager){
			locator = "//div[@id='"+ Locators.zDialogShareId +"']//div[contains(@id,'_content')]//div/fieldset/div/table/tbody/tr[3]/td/input[contains(@id,'ShareRole_MANAGER')]";
		}else{
			throw new HarnessException("zSetRole "+ locator +" is not present");
		}
		this.sFocus(locator);
		this.sClick(locator);
		//this.sCheck(locator);
	}
	
	public static class ShareMessageType {
		public static ShareMessageType SendStandardMsg		 = new ShareMessageType("SendStandardMsg");
		public static ShareMessageType DoNotSendMsg			 = new ShareMessageType("DoNotSendMsg");
		public static ShareMessageType AddNoteToStandardMsg	 = new ShareMessageType("AddNoteToStandardMsg");
		public static ShareMessageType ComposeInNewWindow	 = new ShareMessageType("ComposeInNewWindow");
		
		protected String ID;
		protected ShareMessageType(String id) {
			ID = id;
		}
		
		public String toString() {
			return (ID);
		}
	}
	
	public void zSetMessageType(ShareMessageType type) throws HarnessException {
		logger.info(myPageName() + " zSetMessageType("+ type +")");

		String locator = "implement me";
		
		// Make sure the locator exists
		if ( !this.sIsElementPresent(locator) ) {
			throw new HarnessException("zSetRole "+ locator +" is not present");
		}

	}
	

	@Override
	public AbsPage zClickButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zClickButton("+ button +")");

		String locator = null;
		
		if ( button == Button.B_OK ) {
			
			locator = "css=div[id='"+ Locators.zDialogShareId +"'] td[id^='OK'] td[id$='_title']";
			
		} else if ( button == Button.B_CANCEL ) {
			
			locator = "css=div[id='"+ Locators.zDialogShareId +"'] td[id^='Cancel'] td[id$='_title']";

		} else {
			throw new HarnessException("Button "+ button +" not implemented");
		}
		
		// Default behavior, click the locator
		//
		
		// Make sure the locator was set
		if ( locator == null ) {
			throw new HarnessException("Button "+ button +" not implemented");
		}
		
		this.zClick(locator);
		zWaitForBusyOverlay();
		
		// This dialog sends a message, so we need to check the queue
		Stafpostqueue sp = new Stafpostqueue();
		sp.waitForPostqueue();

		return (null);
	}

	@Override
	public String zGetDisplayedText(String locator) throws HarnessException {
		
		throw new HarnessException("implement me");
		
	}


	/* (non-Javadoc)
	 * @see framework.ui.AbsDialog#myPageName()
	 */
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		
		logger.info(myPageName() + " zIsActive()");

		String locator = "id="+ Locators.zDialogShareId;
		
		if ( !this.sIsElementPresent(locator) ) {
			return (false); // Not even present
		}
		
		if ( !this.zIsVisiblePerPosition(locator, 0, 0) ) {
			return (false);	// Not visible per position
		}
	
		// Yes, visible
		logger.info(myPageName() + " zIsActive() = true");
		return (true);
	}



}
