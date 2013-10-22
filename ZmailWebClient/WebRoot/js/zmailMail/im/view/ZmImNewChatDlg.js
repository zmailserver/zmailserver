/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

/** small dialog for picking one contact with an autocompletion entry */

ZmImNewChatDlg = function() {
	DwtDialog.call(this, {parent:DwtShell.getShell(window), title:ZmMsg.selectBuddyOrContact});
	this._init();
};

ZmImNewChatDlg.prototype = new DwtDialog;
ZmImNewChatDlg.prototype.constructor = ZmImNewChatDlg;

ZmImNewChatDlg.prototype.isZmImNewChatDlg = true;
ZmImNewChatDlg.prototype.toString = function() { return "ZmImNewChatDlg"; };

ZmImNewChatDlg.prototype._init = function() {
	var field = new DwtInputField({ parent : this,
					size   : 25,
					hint   : ZmMsg.search });
	this._contactField = field;
	var id = field.getInputElement().id;
	var div = document.createElement("div");
	div.innerHTML = AjxTemplate.expand("im.Chat#NewChatDlg", { id: id });
	this._getContentDiv().appendChild(div);
	field.reparentHtmlElement(id + "_entryCell");
	this._initAutocomplete();
	this.setButtonListener(DwtDialog.OK_BUTTON, new AjxListener(this, this._okButtonListener));
	this.setButtonListener(DwtDialog.CANCEL_BUTTON, new AjxListener(this, this._cancelButtonListener));

	var list = new ZmImOverview(this, { posStyle	: Dwt.STATIC_STYLE,
		expanded	: true
	});
	list.reparentHtmlElement(id + "_buddyListCont");
	list.setSize(220, 200);
};

ZmImNewChatDlg._INSTANCE = null;
ZmImNewChatDlg.getInstance = function() {
	if (!ZmImNewChatDlg._INSTANCE) {
		ZmImNewChatDlg._INSTANCE = new ZmImNewChatDlg();
	}
	return ZmImNewChatDlg._INSTANCE;
};

ZmImNewChatDlg.show = function(callbacks) {
	var dlg = ZmImNewChatDlg.getInstance();
	dlg._callbacks = callbacks || {};
	dlg.reset();
	dlg.popup();
	dlg._contactField.focus();
};

ZmImNewChatDlg.prototype.reset = function() {
	this._acContactsList.reset();
	this._acContactsList.show(false);
	this._contactField.setValue("", true);
};

ZmImNewChatDlg.prototype._initAutocomplete =
function() {
	if (appCtxt.get(ZmSetting.CONTACTS_ENABLED) || appCtxt.get(ZmSetting.GAL_ENABLED)) {
		var acCallback = this._autocompleteCallback.bind(this);
		var params = {
			dataClass:		appCtxt.getAutocompleter(),
			matchValue:		ZmAutocomplete.AC_VALUE_FULL,
			compCallback:	acCallback,
			contextId:		this.toString()
		};
		this._acContactsList = new ZmAutocompleteListView(params);
		this._acContactsList.handle(this._contactField.getInputElement());
	}
};

ZmImNewChatDlg.prototype._autocompleteCallback = function(text, el, match) {
	this._selectedItem = match.item;
	if (this._callbacks.onAutocomplete)
		this._callbacks.onAutocomplete(match.item, this, text, el, match);
};

ZmImNewChatDlg.prototype._okButtonListener = function() {
	var doPopdown = true;
	if (this._callbacks.onOk) {
		doPopdown = this._callbacks.onOk(this._selectedItem, this._contactField.getValue());
	}
	if (doPopdown) {
		this.popdown();
	}
};

ZmImNewChatDlg.prototype._cancelButtonListener = function() {
	if (this._callbacks.onCancel) {
		this._callbacks.onCancel();
	}
	this.popdown();
};
