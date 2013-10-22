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
package org.zmail.qa.selenium.projects.admin.items;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.zmail.qa.selenium.framework.items.IItem;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;

public class DistributionListItem implements IItem {

	protected static Logger logger = LogManager.getLogger(IItem.class);

	protected static String Id=null;
	
	protected String distributionListLocalName; // Email Address is LocalName@DomainName
	protected String distributionListDomainName;

	public DistributionListItem() {
		super();
		
		distributionListLocalName = "dl" + ZmailSeleniumProperties.getUniqueString();
		distributionListDomainName = ZmailSeleniumProperties.getStringProperty("testdomain");
		//Id = null;
		
	}
	@Override
	public void createUsingSOAP(ZmailAccount account) throws HarnessException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return (getEmailAddress());
	}
	
	public String getID() {
		return (Id);
	}
	
	public String getEmailAddress() {
		return (distributionListLocalName + "@" + distributionListDomainName);
	}
	@Override
	public String prettyPrint() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setLocalName(String name) {
		distributionListLocalName = name;
	}
	
	public String getLocalName() {
		return (distributionListLocalName);
	}

	public void setDomainName(String domain) {
		distributionListDomainName = domain;
	}
	
	public String getDomainName() {
		return (distributionListDomainName);
	}
}
