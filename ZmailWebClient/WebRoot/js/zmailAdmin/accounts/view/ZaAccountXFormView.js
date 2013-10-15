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
* This class describes a view of a single email Account
* @class ZaAccountXFormView
* @contructor
* @param parent {DwtComposite}
* @param app {ZaApp}
* @author Greg Solovyev
**/
ZaAccountXFormView = function(parent, entry) {
	ZaTabView.call(this, {
		parent:parent,  
		iKeyName:"ZaAccountXFormView",
		contextId:ZaId.TAB_ACCT_EDIT
	});	
	this.accountStatusChoices = [
		{value:ZaAccount.ACCOUNT_STATUS_ACTIVE, label:ZaAccount.getAccountStatusMsg (ZaAccount.ACCOUNT_STATUS_ACTIVE)},
		{value:ZaAccount.ACCOUNT_STATUS_CLOSED, label:ZaAccount.getAccountStatusMsg (ZaAccount.ACCOUNT_STATUS_CLOSED)},
		{value:ZaAccount.ACCOUNT_STATUS_LOCKED, label: ZaAccount.getAccountStatusMsg (ZaAccount.ACCOUNT_STATUS_LOCKED)},
        {value:ZaAccount.ACCOUNT_STATUS_LOCKOUT, label: ZaAccount.getAccountStatusMsg (ZaAccount.ACCOUNT_STATUS_LOCKOUT), visible: false},    
        {value:ZaAccount.ACCOUNT_STATUS_PENDING, label: ZaAccount.getAccountStatusMsg (ZaAccount.ACCOUNT_STATUS_PENDING)},
        {value:ZaAccount.ACCOUNT_STATUS_MAINTENANCE, label:ZaAccount.getAccountStatusMsg(ZaAccount.ACCOUNT_STATUS_MAINTENANCE)}
	];
	this.cosChoices = new XFormChoices([], XFormChoices.OBJECT_LIST, "id", "name");
	this.TAB_INDEX = 0;
	this._domains = {} ;
	//console.time("ZaAccountXFormView.initForm");
	//DBG.timePt(AjxDebug.PERF, "started initForm");
	this.initForm(ZaAccount.myXModel,this.getMyXForm(entry), null);
    this._localXForm._setAllowSelection();//bug13705,allow account copyable
	//console.timeEnd("ZaAccountXFormView.initForm");
	//DBG.timePt(AjxDebug.PERF, "finished initForm");
}

ZaAccountXFormView.prototype = new ZaTabView();
ZaAccountXFormView.prototype.constructor = ZaAccountXFormView;
ZaTabView.XFormModifiers["ZaAccountXFormView"] = new Array();
ZaTabView.ObjectModifiers["ZaAccountXFormView"] = [] ;
ZaAccountXFormView.zimletChoices = new XFormChoices([], XFormChoices.SIMPLE_LIST);
ZaAccountXFormView.themeChoices = new XFormChoices([], XFormChoices.OBJECT_LIST);

/**
 * * Get Tab's Icon according to different account's type
 * **/
ZaAccountXFormView.prototype.getTabIcon = 
function () {
	if (this._containedObject && this._containedObject.attrs) {
		var resultType;
                var account = this._containedObject;
		if(account.attrs[ZaAccount.A_zmailIsAdminAccount]=="TRUE" ) {
                       resultType = "AdminUser";
                } else if (account.attrs[ZaAccount.A_zmailIsDelegatedAdminAccount] == "TRUE") {
                       resultType = "DomainAdminUser";
                } else if (account.attrs[ZaAccount.A_zmailIsSystemResource] == "TRUE") {
                       resultType = "SystemResource";
                } else {
                       resultType = "Account";
                }
		return resultType;	
	}else{
		return "Account" ;
	}
}

/**
* Sets the object contained in the view
* @param entry - {ZaAccount} object to display
**/
ZaAccountXFormView.prototype.setObject =
function(entry) {
	//handle the special attributes to be displayed in xform
	//TODO  manageSpecialAttrs can be part of ZaItem.ObjectModifiers ;
    entry.manageSpecialAttrs();
	entry.modifyObject();

    this._containedObject = new Object();
	this._containedObject.attrs = new Object();
    
    for (var a in entry.attrs) {
		var modelItem = this._localXForm.getModel().getItem(a) ;
        if ((modelItem != null && modelItem.type == _LIST_) || (entry.attrs[a] != null && entry.attrs[a] instanceof Array)) {  
        	//need deep clone
            this._containedObject.attrs [a] =
                    ZaItem.deepCloneListItem (entry.attrs[a]);
        } else {
            this._containedObject.attrs[a] = entry.attrs[a];
        }
	}
	this._containedObject.name = entry.name;
	this._containedObject.type = entry.type;

	if(entry.rights)
		this._containedObject.rights = entry.rights;

	if(entry.setAttrs)
		this._containedObject.setAttrs = entry.setAttrs;
    else this._containedObject.setAttrs = {};
	
	if(entry.getAttrs)
		this._containedObject.getAttrs = entry.getAttrs;
		
	if(entry._defaultValues)
		this._containedObject._defaultValues = entry._defaultValues;
	
	if(entry.id)
		this._containedObject.id = entry.id;
	
	for (var a in entry.attrs) {
        var modelItem = this._localXForm.getModel().getItem(a) ;
        if ((modelItem != null && modelItem.type == _LIST_)
           || (entry.attrs[a] != null && entry.attrs[a] instanceof Array)) 
        {  //need deep clone
            this._containedObject.attrs [a] =
                    ZaItem.deepCloneListItem (entry.attrs[a]);
        } else {
            this._containedObject.attrs[a] = entry.attrs[a];
        }
     }
	
	//add the member group, need a deep clone
//	this._containedObject[ZaAccount.A2_memberOf] = entry [ZaAccount.A2_memberOf];
//    this._containedObject[ZaAccount.A2_memberOf] = {};
    this._containedObject[ZaAccount.A2_memberOf] =
                ZaAccountMemberOfListView.cloneMemberOf(entry);
    
    //add the memberList page information
	this._containedObject[ZaAccount.A2_directMemberList + "_offset"] = entry[ZaAccount.A2_directMemberList + "_offset"];
	this._containedObject[ZaAccount.A2_directMemberList + "_more"] = entry[ZaAccount.A2_directMemberList + "_more"];
	this._containedObject[ZaAccount.A2_indirectMemberList + "_offset"] = entry[ZaAccount.A2_indirectMemberList + "_offset"];
	this._containedObject[ZaAccount.A2_indirectMemberList + "_more"] = entry[ZaAccount.A2_indirectMemberList + "_more"];
	this._containedObject[ZaAccount.A2_nonMemberList + "_offset"] = entry[ZaAccount.A2_nonMemberList + "_offset"];
	this._containedObject[ZaAccount.A2_nonMemberList + "_more"] = entry[ZaAccount.A2_nonMemberList + "_more"];
	
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.ALIASES_TAB_ATTRS, ZaAccountXFormView.ALIASES_TAB_RIGHTS)) {
		if(this._containedObject.attrs[ZaAccount.A_zmailMailAlias]) {
			if(!this._containedObject.attrs[ZaAccount.A_zmailMailAlias] instanceof Array) {
				this._containedObject.attrs[ZaAccount.A_zmailMailAlias] = [this._containedObject.attrs[ZaAccount.A_zmailMailAlias]];		
			}
		}		
	}	
	
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.FORWARDING_TAB_ATTRS, ZaAccountXFormView.FORWARDING_TAB_RIGHTS)) {
		if(this._containedObject.attrs[ZaAccount.A_zmailMailForwardingAddress]) {
			if(!this._containedObject.attrs[ZaAccount.A_zmailMailForwardingAddress] instanceof Array) {
				this._containedObject.attrs[ZaAccount.A_zmailMailForwardingAddress] = [this._containedObject.attrs[ZaAccount.A_zmailMailForwardingAddress]];		
			}
		}		
	}
					
	if(this._containedObject.attrs[ZaAccount.A_COSId]) {	
		this._containedObject[ZaAccount.A2_autoCos] = "FALSE" ;
	}
	if(!this._containedObject.attrs[ZaAccount.A_COSId]) {
		this._containedObject[ZaAccount.A2_autoCos] = "TRUE" ;
	}
	if(this._containedObject.setAttrs[ZaAccount.A_COSId]) {
		var cos = ZaCos.getCosById(this._containedObject.attrs[ZaAccount.A_COSId]);	
		this.cosChoices.setChoices([cos]);
		this.cosChoices.dirtyChoices();
	}

	this._containedObject[ZaAccount.A2_autodisplayname] = entry[ZaAccount.A2_autodisplayname];
	this._containedObject[ZaAccount.A2_confirmPassword] = entry[ZaAccount.A2_confirmPassword];
	
	if(!entry[ZaModel.currentTab])
		this._containedObject[ZaModel.currentTab] = "1";
	else
		this._containedObject[ZaModel.currentTab] = entry[ZaModel.currentTab];
	
    //check the account type here 
    var domainName = ZaAccount.getDomain (this._containedObject.name) ;
    var domainObj =  ZaDomain.getDomainByName (domainName) ;
    this._containedObject[ZaAccount.A2_accountTypes] = domainObj.getAccountTypes () ;
    this._containedObject[ZaAccount.A2_currentAccountType] = entry[ZaAccount.A2_currentAccountType]  ;
//    ZaAccountXFormView.themeChoices = new XFormChoices([], XFormChoices.SIMPLE_LIST);
	if(!entry.getAttrs || entry.getAttrs[ZaAccount.A_zmailAvailableSkin] || entry.getAttrs.all) {
		var skins = ZaApp.getInstance().getInstalledSkins();
		
		if(AjxUtil.isEmpty(skins)) {
			
			if(domainObj && domainObj.attrs && !AjxUtil.isEmpty(domainObj.attrs[ZaDomain.A_zmailAvailableSkin])) {
				//if we cannot get all zimlets try getting them from domain
				skins = domainObj.attrs[ZaDomain.A_zmailAvailableSkin];
			} else if(entry._defaultValues && entry._defaultValues.attrs && !AjxUtil.isEmpty(entry._defaultValues.attrs[ZaAccount.A_zmailAvailableSkin])) {
				//if we cannot get all zimlets from domain either, just use whatever came in "defaults" which would be what the COS value is
				skins = entry._defaultValues.attrs[ZaAccount.A_zmailAvailableSkin];
			} else {
				skins = [];
			}
		} else {
			if (AjxUtil.isString(skins))	 {
				skins = [skins];
			}
		}

        var skinsChoices = ZaApp.getInstance().getSkinChoices(skins);
		ZaAccountXFormView.themeChoices.setChoices(skinsChoices);
		ZaAccountXFormView.themeChoices.dirtyChoices();		
		
	}
	
	if(!entry.getAttrs || entry.getAttrs[ZaAccount.A_zmailZimletAvailableZimlets] || entry.getAttrs.all) {
		//get sll Zimlets
		var allZimlets = ZaZimlet.getAll("extension");

		if(!AjxUtil.isEmpty(allZimlets) && allZimlets instanceof ZaItemList || allZimlets instanceof AjxVector)
			allZimlets = allZimlets.getArray();

		if(AjxUtil.isEmpty(allZimlets)) {
			
			if(domainObj && domainObj.attrs && !AjxUtil.isEmpty(domainObj.attrs[ZaDomain.A_zmailZimletDomainAvailableZimlets])) {
				//if we cannot get all zimlets try getting them from domain
				allZimlets = domainObj.attrs[ZaDomain.A_zmailZimletDomainAvailableZimlets];
			} else if(entry._defaultValues && entry._defaultValues.attrs && !AjxUtil.isEmpty(entry._defaultValues.attrs[ZaAccount.A_zmailZimletAvailableZimlets])) {
				//if we cannot get all zimlets from domain either, just use whatever came in "defaults" which would be what the COS value is
				allZimlets = entry._defaultValues.attrs[ZaAccount.A_zmailZimletAvailableZimlets];
			} else {
				allZimlets = [];
			}
			ZaAccountXFormView.zimletChoices.setChoices(allZimlets);
			ZaAccountXFormView.zimletChoices.dirtyChoices();
			
		} else {
			//convert objects to strings	
			var cnt = allZimlets.length;
			var _tmpZimlets = [];
			for(var i=0; i<cnt; i++) {
				var zimlet = allZimlets[i];
				_tmpZimlets.push(zimlet.name);
			}
			ZaAccountXFormView.zimletChoices.setChoices(_tmpZimlets);
			ZaAccountXFormView.zimletChoices.dirtyChoices();
		}
	}

    if (domainObj && domainObj.attrs &&
        domainObj.attrs[ZaDomain.A_AuthMech] &&
        (domainObj.attrs[ZaDomain.A_AuthMech] != ZaDomain.AuthMech_zmail) ) {
        this._containedObject[ZaAccount.A2_isExternalAuth] = true;
    } else {
        this._containedObject[ZaAccount.A2_isExternalAuth] = false;
    }

	if(ZaItem.modelExtensions["ZaAccount"]) {
		for(var i = 0; i< ZaItem.modelExtensions["ZaAccount"].length;i++) {
			var ext = ZaItem.modelExtensions["ZaAccount"][i];
			if(entry[ext]) {
                if (entry[ext] instanceof Array) {
                    this._containedObject[ext] = ZaItem.deepCloneListItem (entry[ext]);
                    if (entry[ext]._version) {
                        this._containedObject[ext]._version = entry[ext]._version;
                    }

                } else {
                    this._containedObject[ext] = {};
                    for (var a in entry[ext]) {
                        var modelItem = this._localXForm.getModel().getItem(a) ;
                        if ((modelItem != null && modelItem.type == _LIST_)
                           || (entry[ext][a] != null && entry[ext][a] instanceof Array))
                        {  //need deep clone
                            this._containedObject[ext][a] =
                                    ZaItem.deepCloneListItem (entry[ext][a]);
                        } else {
                            this._containedObject[ext][a] = entry[ext][a];
                        }
                    }
                }
			}
			
		}
	}

	this.modifyContainedObject () ;

    this._localXForm.setInstance(this._containedObject);
    
	this.formDirtyLsnr = new AjxListener(ZaApp.getInstance().getCurrentController(), ZaXFormViewController.prototype.handleXFormChange);
	this._localXForm.addListener(DwtEvent.XFORMS_FORM_DIRTY_CHANGE, this.formDirtyLsnr);
	this._localXForm.addListener(DwtEvent.XFORMS_VALUE_ERROR, this.formDirtyLsnr);	
	
}

ZaAccountXFormView.gotNoSkins = function() {
	return !ZaAccountXFormView.gotSkins.call(this);
}

ZaAccountXFormView.gotSkins = function () {
	return (
			( (ZaApp.getInstance() != null) 
			  && (ZaApp.getInstance().getInstalledSkins() != null) 
			  && (ZaApp.getInstance().getInstalledSkins().length > 0)
			 ) 
             || !AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A_zmailAvailableSkin))
             || !AjxUtil.isEmpty(this.getInstance()._defaultValues.attrs[ZaAccount.A_zmailAvailableSkin])
           );
}

ZaAccountXFormView.preProcessCOS = 
function(value,  form) {
	var val = value;
	if(ZaItem.ID_PATTERN.test(value))  {
		val = value;
	} else {
		var cos = ZaCos.getCosByName(value);
		if(cos)
			val = cos.id;
	}
    return val;
}

//update the account type output and it is called when the domain name is changed.
ZaAccountXFormView.accountTypeItemId = "account_type_output_" + Dwt.getNextId();
ZaAccountXFormView.prototype.updateAccountType =
function ()  {
    var item = this._localXForm.getItemsById (ZaAccountXFormView.accountTypeItemId) [0] ;
    item.updateElement(ZaAccount.getAccountTypeOutput.call(item, true)) ;
}

ZaAccountXFormView.cosGroupItemId = "cos_grouper_" + Dwt.getNextId();
ZaAccountXFormView.prototype.updateCosGrouper =
function () {
    var item = this._localXForm.getItemsById (ZaAccountXFormView.cosGroupItemId) [0] ;
    item.items[0].setElementEnabled(true);
    item.updateElement() ;
}
/*
ZaAccountXFormView.onRepeatRemove = 
function (index, form) {
	var path = this.getRefPath();
	this.getModel().removeRow(this.getInstance(), path, index);
	this.items[index].clearError();
	this.getForm().setIsDirty(true,this);	
	//form.parent.setDirty(true);
}*/


ZaAccountXFormView.isSendingFromAnyAddressDisAllowed = function () {
	return (this.getInstanceValue(ZaAccount.A_zmailAllowAnyFromAddress) != 'TRUE');
}

ZaAccountXFormView.aliasSelectionListener = 
function (ev) {
	var arr = this.widget.getSelection();	
	if(arr && arr.length) {
		arr.sort();
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_alias_selection_cache, arr);
	} else {
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_alias_selection_cache, null);
	}		
	if (ev.detail == DwtListView.ITEM_DBL_CLICKED) {
		ZaAccountXFormView.editAliasButtonListener.call(this);
	}	
}

ZaAccountXFormView.nonMemberOfSelectionListener =    
function (ev) {
	var arr = this.widget.getSelection();
	if(arr && arr.length) {
		arr.sort();
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_nonMemberListSelected, arr);
	} else {
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_nonMemberListSelected, null);
	}

    if (ev.detail == DwtListView.ITEM_DBL_CLICKED) {
		ZaAccountMemberOfListView._addSelectedLists(this.getForm(), arr);
	}
}

ZaAccountXFormView.directMemberOfSelectionListener =
function (ev) {
	var arr = this.widget.getSelection();
	if(arr && arr.length) {
		arr.sort();
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_directMemberListSelected, arr);
	} else {
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_directMemberListSelected, null);
	}

    if (ev.detail == DwtListView.ITEM_DBL_CLICKED) {
		ZaAccountMemberOfListView._removeSelectedLists(this.getForm(), arr);
	}
}


ZaAccountXFormView.indirectMemberOfSelectionListener =
function (ev) {
	var arr = this.widget.getSelection();
	if(arr && arr.length) {
		arr.sort();
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_indirectMemberListSelected, arr);
	} else {
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_indirectMemberListSelected, null);
	}
}


ZaAccountXFormView.isEditAliasEnabled = function () {
	return (!AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A2_alias_selection_cache)) && this.getInstanceValue(ZaAccount.A2_alias_selection_cache).length==1);
}

ZaAccountXFormView.isDeleteAliasEnabled = function () {
	return (!AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A2_alias_selection_cache)));
}

ZaAccountXFormView.deleteAliasButtonListener = function () {
	var instance = this.getInstance();
	if(instance[ZaAccount.A2_alias_selection_cache] != null) {
		var cnt = instance[ZaAccount.A2_alias_selection_cache].length;
		if(cnt && instance.attrs[ZaAccount.A_zmailMailAlias]) {
			var aliasArr = instance.attrs[ZaAccount.A_zmailMailAlias];
			for(var i=0;i<cnt;i++) {
				var cnt2 = aliasArr.length-1;				
				for(var k=cnt2;k>=0;k--) {
					if(aliasArr[k]==instance[ZaAccount.A2_alias_selection_cache][i]) {
						aliasArr.splice(k,1);
						break;	
					}
				}
			}
			this.getModel().setInstanceValue(instance, ZaAccount.A_zmailMailAlias, aliasArr);	
		}
	}
	this.getModel().setInstanceValue(instance, ZaAccount.A2_alias_selection_cache, []);
	this.getForm().parent.setDirty(true);
}

ZaAccountXFormView.editAliasButtonListener =
function () {
	var instance = this.getInstance();
	if(instance.alias_selection_cache && instance.alias_selection_cache[0]) {	
		var formPage = this.getForm().parent;
		if(!formPage.editAliasDlg) {
			formPage.editAliasDlg = new ZaEditAliasXDialog(ZaApp.getInstance().getAppCtxt().getShell(), "550px", "150px",ZaMsg.Edit_Alias_Title);
			formPage.editAliasDlg.registerCallback(DwtDialog.OK_BUTTON, ZaAccountXFormView.updateAlias, this.getForm(), null);						
		}
		var obj = {};
		obj[ZaAccount.A_name] = instance[ZaAccount.A2_alias_selection_cache][0];
		var cnt = instance.attrs[ZaAccount.A_zmailMailAlias].length;
		for(var i=0;i<cnt;i++) {
			if(instance[ZaAccount.A2_alias_selection_cache][0]==instance.attrs[ZaAccount.A_zmailMailAlias][i]) {
				obj[ZaAlias.A_index] = i;
				break;		
			}
		}
		
		formPage.editAliasDlg.setObject(obj);
		formPage.editAliasDlg.popup();		
	}
}

ZaAccountXFormView.updateAlias = function () {
	if(this.parent.editAliasDlg) {
		this.parent.editAliasDlg.popdown();
		var obj = this.parent.editAliasDlg.getObject();
		var instance = this.getInstance();
		var arr = instance.attrs[ZaAccount.A_zmailMailAlias];
		if(obj[ZaAlias.A_index] >=0 && arr[obj[ZaAlias.A_index]] != obj[ZaAccount.A_name] ) {	
			//get domain name
			var domain;
			var domainName = ZaAccount.getDomain(obj[ZaAccount.A_name]);
			try {
				domain = ZaDomain.getDomainByName(domainName);
			} catch (ex) {
				
			}
			//check if have access to create aliases in this domain
			if(!domain || !ZaItem.hasRight(ZaDomain.RIGHT_CREATE_ALIAS, domain)) {		
				ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_NO_PERMISSION_CREATE_ALIAS, [domainName])) ;
			} else {
                var viewController = null;
				viewController = ZaApp.getInstance().getControllerById (this.parent.__internalId);

				var account = null;
				if(viewController) {
					account = viewController._findAlias(obj[ZaAccount.A_name]);
				}

				if(account) {
					var warning = null;
                    switch(account.type) {
							case ZaItem.DL:
								if(account.name == obj[ZaAccount.A_name]) {
									warning = AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS3,[account.name]);
								} else {
									warning = AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS4,[account.name, obj[ZaAccount.A_name]]);
								}
							break;
							case ZaItem.ACCOUNT:
								if(account.name == obj[ZaAccount.A_name]) {
									warning= AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS2,[account.name]);
								} else {
									warning= AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS1,[account.name, obj[ZaAccount.A_name]]);
								}
							break;
							case ZaItem.RESOURCE:
								if(account.name == obj[ZaAccount.A_name]) {
									warning = AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS5,[account.name]);
								} else {
									warning = AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS6,[account.name, obj[ZaAccount.A_name]]);
								}
							break;
							default:
								warning = AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS0,[obj[ZaAccount.A_name]]);
							break;
                    }
					ZaApp.getInstance().getCurrentController().popupErrorDialog(warning);
				} else {
                    arr[obj[ZaAlias.A_index]] = obj[ZaAccount.A_name];
                    this.getModel().setInstanceValue(this.getInstance(),ZaAccount.A_zmailMailAlias, arr);
                    this.getModel().setInstanceValue(this.getInstance(),ZaAccount.A2_alias_selection_cache, new Array());
                    this.parent.setDirty(true);
                }
			}
		}
	}
}

ZaAccountXFormView.addAliasButtonListener =
function () {
	var instance = this.getInstance();
	var formPage = this.getForm().parent;
	if(!formPage.addAliasDlg) {
		formPage.addAliasDlg = new ZaEditAliasXDialog(ZaApp.getInstance().getAppCtxt().getShell(), "550px", "150px",ZaMsg.Add_Alias_Title);
		formPage.addAliasDlg.registerCallback(DwtDialog.OK_BUTTON, ZaAccountXFormView.addAlias, this.getForm(), null);						
	}
	
	var obj = {};
	obj[ZaAccount.A_name] = "";
	obj[ZaAlias.A_index] = - 1;
	formPage.addAliasDlg.setObject(obj);
	formPage.addAliasDlg.popup();		
}

