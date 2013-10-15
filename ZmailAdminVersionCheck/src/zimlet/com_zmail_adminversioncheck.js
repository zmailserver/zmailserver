/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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
if(ZaSettings && ZaSettings.EnabledZimlet["org_zmail_adminversioncheck"]){
if(window.console && window.console.log) console.log("Start loading org_zmail_adminversioncheck.js");
function ZaVersionCheck() {
	ZaItem.call(this,"ZaVersionCheck");
	this.attrs = new Object();
	this.attrsToGet = [ZaVersionCheck.A_zmailVersionCheckLastAttempt,
		ZaVersionCheck.A_zmailVersionCheckLastSuccess,
		ZaVersionCheck.A_zmailVersionCheckNotificationEmail,
		ZaVersionCheck.A_zmailVersionCheckInterval,
		ZaVersionCheck.A_zmailVersionCheckServer,
		ZaVersionCheck.A_zmailVersionCheckURL]
};
ZaVersionCheck.prototype = new ZaItem;
ZaVersionCheck.prototype.constructor = ZaVersionCheck;
ZaVersionCheck.prototype.errorMsg = "";
ZaVersionCheck.prototype.isAvailable = false;
// URL to direct to downloads
// TODO: Figure out if this can be moved to a properties file
// OLD: ZaVersionCheck.downloadUrl = "http://www.zmail.com/community/downloads.html";
ZaVersionCheck.downloadUrl = "http://www.zmail.com/downloads/ne-downloads.html";

ZaItem.loadMethods["ZaVersionCheck"] = new Array();
ZaItem.modifyMethods["ZaVersionCheck"] = new Array();

ZaOperation.VERSION_CHECK = ++ZA_OP_INDEX;
ZaVersionCheck.INVALID_VC_RESPONSE = "versioncheck.INVALID_VC_RESPONSE";
//constants
ZaVersionCheck.A_zmailVersionCheckLastAttempt = "zmailVersionCheckLastAttempt";
ZaVersionCheck.A_zmailVersionCheckLastSuccess = "zmailVersionCheckLastSuccess";
//ZaVersionCheck.A_zmailVersionCheckLastResponse = "zmailVersionCheckLastResponse";
ZaVersionCheck.A_zmailVersionCheckNotificationEmail = "zmailVersionCheckNotificationEmail";
ZaVersionCheck.A_zmailVersionCheckSendNotifications = "zmailVersionCheckSendNotifications";
ZaVersionCheck.A_zmailVersionCheckNotificationEmailFrom = "zmailVersionCheckNotificationEmailFrom";
ZaVersionCheck.A_zmailVersionCheckNotificationSubject = "zmailVersionCheckNotificationSubject";
ZaVersionCheck.A_zmailVersionCheckNotificationBody = "zmailVersionCheckNotificationBody";
ZaVersionCheck.A_zmailVersionCheckInterval = "zmailVersionCheckInterval";
ZaVersionCheck.A_zmailVersionCheckServer = "zmailVersionCheckServer";
ZaVersionCheck.A_zmailVersionCheckURL = "zmailVersionCheckURL";
ZaVersionCheck.A_zmailVersionCheckUpdates = "updates";
ZaVersionCheck.A_zmailVersionCheckUpdateType = "type";
ZaVersionCheck.A_zmailVersionCheckUpdateCritical = "critical";
ZaVersionCheck.A_zmailVersionCheckUpdateVersion = "version";
ZaVersionCheck.A_zmailVersionCheckUpdateBuildtype = "buildtype";
ZaVersionCheck.A_zmailVersionCheckUpdateUpdateURL = "updateURL";
ZaVersionCheck.A_zmailVersionCheckUpdateDescription = "description";
ZaVersionCheck.A_zmailVersionCheckUpdateShortversion = "shortversion";

if(ZaSettings) {
	ZaSettings.SOFTWARE_UPDATES_VIEW = "softwareUpdatesView";
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.SOFTWARE_UPDATES_VIEW, label: org_zmail_adminversioncheck.UI_Comp_versionCheck });
	ZaSettings.OVERVIEW_TOOLS_ITEMS.push(ZaSettings.SOFTWARE_UPDATES_VIEW);
	ZaSettings.VIEW_RIGHTS [ZaSettings.SOFTWARE_UPDATES_VIEW] = "adminConsoleSoftwareUpdatesRights";
}

