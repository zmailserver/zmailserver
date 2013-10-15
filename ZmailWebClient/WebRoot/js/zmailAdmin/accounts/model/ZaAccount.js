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
* @class ZaAccount
* @contructor ZaAccount
* @param ZaApp app
* this class is a model for zmailAccount ldap objects
* @author Roland Schemers
* @author Greg Solovyev
**/
ZaAccount = function(noInit) {
	if (noInit) return;	
	ZaItem.call(this, "ZaAccount");
	this._init();
	this.type = ZaItem.ACCOUNT;
}

ZaAccount.prototype = new ZaItem;
ZaAccount.prototype.constructor = ZaAccount;

ZaItem.loadMethods["ZaAccount"] = new Array();
ZaItem.initMethods["ZaAccount"] = new Array();
ZaItem.modifyMethods["ZaAccount"] = new Array();
ZaItem.createMethods["ZaAccount"] = new Array();
ZaItem.ObjectModifiers["ZaAccount"] = [];
ZaItem.modelExtensions["ZaAccount"] = new Array();
ZaItem.getRelatedMethods["ZaAccount"] = new Array();
ZaAccount.renameMethods = new Array();
ZaAccount.changePasswordMethods = new Array();

//object attributes
ZaAccount.A_name = "name";
ZaAccount.A_uid = "uid";
ZaAccount.A_accountName = "cn"; //contact name
ZaAccount.A_firstName = "givenName"; //first name
ZaAccount.A_lastName = "sn"; //last name
ZaAccount.A_mail = "mail";
ZaAccount.A_password = "password";
ZaAccount.A_description = "description";
ZaAccount.A_telephoneNumber = "telephoneNumber";
ZaAccount.A_homePhone = "homePhone" ;
ZaAccount.A_mobile = "mobile";
ZaAccount.A_pager = "pager" ;
ZaAccount.A_displayname = "displayName";
ZaAccount.A_country = "co"; //country
ZaAccount.A_company = "company";
ZaAccount.A_title = "title";
ZaAccount.A_facsimileTelephoneNumber = "facsimileTelephoneNumber";
ZaAccount.A_initials = "initials"; //middle initial
ZaAccount.A_city = "l";
ZaAccount.A_orgUnit = "ou";
ZaAccount.A_office = "physicalDeliveryOfficeName";
ZaAccount.A_street = "street";
ZaAccount.A_zip = "postalCode";
ZaAccount.A_state = "st";
ZaAccount.A_mailDeliveryAddress = "zmailMailDeliveryAddress";
ZaAccount.A_accountStatus = "zmailAccountStatus";
ZaAccount.A_notes = "zmailNotes";
ZaAccount.A_zmailMailQuota = "zmailMailQuota";
ZaAccount.A_mailHost = "zmailMailHost";
ZaAccount.A_zmailMailTransport = "zmailMailTransport";
ZaAccount.A_COSId = "zmailCOSId";

//Phonetic attribute
ZaAccount.A_zmailPhoneticFirstName = "zmailPhoneticFirstName";
ZaAccount.A_zmailPhoneticLastName = "zmailPhoneticLastName";
ZaAccount.A_zmailPhoneticCompany = "zmailPhoneticCompany";

ZaAccount.A_zmailIsAdminAccount = "zmailIsAdminAccount";
ZaAccount.A_zmailIsDelegatedAdminAccount = "zmailIsDelegatedAdminAccount";
ZaAccount.A_zmailIsExternalVirtualAccount = "zmailIsExternalVirtualAccount";
// system account
ZaAccount.A_zmailIsSystemAccount = "zmailIsSystemAccount";

ZaAccount.A_zmailMinPwdLength="zmailPasswordMinLength";
ZaAccount.A_zmailMaxPwdLength="zmailPasswordMaxLength";
ZaAccount.A_zmailPasswordMinUpperCaseChars = "zmailPasswordMinUpperCaseChars";
ZaAccount.A_zmailPasswordMinLowerCaseChars = "zmailPasswordMinLowerCaseChars";
ZaAccount.A_zmailPasswordMinPunctuationChars = "zmailPasswordMinPunctuationChars";
ZaAccount.A_zmailPasswordMinNumericChars = "zmailPasswordMinNumericChars";
ZaAccount.A_zmailPasswordMinDigitsOrPuncs = "zmailPasswordMinDigitsOrPuncs";
ZaAccount.A_zmailMinPwdAge="zmailPasswordMinAge";
ZaAccount.A_zmailMaxPwdAge="zmailPasswordMaxAge";
ZaAccount.A_zmailEnforcePwdHistory="zmailPasswordEnforceHistory";
ZaAccount.A_zmailMailAlias="zmailMailAlias";
ZaAccount.A_zmailMailForwardingAddress="zmailMailForwardingAddress";
ZaAccount.A_zmailPasswordMustChange="zmailPasswordMustChange";
ZaAccount.A_zmailPasswordLocked="zmailPasswordLocked";
ZaAccount.A_zmailContactMaxNumEntries = "zmailContactMaxNumEntries";
ZaAccount.A_zmailMailForwardingAddressMaxLength = "zmailMailForwardingAddressMaxLength";
ZaAccount.A_zmailMailForwardingAddressMaxNumAddrs = "zmailMailForwardingAddressMaxNumAddrs";
ZaAccount.A_zmailAttachmentsBlocked = "zmailAttachmentsBlocked";
ZaAccount.A_zmailQuotaWarnPercent = "zmailQuotaWarnPercent";
ZaAccount.A_zmailQuotaWarnInterval = "zmailQuotaWarnInterval";
ZaAccount.A_zmailQuotaWarnMessage = "zmailQuotaWarnMessage";
ZaAccount.A_zmailIsSystemResource = "zmailIsSystemResource";
ZaAccount.A_zmailExcludeFromCMBSearch = "zmailExcludeFromCMBSearch";

ZaAccount.A_zmailAdminAuthTokenLifetime  = "zmailAdminAuthTokenLifetime";
ZaAccount.A_zmailAuthTokenValidityValue = "zmailAuthTokenValidityValue";
ZaAccount.A_zmailAuthTokenLifetime = "zmailAuthTokenLifetime";
ZaAccount.A_zmailMailMessageLifetime = "zmailMailMessageLifetime";
ZaAccount.A_zmailMailSpamLifetime = "zmailMailSpamLifetime";
ZaAccount.A_zmailMailTrashLifetime = "zmailMailTrashLifetime";
ZaAccount.A_zmailMailIdleSessionTimeout = "zmailMailIdleSessionTimeout";
ZaAccount.A_zmailAvailableSkin = "zmailAvailableSkin";
ZaAccount.A_zmailZimletAvailableZimlets = "zmailZimletAvailableZimlets";

ZaAccount.A_zmailDataSourceMinPollingInterval = "zmailDataSourceMinPollingInterval";
ZaAccount.A_zmailDataSourcePop3PollingInterval = "zmailDataSourcePop3PollingInterval";
ZaAccount.A_zmailDataSourceImapPollingInterval = "zmailDataSourceImapPollingInterval";
ZaAccount.A_zmailDataSourceCalendarPollingInterval = "zmailDataSourceCalendarPollingInterval";
ZaAccount.A_zmailDataSourceRssPollingInterval = "zmailDataSourceRssPollingInterval";
ZaAccount.A_zmailDataSourceCaldavPollingInterval = "zmailDataSourceCaldavPollingInterval";


ZaAccount.A_zmailProxyAllowedDomains = "zmailProxyAllowedDomains";
ZaAccount.A_zmailIsCCAccount = "zmailIsCustomerCareAccount";
//prefs
ZaAccount.A_zmailPrefAppleIcalDelegationEnabled = "zmailPrefAppleIcalDelegationEnabled";
ZaAccount.A_zmailPrefCalendarShowPastDueReminders = "zmailPrefCalendarShowPastDueReminders";
ZaAccount.A_zmailPrefCalendarToasterEnabled = "zmailPrefCalendarToasterEnabled";
ZaAccount.A_zmailPrefCalendarAllowCancelEmailToSelf = "zmailPrefCalendarAllowCancelEmailToSelf";
ZaAccount.A_zmailPrefCalendarAllowPublishMethodInvite = "zmailPrefCalendarAllowPublishMethodInvite";
ZaAccount.A_zmailPrefCalendarAllowForwardedInvite = "zmailPrefCalendarAllowForwardedInvite";
ZaAccount.A_zmailPrefCalendarReminderFlashTitle = "zmailPrefCalendarReminderFlashTitle";
ZaAccount.A_zmailPrefCalendarNotifyDelegatedChanges = "zmailPrefCalendarNotifyDelegatedChanges";
ZaAccount.A_zmailPrefCalendarFirstDayOfWeek = "zmailPrefCalendarFirstDayOfWeek";
ZaAccount.A_zmailPrefCalendarInitialView = "zmailPrefCalendarInitialView";
ZaAccount.A_zmailPrefCalendarForwardInvitesTo = "zmailPrefCalendarForwardInvitesTo";
ZaAccount.A_zmailPrefClientType = "zmailPrefClientType";
ZaAccount.A_zmailPrefTimeZoneId = "zmailPrefTimeZoneId";
ZaAccount.A_zmailAllowAnyFromAddress = "zmailAllowAnyFromAddress";
ZaAccount.A_zmailAllowFromAddress = "zmailAllowFromAddress";
ZaAccount.A_zmailPrefCalendarAlwaysShowMiniCal = "zmailPrefCalendarAlwaysShowMiniCal";
ZaAccount.A_zmailPrefCalendarUseQuickAdd = "zmailPrefCalendarUseQuickAdd";
ZaAccount.A_zmailPrefSaveToSent="zmailPrefSaveToSent";
ZaAccount.A_zmailPrefMailSignature="zmailPrefMailSignature";
ZaAccount.A_zmailPrefMailSignatureEnabled="zmailPrefMailSignatureEnabled";
ZaAccount.A_zmailPrefSentMailFolder = "zmailPrefSentMailFolder";
ZaAccount.A_zmailPrefGroupMailBy = "zmailPrefGroupMailBy";
ZaAccount.A_zmailPrefIncludeSpamInSearch = "zmailPrefIncludeSpamInSearch";
ZaAccount.A_zmailPrefIncludeTrashInSearch = "zmailPrefIncludeTrashInSearch";
ZaAccount.A_zmailPrefMailInitialSearch = "zmailPrefMailInitialSearch";
ZaAccount.A_zmailMaxMailItemsPerPage = "zmailMaxMailItemsPerPage";
ZaAccount.A_zmailPrefMailItemsPerPage = "zmailPrefMailItemsPerPage";
ZaAccount.A_zmailPrefMailPollingInterval = "zmailPrefMailPollingInterval";
ZaAccount.A_zmailPrefAutoSaveDraftInterval = "zmailPrefAutoSaveDraftInterval";
ZaAccount.A_zmailPrefMailFlashTitle = "zmailPrefMailFlashTitle";
ZaAccount.A_zmailPrefMailFlashIcon = "zmailPrefMailFlashIcon" ;
ZaAccount.A_zmailPrefMailSoundsEnabled = "zmailPrefMailSoundsEnabled" ;
ZaAccount.A_zmailPrefMailToasterEnabled = "zmailPrefMailToasterEnabled";
ZaAccount.A_zmailMailMinPollingInterval = "zmailMailMinPollingInterval";
ZaAccount.A_zmailPrefOutOfOfficeFromDate = "zmailPrefOutOfOfficeFromDate";
ZaAccount.A_zmailPrefOutOfOfficeUntilDate = "zmailPrefOutOfOfficeUntilDate";
ZaAccount.A_zmailPrefOutOfOfficeReply = "zmailPrefOutOfOfficeReply";
ZaAccount.A_zmailPrefOutOfOfficeReplyEnabled = "zmailPrefOutOfOfficeReplyEnabled";
ZaAccount.A_zmailPrefReplyToAddress = "zmailPrefReplyToAddress";
ZaAccount.A_zmailPrefUseKeyboardShortcuts = "zmailPrefUseKeyboardShortcuts";
ZaAccount.A_zmailMemberOf = "zmailMemberOf";
ZaAccount.A_zmailPrefComposeInNewWindow = "zmailPrefComposeInNewWindow";
ZaAccount.A_zmailPrefForwardReplyInOriginalFormat = "zmailPrefForwardReplyInOriginalFormat";
ZaAccount.A_zmailPrefAutoAddAddressEnabled = "zmailPrefAutoAddAddressEnabled";
ZaAccount.A_zmailPrefComposeFormat = "zmailPrefComposeFormat";
ZaAccount.A_zmailPrefMessageViewHtmlPreferred = "zmailPrefMessageViewHtmlPreferred";
ZaAccount.A_zmailPrefNewMailNotificationAddress = "zmailPrefNewMailNotificationAddress";
ZaAccount.A_zmailPrefNewMailNotificationEnabled = "zmailPrefNewMailNotificationEnabled";
ZaAccount.A_zmailPrefOutOfOfficeReply = "zmailPrefOutOfOfficeReply";
ZaAccount.A_zmailPrefShowSearchString = "zmailPrefShowSearchString";
//ZaAccount.A_zmailPrefMailSignatureStyle = "zmailPrefMailSignatureStyle";
ZaAccount.A_zmailPrefUseTimeZoneListInCalendar = "zmailPrefUseTimeZoneListInCalendar";
ZaAccount.A_zmailPrefImapSearchFoldersEnabled = "zmailPrefImapSearchFoldersEnabled";
ZaAccount.A_zmailPrefMailForwardingAddress = "zmailPrefMailForwardingAddress";
ZaAccount.A_zmailPrefMailLocalDeliveryDisabled = "zmailPrefMailLocalDeliveryDisabled";
ZaAccount.A_zmailPrefCalendarApptReminderWarningTime = "zmailPrefCalendarApptReminderWarningTime";
ZaAccount.A_zmailPrefSkin = "zmailPrefSkin";
ZaAccount.A_zmailPrefGalAutoCompleteEnabled = "zmailPrefGalAutoCompleteEnabled";
ZaAccount.A_zmailPrefWarnOnExit = "zmailPrefWarnOnExit" ;
ZaAccount.A_zmailPrefShowSelectionCheckbox = "zmailPrefShowSelectionCheckbox" ;
ZaAccount.A_zmailPrefHtmlEditorDefaultFontSize = "zmailPrefHtmlEditorDefaultFontSize" ;
ZaAccount.A_zmailPrefHtmlEditorDefaultFontFamily = "zmailPrefHtmlEditorDefaultFontFamily" ;
ZaAccount.A_zmailPrefHtmlEditorDefaultFontColor = "zmailPrefHtmlEditorDefaultFontColor" ;
ZaAccount.A_zmailMailSignatureMaxLength = "zmailMailSignatureMaxLength" ;
ZaAccount.A_zmailPrefDisplayExternalImages = "zmailPrefDisplayExternalImages" ;
ZaAccount.A_zmailPrefOutOfOfficeCacheDuration = "zmailPrefOutOfOfficeCacheDuration";
ZaAccount.A_zmailPrefMailDefaultCharset = "zmailPrefMailDefaultCharset";
ZaAccount.A_zmailPrefLocale ="zmailPrefLocale" ;
ZaAccount.A_zmailJunkMessagesIndexingEnabled = "zmailJunkMessagesIndexingEnabled" ;
ZaAccount.A_zmailPrefMailSendReadReceipts = "zmailPrefMailSendReadReceipts";
ZaAccount.A_zmailPrefReadReceiptsToAddress = "zmailPrefReadReceiptsToAddress";
ZaAccount.A_zmailPrefAdminConsoleWarnOnExit = "zmailPrefAdminConsoleWarnOnExit" ;
ZaAccount.A_zmailPrefMandatorySpellCheckEnabled = "zmailPrefMandatorySpellCheckEnabled";
ZaAccount.A_zmailPrefMessageIdDedupingEnabled = "zmailPrefMessageIdDedupingEnabled";
ZaAccount.A_zmailPrefItemsPerVirtualPage="zmailPrefItemsPerVirtualPage",
//features
ZaAccount.A_zmailFeatureManageZimlets = "zmailFeatureManageZimlets";
ZaAccount.A_zmailFeatureImportFolderEnabled = "zmailFeatureImportFolderEnabled";
ZaAccount.A_zmailFeatureExportFolderEnabled = "zmailFeatureExportFolderEnabled";
ZaAccount.A_zmailDumpsterEnabled = "zmailDumpsterEnabled";
ZaAccount.A_zmailMailDumpsterLifetime = "zmailMailDumpsterLifetime";
ZaAccount.A_zmailDumpsterUserVisibleAge = "zmailDumpsterUserVisibleAge";
ZaAccount.A_zmailDumpsterPurgeEnabled = "zmailDumpsterPurgeEnabled";
ZaAccount.A_zmailPrefCalendarReminderSoundsEnabled = "zmailPrefCalendarReminderSoundsEnabled";
ZaAccount.A_zmailPrefCalendarSendInviteDeniedAutoReply = "zmailPrefCalendarSendInviteDeniedAutoReply";
ZaAccount.A_zmailPrefCalendarAutoAddInvites = "zmailPrefCalendarAutoAddInvites";
ZaAccount.A_zmailPrefCalendarApptVisibility = "zmailPrefCalendarApptVisibility";
ZaAccount.A_zmailFeatureReadReceiptsEnabled = "zmailFeatureReadReceiptsEnabled";
ZaAccount.A_zmailFeatureMailPriorityEnabled = "zmailFeatureMailPriorityEnabled";
//ZaAccount.A_zmailFeatureInstantNotify = "zmailFeatureInstantNotify";
ZaAccount.A_zmailFeatureImapDataSourceEnabled = "zmailFeatureImapDataSourceEnabled";
ZaAccount.A_zmailFeaturePop3DataSourceEnabled = "zmailFeaturePop3DataSourceEnabled";
ZaAccount.A_zmailFeatureMailSendLaterEnabled = "zmailFeatureMailSendLaterEnabled";
//ZaAccount.A_zmailFeatureFreeBusyViewEnabled = "zmailFeatureFreeBusyViewEnabled";
ZaAccount.A_zmailFeatureIdentitiesEnabled = "zmailFeatureIdentitiesEnabled";
ZaAccount.A_zmailFeatureMailForwardingEnabled = "zmailFeatureMailForwardingEnabled";
ZaAccount.A_zmailFeatureContactsEnabled="zmailFeatureContactsEnabled";
ZaAccount.A_zmailFeatureCalendarEnabled="zmailFeatureCalendarEnabled";
ZaAccount.A_zmailFeatureTasksEnabled="zmailFeatureTasksEnabled";
ZaAccount.A_zmailFeatureTaggingEnabled="zmailFeatureTaggingEnabled";
ZaAccount.A_zmailFeaturePeopleSearchEnabled = "zmailFeaturePeopleSearchEnabled";
ZaAccount.A_zmailFeatureAdvancedSearchEnabled="zmailFeatureAdvancedSearchEnabled";
ZaAccount.A_zmailFeatureSavedSearchesEnabled="zmailFeatureSavedSearchesEnabled";
ZaAccount.A_zmailFeatureConversationsEnabled="zmailFeatureConversationsEnabled";
ZaAccount.A_zmailFeatureChangePasswordEnabled="zmailFeatureChangePasswordEnabled";
ZaAccount.A_zmailFeatureInitialSearchPreferenceEnabled="zmailFeatureInitialSearchPreferenceEnabled";
ZaAccount.A_zmailFeatureFiltersEnabled="zmailFeatureFiltersEnabled";
ZaAccount.A_zmailFeatureGalEnabled="zmailFeatureGalEnabled";
ZaAccount.A_zmailFeatureMAPIConnectorEnabled = "zmailFeatureMAPIConnectorEnabled";
ZaAccount.A_zmailFeatureSharingEnabled="zmailFeatureSharingEnabled";
ZaAccount.A_zmailPublicSharingEnabled="zmailPublicSharingEnabled";
ZaAccount.A_zmailExternalSharingEnabled="zmailExternalSharingEnabled";
//ZaAccount.A_zmailFeatureNotebookEnabled = "zmailFeatureNotebookEnabled";
ZaAccount.A_zmailFeatureBriefcasesEnabled = "zmailFeatureBriefcasesEnabled";
ZaAccount.A_zmailFeatureHtmlComposeEnabled = "zmailFeatureHtmlComposeEnabled";
ZaAccount.A_zmailFeatureGalAutoCompleteEnabled = "zmailFeatureGalAutoCompleteEnabled";
ZaAccount.A_zmailImapEnabled = "zmailImapEnabled";
ZaAccount.A_zmailPop3Enabled = "zmailPop3Enabled";
ZaAccount.A_zmailFeatureSkinChangeEnabled = "zmailFeatureSkinChangeEnabled";
ZaAccount.A_zmailFeatureOutOfOfficeReplyEnabled = "zmailFeatureOutOfOfficeReplyEnabled";
ZaAccount.A_zmailFeatureNewMailNotificationEnabled = "zmailFeatureNewMailNotificationEnabled";
ZaAccount.A_zmailFeatureMailPollingIntervalPreferenceEnabled = "zmailFeatureMailPollingIntervalPreferenceEnabled" ;
ZaAccount.A_zmailHideInGal = "zmailHideInGal";
ZaAccount.A_zmailMailCanonicalAddress = "zmailMailCanonicalAddress";
ZaAccount.A_zmailMailCatchAllAddress = "zmailMailCatchAllAddress" ;
ZaAccount.A_zmailFeatureOptionsEnabled = "zmailFeatureOptionsEnabled";
//ZaAccount.A_zmailFeatureShortcutAliasesEnabled = "zmailFeatureShortcutAliasesEnabled" ;
ZaAccount.A_zmailFeatureMailEnabled = "zmailFeatureMailEnabled" ;
ZaAccount.A_zmailFeatureGroupCalendarEnabled = "zmailFeatureGroupCalendarEnabled" ;
//ZaAccount.A_zmailFeatureIMEnabled = "zmailFeatureIMEnabled" ;
ZaAccount.A_zmailFeatureFlaggingEnabled = "zmailFeatureFlaggingEnabled" ;
ZaAccount.A_zmailForeignPrincipal = "zmailForeignPrincipal" ;
//security
ZaAccount.A_zmailPasswordLockoutEnabled = "zmailPasswordLockoutEnabled";
ZaAccount.A_zmailPasswordLockoutDuration = "zmailPasswordLockoutDuration";
ZaAccount.A_zmailPasswordLockoutMaxFailures = "zmailPasswordLockoutMaxFailures";
ZaAccount.A_zmailPasswordLockoutFailureLifetime = "zmailPasswordLockoutFailureLifetime";
ZaAccount.A_zmailAdminConsoleUIComponents = "zmailAdminConsoleUIComponents";
ZaAccount.A_zmailAuthLdapExternalDn = "zmailAuthLdapExternalDn";

