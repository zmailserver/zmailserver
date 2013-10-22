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
package org.zmail.qa.selenium.framework.items;

import org.apache.log4j.*;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.util.*;

/**
 * This class represents a mail message
 * 
 * @author Matt Rhoades
 * 
 */
public class TaskItem implements IItem {

	protected static Logger logger = LogManager.getLogger(IItem.class);

	// //
	// START: GUI Data
	// //

	public boolean gIsChecked;

	public String gTags; // TODO: how to represent the icon?

	public String gPriority; // TODO: how to represent the icon?

	public boolean gHasAttachments;

	public String gSubject;

	public String gStatus;

	public String gPercentComplete;

	public String gDueDate;

	public String gtaskBody;
	public String gtaskHtmlBody;

	// //
	// FINISH: GUI Data
	// //

	/**
	 * Create a mail item
	 */
	public TaskItem() {
		populateTaskData();
	}
	
	public void setName(String name) {
		gSubject=name;
	}
	public String getName() {
		return (gSubject);
	}
	
	
	public String gettaskBody() {

		return gtaskBody;
	}

	public void settaskBody(String taskBody) {
		gtaskBody = taskBody;
	}
	public String getHtmlTaskBody() {

		return gtaskHtmlBody;
	}

	public void setHtmlTaskBody(String taskHtmlBody) {
		gtaskHtmlBody = taskHtmlBody;
	}
	public void populateTaskData() {
		// TODO Auto-generated method stub
		//taskSubject = "subject" + ZmailSeleniumProperties.getUniqueString();
		//taskBody = "Body" + ZmailSeleniumProperties.getUniqueString();
	}
	public String myId;
	public String getId() {
		return (myId);
	}
	public void setId(String id) {
		myId=id;
	}

	public static TaskItem importFromSOAP(Element GetMsgResponse) throws HarnessException {

		if ( GetMsgResponse == null )
			throw new HarnessException("Element cannot be null");

		TaskItem task = null;

		try {

			// Make sure we only have the GetMsgResponse part
			Element getMsgResponse = ZmailAccount.SoapClient.selectNode(
					GetMsgResponse, "//mail:GetMsgResponse");
			
			if (getMsgResponse == null)
				throw new HarnessException(
						"Element does not contain GetMsgResponse");
			Element m = ZmailAccount.SoapClient.selectNode(getMsgResponse,"//mail:comp");
			if (m == null)
				throw new HarnessException(
						"Element does not contain an m element");

			// Create the object
			task = new TaskItem();
			//Set task body
			task.settaskBody(m.getAttribute("desc", null));
			//Set task name
			task.setName(m.getAttribute("name",null));
			//Set task id
			task.setId(m.getAttribute("calItemId", null));
			task.setHtmlTaskBody(m.getAttribute("descHtml", null));
			// TODO: parse the <m/> element

			return (task);

		} catch (Exception e) {
			throw new HarnessException("Could not parse GetMsgResponse: "
					+ GetMsgResponse.prettyPrint(), e);
		} finally {
			if (task != null)
				logger.info(task.prettyPrint());
		}

	}

	public static TaskItem importFromSOAP(ZmailAccount account, String query)
			throws HarnessException {

		try {

			account
					.soapSend("<SearchRequest xmlns='urn:zmailMail' types='task' >"
							+ "<query>"
							+ query
							+ "</query>"
							+ "</SearchRequest>");

			Element[] results = account
					.soapSelectNodes("//mail:SearchResponse/mail:task");
			if (results.length != 1)
				throw new HarnessException("Query should return 1 result, not "
						+ results.length);

			String invId = account.soapSelectValue(
					"//mail:SearchResponse/mail:task", "invId");

			account.soapSend("<GetMsgRequest xmlns='urn:zmailMail'>"
					+ "<m id='" + invId + "' />" + "</GetMsgRequest>");
			Element getMsgResponse = account.soapSelectNode(
					"//mail:GetMsgResponse", 1);

			// Using the response, create this item
			return (importFromSOAP(getMsgResponse));

		} catch (Exception e) {
			throw new HarnessException("Unable to import using SOAP query("
					+ query + ") and account(" + account.EmailAddress + ")", e);
		}
	}

	public void createUsingSOAP(ZmailAccount account) throws HarnessException {
		throw new HarnessException("implement me!");
	}

	@Override
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append(TaskItem.class.getSimpleName()).append('\n');
		sb.append("Checked: ").append(gIsChecked).append('\n');
		sb.append("Tags: ").append(gTags).append('\n');
		sb.append("Priority: ").append(gPriority).append('\n');
		sb.append("Attachments: ").append(gHasAttachments).append('\n');
		sb.append("Subject: ").append(gSubject).append('\n');
		sb.append("Status: ").append(gStatus).append('\n');
		sb.append("% Complete: ").append(gPercentComplete).append('\n');
		sb.append("Due Date: ").append(gDueDate).append('\n');
		return (sb.toString());
	}

}