ZaVersionCheck.myXModel = {	items:[
	{id:ZaVersionCheck.A_zmailVersionCheckLastAttempt, ref:"attrs/" + ZaVersionCheck.A_zmailVersionCheckLastAttempt, type: _DATETIME_},
    {id:ZaVersionCheck.A_zmailVersionCheckLastSuccess, ref:"attrs/" + ZaVersionCheck.A_zmailVersionCheckLastSuccess, type: _DATETIME_},
    {id:ZaVersionCheck.A_zmailVersionCheckNotificationEmail, ref:"attrs/" + ZaVersionCheck.A_zmailVersionCheckNotificationEmail, type: _STRING_},
    {id:ZaVersionCheck.A_zmailVersionCheckNotificationEmailFrom, ref:"attrs/" + ZaVersionCheck.A_zmailVersionCheckNotificationEmailFrom, type: _STRING_},
    {id:ZaVersionCheck.A_zmailVersionCheckNotificationSubject, ref:"attrs/" + ZaVersionCheck.A_zmailVersionCheckNotificationSubject, type: _STRING_},
    {id:ZaVersionCheck.A_zmailVersionCheckNotificationBody, ref:"attrs/" + ZaVersionCheck.A_zmailVersionCheckNotificationBody, type: _STRING_},
    {id:ZaVersionCheck.A_zmailVersionCheckSendNotifications, ref:"attrs/" +  ZaVersionCheck.A_zmailVersionCheckSendNotifications,  type:_ENUM_, choices: ZaModel.BOOLEAN_CHOICES},        
    {id:ZaVersionCheck.A_zmailVersionCheckServer, ref:"attrs/" + ZaVersionCheck.A_zmailVersionCheckServer, type: _STRING_},
	{id:ZaVersionCheck.A_zmailVersionCheckURL, ref:"attrs/" + ZaVersionCheck.A_zmailVersionCheckURL, type: _STRING_},
	{id:ZaVersionCheck.A_zmailVersionCheckInterval, type:_MLIFETIME_, ref:"attrs/"+ZaVersionCheck.A_zmailVersionCheckInterval},
	{id:ZaVersionCheck.A_zmailVersionCheckUpdates, type:_LIST_, listItem:
		{type:_OBJECT_, 
			items: [
				{id:ZaVersionCheck.A_zmailVersionCheckUpdateType, type:_STRING_},
				{id:ZaVersionCheck.A_zmailVersionCheckUpdateCritical, type:_ENUM_, choices: ZaModel.BOOLEAN_CHOICES2},
				{id:ZaVersionCheck.A_zmailVersionCheckUpdateVersion, type:_STRING_},
				{id:ZaVersionCheck.A_zmailVersionCheckUpdateBuildtype, type:_STRING_},
				{id:ZaVersionCheck.A_zmailVersionCheckUpdateUpdateURL, type:_STRING_},
				{id:ZaVersionCheck.A_zmailVersionCheckUpdateDescription, type:_STRING_},
				{id:ZaVersionCheck.A_zmailVersionCheckUpdateShortversion, type:_STRING_}
			]
		}
	}
]};

ZaVersionCheck.getAttemptTime =
function (serverStr) {
	if (serverStr) {
		return ZaItem.formatServerTime(serverStr);
	}else{
		return org_zmail_adminversioncheck.Never;
	}
}

