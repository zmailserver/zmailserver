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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpRecoverableException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.InvalidXPathException;

import org.zmail.common.auth.ZAuthToken;
import org.zmail.common.net.SocketFactories;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.SoapFaultException;
import org.zmail.common.soap.SoapParseException;
import org.zmail.common.soap.SoapProtocol;
import org.zmail.common.soap.SoapUtil;
import org.zmail.common.soap.Element.ContainerException;
import org.zmail.common.soap.XmlParseException;
import org.zmail.common.util.ByteUtil;
import org.zmail.qa.selenium.framework.core.*;
import org.zmail.qa.selenium.framework.ui.I18N;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties.AppType;
import org.zmail.qa.selenium.framework.util.staf.Stafpostqueue;


@SuppressWarnings("deprecation")
public class ZmailAccount {
	private static Logger logger = LogManager.getLogger(ZmailAccount.class);

	protected SoapClient soapClient = new SoapClient();
	public String ZmailSoapClientHost = null;
	public String ZmailSoapAdminHost = null;
	public String ZmailMailHost = null;
	public String ZmailMailClientHost = null;
	public String ZmailId = null;
	public String EmailAddress = null;
	public String Password = null;
	public boolean accountIsDirty = false;
	protected String ZmailPrefLocale = Locale.getDefault().toString();
	protected String MyAuthToken = null;
	protected String MyClientAuthToken = null;
	protected Map<String, String> preferences = new HashMap<String, String>();
	public final static String clientAccountName = "local@host.local";

	/*
	 * Create an account with the email address account<num>@<testdomain>
	 * The password is set to config property "adminPwd"
	 */
	public ZmailAccount() {
		this(null, null);
	}

	/*
	 * Create an account with the email address <name>@<domain>
	 * The password is set to config property "adminPwd"
	 */
	public ZmailAccount(String email, String password) {

		try {
			if ( email == null ) {
				setPref("displayName", ZmailSeleniumProperties.getStringProperty("locale").toLowerCase().replace("_", "") + ZmailSeleniumProperties.getUniqueString());
				email = getPref("displayName") + "@" + ZmailSeleniumProperties.getStringProperty("testdomain", "testdomain.com");
			} else {
				setPref("displayName", email.split("@")[0]);
			}
		} catch (HarnessException e) {
			logger.error(e);
		}
		
		EmailAddress = email;

		if ( password == null ) {
			password = ZmailSeleniumProperties.getStringProperty("adminPwd", "test123");
		}
		Password = password;
	}

	/**
	 * Get the user account logged into ZDC being tested
	 * @return the ZmailAccount object representing the test account
	 */
	public static synchronized ZmailAccount AccountZDC() {
		if ( _AccountZDC == null ) {
			_AccountZDC = new ZmailAccount();
			_AccountZDC.provision();

			logger.debug("Authenticating Mail server");
			_AccountZDC.authenticate();

		}
		return (_AccountZDC);
	}

	/**
	 * This is to reset the Client's authentication.
	 * Note: To be used when terminating client app.
	 */
	public void resetClientAuthentication() {
		logger.debug("Reset client authentication...");
		this.MyClientAuthToken = null;
	}

	/**
	 * Authenticate the user account logged into ZDC being tested to the
	 * client (not ZCS)
	 * @return (String) Client Authorization Token
	 */
	public String authenticateToMailClientHost() {
		if (this.ZmailMailClientHost == null) {
			this.ZmailMailClientHost = ZmailSeleniumProperties.getStringProperty(
					"desktop.server.host", "localhost");
		}
		if (this.MyClientAuthToken == null) {
			logger.debug("Authenticating Client...");
			this.authenticate(SOAP_DESTINATION_HOST_TYPE.CLIENT);
		} else {
			//Fall through
		}
		return MyClientAuthToken;
	}

	public static synchronized void ResetAccountZDC() {
		logger.warn("AccountZDC is being reset");
		_AccountZDC = null;
	}
	private static ZmailAccount _AccountZDC = null;

	/**
	 * Get the user account logged into ZWC being tested
	 * @return the ZmailAccount object representing the test account
	 */
	public static synchronized ZmailAccount AccountZWC() {
		if ( _AccountZWC == null ) {
			_AccountZWC = new ZmailAccount();
			_AccountZWC.provision();
			_AccountZWC.authenticate();
		}
		return (_AccountZWC);
	}
	public static synchronized void ResetAccountZWC() {
		logger.warn("AccountZWC is being reset");
		_AccountZWC = null;
	}
	private static ZmailAccount _AccountZWC = null;

	/**
	 * Get the user account logged into HTML being tested
	 * @return the ZmailAccount object representing the test account
	 */
	public static synchronized ZmailAccount AccountHTML() {
		if ( _AccountHTML == null ) {
			_AccountHTML = new ZmailAccount();
			_AccountHTML.provision();
			_AccountHTML.authenticate();
		}
		return (_AccountHTML);
	}
	public static synchronized void ResetAccountHTML() {
		logger.warn("AccountHTML is being reset");
		_AccountHTML = null;
	}
	private static ZmailAccount _AccountHTML = null;

	public static synchronized ZmailAccount AccountZMC() {
		if ( _AccountZMC == null ) {
			_AccountZMC = new ZmailAccount();
			_AccountZMC.provision();
			_AccountZMC.authenticate();
		}
		return (_AccountZMC);
	}
	public static synchronized void ResetAccountZMC() {
		_AccountZMC = null;
	}
	private static ZmailAccount _AccountZMC = null;

	/**
	 * Get a general use account for interacting with the test account
	 * @return a general use ZmailAccount
	 */
	public static synchronized ZmailAccount AccountA() {
		if ( _AccountA == null ) {
			_AccountA = new ZmailAccount();
			_AccountA.provision();
			_AccountA.authenticate();
		}
		return (_AccountA);
	}
	private static ZmailAccount _AccountA = null;

	/**
	 * Get a general use account for interacting with the test account
	 * @return a general use ZmailAccount
	 */
	public static synchronized ZmailAccount AccountB() {
		if ( _AccountB == null ) {
			_AccountB = new ZmailAccount();
			_AccountB.provision();
			_AccountB.authenticate();
		}	
		return (_AccountB);
	}
	private static ZmailAccount _AccountB = null;


