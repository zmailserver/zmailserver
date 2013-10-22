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
package org.zmail.qa.selenium.projects.admin.ui;

import org.zmail.qa.selenium.framework.ui.AbsApplication;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailAccount;


/**
 * This class defines the Admin Console application
 * @author Matt Rhoades
 *
 */
public class AppAdminConsole extends AbsApplication {

	
	// Login page
	public PageLogin					zPageLogin = null;
	
	// General pages (top menu, overview, search
	public PageMain						zPageMain = null;
	public PageSearchResults			zPageSearchResults = null;

	// Addresses
	public PageManageAccounts			zPageManageAccounts = null;
	public PageEditAccount				zPageEditAccount = null;
	public PageManageAliases			zPageManageAliases = null;
	public PageEditAlias				zPageEditAlias = null;
	public PageManageDistributionLists	zPageManageDistributionList = null;
	public PageEditDistributionList		zPageEditDistributionList = null;
	public PageManageResources			zPageManageResources = null;
	public PageEditResource				zPageEditResource = null;
	
	// Configuration
	public PageManageCOS				zPageManageCOS = null;
	public PageEditCOS					zPageEditCOS = null;
	public PageManageDomains			zPageManageDomains = null;
	public PageEditDomain				zPageEditDomain = null;
	public PageManageServers			zPageManageServers = null;
	public PageEditServer				zPageEditServer = null;
	public PageManageZimlets			zPageManageZimlets = null;
	public PageEditZimlet				zPageEditZimlet = null;
	public PageManageAdminExtensions	zPageManageAdminExtensions = null;
	public PageEditAdminExtension		zPageEditAdminExtension = null;
	public PageManageGlobalSettings		zPageManageGlobalSettings = null;
	public PageManageRights				zPageManageRights = null;
	public PageManageGlobalACL			zPageManageACL = null;
	public PageManageVoiceChatService	zPageManageVoiceChatService = null;
	
	// Monitoring
	public PageManageServerStatus		zPageManageServerStatus = null;
	public PageManageServerStats		zPageManageServerStats = null;
	
	// Tools
	public PageManageMailQueues			zPageManageMailQueues = null;
	public PageEditMailQueue			zPageEditMailQueue = null;
	public PageManageAccountMigration	zPageManageAccountMigration = null;
	public PageManageCertificates		zPageManageCertificates = null;
	public PageManageClientUpload		zPageManageClientUpload = null;
	public PageEditCertificate			zPageEditCertificate = null;
	public PageManageSoftwareUpdates	zPageManageSoftwareUpdates = null;
	public PageManageSearchMail			zPageManageSearchMail = null;
	public PageEditSearchTask			zPageEditSearchTask = null;
	public PageManageBackups			zPageManageBackups = null;
	public PageEditBackup				zPageEditBackup = null;
	
	// Searches
	public PageEditSearch				zPageEditSearch = null;
	
	// Downloads page (http://server.org/zmail/downloads/index.html
	public PageDownloads				zPageDownloads = null;
	
