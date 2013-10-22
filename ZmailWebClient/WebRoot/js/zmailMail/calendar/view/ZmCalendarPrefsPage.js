/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
 * Creates a preferences page for managing calendar prefs
 * @constructor
 * @class
 * This class adds specialized handling for managing calendar ACLs that control whether
 * events can be added to the user's calendar, and who can see the user's free/busy info.
 *
 * @author Conrad Damon
 *
 * @param {DwtControl}	parent			the containing widget
 * @param {Object}	section			which page we are
 * @param {ZmPrefController}	controller		the prefs controller
 * 
 * @extends		ZmPreferencesPage
 * 
 * @private
 */
ZmCalendarPrefsPage = function(parent, section, controller) {

	ZmPreferencesPage.apply(this, arguments);

	ZmCalendarPrefsPage.TEXTAREA = {};
	ZmCalendarPrefsPage.TEXTAREA[ZmSetting.CAL_FREE_BUSY_ACL]	= ZmSetting.CAL_FREE_BUSY_ACL_USERS;
	ZmCalendarPrefsPage.TEXTAREA[ZmSetting.CAL_INVITE_ACL]		= ZmSetting.CAL_INVITE_ACL_USERS;
	ZmCalendarPrefsPage.SETTINGS	= [ZmSetting.CAL_FREE_BUSY_ACL, ZmSetting.CAL_INVITE_ACL];
	ZmCalendarPrefsPage.RIGHTS		= [ZmSetting.RIGHT_VIEW_FREE_BUSY, ZmSetting.RIGHT_INVITE];

	this._currentSelection = {};
	this._initAutocomplete();
};

ZmCalendarPrefsPage.prototype = new ZmPreferencesPage;
ZmCalendarPrefsPage.prototype.constructor = ZmCalendarPrefsPage;

ZmCalendarPrefsPage.prototype.isZmCalendarPrefsPage = true;
ZmCalendarPrefsPage.prototype.toString = function() { return "ZmCalendarPrefsPage"; };

ZmCalendarPrefsPage.prototype.reset =
function(useDefaults) {
	ZmPreferencesPage.prototype.reset.apply(this, arguments);
	var settings = ZmCalendarPrefsPage.SETTINGS;
	for (var i = 0; i < settings.length; i++) {
		this._checkPermTextarea(settings[i]);
    }
    if(this._workHoursControl) {
        this._workHoursControl.reset();
    }
};

ZmCalendarPrefsPage.prototype.showMe =
function() {
	var active = appCtxt.getActiveAccount();
	this._isAclSupported = !appCtxt.multiAccounts || appCtxt.isFamilyMbox || (!active.isMain && active.isZimbraAccount);
	ZmPreferencesPage.prototype.showMe.call(this);
    //this._getWorkHoursHtml();

};

ZmCalendarPrefsPage.prototype._setupCustom = function (id, setup, value) {
    switch(id) {
        case "CAL_WORKING_HOURS":
            var el = document.getElementById([this._htmlElId, id].join("_"));
            if(el) {
                this._workHoursControl = new ZmWorkHours(this, id, value, "WorkHours");
                this._workHoursControl.reparentHtmlElement(el);
            }
            this.setFormObject(id, this._workHoursControl);
            break;
    }
    
};

ZmCalendarPrefsPage.prototype._getTemplateData =
function() {
	var data = ZmPreferencesPage.prototype._getTemplateData.call(this);
	data.domain = appCtxt.getUserDomain();
	data.isAclSupported = this._isAclSupported;

	return data;
};

ZmCalendarPrefsPage.prototype._createControls =
function() {
	// First, load the user's ACL
	this._acl = appCtxt.getACL();
	var respCallback = new AjxCallback(this, this._handleResponseLoadACL);
	if (this._acl && !this._acl._loaded) {
		this._acl.load(respCallback);
	}
};

ZmCalendarPrefsPage.prototype._handleResponseLoadACL =
function() {
	var settings = ZmCalendarPrefsPage.SETTINGS;
	var rights = ZmCalendarPrefsPage.RIGHTS;
	for (var i = 0; i < settings.length; i++) {
		this._setACLValues(settings[i], rights[i]);
	}
	ZmPreferencesPage.prototype._createControls.apply(this, arguments);
	for (var i = 0; i < settings.length; i++) {
		var textarea = this.getFormObject(ZmCalendarPrefsPage.TEXTAREA[settings[i]]);
		if (textarea && this._acList) {
			this._acList.handle(textarea.getInputElement());
			this._checkPermTextarea(settings[i]);
		}
	}
};

