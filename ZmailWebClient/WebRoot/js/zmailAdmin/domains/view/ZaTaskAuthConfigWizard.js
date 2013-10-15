/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2011, 2012 VMware, Inc.
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
/**
 * Created by IntelliJ IDEA.
 * User: qinan
 * Date: 9/23/11
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */


ZaTaskAuthConfigWizard = function(parent) {

	ZaXWizardDialog.call(this, parent, null, ZaMsg.NCD_AuthConfigTitle,"700px", "350px","ZaTaskAuthConfigWizard", null, ZaId.DLG_AUTH_CONFIG);

	this.AuthMechs = [
		{label:"<b>" + ZaMsg.AuthMech_zmail + "</b>", value:ZaDomain.AuthMech_zmail},
		{label:"<b>" + ZaMsg.AuthMech_ldap + "</b>", value:ZaDomain.AuthMech_ldap},
		{label:"<b>" + ZaMsg.AuthMech_ad + "</b>", value:ZaDomain.AuthMech_ad}
	];

	this.TestResultChoices = [
		{label:ZaMsg.AuthTest_check_OK, value:ZaDomain.Check_OK},
		{label:ZaMsg.AuthTest_check_UNKNOWN_HOST, value:ZaDomain.Check_UNKNOWN_HOST},
		{label:ZaMsg.AuthTest_check_CONNECTION_REFUSED, value:ZaDomain.Check_CONNECTION_REFUSED},
		{label:ZaMsg.AuthTest_check_SSL_HANDSHAKE_FAILURE, value:ZaDomain.Check_SSL_HANDSHAKE_FAILURE},
		{label:ZaMsg.AuthTest_check_COMMUNICATION_FAILURE, value:ZaDomain.Check_COMMUNICATION_FAILURE},
		{label:ZaMsg.AuthTest_check_AUTH_FAILED, value:ZaDomain.Check_AUTH_FAILED},
		{label:ZaMsg.AuthTest_check_AUTH_NOT_SUPPORTED, value:ZaDomain.Check_AUTH_NOT_SUPPORTED},
		{label:ZaMsg.AuthTest_check_NAME_NOT_FOUND, value:ZaDomain.Check_NAME_NOT_FOUND},
		{label:ZaMsg.AuthTest_check_INVALID_SEARCH_FILTER, value:ZaDomain.Check_INVALID_SEARCH_FILTER},
		{label:ZaMsg.AuthTest_check_FAILURE, value:ZaDomain.Check_FAILURE}
	];
	this.TAB_INDEX = 0;

    ZaTaskAuthConfigWizard.AUTH_CONFIG_CHOOSE_MODE_STEP = ++this.TAB_INDEX;
	ZaTaskAuthConfigWizard.AUTH_CONFIG_AUTH_SET_STEP = ++this.TAB_INDEX;
	ZaTaskAuthConfigWizard.AUTH_CONFIG_BIND_PWD_STEP = ++this.TAB_INDEX;
	ZaTaskAuthConfigWizard.AUTH_CONFIG_SUMMARY_STEP = ++this.TAB_INDEX;
    ZaTaskAuthConfigWizard.EXTERNAL_LDAP_GROUP_STEP = ++this.TAB_INDEX;
    /* bug 71234, remove SPNEGO
     ZaTaskAuthConfigWizard.SPNEGO_CONFIG_STEP = ++this.TAB_INDEX;
    ZaTaskAuthConfigWizard.SPNEGO_CONFIG_STEP_2 = ++this.TAB_INDEX; */
	ZaTaskAuthConfigWizard.CONFIG_COMPLETE_STEP = ++this.TAB_INDEX;
	
	ZaTaskAuthConfigWizard.AUTH_CONFIG_START_STEP = ZaTaskAuthConfigWizard.AUTH_CONFIG_CHOOSE_MODE_STEP;
	
	this.STEPS = [ZaTaskAuthConfigWizard.AUTH_CONFIG_START_STEP];

	this.stepChoices = [
        {label:ZaMsg.stepAuthModeForDomain, value:ZaTaskAuthConfigWizard.AUTH_CONFIG_CHOOSE_MODE_STEP},
		{label:ZaMsg.stepAuthSetting, value:ZaTaskAuthConfigWizard.AUTH_CONFIG_AUTH_SET_STEP},
		{label:ZaMsg.stepAuthBindLDAP, value:ZaTaskAuthConfigWizard.AUTH_CONFIG_BIND_PWD_STEP},
		{label:ZaMsg.stepAuthSummary, value:ZaTaskAuthConfigWizard.AUTH_CONFIG_SUMMARY_STEP},
		{label:ZaMsg.NAD_ExternalGroup_Setting, value:ZaTaskAuthConfigWizard.EXTERNAL_LDAP_GROUP_STEP},
        /* bug 71234, remove SPNEGO
		{label:ZaMsg.AuthSetting_Spnego, value:ZaTaskAuthConfigWizard.SPNEGO_CONFIG_STEP},
        {label:ZaMsg.AuthSetting_SpnegoDomain, value:ZaTaskAuthConfigWizard.SPNEGO_CONFIG_STEP_2},
        */
		{label:ZaMsg.DomainConfigComplete, value:ZaTaskAuthConfigWizard.CONFIG_COMPLETE_STEP}
	];

	this.initForm(ZaDomain.myXModel,this.getMyXForm());
	this._localXForm.addListener(DwtEvent.XFORMS_FORM_DIRTY_CHANGE, new AjxListener(this, ZaTaskAuthConfigWizard.prototype.handleXFormChange));
	this._localXForm.addListener(DwtEvent.XFORMS_VALUE_ERROR, new AjxListener(this, ZaTaskAuthConfigWizard.prototype.handleXFormChange));
	this.lastErrorStep=0;
	this._helpURL = location.pathname + ZaUtil.HELP_URL + "managing_domains/authentication_settings.htm?locid="+AjxEnv.DEFAULT_LOCALE;
}

ZaTaskAuthConfigWizard.prototype = new ZaXWizardDialog;
ZaTaskAuthConfigWizard.prototype.constructor = ZaTaskAuthConfigWizard;
ZaTaskAuthConfigWizard.prototype.cacheDialog = false;
ZaXDialog.XFormModifiers["ZaTaskAuthConfigWizard"] = new Array();

