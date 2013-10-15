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
 * Date: 8/24/11
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */


ZaTaskAutoProvDialog = function(parent, title, width, height) {
    if (arguments.length == 0) return;
    var applyButton = new DwtDialog_ButtonDescriptor(ZaTaskAutoProvDialog.APPLY_BUTTON, ZaMsg.LBL_ApplyButton,
            DwtDialog.ALIGN_RIGHT, new AjxCallback(this, this._applyButtonListener));
    var helpButton = new DwtDialog_ButtonDescriptor(ZaXWizardDialog.HELP_BUTTON, ZaMsg.TBB_Help,
        DwtDialog.ALIGN_LEFT, new AjxCallback(this, this._helpButtonListener));
    this._standardButtons = [DwtDialog.OK_BUTTON, DwtDialog.CANCEL_BUTTON];
    this._extraButtons = [helpButton, applyButton];
    this._width = width || "680px";
    this._height = height || "390px";
    ZaXDialog.call(this, parent, null, title, this._width, this._height, null, ZaId.DLG_AUTPROV_MANUAL+"_ENHANCE");
    this._containedObject = {};
    this.initForm(ZaDomain.myXModel,this.getMyXForm());
    this._helpURL = ZaTaskAutoProvDialog.helpURL;

	this._forceApplyMessageDialog = new ZaMsgDialog(ZaApp.getInstance().getAppCtxt().getShell(), null, [DwtDialog.YES_BUTTON, DwtDialog.NO_BUTTON],null,ZaId.CTR_PREFIX + ZaId.VIEW_DMLIST + "_forceApplyConfirm");
    this._forceApplyMessageDialog.registerCallback(DwtDialog.YES_BUTTON, ZaTaskAutoProvDialog.prototype._forceApplyCallback, this);

	this._localXForm.addListener(DwtEvent.XFORMS_FORM_DIRTY_CHANGE, new AjxListener(this, ZaTaskAutoProvDialog.prototype.handleXFormChange));
	this._localXForm.addListener(DwtEvent.XFORMS_VALUE_ERROR, new AjxListener(this, ZaTaskAutoProvDialog.prototype.handleXFormChange));
}

ZaTaskAutoProvDialog.prototype = new ZaXDialog;
ZaTaskAutoProvDialog.prototype.constructor = ZaTaskAutoProvDialog;
ZaTaskAutoProvDialog.prototype.supportMinimize = true;
ZaTaskAutoProvDialog.helpURL = location.pathname + ZaUtil.HELP_URL + "managing_domain/autoprov_manual_config.htm?locid="+AjxEnv.DEFAULT_LOCALE;

ZaTaskAutoProvDialog.APPLY_BUTTON = ++DwtDialog.LAST_BUTTON;

ZaTaskAutoProvDialog.prototype.getCacheName = function(){
    return "ZaTaskAutoProvDialog";
}

ZaTaskAutoProvDialog.prototype.setObject = function(entry) {
    this._containedObject = new ZaDomain();
    ZaItem.prototype.copyTo.call(entry,this._containedObject,true,4);

    this._containedObject[ZaDomain.A2_zmailAutoProvSearchActivated] = entry[ZaDomain.A2_zmailAutoProvSearchActivated] || "TRUE";
    if(entry.attrs[ZaDomain.A_zmailAutoProvAttrMap] && (typeof entry.attrs[ZaDomain.A_zmailAutoProvAttrMap] == "string"))
         this._containedObject.attrs[ZaDomain.A_zmailAutoProvAttrMap] = [entry.attrs[ZaDomain.A_zmailAutoProvAttrMap]];

    // auto provisioning object backup
    this._backupLdapObj(this._containedObject);
    this._containedObject[ZaDomain.A2_zmailAutoProvAccountPool] = entry[ZaDomain.A2_zmailAutoProvAccountPool] || [];
    this._containedObject[ZaDomain.A2_zmailAutoProvAccountTargetPool] = entry[ZaDomain.A2_zmailAutoProvAccountTargetPool] || [];

    this._separateConfigureValues(this._containedObject);
    //ZaXDialog.prototype.setObject.call(this,entry);
    this._containedObject._uuid = entry._extid || entry._uuid;
    this._containedObject._editObject = entry._editObject;

    this._localXForm.setInstance(this._containedObject);


    this._button[DwtDialog.OK_BUTTON].setEnabled(false);
    this._button[ZaTaskAutoProvDialog.APPLY_BUTTON].setEnabled(false);
}

ZaTaskAutoProvDialog.prototype.finishWizard =
function(ev) {
	try {
        if(!this._checkGeneralConfig() || !this._checkEagerConfig()
                || !this._checkLazyConfig()) {
            return;
        }
        this._combineConfigureValues(this._containedObject);
		ZaDomain.modifyAutoPovSettings.call(this._containedObject._editObject,this._containedObject);
		ZaApp.getInstance().getDomainListController()._fireDomainChangeEvent(this._containedObject._editObject);
		this.popdown();
		ZaApp.getInstance().getDomainListController()._notifyAllOpenTabs();
	} catch (ex) {
		this._handleException(ex, "ZaDomainListController.prototype._finishAutoProvButtonListener", null, false);
	}
	return;
}

ZaTaskAutoProvDialog.prototype.handleXFormChange =
function() {
    if(this._localXForm.hasErrors()) {
        this._button[DwtDialog.OK_BUTTON].setEnabled(false);
        this._button[ZaTaskAutoProvDialog.APPLY_BUTTON].setEnabled(false);
    } else {
        this._button[DwtDialog.OK_BUTTON].setEnabled(true);
        this._button[ZaTaskAutoProvDialog.APPLY_BUTTON].setEnabled(true);
    }

    // check modification
    this.tabClickHandler();
}