// Sets values for calendar ACL-related settings.
ZmCalendarPrefsPage.prototype._setACLValues =
function(setting, right) {
	var gt = this._acl.getGranteeType(right);
	this._currentSelection[setting] = gt;

	appCtxt.set(setting, gt);
	var list = this._acl.getGrantees(right);
	var textDisplay = list.join("\n");
	appCtxt.set(ZmCalendarPrefsPage.TEXTAREA[setting], textDisplay);

	this._acl.getGranteeType(right);
	// Set up the preference initial value (derived from ACL data) so that the
	// pref is not incorrectly detected as dirty in the _checkSection call.
	var pref = appCtxt.getSettings().getSetting(setting);
	pref.origValue = this._currentSelection[setting];
	pref = appCtxt.getSettings().getSetting(ZmCalendarPrefsPage.TEXTAREA[setting]);
	pref.origValue = textDisplay;
};

/**
 * ZmPrefView.getChangedPrefs() doesn't quite work for performing a dirty check on this page since
 * it only returns true if a changed setting is stored in LDAP (has a 'name' property in its ZmSetting
 * object). This override checks the ACL-related settings to see if they changed.
 */
ZmCalendarPrefsPage.prototype.isDirty =
function(section, list, errors) {
	var dirty = this._controller.getPrefsView()._checkSection(section, this, true, true, list, errors);
    if(!dirty && this._workHoursControl) {
        dirty = this._workHoursControl.isDirty();
    }
	if (!dirty && this._isAclSupported) {
		this._findACLChanges();
		dirty = (this._grants.length || this._revokes.length);
	}
	return dirty;
};

ZmCalendarPrefsPage.prototype._checkPermTextarea =
function(setting) {
	var radioGroup = this.getFormObject(setting);
	var val = radioGroup && radioGroup.getValue();
	var textarea = this.getFormObject(ZmCalendarPrefsPage.TEXTAREA[setting]);
	if (textarea && val) {
		textarea.setEnabled(val == ZmSetting.ACL_USER);
	}
};

ZmCalendarPrefsPage.prototype._setupRadioGroup =
function(id, setup, value) {
	var control = ZmPreferencesPage.prototype._setupRadioGroup.apply(this, arguments);
	var radioGroup = this.getFormObject(id);
	if (id == ZmSetting.CAL_FREE_BUSY_ACL || id == ZmSetting.CAL_INVITE_ACL) {
		radioGroup.addSelectionListener(new AjxListener(this, this._checkPermTextarea, [id]));
	}
	return control;
};

ZmCalendarPrefsPage.prototype.getPreSaveCallback =
function() {
	return new AjxCallback(this, this._preSave);
};

ZmCalendarPrefsPage.prototype.getPostSaveCallback =
function() {
	return new AjxCallback(this, this._postSave);
};

ZmCalendarPrefsPage.prototype._postSave =
function(callback) {
    var settings = appCtxt.getSettings();
    var workHoursSetting = settings.getSetting(ZmSetting.CAL_WORKING_HOURS);
    workHoursSetting._notify(ZmEvent.E_MODIFY);
	if (this._workHoursControl) {
		this._workHoursControl.reloadWorkHours(this._workHoursControl.getValue());
	}
    if (callback instanceof AjxCallback) {
		callback.run();
	}
};

ZmCalendarPrefsPage.prototype._preSave =
function(callback) {
	if (this._isAclSupported) {
		this._findACLChanges();
	}
	if (callback) {
		callback.run();
	}
};

ZmCalendarPrefsPage.prototype._findACLChanges =
function() {
	var settings = ZmCalendarPrefsPage.SETTINGS;
	var rights = ZmCalendarPrefsPage.RIGHTS;
	this._grants = [];
	this._revokes = [];
	for (var i = 0; i < settings.length; i++) {
		var result = this._getACLChanges(settings[i], rights[i]);
		this._grants = this._grants.concat(result.grants);
		this._revokes = this._revokes.concat(result.revokes);
	}
};

