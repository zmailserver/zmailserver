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
 * Creates a generic quick add dialog (which basically mean it has different 
 * than regular dialogs). See "DwtSemiModalDialog" in Ajax widget templates
 * for cosmetics.
 * @constructor
 * @class
 * This class represents a modal dialog which has at least a title and the 
 * standard buttons (OK/Cancel).
 * widgets (i.e. buttons, etc) as necessary.
 * <p>
 * Dialogs always hang off the main shell since their stacking order is managed 
 * through z-index.
 *
 * @author Parag Shah
 * 
 * @param {ZmShell}		parent				parent widget (the shell)
 * 
 * @extends		ZmQuickAddDialog
 * 
 */
ZmApptQuickAddDialog = function(parent) {
	// create extra "more details" button to be added at the footer of DwtDialog
    var moreDetailsButton = new DwtDialog_ButtonDescriptor(ZmApptQuickAddDialog.MORE_DETAILS_BUTTON,
                                                           ZmMsg.moreDetails, DwtDialog.ALIGN_LEFT);
    ZmQuickAddDialog.call(this, parent, null, null, [moreDetailsButton]);
	DBG.timePt("ZmQuickAddDialog constructor", true);

	AjxDispatcher.run("GetResources");
    AjxDispatcher.require("CalendarCore");

    var app = appCtxt.getApp(ZmApp.CALENDAR);
    this._fbCache = new ZmFreeBusyCache(app);

	var html = AjxTemplate.expand("calendar.Appointment#ZmApptQuickAddDialog", {id: this._htmlElId});
	this.setContent(html);

	this.setTitle(ZmMsg.quickAddAppt);
	DBG.timePt("create content");
	this._locations = [];
	this._calendarOrgs = {};

	this._createDwtObjects();
	this._cacheFields();
	this._addEventHandlers();
	this._button[ZmApptQuickAddDialog.MORE_DETAILS_BUTTON].setSize("100");
    this._dateInfo = {};

	DBG.timePt("create dwt controls, fields; register handlers");
};

ZmApptQuickAddDialog.prototype = new ZmQuickAddDialog;
ZmApptQuickAddDialog.prototype.constructor = ZmApptQuickAddDialog;


// Consts

ZmApptQuickAddDialog.MORE_DETAILS_BUTTON = ++DwtDialog.LAST_BUTTON;

// Public

ZmApptQuickAddDialog.prototype.toString = 
function() {
	return "ZmApptQuickAddDialog";
};


ZmApptQuickAddDialog.prototype.getFreeBusyCache =
function() {
    return this._fbCache;
}

ZmApptQuickAddDialog.prototype.initialize = 
function(appt) {
	this._appt = appt;

	// reset fields...
	this._subjectField.setValue(appt.getName() ? appt.getName() : "");
	this._locationField.setValue(appt.getLocation() ? appt.getLocation() : "");
	this._startDateField.value = AjxDateUtil.simpleComputeDateStr(appt.startDate);
	this._endDateField.value = AjxDateUtil.simpleComputeDateStr(appt.endDate);
	var isAllDay = appt.isAllDayEvent();
	this._showTimeFields(!isAllDay);
    this._showAsSelect.setSelectedValue("B");
	if (!isAllDay) {
		this._startTimeSelect.set(appt.startDate);
		this._endTimeSelect.set(appt.endDate);
        //need to capture initial time set while composing/editing appt
        ZmApptViewHelper.getDateInfo(this, this._dateInfo);        
	} else {
        this._dateInfo = {};
        this._showAsSelect.setSelectedValue("F");
    }

    this._privacySelect.enable();
	this._privacySelect.setSelectedValue("PUB");
    this._calendarOrgs = {};
	ZmApptViewHelper.populateFolderSelect(this._folderSelect, this._folderRow, this._calendarOrgs, appt);
	this._repeatSelect.setSelectedValue("NON");
	this._repeatDescField.innerHTML = "";
	this._origFormValue = this._formValue();
	this._locations = [];
};

