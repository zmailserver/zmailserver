/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013 VMware, Inc.
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
* @class ZaDomain
* @ constructor ZaDomain
* @param app reference to the application instance
* Data model for zmailDomain object
* @author Greg Solovyev
**/

ZaDomain = function() {
	ZaItem.call(this,  "ZaDomain");
	this.attrs = new Object();
	this.id = "";
	this.name="";
	this.type=ZaItem.DOMAIN;

	//default attributes
	this.attrs[ZaDomain.A_zmailGalMode] = ZaDomain.GAL_Mode_internal;
    this.attrs[ZaDomain.A_AuthMech] = ZaDomain.AuthMech_zmail;
	this.attrs[ZaDomain.A_GALSyncUseGALSearch]='TRUE';
	this.notebookAcls = {};
	this[ZaDomain.A_NotebookTemplateFolder] = "Template";
	this[ZaDomain.A_NotebookTemplateDir] = "/opt/zmail/wiki/Template";
    this.attrs[ZaDomain.A_zmailAuthMechAdmin] = ZaDomain.AuthMech_ad;
	this.notebookAcls[ZaDomain.A_NotebookAllACLs] = {r:0,w:0,i:0,d:0,a:0,x:0};
	this.notebookAcls[ZaDomain.A_NotebookPublicACLs] = {r:0,w:0,i:0,d:0,a:0,x:0};
	this.notebookAcls[ZaDomain.A_NotebookDomainACLs] = {r:1,w:1,i:1,d:1,a:0,x:0};
	this.notebookAcls[ZaDomain.A_NotebookUserACLs] = [/*{name:"", acl:{r:0,w:0,i:0,d:0,a:0,x:0}}*/];
	this.notebookAcls[ZaDomain.A_NotebookGroupACLs] = [/*{name:"", acl:{r:0,w:0,i:0,d:0,a:0,x:0}, 
			toString:function () {
				return [this.name,this.acl[r],this.acl[w],this.acl[i],this.acl[d],this.acl[a],this.acl[x]].join();
			}
		}*/];

    this.attrs[ZaDomain.A_zmailDomainCOSMaxAccounts ] = [];
    this.attrs[ZaDomain.A_zmailDomainFeatureMaxAccounts ] = [];
}
ZaDomain.DEF_WIKI_ACC = "domain_wiki";
ZaDomain.WIKI_FOLDER_ID = "12";
ZaDomain.RESULTSPERPAGE = ZaSettings.RESULTSPERPAGE; 
ZaDomain.MAXSEARCHRESULTS = ZaSettings.MAXSEARCHRESULTS;
ZaDomain.prototype = new ZaItem;
ZaDomain.prototype.constructor = ZaDomain;
ZaDomain.ACLLabels = {r:ZaMsg.ACL_R, w:ZaMsg.ACL_W, i:ZaMsg.ACL_I, a:ZaMsg.ACL_A, d:ZaMsg.ACL_D, x:ZaMsg.ACL_X};
ZaItem.loadMethods["ZaDomain"] = new Array();
ZaItem.initMethods["ZaDomain"] = new Array();
ZaItem.modifyMethods["ZaDomain"] = new Array();
ZaItem.modifyMethodsExt["ZaDomain"] = new Array();
ZaItem.createMethods["ZaDomain"] = new Array();
ZaItem.getRelatedMethods["ZaDomain"] = new Array();

ZaDomain.DOMAIN_STATUS_ACTIVE = "active";
ZaDomain.DOMAIN_STATUS_MAINTENANCE = "maintenance";
ZaDomain.DOMAIN_STATUS_LOCKED = "locked";
ZaDomain.DOMAIN_STATUS_CLOSED = "closed";
ZaDomain.DOMAIN_STATUS_SUSPENDED = "suspended";
ZaDomain.DOMAIN_STATUS_SHUTDOWN = "shutdown";

ZaDomain.SPNEGO_SUPPORT_UA = [".*Windows.*Firefox/3.*",".*MSIE.*Windows.*",".*Windows.*Chrome.*",
    ".*Windows.*Safari.*",".*Macintosh.*Safari.*"];

ZaDomain._domainStatus = 
function(val) {
	var desc = ZaDomain._DOMAIN_STATUS[val];
	return (desc == null) ? val : desc;
}


/* Translation of Domain status values into screen names */
ZaDomain._DOMAIN_STATUS = new Object ();
ZaDomain.initDomainStatus = function() {
	ZaDomain._DOMAIN_STATUS[ZaDomain.DOMAIN_STATUS_ACTIVE] = ZaMsg.domainStatus_active;
	ZaDomain._DOMAIN_STATUS[ZaDomain.DOMAIN_STATUS_CLOSED] = ZaMsg.domainStatus_closed;
	ZaDomain._DOMAIN_STATUS[ZaDomain.DOMAIN_STATUS_LOCKED] = ZaMsg.domainStatus_locked;
	ZaDomain._DOMAIN_STATUS[ZaDomain.DOMAIN_STATUS_SUSPENDED] = ZaMsg.domainStatus_suspended;
	ZaDomain._DOMAIN_STATUS[ZaDomain.DOMAIN_STATUS_MAINTENANCE] = ZaMsg.domainStatus_maintenance;
	ZaDomain._DOMAIN_STATUS[ZaDomain.DOMAIN_STATUS_SHUTDOWN] = ZaMsg.domainStatus_shutdown;
	
	ZaDomain.getDomainStatusChoices = function(){
	    return [
			{value:ZaDomain.DOMAIN_STATUS_ACTIVE, label:ZaDomain._DOMAIN_STATUS[ZaDomain.DOMAIN_STATUS_ACTIVE]}, 
			{value:ZaDomain.DOMAIN_STATUS_CLOSED, label:ZaDomain._DOMAIN_STATUS[ZaDomain.DOMAIN_STATUS_CLOSED]},
			{value:ZaDomain.DOMAIN_STATUS_LOCKED, label: ZaDomain._DOMAIN_STATUS[ZaDomain.DOMAIN_STATUS_LOCKED]},
			{value:ZaDomain.DOMAIN_STATUS_MAINTENANCE, label:ZaDomain._DOMAIN_STATUS[ZaDomain.DOMAIN_STATUS_MAINTENANCE]},
			{value:ZaDomain.DOMAIN_STATUS_SUSPENDED, label:ZaDomain._DOMAIN_STATUS[ZaDomain.DOMAIN_STATUS_SUSPENDED]}
		];
	}
	ZaDomain.domainStatusChoices = ZaDomain.getDomainStatusChoices;	
}
ZaDomain.initDomainStatus();

ZaDomain.getAggregateQuotaPolicyChoices = function () {
    return [
        {label:ZaMsg.Choice_QuotaPolicy_AllAllowed, value:"ALLOWSENDRECEIVE"},
        {label:ZaMsg.Choice_QuotaPolicy_BlockSends, value:"BLOCKSEND"},
        {label:ZaMsg.Choice_QuotaPolicy_AllBlocked, value:"BLOCKSENDRECEIVE"}
    ];
}
ZaDomain.aggregateQuotaPolicyChoices = ZaDomain.getAggregateQuotaPolicyChoices;

ZaDomain.domainTypes = {alias: "alias", local: "local"} ;
ZaDomain.protocolChoices = {http: "http", https: "https"};

//attribute name constants, this values are taken from zmail.schema
ZaDomain.A_description = "description";
ZaDomain.A_notes = "zmailNotes";
ZaDomain.A_domainName = "zmailDomainName";
ZaDomain.A_domainType = "zmailDomainType" ;
ZaDomain.A_domainDefaultCOSId = "zmailDomainDefaultCOSId";
ZaDomain.A_zmailDomainStatus = "zmailDomainStatus";
ZaDomain.A_zmailPublicServiceHostname = "zmailPublicServiceHostname";
ZaDomain.A_zmailPublicServicePort = "zmailPublicServicePort";
ZaDomain.A_zmailPublicServiceProtocol = "zmailPublicServiceProtocol";
ZaDomain.A_zmailDNSCheckHostname = "zmailDNSCheckHostname";
ZaDomain.A_zmailBasicAuthRealm = "zmailBasicAuthRealm";

//GAL search
ZaDomain.A_zmailGalMaxResults = "zmailGalMaxResults";
ZaDomain.A_zmailGalMode = "zmailGalMode";
ZaDomain.A_GalLdapURL = "zmailGalLdapURL";
ZaDomain.A_GalLdapSearchBase = "zmailGalLdapSearchBase";
ZaDomain.A_GalLdapBindDn = "zmailGalLdapBindDn";
ZaDomain.A_GalLdapBindPassword = "zmailGalLdapBindPassword";
ZaDomain.A_GalLdapBindPasswordConfirm = "zmailGalLdapBindPasswordConfirm";
ZaDomain.A_GalLdapFilter = "zmailGalLdapFilter";
ZaDomain.A_zmailGalAutoCompleteLdapFilter = "zmailGalAutoCompleteLdapFilter";
//GAL Sync
ZaDomain.A_zmailGalSyncLdapURL = "zmailGalSyncLdapURL";
ZaDomain.A_zmailGalSyncLdapSearchBase="zmailGalSyncLdapSearchBase";
ZaDomain.A_zmailGalSyncLdapFilter="zmailGalSyncLdapFilter";
ZaDomain.A_zmailGalSyncLdapAuthMech="zmailGalSyncLdapAuthMech";
ZaDomain.A_zmailGalSyncLdapBindDn="zmailGalSyncLdapBindDn";
ZaDomain.A_zmailGalSyncLdapBindPassword="zmailGalSyncLdapBindPassword";

//GAL Sync accounts
ZaDomain.A_zmailGalAccountId = "zmailGalAccountId";

ZaDomain.A_zmailFeatureCalendarReminderDeviceEmailEnabled = "zmailFeatureCalendarReminderDeviceEmailEnabled";

ZaDomain.A_mailHost = "zmailMailHost";
//Auth
ZaDomain.A_AuthMech = "zmailAuthMech";
ZaDomain.A_AuthLdapURL = "zmailAuthLdapURL";
ZaDomain.A_AuthLdapUserDn = "zmailAuthLdapBindDn";
ZaDomain.A_AuthLdapSearchBase = "zmailAuthLdapSearchBase";
ZaDomain.A_AuthLdapSearchFilter = "zmailAuthLdapSearchFilter";
ZaDomain.A_AuthLdapSearchBindDn ="zmailAuthLdapSearchBindDn";
ZaDomain.A_AuthLdapSearchBindPassword="zmailAuthLdapSearchBindPassword";

ZaDomain.A_zmailAdminConsoleDNSCheckEnabled = "zmailAdminConsoleDNSCheckEnabled";
ZaDomain.A_zmailAdminConsoleCatchAllAddressEnabled = "zmailAdminConsoleCatchAllAddressEnabled";
ZaDomain.A_zmailMailCatchAllAddress = "zmailMailCatchAllAddress" ;
ZaDomain.A_zmailMailCatchAllForwardingAddress = "zmailMailCatchAllForwardingAddress" ;
ZaDomain.A_zmailAdminConsoleSkinEnabled = "zmailAdminConsoleSkinEnabled";
ZaDomain.A_zmailAdminConsoleLDAPAuthEnabled = "zmailAdminConsoleLDAPAuthEnabled" ;
ZaDomain.A_zmailAuthLdapStartTlsEnabled = "zmailAuthLdapStartTlsEnabled";
ZaDomain.A_zmailAuthFallbackToLocal = "zmailAuthFallbackToLocal";
ZaDomain.A_zmailPasswordChangeListener = "zmailPasswordChangeListener";
ZaDomain.A_domainMaxAccounts = "zmailDomainMaxAccounts";
//internal attributes - not synched with the server code yet
//GAL               
ZaDomain.A_GALServerType = "galservertype";
ZaDomain.A_GALSyncServerType = "galsyncservertype";
ZaDomain.A_GALSyncUseGALSearch = "galsyncusegalsearch";
//ZaDomain.A_GALServerName = "galservername";
//ZaDomain.A_GALServerPort = "galserverport";
//ZaDomain.A_GALUseSSL = "galusessl";
ZaDomain.A_GALSearchTestMessage = "galsearchtestmessage";
ZaDomain.A_GALSyncTestMessage = "galsynctestmessage";
ZaDomain.A_GALSearchTestResultCode = "galsearchtestresutcode";
ZaDomain.A_GALSyncTestResultCode = "galsynctestresutcode";
ZaDomain.A_GALSampleQuery = "samplequery";
ZaDomain.A_UseBindPassword = "usebindpassword";
ZaDomain.A_SyncUseBindPassword = "syncusebindpassword";
ZaDomain.A_GALTestSearchResults = "galtestsearchresults";
ZaDomain.A_NotebookTemplateDir = "templatedir";
ZaDomain.A_NotebookTemplateFolder = "templatefolder";
ZaDomain.A_NotebookAccountPassword = "noteBookAccountPassword";
ZaDomain.A_NotebookAccountPassword2 = "noteBookAccountPassword2";
ZaDomain.A_CreateNotebook = "createNotebook";
ZaDomain.A_OverwriteTemplates = "overwritetemplates";
ZaDomain.A_OverwriteNotebookACLs = "overwritenotebookacls";
ZaDomain.A_NotebookPublicACLs = "pub";
ZaDomain.A_NotebookAllACLs = "all";
ZaDomain.A_NotebookDomainACLs = "dom";
ZaDomain.A_NotebookUserACLs = "usr";
ZaDomain.A_NotebookGroupACLs = "grp";
ZaDomain.A_NotebookGuestACLs = "guest";
ZaDomain.A_allNotebookACLS = "allNotebookACLS";
//values
ZaDomain.GAL_Mode_internal = "zmail";
ZaDomain.GAL_Mode_external = "ldap";
ZaDomain.GAL_Mode_both = "both";
ZaDomain.GAL_ServerType_ad = "ad";
ZaDomain.GAL_ServerType_ldap = "ldap";

//Auth
ZaDomain.A_AuthADDomainName = "zmailAuthADDomainName";
//ZaDomain.A_AuthLDAPServerName = "zmailAuthLDAPServerName";
ZaDomain.A_AuthLDAPSearchBase = "zmailAuthLDAPSearchBase";
//ZaDomain.A_AuthLDAPServerPort = "zmailAuthLDAPServerPort";
ZaDomain.A_AuthTestUserName = "authtestusername";
ZaDomain.A_AuthTestPassword = "authtestpassword";
ZaDomain.A_AuthTestMessage = "authtestmessage";
ZaDomain.A_AuthTestResultCode = "authtestresutcode";
ZaDomain.A_AuthComputedBindDn = "authcomputedbinddn";
ZaDomain.A_AuthUseBindPassword = "authusebindpassword";
ZaDomain.A_AuthLdapSearchBindPasswordConfirm = "authldapsearchBindpasswordconfirm";
ZaDomain.A_GalSyncLdapBindPasswordConfirm = "syncldappasswordconfirm";
ZaDomain.A_zmailVirtualHostname = "zmailVirtualHostname";

ZaDomain.A_zmailSSLCertificate = "zmailSSLCertificate";
ZaDomain.A_zmailSSLPrivateKey = "zmailSSLPrivateKey";


//server value constants
ZaDomain.AuthMech_ad = "ad";
ZaDomain.AuthMech_ldap = "ldap";
ZaDomain.AuthMech_zmail = "zmail";
ZaDomain.A_zmailNotebookAccount = "zmailNotebookAccount";

//interop
ZaDomain.A_zmailFreebusyExchangeAuthUsername = "zmailFreebusyExchangeAuthUsername" ;
ZaDomain.A_zmailFreebusyExchangeAuthPassword = "zmailFreebusyExchangeAuthPassword" ;
ZaDomain.A_zmailFreebusyExchangeAuthScheme  = "zmailFreebusyExchangeAuthScheme" ;
ZaDomain.A_zmailFreebusyExchangeServerType = "zmailFreebusyExchangeServerType" ;
ZaDomain.A_zmailFreebusyExchangeURL ="zmailFreebusyExchangeURL";
ZaDomain.A_zmailFreebusyExchangeUserOrg = "zmailFreebusyExchangeUserOrg" ;

ZaDomain.A_zmailAvailableSkin = "zmailAvailableSkin";
ZaDomain.A_zmailZimletDomainAvailableZimlets = "zmailZimletDomainAvailableZimlets" ;
//hosted attributes
ZaDomain.A_zmailDomainCOSMaxAccounts = "zmailDomainCOSMaxAccounts" ;
ZaDomain.A_zmailDomainFeatureMaxAccounts = "zmailDomainFeatureMaxAccounts" ;
ZaDomain.A2_account_limit = "account_limit" ;

// help URL
ZaDomain.A_zmailHelpAdminURL = "zmailHelpAdminURL";
ZaDomain.A_zmailHelpDelegatedURL = "zmailHelpDelegatedURL";
// login / logout URL
ZaDomain.A_zmailAdminConsoleLoginURL = "zmailAdminConsoleLoginURL";
ZaDomain.A_zmailAdminConsoleLogoutURL = "zmailAdminConsoleLogoutURL";
// Kerberos
ZaDomain.A_zmailAuthKerberos5Realm = "zmailAuthKerberos5Realm";
// web client sso
ZaDomain.A_zmailWebClientLoginURL = "zmailWebClientLoginURL";
ZaDomain.A_zmailWebClientLogoutURL = "zmailWebClientLogoutURL";
ZaDomain.A_zmailWebClientLoginURLAllowedUA = "zmailWebClientLoginURLAllowedUA";
ZaDomain.A_zmailWebClientLogoutURLAllowedUA = "zmailWebClientLogoutURLAllowedUA";
ZaDomain.A_zmailWebClientLoginURLAllowedIP = "zmailWebClientLoginURLAllowedIP";
ZaDomain.A_zmailWebClientLogoutURLAllowedIP = "zmailWebClientLogoutURLAllowedIP";
ZaDomain.A_zmailForceClearCookies = "zmailForceClearCookies";

// web client authentication
ZaDomain.A_zmailReverseProxyClientCertMode = "zmailReverseProxyClientCertMode";
ZaDomain.A_zmailMailSSLClientCertPrincipalMap = "zmailMailSSLClientCertPrincipalMap";
ZaDomain.A_zmailReverseProxyClientCertCA = "zmailReverseProxyClientCertCA";

// Provision
ZaDomain.A_zmailAutoProvMode = "zmailAutoProvMode";
ZaDomain.A_zmailAutoProvAuthMech = "zmailAutoProvAuthMech";
ZaDomain.A_zmailAutoProvLdapURL = "zmailAutoProvLdapURL";
ZaDomain.A_zmailAutoProvLdapStartTlsEnabled = "zmailAutoProvLdapStartTlsEnabled";
ZaDomain.A_zmailAutoProvLdapAdminBindDn = "zmailAutoProvLdapAdminBindDn";
ZaDomain.A_zmailAutoProvLdapAdminBindPassword = "zmailAutoProvLdapAdminBindPassword";
ZaDomain.A_zmailAutoProvLdapSearchBase = "zmailAutoProvLdapSearchBase";
ZaDomain.A_zmailAutoProvLdapSearchFilter = "zmailAutoProvLdapSearchFilter";
ZaDomain.A_zmailAutoProvLdapBindDn = "zmailAutoProvLdapBindDn";
ZaDomain.A_zmailAutoProvAccountNameMap = "zmailAutoProvAccountNameMap";
ZaDomain.A_zmailAutoProvAttrMap = "zmailAutoProvAttrMap";
ZaDomain.A_zmailAutoProvNotificationFromAddress = "zmailAutoProvNotificationFromAddress";
ZaDomain.A_zmailAutoProvBatchSize = "zmailAutoProvBatchSize";
ZaDomain.A_zmailAutoProvLastPolledTimestamp = "zmailAutoProvLastPolledTimestamp";
ZaDomain.A2_zmailAutoProvModeEAGEREnabled = ZaDomain.A_zmailAutoProvMode + "_EAGER";
ZaDomain.A2_zmailAutoProvModeLAZYEnabled = ZaDomain.A_zmailAutoProvMode + "_LAZY";
ZaDomain.A2_zmailAutoProvModeMANUALEnabled = ZaDomain.A_zmailAutoProvMode + "_MANUAL";
ZaDomain.A2_zmailAutoProvAuthMechLDAPEnabled = ZaDomain.A_zmailAutoProvAuthMech + "_LDAP";
ZaDomain.A2_zmailAutoProvAuthMechPREAUTHEnabled = ZaDomain.A_zmailAutoProvAuthMech + "_PREAUTH";
ZaDomain.A2_zmailAutoProvAuthMechKRB5Enabled = ZaDomain.A_zmailAutoProvAuthMech + "_KRB5";
ZaDomain.A2_zmailAutoProvAuthMechSPNEGOEnabled = ZaDomain.A_zmailAutoProvAuthMech + "_SPNEGO";
ZaDomain.A2_zmailAutoProvServerList = "zmailAutoProvServerList";
ZaDomain.A2_zmailAutoProvSelectedServerList = "zmailAutoProvSelectedDomainList";
ZaDomain.A2_zmailAutoProvPollingInterval = "zmailAutoProvPollingInterval";
ZaDomain.A2_zmailAutoProvSearchActivated = "zmailAutoProvSearchAcctActivated";
ZaDomain.A2_zmailAutoProvAccountPool = "zmailAutoProvAccountPool";
ZaDomain.A2_zmailAutoProvAccountPoolPageNum = "zmailAutoProvAccountPoolPageNum";
ZaDomain.A2_zmailAutoProvAccountSrcSelectedPool = "zmailAutoProvAccountSrcSelectedPool";
ZaDomain.A2_zmailAutoProvAccountTargetPool = "zmailAutoProvAccountTargetPool";
ZaDomain.A2_zmailAutoProvAccountTgtSelectedPool = "zmailAutoProvAccountTgtSelectedPool";
ZaDomain.A2_zmailAutoProvAccountPoolPageTotal = "zmailAutoProvAccountPoolPageTotal";
ZaDomain.A2_zmailAutoProvAccountPassword = "zmailAutoProvAccountPassword";
ZaDomain.A2_zmailAutoProvAccountPasswordInDlg = ZaDomain.A2_zmailAutoProvAccountPassword + "InDlg";
ZaDomain.A2_zmailAutoProvAccountPasswordAgainInDlg = ZaDomain.A2_zmailAutoProvAccountPassword + "AgainInDlg";
ZaDomain.A2_zmailAutoProvAccountPasswordUnmatchedWarning = ZaDomain.A2_zmailAutoProvAccountPassword + "UnmatchedWarning";

