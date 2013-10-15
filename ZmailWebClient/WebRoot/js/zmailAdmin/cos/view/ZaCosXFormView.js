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
* @class ZaCosXFormView
* @contructor
* @param parent
* @param app
* @author Greg Solovyev
**/
ZaCosXFormView = function(parent, entry) {
	ZaTabView.call(this, {
		parent:parent,
		iKeyName:"ZaCosXFormView",
		contextId:ZaId.TAB_COS_EDIT
	});
	this.TAB_INDEX = 0;	
	this.initForm(ZaCos.myXModel,this.getMyXForm(entry), null);
}

ZaCosXFormView.prototype = new ZaTabView();
ZaCosXFormView.prototype.constructor = ZaCosXFormView;
ZaTabView.XFormModifiers["ZaCosXFormView"] = new Array();
ZaTabView.ObjectModifiers["ZaCosXFormView"] = [] ;
ZaCosXFormView.prototype.TAB_INDEX=0;
ZaCosXFormView.zimletChoices = new XFormChoices([], XFormChoices.SIMPLE_LIST);
ZaCosXFormView.themeChoices = new XFormChoices([], XFormChoices.OBJECT_LIST);

/**
* @method setObject sets the object contained in the view
* @param entry - ZaDomain object to display
**/
ZaCosXFormView.prototype.setObject =
function(entry) {
	//handle the special attributes to be displayed in xform
	entry.manageSpecialAttrs();
	this._containedObject = new Object();
	this._containedObject.attrs = new Object();
	
	this._containedObject.name = entry.name;
	this._containedObject.type = entry.type ;

	if(entry.rights)
		this._containedObject.rights = entry.rights;
	
	if(entry.setAttrs)
		this._containedObject.setAttrs = entry.setAttrs;
	
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

	
	if (entry.getAttrs) {
        if(entry.getAttrs[ZaCos.A_zmailAvailableSkin] || entry.getAttrs.all) {
            var skins = entry.attrs[ZaCos.A_zmailAvailableSkin];
            if(skins != null && skins != "") {
                if (AjxUtil.isString(skins))	 {
                    this._containedObject.attrs[ZaCos.A_zmailAvailableSkin] = [skins];
                } else {
                    var cnt = skins.length;
                    this._containedObject.attrs[ZaCos.A_zmailAvailableSkin] = [];
                    for(var i = 0; i < cnt; i ++) {
                        this._containedObject.attrs[ZaCos.A_zmailAvailableSkin].push(skins[i]);
                    }
                }

            } else {
                this._containedObject.attrs[ZaCos.A_zmailAvailableSkin] = null;
            }

            var skins = ZaApp.getInstance().getInstalledSkins();
            if(skins == null) {
                skins = [];
            } else if (AjxUtil.isString(skins))	 {
                skins = [skins];
            }

            var skinsChoices = ZaApp.getInstance().getSkinChoices(skins);
            ZaCosXFormView.themeChoices.setChoices(skinsChoices);
            ZaCosXFormView.themeChoices.dirtyChoices();
        }

        if(entry.getAttrs[ZaCos.A_zmailZimletAvailableZimlets] || entry.getAttrs.all) {
            //get all Zimlets
            var allZimlets = ZaZimlet.getAll(ZaZimlet.EXCLUDE_EXTENSIONS);
            if(allZimlets == null) {
                allZimlets = [];
            }

            if(allZimlets instanceof ZaItemList || allZimlets instanceof AjxVector)
                allZimlets = allZimlets.getArray();

            //convert objects to strings
            var cnt = allZimlets.length;
            var _tmpZimlets = [];
            for(var i=0; i<cnt; i++) {
                var zimlet = allZimlets[i];
                _tmpZimlets.push(zimlet.name);
            }
            ZaCosXFormView.zimletChoices.setChoices(_tmpZimlets);
            ZaCosXFormView.zimletChoices.dirtyChoices();
        }
    }

    // update the domainlist
    ZaCosXFormView._domainList = ZaApp.getInstance().getDomainList(true).getArray();

    if(!entry[ZaModel.currentTab])
		this._containedObject[ZaModel.currentTab] = "1";
	else
		this._containedObject[ZaModel.currentTab] = entry[ZaModel.currentTab];

	this.modifyContainedObject () ;
	this._localXForm.setInstance(this._containedObject);

}

ZaCosXFormView.gotSkins = function () {
	return ((ZaApp.getInstance().getInstalledSkins() != null) && (ZaApp.getInstance().getInstalledSkins().length > 0));
}

ZaCosXFormView.gotNoSkins = function () {
    return !ZaCosXFormView.gotSkins.call(this);
}

ZaCosXFormView.isPasswordLockoutEnabled = function () {
	return (this.getInstanceValue(ZaCos.A_zmailPasswordLockoutEnabled) == 'TRUE');
}

ZaCosXFormView.isMailFeatureEnabled = function () {
	return (this.getInstanceValue(ZaCos.A_zmailFeatureMailEnabled) == "TRUE");
}

ZaCosXFormView.isContactsFeatureEnabled = function () {
    return this.getInstanceValue(ZaCos.A_zmailFeatureContactsEnabled) == "TRUE";
}

ZaCosXFormView.isCalendarFeatureEnabled = function () {
	return this.getInstanceValue(ZaCos.A_zmailFeatureCalendarEnabled)=="TRUE";
}

ZaCosXFormView.isMailForwardingEnabled = function () {
	return (this.getInstanceValue(ZaCos.A_zmailFeatureMailForwardingEnabled) == "TRUE");
}

//ZaCosXFormView.isMailFeatureEnabled = function () {
//	return (this.getInstanceValue(ZaCos.A_zmailFeatureMailEnabled) == "TRUE");
//}

ZaCosXFormView.isBriefcaseFeatureEnabled = function () {
	return (this.getInstanceValue(ZaCos.A_zmailFeatureBriefcasesEnabled) == "TRUE");
}

ZaCosXFormView.FEATURE_TAB_ATTRS = [ZaCos.A_zmailFeatureMailEnabled,
	ZaCos.A_zmailFeatureReadReceiptsEnabled,
	ZaCos.A_zmailFeatureContactsEnabled,
    ZaCos.A_zmailFeatureDistributionListFolderEnabled,
	ZaCos.A_zmailFeatureCalendarEnabled,
	ZaCos.A_zmailFeatureTasksEnabled,
	//ZaCos.A_zmailFeatureNotebookEnabled,
	ZaCos.A_zmailFeatureBriefcasesEnabled,
	//ZaCos.A_zmailFeatureIMEnabled,
	ZaCos.A_zmailFeatureOptionsEnabled,
	ZaCos.A_zmailFeatureTaggingEnabled,
	ZaCos.A_zmailFeatureSharingEnabled,
	ZaCos.A_zmailExternalSharingEnabled,
	ZaCos.A_zmailPublicSharingEnabled,
	ZaCos.A_zmailFeatureChangePasswordEnabled,
	ZaCos.A_zmailFeatureSkinChangeEnabled,
	ZaCos.A_zmailFeatureManageZimlets,
	ZaCos.A_zmailFeatureHtmlComposeEnabled,
	//ZaCos.A_zmailFeatureShortcutAliasesEnabled,
	ZaCos.A_zmailFeatureGalEnabled,
	ZaCos.A_zmailFeatureMAPIConnectorEnabled,
	ZaCos.A_zmailFeatureGalAutoCompleteEnabled,
	ZaCos.A_zmailFeatureMailPriorityEnabled,
	ZaCos.A_zmailFeatureFlaggingEnabled,
	ZaCos.A_zmailImapEnabled,
	ZaCos.A_zmailPop3Enabled,
	ZaCos.A_zmailFeatureImapDataSourceEnabled,
	ZaCos.A_zmailFeaturePop3DataSourceEnabled,
	ZaCos.A_zmailFeatureConversationsEnabled,
	ZaCos.A_zmailFeatureFiltersEnabled,
	ZaCos.A_zmailFeatureOutOfOfficeReplyEnabled,
	ZaCos.A_zmailFeatureNewMailNotificationEnabled,
	ZaCos.A_zmailFeatureMailPollingIntervalPreferenceEnabled,
	ZaCos.A_zmailFeatureIdentitiesEnabled,
	ZaCos.A_zmailFeatureGroupCalendarEnabled,
	//ZaCos.A_zmailFeatureInstantNotify,
	ZaCos.A_zmailFeaturePeopleSearchEnabled,
	ZaCos.A_zmailFeatureAdvancedSearchEnabled,
	ZaCos.A_zmailFeatureSavedSearchesEnabled,
	ZaCos.A_zmailFeatureInitialSearchPreferenceEnabled,
	ZaCos.A_zmailFeatureImportFolderEnabled,
    ZaCos.A_zmailFeatureExportFolderEnabled,
	ZaCos.A_zmailDumpsterEnabled,
	ZaCos.A_zmailDumpsterPurgeEnabled,
	ZaCos.A_zmailFeatureMailSendLaterEnabled,
	//ZaCos.A_zmailFeatureFreeBusyViewEnabled,
    ZaCos.A_zmailFeatureCalendarReminderDeviceEmailEnabled,
	ZaCos.A_zmailFeatureSMIMEEnabled
];

ZaCosXFormView.FEATURE_TAB_RIGHTS = [];

ZaCosXFormView.PREFERENCES_TAB_ATTRS = [
	ZaCos.A_zmailPrefMailSendReadReceipts,
	ZaCos.A_zmailPrefUseTimeZoneListInCalendar,
	ZaCos.A_zmailPrefCalendarUseQuickAdd,
	ZaCos.A_zmailPrefCalendarAlwaysShowMiniCal,
	ZaCos.A_zmailPrefCalendarApptReminderWarningTime,
	ZaCos.A_zmailPrefTimeZoneId,
	ZaCos.A_zmailPrefGalAutoCompleteEnabled,
	ZaCos.A_zmailPrefAutoAddAddressEnabled,
	ZaCos.A_zmailMailSignatureMaxLength,
	ZaCos.A_zmailPrefForwardReplyInOriginalFormat,
	ZaCos.A_zmailPrefHtmlEditorDefaultFontColor,
	ZaCos.A_zmailPrefHtmlEditorDefaultFontFamily,
	ZaCos.A_zmailPrefHtmlEditorDefaultFontSize,
	ZaCos.A_zmailPrefComposeFormat,
	ZaCos.A_zmailPrefComposeInNewWindow,
	ZaCos.A_zmailAllowAnyFromAddress,
	ZaCos.A_zmailMailMinPollingInterval,
	ZaCos.A_zmailPrefMailPollingInterval,
	ZaCos.A_zmailPrefAutoSaveDraftInterval,
	ZaCos.A_zmailPrefMailDefaultCharset,
	ZaCos.A_zmailMaxMailItemsPerPage,
	ZaCos.A_zmailPrefMailItemsPerPage,
	ZaCos.A_zmailPrefGroupMailBy,
	ZaCos.A_zmailPrefDisplayExternalImages,
	ZaCos.A_zmailPrefMessageViewHtmlPreferred,
	ZaCos.A_zmailPrefLocale,
	ZaCos.A_zmailJunkMessagesIndexingEnabled,
	ZaCos.A_zmailPrefShowSelectionCheckbox,
	ZaCos.A_zmailPrefWarnOnExit,
	ZaCos.A_zmailPrefAdminConsoleWarnOnExit,
    ZaCos.A_zmailPrefUseKeyboardShortcuts,
	ZaCos.A_zmailPrefImapSearchFoldersEnabled,
	ZaCos.A_zmailPrefShowSearchString,
	ZaCos.A_zmailPrefMailInitialSearch,
	ZaCos.A_zmailPrefClientType,
	ZaCos.A_zmailPrefCalendarInitialView,
	ZaCos.A_zmailPrefCalendarFirstDayOfWeek,
	ZaCos.A_zmailPrefCalendarReminderFlashTitle,
	ZaCos.A_zmailPrefCalendarAllowCancelEmailToSelf,
	ZaCos.A_zmailPrefCalendarAllowPublishMethodInvite,
	ZaCos.A_zmailPrefCalendarToasterEnabled,
	ZaCos.A_zmailPrefCalendarShowPastDueReminders,
	ZaCos.A_zmailPrefAppleIcalDelegationEnabled,
	ZaCos.A_zmailPrefMandatorySpellCheckEnabled
];
ZaCosXFormView.PREFERENCES_TAB_RIGHTS = [];	

ZaCosXFormView.SKIN_TAB_ATTRS = [ZaCos.A_zmailPrefSkin,ZaCos.A_zmailAvailableSkin];
ZaCosXFormView.SKIN_TAB_RIGHTS = [];

ZaCosXFormView.ZIMLET_TAB_ATTRS = [ZaCos.A_zmailZimletAvailableZimlets];
ZaCosXFormView.ZIMLET_TAB_RIGHTS = [ZaCos.RIGHT_GET_ZIMLET, ZaCos.RIGHT_LIST_ZIMLET];

ZaCosXFormView.SERVERPOOL_TAB_ATTRS = [ZaCos.A_zmailMailHostPool];
ZaCosXFormView.SERVERPOOL_TAB_RIGHTS = [ZaCos.RIGHT_GET_HOSTNAME];