/**
 * Gets the appointment.
 * 
 * @return	{ZmAppt}	the appointment
 */
ZmApptQuickAddDialog.prototype.getAppt = 
function() {
	// create a copy of the appointment so we dont muck w/ the original
	var appt = ZmAppt.quickClone(this._appt);
	appt.setViewMode(ZmCalItem.MODE_NEW);

	// save field values of this view w/in given appt
	appt.setName(this._subjectField.getValue());
	appt.freeBusy = this._showAsSelect.getValue();
	appt.privacy = this._privacySelect.getValue();
	var calId = this._folderSelect.getValue();
	appt.setFolderId(calId);
	appt.setOrganizer(this._calendarOrgs[calId]);

	// set the start date by aggregating start date/time fields
	var startDate = AjxDateUtil.simpleParseDateStr(this._startDateField.value);
	var endDate = AjxDateUtil.simpleParseDateStr(this._endDateField.value);
	if (this._appt.isAllDayEvent()) {
		appt.setAllDayEvent(true);
        if(AjxDateUtil.isDayShifted(startDate)) {
            AjxDateUtil.rollToNextDay(startDate);
            AjxDateUtil.rollToNextDay(endDate);
        }
	} else {
		appt.setAllDayEvent(false);
		startDate = this._startTimeSelect.getValue(startDate);
		endDate = this._endTimeSelect.getValue(endDate);
	}
	appt.setStartDate(startDate);
	appt.setEndDate(endDate);
	appt.setRecurType(this._repeatSelect.getValue());
	appt.location = this._locationField.getValue();
	appt.setAttendees(this._locations, ZmCalBaseItem.LOCATION);

	//set alarm for reminders
    if (this._hasReminderSupport) {
        appt.setReminderMinutes(this._reminderSelect.getValue());
        if (this._reminderEmailCheckbox && this._reminderEmailCheckbox.isSelected()) {
            appt.addReminderAction(ZmCalItem.ALARM_EMAIL);
        }
        if (this._reminderDeviceEmailCheckbox && this._reminderDeviceEmailCheckbox.isSelected()) {
            appt.addReminderAction(ZmCalItem.ALARM_DEVICE_EMAIL);
        }
    }

	return appt;
};

ZmApptQuickAddDialog.prototype.isValid = 
function() {
	var subj = AjxStringUtil.trim(this._subjectField.getValue());
	var errorMsg = null;

	if (subj && subj.length) {
		if (!ZmTimeInput.validStartEnd( this._startDateField, this._endDateField, this._startTimeSelect, this._endTimeSelect)) {
			errorMsg = ZmMsg.errorInvalidDates;
		}
	} else {
		errorMsg = ZmMsg.errorMissingSubject;
	}
    if (errorMsg) {
        var dlg = appCtxt.getMsgDialog();
		dlg.setMessage(errorMsg, DwtMessageDialog.WARNING_STYLE);
		dlg.popup();
    }

	return errorMsg == null;
};

ZmApptQuickAddDialog.prototype.isDirty = 
function() {
	return this._formValue() != this._origFormValue;
};

ZmApptQuickAddDialog.prototype._setFocusToSubjectFeild =
function(){
    this._tabGroup.setFocusMember(this._subjectField);
};

