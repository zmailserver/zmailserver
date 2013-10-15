/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * ***** END LICENSE BLOCK *****
 */

/*---Enable Proxy Wizard Model---*/
if(ZaSettings && ZaSettings.EnabledZimlet["org_zmail_proxy_config"]) {
function ZaEnableProxy () {
	ZaItem.call(this, "ZaEnableProxy");
	this._init();
	this.type = ZaItem.ENABLE_PROXY ; 
}

ZaItem.ENABLE_PROXY = "enable_proxy" ;
ZaEnableProxy.prototype = new ZaItem ;
ZaEnableProxy.prototype.constructor = ZaEnableProxy;

ZaEnableProxy.myXModel = {
	items: [
		// web proxy
		{id: ZaProxyConfig.A_zmailReverseProxyHttpEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyHttpEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyMailMode, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyMailMode, choices: ZaProxyConfig.MAIL_MODE_CHOICES},
		{id: ZaProxyConfig.A_zmailMailPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailMailPort},
		{id: ZaProxyConfig.A_zmailMailProxyPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailMailProxyPort},
		{id: ZaProxyConfig.A_zmailMailSSLPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailMailSSLPort},
		{id: ZaProxyConfig.A_zmailMailSSLProxyPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailMailSSLProxyPort},
		{id: ZaProxyConfig.A_zmailReverseProxyAdminEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyAdminEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailAdminPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailAdminPort},
		{id: ZaProxyConfig.A_zmailAdminProxyPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailAdminProxyPort},
		{id: ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		
		// mail proxy
		{id: ZaProxyConfig.A_zmailReverseProxyMailEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyMailEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailImapBindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailImapBindPort},
		{id: ZaProxyConfig.A_zmailImapProxyBindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailImapProxyBindPort},
		{id: ZaProxyConfig.A_zmailImapSSLProxyBindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailImapSSLProxyBindPort},
		{id: ZaProxyConfig.A_zmailImapSSLBindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailImapSSLBindPort},
		{id: ZaProxyConfig.A_zmailPop3BindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailPop3BindPort},
		{id: ZaProxyConfig.A_zmailPop3ProxyBindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailPop3ProxyBindPort},
		{id: ZaProxyConfig.A_zmailPop3SSLProxyBindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailPop3SSLProxyBindPort},
		{id: ZaProxyConfig.A_zmailPop3SSLBindPort, type: _PORT_, ref: "attrs/" + ZaProxyConfig.A_zmailPop3SSLBindPort},
		{id: ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode, choices: ZaProxyConfig.STARTTLS_MODE_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode, choices: ZaProxyConfig.STARTTLS_MODE_CHOICES},
		
		// advanced
		{id: ZaProxyConfig.A_zmailReverseProxyWorkerProcesses, type: _INT_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyWorkerProcesses, minInclusive: "1", maxInclusive: "16"},
		{id: ZaProxyConfig.A_zmailReverseProxyWorkerConnections, type: _INT_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyWorkerConnections, minInclusive: "1"},
		{id: ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A_zmailReverseProxyLogLevel, type: _ENUM_, ref: "attrs/" + ZaProxyConfig.A_zmailReverseProxyLogLevel, choices: ZaProxyConfig.PROXY_LOG_LEVEL_CHOICES},

		// utility
		{id: ZaProxyConfig.A2_mbx_name_array, type: _LIST_, itemType: _STRING_},
		{id: ZaProxyConfig.A2_proxy_name_array, type: _LIST_, itemType: _STRING_},
		{id: ZaProxyConfig.A2_target_server, type: _STRING_},
		{id: ZaProxyConfig.A2_target_up_servers, type: _LIST_, itemType: _STRING_},
		{id: ZaProxyConfig.A2_target_lt_servers, type: _LIST_, itemType: _STRING_},
		{id: ZaProxyConfig.A2_all_mailbox_as_upstream, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES},
		{id: ZaProxyConfig.A2_all_mailbox_as_lookuptarget, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES}
	]
}

ZaEnableProxy.init = function () {
	this.attrs = {};
	
	this.attrs[ZaProxyConfig.A_zmailReverseProxyHttpEnabled] = "TRUE";
	this.attrs[ZaProxyConfig.A_zmailReverseProxyMailMode] = ZaProxyConfig.DEFAULT_MAIL_MODE;
	this.attrs[ZaProxyConfig.A_zmailMailPort] = ZaProxyConfig.DEFAULT_MAIL_PORT_ZCS;
	this.attrs[ZaProxyConfig.A_zmailMailProxyPort] = ZaProxyConfig.DEFAULT_MAIL_PORT;
	this.attrs[ZaProxyConfig.A_zmailMailSSLPort] = ZaProxyConfig.DEFAULT_MAIL_SSL_PORT_ZCS;
	this.attrs[ZaProxyConfig.A_zmailMailSSLProxyPort] = ZaProxyConfig.DEFAULT_MAIL_SSL_PORT;
	this.attrs[ZaProxyConfig.A_zmailReverseProxyAdminEnabled] = "FALSE";
	this.attrs[ZaProxyConfig.A_zmailAdminPort] = ZaProxyConfig.DEFAULT_ADMIN_CONSOLE_PORT_ZCS;
	this.attrs[ZaProxyConfig.A_zmailAdminProxyPort] = ZaProxyConfig.DEFAULT_ADMIN_CONSOLE_PORT;
	this.attrs[ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled] = "TRUE";
	
	this.attrs[ZaProxyConfig.A_zmailReverseProxyMailEnabled] = "TRUE";
	this.attrs[ZaProxyConfig.A_zmailImapBindPort] = ZaProxyConfig.DEFAULT_IMAP_PORT_ZCS;
	this.attrs[ZaProxyConfig.A_zmailImapProxyBindPort] = ZaProxyConfig.DEFAULT_IMAP_PORT;
	this.attrs[ZaProxyConfig.A_zmailImapSSLBindPort] = ZaProxyConfig.DEFAULT_IMAP_SSL_PORT_ZCS;
	this.attrs[ZaProxyConfig.A_zmailImapSSLProxyBindPort] = ZaProxyConfig.DEFAULT_IMAP_SSL_PORT;
	this.attrs[ZaProxyConfig.A_zmailPop3BindPort] = ZaProxyConfig.DEFAULT_POP3_PORT_ZCS;
	this.attrs[ZaProxyConfig.A_zmailPop3ProxyBindPort] = ZaProxyConfig.DEFAULT_POP3_PORT;
	this.attrs[ZaProxyConfig.A_zmailPop3SSLBindPort] = ZaProxyConfig.DEFAULT_POP3_SSL_PORT_ZCS;
	this.attrs[ZaProxyConfig.A_zmailPop3SSLProxyBindPort] = ZaProxyConfig.DEFAULT_POP3_SSL_PORT;
	this.attrs[ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode] = "only";
	this.attrs[ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode] = "only";
	
	this.attrs[ZaProxyConfig.A_zmailReverseProxyWorkerProcesses] = 4;
	this.attrs[ZaProxyConfig.A_zmailReverseProxyWorkerConnections] = 10240;
	this.attrs[ZaProxyConfig.A_zmailReverseProxyLogLevel] = "info";
	this.attrs[ZaProxyConfig.A_zmailReverseProxyGenConfigPerVirtualHostname] = "TRUE";
	this.attrs[ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled] = "TRUE";
	
	this[ZaProxyConfig.A2_all_mailbox_as_upstream] = "TRUE";
	this[ZaProxyConfig.A2_all_mailbox_as_lookuptarget] = "TRUE";
	
	this.initServerList();
}

ZaItem.initMethods["ZaEnableProxy"]= [ZaEnableProxy.init];

ZaEnableProxy.prototype.initServerList = function() {
	this[ZaProxyConfig.A2_proxy_name_array] = [];
	this[ZaProxyConfig.A2_mbx_name_array] = [];
	var servers = ZaServer.getAll().getArray();
	for(var i = 0; i < servers.length; i++) {
		var s = servers[i];
		if (s.attrs[ZaServer.A_zmailMailProxyServiceInstalled]) {
			this[ZaProxyConfig.A2_proxy_name_array].push(s["zmailServiceHostname"]);
		}
		
		if (s.attrs[ZaServer.A_zmailMailboxServiceEnabled]) {
			this[ZaProxyConfig.A2_mbx_name_array].push(s["zmailServiceHostname"]);
		}
	}
	
	// As default, all mailbox servers are upstream/lookup target servers
	var mbxServers = this[ZaProxyConfig.A2_mbx_name_array];
	var upServers = [];
	var ltServers = [];
	for (var j = 0; j < mbxServers.length; j++) {
		upServers.push(mbxServers[j]);
		ltServers.push(mbxServers[j]);
	}
	this[ZaProxyConfig.A2_target_up_servers] = upServers;
	this[ZaProxyConfig.A2_target_lt_servers] = ltServers;
	
	// Update proxy server choices
	ZaEnableProxyWizard.proxyServerChoices.setChoices(this[ZaProxyConfig.A2_proxy_name_array]);
	ZaEnableProxyWizard.proxyServerChoices.dirtyChoices();
	
	// Update mailbox server choices
	ZaEnableProxyWizard.mbxServerChoices.setChoices(this[ZaProxyConfig.A2_mbx_name_array]);
	ZaEnableProxyWizard.mbxServerChoices.dirtyChoices();
	
	// Cache the all servers data for the later use
	var allServers = {};
	for(var i = 0; i < servers.length; i++) {
		var s = servers[i];
		allServers[s["zmailServiceHostname"]] = s;
	}
	this[ZaProxyConfig.A2_all_servers] = allServers;
}

/*---Enable Proxy Wizard---*/
function ZaEnableProxyWizard (parent) {
	var w = "500px" ;
	if (AjxEnv.isIE) {
		w = "550px" ;
	}
	ZaXWizardDialog.call(this, parent, null, org_zmail_proxy_config.LBL_EnableProxyWizTitle, w, "330px", "ZaEnableProxyWizard");

	this.stepChoices = [
		{label: org_zmail_proxy_config.LBL_ProxyWizardStepSelectServer, value: ZaEnableProxyWizard.STEP_SELECT_SERVER},
		{label: org_zmail_proxy_config.LBL_ProxyWizardStepWebProxy,     value: ZaEnableProxyWizard.STEP_CONFIG_WEBPROXY},
		{label: org_zmail_proxy_config.LBL_ProxyWizardStepMailProxy,    value: ZaEnableProxyWizard.STEP_CONFIG_MAILPROXY},
		{label: org_zmail_proxy_config.LBL_ProxyWizardStepAdvanced,     value: ZaEnableProxyWizard.STEP_CONFIG_ADVANCED},
		{label: org_zmail_proxy_config.LBL_ProxyWizardStepFinish,       value: ZaEnableProxyWizard.STEP_FINISH}
	];
	
	this.initForm(ZaEnableProxy.myXModel, this.getMyXForm());
	this._localXForm.setController();
	this._localXForm.addListener(DwtEvent.XFORMS_FORM_DIRTY_CHANGE, new AjxListener(this, ZaEnableProxyWizard.prototype.handleXFormChange));
	this._localXForm.addListener(DwtEvent.XFORMS_VALUE_ERROR, new AjxListener(this, ZaEnableProxyWizard.prototype.handleXFormChange));	
}

ZaEnableProxyWizard.prototype = new ZaXWizardDialog;
ZaEnableProxyWizard.prototype.constructor = ZaEnableProxyWizard;
ZaEnableProxyWizard.proxyServerChoices = new XFormChoices([], XFormChoices.SIMPLE_LIST);
ZaEnableProxyWizard.mbxServerChoices = new XFormChoices([], XFormChoices.SIMPLE_LIST);
ZaXDialog.XFormModifiers["ZaEnableProxyWizard"] = new Array();
ZaEnableProxyWizard.helpURL = location.pathname + "help/admin/html/tools/config_proxy.htm?locid=" + AjxEnv.DEFAULT_LOCALE;

ZaEnableProxyWizard.STEP_INDEX = 1 ;
ZaEnableProxyWizard.STEP_SELECT_SERVER = ZaEnableProxyWizard.STEP_INDEX++;
ZaEnableProxyWizard.STEP_CONFIG_WEBPROXY = ZaEnableProxyWizard.STEP_INDEX++;
ZaEnableProxyWizard.STEP_CONFIG_MAILPROXY = ZaEnableProxyWizard.STEP_INDEX++;
ZaEnableProxyWizard.STEP_CONFIG_ADVANCED = ZaEnableProxyWizard.STEP_INDEX++;
ZaEnableProxyWizard.STEP_FINISH = ZaEnableProxyWizard.STEP_INDEX++;


ZaEnableProxyWizard.prototype.handleXFormChange = function () {
	var cStep = this._containedObject[ZaModel.currentStep];

	var obj = this._containedObject;
	if (cStep == ZaEnableProxyWizard.STEP_SELECT_SERVER) {
		if (!AjxUtil.isEmpty(obj[ZaProxyConfig.A2_target_server]) &&
			this.isUpstreamServersConfigValid() &&
			this.isLookupTargetServersConfigValid()){
			this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
		} else {
			this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
		}
	}
}

ZaEnableProxyWizard.prototype.isUpstreamServersConfigValid = function() {
	var obj = this._containedObject;
	if (!AjxUtil.isEmpty(obj[ZaProxyConfig.A2_target_up_servers]) ||
		obj[ZaProxyConfig.A2_all_mailbox_as_upstream] == "TRUE") {
		return true;
	} else {
		return false;
	}
}

ZaEnableProxyWizard.prototype.isLookupTargetServersConfigValid = function() {
	var obj = this._containedObject;
	if (!AjxUtil.isEmpty(obj[ZaProxyConfig.A2_target_lt_servers]) ||
		obj[ZaProxyConfig.A2_all_mailbox_as_lookuptarget] == "TRUE") {
		return true;
	} else {
		return false;
	}
}

/**
* Overwritten methods that control wizard's flow (open, go next,go previous, finish)
**/
ZaEnableProxyWizard.prototype.popup = function (loc) {
	ZaXWizardDialog.prototype.popup.call(this, loc);
	this.changeButtonStateForStep(ZaEnableProxyWizard.STEP_SELECT_SERVER);	
}

ZaEnableProxyWizard.prototype.changeButtonStateForStep = function(stepNum) {
	if(stepNum == ZaEnableProxyWizard.STEP_SELECT_SERVER) {
		// first step, prev is disabled
		this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
		if (AjxUtil.isEmpty(this._containedObject[ZaProxyConfig.A2_target_server])) {
			// we can't continue until there is a target server
			this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
		} else {
			this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
		}
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(false);
	} else if(stepNum == ZaEnableProxyWizard.STEP_FINISH) {
		// last step, next is dsiabled
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
		this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(true);
	} else {
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
		this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
	}
}

ZaEnableProxyWizard.prototype.goPage = function(pageNum) {
	ZaXWizardDialog.prototype.goPage.call(this, pageNum);
	this.changeButtonStateForStep(pageNum);
}

ZaEnableProxyWizard.prototype.finishWizard = function() {
	try {	
		this.applyProxyConfig();
		this.popdown();	
			
	} catch (ex) {
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaEnableWizard.prototype.finishWizard", null, false);
	}
}

/**
 * Write the configured attrs to server
 */
ZaEnableProxyWizard.prototype.applyProxyConfig = function() {
	var instance = this._localXForm.getInstance() ;
	
	// 1) modify upstream servers
	var flags = {};
	var allServers = instance[ZaProxyConfig.A2_all_servers];
	var targetServers;
	if (instance[ZaProxyConfig.A2_all_mailbox_as_upstream] == "TRUE") {
		targetServers = instance[ZaProxyConfig.A2_mbx_name_array];
	} else {
		targetServers = instance[ZaProxyConfig.A2_target_up_servers];
	}
	
	for (var i = 0; i < targetServers.length; i++) {
		var s = targetServers[i];
		flags[s] = {isUpstream: true};
	}
	
	if (instance[ZaProxyConfig.A2_all_mailbox_as_lookuptarget] == "TRUE") {
		targetServers = instance[ZaProxyConfig.A2_mbx_name_array];
	} else {
		targetServers = instance[ZaProxyConfig.A2_target_lt_servers];
	}
	
	for (var i = 0; i < targetServers.length; i++) {
		var s = targetServers[i];
		if (!flags[s]) {
			flags[s] = {isLookupTarget: true};
		}
	}
	
	var batchDoc = AjxSoapDoc.create("BatchRequest", "urn:zmail");
	batchDoc.setMethodAttribute("onerror", "stop");
	for (var s in flags) {
		var md = batchDoc.set("ModifyServerRequest", null, null, ZaZmailAdmin.URN);
		var id = allServers[s].id;
		batchDoc.set("id", id, md);
		
		ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailReverseProxyLookupTarget, "TRUE", md);
		
		if (flags[s].isUpstream) {
			// apply web proxy in upstream
			if (instance.attrs[ZaProxyConfig.A_zmailReverseProxyHttpEnabled] == "TRUE") {
				ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailMailSSLPort, instance.attrs[ZaProxyConfig.A_zmailMailSSLPort], md);
				ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailMailPort, instance.attrs[ZaProxyConfig.A_zmailMailPort], md);
				if (instance.attrs[ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled] == "TRUE") {
					ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailMailMode, "https", md);
				} else {
					ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailMailMode, "http", md);
				}
				
				if (instance.attrs[ZaProxyConfig.A_zmailReverseProxyAdminEnabled] == "TRUE") {
					ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailAdminPort, instance.attrs[ZaProxyConfig.A_zmailAdminPort], md);
				}
			}
			
			// apply mail proxy in upstream
			if (instance.attrs[ZaProxyConfig.A_zmailReverseProxyMailEnabled] == "TRUE") {
				ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailImapBindPort, instance.attrs[ZaProxyConfig.A_zmailImapBindPort], md);
				ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailPop3BindPort, instance.attrs[ZaProxyConfig.A_zmailPop3BindPort], md);
				ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailImapSSLBindPort, instance.attrs[ZaProxyConfig.A_zmailImapSSLBindPort], md);
				ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailPop3SSLBindPort, instance.attrs[ZaProxyConfig.A_zmailPop3SSLBindPort], md);
				if (instance.attrs[ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled] != "TRUE") {
					ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailImapCleartextLoginEnabled, "TRUE", md);
					ZaEnableProxyWizard.setAttr(batchDoc, ZaProxyConfig.A_zmailPop3CleartextLoginEnabled, "TRUE", md);
				}
			}
		}
	}
	
	var params1 = new Object();
	params1.soapDoc = batchDoc;	
	var reqMgrParams1 = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_MODIFY_SERVER
	}
	var resp1 = ZaRequestMgr.invoke(params1, reqMgrParams1).Body.BatchResponse;
	
	
	// 2) modify proxy server
	var proxyServer = allServers[instance[ZaProxyConfig.A2_target_server]];
	
	var proxyDoc = AjxSoapDoc.create("ModifyServerRequest", ZaZmailAdmin.URN);
	proxyDoc.set("id", proxyServer.id);
	if (instance.attrs[ZaProxyConfig.A_zmailReverseProxyHttpEnabled] == "TRUE") {
		// apply web proxy config
		ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyHttpEnabled, instance.attrs[ZaProxyConfig.A_zmailReverseProxyHttpEnabled]);
		ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyMailMode, instance.attrs[ZaProxyConfig.A_zmailReverseProxyMailMode]);
		var mailmode = instance.attrs[ZaProxyConfig.A_zmailReverseProxyMailMode];
		if (mailmode != "https") {
			ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailMailProxyPort, instance.attrs[ZaProxyConfig.A_zmailMailProxyPort]);
		}
		if (mailmode != "http") {
			ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailMailSSLProxyPort, instance.attrs[ZaProxyConfig.A_zmailMailSSLProxyPort]);
		}
		if (instance.attrs[ZaProxyConfig.A_zmailReverseProxyAdminEnabled] == "TRUE") {
			ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyAdminEnabled, instance.attrs[ZaProxyConfig.A_zmailReverseProxyAdminEnabled]);
			ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailAdminProxyPort, instance.attrs[ZaProxyConfig.A_zmailAdminProxyPort]);
		}
	}
	
	if (instance.attrs[ZaProxyConfig.A_zmailReverseProxyMailEnabled] == "TRUE") {
		// apply mail proxy config
		ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyMailEnabled, instance.attrs[ZaProxyConfig.A_zmailReverseProxyMailEnabled]);
		ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailImapProxyBindPort, instance.attrs[ZaProxyConfig.A_zmailImapProxyBindPort]);
		ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailImapSSLProxyBindPort, instance.attrs[ZaProxyConfig.A_zmailImapSSLProxyBindPort]);
		ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode, instance.attrs[ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode]);
		ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailPop3ProxyBindPort, instance.attrs[ZaProxyConfig.A_zmailPop3ProxyBindPort]);
		ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailPop3SSLProxyBindPort, instance.attrs[ZaProxyConfig.A_zmailPop3SSLProxyBindPort]);
		ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode, instance.attrs[ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode]);
	}
	
	// apply advanced proxy config
	ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyWorkerProcesses, instance.attrs[ZaProxyConfig.A_zmailReverseProxyWorkerProcesses]);
	ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyWorkerConnections, instance.attrs[ZaProxyConfig.A_zmailReverseProxyWorkerConnections]);
	ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled, instance.attrs[ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled]);
	ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyLogLevel, instance.attrs[ZaProxyConfig.A_zmailReverseProxyLogLevel]);
	ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled, instance.attrs[ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled]);
	
	// apply upstream settings
	if (instance[ZaProxyConfig.A2_all_mailbox_as_upstream]) {
		ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyUpstreamServers, "");
	} else {
		var upArr = instance[ZaProxyConfig.A2_target_up_servers];
		for (var i = 0; i < upArr.length; i++) {
			ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyUpstreamServers, upArr[i]);
		}
	}
	
	if (instance[ZaProxyConfig.A2_all_mailbox_as_lookuptarget]) {
		ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyAvailableLookupTargets, "");
	} else {
		var ltArr = instance[ZaProxyConfig.A2_target_lt_servers];
		for (var i = 0; i < ltArr.length; i++) {
			ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailReverseProxyAvailableLookupTargets, ltArr[i]);
		}
	}
	
    ZaEnableProxyWizard.setAttr(proxyDoc, ZaProxyConfig.A_zmailServiceEnabled, "proxy", null, "+");
    
	// 3) send request and renew server with the response
	var params2 = new Object();
	params2.soapDoc = proxyDoc;	
	var reqMgrParams2 = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_MODIFY_SERVER
	}
	var resp = ZaRequestMgr.invoke(params2, reqMgrParams2).Body.ModifyServerResponse;		
	proxyServer.initFromJS(resp.server[0]);		
}

