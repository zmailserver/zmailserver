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
package org.zmail.qa.selenium.projects.ajax.core;

import java.util.ArrayList;

import org.apache.log4j.*;

import org.zmail.qa.selenium.framework.core.Bugs;
import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.QuickCommand.*;
import org.zmail.qa.selenium.framework.util.*;

/**
 * A base class that creates some basic Quick Command shortcuts in the mailbox.  This
 * class should only be used when testing Quick Commands.
 * 
 * 
 * @author Matt Rhoades
 *
 */
public class AjaxQuickCommandTest extends AjaxCommonTest {
	protected static Logger logger = LogManager.getLogger(AjaxQuickCommandTest.class);

	private QuickCommand command1;
	private QuickCommand command2;
	private QuickCommand command3;


	public AjaxQuickCommandTest() {
		logger.info("New "+ AjaxQuickCommandTest.class.getCanonicalName());
		
		command1 = null;
		command2 = null;
		command3 = null;
		
	}

	/**
	 * Quick Command:
	 * Item Type: Mail
	 * Action 1: Tag
	 * Action 2: Mark Read
	 * Action 3: Mark Flagged
	 * Action 4: Move To Subfolder
	 * @throws HarnessException
	 */
	protected QuickCommand getQuickCommand01() throws HarnessException {
		
		if ( command1 != null ) {
			// Command already exists, just return it
			return (command1);
		}
		
		
		// Create a tag
		String tagname = "tag" + ZmailSeleniumProperties.getUniqueString();
		ZmailAccount.AccountZWC().soapSend(
					"<CreateTagRequest xmlns='urn:zmailMail'>"
				+		"<tag name='"+ tagname +"' color='1' />"
				+	"</CreateTagRequest>");

		TagItem tag = TagItem.importFromSOAP(ZmailAccount.AccountZWC(), tagname);
		ZAssert.assertNotNull(tag, "Verify the tag was created");

		// Create a subfolder
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
		ZmailAccount.AccountZWC().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='"+ foldername +"' l='1' view='message'/>"
				+	"</CreateFolderRequest>");

		FolderItem folder = FolderItem.importFromSOAP(ZmailAccount.AccountZWC(), foldername);
		ZAssert.assertNotNull(folder, "Verify the subfolder is available");

		// Create the list of actions
		ArrayList<QuickCommandAction> actions = new ArrayList<QuickCommandAction>();
		actions.add(new QuickCommandAction(QuickCommandAction.TypeId.actionTag, tag.getId(), true));
		actions.add(new QuickCommandAction(QuickCommandAction.TypeId.actionFlag, "read", true));
		actions.add(new QuickCommandAction(QuickCommandAction.TypeId.actionFlag, "flagged", true));
		actions.add(new QuickCommandAction(QuickCommandAction.TypeId.actionFileInto, folder.getId(), true));


		String name = "name" + ZmailSeleniumProperties.getUniqueString();
		String description = "description" + ZmailSeleniumProperties.getUniqueString();
		
		command1 = new QuickCommand(name, description, ItemTypeId.MSG, true);
		command1.addActions(actions);

