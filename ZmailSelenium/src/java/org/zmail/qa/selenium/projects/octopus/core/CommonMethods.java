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
package org.zmail.qa.selenium.projects.octopus.core;

import java.util.ArrayList;

import org.zmail.qa.selenium.framework.items.FolderItem;
import org.zmail.qa.selenium.framework.items.FolderItem.SystemFolder;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.OctopusAccount;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;

public class CommonMethods {

	public CommonMethods() {}

	// revoke sharing a folder via soap
	protected void revokeShareFolderViaSoap(ZmailAccount account, ZmailAccount grantee, FolderItem folder) throws HarnessException {

		account.soapSend("<FolderActionRequest xmlns='urn:zmailMail'>"
				+ "<action id='" + folder.getId()
				+ "' op='!grant' zid='" +  grantee.ZmailId +"'" + ">"
				+ "</action>"
				+ "</FolderActionRequest>");

	}
	// create mountpoint via soap
	protected void mountFolderViaSoap(ZmailAccount account, ZmailAccount grantee, FolderItem folder,
			String permission, FolderItem mountPointFolder, String mountPointName) throws HarnessException {

		account.soapSend("<FolderActionRequest xmlns='urn:zmailMail'>"
				+ "<action id='" + folder.getId()
				+ "' op='grant'>" + "<grant d='"
				+ grantee.EmailAddress + "' gt='usr' perm='" + permission + "'/>"
				+ "</action>" + "</FolderActionRequest>");

		grantee.soapSend("<CreateMountpointRequest xmlns='urn:zmailMail'>"
				+ "<link l='" + mountPointFolder.getId()
				+ "' name='" + mountPointName
				+ "' view='document' rid='" + folder.getId()
				+ "' zid='" + account.ZmailId + "'/>"
				+ "</CreateMountpointRequest>");
	}
	// share a folder via soap
	protected void shareFolderViaSoap(ZmailAccount account, ZmailAccount grantee, FolderItem folder,
			String permission) throws HarnessException {

		account.soapSend("<FolderActionRequest xmlns='urn:zmailMail'>"
				+ "<action id='" + folder.getId()
				+ "' op='grant'>" + "<grant d='"
				+ grantee.EmailAddress + "' gt='usr' perm='" + permission + "'/>"
				+ "</action>" + "</FolderActionRequest>");

		account.soapSend("<SendShareNotificationRequest xmlns='urn:zmailMail'>"
				+ "<item id='"
				+ folder.getId()
				+ "'/>"
				+ "<e a='"
				+ grantee.EmailAddress
				+ "'/>"
				+ "<notes _content='You are invited to view my shared folder " + folder.getName() + " '/>"
				+ "</SendShareNotificationRequest>");
	}
	// create a new folder via soap
	protected FolderItem createFolderViaSoap(ZmailAccount account, FolderItem ...folderItemArray) throws HarnessException {

		FolderItem folderItem = FolderItem.importFromSOAP(account, SystemFolder.Briefcase);

		if ((folderItemArray != null) && folderItemArray.length >0) {
			folderItem = folderItemArray[0];
		}

		// generate folder name
		String foldername = "Folder " + ZmailSeleniumProperties.getUniqueString();

		// send soap request
		account.soapSend("<CreateFolderRequest xmlns='urn:zmailMail'>"
				+ "<folder name='" + foldername + "' l='"
				+ folderItem.getId()
				+ "' view='document'/>" + "</CreateFolderRequest>");

		// verify folder creation on the server
		return FolderItem.importFromSOAP(account, foldername);
	}
	// Create a new folder, pass the required folder name
	protected FolderItem createFolderViaSoap(ZmailAccount account, String folderName, FolderItem ...folderItemArray) throws HarnessException
	{
		FolderItem folderItem = FolderItem.importFromSOAP(account, SystemFolder.Briefcase);

		if ((folderItemArray != null) && folderItemArray.length >0) {
			folderItem = folderItemArray[0];
		}

		// send soap request
		account.soapSend("<CreateFolderRequest xmlns='urn:zmailMail'>"
				+ "<folder name='" + folderName + "' l='"
				+ folderItem.getId()
				+ "' view='document'/>" + "</CreateFolderRequest>");

		// verify folder creation on the server
		return FolderItem.importFromSOAP(account, folderName);
	}
	// create a new zmail account
	protected OctopusAccount getNewAccount() {
		OctopusAccount newAccount = new OctopusAccount();
		newAccount.provision();
		newAccount.authenticate();
		return newAccount;
	}
	// return comment id
	protected String makeCommentViaSoap(ZmailAccount account, String fileId, String comment)
			throws HarnessException {
		// Add comments to the file using SOAP
		account.soapSend("<AddCommentRequest xmlns='urn:zmailMail'> <comment parentId='"
				+ fileId + "' text='" + comment + "'/></AddCommentRequest>");

		SleepUtil.sleepVerySmall();

		//TODO: verify valid id?
		return account.soapSelectValue("//mail:AddCommentResponse//mail:comment", "id");
	}
	//Rename a file via Soap
	protected String renameViaSoap(ZmailAccount account, String fileId, String newName)
			throws HarnessException {
		// Rename file using SOAP
		account.soapSend("<ItemActionRequest xmlns='urn:zmailMail'> <action id='"
				+ fileId + "' name='" + newName + "' op='rename' /></ItemActionRequest>");

		SleepUtil.sleepVerySmall();

		//verification
		ZAssert.assertTrue(account.soapMatch(
				"//mail:ItemActionResponse//mail:action", "op", "rename"),
				"Verify file is renamed to " + newName);

		return newName;
	}
	// Mark file favorite using soap
	protected void markFileFavoriteViaSoap(ZmailAccount account, String fileId)
			throws HarnessException {
		account.soapSend("<DocumentActionRequest xmlns='urn:zmailMail'>"
				+ "<action id='" + fileId + "'  op='watch' /></DocumentActionRequest>");

		SleepUtil.sleepVerySmall();

		//verification
		ZAssert.assertTrue(account.soapMatch(
				"//mail:DocumentActionResponse//mail:action", "op", "watch"),
				"Verify file is marked as favorite");

	}
	// Unmark file favorite using soap
	protected void unMarkFileFavoriteViaSoap(ZmailAccount account, String fileId)
			throws HarnessException {
		account.soapSend("<DocumentActionRequest xmlns='urn:zmailMail'>"
				+ "<action id='" + fileId + "'  op='!watch' /></DocumentActionRequest>");

		SleepUtil.sleepVerySmall();

		//verification
		ZAssert.assertTrue(account.soapMatch(
				"//mail:DocumentActionResponse//mail:action", "op", "!watch"),
				"Verify file is inmarked favorite");
	}
	// upload file
	protected String uploadFileViaSoap(ZmailAccount account, String fileName, FolderItem ...folderItemArray)
			throws HarnessException {
		FolderItem folderItem = FolderItem.importFromSOAP(account, SystemFolder.Briefcase);

		if ((folderItemArray != null) && folderItemArray.length >0) {
			folderItem = folderItemArray[0];
		}


		// Create file item
		String filePath = ZmailSeleniumProperties.getBaseDirectory()
				+ "/data/public/other/" + fileName;

		// Upload file to server through RestUtil
		String attachmentId = account.uploadFile(filePath);

		// if file already upload, then delete and upload it again
		if (attachmentId == null) {
			account.soapSend("<ItemActionRequest xmlns='urn:zmailMail'>"
					+ "<action id='" + attachmentId + "' op='trash'/>"
					+ "</ItemActionRequest>");

			attachmentId = account.uploadFile(filePath);
		}

		// Save uploaded file to the root folder through SOAP
		account.soapSend(
				"<SaveDocumentRequest xmlns='urn:zmailMail'>" + "<doc l='"
						+ folderItem.getId() + "'>" + "<upload id='"
						+ attachmentId + "'/>" + "</doc></SaveDocumentRequest>");

		//return id
		return account.soapSelectValue(
				"//mail:SaveDocumentResponse//mail:doc", "id");
	}
	// save document request
	protected void saveDocumentRequestViaSoap(ZmailAccount account, FolderItem folder,String attachmentId) throws HarnessException
	{
		account.soapSend("<SaveDocumentRequest xmlns='urn:zmailMail'>"
				+ "<doc l='" + folder.getId() + "'><upload id='"
				+ attachmentId + "'/></doc></SaveDocumentRequest>");
	}
	// get activity stream request
	protected void getActivityStreamRequest(ZmailAccount account,FolderItem folder) throws HarnessException
	{
		account.soapSend(
				"<GetActivityStreamRequest xmlns='urn:zmailMail' offset='0' limit='250' id='"
						+ folder.getId() + "'/>"
				);
	}
	// delete folder via Soap
	protected void deleteFolderViaSoap(ZmailAccount account, FolderItem folder)throws HarnessException
	{
		account.soapSend(
				"<ItemActionRequest xmlns='urn:zmailMail'>"
						+ "<action id='" + folder.getId() + "' op='delete'/>"
						+ "</ItemActionRequest>"
				);
	}
	//Function returns the array list containing folder Items. folder structure gets created is with hierarchy folder1>folder2>folder3.
	protected ArrayList<FolderItem> createMultipleSubfolders(ZmailAccount act,String ParentFolder,int noOfSubFolders) throws HarnessException