ZmApptQuickAddDialog.prototype.popup =
function(loc) {
	ZmQuickAddDialog.prototype.popup.call(this, loc);
    this._fbCache.clearCache();
	if (!this._tabGroupComplete) {
		// tab group filled in here rather than in the constructor b/c we need
		// all the content fields to have been created
		var members = [this._subjectField, this._locationField, this._showAsSelect, this._privacySelect];
		if (this._folderSelect.size() > 1) {
			members.push(this._folderSelect);
		}
		// XXX: ZmTimeInput doesn't handle focus yet
		members = members.concat([this._startDateField, this._startDateButton, this._startTimeSelect.getInputField(),
								  this._endDateField, this._endDateButton,  this._endTimeSelect.getInputField(),
								  this._repeatSelect]);
		for (var i = 0; i < members.length; i++) {
			this._tabGroup.addMember(members[i], i);
		}
		this._tabGroupComplete = true;
	}
    //bug:68208 Focus must be in the Subject of QuickAdd Appointment dialog after double-click in calendar
    this._focusAction = new AjxTimedAction(this, this._setFocusToSubjectFeild);
    AjxTimedAction.scheduleAction(this._focusAction, 300);

    if (this._hasReminderSupport) {
        var defaultWarningTime = appCtxt.get(ZmSetting.CAL_REMINDER_WARNING_TIME);
        this._reminderSelect.setSelectedValue(defaultWarningTime);
        this._setEmailReminderControls();
    }

	var defaultPrivacyOption = appCtxt.get(ZmSetting.CAL_APPT_VISIBILITY);
	this._privacySelect.setSelectedValue((defaultPrivacyOption == ZmSetting.CAL_VISIBILITY_PRIV) ?  "PRI" : "PUB");

    Dwt.setVisible(this._suggestions, false);
    Dwt.setVisible(this._suggestLocation, appCtxt.get(ZmSetting.GAL_ENABLED));

	DBG.timePt("ZmQuickAddDialog#popup", true);
};

ZmApptQuickAddDialog.prototype._autoCompCallback =
function(text, el, match) {
	if (match.item) {
		this._locationField.setValue(match.item.getFullName());
	}
};

// Private / protected methods

