/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */

/**
 * @overview
 * This file contains the zmail account class.
 */

/**
 * Creates an account object containing meta info about the account.
 * @class
 * This class represents an account. This object is created primarily if a user has added sub-accounts
 * to manage (i.e. a family mailbox).
 *
 * @author Parag Shah
 *
 * @param {String}		id			the unique ID for this account
 * @param {String}		name		the email address
 * @param {Boolean}		visible		if <code>true</code>, make this account available in the overview (i.e. child accounts)
 *
 * @extends	ZmAccount
 */
ZmZmailAccount = function(id, name, visible) {

	ZmAccount.call(this, null, id, name);

	this.visible = (visible !== false);
	/**
	 * The account settings.
	 * @type	ZmSettings
	 */
	this.settings = null;
	this.trees = {};
	this.loaded = false;
	/**
	 * The account Access Control List.
	 * @type	ZmAccessControlList
	 */
	this.acl = new ZmAccessControlList();
	this.metaData = new ZmMetaData(this);
};

ZmZmailAccount.prototype = new ZmAccount;
ZmZmailAccount.prototype.constructor = ZmZmailAccount;

ZmZmailAccount.prototype.isZmZmailAccount;
ZmZmailAccount.prototype.toString = function() { return "ZmZmailAccount"; };


//
// Constants
//

/**
 * Defines the "unknown" status.
 */
ZmZmailAccount.STATUS_UNKNOWN	= "unknown";
/**
 * Defines the "offline" status.
 */
ZmZmailAccount.STATUS_OFFLINE	= "offline";
/**
 * Defines the "online" status.
 */
ZmZmailAccount.STATUS_ONLINE	= "online";
/**
 * Defines the "running" status.
 */
ZmZmailAccount.STATUS_RUNNING	= "running";
/**
 * Defines the "authentication fail" status.
 */
ZmZmailAccount.STATUS_AUTHFAIL	= "authfail";
/**
 * Defines the "error" status.
 */
ZmZmailAccount.STATUS_ERROR	= "error";

//
// Public methods
//

/**
 * Sets the name of the account.
 * 
 * @param		{String}	name	the account name
 */
ZmZmailAccount.prototype.setName =
function(name) {
	var identity = this.getIdentity();
	// TODO: If no identity and name is set, should create one!
	if (!identity) return;
	identity.name = name;
};

/**
 * Gets the name of the account.
 * 
 * @return		{String}		the account name
 */
ZmZmailAccount.prototype.getName =
function() {
	var identity = this.getIdentity();
	var name = (!identity)
		? this.settings.get(ZmSetting.DISPLAY_NAME)
		: identity.name;

	if (!name) {
		name = this.getDisplayName();
	}
	return identity.isDefault && name == ZmIdentity.DEFAULT_NAME ? ZmMsg.accountDefault : name;
};

/**
 * Sets the email address for this account. This method does nothing. The email address is set
 * when the object is created.
 * 
 * @param	{String}	email 	the email address (ignored)
 */
ZmZmailAccount.prototype.setEmail =
function(email) {}; // IGNORE

/**
 * Gets the email address for this account.
 * 
 * @return	{String}	the email address
 */
ZmZmailAccount.prototype.getEmail =
function() {
	return this.name;
};

/**
 * Gets the display name.
 * 
 * @return	{String}	the display name
 */
ZmZmailAccount.prototype.getDisplayName =
function() {
	if (!this.displayName) {
		var dispName = this.isMain
			? this.settings.get(ZmSetting.DISPLAY_NAME)
			: this._displayName;
		this.displayName = (this._accountName || dispName || this.name);
	}
	return this.displayName;
};

/**
 * Gets the identity.
 * 
 * @return	{ZmIdentity}	the identity
 */
ZmZmailAccount.prototype.getIdentity =
function() {
	var defaultIdentity = appCtxt.getIdentityCollection(this).defaultIdentity;
	if (!appCtxt.isFamilyMbox || this.isMain) {
		return defaultIdentity;
	}

	// for family mbox, create dummy identities for child accounts
	if (!this.dummyIdentity) {
		this.dummyIdentity = new ZmIdentity(this.name);
		AjxUtil.hashUpdate(this.dummyIdentity, defaultIdentity, true, ["name","isDefault"]);
	}
	return this.dummyIdentity;
};

/**
 * Gets the tool tip.
 * 
 * @return	{String}		the tool tip
 */
