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
* @class ZaMigrationXWizard
* @contructor ZaMigrationXWizard
* @param parent DwtShell
* @param entry ZaBulkProvision
* @author Greg Solovyev
**/
function ZaMigrationXWizard (parent, entry) {
    var w = "650px" ;
    ZaXWizardDialog.call(this, parent, null, org_zmail_bulkprovision.BP_Exch_Mig_Wiz_Title,
                                w, (AjxEnv.isIE ? "330px" :"320px"),"ZaMigrationXWizard");

	this.stepChoices = [
	    {label:org_zmail_bulkprovision.MIG_Wizard_overview, value:ZaMigrationXWizard.STEP_INTRODUCTION},
	    {label:org_zmail_bulkprovision.MIG_Wizard_migrationOptions, value:ZaMigrationXWizard.STEP_PROV_OPTIONS},
	    {label:org_zmail_bulkprovision.MIG_Wizard_mailServerInfo, value:ZaMigrationXWizard.STEP_EXCHANGE_INFO},
	    {label:org_zmail_bulkprovision.MIG_Wizard_ADOptions, value:ZaMigrationXWizard.STEP_LDAP_INFO},
	    {label:org_zmail_bulkprovision.MIG_Wizard_review, value:ZaMigrationXWizard.STEP_REVIEW},
	    {label:org_zmail_bulkprovision.BP_Wizard_download, value:ZaMigrationXWizard.STEP_DOWNLOAD_FILE}		
	];
		
    this.initForm(ZaBulkProvision.getMyXModel(),this.getMyXForm(entry),null);

    this._helpURL = [location.pathname, ZaUtil.HELP_URL, ZaMigrationXWizard.helpURL, "?locid=", AjxEnv.DEFAULT_LOCALE].join("");;
}
ZaMigrationXWizard.STEP_INDEX = 1;
ZaMigrationXWizard.STEP_INTRODUCTION = ZaMigrationXWizard.STEP_INDEX++;
ZaMigrationXWizard.STEP_PROV_OPTIONS = ZaMigrationXWizard.STEP_INDEX++;
ZaMigrationXWizard.STEP_EXCHANGE_INFO = ZaMigrationXWizard.STEP_INDEX++;
ZaMigrationXWizard.STEP_LDAP_INFO = ZaMigrationXWizard.STEP_INDEX++;
ZaMigrationXWizard.STEP_REVIEW = ZaMigrationXWizard.STEP_INDEX++;
ZaMigrationXWizard.STEP_DOWNLOAD_FILE = ZaMigrationXWizard.STEP_INDEX++;

ZaMigrationXWizard.prototype = new ZaXWizardDialog;
ZaMigrationXWizard.prototype.constructor = ZaMigrationXWizard;
ZaMigrationXWizard.prototype.cacheDialog = false;
ZaMigrationXWizard.prototype.miniType = 2;
ZaXDialog.XFormModifiers["ZaMigrationXWizard"] = new Array();
ZaMigrationXWizard.helpURL = "appliance/zap_migrating_multiple_accounts.htm";
ZaMigrationXWizard.prototype.getCacheName =  function () {
     return "migrationWizard";
}
/**
 * server callbacks
 */
ZaMigrationXWizard.prototype.generateBulkFileCallback = 
function(params,resp) {
	try {
		if(resp && resp.isException()) {
			this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
			ZaApp.getInstance().getCurrentController()._handleException(resp.getException(), "ZaMigrationXWizard.prototype.generateBulkFileCallback");
		} else {
			var response = resp.getResponse().Body.GenerateBulkProvisionFileFromLDAPResponse;
			if(response.fileToken && response.fileToken[0] && response.fileToken[0]._content) {
				this._localXForm.setInstanceValue(
						AjxMessageFormat.format("{0}//{1}:{2}/service/afd/?action=getBulkFile&fileID={3}&fileFormat={4}",
								[location.protocol,location.hostname,location.port,response.fileToken[0]._content,ZaBulkProvision.FILE_FORMAT_MIGRATION_XML]),
								ZaBulkProvision.A2_generatedFileLink);
				this.goPage(ZaMigrationXWizard.STEP_DOWNLOAD_FILE);
				this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(true);
				this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
				this._button[DwtDialog.CANCEL_BUTTON].setEnabled(false);
			}
		}
	} catch (ex) {
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaMigrationXWizard.prototype.generateBulkFileCallback");	
	}
		
	this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);	
};

