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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.zmail.common.soap.Element;



public class ZmailResource extends ZmailAccount {
	private static Logger logger = LogManager.getLogger(ZmailResource.class);

	public static enum Type {
		LOCATION,
		EQUIPMENT
	};
	
	public Type ResourceType;

	public ZmailResource(Type type) {
		this(type, null, null);
	}
	
	public ZmailResource(Type type, String email, String password) {
		super(email, password);
		
		ResourceType = type;
		        
        provision();
        authenticate();
 
		
	}
	
	// Set the default Equipment settings
	@SuppressWarnings("serial")
	private static final Map<String, String> equipmentAttrs = new HashMap<String, String>() {{
		put("zmailPrefLocale", ZmailSeleniumProperties.getStringProperty("locale"));
		put("zmailCalResAutoAcceptDecline", "TRUE");
		put("zmailCalResAutoDeclineIfBusy", "TRUE");
	}};

	// Set the default Location settings
	@SuppressWarnings("serial")
	private static final Map<String, String> locationAttrs = new HashMap<String, String>() {{
		put("zmailPrefLocale", ZmailSeleniumProperties.getStringProperty("locale"));
		put("zmailCalResAutoAcceptDecline", "TRUE");
		put("zmailCalResAutoDeclineIfBusy", "TRUE");
	}};


	public boolean exists() throws HarnessException {
		
		// Check if the account exists
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<GetCalendarResourceRequest xmlns='urn:zmailAdmin'>" +
					"<calresource by='name'>"+ EmailAddress +"</calresource>" +
				"</GetCalendarResourceRequest>");
		
		Element[] getCalendarResourceResponse = ZmailAdminAccount.GlobalAdmin().soapSelectNodes("//admin:GetCalendarResourceResponse");
		
		if ( (getCalendarResourceResponse == null) || (getCalendarResourceResponse.length == 0) ) {
			
			logger.debug("Resource does not exist");
			return (false);
			
		}
		
		// Reset the account settings based on the response
		ZmailId = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:calresource", "id");
		ZmailMailHost = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:calresource/admin:a[@n='zmailMailHost']", null);

		// If the adminHost value is set, use that value for the ZmailMailHost
		String adminHost = ZmailSeleniumProperties.getStringProperty("adminHost", null);
		if ( adminHost != null ) {
			ZmailMailHost = adminHost;
		}

		return (true);
	}

	public ZmailAccount provision() {
		try {
			
			// Check if the account already exists
			// If yes, don't provision again
			//
			if ( exists() ) {
				logger.info(EmailAddress + " already exists.  Not provisioning again.");
				return (this);
			}

			
			// Make sure the domain exists
			ZmailDomain domain = new ZmailDomain(EmailAddress.split("@")[1]);
			domain.provision();
			
			
			
			Map<String, String> attrs = null;
			StringBuilder prefs = null;
			if ( ResourceType == Type.EQUIPMENT ) {
				prefs = new StringBuilder("<a n='zmailCalResType'>Equipment</a>");
				attrs = equipmentAttrs;
			} else if ( ResourceType == Type.LOCATION ) {
				prefs = new StringBuilder("<a n='zmailCalResType'>Location</a>");
				attrs = locationAttrs;
			} else {
				throw new HarnessException("Unknown resource type "+ ResourceType);
			}
			
			// Build the list of default preferences
    		for (Map.Entry<String, String> entry : attrs.entrySet()) {
    			prefs.append(String.format("<a n='%s'>%s</a>", entry.getKey(), entry.getValue()));
    		}

			ZmailAdminAccount.GlobalAdmin().soapSend(
					"<CreateCalendarResourceRequest xmlns='urn:zmailAdmin'>" +
						"<name>" + EmailAddress + "</name>" +
						"<password>" + Password + "</password>" +
						"<a n='displayName'>"+ EmailAddress +"</a>" +
						prefs.toString() +
					"</CreateCalendarResourceRequest>");
			
			Element[] createCalendarResourceResponse = ZmailAdminAccount.GlobalAdmin().soapSelectNodes("//admin:CreateCalendarResourceResponse");
			
			if ( (createCalendarResourceResponse == null) || (createCalendarResourceResponse.length == 0)) {

				Element[] soapFault = ZmailAdminAccount.GlobalAdmin().soapSelectNodes("//soap:Fault");
				if ( soapFault != null && soapFault.length > 0 ) {
				
					String error = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//zmail:Code", null);
					throw new HarnessException("Unable to create account: "+ error);
					
				}
				
				throw new HarnessException("Unknown error when provisioning account");
				
			}


			// Set the account settings based on the response
			ZmailId = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:calresource", "id");
			ZmailMailHost = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:calresource/admin:a[@n='zmailMailHost']", null);

			// If the adminHost value is set, use that value for the ZmailMailHost
			String adminHost = ZmailSeleniumProperties.getStringProperty("adminHost", null);
			if ( adminHost != null ) {
				ZmailMailHost = adminHost;
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