ZmZmailAccount.prototype.getToolTip =
function() {
	if (this.status || this.lastSync || this.isMain) {
		var lastSyncDate = (this.lastSync && this.lastSync != 0)
			? (new Date(parseInt(this.lastSync))) : null;

		var quota = appCtxt.get(ZmSetting.QUOTA_USED, null, this);
		var lastSync;
		if (!lastSyncDate) {
			// this means, we've synced but server lost the last sync timestamp
			if (quota > 0 && !this.isMain) {
				lastSync = ZmMsg.unknown;
			}
		} else {
			lastSync = AjxDateUtil.computeWordyDateStr(new Date(), lastSyncDate);
		}

		var params = {
			lastSync: lastSync,
			hasNotSynced: this.hasNotSynced(),
			status: this.getStatusMessage(),
			quota: AjxUtil.formatSize(quota, false, 1)
		};

		return AjxTemplate.expand("share.App#ZmailAccountTooltip", params);
	}
	return "";
};

/**
 * Gets the default color.
 * 
 * @return	{String}		the default color
 * @see		ZmOrganizer
 */
ZmZmailAccount.prototype.getDefaultColor =
function() {
	if (this.isMain) {
		return ZmOrganizer.C_GRAY;
	}

	switch (this.type) {
		case ZmAccount.TYPE_GMAIL:		return ZmOrganizer.C_RED;
		case ZmAccount.TYPE_MSE:		return ZmOrganizer.C_GREEN;
		case ZmAccount.TYPE_EXCHANGE:	return ZmOrganizer.C_GREEN;
		case ZmAccount.TYPE_YMP:		return ZmOrganizer.C_PURPLE;
	}

	return null;
};

/**
 * Checks if the account has sync'd.
 * 
 * @return	{Boolean}	if <code>true</code>, this account has never been sync'd
 */
ZmZmailAccount.prototype.hasNotSynced =
function() {
	return (this.isOfflineInitialSync() && 
			this.status == ZmZmailAccount.STATUS_UNKNOWN &&
			appCtxt.get(ZmSetting.QUOTA_USED, null, this) == 0);
};

/**
 * Check is this account is currently sync'ing for the first time.
 * 
 * @return	{Boolean}	if <code>true</code>, this account is currently sync'ing for the first time
 */
ZmZmailAccount.prototype.isOfflineInitialSync =
function() {
	return (appCtxt.isOffline && (!this.lastSync || (this.lastSync && this.lastSync == 0)));
};

/**
 * Checks if this account is CalDAV based.
 * 
 * @return	{Boolean}	if <code>true</code>, account is CalDAV based
 */
ZmZmailAccount.prototype.isCalDavBased =
function() {
	return (this.type == ZmAccount.TYPE_GMAIL ||
			this.type == ZmAccount.TYPE_YMP);
};

/**
 * Gets the default calendar. For CalDAV based accounts, the default calendar is hidden;
 * therefore, this method returns the first non-default calendar.
 * 
 * @return	{Object}		the calendar
 * @see		ZmZmailAccount.isCalDavBased
 */
ZmZmailAccount.prototype.getDefaultCalendar =
function() {
	var tree = appCtxt.getFolderTree(this);
	if (this.isCalDavBased()) {
		var calendars = tree.getByType(ZmOrganizer.CALENDAR);
		for (var i = 0; i < calendars.length; i++) {
			if (calendars[i].nId == ZmOrganizer.ID_CALENDAR) { continue; }
			return calendars[i];
		}
	}
	return tree.getById(ZmOrganizer.ID_CALENDAR);
};

/**
 * Updates the account status.
 * 
 * @private
 */
ZmZmailAccount.prototype.updateState =
function(acctInfo) {
	if (this.isMain) { return; } // main account doesn't sync

	// update last sync timestamp
	var updateTooltip = false;
	if (this.lastSync != acctInfo.lastsync) {
		this.lastSync = acctInfo.lastsync;
		if (this.visible) {
			updateTooltip = true;
		}
	}

	// set to update account (offline) status if changed
	var updateStatus = false;
	if (this.status != acctInfo.status) {
		this.status = acctInfo.status;
		if (this.visible) {
			updateStatus = true;
		}
	}

	// for all overview containers, update status/tooltip
	var container = appCtxt.getOverviewController()._overviewContainer;
	for (var i in container) {
		var c = container[i];
		if (updateStatus || updateTooltip) {
			c.updateAccountInfo(this, updateStatus, updateTooltip);
		}
	}

	if (this.visible && acctInfo.unread != this.unread) {
		this.unread = acctInfo.unread;
	}

	this.code = acctInfo.code;
	if (acctInfo.error) {
		var error = acctInfo.error[0];
		this.errorDetail = error.exception[0]._content;
		this.errorMessage = error.message;
	}
};

