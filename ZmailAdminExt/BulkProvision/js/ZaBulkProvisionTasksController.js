/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
* @constructor
* @class ZaBulkProvisionTasksController
* @author Greg Solovyev
**/
ZaBulkProvisionTasksController = function(appCtxt, container) {
	ZaListViewController.call(this, appCtxt, container, "ZaBulkProvisionTasksController");
   	this._toolbarOperations = new Array();
   	this._popupOperations = new Array();			
	this.objType = ZaEvent.S_BULK_PROVISION_TASK;
 	this._helpURL = ZaBulkProvisionTasksController.helpURL;
}

ZaBulkProvisionTasksController.prototype = new ZaListViewController();
ZaBulkProvisionTasksController.prototype.constructor = ZaBulkProvisionTasksController;
ZaBulkProvisionTasksController.helpURL = location.pathname + ZaUtil.HELP_URL + "account_migration/migrating_accounts.htm?locid="+AjxEnv.DEFAULT_LOCALE;
ZaController.initToolbarMethods["ZaBulkProvisionTasksController"] = new Array();
ZaController.initPopupMenuMethods["ZaBulkProvisionTasksController"] = new Array();
ZaController.changeActionsStateMethods["ZaBulkProvisionTasksController"] = new Array(); 
ZaOperation.BULK_DATA_IMPORT = ++ ZA_OP_INDEX;

ZaBulkProvisionTasksController.prototype.show = 
function(list, openInNewTab) {
    if (!this._UICreated) {
		this._createUI();
	} 	
	if (list != null)
		this._contentView.set(list.getVector());
    
	ZaApp.getInstance().pushView(this.getContentViewId());
	this.changeActionsState();	
}

ZaBulkProvisionTasksController.initToolbarMethod =
function () {
	var showBulkProvision = false;
	if(ZaSettings.HAVE_MORE_DOMAINS || ZaZimbraAdmin.currentAdminAccount.attrs[ZaAccount.A_zimbraIsAdminAccount] == 'TRUE') {
		showBulkProvision = true;
	} else {
		var domainList = ZaApp.getInstance().getDomainList().getArray();
		var cnt = domainList.length;
		for(var i = 0; i < cnt; i++) {
			if(ZaItem.hasRight(ZaDomain.RIGHT_CREATE_ACCOUNT,domainList[i])) {
				showBulkProvision = true;
				break;
			}	
		}
	}	
	if(showBulkProvision) {    	
		this._toolbarOperations[ZaOperation.BULK_DATA_IMPORT]=new ZaOperation(ZaOperation.BULK_DATA_IMPORT,com_zimbra_bulkprovision.TB_IMAP_Import, com_zimbra_bulkprovision.TB_IMAP_Import_tt, "ApplianceMigration", "ApplianceMigration", new AjxListener(this, this.bulkDataImportListener));
		this._toolbarOperations[ZaOperation.DELETE]=new ZaOperation(ZaOperation.DELETE,com_zimbra_bulkprovision.DeleteTask, com_zimbra_bulkprovision.DeleteTask_tt, "Delete", "Delete", new AjxListener(this, this.deleteButtonListener));
		this._toolbarOperations[ZaOperation.REFRESH]=new ZaOperation(ZaOperation.REFRESH,ZaMsg.TBB_Refresh, ZaMsg.TBB_Refresh_tt, "Refresh", "Refresh", new AjxListener(this, this.refreshButtonListener));
		this._toolbarOrder.push(ZaOperation.BULK_DATA_IMPORT);
		this._toolbarOrder.push(ZaOperation.REFRESH);
		this._toolbarOrder.push(ZaOperation.DELETE);
	}
				
	this._toolbarOperations[ZaOperation.NONE] = new ZaOperation(ZaOperation.NONE);
	this._toolbarOperations[ZaOperation.HELP]=new ZaOperation(ZaOperation.HELP,ZaMsg.TBB_Help, ZaMsg.TBB_Help_tt, "Help", "Help", new AjxListener(this, this._helpButtonListener));

	this._toolbarOrder.push(ZaOperation.NONE);	
	this._toolbarOrder.push(ZaOperation.HELP);					
}
ZaController.initToolbarMethods["ZaBulkProvisionTasksController"].push(ZaBulkProvisionTasksController.initToolbarMethod);

