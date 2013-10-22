if (window.console && window.console.log) window.console.log("Loaded ZaCert.js");
if(ZaSettings && ZaSettings.EnabledZimlet["com_zimbra_cert_manager"]){
function ZaCert () {
	ZaItem.call(this,  "ZaCert");
	this._init();
	this.type = ZaItem.CERT ;
}

ZaItem.CERT = "certificate" ;
ZaCert.prototype = new ZaItem ;
ZaCert.prototype.constructor = ZaCert ;

ZaCert.DEFAULT_VALIDATION_DAYS = 365 ;
ZaCert.A_countryName = "C" ;
ZaCert.A_commonName = "CN" ;
ZaCert.A_state = "ST" ;
ZaCert.A_city = "L" ;
ZaCert.A_organization = "O" ;
ZaCert.A_organizationUnit = "OU" ;
ZaCert.A_validation_days = "validation_days" ;
//ZaCert.A_allserver = "allserver" ;
ZaCert.A_subject = "subject" ;
ZaCert.A_type = "type" ;
ZaCert.A_type_self = "self" ;
ZaCert.A_type_comm = "comm" ;
ZaCert.A_type_csr = "csr" ; //generate the csr only
ZaCert.A_csr_exists = "csr_exists" ;
ZaCert.A_keysize = "keysize" ;
ZaCert.A_force_new_csr = "force_new_csr" ; //only matters when the csr exists
ZaCert.A_target_server = "target_server" ;
ZaCert.A_subject_alt = "SubjectAltName";
ZaCert.A_use_wildcard_server_name = "user_wildcard_server_name";

ZaCert.ALL_SERVERS = "--- All Servers ---" ; //Don't modify it, it need to be consistent with server side value

ZaCert.TARGET_SERVER_CHOICES =  [
		{label:com_zimbra_cert_manager.lb_ALL_SERVERS, value: ZaCert.ALL_SERVERS }
		/*,
		{label: "test1.zimbra.com", value: "test1.zimbra.com" },
		{label: "test2.zimbra.com", value: "test2.zimbra.com" },
		{label: "admindev2.zimbra.com", value: "admindev2.zimbra.com" }*/
	];

ZaCert.KEY_SIZE_CHOICES = [ {label: "1024", value: "1024"},
                            {label: "2048", value: "2048"}] ;

if(ZaSettings) {
	ZaSettings.CERTS_VIEW = "certsView";
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.CERTS_VIEW, label: com_zimbra_cert_manager.UI_Comp_certsView });
	ZaSettings.OVERVIEW_TOOLS_ITEMS.push(ZaSettings.CERTS_VIEW);
	ZaSettings.VIEW_RIGHTS [ZaSettings.CERTS_VIEW] = "adminConsoleCertificateRights";
}

//Init the ZaCert Object for the new Cert wizard
ZaCert.prototype.init = function (getCSRResp) {
	// 1. Check if CSR is generated, set the csr_exists = true 
	this.attrs = {};
	this.attrs [ZaCert.A_subject_alt] = [];
	this [ZaCert.A_type_self]  = true ;
	this [ZaCert.A_type_comm] = false ;
	this [ZaCert.A_type_csr] = false ;
    this [ZaCert.A_keysize] = "2048" ;
	this.initCSR(getCSRResp) ;
	this [ZaCert.A_validation_days] = ZaCert.DEFAULT_VALIDATION_DAYS ;
	this [ZaCert.A_force_new_csr]  = 'FALSE';
}

ZaCert.prototype.initCSR = function (getCSRResp) {
	if (getCSRResp) {
		var csr_exists = getCSRResp [ZaCert.A_csr_exists];
		if ( csr_exists && csr_exists == "1") {
			this[ZaCert.A_csr_exists] = true ;
		}else{
			this[ZaCert.A_csr_exists] = false ;
		}
		
		/*
		var isComm = getCSRResp ["isComm"];
		if (isComm && isComm == "1") {
			this [ZaCert.A_type_self]  = false ;
			this [ZaCert.A_type_comm] = true ;
			this [ZaCert.A_type_csr] = false ;
		}else{
			this [ZaCert.A_type_self]  = true ;
			this [ZaCert.A_type_comm] = false ;
			this [ZaCert.A_type_csr] = false ;
		}*/
		
		for (var key in getCSRResp) {
			var value = getCSRResp[key] ;
			if (value instanceof Array) {
				//array attributes
				if ((key == ZaCert.A_subject_alt) || (value.length > 1)) {
					this.attrs[key] = [] ;
					for (var i=0; i < value.length; i ++) {
						if (value[i]._content.length > 0) {//non empty value
							this.attrs[key].push (value[i]._content) ;
						}
					}
				}else{ 
					this.attrs[key] = value[0]._content ;
				}
			}
		}
	}
	
	//modify the Subject Alt Name based on the 	target server choices
	this.modifySubjectAltNames();
}