ZaVersionCheck.checkNow = function() {
	var params, soapDoc;
	soapDoc = AjxSoapDoc.create("BatchRequest", "urn:zmail");
    soapDoc.setMethodAttribute("onerror", "continue");
	
	var versionCheck = soapDoc.set("VersionCheckRequest", null, null, ZaZmailAdmin.URN);
	versionCheck.setAttribute("action","check");

	try {
		params = new Object();
		params.soapDoc = soapDoc;	
		var reqMgrParams ={
			controller:ZaApp.getInstance().getCurrentController()
		}
		var respObj = ZaRequestMgr.invoke(params, reqMgrParams);
		if(respObj.isException && respObj.isException()) {
			ZaApp.getInstance().getCurrentController()._handleException(respObj.getException(), "ZaVersionCheck.checkNow", null, false);
		    hasError  = true ;
        } else if(respObj.Body.BatchResponse.Fault) {
			var fault = respObj.Body.BatchResponse.Fault;
			if(fault instanceof Array)
				fault = fault[0];
			
			if (fault) {
				// JS response with fault
				var ex = ZmCsfeCommand.faultToEx(fault);
				ZaApp.getInstance().getCurrentController()._handleException(ex,"ZaVersionCheck.checkNow", null, false);
                hasError = true ;
            }
		} 
	} catch (ex) {
		//show the error and go on
		//we should not stop the Account from loading if some of the information cannot be acces
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaVersionCheck.loadMethod", null, false);
    }		
}

ZaVersionCheck.loadMethod = 
function(by, val) {
	var params, soapDoc;
	soapDoc = AjxSoapDoc.create("BatchRequest", "urn:zmail");
    soapDoc.setMethodAttribute("onerror", "continue");
    var getConfigDoc = soapDoc.set("GetAllConfigRequest", null, null, ZaZmailAdmin.URN);	
	if(!this.getAttrs.all && !AjxUtil.isEmpty(this.attrsToGet)) {
		getConfigDoc.setAttribute("attrs", this.attrsToGet.join(","));
	}	

	var versionCheck = soapDoc.set("VersionCheckRequest", null, null, ZaZmailAdmin.URN);
	versionCheck.setAttribute("action","status");
	this.errorMsg = "";
	this.isAvailable = false;

	try {
		params = new Object();
		params.soapDoc = soapDoc;
		var reqMgrParams ={
			controller:ZaApp.getInstance().getCurrentController()
		}
		var respObj = ZaRequestMgr.invoke(params, reqMgrParams);
		if(respObj.isException && respObj.isException()) {
			var ex = respObj.getException();
			//ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaVersionCheck.loadMethod", null, false);
			throw ex;
		} else if(respObj.Body.BatchResponse.Fault) {
			var fault = respObj.Body.BatchResponse.Fault;
			if(fault instanceof Array)
				fault = fault[0];
			
			if (fault) {
				// JS response with fault
				var ex = ZmCsfeCommand.faultToEx(fault);
				//ZaApp.getInstance().getCurrentController()._handleException(ex,"ZaVersionCheck.loadMethod", null, false);
				this.errorMsg = ex.getErrorMsg();
				//get the error msg then go on to read the configuration from server with all effort
			}
		} 
		var batchResp = respObj.Body.BatchResponse;
			
		if(batchResp.GetAllConfigResponse) {
			resp = batchResp.GetAllConfigResponse[0];
			this.initFromJS(resp);
		}

		if(batchResp.VersionCheckResponse) {
			var resp = batchResp.VersionCheckResponse[0];
			this[ZaVersionCheck.A_zmailVersionCheckUpdates] = [];
			if(resp && resp.versionCheck && resp.versionCheck[0] && resp.versionCheck[0].updates) {
				if(resp.versionCheck[0].updates instanceof Array && resp.versionCheck[0].updates.length>0 && 
				resp.versionCheck[0].updates[0].update && resp.versionCheck[0].updates[0].update.length>0) {
					var cnt = resp.versionCheck[0].updates[0].update.length;
					for(var i = 0; i< cnt; i++) {
						this[ZaVersionCheck.A_zmailVersionCheckUpdates].push(resp.versionCheck[0].updates[0].update[i]);
					}
					this.isAvailable = true;
				}
			}
		}
				
			
	} catch (ex) {
		//no show the error now and go on
		//we should not stop the Account from loading if some of the information cannot be acces
		//ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaVersionCheck.loadMethod", null, false);
		this.errorMsg = ex.getErrorMsg();
    }
}
ZaItem.loadMethods["ZaVersionCheck"].push(ZaVersionCheck.loadMethod);