ZaMigrationXWizard.prototype.previewCallback = function(params,resp) {
	try {
		if(resp && resp.isException()) {
			throw(resp.getException());
		} else {
			var response = resp.getResponse().Body.GenerateBulkProvisionFileFromLDAPResponse;
			var accountCount = "0";
			var domainCount = "0";
			var skippedDomainCount = "0";
			var skippedCount = "0";
			if(response[ZaBulkProvision.A2_domainCount] && response[ZaBulkProvision.A2_domainCount][0] && response[ZaBulkProvision.A2_domainCount][0]._content) {
				domainCount = parseInt(response[ZaBulkProvision.A2_domainCount][0]._content);
			}				
			if(response[ZaBulkProvision.A2_skippedDomainCount] && response[ZaBulkProvision.A2_skippedDomainCount][0] && response[ZaBulkProvision.A2_skippedDomainCount][0]._content) {
				skippedDomainCount = parseInt(response[ZaBulkProvision.A2_skippedDomainCount][0]._content);
			}		
			if(response[ZaBulkProvision.A2_totalCount] && response[ZaBulkProvision.A2_totalCount][0] && response[ZaBulkProvision.A2_totalCount][0]._content) {
				totalCount = parseInt(response[ZaBulkProvision.A2_totalCount][0]._content);
			}
			if(response[ZaBulkProvision.A2_skippedAccountCount] && response[ZaBulkProvision.A2_skippedAccountCount][0] && response[ZaBulkProvision.A2_skippedAccountCount][0]._content) {
				skippedCount = parseInt(response[ZaBulkProvision.A2_skippedAccountCount][0]._content);
			}	
			
			this.goPage(ZaMigrationXWizard.STEP_REVIEW);
			
			this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
			this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
			this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
			this._button[DwtDialog.CANCEL_BUTTON].setEnabled(true);
			
			this._localXForm.setInstanceValue(domainCount,ZaBulkProvision.A2_domainCount);
			this._localXForm.setInstanceValue(skippedDomainCount,ZaBulkProvision.A2_skippedDomainCount);
			this._localXForm.setInstanceValue(totalCount,ZaBulkProvision.A2_totalCount);
			this._localXForm.setInstanceValue(skippedCount,ZaBulkProvision.A2_skippedAccountCount);
		}
	} catch (ex) {
		if(ex.code == ZaBulkProvision.BP_INVALID_SEARCH_FILTER) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(org_zmail_bulkprovision.ERROR_INVALID_SEARCH_FILTER,[ex.msg]),ex);	
		} else if(ex.code == ZaBulkProvision.BP_NAMING_EXCEPTION) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(AjxMessageFormat.format(org_zmail_bulkprovision.BP_NAMING_EXCEPTION,[ex.msg]),ex);	
		} else {
			ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaMigrationXWizard.prototype.previewCallback");
		}

		this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
		this._button[DwtDialog.CANCEL_BUTTON].setEnabled(true);
	}
};

/**
* Overwritten methods that control wizard's flow (open, go next,go previous, finish)
**/
ZaMigrationXWizard.prototype.popup =
function (loc) {
	ZaXWizardDialog.prototype.popup.call(this, loc);
    if(this.prevCallback) {
    	this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
    } else {
    	this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(false);	
    }
    this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
}

ZaMigrationXWizard.prototype.handleXFormChange =
function () {
    var cStep = this._containedObject[ZaModel.currentStep] ;
    if(cStep != ZaMigrationXWizard.STEP_INTRODUCTION) {
        this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
		this._button[DwtDialog.CANCEL_BUTTON].setEnabled(true);
    }
    if(cStep == ZaMigrationXWizard.STEP_DOWNLOAD_FILE) {
        this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(true);
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
		this._button[DwtDialog.CANCEL_BUTTON].setEnabled(false);
     }



}