ZmCalendarPrefsPage.prototype._getACLChanges =
function(setting, right) {

	var curType = appCtxt.get(setting);
	var curUsers = (curType == ZmSetting.ACL_USER) ? this._acl.getGrantees(right) : [];
	var curUsersInfo = (curType == ZmSetting.ACL_USER) ? this._acl.getGranteesInfo(right) : [];
	var zidHash = {};
	for (var i = 0; i < curUsersInfo.length; i++) {
		zidHash[curUsersInfo[i].grantee] = curUsersInfo[i].zid;
	}
	var curHash = AjxUtil.arrayAsHash(curUsers);

	var radioGroup = this.getFormObject(setting);
		var newType = radioGroup.getValue();
	var radioGroupChanged = (newType != this._currentSelection[setting]);

	var newUsers = [];
	if (newType == ZmSetting.ACL_USER) {
		var textarea = this.getFormObject(ZmCalendarPrefsPage.TEXTAREA[setting]);
		var val = textarea.getValue();
		var users = val.split(/[\n,;]/);
		for (var i = 0; i < users.length; i++) {
			var user = AjxStringUtil.trim(users[i]);
			if (!user) { continue; }
			if (zidHash[user] != user) {
				user = (user.indexOf('@') == -1) ? [user, appCtxt.getUserDomain()].join('@') : user;
			}
			newUsers.push(user);
		}
		newUsers.sort();
	}

	var newHash = AjxUtil.arrayAsHash(newUsers);

	var contacts = AjxDispatcher.run("GetContacts");
	var grants = [];
	var revokes = [];
	if (newUsers.length > 0) {
		for (var i = 0; i < newUsers.length; i++) {
			var user = newUsers[i];
			if (!curHash[user]) {
				var contact = contacts.getContactByEmail(user);
				var gt = (contact && contact.isGroup()) ? ZmSetting.ACL_GROUP : ZmSetting.ACL_USER;
				var ace = new ZmAccessControlEntry({grantee:user, granteeType:gt, right:right});
				grants.push(ace);
			}
		}
	}
	if (curUsers.length > 0) {
		for (var i = 0; i < curUsers.length; i++) {
			var user = curUsers[i];
			var zid = (curUsersInfo[i]) ? curUsersInfo[i].zid : null;
			if (!newHash[user]) {
				var contact = contacts.getContactByEmail(user);
				var gt = (contact && contact.isGroup()) ? ZmSetting.ACL_GROUP : ZmSetting.ACL_USER;
				var ace = new ZmAccessControlEntry({grantee: (user!=zid) ? user : null, granteeType:gt, right:right, zid: zid});
				revokes.push(ace);
			}
		}
	}

	var userAdded = (grants.length > 0);
	var userRemoved = (revokes.length > 0);

	var denyAll = (radioGroupChanged && (newType == ZmSetting.ACL_NONE));

	if ((newType == ZmSetting.ACL_USER) && (userAdded || userRemoved || radioGroupChanged)) {
        revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_DOMAIN));
		revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_AUTH));
		revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_PUBLIC));
		
		if (newUsers.length == 0) {
			denyAll = true;
		}
	}

	// deny all
	if (denyAll) {
		revokes = [];
		grants = [];

        revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_DOMAIN));
		revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_USER));
		revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_GROUP));
		revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_PUBLIC));

		//deny all
		var ace = new ZmAccessControlEntry({granteeType: ZmSetting.ACL_AUTH, right:right, negative: true});
		grants.push(ace);
	}

	//allow all users
	if (radioGroupChanged && (newType == ZmSetting.ACL_PUBLIC)) {
		grants = [];
		revokes = [];

		//grant all
		var ace = new ZmAccessControlEntry({granteeType: ZmSetting.ACL_PUBLIC, right:right});
		grants.push(ace);

		//revoke all other aces
		revokes = this._acl.getACLByGranteeType(right, ZmSetting.ACL_USER);
        revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_DOMAIN));
		revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_GROUP));
		revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_AUTH));
	}

	if (radioGroupChanged && (newType == ZmSetting.ACL_AUTH)) {
		grants = [];
		revokes = [];

		//grant all
		var ace = new ZmAccessControlEntry({granteeType: ZmSetting.ACL_AUTH, right:right});
		grants.push(ace);

		//revoke all other aces
		revokes = this._acl.getACLByGranteeType(right, ZmSetting.ACL_USER);
        revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_DOMAIN));
		revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_GROUP));
		revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_PUBLIC));
	}

    if (radioGroupChanged && (newType == ZmSetting.ACL_DOMAIN)) {
		grants = [];
		revokes = [];

		//grant all
		var ace = new ZmAccessControlEntry({granteeType: ZmSetting.ACL_DOMAIN, right:right, grantee:appCtxt.getUserDomain()});
		grants.push(ace);

		//revoke all other aces
		revokes = this._acl.getACLByGranteeType(right, ZmSetting.ACL_USER);
        revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_GROUP));
		revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_AUTH));
		revokes = revokes.concat(this._acl.getACLByGranteeType(right, ZmSetting.ACL_PUBLIC));
	}

	return {grants:grants, revokes:revokes};
};