if(ZaDomain) {
    ZaDomain.A2_zmailSpnegoAuthRealm = "zmailSpnegoAuthRealm";
    ZaDomain.A2_zmailSpnegoAuthErrorURL = "zmailSpnegoAuthErrorURL";
    ZaDomain.A2_zmailSpnegoGlobalAuthEnabled = "zmailSpnegoGlobalAuthEnabled";
    ZaDomain.A2_zmailSpnegoAuthSummary = "zmailSpnegoAuthSummary";
    ZaDomain.A2_zmailSpnegoApplyFor = "zmailSpnegoApplyFor";
    ZaDomain.A2_zmailSpnegoAuthPrincipal = "zmailSpnegoAuthPrincipal";
    ZaDomain.A2_zmailSpnegoAuthTargetName = "zmailSpnegoAuthTargetName";
    ZaDomain.A2_zmailSpnegoUAAllBrowsers = "zmailSpnegoUA_AllBrowsers";
    ZaDomain.A2_zmailSpnegoUASupportedBrowsers = "zmailSpnegoUA_SupportedBrowsers";
    ZaDomain.A2_zmailSpnegoUACustomBrowsers = "zmailSpnegoUA_CustomBrowsers";
    ZaDomain.A2_zmailWebClientURLAllowedUA = "zmailWebClientURLAllowedUA";
    ZaDomain.A2_zmailSpnegoTargetServer = "zmailSpnegoTargetServer";
    ZaDomain.A2_zmailSpnegoGlobalSettingStatus = "zmailSpnegoGlobalSettingStatus";

    ZaDomain.A_zmailExternalGroupLdapSearchBase = "zmailExternalGroupLdapSearchBase";
    ZaDomain.A_zmailExternalGroupLdapSearchFilter = "zmailExternalGroupLdapSearchFilter";
    ZaDomain.A_zmailExternalGroupHandlerClass = "zmailExternalGroupHandlerClass";
    ZaDomain.A_zmailAuthMechAdmin = "zmailAuthMechAdmin";
    ZaDomain.A2_zmailExternalGroupLdapEnabled = "zmailExternalGroupLdapEnabled";
    ZaDomain.A2_zmailAuthConfigTestStatus = "zmailAuthConfigTestStatus";
    if(ZaDomain.myXModel) {
        ZaDomain.myXModel.items.push(
            {id:ZaDomain.A2_zmailSpnegoApplyFor, ref:ZaDomain.A2_zmailSpnegoApplyFor, type: _STRING_},
            {id:ZaDomain.A2_zmailSpnegoGlobalSettingStatus, ref:ZaDomain.A2_zmailSpnegoGlobalSettingStatus, type: _STRING_},
            {id:ZaDomain.A2_zmailWebClientURLAllowedUA, ref:ZaDomain.A2_zmailWebClientURLAllowedUA, type: _STRING_},
            {id:ZaDomain.A2_zmailSpnegoTargetServer, type:_STRING_ , ref: ZaDomain.A2_zmailSpnegoTargetServer},
            {id:ZaDomain.A2_zmailSpnegoAuthPrincipal, ref:ZaDomain.A2_zmailSpnegoAuthPrincipal, type: _STRING_},
            {id:ZaDomain.A2_zmailSpnegoAuthRealm, ref: ZaDomain.A2_zmailSpnegoAuthRealm, type: _STRING_ },
            {id:ZaDomain.A2_zmailSpnegoAuthTargetName, ref: ZaDomain.A2_zmailSpnegoAuthTargetName, type: _STRING_ },
            {id:ZaDomain.A2_zmailSpnegoAuthErrorURL, ref: ZaDomain.A2_zmailSpnegoAuthErrorURL, type: _STRING_ },
            {id:ZaDomain.A2_zmailSpnegoGlobalAuthEnabled, ref:ZaDomain.A2_zmailSpnegoGlobalAuthEnabled, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES},
            {id:ZaDomain.A2_zmailSpnegoAuthSummary, ref:ZaDomain.A2_zmailSpnegoAuthSummary, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES},
            {id:ZaDomain.A2_zmailSpnegoUAAllBrowsers, ref:ZaDomain.A2_zmailSpnegoUAAllBrowsers, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
            {id:ZaDomain.A2_zmailSpnegoUASupportedBrowsers, ref:ZaDomain.A2_zmailSpnegoUASupportedBrowsers, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
            {id:ZaDomain.A2_zmailSpnegoUACustomBrowsers, ref:ZaDomain.A2_zmailSpnegoUACustomBrowsers, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
            {id:ZaDomain.A2_zmailAuthConfigTestStatus, ref:ZaDomain.A2_zmailAuthConfigTestStatus, type:_ENUM_, choices:["STANDBY", "RUNNING", "RUNNED"]}
        );
        ZaDomain.myXModel.items.push(
            {id:ZaDomain.A2_zmailExternalGroupLdapEnabled, ref: ZaDomain.A2_zmailExternalGroupLdapEnabled, type: _ENUM_, choices: ZaModel.BOOLEAN_CHOICES},
            {id:ZaDomain.A_zmailExternalGroupLdapSearchBase, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailExternalGroupLdapSearchBase},
            {id:ZaDomain.A_zmailExternalGroupLdapSearchFilter, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailExternalGroupLdapSearchFilter},
            {id:ZaDomain.A_zmailExternalGroupHandlerClass, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailExternalGroupHandlerClass},
            {id:ZaDomain.A_zmailAuthMechAdmin, type:_STRING_, ref:"attrs/" + ZaDomain.A_zmailAuthMechAdmin}
        );
    }
    
    ZaTaskAuthConfigWizard.loadExtLdapGroup = function (entry) {
        if (!this.attrs[ZaDomain.A_zmailExternalGroupHandlerClass]) {
            this.attrs[ZaDomain.A_zmailExternalGroupHandlerClass] = "org.zmail.cs.account.grouphandler.ADGroupHandler";
        }
        if (!this.attrs[ZaDomain.A_zmailAuthMechAdmin]) {
            this.attrs[ZaDomain.A_zmailAuthMechAdmin] = ZaDomain.AuthMech_ad;
        }

        if (this.attrs[ZaDomain.A_AuthMech]!=ZaDomain.AuthMech_zmail ) {
            this[ZaDomain.A2_zmailExternalGroupLdapEnabled] = "TRUE";
        } else {
            this[ZaDomain.A2_zmailExternalGroupLdapEnabled] = "FALSE";
        }
    }
    
    ZaTaskAuthConfigWizard.loadAuthConfWizard = function (entry) {
    	this[ZaDomain.A2_zmailAuthConfigTestStatus] = "STANDBY";
    }
    
    ZaItem.loadMethods["ZaDomain"].push(ZaTaskAuthConfigWizard.loadAuthConfWizard);
    ZaItem.loadMethods["ZaDomain"].push(ZaTaskAuthConfigWizard.loadExtLdapGroup);
}

ZaDomain.TARGET_SERVER_CHOICES = [];

ZaTaskAuthConfigWizard.prototype.handleXFormChange =
function () {
	if(this._localXForm.hasErrors()) {
		if(this.lastErrorStep < this._containedObject[ZaModel.currentStep])
			this.lastErrorStep=this._containedObject[ZaModel.currentStep];
	} else {
		this.lastErrorStep=0;
	}
	this.changeButtonStateForStep(this._containedObject[ZaModel.currentStep]);
}

ZaTaskAuthConfigWizard.prototype.changeButtonStateForStep =
function(stepNum) {
    this._button[DwtDialog.CANCEL_BUTTON].setVisible(true);
    this._button[ZaXWizardDialog.HELP_BUTTON].setVisible(true);
    this._button[DwtWizardDialog.FINISH_BUTTON].setVisible(true);

	if(this.lastErrorStep == stepNum) {
		this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
		if(stepNum>1)
			this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
	} else {

		if(stepNum == ZaTaskAuthConfigWizard.AUTH_CONFIG_CHOOSE_MODE_STEP) {
			// first step, prev is disabled
			this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
			this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
			this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(false);
		} else if(stepNum == ZaTaskAuthConfigWizard.CONFIG_COMPLETE_STEP) {
			// last step, next is dsiabled
			this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
			this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
			this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(true);
		} else if (stepNum == ZaTaskAuthConfigWizard.AUTH_CONFIG_AUTH_SET_STEP) {
			// valid check
			var nextEnabled = false;
            var authMode = this._containedObject.attrs[ZaDomain.A_AuthMech];
			if (((authMode == ZaDomain.AuthMech_ad) && !AjxUtil.isEmpty(this._localXForm.getInstanceValue(ZaDomain.A_AuthADDomainName))) ||
				((authMode == ZaDomain.AuthMech_ldap) && !AjxUtil.isEmpty(this._localXForm.getInstanceValue(ZaDomain.A_AuthLdapURL)))) {
				nextEnabled = true;
			}
			this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
			this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(nextEnabled);
			this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);

		} else { // other steps,
			this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
			this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
			this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
		}
	}
}

ZaTaskAuthConfigWizard.prototype.testSetings =
function () {
	if(this._containedObject.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_ad) {
		this._containedObject.attrs[ZaDomain.A_AuthLdapUserDn] = "%u@"+this._containedObject.attrs[ZaDomain.A_AuthADDomainName];
	}

	var callback = new AjxCallback(this, this.checkCallBack);
	ZaDomain.testAuthSettings(this._containedObject, callback);
}

/**
* Callback function invoked by Asynchronous CSFE command when "check" call returns
**/
ZaTaskAuthConfigWizard.prototype.checkCallBack =
function (arg) {
	if(!arg)
		return;
	if(arg.isException()) {
		this._containedObject[ZaDomain.A_AuthTestResultCode] = arg.getException().code;
		this._containedObject[ZaDomain.A_AuthTestMessage] = arg.getException().detail+"\n"+arg.getException().msg;
	} else {
		var response = arg.getResponse().Body.CheckAuthConfigResponse;
		this._containedObject[ZaDomain.A_AuthTestResultCode] = response.code[0]._content;
		if(this._containedObject[ZaDomain.A_AuthTestResultCode] != ZaDomain.Check_OK) {
			this._containedObject[ZaDomain.A_AuthTestMessage] = response.message[0]._content;
		} else {
			if(response.bindDn != null) {
				this._containedObject[ZaDomain.A_AuthComputedBindDn] = response.bindDn[0]._content;
			} else {
				this._containedObject[ZaDomain.A_AuthComputedBindDn] = "";
			}
		}
	}
	this._containedObject[ZaDomain.A2_zmailAuthConfigTestStatus] = "RUNNED";
	this._localXForm.refresh(); // force update visibility
}

/**
* Eevent handlers for form items
**/
ZaTaskAuthConfigWizard.onAuthMechChange =
function (value, event, form) {
	this.setInstanceValue(value);

	if(value == ZaDomain.AuthMech_ad) {
		if(!form.getInstance().attrs[ZaDomain.A_AuthADDomainName]) {
			this.setInstanceValue(form.getInstance().attrs[ZaDomain.A_domainName], ZaDomain.A_AuthADDomainName);
			//form.getInstance().attrs[ZaDomain.A_AuthADDomainName] = form.getInstance().attrs[ZaDomain.A_domainName];
		}
	}
	//form.parent.changeButtonStateForStep(1);

}

/**
* Overwritten methods that control wizard's flow (open, go next,go previous, finish)
**/
ZaTaskAuthConfigWizard.prototype.popup =
function (loc) {
	ZaXWizardDialog.prototype.popup.call(this, loc);
	this.changeButtonStateForStep(ZaTaskAuthConfigWizard.AUTH_CONFIG_CHOOSE_MODE_STEP);

}

ZaTaskAuthConfigWizard.prototype.goPage =
function(pageNum) {
	ZaXWizardDialog.prototype.goPage.call(this, pageNum);
	this.changeButtonStateForStep(pageNum);
}

ZaTaskAuthConfigWizard.prototype.goPrev =
function () {	
	if (this.STEPS.length > 1) {
		this.STEPS.pop();
		this.goPage(this.STEPS[this.STEPS.length - 1]);
	} else {
		this.goPage(this.STEPS[0]);
	}
}

ZaTaskAuthConfigWizard.prototype.goNext =
function() {
	var curStep = this._containedObject[ZaModel.currentStep];
	var nextStep = curStep + 1; // default next is to show the next step
	
	// check the configuration of the current step is correct
	if(curStep == ZaTaskAuthConfigWizard.AUTH_CONFIG_AUTH_SET_STEP &&
	   this._containedObject.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_ldap) {
		var temp = this._containedObject.attrs[ZaDomain.A_AuthLdapURL].join(" ");
		if(this._containedObject.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled] == "TRUE") {
		//check that we don't have ldaps://
			if(temp.indexOf("ldaps://") > -1) {
				ZaApp.getInstance().getCurrentController().popupWarningDialog(ZaMsg.Domain_WarningStartTLSIgnored)
			}	
		}
	}  else if (curStep == ZaTaskAuthConfigWizard.AUTH_CONFIG_BIND_PWD_STEP) {
		//clear the password if the checkbox is unchecked
		if(this._containedObject[ZaDomain.A_AuthUseBindPassword] == "FALSE") {
			this._containedObject.attrs[ZaDomain.A_AuthLdapSearchBindDn] = null;
			this._containedObject.attrs[ZaDomain.A_AuthLdapSearchBindPassword] = null;
			this._containedObject[ZaDomain.A_AuthLdapSearchBindPasswordConfirm] = null;
		}
		//check that passwords match
		if(this._containedObject.attrs[ZaDomain.A_AuthLdapSearchBindPassword] !=
		   this._containedObject[ZaDomain.A_AuthLdapSearchBindPasswordConfirm]) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_PASSWORD_MISMATCH);
			return false;
		}
	}
	
	// wizard step flow control
	if(curStep == ZaTaskAuthConfigWizard.AUTH_CONFIG_CHOOSE_MODE_STEP &&
		this._containedObject.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_zmail) {
		nextStep = ZaTaskAuthConfigWizard.CONFIG_COMPLETE_STEP;
		
	} else if (curStep == ZaTaskAuthConfigWizard.AUTH_CONFIG_SUMMARY_STEP) {
		// External LDAP doesn't support External LDAP Group Config
		if(this._containedObject.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_ad) {
			nextStep = ZaTaskAuthConfigWizard.EXTERNAL_LDAP_GROUP_STEP;
		} else {
			nextStep = ZaTaskAuthConfigWizard.CONFIG_COMPLETE_STEP;
		}
		
	} else if (curStep == ZaTaskAuthConfigWizard.AUTH_CONFIG_AUTH_SET_STEP &&
			   this._containedObject.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_ad) {
		if(!this._containedObject.attrs[ZaDomain.A_AuthLdapURL]) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_LDAP_URL_REQUIRED);
			return false;
		}

	} else if(curStep == ZaTaskAuthConfigWizard.AUTH_TEST_RESULT_STEP) {
		if(this._containedObject.attrs[ZaDomain.A_AuthMech] != ZaDomain.AuthMech_ad) { 
			// now only External AD support external group step, External LDAP doesn't
			nextStep = ZaTaskAuthConfigWizard.CONFIG_COMPLETE_STEP;
		}
	}
	
	// always reset the LDAP/AD connection test result
	if (curStep == ZaTaskAuthConfigWizard.AUTH_CONFIG_SUMMARY_STEP ||
		nextStep == ZaTaskAuthConfigWizard.AUTH_CONFIG_SUMMARY_STEP) {
		this._localXForm.setInstanceValue("STANDBY", ZaDomain.A2_zmailAuthConfigTestStatus);
	}
	
	this.STEPS.push(nextStep);
	this.goPage(nextStep);
}