ZaMigrationXWizard.prototype.goNext =
function() {
	var cStep = this._containedObject[ZaModel.currentStep] ;

	if(cStep == ZaMigrationXWizard.STEP_INTRODUCTION) {
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
		this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
		this._button[DwtDialog.CANCEL_BUTTON].setEnabled(true);
		if(this._containedObject[ZaBulkProvision.A2_provAction] == ZaBulkProvision.ACTION_IMPORT_CSV || 
				this._containedObject[ZaBulkProvision.A2_provAction] == ZaBulkProvision.ACTION_IMPORT_XML) {
			this.goPage(ZaMigrationXWizard.STEP_UPLOAD_FILE);
		} else {
			this.goPage(ZaMigrationXWizard.STEP_PROV_OPTIONS);
		}
	} else if(cStep == ZaMigrationXWizard.STEP_PROV_OPTIONS) {
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
		if(this._containedObject[ZaBulkProvision.A2_generatePassword] == "FALSE") {
			/**
			 * Check that passwords match
			 */
			if(this._containedObject[ZaBulkProvision.A2_confirmPassword] != this._containedObject[ZaBulkProvision.A2_password]) {
				ZaApp.getInstance().getCurrentController().popupErrorDialog(org_zmail_bulkprovision.ERROR_PASSWORDS_DONT_MATCH);
				return;
			}
		}
		if(this._containedObject[ZaBulkProvision.A2_provAction] == ZaBulkProvision.ACTION_GENERATE_MIG_XML) {
			this.goPage(ZaMigrationXWizard.STEP_EXCHANGE_INFO);
		} else {
			this.goPage(ZaMigrationXWizard.STEP_LDAP_INFO);
		}
		this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
		this._button[DwtDialog.CANCEL_BUTTON].setEnabled(true);
	} else if(cStep == ZaMigrationXWizard.STEP_EXCHANGE_INFO) { 
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(true);
		/**
		 * Check that passwords match
		 */
		if(this._containedObject[ZaBulkProvision.A2_ZmailAdminPassword] != this._containedObject[ZaBulkProvision.A2_ZmailAdminPasswordConfirm]) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(org_zmail_bulkprovision.ERROR_PASSWORDS_DONT_MATCH);
			return;
		}		
		/**
		 * exch mig wizard requires <domain> in <ZmailServer>
		 */
		if(!this._containedObject[ZaBulkProvision.A2_TargetDomainName]) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(org_zmail_bulkprovision.MUST_SELECT_TARGET_DOMAIN);
		    return;
        }
		
		/**
		 * Set defaults
		 */
		
		if(!this._containedObject[ZaBulkProvision.A2_GalLdapSearchBase] && typeof this._containedObject[ZaBulkProvision.A2_TargetDomainName] == "string") {
			var searchBase = "cn=Users,dc=" + this._containedObject[ZaBulkProvision.A2_TargetDomainName].replace(".",",dc=");
			this._localXForm.setInstanceValue(searchBase,ZaBulkProvision.A2_GalLdapSearchBase);
		}
		
		if(!this._containedObject[ZaBulkProvision.A2_GalLdapBindDn] && typeof this._containedObject[ZaBulkProvision.A2_TargetDomainName] == "string") {
			var bindDN = "cn=administrator,cn=Users,dc=" + this._containedObject[ZaBulkProvision.A2_TargetDomainName].replace(".",",dc=");
			this._localXForm.setInstanceValue(bindDN,ZaBulkProvision.A2_GalLdapBindDn);
		}
		
		this.goPage(ZaMigrationXWizard.STEP_LDAP_INFO);
		this._button[DwtDialog.CANCEL_BUTTON].setEnabled(true);
		this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
				
	} else if(cStep == ZaMigrationXWizard.STEP_LDAP_INFO) {
		/**
		 * Check that LDAP URL is not empty
		 */
		if(AjxUtil.isEmpty(this._containedObject[ZaBulkProvision.A2_GalLdapURL])) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(org_zmail_bulkprovision.ERROR_LDAP_URL_REQUIRED);
			return;
		}		

		/**
		 * Check that Bind DN is not empty
		 */
		if(AjxUtil.isEmpty(this._containedObject[ZaBulkProvision.A2_GalLdapBindDn])) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(org_zmail_bulkprovision.ERROR_BIND_DN_REQUIRED);
			return;
		}		
		
				
		/**
		 * Check that LDAP filter is not empty
		 */
		if(AjxUtil.isEmpty(this._containedObject[ZaBulkProvision.A2_GalLdapFilter])) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(org_zmail_bulkprovision.ERROR_LDAP_FILTER_REQUIRED);
			return;
		}
		
		/**
		 * Check that LDAP search base is not empty
		 */
		if(AjxUtil.isEmpty(this._containedObject[ZaBulkProvision.A2_GalLdapSearchBase])) {
			ZaApp.getInstance().getCurrentController().popupErrorDialog(org_zmail_bulkprovision.ERROR_LDAP_BASE_REQUIRED);
			return;
		}		
		
		this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(false);
		this._button[DwtDialog.CANCEL_BUTTON].setEnabled(true);
		
		var callback = new AjxCallback(this, ZaMigrationXWizard.prototype.previewCallback,{});
		ZaBulkProvision.generateBulkProvisionPreview(this._containedObject,callback);

	} else if (cStep == ZaMigrationXWizard.STEP_REVIEW) {
		/**
		 * Generate the file and launch a callback when file is ready
		 */
		this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(false);
		this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(false);
		this._button[DwtDialog.CANCEL_BUTTON].setEnabled(true);
		
		var callback = new AjxCallback(this, ZaMigrationXWizard.prototype.generateBulkFileCallback,{action:this._containedObject[ZaBulkProvision.A2_provAction]});
		ZaBulkProvision.generateBulkProvisionFile(this._containedObject,callback);
    } 
}

