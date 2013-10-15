/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.qa.selenium.projects.octopus.tests.myfiles.comments;

import java.util.List;

import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.items.CommentItem;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.octopus.core.OctopusCommonTest;
import org.zmail.qa.selenium.projects.octopus.ui.DisplayFileComments;
import org.zmail.qa.selenium.projects.octopus.ui.DisplayFilePreview;
import org.zmail.qa.selenium.projects.octopus.ui.PageMyFiles;

public class DeleteComment extends OctopusCommonTest {


	public DeleteComment() {
		logger.info("New " + DeleteComment.class.getCanonicalName());

		// test starts at the My Files tab
		super.startingPage = app.zPageMyFiles;
		super.startingAccountPreferences = null;
	}

	@Test(
			description = "Delete a comment on a file",
			groups = { "smoke" })
	public void DeleteComment_01() throws HarnessException {

		String filename = "filename"+ ZmailSeleniumProperties.getUniqueString() +".txt";
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/documents/doc01/plaintext.txt";
	
		String commentText = "Comment" + ZmailSeleniumProperties.getUniqueString();


		// Upload file to server through RestUtil
		String attachmentId = app.zGetActiveAccount().uploadFile(filePath);

		// Save uploaded file through SOAP
		FolderItem briefcaseRootFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Briefcase);

		app.zGetActiveAccount().soapSend(
					"<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+		"<doc name='"+ filename +"' l='" + briefcaseRootFolder.getId() + "'>"
				+			"<upload id='" + attachmentId + "'/>"
				+		"</doc>"
				+	"</SaveDocumentRequest>");
		String documentId = app.zGetActiveAccount().soapSelectValue("//mail:doc", "id");


		
		// Sync up
//		app.zPageOctopus.zToolbarPressButton(Button.B_GETMAIL);


		// Add comments to the file using SOAP
		app.zGetActiveAccount().soapSend(
					"<AddCommentRequest xmlns='urn:zmailMail'>"
				+		"<comment parentId='"+ documentId + "' text='" + commentText + "'/>"
				+	"</AddCommentRequest>");
		String id = app.zGetActiveAccount().soapSelectValue("//mail:comment", "id");


		// Get file comments through SOAP
		app.zGetActiveAccount().soapSend(
					"<GetCommentsRequest  xmlns='urn:zmailMail'>"
				+		"<comment parentId='"+ documentId + "'/>"
				+	"</GetCommentsRequest>");


		
		
		// Click on My Files tab
		app.zPageOctopus.zToolbarPressButton(Button.B_TAB_MY_FILES);

		// Verify file exists in My Files view
		ZAssert.assertTrue(app.zPageMyFiles.zWaitForElementPresent(
				PageMyFiles.Locators.zMyFilesListViewItems.locator
						+ ":contains(" + filename + ")", "3000"),
				"Verify file appears in My Files view");

		// Select file in the list view
		DisplayFilePreview filePreview = (DisplayFilePreview) app.zPageMyFiles.zListItem(Action.A_LEFTCLICK, filename);

		DisplayFileComments fileComments = null;
		
		try {

			// Click on Comments button
			fileComments = (DisplayFileComments) filePreview.zPressButton(Button.B_COMMENTS);
			
			CommentItem found = null;
			List<CommentItem> comments = fileComments.zGetCommentsList();
			for ( CommentItem comment : comments ) {
				
				// Verify the comment is found
				if (comment.getCommentText().equals(commentText)) {
					found = comment;
					break;
				}
				
			}
			
			ZAssert.assertNotNull(found, "Verify the commment is found");
			
			// Delete the comment by clicking on the 'x'
			fileComments.zDeleteComment(found);
			found = null;
			
			
			// Get file comments through SOAP
			app.zGetActiveAccount().soapSend(
						"<GetCommentsRequest  xmlns='urn:zmailMail'>"
					+		"<comment parentId='"+ documentId + "'/>"
					+	"</GetCommentsRequest>");
			Element[] nodes = app.zGetActiveAccount().soapSelectNodes("//mail:comment[id='"+ id +"']");
			ZAssert.assertEquals(nodes.length, 0, "Verify the comment element node is no longer present");

		
		} finally {
			if ( fileComments != null ) {
				
				// close Comments view
				fileComments.zPressButton(Button.B_CLOSE);
				fileComments = null;

			}
		}

	}

}
