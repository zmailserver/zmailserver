/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013 VMware, Inc.
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

ZaNewAccountXWizard = function(parent, entry) {
	ZaXWizardDialog.call(this, parent, null, ZaMsg.NCD_NewAccTitle, "720px", "300px","ZaNewAccountXWizard",null,ZaId.DLG_NEW_ACCT);
	this.accountStatusChoices = [
		{value:ZaAccount.ACCOUNT_STATUS_ACTIVE, label:ZaAccount.getAccountStatusMsg (ZaAccount.ACCOUNT_STATUS_ACTIVE)},
		{value:ZaAccount.ACCOUNT_STATUS_CLOSED, label:ZaAccount.getAccountStatusMsg (ZaAccount.ACCOUNT_STATUS_CLOSED)},
		{value:ZaAccount.ACCOUNT_STATUS_LOCKED, label: ZaAccount.getAccountStatusMsg (ZaAccount.ACCOUNT_STATUS_LOCKED)},
        {value:ZaAccount.ACCOUNT_STATUS_PENDING, label: ZaAccount.getAccountStatusMsg (ZaAccount.ACCOUNT_STATUS_PENDING)},
        {value:ZaAccount.ACCOUNT_STATUS_MAINTENANCE, label:ZaAccount.getAccountStatusMsg(ZaAccount.ACCOUNT_STATUS_MAINTENANCE)}
	];

	this.initForm(ZaAccount.myXModel,this.getMyXForm(entry), null);	
  
	this._localXForm.setController();	
	this._localXForm.addListener(DwtEvent.XFORMS_FORM_DIRTY_CHANGE, new AjxListener(this, ZaNewAccountXWizard.prototype.handleXFormChange));
	this._localXForm.addListener(DwtEvent.XFORMS_VALUE_ERROR, new AjxListener(this, ZaNewAccountXWizard.prototype.handleXFormChange));	
	this._helpURL = ZaNewAccountXWizard.helpURL;
	
	this._domains = {} ;
}


ZaNewAccountXWizard.zimletChoices = new XFormChoices([], XFormChoices.SIMPLE_LIST);
ZaNewAccountXWizard.themeChoices = new XFormChoices([], XFormChoices.OBJECT_LIST);
ZaNewAccountXWizard.prototype = new ZaXWizardDialog;
ZaNewAccountXWizard.prototype.constructor = ZaNewAccountXWizard;
ZaNewAccountXWizard.prototype.toString = function() {
    return "ZaNewAccountXWizard";
}
ZaXDialog.XFormModifiers["ZaNewAccountXWizard"] = new Array();
ZaNewAccountXWizard.helpURL = location.pathname + ZaUtil.HELP_URL + "managing_accounts/create_an_account.htm?locid="+AjxEnv.DEFAULT_LOCALE;
ZaNewAccountXWizard.prototype.handleXFormChange = 
function () {
	if(this._localXForm.hasErrors()) {
		var isNeeded = true;
				
/*
 *Bug 49662 If it is alias step, we check the error'root. If the error is thrown
 *for the username is null, we reset this error's status. For emailaddr item's 
 *OnChange() function is called after item value validation. At the stage of
 *value validation, an error is thrown and OnChange can't be called. If we
 *modify the email-address's validation method, it will effect the first stage
 *of account creatin. So we reset the error status here
 */		
        	if(this._containedObject[ZaModel.currentStep] == ZaNewAccountXWizard.ALIASES_STEP){
			var args = arguments[0];
			if(args && args.formItem && (args.formItem.type == "emailaddr")){
				isNeeded = !args.formItem.clearNameNullError();
			}
		}

		if(isNeeded){
			this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
			this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
			this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(false);
		}
	} else {
		if(this._containedObject.attrs[ZaAccount.A_lastName]
                && this._containedObject[ZaAccount.A_name].indexOf("@") > 0
                && ZaAccount.isAccountTypeSet(this._containedObject) 
                ) {
			this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(true);
			if(this._containedObject[ZaModel.currentStep] != this._lastStep) {
				this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
			}
			if(this._containedObject[ZaModel.currentStep] != ZaNewAccountXWizard.GENERAL_STEP) {
				this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
			}			
		}
	}
}

//update the account type output with the right available/used account counts
//and not default account type choices displayed
ZaNewAccountXWizard.accountTypeItemId = "account_type_output_" + Dwt.getNextId();
ZaNewAccountXWizard.prototype.updateAccountType =
function ()  {                                                      
    var item = this._localXForm.getItemsById (ZaNewAccountXWizard.accountTypeItemId) [0] ;
    item.updateElement(ZaAccount.getAccountTypeOutput.call(item, true)) ;
}

ZaNewAccountXWizard.cosGroupItemId = "cos_grouper_" + Dwt.getNextId();
ZaNewAccountXWizard.prototype.updateCosGrouper =
function () {
    var item = this._localXForm.getItemsById (ZaNewAccountXWizard.cosGroupItemId) [0] ;
    item.items[0].setElementEnabled(true);
    item.updateElement() ;
}

/*
ZaNewAccountXWizard.onNameFieldChanged = 
function (value, event, form) {
	if(value && value.length > 0) {
		form.parent._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(true);
	} else {
		form.parent._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
	}
	this.setInstanceValue(value);
	return value;
}*/

/**
* Overwritten methods that control wizard's flow (open, go next,go previous, finish)
**/
ZaNewAccountXWizard.prototype.popup = 
function (loc) {
	ZaXWizardDialog.prototype.popup.call(this, loc);
	this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
	this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
	this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(false);	
}

ZaNewAccountXWizard.prototype.createDomainAndAccount = function(domainName) {
	try {
		var newDomain = new ZaDomain();
		newDomain.name=domainName;
		newDomain.attrs[ZaDomain.A_domainName] = domainName;
		var domain = ZaItem.create(newDomain,ZaDomain,"ZaDomain");
		if(domain != null) {
			ZaApp.getInstance().getCurrentController().closeCnfrmDelDlg();
			this.finishWizard();
		}
	} catch(ex) {
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaNewAccountXWizard.prototype.createDomainAndAccount", null, false);	
	}
}

ZaNewAccountXWizard.prototype.finishWizard = 
function() {
	try {
        if(this._containedObject.attrs[ZaAccount.A_password]) {
            if(this._containedObject.attrs[ZaAccount.A_password] != this._containedObject[ZaAccount.A2_confirmPassword]) {
                ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_PASSWORD_MISMATCH);
                return false;
            }
        }
		
		if(!ZaAccount.checkValues(this._containedObject)) {
			return false;
		}
		var account = ZaItem.create(this._containedObject,ZaAccount,"ZaAccount");
		if(account != null) {
			//if creation took place - fire an change event
			ZaApp.getInstance().getAccountListController().fireCreationEvent(account);
			this.popdown();
            ZaApp.getInstance().getAppCtxt().getAppController().setActionStatusMsg(AjxMessageFormat.format(ZaMsg.AccountCreated,[account.name]));
			//ZaApp.getInstance().getCurrentController().popupMsgDialog(AjxMessageFormat.format(ZaMsg.AccountCreated,[account.name]));
		}
	} catch (ex) {
		switch(ex.code) {		
			case ZmCsfeException.ACCT_EXISTS:
				ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_ACCOUNT_EXISTS);
			break;
			case ZmCsfeException.NO_SUCH_COS:
				ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.ERROR_NO_SUCH_COS,[this._containedObject.attrs[ZaAccount.A_COSId]]), ex);
		    break;
			case ZmCsfeException.ACCT_INVALID_PASSWORD:
				ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_PASSWORD_INVALID, ex);
				ZaApp.getInstance().getAppCtxt().getErrorDialog().showDetail(true);
			break;
			case ZmCsfeException.NO_SUCH_DOMAIN:
				ZaApp.getInstance().dialogs["confirmMessageDialog2"].setMessage(AjxMessageFormat.format(ZaMsg.CreateDomain_q,[ZaAccount.getDomain(this._containedObject.name)]), DwtMessageDialog.WARNING_STYLE);
				ZaApp.getInstance().dialogs["confirmMessageDialog2"].registerCallback(DwtDialog.YES_BUTTON, this.createDomainAndAccount, this, [ZaAccount.getDomain(this._containedObject.name)]);		
				ZaApp.getInstance().dialogs["confirmMessageDialog2"].registerCallback(DwtDialog.NO_BUTTON, ZaController.prototype.closeCnfrmDelDlg, ZaApp.getInstance().getCurrentController(), null);				
				ZaApp.getInstance().dialogs["confirmMessageDialog2"].popup();  				
			break;
			default:
				ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaNewAccountXWizard.prototype.finishWizard", null, false);
			break;		
		}
	}
}

ZaNewAccountXWizard.prototype.goNext = 
function() {
	if (this._containedObject[ZaModel.currentStep] == 1) {
		//check if passwords match
		if(this._containedObject.attrs[ZaAccount.A_password]) {
			if(this._containedObject.attrs[ZaAccount.A_password] != this._containedObject[ZaAccount.A2_confirmPassword]) {
				ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_PASSWORD_MISMATCH);
				return false;
			}
		}
		//check if account exists
        if (ZaSearch.isAccountExist.call(this, {name: this._containedObject[ZaAccount.A_name], popupError: true})) {
            return false ;
        }
        this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
		
	} 
	this.goPage(this._containedObject[ZaModel.currentStep] + 1);
	if(this._containedObject[ZaModel.currentStep] == this._lastStep) {
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
	}	
}

ZaNewAccountXWizard.prototype.goPrev = 
function() {
	if (this._containedObject[ZaModel.currentStep] == 2) {
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(false);
        this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
	} else if(this._containedObject[ZaModel.currentStep] == this._lastStep) {
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
	}
	this.goPage(this._containedObject[ZaModel.currentStep] - 1);
}

