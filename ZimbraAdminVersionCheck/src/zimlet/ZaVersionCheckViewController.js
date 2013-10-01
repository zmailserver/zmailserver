/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2011, 2012 VMware, Inc.
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
* @class ZaVersionCheckViewController 
* @contructor ZaVersionCheckViewController
* @param appCtxt
* @param container
* @author Greg Solovyev
**/
ZaVersionCheckViewController = function(appCtxt, container) {
	ZaXFormViewController.call(this, appCtxt, container, "ZaVersionCheckViewController");
	this._UICreated = false;
	this.objType = ZaEvent.S_GLOBALCONFIG;
	this.tabConstructor = ZaVersionCheckXFormView;
	this._helpURL = [location.pathname, ZaUtil.HELP_URL, ZaVersionCheckViewController.helpURL, "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
	this._helpButtonText = ZaMsg.zimbraHelpCenter;
}

ZaVersionCheckViewController.prototype = new ZaXFormViewController();
ZaVersionCheckViewController.prototype.constructor = ZaVersionCheckViewController;
ZaVersionCheckViewController.helpURL = "monitoring/getting_latest_software_updates.htm";
ZaController.initPopupMenuMethods["ZaVersionCheckViewController"] = new Array();
ZaController.setViewMethods["ZaVersionCheckViewController"] = [];
ZaController.changeActionsStateMethods["ZaVersionCheckViewController"] = new Array();


ZaVersionCheckViewController.initPopupMenuMethod =
function () {
	this._popupOperations[ZaOperation.SAVE] = new ZaOperation(ZaOperation.SAVE, ZaMsg.TBB_Save, ZaMsg.ALTBB_Save_tt, "Save", "SaveDis", new AjxListener(this, this.saveButtonListener));
	this._popupOperations[ZaOperation.VERSION_CHECK] = new ZaOperation(ZaOperation.VERSION_CHECK, com_zimbra_adminversioncheck.CheckNow, com_zimbra_adminversioncheck.CheckNow_tt, "Refresh", "Refresh", new AjxListener(this, this.checkNowListener));
}
ZaController.initPopupMenuMethods["ZaVersionCheckViewController"].push(ZaVersionCheckViewController.initPopupMenuMethod);

ZaVersionCheckViewController.prototype.checkNowListener =
function(ev) {
	if(this._view.isDirty()) {
		this.popupMsgDialog(com_zimbra_adminversioncheck.saveChangesFirst, true);		
	} else if (AjxUtil.isEmpty(this._currentObject.attrs[ZaVersionCheck.A_zimbraVersionCheckServer]) || 
				AjxUtil.isEmpty(this._currentObject.attrs[ZaVersionCheck.A_zimbraVersionCheckURL]) ||
				(this._currentObject.attrs[ZaVersionCheck.A_zimbraVersionCheckNotificationEmail] == "TRUE" &&
					(
			 		 AjxUtil.isEmpty(this._currentObject.attrs[ZaVersionCheck.A_zimbraVersionCheckNotificationSubject]) ||
			 		 AjxUtil.isEmpty(this._currentObject.attrs[ZaVersionCheck.A_zimbraVersionCheckNotificationBody]) ||
			 		 AjxUtil.isEmpty(this._currentObject.attrs[ZaVersionCheck.A_zimbraVersionCheckNotificationEmail])
			 		)
			    )
			) {
		this.popupMsgDialog(com_zimbra_adminversioncheck.ERROR_UPDATES_NOT_CONFIGURED, true);
	} else {
		if(this._view._localXForm && this._view._localXForm.getInstance()) {
			ZaVersionCheck.checkNow();
		}
		this._currentObject.refresh(false,true);
		this._view.setObject(this._currentObject);
	}	
}


ZaVersionCheckViewController.setViewMethod = function (item) {
    if(!this._UICreated) {
//    	this._initToolbar();
    	
        
			//always add Help button at the end of the toolbar		
	    this._initPopupMenu();
		this._contentView = this._view = new this.tabConstructor(this._container,item);
		var elements = new Object();
		elements[ZaAppViewMgr.C_APP_CONTENT] = this._view;
        ZaApp.getInstance().getAppViewMgr().createView(this.getContentViewId(), elements);
		this._UICreated = true;
		ZaApp.getInstance()._controllers[this.getContentViewId ()] = this ;
	}
	ZaApp.getInstance().pushView(this.getContentViewId());
	item.load();
	try {
		item[ZaModel.currentTab] = "1"
		this._view.setDirty(false);
		this._view.setObject(item);
	} catch (ex) {
		this._handleException(ex, "ZaVersionCheckViewController.prototype.show", null, false);
	}
	this._currentObject = item;
}
ZaController.setViewMethods["ZaVersionCheckViewController"].push(ZaVersionCheckViewController.setViewMethod) ;

ZaVersionCheckViewController.prototype._saveChanges =
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
	if(!AjxUtil.isEmpty(tmpObj.attrs[ZaVersionCheck.A_zimbraVersionCheckSendNotifications]) && tmpObj.attrs[ZaVersionCheck.A_zimbraVersionCheckSendNotifications]=="TRUE") {
		if(AjxUtil.isEmpty(tmpObj.attrs[ZaVersionCheck.A_zimbraVersionCheckNotificationEmail])) {
			//show error msg
			this._errorDialog.setMessage(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [com_zimbra_adminversioncheck.MSG_zimbraVersionCheckNotificationEmail]), null, DwtMessageDialog.CRITICAL_STYLE, null);
			this._errorDialog.popup();		
			return false;
		}
				
		if(AjxUtil.isEmpty(tmpObj.attrs[ZaVersionCheck.A_zimbraVersionCheckNotificationBody])) {
			//show error msg
			this._errorDialog.setMessage(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [com_zimbra_adminversioncheck.MSG_zimbraVersionCheckNotificationBody]), null, DwtMessageDialog.CRITICAL_STYLE, null);
			this._errorDialog.popup();		
			return false;
		}
		
		if(AjxUtil.isEmpty(tmpObj.attrs[ZaVersionCheck.A_zimbraVersionCheckNotificationSubject])) {
			//show error msg
			this._errorDialog.setMessage(AjxMessageFormat.format(ZaMsg.ERROR_INVALID_VALUE_FOR, [com_zimbra_adminversioncheck.MSG_zimbraVersionCheckNotificationSubject]), null, DwtMessageDialog.CRITICAL_STYLE, null);
			this._errorDialog.popup();		
			return false;
		}
	}

	
	//transfer the fields from the tmpObj to the _currentObject, since _currentObject is an instance of ZaDomain
	var mods = new Object();
	for (var a in tmpObj.attrs) {
		if(a == ZaItem.A_objectClass || a == ZaGlobalConfig.A_zimbraAccountClientAttr ||
		a == ZaGlobalConfig.A_zimbraServerInheritedAttr || a == ZaGlobalConfig.A_zimbraDomainInheritedAttr ||
		a == ZaGlobalConfig.A_zimbraCOSInheritedAttr || a == ZaGlobalConfig.A_zimbraGalLdapAttrMap || 
		a == ZaGlobalConfig.A_zimbraGalLdapFilterDef || /^_/.test(a) || a == ZaGlobalConfig.A_zimbraMtaBlockedExtension || a == ZaGlobalConfig.A_zimbraMtaCommonBlockedExtension
                || a == ZaItem.A_zimbraACE)
			continue;


		if ((this._currentObject.attrs[a] != tmpObj.attrs[a]) && !(this._currentObject.attrs[a] == undefined && tmpObj.attrs[a] === "")) {
			if(tmpObj.attrs[a] instanceof Array) {
                if (!this._currentObject.attrs[a]) 
                	this._currentObject.attrs[a] = [] ;
                	
                if( tmpObj.attrs[a].join(",").valueOf() !=  this._currentObject.attrs[a].join(",").valueOf()) {
					mods[a] = tmpObj.attrs[a];
				}
			} else {
				mods[a] = tmpObj.attrs[a];
			}				
		}
	}
	//save the model
	this._currentObject.modify(mods);
	return true;
}

ZaVersionCheckViewController.changeActionsStateMethod = function () {
	if(!this._currentObject)
		return;
		
	if(this._popupOperations[ZaOperation.SAVE])	
		this._popupOperations[ZaOperation.SAVE].enabled = false;
}
ZaController.changeActionsStateMethods["ZaVersionCheckViewController"].push(ZaVersionCheckViewController.changeActionsStateMethod);