	{
		ArrayList<FolderItem> folderNames = new ArrayList<FolderItem>();

		String _parent = ParentFolder;

		for(int i=0;i<noOfSubFolders;i++)
		{
			FolderItem newParentFolder = FolderItem.importFromSOAP(act, _parent);

			folderNames.add(newParentFolder);

			String subFolderName = "childFolder"+ZmailSeleniumProperties.getUniqueString();
			// Create sub folder Using SOAP under a folder created

			act.soapSend(
					"<CreateFolderRequest xmlns='urn:zmailMail'>"
							+"<folder name='" + subFolderName + "' l='" + newParentFolder.getId() + "' view='document'/>"
							+"</CreateFolderRequest>");

			_parent =subFolderName;


		}

		return folderNames;

	}
	// Function for checking if required document is present in destination folder or not.
	public boolean isDocumentPresentInFolder(ZmailAccount acount,String folderName, String fileName)throws HarnessException
	{
		boolean docPresent= false;

		FolderItem FolderName = FolderItem.importFromSOAP(acount, folderName);

		acount.soapSend(
				"<SearchRequest xmlns=\"urn:zmailMail\" types=\"document\">"
						+"<query>inid:"+FolderName.getId()+"</query>"
						+"</SearchRequest>"
				);

		docPresent= acount.soapMatch("//mail:SearchResponse/mail:doc", "name", fileName);

		return docPresent;
	}
	// create mountpoint request via soap
	protected void mountRequestViaSoap(ZmailAccount account,ZmailAccount grantee,FolderItem folder,FolderItem mountPointFolder,
			String mountPointName) throws HarnessException {
		grantee
		.soapSend("<CreateMountpointRequest xmlns='urn:zmailMail'>"
				+ "<link l='" + mountPointFolder.getId()
				+ "' name='" + mountPointName
				+ "' view='document' rid='" + folder.getId()
				+ "' zid='" + account.ZmailId + "'/>"
				+ "</CreateMountpointRequest>");
	}

}
