/**
 * This zimlet will add proxy config features to Zmail Admin Console.
 * It push additional XForm items and XModel items into the XForm Object
 * of global configs and server configs. There is no need to add custom
 * MVC code.
 *
 * @author jiankuan@zmail.com
 * @since  ZCS 8.0
 */

if (window.console && window.console.log) {
	console.log("Loading org_zmail_proxy_config.js");
}


if(ZaSettings && ZaSettings.EnabledZimlet["org_zmail_proxy_config"]) {

/*------------------------Model Section------------------------*/

// we only add tabs to global config and server xform view, therefore we need not to
// create ZaItem and ZaXFormView object. ZaProxyConfig is used for namespace. It's not
// a ZaItem like ZaCos or ZaAccount
ZaProxyConfig = function() {};

ZaProxyConfig.MAIL_MODE_CHOICES = ["http", "https", "both", "mixed", "redirect"];
ZaProxyConfig.CLIENT_CERT_MODE_CHOICES = ["off", "on", "optional"];
ZaProxyConfig.STARTTLS_MODE_CHOICES = [
	{label: org_zmail_proxy_config.LBL_ProxyStartTLSMode_OFF,  value: "off"},
	{label: org_zmail_proxy_config.LBL_ProxyStartTLSMode_ON,   value: "on"},
	{label: org_zmail_proxy_config.LBL_ProxyStartTLSMode_ONLY, value: "only"}
];
ZaProxyConfig.PROXY_LOG_LEVEL_CHOICES = ["crit", "error", "warn", "notice", "info", "debug_zmail", "debug_http", "debug_mail", "debug"];

// Proxy Advanced Configurations
ZaProxyConfig.A_zmailReverseProxyWorkerProcesses = "zmailReverseProxyWorkerProcesses";
ZaProxyConfig.A_zmailReverseProxyWorkerConnections = "zmailReverseProxyWorkerConnections";
ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled = "zmailReverseProxyDnsLookupInServerEnabled";
ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname = "zmailReverseProxyGenConfigPerVirtualHostname";
ZaProxyConfig.A_zmailReverseProxyAdminIPAddress = "zmailReverseProxyAdminIPAddress";
ZaProxyConfig.A_zmailReverseProxyLogLevel = "zmailReverseProxyLogLevel";
ZaProxyConfig.A_zmailReverseProxyUpstreamServers = "zmailReverseProxyUpstreamServers";
ZaProxyConfig.A_zmailReverseProxyAvailableLookupTargets = "zmailReverseProxyAvailableLookupTargets";

// Web Proxy Configurations
ZaProxyConfig.A_zmailReverseProxyHttpEnabled = "zmailReverseProxyHttpEnabled";
ZaProxyConfig.A_zmailReverseProxyMailMode = "zmailReverseProxyMailMode";
ZaProxyConfig.A_zmailMailProxyPort = "zmailMailProxyPort";
ZaProxyConfig.A_zmailMailSSLProxyPort = "zmailMailSSLProxyPort";
ZaProxyConfig.A_zmailMailSSLProxyClientCertPort = "zmailMailSSLProxyClientCertPort";
ZaProxyConfig.A_zmailReverseProxyClientCertMode = "zmailReverseProxyClientCertMode";
ZaProxyConfig.A_zmailReverseProxyAdminEnabled = "zmailReverseProxyAdminEnabled";
ZaProxyConfig.A_zmailAdminProxyPort = "zmailAdminProxyPort";
ZaProxyConfig.A_zmailAdminPort = "zmailAdminPort";
ZaProxyConfig.A_zmailMailPort = "zmailMailPort";
ZaProxyConfig.A_zmailMailSSLPort = "zmailMailSSLPort";
ZaProxyConfig.A_zmailMailMode = "zmailMailMode";
ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled = "zmailReverseProxySSLToUpstreamEnabled";

// Mail Proxy Configurations
ZaProxyConfig.A_zmailReverseProxyMailEnabled = "zmailReverseProxyMailEnabled";
ZaProxyConfig.A_zmailReverseProxyAuthWaitInterval = "zmailReverseProxyAuthWaitInterval";
ZaProxyConfig.A_zmailReverseProxyImapSaslPlainEnabled = "zmailReverseProxyImapSaslPlainEnabled";
ZaProxyConfig.A_zmailReverseProxyPop3SaslPlainEnabled = "zmailReverseProxyPop3SaslPlainEnabled";
ZaProxyConfig.A_zmailReverseProxyImapSaslGssapiEnabled = "zmailReverseProxyImapSaslGssapiEnabled";
ZaProxyConfig.A_zmailReverseProxyPop3SaslGssapiEnabled = "zmailReverseProxyPop3SaslGssapiEnabled";
ZaProxyConfig.A_zmailImapProxyBindPort = "zmailImapProxyBindPort";
ZaProxyConfig.A_zmailPop3ProxyBindPort = "zmailPop3ProxyBindPort";
ZaProxyConfig.A_zmailImapSSLProxyBindPort = "zmailImapSSLProxyBindPort";
ZaProxyConfig.A_zmailPop3SSLProxyBindPort = "zmailPop3SSLProxyBindPort";
ZaProxyConfig.A_zmailImapBindPort = "zmailImapBindPort";
ZaProxyConfig.A_zmailPop3BindPort = "zmailPop3BindPort";
ZaProxyConfig.A_zmailImapSSLBindPort = "zmailImapSSLBindPort";
ZaProxyConfig.A_zmailPop3SSLBindPort = "zmailPop3SSLBindPort";
ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode = "zmailReverseProxyImapStartTlsMode";
ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode = "zmailReverseProxyPop3StartTlsMode";
ZaProxyConfig.A_zmailImapCleartextLoginEnabled = "zmailImapCleartextLoginEnabled";
ZaProxyConfig.A_zmailPop3CleartextLoginEnabled = "zmailPop3CleartextLoginEnabled";

// other
ZaProxyConfig.A_zmailServiceEnabled = "zmailServiceEnabled";
ZaProxyConfig.A_zmailReverseProxyLookupTarget = "zmailReverseProxyLookupTarget";
ZaProxyConfig.A_zmailImapCleartextLoginEnabled = "zmailImapCleartextLoginEnabled";
ZaProxyConfig.A_zmailPop3CleartextLoginEnabled = "zmailPop3CleartextLoginEnabled";

// utility
ZaProxyConfig.A2_proxy_name_array = "proxy_name_array";
ZaProxyConfig.A2_mbx_name_array = "mbx_name_array";
ZaProxyConfig.A2_target_server = "proxy_target_server";
ZaProxyConfig.A2_target_up_servers = "proxy_target_up_servers";
ZaProxyConfig.A2_target_lt_servers = "proxy_target_lt_servers";
ZaProxyConfig.A2_current_server = "proxy_current_server"; // the server in whose UI the wizard is opened
ZaProxyConfig.A2_all_mailbox_as_upstream = "proxy_all_mailbox_as_upstream";
ZaProxyConfig.A2_all_mailbox_as_lookuptarget = "proxy_all_mailbox_as_lookuptarget";
ZaProxyConfig.A2_all_servers = "proxy_all_servers";

// Default Values
ZaProxyConfig.DEFAULT_MAIL_MODE = "http";
ZaProxyConfig.DEFAULT_MAIL_PORT = 80;
ZaProxyConfig.DEFAULT_MAIL_PORT_ZCS = 8080;
ZaProxyConfig.DEFAULT_MAIL_SSL_PORT = 443;
ZaProxyConfig.DEFAULT_MAIL_SSL_PORT_ZCS = 7443;
ZaProxyConfig.DEFAULT_ADMIN_CONSOLE_PORT = 9071;
ZaProxyConfig.DEFAULT_ADMIN_CONSOLE_PORT_ZCS = 7071;

ZaProxyConfig.DEFAULT_IMAP_PORT = 143;
ZaProxyConfig.DEFAULT_IMAP_PORT_ZCS = 7143;
ZaProxyConfig.DEFAULT_IMAP_SSL_PORT = 993;
ZaProxyConfig.DEFAULT_IMAP_SSL_PORT_ZCS = 7993;

ZaProxyConfig.DEFAULT_POP3_PORT = 110;
ZaProxyConfig.DEFAULT_POP3_PORT_ZCS = 7110;
ZaProxyConfig.DEFAULT_POP3_SSL_PORT = 900;
ZaProxyConfig.DEFAULT_POP3_SSL_PORT_ZCS = 7900;

//append model definitions to ZaGlobalConfig.myXModel
if (ZaGlobalConfig && ZaGlobalConfig.myXModel) {
	ZaGlobalConfig.myXModel.items.push(
		{id: ZaProxyConfig.A_zmailReverseProxyWorkerProcesses, type: _INT_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyWorkerProcesses, minInclusive: "1", maxInclusive: "16"},
		{id: ZaProxyConfig.A_zmailReverseProxyWorkerConnections, type: _INT_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyWorkerConnections, minInclusive: "1"},
		{id: ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyAdminIPAddress, type: _LIST_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyAdminIPAddress, listItem:{type: _HOSTNAME_OR_IP_, maxLength: 256} },
		{id: ZaProxyConfig.A_zmailReverseProxyLogLevel, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyLogLevel, choices: ZaProxyConfig.PROXY_LOG_LEVEL_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyHttpEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyHttpEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyMailMode, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyMailMode, choices: ZaProxyConfig.MAIL_MODE_CHOICES},
		{id: ZaProxyConfig.A_zmailMailProxyPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailMailProxyPort},
		{id: ZaProxyConfig.A_zmailMailSSLProxyPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailMailSSLProxyPort},
		{id: ZaProxyConfig.A_zmailMailSSLProxyClientCertPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailMailSSLProxyClientCertPort},
		{id: ZaProxyConfig.A_zmailReverseProxyClientCertMode, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyClientCertMode, choices: ZaProxyConfig.CLIENT_CERT_MODE_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyAdminEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyAdminEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailAdminProxyPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailAdminProxyPort},
		{id: ZaProxyConfig.A_zmailReverseProxyMailEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyMailEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyAuthWaitInterval, type: _LIFETIME_NUMBER_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyAuthWaitInterval, minInclusive: 0},
		{id: ZaProxyConfig.A_zmailReverseProxyImapSaslPlainEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyImapSaslPlainEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyPop3SaslPlainEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyPop3SaslPlainEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyImapSaslGssapiEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyImapSaslGssapiEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyPop3SaslGssapiEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyPop3SaslGssapiEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailImapProxyBindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailImapProxyBindPort},
		{id: ZaProxyConfig.A_zmailPop3ProxyBindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailPop3ProxyBindPort},
		{id: ZaProxyConfig.A_zmailImapSSLProxyBindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailImapSSLProxyBindPort},
		{id: ZaProxyConfig.A_zmailPop3SSLProxyBindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailPop3SSLProxyBindPort},
		{id: ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode, choices: ZaProxyConfig.STARTTLS_MODE_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode, choices: ZaProxyConfig.STARTTLS_MODE_CHOICES}
	);
}

//append model definitions to ZaServer.myXModel
if (ZaServer && ZaServer.myXModel) {
	ZaServer.myXModel.items.push(
		{id: ZaProxyConfig.A_zmailReverseProxyWorkerProcesses, type: _COS_INT_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyWorkerProcesses, minInclusive: "1", maxInclusive: "16"},
		{id: ZaProxyConfig.A_zmailReverseProxyWorkerConnections, type: _COS_INT_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyWorkerConnections, minInclusive: "1"},
		{id: ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyLogLevel, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyLogLevel, choices: ZaProxyConfig.PROXY_LOG_LEVEL_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyHttpEnabled, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyHttpEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyMailMode, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyMailMode, choices: ZaProxyConfig.MAIL_MODE_CHOICES},
		{id: ZaProxyConfig.A_zmailMailProxyPort, type: _COS_PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailMailProxyPort},
		{id: ZaProxyConfig.A_zmailMailSSLProxyPort, type: _COS_PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailMailSSLProxyPort},
		{id: ZaProxyConfig.A_zmailMailSSLProxyClientCertPort, type: _COS_PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailMailSSLProxyClientCertPort},
		{id: ZaProxyConfig.A_zmailReverseProxyClientCertMode, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyClientCertMode, choices: ZaProxyConfig.CLIENT_CERT_MODE_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyAdminEnabled, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyAdminEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailAdminProxyPort, type: _COS_PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailAdminProxyPort},
		{id: ZaProxyConfig.A_zmailReverseProxyMailEnabled, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyMailEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyImapSaslPlainEnabled, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyImapSaslPlainEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyPop3SaslPlainEnabled, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyPop3SaslPlainEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyImapSaslGssapiEnabled, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyImapSaslGssapiEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyPop3SaslGssapiEnabled, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyPop3SaslGssapiEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailImapProxyBindPort, type: _COS_PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailImapProxyBindPort},
		{id: ZaProxyConfig.A_zmailPop3ProxyBindPort, type: _COS_PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailPop3ProxyBindPort},
		{id: ZaProxyConfig.A_zmailImapSSLProxyBindPort, type: _COS_PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailImapSSLProxyBindPort},
		{id: ZaProxyConfig.A_zmailPop3SSLProxyBindPort, type: _COS_PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailPop3SSLProxyBindPort},
		{id: ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode, choices: ZaProxyConfig.STARTTLS_MODE_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode, type: _COS_ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode, choices: ZaProxyConfig.STARTTLS_MODE_CHOICES}
	);
}

/*---------------------Controller Section---------------------*/
ZaOperation.ENABLE_PROXY = ++ZA_OP_INDEX;

ZaProxyConfig.initPopupMenu = function () {
	
	if (!ZaProxyConfig.isProxyInstalledInAnyServer())
		return;
	
	// here "this" is controller
	this._popupOperations[ZaOperation.ENABLE_PROXY] = (new ZaOperation(ZaOperation.ENABLE_PROXY, "Enable Proxy", "Enable Proxy",
                                           "Deploy", "Deploy", new AjxListener(this, ZaProxyConfig._enableProxyBtnListener)));
	
	this._popupOperations[ZaOperation.DISABLE_PROXY] = (new ZaOperation(ZaOperation.ENABLE_PROXY, "Disable Proxy", "Disable Proxy",
            "Undeploy", "Undeploy", new AjxListener(this, ZaProxyConfig._disableProxyBtnListener)));
}

if(ZaController.initPopupMenuMethods["ZaServerController"]) {
	ZaController.initPopupMenuMethods["ZaServerController"].push(ZaProxyConfig.initPopupMenu);	
}

if(ZaController.initPopupMenuMethods["ZaServerListController"]) {
	ZaController.initPopupMenuMethods["ZaServerListController"].push(ZaProxyConfig.initPopupMenu);	
}

ZaProxyConfig._enableProxyBtnListener = function (ev) {
	try {
		var ep = new ZaEnableProxy();
		
		if(!this._enableProxyWiz) {
			this._enableProxyWiz = new ZaEnableProxyWizard(this._container, ep);
		}
		
		if(this._currentObject) {
			ep[ZaProxyConfig.A2_current_server] = this._currentObject["zmailServiceHostname"]; 
		}
		
		this._enableProxyWiz.setObject(ep);
		this._enableProxyWiz.popup();
	} catch (ex) {
		this._handleException(ex, "ZaProxyConfig._enableProxyBtnListener", null, false);
	}
}

ZaProxyConfig._disableProxyBtnListener = function (ev) {
	try {
		var dp = new ZaDisableProxy();
		
		if(!this._disableProxyWiz) {
			this._disableProxyWiz = new ZaDisableProxyWizard(this._container, dp);
		}
		
		if (this._currentObject) {
			dp[ZaProxyConfig.A2_current_server] = this._currentObject["zmailServiceHostname"];
		}
		
		this._disableProxyWiz.setObject(dp);
		this._disableProxyWiz.popup();
	} catch (ex) {
		this._handleException(ex, "ZaProxyConfig._disableProxyBtnListener", null, false);
	}
}

ZaProxyConfig.isProxyInstalledInAnyServer = function() {
	if (ZaProxyConfig._proxy_installed) {
		return ZaProxyConfig._proxy_installed;
	}
	var servers = ZaServer.getAll().getArray();
	for (var i = 0; i < servers.length; i++) {
		var s = servers[i];
		if (s.attrs[ZaServer.A_zmailMailProxyServiceInstalled]) {
			ZaProxyConfig._proxy_installed = true;
			break;
		}
	}
	
	if (!ZaProxyConfig._proxy_installed) {
		ZaProxyConfig._proxy_installed = false;
	}
	// TODO: maybe should set a timer to clear this cached result
	
	return ZaProxyConfig._proxy_installed;
}

ZaProxyConfig.isProxyWizardEnabled = function(obj, attrsArray, rightsArray) {
	
	if(!obj)
		return true;
	
	if(AjxUtil.isEmpty(attrsArray) && AjxUtil.isEmpty(rightsArray))
		return true;
		
	if(!AjxUtil.isEmpty(attrsArray)) {
		var cntAttrs = attrsArray.length;
		for(var i=0; i< cntAttrs; i++) {
			if(ZaItem.hasWritePermission(attrsArray[i], obj)) {
				return true;
			}
		}
	} 
	
	if(!AjxUtil.isEmpty(rightsArray)) {
		var cntRights = rightsArray.length;
		for(var i=0; i< cntRights; i++) {
			if(ZaItem.hasRight(rightsArray[i], obj)) {
				return true;
			}
		}
	}
}

ZaProxyConfig.changeActionsStateMethod = function () {
    var obj = this._currentObject; // here "this" is ZaServerController

	var attrsArray = ZaProxyConfig.ENABLE_PROXY_ATTRS;
	var rightsArray = ZaProxyConfig.ENABLE_PROXY_RIGHTS;
	
	// check to enable "Enable Proxy Wizard" or not
	var isToEnable = ZaProxyConfig.isProxyWizardEnabled(obj, attrsArray, rightsArray);

	if(this._popupOperations[ZaOperation.ENABLE_PROXY]) {
        this._popupOperations[ZaOperation.ENABLE_PROXY].enabled = isToEnable;
    }
	
	// check to enable "Disable Proxy Wizard" or not
	isToEnable = ZaProxyConfig.isProxyWizardEnabled(obj, attrsArray, rightsArray);
	
	if(this._popupOperations[ZaOperation.DISABLE_PROXY]) {
        this._popupOperations[ZaOperation.DISABLE_PROXY].enabled = isToEnable;
    }
}
ZaController.changeActionsStateMethods["ZaServerController"].push(ZaProxyConfig.changeActionsStateMethod);
	
/*------------------------View Section------------------------*/

/** enable/disable checks */
ZaProxyConfig.isWebProxyEnabled = function() {
	var webProxyEnabled = this.getInstanceValue(ZaProxyConfig.A_zmailReverseProxyHttpEnabled);
	return (webProxyEnabled == "TRUE");
}

ZaProxyConfig.isAdminProxyEnabled = function() {
	if (!ZaProxyConfig.isWebProxyEnabled.call(this)) { return false;}
	var adminProxyEnabled = this.getInstanceValue(ZaProxyConfig.A_zmailReverseProxyAdminEnabled);
	return (adminProxyEnabled == "TRUE");
}

ZaProxyConfig.isClientCertAuthEnabled = function() {
	if (!ZaProxyConfig.isWebProxyEnabled.call(this)) { return false;}
	var clientCertAuthEnabled = this.getInstanceValue(ZaProxyConfig.A_zmailReverseProxyClientCertMode);
	return (clientCertAuthEnabled == "on" || clientCertAuthEnabled == "optional");
}

ZaProxyConfig.isMailProxyEnabled = function() {
	var mailProxyEnabled = this.getInstanceValue(ZaProxyConfig.A_zmailReverseProxyMailEnabled);
	return (mailProxyEnabled == "TRUE");
}

/** attrs for delegate admin */
ZaProxyConfig.PROXY_CONFIG_GENERAL_ATTRS = [
	ZaProxyConfig.A_zmailReverseProxyWorkerProcesses,
	ZaProxyConfig.A_zmailReverseProxyWorkerConnections,
	ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled,
	ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled,
	ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname
];

// zmailReverseProxyAdminIPAddress is global only attributes
ZaProxyConfig.GLOBAL_PROXY_CONFIG_GENERAL_ATTRS =
	ZaProxyConfig.PROXY_CONFIG_GENERAL_ATTRS.push (
			ZaProxyConfig.A_zmailReverseProxyAdminIPAddress);

	
ZaProxyConfig.PROXY_CONFIG_WEB_PROXY_ATTRS = [
	ZaProxyConfig.A_zmailReverseProxyHttpEnabled,
	ZaProxyConfig.A_zmailReverseProxyMailMode,
	ZaProxyConfig.A_zmailMailProxyPort,
	ZaProxyConfig.A_zmailMailSSLProxyPort,
	//ZaProxyConfig.A_zmailMailSSLProxyClientCertPort,
	//ZaProxyConfig.A_zmailReverseProxyClientCertMode, bug 71233
	ZaProxyConfig.A_zmailReverseProxyAdminEnabled,
	ZaProxyConfig.A_zmailAdminProxyPort
];

ZaProxyConfig.PROXY_CONFIG_MAIL_PROXY_ATTRS = [
	ZaProxyConfig.A_zmailReverseProxyMailEnabled,
	ZaProxyConfig.A_zmailReverseProxyImapSaslPlainEnabled,
	ZaProxyConfig.A_zmailReverseProxyPop3SaslPlainEnabled,
	ZaProxyConfig.A_zmailReverseProxyImapSaslGssapiEnabled,
	ZaProxyConfig.A_zmailReverseProxyPop3SaslGssapiEnabled,
	ZaProxyConfig.A_zmailImapProxyBindPort,
	ZaProxyConfig.A_zmailPop3ProxyBindPort,
	ZaProxyConfig.A_zmailImapSSLProxyBindPort,
	ZaProxyConfig.A_zmailPop3SSLProxyBindPort,
	ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode,
	ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode
]

//ZaProxyConfig.A_zmailReverseProxyAuthWaitInterval is global only attribute
ZaProxyConfig.GLOBAL_PROXY_CONFIG_MAIL_PROXY_ATTRS =
	ZaProxyConfig.PROXY_CONFIG_MAIL_PROXY_ATTRS.push(
			ZaProxyConfig.A_zmailReverseProxyAuthWaitInterval);

ZaProxyConfig.GLOBAL_PROXY_CONFIG_TAB_ATTRS = [].concat(
		ZaProxyConfig.GLOBAL_PROXY_CONFIG_GENERAL_ATTRS,
		ZaProxyConfig.PROXY_CONFIG_WEB_PROXY_ATTRS,
		ZaProxyConfig.GLOBAL_PROXY_CONFIG_MAIL_PROXY_ATTRS);

ZaProxyConfig.SERVER_PROXY_CONFIG_GLOBAL_TAB_ATTRS = [].concat(
		ZaProxyConfig.PROXY_CONFIG_GENERAL_ATTRS,
		ZaProxyConfig.PROXY_CONFIG_WEB_PROXY_ATTRS,
		ZaProxyConfig.PROXY_CONFIG_MAIL_PROXY_ATTRS);

ZaProxyConfig.GLOBAL_PROXY_CONFIG_TAB_RIGHTS = [];
ZaProxyConfig.SERVER_PROXY_CONFIG_TAB_RIGHTS = [];

ZaProxyConfig.ENABLE_PROXY_ATTRS = [
    	ZaProxyConfig.A_zmailReverseProxyHttpEnabled,
    	ZaProxyConfig.A_zmailReverseProxyMailMode,
    	ZaProxyConfig.A_zmailMailMode,
    	ZaProxyConfig.A_zmailMailPort,
    	ZaProxyConfig.A_zmailMailProxyPort,
    	ZaProxyConfig.A_zmailMailSSLPort,
    	ZaProxyConfig.A_zmailMailSSLProxyPort,
    	ZaProxyConfig.A_zmailReverseProxyAdminEnabled,
    	ZaProxyConfig.A_zmailAdminProxyPort,
    	ZaProxyConfig.A_zmailReverseProxyMailEnabled,
    	ZaProxyConfig.A_zmailImapProxyBindPort,
    	ZaProxyConfig.A_zmailImapBindPort,
    	ZaProxyConfig.A_zmailPop3ProxyBindPort,
    	ZaProxyConfig.A_zmailPop3BindPort,
    	ZaProxyConfig.A_zmailImapSSLProxyBindPort,
    	ZaProxyConfig.A_zmailImapSSLBindPort,
    	ZaProxyConfig.A_zmailPop3SSLProxyBindPort,
    	ZaProxyConfig.A_zmailPop3SSLBindPort,
    	ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode,
    	ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode,
        ZaProxyConfig.A_zmailReverseProxyWorkerProcesses,
        ZaProxyConfig.A_zmailReverseProxyWorkerConnections,
        ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled,
        ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled,
        ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname,
        ZaProxyConfig.A_zmailReverseProxyLookupTarget,
        ZaProxyConfig.A_zmailReverseProxyUpstreamServers,
        ZaProxyConfig.A_zmailReverseProxyAvailableLookupTargets
];

ZaProxyConfig.ENABLE_PROXY_RIGHTS = [
        "listServer"
];

/** global level proxy config modifer */
ZaProxyConfig.myGlobalXFormModifier = function(xFormObject, entry) {
	
	if (!ZaProxyConfig.isProxyInstalledInAnyServer()) // don't show anything about proxy if proxy is not installed at all
		return;

	if (ZaTabView.isTAB_ENABLED(entry, ZaProxyConfig.GLOBAL_PROXY_CONFIG_TAB_ATTRS,
									   ZaProxyConfig.GLOBAL_PROXY_CONFIG_TAB_RIGHTS)) {
		var proxyConfigCaseKey = ++this.TAB_INDEX;
		var proxyConfigCase = {
			type: _ZATABCASE_, caseKey: proxyConfigCaseKey,
			colSizes: ["auto"], numCols: 1, paddingStyle: "padding-left:15px;", width: "98%",
			items: [
				{type: _ZA_TOP_GROUPER_, numCols:2, colSizes: ["275px", "auto"],
				 label: org_zmail_proxy_config.LBL_ProxyWebProxyConfig,
				 visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
				                    ZaProxyConfig.PROXY_CONFIG_WEB_PROXY_ATTRS]],
				 items: [
					{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyEnableWebProxy,
					 ref: ZaProxyConfig.A_zmailReverseProxyHttpEnabled,
					 trueValue: "TRUE", falseValue: "FALSE"
					},
					{type: _SELECT1_, label: org_zmail_proxy_config.LBL_ProxyWebProxyMode,
					 ref: ZaProxyConfig.A_zmailReverseProxyMailMode,
					 width: "60px",
					 enableDisableChecks: [ZaProxyConfig.isWebProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled]
					},
					{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyUseSSLToUpstream,
					 ref: ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled,
					 trueValue: "TRUE", falseValue: "FALSE"
					},
					{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyHttpProxyPort,
					 ref: ZaProxyConfig.A_zmailMailProxyPort,
					 width: "60px",
					 enableDisableChecks: [ZaProxyConfig.isWebProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled]
					},
					{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyHttpSSLProxyPort,
					 ref: ZaProxyConfig.A_zmailMailSSLProxyPort,
					 width: "60px",
					 enableDisableChecks: [ZaProxyConfig.isWebProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled]
					},
					{type: _SPACER_, height: 10},
					{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyAdminEnabled,
					 ref: ZaProxyConfig.A_zmailReverseProxyAdminEnabled,
					 trueValue: "TRUE", falseValue: "FALSE",
					 enableDisableChecks: [ZaProxyConfig.isWebProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled]
					},
					{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyAdminProxyPort,
					 ref: ZaProxyConfig.A_zmailAdminProxyPort,
					 width: "60px",
					 enableDisableChecks: [ZaProxyConfig.isAdminProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled,
					                                   ZaProxyConfig.A_zmailReverseProxyAdminEnabled]
					}/*,
					  ---bug 71233, temporarily remove 2-way SSL features from UI---
					 {type: _SPACER_, height: 10},
					{type: _SELECT1_, label: org_zmail_proxy_config.LBL_ProxyClientCertAuthMode,
					 ref: ZaProxyConfig.A_zmailReverseProxyClientCertMode,
					 width: "60px",
					 enableDisableChecks: [ZaProxyConfig.isWebProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled]
					},
					{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyClientCertAuthPort,
					 ref: ZaProxyConfig.A_zmailMailSSLProxyClientCertPort,
					 width: "60px",
					 enableDisableChecks: [ZaProxyConfig.isClientCertAuthEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled,
					                                   ZaProxyConfig.A_zmailReverseProxyClientCertMode]
					} */
				 ]
				},
				{type: _ZA_TOP_GROUPER_, numCols:2, colSizes: ["275px", "auto"],
				 label: org_zmail_proxy_config.LBL_ProxyMailProxyConfig,
				 visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
				                    ZaProxyConfig.GLOBAL_PROXY_CONFIG_MAIL_PROXY_ATTRS]],
				 items: [
					{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyEnableMailProxy,
					 ref: ZaProxyConfig.A_zmailReverseProxyMailEnabled,
					 trueValue: "TRUE", falseValue: "FALSE"
					},
					{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyAuthWaitTime,
					 ref: ZaProxyConfig.A_zmailReverseProxyAuthWaitInterval,
					 width: "60px",
					 getDisplayValue: function(value) {return parseInt(value);}, // only assume the number is in seconds
					 elementChanged: function(elementValue, instanceValue, event) {
						 instanceValue = elementValue + "s";
						 this.getForm().itemChanged(this, instanceValue, event);
					 },
					 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
					},
					{type: _SPACER_, height: 10},
					{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyImapProxyPort,
					 ref: ZaProxyConfig.A_zmailImapProxyBindPort,
					 width: "60px",
					 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
					},
					{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyImapSSLProxyPort,
					 ref: ZaProxyConfig.A_zmailImapSSLProxyBindPort,
					 width: "60px",
					 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
					},
					{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyImapEnablePlainAuth,
					 ref: ZaProxyConfig.A_zmailReverseProxyImapSaslPlainEnabled,
					 trueValue: "TRUE", falseValue: "FALSE",
					 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
					},
					{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyImapEnableGssapiAuth,
					 ref: ZaProxyConfig.A_zmailReverseProxyImapSaslGssapiEnabled,
					 trueValue: "TRUE", falseValue: "FALSE",
					 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
					},
					{type: _SELECT1_, label: org_zmail_proxy_config.LBL_ProxyImapStartTlsMode,
					 ref: ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode,
					 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
					},
					{type: _SPACER_, height: 10},
					{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyPop3ProxyPort,
					 ref: ZaProxyConfig.A_zmailPop3ProxyBindPort,
					 width: "60px",
					 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
					},
					{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyPop3SSLProxyPort,
					 ref: ZaProxyConfig.A_zmailPop3SSLProxyBindPort,
					 width: "60px",
					 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
					},
					{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyPop3EnablePlainAuth,
					 ref: ZaProxyConfig.A_zmailReverseProxyPop3SaslPlainEnabled,
					 trueValue: "TRUE", falseValue: "FALSE",
					 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
					},
					{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyPop3EnableGssapiAuth,
					 ref: ZaProxyConfig.A_zmailReverseProxyPop3SaslGssapiEnabled,
					 trueValue: "TRUE", falseValue: "FALSE",
					 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
					},
					{type: _SELECT1_, label: org_zmail_proxy_config.LBL_ProxyPop3StartTlsMode,
					 ref: ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode,
					 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
					 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
					}
				 ]
				}, // _ZA_TOP_GROUPER_
				{type: _ZA_TOP_GROUPER_, numCols:2, colSizes: ["275px", "auto"],
				 label: org_zmail_proxy_config.LBL_ProxyGeneralConfig,
				 visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
				                    ZaProxyConfig.GLOBAL_PROXY_CONFIG_GENERAL_ATTRS]],
				 items: [
					{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyWorkerProcessNum,
					 ref: ZaProxyConfig.A_zmailReverseProxyWorkerProcesses,
					 width: "60px"
					},
					{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyWorkerConnectionNum,
					 ref: ZaProxyConfig.A_zmailReverseProxyWorkerConnections,
					 width: "60px"
					},
					{type: _SELECT1_, label: org_zmail_proxy_config.LBL_ProxyLogLevel,
					 ref: ZaProxyConfig.A_zmailReverseProxyLogLevel
					},
					{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyAllowServerResolveRoute,
					 ref: ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled,
					 trueValue: "TRUE", falseValue: "FALSE"
					},
					{type: _DWT_ALERT_, style: DwtAlert.INFO, iconVisible: true, colSpan: "*",
					 content: org_zmail_proxy_config.MSG_GenConfigPerVirtualHostname,
					 // this is to make sure the alert will always apply the below item's visibility
					 visibilityChecks: [[ZaItem.hasReadPermission,
					                     ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname]]
					},
					{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyGenConfigPerVirtualHostname,
					 ref: ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname,
					 trueValue: "TRUE", falseValue: "FALSE"
					},
					{type: _SPACER_, height: 10},
					{type: _DWT_ALERT_, style: DwtAlert.INFO, iconVisible: true, colSpan: "*",
					 content: org_zmail_proxy_config.MSG_ReverseProxyAdminIPAddress,
					 // this is to make sure the alert will always apply the below item's visibility
					 visibilityChecks: [[ZaItem.hasReadPermission,
					                     ZaProxyConfig.A_zmailReverseProxyAdminIPAddress]]
					},
					{type: _REPEAT_, label: org_zmail_proxy_config.LBL_ProxyAdminIPAddresses,
					 ref: ZaProxyConfig.A_zmailReverseProxyAdminIPAddress,
					 repeatInstance:"", labelWrap: true ,
					 showAddButton: true, showRemoveButton: true, showAddOnNextRow: true,
					 items: [
					 	{ref: ".", type: _TEXTFIELD_, label: null}
					 ]
					}
				 ]
				} // _ZA_TOP_GROUPER_
			]
		};

		//items[1] of global config XForm object is a _TAB_BAR_, see GlobalConfigXFormView.js
		var tabBarChoices = xFormObject.items[1].choices;
		tabBarChoices.push({value: proxyConfigCaseKey, label: org_zmail_proxy_config.OVT_Proxy});

		// items[2] of global config XForm is a _SWITCH_, see GlobalConfigXFormView.js
		var switchItems = xFormObject.items[2].items;
		switchItems.push(proxyConfigCase);
	}
}

