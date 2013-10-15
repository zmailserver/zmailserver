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
/**
 * 
 */
package org.zmail.qa.selenium.projects.octopus.ui;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.projects.octopus.ui.PageOctopus;

public class AppOctopusClient extends AbsApplication {

	// Pages
	public PageLogin zPageLogin = null;
	public PageOctopus zPageOctopus = null;
	public PageMyFiles zPageMyFiles = null;
	public PageSharing zPageSharing = null;
	public PageFavorites zPageFavorites = null;
	public PageHistory zPageHistory = null;
	public PageTrash zPageTrash = null;
	public PageSearch zPageSearch = null;

	public AppOctopusClient() {
		super();

		logger.info("new " + AppOctopusClient.class.getCanonicalName());

		// Login page
		zPageLogin = new PageLogin(this);
		pages.put(zPageLogin.myPageName(), zPageLogin);

		// Main Octopus page
		zPageOctopus = new PageOctopus(this);
		pages.put(zPageOctopus.myPageName(), zPageOctopus);

		// My Files page
		zPageMyFiles = new PageMyFiles(this);
		pages.put(zPageMyFiles.myPageName(), zPageMyFiles);

		// Sharing page
		zPageSharing = new PageSharing(this);
		pages.put(zPageSharing.myPageName(), zPageSharing);

		// Favorites page
		zPageFavorites = new PageFavorites(this);
		pages.put(zPageFavorites.myPageName(), zPageFavorites);

		// History page
		zPageHistory = new PageHistory(this);
		pages.put(zPageHistory.myPageName(), zPageHistory);

		// Trash page
		zPageTrash = new PageTrash(this);
		pages.put(zPageTrash.myPageName(), zPageTrash);

		// Search page
		zPageSearch = new PageSearch(this);
		pages.put(zPageSearch.myPageName(), zPageSearch);

		// Configure the localization strings
		getL10N().zAddBundlename(I18N.Catalog.I18nMsg);
		getL10N().zAddBundlename(I18N.Catalog.AjxMsg);
		getL10N().zAddBundlename(I18N.Catalog.ZMsg);
		getL10N().zAddBundlename(I18N.Catalog.ZsMsg);
		getL10N().zAddBundlename(I18N.Catalog.ZmMsg);
	}

	@Override
	public boolean zIsLoaded() throws HarnessException {
		if (this.zPageOctopus.zIsActive() || this.zPageLogin.zIsActive()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String myApplicationName() {
		return ("Octopus Client");
	}

	public ZmailAccount zSetActiveAcount(ZmailAccount account)
			throws HarnessException {
		return (super.zSetActiveAcount(account));
	}

	public ZmailAccount zGetActiveAcount() {
		return (super.zGetActiveAccount());
	}
}
