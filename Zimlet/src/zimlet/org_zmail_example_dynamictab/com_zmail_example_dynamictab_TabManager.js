/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Zimlets
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
 * @class
 * This class represents a tab manager.
 * 
 * @param	{ZmZimletBase}		zimletBase		the zimlet base
 *   
 */
function org_zmail_example_dynamictab_TabManager(zimletBase) {
	
	this._zimletBase = zimletBase;
};

org_zmail_example_dynamictab_TabManager.prototype.constructor = org_zmail_example_dynamictab_TabManager;

/**
 * Constants
 */
org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_IDS = "org_zmail_dynamictab_property_tab_ids";
org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_LABEL = "org_zmail_dynamictab_property_tab_label_";
org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_TOOLTIP = "org_zmail_dynamictab_property_tab_tooltip_";
org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_URL = "org_zmail_dynamictab_property_tab_url_";

org_zmail_example_dynamictab_TabManager.TAB_ID_LIST_SEPARATOR = ",";

/**
 * Gets the tab ids.
 * 
 * @return	{Array}	an array of tab ids or an empty array for none
 */
org_zmail_example_dynamictab_TabManager.prototype.getTabIdsArray =
function () {
	var tabIds = this._zimletBase.getUserProperty(org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_IDS);
	if (tabIds == null || tabIds.length <= 0)
		return	new Array();
	
	return	tabIds.split(org_zmail_example_dynamictab_TabManager.TAB_ID_LIST_SEPARATOR);
};

/**
 * Gets the tab ids.
 * 
 * @param	{Array}	props		the properties
 * @return	{Array}	an array of tab ids or an empty array for none
 */
org_zmail_example_dynamictab_TabManager.prototype.getTabIdsArrayFromProps =
function (props) {
	var tabIds = "";
	var i=0;
	for(i=0;props && i<props.length;i++) {
		if (props[i].name == org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_IDS) {
			tabIds = props[i].value;
			break;
		}
	}

	if (tabIds == null || tabIds.length <= 0)
		return	new Array();
	
	return	tabIds.split(org_zmail_example_dynamictab_TabManager.TAB_ID_LIST_SEPARATOR);
};

/**
 * Gets the tab ids.
 * 
 * @return	{String}	a string tab id list or an empty string for none
 */
org_zmail_example_dynamictab_TabManager.prototype.getTabIdsString =
function () {
	var tabIds = this._zimletBase.getUserProperty(org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_IDS);
	if (tabIds == null || tabIds.length <= 0)
		return	"";
	
	return	tabIds;
};

/**
 * Sets the tab ids.
 * 
 * @param	{Array}	an array tab id list
 */
org_zmail_example_dynamictab_TabManager.prototype.setTabIds =
function (tabIds) {
	var tabIdsList = tabIds.join(org_zmail_example_dynamictab_TabManager.TAB_ID_LIST_SEPARATOR);
	
	this._zimletBase.setUserProperty(org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_IDS,tabIdsList,true);
};

/**
 * Gets the tab.
 * 
 * @param	{String}	tabId		the tab id
 * @return	{String}	a hash representing a tab object
 */
org_zmail_example_dynamictab_TabManager.prototype.getTab =
function (tabId) {
	
	var tabLabelPropName = org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_LABEL + tabId;
	var tabToolTipPropName = org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_TOOLTIP + tabId;
	var tabUrlPropName = org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_URL + tabId;

	var tabLabel = this._zimletBase.getUserProperty(tabLabelPropName);
	var tabToolTip = this._zimletBase.getUserProperty(tabToolTipPropName);
	var tabUrl = this._zimletBase.getUserProperty(tabUrlPropName);
	
	var tabObject = {
			tabId: tabId,
			tabLabel: tabLabel,
			tabToolTip: tabToolTip,
			tabUrl: tabUrl
	};
	
	return	tabObject;
};

/**
 * Gets the tab label.
 * 
 * @param	{Array}		props		the properties
 * @param	{String}	tabId		the tab id
 * @param	{String}	defaultValue		the default value
 * @return	{String}	the tab label
 */
org_zmail_example_dynamictab_TabManager.prototype.getTabLabel =
function (props, tabId, defaultValue) {
	return	this._getProp(props, org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_LABEL + tabId, defaultValue);
};

/**
 * Gets the tab tool tip.
 * 
 * @param	{Array}		props		the properties
 * @param	{String}	tabId		the tab id
 * @param	{String}	defaultValue		the default value
 * @return	{String}	the tab label
 */
org_zmail_example_dynamictab_TabManager.prototype.getTabToolTip =
function (props, tabId, defaultValue) {
	return	this._getProp(props, org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_TOOLTIP + tabId, defaultValue);
};

/**
 * Gets the tab tool tip.
 * 
 * @param	{Array}		props		the properties
 * @param	{String}	tabId		the tab id
 * @param	{String}	defaultValue		the default value
 * @return	{String}	the tab label
 */
org_zmail_example_dynamictab_TabManager.prototype.getTabUrl =
function (props, tabId, defaultValue) {
	return	this._getProp(props, org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_URL + tabId, defaultValue);
};

/**
 * Gets the property.
 * 
 */
org_zmail_example_dynamictab_TabManager.prototype._getProp =
function(props, name, defaultValue) {
	var value = defaultValue;
	
	for (i=0;i < props.length;i++) {
		if (props[i].name == name)
			return	props[i].value;
	}
	
	return value;
};

/**
 * Saves the tab.
 * 
 * @param	{String}	tabId		the tab id
 * @param	{String}	tabLabel		the tab label
 * @param	{String}	tabToolTip		the tab tool tip
 * @param	{String}	tabUrl		the tab url
 * @param	{Boolean}	commit		<code>true</code> to commit the properties
 */
org_zmail_example_dynamictab_TabManager.prototype.saveTab =
function (tabId,tabLabel,tabToolTip,tabUrl,commit) {
	
	var tabLabelPropName = org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_LABEL + tabId;
	var tabToolTipPropName = org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_TOOLTIP + tabId;
	var tabUrlPropName = org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_URL + tabId;

	this._zimletBase.setUserProperty(tabLabelPropName,tabLabel);
	this._zimletBase.setUserProperty(tabToolTipPropName,tabToolTip);
	this._zimletBase.setUserProperty(tabUrlPropName,tabUrl,commit);
};

/**
 * Gets the tab id from the property name.
 * 
 * @param	{String	propName	the property name
 * @return	{String}	the tab id or <code>null</code> if not tab property
 */
org_zmail_example_dynamictab_TabManager.prototype.getTabIdFromProperty =
function (propName) {

	var idx = -1;
	var tmp = new String(propName);

	// check for label property
	idx = propName.indexOf(org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_LABEL)
	if (idx >= 0) {
		return	tmp.substr(org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_LABEL.length);
	}

	// check for tooltip property
	idx = propName.indexOf(org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_TOOLTIP)
	if (idx >= 0) {
		return	tmp.substr(org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_TOOLTIP.length);
	}

	// check for url property
	idx = propName.indexOf(org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_URL)
	if (idx >= 0) {
		return	tmp.substr(org_zmail_example_dynamictab_TabManager.USER_PROPERTY_TAB_URL.length);
	}

	return	null;
};
