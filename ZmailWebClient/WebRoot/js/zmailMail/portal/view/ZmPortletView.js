/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
 * Creates the portlet view.
 * @class
 * This class represents the portlet view.
 * 
 * @param	{Element}		parentEl		the parent element
 * @param	{ZmPortlet}		portlet			the portlet
 * @param	{String}		className		the class name
 * 
 * @extends		DwtComposite
 */
ZmPortletView = function(parentEl, portlet, className) {
    className = className || "ZmPortlet";
    DwtComposite.call(this, {parent:DwtShell.getShell(window), className:className, posStyle:DwtControl.STATIC_STYLE});

    // save data
    this._portlet = portlet;
    this._portlet.view = this;

    this._contentsEl = this.getHtmlElement();
    if (parentEl) {
        parentEl.portlet = "loaded";
        parentEl.innerHTML = "";
        parentEl.appendChild(this._contentsEl);
    }

    // setup display
    this.setIcon(portlet.icon);
    this.setTitle(portlet.title);
    this.setContentUrl(portlet.actionUrl && portlet.actionUrl.target);
}
ZmPortletView.prototype = new DwtComposite;
ZmPortletView.prototype.constructor = ZmPortletView;

/**
 * Returns a string representation of the object.
 * 
 * @return		{String}		a string representation of the object
 */
ZmPortletView.prototype.toString = function() {
    return "ZmPortletView";
};

//
// Public methods
//

/**
 * Sets the icon.
 * 
 * @param	{String}	icon		the icon
 * 
 * @see		AjxImg.setImage
 */
ZmPortletView.prototype.setIcon = function(icon) {
    if (icon == null || !this._iconEl) return;
    AjxImg.setImage(this._iconEl, icon);
};

/**
 * Sets the title.
 * 
 * @param	{String}		title		the title
 */
ZmPortletView.prototype.setTitle = function(title) {
    if (title == null || !this._titleEl) return;
    this._titleEl.innerHTML = title;
};

ZmPortletView.prototype.setContent = function(content) {
    if (AjxUtil.isString(content)) {
        this._contentsEl.innerHTML = content;
    }
    else if (AjxUtil.ELEMENT_NODE) {
        this._contentsEl.innerHTML = "";
        this._contentsEl.appendChild(content);
    }
    else {
        this._contentsEl.innerHTML = AjxStringUtil.htmlEncode(String(content));
    }
};

/**
 * Sets the content URL as an iframe.
 * 
 * @param	{String}	url		the url
 */
ZmPortletView.prototype.setContentUrl = function(url) {
    if (!url) return;

    var props = this._portlet.properties;
    var func = AjxCallback.simpleClosure(ZmPortletView.__replaceProp, null, props);
    url = url.replace(ZmZimletContext.RE_SCAN_PROP, func);
    url = this._portlet.zimletCtxt ? this._portlet.zimletCtxt.makeURL({ target: url }, null, props) : url;
    var html = [
        "<iframe style='border:none;width:100%;height:100%' ",
            "src='",url,"'>",
        "</iframe>"
    ].join("");
    this.setContent(html);
};

//
// Private methods
//

ZmPortletView.__replaceProp = function(props, $0, $1, $2) {
    return props[$2];
};