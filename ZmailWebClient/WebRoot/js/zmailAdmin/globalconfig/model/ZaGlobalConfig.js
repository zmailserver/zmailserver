/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

ZaGlobalConfig = function() {
	ZaItem.call(this,"ZaGlobalConfig");
	this.attrs = new Object();
	this.type = ZaItem.GLOBAL_CONFIG;
//	this.attrsInternal = new Object();	
	this.load();
}

ZaGlobalConfig.prototype = new ZaItem;
ZaGlobalConfig.prototype.constructor = ZaGlobalConfig;
ZaItem.loadMethods["ZaGlobalConfig"] = new Array();
ZaItem.modifyMethods["ZaGlobalConfig"] = new Array();
ZaItem.modifyMethodsExt["ZaGlobalConfig"] = new Array();

ZaGlobalConfig.MTA_RESTRICTIONS = [
	"reject_invalid_hostname", "reject_non_fqdn_hostname", "reject_non_fqdn_sender",
	"reject_unknown_client", "reject_unknown_hostname", "reject_unknown_sender_domain"
];

//general
ZaGlobalConfig.A_zmailLastLogonTimestampFrequency = "zmailLastLogonTimestampFrequency";
ZaGlobalConfig.A_zmailDefaultDomainName = "zmailDefaultDomainName";
ZaGlobalConfig.A_zmailScheduledTaskNumThreads = "zmailScheduledTaskNumThreads" ;
ZaGlobalConfig.A_zmailMailPurgeSleepInterval = "zmailMailPurgeSleepInterval" ;
		
// attachements
ZaGlobalConfig.A_zmailAttachmentsBlocked = "zmailAttachmentsBlocked";
ZaGlobalConfig.A_zmailMtaBlockedExtensionWarnRecipient = "zmailMtaBlockedExtensionWarnRecipient";
ZaGlobalConfig.A_zmailMtaBlockedExtension = "zmailMtaBlockedExtension";
ZaGlobalConfig.A_zmailMtaCommonBlockedExtension = "zmailMtaCommonBlockedExtension";

// MTA
ZaGlobalConfig.A_zmailMtaSaslAuthEnable = "zmailMtaSaslAuthEnable";
ZaGlobalConfig.A_zmailMtaTlsAuthOnly = "zmailMtaTlsAuthOnly";
ZaGlobalConfig.A_zmailMtaDnsLookupsEnabled  = "zmailMtaDnsLookupsEnabled";
ZaGlobalConfig.A_zmailMtaMaxMessageSize = "zmailMtaMaxMessageSize";
ZaGlobalConfig.A_zmailMtaRelayHost = "zmailMtaRelayHost";
ZaGlobalConfig.A_zmailMtaFallbackRelayHost = "zmailMtaFallbackRelayHost";
ZaGlobalConfig.A_zmailMtaMyNetworks = "zmailMtaMyNetworks";
//ZaGlobalConfig.A_zmailMtaRelayHostInternal = "__zmailMtaRelayHost";
//ZaGlobalConfig.A_zmailMtaRelayPortInternal = "__zmailMtaRelayPort";
ZaGlobalConfig.A_zmailComponentAvailable = "zmailComponentAvailable";
ZaGlobalConfig.A_zmailSmtpSendAddOriginatingIP = "zmailSmtpSendAddOriginatingIP";
ZaGlobalConfig.A_zmailDNSCheckHostname = "zmailDNSCheckHostname";
ZaGlobalConfig.A_zmailBasicAuthRealm = "zmailBasicAuthRealm";
ZaGlobalConfig.A_zmailAdminConsoleDNSCheckEnabled = "zmailAdminConsoleDNSCheckEnabled";
ZaGlobalConfig.A_zmailAdminConsoleCatchAllAddressEnabled = "zmailAdminConsoleCatchAllAddressEnabled";
ZaGlobalConfig.A_zmailAdminConsoleSkinEnabled = "zmailAdminConsoleSkinEnabled";
ZaGlobalConfig.A_zmailAdminConsoleLDAPAuthEnabled = "zmailAdminConsoleLDAPAuthEnabled" ;

ZaGlobalConfig.A_zmailMilterBindPort = "zmailMilterBindPort";
ZaGlobalConfig.A_zmailMilterServerEnabled = "zmailMilterServerEnabled";


ZaGlobalConfig.A_zmailMtaRestriction = "zmailMtaRestriction";

// --policy service checks
ZaGlobalConfig.A_zmailMtaPolicyService = "_"+ZaGlobalConfig.A_zmailMtaRestriction+"_policy_service";
// --protocol checks
ZaGlobalConfig.A_zmailMtaRejectInvalidHostname = "_"+ZaGlobalConfig.A_zmailMtaRestriction+"_reject_invalid_hostname";
ZaGlobalConfig.A_zmailMtaRejectNonFqdnHostname = "_"+ZaGlobalConfig.A_zmailMtaRestriction+"_reject_non_fqdn_hostname";
ZaGlobalConfig.A_zmailMtaRejectNonFqdnSender = "_"+ZaGlobalConfig.A_zmailMtaRestriction+"_reject_non_fqdn_sender";
// -- dns checks
ZaGlobalConfig.A_zmailMtaRejectUnknownClient = "_"+ZaGlobalConfig.A_zmailMtaRestriction+"_reject_unknown_client";
ZaGlobalConfig.A_zmailMtaRejectUnknownHostname = "_"+ZaGlobalConfig.A_zmailMtaRestriction+"_reject_unknown_hostname";
ZaGlobalConfig.A_zmailMtaRejectUnknownSenderDomain = "_"+ZaGlobalConfig.A_zmailMtaRestriction+"_reject_unknown_sender_domain";
//rbl check
ZaGlobalConfig.A_zmailMtaRejectRblClient = "_"+ZaGlobalConfig.A_zmailMtaRestriction+"_reject_rbl_client";
  
