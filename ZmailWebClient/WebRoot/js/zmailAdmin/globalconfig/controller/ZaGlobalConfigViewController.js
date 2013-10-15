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

/**
* @class ZaGlobalConfigViewController 
* @contructor ZaGlobalConfigViewController
* @param appCtxt
* @param container
* @param app
* @author Greg Solovyev
**/
ZaGlobalConfigViewController = function(appCtxt, container) {
	ZaXFormViewController.call(this, appCtxt, container, "ZaGlobalConfigViewController");
	this._UICreated = false;
	this._helpURL = location.pathname + ZaUtil.HELP_URL + "managing_global_settings/global_settings.htm?locid="+AjxEnv.DEFAULT_LOCALE;
	this._helpButtonText = ZaMsg.helpManageGlobalSettings;
	this.objType = ZaEvent.S_GLOBALCONFIG;
	this.tabConstructor = GlobalConfigXFormView;					
}

ZaGlobalConfigViewController.prototype = new ZaXFormViewController();
ZaGlobalConfigViewController.prototype.constructor = ZaGlobalConfigViewController;

//ZaGlobalConfigViewController.STATUS_VIEW = "ZaGlobalConfigViewController.STATUS_VIEW";
ZaController.initToolbarMethods["ZaGlobalConfigViewController"] = new Array();
ZaController.initPopupMenuMethods["ZaGlobalConfigViewController"] = new Array();
ZaController.setViewMethods["ZaGlobalConfigViewController"] = [];
ZaController.changeActionsStateMethods["ZaGlobalConfigViewController"] = [];
ZaXFormViewController.preSaveValidationMethods["ZaGlobalConfigViewController"] = new Array();
//qin
ZaController.saveChangeCheckMethods["ZaGlobalConfigViewController"] = new Array();

/**
* Adds listener to removal of an ZaDomain 
* @param listener
**/
ZaGlobalConfigViewController.prototype.addSettingsChangeListener = 
function(listener) {
	this._evtMgr.addListener(ZaEvent.E_MODIFY, listener);
}

ZaGlobalConfigViewController.prototype.show = 
function(item, openInNewTab) {
	this._setView(item, false);
}

ZaGlobalConfigViewController.initPopupMenuMethod =
function () {
	this._popupOperations[ZaOperation.SAVE] = new ZaOperation(ZaOperation.SAVE, ZaMsg.TBB_Save, ZaMsg.ALTBB_Save_tt, "Save", "SaveDis", new AjxListener(this, this.saveButtonListener));    			
	this._popupOperations[ZaOperation.DOWNLOAD_GLOBAL_CONFIG] = new ZaOperation(ZaOperation.DOWNLOAD_GLOBAL_CONFIG, ZaMsg.TBB_DownloadConfig, ZaMsg.GLOBTBB_DownloadConfig_tt, "DownloadGlobalConfig", "DownloadGlobalConfig", new AjxListener(this, this.downloadConfigButtonListener));

}
ZaController.initPopupMenuMethods["ZaGlobalConfigViewController"].push(ZaGlobalConfigViewController.initPopupMenuMethod);

ZaGlobalConfigViewController.setViewMethod = function (entry) {
    try {    	
    	entry.load();
	} catch (ex) {
		this._handleException(ex, "ZaGlobalConfigViewController.prototype.show", null, false);
	}
	entry[ZaModel.currentTab] = "1"
	this._currentObject = entry;
	this._createUI(entry);
     
	ZaApp.getInstance().pushView(this.getContentViewId());
	this._view.setDirty(false);
	this._view.setObject(entry); 	//setObject is delayed to be called after pushView in order to avoid jumping of the view
}
ZaController.setViewMethods["ZaGlobalConfigViewController"].push(ZaGlobalConfigViewController.setViewMethod) ;

