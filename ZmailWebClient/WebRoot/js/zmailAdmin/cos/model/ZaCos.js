/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
* @class ZaCos
* Data model for zmailCos object
* @ constructor ZaCos
* @param app reference to the application instance
* @author Greg Solovyev
**/
ZaCos = function() {
	ZaItem.call(this,"ZaCos");
	this._init();
}
ZaItem.loadMethods["ZaCos"] = new Array();
ZaItem.modifyMethods["ZaCos"] = new Array();
ZaItem.initMethods["ZaCos"] = new Array();
ZaItem.getRelatedMethods["ZaCos"] = new Array();

ZaCos.prototype = new ZaItem;
ZaCos.prototype.constructor = ZaCos;

ZaCos.NONE = "none";
//object attributes
ZaCos.A_zmailNotes="zmailNotes";
ZaCos.A_zmailMailQuota="zmailMailQuota";
ZaCos.A_zmailMinPwdLength="zmailPasswordMinLength";
ZaCos.A_zmailMaxPwdLength="zmailPasswordMaxLength";
ZaCos.A_zmailPasswordMinUpperCaseChars = "zmailPasswordMinUpperCaseChars";
ZaCos.A_zmailPasswordMinLowerCaseChars = "zmailPasswordMinLowerCaseChars";
ZaCos.A_zmailPasswordMinPunctuationChars = "zmailPasswordMinPunctuationChars";
ZaCos.A_zmailPasswordMinNumericChars = "zmailPasswordMinNumericChars";
ZaCos.A_zmailPasswordMinDigitsOrPuncs = "zmailPasswordMinDigitsOrPuncs";
ZaCos.A_zmailMinPwdAge = "zmailPasswordMinAge";
ZaCos.A_zmailMaxPwdAge = "zmailPasswordMaxAge";
ZaCos.A_zmailEnforcePwdHistory ="zmailPasswordEnforceHistory";
ZaCos.A_zmailPasswordLocked = "zmailPasswordLocked";
ZaCos.A_name = "cn";
ZaCos.A_description = "description";
ZaCos.A_zmailAttachmentsBlocked = "zmailAttachmentsBlocked";
ZaCos.A_zmailQuotaWarnPercent = "zmailQuotaWarnPercent";
ZaCos.A_zmailQuotaWarnInterval = "zmailQuotaWarnInterval";
ZaCos.A_zmailQuotaWarnMessage = "zmailQuotaWarnMessage";

ZaCos.A_zmailAdminAuthTokenLifetime = "zmailAdminAuthTokenLifetime";
ZaCos.A_zmailAuthTokenLifetime = "zmailAuthTokenLifetime";
ZaCos.A_zmailMailIdleSessionTimeout = "zmailMailIdleSessionTimeout";
ZaCos.A_zmailContactMaxNumEntries = "zmailContactMaxNumEntries";
ZaCos.A_zmailMailMinPollingInterval = "zmailMailMinPollingInterval";
ZaCos.A_zmailMailMessageLifetime = "zmailMailMessageLifetime";
ZaCos.A_zmailMailTrashLifetime = "zmailMailTrashLifetime";
ZaCos.A_zmailMailSpamLifetime = "zmailMailSpamLifetime";
ZaCos.A_zmailMailHostPool = "zmailMailHostPool";
ZaCos.A_zmailAvailableSkin = "zmailAvailableSkin";
ZaCos.A_zmailZimletAvailableZimlets = "zmailZimletAvailableZimlets";
ZaCos.A_zmailMailForwardingAddressMaxLength = "zmailMailForwardingAddressMaxLength";
ZaCos.A_zmailMailForwardingAddressMaxNumAddrs = "zmailMailForwardingAddressMaxNumAddrs";
ZaCos.A_zmailPrefItemsPerVirtualPage="zmailPrefItemsPerVirtualPage",

ZaCos.A_zmailDataSourceMinPollingInterval = "zmailDataSourceMinPollingInterval";
ZaCos.A_zmailDataSourcePop3PollingInterval = "zmailDataSourcePop3PollingInterval";
ZaCos.A_zmailDataSourceImapPollingInterval = "zmailDataSourceImapPollingInterval";
ZaCos.A_zmailDataSourceCalendarPollingInterval = "zmailDataSourceCalendarPollingInterval";
ZaCos.A_zmailDataSourceRssPollingInterval = "zmailDataSourceRssPollingInterval";
ZaCos.A_zmailDataSourceCaldavPollingInterval = "zmailDataSourceCaldavPollingInterval";


ZaCos.A_zmailProxyAllowedDomains = "zmailProxyAllowedDomains";
//prefs
ZaCos.A_zmailPrefMandatorySpellCheckEnabled = "zmailPrefMandatorySpellCheckEnabled";
ZaCos.A_zmailPrefAppleIcalDelegationEnabled = "zmailPrefAppleIcalDelegationEnabled";
ZaCos.A_zmailPrefCalendarShowPastDueReminders = "zmailPrefCalendarShowPastDueReminders";
ZaCos.A_zmailPrefCalendarToasterEnabled = "zmailPrefCalendarToasterEnabled";
ZaCos.A_zmailPrefCalendarAllowCancelEmailToSelf = "zmailPrefCalendarAllowCancelEmailToSelf";
ZaCos.A_zmailPrefCalendarAllowPublishMethodInvite = "zmailPrefCalendarAllowPublishMethodInvite";
ZaCos.A_zmailPrefCalendarAllowForwardedInvite = "zmailPrefCalendarAllowForwardedInvite";
ZaCos.A_zmailPrefCalendarReminderFlashTitle = "zmailPrefCalendarReminderFlashTitle";
ZaCos.A_zmailPrefCalendarReminderSoundsEnabled = "zmailPrefCalendarReminderSoundsEnabled";
ZaCos.A_zmailPrefCalendarSendInviteDeniedAutoReply = "zmailPrefCalendarSendInviteDeniedAutoReply";
ZaCos.A_zmailPrefCalendarAutoAddInvites = "zmailPrefCalendarAutoAddInvites";
ZaCos.A_zmailPrefCalendarApptVisibility = "zmailPrefCalendarApptVisibility";
ZaCos.A_zmailPrefCalendarNotifyDelegatedChanges = "zmailPrefCalendarNotifyDelegatedChanges";
ZaCos.A_zmailPrefCalendarInitialView = "zmailPrefCalendarInitialView";
ZaCos.A_zmailPrefClientType = "zmailPrefClientType";
ZaCos.A_zmailPrefTimeZoneId = "zmailPrefTimeZoneId";
ZaCos.A_zmailAllowAnyFromAddress = "zmailAllowAnyFromAddress";
ZaCos.A_zmailPrefCalendarAlwaysShowMiniCal = "zmailPrefCalendarAlwaysShowMiniCal";
ZaCos.A_zmailPrefCalendarUseQuickAdd = "zmailPrefCalendarUseQuickAdd";
ZaCos.A_zmailPrefGroupMailBy = "zmailPrefGroupMailBy";
ZaCos.A_zmailPrefIncludeSpamInSearch = "zmailPrefIncludeSpamInSearch";
ZaCos.A_zmailPrefIncludeTrashInSearch = "zmailPrefIncludeTrashInSearch";
ZaCos.A_zmailPrefMailInitialSearch = "zmailPrefMailInitialSearch";
ZaCos.A_zmailMaxMailItemsPerPage = "zmailMaxMailItemsPerPage";
ZaCos.A_zmailPrefMailItemsPerPage = "zmailPrefMailItemsPerPage";
ZaCos.A_zmailPrefMailPollingInterval = "zmailPrefMailPollingInterval";
ZaCos.A_zmailPrefAutoSaveDraftInterval = "zmailPrefAutoSaveDraftInterval";
ZaCos.A_zmailPrefMailFlashTitle = "zmailPrefMailFlashTitle";
ZaCos.A_zmailPrefMailFlashIcon = "zmailPrefMailFlashIcon" ;
ZaCos.A_zmailPrefMailSoundsEnabled = "zmailPrefMailSoundsEnabled" ;
ZaCos.A_zmailPrefMailToasterEnabled = "zmailPrefMailToasterEnabled";
ZaCos.A_zmailPrefMessageIdDedupingEnabled = "zmailPrefMessageIdDedupingEnabled";
ZaCos.A_zmailPrefUseKeyboardShortcuts = "zmailPrefUseKeyboardShortcuts";
ZaCos.A_zmailPrefSaveToSent = "zmailPrefSaveToSent";
ZaCos.A_zmailPrefComposeInNewWindow = "zmailPrefComposeInNewWindow";
ZaCos.A_zmailPrefForwardReplyInOriginalFormat = "zmailPrefForwardReplyInOriginalFormat";
ZaCos.A_zmailPrefAutoAddAddressEnabled = "zmailPrefAutoAddAddressEnabled";
ZaCos.A_zmailPrefComposeFormat = "zmailPrefComposeFormat";
ZaCos.A_zmailPrefMessageViewHtmlPreferred = "zmailPrefMessageViewHtmlPreferred";
ZaCos.A_zmailPrefShowSearchString = "zmailPrefShowSearchString";
//ZaCos.A_zmailPrefMailSignatureStyle = "zmailPrefMailSignatureStyle";
ZaCos.A_zmailPrefUseTimeZoneListInCalendar = "zmailPrefUseTimeZoneListInCalendar";
ZaCos.A_zmailPrefImapSearchFoldersEnabled = "zmailPrefImapSearchFoldersEnabled";
ZaCos.A_zmailPrefCalendarApptReminderWarningTime = "zmailPrefCalendarApptReminderWarningTime";
ZaCos.A_zmailPrefSkin = "zmailPrefSkin";
ZaCos.A_zmailPrefGalAutoCompleteEnabled = "zmailPrefGalAutoCompleteEnabled";
ZaCos.A_zmailPrefWarnOnExit = "zmailPrefWarnOnExit" ;
ZaCos.A_zmailPrefShowSelectionCheckbox = "zmailPrefShowSelectionCheckbox" ;
ZaCos.A_zmailPrefHtmlEditorDefaultFontFamily = "zmailPrefHtmlEditorDefaultFontFamily";
ZaCos.A_zmailPrefHtmlEditorDefaultFontSize = "zmailPrefHtmlEditorDefaultFontSize" ;
ZaCos.A_zmailPrefHtmlEditorDefaultFontColor = "zmailPrefHtmlEditorDefaultFontColor" ;
ZaCos.A_zmailMailSignatureMaxLength = "zmailMailSignatureMaxLength" ;
ZaCos.A_zmailPrefDisplayExternalImages = "zmailPrefDisplayExternalImages" ;
ZaCos.A_zmailPrefOutOfOfficeCacheDuration = "zmailPrefOutOfOfficeCacheDuration";
//ZaCos.A_zmailPrefIMAutoLogin = "zmailPrefIMAutoLogin";
ZaCos.A_zmailPrefMailDefaultCharset = "zmailPrefMailDefaultCharset";
ZaCos.A_zmailPrefLocale = "zmailPrefLocale" ;
ZaCos.A_zmailJunkMessagesIndexingEnabled = "zmailJunkMessagesIndexingEnabled";
ZaCos.A_zmailPrefMailSendReadReceipts = "zmailPrefMailSendReadReceipts";
ZaCos.A_zmailPrefAdminConsoleWarnOnExit = "zmailPrefAdminConsoleWarnOnExit" ;