ZaCosXFormView.ADVANCED_TAB_ATTRS = [ZaCos.A_zmailAttachmentsBlocked,
	ZaCos.A_zmailMailQuota,
	ZaCos.A_zmailContactMaxNumEntries,
	ZaCos.A_zmailQuotaWarnPercent,
	ZaCos.A_zmailQuotaWarnInterval,
	ZaCos.A_zmailQuotaWarnMessage,
	ZaCos.A_zmailPasswordLocked,
	ZaCos.A_zmailMinPwdLength,
	ZaCos.A_zmailMaxPwdLength,
	ZaCos.A_zmailPasswordMinUpperCaseChars,
	ZaCos.A_zmailPasswordMinLowerCaseChars,
	ZaCos.A_zmailPasswordMinPunctuationChars,
	ZaCos.A_zmailPasswordMinNumericChars,
	ZaCos.A_zmailPasswordMinPunctuationChars,
	ZaCos.A_zmailMinPwdAge,
	ZaCos.A_zmailMaxPwdAge,
	ZaCos.A_zmailEnforcePwdHistory,
	ZaCos.A_zmailPasswordLockoutEnabled,
	ZaCos.A_zmailPasswordLockoutMaxFailures,
	ZaCos.A_zmailPasswordLockoutDuration,
	ZaCos.A_zmailPasswordLockoutFailureLifetime,
	ZaCos.A_zmailAdminAuthTokenLifetime,
	ZaCos.A_zmailAuthTokenLifetime,
	ZaCos.A_zmailMailIdleSessionTimeout,
	ZaCos.A_zmailMailMessageLifetime,
	ZaCos.A_zmailMailTrashLifetime,
	ZaCos.A_zmailMailSpamLifetime,
	ZaCos.A_zmailMailDumpsterLifetime,
	ZaCos.A_zmailDumpsterUserVisibleAge,
	ZaCos.A_zmailFreebusyExchangeUserOrg,
        ZaCos.A_zmailDataSourcePop3PollingInterval,
        ZaCos.A_zmailDataSourceImapPollingInterval,
        ZaCos.A_zmailDataSourceCalendarPollingInterval,
        ZaCos.A_zmailDataSourceRssPollingInterval,
        ZaCos.A_zmailDataSourceCaldavPollingInterval,
	ZaCos.A_zmailDataSourceMinPollingInterval

];
ZaCosXFormView.ADVANCED_TAB_RIGHTS = [];

ZaCosXFormView.RETENTION_POLICY_TAB_ATTRS = [];
ZaCosXFormView.RETENTION_POLICY_TAB_RIGHTS = [];

ZaCosXFormView.prototype.loadRetentionPolicies = function () {
    var resultCos = ZaRetentionPolicy.getRetentionPolicies("id", this.getForm().getInstance().id);
    var resultGlobal = ZaRetentionPolicy.getRetentionPolicies();

    if (resultCos[ZaRetentionPolicy.TYPE_KEEP].length) {
        //It is global
        if (ZaCosXFormView._arrayContains(resultGlobal[ZaRetentionPolicy.TYPE_KEEP], resultCos[ZaRetentionPolicy.TYPE_KEEP][0])) {
            this.getForm().setInstanceValue(resultGlobal[ZaRetentionPolicy.TYPE_KEEP],ZaCos.A2_retentionPoliciesKeep);
            this.getForm().setInstanceValue(resultGlobal[ZaRetentionPolicy.TYPE_PURGE],ZaCos.A2_retentionPoliciesPurge);
            this.getForm().setInstanceValue("FALSE",ZaCos.A2_retentionPoliciesKeepInherited);
            return;
        }
    }

    if (resultCos[ZaRetentionPolicy.TYPE_PURGE].length) {
        //It is global
        if (ZaCosXFormView._arrayContains(resultGlobal[ZaRetentionPolicy.TYPE_PURGE], resultCos[ZaRetentionPolicy.TYPE_PURGE][0])) {
            this.getForm().setInstanceValue(resultGlobal[ZaRetentionPolicy.TYPE_KEEP],ZaCos.A2_retentionPoliciesKeep);
            this.getForm().setInstanceValue(resultGlobal[ZaRetentionPolicy.TYPE_PURGE],ZaCos.A2_retentionPoliciesPurge);
            this.getForm().setInstanceValue("FALSE",ZaCos.A2_retentionPoliciesKeepInherited);
            return;
        }
    }



    if (resultCos[ZaRetentionPolicy.TYPE_PURGE].length == 0 &&
        resultCos[ZaRetentionPolicy.TYPE_KEEP].length == 0) {
        this.getForm().setInstanceValue(resultGlobal[ZaRetentionPolicy.TYPE_KEEP],ZaCos.A2_retentionPoliciesKeep);
        this.getForm().setInstanceValue(resultGlobal[ZaRetentionPolicy.TYPE_PURGE],ZaCos.A2_retentionPoliciesPurge);
        this.getForm().setInstanceValue("FALSE",ZaCos.A2_retentionPoliciesKeepInherited);
    } else {
        this.getForm().setInstanceValue(resultCos[ZaRetentionPolicy.TYPE_KEEP],ZaCos.A2_retentionPoliciesKeep);
        this.getForm().setInstanceValue(resultCos[ZaRetentionPolicy.TYPE_PURGE],ZaCos.A2_retentionPoliciesPurge);
        this.getForm().setInstanceValue("TRUE",ZaCos.A2_retentionPoliciesKeepInherited);
    }
}

ZaCosXFormView._arrayContains = function(arr, obj) {
    if (!arr || !(arr instanceof Array) || !obj) {
        return false;
    }

    for (var i = 0; i < arr.length; i++) {
        if (arr[i].id && obj.id && arr[i].id == obj.id) {
            return true;
        }
    }
    return false;
}

ZaCosXFormView.retentionSelectionListener = function(ev) {
    this.getForm().setInstanceValue(this.getSelection(),ZaCos.A2_retentionPoliciesKeep_Selection);
}

ZaCosXFormView.purgeSelectionListener = function() {
    this.getForm().setInstanceValue(this.getSelection(),ZaCos.A2_retentionPoliciesPurge_Selection);
}

ZaCosXFormView.deleteButtonListener = function(action) {
    var selected = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesKeep_Selection);

    if (action == 1) {
        selected = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesPurge_Selection);
    } else if (action == 2) {
        selected = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesPurge).length +
            this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesKeep).length
    }

    //The original list is empty, but should show global polices
    if (selected === 0) {
        ZaCosXFormView.deleteRetentionPolicies.call(this, action);
        return;
    }
    if (!selected) {
        return;
    }

    if(!ZaApp.getInstance().dialogs["DeleteRetionPolicyConfirmMessageDialog"])
        ZaApp.getInstance().dialogs["DeleteRetionPolicyConfirmMessageDialog"] = new ZaMsgDialog(ZaApp.getInstance().getAppCtxt().getShell(), null, [DwtDialog.YES_BUTTON, DwtDialog.NO_BUTTON], null, ZaId.CTR_PREFIX + "DELETE_RETENTION_POLICY_ConfirmMessage");

    ZaApp.getInstance().dialogs["DeleteRetionPolicyConfirmMessageDialog"].setMessage(ZaMsg.Q_Delete_RetentionPolicy_Confirm,
        DwtMessageDialog.WARNING_STYLE);
    ZaApp.getInstance().dialogs["DeleteRetionPolicyConfirmMessageDialog"].registerCallback(DwtDialog.YES_BUTTON, ZaCosXFormView.deleteRetentionPolicies, this, [action]);
    ZaApp.getInstance().dialogs["DeleteRetionPolicyConfirmMessageDialog"].popup();
}

ZaCosXFormView.deleteRetentionPolicies = function(action) {
    if(ZaApp.getInstance().dialogs["DeleteRetionPolicyConfirmMessageDialog"])
        ZaApp.getInstance().dialogs["DeleteRetionPolicyConfirmMessageDialog"].popdown();

    var selected;
    var all;
    if (action == 1) { //Remove purge
        selected = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesPurge_Selection);
        all = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesPurge);
        if (!selected) {
            return;
        }
        for (var i = 0; i <selected.length; i++ ) {
            selected[i].deletePolicy("id", this.getForm().getInstance().id);
            AjxUtil.arrayRemove(all, selected[i]);
        }
        this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesPurge);
        this.getForm().setInstanceValue(all,ZaCos.A2_retentionPoliciesPurge);
        this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesPurge_Selection);
    } else if (action == 2) { //Remove all
        all = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesPurge);
        all = all.concat(this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesKeep));
        for (var i = 0; i < all.length; i++) {
            all[i].deletePolicy("id", this.getForm().getInstance().id);
        }
        this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesPurge);
        this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesKeep);

        var resultGlobal = ZaRetentionPolicy.getRetentionPolicies();
        this.getForm().setInstanceValue(resultGlobal[ZaRetentionPolicy.TYPE_KEEP],ZaCos.A2_retentionPoliciesKeep);
        this.getForm().setInstanceValue(resultGlobal[ZaRetentionPolicy.TYPE_PURGE],ZaCos.A2_retentionPoliciesPurge);
        this.getForm().setInstanceValue("FALSE",ZaCos.A2_retentionPoliciesKeepInherited);
    }else { //Remove keep
        selected = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesKeep_Selection);
        all = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesKeep);
        if (!selected) {
            return;
        }
        for (var i = 0; i <selected.length; i++ ) {
            selected[i].deletePolicy("id", this.getForm().getInstance().id);
            AjxUtil.arrayRemove(all, selected[i]);
        }
        this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesKeep);
        this.getForm().setInstanceValue(all,ZaCos.A2_retentionPoliciesKeep);
        this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesKeep_Selection);
    }
}

ZaCosXFormView.addButtonListener = function (isPurge) {
    var policy;
    if (isPurge) {
        policy = new ZaRetentionPolicy(null, null, null, ZaRetentionPolicy.TYPE_PURGE);
    } else {
        policy = new ZaRetentionPolicy();
    }

    if(!ZaApp.getInstance().dialogs["newRetentionPolicyDialog"]) {
        ZaApp.getInstance().dialogs["newRetentionPolicyDialog"] =
            new ZaRetentionPolicyDlg(ZaApp.getInstance().getAppCtxt().getShell(), "500px","100px", ZaMsg.TTL_Policy_Add);
        ZaApp.getInstance().dialogs["newRetentionPolicyDialog"].registerCallback(DwtDialog.OK_BUTTON,
            ZaCosXFormView.createRetentionPolicy,
            this, ZaApp.getInstance().dialogs["newRetentionPolicyDialog"]._localXForm);
    }

    ZaApp.getInstance().dialogs["newRetentionPolicyDialog"].setObject(policy);
    ZaApp.getInstance().dialogs["newRetentionPolicyDialog"].popup();
}

ZaCosXFormView.createRetentionPolicy = function (form) {
    if(ZaApp.getInstance().dialogs["newRetentionPolicyDialog"]) {
        var obj = form.getInstance();
        var all;
        if (obj[ZaRetentionPolicy.A2_type] == ZaRetentionPolicy.TYPE_KEEP) {
            all = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesKeep);
        } else {
            all = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesPurge);
        }
        if (!all) {
            all = [];
        }

        if (!ZaRetentionPolicy.checkValues(obj, all)) {
            return false;
        }

        ZaApp.getInstance().dialogs["newRetentionPolicyDialog"].popdown();

        obj.createPolicy("id", this.getForm().getInstance().id);

        all.push(obj);

        if (obj[ZaRetentionPolicy.A2_type] == ZaRetentionPolicy.TYPE_KEEP) {
            this.getForm().setInstanceValue([], ZaCos.A2_retentionPoliciesKeep);
            this.getForm().setInstanceValue(all, ZaCos.A2_retentionPoliciesKeep);
            this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesKeep_Selection);
        } else {
            this.getForm().setInstanceValue([], ZaCos.A2_retentionPoliciesPurge);
            this.getForm().setInstanceValue(all, ZaCos.A2_retentionPoliciesPurge);
            this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesPurge_Selection);
        }
    }
}

ZaCosXFormView.editButtonListener = function (isPurge) {
    var policy;
    if (isPurge) {
        policy = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesPurge_Selection)[0];
    } else {
        policy = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesKeep_Selection)[0];
    }

    if(!ZaApp.getInstance().dialogs["editRetentionPolicyDialog"]) {
        ZaApp.getInstance().dialogs["editRetentionPolicyDialog"] =
            new ZaRetentionPolicyDlg(ZaApp.getInstance().getAppCtxt().getShell(), "500px","100px", ZaMsg.TTL_Policy_Edit);
        ZaApp.getInstance().dialogs["editRetentionPolicyDialog"].registerCallback(DwtDialog.OK_BUTTON,
            ZaCosXFormView.updateRetentionPolicy,
            this, ZaApp.getInstance().dialogs["editRetentionPolicyDialog"]._localXForm);
    }

    ZaApp.getInstance().dialogs["editRetentionPolicyDialog"].setObject(policy);
    ZaApp.getInstance().dialogs["editRetentionPolicyDialog"].popup();
}

