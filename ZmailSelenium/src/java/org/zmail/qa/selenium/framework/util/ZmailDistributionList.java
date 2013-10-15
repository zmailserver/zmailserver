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
package org.zmail.qa.selenium.framework.util;

import org.apache.log4j.*;

public class ZmailDistributionList {
	private static Logger logger = LogManager.getLogger(ZmailDistributionList.class);

	public String ZmailId = null;
	public String DisplayName = null;
	public String EmailAddress = null;
	public String Password = null;

	public ZmailDistributionList() {
		this(null, null);
	}
	
	/*
	 * Create an account with the email address <name>@<domain>
	 * The password is set to config property "adminPwd"
	 */
	public ZmailDistributionList(String email, String password) {

		EmailAddress = email;
		if ( (email == null) || (email.trim().length() == 0) ) {
			EmailAddress = "dl" + ZmailSeleniumProperties.getUniqueString() + "@" + ZmailSeleniumProperties.getStringProperty("testdomain", "testdomain.com");
		}

		Password = password;
		if ( (password == null) || (password.trim().length() == 0) ) {
			password = ZmailSeleniumProperties.getStringProperty("adminPwd", "test123");
		}
	}

	/**
	 * Creates the account on the ZCS using CreateAccountRequest
	 */
	public ZmailDistributionList provision() {
		
		try {

			// Make sure domain exists
			String domain = EmailAddress.split("@")[1];


			// If the domain does not exist, create it
			ZmailAdminAccount.GlobalAdmin().soapSend(
						"<CreateDomainRequest xmlns='urn:zmailAdmin'>"
					+		"<name>"+ domain +"</name>"
					+	"</CreateDomainRequest>");




			// Create the account
			ZmailAdminAccount.GlobalAdmin().soapSend(
						"<CreateDistributionListRequest xmlns='urn:zmailAdmin'>"
					+		"<name>"+ this.EmailAddress +"</name>"
					+		"<a n='description'>description"+ ZmailSeleniumProperties.getUniqueString() +"</a>"
					+	"</CreateDistributionListRequest>");

			ZmailId = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:dl", "id");

			// You can't add a logger to a DL
//			if ( ZmailSeleniumProperties.getStringProperty("soap.trace.enabled", "false").toLowerCase().equals("true") ) {
//				
//				ZmailAdminAccount.GlobalAdmin().soapSend(
//							"<AddAccountLoggerRequest xmlns='urn:zmailAdmin'>"
//						+		"<account by='name'>"+ this.EmailAddress + "</account>"
//						+		"<logger category='zmail.soap' level='trace'/>"
//						+	"</AddAccountLoggerRequest>");
//
//			}
			

		} catch (HarnessException e) {

			logger.error("Unable to provision DL: "+ EmailAddress, e);
			ZmailId = null;

		}


		return (this);
	}

	
	public ZmailDistributionList addMember(ZmailAccount account) throws HarnessException {
		return(addMember(account.EmailAddress));
	}
	
	public ZmailDistributionList addMember(ZmailDistributionList list) throws HarnessException {
		return (addMember(list.EmailAddress));
	}
	
	protected ZmailDistributionList addMember(String email) throws HarnessException {
		
		ZmailAdminAccount.GlobalAdmin().soapSend(
					"<AddDistributionListMemberRequest xmlns='urn:zmailAdmin'>"
				+		"<id>"+ this.ZmailId +"</id>"
				+		"<dlm>"+ email +"</dlm>"
				+	"</AddDistributionListMemberRequest>");

		return (this);
	}
	
	public ZmailDistributionList grantRight(ZmailAccount grantee, String right) throws HarnessException {

		ZmailAdminAccount.GlobalAdmin().soapSend(
					"<GrantRightRequest xmlns='urn:zmailAdmin'>"
				+		"<target by='name' type='dl'>"+ this.EmailAddress +"</target>"
				+		"<grantee by='name' type='usr'>"+ grantee.EmailAddress +"</grantee>"
				+		"<right>"+ right +"</right>"
				+	"</GrantRightRequest>");

		return (this);

	}
}
