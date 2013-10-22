/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
 * This class represent a search toolbar. The components are some set of: an input field,
 * a search button, a save button, and a button to choose what type of item to search for.
 * 
 * @param {hash}			params		a hash of parameters:
 * @param {DwtComposite}	parent		the parent widget
 * @param {string}			id			an explicit ID to use for the control's HTML element
 * 
 * @extends		DwtComposite
 */
ZmSearchToolBar = function(params) {

	if (arguments.length == 0) { return; }

	params.className = params.className || "ZmSearchToolbar";
	DwtComposite.apply(this, arguments);
	
	this._button = {};
	this._origin = ZmId.SEARCH;
	this._searchMenu = null;
	
	this._createHtml();
};

ZmSearchToolBar.prototype = new DwtComposite;
ZmSearchToolBar.prototype.constructor = ZmSearchToolBar;

ZmSearchToolBar.prototype.isZmSearchToolBar = true;
ZmSearchToolBar.prototype.toString = function() { return "ZmSearchToolBar"; };

// Consts


ZmSearchToolBar.TYPES_BUTTON		= "TYPES";
ZmSearchToolBar.SEARCH_BUTTON 		= "SEARCH";
ZmSearchToolBar.SAVE_BUTTON 		= "SAVE";
ZmSearchToolBar.SEARCH_MENU_BUTTON	= ZmSearchToolBar.TYPES_BUTTON;	// back-compatibility

ZmSearchToolBar.MENUITEM_ID 		= "_menuItemId";		// menu item key

ZmSearchToolBar.MENU_ITEMS 			= [];		// list of menu items
ZmSearchToolBar.SETTING 			= {};		// required setting for menu item to appear
ZmSearchToolBar.MSG_KEY 			= {};		// text for menu item
ZmSearchToolBar.TT_MSG_KEY 			= {};		// tooltip text for menu item
ZmSearchToolBar.ICON 				= {};		// icon for menu item
ZmSearchToolBar.SHARE_ICON			= {};		// icon for shared menu item
ZmSearchToolBar.ID 					= {};		// ID for menu item


// Public static methods

/**
 * Defines a menu item to add when the types menu is created. Static so that it can be called before the
 * toolbar has been created.
 * 
 * @param {string}	id			ID of menu item
 * @param {hash}	params		menu item properties
 */
ZmSearchToolBar.addMenuItem =
function(id, params) {

	if (params.msgKey)		{ ZmSearchToolBar.MSG_KEY[id]		= params.msgKey; }
	if (params.tooltipKey)	{ ZmSearchToolBar.TT_MSG_KEY[id]	= params.tooltipKey; }
	if (params.icon)		{ ZmSearchToolBar.ICON[id]			= params.icon; }
	if (params.shareIcon)	{ ZmSearchToolBar.SHARE_ICON[id]	= params.shareIcon; }
	if (params.setting)		{ ZmSearchToolBar.SETTING[id]		= params.setting; }
	if (params.id)			{ ZmSearchToolBar.ID[id]			= params.id; }

	if (params.index == null || params.index < 0 || params.index >= ZmSearchToolBar.MENU_ITEMS.length) {
		ZmSearchToolBar.MENU_ITEMS.push(id);
	} else {
		ZmSearchToolBar.MENU_ITEMS.splice(params.index, 0, id);
	}
};


// Public methods

/**
 * Removes a menu item from the types menu.
 * 
 * @param {string}	id			ID of menu item
 */
ZmSearchToolBar.prototype.removeMenuItem =
function(id) {
	
	var menu = this._searchMenu;
	if (menu) {
		var mi = menu.getItemById("_menuItemId", id);
		if (mi) {
			menu.removeChild(mi);
			mi.dispose();
		}
		this._cleanupSeparators(menu);
	}
};