//this function should be called when the CSR creation wizard is changed or shown
ZaCert.prototype.modifySubjectAltNames = function () {
	if(window.console && window.console.log) console.log("Enter ZaCert.prototype.modifySubjectAltNames ");
	
	var currentSubjAltNames = this.attrs[ZaCert.A_subject_alt] ;
	// only modify when a new CSR should be created.
	if ((!this[ZaCert.A_csr_exists]) || (this[ZaCert.A_force_new_csr] == 'TRUE')) { 
		if(window.console && window.console.log) console.log("Modifying SubjectAltNames ");
		if (this[ZaCert.A_target_server] == ZaCert.ALL_SERVERS) {
			for (var i=0; i < ZaCert.TARGET_SERVER_CHOICES.length; i ++) {
				if ((ZaCert.TARGET_SERVER_CHOICES[i].value != ZaCert.ALL_SERVERS) && //Not All Servers Value
				//the target server name doesn't exist in the current subjectAltName
				   (ZaUtil.findValueInArray(currentSubjAltNames, ZaCert.TARGET_SERVER_CHOICES[i].label) == -1)){
					
					//add this target server value to subject alt names
					if(window.console && window.console.log) console.log("Adding " + ZaCert.TARGET_SERVER_CHOICES[i].label);
					currentSubjAltNames.push(ZaCert.TARGET_SERVER_CHOICES[i].label);
				}			
			}
		}else{
			var targetServerName ;
			for (var i=0; i < ZaCert.TARGET_SERVER_CHOICES.length; i ++) {
				if (ZaCert.TARGET_SERVER_CHOICES[i].value == this[ZaCert.A_target_server]){
					targetServerName = ZaCert.TARGET_SERVER_CHOICES[i].label ;
				}
			}
				//add this target server value to subject alt names
			
			if ((targetServerName != null) && 
				//the target server name doesn't exist in the current subjectAltName
				 (ZaUtil.findValueInArray(currentSubjAltNames, targetServerName) == -1)){
				if(window.console && window.console.log) console.log("Adding " + targetServerName);
				currentSubjAltNames.push(targetServerName);
			}
		}
	}
}