/**
 * Set attr to doc.
 * if val is undefined, doc[attr] = obj[attr];
 * otherwise, doc[attr] = val;
 * 
 * op, optional, may be "+" or "-"
 */
ZaEnableProxyWizard.setAttr = function(doc, attr, val, parent, op) {
	var attribute;
	if (parent) {
		attribute = doc.set("a", val, parent);
	} else {
		attribute = doc.set("a", val);
	}
	if (op) {
		attribute.setAttribute("n", op + attr);
	} else {
		attribute.setAttribute("n", attr);
	}
} 

/**
* @method setObject sets the object contained in the view
* @param entry -  object to display
**/
ZaEnableProxyWizard.prototype.setObject =
function(obj) {
	this._containedObject = obj ;
	
	// initialize the XWizard Model
	this._containedObject[ZaModel.currentStep] = obj[ZaModel.currentStep] || ZaEnableProxyWizard.STEP_SELECT_SERVER;
	
	if (AjxUtil.arrayContains(obj[ZaProxyConfig.A2_proxy_name_array], obj[ZaProxyConfig.A2_current_server])) {
		this._containedObject[ZaProxyConfig.A2_target_server] = obj[ZaProxyConfig.A2_current_server];
	} else {
		this._containedObject[ZaProxyConfig.A2_target_server] = undefined;
    }
 
	this._localXForm.setInstance(this._containedObject);
}