	/**
	 * Reset all static accounts.  This method should be used before/after
	 * the harness has executed in the STAF service mode.  For example, if
	 * one STAF request executes on server1 and the subsequent STAF request
	 * executes on server2, then all accounts need to be reset, otherwise
	 * the second request will have references to server1.
	 */
	public static void reset() {
		ZmailAccount._AccountA = null;
		ZmailAccount._AccountB = null;
		ZmailAccount._AccountHTML = null;
		ZmailAccount._AccountZDC = null;
		ZmailAccount._AccountZMC = null;
		ZmailAccount._AccountZWC = null;		
	}
	
	// Set the default account settings
	@SuppressWarnings("serial")
	private static final Map<String, String> accountAttrs = new HashMap<String, String>() {{
		
		// The following settings can be tuned from config.properties
		//
		
		put("zmailPrefLocale", ZmailSeleniumProperties.getStringProperty("locale"));
		put("zmailPrefTimeZoneId", ZmailSeleniumProperties.getStringProperty("zmailPrefTimeZoneId", "America/Los_Angeles"));

		
		// The following settings are specific to the test harness
		// and deviate from the default settings to work around
		// test harness issues/limitations
		//
		// zmailPrefCalendarApptReminderWarningTime=0 ... A random reminder will throw off the tests
		// zmailPrefAutoAddAddressEnabled=FALSE ... Adding addresses to the addressbook might break a test
		// zmailPrefWarnOnExit=FALSE ... A system popup will occur if this is TRUE, which will freeze the tests

		put("zmailPrefAutoAddAddressEnabled", "FALSE");
		put("zmailPrefCalendarApptReminderWarningTime", "0");
		put("zmailPrefWarnOnExit","FALSE");
	}};

	/**
	 * Determines if the account already exists
	 * If yes, then the account settings are reset based on the GetAccountResponse
	 * @throws HarnessException 
	 */
	public boolean exists() throws HarnessException {
	
		// Check if the account exists
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<GetAccountRequest xmlns='urn:zmailAdmin'>"
				+		"<account by='name'>"+ EmailAddress + "</account>"
				+	"</GetAccountRequest>");
		
		Element[] getAccountResponse = ZmailAdminAccount.GlobalAdmin().soapSelectNodes("//admin:GetAccountResponse");


		if ( (getAccountResponse == null) || (getAccountResponse.length == 0) ) {
			
			logger.debug("Account does not exist");
			return (false);
			
		}
		

		// Reset the account settings based on the response
		ZmailId = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account", "id");
		ZmailMailHost = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account/admin:a[@n='zmailMailHost']", null);
		ZmailPrefLocale = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account/admin:a[@n='zmailPrefLocale']", null);

		// If pref is not set, then use default
		if ( (ZmailPrefLocale == null) || ZmailPrefLocale.trim().equals("") ) {
			ZmailPrefLocale = Locale.getDefault().toString();
		}

		// If the adminHost value is set, use that value for the ZmailMailHost
		String adminHost = ZmailSeleniumProperties.getStringProperty("adminHost", null);
		if ( adminHost != null ) {
			ZmailMailHost = adminHost;
		}
		