ZaCert.certOvTreeModifier = function (tree) {
	var overviewPanelController = this ;
	if (!overviewPanelController) throw new Exception("ZaCert.certOvTreeModifier: Overview Panel Controller is not set.");
	
	if(ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CERTS_VIEW] || ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CARTE_BLANCHE_UI]) {
		try {
            if (appNewUI) {
            var parentPath = ZaTree.getPathByArray([ZaMsg.OVP_home, ZaMsg.OVP_configure]);

			var serverList = overviewPanelController._app.getServerList().getArray();
			if(serverList && serverList.length) {
				var cnt = serverList.length;
				if(cnt>0) {
                    var certTi = new ZaTreeItemData({
                                        parent:parentPath,
                                        id:ZaId.getTreeItemId(ZaId.PANEL_APP,ZaId.PANEL_CONFIGURATION,null, "CertHV"),
                                        text: com_zimbra_cert_manager.OVP_certs,
                                        canShowOnRoot: false,
                                        forceNode: false,
                                        mappingId: ZaZimbraAdmin._CERTS_SERVER_LIST_VIEW});
                    tree.addTreeItemData(certTi);
					//add the server nodes
                    var subParentPath = ZaTree.getPathByArray([ZaMsg.OVP_home, ZaMsg.OVP_configure, com_zimbra_cert_manager.OVP_certs]);
					for(var ix=0; ix< cnt; ix++) {
                        var ti1 = new ZaTreeItemData({
                                parent:subParentPath,
                                id:DwtId._makeId(certTi.id, ix + 1),
                                text: serverList[ix].name,
                                defaultSelectedItem: 1,
                                mappingId: ZaZimbraAdmin._CERTS});
                        ti1.setData(ZaOverviewPanelController._OBJ_ID, serverList[ix].id);
                        tree.addTreeItemData(ti1);
						ZaCert.TARGET_SERVER_CHOICES.push (
							{label: serverList[ix].name, value: serverList[ix].id }
						);
					}
					ZaOverviewPanelController.overviewTreeListeners[ZaZimbraAdmin._CERTS_SERVER_LIST_VIEW] = ZaCert.certsServerListTreeListener;
				} else {
                    var certTi = new ZaTreeItemData({
                                        parent:parentPath,
                                        id:ZaId.getTreeItemId(ZaId.PANEL_APP,ZaId.PANEL_CONFIGURATION,null, "CertHV"),
                                        text: com_zimbra_cert_manager.OVP_certs,
                                        mappingId: ZaZimbraAdmin._CERTS});
					certTi.setData(ZaOverviewPanelController._OBJ_ID, serverList[0].id);
                    tree.addTreeItemData(certTi);
					ZaCert.TARGET_SERVER_CHOICES.push (
							{label: serverList[0].name, value: serverList[0].id }
						);
				}
				ZaOverviewPanelController.overviewTreeListeners[ZaZimbraAdmin._CERTS] = ZaCert.certsServerNodeTreeListener;
			}
            } else {
			overviewPanelController._certTi = new DwtTreeItem({parent:overviewPanelController._toolsTi,className:"AdminTreeItem"});
			overviewPanelController._certTi.setText(com_zimbra_cert_manager.OVP_certs);
			overviewPanelController._certTi.setImage("OverviewCertificate"); //TODO: Use Cert icons

			var serverList = overviewPanelController._app.getServerList().getArray();
			if(serverList && serverList.length) {
				var cnt = serverList.length;
				if(cnt>1) {
					overviewPanelController._certTi.setData(ZaOverviewPanelController._TID, ZaZimbraAdmin._CERTS_SERVER_LIST_VIEW);
					//add the server nodes
					for(var ix=0; ix< cnt; ix++) {
						var ti1 = new DwtTreeItem({parent:overviewPanelController._certTi,className:"AdminTreeItem"});
						ti1.setText(serverList[ix].name);
						ti1.setImage("Server");
						ti1.setData(ZaOverviewPanelController._TID, ZaZimbraAdmin._CERTS);
						ti1.setData(ZaOverviewPanelController._OBJ_ID, serverList[ix].id);
						ZaCert.TARGET_SERVER_CHOICES.push (
							{label: serverList[ix].name, value: serverList[ix].id }
						);
					}
					ZaOverviewPanelController.overviewTreeListeners[ZaZimbraAdmin._CERTS_SERVER_LIST_VIEW] = ZaCert.certsServerListTreeListener;
				} else {
					overviewPanelController._certTi.setData(ZaOverviewPanelController._TID, ZaZimbraAdmin._CERTS);
					overviewPanelController._certTi.setData(ZaOverviewPanelController._OBJ_ID, serverList[0].id);
					ZaCert.TARGET_SERVER_CHOICES.push (
							{label: serverList[0].name, value: serverList[0].id }
						);
				}
				ZaOverviewPanelController.overviewTreeListeners[ZaZimbraAdmin._CERTS] = ZaCert.certsServerNodeTreeListener;
			}
            }
		} catch (ex) {
			overviewPanelController._handleException(ex, "ZaCert.certOvTreeModifier", null, false);
		}
	}
}
                         
//When the certs tree item is clicked
ZaCert.certsServerListTreeListener = function (ev) {
	if(window.console && window.console.log) console.log("Show the server lists ...") ;
	if(ZaApp.getInstance().getCurrentController()) {
		ZaApp.getInstance().getCurrentController().switchToNextView(
			ZaApp.getInstance().getCertsServerListController(),ZaCertsServerListController.prototype.show, ZaServer.getAll());
	} else {					
		ZaApp.getInstance().getCertsServerListController().show(ZaServer.getAll());
	}
}