ZaAccount.A_zmailFreebusyExchangeUserOrg = "zmailFreebusyExchangeUserOrg" ;
ZaAccount.A_zmailFeatureManageSMIMECertificateEnabled = "zmailFeatureManageSMIMECertificateEnabled";
ZaAccount.A_zmailFeatureSMIMEEnabled = "zmailFeatureSMIMEEnabled";
ZaAccount.A_zmailFeatureDistributionListFolderEnabled = "zmailFeatureDistributionListFolderEnabled";

ZaAccount.A_zmailFeatureCalendarReminderDeviceEmailEnabled = "zmailFeatureCalendarReminderDeviceEmailEnabled";

//readonly
ZaAccount.A_zmailLastLogonTimestamp = "zmailLastLogonTimestamp";
ZaAccount.A_zmailPasswordModifiedTime = "zmailPasswordModifiedTime";


ZaAccount.ACCOUNT_STATUS_ACTIVE = "active";
ZaAccount.ACCOUNT_STATUS_MAINTENANCE = "maintenance";
ZaAccount.ACCOUNT_STATUS_LOCKED = "locked";
ZaAccount.ACCOUNT_STATUS_LOCKOUT = "lockout";
ZaAccount.ACCOUNT_STATUS_CLOSED = "closed";
ZaAccount.ACCOUNT_STATUS_PENDING = "pending" ;

//this attributes are not used in the XML object, but is used in the model
ZaAccount.A2_ldap_ds = "ldap_ds";
ZaAccount.A2_zmail_ds = "zmail_ds";
ZaAccount.A2_datasources = "datasources";
ZaAccount.A2_confirmPassword = "confirmPassword";
ZaAccount.A2_mbxsize = "mbxSize";
ZaAccount.A2_quota = "quota2";
ZaAccount.A2_autodisplayname = "autodisplayname";
ZaAccount.A2_autoMailServer = "automailserver";
ZaAccount.A2_autoCos = "autoCos" ;
ZaAccount.A2_myCOS = "mycos";
ZaAccount.A2_newAlias = "newalias";

//ZaAccount.A2_newForward = "newforward";
ZaAccount.A2_aliases = "aliases";
ZaAccount.A2_forwarding = "forwardings";

//Group (Member Of tab needed)
ZaAccount.A2_memberOf = "memberOf" ;
//ZaAccount.A2_isgroup = "isgroup" ;
ZaAccount.A2_directMemberList = "directMemberList" ;
ZaAccount.A2_indirectMemberList = "indirectMemberList";
ZaAccount.A2_nonMemberList = "nonMemberList" ;
ZaAccount.A2_nonMemberListSelected = "nonMemberListSelected" ;
ZaAccount.A2_indirectMemberListSelected = "indirectMemberListSelected" ;
ZaAccount.A2_directMemberListSelected = "directMemberListSelected" ;
ZaAccount.A2_showSameDomain = "showSameDomain" ;
ZaAccount.A2_domainLeftAccounts = "leftDomainAccounts" ;
ZaAccount.A2_publicMailURL = "publicMailURL";
ZaAccount.A2_adminSoapURL = "adminSoapURL";
ZaAccount.A2_soapURL = "soapURL";
ZaAccount.MAXSEARCHRESULTS = ZaSettings.MAXSEARCHRESULTS;
ZaAccount.RESULTSPERPAGE = ZaSettings.RESULTSPERPAGE;

ZaAccount.A2_accountTypes = "accountTypes" ; //used to save the account types available to this account based on domain
ZaAccount.A2_currentAccountType = "currentAccountType" ; //used to save the current account type - cos id
ZaAccount.A2_alias_selection_cache = "alias_selection_cache";
ZaAccount.A2_fwdAddr_selection_cache = "fwdAddr_selection_cache";
ZaAccount.A2_calFwdAddr_selection_cache = "calFwdAddr_selection_cache";
ZaAccount.A2_fp_selection_cache = "fp_selection_cache"; 
ZaAccount.A2_errorMessage = "errorMessage";
ZaAccount.A2_warningMessage = "warningMessage";
ZaAccount.A2_showAccountTypeMsg = "showAccountTypeMsg";
ZaAccount.A2_isExternalAuth = "isExternalAuth";
//constants for rights