/**
* @method setObject sets the object contained in the view
* @param entry - ZaDomain object to display
**/
ZaTaskAuthConfigWizard.prototype.setObject =
function(entry) {
	this._containedObject = new Object();
	this._containedObject.attrs = new Object();

	for (var a in entry.attrs) {
		this._containedObject.attrs[a] = entry.attrs[a];
	}

	this._containedObject.name = entry.name;
	this._containedObject.type = entry.type ;
	this._containedObject.id = entry.id;

	if(entry.rights)
		this._containedObject.rights = entry.rights;

	if(entry.setAttrs)
		this._containedObject.setAttrs = entry.setAttrs;

	if(entry.getAttrs)
		this._containedObject.getAttrs = entry.getAttrs;

	if(entry._defaultValues)
		this._containedObject._defaultValues = entry._defaultValues;

    if(entry[ZaDomain.A2_zmailExternalGroupLdapEnabled])
        this._containedObject[ZaDomain.A2_zmailExternalGroupLdapEnabled]= entry[ZaDomain.A2_zmailExternalGroupLdapEnabled];

	this._containedObject[ZaDomain.A_AuthUseBindPassword] = entry[ZaDomain.A_AuthUseBindPassword];
	this.setTitle(ZaMsg.NCD_AuthConfigTitle + " (" + entry.name + ")");

    if (ZaSettings.isDomainAdmin && (entry.attrs[ZaDomain.A_zmailAdminConsoleLDAPAuthEnabled] == "TRUE")
           && entry.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled] != "TRUE") {
        this._containedObject [ZaDomain.A2_allowClearTextLDAPAuth] = "FALSE" ;
    }

    var serverList = ZaApp.getInstance().getServerList().getArray();
    for(var ix = 0; ix < serverList.length; ix++) {
        ZaDomain.TARGET_SERVER_CHOICES.push (
            {label: serverList[ix].name, value: serverList[ix].id }
        );
    }
    if(serverList.length > 0)
        this._containedObject [ZaDomain.A2_zmailSpnegoTargetServer] = serverList[0].id;
    if(this._containedObject.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA]
            && this._containedObject.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA]
            && (this._containedObject.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA].join(";")
                == this._containedObject.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA].join(";"))
            && (this._containedObject.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA].join(";")
                == ZaDomain.SPNEGO_SUPPORT_UA.join(";"))
     ) {
        this._containedObject[ZaDomain.A2_zmailSpnegoUASupportedBrowsers] = "TRUE";
    } else this._containedObject[ZaDomain.A2_zmailSpnegoUACustomBrowsers] = "TRUE";

    if(this._containedObject.attrs[ZaDomain.A_zmailWebClientLoginURL]
            || this._containedObject.attrs[ZaDomain.A_zmailWebClientLogoutURL]
            || (this._containedObject.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA]
            && this._containedObject.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA].length > 0))
       this._containedObject[ZaDomain.A2_zmailSpnegoAuthEnabled] = "TRUE";

    var globalConfig = ZaApp.getInstance().getGlobalConfig();
    this._containedObject[ZaDomain.A2_zmailSpnegoGlobalAuthEnabled] = globalConfig.attrs[ZaGlobalConfig.A_zmailSpnegoAuthEnabled];
    this._containedObject[ZaDomain.A2_zmailSpnegoAuthErrorURL] = globalConfig.attrs[ZaGlobalConfig.A_zmailSpnegoAuthErrorURL];
    this._containedObject[ZaDomain.A2_zmailSpnegoAuthRealm] = globalConfig.attrs[ZaGlobalConfig.A_zmailSpnegoAuthRealm];
    this._containedObject[ZaDomain.A2_zmailSpnegoGlobalSettingStatus] = ZaTaskAuthConfigWizard.getGlobalSettingMsg(this._containedObject);

    this._containedObject[ZaModel.currentStep] = entry[ZaModel.currentStep] || ZaTaskAuthConfigWizard.AUTH_CONFIG_CHOOSE_MODE_STEP;
    this._containedObject[ZaDomain.A2_zmailSpnegoUAAllBrowsers] = entry[ZaDomain.A2_zmailSpnegoUAAllBrowsers] || "FALSE";
    this._containedObject[ZaDomain.A2_zmailSpnegoUASupportedBrowsers] = entry[ZaDomain.A2_zmailSpnegoUASupportedBrowsers] || "FALSE";
    this._containedObject[ZaDomain.A2_zmailSpnegoUACustomBrowsers] = entry[ZaDomain.A2_zmailSpnegoUACustomBrowsers] || "FALSE";

    this._containedObject._uuid = entry._extid || entry._uuid;
    this._containedObject._editObject = entry._editObject;
	this._localXForm.setInstance(this._containedObject);
}



ZaTaskAuthConfigWizard.prototype.finishWizard =
function() {
	try {
		ZaDomain.modifyAuthSettings.call(this._containedObject._editObject,this._containedObject);
		ZaApp.getInstance().getDomainListController()._fireDomainChangeEvent(this._containedObject._editObject);
		this.popdown();
        ZaApp.getInstance().getDomainListController().notifyAllOpenTabs(this._containedObject._editObject);
	} catch (ex) {
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaTaskAuthConfigWizard.prototype.finishWizard", null, false);
	}
}

/**
* XForm definition
**/