//Domain
ZaGlobalConfig.A_zmailGalLdapFilterDef = "zmailGalLdapFilterDef";
ZaGlobalConfig.A_zmailGalMaxResults = "zmailGalMaxResults";
ZaGlobalConfig.A_zmailNotebookAccount = "zmailNotebookAccount";
//Server
ZaGlobalConfig.A_zmailLmtpNumThreads = "zmailLmtpNumThreads";
ZaGlobalConfig.A_zmailLmtpBindPort = "zmailLmtpBindPort";
ZaGlobalConfig.A_zmailPop3NumThreads = "zmailPop3NumThreads";
ZaGlobalConfig.A_zmailPop3BindPort = "zmailPop3BindPort";
ZaGlobalConfig.A_zmailRedologEnabled = "zmailRedologEnabled";
ZaGlobalConfig.A_zmailRedologLogPath = "zmailRedologLogPath";
ZaGlobalConfig.A_zmailRedologArchiveDir = "zmailRedologArchiveDir";
ZaGlobalConfig.A_zmailRedologBacklogDir = "zmailRedologBacklogDir";
ZaGlobalConfig.A_zmailRedologRolloverFileSizeKB = "zmailRedologRolloverFileSizeKB";
ZaGlobalConfig.A_zmailRedologFsyncIntervalMS = "zmailRedologFsyncIntervalMS";
ZaGlobalConfig.A_zmailFileUploadMaxSize = "zmailFileUploadMaxSize"

// smtp
ZaGlobalConfig.A_zmailSmtpHostname = "zmailSmtpHostname";
ZaGlobalConfig.A_zmailSmtpPort = "zmailSmtpPort";
ZaGlobalConfig.A_zmailSmtpTimeout = "zmailSmtpTimeout";
// pop
ZaGlobalConfig.A_zmailPop3BindPort="zmailPop3BindPort";
ZaGlobalConfig.A_zmailPop3ServerEnabled = "zmailPop3ServerEnabled";
ZaGlobalConfig.A_zmailPop3SSLBindPort = "zmailPop3SSLBindPort";
ZaGlobalConfig.A_zmailPop3SSLServerEnabled = "zmailPop3SSLServerEnabled";
ZaGlobalConfig.A_zmailPop3CleartextLoginEnabled = "zmailPop3CleartextLoginEnabled";
// imap
ZaGlobalConfig.A_zmailImapBindPort = "zmailImapBindPort";
ZaGlobalConfig.A_zmailImapServerEnabled = "zmailImapServerEnabled";
ZaGlobalConfig.A_zmailImapNumThreads = "zmailImapNumThreads"
ZaGlobalConfig.A_zmailImapSSLBindPort = "zmailImapSSLBindPort";
ZaGlobalConfig.A_zmailImapSSLServerEnabled = "zmailImapSSLServerEnabled";
ZaGlobalConfig.A_zmailImapCleartextLoginEnabled = "zmailImapCleartextLoginEnabled";
// anti-spam
ZaGlobalConfig.A_zmailSpamKillPercent = "zmailSpamKillPercent";
ZaGlobalConfig.A_zmailSpamTagPercent = "zmailSpamTagPercent";
ZaGlobalConfig.A_zmailSpamSubjectTag = "zmailSpamSubjectTag";
ZaGlobalConfig.A_zmailSpamAccount = "zmailSpamIsSpamAccount";
ZaGlobalConfig.A_zmailHamAccount = "zmailSpamIsNotSpamAccount";
//wiki account
ZaGlobalConfig.A_zmailWikiAccount = "zmailNotebookAccount";
//Amavis account
ZaGlobalConfig.A_zmailAmavisQAccount = "zmailAmavisQuarantineAccount";
// anti-virus
ZaGlobalConfig.A_zmailVirusWarnRecipient = "zmailVirusWarnRecipient";
ZaGlobalConfig.A_zmailVirusWarnAdmin = "zmailVirusWarnAdmin";
ZaGlobalConfig.A_zmailVirusDefinitionsUpdateFrequency = "zmailVirusDefinitionsUpdateFrequency";
ZaGlobalConfig.A_zmailVirusBlockEncryptedArchive = "zmailVirusBlockEncryptedArchive";
//immutable attrs
ZaGlobalConfig.A_zmailAccountClientAttr = "zmailAccountClientAttr";
ZaGlobalConfig.A_zmailServerInheritedAttr = "zmailServerInheritedAttr";
ZaGlobalConfig.A_zmailDomainInheritedAttr = "zmailDomainInheritedAttr";
ZaGlobalConfig.A_zmailCOSInheritedAttr = "zmailCOSInheritedAttr";
ZaGlobalConfig.A_zmailGalLdapAttrMap = "zmailGalLdapAttrMap";
ZaGlobalConfig.A_zmailGalLdapFilterDef = "zmailGalLdapFilterDef";

//security
ZaGlobalConfig.A_zmailMailMode = "zmailMailMode"  ;

//mailproxy
ZaGlobalConfig.A_zmailImapProxyBindPort="zmailImapProxyBindPort";
ZaGlobalConfig.A_zmailImapSSLProxyBindPort="zmailImapSSLProxyBindPort";
ZaGlobalConfig.A_zmailPop3ProxyBindPort="zmailPop3ProxyBindPort";
ZaGlobalConfig.A_zmailPop3SSLProxyBindPort="zmailPop3SSLProxyBindPort";
ZaGlobalConfig.A_zmailReverseProxyLookupTarget = "zmailReverseProxyLookupTarget";

// mail validation
ZaGlobalConfig.A_zmailMailAddressValidationRegex = "zmailMailAddressValidationRegex";

// others
ZaGlobalConfig.A_zmailInstalledSkin = "zmailInstalledSkin";
ZaGlobalConfig.A_zmailNewExtension = "_zmailNewExtension";