//When the individul server node under the certs tree item is clicked
ZaCert.certsServerNodeTreeListener = function (ev) {
	var serverNodeId = ev.item.getData(ZaOverviewPanelController._OBJ_ID) ;
	if(window.console && window.console.log) console.log("Click the server node: " + serverNodeId) ;
	
	if(ZaApp.getInstance().getCurrentController()) {
		ZaApp.getInstance().getCurrentController().switchToNextView(
			ZaApp.getInstance().getCertViewController(),
			ZaCertViewController.prototype.show,
			[ZaCert.getCerts(ZaApp.getInstance(), serverNodeId), serverNodeId]);
	} else {
		ZaApp.getInstance().getCertViewController().show(
			ZaCert.getCerts(ZaApp.getInstance(), serverNodeId),
			serverNodeId);
	}
}

if(ZaTabView.XFormModifiers["ZaHomeXFormView"]) {

    ZaHomeXFormView.onInstallCertficate = function (ev) {
        var certServerList = ZaApp.getInstance().getCertsServerListController();
        var serverList = ZaServer.getAll();
        if (serverList.size() > 0) {
            var lastServer = serverList.getVector().getLast();
            var certServerList = ZaApp.getInstance().getCertsServerListController();
	        ZaApp.getInstance().getCertsServerListController().show(serverList);
	       ZaCert.launchNewCertWizard.call (certServerList, lastServer.id) ;
        }
    }

    ZaCert.HomeXFormModifier = function(xFormObject) {
        if(ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CERTS_VIEW] || ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CARTE_BLANCHE_UI]) {
        
            var setupItem = ZaHomeXFormView.getHomeSetupItem(xFormObject);
            var labelItem = setupItem.headerLabels;
            var contentItem = setupItem.contentItems;
            var index;
            for (var index = 0; index < labelItem.length; index ++ ) {
                if (labelItem[index] == ZaMsg.LBL_HomeGetStared) {
                    break;
                }
            }
            if (index != labelItem.length) {
                var content = contentItem[index];
                content[2] = {value:ZaMsg.LBL_HomeInstallCert, onClick: ZaHomeXFormView.onInstallCertficate};
            }
        }
    }

    ZaTabView.XFormModifiers["ZaHomeXFormView"].push(ZaCert.HomeXFormModifier);
}

ZaCert.isCertExpired = function() {
	var value = this.getInstanceValue(ZaTask.A2_expiredCertMsg);
	return !(value === undefined);
}

ZaCert.isCertExpiring = function() {
	var value = this.getInstanceValue(ZaTask.A2_expiringCertMsg);
	return !(value === undefined);
}

// add cert expire status to Server Status
if (ZaTask && ZaTask.myXModel){
	ZaTask.A2_expiredCertMsg = "expiredCertMsg";
	ZaTask.A2_expiringCertMsg = "expiringCertMsg";
	ZaTask.myXModel.items.push(
			{id: ZaTask.A2_expiredCertMsg, type: _STRING_},
			{id: ZaTask.A2_expiringCertMsg, type: _STRING_});

	if (ZaTabView.XFormModifiers["ZaTaskContentView"] != null) {
		ZaCert.taskContentViewXFormModifier = function(xFormObject) {
			
			if(!(ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CERTS_VIEW] || ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CARTE_BLANCHE_UI])) {
		        return ;
		    }
			
			var board = ZaTaskContentView.getNotificationBoard(xFormObject);
			board.items.push(
				{type: _GROUP_, numCols:1, width: "98%", //100%
				 visibilityChecks:[ZaCert.isCertExpired],
				 visibilityChangeEventSources:[ZaTask.A2_expiredCertMsg],
				 items:[
					{type: _GROUP_, numCols:2, colSizes:["20px", "*"],
						cssStyle: "padding-left:5px; padding-top:8px;",
						items:[
							{type:_OUTPUT_, value: AjxImg.getImageHtml("Critical"), valign:_TOP_, bmolsnr: true
							},
							{type:_OUTPUT_, ref: ZaTask.A2_expiredCertMsg,
							 cssStyle:"padding-left:5px;", bmolsnr: true
							}
						]
					},
					{type:_OUTPUT_, align:_RIGHT_, value: com_zimbra_cert_manager.ManageCert,
					 containerCssClass:"ZaLinkedItem",
					 onClick: ZaCert.onManageCert
					},
					{type:_SPACER_, height:5}
				 ]
				},
				{type: _GROUP_, numCols:1, width: "98%", //100%
				 visibilityChecks:[ZaCert.isCertExpiring],
				 visibilityChangeEventSources:[ZaTask.A2_expiringCertMsg],
				 items:[
					{type: _GROUP_, numCols:2, colSizes:["20px", "*"],
						cssStyle: "padding-left:5px; padding-top:8px;",
						items:[
							{type:_OUTPUT_, value: AjxImg.getImageHtml("Warning"), valign:_TOP_, bmolsnr: true
							},
							{type:_OUTPUT_, ref: ZaTask.A2_expiringCertMsg,
							 cssStyle:"padding-left:5px;", bmolsnr: true
							}
						]
					},
					{type:_OUTPUT_, align:_RIGHT_, value: com_zimbra_cert_manager.ManageCert,
					 containerCssClass:"ZaLinkedItem",
					 onClick: ZaCert.onManageCert
					},
					{type:_SPACER_, height:5}
				 ]
				}
			);
		}
		ZaTabView.XFormModifiers["ZaTaskContentView"].push(ZaCert.taskContentViewXFormModifier);
		
	    ZaCert.onManageCert = function(ev) {
	        var tree = ZaZimbraAdmin.getInstance().getOverviewPanelController().getOverviewPanel().getFolderTree();
	        var path = ZaTree.getPathByArray([ZaMsg.OVP_home, ZaMsg.OVP_configure, com_zimbra_cert_manager.OVP_certs]);
	        tree.setSelectionByPath(path, false);
	    }		
	}
}