ZaVersionCheck.modifyMethod = function (mods) {
	var soapDoc = AjxSoapDoc.create("ModifyConfigRequest", ZaZmailAdmin.URN, null);
	for (var aname in mods) {
		//multy value attribute
		if(mods[aname] instanceof Array) {
			var cnt = mods[aname].length;
			if(cnt > 0) {
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
			else {
				var attr = soapDoc.set("a");
				attr.setAttribute("n", aname);
			}
		} else {
			var attr = soapDoc.set("a", mods[aname]);
			attr.setAttribute("n", aname);
		}
	}
	var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;
	params.noAuthToken = true;	
	command.invoke(params);
}
ZaItem.modifyMethods["ZaVersionCheck"].push(ZaVersionCheck.modifyMethod);

ZaZmailAdmin._VERSION_CHECK_VIEW = ZaZmailAdmin.VIEW_INDEX++;

ZaApp.prototype.getVersionCheckViewController =
function() {
	if (this._controllers[ZaZmailAdmin._VERSION_CHECK_VIEW] == null)
		this._controllers[ZaZmailAdmin._VERSION_CHECK_VIEW] = new ZaVersionCheckViewController(this._appCtxt, this._container);
	return this._controllers[ZaZmailAdmin._VERSION_CHECK_VIEW];
}

ZaVersionCheck.versionCheckTreeListener = function (ev) {
	var versionCheck = new ZaVersionCheck();
	
	if(ZaApp.getInstance().getCurrentController()) {
		ZaApp.getInstance().getCurrentController().switchToNextView(ZaApp.getInstance().getVersionCheckViewController(),ZaVersionCheckViewController.prototype.show, [versionCheck]);
	} else {					
		ZaApp.getInstance().getVersionCheckViewController().show(versionCheck);
	}
}

ZaVersionCheck.versionCheckTreeModifier = function (tree) {
	var overviewPanelController = this ;
	if (!overviewPanelController) throw new Exception("ZaCert.versionCheckTreeModifier: Overview Panel Controller is not set.");	
	if(ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.SOFTWARE_UPDATES_VIEW] || ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CARTE_BLANCHE_UI]) {
        if (!appNewUI) {
            if(!this._toolsTi) {
                this._toolsTi = new DwtTreeItem(tree, null, null, null, null, "overviewHeader");
                this._toolsTi.enableSelection(false);
                this._toolsTi.setText(ZaMsg.OVP_tools);
                this._toolsTi.setData(ZaOverviewPanelController._TID, ZaZmailAdmin._TOOLS);
            }

            this._versionCheckTi = new DwtTreeItem({parent:this._toolsTi,className:"AdminTreeItem"});
            this._versionCheckTi.setText(org_zmail_adminversioncheck.OVP_versionCheck);
            this._versionCheckTi.setImage("AdminRefresh");
            this._versionCheckTi.setData(ZaOverviewPanelController._TID, ZaZmailAdmin._VERSION_CHECK_VIEW);
        } else {
            var parentPath = ZaTree.getPathByArray([ZaMsg.OVP_home, ZaMsg.OVP_toolMig]);

            var ti = new ZaTreeItemData({
                                    parent:parentPath,
                                    id:ZaId.getTreeItemId(ZaId.PANEL_APP,"magHV",null, "VersionCheckHV"),
                                    text: org_zmail_adminversioncheck.OVP_versionCheck,
                                    mappingId: ZaZmailAdmin._VERSION_CHECK_VIEW});
            tree.addTreeItemData(ti);
        }
		if(ZaOverviewPanelController.overviewTreeListeners) {
			ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._VERSION_CHECK_VIEW] = ZaVersionCheck.versionCheckTreeListener;
		}
	}
}

if(ZaOverviewPanelController.treeModifiers)
	ZaOverviewPanelController.treeModifiers.push(ZaVersionCheck.versionCheckTreeModifier);

if (ZaHome && ZaHome.myXModel) {
    ZaHome.A2_versionUpdateAvailable = "versionUpdateAvailable";
    ZaHome.A2_updateMessage = "A2_updateMessage";
    ZaHome.myXModel.items.push(
        {id:ZaHome.A2_versionUpdateAvailable, type:_ENUM_, ref: "attrs/" + ZaHome.A2_versionUpdateAvailable, choices: ZaModel.BOOLEAN_CHOICES}
    );
    ZaHome.myXModel.items.push(
        {id:ZaHome.A2_updateMessage, type:_STRING_, ref: "attrs/" + ZaHome.A2_updateMessage}
    );
    ZaHome.loadVersionMethod =
    function () {

        this.attrs[ZaHome.A2_versionUpdateAvailable] = false;
        // TODO: should use the load method to get version information
        this.attrs[ZaHome.A2_updateMessage] = "Zmail 8.1 is available";
    }
    ZaItem.loadMethods["ZaHome"].push(ZaHome.loadVersionMethod);
}