		return (command1);
	}
	
	/**
	 * Quick Command:
	 * Item Type: Contact
	 * Action 1: Tag
	 * Action 2: Move To Subfolder
	 * @throws HarnessException
	 */
	protected QuickCommand getQuickCommand02() throws HarnessException {

		
		if ( command2 != null ) {
			// Command already exists, just return it
			return (command2);
		}
		
		
		// Create a tag
		String tagname = "tag" + ZmailSeleniumProperties.getUniqueString();
		ZmailAccount.AccountZWC().soapSend(
					"<CreateTagRequest xmlns='urn:zmailMail'>"
				+		"<tag name='"+ tagname +"' color='1' />"
				+	"</CreateTagRequest>");

		TagItem tag = TagItem.importFromSOAP(ZmailAccount.AccountZWC(), tagname);
		ZAssert.assertNotNull(tag, "Verify the tag was created");

		// Create a subfolder
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
		ZmailAccount.AccountZWC().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='"+ foldername +"' l='1' view='contact'/>"
				+	"</CreateFolderRequest>");

		FolderItem folder = FolderItem.importFromSOAP(ZmailAccount.AccountZWC(), foldername);
		ZAssert.assertNotNull(folder, "Verify the subfolder is available");

		// Create the list of actions
		ArrayList<QuickCommandAction> actions = new ArrayList<QuickCommandAction>();
		actions.add(new QuickCommandAction(QuickCommandAction.TypeId.actionTag, tag.getId(), true));
		actions.add(new QuickCommandAction(QuickCommandAction.TypeId.actionFileInto, folder.getId(), true));


		String name = "name" + ZmailSeleniumProperties.getUniqueString();
		String description = "description" + ZmailSeleniumProperties.getUniqueString();
		
		command2 = new QuickCommand(name, description, ItemTypeId.CONTACT, true);
		command2.addActions(actions);

		return (command2);
	}
	
	/**
	 * Quick Command:
	 * Item Type: Appointment
	 * Action 1: Tag
	 * Action 2: Mark Read
	 * Action 3: Mark Flagged
	 * Action 4: Move To Subfolder
	 * @throws HarnessException
	 */
	protected QuickCommand getQuickCommand03() throws HarnessException {
		
		if ( command3 != null ) {
			// Command already exists, just return it
			return (command3);
		}
		
		
		// Create a tag
		String tagname = "tag" + ZmailSeleniumProperties.getUniqueString();
		ZmailAccount.AccountZWC().soapSend(
					"<CreateTagRequest xmlns='urn:zmailMail'>"
				+		"<tag name='"+ tagname +"' color='1' />"
				+	"</CreateTagRequest>");

		TagItem tag = TagItem.importFromSOAP(ZmailAccount.AccountZWC(), tagname);
		ZAssert.assertNotNull(tag, "Verify the tag was created");

		// Create a subfolder
		String foldername = "folder" + ZmailSeleniumProperties.getUniqueString();
		ZmailAccount.AccountZWC().soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
				+		"<folder name='"+ foldername +"' l='1' view='appointment'/>"
				+	"</CreateFolderRequest>");

		FolderItem folder = FolderItem.importFromSOAP(ZmailAccount.AccountZWC(), foldername);
		ZAssert.assertNotNull(folder, "Verify the subfolder is available");

		// Create the list of actions
		ArrayList<QuickCommandAction> actions = new ArrayList<QuickCommandAction>();
		actions.add(new QuickCommandAction(QuickCommandAction.TypeId.actionTag, tag.getId(), true));
		actions.add(new QuickCommandAction(QuickCommandAction.TypeId.actionFlag, "read", true));
		actions.add(new QuickCommandAction(QuickCommandAction.TypeId.actionFlag, "flagged", true));
		actions.add(new QuickCommandAction(QuickCommandAction.TypeId.actionFileInto, folder.getId(), true));


		String name = "name" + ZmailSeleniumProperties.getUniqueString();
		String description = "description" + ZmailSeleniumProperties.getUniqueString();
		
		command3 = new QuickCommand(name, description, ItemTypeId.APPT, true);
		command3.addActions(actions);

		return (command3);
	}
	

	/**
	 * Set up basic quick commands for all combinations 
	 * @throws HarnessException
	 */
	@Bugs(ids = "71389")	// Hold off on GUI implementation of Quick Commands in 8.X
//	@BeforeClass( groups = { "always" } )
	public void addQuickCommands() throws HarnessException {
		logger.info("addQuickCommands: start");
		

		// Create a quick command in the user preferences
		ZmailAccount.AccountZWC().soapSend(
				"<ModifyPrefsRequest xmlns='urn:zmailAccount'>"
				+		"<pref name='zmailPrefQuickCommand'>"+ this.getQuickCommand01().toString() +"</pref>"
				+		"<pref name='zmailPrefQuickCommand'>"+ this.getQuickCommand02().toString() +"</pref>"
				+		"<pref name='zmailPrefQuickCommand'>"+ this.getQuickCommand03().toString() +"</pref>"
				+	"</ModifyPrefsRequest>");

		
		// Re-login to pick up the new preferences
		super.startingPage.zRefresh();

		// The AjaxCommonTest.commonTestBeforeMethod() method will log into the client
		
		logger.info("addQuickCommands: finish");


	}


}