ZaCosXFormView.updateRetentionPolicy = function (form) {
    if(ZaApp.getInstance().dialogs["editRetentionPolicyDialog"]) {
        var obj = form.getInstance();
        var all;
        if (obj[ZaRetentionPolicy.A2_type] == ZaRetentionPolicy.TYPE_KEEP) {
            all = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesKeep);
        } else {
            all = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesPurge);
        }
        if (!all) {
            all = [];
        }
        if (!ZaRetentionPolicy.checkValues(obj, all)) {
            return false;
        }

        ZaApp.getInstance().dialogs["editRetentionPolicyDialog"].popdown();

        obj.modifyPolicy("id", this.getForm().getInstance().id);

        var index = AjxUtil.indexOf(all, obj);
        AjxUtil.arrayRemove(all, obj);
        AjxUtil.arrayAdd(all, obj, index);

        if (obj[ZaRetentionPolicy.A2_type] == ZaRetentionPolicy.TYPE_KEEP) {
            this.getForm().setInstanceValue([], ZaCos.A2_retentionPoliciesKeep);
            this.getForm().setInstanceValue(all, ZaCos.A2_retentionPoliciesKeep);
            this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesKeep_Selection);
        } else {
            this.getForm().setInstanceValue([], ZaCos.A2_retentionPoliciesPurge);
            this.getForm().setInstanceValue(all, ZaCos.A2_retentionPoliciesPurge);
            this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesPurge_Selection);
        }
    }
}

ZaCosXFormView.inheritCOSRetentionPolicies = function(value, event, form) {
    if (value == "TRUE") {
        this.setInstanceValue(value);
        this.getForm().setInstanceValue([], ZaCos.A2_retentionPoliciesKeep);
        this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesKeep_Selection);
        this.getForm().setInstanceValue([], ZaCos.A2_retentionPoliciesPurge);
        this.getForm().setInstanceValue([],ZaCos.A2_retentionPoliciesPurge_Selection);
    } else if (value == "FALSE") {
        ZaCosXFormView.deleteButtonListener.call(this, 2);
    }
}