ZaAccountXFormView.addAlias  = function () {
	if(this.parent.addAliasDlg) {
		this.parent.addAliasDlg.popdown();
		var obj = this.parent.addAliasDlg.getObject();
		if(obj[ZaAccount.A_name] && obj[ZaAccount.A_name].length>1) {
			//get domain name
			var domain;
			var domainName = ZaAccount.getDomain(obj[ZaAccount.A_name]);
			try {
				domain = ZaDomain.getDomainByName(domainName);
			} catch (ex) {
				
			}
			//check if have access to create aliases in this domain
			if(!domain || !ZaItem.hasRight(ZaDomain.RIGHT_CREATE_ALIAS, domain)) {
				ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_NO_PERMISSION_CREATE_ALIAS, [domainName])) ;
			} else {
				var viewController = null;
				viewController = ZaApp.getInstance().getControllerById (this.parent.__internalId);
				
				var account = null; 
				if(viewController) {
					account = viewController._findAlias(obj[ZaAccount.A_name]);
				}
				
				if(account) {
					var warning = null;
                    switch(account.type) {
							case ZaItem.DL:
								if(account.name == obj[ZaAccount.A_name]) {
									warning = AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS3,[account.name]);
								} else {
									warning = AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS4,[account.name, obj[ZaAccount.A_name]]);
								}
							break;
							case ZaItem.ACCOUNT:
								if(account.name == obj[ZaAccount.A_name]) {
									warning= AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS2,[account.name]);
								} else {
									warning= AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS1,[account.name, obj[ZaAccount.A_name]]);
								}
							break;
							case ZaItem.RESOURCE:
								if(account.name == obj[ZaAccount.A_name]) {
									warning = AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS5,[account.name]);
								} else {
									warning = AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS6,[account.name, obj[ZaAccount.A_name]]);
								}
							break;
							default:
								warning = AjxMessageFormat.format(ZaMsg.WARNING_EACH_ALIAS0,[obj[ZaAccount.A_name]]);
							break;
                    }
					ZaApp.getInstance().getCurrentController().popupErrorDialog(warning);
				}
				else {
					var instance = this.getInstance();
					var arr = instance.attrs[ZaAccount.A_zmailMailAlias]; 
					arr.push(obj[ZaAccount.A_name]);
					this.getModel().setInstanceValue(this.getInstance(),ZaAccount.A_zmailMailAlias, arr);
					this.getModel().setInstanceValue(this.getInstance(),ZaAccount.A2_alias_selection_cache, new Array());
					this.parent.setDirty(true);
				}
				
			}
		}
	}
}


ZaAccountXFormView.isAuthfromInternal =
function(acctName) {
	var res = true;
	var domainName = null
	var acct = acctName.split("@");
	if (acct.length == 2) domainName = acct[1];
	else domainName = acct[0];
	
	if(domainName) {
		var domainObj = ZaDomain.getDomainByName(domainName);
		if(domainObj.attrs[ZaDomain.A_AuthMech] != ZaDomain.AuthMech_zmail){
			res = false;;
		}
		if(!res && domainObj.attrs[ZaDomain.A_zmailAuthFallbackToLocal] == "TRUE")
			res = true;
	}
	return res;
}

ZaAccountXFormView.isAuthfromInternalSync =
function(domainName, attrName) {

        var acctName = null;
        if(attrName) {
                var instance = this.getInstance();
                if(instance)
                        acctName = this.getInstanceValue(attrName);

        }
        if(!acctName) acctName = domainName;
        return ZaAccountXFormView.isAuthfromInternal(acctName);
}


ZaAccountXFormView.isEditFwdAddrEnabled = function () {
	return (!AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A2_fwdAddr_selection_cache)) && this.getInstanceValue(ZaAccount.A2_fwdAddr_selection_cache).length==1);
}

ZaAccountXFormView.isDeleteFwdAddrEnabled = function () {
	return (!AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A2_fwdAddr_selection_cache)));
}

ZaAccountXFormView.isEditCalFwdAddrEnabled = function () {
	return (!AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A2_calFwdAddr_selection_cache)) && this.getInstanceValue(ZaAccount.A2_calFwdAddr_selection_cache).length==1);
}

ZaAccountXFormView.isDeleteCalFwdAddrEnabled = function () {
	return (!AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A2_calFwdAddr_selection_cache)));
}

ZaAccountXFormView.deleteFwdAddrButtonListener = function () {
	var instance = this.getInstance();	
	if(instance[ZaAccount.A2_fwdAddr_selection_cache] != null) {
		var cnt = instance[ZaAccount.A2_fwdAddr_selection_cache].length;
		if(cnt && instance.attrs[ZaAccount.A_zmailMailForwardingAddress]) {
			var arr = instance.attrs[ZaAccount.A_zmailMailForwardingAddress];
			for(var i=0;i<cnt;i++) {
				var cnt2 = arr.length-1;				
				for(var k=cnt2;k>=0;k--) {
					if(arr[k]==instance[ZaAccount.A2_fwdAddr_selection_cache][i]) {
						arr.splice(k,1);
						break;	
					}
				}
			}
			this.getModel().setInstanceValue(instance, ZaAccount.A_zmailMailForwardingAddress, arr);
			this.getModel().setInstanceValue(instance, ZaAccount.A2_fwdAddr_selection_cache, []);	
		}
	}
	this.getForm().parent.setDirty(true);
}

ZaAccountXFormView.deleteCalFwdAddrButtonListener = function () {
	var instance = this.getInstance();	
	if(instance[ZaAccount.A2_calFwdAddr_selection_cache] != null) {
		var cnt = instance[ZaAccount.A2_calFwdAddr_selection_cache].length;
		if(cnt && instance.attrs[ZaAccount.A_zmailPrefCalendarForwardInvitesTo]) {
			var arr = instance.attrs[ZaAccount.A_zmailPrefCalendarForwardInvitesTo];
			for(var i=0;i<cnt;i++) {
				var cnt2 = arr.length-1;				
				for(var k=cnt2;k>=0;k--) {
					if(arr[k]==instance[ZaAccount.A2_calFwdAddr_selection_cache][i]) {
						arr.splice(k,1);
						break;	
					}
				}
			}
			this.getModel().setInstanceValue(instance, ZaAccount.A_zmailPrefCalendarForwardInvitesTo, arr);
			this.getModel().setInstanceValue(instance, ZaAccount.A2_calFwdAddr_selection_cache, []);	
		}
	}
	this.getForm().parent.setDirty(true);
}

ZaAccountXFormView.fwdAddrSelectionListener = 
function (ev) {
	var arr = this.widget.getSelection();	
	if(arr && arr.length) {
		arr.sort();
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_fwdAddr_selection_cache, arr);	
	} else {
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_fwdAddr_selection_cache, []);
	}	
	if (ev.detail == DwtListView.ITEM_DBL_CLICKED) {
		ZaAccountXFormView.editFwdAddrButtonListener.call(this);
	}	
}

ZaAccountXFormView.calFwdAddrSelectionListener = 
function (ev) {
	var arr = this.widget.getSelection();	
	if(arr && arr.length) {
		arr.sort();
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_calFwdAddr_selection_cache, arr);	
	} else {
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_calFwdAddr_selection_cache, []);
	}	
	if (ev.detail == DwtListView.ITEM_DBL_CLICKED) {
		ZaAccountXFormView.editCalFwdAddrButtonListener.call(this);
	}	
}

ZaAccountXFormView.editFwdAddrButtonListener =
function () {
	var instance = this.getInstance();
	if(instance[ZaAccount.A2_fwdAddr_selection_cache] && instance[ZaAccount.A2_fwdAddr_selection_cache][0]) {	
		var formPage = this.getForm().parent;
		if(!formPage.editFwdAddrDlg) {
			formPage.editFwdAddrDlg = new ZaEditFwdAddrXDialog(ZaApp.getInstance().getAppCtxt().getShell(),"400px", "150px",ZaMsg.Edit_FwdAddr_Title);
			formPage.editFwdAddrDlg.registerCallback(DwtDialog.OK_BUTTON, ZaAccountXFormView.updateFwdAddr, this.getForm(), null);						
		}
		var obj = {};
		obj[ZaAccount.A_name] = instance[ZaAccount.A2_fwdAddr_selection_cache][0];
		var cnt = instance.attrs[ZaAccount.A_zmailMailForwardingAddress].length;
		for(var i=0;i<cnt;i++) {
			if(instance.fwdAddr_selection_cache[0]==instance.attrs[ZaAccount.A_zmailMailForwardingAddress][i]) {
				obj[ZaAlias.A_index] = i;
				break;		
			}
		}
		
		formPage.editFwdAddrDlg.setObject(obj);
		formPage.editFwdAddrDlg.popup();		
	}
}

ZaAccountXFormView.editCalFwdAddrButtonListener =
function () {
	var instance = this.getInstance();
	if(instance[ZaAccount.A2_calFwdAddr_selection_cache] && instance[ZaAccount.A2_calFwdAddr_selection_cache][0]) {	
		var formPage = this.getForm().parent;
		if(!formPage.editCalFwdAddrDlg) {
			formPage.editCalFwdAddrDlg = new ZaEditFwdAddrXDialog(ZaApp.getInstance().getAppCtxt().getShell(),"400px", "150px",ZaMsg.Edit_FwdAddr_Title);
			formPage.editCalFwdAddrDlg.registerCallback(DwtDialog.OK_BUTTON, ZaAccountXFormView.updateCalFwdAddr, this.getForm(), null);						
		}
		var obj = {};
		obj[ZaAccount.A_name] = instance[ZaAccount.A2_calFwdAddr_selection_cache][0];
		var cnt = instance.attrs[ZaAccount.A_zmailPrefCalendarForwardInvitesTo].length;
		for(var i=0;i<cnt;i++) {
			if(instance[ZaAccount.A2_calFwdAddr_selection_cache][0]==instance.attrs[ZaAccount.A_zmailPrefCalendarForwardInvitesTo][i]) {
				obj[ZaAlias.A_index] = i;
				break;		
			}
		}
		
		formPage.editCalFwdAddrDlg.setObject(obj);
		formPage.editCalFwdAddrDlg.popup();		
	}
}
ZaAccountXFormView.updateFwdAddr = function () {
	if(this.parent.editFwdAddrDlg) {
		this.parent.editFwdAddrDlg.popdown();
		var obj = this.parent.editFwdAddrDlg.getObject();
		var instance = this.getInstance();
		var arr = instance.attrs[ZaAccount.A_zmailMailForwardingAddress];
		if(obj[ZaAlias.A_index] >=0 && arr[obj[ZaAlias.A_index]] != obj[ZaAccount.A_name] ) {
			this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_fwdAddr_selection_cache, []);
			arr[obj[ZaAlias.A_index]] = obj[ZaAccount.A_name];
			this.getModel().setInstanceValue(instance, ZaAccount.A_zmailMailForwardingAddress, arr);
			this.parent.setDirty(true);	
		}
	}
}

ZaAccountXFormView.updateCalFwdAddr = function () {
	if(this.parent.editCalFwdAddrDlg) {
		this.parent.editCalFwdAddrDlg.popdown();
		var obj = this.parent.editCalFwdAddrDlg.getObject();
		var instance = this.getInstance();
		var arr = instance.attrs[ZaAccount.A_zmailPrefCalendarForwardInvitesTo];
		if(obj[ZaAlias.A_index] >=0 && arr[obj[ZaAlias.A_index]] != obj[ZaAccount.A_name] ) {
			this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_calFwdAddr_selection_cache, []);
			arr[obj[ZaAlias.A_index]] = obj[ZaAccount.A_name];
			this.getModel().setInstanceValue(instance, ZaAccount.A_zmailPrefCalendarForwardInvitesTo, arr);
			this.parent.setDirty(true);	
		}
	}
}

ZaAccountXFormView.addFwdAddrButtonListener =
function () {
	var instance = this.getInstance();
	var formPage = this.getForm().parent;
	if(!formPage.addFwdAddrDlg) {
		formPage.addFwdAddrDlg = new ZaEditFwdAddrXDialog(ZaApp.getInstance().getAppCtxt().getShell(), "400px", "150px",ZaMsg.Add_FwdAddr_Title);
		formPage.addFwdAddrDlg.registerCallback(DwtDialog.OK_BUTTON, ZaAccountXFormView.addFwdAddr, this.getForm(), null);						
	}
	
	var obj = {};
	obj[ZaAccount.A_name] = "";
	obj[ZaAlias.A_index] = - 1;
	formPage.addFwdAddrDlg.setObject(obj);
	formPage.addFwdAddrDlg.popup();		
}

ZaAccountXFormView.addCalFwdAddrButtonListener =
function () {
	var instance = this.getInstance();
	var formPage = this.getForm().parent;
	if(!formPage.addCalFwdAddrDlg) {
		formPage.addCalFwdAddrDlg = new ZaEditFwdAddrXDialog(ZaApp.getInstance().getAppCtxt().getShell(), "400px", "150px",ZaMsg.Add_FwdAddr_Title);
		formPage.addCalFwdAddrDlg.registerCallback(DwtDialog.OK_BUTTON, ZaAccountXFormView.addCalFwdAddr, this.getForm(), null);						
	}
	
	var obj = {};
	obj[ZaAccount.A_name] = "";
	obj[ZaAlias.A_index] = - 1;
	formPage.addCalFwdAddrDlg.setObject(obj);
	formPage.addCalFwdAddrDlg.popup();		
}

ZaAccountXFormView.addFwdAddr  = function () {
	if(this.parent.addFwdAddrDlg) {
		this.parent.addFwdAddrDlg.popdown();
		var obj = this.parent.addFwdAddrDlg.getObject();
		if(obj[ZaAccount.A_name] && obj[ZaAccount.A_name].length>1) {
			var arr = this.getInstance().attrs[ZaAccount.A_zmailMailForwardingAddress];
			arr.push(obj[ZaAccount.A_name]);
			this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A_zmailMailForwardingAddress, arr);
			this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_fwdAddr_selection_cache, []);
			this.parent.setDirty(true);
		}
	}
}

ZaAccountXFormView.addCalFwdAddr  = function () {
	if(this.parent.addCalFwdAddrDlg) {
		this.parent.addCalFwdAddrDlg.popdown();
		var obj = this.parent.addCalFwdAddrDlg.getObject();
		if(obj[ZaAccount.A_name] && obj[ZaAccount.A_name].length>1) {
			var arr = this.getInstance().attrs[ZaAccount.A_zmailPrefCalendarForwardInvitesTo];
			arr.push(obj[ZaAccount.A_name]);
			this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A_zmailPrefCalendarForwardInvitesTo, arr);
			this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_calFwdAddr_selection_cache, []);
			this.parent.setDirty(true);
		}
	}
}

//interop account
ZaAccountXFormView.fpSelectionListener =
function (ev) {
	var arr = this.widget.getSelection();
	if(arr && arr.length) {
		arr.sort();
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_fp_selection_cache, arr);
	} else
		this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_fp_selection_cache, []);

	if (ev.detail == DwtListView.ITEM_DBL_CLICKED) {
		ZaAccountXFormView.editFpButtonListener.call(this);
	}
}

ZaAccountXFormView.isEditFpEnabled = function () {
	return (!AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A2_fp_selection_cache)) && this.getInstanceValue(ZaAccount.A2_fp_selection_cache).length==1
            && !AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A_zmailForeignPrincipal)));
}

ZaAccountXFormView.isDeleteFpEnabled = function () {
	return (!AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A2_fp_selection_cache)));
}

ZaAccountXFormView.isPushFpEnabled = function () {
	return (!AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A_zmailForeignPrincipal)));
}

ZaAccountXFormView.deleteFpButtonListener = function () {
	var instance = this.getInstance();
	if(!AjxUtil.isEmpty(instance.fp_selection_cache)) {
		var cnt = instance.fp_selection_cache.length;
		var arr = instance.attrs[ZaAccount.A_zmailForeignPrincipal];
		if(cnt && !AjxUtil.isEmpty(arr)) {
			for(var i=0;i<cnt;i++) {
				var cnt2 = arr.length-1;
				for(var k=cnt2;k>=0;k--) {
					if(arr[k]==instance.fp_selection_cache[i]) {
						arr.splice(k,1);
						break;
					}
				}
			}
			this.getModel().setInstanceValue(instance, ZaAccount.A_zmailForeignPrincipal, arr);
			this.getModel().setInstanceValue(instance, ZaAccount.A2_fp_selection_cache, []);
		}
	}
	this.getForm().parent.setDirty(true);
}

ZaAccountXFormView.editFpButtonListener =
function () {
	var instance = this.getInstance();
	if(instance.fp_selection_cache && instance.fp_selection_cache[0]) {
		var formPage = this.getForm().parent;
		if(!formPage.editFpDlg) {
			formPage.editFpDlg = new ZaEditFpXDialog(ZaApp.getInstance().getAppCtxt().getShell(), "550px", "150px",ZaMsg.Edit_Fp_Title);
			formPage.editFpDlg.registerCallback(DwtDialog.OK_BUTTON, ZaAccountXFormView.updateFp, this.getForm(), null);
		}
		var obj = ZaFp.getObject (instance.fp_selection_cache[0]) ;
		var cnt = instance.attrs[ZaAccount.A_zmailForeignPrincipal].length;
		for(var i=0;i<cnt;i++) {
			if(instance.fp_selection_cache[0]==instance.attrs[ZaAccount.A_zmailForeignPrincipal][i]) {
				obj[ZaFp.A_index] = i;
				break;
			}
		}

		formPage.editFpDlg.setObject(obj);
		formPage.editFpDlg.popup();
	}
}

ZaAccountXFormView.pushFpButtonListener = function () {
	var instance = this.getInstance();

    if (this.getForm().parent.isDirty()) {
       ZaApp.getInstance().getCurrentController().popupMsgDialog (ZaMsg.DIRTY_SAVE_ACCT, true);
    } else if (instance.attrs[ZaAccount.A_zmailForeignPrincipal].length > 0) {
	   ZaFp.push (instance.id);
  	}
}

ZaAccountXFormView.updateFp = function () {
	if(this.parent.editFpDlg) {
        this.parent.editFpDlg.popdown();
		var obj = this.parent.editFpDlg.getObject();
		var arr = this.getInstance().attrs[ZaAccount.A_zmailForeignPrincipal];
		if(obj[ZaFp.A_index] >=0 && arr[obj[ZaFp.A_index]] != ZaFp.getEntry (obj) ) {
			this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_fp_selection_cache, []);
			arr[obj[ZaFp.A_index]] = ZaFp.getEntry (obj);
			this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A_zmailForeignPrincipal, arr);
			this.parent.setDirty(true);
		}
	}
}

ZaAccountXFormView.addFpButtonListener =
function () {
	var instance = this.getInstance();
	var formPage = this.getForm().parent;
    
    if(!formPage.addFpDlg) {
		formPage.addFpDlg = new ZaEditFpXDialog(ZaApp.getInstance().getAppCtxt().getShell(), "550px", "150px",ZaMsg.Add_Fp_Title);
		formPage.addFpDlg.registerCallback(DwtDialog.OK_BUTTON, ZaAccountXFormView.addFp, this.getForm(), null);
	}

    var obj = {};
    obj [ZaFp.A_prefix] = "" ;
    obj [ZaFp.A_name] = "";
    obj [ZaFp.A_index] = -1 ;
    
    formPage.addFpDlg.setObject(obj);
	formPage.addFpDlg.popup();
}

ZaAccountXFormView.addFp  = function () {
	if(this.parent.addFpDlg) {
        var obj = this.parent.addFpDlg.getObject();
        var app = this.parent._app ;
        var currentFps =  this.getInstance().attrs[ZaAccount.A_zmailForeignPrincipal] ;
        if (ZaFp.findDupPrefixFp(currentFps, obj[ZaFp.A_prefix])){
            ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_ONE_FP_PREFIX_ALLOWED, null);
        }  else {
            this.parent.addFpDlg.popdown();
            if(ZaFp.getEntry(obj).length > 0) {
                currentFps.push(ZaFp.getEntry(obj));
                this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A_zmailForeignPrincipal, currentFps);
				this.getModel().setInstanceValue(this.getInstance(), ZaAccount.A2_fp_selection_cache, []);
                this.parent.setDirty(true);
            }
        }
    }
}

ZaAccountXFormView.isDomainLeftAccountsAlertVisible = function () {
	var val1 = this.getInstanceValue(ZaAccount.A2_domainLeftAccounts);
	var val2 = this.getInstanceValue(ZaAccount.A2_accountTypes);
	return (!AjxUtil.isEmpty(val1) && AjxUtil.isEmpty(val2));
}

ZaAccountXFormView.isAccountTypeGrouperVisible = function () {
	 return !AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A2_accountTypes));
}

ZaAccountXFormView.isAccountsTypeAlertInvisible = function () {
        var val = this.getInstanceValue(ZaAccount.A2_showAccountTypeMsg);
        return (AjxUtil.isEmpty(val));
}

ZaAccountXFormView.isAccountTypeSet = function () {
	 return !ZaAccount.isAccountTypeSet(this.getInstance());
}

ZaAccountXFormView.CONTACT_TAB_ATTRS = [ZaAccount.A_telephoneNumber,
        ZaAccount.A_homePhone,
        ZaAccount.A_mobile,
        ZaAccount.A_pager ,
		ZaAccount.A_company,
        ZaAccount.A_title,
        ZaAccount.A_facsimileTelephoneNumber,
		ZaAccount.A_street, 
		ZaAccount.A_city, 
		ZaAccount.A_state,
		ZaAccount.A_zip,
		ZaAccount.A_country];
ZaAccountXFormView.CONTACT_TAB_RIGHTS = [];

ZaAccountXFormView.ACCOUNT_NAME_GROUP_ATTRS = [ZaAccount.A_name,
        ZaAccount.A_firstName,
        ZaAccount.A_initials,
        ZaAccount.A_lastName,
        ZaAccount.A_displayname,
	ZaAccount.A_zmailHideInGal 
];

ZaAccountXFormView.MEMBEROF_TAB_ATTRS = [];		
ZaAccountXFormView.MEMBEROF_TAB_RIGHTS = [ZaAccount.GET_ACCOUNT_MEMBERSHIP_RIGHT];

ZaAccountXFormView.FEATURE_TAB_ATTRS = [ZaAccount.A_zmailFeatureManageZimlets,
	ZaAccount.A_zmailFeatureReadReceiptsEnabled,
	ZaAccount.A_zmailFeatureMailEnabled,
	ZaAccount.A_zmailFeatureContactsEnabled,
    ZaAccount.A_zmailFeatureDistributionListFolderEnabled,
	ZaAccount.A_zmailFeatureCalendarEnabled,
	ZaAccount.A_zmailFeatureTasksEnabled,
	//ZaAccount.A_zmailFeatureNotebookEnabled,
	ZaAccount.A_zmailFeatureBriefcasesEnabled,
	//ZaAccount.A_zmailFeatureBriefcaseDocsEnabled,
	//ZaAccount.A_zmailFeatureIMEnabled,
	ZaAccount.A_zmailFeatureOptionsEnabled,
	ZaAccount.A_zmailFeatureTaggingEnabled,
	ZaAccount.A_zmailFeatureSharingEnabled,
	ZaAccount.A_zmailExternalSharingEnabled,
	ZaAccount.A_zmailSharingEnabled,
	ZaAccount.A_zmailFeatureChangePasswordEnabled,
	ZaAccount.A_zmailFeatureSkinChangeEnabled,
	ZaAccount.A_zmailFeatureHtmlComposeEnabled,
	//ZaAccount.A_zmailFeatureShortcutAliasesEnabled,
	ZaAccount.A_zmailFeatureGalEnabled,
	ZaAccount.A_zmailFeatureMAPIConnectorEnabled,
	ZaAccount.A_zmailFeatureGalAutoCompleteEnabled,
	ZaAccount.A_zmailFeatureMailPriorityEnabled,
	ZaAccount.A_zmailFeatureFlaggingEnabled,
	ZaAccount.A_zmailImapEnabled,
	ZaAccount.A_zmailPop3Enabled,
	ZaAccount.A_zmailFeatureImapDataSourceEnabled,
	ZaAccount.A_zmailFeaturePop3DataSourceEnabled,
	ZaAccount.A_zmailFeatureMailSendLaterEnabled,
	//ZaAccount.A_zmailFeatureFreeBusyViewEnabled,
	ZaAccount.A_zmailFeatureConversationsEnabled,
	ZaAccount.A_zmailFeatureFiltersEnabled,
	ZaAccount.A_zmailFeatureOutOfOfficeReplyEnabled,
	ZaAccount.A_zmailFeatureNewMailNotificationEnabled,
	ZaAccount.A_zmailFeatureMailPollingIntervalPreferenceEnabled,
	ZaAccount.A_zmailFeatureIdentitiesEnabled,
	ZaAccount.A_zmailFeatureGroupCalendarEnabled,
	//ZaAccount.A_zmailFeatureInstantNotify,
  ZaAccount.A_zmailFeaturePeopleSearchEnabled,
  ZaAccount.A_zmailFeatureAdvancedSearchEnabled,
	ZaAccount.A_zmailFeatureSavedSearchesEnabled,
	ZaAccount.A_zmailFeatureInitialSearchPreferenceEnabled,
	ZaAccount.A_zmailFeatureImportFolderEnabled,
    ZaAccount.A_zmailFeatureExportFolderEnabled,
	ZaAccount.A_zmailDumpsterEnabled,
	ZaAccount.A_zmailDumpsterPurgeEnabled,
	ZaAccount.A_zmailFeatureSMIMEEnabled,
    ZaAccount.A_zmailFeatureCalendarReminderDeviceEmailEnabled
];