/**
* @method _createUI
**/
ZaGlobalConfigViewController.prototype._createUI =
function (entry) {
	this._contentView = this._view = new this.tabConstructor(this._container, entry);

    this._initPopupMenu();
	//always add Help button at the end of the toolbar
	
	var elements = new Object();
	elements[ZaAppViewMgr.C_APP_CONTENT] = this._view;
    ZaApp.getInstance().getAppViewMgr().createView(this.getContentViewId(), elements);
	this._UICreated = true;
	ZaApp.getInstance()._controllers[this.getContentViewId ()] = this ;
}

ZaGlobalConfigViewController.prototype.setEnabled = 
function(enable) {
	this._view.setEnabled(enable);
}

ZaGlobalConfigViewController.changeActionsStateMethod =
function () {
    var isToEnable = (this._view && this._view.isDirty());
    if(this._popupOperations[ZaOperation.SAVE]) {
        this._popupOperations[ZaOperation.SAVE].enabled = isToEnable;
    }
}
ZaController.changeActionsStateMethods["ZaGlobalConfigViewController"].push(ZaGlobalConfigViewController.changeActionsStateMethod);


/**
* handles "download" button click. Launches file download in a new window
**/
ZaGlobalConfigViewController.prototype.downloadConfigButtonListener = 
function(ev) {
	window.open("/service/collectldapconfig/");
}

