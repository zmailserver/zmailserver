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
package com.zimbra.qa.selenium.framework.items;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.XmlParseException;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.XmlStringUtil;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;

/**
 * This class represents a new document item
 * 
 * 
 */
public class DocumentItem implements IItem {
	protected static Logger logger = LogManager.getLogger(IItem.class);
	/**
	 * The document name
	 */
	private String docName;

	/**
	 * The document text
	 */
	private String docText;

	/**
	 * The document id
	 */
	private String docId;

	
	/**
	 * The status of this document
	 */
	public boolean isSaved;

	/**
	 * The version of this document
	 */
	public int version;

	/**
	 * briefcaseFolderID
	 */
	private String briefcaseFolderID;

	public static Element e;

	/**
	 * Create a document item
	 */
	public DocumentItem() {
		populateDocumentData();
	}

	/**
	 * Create a document item
	 */
	public DocumentItem(String name) {
		docName = name;
	}
	
	@Override
	public String getName() {
		return docName;
	}
	
	/**
	 * Populate DocumentItem data
	 * 
	 */
	public void populateDocumentData() {
		docName = "name" + ZimbraSeleniumProperties.getUniqueString();
		docText = "text" + ZimbraSeleniumProperties.getUniqueString();
	}

	/**
	 * Get document id
	 */
	public String getId() {
		return docId;
	}
	
	/**
	 * Set document id
	 * 
	 * @param id
	 */
	public void setDocId(String id) {
		this.docId = id;
	}

	/**
	 * Set document name
	 * 
	 * @param docName
	 */
	public void setDocName(String docName) {
		this.docName = docName;
	}

	/**
	 * Get document text
	 */
	public String getDocText() {
		return docText;
	}

	/**
	 * Set document text
	 * 
	 * @param text
	 */
	public void setDocText(String text) {
		this.docText = text;
	}

	public String GetBriefcaseIdUsingSOAP(ZimbraAccount account)
			throws HarnessException {
		if (briefcaseFolderID == null) {
			try {
				Element getFolderResponse = account
						.soapSend("<GetFolderRequest xmlns='urn:zimbraMail'/>");
				Element briefcase = ZimbraAccount.SoapClient.selectNode(
						getFolderResponse,
						"//mail:folder/mail:folder[@name='Briefcase']");
				briefcaseFolderID = briefcase.getAttribute("id");
			} catch (Exception e) {
				throw new HarnessException(
						"Could not parse GetFolderRequest: ", e);
			}
		}
		return briefcaseFolderID;
	}

	public void createUsingSOAP(ZimbraAccount account) throws HarnessException {
		String contentHTML = XmlStringUtil.escapeXml("<html>" + "<body>"
				+ docText + "</body>" + "</html>");
		e = account
				.soapSend("<SaveDocumentRequest requestId='0' xmlns='urn:zimbraMail'>"
						+ "<doc name='"
						+ docName
						+ "' l='16' ct='application/x-zimbra-doc'>"
						+ "<content>"
						+ contentHTML
						+ "</content>"
						+ "</doc>"
						+ "</SaveDocumentRequest>");
	}

	public void createUsingSOAP(ZimbraAccount account, String attachmentId)
			throws HarnessException {
		account.soapSend("<SaveDocumentRequest xmlns='urn:zimbraMail'>"
				+ "<doc l='16'>" + "<upload id='" + attachmentId + "'/>"
				+ "</doc>" + "</SaveDocumentRequest>");

		// account.soapSelectNodes("//mail:SaveDocumentResponse");

		// account.soapSelectNode("//mail:SaveDocumentResponse", 1);

		// account.soapSelectValue("//mail:doc","name");
	}