ZaTaskAutoProvDialog.prototype.getMyXForm =
function() {
    this.tabChoices = new Array();
    this.TAB_INDEX = 0;
	var _tab1, _tab2, _tab3, _tab4, _tab5;

	_tab1 = ++this.TAB_INDEX;
	this.tabChoices.push({value:_tab1, label:ZaMsg.TBB_AUTOPROV_GENERAL});

    _tab2 = ++this.TAB_INDEX;
    this.tabChoices.push({value:_tab2, label:ZaMsg.TBB_AUTOPROV_EAGER});

    _tab3 = ++this.TAB_INDEX;
    this.tabChoices.push({value:_tab3, label:ZaMsg.TBB_AUTOPROV_LAZY});

    this.TAB_STEP_MANUAL = _tab4 = ++this.TAB_INDEX;
    this.tabChoices.push({value:_tab4, label:ZaMsg.TBB_AUTOPROV_MANUAL});

    _tab5 = ++this.TAB_INDEX;
    this.tabChoices.push({value:_tab5, label:ZaMsg.TBB_zmailAutoProvEmailSetting});

	var cases = [];
    var case1={type:_ZATABCASE_, numCols:2,colSizes:["150px","490px"], caseKey:_tab1, id:"auto_provision_config_general",
        getCustomWidth:ZaTaskAutoProvDialog.getCustomWidth,
        getCustomHeight:ZaTaskAutoProvDialog.getCustomHeight,
        items: [
            {type: _SPACER_, height: 10 },
            {type:_GROUPER_, colSpan:"*", width: "100%",label:"LDAP Configuration", containerCssStyle: "padding-top:5px",
                enableDisableChecks:[],
                enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvModeEAGEREnabled, ZaDomain.A2_zmailAutoProvModeLAZYEnabled,
                ZaDomain.A2_zmailAutoProvModeMANUALEnabled],
            items: [
            {type:_GROUP_, numCols:6, label:"   ", labelLocation:_LEFT_,
                visibilityChecks: [],
                visibilityChangeEventSources:[],
                items: [
                    {type:_OUTPUT_, label:null, labelLocation:_NONE_, value:" ", width:"35px"},
                    {type:_OUTPUT_, label:null, labelLocation:_NONE_, value:ZaMsg.Domain_AuthLDAPServerName, width:"200px"},
                    {type:_OUTPUT_, label:null, labelLocation:_NONE_, value:" ", width:"5px"},
                    {type:_OUTPUT_, label:null, labelLocation:_NONE_, value:ZaMsg.Domain_AuthLDAPServerPort,  width:"40px"},
                    {type:_OUTPUT_, label:null, labelLocation:_NONE_, value:ZaMsg.Domain_AuthLDAPUseSSL, width:"80px"}
                ]
            },


            {ref:ZaDomain.A_zmailAutoProvLdapURL, type:_LDAPURL_, label:ZaMsg.LBL_zmailAutoProvLdapURL,
                ldapSSLPort:"636",ldapPort:"389",
                labelLocation:_LEFT_,
                label: ZaMsg.LBL_zmailAutoProvLdapURL
            },
            {ref:ZaDomain.A_zmailAutoProvLdapStartTlsEnabled, type:_CHECKBOX_,
                label:ZaMsg.LBL_zmailAutoProvLdapStartTlsEnabled, subLabel:"", align:_RIGHT_,
                trueValue:"TRUE", falseValue:"FALSE",labelLocation:_RIGHT_
            },
            {ref:ZaDomain.A_zmailAutoProvLdapAdminBindDn, type:_INPUT_,
                label:ZaMsg.LBL_zmailAutoProvLdapAdminBindDn, labelLocation:_LEFT_,
                enableDisableChecks:[],
                enableDisableChangeEventSources:[]
            },
            {ref:ZaDomain.A_zmailAutoProvLdapAdminBindPassword, type:_SECRET_,
                label:ZaMsg.LBL_zmailAutoProvLdapAdminBindPassword, labelLocation:_LEFT_
            },
            {ref:ZaDomain.A_zmailAutoProvLdapSearchFilter, type:_TEXTAREA_, width:350, height:40,
                label:ZaMsg.LBL_zmailAutoProvLdapSearchFilter, labelLocation:_LEFT_,
                textWrapping:"soft"
            },
            {ref:ZaDomain.A_zmailAutoProvLdapSearchBase, type:_TEXTAREA_, width:350, height:40,
                label:ZaMsg.LBL_zmailAutoProvLdapSearchBase, labelLocation:_LEFT_,
                textWrapping:"soft"
            },
            {ref:ZaDomain.A_zmailAutoProvLdapBindDn, type:_INPUT_,
                label:ZaMsg.LBL_zmailAutoProvLdapBindDn, labelLocation:_LEFT_
            }
            ]},
            {type: _SPACER_, height: 10 },
            {ref:ZaDomain.A_zmailAutoProvNotificationFromAddress, type:_TEXTFIELD_,
                label:ZaMsg.LBL_zmailAutoProvNotificationFromAddress, labelLocation:_LEFT_,
                width:250, onChange:ZaDomainXFormView.onFormFieldChanged
            },
            {ref:ZaDomain.A_zmailAutoProvAccountNameMap, type:_TEXTFIELD_,
                label:ZaMsg.LBL_zmailAutoProvAccountNameMap, labelLocation:_LEFT_,
                width:250, onChange:ZaDomainXFormView.onFormFieldChanged
            },
            {ref:ZaDomain.A_zmailAutoProvAttrMap, type:_REPEAT_,
                label:ZaMsg.LBL_zmailAutoProvAttrMap, repeatInstance:"", showAddButton:true,
                showRemoveButton:true,
                    addButtonLabel:ZaMsg.NAD_Add,
                    showAddOnNextRow:true,
                    removeButtonLabel:ZaMsg.NAD_Remove,
                    items: [
                        {ref:".", type:_TEXTFIELD_, label:null,
                        enableDisableChecks:[], visibilityChecks:[],
                        onChange:ZaDomainXFormView.onFormFieldChanged}
                    ]
            }

        ]
    };
    cases.push(case1);

    var case2={type:_ZATABCASE_, numCols:2,colSizes:["45px","*"], caseKey:_tab2,  //cssStyle:"width:550px;",//width: "650px",
        id:"auto_provision_config_eager", getCustomWidth:ZaTaskAutoProvDialog.getCustomWidth,
        getCustomHeight:ZaTaskAutoProvDialog.getCustomHeight,
        items: [
            {type: _SPACER_, height: 20 },
            {ref:ZaDomain.A2_zmailAutoProvModeEAGEREnabled, type:_CHECKBOX_,
                label:ZaMsg.LBL_zmailAutoProvModeEAGER, subLabel:"", align:_RIGHT_,
                trueValue:"TRUE", falseValue:"FALSE",labelLocation:_RIGHT_
            },
            {type: _SPACER_, height: 20 },
            {type:_GROUPER_, colSpan:"*", width: "100%",label:"Configuration", containerCssStyle: "padding-top:5px", colSizes:["175px","*"], numCols:2,
                enableDisableChecks:[[XForm.checkInstanceValue,ZaDomain.A2_zmailAutoProvModeEAGEREnabled,"TRUE"]],
                enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvModeEAGEREnabled],
                items: [
                {ref:ZaDomain.A_zmailAutoProvBatchSize, type:_TEXTFIELD_, label:ZaMsg.LBL_zmailAutoProvBatchSize,
                    autoSaveValue:true, labelLocation:_LEFT_,
                    enableDisableChecks: [[XForm.checkInstanceValue,ZaDomain.A2_zmailAutoProvModeEAGEREnabled,"TRUE"]],
                    enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvModeEAGEREnabled],
                    cssClass:"admin_xform_number_input"
                },
                {ref:ZaDomain.A2_zmailAutoProvPollingInterval, type:_LIFETIME_,
                    colSizes:["80px","100px","*"],
                    label:ZaMsg.LBL_zmailAutoProvPollingInterval, labelLocation:_LEFT_,
                    enableDisableChecks: [[XForm.checkInstanceValue,ZaDomain.A2_zmailAutoProvModeEAGEREnabled,"TRUE"]],
                    enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvModeEAGEREnabled]
                },
                {type: _DWT_LIST_, ref: ZaDomain.A2_zmailAutoProvServerList,  width: 250, height: 50,
                    label:ZaMsg.LBL_zmailAutoProvServerList,
                    labelLocation:_LEFT_,   labelCssStyle:"vertical-align:top",
                    nowrap:false,labelWrap:true,
                    forceUpdate: true, widgetClass: ZaServerOptionList,
                    multiselect: true, preserveSelection: true,
                    enableDisableChecks: [[XForm.checkInstanceValue,ZaDomain.A2_zmailAutoProvModeEAGEREnabled,"TRUE"]],
                    enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvModeEAGEREnabled],
                    onSelection: ZaTaskAutoProvDialog.filterSelectionListener
                }
            ]},
            {type:_GROUPER_, colSpan:"*", width: "100%",label:"Note",
                containerCssStyle: "padding-top:15px", numCols:1,
                items: [
                    {type:_OUTPUT_,value:ZaMsg.MSG_AUTOPROV_DLG_EAGER}
            ]}
        ]
    };
    cases.push(case2);

    var case3={type:_ZATABCASE_, numCols:2,colSizes:["45px","*"],  caseKey:_tab3,
        id:"auto_provision_config_lazy", getCustomWidth:ZaTaskAutoProvDialog.getCustomWidth,
        getCustomHeight:ZaTaskAutoProvDialog.getCustomHeight,
        items: [
            {type: _SPACER_, height: 20 },
            {ref:ZaDomain.A2_zmailAutoProvModeLAZYEnabled, type:_CHECKBOX_,
                label:ZaMsg.LBL_zmailAutoProvModeLAZY, subLabel:"", align:_RIGHT_,
                trueValue:"TRUE", falseValue:"FALSE",labelLocation:_RIGHT_
            },
            {type: _SPACER_, height: 20 },
            {type:_GROUPER_, colSpan:"*", width: "100%",label:"Configuration", containerCssStyle: "padding-top:5px", colSizes:["200px","*"], numCols:2,
            items: [
                {type:_GROUP_, numCols:2, label:ZaMsg.LBL_zmailAutoProvAuthMech,
                    labelLocation:_LEFT_, colSizes:["20px","150px"],labelCssStyle:"vertical-align:top",
                    nowrap:false,labelWrap:true,
                    enableDisableChecks:[[XForm.checkInstanceValue,ZaDomain.A2_zmailAutoProvModeLAZYEnabled,"TRUE"]],
                    enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvModeLAZYEnabled],
                    items: [
                        {ref:ZaDomain.A2_zmailAutoProvAuthMechLDAPEnabled, type:_CHECKBOX_,
                            label:ZaMsg.LBL_zmailAutoProvAuthMechLDAP, subLabel:"",
                            trueValue:"TRUE", falseValue:"FALSE",labelLocation:_RIGHT_
                        },
                        {ref:ZaDomain.A2_zmailAutoProvAuthMechPREAUTHEnabled, type:_CHECKBOX_,
                            label:ZaMsg.LBL_zmailAutoProvAuthMechPREAUTH, subLabel:"",
                            trueValue:"TRUE", falseValue:"FALSE",labelLocation:_RIGHT_
                        },
                        {ref:ZaDomain.A2_zmailAutoProvAuthMechKRB5Enabled, type:_CHECKBOX_,
                            label:ZaMsg.LBL_zmailAutoProvAuthMechKRB5, subLabel:"",
                            trueValue:"TRUE", falseValue:"FALSE",labelLocation:_RIGHT_
                        },
                        {ref:ZaDomain.A2_zmailAutoProvAuthMechSPNEGOEnabled, type:_CHECKBOX_,
                            label:ZaMsg.LBL_zmailAutoProvAuthMechSPNEGO, subLabel:"",
                            trueValue:"TRUE", falseValue:"FALSE",labelLocation:_RIGHT_
                        }
                    ]
                }
            ]},
            {type:_GROUPER_, colSpan:"*", width: "100%",label:"Note",
                containerCssStyle: "padding-top:15px", numCols:1,
                items: [
                    {type:_OUTPUT_,value:ZaMsg.MSG_AUTOPROV_DLG_LAZY}
            ]}
        ]
    };
    cases.push(case3);

    var case4={type:_ZATABCASE_, numCols:2,colSizes:["45px","*"],  caseKey:_tab4,
        id:"auto_provision_config_lazy", getCustomWidth:ZaTaskAutoProvDialog.getCustomWidth,
        getCustomHeight:ZaTaskAutoProvDialog.getCustomHeight,
        items: [
            {type: _SPACER_, height: 20 },
            {ref:ZaDomain.A2_zmailAutoProvModeMANUALEnabled, type:_CHECKBOX_,
                label:ZaMsg.LBL_zmailAutoProvModeMANUAL, subLabel:"", align:_RIGHT_,
                trueValue:"TRUE", falseValue:"FALSE",labelLocation:_RIGHT_,
                onChange: ZaTaskAutoProvDialog.onFormFieldChanged
            },
            {type: _SPACER_, height: 20 },
            {type:_GROUPER_, colSpan:"*", width: "100%",label:"Find & Provisioning",
            enableDisableChecks:[[XForm.checkInstanceValue,ZaDomain.A2_zmailAutoProvModeMANUALEnabled,"TRUE"]],
            enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvModeMANUALEnabled],
            containerCssStyle: "padding-top:5px",
            items: [
                    {type:_GROUP_, colSpan:2, numCols:3, width:"100%", colSizes:["180px","85px","180px"], cellspacing:"5px",
                        items:[
                            {type:_TEXTFIELD_, cssClass:"admin_xform_name_input",width:"185px", ref:ZaSearch.A_query, label:null,
                                elementChanged: function(elementValue,instanceValue, event) {
                                  var charCode = event.charCode;
                                  if (charCode == 13 || charCode == 3) {
                                      ZaTaskAutoProvDialog.srchButtonHndlr.call(this);
                                  } else {
                                      this.getForm().itemChanged(this, elementValue, event);
                                  }
                                },
                                visibilityChecks:[],enableDisableChecks:[]
                            },
                            {type:_DWT_BUTTON_, label:ZaMsg.DLXV_ButtonSearch, width:"80px",
                                onActivate:ZaTaskAutoProvDialog.srchButtonHndlr,align:_CENTER_,
                                enableDisableChecks:[[XForm.checkInstanceValue,ZaDomain.A2_zmailAutoProvSearchActivated,"TRUE"]],
                                enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvSearchActivated]
                            },
                            {type:_OUTPUT_, value:ZaMsg.LBL_ManualProvAccount,visibilityChecks:[]},
                            {ref:ZaDomain.A2_zmailAutoProvAccountPool, type:_DWT_LIST_, height:"180px", width:"180px",
                                cssClass: "DLSource",
                                widgetClass:ZaAccMiniListView,
                                rowSpan:4,
                                onSelection:ZaTaskAutoProvDialog.accPoolSelectionListener,
                                visibilityChecks:[],enableDisableChecks:[]
                            },
                            {type:_DWT_BUTTON_, label:AjxMsg.addAll, width:"80px",
                                onActivate:ZaTaskAutoProvDialog.addAllButtonHndlr,
                                enableDisableChecks:[[XForm.checkInstanceValueNotEmty,ZaDomain.A2_zmailAutoProvAccountPool]],
                                enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvAccountPool]
                            },
                            {ref: ZaDomain.A2_zmailAutoProvAccountTargetPool, type:_DWT_LIST_, height:"180px", width:"180px",
                                cssClass: "DLSource",
                                widgetClass:ZaAccMiniListView,
                                rowSpan:4,
                                onSelection:ZaTaskAutoProvDialog.accTargetSelectionListener,
                                visibilityChecks:[],enableDisableChecks:[]
                            },
                            {type:_DWT_BUTTON_, label:AjxMsg.add, width:"80px",
                               onActivate:ZaTaskAutoProvDialog.addButtonHndlr,
                               enableDisableChecks:[[XForm.checkInstanceValueNotEmty,ZaDomain.A2_zmailAutoProvAccountSrcSelectedPool]],
                               enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvAccountSrcSelectedPool]
                            },
                            {type:_DWT_BUTTON_, label:AjxMsg.remove, width:"80px",
                               onActivate:ZaTaskAutoProvDialog.removeButtonHndlr,
                               enableDisableChecks:[[XForm.checkInstanceValueNotEmty,ZaDomain.A2_zmailAutoProvAccountTgtSelectedPool]],
                               enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvAccountTgtSelectedPool]
                            },
                            {type:_DWT_BUTTON_, label:AjxMsg.removeAll, width:"80px",
                                onActivate:ZaTaskAutoProvDialog.removeAllButtonHndlr,
                                enableDisableChecks:[[XForm.checkInstanceValueNotEmty,ZaDomain.A2_zmailAutoProvAccountTargetPool]],
                                enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvAccountTargetPool]
                            },
                            {type:_GROUP_,numCols:3,colSizes:["90px","*","90px"],
                                items:[
                                    {type:_SPACER_, colSpan:3},
                                    {type:_DWT_BUTTON_, label:ZaMsg.Previous, width:75,
                                       id:"backButton", icon:"LeftArrow", disIcon:"LeftArrowDis",
                                       onActivate:ZaTaskAutoProvDialog.backPoolButtonHndlr,align:_CENTER_,
                                       enableDisableChecks:[ZaTaskAutoProvDialog.backBtnEnabled],
                                       enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvAccountPoolPageNum,ZaDomain.A2_zmailAutoProvSearchActivated]
                                    },
                                    {type:_CELLSPACER_},
                                    {type:_DWT_BUTTON_, label:ZaMsg.Next, width:75,
                                       id:"fwdButton", icon:"RightArrow", disIcon:"RightArrowDis",
                                       onActivate:ZaTaskAutoProvDialog.fwdPoolButtonHndlr,align:_CENTER_,
                                       enableDisableChecks:[ZaTaskAutoProvDialog .forwardBtnEnabled],
                                       enableDisableChangeEventSources:[ZaDomain.A2_zmailAutoProvAccountPoolPageNum,ZaDomain.A2_zmailAutoProvSearchActivated]
                                    }
                                ]
                            },
                            {type:_CELLSPACER_}
                        ]
                    }
            ]}
        ]
    };
    cases.push(case4);

    var case5={type:_ZATABCASE_, numCols:1,width:"98%",  caseKey:_tab5,
        id:"auto_provision_email_setting", getCustomWidth:ZaTaskAutoProvDialog.getCustomWidth,
        getCustomHeight:ZaTaskAutoProvDialog.getCustomHeight,
        items: [
            {type: _SPACER_, height: 20 },
            {type:_GROUPER_, colSpan:"*", width: "100%",label:ZaMsg.LBL_zmailAutoProvConfiguration, containerCssStyle: "padding-top:5px", colSizes:["100px","auto"], numCols:2,
            items: [
                {ref:ZaDomain.A_zmailAutoProvNotificationSubject, type:_SUPER_TEXTFIELD_, colSpan:2, label:ZaMsg.LBL_zmailAutoProvEmailSubject,
                    labelLocation:_LEFT_, textFieldCssStyle:"width:300; margin-right:5",
                    onChange:ZaTaskAutoProvDialog.onFormFieldChanged,
                    resetToSuperLabel:ZaMsg.NAD_ResetToGlobal},
                {ref:ZaDomain.A_zmailAutoProvNotificationBody, type:_SUPER_TEXTAREA_, colSpan:2, label:ZaMsg.LBL_zmailAutoProvEmailBody,
                    labelLocation:_LEFT_, textAreaCssStyle:"width:300; margin-right:5",
                    onChange:ZaTaskAutoProvDialog.onFormFieldChanged,
                    resetToSuperLabel:ZaMsg.NAD_ResetToGlobal}
            ]}
        ]
    };
    cases.push(case5);

	var xFormObject = {
		numCols:1,
		items:[
            {type:_TAB_BAR_,  ref:ZaModel.currentTab,choices:this.tabChoices,cssClass:"ZaTabBar", id:"xform_tabbar"},
            {type:_SWITCH_, align:_LEFT_, valign:_TOP_, items:cases}
        ]
    };
    return xFormObject;
}