ZaAccountXFormView.FEATURE_TAB_RIGHTS = [];
ZaAccountXFormView.PREFERENCES_TAB_ATTRS = [
	ZaAccount.A_zmailPrefReadReceiptsToAddress,
	ZaAccount.A_zmailPrefMailSendReadReceipts,
	ZaAccount.A_zmailPrefUseTimeZoneListInCalendar,
	ZaAccount.A_zmailPrefCalendarUseQuickAdd,
	ZaAccount.A_zmailPrefCalendarAlwaysShowMiniCal,
	ZaAccount.A_zmailPrefCalendarApptReminderWarningTime,
	ZaAccount.A_zmailPrefTimeZoneId,
	ZaAccount.A_zmailPrefGalAutoCompleteEnabled,
	ZaAccount.A_zmailPrefAutoAddAddressEnabled,
	ZaAccount.A_zmailPrefMailSignature,
	ZaAccount.A_zmailMailSignatureMaxLength,
	//ZaAccount.A_zmailPrefMailSignatureStyle,
	ZaAccount.A_zmailPrefMailSignatureEnabled,
	ZaAccount.A_zmailPrefForwardReplyInOriginalFormat,
	ZaAccount.A_zmailPrefHtmlEditorDefaultFontColor,
	ZaAccount.A_zmailPrefHtmlEditorDefaultFontFamily,
	ZaAccount.A_zmailPrefHtmlEditorDefaultFontSize,
	ZaAccount.A_zmailPrefComposeFormat,
	ZaAccount.A_zmailPrefComposeInNewWindow,
	ZaAccount.A_zmailAllowFromAddress,
	ZaAccount.A_zmailAllowAnyFromAddress,
	ZaAccount.A_zmailPrefSaveToSent,
	ZaAccount.A_zmailPrefOutOfOfficeReply,
	ZaAccount.A_zmailPrefNewMailNotificationAddress,
	ZaAccount.A_zmailPrefNewMailNotificationEnabled,
	ZaAccount.A_zmailMailMinPollingInterval,
	ZaAccount.A_zmailPrefMailPollingInterval,
	ZaAccount.A_zmailPrefAutoSaveDraftInterval,
    ZaAccount.A_zmailPrefMailSoundsEnabled,
    ZaAccount.A_zmailPrefMailFlashIcon,
    ZaAccount.A_zmailPrefMailFlashTitle,
	ZaAccount.A_zmailPrefMailDefaultCharset,
	ZaAccount.A_zmailMaxMailItemsPerPage,
	ZaAccount.A_zmailPrefMailItemsPerPage,
	ZaAccount.A_zmailPrefGroupMailBy,
	ZaAccount.A_zmailPrefDisplayExternalImages,
	ZaAccount.A_zmailPrefMessageViewHtmlPreferred,
	ZaAccount.A_zmailPrefLocale,
	ZaAccount.A_zmailJunkMessagesIndexingEnabled,
	ZaAccount.A_zmailPrefShowSelectionCheckbox,
	ZaAccount.A_zmailPrefWarnOnExit,
    ZaAccount.A_zmailPrefAdminConsoleWarnOnExit,    
	ZaAccount.A_zmailPrefUseKeyboardShortcuts,
	ZaAccount.A_zmailPrefImapSearchFoldersEnabled,
	ZaAccount.A_zmailPrefShowSearchString,
	ZaAccount.A_zmailPrefMailInitialSearch,
	ZaAccount.A_zmailPrefClientType,
	ZaAccount.A_zmailPrefCalendarInitialView,
	ZaAccount.A_zmailPrefCalendarFirstDayOfWeek,
	ZaAccount.A_zmailPrefCalendarNotifyDelegatedChanges,
	ZaAccount.A_zmailPrefCalendarApptVisibility,
	ZaAccount.A_zmailPrefCalendarAutoAddInvites,
	ZaAccount.A_zmailPrefCalendarReminderSoundsEnabled,
	ZaAccount.A_zmailPrefCalendarReminderFlashTitle,
	ZaAccount.A_zmailPrefCalendarAllowForwardedInvite,
	ZaAccount.A_zmailPrefCalendarAllowPublishMethodInvite,
	ZaAccount.A_zmailPrefCalendarAllowCancelEmailToSelf,
	ZaAccount.A_zmailPrefCalendarToasterEnabled,
	ZaAccount.A_zmailPrefCalendarShowPastDueReminders,
	ZaAccount.A_zmailPrefAppleIcalDelegationEnabled,
	ZaAccount.A_zmailPrefMandatorySpellCheckEnabled
];
ZaAccountXFormView.PREFERENCES_TAB_RIGHTS = [];	

ZaAccountXFormView.ALIASES_TAB_ATTRS = [ZaAccount.A_zmailMailAlias];
ZaAccountXFormView.ALIASES_TAB_RIGHTS = [ZaAccount.ADD_ACCOUNT_ALIAS_RIGHT, ZaAccount.REMOVE_ACCOUNT_ALIAS_RIGHT];

ZaAccountXFormView.FORWARDING_TAB_ATTRS = [ZaAccount.A_zmailFeatureMailForwardingEnabled,
	ZaAccount.A_zmailPrefMailLocalDeliveryDisabled,
	ZaAccount.A_zmailMailForwardingAddress,
	ZaAccount.A_zmailPrefCalendarForwardInvitesTo];
ZaAccountXFormView.FORWARDING_TAB_RIGHTS = [];

ZaAccountXFormView.INTEROP_TAB_ATTRS = [ZaAccount.A_zmailForeignPrincipal];
ZaAccountXFormView.INTEROP_TAB_RIGHTS = [];

ZaAccountXFormView.SKIN_TAB_ATTRS = [ZaAccount.A_zmailPrefSkin,ZaAccount.A_zmailAvailableSkin];
ZaAccountXFormView.SKIN_TAB_RIGHTS = [];

ZaAccountXFormView.ZIMLET_TAB_ATTRS = [ZaAccount.A_zmailZimletAvailableZimlets];
ZaAccountXFormView.ZIMLET_TAB_RIGHTS = [];

ZaAccountXFormView.ADVANCED_TAB_ATTRS = [ZaAccount.A_zmailAttachmentsBlocked,
	ZaAccount.A_zmailMailQuota,
	ZaAccount.A_zmailContactMaxNumEntries,
	ZaAccount.A_zmailQuotaWarnPercent,
	ZaAccount.A_zmailQuotaWarnInterval,
	ZaAccount.A_zmailQuotaWarnMessage,
	ZaAccount.A_zmailPasswordLocked,
	ZaAccount.A_zmailMinPwdLength,
	ZaAccount.A_zmailMaxPwdLength,
	ZaAccount.A_zmailPasswordMinUpperCaseChars,
	ZaAccount.A_zmailPasswordMinLowerCaseChars,
	ZaAccount.A_zmailPasswordMinPunctuationChars,
	ZaAccount.A_zmailPasswordMinNumericChars,
	ZaAccount.A_zmailPasswordMinDigitsOrPuncs,
	ZaAccount.A_zmailMinPwdAge,
	ZaAccount.A_zmailMaxPwdAge,
	ZaAccount.A_zmailEnforcePwdHistory,
	ZaAccount.A_zmailPasswordLockoutEnabled,
	ZaAccount.A_zmailPasswordLockoutMaxFailures,
	ZaAccount.A_zmailPasswordLockoutDuration,
	ZaAccount.A_zmailPasswordLockoutFailureLifetime,
	ZaAccount.A_zmailAdminAuthTokenLifetime,
	ZaAccount.A_zmailAuthTokenLifetime,
	ZaAccount.A_zmailMailIdleSessionTimeout,
	ZaAccount.A_zmailMailMessageLifetime,
	ZaAccount.A_zmailMailTrashLifetime,
	ZaAccount.A_zmailMailSpamLifetime,
	ZaAccount.A_zmailDumpsterUserVisibleAge,
	ZaAccount.A_zmailMailDumpsterLifetime,
	ZaAccount.A_zmailFreebusyExchangeUserOrg,
	ZaAccount.A_zmailMailCanonicalAddress,	
	ZaAccount.A_zmailMailTransport	
	];
ZaAccountXFormView.ADVANCED_TAB_RIGHTS = [];

ZaAccountXFormView.addressItemsPool = null;
ZaAccountXFormView.addressItemsPoolForDialog = null;
ZaAccountXFormView.getAddressFormItem = function(){
	// the subItems of Address Items only init once;
	if(AjxUtil.isEmpty(ZaAccountXFormView.addressItemsPool)){
		ZaAccountXFormView.addressItemsPool = new Object();
		ZaAccountXFormView.addressItemsPool[ZaAccount.A_zip] =  {ref:ZaAccount.A_zip, type:_TEXTFIELD_, msgName:ZaMsg.NAD_zip,label:ZaMsg.NAD_zip,
            labelLocation:_LEFT_, width:100};
		ZaAccountXFormView.addressItemsPool[ZaAccount.A_state] = {ref:ZaAccount.A_state, type:_TEXTFIELD_, msgName:ZaMsg.NAD_state,label:ZaMsg.NAD_state,
            labelLocation:_LEFT_, width:250};
		ZaAccountXFormView.addressItemsPool[ZaAccount.A_street] = {ref:ZaAccount.A_street, type:_TEXTAREA_, msgName:ZaMsg.NAD_street,label:ZaMsg.NAD_street,
            labelLocation:_LEFT_, width:250};
		ZaAccountXFormView.addressItemsPool[ZaAccount.A_city] = {ref:ZaAccount.A_city, type:_TEXTFIELD_, msgName:ZaMsg.NAD_city,label:ZaMsg.NAD_city,
            labelLocation:_LEFT_, width:250};
		ZaAccountXFormView.addressItemsPool[ZaAccount.A_country] = {ref:ZaAccount.A_country, type:_TEXTFIELD_, msgName:ZaMsg.NAD_country,label:ZaMsg.NAD_country,
            labelLocation:_LEFT_, width:250};
		
	}
	var addressFormItems = new Array();
	var addressFormItemsOrders = new Array();
	if(ZaZmailAdmin.isLanguage("ja")){
		addressFormItemsOrders = [ZaAccount.A_zip, ZaAccount.A_state, ZaAccount.A_city, ZaAccount.A_street, ZaAccount.A_country];
	}
	else{
		addressFormItemsOrders = [ZaAccount.A_street, ZaAccount.A_city, ZaAccount.A_state, ZaAccount.A_zip, ZaAccount.A_country]; 
	}
	
	for(var i = 0; i < addressFormItemsOrders.length; i++){
		addressFormItems.push(ZaAccountXFormView.addressItemsPool[addressFormItemsOrders[i]]);
	}
	return addressFormItems;
}
ZaAccountXFormView.getAddressFormItemForDialog = function(){
	// the subItems of Address Items only init once;
	if(AjxUtil.isEmpty(ZaAccountXFormView.addressItemsPoolForDialog)){
		ZaAccountXFormView.addressItemsPoolForDialog = new Object();
		ZaAccountXFormView.addressItemsPoolForDialog[ZaAccount.A_zip] =  {ref:ZaAccount.A_zip, type:_TEXTFIELD_, msgName:ZaMsg.NAD_zip,label:ZaMsg.NAD_zip,
            labelLocation:_LEFT_, width:100};
		ZaAccountXFormView.addressItemsPoolForDialog[ZaAccount.A_state] = {ref:ZaAccount.A_state, type:_TEXTFIELD_, msgName:ZaMsg.NAD_state,label:ZaMsg.NAD_state,
            labelLocation:_LEFT_, width:250};
		ZaAccountXFormView.addressItemsPoolForDialog[ZaAccount.A_street] = {ref:ZaAccount.A_street, type:_TEXTAREA_, msgName:ZaMsg.NAD_street,label:ZaMsg.NAD_street,
            labelLocation:_LEFT_, width:250};
		ZaAccountXFormView.addressItemsPoolForDialog[ZaAccount.A_city] = {ref:ZaAccount.A_city, type:_TEXTFIELD_, msgName:ZaMsg.NAD_city,label:ZaMsg.NAD_city,
            labelLocation:_LEFT_, width:250};
		ZaAccountXFormView.addressItemsPoolForDialog[ZaAccount.A_country] = {ref:ZaAccount.A_country, type:_TEXTFIELD_, msgName:ZaMsg.NAD_country,label:ZaMsg.NAD_country,
            labelLocation:_LEFT_, width:250};

	}
	var addressFormItems = new Array();
	var addressFormItemsOrders = new Array();
	if(ZaZmailAdmin.isLanguage("ja")){
		addressFormItemsOrders = [ZaAccount.A_zip, ZaAccount.A_state, ZaAccount.A_city, ZaAccount.A_street, ZaAccount.A_country];
	}
	else{
		addressFormItemsOrders = [ZaAccount.A_street, ZaAccount.A_city, ZaAccount.A_state, ZaAccount.A_zip, ZaAccount.A_country];
	}

	for(var i = 0; i < addressFormItemsOrders.length; i++){
		addressFormItems.push(ZaAccountXFormView.addressItemsPoolForDialog[addressFormItemsOrders[i]]);
	}
	return addressFormItems;
}

ZaAccountXFormView.accountNameInfoPool = null;
ZaAccountXFormView.getAccountNameInfoItem = function(){
	if(AjxUtil.isEmpty(ZaAccountXFormView.accountNameInfoPool)){
		ZaAccountXFormView.accountNameInfoPool = new Object();
		ZaAccountXFormView.accountNameInfoPool[ZaAccount.A_name] = {ref:ZaAccount.A_name, type:_EMAILADDR_,
					 msgName:ZaMsg.NAD_AccountName,label:ZaMsg.NAD_AccountName, bmolsnr:false,
                                        labelLocation:_LEFT_,onChange:ZaAccount.setDomainChanged,forceUpdate:true,
                                        enableDisableChecks:[[ZaItem.hasRight,ZaAccount.RENAME_ACCOUNT_RIGHT]],
                                        visibilityChecks:[]
            ,domainPartWidth:"100%", domainContainerWidth: "100%"
                                },
		ZaAccountXFormView.accountNameInfoPool[ZaAccount.A_firstName] = {ref:ZaAccount.A_firstName, type:_TEXTFIELD_,
					msgName:ZaMsg.NAD_FirstName,label:ZaMsg.NAD_FirstName, 
					labelLocation:_LEFT_, cssClass:"admin_xform_name_input", width:150,
					elementChanged: function(elementValue,instanceValue, event) {
						if(this.getInstance()[ZaAccount.A2_autodisplayname]=="TRUE") {
							ZaAccount.generateDisplayName.call(this, this.getInstance(), elementValue, this.getInstance().attrs[ZaAccount.A_lastName],this.getInstance().attrs[ZaAccount.A_initials] );
						}
						this.getForm().itemChanged(this, elementValue, event);
					}
				};
		ZaAccountXFormView.accountNameInfoPool[ZaAccount.A_initials] = {ref:ZaAccount.A_initials, type:_TEXTFIELD_,
					msgName:ZaMsg.NAD_Initials,label:ZaMsg.NAD_Initials, labelLocation:_LEFT_, 
					cssClass:"admin_xform_name_input", width:50,
					elementChanged: function(elementValue,instanceValue, event) {
						if(this.getInstance()[ZaAccount.A2_autodisplayname]=="TRUE") {
							ZaAccount.generateDisplayName.call(this, this.getInstance(), this.getInstanceValue(ZaAccount.A_firstName), this.getInstanceValue(ZaAccount.A_lastName),elementValue);
						}
						this.getForm().itemChanged(this, elementValue, event);
					}
				};
		ZaAccountXFormView.accountNameInfoPool[ZaAccount.A_lastName] = {ref:ZaAccount.A_lastName, type:_TEXTFIELD_, 
					msgName:ZaMsg.NAD_LastName,label:ZaMsg.NAD_LastName, labelLocation:_LEFT_, 
					cssClass:"admin_xform_name_input", width:150,
					elementChanged: function(elementValue,instanceValue, event) {
						if(this.getInstance()[ZaAccount.A2_autodisplayname]=="TRUE") {
							ZaAccount.generateDisplayName.call(this, this.getInstance(),  this.getInstanceValue(ZaAccount.A_firstName), elementValue ,this.getInstanceValue(ZaAccount.A_initials));
						}
						this.getForm().itemChanged(this, elementValue, event);
					}
				};
		ZaAccountXFormView.accountNameInfoPool["ZaAccountDisplayInfoGroup"] = {type:_GROUP_, numCols:3, nowrap:true,
                    attributeName: ZaAccount.A_displayname,
					width:200, msgName:ZaMsg.NAD_DisplayName,label:ZaMsg.NAD_DisplayName, labelLocation:_LEFT_,
                                        visibilityChecks:[[ZaItem.hasReadPermission,ZaAccount.A_displayname]],
                                        items: [
                                                {ref:ZaAccount.A_displayname, type:_TEXTFIELD_, label:null,     cssClass:"admin_xform_name_input", width:150,
                                                        enableDisableChecks:[ [XForm.checkInstanceValue,ZaAccount.A2_autodisplayname,"FALSE"],ZaItem.hasWritePermission],
                                                        enableDisableChangeEventSources:[ZaAccount.A2_autodisplayname],bmolsnr:true,
                                                        visibilityChecks:[]
                                                },
                                                {ref:ZaAccount.A2_autodisplayname, type:_CHECKBOX_, msgName:ZaMsg.NAD_Auto,label:ZaMsg.NAD_Auto,labelLocation:_RIGHT_,trueValue:"TRUE", falseValue:"FALSE", subLabel:"", helpTooltip: false,
                                                        elementChanged: function(elementValue,instanceValue, event) {
                                                                if(elementValue=="TRUE") {
                                                                        if(ZaAccount.generateDisplayName.call(this, this.getInstance(), this.getInstanceValue(ZaAccount.A_firstName), this.getInstanceValue(ZaAccount.A_lastName),this.getInstanceValue(ZaAccount.A_initials))) {
                                                                                this.getForm().parent.setDirty(true);
                                                                        }
                                                                }
                                                                this.getForm().itemChanged(this, elementValue, event);
                                                        },
                                                        enableDisableChecks:[[ZaItem.hasWritePermission,ZaAccount.A_displayname]],
                            visibilityChecks:[[ZaItem.hasWritePermission,ZaAccount.A_displayname]]

                                                }
                                        ]
                                },
		ZaAccountXFormView.accountNameInfoPool[ZaAccount.A_zmailHideInGal]={ref:ZaAccount.A_zmailHideInGal, type:_CHECKBOX_,
				  			msgName:ZaMsg.LBL_zmailHideInGal,
				  			label:ZaMsg.LBL_zmailHideInGal, trueValue:"TRUE", falseValue:"FALSE"
				},
		ZaAccountXFormView.accountNameInfoPool[ZaAccount.A_zmailPhoneticFirstName] = {
					ref:ZaAccount.A_zmailPhoneticFirstName, type:_TEXTFIELD_, 
					msgName:ZaMsg.NAD_zmailPhoneticFirstName,label:ZaMsg.NAD_zmailPhoneticFirstName,
                                        labelLocation:_LEFT_, cssClass:"admin_xform_name_input",width:150
                                };
		ZaAccountXFormView.accountNameInfoPool[ZaAccount.A_zmailPhoneticLastName] = {
                                        ref:ZaAccount.A_zmailPhoneticLastName, type:_TEXTFIELD_,
                                        msgName:ZaMsg.NAD_zmailPhoneticLastName,label:ZaMsg.NAD_zmailPhoneticLastName,
                                        labelLocation:_LEFT_, cssClass:"admin_xform_name_input",width:150
                                };

	}

	var accountNameFormItems = new Array();
        var accountNameItemsOrders = new Array();
        if(ZaZmailAdmin.isLanguage("ja")){
		accountNameItemsOrders = [ZaAccount.A_name, ZaAccount.A_zmailPhoneticLastName, ZaAccount.A_lastName, ZaAccount.A_initials, ZaAccount.A_zmailPhoneticFirstName, ZaAccount.A_firstName, "ZaAccountDisplayInfoGroup", ZaAccount.A_zmailHideInGal];
        }
        else{
		accountNameItemsOrders = [ZaAccount.A_name, ZaAccount.A_firstName, ZaAccount.A_initials, ZaAccount.A_lastName,"ZaAccountDisplayInfoGroup", ZaAccount.A_zmailHideInGal];
        }

        for(var i = 0; i < accountNameItemsOrders.length; i++){
                accountNameFormItems.push(ZaAccountXFormView.accountNameInfoPool[accountNameItemsOrders[i]]);
        }
        return accountNameFormItems;
}

ZaAccountXFormView.validatePollingInterval = function(value, event, form) {
    var instance = form.getInstance ();
	this.setInstanceValue(value);
	var prefPollingInterval = instance.attrs[ZaAccount.A_zmailPrefMailPollingInterval];
	if (!prefPollingInterval) {
		prefPollingInterval = instance._defaultValues.attrs[ZaAccount.A_zmailPrefMailPollingInterval];
	}
	var minPollingInterval = instance.attrs[ZaAccount.A_zmailMailMinPollingInterval];
	if (!minPollingInterval) {
		minPollingInterval = instance._defaultValues.attrs[ZaAccount.A_zmailMailMinPollingInterval];
	}
	var prefPollingIntervalItem = form.getItemsById (ZaAccount.A_zmailPrefMailPollingInterval)[0];
	try {
        var prefPollingInNum = parseInt(ZaUtil.getLifeTimeInSeconds(prefPollingInterval));
        var minPollingNum = parseInt(ZaUtil.getLifeTimeInSeconds(minPollingInterval));
		if (prefPollingInNum < minPollingNum){
			prefPollingIntervalItem.setError (ZaMsg.tt_mailPollingIntervalError + minPollingInterval) ;
			form.parent.setDirty(false);	
		}else{
			prefPollingIntervalItem.clearError();	
			form.parent.setDirty(true);	
		}
	}catch (e){
		prefPollingIntervalItem.setError (e.message);
		form.parent.setDirty(false);
	}
}