/**
 * Gets the status icon.
 * 
 * @return	{String}	the status icon
 */
ZmZmailAccount.prototype.getStatusIcon =
function() {
	if (this.inNewMailMode) {
		return "NewMailAlert";
	}

	switch (this.status) {
//		case ZmZmailAccount.STATUS_UNKNOWN:	return "Offline"; 				// bug: 42403 - remove
		case ZmZmailAccount.STATUS_OFFLINE:	return "ImAway";
//		case ZmZmailAccount.STATUS_ONLINE:		return "";						// no icon for "online"
//		case ZmZmailAccount.STATUS_RUNNING:	// animated, so cannot be set using AjxImg
		case ZmZmailAccount.STATUS_AUTHFAIL:	return "ImDnd";
		case ZmZmailAccount.STATUS_ERROR:		return "Critical";
	}
	return null;
};

/**
 * Checks if this account is in error status.
 * 
 * @return	{Boolean}	if <code>true</code>, the account is in error status
 */
ZmZmailAccount.prototype.isError =
function() {
	return (this.status == ZmZmailAccount.STATUS_AUTHFAIL ||
			this.status == ZmZmailAccount.STATUS_ERROR);
};

/**
 * Gets the icon.
 * 
 * @return	{String}	the icon
 */
ZmZmailAccount.prototype.getIcon =
function() {
	return (this.isMain && appCtxt.isOffline) ? "LocalFolders" : this.icon;
};

/**
 * Gets the Zd message.
 * 
 * @private
 */
ZmZmailAccount.prototype.getZdMsg =
function(code) {
	var msg = ((ZdMsg["client." + code]) || (ZdMsg["exception." + code]));
	if (!msg && code) {
		msg = ZdMsg["exception.offline.UNEXPECTED"];
	}
	return msg;
};

/**
 * Gets the status message.
 * 
 * @return	{String}		the status message
 */
ZmZmailAccount.prototype.getStatusMessage =
function() {
	if (this.inNewMailMode) {
		return AjxMessageFormat.format(ZmMsg.unreadCount, this.unread);
	}

	switch (this.status) {
//		case ZmZmailAccount.STATUS_UNKNOWN:	return ZmMsg.unknown;
		case ZmZmailAccount.STATUS_OFFLINE:	return ZmMsg.imStatusOffline;
		case ZmZmailAccount.STATUS_ONLINE:		return ZmMsg.imStatusOnline;
		case ZmZmailAccount.STATUS_RUNNING:	return ZmMsg.running;
		case ZmZmailAccount.STATUS_AUTHFAIL:	return this.code ? this.getZdMsg(this.code) : AjxMessageFormat.format(ZmMsg.authFailure, this.getEmail());
		case ZmZmailAccount.STATUS_ERROR:		return this.code ? this.getZdMsg(this.code) : ZmMsg.error;
	}
	return "";
};

/**
 * Shows an error message.
 * 
 * Offline use only.
 * 
 * @private
 */
ZmZmailAccount.prototype.showErrorMessage =
function() {
	if (!this.isError()) { return; }

	var dialog = (this.status == ZmZmailAccount.STATUS_ERROR)
		? appCtxt.getErrorDialog() : appCtxt.getMsgDialog();

	// short message
	var msg = this.getZdMsg(this.code);
	if (msg == "") {
		msg = this.getStatusMessage();
	}
	dialog.setMessage(msg);

	if (this.status == ZmZmailAccount.STATUS_ERROR) {
		// detailed message
		var html = [];
		var i = 0;
		if (this.errorMessage) {
			html[i++] = "<p><b>";
			html[i++] = ZdMsg.DebugMsg;
			html[i++] = "</b>: ";
			html[i++] = this.errorMessage;
			html[i++] = "</p>";
		}

		if (this.errorDetail) {
			html[i++] = "<p><b>";
			html[i++] = ZdMsg.DebugStack;
			html[i++] = "</b>:</p><p><pre>";
			html[i++] = this.errorDetail;
			html[i++] = "</pre></p>";
		}

		html[i++] = "<p><b>";
		html[i++] = ZdMsg.DebugActionNote;
		html[i++] = "</b></p>";

		dialog.setDetailString(html.join(""));
	}

	dialog.popup(null, true);
};

/**
 * @private
 */
ZmZmailAccount.createFromDom =
function(node) {
	var acct = new ZmZmailAccount();
	acct._loadFromDom(node);
	return acct;
};

/**
 * Loads the account.
 * 
 * @param	{AjxCallback}	callback		the callback
 */