ZaCosXFormView.myXFormModifier = function(xFormObject, entry) {
    var headerListKeep = new Array();
    var sortable = 1;
    var i = 0 ;
    //idPrefix, label, iconInfo, width, sortable, sortField, resizeable, visible
    headerListKeep[i++] = new ZaListHeaderItem(ZaRetentionPolicy.A2_name, ZaMsg.CLV_Policy_Name_col, null, "200px", sortable, ZaRetentionPolicy.A2_name, true, true);
    headerListKeep[i++] = new ZaListHeaderItem(ZaRetentionPolicy.A2_lifetime, ZaMsg.CLV_Policy_Retention_col, null, "auto", null, null, true, true);

    var headerListPurge = new Array();
    i = 0 ;
    //idPrefix, label, iconInfo, width, sortable, sortField, resizeable, visible
    headerListPurge[i++] = new ZaListHeaderItem(ZaRetentionPolicy.A2_name, ZaMsg.CLV_Policy_Name_col, null, "200px", sortable++, ZaRetentionPolicy.A2_name, true, true);
    headerListPurge[i++] = new ZaListHeaderItem(ZaRetentionPolicy.A2_lifetime, ZaMsg.CLV_Policy_Purge_col, null, "auto", null, null, true, true);
    this.helpMap = {};
    this.tabChoices = new Array();
	var _tab1 = ++this.TAB_INDEX;
	var _tab2, _tab3, _tab4, _tab5, _tab6, _tab7, _tab8;
	
	var headerItems = [	{type:_AJX_IMAGE_, src:"COS_32", label:null,rowSpan:2},
							{type:_OUTPUT_, ref:ZaCos.A_name, label:null,cssClass:"AdminTitle",
                                visibilityChecks:[ZaItem.hasReadPermission], height: 32, rowSpan:2},				
							{type:_OUTPUT_, ref:ZaItem.A_zmailId, label:ZaMsg.NAD_ZmailID,visibilityChecks:[ZaItem.hasReadPermission]},
							{type:_OUTPUT_, ref:ZaItem.A_zmailCreateTimestamp, 
								label:ZaMsg.LBL_zmailCreateTimestamp, labelLocation:_LEFT_,
								getDisplayValue:function() {
									var val = ZaItem.formatServerTime(this.getInstanceValue());
									if(!val)
										return ZaMsg.Server_Time_NA;
									else
										return val;
								},
								visibilityChecks:[ZaItem.hasReadPermission]	
					 		}];
							
	this.tabChoices.push({value:_tab1, label:ZaMsg.TABT_GeneralPage});
	this.helpMap[_tab1] = [location.pathname, ZaUtil.HELP_URL, ZaCosController.helpURL, "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
    if(ZaTabView.isTAB_ENABLED(entry,ZaCosXFormView.FEATURE_TAB_ATTRS, ZaCosXFormView.FEATURE_TAB_RIGHTS)) {
        _tab2 = ++this.TAB_INDEX;
        this.tabChoices.push({value:_tab2, label:ZaMsg.TABT_Features});
        this.helpMap[_tab2] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/user_interface_features.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
    }
    
    if(ZaTabView.isTAB_ENABLED(entry,ZaCosXFormView.PREFERENCES_TAB_ATTRS, ZaCosXFormView.PREFERENCES_TAB_RIGHTS)) {
    	_tab3 = ++this.TAB_INDEX;
        this.tabChoices.push({value:_tab3, label:ZaMsg.TABT_Preferences});
        this.helpMap[_tab3] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/preferences.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
    }
    
    if(ZaTabView.isTAB_ENABLED(entry,ZaCosXFormView.SKIN_TAB_ATTRS, ZaCosXFormView.SKIN_TAB_RIGHTS)) {
       	_tab4 = ++this.TAB_INDEX;
        this.tabChoices.push({value:_tab4, label:ZaMsg.TABT_Themes});
        this.helpMap[_tab4] = [location.pathname, ZaUtil.HELP_URL, "ui_themes/defining_theme_for_the_zmail_web_client_ui.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
    }
    
    var allZimlets = ZaZimlet.getAll(ZaZimlet.EXCLUDE_EXTENSIONS);
    if(allZimlets != null && !AjxUtil.isEmpty(allZimlets.getArray()) && ZaTabView.isTAB_ENABLED(entry,ZaCosXFormView.ZIMLET_TAB_ATTRS, ZaCosXFormView.ZIMLET_TAB_RIGHTS)) {
		_tab5 = ++this.TAB_INDEX;
        this.tabChoices.push({value:_tab5, label:ZaMsg.TABT_Zimlets});
        this.helpMap[_tab5] = [location.pathname, ZaUtil.HELP_URL, "zimlets/about_zimlets.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
    }

    if(ZaTabView.isTAB_ENABLED(entry,ZaCosXFormView.SERVERPOOL_TAB_ATTRS, ZaCosXFormView.SERVERPOOL_TAB_RIGHTS)) {
    	_tab6 = ++this.TAB_INDEX;
        this.tabChoices.push({value:_tab6, label:ZaMsg.TABT_ServerPool});
        this.helpMap[_tab6] = [location.pathname, ZaUtil.HELP_URL, "cos/assigning_accounts_to_different_servers.htm?locid=", AjxEnv.DEFAULT_LOCALE].join("");
    }

    if(ZaTabView.isTAB_ENABLED(entry,ZaCosXFormView.ADVANCED_TAB_ATTRS, ZaCosXFormView.ADVANCED_TAB_RIGHTS)) {
    	_tab7 = ++this.TAB_INDEX;
        this.tabChoices.push({value:_tab7, label:ZaMsg.TABT_Advanced});
        this.helpMap[_tab7] = [location.pathname, ZaUtil.HELP_URL, "managing_accounts/account_advanced_features.htm", "?locid=", AjxEnv.DEFAULT_LOCALE].join("");
    }

    if(ZaTabView.isTAB_ENABLED(entry,ZaCosXFormView.RETENTION_POLICY_TAB_ATTRS, ZaCosXFormView.RETENTION_POLICY_TAB_RIGHTS)) {
        _tab8 = ++this.TAB_INDEX;
        this.tabChoices.push({value:_tab8, label:ZaMsg.TABT_RetentionPolicy});
        this.helpMap[_tab8] = [location.pathname, ZaUtil.HELP_URL, "managing_global_settings/retention_policies.htm?locid=", AjxEnv.DEFAULT_LOCALE].join("");
    }

    var cases = [];
	var case1 = {type:_ZATABCASE_,caseKey:_tab1,numCols:1,
        paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2
    };

    var case1Items = [
		{type:_ZA_TOP_GROUPER_, label:ZaMsg.TABT_GeneralPage, numCols:2,colSizes: ["275px","auto"],
			items:[
				{ref:ZaCos.A_name, type:_INPUT_,
					enableDisableChecks:[[ZaItem.hasRight,ZaCos.RENAME_COS_RIGHT]],
					msgName:ZaMsg.NAD_DisplayName,label:ZaMsg.NAD_DisplayName, labelLocation:_LEFT_, cssClass:"admin_xform_name_input", required:true, width: "30em"},
				ZaItem.descriptionXFormItem ,
                {ref:ZaCos.A_zmailNotes, type:_TEXTAREA_, msgName:ZaMsg.NAD_Notes,label:ZaMsg.NAD_Notes, labelLocation:_LEFT_, labelCssStyle:"vertical-align:top;",width: "30em"}
			]
		}
	];

	case1.items = case1Items;
	cases.push(case1);

    if(_tab2) {
        var case2 = {type:_ZATABCASE_,caseKey:_tab2,numCols:1,colSizes:["auto"],
            paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,
            id:"cos_form_features_tab"};

        var case2Items = [
            {type:_ZA_TOP_GROUPER_,  label:ZaMsg.NAD_zmailMajorFeature, id:"cos_form_features_major",
            	visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
					[
						ZaCos.A_zmailFeatureMailEnabled,
						ZaCos.A_zmailFeatureContactsEnabled,
						ZaCos.A_zmailFeatureCalendarEnabled,
						ZaCos.A_zmailFeatureTasksEnabled,
						ZaCos.A_zmailFeatureBriefcasesEnabled,
						ZaCos.A_zmailFeatureOptionsEnabled
					]]
				],
                items:[
                    {ref:ZaCos.A_zmailFeatureMailEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureMailEnabled,label:ZaMsg.LBL_zmailFeatureMailEnabled,trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureContactsEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureContactsEnabled,label:ZaMsg.LBL_zmailFeatureContactsEnabled,trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureCalendarEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureCalendarEnabled,label:ZaMsg.LBL_zmailFeatureCalendarEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureTasksEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureTaskEnabled,label:ZaMsg.LBL_zmailFeatureTaskEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                    //{ref:ZaCos.A_zmailFeatureNotebookEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureNotebookEnabled,label:ZaMsg.LBL_zmailFeatureNotebookEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureBriefcasesEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureBriefcasesEnabled,label:ZaMsg.LBL_zmailFeatureBriefcasesEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                    //{ref:ZaCos.A_zmailFeatureIMEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureIMEnabled,label:ZaMsg.LBL_zmailFeatureIMEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureOptionsEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureOptionsEnabled,label:ZaMsg.LBL_zmailFeatureOptionsEnabled,  trueValue:"TRUE", falseValue:"FALSE"}
                    //zmailMobile from the extension
                ]
            },
            {type:_ZA_TOP_GROUPER_,  label:ZaMsg.NAD_zmailGeneralFeature, id:"cos_form_features_general",
                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
					[
						ZaCos.A_zmailFeatureTaggingEnabled,
						ZaCos.A_zmailFeatureSharingEnabled,
						ZaCos.A_zmailFeatureChangePasswordEnabled,
						ZaCos.A_zmailFeatureSkinChangeEnabled,
						ZaCos.A_zmailFeatureManageZimlets,
						ZaCos.A_zmailFeatureHtmlComposeEnabled,
						ZaCos.A_zmailFeatureGalEnabled,
						ZaCos.A_zmailFeatureMAPIConnectorEnabled,
						ZaCos.A_zmailFeatureGalAutoCompleteEnabled,
						ZaCos.A_zmailFeatureImportFolderEnabled,
                        ZaCos.A_zmailFeatureExportFolderEnabled,
						ZaCos.A_zmailDumpsterEnabled,
						ZaCos.A_zmailDumpsterPurgeEnabled
					]]
				],
                items:[
                    {ref:ZaCos.A_zmailFeatureTaggingEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureTaggingEnabled,label:ZaMsg.LBL_zmailFeatureTaggingEnabled,trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureSharingEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureSharingEnabled,label:ZaMsg.LBL_zmailFeatureSharingEnabled,trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailExternalSharingEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailExternalSharingEnabled,label:ZaMsg.LBL_zmailExternalSharingEnabled,trueValue:"TRUE", falseValue:"FALSE",
                    	visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaCos.A_zmailFeatureSharingEnabled, "TRUE"]], visibilityChangeEventSources:[ZaCos.A_zmailFeatureSharingEnabled]	
                    },
                    {ref:ZaCos.A_zmailPublicSharingEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailPublicSharingEnabled,label:ZaMsg.LBL_zmailPublicSharingEnabled,trueValue:"TRUE", falseValue:"FALSE",
                    	visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaCos.A_zmailFeatureSharingEnabled, "TRUE"]], visibilityChangeEventSources:[ZaCos.A_zmailFeatureSharingEnabled]	
                    },
                    {ref:ZaCos.A_zmailFeatureChangePasswordEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureChangePasswordEnabled,label:ZaMsg.LBL_zmailFeatureChangePasswordEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureSkinChangeEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureSkinChangeEnabled,label:ZaMsg.LBL_zmailFeatureSkinChangeEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureManageZimlets, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureManageZimlets,label:ZaMsg.LBL_zmailFeatureManageZimlets,  trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureHtmlComposeEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureHtmlComposeEnabled,label:ZaMsg.LBL_zmailFeatureHtmlComposeEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                    //{ref:ZaCos.A_zmailFeatureShortcutAliasesEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureShortcutAliasesEnabled,label:ZaMsg.LBL_zmailFeatureShortcutAliasesEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureGalEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureGalEnabled,label:ZaMsg.LBL_zmailFeatureGalEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureMAPIConnectorEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureMAPIConnectorEnabled,label:ZaMsg.LBL_zmailFeatureMAPIConnectorEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureGalAutoCompleteEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureGalAutoCompleteEnabled,label:ZaMsg.LBL_zmailFeatureGalAutoCompleteEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureImportFolderEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureImportFolderEnabled,label:ZaMsg.LBL_zmailFeatureImportFolderEnabled, trueValue:"TRUE", falseValue:"FALSE"},
		    		{ref:ZaCos.A_zmailFeatureExportFolderEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureExportFolderEnabled,label:ZaMsg.LBL_zmailFeatureExportFolderEnabled, trueValue:"TRUE", falseValue:"FALSE"},
					{ref:ZaCos.A_zmailDumpsterEnabled, type:_CHECKBOX_, msgName:ZaMsg.MSG_zmailDumpsterEnabled,label:ZaMsg.LBL_zmailDumpsterEnabled, trueValue:"TRUE", falseValue:"FALSE"},
					{ref:ZaCos.A_zmailDumpsterPurgeEnabled, type:_CHECKBOX_, msgName:ZaMsg.MSG_zmailDumpsterPurgeEnabled,label:ZaMsg.LBL_zmailDumpsterPurgeEnabled, trueValue:"TRUE", falseValue:"FALSE",
						visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaCos.A_zmailDumpsterEnabled, "TRUE"]], visibilityChangeEventSources:[ZaCos.A_zmailDumpsterEnabled]
					}

                ] 
            },
            {type:_ZA_TOP_GROUPER_,  label:ZaMsg.NAD_zmailMailFeature, id:"cos_form_features_mail",
                enableDisableChecks:[ZaCosXFormView.isMailFeatureEnabled],
                enableDisableChangeEventSources:[ZaCos.A_zmailFeatureMailEnabled],
                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
					[
						ZaCos.A_zmailFeatureMailPriorityEnabled,
						ZaCos.A_zmailFeatureFlaggingEnabled,
						ZaCos.A_zmailImapEnabled,
						ZaCos.A_zmailPop3Enabled,
						ZaCos.A_zmailFeatureImapDataSourceEnabled,
						ZaCos.A_zmailFeaturePop3DataSourceEnabled,
						ZaCos.A_zmailFeatureMailForwardingEnabled,
						ZaCos.A_zmailFeatureMailSendLaterEnabled,
						ZaCos.A_zmailFeatureConversationsEnabled,
						ZaCos.A_zmailFeatureFiltersEnabled,
						ZaCos.A_zmailFeatureOutOfOfficeReplyEnabled,
						ZaCos.A_zmailFeatureNewMailNotificationEnabled,
						ZaCos.A_zmailFeatureMailPollingIntervalPreferenceEnabled,
						ZaCos.A_zmailFeatureIdentitiesEnabled,
						ZaCos.A_zmailFeatureReadReceiptsEnabled
					]]
				],
                items:[
                        {ref:ZaCos.A_zmailFeatureMailPriorityEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureMailPriorityEnabled,label:ZaMsg.LBL_zmailFeatureMailPriorityEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailFeatureFlaggingEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureFlaggingEnabled,label:ZaMsg.LBL_zmailFeatureFlaggingEnabled,  trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailImapEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailImapEnabled,label:ZaMsg.LBL_zmailImapEnabled,trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailPop3Enabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailPop3Enabled,label:ZaMsg.LBL_zmailPop3Enabled,trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailFeatureImapDataSourceEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailExternalImapEnabled,label:ZaMsg.LBL_zmailExternalImapEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailFeaturePop3DataSourceEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailExternalPop3Enabled,label:ZaMsg.LBL_zmailExternalPop3Enabled, trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailFeatureMailForwardingEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureMailForwardingEnabled,label:ZaMsg.LBL_zmailFeatureMailForwardingEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailFeatureMailSendLaterEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureMailSendLaterEnabled, label:ZaMsg.LBL_zmailFeatureMailSendLaterEnabled, trueValue:"TRUE", falseValue:"FALSE"},
			{ref:ZaCos.A_zmailFeatureConversationsEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureConversationsEnabled,label:ZaMsg.LBL_zmailFeatureConversationsEnabled,trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailFeatureFiltersEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureFiltersEnabled,label:ZaMsg.LBL_zmailFeatureFiltersEnabled,trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailFeatureOutOfOfficeReplyEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureOutOfOfficeReplyEnabled,label:ZaMsg.LBL_zmailFeatureOutOfOfficeReplyEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailFeatureNewMailNotificationEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureNewMailNotificationEnabled,label:ZaMsg.LBL_zmailFeatureNewMailNotificationEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailFeatureMailPollingIntervalPreferenceEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureMailPollingIntervalPreferenceEnabled,label:ZaMsg.LBL_zmailFeatureMailPollingIntervalPreferenceEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailFeatureIdentitiesEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureIdentitiesEnabled,label:ZaMsg.LBL_zmailFeatureIdentitiesEnabled,trueValue:"TRUE", falseValue:"FALSE"},
                        {ref:ZaCos.A_zmailFeatureReadReceiptsEnabled, type:_CHECKBOX_,label:ZaMsg.LBL_zmailFeatureReadReceiptsEnabled,trueValue:"TRUE", falseValue:"FALSE"}

                ]
            },
            {
                type: _ZA_TOP_GROUPER_,
                label: ZaMsg.NAD_zmailContactFeature,
                id: "cos_form_features_contact",
                enableDisableChecks: [ZaCosXFormView.isContactsFeatureEnabled],
                enableDisableChangeEventSources: [ZaCos.A_zmailFeatureContactsEnabled],
                visibilityChecks: [
                    [
                        ZATopGrouper_XFormItem.isGroupVisible,
                        [
                            ZaCos.A_zmailFeatureDistributionListFolderEnabled
                        ]
                    ]
                ],
                items: [
                    {
                        ref: ZaCos.A_zmailFeatureDistributionListFolderEnabled,
                        type: _CHECKBOX_,
                        msgName: ZaMsg.MSG_zmailFeatureDistributionListFolderEnabled,
                        label: ZaMsg.LBL_zmailFeatureDistributionListFolderEnabled,
                        trueValue: "TRUE",
                        falseValue: "FALSE"
                    }
                ]
            },
            {type:_ZA_TOP_GROUPER_,  label:ZaMsg.NAD_zmailCalendarFeature, id:"cos_form_features_calendar",
                enableDisableChecks:[ZaCosXFormView.isCalendarFeatureEnabled],
                enableDisableChangeEventSources:[ZaCos.A_zmailFeatureCalendarEnabled],
				visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
					[
						ZaCos.A_zmailFeatureGroupCalendarEnabled,
						//ZaCos.A_zmailFeatureFreeBusyViewEnabled,
                        ZaCos.A_zmailFeatureCalendarReminderDeviceEmailEnabled
					]]
				],
                items:[
                    {ref:ZaCos.A_zmailFeatureGroupCalendarEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureGroupCalendarEnabled,label:ZaMsg.LBL_zmailFeatureGroupCalendarEnabled, trueValue:"TRUE", falseValue:"FALSE"},
		            //{ref:ZaCos.A_zmailFeatureFreeBusyViewEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureFreeBusyViewEnabled, label:ZaMsg.LBL_zmailFeatureFreeBusyViewEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureCalendarReminderDeviceEmailEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureCalendarReminderDeviceEmailEnabled, label:ZaMsg.LBL_zmailFeatureCalendarReminderDeviceEmailEnabled, trueValue:"TRUE", falseValue:"FALSE"}
                ]
            },
            //{type:_ZA_TOP_GROUPER_,  label:ZaMsg.NAD_zmailIMFeature, id:"cos_form_features_im",
            //    visibilityChecks:[ZaCosXFormView.isIMFeatureEnabled],
            //    visibilityChangeEventSources:[ZaCos.A_zmailFeatureIMEnabled],
            //    visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
	//				[
	//					ZaCos.A_zmailFeatureInstantNotify
	//				]]
	//			], 
          //      items:[
          //        {ref:ZaCos.A_zmailFeatureInstantNotify,
          //           type:_CHECKBOX_,
          //           msgName:ZaMsg.LBL_zmailFeatureInstantNotify,
          //           label:ZaMsg.LBL_zmailFeatureInstantNotify,
          //           trueValue:"TRUE",
          //           falseValue:"FALSE"}
		
          //      ]
          //  },
            {type:_ZA_TOP_GROUPER_,  label:ZaMsg.NAD_zmailSearchFeature, id:"cos_form_features_search",
                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
					[
						ZaCos.A_zmailFeatureAdvancedSearchEnabled,
						ZaCos.A_zmailFeatureSavedSearchesEnabled,
						ZaCos.A_zmailFeatureInitialSearchPreferenceEnabled,
						ZaCos.A_zmailFeaturePeopleSearchEnabled
					]]
				],
                items:[
                    {ref:ZaCos.A_zmailFeatureAdvancedSearchEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureAdvancedSearchEnabled,label:ZaMsg.LBL_zmailFeatureAdvancedSearchEnabled,trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureSavedSearchesEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureSavedSearchesEnabled,label:ZaMsg.LBL_zmailFeatureSavedSearchesEnabled,trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeatureInitialSearchPreferenceEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureInitialSearchPreferenceEnabled,label:ZaMsg.LBL_zmailFeatureInitialSearchPreferenceEnabled, trueValue:"TRUE", falseValue:"FALSE"},
                    {ref:ZaCos.A_zmailFeaturePeopleSearchEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeaturePeopleSearchEnabled,label:ZaMsg.LBL_zmailFeaturePeopleSearchEnabled, trueValue:"TRUE", falseValue:"FALSE"}
                ]
            },
            {type:_ZA_TOP_GROUPER_,  label:ZaMsg.NAD_zmailSMIMEFeature, id:"cos_form_features_smime",
                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
					[
						ZaCos.A_zmailFeatureSMIMEEnabled
					]]
				],
                items:[
                    {ref:ZaCos.A_zmailFeatureSMIMEEnabled, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailFeatureSMIMEEnabled,label:ZaMsg.LBL_zmailFeatureSMIMEEnabled, trueValue:"TRUE", falseValue:"FALSE"}
                ]
            }


        ];

        case2.items = case2Items;
        cases.push(case2);
    }

    if(_tab3) {
        var case3 = {type:_ZATABCASE_,caseKey:_tab3, id:"cos_for_prefs_tab",
            paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,
            numCols:1};
        var case3Items = [
            {type:_ZA_TOP_GROUPER_, id:"account_prefs_general", colSizes:["275px","auto"],numCols:2,
                label: ZaMsg.NAD_GeneralOptions,
                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
					[
						ZaCos.A_zmailPrefClientType,
						ZaCos.A_zmailPrefShowSearchString,
						ZaCos.A_zmailPrefMailInitialSearch,
						ZaCos.A_zmailPrefImapSearchFoldersEnabled,
						ZaCos.A_zmailPrefUseKeyboardShortcuts,
						ZaCos.A_zmailPrefWarnOnExit,
						ZaCos.A_zmailPrefAdminConsoleWarnOnExit,
						ZaCos.A_zmailPrefShowSelectionCheckbox,
						//ZaCos.A_zmailPrefIMAutoLogin,
						ZaCos.A_zmailJunkMessagesIndexingEnabled,
						ZaCos.A_zmailPrefLocale
					]]
				],
                items :[
                {ref:ZaCos.A_zmailPrefClientType, type:_OSELECT1_, msgName:ZaMsg.MSG_zmailPrefClientType,
                    label:ZaMsg.LBL_zmailPrefClientType, labelLocation:_LEFT_
                },
                {ref:ZaCos.A_zmailPrefShowSearchString, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefShowSearchString,
                    label:ZaMsg.LBL_zmailPrefShowSearchString, trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefMailInitialSearch, type:_TEXTFIELD_, cssClass:"admin_xform_name_input",
                    msgName:ZaMsg.LBL_zmailPrefMailInitialSearch,label:ZaMsg.LBL_zmailPrefMailInitialSearch,
                    labelLocation:_LEFT_
                },
                {ref:ZaCos.A_zmailPrefImapSearchFoldersEnabled, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefImapSearchFoldersEnabled,
                    label:ZaMsg.LBL_zmailPrefImapSearchFoldersEnabled, trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefUseKeyboardShortcuts, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefUseKeyboardShortcuts,label:ZaMsg.LBL_zmailPrefUseKeyboardShortcuts,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefWarnOnExit, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefWarnOnExit,label:ZaMsg.LBL_zmailPrefWarnOnExit,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefAdminConsoleWarnOnExit, type:_CHECKBOX_,
                    msgName:ZabMsg.LBL_zmailPrefAdminConsoleWarnOnExit,label:ZabMsg.LBL_zmailPrefAdminConsoleWarnOnExit,
                    trueValue:"TRUE", falseValue:"FALSE"
                },  
                {ref:ZaCos.A_zmailPrefShowSelectionCheckbox, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefShowSelectionCheckbox,label:ZaMsg.LBL_zmailPrefShowSelectionCheckbox,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
                //{ref:ZaCos.A_zmailPrefIMAutoLogin, type:_CHECKBOX_,
                //    msgName:ZaMsg.LBL_zmailPrefIMAutoLogin,label:ZaMsg.LBL_zmailPrefIMAutoLogin,
                //    trueValue:"TRUE", falseValue:"FALSE"
                //},
                {ref:ZaCos.A_zmailJunkMessagesIndexingEnabled, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailJunkMessagesIndexingEnabled,
                    label:ZaMsg.LBL_zmailJunkMessagesIndexingEnabled,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefLocale, type:_OSELECT1_,
                     msgName:ZaMsg.LBL_zmailPrefLocale,label:ZaMsg.LBL_zmailPrefLocale,
                     labelLocation:_LEFT_,
                     labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true,
                     choices: ZaSettings.getLocaleChoices()
                }
            ]},
            {type:_ZA_TOP_GROUPER_, id:"cos_prefs_standard_client",label:ZaMsg.NAD_MailOptionsStandardClient,
                colSizes:["275px","auto"],numCols:2,
            	visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
					[
						ZaCos.A_zmailMaxMailItemsPerPage,
						ZaCos.A_zmailPrefMailItemsPerPage
					]]
				],
            	items :[
					{ref:ZaCos.A_zmailMaxMailItemsPerPage, type:_OSELECT1_, msgName:ZaMsg.MSG_zmailMaxMailItemsPerPage,
	                    label:ZaMsg.LBL_zmailMaxMailItemsPerPage, labelLocation:_LEFT_, choices:[10,25,50,100,250,500,1000], editable:true,
	                    inputSize:4
	                },                
	                {ref:ZaCos.A_zmailPrefMailItemsPerPage, type:_OSELECT1_, msgName:ZaMsg.MSG_zmailPrefMailItemsPerPage,
	                    label:ZaMsg.LBL_zmailPrefMailItemsPerPage, labelLocation:_LEFT_
	                }            		
            	]
            },
            {type:_ZA_TOP_GROUPER_, id:"cos_prefs_mail_general", colSizes:["275px","auto"],numCols:2,
                label:ZaMsg.NAD_MailOptions,
                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
					[
						ZaCos.A_zmailPrefMessageViewHtmlPreferred,
						ZaCos.A_zmailPrefDisplayExternalImages,
						ZaCos.A_zmailPrefGroupMailBy,
						ZaCos.A_zmailPrefMailDefaultCharset,
						ZaCos.A_zmailPrefCalendarToasterEnabled,
                                                ZaCos.A_zmailPrefItemsPerVirtualPage
					]]
				],
                items :[
                {ref:ZaCos.A_zmailPrefMessageViewHtmlPreferred, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefMessageViewHtmlPreferred,
                    label:ZaMsg.LBL_zmailPrefMessageViewHtmlPreferred,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefDisplayExternalImages, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefDisplayExternalImages,
                    label:ZaMsg.LBL_zmailPrefDisplayExternalImages,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefGroupMailBy, type:_OSELECT1_, msgName:ZaMsg.LBL_zmailPrefGroupMailBy,
                    label:ZaMsg.LBL_zmailPrefGroupMailBy, labelLocation:_LEFT_
                },
                {ref:ZaCos.A_zmailPrefMailDefaultCharset, type:_OSELECT1_,
                     msgName:ZaMsg.LBL_zmailPrefMailDefaultCharset,label:ZaMsg.LBL_zmailPrefMailDefaultCharset,
                     labelLocation:_LEFT_,
                     labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                },
		{ref:ZaCos.A_zmailPrefMailToasterEnabled, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefMailToasterEnabled,
                    label:ZaMsg.LBL_zmailPrefMailToasterEnabled,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
		        {ref:ZaCos.A_zmailPrefMessageIdDedupingEnabled, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefMessageIdDedupingEnabled,
                    label:ZaMsg.LBL_zmailPrefMessageIdDedupingEnabled,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefItemsPerVirtualPage, 
	       	type:_TEXTFIELD_, 
		msgName:ZaMsg.LBL_zmailPrefItemsPerVirtualPage,
		label:ZaMsg.LBL_zmailPrefItemsPerVirtualPage, 
		labelLocation:_LEFT_
		}

            ]},
            {type:_ZA_TOP_GROUPER_, id:"cos_prefs_mail_receiving",label:ZaMsg.NAD_MailOptionsReceiving,
            	visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
					[
						ZaCos.A_zmailPrefMailSoundsEnabled,
						ZaCos.A_zmailPrefMailFlashIcon,
						ZaCos.A_zmailPrefMailFlashTitle,
						ZaCos.A_zmailPrefMailPollingInterval,
						ZaCos.A_zmailMailMinPollingInterval,
						ZaCos.A_zmailPrefOutOfOfficeCacheDuration,
						ZaCos.A_zmailPrefMailSendReadReceipts
					]]
				],
            	items :[
                {ref:ZaCos.A_zmailPrefMailSoundsEnabled,
                    type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_playSound,
                    label:ZaMsg.LBL_playSound,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefMailFlashIcon,
                    type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_flashIcon,
                    label:ZaMsg.LBL_flashIcon,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefMailFlashTitle,
                    type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_flashTitle,
                    label:ZaMsg.LBL_flashTitle,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefMailPollingInterval, type:_OSELECT1_,
                    msgName:ZaMsg.MSG_zmailPrefMailPollingInterval,
                    label:ZaMsg.LBL_zmailPrefMailPollingInterval, labelLocation:_LEFT_,
                    onChange:ZaCosXFormView.validatePollingInterval,
                    labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                },
                {ref:ZaCos.A_zmailMailMinPollingInterval, type:_LIFETIME_,
                    msgName:ZaMsg.MSG_zmailMailMinPollingInterval,
                    label:ZaMsg.LBL_zmailMailMinPollingInterval, labelLocation:_LEFT_,
                    onChange:ZaCosXFormView.validatePollingInterval,
                    labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                },
                {ref:ZaCos.A_zmailPrefOutOfOfficeCacheDuration, type:_LIFETIME_,
                    msgName:ZaMsg.MSG_zmailPrefOutOfOfficeCacheDuration,
                    label:ZaMsg.LBL_zmailPrefOutOfOfficeCacheDuration, labelLocation:_LEFT_,
                    labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                },
				{ref:ZaCos.A_zmailPrefMailSendReadReceipts, type:_OSELECT1_, label:ZaMsg.LBL_zmailPrefMailSendReadReceipts,labelLocation:_LEFT_,nowrap:false,labelWrap:true}                
            ]},
            {type:_ZA_TOP_GROUPER_, id:"cos_prefs_mail_sending",borderCssClass:"LowPaddedTopGrouperBorder",label:ZaMsg.NAD_MailOptionsSending,
            	visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
					[
						ZaCos.A_zmailPrefSaveToSent,
						ZaCos.A_zmailAllowAnyFromAddress
					]]
				],
            	items :[
                {ref:ZaCos.A_zmailPrefSaveToSent, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailPrefSaveToSent,label:ZaMsg.LBL_zmailPrefSaveToSent,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailAllowAnyFromAddress, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailAllowAnyFromAddress,
                	label:ZaMsg.LBL_zmailAllowAnyFromAddress,
                    trueValue:"TRUE", falseValue:"FALSE"
                }
            ]},
            {type:_ZA_TOP_GROUPER_, id:"cos_prefs_mail_sending",borderCssClass:"LowPaddedTopGrouperBorder",label:ZaMsg.NAD_MailOptionsComposing,
            	visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible, 
					[
						ZaCos.A_zmailPrefComposeInNewWindow,
						ZaCos.A_zmailPrefComposeFormat,
						ZaCos.A_zmailPrefHtmlEditorDefaultFontFamily,
						ZaCos.A_zmailPrefHtmlEditorDefaultFontSize,
						ZaCos.A_zmailPrefHtmlEditorDefaultFontColor,
						ZaCos.A_zmailPrefForwardReplyInOriginalFormat,
						ZaCos.A_zmailPrefMandatorySpellCheckEnabled,
						ZaCos.A_zmailMailSignatureMaxLength,
						ZaCos.A_zmailPrefAutoSaveDraftInterval

					]]
				],
            	items :[
                {ref:ZaCos.A_zmailPrefComposeInNewWindow, type:_CHECKBOX_, msgName:ZaMsg.LBL_zmailPrefComposeInNewWindow,
                    label:ZaMsg.LBL_zmailPrefComposeInNewWindow, trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefComposeFormat, type:_OSELECT1_, msgName:ZaMsg.LBL_zmailPrefComposeFormat,label:ZaMsg.LBL_zmailPrefComposeFormat, labelLocation:_LEFT_},
                {ref:ZaCos.A_zmailPrefHtmlEditorDefaultFontFamily, type:_OSELECT1_, msgName:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontFamily,
                    label:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontFamily, labelLocation:_LEFT_
                },
                {ref:ZaCos.A_zmailPrefHtmlEditorDefaultFontSize, type:_OSELECT1_, msgName:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontSize,
                    label:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontSize, labelLocation:_LEFT_
                },
                {ref:ZaCos.A_zmailPrefHtmlEditorDefaultFontColor, type:_DWT_COLORPICKER_, msgName:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontColor,
                    height: "25px",
                    label:ZaMsg.LBL_zmailPrefHtmlEditorDefaultFontColor, labelLocation:_LEFT_
                },
                {ref:ZaCos.A_zmailPrefForwardReplyInOriginalFormat, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefForwardReplyInOriginalFormat,
                    label:ZaMsg.LBL_zmailPrefForwardReplyInOriginalFormat, trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefMandatorySpellCheckEnabled, type:_CHECKBOX_, 
                	msgName:ZaMsg.LBL_zmailPrefMandatorySpellCheckEnabled,
                	label:ZaMsg.LBL_zmailPrefMandatorySpellCheckEnabled,
                    trueValue:"TRUE", falseValue:"FALSE"
                },
               /* {ref:ZaCos.A_zmailPrefMailSignatureStyle, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefMailSignatureStyle,
                    label:ZaMsg.LBL_zmailPrefMailSignatureStyle,
                    trueValue:"internet", falseValue:"outlook"
                },*/
                {ref:ZaCos.A_zmailMailSignatureMaxLength, type:_TEXTFIELD_,
                    msgName:ZaMsg.LBL_zmailMailSignatureMaxLength,
                    label:ZaMsg.LBL_zmailMailSignatureMaxLength, labelLocation:_LEFT_,
                    cssClass:"admin_xform_number_input"},
                {ref:ZaCos.A_zmailPrefAutoSaveDraftInterval, type:_LIFETIME_,
                    msgName:ZaMsg.MSG_zmailPrefAutoSaveDraftInterval,
                    label:ZaMsg.LBL_zmailPrefAutoSaveDraftInterval, labelLocation:_LEFT_,
                    onChange:ZaCosXFormView.validatePollingInterval,
                    labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                }
            ]},
            {type:_ZA_TOP_GROUPER_, id:"cos_prefs_contacts_general", colSizes:["275px","auto"],numCols:2,
                label: ZaMsg.NAD_ContactsOptions,
                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
					[
						ZaCos.A_zmailPrefAutoAddAddressEnabled,
						ZaCos.A_zmailPrefGalAutoCompleteEnabled
					]]
				],
                items :[
                {ref:ZaCos.A_zmailPrefAutoAddAddressEnabled, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefAutoAddAddressEnabled,
                    label:ZaMsg.LBL_zmailPrefAutoAddAddressEnabled, trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefGalAutoCompleteEnabled, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefGalAutoCompleteEnabled,
                    label:ZaMsg.LBL_zmailPrefGalAutoCompleteEnabled, trueValue:"TRUE", falseValue:"FALSE"
                }
            ]},
            {type:_ZA_TOP_GROUPER_, id:"cos_prefs_calendar_general",colSizes:["275px","auto"],numCols:2,
                label: ZaMsg.NAD_CalendarOptions,
                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
					[
						ZaCos.A_zmailPrefTimeZoneId,
						ZaCos.A_zmailPrefCalendarApptReminderWarningTime,
						ZaCos.A_zmailPrefCalendarInitialView,
						ZaCos.A_zmailPrefCalendarFirstDayOfWeek,
						ZaCos.A_zmailPrefCalendarApptVisibility,
						ZaCos.A_zmailPrefAppleIcalDelegationEnabled,
						ZaCos.A_zmailPrefCalendarShowPastDueReminders,
						ZaCos.A_zmailPrefCalendarToasterEnabled,
						ZaCos.A_zmailPrefCalendarAllowCancelEmailToSelf,
						ZaCos.A_zmailPrefCalendarAllowPublishMethodInvite,
						ZaCos.A_zmailPrefCalendarAllowForwardedInvite,
						ZaCos.A_zmailPrefCalendarReminderFlashTitle,
						ZaCos.A_zmailPrefCalendarReminderSoundsEnabled,
						ZaCos.A_zmailPrefCalendarSendInviteDeniedAutoReply,
						ZaCos.A_zmailPrefCalendarAutoAddInvites,
						ZaCos.A_zmailPrefCalendarNotifyDelegatedChanges,
						ZaCos.A_zmailPrefCalendarAlwaysShowMiniCal,
						ZaCos.A_zmailPrefCalendarUseQuickAdd,
						ZaCos.A_zmailPrefUseTimeZoneListInCalendar
					]]
				],
                items :[
                {ref:ZaCos.A_zmailPrefTimeZoneId, type:_OSELECT1_,
                     msgName:ZaMsg.MSG_zmailPrefTimeZoneId,label:ZaMsg.LBL_zmailPrefTimeZoneId,
                     labelLocation:_LEFT_,
                     labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                },
                {ref:ZaCos.A_zmailPrefCalendarApptReminderWarningTime, type:_OSELECT1_,
                     msgName:ZaMsg.MSG_zmailPrefCalendarApptReminderWarningTime,
                     label:ZaMsg.LBL_zmailPrefCalendarApptReminderWarningTime,
                     labelLocation:_LEFT_,
                     labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                },
                {ref:ZaCos.A_zmailPrefCalendarInitialView, type:_OSELECT1_, msgName:ZaMsg.MSG_zmailPrefCalendarInitialView,
                    label:ZaMsg.LBL_zmailPrefCalendarInitialView, labelLocation:_LEFT_
                },
				{ref:ZaCos.A_zmailPrefCalendarFirstDayOfWeek, type:_OSELECT1_,
                    msgName:ZaMsg.LBL_zmailPrefCalendarFirstDayOfWeek,
                    label:ZaMsg.LBL_zmailPrefCalendarFirstDayOfWeek, labelLocation:_LEFT_,
                    labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                },
				{ref:ZaCos.A_zmailPrefCalendarApptVisibility, type:_OSELECT1_,
                    msgName:ZaMsg.LBL_zmailPrefCalendarApptVisibility,
                    label:ZaMsg.LBL_zmailPrefCalendarApptVisibility, labelLocation:_LEFT_,
                    labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                },
                {ref:ZaCos.A_zmailPrefAppleIcalDelegationEnabled, 
                	type:_CHECKBOX_,
                    msgName:ZaMsg.MSG_zmailPrefAppleIcalDelegationEnabled,
                    label:ZaMsg.LBL_zmailPrefAppleIcalDelegationEnabled,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",
                    nowrap:false,labelWrap:true
                },
                {ref:ZaCos.A_zmailPrefCalendarShowPastDueReminders, 
                	type:_CHECKBOX_,
                    msgName:ZaMsg.MSG_zmailPrefCalendarShowPastDueReminders,
                    label:ZaMsg.LBL_zmailPrefCalendarShowPastDueReminders,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",
                    nowrap:false,labelWrap:true
                },
                {ref:ZaCos.A_zmailPrefCalendarToasterEnabled, 
                	type:_CHECKBOX_,
                    msgName:ZaMsg.MSG_zmailPrefCalendarToasterEnabled,
                    label:ZaMsg.LBL_zmailPrefCalendarToasterEnabled,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",
                    nowrap:false,labelWrap:true
                },
                {ref:ZaCos.A_zmailPrefCalendarAllowCancelEmailToSelf, 
                	type:_CHECKBOX_,
                    msgName:ZaMsg.MSG_zmailPrefCalendarAllowCancelEmailToSelf,
                    label:ZaMsg.LBL_zmailPrefCalendarAllowCancelEmailToSelf,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",
                    nowrap:false,labelWrap:true
                },
                {ref:ZaCos.A_zmailPrefCalendarAllowPublishMethodInvite, 
                	type:_CHECKBOX_,
                    msgName:ZaMsg.MSG_zmailPrefCalendarAllowPublishMethodInvite,
                    label:ZaMsg.LBL_zmailPrefCalendarAllowPublishMethodInvite,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",
                    nowrap:false,labelWrap:true
                },                                
                {ref:ZaCos.A_zmailPrefCalendarAllowForwardedInvite, 
                	type:_CHECKBOX_,
                    msgName:ZaMsg.MSG_zmailPrefCalendarAllowForwardedInvite,
                    label:ZaMsg.LBL_zmailPrefCalendarAllowForwardedInvite,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",
                    nowrap:false,labelWrap:true
                },                                                                
                {ref:ZaCos.A_zmailPrefCalendarReminderFlashTitle, 
                	type:_CHECKBOX_,
                    msgName:ZaMsg.MSG_zmailPrefCalendarReminderFlashTitle,
                    label:ZaMsg.LBL_zmailPrefCalendarReminderFlashTitle,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",
                    nowrap:false,labelWrap:true
                },                                                                
                {ref:ZaCos.A_zmailPrefCalendarReminderSoundsEnabled, 
                	type:_CHECKBOX_,
                    msgName:ZaMsg.MSG_zmailPrefCalendarReminderSoundsEnabled,
                    label:ZaMsg.LBL_zmailPrefCalendarReminderSoundsEnabled,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",
                    nowrap:false,labelWrap:true
                },                                                
                {ref:ZaCos.A_zmailPrefCalendarSendInviteDeniedAutoReply, type:_CHECKBOX_,
                    msgName:ZaMsg.MSG_zmailPrefCalendarSendInviteDeniedAutoReply,
                    label:ZaMsg.LBL_zmailPrefCalendarSendInviteDeniedAutoReply,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",
                    nowrap:false,labelWrap:true
                },                                                
                {ref:ZaCos.A_zmailPrefCalendarAutoAddInvites, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefCalendarAutoAddInvites,label:ZaMsg.LBL_zmailPrefCalendarAutoAddInvites,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                },                                
                {ref:ZaCos.A_zmailPrefCalendarNotifyDelegatedChanges, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefCalendarNotifyDelegatedChanges,label:ZaMsg.LBL_zmailPrefCalendarNotifyDelegatedChanges,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                },
                {ref:ZaCos.A_zmailPrefCalendarAlwaysShowMiniCal, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefCalendarAlwaysShowMiniCal,label:ZaMsg.LBL_zmailPrefCalendarAlwaysShowMiniCal,
                    trueValue:"TRUE", falseValue:"FALSE",
                    labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                },
                {ref:ZaCos.A_zmailPrefCalendarUseQuickAdd, type:_CHECKBOX_, msgName:ZaMsg.NAD_useQuickAdd,
                    label:ZaMsg.LBL_zmailPrefCalendarUseQuickAdd, trueValue:"TRUE", falseValue:"FALSE"
                },
                {ref:ZaCos.A_zmailPrefUseTimeZoneListInCalendar, type:_CHECKBOX_,
                    msgName:ZaMsg.LBL_zmailPrefUseTimeZoneListInCalendar,
                    label:ZaMsg.LBL_zmailPrefUseTimeZoneListInCalendar, trueValue:"TRUE", falseValue:"FALSE"
                }
            ]}
        ];

        case3.items = case3Items;
        cases.push(case3);
    }

    if(_tab4) {
        var case4 = {type:_ZATABCASE_, numCols:1, caseKey:_tab4};
        var case4Items = [
            {type:_SPACER_},
            {type:_GROUP_, numCols:2, colSizes:["275px","auto"],
                items:[
                    {type:_OSELECT1_,
                        ref:ZaCos.A_zmailPrefSkin,
                        msgName:ZaMsg.LBL_zmailPrefSkin,label:ZaMsg.LBL_zmailPrefSkin, labelLocation:_LEFT_,choices:ZaCosXFormView.themeChoices,
                        visibilityChecks:[ZaCosXFormView.gotSkins]
                    }
                ]
            },
            {type:_GROUP_,
                items:[
                    {type:_OUTPUT_,ref:ZaCos.A_zmailPrefSkin,label:ZaMsg.LBL_zmailPrefSkin, labelLocation:_LEFT_,
                        visibilityChecks:[ZaCosXFormView.gotNoSkins]
                    }
                ]
            },
            {type:_SPACER_},
            {type:_GROUP_, numCols:1,colSizes:["auto"],
                items: [
                    {type:_ZASELECT_RADIO_,
                        selectRef:ZaCos.A_zmailAvailableSkin,
                        ref:ZaCos.A_zmailAvailableSkin,
                        choices:ZaCosXFormView.themeChoices,
                        visibilityChecks:[Case_XFormItem.prototype.isCurrentTab, ZaCosXFormView.gotSkins],
                        visibilityChangeEventSources:[ZaModel.currentTab],
                        caseKey:_tab4, caseVarRef:ZaModel.currentTab,
                        radioBoxLabel1:ZaMsg.COS_DontLimitThemes,
                        radioBoxLabel2:ZaMsg.COS_LimitThemesTo
                    },
                    {type:_DWT_ALERT_,style: DwtAlert.WARNING, iconVisible:true,
                        visibilityChecks:[ZaCosXFormView.gotNoSkins,ZaZmailAdmin.isGlobalAdmin],
                        value:ZaMsg.ERROR_CANNOT_FIND_SKINS_FOR_COS
                    },
                    {type:_DWT_ALERT_,style: DwtAlert.WARNING, iconVisible:true,
                        visibilityChecks:[ZaCosXFormView.gotNoSkins, [function(){return !ZaZmailAdmin.isGlobalAdmin()}]],
                        value:ZaMsg.ERROR_CANNOT_FIND_SKINS_FOR_COS_OR_NO_PERM
                    }
                ]
            }
        ];

        case4.items=case4Items;
        cases.push(case4);
    }
    
    if(_tab5) {
        var case5 = {type:_ZATABCASE_, caseKey:_tab5};
        var case5Items = [
            {type:_ZAGROUP_, numCols:1,colSizes:["auto"], border:0,
                items: [
                    {type: _OUTPUT_, value: ZaMsg.COS_LimitZimletsTo},
                    {type:_ZA_ZIMLET_SELECT_COMBO_,
                        selectRef:ZaCos.A_zmailZimletAvailableZimlets,
                        ref:ZaCos.A_zmailZimletAvailableZimlets,
                        choices:ZaCosXFormView.zimletChoices,
                        visibilityChecks:[Case_XFormItem.prototype.isCurrentTab],
                        visibilityChangeEventSources:[ZaModel.currentTab],
                        caseKey:_tab5, caseVarRef:ZaModel.currentTab,
                        selectLabel:"",selectLabelLocation:_NONE_
                    },
					{type: _DWT_ALERT_,
						containerCssStyle: "padding-bottom:0;",
						style: DwtAlert.INFO,
						iconVisible: false,
						content: ZaMsg.Zimlet_Note
					}
                ]
            }
        ];

        case5.items = case5Items;
        cases.push(case5);
    }

    if(_tab6) {
        var case6 = {type:_ZATABCASE_, numCols:1,caseKey:_tab6};
        var case6Items = [
            {type:_ZAGROUP_, numCols:1,colSizes:["auto"],border:0,
                items: [
                    {type:_ZASELECT_RADIO_,
                        selectRef:ZaCos.A_zmailMailHostPool,
                        ref:ZaCos.A_zmailMailHostPool,
                        choices:ZaApp.getInstance().getServerIdListChoices(),
                        visibilityChecks:[Case_XFormItem.prototype.isCurrentTab],
                        visibilityChangeEventSources:[ZaModel.currentTab],
                        caseKey:_tab6, caseVarRef:ZaModel.currentTab,
                        radioBoxLabel1:ZaMsg.ServerPool_Donotlimit,
                        radioBoxLabel2:ZaMsg.COS_LimitServersTo
                    }
                ]
            }
        ];

        case6.items = case6Items;
        cases.push(case6);
    }
    
    if(_tab7) {
        var case7 = {type:_ZATABCASE_, numCols:1, colSizes:["auto"], caseKey:_tab7,
            paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,
            id:"cos_form_advanced_tab"};
        var case7Items = [
            { type: _DWT_ALERT_,
              style: DwtAlert.INFO,
	      iconVisible: false,
	      content: ZaMsg.NAD_ZERO_UNLIMETED
	    },      
            {type:_ZA_TOP_GROUPER_, id:"cos_attachment_settings",
                label:ZaMsg.NAD_AttachmentsGrouper,visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                                                          			[ZaCos.A_zmailAttachmentsBlocked]]],
                items :[
                    {ref:ZaCos.A_zmailAttachmentsBlocked, type:_CHECKBOX_,  msgName:ZaMsg.NAD_RemoveAllAttachments,label:ZaMsg.NAD_RemoveAllAttachments, labelLocation:_LEFT_, trueValue:"TRUE", falseValue:"FALSE",labelCssClass:"xform_label",  align:_LEFT_}
                ]
            },
            {type:_ZA_TOP_GROUPER_, id:"cos_quota_settings",
                label:ZaMsg.NAD_QuotaGrouper,
                items: [
                    {ref:ZaCos.A_zmailMailForwardingAddressMaxLength, type:_TEXTFIELD_, 
                    	msgName:ZaMsg.MSG_zmailMailForwardingAddressMaxLength,
                    	label:ZaMsg.LBL_zmailMailForwardingAddressMaxLength, 
                    	labelLocation:_LEFT_, 
                    	cssClass:"admin_xform_number_input"
                    },
                    {ref:ZaCos.A_zmailMailForwardingAddressMaxNumAddrs, type:_TEXTFIELD_, 
                    	msgName:ZaMsg.MSG_zmailMailForwardingAddressMaxNumAddrs,
                    	label:ZaMsg.LBL_zmailMailForwardingAddressMaxNumAddrs, 
                    	labelLocation:_LEFT_, 
                    	cssClass:"admin_xform_number_input"
                    },                
                    {ref:ZaCos.A_zmailMailQuota, type:_TEXTFIELD_, 
                    	msgName:ZaMsg.MSG_zmailMailQuota,
                    	label:ZaMsg.LBL_zmailMailQuota, labelLocation:_LEFT_, cssClass:"admin_xform_number_input"
                    },
                    {ref:ZaCos.A_zmailContactMaxNumEntries, type:_TEXTFIELD_, 
                    	msgName:ZaMsg.MSG_zmailContactMaxNumEntries,
                    	label:ZaMsg.LBL_zmailContactMaxNumEntries, 
                    	labelLocation:_LEFT_, 
                    	cssClass:"admin_xform_number_input"
                    },
                    {ref:ZaCos.A_zmailQuotaWarnPercent, type:_INPUT_, msgName:ZaMsg.MSG_zmailQuotaWarnPercent,
                    	label:ZaMsg.LBL_zmailQuotaWarnPercent, labelLocation:_LEFT_, cssClass:"admin_xform_number_input"
                    },
                    {ref:ZaCos.A_zmailQuotaWarnInterval, type:_LIFETIME_, msgName:ZaMsg.MSG_zmailQuotaWarnInterval,
                    	label:ZaMsg.LBL_zmailQuotaWarnInterval, labelLocation:_LEFT_
                    },
                    {ref:ZaCos.A_zmailQuotaWarnMessage, type:_TEXTAREA_, msgName:ZaMsg.MSG_zmailQuotaWarnMessage,
                    	label:ZaMsg.LBL_zmailQuotaWarnMessage, labelLocation:_LEFT_, labelCssStyle:"vertical-align:top;",width: "30em"
                    }
                ]
            },

            {type:_ZA_TOP_GROUPER_, id:"cos_datasourcepolling_settings",
                label:ZaMsg.NAD_DataSourcePolling,
                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                                  [ZaCos.A_zmailDataSourceMinPollingInterval, ZaCos.A_zmailDataSourcePop3PollingInterval,ZaCos.A_zmailDataSourceImapPollingInterval,
                                   ZaCos.A_zmailDataSourceCalendarPollingInterval,ZaCos.A_zmailDataSourceRssPollingInterval,ZaCos.A_zmailDataSourceCaldavPollingInterval
                                  ]]],
                items: [
                    {ref:ZaCos.A_zmailDataSourceMinPollingInterval, type:_LIFETIME_,
                        msgName:ZaMsg.MSG_zmailDataSourceMinPollingInterval,
                        label:ZaMsg.LBL_zmailDataSourceMinPollingInterval, labelLocation:_LEFT_,
                        onChange:ZaCosXFormView.validatePollingInterval,
                        labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                    },
                    {ref:ZaCos.A_zmailDataSourcePop3PollingInterval, type:_LIFETIME_,
                        msgName:ZaMsg.MSG_zmailDataSourcePop3PollingInterval,
                        label:ZaMsg.LBL_zmailDataSourcePop3PollingInterval, labelLocation:_LEFT_,
                        onChange:ZaCosXFormView.validatePollingInterval,
                        labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                    },
                    {ref:ZaCos.A_zmailDataSourceImapPollingInterval, type:_LIFETIME_,
                        msgName:ZaMsg.MSG_zmailDataSourceImapPollingInterval,
                        label:ZaMsg.LBL_zmailDataSourceImapPollingInterval, labelLocation:_LEFT_,
                        onChange:ZaCosXFormView.validatePollingInterval,
                        labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                    },
                    {ref:ZaCos.A_zmailDataSourceCalendarPollingInterval, type:_LIFETIME_,
                        msgName:ZaMsg.MSG_zmailDataSourceCalendarPollingInterval,
                        label:ZaMsg.LBL_zmailDataSourceCalendarPollingInterval, labelLocation:_LEFT_,
                        onChange:ZaCosXFormView.validatePollingInterval,
                        labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                    },
                    {ref:ZaCos.A_zmailDataSourceRssPollingInterval, type:_LIFETIME_,
                        msgName:ZaMsg.MSG_zmailDataSourceRssPollingInterval,
                        label:ZaMsg.LBL_zmailDataSourceRssPollingInterval, labelLocation:_LEFT_,
                        onChange:ZaCosXFormView.validatePollingInterval,
                        labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                    },
                    {ref:ZaCos.A_zmailDataSourceCaldavPollingInterval, type:_LIFETIME_,
                        msgName:ZaMsg.MSG_zmailDataSourceCaldavPollingInterval,
                        label:ZaMsg.LBL_zmailDataSourceCaldavPollingInterval, labelLocation:_LEFT_,
                        onChange:ZaCosXFormView.validatePollingInterval,
                        labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true
                    }
                ]
            },
            
            {type:_ZA_TOP_GROUPER_, id:"cos_proxyalloweddomain_settings",
             	label: ZaMsg.NAD_ProxyAllowedDomains,
                visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                                  [ZaCos.A_zmailProxyAllowedDomains]]],
             	items:[
		    {
			ref: ZaCos.A_zmailProxyAllowedDomains,  
			msgName:ZaMsg.MSG_zmailProxyAllowedDomains,
    			label:ZaMsg.LBL_zmailProxyAllowedDomains, labelLocation:_LEFT_, 
   			labelCssStyle:"vertical-align:top;",
    			type:_REPEAT_,
    			align:_LEFT_,
    			repeatInstance:"",
                        addButtonLabel:ZaMsg.NAD_ProxyAddAllowedDomain ,
                        removeButtonLabel: ZaMsg.NAD_ProxyRemoveAllowedDomain,
    			showAddButton:true,
    			showRemoveButton:true,
    			showAddOnNextRow:true,
 
        		items: [
                		{ref:".", type:_TEXTFIELD_,
                         	 enableDisableChecks:[] ,
                                 visibilityChecks:[],
                                 width: "15em"}
                               ]
                    }
                ]
            },

            {type:_ZA_TOP_GROUPER_,id:"cos_password_settings",
                label:ZaMsg.NAD_PasswordGrouper,
                items: [ 
                    { type: _DWT_ALERT_,
                      containerCssStyle: "padding-bottom:0;",
                      style: DwtAlert.INFO,
                      iconVisible:  false,
                      content: ((ZaCosXFormView.isAllAuthfromInternal())?ZaMsg.Alert_InternalPassword:ZaMsg.Alert_ExternalPassword)
                    },
                    {ref:ZaCos.A_zmailPasswordLocked, type:_CHECKBOX_,
                        msgName:ZaMsg.NAD_PwdLocked,
                        label:ZaMsg.NAD_PwdLocked,
                        trueValue:"TRUE", falseValue:"FALSE",
			visibilityChecks:[],enableDisableChecks:[[ZaCosXFormView.isAllAuthfromInternal]]
                    },
                    {ref:ZaCos.A_zmailMinPwdLength, 
			type:_TEXTFIELD_, msgName:ZaMsg.MSG_zmailMinPwdLength,
			label:ZaMsg.LBL_zmailMinPwdLength, labelLocation:_LEFT_, cssClass:"admin_xform_number_input",
			visibilityChecks:[ZaItem.hasWritePermission],enableDisableChecks:[[ZaCosXFormView.isAllAuthfromInternal]]
		    },
                    {ref:ZaCos.A_zmailMaxPwdLength, 
			type:_TEXTFIELD_, msgName:ZaMsg.MSG_zmailMaxPwdLength,
			label:ZaMsg.LBL_zmailMaxPwdLength, labelLocation:_LEFT_, cssClass:"admin_xform_number_input",
			visibilityChecks:[ZaItem.hasWritePermission],enableDisableChecks:[[ZaCosXFormView.isAllAuthfromInternal]]
		    },

                    {ref:ZaCos.A_zmailPasswordMinUpperCaseChars, 
			type:_TEXTFIELD_, msgName:ZaMsg.MSG_zmailPasswordMinUpperCaseChars,
			label:ZaMsg.LBL_zmailPasswordMinUpperCaseChars, labelLocation:_LEFT_, cssClass:"admin_xform_number_input",
			visibilityChecks:[ZaItem.hasWritePermission],enableDisableChecks:[[ZaCosXFormView.isAllAuthfromInternal]]
		    },
                    {ref:ZaCos.A_zmailPasswordMinLowerCaseChars, 
			type:_TEXTFIELD_, msgName:ZaMsg.MSG_zmailPasswordMinLowerCaseChars,
			label:ZaMsg.LBL_zmailPasswordMinLowerCaseChars, labelLocation:_LEFT_, cssClass:"admin_xform_number_input",
			visibilityChecks:[ZaItem.hasWritePermission],enableDisableChecks:[[ZaCosXFormView.isAllAuthfromInternal]]
		    },
                    {ref:ZaCos.A_zmailPasswordMinPunctuationChars, 
			type:_TEXTFIELD_, msgName:ZaMsg.MSG_zmailPasswordMinPunctuationChars,
			label:ZaMsg.LBL_zmailPasswordMinPunctuationChars, labelLocation:_LEFT_, cssClass:"admin_xform_number_input",
			visibilityChecks:[ZaItem.hasWritePermission],enableDisableChecks:[[ZaCosXFormView.isAllAuthfromInternal]]
	 	    },
                    {ref:ZaCos.A_zmailPasswordMinNumericChars, 
			type:_TEXTFIELD_, msgName:ZaMsg.MSG_zmailPasswordMinNumericChars,
			label:ZaMsg.LBL_zmailPasswordMinNumericChars, labelLocation:_LEFT_, cssClass:"admin_xform_number_input",
			visibilityChecks:[ZaItem.hasWritePermission],enableDisableChecks:[[ZaCosXFormView.isAllAuthfromInternal]]
		    },
                    {ref:ZaCos.A_zmailPasswordMinDigitsOrPuncs, 
			type:_TEXTFIELD_, msgName:ZaMsg.MSG_zmailPasswordMinDigitsOrPuncs,
			label:ZaMsg.LBL_zmailPasswordMinDigitsOrPuncs, labelLocation:_LEFT_, cssClass:"admin_xform_number_input",
			visibilityChecks:[ZaItem.hasWritePermission],enableDisableChecks:[[ZaCosXFormView.isAllAuthfromInternal]]
		    },

                    {ref:ZaCos.A_zmailMinPwdAge, 
			type:_TEXTFIELD_, msgName:ZaMsg.MSG_passMinAge,
			label:ZaMsg.LBL_passMinAge, labelLocation:_LEFT_, cssClass:"admin_xform_number_input",
			visibilityChecks:[],enableDisableChecks:[[ZaCosXFormView.isAllAuthfromInternal]]
		    },
                    {ref:ZaCos.A_zmailMaxPwdAge, 
			type:_TEXTFIELD_, msgName:ZaMsg.MSG_passMaxAge,
			label:ZaMsg.LBL_passMaxAge, labelLocation:_LEFT_, cssClass:"admin_xform_number_input",
			visibilityChecks:[],enableDisableChecks:[[ZaCosXFormView.isAllAuthfromInternal]]
		    },
                    {ref:ZaCos.A_zmailEnforcePwdHistory, 
			type:_TEXTFIELD_, msgName:ZaMsg.MSG_zmailEnforcePwdHistory,
			label:ZaMsg.LBL_zmailEnforcePwdHistory, labelLocation:_LEFT_, cssClass:"admin_xform_number_input",
			visibilityChecks:[],enableDisableChecks:[[ZaCosXFormView.isAllAuthfromInternal]]
		    }
                ]
            },
            {type:_ZA_TOP_GROUPER_, id:"cos_password_lockout_settings",
                label:ZaMsg.NAD_FailedLoginGrouper,
                items :[

                    {ref:ZaCos.A_zmailPasswordLockoutEnabled, type:_CHECKBOX_,
                        msgName:ZaMsg.LBL_zmailPasswordLockoutEnabled,
                        label:ZaMsg.LBL_zmailPasswordLockoutEnabled,
                        trueValue:"TRUE", falseValue:"FALSE"
                    },
                    {ref:ZaCos.A_zmailPasswordLockoutMaxFailures, type:_TEXTFIELD_,
                        enableDisableChecks: [ZaCosXFormView.isPasswordLockoutEnabled],
                        enableDisableChangeEventSources:[ZaCos.A_zmailPasswordLockoutEnabled],
                        label:ZaMsg.LBL_zmailPasswordLockoutMaxFailures,
                        subLabel:ZaMsg.TTP_zmailPasswordLockoutMaxFailuresSub,
                        msgName:ZaMsg.MSG_zmailPasswordLockoutMaxFailures,
                        labelLocation:_LEFT_,
                        cssClass:"admin_xform_number_input"
                    },
                    {ref:ZaCos.A_zmailPasswordLockoutDuration, type:_LIFETIME_,
                        enableDisableChecks: [ZaCosXFormView.isPasswordLockoutEnabled],
                        enableDisableChangeEventSources:[ZaCos.A_zmailPasswordLockoutEnabled],
                        label:ZaMsg.LBL_zmailPasswordLockoutDuration,
                        subLabel:ZaMsg.TTP_zmailPasswordLockoutDurationSub,
                        msgName:ZaMsg.MSG_zmailPasswordLockoutDuration,
                        labelLocation:_LEFT_,
                        textFieldCssClass:"admin_xform_number_input"
                    },
                    {ref:ZaCos.A_zmailPasswordLockoutFailureLifetime, type:_LIFETIME_,
                        enableDisableChecks: [ZaCosXFormView.isPasswordLockoutEnabled],
                        enableDisableChangeEventSources:[ZaCos.A_zmailPasswordLockoutEnabled],
                        label:ZaMsg.LBL_zmailPasswordLockoutFailureLifetime,
                        subLabel:ZaMsg.TTP_zmailPasswordLockoutFailureLifetimeSub,
                        msgName:ZaMsg.MSG_zmailPasswordLockoutFailureLifetime,
                        labelLocation:_LEFT_,
                        textFieldCssClass:"admin_xform_number_input",
                        labelCssStyle:"white-space:normal;",
                        nowrap:false,labelWrap:true
                    }
                ]
            },
            {type:_ZA_TOP_GROUPER_, id:"cos_timeout_settings",
                label:ZaMsg.NAD_TimeoutGrouper,
                items: [
                    {ref:ZaCos.A_zmailAdminAuthTokenLifetime, type:_LIFETIME_, msgName:ZaMsg.MSG_zmailAdminAuthTokenLifetime,label:ZaMsg.LBL_zmailAdminAuthTokenLifetime,labelLocation:_LEFT_},
                    {ref:ZaCos.A_zmailAuthTokenLifetime, type:_LIFETIME_, msgName:ZaMsg.MSG_zmailAuthTokenLifetime,label:ZaMsg.LBL_zmailAuthTokenLifetime,labelLocation:_LEFT_},
                    {ref:ZaCos.A_zmailMailIdleSessionTimeout, type:_LIFETIME_, msgName:ZaMsg.MSG_zmailMailIdleSessionTimeout,label:ZaMsg.LBL_zmailMailIdleSessionTimeout,labelLocation:_LEFT_},
                    {ref:ZaCos.A_zmailDumpsterUserVisibleAge, type:_LIFETIME_, msgName:ZaMsg.MSG_zmailDumpsterUserVisibleAge, label:ZaMsg.LBL_zmailDumpsterUserVisibleAge,
                        visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaCos.A_zmailDumpsterEnabled, "TRUE"]], visibilityChangeEventSources:[ZaCos.A_zmailDumpsterEnabled]
                    }
                ]
            },
            {type:_ZA_TOP_GROUPER_, id:"cos_mail_retention_settings",
                label:ZaMsg.NAD_MailRetentionGrouper,
                items: [
                    { type: _DWT_ALERT_,
                      containerCssStyle: "padding-bottom:0;",
                      style: DwtAlert.INFO,
                      iconVisible: false,
                      content: ZaMsg.Alert_MailRetention
                    },
                    {ref:ZaCos.A_zmailMailMessageLifetime, type:_LIFETIME2_, msgName:ZaMsg.MSG_zmailMailMessageLifetime,label:ZaMsg.LBL_zmailMailMessageLifetime,labelLocation:_LEFT_},
                    {ref:ZaCos.A_zmailMailTrashLifetime, type:_LIFETIME1_, msgName:ZaMsg.MSG_zmailMailTrashLifetime,label:ZaMsg.LBL_zmailMailTrashLifetime, labelLocation:_LEFT_},
                    {ref:ZaCos.A_zmailMailSpamLifetime, type:_LIFETIME1_, msgName:ZaMsg.MSG_zmailMailSpamLifetime,label:ZaMsg.LBL_zmailMailSpamLifetime, labelLocation:_LEFT_},
                    {ref:ZaCos.A_zmailMailDumpsterLifetime, type:_LIFETIME1_, msgName:ZaMsg.MSG_zmailMailDumpsterLifetime, label:ZaMsg.LBL_zmailMailDumpsterLifetime,
                        visibilityChecks:[[ZaItem.hasReadPermission], [XForm.checkInstanceValue, ZaCos.A_zmailDumpsterEnabled, "TRUE"], [XForm.checkInstanceValue, ZaCos.A_zmailDumpsterPurgeEnabled, "TRUE"]],
                        visibilityChangeEventSources:[ZaCos.A_zmailDumpsterEnabled, ZaCos.A_zmailDumpsterPurgeEnabled]
                    }
                ]
            },
            {type:_ZA_TOP_GROUPER_,
                label:ZaMsg.NAD_InteropGrouper,visibilityChecks:[[ZATopGrouper_XFormItem.isGroupVisible,
                                                      			[ZaCos.A_zmailFreebusyExchangeUserOrg]]],
                items: [
                    {ref:ZaCos.A_zmailFreebusyExchangeUserOrg, type:_TEXTFIELD_,
                        msgName:ZaMsg.LBL_zmailFreebusyExchangeUserOrg, width: "250px",
                        label:ZaMsg.LBL_zmailFreebusyExchangeUserOrg,labelLocation:_LEFT_
                    }
                ]
            } ,
            {type: _SPACER_ , height: "10px" }  //add some spaces at the bottom of the page
        ];

        case7.items = case7Items;
        cases.push(case7);
    }

    if(_tab8) {
        var case8 =
        {type: _SUPER_TABCASE_, caseKey:_tab8,
            paddingStyle:"padding-left:15px;", width:"98%", cellpadding:2,
            colSizes:["100%"],numCols:1,id:"cos_retentionpolicy_tab",
            loadDataMethods: [ZaCosXFormView.prototype.loadRetentionPolicies],
            items: [
                {type:_GROUP_, numCols:2, colSizes:["20px","auto"], width:"600px",
                    cssStyle:"margin-left:10px;",
                    items: [
                        {type:_SPACER_, height:"10px"},
                        {ref:ZaCos.A2_retentionPoliciesKeepInherited, type:_CHECKBOX_,
                            onChange: ZaCosXFormView.inheritCOSRetentionPolicies,
                            msgName:ZaMsg.LBL_POLICY_COS_INHERIT_PROMPT,label:ZaMsg.LBL_POLICY_COS_INHERIT_PROMPT,
                            trueValue:"TRUE", falseValue:"FALSE", labelLocation:_RIGHT_}
                    ]
                },
                {type:_ZA_TOP_GROUPER_, id:"cos_form_keep_p_group",width:"98%",
                    numCols:1,colSizes:["auto"],label:ZaMsg.Glb_RetentionPolicies,
                    cssStyle:"margin:10px;padding-bottom:0;",
                    items: [
                        {ref:ZaCos.A2_retentionPoliciesKeep, type:_DWT_LIST_, height:"200", width:"99%",
                            preserveSelection:false, multiselect:true,cssClass: "DLSource",
                            headerList:headerListKeep, widgetClass:ZaRetentionPolicyListView,
                            onSelection:ZaCosXFormView.retentionSelectionListener,
                            valueChangeEventSources:[ZaCos.A2_retentionPoliciesKeep]
                        },
                        {type:_GROUP_, numCols:5, colSizes:["100px","auto","100px","auto","100px"], width:"350px",
                            cssStyle:"margin:10px;padding-bottom:0;",
                            items: [
                                {type:_DWT_BUTTON_, label:ZaMsg.TBB_Delete,width:"100px",
                                    onActivate:"ZaCosXFormView.deleteButtonListener.call(this);",
                                    enableDisableChangeEventSources:[ZaCos.A2_retentionPoliciesKeep_Selection,ZaCos.A2_retentionPoliciesKeepInherited],
                                    enableDisableChecks:[[
                                        function() {
                                            var sel = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesKeep_Selection);
                                            return sel && sel.length > 0;
                                        }],
                                        [XForm.checkInstanceValue, ZaCos.A2_retentionPoliciesKeepInherited, "TRUE"]
                                    ]
                                },
                                {type:_CELLSPACER_},
                                {type:_DWT_BUTTON_, label:ZaMsg.TBB_Edit,width:"100px",
                                    onActivate:"ZaCosXFormView.editButtonListener.call(this);",
                                    enableDisableChangeEventSources:[ZaCos.A2_retentionPoliciesKeep_Selection, ZaCos.A2_retentionPoliciesKeepInherited],
                                    enableDisableChecks:[[
                                        function() {
                                            var sel = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesKeep_Selection);
                                            return sel && sel.length == 1;
                                        }],
                                        [XForm.checkInstanceValue, ZaCos.A2_retentionPoliciesKeepInherited, "TRUE"]
                                    ]
                                },
                                {type:_CELLSPACER_},
                                {type:_DWT_BUTTON_, label:ZaMsg.NAD_Add,width:"100px",
                                    enableDisableChangeEventSources:[ZaCos.A2_retentionPoliciesKeepInherited],
                                    enableDisableChecks:[[XForm.checkInstanceValue, ZaCos.A2_retentionPoliciesKeepInherited, "TRUE"]],
                                    onActivate:"ZaCosXFormView.addButtonListener.call(this);"
                                }
                            ]
                        }
                    ]
                },
                {type:_ZA_TOP_GROUPER_, id:"cos_form_purge_p_group",width:"98%",
                    numCols:1,colSizes:["auto"],label:ZaMsg.Glb_DisposalPolicies,
                    cssStyle:"margin:10px;padding-bottom:0;",
                    items: [
                        {ref:ZaCos.A2_retentionPoliciesPurge, type:_DWT_LIST_, height:"200", width:"99%",
                            preserveSelection:false, multiselect:true,cssClass: "DLSource",
                            headerList:headerListPurge, widgetClass:ZaRetentionPolicyListView,
                            onSelection:ZaCosXFormView.purgeSelectionListener,
                            valueChangeEventSources:[ZaCos.A2_retentionPoliciesPurge]
                        },
                        {type:_GROUP_, numCols:5, colSizes:["100px","auto","100px","auto","100px"], width:"350px",
                            cssStyle:"margin:10px;padding-bottom:0;",
                            items: [
                                {type:_DWT_BUTTON_, label:ZaMsg.TBB_Delete,width:"100px",
                                    onActivate:"ZaCosXFormView.deleteButtonListener.call(this, 1);",
                                    enableDisableChangeEventSources:[ZaCos.A2_retentionPoliciesPurge_Selection, ZaCos.A2_retentionPoliciesKeepInherited],
                                    enableDisableChecks:[
                                        [function() {
                                            var sel = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesPurge_Selection);
                                            return sel && sel.length > 0;
                                        }],
                                        [XForm.checkInstanceValue, ZaCos.A2_retentionPoliciesKeepInherited, "TRUE"]
                                    ]
                                },
                                {type:_CELLSPACER_},
                                {type:_DWT_BUTTON_, label:ZaMsg.TBB_Edit,width:"100px",
                                    onActivate:"ZaCosXFormView.editButtonListener.call(this, 1);",
                                    enableDisableChangeEventSources:[ZaCos.A2_retentionPoliciesPurge_Selection, ZaCos.A2_retentionPoliciesKeepInherited],
                                    enableDisableChecks:[
                                        [function() {
                                            var sel = this.getForm().getInstanceValue(ZaCos.A2_retentionPoliciesPurge_Selection);
                                            return sel && sel.length == 1;
                                        }],
                                        [XForm.checkInstanceValue, ZaCos.A2_retentionPoliciesKeepInherited, "TRUE"]
                                    ]
                                },
                                {type:_CELLSPACER_},
                                {type:_DWT_BUTTON_, label:ZaMsg.NAD_Add,width:"100px",
                                    enableDisableChangeEventSources:[ZaCos.A2_retentionPoliciesKeepInherited],
                                    enableDisableChecks:[[XForm.checkInstanceValue, ZaCos.A2_retentionPoliciesKeepInherited, "TRUE"]],
                                    onActivate:"ZaCosXFormView.addButtonListener.call(this,1);"
                                }
                            ]
                        }
                    ]
                }
            ]
        };
        cases.push (case8) ;
    }
    
    xFormObject.tableCssStyle = "width:100%;overflow:auto;";
	xFormObject.items = [
			{type:_GROUP_, cssClass:"ZmSelectedHeaderBg", colSpan:"*", id:"xform_header",
				items: [
					{type:_GROUP_,	numCols:4,colSizes:["32px","*","80px","*"],items:headerItems}
				]
			},
			{type:_TAB_BAR_,  ref:ZaModel.currentTab,choices:this.tabChoices,cssClass:"ZaTabBar", id:"xform_tabbar", cssStyle:"display:none;"},
			{type:_SWITCH_, align:_LEFT_, valign:_TOP_, items:cases}
	];		
};
ZaTabView.XFormModifiers["ZaCosXFormView"].push(ZaCosXFormView.myXFormModifier);