ZaAccount.SET_PASSWORD_RIGHT = "setAccountPassword";
ZaAccount.CHANGE_PASSWORD_RIGHT = "changeAccountPassword"; // Enable password policy
ZaAccount.RENAME_ACCOUNT_RIGHT = "renameAccount";
ZaAccount.REINDEX_MBX_RIGHT = "reindexMailbox";
ZaAccount.DELETE_ACCOUNT_RIGHT = "deleteAccount";
ZaAccount.GET_MBX_DUMP_RIGHT = "getMailboxDump";
ZaAccount.VIEW_MAIL_RIGHT = "adminLoginAs";
ZaAccount.ADD_ACCOUNT_ALIAS_RIGHT = "addAccountAlias";
ZaAccount.REMOVE_ACCOUNT_ALIAS_RIGHT = "removeAccountAlias";
ZaAccount.GET_ACCOUNT_MEMBERSHIP_RIGHT = "getAccountMembership";
ZaAccount.GET_MAILBOX_INFO_RIGHT = "getMailboxInfo";
ZaAccount.GET_ACCOUNT_INFO_RIGHT = "getAccountInfo";
ZaAccount.RIGHT_VIEW_ADMINUI_COMPONENTS = "viewAccountAdminUI";
ZaAccount.RIGHT_DELETE_DL = "deleteDistributionList";
ZaAccount.checkValues = 
function(tmpObj) {
	/**
	* check values
	**/

	if(ZaItem.hasWritePermission(ZaAccount.A_name,tmpObj) && (tmpObj.name == null || tmpObj.name.length < 1)) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_ACCOUNT_NAME_REQUIRED);
		return false;
	}

    if(ZaItem.hasWritePermission(ZaAccount.A_name,tmpObj) && ( tmpObj.name.length > 255)) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_ACCOUNT_NAME_TOOLONG);
		return false;
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_lastName,tmpObj) && (tmpObj.attrs[ZaAccount.A_lastName] == null || tmpObj.attrs[ZaAccount.A_lastName].length < 1)) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_ACCOUNT_LAST_NAME_REQUIRED);
		return false;
	}

	/*if(!AjxUtil.EMAIL_SHORT_RE.test(tmpObj.name) ) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_ACCOUNT_NAME_INVALID);
		return false;
	}*/
	if(ZaItem.hasWritePermission(ZaAccount.A_name,tmpObj) && !AjxUtil.isValidEmailNonReg(tmpObj.name)) {
		ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_ACCOUNT_NAME_INVALID);
		return false;		
	}

	var maxPwdLen = Number.POSITIVE_INFINITY;
	var minPwdLen = 0;	
	var maxPwdAge = Number.POSITIVE_INFINITY;
	var minPwdAge = 0;		


	//validate this account's password constraints
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMinPwdLength,tmpObj) && tmpObj.attrs[ZaAccount.A_zmailMinPwdLength] != "" && tmpObj.attrs[ZaAccount.A_zmailMinPwdLength] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailMinPwdLength])) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMinPwdLength])) ;
		return false;
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMaxPwdLength,tmpObj) && tmpObj.attrs[ZaAccount.A_zmailMaxPwdLength] != "" && tmpObj.attrs[ZaAccount.A_zmailMaxPwdLength] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailMaxPwdLength])) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMaxPwdLength])) ;
		return false;
	}	
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMaxPwdLength,tmpObj) && tmpObj.attrs[ZaAccount.A_zmailMaxPwdLength])
		tmpObj.attrs[ZaAccount.A_zmailMaxPwdLength] = parseInt(tmpObj.attrs[ZaAccount.A_zmailMaxPwdLength]);
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMinPwdLength,tmpObj) && tmpObj.attrs[ZaAccount.A_zmailMinPwdLength])
		tmpObj.attrs[ZaAccount.A_zmailMinPwdLength] = parseInt(tmpObj.attrs[ZaAccount.A_zmailMinPwdLength]);
		
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMinPwdAge,tmpObj) && tmpObj.attrs[ZaAccount.A_zmailMinPwdAge] != "" && tmpObj.attrs[ZaAccount.A_zmailMinPwdAge] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailMinPwdAge])) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMinPwdAge])) ;		
		return false;
	}		
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMaxPwdAge,tmpObj) && tmpObj.attrs[ZaAccount.A_zmailMaxPwdAge] != "" && tmpObj.attrs[ZaAccount.A_zmailMaxPwdAge] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailMaxPwdAge])) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMaxPwdAge])) ;
		return false;
	}		
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMinPwdAge,tmpObj) && tmpObj.attrs[ZaAccount.A_zmailMinPwdAge])
		tmpObj.attrs[ZaAccount.A_zmailMinPwdAge] = parseInt(tmpObj.attrs[ZaAccount.A_zmailMinPwdAge]);
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMaxPwdAge,tmpObj) && tmpObj.attrs[ZaCos.A_zmailMaxPwdAge])
		tmpObj.attrs[ZaCos.A_zmailMaxPwdAge] = parseInt(tmpObj.attrs[ZaCos.A_zmailMaxPwdAge]);
	
	//validate password length against this account's or COS setting
	//if the account did not have a valid cos id - pick the first COS
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMinPwdLength,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailMinPwdLength] != null) {
			minPwdLen = parseInt(tmpObj.attrs[ZaAccount.A_zmailMinPwdLength]);
		} else {
			minPwdLen = parseInt(tmpObj._defaultValues.attrs[ZaAccount.A_zmailMinPwdLength]);
		}
	}	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMaxPwdLength,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailMaxPwdLength] != null) {
			maxPwdLen = parseInt (tmpObj.attrs[ZaAccount.A_zmailMaxPwdLength]);
		} else {
			maxPwdLen = parseInt (tmpObj._defaultValues.attrs[ZaAccount.A_zmailMaxPwdLength]);
		}
	}
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMaxPwdLength,tmpObj) || ZaItem.hasWritePermission(ZaAccount.A_zmailMinPwdLength,tmpObj)) {	
		if(maxPwdLen < minPwdLen) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_MAX_MIN_PWDLENGTH);
			return false;		
		}
	}		

	//validate password age settings
	//if the account did not have a valid cos id - pick the first COS
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMaxPwdLength,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailMaxPwdAge] != null) {
			maxPwdAge = parseInt (tmpObj.attrs[ZaAccount.A_zmailMaxPwdAge]);
		} else {
			maxPwdAge = parseInt ( tmpObj._defaultValues.attrs[ZaAccount.A_zmailMaxPwdAge]);
		}
	}
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMinPwdAge,tmpObj)) {	
		if(tmpObj.attrs[ZaAccount.A_zmailMinPwdAge] != null) {
			minPwdAge = parseInt (tmpObj.attrs[ZaAccount.A_zmailMinPwdAge]);
		} else {
			minPwdAge = parseInt (tmpObj._defaultValues.attrs[ZaCos.A_zmailMinPwdAge]);
		}
	}		
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMaxPwdLength,tmpObj) || ZaItem.hasWritePermission(ZaAccount.A_zmailMinPwdLength,tmpObj)) {	
		if(maxPwdAge < minPwdAge) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_MAX_MIN_PWDAGE);
			return false;		
		}	
	}
	//if there is a password - validate it
	if(ZaItem.hasAnyRight([ZaAccount.SET_PASSWORD_RIGHT, ZaAccount.CHANGE_PASSWORD_RIGHT],tmpObj)) {
		if(!AjxUtil.isEmpty(tmpObj.attrs[ZaAccount.A_password]) || !AjxUtil.isEmpty(tmpObj[ZaAccount.A2_confirmPassword])) {
			if(tmpObj.attrs[ZaAccount.A_password] != tmpObj[ZaAccount.A2_confirmPassword]) {
				//show error msg
				ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_PASSWORD_MISMATCH);
				return false;
			} 			
			if(tmpObj.attrs[ZaAccount.A_password].length < minPwdLen || AjxStringUtil.trim(tmpObj.attrs[ZaAccount.A_password]).length < minPwdLen) { 
				//show error msg
                var minpassMsg;
                if (minPwdLen > 1) {
                    minpassMsg =  String(ZaMsg.NAD_passMinLengthMsg_p).replace("{0}",minPwdLen);
                } else {
                    minpassMsg =  String(ZaMsg.NAD_passMinLengthMsg_s).replace("{0}",minPwdLen);
                }
				ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_PASSWORD_TOOSHORT + "<br>" + minpassMsg);
				return false;		
			}
			
			if(AjxStringUtil.trim(tmpObj.attrs[ZaAccount.A_password]).length > maxPwdLen) { 
				//show error msg
				//show error msg
                var maxpassMsg;
                if (maxPwdLen > 1) {
                    maxpassMsg =  String(ZaMsg.NAD_passMaxLengthMsg_p).replace("{0}",maxPwdLen);
                } else {
                    maxpassMsg =  String(ZaMsg.NAD_passMaxLengthMsg_s).replace("{0}",maxPwdLen);
                }
				ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_PASSWORD_TOOLONG+ "<br>" + maxpassMsg);
				return false;		
			}
		}
	}
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMailQuota,tmpObj)) {		
		if(tmpObj.attrs[ZaAccount.A_zmailMailQuota] != "" && tmpObj.attrs[ZaAccount.A_zmailMailQuota] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailMailQuota])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailQuota])) ;
			return false;
		}
	}
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailContactMaxNumEntries,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailContactMaxNumEntries] != "" && tmpObj.attrs[ZaAccount.A_zmailContactMaxNumEntries] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailContactMaxNumEntries])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailContactMaxNumEntries])) ;
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMailForwardingAddressMaxLength,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailMailForwardingAddressMaxLength] != "" && tmpObj.attrs[ZaAccount.A_zmailMailForwardingAddressMaxLength] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailMailForwardingAddressMaxLength])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailForwardingAddressMaxLength])) ;
			return false;
		}
	}	
        if(ZaItem.hasWritePermission(ZaAccount.A_zmailDataSourceMinPollingInterval,tmpObj)) {
                var min_dataInterval = tmpObj.attrs[ZaAccount.A_zmailDataSourceMinPollingInterval] ;
                if( min_dataInterval != "" && min_dataInterval !=null && !AjxUtil.isLifeTime(min_dataInterval)) {
                        ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailDataSourceMinPollingInterval])) ;
                        return false;
                }
        }
        if(ZaItem.hasWritePermission(ZaAccount.A_zmailDataSourcePop3PollingInterval,tmpObj)) {
                var p_dataInterval = tmpObj.attrs[ZaAccount.A_zmailDataSourcePop3PollingInterval] ;
                if( p_dataInterval != "" && p_dataInterval !=null && !AjxUtil.isLifeTime(p_dataInterval)) {
                        ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailDataSourcePop3PollingInterval])) ;
                        return false;
                }
                if (min_dataInterval == "" || min_dataInterval == null){
                        min_dataInterval = tmpObj._defaultValues.attrs[ZaAccount.A_zmailDataSourceMinPollingInterval];
                }
                if(p_dataInterval != null && min_dataInterval != null) {
                        if (ZaUtil.getLifeTimeInSeconds(p_dataInterval) < ZaUtil.getLifeTimeInSeconds(min_dataInterval)){
                                ZaApp.getInstance().getCurrentController().popupErrorDialog (ZaMsg.tt_mailPollingIntervalError + min_dataInterval) ;
                                return false ;
                        }
                }
        }
        if(ZaItem.hasWritePermission(ZaAccount.A_zmailDataSourceImapPollingInterval,tmpObj)) {
                var p_dataInterval = tmpObj.attrs[ZaAccount.A_zmailDataSourceImapPollingInterval] ;
                if( p_dataInterval != "" && p_dataInterval !=null && !AjxUtil.isLifeTime(p_dataInterval)) {
                        ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailDataSourceImapPollingInterval])) ;
                        return false;
                }
                if (min_dataInterval == "" || min_dataInterval == null){
                	min_dataInterval = tmpObj._defaultValues.attrs[ZaAccount.A_zmailDataSourceMinPollingInterval];
                }
                if(p_dataInterval != null && min_dataInterval != null) {
                        if (ZaUtil.getLifeTimeInSeconds(p_dataInterval) < ZaUtil.getLifeTimeInSeconds(min_dataInterval)){
                                ZaApp.getInstance().getCurrentController().popupErrorDialog (ZaMsg.tt_mailPollingIntervalError + min_dataInterval) ;
                                return false ;
                        }
                }
        }
        if(ZaItem.hasWritePermission(ZaAccount.A_zmailDataSourceCalendarPollingInterval,tmpObj)) {
                var p_dataInterval = tmpObj.attrs[ZaAccount.A_zmailDataSourceCalendarPollingInterval] ;
                if( p_dataInterval != "" && p_dataInterval !=null && !AjxUtil.isLifeTime(p_dataInterval)) {
                        ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailDataSourceCalendarPollingInterval])) ;
                        return false;
                }
                if (min_dataInterval == "" || min_dataInterval == null){
                	min_dataInterval = tmpObj._defaultValues.attrs[ZaAccount.A_zmailDataSourceMinPollingInterval];
                }
                if(p_dataInterval != null && min_dataInterval != null) {
                        if (ZaUtil.getLifeTimeInSeconds(p_dataInterval) < ZaUtil.getLifeTimeInSeconds(min_dataInterval)){
                                ZaApp.getInstance().getCurrentController().popupErrorDialog (ZaMsg.tt_mailPollingIntervalError + min_dataInterval) ;
                                return false ;
                        }
                }
        }
        if(ZaItem.hasWritePermission(ZaAccount.A_zmailDataSourceRssPollingInterval,tmpObj)) {
                var p_dataInterval = tmpObj.attrs[ZaAccount.A_zmailDataSourceRssPollingInterval] ;
                if( p_dataInterval != "" && p_dataInterval !=null && !AjxUtil.isLifeTime(p_dataInterval)) {
                        ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailDataSourceRssPollingInterval])) ;
                        return false;
                }
                if (min_dataInterval == "" || min_dataInterval == null){
                	min_dataInterval = tmpObj._defaultValues.attrs[ZaAccount.A_zmailDataSourceMinPollingInterval];
                }
                if(p_dataInterval != null && min_dataInterval != null) {
                        if (ZaUtil.getLifeTimeInSeconds(p_dataInterval) < ZaUtil.getLifeTimeInSeconds(min_dataInterval)){
                                ZaApp.getInstance().getCurrentController().popupErrorDialog (ZaMsg.tt_mailPollingIntervalError + min_dataInterval) ;
                                return false ;
                        }
                }
        }
        if(ZaItem.hasWritePermission(ZaAccount.A_zmailDataSourceCaldavPollingInterval,tmpObj)) {
                var p_dataInterval = tmpObj.attrs[ZaAccount.A_zmailDataSourceCaldavPollingInterval] ;
                if( p_dataInterval != "" && p_dataInterval !=null && !AjxUtil.isLifeTime(p_dataInterval)) {
                        ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailDataSourceCaldavPollingInterval])) ;
                        return false;
                }
                if (min_dataInterval == "" || min_dataInterval == null){
                	min_dataInterval = tmpObj._defaultValues.attrs[ZaAccount.A_zmailDataSourceMinPollingInterval]; 
                }
                if(p_dataInterval != null && min_dataInterval != null) {
                        if (ZaUtil.getLifeTimeInSeconds(p_dataInterval) < ZaUtil.getLifeTimeInSeconds(min_dataInterval)){
                                ZaApp.getInstance().getCurrentController().popupErrorDialog (ZaMsg.tt_mailPollingIntervalError + min_dataInterval) ;
                                return false ;
                        }
                }
        }

        if(ZaItem.hasWritePermission(ZaAccount.A_zmailPrefAutoSaveDraftInterval,tmpObj)) {
                var p_autoSaveInterval = tmpObj.attrs[ZaAccount.A_zmailPrefAutoSaveDraftInterval] ;
                if( p_autoSaveInterval != "" && p_autoSaveInterval !=null && !AjxUtil.isLifeTime(p_autoSaveInterval)) {
                        ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPrefAutoSaveDraftInterval])) ;
                        return false;
                }
        }
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMailForwardingAddressMaxNumAddrs,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailMailForwardingAddressMaxNumAddrs] != "" && tmpObj.attrs[ZaAccount.A_zmailMailForwardingAddressMaxNumAddrs] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailMailForwardingAddressMaxNumAddrs])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailForwardingAddressMaxNumAddrs])) ;		
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailContactMaxNumEntries,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailContactMaxNumEntries])
			tmpObj.attrs[ZaAccount.A_zmailContactMaxNumEntries] = parseInt	(tmpObj.attrs[ZaAccount.A_zmailContactMaxNumEntries]);
	}

	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordMinUpperCaseChars,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordMinUpperCaseChars] != "" && tmpObj.attrs[ZaAccount.A_zmailPasswordMinUpperCaseChars] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailPasswordMinUpperCaseChars])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordMinUpperCaseChars])) ;
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordMinUpperCaseChars,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordMinUpperCaseChars])
			tmpObj.attrs[ZaAccount.A_zmailPasswordMinUpperCaseChars] = parseInt	(tmpObj.attrs[ZaAccount.A_zmailPasswordMinUpperCaseChars]);
	}

	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordMinLowerCaseChars,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordMinLowerCaseChars] != "" && tmpObj.attrs[ZaAccount.A_zmailPasswordMinLowerCaseChars] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailPasswordMinLowerCaseChars])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordMinLowerCaseChars])) ;		
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordMinLowerCaseChars,tmpObj)) {	
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordMinLowerCaseChars])
			tmpObj.attrs[ZaAccount.A_zmailPasswordMinLowerCaseChars] = parseInt	(tmpObj.attrs[ZaAccount.A_zmailPasswordMinLowerCaseChars]);
	}

	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordMinPunctuationChars,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordMinPunctuationChars] != "" && tmpObj.attrs[ZaAccount.A_zmailPasswordMinPunctuationChars] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailPasswordMinPunctuationChars])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordMinPunctuationChars])) ;		
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordMinPunctuationChars,tmpObj)) {	
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordMinPunctuationChars])
			tmpObj.attrs[ZaAccount.A_zmailPasswordMinPunctuationChars] = parseInt	(tmpObj.attrs[ZaAccount.A_zmailPasswordMinPunctuationChars]);
	}
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordMinNumericChars,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordMinNumericChars] != "" && tmpObj.attrs[ZaAccount.A_zmailPasswordMinNumericChars] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailPasswordMinNumericChars])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordMinNumericChars])) ;
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordMinNumericChars,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordMinNumericChars])
			tmpObj.attrs[ZaAccount.A_zmailPasswordMinNumericChars] = parseInt	(tmpObj.attrs[ZaAccount.A_zmailPasswordMinNumericChars]);
	}

	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordMinDigitsOrPuncs,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordMinDigitsOrPuncs] != "" && tmpObj.attrs[ZaAccount.A_zmailPasswordMinDigitsOrPuncs] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailPasswordMinDigitsOrPuncs])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordMinDigitsOrPuncs])) ;
			return false;
		}
	}	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordMinDigitsOrPuncs,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordMinDigitsOrPuncs])
			tmpObj.attrs[ZaAccount.A_zmailPasswordMinDigitsOrPuncs] = parseInt	(tmpObj.attrs[ZaAccount.A_zmailPasswordMinDigitsOrPuncs]);
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailAuthTokenLifetime,tmpObj)) {			
		if(tmpObj.attrs[ZaAccount.A_zmailAuthTokenLifetime] != "" && tmpObj.attrs[ZaAccount.A_zmailAuthTokenLifetime] !=null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaAccount.A_zmailAuthTokenLifetime])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailAuthTokenLifetime])) ;
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailAdminAuthTokenLifetime,tmpObj)) {	
		if(tmpObj.attrs[ZaAccount.A_zmailAdminAuthTokenLifetime] != "" && tmpObj.attrs[ZaAccount.A_zmailAdminAuthTokenLifetime] !=null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaAccount.A_zmailAdminAuthTokenLifetime])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailAdminAuthTokenLifetime])) ;
			return false;
		}	
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPrefOutOfOfficeCacheDuration,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailPrefOutOfOfficeCacheDuration] != "" && tmpObj.attrs[ZaAccount.A_zmailPrefOutOfOfficeCacheDuration] !=null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaAccount.A_zmailPrefOutOfOfficeCacheDuration])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPrefOutOfOfficeCacheDuration])) ;
			return false;
		}
	}
		
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPrefMailPollingInterval,tmpObj)) {
		var p_mailPollingInterval = tmpObj.attrs[ZaAccount.A_zmailPrefMailPollingInterval] ;
		if( p_mailPollingInterval != "" && p_mailPollingInterval !=null && !AjxUtil.isLifeTime(p_mailPollingInterval)) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPrefMailPollingInterval])) ;
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMailMinPollingInterval,tmpObj)) {
		var min_mailPollingInterval = tmpObj.attrs[ZaAccount.A_zmailMailMinPollingInterval]
		if( min_mailPollingInterval != "" && min_mailPollingInterval !=null && !AjxUtil.isLifeTime(min_mailPollingInterval)) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailMinPollingInterval])) ;
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMailMinPollingInterval,tmpObj)) {	
		if (min_mailPollingInterval == "" || min_mailPollingInterval == null) {
			//take the cos value
			min_mailPollingInterval = tmpObj._defaultValues.attrs[ZaAccount.A_zmailMailMinPollingInterval];
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPrefMailPollingInterval,tmpObj)) {
		if (p_mailPollingInterval == "" || p_mailPollingInterval == null){
			p_mailPollingInterval = tmpObj._defaultValues.attrs[ZaAccount.A_zmailPrefMailPollingInterval];
		}
		if(p_mailPollingInterval != null && min_mailPollingInterval != null) {
			if (ZaUtil.getLifeTimeInSeconds(p_mailPollingInterval) < ZaUtil.getLifeTimeInSeconds(min_mailPollingInterval)){
				ZaApp.getInstance().getCurrentController().popupErrorDialog (ZaMsg.tt_mailPollingIntervalError + min_mailPollingInterval) ;
				return false ;
			}
		}
	}
			
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMailIdleSessionTimeout,tmpObj)) {		
		if(tmpObj.attrs[ZaAccount.A_zmailMailIdleSessionTimeout] != "" && tmpObj.attrs[ZaAccount.A_zmailMailIdleSessionTimeout] !=null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaAccount.A_zmailMailIdleSessionTimeout])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailIdleSessionTimeout])) ;
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMailMessageLifetime,tmpObj)) {		
		if(tmpObj.attrs[ZaAccount.A_zmailMailMessageLifetime] != "" && tmpObj.attrs[ZaAccount.A_zmailMailMessageLifetime] !=null) {
			if(!AjxUtil.isLifeTime(tmpObj.attrs[ZaAccount.A_zmailMailMessageLifetime])) {
				//show error msg
				ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailMessageLifetime])) ;
				return false;
			}
			var itestVal = parseInt(tmpObj.attrs[ZaAccount.A_zmailMailMessageLifetime].substr(0, tmpObj.attrs[ZaAccount.A_zmailMailMessageLifetime].length-1));			
			if(itestVal > 0 && itestVal < 31) {
				ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_MESSAGE_LIFETIME_BELOW_31);
				return false;
			}
		}			
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMailTrashLifetime,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailMailTrashLifetime] != "" && tmpObj.attrs[ZaAccount.A_zmailMailTrashLifetime] !=null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaAccount.A_zmailMailTrashLifetime])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailTrashLifetime])) ;
			return false;
		}	
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMailSpamLifetime,tmpObj)) {	
		if(tmpObj.attrs[ZaAccount.A_zmailMailSpamLifetime] != "" && tmpObj.attrs[ZaAccount.A_zmailMailSpamLifetime] != 0 && tmpObj.attrs[ZaAccount.A_zmailMailSpamLifetime] !=null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaAccount.A_zmailMailSpamLifetime])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailMailSpamLifetime])) ;
			return false;
		}		
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordLockoutFailureLifetime,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordLockoutFailureLifetime] != "" && tmpObj.attrs[ZaAccount.A_zmailPasswordLockoutFailureLifetime] !=null && !AjxUtil.isLifeTime(tmpObj.attrs[ZaAccount.A_zmailPasswordLockoutFailureLifetime])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordLockoutFailureLifetime])) ;		
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordLockoutDuration,tmpObj)) {	
		if(tmpObj.attrs[ZaAccount.A_zmailPasswordLockoutDuration] != "" && tmpObj.attrs[ZaAccount.A_zmailPasswordLockoutDuration] !=null && tmpObj.attrs[ZaAccount.A_zmailPasswordLockoutDuration] !=0 && !AjxUtil.isLifeTime(tmpObj.attrs[ZaAccount.A_zmailPasswordLockoutDuration])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailPasswordLockoutDuration])) ;
			return false;
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailEnforcePwdHistory,tmpObj)) {
		if(tmpObj.attrs[ZaAccount.A__zmailEnforcePwdHistory] != "" && tmpObj.attrs[ZaAccount.A_zmailEnforcePwdHistory] !=null && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaAccount.A_zmailEnforcePwdHistory])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.MSG_zmailEnforcePwdHistory])) ;
			return false;
		}	
		if(tmpObj.attrs[ZaAccount.A_zmailEnforcePwdHistory])
			tmpObj.attrs[ZaAccount.A_zmailEnforcePwdHistory] = parseInt(tmpObj.attrs[ZaAccount.A_zmailEnforcePwdHistory]);
	}
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailMaxMailItemsPerPage,tmpObj)) {	
		var maxItemsPerPage;
		if(tmpObj.attrs[ZaAccount.A_zmailMaxMailItemsPerPage] != null) {
			maxItemsPerPage = parseInt (tmpObj.attrs[ZaAccount.A_zmailMaxMailItemsPerPage]);
		} else {
			maxItemsPerPage = parseInt ( tmpObj._defaultValues.attrs[ZaAccount.A_zmailMaxMailItemsPerPage]);
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPrefMailItemsPerPage,tmpObj)) {	
		var prefItemsPerPage;
		if(tmpObj.attrs[ZaAccount.A_zmailPrefMailItemsPerPage] != null) {
			prefItemsPerPage = parseInt (tmpObj.attrs[ZaAccount.A_zmailPrefMailItemsPerPage]);
		} else {
			prefItemsPerPage = parseInt ( tmpObj._defaultValues.attrs[ZaAccount.A_zmailPrefMailItemsPerPage]);
		}
	}
	
	if(ZaItem.hasWritePermission(ZaAccount.A_zmailPrefMailItemsPerPage,tmpObj) && ZaItem.hasWritePermission(ZaAccount.A_zmailMaxMailItemsPerPage,tmpObj)) {		
		if(maxItemsPerPage < prefItemsPerPage) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_ITEMS_PER_PAGE_OVER_MAX);
			return false;		
		}	
	}

    if(ZaItem.hasWritePermission(ZaAccount.A_zmailPasswordLockoutMaxFailures,tmpObj)&& tmpObj.attrs[ZaAccount.A_zmailPasswordLockoutMaxFailures] && !AjxUtil.isInt(tmpObj.attrs[ZaAccount.A_zmailPasswordLockoutMaxFailures])) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog( AjxMessageFormat.format(ZaMsg.ERROR_VALUE_NOT_INTEGER,ZaAccount.A_zmailPasswordLockoutMaxFailures));
			return false;
	}

	if(ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.ACCOUNTS_SKIN_TAB] || ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CARTE_BLANCHE_UI]) {
		//check that current theme is part of selected themes
		var currentTheme = tmpObj.attrs[ZaAccount.A_zmailPrefSkin] ? tmpObj.attrs[ZaAccount.A_zmailPrefSkin] : tmpObj._defaultValues.attrs[ZaCos.A_zmailPrefSkin];
		var availableThemes = tmpObj.attrs[ZaAccount.A_zmailAvailableSkin] ? tmpObj.attrs[ZaAccount.A_zmailAvailableSkin] : tmpObj._defaultValues.attrs[ZaCos.A_zmailAvailableSkin];	
	
		if(currentTheme && availableThemes) {
			var arr = availableThemes instanceof Array ? availableThemes : [availableThemes];
			var cnt = arr.length;
			var found=false;
			for(var i=0; i < cnt; i++) {
				if(arr[i]==currentTheme) {
					found=true;
					break;
				}
			}
			if(!found) {
				//show error msg
				ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format (ZaMsg.NAD_WarningCurrentThemeNotAvail, [currentTheme, currentTheme]));
				return false;			
			}
		}	
	}		

    if (!ZaAccount.isAccountTypeSet(tmpObj))  {
        ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_ACCOUNT_TYPE_NOT_SET);
        return false;
    }
    
    return true;
}