ZaTaskAutoProvDialog.onFormFieldChanged =
function (value, event, form) {
    var ref = this.getRefPath();
    if (ref == ZaDomain.A_zmailAutoProvNotificationSubject || ref == ZaDomain.A_zmailAutoProvNotificationBody) {
        this.setInstanceValue(value);
        return;
    }
    var instance = this.getInstance();
    instance[ZaDomain.A2_zmailAutoProvSearchActivated] = "TRUE";
    this.setInstanceValue(value);
    return value;
}

ZaTaskAutoProvDialog.prototype._forceApplyCallback =
function() {
    this._applyButtonListener();
}

ZaTaskAutoProvDialog.prototype._confirmPasswordSettingCallback =
function() {
    if(this._confirmPasswordSettingDialog)
        this._confirmPasswordSettingDialog.popdown();
    var obj = this.getObject();
    if(obj[ZaDomain.A2_zmailAutoProvAccountPasswordInDlg])
        obj[ZaDomain.A2_zmailAutoProvAccountPassword] = obj[ZaDomain.A2_zmailAutoProvAccountPasswordInDlg]
}

ZaTaskAutoProvDialog.getCustomWidth = function() {
	return "100%";
}

ZaTaskAutoProvDialog.getCustomHeight = function() {
	return "100%";
}