ZaCert.postLoadCertExpireStatus = function () {
	if(!(ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CERTS_VIEW] || ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CARTE_BLANCHE_UI])) {
        return ;
    }
	
	var callback = new AjxCallback(this, ZaCert.doLoadCertExpireStatus);
	ZaCert.getCerts(ZaApp.getInstance(), ZaCert.ALL_SERVERS, true, callback);
}
ZaHome.postLoadDataFunction.push(ZaCert.postLoadCertExpireStatus);

ZaCert.doLoadCertExpireStatus = function(resp) {
	if (resp && resp.getException && resp.getException()) {
		return;
	}
	
	var response = resp._data.Body.GetCertResponse;
	
	var now = new Date();
	var _30days = 30 * 24 * 60 * 60 * 1000; // the millisecond of 30 days
	var expiredCerts = new Array();
	var expiringCerts = new Array();
	var minExpDateForExpiredCerts = new Date(9999, 12, 31);
	var minExpDateForExpiringCerts = new Date(9999, 12, 31);
	for (var i in response.cert) {
		var cert = response.cert[i];
		var expDate = cert.notAfter? new Date(Date.parse(cert.notAfter[0]._content)) : null;
		if (!expDate) continue;
		if (now > expDate) {
			expiredCerts.push(cert);
			if (expDate < minExpDateForExpiredCerts) minExpDateForExpiredCerts = expDate;
		} else if (new Date(now.getTime() + _30days) > expDate) {
			expiringCerts.push(cert);
			if (expDate < minExpDateForExpiringCerts) minExpDateForExpiringCerts = expDate;
		}
	}
	
	var formatter = AjxDateFormat.getDateInstance(AjxDateFormat.LONG);
	var taskController = ZaZimbraAdmin.getInstance().getTaskController();
	if (expiredCerts.length > 0) {
		var message = AjxMessageFormat.format(com_zimbra_cert_manager.ExpiredCertMsg, [expiredCerts.length]);
		
		var expMsg = (expiredCerts.length > 1)?  
				AjxMessageFormat.format(com_zimbra_cert_manager.MultiExpDate, formatter.format(minExpDateForExpiredCerts)):
				AjxMessageFormat.format(com_zimbra_cert_manager.SingleExpDate, formatter.format(minExpDateForExpiredCerts));

		taskController.setInstanceValue(message + " " + expMsg, ZaTask.A2_expiredCertMsg);
		taskController.increaseNotificationCount(ZaTask.A2_expiredCertMsg);
	} else {
		taskController.setInstanceValue(undefined, ZaTask.A2_expiredCertMsg);
		taskController.decreaseNotificationCount(ZaTask.A2_expiredCertMsg);
	}

	
	if (expiringCerts.length > 0) {
		var message = AjxMessageFormat.format(com_zimbra_cert_manager.ExpiringCertMsg, [expiringCerts.length]);
		
		var expMsg = (expiringCerts.length > 1)?  
				AjxMessageFormat.format(com_zimbra_cert_manager.MultiExpDate, formatter.format(minExpDateForExpiringCerts)):
				AjxMessageFormat.format(com_zimbra_cert_manager.SingleExpDate, formatter.format(minExpDateForExpiringCerts));

		taskController.setInstanceValue(message + " " + expMsg, ZaTask.A2_expiringCertMsg);
		taskController.increaseNotificationCount(ZaTask.A2_expiringCertMsg);
	} else {
		taskController.setInstanceValue(undefined, ZaTask.A2_expiringCertMsg);
		taskController.decreaseNotificationCount(ZaTask.A2_expiringCertMsg);
	}
}