//skin properties
ZaDomain.A_zmailSkinForegroundColor = "zmailSkinForegroundColor" ;
ZaDomain.A_zmailSkinBackgroundColor = "zmailSkinBackgroundColor" ;
ZaDomain.A_zmailSkinSecondaryColor = "zmailSkinSecondaryColor" ;
ZaDomain.A_zmailSkinSelectionColor  = "zmailSkinSelectionColor" ;

ZaDomain.A_zmailSkinLogoURL ="zmailSkinLogoURL" ;
ZaDomain.A_zmailSkinLogoLoginBanner = "zmailSkinLogoLoginBanner" ;
ZaDomain.A_zmailSkinLogoAppBanner = "zmailSkinLogoAppBanner" ;

// domain quota
ZaDomain.A_zmailMailDomainQuota = "zmailMailDomainQuota";
ZaDomain.A_zmailDomainAggregateQuota = "zmailDomainAggregateQuota";
ZaDomain.A_zmailDomainAggregateQuotaPolicy = "zmailDomainAggregateQuotaPolicy";
ZaDomain.A_zmailDomainAggregateQuotaWarnPercent = "zmailDomainAggregateQuotaWarnPercent";
ZaDomain.A_zmailDomainAggregateQuotaWarnEmailRecipient = "zmailDomainAggregateQuotaWarnEmailRecipient";

// regex of domain name
ZaDomain.A_zmailMailAddressValidationRegex = "zmailMailAddressValidationRegex";

//email setting for auto provision
ZaDomain.A_zmailAutoProvNotificationSubject = "zmailAutoProvNotificationSubject";
ZaDomain.A_zmailAutoProvNotificationBody = "zmailAutoProvNotificationBody";

ZaDomain.A_zmailDomainAliasTargetId = "zmailDomainAliasTargetId" ;
ZaDomain.A2_zmailDomainAliasTarget = "zmailDomainAliasTargetName" ;
ZaDomain.A_zmailPrefTimeZoneId = "zmailPrefTimeZoneId" ;
ZaDomain.A_zmailAdminConsoleLoginMessage = "zmailAdminConsoleLoginMessage" ;
ZaDomain.A2_allowClearTextLDAPAuth = "allowClearTextLdapAuth" ;
ZaDomain.A2_isTestingGAL = "isTestingGAL";
ZaDomain.A2_isTestingSync = "isTestingSync";
ZaDomain.A2_isTestingAuth = "isTestingAuth";
ZaDomain.A2_acl_selection_cache = "acl_selection_cache";
ZaDomain.A2_gal_sync_accounts = "gal_sync_accounts";
ZaDomain.A2_new_gal_sync_account_name = "new_gal_sync_account_name";
ZaDomain.A2_new_internal_gal_ds_name = "new_internal_gal_ds_name";
ZaDomain.A2_new_external_gal_ds_name = "new_external_gal_ds_name";
ZaDomain.A2_new_internal_gal_polling_interval = "new_internal_gal_polling_interval";
ZaDomain.A2_new_external_gal_polling_interval = "new_external_gal_polling_interval";
ZaDomain.A2_gal_sync_accounts_set = "gal_sync_accounts_set";
ZaDomain.A2_create_gal_acc = "create_gal_acc";

ZaDomain.A2_domain_account_quota = "domain_account_quota";
//result codes returned from Check* requests
ZaDomain.Check_OK = "check.OK";
ZaDomain.Check_UNKNOWN_HOST="check.UNKNOWN_HOST";
ZaDomain.Check_CONNECTION_REFUSED = "check.CONNECTION_REFUSED";
ZaDomain.Check_SSL_HANDSHAKE_FAILURE = "check.SSL_HANDSHAKE_FAILURE";
ZaDomain.Check_COMMUNICATION_FAILURE = "check.COMMUNICATION_FAILURE";
ZaDomain.Check_AUTH_FAILED = "check.AUTH_FAILED";
ZaDomain.Check_AUTH_NOT_SUPPORTED = "check.AUTH_NOT_SUPPORTED";
ZaDomain.Check_NAME_NOT_FOUND = "check.NAME_NOT_FOUND";
ZaDomain.Check_INVALID_SEARCH_FILTER = "check.INVALID_SEARCH_FILTER";
ZaDomain.Check_FAILURE = "check.FAILURE"; 
ZaDomain.Check_FAULT = "Fault";
ZaDomain.Check_SKIPPED = "Skiped";

ZaDomain.AUTH_MECH_CHOICES = [ZaDomain.AuthMech_ad,ZaDomain.AuthMech_ldap,ZaDomain.AuthMech_zmail];

ZaDomain.LOCAL_DOMAIN_QUERY = "(zmailDomainType=local)";

//constants for rights
ZaDomain.RIGHT_LIST_DOMAIN = "listDomain";
ZaDomain.RIGHT_CREATE_TOP_DOMAIN = "createTopDomain";
ZaDomain.RIGHT_DELETE_DOMAIN = "deleteDomain";
ZaDomain.RIGHT_RENAME_DOMAIN = "renameDomain";
ZaDomain.RIGHT_CREATE_SUB_DOMAIN = "createSubDomain";
ZaDomain.RIGHT_CREATE_ACCOUNT = "createAccount";
ZaDomain.RIGHT_CREATE_CALRES = "createCalendarResource";
ZaDomain.RIGHT_CREATE_DL = "createDistributionList";
ZaDomain.RIGHT_CREATE_ALIAS = "createAlias";
ZaDomain.RIGHT_ADMIN_LOGIN_AS = "adminLoginAs";
ZaDomain.RIGHT_CHECK_MX_RECORD = "checkDomainMXRecord";
ZaDomain.CHECK_AUTH_CONFIG = "checkExchangeAuthConfig";
ZaDomain.RIGHT_GET_DOMAIN_QUOTA = "getDomainQuotaUsage";
ZaDomain.cacheCounter = 0;
ZaDomain.staticDomainByNameCacheTable = {};
ZaDomain.staticDomainByIdCacheTable = {};
ZaDomain.putDomainToCache = function(domain) {
	if(ZaDomain.cacheCounter==100) {
		ZaDomain.staticDomainByNameCacheTable = {};
		ZaDomain.staticDomainByIdCacheTable = {};
		ZaDomain.cacheCounter = 0;
	}
		
	if(!ZaDomain.staticDomainByNameCacheTable[domain.name] || !ZaDomain.staticDomainByIdCacheTable[domain.id]) {
		ZaDomain.cacheCounter++;
	}

    	if(domain.name)
    		ZaDomain.staticDomainByNameCacheTable[domain.name] = domain;
  	
	if(domain.id)
    		ZaDomain.staticDomainByIdCacheTable[domain.id] = domain;
}
ZaDomain.compareACLs = function (val1, val2) {
	if(AjxUtil.isEmpty(val1.name) && AjxUtil.isEmpty(val2.name)) {
		if(AjxUtil.isEmpty(val1.gt) && AjXUtil.isEmpty(val2.gt)) {
			return 0;
		}
		
		if(val1.gt == val2.gt) 
			return 0;
		
		if(val1.gt < val2.gt)
			return -1;

		if(val1.gt > val2.gt)
			return 1;
	} else {
		if(val1.gt == val2.gt) {
			if(val1.name == val2.name)
				return 0;
			if(val1.name < val2.name)
				return -1;
			if(val1.name > val2.name)
				return 1;	
		} else {
			if(val1.gt == val2.gt) 
				return 0;
			
			if(val1.gt < val2.gt)
				return -1;
	
			if(val1.gt > val2.gt)
				return 1;
		}	
	}		
}
//Use ZaSearch.SearchDirectory
//In order to keep the domain list synchronized with server, we use synchronous call here.
ZaDomain.getAll =
function(target) {
	var query = "";
        if(!ZaZmailAdmin.hasGlobalDomainListAccess()) {
            var domainNameList = ZaApp.getInstance()._domainNameList;
            if(!domainNameList || !(domainNameList instanceof Array) || domainNameList.length == 0) {
                return  new ZaItemList(ZaDomain);
            }
            if(domainNameList && domainNameList instanceof Array) {
                for(var i = 0; i < domainNameList.length; i++) {
                    if(!target || domainNameList[i].indexOf(target) != -1)
                    query += "(" + ZaDomain.A_domainName + "=" + domainNameList[i] + ")";
                }
                if(domainNameList.length > 1)
                    query = "(|" + query + ")";
            }
        } else
	    if(target) query = "(" + ZaDomain.A_domainName + "=*" + target + "*)";
	var params = {
//		query: ZaDomain.LOCAL_DOMAIN_QUERY,
        	query: query,
		types:[ZaSearch.DOMAINS],
		sortBy:ZaDomain.A_domainName,
		offset:"0",
		attrs:[ZaDomain.A_domainName,ZaDomain.A_zmailDomainStatus,ZaItem.A_zmailId, ZaDomain.A_domainType, ZaDomain.A_AuthMech],
		sortAscending:"1",
		limit:ZaDomain.MAXSEARCHRESULTS,
		ignoreTooManyResultsException: true,
		exceptionFrom: "ZaDomain.getAll",
		controller: ZaApp.getInstance().getCurrentController()
	}
	var list = new ZaItemList(ZaDomain);
	var responce = ZaSearch.searchDirectory(params);
	if(responce) {
		var resp = responce.Body.SearchDirectoryResponse;
		if(resp != null) {
			ZaSearch.TOO_MANY_RESULTS_FLAG = false;
			list.loadFromJS(resp);		
		}
	}
	return list;
}

/**
* Creates a new ZaDomain. This method makes SOAP request (CreateDomainRequest) to create a new domain record in LDAP. 
* @param attrs
* @param name 
* @return ZaDomain
**/
ZaDomain.createMethod =
function(tmpObj, newDomain) {

	if(tmpObj.attrs == null) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_UNKNOWN, null);
		return null;	
	}
	
	//name
	if(tmpObj.attrs[ZaDomain.A_domainName] ==null || tmpObj.attrs[ZaDomain.A_domainName].length < 1) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_DOMAIN_NAME_REQUIRED);
		return null;
	}
	tmpObj.name = tmpObj.attrs[ZaDomain.A_domainName];
	//check values
	if(!AjxUtil.isEmpty(tmpObj.attrs[ZaDomain.A_zmailGalMaxResults]) && !AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaDomain.A_zmailGalMaxResults])) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR , [ZaMsg.NAD_GalMaxResults]));
		return null;
	}
	
	if(tmpObj.name.length > 256 || tmpObj.attrs[ZaDomain.A_domainName].length > 256) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_DOMAIN_NAME_TOOLONG);
		return null;
	}
	
	if(tmpObj.attrs[ZaDomain.A_zmailGalMode]!=ZaDomain.GAL_Mode_internal) {	
		//check that Filter is provided and at least one server
		if(!tmpObj.attrs[ZaDomain.A_GalLdapFilter]) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_SEARCH_FILTER_REQUIRED);			
			return null;
		}
		if(!tmpObj.attrs[ZaDomain.A_GalLdapURL] || tmpObj.attrs[ZaDomain.A_GalLdapURL].length < 1) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_LDAP_URL_REQUIRED);					
			return null;
		}
	} 	
	if(tmpObj.attrs[ZaDomain.A_AuthMech]!=ZaDomain.AuthMech_zmail) {	
		if(!tmpObj.attrs[ZaDomain.A_AuthLdapURL]) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_LDAP_URL_REQUIRED);
			return null;
		}
	}
	/*var domainRegEx = AjxUtil.DOMAIN_NAME_FULL_RE;
	if( !domainRegEx.test(tmpObj.attrs[ZaDomain.A_domainName]) ) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_DOMAIN_NAME_INVALID);
		return null;
	}
	var nonAlphaNumEx = /[^a-zA-Z0-9\-\.]+/;
	if(nonAlphaNumEx.test(tmpObj.attrs[ZaDomain.A_domainName]) ) {
		//show error msg
		ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_DOMAIN_NAME_INVALID);
		return null;
	}*/	

	var soapDoc = AjxSoapDoc.create("CreateDomainRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("name", tmpObj.attrs[ZaDomain.A_domainName]);
	var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailGalMode]);
	attr.setAttribute("n", ZaDomain.A_zmailGalMode);	

	attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailGalMaxResults]);
	attr.setAttribute("n", ZaDomain.A_zmailGalMaxResults);

	attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_notes]);
	attr.setAttribute("n", ZaDomain.A_notes);	

	// help URL
    if(tmpObj.attrs[ZaDomain.A_zmailHelpAdminURL]) {
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailHelpAdminURL]);
        attr.setAttribute("n", ZaDomain.A_zmailHelpAdminURL);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailHelpDelegatedURL]) {
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailHelpDelegatedURL]);
        attr.setAttribute("n", ZaDomain.A_zmailHelpDelegatedURL);
    }
	// login / logout URL
    if(tmpObj.attrs[ZaDomain.A_zmailAdminConsoleLoginURL]) {
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAdminConsoleLoginURL]);
        attr.setAttribute("n", ZaDomain.A_zmailAdminConsoleLoginURL);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAdminConsoleLogoutURL]) {
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAdminConsoleLogoutURL]);
        attr.setAttribute("n", ZaDomain.A_zmailAdminConsoleLogoutURL);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailSSLCertificate]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailSSLCertificate]);
        attr.setAttribute("n", ZaDomain.A_zmailSSLCertificate);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailSSLPrivateKey]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailSSLPrivateKey]);
        attr.setAttribute("n", ZaDomain.A_zmailSSLPrivateKey);
    }

	if(tmpObj.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled]) {
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled]);
		attr.setAttribute("n", ZaDomain.A_zmailAuthLdapStartTlsEnabled);	
	}
		
	if(tmpObj.attrs[ZaDomain.A_AuthLdapURL]) {
		var temp = tmpObj.attrs[ZaDomain.A_AuthLdapURL].join(" ");
		attr = soapDoc.set("a", temp);
		attr.setAttribute("n", ZaDomain.A_AuthLdapURL);	
				
		if(tmpObj.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled] == "TRUE" && tmpObj.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_ldap) {
			//check that we don't have ldaps://
			if(temp.indexOf("ldaps://") > -1) {
				ZaApp.getInstance().getCurrentController().popupWarningDialog(ZaMsg.Domain_WarningStartTLSIgnored)
			}		
		}		
	}
	
	attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_description]);
	attr.setAttribute("n", ZaDomain.A_description);		
    
	if(tmpObj.attrs[ZaDomain.A_zmailBasicAuthRealm]){
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailBasicAuthRealm]);
        	attr.setAttribute("n", ZaDomain.A_zmailBasicAuthRealm);
	}

	if(tmpObj.attrs[ZaDomain.A_zmailGalMode] != ZaDomain.GAL_Mode_internal) {
		temp = tmpObj.attrs[ZaDomain.A_GalLdapURL].join(" ");
		attr = soapDoc.set("a", temp);
		attr.setAttribute("n", ZaDomain.A_GalLdapURL);	

		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_GalLdapSearchBase]);
		attr.setAttribute("n", ZaDomain.A_GalLdapSearchBase);	

		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_GalLdapBindDn]);
		attr.setAttribute("n", ZaDomain.A_GalLdapBindDn);	

		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_GalLdapBindPassword]);
		attr.setAttribute("n", ZaDomain.A_GalLdapBindPassword);	

		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_GalLdapFilter]);
		attr.setAttribute("n", ZaDomain.A_GalLdapFilter);	
		
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailGalAutoCompleteLdapFilter]);
		attr.setAttribute("n", ZaDomain.A_zmailGalAutoCompleteLdapFilter);			
	}

	if(tmpObj.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_ad) {
		//set bind DN to default for AD
		attr = soapDoc.set("a", "%u@"+tmpObj.attrs[ZaDomain.A_AuthADDomainName]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapUserDn);	
	} else if(tmpObj.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_ldap) {
	
	/*	if(tmpObj.attrs[ZaDomain.A_AuthLdapSearchFilter] ==null || tmpObj.attrs[ZaDomain.A_AuthLdapSearchFilter].length < 1) {
			//show error msg
			ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_SEARCH_FILTER_REQUIRED);
			return null;
		}
	*/
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthLdapSearchFilter]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapSearchFilter);
			
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthLdapSearchBase]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapSearchBase);	
		
		if(tmpObj[ZaDomain.A_AuthUseBindPassword] && tmpObj[ZaDomain.A_AuthUseBindPassword] == "TRUE") {
			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthLdapSearchBindDn]);
			attr.setAttribute("n", ZaDomain.A_AuthLdapSearchBindDn);	
			
			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthLdapSearchBindPassword]);
			attr.setAttribute("n", ZaDomain.A_AuthLdapSearchBindPassword);			
		}
	}

	if(tmpObj.attrs[ZaDomain.A_AuthMech] != ZaDomain.AuthMech_zmail) {
		if(tmpObj.attrs[ZaDomain.A_zmailAuthFallbackToLocal]) {
               		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAuthFallbackToLocal]);
               		attr.setAttribute("n", ZaDomain.A_zmailAuthFallbackToLocal);
		}
		if(tmpObj.attrs[ZaDomain.A_zmailPasswordChangeListener]) {
               		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailPasswordChangeListener]);
               		attr.setAttribute("n", ZaDomain.A_zmailPasswordChangeListener);
		}
	}
	var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthMech]);
	attr.setAttribute("n", ZaDomain.A_AuthMech);	

	if(tmpObj.attrs[ZaDomain.A_domainDefaultCOSId]) {
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_domainDefaultCOSId]);
		attr.setAttribute("n", ZaDomain.A_domainDefaultCOSId);	
	}
	
	if(tmpObj.attrs[ZaDomain.A_zmailPublicServiceHostname]) {
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailPublicServiceHostname]);
		attr.setAttribute("n", ZaDomain.A_zmailPublicServiceHostname);
	}

    if(tmpObj.attrs[ZaDomain.A_zmailPublicServiceProtocol]) {
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailPublicServiceProtocol]);
        attr.setAttribute("n", ZaDomain.A_zmailPublicServiceProtocol);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailPublicServicePort]) {
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailPublicServicePort]);
        attr.setAttribute("n", ZaDomain.A_zmailPublicServicePort);
    }
		
	
	if(tmpObj.attrs[ZaDomain.A_zmailDNSCheckHostname]) {
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailDNSCheckHostname]);
		attr.setAttribute("n", ZaDomain.A_zmailDNSCheckHostname);	
	}
	
	if(tmpObj.attrs[ZaDomain.A_zmailAdminConsoleDNSCheckEnabled]) {
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAdminConsoleDNSCheckEnabled]);
		attr.setAttribute("n", ZaDomain.A_zmailAdminConsoleDNSCheckEnabled);	
	}

	if(tmpObj.attrs[ZaDomain.A_zmailFeatureCalendarReminderDeviceEmailEnabled]) {
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailFeatureCalendarReminderDeviceEmailEnabled]);
		attr.setAttribute("n", ZaDomain.A_zmailFeatureCalendarReminderDeviceEmailEnabled);
	}

    if(tmpObj.attrs[ZaDomain.A_zmailAdminConsoleCatchAllAddressEnabled]) {
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAdminConsoleCatchAllAddressEnabled]);
		attr.setAttribute("n", ZaDomain.A_zmailAdminConsoleCatchAllAddressEnabled);
	}

    if(tmpObj.attrs[ZaDomain.A_zmailAdminConsoleLDAPAuthEnabled]) {
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAdminConsoleLDAPAuthEnabled]);
		attr.setAttribute("n", ZaDomain.A_zmailAdminConsoleLDAPAuthEnabled);
	}

    if(tmpObj.attrs[ZaDomain.A_zmailDomainStatus]) {
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailDomainStatus]);
		attr.setAttribute("n", ZaDomain.A_zmailDomainStatus);	
	}
		
	if(tmpObj.attrs[ZaDomain.A_domainMaxAccounts]) {
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_domainMaxAccounts]);
		attr.setAttribute("n", ZaDomain.A_domainMaxAccounts);	
	}

	if(tmpObj.attrs[ZaDomain.A_zmailVirtualHostname]) {
		if(tmpObj.attrs[ZaDomain.A_zmailVirtualHostname] instanceof Array) {
			var cnt = tmpObj.attrs[ZaDomain.A_zmailVirtualHostname].length;
			for(var ix=0; ix<cnt; ix++) {
				attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailVirtualHostname][ix]);
				attr.setAttribute("n", ZaDomain.A_zmailVirtualHostname);					
			}
		} else {
			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailVirtualHostname]);
			attr.setAttribute("n", ZaDomain.A_zmailVirtualHostname);					
		}
	}
	
    if(tmpObj.attrs[ZaDomain.A_zmailMailAddressValidationRegex]) {
            if(tmpObj.attrs[ZaDomain.A_zmailMailAddressValidationRegex] instanceof Array) {
                    var cnt = tmpObj.attrs[ZaDomain.A_zmailMailAddressValidationRegex].length;
                    for(var ix=0; ix<cnt; ix++) {
                            attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailMailAddressValidationRegex][ix]);
                            attr.setAttribute("n", ZaDomain.A_zmailMailAddressValidationRegex);
                    }
            } else {
                    attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailMailAddressValidationRegex]);
                    attr.setAttribute("n", ZaDomain.A_zmailMailAddressValidationRegex);
            }
    }

    if(tmpObj.attrs[ZaDomain.A_zmailAuthKerberos5Realm]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAuthKerberos5Realm]);
        attr.setAttribute("n", ZaDomain.A_zmailAuthKerberos5Realm);
    }

    if (tmpObj.attrs[ZaDomain.A_zmailForceClearCookies]) {
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailForceClearCookies]);
        attr.setAttribute("n", ZaDomain.A_zmailForceClearCookies);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURL]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURL]);
        attr.setAttribute("n", ZaDomain.A_zmailWebClientLoginURL);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURL]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURL]);
        attr.setAttribute("n", ZaDomain.A_zmailWebClientLogoutURL);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailReverseProxyClientCertMode]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailReverseProxyClientCertMode]);
        attr.setAttribute("n", ZaDomain.A_zmailReverseProxyClientCertMode);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailMailSSLClientCertPrincipalMap]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailMailSSLClientCertPrincipalMap]);
        attr.setAttribute("n", ZaDomain.A_zmailMailSSLClientCertPrincipalMap);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailReverseProxyClientCertCA]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailReverseProxyClientCertCA]);
        attr.setAttribute("n", ZaDomain.A_zmailReverseProxyClientCertCA);
    }

	if(tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA]) {
		if(tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA] instanceof Array) {
			var cnt = tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA].length;
			for(var ix=0; ix<cnt; ix++) {
				attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA][ix]);
				attr.setAttribute("n", ZaDomain.A_zmailWebClientLoginURLAllowedUA);
			}
		} else {
			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA]);
			attr.setAttribute("n", ZaDomain.A_zmailWebClientLoginURLAllowedUA);
		}
	}

	if(tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA]) {
		if(tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA] instanceof Array) {
			var cnt = tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA].length;
			for(var ix=0; ix<cnt; ix++) {
				attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA][ix]);
				attr.setAttribute("n", ZaDomain.A_zmailWebClientLogoutURLAllowedUA);
			}
		} else {
			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA]);
			attr.setAttribute("n", ZaDomain.A_zmailWebClientLogoutURLAllowedUA);
		}
	}

	if(tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP]) {
		if(tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP] instanceof Array) {
			var cnt = tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP].length;
			for(var ix=0; ix<cnt; ix++) {
				attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP][ix]);
				attr.setAttribute("n", ZaDomain.A_zmailWebClientLoginURLAllowedIP);
			}
		} else {
			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP]);
			attr.setAttribute("n", ZaDomain.A_zmailWebClientLoginURLAllowedIP);
		}
	}

	if(tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP]) {
		if(tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP] instanceof Array) {
			var cnt = tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP].length;
			for(var ix=0; ix<cnt; ix++) {
				attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP][ix]);
				attr.setAttribute("n", ZaDomain.A_zmailWebClientLogoutURLAllowedIP);
			}
		} else {
			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP]);
			attr.setAttribute("n", ZaDomain.A_zmailWebClientLogoutURLAllowedIP);
		}
	}

    if(tmpObj.attrs[ZaDomain.A_zmailMailDomainQuota]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailMailDomainQuota]);
        attr.setAttribute("n", ZaDomain.A_zmailMailDomainQuota);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailDomainAggregateQuota]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailDomainAggregateQuota]);
        attr.setAttribute("n", ZaDomain.A_zmailDomainAggregateQuota);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailDomainAggregateQuotaWarnPercent]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailDomainAggregateQuotaWarnPercent]);
        attr.setAttribute("n", ZaDomain.A_zmailDomainAggregateQuotaWarnPercent);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailDomainAggregateQuotaWarnEmailRecipient]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailDomainAggregateQuotaWarnEmailRecipient]);
        attr.setAttribute("n", ZaDomain.A_zmailDomainAggregateQuotaWarnEmailRecipient);
    }

    if(tmpObj.attrs[ZaDomain.A_zmailDomainAggregateQuotaPolicy]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailDomainAggregateQuotaPolicy]);
        attr.setAttribute("n", ZaDomain.A_zmailDomainAggregateQuotaPolicy);
    }
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_CREATE_DOMAIN
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.CreateDomainResponse;	
	newDomain.initFromJS(resp.domain[0]);
}
ZaItem.createMethods["ZaDomain"].push(ZaDomain.createMethod);

