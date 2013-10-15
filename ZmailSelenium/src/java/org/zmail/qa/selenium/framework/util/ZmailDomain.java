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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.zmail.common.soap.Element;






public class ZmailDomain {
	private static Logger logger = LogManager.getLogger(ZmailDomain.class);

	
	// Domain information
	protected String DomainName = null;
	
	// GAL Sync account information
	protected String DomainGalSyncAccountID = null;
	protected String DomainGalSyncDatasourceID = null;
	
	
	
	
	/**
	 * 
	 * @param name
	 */
	public ZmailDomain(String name) {
		DomainName = name;
	}
	
	
	/**
	 * Does the domain exist already?
	 * @return
	 * @throws HarnessException
	 */
	public boolean exists() throws HarnessException {
		
		// Check if the domain exists
		ZmailAdminAccount.GlobalAdmin().soapSend(
					"<GetDomainRequest xmlns='urn:zmailAdmin'>"
				+		"<domain by='name'>"+ DomainName +"</domain>"
				+	"</GetDomainRequest>");
		
		Element response = ZmailAdminAccount.GlobalAdmin().soapSelectNode("//admin:GetDomainResponse",  1);
		
		// If the domain exists, there will be an id
		if ( response == null ) {
			return (false);
		}
		
		// If there was a response, make sure we have the up to date information
		DomainGalSyncAccountID = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:GetDomainResponse//admin:a[@n='zmailGalAccountId']", null);
		logger.info("DomainGalSyncAccountID="+DomainGalSyncAccountID);
		
		
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<GetDataSourcesRequest xmlns='urn:zmailAdmin'>"
			+		"<id>"+ DomainGalSyncAccountID +"</id>"
			+	"</GetDataSourcesRequest>");

		DomainGalSyncDatasourceID = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:GetDataSourcesResponse//admin:dataSource", "id");	
		logger.info("DomainGalSyncDatasourceID="+DomainGalSyncDatasourceID);

		return (true);

	}
	
	
	/**
	 * Create the domain
	 * @throws HarnessException
	 */
	public void provision() throws HarnessException {
		
		if ( exists() ) {
			logger.info(DomainName + " already exists.  Not provisioning again.");
			return;
		}
		
		// If the domain does not exist, create it
		ZmailAdminAccount.GlobalAdmin().soapSend(
					"<CreateDomainRequest xmlns='urn:zmailAdmin'>"
				+		"<name>"+ DomainName +"</name>"
				+		"<a n='zmailGalMode'>zmail</a>"
				+		"<a n='zmailGalMaxResults'>15</a>"
				+	"</CreateDomainRequest>");
		
		
		this.createGalSyncAccount();
		
		this.syncGalAccount();
		
	}
	
	
	/**
	 * Create the GAL sync account for this domain
	 * @throws HarnessException
	 */
	public void createGalSyncAccount() throws HarnessException {
		
		// Create the Sync GAL Account
		String galaccount = "galaccount"+ ZmailSeleniumProperties.getUniqueString() + "@"+ DomainName;
		String datasourcename = "datasource" + ZmailSeleniumProperties.getUniqueString();
		
		
		ZmailAdminAccount.GlobalAdmin().soapSend(
					"<CreateGalSyncAccountRequest xmlns='urn:zmailAdmin' name='"+ datasourcename + "' type='zmail' domain='"+ DomainName +"' >"
				+		"<account by='name'>"+ galaccount +"</account>"
				+		"<password>"+ ZmailSeleniumProperties.getStringProperty("adminPwd", "test123") +"</password>"
				+	"</CreateGalSyncAccountRequest>");
		
		DomainGalSyncAccountID = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:CreateGalSyncAccountResponse/admin:account", "id");
		logger.info("DomainGalSyncAccountID="+ DomainGalSyncAccountID);
		
		
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<GetDataSourcesRequest xmlns='urn:zmailAdmin'>"
			+		"<id>"+ DomainGalSyncAccountID +"</id>"
			+	"</GetDataSourcesRequest>");

		DomainGalSyncDatasourceID = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:GetDataSourcesResponse//admin:dataSource", "id");
		logger.info("DomainGalSyncDatasourceID="+ DomainGalSyncDatasourceID);

	}
	
	/**
	 * Sync the GAL for this domain
	 * @throws HarnessException
	 */
	public void syncGalAccount() throws HarnessException {
		
		// Sync the GAL Account
		ZmailAdminAccount.GlobalAdmin().soapSend(
					"<SyncGalAccountRequest xmlns='urn:zmailAdmin'>"
				+		"<account id='"+ DomainGalSyncAccountID +"'>"
				+			"<datasource by='id' fullSync='true' reset='true'>"+ DomainGalSyncDatasourceID +"</datasource>"
				+		"</account>"
				+	"</SyncGalAccountRequest>");
		String response = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:SyncGalAccountResponse", null);
		
		if ( response == null) {
			throw new HarnessException("Unable to sync GAL account");
		}

	}


}