ZaTaskAuthConfigWizard.myXFormModifier = function(xFormObject) {
	var labelVisibility = {};
    labelVisibility[ZaTaskAuthConfigWizard.AUTH_CONFIG_AUTH_SET_STEP] = {
        checks:[[XForm.checkInstanceValueNot,ZaDomain.A_AuthMech,ZaDomain.AuthMech_zmail]],
        sources:[ZaDomain.A_AuthMech]
    };
    labelVisibility[ZaTaskAuthConfigWizard.AUTH_CONFIG_BIND_PWD_STEP] = {
        checks:[[XForm.checkInstanceValueNot,ZaDomain.A_AuthMech,ZaDomain.AuthMech_zmail]],
        sources:[ZaDomain.A_AuthMech]
    };
    labelVisibility[ZaTaskAuthConfigWizard.AUTH_CONFIG_BIND_PWD_STEP] = {
        checks:[[XForm.checkInstanceValueNot,ZaDomain.A_AuthMech,ZaDomain.AuthMech_zmail]],
        sources:[ZaDomain.A_AuthMech]
    };
    labelVisibility[ZaTaskAuthConfigWizard.AUTH_CONFIG_SUMMARY_STEP] = {
        checks:[[XForm.checkInstanceValueNot,ZaDomain.A_AuthMech,ZaDomain.AuthMech_zmail]],
        sources:[ZaDomain.A_AuthMech]
    };
    labelVisibility[ZaTaskAuthConfigWizard.EXTERNAL_LDAP_GROUP_STEP] = {
        checks:[[XForm.checkInstanceValue,ZaDomain.A_AuthMech,ZaDomain.AuthMech_ad]],
        sources:[ZaDomain.A_AuthMech]
    };

	xFormObject.items = [
			{type:_OUTPUT_, colSpan:2, align:_CENTER_, valign:_TOP_, ref:ZaModel.currentStep, choices:this.stepChoices,valueChangeEventSources:[ZaModel.currentStep], labelVisibility:labelVisibility},
			{type:_SEPARATOR_, align:_CENTER_, valign:_TOP_},
			{type:_SPACER_,  align:_CENTER_, valign:_TOP_},
			{type:_SWITCH_,width:580, valign:_TOP_,
				items:[
					{type:_CASE_, numCols:2,colSizes:["60px","430px"],	caseKey:ZaTaskAuthConfigWizard.AUTH_CONFIG_CHOOSE_MODE_STEP,
						items:[
                            {type:_OUTPUT_, value:ZaMsg.authForDomainMsg,
                                colSpan:"*", cssStyle:"padding-left:10px;padding-top:10px;"
                            },
                            {type: _SPACER_, height: 10 },
							{ref:ZaDomain.A_AuthMech, type:_RADIO_,groupname:"auth_mechanism_group",
								label:"<b>" + ZaMsg.AuthMech_zmail + "</b>", labelLocation:_RIGHT_,
								updateElement:function (value) {
									this.getElement().checked = (value == ZaDomain.AuthMech_zmail);
								},
								elementChanged: function(elementValue,instanceValue, event) {
									this.getForm().itemChanged(this, ZaDomain.AuthMech_zmail, event);
                                    this.setInstanceValue("FALSE", ZaDomain.A2_zmailExternalGroupLdapEnabled);
								},
								visibilityChecks:[],enableDisableChecks:[]
							},
                            {type: _GROUP_, colSpan:2, numCols:2, colSizes: ["80px", "*" ],
                                items :[
                                    {type:_OUTPUT_, label:"" , value:ZaMsg.domainAuthDlgInternalHelp}
                                ]
                            },
                            {type: _SPACER_, height: 15 },
							{ref:ZaDomain.A_AuthMech, type:_RADIO_,groupname:"auth_mechanism_group",
								label:"<b>" + ZaMsg.AuthMech_ad + "</b>", labelLocation:_RIGHT_,
								updateElement:function (value) {
									this.getElement().checked = (value == ZaDomain.AuthMech_ad);
								},
								elementChanged: function(elementValue,instanceValue, event) {
									this.getForm().itemChanged(this, ZaDomain.AuthMech_ad, event);
                                    this.setInstanceValue("TRUE", ZaDomain.A2_zmailExternalGroupLdapEnabled);
								},
								visibilityChecks:[],enableDisableChecks:[]
							},
                            {type: _GROUP_, colSpan:2, numCols:2, colSizes: ["80px", "*" ],
                                items :[
                                    {type:_OUTPUT_, label:"" , value:ZaMsg.domainAuthDlgADHelp}
                                ]
                            },
                            {type: _SPACER_, height: 15 },
							{ref:ZaDomain.A_AuthMech, type:_RADIO_,groupname:"auth_mechanism_group",
								label:"<b>" + ZaMsg.AuthMech_ldap + "</b>",  labelLocation:_RIGHT_,
								updateElement:function (value) {
									this.getElement().checked = (value == ZaDomain.AuthMech_ldap);
								},
								elementChanged: function(elementValue,instanceValue, event) {
									this.getForm().itemChanged(this, ZaDomain.AuthMech_ldap, event);
                                    this.setInstanceValue("TRUE", ZaDomain.A2_zmailExternalGroupLdapEnabled);
								},
								visibilityChecks:[],enableDisableChecks:[]
							},
                            {type: _GROUP_, colSpan:2, numCols:2, colSizes: ["80px", "*" ],
                                items :[
                                    {type:_OUTPUT_, label:"" , value:ZaMsg.domainAuthDlgExternalHelp}
                                ]
                            }
                        ]
                    },
					{type:_CASE_, numCols:2,colSizes:["150px","430px"],	caseKey:ZaTaskAuthConfigWizard.AUTH_CONFIG_AUTH_SET_STEP,
                        cellpadding:10,
						items:[
						    //{type:_OUTPUT_, label:ZaMsg.Domain_AuthMech, choices:this.AuthMechs, ref:ZaDomain.A_AuthMech, //onChange:ZaTaskAuthConfigWizard.onAuthMechChange,
                            //    labelCssStyle:"text-align:left;",
						    //	enableDisableChecks:[],visibilityChecks:[]
						    //},
							{type:_GROUP_, colSpan:2,numCols:2,colSizes:["150px","430px"],
								visibilityChecks:[[XForm.checkInstanceValue,ZaDomain.A_AuthMech,ZaDomain.AuthMech_ad]],
								visibilityChangeEventSources:[ZaDomain.A_AuthMech],
								items:[
                                    {type:_OUTPUT_, value:ZaMsg.authForADMsg,  colSpan:"*"
                                        //colSpan:"*", cssStyle:"padding-left:10px;padding-top:10px;"
                                    },
								    {type:_SPACER_, height:20,colSpan:2},
									{ref:ZaDomain.A_AuthADDomainName, type:_TEXTFIELD_, label:ZaMsg.Domain_AuthADDomainName, labelLocation:_LEFT_,
                                        labelCssStyle:"text-align:left;padding-left:20px;", required: true,
										visibilityChecks:[],enableDisableChecks:[],bmolsnr:true
									},
                                    {type:_SPACER_, height:10,colSpan:2},
                                    {type:_OUTPUT_, label:ZaMsg.Domain_AuthADServer, value:" ", labelCssStyle:"text-align:left;padding-left:20px;"},
									{type:_GROUP_, numCols:6, colSpan:2,label:null,labelLocation:_LEFT_, containerCssStyle:"padding-left:20px;",
										items: [
											{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:" ", width:"35px"},
											{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:ZaMsg.Domain_AuthADServerName, width:"200px",
											 getDisplayValue: function(value) {  // show required symbol, xform items with null label can't use with "required".
												 return value + "<span class=\"redAsteric\">*</span>";
											 }
											},
											{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:" ", width:"5px"},
											{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:ZaMsg.Domain_AuthADServerPort, width:"40px",
											 getDisplayValue: function(value) {
												 return value + "<span class=\"redAsteric\">*</span>";
											 }
											},
											{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:ZaMsg.Domain_AuthADUseSSL, width:"40px"}
										]
									},
									{ref:ZaDomain.A_AuthLdapURL, type:_REPEAT_, colSpan:"*",containerCssStyle:"padding-left:20px;",//label:ZaMsg.Domain_AuthADURL,
										repeatInstance:"", showAddButton:true, showRemoveButton:true,
										addButtonLabel:ZaMsg.Domain_AddURL,
										removeButtonLabel:ZaMsg.Domain_REPEAT_REMOVE,
										showAddOnNextRow:true,
										items: [
											{ref:".", type:_LDAPURL_, label:null, labelLocation:_NONE_,ldapSSLPort:"3269",ldapPort:"3268",
											visibilityChecks:[],enableDisableChecks:[]}
										]
									}
								]
							},
							{type:_GROUP_, colSpan:2,numCols:2,colSizes:["150px","430px"],
								visibilityChecks:[[XForm.checkInstanceValue,ZaDomain.A_AuthMech,ZaDomain.AuthMech_ldap]],
								visibilityChangeEventSources:[ZaDomain.A_AuthMech],
								items:[
                                    {type:_OUTPUT_, value:ZaMsg.authForLDAPMsg,  colSpan:"*"
                                    },
                                    {type:_SPACER_, height:20,colSpan:2},
                                    {type:_OUTPUT_, value:"LDAP Setting",  colSpan:"*"
                                    },
									{type:_GROUP_, numCols:6, colSpan:2,label:null,labelLocation:_LEFT_,
										items: [
											{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:" ", width:"35px"},
											{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:ZaMsg.Domain_AuthLDAPServerName, width:"200px",
											 getDisplayValue: function(value) {
												 return value + "<span class=\"redAsteric\">*</span>";
											 }
											},
											{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:" ", width:"5px"},
											{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:ZaMsg.Domain_AuthLDAPServerPort,  width:"40px",
											 getDisplayValue: function(value) {
												 return value + "<span class=\"redAsteric\">*</span>";
											 }
											},
											{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:ZaMsg.Domain_AuthLDAPUseSSL, width:"40px"}
										]
									},
									{ref:ZaDomain.A_AuthLdapURL, type:_REPEAT_, colSpan:"*",cssStyle:"padding-left:10px;",//label:ZaMsg.Domain_AuthLdapURL, repeatInstance:"", showAddButton:true, showRemoveButton:true,
										addButtonLabel:ZaMsg.Domain_AddURL,
										removeButtonLabel:ZaMsg.Domain_REPEAT_REMOVE,
										showAddOnNextRow:true,
										items: [
											{ref:".", type:_LDAPURL_, label:null,ldapSSLPort:"636",ldapPort:"389",  labelLocation:_NONE_,
											visibilityChecks:[],enableDisableChecks:[]}
										]
									},
                                    {type:_SPACER_, height:10,colSpan:2},
									{ref:ZaDomain.A_zmailAuthLdapStartTlsEnabled, type:_CHECKBOX_, label:ZaMsg.Domain_AuthLdapStartTlsEnabled, onChange: ZaTaskAuthConfigWizard.startTlsEnabledChanged,
										 trueValue:"TRUE", falseValue:"FALSE"
									},
                                    {type:_SPACER_, height:10,colSpan:2},
									{ref:ZaDomain.A_AuthLdapSearchFilter, type:_TEXTAREA_, width:380, height:40, label:ZaMsg.Domain_AuthLdapFilter,
                                        labelCssStyle:"text-align:left;",
                                        labelLocation:_LEFT_, textWrapping:"soft"},
                                    {type:_SPACER_, height:10,colSpan:2},
									{ref:ZaDomain.A_AuthLdapSearchBase, type:_TEXTAREA_, width:380, height:40, label:ZaMsg.Domain_AuthLdapSearchBase,
                                        labelCssStyle:"text-align:left;",
                                        labelLocation:_LEFT_, textWrapping:"soft"},
                                    {type:_SPACER_, height:20,colSpan:2},
									{type:_OUTPUT_, value:ZaMsg.NAD_DomainsAuthStr, colSpan:2}
								]
							}
						]
					},
					{type:_CASE_, numCols:2, colSizes:["150px","430px"], caseKey:ZaTaskAuthConfigWizard.AUTH_CONFIG_BIND_PWD_STEP,
						visibilityChecks:[[Case_XFormItem.prototype.isCurrentTab]],
						items: [
							{ref:ZaDomain.A_AuthUseBindPassword, type:_CHECKBOX_, label:ZaMsg.Domain_AuthUseBindPassword, labelLocation:_LEFT_,trueValue:"TRUE", falseValue:"FALSE",labelCssClass:"xform_label", align:_LEFT_,
							visibilityChecks:[],enableDisableChecks:[]},
							{ref:ZaDomain.A_AuthLdapSearchBindDn, type:_INPUT_, label:ZaMsg.Domain_AuthLdapBindDn, labelLocation:_LEFT_,
								enableDisableChecks:[[XForm.checkInstanceValue,ZaDomain.A_AuthUseBindPassword,"TRUE"]],
								enableDisableChangeEventSources:[ZaDomain.A_AuthUseBindPassword],
								visibilityChecks:[]
							},
							{ref:ZaDomain.A_AuthLdapSearchBindPassword, type:_SECRET_, label:ZaMsg.Domain_AuthLdapBindPassword, labelLocation:_LEFT_,
								enableDisableChecks:[[XForm.checkInstanceValue,ZaDomain.A_AuthUseBindPassword,"TRUE"]],
								enableDisableChangeEventSources:[ZaDomain.A_AuthUseBindPassword],
								visibilityChecks:[]
							},
							{ref:ZaDomain.A_AuthLdapSearchBindPasswordConfirm, type:_SECRET_, label:ZaMsg.Domain_AuthLdapBindPasswordConfirm, labelLocation:_LEFT_,
								enableDisableChecks:[[XForm.checkInstanceValue,ZaDomain.A_AuthUseBindPassword,"TRUE"]],
								enableDisableChangeEventSources:[ZaDomain.A_AuthUseBindPassword],
								visibilityChecks:[]
							}
						]
					},
					{type:_CASE_, numCols:2,colSizes:["180px","430px"],	caseKey:ZaTaskAuthConfigWizard.AUTH_CONFIG_SUMMARY_STEP,
						visibilityChecks:[Case_XFormItem.prototype.isCurrentTab,ZaNewDomainXWizard.isAuthMechNotZmail],
						items: [
                            {type:_OUTPUT_, value:ZaMsg.authSummaryMsg,  colSpan:"*"
                            },
							{type:_SPACER_, height:10},
							{type:_OUTPUT_, ref:ZaDomain.A_AuthMech, label:ZaMsg.Domain_AuthMech, choices:this.AuthMechs, alignment:_LEFT_,
                                labelCssStyle:"text-align:right;padding-left:20px;"},
							{type:_GROUP_, useParentTable:true,
								visibilityChecks:[[XForm.checkInstanceValue,ZaDomain.A_AuthMech,ZaDomain.AuthMech_ad]],
								visibilityChangeEventSources:[ZaDomain.A_AuthMech],
								items:[
									{ref:ZaDomain.A_AuthADDomainName, type:_OUTPUT_, label:ZaMsg.Domain_AuthADDomainName, labelLocation:_LEFT_,
                                        labelCssStyle:"text-align:right;padding-left:20px;"},
									{ref:ZaDomain.A_AuthLdapURL, type:_REPEAT_, label:ZaMsg.Domain_AuthLdapURL, labelLocation:_LEFT_,showAddButton:false,
                                        showRemoveButton:false, labelCssStyle:"text-align:right;padding-left:20px;",
										items:[
											{type:_OUTPUT_, ref:".", label:null,labelLocation:_NONE_}
										]
									}
								]
							},
							{type:_GROUP_, useParentTable:true,
								visibilityChecks:[[XForm.checkInstanceValue,ZaDomain.A_AuthMech,ZaDomain.AuthMech_ldap]],
								visibilityChangeEventSources:[ZaDomain.A_AuthMech],
								items:[
									{ref:ZaDomain.A_AuthLdapURL, type:_REPEAT_, label:ZaMsg.Domain_AuthLdapURL, labelLocation:_LEFT_,
                                        labelCssStyle:"text-align:right;padding-left:20px;",showAddButton:false, showRemoveButton:false,visibilityChecks:[],
										items:[
											{type:_OUTPUT_, ref:".", label:null,labelLocation:_NONE_,visibilityChecks:[]}
										]

									},
									{ref:ZaDomain.A_zmailAuthLdapStartTlsEnabled, type:_OUTPUT_, label:ZaMsg.Domain_AuthLdapStartTlsEnabled, labelLocation:_LEFT_,
                                        labelCssStyle:"text-align:right;padding-left:20px;",choices:ZaModel.BOOLEAN_CHOICES,visibilityChecks:[]},
									{ref:ZaDomain.A_AuthLdapSearchFilter, type:_OUTPUT_, label:ZaMsg.Domain_AuthLdapFilter, labelLocation:_LEFT_,
                                        labelCssStyle:"text-align:right;padding-left:20px;",visibilityChecks:[]},
									{ref:ZaDomain.A_AuthLdapSearchBase, type:_OUTPUT_, label:ZaMsg.Domain_AuthLdapSearchBase, labelLocation:_LEFT_,
                                        labelCssStyle:"text-align:right;padding-left:20px;",visibilityChecks:[]},
									{ref:ZaDomain.A_AuthUseBindPassword, type:_OUTPUT_, label:ZaMsg.Domain_AuthUseBindPassword, labelLocation:_LEFT_,
                                        labelCssStyle:"text-align:right;padding-left:20px;",choices:ZaModel.BOOLEAN_CHOICES},
									{ref:ZaDomain.A_AuthLdapSearchBindDn, type:_OUTPUT_, label:ZaMsg.Domain_AuthLdapBindDn, labelLocation:_LEFT_,
                                        labelCssStyle:"text-align:right;padding-left:20px;",
										visibilityChecks:[[XForm.checkInstanceValue,ZaDomain.A_AuthUseBindPassword,"TRUE"]],
										visibilityChangeEventSources:[ZaDomain.A_AuthUseBindPassword]
									}
								]
							},
							{type:_SPACER_, height:20},
							{type:_OUTPUT_,value:ZaMsg.Domain_AuthProvideLoginPwd, align:_LEFT_, colSpan:"*",visibilityChecks:[]},
                            {type:_SPACER_, height:10},
                            {type:_GROUP_, numCols:2, colSpan:"*", colSizes:["100px", "auto"], width:"100%",
                             items: [
                                     {type:_TEXTFIELD_, label:ZaMsg.LBL_Domain_AuthTestUserName, ref:ZaDomain.A_AuthTestUserName, alignment:_LEFT_,
                                      labelCssStyle:"text-align:right;padding-left:20px;",width:"150px", visibilityChecks:[],enableDisableChecks:[]},
                                     {type:_SECRET_, label:ZaMsg.LBL_Domain_AuthTestPassword, ref:ZaDomain.A_AuthTestPassword, alignment:_LEFT_,
                                      labelCssStyle:"text-align:right;padding-left:20px;",width:"150px", visibilityChecks:[],enableDisableChecks:[]}]
                            },
                            {type:_GROUP_, numCols:1, colSpan:"*", colSizes:["300px","200px","auto"],
                                cssStyle:"margin-bottom:10px;padding-bottom:0px;margin-top:10px;margin-left:10px;margin-right:10px;",
                                items: [
                                    {type:_CELLSPACER_},
                                    {type:_DWT_BUTTON_, label:ZaMsg.Domain_AuthTestSettings,width:"100px",
                                    	ref:ZaDomain.A2_zmailAuthConfigTestStatus,
                                        onActivate:"ZaTaskAuthConfigWizard.autoTestSetting.call(this);"},
                                    {type:_GROUP_, isTabGroup:false, deferred:false, colSpan:2, colSizes:["100px", "400px"],
                                        visibilityChangeEventSources:[ZaModel.currentStep, ZaDomain.A2_zmailAuthConfigTestStatus],
                                        visibilityChecks:[[XForm.checkInstanceValue, ZaDomain.A2_zmailAuthConfigTestStatus, "RUNNING"]],
                                        items: [
                                            {type:_DWT_ALERT_, content:ZaMsg.Domain_AuthTestingInProgress, alignment:_CENTER_, colSpan:2, style:DwtAlert.INFO}
                                        ]
                                    },
                                    {type:_GROUP_, isTabGroup:false, deferred:false, colSpan:2, colSizes:["100px", "400px"],
                                    	 visibilityChangeEventSources:[ZaDomain.A2_zmailAuthConfigTestStatus, ZaDomain.A_AuthTestResultCode],
    									 visibilityChecks:[[XForm.checkInstanceValue, ZaDomain.A2_zmailAuthConfigTestStatus, "RUNNED"],
    											           [XForm.checkInstanceValue, ZaDomain.A_AuthTestResultCode, ZaDomain.Check_OK]],
    								     items: [
    								    	 {type:_DWT_ALERT_, style:DwtAlert.INFORMATION, content:ZaMsg.Domain_AuthTestSuccessful, alignment:_CENTER_, colSpan:2},
    										 {type:_OUTPUT_, ref:ZaDomain.A_AuthComputedBindDn, label:ZaMsg.LBL_Domain_AuthComputedBindDn}
    								     ]
                                    },
									{type:_GROUP_, isTabGroup:false, deferred:false, colSpan:2, colSizes:["100px", "400px"],
								     visibilityChangeEventSources:[ZaDomain.A2_zmailAuthConfigTestStatus, ZaDomain.A_AuthTestResultCode],
									 visibilityChecks:[
											function () {
												return  (this.getInstanceValue(ZaDomain.A2_zmailAuthConfigTestStatus) == "RUNNED") &&
														(this.getInstanceValue(ZaDomain.A_AuthTestResultCode) != ZaDomain.Check_OK);
											}
										],
									 items: [
										{type:_DWT_ALERT_, content:ZaMsg.Domain_AuthTestFailed, alignment:_CENTER_, colSpan:2, style:DwtAlert.CRITICAL},
										{type:_OUTPUT_, ref:ZaDomain.A_AuthTestResultCode, label:ZaMsg.LBL_Domain_AuthTestResultCode, choices:this.TestResultChoices},
										{type:_OUTPUT_, value: ZaMsg.LBL_Domain_AuthTestMessage, colSpan:2},
										{type:_TEXTAREA_, ref:ZaDomain.A_AuthTestMessage, height:80, alignment:_LEFT_, colSpan:2}
									 ]
									}
                                ]
                            }
						]
					},
					/* bug 74123, remove SPNEGO config
					{type:_CASE_, caseKey:ZaTaskAuthConfigWizard.SPNEGO_CONFIG_STEP,colSpan:"*", numCols:1,
						items: [
                            {type:_OUTPUT_, value:ZaMsg.spnegoGlobalSettingTitle,  colSpan:"*"
                            },
                            {type:_OUTPUT_, value:ZaMsg.spnegoGlobalSettingMsg,  colSpan:"*",
                                cssStyle:"padding-left:20px;padding-top:10px;"
                            },
                            {type:_SPACER_, height:10, colSpan:"*"},
                            {type:_OUTPUT_, ref: ZaDomain.A2_zmailSpnegoGlobalSettingStatus, bmolsnr:true,
                                cssStyle:"padding-left:20px;padding-top:10px;"
                            },
                            {type:_OUTPUT_, label: null, value: ZaMsg.SpnegoGlobalConfig,
                                onClick:ZaTaskAuthConfigWizard.changeSpnegoGlobalConfig,
                                containerCssStyle:"color:blue;cursor:pointer;padding-left:20px;padding-top:10px;"
                            },
                            {type:_SPACER_, height:30, colSpan:"*"},
                            {type:_OUTPUT_, value:ZaMsg.authForSpnegoSettingMsg,  colSpan:"*"
                                //colSpan:"*", cssStyle:"padding-left:10px;padding-top:10px;"
                            },
                            {type:_OUTPUT_, value:ZaMsg.spnegoServerSettingMsg,  colSpan:"*",
                                cssStyle:"padding-left:20px;padding-top:10px;"
                            },
                            {type:_SPACER_, height:10, colSpan:"*"},
                            {type:_GROUP_, numCols:2, colSpan:"*", colSizes:["80px","auto"],items:[
                                {ref:ZaDomain.A2_zmailSpnegoTargetServer, type:_OSELECT1_,
                                    label:ZaMsg.spnegoTagetServer,
                                    labelLocation:_LEFT_,
                                    labelCssStyle:"text-align:left;padding-left:20px;",
                                    choices:ZaDomain.TARGET_SERVER_CHOICES,
                                    visibilityChecks:[],
                                    enableDisableChecks:[],
                                    editable: true
                                }
                            ]},
                            {type:_OUTPUT_, label: null, value: ZaMsg.SpnegoServerConfig,
                                onClick:ZaTaskAuthConfigWizard.changeSpnegoServerConfig,
                                containerCssStyle:"color:blue;cursor:pointer;padding-left:20px;padding-top:10px;"
                            }
						]
					},
					{type:_CASE_, caseKey:ZaTaskAuthConfigWizard.SPNEGO_CONFIG_STEP_2, colSizes:["200px", "380px"],
						items: [
							{type:_OUTPUT_, value:ZaMsg.spnegoSettingSSOMsg, colSpan:2},
                            {type:_SPACER_, height:10, colSpan:"*"},
                            {ref:ZaDomain.A_zmailVirtualHostname, type:_REPEAT_,
                                label:ZaMsg.Domain_Tab_VirtualHost, repeatInstance:"", showAddButton:true,
                                labelCssStyle:"text-align:left;vertical-align:top;padding-left:20px;",
                                showRemoveButton:true,
                                addButtonLabel:ZaMsg.NAD_AddVirtualHost,
                                showAddOnNextRow:true,
                                removeButtonLabel:ZaMsg.NAD_RemoveVirtualHost,
                                items: [
                                    {ref:".", type:_TEXTFIELD_, label:null, width:"150px",
                                        enableDisableChecks:[[ZaItem.hasWritePermission,ZaDomain.A_zmailVirtualHostname]],
                                        visibilityChecks:[[ZaItem.hasReadPermission,ZaDomain.A_zmailVirtualHostname]],
                                        onChange:ZaDomainXFormView.onFormFieldChanged}
                                ]
                            },
                            {type:_OUTPUT_, value:ZaMsg.spnegoVirtualHostMsg,
                                width:AjxEnv.isIE?"450px":"250px", // bug 71321, IE uses different box model
                                colSpan:"*",cssStyle:"padding-left:200px;"
                            },
                            {type:_SPACER_, height:15, colSpan:"*"},
                            {ref: ZaDomain.A_zmailWebClientLoginURL,useParentTable: false,
                                colSizes:["275px","*"], colSpan: 2,
                                type:_TEXTFIELD_, width: "150px",
                                labelCssStyle:"text-align:left;padding-left:20px;",
                                msgName: ZaMsg.LBL_zmailWebClientLoginURL,
                                label: ZaMsg.LBL_zmailWebClientLoginURL,
                                onChange:ZaDomainXFormView.onFormFieldChanged
                            },
                            {type:_OUTPUT_, value:ZaMsg.spnegoWebClientLoginMsg,
                                width:AjxEnv.isIE?"450px":"250px", // bug 71321, IE uses different box model
                                colSpan:"*",cssStyle:"padding-left:200px;"
                            },
                            {ref: ZaDomain.A_zmailWebClientLogoutURL,useParentTable: false,
                                colSizes:["275px","*"], colSpan: 2,
                                type:_TEXTFIELD_, width:"150px",
                                labelCssStyle:"text-align:left;padding-left:20px;",
                                msgName: ZaMsg.LBL_zmailWebClientLogoutURL,
                                label: ZaMsg.LBL_zmailWebClientLogoutURL,
                                onChange:ZaDomainXFormView.onFormFieldChanged
                            },
                            {type:_OUTPUT_, value:ZaMsg.spnegoWebClientLogoutMsg,
                            	width:AjxEnv.isIE?"450px":"250px", // bug 71321, IE uses different box model
                            	colSpan:"*",cssStyle:"padding-left:200px;"
                            },
                    		{type:_REPEAT_, repeatInstance:"", label: ZaMsg.LBL_zmailWebClientLoginURLAllowedIP,
                    		 ref: ZaDomain.A_zmailWebClientLoginURLAllowedIP,
                    		 labelCssStyle:"text-align:left;padding-left:20px;",
                    		 removeButtonLabel: ZaMsg.NAD_Remove,
                    		 addButtonLabel: ZaMsg.NAD_AddAllowedIP,
                    		 showAddButton: true,
                    		 showRemoveButton: true,
                    		 showAddOnNextRow: true,
                    		 items: [
                                  {ref: ".", type:_TEXTFIELD_, width: "150px",
                                   onChange:ZaDomainXFormView.onFormFieldChanged}
                             ]
                            },
                            {type:_SPACER_, height:15, colSpan:"*"},
                            {type:_REPEAT_, repeatInstance:"", label: ZaMsg.LBL_zmailWebClientLogoutURLAllowedIP,
                       		 ref: ZaDomain.A_zmailWebClientLogoutURLAllowedIP,
                       		 labelCssStyle:"text-align:left;padding-left:20px;",
                       		 removeButtonLabel: ZaMsg.NAD_Remove,
                       		 addButtonLabel: ZaMsg.NAD_AddAllowedIP,
                       		 showAddButton: true,
                       		 showRemoveButton: true,
                       		 showAddOnNextRow: true,
                       		 items: [
                                     {ref: ".", type:_TEXTFIELD_, width: "150px",
                                      onChange:ZaDomainXFormView.onFormFieldChanged}
                                ]
                            },
                            {type:_SPACER_, height:15, colSpan:"*"},
                            {type:_OUTPUT_, value:ZaMsg.spnegoAllowedUASetting, colSpan:2, cssStyle:"padding-left:20px;"},
                            {type:_SPACER_, height:10, colSpan:"*"},
                            {type: _GROUP_,  id:"spnego_user_agent_settings",
                                numCols: 2, colSpan:2, colSizes:["200px", "*"],
                                items: [
                                    {ref:ZaDomain.A2_zmailSpnegoUAAllBrowsers, type:_RADIO_, groupname:"user_agent_setting",
										msgName:ZaMsg.SpnegoSettingAllBrowsers,label:ZaMsg.SpnegoSettingAllBrowsers, labelLocation:_RIGHT_,
                                        labelCssClass:"xform_label_right",
										visibilityChecks:[],
										updateElement:function () {
                                            if(ZaTaskAuthConfigWizard.checkRadioEnableDisable(this.getInstance(),ZaDomain.A2_zmailSpnegoUAAllBrowsers))
											    this.getElement().checked = "TRUE";
										},
										elementChanged: function(elementValue,instanceValue, event) {
											this.setInstanceValue("FALSE",ZaDomain.A2_zmailSpnegoUACustomBrowsers);
											this.setInstanceValue("FALSE",ZaDomain.A2_zmailSpnegoUASupportedBrowsers);
                                            this.setInstanceValue("TRUE",ZaDomain.A2_zmailSpnegoUAAllBrowsers);

										}
									},
                                    {ref:ZaDomain.A2_zmailSpnegoUASupportedBrowsers, type:_RADIO_, groupname:"user_agent_setting",
										msgName:ZaMsg.SpnegoSettingSupportedBrowsers,label:ZaMsg.SpnegoSettingSupportedBrowsers, labelLocation:_RIGHT_,
                                        labelCssClass:"xform_label_right",
										visibilityChecks:[],
										updateElement:function () {
                                            if(ZaTaskAuthConfigWizard.checkRadioEnableDisable(this.getInstance(),ZaDomain.A2_zmailSpnegoUASupportedBrowsers))
											    this.getElement().checked = "TRUE";
										},
										elementChanged: function(elementValue,instanceValue, event) {
											this.setInstanceValue("FALSE",ZaDomain.A2_zmailSpnegoUACustomBrowsers);
											this.setInstanceValue("FALSE",ZaDomain.A2_zmailSpnegoUAAllBrowsers);
                                            this.setInstanceValue("TRUE",ZaDomain.A2_zmailSpnegoUASupportedBrowsers);
										}
									},
                                    {ref:ZaDomain.A2_zmailSpnegoUACustomBrowsers, type:_RADIO_, groupname:"user_agent_setting",
										msgName:ZaMsg.SpnegoSettingCustomBrowsers,label:ZaMsg.SpnegoSettingCustomBrowsers, labelLocation:_RIGHT_,
                                        labelCssClass:"xform_label_right",
										updateElement:function () {
                                            if(ZaTaskAuthConfigWizard.checkRadioEnableDisable(this.getInstance(),ZaDomain.A2_zmailSpnegoUACustomBrowsers))
											    this.getElement().checked = "TRUE";
										},
										visibilityChecks:[],
										elementChanged: function(elementValue,instanceValue, event) {
											this.setInstanceValue("FALSE",ZaDomain.A2_zmailSpnegoUAAllBrowsers);
											this.setInstanceValue("FALSE",ZaDomain.A2_zmailSpnegoUASupportedBrowsers);
                                            this.setInstanceValue("TRUE",ZaDomain.A2_zmailSpnegoUACustomBrowsers);
										}
									},
                                    {type:_GROUP_, numCols:3, colSpan:"*", colSizes:["275px","80px","auto"],
                                        cssStyle:"margin-bottom:10px;padding-bottom:0px;margin-top:0px;margin-left:10px;margin-right:10px;",
                                        items: [
                                            {type:_CELLSPACER_},
                                            {type:_DWT_BUTTON_, label:ZaMsg.spnegoAllowedUASetting_specify,width:"70px",
                                                enableDisableChangeEventSources:[ZaDomain.A2_zmailSpnegoUACustomBrowsers],
                                                enableDisableChecks:[[XForm.checkInstanceValue,ZaDomain.A2_zmailSpnegoUACustomBrowsers,'TRUE']],
                                                onActivate:"ZaTaskAuthConfigWizard.customAllowedUASetting.call(this);"
                                            }
                                        ]
                                    }
                                ]
							}
						]
					}, */
					{type:_CASE_, caseKey:ZaTaskAuthConfigWizard.EXTERNAL_LDAP_GROUP_STEP, colSizes:["200px", "*"],
						items: [
                            {type:_OUTPUT_, ref: ZaDomain.A_zmailAuthMechAdmin, label: "Auth Mech for Admin:", choices:this.AuthMechs},
                            {type:_TEXTAREA_, ref:ZaDomain.A_zmailExternalGroupLdapSearchBase, label: "External Group LDAP Search Base:"},
                            {type:_TEXTAREA_, ref:ZaDomain.A_zmailExternalGroupLdapSearchFilter, label: "External Group LDAP Search Filter:"},
                            {type:_TEXTFIELD_, ref:ZaDomain.A_zmailExternalGroupHandlerClass, width:"100%", label :"External Group Handle Class Name:"}
						]
					},
					{type:_CASE_, caseKey:ZaTaskAuthConfigWizard.CONFIG_COMPLETE_STEP,
						items: [
							{type:_OUTPUT_, value:ZaMsg.Domain_Auth_Config_Complete}
						]
					}
				]
			}
		];
}
ZaXDialog.XFormModifiers["ZaTaskAuthConfigWizard"].push(ZaTaskAuthConfigWizard.myXFormModifier);