ZaDomain.createGalAccounts = function (tmpDomainObj,newDomain) {
    if (tmpDomainObj[ZaDomain.A2_create_gal_acc] && tmpDomainObj[ZaDomain.A2_create_gal_acc]=="TRUE" && !AjxUtil.isEmpty(tmpDomainObj[ZaDomain.A2_gal_sync_accounts_set])) {
        for(var i in tmpDomainObj[ZaDomain.A2_gal_sync_accounts_set]) {
            var tmpObj = tmpDomainObj[ZaDomain.A2_gal_sync_accounts_set][i];
            if (!tmpObj)
                continue;
            if(tmpObj[ZaDomain.A2_new_gal_sync_account_name] &&
                (tmpObj[ZaDomain.A2_new_internal_gal_ds_name] || tmpObj[ZaDomain.A2_new_external_gal_ds_name])) {
                var soapDoc = AjxSoapDoc.create("BatchRequest", "urn:zmail");
                soapDoc.setMethodAttribute("onerror", "stop");
                if(tmpObj[ZaDomain.A2_new_gal_sync_account_name].indexOf("@") < 0) {
                    tmpObj[ZaDomain.A2_new_gal_sync_account_name] = [tmpObj[ZaDomain.A2_new_gal_sync_account_name],"@",tmpDomainObj.attrs[ZaDomain.A_domainName]].join("");
                }
                if((tmpDomainObj.attrs[ZaDomain.A_zmailGalMode] == ZaDomain.GAL_Mode_internal || tmpDomainObj.attrs[ZaDomain.A_zmailGalMode] == ZaDomain.GAL_Mode_both)
                    && tmpObj[ZaDomain.A2_new_gal_sync_account_name] && tmpObj[ZaDomain.A2_new_internal_gal_ds_name] && tmpObj[ZaDomain.A_mailHost]) {
                    var createInternalDSDoc = soapDoc.set("CreateGalSyncAccountRequest", null, null, ZaZmailAdmin.URN);
                    createInternalDSDoc.setAttribute("name", tmpObj[ZaDomain.A2_new_internal_gal_ds_name]);
                    createInternalDSDoc.setAttribute("folder", "_"+tmpObj[ZaDomain.A2_new_internal_gal_ds_name]);
                    createInternalDSDoc.setAttribute("type", "zmail");
                    createInternalDSDoc.setAttribute("domain", tmpDomainObj.attrs[ZaDomain.A_domainName]);
                    createInternalDSDoc.setAttribute("server",tmpObj[ZaDomain.A_mailHost]);
                    //zmailDataSourcePollingInterval
                    soapDoc.set("account", tmpObj[ZaDomain.A2_new_gal_sync_account_name],createInternalDSDoc).setAttribute("by","name");
                    soapDoc.set("a", tmpObj[ZaDomain.A2_new_internal_gal_polling_interval],createInternalDSDoc).setAttribute("n",ZaDataSource.A_zmailDataSourcePollingInterval);
                }

                if(tmpDomainObj.attrs[ZaDomain.A_zmailGalMode] != ZaDomain.GAL_Mode_internal
                    && tmpObj[ZaDomain.A2_new_gal_sync_account_name] && tmpObj[ZaDomain.A2_new_external_gal_ds_name] && tmpObj[ZaDomain.A_mailHost]) {
                    var createExternalDSDoc = soapDoc.set("CreateGalSyncAccountRequest", null, null, ZaZmailAdmin.URN);
                    createExternalDSDoc.setAttribute("name", tmpObj[ZaDomain.A2_new_external_gal_ds_name]);
                    createExternalDSDoc.setAttribute("folder", "_"+tmpObj[ZaDomain.A2_new_external_gal_ds_name]);
                    createExternalDSDoc.setAttribute("type", "ldap");
                    createExternalDSDoc.setAttribute("domain", tmpDomainObj.attrs[ZaDomain.A_domainName]);
                    createExternalDSDoc.setAttribute("server",tmpObj[ZaDomain.A_mailHost]);
                    soapDoc.set("account", tmpObj[ZaDomain.A2_new_gal_sync_account_name],createExternalDSDoc).setAttribute("by","name");
                    soapDoc.set("a", tmpObj[ZaDomain.A2_new_external_gal_polling_interval],createExternalDSDoc).setAttribute("n",ZaDataSource.A_zmailDataSourcePollingInterval);
                }

                try {
                    var params = new Object();
                    params.soapDoc = soapDoc;
                    var reqMgrParams ={
                        controller:ZaApp.getInstance().getCurrentController(),
                        busyMsg : ZaMsg.BUSY_CREATING_GALDS,
                        showBusy:true
                    }
                    var respObj = ZaRequestMgr.invoke(params, reqMgrParams);
                    if(respObj.isException && respObj.isException()) {
                        ZaApp.getInstance().getCurrentController()._handleException(respObj.getException(), "ZaDomain.createGalAccounts", null, false);
                        hasError  = true ;
                        lastException = ex ;
                    } else if(respObj.Body.BatchResponse.Fault) {
                        var fault = respObj.Body.BatchResponse.Fault;
                        if(fault instanceof Array)
                            fault = fault[0];

                        if (fault) {
                            // JS response with fault
                            var ex = ZmCsfeCommand.faultToEx(fault);
                            ZaApp.getInstance().getCurrentController()._handleException(ex,"ZaDomain.createGalAccounts", null, false);
                            hasError = true ;
                            lastException = ex ;
                        }
                    } else {
                        var batchResp = respObj.Body.BatchResponse;
                    }
                } catch (ex) {
                    //show the error and go on
                    ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaDomain.createGalAccounts", null, false);
                    hasError = true ;
                    lastException = ex ;
                }
            }
        }
    }
}
ZaItem.createMethods["ZaDomain"].push(ZaDomain.createGalAccounts);
/*
ZaDomain.prototype.loadNewObjectDefaults = function (domainBy, domain, cosBy, cos) {
	ZaItem.prototype.loadNewObjectDefaults.call(this,domainBy, domain, cosBy, cos);
	this[ZaDomain.A2_new_gal_sync_account_name] = "galsync";
	this[ZaDomain.A2_new_internal_gal_ds_name] = "zmail";
	this[ZaDomain.A2_new_external_gal_ds_name] = "ldap";
		
} */
ZaDomain.canConfigureAuth = function (obj) {
	return (ZaItem.hasRight(ZaDomain.CHECK_AUTH_CONFIG,obj) 
		&& ZaItem.hasWritePermission(ZaDomain.A_AuthMech,obj) 
		&& ZaItem.hasWritePermission(ZaDomain.A_AuthLdapURL,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_AuthLdapUserDn,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_AuthLdapSearchBase,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_AuthLdapSearchFilter,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_AuthLdapSearchBindDn,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_AuthLdapSearchBindPassword,obj));
}
ZaDomain.canConfigureGal = function (obj) {
	return (ZaItem.hasWritePermission(ZaDomain.A_zmailGalAccountId,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailGalMode,obj) 
		&& ZaItem.hasWritePermission(ZaDomain.A_GalLdapURL,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_GalLdapSearchBase,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_GalLdapBindDn,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_GalLdapBindPassword,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_GalLdapFilter,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailGalAutoCompleteLdapFilter,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailGalSyncLdapURL,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailGalSyncLdapSearchBase,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailGalSyncLdapFilter,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailGalSyncLdapBindDn,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailGalSyncLdapBindPassword,obj));
}
ZaDomain.canConfigureAutoProv = function (obj) {
	return (ZaItem.hasRight(ZaDomain.A_zmailAutoProvMode,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailAutoProvAuthMech,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailAutoProvLdapURL,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailAutoProvLdapAdminBindDn,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailAutoProvLdapAdminBindPassword,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailAutoProvLdapSearchBase,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailAutoProvLdapSearchFilter,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailAutoProvLdapBindDn,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailAutoProvAccountNameMap,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailAutoProvAttrMap,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailAutoProvNotificationFromAddress,obj)
		&& ZaItem.hasWritePermission(ZaDomain.A_zmailAutoProvBatchSize,obj));
}

ZaDomain.testAuthSettings = 
function (obj, callback) {
	var soapDoc = AjxSoapDoc.create("CheckAuthConfigRequest", ZaZmailAdmin.URN, null);
	var attr = soapDoc.set("a", obj.attrs[ZaDomain.A_AuthMech]);
	attr.setAttribute("n", ZaDomain.A_AuthMech);
	
	var temp = obj.attrs[ZaDomain.A_AuthLdapURL].join(" ");
	attr = soapDoc.set("a", temp);
	attr.setAttribute("n", ZaDomain.A_AuthLdapURL);	
	
	if(obj.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_ad) {	
		attr = soapDoc.set("a", "%u@"+obj.attrs[ZaDomain.A_AuthADDomainName]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapUserDn);	
	}
	if(obj.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_ldap) {
		if(obj.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled]) {
			attr = soapDoc.set("a", obj.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled]);
			attr.setAttribute("n", ZaDomain.A_zmailAuthLdapStartTlsEnabled);	
		}			
			
		if(obj.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled] == "TRUE") {
			//check that we don't have ldaps://
			if(temp.indexOf("ldaps://") > -1) {
				ZaApp.getInstance().getCurrentController().popupWarningDialog(ZaMsg.Domain_WarningStartTLSIgnored)
			}		
		}
					
		attr = soapDoc.set("a", obj.attrs[ZaDomain.A_AuthLdapSearchBase]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapSearchBase);
	
		attr = soapDoc.set("a", obj.attrs[ZaDomain.A_AuthLdapSearchFilter]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapSearchFilter);

		attr = soapDoc.set("a", obj.attrs[ZaDomain.A_AuthLdapSearchBindDn]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapSearchBindDn);
	
		attr = soapDoc.set("a", obj.attrs[ZaDomain.A_AuthLdapSearchBindPassword]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapSearchBindPassword);
	}

	
	attr = soapDoc.set("name", obj[ZaDomain.A_AuthTestUserName]);
	attr = soapDoc.set("password", obj[ZaDomain.A_AuthTestPassword]);	
	
	var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;
	params.noAuthToken = true;	
	params.asyncMode = true;
	params.callback = callback;
	command.invoke(params);	

}

ZaDomain.getRevokeNotebookACLsRequest = function (permsToRevoke, soapDoc) {
	var cnt = permsToRevoke.length;
	for(var i = 0; i < cnt; i++) {
		var folderActionRequest = soapDoc.set("FolderActionRequest", null, null, "urn:zmailMail");			
		var actionEl = soapDoc.set("action", "",folderActionRequest);
		actionEl.setAttribute("id", ZaDomain.WIKI_FOLDER_ID);	
		actionEl.setAttribute("op", "!grant");	
		actionEl.setAttribute("zid", permsToRevoke[i].zid);				
	}
}

ZaDomain.getGrantNotebookACLsRequest = function (obj, soapDoc) {
	var folderActionRequest = soapDoc.set("FolderActionRequest", null, null, "urn:zmailMail");
	var actionEl = soapDoc.set("action", "",folderActionRequest);
	actionEl.setAttribute("id", ZaDomain.WIKI_FOLDER_ID);	
	actionEl.setAttribute("op", "grant");	
	var grantEl = soapDoc.set("grant", "",actionEl);	
	grantEl.setAttribute("gt", obj.gt);
	if(obj.name) {
		grantEl.setAttribute("d", obj.name);
	}
	var perms = "";
	for(var a in obj.acl) {
		if(obj.acl[a]==1)
			perms+=a;
	}
	grantEl.setAttribute("perm", perms);				
}

ZaDomain.testSyncSettings = function (obj, callback){
	var soapDoc = AjxSoapDoc.create("CheckGalConfigRequest", ZaZmailAdmin.URN, null);
	var attr = soapDoc.set("a", ZaDomain.GAL_Mode_external);
	attr.setAttribute("n", ZaDomain.A_zmailGalMode);

	var temp = obj.attrs[ZaDomain.A_GalLdapURL].join(" ");
	attr = soapDoc.set("a", temp);
	attr.setAttribute("n", ZaDomain.A_GalLdapURL);	
	
	attr = soapDoc.set("a", obj.attrs[ZaDomain.A_GalLdapSearchBase]);
	attr.setAttribute("n", ZaDomain.A_GalLdapSearchBase);	

	attr = soapDoc.set("a", obj.attrs[ZaDomain.A_GalLdapFilter]);
	attr.setAttribute("n", ZaDomain.A_GalLdapFilter);	

	if(obj.attrs[ZaDomain.A_GalLdapBindDn]) {
		attr = soapDoc.set("a", obj.attrs[ZaDomain.A_GalLdapBindDn]);
		attr.setAttribute("n", ZaDomain.A_GalLdapBindDn);
	}

	if(obj.attrs[ZaDomain.A_GalLdapBindPassword]) {
		attr = soapDoc.set("a", obj.attrs[ZaDomain.A_GalLdapBindPassword]);
		attr.setAttribute("n", ZaDomain.A_GalLdapBindPassword);
	}

//	
	if(obj.attrs[ZaDomain.A_GALSyncUseGALSearch]=="FALSE") {
		if(obj.attrs[ZaDomain.A_zmailGalSyncLdapURL]) {
			var temp = obj.attrs[ZaDomain.A_zmailGalSyncLdapURL].join(" ");
			attr = soapDoc.set("a", temp);
			attr.setAttribute("n", ZaDomain.A_zmailGalSyncLdapURL);	
		}
		
		if(obj.attrs[ZaDomain.A_zmailGalSyncLdapSearchBase]) {
			attr = soapDoc.set("a", obj.attrs[ZaDomain.A_zmailGalSyncLdapSearchBase]);
			attr.setAttribute("n", ZaDomain.A_zmailGalSyncLdapSearchBase);	
		}
	
		if(obj.attrs[ZaDomain.A_zmailGalSyncLdapFilter]) {
			attr = soapDoc.set("a", obj.attrs[ZaDomain.A_zmailGalSyncLdapFilter]);
			attr.setAttribute("n", ZaDomain.A_zmailGalSyncLdapFilter);	
		}
	
		if(obj.attrs[ZaDomain.A_zmailGalSyncLdapBindDn]) {
			attr = soapDoc.set("a", obj.attrs[ZaDomain.A_zmailGalSyncLdapBindDn]);
			attr.setAttribute("n", ZaDomain.A_zmailGalSyncLdapBindDn);
		}
	
		if(obj.attrs[ZaDomain.A_zmailGalSyncLdapBindPassword]) {
			attr = soapDoc.set("a", obj.attrs[ZaDomain.A_zmailGalSyncLdapBindPassword]);
			attr.setAttribute("n", ZaDomain.A_zmailGalSyncLdapBindPassword);
		}
	}

	soapDoc.set("action", "sync");	
	var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	params.asyncMode = true;
	params.noAuthToken = true;
	params.callback = callback;
	command.invoke(params);	
}