ZaMigrationXWizard.prototype.goPrev =
function() {
	var cStep = this._containedObject[ZaModel.currentStep] ;
	var prevStep ;
	
	if (cStep == ZaMigrationXWizard.STEP_PROV_OPTIONS) {
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
		if(!this.prevCallback) {
			this._button[DwtWizardDialog.PREV_BUTTON].setEnabled(false);
		}
		prevStep = ZaMigrationXWizard.STEP_INTRODUCTION ;
    } else if (cStep == ZaMigrationXWizard.STEP_LDAP_INFO) {
		prevStep = ZaMigrationXWizard.STEP_EXCHANGE_INFO;
		this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
    } else if (cStep == ZaMigrationXWizard.STEP_EXCHANGE_INFO) {
    	prevStep = ZaMigrationXWizard.STEP_PROV_OPTIONS;
    	this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
    } else if(cStep == ZaMigrationXWizard.STEP_DOWNLOAD_FILE) {
		prevStep = ZaMigrationXWizard.STEP_REVIEW;
    	this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
    } else if(cStep == ZaMigrationXWizard.STEP_REVIEW) {
		prevStep = ZaMigrationXWizard.STEP_LDAP_INFO;
    	this._button[DwtWizardDialog.NEXT_BUTTON].setEnabled(true);
    } else if(this.prevCallback && cStep == ZaMigrationXWizard.STEP_INTRODUCTION) {
    	this.prevCallback.run(this._containedObject);
    	return;
    }
	this._button[DwtWizardDialog.FINISH_BUTTON].setEnabled(false);
	this._button[DwtDialog.CANCEL_BUTTON].setEnabled(true);
    this.goPage(prevStep);
}

/**
* @method setObject sets the object contained in the view
* @param entry -  object to display
**/
ZaMigrationXWizard.prototype.setObject =
function(entry) {
	this._containedObject = new ZaBulkProvision();
	this._containedObject = entry ;

	this._containedObject[ZaModel.currentStep] = entry[ZaModel.currentStep]||ZaMigrationXWizard.STEP_INTRODUCTION;
	this._containedObject[ZaBulkProvision.A_mustChangePassword] = entry[ZaBulkProvision.A_mustChangePassword]||"TRUE";

    this._containedObject._uuid = entry._uuid||ZaUtil.getItemUUid();

    this.prevCallback = entry.prevCallback;
    this._localXForm.setInstance(this._containedObject);
}