ZmCalendarPrefsPage.prototype.addCommand =
function(batchCmd) {
    if (this._isAclSupported) {
        var respCallback = new AjxCallback(this, this._handleResponseACLChange);
        if (this._revokes.length) {
            this._acl.revoke(this._revokes, respCallback, batchCmd);
        }
        if (this._grants.length) {
            this._acl.grant(this._grants, respCallback, batchCmd);
        }
    }

    if(this._workHoursControl) {
        if(this._workHoursControl.isValid()) {
            var value = this._workHoursControl.getValue(),
                    soapDoc = AjxSoapDoc.create("ModifyPrefsRequest", "urn:zimbraAccount"),
                    node = soapDoc.set("pref", value),
                    respCallback = new AjxCallback(this, this._postSaveBatchCmd, [value]);
            node.setAttribute("name", "zimbraPrefCalendarWorkingHours");
            batchCmd.addNewRequestParams(soapDoc, respCallback);
        }
        else {
            throw new AjxException(ZmMsg.calendarWorkHoursInvalid);
        }
    }
};

ZmCalendarPrefsPage.prototype._postSaveBatchCmd =
function(value) {
    appCtxt.set(ZmSetting.CAL_WORKING_HOURS, value);
    if(this._workHoursControl) {
        if(this._workHoursControl.getDaysChanged()) {
            this._workHoursControl.setDaysChanged(false);
            var cd = appCtxt.getYesNoMsgDialog();
            cd.reset();
            cd.registerCallback(DwtDialog.YES_BUTTON, this._newWorkHoursYesCallback, this, [skin, cd]);
            cd.setMessage(ZmMsg.workingDaysRestart, DwtMessageDialog.WARNING_STYLE);
            cd.popup();
        }
    }
};

ZmCalendarPrefsPage.prototype._newWorkHoursYesCallback =
function(skin, dialog) {
	dialog.popdown();
	window.onbeforeunload = null;
	var url = AjxUtil.formatUrl();
	DBG.println(AjxDebug.DBG1, "Working days change, redirect to: " + url);
	ZmZimbraMail.sendRedirect(url); // redirect to self to force reload
};

ZmCalendarPrefsPage.prototype._handleResponseACLChange =
function(aces) {
	if (aces && !(aces instanceof Array)) { aces = [aces]; }

	if (aces && aces.length) {
		for (var i = 0; i < aces.length; i++) {
			var ace = aces[i];
			var setting = (ace.right == ZmSetting.RIGHT_INVITE) ? ZmSetting.CAL_INVITE_ACL : ZmSetting.CAL_FREE_BUSY_ACL;
			this._setACLValues(setting, ace.right);
		}
	}
};

ZmCalendarPrefsPage.prototype._initAutocomplete =
function() {
	if (appCtxt.get(ZmSetting.CONTACTS_ENABLED) && appCtxt.get(ZmSetting.GAL_AUTOCOMPLETE_ENABLED)) {
		var contactsClass = appCtxt.getApp(ZmApp.CONTACTS);
		var contactsLoader = contactsClass.getContactList;
		var params = {
			dataClass:	appCtxt.getAutocompleter(),
			separator:	"",
			matchValue:	ZmAutocomplete.AC_VALUE_EMAIL,
			options:	{galOnly:true},
			contextId:	this.toString()
		};
		this._acList = new ZmAutocompleteListView(params);
	}
};