ZaTaskAuthConfigWizard.startTlsEnabledChanged =  function (value, event, form) {
	this.setInstanceValue(value);
    var instance = form.getInstance () ;
    var ldapUrls = instance.attrs[ZaDomain.A_AuthLdapURL] ;
    var newUrls = [];
    if (ZaSettings.isDomainAdmin && (instance.attrs[ZaDomain.A_zmailAdminConsoleLDAPAuthEnabled] == "TRUE")
               && instance.attrs[ZaDomain.A_zmailAuthLdapStartTlsEnabled] != "TRUE") {
        //force ldaps protocol
        instance[ZaDomain.A2_allowClearTextLDAPAuth] = "FALSE" ;

        for (var i=0; i< ldapUrls.length; i++) {
            var ldapUrl = ldapUrls [i] ;
            if (ldapUrl == null || ldapUrl.length <=0) {
            }else {
                //force to use ldaps://
                ldapUrls[i] = ldapUrl.replace("ldap://", "ldaps://")  ;
                newUrls.push (ldapUrls[i]);
            }
        }

    }else{
        for (var i=0; i< ldapUrls.length; i++) {
            var ldapUrl = ldapUrls [i] ;
            //remove this empty item
            if (ldapUrl == null || ldapUrl.length <=0) {

            }else {
                newUrls.push (ldapUrls[i]);
            }
        }
        instance[ZaDomain.A2_allowClearTextLDAPAuth] = "TRUE" ;
    }

    instance.attrs[ZaDomain.A_AuthLdapURL] = newUrls ;
    form.refresh ();
}