/**
* @method setObject sets the object contained in the view
* @param entry - ZaAccount object to display
**/
ZaNewAccountXWizard.prototype.setObject =
function(entry) {
	this._containedObject = new ZaAccount();
	this._containedObject.attrs = new Object();

	for (var a in entry.attrs) {
		this._containedObject.attrs[a] = entry.attrs[a];
	}
	this._containedObject.name = entry.name || "";

    if(entry._uuid) {
        this._containedObject._uuid = entry._uuid;
    }

	if(entry.rights) {
		this._containedObject.rights = entry.rights;
	} else {
		this._containedObject.rights = [];
	}
	if(this._containedObject.rights[ZaAccount.RENAME_ACCOUNT_RIGHT] === undefined)
		this._containedObject.rights[ZaAccount.RENAME_ACCOUNT_RIGHT] = true; //since this is a new account, we should be able to give it a name
	
	if(entry.setAttrs)
		this._containedObject.setAttrs = entry.setAttrs;
	
	if(entry.getAttrs)
		this._containedObject.getAttrs = entry.getAttrs;
		
	if(entry._defaultValues)
		this._containedObject._defaultValues = entry._defaultValues;

	this._containedObject.id = entry.id || null;

	this.cosChoices.setChoices([this._containedObject.cos]);
	this.cosChoices.dirtyChoices();

    this._containedObject.attrs[ZaAccount.A_accountStatus] =  ZaAccount.ACCOUNT_STATUS_ACTIVE;
	this._containedObject[ZaAccount.A2_autodisplayname] = entry[ZaAccount.A2_autodisplayname] || "TRUE";
	this._containedObject[ZaAccount.A2_autoMailServer] = entry[ZaAccount.A2_autoMailServer] || "TRUE";
	this._containedObject[ZaAccount.A2_autoCos] = entry[ZaAccount.A2_autoCos] || "TRUE";
	this._containedObject[ZaAccount.A2_confirmPassword] = entry[ZaAccount.A2_confirmPassword] || null;
	this._containedObject[ZaModel.currentStep] = entry[ZaModel.currentStep] || 1;
	this._containedObject.attrs[ZaAccount.A_zmailMailAlias] = entry.attrs[ZaAccount.A_zmailMailAlias] || new Array();
	this._containedObject[ZaAccount.A2_errorMessage] = entry[ZaAccount.A2_errorMessage] || "";
	var domainName;
	if(!domainName) {
		//find out what is the default domain
		try {
			domainName = ZaApp.getInstance().getGlobalConfig().attrs[ZaGlobalConfig.A_zmailDefaultDomainName];
		} catch (ex) {
			if(ex.code != ZmCsfeException.SVC_PERM_DENIED) {
				throw(ex);
			}
		} 

	}
	//this._containedObject.globalConfig = ZaApp.getInstance().getGlobalConfig();
	 
	if(!domainName) {
		domainName =  ZaSettings.myDomainName;
	}
	this._containedObject[ZaAccount.A_name] = "@" + domainName;
    if (entry[ZaAccount.A_name])
        this._containedObject[ZaAccount.A_name] = entry[ZaAccount.A_name];
	EmailAddr_XFormItem.domainChoices.setChoices([]);
	EmailAddr_XFormItem.domainChoices.dirtyChoices();

    var domainName = ZaAccount.getDomain (this._containedObject.name) ;
    try {
   	 	var domainObj = ZaDomain.getDomainByName(domainName) ;
    	this._containedObject[ZaAccount.A2_accountTypes] = domainObj.getAccountTypes () ;
    } catch (ex) {
    	if(ex.code == ZmCsfeException.SVC_PERM_DENIED) {
    		this._containedObject[ZaAccount.A2_errorMessage] = AjxMessageFormat.format(ZaMsg.CANNOT_CREATE_ACCOUNTS_IN_THIS_DOMAIN,[domainName]);
    		//ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(ZaMsg.CANNOT_CREATE_ACCOUNTS_IN_THIS_DOMAIN,[domainName])	, ex);
    	} else {	
    		this._containedObject[ZaAccount.A2_errorMessage] = "";
	 		throw(ex);
		}
    }

    this._containedObject[ZaAccount.A2_memberOf] = ZaAccountMemberOfListView.cloneMemberOf(entry);

    //add the memberList page information
	this._containedObject[ZaAccount.A2_directMemberList + "_offset"] = entry[ZaAccount.A2_directMemberList + "_offset"];
	this._containedObject[ZaAccount.A2_directMemberList + "_more"] = entry[ZaAccount.A2_directMemberList + "_more"];
	this._containedObject[ZaAccount.A2_indirectMemberList + "_offset"] = entry[ZaAccount.A2_indirectMemberList + "_offset"];
	this._containedObject[ZaAccount.A2_indirectMemberList + "_more"] = entry[ZaAccount.A2_indirectMemberList + "_more"];
	this._containedObject[ZaAccount.A2_nonMemberList + "_offset"] = entry[ZaAccount.A2_nonMemberList + "_offset"];
	this._containedObject[ZaAccount.A2_nonMemberList + "_more"] = entry[ZaAccount.A2_nonMemberList + "_more"];
	if(entry.getAttrs[ZaAccount.A_zmailAvailableSkin] || entry.getAttrs.all) {
		var skins = ZaApp.getInstance().getInstalledSkins();
		
		if(AjxUtil.isEmpty(skins)) {
			if(entry._defaultValues && entry._defaultValues.attrs && !AjxUtil.isEmpty(entry._defaultValues.attrs[ZaAccount.A_zmailAvailableSkin])) {
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
		ZaNewAccountXWizard.themeChoices.setChoices(skinsChoices);
		ZaNewAccountXWizard.themeChoices.dirtyChoices();		
		
	}	

	if(entry.getAttrs[ZaAccount.A_zmailZimletAvailableZimlets] || entry.getAttrs.all) {
		//get sll Zimlets
		var allZimlets = ZaZimlet.getAll("extension");

		if(!AjxUtil.isEmpty(allZimlets) && allZimlets instanceof ZaItemList || allZimlets instanceof AjxVector)
			allZimlets = allZimlets.getArray();

		if(AjxUtil.isEmpty(allZimlets)) {
			
			if(entry._defaultValues && entry._defaultValues.attrs && !AjxUtil.isEmpty(entry._defaultValues.attrs[ZaAccount.A_zmailZimletAvailableZimlets])) {
				allZimlets = entry._defaultValues.attrs[ZaAccount.A_zmailZimletAvailableZimlets];
			} else {
				allZimlets = [];
			}
			ZaNewAccountXWizard.zimletChoices.setChoices(allZimlets);
			ZaNewAccountXWizard.zimletChoices.dirtyChoices();
			
		} else {
			//convert objects to strings	
			var cnt = allZimlets.length;
			var _tmpZimlets = [];
			for(var i=0; i<cnt; i++) {
				var zimlet = allZimlets[i];
				_tmpZimlets.push(zimlet.name);
			}
			ZaNewAccountXWizard.zimletChoices.setChoices(_tmpZimlets);
			ZaNewAccountXWizard.zimletChoices.dirtyChoices();
		}
	}	

    if (domainObj && domainObj.attrs &&
        domainObj.attrs[ZaDomain.A_AuthMech] &&
        (domainObj.attrs[ZaDomain.A_AuthMech] != ZaDomain.AuthMech_zmail) ) {
        this._containedObject[ZaAccount.A2_isExternalAuth] = true;
    } else {
        this._containedObject[ZaAccount.A2_isExternalAuth] = false;
    }

    //check the account type here
    this._localXForm.setInstance(this._containedObject);
    var nameFields = this._localXForm.getItemsById(ZaAccount.A_name);
    if(!AjxUtil.isEmpty(nameFields) && nameFields[0] && nameFields[0].resetEditedState)
		nameFields[0].resetEditedState();
}

ZaNewAccountXWizard.isAuthfromInternal =
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


ZaNewAccountXWizard.isDomainLeftAccountsAlertVisible = function () {
	var val1 = this.getInstanceValue(ZaAccount.A2_domainLeftAccounts);
	var val2 = this.getInstanceValue(ZaAccount.A2_accountTypes);
	return (!AjxUtil.isEmpty(val1) && AjxUtil.isEmpty(val2));
}

ZaNewAccountXWizard.isAccountsTypeAlertInvisible = function () {
        var val = this.getInstanceValue(ZaAccount.A2_showAccountTypeMsg);
        return (AjxUtil.isEmpty(val));
}

ZaNewAccountXWizard.isAccountTypeGrouperVisible = function () {
	return !AjxUtil.isEmpty(this.getInstanceValue(ZaAccount.A2_accountTypes));
}

ZaNewAccountXWizard.isAccountTypeSet = function () {
	return !ZaAccount.isAccountTypeSet(this.getInstance());
}

ZaNewAccountXWizard.isAutoDisplayname = function () {
	return (this.getInstanceValue(ZaAccount.A2_autodisplayname)=="FALSE");
}

ZaNewAccountXWizard.isAutoCos = function () {
	return (this.getInstanceValue(ZaAccount.A2_autoCos)=="FALSE");
}


ZaNewAccountXWizard.isIMFeatureEnabled = function () {
	return (this.getInstanceValue(ZaAccount.A_zmailFeatureIMEnabled) == "TRUE");
}

ZaNewAccountXWizard.isContactsFeatureEnabled = function () {
    return this.getInstanceValue(ZaAccount.A_zmailFeatureDistributionListFolderEnabled) == "TRUE";
}

ZaNewAccountXWizard.isCalendarFeatureEnabled = function () {
	return this.getInstanceValue(ZaAccount.A_zmailFeatureCalendarEnabled)=="TRUE";
}

ZaNewAccountXWizard.isMailForwardingEnabled = function () {
	return (this.getInstanceValue(ZaAccount.A_zmailFeatureMailForwardingEnabled) == "TRUE");
}

ZaNewAccountXWizard.onCOSChanged = 
function(value, event, form) {
	if(ZaItem.ID_PATTERN.test(value))  {
		form.getInstance()._defaultValues = ZaCos.getCosById(value, form.parent._app);
		this.setInstanceValue(value);
	} else {
		form.getInstance()._defaultValues = ZaCos.getCosByName(value, form.parent._app);
		if(form.getInstance().cos) {
			//value = form.getInstance()._defaultValues.id;
			value = form.getInstance()._defaultValues.id;
		} 
	}
	this.setInstanceValue(value);
    form.parent._isCosChanged = true ;

    //if cos is changed,  update the account type information
    form.parent.updateAccountType();
    
    return value;
}

ZaNewAccountXWizard.getAccountNameInfoItem = function(){
	if(AjxUtil.isEmpty(ZaNewAccountXWizard.accountNameInfoPool)){
		ZaNewAccountXWizard.accountNameInfoPool = new Object();
		ZaNewAccountXWizard.accountNameInfoPool[ZaAccount.A_name] = {ref:ZaAccount.A_name, type:_EMAILADDR_,
					 msgName:ZaMsg.NAD_AccountName,label:ZaMsg.NAD_AccountName, bmolsnr:false,
                                        domainPartWidth:"100%",
                                        labelLocation:_LEFT_,onChange:ZaAccount.setDomainChanged,forceUpdate:true,
                                        enableDisableChecks:[],
                                        visibilityChecks:[]
                                },
		ZaNewAccountXWizard.accountNameInfoPool[ZaAccount.A_firstName] = {ref:ZaAccount.A_firstName, type:_TEXTFIELD_,
					msgName:ZaMsg.NAD_FirstName,label:ZaMsg.NAD_FirstName,
					labelLocation:_LEFT_, cssClass:"admin_xform_name_input", width:150,
					elementChanged: function(elementValue,instanceValue, event) {
						if(this.getInstance()[ZaAccount.A2_autodisplayname]=="TRUE") {
							ZaAccount.generateDisplayName.call(this, this.getInstance(), elementValue, this.getInstance().attrs[ZaAccount.A_lastName],this.getInstance().attrs[ZaAccount.A_initials] );
						}
						this.getForm().itemChanged(this, elementValue, event);
					}
				};
		ZaNewAccountXWizard.accountNameInfoPool[ZaAccount.A_initials] = {ref:ZaAccount.A_initials, type:_TEXTFIELD_,
					msgName:ZaMsg.NAD_Initials,label:ZaMsg.NAD_Initials, labelLocation:_LEFT_,
					cssClass:"admin_xform_name_input", width:50,
					elementChanged: function(elementValue,instanceValue, event) {
						if(this.getInstance()[ZaAccount.A2_autodisplayname]=="TRUE") {
							ZaAccount.generateDisplayName.call(this, this.getInstance(), this.getInstanceValue(ZaAccount.A_firstName), this.getInstanceValue(ZaAccount.A_lastName),elementValue);
						}
						this.getForm().itemChanged(this, elementValue, event);
					}
				};
		ZaNewAccountXWizard.accountNameInfoPool[ZaAccount.A_lastName] = {ref:ZaAccount.A_lastName, type:_TEXTFIELD_,
					msgName:ZaMsg.NAD_LastName,label:ZaMsg.NAD_LastName, labelLocation:_LEFT_,
					cssClass:"admin_xform_name_input", width:150,
					elementChanged: function(elementValue,instanceValue, event) {
						if(this.getInstance()[ZaAccount.A2_autodisplayname]=="TRUE") {
							ZaAccount.generateDisplayName.call(this, this.getInstance(),  this.getInstanceValue(ZaAccount.A_firstName), elementValue ,this.getInstanceValue(ZaAccount.A_initials));
						}
						this.getForm().itemChanged(this, elementValue, event);
					}
				};
		ZaNewAccountXWizard.accountNameInfoPool["ZaAccountDisplayInfoGroup"] = {type:_GROUP_, numCols:3, nowrap:true,
					width:200, msgName:ZaMsg.NAD_DisplayName,label:ZaMsg.NAD_DisplayName, labelLocation:_LEFT_,
                                        visibilityChecks:[[ZaItem.hasReadPermission,ZaAccount.A_displayname]],
                                        items: [
                                                {ref:ZaAccount.A_displayname, type:_TEXTFIELD_, label:null,     cssClass:"admin_xform_name_input", width:150,
                                                        enableDisableChecks:[ [XForm.checkInstanceValue,ZaAccount.A2_autodisplayname,"FALSE"],ZaItem.hasWritePermission],
                                                        enableDisableChangeEventSources:[ZaAccount.A2_autodisplayname],bmolsnr:true,
                                                        visibilityChecks:[]
                                                },
                                                {ref:ZaAccount.A2_autodisplayname, type:_WIZ_CHECKBOX_, msgName:ZaMsg.NAD_Auto,label:ZaMsg.NAD_Auto,labelLocation:_RIGHT_,trueValue:"TRUE", falseValue:"FALSE", subLabel:"",
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
		ZaNewAccountXWizard.accountNameInfoPool[ZaAccount.A_zmailHideInGal]={ref:ZaAccount.A_zmailHideInGal, type:_WIZ_CHECKBOX_,
				  			msgName:ZaMsg.LBL_zmailHideInGal, subLabel:"", labelLocation:_RIGHT_,align:_RIGHT_,
				  			label:ZaMsg.LBL_zmailHideInGal, trueValue:"TRUE", falseValue:"FALSE"
				},
		ZaNewAccountXWizard.accountNameInfoPool[ZaAccount.A_zmailPhoneticFirstName] = {
					ref:ZaAccount.A_zmailPhoneticFirstName, type:_TEXTFIELD_,
					msgName:ZaMsg.NAD_zmailPhoneticFirstName,label:ZaMsg.NAD_zmailPhoneticFirstName,
                                        labelLocation:_LEFT_, cssClass:"admin_xform_name_input",width:150
                                };
		ZaNewAccountXWizard.accountNameInfoPool[ZaAccount.A_zmailPhoneticLastName] = {
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
                accountNameFormItems.push(ZaNewAccountXWizard.accountNameInfoPool[accountNameItemsOrders[i]]);
        }
        return accountNameFormItems;
}

ZaNewAccountXWizard.myXFormModifier = function(xFormObject, entry) {	
	var domainName = ZaSettings.myDomainName;

	var emptyAlias = "@" + domainName;
	var cases = new Array();

	this.stepChoices = [];
	this.TAB_INDEX = 0;	
	ZaNewAccountXWizard.GENERAL_STEP = ++this.TAB_INDEX;
	this.stepChoices.push({value:ZaNewAccountXWizard.GENERAL_STEP, label:ZaMsg.TABT_GeneralPage});
	this.cosChoices = new XFormChoices([], XFormChoices.OBJECT_LIST, "id", "name");
	var case1 = {type:_CASE_, tabGroupKey:ZaNewAccountXWizard.GENERAL_STEP, caseKey:ZaNewAccountXWizard.GENERAL_STEP, numCols:1,  align:_LEFT_, valign:_TOP_};
	var case1Items = [ 
		 {type: _DWT_ALERT_, ref: ZaAccount.A2_domainLeftAccounts,
                visibilityChecks:[ZaNewAccountXWizard.isDomainLeftAccountsAlertVisible],
                visibilityChangeEventSources:[ZaAccount.A2_domainLeftAccounts,ZaAccount.A2_accountTypes, ZaAccount.A_name],
			    bmolsnr:true,	
                containerCssStyle: "width:400px;",
				style: DwtAlert.WARNING, iconVisible: false
		 },
		{type: _DWT_ALERT_, ref: ZaAccount.A2_warningMessage,
                visibilityChecks:[[XForm.checkInstanceValueNotEmty,ZaAccount.A2_warningMessage]],
                visibilityChangeEventSources:[ZaAccount.A2_warningMessage],
                bmolsnr:true,
				containerCssStyle: "width:400px;",
				style: DwtAlert.WARNING, iconVisible: false
		 },
		{type: _DWT_ALERT_, ref: ZaAccount.A2_errorMessage,
                visibilityChecks:[[XForm.checkInstanceValueNotEmty,ZaAccount.A2_errorMessage]],
                visibilityChangeEventSources:[ZaAccount.A2_errorMessage],
                bmolsnr:true,
				containerCssStyle: "width:400px;",
				style: DwtAlert.CRITICAL, iconVisible: false
		 },
        //account types group
        {type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_AccountTypeGrouper, id:"account_wiz_type_group",
                colSpan: "*", numCols: 1, colSizes: ["100%"],
                visibilityChecks:[ZaNewAccountXWizard.isAccountTypeGrouperVisible,ZaAccount.isShowAccountType],
                visibilityChangeEventSources:[ZaAccount.A2_accountTypes,ZaAccount.A_COSId,ZaAccount.A_name, ZaAccount.A2_showAccountTypeMsg],
                items: [
                    {type: _DWT_ALERT_, 
                    	visibilityChecks:[ZaNewAccountXWizard.isAccountTypeSet, ZaNewAccountXWizard.isAccountsTypeAlertInvisible],
                        visibilityChangeEventSources:[ZaAccount.A2_accountTypes,ZaAccount.A_COSId, ZaAccount.A_name, ZaAccount.A2_showAccountTypeMsg],
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
                    { type: _OUTPUT_, id: ZaNewAccountXWizard.accountTypeItemId,
                        getDisplayValue: ZaAccount.getAccountTypeOutput,
                        valueChangeEventSources:[ZaAccount.A_name,ZaAccount.A_COSId,ZaAccount.A2_accountTypes,ZaAccount.A2_currentAccountType],
                        //center the elements
                        cssStyle: "margin-left:auto;margin-right:auto;width:600px;"
                    }
               ]
        },
        {type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_AccountNameGrouper, id:"account_wiz_name_group",numCols:2,
			items:ZaNewAccountXWizard.getAccountNameInfoItem(),
                displayLabelItem: true, headerLabelWidth:"100px",
                headerItems:[
                    {ref:ZaAccount.A_name, type:_EMAILADDR_,
					 msgName:ZaMsg.NAD_AccountName,
                                        labelLocation:_LEFT_,onChange:ZaAccount.setDomainChanged,forceUpdate:true,
                                        enableDisableChecks:[[ZaItem.hasRight,ZaAccount.RENAME_ACCOUNT_RIGHT]],
                                        visibilityChecks:[]
                     }
                ]
		}
	];
	if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry, 
		[ZaAccount.A_accountStatus, ZaAccount.A_COSId, ZaAccount.A_zmailIsAdminAccount,ZaAccount.A_mailHost],[])) {
		var setupGroup = {type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_AccountSetupGrouper, id:"account_wiz_setup_group", 
			numCols:2,colSizes:["200px","400px"],
			items: [
				{ref:ZaAccount.A_accountStatus, type:_OSELECT1_, msgName:ZaMsg.NAD_AccountStatus,
					label:ZaMsg.NAD_AccountStatus, 
					labelLocation:_LEFT_, choices:this.accountStatusChoices
				}
			],
            headerItems: [
                    {ref:ZaAccount.A_accountStatus, type:_OSELECT1_,
                        bmolsnr:true,
                        labelLocation:_LEFT_, choices:this.accountStatusChoices
                    }
            ], displayLabelItem: true, headerLabelWidth:"100px"
		}
		

		setupGroup.items.push(
			{type:_GROUP_, numCols:3, nowrap:true, label:ZaMsg.NAD_ClassOfService, labelLocation:_LEFT_,
				visibilityChecks:[[ZaItem.hasWritePermission,ZaAccount.A_COSId]],
				id: ZaNewAccountXWizard.cosGroupItemId,
				items: [
					{ref:ZaAccount.A_COSId, type:_DYNSELECT_,label: null, 
						inputPreProcessor:ZaAccountXFormView.preProcessCOS,
						toolTipContent:ZaMsg.tt_StartTypingCOSName,
						onChange:ZaAccount.setCosChanged,
						onClick:ZaController.showTooltip,
						emptyText:ZaMsg.enterSearchTerm,						
						enableDisableChecks:[[ZaNewAccountXWizard.isAutoCos], [ZaItem.hasWritePermission,ZaAccount.A_COSId]],
						enableDisableChangeEventSources:[ZaAccount.A2_autoCos],
						visibilityChecks:[],
						dataFetcherMethod:ZaSearch.prototype.dynSelectSearchCoses,
						choices:this.cosChoices,
						dataFetcherClass:ZaSearch,
						editable:true,
						getDisplayValue:function(newValue) {
							// dereference through the choices array, if provided
							//newValue = this.getChoiceLabel(newValue);
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
						}
					},
					{ref:ZaAccount.A2_autoCos, type:_WIZ_CHECKBOX_,
						msgName:ZaMsg.NAD_Auto,label:ZaMsg.NAD_Auto,labelLocation:_RIGHT_,
						trueValue:"TRUE", falseValue:"FALSE" , subLabel:"",
						elementChanged: function(elementValue,instanceValue, event) {
							if(elementValue=="TRUE") {
								ZaAccount.setDefaultCos(this.getInstance(), this.getForm().parent._app);	
							}
							this.getForm().itemChanged(this, elementValue, event);
						},
                        enableDisableChecks:[ [ZaItem.hasWritePermission,ZaAccount.A_COSId]],
						visibilityChecks:[]
					}
				]
			});
	
		setupGroup.items.push({ref:ZaAccount.A_zmailIsAdminAccount, type:_WIZ_CHECKBOX_, labelLocation:_RIGHT_,align:_RIGHT_,subLabel:"",
								visibilityChecks:[[ZaItem.hasWritePermission,ZaAccount.A_zmailIsAdminAccount]],
								msgName:ZaMsg.NAD_IsSystemAdminAccount,label:ZaMsg.NAD_IsSystemAdminAccount,
								bmolsnr:true, trueValue:"TRUE", falseValue:"FALSE"
							});
						
		setupGroup.items.push({type:_GROUP_, numCols:3, nowrap:true, label:ZabMsg.attrDesc_mailHost, labelLocation:_LEFT_,
							visibilityChecks:[[ZaItem.hasWritePermission,ZaAccount.A_mailHost]],
							items: [
								{ ref: ZaAccount.A_mailHost, type: _OSELECT1_, label: null, editable:false, choices: ZaApp.getInstance().getServerListChoices(), 
									enableDisableChecks:[ZaAccount.isAutoMailServer],
									enableDisableChangeEventSources:[ZaAccount.A2_autoMailServer],
									visibilityChecks:[],
									tableCssStyle: "height:15px;"
							  	},
								{ref:ZaAccount.A2_autoMailServer, type:_WIZ_CHECKBOX_, msgName:ZaMsg.NAD_Auto,label:ZaMsg.NAD_Auto,labelLocation:_RIGHT_,trueValue:"TRUE", falseValue:"FALSE",
									visibilityChecks:[], labelLocation:_RIGHT_,align:_RIGHT_, subLabel:"",
									enableDisableChecks:[]
								}
							]
						});
						
		case1Items.push(setupGroup);
	}	
	var passwordGroup = {type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_PasswordGrouper,id:"account_wiz_password_group", 
		numCols:2,visibilityChecks:[
            [ZaItem.adminHasAnyRight,[ZaAccount.SET_PASSWORD_RIGHT, ZaAccount.CHANGE_PASSWORD_RIGHT]],
            [XForm.checkInstanceValueNot,ZaAccount.A2_isExternalAuth,true]],
        visibilityChangeEventSources:[ZaAccount.A2_isExternalAuth],
		items:[
               	{ type: _DWT_ALERT_, containerCssStyle: "padding-bottom:0;",
                        style: DwtAlert.WARNING,iconVisible: false, 
                        content: ZaMsg.Alert_InternalPassword
                },
		{ref:ZaAccount.A_password, type:_SECRET_, msgName:ZaMsg.NAD_Password,
			label:ZaMsg.NAD_Password, labelLocation:_LEFT_, 
			visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]], 
			cssClass:"admin_xform_name_input"
		},
		{ref:ZaAccount.A2_confirmPassword, type:_SECRET_, msgName:ZaMsg.NAD_ConfirmPassword,
			label:ZaMsg.NAD_ConfirmPassword, labelLocation:_LEFT_,
			visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]],  
			cssClass:"admin_xform_name_input"
		},
		{ref:ZaAccount.A_zmailPasswordMustChange,  type:_WIZ_CHECKBOX_, labelLocation:_RIGHT_,align:_RIGHT_, subLabel:"",
			msgName:ZaMsg.NAD_MustChangePwd,label:ZaMsg.NAD_MustChangePwd,trueValue:"TRUE", falseValue:"FALSE",
			visibilityChecks:[], enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
		}
		]
	};
	case1Items.push(passwordGroup);

    var externalAuthGroup = {type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_ExternalAuthGrouper,id:"account_wiz_ext_auth_group",
        numCols:2,
        visibilityChecks:[
            [XForm.checkInstanceValue,ZaAccount.A2_isExternalAuth,true],
            [ZaItem.hasReadPermission,ZaAccount.A_zmailAuthLdapExternalDn]],
        visibilityChangeEventSources:[ZaAccount.A2_isExternalAuth],
        items:[
            {ref:ZaAccount.A_zmailAuthLdapExternalDn,type:_TEXTFIELD_,width:256,
                msgName:ZaMsg.NAD_AuthLdapExternalDn,label:ZaMsg.NAD_AuthLdapExternalDn, labelLocation:_LEFT_,
                align:_LEFT_, toolTipContent: ZaMsg.tt_AuthLdapExternalDn
            }
        ]
    };
    case1Items.push(externalAuthGroup);

    var new_acct_timezone_group = {
         type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_TimezoneGrouper, id: "account_wiz_timezone_group",
         visibilityChecks:[[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefTimeZoneId]],
         numCols: 2,
         items: [
                   /*
                {ref:"default_timezone", type:_CHECKBOX_, msgName:"default",
                    label:ZaMsg.NAD_Auto,labelLocation:_RIGHT_,trueValue:"TRUE", falseValue:"FALSE",
                    elementChanged: function(elementValue,instanceValue, event) {
                        if(elementValue=="TRUE") {
                            ZaAccount.generateDisplayName(this.getInstance(), this.getInstance().attrs[ZaAccount.A_firstName], this.getInstance().attrs[ZaAccount.A_lastName],this.getInstance().attrs[ZaAccount.A_initials]);
                        }
                        this.getForm().itemChanged(this, elementValue, event);
                    }
              }     */
            {ref:ZaAccount.A_zmailPrefTimeZoneId, type:_SELECT1_, msgName:ZaMsg.LBL_zmailPrefTimeZoneId,
                 label:ZaMsg.LBL_zmailPrefTimeZoneId, labelLocation:_LEFT_ }
         ]
    }

    case1Items.push (new_acct_timezone_group) ;
    

	if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_description,ZaAccount.A_notes],[])) {
	    var notesGroup = {type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_NotesGrouper, id:"account_wiz_notes_group",
			
			numCols:2,
		 	items:[
				{ref:ZaAccount.A_description, type:_INPUT_, msgName:ZaMsg.NAD_Description,
					label:ZaMsg.NAD_Description, labelLocation:_LEFT_, cssClass:"admin_xform_name_input",
					 visibilityChecks:[[ZaItem.hasWritePermission,ZaAccount.A_description]],
					 enableDisableChecks:[]
				},
				{ref:ZaAccount.A_notes, type:_TEXTAREA_, msgName:ZaMsg.NAD_Notes,
					label:ZaMsg.NAD_Notes, labelLocation:_LEFT_, labelCssStyle:"vertical-align:top;", width:"30em",
					visibilityChecks:[[ZaItem.hasWritePermission,ZaAccount.A_notes]],
					enableDisableChecks:[]
				}
			]
		};
	
		case1Items.push(notesGroup);
	}
	case1.items = case1Items;
	cases.push(case1);

	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.CONTACT_TAB_ATTRS, ZaAccountXFormView.CONTACT_TAB_RIGHTS) &&
        !ZaSettings.isOctopus()) {
		ZaNewAccountXWizard.CONTACT_STEP = ++this.TAB_INDEX;
		this.stepChoices.push({value:ZaNewAccountXWizard.CONTACT_STEP, label:ZaMsg.TABT_ContactInfo});
		var case2={type:_CASE_, caseKey:ZaNewAccountXWizard.CONTACT_STEP, tabGroupKey:ZaNewAccountXWizard.CONTACT_STEP, numCols:1, 
						items: [
							{type:_ZAWIZGROUP_, 
								items:[
									{ref:ZaAccount.A_telephoneNumber, type:_TEXTFIELD_, msgName:ZaMsg.NAD_telephoneNumber,label:ZaMsg.NAD_telephoneNumber, labelLocation:_LEFT_, width:250},
                                    {ref:ZaAccount.A_homePhone, type:_TEXTFIELD_, msgName:ZaMsg.NAD_homePhone,label:ZaMsg.NAD_homePhone, labelLocation:_LEFT_, width:250} ,
                                    {ref:ZaAccount.A_mobile, type:_TEXTFIELD_, msgName:ZaMsg.NAD_mobile,label:ZaMsg.NAD_mobile, labelLocation:_LEFT_, width:250} ,
                                    {ref:ZaAccount.A_pager, type:_TEXTFIELD_, msgName:ZaMsg.NAD_pager,label:ZaMsg.NAD_pager, labelLocation:_LEFT_, width:250} ,
                                    {ref:ZaAccount.A_facsimileTelephoneNumber, type:_TEXTFIELD_, msgName:ZaMsg.NAD_facsimileTelephoneNumber,label:ZaMsg.NAD_facsimileTelephoneNumber, labelLocation:_LEFT_, width:250}
								]
							},
							{type:_ZAWIZGROUP_, 
								items:[	
									{ref:ZaAccount.A_zmailPhoneticCompany, type:_TEXTFIELD_, msgName:ZaMsg.NAD_zmailPhoneticCompany, label:ZaMsg.NAD_zmailPhoneticCompany, labelLocation:_LEFT_, width:250, visibilityChecks:[[ZaZmailAdmin.isLanguage, "ja"]]},				
									{ref:ZaAccount.A_company, type:_TEXTFIELD_, msgName:ZaMsg.NAD_company,label:ZaMsg.NAD_company, labelLocation:_LEFT_, width:250} ,
									{ref:ZaAccount.A_title,  type:_TEXTFIELD_, msgName:ZaMsg.NAD_title,label:ZaMsg.NAD_title, labelLocation:_LEFT_, width:250}
									/*,
									{ref:ZaAccount.A_orgUnit, type:_TEXTFIELD_, msgName:ZaMsg.NAD_orgUnit,label:ZaMsg.NAD_orgUnit, labelLocation:_LEFT_, width:250},														
									{ref:ZaAccount.A_office, type:_TEXTFIELD_, msgName:ZaMsg.NAD_office,label:ZaMsg.NAD_office, labelLocation:_LEFT_, width:250}    */
								]
							},
							{type:_ZAWIZGROUP_, 
								items:ZaAccountXFormView.getAddressFormItemForDialog()
							}							
						]
					};
		cases.push(case2);
	}
	
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.ALIASES_TAB_ATTRS, ZaAccountXFormView.ALIASES_TAB_RIGHTS)) {
		ZaNewAccountXWizard.ALIASES_STEP = ++this.TAB_INDEX;
		this.stepChoices.push({value:ZaNewAccountXWizard.ALIASES_STEP, label:ZaMsg.TABT_Aliases});
		cases.push({type:_CASE_, tabGroupKey:ZaNewAccountXWizard.ALIASES_STEP, caseKey:ZaNewAccountXWizard.ALIASES_STEP, numCols:1, 
					items: [
						{type:_OUTPUT_, value:ZaMsg.NAD_AccountAliases},
						{ref:ZaAccount.A_zmailMailAlias, type:_REPEAT_, label:null, repeatInstance:emptyAlias, showAddButton:true, 
							showRemoveButton:true, 
							addButtonLabel:ZaMsg.NAD_AddAlias, 
							showAddOnNextRow:true,
							removeButtonLabel:ZaMsg.NAD_RemoveAlias,
							removeButtonCSSStyle: "margin-left:50px;",
							visibilityChecks:[
								[ZaItem.hasWritePermission, ZaAccount.A_zmailMailAlias]
							],
							items: [
								{ref:".", type:_EMAILADDR_, label:null, enableDisableChecks:[], 
									visibilityChecks:[
										[ZaItem.hasWritePermission, ZaAccount.A_zmailMailAlias]
									]
								}
							]
						}
					]
				});								
	}

    if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.MEMBEROF_TAB_ATTRS, ZaAccountXFormView.MEMBEROF_TAB_RIGHTS)) {
        var directMemberOfHeaderList = new ZaAccountMemberOfsourceHeaderList(ZaAccountMemberOfsourceHeaderList.DIRECT);
	    var indirectMemberOfHeaderList = new ZaAccountMemberOfsourceHeaderList(ZaAccountMemberOfsourceHeaderList.INDIRECT, 150);
	    var nonMemberOfHeaderList = new ZaAccountMemberOfsourceHeaderList(ZaAccountMemberOfsourceHeaderList.NON);
        ZaNewAccountXWizard.MEMBEROF_STEP = ++this.TAB_INDEX;
        this.stepChoices.push({value:ZaNewAccountXWizard.MEMBEROF_STEP, label:ZaMsg.TABT_MemberOf});
        var memberofCase = {type:_CASE_, caseKey:ZaNewAccountXWizard.MEMBEROF_STEP, tabGroupKey:ZaNewAccountXWizard.MEMBEROF_STEP,
                            numCols:2, colSizes: ["50%","50%"], id:"memberof_step",
                            items: [
                                //layout rapper around the direct/indrect list
                                {type: _GROUP_, width: "98%", numCols: 1, //colSizes: ["auto", 20],
                                    items: [
                                        //direct member group
                                        {type:_ZALEFT_GROUPER_, numCols:1, width: "100%",
                                            label:ZaMsg.Account_DirectGroupLabel,
                                            containerCssStyle: "padding-top:5px;",
                                            items:[
                                                {ref: ZaAccount.A2_directMemberList, type: _S_DWT_LIST_, width: "98%", height: 208,
                                                    cssClass: "DLSource", widgetClass: ZaAccountMemberOfListView,
                                                    headerList: directMemberOfHeaderList, defaultColumnSortable: 0,
                                                    onSelection:ZaAccountXFormView.directMemberOfSelectionListener,
                                                    forceUpdate: true }	,
                                                {type:_SPACER_, height:"5"},
                                                {type:_GROUP_, width:"100%", numCols:8, colSizes:[90,5,90, "auto",30,5, 30,5],
                                                    items:[
                                                        {type:_DWT_BUTTON_, label:ZaMsg.DLXV_ButtonRemoveAll, width:90,
                                                            enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableAllButton,ZaAccount.A2_directMemberList]],
                                                            enableDisableChangeEventSources:[ZaAccount.A2_directMemberList],
                                                            onActivate:"ZaAccountMemberOfListView.removeAllGroups.call(this,event, ZaAccount.A2_directMemberList)"
                                                        },
                                                        {type:_CELLSPACER_, height:"100%"},
                                                        {type:_DWT_BUTTON_, label:ZaMsg.DLXV_ButtonRemove, width:90, id:"removeButton",
                                                            enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableAddRemoveButton,ZaAccount.A2_directMemberList]],
                                                            enableDisableChangeEventSources:[ZaAccount.A2_directMemberListSelected],
                                                            onActivate:"ZaAccountMemberOfListView.removeGroups.call(this,event, ZaAccount.A2_directMemberList)"
                                                        },
                                                        {type:_CELLSPACER_,height:"100%"},
                                                        {type:_DWT_BUTTON_, label:"", width:30, id:"backButton", icon:"LeftArrow", disIcon:"LeftArrowDis",
                                                            onActivate:"ZaAccountMemberOfListView.backButtonHndlr.call(this,event, ZaAccount.A2_directMemberList)",
                                                            enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableBackButton,ZaAccount.A2_directMemberList]],
                                                            enableDisableChangeEventSources:[ZaAccount.A2_directMemberList +"_offset"]
                                                        },
                                                        {type:_CELLSPACER_, height:"100%"},
                                                        {type:_DWT_BUTTON_, label:"", width:30, id:"fwdButton", icon:"RightArrow", disIcon:"RightArrowDis",
                                                            onActivate:"ZaAccountMemberOfListView.fwdButtonHndlr.call(this,event, ZaAccount.A2_directMemberList)",
                                                            enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableForwardButton,ZaAccount.A2_directMemberList]],
                                                            enableDisableChangeEventSources:[ZaAccount.A2_directMemberList + "_more"]
                                                        },
                                                        {type:_CELLSPACER_, height:"100%"}
                                                    ]
                                                }
                                            ]
                                        },
                                        {type:_SPACER_, height:"5"},
                                        //indirect member group
                                        {type:_ZALEFT_GROUPER_, numCols:1,  width: "100%", label:ZaMsg.Account_IndirectGroupLabel,
                                            containerCssStyle: "padding-top:5px;",
                                            items:[
                                                {ref: ZaAccount.A2_indirectMemberList, type: _S_DWT_LIST_, width: "98%", height: 208,
                                                    cssClass: "DLSource", widgetClass: ZaAccountMemberOfListView,
                                                    headerList: indirectMemberOfHeaderList, defaultColumnSortable: 0,
                                                    onSelection:ZaAccountXFormView.indirectMemberOfSelectionListener,
                                                    forceUpdate: true }	,
                                                {type:_SPACER_, height:"5"},
                                                {type:_GROUP_, width:"100%", numCols:5, colSizes:["auto",30,5,30,5],
                                                    items:[
                                                        {type:_CELLSPACER_, height:"100%"},
                                                        {type:_DWT_BUTTON_, label:"", width:30, id:"backButton", icon:"LeftArrow", disIcon:"LeftArrowDis",
                                                            onActivate:"ZaAccountMemberOfListView.backButtonHndlr.call(this,event, ZaAccount.A2_indirectMemberList)",
                                                            enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableBackButton,ZaAccount.A2_indirectMemberList]],
                                                            enableDisableChangeEventSources:[ZaAccount.A2_indirectMemberList + "_offset"]
                                                        },
                                                        {type:_CELLSPACER_, height:"100%"},
                                                        {type:_DWT_BUTTON_, label:"", width:30, id:"fwdButton", icon:"RightArrow", disIcon:"RightArrowDis",
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

                                {type: _GROUP_, width: "98%", numCols: 1, //colSizes: ["auto", 20],
					            items: [
                                    {type:_ZARIGHT_GROUPER_, numCols:1, width: "100%", label:ZaMsg.Account_NonGroupLabel,
                                        containerCssStyle: "padding-top:5px;",
                                        items:[
                                            {type:_GROUP_, numCols:3, colSizes:["40", "auto", "80"], width:"98%",
                                               items:[
                                                    {ref:"query", type:_TEXTFIELD_, width:"100%", cssClass:"admin_xform_name_input",
                                                        nowrap:false,labelWrap:true,
                                                        label:ZaMsg.DLXV_LabelFind,
                                                        visibilityChecks:[],enableDisableChecks:[],
                                                        elementChanged: function(elementValue,instanceValue, event) {
                                                          var charCode = event.charCode;
                                                          if (charCode == 13 || charCode == 3) {
                                                              ZaAccountMemberOfListView.prototype.srchButtonHndlr.call(this);
                                                          } else {
                                                              this.getForm().itemChanged(this, elementValue, event);
                                                          }
                                                        }
                                                    },
                                                    {type:_DWT_BUTTON_, label:ZaMsg.DLXV_ButtonSearch, width:80,
                                                       onActivate:ZaAccountMemberOfListView.prototype.srchButtonHndlr
                                                    },
                                                    {ref: ZaAccount.A2_showSameDomain, type: _WIZ_CHECKBOX_, labelLocation:_RIGHT_,align:_RIGHT_, subLabel:"",
                                                            label:null,labelLocation:_NONE_, trueValue:"TRUE", falseValue:"FALSE",
                                                            visibilityChecks:[]
                                                    },
                                                    {type:_OUTPUT_, value:ZaMsg.NAD_SearchSameDomain,colSpan:2}
                                                ]
                                            },

                                            {ref: ZaAccount.A2_nonMemberList, type: _S_DWT_LIST_, width: "98%", height: 460,
                                                cssClass: "DLSource", widgetClass: ZaAccountMemberOfListView,
                                                headerList: nonMemberOfHeaderList, defaultColumnSortable: 0,
                                                onSelection:ZaAccountXFormView.nonMemberOfSelectionListener,
                                                forceUpdate: true },

                                            {type:_SPACER_, height:"5"},
                                            //add action buttons
                                            {type:_GROUP_, width:"100%", numCols:8, colSizes:[90,5,90,"auto",30,5,30,5],
                                                items: [
                                                    {type:_DWT_BUTTON_, label:ZaMsg.DLXV_ButtonAddFromList, width:90,
                                                        enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableAddRemoveButton,ZaAccount.A2_nonMemberList]],
                                                        enableDisableChangeEventSources:[ZaAccount.A2_nonMemberListSelected],
                                                        onActivate:"ZaAccountMemberOfListView.addGroups.call(this,event, ZaAccount.A2_nonMemberList)"
                                                    },
                                                    {type:_CELLSPACER_, height:"100%"},
                                                    {type:_DWT_BUTTON_, label:ZaMsg.DLXV_ButtonAddAll, width:90,
                                                        enableDisableChangeEventSources:[ZaAccount.A2_nonMemberList],
                                                        enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableAllButton,ZaAccount.A2_nonMemberList]],
                                                        onActivate:"ZaAccountMemberOfListView.addAllGroups.call(this,event, ZaAccount.A2_nonMemberList)"
                                                    },
                                                    {type:_CELLSPACER_, height:"100%"},
                                                    {type:_DWT_BUTTON_, label:"", width:30, id:"backButton", icon:"LeftArrow", disIcon:"LeftArrowDis",
                                                        enableDisableChangeEventSources:[ZaAccount.A2_nonMemberList + "_offset"],
                                                        enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableBackButton,ZaAccount.A2_nonMemberList]],
                                                        onActivate:"ZaAccountMemberOfListView.backButtonHndlr.call(this,event, ZaAccount.A2_nonMemberList)"
                                                    },
                                                    {type:_CELLSPACER_, height:"100%"},
                                                    {type:_DWT_BUTTON_, label:"", width:30, id:"fwdButton", icon:"RightArrow", disIcon:"RightArrowDis",
                                                        enableDisableChangeEventSources:[ZaAccount.A2_nonMemberList + "_more"],
                                                        enableDisableChecks:[[ZaAccountMemberOfListView.shouldEnableForwardButton,ZaAccount.A2_nonMemberList]],
                                                        onActivate:"ZaAccountMemberOfListView.fwdButtonHndlr.call(this,event, ZaAccount.A2_nonMemberList)"
                                                    },
                                                    {type:_CELLSPACER_, height:"100%"}
                                                  ]
                                            }
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
        cases.push(memberofCase);
    }
	var zmailFeatureMailForwardingEnabledItem = 
				{ref:ZaAccount.A_zmailFeatureMailForwardingEnabled, 
					msgName:ZaMsg.LBL_zmailFeatureMailForwardingEnabled,
					trueValue:"TRUE", falseValue:"FALSE"
				}


	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.FORWARDING_TAB_ATTRS, ZaAccountXFormView.FORWARDING_TAB_RIGHTS)) {
		ZaNewAccountXWizard.FORWARDING_STEP = ++this.TAB_INDEX;		
		this.stepChoices.push({value:ZaNewAccountXWizard.FORWARDING_STEP, label:ZaMsg.TABT_Forwarding});

		cases.push({type:_CASE_, caseKey:ZaNewAccountXWizard.FORWARDING_STEP, tabGroupKey:ZaNewAccountXWizard.FORWARDING_STEP, numCols:2,colSizes:["250px","auto"], 
					id:"account_form_forwarding_step",
					items: [
						{
							ref:ZaAccount.A_zmailFeatureMailForwardingEnabled,
							resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
							type:_SUPER_WIZ_CHECKBOX_, colSpan:2,
							checkBoxLabel:ZaMsg.LBL_zmailFeatureMailForwardingEnabled,  
							trueValue:"TRUE", falseValue:"FALSE",
							colSizes:["250px","250px","auto"]
						},
						{ref:ZaAccount.A_zmailPrefMailLocalDeliveryDisabled, 
							type:_WIZ_CHECKBOX_,
							msgName:ZaMsg.LBL_zmailPrefMailLocalDeliveryDisabled,
							label:ZaMsg.LBL_zmailPrefMailLocalDeliveryDisabled, 
							trueValue:"TRUE", falseValue:"FALSE"
						},
						{ref:ZaAccount.A_zmailPrefMailForwardingAddress,width:250,
							labelCssClass:"xform_label",
							type:_TEXTFIELD_, msgName:ZaMsg.LBL_zmailPrefMailForwardingAddress,
							label:ZaMsg.LBL_zmailPrefMailForwardingAddress, labelLocation:_LEFT_, 
							cssClass:"admin_xform_name_input",
							nowrap:false,labelWrap:true, 
							enableDisableChecks:[ZaAccountXFormView.isMailForwardingEnabled],
							enableDisableChangeEventSources:[ZaAccount.A_zmailFeatureMailForwardingEnabled, ZaAccount.A_COSId]
						},		
						{type:_SEPARATOR_,colSpan:2},
                        {type: _DWT_ALERT_, colSpan: 2,
                                            containerCssStyle: "padding:0 10px 10px;width:100%;",
                                            style: DwtAlert.WARNING,
                                            iconVisible: true,
                                            content: ZaMsg.Alert_Bouncing_Reveal_Hidden_Adds
                                        },    
                        {ref:ZaAccount.A_zmailMailForwardingAddress, type:_REPEAT_,
							label:ZaMsg.NAD_EditFwdGroup, labelLocation:_LEFT_,labelCssClass:"xform_label",
							repeatInstance:emptyAlias, 
							showAddButton:true, showRemoveButton:true, 
							addButtonLabel:ZaMsg.NAD_AddAddress, 
							showAddOnNextRow:true,
							removeButtonLabel:ZaMsg.NAD_RemoveAddress,
							nowrap:false,labelWrap:true,
							items: [
								{ref:".", type:_TEXTFIELD_, label:null, width:250, enableDisableChecks:[],
								visibilityChecks:[[ZaItem.hasWritePermission, ZaAccount.A_zmailMailForwardingAddress]]}
							]
						},
						{ref:ZaAccount.A_zmailPrefCalendarForwardInvitesTo, type:_REPEAT_,
							label:ZaMsg.zmailPrefCalendarForwardInvitesTo, labelLocation:_LEFT_,labelCssClass:"xform_label",
							repeatInstance:emptyAlias, 
							showAddButton:true, showRemoveButton:true, 
							addButtonLabel:ZaMsg.NAD_AddAddress, 
							showAddOnNextRow:true,
							removeButtonLabel:ZaMsg.NAD_RemoveAddress,
							nowrap:false,labelWrap:true,
							items: [
								{ref:".", type:_TEXTFIELD_, label:null, width:250, enableDisableChecks:[],
								visibilityChecks:[[ZaItem.hasWritePermission, ZaAccount.A_zmailPrefCalendarForwardInvitesTo]]}
							]
						}						
					]
				});				
	};
		
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.FEATURE_TAB_ATTRS, ZaAccountXFormView.FEATURE_TAB_RIGHTS) &&
        !ZaSettings.isOctopus()) {
		ZaNewAccountXWizard.FEATURES_STEP = ++this.TAB_INDEX;		
		this.stepChoices.push({value:ZaNewAccountXWizard.FEATURES_STEP, label:ZaMsg.TABT_Features});
		var featuresCase = {type:_CASE_,caseKey:ZaNewAccountXWizard.FEATURES_STEP, tabGroupKey:ZaNewAccountXWizard.FEATURES_STEP,id:"account_form_features_step",
				numCols:1, width:"100%",
				items: [
					{ type: _DWT_ALERT_,
					  containerCssStyle: "padding-top:20px;width:400px;",
					  style: DwtAlert.WARNING,
					  iconVisible: false, 
					  content: ZaMsg.NAD_CheckFeaturesInfo
					}
				]
		}; 
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailFeatureMailEnabled,ZaAccount.A_zmailFeatureContactsEnabled,
			ZaAccount.A_zmailFeatureCalendarEnabled,ZaAccount.A_zmailFeatureTasksEnabled,ZaAccount.A_zmailFeatureTasksEnabled,
			/*ZaAccount.A_zmailFeatureNotebookEnabled, */ ZaAccount.A_zmailFeatureBriefcasesEnabled,ZaAccount.A_zmailFeatureIMEnabled,
			ZaAccount.A_zmailFeatureOptionsEnabled],[])) {
			featuresCase.items.push({type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_zmailMajorFeature, id:"account_wiz_features_major", colSizes:["auto"],numCols:1,
						items:[	
							{ref:ZaAccount.A_zmailFeatureMailEnabled,
								type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureMailEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureMailEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"},	
							{ref:ZaAccount.A_zmailFeatureContactsEnabled,type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureContactsEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureContactsEnabled, labelLocation:_LEFT_, trueValue:"TRUE", falseValue:"FALSE"},							
							{ref:ZaAccount.A_zmailFeatureCalendarEnabled,
								type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureCalendarEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureCalendarEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"},														
							{ref:ZaAccount.A_zmailFeatureTasksEnabled,
								type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureTaskEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureTaskEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"},														
							//{ref:ZaAccount.A_zmailFeatureNotebookEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureNotebookEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureNotebookEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureBriefcasesEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureBriefcasesEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureBriefcasesEnabled,  trueValue:"TRUE", falseValue:"FALSE"},							
							//{ref:ZaAccount.A_zmailFeatureIMEnabled,
							//	type:_SUPER_WIZ_CHECKBOX_, 
							//	resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
							//	msgName:ZaMsg.LBL_zmailFeatureIMEnabled,
							//	checkBoxLabel:ZaMsg.LBL_zmailFeatureIMEnabled,  
							//	trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureOptionsEnabled,
								type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureOptionsEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureOptionsEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"}	
						]
					});
		};
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailFeatureTaggingEnabled,ZaAccount.A_zmailFeatureSharingEnabled,
			ZaAccount.A_zmailFeatureChangePasswordEnabled,ZaAccount.A_zmailFeatureSkinChangeEnabled,ZaAccount.A_zmailFeatureManageZimlets,
			//ZaAccount.A_zmailFeatureHtmlComposeEnabled,ZaAccount.A_zmailFeatureShortcutAliasesEnabled,
			ZaAccount.A_zmailFeatureGalEnabled,ZaAccount.A_zmailFeatureMAPIConnectorEnabled,ZaAccount.A_zmailFeatureGalAutoCompleteEnabled,
			ZaAccount.A_zmailFeatureImportFolderEnabled, ZaAccount.A_zmailFeatureExportFolderEnabled, ZaAccount.A_zmailDumpsterEnabled],[])) {
			featuresCase.items.push({type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_zmailGeneralFeature, id:"account_wiz_features_general",
						 colSizes:["auto"],numCols:1,
						items:[							
							{ref:ZaAccount.A_zmailFeatureTaggingEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureTaggingEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureTaggingEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureSharingEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureSharingEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureSharingEnabled,trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailExternalSharingEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailExternalSharingEnabled,checkBoxLabel:ZaMsg.LBL_zmailExternalSharingEnabled,trueValue:"TRUE", falseValue:"FALSE",
								visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaAccount.A_zmailFeatureSharingEnabled, "TRUE"]], visibilityChangeEventSources:[ZaAccount.A_zmailFeatureSharingEnabled]
							},
							{ref:ZaAccount.A_zmailPublicSharingEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPublicSharingEnabled,checkBoxLabel:ZaMsg.LBL_zmailPublicSharingEnabled,trueValue:"TRUE", falseValue:"FALSE",
								visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaAccount.A_zmailFeatureSharingEnabled, "TRUE"]], visibilityChangeEventSources:[ZaAccount.A_zmailFeatureSharingEnabled]
							},							
							{ref:ZaAccount.A_zmailFeatureChangePasswordEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureChangePasswordEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureChangePasswordEnabled,trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureSkinChangeEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureSkinChangeEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureSkinChangeEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureManageZimlets, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureManageZimlets,checkBoxLabel:ZaMsg.LBL_zmailFeatureManageZimlets, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureHtmlComposeEnabled, type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureHtmlComposeEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureHtmlComposeEnabled, 
								trueValue:"TRUE", falseValue:"FALSE"},							
							/*{ref:ZaAccount.A_zmailFeatureShortcutAliasesEnabled, 
								type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureShortcutAliasesEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureShortcutAliasesEnabled, 
								trueValue:"TRUE", falseValue:"FALSE"},*/
							{ref:ZaAccount.A_zmailFeatureGalEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureGalEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureGalEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureMAPIConnectorEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureMAPIConnectorEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureMAPIConnectorEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureGalAutoCompleteEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureGalAutoCompleteEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureGalAutoCompleteEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureImportFolderEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureImportFolderEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureImportFolderEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                            {ref:ZaAccount.A_zmailFeatureExportFolderEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureExportFolderEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureExportFolderEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailDumpsterEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailDumpsterEnabled,checkBoxLabel:ZaMsg.LBL_zmailDumpsterEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailDumpsterPurgeEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailDumpsterPurgeEnabled,checkBoxLabel:ZaMsg.LBL_zmailDumpsterPurgeEnabled,  trueValue:"TRUE", falseValue:"FALSE",
								visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaAccount.A_zmailDumpsterEnabled, "TRUE"]], visibilityChangeEventSources:[ZaAccount.A_zmailDumpsterEnabled]
							}
						]
					});
			
		};		
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailFeatureMailPriorityEnabled,ZaAccount.A_zmailFeatureFlaggingEnabled,
			ZaAccount.A_zmailImapEnabled,ZaAccount.A_zmailPop3Enabled,ZaAccount.A_zmailFeatureImapDataSourceEnabled,
			ZaAccount.A_zmailFeaturePop3DataSourceEnabled,ZaAccount.A_zmailFeatureConversationsEnabled,ZaAccount.A_zmailFeatureFiltersEnabled,
			ZaAccount.A_zmailFeatureOutOfOfficeReplyEnabled,ZaAccount.A_zmailFeatureNewMailNotificationEnabled,ZaAccount.A_zmailFeatureMailPollingIntervalPreferenceEnabled, 
			ZaAccount.A_zmailFeatureMailSendLaterEnabled,ZaAccount.A_zmailFeatureIdentitiesEnabled,ZaAccount.A_zmailFeatureReadReceiptsEnabled],[])) {
			featuresCase.items.push({type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_zmailMailFeature, id:"account_wiz_features_mail",
						colSizes:["auto"],numCols:1,
					 	enableDisableChecks:[ZaAccountXFormView.isMailFeatureEnabled],
						enableDisableChangeEventSources:[ZaAccount.A_zmailFeatureMailEnabled, ZaAccount.A_COSId],
						items:[													
							{ref:ZaAccount.A_zmailFeatureMailPriorityEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureMailPriorityEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureMailPriorityEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureFlaggingEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureFlaggingEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureFlaggingEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailImapEnabled, type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailImapEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailImapEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailPop3Enabled, type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPop3Enabled,
								checkBoxLabel:ZaMsg.LBL_zmailPop3Enabled,  trueValue:"TRUE", falseValue:"FALSE"},		
							{ref:ZaAccount.A_zmailFeatureImapDataSourceEnabled, type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailExternalImapEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailExternalImapEnabled,  trueValue:"TRUE", falseValue:"FALSE"},		
							{ref:ZaAccount.A_zmailFeaturePop3DataSourceEnabled, type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailExternalPop3Enabled,
								checkBoxLabel:ZaMsg.LBL_zmailExternalPop3Enabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureMailSendLaterEnabled, type:_SUPER_WIZ_CHECKBOX_,
                                                                resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureMailSendLaterEnabled,
                                                                checkBoxLabel:ZaMsg.LBL_zmailFeatureMailSendLaterEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
		
							{ref:ZaAccount.A_zmailFeatureConversationsEnabled, type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureConversationsEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureConversationsEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureFiltersEnabled, type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureFiltersEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureFiltersEnabled,trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureOutOfOfficeReplyEnabled, type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureOutOfOfficeReplyEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureOutOfOfficeReplyEnabled, 
								trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureNewMailNotificationEnabled, 
								type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureNewMailNotificationEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureNewMailNotificationEnabled, 
								trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureMailPollingIntervalPreferenceEnabled, 
								type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureMailPollingIntervalPreferenceEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureMailPollingIntervalPreferenceEnabled, 
								trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureIdentitiesEnabled,
								type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								msgName:ZaMsg.LBL_zmailFeatureIdentitiesEnabled,
								checkBoxLabel:ZaMsg.LBL_zmailFeatureIdentitiesEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"
							},
							{ref:ZaAccount.A_zmailFeatureReadReceiptsEnabled,
								type:_SUPER_WIZ_CHECKBOX_, 
								resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
								checkBoxLabel:ZaMsg.LBL_zmailFeatureReadReceiptsEnabled,  
								trueValue:"TRUE", falseValue:"FALSE"
							}
						]
					});
			
		};
        if (ZAWizTopGrouper_XFormItem.isGroupVisible(
            entry,
            [
                ZaAccount.A_zmailFeatureContactsEnabled,
                ZaAccount.A_zmailFeatureDistributionListFolderEnabled
            ],
            []
        )
            ) {
            featuresCase.items.push(
                {
                    type: _ZAWIZ_TOP_GROUPER_,
                    label: ZaMsg.NAD_zmailContactFeature,
                    id: "account_wiz_features_contact",
                    enableDisableChecks: [ZaNewAccountXWizard.isContactsFeatureEnabled],
                    enableDisableChangeEventSources: [
                        ZaAccount.A_zmailFeatureContactsEnabled,
                        ZaAccount.A_COSId
                    ],
                    items: [
                        {
                            ref: ZaAccount.A_zmailFeatureDistributionListFolderEnabled,
                            type: _SUPER_WIZ_CHECKBOX_,
                            resetToSuperLabel: ZaMsg.NAD_ResetToCOS,
                            msgName: ZaMsg.MSG_zmailFeatureDistributionListFolderEnabled,
                            checkBoxLabel: ZaMsg.LBL_zmailFeatureDistributionListFolderEnabled,
                            trueValue: "TRUE",
                            falseValue: "FALSE"
                        }
                    ]
                }
            );
        }
		if  (ZAWizTopGrouper_XFormItem.isGroupVisible(
					entry,
					[ZaAccount.A_zmailFeatureGroupCalendarEnabled,
						//ZaAccount.A_zmailFeatureFreeBusyViewEnabled,
						ZaAccount.A_zmailFeatureCalendarReminderDeviceEmailEnabled
					],
					[]
				)
			)
		{
			featuresCase.items.push(
				{type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_zmailCalendarFeature, id:"account_wiz_features_calendar",
				 	colSizes:["auto"],numCols:1,
				 	enableDisableChecks:[ZaAccountXFormView.isCalendarFeatureEnabled],
					enableDisableChangeEventSources:[ZaAccount.A_zmailFeatureCalendarEnabled,ZaAccount.A_COSId],
					items:[		
						{ref:ZaAccount.A_zmailFeatureGroupCalendarEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureGroupCalendarEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureGroupCalendarEnabled, trueValue:"TRUE", falseValue:"FALSE"},
						//{ref:ZaAccount.A_zmailFeatureFreeBusyViewEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureFreeBusyViewEnabled, checkBoxLabel:ZaMsg.LBL_zmailFeatureFreeBusyViewEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaAccount.A_zmailFeatureCalendarReminderDeviceEmailEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureCalendarReminderDeviceEmailEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureCalendarReminderDeviceEmailEnabled, trueValue:"TRUE", falseValue:"FALSE"}
					]
				}
			);
		};	
	//	if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailFeatureIMEnabled],[])) {
	//		featuresCase.items.push(
	//			{type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_zmailIMFeature, id:"account_wiz_features_im",
	//				 	colSizes:["auto"],numCols:1,
	//					visibilityChecks:[ZaAccountXFormView.isIMFeatureEnabled],
	//					visibilityChangeEventSources:[ZaAccount.A_zmailFeatureIMEnabled,ZaAccount.A_COSId],
	//					items:[			
	//						{ref:ZaAccount.A_zmailFeatureInstantNotify, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureInstantNotify,checkBoxLabel:ZaMsg.LBL_zmailFeatureInstantNotify, trueValue:"TRUE", falseValue:"FALSE"}
	//					]
	//			}
	//		);
	//	};

		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ //ZaAccount.A_zmailFeatureAdvancedSearchEnabled,
			ZaAccount.A_zmailFeatureAdvancedSearchEnabled,
			ZaAccount.A_zmailFeatureSavedSearchesEnabled,
			ZaAccount.A_zmailFeatureInitialSearchPreferenceEnabled,
			ZaAccount.A_zmailFeaturePeopleSearchEnabled
			],[])) {
			featuresCase.items.push(
				{type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_zmailSearchFeature, id:"account_wiz_features_search",
					 colSizes:["auto"],numCols:1,
						items:[
							{ref:ZaAccount.A_zmailFeatureAdvancedSearchEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureAdvancedSearchEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureAdvancedSearchEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureSavedSearchesEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureSavedSearchesEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureSavedSearchesEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeatureInitialSearchPreferenceEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeatureInitialSearchPreferenceEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeatureInitialSearchPreferenceEnabled, trueValue:"TRUE", falseValue:"FALSE"},
							{ref:ZaAccount.A_zmailFeaturePeopleSearchEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailFeaturePeopleSearchEnabled,checkBoxLabel:ZaMsg.LBL_zmailFeaturePeopleSearchEnabled, trueValue:"TRUE", falseValue:"FALSE"}
						]
				}
			);
		};
                if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailFeatureSMIMEEnabled],[])) {
                        featuresCase.items.push(
                                {type:_ZAWIZ_TOP_GROUPER_, label:ZaMsg.NAD_zmailSMIMEFeature, id:"account_wiz_features_smime",
                                        colSizes:["auto"],numCols:1,                                        
                                        items:[
                                                {ref:ZaAccount.A_zmailFeatureSMIMEEnabled, type:_SUPER_WIZ_CHECKBOX_,
                                                resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                                msgName:ZaMsg.LBL_zmailFeatureSMIMEEnabled,
                                                checkBoxLabel:ZaMsg.LBL_zmailFeatureSMIMEEnabled,
                                                trueValue:"TRUE", falseValue:"FALSE"}
                                        ]
                                }
                        );
                };	

		cases.push(featuresCase);
	}



	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.PREFERENCES_TAB_ATTRS, ZaAccountXFormView.PREFERENCES_TAB_RIGHTS)) {
		ZaNewAccountXWizard.PREFS_STEP = ++this.TAB_INDEX;	
		this.stepChoices.push({value:ZaNewAccountXWizard.PREFS_STEP, label:ZaMsg.TABT_Preferences});

		var prefItems = [ ];
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailPrefImapSearchFoldersEnabled,ZaAccount.A_zmailPrefShowSearchString,
			ZaAccount.A_zmailPrefUseKeyboardShortcuts,ZaAccount.A_zmailPrefMailInitialSearch,
			ZaAccount.A_zmailPrefWarnOnExit,ZaAccount.A_zmailPrefAdminConsoleWarnOnExit, ZaAccount.A_zmailPrefShowSelectionCheckbox,
			ZaAccount.A_zmailJunkMessagesIndexingEnabled,ZaAccount.A_zmailPrefLocale],[])) {
			

			prefItems.push({type:_ZAWIZ_TOP_GROUPER_, id:"account_prefs_general",
                            label:ZaMsg.NAD_GeneralOptions,
							items :[
                                    {ref:ZaAccount.A_zmailPrefImapSearchFoldersEnabled, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefImapSearchFoldersEnabled,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefImapSearchFoldersEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefShowSearchString, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefShowSearchString,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefShowSearchString,trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefUseKeyboardShortcuts, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,checkBoxLabel:ZaMsg.LBL_zmailPrefUseKeyboardShortcuts,
                                        trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefClientType, type:_SUPERWIZ_SELECT1_,
                                        colSizes:["300px", "*"],
                                        msgName:ZaMsg.MSG_zmailPrefClientType,
                                        label:ZaMsg.LBL_zmailPrefClientType, labelLocation:_LEFT_,
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS
                                    },
                                    {ref:ZaAccount.A_zmailPrefMailInitialSearch, type:_SUPERWIZ_TEXTFIELD_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        msgName:ZaMsg.LBL_zmailPrefMailInitialSearch,
                                        txtBoxLabel:ZaMsg.LBL_zmailPrefMailInitialSearch,
                                        labelLocation:_LEFT_,
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS
                                    },
                                    {ref:ZaAccount.A_zmailPrefWarnOnExit, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,checkBoxLabel:ZaMsg.LBL_zmailPrefWarnOnExit,
                                        labelWrap: true,trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefAdminConsoleWarnOnExit, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,checkBoxLabel:ZabMsg.LBL_zmailPrefAdminConsoleWarnOnExit,
                                        labelWrap: true,trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefShowSelectionCheckbox, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,checkBoxLabel:ZaMsg.LBL_zmailPrefShowSelectionCheckbox,
                                        trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailJunkMessagesIndexingEnabled, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,checkBoxLabel:ZaMsg.LBL_zmailJunkMessagesIndexingEnabled,
                                        trueValue:"TRUE", falseValue:"FALSE"}	,
                                    {ref:ZaAccount.A_zmailPrefLocale, type:_SUPERWIZ_SELECT1_, msgName:ZaMsg.LBL_zmailPrefMailLocale,
                                        choices: ZaSettings.getLocaleChoices(),
                                        colSizes:["300px", "*"],
                                        label:ZaMsg.LBL_zmailPrefLocale, labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS}
                                    ]
						});
		}
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailPrefMailItemsPerPage,ZaAccount.A_zmailMaxMailItemsPerPage],[])) {				
			prefItems.push({type:_ZAWIZ_TOP_GROUPER_, id:"account_prefs_standard_client",borderCssClass:"LowPaddedTopGrouperBorder",
							label:ZaMsg.NAD_MailOptionsStandardClient,
							items :[
								{ref:ZaAccount.A_zmailMaxMailItemsPerPage, type:_SUPERWIZ_SELECT1_,
                                    colSizes:["300px", "*"],
									editable:true,inputSize:4,choices:[10,25,50,100,250,500,1000],
									msgName:ZaMsg.MSG_zmailMaxMailItemsPerPage,label:ZaMsg.LBL_zmailMaxMailItemsPerPage,
									labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, valueLabel:null
								},								
								{ref:ZaAccount.A_zmailPrefMailItemsPerPage, type:_SUPERWIZ_SELECT1_,
                                    colSizes:["300px", "*"],
									msgName:ZaMsg.MSG_zmailPrefMailItemsPerPage,label:ZaMsg.LBL_zmailPrefMailItemsPerPage,
									labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, valueLabel:null
								}							
							]
			});
			prefItems.push({type: _SPACER_ , height: "10px" });
		}			
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailPrefMessageViewHtmlPreferred,ZaAccount.A_zmailPrefDisplayExternalImages,ZaAccount.A_zmailPrefMailToasterEnabled,
            ZaAccount.A_zmailPrefMessageIdDedupingEnabled,
			ZaAccount.A_zmailPrefGroupMailBy,ZaAccount.A_zmailPrefMailDefaultCharset,ZaAccount.A_zmailPrefItemsPerVirtualPage],[])) {				

			prefItems.push({type:_ZAWIZ_TOP_GROUPER_, id:"account_prefs_mail_general",
                            label:ZaMsg.NAD_MailOptions,
                            items: [
                                     {ref:ZaAccount.A_zmailPrefMessageViewHtmlPreferred,
                                        type:_SUPER_WIZ_CHECKBOX_, colSpan:2,
                                        colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                        msgName:ZaMsg.LBL_zmailPrefMessageViewHtmlPreferred,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefMessageViewHtmlPreferred,
                                        trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefDisplayExternalImages,
                                        type:_SUPER_WIZ_CHECKBOX_, colSpan:2,
                                        colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                        msgName:ZaMsg.LBL_zmailPrefDisplayExternalImages,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefDisplayExternalImages,
                                        trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefGroupMailBy, type:_SUPERWIZ_SELECT1_,
                                        colSizes:["300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefGroupMailBy,
                                        label:ZaMsg.LBL_zmailPrefGroupMailBy, labelLocation:_LEFT_
                                    },
                                    {ref:ZaAccount.A_zmailPrefMailDefaultCharset, type:_SUPERWIZ_SELECT1_,
                                        colSizes:["300px", "*"],
                                        msgName:ZaMsg.LBL_zmailPrefMailDefaultCharset,
                                        label:ZaMsg.LBL_zmailPrefMailDefaultCharset, labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS},
                                    {ref:ZaAccount.A_zmailPrefMailToasterEnabled,
                                        type:_SUPER_WIZ_CHECKBOX_, colSpan:2,
                                        colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                        msgName:ZaMsg.MSG_zmailPrefMailToasterEnabled,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefMailToasterEnabled,
                                        trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefMessageIdDedupingEnabled,
                                        type:_SUPER_WIZ_CHECKBOX_, colSpan:2,
                                        colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                        msgName:ZaMsg.MSG_zmailPrefMessageIdDedupingEnabled,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefMessageIdDedupingEnabled,
                                        trueValue:"TRUE", falseValue:"FALSE"},
{ref:ZaAccount.A_zmailPrefItemsPerVirtualPage,
         type:_SUPERWIZ_TEXTFIELD_,
         colSizes:["200px", "*"],	colSpan:2,nowrap:false,labelWrap:true,
         resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
         msgName:ZaMsg.LBL_zmailPrefItemsPerVirtualPage,
         txtBoxLabel:ZaMsg.LBL_zmailPrefItemsPerVirtualPage      
 
}

                                ]
						});
		}
	
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailPrefMailPollingInterval,ZaAccount.A_zmailMailMinPollingInterval,
			ZaAccount.A_zmailPrefNewMailNotificationEnabled,ZaAccount.A_zmailPrefNewMailNotificationAddress,ZaAccount.A_zmailPrefOutOfOfficeReplyEnabled,
			ZaAccount.A_zmailPrefOutOfOfficeReplyEnabled,ZaAccount.A_zmailPrefOutOfOfficeCacheDuration,
			ZaAccount.A_zmailPrefOutOfOfficeReply,ZaAccount.A_zmailPrefReadReceiptsToAddress,ZaAccount.A_zmailPrefMailSendReadReceipts],[])) {				
			prefItems.push(				
						{type:_ZAWIZ_TOP_GROUPER_, id:"account_prefs_mail_receiving",
							label:ZaMsg.NAD_MailOptionsReceiving,
							items :[
								{ref:ZaAccount.A_zmailPrefMailPollingInterval, type:_SUPERWIZ_SELECT1_,
									colSizes:["300px","*"],
									msgName:ZaMsg.MSG_zmailPrefMailPollingInterval,
                                    label:ZaMsg.LBL_zmailPrefMailPollingInterval,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                    onChange:ZaAccountXFormView.validatePollingInterval,
									nowrap:false,labelWrap:true									
								},							
								{ref:ZaAccount.A_zmailMailMinPollingInterval, 
									type:_SUPERWIZ_LIFETIME_, colSizes:["200px","130px","170px","*"],
									msgName:ZaMsg.MSG_zmailMailMinPollingInterval,
									txtBoxLabel:ZaMsg.LBL_zmailMailMinPollingInterval, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                    onChange:ZaAccountXFormView.validatePollingInterval,
									colSpan:2,nowrap:false,labelWrap:true	
								},
								{ref:ZaAccount.A_zmailPrefNewMailNotificationEnabled, 
									type:_WIZ_CHECKBOX_,
									msgName:ZaMsg.LBL_zmailPrefNewMailNotificationEnabled,
									label:ZaMsg.LBL_zmailPrefNewMailNotificationEnabled,
									trueValue:"TRUE", falseValue:"FALSE"
								},
								{ref:ZaAccount.A_zmailPrefNewMailNotificationAddress, type:_TEXTFIELD_, 
									msgName:ZaMsg.MSG_zmailPrefNewMailNotificationAddress,
									label:ZaMsg.LBL_zmailPrefNewMailNotificationAddress, 
									labelLocation:_LEFT_,
									enableDisableChecks:[ZaAccountXFormView.isMailNotificationAddressEnabled],
									enableDisableChangeEventSources:[ZaAccount.A_zmailPrefNewMailNotificationEnabled]
								},
								{ref:ZaAccount.A_zmailPrefOutOfOfficeReplyEnabled, 
									type:_WIZ_CHECKBOX_, msgName:ZaMsg.LBL_zmailPrefOutOfOfficeReplyEnabled,label:ZaMsg.LBL_zmailPrefOutOfOfficeReplyEnabled, trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefOutOfOfficeCacheDuration, 
									type:_SUPERWIZ_LIFETIME_, colSizes:["200px","130px","170px","*"],
									msgName:ZaMsg.LBL_zmailPrefOutOfOfficeCacheDuration,
									txtBoxLabel:ZaMsg.LBL_zmailPrefOutOfOfficeCacheDuration, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									colSpan:2,nowrap:false,labelWrap:true	
								},
								{ref:ZaAccount.A_zmailPrefOutOfOfficeReply, type:_TEXTAREA_, 
									msgName:ZaMsg.LBL_zmailPrefOutOfOfficeReply,
									label:ZaMsg.LBL_zmailPrefOutOfOfficeReply, 
									labelLocation:_LEFT_, labelCssStyle:"vertical-align:top;", width:"30em",
									enableDisableChecks:[ZaAccountXFormView.isOutOfOfficeReplyEnabled],
									enableDisableChangeEventSources:[ZaAccount.A_zmailPrefOutOfOfficeReplyEnabled]
								},
								{ref:ZaAccount.A_zmailPrefMailSendReadReceipts, 
									type:_SUPERWIZ_SELECT1_,
                                    colSizes:["300px", "*"],
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									label:ZaMsg.LBL_zmailPrefMailSendReadReceipts, 
									nowrap:false,labelWrap:true,
									enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailFeatureReadReceiptsEnabled,"TRUE"],
										[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefMailSendReadReceipts]],
									enableDisableChangeEventSources:[ZaAccount.A_zmailFeatureReadReceiptsEnabled]									
								},	
								{ref:ZaAccount.A_zmailPrefReadReceiptsToAddress, type:_TEXTFIELD_, 
									label:ZaMsg.LBL_zmailPrefReadReceiptsToAddress,
									nowrap:false,labelWrap:true,
									msgName:ZaMsg.LBL_zmailPrefReadReceiptsToAddress,
									labelLocation:_LEFT_, cssClass:"admin_xform_name_input", width:150,
									enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailFeatureReadReceiptsEnabled,"TRUE"],
										[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefReadReceiptsToAddress]],
									enableDisableChangeEventSources:[ZaAccount.A_zmailFeatureReadReceiptsEnabled]									
								}															
							]
						});
		}
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_prefSaveToSent,ZaAccount.A_zmailAllowAnyFromAddress, 
			ZaAccount.A_zmailAllowFromAddress],[])) {				
			prefItems.push({type:_ZAWIZ_TOP_GROUPER_, id:"account_prefs_mail_sending",borderCssClass:"LowPaddedTopGrouperBorder",
							label:ZaMsg.NAD_MailOptionsSending,
							items :[
								{ref:ZaAccount.A_zmailPrefSaveToSent,  
									colSpan:2, colSizes:["200px","300px","*"],
									type:_SUPER_WIZ_CHECKBOX_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefSaveToSent,
									checkBoxLabel:ZaMsg.LBL_zmailPrefSaveToSent,
									trueValue:"TRUE", falseValue:"FALSE"},
									
								{ref:ZaAccount.A_zmailAllowAnyFromAddress,  
									colSpan:2, colSizes:["200px","300px","*"],
									type:_SUPER_WIZ_CHECKBOX_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailAllowAnyFromAddress,
									checkBoxLabel:ZaMsg.LBL_zmailAllowAnyFromAddress,
									trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailAllowFromAddress,
									type:_REPEAT_,
									label:ZaMsg.LBL_zmailAllowFromAddress,
									labelLocation:_LEFT_, 
									addButtonLabel:ZaMsg.NAD_AddAddress, 
									align:_LEFT_,
									repeatInstance:emptyAlias, 
									showAddButton:true, 
									showRemoveButton:true, 
									showAddOnNextRow:true, 
									removeButtonLabel:ZaMsg.NAD_RemoveAddress,								
									items: [
										{ref:".", type:_TEXTFIELD_, label:null, width:"200px", enableDisableChecks:[],visibilityChecks:[]}
									],
									nowrap:false,labelWrap:true,
									enableDisableChecks:[],
									visibilityChecks:[ZaAccountXFormView.isSendingFromAnyAddressDisAllowed,[ZaItem.hasWritePermission,ZaAccount.A_zmailAllowFromAddress]],
									visibilityChangeEventSources:[ZaAccount.A_zmailAllowAnyFromAddress, ZaAccount.A_zmailAllowFromAddress, ZaAccount.A_COSId]										
								}
							]
						});
		}
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailPrefComposeInNewWindow,ZaAccount.A_zmailPrefComposeFormat,
			ZaAccount.A_zmailPrefHtmlEditorDefaultFontFamily,ZaAccount.A_zmailPrefHtmlEditorDefaultFontSize,
			ZaAccount.A_zmailPrefHtmlEditorDefaultFontColor,ZaAccount.A_zmailPrefForwardReplyInOriginalFormat,
			ZaAccount.A_zmailPrefMailSignatureEnabled,/*ZaAccount.A_zmailPrefMailSignatureStyle,*/
			ZaAccount.A_zmailMailSignatureMaxLength,ZaAccount.A_zmailPrefMailSignature,
			ZaAccount.A_zmailPrefMandatorySpellCheckEnabled, ZaAccount.A_zmailPrefAutoSaveDraftInterval],[])) {				
			prefItems.push({type:_ZAWIZ_TOP_GROUPER_, id:"account_prefs_mail_composing",borderCssClass:"LowPaddedTopGrouperBorder",
							label:ZaMsg.NAD_MailOptionsComposing,
							items :[																										
								{ref:ZaAccount.A_zmailPrefComposeInNewWindow, 
									colSpan:2,
									type:_SUPER_WIZ_CHECKBOX_,
									colSizes:["200px","300px","*"],
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefComposeInNewWindow,
									checkBoxLabel:ZaMsg.LBL_zmailPrefComposeInNewWindow,
									trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefComposeFormat, 
									type:_SUPERWIZ_SELECT1_,
                                    colSizes:["300px", "*"],
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefComposeFormat,
									label:ZaMsg.LBL_zmailPrefComposeFormat},
								{ref:ZaAccount.A_zmailPrefHtmlEditorDefaultFontFamily, type:_SUPERWIZ_SELECT1_,
                                    colSizes:["300px", "*"],
									msgName:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontFamily,label:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontFamily,
									labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, valueLabel:null
								},
								{ref:ZaAccount.A_zmailPrefHtmlEditorDefaultFontSize, type:_SUPERWIZ_SELECT1_,
                                    colSizes:["300px", "*"],
									msgName:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontSize, label:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontSize,
									labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, valueLabel:null
								},
								{ref:ZaAccount.A_zmailPrefHtmlEditorDefaultFontColor, type:_SUPERWIZ_DWT_COLORPICKER_,
									msgName:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontColor, label:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontColor,
									labelLocation:_LEFT_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailPrefForwardReplyInOriginalFormat, 
									colSpan:2,
									type:_SUPER_WIZ_CHECKBOX_, 
									colSizes:["200px", "300px", "*"],
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefForwardReplyInOriginalFormat,
									checkBoxLabel:ZaMsg.LBL_zmailPrefForwardReplyInOriginalFormat, 
									trueValue:"TRUE", falseValue:"FALSE"},
								{ref:ZaAccount.A_zmailPrefMandatorySpellCheckEnabled, 
									colSpan:2,
									type:_SUPER_WIZ_CHECKBOX_, 
									colSizes:["200px", "300px", "*"],
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefMandatorySpellCheckEnabled,
									checkBoxLabel:ZaMsg.LBL_zmailPrefMandatorySpellCheckEnabled,
									trueValue:"TRUE", falseValue:"FALSE"},									
								{ref:ZaAccount.A_zmailPrefMailSignatureEnabled,
									colSizes:["200px", "300px", "*"],
									type:_WIZ_CHECKBOX_, msgName:ZaMsg.LBL_zmailPrefMailSignatureEnabled,
									label:ZaMsg.LBL_zmailPrefMailSignatureEnabled,  
									trueValue:"TRUE", falseValue:"FALSE"},
								/*{ref:ZaAccount.A_zmailPrefMailSignatureStyle, 
									//colSpan:2,								
									type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPrefMailSignatureStyle,
									checkBoxLabel:ZaMsg.LBL_zmailPrefMailSignatureStyle,
									trueValue:"internet", falseValue:"outlook"
								},*/
								{ref:ZaAccount.A_zmailMailSignatureMaxLength, type:_SUPERWIZ_TEXTFIELD_,
                                    colSpan:2,
									colSizes:["200px", "300px", "*"],
									txtBoxLabel:ZaMsg.LBL_zmailMailSignatureMaxLength, 
									msgName:ZaMsg.MSG_zmailMailSignatureMaxLength,
									labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input", 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailPrefMailSignature, type:_TEXTAREA_,
									colSizes:["200px", "300px", "*"],
									msgName:ZaMsg.MSG_zmailPrefMailSignature,
									label:ZaMsg.LBL_zmailPrefMailSignature, labelLocation:_LEFT_, 
									labelCssStyle:"vertical-align:top;", width:"30em",
									enableDisableChangeEventSources:[ZaAccount.A_zmailPrefMailSignatureEnabled],
									enableDisableChecks:[ZaAccountXFormView.isMailSignatureEnabled]									
								},
                                {ref:ZaAccount.A_zmailPrefAutoSaveDraftInterval, type:_SUPERWIZ_LIFETIME_,
                                    colSizes:["200px","80px","220px","*"],
                                    msgName:ZaMsg.MSG_zmailPrefAutoSaveDraftInterval,
                                    txtBoxLabel:ZaMsg.LBL_zmailPrefAutoSaveDraftInterval,
                                    resetToSuperLabel:ZaMsg.NAD_ResetToCOS,colSpan:2,
                                    nowrap:false,labelWrap:true
                                }
							]
						});
		}		
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailPrefAutoAddAddressEnabled,ZaAccount.A_zmailPrefGalAutoCompleteEnabled],[])) {				
			prefItems.push(
						{type:_ZAWIZ_TOP_GROUPER_, id:"account_prefs_contacts_general",
                            label:ZaMsg.NAD_ContactsOptions,
							items :[
                                    {ref:ZaAccount.A_zmailPrefAutoAddAddressEnabled, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2,
                                        colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                        msgName:ZaMsg.LBL_zmailPrefAutoAddAddressEnabled,checkBoxLabel:ZaMsg.LBL_zmailPrefAutoAddAddressEnabled,
                                        trueValue:"TRUE", falseValue:"FALSE"
                                    },
                                    {ref:ZaAccount.A_zmailPrefGalAutoCompleteEnabled, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2,
                                        colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                        msgName:ZaMsg.LBL_zmailPrefGalAutoCompleteEnabled,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefGalAutoCompleteEnabled,
                                        trueValue:"TRUE", falseValue:"FALSE"}
                                    ]
						});
		}
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailPrefTimeZoneId,ZaAccount.A_zmailPrefCalendarApptReminderWarningTime,
			ZaAccount.A_zmailPrefCalendarAlwaysShowMiniCal,ZaAccount.A_zmailPrefCalendarUseQuickAdd,ZaAccount.A_zmailPrefUseTimeZoneListInCalendar,
			ZaAccount.A_zmailPrefCalendarInitialView,ZaAccount.A_zmailPrefCalendarFirstDayOfWeek,ZaAccount.A_zmailPrefCalendarNotifyDelegatedChanges,
			ZaAccount.A_zmailPrefCalendarApptVisibility,ZaAccount.A_zmailPrefCalendarReminderSoundsEnabled,
			ZaAccount.A_zmailPrefCalendarSendInviteDeniedAutoReply,ZaAccount.A_zmailPrefCalendarAutoAddInvites,
			ZaAccount.A_zmailPrefCalendarAllowForwardedInvite,ZaAccount.A_zmailPrefCalendarReminderFlashTitle,
			ZaAccount.A_zmailPrefCalendarAllowCancelEmailToSelf,ZaAccount.A_zmailPrefCalendarToasterEnabled,
			ZaAccount.A_zmailPrefCalendarShowPastDueReminders,ZaAccount.A_zmailPrefAppleIcalDelegationEnabled],[])) {				
			prefItems.push(
						{type:_ZAWIZ_TOP_GROUPER_, id:"account_prefs_calendar_general",
                          label:ZaMsg.NAD_CalendarOptions,
							items :[
                                    {ref:ZaAccount.A_zmailPrefTimeZoneId, type:_SUPERWIZ_SELECT1_,
                                        msgName:ZaMsg.LBL_zmailPrefTimeZoneId, valueWidth: "280px",
                                        colSizes:["300px", "*"],
                                        label:ZaMsg.LBL_zmailPrefTimeZoneId, labelLocation:_LEFT_,
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS},
                                    {ref:ZaAccount.A_zmailPrefCalendarApptReminderWarningTime, type:_SUPERWIZ_SELECT1_,
                                        colSizes:["300px", "*"],
                                        msgName:ZaMsg.LBL_zmailPrefCalendarApptReminderWarningTime,
                                        label:ZaMsg.LBL_zmailPrefCalendarApptReminderWarningTime, labelLocation:_LEFT_,
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS},
                                    {ref:ZaAccount.A_zmailPrefCalendarInitialView, type:_SUPERWIZ_SELECT1_,
                                        colSizes:["300px", "*"],
                                        msgName:ZaMsg.MSG_zmailPrefCalendarInitialView,
                                        label:ZaMsg.LBL_zmailPrefCalendarInitialView, labelLocation:_LEFT_,
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS
                                    },
                                    {ref:ZaAccount.A_zmailPrefCalendarFirstDayOfWeek,
                                        type:_SUPERWIZ_SELECT1_,
                                        colSizes:["300px", "*"],
                                        msgName:ZaMsg.LBL_zmailPrefCalendarFirstDayOfWeek,
                                        label:ZaMsg.LBL_zmailPrefCalendarFirstDayOfWeek, labelLocation:_LEFT_,
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS
                                    },
                                    {ref:ZaAccount.A_zmailPrefCalendarApptVisibility,
                                        type:_SUPERWIZ_SELECT1_,
                                        colSizes:["300px", "*"],
                                        msgName:ZaMsg.LBL_zmailPrefCalendarApptVisibility,
                                        label:ZaMsg.LBL_zmailPrefCalendarApptVisibility, labelLocation:_LEFT_,
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS
                                    },
                                    {ref:ZaAccount.A_zmailPrefAppleIcalDelegationEnabled, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailPrefAppleIcalDelegationEnabled,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefAppleIcalDelegationEnabled,
                                        trueValue:"TRUE", falseValue:"FALSE",
                                        nowrap:false,labelWrap:true
                                    },
                                    {ref:ZaAccount.A_zmailPrefCalendarShowPastDueReminders, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailPrefCalendarShowPastDueReminders,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarShowPastDueReminders,
                                        trueValue:"TRUE", falseValue:"FALSE",
                                        nowrap:false,labelWrap:true
                                    },
                                    {ref:ZaAccount.A_zmailPrefCalendarToasterEnabled, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailPrefCalendarToasterEnabled,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarToasterEnabled,
                                        trueValue:"TRUE", falseValue:"FALSE",
                                        nowrap:false,labelWrap:true
                                    },
                                    {ref:ZaAccount.A_zmailPrefCalendarAllowCancelEmailToSelf, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailPrefCalendarAllowCancelEmailToSelf,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarAllowCancelEmailToSelf,
                                        trueValue:"TRUE", falseValue:"FALSE",
                                        nowrap:false,labelWrap:true
                                    },
                                    {ref:ZaAccount.A_zmailPrefCalendarAllowPublishMethodInvite, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailPrefCalendarAllowPublishMethodInvite,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarAllowPublishMethodInvite,
                                        trueValue:"TRUE", falseValue:"FALSE",
                                        nowrap:false,labelWrap:true
                                    },
                                    {ref:ZaAccount.A_zmailPrefCalendarAllowForwardedInvite, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailPrefCalendarAllowForwardedInvite,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarAllowForwardedInvite,
                                        trueValue:"TRUE", falseValue:"FALSE",
                                        nowrap:false,labelWrap:true
                                    },
                                    {ref:ZaAccount.A_zmailPrefCalendarReminderFlashTitle, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailPrefCalendarReminderFlashTitle,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarReminderFlashTitle,
                                        trueValue:"TRUE", falseValue:"FALSE",
                                        nowrap:false,labelWrap:true
                                    },
                                    {ref:ZaAccount.A_zmailPrefCalendarReminderSoundsEnabled, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefCalendarReminderSoundsEnabled,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarReminderSoundsEnabled,
                                        trueValue:"TRUE", falseValue:"FALSE",
                                        nowrap:false,labelWrap:true
                                    },
                                    {ref:ZaAccount.A_zmailPrefCalendarSendInviteDeniedAutoReply, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefCalendarSendInviteDeniedAutoReply,
                                        checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarSendInviteDeniedAutoReply,
                                        trueValue:"TRUE", falseValue:"FALSE",
                                        nowrap:false,labelWrap:true
                                    },
                                    {ref:ZaAccount.A_zmailPrefCalendarAutoAddInvites, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefCalendarAutoAddInvites,checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarAutoAddInvites, trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefCalendarNotifyDelegatedChanges, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefCalendarNotifyDelegatedChanges,checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarNotifyDelegatedChanges, trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefCalendarAlwaysShowMiniCal, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefCalendarAlwaysShowMiniCal,checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarAlwaysShowMiniCal, trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefCalendarUseQuickAdd, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefCalendarUseQuickAdd,checkBoxLabel:ZaMsg.LBL_zmailPrefCalendarUseQuickAdd, trueValue:"TRUE", falseValue:"FALSE"},
                                    {ref:ZaAccount.A_zmailPrefUseTimeZoneListInCalendar, type:_SUPER_WIZ_CHECKBOX_,
                                        colSpan:2, colSizes:["200px", "300px", "*"],
                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.LBL_zmailPrefUseTimeZoneListInCalendar,checkBoxLabel:ZaMsg.LBL_zmailPrefUseTimeZoneListInCalendar,trueValue:"TRUE", falseValue:"FALSE"}
							]
						});							
		}			
		cases.push({type:_CASE_, caseKey:ZaNewAccountXWizard.PREFS_STEP, tabGroupKey:ZaNewAccountXWizard.PREFS_STEP, 
					numCols:1, width:"680", items :prefItems});
	}	

	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.SKIN_TAB_ATTRS, ZaAccountXFormView.SKIN_TAB_RIGHTS) &&
        !ZaSettings.isOctopus()) {
		ZaNewAccountXWizard.SKINS_STEP = ++this.TAB_INDEX;		
		this.stepChoices.push({value:ZaNewAccountXWizard.SKINS_STEP, label:ZaMsg.TABT_Themes});

		cases.push({type:_CASE_, caseKey:ZaNewAccountXWizard.SKINS_STEP, tabGroupKey:ZaNewAccountXWizard.SKINS_STEP, id:"account_form_themes_step", numCols:1, width:"100%",  
						items: [	
							{type:_GROUP_, 
								items:[
								  {ref:ZaAccount.A_zmailPrefSkin, type:_SUPERWIZ_SELECT1_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPrefSkin,label:ZaMsg.LBL_zmailPrefSkin, labelLocation:_LEFT_, 
									choices:ZaNewAccountXWizard.themeChoices,
									visibilityChecks:[ZaAccountXFormView.gotSkins]                                     
                                  },
                                  {type:_OUTPUT_,ref:ZaAccount.A_zmailPrefSkin,label:ZaMsg.LBL_zmailPrefSkin, labelLocation:_LEFT_, 
                                 	  visibilityChecks:[ZaAccountXFormView.gotNoSkins]
                                  }                                  
								] 
							},
							{type:_SPACER_},
							{type:_SUPER_WIZ_SELECT_CHECK_,
								selectRef:ZaAccount.A_zmailAvailableSkin, 
								ref:ZaAccount.A_zmailAvailableSkin, 
								choices:ZaNewAccountXWizard.themeChoices,
								visibilityChecks:[Case_XFormItem.prototype.isCurrentTab,ZaAccountXFormView.gotSkins],
								visibilityChangeEventSources:[ZaModel.currentStep],
								caseKey:ZaNewAccountXWizard.SKINS_STEP, caseVarRef:ZaModel.currentStep,
								limitLabel:ZaMsg.NAD_LimitThemesTo
							},
							{type:_DWT_ALERT_,colSpan:2,style: DwtAlert.WARNING, iconVisible:true,
								visibilityChecks:[ZaAccountXFormView.gotNoSkins],
								value:ZaMsg.ERROR_CANNOT_FIND_SKINS_FOR_ACCOUNT
							}
						]
		});			
	}
	
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.ZIMLET_TAB_ATTRS, ZaAccountXFormView.ZIMLET_TAB_RIGHTS)) {
		ZaNewAccountXWizard.ZIMLETS_STEP = ++this.TAB_INDEX;			
		this.stepChoices.push({value:ZaNewAccountXWizard.ZIMLETS_STEP, label:ZaMsg.TABT_Zimlets});

		cases.push({type:_CASE_, caseKey:ZaNewAccountXWizard.ZIMLETS_STEP, tabGroupKey:ZaNewAccountXWizard.ZIMLETS_STEP, id:"account_form_zimlets_step", numCols:1, width:"100%", 
						items: [	
							{type:_ZAWIZGROUP_, numCols:1,colSizes:["auto"], 
								items: [
									{type:_SUPER_WIZ_ZIMLET_SELECT_,
										selectRef:ZaAccount.A_zmailZimletAvailableZimlets, 
										ref:ZaAccount.A_zmailZimletAvailableZimlets, 
										choices:ZaNewAccountXWizard.zimletChoices,
										limitLabel:ZaMsg.NAD_LimitZimletsTo
									}
								]
							}							
						]
		});			
	}
		
	if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.ADVANCED_TAB_ATTRS, ZaAccountXFormView.ADVANCED_TAB_RIGHTS) &&
        !ZaSettings.isOctopus()) {
		ZaNewAccountXWizard.ADVANCED_STEP = ++this.TAB_INDEX;			
		this.stepChoices.push({value:ZaNewAccountXWizard.ADVANCED_STEP, label:ZaMsg.TABT_Advanced});
		advancedCaseItems = [];
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailAttachmentsBlocked],[])) {				
			advancedCaseItems.push({type:_ZAWIZ_TOP_GROUPER_, id:"account_attachment_settings",colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_AttachmentsGrouper,						
							items :[
								{ref:ZaAccount.A_zmailAttachmentsBlocked, type:_SUPER_WIZ_CHECKBOX_,
                                    colSizes:["200px", "300px", "*"], colSpan:3,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.NAD_RemoveAllAttachments,
									checkBoxLabel:ZaMsg.NAD_RemoveAllAttachments, 
									trueValue:"TRUE", falseValue:"FALSE"
								}
							]
						});
		}
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailMailQuota,ZaAccount.A_zmailContactMaxNumEntries,
			ZaAccount.A_zmailQuotaWarnPercent,ZaAccount.A_zmailQuotaWarnInterval,ZaAccount.A_zmailQuotaWarnMessage],[])) {						
			advancedCaseItems.push({type:_ZAWIZ_TOP_GROUPER_, id:"account_quota_settings",colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_QuotaGrouper,						
							items: [
								{ref:ZaAccount.A_zmailMailForwardingAddressMaxLength, type:_SUPERWIZ_TEXTFIELD_,
                                    colSizes:["200px", "300px", "*"], colSpan: 1,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMailForwardingAddressMaxLength,
									txtBoxLabel:ZaMsg.LBL_zmailMailForwardingAddressMaxLength, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input"
								},
								{ref:ZaAccount.A_zmailMailForwardingAddressMaxNumAddrs, type:_SUPERWIZ_TEXTFIELD_,
                                    colSizes:["200px", "300px", "*"], colSpan: 1,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMailForwardingAddressMaxNumAddrs,
									txtBoxLabel:ZaMsg.LBL_zmailMailForwardingAddressMaxNumAddrs, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input"
								},							
								{ref:ZaAccount.A_zmailMailQuota, type:_SUPERWIZ_TEXTFIELD_,
                                    colSizes:["200px", "300px", "*"],colSpan: 1,
									txtBoxLabel:ZaMsg.LBL_zmailMailQuota, msgName:ZaMsg.MSG_zmailMailQuota,labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input", 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailContactMaxNumEntries, type:_SUPERWIZ_TEXTFIELD_,
                                    colSizes:["200px", "300px", "*"], colSpan: 1,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailContactMaxNumEntries,
									txtBoxLabel:ZaMsg.LBL_zmailContactMaxNumEntries, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input"
								},
								{ref:ZaAccount.A_zmailQuotaWarnPercent, type:_SUPERWIZ_TEXTFIELD_,
                                    colSizes:["200px", "300px", "*"], colSpan: 1,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									txtBoxLabel:ZaMsg.LBL_zmailQuotaWarnPercent, msgName:ZaMsg.MSG_zmailQuotaWarnPercent,labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input", 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailQuotaWarnInterval, type:_SUPERWIZ_LIFETIME_,
                                    colSizes:["200px", "80px", "220px", "*"], colSpan: 1,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									textFieldCssClass:"admin_xform_number_input", 
									txtBoxLabel:ZaMsg.LBL_zmailQuotaWarnInterval, msgName:ZaMsg.MSG_zmailQuotaWarnInterval,labelLocation:_LEFT_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailQuotaWarnMessage, type:_SUPERWIZ_TEXTAREA_,
                                    colSizes:["200px", "300px", "*"], colSpan: 1,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									txtBoxLabel:ZaMsg.LBL_zmailQuotaWarnMessage, 
									msgName:ZaMsg.MSG_zmailQuotaWarnMessage,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								}
							]
						});
		}

		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ 
								ZaAccount.A_zmailDataSourceMinPollingInterval,
								ZaAccount.A_zmailDataSourcePop3PollingInterval,
								ZaAccount.A_zmailDataSourceImapPollingInterval,
								ZaAccount.A_zmailDataSourceCalendarPollingInterval,
								ZaAccount.A_zmailDataSourceRssPollingInterval,
								ZaAccount.A_zmailDataSourceCaldavPollingInterval],[])) {						
			advancedCaseItems.push({type:_ZAWIZ_TOP_GROUPER_, id:"account_datasourcepolling_settings",colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_DataSourcePolling,						
							items: [
                                                                {ref:ZaAccount.A_zmailDataSourceMinPollingInterval, type:_SUPERWIZ_LIFETIME_,
                                                                        colSizes:["200px","80px","220px","*"],
                                                                        msgName:ZaMsg.MSG_zmailDataSourceMinPollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourceMinPollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                                                        nowrap:false,labelWrap:true
                                                                },
                                                                {ref:ZaAccount.A_zmailDataSourcePop3PollingInterval, type:_SUPERWIZ_LIFETIME_,
                                                                        colSizes:["200px","80px","220px","*"],
                                                                        msgName:ZaMsg.MSG_zmailDataSourcePop3PollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourcePop3PollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                                                        nowrap:false,labelWrap:true
                                                                },
                                                                {ref:ZaAccount.A_zmailDataSourceImapPollingInterval, type:_SUPERWIZ_LIFETIME_,
                                                                        colSizes:["200px","80px","220px","*"],
                                                                        msgName:ZaMsg.MSG_zmailDataSourceImapPollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourceImapPollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                                                        nowrap:false,labelWrap:true
                                                                },
                                                                {ref:ZaAccount.A_zmailDataSourceCalendarPollingInterval, type:_SUPERWIZ_LIFETIME_,
                                                                        colSizes:["200px","80px","220px","*"],
                                                                        msgName:ZaMsg.MSG_zmailDataSourceCalendarPollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourceCalendarPollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                                                        nowrap:false,labelWrap:true
                                                                },
                                                                {ref:ZaAccount.A_zmailDataSourceRssPollingInterval, type:_SUPERWIZ_LIFETIME_,
                                                                        colSizes:["200px","80px","220px","*"],
                                                                        msgName:ZaMsg.MSG_zmailDataSourceRssPollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourceRssPollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                                                        nowrap:false,labelWrap:true
                                                                },
                                                                {ref:ZaAccount.A_zmailDataSourceCaldavPollingInterval, type:_SUPERWIZ_LIFETIME_,
                                                                        colSizes:["200px","80px","220px","*"],
                                                                        msgName:ZaMsg.MSG_zmailDataSourceCaldavPollingInterval,
                                                                        txtBoxLabel:ZaMsg.LBL_zmailDataSourceCaldavPollingInterval,
                                                                        resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                                                        nowrap:false,labelWrap:true
                                                                }

							]
						});
		}

				if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailProxyAllowedDomains],[])) {						
			          advancedCaseItems.push({type:_ZAWIZ_TOP_GROUPER_, id:"account_proxyalloweddomains_settings",colSizes:["200px","auto"],numCols:2,
							label:ZaMsg.NAD_ProxyAllowedDomains,
							items: [
              							{ ref: ZaAccount.A_zmailProxyAllowedDomains,
                                               label:ZaMsg.LBL_zmailProxyAllowedDomains,
                                               labelCssStyle:"vertical-align:top;",
                                               type:_SUPER_REPEAT_,
                                               colSizes:["300px", "*"],
                                               resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                               repeatInstance:"",
                                               addButtonLabel:ZaMsg.NAD_ProxyAddAllowedDomain ,
                                               removeButtonLabel: ZaMsg.NAD_ProxyRemoveAllowedDomain,
                                               showAddButton:true,
                                               showRemoveButton:true,
                                               showAddOnNextRow:true,
                                               repeatItems: [
                                                  { ref:".", type:_TEXTFIELD_,
                                                    enableDisableChecks:[ZaItem.hasWritePermission] ,
                                                    visibilityChecks:[ZaItem.hasReadPermission],
                                                    width: "15em"
                                                  }
                                               ]
                                        }
							]
						});
		}
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailPasswordLocked,ZaAccount.A_zmailMinPwdLength,
			ZaAccount.A_zmailMaxPwdLength,ZaAccount.A_zmailPasswordMinUpperCaseChars,ZaAccount.A_zmailPasswordMinLowerCaseChars,
			ZaAccount.A_zmailPasswordMinPunctuationChars,ZaAccount.A_zmailPasswordMinNumericChars,ZaAccount.A_zmailPasswordMinDigitsOrPuncs,
			ZaAccount.A_zmailMinPwdAge,ZaAccount.A_zmailMaxPwdAge,ZaAccount.A_zmailEnforcePwdHistory],[])) {						
			advancedCaseItems.push({type:_ZAWIZ_TOP_GROUPER_,id:"account_password_settings",colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_PasswordGrouper,				
							items: [
						        { type: _DWT_ALERT_, containerCssStyle: "padding-bottom:0;",
						            style: DwtAlert.WARNING,iconVisible: false,
						            content: ZaMsg.Alert_InternalPassword, colSpan:3
						        },

								{ref:ZaAccount.A_zmailPasswordLocked, 
									type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.NAD_PwdLocked,checkBoxLabel:ZaMsg.NAD_PwdLocked, 
									trueValue:"TRUE", falseValue:"FALSE",
                                    colSizes:["200px", "300px", "*"],
									visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailMinPwdLength, 
									type:_SUPERWIZ_TEXTFIELD_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMinPwdLength,
									txtBoxLabel:ZaMsg.LBL_zmailMinPwdLength, 
									labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
                                    colSizes:["200px", "300px", "*"],
									visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailMaxPwdLength, type:_SUPERWIZ_TEXTFIELD_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailMaxPwdLength,
									txtBoxLabel:ZaMsg.LBL_zmailMaxPwdLength, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
                                    colSizes:["200px", "300px", "*"],
									visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailPasswordMinUpperCaseChars, 
									type:_SUPERWIZ_TEXTFIELD_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPasswordMinUpperCaseChars,
									txtBoxLabel:ZaMsg.LBL_zmailPasswordMinUpperCaseChars, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
                                    colSizes:["200px", "300px", "*"],
									visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailPasswordMinLowerCaseChars,
									type:_SUPERWIZ_TEXTFIELD_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPasswordMinLowerCaseChars,
									txtBoxLabel:ZaMsg.LBL_zmailPasswordMinLowerCaseChars, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
                                    colSizes:["200px", "300px", "*"],
									visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailPasswordMinPunctuationChars,  
									type:_SUPERWIZ_TEXTFIELD_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPasswordMinPunctuationChars,
									txtBoxLabel:ZaMsg.LBL_zmailPasswordMinPunctuationChars, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
                                    colSizes:["200px", "300px", "*"],
									visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailPasswordMinNumericChars, 
									type:_SUPERWIZ_TEXTFIELD_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPasswordMinNumericChars,
									txtBoxLabel:ZaMsg.LBL_zmailPasswordMinNumericChars, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
                                    colSizes:["200px", "300px", "*"],
									visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailPasswordMinDigitsOrPuncs, 
									type:_SUPERWIZ_TEXTFIELD_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailPasswordMinDigitsOrPuncs,
									txtBoxLabel:ZaMsg.LBL_zmailPasswordMinDigitsOrPuncs, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
                                    colSizes:["200px", "300px", "*"],
									visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
								},
																							
								{ref:ZaAccount.A_zmailMinPwdAge, 
									type:_SUPERWIZ_TEXTFIELD_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_passMinAge,txtBoxLabel:ZaMsg.LBL_passMinAge, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
                                    colSizes:["200px", "300px", "*"],
									visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailMaxPwdAge, 
									type:_SUPERWIZ_TEXTFIELD_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_passMaxAge,txtBoxLabel:ZaMsg.LBL_passMaxAge, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
                                    colSizes:["200px", "300px", "*"],
									visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
								},
								{ref:ZaAccount.A_zmailEnforcePwdHistory, 
									type:_SUPERWIZ_TEXTFIELD_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailEnforcePwdHistory,
									txtBoxLabel:ZaMsg.LBL_zmailEnforcePwdHistory, labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input",
                                    colSizes:["200px", "300px", "*"],
									visibilityChecks:[],enableDisableChecks:[[ZaNewAccountXWizard.isAuthfromInternal, domainName,ZaAccount.A_name]]
								}
							]
						});
		}
		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,[ZaAccount.A_zmailPasswordLockoutEnabled,ZaAccount.A_zmailPasswordLockoutMaxFailures,
			ZaAccount.A_zmailPasswordLockoutDuration,ZaAccount.A_zmailPasswordLockoutFailureLifetime],[])) {						
			advancedCaseItems.push({type:_ZAWIZ_TOP_GROUPER_, id:"password_lockout_settings",colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_FailedLoginGrouper,							
							items :[

								{ref:ZaAccount.A_zmailPasswordLockoutEnabled, type:_SUPER_WIZ_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.LBL_zmailPasswordLockoutEnabled,checkBoxLabel:ZaMsg.LBL_zmailPasswordLockoutEnabled, 
                                    colSizes:["200px", "300px", "*"],colSpan:1, trueValue:"TRUE", falseValue:"FALSE"
								},
								{ref:ZaAccount.A_zmailPasswordLockoutMaxFailures, type:_SUPERWIZ_TEXTFIELD_, 
									enableDisableChecks: [[ZaAccountXFormView.isPasswordLockoutEnabled],[XForm.checkInstanceValue,ZaAccount.A_zmailPasswordLockoutEnabled,"TRUE"]],
								 	enableDisableChangeEventSources:[ZaAccount.A_zmailPasswordLockoutEnabled,ZaAccount.A_COSId, ZaAccount.A_zmailPasswordLockoutEnabled],
									txtBoxLabel:ZaMsg.LBL_zmailPasswordLockoutMaxFailures,
									toolTipContent:ZaMsg.TTP_zmailPasswordLockoutMaxFailuresSub,
									msgName:ZaMsg.MSG_zmailPasswordLockoutMaxFailures,
									labelLocation:_LEFT_, 
									textFieldCssClass:"admin_xform_number_input", 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                    colSizes:["200px", "300px", "*"],colSpan:1
								},
								{ref:ZaAccount.A_zmailPasswordLockoutDuration, type:_SUPERWIZ_LIFETIME_, 
									enableDisableChecks: [[ZaAccountXFormView.isPasswordLockoutEnabled],[XForm.checkInstanceValue,ZaAccount.A_zmailPasswordLockoutEnabled,"TRUE"]],
								 	enableDisableChangeEventSources:[ZaAccount.A_zmailPasswordLockoutEnabled,ZaAccount.A_COSId, ZaAccount.A_zmailPasswordLockoutEnabled],
									txtBoxLabel:ZaMsg.LBL_zmailPasswordLockoutDuration,
									toolTipContent:ZaMsg.TTP_zmailPasswordLockoutDurationSub,
									msgName:ZaMsg.MSG_zmailPasswordLockoutDuration,
									textFieldCssClass:"admin_xform_number_input",
                                    colSizes:["200px", "80px", "220px", "*"],colSpan:1,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS
								},
								{ref:ZaAccount.A_zmailPasswordLockoutFailureLifetime, type:_SUPERWIZ_LIFETIME_, 
									enableDisableChecks: [[ZaAccountXFormView.isPasswordLockoutEnabled],[XForm.checkInstanceValue,ZaAccount.A_zmailPasswordLockoutEnabled,"TRUE"]],
								 	enableDisableChangeEventSources:[ZaAccount.A_zmailPasswordLockoutEnabled,ZaAccount.A_COSId, ZaAccount.A_zmailPasswordLockoutEnabled],
									txtBoxLabel:ZaMsg.LBL_zmailPasswordLockoutFailureLifetime,
									toolTipContent:ZaMsg.TTP_zmailPasswordLockoutFailureLifetimeSub,
									msgName:ZaMsg.MSG_zmailPasswordLockoutFailureLifetime,
									textFieldCssClass:"admin_xform_number_input", 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									labelCssStyle:"white-space:normal;",
                                    colSizes:["200px", "80px", "220px", "*"],colSpan:1,
									nowrap:false,labelWrap:true
								}								
							]
						});
		}

		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,
			[
			ZaAccount.A_zmailAdminAuthTokenLifetime,
			ZaAccount.A_zmailAuthTokenLifetime,
			ZaAccount.A_zmailMailIdleSessionTimeout,
			ZaAccount.A_zmailDumpsterUserVisibleAge
			],[])) {
			advancedCaseItems.push({type:_ZAWIZ_TOP_GROUPER_, colSizes:["auto"],numCols:1,
							label:ZaMsg.NAD_TimeoutGrouper,	id:"timeout_settings",
							items: [
								{ref:ZaAccount.A_zmailAdminAuthTokenLifetime,
									type:_SUPERWIZ_LIFETIME_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailAdminAuthTokenLifetime,
									txtBoxLabel:ZaMsg.LBL_zmailAdminAuthTokenLifetime,
									enableDisableChecks:[ZaAccountXFormView.isAdminAccount],
                                    colSizes:["200px", "80px", "220px", "*"],colSpan:1,
									enableDisableChangeEventSources:[ZaAccount.A_zmailIsAdminAccount]
								},							
								{ref:ZaAccount.A_zmailAuthTokenLifetime,
									type:_SUPERWIZ_LIFETIME_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailAuthTokenLifetime,
                                    colSizes:["200px", "80px", "220px", "*"],colSpan:1,
									txtBoxLabel:ZaMsg.LBL_zmailAuthTokenLifetime},								
								{ref:ZaAccount.A_zmailMailIdleSessionTimeout, 
									type:_SUPERWIZ_LIFETIME_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMailIdleSessionTimeout,
                                    colSizes:["200px", "80px", "220px", "*"],colSpan:1,
									txtBoxLabel:ZaMsg.LBL_zmailMailIdleSessionTimeout},
								{ref:ZaAccount.A_zmailDumpsterUserVisibleAge, type:_SUPERWIZ_LIFETIME_,
                                    colSizes:["200px", "80px", "220px", "*"],colSpan:1,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailDumpsterUserVisibleAge, 
									txtBoxLabel:ZaMsg.LBL_zmailDumpsterUserVisibleAge,
									visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaAccount.A_zmailDumpsterEnabled, "TRUE"]],
									visibilityChangeEventSources:[ZaAccount.A_zmailDumpsterEnabled]
								}


							]
						});
		}


		if(ZAWizTopGrouper_XFormItem.isGroupVisible(entry,
			[
				ZaAccount.A_zmailMailMessageLifetime,
				ZaAccount.A_zmailMailTrashLifetime,
				ZaAccount.A_zmailMailSpamLifetime,
				ZaAccount.A_zmailMailDumpsterLifetime
			],[])) {
			advancedCaseItems.push({ type:_ZAWIZ_TOP_GROUPER_, colSizes:["auto"], numCols:1,
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
								{ref:ZaAccount.A_zmailMailMessageLifetime, type:_SUPERWIZ_LIFETIME2_, 
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMailMessageLifetime,
                                    colSizes:["200px", "80px", "220px", "*"],colSpan:1,
                                    visibilityChecks:[[ZaItem.hasReadPermission], [ZaAccount.isEmailRetentionPolicyEnabled]],
									txtBoxLabel:ZaMsg.LBL_zmailMailMessageLifetime},
								{ref:ZaAccount.A_zmailMailTrashLifetime, type:_SUPERWIZ_LIFETIME1_,
                                    colSizes:["200px", "80px", "220px", "*"],colSpan:1,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, msgName:ZaMsg.MSG_zmailMailTrashLifetime,
                                    visibilityChecks:[[ZaItem.hasReadPermission], [ZaAccount.isEmailRetentionPolicyEnabled]],
									txtBoxLabel:ZaMsg.LBL_zmailMailTrashLifetime},
								{ref:ZaAccount.A_zmailMailSpamLifetime, type:_SUPERWIZ_LIFETIME1_,
                                    colSizes:["200px", "80px", "220px", "*"],colSpan:1,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMailSpamLifetime,
                                    visibilityChecks:[[ZaItem.hasReadPermission], [ZaAccount.isEmailRetentionPolicyEnabled]],
									txtBoxLabel:ZaMsg.LBL_zmailMailSpamLifetime},
								{ref:ZaAccount.A_zmailMailDumpsterLifetime, type:_SUPERWIZ_LIFETIME1_,
									colSizes:["200px", "80px", "220px", "*"],colSpan:1,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS, 
									msgName:ZaMsg.MSG_zmailMailDumpsterLifetime,
									txtBoxLabel:ZaMsg.LBL_zmailMailDumpsterLifetime,
									visibilityChecks:[[ZaItem.hasReadPermission], [ZaAccount.isEmailRetentionPolicyEnabled], [XForm.checkInstanceValue, ZaAccount.A_zmailDumpsterEnabled, "TRUE"], [XForm.checkInstanceValue, ZaAccount.A_zmailDumpsterPurgeEnabled, "TRUE"]],
									visibilityChangeEventSources:[ZaAccount.A_zmailDumpsterEnabled, ZaAccount.A_zmailDumpsterPurgeEnabled]
								}
							]
						});
		}

		cases.push({type:_CASE_, caseKey:ZaNewAccountXWizard.ADVANCED_STEP, tabGroupKey:ZaNewAccountXWizard.ADVANCED_STEP, id:"account_form_advanced_step", numCols:1, width:"100%", 
				items:advancedCaseItems});
	}
	this._lastStep = this.stepChoices.length;
	xFormObject.items = [
			{type:_OUTPUT_, colSpan:2, align:_CENTER_, valign:_TOP_, ref:ZaModel.currentStep, choices:this.stepChoices, valueChangeEventSources:[ZaModel.currentStep]},
			{type:_SEPARATOR_, align:_CENTER_, valign:_TOP_},
			{type:_SPACER_,  align:_CENTER_, valign:_TOP_},
			{type:_SWITCH_, width:680, align:_LEFT_, valign:_TOP_, items:cases}
		];
};
ZaXDialog.XFormModifiers["ZaNewAccountXWizard"].push(ZaNewAccountXWizard.myXFormModifier);
