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
package org.zmail.qa.selenium.projects.ajax.tests.briefcase.file;

import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.*;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.core.FeatureBriefcaseTest;

public class UnTagFile extends FeatureBriefcaseTest {

	public UnTagFile() throws HarnessException {
		logger.info("New " + UnTagFile.class.getCanonicalName());

		// All tests start at the Briefcase page
		super.startingPage = app.zPageBriefcase;

		if(ZmailSeleniumProperties.zmailGetVersionString().contains("FOSS")){
		    super.startingAccountPreferences.put("zmailPrefShowSelectionCheckbox","TRUE");
		}
			
		super.startingAccountPreferences.put("zmailPrefBriefcaseReadingPaneLocation", "bottom");
	}

	@Test(description = "Remove a tag from a File using Toolbar -> Tag -> Remove Tag", groups = { "smoke" })
	public void UnTagFile_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/org_zmail_ymaps.zip";

		FileItem fileItem = new FileItem(filePath);

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// Save uploaded file to briefcase through SOAP
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+ "<doc l='" + briefcaseFolder.getId() + "'><upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");

		/*
		 * String docId =
		 * account.soapSelectValue("//mail:SaveDocumentResponse//mail:doc"
		 * ,"id");
		 * 
		 * // Search for created documentaccount.soapSend(
		 * "<SearchRequest xmlns='urn:zmailMail' types='document'>" +
		 * "<query>in:" + briefcaseFolder.getName() +
		 * "</query></SearchRequest>");
		 * 
		 * String docId = account.soapSelectValue(
		 * "//mail:SearchResponse//mail:doc[@name='" + docName + "']", "id");
		 * String version = account.soapSelectValue(
		 * "//mail:SearchResponse//mail:doc[@name='" + docName + "']", "ver");
		 * 
		 * account.soapSend(
		 * "<SearchRequest xmlns='urn:zmailMail' types='document'>" + "<query>"
		 * + docName + "</query>" + "</SearchRequest>");
		 * 
		 * docId = account.soapSelectValue("//mail:doc", "id"); version =
		 * account.soapSelectValue("//mail:doc", "ver");
		 */

		// Create a tag
		String tagName = "tag" + ZmailSeleniumProperties.getUniqueString();

		/*
		 * //this flow needs page reload account.soapSend(
		 * "<CreateTagRequest xmlns='urn:zmailMail'>" + "<tag name='"+ tagName
		 * +"' color='1' />" + "</CreateTagRequest>");
		 * 
		 * String tagId =
		 * account.soapSelectValue("//mail:CreateTagResponse/mail:tag", "id");
		 * 
		 * account.soapSend( "<ItemActionRequest xmlns='urn:zmailMail'>" +
		 * "<action id='"+ docId +"' op='tag' tag='" + tagId + "'/>" +
		 * "</ItemActionRequest>");
		 * 
		 * //ClientSessionFactory.session().selenium().refresh();
		 */
		/*
		 * // this flow is using tag pull down menu // refresh briefcase page
		 * app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder,
		 * true);
		 * 
		 * SleepUtil.sleepVerySmall();
		 * 
		 * // Click on created document
		 * GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		 * app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, docItem);
		 * 
		 * // Click on New Tag DialogTag dialogTag = (DialogTag)
		 * app.zPageBriefcase .zToolbarPressPulldown(Button.B_TAG,
		 * Button.O_TAG_NEWTAG, null);
		 * 
		 * dialogTag.zSetTagName(tagName); dialogTag.zClickButton(Button.B_OK);
		 */
		
		account.soapSend("<CreateTagRequest xmlns='urn:zmailMail'>"
				+ "<tag name='" + tagName + "' color='1' />"
				+ "</CreateTagRequest>");

		// Make sure the tag was created on the server
		// account.soapSend("<GetTagRequest xmlns='urn:zmailMail'/>");
		// String tagId = account.soapSelectValue(
		//		"//mail:GetTagResponse//mail:tag[@name='" + tagName + "']",
		//		"id");

		TagItem tagItem = TagItem.importFromSOAP(app.zGetActiveAccount(),
				tagName);
		
		ZAssert.assertNotNull(tagItem, "Verify the new tag was created");

		String tagId = tagItem.getId();
		
		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		SleepUtil.sleepVerySmall();

		// Click on created file
		if(ZmailSeleniumProperties.zmailGetVersionString().contains(
    			"FOSS")){
		    app.zPageBriefcase.zListItem(Action.A_BRIEFCASE_CHECKBOX, fileItem);

		}else{
		    app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);
		}

		// Tag document using Right Click context menu
		app.zPageBriefcase.zListItem(Action.A_RIGHTCLICK, Button.O_TAG_FILE,
				tagItem.getName(), fileItem);


		// Make sure the tag was applied to the document
		account
				.soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
						+ "<query>"
						+ fileItem.getName()
						+ "</query>"
						+ "</SearchRequest>");

		String id = account.soapSelectValue("//mail:SearchResponse//mail:doc",
				"t");

		ZAssert.assertNotNull(id,
				"Verify the search response returns the document tag id");

		ZAssert.assertEquals(id, tagId,
				"Verify the tag was attached to the document");

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		SleepUtil.sleepVerySmall();

		// Click on tagged file
		if(ZmailSeleniumProperties.zmailGetVersionString().contains(
    			"FOSS")){
		    app.zPageBriefcase.zListItem(Action.A_BRIEFCASE_CHECKBOX, fileItem);

		}else{
		    app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, fileItem);
		}

		// Click Remove Tag
		app.zPageBriefcase.zToolbarPressPulldown(Button.B_TAG,
				Button.O_TAG_REMOVETAG, null);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());

		account
				.soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
						+ "<query>"
						+ fileItem.getName()
						+ "</query>"
						+ "</SearchRequest>");

		id = account.soapSelectValue("//mail:SearchResponse//mail:doc", "t");

		ZAssert.assertStringDoesNotContain(id, tagId,
				"Verify that the tag is removed from the message");

		// delete file upon test completion
		app.zPageBriefcase.deleteFileByName(fileItem.getName());
	}
}