/**
 * Creates the WorkHours custom control
 * 
 * @constructor
 * @param parent
 * @param id
 * @param value work hours string
 * @param templateId
 */
ZmWorkHours = function(parent, id, value, templateId) {
	DwtComposite.call(this, {parent:parent});


    this._workDaysCheckBox = [];
    this._startTimeSelect = null;
    this._endTimeSelect = null;
    this._customDlg = null;
    this._customBtn = null;
	this._tabGroup = new DwtTabGroup(this._htmlElId);
    this.reloadWorkHours(value);
    this._radioNormal = null;
    this._radioCustom = null;
    this._daysChanged = false;
    this._setContent(templateId);
};

ZmWorkHours.STR_DAY_SEP = ",";
ZmWorkHours.STR_TIME_SEP = ":";

ZmWorkHours.prototype = new DwtComposite;
ZmWorkHours.prototype.constructor = ZmWorkHours;

ZmWorkHours.prototype.toString =
function() {
	return "ZmWorkHours";
};

ZmWorkHours.prototype.getTabGroupMember =
function() {
	return this._tabGroup;
};

ZmWorkHours.prototype.getTabGroup = ZmWorkHours.prototype.getTabGroupMember;

ZmWorkHours.prototype.reloadWorkHours =
function(value) {
    value = value || appCtxt.get(ZmSetting.CAL_WORKING_HOURS);
    var workHours = this._workHours = this.decodeWorkingHours(value),
        dayIdx = new Date().getDay();
    this._startTime = new Date();
    this._endTime = new Date();
    this._startTime.setHours(workHours[dayIdx].startTime/100, workHours[dayIdx].startTime%100, 0);
    this._endTime.setHours(workHours[dayIdx].endTime/100, workHours[dayIdx].endTime%100, 0);
    this._isCustom = this._isCustomTimeSet();
    if(this._customDlg) {
        this._customDlg.reloadWorkHours(workHours);
    }
};

ZmWorkHours.prototype.reset =
function() {
    if (!this.isDirty()) { return; }
    var i,
        workHours = this._workHours,
        startTime = new Date();

    this._startTimeSelect.set(this._startTime);
    this._endTimeSelect.set(this._endTime);

    for (i=0;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
        this._workDaysCheckBox[i].setSelected(workHours[i].isWorkingDay);
    }
};

ZmWorkHours.prototype.isDirty =
function() {
	var i,
        isDirty = false,
        workHours = this._workHours,
        tf = new AjxDateFormat("HHmm"),
        startInputTime = tf.format(this._startTimeSelect.getValue()),
        endInputTime = tf.format(this._endTimeSelect.getValue()),
        isCustom = this._radioCustom.isSelected();


    if(!isCustom) {
        for (i=0;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
            if(this._workDaysCheckBox[i].isSelected() != workHours[i].isWorkingDay) {
                this.setDaysChanged(true);
                isDirty = true;
                break;
            }
        }

        if(startInputTime != workHours[0].startTime || endInputTime != workHours[0].endTime) {
            isDirty = true;
        }
    }
    else if(this._customDlg) {
        isDirty = this._customDlg.isDirty();
    }

    if(isCustom != this._isCustom) {
        isDirty = true;
    }

	return isDirty;
};

ZmWorkHours.prototype.setDaysChanged =
function(value) {
    var isCustom = this._radioCustom.isSelected();
    if(isCustom && this._customDlg) {
        this._customDlg.setDaysChanged(value);
    }
    else {
        this._daysChanged = value;
    }
};

ZmWorkHours.prototype.getDaysChanged =
function() {
    var isCustom = this._radioCustom.isSelected();
    if(isCustom && this._customDlg) {
        return this._customDlg.getDaysChanged();
    }
    else {
        return this._daysChanged;
    }
};