ZaTaskAutoProvDialog.prototype._applyButtonListener =
function() {
    if(this._forceApplyMessageDialog)
        this._forceApplyMessageDialog.popdown();
    try {
        var controller = ZaApp.getInstance().getCurrentController();
        if(this._checkGeneralConfig() && this._checkEagerConfig() && this._checkLazyConfig()) {
            var savedObj = this.getObject();
            this._combineConfigureValues(savedObj);
            ZaDomain.modifyAutoPovSettings.call(this._containedObject,savedObj);
            controller._notifyAllOpenTabs();
            if(savedObj.currentTab == 4) {
                if(this._checkManualConfig())
                    this.finishConfig();
                else return;
            }
            this._button[DwtDialog.OK_BUTTON].setEnabled(false);
            this._button[ZaTaskAutoProvDialog.APPLY_BUTTON].setEnabled(false);
            this._backupLdapObj(savedObj);
        }
    } catch (ex) {
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaTaskAutoProvDialog.prototype._applyButtonListener", null, false);
	}
}

ZaTaskAutoProvDialog.prototype._checkGeneralConfig =
function() {
    var isError = false;
    var errorMsg = "";
    if(!this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapURL]
            || this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapURL] == "") {
        isError = true;
        errorMsg = AjxMessageFormat.format(ZaMsg.ERROR_AUTOPROV,ZaMsg.MSG_zmailAutoProvLdapURL);
    } else if(!this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindDn]
            || this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindDn] == "") {
        isError = true;
        errorMsg = AjxMessageFormat.format(ZaMsg.ERROR_AUTOPROV,ZaMsg.MSG_zmailAutoProvLdapAdminBindDn);
    } else if(!this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindPassword]
            || this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindPassword] == "") {
        isError = true;
        errorMsg = AjxMessageFormat.format(ZaMsg.ERROR_AUTOPROV,ZaMsg.MSG_zmailAutoProvLdapAdminBindPassword);
    } else if(!this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapSearchBase]
            || this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapSearchBase] == "") {
        isError = true;
        errorMsg = AjxMessageFormat.format(ZaMsg.ERROR_AUTOPROV,ZaMsg.MSG_zmailAutoProvLdapSearchBase);
    }
    if(!isError && this._containedObject[ZaDomain.A2_zmailAutoProvModeEAGEREnabled] == "TRUE") {
        if(!this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapSearchFilter]
                || this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapSearchFilter] == "") {
            isError = true;
            errorMsg = AjxMessageFormat.format(ZaMsg.ERROR_AUTOPROV,ZaMsg.MSG_zmailAutoProvLdapSearchFilter);
        } else if(!this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapBindDn]
                || this._containedObject.attrs[ZaDomain.A_zmailAutoProvLdapBindDn] == "") {
            isError = true;
            errorMsg = AjxMessageFormat.format(ZaMsg.ERROR_AUTOPROV,ZaMsg.MSG_zmailAutoProvLdapBindDn);
        }
    }
    if(isError) {
        ZaApp.getInstance().getCurrentController().popupErrorDialog(errorMsg);
        return false;
    } else return true;
}

ZaTaskAutoProvDialog.prototype._checkEagerConfig =
function() {
    var isError = false;
    var errorMsg = "";
    if(!isError && this._containedObject[ZaDomain.A2_zmailAutoProvModeEAGEREnabled] == "TRUE") {
        if(!this._containedObject.attrs[ZaDomain.A_zmailAutoProvBatchSize]
                || this._containedObject.attrs[ZaDomain.A_zmailAutoProvBatchSize] == "") {
            isError = true;
            errorMsg = AjxMessageFormat.format(ZaMsg.ERROR_AUTOPROV,ZaMsg.MSG_zmailAutoProvBatchSize);
        }
    }
    if(isError) {
        ZaApp.getInstance().getCurrentController().popupErrorDialog(errorMsg);
        return false;
    } else return true;
}

ZaTaskAutoProvDialog.prototype._checkLazyConfig =
function() {
        if(this._containedObject[ZaDomain.A2_zmailAutoProvModeLAZYEnabled] == "TRUE"
                && (!this._containedObject[ZaDomain.A2_zmailAutoProvAuthMechLDAPEnabled]
                    || this._containedObject[ZaDomain.A2_zmailAutoProvAuthMechLDAPEnabled] == "FALSE")
                && (!this._containedObject[ZaDomain.A2_zmailAutoProvAuthMechPREAUTHEnabled]
                    || this._containedObject[ZaDomain.A2_zmailAutoProvAuthMechPREAUTHEnabled] == "FALSE")
                && (!this._containedObject[ZaDomain.A2_zmailAutoProvAuthMechKRB5Enabled]
                    || this._containedObject[ZaDomain.A2_zmailAutoProvAuthMechKRB5Enabled] == "FALSE")
                && (!this._containedObject[ZaDomain.A2_zmailAutoProvAuthMechSPNEGOEnabled]
                    || this._containedObject[ZaDomain.A2_zmailAutoProvAuthMechSPNEGOEnabled] == "FALSE")) {
            ZaApp.getInstance().getCurrentController().popupErrorDialog(ZaMsg.ERROR_AUTOPROV_LAZYAUTH);
            return false;
        } else return true;
}

ZaTaskAutoProvDialog.prototype._checkManualConfig =
function() {
    var attrMaps =  this._containedObject.attrs[ZaDomain.A_zmailAutoProvAttrMap];
    var obj = this.getObject();
    var isGiven = false;
    if(attrMaps) {
        if(!(attrMaps instanceof Array))
            attrMaps = [attrMaps];
        for(var i = 0; i < attrMaps.length && !isGiven; i ++ ) {
            var kv = attrMaps[i].split("=");
            if(kv.length > 0 && kv[0].indexOf("userPassword") == 0)
                isGiven = true;
        }
    }
    if(obj[ZaDomain.A2_zmailAutoProvAccountPassword])
        return true;
    else if(!isGiven) {
        if(!this._confirmPasswordSettingDialog) {
            var height = "220px"
            if (AjxEnv.isIE) {
                height = "245px";
            }
            this._confirmPasswordSettingDialog = new ZaConfirmPasswordDialog(ZaApp.getInstance().getAppCtxt().getShell(), "450px", height, ZaMsg.DLG_TITILE_MANUAL_PROV);
        }
        this._confirmPasswordSettingDialog.registerCallback(DwtDialog.OK_BUTTON, ZaTaskAutoProvDialog.prototype._confirmPasswordSettingCallback, this, null);
		this._confirmPasswordSettingDialog.setObject(this._containedObject);
		this._confirmPasswordSettingDialog.popup();
    }
    return isGiven;
}