ZmApptQuickAddDialog.prototype._createDwtObjects =
function() {

	// create DwtInputField's
	this._subjectField = new DwtInputField({parent:this, type:DwtInputField.STRING,
											initialValue:null, size:null, maxLen:null,
											errorIconStyle:DwtInputField.ERROR_ICON_NONE,
											validationStyle:DwtInputField.CONTINUAL_VALIDATION,
											parentElement:(this._htmlElId + "_subject")});
	this._subjectField.setRequired();
	Dwt.setSize(this._subjectField.getInputElement(), "100%", "22px");


    this._locationField = new DwtInputField({parent:this, type:DwtInputField.STRING,
											initialValue:null, size:null, maxLen:null,
											errorIconStyle:DwtInputField.ERROR_ICON_NONE,
											validationStyle:DwtInputField.ONEXIT_VALIDATION,
											parentElement:(this._htmlElId + "_location")});
	Dwt.setSize(this._locationField.getInputElement(), "100%", "22px");

    // create DwtSelects
	this._showAsSelect = new DwtSelect({parent:this, parentElement:(this._htmlElId + "_showAs")});
	for (var i = 0; i < ZmApptViewHelper.SHOWAS_OPTIONS.length; i++) {
		var option = ZmApptViewHelper.SHOWAS_OPTIONS[i];
		this._showAsSelect.addOption(option.label, option.selected, option.value, "showAs" + option.value);
	}

	this._privacySelect = new DwtSelect({parent:this, parentElement:(this._htmlElId + "_privacy")});
	for (var j = 0; j < ZmApptEditView.PRIVACY_OPTIONS.length; j++) {
		var option = ZmApptEditView.PRIVACY_OPTIONS[j];
		this._privacySelect.addOption(option.label, option.selected, option.value);
	}
	this._privacySelect.addChangeListener(new AjxListener(this, this._privacyListener));

	this._folderSelect = new DwtSelect({parent:this, parentElement:(this._htmlElId + "_calendar")});
	this._folderSelect.addChangeListener(new AjxListener(this, this._privacyListener));

	var dateButtonListener = new AjxListener(this, this._dateButtonListener);
	var dateCalSelectionListener = new AjxListener(this, this._dateCalSelectionListener);

	var startMiniCalId = this._htmlElId + "_startMiniCal";
	this._startDateButton = ZmCalendarApp.createMiniCalButton(this, startMiniCalId, dateButtonListener, dateCalSelectionListener);
	var endMiniCalId = this._htmlElId + "_endMiniCal";
	this._endDateButton = ZmCalendarApp.createMiniCalButton(this, endMiniCalId, dateButtonListener, dateCalSelectionListener);

	// create selects for Time section
	var timeSelectListener = new AjxListener(this, this._timeChangeListener);
	
	this._startTimeSelect = new ZmTimeInput(this, ZmTimeInput.START);
	this._startTimeSelect.addChangeListener(timeSelectListener);
	this._startTimeSelect.reparentHtmlElement(this._htmlElId + "_startTime");

	this._endTimeSelect = new ZmTimeInput(this, ZmTimeInput.END);
	this._endTimeSelect.addChangeListener(timeSelectListener);
	this._endTimeSelect.reparentHtmlElement(this._htmlElId + "_endTime");

	this._repeatSelect = new DwtSelect({parent:this, parentElement:(this._htmlElId + "_repeat")});
	this._repeatSelect.addChangeListener(new AjxListener(this, this._repeatChangeListener));
	for (var i = 0; i < ZmApptViewHelper.REPEAT_OPTIONS.length-1; i++) {
		var option = ZmApptViewHelper.REPEAT_OPTIONS[i];
		this._repeatSelect.addOption(option.label, option.selected, option.value);
	}

	//reminder DwtSelect
    var	displayOptions = [
		ZmMsg.apptRemindNever,
		ZmMsg.apptRemindNMinutesBefore,
		ZmMsg.apptRemindNMinutesBefore,
		ZmMsg.apptRemindNMinutesBefore,
		ZmMsg.apptRemindNMinutesBefore,
		ZmMsg.apptRemindNMinutesBefore,
		ZmMsg.apptRemindNMinutesBefore,
		ZmMsg.apptRemindNMinutesBefore,
		ZmMsg.apptRemindNHoursBefore,
		ZmMsg.apptRemindNHoursBefore,
		ZmMsg.apptRemindNHoursBefore,
		ZmMsg.apptRemindNHoursBefore,
		ZmMsg.apptRemindNHoursBefore,
		ZmMsg.apptRemindNDaysBefore,
		ZmMsg.apptRemindNDaysBefore,
		ZmMsg.apptRemindNDaysBefore,
		ZmMsg.apptRemindNDaysBefore,
		ZmMsg.apptRemindNWeeksBefore,
		ZmMsg.apptRemindNWeeksBefore
	];

	var	options = [0, 1, 5, 10, 15, 30, 45, 60, 120, 180, 240, 300, 1080, 1440, 2880, 4320, 5760, 10080, 20160];
	var	labels = [0, 1, 5, 10, 15, 30, 45, 60, 2, 3, 4, 5, 18, 1, 2, 3, 4, 1, 2];
	var defaultWarningTime = appCtxt.get(ZmSetting.CAL_REMINDER_WARNING_TIME);

    this._hasReminderSupport = Dwt.byId(this._htmlElId + "_reminderSelect") != null;

    if (this._hasReminderSupport) {
        this._reminderSelect = new DwtSelect({parent:this});
        this._reminderSelect.addChangeListener(new AjxListener(this, this._setEmailReminderControls));
        for (var j = 0; j < options.length; j++) {
            var optLabel = ZmCalendarApp.__formatLabel(displayOptions[j], labels[j]);
            this._reminderSelect.addOption(optLabel, (defaultWarningTime == options[j]), options[j]);
        }
        this._reminderSelect.reparentHtmlElement(this._htmlElId + "_reminderSelect");

        this._reminderEmailCheckbox = new DwtCheckbox({parent: this});
        this._reminderEmailCheckbox.replaceElement(document.getElementById(this._htmlElId + "_reminderEmailCheckbox"));
        this._reminderEmailCheckbox.setText(ZmMsg.email);
        this._reminderDeviceEmailCheckbox = new DwtCheckbox({parent: this});
        this._reminderDeviceEmailCheckbox.replaceElement(document.getElementById(this._htmlElId + "_reminderDeviceEmailCheckbox"));
        this._reminderDeviceEmailCheckbox.setText(ZmMsg.deviceEmail);
        this._reminderConfigure = new DwtText({parent:this,className:"FakeAnchor"});
        this._reminderConfigure.setText(ZmMsg.remindersConfigure);
        this._reminderConfigure.getHtmlElement().onclick = AjxCallback.simpleClosure(this._handleConfigureClick, this);
        this._reminderConfigure.replaceElement(document.getElementById(this._htmlElId+"_reminderConfigure"));
        this._setEmailReminderControls();

        var settings = appCtxt.getSettings();
        var listener = new AjxListener(this, this._settingChangeListener);
        settings.getSetting(ZmSetting.CAL_EMAIL_REMINDERS_ADDRESS).addChangeListener(listener);
        settings.getSetting(ZmSetting.CAL_DEVICE_EMAIL_REMINDERS_ADDRESS).addChangeListener(listener);
    }

	// init auto-complete widget if contacts app enabled
	if (appCtxt.get(ZmSetting.CONTACTS_ENABLED)) {
		this._initAutocomplete();
	}

	this._suggestLocationId = this._htmlElId + "_suggest_location";
	this._suggestLocation   = document.getElementById(this._suggestLocationId);

	this._suggestions = document.getElementById(this._htmlElId + "_suggestions");
	Dwt.setVisible(this._suggestions, false);

	var closeCallback = this._onSuggestionClose.bind(this);
	var dialogContentEl = document.getElementById(this._htmlElId + "_content");
	this._containerSize = Dwt.getSize(dialogContentEl);
	if (appCtxt.get(ZmSetting.GAL_ENABLED)) {
		this._locationAssistant = new ZmLocationAssistantView(this, appCtxt.getCurrentController(), this, closeCallback);
		this._locationAssistant.reparentHtmlElement(this._suggestions);
	}
	AjxTimedAction.scheduleAction(new AjxTimedAction(this, this.loadPreference), 300);
};

