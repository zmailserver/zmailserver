/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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

ZmCustomStatusDlg = function(params) {
	ZmDialog.call(this, params);
	this._setNameField(this._messageFieldId);
};

ZmCustomStatusDlg.prototype = new ZmDialog;
ZmCustomStatusDlg.prototype.constructor = ZmCustomStatusDlg;

ZmCustomStatusDlg.prototype.toString =
function() {
	return "ZmCustomStatusDlg";
};

ZmCustomStatusDlg.prototype.popup =
function () {
	ZmDialog.prototype.popup.call(this);
	Dwt.byId(this._messageFieldId).focus();
};

ZmCustomStatusDlg.prototype.getValue =
function() {
	return Dwt.byId(this._messageFieldId).value;
};

ZmCustomStatusDlg.prototype._contentHtml =
function() {
	this._messageFieldId = Dwt.getNextId();
	return AjxTemplate.expand("im.Chat#ZmCustomStatusDlg", { id: this._messageFieldId });
};

ZmCustomStatusDlg.prototype._enterListener =
function() {
	this._runEnterCallback();
};

ZmCustomStatusDlg.prototype._okButtonListener =
function(ev) {
	ZmDialog.prototype._buttonListener.call(this, ev);
};
