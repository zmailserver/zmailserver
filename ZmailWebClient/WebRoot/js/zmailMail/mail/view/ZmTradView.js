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

ZmTradView = function(params) {

	params.className = params.className || "ZmTradView";
	params.mode = ZmId.VIEW_TRAD;
	ZmDoublePaneView.call(this, params);
}

ZmTradView.prototype = new ZmDoublePaneView;
ZmTradView.prototype.constructor = ZmTradView;

ZmTradView.prototype.isZmTradView = true;
ZmTradView.prototype.toString = function() { return "ZmTradView"; };

ZmTradView.prototype._createMailListView =
function(params) {
	return new ZmMailMsgListView(params);
};

ZmTradView.prototype._createMailItemView =
function(params) {
	params.id = ZmId.getViewId(ZmId.VIEW_MSG, null, params.view);
	return new ZmMailMsgView(params);
};
