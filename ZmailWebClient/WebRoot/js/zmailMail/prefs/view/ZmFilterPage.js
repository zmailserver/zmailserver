/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2010, 2012 VMware, Inc.
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
 * Creates the import/export page.
 * @class
 * This class represents the import/export page.
 * 
 * @param {DwtControl}	parent			the containing widget
 * @param {Object}	section			the page
 * @param {ZmPrefController}	controller		the prefs controller
 * 
 * @extends	ZmPreferencesPage
 * 
 * @private
 */
ZmFilterPage = function(parent, section, controller) {
	ZmPreferencesPage.apply(this, arguments);
};

ZmFilterPage.prototype = new ZmPreferencesPage;
ZmFilterPage.prototype.constructor = ZmFilterPage;

ZmFilterPage.prototype.toString =
function () {
    return "ZmFilterPage";
};

ZmFilterPage.prototype._createControls =
function() {
	if (appCtxt.get(ZmSetting.PRIORITY_INBOX_ENABLED)) {
		this._activityStreamsButton = new DwtButton({parent:this, parentElement: this._htmlElId+"_ACTIVITY_STREAM_BUTTON" });
		this._activityStreamsButton.setText(ZmMsg.activityStreamSettings);
		this._activityStreamsButton.addSelectionListener(new AjxListener(this, this._activityStreamDialog));
	}
	this._tabView = new DwtTabView({parent:this, posStyle:Dwt.STATIC_STYLE});
	this._tabView.reparentHtmlElement(this._htmlElId+"_tabview");
	var incomingController = this._controller.getIncomingFilterRulesController();
	this._tabView.addTab(ZmMsg.incomingMessageFilters, incomingController.getFilterRulesView());
	var outgoingController = this._controller.getOutgoingFilterRulesController();
	this._tabView.addTab(ZmMsg.outgoingMessageFilters, outgoingController.getFilterRulesView());
	this.setVisible(true);
	
	this.hasRendered = true;
}

ZmFilterPage.prototype.getTabView =
function () {
	return this._tabView;
};

ZmFilterPage.prototype.hasResetButton =
function() {
	return false;
};


//
// Protected methods
//

ZmFilterPage.prototype._setupCustom = function(id, setup, value) {
	if (id == "FILTER_TABS") {
		return this.getTabView();
	}
	return ZmPreferencesPage.prototype._setupCustom.apply(this, arguments);
};

ZmFilterPage.prototype._activityStreamDialog = function() {
	var priorityFilterDialog = appCtxt.getPriorityMessageFilterDialog();
	ZmController.showDialog(priorityFilterDialog);
};