ZaGlobalConfig.A_originalMonitorHost = "_originalMonitorHost";
ZaGlobalConfig.A_currentMonitorHost = "_currentMonitorHost";

//interop
ZaGlobalConfig.A_zmailFreebusyExchangeAuthUsername = "zmailFreebusyExchangeAuthUsername" ;
ZaGlobalConfig.A_zmailFreebusyExchangeAuthPassword = "zmailFreebusyExchangeAuthPassword" ;
ZaGlobalConfig.A_zmailFreebusyExchangeAuthScheme  = "zmailFreebusyExchangeAuthScheme" ;
ZaGlobalConfig.A_zmailFreebusyExchangeServerType  = "zmailFreebusyExchangeServerType" ;
ZaGlobalConfig.A_zmailFreebusyExchangeURL ="zmailFreebusyExchangeURL";
ZaGlobalConfig.A_zmailFreebusyExchangeUserOrg = "zmailFreebusyExchangeUserOrg"  ;

//spnego
ZaGlobalConfig.A_zmailSpnegoAuthEnabled = "zmailSpnegoAuthEnabled";
ZaGlobalConfig.A_zmailSpnegoAuthRealm = "zmailSpnegoAuthRealm";
ZaGlobalConfig.A_zmailSpnegoAuthErrorURL = "zmailSpnegoAuthErrorURL";

//sso
ZaGlobalConfig.A_zmailWebClientLoginURL = "zmailWebClientLoginURL";
ZaGlobalConfig.A_zmailWebClientLogoutURL = "zmailWebClientLogoutURL";
ZaGlobalConfig.A_zmailWebClientLoginURLAllowedUA = "zmailWebClientLoginURLAllowedUA";
ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedUA = "zmailWebClientLogoutURLAllowedUA";
ZaGlobalConfig.A_zmailWebClientLoginURLAllowedIP = "zmailWebClientLoginURLAllowedIP";
ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedIP = "zmailWebClientLogoutURLAllowedIP";
ZaGlobalConfig.A_zmailForceClearCookies = "zmailForceClearCookies";

// Auto provision
ZaGlobalConfig.A_zmailAutoProvBatchSize = "zmailAutoProvBatchSize";
ZaGlobalConfig.A_zmailAutoProvPollingInterval = "zmailAutoProvPollingInterval";

// web client authentication
ZaGlobalConfig.A_zmailMailSSLClientCertMode = "zmailMailSSLClientCertMode";
ZaGlobalConfig.A_zmailMailSSLClientCertPort = "zmailMailSSLClientCertPort";
ZaGlobalConfig.A_zmailMailSSLClientCertPrincipalMap = "zmailMailSSLClientCertPrincipalMap";
ZaGlobalConfig.A_zmailReverseProxyClientCertMode = "zmailReverseProxyClientCertMode";
ZaGlobalConfig.A_zmailMailSSLProxyClientCertPort = "zmailMailSSLProxyClientCertPort";
ZaGlobalConfig.A_zmailReverseProxyMailMode = "zmailReverseProxyMailMode";
ZaGlobalConfig.A_zmailReverseProxyAdminIPAddress = "zmailReverseProxyAdminIPAddress";
ZaGlobalConfig.A_zmailReverseProxyClientCertCA = "zmailReverseProxyClientCertCA";
ZaGlobalConfig.A_zmailAutoProvNotificationSubject = "zmailAutoProvNotificationSubject";
ZaGlobalConfig.A_zmailAutoProvNotificationBody = "zmailAutoProvNotificationBody";

//Skin Properties
ZaGlobalConfig.A_zmailSkinForegroundColor = "zmailSkinForegroundColor" ;
ZaGlobalConfig.A_zmailSkinBackgroundColor = "zmailSkinBackgroundColor" ;
ZaGlobalConfig.A_zmailSkinSecondaryColor = "zmailSkinSecondaryColor" ;
ZaGlobalConfig.A_zmailSkinSelectionColor  = "zmailSkinSelectionColor" ;
ZaGlobalConfig.A_zmailSkinLogoURL ="zmailSkinLogoURL" ;
ZaGlobalConfig.A_zmailSkinLogoLoginBanner = "zmailSkinLogoLoginBanner" ;
ZaGlobalConfig.A_zmailSkinLogoAppBanner = "zmailSkinLogoAppBanner" ;
ZaGlobalConfig.A2_blocked_extension_selection = "blocked_extension_selection";
ZaGlobalConfig.A2_common_extension_selection = "common_extension_selection";
ZaGlobalConfig.A2_retentionPoliciesKeep = "retentionPolicyKeep";
ZaGlobalConfig.A2_retentionPoliciesPurge = "retentionPolicyPurge";
ZaGlobalConfig.A2_retentionPoliciesKeep_Selection = "retentionPoliciesKeep_Selection";
ZaGlobalConfig.A2_retentionPoliciesPurge_Selection = "retentionPoliciesPurge_Selection";

// help URL
ZaGlobalConfig.A_zmailHelpAdminURL = "zmailHelpAdminURL";
ZaGlobalConfig.A_zmailHelpDelegatedURL = "zmailHelpDelegatedURL";

ZaGlobalConfig.__configInstance = null;
ZaGlobalConfig.isDirty = true;

ZaGlobalConfig.CHECK_EXCHANGE_AUTH_CONFIG_RIGHT = "checkExchangeAuthConfig"
ZaGlobalConfig.getInstance = function(refresh) {
	if(refresh || ZaGlobalConfig.isDirty || !ZaGlobalConfig.__configInstance) {
		ZaGlobalConfig.__configInstance = new ZaGlobalConfig();
		ZaGlobalConfig.isDirty = false;
	}
	return ZaGlobalConfig.__configInstance;
}