ZmApptQuickAddDialog.prototype.loadPreference =
function() {
    var prefDlg = appCtxt.getSuggestionPreferenceDialog();
    prefDlg.setCallback(new AjxCallback(this, this._prefChangeListener));
    prefDlg.getSearchPreference(appCtxt.getActiveAccount());
};

ZmApptQuickAddDialog.prototype._prefChangeListener =
function() {
    // Preference Dialog is only displayed when the suggestions panel is visible - so update suggestions
    if (this._locationAssistant) {
		this._locationAssistant.clearResources();
		this._locationAssistant.suggestAction();
	}
};

ZmApptQuickAddDialog.prototype._handleConfigureClick = function() {
    // transfer settings to new appt compose tab
    var button = this._button[ZmApptQuickAddDialog.MORE_DETAILS_BUTTON];
    if (button) {
        button._emulateSingleClick(); // HACK: should be public method on DwtButton
    }

    // or just get rid of this modal dialog
    else {
        this.popdown();
    }

    // go to reminders prefs page
    // NOTE: We can't query the section name based on the pref id
    // NOTE: because that info won't be available until the first time
    // NOTE: prefs app is launched.
    skin.gotoPrefs("NOTIFICATIONS");
};

ZmApptQuickAddDialog.prototype._initAutocomplete =
function() {
	var acCallback = new AjxCallback(this, this._autocompleteCallback);
	this._acList = null;

	if (appCtxt.get(ZmSetting.GAL_ENABLED) || appCtxt.get(ZmSetting.GAL_ENABLED)) {
		// autocomplete for locations
		var app = appCtxt.getApp(ZmApp.CALENDAR);
		var params = {
			dataClass:		appCtxt.getAutocompleter(),
			matchValue:		ZmAutocomplete.AC_VALUE_NAME,
			compCallback:	acCallback,
            keyUpCallback:	this._handleLocationChange.bind(this),
			options:		{type:ZmAutocomplete.AC_TYPE_LOCATION},
			contextId:		[this.toString(), ZmCalBaseItem.LOCATION].join("-")
		};
		this._acLocationsList = new ZmAutocompleteListView(params);
		this._acLocationsList.handle(this._locationField.getInputElement());
		this._acList = this._acLocationsList;
	}
};

