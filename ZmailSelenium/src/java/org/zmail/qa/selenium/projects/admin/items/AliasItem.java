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
import org.zmail.qa.selenium.framework.util.ZmailAdminAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;

public class AliasItem implements IItem {

	protected String aliasLocalName;
	protected String aliasDomainName;
	protected String aliasId;
	protected String aliasTargetEmail;
	protected String aliasTargetId;
	
	public AliasItem() {
		super();
		
		aliasLocalName = "alias" + ZmailSeleniumProperties.getUniqueString();
		aliasDomainName = ZmailSeleniumProperties.getStringProperty("testdomain");
		aliasId = null;	
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
		return (getEmailAddress());
	}
	
	public String getID() {
		return (aliasId);
	}
	
	public String getEmailAddress() {
		return (aliasLocalName + "@" + aliasDomainName);
	}
	
	public void setLocalName(String name) {
		aliasLocalName = name;
	}
	
	public String getLocalName() {
		return (aliasLocalName);
	}

	public void setDomainName(String domain) {
		aliasDomainName = domain;
	}
	
	public String getDomainName() {
		return (aliasDomainName);
	}
	
	public void setTarget(AccountItem account) throws HarnessException {
		if ( (account.getEmailAddress() == null) || (account.getEmailAddress().trim().length() == 0) )
			throw new HarnessException("AccountItem email address is not set");
		if ( (account.Id == null) || (account.Id.trim().length() == 0) )
			throw new HarnessException("AccountItem ID is not set");

		aliasTargetEmail = account.getEmailAddress();		
		aliasTargetId = account.Id;

	}

	public void setTargetAccountEmail(String emailAddress) throws HarnessException {
		if ( (aliasTargetEmail != null) && (aliasTargetEmail.equals(emailAddress)) )
			return; // Nothing to update
		
		if ( (emailAddress == null) || (emailAddress.trim().length() == 0) )
			throw new HarnessException("emailAddress cannot be null or blank");
		
		// Need to get the AccountID
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
							"<GetAccountRequest xmlns='urn:zmailAdmin'>"
				+                "<account by='name'>"+ emailAddress +"</account>"
                +            "</GetAccountRequest>");
		String id = ZmailAdminAccount.AdminConsoleAdmin().soapSelectValue("//admin:account", "id");
		
		aliasTargetEmail = emailAddress;		
		aliasTargetId = id;
	}
	
	public String getTargetAccountEmail() {
		return (aliasTargetEmail);
	}

	public void setTargetAccountId(String id) throws HarnessException {
		if ( (aliasTargetId != null) && (aliasTargetId.equals(id)) )
			return; // Nothing to update
		
		if ( (id == null) || (id.trim().length() == 0) )
			throw new HarnessException("id cannot be null or blank");
		
		// Need to get the AccountID
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
							"<GetAccountRequest xmlns='urn:zmailAdmin'>"
				+                "<account by='id'>"+ id +"</account>"
                +            "</GetAccountRequest>");
		String emailAddress = ZmailAdminAccount.AdminConsoleAdmin().soapSelectValue("//admin:account", "name");

		aliasTargetEmail = emailAddress;		
		aliasTargetId = id;

	}
	
	public String getTargetAccountId() {
		return (aliasTargetId);
	}


}