// Remove unneeded separators
ZmSearchToolBar.prototype._cleanupSeparators =
function(menu) {

	var button = this._button[ZmSearchToolBar.TYPES_BUTTON];
	menu = menu || (button && button.getMenu());
	if (!menu) { return; }

	var items = menu.getItems();
	var toRemove = [];
	for (var i = 0; i < items.length; i++) {
		var mi = items[i];
		if (mi.isSeparator() && (i == 0 || i == items.length - 1 || items[i - 1].isSeparator())) {
			toRemove.push(mi);
		}
	}
	for (var i = 0; i < toRemove.length; i++) {
		var mi = toRemove[i];
		menu.removeChild(mi);
		mi.dispose();
	}
};

ZmSearchToolBar.prototype.getSearchField =
function() {
	return this._searchField.getInputElement();
};

// sets up a function to call when Enter has been pressed
ZmSearchToolBar.prototype.registerEnterCallback =
function(callback) {
	this._enterCallback = callback;
};

ZmSearchToolBar.prototype.addSelectionListener =
function(buttonId, listener) {
	var button = this._button[buttonId];
	if (button) {
		button.addSelectionListener(listener);
	}
};

ZmSearchToolBar.prototype.getButton =
function(buttonId) {
	return this._button[buttonId];
};

ZmSearchToolBar.prototype.getButtons =
function() {
	return AjxUtil.values(this._button);
};

ZmSearchToolBar.prototype.focus =
function() {
	if (this._searchField) {
		this._searchField.focus();
		this._searchField.moveCursorToEnd();
	}
};

ZmSearchToolBar.prototype.blur =
function() {
	if (this._searchField) {
		this._searchField.blur();
	}
};

ZmSearchToolBar.prototype.setEnabled =
function(enable) {
	if (this._searchField) {
		this._searchField.setEnabled(enable);
	}
	for (var buttonId in this._button) {
		this._button[buttonId].setEnabled(enable);
	}
};

ZmSearchToolBar.prototype.setSearchFieldValue =
function(value) {
	if (this._searchField && value != this.getSearchFieldValue()) {
		this._searchField.setValue(value);
	}
};

ZmSearchToolBar.prototype.getSearchFieldValue =
function() {
	return this._searchField ? this._searchField.getValue() : null;
};



// Private methods

ZmSearchToolBar.prototype._createHtml = function() {};


ZmSearchToolBar.prototype._handleKeyDown =
function(ev) {
	var key = DwtKeyEvent.getCharCode(ev);
	if (key == 3 || key == 13) {
		return this._handleEnterKeyPress(ev);
	}
	return true;
};

ZmSearchToolBar.prototype._handleEnterKeyPress =
function(ev) {
	if (this._enterCallback) {
		this._enterCallback.run({
					ev:				ev,
					zimletEvent:	"onKeyPressSearchField",
					origin:			this._origin
				});
	}
	return false;
};

/**
 * Initializes search autocomplete.
 */
ZmSearchToolBar.prototype.initAutocomplete =
function() {
	if (!this._acList) {
		this._acList = new ZmAutocompleteListView(this._getAutocompleteParams());
		this._acList.handle(this.getSearchField());
	}
};

ZmSearchToolBar.prototype._getAutocompleteParams =
function() {
	var params = {
		dataClass:			new ZmSearchAutocomplete(),
		matchValue:			"matchText",
		delims:				[" ", "\t"],
		delimCodes:			[3, 13, 9],
		separator:			" ",
		keyDownCallback:	this._handleKeyDown.bind(this),
		contextId:			this.toString(),
		locationCallback:	this._getAcLocation.bind(this)
	};
	return params;
};

ZmSearchToolBar.prototype.getAutocompleteListView =
function() {
	return this._acList;
};

ZmSearchToolBar.prototype._getAcLocation =
function() {
	var el = this._searchField.getInputElement();
	if (!el) { return {}; }
	
	var elLoc = Dwt.getLocation(el);
	var elSize = Dwt.getSize(el);
	var strWidth = AjxStringUtil.getWidth(el.value);
	if (AjxEnv.isWindows && (AjxEnv.isFirefox || AjxEnv.isSafari || AjxEnv.isChrome) ){
		// FF/Win: fudge factor since string is longer in INPUT than when measured in SPAN
		strWidth = strWidth * 1.2;
	}
	var x = elLoc.x + strWidth;
	var y = elLoc.y + elSize.y;
	DwtPoint.tmp.set(x, y);
	return DwtPoint.tmp;
};




