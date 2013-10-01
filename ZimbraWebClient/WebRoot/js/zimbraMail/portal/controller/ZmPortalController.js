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

/**
 * @overview
 * This file contains the portal controller class.
 */

/**
 * Creates the portal controller.
 * @class
 * This class represents the portal controller.
 * 
 * @param	{DwtComposite}	container	the containing element
 * @param	{ZmPortalApp}	app			the application
 * @param	{constant}		type		controller type
 * @param	{string}		sessionId	the session id
 * 
 * @extends		ZmListController
 */
ZmPortalController = function(container, app, type, sessionId) {
	if (arguments.length == 0) { return; }
	ZmListController.apply(this, arguments);

    // TODO: Where does this really belong? Answer: in ZmPortalApp
    ZmOperation.registerOp(ZmId.OP_PAUSE_TOGGLE, {textKey:"pause", image:"Pause", style: DwtButton.TOGGLE_STYLE});

    this._listeners[ZmOperation.REFRESH] = new AjxListener(this, this._refreshListener);
    this._listeners[ZmOperation.PAUSE_TOGGLE] = new AjxListener(this, this._pauseListener);
}
ZmPortalController.prototype = new ZmListController;
ZmPortalController.prototype.constructor = ZmPortalController;

ZmPortalController.prototype.isZmPortalController = true;
ZmPortalController.prototype.toString = function() { return "ZmPortalController"; };

//
// Public methods
//

ZmPortalController.prototype.getDefaultViewType = function() {
	return ZmId.VIEW_PORTAL;
};

ZmPortalController.prototype.show = function() {
	ZmListController.prototype.show.call(this);
	this._setup(this._currentViewId);

	var elements = this.getViewElements(this._currentViewId, this._listView[this._currentViewId]);

	this._setView({ view:		this._currentViewId,
					viewType:	this._currentViewType,
					elements:	elements,
					isAppView:	true});
};

/**
 * Sets the paused flag for the portlets.
 * 
 * @param	{Boolean}	paused		if <code>true</code>, pause the portlets
 */
ZmPortalController.prototype.setPaused = function(paused) {
    var view = this._listView[this._currentViewId];
    var portletIds = view && view.getPortletIds();
    if (portletIds && portletIds.length > 0) {
        var portletMgr = appCtxt.getApp(ZmApp.PORTAL).getPortletMgr();
        for (var i = 0; i < portletIds.length; i++) {
            var portlet = portletMgr.getPortletById(portletIds[i]);
            portlet.setPaused(paused);
        }
    }
};

//
// Protected methods
//

ZmPortalController.prototype._getToolBarOps = function() {
	return [ ZmOperation.REFRESH /*, ZmOperation.PAUSE_TOGGLE*/ ];
};

ZmPortalController.prototype._createNewView = function(view) {
	return new ZmPortalView(this._container, this, this._dropTgt);
};

ZmPortalController.prototype._setViewContents = function(view) {
	this._listView[view].set();
};

// listeners

ZmPortalController.prototype._refreshListener = function() {
    this._app.refreshPortlets();
};

ZmPortalController.prototype._pauseListener = function(event) {
    var toolbar = this._toolbar[this._currentViewId];

    // en/disable refresh button
    var button = toolbar && toolbar.getButton(ZmOperation.REFRESH);
    var paused = event.item.isToggled();
    if (button) {
        button.setEnabled(!paused);
    }

    // pause portlets appearing on portal page
    this.setPaused(paused);
};

ZmPortalController.prototype._resetOperations = function(parent, num) {
//    ZmListController.prototype._resetOperations.call(parent, num);
    parent.enable(this._getToolBarOps(), true);
};