ZmApptQuickAddDialog.prototype._autocompleteCallback =
function(text, el, match) {
	if (!match) {
		DBG.println(AjxDebug.DBG1, "ZmApptQuickAddDialog: match empty in autocomplete callback; text: " + text);
		return;
	}
	var attendee = match.item;
	if (attendee) {
		var type = el._attType;
		this._isKnownLocation = true;
		attendee = (attendee instanceof AjxVector) ? attendee.getArray() :
				   (attendee instanceof Array) ? attendee : [attendee];
		for (var i = 0; i < attendee.length; i++) {
			this._locations.push(attendee[i]);
		}
	}
};

//monitor location field change and reset location resources array
ZmApptQuickAddDialog.prototype._handleLocationChange =
function(event, aclv, result) {
	var val = this._locationField.getValue();
    if (val.length <= 1) {
        // This is only called onKeyUp, so a length 1 string means typing just started
        this._locations = [];
		this._isKnownLocation = false;
	}
};

ZmApptQuickAddDialog.prototype._privacyListener =
function() {
	if (!this._privacySelect) { return; }

	var value = this._privacySelect.getValue();
	var calId = this._folderSelect.getValue();
	var cal = calId && appCtxt.getById(calId);

    if (appCtxt.isOffline) {
        var currAcct = cal.getAccount();
        appCtxt.accountList.setActiveAccount(currAcct);
    }

	if (cal) {
        var isRemote = cal.isRemote();        
		if (value == "PRI" && isRemote && !cal.hasPrivateAccess()) {
			this._privacySelect.setSelectedValue("PUB");
			this._privacySelect.disable();
		} else {
			this._privacySelect.enable();
		}
	}
};

ZmApptQuickAddDialog.prototype._cacheFields =
function() {
	this._folderRow			= document.getElementById(this._htmlElId + "_folderRow");
	this._startDateField 	= document.getElementById(this._htmlElId + "_startDate");
	this._endDateField 		= document.getElementById(this._htmlElId + "_endDate");
	this._repeatDescField 	= document.getElementById(this._htmlElId + "_repeatDesc");
};

ZmApptQuickAddDialog.prototype._addEventHandlers = 
function() {
	var qadId = AjxCore.assignId(this);

	Dwt.setHandler(this._startDateField, DwtEvent.ONCHANGE, ZmApptQuickAddDialog._onChange);
	Dwt.setHandler(this._endDateField, DwtEvent.ONCHANGE, ZmApptQuickAddDialog._onChange);
	Dwt.setHandler(this._suggestLocation, DwtEvent.ONCLICK, this._showLocationSuggestions.bind(this));

	var dateSelectListener = this._dateChangeListener.bind(this);
	Dwt.setHandler(this._startDateField, DwtEvent.ONCHANGE, dateSelectListener);
	Dwt.setHandler(this._endDateField,   DwtEvent.ONCHANGE, dateSelectListener);

	this._startDateField._qadId = this._endDateField._qadId =  qadId;
};