//features
ZaCos.A_zmailFeatureExportFolderEnabled = "zmailFeatureExportFolderEnabled";
ZaCos.A_zmailFeatureImportFolderEnabled = "zmailFeatureImportFolderEnabled";
ZaCos.A_zmailDumpsterEnabled = "zmailDumpsterEnabled";
ZaCos.A_zmailMailDumpsterLifetime = "zmailMailDumpsterLifetime";
ZaCos.A_zmailDumpsterUserVisibleAge = "zmailDumpsterUserVisibleAge";
ZaCos.A_zmailDumpsterPurgeEnabled = "zmailDumpsterPurgeEnabled";
ZaCos.A_zmailPrefCalendarFirstDayOfWeek = "zmailPrefCalendarFirstDayOfWeek"; 
ZaCos.A_zmailFeatureReadReceiptsEnabled = "zmailFeatureReadReceiptsEnabled";
ZaCos.A_zmailFeatureMailPriorityEnabled = "zmailFeatureMailPriorityEnabled";
ZaCos.A_zmailFeatureIMEnabled = "zmailFeatureIMEnabled";
ZaCos.A_zmailFeatureInstantNotify = "zmailFeatureInstantNotify";
ZaCos.A_zmailFeatureImapDataSourceEnabled = "zmailFeatureImapDataSourceEnabled";
ZaCos.A_zmailFeaturePop3DataSourceEnabled = "zmailFeaturePop3DataSourceEnabled";
ZaCos.A_zmailFeatureIdentitiesEnabled = "zmailFeatureIdentitiesEnabled";
ZaCos.A_zmailFeatureContactsEnabled="zmailFeatureContactsEnabled";
ZaCos.A_zmailFeatureCalendarEnabled="zmailFeatureCalendarEnabled";
ZaCos.A_zmailFeatureTasksEnabled = "zmailFeatureTasksEnabled" ;
ZaCos.A_zmailFeatureTaggingEnabled="zmailFeatureTaggingEnabled";
ZaCos.A_zmailFeaturePeopleSearchEnabled="zmailFeaturePeopleSearchEnabled";
ZaCos.A_zmailFeatureAdvancedSearchEnabled="zmailFeatureAdvancedSearchEnabled";
ZaCos.A_zmailFeatureSavedSearchesEnabled="zmailFeatureSavedSearchesEnabled";
ZaCos.A_zmailFeatureConversationsEnabled="zmailFeatureConversationsEnabled";
ZaCos.A_zmailFeatureChangePasswordEnabled="zmailFeatureChangePasswordEnabled";
ZaCos.A_zmailFeatureInitialSearchPreferenceEnabled="zmailFeatureInitialSearchPreferenceEnabled";
ZaCos.A_zmailFeatureFiltersEnabled="zmailFeatureFiltersEnabled";
ZaCos.A_zmailFeatureGalEnabled="zmailFeatureGalEnabled";
ZaCos.A_zmailFeatureMAPIConnectorEnabled = "zmailFeatureMAPIConnectorEnabled";
ZaCos.A_zmailFeatureMailForwardingEnabled = "zmailFeatureMailForwardingEnabled";
ZaCos.A_zmailFeatureMailSendLaterEnabled = "zmailFeatureMailSendLaterEnabled";
//ZaCos.A_zmailFeatureFreeBusyViewEnabled = "zmailFeatureFreeBusyViewEnabled";
ZaCos.A_zmailFeatureSharingEnabled="zmailFeatureSharingEnabled";
ZaCos.A_zmailExternalSharingEnabled = "zmailExternalSharingEnabled";
ZaCos.A_zmailPublicSharingEnabled = "zmailPublicSharingEnabled";
ZaCos.A_zmailFeatureCalendarReminderDeviceEmailEnabled = "zmailFeatureCalendarReminderDeviceEmailEnabled";
//ZaCos.A_zmailFeatureNotebookEnabled="zmailFeatureNotebookEnabled"
ZaCos.A_zmailFeatureBriefcasesEnabled="zmailFeatureBriefcasesEnabled";
ZaCos.A_zmailFeatureBriefcaseDocsEnabled = "zmailFeatureBriefcaseDocsEnabled";
ZaCos.A_zmailImapEnabled = "zmailImapEnabled";
ZaCos.A_zmailPop3Enabled = "zmailPop3Enabled";
ZaCos.A_zmailFeatureHtmlComposeEnabled = "zmailFeatureHtmlComposeEnabled";
ZaCos.A_zmailFeatureGalAutoCompleteEnabled = "zmailFeatureGalAutoCompleteEnabled";
ZaCos.A_zmailFeatureManageZimlets = "zmailFeatureManageZimlets";
ZaCos.A_zmailFeatureSkinChangeEnabled = "zmailFeatureSkinChangeEnabled";
ZaCos.A_zmailFeatureOutOfOfficeReplyEnabled = "zmailFeatureOutOfOfficeReplyEnabled";
ZaCos.A_zmailFeatureNewMailNotificationEnabled = "zmailFeatureNewMailNotificationEnabled";
ZaCos.A_zmailFeatureMailPollingIntervalPreferenceEnabled = "zmailFeatureMailPollingIntervalPreferenceEnabled" ;
ZaCos.A_zmailFeatureOptionsEnabled = "zmailFeatureOptionsEnabled" ;
//ZaCos.A_zmailFeatureShortcutAliasesEnabled = "zmailFeatureShortcutAliasesEnabled" ;
ZaCos.A_zmailFeatureMailEnabled = "zmailFeatureMailEnabled";
ZaCos.A_zmailFeatureGroupCalendarEnabled = "zmailFeatureGroupCalendarEnabled";
ZaCos.A_zmailFeatureFlaggingEnabled = "zmailFeatureFlaggingEnabled" ;
ZaCos.A_zmailFeatureManageSMIMECertificateEnabled = "zmailFeatureManageSMIMECertificateEnabled";
ZaCos.A_zmailFeatureSMIMEEnabled = "zmailFeatureSMIMEEnabled";
ZaCos.A_zmailFeatureDistributionListFolderEnabled = "zmailFeatureDistributionListFolderEnabled";

//security
ZaCos.A_zmailPasswordLockoutEnabled = "zmailPasswordLockoutEnabled";
ZaCos.A_zmailPasswordLockoutDuration = "zmailPasswordLockoutDuration";
ZaCos.A_zmailPasswordLockoutMaxFailures = "zmailPasswordLockoutMaxFailures";
ZaCos.A_zmailPasswordLockoutFailureLifetime = "zmailPasswordLockoutFailureLifetime";

//file retension
ZaCos.A_zmailNumFileVersionsToKeep = "zmailNumFileVersionsToKeep";
ZaCos.A_zmailUnaccessedFileLifetime = "zmailUnaccessedFileLifetime";
ZaCos.A_zmailFileTrashLifetime = "zmailFileTrashLifetime";
ZaCos.A_zmailFileSendExpirationWarning = "zmailFileSendExpirationWarning";
ZaCos.A_zmailFileExpirationWarningDays = "zmailFileExpirationWarningDays";

ZaCos.A2_retentionPoliciesKeepInherited ="retentionPoliciesKeepInherited";
ZaCos.A2_retentionPoliciesKeep = "retentionPolicyKeep";
ZaCos.A2_retentionPoliciesPurge = "retentionPolicyPurge";
ZaCos.A2_retentionPoliciesKeep_Selection = "retentionPoliciesKeep_Selection";
ZaCos.A2_retentionPoliciesPurge_Selection = "retentionPoliciesPurge_Selection";

// right
ZaCos.RIGHT_LIST_COS = "listCos";
ZaCos.RIGHT_LIST_ZIMLET = "listZimlet";
ZaCos.RIGHT_GET_ZIMLET = "getZimlet";
ZaCos.RIGHT_GET_HOSTNAME = "zmailVirtualHostname";