/**
 * Adds a types button and support for a custom search menu item to a search toolbar
 * 
 * @param params
 */
ZmMainSearchToolBar = function(params) {

	if (arguments.length == 0) { return; }

	ZmSearchToolBar.apply(this, arguments);

	// setup "include shared" menu item
	var miParams = {
		msgKey:		"searchShared",
		tooltipKey:	"searchShared",
		icon:		"Group",
		setting:	ZmSetting.SHARING_ENABLED,
		id:			ZmId.getMenuItemId(ZmId.SEARCH, ZmId.SEARCH_SHARED)
	};
	ZmSearchToolBar.addMenuItem(ZmId.SEARCH_SHARED, miParams);

	// setup "all accounts" menu item for multi account
	if (appCtxt.multiAccounts) {
		var miParams = {
			msgKey:	"searchAllAccounts",
			icon:	"Globe",
			id:		ZmId.getMenuItemId(ZmId.SEARCH, ZmId.SEARCH_ALL_ACCOUNTS)
		};
		ZmSearchToolBar.addMenuItem(ZmId.SEARCH_ALL_ACCOUNTS, miParams);
	}
};

ZmMainSearchToolBar.prototype = new ZmSearchToolBar;
ZmMainSearchToolBar.prototype.constructor = ZmMainSearchToolBar;

ZmMainSearchToolBar.prototype.isZmMainSearchToolBar = true;
ZmMainSearchToolBar.prototype.toString = function() { return "ZmMainSearchToolBar"; };


ZmMainSearchToolBar.prototype.TEMPLATE = "share.Widgets#ZmSearchToolBar";

ZmMainSearchToolBar.CUSTOM_ITEM_ID		= "CustomSearchItem";	// custom search menu item key
ZmMainSearchToolBar.CUSTOM_BUTTON 		= "CUSTOM";				// button ID

ZmMainSearchToolBar.prototype._createHtml =
function() {
    var isExternalAccount = appCtxt.isExternalAccount()
	this.getHtmlElement().innerHTML = AjxTemplate.expand(this.TEMPLATE, {id:this._htmlElId});

	// add search input field
	var inputFieldId = this._htmlElId + "_inputField";
	var inputField = document.getElementById(inputFieldId);
	if (inputField) {
		this._searchField = new DwtInputField({parent:this, hint:ZmMsg.searchInput, inputId:ZmId.SEARCH_INPUTFIELD});
		var inputEl = this._searchField.getInputElement();
		inputEl.className = "search_input";
		this._searchField.reparentHtmlElement(inputFieldId);
		this._searchField._showHint();
		this._searchField.addListener(DwtEvent.ONFOCUS, this._onInputFocus.bind(this));
		this._searchField.addListener(DwtEvent.ONBLUR, this._onInputBlur.bind(this));
        if(isExternalAccount) {
            this._searchField.setEnabled(false);
        }
	}

	// add "search types" menu
	var searchMenuBtnId = this._htmlElId + "_searchMenuButton";
	var searchMenuBtn = document.getElementById(searchMenuBtnId);
	if (searchMenuBtn) {
		var firstItem = ZmSearchToolBar.MENU_ITEMS[0];
		var button = this._button[ZmSearchToolBar.TYPES_BUTTON] = ZmToolBar.addButton({
					parent:		this, 
					tdId:		"_searchMenuButton",
					buttonId:	ZmId.getButtonId(ZmId.SEARCH, ZmId.SEARCH_MENU),
					tooltip:	ZmMsg[ZmSearchToolBar.TT_MSG_KEY[firstItem]],
					icon:		ZmSearchToolBar.ICON[firstItem]
				});
		var menu = new AjxCallback(this, this._createSearchMenu);
		button.setMenu(menu, false, DwtMenuItem.RADIO_STYLE);
		button.reparentHtmlElement(searchMenuBtnId);
        if(isExternalAccount) {
            button.setEnabled(false);
        }
	}

	// add search button
	this._button[ZmSearchToolBar.SEARCH_BUTTON] = ZmToolBar.addButton({
				parent:		this, 
				tdId:		"_searchButton",
				buttonId:	ZmId.getButtonId(ZmId.SEARCH, ZmId.SEARCH_SEARCH),
				lbl:		"",
				icon:		"Search2",
				template: 	"dwt.Widgets#ZImageOnlyButton",
				className: 	"ZImageOnlyButton",
				tooltip:	ZmMsg.searchTooltip
			});

	// add save search button if saved-searches enabled
	this._button[ZmSearchToolBar.SAVE_BUTTON] = ZmToolBar.addButton({
				parent:		this, 
				setting:	ZmSetting.SAVED_SEARCHES_ENABLED,
				tdId:		"_saveButton",
				buttonId:	ZmId.getButtonId(ZmId.SEARCH, ZmId.SEARCH_SAVE),
				lbl:		ZmMsg.save,
				icon:		"Save",
				type:		"toolbar",
				tooltip:	ZmMsg.saveSearchTooltip });
    if(isExternalAccount) {
        if(this._button[ZmSearchToolBar.SEARCH_BUTTON]) this._button[ZmSearchToolBar.SEARCH_BUTTON].setEnabled(false);
        if(this._button[ZmSearchToolBar.SAVE_BUTTON]) this._button[ZmSearchToolBar.SAVE_BUTTON].setEnabled(false);
    }
};