ZaTabView.XFormModifiers["GlobalConfigXFormView"].push(ZaProxyConfig.myGlobalXFormModifier);


/** server level visibility check functions */
ZaProxyConfig.isProxyInstalled = function() {
	return XForm.checkInstanceValue.call(this, ZaServer.A_zmailMailProxyServiceInstalled, true);
}

ZaProxyConfig.isProxyEnabled = function() {
	return XForm.checkInstanceValue.call(this, ZaServer.A_zmailMailProxyServiceEnabled, true);
}

ZaProxyConfig.isProxyInstalledAndEnabled = function() {
	return (ZaProxyConfig.isProxyInstalled.call(this) && ZaProxyConfig.isProxyEnabled.call(this));
}

ZaProxyConfig.isProxyNotInstalledOrEnabled = function() {
	return !ZaProxyConfig.isProxyInstalledAndEnabled.call(this);
}

/** server level proxy config modifer */
ZaProxyConfig.myServerXFormModifier = function(xFormObject, entry) {
	
	if (!ZaProxyConfig.isProxyInstalledInAnyServer()) // don't show anything about proxy if proxy is not installed at all
		return;

	if (ZaTabView.isTAB_ENABLED(entry, ZaProxyConfig.SERVER_PROXY_CONFIG_TAB_ATTRS,
									   ZaProxyConfig.SERVER_PROXY_CONFIG_TAB_RIGHTS)) {
		var proxyConfigCaseKey = ++this.TAB_INDEX;
		var proxyConfigCase = {
			type: _ZATABCASE_, caseKey: proxyConfigCaseKey,
			colSizes: ["auto"], numCols: 1, paddingStyle: "padding-left:15px;", width: "98%",
			items: [
			    {type: _GROUP_, numCols: 1,
			     visibilityChecks: [ZaProxyConfig.isProxyInstalledAndEnabled],
			     visibilityChangeEventSources: [ZaServer.A_zmailMailProxyServiceInstalled,
			                                    ZaServer.A_zmailMailProxyServiceEnabled],
			     items: [
					{type: _ZA_TOP_GROUPER_, numCols:2, colSizes: ["275px", "*"],
					 label: org_zmail_proxy_config.LBL_ProxyWebProxyConfig,
					 visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
					                    ZaProxyConfig.PROXY_CONFIG_WEB_PROXY_ATTRS]],
					 items: [
						{type: _SUPER_CHECKBOX_, checkBoxLabel: org_zmail_proxy_config.LBL_ProxyEnableWebProxy,
						 ref: ZaProxyConfig.A_zmailReverseProxyHttpEnabled,
						 trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
	
						 onChange: ZaServerXFormView.onFormFieldChanged
						},
						{type: _SUPER_SELECT1_, label: org_zmail_proxy_config.LBL_ProxyWebProxyMode,
						 ref: ZaProxyConfig.A_zmailReverseProxyMailMode,
						 colSpan: "2", // the colSpan here and below are to fix the problem brought by 2 kinds of super control implementation.
						 resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isWebProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled]
						},
						{type: _SUPER_CHECKBOX_, checkBoxLabel: org_zmail_proxy_config.LBL_ProxyUseSSLToUpstream,
						 ref: ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled, colSpan: "3",
						 trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
						 onChange: ZaServerXFormView.onFormFieldChanged
						},
						{type: _SUPER_TEXTFIELD_, txtBoxLabel: org_zmail_proxy_config.LBL_ProxyHttpProxyPort,
						 ref: ZaProxyConfig.A_zmailMailProxyPort,
						 textFieldWidth: "60px", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal, colSpan: "3",
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isWebProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled]
						},
						{type: _SUPER_TEXTFIELD_, txtBoxLabel: org_zmail_proxy_config.LBL_ProxyHttpSSLProxyPort,
						 ref: ZaProxyConfig.A_zmailMailSSLProxyPort,
						 textFieldWidth: "60px", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal, colSpan: "3",
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isWebProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled]
						},
						{type: _SPACER_, height: 10, colSpan: "3"},
						{type: _SUPER_CHECKBOX_, checkBoxLabel: org_zmail_proxy_config.LBL_ProxyAdminEnabled,
						 ref: ZaProxyConfig.A_zmailReverseProxyAdminEnabled,
						 trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
						 colSpan: "3",
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isWebProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled]
						},
						{type: _SUPER_TEXTFIELD_, txtBoxLabel: org_zmail_proxy_config.LBL_ProxyAdminProxyPort,
						 ref: ZaProxyConfig.A_zmailAdminProxyPort,
						 textFieldWidth: "60px", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal, colSpan: "3",
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isAdminProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled,
						                                   ZaProxyConfig.A_zmailReverseProxyAdminEnabled]
						}/*,
						  ---bug 71233, temporarily remove 2-way SSL features from UI---
						 {type: _SPACER_, height: 10},
						{type: _SELECT1_, label: org_zmail_proxy_config.LBL_ProxyClientCertAuthMode,
						 ref: ZaProxyConfig.A_zmailReverseProxyClientCertMode,
						 width: "60px",
						 enableDisableChecks: [ZaProxyConfig.isWebProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled]
						},
						{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyClientCertAuthPort,
						 ref: ZaProxyConfig.A_zmailMailSSLProxyClientCertPort,
						 width: "60px",
						 enableDisableChecks: [ZaProxyConfig.isClientCertAuthEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled,
						                                   ZaProxyConfig.A_zmailReverseProxyClientCertMode]
						} */
					 ]
					},
					{type: _ZA_TOP_GROUPER_, numCols:2, colSizes: ["275px", "*"],
					 label: org_zmail_proxy_config.LBL_ProxyMailProxyConfig,
					 visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
					                    ZaProxyConfig.PROXY_CONFIG_MAIL_PROXY_ATTRS]],
					 items: [
						{type: _SUPER_CHECKBOX_, checkBoxLabel: org_zmail_proxy_config.LBL_ProxyEnableMailProxy,
						 ref: ZaProxyConfig.A_zmailReverseProxyMailEnabled,
						 trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
						 colSpan: "3",  // the colSpan here and below are to fix the problem brought by 2 kinds of super control implementation.
						 onChange: ZaServerXFormView.onFormFieldChanged
						},
						{type: _SPACER_, height: 10, colSpan: 3},
						{type: _SUPER_TEXTFIELD_, txtBoxLabel: org_zmail_proxy_config.LBL_ProxyImapProxyPort,
						 ref: ZaProxyConfig.A_zmailImapProxyBindPort,
						 textFieldWidth: "60px", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal, colSpan: "3",
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
						},
						{type: _SUPER_TEXTFIELD_, txtBoxLabel: org_zmail_proxy_config.LBL_ProxyImapSSLProxyPort,
						 ref: ZaProxyConfig.A_zmailImapSSLProxyBindPort,
						 textFieldWidth: "60px", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal, colSpan: "3",
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
						},
						{type: _SUPER_CHECKBOX_, checkBoxLabel: org_zmail_proxy_config.LBL_ProxyImapEnablePlainAuth,
						 ref: ZaProxyConfig.A_zmailReverseProxyImapSaslPlainEnabled,
						 trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
						 colSpan: "3",
						 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
						},
						{type: _SUPER_CHECKBOX_, checkBoxLabel: org_zmail_proxy_config.LBL_ProxyImapEnableGssapiAuth,
						 ref: ZaProxyConfig.A_zmailReverseProxyImapSaslGssapiEnabled,
						 trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
						 colSpan: "3",
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
						},
						{type: _SUPER_SELECT1_, label: org_zmail_proxy_config.LBL_ProxyImapStartTlsMode,
						 ref: ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode,
						 resetToSuperLabel:ZaMsg.NAD_ResetToGlobal, colSpan: "2",
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
						},
						{type: _SPACER_, height: 10, colSpan: "3"},
						{type: _SUPER_TEXTFIELD_, txtBoxLabel: org_zmail_proxy_config.LBL_ProxyPop3ProxyPort,
						 ref: ZaProxyConfig.A_zmailPop3ProxyBindPort,
						 textFieldWidth: "60px", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal, colSpan: "3",
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
						},
						{type: _SUPER_TEXTFIELD_, txtBoxLabel: org_zmail_proxy_config.LBL_ProxyPop3SSLProxyPort,
						 ref: ZaProxyConfig.A_zmailPop3SSLProxyBindPort,
						 textFieldWidth: "60px", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal, colSpan: "3",
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
						},
						{type: _SUPER_CHECKBOX_, checkBoxLabel: org_zmail_proxy_config.LBL_ProxyPop3EnablePlainAuth,
						 ref: ZaProxyConfig.A_zmailReverseProxyPop3SaslPlainEnabled,
						 trueValue: "TRUE", falseValue: "FALSE", colSpan: "3",
						 resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
						},
						{type: _SUPER_CHECKBOX_, checkBoxLabel: org_zmail_proxy_config.LBL_ProxyPop3EnableGssapiAuth,
						 ref: ZaProxyConfig.A_zmailReverseProxyPop3SaslGssapiEnabled,
						 trueValue: "TRUE", falseValue: "FALSE", colSpan: "3",
						 resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
						},
						{type: _SUPER_SELECT1_, label: org_zmail_proxy_config.LBL_ProxyPop3StartTlsMode,
						 ref: ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode,
						 resetToSuperLabel:ZaMsg.NAD_ResetToGlobal, colSpan: "2",
						 onChange: ZaServerXFormView.onFormFieldChanged,
						 enableDisableChecks: [ZaProxyConfig.isMailProxyEnabled],
						 enableDisableChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled]
						}
					 ]
					},
					{type: _ZA_TOP_GROUPER_, numCols:2, colSizes: ["275px", "*"],
					 label: org_zmail_proxy_config.LBL_ProxyGeneralConfig,
					 visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
					                    ZaProxyConfig.PROXY_CONFIG_GENERAL_ATTRS]],
					 items: [
						{type: _SUPER_TEXTFIELD_, txtBoxLabel: org_zmail_proxy_config.LBL_ProxyWorkerProcessNum,
						 ref: ZaProxyConfig.A_zmailReverseProxyWorkerProcesses,
						 textFieldWidth: "60px", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal, colSpan: "3",
						 onChange: ZaServerXFormView.onFormFieldChanged
						},
						{type: _SUPER_TEXTFIELD_, txtBoxLabel: org_zmail_proxy_config.LBL_ProxyWorkerConnectionNum,
						 ref: ZaProxyConfig.A_zmailReverseProxyWorkerConnections,
						 textFieldWidth: "60px", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal, colSpan: "3",
						 onChange: ZaServerXFormView.onFormFieldChanged
						},
						{type: _SUPER_SELECT1_, label: org_zmail_proxy_config.LBL_ProxyLogLevel,
						 ref: ZaProxyConfig.A_zmailReverseProxyLogLevel,
						 resetToSuperLabel: ZaMsg.NAD_ResetToGlobal, colSpan: "2",
						 onChange: ZaServerXFormView.onFormFieldChanged
						},
						{type: _SUPER_CHECKBOX_, checkBoxLabel: org_zmail_proxy_config.LBL_ProxyAllowServerResolveRoute,
						 ref: ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled,
						 trueValue: "TRUE", falseValue: "FALSE",
						 resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
					     onChange: ZaServerXFormView.onFormFieldChanged
						},
						{type: _DWT_ALERT_, style: DwtAlert.INFO, iconVisible: true, colSpan: "3",
						 content: org_zmail_proxy_config.MSG_GenConfigPerVirtualHostname,
						 // this is to make sure the alert will always apply the below item's visibility
						 visibilityChecks: [[ZaItem.hasReadPermission,
						                     ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname]]
						},
						{type: _SUPER_CHECKBOX_, checkBoxLabel: org_zmail_proxy_config.LBL_ProxyGenConfigPerVirtualHostname,
						 ref: ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname, colSpan: "3",
						trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
						 onChange: ZaServerXFormView.onFormFieldChanged
						}
					 ]
					}// _ZA_TOP_GROUPER_
				 ]
				}, // _GROUP_
				{type: _GROUP_, numCols: 1, colSize: ["*"], colSpan: "*",
				 items: [
				    {type: _DWT_ALERT_, style: DwtAlert.WARNING, iconVisible: true, colSpan: "*",
				     content: org_zmail_proxy_config.MSG_NeedProxyInstalledAndEnabled}
				 ],
				 visibilityChecks: [ZaProxyConfig.isProxyNotInstalledOrEnabled],
				 visibilityChangeEventSources: [ZaServer.A_zmailMailProxyServiceInstalled,
				                                ZaServer.A_zmailMailProxyServiceEnabled]
				}
			]
		}; // _ZATABCASE_

		// items[1] of server XForm object is a _TAB_BAR_, see ZaServerConfigXFormView.js
		var tabBarChoices = xFormObject.items[1].choices;
		tabBarChoices.push({value: proxyConfigCaseKey, label: org_zmail_proxy_config.OVT_Proxy});
		
		// items[2] of server XForm is a _SWITCH_, see ZaServerFormView.js
		var switchItems = xFormObject.items[2].items;
		switchItems.push(proxyConfigCase);
	}
}

ZaTabView.XFormModifiers["ZaServerXFormView"].push(ZaProxyConfig.myServerXFormModifier);

} // if(ZaSettings && ZaSettings.EnabledZimlet["org_zmail_proxy_config"])