ZaBulkProvisionTasksController.initPopupMenuMethod =
function () {
	var showBulkProvision = false;
	if(ZaSettings.HAVE_MORE_DOMAINS || ZaZimbraAdmin.currentAdminAccount.attrs[ZaAccount.A_zimbraIsAdminAccount] == 'TRUE') {
		showBulkProvision = true;
	} else {
		var domainList = ZaApp.getInstance().getDomainList().getArray();
		var cnt = domainList.length;
		for(var i = 0; i < cnt; i++) {
			if(ZaItem.hasRight(ZaDomain.RIGHT_CREATE_ACCOUNT,domainList[i])) {
				showBulkProvision = true;
				break;
			}
		}
	}
	if(showBulkProvision) {
		this._popupOperations[ZaOperation.BULK_DATA_IMPORT]=new ZaOperation(ZaOperation.BULK_DATA_IMPORT,com_zimbra_bulkprovision.TB_IMAP_Import, com_zimbra_bulkprovision.TB_IMAP_Import_tt, "ApplianceMigration", "ApplianceMigration", new AjxListener(this, this.bulkDataImportListener));
		this._popupOperations[ZaOperation.DELETE]=new ZaOperation(ZaOperation.DELETE,com_zimbra_bulkprovision.DeleteTask, com_zimbra_bulkprovision.DeleteTask_tt, "Delete", "Delete", new AjxListener(this, this.deleteButtonListener));
		this._popupOperations[ZaOperation.REFRESH]=new ZaOperation(ZaOperation.REFRESH,ZaMsg.TBB_Refresh, ZaMsg.TBB_Refresh_tt, "Refresh", "Refresh", new AjxListener(this, this.refreshButtonListener));
	}
}
ZaController.initPopupMenuMethods["ZaBulkProvisionTasksController"].push(ZaBulkProvisionTasksController.initPopupMenuMethod);


ZaBulkProvisionTasksController.prototype.refreshButtonListener = function(ev) {
	var list = ZaBulkProvision.getBulkDataImportTasks();
	if (list != null)
		this._contentView.set(list.getVector());
	this.changeActionsState();	
}

ZaBulkProvisionTasksController.prototype.deleteButtonListener = function(ev) {
	ZaApp.getInstance().dialogs["confirmMessageDialog"].setMessage(com_zimbra_bulkprovision.ConfirmDeleteTask, DwtMessageDialog.INFO_STYLE);
	ZaApp.getInstance().dialogs["confirmMessageDialog"].registerCallback(DwtDialog.YES_BUTTON, this.deleteAndGoAway, this, null);		
    ZaApp.getInstance().dialogs["confirmMessageDialog"].registerCallback(DwtDialog.NO_BUTTON, this.closeCnfrmDlg, this, null);				
	ZaApp.getInstance().dialogs["confirmMessageDialog"].popup();
}

ZaBulkProvisionTasksController.prototype.deleteAndGoAway = function () {
	try {
		ZaApp.getInstance().dialogs["confirmMessageDialog"].popdown();
		ZaBulkProvision.deleteBulkDataImportTasks();
		this.show(ZaBulkProvision.getBulkDataImportTasks());
	} catch (ex) {
		this._handleException(ex, "ZaBulkProvisionTasksController.prototype.deleteAndGoAway", null, false);
	}	
}

ZaBulkProvisionTasksController.prototype.openBulkProvisionDialog = function (params,ev) {
    try {
		var obj = null;
		if (params && params.obj) {
			obj = params.obj;
		} else {
			obj = new ZaBulkProvision();
		}
		if(params && params.hideWiz) {
			if(ZaApp.getInstance().dialogs[params.hideWiz]) {
				ZaApp.getInstance().dialogs[params.hideWiz].popdown();
			}
		}    	
		if(!obj[ZaBulkProvision.A2_provAction])
			obj[ZaBulkProvision.A2_provAction] = ZaBulkProvision.ACTION_IMPORT_LDAP;
		
		obj[ZaBulkProvision.A2_generatedFileLink] = null;
		obj[ZaBulkProvision.A2_maxResults] = "0";
		obj[ZaBulkProvision.A2_GalLdapFilter] = "(objectClass=organizationalPerson)";
		obj[ZaBulkProvision.A2_generatePassword] = "TRUE";
		obj[ZaBulkProvision.A2_genPasswordLength] = 8;
		obj[ZaBulkProvision.A2_ZimbraAdminLogin] = ZaZimbraAdmin.currentUserLogin;
		obj[ZaBulkProvision.A2_createDomains] = "TRUE";
		obj[ZaBulkProvision.A2_connectionType] = ZaBulkProvision.CONNECTION_SSL;
		ZaApp.getInstance().dialogs["importAccountsWizard"] = new ZaBulkImportXWizard(DwtShell.getShell(window),obj);
		if(params && params.prevCallback) {
			ZaApp.getInstance().dialogs["importAccountsWizard"].prevCallback = params.prevCallback;
            obj.prevCallback = params.prevCallback;
		} else {
			ZaApp.getInstance().dialogs["importAccountsWizard"].prevCallback = null;
            obj.prevCallback = null
		}

		if(params && params.finishCallback) {
			ZaApp.getInstance().dialogs["importAccountsWizard"].finishCallback = params.finishCallback;
            obj.finishCallback = params.finishCallback;
		} else {
			ZaApp.getInstance().dialogs["importAccountsWizard"].finishCallback = null;
            obj.finishCallback = null;
		}

		ZaApp.getInstance().dialogs["importAccountsWizard"].setObject(obj);
		ZaApp.getInstance().dialogs["importAccountsWizard"].popup();
	} catch (ex) {
		this._handleException(ex, "ZaBulkProvisionTasksController.prototype.openBulkProvisionDialog", null, false);
	}
};