ZmApptQuickAddDialog.prototype._showTimeFields = 
function(show) {
	Dwt.setVisibility(this._startTimeSelect.getHtmlElement(), show);
	Dwt.setVisibility(this._endTimeSelect.getHtmlElement(), show);
	if (this._supportTimeZones)
		Dwt.setVisibility(this._endTZoneSelect.getHtmlElement(), show);
	// also show/hide the "@" text
	Dwt.setVisibility(document.getElementById(this._htmlElId + "_startTimeAt"), show);
	Dwt.setVisibility(document.getElementById(this._htmlElId + "_endTimeAt"), show);
};



ZmApptQuickAddDialog.prototype._onSuggestionClose =
function() {
    // Make the trigger link visible
    Dwt.setVisible(this._suggestLocation, true);
}

ZmApptQuickAddDialog.prototype._showLocationSuggestions =
function() {
    // Hide the trigger link and display the location suggestion panel
    Dwt.setVisible(this._suggestLocation, false);
    Dwt.setVisible(this._suggestions, true);
    this._locationAssistant.show(this._containerSize);
    this._locationAssistant.suggestAction();
};

ZmApptQuickAddDialog.prototype._formValue =
function() {
	var vals = [];

	vals.push(this._subjectField.getValue());
	vals.push(this._locationField.getValue());
	vals.push(this._startDateField.value);
	vals.push(this._endDateField.value);
    if (this._hasReminderSupport) {
        vals.push(this._reminderSelect.getValue());
        vals.push(this._reminderEmailCheckbox.isSelected());
        vals.push(this._reminderDeviceEmailCheckbox.isSelected());
    }
	if (!this._appt.isAllDayEvent()) {
		vals.push(
			AjxDateUtil.getServerDateTime(this._startTimeSelect.getValue()),
			AjxDateUtil.getServerDateTime(this._endTimeSelect.getValue())
		);
	}
	vals.push(this._repeatSelect.getValue());

	var str = vals.join("|");
	str = str.replace(/\|+/, "|");
	return str;
};

// Listeners

ZmApptQuickAddDialog.prototype._dateButtonListener = 
function(ev) {
	var calDate = ev.item == this._startDateButton
		? AjxDateUtil.simpleParseDateStr(this._startDateField.value)
		: AjxDateUtil.simpleParseDateStr(this._endDateField.value);

	// if date was input by user and its foobar, reset to today's date
	if (isNaN(calDate)) {
		calDate = new Date();
		var field = ev.item == this._startDateButton
			? this._startDateField : this._endDateField;
		field.value = AjxDateUtil.simpleComputeDateStr(calDate);
	}

	// always reset the date to current field's date
	var menu = ev.item.getMenu();
	var cal = menu.getItem(0);
	cal.setDate(calDate, true);
	ev.item.popup();
};

ZmApptQuickAddDialog.prototype._dateCalSelectionListener = 
function(ev) {
	var parentButton = ev.item.parent.parent;

	// do some error correction... maybe we can optimize this?
	var sd = AjxDateUtil.simpleParseDateStr(this._startDateField.value);
	var ed = AjxDateUtil.simpleParseDateStr(this._endDateField.value);
	var newDate = AjxDateUtil.simpleComputeDateStr(ev.detail);

	// change the start/end date if they mismatch
	if (parentButton == this._startDateButton) {
		if (ed.valueOf() < ev.detail.valueOf())
			this._endDateField.value = newDate;
		this._startDateField.value = newDate;
	} else {
		if (sd.valueOf() > ev.detail.valueOf())
			this._startDateField.value = newDate;
		this._endDateField.value = newDate;
	}
    this._dateChangeListener();
};

ZmApptQuickAddDialog.prototype._repeatChangeListener = 
function(ev) {
	this._repeatDescField.innerHTML = ev._args.newValue != "NON" ? AjxStringUtil.htmlEncode(ZmMsg.recurEndNone) : "";
};

ZmApptQuickAddDialog.prototype._timeChangeListener =
function(ev, id) {
	ZmTimeInput.adjustStartEnd(ev, this._startTimeSelect, this._endTimeSelect, this._startDateField, this._endDateField, this._dateInfo, id);
    if (!this._appt.isAllDayEvent()) {
        ZmApptViewHelper.getDateInfo(this, this._dateInfo);
    }
	this._locationAssistant && this._locationAssistant.updateTime();
};