ZmWorkHours.prototype.isValid =
function() {
    var tf = new AjxDateFormat("HHmm"),
        startInputTime = tf.format(this._startTimeSelect.getValue()),
        endInputTime = tf.format(this._endTimeSelect.getValue());
    if(this._radioCustom.isSelected() && this._customDlg) {
        return this._customDlg.isValid();        
    }
    return startInputTime < endInputTime ? true : false;
};

ZmWorkHours.prototype.decodeWorkingHours =
function(wHrsString) {
    if(wHrsString === 0) {
        return [];
    }
	var wHrsPerDay = wHrsString.split(ZmWorkHours.STR_DAY_SEP),
        i,
        wHrs = [],
        wDay,
        w,
        idx;

    for(i=0; i<wHrsPerDay.length; i++) {
        wDay = wHrsPerDay[i].split(ZmWorkHours.STR_TIME_SEP);
        w = {};
        idx = wDay[0];
        if(wDay[1] === "Y") {
            w.isWorkingDay = true;
        }
        else {
            w.isWorkingDay = false;
        }
        w.startTime = wDay[2];
        w.endTime = wDay[3];

        wHrs[idx-1] = w;
    }
    return wHrs;
};

ZmWorkHours.prototype.encodeWorkingHours =
function() {
    var i,
        tf = new AjxDateFormat("HHmm"),
        startInputTime = tf.format(this._startTimeSelect.getValue()),
        endInputTime = tf.format(this._endTimeSelect.getValue()),
        dayStr,
        wDaysStr = [];

    for (i=0;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
        dayStr = [];
        dayStr.push(i+1);
        if(this._workDaysCheckBox[i].isSelected()) {
            dayStr.push("Y");
        }
        else {
            dayStr.push("N");
        }
        dayStr.push(startInputTime);
        dayStr.push(endInputTime);
        wDaysStr.push(dayStr.join(ZmWorkHours.STR_TIME_SEP));
    }
    return wDaysStr.join(ZmWorkHours.STR_DAY_SEP);
};

ZmWorkHours.prototype._isCustomTimeSet =
function() {
    var i,
        w = this._workHours;
    for (i=1;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
        if(w[i].startTime != w[i-1].startTime || w[i].endTime != w[i-1].endTime) {
            return true;
        }
    }
    return false;
};

ZmWorkHours.prototype._closeCustomDialog =
function(value) {
    this._customDlg.popdown();
};

ZmWorkHours.prototype._openCustomizeDlg =
function() {
    if(!this._customDlg) {
        this._customDlg = new ZmCustomWorkHoursDlg(appCtxt.getShell(), "CustomWorkHoursDlg", this._workHours);
        this._customDlg.initialize(this._workHours);
        this._customDlg.setButtonListener(DwtDialog.OK_BUTTON, new AjxListener(this, this._closeCustomDialog, [true]));
        this._customDlg.setButtonListener(DwtDialog.CANCEL_BUTTON, new AjxListener(this, this._closeCustomDialog, [false]));
    }
    this._customDlg.popup();
};

ZmWorkHours.prototype.getValue =
function() {
    if(this._radioCustom.isSelected()) { 
        if(this._customDlg) {
            return this._customDlg.getValue();
        }
        else {
            this._radioNormal.setSelected(true);
            this._radioCustom.setSelected(false);
            this._toggleNormalCustom();
        }
    }
    return this.encodeWorkingHours();
};

ZmWorkHours.prototype._toggleNormalCustom =
function() {
    var i;
    if(this._radioNormal.isSelected()) {
        this._startTimeSelect.setEnabled(true);
        this._endTimeSelect.setEnabled(true);
        for (i=0;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
            this._workDaysCheckBox[i].setEnabled(true);
        }
        this._customBtn.setEnabled(false);
    }
    else {
        for (i=0;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
            this._workDaysCheckBox[i].setEnabled(false);
        }
        this._startTimeSelect.setEnabled(false);
        this._endTimeSelect.setEnabled(false);
        this._customBtn.setEnabled(true);
    }
};