ZaBulkProvisionTasksController.prototype.openMigrationWizard = function (params,ev) {
    try {
		var obj = null;
		if (params && params.obj) {
			obj = params.obj;
		} else {
			obj = new ZaBulkProvision();
		}
		if(params && params.hideWiz) {
			if(ZaApp.getInstance().dialogs[params.hideWiz]) {
				ZaApp.getInstance().dialogs[params.hideWiz].popdown();
			}
		}
    	
		obj[ZaBulkProvision.A2_provAction] = ZaBulkProvision.ACTION_GENERATE_MIG_XML;
		obj[ZaBulkProvision.A2_generatedFileLink] = null;
		obj[ZaBulkProvision.A2_maxResults] = "0";
		obj[ZaBulkProvision.A2_GalLdapFilter] = "(objectClass=organizationalPerson)";
		obj[ZaBulkProvision.A2_generatePassword] = "TRUE";
		obj[ZaBulkProvision.A2_provisionUsers] = "TRUE";
		obj[ZaBulkProvision.A2_importMails] = "TRUE";
		obj[ZaBulkProvision.A2_importContacts] = "TRUE";
		obj[ZaBulkProvision.A2_importTasks] = "TRUE";
		obj[ZaBulkProvision.A2_importCalendar] = "TRUE";
		obj[ZaBulkProvision.A2_InvalidSSLOk] = "TRUE";
		obj[ZaBulkProvision.A2_genPasswordLength] = 8;
		obj[ZaBulkProvision.A2_ZimbraAdminLogin] = ZaZimbraAdmin.currentUserLogin;
		obj[ZaBulkProvision.A2_createDomains] = "TRUE";
		obj[ZaBulkProvision.A2_connectionType] = ZaBulkProvision.CONNECTION_SSL;
		
		ZaApp.getInstance().dialogs["migrationWizard"] = new ZaMigrationXWizard(DwtShell.getShell(window),obj);
		
		if(params && params.prevCallback) {
			ZaApp.getInstance().dialogs["migrationWizard"].prevCallback = params.prevCallback;
            obj.prevCallback = params.prevCallback;
		} else {
			ZaApp.getInstance().dialogs["migrationWizard"].prevCallback = null;
            obj.prevCallback = null;
		}
		ZaApp.getInstance().dialogs["migrationWizard"].setObject(obj);
		ZaApp.getInstance().dialogs["migrationWizard"].popup();
	} catch (ex) {
		this._handleException(ex, "ZaBulkProvisionTasksController.prototype.openMigrationWizard", null, false);
	}
};