	public AppAdminConsole() {
		super();
		
		logger.info("new " + AppAdminConsole.class.getCanonicalName());

		
		// Login page
		
		zPageLogin = new PageLogin(this);
		pages.put(zPageLogin.myPageName(), zPageLogin);
		
		
		// Main pages
		
		zPageMain = new PageMain(this);
		pages.put(zPageMain.myPageName(), zPageMain);
		
		zPageSearchResults = new PageSearchResults(this);
		pages.put(zPageSearchResults.myPageName(), zPageSearchResults);
		
		
		// Addresses
		
		zPageManageAccounts = new PageManageAccounts(this);
		pages.put(zPageManageAccounts.myPageName(), zPageManageAccounts);
		
		zPageEditAccount = new PageEditAccount(this);
		pages.put(zPageEditAccount.myPageName(), zPageEditAccount);
		
		zPageManageAliases = new PageManageAliases(this);
		pages.put(zPageManageAliases.myPageName(), zPageManageAliases);
		
		zPageEditAlias = new PageEditAlias(this);
		pages.put(zPageEditAlias.myPageName(), zPageEditAlias);
		
		zPageManageDistributionList = new PageManageDistributionLists(this);
		pages.put(zPageManageDistributionList.myPageName(), zPageManageDistributionList);
		
		zPageEditDistributionList = new PageEditDistributionList(this);
		pages.put(zPageEditDistributionList.myPageName(), zPageEditDistributionList);
		
		zPageManageResources = new PageManageResources(this);
		pages.put(zPageManageResources.myPageName(), zPageManageResources);
		
		zPageEditResource = new PageEditResource(this);
		pages.put(zPageEditResource.myPageName(), zPageEditResource);
		
		
		// Configuration pages
		
		zPageManageCOS = new PageManageCOS(this);
		pages.put(zPageManageCOS.myPageName(), zPageManageCOS);
		
		zPageEditCOS = new PageEditCOS(this);
		pages.put(zPageEditCOS.myPageName(), zPageEditCOS);
		
		zPageManageDomains = new PageManageDomains(this);
		pages.put(zPageManageDomains.myPageName(), zPageManageDomains);
		
		zPageEditDomain = new PageEditDomain(this);
		pages.put(zPageEditDomain.myPageName(), zPageEditDomain);
		
		zPageManageServers = new PageManageServers(this);
		pages.put(zPageManageServers.myPageName(), zPageManageServers);
		
		zPageEditServer = new PageEditServer(this);
		pages.put(zPageEditServer.myPageName(), zPageEditServer);
		
		zPageManageZimlets = new PageManageZimlets(this);
		pages.put(zPageManageZimlets.myPageName(), zPageManageZimlets);
		
		zPageEditZimlet = new PageEditZimlet(this);
		pages.put(zPageEditZimlet.myPageName(), zPageEditZimlet);
		
		zPageManageAdminExtensions = new PageManageAdminExtensions(this);
		pages.put(zPageManageAdminExtensions.myPageName(), zPageManageAdminExtensions);
		
		zPageEditAdminExtension = new PageEditAdminExtension(this);
		pages.put(zPageEditAdminExtension.myPageName(), zPageEditAdminExtension);
		
		zPageManageGlobalSettings = new PageManageGlobalSettings(this);
		pages.put(zPageManageGlobalSettings.myPageName(), zPageManageGlobalSettings);
		
		zPageManageRights = new PageManageRights(this);
		pages.put(zPageManageRights.myPageName(), zPageManageRights);
		
		zPageManageACL = new PageManageGlobalACL(this);
		pages.put(zPageManageACL.myPageName(), zPageManageACL);
		
		zPageManageVoiceChatService = new PageManageVoiceChatService(this);
		pages.put(zPageManageVoiceChatService.myPageName(), zPageManageVoiceChatService);

		
		// Monitoring

		zPageManageServerStatus = new PageManageServerStatus(this);
		pages.put(zPageManageServerStatus.myPageName(), zPageManageServerStatus);
		
		zPageManageServerStats = new PageManageServerStats(this);
		pages.put(zPageManageServerStats.myPageName(), zPageManageServerStats);

		
		// Tools
		
		zPageManageServerStats = new PageManageServerStats(this);
		pages.put(zPageManageServerStats.myPageName(), zPageManageServerStats);

		zPageManageMailQueues = new PageManageMailQueues(this);
		pages.put(zPageManageMailQueues.myPageName(), zPageManageMailQueues);

		zPageEditMailQueue = new PageEditMailQueue(this);
		pages.put(zPageEditMailQueue.myPageName(), zPageEditMailQueue);

		zPageManageAccountMigration = new PageManageAccountMigration(this);
		pages.put(zPageManageAccountMigration.myPageName(), zPageManageAccountMigration);

		zPageManageCertificates = new PageManageCertificates(this);
		pages.put(zPageManageCertificates.myPageName(), zPageManageCertificates);
		
		zPageManageClientUpload = new PageManageClientUpload(this);
		pages.put(zPageManageClientUpload.myPageName(), zPageManageClientUpload);

		zPageEditCertificate = new PageEditCertificate(this);
		pages.put(zPageEditCertificate.myPageName(), zPageEditCertificate);

		zPageManageSoftwareUpdates = new PageManageSoftwareUpdates(this);
		pages.put(zPageManageSoftwareUpdates.myPageName(), zPageManageSoftwareUpdates);

		zPageManageSearchMail = new PageManageSearchMail(this);
		pages.put(zPageManageSearchMail.myPageName(), zPageManageSearchMail);

		zPageEditSearchTask = new PageEditSearchTask(this);
		pages.put(zPageEditSearchTask.myPageName(), zPageEditSearchTask);

		zPageManageBackups = new PageManageBackups(this);
		pages.put(zPageManageBackups.myPageName(), zPageManageBackups);

		zPageEditBackup = new PageEditBackup(this);
		pages.put(zPageEditBackup.myPageName(), zPageEditBackup);

		
		// Searches
		
		zPageEditSearch = new PageEditSearch(this);
		pages.put(zPageEditSearch.myPageName(), zPageEditSearch);


		// Downloads
		
		zPageDownloads = new PageDownloads(this);
		pages.put(zPageDownloads.myPageName(), zPageDownloads);
		
	}


	@Override
	public boolean zIsLoaded() throws HarnessException {
		// TODO: how to determine if the current browser app is the AdminConsole
		// Maybe check the current URL?
		return (true);
	}

	@Override
	public String myApplicationName() {
		return ("Admin Console");
	}

	protected ZmailAccount zSetActiveAcount(ZmailAccount account) throws HarnessException {
		// Should we throw an exception if the account is not a ZmailAdminAccount?
		return (super.zSetActiveAcount(account));
	}


	
}