ZmWorkHours.prototype._setContent =
function(templateId) {
	var i,
        el,
        checkbox,
        customBtn,
        workHours = this._workHours,
        startTimeSelect,
        endTimeSelect,
        radioNormal,
        radioCustom,
        radioGroup,
        selectedRadioId,
        radioIds = {},
        radioName = this._htmlElId + "_normalCustom",
        isCustom = this._isCustom;
    
    this.getHtmlElement().innerHTML = AjxTemplate.expand("prefs.Pages#"+templateId, {id:this._htmlElId});
    //fill the containers for the work days and work time
    el = document.getElementById(this._htmlElId + "_CAL_WORKING_START_TIME");
    startTimeSelect = new ZmTimeInput(this, ZmTimeInput.START, el);
    startTimeSelect.set(this._startTime);
    startTimeSelect.setEnabled(!isCustom);
    this._startTimeSelect = startTimeSelect;

    el = document.getElementById(this._htmlElId + "_CAL_WORKING_END_TIME");
    endTimeSelect = new ZmTimeInput(this, ZmTimeInput.END, el);
    endTimeSelect.set(this._endTime);
    endTimeSelect.setEnabled(!isCustom);
    this._endTimeSelect = endTimeSelect;

    customBtn = new DwtButton({parent:this, parentElement:(this._htmlElId + "_CAL_CUSTOM_WORK_HOURS")});
    customBtn.setText(ZmMsg.calendarCustomBtnTitle);
    customBtn.addSelectionListener(new AjxListener(this, this._openCustomizeDlg));
    customBtn.setEnabled(isCustom);
    this._customBtn = customBtn;

    radioNormal = new DwtRadioButton({parent:this, name:radioName, parentElement:(this._htmlElId + "_CAL_WORKING_HOURS_NORMAL")});
    radioNormal.setSelected(!isCustom);
    radioIds[radioNormal.getInputElement().id] = radioNormal;
    
    radioCustom = new DwtRadioButton({parent:this, name:radioName, parentElement:(this._htmlElId + "_CAL_WORKING_HOURS_CUSTOM")});
    radioCustom.setSelected(isCustom);
    radioIds[radioCustom.getInputElement().id] = radioCustom;

    radioGroup = new DwtRadioButtonGroup(radioIds, isCustom ? radioCustom.getInputElement().id : radioNormal.getInputElement().id );
    
    radioGroup.addSelectionListener(new AjxListener(this, this._toggleNormalCustom));
    this._radioCustom = radioCustom;
    this._radioNormal = radioNormal;
    for (i=0;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
        checkbox = new DwtCheckbox({parent:this, parentElement:(this._htmlElId + "_CAL_WORKING_DAY_" + i)});
        checkbox.setText(AjxDateUtil.WEEKDAY_MEDIUM[i]);
	    checkbox.setSelected(workHours[i].isWorkingDay);
        checkbox.setEnabled(!isCustom)
        this._workDaysCheckBox.push(checkbox);
        this.parent._addControlTabIndex(el, checkbox);
    }
};

/**
 * Create the custom work hours dialog box
 * @param parent
 * @param templateId
 * @param workHours the work hours parsed array
 */
ZmCustomWorkHoursDlg = function (parent, templateId, workHours) {
    DwtDialog.call(this, {parent:parent});
    this._workHours = workHours;
    this._startTimeSelect = [];
    this._endTimeSelect = [];
    this._workDaysCheckBox = [];
    var contentHtml = AjxTemplate.expand("prefs.Pages#"+templateId, {id:this._htmlElId});
    this.setContent(contentHtml);
	this.setTitle(ZmMsg.calendarCustomDlgTitle);
    this._daysChanged = false;
};

ZmCustomWorkHoursDlg.prototype = new DwtDialog;
ZmCustomWorkHoursDlg.prototype.constructor = ZmCustomWorkHoursDlg;

