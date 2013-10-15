/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013 VMware, Inc.
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

import org.zmail.common.soap.Element;



public class ZmailAdminAccount extends ZmailAccount {
	private static Logger logger = LogManager.getLogger(ZmailAccount.class);

	public ZmailAdminAccount(String email) {
		EmailAddress = email;
		Password = ZmailSeleniumProperties.getStringProperty("adminPwd", "test123");
		
		// In the dev environment, they may need a config value to override
		// the default, so use that value here
		ZmailMailHost = ZmailSeleniumProperties.getStringProperty("adminHost", EmailAddress.split("@")[1]);

	}


	/**
	 * Creates the account on the ZCS using CreateAccountRequest
	 * zmailIsAdminAccount is set to TRUE
	 */
	public ZmailAccount provision() {
		
		try {
			
			// Check if the account already exists
			// If yes, don't provision again
			//
			if ( exists() ) {
				logger.info(EmailAddress + " already exists.  Not provisioning again.");
				return (this);
			}

			// Make sure domain exists
			ZmailDomain domain = new ZmailDomain( EmailAddress.split("@")[1]);
			domain.provision();
			
				
			// Account does not exist.  Create it now.
			ZmailAdminAccount.GlobalAdmin().soapSend(
					"<CreateAccountRequest xmlns='urn:zmailAdmin'>" +
						"<name>"+ EmailAddress +"</name>" +
						"<password>"+ Password +"</password>" +
						"<a n='zmailIsAdminAccount'>TRUE</a>" +
					"</CreateAccountRequest>");
			
			Element[] createAccountResponse = ZmailAdminAccount.GlobalAdmin().soapSelectNodes("//admin:CreateAccountResponse");


			if ( (createAccountResponse == null) || (createAccountResponse.length == 0)) {

				Element[] soapFault = ZmailAdminAccount.GlobalAdmin().soapSelectNodes("//soap:Fault");
				if ( soapFault != null && soapFault.length > 0 ) {
				
					String error = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//zmail:Code", null);
					throw new HarnessException("Unable to create account: "+ error);
					
				}
				
				throw new HarnessException("Unknown error when provisioning account");
				
			}

			// Set the account settings based on the response
			ZmailId = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account", "id");
			ZmailMailHost = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account/admin:a[@n='zmailMailHost']", null);

			// If the adminHost value is set, use that value for the ZmailMailHost
			String adminHost = ZmailSeleniumProperties.getStringProperty("adminHost", null);
			if ( adminHost != null ) {
				ZmailMailHost = adminHost;
			}

			// If SOAP trace logging is specified, turn it on
			if ( ZmailSeleniumProperties.getStringProperty("soap.trace.enabled", "false").toLowerCase().equals("true") ) {
				
				ZmailAdminAccount.GlobalAdmin().soapSend(
							"<AddAccountLoggerRequest xmlns='urn:zmailAdmin'>"
						+		"<account by='name'>"+ EmailAddress + "</account>"
						+		"<logger category='zmail.soap' level='trace'/>"
						+	"</AddAccountLoggerRequest>");

			}


			// Sync the GAL to put the account into the list
			domain.syncGalAccount();

		} catch (HarnessException e) {
			logger.error("Unable to provision account: "+ EmailAddress);
			ZmailId = null;
			ZmailMailHost = null;
		}
		
		return (this);
	}

	/**
	 * Authenticates the admin account (using SOAP admin AuthRequest)
	 * Sets the authToken
	 */
	public ZmailAccount authenticate() {
		try {
			soapSend(
					"<AuthRequest xmlns='urn:zmailAdmin'>" +
					"<name>"+ EmailAddress +"</name>" +
					"<password>"+ Password +"</password>" +
			"</AuthRequest>");
			String token = soapSelectValue("//admin:authToken", null);
			soapClient.setAuthToken(token);
		} catch (HarnessException e) {
			logger.error("Unable to authenticate "+ EmailAddress, e);
			soapClient.setAuthToken(null);
		}
		return (this);
	}