ZaGlobalConfig.loadMethod = 
function(by, val) {
	var soapDoc = AjxSoapDoc.create("GetAllConfigRequest", ZaZmailAdmin.URN, null);
	if(!this.getAttrs.all && !AjxUtil.isEmpty(this.attrsToGet)) {
		soapDoc.setMethodAttribute("attrs", this.attrsToGet.join(","));
	}	
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;
	params.noAuthToken = true;	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_GET_ALL_CONFIG
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.GetAllConfigResponse;
	this.initFromJS(resp);	
}
ZaItem.loadMethods["ZaGlobalConfig"].push(ZaGlobalConfig.loadMethod);

ZaGlobalConfig.prototype.initFromJS = function(obj) {
	ZaItem.prototype.initFromJS.call(this, obj);
	
	if(AjxUtil.isString(this.attrs[ZaGlobalConfig.A_zmailMtaBlockedExtension])) {
		this.attrs[ZaGlobalConfig.A_zmailMtaBlockedExtension] = [this.attrs[ZaGlobalConfig.A_zmailMtaBlockedExtension]];
	}
	
	if(AjxUtil.isString(this.attrs[ZaGlobalConfig.A_zmailMtaCommonBlockedExtension])) {
		this.attrs[ZaGlobalConfig.A_zmailMtaCommonBlockedExtension] = [this.attrs[ZaGlobalConfig.A_zmailMtaCommonBlockedExtension]];
	}
	
	if(AjxUtil.isString(this.attrs[ZaGlobalConfig.A_zmailSmtpHostname])) {
		this.attrs[ZaGlobalConfig.A_zmailSmtpHostname] = [this.attrs[ZaGlobalConfig.A_zmailSmtpHostname]];
	}

        if(AjxUtil.isString(this.attrs[ZaGlobalConfig.A_zmailMailAddressValidationRegex])) {
                this.attrs[ZaGlobalConfig.A_zmailMailAddressValidationRegex] = [this.attrs[ZaGlobalConfig.A_zmailMailAddressValidationRegex]];
        }	
    if(AjxUtil.isString(this.attrs[ZaGlobalConfig.A_zmailReverseProxyAdminIPAddress])) {
		this.attrs[ZaGlobalConfig.A_zmailReverseProxyAdminIPAddress] = [this.attrs[ZaGlobalConfig.A_zmailReverseProxyAdminIPAddress]];
	}

	if(AjxUtil.isString(this.attrs[ZaGlobalConfig.A_zmailWebClientLoginURLAllowedUA])) {
		this.attrs[ZaGlobalConfig.A_zmailWebClientLoginURLAllowedUA] = [this.attrs[ZaGlobalConfig.A_zmailWebClientLoginURLAllowedUA]];
	}

    if(AjxUtil.isString(this.attrs[ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedUA])) {
		this.attrs[ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedUA] = [this.attrs[ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedUA]];
	}

    if(AjxUtil.isString(this.attrs[ZaGlobalConfig.A_zmailWebClientLoginURLAllowedIP])) {
        this.attrs[ZaGlobalConfig.A_zmailWebClientLoginURLAllowedIP] = [this.attrs[ZaGlobalConfig.A_zmailWebClientLoginURLAllowedIP]];
    }

    if(AjxUtil.isString(this.attrs[ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedIP])) {
        this.attrs[ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedIP] = [this.attrs[ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedIP]];
    }
	// convert available components to hidden fields for xform binding
	var components = this.attrs[ZaGlobalConfig.A_zmailComponentAvailable];
	if (components) {
		if (AjxUtil.isString(components)) {
			components = [ components ];
		}
		for (var i = 0; i < components.length; i++) {
			var component = components[i];
			this.attrs["_"+ZaGlobalConfig.A_zmailComponentAvailable+"_"+component] = true;
		}
	}
	
	//init list of RBLs
	this.attrs[ZaGlobalConfig.A_zmailMtaRejectRblClient] = [];
        this.attrs[ZaGlobalConfig.A_zmailMtaPolicyService] = [];	
	// convert restrictions to hidden fields for xform binding
	var restrictions = this.attrs[ZaGlobalConfig.A_zmailMtaRestriction];
	if (restrictions) {
		if (AjxUtil.isString(restrictions)) {
			restrictions = [ restrictions ];
		}
		for (var i = 0; i < restrictions.length; i++) {
			if(restrictions[i].indexOf("reject_rbl_client")>-1) {
				var restriction = restrictions[i];
				var chunks = restriction.split(" ");
				if(chunks && chunks.length>0) {
					this.attrs[ZaGlobalConfig.A_zmailMtaRejectRblClient].push(chunks[1]);
				}
			} else if (restrictions[i].indexOf("check_policy_service")>-1){
				var restriction = restrictions[i];
                                var chunks = restriction.split(" ");
                                if(chunks && chunks.length>0) {
                                        this.attrs[ZaGlobalConfig.A_zmailMtaPolicyService].push(chunks[1]);
                                }

                        } else {
				var restriction = restrictions[i];
				this.attrs["_"+ZaGlobalConfig.A_zmailMtaRestriction+"_"+restriction] = true;
			}
		}
	}
	if(this.attrs[ZaGlobalConfig.A_zmailInstalledSkin] != null && !(this.attrs[ZaGlobalConfig.A_zmailInstalledSkin] instanceof Array)) {
		this.attrs[ZaGlobalConfig.A_zmailInstalledSkin] = [this.attrs[ZaGlobalConfig.A_zmailInstalledSkin]];
	}
}