ZaCos.A_zmailFreebusyExchangeUserOrg = "zmailFreebusyExchangeUserOrg" ;
ZaCos.cacheCounter = 0;
ZaCos.staticCosByNameCacheTable={};
ZaCos.staticCosByIdCacheTable = {};
ZaCos.putCosToCache = function(cos) {
	if(ZaCos.cacheCounter==100) {
		ZaCos.staticCosByNameCacheTable = {};
		ZaCos.staticCosByIdCacheTable = {};
		ZaCos.cacheCounter = 0;
	}
		
	if(!ZaCos.staticCosByNameCacheTable[cos.name] || !ZaCos.staticCosByIdCacheTable[cos.id]) {
		ZaCos.cacheCounter++;
		ZaCos.staticCosByNameCacheTable[cos.name] = cos;
		ZaCos.staticCosByIdCacheTable[cos.id] = cos;
	}
}

ZaCos.MAJOR_FEATURES_CHOICES = [
    {value: ZaCos.A_zmailFeatureMailEnabled, label:ZaMsg.NAD_zmailFeatureMailEnabled },
    {value: ZaCos.A_zmailFeatureContactsEnabled, label:ZaMsg.NAD_FeatureContactsEnabled },
    {value: ZaCos.A_zmailFeatureCalendarEnabled, label:ZaMsg.NAD_FeatureCalendarEnabled },
    {value:ZaCos.A_zmailFeatureTasksEnabled, label:ZaMsg.NAD_FeatureTaskEnabled},
    //{value:ZaCos.A_zmailFeatureNotebookEnabled,label:ZaMsg.NAD_zmailFeatureNotebookEnabled},
    {value:ZaCos.A_zmailFeatureBriefcasesEnabled,label:ZaMsg.NAD_zmailFeatureBriefcasesEnabled},
    {value:ZaCos.A_zmailFeatureIMEnabled,label:ZaMsg.NAD_zmailFeatureIMEnabled},
    {value:ZaCos.A_zmailFeatureOptionsEnabled, label:ZaMsg.NAD_zmailFeatureOptionsEnabled}
        
];

ZaCos.RENAME_COS_RIGHT = "renameCos";
ZaCos.CREATE_COS_RIGHT = "createCos";
ZaCos.DELETE_COS_RIGHT = "deleteCos";
//internal attributes - do not send these to the server
//ZaCos.A_zmailMailAllServersInternal = "allserversarray";
//ZaCos.A_zmailMailHostPoolInternal = "hostpoolarray";

ZaCos.initMethod = function () {
	this.attrs = new Object();
	this.id = "";
	this.name="";
	this.type = ZaItem.COS;
}
ZaItem.initMethods["ZaCos"].push(ZaCos.initMethod);


ZaCos.loadMethod =
function (by, val) {
	var soapDoc = AjxSoapDoc.create("GetCosRequest", ZaZmailAdmin.URN, null);
	var el = soapDoc.set("cos", val);
	el.setAttribute("by", by);
	if(!this.getAttrs.all && !AjxUtil.isEmpty(this.attrsToGet)) {
		soapDoc.getMethod().setAttribute("attrs", this.attrsToGet.join(","));
	}	
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	var reqMgrParams = {
			controller: ZaApp.getInstance().getCurrentController(),
			busyMsg: ZaMsg.BUSY_GET_COS
		}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.GetCosResponse;
	this.initFromJS(resp.cos[0]);

	if(this.attrs[ZaAccount.A_zmailPrefMailPollingInterval]) {
    	var poIntervalInS = ZaUtil.getLifeTimeInSeconds(this.attrs[ZaAccount.A_zmailPrefMailPollingInterval]);
        if (poIntervalInS >= 1)
            this.attrs[ZaAccount.A_zmailPrefMailPollingInterval] = poIntervalInS + "s";
    }

	if(this.attrs[ZaCos.A_zmailProxyAllowedDomains] &&
       (!(this.attrs[ZaCos.A_zmailProxyAllowedDomains] instanceof Array)) ) {
		this.attrs[ZaCos.A_zmailProxyAllowedDomains] = [this.attrs[ZaCos.A_zmailProxyAllowedDomains]];
	}
}
ZaItem.loadMethods["ZaCos"].push(ZaCos.loadMethod);

ZaCos.prototype.refresh = 
function () {
	this.load("name", this.name);
}

ZaCos.prototype.initFromJS =
function (obj) {
	ZaItem.prototype.initFromJS.call(this, obj);
	if(typeof(this.attrs[ZaCos.A_zmailMailHostPool]) == 'string'){
		this.attrs[ZaCos.A_zmailMailHostPool] = [this.attrs[ZaCos.A_zmailMailHostPool]];
	}
}