ZaCert.getCerts = function (app, serverId, isAsync, callback) {
	if(window.console && window.console.log) console.log("Getting certificates for server " + serverId) ;
	
	var soapDoc = AjxSoapDoc.create("GetCertRequest", "urn:zimbraAdmin", null);
	soapDoc.getMethod().setAttribute("type", "all");
	var csfeParams = new Object();
	csfeParams.soapDoc = soapDoc;
	if (isAsync) {
		csfeParams.asyncMode = true;
		csfeParams.callback = callback;
   }
	if (serverId != null) {
		soapDoc.getMethod().setAttribute("server", serverId);
	}else{
		if(window.console && window.console.log) console.log("Warning: serverId is missing for ZaCert.getCerts.") ;
	}
	
	try {
		var reqMgrParams = {} ;
		reqMgrParams.controller = app.getCurrentController();
		reqMgrParams.busyMsg = com_zimbra_cert_manager.BUSY_RETRIEVE_CERT;
		if (!isAsync) {
			resp = ZaRequestMgr.invoke(csfeParams, reqMgrParams).Body.GetCertResponse;
			return resp;
		} else {
			ZaRequestMgr.invoke(csfeParams, reqMgrParams);
		}
		
	}catch (ex) {
		app.getCurrentController()._handleException(ex, "ZaCert.getCerts", null, false);
	}
}

ZaCert.getCSR = function (app, serverId, type) {
	if(window.console && window.console.log) console.log("ZaCert.getCSR: Getting CSR for server: " + serverId) ;
	
	var soapDoc = AjxSoapDoc.create("GetCSRRequest", "urn:zimbraAdmin", null);
	soapDoc.getMethod().setAttribute("type", type);
	var csfeParams = new Object();
	csfeParams.soapDoc = soapDoc;	
	
	//if (serverId && serverId != ZaCert.ALL_SERVERS) {
	if (serverId != null){
		soapDoc.getMethod().setAttribute("server", serverId);
	}else{
		if(window.console && window.console.log) console.log("Warning: serverId is missing for ZaCert.getCSR.") ;
	}
	
	try {
		var reqMgrParams = {} ;
		reqMgrParams.controller = app.getCurrentController();
		reqMgrParams.busyMsg = com_zimbra_cert_manager.BUSY_GET_CSR ;
		resp = ZaRequestMgr.invoke(csfeParams, reqMgrParams ).Body.GetCSRResponse;
		return resp;	
	}catch (ex) {
		app.getCurrentController()._handleException(ex, "ZaCert.getCSR", null, false);
	}
}

ZaCert.genCSR = function (app, subject_attrs,  type, newCSR, serverId, keysize) {
	if(window.console && window.console.log) console.log("Generating certificates") ;
	var soapDoc = AjxSoapDoc.create("GenCSRRequest", "urn:zimbraAdmin", null);
	soapDoc.getMethod().setAttribute("type", type);
	soapDoc.getMethod().setAttribute("keysize", keysize) ;
    if (newCSR) {
		soapDoc.getMethod().setAttribute("new", "1");
	}else{
		soapDoc.getMethod().setAttribute("new", "0");		
	}
	
	if (serverId != null) {
		soapDoc.getMethod().setAttribute("server", serverId);
	}else{
		if(window.console && window.console.log) console.log("Warning: serverId is missing for ZaCert.genCSR.") ;
	}
	
	for (var n in subject_attrs) {
		if (n == ZaCert.A_subject_alt) {
			var subjectAlts = subject_attrs[n] ;
			if (( subjectAlts instanceof Array) && (subjectAlts.length > 0)){
				for (var i=0; i < subjectAlts.length; i ++) {
					soapDoc.set(n, subject_attrs[n][i]);
				}
			}
		}else{
			soapDoc.set(n, subject_attrs[n]) ;
		}
	}
	
	var csfeParams = new Object();
	csfeParams.soapDoc = soapDoc;	
	try {
		var reqMgrParams = {} ;
		reqMgrParams.controller = app.getCurrentController();
		reqMgrParams.busyMsg = com_zimbra_cert_manager.BUSY_GENERATE_CSR ;
		resp = ZaRequestMgr.invoke(csfeParams, reqMgrParams ).Body.GenCSRResponse;
		return resp;
	}catch (ex) {
		app.getCurrentController()._handleException(ex, "ZaCert.genCSR", null, false);
	}
}