ZmZmailAccount.prototype.load =
function(callback) {
	if (!this.loaded) {
		// create new ZmSetting for this account
		this.settings = new ZmSettings();

		// check "{APP}_ENABLED" state against main account's settings
		var mainAcct = appCtxt.accountList.mainAccount;

		// for all *loaded* apps, add their app-specific settings
		for (var i = 0; i < ZmApp.APPS.length; i++) {
			var appName = ZmApp.APPS[i];
			var setting = ZmApp.SETTING[appName];
			if (setting && appCtxt.get(setting, null, mainAcct)) {
				var app = appCtxt.getApp(appName);
				if (app) {
					app._registerSettings(this.settings);
				}
			}
		}

		var command = new ZmBatchCommand(null, this.name);

		// load user settings retrieved from server now
		var loadCallback = new AjxCallback(this, this._handleLoadSettings);
		this.settings.loadUserSettings(loadCallback, null, this.name, null, command);

		// get tag info for this account *FIRST* - otherwise, root ID get overridden
		var tagDoc = AjxSoapDoc.create("GetTagRequest", "urn:zmailMail");
		var tagCallback = new AjxCallback(this, this._handleLoadTags);
		command.addNewRequestParams(tagDoc, tagCallback);

		// get meta data for this account
		this.loadMetaData(null, command);

		// get folder info for this account
		var folderDoc = AjxSoapDoc.create("GetFolderRequest", "urn:zmailMail");
		folderDoc.getMethod().setAttribute("visible", "1");
		var folderCallback = new AjxCallback(this, this._handleLoadFolders);
		command.addNewRequestParams(folderDoc, folderCallback);

		var respCallback = new AjxCallback(this, this._handleLoadUserInfo, callback);
		var errCallback = new AjxCallback(this, this._handleErrorLoad, callback);
		command.run(respCallback, errCallback);
	}
	else if (callback) {
		callback.run();
	}
};

ZmZmailAccount.prototype.loadMetaData =
function(callback, batchCommand) {
	var metaDataCallback = new AjxCallback(this, this._handleLoadMetaData, [callback]);
	var sections = [ZmSetting.M_IMPLICIT, ZmSetting.M_OFFLINE];
	this.metaData.load(sections, metaDataCallback, batchCommand);
};

/**
 * Unloads the account and removes any account-specific data stored globally.
 * 
 */
ZmZmailAccount.prototype.unload =
function() {
	if (!appCtxt.inStartup) {
		// unset account-specific shortcuts
		this.settings.loadShortcuts(true);
	}
};

/**
 * Sync the account.
 * 
 * @param	{AjxCallback}	callback		the callback
 */
ZmZmailAccount.prototype.sync =
function(callback) {
	var soapDoc = AjxSoapDoc.create("SyncRequest", "urn:zmailOffline");
	if (appCtxt.get(ZmSetting.OFFLINE_DEBUG_TRACE)) {
		var method = soapDoc.getMethod();
		method.setAttribute("debug", 1);
	}
	appCtxt.getAppController().sendRequest({
		soapDoc:soapDoc,
		asyncMode:true,
		noBusyOverlay:true,
		callback:callback,
		accountName:this.name
	});
};

/**
 * Saves the account.
 * 
 * @param	{AjxCallback}	callback		the callback
 * @param	{AjxCallback}	errorCallback		the error callback
 * @param	{Object}	batchCmd		the batch command
 */
ZmZmailAccount.prototype.save =
function(callback, errorCallback, batchCmd) {
	return (this.getIdentity().save(callback, errorCallback, batchCmd));
};

/**
 * Saves implicit prefs. Because it's done onunload, the save is sync.
 * 
 * @private
 */
ZmZmailAccount.prototype.saveImplicitPrefs =
function() {
    var isExternal = this.settings ? this.settings.get(ZmSetting.IS_EXTERNAL) : false;
    if (isExternal) {
        return;
    }
	var list = [];
	for (var id in ZmSetting.CHANGED_IMPLICIT) {
		var setting = this.settings ? this.settings.getSetting(id) : null;
		if (ZmSetting.IS_GLOBAL[setting.id] && !this.isMain) { continue; }
		if (setting && (setting.getValue(null, true) != setting.getOrigValue(null, true))) {
			list.push(setting);
		}
	}

	if (list.length > 0) {
		this.settings.save(list, null, null, this);
	}
};

/**
 * Checks if this account supports the given application name
 *
 * @param {String}		appName		the name of the application
 * @return	{Boolean}	<code>true</code> if account supports the application
 */