/**
* Creates a new ZaAccount. This method makes SOAP request to create a new account record. 
* @param tmpObj
* @param app {ZaApp}
* @param account {ZaAccount}
**/
ZaAccount.createMethod = 
function (tmpObj, account) {
	tmpObj.attrs[ZaAccount.A_mail] = tmpObj.name.replace(/[\s]+/g,"");	
	var resp;	
	//create SOAP request
	var soapDoc = AjxSoapDoc.create("CreateAccountRequest", ZaZmailAdmin.URN, null);
	soapDoc.set(ZaAccount.A_name, tmpObj.attrs[ZaAccount.A_mail]);
	if(tmpObj.attrs[ZaAccount.A_password] && tmpObj.attrs[ZaAccount.A_password].length > 0)
		soapDoc.set(ZaAccount.A_password, tmpObj.attrs[ZaAccount.A_password]);
		
	if(tmpObj[ZaAccount.A2_autoMailServer] == "TRUE") {
		tmpObj.attrs[ZaAccount.A_mailHost] = null;
	}
	
	//check if we need to set the cosId
	if (tmpObj[ZaAccount.A2_autoCos] == "TRUE" ) {
		tmpObj.attrs[ZaAccount.A_COSId] = null ;
	}
	
	for (var aname in tmpObj.attrs) {
		if(aname == ZaAccount.A_password || aname == ZaAccount.A_zmailMailAlias || aname == ZaItem.A_objectClass || aname == ZaAccount.A2_mbxsize || aname == ZaAccount.A_mail) {
			continue;
		}	
		
		if(tmpObj.attrs[aname] instanceof Array) {
			var cnt = tmpObj.attrs[aname].length;
			if(cnt) {
				for(var ix=0; ix <cnt; ix++) {
					if(typeof(tmpObj.attrs[aname][ix])=="object") {
						var attr = soapDoc.set("a", tmpObj.attrs[aname][ix].toString());
						attr.setAttribute("n", aname);
					} else {
						var attr = soapDoc.set("a", tmpObj.attrs[aname][ix]);
						attr.setAttribute("n", aname);						
					}
				}
			} 
		} else if (tmpObj.attrs[aname] instanceof AjxVector) {
			var tmpArray = tmpObj.attrs[aname].getArray();
			var cnt = tmpArray.length;
			if(cnt) {
				for(var ix=0; ix <cnt; ix++) {
					if(tmpArray[ix] !=null) {
						if(typeof(tmpArray[ix])=="object") {
							var attr = soapDoc.set("a", tmpArray[ix].toString());
							attr.setAttribute("n", aname);
						} else {
							var attr = soapDoc.set("a", tmpArray[ix]);
							attr.setAttribute("n", aname);
						}
					}
				}
			} 			
			
		} else {	
			if(tmpObj.attrs[aname] != null) {
				if(typeof(tmpObj.attrs[aname]) == "object") {				
					var attr = soapDoc.set("a", tmpObj.attrs[aname].toString());
					attr.setAttribute("n", aname);
				} else {
					var attr = soapDoc.set("a", tmpObj.attrs[aname]);
					attr.setAttribute("n", aname);					
				}
			}
		}
	}
	try {

		//var createAccCommand = new ZmCsfeCommand();
		var csfeParams = new Object();
		csfeParams.soapDoc = soapDoc;	
		var reqMgrParams = {} ;
		reqMgrParams.controller = ZaApp.getInstance().getCurrentController();
		reqMgrParams.busyMsg = ZaMsg.BUSY_CREATE_ACCOUNTS ;
		//reqMgrParams.busyMsg = "Creating Accounts ...";
		//resp = createAccCommand.invoke(params).Body.CreateAccountResponse;
		resp = ZaRequestMgr.invoke(csfeParams, reqMgrParams ).Body.CreateAccountResponse;
	} catch (ex) {
		throw ex;
		return null;
	}
	
	account.initFromJS(resp.account[0]);		
	//add aliases
	if(tmpObj.attrs[ZaAccount.A_zmailMailAlias].length) {
		var tmpObjCnt = tmpObj.attrs[ZaAccount.A_zmailMailAlias].length;
		var failedAliases = "";
		var failedAliasesCnt = 0;
		try {
			for(var ix=0; ix < tmpObjCnt; ix++) {
				try {
                    if(tmpObj.attrs[ZaAccount.A_zmailMailAlias][ix]!="") {
                        account.addAlias(tmpObj.attrs[ZaAccount.A_zmailMailAlias][ix]);
					    account.attrs[ZaAccount.A_zmailMailAlias].push(tmpObj.attrs[ZaAccount.A_zmailMailAlias][ix]);
                    }
				} catch (ex) {
					if(ex.code == ZmCsfeException.ACCT_EXISTS) {
						//if failed because account exists just show a warning
						failedAliases += ("<br>" + tmpObj.attrs[ZaAccount.A_zmailMailAlias][ix]);
						failedAliasesCnt++;
					} else {
						//if failed for another reason - jump out
						throw (ex);
					}
				}
			}
			if(failedAliasesCnt == 1) {
				ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.WARNING_ALIAS_EXISTS, [failedAliases]));
			} else if(failedAliasesCnt > 1) {
				ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.WARNING_ALIASES_EXIST, [failedAliases]));
			}
		} catch (ex) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.FAILED_ADD_ALIASES, ex);
			return null;
		}	
	}	
}
ZaItem.createMethods["ZaAccount"].push(ZaAccount.createMethod);