ZaCert.installCert = function (app, params, serverId) {
	if(window.console && window.console.log) console.log("Installing certificates") ;
	var type = params.type ;
	var comm_cert = params.comm_cert ;
	var validation_days = params.validation_days ;
	var callback = params.callback ;
    var subject = params.subject ;
    var keysize = params.keysize ;
    //var allserver = 0 || params.allserver ;
	//if(window.console && window.console.log) console.log("allserver = " + allserver) ;
	
	var controller = app.getCurrentController();
	
	var certView = controller._contentView ;
	certView._certInstallStatus.setStyle (DwtAlert.INFORMATION) ;
	certView._certInstallStatus.setContent(com_zimbra_cert_manager.CERT_INSTALLING );
	certView._certInstallStatus.setDisplay(Dwt.DISPLAY_BLOCK) ;
	
	var soapDoc = AjxSoapDoc.create("InstallCertRequest", "urn:zimbraAdmin", null);
	soapDoc.getMethod().setAttribute("type", type);
	if (serverId != null) {
		soapDoc.getMethod().setAttribute("server", serverId);
	}else{
		if(window.console && window.console.log) console.log("Warning: serverId is missing for ZaCert.installCert.") ;
	}
	
	if (type == ZaCert.A_type_self || type == ZaCert.A_type_comm) {
		soapDoc.set(ZaCert.A_validation_days, validation_days);

		//soapDoc.set(ZaCert.A_allserver, allserver) ;
		if (type == ZaCert.A_type_comm) {
			//set the comm_cert element
			soapDoc.set("comm_cert", comm_cert);	
		}else if (type == ZaCert.A_type_self) {
            soapDoc.set(ZaCert.A_keysize, keysize) ;
        }
        //add the subject element and subjectAltNames element
        if (subject != null) {
            var subject_attrs = {} ;
            for (var n in subject) {
                if (n == ZaCert.A_subject_alt) {
                   var subjectAlts = subject[n] ;
                    if (( subjectAlts instanceof Array) && (subjectAlts.length > 0)){
                        for (var i=0; i < subjectAlts.length; i ++) {
                            soapDoc.set(n, subjectAlts[i]);
                        }
                    }
                }else{
                    subject_attrs [n] = subject[n] ;
                }
            }
            soapDoc.set("subject", subject_attrs);
        }
    }else {
		throw new AjxException (com_zimbra_cert_manager.UNKNOW_INSTALL_TYPE_ERROR, "ZaCert.installCert") ;		
	}
	
	var csfeParams = new Object();
	csfeParams.soapDoc = soapDoc;	
	var reqMgrParams = {} ;
	reqMgrParams.controller = app.getCurrentController();
	reqMgrParams.busyMsg = com_zimbra_cert_manager.BUSY_INSTALL_CERT ;
	if (callback) {
		csfeParams.callback = callback;
		csfeParams.asyncMode = true ;	
	}
	ZaRequestMgr.invoke(csfeParams, reqMgrParams ) ;
}


ZaCert.verifyCertKey = function (app, params) {
	var cert = params.cert;
	var prvkey = params.prvkey;
	var controller = app.getCurrentController();
	var callback = params.callback;
	var type = params.type;

	var soapDoc = AjxSoapDoc.create("VerifyCertKeyRequest", "urn:zimbraAdmin", null);
        soapDoc.getMethod().setAttribute("type", type);
        soapDoc.getMethod().setAttribute("cert", cert);
        soapDoc.getMethod().setAttribute("privkey", prvkey);
        
        var csfeParams = new Object();
        csfeParams.soapDoc = soapDoc;
        try {
                var reqMgrParams = {} ;
                reqMgrParams.controller = controller;
                reqMgrParams.busyMsg = com_zimbra_cert_manager.BUSY_VERIFY_CERTKEY;
                resp = ZaRequestMgr.invoke(csfeParams, reqMgrParams ).Body.VerifyCertKeyResponse;
                return resp;
        }catch (ex) {
                app.getCurrentController()._handleException(ex, "ZaCert.verifyCertKey", null, false);
        }


}