ZaDomain.testGALSettings = function (obj, callback, sampleQuery) {
	
	//search
	var soapDoc = AjxSoapDoc.create("CheckGalConfigRequest", ZaZmailAdmin.URN, null);
	var attr = soapDoc.set("a", ZaDomain.GAL_Mode_external);
	attr.setAttribute("n", ZaDomain.A_zmailGalMode);

	var temp = obj.attrs[ZaDomain.A_GalLdapURL].join(" ");
	attr = soapDoc.set("a", temp);
	attr.setAttribute("n", ZaDomain.A_GalLdapURL);	
	
	attr = soapDoc.set("a", obj.attrs[ZaDomain.A_GalLdapSearchBase]);
	attr.setAttribute("n", ZaDomain.A_GalLdapSearchBase);	

	attr = soapDoc.set("a", obj.attrs[ZaDomain.A_GalLdapFilter]);
	attr.setAttribute("n", ZaDomain.A_GalLdapFilter);	

	if(obj.attrs[ZaDomain.A_GalLdapBindDn]) {
		attr = soapDoc.set("a", obj.attrs[ZaDomain.A_GalLdapBindDn]);
		attr.setAttribute("n", ZaDomain.A_GalLdapBindDn);
	}

	if(obj.attrs[ZaDomain.A_GalLdapBindPassword]) {
		attr = soapDoc.set("a", obj.attrs[ZaDomain.A_GalLdapBindPassword]);
		attr.setAttribute("n", ZaDomain.A_GalLdapBindPassword);
	}
	soapDoc.set("query", "*" + sampleQuery + "*");
	soapDoc.set("action", "search");
	
	var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;
	params.noAuthToken = true;	
	params.asyncMode = true;
	params.callback = callback;
	command.invoke(params);
}

ZaDomain.modifyGalSettings = 
function(tmpObj) {
	var soapDoc = AjxSoapDoc.create("BatchRequest", "urn:zmail");
	soapDoc.setMethodAttribute("onerror", "stop");
    // Create New GAl Account
	if(tmpObj[ZaDomain.A2_create_gal_acc] && tmpObj[ZaDomain.A2_create_gal_acc]=="TRUE" && !AjxUtil.isEmpty(tmpObj[ZaDomain.A2_gal_sync_accounts_set])) {
        for(var i in tmpObj[ZaDomain.A2_gal_sync_accounts_set]) {
            var newGalAccount = tmpObj[ZaDomain.A2_gal_sync_accounts_set][i];
            if(newGalAccount[ZaDomain.A2_new_gal_sync_account_name].indexOf("@") < 0) {
                newGalAccount[ZaDomain.A2_new_gal_sync_account_name] = [newGalAccount[ZaDomain.A2_new_gal_sync_account_name],"@",tmpObj.attrs[ZaDomain.A_domainName]].join("");
            }
            if((tmpObj.attrs[ZaDomain.A_zmailGalMode] == ZaDomain.GAL_Mode_internal || tmpObj.attrs[ZaDomain.A_zmailGalMode] == ZaDomain.GAL_Mode_both)
                && newGalAccount[ZaDomain.A2_new_internal_gal_ds_name] && newGalAccount[ZaDomain.A_mailHost]) {
                var createInternalDSDoc = soapDoc.set("CreateGalSyncAccountRequest", null, null, ZaZmailAdmin.URN);
                createInternalDSDoc.setAttribute("name", newGalAccount[ZaDomain.A2_new_internal_gal_ds_name]);
                createInternalDSDoc.setAttribute("folder", "_"+newGalAccount[ZaDomain.A2_new_internal_gal_ds_name]);
                createInternalDSDoc.setAttribute("type", "zmail");
                createInternalDSDoc.setAttribute("domain", tmpObj.attrs[ZaDomain.A_domainName]);
                createInternalDSDoc.setAttribute("server",newGalAccount[ZaDomain.A_mailHost]);
                if(newGalAccount[ZaDomain.A2_new_gal_sync_account_name]) {
                    soapDoc.set("account", newGalAccount[ZaDomain.A2_new_gal_sync_account_name],createInternalDSDoc).setAttribute("by","name");

                }  else if (tmpObj[ZaDomain.A2_gal_sync_accounts] && tmpObj[ZaDomain.A2_gal_sync_accounts][i]) {
                    soapDoc.set("account", tmpObj[ZaDomain.A2_gal_sync_accounts][i].name,createExternalDSDoc).setAttribute("by","name");
                }

                if(newGalAccount[ZaDomain.A2_new_internal_gal_polling_interval]) {
                    soapDoc.set("a", newGalAccount[ZaDomain.A2_new_internal_gal_polling_interval],createInternalDSDoc).setAttribute("n",ZaDataSource.A_zmailDataSourcePollingInterval);
                }
            }

            if(tmpObj.attrs[ZaDomain.A_zmailGalMode] != ZaDomain.GAL_Mode_internal
                && newGalAccount[ZaDomain.A2_new_external_gal_ds_name] && newGalAccount[ZaDomain.A_mailHost]) {
                var createExternalDSDoc = soapDoc.set("CreateGalSyncAccountRequest", null, null, ZaZmailAdmin.URN);
                createExternalDSDoc.setAttribute("name", newGalAccount[ZaDomain.A2_new_external_gal_ds_name]);
                createExternalDSDoc.setAttribute("folder", "_"+newGalAccount[ZaDomain.A2_new_external_gal_ds_name]);
                createExternalDSDoc.setAttribute("type", "ldap");
                createExternalDSDoc.setAttribute("domain", tmpObj.attrs[ZaDomain.A_domainName]);
                createExternalDSDoc.setAttribute("server",newGalAccount[ZaDomain.A_mailHost]);
                if(newGalAccount[ZaDomain.A2_new_gal_sync_account_name]) {
                    soapDoc.set("account", newGalAccount[ZaDomain.A2_new_gal_sync_account_name],createExternalDSDoc).setAttribute("by","name");
                }  else if (tmpObj[ZaDomain.A2_gal_sync_accounts] && tmpObj[ZaDomain.A2_gal_sync_accounts][i]) {
                    soapDoc.set("account", tmpObj[ZaDomain.A2_gal_sync_accounts][i].name,createExternalDSDoc).setAttribute("by","name");
                }

                if(newGalAccount[ZaDomain.A2_new_external_gal_polling_interval]) {
                    soapDoc.set("a", newGalAccount[ZaDomain.A2_new_external_gal_polling_interval],createExternalDSDoc).setAttribute("n",ZaDataSource.A_zmailDataSourcePollingInterval);
                }/* else if(tmpObj[ZaDomain.A2_gal_sync_accounts][0][ZaAccount.A2_ldap_ds] && tmpObj[ZaDomain.A2_gal_sync_accounts][0][ZaAccount.A2_ldap_ds].attrs) {
                    soapDoc.set("a", tmpObj[ZaDomain.A2_gal_sync_accounts][0][ZaAccount.A2_ldap_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval],createExternalDSDoc).setAttribute("n",ZaDataSource.A_zmailDataSourcePollingInterval);
                }*/

            }
        }
	}

    // Modify data source of existed gal account
	if(tmpObj[ZaDomain.A2_gal_sync_accounts] && !AjxUtil.isEmpty(tmpObj[ZaDomain.A2_gal_sync_accounts])) {
        for (var i in tmpObj[ZaDomain.A2_gal_sync_accounts]) {
            var currentSyncAccount = tmpObj[ZaDomain.A2_gal_sync_accounts][i];
            if(currentSyncAccount[ZaAccount.A2_zmail_ds]
                && currentSyncAccount[ZaAccount.A2_zmail_ds].attrs
                && this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_zmail_ds]
                && this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_zmail_ds].attrs) {
                if(this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_zmail_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval] !=
                currentSyncAccount[ZaAccount.A2_zmail_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval]) {
                    var modifyDSDoc = soapDoc.set("ModifyDataSourceRequest", null, null, ZaZmailAdmin.URN);
                    soapDoc.set("id", this[ZaDomain.A2_gal_sync_accounts][i].id, modifyDSDoc);
                    var ds = soapDoc.set("dataSource", null,modifyDSDoc);
                    ds.setAttribute("id", this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_zmail_ds].id);
                    var attr = soapDoc.set("a", currentSyncAccount[ZaAccount.A2_zmail_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval],ds);
                    attr.setAttribute("n", ZaDataSource.A_zmailDataSourcePollingInterval);
                }

            }

            if(currentSyncAccount[ZaAccount.A2_ldap_ds]
                && currentSyncAccount[ZaAccount.A2_ldap_ds].attrs
                &&  this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_ldap_ds]
                &&  this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_ldap_ds].attrs) {
                if(this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_ldap_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval] !=
                    currentSyncAccount[ZaAccount.A2_ldap_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval]) {
                    var modifyDSDoc = soapDoc.set("ModifyDataSourceRequest", null, null, ZaZmailAdmin.URN);
                    soapDoc.set("id", this[ZaDomain.A2_gal_sync_accounts][i].id, modifyDSDoc);
                    var ds = soapDoc.set("dataSource", null,modifyDSDoc);
                    ds.setAttribute("id", this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_ldap_ds].id);
                    var attr = soapDoc.set("a", currentSyncAccount[ZaAccount.A2_ldap_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval],ds);
                    attr.setAttribute("n", ZaDataSource.A_zmailDataSourcePollingInterval);
                }

		}
        }
	}	
	var modifyDomainDoc = soapDoc.set("ModifyDomainRequest", null, null, ZaZmailAdmin.URN);
	soapDoc.set("id", this.id,modifyDomainDoc);
	
	var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailGalMode],modifyDomainDoc);
	attr.setAttribute("n", ZaDomain.A_zmailGalMode);	

	if(tmpObj.attrs[ZaDomain.A_zmailGalMode] != ZaDomain.GAL_Mode_internal) {
		var temp = tmpObj.attrs[ZaDomain.A_GalLdapURL].join(" ");
		attr = soapDoc.set("a", temp,modifyDomainDoc);
		attr.setAttribute("n", ZaDomain.A_GalLdapURL);	

		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_GalLdapSearchBase],modifyDomainDoc);
		attr.setAttribute("n", ZaDomain.A_GalLdapSearchBase);	

		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_GalLdapBindDn],modifyDomainDoc);
		attr.setAttribute("n", ZaDomain.A_GalLdapBindDn);	

		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_GalLdapBindPassword],modifyDomainDoc);
		attr.setAttribute("n", ZaDomain.A_GalLdapBindPassword);	

		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_GalLdapFilter],modifyDomainDoc);
		attr.setAttribute("n", ZaDomain.A_GalLdapFilter);	
		
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailGalAutoCompleteLdapFilter],modifyDomainDoc);
		attr.setAttribute("n", ZaDomain.A_zmailGalAutoCompleteLdapFilter);		
	}
	if(tmpObj.attrs[ZaDomain.A_zmailGalMaxResults] != tmpObj.attrs[ZaDomain.A_zmailGalMaxResults]) {
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailGalMaxResults],modifyDomainDoc);
		attr.setAttribute("n", ZaDomain.A_zmailGalMaxResults);	
	}
		
	try {
		params = new Object();
		params.soapDoc = soapDoc;	
		var reqMgrParams ={
			controller:ZaApp.getInstance().getCurrentController(),
			busyMsg : ZaMsg.BUSY_CREATING_GALDS,
			showBusy:true
		}
		var respObj = ZaRequestMgr.invoke(params, reqMgrParams);
		if(respObj.isException && respObj.isException()) {
			ZaApp.getInstance().getCurrentController()._handleException(respObj.getException(), "ZaDomain.modifyGalSettings", null, false);
		    hasError  = true ;
            lastException = ex ;
        } else if(respObj.Body.BatchResponse.Fault) {
			var fault = respObj.Body.BatchResponse.Fault;
			if(fault instanceof Array)
				fault = fault[0];
			
			if (fault) {
				// JS response with fault
				var ex = ZmCsfeCommand.faultToEx(fault);
				ZaApp.getInstance().getCurrentController()._handleException(ex,"ZaDomain.modifyGalSettings", null, false);
                hasError = true ;
                lastException = ex ;
            }
		} else {
/*			var batchResp = respObj.Body.BatchResponse;
			var resp = batchResp.ModifyDomainResponse[0];	
			this.initFromJS(resp.domain[0]);*/
			this.refresh(false,true);
			ZaDomain.putDomainToCache(this);			
		}
	} catch (ex) {
		//show the error and go on
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaDomain.modifyGalSettings", null, false);
	    hasError = true ;
        lastException = ex ;
	}			
}	

ZaDomain.modifyAuthSettings = 
function(tmpObj) {

	var soapDoc = AjxSoapDoc.create("ModifyDomainRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("id", this.id);
	
	var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthMech]);
	attr.setAttribute("n", ZaDomain.A_AuthMech);	
	
	if(tmpObj.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_ad) {
		var temp = tmpObj.attrs[ZaDomain.A_AuthLdapURL].join(" ");
		attr = soapDoc.set("a", temp);
		attr.setAttribute("n", ZaDomain.A_AuthLdapURL);	

		attr = soapDoc.set("a", "%u@"+tmpObj.attrs[ZaDomain.A_AuthADDomainName]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapUserDn);

		if(tmpObj[ZaDomain.A_AuthUseBindPassword] && tmpObj[ZaDomain.A_AuthUseBindPassword] == "TRUE") {
			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthLdapSearchBindDn]);
			attr.setAttribute("n", ZaDomain.A_AuthLdapSearchBindDn);

			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthLdapSearchBindPassword]);
			attr.setAttribute("n", ZaDomain.A_AuthLdapSearchBindPassword);
		}
	} else if (tmpObj.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_ldap) {

		if(tmpObj.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled]) {
			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled]);
			attr.setAttribute("n", ZaDomain.A_zmailAuthLdapStartTlsEnabled);	
		}		
		
		var temp = tmpObj.attrs[ZaDomain.A_AuthLdapURL].join(" ");
		
		if(tmpObj.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled] == "TRUE") {
			//check that we don't have ldaps://
			if(temp.indexOf("ldaps://") > -1) {
				ZaApp.getInstance().getCurrentController().popupWarningDialog(ZaMsg.Domain_WarningStartTLSIgnored)
			}		
		}		
		attr = soapDoc.set("a", temp);
		attr.setAttribute("n", ZaDomain.A_AuthLdapURL);	

		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthLdapSearchFilter]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapSearchFilter);
			
		attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthLdapSearchBase]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapSearchBase);	
		
		if(tmpObj[ZaDomain.A_AuthUseBindPassword] && tmpObj[ZaDomain.A_AuthUseBindPassword] == "TRUE") {
			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthLdapSearchBindDn]);
			attr.setAttribute("n", ZaDomain.A_AuthLdapSearchBindDn);	
			
			attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthLdapSearchBindPassword]);
			attr.setAttribute("n", ZaDomain.A_AuthLdapSearchBindPassword);			
		}
		/*attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_AuthLdapUserDn]);
		attr.setAttribute("n", ZaDomain.A_AuthLdapUserDn);	
		*/

	}

    if (tmpObj.attrs[ZaDomain.A_AuthMech] != ZaDomain.AuthMech_zmail &&
        tmpObj[ZaDomain.A2_zmailExternalGroupLdapEnabled] == "TRUE") {
        if (tmpObj.attrs[ZaDomain.A_zmailExternalGroupLdapSearchBase] &&
            tmpObj.attrs[ZaDomain.A_zmailExternalGroupLdapSearchFilter] &&
            tmpObj.attrs[ZaDomain.A_zmailExternalGroupHandlerClass]) {
            attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailExternalGroupLdapSearchBase]);
            attr.setAttribute("n", ZaDomain.A_zmailExternalGroupLdapSearchBase);

            attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailExternalGroupLdapSearchFilter]);
            attr.setAttribute("n", ZaDomain.A_zmailExternalGroupLdapSearchFilter);

            attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailExternalGroupHandlerClass]);
            attr.setAttribute("n", ZaDomain.A_zmailExternalGroupHandlerClass);

            attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAuthMechAdmin]);
            attr.setAttribute("n", ZaDomain.A_zmailAuthMechAdmin);
        }
    }
    
    ZaDomain.modifySSOSettings(soapDoc, tmpObj); // temporarily put here, will be invoked by the new SSO Wizard in the future
    
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_MODIFY_DOMAIN
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.ModifyDomainResponse;	
	this.initFromJS(resp.domain[0]);
	ZaDomain.putDomainToCache(this);
}

ZaDomain.modifySSOSettings = function(soapDoc, tmpObj) {
    // SPNEGO configuration
    
    // virtual hosts
    if(tmpObj.attrs[ZaDomain.A_zmailVirtualHostname]) {
        if(tmpObj.attrs[ZaDomain.A_zmailVirtualHostname] instanceof Array) {
            var cnt = tmpObj.attrs[ZaDomain.A_zmailVirtualHostname].length;
            for(var ix=0; ix<cnt; ix++) {
                var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailVirtualHostname][ix]);
                attr.setAttribute("n", ZaDomain.A_zmailVirtualHostname);
            }
        } else {
            var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailVirtualHostname]);
            attr.setAttribute("n", ZaDomain.A_zmailVirtualHostname);
        }
    }
    // web client login/logout URL
    if(tmpObj[ZaDomain.A2_zmailSpnegoUAAllBrowsers] == "TRUE"
            || tmpObj[ZaDomain.A2_zmailSpnegoUASupportedBrowsers] == "TRUE") {
        tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA] = ZaDomain.SPNEGO_SUPPORT_UA;
        tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA] = ZaDomain.SPNEGO_SUPPORT_UA;
    }

    if(tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA]) {
        if(tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA] instanceof Array) {
            var cnt = tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA].length;
            for(var ix=0; ix<cnt; ix++) {
                var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA][ix]);
                attr.setAttribute("n", ZaDomain.A_zmailWebClientLoginURLAllowedUA);
            }
        } else {
            var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA]);
            attr.setAttribute("n", ZaDomain.A_zmailWebClientLoginURLAllowedUA);
        }
    }

    if(tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA]) {
        if(tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA] instanceof Array) {
            var cnt = tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA].length;
            for(var ix=0; ix<cnt; ix++) {
                var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA][ix]);
                attr.setAttribute("n", ZaDomain.A_zmailWebClientLogoutURLAllowedUA);
            }
        } else {
            var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA]);
            attr.setAttribute("n", ZaDomain.A_zmailWebClientLogoutURLAllowedUA);
        }
    }
    
    if(tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP]) {
        if(tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP] instanceof Array) {
            var cnt = tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP].length;
            for(var ix=0; ix<cnt; ix++) {
                var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP][ix]);
                attr.setAttribute("n", ZaDomain.A_zmailWebClientLoginURLAllowedIP);
            }
        } else {
            var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP]);
            attr.setAttribute("n", ZaDomain.A_zmailWebClientLoginURLAllowedIP);
        }
    }

    if(tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP]) {
        if(tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP] instanceof Array) {
            var cnt = tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP].length;
            for(var ix=0; ix<cnt; ix++) {
                var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP][ix]);
                attr.setAttribute("n", ZaDomain.A_zmailWebClientLogoutURLAllowedIP);
            }
        } else {
            var attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP]);
            attr.setAttribute("n", ZaDomain.A_zmailWebClientLogoutURLAllowedIP);
        }
    }
}