ZaTaskAutoProvDialog.prototype._separateConfigureValues =
function(entry) {
    if(entry.attrs[ZaDomain.A_zmailAutoProvMode]) {
        if(entry.attrs[ZaDomain.A_zmailAutoProvMode] instanceof Array) {
            for(var mode = 0; mode < entry.attrs[ZaDomain.A_zmailAutoProvMode].length; mode ++){
                if(entry.attrs[ZaDomain.A_zmailAutoProvMode][mode] == "EAGER")
                   entry[ZaDomain.A2_zmailAutoProvModeEAGEREnabled] = "TRUE";
                else if(entry.attrs[ZaDomain.A_zmailAutoProvMode][mode] == "LAZY")
                   entry[ZaDomain.A2_zmailAutoProvModeLAZYEnabled] = "TRUE";
                else if(entry.attrs[ZaDomain.A_zmailAutoProvMode][mode] == "MANUAL")
                   entry[ZaDomain.A2_zmailAutoProvModeMANUALEnabled] = "TRUE";
            }
        } else {
            if(entry.attrs[ZaDomain.A_zmailAutoProvMode] == "EAGER")
               entry[ZaDomain.A2_zmailAutoProvModeEAGEREnabled] = "TRUE";
            else if(entry.attrs[ZaDomain.A_zmailAutoProvMode] == "LAZY")
               entry[ZaDomain.A2_zmailAutoProvModeLAZYEnabled] = "TRUE";
            else if(entry.attrs[ZaDomain.A_zmailAutoProvMode] == "MANUAL")
               entry[ZaDomain.A2_zmailAutoProvModeMANUALEnabled] = "TRUE";
        }
    }

    if(entry.attrs[ZaDomain.A_zmailAutoProvAuthMech]) {
        if(entry.attrs[ZaDomain.A_zmailAutoProvAuthMech] instanceof Array) {
            for(var mode = 0; mode < entry.attrs[ZaDomain.A_zmailAutoProvAuthMech].length; mode ++){
                if(entry.attrs[ZaDomain.A_zmailAutoProvAuthMech][mode] == "LDAP")
                   entry[ZaDomain.A2_zmailAutoProvAuthMechLDAPEnabled] = "TRUE";
                else if(entry.attrs[ZaDomain.A_zmailAutoProvAuthMech][mode] == "PREAUTH")
                   entry[ZaDomain.A2_zmailAutoProvAuthMechPREAUTHEnabled] = "TRUE";
                else if(entry.attrs[ZaDomain.A_zmailAutoProvAuthMech][mode] == "KRB5")
                   entry[ZaDomain.A2_zmailAutoProvAuthMechKRB5Enabled] = "TRUE";
                else if(entry.attrs[ZaDomain.A_zmailAutoProvAuthMech][mode] == "SPNEGO")
                   entry[ZaDomain.A2_zmailAutoProvAuthMechSPNEGOEnabled] = "TRUE";
            }
        } else {
                if(entry.attrs[ZaDomain.A_zmailAutoProvAuthMech] == "LDAP")
                   entry[ZaDomain.A2_zmailAutoProvAuthMechLDAPEnabled] = "TRUE";
                else if(entry.attrs[ZaDomain.A_zmailAutoProvAuthMech] == "PREAUTH")
                   entry[ZaDomain.A2_zmailAutoProvAuthMechPREAUTHEnabled] = "TRUE";
                else if(entry.attrs[ZaDomain.A_zmailAutoProvAuthMech] == "KRB5")
                   entry[ZaDomain.A2_zmailAutoProvAuthMechKRB5Enabled] = "TRUE";
                else if(entry.attrs[ZaDomain.A_zmailAutoProvAuthMech] == "SPNEGO")
                   entry[ZaDomain.A2_zmailAutoProvAuthMechSPNEGOEnabled] = "TRUE";
        }
    }
    entry[ZaDomain.A2_zmailAutoProvServerList] = ZaApp.getInstance().getServerList(true).getArray();
    entry[ZaDomain.A2_zmailAutoProvSelectedServerList] = new AjxVector ();
    for(var i = 0; i < entry[ZaDomain.A2_zmailAutoProvServerList].length; i++) {
        var server = entry[ZaDomain.A2_zmailAutoProvServerList][i];
        var scheduledDomains = server.attrs[ZaServer.A_zmailAutoProvScheduledDomains];
        for(var j = 0; scheduledDomains && j < scheduledDomains.length; j++) {
            if(scheduledDomains[j] == entry.name) {
               entry[ZaDomain.A2_zmailAutoProvSelectedServerList].add(server.name);
                server["checked"] = true;

                if(server.attrs[ZaServer.A_zmailAutoProvPollingInterval])
                    entry[ZaDomain.A2_zmailAutoProvPollingInterval] = server.attrs[ZaServer.A_zmailAutoProvPollingInterval];
            }
        }
    }

}

ZaTaskAutoProvDialog.prototype._combineConfigureValues =
function(entry) {
    entry.attrs[ZaDomain.A_zmailAutoProvMode] = [];
    if(entry[ZaDomain.A2_zmailAutoProvModeEAGEREnabled] == "TRUE")
        entry.attrs[ZaDomain.A_zmailAutoProvMode].push("EAGER");
    if(entry[ZaDomain.A2_zmailAutoProvModeLAZYEnabled] == "TRUE")
        entry.attrs[ZaDomain.A_zmailAutoProvMode].push("LAZY");
    if(entry[ZaDomain.A2_zmailAutoProvModeMANUALEnabled] == "TRUE")
        entry.attrs[ZaDomain.A_zmailAutoProvMode].push("MANUAL");

    entry.attrs[ZaDomain.A_zmailAutoProvAuthMech] = [];
    if(entry[ZaDomain.A2_zmailAutoProvAuthMechLDAPEnabled] == "TRUE")
        entry.attrs[ZaDomain.A_zmailAutoProvAuthMech].push("LDAP");
    if(entry[ZaDomain.A2_zmailAutoProvAuthMechPREAUTHEnabled] == "TRUE")
        entry.attrs[ZaDomain.A_zmailAutoProvAuthMech].push("PREAUTH");
    if(entry[ZaDomain.A2_zmailAutoProvAuthMechKRB5Enabled] == "TRUE")
        entry.attrs[ZaDomain.A_zmailAutoProvAuthMech].push("KRB5");
    if(entry[ZaDomain.A2_zmailAutoProvAuthMechSPNEGOEnabled] == "TRUE")
        entry.attrs[ZaDomain.A_zmailAutoProvAuthMech].push("SPNEGO");
}

ZaTaskAutoProvDialog.prototype._backupLdapObj = function(entry) {
    if(!this._autoprovLdapObject)
        this._autoprovLdapObject = {};
    if(!entry || !entry.attrs) return;
    if(entry.attrs[ZaDomain.A_zmailAutoProvLdapURL])
        this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapURL] = entry.attrs[ZaDomain.A_zmailAutoProvLdapURL];
    else this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapURL] = null;
    if(entry.attrs[ZaDomain.A_zmailAutoProvLdapStartTlsEnabled])
        this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapStartTlsEnabled] = entry.attrs[ZaDomain.A_zmailAutoProvLdapStartTlsEnabled];
    else this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapStartTlsEnabled] = null;
    if(entry.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindDn])
        this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapAdminBindDn] = entry.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindDn];
    else  this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapAdminBindDn] = null;
    if(entry.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindPassword])
        this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapAdminBindPassword] = entry.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindPassword];
    else this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapAdminBindPassword] = null;
    if(entry.attrs[ZaDomain.A_zmailAutoProvLdapSearchBase])
        this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapSearchBase] = entry.attrs[ZaDomain.A_zmailAutoProvLdapSearchBase];
    else this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapSearchBase] = null;
    if(entry.attrs[ZaDomain.A_zmailAutoProvLdapSearchFilter])
        this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapSearchFilter] = entry.attrs[ZaDomain.A_zmailAutoProvLdapSearchFilter];
    else this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapSearchFilter] = null;
    if(entry.attrs[ZaDomain.A_zmailAutoProvLdapBindDn])
        this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapBindDn] = entry.attrs[ZaDomain.A_zmailAutoProvLdapBindDn];
    else this._autoprovLdapObject[ZaDomain.A_zmailAutoProvLdapBindDn] = null;
    if(entry.attrs[ZaDomain.A_zmailAutoProvNotificationSubject])
        this._autoprovLdapObject[ZaDomain.A_zmailAutoProvNotificationSubject] = entry.attrs[ZaDomain.A_zmailAutoProvNotificationSubject];
    else this._autoprovLdapObject[ZaDomain.A_zmailAutoProvNotificationSubject] = null;
    if(entry.attrs[ZaDomain.A_zmailAutoProvNotificationBody])
        this._autoprovLdapObject[ZaDomain.A_zmailAutoProvNotificationBody] = entry.attrs[ZaDomain.A_zmailAutoProvNotificationBody];
    else this._autoprovLdapObject[ZaDomain.A_zmailAutoProvNotificationBody] = null;
}