ZmApptQuickAddDialog.prototype._dateChangeListener =
function(ev, id) {
    if (!this._appt.isAllDayEvent()) {
        ZmApptViewHelper.getDateInfo(this, this._dateInfo);
    }
	this._locationAssistant && this._locationAssistant.updateTime();
};

ZmApptQuickAddDialog.prototype.getDurationInfo =
function() {
    var startDate = AjxDateUtil.simpleParseDateStr(this._dateInfo.startDate);
    var endDate   = AjxDateUtil.simpleParseDateStr(this._dateInfo.endDate);
    startDate = this._startTimeSelect.getValue(startDate);
    endDate   = this._endTimeSelect.getValue(endDate);

    var durationInfo = {};
    durationInfo.startTime = startDate.getTime();
    durationInfo.endTime   = endDate.getTime();
    durationInfo.duration  = durationInfo.endTime - durationInfo.startTime;
    return durationInfo;
};

ZmApptQuickAddDialog.prototype._setEmailReminderControls =
function() {
    var enabled = this._reminderSelect.getValue() != 0;

    var email = appCtxt.get(ZmSetting.CAL_EMAIL_REMINDERS_ADDRESS);
    var emailEnabled = Boolean(email);
    this._reminderEmailCheckbox.setEnabled(enabled && emailEnabled);
    this._reminderEmailCheckbox.setToolTipContent(emailEnabled ? email : null);

    var deviceEmail = appCtxt.get(ZmSetting.CAL_DEVICE_EMAIL_REMINDERS_ADDRESS);
    var deviceEmailEnabled = Boolean(deviceEmail);
    this._reminderDeviceEmailCheckbox.setEnabled(enabled && deviceEmailEnabled);
    this._reminderDeviceEmailCheckbox.setToolTipContent(deviceEmailEnabled ? deviceEmail : null);

    var configureEnabled = !emailEnabled && !deviceEmailEnabled;
    this._reminderConfigure.setVisible(configureEnabled);
    this._reminderEmailCheckbox.setVisible(!configureEnabled);
    this._reminderDeviceEmailCheckbox.setVisible((!configureEnabled && appCtxt.get(ZmSetting.CAL_DEVICE_EMAIL_REMINDERS_ENABLED)));
};

ZmApptQuickAddDialog.prototype._settingChangeListener =
function(ev) {
	if (ev.type != ZmEvent.S_SETTING) { return; }
	var id = ev.source.id;
	if (id == ZmSetting.CAL_EMAIL_REMINDERS_ADDRESS || id == ZmSetting.CAL_DEVICE_EMAIL_REMINDERS_ADDRESS) {
		this._setEmailReminderControls();
	}
};

// Static methods
ZmApptQuickAddDialog._onChange = 
function(ev) {
	var el = DwtUiEvent.getTarget(ev);
	var qad = AjxCore.objectWithId(el._qadId);
	ZmApptViewHelper.handleDateChange(qad._startDateField, qad._endDateField, el == qad._startDateField);
};

ZmApptQuickAddDialog.prototype.updateLocation =
function(location, locationStr) {
    this._locationField.setValue(locationStr);
    if (this._useAcAddrBubbles) {
        this._locationField.clear();
        this._locationField.addBubble({address:locationStr, match:match, skipNotify:true});
    }
    this._locations.push(location);
};

// Stub for the location picker and ZmScheduleAssistant
ZmApptQuickAddDialog.prototype.getCalendarAccount =
function() {
    var cal = appCtxt.getById(this._folderSelect.getValue());
    return cal && cal.getAccount();
};

ZmApptQuickAddDialog.prototype.getFreeBusyExcludeInfo =
function(emailAddr){
    return null;
};
