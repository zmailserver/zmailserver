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
package org.zmail.qa.selenium.projects.desktop.tests.briefcase.document;

import org.testng.annotations.Test;
import org.zmail.qa.selenium.framework.items.DocumentItem;
import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.XmlStringUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.desktop.core.AjaxCommonTest;
import org.zmail.qa.selenium.projects.desktop.ui.briefcase.DocumentBriefcaseEdit;
import org.zmail.qa.selenium.projects.desktop.ui.briefcase.DocumentBriefcaseOpen;
import org.zmail.qa.selenium.projects.desktop.ui.briefcase.PageBriefcase;

public class EditDocument extends AjaxCommonTest {

	public EditDocument() {
		logger.info("New " + EditDocument.class.getCanonicalName());

		super.startingPage = app.zPageBriefcase;

		super.startingAccountPreferences = null;
	}

	@Test(description = "Create document through SOAP - edit name & verify through GUI", groups = { "smoke" })
	public void EditDocument_01() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create document item
		DocumentItem docItem1 = new DocumentItem();
		DocumentItem docItem2 = new DocumentItem();

		// Create document using SOAP
		String contentHTML = XmlStringUtil.escapeXml("<html>" + "<body>"
				+ docItem1.getDocText() + "</body>" + "</html>");

		account
				.soapSend("<SaveDocumentRequest requestId='0' xmlns='urn:zmailMail'>"
						+ "<doc name='"
						+ docItem1.getName()
						+ "' l='"
						+ briefcaseFolder.getId()
						+ "' ct='application/x-zmail-doc'>"
						+ "<content>"
						+ contentHTML
						+ "</content>"
						+ "</doc>"
						+ "</SaveDocumentRequest>");

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on created document
		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, docItem1);

		// Click on Edit document icon in toolbar
		DocumentBriefcaseEdit documentBriefcaseEdit = (DocumentBriefcaseEdit) app.zPageBriefcase
				.zToolbarPressButton(Button.B_EDIT_FILE, docItem1);

		app.zPageBriefcase.isEditDocLoaded(docItem1);

		// Select edit document window
		try {
			app.zPageBriefcase.zSelectWindow(docItem1.getName());

			// Fill out the document with the new data
			documentBriefcaseEdit.typeDocumentName(docItem2.getName());

			// Save and close
			documentBriefcaseEdit.zSubmit();
		} catch (Exception ex) {
			throw new HarnessException("error in editing document " + docItem1.getName(),
					ex);
		} finally {
			app.zPageBriefcase.zSelectWindow(PageBriefcase.pageTitle);
		}

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Verify document was saved with new data
		// String name = app.zPageBriefcase.getText(docName2);
		// ZAssert.assertStringContains(name, docName2,

		// "Verify document name through GUI");
		ZAssert.assertTrue(app.zPageBriefcase
				.waitForPresentInListView(docItem2.getName()),
				"Verify document name through GUI");

		// delete file upon test completion
		app.zPageBriefcase.deleteFileByName(docItem2.getName());

		/*
		 * //name =ClientSessionFactory.session().selenium().getText(
		 * "css=div[id='zl__BDLV__rows'][class='DwtListView-Rows'] td[width*='auto'] div[id^=zlif__BDLV__]"
		 * );//ClientSessionFactory.session().selenium().isElementPresent(
		 * "css=div[id='zl__BDLV__rows'][class='DwtListView-Rows'] td[width*='auto']>div:contains[id*='zlif__BDLV__']"
		 * );//ClientSessionFactory.session().selenium().isElementPresent(
		 * "css=div[id='zl__BDLV__rows'][class='DwtListView-Rows'] div:contains('name')"
		 * );
		 */
	}

	@Test(description = "Create document through SOAP - edit text & name & verify through GUI", groups = { "smoke" })
	public void EditDocument_02() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create document item
		DocumentItem docItem1 = new DocumentItem();
		DocumentItem docItem2 = new DocumentItem();

    	// Create document using SOAP
		String contentHTML = XmlStringUtil.escapeXml("<html>" + "<body>"
				+ docItem1.getDocText() + "</body>" + "</html>");

		account
				.soapSend("<SaveDocumentRequest requestId='0' xmlns='urn:zmailMail'>"
						+ "<doc name='"
						+ docItem1.getName()
						+ "' l='"
						+ briefcaseFolder.getId()
						+ "' ct='application/x-zmail-doc'>"
						+ "<content>"
						+ contentHTML
						+ "</content>"
						+ "</doc>"
						+ "</SaveDocumentRequest>");

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on created document
		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, docItem1);

		// Click on Edit document icon in toolbar
		DocumentBriefcaseEdit documentBriefcaseEdit = (DocumentBriefcaseEdit) app.zPageBriefcase
				.zToolbarPressButton(Button.B_EDIT_FILE, docItem1);

		app.zPageBriefcase.isEditDocLoaded(docItem1);

		// Select document window opened for editing
		try {
			app.zPageBriefcase.zSelectWindow(docItem1.getName());

			// Type new document text
			documentBriefcaseEdit.typeDocumentText(docItem2.getDocText());

			// Type new document name
			documentBriefcaseEdit.typeDocumentName(docItem2.getName());

			// Save and close
			documentBriefcaseEdit.zSubmit();
		} catch (Exception ex) {
			throw new HarnessException("error in editing document " + docItem1.getName(),
					ex);
		} finally {
			app.zPageBriefcase.zSelectWindow(PageBriefcase.pageTitle);
		}

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on modified document
		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, docItem2);

		// Click on open in a separate window icon in toolbar
		DocumentBriefcaseOpen documentBriefcaseOpen = (DocumentBriefcaseOpen) app.zPageBriefcase
				.zToolbarPressButton(Button.B_OPEN_IN_SEPARATE_WINDOW, docItem2);

		app.zPageBriefcase.isOpenDocLoaded(docItem2);

		String name = "";
		String text = "";

		// Select document opened in a separate window
		try {
			app.zPageBriefcase.zSelectWindow(docItem2.getName());

			name = documentBriefcaseOpen.retriveDocumentName();
			text = documentBriefcaseOpen.retriveDocumentText();

			// close
			app.zPageBriefcase.closeWindow();
		} finally {
			app.zPageBriefcase.zSelectWindow(PageBriefcase.pageTitle);
		}

		ZAssert.assertStringContains(name, docItem2.getName(),
				"Verify document name through GUI");

		ZAssert.assertStringContains(text, docItem2.getDocText(),
				"Verify document text through GUI");

		// delete file upon test completion
		app.zPageBriefcase.deleteFileByName(docItem2.getName());
	}

	@Test(description = "Create document & edit text through SOAP & verify through GUI", groups = { "smoke" })
	public void EditDocument_03() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create document item
		DocumentItem docItem = new DocumentItem();

		// Create document using SOAP
		String contentHTML = XmlStringUtil.escapeXml("<html>" + "<body>"
				+ docItem.getDocText() + "</body>" + "</html>");

		account
				.soapSend("<SaveDocumentRequest requestId='0' xmlns='urn:zmailMail'>"
						+ "<doc name='"
						+ docItem.getName()
						+ "' l='"
						+ briefcaseFolder.getId()
						+ "' ct='application/x-zmail-doc'>"
						+ "<content>"
						+ contentHTML
						+ "</content>"
						+ "</doc>"
						+ "</SaveDocumentRequest>");

		// TODO: Bug 59439
		// Search for created document
		//account
		//		.soapSend("<SearchRequest xmlns='urn:zmailMail' types='document'>"
		//				+ "<query>" + docItem.getName() + "</query>" + "</SearchRequest>");

		String docId = account.soapSelectValue("//mail:doc", "id");
		String version = account.soapSelectValue("//mail:doc", "ver");
		
		docItem.setDocText("editText" + ZmailSeleniumProperties.getUniqueString());

		// Edit document through SOAP
		account
				.soapSend("<SaveDocumentRequest requestId='0' xmlns='urn:zmailMail'>"
						+ "<doc name='"
						+ docItem.getName()
						+ "' l='"
						+ briefcaseFolder.getId()
						+ "' ver='"
						+ version
						+ "' id='"
						+ docId
						+ "' ct='application/x-zmail-doc'>"
						+ "<content>&lt;html>&lt;body>"
						+ docItem.getDocText()
						+ "&lt;/body>&lt;/html></content>"
						+ "</doc>"
						+ "</SaveDocumentRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on modified document
		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, docItem);

		// Click on open in a separate window icon in toolbar
		DocumentBriefcaseOpen documentBriefcaseOpen = (DocumentBriefcaseOpen) app.zPageBriefcase
				.zToolbarPressButton(Button.B_OPEN_IN_SEPARATE_WINDOW, docItem);

		app.zPageBriefcase.isOpenDocLoaded(docItem);

		String text = "";

		// Select document opened in a separate window
		try {
			app.zPageBriefcase.zSelectWindow(docItem.getName());

			text = documentBriefcaseOpen.retriveDocumentText();

			// close
			app.zPageBriefcase.closeWindow();
		} finally {
			app.zPageBriefcase.zSelectWindow(PageBriefcase.pageTitle);
		}

		ZAssert.assertStringContains(text, docItem.getDocText(),
				"Verify document text through GUI");

		// delete file upon test completion
		app.zPageBriefcase.deleteFileByName(docItem.getName());
	}

	@Test(description = "Create document through SOAP - edit text & verify through GUI", groups = { "smoke" })
	public void EditDocument_04() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create document item
		DocumentItem docItem = new DocumentItem();

		// Create document using SOAP
		String contentHTML = XmlStringUtil.escapeXml("<html>" + "<body>"
				+ docItem.getDocText() + "</body>" + "</html>");

		account
				.soapSend("<SaveDocumentRequest requestId='0' xmlns='urn:zmailMail'>"
						+ "<doc name='"
						+ docItem.getName()
						+ "' l='"
						+ briefcaseFolder.getId()
						+ "' ct='application/x-zmail-doc'>"
						+ "<content>"
						+ contentHTML
						+ "</content>"
						+ "</doc>"
						+ "</SaveDocumentRequest>");

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on created document
		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, docItem);

		// Click on Edit document icon in toolbar
		DocumentBriefcaseEdit documentBriefcaseEdit = (DocumentBriefcaseEdit) app.zPageBriefcase
				.zToolbarPressButton(Button.B_EDIT_FILE, docItem);

		app.zPageBriefcase.isEditDocLoaded(docItem);

		String editText="";
		
		// Select document window opened for editing
		try {
			app.zPageBriefcase.zSelectWindow(docItem.getName());

			editText = "editText" + ZmailSeleniumProperties.getUniqueString();
			
			// Fill out the document with the new data
			documentBriefcaseEdit.typeDocumentText(editText);

			// Save and close
			documentBriefcaseEdit.zSubmit();
		} catch (Exception ex) {
			throw new HarnessException("error in editing document " + docItem.getName(),
					ex);
		} finally {
			app.zPageBriefcase.zSelectWindow(PageBriefcase.pageTitle);
		}
		
		docItem.setDocText(editText);
		
		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on modified document
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, docItem);

		// Click on open in a separate window icon in toolbar
		DocumentBriefcaseOpen documentBriefcaseOpen = (DocumentBriefcaseOpen) app.zPageBriefcase
				.zToolbarPressButton(Button.B_OPEN_IN_SEPARATE_WINDOW, docItem);

		app.zPageBriefcase.isOpenDocLoaded(docItem);

		String text = "";

		// Select document opened in a separate window
		try {
			app.zPageBriefcase.zSelectWindow(docItem.getName());

			text = documentBriefcaseOpen.retriveDocumentText();

			// close
			app.zPageBriefcase.zSelectWindow(docItem.getName());

			app.zPageBriefcase.closeWindow();
		} finally {
			app.zPageBriefcase.zSelectWindow(PageBriefcase.pageTitle);
		}

		ZAssert.assertStringContains(text, docItem.getDocText(),
				"Verify document text through GUI");

		// delete file upon test completion
		app.zPageBriefcase.deleteFileByName(docItem.getName());
	}

	@Test(description = "Create document through SOAP - Edit Document using Right Click Context Menu & verify through GUI", groups = { "functional" })
	public void EditDocument_05() throws HarnessException {
		ZmailAccount account = app.zGetActiveAccount();

		FolderItem briefcaseFolder = FolderItem.importFromSOAP(account,
				SystemFolder.Briefcase);

		// Create document item
		DocumentItem docItem = new DocumentItem();

		// Create document using SOAP
		String contentHTML = XmlStringUtil.escapeXml("<html>" + "<body>"
				+ docItem.getDocText() + "</body>" + "</html>");

		account
				.soapSend("<SaveDocumentRequest requestId='0' xmlns='urn:zmailMail'>"
						+ "<doc name='"
						+ docItem.getName()
						+ "' l='"
						+ briefcaseFolder.getId()
						+ "' ct='application/x-zmail-doc'>"
						+ "<content>"
						+ contentHTML
						+ "</content>"
						+ "</doc>"
						+ "</SaveDocumentRequest>");

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageBriefcase.zWaitForDesktopLoadingSpinner(5000);

		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Click on created document
		app.zPageBriefcase.zListItem(Action.A_LEFTCLICK, docItem);

		// Edit Document using Right Click Context Menu
		DocumentBriefcaseEdit documentBriefcaseEdit = (DocumentBriefcaseEdit) app.zPageBriefcase
				.zListItem(Action.A_RIGHTCLICK, Button.O_EDIT, docItem);

		// app.zPageBriefcase.isEditDocLoaded(docName, docText);

		String editDocName = "";
		
		// Select edit document window
		try {
			app.zPageBriefcase.zSelectWindow(docItem.getName());

			// Fill out the document with the new data
			editDocName = "editDocName" + ZmailSeleniumProperties.getUniqueString();
			
			documentBriefcaseEdit.typeDocumentName(editDocName);

			// Save and close
			documentBriefcaseEdit.zSubmit();
		} catch (Exception ex) {
			throw new HarnessException("error in editing document " + docItem.getName(),
					ex);
		} finally {
			app.zPageBriefcase.zSelectWindow(PageBriefcase.pageTitle);
		}

		docItem.setDocName(editDocName);
		
		// refresh briefcase page
		app.zTreeBriefcase.zTreeItem(Action.A_LEFTCLICK, briefcaseFolder, true);

		// Verify document was saved with new data
		// String name = app.zPageBriefcase.getText(docName);
		// ZAssert.assertStringContains(name, docName,

		// "Verify document name through GUI");
		boolean present = app.zPageBriefcase.waitForPresentInListView(docItem.getName());

		ZAssert.assertTrue(present, "Verify document name through GUI");

		// delete file upon test completion
		app.zPageBriefcase.deleteFileByName(docItem.getName());

		/*
		 * //name =ClientSessionFactory.session().selenium().getText(
		 * "css=div[id='zl__BDLV__rows'][class='DwtListView-Rows'] td[width*='auto'] div[id^=zlif__BDLV__]"
		 * );//ClientSessionFactory.session().selenium().isElementPresent(
		 * "css=div[id='zl__BDLV__rows'][class='DwtListView-Rows'] td[width*='auto']>div:contains[id*='zlif__BDLV__']"
		 * );//ClientSessionFactory.session().selenium().isElementPresent(
		 * "css=div[id='zl__BDLV__rows'][class='DwtListView-Rows'] div:contains('name')"
		 * );
		 */
	}
}