ZaTaskAutoProvDialog.prototype._checkModified = function() {
    var newObj = this.getObject();
    var oldObj = this._autoprovLdapObject;

    if((oldObj[ZaDomain.A_zmailAutoProvLdapURL] == newObj.attrs[ZaDomain.A_zmailAutoProvLdapURL]
            || !oldObj[ZaDomain.A_zmailAutoProvLdapURL] && !newObj.attrs[ZaDomain.A_zmailAutoProvLdapURL])
    && (oldObj[ZaDomain.A_zmailAutoProvLdapStartTlsEnabled] == newObj.attrs[ZaDomain.A_zmailAutoProvLdapStartTlsEnabled]
            || !oldObj[ZaDomain.A_zmailAutoProvLdapStartTlsEnabled] && !newObj.attrs[ZaDomain.A_zmailAutoProvLdapStartTlsEnabled])
    && (oldObj[ZaDomain.A_zmailAutoProvLdapAdminBindDn] == newObj.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindDn]
            || !oldObj[ZaDomain.A_zmailAutoProvLdapAdminBindDn] && !newObj.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindDn])
    && (oldObj[ZaDomain.A_zmailAutoProvLdapAdminBindPassword] == newObj.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindPassword]
            || !oldObj[ZaDomain.A_zmailAutoProvLdapAdminBindPassword] && !newObj.attrs[ZaDomain.A_zmailAutoProvLdapAdminBindPassword])
    && (oldObj[ZaDomain.A_zmailAutoProvLdapSearchBase] == newObj.attrs[ZaDomain.A_zmailAutoProvLdapSearchBase]
            || !oldObj[ZaDomain.A_zmailAutoProvLdapSearchBase] && !newObj.attrs[ZaDomain.A_zmailAutoProvLdapSearchBase])
    && (oldObj[ZaDomain.A_zmailAutoProvLdapSearchFilter] == newObj.attrs[ZaDomain.A_zmailAutoProvLdapSearchFilter]
            || !oldObj[ZaDomain.A_zmailAutoProvLdapSearchFilter] && !newObj.attrs[ZaDomain.A_zmailAutoProvLdapSearchFilter])
    && (oldObj[ZaDomain.A_zmailAutoProvLdapBindDn] == newObj.attrs[ZaDomain.A_zmailAutoProvLdapBindDn]
            || !oldObj[ZaDomain.A_zmailAutoProvLdapBindDn] && !newObj.attrs[ZaDomain.A_zmailAutoProvLdapBindDn])
    && (oldObj[ZaDomain.A_zmailAutoProvNotificationSubject] == newObj.attrs[ZaDomain.A_zmailAutoProvNotificationSubject]
            || !oldObj[ZaDomain.A_zmailAutoProvNotificationSubject] && !newObj.attrs[ZaDomain.A_zmailAutoProvNotificationSubject])
    && (oldObj[ZaDomain.A_zmailAutoProvNotificationBody] == newObj.attrs[ZaDomain.A_zmailAutoProvNotificationBody]
            || !oldObj[ZaDomain.A_zmailAutoProvNotificationBody] && !newObj.attrs[ZaDomain.A_zmailAutoProvNotificationBody]))
        return false;
    else
        return true;
}

ZaTaskAutoProvDialog.prototype.tabClickHandler = function() {
    if(this.getObject().currentTab != this.TAB_STEP_MANUAL)
        return;
    if(this._checkModified()) {
        var dlgMsg = ZaMsg.MSG_LDAP_CHANGED;
        this._forceApplyMessageDialog.setMessage(dlgMsg, DwtMessageDialog.INFO_STYLE);
        this._forceApplyMessageDialog.popup();
    }
}
///////////////////
ZaTaskAutoProvDialog.srchButtonHndlr = function() {
	var instance = this.getForm().getInstance();
	var formParent = this.getForm().parent;
    if(!formParent._checkGeneralConfig())
        return;
    var soapDoc = AjxSoapDoc.create("SearchAutoProvDirectoryRequest", ZaZmailAdmin.URN, null);
    soapDoc.getMethod().setAttribute("keyAttr","name");
	var attr = soapDoc.set("domain", instance.id);
	attr.setAttribute("by", "id");

    var query = "(|(mail=*)(zmailMailAlias=*)(uid=*))";
	if(instance[ZaSearch.A_query]) {
		query = ZaSearch.getSearchByNameQuery (instance[ZaSearch.A_query]);
	}
    soapDoc.set("query", query);
    var limit = ZaSettings.RESULTSPERPAGE;
	if(!instance[ZaDomain.A2_zmailAutoProvAccountPoolPageNum]) {
		instance[ZaDomain.A2_zmailAutoProvAccountPoolPageNum] = 0;
	}
	var offset = instance[ZaDomain.A2_zmailAutoProvAccountPoolPageNum]*ZaSettings.RESULTSPERPAGE;
	var attrs = [ZaAccount.A_name, ZaAccount.A_mail, ZaItem.A_zmailId,ZaAccount.A_displayname].join(",");
    soapDoc.getMethod().setAttribute("keyAttr","name");
	soapDoc.getMethod().setAttribute("offset", offset);
	soapDoc.getMethod().setAttribute("limit", limit);
    soapDoc.getMethod().setAttribute("attrs", attrs);
    soapDoc.getMethod().setAttribute("refresh", "1");

	this.getModel().setInstanceValue(this.getInstance(),ZaDomain.A2_zmailAutoProvSearchActivated,"FALSE");

    var params = {};
    params.soapDoc = soapDoc;
    params.asyncMode = false;
    var reqMgrParams = {
        controller : ZaApp.getInstance().getCurrentController(),
        busyMsg : ZaMsg.BUSY_AUTOPROV_GETACCT
    }

    try {
        var resp = ZaRequestMgr.invoke(params, reqMgrParams);
        this.getModel().setInstanceValue(this.getInstance(),ZaDomain.A2_zmailAutoProvSearchActivated,"TRUE");
        if(!resp || resp.Body.SearchAutoProvDirectoryResponse.Fault)
            return;
        if(!resp.Body.SearchAutoProvDirectoryResponse || !resp.Body.SearchAutoProvDirectoryResponse.entry)
            return;
        var provAcctList = [];
        var objs = resp.Body.SearchAutoProvDirectoryResponse.entry;
        var searchTotal = resp.Body.SearchAutoProvDirectoryResponse.searchTotal;
        for(var i = 0; objs && i < objs.length; i++) {
            var obj = objs[i];
            var acct = new Object();
            acct.dn = obj.dn;
            var len = obj.a.length;
            acct.attrs = new Array();
            for(var ix = 0; ix < len; ix ++) {
                if(!acct.attrs[[obj.a[ix].n]]) {
                    acct.attrs[[obj.a[ix].n]] = obj.a[ix]._content;
                } else {
                    if(!(acct.attrs[[obj.a[ix].n]] instanceof Array)) {
                        acct.attrs[[obj.a[ix].n]] = [acct.attrs[[obj.a[ix].n]]];
                    }
                    acct.attrs[[obj.a[ix].n]].push(obj.a[ix]._content);
                }
            }
            acct.name = acct.attrs[ZaAccount.A_mail];
            provAcctList.push(acct);
        }
        this.getModel().setInstanceValue(this.getInstance(),ZaDomain.A2_zmailAutoProvAccountPool,provAcctList);
        var poolTotalPages = Math.ceil(searchTotal/ZaSettings.RESULTSPERPAGE);
        this.getModel().setInstanceValue(this.getInstance(),ZaDomain.A2_zmailAutoProvAccountPoolPageTotal,poolTotalPages);
    } catch(ex) {
        ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaTaskAutoProvDialog.srchButtonHndlr", null, false);
    }
}