/**
* @method modify
* Updates ZaAccount attributes (SOAP)
* @param mods set of modified attributes and their new values
*/
ZaAccount.modifyMethod =
function(mods) {
	var gotSomething = false;
	//update the object
	var soapDoc = AjxSoapDoc.create("ModifyAccountRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("id", this.id);
	for (var aname in mods) {
		gotSomething = true;
		//multy value attribute
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
		
	//var modifyAccCommand = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	var reqMgrParams = {
		controller:ZaApp.getInstance().getCurrentController(),
		busyMsg: ZaMsg.BUSY_MODIFY_ACCOUNT 
	} ;
	
	//resp = modifyAccCommand.invoke(params).Body.ModifyAccountResponse;
	resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.ModifyAccountResponse ;
	
	this.initFromJS(resp.account[0]);
	this[ZaAccount.A2_confirmPassword] = null;
	//invalidate the original tooltip
	this._toolTip = null ;
	return;
}
ZaItem.modifyMethods["ZaAccount"].push(ZaAccount.modifyMethod);



ZaAccount.getViewMailLink = 
function(accId) {
	var retVal={authToken:"", lifetime:0};
	var soapDoc = AjxSoapDoc.create("DelegateAuthRequest", ZaZmailAdmin.URN, null);	
	var attr = soapDoc.set("account", accId);
	attr.setAttribute("by", "id");
	
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	//var resp = command.invoke(params).Body.DelegateAuthResponse;
	var reqMgrParams = {
		controller: ZaApp.getInstance().getCurrentController ()
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.DelegateAuthResponse ; 
	retVal.authToken = resp.authToken[0]._content;
	retVal.lifetime = resp.lifetime;
	
	return retVal;
}

ZaReindexMailbox = function() {
	this.status = null;
	this.numSucceeded = 0;
	this.numFailed = 0;	
	this.numRemaining = 0;	
	this.numTotal = 100;	
	this.numDone = 0;	
	this.progressMsg = ZaMsg.NAD_ACC_ReindexingNotRunning;
	this.mbxId = null;
	this.resultMsg = null;
	this.errorDetail = null;
	this.pollInterval = 500;	
}
ZaReindexMailbox.A_status = "status";
ZaReindexMailbox.A_numSucceeded = "numSucceeded";
ZaReindexMailbox.A_numFailed = "numFailed";
ZaReindexMailbox.A_numRemaining = "numRemaining";
ZaReindexMailbox.A_mbxId = "mbxId";
ZaReindexMailbox.A_numTotal = "numTotal";
ZaReindexMailbox.A_numDone = "numDone";
ZaReindexMailbox.A_pollInterval = "pollInterval";
ZaReindexMailbox.A_progressMsg = "progressMsg";
ZaReindexMailbox.A_errorDetail = "errorDetail";
ZaReindexMailbox.A_resultMsg = "resultMsg";

ZaReindexMailbox.myXModel = {
	items: [
		{id:ZaReindexMailbox.A_status, ref:ZaReindexMailbox.A_status, type:_STRING_},						
		{id:ZaReindexMailbox.A_numSucceeded, ref:ZaReindexMailbox.A_numSucceeded, type:_NUMBER_},								
		{id:ZaReindexMailbox.A_numFailed, ref:ZaReindexMailbox.A_numFailed, type:_NUMBER_},										
		{id:ZaReindexMailbox.A_numRemaining, ref:ZaReindexMailbox.A_numRemaining, type:_NUMBER_},												
		{id:ZaReindexMailbox.A_mbxId, ref:ZaReindexMailbox.A_mbxId, type:_STRING_},														
		{id:ZaReindexMailbox.A_numTotal, ref:ZaReindexMailbox.A_numTotal, type:_NUMBER_},								
		{id:ZaReindexMailbox.A_numDone, ref:ZaReindexMailbox.A_numDone, type:_NUMBER_},							
		{id:ZaReindexMailbox.A_pollInterval, ref:ZaReindexMailbox.A_pollInterval, type:_STRING_},
		{id:ZaReindexMailbox.A_progressMsg, ref:ZaReindexMailbox.A_progressMsg, type:_STRING_},
		{id:ZaReindexMailbox.A_resultMsg, ref:ZaReindexMailbox.A_pollInterval, type:_STRING_},
		{id:ZaReindexMailbox.A_errorDetail, ref:ZaReindexMailbox.A_pollInterval, type:_STRING_}
	]
};

ZaAccount.prototype.remove = 
function(callback) {
	var soapDoc;
	if(this[ZaAccount.A2_ldap_ds] || this[ZaAccount.A2_zmail_ds]) {
		soapDoc = AjxSoapDoc.create("DeleteGalSyncAccountRequest", ZaZmailAdmin.URN, null);
		var accEl = soapDoc.set("account", this.id);
		accEl.setAttribute("by", "id");
	} else {
		soapDoc = AjxSoapDoc.create("DeleteAccountRequest", ZaZmailAdmin.URN, null);
		soapDoc.set("id", this.id);
	}
	
	this.deleteCommand = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;
	params.noAuthToken = true;	
	if(callback) {
		params.asyncMode = true;
		params.callback = callback;
	}
	this.deleteCommand.invoke(params);		
}

ZaAccount.getReindexStatus = 
function (mbxId, callback) {
	var soapDoc = AjxSoapDoc.create("ReIndexRequest", ZaZmailAdmin.URN, null);
	soapDoc.getMethod().setAttribute("action", "status");
	var attr = soapDoc.set("mbox", null);
	attr.setAttribute("id", mbxId);
	var resp = null;
	try {
		var command = new ZmCsfeCommand();
		var params = new Object();
		params.soapDoc = soapDoc;
		params.noAuthToken = true;	
		if(callback) {
			params.asyncMode = true;
			params.callback = callback;
			command.invoke(params);	
		} else {
			resp = command.invoke(params);	
		}
		
	} catch (ex) {
		if(ex.code == "service.NOT_IN_PROGRESS") {
			resp = null;
		} else {
			throw (ex);
		}
	}
	return resp;
}

ZaAccount.startReindexMailbox = 
function (mbxId, callback) {
	var soapDoc = AjxSoapDoc.create("ReIndexRequest", ZaZmailAdmin.URN, null);
	soapDoc.getMethod().setAttribute("action", "start");
	var attr = soapDoc.set("mbox", null);
	attr.setAttribute("id", mbxId);
	
	var resp;
	try {
		var command = new ZmCsfeCommand();
		var params = new Object();
		params.soapDoc = soapDoc;
		params.noAuthToken = true;	
		if(callback) {
			params.asyncMode = true;
			params.callback = callback;
			command.invoke(params);	
		} else {
			resp = command.invoke(params);	
		}

	} catch (ex) {
		resp = ex;
	}
	return resp;
}

ZaAccount.abortReindexMailbox = 
function (mbxId, callback) {
	var soapDoc = AjxSoapDoc.create("ReIndexRequest", ZaZmailAdmin.URN, null);
	soapDoc.getMethod().setAttribute("action", "cancel");
	var attr = soapDoc.set("mbox", null);
	attr.setAttribute("id", mbxId);
	var resp;
	try {
		var command = new ZmCsfeCommand();
		var params = new Object();
		params.soapDoc = soapDoc;
		params.noAuthToken = true;	
		if(callback) {
			params.asyncMode = true;
			params.callback = callback;
			command.invoke(params);	
		} else {
			resp = command.invoke(params);	
		}	
	} catch (ex) {
		resp = ex;
	}
	return resp;
}

ZaAccount.parseReindexResponse =
function (respObj, instance, form) {
	if(!respObj)
		return;
	if(respObj.isException && respObj.isException()) {
		var errCode = respObj.getException().code;
		if(errCode && errCode == "service.NOT_IN_PROGRESS") {
			form.setInstanceValue("", ZaReindexMailbox.A_errorDetail);
			form.setInstanceValue("", ZaReindexMailbox.A_resultMsg);
			form.setInstanceValue(ZaMsg.NAD_ACC_ReindexingNotRunning, ZaReindexMailbox.A_progressMsg);
			if(instance.numRemaining > 0 || instance.status == "started") {
				form.setInstanceValue(instance.numTotal, ZaReindexMailbox.A_numDone);
				form.setInstanceValue(ZaMsg.NAD_ACC_ReindexingComplete, ZaReindexMailbox.A_progressMsg);
				form.setInstanceValue("complete", ZaReindexMailbox.A_status);
			} else {
				form.setInstanceValue(null, ZaReindexMailbox.A_status);
				//instance.status = null;
			}
		} else if(errCode && errCode == ZmCsfeException.EMPTY_RESPONSE) {
			form.setInstanceValue(ZaMsg.ERROR_RECEIVED_EMPTY_RESPONSE,ZaReindexMailbox.A_resultMsg);
			form.setInstanceValue(null,ZaReindexMailbox.A_errorDetail);
			form.setInstanceValue("error", ZaReindexMailbox.A_status);
		} else {
			var detail = respObj.getException().detail;
			var msg = respObj.getException().msg;
			var strBuf = [];
			if(detail) {
				strBuf.push(detail);
			}	
			if(msg) {
				strBuf.push(msg);
			}
			form.setInstanceValue(AjxMessageFormat.format(ZaMsg.FAILED_REINDEX,[errCode]),ZaReindexMailbox.A_resultMsg);
			form.setInstanceValue(strBuf.join("\n"),ZaReindexMailbox.A_errorDetail);
			form.setInstanceValue("error", ZaReindexMailbox.A_status);
		}
	} else  {
		var resp;
		if(respObj.getResponse) {
			resp = respObj.getResponse();
		} else if(respObj.Body.ReIndexResponse) {
			resp = respObj;
		}
		if(resp && resp.Body.ReIndexResponse) {
			if(resp.Body.ReIndexResponse.status == "idle") {
				form.setInstanceValue("", ZaReindexMailbox.A_errorDetail);
				form.setInstanceValue("", ZaReindexMailbox.A_resultMsg);
				form.setInstanceValue(ZaMsg.NAD_ACC_ReindexingNotRunning, ZaReindexMailbox.A_progressMsg);
				if(instance.numRemaining > 0 || instance.status == "started") {
					form.setInstanceValue(instance.numTotal, ZaReindexMailbox.A_numDone);
					form.setInstanceValue(ZaMsg.NAD_ACC_ReindexingComplete, ZaReindexMailbox.A_progressMsg);
					form.setInstanceValue("complete", ZaReindexMailbox.A_status);
				} else {
					form.setInstanceValue(null, ZaReindexMailbox.A_status);
				}				
				form.setInstanceValue(resp.Body.ReIndexResponse.status, ZaReindexMailbox.A_status);
			} else {
				form.setInstanceValue(resp.Body.ReIndexResponse.status, ZaReindexMailbox.A_status);
				if(resp.Body.ReIndexResponse.status == "started") {
					form.setInstanceValue(0, ZaReindexMailbox.A_numDone);
					form.setInstanceValue(ZaMsg.NAD_ACC_ReindexingStarted, ZaReindexMailbox.A_progressMsg);
				}
				if(resp.Body.ReIndexResponse.progress && resp.Body.ReIndexResponse.progress[0]) {
					var progress = resp.Body.ReIndexResponse.progress[0];
					
					form.setInstanceValue(progress.numFailed, ZaReindexMailbox.A_numFailed);
					form.setInstanceValue(progress.numSucceeded, ZaReindexMailbox.A_numSucceeded);
					form.setInstanceValue(progress.numRemaining, ZaReindexMailbox.A_numRemaining);
					form.setInstanceValue(progress.numSucceeded + progress.numFailed + progress.numRemaining, ZaReindexMailbox.A_numTotal);
					form.setInstanceValue(progress.numFailed + progress.numSucceeded, ZaReindexMailbox.A_numDone);
					form.setInstanceValue(AjxMessageFormat.format(ZaMsg.NAD_ACC_ReindexingStatus,[progress.numSucceeded,progress.numRemaining,progress.numFailed]), ZaReindexMailbox.A_progressMsg);
					
					if(instance.status == "cancelled") {
						form.setInstanceValue((instance.progressMsg + "<br>" + ZaMsg.NAD_ACC_ReindexingCancelled), ZaReindexMailbox.A_progressMsg);
					}			
					if(instance.numRemaining == 0) {
						form.setInstanceValue(instance.numTotal, ZaReindexMailbox.A_numDone);
					}					
				}
			}
		}
	}
}

ZaAccount.prototype.initFromDom =
function(node) {
	this.name = node.getAttribute("name");
	this.id = node.getAttribute("id");
	this.attrs[ZaAccount.A_zmailMailAlias] = new Array();
	this.attrs[ZaAccount.A_zmailMailForwardingAddress] = new Array();
	this.attrs[ZaAccount.A_zmailPrefCalendarForwardInvitesTo] = new Array();
	this.attrs[ZaAccount.A_zmailAllowFromAddress] = new Array();
    this.attrs[ZaAccount.A_zmailForeignPrincipal ] = [];
    var children = node.childNodes;
	for (var i=0; i< children.length;  i++) {
		child = children[i];
		if (child.nodeName != 'a') continue;
		var name = child.getAttribute("n");
		if (child.firstChild != null) {
			var value = child.firstChild.nodeValue;
			if (name in this.attrs) {
				var vc = this.attrs[name];
				if ((typeof vc) == "object") {
					vc.push(value);
				} else {
					this.attrs[name] = [vc, value];
				}
			} else {
				this.attrs[name] = value;
			}
		}
	}
}

ZaAccount.prototype.initFromJS = 
function (account) {
	if(!account)
		return;
		
	this.attrs = new Object();	
	this.attrs[ZaAccount.A_zmailMailAlias] = new Array();
	this.name = account.name;
	this.id = account.id;
	this.isExternal = account.isExternal;
	var len = account.a.length;
	this.attrs[ZaAccount.A_zmailMailAlias] = new Array();
	this.attrs[ZaAccount.A_zmailMailForwardingAddress] = new Array();	
	this.attrs[ZaAccount.A_zmailPrefCalendarForwardInvitesTo] = new Array();	
	this.attrs[ZaAccount.A_zmailAllowFromAddress] = new Array();
    this.attrs[ZaAccount.A_zmailForeignPrincipal ] = [];
    for(var ix = 0; ix < len; ix++) {
		if(!this.attrs[[account.a[ix].n]]) {
			this.attrs[[account.a[ix].n]] = account.a[ix]._content;
		} else {
			if(!(this.attrs[[account.a[ix].n]] instanceof Array)) {
				this.attrs[[account.a[ix].n]] = [this.attrs[[account.a[ix].n]]];
			} 
			this.attrs[[account.a[ix].n]].push(account.a[ix]._content);
		}
	}
	if(!(this.attrs[ZaAccount.A_description] instanceof Array)) {
		this.attrs[ZaAccount.A_description] = [this.attrs[ZaAccount.A_description]];
	}
	
	if(!this.attrs[ZaItem.A_zmailId] && this.id) {
		this.attrs[ZaItem.A_zmailId] = this.id;
	}

	if(this.attrs[ZaAccount.A_zmailProxyAllowedDomains] &&
       (!(this.attrs[ZaAccount.A_zmailProxyAllowedDomains] instanceof Array)) ) {
		this.attrs[ZaAccount.A_zmailProxyAllowedDomains] = [this.attrs[ZaAccount.A_zmailProxyAllowedDomains]];
	}
}

/**
* Returns HTML for a tool tip for this account.
*/
ZaAccount.prototype.getToolTip =
function() {
	// update/null if modified
	if (!this._toolTip) {
		var html = new Array(20);
		var idx = 0;
		html[idx++] = "<table cellpadding='0' cellspacing='0' border='0'>";
		html[idx++] = "<tr valign='center'><td colspan='2' align='left'>";
		html[idx++] = "<div style='border-bottom: 1px solid black; white-space:nowrap; overflow:hidden;width:350' >";
		html[idx++] = "<table cellpadding='0' cellspacing='0' border='0' style='width:100%;'>";
		html[idx++] = "<tr valign='center'>";
		html[idx++] = "<td><b>" + AjxStringUtil.htmlEncode(this.name) + "</b></td>";
		html[idx++] = "<td align='right'>";
		if( this.attrs[ZaAccount.A_zmailIsAdminAccount]=="TRUE" ) {
			html[idx++] = AjxImg.getImageHtml("AdminUser");
		} else if (this.attrs[ZaAccount.A_zmailIsDelegatedAdminAccount] == "TRUE") {
			html[idx++] = AjxImg.getImageHtml("DomainAdminUser");
		} else if (this.attrs[ZaAccount.A_zmailIsSystemResource] == "TRUE") {
			html[idx++] = AjxImg.getImageHtml("SystemResource");
		} else {
			html[idx++] = AjxImg.getImageHtml("Account");
		}
		  html[idx++] = "</td>";
                html[idx++] = "</table></div></td></tr>";
                html[idx++] = "<tr></tr>";

                if(this.isExternal) {
                        idx = this._addRow(ZaMsg.NAD_MailServer, this.attrs[ZaAccount.A_zmailMailTransport], html, idx);
                        html[idx++] = "<tr valign='top'><td align='left' style='padding-right: 5px;' colspan='2'><b>";
                        html[idx++] = AjxStringUtil.htmlEncode(ZaMsg.externalAccountNote);
                        html[idx++] = "</b></td>";
                        html[idx++] = "</td></tr>";
                } else {
                        idx = this._addRow(ZaMsg.NAD_MailServer, this.attrs[ZaAccount.A_mailHost], html, idx);
                }
                idx = this._addAttrRow(ZaItem.A_zmailId, html, idx);
                html[idx++] = "</table>";
                this._toolTip = html.join("");
		
	}
	return this._toolTip;
}



ZaAccount.loadMethod = 
function(by, val) {
	var soapDoc, params, resp;
	//batch the rest of the requests
	soapDoc = AjxSoapDoc.create("BatchRequest", "urn:zmail");
    soapDoc.setMethodAttribute("onerror", "continue");

	if(by=="id") {
		this.id = val;
		this.attrs[ZaItem.A_zmailId] = val;
		var getAccDoc = soapDoc.set("GetAccountRequest", null, null, ZaZmailAdmin.URN);
		getAccDoc.setAttribute("applyCos", "0");
		if(!this.getAttrs.all && !AjxUtil.isEmpty(this.attrsToGet)) {
			getAccDoc.setAttribute("attrs", this.attrsToGet.join(","));
		}
		var elBy = soapDoc.set("account", val, getAccDoc);
		elBy.setAttribute("by", by);		
	} else {
		var getAccDoc = AjxSoapDoc.create("GetAccountRequest", ZaZmailAdmin.URN, null);
		getAccDoc.getMethod().setAttribute("applyCos", "0");
		if(!this.getAttrs.all && !AjxUtil.isEmpty(this.attrsToGet)) {
			getAccDoc.getMethod().setAttribute("attrs", this.attrsToGet.join(","));
		}
		var elBy = getAccDoc.set("account", val);
		elBy.setAttribute("by", by);

		//var getAccCommand = new ZmCsfeCommand();
		var params = new Object();
		params.soapDoc = getAccDoc;	
		var reqMgrParams = {
			controller:ZaApp.getInstance().getCurrentController()
		}
		resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.GetAccountResponse;
		this.attrs = new Object();
		this.initFromJS(resp.account[0]);
	}
	
	if(!AjxUtil.isEmpty(this.attrs[ZaAccount.A_mailHost]) && ZaItem.hasRight(ZaAccount.GET_MAILBOX_INFO_RIGHT,this) && this.attrs[ZaAccount.A_zmailIsExternalVirtualAccount] != "TRUE") {
		var getMailboxReq = soapDoc.set("GetMailboxRequest", null, null, ZaZmailAdmin.URN);
		var mbox = soapDoc.set("mbox", "", getMailboxReq);
		mbox.setAttribute("id", this.attrs[ZaItem.A_zmailId]);
	}				
	this[ZaAccount.A2_confirmPassword] = null;
	
	//Make a GetAccountMembershipRequest
	if(ZaItem.hasRight(ZaAccount.GET_ACCOUNT_MEMBERSHIP_RIGHT,this)) {
		var getAccMembershipReq = soapDoc.set("GetAccountMembershipRequest", null, null, ZaZmailAdmin.URN);
		var account = soapDoc.set("account", this.attrs[ZaItem.A_zmailId], getAccMembershipReq);
		account.setAttribute("by","id");		
	}
	
	if(ZaItem.hasRight(ZaAccount.GET_ACCOUNT_INFO_RIGHT,this)) {
		var getAccInfoReq = soapDoc.set("GetAccountInfoRequest", null, null, ZaZmailAdmin.URN);
		var account = soapDoc.set("account", this.attrs[ZaItem.A_zmailId], getAccInfoReq);
		account.setAttribute("by","id");		
	}

	if(ZaItem.hasRight(ZaAccount.VIEW_MAIL_RIGHT, this)) {
		var getDSReq = soapDoc.set("GetDataSourcesRequest", null, null, ZaZmailAdmin.URN);
		var elId = soapDoc.set("id", this.attrs[ZaItem.A_zmailId], getDSReq);
	}
	
    var hasError = false ;
    var lastException  ;
	if(by=="id" || 
		ZaItem.hasRight(ZaAccount.GET_ACCOUNT_INFO_RIGHT,this) || ZaItem.hasRight(ZaAccount.GET_ACCOUNT_MEMBERSHIP_RIGHT,this) ||
		(!AjxUtil.isEmpty(this.attrs[ZaAccount.A_mailHost]) && ZaItem.hasRight(ZaAccount.GET_MAILBOX_INFO_RIGHT,this)) ) {
		try {
			params = new Object();
			params.soapDoc = soapDoc;	
			var reqMgrParams ={
				controller:ZaApp.getInstance().getCurrentController()
			}
			var respObj = ZaRequestMgr.invoke(params, reqMgrParams);
			if(respObj.isException && respObj.isException()) {
				ZaApp.getInstance().getCurrentController()._handleException(respObj.getException(), "ZaAccount.loadMethod", null, false);
			    hasError  = true ;
                lastException = ex ;
            } else if(respObj.Body.BatchResponse.Fault) {
				var fault = respObj.Body.BatchResponse.Fault;
				if(fault instanceof Array)
					fault = fault[0];
			
				if (fault) {
					// JS response with fault
					var ex = ZmCsfeCommand.faultToEx(fault);
					ZaApp.getInstance().getCurrentController()._handleException(ex,"ZaAccount.loadMethod", null, false);
                    hasError = true ;
                    lastException = ex ;
                }
			} else {
				var batchResp = respObj.Body.BatchResponse;
				
				if(batchResp.GetAccountResponse) {
					resp = batchResp.GetAccountResponse[0];
					this.initFromJS(resp.account[0]);
				}
				
				if(batchResp.GetMailboxResponse) {
					resp = batchResp.GetMailboxResponse[0];
					if(resp && resp.mbox && resp.mbox[0]) {
						this.attrs[ZaAccount.A2_mbxsize] = resp.mbox[0].s;
					}
				}
				
				if(batchResp.GetAccountMembershipResponse) {
					resp = batchResp.GetAccountMembershipResponse[0];
					this[ZaAccount.A2_memberOf] = ZaAccountMemberOfListView.parseGetAccMembershipResponse(resp) ;
					this[ZaAccount.A2_directMemberList + "_more"] = 
						(this[ZaAccount.A2_memberOf][ZaAccount.A2_directMemberList].length > ZaAccountMemberOfListView.SEARCH_LIMIT) ? 1: 0;
					this[ZaAccount.A2_indirectMemberList + "_more"] = 
						(this[ZaAccount.A2_memberOf][ZaAccount.A2_indirectMemberList].length > ZaAccountMemberOfListView.SEARCH_LIMIT) ? 1: 0;
				}
				
				if(batchResp.GetAccountInfoResponse) {
					resp = batchResp.GetAccountInfoResponse[0];
					if(resp[ZaAccount.A2_publicMailURL] && resp[ZaAccount.A2_publicMailURL][0])
						this[ZaAccount.A2_publicMailURL] = resp[ZaAccount.A2_publicMailURL][0]._content;
					
					if(resp[ZaAccount.A2_adminSoapURL] && resp[ZaAccount.A2_adminSoapURL][0])
						this[ZaAccount.A2_adminSoapURL] = resp[ZaAccount.A2_adminSoapURL][0]._content;
					
					if(resp[ZaAccount.A2_soapURL] && resp[ZaAccount.A2_soapURL][0])
						this[ZaAccount.A2_soapURL] = resp[ZaAccount.A2_soapURL][0]._content;
				
				    if (resp.cos && resp.cos.id)
				        this[ZaAccount.A2_currentAccountType] = resp.cos.id ;					
				}
				
				if(batchResp.GetDataSourcesResponse && batchResp.GetDataSourcesResponse instanceof Array && batchResp.GetDataSourcesResponse[0]) {
					this[ZaAccount.A2_datasources] = new ZaItemList(ZaDataSource);
					this[ZaAccount.A2_datasources].loadFromJS(batchResp.GetDataSourcesResponse[0]);
					var dss = this[ZaAccount.A2_datasources].getArray(); 
					if(dss && dss.length) {
						for(var i=0; i < dss.length; i++) {
							if(dss[i].attrs[ZaDataSource.A_zmailDataSourceType] == ZaDataSource.DS_TYPE_GAL) {
								if(dss[i].attrs[ZaDataSource.A_zmailGalType] == ZaDataSource.GAL_TYPE_ZIMBRA) {
									this[ZaAccount.A2_zmail_ds] = dss[i];
								} else if(dss[i].attrs[ZaDataSource.A_zmailGalType] == ZaDataSource.GAL_TYPE_LDAP) {
									this[ZaAccount.A2_ldap_ds] = dss[i];
								}
							}
						}
					}
				}
			}
		} catch (ex) {
			//show the error and go on
			//we should not stop the Account from loading if some of the information cannot be acces
			ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaAccount.prototype.load", null, false);
		    hasError = true ;
            lastException = ex ;
        }
	}

    if (hasError) {
        throw lastException ;
    }
	
	var autoDispName;
	if(this.attrs[ZaAccount.A_firstName])
		autoDispName = this.attrs[ZaAccount.A_firstName];
	else
		autoDispName = "";
		
	if(this.attrs[ZaAccount.A_initials]) {
		autoDispName += " ";
		autoDispName += this.attrs[ZaAccount.A_initials];
		autoDispName += ".";
	}
	if(this.attrs[ZaAccount.A_lastName]) {
		if(autoDispName.length > 0)
			autoDispName += " ";
			
	    autoDispName += this.attrs[ZaAccount.A_lastName];
	} 	
     
	if(this.attrs[ZaAccount.A_zmailPrefMailPollingInterval]) {
        var poIntervalInS = ZaUtil.getLifeTimeInSeconds(this.attrs[ZaAccount.A_zmailPrefMailPollingInterval]);
        if (poIntervalInS >= 1)
            this.attrs[ZaAccount.A_zmailPrefMailPollingInterval] = poIntervalInS + "s";
	}

	if(this._defaultValues.attrs[ZaAccount.A_zmailPrefMailPollingInterval]) {
        var dePoIntervalInS = ZaUtil.getLifeTimeInSeconds(this._defaultValues.attrs[ZaAccount.A_zmailPrefMailPollingInterval]);
        if (dePoIntervalInS  >= 1)
            this._defaultValues.attrs[ZaAccount.A_zmailPrefMailPollingInterval] = dePoIntervalInS  + "s";
	}

	if( autoDispName == this.attrs[ZaAccount.A_displayname]) {
		this[ZaAccount.A2_autodisplayname] = "TRUE";
	} else {
		this[ZaAccount.A2_autodisplayname] = "FALSE";
	}
	
}

ZaItem.loadMethods["ZaAccount"].push(ZaAccount.loadMethod);

/**
* public rename; 
**/
ZaAccount.prototype.rename = 
function (newName) {
	//Instrumentation code start
	if(ZaAccount.renameMethods) {
		var methods = ZaAccount.renameMethods;
		var cnt = methods.length;
		for(var i = 0; i < cnt; i++) {
			if(typeof(methods[i]) == "function") {
				methods[i].call(this, newName);
			}
		}
	}	
	//Instrumentation code end
}

/**
* public renameMethod; sends RenameAccountRequest soap request
**/
ZaAccount.renameMethod = 
function (newName) {
	var soapDoc = AjxSoapDoc.create("RenameAccountRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("id", this.id);
	soapDoc.set("newName", newName);	
	var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;
	params.noAuthToken = true;	
	command.invoke(params);
}
ZaAccount.renameMethods.push(ZaAccount.renameMethod);

/**
* private changePasswordMethod; sends SetPasswordRequest soap request
* @param newPassword
**/
ZaAccount.changePasswordMethod = 
function (newPassword) {
	var soapDoc = AjxSoapDoc.create("SetPasswordRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("id", this.id);
	soapDoc.set("newPassword", newPassword);	
	var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;
	params.noAuthToken = true;	
	command.invoke(params);	
}
/**
* private _changePassword;
* @param newPassword
**/
ZaAccount.prototype.changePassword = 
function (newPassword) {
	//Instrumentation code start
	if(ZaAccount.changePasswordMethods) {
		var methods = ZaAccount.changePasswordMethods;
		var cnt = methods.length;
		for(var i = 0; i < cnt; i++) {
			if(typeof(methods[i]) == "function") {
				methods[i].call(this, newPassword);
			}
		}
	}	
	//Instrumentation code end
}
ZaAccount.changePasswordMethods.push(ZaAccount.changePasswordMethod);


ZaAccount.isValidName =
function(value) {
	if (AjxUtil.isEmpty(value))
		return false;

	var index = value.indexOf("@");
	var name = value.substring(0, index);

	var namePart = name.replace(/(\s*$)/g, "");
	namePart = AjxStringUtil.trim(namePart);
	var domainPart = value.substring(index+1);
	domainPart = AjxStringUtil.trim(domainPart);

	value = namePart + "@" + domainPart;
	return (AjxEmailAddress.isValid(value));
}

/**
* ZaAccount.myXModel - XModel for XForms
**/
ZaAccount.myXModel = {
    items: [
    	{id:"getAttrs",type:_LIST_},
    	{id:"setAttrs",type:_LIST_},
    	{id:"rights",type:_LIST_},
    	{id:ZaItem.A_zmailACE, ref:"attrs/" + ZaItem.A_zmailACE, type:_LIST_},
    	{id:ZaAccount.A2_errorMessage, ref:ZaAccount.A2_errorMessage, type:_STRING_},
    	{id:ZaAccount.A2_warningMessage, ref:ZaAccount.A2_warningMessage, type:_STRING_},
        {id:ZaAccount.A2_domainLeftAccounts, ref:ZaAccount.A2_domainLeftAccounts, type:_STRING_},
        {id:ZaAccount.A2_showAccountTypeMsg, ref:ZaAccount.A2_showAccountTypeMsg, type:_STRING_},
        {id:ZaAccount.A_name, type:_STRING_, ref:"name", required:true, 
			constraints: {type:"method", value:
				function (value, form, formItem, instance) {
					if (value){
						if(ZaAccount.isValidName(value)) {
							return value;
						} else {
							throw ZaMsg.ErrorInvalidEmailAddress;
						}
					}
			   }
			}
        },
        {id:ZaItem.A_zmailId, type:_STRING_, ref:"attrs/" + ZaItem.A_zmailId},
        {id:ZaAccount.A_uid, type:_STRING_, ref:"attrs/"+ZaAccount.A_uid},
        {id:ZaItem.A_zmailCreateTimestamp, ref:"attrs/" + ZaItem.A_zmailCreateTimestamp},
        {id:ZaAccount.A_accountName, type:_STRING_, ref:"attrs/"+ZaAccount.A_accountName},
        {id:ZaAccount.A_firstName, type:_STRING_, ref:"attrs/"+ZaAccount.A_firstName},
        {id:ZaAccount.A_lastName, type:_STRING_, ref:"attrs/"+ZaAccount.A_lastName, required:true},
        {id:ZaAccount.A_zmailPhoneticFirstName, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailPhoneticFirstName},
        {id:ZaAccount.A_zmailPhoneticLastName, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailPhoneticLastName},
	{id:ZaAccount.A_zmailPhoneticCompany, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailPhoneticCompany},
	{id:ZaAccount.A_mail, type:_STRING_, ref:"attrs/"+ZaAccount.A_mail},
        {id:ZaAccount.A_password, type:_STRING_, ref:"attrs/"+ZaAccount.A_password},
        {id:ZaAccount.A2_confirmPassword, type:_STRING_},
         ZaItem.descriptionModelItem,
            /*
        {id:ZaAccount.A_description, type: _LIST_, ref:"attrs/"+ZaAccount.A_description,
            listItem:{type:_STRING_}
        },    */
        {id:ZaAccount.A_telephoneNumber, type:_STRING_, ref:"attrs/"+ZaAccount.A_telephoneNumber},
        {id:ZaAccount.A_mobile, type:_STRING_, ref:"attrs/"+ZaAccount.A_mobile},
        {id:ZaAccount.A_pager, type:_STRING_, ref:"attrs/"+ZaAccount.A_pager},
        {id:ZaAccount.A_homePhone, type:_STRING_, ref:"attrs/"+ZaAccount.A_homePhone},
        {id:ZaAccount.A_displayname, type:_STRING_, ref:"attrs/"+ZaAccount.A_displayname},
        {id:ZaAccount.A_country, type:_STRING_, ref:"attrs/"+ZaAccount.A_country},
        {id:ZaAccount.A_company, type:_STRING_, ref:"attrs/"+ZaAccount.A_company},
        {id:ZaAccount.A_title, type:_STRING_, ref:"attrs/"+ZaAccount.A_title},
        {id:ZaAccount.A_facsimileTelephoneNumber, type:_STRING_, ref:"attrs/"+ZaAccount.A_facsimileTelephoneNumber},
        {id:ZaAccount.A_initials, type:_STRING_, ref:"attrs/"+ZaAccount.A_initials},
        {id:ZaAccount.A_city, type:_STRING_, ref:"attrs/"+ZaAccount.A_city},
        {id:ZaAccount.A_orgUnit, type:_STRING_, ref:"attrs/"+ZaAccount.A_orgUnit},
        {id:ZaAccount.A_office, type:_STRING_, ref:"attrs/"+ZaAccount.A_office},
        {id:ZaAccount.A_street, type:_STRING_, ref:"attrs/"+ZaAccount.A_street},
        {id:ZaAccount.A_zip, type:_STRING_, ref:"attrs/"+ZaAccount.A_zip},
        {id:ZaAccount.A_state, type:_STRING_, ref:"attrs/"+ZaAccount.A_state},
        {id:ZaAccount.A_mailDeliveryAddress, type:_EMAIL_ADDRESS_, ref:"attrs/"+ZaAccount.A_mailDeliveryAddress},
        {id:ZaAccount.A_zmailMailCanonicalAddress, type:_EMAIL_ADDRESS_, ref:"attrs/"+ZaAccount.A_zmailMailCanonicalAddress},
        {id:ZaAccount.A_accountStatus, type:_STRING_, ref:"attrs/"+ZaAccount.A_accountStatus},
        {id:ZaAccount.A_notes, type:_STRING_, ref:"attrs/"+ZaAccount.A_notes},
        {id:ZaAccount.A_zmailMailQuota, type:_COS_MAILQUOTA_, ref:"attrs/"+ZaAccount.A_zmailMailQuota},
        {id:ZaAccount.A_mailHost, type:_STRING_, ref:"attrs/"+ZaAccount.A_mailHost},
        {id:ZaAccount.A_COSId, type:_STRING_, ref:"attrs/" + ZaAccount.A_COSId},
        {id:ZaAccount.A_zmailIsAdminAccount, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailIsAdminAccount},
        {id:ZaAccount.A_zmailIsSystemResource, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailIsSystemResource},
        {id:ZaAccount.A_zmailIsSystemAccount, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailIsSystemAccount},
 
        {id:ZaAccount.A_zmailLastLogonTimestamp, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailLastLogonTimestamp},
        {id:ZaAccount.A_zmailMaxPwdLength, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailMaxPwdLength, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailMinPwdLength, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailMinPwdLength, maxInclusive:2147483647, minInclusive:0},

        {id:ZaAccount.A_zmailPasswordMinUpperCaseChars, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailPasswordMinUpperCaseChars, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailPasswordMinLowerCaseChars, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailPasswordMinLowerCaseChars, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailPasswordMinPunctuationChars, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailPasswordMinPunctuationChars, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailPasswordMinNumericChars, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailPasswordMinNumericChars, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailPasswordMinDigitsOrPuncs, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailPasswordMinDigitsOrPuncs, maxInclusive:2147483647, minInclusive:0},
 	{id:ZaAccount.A_zmailAuthLdapExternalDn, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailAuthLdapExternalDn},

        {id:ZaAccount.A_zmailMinPwdAge, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailMinPwdAge, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailMaxPwdAge, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailMaxPwdAge, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailEnforcePwdHistory, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailEnforcePwdHistory, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailMailAlias, type:_LIST_, ref:"attrs/"+ZaAccount.A_zmailMailAlias, listItem:{type:_EMAIL_ADDRESS_}},
        {id:ZaAccount.A_zmailForeignPrincipal, type:_LIST_, ref:"attrs/"+ZaAccount.A_zmailForeignPrincipal, listItem:{type:_STRING_}},
        {id:ZaAccount.A_zmailMailForwardingAddress, type:_LIST_, ref:"attrs/"+ZaAccount.A_zmailMailForwardingAddress, listItem:{type:_EMAIL_ADDRESS_}},
		{id:ZaAccount.A_zmailPrefCalendarForwardInvitesTo, type:_LIST_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarForwardInvitesTo, listItem:{type:_EMAIL_ADDRESS_}},        
        {id:ZaAccount.A_zmailPasswordMustChange, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPasswordMustChange},
        {id:ZaAccount.A_zmailPasswordLocked, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPasswordLocked, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailContactMaxNumEntries, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailContactMaxNumEntries, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailMailForwardingAddressMaxLength, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailMailForwardingAddressMaxLength, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailDataSourcePop3PollingInterval, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailDataSourcePop3PollingInterval},
        {id:ZaAccount.A_zmailDataSourceImapPollingInterval, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailDataSourceImapPollingInterval},
        {id:ZaAccount.A_zmailDataSourceCalendarPollingInterval, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailDataSourceCalendarPollingInterval},
        {id:ZaAccount.A_zmailDataSourceRssPollingInterval, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailDataSourceRssPollingInterval},
        {id:ZaAccount.A_zmailDataSourceCaldavPollingInterval, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailDataSourceCaldavPollingInterval},
        {id:ZaAccount.A_zmailDataSourceMinPollingInterval, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailDataSourceMinPollingInterval},
        {id:ZaAccount.A_zmailPrefAutoSaveDraftInterval, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailPrefAutoSaveDraftInterval},
        {id:ZaAccount.A_zmailProxyAllowedDomains, type:_COS_LIST_, ref:"attrs/"+ZaAccount.A_zmailProxyAllowedDomains, listItem:{ type: _STRING_}},
        {id:ZaAccount.A_zmailMailForwardingAddressMaxNumAddrs, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailMailForwardingAddressMaxNumAddrs, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailAttachmentsBlocked, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailAttachmentsBlocked, choices:ZaModel.BOOLEAN_CHOICES},

        {id:ZaAccount.A_zmailQuotaWarnPercent, type:_COS_NUMBER_, ref:"attrs/" + ZaAccount.A_zmailQuotaWarnPercent},
        {id:ZaAccount.A_zmailQuotaWarnInterval, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailQuotaWarnInterval},
        {id:ZaAccount.A_zmailQuotaWarnMessage, type:_COS_STRING_, ref:"attrs/" + ZaAccount.A_zmailQuotaWarnMessage},

        {id:ZaAccount.A_zmailAuthTokenLifetime, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailAuthTokenLifetime},
        {id:ZaAccount.A_zmailAdminAuthTokenLifetime, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailAdminAuthTokenLifetime},
        {id:ZaAccount.A_zmailMailIdleSessionTimeout, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailMailIdleSessionTimeout},
        {id:ZaAccount.A_zmailMailMessageLifetime, type:_COS_MLIFETIME_, ref:"attrs/" + ZaAccount.A_zmailMailMessageLifetime},
        {id:ZaAccount.A_zmailMailSpamLifetime, type:_COS_MLIFETIME_, ref:"attrs/" + ZaAccount.A_zmailMailSpamLifetime},
        {id:ZaAccount.A_zmailMailTrashLifetime, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailMailTrashLifetime},
        {id:ZaAccount.A_zmailPrefSaveToSent, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefSaveToSent, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefMailSignature, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefMailSignature},
        {id:ZaAccount.A_zmailPrefMailSignatureEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefMailSignatureEnabled},
        //preferences
        {id:ZaAccount.A_zmailPrefMandatorySpellCheckEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefMandatorySpellCheckEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefAppleIcalDelegationEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefAppleIcalDelegationEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarShowPastDueReminders, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarShowPastDueReminders, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarToasterEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarToasterEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarAllowCancelEmailToSelf, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarAllowCancelEmailToSelf, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarAllowPublishMethodInvite, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarAllowPublishMethodInvite, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarAllowForwardedInvite, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarAllowForwardedInvite, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarReminderFlashTitle, type:_COS_ENUM_, ref:"attrs/" + ZaAccount.A_zmailPrefCalendarReminderFlashTitle, choices:ZaModel.BOOLEAN_CHOICES}, 
        {id:ZaAccount.A_zmailPrefCalendarReminderSoundsEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarReminderSoundsEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarSendInviteDeniedAutoReply, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarSendInviteDeniedAutoReply, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarNotifyDelegatedChanges, type:_COS_ENUM_, ref:"attrs/" + ZaAccount.A_zmailPrefCalendarNotifyDelegatedChanges, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarFirstDayOfWeek, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarFirstDayOfWeek, choices:ZaSettings.dayOfWeekChoices},
        {id:ZaAccount.A_zmailPrefCalendarInitialView, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarInitialView, choices:ZaSettings.calendarViewChoinces},
        {id:ZaAccount.A_zmailPrefClientType, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefClientType, choices:ZaSettings.clientTypeChoices},
        {id:ZaAccount.A_zmailPrefTimeZoneId,type:_COS_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefTimeZoneId, choices:ZaSettings.timeZoneChoices},
        {id:ZaAccount.A_zmailPrefMailDefaultCharset,type:_COS_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefMailDefaultCharset, choices:ZaSettings.mailCharsetChoices},
        {id:ZaAccount.A_zmailPrefMailToasterEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefMailToasterEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefMessageIdDedupingEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefMessageIdDedupingEnabled, choices:ZaModel.BOOLEAN_CHOICES},

	{id:ZaAccount.A_zmailPrefLocale,type:_COS_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefLocale},
        {id:ZaAccount.A_zmailPrefSentMailFolder, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefSentMailFolder},
        {id:ZaAccount.A_zmailPrefIncludeSpamInSearch, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefIncludeSpamInSearch, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefIncludeTrashInSearch, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefIncludeTrashInSearch, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefMailInitialSearch, type:_COS_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefMailInitialSearch},
        {id:ZaAccount.A_zmailMaxMailItemsPerPage, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailMaxMailItemsPerPage,maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailPrefMailItemsPerPage, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailPrefMailItemsPerPage, choices:[10,25,50,100]},
        {id:ZaAccount.A_zmailPrefMailPollingInterval, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefMailPollingInterval, choices: ZaSettings.mailPollingIntervalChoices},
        {id:ZaAccount.A_zmailMailMinPollingInterval, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailMailMinPollingInterval},
        {id:ZaAccount.A_zmailPrefMailFlashIcon, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefMailFlashIcon, type:_COS_ENUM_},
        {id:ZaAccount.A_zmailPrefMailFlashTitle, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefMailFlashTitle, type:_COS_ENUM_},
        {id:ZaAccount.A_zmailPrefMailSoundsEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefMailSoundsEnabled, type:_COS_ENUM_},
        {id:ZaAccount.A_zmailPrefOutOfOfficeReply, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefOutOfOfficeReply},
	{id:ZaAccount.A_zmailPrefOutOfOfficeReplyEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefOutOfOfficeReplyEnabled, type:_COS_ENUM_},
        {id:ZaAccount.A_zmailPrefItemsPerVirtualPage, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailPrefItemsPerVirtualPage},
        {id:ZaAccount.A_zmailPrefReplyToAddress, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefReplyToAddress},
        {id:ZaAccount.A_zmailPrefUseKeyboardShortcuts, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefUseKeyboardShortcuts, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailAllowAnyFromAddress, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailAllowAnyFromAddress, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailAllowFromAddress,type: _LIST_, ref:"attrs/"+ZaAccount.A_zmailAllowFromAddress, listItem:{type:_STRING_}},
        {id:ZaAccount.A_zmailPrefComposeInNewWindow, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefComposeInNewWindow, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefForwardReplyInOriginalFormat, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefForwardReplyInOriginalFormat, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefAutoAddAddressEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefAutoAddAddressEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefComposeFormat, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefComposeFormat, choices:ZaModel.COMPOSE_FORMAT_CHOICES},
        {id:ZaAccount.A_zmailPrefHtmlEditorDefaultFontFamily, choices:ZaModel.FONT_FAMILY_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefHtmlEditorDefaultFontFamily, type:_COS_ENUM_},
        {id:ZaAccount.A_zmailPrefHtmlEditorDefaultFontSize, choices:ZaModel.FONT_SIZE_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefHtmlEditorDefaultFontSize, type:_COS_ENUM_},
        {id:ZaAccount.A_zmailPrefHtmlEditorDefaultFontColor, ref:"attrs/"+ZaAccount.A_zmailPrefHtmlEditorDefaultFontColor, type:_COS_STRING_},
        {id:ZaAccount.A_zmailMailSignatureMaxLength, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailMailSignatureMaxLength},
        {id:ZaAccount.A_zmailPrefGroupMailBy, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefGroupMailBy, choices:ZaModel.GROUP_MAIL_BY_CHOICES},
        {id:ZaAccount.A_zmailPrefMessageViewHtmlPreferred, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefMessageViewHtmlPreferred, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefNewMailNotificationAddress, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefNewMailNotificationAddress},
        {id:ZaAccount.A_zmailPrefMailForwardingAddress, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefMailForwardingAddress,
         constraints: {type:"method", value:
	     function (value, form, formItem, instance) {				   	 
                 if (value){
		      var i;
                      var startIndex = 0;
                      var endIndex = 0;
                      var currentEmailAddress;
                      var ret;
                      var isThrown = false;
                      value = value.replace(/\s/g,""); //delete all the white space
                      for(i = 0; i < value.length; i++){
                         endIndex = value.indexOf(",", startIndex);
                         if(endIndex == -1){
                            currentEmailAddress = value.substring(startIndex);
                    
                            if(!AjxUtil.isEmailAddress(currentEmailAddress, false)){
                                   isThrown = true;
                            }
                            
                            break;
                         }   
                         currentEmailAddress = value.substring(startIndex, endIndex);
                         
                         if(!AjxUtil.isEmailAddress(currentEmailAddress, false)){ 
                             isThrown = true;
                             break;
                         }
                      
                         startIndex = endIndex + 1;
                    }
                    if(isThrown){
                       throw  ZaMsg.ErrorInvalidEmailAddressList;
                    } 	
	         }
                return value;
	   }
	 } 
        },
        {id:ZaAccount.A_zmailPrefNewMailNotificationEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefNewMailNotificationEnabled},
        {id:ZaAccount.A_zmailPrefOutOfOfficeReply, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefOutOfOfficeReply},
        {id:ZaAccount.A_zmailPrefMailLocalDeliveryDisabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefMailLocalDeliveryDisabled},
        {id:ZaAccount.A_zmailIsExternalVirtualAccount, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailIsExternalVirtualAccount},
        {id:ZaAccount.A_zmailPrefShowSearchString, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefShowSearchString, choices:ZaModel.BOOLEAN_CHOICES},
        //{id:ZaAccount.A_zmailPrefMailSignatureStyle, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefMailSignatureStyle, choices:ZaModel.SIGNATURE_STYLE_CHOICES},
        {id:ZaAccount.A_zmailPrefUseTimeZoneListInCalendar, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefUseTimeZoneListInCalendar, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefImapSearchFoldersEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefImapSearchFoldersEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarUseQuickAdd, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarUseQuickAdd, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarAlwaysShowMiniCal, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarAlwaysShowMiniCal, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefSkin, type:_COS_STRING_, ref:"attrs/"+ZaAccount.A_zmailPrefSkin},
        {id:ZaAccount.A_zmailAvailableSkin, type:_COS_LIST_, ref:"attrs/" + ZaAccount.A_zmailAvailableSkin, dataType: _STRING_},
        {id:ZaAccount.A_zmailZimletAvailableZimlets, type:_COS_LIST_, ref:"attrs/" + ZaAccount.A_zmailZimletAvailableZimlets, dataType: _STRING_,outputType:_LIST_},
        {id:ZaAccount.A_zmailPrefGalAutoCompleteEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefGalAutoCompleteEnabled},
        {id:ZaAccount.A_zmailPrefAdminConsoleWarnOnExit, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefAdminConsoleWarnOnExit},
        {id:ZaAccount.A_zmailPrefWarnOnExit, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefWarnOnExit},
        {id:ZaAccount.A_zmailPrefShowSelectionCheckbox, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaAccount.A_zmailPrefShowSelectionCheckbox},
        {id:ZaAccount.A_zmailPrefDisplayExternalImages, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefDisplayExternalImages, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefOutOfOfficeCacheDuration, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailPrefOutOfOfficeCacheDuration},
        {id:ZaAccount.A_zmailJunkMessagesIndexingEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailJunkMessagesIndexingEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefMailSendReadReceipts, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefMailSendReadReceipts, choices:ZaModel.SEND_READ_RECEPIT_CHOICES},
		{id:ZaAccount.A_zmailPrefReadReceiptsToAddress, type:_EMAIL_ADDRESS_, ref:"attrs/"+ZaAccount.A_zmailPrefReadReceiptsToAddress},
        {id:ZaAccount.A_zmailPrefCalendarAutoAddInvites, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarAutoAddInvites, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarApptVisibility, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarApptVisibility, choices:ZaSettings.apptVisibilityChoices},
        //features
        {id:ZaAccount.A_zmailFeatureManageZimlets, type:_COS_ENUM_, ref:"attrs/" + ZaAccount.A_zmailFeatureManageZimlets, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureImportFolderEnabled, type:_COS_ENUM_, ref:"attrs/" + ZaAccount.A_zmailFeatureImportFolderEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureExportFolderEnabled, type:_COS_ENUM_, ref:"attrs/" + ZaAccount.A_zmailFeatureExportFolderEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailDumpsterEnabled, type:_COS_ENUM_, ref:"attrs/" + ZaAccount.A_zmailDumpsterEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailDumpsterUserVisibleAge, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailDumpsterUserVisibleAge },
        {id:ZaAccount.A_zmailDumpsterPurgeEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailDumpsterPurgeEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailMailDumpsterLifetime, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailMailDumpsterLifetime },
        {id:ZaAccount.A_zmailFeatureMailPriorityEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureMailPriorityEnabled, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaAccount.A_zmailFeatureReadReceiptsEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureReadReceiptsEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureImapDataSourceEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureImapDataSourceEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeaturePop3DataSourceEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeaturePop3DataSourceEnabled, choices:ZaModel.BOOLEAN_CHOICES},
       	{id:ZaAccount.A_zmailFeatureMailSendLaterEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureMailSendLaterEnabled, choices:ZaModel.BOOLEAN_CHOICES},
       	//{id:ZaAccount.A_zmailFeatureFreeBusyViewEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureFreeBusyViewEnabled, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaAccount.A_zmailFeatureIdentitiesEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureIdentitiesEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureContactsEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureContactsEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureCalendarEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureCalendarEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureTasksEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureTasksEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureTaggingEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureTaggingEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeaturePeopleSearchEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeaturePeopleSearchEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureAdvancedSearchEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureAdvancedSearchEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureSavedSearchesEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureSavedSearchesEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureConversationsEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureConversationsEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureChangePasswordEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureChangePasswordEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureInitialSearchPreferenceEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureInitialSearchPreferenceEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureFiltersEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureFiltersEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureGalEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureGalEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureMAPIConnectorEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureMAPIConnectorEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureMailEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureMailEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        //{id:ZaAccount.A_zmailFeatureNotebookEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureNotebookEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureBriefcasesEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureBriefcasesEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureHtmlComposeEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureHtmlComposeEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureMailForwardingEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureMailForwardingEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureSharingEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureSharingEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailExternalSharingEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailExternalSharingEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPublicSharingEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPublicSharingEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureOutOfOfficeReplyEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureOutOfOfficeReplyEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureNewMailNotificationEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureNewMailNotificationEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureMailPollingIntervalPreferenceEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureMailPollingIntervalPreferenceEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        //{id:ZaAccount.A_zmailFeatureShortcutAliasesEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureShortcutAliasesEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureOptionsEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureOptionsEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureSkinChangeEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureSkinChangeEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPrefCalendarApptReminderWarningTime, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefCalendarApptReminderWarningTime, choices:ZaModel.REMINDER_CHOICES},
        {id:ZaAccount.A_zmailFeatureGalAutoCompleteEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureGalAutoCompleteEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureGroupCalendarEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureGroupCalendarEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        //{id:ZaAccount.A_zmailFeatureIMEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureIMEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        //{id:ZaAccount.A_zmailFeatureInstantNotify, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureInstantNotify, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureFlaggingEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureFlaggingEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailImapEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailImapEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPop3Enabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPop3Enabled, choices:ZaModel.BOOLEAN_CHOICES},
        {
            id: ZaAccount.A_zmailFeatureDistributionListFolderEnabled,
            choices: ZaModel.BOOLEAN_CHOICES,
            ref: "attrs/" + ZaAccount.A_zmailFeatureDistributionListFolderEnabled,
            type: _COS_ENUM_
        },

        {id:ZaAccount.A_zmailFeatureSMIMEEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureSMIMEEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureManageSMIMECertificateEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureManageSMIMECertificateEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailFeatureCalendarReminderDeviceEmailEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFeatureCalendarReminderDeviceEmailEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaModel.currentStep, type:_NUMBER_, ref:ZaModel.currentStep},
        {id:ZaAccount.A2_newAlias, type:_STRING_},
        {id:ZaAccount.A2_aliases, type:_LIST_,listItem:{type:_STRING_}},
        {id:ZaAccount.A2_forwarding, type:_LIST_,listItem:{type:_STRING_}},
        {id:ZaAccount.A2_mbxsize, type:_NUMBER_, ref:"attrs/"+ZaAccount.A2_mbxsize},
        //{id:ZaAccount.A2_quota, type:_MAILQUOTA_2_, ref:"attrs/"+ZaAccount.A_zmailMailQuota},
        {id:ZaAccount.A2_autodisplayname, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A2_autoMailServer, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A2_autoCos, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A2_alias_selection_cache, type:_LIST_},
        {id:ZaAccount.A2_fwdAddr_selection_cache, type:_LIST_},
        {id:ZaAccount.A2_calFwdAddr_selection_cache, type:_LIST_},
        {id:ZaAccount.A2_fp_selection_cache, type:_LIST_},
        {id:ZaAccount.A_zmailHideInGal, type:_ENUM_, ref:"attrs/"+ZaAccount.A_zmailHideInGal, choices:ZaModel.BOOLEAN_CHOICES},

	{id:ZaAccount.A_zmailMailTransport, type:_STRING_, ref:"attrs/"+ZaAccount.A_zmailMailTransport},	

        //security
        {id:ZaAccount.A_zmailPasswordLockoutEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPasswordLockoutEnabled, choices:ZaModel.BOOLEAN_CHOICES},
        {id:ZaAccount.A_zmailPasswordLockoutDuration, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailPasswordLockoutDuration},
        {id:ZaAccount.A_zmailPasswordLockoutMaxFailures, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailPasswordLockoutMaxFailures, maxInclusive:2147483647, minInclusive:0},
        {id:ZaAccount.A_zmailPasswordLockoutFailureLifetime, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailPasswordLockoutFailureLifetime},

        //interop
        {id:ZaAccount.A_zmailFreebusyExchangeUserOrg, ref:"attrs/" +  ZaAccount.A_zmailFreebusyExchangeUserOrg, type:_COS_STRING_},
        
        //datasources
        {id:ZaAccount.A2_ldap_ds, ref:ZaAccount.A2_ldap_ds, type:_OBJECT_, items:ZaDataSource.myXModel.items},
        {id:ZaAccount.A2_zmail_ds, ref:ZaAccount.A2_zmail_ds, type:_OBJECT_, items:ZaDataSource.myXModel.items},
        {id:ZaAccount.A2_datasources, ref:ZaAccount.A2_datasources, type:_LIST_, listItem:{type:_OBJECT_, items:ZaDataSource.myXModel.items}} ,
        {id:ZaAccount.A2_isExternalAuth, ref:ZaAccount.A2_isExternalAuth, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES1}
    ]
};



ZaItem._ATTR[ZaAccount.A_displayname] = ZaMsg.attrDesc_accountName;
ZaItem._ATTR[ZaAccount.A_description] = ZaMsg.attrDesc_description;
ZaItem._ATTR[ZaAccount.A_firstName] = ZaMsg.attrDesc_firstName;
ZaItem._ATTR[ZaAccount.A_lastName] = ZaMsg.attrDesc_lastName;
ZaItem._ATTR[ZaAccount.A_accountStatus] = ZaMsg.attrDesc_accountStatus;
ZaItem._ATTR[ZaAccount.A_mailHost] =  ZabMsg.attrDesc_mailHost;
ZaItem._ATTR[ZaAccount.A_zmailMailQuota] = ZaMsg.attrDesc_zmailMailQuota;
ZaItem._ATTR[ZaAccount.A_notes] = ZaMsg.attrDesc_notes;

ZaAccount._accountStatus = 
function(val) {
	var desc = ZaAccount.getAccountStatusMsg (val);
	return (desc == null) ? val : desc;
}

/* Translation of Account status values into screen names */     

ZaAccount.getAccountStatusMsg = function (status) {
    if (status == ZaAccount.ACCOUNT_STATUS_ACTIVE)  {
        return ZaMsg.accountStatus_active;
    }else if (status == ZaAccount.ACCOUNT_STATUS_CLOSED) {
        return ZaMsg.accountStatus_closed;
    }else if (status == ZaAccount.ACCOUNT_STATUS_LOCKED ) {
        return  ZaMsg.accountStatus_locked;
    }else if (status == ZaAccount.ACCOUNT_STATUS_LOCKOUT){
        return  ZaMsg.accountStatus_lockout;
    }else if (status == ZaAccount.ACCOUNT_STATUS_MAINTENANCE){
        return  ZaMsg.accountStatus_maintenance;
    }else if (status == ZaAccount.ACCOUNT_STATUS_PENDING) {
        return ZaMsg.accountStatus_pending ;
    }else {
        return "";
    }
}

ZaAccount.initMethod = function () {
	this.attrs = new Object();
	this.id = "";
	this.name="";
	this.attrs[ZaAccount.A_zmailMailAlias] = new Array();
    this.attrs[ZaAccount.A_zmailForeignPrincipal] = new Array ();
    this[ZaAccount.A2_memberOf] = {directMemberList: [],indirectMemberList: [],nonMemberList: []};
    this[ZaAccount.A2_directMemberList + "_more"] = 0;
    this[ZaAccount.A2_indirectMemberList + "_more"] = 0;
}
ZaItem.initMethods["ZaAccount"].push(ZaAccount.initMethod);

ZaAccount.getDomain =
function (accountName) {
	if (!accountName) return null;
	return accountName.substring(accountName.lastIndexOf ("@") + 1 ) ;	
}

ZaAccount.isAutoMailServer = function () {
	return (this.getInstanceValue(ZaAccount.A2_autoMailServer)=="FALSE" && !AjxUtil.isEmpty(ZaApp.getInstance().getServerListChoices().getChoices()) && !AjxUtil.isEmpty(ZaApp.getInstance().getServerListChoices().getChoices().values));
}


ZaAccount.setCosChanged = function (value, event, form) {
	var oldVal = this.getInstanceValue();
	if(oldVal == value)
		return;
			
	this.setInstanceValue(value);
	
	if(ZaItem.ID_PATTERN.test(value)) {
		this._defaultValues = ZaCos.getCosById(value);
	} else if(!AjxUtil.isEmpty(value)) {
		var cos = ZaCos.getCosByName(value);
		if(cos) {
			this._defaultValues = cos;
		} else {
			this.setError(AjxMessageFormat.format(ZaMsg.ERROR_NO_SUCH_COS,[value]));
			var event = new DwtXFormsEvent(form, this, value);
			form.notifyListeners(DwtEvent.XFORMS_VALUE_ERROR, event);
		}
	}
} 

ZaAccount.setDomainChanged =
function (value, event, form){
	//form.parent.setDirty(true);
	try {
		var instance = form.getInstance();
		var oldDomainName = ZaAccount.getDomain(instance[ZaAccount.A_name]);
		this.setInstanceValue(value);
		var p = form.parent ;
        var newDomainName = ZaAccount.getDomain(value) ;
	
	if( !newDomainName ){
		return;
	}
	
        var domainObj;
        try {
        	domainObj =  ZaDomain.getDomainByName(newDomainName) ;
        } catch (ex) {
        	if(ex.code == ZmCsfeException.SVC_PERM_DENIED) {
        		form.getModel().setInstanceValue(form.getInstance(),"setAttrs",[]);
        		form.getModel().setInstanceValue(form.getInstance(),ZaAccount.A2_errorMessage,AjxMessageFormat.format(ZaMsg.CANNOT_CREATE_ACCOUNTS_IN_THIS_DOMAIN,[newDomainName]));
        		return;
        	} else if(ex.code == ZmCsfeException.NO_SUCH_DOMAIN) {
        		return;
        	} else {
        		throw (ex);
        	}
        	
        }
		
	if (instance [ZaAccount.A2_autoCos] == "TRUE"){
		ZaAccount.setDefaultCos(instance);
	}
	
        if ((newDomainName != oldDomainName)
				//set the right default cos at the account creation time
				|| (instance [ZaAccount.A_name].indexOf("@") == 0)) 
		{ //see if the cos needs to be updated accordingly
			try {
				ZaItem.prototype.loadNewObjectDefaults.call(instance,"name", newDomainName);
			} catch (ex) {
				if(ex.code == ZmCsfeException.NO_SUCH_DOMAIN) {
        			return value;
        		} else {
        			throw (ex);
        		}
			}
			
			if(instance.getAttrs[ZaAccount.A_zmailAvailableSkin] || instance.getAttrs.all) {
				var skins = ZaApp.getInstance().getInstalledSkins();
				
				if(AjxUtil.isEmpty(skins)) {
					if(domainObj && domainObj.attrs && !AjxUtil.isEmpty(domainObj.attrs[ZaDomain.A_zmailAvailableSkin])) {
						//if we cannot get all zimlets try getting them from domain
						skins = domainObj.attrs[ZaDomain.A_zmailAvailableSkin];
					} else if(instance._defaultValues && instance._defaultValues.attrs && !AjxUtil.isEmpty(instance._defaultValues.attrs[ZaAccount.A_zmailAvailableSkin])) {
						//if we cannot get all zimlets from domain either, just use whatever came in "defaults" which would be what the COS value is
						skins = instance._defaultValues.attrs[ZaAccount.A_zmailAvailableSkin];
					} else {
						skins = [];
					}
				} else {
					if (AjxUtil.isString(skins))	 {
						skins = [skins];
					}
				}

                var skinsChoices = ZaApp.getInstance().getSkinChoices(skins);
				if(ZaNewAccountXWizard.themeChoices) {
					ZaNewAccountXWizard.themeChoices.setChoices(skinsChoices);
					ZaNewAccountXWizard.themeChoices.dirtyChoices();
				}		
				
				if(ZaAccountXFormView.themeChoices) {
					ZaAccountXFormView.themeChoices.setChoices(skinsChoices);
					ZaAccountXFormView.themeChoices.dirtyChoices();
				}		
				
			}	
	
			if(instance.getAttrs[ZaAccount.A_zmailZimletAvailableZimlets] || instance.getAttrs.all) {
				//get sll Zimlets
				var allZimlets = ZaZimlet.getAll("extension");
		
				if(!AjxUtil.isEmpty(allZimlets) && allZimlets instanceof ZaItemList || allZimlets instanceof AjxVector)
					allZimlets = allZimlets.getArray();
		
				if(AjxUtil.isEmpty(allZimlets)) {
					
					if(domainObj && domainObj.attrs && !AjxUtil.isEmpty(domainObj.attrs[ZaDomain.A_zmailZimletDomainAvailableZimlets])) {
						//if we cannot get all zimlets try getting them from domain
						allZimlets = domainObj.attrs[ZaDomain.A_zmailZimletDomainAvailableZimlets];
					} else if(instance._defaultValues && instance._defaultValues.attrs && !AjxUtil.isEmpty(instance._defaultValues.attrs[ZaAccount.A_zmailZimletAvailableZimlets])) {
						allZimlets = instance._defaultValues.attrs[ZaAccount.A_zmailZimletAvailableZimlets];
					} else {
						allZimlets = [];
					}
					if(ZaNewAccountXWizard.zimletChoices) {
						ZaNewAccountXWizard.zimletChoices.setChoices(allZimlets);
						ZaNewAccountXWizard.zimletChoices.dirtyChoices();
					}
					
					if(ZaAccountXFormView.zimletChoices) {
						ZaAccountXFormView.zimletChoices.setChoices(allZimlets);
						ZaAccountXFormView.zimletChoices.dirtyChoices();
					}					
					
				} else {
					//convert objects to strings	
					var cnt = allZimlets.length;
					var _tmpZimlets = [];
					for(var i=0; i<cnt; i++) {
						var zimlet = allZimlets[i];
						_tmpZimlets.push(zimlet.name);
					}
					if(ZaNewAccountXWizard.zimletChoices) {
						ZaNewAccountXWizard.zimletChoices.setChoices(_tmpZimlets);
						ZaNewAccountXWizard.zimletChoices.dirtyChoices();
					}
					if(ZaAccountXFormView.zimletChoices) {
						ZaAccountXFormView.zimletChoices.setChoices(_tmpZimlets);
						ZaAccountXFormView.zimletChoices.dirtyChoices();
					}					
				}
			}
                	ZaAccount.setDefaultCos(instance);
                	instance [ZaAccount.A2_autoCos] = "TRUE";
                    form.refresh();

		}
                   
        //if domain name is not changed, we don't want to update the account type output
        if  (oldDomainName !=  newDomainName){   
            if (domainObj && domainObj.attrs ){
                var cosMaxAccounts = domainObj.attrs[ZaDomain.A_zmailDomainCOSMaxAccounts] ;
                if (cosMaxAccounts && cosMaxAccounts.length > 0){
                	form.getModel().setInstanceValue(form.getInstance(),ZaAccount.A2_errorMessage,"");
                	form.getModel().setInstanceValue(form.getInstance(),ZaAccount.A2_accountTypes,domainObj.getAccountTypes ());
                	form.parent.updateAccountType();
                }

             }
		
        } 
	if (domainObj && domainObj.attrs ){
                var maxDomainAccounts = domainObj.attrs[ZaDomain.A_domainMaxAccounts] ;
                var cosMaxAccounts = domainObj.attrs[ZaDomain.A_zmailDomainCOSMaxAccounts] ;
                if (maxDomainAccounts) {
                    maxDomainAccounts = parseInt (maxDomainAccounts);
                }
                if (maxDomainAccounts && maxDomainAccounts > 0) {
                    var usedAccounts = domainObj.getUsedDomainAccounts(newDomainName );
                    if (maxDomainAccounts < usedAccounts && (!cosMaxAccounts || cosMaxAccounts.length <= 0)) {
			form.getModel().setInstanceValue(form.getInstance(),ZaAccount.A2_accountTypes,null);
                        var msg;
                        if (usedAccounts - maxDomainAccounts > 1) {
                            msg = AjxMessageFormat.format (ZaMsg.NAD_DomainAccountLimits_p, [newDomainName, usedAccounts - maxDomainAccounts]);
                        } else {
                            msg = AjxMessageFormat.format (ZaMsg.NAD_DomainAccountLimits_s, [newDomainName, usedAccounts - maxDomainAccounts]);
                        }
                        form.getModel().setInstanceValue(form.getInstance(),ZaAccount.A2_domainLeftAccounts, msg);
		    }else {
			form.getModel().setInstanceValue(form.getInstance(),ZaAccount.A2_domainLeftAccounts,null);
		    }
		} else {
                    form.getModel().setInstanceValue(form.getInstance(),ZaAccount.A2_domainLeftAccounts,null);
		}
	}

        if (domainObj && domainObj.attrs &&
            domainObj.attrs[ZaDomain.A_AuthMech] &&
            (domainObj.attrs[ZaDomain.A_AuthMech] != ZaDomain.AuthMech_zmail) ) {
            form.getModel().setInstanceValue(form.getInstance(),ZaAccount.A2_isExternalAuth, true);
        } else {
            form.getModel().setInstanceValue(form.getInstance(),ZaAccount.A2_isExternalAuth, false);
        }

        if(form.parent.setDirty)  { //edit account view
			form.parent.setDirty(true);	
        }
        
	} catch (ex) {
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaAccount.setDomainChanged", null, false);	
	}
}

ZaAccount.generateDisplayName =
function (instance, firstName, lastName, initials) {
	var oldDisplayName = this.getInstanceValue(ZaAccount.A_displayname);
	var newDisplayname = "";
	var firstOne = firstName, secondOne = lastName;
	if(ZaZmailAdmin.isLanguage("ja")){
		firstOne = lastName;
		secondOne = firstName;
	}

	if(firstOne)
		newDisplayname = firstOne;
	else
		newDisplayname = "";
		
	if(initials) {
		
		newDisplayname += " ";
		newDisplayname += initials;
		newDisplayname += ".";
	}
	if(secondOne) {
		if(newDisplayname.length > 0)
			newDisplayname += " ";
			
	    newDisplayname += secondOne;
	} 
	if(newDisplayname == oldDisplayName) {
		return false;
	} else {
		this.getModel().setInstanceValue(instance, ZaAccount.A_displayname, newDisplayname);
		return true;
	}
}

ZaAccount.setDefaultCos =
function (instance) {
	var defaultCos = ZaCos.getDefaultCos4Account(instance[ZaAccount.A_name]);
			
	if( defaultCos && defaultCos.id) {
		instance._defaultValues = defaultCos;
		instance.attrs[ZaAccount.A_COSId] = defaultCos.id;	
	}
}

ZaAccount.prototype.getCurrentCos =
function (){
	try {
		var cosId = this.attrs[ZaAccount.A_COSId] ;
		var currentCos ;
		currentCos = ZaCos.getCosById(this.attrs[ZaAccount.A_COSId]);
		if (!currentCos){
			currentCos = ZaCos.getDefaultCos4Account( this.name );
		}
		return currentCos ;
	} catch (ex) {
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaAccount.prototype.getCurrentCos", null, false);
	}	
}

//the serverStr is in format yyyyMMddHHmmssZ to be converted to MM/dd/yyyy HH:mm:ss 
ZaAccount.getLastLoginTime =
function (serverStr) {
	if (serverStr) {
		return ZaItem.formatServerTime(serverStr);
	}else{
		return ZaMsg.Last_Login_Never;
	}
}

ZaAccount.prototype.manageSpecialAttrs =
function () {
	var warning = "" ;
	
	//handle the unrecognized timezone
	var tz = this.attrs[ZaAccount.A_zmailPrefTimeZoneId] ;
	if (tz) {
		var n_tz = ZaModel.setUnrecoganizedChoiceValue(tz, ZaSettings.timeZoneChoices) ;
		if (tz != n_tz) {
			this.attrs[ZaAccount.A_zmailPrefTimeZoneId] = n_tz ;
			warning +=  AjxMessageFormat.format(ZaMsg.WARNING_TIME_ZONE_INVALID ,  [ tz, "account - \"" + this.name +"\""] );
		}
	}

	//handle the unrecognized mail charset
	var mdc = this.attrs[ZaAccount.A_zmailPrefMailDefaultCharset] ;
	if (mdc) {
		var n_mdc = ZaModel.setUnrecoganizedChoiceValue(mdc, ZaSettings.mailCharsetChoices) ;
		if (mdc != n_mdc) {
			this.attrs[ZaAccount.A_zmailPrefMailDefaultCharset] = n_mdc ;
			warning += AjxMessageFormat.format(ZaMsg.WARNING_CHARSET_INVALID , [ mdc, "account - \"" + this.name +"\""]);
		}
	}


    //handle the unrecognized locale value
    var lv = this.attrs[ZaCos.A_zmailPrefLocale] ;
    if (lv) {
        var n_lv = ZaModel.setUnrecoganizedChoiceValue(lv, ZaSettings.getLocaleChoices()) ;
		if (lv != n_lv) {
			this.attrs[ZaCos.A_zmailPrefLocale] = n_lv ;
			warning += AjxMessageFormat.format(ZaMsg.WARNING_LOCALE_INVALID , [ lv, "account - \"" + this.name +"\""]);
		}
    }
    //display warnings about the if manageSpecialAttrs return value
	if (warning && warning.length > 0) { 
		ZaApp.getInstance().getCurrentController().popupMsgDialog (warning, true);
	}	
}
ZaAccount.isAdminAccount = function () {
	try {
		return (this.getInstanceValue(ZaAccount.A_zmailIsAdminAccount)=="TRUE" || this.getInstanceValue(ZaAccount.A_zmailIsDelegatedAdminAccount)=="TRUE");
	} catch (ex)	 {
		return false;
	}
}
/**
 * Test if the email retention policy should be enabled based on
 * if (serversetting is not set) { //check global setting
       if (gs != 0 ) {
        enable ERP for account on this server
        } else {
        disable ERP for account on this server
  }
}else{  //check server setting
    if ( serverSetting != 0 ) {
        enable ERP for account on this server
      } else if (serverSetting == 0 ){
        disable ERP  for account on this server
      }

}
 */
ZaAccount.isEmailRetentionPolicyEnabled = function () {
	try {
		var instance  = this.getInstance () ;
    	var gc   = ZaApp.getInstance().getGlobalConfig();
    	var sc =  ZaApp.getInstance().getServerByName(instance.attrs[ZaAccount.A_mailHost]);
    	var s_mailpurge = sc.attrs[ZaServer.A_zmailMailPurgeSleepInterval] ;    //always end with [s,m,h,d]
    	var g_mailpurge = gc.attrs[ZaGlobalConfig.A_zmailMailPurgeSleepInterval] ;
    	if (s_mailpurge === _UNDEFINED_ || s_mailpurge === null)  {
        	if(window.console && window.console.log) console.log("server setting A_zmailMailPurgeSleepInterval is NOT set.")
        	if (g_mailpurge != null && ZaUtil.getLifeTimeInSeconds(g_mailpurge) == 0) {
            	return false ;
        	}
    	} else if (ZaUtil.getLifeTimeInSeconds(s_mailpurge) == 0){
        	return false ;
    	}
		
    	return true ;
	} catch (ex) {
		return true;
   	}
}

ZaAccount.isEmailRetentionPolicyDisabled = function () {
	return !ZaAccount.isEmailRetentionPolicyEnabled.call(this);
}

ZaAccount.isShowAccountType = function() {
    var form = this.getForm () ;
    var instance = form.getInstance () ;
    var acctTypes = instance[ZaAccount.A2_accountTypes] ;
    var isShow = false;

    for (var i=0; i < acctTypes.length && !isShow; i ++) {
   
    	var domainName = ZaAccount.getDomain (instance.name) ;
    	var domainObj =  ZaDomain.getDomainByName (domainName, form.parent._app);
        var cos = ZaCos.getCosById (acctTypes[i] , ZaApp.getInstance()) ;
        if (cos == null) {
        	ZaApp.getInstance().getCurrentController().popupErrorDialog(
                        AjxMessageFormat.format(ZaMsg.ERROR_INVALID_ACCOUNT_TYPE, [acctTypes[i]]));
                return isShow;
        }
	if(domainObj.isCosLimitInDomain(cos.name))
		isShow = true;
    }
    return isShow;
}

ZaAccount.getAccountTypeOutput = function (isNewAccount) {
    var form = this.getForm () ;
    var instance = form.getInstance () ;
    var acctTypes = instance[ZaAccount.A2_accountTypes] ;
    var out = [] ;
    if (acctTypes && acctTypes.length > 0) {
        /*
        var currentCos = ZaCos.getCosById(instance.attrs[ZaAccount.A_COSId], form.parent._app) ;
        var currentType = null ;
        if (currentCos)
            currentType = currentCos.id ;
        */
        var currentType = instance[ZaAccount.A2_currentAccountType] ;
	var defaultType = ZaCos.getDefaultCos4Account(instance[ZaAccount.A_name]); 
	if(!currentType && defaultType)
		currentType = defaultType.id;

        var domainName = ZaAccount.getDomain (instance.name) ;
        var domainObj =  ZaDomain.getDomainByName (domainName, form.parent._app);
	var isFullUsed = true;

        out.push("<table with=100%>");
        out.push("<colgroup><col width='200px' /><col width='200px' /><col width='200px' /></colgroup> ");
        out.push("<tbody>") ;

        var radioGroupName = "account_type_radio_group_" + Dwt.getNextId() ;
        //make sure CountAccountRequest is called to refresh the used accounts counts
        domainObj.updateUsedAccounts();  
        for (var i=0; i < acctTypes.length; i ++) {
            var cos = ZaCos.getCosById (acctTypes[i] , ZaApp.getInstance()) ;
            var isCheck = false;
            if (cos == null) {
                ZaApp.getInstance().getCurrentController().popupErrorDialog(
                        AjxMessageFormat.format(ZaMsg.ERROR_INVALID_ACCOUNT_TYPE, [acctTypes[i]]));
                return ;
            }
            var accountTypeDisplayValue = cos.attrs[ZaCos.A_description] ;
            if (!accountTypeDisplayValue || accountTypeDisplayValue.length < 1)
                accountTypeDisplayValue = cos.name ;
            
            //3 columns a row
            if (i % 3 == 0) { //first col, need open <tr>
                out.push("<tr>") ;
            }
            out.push("<td>") ;

            //output the contents
            var usedAccounts = domainObj.getUsedAccounts(cos.name);
            var availableAccounts = domainObj.getAvailableAccounts(cos.name);

            var isNewAccount = false;
            if(form &&form.parent && form.parent instanceof ZaNewAccountXWizard)
                  isNewAccount = true;


	    if(availableAccounts > 0) isFullUsed = false;
            else if (currentType == acctTypes[i]) isFullUsed = false;
	    if (currentType == acctTypes[i]) {
		 //isFullUsed = false;
                 isCheck = true;
		 if (currentType != instance.attrs[ZaAccount.A_COSId]) {
        		instance.autoCos = "FALSE" ;
        		instance.attrs[ZaAccount.A_COSId] = currentType;
			if(isNewAccount)
			      form.parent.updateCosGrouper();
		 }
	    }

            out.push("<div>" +
                     "<label style='font-weight: bold;"
                    + ((availableAccounts > 0 || (currentType == acctTypes[i] && !isNewAccount) ) ? "" : "color: #686357;")
                    + "'>") ;
            //account type is disable when no accounts available
            out.push("<input type=radio name=" + radioGroupName + " value=" + acctTypes[i]
                    + ((availableAccounts > 0 || (currentType == acctTypes[i] && !isNewAccount)) ?  (" onclick=\"ZaAccount.setAccountType.call("
                                    + this.getGlobalRef() + ", '" + acctTypes[i] +  "', event );\" ") : (" disabled "))
                    + (/*(currentType == acctTypes[i])*/(isCheck && (availableAccounts > 0 || !isNewAccount)) ? " checked " : "" )
                    + " />") ;
            out.push(accountTypeDisplayValue + "</label></div>") ;

            out.push("<div>" + AjxMessageFormat.format(ZaMsg.AccountsAvailable, [usedAccounts, availableAccounts])  + "</div> ") ;
            out.push("</td>")
       
            if ((i % 3 == 2) || (i + 1 == acctTypes.length)) { //last col, need close </td>
                out.push("</tr>") ;
            }
        }

        out.push("</tbody></table>") ;
    
	// set warning message because of not avaliable account
	if(isFullUsed) {
                form.getModel().setInstanceValue(form.getInstance(),ZaAccount.A2_showAccountTypeMsg,
                        AjxMessageFormat.format (ZaMsg.MSG_AccountTypeUnavailable, [defaultType.name]));
	}else{
                form.getModel().setInstanceValue(form.getInstance(),ZaAccount.A2_showAccountTypeMsg,null);
	}

    }
    return out.join("") ; 
}

ZaAccount.setAccountType = function (newType, ev) {
    //console.log ("Account Type Changed") ;
    var form = this.getForm() ;
    var instance = form.getInstance () ;

    var newCos = ZaCos.getCosById (newType) ;
    if (newCos.id != instance.attrs[ZaAccount.A_COSId])  {
        //change the account type
        if (instance.cos) instance._defaultValues = newCos ;
        instance.autoCos = "FALSE" ;
        instance.attrs[ZaAccount.A_COSId] = newCos.id ;
        form.parent._isCosChanged = true ;

        form.itemChanged(this, newType, ev);

        if(form.parent.setDirty)
			form.parent.setDirty(true);

	form.parent.updateCosGrouper();
    }
}

ZaAccount.isAccountTypeSet = function (tmpObj) {

    var cosId = tmpObj.attrs [ZaAccount.A_COSId] || tmpObj[ZaAccount.A2_currentAccountType];
    var defaultType = ZaCos.getDefaultCos4Account(tmpObj[ZaAccount.A_name]);
    if (!tmpObj.accountTypes  || tmpObj.accountTypes.length <= 0) {
        return  true ; //account type is not present, no need to check if it is set
    } else if (!cosId){
        return false ;
    }

    // check whether default account type is adopted
    if(cosId == defaultType.id)
	return true;
    // if not, check whether accountType in list is selected
    for (var i=0; i < tmpObj.accountTypes.length; i ++) {
        if (cosId == tmpObj.accountTypes[i] )
            return true ;
    }

    return false ;

}

ZaAccount.getCatchAllDomain = function (domainName) {
    return "@" + domainName ;
}

//find the catch all account for the domain
ZaAccount.getCatchAllAccount = function (domainName) {
	  /* var accounts = ZaAccount.getAllDomainAccounts (domainName) ;
	    for (var i=0; i < accounts.length; i++) {
	        if (accounts [i].attrs[ZaAccount.A_zmailMailCatchAllAddress] == ZaAccount.getCatchAllDomain(domainName)) {
	            return accounts [i].id;
	        }
	   }
	 */
	  var searchParams = {
	     limit : 1 , //just need one
	     type : [ZaSearch.ACCOUNTS] ,
	     domain: domainName ,
	     applyCos:  0,
	     attrs: [ZaAccount.A_zmailMailCatchAllAddress],
	     query:(["(",ZaAccount.A_zmailMailCatchAllAddress,"=",ZaAccount.getCatchAllDomain(domainName),")"].join(""))
	  }	
	  
	  var resp =  ZaSearch.searchDirectory (searchParams).Body.SearchDirectoryResponse ;
	  var list = new ZaItemList(ZaAccount);
	  list.loadFromJS(resp);
	  var arr = list.getArray();
	  if(!AjxUtil.isEmpty(arr)) {
	  	if(arr[0]) {
	  		return arr[0];
	  	}
	  }  
	  return new ZaAccount(ZaApp.getInstance());
}

//++++++++++Modify CatchAll +++++++++++++++++++++++++
ZaAccount.modifyCatchAll =
function (accountId, domainName) {
    if (accountId == null | accountId.length <= 0) {
        return ;
    }
    var soapDoc = AjxSoapDoc.create("ModifyAccountRequest", "urn:zmailAdmin", null);
	soapDoc.set("id", accountId);
    var catchAllDomain = "" ;
    if (domainName == null || domainName.length == 0) {
        //remove the catchAll value from the account
        catchAllDomain = "" ;
    }else if (domainName.indexOf("@") == -1) { //has no @
        catchAllDomain = ZaAccount.getCatchAllDomain (domainName) ;
    }else if (domainName.indexOf("@") != 0) {
        catchAllDomain = domainName.substring(domainName.lastIndexOf("@"))
    }else {
        catchAllDomain = domainName ;
    }
    var el = soapDoc.set("a", catchAllDomain) ;

    el.setAttribute("n", ZaAccount.A_zmailMailCatchAllAddress) ;

    var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;
	params.noAuthToken = true;
	command.invoke(params);
}

ZaAccount.getRelatedList =
function (parentPath) {
    var alias = this.attrs[ZaAccount.A_zmailMailAlias];
    var cos = ZaCos.getCosById(this.attrs[ZaAccount.A_COSId])
            || ZaCos.getDefaultCos4Account(this[ZaAccount.A_name]);
    var domainName = ZaAccount.getDomain(this[ZaAccount.A_name]);
    var domainObj =  ZaDomain.getDomainByName (domainName) ;
    //var zimletList = item.attrs[ZaAccount.A_zmailZimletAvailableZimlets]
    //        || item._defaultValues.attrs[ZaAccount.A_zmailZimletAvailableZimlets];

    var Tis = [];
    if(alias.length > 0) {
        var aliasTi = new ZaTreeItemData({
                    text: ZaMsg.TABT_Aliases,
                    //type: 1,
                    count:alias.length,
                    image:"AccountAlias",
                    mappingId: ZaZmailAdmin._ACCOUNT_ALIAS_LIST_VIEW,    //ZaZmailAdmin._ALIASES_LIST_VIEW,
                    path: parentPath + ZaTree.SEPERATOR + this.name + ZaTree.SEPERATOR + ZaMsg.TABT_Aliases
                    }
                );
        aliasTi.setData("aliasTargetId", this.id);
        ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._ACCOUNT_ALIAS_LIST_VIEW] = ZaOverviewPanelController.aliasListTreeListener;
        Tis.push(aliasTi);
    }

    var dls = this[ZaAccount.A2_memberOf];
    if (dls != null) {
        var direct_dls = dls[ZaAccount.A2_directMemberList];
        var indirect_dls = dls[ZaAccount.A2_indirectMemberList];

        if ((direct_dls.length + indirect_dls.length) > 0) {
            var dlsTi = new ZaTreeItemData({
                    text: ZaMsg.OVP_distributionLists,
                    count:direct_dls.length + indirect_dls.length,
                    image:"DistributionList",
                    mappingId: ZaZmailAdmin._DISTRIBUTION_LISTS_LIST_VIEW,
                    path: parentPath + ZaTree.SEPERATOR + this.name + ZaTree.SEPERATOR + ZaMsg.OVP_distributionLists
                }
            );
            dlsTi.setData(ZaAccount.A2_memberOf, dls);
            ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._DISTRIBUTION_LISTS_LIST_VIEW] = ZaOverviewPanelController.dlListTreeListener;
            Tis.push(dlsTi);
        }
    }

    var cosTi = new ZaTreeItemData({
                text: cos.name,
                image:"COS",
                forceNode: true,
                mappingId: ZaZmailAdmin._COS_VIEW,
                path: parentPath + ZaTree.SEPERATOR + cos.name
                }
            );
    cosTi.setData(ZaOverviewPanelController._OBJ_ID, cos.id);
    cosTi.setData("skipHistory", "TRUE");
    ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._COS_VIEW] = ZaOverviewPanelController.cosTreeListener;
    Tis.push(cosTi);

    var domainTi = new ZaTreeItemData({
                text: domainName,
                image:"Domain",
                forceNode: true,
                mappingId: ZaZmailAdmin._DOMAIN_VIEW,
                path: parentPath + ZaTree.SEPERATOR + domainName
                }
            );
    domainTi.setData(ZaOverviewPanelController._OBJ_ID, domainObj.id);
    domainTi.setData("skipHistory", "TRUE");
    ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._DOMAIN_VIEW] = ZaOverviewPanelController.domainTreeListener;
    Tis.push(domainTi);
    /*
    var zimletTi = new ZaTreeItemData({
                text: ZaMsg.TABT_Zimlets,
                //type: 1,
                count:zimletList.length,
                mappingId:ZaZmailAdmin._ZIMLET_LIST_VIEW,
                path: parentPath + ZaTree.SEPERATOR + item.name + ZaTree.SEPERATOR + ZaMsg.TABT_Zimlets
                }
            );
    ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._ZIMLET_LIST_VIEW] = ZaOverviewPanelController.zimletListTreeListener;
    */
    return Tis;
}
ZaItem.getRelatedMethods["ZaAccount"].push(ZaAccount.getRelatedList);