/**
* This method is added to the map {@link ZaTabView#XFormModifiers}
* @param xFormObject {Object} a definition of the form. This method adds/removes/modifies xFormObject to construct
* an Account view. 
**/
ZaAccountXFormView.myXFormModifier = function(xFormObject, entry) {
	
	var domainName;
	try {
		domainName = ZaApp.getInstance().getGlobalConfig().attrs[ZaGlobalConfig.A_zmailDefaultDomainName];
		if(!domainName && ZaApp.getInstance().getDomainList().size() > 0)
			domainName = ZaApp.getInstance().getDomainList().getArray()[0].name;
	} catch (ex) { 
		domainName = ZaSettings.myDomainName;
		if(ex.code != ZmCsfeException.SVC_PERM_DENIED) {
			throw(ex);
		}		
	}
		
	var emptyAlias = " @" + domainName;
	var headerItems = [{type:_AJX_IMAGE_, src:"Person_32", label:null, rowSpan:3, cssStyle:"margin:auto;"},
		{type:_OUTPUT_, ref:ZaAccount.A_displayname, label:null,cssClass:"AdminTitle", height:"auto", width:350, rowSpan:3, 
			cssStyle:"word-wrap:break-word;overflow:hidden;",
			visibilityChecks:[ZaItem.hasReadPermission],
			getDisplayValue:function(newValue) {
			return AjxStringUtil.htmlEncode(newValue);
		}
	}];
	/*headerItems.push({type:_OUTPUT_, ref:ZaAccount.A_COSId,valueChangeEventSources:[ZaAccount.A_COSId], labelLocation:_LEFT_, label:ZaMsg.NAD_ClassOfService, choices:this.cosChoices,getDisplayValue:function(newValue) {
			if(ZaItem.ID_PATTERN.test(newValue)) {
				var cos = ZaCos.getCosById(newValue, this.getForm().parent._app);
				if(cos)
					newValue = cos.name;
				} 
				if (newValue == null) {
					newValue = "";
				} else {
					newValue = "" + newValue;
				}
				return newValue;
			},
			visibilityChecks:[ZaItem.hasReadPermission]	
	});*/
	
	if (ZaItem.hasReadPermission(ZaItem.A_zmailId, entry)) {
		headerItems.push({type:_OUTPUT_,ref:ZaItem.A_zmailId, label:ZaMsg.NAD_ZmailID});
	}
	
	if (ZaItem.hasReadPermission(ZaItem.A_zmailCreateTimestamp, entry))	{
		headerItems.push(
						 {
						 type:_OUTPUT_, ref:ZaItem.A_zmailCreateTimestamp,
						 label:ZaMsg.LBL_zmailCreateTimestamp, labelLocation:_LEFT_,
						 getDisplayValue:function() {
						 var val = ZaItem.formatServerTime(this.getInstanceValue());
						 if(!val)
						 return ZaMsg.Server_Time_NA;
						 else
						 return val;
						 }	
						 });
	}

    if (!entry.isExternal && ZaItem.hasReadPermission(ZaAccount.A_mailHost, entry)) {
            headerItems.push({type:_OUTPUT_, ref:ZaAccount.A_mailHost, labelLocation:_LEFT_,label:ZabMsg.attrDesc_mailHost});
    } else if(entry.isExternal && ZaItem.hasReadPermission(ZaAccount.A_zmailMailTransport, entry)) {
        headerItems.push({type:_OUTPUT_, ref:ZaAccount.A_zmailMailTransport, labelLocation:_LEFT_,label:ZabMsg.attrDesc_mailHost});
    }
	
	if (ZaItem.hasReadPermission(ZaAccount.A_name, entry)) {
		headerItems.push({type:_OUTPUT_,ref:ZaAccount.A_name, label:ZaMsg.NAD_Email, labelLocation:_LEFT_, required:false, cssStyle:"word-wrap:break-word;overflow:hidden;"});
	}

    if (ZaItem.hasReadPermission(ZaAccount.A_accountStatus, entry)) {
	    headerItems.push({type:_OUTPUT_,ref:ZaAccount.A_accountStatus, label:ZaMsg.NAD_AccountStatus, labelLocation:_LEFT_, choices:this.accountStatusChoices});
    }

    if (!entry.isExternal) {
        if(ZaItem.hasReadPermission(ZaAccount.A_zmailMailQuota,entry) && ZaItem.hasRight(ZaAccount.GET_MAILBOX_INFO_RIGHT,entry)) {
                headerItems.push(
                    {type:_OUTPUT_,ref:ZaAccount.A2_mbxsize,
                        label:ZaMsg.LBL_quota,
                        getDisplayValue:function() {
                            var usedVal = this.getInstanceValue();
                            var formatter = AjxNumberFormat.getNumberInstance();
                            if(!usedVal)
                                usedVal = "0";
                            else {
                                usedVal = Number(usedVal / 1048576).toFixed(3);
                                usedVal = formatter.format(usedVal);
                            }

                            var quotaLimit = this.getInstanceValue(ZaAccount.A_zmailMailQuota);
                            if(!quotaLimit || quotaLimit == "0") {
                                quotaLimit = ZaMsg.Unlimited;
                            } else {
                                quotaLimit = formatter.format(quotaLimit);
                            }

                            if(quotaLimit == ZaMsg.Unlimited) {
                                return AjxMessageFormat.format (ZaMsg.unlimitedQuotaValueTemplate,[usedVal,quotaLimit]);
                            } else {
                                return AjxMessageFormat.format (ZaMsg.quotaValueTemplate,[usedVal,quotaLimit]);
                            }
                        },
                        valueChangeEventSources:[ZaAccount.A_zmailMailQuota,ZaAccount.A2_mbxsize]
                    });
        } else if(ZaItem.hasReadPermission(ZaAccount.A_zmailMailQuota,entry)) {
                //assigned quota
            headerItems.push ({type:_OUTPUT_,ref:ZaAccount.A_zmailMailQuota, label:ZaMsg.LBL_assignedQuota,
                getDisplayValue:function() {
                    var val = this.getInstanceValue();
                    if(!val || val == "0")
                        val = ZaMsg.Unlimited;

                    if(val == ZaMsg.Unlimited) {
                        return AjxMessageFormat.format (ZaMsg.unlimitedAssignedQuotaValueTemplate,[val]);
                    } else {
                        return AjxMessageFormat.format (ZaMsg.assignedQuotaTemplate,[val]);
                    }
                },
                bmolsnr:true
            });
        } else if(ZaItem.hasRight(ZaAccount.GET_MAILBOX_INFO_RIGHT,entry)) {
            headerItems.push({type:_OUTPUT_,ref:ZaAccount.A2_mbxsize, label:ZaMsg.LBL_usedQuota,
                getDisplayValue:function() {
                    var val = this.getInstanceValue();
                    if(!val)
                        val = "0";
                    else {
                        val = Number(val / 1048576).toFixed(3);
                    }
                    return AjxMessageFormat.format (ZaMsg.usedQuotaTemplate,[val]);
                },
                bmolsnr:true
            });
        }
    }

	if (ZaItem.hasReadPermission(ZaAccount.A_zmailLastLogonTimestamp, entry))	{			
	    headerItems.push(
            {type:_OUTPUT_, ref:ZaAccount.A_zmailLastLogonTimestamp,
                label:ZaMsg.LBL_Last_Login, labelLocation:_LEFT_,
                getDisplayValue:function() {
                    var val = this.getInstanceValue();
                    return ZaAccount.getLastLoginTime(val) ;
                }
             });
    }
					 
    this.tabChoices = new Array();
	var _tab1, _tab2, _tab3, _tab4, _tab5, _tab6, _tab7, _tab8, _tab9, _tab10, _tab11;
	this.helpMap = {};
	_tab1 = ++this.TAB_INDEX;
	this.tabChoices.push({value:_tab1, label:ZaMsg.TABT_GeneralPage});
	this.helpMap[_tab1] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/user_interface_features.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");	
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.CONTACT_TAB_ATTRS, ZaAccountXFormView.CONTACT_TAB_RIGHTS)) {
		_tab2 = ++this.TAB_INDEX;
		this.tabChoices.push({value:_tab2, label:ZaMsg.TABT_ContactInfo});	
		this.helpMap[_tab2] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/user_interface_features.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
	}
		
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.MEMBEROF_TAB_ATTRS, ZaAccountXFormView.MEMBEROF_TAB_RIGHTS)) {
		_tab3 = ++this.TAB_INDEX;
		this.tabChoices.push({value:_tab3, label:ZaMsg.TABT_MemberOf});	
		this.helpMap[_tab3] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/adding_distribution_lists_to_an_account.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
	}

	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.FEATURE_TAB_ATTRS, ZaAccountXFormView.FEATURE_TAB_RIGHTS)) {
		_tab4 = ++this.TAB_INDEX;
		this.tabChoices.push({value:_tab4, label:ZaMsg.TABT_Features});	
		this.helpMap[_tab4] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/user_interface_features.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
	}
	
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.PREFERENCES_TAB_ATTRS, ZaAccountXFormView.PREFERENCES_TAB_RIGHTS)) {
		_tab5 = ++this.TAB_INDEX;
		this.tabChoices.push({value:_tab5, label:ZaMsg.TABT_Preferences});	
		this.helpMap[_tab5] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/preferences.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
	}
	
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.ALIASES_TAB_ATTRS, ZaAccountXFormView.ALIASES_TAB_RIGHTS)) {
		_tab6 = ++this.TAB_INDEX;
		this.tabChoices.push({value:_tab6, label:ZaMsg.TABT_Aliases});	
		this.helpMap[_tab6] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/user_interface_features.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
	}
			
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.FORWARDING_TAB_ATTRS, ZaAccountXFormView.FORWARDING_TAB_RIGHTS)) {
		_tab7 = ++this.TAB_INDEX;
		this.tabChoices.push({value:_tab7, label:ZaMsg.TABT_Forwarding});	
		this.helpMap[_tab7] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/forwarding_mail.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
	}
				
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.INTEROP_TAB_ATTRS, ZaAccountXFormView.INTEROP_TAB_RIGHTS)) {
		_tab8 = ++this.TAB_INDEX;
		this.tabChoices.push({value: _tab8, label: ZaMsg.TABT_Interop}) ;
		this.helpMap[_tab8] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/manage_account_free_busy_email_address.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
	}
		
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.SKIN_TAB_ATTRS, ZaAccountXFormView.SKIN_TAB_RIGHTS)) {
		_tab9 = ++this.TAB_INDEX;
		this.tabChoices.push({value:_tab9, label:ZaMsg.TABT_Themes});	
		this.helpMap[_tab9] = [location.pathname, ZaUtil.HELP_URL, "ui_themes/defining_theme_for_the_zmail_web_client_ui.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
	}
	 			
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.ZIMLET_TAB_ATTRS, ZaAccountXFormView.ZIMLET_TAB_RIGHTS)) {
		_tab10 = ++this.TAB_INDEX;
		this.tabChoices.push({value:_tab10, label:ZaMsg.TABT_Zimlets});	
		this.helpMap[_tab10] = [location.pathname, ZaUtil.HELP_URL, "zimlets/about_zimlets.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
	}
		
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.ADVANCED_TAB_ATTRS, ZaAccountXFormView.ADVANCED_TAB_RIGHTS)) {
		_tab11 = ++this.TAB_INDEX;
		this.tabChoices.push({value:_tab11, label:ZaMsg.TABT_Advanced});	
		this.helpMap[_tab11] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/account_advanced_features.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
	}

	var cases = [];

		var case1 = {type:_ZATABCASE_,caseKey:_tab1,
            paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,
			numCols:1};
		
		var case1Items = [
			 {type: _DWT_ALERT_, ref: ZaAccount.A2_domainLeftAccounts,
			 	visibilityChecks:[ZaAccountXFormView.isDomainLeftAccountsAlertVisible],
			 	visibilityChangeEventSources:[ZaAccount.A2_domainLeftAccounts,ZaAccount.A_name, ZaAccount.A2_accountTypes],
				containerCssStyle: "width:400px;",
				bmolsnr:true,
				style: DwtAlert.WARNING, iconVisible: false
			 },
	
	        //account types group
	        {type:_TOP_GROUPER_, label:ZaMsg.NAD_AccountTypeGrouper, id:"account_type_group",
	                colSpan: "*", numCols: 1, colSizes: ["100%"],
	                visibilityChecks:[ZaAccountXFormView.isAccountTypeGrouperVisible, ZaAccount.isShowAccountType],
	                visibilityChangeEventSources:[ZaAccount.A2_accountTypes,ZaAccount.A_COSId, ZaAccount.A_name, ZaAccount.A2_showAccountTypeMsg],
	                items: [
	                    {type: _DWT_ALERT_,
	                    	visibilityChecks:[ZaAccountXFormView.isAccountTypeSet, ZaAccountXFormView.isAccountsTypeAlertInvisible],
	                    	visibilityChangeEventSources:[ZaAccount.A2_accountTypes,ZaAccount.A_COSId, ZaAccount.A_name,ZaAccount.A2_showAccountTypeMsg],
	                    	containerCssStyle: "width:400px;",
	                        style: DwtAlert.CRITICAL, iconVisible: false ,
	                        content: ZaMsg.ERROR_ACCOUNT_TYPE_NOT_SET
	                    },
                    	    {type: _DWT_ALERT_, ref: ZaAccount.A2_showAccountTypeMsg,
                        	visibilityChecks:[[XForm.checkInstanceValueNotEmty,ZaAccount.A2_showAccountTypeMsg]],
                        	visibilityChangeEventSources:[ZaAccount.A2_showAccountTypeMsg, ZaAccount.A_name],
                        	bmolsnr:true,
                        	containerCssStyle: "width:400px;",
                        	style: DwtAlert.WARNING, iconVisible: false
                            },
	                    { type: _OUTPUT_, id: ZaAccountXFormView.accountTypeItemId ,
	                        getDisplayValue: ZaAccount.getAccountTypeOutput,
	                        //center the elements
	                        cssStyle: "margin-left:auto;margin-right:auto;width:600px;"
	                    }
	               ]
	        },
	
	        {type:_TOP_GROUPER_, label:ZaMsg.NAD_AccountNameGrouper, id:"account_form_name_group",
				colSizes:["275px","*"],numCols:2,
				visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        			ZaAccountXFormView.ACCOUNT_NAME_GROUP_ATTRS]],				
				items:ZaAccountXFormView.getAccountNameInfoItem()

			}
		];
	
		var setupGroup = {type:_TOP_GROUPER_, label:ZaMsg.NAD_AccountSetupGrouper, id:"account_form_setup_group",
			colSizes:["275px","*"],numCols:2,
			items: [
				{ref:ZaAccount.A_accountStatus, type:_OSELECT1_, msgName:ZaMsg.NAD_AccountStatus,
					label:ZaMsg.NAD_AccountStatus, bmolsnr:true,
					labelLocation:_LEFT_, choices:this.accountStatusChoices
				}
			],
			visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        			[ZaAccount.A_accountStatus,
                        			ZaAccount.A_COSId,
                        			ZaAccount.A_zmailIsAdminAccount]]]			
		}
			

		setupGroup.items.push(
			{type:_GROUP_, numCols:3,colSizes:["156px","22px","100px"], nowrap:true, label:ZaMsg.NAD_ClassOfService, labelLocation:_LEFT_,
				visibilityChecks:[[ZaItem.hasReadPermission,ZaAccount.A_COSId]], attributeName: ZaAccount.A_COSId,
				id: ZaAccountXFormView.cosGroupItemId,
				items: [
					{ref:ZaAccount.A_COSId, type:_DYNSELECT_,label: null, choices:this.cosChoices,
						//inputPreProcessor:ZaAccountXFormView.preProcessCOS,
						enableDisableChecks:[ [XForm.checkInstanceValue,ZaAccount.A2_autoCos,"FALSE"],
                            [ZaItem.hasWritePermission,ZaAccount.A_COSId]],
						enableDisableChangeEventSources:[ZaAccount.A2_autoCos],
						visibilityChecks:[],
						bmolsnr:false,
						width:"auto",
						toolTipContent:ZaMsg.tt_StartTypingCOSName,
						dataFetcherMethod:ZaSearch.prototype.dynSelectSearchCoses,
						onChange:ZaAccount.setCosChanged,
						emptyText:ZaMsg.enterSearchTerm,
						dataFetcherClass:ZaSearch,editable:true,getDisplayValue:function(newValue) {
								if(ZaItem.ID_PATTERN.test(newValue)) {
									var cos = ZaCos.getCosById(newValue);
									if(cos)
										newValue = cos.name;
								} 
								if (newValue == null) {
									newValue = "";
								} else {
									newValue = "" + newValue;
								}
								return newValue;
							}
					},
					{ref:ZaAccount.A2_autoCos, type:_CHECKBOX_,
						visibilityChecks:[], subLabel:"",
                        enableDisableChecks:[ [ZaItem.hasWritePermission,ZaAccount.A_COSId]],
						msgName:ZaMsg.NAD_Auto,label:ZaMsg.NAD_Auto,labelLocation:_RIGHT_,
						trueValue:"TRUE", falseValue:"FALSE" , helpTooltip: false,
						elementChanged: function(elementValue,instanceValue, event) {
							this.getForm().parent.setDirty(true);
							if(elementValue=="TRUE") {
								var defaultCos = ZaCos.getDefaultCos4Account(this.getInstance()[ZaAccount.A_name]);
								if(defaultCos && defaultCos.id) {
									this.getInstance()._defaultValues = defaultCos;
									this.getModel().setInstanceValue(this.getInstance(),ZaAccount.A_COSId,defaultCos.id);
									//instance.attrs[ZaAccount.A_COSId] = defaultCos.id;	
								}									
								//ZaAccount.setDefaultCos(this.getInstance());	
							}
							this.getForm().itemChanged(this, elementValue, event);
						}
					}
				]
			});

	
	setupGroup.items.push({ref:ZaAccount.A_zmailIsAdminAccount, type:_CHECKBOX_,
            msgName:ZaMsg.NAD_IsSystemAdminAccount,label:ZaMsg.NAD_IsSystemAdminAccount,
			bmolsnr:true, trueValue:"TRUE", falseValue:"FALSE",
			visibilityChecks:[[XForm.checkInstanceValueNot,ZaAccount.A_zmailIsExternalVirtualAccount,"TRUE"],[ZaItem.hasReadPermission,ZaAccount.A_zmailIsAdminAccount]],
			visibilityChangeEventSources:[ZaAccount.A_zmailIsExternalVirtualAccount,ZaAccount.A_zmailIsAdminAccount]
	});
	case1Items.push(setupGroup);
	
	var passwordGroup = {type:_TOP_GROUPER_, label:ZaMsg.NAD_PasswordGrouper,id:"account_form_password_group",
		visibilityChecks:[[ZaItem.hasAnyRight,[ZaAccount.SET_PASSWORD_RIGHT, ZaAccount.CHANGE_PASSWORD_RIGHT]],
                          [XForm.checkInstanceValueNot,ZaAccount.A2_isExternalAuth,true]
            ],
        visibilityChangeEventSources:[ZaAccount.A2_isExternalAuth],
		colSizes:["275px","*"],numCols:2,
		items:[ 
                { type: _DWT_ALERT_, containerCssStyle: "padding-bottom:0;",
                      //style: DwtAlert.INFO,iconVisible: (!ZaAccountXFormView.isAuthfromInternal(entry.name)),
                      //content: ((ZaAccountXFormView.isAuthfromInternal(entry.name))?ZaMsg.Alert_InternalPassword:ZaMsg.Alert_ExternalPassword)
                      style: DwtAlert.INFO,iconVisible: false,
                      content: ZaMsg.Alert_InternalPassword

                },
		{ref:ZaAccount.A_password, type:_SECRET_, msgName:ZaMsg.NAD_Password,
			label:ZaMsg.NAD_Password, labelLocation:_LEFT_,
			visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]], 
			cssClass:"admin_xform_name_input"
		},
		{ref:ZaAccount.A2_confirmPassword, type:_SECRET_, msgName:ZaMsg.NAD_ConfirmPassword,
			label:ZaMsg.NAD_ConfirmPassword, labelLocation:_LEFT_,
			visibilityChecks:[], enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]],
			cssClass:"admin_xform_name_input"
		},
		{ref:ZaAccount.A_zmailPasswordMustChange,  type:_CHECKBOX_,
			msgName:ZaMsg.NAD_MustChangePwd,label:ZaMsg.NAD_MustChangePwd,
			trueValue:"TRUE", falseValue:"FALSE",
			visibilityChecks:[], enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
		}
		]
	};
	case1Items.push(passwordGroup);

    var externalAuthGroup = {type:_TOP_GROUPER_, label:ZaMsg.NAD_ExternalAuthGrouper,id:"account_form_ext_auth_group",
        visibilityChecks:[
            [XForm.checkInstanceValue,ZaAccount.A2_isExternalAuth,true],
            [ZaItem.hasReadPermission,ZaAccount.A_zmailAuthLdapExternalDn]
        ],
        visibilityChangeEventSources:[ZaAccount.A2_isExternalAuth],
        colSizes:["275px","*"],numCols:2,
        items:[
            {ref:ZaAccount.A_zmailAuthLdapExternalDn,type:_TEXTFIELD_,width:256,
                msgName:ZaMsg.NAD_AuthLdapExternalDn,label:ZaMsg.NAD_AuthLdapExternalDn, labelLocation:_LEFT_,
                align:_LEFT_, toolTipContent: ZaMsg.tt_AuthLdapExternalDn
            }
        ]
    };
    case1Items.push(externalAuthGroup);

	var notesGroup = {type:_TOP_GROUPER_, label:ZaMsg.NAD_NotesGrouper, id:"account_form_notes_group",
		colSizes:["275px","*"],numCols:2,
		visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, [ZaAccount.A_notes, ZaAccount.A_description]]],
	 	items:[
	        ZaItem.descriptionXFormItem,
			{ref:ZaAccount.A_notes, type:_TEXTAREA_, msgName:ZaMsg.NAD_Notes,
				label:ZaMsg.NAD_Notes, labelLocation:_LEFT_, labelCssStyle:"vertical-align:top;", width:"30em"
			}
		]
	};

	case1Items.push(notesGroup);
	case1.items = case1Items;
	cases.push(case1);

	if(_tab2) {
		var case2={type:_ZATABCASE_, numCols:1, caseKey:_tab2,
            paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,  align:_CENTER_,
					items: [
						{type:_ZA_TOP_GROUPER_, label:ZaMsg.LBL_phone, id:"contact_form_phone_group",
							width:"100%", numCols:2,colSizes: ["275px","100%"],
							items:[
								{ref:ZaAccount.A_telephoneNumber, type:_TEXTFIELD_, msgName:ZaMsg.NAD_telephoneNumber,label:ZaMsg.NAD_telephoneNumber,
                                 labelLocation:_LEFT_, width:250} ,
                                {ref:ZaAccount.A_homePhone, type:_TEXTFIELD_, msgName:ZaMsg.NAD_homePhone,label:ZaMsg.NAD_homePhone,
                                 labelLocation:_LEFT_, width:250} ,
                                {ref:ZaAccount.A_mobile, type:_TEXTFIELD_, msgName:ZaMsg.NAD_mobile,label:ZaMsg.NAD_mobile,
                                 labelLocation:_LEFT_, width:250} ,
                                {ref:ZaAccount.A_pager, type:_TEXTFIELD_, msgName:ZaMsg.NAD_pager,label:ZaMsg.NAD_pager,
                                 labelLocation:_LEFT_, width:250},
                                 {ref:ZaAccount.A_facsimileTelephoneNumber, type:_TEXTFIELD_, msgName:ZaMsg.NAD_facsimileTelephoneNumber,
                                 label:ZaMsg.NAD_facsimileTelephoneNumber, labelLocation:_LEFT_, width:250}

							]
						},
						{type:_ZA_TOP_GROUPER_, label:ZaMsg.LBL_company, id:"contact_form_company_group",
							width:"100%", numCols:2,colSizes: ["275px","100%"],
							items:[	
								{ref:ZaAccount.A_zmailPhoneticCompany, type:_TEXTFIELD_, msgName:ZaMsg.NAD_zmailPhoneticCompany,
                                 label:ZaMsg.NAD_zmailPhoneticCompany, labelLocation:_LEFT_, width:250, visibilityChecks:[[ZaZmailAdmin.isLanguage, "ja"]]},
								{ref:ZaAccount.A_company, type:_TEXTFIELD_, msgName:ZaMsg.NAD_company,label:ZaMsg.NAD_company, labelLocation:_LEFT_,
                                 width:250} ,
                                {ref:ZaAccount.A_title,  type:_TEXTFIELD_, msgName:ZaMsg.NAD_title,label:ZaMsg.NAD_title, labelLocation:_LEFT_,
                                 width:250}
							]
						},
						{type:_ZA_TOP_GROUPER_, label:ZaMsg.LBL_address, id:"contact_form_address_group",
							width:"100%", numCols:2,colSizes: ["275px","100%"],
							items: ZaAccountXFormView.getAddressFormItem() 
						}							
					]
				};
		cases.push(case2);
	}
	var directMemberOfHeaderList = new ZaAccountMemberOfsourceHeaderList(ZaAccountMemberOfsourceHeaderList.DIRECT);
	var indirectMemberOfHeaderList = new ZaAccountMemberOfsourceHeaderList(ZaAccountMemberOfsourceHeaderList.INDIRECT);
	var nonMemberOfHeaderList = new ZaAccountMemberOfsourceHeaderList(ZaAccountMemberOfsourceHeaderList.NON);
	
	if(_tab3) {
		//MemberOf Tab
		var case3={type:_ZATABCASE_, numCols:2, caseKey:_tab3, colSizes: ["390px","390px"],
            paddingStyle:"padding-left:15px;", cellpadding:2,
					items: [
						{type:_SPACER_, height:"10"},
						//layout rapper around the direct/indrect list						
						{type: _GROUP_, width: "98%", numCols: 1, //colSizes: ["auto", 20],
							items: [
								//direct member group
								{type:_ZALEFT_GROUPER_, numCols:1, width: "100%", 
									label:ZaMsg.Account_DirectGroupLabel,
									containerCssStyle: "padding-top:5px;",
									items:[
										{ref: ZaAccount.A2_directMemberList, type: _S_DWT_LIST_, width: "98%", height: 200,
											cssClass: "DLSource", widgetClass: ZaAccountMemberOfListView, 
											headerList: directMemberOfHeaderList, defaultColumnSortable: 0,
                                            onSelection:ZaAccountXFormView.directMemberOfSelectionListener,
                                            forceUpdate: true }	,
										{type:_SPACER_, height:"5"},
										{type:_GROUP_, width:"100%", numCols:8, colSizes:[85,5, 85,"100%",80,5,80,5], 
											items:[
												{type:_DWT_BUTTON_, label:ZaMsg.DLXV_ButtonRemoveAll, width:80, 
													enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableAllButton,ZaAccount.A2_directMemberList]],
													enableDisableChangeEventSources:[ZaAccount.A2_directMemberList],
												   	onActivate:"ZaAccountMemberOfListView.removeAllGroups.call(this,event, ZaAccount.A2_directMemberList)"
												},
												{type:_CELLSPACER_, height:"100%"},
												{type:_DWT_BUTTON_, label:ZaMsg.DLXV_ButtonRemove, width:80, id:"removeButton",
													enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableAddRemoveButton,ZaAccount.A2_directMemberList]],
											      	enableDisableChangeEventSources:[ZaAccount.A2_directMemberListSelected],
											      	onActivate:"ZaAccountMemberOfListView.removeGroups.call(this,event, ZaAccount.A2_directMemberList)"
											    },
												{type:_CELLSPACER_,height:"100%"},
												{type:_DWT_BUTTON_, label:ZaMsg.Previous, width:75, id:"backButton", icon:"LeftArrow", disIcon:"LeftArrowDis", 	
													onActivate:"ZaAccountMemberOfListView.backButtonHndlr.call(this,event, ZaAccount.A2_directMemberList)", 
													enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableBackButton,ZaAccount.A2_directMemberList]],
											      	enableDisableChangeEventSources:[ZaAccount.A2_directMemberList +"_offset"]
											    },								       
												{type:_CELLSPACER_, height:"100%"},
												{type:_DWT_BUTTON_, label:ZaMsg.Next, width:75, id:"fwdButton", icon:"RightArrow", disIcon:"RightArrowDis",	
													onActivate:"ZaAccountMemberOfListView.fwdButtonHndlr.call(this,event, ZaAccount.A2_directMemberList)", 
													enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableForwardButton,ZaAccount.A2_directMemberList]],
											      	enableDisableChangeEventSources:[ZaAccount.A2_directMemberList + "_more"]
											    },								       
												{type:_CELLSPACER_, height:"100%"}									
											]
										}		
									]
								},	
								{type:_SPACER_, height:"10"},	
								//indirect member group
								{type:_ZALEFT_GROUPER_, numCols:1,  width: "100%", label:ZaMsg.Account_IndirectGroupLabel,
									containerCssStyle: "padding-top:5px;",
									items:[
										{ref: ZaAccount.A2_indirectMemberList, type: _S_DWT_LIST_, width: "98%", height: 200,
											cssClass: "DLSource", widgetClass: ZaAccountMemberOfListView, 
											headerList: indirectMemberOfHeaderList, defaultColumnSortable: 0,
                                            onSelection:ZaAccountXFormView.indirectMemberOfSelectionListener,
                                            forceUpdate: true }	,
										{type:_SPACER_, height:"5"},
										{type:_GROUP_, width:"100%", numCols:8, colSizes:[85,5, 85,"100%",80,5,80,5], 
											items:[
												{type:_CELLSPACER_, height:"100%"},
												{type:_CELLSPACER_, height:"100%"},
												{type:_CELLSPACER_, height:"100%"},
												{type:_CELLSPACER_, height:"100%"},
												{type:_DWT_BUTTON_, label:ZaMsg.Previous, width:75, id:"backButton", icon:"LeftArrow", disIcon:"LeftArrowDis", 	
													onActivate:"ZaAccountMemberOfListView.backButtonHndlr.call(this,event, ZaAccount.A2_indirectMemberList)", 
													enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableBackButton,ZaAccount.A2_indirectMemberList]],
											      	enableDisableChangeEventSources:[ZaAccount.A2_indirectMemberList + "_offset"]
											    },								       
												{type:_CELLSPACER_, height:"100%"},
												{type:_DWT_BUTTON_, label:ZaMsg.Next, width:75, id:"fwdButton", icon:"RightArrow", disIcon:"RightArrowDis",	
													onActivate:"ZaAccountMemberOfListView.fwdButtonHndlr.call(this,event, ZaAccount.A2_indirectMemberList)", 
													enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableForwardButton,ZaAccount.A2_indirectMemberList]],
											      	enableDisableChangeEventSources:[ZaAccount.A2_indirectMemberList + "_more"]
											    },								       
												{type:_CELLSPACER_, height:"100%"}									
											]
										}
									]
								}
							]
						},

						//non member group
						{type:_ZARIGHT_GROUPER_, numCols:1, width: "100%", label:ZaMsg.Account_NonGroupLabel,
							containerCssStyle: "padding-top:5px;",
							items:[
								{type:_GROUP_, numCols:5, colSizes:[55, "auto",10,80, 120,20], width:"100%", 
								   items:[
								   		{type:_OUTPUT_, value:ZaMsg.DLXV_LabelFind, nowrap:true},
										{ref:"query", type:_TEXTFIELD_, width:"100%", label:null,
									      elementChanged: function(elementValue,instanceValue, event) {
											  var charCode = event.charCode;
											  if (charCode == 13 || charCode == 3) {
											      ZaAccountMemberOfListView.prototype.srchButtonHndlr.call(this);
											  } else {
											      this.getForm().itemChanged(this, elementValue, event);
											  }
								      		},
								      		visibilityChecks:[],
								      		enableDisableChecks:[]
										},
										{type:_CELLSPACER_},
										{type:_DWT_BUTTON_, label:ZaMsg.DLXV_ButtonSearch, width:80,
										   onActivate:ZaAccountMemberOfListView.prototype.srchButtonHndlr
										},
										{ref: ZaAccount.A2_showSameDomain, type: _CHECKBOX_, align:_RIGHT_, msgName:ZaMsg.NAD_SearchSameDomain,
												label:AjxMessageFormat.format (ZaMsg.NAD_SearchSameDomain),
                                                subLabel:"",
												labelCssClass:"xform_label",
												labelLocation:_LEFT_, trueValue:"TRUE", falseValue:"FALSE",
												enableDisableChecks:[],
												visibilityChecks:[]
										}										
									]
						         },
						        {type:_SPACER_, height:"5"},
								
								{ref: ZaAccount.A2_nonMemberList, type: _S_DWT_LIST_, width: "98%", height: 455,
									cssClass: "DLSource", widgetClass: ZaAccountMemberOfListView, 
									headerList: nonMemberOfHeaderList, defaultColumnSortable: 0,
                                    onSelection:ZaAccountXFormView.nonMemberOfSelectionListener,
                                    forceUpdate: true },
									
								{type:_SPACER_, height:"5"},	
								//add action buttons
								{type:_GROUP_, width:"100%", numCols:8, colSizes:[85,5, 85,"100%",80,5,80,5],
									items: [
									   	{type:_DWT_BUTTON_, label:ZaMsg.DLXV_ButtonAddFromList, width:80,
											enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableAddRemoveButton,ZaAccount.A2_nonMemberList]],
											enableDisableChangeEventSources:[ZaAccount.A2_nonMemberListSelected],
											onActivate:"ZaAccountMemberOfListView.addGroups.call(this,event, ZaAccount.A2_nonMemberList)"
										},
									   	{type:_CELLSPACER_, height:"100%"},
									   	{type:_DWT_BUTTON_, label:ZaMsg.DLXV_ButtonAddAll, width:80,
											enableDisableChangeEventSources:[ZaAccount.A2_nonMemberList],
											enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableAllButton,ZaAccount.A2_nonMemberList]],
											onActivate:"ZaAccountMemberOfListView.addAllGroups.call(this,event, ZaAccount.A2_nonMemberList)"
										},
										{type:_CELLSPACER_, height:"100%"},
										{type:_DWT_BUTTON_, label:ZaMsg.Previous, width:75, id:"backButton", icon:"LeftArrow", disIcon:"LeftArrowDis",
											enableDisableChangeEventSources:[ZaAccount.A2_nonMemberList + "_offset"],
											enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableBackButton,ZaAccount.A2_nonMemberList]],
											onActivate:"ZaAccountMemberOfListView.backButtonHndlr.call(this,event, ZaAccount.A2_nonMemberList)"
										},								       
										{type:_CELLSPACER_, height:"100%"},
										{type:_DWT_BUTTON_, label:ZaMsg.Next, width:75, id:"fwdButton", icon:"RightArrow", disIcon:"RightArrowDis",
										 	enableDisableChangeEventSources:[ZaAccount.A2_nonMemberList + "_more"],
											enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableForwardButton,ZaAccount.A2_nonMemberList]],
											onActivate:"ZaAccountMemberOfListView.fwdButtonHndlr.call(this,event, ZaAccount.A2_nonMemberList)"									
										},								       
										{type:_CELLSPACER_, height:"100%"}	
									  ]
							    }								
							]
						},
						{type: _GROUP_, width: "100%", items: [
								{type:_CELLSPACER_}
							]
						}
					]
				};
				
		cases.push(case3);		
	}				
	if(_tab4) {
		cases.push({type:_ZATABCASE_,id:"account_form_features_tab",  numCols:1, width:"100%", caseKey:_tab4,
        paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,
				items: [
					{ type: _DWT_ALERT_,
					  containerCssStyle: "padding-top:20px;width:400px;",
					  style: DwtAlert.INFO,
					  iconVisible: false, 
					  content: ZaMsg.NAD_CheckFeaturesInfo
					},				
					{type:_ZA_TOP_GROUPER_, label: ZaMsg.NAD_zmailMajorFeature, id:"account_form_features_major", colSizes:["auto"],numCols:1,
						visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
							[ZaAccount.A_zmailFeatureMailEnabled, 
							 ZaAccount.A_zmailFeatureContactsEnabled,
							 ZaAccount.A_zmailFeatureCalendarEnabled,
							 ZaAccount.A_zmailFeatureTasksEnabled,
							 ZaAccount.A_zmailFeatureBriefcasesEnabled,
							 ZaAccount.A_zmailFeatureOptionsEnabled
							 ]]
						],
						items:[	
							{ref:ZaAccount.A_zmailFeatureMailEnabled,
								type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureMailEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureMailEnabled, 
								trueValue:"TRUE", falseValue:"FALSE"
							},							
							{ref:ZaAccount.A_zmailFeatureContactsEnabled,
								type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureContactsEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureContactsEnabled, 
								trueValue:"TRUE", falseValue:"FALSE"},							
							{ref:ZaAccount.A_zmailFeatureCalendarEnabled,
								type:_SUPER_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureCalendarEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureCalendarEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"},		
							{ref:ZaAccount.A_zmailFeatureTasksEnabled,
								type:_SUPER_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureTaskEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureTaskEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"},													
							//{ref:ZaAccount.A_zmailFeatureNotebookEnabled, type:_SUPER_CHECKBOX_,
							//	resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
							//	msgName:ZaMsg.LBL_zmailFeatureNotebookEnabled,
							//	checkBoxLabel:ZaMsg.LBL_zmailFeatureNotebookEnabled,  
							//	trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureBriefcasesEnabled, type:_SUPER_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureBriefcasesEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureBriefcasesEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"},								
							//{ref:ZaAccount.A_zmailFeatureIMEnabled, type:_SUPER_CHECKBOX_, 
							//	resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
							//	msgName:ZaMsg.LBL_zmailFeatureIMEnavbled,
							//	checkBoxLabel:ZaMsg.LBL_zmailFeatureIMEnabled,  
							//	trueValue:"TRUE", falseValue:"FALSE"},								
							{ref:ZaAccount.A_zmailFeatureOptionsEnabled, type:_SUPER_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureOptionsEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureOptionsEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"}
						]
					},	
					{type:_ZA_TOP_GROUPER_, label: ZaMsg.NAD_zmailGeneralFeature, id:"account_form_features_general", colSizes:["auto"],numCols:1,
						visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
							[ZaAccount.A_zmailFeatureTaggingEnabled, 
							 ZaAccount.A_zmailFeatureSharingEnabled,
							 ZaAccount.A_zmailFeatureChangePasswordEnabled,
							 ZaAccount.A_zmailFeatureSkinChangeEnabled,
							 ZaAccount.A_zmailFeatureManageZimlets,
							 ZaAccount.A_zmailFeatureHtmlComposeEnabled,
							 ZaAccount.A_zmailFeatureGalEnabled,
							 ZaAccount.A_zmailFeatureMAPIConnectorEnabled,
							 ZaAccount.A_zmailFeatureBriefcasesEnabled,
							 ZaAccount.A_zmailFeatureGalAutoCompleteEnabled,
							 ZaAccount.A_zmailFeatureImportFolderEnabled,
                             ZaAccount.A_zmailFeatureExportFolderEnabled,
							 ZaAccount.A_zmailDumpsterEnabled,
							 ZaAccount.A_zmailDumpsterPurgeEnabled
							 ]]
						],
						items:[							
							{ref:ZaAccount.A_zmailFeatureTaggingEnabled, type:_SUPER_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureTaggingEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureTaggingEnabled, 
								trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureSharingEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureSharingEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureSharingEnabled,trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailExternalSharingEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailExternalSharingEnabled,checkBoxLabel:ZaMsg.LBL_zmailExternalSharingEnabled,trueValue:"TRUE", falseValue:"FALSE",
								visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaAccount.A_zmailFeatureSharingEnabled, "TRUE"]], visibilityChangeEventSources:[ZaAccount.A_zmailFeatureSharingEnabled]
							},
							{ref:ZaAccount.A_zmailPublicSharingEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPublicSharingEnabled,checkBoxLabel:ZaMsg.LBL_zmailPublicSharingEnabled,trueValue:"TRUE", falseValue:"FALSE",
								visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaAccount.A_zmailFeatureSharingEnabled, "TRUE"]], visibilityChangeEventSources:[ZaAccount.A_zmailFeatureSharingEnabled]
							},
							{ref:ZaAccount.A_zmailFeatureChangePasswordEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureChangePasswordEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureChangePasswordEnabled,trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureSkinChangeEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureSkinChangeEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureSkinChangeEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureManageZimlets, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureManageZimlets,checkBoxLabel:ZaMsg.LBL_zmailFeatureManageZimlets, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureHtmlComposeEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureHtmlComposeEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureHtmlComposeEnabled, trueValue:"TRUE", falseValue:"FALSE"},														
							//{ref:ZaAccount.A_zmailFeatureShortcutAliasesEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureShortcutAliasesEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureShortcutAliasesEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureGalEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureGalEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureGalEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureMAPIConnectorEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureMAPIConnectorEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureMAPIConnectorEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureGalAutoCompleteEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureGalAutoCompleteEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureGalAutoCompleteEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureImportFolderEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureImportFolderEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureImportFolderEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                            {ref:ZaAccount.A_zmailFeatureExportFolderEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureExportFolderEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureExportFolderEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailDumpsterEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailDumpsterEnabled, checkBoxLabel:ZaMsg.LBL_zmailDumpsterEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailDumpsterPurgeEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailDumpsterPurgeEnabled, checkBoxLabel:ZaMsg.LBL_zmailDumpsterPurgeEnabled, trueValue:"TRUE", falseValue:"FALSE",
								visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaAccount.A_zmailDumpsterEnabled, "TRUE"]], visibilityChangeEventSources:[ZaAccount.A_zmailDumpsterEnabled]
							}

						]
					},	
					{type:_ZA_TOP_GROUPER_, label: ZaMsg.NAD_zmailMailFeature, id:"account_form_features_mail", colSizes:["auto"],numCols:1,
						visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
							[ZaAccount.A_zmailFeatureMailPriorityEnabled,
							 ZaAccount.A_zmailFeatureFlaggingEnabled,
							 ZaAccount.A_zmailImapEnabled,
							 ZaAccount.A_zmailPop3Enabled,
							 ZaAccount.A_zmailFeatureImapDataSourceEnabled,
							 ZaAccount.A_zmailFeaturePop3DataSourceEnabled,
							 ZaAccount.A_zmailFeatureMailSendLaterEnabled,
							 ZaAccount.A_zmailFeatureConversationsEnabled,
							 ZaAccount.A_zmailFeatureFiltersEnabled,
							 ZaAccount.A_zmailFeatureOutOfOfficeReplyEnabled,
							 ZaAccount.A_zmailFeatureNewMailNotificationEnabled,
							 ZaAccount.A_zmailFeatureMailPollingIntervalPreferenceEnabled,
							 ZaAccount.A_zmailFeatureIdentitiesEnabled,
							 ZaAccount.A_zmailFeatureReadReceiptsEnabled
							 ]]
						],						
						enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailFeatureMailEnabled,"TRUE"]],
						enableDisableChangeEventSources:[ZaAccount.A_zmailFeatureMailEnabled, ZaAccount.A_COSId],
						items:[													
							{ref:ZaAccount.A_zmailFeatureMailPriorityEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureMailPriorityEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureMailPriorityEnabled, trueValue:"TRUE", falseValue:"FALSE"}	,
							{ref:ZaAccount.A_zmailFeatureFlaggingEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureFlaggingEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureFlaggingEnabled, trueValue:"TRUE", falseValue:"FALSE"}	,
							{ref:ZaAccount.A_zmailImapEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailImapEnabled,checkBoxLabel:ZaMsg.LBL_zmailImapEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailPop3Enabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPop3Enabled,checkBoxLabel:ZaMsg.LBL_zmailPop3Enabled,  trueValue:"TRUE", falseValue:"FALSE"},		
							{ref:ZaAccount.A_zmailFeatureImapDataSourceEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailExternalImapEnabled,checkBoxLabel:ZaMsg.LBL_zmailExternalImapEnabled,  trueValue:"TRUE", falseValue:"FALSE"},									
							{ref:ZaAccount.A_zmailFeaturePop3DataSourceEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailExternalPop3Enabled,checkBoxLabel:ZaMsg.LBL_zmailExternalPop3Enabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureMailSendLaterEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureMailSendLaterEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureMailSendLaterEnabled,  trueValue:"TRUE", falseValue:"FALSE"},		
							{ref:ZaAccount.A_zmailFeatureConversationsEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureConversationsEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureConversationsEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureFiltersEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureFiltersEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureFiltersEnabled,trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureOutOfOfficeReplyEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureOutOfOfficeReplyEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureOutOfOfficeReplyEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureNewMailNotificationEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureNewMailNotificationEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureNewMailNotificationEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureMailPollingIntervalPreferenceEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureMailPollingIntervalPreferenceEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureMailPollingIntervalPreferenceEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureIdentitiesEnabled,
								type:_SUPER_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureIdentitiesEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureIdentitiesEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"
							},
							{ref:ZaAccount.A_zmailFeatureReadReceiptsEnabled,
								type:_SUPER_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								checkBoxLabel:ZaMsg.LBL_zmailFeatureReadReceiptsEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"
							}							
						]
					},
                    {
                        type: _ZA_TOP_GROUPER_,
                        label: ZaMsg.NAD_zmailContactFeature,
                        id: "account_form_features_contact",
                        enableDisableChecks: [
                            [
                                XForm.checkInstanceValue,
                                ZaAccount.A_zmailFeatureContactsEnabled,
                                "TRUE"
                            ]
                        ],
                        enableDisableChangeEventSources: [ZaAccount.A_zmailFeatureContactsEnabled, ZaAccount.A_COSId],
                        visibilityChecks: [
                            [
                                ZATopGrouper_XFormItem.isGroupVisible,
                                [
                                    ZaAccount.A_zmailFeatureDistributionListFolderEnabled
                                ]
                            ]
                        ],
                        items: [
                            {
                                ref: ZaAccount.A_zmailFeatureDistributionListFolderEnabled,
                                type: _SUPER_CHECKBOX_,
                                resetToSuperLabel: ZaMsg.NAD_ResetToCOS,
                                msgName: ZaMsg.MSG_zmailFeatureDistributionListFolderEnabled,
                                checkBoxLabel: ZaMsg.LBL_zmailFeatureDistributionListFolderEnabled,
                                trueValue: "TRUE",
                                falseValue: "FALSE"
                            }
                        ]
                    },
					{type:_ZA_TOP_GROUPER_, label: ZaMsg.NAD_zmailCalendarFeature, id:"account_form_features_calendar",colSizes:["auto"],numCols:1,
						visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
							[ZaAccount.A_zmailFeatureGroupCalendarEnabled,
							 //ZaAccount.A_zmailFeatureFreeBusyViewEnabled,
                             ZaAccount.A_zmailFeatureCalendarReminderDeviceEmailEnabled
							 ]]
						],						
						enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailFeatureCalendarEnabled,"TRUE"]],
						enableDisableChangeEventSources:[ZaAccount.A_zmailFeatureCalendarEnabled,ZaAccount.A_COSId],
						items:[						
							{ref:ZaAccount.A_zmailFeatureGroupCalendarEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureGroupCalendarEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureGroupCalendarEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							//{ref:ZaAccount.A_zmailFeatureFreeBusyViewEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureFreeBusyViewEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureFreeBusyViewEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                            {ref:ZaAccount.A_zmailFeatureCalendarReminderDeviceEmailEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureCalendarReminderDeviceEmailEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureCalendarReminderDeviceEmailEnabled,  trueValue:"TRUE", falseValue:"FALSE"}
						]
					},
				//	{type:_ZA_TOP_GROUPER_, label: ZaMsg.NAD_zmailIMFeature, id:"account_form_features_im", colSizes:["auto"],numCols:1,
				//		visibilityChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailFeatureIMEnabled,"TRUE"]],
				//		visibilityChangeEventSources:[ZaAccount.A_zmailFeatureIMEnabled,ZaAccount.A_COSId],
				//		visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
				//			[ZaAccount.A_zmailFeatureInstantNotify ]]
				//		],
				//		items:[	
				//			{ref:ZaAccount.A_zmailFeatureInstantNotify,
				//				 type:_SUPER_CHECKBOX_,
				//				 msgName:ZaMsg.LBL_zmailFeatureInstantNotify,
				//				 checkBoxLabel:ZaMsg.LBL_zmailFeatureInstantNotify,
				//				 trueValue:"TRUE",
				//				 falseValue:"FALSE",
				//				 resetToSuperLabel:ZaMsg.NAD_ResetToCOS
				//			}
				//		]
				//	},
					{type:_ZA_TOP_GROUPER_, label: ZaMsg.NAD_zmailSearchFeature, id:"account_form_features_search", colSizes:["auto"],numCols:1,
						visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
							[ZaAccount.A_zmailFeatureAdvancedSearchEnabled,
							ZaAccount.A_zmailFeatureSavedSearchesEnabled,
							ZaAccount.A_zmailFeatureInitialSearchPreferenceEnabled,
							ZaAccount.A_zmailFeaturePeopleSearchEnabled
							]]
						],
						items:[
							{ref:ZaAccount.A_zmailFeatureAdvancedSearchEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureAdvancedSearchEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureAdvancedSearchEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureSavedSearchesEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureSavedSearchesEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureSavedSearchesEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureInitialSearchPreferenceEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureInitialSearchPreferenceEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureInitialSearchPreferenceEnabled, trueValue:"TRUE", falseValue:"FALSE"},
						  {ref:ZaAccount.A_zmailFeaturePeopleSearchEnabled, type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeaturePeopleSearchEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeaturePeopleSearchEnabled, trueValue:"TRUE", falseValue:"FALSE"}
						]
					},
                                        {type:_ZA_TOP_GROUPER_, label: ZaMsg.NAD_zmailSMIMEFeature, id:"account_form_features_smime", colSizes:["auto"],numCols:1,
                                                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,             
                                      		[ ZaAccount.A_zmailFeatureSMIMEEnabled]]],
                                                items:[
                                                 {ref:ZaAccount.A_zmailFeatureSMIMEEnabled,
                                                        type:_SUPER_CHECKBOX_,
                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                                        msgName:ZaMsg.LBL_zmailFeatureSMIMEEnabled,
                                                        checkBoxLabel:ZaMsg.LBL_zmailFeatureSMIMEEnabled,
                                                        trueValue:"TRUE", falseValue:"FALSE"}
                                                ]
                                        }
				]
			});
	}
	if(_tab5) {
		var prefItems = [
						{type:_ZA_TOP_GROUPER_, id:"account_prefs_general",colSizes:["275px","auto"],numCols:2,
                            label: ZaMsg.NAD_GeneralOptions,
                            visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        			[ZaAccount.A_zmailPrefClientType,
                        			ZaAccount.A_zmailPrefMailInitialSearch,
                        			ZaAccount.A_zmailPrefShowSearchString,
                        			ZaAccount.A_zmailPrefImapSearchFoldersEnabled,
                        			ZaAccount.A_zmailPrefUseKeyboardShortcuts,
                        			ZaAccount.A_zmailPrefWarnOnExit,
                        			ZaAccount.A_zmailPrefAdminConsoleWarnOnExit,
                        			ZaAccount.A_zmailPrefShowSelectionCheckbox,
                        			ZaAccount.A_zmailJunkMessagesIndexingEnabled,
                        			ZaAccount.A_zmailPrefLocale
                        			]]
                        	],
							items :[
								{ref:ZaAccount.A_zmailPrefClientType,
									type:_SUPER_SELECT1_,
                                    colSpan:2,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPrefClientType,
									label:ZaMsg.LBL_zmailPrefClientType, 
									labelLocation:_LEFT_
								},
								{ref:ZaAccount.A_zmailPrefMailInitialSearch, type:_SUPER_TEXTFIELD_,
									msgName:ZaMsg.LBL_zmailPrefMailInitialSearch,
									txtBoxLabel:ZaMsg.LBL_zmailPrefMailInitialSearch,
									labelCssClass:"gridGroupBodyLabel",
									labelCssStyle:"border-right:1px solid;",
									labelLocation:_LEFT_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailPrefShowSearchString,
									type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefShowSearchString,checkBoxLabel:ZaMsg.LBL_zmailPrefShowSearchString,trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefImapSearchFoldersEnabled, 
									type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefImapSearchFoldersEnabled,
									checkBoxLabel:ZaMsg.LBL_zmailPrefImapSearchFoldersEnabled,  
									trueValue:"TRUE", falseValue:"FALSE"
								},
								{ref:ZaAccount.A_zmailPrefUseKeyboardShortcuts,
									type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS,checkBoxLabel:ZaMsg.LBL_zmailPrefUseKeyboardShortcuts, trueValue:"TRUE", falseValue:"FALSE"},
								
								{ref:ZaAccount.A_zmailPrefWarnOnExit, type:_SUPER_CHECKBOX_, nowrap:false,labelWrap:true,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,checkBoxLabel:ZaMsg.LBL_zmailPrefWarnOnExit,
									trueValue:"TRUE", falseValue:"FALSE"},
                                {ref:ZaAccount.A_zmailPrefAdminConsoleWarnOnExit, type:_SUPER_CHECKBOX_, nowrap:false,labelWrap:true,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,checkBoxLabel:ZabMsg.LBL_zmailPrefAdminConsoleWarnOnExit,
									trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefShowSelectionCheckbox, type:_SUPER_CHECKBOX_,
									labelWrap: true,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, checkBoxLabel:ZaMsg.LBL_zmailPrefShowSelectionCheckbox,
									trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailJunkMessagesIndexingEnabled, 
									type:_SUPER_CHECKBOX_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailJunkMessagesIndexingEnabled,
									checkBoxLabel:ZaMsg.LBL_zmailJunkMessagesIndexingEnabled, 
									trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefLocale, type:_SUPER_SELECT1_,
                                    colSpan:2,
                                    choices: ZaSettings.getLocaleChoices(),
                                    msgName:ZaMsg.LBL_zmailPrefLocale,label:ZaMsg.LBL_zmailPrefLocale,
									labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS}
                            ]                                                             
						},	
						{type:_ZA_TOP_GROUPER_, id:"account_prefs_standard_client",colSizes:["275px","auto"],numCols:2,
							label:ZaMsg.NAD_MailOptionsStandardClient,
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
								[
									ZaAccount.A_zmailMaxMailItemsPerPage,
									ZaAccount.A_zmailPrefMailItemsPerPage
								]]
							],
							items :[
								{ref:ZaAccount.A_zmailMaxMailItemsPerPage, 
									type:_SUPER_SELECT1_, 
									editable:true,
									inputSize:4,
									choices:[10,25,50,100,250,500,1000],
									msgName:ZaMsg.MSG_zmailMaxMailItemsPerPage,
									label:ZaMsg.LBL_zmailMaxMailItemsPerPage, labelLocation:_LEFT_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, valueLabel:null},									
								{ref:ZaAccount.A_zmailPrefMailItemsPerPage, 
									type:_SUPER_SELECT1_, 
									editable:false,
									msgName:ZaMsg.MSG_zmailPrefMailItemsPerPage,
									label:ZaMsg.LBL_zmailPrefMailItemsPerPage, labelLocation:_LEFT_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, valueLabel:null}							
							]
						},
						{type:_ZA_TOP_GROUPER_, id:"account_prefs_mail_general",
                            label: ZaMsg.NAD_MailOptions,
                            visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
								[
									ZaAccount.A_zmailPrefMessageViewHtmlPreferred,
									ZaAccount.A_zmailPrefDisplayExternalImages,
									ZaAccount.A_zmailPrefGroupMailBy,
									ZaAccount.A_zmailPrefMailDefaultCharset,
									ZaAccount.A_zmailPrefMailToasterEnabled,
                                    ZaAccount.A_zmailPrefMessageIdDedupingEnabled,
                                    ZaAccount.A_zmailPrefItemsPerVirtualPage,
								]]
							],
							items :[
								{ref:ZaAccount.A_zmailPrefMessageViewHtmlPreferred, 
									type:_SUPER_CHECKBOX_,  colSpan:2,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefMessageViewHtmlPreferred,
									checkBoxLabel:ZaMsg.LBL_zmailPrefMessageViewHtmlPreferred, 
									trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefDisplayExternalImages, 
									type:_SUPER_CHECKBOX_,  colSpan:2,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefDisplayExternalImages,
									checkBoxLabel:ZaMsg.LBL_zmailPrefDisplayExternalImages, 
									trueValue:"TRUE", falseValue:"FALSE"},	
								{ref:ZaAccount.A_zmailPrefGroupMailBy,
									type:_SUPER_SELECT1_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefGroupMailBy,
									label:ZaMsg.LBL_zmailPrefGroupMailBy, 
									labelLocation:_LEFT_},

								{ref:ZaAccount.A_zmailPrefMailDefaultCharset, type:_SUPER_SELECT1_,
									msgName:ZaMsg.LBL_zmailPrefMailDefaultCharset,label:ZaMsg.LBL_zmailPrefMailDefaultCharset,
									 labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS},
								{ref:ZaAccount.A_zmailPrefMailToasterEnabled,
                                     type:_SUPER_CHECKBOX_,  colSpan:2,
                                     resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                     msgName:ZaMsg.MSG_zmailPrefMailToasterEnabled,
                                     checkBoxLabel:ZaMsg.LBL_zmailPrefMailToasterEnabled,
                                     trueValue:"TRUE", falseValue:"FALSE"},
                                {ref:ZaAccount.A_zmailPrefMessageIdDedupingEnabled,
                                    type:_SUPER_CHECKBOX_,  colSpan:2,
                                    resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                    msgName:ZaMsg.MSG_zmailPrefMessageIdDedupingEnabled,
                                    checkBoxLabel:ZaMsg.LBL_zmailPrefMessageIdDedupingEnabled,
                                    trueValue:"TRUE", falseValue:"FALSE"},
                                {ref:ZaAccount.A_zmailPrefItemsPerVirtualPage, type:_SUPER_TEXTFIELD_,
                                     colSizes:["275px", "275px", "*"],colSpan:2,
				     msgName:ZaMsg.LBL_zmailPrefItemsPerVirtualPage,
                                     txtBoxLabel:ZaMsg.LBL_zmailPrefItemsPerVirtualPage, 
				     labelLocation:_LEFT_,
resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
textFieldCssClass:"admin_xform_number_input"}
							]
						},
						{type:_ZA_TOP_GROUPER_,colSizes:["275px","100%"], id:"account_prefs_mail_receiving", numCols: 2,
							label:ZaMsg.NAD_MailOptionsReceiving,
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
								[ZaAccount.A_zmailPrefMailPollingInterval, 
								ZaAccount.A_zmailMailMinPollingInterval,
								ZaAccount.A_zmailPrefMailSoundsEnabled,
								ZaAccount.A_zmailPrefMailFlashIcon,
								ZaAccount.A_zmailPrefMailFlashTitle,
								ZaAccount.A_zmailPrefNewMailNotificationEnabled,
								ZaAccount.A_zmailPrefNewMailNotificationAddress,
								ZaAccount.A_zmailPrefOutOfOfficeReplyEnabled,
								ZaAccount.A_zmailPrefOutOfOfficeCacheDuration,
								ZaAccount.A_zmailPrefOutOfOfficeReply,
								ZaAccount.A_zmailPrefMailSendReadReceipts,
								ZaAccount.A_zmailPrefReadReceiptsToAddress]]
							],							
							items :[

								{ref:ZaAccount.A_zmailPrefMailPollingInterval, type:_SUPER_SELECT1_, 
									labelCssClass:"gridGroupBodyLabel",
									msgName:ZaMsg.MSG_zmailPrefMailPollingInterval,
									label:ZaMsg.LBL_zmailPrefMailPollingInterval, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                    onChange:ZaAccountXFormView.validatePollingInterval,
									nowrap:false,labelWrap:true									
								},							
								{ref:ZaAccount.A_zmailMailMinPollingInterval,
                                    labelCssClass:"gridGroupBodyLabel",
									type:_SUPER_LIFETIME_,
									msgName:ZaMsg.MSG_zmailMailMinPollingInterval,
									txtBoxLabel:ZaMsg.LBL_zmailMailMinPollingInterval,
                                    onChange:ZaAccountXFormView.validatePollingInterval,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									colSpan:2
								},
                                {ref:ZaAccount.A_zmailPrefMailSoundsEnabled,
									type:_SUPER_CHECKBOX_, colSpan:2,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.LBL_playSound,
									checkBoxLabel:ZaMsg.LBL_playSound,
									trueValue:"TRUE", falseValue:"FALSE"
								},
                                {ref:ZaAccount.A_zmailPrefMailFlashIcon,
									type:_SUPER_CHECKBOX_,  colSpan:2,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.LBL_flashIcon,
									checkBoxLabel:ZaMsg.LBL_flashIcon,
									trueValue:"TRUE", falseValue:"FALSE"
								},
                                {ref:ZaAccount.A_zmailPrefMailFlashTitle,
									type:_SUPER_CHECKBOX_, colSpan:2,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.LBL_flashTitle,
									checkBoxLabel:ZaMsg.LBL_flashTitle,
									trueValue:"TRUE", falseValue:"FALSE"
								},         
								{ref:ZaAccount.A_zmailPrefNewMailNotificationEnabled, 
									type:_ZA_CHECKBOX_, 
									msgName:ZaMsg.LBL_zmailPrefNewMailNotificationEnabled,
									label:ZaMsg.LBL_zmailPrefNewMailNotificationEnabled,
									trueValue:"TRUE", falseValue:"FALSE"
								},
								{ref:ZaAccount.A_zmailPrefNewMailNotificationAddress, 
									type:_TEXTFIELD_, 
									msgName:ZaMsg.LBL_zmailPrefNewMailNotificationAddress,
									label:ZaMsg.LBL_zmailPrefNewMailNotificationAddress, 
									labelLocation:_LEFT_,
									enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailPrefNewMailNotificationEnabled,"TRUE"],
										[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefNewMailNotificationAddress]],
									enableDisableChangeEventSources:[ZaAccount.A_zmailPrefNewMailNotificationEnabled],
									nowrap:false,labelWrap:true
								},
								{ref:ZaAccount.A_zmailPrefOutOfOfficeReplyEnabled, 
									type:_ZA_CHECKBOX_, msgName:ZaMsg.LBL_zmailPrefOutOfOfficeReplyEnabled,
									label:ZaMsg.LBL_zmailPrefOutOfOfficeReplyEnabled, trueValue:"TRUE", 
									falseValue:"FALSE"
								},							
								{ref:ZaAccount.A_zmailPrefOutOfOfficeCacheDuration, 
									type:_SUPER_LIFETIME_,
									msgName:ZaMsg.MSG_zmailPrefOutOfOfficeCacheDuration,
									txtBoxLabel:ZaMsg.LBL_zmailPrefOutOfOfficeCacheDuration, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									colSpan:2
								},
								{ref:ZaAccount.A_zmailPrefOutOfOfficeReply, 
									type:_TEXTAREA_, msgName:ZaMsg.LBL_zmailPrefOutOfOfficeReply,
									label:ZaMsg.LBL_zmailPrefOutOfOfficeReply, labelLocation:_LEFT_, 
									labelCssStyle:"vertical-align:top;", 
									width:"30em",
									enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailPrefOutOfOfficeReplyEnabled,"TRUE"],
										[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefOutOfOfficeReply]],
									enableDisableChangeEventSources:[ZaAccount.A_zmailPrefOutOfOfficeReplyEnabled]
								},
								{ref:ZaAccount.A_zmailPrefMailSendReadReceipts, 
									type:_SUPER_SELECT1_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									label:ZaMsg.LBL_zmailPrefMailSendReadReceipts, 
									enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailFeatureReadReceiptsEnabled,"TRUE"],
										[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefMailSendReadReceipts]],
									enableDisableChangeEventSources:[ZaAccount.A_zmailFeatureReadReceiptsEnabled]						
								},
								{ref:ZaAccount.A_zmailPrefReadReceiptsToAddress, 
									type:_TEXTFIELD_, 
									msgName:ZaMsg.MSG_zmailPrefReadReceiptsToAddress,
									label:ZaMsg.LBL_zmailPrefReadReceiptsToAddress, 
									labelLocation:_LEFT_,
									enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailFeatureReadReceiptsEnabled,"TRUE"],
										[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefReadReceiptsToAddress]],
									enableDisableChangeEventSources:[ZaAccount.A_zmailFeatureReadReceiptsEnabled],
									nowrap:false,labelWrap:true
								}								
							]
						},						
						{type:_ZA_TOP_GROUPER_, colSizes:["275px","100%"], id:"account_prefs_mail_sending",borderCssClass:"LowPaddedTopGrouperBorder",
							label:ZaMsg.NAD_MailOptionsSending,
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
								[ZaAccount.A_zmailPrefSaveToSent,
								 ZaAccount.A_zmailAllowAnyFromAddress,
								 ZaAccount.A_zmailAllowFromAddress
								]]
							],							
							items :[
								{ref:ZaAccount.A_zmailPrefSaveToSent,  
									colSpan:2,								
									type:_SUPER_CHECKBOX_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefSaveToSent,
									checkBoxLabel:ZaMsg.LBL_zmailPrefSaveToSent,
									trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailAllowAnyFromAddress,  
									colSpan:2,								
									type:_SUPER_CHECKBOX_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailAllowAnyFromAddress,
									checkBoxLabel:ZaMsg.LBL_zmailAllowAnyFromAddress,
									trueValue:"TRUE", falseValue:"FALSE"},	
									
								{ref:ZaAccount.A_zmailAllowFromAddress,
									type:_REPEAT_,
									nowrap:false,labelWrap:true,
									label:ZaMsg.LBL_zmailAllowFromAddress,
									msgName:ZaMsg.MSG_zmailAllowFromAddress,
									labelLocation:_LEFT_, 
									addButtonLabel:ZaMsg.NAD_AddAddress, 
									align:_LEFT_,
									repeatInstance:emptyAlias,
									showAddButton:true,
									showRemoveButton:true,
									showAddOnNextRow:true,
									removeButtonLabel:ZaMsg.NAD_RemoveAddress,
									items: [
										{
											ref:".", type:_TEXTFIELD_, label:null,width:"200px", 
											enableDisableChecks:[[ZaItem.hasWritePermission,ZaAccount.A_zmailAllowFromAddress]],
											visibilityChecks:[[ZaItem.hasReadPermission,ZaAccount.A_zmailAllowFromAddress]]
										}
									],
									//onRemove:ZaAccountXFormView.onRepeatRemove,
									visibilityChecks:[ZaAccountXFormView.isSendingFromAnyAddressDisAllowed,[ZaItem.hasReadPermission,ZaAccount.A_zmailAllowFromAddress]],
									visibilityChangeEventSources:[ZaAccount.A_zmailAllowAnyFromAddress, ZaAccount.A_zmailAllowFromAddress, ZaAccount.A_COSId]								
								}															
							]
						},
						{type:_ZA_TOP_GROUPER_,colSizes:["275px","100%"], id:"account_prefs_mail_composing",borderCssClass:"LowPaddedTopGrouperBorder",
							label:ZaMsg.NAD_MailOptionsComposing,
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
								[
									ZaAccount.A_zmailPrefComposeInNewWindow,
								 	ZaAccount.A_zmailPrefComposeFormat,
								 	ZaAccount.A_zmailPrefHtmlEditorDefaultFontSize,
								 	ZaAccount.A_zmailPrefHtmlEditorDefaultFontFamily,
								 	ZaAccount.A_zmailPrefHtmlEditorDefaultFontColor,
								 	ZaAccount.A_zmailPrefForwardReplyInOriginalFormat,
								 	ZaAccount.A_zmailPrefMandatorySpellCheckEnabled,
								 	ZaAccount.A_zmailMailSignatureMaxLength,
								 	ZaAccount.A_zmailPrefMailSignature,
									ZaAccount.A_zmailPrefAutoSaveDraftInterval
								]]
							],
							items :[
								{ref:ZaAccount.A_zmailPrefComposeInNewWindow, 
									colSpan:2,
									type:_SUPER_CHECKBOX_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefComposeInNewWindow,
									checkBoxLabel:ZaMsg.LBL_zmailPrefComposeInNewWindow,
									trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefComposeFormat, 
									//colSpan:2,
									type:_SUPER_SELECT1_, 
									nowrap:false,labelWrap:true,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefComposeFormat,
									label:ZaMsg.LBL_zmailPrefComposeFormat},
								
								{ref:ZaAccount.A_zmailPrefHtmlEditorDefaultFontSize, 
									//colSpan:2,
									type:_SUPER_SELECT1_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontSize,
									label:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontSize},
								{ref:ZaAccount.A_zmailPrefHtmlEditorDefaultFontFamily, 
									//colSpan:2,
									type:_SUPER_SELECT1_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontFamily,
									label:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontFamily},
								{ref:ZaAccount.A_zmailPrefHtmlEditorDefaultFontColor, 
									type:_SUPER_DWT_COLORPICKER_,
									labelCssStyle:"width:269px;",
									msgName:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontColor,
									label:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontColor,
									labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailPrefForwardReplyInOriginalFormat, 
									colSpan:2,
									type:_SUPER_CHECKBOX_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefForwardReplyInOriginalFormat,
									checkBoxLabel:ZaMsg.LBL_zmailPrefForwardReplyInOriginalFormat, 
									trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefMandatorySpellCheckEnabled, 
									colSpan:2,
									type:_SUPER_CHECKBOX_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefMandatorySpellCheckEnabled,
									checkBoxLabel:ZaMsg.LBL_zmailPrefMandatorySpellCheckEnabled,
									trueValue:"TRUE", falseValue:"FALSE"},									
								{ref:ZaAccount.A_zmailPrefMailSignatureEnabled, 
									type:_ZA_CHECKBOX_, msgName:ZaMsg.LBL_zmailPrefMailSignatureEnabled,
									label:ZaMsg.LBL_zmailPrefMailSignatureEnabled,  
									trueValue:"TRUE", falseValue:"FALSE"},	
								/*{ref:ZaAccount.A_zmailPrefMailSignatureStyle, 
									//colSpan:2,								
									type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									colSizes:["195px","375px","190px"],
									msgName:ZaMsg.MSG_zmailPrefMailSignatureStyle,
									checkBoxLabel:ZaMsg.LBL_zmailPrefMailSignatureStyle,
									trueValue:"internet", falseValue:"outlook"},*/
								{ref:ZaAccount.A_zmailMailSignatureMaxLength, 
									colSpan:2,
									type:_SUPER_TEXTFIELD_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									labelLocation:_LEFT_, 
									msgName:ZaMsg.MSG_zmailMailSignatureMaxLength,
									txtBoxLabel:ZaMsg.LBL_zmailMailSignatureMaxLength,
									textFieldCssClass:"admin_xform_number_input"},
								{ref:ZaAccount.A_zmailPrefMailSignature, type:_TEXTAREA_, 
									msgName:ZaMsg.MSG_zmailPrefMailSignature,
									label:ZaMsg.LBL_zmailPrefMailSignature, labelLocation:_LEFT_, 
									labelCssStyle:"vertical-align:top;", width:"30em",
									enableDisableChangeEventSources:[ZaAccount.A_zmailPrefMailSignatureEnabled],
									enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailPrefMailSignatureEnabled,"TRUE"]]
								},
                                {ref:ZaAccount.A_zmailPrefAutoSaveDraftInterval, type:_SUPER_LIFETIME_,
                                    msgName:ZaMsg.MSG_zmailPrefAutoSaveDraftInterval,
                                    txtBoxLabel:ZaMsg.LBL_zmailPrefAutoSaveDraftInterval,
                                    resetToSuperLabel:ZaMsg.NAD_ResetToCOS,colSpan:2,
                                    nowrap:false,labelWrap:true
                                }
						
							]
						},
						{type:_ZA_TOP_GROUPER_, id:"account_prefs_contacts_general",
							label:ZaMsg.NAD_ContactsOptions,
                            visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        		[
									ZaAccount.A_zmailPrefAutoAddAddressEnabled,
									ZaAccount.A_zmailPrefGalAutoCompleteEnabled
                        		]]
                        	],
							items :[
								{ref:ZaAccount.A_zmailPrefAutoAddAddressEnabled, type:_SUPER_CHECKBOX_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefAutoAddAddressEnabled,checkBoxLabel:ZaMsg.LBL_zmailPrefAutoAddAddressEnabled, 
									trueValue:"TRUE", falseValue:"FALSE",
									colSpan:2
								},							
								{ref:ZaAccount.A_zmailPrefGalAutoCompleteEnabled,colSpan:2,
									type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefGalAutoCompleteEnabled,checkBoxLabel:ZaMsg.LBL_zmailPrefGalAutoCompleteEnabled, trueValue:"TRUE", falseValue:"FALSE"}
							]
						},
						{type:_ZA_TOP_GROUPER_, id:"account_prefs_calendar_general",
							label:ZaMsg.NAD_CalendarOptions,
                            visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        		[
                        			ZaAccount.A_zmailPrefTimeZoneId,
                        			ZaAccount.A_zmailPrefCalendarApptReminderWarningTime,
                        			ZaAccount.A_zmailPrefCalendarInitialView,
                        			ZaAccount.A_zmailPrefCalendarFirstDayOfWeek,
                        			ZaAccount.A_zmailPrefCalendarApptVisibility,
                        			ZaAccount.A_zmailPrefAppleIcalDelegationEnabled,
                        			ZaAccount.A_zmailPrefCalendarShowPastDueReminders,
                        			ZaAccount.A_zmailPrefCalendarToasterEnabled,
                        			ZaAccount.A_zmailPrefCalendarAllowCancelEmailToSelf,
                        			ZaAccount.A_zmailPrefCalendarAllowPublishMethodInvite,
                        			ZaAccount.A_zmailPrefCalendarAllowForwardedInvite,
                        			ZaAccount.A_zmailPrefCalendarReminderFlashTitle,
                        			ZaAccount.A_zmailPrefCalendarReminderSoundsEnabled,
                        			ZaAccount.A_zmailPrefCalendarSendInviteDeniedAutoReply,
                        			ZaAccount.A_zmailPrefCalendarAutoAddInvites,
                        			ZaAccount.A_zmailPrefCalendarNotifyDelegatedChanges,
                        			ZaAccount.A_zmailPrefCalendarAlwaysShowMiniCal,
                        			ZaAccount.A_zmailPrefCalendarUseQuickAdd,
                        			ZaAccount.A_zmailPrefUseTimeZoneListInCalendar
                        		]]
                        	],
							items :[
								{ref:ZaAccount.A_zmailPrefTimeZoneId, type:_SUPER_SELECT1_,
								    valueWidth: "220px",
									msgName:ZaMsg.MSG_zmailPrefTimeZoneId,label:ZaMsg.LBL_zmailPrefTimeZoneId, labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS},
								{ref:ZaAccount.A_zmailPrefCalendarApptReminderWarningTime,
									type:_SUPER_SELECT1_, msgName:ZaMsg.MSG_zmailPrefCalendarApptReminderWarningTime,label:ZaMsg.LBL_zmailPrefCalendarApptReminderWarningTime, labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS},
								{ref:ZaAccount.A_zmailPrefCalendarInitialView,
									type:_SUPER_SELECT1_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPrefCalendarInitialView,
									label:ZaMsg.LBL_zmailPrefCalendarInitialView, 
									labelLocation:_LEFT_
								},
								{ref:ZaAccount.A_zmailPrefCalendarFirstDayOfWeek, 
									type:_SUPER_SELECT1_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPrefCalendarFirstDayOfWeek,
									label:ZaMsg.LBL_zmailPrefCalendarFirstDayOfWeek, 
									labelLocation:_LEFT_
								},
								{ref:ZaAccount.A_zmailPrefCalendarApptVisibility, 
									type:_SUPER_SELECT1_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPrefCalendarApptVisibility,
									label:ZaMsg.LBL_zmailPrefCalendarApptVisibility, 
									labelLocation:_LEFT_
								},
								{ref:ZaAccount.A_zmailPrefAppleIcalDelegationEnabled,
									type:_SUPER_CHECKBOX_,
									 colSpan:2, resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.MSG_zmailPrefAppleIcalDelegationEnabled,
									checkBoxLabel:ZaMsg.LBL_zmailPrefAppleIcalDelegationEnabled, 
									trueValue:"TRUE", falseValue:"FALSE",
									nowrap:false,labelWrap:true
								},
								{ref:ZaAccount.A_zmailPrefCalendarShowPastDueReminders, type:_SUPER_CHECKBOX_,
									 colSpan:2,resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.MSG_zmailPrefCalendarShowPastDueReminders,
									checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarShowPastDueReminders, 
									trueValue:"TRUE", falseValue:"FALSE",
									nowrap:false,labelWrap:true
								},								
								{ref:ZaAccount.A_zmailPrefCalendarToasterEnabled, type:_SUPER_CHECKBOX_,
									 colSpan:2, resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.MSG_zmailPrefCalendarToasterEnabled,
									checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarToasterEnabled, 
									trueValue:"TRUE", falseValue:"FALSE",
									nowrap:false,labelWrap:true
								},								
								{ref:ZaAccount.A_zmailPrefCalendarAllowCancelEmailToSelf, type:_SUPER_CHECKBOX_,
									 colSpan:2, resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.MSG_zmailPrefCalendarAllowCancelEmailToSelf,
									checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarAllowCancelEmailToSelf, 
									trueValue:"TRUE", falseValue:"FALSE",
									nowrap:false,labelWrap:true
								},								
								{ref:ZaAccount.A_zmailPrefCalendarAllowPublishMethodInvite, type:_SUPER_CHECKBOX_,
									colSpan:2, resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.MSG_zmailPrefCalendarAllowPublishMethodInvite,
									checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarAllowPublishMethodInvite, 
									trueValue:"TRUE", falseValue:"FALSE",
									nowrap:false,labelWrap:true
								},								
								{ref:ZaAccount.A_zmailPrefCalendarAllowForwardedInvite, type:_SUPER_CHECKBOX_,
									 colSpan:2,resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.MSG_zmailPrefCalendarAllowForwardedInvite,
									checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarAllowForwardedInvite, 
									trueValue:"TRUE", falseValue:"FALSE",
									nowrap:false,labelWrap:true
								},								
								{ref:ZaAccount.A_zmailPrefCalendarReminderFlashTitle, type:_SUPER_CHECKBOX_,
									 colSpan:2, resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.MSG_zmailPrefCalendarReminderFlashTitle,
									checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarReminderFlashTitle, 
									trueValue:"TRUE", falseValue:"FALSE",
									nowrap:false,labelWrap:true
								},
								{ref:ZaAccount.A_zmailPrefCalendarReminderSoundsEnabled, type:_SUPER_CHECKBOX_,
									 colSpan:2, resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.LBL_zmailPrefCalendarReminderSoundsEnabled,
									checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarReminderSoundsEnabled, 
									trueValue:"TRUE", falseValue:"FALSE",
									nowrap:false,labelWrap:true
								},								
								{ref:ZaAccount.A_zmailPrefCalendarSendInviteDeniedAutoReply, type:_SUPER_CHECKBOX_,
									 colSpan:2, resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.LBL_zmailPrefCalendarSendInviteDeniedAutoReply,
									checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarSendInviteDeniedAutoReply, 
									trueValue:"TRUE", falseValue:"FALSE",
									nowrap:false,labelWrap:true
								},
								{ref:ZaAccount.A_zmailPrefCalendarAutoAddInvites, type:_SUPER_CHECKBOX_,
								 colSpan:2, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefCalendarAutoAddInvites,checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarAutoAddInvites, trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefCalendarNotifyDelegatedChanges, type:_SUPER_CHECKBOX_,
								 colSpan:2,resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefCalendarNotifyDelegatedChanges,checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarNotifyDelegatedChanges, trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefCalendarAlwaysShowMiniCal, type:_SUPER_CHECKBOX_,
								 colSpan:2, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefCalendarAlwaysShowMiniCal,checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarAlwaysShowMiniCal, trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefCalendarUseQuickAdd, 
								 colSpan:2,type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefCalendarUseQuickAdd,checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarUseQuickAdd, trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefUseTimeZoneListInCalendar, 
								 colSpan:2,type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefUseTimeZoneListInCalendar,checkBoxLabel:ZaMsg.LBL_zmailPrefUseTimeZoneListInCalendar,trueValue:"TRUE", falseValue:"FALSE"}
							]
						}						
					];
		cases.push({type:_ZATABCASE_, id:"account_form_prefs_tab", numCols:1,
            paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,
					 caseKey:_tab5,
					/*colSizes:["275px","275px","150px"],*/ items :prefItems});
	}


	if(_tab6) {
		cases.push({type:_ZATABCASE_, id:"account_form_aliases_tab", width:"100%", numCols:1,colSizes:["auto"],
            paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,
					caseKey:_tab6, 
					items: [
						{type:_ZA_TOP_GROUPER_, id:"account_form_aliases_group",borderCssClass:"LowPaddedTopGrouperBorder",
							width:"100%", numCols:1,colSizes:["auto"],
							label:ZaMsg.NAD_EditAliasesGroup,
							items :[
								{ref:ZaAccount.A_zmailMailAlias, type:_DWT_LIST_, height:"200", width:"350px", 
									forceUpdate: true, preserveSelection:false, multiselect:true,cssClass: "DLSource", 
									headerList:null,onSelection:ZaAccountXFormView.aliasSelectionListener
								},
								{type:_GROUP_, numCols:5, width:"350px", colSizes:["100px","auto","100px","auto","100px"], 
									cssStyle:"margin:10px;padding-bottom:0;",
									items: [
										{type:_DWT_BUTTON_, label:ZaMsg.TBB_Delete,width:"100px",
											onActivate:"ZaAccountXFormView.deleteAliasButtonListener.call(this);",id:"deleteAliasButton",
											enableDisableChecks:[ZaAccountXFormView.isDeleteAliasEnabled,[ZaItem.hasRight,ZaAccount.REMOVE_ACCOUNT_ALIAS_RIGHT]],
											enableDisableChangeEventSources:[ZaAccount.A2_alias_selection_cache]
										},
										{type:_CELLSPACER_},
										{type:_DWT_BUTTON_, label:ZaMsg.TBB_Edit,width:"100px",
											onActivate:"ZaAccountXFormView.editAliasButtonListener.call(this);",id:"editAliasButton",
											enableDisableChangeEventSources:[ZaAccount.A2_alias_selection_cache],
											enableDisableChecks:[ZaAccountXFormView.isEditAliasEnabled,[ZaItem.hasRight,ZaAccount.ADD_ACCOUNT_ALIAS_RIGHT],[ZaItem.hasRight,ZaAccount.REMOVE_ACCOUNT_ALIAS_RIGHT]]
										},
										{type:_CELLSPACER_},
										{type:_DWT_BUTTON_, label:ZaMsg.NAD_Add,width:"100px",
											enableDisableChecks:[[ZaItem.hasRight,ZaAccount.ADD_ACCOUNT_ALIAS_RIGHT]],
											enableDisableChangeEventSources:[ZaAccount.A2_alias_selection_cache],
											onActivate:"ZaAccountXFormView.addAliasButtonListener.call(this);"
										}
									]
								}
							]
						}
					]
				});
	}
	
	if(_tab7) {
		cases.push({type:_ZATABCASE_,id:"account_form_forwarding_tab", width:"100%", numCols:1,colSizes:["auto"],
					caseKey:_tab7, paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,
					items: [
						{type:_ZA_TOP_GROUPER_, label:ZaMsg.NAD_EditFwdTopGroupGrouper,
							id:"account_form_user_forwarding_addr",colSizes:["275px","100%"],
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
								[
									ZaAccount.A_zmailFeatureMailForwardingEnabled
								]]
							],
							items :[					
							{
								ref:ZaAccount.A_zmailFeatureMailForwardingEnabled,
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								type:_SUPER_CHECKBOX_, colSpan: 2,
								colSizes:["275", "275", "*"],
								checkBoxLabel:ZaMsg.LBL_zmailFeatureMailForwardingEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"
							},
									{ref:ZaAccount.A_zmailPrefMailLocalDeliveryDisabled, 
										type:_ZA_CHECKBOX_, 
										msgName:ZaMsg.LBL_zmailPrefMailLocalDeliveryDisabled,
										label:ZaMsg.LBL_zmailPrefMailLocalDeliveryDisabled, 
										trueValue:"TRUE", falseValue:"FALSE"
									},
									{ref:ZaAccount.A_zmailPrefMailForwardingAddress, type:_TEXTFIELD_,width:"350px",

labelCssClass:"xform_label", cssClass:"admin_xform_name_input",
										label:ZaMsg.LBL_zmailPrefMailForwardingAddress, 
										msgName:ZaMsg.LBL_zmailPrefMailForwardingAddressMsg,

nowrap:false, labelWrap:true,
										labelLocation:_LEFT_,
                                        labelCssStyle:"text-align:left;",
										align:_LEFT_,
										visibilityChecks:[ZaItem.hasReadPermission],
										enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailFeatureMailForwardingEnabled,"TRUE"]],
										enableDisableChangeEventSources:[ZaAccount.A_zmailFeatureMailForwardingEnabled, ZaAccount.A_COSId]										
									},
								{type:_GROUP_, colSizes:["275px", "*"], numCols: 2, width: "100%", colSpan:2,items:[
								{ref:ZaAccount.A_zmailPrefCalendarForwardInvitesTo, type:_DWT_LIST_, height:"100", width:"350px",
									forceUpdate: true, preserveSelection:false, multiselect:true,cssClass: "DLSource",
									nowrap:false, labelWrap:true,
									headerList:null,onSelection:ZaAccountXFormView.calFwdAddrSelectionListener,label:ZaMsg.zmailPrefCalendarForwardInvitesTo,
									labelCssClass:"gridGroupBodyLabel",
									labelCssStyle:"text-align:left;border-right:1px solid;",
									visibilityChecks:[ZaItem.hasReadPermission]
								},
								{type:_GROUP_, numCols:6, width:"625px",colSizes:["275","100px","auto","100px","auto","100px"], colSpan:2,
									visibilityChecks:[[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefCalendarForwardInvitesTo]],
									cssStyle:"margin:10px;padding-bottom:0;",
									items: [
										{type:_CELLSPACER_},
										{type:_DWT_BUTTON_, label:ZaMsg.TBB_Delete,width:"100px",
											onActivate:"ZaAccountXFormView.deleteCalFwdAddrButtonListener.call(this);",
											enableDisableChecks:[ZaAccountXFormView.isDeleteCalFwdAddrEnabled,[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefCalendarForwardInvitesTo]],
											enableDisableChangeEventSources:[ZaAccount.A2_calFwdAddr_selection_cache]
										},
										{type:_CELLSPACER_},
										{type:_DWT_BUTTON_, label:ZaMsg.TBB_Edit,width:"100px",
											onActivate:"ZaAccountXFormView.editCalFwdAddrButtonListener.call(this);",
											enableDisableChecks:[ZaAccountXFormView.isEditCalFwdAddrEnabled,[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefCalendarForwardInvitesTo]],
											enableDisableChangeEventSources:[ZaAccount.A2_calFwdAddr_selection_cache]
										},
										{type:_CELLSPACER_},
	                                       {type:_DWT_BUTTON_, label:ZaMsg.NAD_Add,width:"100px",
											enableDisableChecks:[[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefCalendarForwardInvitesTo]],                                        
											onActivate:"ZaAccountXFormView.addCalFwdAddrButtonListener.call(this);"
										}
									]
								},
                               {type: _DWT_ALERT_, colSpan: 2,
                                   containerCssStyle: "padding:0 10px 10px;width:100%;",
                                   style: DwtAlert.WARNING,
                                   iconVisible: true,
                                   content: ZaMsg.Alert_Bouncing_Reveal_Hidden_Adds
                                },
                                {ref:ZaAccount.A_zmailMailForwardingAddress, type:_DWT_LIST_, height:"100", width:"350px",
									forceUpdate: true, preserveSelection:false, multiselect:true,cssClass: "DLSource", 
									headerList:null,onSelection:ZaAccountXFormView.fwdAddrSelectionListener,label:ZaMsg.NAD_EditFwdGroup,
                                    labelCssClass:"gridGroupBodyLabel", nowrap:false, labelWrap:true,
                                    labelCssStyle:"text-align:left;border-right:1px solid;",
									visibilityChecks:[ZaItem.hasReadPermission]
								},
								{type:_GROUP_, numCols:6, width:"625px",colSizes:["275","100px","auto","100px","auto","100px"], colSpan:2,
									cssStyle:"margin:10px;padding-bottom:0;",
									items: [
										{type:_CELLSPACER_},
										{type:_DWT_BUTTON_, label:ZaMsg.TBB_Delete,width:"100px",
											onActivate:"ZaAccountXFormView.deleteFwdAddrButtonListener.call(this);",
											enableDisableChecks:[ZaAccountXFormView.isDeleteFwdAddrEnabled,[ZaItem.hasWritePermission,ZaAccount.A_zmailMailForwardingAddress]],
											enableDisableChangeEventSources:[ZaAccount.A2_fwdAddr_selection_cache]
										},
										{type:_CELLSPACER_},
										{type:_DWT_BUTTON_, label:ZaMsg.TBB_Edit,width:"100px",
											onActivate:"ZaAccountXFormView.editFwdAddrButtonListener.call(this);",
											enableDisableChecks:[ZaAccountXFormView.isEditFwdAddrEnabled,[ZaItem.hasWritePermission,ZaAccount.A_zmailMailForwardingAddress]],
											enableDisableChangeEventSources:[ZaAccount.A2_fwdAddr_selection_cache]
										},
										{type:_CELLSPACER_},
                                        {type:_DWT_BUTTON_, label:ZaMsg.NAD_Add,width:"100px",
											enableDisableChecks:[[ZaItem.hasWritePermission,ZaAccount.A_zmailMailForwardingAddress]],                                        
											onActivate:"ZaAccountXFormView.addFwdAddrButtonListener.call(this);"
										}
									]
								}
							]
						}
                        ]}
					]
				});
	}

	if(_tab8) {
		cases.push({type:_ZATABCASE_, id:"account_form_interop_tab", width:"100%", numCols:1,colSizes:["auto"],
                    paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,
					caseKey:_tab8, 
					items: [
						{type:_ZA_TOP_GROUPER_, id:"account_form_interop_group",
                            borderCssClass:"LowPaddedTopGrouperBorder",
							 width:"100%", numCols:1,colSizes:["auto"],
							label:ZaMsg.NAD_EditFpGroup,
							items :[
								{ref:ZaAccount.A_zmailForeignPrincipal, type:_DWT_LIST_, height:"200", width:"350px",
									forceUpdate: true, preserveSelection:false, multiselect:true,cssClass: "DLSource",
									headerList:null,onSelection:ZaAccountXFormView.fpSelectionListener
								},
								{type:_GROUP_, numCols:7, width:"350px", colSizes:["100px","auto","100px","auto","100px", "auto","100px"],
									cssStyle:"margin:10px;padding-bottom:0;",
									items: [
										{type:_DWT_BUTTON_, label:ZaMsg.TBB_Push,width:"100px",
											onActivate:"ZaAccountXFormView.pushFpButtonListener.call(this);",
											enableDisableChecks:[ZaAccountXFormView.isPushFpEnabled,[ZaItem.hasWritePermission,ZaAccount.A_zmailForeignPrincipal]],
											enableDisableChangeEventSources:[ZaAccount.A_zmailForeignPrincipal]
										},
										{type:_CELLSPACER_},
                                        {type:_DWT_BUTTON_, label:ZaMsg.TBB_Delete,width:"100px",
                                            onActivate:"ZaAccountXFormView.deleteFpButtonListener.call(this);",
                                            enableDisableChecks:[ZaAccountXFormView.isDeleteFpEnabled,[ZaItem.hasWritePermission,ZaAccount.A_zmailForeignPrincipal]],
                                            enableDisableChangeEventSources:[ZaAccount.A2_fp_selection_cache]
                                        },
                                        {type:_CELLSPACER_},
                                        {type:_DWT_BUTTON_, label:ZaMsg.TBB_Edit,width:"100px",
                                            onActivate:"ZaAccountXFormView.editFpButtonListener.call(this);",
                                            enableDisableChecks:[ZaAccountXFormView.isEditFpEnabled,[ZaItem.hasWritePermission,ZaAccount.A_zmailForeignPrincipal]],
                                            enableDisableChangeEventSources:[ZaAccount.A2_fp_selection_cache]
										},
										{type:_CELLSPACER_},
										{type:_DWT_BUTTON_, label:ZaMsg.NAD_Add,width:"100px",
											enableDisableChecks:[[ZaItem.hasWritePermission,ZaAccount.A_zmailForeignPrincipal]],
											onActivate:"ZaAccountXFormView.addFpButtonListener.call(this);"
										}
									]
								}
							]
						}
					]
				});
	}

	if(_tab9) {
		cases.push({type:_ZATABCASE_, id:"account_form_themes_tab", numCols:1,
            caseKey:_tab9,
			items:[
				{type:_SPACER_},
				{type:_GROUP_, 
					items:[
						{ref:ZaAccount.A_zmailPrefSkin, type:_SUPER_SELECT1_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
	                        msgName:ZaMsg.LBL_zmailPrefSkin,label:ZaMsg.LBL_zmailPrefSkin, labelLocation:_LEFT_,
	                        choices:ZaAccountXFormView.themeChoices,
							visibilityChecks:[ZaAccountXFormView.gotSkins]
						},
	                    {type:_OUTPUT_,ref:ZaAccount.A_zmailPrefSkin,label:ZaMsg.LBL_zmailPrefSkin, labelLocation:_LEFT_, 
	                   	  visibilityChecks:[ZaAccountXFormView.gotNoSkins]
	                    }
					] 
				},
				{type:_SPACER_},
				{type:_SUPER_SELECT_CHECK_,
					selectRef:ZaAccount.A_zmailAvailableSkin, 
					ref:ZaAccount.A_zmailAvailableSkin, 
					choices:ZaAccountXFormView.themeChoices,
					visibilityChecks:[Case_XFormItem.prototype.isCurrentTab,ZaAccountXFormView.gotSkins],
					visibilityChangeEventSources:[ZaModel.currentTab],
					caseKey:_tab9, caseVarRef:ZaModel.currentTab,
					limitLabel:ZaMsg.NAD_LimitThemesTo
				},
				{type:_DWT_ALERT_,colSpan:2,style: DwtAlert.WARNING, iconVisible:true,
					visibilityChecks:[ZaAccountXFormView.gotNoSkins],
					value:ZaMsg.ERROR_CANNOT_FIND_SKINS_FOR_ACCOUNT
				}
			] 
		});
	}	

	if(_tab10) {
		cases.push({type:_ZATABCASE_, id:"account_form_zimlets_tab", numCols:1,
            caseKey:_tab10, 
			items:[
				{type:_GROUP_, numCols:1,colSizes:["auto"],
					items: [
						{type:_SUPER_ZIMLET_SELECT_,
							selectRef:ZaAccount.A_zmailZimletAvailableZimlets, 
							ref:ZaAccount.A_zmailZimletAvailableZimlets, 
							choices:ZaAccountXFormView.zimletChoices,
							visibilityChecks:[Case_XFormItem.prototype.isCurrentTab],
							visibilityChangeEventSources:[ZaModel.currentTab],
							caseKey:_tab10, caseVarRef:ZaModel.currentTab,
							limitLabel:ZaMsg.NAD_LimitZimletsTo
						},
						{type: _DWT_ALERT_,
							containerCssStyle: "padding-bottom:0",
							style: DwtAlert.INFO,
							iconVisible: false,
							content: ZaMsg.Zimlet_Note
						}
					]
				}
			] 
		});
	}
	if(_tab11) {
		cases.push({type:_ZATABCASE_, id:"account_form_advanced_tab", numCols:1,
        paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,
					caseKey:_tab11, 
					items: [
						{type:_ZA_TOP_GROUPER_, id:"account_attachment_settings",colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_AttachmentsGrouper,
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        			[ZaAccount.A_zmailAttachmentsBlocked]]],
							items :[
								{ref:ZaAccount.A_zmailAttachmentsBlocked, type:_SUPER_CHECKBOX_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:ZaMsg.NAD_RemoveAllAttachments,
									checkBoxLabel:ZaMsg.NAD_RemoveAllAttachments, 
									trueValue:"TRUE", falseValue:"FALSE"
								}
							]
						},

						{type:_ZA_TOP_GROUPER_, id:"account_quota_settings",colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_QuotaGrouper,
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        			[ZaAccount.A_zmailMailForwardingAddressMaxLength,
                        			ZaAccount.A_zmailMailForwardingAddressMaxNumAddrs,
                        			ZaAccount.A_zmailMailQuota,
                        			ZaAccount.A_zmailContactMaxNumEntries,
                        			ZaAccount.A_zmailQuotaWarnPercent,
                        			ZaAccount.A_zmailQuotaWarnInterval,
                        			ZaAccount.A_zmailQuotaWarnMessage]],
                        			[XForm.checkInstanceValueNot,ZaAccount.A_zmailIsExternalVirtualAccount,"TRUE"]],
							items: [
								{ref:ZaAccount.A_zmailMailForwardingAddressMaxLength, type:_SUPER_TEXTFIELD_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMailForwardingAddressMaxLength,
                                     colSpan:1,
									txtBoxLabel:ZaMsg.LBL_zmailMailForwardingAddressMaxLength, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input"
								},
								{ref:ZaAccount.A_zmailMailForwardingAddressMaxNumAddrs, type:_SUPER_TEXTFIELD_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMailForwardingAddressMaxNumAddrs,
                                     colSpan:1,
									txtBoxLabel:ZaMsg.LBL_zmailMailForwardingAddressMaxNumAddrs, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input"
								},							
								{ref:ZaAccount.A_zmailMailQuota, type:_SUPER_TEXTFIELD_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                     colSpan:1,
									txtBoxLabel:ZaMsg.LBL_zmailMailQuota, msgName:ZaMsg.MSG_zmailMailQuota,labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input", 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailContactMaxNumEntries, type:_SUPER_TEXTFIELD_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailContactMaxNumEntries,
                                     colSpan:1,
									txtBoxLabel:ZaMsg.LBL_zmailContactMaxNumEntries, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input"
								},
								{ref:ZaAccount.A_zmailQuotaWarnPercent, type:_SUPER_TEXTFIELD_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									txtBoxLabel:ZaMsg.LBL_zmailQuotaWarnPercent,
                                     colSpan:1,
									msgName:ZaMsg.MSG_zmailQuotaWarnPercent,labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input", 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailQuotaWarnInterval, type:_SUPER_LIFETIME_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                     colSpan:1,
									txtBoxLabel:ZaMsg.LBL_zmailQuotaWarnInterval, 
									msgName:ZaMsg.MSG_zmailQuotaWarnInterval,labelLocation:_LEFT_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailQuotaWarnMessage, type:_SUPER_TEXTAREA_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									txtBoxLabel:ZaMsg.MSG_zmailQuotaWarnMessage,
									msgName:ZaMsg.LBL_zmailQuotaWarnMessage,
									colSpan:1,
									labelCssStyle:"vertical-align:top;", textAreaWidth:"250px", 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								}
								
							]
						},

						{type:_ZA_TOP_GROUPER_, id:"account_datasourcepolling_settings",colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_DataSourcePolling,
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        			[ZaAccount.A_zmailDataSourceMinPollingInterval, 
							ZaAccount.A_zmailDataSourcePop3PollingInterval,
							ZaAccount.A_zmailDataSourceImapPollingInterval,
							ZaAccount.A_zmailDataSourceCalendarPollingInterval,
							ZaAccount.A_zmailDataSourceRssPollingInterval,
							ZaAccount.A_zmailDataSourceCaldavPollingInterval
							]]],
							items: [
                                                                {ref:ZaAccount.A_zmailDataSourceMinPollingInterval, type:_SUPER_LIFETIME_,
                                                                        msgName:ZaMsg.MSG_zmailDataSourceMinPollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourceMinPollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,colSpan:1,
                                                                        nowrap:false,labelWrap:true
                                                                },
                                                                {ref:ZaAccount.A_zmailDataSourcePop3PollingInterval, type:_SUPER_LIFETIME_,
                                                                        sgName:ZaMsg.MSG_zmailDataSourcePop3PollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourcePop3PollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,colSpan:1,
                                                                        nowrap:false,labelWrap:true
                                                                },
                                                                {ref:ZaAccount.A_zmailDataSourceImapPollingInterval, type:_SUPER_LIFETIME_,
                                                                        msgName:ZaMsg.MSG_zmailDataSourceImapPollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourceImapPollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,colSpan:1,
                                                                        nowrap:false,labelWrap:true
                                                                },
                                                                {ref:ZaAccount.A_zmailDataSourceCalendarPollingInterval, type:_SUPER_LIFETIME_,
                                                                        msgName:ZaMsg.MSG_zmailDataSourceCalendarPollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourceCalendarPollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,colSpan:1,
                                                                        nowrap:false,labelWrap:true
                                                                },
                                                                {ref:ZaAccount.A_zmailDataSourceRssPollingInterval, type:_SUPER_LIFETIME_,
                                                                        msgName:ZaMsg.MSG_zmailDataSourceRssPollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourceRssPollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,colSpan:1,
                                                                        nowrap:false,labelWrap:true
                                                                },
                                                                {ref:ZaAccount.A_zmailDataSourceCaldavPollingInterval, type:_SUPER_LIFETIME_,
                                                                        msgName:ZaMsg.MSG_zmailDataSourceCaldavPollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourceCaldavPollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,colSpan:1,
                                                                        nowrap:false,labelWrap:true
                                                                }
							]
						},
                                                
                                                {type:_ZA_TOP_GROUPER_, id:"account_proxyalloweddomain_settings",
               					 	label: ZaMsg.NAD_ProxyAllowedDomains,
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        					[ZaAccount.A_zmailProxyAllowedDomains]]],
               						items:[
                   					{
                       						ref: ZaAccount.A_zmailProxyAllowedDomains,
                       						label:ZaMsg.LBL_zmailProxyAllowedDomains, 
                       						labelCssStyle:"vertical-align:top;",
                       						type:_SUPER_REPEAT_,
                       						resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                       						repeatInstance:"",
                                            colSizes:["275px", "*"],
                       						addButtonLabel:ZaMsg.NAD_ProxyAddAllowedDomain ,
                       						removeButtonLabel: ZaMsg.NAD_ProxyRemoveAllowedDomain,
                       						showAddButton:true,
                       						showRemoveButton:true,
                       						showAddOnNextRow:true,
                       						repeatItems: [
                               					{ref:".", type:_TEXTFIELD_,
                                				enableDisableChecks:[ZaItem.hasWritePermission] ,
                                  				visibilityChecks:[[ZaItem.hasReadPermission,ZaAccount.A_zmailProxyAllowedDomains]],
                                  				enableDisableChecks:[[ZaItem.hasWritePermission,ZaAccount.A_zmailProxyAllowedDomains]],
                                  				width: "15em"}
                                				]			
                     					}
               						]				
             					},

						{type:_ZA_TOP_GROUPER_,id:"account_password_settings",colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_PasswordGrouper,
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        			[ZaAccount.A_zmailPasswordLocked,
                        			ZaAccount.A_zmailMinPwdLength,
                        			ZaAccount.A_zmailMaxPwdLength,
                        			ZaAccount.A_zmailPasswordMinUpperCaseChars,
                        			ZaAccount.A_zmailPasswordMinLowerCaseChars,
                        			ZaAccount.A_zmailPasswordMinPunctuationChars,
                        			ZaAccount.A_zmailPasswordMinNumericChars,
                        			ZaAccount.A_zmailPasswordMinDigitsOrPuncs,
                        			ZaAccount.A_zmailMinPwdAge,
                        			ZaAccount.A_zmailMaxPwdAge,
                        			ZaAccount.A_zmailEnforcePwdHistory]]],
							items: [ 
						                { type: _DWT_ALERT_, containerCssStyle: "padding-bottom:0;", colSpan:3,
						                      style: DwtAlert.INFO,iconVisible: (!ZaAccountXFormView.isAuthfromInternal(entry.name)),
						                      content: ((ZaAccountXFormView.isAuthfromInternal(entry.name))?ZaMsg.Alert_InternalPassword:ZaMsg.Alert_ExternalPassword)
						                },
								{ref:ZaAccount.A_zmailPasswordLocked, type:_SUPER_CHECKBOX_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.NAD_PwdLocked,
									checkBoxLabel:ZaMsg.NAD_PwdLocked, 
								 	trueValue:"TRUE", falseValue:"FALSE",
									visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailMinPwdLength, 
									type:_SUPER_TEXTFIELD_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMinPwdLength,
									txtBoxLabel:ZaMsg.LBL_zmailMinPwdLength, 
									labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
									visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailMaxPwdLength, type:_SUPER_TEXTFIELD_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMaxPwdLength,txtBoxLabel:ZaMsg.LBL_zmailMaxPwdLength,
									labelLocation:_LEFT_, textFieldCssClass:"admin_xform_number_input",
									visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailPasswordMinUpperCaseChars, 
									type:_SUPER_TEXTFIELD_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPasswordMinUpperCaseChars,
									txtBoxLabel:ZaMsg.LBL_zmailPasswordMinUpperCaseChars, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
									visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailPasswordMinLowerCaseChars, 
									type:_SUPER_TEXTFIELD_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPasswordMinLowerCaseChars,
									txtBoxLabel:ZaMsg.LBL_zmailPasswordMinLowerCaseChars, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
									visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailPasswordMinPunctuationChars, 
									type:_SUPER_TEXTFIELD_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPasswordMinPunctuationChars,
									txtBoxLabel:ZaMsg.LBL_zmailPasswordMinPunctuationChars, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
									visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailPasswordMinNumericChars, 
									type:_SUPER_TEXTFIELD_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPasswordMinNumericChars,
									txtBoxLabel:ZaMsg.LBL_zmailPasswordMinNumericChars, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
									visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailPasswordMinDigitsOrPuncs, 
									type:_SUPER_TEXTFIELD_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPasswordMinDigitsOrPuncs,
									txtBoxLabel:ZaMsg.LBL_zmailPasswordMinDigitsOrPuncs, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
									visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
								},																
								{ref:ZaAccount.A_zmailMinPwdAge, 
									type:_SUPER_TEXTFIELD_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_passMinAge,txtBoxLabel:ZaMsg.LBL_passMinAge, labelLocation:_LEFT_,
									textFieldCssClass:"admin_xform_number_input",
									visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailMaxPwdAge, 
									type:_SUPER_TEXTFIELD_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_passMaxAge,txtBoxLabel:ZaMsg.LBL_passMaxAge, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
									visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailEnforcePwdHistory, 
									type:_SUPER_TEXTFIELD_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailEnforcePwdHistory,
									txtBoxLabel:ZaMsg.LBL_zmailEnforcePwdHistory, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
									visibilityChecks:[],enableDisableChecks:[[ZaAccountXFormView.isAuthfromInternalSync, entry.name, ZaAccount.A_name]]
								}
							]
						},
						{type:_ZA_TOP_GROUPER_, id:"password_lockout_settings",colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_FailedLoginGrouper,
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        			[ZaAccount.A_zmailPasswordLockoutEnabled,
                        			ZaAccount.A_zmailPasswordLockoutMaxFailures,
                        			ZaAccount.A_zmailPasswordLockoutDuration,
                        			ZaAccount.A_zmailPasswordLockoutFailureLifetime]]],							
							items :[
								{ref:ZaAccount.A_zmailPasswordLockoutEnabled, 
									type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPasswordLockoutEnabled, colSpan:1,
									checkBoxLabel:ZaMsg.LBL_zmailPasswordLockoutEnabled, 
									trueValue:"TRUE", falseValue:"FALSE"
								},
								{ref:ZaAccount.A_zmailPasswordLockoutMaxFailures, type:_SUPER_TEXTFIELD_, 
									enableDisableChecks: [[XForm.checkInstanceValue,ZaAccount.A_zmailPasswordLockoutEnabled,"TRUE"]],
								 	enableDisableChangeEventSources:[ZaAccount.A_zmailPasswordLockoutEnabled,ZaAccount.A_COSId],
									txtBoxLabel:ZaMsg.LBL_zmailPasswordLockoutMaxFailures,
									toolTipContent:ZaMsg.TTP_zmailPasswordLockoutMaxFailuresSub,
									msgName:ZaMsg.MSG_zmailPasswordLockoutMaxFailures,
									labelLocation:_LEFT_,
                                    colSpan:1,
									textFieldCssClass:"admin_xform_number_input", 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailPasswordLockoutDuration, type:_SUPER_LIFETIME_, 
									colSpan:1,
									enableDisableChecks: [[XForm.checkInstanceValue,ZaAccount.A_zmailPasswordLockoutEnabled,"TRUE"]],
								 	enableDisableChangeEventSources:[ZaAccount.A_zmailPasswordLockoutEnabled,ZaAccount.A_COSId],
									txtBoxLabel:ZaMsg.LBL_zmailPasswordLockoutDuration,
									toolTipContent:ZaMsg.TTP_zmailPasswordLockoutDurationSub,
									msgName:ZaMsg.MSG_zmailPasswordLockoutDuration,
									textFieldCssClass:"admin_xform_number_input", 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailPasswordLockoutFailureLifetime, type:_SUPER_LIFETIME_, 
									colSpan:1,
									enableDisableChecks: [[XForm.checkInstanceValue,ZaAccount.A_zmailPasswordLockoutEnabled,"TRUE"]],
								 	enableDisableChangeEventSources:[ZaAccount.A_zmailPasswordLockoutEnabled,ZaAccount.A_COSId],								
									txtBoxLabel:ZaMsg.LBL_zmailPasswordLockoutFailureLifetime,
									toolTipContent:ZaMsg.TTP_zmailPasswordLockoutFailureLifetimeSub,
									msgName:ZaMsg.MSG_zmailPasswordLockoutFailureLifetime,
									textFieldCssClass:"admin_xform_number_input", 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									labelCssStyle:"white-space:normal;border-right:1px solid;",
									nowrap:false,labelWrap:true
								}																		
								
							]
						},
						{type:_ZA_TOP_GROUPER_, colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_TimeoutGrouper,id:"timeout_settings",
							visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        			[ZaAccount.A_zmailAdminAuthTokenLifetime,
                        			ZaAccount.A_zmailAuthTokenLifetime,
                        			ZaAccount.A_zmailMailIdleSessionTimeout,
                        			ZaAccount.A_zmailDumpsterUserVisibleAge]]],
							items: [
								{ref:ZaAccount.A_zmailAdminAuthTokenLifetime,
									type:_SUPER_LIFETIME_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailAdminAuthTokenLifetime,
									txtBoxLabel:ZaMsg.LBL_zmailAdminAuthTokenLifetime,
									//enableDisableChecks:[ZaAccountXFormView.isAdminAccount],
									enableDisableChecks:[ZaAccount.isAdminAccount],
									enableDisableChangeEventSources:[ZaAccount.A_zmailIsAdminAccount, ZaAccount.A_zmailIsDelegatedAdminAccount]
								},								
								{ref:ZaAccount.A_zmailAuthTokenLifetime,
									type:_SUPER_LIFETIME_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailAuthTokenLifetime,
									txtBoxLabel:ZaMsg.LBL_zmailAuthTokenLifetime},										
								{ref:ZaAccount.A_zmailMailIdleSessionTimeout, 
									type:_SUPER_LIFETIME_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMailIdleSessionTimeout,
									txtBoxLabel:ZaMsg.LBL_zmailMailIdleSessionTimeout},
                                {ref:ZaAccount.A_zmailDumpsterUserVisibleAge,
                                    resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                    type:_SUPER_LIFETIME_,
                                    msgName:ZaMsg.MSG_zmailDumpsterUserVisibleAge, 
                                    txtBoxLabel:ZaMsg.LBL_zmailDumpsterUserVisibleAge,
                                    visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaAccount.A_zmailDumpsterEnabled, "TRUE"]],
                                    visibilityChangeEventSources:[ZaAccount.A_zmailDumpsterEnabled]
                                }
							]
						},
                        { type:_ZA_TOP_GROUPER_, colSizes:["auto"], numCols:1,
                        	visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        			[ZaAccount.A_zmailMailMessageLifetime,
                        			ZaAccount.A_zmailMailTrashLifetime,
                        			ZaAccount.A_zmailMailSpamLifetime,
                        			ZaAccount.A_zmailMailDumpsterLifetime
									]],[XForm.checkInstanceValueNot,ZaAccount.A_zmailIsExternalVirtualAccount,"TRUE"]],
							label:ZaMsg.NAD_MailRetentionGrouper, id: "mailretention_settings",
							items: [
                                { type: _DWT_ALERT_,
                                  containerCssStyle: "padding:0 10px 10px;width:100%;",
                                  style: DwtAlert.INFO,
                                  iconVisible: false,
                                  content: ZaMsg.Alert_EnableMailRetention
                                },
                                {ref:ZaAccount.A_zmailMailMessageLifetime, type:_SUPER_LIFETIME2_,
                                    resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                    msgName:ZaMsg.MSG_zmailMailMessageLifetime,
                                    txtBoxLabel:ZaMsg.LBL_zmailMailMessageLifetime,
                                    visibilityChecks:[[ZaItem.hasReadPermission], [ZaAccount.isEmailRetentionPolicyEnabled]],
                                    visibilityChangeEventSources:[ZaAccount.A_mailHost],
									labelCssStyle:"border-right:1px solid;",
                                    labelCssClass:"gridGroupBodyLabel"
                                },
                                {ref:ZaAccount.A_zmailMailTrashLifetime, type:_SUPER_LIFETIME1_,
                                    resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailMailTrashLifetime,
                                    txtBoxLabel:ZaMsg.LBL_zmailMailTrashLifetime,
                                    visibilityChecks:[[ZaItem.hasReadPermission], [ZaAccount.isEmailRetentionPolicyEnabled]],
                                    visibilityChangeEventSources:[ZaAccount.A_mailHost],
									labelCssStyle:"padding-left:10px; text-align:left; border-right:1px solid;",
                                    labelCssClass:"gridGroupBodyLabel"
                                },
                                {ref:ZaAccount.A_zmailMailSpamLifetime, type:_SUPER_LIFETIME1_,
                                    resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                    msgName:ZaMsg.MSG_zmailMailSpamLifetime,
                                    txtBoxLabel:ZaMsg.LBL_zmailMailSpamLifetime,
                                    visibilityChecks:[[ZaItem.hasReadPermission], [ZaAccount.isEmailRetentionPolicyEnabled]],
                                    visibilityChangeEventSources:[ZaAccount.A_mailHost],
									labelCssStyle:"padding-left:10px; text-align:left; border-right:1px solid;",
                                    labelCssClass:"gridGroupBodyLabel"
                                },
                                {ref:ZaAccount.A_zmailMailDumpsterLifetime,
                                    resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                    type:_SUPER_LIFETIME1_,
                                    msgName:ZaMsg.MSG_zmailMailDumpsterLifetime,
                                    txtBoxLabel:ZaMsg.LBL_zmailMailDumpsterLifetime,
									labelCssStyle:"padding-left:10px; text-align:left;border-right:1px solid;",
                                    labelCssClass:"gridGroupBodyLabel",
                                    visibilityChecks:[[ZaItem.hasReadPermission], [ZaAccount.isEmailRetentionPolicyEnabled], [XForm.checkInstanceValue, ZaAccount.A_zmailDumpsterEnabled, "TRUE"], [XForm.checkInstanceValue, ZaAccount.A_zmailDumpsterPurgeEnabled, "TRUE"]],
                                    visibilityChangeEventSources:[ZaAccount.A_zmailDumpsterEnabled, ZaAccount.A_zmailDumpsterPurgeEnabled]
                                }
                            ]
                        },
                        {type:_ZA_TOP_GROUPER_, //colSizes:["auto"],numCols:1,
								label:ZaMsg.NAD_InteropGrouper,   id: "interop_settings",
								visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                        			[ZaAccount.A_zmailFreebusyExchangeUserOrg]]],
							items: [
                                { ref: ZaAccount.A_zmailFreebusyExchangeUserOrg, type: _SUPER_TEXTFIELD_,
                                    textFieldWidth: "220px",
                                    resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                    msgName:ZaMsg.LBL_zmailFreebusyExchangeUserOrg,
                                    txtBoxLabel:ZaMsg.LBL_zmailFreebusyExchangeUserOrg, labelLocation:_LEFT_,
                                    textFieldCssClass:"admin_xform_number_input"
                                }
                            ]
                        },
	                	{type:_ZA_TOP_GROUPER_, label:ZaMsg.NAD_MailTransportGrouper, id:"mailtransport_setting",
	                                colSizes:["275px","*"],numCols:2,
	                                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
	                                                [ZaAccount.A_zmailMailTransport, ZaAccount.A_zmailMailCanonicalAddress]]],
	                           items:[
	                                {type:_OUTPUT_,ref:".",label:"", labelLocation:_LEFT_, value: ZaMsg.MSG_MailTransportMessage},
	                                {ref:ZaAccount.A_zmailMailTransport, type:_TEXTFIELD_, msgName:ZaMsg.NAD_MailTransport,label:ZaMsg.NAD_MailTransport,
	                                        labelLocation:_LEFT_, cssClass:"admin_xform_name_input", width:220
	                                },
	                                {type: _DWT_ALERT_, 
	                                	content:ZaMsg.CannonicalWarning,
	                    			 	visibilityChecks:[[ZaItem.hasWritePermission,ZaAccount.A_zmailMailCanonicalAddress]],
	                    			 	visibilityChangeEventSources:[ZaAccount.A_zmailMailCanonicalAddress],
	                    				containerCssStyle: "width:400px;",
	                    				style: DwtAlert.WARNING, iconVisible: true
	                    			},
	                            	{ref:ZaAccount.A_zmailMailCanonicalAddress, type:_TEXTFIELD_, msgName:ZaMsg.NAD_CanonicalFrom,label:ZaMsg.NAD_CanonicalFrom,
	                                    labelLocation:_LEFT_, cssClass:"admin_xform_name_input", width:220
	                                }
	
	                    		]
	                	},
	                	{type: _SPACER_ , height: "10px" }  //add some spaces at the bottom of the page
                    ]
                });
	}
	
	xFormObject.tableCssStyle="width:100%;";

        xFormObject.items = [
                {type:_GROUP_, cssClass:"ZmSelectedHeaderBg", colSpan:"*", id:"xform_header",
                    items: [
                        {type:_GROUP_, numCols:4, width:"100%", colSizes:["60px","*","80px","*"],items:headerItems}
                    ]
                },
                {type:_TAB_BAR_,  ref:ZaModel.currentTab,choices:this.tabChoices,cssClass:"ZaTabBar", cssStyle:"display:none;", id:"xform_tabbar"},
                {type:_SWITCH_, align:_LEFT_, valign:_TOP_, items:cases}
        ];
};
ZaTabView.XFormModifiers["ZaAccountXFormView"].push(ZaAccountXFormView.myXFormModifier);

ZaAccountXFormView.prototype.getTabChoices =
function() {
    return this.tabChoices;
}