ZaEnableProxyWizard.isWebProxyEnabled = function() {
	var webProxyEnabled = this.getInstanceValue(ZaProxyConfig.A_zmailReverseProxyHttpEnabled);
	return (webProxyEnabled == "TRUE");
}

ZaEnableProxyWizard.isAdminProxyEnabled = function() {
	var adminProxyEnabled = this.getInstanceValue(ZaProxyConfig.A_zmailReverseProxyAdminEnabled);
	return (adminProxyEnabled == "TRUE");
}

ZaEnableProxyWizard.isMailProxyEnabled = function() {
	var mailProxyEnabled = this.getInstanceValue(ZaProxyConfig.A_zmailReverseProxyMailEnabled);
	return (mailProxyEnabled == "TRUE");
}

ZaEnableProxyWizard.isProxyMailModeNotHTTP = function() {
	var mailmode = this.getInstanceValue(ZaProxyConfig.A_zmailReverseProxyMailMode);
	return (mailmode != "http");
}

ZaEnableProxyWizard.isProxyMailModeNotHTTPS = function() {
	var mailmode = this.getInstanceValue(ZaProxyConfig.A_zmailReverseProxyMailMode);
	return (mailmode != "https");
}

ZaEnableProxyWizard.isSSLToUpstreamEnabled = function() {
	var uptossl = this.getInstanceValue(ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled);
	return (uptossl == "TRUE");
}

