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
package org.zmail.qa.selenium.projects.desktop.tests.briefcase.file;

import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.DialogTag;

public class TagFile extends AjaxCommonTest {

	public TagFile() {
		logger.info("New " + TagFile.class.getCanonicalName());

		// All tests start at the Briefcase page
		super.startingPage = app.zPageBriefcase;

		super.startingAccountPreferences = null;
	}

	@Test(description = "Tag a File using Toolbar -> Tag -> New Tag", groups = { "smoke" })
	public void TagFile_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
		+ "/data/public/other/testpptfile.ppt";
		
		FileItem fileItem = new FileItem(filePath);

		String fileName = fileItem.getName();

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+ "<doc l='" + briefcaseFolder.getId() + "'><upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on created document
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);

		// Create a tag using GUI
		String tagName = "tag" + ZmailSeleniumProperties.getUniqueString();

		// Click on New Tag
		DialogTag dialogTag = (DialogTag) app.zPageBriefcase
				.zToolbarPressPulldown(Button.B_TAG, Button.O_TAG_NEWTAG);

		dialogTag.zSetTagName(tagName);
		dialogTag.zClickButton(Button.B_OK);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

		// Make sure the tag was created on the server (get the tag ID)
		account.soapSend("<GetTagRequest xmlns='urn:zmailMail'/>");

		String tagId = account.soapSelectValue(
				"//mail:GetTagResponse//mail:tag[@name='" + tagName + "']",
				"id");

		// Verify tagged document name
		account
				.soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
						+ "<query>tag:"
						+ tagName
						+ "</query>"
						+ "</SearchRequest>");

		String name = account.soapSelectValue(
				"//mail:SearchResponse//mail:doc", "name");

		ZAssert.assertEquals(name, fileName, "Verify tagged document name");

		// Make sure the tag was applied to the document
		// account.soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
		// + "<query>in:briefcase</query></SearchRequest>");

		// String id = account.soapSelectValue(
		// "//mail:SearchResponse//mail:doc[@name='" + docName + "']", "t");

		account
				.soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
						+ "<query>"
						+ fileName
						+ "</query>"
						+ "</SearchRequest>");

		String id = account.soapSelectValue("//mail:SearchResponse//mail:doc",
				"t");

		ZAssert.assertEquals(id, tagId,
				"Verify the tag was attached to the document");

		//delete file upon test completion
		app.zPageBriefcase.deleteFileByName(fileName);
		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);
	}

	@Test(description = "Tag uploaded File using pre-existing Tag", groups = { "functional" })
	public void TagFile_02() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
		+ "/data/public/other/testpptfile.ppt";
		
		FileItem fileItem = new FileItem(filePath);

		String fileName = fileItem.getName();

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+ "<doc l='" + briefcaseFolder.getId() + "'><upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		// Create a tag
		String tagName = "tag" + ZmailSeleniumProperties.getUniqueString();

		account.soapSend("<CreateTagRequest xmlns='urn:zmailMail'>"
				+ "<tag name='" + tagName + "' color='1' />"
				+ "</CreateTagRequest>");

		// Make sure the tag was created on the server
		TagItem tag = TagItem.importFromSOAP(app.zGetActiveAccount(), tagName);
		ZAssert.assertNotNull(tag, "Verify the new tag was created");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on uploaded file
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);

		// Tag file selecting pre-existing tag from Toolbar drop down list
		app.zPageBriefcase.zToolbarPressPulldown(Button.B_TAG, tag.getName());

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // Make sure the tag was applied to the document
		account
				.soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
						+ "<query>" + fileName + "</query>" + "</SearchRequest>");

		String id = account.soapSelectValue("//mail:SearchResponse//mail:doc",
				"t");

		ZAssert.assertStringContains(id, tag.getId(),
				"Verify the tag was attached to the document");
		
		//delete file upon test completion
		app.zPageBriefcase.deleteFileByName(fileName);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);
	}

	@Test(description = "Tag uploaded File using Right Click context menu", groups = { "functional" })
	public void TagFile_03() throws HarnessException {
	   ZmailAccount account = app.zGetActiveAccount();

	   FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
	         SystemFolder.Briefcase);

	   // Create file item
	   String filePath = ZmailSeleniumProperties.getBaseDirectory()
	         + "/data/public/other/testpptfile.ppt";

	   FileItem fileItem = new FileItem(filePath);

	   String fileName = fileItem.getName();

	   // Upload file to server through RestUtil
	   String attachmentId = account.uploadFile(filePath);

	   // Save uploaded file to briefcase through SOAP
	   account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
	         + "<doc l='" + briefcaseFolder.getId() + "'><upload id='"
	         + attachmentId + "'/></doc></SaveDocumentRequest>");

	   // Create a tag
	   String tagName = "tag" + ZmailSeleniumProperties.getUniqueString();

	   account.soapSend("<CreateTagRequest xmlns='urn:zmailMail'>"
	         + "<tag name='" + tagName + "' color='1' />"
	         + "</CreateTagRequest>");

	   // Make sure the tag was created on the server
	   TagItem tagItem = TagItem.importFromSOAP(app.zGetActiveAccount(),
	         tagName);

	   ZAssert.assertNotNull(tagItem, "Verify the new tag was created");

	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // refresh briefcase page
	   app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

	   SleepUtil.sleepVerySmall();

	   // Click on uploaded file
	   app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);

	   // Click on header check box
	   // app.zPageBriefcase.zHeader(Action.A_BRIEFCASE_HEADER_CHECKBOX);

	   // Tag File using Right Click context menu
	   app.zPageBriefcase.zListItem(Action.A_RIGHTCLICK, Button.O_TAG_FILE,
	         tagItem.getName(), fileItem);

	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

      // Make sure the tag was applied to the File
	   account
	         .soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
	               + "<query>"
	               + fileName
	               + "</query>"
	               + "</SearchRequest>");

	   String id = account.soapSelectValue("//mail:SearchResponse//mail:doc",
	         "t");

	   ZAssert.assertNotNull(id,
	         "Verify the search response returns the document tag id");

	   ZAssert.assertStringContains(id, tagItem.getId(),
	         "Verify the tag was attached to the File");

	   // delete file upon test completion
	   app.zPageBriefcase.deleteFileByName(fileName);
	   GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);
	}

}