ZaDomain.modifyAutoPovSettings = function(tmpObj) {
	var soapDoc = AjxSoapDoc.create("BatchRequest", "urn:zmail");
	soapDoc.setMethodAttribute("onerror", "stop");

    // modify domain
	var modifyDomainDoc = soapDoc.set("ModifyDomainRequest", null, null, ZaZmailAdmin.URN);
	soapDoc.set("id", this.id,modifyDomainDoc);
    var attr;

    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvMode]) {
        if(tmpObj.attrs[ZaDomain.A_zmailAutoProvMode] instanceof Array) {
            var cnt =  tmpObj.attrs[ZaDomain.A_zmailAutoProvMode].length;
            for(var i = 0; i < cnt; i ++) {
				attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvMode][i],modifyDomainDoc);
				attr.setAttribute("n", ZaDomain.A_zmailAutoProvMode);
            }
            if(cnt == 0) {
				attr = soapDoc.set("a", "",modifyDomainDoc);
				attr.setAttribute("n", ZaDomain.A_zmailAutoProvMode);
            }
        } else {
				attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvMode],modifyDomainDoc);
				attr.setAttribute("n", ZaDomain.A_zmailAutoProvMode);
        }
    }

    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvAuthMech]) {
        if(tmpObj.attrs[ZaDomain.A_zmailAutoProvAuthMech] instanceof Array
                && tmpObj.attrs[ZaDomain.A_zmailAutoProvAuthMech].length > 0) {
            var cnt =  tmpObj.attrs[ZaDomain.A_zmailAutoProvAuthMech].length;
            for(var i = 0; i < cnt; i ++) {
				attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvAuthMech][i],modifyDomainDoc);
				attr.setAttribute("n", ZaDomain.A_zmailAutoProvAuthMech);
            }
        } else {
				attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvAuthMech],modifyDomainDoc);
				attr.setAttribute("n", ZaDomain.A_zmailAutoProvAuthMech);
        }
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvAttrMap]) {
        if((tmpObj.attrs[ZaDomain.A_zmailAutoProvAttrMap] instanceof Array)
                && tmpObj.attrs[ZaDomain.A_zmailAutoProvAttrMap].length > 0) {
            var cnt =  tmpObj.attrs[ZaDomain.A_zmailAutoProvAttrMap].length;
            for(var i = 0; i < cnt; i ++) {
				attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvAttrMap][i],modifyDomainDoc);
				attr.setAttribute("n", ZaDomain.A_zmailAutoProvAttrMap);
            }
        } else {
				attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvAttrMap],modifyDomainDoc);
				attr.setAttribute("n", ZaDomain.A_zmailAutoProvAttrMap);
        }
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapURL]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapURL],modifyDomainDoc);
        attr.setAttribute("n", ZaDomain.A_zmailAutoProvLdapURL);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapStartTlsEnabled]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapStartTlsEnabled],modifyDomainDoc);
        attr.setAttribute("n", ZaDomain.A_zmailAutoProvLdapStartTlsEnabled);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindDn]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindDn],modifyDomainDoc);
        attr.setAttribute("n", ZaDomain.A_zmailAutoProvLdapAdminBindDn);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindPassword]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindPassword],modifyDomainDoc);
        attr.setAttribute("n", ZaDomain.A_zmailAutoProvLdapAdminBindPassword);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapSearchBase]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapSearchBase],modifyDomainDoc);
        attr.setAttribute("n", ZaDomain.A_zmailAutoProvLdapSearchBase);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapSearchFilter]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapSearchFilter],modifyDomainDoc);
        attr.setAttribute("n", ZaDomain.A_zmailAutoProvLdapSearchFilter);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapBindDn]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvLdapBindDn],modifyDomainDoc);
        attr.setAttribute("n", ZaDomain.A_zmailAutoProvLdapBindDn);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvAccountNameMap]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvAccountNameMap],modifyDomainDoc);
        attr.setAttribute("n", ZaDomain.A_zmailAutoProvAccountNameMap);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvNotificationFromAddress]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvNotificationFromAddress],modifyDomainDoc);
        attr.setAttribute("n", ZaDomain.A_zmailAutoProvNotificationFromAddress);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvBatchSize]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvBatchSize],modifyDomainDoc);
        attr.setAttribute("n", ZaDomain.A_zmailAutoProvBatchSize);
    }
    if(tmpObj.attrs[ZaDomain.A_zmailAutoProvLastPolledTimestamp]){
        attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvLastPolledTimestamp],modifyDomainDoc);
        attr.setAttribute("n", ZaDomain.A_zmailAutoProvLastPolledTimestamp);
    }

    attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvNotificationSubject],modifyDomainDoc);
    attr.setAttribute("n", ZaDomain.A_zmailAutoProvNotificationSubject);
    attr = soapDoc.set("a", tmpObj.attrs[ZaDomain.A_zmailAutoProvNotificationBody],modifyDomainDoc);
    attr.setAttribute("n", ZaDomain.A_zmailAutoProvNotificationBody);

    // scheduled domain list in server config
    if(tmpObj[ZaDomain.A2_zmailAutoProvSelectedServerList]
            && tmpObj[ZaDomain.A2_zmailAutoProvSelectedServerList].getArray().length > 0) {
        var modifyServerDoc = null;
        var selectedList = tmpObj[ZaDomain.A2_zmailAutoProvSelectedServerList].getArray();
        var serverList = tmpObj[ZaDomain.A2_zmailAutoProvServerList];
        var cnt = selectedList.length;
        for (var i = 0; i < cnt; i++) {
            var server = null;
            for(var j = 0; j < serverList.length; j++) {
                if(selectedList[i] == serverList[j].name)
                    server = serverList[j];
            }
            if(server) {
                var scheduledDomains = server.attrs[ZaServer.A_zmailAutoProvScheduledDomains];
                modifyServerDoc = soapDoc.set("ModifyServerRequest", null, null, ZaZmailAdmin.URN);
                soapDoc.set("id", server.id,modifyServerDoc);
                var isExist = false;
                for(var i = 0; scheduledDomains && i < scheduledDomains.length; i++) {
                    attr = soapDoc.set("a", scheduledDomains[i],modifyServerDoc);
                    attr.setAttribute("n", ZaServer.A_zmailAutoProvScheduledDomains);
                    if(scheduledDomains[i] == this.name) isExist = true;
                }
                if(!isExist) {
                    attr = soapDoc.set("a", this.name,modifyServerDoc);
                    attr.setAttribute("n", ZaServer.A_zmailAutoProvScheduledDomains);
                }
                if(tmpObj[ZaDomain.A2_zmailAutoProvPollingInterval]) {
                    attr = soapDoc.set("a",tmpObj[ZaDomain.A2_zmailAutoProvPollingInterval],modifyServerDoc);
                    attr.setAttribute("n", ZaServer.A_zmailAutoProvPollingInterval);
                }
            }
        }

    }
	try {
		params = new Object();
		params.soapDoc = soapDoc;
		var reqMgrParams ={
			controller:ZaApp.getInstance().getCurrentController(),
			busyMsg : ZaMsg.BUSY_MODIFY_DOMAIN,
			showBusy:true
		}
		var respObj = ZaRequestMgr.invoke(params, reqMgrParams);
		if(respObj.isException && respObj.isException()) {
			ZaApp.getInstance().getCurrentController()._handleException(respObj.getException(), "ZaDomain.modifyAutoPovSettings", null, false);
		    hasError  = true ;
            lastException = ex ;
        } else if(respObj.Body.BatchResponse.Fault) {
			var fault = respObj.Body.BatchResponse.Fault;
			if(fault instanceof Array)
				fault = fault[0];

			if (fault) {
				// JS response with fault
				var ex = ZmCsfeCommand.faultToEx(fault);
				ZaApp.getInstance().getCurrentController()._handleException(ex,"ZaDomain.modifyGalSettings", null, false);
                hasError = true ;
                lastException = ex ;
            }
		} else {
			var batchResp = respObj.Body.BatchResponse;
			var resp = batchResp.ModifyDomainResponse[0];
			this.initFromJS(resp.domain[0]);
		}
	} catch (ex) {
		//show the error and go on
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaDomain.modifyAutoPovSettings", null, false);
	    hasError = true ;
        lastException = ex ;
	}

	this.refresh(false,true);
	ZaDomain.putDomainToCache(this);
}

ZaDomain.prototype.setStatus = function (newStatus) {
	var soapDoc = AjxSoapDoc.create("ModifyDomainStatusRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("id", this.id);

	var params = new Object();
	params.soapDoc = soapDoc;
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_MODIFY_DOMAIN_STATUS
	}	
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.ModifyDomainStatusResponse;	
	this.attrs[ZaDomain.A_zmailDomainStatus] = resp.domain.status;
}

/**
* @param mods - map of modified attributes that will be sent to the server
* modifies object's information in the database
**/
ZaDomain.modifyMethod =
function(tmods,tmpObj) {
	var soapDoc = AjxSoapDoc.create("BatchRequest", "urn:zmail");
	soapDoc.setMethodAttribute("onerror", "stop");

	// method  plugin
	var mods = tmods;
	if(ZaItem.modifyMethodsExt["ZaDomain"]) {
                var methods = ZaItem.modifyMethodsExt["ZaDomain"];
                var cnt = methods.length;
                for(var i = 0; i < cnt; i++) {
                        if(typeof(methods[i]) == "function")
                               methods[i].call(this, mods, tmpObj, soapDoc);
                }

	}	 
	var modifyDomainDoc = soapDoc.set("ModifyDomainRequest", null, null, ZaZmailAdmin.URN);
	soapDoc.set("id", this.id,modifyDomainDoc);
	
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
						var attr = soapDoc.set("a", mods[aname][ix].toString(),modifyDomainDoc);
					} else if(mods[aname][ix] instanceof Object) {
						var attr = soapDoc.set("a", mods[aname][ix].toString(),modifyDomainDoc);
						nonemptyElements = true;
					} else {
						var attr = soapDoc.set("a", mods[aname][ix],modifyDomainDoc);
						nonemptyElements = true;
					}
					
					if(attr)
						attr.setAttribute("n", aname);
				}
				if(!nonemptyElements) {
					var attr = soapDoc.set("a", "",modifyDomainDoc);
					attr.setAttribute("n", aname);
				}
			} else {
				var attr = soapDoc.set("a", "", modifyDomainDoc);
				attr.setAttribute("n", aname);
			}
		} else {
			var attr = soapDoc.set("a", mods[aname], modifyDomainDoc);
			attr.setAttribute("n", aname);
		}
	}
    
	if(tmpObj[ZaDomain.A2_gal_sync_accounts] && !AjxUtil.isEmpty(tmpObj[ZaDomain.A2_gal_sync_accounts])) {
        for (var i in tmpObj[ZaDomain.A2_gal_sync_accounts]) {
            var currentGalAccount = tmpObj[ZaDomain.A2_gal_sync_accounts][i]
            if (currentGalAccount) {
                if(currentGalAccount[ZaAccount.A2_zmail_ds]
                    && currentGalAccount[ZaAccount.A2_zmail_ds].attrs
                    && this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_zmail_ds]
                    && this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_zmail_ds].attrs) {
                    if(this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_zmail_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval] !=
                        currentGalAccount[ZaAccount.A2_zmail_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval]) {
                        var modifyDSDoc = soapDoc.set("ModifyDataSourceRequest", null, null, ZaZmailAdmin.URN);
                        soapDoc.set("id", this[ZaDomain.A2_gal_sync_accounts][i].id, modifyDSDoc);
                        var ds = soapDoc.set("dataSource", null,modifyDSDoc);
                        ds.setAttribute("id", this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_zmail_ds].id);
                        var attr = soapDoc.set("a", currentGalAccount[ZaAccount.A2_zmail_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval],ds);
                        attr.setAttribute("n", ZaDataSource.A_zmailDataSourcePollingInterval);
                    }
                }

                if(currentGalAccount[ZaAccount.A2_ldap_ds]
                    && currentGalAccount[ZaAccount.A2_ldap_ds].attrs
                    && this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_ldap_ds]
                    && this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_ldap_ds].attrs) {
                    if(this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_ldap_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval] !=
                        currentGalAccount[ZaAccount.A2_ldap_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval]) {
                        var modifyDSDoc = soapDoc.set("ModifyDataSourceRequest", null, null, ZaZmailAdmin.URN);
                        soapDoc.set("id", this[ZaDomain.A2_gal_sync_accounts][i].id, modifyDSDoc);
                        var ds = soapDoc.set("dataSource", null,modifyDSDoc);
                        ds.setAttribute("id", this[ZaDomain.A2_gal_sync_accounts][i][ZaAccount.A2_ldap_ds].id);
                        var attr = soapDoc.set("a", currentGalAccount[ZaAccount.A2_ldap_ds].attrs[ZaDataSource.A_zmailDataSourcePollingInterval],ds);
                        attr.setAttribute("n", ZaDataSource.A_zmailDataSourcePollingInterval);
                    }
                }
            }
        }
	}
	
	try {
		params = new Object();
		params.soapDoc = soapDoc;	
		var reqMgrParams ={
			controller:ZaApp.getInstance().getCurrentController(),
			busyMsg : ZaMsg.BUSY_MODIFY_DOMAIN,
			showBusy:true
		}
		var respObj = ZaRequestMgr.invoke(params, reqMgrParams);
		if(respObj.isException && respObj.isException()) {
			ZaApp.getInstance().getCurrentController()._handleException(respObj.getException(), "ZaDomain.modifyGalSettings", null, false);
		    hasError  = true ;
            lastException = ex ;
        } else if(respObj.Body.BatchResponse.Fault) {
			var fault = respObj.Body.BatchResponse.Fault;
			if(fault instanceof Array)
				fault = fault[0];
			
			if (fault) {
				// JS response with fault
				var ex = ZmCsfeCommand.faultToEx(fault);
				ZaApp.getInstance().getCurrentController()._handleException(ex,"ZaDomain.modifyGalSettings", null, false);
                hasError = true ;
                lastException = ex ;
            }
		} else {
			var batchResp = respObj.Body.BatchResponse;
			var resp = batchResp.ModifyDomainResponse[0];	
			this.initFromJS(resp.domain[0]);
		}
	} catch (ex) {
		//show the error and go on
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaDomain.modifyGalSettings", null, false);
	    hasError = true ;
        lastException = ex ;
	}		    

	this.refresh(false,true);
	ZaDomain.putDomainToCache(this);
}
ZaItem.modifyMethods["ZaDomain"].push(ZaDomain.modifyMethod);

ZaDomain.getAccountQuota = function ( name, offset, limit, sortBy, sortAscending, callback){
	var soapDoc = AjxSoapDoc.create("GetQuotaUsageRequest", ZaZmailAdmin.URN, null);

	if (sortBy == null || sortBy == ZaAccountQuota.A2_diskUsage) {
		sortBy = "totalUsed" ;
	}else if (sortBy == ZaAccountQuota.A2_quotaUsage){
		sortBy = "percentUsed" ;
	}else if (sortBy == ZaAccountQuota.A2_quota){
		sortBy = "quotaLimit";
	}else if (sortBy == ZaAccountQuota.A2_name){
		sortBy = "account";
	} else {
		sortBy = "totalUsed";
	}
	soapDoc.getMethod().setAttribute("sortBy", sortBy );
	if (sortAscending) {
		soapDoc.getMethod().setAttribute("sortAscending", sortAscending);
	}

    limit = limit ? limit : ZaSettings.RESULTSPERPAGE;
	soapDoc.getMethod().setAttribute("offset", offset);
	soapDoc.getMethod().setAttribute("limit", ZaServerMBXStatsPage.MBX_DISPLAY_LIMIT);
	soapDoc.getMethod().setAttribute("refresh", "1");
    if (name) {
        soapDoc.getMethod().setAttribute("domain", name);
        soapDoc.getMethod().setAttribute("allServers", "1");
    }
	//use refresh="1" to force server side re-calculating quota and ignore cached data.

	var params = new Object ();
	params.soapDoc = soapDoc ;
    var isAsyncMode = callback? true: false;
    if (isAsyncMode) {
        params.asyncMode = true;
        params.callback = callback;
    }
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_GET_QUOTA
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams);
    if (isAsyncMode) {
        return resp;
    } else {
        resp = resp.Body.GetQuotaUsageResponse;
    }

    var result = { hasMore: false, mbxes: new Array() };
	if ((resp.account && resp.account.length > 0) && (resp.searchTotal && resp.searchTotal > 0)){
		result.hasMore = resp.more ;

		var accounts = resp.account ;
		var accountArr = new Array ();

		for (var i=0; i<accounts.length; i ++){
            accountArr[i] = new ZaAccountQuota(accounts[i]);
		}

		result.mbxes = accountArr ;
	}

	return result;
};

ZaDomain.prototype.initFromJS = 
function (obj) {
    ZaItem.prototype.initFromJS.call(this, obj);

    if (!(this.attrs[ZaDomain.A_zmailDomainCOSMaxAccounts] instanceof Array)) {
        if (this.attrs[ZaDomain.A_zmailDomainCOSMaxAccounts]) {
            this.attrs[ZaDomain.A_zmailDomainCOSMaxAccounts] = [this.attrs[ZaDomain.A_zmailDomainCOSMaxAccounts]];
        } else {
            this.attrs[ZaDomain.A_zmailDomainCOSMaxAccounts] = [];
        }
    }

    if (!(this.attrs[ZaDomain.A_zmailDomainFeatureMaxAccounts] instanceof Array)) {
        if (this.attrs[ZaDomain.A_zmailDomainFeatureMaxAccounts]) {
            this.attrs[ZaDomain.A_zmailDomainFeatureMaxAccounts] = [this.attrs[ZaDomain.A_zmailDomainFeatureMaxAccounts]];
        } else {
            this.attrs[ZaDomain.A_zmailDomainFeatureMaxAccounts] = [];
        }
    }


    if(!(this.attrs[ZaDomain.A_zmailVirtualHostname] instanceof Array)) {
		if(this.attrs[ZaDomain.A_zmailVirtualHostname])
			this.attrs[ZaDomain.A_zmailVirtualHostname] = [this.attrs[ZaDomain.A_zmailVirtualHostname]];	
		else
			this.attrs[ZaDomain.A_zmailVirtualHostname] = new Array();
	}
    	if(!(this.attrs[ZaDomain.A_zmailMailAddressValidationRegex] instanceof Array)) {
                if(this.attrs[ZaDomain.A_zmailMailAddressValidationRegex])
                        this.attrs[ZaDomain.A_zmailMailAddressValidationRegex] = [this.attrs[ZaDomain.A_zmailMailAddressValidationRegex]];
                else
                        this.attrs[ZaDomain.A_zmailMailAddressValidationRegex] = new Array();
        }

	if(AjxUtil.isString(this.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA])) {
		this.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA] = [this.attrs[ZaGlobalConfig.A_zmailWebClientLoginURLAllowedUA]];
	}

    if(AjxUtil.isString(this.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA])) {
		this.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA] = [this.attrs[ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedUA]];
	}
    
    if(AjxUtil.isString(this.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP])) {
        this.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedIP] = [this.attrs[ZaGlobalConfig.A_zmailWebClientLoginURLAllowedIP]];
    }

    if(AjxUtil.isString(this.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP])) {
        this.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedIP] = [this.attrs[ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedIP]];
    }

	if(!this.attrs[ZaDomain.A_AuthMech]) {
		this.attrs[ZaDomain.A_AuthMech] = ZaDomain.AuthMech_zmail; //default value
	}
	if(!this.attrs[ZaDomain.A_zmailGalMode]) {
		this.attrs[ZaDomain.A_zmailGalMode] = ZaDomain.GAL_Mode_internal; //default value
	}

	if(this.attrs[ZaDomain.A_AuthLdapURL]) {
		// Split Auth URL into an array 
		var temp = this.attrs[ZaDomain.A_AuthLdapURL];
		this.attrs[ZaDomain.A_AuthLdapURL] = temp.split(" ");

	} else this.attrs[ZaDomain.A_AuthLdapURL] = new Array();
	if(this.attrs[ZaDomain.A_GalLdapURL])	{	
		var temp = this.attrs[ZaDomain.A_GalLdapURL];
		this.attrs[ZaDomain.A_GalLdapURL] = temp.split(" ");		
	} else this.attrs[ZaDomain.A_GalLdapURL] = new Array();
	
	if(this.attrs[ZaDomain.A_zmailGalMode]) {
		if(this.attrs[ZaDomain.A_zmailGalMode] == "ldap" || this.attrs[ZaDomain.A_zmailGalMode] == "both") {
			if(this.attrs[ZaDomain.A_GalLdapFilter] == "ad") {
				this.attrs[ZaDomain.A_GALServerType] = "ad";
			} else {
				this.attrs[ZaDomain.A_GALServerType] = "ldap";
			}
			if(!this.attrs[ZaDomain.A_zmailGalSyncLdapURL]) {
				this.attrs[ZaDomain.A_GALSyncUseGALSearch]="TRUE";
			} else {
				this.attrs[ZaDomain.A_GALSyncUseGALSearch]="FALSE";
				if(this.attrs[ZaDomain.A_zmailGalSyncLdapBindDn] || this.attrs[ZaDomain.A_zmailGalSyncLdapBindPassword]) {
					this.attrs[ZaDomain.A_SyncUseBindPassword] = "TRUE";
				} else {
					this.attrs[ZaDomain.A_SyncUseBindPassword] = "FALSE";
				}
				if(this.attrs[ZaDomain.A_zmailGalSyncLdapFilter] == "ad") {
					this.attrs[ZaDomain.A_GALSyncServerType] = "ad";
				} else {
					this.attrs[ZaDomain.A_GALSyncServerType] = "ldap";
				}
			}
		} else {
			this.attrs[ZaDomain.A_GALSyncUseGALSearch]="TRUE";
		}
	} else {
		this.attrs[ZaDomain.A_zmailGalMode] = "zmail";
		this.attrs[ZaDomain.A_GALSyncUseGALSearch]="TRUE";
	}
	
	if(this.attrs[ZaDomain.A_GalLdapBindDn] || this.attrs[ZaDomain.A_GalLdapBindPassword]) {
		this.attrs[ZaDomain.A_UseBindPassword] = "TRUE";
	} else {
		this.attrs[ZaDomain.A_UseBindPassword] = "FALSE";
	}
	
	//
	//if(this.attrs[ZaDomain.A_AuthADDomainName]);
	if(!AjxUtil.isEmpty(this.attrs[ZaDomain.A_AuthLdapUserDn])) {
		this.attrs[ZaDomain.A_AuthADDomainName] = ZaAccount.getDomain(this.attrs[ZaDomain.A_AuthLdapUserDn]);	
	}
		
	if(this.attrs[ZaDomain.A_AuthLdapSearchBindDn] || this.attrs[ZaDomain.A_AuthLdapSearchBindPassword]) {
		this[ZaDomain.A_AuthUseBindPassword] = "TRUE";
	} else {
		this[ZaDomain.A_AuthUseBindPassword] = "FALSE";
	}
		
	this[ZaDomain.A_GALSampleQuery] = "john";
	if(!this.attrs[ZaDomain.A_zmailGalAutoCompleteLdapFilter])
		this.attrs[ZaDomain.A_zmailGalAutoCompleteLdapFilter] = "(|(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*))";
}