//ZaGlobalConfig.prototype.modify = 
ZaGlobalConfig.modifyMethod = function (tmods, tmpObj) {
        var soapDoc = AjxSoapDoc.create("BatchRequest", "urn:zmail");
        soapDoc.setMethodAttribute("onerror", "stop");

        // S/MIME
        var mods = tmods;
        if(ZaItem.modifyMethodsExt["ZaGlobalConfig"]) {
                var methods = ZaItem.modifyMethodsExt["ZaGlobalConfig"];
                var cnt = methods.length;
                for(var i = 0; i < cnt; i++) {
                        if(typeof(methods[i]) == "function")
                               methods[i].call(this, mods, tmpObj, soapDoc);
                }

        }

        var modifyConfDoc = soapDoc.set("ModifyConfigRequest", null, null, ZaZmailAdmin.URN);
        for (var aname in mods) {
                //multy value attribute
                if(mods[aname] instanceof Array) {
                        var cnt = mods[aname].length;
                        if(cnt > 0) {
                                for(var ix=0; ix <cnt; ix++) {
                                        if(mods[aname][ix] instanceof String)
                                                var attr = soapDoc.set("a", mods[aname][ix].toString(), modifyConfDoc);
                                        else if(mods[aname][ix] instanceof Object)
                                                var attr = soapDoc.set("a", mods[aname][ix].toString(), modifyConfDoc);
                                        else 
                                                var attr = soapDoc.set("a", mods[aname][ix], modifyConfDoc);
                                                
                                        attr.setAttribute("n", aname);
                                }
                        } 
                        else {
                                var attr = soapDoc.set("a", "", modifyConfDoc);
                                attr.setAttribute("n", aname);
                        }
                } else {
                        //bug fix 10354: ingnore the changed ZaLicense Properties
                        if ((typeof ZaLicense == "function") && (ZaSettings.LICENSE_ENABLED)){
                                if (ZaUtil.findValueInObjArrByPropertyName (ZaLicense.myXModel.items, aname, "id") > -1 ){
                                        continue ;
                                }
                        }
                        var attr = soapDoc.set("a", mods[aname], modifyConfDoc);
                        attr.setAttribute("n", aname);
                }
        }

	var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;
	params.noAuthToken = true;	
	command.invoke(params);
	ZaGlobalConfig.isDirty = true;
}
ZaItem.modifyMethods["ZaGlobalConfig"].push(ZaGlobalConfig.modifyMethod);

// REVISIT: Move to a common location if needed by others
LifetimeNumber_XFormItem = function() {}
XModelItemFactory.createItemType("_LIFETIME_NUMBER_", "lifetime_number", LifetimeNumber_XFormItem, Number_XModelItem);
LifetimeNumber_XFormItem.prototype.validateType = function(value) {
	// strip off lifetime char (e.g. 's', 'h', 'd', ...)
	var number = value.substring(0, value.length - 1);
	this.validateNumber(number);
	return value;
}