if(ZaTabView.XFormModifiers["ZaHomeXFormView"]) {

    ZaHomeXFormView.onViewVersionUpdate = function(ev) {
        var tree = ZaZmailAdmin.getInstance().getOverviewPanelController().getOverviewPanel().getFolderTree();
        var path = ZaTree.getPathByArray([ZaMsg.OVP_home, ZaMsg.OVP_toolMig, org_zmail_adminversioncheck.OVP_versionCheck]);
        tree.setSelectionByPath(path, false);
    }

    ZaVersionCheck.HomeXFormModifier = function(xFormObject) {
        if(ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.SOFTWARE_UPDATES_VIEW] || ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CARTE_BLANCHE_UI]) {
            var infoItem = ZaHomeXFormView.getWarningPanelItem(xFormObject);
            infoItem.items.push(
                {type:_GROUP_, numCols:3,  width:"100%", colSizes:ZaHomeXFormView.getWarningPanelCol(),
                    visibilityChecks:[[XForm.checkInstanceValue,ZaHome.A2_versionUpdateAvailable,true]],
                    items:[
                    {type:_OUTPUT_, ref: ZaHome.A2_versionUpdateAvailable,
                        getDisplayValue: function (value){
                            if (value) {
                                return AjxImg.getImageHtml ("Information");
                            }
                        }
                    },
                    {type:_OUTPUT_, ref: ZaHome.A2_updateMessage},
                    {type:_OUTPUT_, value:org_zmail_adminversioncheck.LBL_ViewUpdate, containerCssStyle:"cursor:pointer;color:white",onClick: ZaHomeXFormView.onViewVersionUpdate}
                ]});
        }
    }

    ZaTabView.XFormModifiers["ZaHomeXFormView"].push(ZaVersionCheck.HomeXFormModifier);
}