/**
* public ZaCos.rename
* @param name - name for the new COS
* @param attrs - map of attributes
**/
ZaCos.prototype.create = 
function(name, mods) {
	var soapDoc = AjxSoapDoc.create("CreateCosRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("name", name);
	for (var aname in mods) {
		//multy value attribute
		if(mods[aname] instanceof Array) {
			var cnt = mods[aname].length;
			if(cnt) { //only set if not empty
				for(var ix=0; ix <cnt; ix++) {
					if(mods[aname][ix] instanceof String)
						var attr = soapDoc.set("a", mods[aname][ix].toString());
					else if(mods[aname][ix] instanceof Object)
						var attr = soapDoc.set("a", mods[aname][ix].toString());
					else 
						var attr = soapDoc.set("a", mods[aname][ix]);
						
					attr.setAttribute("n", aname);
				}
			} 
		} else if(mods[aname] && (mods[aname].length || !isNaN(mods[aname]) )) {
			var attr = soapDoc.set("a", mods[aname]);
			attr.setAttribute("n", aname);
		}	
	}
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	var reqMgrParams = {
			controller: ZaApp.getInstance().getCurrentController(),
			busyMsg : ZaMsg.BUSY_CREATE_COS
		}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.CreateCosResponse;
	this.initFromJS(resp.cos[0]);
}

/**
* public ZaCos.rename
* @param newName - new name
**/
ZaCos.prototype.rename = 
function(newName) {
	var soapDoc = AjxSoapDoc.create("RenameCosRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("id", this.id);
	soapDoc.set("newName", newName);	
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_RENAME_COS
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.RenameCosResponse;
	this.initFromJS(resp.cos[0]);	
}

/**
* public ZaCos.remove
* sends DeleteCosRequest SOAP command
**/
ZaCos.prototype.remove = 
function(callback) {
	var soapDoc = AjxSoapDoc.create("DeleteCosRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("id", this.id);
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	params.soapDoc = soapDoc;	
	if(callback) {
		params.asyncMode = true;
		params.callback = callback;
	}	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_DELETE_COS
	}
	ZaRequestMgr.invoke(params, reqMgrParams);
}
/**
* public ZaCos.modifyMethod
* @param mods - map of modified attributes
**/
ZaCos.modifyMethod = 
function (mods) {
	var gotSomething = false;
	
	var soapDoc = AjxSoapDoc.create("ModifyCosRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("id", this.id);
	for (var aname in mods) {
		gotSomething = true;
		//multi value attribute
		if(mods[aname] instanceof Array) {
			var cnt = mods[aname].length;
			if(cnt) {
				var nonemptyElements = false;
				for(var ix=0; ix <cnt; ix++) {
					var attr = null;
					if(mods[aname][ix] instanceof String || AjxUtil.isString(mods[aname][ix])) {
						if(AjxUtil.isEmpty(mods[aname][ix])) {
							continue;
						} else {
							nonemptyElements = true;
						}
						var attr = soapDoc.set("a", mods[aname][ix].toString());
					} else if(mods[aname][ix] instanceof Object) {
						var attr = soapDoc.set("a", mods[aname][ix].toString());
						nonemptyElements = true;
					} else {
						var attr = soapDoc.set("a", mods[aname][ix]);
						nonemptyElements = true;
					}
					
					if(attr)
						attr.setAttribute("n", aname);
				}
				if(!nonemptyElements) {
					var attr = soapDoc.set("a", "");
					attr.setAttribute("n", aname);
				}
			} else {
				var attr = soapDoc.set("a", "");
				attr.setAttribute("n", aname);
			}
		} else {
			var attr = soapDoc.set("a", mods[aname]);
			attr.setAttribute("n", aname);
		}
	}
	if(!gotSomething)
		return;
		
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_MODIFY_COS
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.ModifyCosResponse;
	this.initFromJS(resp.cos[0]);
	ZaCos.putCosToCache(this);

}
ZaItem.modifyMethods["ZaCos"].push(ZaCos.modifyMethod);
/**
* Returns HTML for a tool tip for this cos.
*/
ZaCos.prototype.getToolTip =
function() {
	// update/null if modified
	if (!this._toolTip) {
		var html = new Array(20);
		var idx = 0;
		html[idx++] = "<table cellpadding='0' cellspacing='0' border='0'>";
		html[idx++] = "<tr valign='center'><td colspan='2' align='left'>";
		html[idx++] = "<div style='border-bottom: 1px solid black; white-space:nowrap; overflow:hidden;'>";
		html[idx++] = "<table cellpadding='0' cellspacing='0' border='0' style='width:100%;'>";
		html[idx++] = "<tr valign='center'>";
		html[idx++] = "<td><b>" + AjxStringUtil.htmlEncode(this.name) + "</b></td>";
		html[idx++] = "<td align='right'>";
		html[idx++] = AjxImg.getImageHtml("COS");				
		html[idx++] = "</td>";
		html[idx++] = "</table></div></td></tr>";
		html[idx++] = "<tr></tr>";
		idx = this._addAttrRow(ZaItem.A_description, html, idx);
		idx = this._addAttrRow(ZaItem.A_zmailId, html, idx);
		html[idx++] = "</table>";
		this._toolTip = html.join("");
	}
	return this._toolTip;
}

ZaCos.getAll =
function() {
	var soapDoc = AjxSoapDoc.create("GetAllCosRequest", ZaZmailAdmin.URN, null);	
	//var getAllCosCmd = new ZmCsfeCommand ();
	var params = new Object ();
	params.soapDoc = soapDoc ;
	var reqMgrParams = {
			controller: ZaApp.getInstance().getCurrentController(),
			busyMsg: ZaMsg.BUSY_GET_ALL_COS
		}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.GetAllCosResponse;
	var list = new ZaItemList(ZaCos);
	list.loadFromJS(resp);
	//list.sortByName();	
	
	return list;
}

ZaCos.getCosChoices = function () {
    if (ZaCos.cosChoices) {
        return ZaCos.cosChoices ;
    }else{
        ZaCos.cosChoices = new XFormChoices
    }
}





ZaCos.getDefaultCos4Account =
function (accountName){
	var defaultCos ;
	var defaultDomainCos ;

		
	if (!accountName) {
		return defaultCos; //default cos
	}
	
	var domainName = ZaAccount.getDomain(accountName);
	var domainCosId ;
	var domain;
    try {
        domain= ZaDomain.getDomainByName(domainName);
    } catch (ex) {
        domain = undefined;
    }

	if(domain) {
		domainCosId = domain.attrs[ZaDomain.A_domainDefaultCOSId] ;
		//when domainCosId doesn't exist, we always set default cos
		if (!domainCosId) {
			var cos = ZaCos.getCosByName("default");
			return cos ;
		} else{
			var cos = ZaCos.getCosById (domainCosId);
			if(!cos)
				cos = ZaCos.getCosByName("default");
			
		 	return cos ;
			//return cosList.getItemById(domainCosId);
		}
	} else {
		return null;
	}
	
}

ZaCos.getCosByName = 
function(cosName) {
	if(!cosName)
		return null;
		
	var cos = ZaCos.staticCosByNameCacheTable[cosName];
	if(!cos) {
		cos = new ZaCos();
		try {
			cos.load("name", cosName);
		} catch (ex) {
			if(ex.code == ZmCsfeException.NO_SUCH_COS) {
				return null;
			} else {
				throw (ex);
			}
		}
		ZaCos.putCosToCache(cos);
	}
	return cos;	
} 

ZaCos.getCosById = 
function (cosId) {
	if(!cosId)
		return null;
		
	var cos = ZaCos.staticCosByIdCacheTable[cosId];
	if(!cos) {
		cos = new ZaCos();
		try {
			cos.load("id", cosId);
		} catch (ex) {
			if(ex.code == ZmCsfeException.NO_SUCH_COS) {
				return null;
			} else {
				throw (ex);
			}
		}
		ZaCos.putCosToCache(cos);
	}
	return cos;
	
	/*var cnt = cosListArray.length;
	for(var i = 0; i < cnt; i++) {
		if(cosListArray[i].id == cosId) {
			return cosListArray[i];
		}
	}*/
}

ZaCos.myXModel = {
    items: [
        {id:"getAttrs",type:_LIST_},
    	{id:"setAttrs",type:_LIST_},
    	{id:"rights",type:_LIST_},
        {id:ZaItem.A_zmailId, type:_STRING_, ref:"attrs/" + ZaItem.A_zmailId},
        {id:ZaItem.A_zmailCreateTimestamp, ref:"attrs/" + ZaItem.A_zmailCreateTimestamp},
        {id:ZaCos.A_zmailMailHostPool, ref:"attrs/" + ZaCos.A_zmailMailHostPool, type:_LIST_, dataType: _STRING_,outputType:_LIST_},
        {id:ZaCos.A_zmailNotes, type:_STRING_, ref:"attrs/"+ZaCos.A_zmailNotes},
        {id:ZaCos.A_zmailMailQuota, type:_MAILQUOTA_, ref:"attrs."+ZaCos.A_zmailMailQuota},
        {id:ZaCos.A_zmailMinPwdLength, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailMinPwdLength, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailMaxPwdLength, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailMaxPwdLength, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailPasswordMinUpperCaseChars, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailPasswordMinUpperCaseChars, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailPasswordMinLowerCaseChars, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailPasswordMinLowerCaseChars, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailPasswordMinPunctuationChars, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailPasswordMinPunctuationChars, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailPasswordMinNumericChars, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailPasswordMinNumericChars, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailPasswordMinDigitsOrPuncs, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailPasswordMinDigitsOrPuncs, maxInclusive:2147483647, minInclusive:0},        
        {id:ZaCos.A_zmailMinPwdAge, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailMinPwdAge, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailMaxPwdAge, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailMaxPwdAge, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailEnforcePwdHistory, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailEnforcePwdHistory, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailPasswordLocked, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPasswordLocked},
        {id:ZaCos.A_name, type:_STRING_, ref:"attrs/"+ZaCos.A_name},
//        {id:ZaCos.A_description, type:_STRING_, ref:"attrs/"+ZaCos.A_description},
        ZaItem.descriptionModelItem ,
        {id:ZaCos.A_zmailAttachmentsBlocked, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailAttachmentsBlocked},
        {id:ZaCos.A_zmailAuthTokenLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailAuthTokenLifetime},
        {id:ZaCos.A_zmailAdminAuthTokenLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailAdminAuthTokenLifetime},
        {id:ZaCos.A_zmailMailIdleSessionTimeout, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailMailIdleSessionTimeout},
        {id:ZaCos.A_zmailContactMaxNumEntries, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailContactMaxNumEntries, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailMailForwardingAddressMaxLength, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailMailForwardingAddressMaxLength, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailMailForwardingAddressMaxNumAddrs, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailMailForwardingAddressMaxNumAddrs, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailMailMinPollingInterval, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailMailMinPollingInterval},
        {id:ZaCos.A_zmailMailMessageLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailMailMessageLifetime},
        {id:ZaCos.A_zmailMailTrashLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailMailTrashLifetime},
        {id:ZaCos.A_zmailPrefItemsPerVirtualPage, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailPrefItemsPerVirtualPage},
        {id:ZaCos.A_zmailMailSpamLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailMailSpamLifetime},

        {id:ZaCos.A_zmailQuotaWarnPercent, type:_NUMBER_, ref:"attrs/" + ZaCos.A_zmailQuotaWarnPercent},
        {id:ZaCos.A_zmailQuotaWarnInterval, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailQuotaWarnInterval},
        {id:ZaCos.A_zmailQuotaWarnMessage, type:_STRING_, ref:"attrs/" + ZaCos.A_zmailQuotaWarnMessage},

//pref
		{id:ZaCos.A_zmailPrefMandatorySpellCheckEnabled, types:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefMandatorySpellCheckEnabled,  choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaCos.A_zmailPrefAppleIcalDelegationEnabled, types:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefAppleIcalDelegationEnabled,  choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaCos.A_zmailPrefCalendarShowPastDueReminders, types:_ENUM_, ref:"attrs/" + ZaCos.A_zmailPrefCalendarShowPastDueReminders, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaCos.A_zmailPrefCalendarToasterEnabled, type:_ENUM_, ref:"attrs/" + ZaCos.A_zmailPrefCalendarToasterEnabled, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaCos.A_zmailPrefCalendarAllowCancelEmailToSelf, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefCalendarAllowCancelEmailToSelf, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaCos.A_zmailPrefCalendarAllowPublishMethodInvite, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefCalendarAllowPublishMethodInvite,choices:ZaModel.BOOLEAN_CHOICES}, 
		{id:ZaCos.A_zmailPrefCalendarAllowForwardedInvite, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefCalendarAllowForwardedInvite, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaCos.A_zmailPrefCalendarReminderFlashTitle, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefCalendarReminderFlashTitle, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaCos.A_zmailPrefCalendarReminderSoundsEnabled, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefCalendarReminderSoundsEnabled, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaCos.A_zmailPrefCalendarAutoAddInvites, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefCalendarAutoAddInvites, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaCos.A_zmailPrefCalendarNotifyDelegatedChanges, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefCalendarNotifyDelegatedChanges, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaCos.A_zmailPrefCalendarApptVisibility, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefCalendarApptVisibility, choices:ZaSettings.apptVisibilityChoices},
		{id:ZaCos.A_zmailPrefCalendarFirstDayOfWeek, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailPrefCalendarFirstDayOfWeek, choices:ZaSettings.dayOfWeekChoices},
		{id:ZaCos.A_zmailPrefCalendarInitialView, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefCalendarInitialView, choices:ZaSettings.calendarViewChoinces},
        {id:ZaCos.A_zmailPrefClientType,type:_STRING_, ref:"attrs/"+ZaCos.A_zmailPrefClientType, choices:ZaSettings.clientTypeChoices},
        {id:ZaCos.A_zmailPrefTimeZoneId,type:_STRING_, ref:"attrs/"+ZaCos.A_zmailPrefTimeZoneId, choices:ZaSettings.timeZoneChoices},
        {id:ZaCos.A_zmailPrefGroupMailBy, type:_STRING_, ref:"attrs/"+ZaCos.A_zmailPrefGroupMailBy},
        {id:ZaCos.A_zmailPrefIncludeSpamInSearch, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefIncludeSpamInSearch, type:_ENUM_},
        {id:ZaCos.A_zmailPrefIncludeTrashInSearch, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefIncludeTrashInSearch, type:_ENUM_},
        {id:ZaCos.A_zmailPrefMailInitialSearch, type:_STRING_, ref:"attrs/"+ZaCos.A_zmailPrefMailInitialSearch},
        {id:ZaCos.A_zmailPrefUseKeyboardShortcuts, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefUseKeyboardShortcuts, type:_ENUM_},
        {id:ZaCos.A_zmailAllowAnyFromAddress, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailAllowAnyFromAddress, type:_ENUM_},
        {id:ZaCos.A_zmailPrefSaveToSent, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefSaveToSent, type:_ENUM_},
        {id:ZaCos.A_zmailMaxMailItemsPerPage, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailMaxMailItemsPerPage,maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailPrefMailItemsPerPage, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailPrefMailItemsPerPage, choices:[10,25,50,100]},
        {id:ZaCos.A_zmailPrefHtmlEditorDefaultFontFamily, choices:ZaModel.FONT_FAMILY_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefHtmlEditorDefaultFontFamily, type:_ENUM_},
        {id:ZaCos.A_zmailPrefHtmlEditorDefaultFontSize, choices:ZaModel.FONT_SIZE_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefHtmlEditorDefaultFontSize, type:_ENUM_},
        {id:ZaCos.A_zmailPrefHtmlEditorDefaultFontColor, ref:"attrs/"+ZaCos.A_zmailPrefHtmlEditorDefaultFontColor, type:_STRING_},
        {id:ZaCos.A_zmailMailSignatureMaxLength, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailMailSignatureMaxLength},
        {id:ZaCos.A_zmailPrefMailToasterEnabled, type:_ENUM_, ref:"attrs/" + ZaCos.A_zmailPrefMailToasterEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaCos.A_zmailPrefMessageIdDedupingEnabled, type:_ENUM_, ref:"attrs/" + ZaCos.A_zmailPrefMessageIdDedupingEnabled, choices:ZaModel.BOOLEAN_CHOICES},
	{id:ZaCos.A_zmailPrefComposeInNewWindow, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefComposeInNewWindow, type:_ENUM_},
        {id:ZaCos.A_zmailPrefForwardReplyInOriginalFormat, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefForwardReplyInOriginalFormat, type:_ENUM_},
        {id:ZaCos.A_zmailPrefComposeFormat, choices:ZaModel.COMPOSE_FORMAT_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefComposeFormat, type:_ENUM_},
        {id:ZaCos.A_zmailPrefAutoAddAddressEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefAutoAddAddressEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailPrefImapSearchFoldersEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefImapSearchFoldersEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailPrefGroupMailBy, choices:ZaModel.GROUP_MAIL_BY_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefGroupMailBy, type:_ENUM_},
        {id:ZaCos.A_zmailPrefMessageViewHtmlPreferred, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefMessageViewHtmlPreferred, type:_ENUM_},
        {id:ZaCos.A_zmailPrefShowSearchString, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefShowSearchString, type:_ENUM_},
        //{id:ZaCos.A_zmailPrefMailSignatureStyle, choices:ZaModel.SIGNATURE_STYLE_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefMailSignatureStyle, type:_ENUM_,defaultValue:"internet"},
        {id:ZaCos.A_zmailPrefUseTimeZoneListInCalendar, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefUseTimeZoneListInCalendar, type:_ENUM_},
        {id:ZaCos.A_zmailPrefMailPollingInterval, ref:"attrs/"+ZaCos.A_zmailPrefMailPollingInterval, type:_ENUM_, choices: ZaSettings.mailPollingIntervalChoices},
	{id:ZaCos.A_zmailPrefAutoSaveDraftInterval, ref:"attrs/"+ZaCos.A_zmailPrefAutoSaveDraftInterval, type:_MLIFETIME_},
        {id:ZaCos.A_zmailDataSourceMinPollingInterval, ref:"attrs/"+ZaCos.A_zmailDataSourceMinPollingInterval, type:_MLIFETIME_},
        {id:ZaCos.A_zmailDataSourcePop3PollingInterval, ref:"attrs/"+ZaCos.A_zmailDataSourcePop3PollingInterval, type:_MLIFETIME_},
        {id:ZaCos.A_zmailDataSourceImapPollingInterval, ref:"attrs/"+ZaCos.A_zmailDataSourceImapPollingInterval, type:_MLIFETIME_},
        {id:ZaCos.A_zmailDataSourceCalendarPollingInterval, ref:"attrs/"+ZaCos.A_zmailDataSourceCalendarPollingInterval, type:_MLIFETIME_},
        {id:ZaCos.A_zmailDataSourceRssPollingInterval, ref:"attrs/"+ZaCos.A_zmailDataSourceRssPollingInterval, type:_MLIFETIME_},
        {id:ZaCos.A_zmailDataSourceCaldavPollingInterval, ref:"attrs/"+ZaCos.A_zmailDataSourceCaldavPollingInterval, type:_MLIFETIME_},
	{id:ZaCos.A_zmailProxyAllowedDomains, type: _LIST_, ref:"attrs/"+ZaCos.A_zmailProxyAllowedDomains, listItem:{ type: _STRING_}}, 
        {id:ZaCos.A_zmailPrefMailFlashIcon, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefMailFlashIcon, type:_ENUM_},
        {id:ZaCos.A_zmailPrefMailFlashTitle, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefMailFlashTitle, type:_ENUM_},
        {id:ZaCos.A_zmailPrefMailSoundsEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefMailSoundsEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailPrefCalendarUseQuickAdd, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefCalendarUseQuickAdd, type:_ENUM_},
        {id:ZaCos.A_zmailPrefCalendarAlwaysShowMiniCal, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefCalendarAlwaysShowMiniCal, type:_ENUM_},
        {id:ZaCos.A_zmailPrefCalendarApptReminderWarningTime, choices:ZaModel.REMINDER_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefCalendarApptReminderWarningTime, type:_ENUM_},
        {id:ZaCos.A_zmailPrefSkin, ref:"attrs/"+ZaCos.A_zmailPrefSkin, type:_STRING_},
        {id:ZaCos.A_zmailPrefGalAutoCompleteEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefGalAutoCompleteEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailPrefWarnOnExit, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefWarnOnExit, type:_ENUM_},
        {id:ZaCos.A_zmailPrefAdminConsoleWarnOnExit, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefAdminConsoleWarnOnExit, type:_ENUM_},
        {id:ZaCos.A_zmailPrefShowSelectionCheckbox, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefShowSelectionCheckbox, type:_ENUM_},
        {id:ZaCos.A_zmailPrefIMAutoLogin, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefIMAutoLogin, type:_ENUM_},
        {id:ZaCos.A_zmailAvailableSkin, ref:"attrs/" + ZaCos.A_zmailAvailableSkin, type:_LIST_, dataType: _STRING_,outputType:_LIST_},
        {id:ZaCos.A_zmailZimletAvailableZimlets, ref:"attrs/" + ZaCos.A_zmailZimletAvailableZimlets, type:_LIST_, dataType: _STRING_,outputType:_LIST_},
        {id:ZaCos.A_zmailPrefDisplayExternalImages, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefDisplayExternalImages, type:_ENUM_},
        {id:ZaCos.A_zmailPrefOutOfOfficeCacheDuration, ref:"attrs/"+ZaCos.A_zmailPrefOutOfOfficeCacheDuration, type:_MLIFETIME_},
        {id:ZaCos.A_zmailPrefMailDefaultCharset,type:_STRING_, ref:"attrs/"+ZaCos.A_zmailPrefMailDefaultCharset, choices:ZaSettings.mailCharsetChoices},
        {id:ZaCos.A_zmailPrefLocale, type: _STRING_, ref: "attrs/" + ZaCos.A_zmailPrefLocale  },
        {id:ZaCos.A_zmailJunkMessagesIndexingEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailJunkMessagesIndexingEnabled, type:_ENUM_},
		{id:ZaCos.A_zmailPrefMailSendReadReceipts, choices:ZaModel.SEND_READ_RECEPIT_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefMailSendReadReceipts, type:_ENUM_},
//features
		{id:ZaCos.A_zmailFeatureExportFolderEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureExportFolderEnabled, type:_ENUM_},
		{id:ZaCos.A_zmailFeatureImportFolderEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureImportFolderEnabled, type:_ENUM_},
		{id:ZaCos.A_zmailDumpsterEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailDumpsterEnabled, type:_ENUM_},
		{id:ZaCos.A_zmailDumpsterUserVisibleAge, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailDumpsterUserVisibleAge },
		{id:ZaCos.A_zmailDumpsterPurgeEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailDumpsterPurgeEnabled, type:_ENUM_},
		{id:ZaCos.A_zmailMailDumpsterLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailMailDumpsterLifetime },
		{id:ZaCos.A_zmailPrefCalendarSendInviteDeniedAutoReply, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPrefCalendarSendInviteDeniedAutoReply, type:_ENUM_},
		{id:ZaCos.A_zmailFeatureReadReceiptsEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureReadReceiptsEnabled, type:_ENUM_},
        {
            id: ZaCos.A_zmailFeatureDistributionListFolderEnabled,
            choices: ZaModel.BOOLEAN_CHOICES,
            ref: "attrs/" + ZaCos.A_zmailFeatureDistributionListFolderEnabled,
            type: _ENUM_
        },
        {id:ZaCos.A_zmailFeatureMailPriorityEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureMailPriorityEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureImapDataSourceEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureImapDataSourceEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeaturePop3DataSourceEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeaturePop3DataSourceEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureIdentitiesEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureIdentitiesEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureContactsEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureContactsEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureCalendarEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureCalendarEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureTasksEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureTasksEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureIMEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaCos.A_zmailFeatureIMEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureInstantNotify, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaCos.A_zmailFeatureInstantNotify, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureTaggingEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureTaggingEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeaturePeopleSearchEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeaturePeopleSearchEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureAdvancedSearchEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureAdvancedSearchEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureSavedSearchesEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureSavedSearchesEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureConversationsEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureConversationsEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureChangePasswordEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureChangePasswordEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureHtmlComposeEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureHtmlComposeEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureInitialSearchPreferenceEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureInitialSearchPreferenceEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureFiltersEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureFiltersEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureGalEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureGalEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureMAPIConnectorEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureMAPIConnectorEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureManageSMIMECertificateEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureManageSMIMECertificateEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureSMIMEEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureSMIMEEnabled, type:_ENUM_},
	{id:ZaCos.A_zmailFeatureMailForwardingEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureMailForwardingEnabled, type:_ENUM_},
	{id:ZaCos.A_zmailFeatureMailSendLaterEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureMailSendLaterEnabled, type:_ENUM_},
        //{id:ZaCos.A_zmailFeatureFreeBusyViewEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureFreeBusyViewEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureCalendarReminderDeviceEmailEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureCalendarReminderDeviceEmailEnabled, type:_ENUM_},
	//{id:ZaCos.A_zmailFeatureNotebookEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureNotebookEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureBriefcasesEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureBriefcasesEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureBriefcaseDocsEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureBriefcaseDocsEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureGalAutoCompleteEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureGalAutoCompleteEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailImapEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailImapEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureHtmlComposeEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureHtmlComposeEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailImapEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailImapEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailPop3Enabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPop3Enabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureSharingEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureSharingEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailExternalSharingEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailExternalSharingEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailPublicSharingEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailPublicSharingEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureOutOfOfficeReplyEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureOutOfOfficeReplyEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureNewMailNotificationEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureNewMailNotificationEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureMailPollingIntervalPreferenceEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureMailPollingIntervalPreferenceEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureOptionsEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureOptionsEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureSkinChangeEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureSkinChangeEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureManageZimlets, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureManageZimlets, type:_ENUM_},
        //{id:ZaCos.A_zmailFeatureShortcutAliasesEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureShortcutAliasesEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureMailEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureMailEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureGroupCalendarEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureGroupCalendarEnabled, type:_ENUM_},
        {id:ZaCos.A_zmailFeatureFlaggingEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureFlaggingEnabled, type:_ENUM_},
        //security
        {id:ZaCos.A_zmailPasswordLockoutEnabled, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPasswordLockoutEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaCos.A_zmailPasswordLockoutDuration, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailPasswordLockoutDuration},
        {id:ZaCos.A_zmailPasswordLockoutMaxFailures, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailPasswordLockoutMaxFailures, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailPasswordLockoutFailureLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailPasswordLockoutFailureLifetime},
        //interop
        {id:ZaCos.A_zmailFreebusyExchangeUserOrg ,type:_STRING_, ref:"attrs/"+ZaCos.A_zmailFreebusyExchangeUserOrg },
        
        
        //file retension
        {id:ZaCos.A_zmailFileTrashLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailFileTrashLifetime},
        {id:ZaCos.A_zmailUnaccessedFileLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailUnaccessedFileLifetime},
        {id:ZaCos.A_zmailNumFileVersionsToKeep, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailNumFileVersionsToKeep, maxInclusive:2147483647, minInclusive:0},
        {id:ZaCos.A_zmailFileSendExpirationWarning, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailFileSendExpirationWarning,
        	choices:["none", "owner", "all"]
        },
        {id:ZaCos.A_zmailFileExpirationWarningDays, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailFileExpirationWarningDays},
        {id:ZaCos.A2_retentionPoliciesKeepInherited, type:_ENUM_, ref:ZaCos.A2_retentionPoliciesKeepInherited, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaCos.A2_retentionPoliciesKeep, type:_LIST_},
        {id:ZaCos.A2_retentionPoliciesPurge, type:_LIST_},
        {id:ZaCos.A2_retentionPoliciesKeep_Selection, type:_LIST_},
        {id:ZaCos.A2_retentionPoliciesPurge_Selection, type:_LIST_}
    ]
};


ZaCos.prototype.manageSpecialAttrs =
function () {
	var warning = "" ;
	
	//handle the unrecognized timezone
	var tz = this.attrs[ZaCos.A_zmailPrefTimeZoneId] ;
	if (tz) {
		var n_tz = ZaModel.setUnrecoganizedChoiceValue(tz, ZaSettings.timeZoneChoices) ;
		if (tz != n_tz) {
			this.attrs[ZaCos.A_zmailPrefTimeZoneId] = n_tz ;
			warning += AjxMessageFormat.format(ZaMsg.WARNING_TIME_ZONE_INVALID , [ tz, "cos - \"" + this.name +"\""]);
		}
	}

	//handle the unrecognized mail charset
	var mdc = this.attrs[ZaCos.A_zmailPrefMailDefaultCharset] ;
	if (mdc) {
		var n_mdc = ZaModel.setUnrecoganizedChoiceValue(mdc, ZaSettings.mailCharsetChoices) ;
		if (mdc != n_mdc) {
			this.attrs[ZaCos.A_zmailPrefMailDefaultCharset] = n_mdc ;
			warning += AjxMessageFormat.format(ZaMsg.WARNING_CHARSET_INVALID , [ mdc, "cos - \"" + this.name +"\""]);
		}
	}

    //handle the unrecognized locale value
    var lv = this.attrs[ZaCos.A_zmailPrefLocale] ;
    if (lv) {
        var n_lv = ZaModel.setUnrecoganizedChoiceValue(lv, ZaSettings.getLocaleChoices()) ;
		if (lv != n_lv) {
			this.attrs[ZaCos.A_zmailPrefLocale] = n_lv ;
			warning += AjxMessageFormat.format(ZaMsg.WARNING_LOCALE_INVALID , [ lv, "cos - \"" + this.name +"\""]);
		}
    }

    //display warnings about the if manageSpecialAttrs return value
	if (warning && warning.length > 0) {
		ZaApp.getInstance().getCurrentController().popupMsgDialog (warning, true);
	}	
	
}
ZaCos.globalRights = {};
ZaCos.getEffectiveCosList = function(adminId) {

    var soapDoc = AjxSoapDoc.create("GetAllEffectiveRightsRequest", ZaZmailAdmin.URN, null);
    var elGrantee = soapDoc.set("grantee", adminId);
    elGrantee.setAttribute("type", "usr");
    elGrantee.setAttribute("by", "id");

    var params = {};
    params.soapDoc = soapDoc;
    params.asyncMode = false;
    var reqMgrParams = {
        controller : ZaApp.getInstance().getCurrentController(),
        busyMsg : ZaMsg.BUSY_GET_EFFICIENT_COS_LIST
    }

    var cosNameList = [];
    try {
        var resp = ZaRequestMgr.invoke(params, reqMgrParams);
        if(!resp || resp.Body.GetAllEffectiveRightsResponse.Fault)
            return cosNameList;
        var targets = resp.Body.GetAllEffectiveRightsResponse.target;
        for(var i = 0; i < targets.length; i++) {
            if(targets[i].type != ZaItem.COS)
                continue;
            if(!targets[i].entries && !targets[i].all) continue;
            
            if(targets[i].all) { 
            	//we have access to all domains
            	if(targets[i].all.length && targets[i].all[0] && targets[i].all[0].right && targets[i].all[0].right.length) {
            		for(var j=0;j<targets[i].all[0].right.length;j++) {
            			ZaCos.globalRights[targets[i].all[0].right[j].n] = true;
            		}
            	}
            }
            
            for(var j = 0; j < targets[i].entries.length; j++) {
                var entry = targets[i].entries[j].entry;
                for(var k = 0; k < entry.length; k++)
                    cosNameList.push(entry[k].name);
            }
            break;
        }
        return cosNameList;
    } catch(ex) {
        return cosNameList;
    }

}

ZaCos.prototype.countAllAccounts = function() {
	var soapDoc = AjxSoapDoc.create("SearchDirectoryRequest", ZaZmailAdmin.URN, null);
    soapDoc.getMethod().setAttribute("maxResults", "0");
    soapDoc.getMethod().setAttribute("limit", "-1");
	var query = "(" + ZaAccount.A_COSId + "=" + this.id + ")";

    if(this.name == "default") {
        query = "(|(!(" + ZaAccount.A_COSId + "=*))" + query + ")";
    }
    query = "(&" + query + "(!("+ ZaAccount.A_zmailIsSystemAccount +"=TRUE)))" ;
	soapDoc.set("query", query);
    soapDoc.set("types", ZaSearch.ACCOUNTS);
	var command = new ZmCsfeCommand();
	var cmdParams = new Object();
	cmdParams.soapDoc = soapDoc;
    cmdParams.noAuthToken = true;
    try {
	    var resp = command.invoke(cmdParams).Body.SearchDirectoryResponse;
        if(resp.searchTotal)
            return  resp.searchTotal;
        else return 0;
    } catch(ex) {
        throw (ex);
    }
    return 0;
}

ZaCos.prototype.countAllDomains = function() {
	var soapDoc = AjxSoapDoc.create("SearchDirectoryRequest", ZaZmailAdmin.URN, null);
    soapDoc.getMethod().setAttribute("maxResults", "0");
    soapDoc.getMethod().setAttribute("limit", "-1");
	var query = "(" + ZaDomain.A_domainDefaultCOSId + "=" + this.id + ")";

    if(this.name == "default") {
        query = "(|(!(" + ZaDomain.A_domainDefaultCOSId + "=*))" + query + ")";
    }
	soapDoc.set("query", query);
    soapDoc.set("types", ZaSearch.DOMAINS);
	var command = new ZmCsfeCommand();
	var cmdParams = new Object();
	cmdParams.soapDoc = soapDoc;
    cmdParams.noAuthToken = true;
    try {
	    var resp = command.invoke(cmdParams).Body.SearchDirectoryResponse;
        if(resp.searchTotal)
            return  resp.searchTotal;
        else return 0;
    } catch(ex) {
        throw (ex);
    }
    return 0;
}


ZaCos.checkValues = function(tmpObj){
   if(tmpObj.attrs == null) {
		//show error msg
        ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_UNKNOWN);
	    return false;
	}

	//name
	if(ZaItem.hasWritePermission(ZaCos.A_name,tmpObj)) {
		 if((tmpObj.attrs[ZaCos.A_name] == null || tmpObj.attrs[ZaCos.A_name].length < 1 )) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_NAME_REQUIRED);
			return false;
		} else {
			tmpObj.name = tmpObj.attrs[ZaCos.A_name];
		}

		if(tmpObj.name.length > 256 || tmpObj.attrs[ZaCos.A_name].length > 256) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_COS_NAME_TOOLONG);
			return false;
		}
	}
	/**
	* check values
	**/

	//if(tmpObj.attrs[ZaCos.A_zmailPasswordMinUpperCaseChars] && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailPasswordMinUpperCaseChars])) {
   	if(ZaItem.hasWritePermission(ZaCos.A_zmailPasswordMinUpperCaseChars,tmpObj)) {
	   if (tmpObj.attrs[ZaCos.A_zmailPasswordMinUpperCaseChars] != null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailPasswordMinUpperCaseChars])) {
			//show error msg
           ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordMinUpperCaseChars]));
			return false;
		}
	}
   	if(ZaItem.hasWritePermission(ZaCos.A_zmailPasswordMinLowerCaseChars,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailPasswordMinLowerCaseChars] != null && tmpObj.attrs[ZaCos.A_zmailPasswordMinLowerCaseChars] && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailPasswordMinLowerCaseChars])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordMinLowerCaseChars]));
			return false;
		}
   	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailPasswordMinPunctuationChars,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailPasswordMinPunctuationChars] != null && tmpObj.attrs[ZaCos.A_zmailPasswordMinPunctuationChars] && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailPasswordMinPunctuationChars])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordMinPunctuationChars]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailPasswordMinNumericChars,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailPasswordMinNumericChars] != null && tmpObj.attrs[ZaCos.A_zmailPasswordMinNumericChars] && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailPasswordMinNumericChars])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordMinNumericChars]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailPasswordMinDigitsOrPuncs,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailPasswordMinDigitsOrPuncs] != null && tmpObj.attrs[ZaCos.A_zmailPasswordMinDigitsOrPuncs] && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailPasswordMinDigitsOrPuncs])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordMinDigitsOrPuncs]));
			return false;
		}
	}

	if(tmpObj.attrs[ZaCos.A_zmailMailQuota] != null && tmpObj.attrs[ZaCos.A_zmailMailQuota] && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailMailQuota])) {
		//show error msg
        ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailQuota]));
		return false;
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailContactMaxNumEntries,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailContactMaxNumEntries] != null && tmpObj.attrs[ZaCos.A_zmailContactMaxNumEntries] && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailContactMaxNumEntries])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailContactMaxNumEntries]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailMinPwdLength,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailMinPwdLength] != null && tmpObj.attrs[ZaCos.A_zmailMinPwdLength] && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailMinPwdLength])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMinPwdLength]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailMaxPwdLength,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailMaxPwdLength] != null && tmpObj.attrs[ZaCos.A_zmailMaxPwdLength] && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailMaxPwdLength])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMaxPwdLength]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailMaxPwdLength,tmpObj) && ZaItem.hasWritePermission(ZaCos.A_zmailMinPwdLength,tmpObj)) {
		if (tmpObj.attrs[ZaCos.A_zmailMaxPwdLength] != null &&  tmpObj.attrs[ZaCos.A_zmailMinPwdLength] != null) {
			if(parseInt(tmpObj.attrs[ZaCos.A_zmailMaxPwdLength]) < parseInt(tmpObj.attrs[ZaCos.A_zmailMinPwdLength]) && parseInt(tmpObj.attrs[ZaCos.A_zmailMaxPwdLength]) > 0) {
				//show error msg
                ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_MAX_MIN_PWDLENGTH);
				return false;
			}
		}
	}
	if(ZaItem.hasWritePermission(ZaCos.A_zmailMinPwdAge,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailMinPwdAge] != null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailMinPwdAge])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_passMinAge]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailMaxPwdAge,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailMaxPwdAge] != null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailMaxPwdAge])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_passMaxAge]));
			return false;
		}
	}
	if(ZaItem.hasWritePermission(ZaCos.A_zmailMinPwdAge,tmpObj) && ZaItem.hasWritePermission(ZaCos.A_zmailMaxPwdAge,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailMinPwdAge] != null && tmpObj.attrs[ZaCos.A_zmailMaxPwdAge] != null ){
			if(parseInt(tmpObj.attrs[ZaCos.A_zmailMaxPwdAge]) < parseInt(tmpObj.attrs[ZaCos.A_zmailMinPwdAge]) && parseInt(tmpObj.attrs[ZaCos.A_zmailMaxPwdAge]) > 0) {
				//show error msg
                ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_MAX_MIN_PWDAGE);
				return false;
			}
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailAuthTokenLifetime,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailAuthTokenLifetime] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailAuthTokenLifetime])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailAuthTokenLifetime]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailPrefOutOfOfficeCacheDuration,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailPrefOutOfOfficeCacheDuration] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailPrefOutOfOfficeCacheDuration])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPrefOutOfOfficeCacheDuration]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailMailIdleSessionTimeout,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailMailIdleSessionTimeout] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailMailIdleSessionTimeout])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailIdleSessionTimeout]));
			return false;
		}
	}

        if(ZaItem.hasWritePermission(ZaCos.A_zmailPrefAutoSaveDraftInterval,tmpObj)) {
                if(tmpObj.attrs[ZaCos.A_zmailPrefAutoSaveDraftInterval] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailPrefAutoSaveDraftInterval])) {
                    ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPrefAutoSaveDraftInterval]));
                        return false;
                }
        }

        if(ZaItem.hasWritePermission(ZaCos.A_zmailDataSourceMinPollingInterval,tmpObj)) {
                if(tmpObj.attrs[ZaCos.A_zmailDataSourceMinPollingInterval] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailDataSourceMinPollingInterval])) {
                    ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailDataSourceMinPollingInterval]));
                        return false;
                }
        }
        if(ZaItem.hasWritePermission(ZaCos.A_zmailDataSourcePop3PollingInterval,tmpObj)) {
                if(tmpObj.attrs[ZaCos.A_zmailDataSourcePop3PollingInterval] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailDataSourcePop3PollingInterval])) {
                    ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailDataSourcePop3PollingInterval]));
                        return false;
                }
		var min_dataInterval = tmpObj.attrs[ZaCos.A_zmailDataSourceMinPollingInterval] ;
		var p_dataInterval = tmpObj.attrs[ZaCos.A_zmailDataSourcePop3PollingInterval] ;
                if(p_dataInterval != null && min_dataInterval != null) {
                        if (ZaUtil.getLifeTimeInSeconds(p_dataInterval) < ZaUtil.getLifeTimeInSeconds(min_dataInterval)){
                                ZaApp.getInstance().getCurrentController().popupErrorDialog (ZaMsg.tt_mailPollingIntervalError + min_dataInterval) ;
                                return false ;
                        }
                }

        }
        if(ZaItem.hasWritePermission(ZaCos.A_zmailDataSourceImapPollingInterval,tmpObj)) {
                if(tmpObj.attrs[ZaCos.A_zmailDataSourceImapPollingInterval] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailDataSourceImapPollingInterval])) {
                    ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailDataSourceImapPollingInterval]));
                        return false;
                }
                var min_dataInterval = tmpObj.attrs[ZaCos.A_zmailDataSourceMinPollingInterval] ;
                var p_dataInterval = tmpObj.attrs[ZaCos.A_zmailDataSourceImapPollingInterval] ;
                if(p_dataInterval != null && min_dataInterval != null) {
                        if (ZaUtil.getLifeTimeInSeconds(p_dataInterval) < ZaUtil.getLifeTimeInSeconds(min_dataInterval)){
                                ZaApp.getInstance().getCurrentController().popupErrorDialog (ZaMsg.tt_mailPollingIntervalError + min_dataInterval) ;
                                return false ;
                        }
                }
        }
        if(ZaItem.hasWritePermission(ZaCos.A_zmailDataSourceCalendarPollingInterval,tmpObj)) {
                if(tmpObj.attrs[ZaCos.A_zmailDataSourceCalendarPollingInterval] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailDataSourceCalendarPollingInterval])) {
                    ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailDataSourceCalendarPollingInterval]));
                        return false;
                }
                var min_dataInterval = tmpObj.attrs[ZaCos.A_zmailDataSourceMinPollingInterval] ;
                var p_dataInterval = tmpObj.attrs[ZaCos.A_zmailDataSourceCalendarPollingInterval] ;
                if(p_dataInterval != null && min_dataInterval != null) {
			if (ZaUtil.getLifeTimeInSeconds(p_dataInterval) < ZaUtil.getLifeTimeInSeconds(min_dataInterval)){
				ZaApp.getInstance().getCurrentController().popupErrorDialog (ZaMsg.tt_mailPollingIntervalError + min_dataInterval) ;
                                return false ;
                        }
                }
        }
        if(ZaItem.hasWritePermission(ZaCos.A_zmailDataSourceRssPollingInterval,tmpObj)) {
                if(tmpObj.attrs[ZaCos.A_zmailDataSourceRssPollingInterval] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailDataSourceRssPollingInterval])) {

                    ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailDataSourceRssPollingInterval]));
                        return false;
                }
                var min_dataInterval = tmpObj.attrs[ZaCos.A_zmailDataSourceMinPollingInterval] ;
                var p_dataInterval = tmpObj.attrs[ZaCos.A_zmailDataSourceRssPollingInterval] ;
                if(p_dataInterval != null && min_dataInterval != null) {
                        if (ZaUtil.getLifeTimeInSeconds(p_dataInterval) < ZaUtil.getLifeTimeInSeconds(min_dataInterval)){
                                ZaApp.getInstance().getCurrentController().popupErrorDialog (ZaMsg.tt_mailPollingIntervalError + min_dataInterval) ;                                        return false ;
                        }
		}
        }
        if(ZaItem.hasWritePermission(ZaCos.A_zmailDataSourceCaldavPollingInterval,tmpObj)) {
                if(tmpObj.attrs[ZaCos.A_zmailDataSourceCaldavPollingInterval] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailDataSourceCaldavPollingInterval])) {

                    ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zzmailDataSourceCaldavPollingInterval]));
                        return false;
                }
                var min_dataInterval = tmpObj.attrs[ZaCos.A_zmailDataSourceMinPollingInterval] ;
                var p_dataInterval = tmpObj.attrs[ZaCos.A_zmailDataSourceCaldavPollingInterval] ;
                if(p_dataInterval != null && min_dataInterval != null) {
			if (ZaUtil.getLifeTimeInSeconds(p_dataInterval) < ZaUtil.getLifeTimeInSeconds(min_dataInterval)){
                                ZaApp.getInstance().getCurrentController().popupErrorDialog (ZaMsg.tt_mailPollingIntervalError + min_dataInterval) ;
                                return false ;
                        }
                }
        }

	if(ZaItem.hasWritePermission(ZaCos.A_zmailPrefMailPollingInterval,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailPrefMailPollingInterval] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailPrefMailPollingInterval])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPrefMailPollingInterval]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailPrefMailPollingInterval,tmpObj)) {
		var n_minPollingInterval = tmpObj.attrs[ZaCos.A_zmailMailMinPollingInterval] ;

		if(n_minPollingInterval != null && !AjxUtil.isLifeTime(n_minPollingInterval)) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailMinPollingInterval]));
			return false;
		}

		//var o_minPollingInterval = this.currentObject.attrs[ZaCos.A_zmailMailMinPollingInterval] ;
        var o_minPollingInterval = tmpObj.attrs[ZaCos.A_zmailMailMinPollingInterval] ;
		if (o_minPollingInterval != null && ZaUtil.getLifeTimeInSeconds (n_minPollingInterval)
			 > ZaUtil.getLifeTimeInSeconds(o_minPollingInterval)){
			 ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format (ZaMsg.tt_minPollingIntervalWarning, [o_minPollingInterval, n_minPollingInterval]),  true);
		}
	}


	if(ZaItem.hasWritePermission(ZaCos.A_zmailMailMessageLifetime,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailMailMessageLifetime] != null) {

			if(!AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailMailMessageLifetime])) {
				//show error msg
                ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailMessageLifetime]));
				return false;
			}
			var itestVal = parseInt(tmpObj.attrs[ZaCos.A_zmailMailMessageLifetime].substr(0, tmpObj.attrs[ZaCos.A_zmailMailMessageLifetime].length-1));
			if(itestVal > 0 && itestVal < 31) {
                ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_MESSAGE_LIFETIME_BELOW_31);
				return false;
			}
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailMailTrashLifetime,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailMailTrashLifetime] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailMailTrashLifetime])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailTrashLifetime]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailMailSpamLifetime,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailMailSpamLifetime] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailMailSpamLifetime])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailSpamLifetime]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailPasswordLockoutDuration,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailPasswordLockoutDuration] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailPasswordLockoutDuration])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordLockoutDuration]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailPasswordLockoutFailureLifetime,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailPasswordLockoutFailureLifetime] != null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaCos.A_zmailPasswordLockoutFailureLifetime])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordLockoutFailureLifetime]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailEnforcePwdHistory,tmpObj)) {
		if(tmpObj.attrs[ZaCos.A_zmailEnforcePwdHistory] != null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaCos.A_zmailEnforcePwdHistory])) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailEnforcePwdHistory]));
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailMaxMailItemsPerPage,tmpObj) && ZaItem.hasWritePermission(ZaCos.A_zmailPrefMailItemsPerPage,tmpObj)) {
		var maxItemsPerPage;
		if(tmpObj.attrs[ZaAccount.A_zmailMaxMailItemsPerPage] != null) {
			maxItemsPerPage = parseInt (tmpObj.attrs[ZaAccount.A_zmailMaxMailItemsPerPage]);
		} else {
			maxItemsPerPage = parseInt ( tmpObj._defaultValues.attrs[ZaAccount.A_zmailMaxMailItemsPerPage]);
		}

		var prefItemsPerPage;
		if(tmpObj.attrs[ZaAccount.A_zmailPrefMailItemsPerPage] != null) {
			prefItemsPerPage = parseInt (tmpObj.attrs[ZaAccount.A_zmailPrefMailItemsPerPage]);
		} else {
			prefItemsPerPage = parseInt ( tmpObj._defaultValues.attrs[ZaAccount.A_zmailPrefMailItemsPerPage]);
		}

		if(maxItemsPerPage < prefItemsPerPage) {
			//show error msg
            ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_ITEMS_PER_PAGE_OVER_MAX);
			return false;
		}
	}

	if(ZaItem.hasWritePermission(ZaCos.A_zmailAvailableSkin,tmpObj)) {
		//check that current theme is part of selected themes
		if(tmpObj.attrs[ZaCos.A_zmailAvailableSkin] !=null && tmpObj.attrs[ZaCos.A_zmailAvailableSkin].length > 0 && tmpObj.attrs[ZaCos.A_zmailPrefSkin] ) {
			var arr = tmpObj.attrs[ZaCos.A_zmailAvailableSkin] instanceof Array ? tmpObj.attrs[ZaCos.A_zmailAvailableSkin] : [tmpObj.attrs[ZaCos.A_zmailAvailableSkin]];
			var cnt = arr.length;
			var found=false;
			for(var i=0; i < cnt; i++) {
				if(arr[i]==tmpObj.attrs[ZaCos.A_zmailPrefSkin]) {
					found=true;
					break;
				}
			}
			if(!found) {
				//show error msg
                ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format (ZaMsg.COS_WarningCurrentThemeNotAvail, [tmpObj.attrs[ZaCos.A_zmailPrefSkin], tmpObj.attrs[ZaCos.A_zmailPrefSkin]]));
				return false;
			}
		}
	}
    return true;
}