ZaGlobalConfig.myXModel = {
	items:[
	  	// ...other...
		{ id:ZaGlobalConfig.A_zmailGalMaxResults, ref:"attrs/" + ZaGlobalConfig.A_zmailGalMaxResults , type:_NUMBER_, minInclusive: 0 },
		{ id:ZaGlobalConfig.A_zmailDefaultDomainName, ref:"attrs/" + ZaGlobalConfig.A_zmailDefaultDomainName, type:_STRING_, maxLength: 256},
		{ id:ZaGlobalConfig.A_zmailScheduledTaskNumThreads, ref:"attrs/" + ZaGlobalConfig.A_zmailScheduledTaskNumThreads , type:_NUMBER_, minInclusive: 1 },
		{ id:ZaGlobalConfig.A_zmailMailPurgeSleepInterval, type:_MLIFETIME_, ref:"attrs/"+ZaGlobalConfig.A_zmailMailPurgeSleepInterval},
		
		{ id:ZaGlobalConfig.A_currentMonitorHost, ref: "attrs/"+ZaGlobalConfig.A_currentMonitorHost, type: _STRING_ },
		// attachments
		{ id:ZaGlobalConfig.A_zmailAttachmentsBlocked, ref:"attrs/" + ZaGlobalConfig.A_zmailAttachmentsBlocked, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
		{ id:ZaGlobalConfig.A_zmailMtaBlockedExtensionWarnRecipient, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaBlockedExtensionWarnRecipient, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES},
		{ id:ZaGlobalConfig.A_zmailMtaBlockedExtension, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaBlockedExtension, type: _LIST_, dataType: _STRING_ },
		{ id:ZaGlobalConfig.A_zmailMtaCommonBlockedExtension, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaCommonBlockedExtension, type: _LIST_, dataType: _STRING_ },
		// MTA
		{ id:ZaGlobalConfig.A_zmailMtaSaslAuthEnable, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaSaslAuthEnable, type: _ENUM_, choices: ["yes", "no"] },
		{ id:ZaGlobalConfig.A_zmailMtaTlsAuthOnly, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaTlsAuthOnly, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES },
                { id:ZaGlobalConfig.A_zmailMailAddressValidationRegex, ref:"attrs/" + ZaGlobalConfig.A_zmailMailAddressValidationRegex, type:_LIST_, listItem:{ type:_STRING_, maxLength: 512} },
		{ id:ZaGlobalConfig.A_zmailSmtpHostname, ref:"attrs/" + ZaGlobalConfig.A_zmailSmtpHostname, type:_LIST_, listItem:{ type:_HOSTNAME_OR_IP_, maxLength: 256} },
		{ id:ZaGlobalConfig.A_zmailSmtpPort, ref:"attrs/" + ZaGlobalConfig.A_zmailSmtpPort, type:_PORT_ },
		{ id:ZaGlobalConfig.A_zmailMtaMaxMessageSize, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaMaxMessageSize, type: _FILE_SIZE_, units: AjxUtil.SIZE_KILOBYTES, required: true },
		{ id:ZaGlobalConfig.A_zmailFileUploadMaxSize, ref:"attrs/" + ZaGlobalConfig.A_zmailFileUploadMaxSize, type: _FILE_SIZE_, units: AjxUtil.SIZE_KILOBYTES },
		{id:ZaGlobalConfig.A_zmailMtaMyNetworks, ref:"attrs/" +  ZaGlobalConfig.A_zmailMtaMyNetworks, type:_STRING_, maxLength: 10240 },
		{ id:ZaGlobalConfig.A_zmailMtaRelayHost, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaRelayHost, type: _HOSTNAME_OR_IP_, maxLength: 256 },
        { id:ZaGlobalConfig.A_zmailMtaFallbackRelayHost, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaFallbackRelayHost, type: _HOSTNAME_OR_IP_, maxLength: 256 },
		{ id:ZaGlobalConfig.A_zmailSmtpSendAddOriginatingIP, ref: "attrs/" + ZaGlobalConfig.A_zmailSmtpSendAddOriginatingIP, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
		
		{ id:ZaGlobalConfig.A_zmailMtaDnsLookupsEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaDnsLookupsEnabled, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES },
		{ id:ZaGlobalConfig.A_zmailMilterServerEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailMilterServerEnabled, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES },
                { id:ZaGlobalConfig.A_zmailMilterBindPort, ref:"attrs/" + ZaGlobalConfig.A_zmailMilterBindPort, type:_PORT_ },

		// -- protocol checks
		{ id:ZaGlobalConfig.A_zmailMtaRejectInvalidHostname, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaRejectInvalidHostname, type: _ENUM_, choices: [false,true] },
		{ id:ZaGlobalConfig.A_zmailMtaRejectNonFqdnHostname, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaRejectNonFqdnHostname, type: _ENUM_, choices: [false,true] },
		{ id:ZaGlobalConfig.A_zmailMtaRejectNonFqdnSender, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaRejectNonFqdnSender, type: _ENUM_, choices: [false,true] },
		// -- dns checks
		{ id:ZaGlobalConfig.A_zmailMtaRejectUnknownClient, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaRejectUnknownClient, type: _ENUM_, choices: [false,true] },
		{ id:ZaGlobalConfig.A_zmailMtaRejectUnknownHostname, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaRejectUnknownHostname, type: _ENUM_, choices: [false,true] },
		{ id:ZaGlobalConfig.A_zmailMtaRejectUnknownSenderDomain, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaRejectUnknownSenderDomain, type: _ENUM_, choices: [false,true] },
		{id:ZaGlobalConfig.A_zmailDNSCheckHostname, type:_STRING_, ref:"attrs/" + ZaGlobalConfig.A_zmailDNSCheckHostname, maxLength:255},		
		{id:ZaGlobalConfig.A_zmailBasicAuthRealm, type:_STRING_, ref:"attrs/" + ZaGlobalConfig.A_zmailBasicAuthRealm, maxLength:255},
		{id:ZaGlobalConfig.A_zmailAdminConsoleDNSCheckEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaGlobalConfig.A_zmailAdminConsoleDNSCheckEnabled},

        {id:ZaGlobalConfig.A_zmailAdminConsoleCatchAllAddressEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaGlobalConfig.A_zmailAdminConsoleCatchAllAddressEnabled},
		{id:ZaGlobalConfig.A_zmailAdminConsoleSkinEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaGlobalConfig.A_zmailAdminConsoleSkinEnabled},
        {id:ZaGlobalConfig.A_zmailAdminConsoleLDAPAuthEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaGlobalConfig.A_zmailAdminConsoleLDAPAuthEnabled},    
                //check policy service
                { id:ZaGlobalConfig.A_zmailMtaPolicyService, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaPolicyService, type: _LIST_, listItem:{type:_STRING_}},

                //rbl check
		{ id:ZaGlobalConfig.A_zmailMtaRejectRblClient, ref:"attrs/" + ZaGlobalConfig.A_zmailMtaRejectRblClient, type: _LIST_, listItem:{type:_STRING_}},
		// smtp
		{ id:ZaGlobalConfig.A_zmailSmtpTimeout, ref:"attrs/" + ZaGlobalConfig.A_zmailSmtpTimeout, type:_NUMBER_, minInclusive: 0 },
		// pop
		{ id:ZaGlobalConfig.A_zmailPop3ServerEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailPop3ServerEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
		{ id:ZaGlobalConfig.A_zmailPop3SSLServerEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailPop3SSLServerEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},		
		{ id:ZaGlobalConfig.A_zmailPop3CleartextLoginEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailPop3CleartextLoginEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},				
		{ id:ZaGlobalConfig.A_zmailPop3BindPort, ref:"attrs/" + ZaGlobalConfig.A_zmailPop3BindPort, type:_PORT_ },
		{ id:ZaGlobalConfig.A_zmailPop3SSLBindPort, ref:"attrs/" + ZaGlobalConfig.A_zmailPop3SSLBindPort, type:_PORT_ },
		// imap
		{ id:ZaGlobalConfig.A_zmailImapServerEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailImapServerEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},						
		{ id:ZaGlobalConfig.A_zmailImapSSLServerEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailImapSSLServerEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},								
		{ id:ZaGlobalConfig.A_zmailImapCleartextLoginEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailImapCleartextLoginEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},										
        { id:ZaGlobalConfig.A_zmailImapNumThreads, ref:"attrs/" + ZaGlobalConfig.A_zmailImapNumThreads, type:_INT_,  minInclusive: 0, maxInclusive:2147483647  },
		{ id:ZaGlobalConfig.A_zmailImapBindPort, ref:"attrs/" + ZaGlobalConfig.A_zmailImapBindPort, type:_PORT_ },
		{ id:ZaGlobalConfig.A_zmailImapSSLBindPort, ref:"attrs/" + ZaGlobalConfig.A_zmailImapSSLBindPort, type:_PORT_ },
		// anti-spam
	  	{ id:ZaGlobalConfig.A_zmailSpamCheckEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailSpamCheckEnabled, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES },
	  	{ id:ZaGlobalConfig.A_zmailSpamKillPercent, ref:"attrs/" + ZaGlobalConfig.A_zmailSpamKillPercent, type: _PERCENT_, fractionDigits: 0 },
	  	{ id:ZaGlobalConfig.A_zmailSpamTagPercent, ref:"attrs/" + ZaGlobalConfig.A_zmailSpamTagPercent, type: _PERCENT_, fractionDigits: 0 },
	  	{ id:ZaGlobalConfig.A_zmailSpamSubjectTag, ref:"attrs/" + ZaGlobalConfig.A_zmailSpamSubjectTag, type: _STRING_, whiteSpace: 'collapse', maxLength: 32 },
	  	// anti-virus
	  	{ id:ZaGlobalConfig.A_zmailVirusCheckEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailVirusCheckEnabled, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES },
	  	{ id:ZaGlobalConfig.A_zmailVirusDefinitionsUpdateFrequency, ref:"attrs/" + ZaGlobalConfig.A_zmailVirusDefinitionsUpdateFrequency, type: _LIFETIME_NUMBER_, minInclusive: 0, fractionDigits: 0 },
	  	{ id:ZaGlobalConfig.A_zmailVirusBlockEncryptedArchive, ref:"attrs/" + ZaGlobalConfig.A_zmailVirusBlockEncryptedArchive, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES},
	  	{ id:ZaGlobalConfig.A_zmailVirusWarnAdmin, ref:"attrs/" + ZaGlobalConfig.A_zmailVirusWarnAdmin, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES},
	  	{ id:ZaGlobalConfig.A_zmailVirusWarnRecipient, ref:"attrs/" + ZaGlobalConfig.A_zmailVirusWarnRecipient, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES},
	  	//proxy
		{ id:ZaGlobalConfig.A_zmailImapProxyBindPort, ref:"attrs/" + ZaGlobalConfig.A_zmailImapProxyBindPort, type:_PORT_ },
		{ id:ZaGlobalConfig.A_zmailImapSSLProxyBindPort, ref:"attrs/" + ZaGlobalConfig.A_zmailImapSSLProxyBindPort, type:_PORT_ },
		{ id:ZaGlobalConfig.A_zmailPop3ProxyBindPort, ref:"attrs/" + ZaGlobalConfig.A_zmailPop3ProxyBindPort, type:_PORT_ },
		{ id:ZaGlobalConfig.A_zmailPop3SSLProxyBindPort, ref:"attrs/" + ZaGlobalConfig.A_zmailPop3SSLProxyBindPort, type:_PORT_ },
		{ id:ZaGlobalConfig.A_zmailLmtpBindPort, ref:"attrs/" + ZaGlobalConfig.A_zmailLmtpBindPort, type:_PORT_ },
		{ id:ZaGlobalConfig.A_zmailPop3NumThreads, ref:"attrs/" + ZaGlobalConfig.A_zmailPop3NumThreads, type:_INT_, minInclusive: 0, maxInclusive:2147483647 },		
		{ id:ZaGlobalConfig.A_zmailInstalledSkin, ref:"attrs/" + ZaGlobalConfig.A_zmailInstalledSkin, type:_LIST_, listItem:{type:_STRING_}},
        //spnego
        { id:ZaGlobalConfig.A_zmailSpnegoAuthEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailSpnegoAuthEnabled, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES },
        { id:ZaGlobalConfig.A_zmailSpnegoAuthRealm, ref:"attrs/" + ZaGlobalConfig.A_zmailSpnegoAuthRealm, type: _STRING_ },
        { id:ZaGlobalConfig.A_zmailSpnegoAuthErrorURL, ref:"attrs/" + ZaGlobalConfig.A_zmailSpnegoAuthErrorURL, type: _STRING_ },
        //web client
        { id:ZaGlobalConfig.A_zmailWebClientLoginURL, ref:"attrs/" + ZaGlobalConfig.A_zmailWebClientLoginURL, type:_STRING_, maxLength:255 },
        { id:ZaGlobalConfig.A_zmailWebClientLogoutURL, ref:"attrs/" + ZaGlobalConfig.A_zmailWebClientLogoutURL, type:_STRING_, maxLength:255 },
		{ id:ZaGlobalConfig.A_zmailWebClientLoginURLAllowedUA, ref:"attrs/" + ZaGlobalConfig.A_zmailWebClientLoginURLAllowedUA, type:_LIST_, listItem:{type:_STRING_}},
		{ id:ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedUA, ref:"attrs/" + ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedUA, type:_LIST_, listItem:{type:_STRING_}},
		{ id:ZaGlobalConfig.A_zmailWebClientLoginURLAllowedIP, ref:"attrs/" + ZaGlobalConfig.A_zmailWebClientLoginURLAllowedIP, type:_LIST_, listItem:{type:_STRING_}},
        { id:ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedIP, ref:"attrs/" + ZaGlobalConfig.A_zmailWebClientLogoutURLAllowedIP, type:_LIST_, listItem:{type:_STRING_}},
        // Clear Cookies
        {
            id: ZaGlobalConfig.A_zmailForceClearCookies,
            ref: "attrs/" + ZaGlobalConfig.A_zmailForceClearCookies,
            type: _ENUM_,
            choices: ZaModel.BOOLEAN_CHOICES
        },
        // web client authentication
        { id:ZaGlobalConfig.A_zmailMailSSLClientCertMode, ref:"attrs/" +  ZaGlobalConfig.A_zmailMailSSLClientCertMode, type:_STRING_, choices:["Disabled","NeedClientAuth","WantClientAuth"]},
        { id:ZaGlobalConfig.A_zmailMailSSLClientCertPort, ref:"attrs/" +  ZaGlobalConfig.A_zmailMailSSLClientCertPort, type:_PORT_},
        { id:ZaGlobalConfig.A_zmailMailSSLProxyClientCertPort, ref:"attrs/" +  ZaGlobalConfig.A_zmailMailSSLProxyClientCertPort, type:_PORT_},
        { id:ZaGlobalConfig.A_zmailReverseProxyMailMode, ref:"attrs/" +  ZaGlobalConfig.A_zmailReverseProxyMailMode, type:_STRING_, choices:["http","https","both","mixed","redirect"]},
        { id:ZaGlobalConfig.A_zmailReverseProxyClientCertMode, ref:"attrs/" +  ZaGlobalConfig.A_zmailReverseProxyClientCertMode, type:_STRING_, choices:["on","off","optional"]},
        { id:ZaGlobalConfig.A_zmailMailSSLClientCertPrincipalMap, ref:"attrs/" + ZaGlobalConfig.A_zmailMailSSLClientCertPrincipalMap, type:_STRING_ },
        { id:ZaGlobalConfig.A_zmailReverseProxyAdminIPAddress, ref:"attrs/" + ZaGlobalConfig.A_zmailReverseProxyAdminIPAddress, type:_LIST_, listItem:{type:_STRING_}},
        { id:ZaGlobalConfig.A_zmailReverseProxyClientCertCA, ref:"attrs/" + ZaGlobalConfig.A_zmailReverseProxyClientCertCA, type:_STRING_},
        //skin properties
        { id:ZaGlobalConfig.A_zmailSkinForegroundColor, ref:"attrs/" + ZaGlobalConfig.A_zmailSkinForegroundColor, type: _STRING_ },
        { id:ZaGlobalConfig.A_zmailSkinBackgroundColor, ref:"attrs/" + ZaGlobalConfig.A_zmailSkinBackgroundColor, type: _STRING_ },
        { id:ZaGlobalConfig.A_zmailSkinSecondaryColor, ref:"attrs/" + ZaGlobalConfig.A_zmailSkinSecondaryColor, type: _STRING_ },
        { id:ZaGlobalConfig.A_zmailSkinSelectionColor, ref:"attrs/" + ZaGlobalConfig.A_zmailSkinSelectionColor, type: _STRING_ },

        { id:ZaGlobalConfig.A_zmailSkinLogoURL, ref:"attrs/" + ZaGlobalConfig.A_zmailSkinLogoURL, type:_STRING_ },
        { id:ZaGlobalConfig.A_zmailSkinLogoLoginBanner, ref:"attrs/" + ZaGlobalConfig.A_zmailSkinLogoLoginBanner, type:_STRING_ },
        { id:ZaGlobalConfig.A_zmailSkinLogoAppBanner, ref:"attrs/" + ZaGlobalConfig.A_zmailSkinLogoAppBanner, type:_STRING_ },

        // auto provision
        { id:ZaGlobalConfig.A_zmailAutoProvBatchSize, type:_NUMBER_, ref:"attrs/" + ZaGlobalConfig.A_zmailAutoProvBatchSize, maxInclusive:2147483647, minInclusive:0},
        { id:ZaGlobalConfig.A_zmailAutoProvPollingInterval, ref:"attrs/" + ZaGlobalConfig.A_zmailAutoProvPollingInterval, type: _LIFETIME_NUMBER_, minInclusive: 0, fractionDigits: 0 },
        { id:ZaGlobalConfig.A_zmailAutoProvNotificationSubject, ref:"attrs/" + ZaGlobalConfig.A_zmailAutoProvNotificationSubject, type:_STRING_ },
        { id:ZaGlobalConfig.A_zmailAutoProvNotificationBody, ref:"attrs/" + ZaGlobalConfig.A_zmailAutoProvNotificationBody, type:_STRING_ },

	// help URL
        { id:ZaGlobalConfig.A_zmailHelpAdminURL, ref:"attrs/" + ZaGlobalConfig.A_zmailHelpAdminURL, type:_STRING_ },
        { id:ZaGlobalConfig.A_zmailHelpDelegatedURL, ref:"attrs/" + ZaGlobalConfig.A_zmailHelpDelegatedURL, type:_STRING_ },
         //interop
        { id:ZaGlobalConfig.A_zmailFreebusyExchangeAuthUsername, ref:"attrs/" + ZaGlobalConfig.A_zmailFreebusyExchangeAuthUsername, type: _STRING_ },
        { id:ZaGlobalConfig.A_zmailFreebusyExchangeAuthPassword, ref:"attrs/" + ZaGlobalConfig.A_zmailFreebusyExchangeAuthPassword, type: _STRING_ },
        { id:ZaGlobalConfig.A_zmailFreebusyExchangeAuthScheme, ref:"attrs/" + ZaGlobalConfig.A_zmailFreebusyExchangeAuthScheme,
            type: _ENUM_, choices: ZaSettings.authorizationScheme },
        { id:ZaGlobalConfig.A_zmailFreebusyExchangeServerType, ref:"attrs/" + ZaGlobalConfig.A_zmailFreebusyExchangeServerType,
            type: _ENUM_, choices: ZaSettings.exchangeServerType },
	{ id:ZaGlobalConfig.A_zmailFreebusyExchangeURL, ref:"attrs/" + ZaGlobalConfig.A_zmailFreebusyExchangeURL, type: _STRING_ },
        { id:ZaGlobalConfig.A_zmailFreebusyExchangeUserOrg, ref:"attrs/" + ZaGlobalConfig.A_zmailFreebusyExchangeUserOrg, type: _STRING_ },
        {id:ZaGlobalConfig.A2_blocked_extension_selection, type:_LIST_},
        {id:ZaGlobalConfig.A2_common_extension_selection, type:_LIST_},
        {id:ZaGlobalConfig.A2_retentionPoliciesKeep, type:_LIST_},
        {id:ZaGlobalConfig.A2_retentionPoliciesPurge, type:_LIST_},
        {id:ZaGlobalConfig.A2_retentionPoliciesKeep_Selection, type:_LIST_},
        {id:ZaGlobalConfig.A2_retentionPoliciesPurge_Selection, type:_LIST_}

    ]
}