ZaGlobalConfigViewController.prototype._saveChanges =
function () {
	var tmpObj = this._view.getObject();
	var isNew = false;
	if(tmpObj.attrs == null) {
		//show error msg
		this._errorDialog.setMessage(ZaMsg.ERROR_UNKNOWN, null, DwtMessageDialog.CRITICAL_STYLE, null);
		this._errorDialog.popup();		
		return false;	
	}

	//check values
	if(ZaItem.hasWritePermission(ZaGlobalConfig.A_zmailSmtpPort,tmpObj)) {
		if(!AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaGlobalConfig.A_zmailSmtpPort])) {
			//show error msg
			this._errorDialog.setMessage(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [ZaMsg.NAD_SmtpPort]), null, DwtMessageDialog.CRITICAL_STYLE, null);
			this._errorDialog.popup();		
			return false;
		}
	}	
	//check if domain is real
	if(ZaItem.hasWritePermission(ZaGlobalConfig.A_zmailDefaultDomainName,tmpObj)) {
		if(tmpObj.attrs[ZaGlobalConfig.A_zmailDefaultDomainName]) {
			if(tmpObj.attrs[ZaGlobalConfig.A_zmailDefaultDomainName] != this._currentObject.attrs[ZaGlobalConfig.A_zmailDefaultDomainName]) {
				var testD = new ZaDomain();
				try {
					testD.load("name",tmpObj.attrs[ZaGlobalConfig.A_zmailDefaultDomainName]);
				} catch (ex) {
					if (ex.code == ZmCsfeException.NO_SUCH_DOMAIN) {
						this._errorDialog.setMessage(AjxMessageFormat.format(ZaMsg.ERROR_WRONG_DOMAIN_IN_GS, [tmpObj.attrs[ZaGlobalConfig.A_zmailDefaultDomainName]]), null, DwtMessageDialog.CRITICAL_STYLE, null);
						this._errorDialog.popup();	
						return false;	
					} else {
						throw (ex);
					}
				}
			}
		}
	}	
	if(ZaItem.hasWritePermission(ZaGlobalConfig.A_zmailGalMaxResults,tmpObj)) {
		if(!AjxUtil.isNonNegativeLong(tmpObj.attrs[ZaGlobalConfig.A_zmailGalMaxResults])) {
			//show error msg
			this._errorDialog.setMessage(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR,[ZaMsg.MSG_zmailGalMaxResults]), null, DwtMessageDialog.CRITICAL_STYLE, null);
			this._errorDialog.popup();		
			return false;
		}	
	}	
	if(ZaItem.hasWritePermission(ZaGlobalConfig.A_zmailScheduledTaskNumThreads,tmpObj)) {
		if (tmpObj.attrs[ZaGlobalConfig.A_zmailScheduledTaskNumThreads] &&
		 	 !AjxUtil.isPositiveInt(tmpObj.attrs[ZaGlobalConfig.A_zmailScheduledTaskNumThreads])) {
				//show error msg
			this._errorDialog.setMessage(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR,[ZaMsg.NAD_zmailScheduledTaskNumThreads]), null, DwtMessageDialog.CRITICAL_STYLE, null);
			this._errorDialog.popup();		
			return false;
		}	
	}	
	// update zmailMtaRestriction (except RBLs)
	if(ZaItem.hasWritePermission(ZaGlobalConfig.A_zmailMtaRestriction,tmpObj)) {
		var restrictions = [];
		for (var i = 0; i < ZaGlobalConfig.MTA_RESTRICTIONS.length; i++) {
			var restriction = ZaGlobalConfig.MTA_RESTRICTIONS[i];
			if (tmpObj.attrs["_"+ZaGlobalConfig.A_zmailMtaRestriction+"_"+restriction]) {
				restrictions.push(restriction);
			}			
		}
		var dirty = restrictions.length > 0;
		if (tmpObj.attrs[ZaGlobalConfig.A_zmailMtaRestriction]) {
			var prevRestrictions = AjxUtil.isString(tmpObj.attrs[ZaGlobalConfig.A_zmailMtaRestriction])
			                     ? [ tmpObj.attrs[ZaGlobalConfig.A_zmailMtaRestriction] ]
			                     : tmpObj.attrs[ZaGlobalConfig.A_zmailMtaRestriction];
			dirty = restrictions.length != prevRestrictions.length;
			if (!dirty) {
				for (var i = 0; i < prevRestrictions.length; i++) {
					var restriction = prevRestrictions[i];
					if (!tmpObj.attrs["_"+ZaGlobalConfig.A_zmailMtaRestriction+"_"+restriction]) {
						dirty = true;
						break;
					}
				}
			}
		}
		
		//check policy service
		var numPolicyService = tmpObj.attrs[ZaGlobalConfig.A_zmailMtaPolicyService].length;
                if( (numPolicyService !=  this._currentObject.attrs[ZaGlobalConfig.A_zmailMtaPolicyService].length) || 
                       (tmpObj.attrs[ZaGlobalConfig.A_zmailMtaPolicyService].join("") != this._currentObject.attrs[ZaGlobalConfig.A_zmailMtaPolicyService].join(""))) {
                        dirty = true;
                }
                for(var ix=0;ix<numPolicyService;ix++) {
                        restrictions.push("check_policy_service "+tmpObj.attrs[ZaGlobalConfig.A_zmailMtaPolicyService][ix]);
                }

		//check RBLs
		var numRBLs = tmpObj.attrs[ZaGlobalConfig.A_zmailMtaRejectRblClient].length;
		if( (numRBLs !=  this._currentObject.attrs[ZaGlobalConfig.A_zmailMtaRejectRblClient].length) ||
			(tmpObj.attrs[ZaGlobalConfig.A_zmailMtaRejectRblClient].join("") != this._currentObject.attrs[ZaGlobalConfig.A_zmailMtaRejectRblClient].join(""))) {
			dirty = true;
		}
		for(var ix=0;ix<numRBLs;ix++) {
			restrictions.push("reject_rbl_client "+tmpObj.attrs[ZaGlobalConfig.A_zmailMtaRejectRblClient][ix]);
		}
	
		if (dirty) {
			tmpObj.attrs[ZaGlobalConfig.A_zmailMtaRestriction] = restrictions;
		}
	}

	// check validation expression, which should be email-like pattern
        if(tmpObj.attrs[ZaGlobalConfig.A_zmailMailAddressValidationRegex]) {
                var regList = tmpObj.attrs[ZaGlobalConfig.A_zmailMailAddressValidationRegex];
                var islegal = true;
                var regval = null;
                if(regList && regList instanceof Array) {
                        for(var i = 0; i < regList.length && islegal; i++) {
                                if (regList[i].indexOf("@") == -1) {
                                        islegal = false;
                                        regval = regList[i];
                                }
                        }
                } else if(regList) {
                        if (regList.indexOf("@") == -1) {
                                islegal = false;
                                regval = regList;
                        }
                }
                if(!islegal) {
                        this._errorDialog.setMessage(AjxMessageFormat.format(ZaMsg.ERROR_MSG_EmailValidReg, regval),
                                null, DwtMessageDialog.CRITICAL_STYLE, ZabMsg.zmailAdminTitle);
                        this._errorDialog.popup();
                        return islegal;
                }
        }

        //transfer the fields from the tmpObj to the _currentObject, since _currentObject is an instance of ZaDomain
        var mods = new Object();

        // execute other plugin methods
        if(ZaController.saveChangeCheckMethods["ZaGlobalConfigViewController"]) {
                var methods = ZaController.saveChangeCheckMethods["ZaGlobalConfigViewController"];
                var cnt = methods.length;
                for(var i = 0; i < cnt; i++) {
                        if(typeof(methods[i]) == "function")
                               methods[i].call(this, mods, tmpObj, this._currentObject);
                }
        }
	
	for (var a in tmpObj.attrs) {
		if(a == ZaItem.A_objectClass || a == ZaGlobalConfig.A_zmailAccountClientAttr ||
		a == ZaGlobalConfig.A_zmailServerInheritedAttr || a == ZaGlobalConfig.A_zmailDomainInheritedAttr ||
		a == ZaGlobalConfig.A_zmailCOSInheritedAttr || a == ZaGlobalConfig.A_zmailGalLdapAttrMap || 
		a == ZaGlobalConfig.A_zmailGalLdapFilterDef || /^_/.test(a) || a == ZaGlobalConfig.A_zmailMtaBlockedExtension || a == ZaGlobalConfig.A_zmailMtaCommonBlockedExtension
                || a == ZaItem.A_zmailACE)
			continue;
		if(!ZaItem.hasWritePermission(a,tmpObj)) {
			continue;
		}		
		
		if ((this._currentObject.attrs[a] != tmpObj.attrs[a]) && !(this._currentObject.attrs[a] == undefined && tmpObj.attrs[a] === "")) {
			if(tmpObj.attrs[a] instanceof Array) {
                if (!this._currentObject.attrs[a]) 
                	this._currentObject.attrs[a] = [] ;
		else if(!(this._currentObject.attrs[a] instanceof Array))
			this._currentObject.attrs[a] = [this._currentObject.attrs[a]];
                
                if( tmpObj.attrs[a].join(",").valueOf() !=  this._currentObject.attrs[a].join(",").valueOf()) {
					mods[a] = tmpObj.attrs[a];
				}
			} else {
				mods[a] = tmpObj.attrs[a];
			}				
		}
	}
	//check if blocked extensions are changed
	if(ZaItem.hasWritePermission(ZaGlobalConfig.A_zmailMtaBlockedExtension,tmpObj)) {
		if(!AjxUtil.isEmpty(tmpObj.attrs[ZaGlobalConfig.A_zmailMtaBlockedExtension])) {
			if(
				(
					(!this._currentObject.attrs[ZaGlobalConfig.A_zmailMtaBlockedExtension] || !this._currentObject.attrs[ZaGlobalConfig.A_zmailMtaBlockedExtension].length))
					|| (tmpObj.attrs[ZaGlobalConfig.A_zmailMtaBlockedExtension].join("") != this._currentObject.attrs[ZaGlobalConfig.A_zmailMtaBlockedExtension].join(""))
				) {
				mods[ZaGlobalConfig.A_zmailMtaBlockedExtension] = tmpObj.attrs[ZaGlobalConfig.A_zmailMtaBlockedExtension];
			} 
		} else if (AjxUtil.isEmpty(tmpObj.attrs[ZaGlobalConfig.A_zmailMtaBlockedExtension])  && !AjxUtil.isEmpty(this._currentObject.attrs[ZaGlobalConfig.A_zmailMtaBlockedExtension])) {
			mods[ZaGlobalConfig.A_zmailMtaBlockedExtension] = "";
		}		
	}
	//save the model
    if (this._currentObject[ZaModel.currentTab]!= tmpObj[ZaModel.currentTab])
             this._currentObject[ZaModel.currentTab] = tmpObj[ZaModel.currentTab];
	//var changeDetails = new Object();
	this._currentObject.modify(mods,tmpObj);

    // skin modification needs to restart server
    if(mods.hasOwnProperty(ZaGlobalConfig.A_zmailSkinForegroundColor)
            ||  mods.hasOwnProperty(ZaGlobalConfig.A_zmailSkinBackgroundColor)
            ||  mods.hasOwnProperty(ZaGlobalConfig.A_zmailSkinSecondaryColor)
            ||  mods.hasOwnProperty(ZaGlobalConfig.A_zmailSkinSelectionColor)
            ||  mods.hasOwnProperty(ZaGlobalConfig.A_zmailSkinLogoURL)
            ||  mods.hasOwnProperty(ZaGlobalConfig.A_zmailSkinLogoLoginBanner)
            ||  mods.hasOwnProperty(ZaGlobalConfig.A_zmailSkinLogoAppBanner)
            ) {
            	try {
            		var mbxSrvrs = ZaApp.getInstance().getMailServers();
            		var serverList = [];
            		var cnt = mbxSrvrs.length;
            		for(var i=0; i<cnt; i++) {
            			if(ZaItem.hasRight(ZaServer.FLUSH_CACHE_RIGHT,mbxSrvrs[i])) {
            				serverList.push(mbxSrvrs[i]);
            			}
            		}

            		if(serverList.length > 0) {
						ZaApp.getInstance().dialogs["confirmMessageDialog2"].setMessage(ZaMsg.Domain_flush_cache_q, DwtMessageDialog.INFO_STYLE);
						ZaApp.getInstance().dialogs["confirmMessageDialog2"].registerCallback(DwtDialog.YES_BUTTON, this.openFlushCacheDlg, this, [serverList]);
						ZaApp.getInstance().dialogs["confirmMessageDialog2"].registerCallback(DwtDialog.NO_BUTTON, this.closeCnfrmDelDlg, this, null);
						ZaApp.getInstance().dialogs["confirmMessageDialog2"].popup();
            		}

            	} catch (ex) {
                    this._handleException(ex, "ZaGlobalConfigViewController.prototype._saveChange", null, false);
                    return false;
            	}

    }
    ZaApp.getInstance().getAppCtxt().getAppController().setActionStatusMsg(ZaMsg.GlobalConfigModified);
	return true;
}