	/**
	 * Get the global admin account used for Admin Console testing
	 * This global admin has the zmailPrefAdminConsoleWarnOnExit set to false
	 */
	public static synchronized ZmailAdminAccount AdminConsoleAdmin() {
		if ( _AdminConsoleAdmin == null ) {
			try {
				String name = "globaladmin"+ ZmailSeleniumProperties.getUniqueString();
				String domain = ZmailSeleniumProperties.getStringProperty("server.host","qa60.lab.zmail.com");
				_AdminConsoleAdmin = new ZmailAdminAccount(name +"@"+ domain);
				_AdminConsoleAdmin.provision();
				_AdminConsoleAdmin.authenticate();
				_AdminConsoleAdmin.soapSend(
							"<ModifyAccountRequest xmlns='urn:zmailAdmin'>"
						+		"<id>"+ _AdminConsoleAdmin.ZmailId +"</id>"
						+		"<a n='zmailPrefAdminConsoleWarnOnExit'>FALSE</a>"
						+	"</ModifyAccountRequest>");
			} catch (HarnessException e) {
				logger.error("Unable to fully provision admin account", e);
			}
		}
		return (_AdminConsoleAdmin);
	}
	public static synchronized void ResetAccountAdminConsoleAdmin() {
		logger.warn("AdminConsoleAdmin is being reset");
		_AdminConsoleAdmin = null;
	}
	private static ZmailAdminAccount _AdminConsoleAdmin = null;

	/**
	 * Reset all static accounts.  This method should be used before/after
	 * the harness has executed in the STAF service mode.  For example, if
	 * one STAF request executes on server1 and the subsequent STAF request
	 * executes on server2, then all accounts need to be reset, otherwise
	 * the second request will have references to server1.
	 */
	public static void reset() {
		ZmailAdminAccount._AdminConsoleAdmin = null;
		ZmailAdminAccount._GlobalAdmin = null;		
	}

	/**
	 * Get the global admin account
	 * This account is defined in config.properties as <adminName>@<server>
	 * @return The global admin account
	 */
	public static synchronized ZmailAdminAccount GlobalAdmin() {
		if ( _GlobalAdmin == null ) {
			String name = ZmailSeleniumProperties.getStringProperty("adminName", "admin@zqa-062.eng.vmware.com");
			_GlobalAdmin = new ZmailAdminAccount(name);
			_GlobalAdmin.authenticate();
		}
		return (_GlobalAdmin);
	}
	private static ZmailAdminAccount _GlobalAdmin = null;


	/**
	 * @param args
	 * @throws HarnessException 
	 */
	public static void main(String[] args) throws HarnessException {

		// Configure log4j using the basic configuration
		BasicConfigurator.configure();



		// Use the pre-provisioned global admin account to send a basic request
		ZmailAdminAccount.GlobalAdmin().soapSend("<GetVersionInfoRequest xmlns='urn:zmailAdmin'/>");
		if ( !ZmailAdminAccount.GlobalAdmin().soapMatch("//admin:GetVersionInfoResponse", null, null) )
			throw new HarnessException("GetVersionInfoRequest did not return GetVersionInfoResponse");



		// Create a new global admin account
		String domain = ZmailSeleniumProperties.getStringProperty("server.host","qa60.lab.zmail.com");
		ZmailAdminAccount admin = new ZmailAdminAccount("admin"+ System.currentTimeMillis() +"@"+ domain);
		admin.provision();	// Create the account (CreateAccountRequest)
		admin.authenticate();		// Authenticate the account (AuthRequest)

		// Send a basic request as the new admin account
		admin.soapSend("<GetServiceStatusRequest xmlns='urn:zmailAdmin'/>");
		if ( !admin.soapMatch("//admin:GetServiceStatusResponse", null, null) )
			throw new HarnessException("GetServiceStatusRequest did not return GetServiceStatusResponse");


		logger.info("Done!");
	}

}
