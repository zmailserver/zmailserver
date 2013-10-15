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
package org.zmail.qa.selenium.framework.util.staf;

import org.zmail.qa.selenium.framework.util.*;

/**
 * @deprecated As of version 7.0
 * @author zmail
 *
 */
public class Stafzmprov extends StafServicePROCESS {
	
	
	public static String createAccount(String emailaddress) throws HarnessException {
		ZmailAccount a = new ZmailAccount(emailaddress, "test123");
		a.provision();
		a.authenticate();
		return (a.EmailAddress);
	}
	
	public static String getRandomAccount() throws HarnessException {

		// Get the account name
		// Use <locale>_<timestamp>@domain.com
		//
		long now = System.currentTimeMillis();
		String testdomain = ZmailSeleniumProperties.getStringProperty("testdomain");
		String locale = ZmailSeleniumProperties.getStringProperty("locale");
		String emailaddress = locale + "_" + now + "@" + testdomain;

		return (createAccount(emailaddress));
		
	}
	
	public static void modifyAccount(ZmailAccount account, String attr, String value) throws HarnessException {
		
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<ModifyAccountRequest xmlns='urn:zmailAdmin'>" +
					"<id>"+ account.ZmailId  +"</id>" +
					"<a n='" + attr +"'>"+ value +"</a>" +
				"</ModifyAccountRequest>");
		ZmailAdminAccount.GlobalAdmin().soapSelectNode("//admin:ModifyAccountResponse", 1);

	}
	
	public static void modifyAccount(String emailaddress, String attr, String value) throws HarnessException {
		
		// Get the account id by calling provision
		ZmailAccount account = new ZmailAccount(emailaddress, "test123");
		account.provision();
		
		// Next, modify the account
		modifyAccount(account, attr, value);

	}
	
	public static String getAccountPreferenceValue(String emailaddress, String attr) throws HarnessException {
				
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<GetAccountRequest xmlns='urn:zmailAdmin'>" +
					"<account by='name'>"+ emailaddress +"</account>" +
				"</GetAccountRequest>");
		
		ZmailAdminAccount.GlobalAdmin().soapSelectNode("//admin:GetAccountResponse", 1);
		String value = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:a[@n='" + attr + "']", null);
		logger.debug("GetAccountRequest returned "+ attr +" "+ value);
		
		return (value);
		
	}
	
	public static ZmailAccount addAccountAlias(ZmailAccount account, String alias) throws HarnessException {
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<AddAccountAliasRequest xmlns='urn:zmailAdmin'>" +
					"<id>"+ account.ZmailId +"</id>" +
					"<alias>"+ alias +"</alias>" +
				"</AddAccountAliasRequest>");
		return (account);
	}
	
	public static String addAccountAlias(String emailaddress, String alias) throws HarnessException {
		
		// Get the account so we know the ID
		ZmailAccount account = new ZmailAccount(emailaddress, "test123");
		account.provision();
		
		// Add the alias
		addAccountAlias(account, alias);
		
		return (account.EmailAddress);
	}
	
	public static String createEquipment(String emailaddress) throws HarnessException {
		ZmailResource resource = new ZmailResource(ZmailResource.Type.EQUIPMENT, emailaddress, "test123");
		return (resource.EmailAddress);
	}
	
	public static String createLocation(String emailaddress) throws HarnessException {
		ZmailResource resource = new ZmailResource(ZmailResource.Type.LOCATION, emailaddress, "test123");
		return (resource.EmailAddress);
	}
	
	public static String createDomain(String domain) throws HarnessException {
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<CreateDomainRequest xmlns='urn:zmailAdmin'>"+
                	"<name>"+ domain +"</name>" +
                "</CreateDomainRequest>");
		
		// No need to check the response, since the domain may already exist
		return (domain);
	}
	
	
	
	public Stafzmprov() {
		super();
		
		logger.info("new Stafzmprov");
		StafService = "PROCESS";

	}
	
	
	public boolean execute(String command) throws HarnessException {
		setCommand(command);
		return (super.execute());
	}
	
	protected String setCommand(String command) {
		
		// Make sure the full path is specified
		if ( command.trim().startsWith("zmprov") ) {
			command = "/opt/zmail/bin/" + command;
		}
		// Running a command as 'zmail' user.
		// We must convert the command to a special format
		// START SHELL COMMAND "su - zmail -c \'<cmd>\'" RETURNSTDOUT RETURNSTDERR WAIT 30000</params>

		StafParms = String.format("START SHELL COMMAND \"su - zmail -c '%s'\" RETURNSTDOUT RETURNSTDERR WAIT %d", command, this.getTimeout());
		return (getStafCommand());
	}

 


}