if (ZaTask && ZaTask.myXModel){
	ZaTask.A2_versionCanBeShown = "versionCanBeShownForTask";
	ZaTask.A2_versionUpdateAvailable = "versionUpdateAvailableForTask";
	ZaTask.A2_versionHasError = "versionHasError";
	ZaTask.A2_versionUpdateMessage = "updateMessageForTask";
	ZaTask.myXModel.items.push(
	    {id:ZaTask.A2_versionUpdateAvailable, type:_ENUM_, ref: "attrs/" + ZaTask.A2_versionUpdateAvailable, choices: ZaModel.BOOLEAN_CHOICES}
	);
	ZaTask.myXModel.items.push(
	    {id:ZaTask.A2_versionUpdateMessage, type:_STRING_, ref: "attrs/" + ZaTask.A2_versionUpdateMessage}
	);
	ZaTask.myXModel.items.push({id: ZaTask.A2_versionCanBeShown, type:_ENUM_, choices: ZaModel.BOOLEAN_CHOICES, defaultValue: false});
	ZaTask.myXModel.items.push({id: ZaTask.A2_versionHasError, type:_ENUM_, choices: ZaModel.BOOLEAN_CHOICES, defaultValue: false});

	if (ZaTabView.XFormModifiers["ZaTaskContentView"] != null) {
		ZaTask.loadVersionMethod =
		function () {
		    this.attrs[ZaTask.A2_versionUpdateAvailable] = false;
		    this.attrs[ZaTask.A2_versionUpdateMessage] = "";
		}
		ZaItem.loadMethods["ZaTask"].push(ZaTask.loadVersionMethod);

		ZaVersionCheck.taskContentViewXFormModifier = function(xFormObject) {
			if( !ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.SOFTWARE_UPDATES_VIEW] &&
				!ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CARTE_BLANCHE_UI]) {
				return;
			}

			var board = ZaTaskContentView.getNotificationBoard(xFormObject);
			board.items.push(
				{type: _GROUP_, numCols:1, width: "98%", //100%
					visibilityChecks:[[XForm.checkInstanceValue, ZaTask.A2_versionCanBeShown, true]],
					visibilityChangeEventSources:[ZaTask.A2_versionCanBeShown],
					items:[
						{type: _GROUP_, numCols:2, colSizes:["20px", "*"],
							cssStyle: "padding-left:5px; padding-top:8px;",
							items:[
								{type:_OUTPUT_, valign:_TOP_, ref:ZaTask.A2_versionHasError, bmolsnr: true,
									getDisplayValue: function (value){
										if (value) {
											return AjxImg.getImageHtml ("Warning");
										} else {
											return AjxImg.getImageHtml ("Information");
										}
									}
								},
								{type:_OUTPUT_, ref: ZaTask.A2_versionUpdateMessage,
									cssStyle:"padding-left:5px;", bmolsnr: true
								}]
						},
						{type:_OUTPUT_, align:_RIGHT_, value:org_zmail_adminversioncheck.LBL_ViewUpdate,
							visibilityChecks:[[XForm.checkInstanceValue, ZaTask.A2_versionHasError, true]],
							visibilityChangeEventSources:[ZaTask.A2_versionHasError],
							containerCssClass: "ZaLinkedItem",
							onClick: ZaHomeXFormView.onViewVersionUpdate
						},
						{type:_OUTPUT_, align:_RIGHT_, value:org_zmail_adminversioncheck.LBL_GO_TO_DOWNLOAD_URL,
							visibilityChecks:[[XForm.checkInstanceValue, ZaTask.A2_versionHasError, false], [XForm.checkInstanceValue, ZaTask.A2_versionUpdateAvailable, true]],
							visibilityChangeEventSources:[ZaTask.A2_versionHasError, ZaTask.A2_versionUpdateAvailable],
							containerCssClass: "ZaLinkedItem",
							onClick: ZaVersionCheck.goToDownloadUrl
						},
						{type:_SPACER_, height:5}
					]
				}
			);
		}
		ZaTabView.XFormModifiers["ZaTaskContentView"].push(ZaVersionCheck.taskContentViewXFormModifier);

		ZaVersionCheck.goToDownloadUrl = function(){
			window.open(ZaVersionCheck.downloadUrl, "_blank");
		}

		ZaTaskContentView.postLoadVersionUpdateInfo = function() {
			if( !ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.SOFTWARE_UPDATES_VIEW] &&
				!ZaSettings.ENABLED_UI_COMPONENTS[ZaSettings.CARTE_BLANCHE_UI]) {
				return;
			}

			if (this._versionCheck == null) {
				this._versionCheck = new ZaVersionCheck();
			}
			this._versionCheck.load();

			var canShow = false;
			var msg = "";
			var hasError = (this._versionCheck.errorMsg != "");
			var isAvailable = this._versionCheck.isAvailable;
			if (hasError) {
				canShow = true;
				msg = org_zmail_adminversioncheck.WARNING_CURRENT_ATTEMPT_FAILED;
				//TODO: let user can see the this._versionCheck.errorMsg as detail, after he clicking the msg;
			} else if (isAvailable) {
				canShow = true;
				msg = org_zmail_adminversioncheck.UpdatesAreAvailable;
				//msg = "Zmail 8.1 is available"; //test
			}

			var taskController = ZaZmailAdmin.getInstance().getTaskController();
			taskController.setInstanceValue(msg, ZaTask.A2_versionUpdateMessage);
			taskController.setInstanceValue(canShow, ZaTask.A2_versionCanBeShown);
			taskController.setInstanceValue(isAvailable, ZaTask.A2_versionUpdateAvailable);
			taskController.setInstanceValue(hasError, ZaTask.A2_versionHasError);

			if (canShow) {
				taskController.increaseNotificationCount(ZaTask.A2_versionUpdateMessage);
			} else {
				taskController.decreaseNotificationCount(ZaTask.A2_versionUpdateMessage);
			}
		}


		if ( ZaTask.postLoadDataFunction != null ){
			ZaTask.postLoadDataFunction.push( ZaTaskContentView.postLoadVersionUpdateInfo );
		}
	}
}

}

