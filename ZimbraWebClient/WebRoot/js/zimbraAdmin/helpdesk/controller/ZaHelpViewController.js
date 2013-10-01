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
* @class ZaHelpViewController 
* @contructor ZaHelpViewController
* @param appCtxt
* @param container
* @param app
* @author Greg Solovyev
**/
ZaHelpViewController = function(appCtxt, container) {

	ZaController.call(this, appCtxt, container, "ZaHelpViewController");
	this.tabConstructor = ZaHelpView;
}

ZaHelpViewController.prototype = new ZaController();
ZaHelpViewController.prototype.constructor = ZaHelpViewController;
ZaController.initToolbarMethods["ZaHelpViewController"] = new Array();


ZaHelpViewController.prototype.show = 
function(openInNewTab) {
    if (!this._contentView) {
		var elements = new Object();
		this._contentView = new this.tabConstructor(this._container);
		elements[ZaAppViewMgr.C_APP_CONTENT] = this._contentView;

        ZaApp.getInstance().getAppViewMgr().createView(this.getContentViewId(), elements) ;
		this._UICreated = true;
		ZaApp.getInstance()._controllers[this.getContentViewId ()] = this ;
	}
	ZaApp.getInstance().pushView(this.getContentViewId());
};