ZaTaskAutoProvDialog.accPoolSelectionListener = function() {
	var arr = this.widget.getSelection();
	if(arr && arr.length) {
		arr.sort();
		this.getModel().setInstanceValue(this.getInstance(), ZaDomain.A2_zmailAutoProvAccountSrcSelectedPool, arr);
	} else {
		this.getModel().setInstanceValue(this.getInstance(), ZaDomain.A2_zmailAutoProvAccountSrcSelectedPool, null);
	}
}

ZaTaskAutoProvDialog.addButtonHndlr = function (ev) {
	var form = this.getForm();
	var instance = form.getInstance();
	var sourceListItems = form.getItemsById(ZaDomain.A2_zmailAutoProvAccountPool);
	if(sourceListItems && (sourceListItems instanceof Array) && sourceListItems[0] && sourceListItems[0].widget) {
		var selection = sourceListItems[0].widget.getSelection();
		var currentTargetList = instance[ZaDomain.A2_zmailAutoProvAccountTargetPool] ? instance[ZaDomain.A2_zmailAutoProvAccountTargetPool] : [];
		var list = (selection instanceof AjxVector) ? selection.getArray() : (selection instanceof Array) ? selection : [selection];
		if(list) {
			list.sort(ZaItem.compareNamesDesc);
			var tmpTargetList = [];
			var cnt2 = currentTargetList.length;
			for(var i=0;i<cnt2;i++)
				tmpTargetList.push(currentTargetList[i]);

			tmpTargetList.sort(ZaItem.compareNamesDesc);

			var tmpList = [];
			var cnt = list.length;
			for(var i=cnt-1; i>=0; i--) {
				var dup = false;
				cnt2 = tmpTargetList.length;
				for(var j = cnt2-1; j >=0; j--) {
					if(list[i].name==tmpTargetList[j].name) {
						dup=true;
						tmpTargetList.splice(j,cnt2-j);
						break;
					}
				}
				if(!dup) {
					currentTargetList.push(list[i])
				}
			}
			this.getModel().setInstanceValue(this.getInstance(), ZaDomain.A2_zmailAutoProvAccountTargetPool, currentTargetList);
		}
	}
	if(currentTargetList.length > 0) {
		form.parent._button[DwtDialog.OK_BUTTON].setEnabled(true);
	} else {
		form.parent._button[DwtDialog.OK_BUTTON].setEnabled(false);
	}
}

ZaTaskAutoProvDialog.accTargetSelectionListener = function() {
	var arr = this.widget.getSelection();
	if(arr && arr.length) {
		arr.sort();
		this.getModel().setInstanceValue(this.getInstance(), ZaDomain.A2_zmailAutoProvAccountTgtSelectedPool, arr);
	} else {
		this.getModel().setInstanceValue(this.getInstance(), ZaDomain.A2_zmailAutoProvAccountTgtSelectedPool, null);
	}
}

ZaTaskAutoProvDialog.addAllButtonHndlr = function (ev) {
	var form = this.getForm();
	var instance = form.getInstance();
	var oldArr = instance[ZaDomain.A2_zmailAutoProvAccountTargetPool] ? instance[ZaDomain.A2_zmailAutoProvAccountTargetPool]  : [];
	var arr = instance[ZaDomain.A2_zmailAutoProvAccountPool];
	var arr2 = new Array();
	if(arr) {
		var cnt = arr.length;
		var oldCnt = oldArr.length;
		for(var ix=0; ix< cnt; ix++) {
			var found = false;
			for(var j = oldCnt-1;j>=0;j-- ) {
				if(oldArr[j].name == arr[ix].name) {
					found = true;
					break;
				}
			}
			if(!found)
				arr2.push(arr[ix]);
		}
	}
	arr2 = arr2.concat(oldArr);
	this.getModel().setInstanceValue(this.getInstance(), ZaDomain.A2_zmailAutoProvAccountTargetPool, arr2);
	//this.getModel().setInstanceValue(this.getInstance(), ZaDomain.A2_zmailAutoProvAccountPool, new Array());
	var instance = form.getInstance();
	var currentTargetList = instance[ZaDomain.A2_zmailAutoProvAccountTargetPool] ? instance[ZaDomain.A2_zmailAutoProvAccountTargetPool] : [];
	if(currentTargetList.length > 0) {
		form.parent._button[DwtDialog.OK_BUTTON].setEnabled(true);
	} else {
		form.parent._button[DwtDialog.OK_BUTTON].setEnabled(false);
	}
}

ZaTaskAutoProvDialog.removeButtonHndlr = function (ev) {
	var form = this.getForm();
	var instance = form.getInstance();
	var targetListItems = form.getItemsById(ZaDomain.A2_zmailAutoProvAccountTargetPool);
	if(targetListItems && (targetListItems instanceof Array) && targetListItems[0] && targetListItems[0].widget) {
		var selection = targetListItems[0].widget.getSelection();

		var currentTargetList = instance[ZaDomain.A2_zmailAutoProvAccountTargetPool] ? instance[ZaDomain.A2_zmailAutoProvAccountTargetPool] : [];
		currentTargetList.sort(ZaItem.compareNamesDesc);
		var tmpTargetList = [];
		var list = (selection instanceof AjxVector) ? selection.getArray() : (selection instanceof Array) ? selection : [selection];
		if(list) {
			list.sort(ZaItem.compareNamesDesc);
			var cnt = list.length;
			var cnt2 = currentTargetList.length;
			for(var i=0;i<cnt2;i++)
				tmpTargetList.push(currentTargetList[i]);

			for(var i=cnt-1; i>=0; i--) {
				var cnt2 = tmpTargetList.length;
				for(var j = cnt2-1; j >=0; j--) {
					if(list[i].name==tmpTargetList[j].name) {
						currentTargetList.splice(j,1);
						tmpTargetList.splice(j,cnt2-j);
					}
				}
			}
			this.getModel().setInstanceValue(this.getInstance(), ZaDomain.A2_zmailAutoProvAccountTargetPool, currentTargetList);
		}
	}
	if(currentTargetList.length > 0) {
		form.parent._button[DwtDialog.OK_BUTTON].setEnabled(true);
	} else {
		form.parent._button[DwtDialog.OK_BUTTON].setEnabled(false);
	}
}

ZaTaskAutoProvDialog.removeAllButtonHndlr = function (ev) {
	var form = this.getForm();
	var instance = form.getInstance();

	instance[ZaDomain.A2_zmailAutoProvAccountTargetPool] = new Array();
	var currentTargetList = instance[ZaDomain.A2_zmailAutoProvAccountTargetPool] ? instance[ZaDomain.A2_zmailAutoProvAccountTargetPool] : [];
	if(currentTargetList.length > 0) {
		form.parent._button[DwtDialog.OK_BUTTON].setEnabled(true);
	} else {
		form.parent._button[DwtDialog.OK_BUTTON].setEnabled(false);
	}
	this.getForm().setInstance(instance);
}

ZaTaskAutoProvDialog.backPoolButtonHndlr =
function(evt) {
	var currentPageNum = parseInt(this.getInstanceValue("/poolPagenum"))-1;
	this.setInstanceValue(currentPageNum,"/poolPagenum");
	ZaTaskAutoProvDialog.srchButtonHndlr.call(this, evt);
}

ZaTaskAutoProvDialog.fwdPoolButtonHndlr =
function(evt) {
	var currentPageNum = parseInt(this.getInstanceValue("/poolPagenum"));
	this.setInstanceValue(currentPageNum+1,"/poolPagenum");
	ZaTaskAutoProvDialog.srchButtonHndlr.call(this, evt);
}

ZaTaskAutoProvDialog.forwardBtnEnabled = function () {
	return (parseInt(this.getInstanceValue(ZaDomain.A2_zmailAutoProvAccountPoolPageNum)) < (parseInt(this.getInstanceValue(ZaDomain.A2_zmailAutoProvAccountPoolPageTotal))-1)
            && this.getInstanceValue(ZaDomain.A2_zmailAutoProvSearchActivated)=="TRUE");
};

ZaTaskAutoProvDialog.backBtnEnabled = function () {
	return (parseInt(this.getInstanceValue(ZaDomain.A2_zmailAutoProvAccountPoolPageNum)) > 0
            && this.getInstanceValue(ZaDomain.A2_zmailAutoProvSearchActivated)== "TRUE");
};

