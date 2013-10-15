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

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailAccount;


/**
 * This class represents a mail message
 * 
 * @author Matt Rhoades
 *
 */
public class ConversationItem extends MailItem {


	////
	// START: GUI Data
	////

	/**
	 * Whether the conversation is expanded or not
	 */
	public boolean isExpanded = false;
	
	/**
	 * Whether this object is a mail that is part of an expanded conversation
	 */
	public boolean gIsConvExpanded = false;
	

	////
	// FINISH: GUI Data
	////
		
	
	/**
	 * Create a mail item
	 */
	public ConversationItem() {
	}

	@Override
	public String getName() {
		return (gSubject);
	}
	
	public void createUsingSOAP(ZmailAccount account) throws HarnessException {
		throw new HarnessException("implement me");
	}

	public static ConversationItem importFromSOAP(Element GetMsgResponse) throws HarnessException {
		if ( GetMsgResponse == null )
			throw new HarnessException("Element cannot be null");

		throw new HarnessException("implement me");
	}

	public static ConversationItem importFromSOAP(ZmailAccount account, String query) throws HarnessException {
		throw new HarnessException("implement me");
	}
	
	@Override
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append(ConversationItem.class.getSimpleName()).append('\n');
		sb.append("isExpanded: ").append(isExpanded).append('\n');
		sb.append(super.prettyPrint());
		return (sb.toString());
	}




}