ZaDomain.prototype.parseNotebookFolderAcls = function (resp) {
	try {
		if(resp.isException && resp.isException()) {
			throw(resp.getException());
		}
		
		var response;
		if(resp.getResponse)
			response = resp.getResponse().Body.GetFolderResponse;
		else
			response = resp.Body.GetFolderResponse;
			
		if(response && response.folder && response.folder[0] && response.folder[0].acl
			&& response.folder[0].acl.grant) {
			var grants = response.folder[0].acl.grant;
			var cnt = grants.length;
			this[ZaDomain.A_allNotebookACLS] = [];
			for (var gi = 0; gi < cnt; gi++) {
				var grant = grants[gi];
				var grantObj = {
					r:grant.perm.indexOf("r")>=0 ? 1 : 0,
					w:grant.perm.indexOf("w")>=0 ? 1 : 0,
					i:grant.perm.indexOf("i")>=0 ? 1 : 0,
					d:grant.perm.indexOf("d")>=0 ? 1 : 0,
					a:grant.perm.indexOf("a")>=0 ? 1 : 0,
					x:grant.perm.indexOf("x")>=0 ? 1 : 0,
					toString:function () {
						return this.r+this.w+this.i+this.d+this.a+this.x;
					}
				};
				this[ZaDomain.A_allNotebookACLS].push({acl:grantObj, name:grant.d, zid:grant.zid, gt:grant.gt, 
						toString:function() {
							return (this.gt+":"+this.name+":"+this.grantObj.toString());
						}
					});
			}
			this[ZaDomain.A_allNotebookACLS]._version = 0;
			
		}
	} catch (ex) {
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaDomain.prototype.parseNotebookFolderAcls", null, false);	
	}
}
/**
* Returns HTML for a tool tip for this domain.
*/
ZaDomain.prototype.getToolTip =
function() {
	// update/null if modified
	if (!this._toolTip) {
		var html = new Array(20);
		var idx = 0;
		html[idx++] = "<table cellpadding='0' cellspacing='0' border='0'>";
		html[idx++] = "<tr valign='center'><td colspan='2' align='left'>";
		html[idx++] = "<div style='border-bottom: 1px solid black; white-space:nowrap; overflow:hidden;width:350'>";
		html[idx++] = "<table cellpadding='0' cellspacing='0' border='0' style='width:100%;'>";
		html[idx++] = "<tr valign='center'>";
		html[idx++] = "<td><b>" + AjxStringUtil.htmlEncode(this.name) + "</b></td>";
		html[idx++] = "<td align='right'>";
		html[idx++] = AjxImg.getImageHtml("Domain");			
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

ZaDomain.prototype.remove = 
function(callback) {
	var soapDoc = AjxSoapDoc.create("DeleteDomainRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("id", this.id);
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;
	if(callback) {
		params.asyncMode = true;
		params.callback = callback;
	}
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_DELETE_DOMAIN
	}
	ZaRequestMgr.invoke(params, reqMgrParams);	
}

ZaDomain.getLoginMessage = function () {
    var domain = ZaDomain.getDomainByName (ZaSettings.myDomainName, true) ;
    return domain.attrs[ZaDomain.A_zmailAdminConsoleLoginMessage]  ;
}

ZaDomain.getDomainByName =
function(domName) {
	if(!domName)
		return null;
	domName = domName.toLowerCase();
	var domain = ZaDomain.staticDomainByNameCacheTable[domName];
	if(!domain) {
		domain = new ZaDomain();
		try {
			domain.load("name", domName, false, true);
		} catch (ex) {
            throw (ex);
        }

		ZaDomain.putDomainToCache(domain);
	} 
	return domain;	
} 

ZaDomain.getDomainById = 
function (domId) {
	if(!domId)
		return null;
		
	var domain = ZaDomain.staticDomainByIdCacheTable[domId];
	if(!domain) {
		domain = new ZaDomain();
		try {
			domain.load("id", domId, false, true);
		} catch (ex) {
			if(ex.code == ZmCsfeException.NO_SUCH_DOMAIN) {
				return null;
			} else {
				throw (ex);
			}
		}
		ZaDomain.putDomainToCache(domain);
	}
	return domain;
}

ZaDomain.loadMethod = 
function(by, val) {
	by = by ? by : "name";
	val = val ? val : this.attrs[ZaDomain.A_domainName];
	this.notebookAcls[ZaDomain.A_NotebookAllACLs] = {r:0,w:0,i:0,d:0,a:0,x:0};
	this.notebookAcls[ZaDomain.A_NotebookPublicACLs] = {r:0,w:0,i:0,d:0,a:0,x:0};
	this.notebookAcls[ZaDomain.A_NotebookDomainACLs] = {r:1,w:1,i:1,d:1,a:0,x:0};
	this.notebookAcls[ZaDomain.A_NotebookUserACLs] = [/*{name:"", acl:{r:0,w:0,i:0,d:0,a:0,x:0}, 
			toString:function () {
				return [this.name,this.acl[r],this.acl[w],this.acl[i],this.acl[d],this.acl[a],this.acl[x]].join();
			}}*/];
	this.notebookAcls[ZaDomain.A_NotebookGroupACLs] = [/*{name:"", acl:{r:0,w:0,i:0,d:0,a:0,x:0}, 
			toString:function () {
				return [this.name,this.acl[r],this.acl[w],this.acl[i],this.acl[d],this.acl[a],this.acl[x]].join();
			}
		}*/];

	var soapDoc = AjxSoapDoc.create("GetDomainRequest", ZaZmailAdmin.URN, null);
	soapDoc.getMethod().setAttribute("applyConfig", "false");
	var elBy = soapDoc.set("domain", val);
	elBy.setAttribute("by", by);
	if(!this.getAttrs.all && !AjxUtil.isEmpty(this.attrsToGet)) {
		soapDoc.setMethodAttribute("attrs", this.attrsToGet.join(","));
	}
	
	var params = new Object();
	params.soapDoc = soapDoc;	
	
	var reqMgrParams = {
		controller : (ZaApp.getInstance() ? ZaApp.getInstance().getCurrentController() : null),
		busyMsg : ZaMsg.BUSY_GET_DOMAIN
	}
	var respObj = ZaRequestMgr.invoke(params, reqMgrParams);
	if(respObj.isException && respObj.isException()) {
		if(ZaApp.getInstance() && ZaApp.getInstance().getCurrentController()) {
			ZaApp.getInstance().getCurrentController()._handleException(respObj.getException(), "ZaDomain.loadMethod", null, false);
		}
    } else {
		var resp = respObj.Body.GetDomainResponse;
		this.initFromJS(resp.domain[0]);

    }
}
ZaItem.loadMethods["ZaDomain"].push(ZaDomain.loadMethod);

ZaDomain.loadDataSources = function (by, val) {
	if(this.attrs[ZaDomain.A_zmailGalAccountId]) {
		if(!(this.attrs[ZaDomain.A_zmailGalAccountId] instanceof Array)) {
			this.attrs[ZaDomain.A_zmailGalAccountId] = [this.attrs[ZaDomain.A_zmailGalAccountId]];
		}
		this[ZaDomain.A2_gal_sync_accounts] = [];
		
		for(var i=0; i< this.attrs[ZaDomain.A_zmailGalAccountId].length; i++) {
			try {
				var galSyncAccount = new ZaAccount();
				galSyncAccount.load("id", this.attrs[ZaDomain.A_zmailGalAccountId][i], false, false);
				this[ZaDomain.A2_gal_sync_accounts].push(galSyncAccount);
			} catch (ex) {
				if (ex.code == ZmCsfeException.ACCT_NO_SUCH_ACCOUNT) {
					if(ZaApp.getInstance() && ZaApp.getInstance().getCurrentController())
						ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_GALSYNC_ACCOUNT_INVALID,[this.name,this.attrs[ZaDomain.A_zmailGalAccountId][i]]), ex, true);
				} else {
					if(ZaApp.getInstance() && ZaApp.getInstance().getCurrentController())
						ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaDomain.loadDataSources", null, false);
				}	
			}
		}
	}
}
ZaItem.loadMethods["ZaDomain"].push(ZaDomain.loadDataSources);

ZaDomain.loadNotebookACLs = function(by, val) {
    if(this.attrs[ZaDomain.A_zmailDomainStatus] != ZaDomain.DOMAIN_STATUS_MAINTENANCE && this.attrs[ZaDomain.A_zmailDomainStatus] != ZaDomain.DOMAIN_STATUS_SUSPENDED) {                                                                                    
	    if(this.attrs[ZaDomain.A_zmailNotebookAccount]) {
			var soapDoc = AjxSoapDoc.create("GetFolderRequest", "urn:zmailMail", null);
			var getFolderCommand = new ZmCsfeCommand();
			var params = new Object();
			params.soapDoc = soapDoc;
			params.noAuthToken = true;
			params.accountName = this.attrs[ZaDomain.A_zmailNotebookAccount];
		
			var folderEl = soapDoc.set("folder", "");
			folderEl.setAttribute("l", ZaDomain.WIKI_FOLDER_ID);	
			try {
				this.parseNotebookFolderAcls(getFolderCommand.invoke(params));
			} catch (ex) {
				ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaDomain.loadMethod", null, false);
			}
		}
    }
}
ZaItem.loadMethods["ZaDomain"].push(ZaDomain.loadNotebookACLs);

ZaDomain.loadCatchAll = function () {
	if((this.attrs[ZaDomain.A_zmailAdminConsoleCatchAllAddressEnabled] && this.attrs[ZaDomain.A_zmailAdminConsoleCatchAllAddressEnabled] == "TRUE")
		|| (AjxUtil.isEmpty(this.attrs[ZaDomain.A_zmailAdminConsoleCatchAllAddressEnabled]) && 
			(!AjxUtil.isEmpty(this._defaultValues.attrs[ZaDomain.A_zmailAdminConsoleCatchAllAddressEnabled]) &&
				this._defaultValues.attrs[ZaDomain.A_zmailAdminConsoleCatchAllAddressEnabled] == "TRUE") )) {
		
		var acc = ZaAccount.getCatchAllAccount (this.name);
		if(!AjxUtil.isEmpty(acc) && !AjxUtil.isEmpty(acc.id) && !AjxUtil.isEmpty(acc.name)) {
			this [ZaAccount.A_zmailMailCatchAllAddress] = acc;
		} else {
			this [ZaAccount.A_zmailMailCatchAllAddress] = null;
		}
	} else {
		this [ZaAccount.A_zmailMailCatchAllAddress] = null;
	}
}
ZaItem.loadMethods["ZaDomain"].push(ZaDomain.loadCatchAll);

ZaDomain.checkDomainMXRecord = 
function(obj, callback) {
	var soapDoc = AjxSoapDoc.create("CheckDomainMXRecordRequest", ZaZmailAdmin.URN, null);
	var elBy = soapDoc.set("domain", obj.id);
	elBy.setAttribute("by", "id");
	var params = new Object();
	params.soapDoc = soapDoc;	
	if(callback) {
		params.asyncMode = true;
		params.callback = callback;
	}
	var reqMgrParams = {
		controller : (ZaApp.getInstance() ? ZaApp.getInstance().getCurrentController() : null),
		busyMsg : ZaMsg.BUSY_CHECKING_MX
	}
	ZaRequestMgr.invoke(params, reqMgrParams);
}


ZaDomain.aclXModel = {
	items: [
		{id:"acl",
			type:_OBJECT_,
			items: [
				{id:"r", type:_NUMBER_},
				{id:"w", type:_NUMBER_},
				{id:"d", type:_NUMBER_},
				{id:"i", type:_NUMBER_},
				{id:"a", type:_NUMBER_},				
				{id:"x", type:_NUMBER_}
		]},
		{id:"name", type:_STRING_},
		{id:"gt",  type:_STRING_}	
	]
}