ZaCert.prototype.setTargetServer = function (serverId) {
		this[ZaCert.A_target_server] = serverId ;
}

ZaCert.launchNewCertWizard = function (serverId) {
	try {
		//if(!ZaApp.getInstance().dialogs["certInstallWizard"])
			ZaApp.getInstance().dialogs["certInstallWizard"] = new ZaCertWizard (this._container);	
		
		this._cert = new ZaCert(ZaApp.getInstance());
		this._cert.setTargetServer (serverId);		
		this._cert.init() ;
		ZaApp.getInstance().dialogs["certInstallWizard"].setObject(this._cert);
		ZaApp.getInstance().dialogs["certInstallWizard"].popup();
	} catch (ex) {
		this._handleException(ex, "ZaCert.launchNewCertWizard", null, false);
	}
}

ZaCert.myXModel = {
	items: [
		{id: ZaCert.A_installStatus, type: _STRING_, ref: ZaCert.A_installStatus },
		{id: ZaCert.A_subject_alt, type: _LIST_, ref:"attrs/" + ZaCert.A_subject_alt, listItem:{type:_STRING_}},
		{id: ZaCert.A_target_server, type:_STRING_ , ref: ZaCert.A_target_server },
		{id: ZaCert.A_countryName, type: _STRING_, ref: "attrs/" + ZaCert.A_countryName, length: 2, pattern: /^\s*[a-zA-Z0-9\/\.\-\\_:\@\=\'\*]*$/ },
        {id: ZaCert.A_keysize, type: _STRING_, ref: ZaCert.A_keysize},
		{id: ZaCert.A_commonName, type: _STRING_, ref: "attrs/" + ZaCert.A_commonName },
		{id: ZaCert.A_state, type: _STRING_, ref: "attrs/" + ZaCert.A_state, pattern: /^\s*[a-zA-Z0-9\/\.\-\\_:\@\=\'\*]*$/ },
		{id: ZaCert.A_city, type: _STRING_, ref: "attrs/" + ZaCert.A_city, pattern: /^\s*[a-zA-Z0-9\/\.\-\\_:\@\=\'\*\s]*$/ },
		{id: ZaCert.A_organization, type: _STRING_, ref: "attrs/" + ZaCert.A_organization, pattern: /^\s*[a-zA-Z0-9\/\.\-\\_:\@\=\,\'\*\s]*$/ },
		{id: ZaCert.A_organizationUnit, type: _STRING_, ref: "attrs/" + ZaCert.A_organizationUnit, pattern: /^\s*[a-zA-Z0-9\/\.\-\\_:\@\=\,\'\*\s]*$/ },
		{id: ZaCert.A_validation_days, type: _NUMBER_, ref: ZaCert.A_validation_days, required: true },
		{id: ZaCert.A_type_comm, type: _ENUM_, ref: ZaCert.A_type_comm, choices:ZaModel.BOOLEAN_CHOICES1 },
		{id: ZaCert.A_type_self, type: _ENUM_, ref: ZaCert.A_type_self, choices:ZaModel.BOOLEAN_CHOICES1 },
		{id: ZaCert.A_type_csr, type: _ENUM_, ref: ZaCert.A_type_csr, choices:ZaModel.BOOLEAN_CHOICES1 },
		{id: ZaCert.A_csr_exists, type: _ENUM_, ref: ZaCert.A_csr_exists, choices:ZaModel.BOOLEAN_CHOICES1 },
		{id: ZaCert.A_force_new_csr, type: _ENUM_, ref: ZaCert.A_force_new_csr, choices:ZaModel.BOOLEAN_CHOICES }
	]
}

ZaCert.getWildCardServerName = function (serverName)  {
	var pattern = /^.*(\.[^\.]+\.[^\.]+)$/
	if (serverName) {
		var results =  serverName.match(pattern) ;
		if (results != null) {
			return "*" + results[1] ;
		}
	}
	
	return serverName ;
}
}