ZaTaskAuthConfigWizard.autoTestSetting = function() {
	this.getForm().setInstanceValue("RUNNING", ZaDomain.A2_zmailAuthConfigTestStatus);
    var parent = this.getForm().parent;
    parent.testSetings();
    
}

ZaTaskAuthConfigWizard.checkSpnegoApplyType = function(refPath,val) {
    var instance = this.getInstance();
    return (instance[refPath] == val);

}

ZaTaskAuthConfigWizard.changeSpnegoGlobalConfig = function() {
    var instance = this.getInstance();
    ZaTaskAuthConfigWizard.spnegoGlobalServerSetting(instance, ZaItem.GLOBAL_CONFIG);
}

ZaTaskAuthConfigWizard.changeSpnegoServerConfig = function() {
    var instance = this.getInstance();
    ZaTaskAuthConfigWizard.spnegoGlobalServerSetting(instance, ZaItem.SERVER);
}

ZaTaskAuthConfigWizard.changeSpnegoDomainConfig = function() {
    var instance = this.getInstance();
    instance[ZaDomain.A2_zmailSpnegoApplyFor] = ZaItem.DOMAIN;
    var parent = this.getForm().parent;
    parent.goPage(ZaTaskAuthConfigWizard.SPNEGO_CONFIG_STEP_1);
}

