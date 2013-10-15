/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.zmail.common.soap.Element;

public class OctopusAccount extends ZmailAccount {
	private static Logger logger = LogManager.getLogger(OctopusAccount.class);
	private static OctopusAccount _AccountZWC = null;

	/**
	 * Get the user account logged into ZWC being tested
	 * @return the ZmailAccount object representing the test account
	 */
	public static synchronized OctopusAccount AccountZWC() {
		if ( _AccountZWC == null ) {
			_AccountZWC = new OctopusAccount();
			_AccountZWC.provision();
			_AccountZWC.authenticate();
		}
		return (_AccountZWC);
	}
	
	// Set the default account settings
	@SuppressWarnings("serial")
	private static final Map<String, String> accountAttrs = new HashMap<String, String>() {{
		put("zmailPrefLocale", ZmailSeleniumProperties.getStringProperty("locale"));
		put("zmailPrefAutoAddAddressEnabled", "FALSE");
		put("zmailPrefTimeZoneId", ZmailSeleniumProperties.getStringProperty("zmailPrefTimeZoneId", "America/Los_Angeles"));			
	}};

	/**
	 * Creates the account on the ZCS using CreateAccountRequest
	 */
	public ZmailAccount provision() {
		try {

			
			// Make sure domain exists
			ZmailDomain domain = new ZmailDomain( EmailAddress.split("@")[1]);
			domain.provision();
			


			// Build the list of default preferences
			StringBuilder prefs = new StringBuilder();
			for (Map.Entry<String, String> entry : accountAttrs.entrySet()) {
				prefs.append(String.format("<a n='%s'>%s</a>", entry.getKey(), entry.getValue()));
			}
			for (Map.Entry<String, String> entry : preferences.entrySet()) {
				prefs.append(String.format("<a n='%s'>%s</a>", entry.getKey(), entry.getValue()));
			}

			// Create the account
			ZmailAdminAccount.GlobalAdmin().soapSend(
					"<CreateAccountRequest xmlns='urn:zmailAdmin'>"
					+		"<name>"+ EmailAddress +"</name>"
					+		"<password>"+ Password +"</password>"
					+		prefs.toString()
					+	"</CreateAccountRequest>");

			Element[] createAccountResponse = ZmailAdminAccount.GlobalAdmin().soapSelectNodes("//admin:CreateAccountResponse");


			if ( (createAccountResponse == null) || (createAccountResponse.length == 0)) {

				Element[] soapFault = ZmailAdminAccount.GlobalAdmin().soapSelectNodes("//soap:Fault");
				if ( soapFault != null && soapFault.length > 0 ) {
				
					String error = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//zmail:Code", null);
					throw new HarnessException("Unable to create account: "+ error);
					
				}
				
				
				logger.error("Error occured during account provisioning, perhaps account already exists: "+ EmailAddress);
				ZmailAdminAccount.GlobalAdmin().soapSend(
						"<GetAccountRequest xmlns='urn:zmailAdmin'>"
						+		"<account by='name'>"+ EmailAddress + "</account>"
						+	"</GetAccountRequest>");

				Element[] getAccountResponse = ZmailAdminAccount.GlobalAdmin().soapSelectNodes("//admin:GetAccountResponse");


				if ( (getAccountResponse == null) || (getAccountResponse.length == 0)) {

					logger.error("Error occured during get account provisioning.  Now I'm really confused");

				} else {

					ZmailId = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account", "id");
					ZmailMailHost = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account/admin:a[@n='zmailMailHost']", null);
					ZmailPrefLocale = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account/admin:a[@n='zmailPrefLocale']", null);

				}
			} else {

				ZmailId = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account", "id");
				ZmailMailHost = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account/admin:a[@n='zmailMailHost']", null);
				ZmailPrefLocale = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account/admin:a[@n='zmailPrefLocale']", null);


			}

			if ( (ZmailPrefLocale == null) || ZmailPrefLocale.trim().equals("") ) {
				ZmailPrefLocale = Locale.getDefault().toString();
			}

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

			logger.error("Unable to provision account: "+ EmailAddress, e);
			ZmailId = null;
			ZmailMailHost = null;

		}


		return (this);
	}

}