ZaGlobalConfigViewController.prototype.validateMyNetworks = ZaServerController.prototype.validateMyNetworks;
ZaXFormViewController.preSaveValidationMethods["ZaGlobalConfigViewController"].push(ZaGlobalConfigViewController.prototype.validateMyNetworks);

ZaGlobalConfigViewController.prototype.openFlushCacheDlg =
function (serverList) {
	ZaApp.getInstance().dialogs["confirmMessageDialog2"].popdown();
	
	serverList._version = 1;
	var uuid = [];
	for(var i=0;i<serverList.length;i++) {
		serverList[i]["status"] = 0;
		uuid.push(serverList[i].id)
	}
	obj = {statusMessage:null,flushZimlet:false,flushSkin:true,flushLocale:false,serverList:serverList,status:0, _uuid:(uuid.length > 1 ? uuid.join("__") : uuid[0]), name:(uuid.length > 1 ? ZaMsg.multiple_servers : serverList[0].name)};
	ZaApp.getInstance().dialogs["flushCacheDialog"] = new ZaFlushCacheXDialog(this._container, {id:(uuid.length > 1 ? uuid.join("__") : uuid[0]), name:(uuid.length > 1 ? ZaMsg.multiple_servers : serverList[0].name)});
	ZaApp.getInstance().dialogs["flushCacheDialog"].setObject(obj);
	ZaApp.getInstance().dialogs["flushCacheDialog"].popup();
}