		return (true);
	}

	/**
	 * Creates the account on the ZCS using CreateAccountRequest
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
				
				throw new HarnessException("Unknown error when provisioning account");
				
			}
			

			// Set the account settings based on the response
			ZmailId = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account", "id");
			ZmailMailHost = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account/admin:a[@n='zmailMailHost']", null);
			ZmailPrefLocale = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:account/admin:a[@n='zmailPrefLocale']", null);


			// If pref is not set, then use default
			if ( (ZmailPrefLocale == null) || ZmailPrefLocale.trim().equals("") ) {
				ZmailPrefLocale = Locale.getDefault().toString();
			}

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

			logger.error("Unable to provision account: "+ EmailAddress, e);
			ZmailId = null;
			ZmailMailHost = null;

		}


		return (this);
	}

	public ZmailAccount authenticate() {
		return authenticate(SOAP_DESTINATION_HOST_TYPE.SERVER);
	}

	/**
	 * Authenticates the account (using SOAP client AuthRequest)
	 * Sets the authToken
	 */
	public ZmailAccount authenticate(SOAP_DESTINATION_HOST_TYPE destinationType) {
		try {
			switch (destinationType) {
			case SERVER:
				soapSend(
						"<AuthRequest xmlns='urn:zmailAccount'>" +
						"<account by='name'>"+ EmailAddress + "</account>" +
						"<password>"+ Password +"</password>" +
						"</AuthRequest>",
						destinationType);
				MyAuthToken = soapSelectValue("//acct:authToken", null);
				soapClient.setAuthToken(MyAuthToken);
				break;
			case CLIENT:
				String username = clientAccountName;
				String password = ZmailDesktopProperties.getInstance().getSerialNumber();
				soapSend(
						"<AuthRequest xmlns='urn:zmailAccount'>" +
						"<account by='name'>"+ username + "</account>" +
						"<password>"+ password +"</password>" +
						"</AuthRequest>",
						destinationType);
				MyClientAuthToken = soapSelectValue("//acct:authToken", null);
				soapClient.setClientAuthToken(MyClientAuthToken);
				break;
			}
		} catch (HarnessException e) {
			logger.error("Unable to authenticate "+ EmailAddress, e);
			soapClient.setAuthToken(null);
		}
		return (this);
	}

	  /**
    * Modify user prefences using ModifyPrefsRequest with the default SERVER
    * host destination type
    * @param preferences Preferences to be modified through SOAP
    * @throws HarnessException 
    */
   public ZmailAccount modifyPreferences(Map<String, String> preferences) {
      return modifyPreferences(preferences, SOAP_DESTINATION_HOST_TYPE.SERVER);

   }

   /**
    * Modify user preferences using ModifyPrefsRequest
    * @param preferences Preferences to be modified through SOAP
    * @param destinationType The destination Host Type: SERVER or CLIENT
    * @throws HarnessException
    */
   public ZmailAccount modifyPreferences(Map<String, String> preferences,
         SOAP_DESTINATION_HOST_TYPE destinationType) {

      // Test Case Trace logging
      for (Map.Entry<String, String> entry : preferences.entrySet()) {
         ExecuteHarnessMain.tracer.trace(EmailAddress +" preferences: "+ entry.getKey() +"="+ entry.getValue());
      }

      
      StringBuilder sb = new StringBuilder();
      for (Map.Entry<String, String> entry : preferences.entrySet()) {
         sb.append(String.format("<pref name='%s'>%s</pref>", entry.getKey(), entry.getValue()));
         
         
         // If the locale preference is being changed, then remember the value
         if ( entry.getKey().equals("zmailPrefLocale") ) {
            setLocalePreference(entry.getValue());
         }
         

      }

      if ( sb.length() <= 0 )
         return (this); // Nothing to modify
      
      
      try
      {
         
         soapSend(
            "<ModifyPrefsRequest xmlns='urn:zmailAccount'>" +
                  sb.toString() +
            "</ModifyPrefsRequest>",
            destinationType);

         Element[] response = soapSelectNodes("//acct:ModifyPrefsResponse");
         if ( response == null || response.length != 1 )
            throw new HarnessException("Unable to modify preference "+ soapLastResponse());
         
      } catch (HarnessException e) {
         // TODO: I would prefer to throw HarnessException here
         logger.error("Unable to modify preference", e);
      }

      accountIsDirty = true;

      return (this);

   }

   /**
	 * Get all the available zimlets through SOAP from either client or server
	 * @param info Information to look for
	 * @param destinationType Type of SOAP destination, client or server
	 * @return String[] All available zimlets
	 * @throws HarnessException
	 */
	public String[] getAvailableZimlets(SOAP_DESTINATION_HOST_TYPE destinationType)
	throws HarnessException {
		String[] output = null;

		try {
			this.soapSend(
					"<GetInfoRequest xmlns='urn:zmailAccount'>" +
					"</GetInfoRequest>",
					destinationType,
					this.EmailAddress);
			Element[] response = soapSelectNodes("//acct:GetInfoResponse/acct:attrs/acct:attr");

			StringBuilder temp = new StringBuilder();
			for (Element element : response) {
				if (element.getAttribute("name").equals("zmailZimletAvailableZimlets")) {
					temp.append(element.getText().trim().replace("+", "")).append(";");
				}
			}

			temp.deleteCharAt(temp.length() - 1);
			output = temp.toString().split(";");

		} catch (ServiceException se) {
			throw new HarnessException("Getting service exception while getting available zimlets", se);
		}

		accountIsDirty = true;

		return output;

	}

	/**
	 * Modify user zimlet preferences using ModifyZimletPrefsRequest
	 * @param zimletPreferences Zimlet Preferences to be modified through SOAP
	 * @param destinationType The destination Host Type: SERVER or CLIENT
	 * @throws HarnessException
	 */
	public ZmailAccount modifyZimletPreferences(Map<String, String> preferences) {
		return modifyZimletPreferences(preferences, SOAP_DESTINATION_HOST_TYPE.SERVER);
	}


	/**
	 * Modify user zimlet preferences using ModifyZimletPrefsRequest
	 * @param zimletPreferences Zimlet Preferences to be modified through SOAP
	 * @param destinationType The destination Host Type: SERVER or CLIENT
	 * @throws HarnessException
	 */
	public ZmailAccount modifyZimletPreferences(Map<String, String> zimletPreferences,
			SOAP_DESTINATION_HOST_TYPE destinationType) {

		for (Map.Entry<String, String> entry : zimletPreferences.entrySet()) {
			ExecuteHarnessMain.tracer.trace(EmailAddress +" zimletPreferences: "+
					entry.getKey() + "=" + entry.getValue());
		}

		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : zimletPreferences.entrySet()) {
			sb.append(String.format("<zimlet xmlns='' name='%s' presence='%s'/>",
					entry.getKey(), entry.getValue()));
		}

		if ( sb.length() <= 0 )
			return (this); // Nothing to modify

		try
		{
			soapSend(
					"<ModifyZimletPrefsRequest xmlns='urn:zmailAccount'>" +
					sb.toString() +
					"</ModifyZimletPrefsRequest>",
					destinationType);

			Element[] response = soapSelectNodes("//acct:ModifyZimletPrefsResponse");
			if ( response == null || response.length != 1 )
				throw new HarnessException("Unable to modify preference "+ soapLastResponse());
		} catch (HarnessException e) {
			logger.error("Unable to modify preference", e);
		}
		return (this);
	}


	/**
	 * Get a user preference value
	 */
	public String getPreference(String pref) throws HarnessException {

		soapSend(
				"<GetPrefsRequest xmlns='urn:zmailAccount'>" +
				"<pref name='"+ pref +"'/>" +
		"</GetPrefsRequest>");

		String value = soapSelectValue("//acct:pref[@name='"+ pref +"']", null);
		return (value);
	}

	/**
	 * Set a user preference.  This method only changes the ZmailAccount object.  The
	 * harness must still call ModifyPrefsRequest, CreateAccountRequest, ModifyAccountRequest,
	 * etc.
	 * 
	 */
	public void setPref(String key, String value) throws HarnessException {

		preferences.put(key, value);
		
	}
	
	public String getPref(String key) throws HarnessException {
		
		return (preferences.get(key));
		
	}
	
	public void clearPref(String key) throws HarnessException {
		
		if ( preferences.containsKey(key) ) {
			preferences.remove(key);
		}
		
	}

	/**
	 * Get this Account's Locale Preference (zmailPrefLocale)
	 * @return
	 * @throws HarnessException
	 */
	public Locale getLocalePreference() {
		return (I18N.getLocaleFromString(ZmailPrefLocale));
	}

	protected void setLocalePreference(String locale) {
		ZmailPrefLocale = locale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
		+ ((EmailAddress == null) ? 0 : EmailAddress.hashCode());
		return result;
	}

	/*
	 * If the account email addresses are equal, then the objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZmailAccount other = (ZmailAccount) obj;
		if (EmailAddress == null) {
			if (other.EmailAddress != null)
				return false;
		} else if (!EmailAddress.equals(other.EmailAddress))
			return false;
		return true;
	}


	/**
	 * Upload a file to the upload servlet
	 * @param filename The full path to the upload file
	 * @return the attachment id, to be used with SaveDocumentRequest, for example
	 * @throws HarnessException
	 */
	public String uploadFile(String filename) throws HarnessException {
		RestUtil util = new RestUtil();
		util.setAuthentication(this);
		util.setPath("/service/upload");
		util.setQueryParameter("fmt", "raw");
		util.setUploadFile(new File(filename));
		if ( util.doPost() != HttpStatus.SC_OK )
			throw new HarnessException("Unable to upload "+ filename +" to "+ util.getLastURI());

		String response = util.getLastResponseBody();

		// paw through the returned HTML and get the attachment id
		// example: loaded(<code>,'null','<id>')
		//
		int firstQuote = response.indexOf("','") + 3;
		int lastQuote = response.lastIndexOf("'");
		if (lastQuote == -1 || firstQuote == -1)
			throw new HarnessException("Attachment post failed, unexpected response: " + response);

		String id = response.substring(firstQuote, lastQuote);
		logger.info("Attachment ID: "+ id);

		return (id);
	}

	public enum SOAP_DESTINATION_HOST_TYPE {
		SERVER, CLIENT
	}

	/**
	 * Send a SOAP request from this account to the specified destination host type
	 * @param request the SOAP request body (see ZmailServer/docs/soap.txt)
	 * @param destinationHostType The destination Host Type: SERVER or CLIENT
	 * @return the response envelope
	 * @throws HarnessException on failure
	 */
	public Element soapSend(String request, SOAP_DESTINATION_HOST_TYPE destinationHostType)
	throws HarnessException {
		return soapSend(request, destinationHostType, null);
	}

	/**
	 * Send a SOAP request from this account to the specified destination host type with specific
	 * account name to be added in SOAP context
	 * @param request the SOAP request body (see ZmailServer/docs/soap.txt)
	 * @param destinationHostType The destination Host Type: SERVER or CLIENT
	 * @param accountName Account name to be added in SOAP context
	 * @return the response envelope
	 * @throws HarnessException on failure
	 */
	public Element soapSend(String request, SOAP_DESTINATION_HOST_TYPE destinationHostType,
			String accountName) throws HarnessException {
		// TODO: need to watch for certain SOAP requests, such
		// as ModifyPrefsRequest, which could trigger a client reload
		//
		String destination = null;
		switch (destinationHostType) {
		case CLIENT:
			destination = ZmailMailClientHost;
			break;
		case SERVER:
			destination = ZmailMailHost;
			break;
		}

		return (soapClient.sendSOAP(destination, request, destinationHostType, accountName));
	}

	/**
	 * Send a SOAP request from this account with the default mail server destination
	 * @param request the SOAP request body (see ZmailServer/docs/soap.txt)
	 * @return the response envelope
	 * @throws HarnessException on failure
	 */
	public Element soapSend(String request) throws HarnessException {

		try {
			if ( !(this instanceof ZmailAdminAccount) ) {
				ExecuteHarnessMain.tracer.trace(EmailAddress +" sends "+ Element.parseXML(request).getName());
			}
		} catch (XmlParseException e) {
			ExecuteHarnessMain.tracer.warn("Unable to parse "+ request);
		}

		// TODO: need to watch for certain SOAP requests, such
		// as ModifyPrefsRequest, which could trigger a client reload
		//

		return soapSend(request, SOAP_DESTINATION_HOST_TYPE.SERVER);
	}

	/**
	 * Match an xpath or regex from the last SOAP response
	 * if xpath == null, then use the root element - TODO: not yet supported
	 * if attr == null, value is element text.  if attr != null, value is attr value
	 * if regex == null, return true if xpath matches.  If regex != null, a regex to match against the value
	 * @param xpath
	 * @param attr
	 * @param regex
	 * @return
	 */
	public boolean soapMatch(String xpath, String attr, String regex) {

		// TODO: support xpath == null

		// Find all nodes that match the expath
		Element[] elements = soapClient.selectNodes(xpath);

		// If regex == null, return true if xpath matched elements
		if ( regex == null ) {
			return (elements.length > 0);
		}

		// Loop through all xpath matches, looking for values that may match
		for (Element e : elements) {

			String value;
			if ( attr == null ) {
				value = e.getText();
			} else {
				value = e.getAttribute(attr, null);
			}

			if ( value == null )
				continue; // No match in this element

			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(value);
			if ( m.matches() )
				return (true); // Otherwise, continue on next element
		}

		return (false); // Never found a match
	}

	/**
	 * Get a value from the last SOAP response
	 * @param xpath
	 * @param attr
	 * @return if attr == null, the element text.  if attr != null, the attr text.
	 */
	public String soapSelectValue(String xpath, String attr) {
		return (soapClient.selectValue(xpath, attr, 1));
	}

	/**
	 * Get a list of elements from the last response that match an xpath
	 * @param xpath
	 * @return
	 */
	public Element[] soapSelectNodes(String xpath) {

		return (soapClient.selectNodes(xpath));
	}

	/**
	 * Get an element that matches the last response
	 * @param xpath
	 * @param index 1-based index if xpath matches multiple elements
	 * @return
	 */
	public Element soapSelectNode(String xpath, int index) {
		return (soapClient.selectNode(xpath, index));
	}

	/**
	 * Return the last SOAP response
	 * @return the last SOAP envelope in prettyPrint() string format
	 */ 
	public String soapLastResponse() {
		return (soapClient.responseEnvelope == null ? "null" : soapClient.responseEnvelope.prettyPrint());
	}



	public static class SoapClient {
		private Logger logger = LogManager.getLogger(SoapClient.class);

		protected String AuthToken = null;
		protected String ClientAuthToken = null;
		protected String SessionId = null;
		protected String clientSessionId = null;
		protected int SequenceNum = -1;

		protected Element requestEnvelope;
		protected Element responseEnvelope;
		protected Element requestContext;
		protected Element requestBody;

		private static final Element[] EMPTY_ELEMENT_ARRAY = new Element[0];

		protected URI mURI = null;

		protected SoapProtocol mSoapProto = null;
		protected ProxySoapHttpTransport mTransport = null;

		private static boolean isEasySslInitiated = false;

		/**
		 * Create a SOAP 1.2 client
		 */
		 public SoapClient() {

			 if ( mSoapProto == null ){
				 mSoapProto = SoapProtocol.Soap12;
			 }

			 if ( !isEasySslInitiated ) {
				 SocketFactories.registerProtocols(true);
				 isEasySslInitiated = true;
			 }
		 }

		 /**
		  * Set the Zmail AuthToken
		  * @param token - use null to clear the context
		  * @return
		  */
		 public String setAuthToken(String token) {
			 return (AuthToken = token);
		 }

		 /**
		  * Set the Zmail ClientAuthToken
		  * @param token - use null to clear the context
		  * @return
		  */
		 public String setClientAuthToken(String token) {
			 return (ClientAuthToken = token);
		 }

		 /**
		  * Set the Zmail SessionId
		  * @param id
		  * @return
		  */
		 public String setSessionId(String id) {
			 return (SessionId = id);
		 }

		 /**
		  * Set the Zmail SessionId
		  * @param id
		  * @return
		  */
		 public String setClientSessionId(String id) {
			 return (clientSessionId = id);
		 }

		 /**
		  * Set the Zmail SequenceNum
		  * Use with the SessionId
		  * @param num - default -1
		  * @return
		  */
		 public int setSequenceNum(int num) {
			 return (SequenceNum = num);
		 }


		 protected Element setContext(String token, String sessionId, int sequenceId) {
			 if ( token == null ) {
				 requestContext = null;
			 } else {
				 ZAuthToken zat = new ZAuthToken(null, token, null);
				 if ( sessionId == null )
				 {
					 requestContext = SoapUtil.toCtxt(mSoapProto, zat);
				 }
				 else 
				 {
					 requestContext = SoapUtil.toCtxt(mSoapProto, zat, sessionId, sequenceId);
					 if ( sequenceId != -1 ) {
						 Element e = requestContext.addElement("notify");
						 e.addAttribute("seq", String.valueOf(sequenceId));
					 }
				 }

			 }
			 return (requestContext);
		 }

		 /**
		  * Send the specified Zmail SOAP request to the specified host with the
		  * default SERVER type destination host
		  * @param host Host name to send the SOAP context/request
		  * @param request Request to be sent over SOAP
		  * @return
		  * @throws HarnessException
		  */
		 public Element sendSOAP(String host, String request) throws HarnessException {
			 return sendSOAP(host, request, SOAP_DESTINATION_HOST_TYPE.SERVER);
		 }

		 /**
		  * Send the specified Zmail SOAP request to the specified host
		  * @param host Host name to send the SOAP context/request
		  * @param request Request to be sent over SOAP
		  * @param destinationType Destination host type: SERVER or CLIENT
		  * @return
		  * @throws HarnessException
		  */
		 public Element sendSOAP(String host, String request,
				 SOAP_DESTINATION_HOST_TYPE destinationType) throws HarnessException {
			 return sendSOAP(host, request, destinationType, null);
		 }

		 /**
		  * Send the specified Zmail SOAP request to the specified host with
		  * specific account name in context
		  * @param host Host name to send the SOAP context/request
		  * @param request Request to be sent over SOAP
		  * @param destinationType Destination host type: SERVER or CLIENT
		  * @param accountName Account name to be added to the context, if none, enter null
		  * @return
		  * @throws HarnessException
		  */
		 public Element sendSOAP(String host, String request, SOAP_DESTINATION_HOST_TYPE destinationType, String accountName) throws HarnessException {        	
			 try
			 {
				 switch (destinationType) {
				 case SERVER:
					 setContext(AuthToken, SessionId, SequenceNum);
					 break;
				 case CLIENT:
					 // Setting the session ID to 0 shouldn't affect anything for Desktop
					 // SOAP request
					 setClientSessionId("0");
					 setContext(ClientAuthToken, clientSessionId, SequenceNum);
					 if (accountName != null) {
						 SoapUtil.addTargetAccountToCtxt(requestContext, null, accountName);
					 }
					 break;
				 }




				 return (sendSOAP(host, requestContext, Element.parseXML(request), destinationType));

			 } catch (XmlParseException e) {
				 throw new HarnessException("Unable to parse request "+ request, e);
			 } catch (ContainerException e) {
				 throw new HarnessException("Unable to parse request "+ request, e);

			 }
		 }

		 /**
		  * Send a Zmail SOAP context/request to the host with the
		  * default SERVER type destination host
		  * @param host Host name to send the SOAP context/request
		  * @param context
		  * @param request Request to be sent over SOAP
		  * @return
		  * @throws HarnessException
		  */
		 public Element sendSOAP(String host, Element context, Element request)
		 throws HarnessException {
			 return sendSOAP(host, context, request, SOAP_DESTINATION_HOST_TYPE.SERVER);
		 }

		 /**
		  * Send a Zmail SOAP context/request to the host
		  * @param host Host name to send the SOAP context/request
		  * @param context
		  * @param request Request to be sent over SOAP
		  * @param destinationType Destination host type: SERVER or CLIENT
		  * @return
		  * @throws HarnessException
		  */
		 public Element sendSOAP(String host, Element context, Element request,
				 SOAP_DESTINATION_HOST_TYPE destinationType) throws HarnessException {

			 setTransport(host, request, destinationType);


			 // Remember the context, request, envelope and response for logging purposes
			 requestBody = request;
			 requestEnvelope = mSoapProto.soapEnvelope(requestBody, context);

			 try {
				 responseEnvelope = mTransport.invokeRaw(requestEnvelope);
			 } catch (IOException e) {
				 throw new HarnessException("Unable to send SOAP to "+ this.mURI.toString(), e);
			 } catch (ServiceException e) {
				 throw new HarnessException("Unable to send SOAP to "+ this.mURI.toString(), e);
			 }

			 // Log the request/response
			 logger.info("\n" + new Date() +" "+ mURI.toString() +"\n---\n"+ requestEnvelope.prettyPrint() +"\n---\n"+ responseEnvelope.prettyPrint() +"\n---\n");

			 // Check the queue, if required
			 doPostfixDelay();

			 return (responseEnvelope);
		 }

		 /**
		  * For certain SOAP requests, such as SendMsgRequest, a message may wind up in the
		  * postfix queue.  Check that the queue is empty before proceeding
		  * @throws HarnessException 
		  */
		 public void doPostfixDelay() throws HarnessException {

			 // If disabled, don't do anything
			 boolean enabled = ZmailSeleniumProperties.getStringProperty("postfix.check", "true").equals("true");
			 if ( !enabled ) {
				 logger.debug("postfix.check was not true ... skipping queue check");
				 return;
			 }


			 // Create an array of the requests that require a queue check
			 final List<String> requests = new ArrayList<String>();
			 requests.add("mail:SendMsgRequest");
			 requests.add("mail:SendDeliveryReportRequest");
			 requests.add("mail:CreateTaskRequest");
			 requests.add("mail:ModifyTaskRequest");
			 requests.add("mail:SetTaskRequest");
			 requests.add("mail:SetAppointmentRequest");
			 requests.add("mail:CreateAppointmentRequest");
			 requests.add("mail:ModifyAppointmentRequest");
			 requests.add("mail:CancelAppointmentRequest");
			 requests.add("mail:ForwardAppointmentRequest");
			 requests.add("mail:ForwardAppointmentInviteRequest");
			 requests.add("mail:SendInviteReplyRequest");
			 requests.add("mail:CreateAppointmentExceptionRequest");

			 // If the current SOAP request matches any of the "queue" requests, set matched=true
			 for (String request : requests) {
				 Element[] nodes = selectNodes(requestEnvelope, "//"+ request);
				 if ( nodes.length > 0 ) {
					 Stafpostqueue sp = new Stafpostqueue();
					 sp.waitForPostqueue();
					 break;
				 }
			 }

		 }

		 /**
		  * Return an array of elements from the last received SOAP response that match the xpath
		  * @param xpath
		  * @return
		  */
		 public Element[] selectNodes(String xpath) {
			 return (selectNodes(responseEnvelope, xpath));
		 }

		 /**
		  * Return the first matching element from the context that match the xpath
		  * @param context
		  * @param xpath
		  * @return
		  * @throws HarnessException 
		  */
		 public static Element selectNode(Element context, String xpath) {
			 Element[] nodes = selectNodes(context, xpath);
			 if (nodes.length == 0)
				 return (null);
			 return (nodes[0]);        		
		 }

		 /**
		  * Return an array of elements from the context that match the xpath
		  * @param context
		  * @param xpath
		  * @return An Array of elements that match the xpath (empty array if none)
		 * @throws HarnessException 
		  * @throws HarnessException 
		  */
		 public static Element[] selectNodes(Element context, String xpath) {
			 if ( context == null )
				 return (SoapClient.EMPTY_ELEMENT_ARRAY);

			 try {
				 org.dom4j.Element d4context = context.toXML();
				 org.dom4j.XPath Xpath = d4context.createXPath(xpath);
				 Xpath.setNamespaceURIs(getURIs());
				 
				 List<Element> zmailElements = new ArrayList<Element>();

				 for (Object o : Xpath.selectNodes(d4context)) {
					 if ( o instanceof org.dom4j.Element ) {
						 zmailElements.add(Element.convertDOM((org.dom4j.Element) o));
					 }
				 }
				 
				 return (zmailElements.toArray(new Element[zmailElements.size()]));

			 } catch (InvalidXPathException e) {
				 LogManager.getRootLogger().error("Unable to select nodes", e);
				 throw e;
			 }
		 }

		 /**
		  * Return the element from the last received SOAP response that matches the xpath
		  * @param xpath
		  * @param index - 1 based index
		  * @return
		  */
		 public Element selectNode(String xpath, int index) {
			 return(selectNode(responseEnvelope, xpath, index));
		 }

		 /**
		  * Return the element from context that matches the xpath
		  * @param context
		  * @param xpath
		  * @param index
		  * @return
		  */
		 public Element selectNode(Element context, String xpath, int index) {
			 Element[] nodes = selectNodes(context, xpath);
			 if ( nodes.length < index )
				 return (null);
			 return (nodes[index - 1]);
		 }

		 /**
		  * Return the 
		  * @param xpath
		  * @param attr
		  * @param index
		  * @return
		  */
		 public String selectValue(String xpath, String attr, int index) {
			 return (selectValue(responseEnvelope, xpath, attr, index));
		 }

		 public String selectValue(Element context, String xpath, String attr, int index) {

			 Element[] elements = null;
			 if ( xpath == null ) {

				 // no xpath - use the entire context
				 elements = new Element[1];
				 elements[0] = context;

			 } else {

				 // xpath specfied - only use the matching nodes
				 elements = selectNodes(context, xpath);

			 }

			 // Make sure we have at least the specified index
			 if ( elements.length < index )
				 return (null);


			 // Only use the element corresponding to the specified index
			 Element element = elements[index - 1];
			 String value = null;

			 if ( attr == null ) {
				 value = element.getText();
			 } else {
				 value = element.getAttribute(attr, null);
			 }

			 return (value);
		 }

		 protected void setTransport(String host, Element request) throws HarnessException {
			 setTransport(host, request, SOAP_DESTINATION_HOST_TYPE.SERVER);
		 }

		 protected void setTransport(String host, Element request, SOAP_DESTINATION_HOST_TYPE destinationHostType) throws HarnessException {

			 // Only set the transport if the URI changes
			 if ( setURI(host, request, destinationHostType) ) {


				 synchronized (mSoapProto) {

					 if (mTransport != null) {

						 logger.debug("mTransport shutting down");

						 mTransport.shutdown();
						 mTransport = null;

					 }

					 mTransport = new ProxySoapHttpTransport(mURI.toString());

					 logger.debug("mTransport pointing at " + mURI);

				 }                 

			 }
		 }

		 protected boolean setURI(String host, Element request) throws HarnessException {
			 return setURI(host, request, SOAP_DESTINATION_HOST_TYPE.SERVER);
		 }

		 protected boolean setURI(String host, Element request,
				 SOAP_DESTINATION_HOST_TYPE destinationType) throws HarnessException {

			 // TODO: need to get URI settings from config.properties


			 String scheme = ZmailSeleniumProperties.getStringProperty("server.scheme", "http");
			 String userInfo = null;
			 String p = ZmailSeleniumProperties.getStringProperty("server.port", "80");
			 int port = Integer.parseInt(p);
			 String path = "/";
			 String query = null;
			 String fragment = null;

			 String namespace = getNamespace(request);
			 logger.debug("namespace: " + namespace);

			 if ( namespace.equals("urn:zmailAdmin") ) {

				 // https://server.com:7071/service/admin/soap/
				 scheme = "https";
				 path = "/service/admin/soap/";

			 } else if ( namespace.equals("urn:zmailAccount") ) {

				 // http://server.com:80/service/soap/
				 path = "/service/soap/";

			 } else if ( namespace.equals("urn:zmailMail") ) {

				 // http://server.com:80/service/soap/
				 path = "/service/soap/";

			 } else if ( namespace.equals("urn:zmail") ) {

				 // http://server.com:80/service/soap/
				 path = "/service/soap/";

			 } else if ( namespace.equals("urn:zmailOffline") &&
					 ZmailSeleniumProperties.getAppType() == AppType.DESKTOP) {
				 // This is only for desktop
				 path = "/service/soap/";

			 } else {
				 throw new HarnessException("Unsupported qname: "+ namespace +".  Need to implement setURI for it.");
			 }

			 switch (destinationType) {
			 case SERVER:
				 if ( namespace.equals("urn:zmailAdmin") ) {
					 port = 7071;
				 }
				 break;
			 case CLIENT:
				 port = Integer.parseInt(ZmailDesktopProperties.getInstance().getConnectionPort());
				 break;
			 }

			 try {
				 URI uri = new URI(scheme, userInfo, host, port, path, query, fragment);
				 logger.debug("scheme: " + scheme);
				 logger.debug("userInfo: " + userInfo);
				 logger.debug("Host: " + host);
				 logger.debug("Port: " + port);
				 logger.debug("Path: " + path);
				 logger.debug("Query: " + query);
				 logger.debug("Fragment: " + fragment);

				 if ( uri.equals(mURI) ) {
					 return (false); // URI didn't change
				 } else {
					 mURI = uri;
					 return (true);
				 }
			 } catch (URISyntaxException e) {
				 throw new HarnessException("Unable to create SOAP URI", e);
			 }
		 }

		 protected static final Pattern mNamespacePattern = Pattern.compile("(xmlns=\\\"([^\"]+)\\\")");
		 protected String getNamespace(Element e) {
			 Matcher matcher = mNamespacePattern.matcher(e.toString());
			 while (matcher.find()) {
				 return (matcher.group(2));
			 }
			 return (null);
		 }
		 /*        
		protected Element[] getElementsFromPath(Element context, String path) {
    		org.dom4j.Element d4context = context.toXML();
    		org.dom4j.XPath xpath = d4context.createXPath(path);
    		xpath.setNamespaceURIs(getURIs());
    		org.dom4j.Node node;
    		List dom4jElements = xpath.selectNodes(d4context);

    		List<Element> zmailElements = new ArrayList<Element>();
    		Iterator iter = dom4jElements.iterator();
    		while (iter.hasNext()) {
    			node = (org.dom4j.Node)iter.next();
    			if (node instanceof org.dom4j.Element) {
    				Element zmailElement = Element.convertDOM((org.dom4j.Element) node);
    				zmailElements.add(zmailElement);
    			}
    		}

    		Element[] retVal = new Element[zmailElements.size()];
    		zmailElements.toArray(retVal);
    		return retVal;
        }

		  */    	

		 private static Map<String, String> mURIs = null;
		 static {
			 mURIs = new HashMap<String, String>();
			 mURIs.put("zmail", "urn:zmail");
			 mURIs.put("acct", "urn:zmailAccount");
			 mURIs.put("mail", "urn:zmailMail");
			 mURIs.put("offline", "urn:zmailOffline");
			 mURIs.put("admin", "urn:zmailAdmin");
			 mURIs.put("voice", "urn:zmailVoice");
			 mURIs.put("im", "urn:zmailIM");
			 mURIs.put("mapi", "urn:zmailMapi");
			 mURIs.put("sync", "urn:zmailSync");
			 mURIs.put("cs", "urn:zmailCS");
			 mURIs.put("test", "urn:zmailTestHarness");
			 mURIs.put("soap", "http://www.w3.org/2003/05/soap-envelope");
			 mURIs.put("soap12", "http://www.w3.org/2003/05/soap-envelope");
			 mURIs.put("soap11", "http://schemas.xmlsoap.org/soap/envelope/");
		 }
		 @SuppressWarnings("unchecked")
		private static Map getURIs() {
			 return mURIs;
		 }

	}

	public static class ProxySoapHttpTransport extends org.zmail.common.soap.SoapTransport {

		private static final String X_ORIGINATING_IP = "X-Originating-IP";

		private int mRetryCount;
		private int mTimeout;
		private String mUri;
		private HttpClient mClient;
		public String mAuthToken = null;

		public String toString() { 
			return "ProxySoapHttpTransport(uri="+mUri+")";
		}

		private static final HttpClientParams sDefaultParams = new HttpClientParams();
		static {
			// we're doing the retry logic at the SoapHttpTransport level, so don't do it at the HttpClient level as well
			sDefaultParams.setParameter(HttpMethodParams.RETRY_HANDLER, new HttpMethodRetryHandler() {
				public boolean retryMethod(HttpMethod method, IOException exception, int executionCount)  { return false; }
			});
		}

		/**
		 * Create a new SoapHttpTransport object for the specified URI.
		 * Supported schemes are http and https. The connection
		 * is not made until invoke or connect is called.
		 *
		 * Multiple threads using this transport must do their own
		 * synchronization.
		 */
		public ProxySoapHttpTransport(String uri) {
			this(uri, null, 0);
		}

		/**
		 * Create a new SoapHttpTransport object for the specified URI, with specific proxy information.
		 * 
		 * @param uri the origin server URL
		 * @param proxyHost hostname of proxy
		 * @param proxyPort port of proxy
		 */
		public ProxySoapHttpTransport(String uri, String proxyHost, int proxyPort) {
			this(uri, proxyHost, proxyPort, null, null);
		}

		/**
		 * Create a new SoapHttpTransport object for the specified URI, with specific proxy information including
		 * proxy auth credentials.
		 * 
		 * @param uri the origin server URL
		 * @param proxyHost hostname of proxy
		 * @param proxyPort port of proxy
		 * @param proxyUser username for proxy auth
		 * @param proxyPass password for proxy auth
		 */
		public ProxySoapHttpTransport(String uri, String proxyHost, int proxyPort, String proxyUser, String proxyPass) {
			super();
			mClient = new HttpClient(sDefaultParams);
			commonInit(uri);

			if (proxyHost != null && proxyHost.length() > 0 && proxyPort > 0) {
				mClient.getHostConfiguration().setProxy(proxyHost, proxyPort);
				if (proxyUser != null && proxyUser.length() > 0 && proxyPass != null && proxyPass.length() > 0) {
					mClient.getState().setProxyCredentials(new AuthScope(proxyHost, proxyPort), new UsernamePasswordCredentials(proxyUser, proxyPass));
				}
			}
		}

		/**
		 * Creates a new SoapHttpTransport that supports multiple connections
		 * to the specified URI.  Multiple threads can call the invoke()
		 * method safely without synchronization.
		 *
		 * @param uri
		 * @param maxConnections Note RFC2616 recommends the default of 2.
		 */
		public ProxySoapHttpTransport(String uri, int maxConnections, boolean connectionStaleCheckEnabled) {
			super();
			MultiThreadedHttpConnectionManager connMgr = new MultiThreadedHttpConnectionManager();
			connMgr.setMaxConnectionsPerHost(maxConnections);
			connMgr.setConnectionStaleCheckingEnabled(connectionStaleCheckEnabled);
			mClient = new HttpClient(sDefaultParams, connMgr);
			commonInit(uri);
		}

		/**
		 * Frees any resources such as connection pool held by this transport.
		 */
		public void shutdown() {
			HttpConnectionManager connMgr = mClient.getHttpConnectionManager();
			if (connMgr instanceof MultiThreadedHttpConnectionManager) {
				MultiThreadedHttpConnectionManager multiConnMgr = (MultiThreadedHttpConnectionManager) connMgr;
				multiConnMgr.shutdown();
			}
			mClient = null;
		}

		private void commonInit(String uri) {
			mUri = uri;
			mRetryCount = 3;
			setTimeout(0);
		}

		/**
		 *  Gets the URI
		 */
		 public String getURI() {
			 return mUri;
		 }

		 /**
		  * The number of times the invoke method retries when it catches a 
		  * RetryableIOException.
		  *
		  * <p> Default value is <code>3</code>.
		  */
		 public void setRetryCount(int retryCount) {
			 this.mRetryCount = retryCount;
		 }


		 /**
		  * Get the mRetryCount value.
		  */
		 public int getRetryCount() {
			 return mRetryCount;
		 }

		 /**
		  * The number of miliseconds to wait when connecting or reading
		  * during a invoke call. 
		  * <p>
		  * Default value is <code>0</code>, which means no mTimeout.
		  */
		 public void setTimeout(int timeout) {
			 mTimeout = timeout;
			 mClient.setConnectionTimeout(mTimeout);
			 mClient.setTimeout(mTimeout);
		 }

		 /**
		  * Get the mTimeout value.
		  */
		 public int getTimeout() {
			 return mTimeout;
		 }

		 public Element invoke(Element document, boolean raw, boolean noSession, String requestedAccountId, String changeToken, String tokenType) 
		 throws SoapFaultException, IOException, HttpException {
			 int statusCode = -1;

			 PostMethod method = null;
			 try {
				 // the content-type charset will determine encoding used
				 // when we set the request body
				 method = new PostMethod(mUri);
				 method.setRequestHeader("Content-Type", getRequestProtocol().getContentType());
				 if (getClientIp() != null)
					 method.setRequestHeader(X_ORIGINATING_IP, getClientIp());

				 Element soapReq = generateSoapMessage(document, raw, noSession, requestedAccountId, changeToken, tokenType);
				 String soapMessage = SoapProtocol.toString(soapReq, getPrettyPrint());
				 method.setRequestBody(soapMessage);
				 method.setRequestContentLength(EntityEnclosingMethod.CONTENT_LENGTH_AUTO);

				 if (getRequestProtocol().hasSOAPActionHeader())
					 method.setRequestHeader("SOAPAction", mUri);

				 if ( mAuthToken != null )
				 {
					 HttpState initialState = new HttpState();
					 String mUriHost = "";
					 try {
						 mUriHost = (new URI(mUri)).getHost();
						 Cookie authCookie = new Cookie(mUriHost, "ZM_AUTH_TOKEN", mAuthToken, "/", null, false);
						 initialState.addCookie(authCookie);
						 mClient.setState(initialState);
					 } catch (URISyntaxException e) {
						 // TODO: how to handle this?
					 }

				 }

				 for (int attempt = 0; statusCode == -1 && attempt < mRetryCount; attempt++) {
					 try {
						 // execute the method.
						 statusCode = mClient.executeMethod(method);
					 } catch (HttpRecoverableException e) {
						 if (attempt == mRetryCount - 1)
							 throw e;
						 System.err.println("A recoverable exception occurred, retrying." + e.getMessage());
					 }
				 }

				 // Read the response body.  Use the stream API instead of the byte[] one
				 // to avoid HTTPClient whining about a large response.
				 byte[] responseBody = ByteUtil.getContent(method.getResponseBodyAsStream(), (int) method.getResponseContentLength());

				 // Deal with the response.
				 // Use caution: ensure correct character encoding and is not binary data
				 String responseStr = SoapProtocol.toString(responseBody);

				 try {
					 return parseSoapResponse(responseStr, raw);
				 } catch (SoapFaultException x) {
					 //attach request/response to the exception and rethrow for downstream consumption
					 x.setFaultRequest(soapMessage);
					 x.setFaultResponse(responseStr);
					 throw x;
				 }
			 } finally {
				 // Release the connection.
				 if (method != null)
					 method.releaseConnection();        
			 }
		 }

		@Override
        protected Element parseSoapResponse(String envelopeStr, boolean raw) throws SoapParseException, SoapFaultException {
			 Element env;
			 try {
				 if (envelopeStr.trim().startsWith("<")) {
				    logger.debug("envelopeStr: " + envelopeStr);
					 env = Element.parseXML(envelopeStr);
				 } else {
					 env = Element.parseJSON(envelopeStr);
				 }
			 } catch (XmlParseException e) {
				 throw new SoapParseException("unable to parse response", envelopeStr);
			 }

			 //if (mDebugListener != null) mDebugListener.receiveSoapMessage(env);

			 return raw ? env : extractBodyElement(env);
		 }


	}
	
	/**
	 * @param args
	 * @throws HarnessException 
	 */
	public static void main(String[] args) throws HarnessException {


		String domain = ZmailSeleniumProperties.getStringProperty("server.host","qa60.lab.zmail.com");

		// Configure log4j using the basic configuration
		BasicConfigurator.configure();

		// Create a new account object
		ZmailAccount account = new ZmailAccount("foo"+System.currentTimeMillis(), domain);

		// Provision it on the server
		account.provision();

		// Get the SOAP authToken
		account.authenticate();

		// Send a basic SOAP request.  Check the response.
		account.soapSend("<NoOpRequest xmlns='urn:zmailMail'/>");
		if ( !account.soapMatch("//mail:NoOpResponse", null, null) )
			throw new HarnessException("NoOpRequest did not return NoOpResponse");

		// Add a message to the mailbox.  Check the response
		account.soapSend(
				"<SendMsgRequest xmlns='urn:zmailMail'>" +
				"<m>" +
				"<e t='t' a='"+ account.EmailAddress +"'/>" +
				"<su>subject123</su>" +
				"<mp ct='text/plain'>" +
				"<content>content123</content>" +
				"</mp>" +
				"</m>" +
		"</SendMsgRequest>");
		if ( !account.soapMatch("//mail:SendMsgResponse", null, null) )
			throw new HarnessException("SendMsgRequest did not return SendMsgResponse");

		logger.info("Done!");

	}

}