ZaMigrationXWizard.myXFormModifier = function(xFormObject,entry) {
	var cases = new Array();

	var case_introduction = {
		type:_CASE_,numCols:1,tabGroupKey:ZaMigrationXWizard.STEP_INTRODUCTION,caseKey:ZaMigrationXWizard.STEP_INTRODUCTION,
		items:[
		      	{type:_DWT_ALERT_, style:DwtAlert.INFO,iconVisible:false,content:org_zmail_bulkprovision.ExchMigrationWizardIntro,visibilityChecks:[],enableDisableChecks:[]}
		]
	};
	cases.push(case_introduction);
	/**
	 * Enter options for provisioning
	 */
	var case_prov_options = {
		type:_CASE_, numCols:2, colSizes:["250px","*"],tabGroupKey:ZaMigrationXWizard.STEP_PROV_OPTIONS, caseKey:ZaMigrationXWizard.STEP_PROV_OPTIONS,
		items:[
		       	{type:_DWT_ALERT_, style:DwtAlert.INFO,iconVisible:false,content:org_zmail_bulkprovision.ProvOptionsNote,visibilityChecks:[],enableDisableChecks:[]},
		       	{type:_RADIO_, groupname:"account_password_option",ref:ZaBulkProvision.A2_generatePassword,
					labelLocation:_RIGHT_,label:org_zmail_bulkprovision.GenerateRandomPassword, bmolsnr:true,
					updateElement:function (newValue) {
						this.getElement().checked = (newValue == "TRUE");
					},
					elementChanged: function(elementValue,instanceValue, event) {
						this.setInstanceValue("TRUE",ZaBulkProvision.A2_generatePassword);
					},visibilityChecks:[],enableDisableChecks:[]
				},
				{type:_TEXTFIELD_,ref:ZaBulkProvision.A2_genPasswordLength,label:org_zmail_bulkprovision.GeneratedPasswordLength,
				   visibilityChecks:[],enableDisableChangeEventSources:[ZaBulkProvision.A2_generatePassword],
				   enableDisableChecks:[[XForm.checkInstanceValue,ZaBulkProvision.A2_generatePassword,"TRUE"]],
				   cssClass:"admin_xform_number_input"
				},
				{type:_RADIO_, groupname:"account_password_option",ref:ZaBulkProvision.A2_generatePassword, bmolsnr:true,
					labelLocation:_RIGHT_,label:org_zmail_bulkprovision.UseSamePassword,
					updateElement:function (newValue) {
						this.getElement().checked = (newValue == "FALSE");
					},
					elementChanged: function(elementValue,instanceValue, event) {
						this.setInstanceValue("FALSE",ZaBulkProvision.A2_generatePassword);
					},visibilityChecks:[],enableDisableChecks:[]
				},
		       	{ref:ZaBulkProvision.A2_password, type:_SECRET_, 
					label:org_zmail_bulkprovision.PasswordToUse, labelLocation:_LEFT_, 
					cssClass:"admin_xform_name_input",visibilityChecks:[],
					enableDisableChangeEventSources:[ZaBulkProvision.A2_generatePassword],
					enableDisableChecks:[[XForm.checkInstanceValue,ZaBulkProvision.A2_generatePassword,"FALSE"]]
				},
				{ref:ZaBulkProvision.A2_confirmPassword, type:_SECRET_,
					label:org_zmail_bulkprovision.PasswordToUseConfirm, labelLocation:_LEFT_, 
					cssClass:"admin_xform_name_input",visibilityChecks:[],
					enableDisableChangeEventSources:[ZaBulkProvision.A2_generatePassword],
					enableDisableChecks:[[XForm.checkInstanceValue,ZaBulkProvision.A2_generatePassword,"FALSE"]]
				},
				{ref:ZaBulkProvision.A_mustChangePassword,  type:_WIZ_CHECKBOX_,
					label:org_zmail_bulkprovision.RequireChangePassword,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[]
				},
				{ref:ZaBulkProvision.A2_provisionUsers,  type:_WIZ_CHECKBOX_,
					label:org_zmail_bulkprovision.A2_provisionUsers,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[]
				},				
				{ref:ZaBulkProvision.A2_importMails,  type:_WIZ_CHECKBOX_,
					label:org_zmail_bulkprovision.MigImportMails,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[]
				},
				{ref:ZaBulkProvision.A2_importContacts,  type:_WIZ_CHECKBOX_,
					label:org_zmail_bulkprovision.MigImportContacts,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[]
				},
				{ref:ZaBulkProvision.A2_importTasks,  type:_WIZ_CHECKBOX_,
					label:org_zmail_bulkprovision.MigImportTasks,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[]
				},
				{ref:ZaBulkProvision.A2_importCalendar,  type:_WIZ_CHECKBOX_,
					label:org_zmail_bulkprovision.MigImportCalendar,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[]
				},
				{ref:ZaBulkProvision.A2_importDeletedItems,  type:_WIZ_CHECKBOX_,
					label:org_zmail_bulkprovision.MigImportDeletedItems,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[]
				},
				{ref:ZaBulkProvision.A2_importJunk,  type:_WIZ_CHECKBOX_,
					label:org_zmail_bulkprovision.MigImportJunk,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[]
				},
				{ref:ZaBulkProvision.A2_ignorePreviouslyImported,  type:_WIZ_CHECKBOX_,
					label:org_zmail_bulkprovision.MigIgnorePreviouslyImported,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[]
				},
				{ref:ZaBulkProvision.A2_InvalidSSLOk,  type:_WIZ_CHECKBOX_,
					label:org_zmail_bulkprovision.MigInvalidSSLOk,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[]
				}						
		]
	};
	cases.push(case_prov_options);
	
	/**
	 * Enter options specific to Exchange Migration
	 */
	
	var case_exchange_options = {
			type:_CASE_, numCols:2,colSizes:["250px","*"], tabGroupKey:ZaMigrationXWizard.STEP_EXCHANGE_INFO, caseKey:ZaMigrationXWizard.STEP_EXCHANGE_INFO,
			items:[
			       	{ref:ZaBulkProvision.A2_TargetDomainName, type:_TEXTFIELD_,
						label:org_zmail_bulkprovision.TargetDomainName,
						toolTipContent:ZaMsg.tt_StartTypingDomainName,
						//dataFetcherMethod:ZaSearch.prototype.dynSelectSearchDomains,
						//dataFetcherClass:ZaSearch,editable:true,
						visibilityChecks:[],
						visibilityChangeEventSources:[],
						enableDisableChecks:[]
					},
			       	{type:_DWT_ALERT_, style:DwtAlert.INFO,iconVisible:false,content:org_zmail_bulkprovision.ZmailAdminPasswordNote,
			       		visibilityChecks:[],enableDisableChecks:[],colSpan:2
			       	},
					{ref:ZaBulkProvision.A2_ZmailAdminLogin, type:_OUTPUT_, label:org_zmail_bulkprovision.A2_ZmailAdminLogin, labelLocation:_LEFT_, 
						enableDisableChecks:[],visibilityChecks:[]				
					},
					{ref:ZaBulkProvision.A2_ZmailAdminPassword, type:_SECRET_, label:org_zmail_bulkprovision.A2_ZmailAdminPassword, labelLocation:_LEFT_, 
						enableDisableChecks:[],visibilityChecks:[]				
					},
					{ref:ZaBulkProvision.A2_ZmailAdminPasswordConfirm, type:_SECRET_, label:org_zmail_bulkprovision.A2_ZmailAdminPasswordConfirm, labelLocation:_LEFT_, 
						enableDisableChecks:[],visibilityChecks:[]				
					},
					{type:_DWT_ALERT_, style:DwtAlert.INFO,iconVisible:false,content:org_zmail_bulkprovision.MAPIINfoNote,
			       		visibilityChecks:[],enableDisableChecks:[],colSpan:2
			       	},
					{ref:ZaBulkProvision.A2_MapiProfile, type:_TEXTFIELD_, label:org_zmail_bulkprovision.A2_MapiProfile, labelLocation:_LEFT_, 
						enableDisableChecks:[],visibilityChecks:[]				
					},
					{ref:ZaBulkProvision.A2_MapiServer, type:_TEXTFIELD_, label:org_zmail_bulkprovision.A2_MapiServer, labelLocation:_LEFT_, 
						enableDisableChecks:[],visibilityChecks:[]				
					},
					{ref:ZaBulkProvision.A2_MapiLogonUserDN, type:_TEXTFIELD_, width:"380px", label:org_zmail_bulkprovision.A2_MapiLogonUserDN, labelLocation:_LEFT_, 
						enableDisableChecks:[],visibilityChecks:[]				
					}

			]
	};
	cases.push(case_exchange_options);
	/**
	 * Enter LDAP info for generating bulk file or direct import
	 */
	var case_ldap_info = {
		type:_CASE_, numCols:2, colSizes:["250px","380px"],tabGroupKey:ZaMigrationXWizard.STEP_LDAP_INFO, caseKey:ZaMigrationXWizard.STEP_LDAP_INFO,
		items:[
		       	{type:_DWT_ALERT_, style:DwtAlert.INFO, iconVisible:false, content:org_zmail_bulkprovision.ADInfoStepNote,colSpan:2},
		       	{ref:ZaBulkProvision.A2_maxResults, type:_TEXTFIELD_,cssClass:"admin_xform_number_input", 
		       		label:org_zmail_bulkprovision.LDAPMaxResults, labelLocation:_LEFT_,labelWrap:true,
		       		visibilityChecks:[],enableDisableChecks:[]
		       	},
		       	{type:_GROUP_, numCols:6, colSpan:2,label:"   ",labelLocation:_LEFT_,
					visibilityChecks:[],
					items: [
						{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:" ", width:"35px"},
						{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:org_zmail_bulkprovision.ADServerName, width:"200px"},
						{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:" ", width:"5px"},									
						{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:org_zmail_bulkprovision.ADServerPort,  width:"40px"},	
						{type:_OUTPUT_, label:null, labelLocation:_NONE_, value:org_zmail_bulkprovision.ADUseSSL, width:"*"}									
					]
				},		       
				{ref:ZaBulkProvision.A2_GalLdapURL, type:_LDAPURL_, label:org_zmail_bulkprovision.LDAPUrl,ldapSSLPort:"3269",ldapPort:"3268",  labelLocation:_LEFT_,
					visibilityChecks:[],enableDisableChecks:[]
				},
				{ref:ZaBulkProvision.A2_GalLdapBindDn, type:_TEXTFIELD_, width:"380px", label:ZaMsg.Domain_GalLdapBindDn, labelLocation:_LEFT_, 
					enableDisableChecks:[],visibilityChecks:[],bmolsnr:true				
				},
				{ref:ZaBulkProvision.A2_GalLdapBindPassword, type:_SECRET_, label:ZaMsg.Domain_GalLdapBindPassword, labelLocation:_LEFT_, 
					enableDisableChecks:[],visibilityChecks:[]				
				},
				{ref:ZaBulkProvision.A2_GalLdapFilter, type:_TEXTAREA_, width:380, height:40, 
					label:ZaMsg.Domain_GalLdapFilter, labelLocation:_LEFT_, 
					enableDisableChecks:[],visibilityChecks:[]
				},
				{ref:ZaBulkProvision.A2_GalLdapSearchBase, type:_TEXTAREA_, width:380, height:40, label:ZaMsg.Domain_GalLdapSearchBase, 
					labelLocation:_LEFT_, enableDisableChecks:[],visibilityChecks:[],bmolsnr:true
				}					
		]
	};
	cases.push(case_ldap_info);
	
	/**
	 * Review options before generating the file
	 */

	var case_review = {
		type:_CASE_, numCols:2, colSizes:["200px", "*"], 
		caseKey:ZaMigrationXWizard.STEP_REVIEW,
		items:[
		       {type:_DWT_ALERT_, style:DwtAlert.INFO,iconVisible:false,content:org_zmail_bulkprovision.ReviewStepNote,visibilityChecks:[],enableDisableChecks:[]},
		       {type:_OUTPUT_,ref:ZaBulkProvision.A2_domainCount,label:org_zmail_bulkprovision.ReviewDomainCount,bmolsnr:true},
		       {type:_OUTPUT_,ref:ZaBulkProvision.A2_skippedDomainCount,label:org_zmail_bulkprovision.ReviewSkippedDomainCount,bmolsnr:true},
		       {type:_OUTPUT_,ref:ZaBulkProvision.A2_totalCount,label:org_zmail_bulkprovision.ReviewAccountCount,bmolsnr:true},
		       {type:_OUTPUT_,ref:ZaBulkProvision.A2_skippedAccountCount,label:org_zmail_bulkprovision.ReviewSkippedAccountCount,bmolsnr:true},
		       {ref:ZaBulkProvision.A2_TargetDomainName, type:_OUTPUT_,	label:org_zmail_bulkprovision.TargetDomainName,visibilityChecks:[]},
		       {ref:ZaBulkProvision.A2_ZmailAdminLogin, type:_OUTPUT_, label:org_zmail_bulkprovision.A2_ZmailAdminLogin, labelLocation:_LEFT_, 
		    	   enableDisableChecks:[],visibilityChecks:[]				
		       },
		       {ref:ZaBulkProvision.A2_generatePassword,  type:_OUTPUT_,  
		    	   label:org_zmail_bulkprovision.RevGenerateRandomPassword,visibilityChecks:[],enableDisableChecks:[],
		    	   getDisplayValue:function(val) {	return val=="TRUE" ? ZaMsg.Yes : ZaMsg.No; 	}
		       },
		       {type:_OUTPUT_,ref:ZaBulkProvision.A2_genPasswordLength,label:org_zmail_bulkprovision.GeneratedPasswordLength,
				   visibilityChecks:[],enableDisableChangeEventSources:[ZaBulkProvision.A2_generatePassword],
				   enableDisableChecks:[[XForm.checkInstanceValue,ZaBulkProvision.A2_generatePassword,"TRUE"]]
			   },
		       {ref:ZaBulkProvision.A_mustChangePassword,  type:_OUTPUT_,  
		    	   label:org_zmail_bulkprovision.RevRequireChangePassword,visibilityChecks:[],enableDisableChecks:[],
		    	   getDisplayValue:function(val) {	return val=="TRUE" ? ZaMsg.Yes : ZaMsg.No; 	}
		       },		       
		       {ref:ZaBulkProvision.A2_provisionUsers,  type:_OUTPUT_,  
		    	   label:org_zmail_bulkprovision.RevA2_provisionUsers,visibilityChecks:[],enableDisableChecks:[],
		    	   getDisplayValue:function(val) {	return val=="TRUE" ? ZaMsg.Yes : ZaMsg.No; 	}
		       },
		       {ref:ZaBulkProvision.A2_importMails,  type:_OUTPUT_,  
					label:org_zmail_bulkprovision.RevMigImportMails,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[],
					getDisplayValue:function(val) {	return val=="TRUE" ? ZaMsg.Yes : ZaMsg.No; 	}		       
		       },
		       {ref:ZaBulkProvision.A2_importContacts,  type:_OUTPUT_,  
		    	   label:org_zmail_bulkprovision.RevMigImportContacts,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[],
		    	   getDisplayValue:function(val) {	return val=="TRUE" ? ZaMsg.Yes : ZaMsg.No; 	}
		       },
		       {ref:ZaBulkProvision.A2_importTasks,  type:_OUTPUT_,  
					label:org_zmail_bulkprovision.RevMigImportTasks,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[],
					getDisplayValue:function(val) {	return val=="TRUE" ? ZaMsg.Yes : ZaMsg.No; 	}
		       },
		       {ref:ZaBulkProvision.A2_importCalendar,  type:_OUTPUT_,  
					label:org_zmail_bulkprovision.RevMigImportCalendar,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[],
					getDisplayValue:function(val) {	return val=="TRUE" ? ZaMsg.Yes : ZaMsg.No; 	}
		       },
		       {ref:ZaBulkProvision.A2_importDeletedItems,  type:_OUTPUT_,  
					label:org_zmail_bulkprovision.RevMigImportDeletedItems,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[],
					getDisplayValue:function(val) {	return val=="TRUE" ? ZaMsg.Yes : ZaMsg.No; 	}
		       },
		       {ref:ZaBulkProvision.A2_importJunk,  type:_OUTPUT_,  
					label:org_zmail_bulkprovision.RevMigImportJunk,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[],
					getDisplayValue:function(val) {	return val=="TRUE" ? ZaMsg.Yes : ZaMsg.No; 	}
		       },
		       {ref:ZaBulkProvision.A2_ignorePreviouslyImported,  type:_OUTPUT_,  
					label:org_zmail_bulkprovision.RevMigIgnorePreviouslyImported,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[],
					getDisplayValue:function(val) {	return val=="TRUE" ? ZaMsg.Yes : ZaMsg.No; 	}
		       },
		       {ref:ZaBulkProvision.A2_InvalidSSLOk,  type:_OUTPUT_,  
		    	   label:org_zmail_bulkprovision.RevMigInvalidSSLOk,trueValue:"TRUE", falseValue:"FALSE",visibilityChecks:[],enableDisableChecks:[],
		    	   getDisplayValue:function(val) {	return val=="TRUE" ? ZaMsg.Yes : ZaMsg.No; 	}
		       }		       
		]
	};

	cases.push(case_review);	
	/**
	 * Download generated bulk file
	 */
	var case_download_file = {
		type:_CASE_, numCols:1, colSizes:["*"], tabGroupKey:ZaMigrationXWizard.STEP_DOWNLOAD_FILE, 
		caseKey:ZaMigrationXWizard.STEP_DOWNLOAD_FILE,
		items:[
		       {type:_DWT_ALERT_, style:DwtAlert.INFO, iconVisible:false, content:org_zmail_bulkprovision.ClickToDownloadGeneratedFile},
		       {type:_DATA_URL_,labelLocation:_NONE_,label:org_zmail_bulkprovision.DownloadMigrationXML,ref:ZaBulkProvision.A2_generatedFileLink},
		       {type:_ANCHOR_,labelLocation:_NONE_,label:org_zmail_bulkprovision.DownloadMigrationWizard,href:ZaMsg.MIG_WIZ_DOWNLOAD_LINK}
		]
	};
	cases.push(case_download_file);
	

	
    var contentW = 620;
    xFormObject.items = [
			{type:_OUTPUT_, colSpan:2, align:_CENTER_, valign:_TOP_, ref:ZaModel.currentStep,
                choices:this.stepChoices, valueChangeEventSources:[ZaModel.currentStep]},
			{type:_SEPARATOR_, align:_CENTER_, valign:_TOP_},
			{type:_SPACER_,  align:_CENTER_, valign:_TOP_},
			{type:_SWITCH_, width:contentW, align:_LEFT_, valign:_TOP_, items:cases}
		];
};
ZaXDialog.XFormModifiers["ZaMigrationXWizard"].push(ZaMigrationXWizard.myXFormModifier);