ZaTaskAuthConfigWizard.checkRadioEnableDisable = function (instance, refPath) {
    if(!instance) return false;
    if(instance.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA]
            && instance.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA]
            && (instance.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA].join(";")
                == instance.attrs[ZaDomain.A_zmailWebClientLogoutURLAllowedUA].join(";"))
            && (instance.attrs[ZaDomain.A_zmailWebClientLoginURLAllowedUA].join(";")
                == ZaDomain.SPNEGO_SUPPORT_UA.join(";"))
    ) {
        if(ZaDomain.A2_zmailSpnegoUASupportedBrowsers == refPath)
            return true;
    } else  {
        if(ZaDomain.A2_zmailSpnegoUACustomBrowsers == refPath)
            return true;
    }
    return false;

}

ZaTaskAuthConfigWizard.getGlobalSettingMsg = function (instance) {
    if((!instance[ZaDomain.A2_zmailSpnegoGlobalAuthEnabled]
            ||instance[ZaDomain.A2_zmailSpnegoGlobalAuthEnabled] == "FALSE")
        && !instance[ZaDomain.A2_zmailSpnegoAuthErrorURL]
            && !instance[ZaDomain.A2_zmailSpnegoAuthRealm])
        return ZaMsg.spnegoGlobalSettingEmpty;
    if(!instance[ZaDomain.A2_zmailSpnegoGlobalAuthEnabled]
            ||instance[ZaDomain.A2_zmailSpnegoGlobalAuthEnabled] == "FALSE")
        return ZaMsg.spnegoGlobalSettingUnenabled;
    else return ZaMsg.spnegoGlobalSettingUnempty;
}

ZaTaskAuthConfigWizard.customAllowedUASetting = function() {
    if(!ZaApp.getInstance().dialogs["spnegoUACustomDialog"]) {
        ZaApp.getInstance().dialogs["spnegoUACustomDialog"] = new ZaSpnegoUACustomDialog(
            ZaApp.getInstance().getAppCtxt().getShell(), "450px", "280px",ZaMsg.SpnegoSettingCustomDialogTitle );
        ZaApp.getInstance().dialogs["spnegoUACustomDialog"].registerCallback(
                DwtDialog.OK_BUTTON, ZaSpnegoUACustomDialog.updateSpnegoUASetting,
                this, ZaApp.getInstance().dialogs["spnegoUACustomDialog"]._localXForm );
    }
    var allowedUAs = this.getInstanceValue(ZaDomain.A_zmailWebClientLoginURLAllowedUA);
    var obj = {};
    if(typeof allowedUAs == "string")
        obj[ZaDomain.A2_zmailWebClientURLAllowedUA] = allowedUAs;
    else if(allowedUAs instanceof Array)
        obj[ZaDomain.A2_zmailWebClientURLAllowedUA] = allowedUAs.join('\n');

    ZaApp.getInstance().dialogs["spnegoUACustomDialog"].setObject(obj);
    ZaApp.getInstance().dialogs["spnegoUACustomDialog"].popup();
}

ZaTaskAuthConfigWizard.spnegoGlobalServerSetting = function(obj, caller) {
    var instance = {};
    if(obj) instance = obj;
    var titleText = ZaMsg.SpnegoSettingGlobalDialogTitle;
    if(caller == ZaItem.GLOBAL_CONFIG) {
        instance[ZaDomain.A2_zmailSpnegoApplyFor] = ZaItem.GLOBAL_CONFIG;
    } else if(caller == ZaItem.SERVER) {
        titleText = ZaMsg.SpnegoSettingServerDialogTitle;
        instance[ZaDomain.A2_zmailSpnegoApplyFor] = ZaItem.SERVER;
        var serverList = ZaApp.getInstance().getServerList().getArray();
        var serverObj = null;
        for(var i = 0; i < serverList.length; i++) {
            if(serverList[i].id == instance[ZaDomain.A2_zmailSpnegoTargetServer]) {
                serverObj = serverList[i];
                break;
            }
        }
        if(serverObj) {
            instance[ZaDomain.A2_zmailSpnegoAuthPrincipal] = serverObj.attrs[ZaServer.A_zmailSpnegoAuthPrincipal];
            instance[ZaDomain.A2_zmailSpnegoAuthTargetName] = serverObj.attrs[ZaServer.A_zmailSpnegoAuthTargetName];
        }
    }

    if(!ZaApp.getInstance().dialogs["updateGlobalServerDialog"]) {
        ZaApp.getInstance().dialogs["updateGlobalServerDialog"] = new ZaSpnegoGlobalServerDialog(
            ZaApp.getInstance().getAppCtxt().getShell(), "700px", "350px",titleText );
        ZaApp.getInstance().dialogs["updateGlobalServerDialog"].registerCallback(
                DwtDialog.OK_BUTTON, ZaSpnegoGlobalServerDialog.updateGlobalServerSetting,
                instance, ZaApp.getInstance().dialogs["updateGlobalServerDialog"]._localXForm );
    } else
        ZaApp.getInstance().dialogs["updateGlobalServerDialog"].setTitle(titleText);

    ZaApp.getInstance().dialogs["updateGlobalServerDialog"].setObject(instance);
    ZaApp.getInstance().dialogs["updateGlobalServerDialog"].popup();
}

ZaTaskAuthConfigWizard.clientUAChanged = function (value, event, form) {
    var instance = this.getInstance();
}

///////////  Custom UA dialog //////////////