ZmCustomWorkHoursDlg.prototype.initialize = function(workHours) {
    var i,
        el,
        checkbox,
        startTimeSelect,
        endTimeSelect,
        inputTime;
    
    workHours = workHours || this._workHours;

    for (i=0;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
        //fill the containers for the work days and work time
        el = document.getElementById(this._htmlElId + "_CAL_WORKING_START_TIME_"+i);
        startTimeSelect = new ZmTimeInput(this, ZmTimeInput.START, el);
        inputTime = new Date();
        inputTime.setHours(workHours[i].startTime/100, workHours[i].startTime%100, 0);
        startTimeSelect.set(inputTime);
        startTimeSelect.setEnabled(workHours[i].isWorkingDay);
        this._startTimeSelect.push(startTimeSelect);


        inputTime = new Date();
        inputTime.setHours(workHours[i].endTime/100, workHours[i].endTime%100, 0);
        el = document.getElementById(this._htmlElId + "_CAL_WORKING_END_TIME_"+i);
        endTimeSelect = new ZmTimeInput(this, ZmTimeInput.END, el);
        endTimeSelect.set(inputTime);
        endTimeSelect.setEnabled(workHours[i].isWorkingDay);
        this._endTimeSelect.push(endTimeSelect);


        checkbox = new DwtCheckbox({parent:this, parentElement:(this._htmlElId + "_CAL_WORKING_DAY_" + i)});
        checkbox.setText(AjxDateUtil.WEEKDAY_MEDIUM[i]);
	    checkbox.setSelected(workHours[i].isWorkingDay);
        checkbox.addSelectionListener(new AjxListener(this, this._setTimeInputEnabled, [i, checkbox]));
        this._workDaysCheckBox.push(checkbox);
    }
};

ZmCustomWorkHoursDlg.prototype.reloadWorkHours =
function(workHours) {
    workHours = workHours || appCtxt.get(ZmSetting.CAL_WORKING_HOURS);
    this._workHours = workHours;
};

ZmCustomWorkHoursDlg.prototype._setTimeInputEnabled =
function(idx, checkbox) {
    this._startTimeSelect[idx].setEnabled(checkbox.isSelected());
    this._endTimeSelect[idx].setEnabled(checkbox.isSelected());
};

ZmCustomWorkHoursDlg.prototype.isDirty =
function() {
    var i,
        workHours = this._workHours,
        tf = new AjxDateFormat("HHmm"),
        startInputTime,
        endInputTime;

    for (i=0;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
        if(this._workDaysCheckBox[i].isSelected() != workHours[i].isWorkingDay) {
            this.setDaysChanged(true);
            return true;
        }
    }
    for (i=0;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
        startInputTime = tf.format(this._startTimeSelect[i].getValue());
        endInputTime = tf.format(this._endTimeSelect[i].getValue());
        
        if(startInputTime != workHours[i].startTime
        || endInputTime != workHours[i].endTime
        || this._workDaysCheckBox[i].isSelected() != workHours[i].isWorkingDay) {
            return true;
        }
    }
    return false;
};

ZmCustomWorkHoursDlg.prototype.popup =
function() {
	DwtDialog.prototype.popup.call(this);
};

ZmCustomWorkHoursDlg.prototype.isValid =
function() {
    var i,
        tf = new AjxDateFormat("HHmm"),
        startInputTime,
        endInputTime;

    for (i=0;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
        if(this._workDaysCheckBox[i].isSelected()) {
            startInputTime = tf.format(this._startTimeSelect[i].getValue());
            endInputTime = tf.format(this._endTimeSelect[i].getValue());

            if(startInputTime > endInputTime) {
                return false;
            }
        }
    }
    return true;

};

ZmCustomWorkHoursDlg.prototype.getValue =
function() {
    var i,
        tf = new AjxDateFormat("HHmm"),
        startInputTime,
        endInputTime,
        dayStr,
        wDaysStr = [];

    for (i=0;i<AjxDateUtil.WEEKDAY_MEDIUM.length; i++) {
        startInputTime = tf.format(this._startTimeSelect[i].getValue());
        endInputTime = tf.format(this._endTimeSelect[i].getValue());
        dayStr = [];
        dayStr.push(i+1);
        if(this._workDaysCheckBox[i].isSelected()) {
            dayStr.push("Y");
        }
        else {
            dayStr.push("N");
        }
        dayStr.push(startInputTime);
        dayStr.push(endInputTime);
        wDaysStr.push(dayStr.join(ZmWorkHours.STR_TIME_SEP));
    }
    return wDaysStr.join(ZmWorkHours.STR_DAY_SEP);
};

ZmCustomWorkHoursDlg.prototype.setDaysChanged =
function(value) {
    this._daysChanged = value;
};

ZmCustomWorkHoursDlg.prototype.getDaysChanged =
function() {
    return this._daysChanged;
};