ZmMainSearchToolBar.prototype._createSearchMenu =
function() {

	var menu = this._searchMenu = new DwtMenu({
				parent:		this._button[ZmSearchToolBar.TYPES_BUTTON],
				className:	"ActionMenu",
				id:			ZmId.getMenuId(ZmId.SEARCH)
			});
	var mi;
	if (this._customSearchMenuItems) {
		for (var i = 0; i < this._customSearchMenuItems.length; i++) {
			var csmi = this._customSearchMenuItems[i];
			this._createCustomSearchMenuItem(menu, csmi.icon, csmi.text, csmi.listener);
		}
	}
	var params = {parent:menu, enabled:true, radioGroupId:0};
	for (var i = 0; i < ZmSearchToolBar.MENU_ITEMS.length; i++) {
		var id = ZmSearchToolBar.MENU_ITEMS[i];

		// add separator *before* "shared" menu item
		if (id == ZmId.SEARCH_SHARED) {
			if (ZmSearchToolBar.MENU_ITEMS.length <= 1) { continue; }
			mi = new DwtMenuItem({parent:menu, style:DwtMenuItem.SEPARATOR_STYLE});
		}

		var setting = ZmSearchToolBar.SETTING[id];
		if (setting && !appCtxt.get(setting)) { continue; }

		params.style = (id == ZmId.SEARCH_SHARED || id == ZmId.SEARCH_ALL_ACCOUNTS)
			? DwtMenuItem.CHECK_STYLE : DwtMenuItem.RADIO_STYLE;
		params.imageInfo = ZmSearchToolBar.ICON[id];
		params.text = ZmMsg[ZmSearchToolBar.MSG_KEY[id]];
		params.id = ZmSearchToolBar.ID[id];
		mi = DwtMenuItem.create(params);
		mi.setData(ZmSearchToolBar.MENUITEM_ID, id);
	}
	
	this._checkSharedMenuItem();
	appCtxt.getSettings().getSetting(ZmSetting.SEARCH_INCLUDES_SHARED).addChangeListener(this._checkSharedMenuItem.bind(this));

	appCtxt.getSearchController()._addMenuListeners(menu);
	this._searchMenuCreated = true;

	return menu;
};

ZmMainSearchToolBar.prototype.getSearchType =
function() {
	var button = this._button[ZmSearchToolBar.TYPES_BUTTON];
	var menu = button && button.getMenu();
    var item = menu ? menu.getSelectedItem() || menu.getItems()[0] : null;
	var data = item ? item.getData(ZmMainSearchToolBar.CUSTOM_ITEM_ID) || item.getData(ZmSearchToolBar.MENUITEM_ID) :
					  ZmSearchToolBar.MENU_ITEMS[0];
	return data;
};