ZaCosXFormView.prototype.getTabChoices =
function() {
    return this.tabChoices;
}

ZaCosXFormView.isAllAuthfromInternal =
function() {
	var isAll = true;  // is all external?
	var domainList = ZaCosXFormView._domainList;
	if(!domainList) return isAll;
	for(var i = 0; i < domainList.length && isAll; i ++) {
		var dom = domainList[i];
		if(dom.attrs[ZaDomain.A_AuthMech] == ZaDomain.AuthMech_zmail)
			isAll = false; 
	}
        return !isAll;
}

ZaCosXFormView.validatePollingInterval =
function (value, event, form) {
	DBG.println(AjxDebug.DBG3, "The polling interval = " + value);
	var instance = form.getInstance ();
	this.setInstanceValue(value);
	var prefPollingInterval = instance.attrs[ZaCos.A_zmailPrefMailPollingInterval] ;
	var minPollingInterval = instance.attrs[ZaCos.A_zmailMailMinPollingInterval] ;
	var prefPollingIntervalItem = form.getItemsById (ZaCos.A_zmailPrefMailPollingInterval)[0];
	try {
		if (ZaUtil.getLifeTimeInSeconds(prefPollingInterval) < ZaUtil.getLifeTimeInSeconds(minPollingInterval)){
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