ZmZmailAccount.prototype.isAppEnabled =
function(appName) {
	switch (appName) {
		case ZmApp.BRIEFCASE: 	return appCtxt.get(ZmSetting.BRIEFCASE_ENABLED, null, this);
		case ZmApp.CALENDAR:	return appCtxt.get(ZmSetting.CALENDAR_ENABLED, 	null, this);
		case ZmApp.CONTACTS:	return appCtxt.get(ZmSetting.CONTACTS_ENABLED, 	null, this);
		case ZmApp.IM:			return appCtxt.get(ZmSetting.IM_ENABLED, 		null, this);
		case ZmApp.MAIL:		return appCtxt.get(ZmSetting.MAIL_ENABLED, 		null, this);
		case ZmApp.PREFERENCES:	return appCtxt.get(ZmSetting.OPTIONS_ENABLED, 	null, this);
		case ZmApp.TASKS:		return appCtxt.get(ZmSetting.TASKS_ENABLED, 	null, this);
	}
	return false;
};


//
// Protected methods
//

/**
 * @private
 */
ZmZmailAccount.prototype._handleLoadSettings =
function(result) {
	DBG.println(AjxDebug.DBG1, "Account settings successfully loaded for " + this.name);

	// set account type
	this.type = appCtxt.isOffline
		? appCtxt.get(ZmSetting.OFFLINE_ACCOUNT_FLAVOR, null, this)
		: ZmAccount.TYPE_ZIMBRA;

	this.isZmailAccount = this.type == ZmAccount.TYPE_ZIMBRA;

	// set icon now that we know the type
	switch (this.type) {
		case ZmAccount.TYPE_AOL:		this.icon = "AccountAOL"; break;
		case ZmAccount.TYPE_GMAIL:		this.icon = "AccountGmail"; break;
		case ZmAccount.TYPE_IMAP:		this.icon = "AccountIMAP"; break;
		case ZmAccount.TYPE_LIVE:		this.icon = "AccountMSN"; break;
		case ZmAccount.TYPE_MSE:		this.icon = "AccountExchange"; break;
		case ZmAccount.TYPE_EXCHANGE:	this.icon = "AccountExchange"; break;
		case ZmAccount.TYPE_POP:		this.icon = "AccountPOP"; break;
		case ZmAccount.TYPE_YMP:		this.icon = "AccountYahoo"; break;
		case ZmAccount.TYPE_ZIMBRA:		this.icon = "AccountZmail"; break;
	}

	// initialize identities/data-sources/signatures for this account
	var obj = result.getResponse().GetInfoResponse;
	appCtxt.getIdentityCollection(this).initialize(obj.identities);
	appCtxt.getDataSourceCollection(this).initialize(obj.dataSources);
	appCtxt.getSignatureCollection(this).initialize(obj.signatures);

};

/**
 * @private
 */
ZmZmailAccount.prototype._handleLoadFolders =
function(result) {
	var resp = result.getResponse().GetFolderResponse;
	var folders = resp ? resp.folder[0] : null;
	if (folders) {
		appCtxt.getRequestMgr()._loadTree(ZmOrganizer.FOLDER, null, resp.folder[0], "folder", this);
	}
};

/**
 * @private
 */
ZmZmailAccount.prototype._handleLoadTags =
function(result) {
	var resp = result.getResponse().GetTagResponse;
	appCtxt.getRequestMgr()._loadTree(ZmOrganizer.TAG, null, resp, null, this);
};

/**
 * @private
 */
ZmZmailAccount.prototype._handleLoadUserInfo =
function(callback) {
	this.loaded = true;

	// bug fix #33168 - get perms for all mountpoints in account
	var folderTree = appCtxt.getFolderTree(this);
	if (folderTree) {
		folderTree.getPermissions({noBusyOverlay:true, accountName:this.name});
	}

	if (callback) {
		callback.run();
	}
};

/**
 * @private
 */
ZmZmailAccount.prototype._handleLoadMetaData =
function(callback, sections) {
	for (var i in sections) {
		this.settings.createFromJs(sections[i]);
	}

	if (callback) {
		callback.run();
	}
};

/**
 * @private
 */
ZmZmailAccount.prototype._handleErrorLoad =
function(callback, ev) {
	DBG.println(AjxDebug.DBG1, "------- ERROR loading account settings for " + this.name);
	if (callback) {
		callback.run();
	}
};

/**
 * @private
 */
ZmZmailAccount.prototype._loadFromDom =
function(node) {
	this.id = node.id;
	this.name = node.name;
	this.visible = node.visible;
	this.active = node.active;

	var data = node.attrs && node.attrs._attrs;
	this._displayName = data ? data.displayName : this.email;
	this._accountName = data && data.zmailPrefLabel;
};