ZaTaskAutoProvDialog.prototype.finishConfig = function () {
    var instance = this.getObject();

    var acctlist = instance[ZaDomain.A2_zmailAutoProvAccountTargetPool];//this.getModel().getInstanceValue(instance,ZaDomain.A2_zmailAutoProvAccountTargetPool);
    if(!acctlist || acctlist.length < 1) return;
    var soapDoc = AjxSoapDoc.create("BatchRequest", "urn:zmail");
    soapDoc.setMethodAttribute("onerror", "continue");

    for(var i = 0; i < acctlist.length; i++) {
		var autoProvDoc = soapDoc.set("AutoProvAccountRequest", null, null, ZaZmailAdmin.URN);

        var attr = soapDoc.set("domain", instance.id, autoProvDoc);
        attr.setAttribute("by", "id");

        attr = soapDoc.set("principal", acctlist[i].dn, autoProvDoc);
        attr.setAttribute("by", "dn");

        if(instance[ZaDomain.A2_zmailAutoProvAccountPassword]) {
            attr = soapDoc.set("password", instance[ZaDomain.A2_zmailAutoProvAccountPassword], autoProvDoc);
        }
    }

    var params = new Object();
    params.soapDoc = soapDoc;
    var reqMgrParams ={
        controller:ZaApp.getInstance().getCurrentController(),
        busyMsg : ZaMsg.BUSY_CREATING_GALDS,
        showBusy:true
    }
    ZaRequestMgr.invoke(params, reqMgrParams);
}

ZaTaskAutoProvDialog.filterSelectionListener =
function (value) {
	var targetEl = value.target ;
	if (targetEl.type && targetEl.type == "checkbox") {

		var item = targetEl.value ;
		var form = this.getForm ();
		var instance = form.getInstance ();

		var checkedFiltersVector = null ;

        checkedFiltersVector = instance[ZaDomain.A2_zmailAutoProvSelectedServerList];

		if (targetEl.checked) {
			checkedFiltersVector.remove(item);

		}else{

			checkedFiltersVector.add(item);

		}
	}
}

/////////////////////////////
ZaServerOptionList = function(parent,className) {
	DwtListView.call(this, parent, null);//, Dwt.ABSOLUTE_STYLE);
}

ZaServerOptionList.prototype = new DwtListView;
ZaServerOptionList.prototype.constructor = ZaServerOptionList;

ZaServerOptionList.prototype.toString =
function() {
	return "ZaServerOptionList";
}

ZaServerOptionList.prototype._createItemHtml =
function(item, params, asHtml, count) {
	var html = new Array(10);
	var	div = document.createElement("div");
	div[DwtListView._STYLE_CLASS] = "Row";
	div[DwtListView._SELECTED_STYLE_CLASS] = div[DwtListView._STYLE_CLASS] + "-" + DwtCssStyle.SELECTED;
	div.className = div[DwtListView._STYLE_CLASS];
	this.associateItemWithElement(item, div, DwtListView.TYPE_LIST_ITEM);

	var idx = 0;
    var checked = "";

    if(item.checked) checked = "checked";
	html[idx++] = "<table width='100%' cellspacing='0' cellpadding='0' ><tr><td width=20>"

    if(this.initializeDisable)
	    html[idx++] = "<input id='"+this._htmlElId+"_schedule_"+count+"'  disabled type=checkbox value='" + item + "' " + checked + "/></td>" ;
	else
        html[idx++] = "<input id='"+this._htmlElId+"_schedule_"+count+"' type=checkbox value='" + item + "' " + checked + "/></td>" ;

    html[idx++] = "<td>"+ item + "</td></tr></table>";
	div.innerHTML = html.join("");
	return div;
}


/////////////////////////////
ZaConfirmPasswordDialog = function(parent,   w, h, title) {
	if (arguments.length == 0) return;
	this._standardButtons = [DwtDialog.OK_BUTTON, DwtDialog.CANCEL_BUTTON];
	ZaXDialog.call(this, parent, null, title, w, h, null, ZaId.DLG_AUTPROV_MANUAL_PWD);
	this._containedObject = {};
	this.initForm(ZaAlias.myXModel,this.getMyXForm());
	this._helpURL = ZaConfirmPasswordDialog.helpURL;
	this._localXForm.addListener(DwtEvent.XFORMS_FORM_DIRTY_CHANGE, new AjxListener(this, ZaConfirmPasswordDialog.prototype.handleXFormChange));
}

ZaConfirmPasswordDialog.prototype = new ZaXDialog;
ZaConfirmPasswordDialog.prototype.constructor = ZaConfirmPasswordDialog;
ZaConfirmPasswordDialog.helpURL = location.pathname + ZaUtil.HELP_URL + "managing_domain/autoprov_manual_config.htm?locid="+AjxEnv.DEFAULT_LOCALE;


ZaConfirmPasswordDialog.prototype.popup = function(loc){
    ZaXDialog.prototype.popup.call(this, loc);
    //this._button[DwtDialog.OK_BUTTON].setEnabled(false); //if we don't allow empty password, should switch to this
    this._button[DwtDialog.OK_BUTTON].setEnabled(true);
    this._localXForm.setInstanceValue(false, ZaDomain.A2_zmailAutoProvAccountPasswordUnmatchedWarning);
}

ZaConfirmPasswordDialog.prototype.handleXFormChange =
function ( ) {
    var xformObj = this._localXForm;
    if(!xformObj || xformObj.hasErrors() || !xformObj.getInstance()){
        return;
    }

    var pw = xformObj.getInstanceValue(ZaDomain.A2_zmailAutoProvAccountPasswordInDlg);
    var pwAgain = xformObj.getInstanceValue(ZaDomain.A2_zmailAutoProvAccountPasswordAgainInDlg);

    if (pw == pwAgain){
        xformObj.setInstanceValue(false, ZaDomain.A2_zmailAutoProvAccountPasswordUnmatchedWarning);
        this._button[DwtDialog.OK_BUTTON].setEnabled(true);
    } else {
        var is1stTime = AjxUtil.isEmpty(pwAgain);
        //we show the warning msg until user start to input pwAgain
        xformObj.setInstanceValue(!is1stTime, ZaDomain.A2_zmailAutoProvAccountPasswordUnmatchedWarning);

        this._button[DwtDialog.OK_BUTTON].setEnabled(false);
    }
}


ZaConfirmPasswordDialog.prototype.getMyXForm =
function() {
    var xFormObject = {
        items:[
            {type:_GROUP_, numCols:2, colSizes:["200px","*"], colSpan:"*",
                items: [
                    {type:_DWT_ALERT_, style:DwtAlert.WARNING, iconVisible:true,
                        content:ZaMsg.MSG_AUTOPROV_MANUAL_PASSSET,
                        width:"100%", colSpan:"*"
                    },
                    {type:_SPACER_, height:10, colSpan:"*"},
                    {
                        ref:ZaDomain.A2_zmailAutoProvAccountPasswordInDlg,
                        type:_SECRET_, msgName:ZaMsg.LBL_provisionedAccountPassword,
                        label:ZaMsg.LBL_provisionedAccountPassword, labelLocation:_LEFT_,
                        width:"190px",
                        cssClass:"admin_xform_name_input"
                    },
                    {type:_SPACER_, height:10, colSpan:"*"},
                    {
                        ref:ZaDomain.A2_zmailAutoProvAccountPasswordAgainInDlg,
                        type:_SECRET_, msgName:ZaMsg.NAD_ConfirmPassword,
                        label:ZaMsg.NAD_ConfirmPassword, labelLocation:_LEFT_,
                        width:"190px",
                        cssClass:"admin_xform_name_input"
                    },
                    {
                        ref:ZaDomain.A2_zmailAutoProvAccountPasswordUnmatchedWarning,
                        type:_DWT_ALERT_, style: DwtAlert.CRITICAL, iconVisible: false,
                        label:"", labelLocation:_LEFT_,
                        width:"180px", colSpan:"1",
                        content: ZaMsg.ERROR_PASSWORD_MISMATCH,
                        visibilityChecks:[[XForm.checkInstanceValue, ZaDomain.A2_zmailAutoProvAccountPasswordUnmatchedWarning, true]],
                        visibilityChangeEventSources:[ZaDomain.A2_zmailAutoProvAccountPasswordUnmatchedWarning]
                    }
                ]
            }
        ]
    };
    return xFormObject;
}


/////////////////////////////
ZaServerOptionList.prototype.setEnabled =
function(enabled) {
	 DwtListView.prototype.setEnabled.call(this, enabled);
    //
     this.initializeDisable=!enabled;
     if(!AjxUtil.isEmpty(this._list)){
        for(var i=0;i<this._list.size();i++){
            document.getElementById(this._htmlElId+"_schedule_"+i).disabled=!enabled;
        }
     }

}
