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

import org.zmail.qa.selenium.framework.items.IItem;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;

public class ResourceItem implements IItem {
	protected String resourceLocalName;
	protected String resourceDomainName;
	protected String resourceId;

	public ResourceItem() {
		super();

		resourceLocalName = "resource" + ZmailSeleniumProperties.getUniqueString();
		resourceDomainName = ZmailSeleniumProperties.getStringProperty("testdomain");
		resourceId = null;	
	}

	@Override
	public void createUsingSOAP(ZmailAccount account) throws HarnessException {
		// TODO Auto-generated method stub

	}

	@Override
	public String prettyPrint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return (getLocalName());
	}

	public String getID() {
		return (resourceId);
	}

	public String getEmailAddress() {
		return (resourceLocalName + "@" + resourceDomainName);
	}

	public void setLocalName(String name) {
		resourceLocalName = name;
	}

	public String getLocalName() {
		return (resourceLocalName);
	}

	public void setDomainName(String domain) {
		resourceDomainName = domain;
	}

	public String getDomainName() {
		return (resourceDomainName);
	}
}