ZaCos.getRelatedList =
function (parentPath) {
    var Tis = [];
    var count = this.countAllAccounts();
    if(count > 0) {
        var accountTi = new ZaTreeItemData({
                    text: ZaMsg.OVP_accounts,
                    count:count,
                    image:"Account",
                    mappingId: ZaZmailAdmin._COS_ACCOUNT_LIST_VIEW,
                    path: parentPath + ZaTree.SEPERATOR + this.name + ZaTree.SEPERATOR + ZaMsg.OVP_accounts
                    }
                );
        accountTi.setData("cosItem", this);
        ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._COS_ACCOUNT_LIST_VIEW] = ZaOverviewPanelController.accountListTreeListener;
        Tis.push(accountTi);
    }
    count = this.countAllDomains();
    if(count > 0) {
        var domainTi = new ZaTreeItemData({
                    text: ZaMsg.OVP_domains,
                    count:count,
                    image:"Domain",
                    mappingId: ZaZmailAdmin._COS_DOMAIN_LIST_VIEW,
                    path: parentPath + ZaTree.SEPERATOR + this.name + ZaTree.SEPERATOR + ZaMsg.OVP_domains
                    }
                );
        domainTi.setData("cosItem", this);
        ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._COS_DOMAIN_LIST_VIEW] = ZaOverviewPanelController.domainListTreeListener;
        Tis.push(domainTi);
    }
    return Tis;
}
ZaItem.getRelatedMethods["ZaCos"].push(ZaCos.getRelatedList);