ZaDomain.myXModel = {
	items: [
    	{id:"getAttrs",type:_LIST_},
    	{id:"setAttrs",type:_LIST_},
    	{id:"rights",type:_LIST_},	
		{id:"name", type:_STRING_, ref:"name"},
		{id:ZaItem.A_zmailId, type:_STRING_, ref:"attrs/" + ZaItem.A_zmailId},
        {id:ZaItem.A_zmailDomainAliasTargetId, type:_STRING_, ref:"attrs/" + ZaItem.A_zmailDomainAliasTargetId},                
		{id:ZaItem.A_zmailCreateTimestamp, ref:"attrs/" + ZaItem.A_zmailCreateTimestamp},
		{id:ZaDomain.A_domainName, type:_STRING_, ref:"attrs/" + ZaDomain.A_domainName, maxLength:255,constraints: {type:"method", value:
                   function (value) {
                         value = value.replace(/(^\s*)/g, "");
                                                 return value;
                                           }

                           } },
		{id:ZaDomain.A_zmailPublicServiceHostname, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailPublicServiceHostname, maxLength:255},
		{id:ZaDomain.A_zmailPublicServiceProtocol, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailPublicServiceProtocol, maxLength:255, defaultValue:"http"},
		{id:ZaDomain.A_zmailPublicServicePort, type:_NUMBER_, ref:"attrs/" + ZaDomain.A_zmailPublicServicePort, minInclusive: 0, maxInclusive:65535, defaultValue:80},
		{id:ZaDomain.A_zmailDNSCheckHostname, type:_COS_STRING_, ref:"attrs/" + ZaDomain.A_zmailDNSCheckHostname, maxLength:255},
		{id:ZaDomain.A_zmailBasicAuthRealm, type:_COS_STRING_, ref:"attrs/" + ZaDomain.A_zmailBasicAuthRealm, maxLength:255},		
		{id:ZaDomain.A_zmailAdminConsoleDNSCheckEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDomain.A_zmailAdminConsoleDNSCheckEnabled},
        {id:ZaDomain.A_zmailAdminConsoleCatchAllAddressEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDomain.A_zmailAdminConsoleCatchAllAddressEnabled},
        {id:ZaDomain.A_zmailAdminConsoleLDAPAuthEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDomain.A_zmailAdminConsoleLDAPAuthEnabled},    
        {id:ZaDomain.A_zmailAdminConsoleSkinEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDomain.A_zmailAdminSkinAddressEnabled},
	    // regex
	    {id:ZaDomain.A_zmailMailAddressValidationRegex, type:_LIST_, listItem:{type:_STRING_, maxLength:512}, ref:"attrs/" + ZaDomain.A_zmailMailAddressValidationRegex},
        {id:ZaDomain.A_zmailVirtualHostname, type:_LIST_, listItem:{type:_STRING_, maxLength:255}, ref:"attrs/" + ZaDomain.A_zmailVirtualHostname},
         ZaItem.descriptionModelItem,  
	    {id:ZaDomain.A_zmailSSLCertificate, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailSSLCertificate},
	    {id:ZaDomain.A_zmailSSLPrivateKey, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailSSLPrivateKey},
        {id:ZaDomain.A_notes, type:_STRING_, ref:"attrs/" + ZaDomain.A_notes},
		{id:ZaDomain.A_domainDefaultCOSId, type:_STRING_, ref:"attrs/" + ZaDomain.A_domainDefaultCOSId},		
		{id:ZaDomain.A_zmailGalMode, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailGalMode},
        {id:ZaDomain.A_mailHost, type:_STRING_, ref:"attrs/" + ZaDomain.A_mailHost},
		{id:ZaDomain.A_zmailGalMaxResults, type:_NUMBER_, ref:"attrs/" + ZaDomain.A_zmailGalMaxResults, maxInclusive:2147483647, minInclusive:1},					
		{id:ZaDomain.A_GALServerType, type:_STRING_, ref:"attrs/" + ZaDomain.A_GALServerType},
		{id:ZaDomain.A_GALSyncServerType, type:_STRING_, ref:"attrs/" + ZaDomain.A_GALSyncServerType},
		{id:ZaDomain.A_GALSyncUseGALSearch, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDomain.A_GALSyncUseGALSearch},
		{id:ZaDomain.A_zmailGalSyncLdapBindDn, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailGalSyncLdapBindDn},
        {id:ZaDomain.A_zmailGalSyncLdapBindPassword, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailGalSyncLdapBindPassword},
		{id:ZaDomain.A_zmailGalSyncLdapSearchBase, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailGalSyncLdapSearchBase},
		{id:ZaDomain.A_zmailGalSyncLdapFilter, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailGalSyncLdapFilter,required:true},
		{id:ZaDomain.A_GalLdapFilter, type:_STRING_, ref:"attrs/" + ZaDomain.A_GalLdapFilter,required:true},
		{id:ZaDomain.A_zmailGalAutoCompleteLdapFilter, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailGalAutoCompleteLdapFilter},		
		{id:ZaDomain.A_GalLdapSearchBase, type:_STRING_, ref:"attrs/" + ZaDomain.A_GalLdapSearchBase},
		{id:ZaDomain.A_UseBindPassword, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDomain.A_UseBindPassword},
		{id:ZaDomain.A_SyncUseBindPassword, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDomain.A_SyncUseBindPassword},
		{id:ZaDomain.A_GalLdapURL, type:_LIST_,  listItem:{type:_SHORT_URL_}, ref:"attrs/" + ZaDomain.A_GalLdapURL},
		{id:ZaDomain.A_zmailGalSyncLdapURL, type:_LIST_,  listItem:{type:_SHORT_URL_}, ref:"attrs/" + ZaDomain.A_zmailGalSyncLdapURL},
		{id:ZaDomain.A_GalLdapBindDn, type:_STRING_, ref:"attrs/" + ZaDomain.A_GalLdapBindDn},
		{id:ZaDomain.A_GalLdapBindPassword, type:_STRING_, ref:"attrs/" + ZaDomain.A_GalLdapBindPassword},
		{id:ZaDomain.A_GalLdapBindPasswordConfirm, type:_STRING_, ref:"attrs/" + ZaDomain.A_GalLdapBindPasswordConfirm},
		//GAL Account
        {id:ZaDomain.A_zmailGalAccountId, type:_LIST_, listItem:{type:_STRING_}, ref:"attrs/" + ZaDomain.A_zmailGalAccountId},
        {id:ZaDomain.A2_create_gal_acc, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:ZaDomain.A2_create_gal_acc},
        {id:ZaDomain.A2_gal_sync_accounts_set, type:_LIST_, ref:ZaDomain.A2_gal_sync_accounts_set, listItem:{
            type:_OBJECT_, ref:".", items:[
                {id:ZaDomain.A2_new_gal_sync_account_name, type:_STRING_, ref:ZaDomain.A2_new_gal_sync_account_name, defaultValue: "galsync"},
                {id:ZaDomain.A2_new_internal_gal_ds_name, type:_STRING_, ref:ZaDomain.A2_new_internal_gal_ds_name, defaultValue: "zmail"},
                {id:ZaDomain.A2_new_external_gal_ds_name, type:_STRING_, ref:ZaDomain.A2_new_external_gal_ds_name, defaultValue: "ldap"},
                {id:ZaDomain.A2_new_internal_gal_polling_interval, type:_MLIFETIME_, ref:ZaDomain.A2_new_internal_gal_polling_interval, defaultValue: "1d"},
                {id:ZaDomain.A2_new_external_gal_polling_interval, type:_MLIFETIME_, ref:ZaDomain.A2_new_external_gal_polling_interval, defaultValue: "1d"}
            ]
        }},
		{id:ZaDomain.A2_gal_sync_accounts, type:_LIST_, listItem:{type:_OBJECT_, items:ZaAccount.myXModel.items}, ref:ZaDomain.A2_gal_sync_accounts},
		{id:ZaDomain.A_AuthLdapUserDn, type:_STRING_,ref:"attrs/" + ZaDomain.A_AuthLdapUserDn},
		{id:ZaDomain.A_zmailAuthLdapStartTlsEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDomain.A_zmailAuthLdapStartTlsEnabled},
		//{id:ZaDomain.A_AuthLDAPServerName, type:_STRING_, ref:"attrs/" + ZaDomain.A_AuthLDAPServerName},
		{id:ZaDomain.A_AuthLDAPSearchBase, type:_STRING_, ref:"attrs/" + ZaDomain.A_AuthLDAPSearchBase},
		//{id:ZaDomain.A_AuthLDAPServerPort, type:_NUMBER_, ref:"attrs/" + ZaDomain.A_AuthLDAPServerPort, maxInclusive:2147483647},
		{id:ZaDomain.A_AuthMech, type:_STRING_, ref:"attrs/" + ZaDomain.A_AuthMech},
		{id:ZaDomain.A_AuthLdapURL, type:_LIST_,  listItem:{type:_SHORT_URL_}, ref:"attrs/" + ZaDomain.A_AuthLdapURL},
		{id:ZaDomain.A_AuthADDomainName, type:_STRING_, ref:"attrs/" + ZaDomain.A_AuthADDomainName},
		{id:ZaDomain.A_AuthLdapSearchBase, type:_STRING_, ref:"attrs/" + ZaDomain.A_AuthLdapSearchBase},		
		{id:ZaDomain.A_AuthLdapSearchFilter, type:_STRING_, ref:"attrs/" + ZaDomain.A_AuthLdapSearchFilter},		
		{id:ZaDomain.A_AuthLdapSearchBindDn, type:_STRING_, ref:"attrs/" + ZaDomain.A_AuthLdapSearchBindDn},		
		{id:ZaDomain.A_AuthLdapSearchBindPassword, type:_STRING_, ref:"attrs/" + ZaDomain.A_AuthLdapSearchBindPassword},		
		{id:ZaDomain.A_zmailDomainStatus, type:_STRING_, ref:"attrs/"+ZaDomain.A_zmailDomainStatus},
		{id:ZaDomain.A_AuthTestUserName, type:_STRING_},
		{id:ZaDomain.A_AuthTestPassword, type:_STRING_},
		{id:ZaDomain.A_AuthTestMessage, type:_STRING_},
		{id:ZaDomain.A_AuthTestResultCode, type:_STRING_},
		{id:ZaDomain.A_AuthTestMessage, type:_STRING_},
		{id:ZaDomain.A_AuthComputedBindDn, type:_STRING_},
		{id:ZaDomain.A_zmailPasswordChangeListener, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailPasswordChangeListener},
		{id:ZaDomain.A_zmailAuthFallbackToLocal, type:_ENUM_, ref:"attrs/" + ZaDomain.A_zmailAuthFallbackToLocal, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaDomain.A_GALSearchTestMessage, type:_STRING_},
		{id:ZaDomain.A_GALSyncTestMessage, type:_STRING_},
		{id:ZaDomain.A_GALSearchTestResultCode, type:_STRING_},
		{id:ZaDomain.A_GALSyncTestResultCode, type:_STRING_},		
		{id:ZaDomain.A_GALSampleQuery, type:_STRING_,required:true},
		{id:ZaDomain.A_AuthUseBindPassword, type:_STRING_,type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},		
		{id:ZaDomain.A_AuthLdapSearchBindPasswordConfirm, type:_STRING_},
        // provision
		{id:ZaDomain.A_zmailAutoProvLdapURL, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailAutoProvLdapURL, maxLength:256},
        {id:ZaDomain.A_zmailAutoProvLdapStartTlsEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDomain.A_zmailAutoProvLdapStartTlsEnabled},
        {id:ZaDomain.A_zmailAutoProvLdapAdminBindDn, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailAutoProvLdapAdminBindDn, maxLength:256},
        {id:ZaDomain.A_zmailAutoProvLdapAdminBindPassword, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailAutoProvLdapAdminBindPassword, maxLength:256},
        {id:ZaDomain.A_zmailAutoProvLdapSearchBase, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailAutoProvLdapSearchBase, maxLength:256},
        {id:ZaDomain.A_zmailAutoProvLdapSearchFilter, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailAutoProvLdapSearchFilter, maxLength:256},
        {id:ZaDomain.A_zmailAutoProvLdapBindDn, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailAutoProvLdapBindDn, maxLength:256},
        {id:ZaDomain.A_zmailAutoProvAccountNameMap, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailAutoProvAccountNameMap, maxLength:256},
        {id:ZaDomain.A_zmailAutoProvNotificationFromAddress, type:_EMAIL_ADDRESS_, ref:"attrs/" + ZaDomain.A_zmailAutoProvNotificationFromAddress, maxLength:256},
        {id:ZaDomain.A_zmailAutoProvBatchSize, type:_NUMBER_, ref:"attrs/" + ZaDomain.A_zmailAutoProvBatchSize, maxInclusive:2147483647, minInclusive:0},
        {id:ZaDomain.A_zmailAutoProvLastPolledTimestamp, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailAutoProvLastPolledTimestamp, maxLength:256},
        {id:ZaDomain.A_zmailAutoProvAttrMap, type:_LIST_, listItem:{type:_STRING_, maxLength:255}, ref:"attrs/" + ZaDomain.A_zmailAutoProvAttrMap},
		{id:ZaDomain.A2_zmailAutoProvModeEAGEREnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:ZaDomain.A2_zmailAutoProvModeEAGEREnabled},
		{id:ZaDomain.A2_zmailAutoProvModeLAZYEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:ZaDomain.A2_zmailAutoProvModeLAZYEnabled},
		{id:ZaDomain.A2_zmailAutoProvModeMANUALEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:ZaDomain.A2_zmailAutoProvModeMANUALEnabled},
		{id:ZaDomain.A2_zmailAutoProvAuthMechLDAPEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:ZaDomain.A2_zmailAutoProvAuthMechLDAPEnabled},
		{id:ZaDomain.A2_zmailAutoProvAuthMechPREAUTHEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:ZaDomain.A2_zmailAutoProvAuthMechPREAUTHEnabled},
		{id:ZaDomain.A2_zmailAutoProvAuthMechKRB5Enabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:ZaDomain.A2_zmailAutoProvAuthMechKRB5Enabled},
		{id:ZaDomain.A2_zmailAutoProvAuthMechSPNEGOEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:ZaDomain.A2_zmailAutoProvAuthMechSPNEGOEnabled},
        {id:ZaDomain.A2_zmailAutoProvServerList, type:_LIST_, ref:ZaDomain.A2_zmailAutoProvServerList},
        {id:ZaDomain.A2_zmailAutoProvSelectedServerList, type:_LIST_},
        {id:ZaDomain.A2_zmailAutoProvPollingInterval, ref: ZaDomain.A2_zmailAutoProvPollingInterval, type: _MLIFETIME_, minInclusive: 0 },
        {id:ZaDomain.A2_zmailAutoProvSearchActivated, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:ZaDomain.A2_zmailAutoProvSearchActivated},
        {id:ZaDomain.A2_zmailAutoProvAccountPool,type:_LIST_,ref:ZaDomain.A2_zmailAutoProvAccountPool},
        {id:ZaDomain.A2_zmailAutoProvAccountPoolPageNum,ref:ZaDomain.A2_zmailAutoProvAccountPoolPageNum, type:_NUMBER_,defaultValue:1},
        {id:ZaDomain.A2_zmailAutoProvAccountSrcSelectedPool,type:_LIST_,ref:ZaDomain.A2_zmailAutoProvAccountSrcSelectedPool},
        {id:ZaDomain.A2_zmailAutoProvAccountTargetPool,type:_LIST_,ref:ZaDomain.A2_zmailAutoProvAccountTargetPool},
        {id:ZaDomain.A2_zmailAutoProvAccountTgtSelectedPool,type:_LIST_,ref:ZaDomain.A2_zmailAutoProvAccountTgtSelectedPool},
        {id:ZaDomain.A2_zmailAutoProvAccountPoolPageTotal,ref:ZaDomain.A2_zmailAutoProvAccountPoolPageTotal, type:_NUMBER_,defaultValue:1},
        {id:ZaDomain.A2_zmailAutoProvAccountPassword	, type:_STRING_, ref:ZaDomain.A2_zmailAutoProvAccountPassword, maxLength:256},
        // Domain Quota
    	{id:ZaDomain.A_domainMaxAccounts, type:_INT_, ref:"attrs/" + ZaDomain.A_domainMaxAccounts,minInclusive:0},
        {id:ZaDomain.A_zmailMailDomainQuota, type:_NUMBER_, ref: "attrs/"+ZaDomain.A_zmailMailDomainQuota, minInclusive:0},
        {id:ZaDomain.A_zmailDomainAggregateQuota, type:_NUMBER_, ref: "attrs/"+ZaDomain.A_zmailDomainAggregateQuota, minInclusive:0},
        {id:ZaDomain.A_zmailDomainAggregateQuotaWarnPercent, type:_NUMBER_, ref:"attrs/"+ZaDomain.A_zmailDomainAggregateQuotaWarnPercent, maxInclusive:100, minInclusive:0},
        {id:ZaDomain.A_zmailDomainAggregateQuotaWarnEmailRecipient, type:_EMAIL_ADDRESS_, ref:"attrs/"+ZaDomain.A_zmailDomainAggregateQuotaWarnEmailRecipient},
        {id:ZaDomain.A_zmailDomainAggregateQuotaPolicy, type:_ENUM_, ref:"attrs/"+ZaDomain.A_zmailDomainAggregateQuotaPolicy, choices:ZaDomain.aggregateQuotaPolicyChoices},
		{id:ZaDomain.A_zmailPrefTimeZoneId,type:_STRING_, ref:"attrs/"+ZaDomain.A_zmailPrefTimeZoneId, choices:ZaSettings.timeZoneChoices},
        {id:ZaModel.currentStep, type:_NUMBER_, ref:ZaModel.currentStep, maxInclusive:2147483647},
		{id:ZaDomain.A2_acl_selection_cache, type:_LIST_},
		{id:ZaDomain.A_GALTestSearchResults, ref:ZaDomain.A_GALTestSearchResults, type:_LIST_, 
			listItem: {type:_OBJECT_, 
				items:[
					{id:"email", type:_STRING_},
					{id:"fullName", type:_STRING_},					
					{id:"firstName", type:_STRING_},										
					{id:"lastName", type:_STRING_}														
				]
			}
		},		
		{id:ZaDomain.A_NotebookTemplateDir, type:_STRING_, ref:ZaDomain.A_NotebookTemplateDir},
		{id:ZaDomain.A_NotebookTemplateFolder, type:_STRING_, ref:ZaDomain.A_NotebookTemplateFolder},
		{id:ZaDomain.A_NotebookAccountPassword, type:_STRING_},
		{id:ZaDomain.A_NotebookAccountPassword2, type:_STRING_},		
		{id:ZaDomain.A_CreateNotebook, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaDomain.A_OverwriteTemplates, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
		{id:ZaDomain.A_zmailNotebookAccount, type:_STRING_, ref:"attrs/" +ZaDomain.A_zmailNotebookAccount},
		{id:ZaDomain.A_NotebookAllACLs, ref:"notebookAcls/"+ZaDomain.A_NotebookAllACLs, type:_OBJECT_,
			items: [
				{id:"r", type:_NUMBER_},
				{id:"w", type:_NUMBER_},
				{id:"d", type:_NUMBER_},
				{id:"i", type:_NUMBER_},
				{id:"a", type:_NUMBER_},				
				{id:"x", type:_NUMBER_}
			]
		},
		{id:ZaDomain.A_NotebookDomainACLs, ref:"notebookAcls/"+ZaDomain.A_NotebookDomainACLs, type:_OBJECT_,
			items: [
				{id:"r", type:_NUMBER_},
				{id:"w", type:_NUMBER_},
				{id:"d", type:_NUMBER_},
				{id:"i", type:_NUMBER_},
				{id:"a", type:_NUMBER_},				
				{id:"x", type:_NUMBER_}
			]
		},
		{id:ZaDomain.A_NotebookPublicACLs, ref:"notebookAcls/"+ZaDomain.A_NotebookPublicACLs, type:_OBJECT_,
			items: [
				{id:"r", type:_NUMBER_},
				{id:"w", type:_NUMBER_},
				{id:"d", type:_NUMBER_},
				{id:"i", type:_NUMBER_},
				{id:"a", type:_NUMBER_},				
				{id:"x", type:_NUMBER_}
			]
		}, 
		{id:ZaDomain.A_NotebookGroupACLs, ref:"notebookAcls/"+ZaDomain.A_NotebookGroupACLs, type:_LIST_,
			listItem:{type:_OBJECT_,
				items: [
					{id:"acl", /*type:_LIST_, 
						listItem:{*/
							type:_OBJECT_,
							items: [
								{id:"r", type:_NUMBER_},
								{id:"w", type:_NUMBER_},
								{id:"d", type:_NUMBER_},
								{id:"i", type:_NUMBER_},
								{id:"a", type:_NUMBER_},				
								{id:"x", type:_NUMBER_}
							]
//						}
					},
					{id:"name", type:_STRING_},
					{id:"gt",  type:_STRING_}
				]
			}
		}, 
		{id:ZaDomain.A_NotebookUserACLs, ref:"notebookAcls/"+ZaDomain.A_NotebookUserACLs, type:_LIST_,
			listItem:{type:_OBJECT_,
				items: [
					{id:"acl",/* type:_LIST_, 
						listItem:{*/
							type:_OBJECT_,
							items: [
								{id:"r", type:_NUMBER_},
								{id:"w", type:_NUMBER_},
								{id:"d", type:_NUMBER_},
								{id:"i", type:_NUMBER_},
								{id:"a", type:_NUMBER_},				
								{id:"x", type:_NUMBER_}
							]
						//}
					},
					{id:"name", type:_STRING_},
					{id:"gt",  type:_STRING_}
				]
			}
		},
		{id:ZaDomain.A_allNotebookACLS, ref:ZaDomain.A_allNotebookACLS, type:_LIST_,
			listItem:{type:_OBJECT_,
				items: [
					{id:"acl", type:_OBJECT_,
						items: [
							{id:"r", type:_NUMBER_},
							{id:"w", type:_NUMBER_},
							{id:"d", type:_NUMBER_},
							{id:"i", type:_NUMBER_},
							{id:"a", type:_NUMBER_},				
							{id:"x", type:_NUMBER_}
						]
					},
					{id:"name", type:_STRING_}, //null, domain name, group name, user name
					{id:"gt",  type:_STRING_} //grp, usr, dom, pub, all, guest
				]
			}
		},
        {id:ZaDomain.A_zmailZimletDomainAvailableZimlets, type:_LIST_,
            ref:"attrs/" + ZaDomain.A_zmailZimletDomainAvailableZimlets,
            dataType: _STRING_ ,outputType:_LIST_},
      { id:ZaAccount.A_zmailMailCatchAllAddress, ref:ZaAccount.A_zmailMailCatchAllAddress , type:_OBJECT_,items:[ {id:"id", type:_STRING_},{id:"name", type:_STRING_}] },
      { id:ZaDomain.A_zmailDomainCOSMaxAccounts, ref:"attrs/" + ZaDomain.A_zmailDomainCOSMaxAccounts ,
                 type:_LIST_ , dataType: _STRING_ ,outputType:_LIST_ },
      { id:ZaDomain.A_zmailDomainFeatureMaxAccounts, ref:"attrs/" + ZaDomain.A_zmailDomainFeatureMaxAccounts ,
                        type:_LIST_ , dataType: _STRING_ ,outputType:_LIST_ },
      // domain account quota
      { id:ZaDomain.A2_domain_account_quota, ref: ZaDomain.A2_domain_account_quota, type:_LIST_, listItem: {type:_OBJECT_} },
       //skin properties
      { id:ZaDomain.A_zmailSkinForegroundColor, ref:"attrs/" + ZaDomain.A_zmailSkinForegroundColor, type: _COS_STRING_ },
      { id:ZaDomain.A_zmailSkinBackgroundColor, ref:"attrs/" + ZaDomain.A_zmailSkinBackgroundColor, type: _COS_STRING_ },
      { id:ZaDomain.A_zmailSkinSecondaryColor, ref:"attrs/" + ZaDomain.A_zmailSkinSecondaryColor, type: _COS_STRING_ },
      { id:ZaDomain.A_zmailSkinSelectionColor, ref:"attrs/" + ZaDomain.A_zmailSkinSelectionColor, type: _COS_STRING_ },

      { id:ZaDomain.A_zmailSkinLogoURL, ref:"attrs/" + ZaDomain.A_zmailSkinLogoURL, type:_COS_STRING_ },
      { id:ZaDomain.A_zmailSkinLogoLoginBanner, ref:"attrs/" + ZaDomain.A_zmailSkinLogoLoginBanner, type:_COS_STRING_ },
      { id:ZaDomain.A_zmailSkinLogoAppBanner, ref:"attrs/" + ZaDomain.A_zmailSkinLogoAppBanner, type:_COS_STRING_ },
      // web client redirect
      { id:ZaDomain.A_zmailWebClientLoginURL, ref:"attrs/" + ZaDomain.A_zmailWebClientLoginURL, type:_COS_STRING_ },
      { id:ZaDomain.A_zmailWebClientLogoutURL, ref:"attrs/" + ZaDomain.A_zmailWebClientLogoutURL, type:_COS_STRING_ },
        // Clear Cookies
        {
            id: ZaDomain.A_zmailForceClearCookies,
            ref: "attrs/" + ZaDomain.A_zmailForceClearCookies,
            type: _ENUM_,
            choices: ZaModel.BOOLEAN_CHOICES
        },
    // web client authentication
      { id:ZaDomain.A_zmailReverseProxyClientCertMode, ref:"attrs/" + ZaDomain.A_zmailReverseProxyClientCertMode, type:_COS_STRING_, choices:["on","off","optional"]},
      { id:ZaDomain.A_zmailMailSSLClientCertPrincipalMap, ref:"attrs/" + ZaDomain.A_zmailMailSSLClientCertPrincipalMap, type:_COS_STRING_ },
      { id:ZaDomain.A_zmailReverseProxyClientCertCA, ref:"attrs/" + ZaDomain.A_zmailReverseProxyClientCertCA, type:_COS_STRING_ },
	// help URL
      { id:ZaDomain.A_zmailHelpAdminURL, ref:"attrs/" + ZaDomain.A_zmailHelpAdminURL, type:_COS_STRING_ },
      { id:ZaDomain.A_zmailHelpDelegatedURL, ref:"attrs/" + ZaDomain.A_zmailHelpDelegatedURL, type:_COS_STRING_ },
	// login/out URL
      { id:ZaDomain.A_zmailAdminConsoleLoginURL, ref:"attrs/" + ZaDomain.A_zmailAdminConsoleLoginURL, type:_COS_STRING_ },
      { id:ZaDomain.A_zmailAdminConsoleLogoutURL, ref:"attrs/" + ZaDomain.A_zmailAdminConsoleLogoutURL, type:_COS_STRING_ },
      { id:ZaDomain.A_zmailWebClientLoginURLAllowedUA, type:_COS_LIST_, ref:"attrs/"+ZaDomain.A_zmailWebClientLoginURLAllowedUA, listItem:{ type: _STRING_}},
      { id:ZaDomain.A_zmailWebClientLogoutURLAllowedUA, type:_COS_LIST_, ref:"attrs/"+ZaDomain.A_zmailWebClientLogoutURLAllowedUA, listItem:{ type: _STRING_}},
      { id:ZaDomain.A_zmailWebClientLoginURLAllowedIP, type:_COS_LIST_, ref:"attrs/"+ZaDomain.A_zmailWebClientLoginURLAllowedIP, listItem:{ type: _STRING_}},
      { id:ZaDomain.A_zmailWebClientLogoutURLAllowedIP, type:_COS_LIST_, ref:"attrs/"+ZaDomain.A_zmailWebClientLogoutURLAllowedIP, listItem:{ type: _STRING_}},
    //kerberos
      { id:ZaDomain.A_zmailAuthKerberos5Realm, type:_STRING_, ref:"attrs/"+ZaDomain.A_zmailAuthKerberos5Realm},
        //interop
       { id:ZaDomain.A_zmailFreebusyExchangeAuthUsername, ref:"attrs/" + ZaDomain.A_zmailFreebusyExchangeAuthUsername, type: _COS_STRING_ },
       { id:ZaDomain.A_zmailFreebusyExchangeAuthPassword, ref:"attrs/" + ZaDomain.A_zmailFreebusyExchangeAuthPassword, type: _COS_STRING_ },
       { id:ZaDomain.A_zmailFreebusyExchangeAuthScheme, ref:"attrs/" + ZaDomain.A_zmailFreebusyExchangeAuthScheme,
             type: _COS_ENUM_ , choices: ZaSettings.authorizationScheme },
       { id:ZaDomain.A_zmailFreebusyExchangeServerType, ref:"attrs/" + ZaDomain.A_zmailFreebusyExchangeServerType,
             type: _COS_ENUM_ , choices: ZaSettings.exchangeServerType },
       { id:ZaDomain.A_zmailFreebusyExchangeURL, ref:"attrs/" + ZaDomain.A_zmailFreebusyExchangeURL, type: _COS_STRING_ } ,
       { id:ZaDomain.A_zmailFreebusyExchangeUserOrg, ref:"attrs/" + ZaDomain.A_zmailFreebusyExchangeUserOrg, type: _COS_STRING_ },
       {id:ZaDomain.A2_isTestingGAL, ref:ZaDomain.A2_isTestingGAL, type:_NUMBER_},
       {id:ZaDomain.A2_isTestingSync, ref:ZaDomain.A2_isTestingSync, type:_NUMBER_},
       {id:ZaDomain.A2_isTestingAuth, ref:ZaDomain.A2_isTestingAuth, type:_NUMBER_},
       {id:ZaDomain.A_zmailFeatureCalendarReminderDeviceEmailEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDomain.A_zmailFeatureCalendarReminderDeviceEmailEnabled},

       {id:ZaDomain.A_zmailAutoProvNotificationSubject, type:_COS_STRING_, ref:"attrs/" + ZaDomain.A_zmailAutoProvNotificationSubject},
       {id:ZaDomain.A_zmailAutoProvNotificationBody, type:_COS_STRING_, ref:"attrs/" + ZaDomain.A_zmailAutoProvNotificationBody}
    ]
};

/**
 * Domain Level Account Limits Object, it is a client side only domain property and used
 * by both domain view and account view.  The value is based on the zmailDomainCOSMaxAccounts
 *  zmailDomainCOSMaxAccounts is a multi value attribute, its value is in format of "cosName:account_limits",
 * eg.
 *  zmailDomainCOSMaxAccounts = professional:10
 *
 *  //this value is built on demand
 *
 *  domain.account_limits =
 *   {
 *      cosName : { max: 10,
 *                  used: 2 , //used value is got by the searchDirectory on this domain
 *                  available: 8
 *                  }
 *   }
 *
 **/

ZaDomain.prototype.getUsedDomainAccounts = function () {
    var total = 0 ;    
    var accountCountsByCoses = this.getAccountCountsByCoses();
    if (accountCountsByCoses && accountCountsByCoses.length > 0) {
      for (var i = 0 ; i < accountCountsByCoses.length; i ++) {
        var count = accountCountsByCoses[i]._content ;
        total += parseInt(count) ;
      }
    }
    return total ;
}

ZaDomain.prototype.getAccountCountsByCoses = function () {
    try {
        var soapDoc = AjxSoapDoc.create("CountAccountRequest", ZaZmailAdmin.URN, null);
        var elBy = soapDoc.set("domain", this.name);
        elBy.setAttribute("by", "name");
        //var command = new ZmCsfeCommand();
        var params = new Object();
        params.soapDoc = soapDoc;

        var reqMgrParams = {
            controller : ZaApp.getInstance().getCurrentController(),
            busyMsg : ZaMsg.BUSY_COUNT_ACCOUNTS
        }
        var resp = ZaRequestMgr.invoke(params, reqMgrParams);
        var accountCountsByCoses = resp.Body.CountAccountResponse.cos ;
        return accountCountsByCoses ;
    }catch (ex) {
        ZaApp.getInstance().getCurrentController().popupErrorDialog(
                AjxMessageFormat.format(ZaMsg.ERROR_GET_USED_ACCOUNTS, [this.name]), ex);
    }
}

ZaDomain.prototype.updateUsedAccounts = function () {
    this.updateMaxAccounts() ;  //make sure all the defined cos in cosMaxAccounts are initialized

    //need to call the CountAccountRequest
    var accountCountsByCoses = this.getAccountCountsByCoses();
    if (accountCountsByCoses && accountCountsByCoses.length > 0) {
        for (var i = 0 ; i < accountCountsByCoses.length; i ++) {
            var aCosName =  accountCountsByCoses[i].name ;
            if (!this[ZaDomain.A2_account_limit][aCosName])
                this[ZaDomain.A2_account_limit][aCosName] = {} ;
            this[ZaDomain.A2_account_limit][aCosName].used =
                                            parseInt (accountCountsByCoses[i]._content) ;
        }
    }

    for (aCosName in this[ZaDomain.A2_account_limit]) {
        if (!this[ZaDomain.A2_account_limit][aCosName].used)
            this[ZaDomain.A2_account_limit][aCosName].used = 0;
    }
}

ZaDomain.prototype.getUsedAccounts =
function (cosName, refresh) {
    if (!this[ZaDomain.A2_account_limit]) this[ZaDomain.A2_account_limit] = {};
    if (!this[ZaDomain.A2_account_limit][cosName])  this[ZaDomain.A2_account_limit][cosName] = {used:null} ;

    if (refresh || (this[ZaDomain.A2_account_limit][cosName].used == null)) {
        this.updateUsedAccounts();   
    }

    if(!this[ZaDomain.A2_account_limit][cosName])
	return 0;
    else 
    	return this[ZaDomain.A2_account_limit][cosName].used ;
}

ZaDomain.prototype.isCosLimitInDomain =
function(cosName) {
    if(!this[ZaDomain.A2_account_limit] || this[ZaDomain.A2_account_limit].length < 1) {
	this.updateMaxAccounts();	
    }
    if(this[ZaDomain.A2_account_limit][cosName]) return true;
    else return false;
}

ZaDomain.prototype.getMaxAccounts = function (cosName, refresh) {
    if (! this [ZaDomain.A2_account_limit] )  this[ZaDomain.A2_account_limit] = {} ;

    //var cosName = ZaCos.getCosById (cosId).name ;
    if (!this[ZaDomain.A2_account_limit][cosName]) this[ZaDomain.A2_account_limit][cosName] = {} ;

    if (! this[ZaDomain.A2_account_limit][cosName].max || refresh) {
        //retrieve the total allowed accounts
       this.updateMaxAccounts ();
    }

    if(!this[ZaDomain.A2_account_limit][cosName])
	return 0;
    else
    return  this[ZaDomain.A2_account_limit][cosName].max ;
}

//init or refresh the cos max accounts
ZaDomain.prototype.updateMaxAccounts = function () {
    this[ZaDomain.A2_account_limit] = {} ;
    
    var cosMaxAccounts = this.attrs[ZaDomain.A_zmailDomainCOSMaxAccounts];
    for (var i=0; i < cosMaxAccounts.length; i ++) {
        var val = cosMaxAccounts[i].split(":") ;
        var cos = ZaCos.getCosById (val[0]) ;
        if (cos == null) {
                ZaApp.getInstance().getCurrentController.popupErrorDialog(
                    AjxMessageFormat.format(ZaMsg.ERROR_INVALID_ACCOUNT_TYPE, [val[0]]));
            return ;
        }
        var n = cos.name ;

        this[ZaDomain.A2_account_limit][n] = {} ;
        this[ZaDomain.A2_account_limit][n].max = val [1] ;
    }
}

ZaDomain.prototype.getAvailableAccounts = function (cosName, refresh) {
    //var cosName = ZaCos.getCosById (cosId).name ;
    if (!this[ZaDomain.A2_account_limit][cosName]) this[ZaDomain.A2_account_limit][cosName] = {} ;
//    if (! this [ZaDomain.A2_account_limit][cosName].available
//           || refresh ) {
        //retrieve the used accounts
        var used = this.getUsedAccounts (cosName, refresh);
        var max = this.getMaxAccounts (cosName, refresh) ;
        //this [ZaDomain.A2_account_limit][cosName].available = max - used ;
//    }
    if(!this [ZaDomain.A2_account_limit][cosName])
	return 0;
    else {
	this[ZaDomain.A2_account_limit][cosName].available = max - used ;
    	return this[ZaDomain.A2_account_limit][cosName].available;
    }
}

//Account types is only available when ZmailDomainCOSMaxAccounts are set
ZaDomain.prototype.getAccountTypes = function () {
    var types = [] ;
    var cosMaxAccounts = this.attrs[ZaDomain.A_zmailDomainCOSMaxAccounts];
    if (cosMaxAccounts && cosMaxAccounts.length > 0 ) {
        for (var i=0; i < cosMaxAccounts.length; i ++) {
            var val = cosMaxAccounts[i].split(":") ;
            types.push (val[0]) ;
        }
    }

    return types ;
}

ZaDomain.getTotalLimitsPerAccountTypes = function (cosMaxAccounts) {
    var total = 0 ;
    //var cosMaxAccounts = this.attrs[ZaDomain.A_zmailDomainCOSMaxAccounts];
    if (cosMaxAccounts && cosMaxAccounts.length > 0 ) {
        for (var i=0; i < cosMaxAccounts.length; i ++) {
            var val = cosMaxAccounts[i].split(":") ;
            total += new Number (val [1]);
        }
    }

    return total ;
}

ZaDomain.searchAccountsInDomain =
function (domainName) {
    if (domainName) {
        var controller = ZaApp.getInstance().getSearchListController();
        var busyId = Dwt.getNextId();
        var callback =  new AjxCallback(controller, controller.searchCallback, {limit:controller.RESULTSPERPAGE,show:true,busyId:busyId});
	// set search query
        controller._currentQuery = "" ;
        var searchTypes = [ZaSearch.ACCOUNTS, ZaSearch.DLS, ZaSearch.ALIASES, ZaSearch.RESOURCES] ;
	// set search types
        if(controller.setSearchTypes)
            controller.setSearchTypes(searchTypes);
	// search domain
	controller._currentDomain = domainName;
	// search attributes
	controller.fetchAttrs = AjxBuffer.concat(ZaAlias.searchAttributes,",",
                        ZaDistributionList.searchAttributes,",",
                        ZaResource.searchAttributes,",",
                        ZaSearch.standardAttributes);
	// set current pagenum
	controller._currentPageNum = 1;

        var searchParams = {
                query:controller._currentQuery,
                domain: controller._currentDomain,
                types:searchTypes,
                attrs:controller.fetchAttrs,
                callback:callback,
                controller: controller,
                                showBusy:true,
                                busyId:busyId,
                                busyMsg:ZaMsg.BUSY_SEARCHING,
                                skipCallbackIfCancelled:false                
        }
         controller.scrollSearchParams={
                query:controller._currentQuery,
                domain: controller._currentDomain,
                types:searchTypes,
                attrs:controller.fetchAttrs,
                controller: controller,
                                showBusy:true,
                                busyMsg:ZaMsg.BUSY_SEARCHING,
                                skipCallbackIfCancelled:false
         }
        ZaSearch.searchDirectory(searchParams);
    }else {
        var currentController = ZaApp.getInstance().getCurrentController () ;
        currentController.popupErrorDialog(ZaMsg.ERROR_NO_DOMAIN_NAME) ;
    }
}

ZaDomain.prototype.createDomainAlias = function (form) {
    var instance = form.getInstance() ;
	var newAlias = instance.attrs [ZaDomain.A_domainName] ;
	var targetName = instance [ZaDomain.A2_zmailDomainAliasTarget] ;

	try {
		var targetObj = ZaDomain.getTargetDomainByName(targetName) ;
        if (targetObj == null) {
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(
                    ZaMsg.ERROR_TARGET_DOMAIN_NOT_EXIST, [targetName]));
            return ;
        } else if (targetObj.attrs [ZaDomain.A_domainType] != ZaDomain.domainTypes.local){
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(
                    ZaMsg.ERROR_TARGET_DOMAIN_IS_ALIAS, [targetName]));
            return ;
        } else if (newAlias == null) {
            ZaApp.getInstance().getCurrentController().popupErrorDialog(
                    ZaMsg.ERROR_DOMAIN_ALIAS_NOT_EXIST);
            return ;
        }

        if (!this.attrs)  this.attrs = {};
		this.attrs [ZaDomain.A_domainName] = newAlias ;
        this.attrs [ZaDomain.A_domainType] = ZaDomain.domainTypes.alias ;
        this.attrs [ZaDomain.A_zmailDomainAliasTargetId] = targetObj.id ;
        this.attrs [ZaDomain.A_description] = AjxMessageFormat.format(
                ZaMsg.DESC_targetDomain, [targetName]) ;
        this.attrs [ZaDomain.A_zmailMailCatchAllAddress] = "@" + newAlias ;
        this.attrs [ZaDomain.A_zmailMailCatchAllForwardingAddress] = "@" + targetName ;

        var soapDoc = AjxSoapDoc.create("CreateDomainRequest", ZaZmailAdmin.URN, null);
        soapDoc.set("name", this.attrs[ZaDomain.A_domainName]);

        var attrNames = [ ZaDomain.A_domainType, ZaDomain.A_zmailDomainAliasTargetId,
                    ZaDomain.A_description, ZaDomain.A_zmailMailCatchAllAddress,
                    ZaDomain.A_zmailMailCatchAllForwardingAddress] ;
        for (var i=0; i < attrNames.length; i ++) {
            var aname = attrNames [i] ;
            if (this.attrs [aname] != null) {
                var attr = soapDoc.set("a", this.attrs[aname]);
		        attr.setAttribute("n", aname);
            }
        }

        var params = new Object();
	    params.soapDoc = soapDoc;
        var reqMgrParams = {
            controller : ZaApp.getInstance().getCurrentController(),
            busyMsg : ZaMsg.BUSY_CREATE_DOMAIN
        }
        var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.CreateDomainResponse;
        ZaApp.getInstance().getDomainListController().fireCreationEvent(this);
        form.parent.popdown();
	} catch (ex) {
		if(ex.code == ZmCsfeException.DOMAIN_EXISTS ) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(
                    ZaMsg.ERROR_DOMAIN_ALIAS_EXIST, [newAlias]));
		}else{
			//if failed for another reason - jump out
			ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaDomain.prototype.createDomainAlias", null, false);
		}
	}   

}