	public void importFromSOAP(Element response) throws HarnessException {
		if ( response == null )
			throw new HarnessException("Element cannot be null");
		
		try {
			// Make sure we only have the DocumentResponse part
			Element getDocumentResponse = ZimbraAccount.SoapClient.selectNode(
					response,
					"//mail:SaveDocumentResponse | //mail:SearchResponse");
			if (getDocumentResponse == null)
				throw new HarnessException(
						"Element does not contain GetSaveDocumentResponse");

			Element doc = ZimbraAccount.SoapClient.selectNode(
					getDocumentResponse, "//mail:doc");
			if (doc == null)
				throw new HarnessException(
						"Element does not contain doc element");

			// get the ID
			doc.getAttribute("id", null);

		} catch (Exception e) {
			throw new HarnessException("Could not parse SaveDocumentResponse: "
					+ response.prettyPrint(), e);
		}

	}

	public void importFromSOAP(ZimbraAccount account, String query)
			throws HarnessException {
		try {
			// Element search =
			account
					.soapSend("<SearchRequest xmlns='urn:zimbraMail' types='document'>"
							+ "<query>"
							+ query
							+ "</query>"
							+ "</SearchRequest>");

			Element[] results = account
					.soapSelectNodes("//mail:SearchResponse/mail:doc");
			if (results.length != 1)
				throw new HarnessException("Query should return 1 result, not "
						+ results.length);

			// String id =
			account.soapSelectValue("//mail:SearchResponse/mail:doc", "id");

			/*
			 * account.soapSend( "<GetDocumentRequest xmlns='urn:zimbraMail'>" +
			 * "<doc id='" + id + "'/>" + "</GetDocumentRequest>");
			 * 
			 * Element getDocumentResponse =
			 * account.soapSelectNode("//mail:GetDocumentResponse", 1);
			 */

			Element getDocumentResponse = account.soapSelectNode(
					"//mail:SearchResponse", 1);

			// Using the response, create this item
			importFromSOAP(getDocumentResponse);

		} catch (Exception e) {
			throw new HarnessException("Unable to import using SOAP query("
					+ query + ") and account(" + account.EmailAddress + ")", e);
		}
	}

	@Override
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append(DocumentItem.class.getSimpleName()).append('\n');
		sb.append("Doc name: \n").append(docName).append('\n');
		sb.append("Doc text: \n").append(docText).append("\n");
		return (sb.toString());
	}

	/**
	 * Sample DocItem Driver
	 * 
	 * @param args
	 * @throws HarnessException 
	 * @throws ServiceException 
	 */
	public static void main(String[] args) throws HarnessException, XmlParseException {
		DocumentItem d = new DocumentItem();
		d.createUsingSOAP(ZimbraAccount.AccountA());

		String envelopeString = "<soap:Envelope xmlns:soap='http://www.w3.org/2003/05/soap-envelope'>"
				+ "<soap:Header>"
				+ "<context xmlns='urn:zimbra'>"
				+ "<change token='2'></change>"
				+ "</context>"
				+ "</soap:Header>"
				+ "<soap:Body>"
				+ "<SaveDocumentResponse requestId='0' xmlns='urn:zimbraMail'>"
				+ "<doc name='"
				+ d.docName
				+ "' ver='1'/>"
				+ "</SaveDocumentResponse>"
				+ "</soap:Body>"
				+ "</soap:Envelope>";

		d = new DocumentItem();
		d.importFromSOAP(Element.parseXML(envelopeString));
		d.importFromSOAP(e);
		System.out.println("Imported document item from SOAP");
		System.out.println(d.prettyPrint());

		String contentHTML = XmlStringUtil.escapeXml("<html>" + "<body>" + "t1"
				+ "</body>" + "</html>");

		ZimbraAccount.AccountA().soapSend(
				"<SaveDocumentRequest requestId='0' xmlns='urn:zimbraMail'>" +

				"<doc name='d1' l='16' ct='application/x-zimbra-doc'>"
						+ "<content>" + contentHTML + "</content>" + "</doc>"
						+ "</SaveDocumentRequest>");

		d = new DocumentItem();
		d.importFromSOAP(ZimbraAccount.AccountA(), "d1");
		System.out.println("Imported mail item from query");
		System.out.println(d.prettyPrint());
	}
}