ZaSpnegoUACustomDialog = function(parent,   w, h, title) {
	if (arguments.length == 0) return;
	this._standardButtons = [DwtDialog.OK_BUTTON, DwtDialog.CANCEL_BUTTON];
	ZaXDialog.call(this, parent, "ZaSpnegoUACustomDialog", title, w, h,null,ZaId.DLG_AUTH_SPNEGO_UA);
	this._containedObject = {};
	this.initForm(ZaAlias.myXModel,this.getMyXForm());
    //this._helpURL = ZaSpnegoUACustomDialog.helpURL;
}

ZaSpnegoUACustomDialog.prototype = new ZaXDialog;
ZaSpnegoUACustomDialog.prototype.constructor = ZaSpnegoUACustomDialog;
//ZaSpnegoUACustomDialog.helpURL = location.pathname + ZaUtil.HELP_URL + "managing_domain/spnego_configuration.htm?locid="+AjxEnv.DEFAULT_LOCALE;



ZaSpnegoUACustomDialog.prototype.getMyXForm =
function() {
	var xFormObject = {
		numCols:1,
		items:[
            {type:_GROUP_,numCols:1,colSizes:["100%"],items: [ //allows tab key iteration
                {type:_OUTPUT_, value:ZaMsg.SpnegoSettingCustomDialogHeader, cssStyle:"padding-bottom:20px;padding-top:10px;"},
                {ref:ZaDomain.A2_zmailWebClientURLAllowedUA, type:_TEXTAREA_, width:450, height:100, align:_CENTER_
                },
                {type:_OUTPUT_, value:ZaMsg.SpnegoSettingCustomDialogHelp, cssStyle:"padding-bottom:10px;padding-top:20px;"}
            ]}
        ]
	};
	return xFormObject;
}

ZaSpnegoUACustomDialog.updateSpnegoUASetting = function() {
    if(ZaApp.getInstance().dialogs["spnegoUACustomDialog"])
        ZaApp.getInstance().dialogs["spnegoUACustomDialog"].popdown();
    var obj = ZaApp.getInstance().dialogs["spnegoUACustomDialog"].getObject();
    var allowedUA = [];
    if(obj[ZaDomain.A2_zmailWebClientURLAllowedUA]) {
        allowedUA = obj[ZaDomain.A2_zmailWebClientURLAllowedUA].split("\n");
    }
    this.setInstanceValue(allowedUA, ZaDomain.A_zmailWebClientLoginURLAllowedUA);
    this.setInstanceValue(allowedUA, ZaDomain.A_zmailWebClientLogoutURLAllowedUA);
}


///////////  Spnego Global & Server dialog //////////////



ZaSpnegoGlobalServerDialog = function(parent,   w, h, title) {
	if (arguments.length == 0) return;
	this._standardButtons = [DwtDialog.OK_BUTTON, DwtDialog.CANCEL_BUTTON];
	ZaXDialog.call(this, parent, "ZaSpnegoUACustomDialog", title, w, h,null,ZaId.DLG_AUTH_SPNEGO);
	this._containedObject = {};
	this.initForm(ZaAlias.myXModel,this.getMyXForm());
    //this._helpURL = ZaSpnegoGlobalServerDialog.helpURL;
}

ZaSpnegoGlobalServerDialog.prototype = new ZaXDialog;
ZaSpnegoGlobalServerDialog.prototype.constructor = ZaSpnegoGlobalServerDialog;
//ZaSpnegoGlobalServerDialog.helpURL = location.pathname + ZaUtil.HELP_URL + "managing_domain/spnego_configuration.htm?locid="+AjxEnv.DEFAULT_LOCALE;



ZaSpnegoGlobalServerDialog.prototype.getMyXForm =
function() {
	var xFormObject = {
		numCols:1,
		items:[
            {type:_GROUP_,numCols:1,colSizes:["100%"],items: [ //allows tab key iteration
                {type:_GROUP_, colSpan:2,numCols:2,colSizes:["150px","430px"],
                    visibilityChecks:[[ZaTaskAuthConfigWizard.checkSpnegoApplyType,ZaDomain.A2_zmailSpnegoApplyFor,ZaItem.GLOBAL_CONFIG]],
                    visibilityChangeEventSources:[ZaDomain.A2_zmailSpnegoApplyFor],
                    items:[
                            {type:_OUTPUT_, value:ZaMsg.spnegoGlobalSettingPageTitle, colSpan:2},
                            {type:_SPACER_, height:10, colSpan:"*"},
                            {ref: ZaDomain.A2_zmailSpnegoGlobalAuthEnabled, type: _CHECKBOX_,
                                label:ZaMsg.EnableSpnegoGlobal, width: "200px", subLabel:"",
                                labelLocation:_RIGHT_, align:_RIGHT_,
                                //labelCssStyle:"text-align:left;padding-left:20px;",
                                trueValue: "TRUE", falseValue: "FALSE"
                            },
                            {ref: ZaDomain.A2_zmailSpnegoAuthRealm,
                                type: _TEXTFIELD_, width: "200px",
                                label: ZaMsg.LBL_zmailSpnegoAuthRealm, labelCssStyle:"text-align:left;padding-left:20px;"
                            },
                            {ref: ZaDomain.A2_zmailSpnegoAuthErrorURL,
                                type: _TEXTFIELD_, width: "200px",
                                label: ZaMsg.LBL_zmailSpnegoAuthErrorURL, labelCssStyle:"text-align:left;padding-left:20px;"
                            }
                    ]
                },
                {type:_GROUP_, colSpan:2,numCols:2,colSizes:["150px","430px"],
                    visibilityChecks:[[ZaTaskAuthConfigWizard.checkSpnegoApplyType,ZaDomain.A2_zmailSpnegoApplyFor,ZaItem.SERVER]],
                    visibilityChangeEventSources:[ZaDomain.A2_zmailSpnegoApplyFor],
                    items:[
                            {type:_OUTPUT_, value:ZaMsg.spnegoServerSettingPageTitle, colSpan:2},
                            {type:_SPACER_, height:10, colSpan:"*"},
                            {ref:ZaDomain.A2_zmailSpnegoAuthPrincipal, type:_TEXTFIELD_,
                                labelCssStyle:"text-align:left;padding-left:20px;",
                                label:ZaMsg.NAD_MTA_SpnegoAuthPrincipal, width: "20em",
                                onChange: ZaServerXFormView.onFormFieldChanged
                            },
                            {ref:ZaDomain.A2_zmailSpnegoAuthTargetName, type:_TEXTFIELD_,
                                labelCssStyle:"text-align:left;padding-left:20px;",
                                label:ZaMsg.NAD_MTA_SpnegoAuthTargetName, width: "20em",
                                onChange: ZaServerXFormView.onFormFieldChanged
                            }
                    ]
                }
            ]}
        ]
	};
	return xFormObject;
}

ZaSpnegoGlobalServerDialog.updateGlobalServerSetting = function() {
    if(ZaApp.getInstance().dialogs["updateGlobalServerDialog"])
        ZaApp.getInstance().dialogs["updateGlobalServerDialog"].popdown();
    else return;

    var attr = null;
    var soapDoc = null;
    var obj = ZaApp.getInstance().dialogs["updateGlobalServerDialog"].getObject();
    if(obj[ZaDomain.A2_zmailSpnegoApplyFor] == ZaItem.GLOBAL_CONFIG) {
        var autoDlgForm = ZaApp.getInstance().dialogs["authWizard"]._localXForm;
        autoDlgForm.setInstanceValue(ZaTaskAuthConfigWizard.getGlobalSettingMsg(obj),
                ZaDomain.A2_zmailSpnegoGlobalSettingStatus);

        soapDoc = AjxSoapDoc.create("ModifyConfigRequest", ZaZmailAdmin.URN, null);

        if(obj[ZaDomain.A2_zmailSpnegoGlobalAuthEnabled]) {
            attr = soapDoc.set("a", obj[ZaDomain.A2_zmailSpnegoGlobalAuthEnabled]);
            attr.setAttribute("n", ZaGlobalConfig.A_zmailSpnegoAuthEnabled);
        }
        if(obj[ZaDomain.A2_zmailSpnegoAuthRealm]) {
            attr = soapDoc.set("a", obj[ZaDomain.A2_zmailSpnegoAuthRealm]);
            attr.setAttribute("n", ZaDomain.A2_zmailSpnegoAuthRealm);
        }
        if(obj[ZaDomain.A2_zmailSpnegoAuthErrorURL]) {
            attr = soapDoc.set("a", obj[ZaDomain.A2_zmailSpnegoAuthErrorURL]);
            attr.setAttribute("n", ZaDomain.A2_zmailSpnegoAuthErrorURL);
        }

        var params = new Object();
        params.soapDoc = soapDoc;
        var reqMgrParams = {
            controller : ZaApp.getInstance().getCurrentController(),
            busyMsg : ZaMsg.BUSY_GET_ALL_CONFIG
        }
	    ZaRequestMgr.invoke(params, reqMgrParams);

    } else if(obj[ZaDomain.A2_zmailSpnegoApplyFor] == ZaItem.SERVER && obj[ZaDomain.A2_zmailSpnegoTargetServer]) {
        soapDoc = AjxSoapDoc.create("ModifyServerRequest", ZaZmailAdmin.URN, null);
        soapDoc.set("id", obj[ZaDomain.A2_zmailSpnegoTargetServer]);
        if(obj[ZaDomain.A2_zmailSpnegoAuthPrincipal]) {
            attr = soapDoc.set("a", obj[ZaDomain.A2_zmailSpnegoAuthPrincipal]);
            attr.setAttribute("n", ZaDomain.A2_zmailSpnegoAuthPrincipal);
        }
        if(obj[ZaDomain.A2_zmailSpnegoAuthTargetName]) {
            attr = soapDoc.set("a", obj[ZaDomain.A2_zmailSpnegoAuthTargetName]);
            attr.setAttribute("n", ZaDomain.A2_zmailSpnegoAuthTargetName);
        }

		var params = new Object();
		params.soapDoc = soapDoc;
		var reqMgrParams = {
			controller : ZaApp.getInstance().getCurrentController(),
			busyMsg : ZaMsg.BUSY_MODIFY_SERVER
		}
		ZaRequestMgr.invoke(params, reqMgrParams);
    }

}