ZmMainSearchToolBar.prototype.createCustomSearchBtn =
function(icon, text, listener, id) {

	if (!this._customSearchListener) {
		this._customSearchListener = this._customSearchBtnListener.bind(this);
	}

	// check if custom search should be a button by checking for the Id against the template
	var customSearchBtn = document.getElementById(this._htmlElId + "_customSearchButton");
	if (customSearchBtn) {
		var data = { icon:icon, text:text, listener:listener };
		var button = this._button[ZmSearchToolBar.CUSTOM_BUTTON]
		if (!button) {
			button = this._button[ZmSearchToolBar.CUSTOM_BUTTON] = ZmToolBar.addButton({
						parent:		this,
						tdId:		"_customSearchButton",
						buttonId:	ZmId.getButtonId(ZmId.SEARCH, ZmId.SEARCH_CUSTOM),
						lbl:		text,
						icon:		icon
					});
			button.setData(ZmMainSearchToolBar.CUSTOM_ITEM_ID, data);
			button.addSelectionListener(this._customSearchListener);

			// show the separator now that we've added a custom search button
			var sep = document.getElementById(this._htmlElId + "_customSearchButtonSep");
			if (sep) {
				Dwt.setVisible(sep, true);
			}
		} else {
			var menu = button && button.getMenu();
			var item;
			var params = {
				parent:			menu,
				enabled:		true,
				style:			DwtMenuItem.RADIO_STYLE,
				radioGroupId:	0,
				id:				id
			};
			if (!menu) {
				var btnData = button.getData(ZmMainSearchToolBar.CUSTOM_ITEM_ID);
				menu = new DwtMenu({
							parent:		button,
							className:	"ActionMenu",
							id:			ZmId.getMenuId(ZmId.SEARCH, ZmId.SEARCH_CUSTOM)
						});
				button.setMenu(menu, false, DwtMenuItem.RADIO_STYLE);
				params.imageInfo = btnData.icon;
				params.text = btnData.text;
				item = DwtMenuItem.create(params);
				item.setData(ZmMainSearchToolBar.CUSTOM_ITEM_ID, btnData);
				item.setData(ZmSearchToolBar.MENUITEM_ID, ZmId.SEARCH_CUSTOM);
				item.setChecked(true, true);
				item.addSelectionListener(this._customSearchListener);
			}
			params.imageInfo = icon;
			params.text = text;
			item = DwtMenuItem.create(params);
			item.setData(ZmMainSearchToolBar.CUSTOM_ITEM_ID, data);
			item.addSelectionListener(this._customSearchListener);
		}
	} else {
		if (this._searchMenuCreated) {
			var menu = this._button[ZmSearchToolBar.TYPES_BUTTON].getMenu();
			this._createCustomSearchMenuItem(menu, icon, text, listener, id);
		} else {
			if (!this._customSearchMenuItems) {
				this._customSearchMenuItems = [];
			}
			this._customSearchMenuItems.push({icon:icon, text:text, listener:listener, id:id});
		}
	}
};

ZmMainSearchToolBar.prototype._createCustomSearchMenuItem =
function(menu, icon, text, listener, id) {
	var mi = menu.getItem(0);
	var params = {
		parent: menu,
		imageInfo: icon,
		text: text,
		enabled: true,
		style: DwtMenuItem.RADIO_STYLE,
		radioGroupId: 0,
		index: 0,
		id: id
	};
	mi = DwtMenuItem.create(params);
	var data = { icon:icon, text:text, listener:listener };
	mi.setData(ZmMainSearchToolBar.CUSTOM_ITEM_ID, data);
	mi.setData(ZmSearchToolBar.MENUITEM_ID, ZmId.SEARCH_CUSTOM);
	mi.addSelectionListener(this._customSearchListener);

	// only add separator if this is the first custom search menu item
	if (!(mi && mi.getData(ZmMainSearchToolBar.CUSTOM_ITEM_ID))) {
		mi = new DwtMenuItem({parent:menu, style:DwtMenuItem.SEPARATOR_STYLE, index:1});
	}
};