ZaBulkProvisionTasksController.prototype.bulkDataImportListener = 
function (hideWiz,auxObj,passedObj,ev) {
    try {
		if(!ZaApp.getInstance().dialogs["bulkDataImportWizard"]) {
			ZaApp.getInstance().dialogs["bulkDataImportWizard"] = new ZaBulkDataImportXWizard(this._container);
		}
		var obj = null;
		if (passedObj && typeof passedObj == "object") {
			obj = passedObj;
		} else {
			obj = new ZaBulkProvision();
			obj[ZaModel.currentStep] = ZaBulkDataImportXWizard.STEP_INTRODUCTION;
			obj[ZaBulkProvision.A2_provisionUsers] = "TRUE";
			obj[ZaBulkProvision.A2_importEmail] = "TRUE";
			obj[ZaBulkProvision.A2_sourceType] = ZaBulkProvision.SOURCE_TYPE_XML;
			obj[ZaBulkProvision.A2_sourceServerType] = ZaBulkProvision.MAIL_SOURCE_TYPE_IMAP;
			obj[ZaBulkProvision.A2_connectionType] = ZaBulkProvision.CONNECTION_SSL;
		}
		if(auxObj) {
			for(var attr in auxObj) {
				obj[attr] = auxObj[attr];
			}
		}
		
		if(hideWiz && typeof hideWiz == "string") {
			if(ZaApp.getInstance().dialogs[hideWiz]) {
				ZaApp.getInstance().dialogs[hideWiz].popdown();
			}
		}
		ZaApp.getInstance().dialogs["bulkDataImportWizard"].setObject(obj);
		ZaApp.getInstance().dialogs["bulkDataImportWizard"].popup();
	} catch (ex) {
		this._handleException(ex, "ZaBulkProvisionTasksController.prototype.bulkDataImportListener", null, false);
	}

}

ZaBulkProvisionTasksController.prototype._createUI = function () {
	try {
		var elements = new Object();
		this._contentView = new ZaBulkProvisionTasksView(this._container);
		this._initToolbar();
		this._toolbar = new ZaToolBar(this._container, this._toolbarOperations,this._toolbarOrder);

		this._initPopupMenu();
		this._actionMenu =  new ZaPopupMenu(this._contentView, "ActionMenu", null, this._popupOperations);

		elements[ZaAppViewMgr.C_APP_CONTENT] = this._contentView;
        if (!appNewUI) {
		    elements[ZaAppViewMgr.C_TOOLBAR_TOP] = this._toolbar;
            var tabParams = {
                openInNewTab: false,
                tabId: this.getContentViewId(),
                tab: this.getMainTab()
            }
            ZaApp.getInstance().createView(this.getContentViewId(), elements, tabParams) ;
        } else {
            ZaApp.getInstance().getAppViewMgr().createView(this.getContentViewId(), elements);
        }
		this._UICreated = true;
		ZaApp.getInstance()._controllers[this.getContentViewId ()] = this ;
		this._contentView.addSelectionListener(new AjxListener(this, this._listSelectionListener));
		this._contentView.addActionListener(new AjxListener(this, this._listActionListener));			
		
	} catch (ex) {
		this._handleException(ex, "ZaBulkProvisionTasksController.prototype._createUI", null, false);
		return;
	}	
}

ZaBulkProvisionTasksController.prototype.set = 
function(taskList) {
	this.show(taskList);
}

/**
* This listener is called when the item in the list is double clicked. It call ZaCosController.show method
* in order to display the Cos View
**/
ZaBulkProvisionTasksController.prototype._listSelectionListener =
function(ev) {
	this.changeActionsState();	
}


ZaBulkProvisionTasksController.prototype._listActionListener =
function (ev) {
	this.changeActionsState();
	this._actionMenu.popup(0, ev.docX, ev.docY);
}

ZaBulkProvisionTasksController.changeActionsStateMethod = 
	function (enableArray,disableArray) {
		if(!this._contentView)
			return;
		
		var cnt = this._contentView.getSelectionCount();
		var hasDefault = false;
		if(cnt >= 1) {
			var arrDivs = this._contentView.getSelectedItems().getArray();
			for(var key in arrDivs) {
				var item = this._contentView.getItemFromElement(arrDivs[key]);
				if(item) {
					if(item.name == "default") {
						hasDefault = true;
						break;
					}		
				}
			}
		}
		if(cnt == 1) {
			var item = this._contentView.getSelection()[0];
			if(!item) {
				if(this._toolbarOperations[ZaOperation.DELETE]) {
					this._toolbarOperations[ZaOperation.DELETE].enabled=false;
				}	
				if(this._popupOperations[ZaOperation.DELETE]) {
					this._popupOperations[ZaOperation.DELETE].enabled=false;
				}	
			}
		} else if (cnt < 1){
			if(this._toolbarOperations[ZaOperation.DELETE]) {
				this._toolbarOperations[ZaOperation.DELETE].enabled=false;
			}	
			if(this._popupOperations[ZaOperation.DELETE]) {
				this._popupOperations[ZaOperation.DELETE].enabled=false;
			}	
		}
	}
	ZaController.changeActionsStateMethods["ZaBulkProvisionTasksController"].push(ZaBulkProvisionTasksController.changeActionsStateMethod);
