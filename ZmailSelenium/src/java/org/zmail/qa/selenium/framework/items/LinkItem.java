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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailAccount;

/**
 * This class represents a new document item
 * 
 * 
 */
public class LinkItem implements IItem {
	protected static Logger logger = LogManager.getLogger(IItem.class);
	
	/**
	 * The link name
	 */
	private String linkName;

	public void setName(String name) {
		linkName = name;
	}
	
	public String getName() {
		return (linkName);
	}
		
	@Override
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append(LinkItem.class.getSimpleName()).append('\n');
		sb.append("Link: \n").append(linkName).append('\n');
		return (sb.toString());
	}

	@Override
	public void createUsingSOAP(ZmailAccount account) throws HarnessException {
		// TODO Auto-generated method stub		
	}	
}