ZmMainSearchToolBar.prototype._customSearchBtnListener = 
function(ev) {
	var item = ev.item;
	if (!item) { return; }
	var data = item.getData(ZmMainSearchToolBar.CUSTOM_ITEM_ID);
	if (!data) { return; }
	if (this._customSearchBtn) {
		if (item.isDwtMenuItem) {
			if (ev.detail != DwtMenuItem.CHECKED) { return; }
			this._customSearchBtn.setToolTipContent(data[1]);
			this._customSearchBtn.setData(ZmMainSearchToolBar.CUSTOM_ITEM_ID, data);
		}
		data.listener.run(ev); // call original listener
	} else {
		var button = this._button[ZmSearchToolBar.TYPES_BUTTON];
		button.setToolTipContent(data.text);

		var menu = item.parent;
		var shareMenuItem = menu ? menu.getItemById(ZmSearchToolBar.MENUITEM_ID, ZmId.SEARCH_SHARED) : null;
		if (shareMenuItem) {
			shareMenuItem.setChecked(false, true);
			shareMenuItem.setEnabled(false);
		}

		button.setImage(data.icon);
		button.setText(data.text);
	}
};

// Expand INPUT when it gets focus
ZmMainSearchToolBar.prototype._onInputFocus =
function(ev) {
	this._setInputExpanded(true);
};

// Collapse INPUT when it loses focus (unless that was due to a click on the menu button)
ZmMainSearchToolBar.prototype._onInputBlur =
function(ev) {
	var focusObj = appCtxt.getKeyboardMgr().getFocusObj();
	if (focusObj != this._button[ZmSearchToolBar.TYPES_BUTTON] && focusObj != this._button[ZmSearchToolBar.SEARCH_BUTTON]) {
		this._setInputExpanded(false);
	}
};

ZmMainSearchToolBar.prototype._setInputExpanded =
function(expanded) {
	this._searchField.getInputElement().className = expanded ? "search_input-expanded" : "search_input";
};

/**
 * Initializes people autocomplete and search autocomplete.
 */
ZmMainSearchToolBar.prototype.initAutocomplete =
function() {

	ZmSearchToolBar.prototype.initAutocomplete.apply(this, arguments);
	
	var id = this.getSearchType();
	var needPeople = (id == ZmItem.CONTACT || id == ZmId.SEARCH_GAL);

	if (false && !this._peopleAutocomplete) {
		var params = {
			parent:			appCtxt.getShell(),
			dataClass:		(new ZmPeopleSearchAutocomplete()),
			matchValue:		ZmAutocomplete.AC_VALUE_EMAIL,
			options:		{type:ZmAutocomplete.AC_TYPE_GAL},
			separator:		""
		};
		this._peopleAutocomplete = new ZmPeopleAutocompleteListView(params);
		if (needPeople) {
			this._peopleAutocomplete.handle(this.getSearchField());
		}
	}
};

/**
 * Enables or disables people autocomplete depending on what user is searching for.
 * 
 * @param {string}	id		ID of menu item selected in "search for" menu
 */
ZmMainSearchToolBar.prototype.setPeopleAutocomplete =
function(id) {
	
	return;

	//no change required when checking/unchecking the "include shared items" checkbox
	if (id == ZmId.SEARCH_SHARED || !this._peopleAutocomplete) { return; }
	
	var needPeople = (id == ZmItem.CONTACT || id == ZmId.SEARCH_GAL);
	if (this._peopleAutocomplete.isActive != needPeople) {
		var input = this.getSearchField();
		if (needPeople) {
			this._peopleAutocomplete.handle(input);
		}
		else {
			this._peopleAutocomplete.unhandle(input);
		}
	}
};

ZmMainSearchToolBar.prototype._checkSharedMenuItem =
function() {
	var mi = this._searchMenu && this._searchMenu.getItemById(ZmSearchToolBar.MENUITEM_ID, ZmId.SEARCH_SHARED);
	if (mi) {
		mi.setChecked(appCtxt.get(ZmSetting.SEARCH_INCLUDES_SHARED));
	}
};