ZaEnableProxyWizard.isSSLToUpstreamDisabled = function() {
	return !ZaEnableProxyWizard.isSSLToUpstreamEnabled.call(this);
}

ZaEnableProxyWizard.isNotAllMbxAsUp = function() {
	var allMbxAsUp = this.getInstanceValue(ZaProxyConfig.A2_all_mailbox_as_upstream);
	return (allMbxAsUp == "FALSE");
}

ZaEnableProxyWizard.isNotAllMbxAsLT = function() {
	var allMbxAsLT = this.getInstanceValue(ZaProxyConfig.A2_all_mailbox_as_lookuptarget);
	return (allMbxAsLT == "FALSE");
}

ZaEnableProxyWizard.myXFormModifier = function(xFormObject) {		
	var cases = new Array();
	
	// case 1: select server
	var case_select_server = {
		type:_CASE_, numCols:1, colSizes:["350px"],
		tabGroupKey:ZaEnableProxyWizard.STEP_SELECT_SERVER, caseKey:ZaEnableProxyWizard.STEP_SELECT_SERVER,
		align:_LEFT_, valign:_TOP_, width:"90%"
	};
	
	case_select_server.items = [
		{type: _SPACER_, height: 10},
		{type: _OUTPUT_, colSpan: 2, value: org_zmail_proxy_config.LBL_ProxySelectProxyServer},
		{type: _SPACER_, height: 10},
		{type: _GROUP_, colSpan: "*", colSizes: ["260px", "*"],
		 items:[
			{type: _OSELECT1_, ref: ZaProxyConfig.A2_target_server, 
			 label: org_zmail_proxy_config.LBL_ProxySelectProxyServerToEnableInDetail, 
			 labelLocation:_LEFT_, labelCssStyle: "text-align:left",
			 choices: ZaEnableProxyWizard.proxyServerChoices,
			 editable: false
			}
		]
		},
		{type: _SPACER_, height: 20},
		{type: _GROUP_, colSpan: "*", colSizes: ["15px", "*"],
		 items:[
			{type: _OUTPUT_, colSpan: 2, value: org_zmail_proxy_config.LBL_ProxySelectUpServer},
			{type: _SPACER_, height: 10},
			{type: _CHECKBOX_, ref: ZaProxyConfig.A2_all_mailbox_as_upstream,
			 label: org_zmail_proxy_config.LBL_ProxyAllMailboxAsUp,
			 trueValue: "TRUE", falseValue: "FALSE", labelLocation: _RIGHT_
			},
			{type: _SPACER_, height: "10px"},
			{type: _OUTPUT_, colSpan: "*", label: "", labelCssSytle: "padding-left:10px",
			 value: org_zmail_proxy_config.LBL_ProxyLimitUp,
			 visibilityChecks: [ZaEnableProxyWizard.isNotAllMbxAsUp],
			 visibilityChangeEventSources: [ZaProxyConfig.A2_all_mailbox_as_upstream]
			},
			{type: _OSELECT_CHECK_, ref: ZaProxyConfig.A2_target_up_servers, colSpan: "*",
			 cssStyle: "margin-bottom:5px;margin-top:5px;border:2px inset gray;",
			 width: "250px", choices: ZaEnableProxyWizard.mbxServerChoices,
			 visibilityChecks: [ZaEnableProxyWizard.isNotAllMbxAsUp],
			 visibilityChangeEventSources: [ZaProxyConfig.A2_all_mailbox_as_upstream]
			}
		 ]
		},
		{type: _SPACER_, height: 20},
		{type: _GROUP_,  colSpan: "*", colSizes: ["15px", "*"],
		 items:[
			{type: _OUTPUT_, colSpan: 2, value: org_zmail_proxy_config.LBL_ProxySelectLTServer},
			{type: _SPACER_, height: 10},
			{type: _CHECKBOX_, ref: ZaProxyConfig.A2_all_mailbox_as_lookuptarget,
			 label: org_zmail_proxy_config.LBL_ProxyAllMailboxAsLT,
			 trueValue: "TRUE", falseValue: "FALSE", labelLocation: _RIGHT_
			},
			{type: _SPACER_, height: "10px"},
			{type: _OUTPUT_, colSpan: "*", label: "", labelCssSytle: "padding-left:10px",
			 value: org_zmail_proxy_config.LBL_ProxyLimitLT,
			 visibilityChecks: [ZaEnableProxyWizard.isNotAllMbxAsLT],
			 visibilityChangeEventSources: [ZaProxyConfig.A2_all_mailbox_as_lookuptarget]
			},
			{type: _OSELECT_CHECK_, ref: ZaProxyConfig.A2_target_lt_servers, colSpan: "*",
			 cssStyle: "margin-bottom:5px;margin-top:5px;border:2px inset gray;",
			 width: "250px", choices: ZaEnableProxyWizard.mbxServerChoices,
			 visibilityChecks: [ZaEnableProxyWizard.isNotAllMbxAsLT],
			 visibilityChangeEventSources: [ZaProxyConfig.A2_all_mailbox_as_lookuptarget]
			}
			 
		 ]
		}	
	];
	cases.push(case_select_server);
	
	// case 2: web proxy config
	var case_config_webproxy = {
		type:_CASE_, numCols:1, colSizes:["350px"], 
		tabGroupKey:ZaEnableProxyWizard.STEP_CONFIG_WEBPROXY, caseKey:ZaEnableProxyWizard.STEP_CONFIG_WEBPROXY,
		align:_LEFT_, valign:_TOP_, width:"90%"};
			
	case_config_webproxy.items = [
		{type: _GROUP_, numCols:2, colSpan: "*", colSizes:["100px","*"],
		 items: [
			{ type:_OUTPUT_, colSpan: "*", value: org_zmail_proxy_config.LBL_ProxyWebProxyConfig, cssStyle: "font-weight:bold"},
			{ type:_OUTPUT_ , ref: ZaProxyConfig.A2_target_server, 
			  labelLocation:_LEFT_ , labelCssStyle: "text-align: left; font-weight:bold",
			  label: org_zmail_proxy_config.LBL_ProxyServerName
			}
		 ]
		},
		{type:_SPACER_, height: 10},
		{type: _GROUP_, numCols:2, colSizes: ["120px", "auto"],
		 items: [
			{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyEnableWebProxy,
			 ref: ZaProxyConfig.A_zmailReverseProxyHttpEnabled,
			 trueValue: "TRUE", falseValue: "FALSE", labelCssStyle: "text-align: right"
			}
		 ]
		},
		{type: _GROUP_, numCols:2, colSizes: ["200px", "auto"],
		 visibilityChecks: [ZaEnableProxyWizard.isWebProxyEnabled],
		 visibilityChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyHttpEnabled],
		 items: [
			{type: _SELECT1_, label: org_zmail_proxy_config.LBL_ProxyWebProxyMode,
			 ref: ZaProxyConfig.A_zmailReverseProxyMailMode,
			 width: "60px"
			},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyHttpProxyPort,
			 ref: ZaProxyConfig.A_zmailMailProxyPort,
			 width: "60px",
			 visibilityChecks: [ZaEnableProxyWizard.isProxyMailModeNotHTTPS],
			 visibilityChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailMode]
			},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyHttpSSLProxyPort,
			 ref: ZaProxyConfig.A_zmailMailSSLProxyPort,
			 width: "60px",
			 visibilityChecks: [ZaEnableProxyWizard.isProxyMailModeNotHTTP],
			 visibilityChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailMode]
			},
			{type:_SPACER_, height: 10},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyHttpUpPort,
			 ref: ZaProxyConfig.A_zmailMailPort,
			 width: "60px"
			},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyHttpSSLUpPort,
			 ref: ZaProxyConfig.A_zmailMailSSLPort,
			 width: "60px"
			},
			{type: _SPACER_, height: 10},
			{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyAdminEnabled,
			 ref: ZaProxyConfig.A_zmailReverseProxyAdminEnabled,
			 trueValue: "TRUE", falseValue: "FALSE", labelCssStyle: "text-align: right"
			},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyAdminProxyPort,
			 ref: ZaProxyConfig.A_zmailAdminProxyPort,
			 width: "60px",
			 visibilityChecks: [ZaEnableProxyWizard.isAdminProxyEnabled],
			 visibilityChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyAdminEnabled]
			},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyAdminUpPort,
			 ref: ZaProxyConfig.A_zmailAdminPort,
			 width: "60px",
			 visibilityChecks: [ZaEnableProxyWizard.isAdminProxyEnabled],
			 visibilityChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyAdminEnabled]
			},
		 ]
		},
		{ type:_SPACER_, height: 10}
	];
	cases.push(case_config_webproxy);

	// case 3: mail proxy config
	var case_config_mailproxy = {
			type:_CASE_, numCols:1, colSizes:["350px"], 
			tabGroupKey:ZaEnableProxyWizard.STEP_CONFIG_MAILPROXY, caseKey:ZaEnableProxyWizard.STEP_CONFIG_MAILPROXY,
			align:_LEFT_, valign:_TOP_, width:"90%"};
				
	case_config_mailproxy.items = [
		{type: _GROUP_, numCols:2, colSpan: "*", colSizes:["100px","*"],
		 items: [
			{ type:_OUTPUT_, colSpan: "*", value: org_zmail_proxy_config.LBL_ProxyMailProxyConfig, cssStyle: "font-weight:bold"},
			{ type:_OUTPUT_ , ref: ZaProxyConfig.A2_target_server, 
			  labelLocation:_LEFT_ , labelCssStyle: "text-align: left; font-weight:bold",
			  label: org_zmail_proxy_config.LBL_ProxyServerName
			}
		 ]
		},
		{type:_SPACER_, height: 10},
		{type: _GROUP_, numCols:2, colSizes: ["120px", "auto"],
		 items: [
			{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyEnableMailProxy,
			 ref: ZaProxyConfig.A_zmailReverseProxyMailEnabled,
			 trueValue: "TRUE", falseValue: "FALSE", labelCssStyle: "text-align: right"
			}
		 ]
		},
		{type: _GROUP_, numCols:2, colSizes: ["200px", "auto"],
		 visibilityChecks: [ZaEnableProxyWizard.isMailProxyEnabled],
		 visibilityChangeEventSources: [ZaProxyConfig.A_zmailReverseProxyMailEnabled],
		 items: [
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyImapProxyPort,
			 ref: ZaProxyConfig.A_zmailImapProxyBindPort,
			 width: "60px"
			},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyImapSSLProxyPort,
			 ref: ZaProxyConfig.A_zmailImapSSLProxyBindPort,
			 width: "60px"
			},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyImapUpPort,
			 ref: ZaProxyConfig.A_zmailImapBindPort,
			 width: "60px"
			},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyImapSSLUpPort,
			 ref: ZaProxyConfig.A_zmailImapSSLBindPort,
			 width: "60px"
			},
			{type: _SELECT1_, label: org_zmail_proxy_config.LBL_ProxyImapStartTlsMode,
			 ref: ZaProxyConfig.A_zmailReverseProxyImapStartTlsMode
			},
			{type: _SPACER_, height: 10},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyPop3ProxyPort,
			 ref: ZaProxyConfig.A_zmailPop3ProxyBindPort,
			 width: "60px"
			},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyPop3SSLProxyPort,
			 ref: ZaProxyConfig.A_zmailPop3SSLProxyBindPort,
			 width: "60px"
			},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyPop3UpPort,
			 ref: ZaProxyConfig.A_zmailPop3BindPort,
			 width: "60px"
			},
			{type: _TEXTFIELD_, label: org_zmail_proxy_config.LBL_ProxyPop3SSLUpPort,
			 ref: ZaProxyConfig.A_zmailPop3SSLBindPort,
			 width: "60px"
			},
			{type: _SELECT1_, label: org_zmail_proxy_config.LBL_ProxyPop3StartTlsMode,
			 ref: ZaProxyConfig.A_zmailReverseProxyPop3StartTlsMode
			}
		 ]
		}
	];
	cases.push(case_config_mailproxy);

	// case 4: advanced config
	var case_config_advanced = {type:_CASE_, numCols:1, colSizes:["350px"], 
			tabGroupKey: ZaEnableProxyWizard.STEP_CONFIG_ADVANCED, caseKey: ZaEnableProxyWizard.STEP_CONFIG_ADVANCED,
			align:_LEFT_, valign:_TOP_};
			
	case_config_advanced.items = [
		{type: _GROUP_, numCols:2, colSpan: "*", colSizes:["100px", "*"],
		 items: [
			{ type:_OUTPUT_, colSpan: "*", value: org_zmail_proxy_config.LBL_ProxyGeneralConfig, cssStyle: "font-weight:bold"},
			{ type:_OUTPUT_ , ref: ZaProxyConfig.A2_target_server, 
			  labelLocation:_LEFT_ , labelCssStyle: "text-align: left; font-weight:bold",
			  label: org_zmail_proxy_config.LBL_ProxyServerName
			}
		 ]
		},
		{type:_SPACER_, height: 10},
		{type: _GROUP_, numCols:2, colSizes: ["250px", "auto"],
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
			 ref: ZaProxyConfig.A_zmailReverseProxyLogLevel,
			 getDisplayValue: function (val) {
				 return val;
			 }
			},
			{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyUseSSLToUpstream,
			 ref: ZaProxyConfig.A_zmailReverseProxySSLToUpstreamEnabled,
			 trueValue: "TRUE", falseValue: "FALSE",labelCssStyle: "text-align: right"
			},
			{type: _CHECKBOX_, label: org_zmail_proxy_config.LBL_ProxyAllowServerResolveRoute,
			 ref: ZaProxyConfig.A_zmailReverseProxyDnsLookupInServerEnabled,
			 labelCssStyle: "text-align: right",
			 trueValue: "FALSE", falseValue: "TRUE" // the true and false value are meant to be reversed
			}
		  ]
		},
        { type:_SPACER_, height: 10}
    ];
	cases.push(case_config_advanced);
	
	var case_config_finish = {type:_CASE_, numCols:1, colSizes:["350px"], 
		tabGroupKey: ZaEnableProxyWizard.STEP_FINISH, caseKey: ZaEnableProxyWizard.STEP_FINISH,
		align:_LEFT_, valign:_TOP_};
	
	case_config_finish.items = [
		{type: _GROUP_, numCols:1, colSpan: "*", colSizes:["400px"],
	     items: [
			{ type:_SPACER_, height: 10},
			{ type:_OUTPUT_, 
			  labelLocation:_LEFT_ , labelCssStyle: "text-align: left",
			  label: org_zmail_proxy_config.MSG_ProxyEnableFinish
			}
		 ]
		}
	];
	cases.push(case_config_finish);

    var w = "470px" ;  //500px-padding-left:15-padding-right:15
    if (AjxEnv.isIE) {
        w = "520px" ;
    }

    xFormObject.items = [
		{type:_OUTPUT_, colSpan:2, align:_CENTER_, valign:_TOP_, ref:ZaModel.currentStep,
         choices:this.stepChoices, valueChangeEventSources:[ZaModel.currentStep]},
		{type:_SEPARATOR_, align:_CENTER_, valign:_TOP_},
		{type:_SPACER_,  align:_CENTER_, valign:_TOP_},
		{type:_SWITCH_,  width:w, align:_LEFT_, valign:_TOP_, items:cases}
	];
}
ZaXDialog.XFormModifiers["ZaEnableProxyWizard"].push(ZaEnableProxyWizard.myXFormModifier);
}