ZaDomain.prototype.modifyDomainAlias = function (form) {
    var instance = form.getInstance() ;
	var targetName =  instance [ZaDomain.A2_zmailDomainAliasTarget] ;
    if (targetName != null && (("@" + targetName) != this.attrs [ZaDomain.A_zmailMailCatchAllForwardingAddress])) {
        //changed
        var targetObj = ZaDomain.getTargetDomainByName(targetName) ;
        if (targetObj == null) {
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(
                    ZaMsg.ERROR_TARGET_DOMAIN_NOT_EXIST, [targetName]));
            return ;
        } else if (targetObj.attrs [ZaDomain.A_domainType] != ZaDomain.domainTypes.local){
            ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(
                    ZaMsg.ERROR_TARGET_DOMAIN_IS_ALIAS, [targetName]));
            return ;
        }

        if (!this.attrs)  this.attrs = {};
		this.attrs [ZaDomain.A_zmailDomainAliasTargetId] = targetObj.id ;
        this.attrs [ZaDomain.A_description] = AjxMessageFormat.format(
                ZaMsg.DESC_targetDomain, [targetName]) ;
        this.attrs [ZaDomain.A_zmailMailCatchAllForwardingAddress] = "@" + targetName ;

        var soapDoc = AjxSoapDoc.create("ModifyDomainRequest", ZaZmailAdmin.URN, null);
        soapDoc.set("id", this.id);

        var attrNames = [ZaDomain.A_zmailDomainAliasTargetId,
                            ZaDomain.A_description,  ZaDomain.A_zmailMailCatchAllForwardingAddress] ;
        for (var i=0; i < attrNames.length; i ++) {
            var aname = attrNames [i] ;
            if (this.attrs [aname] != null) {
                var attr = soapDoc.set("a", this.attrs[aname]);
                attr.setAttribute("n", aname);
            }
        }

        var params = new Object();
	    params.soapDoc = soapDoc;
        var reqMgrParams = {
            controller : ZaApp.getInstance().getCurrentController(),
            busyMsg : ZaMsg.BUSY_MODIFY_DOMAIN
        }
        var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.ModifyDomainResponse;
        this.oldItem = instance;
        ZaApp.getInstance().getDomainListController().fireChangeEvent(this);
    }

    form.parent.popdown();
}


ZaDomain.getTargetDomainByName = function (targetName) {
    var domainList = ZaDomain.getAll(targetName).getArray (); 
    for (var i = 0; i < domainList.length; i ++) {
        var domain = domainList [i] ;
        if (targetName == domain.name)  {
            return domain ;
        }
    }
    return null ;
}

ZaDomain.globalRights = {};
ZaDomain.getEffectiveDomainList = function(adminId) {
    var soapDoc = AjxSoapDoc.create("GetAllEffectiveRightsRequest", ZaZmailAdmin.URN, null);
    var elGrantee = soapDoc.set("grantee", adminId);
    elGrantee.setAttribute("type", "usr");
    elGrantee.setAttribute("by", "id");

    var params = {};
    params.soapDoc = soapDoc;
    params.asyncMode = false;
    var reqMgrParams = {
        controller : ZaApp.getInstance().getCurrentController(),
        busyMsg : ZaMsg.BUSY_GET_EFFICIENT_DOMAIN_LIST
    }

    var domainNameList = [];
    try {
        var resp = ZaRequestMgr.invoke(params, reqMgrParams);
        if(!resp || resp.Body.GetAllEffectiveRightsResponse.Fault)
            return domainNameList;
        var targets = resp.Body.GetAllEffectiveRightsResponse.target;
        for(var i = 0; i < targets.length; i++) {
            if(targets[i].type != ZaItem.DOMAIN) 
                continue;
            
            if(!targets[i].entries && !targets[i].all)
            	continue;
            
            if(targets[i].entries) { 
	            for(var j = 0; j < targets[i].entries.length; j++) {
	                var entry = targets[i].entries[j].entry;
	                for(var k = 0; k < entry.length; k++)
	                    domainNameList.push(entry[k].name);
	            }
            }
            
            if(targets[i].all) { 
            	//we have global access to domains
            	if(targets[i].all.length && targets[i].all[0] && targets[i].all[0].right && targets[i].all[0].right.length) {
            		for(var j=0;j<targets[i].all[0].right.length;j++) {
        				ZaDomain.globalRights[targets[i].all[0].right[j].n] = true;
            		}
            	}
            }

            break;
        }
        return domainNameList;
    } catch(ex) {
        ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaDomain.getEffectiveDomainList", null, false);
    }
}

ZaDomain.prototype.countAllAccounts = function() {
	var soapDoc = AjxSoapDoc.create("SearchDirectoryRequest", ZaZmailAdmin.URN, null);
    soapDoc.getMethod().setAttribute("maxResults", "0");
    soapDoc.getMethod().setAttribute("limit", "-1");
	var query = "";
    var types = [ZaSearch.ACCOUNTS, ZaSearch.DLS, ZaSearch.ALIASES, ZaSearch.RESOURCES];

	soapDoc.set("query", query);
    soapDoc.set("types", types.toString());
    soapDoc.set("domain", this.name);
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

ZaDomain.prototype.countAllAliases = function() {
	var soapDoc = AjxSoapDoc.create("SearchDirectoryRequest", ZaZmailAdmin.URN, null);
	soapDoc.getMethod().setAttribute("limit", "1");
	var query = "(" + ZaDomain.A_zmailDomainAliasTargetId + "=" + this.id + ")";
    var types = [ZaSearch.DOMAINS];

	soapDoc.set("query", query);
    soapDoc.set("types", types.toString());
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

ZaDomain.getRelatedList =
function (parentPath) {
    var Tis = [];
    var count = this.countAllAccounts();
    if(count > 0) {
        var accountTi = new ZaTreeItemData({
                    text: ZaMsg.OVP_accounts,
                    count:count,
                    image:"Account",
                    mappingId: ZaZmailAdmin._DOMAIN_ACCOUNT_LIST_VIEW,
                    path: parentPath + ZaTree.SEPERATOR + this.name + ZaTree.SEPERATOR + ZaMsg.OVP_accounts
                    }
                );
        accountTi.setData("domainItem", this);
        ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._DOMAIN_ACCOUNT_LIST_VIEW] = ZaOverviewPanelController.accountListInDomainTreeListener;
        Tis.push(accountTi);
    }

    count = this.countAllAliases();
    if(count > 0) {
        var aliasTi = new ZaTreeItemData({
                    text: ZaMsg.TABT_Aliases,
                    count:count,
                    image:"DomainAlias",
                    mappingId: ZaZmailAdmin._DOMAIN_ALIAS_LIST_VIEW,
                    path: parentPath + ZaTree.SEPERATOR + this.name + ZaTree.SEPERATOR + ZaMsg.OVP_accounts
                    }
                );
        aliasTi.setData("domainItem", this);
        ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._DOMAIN_ALIAS_LIST_VIEW] = ZaOverviewPanelController.domainListTreeListener;
        Tis.push(aliasTi);
    }
    return Tis;
}

ZaItem.getRelatedMethods["ZaDomain"].push(ZaDomain.getRelatedList);
