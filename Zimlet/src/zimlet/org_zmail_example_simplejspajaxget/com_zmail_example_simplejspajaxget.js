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

org_zmail_example_simplejspajaxget_HandlerObject = function() {
};
org_zmail_example_simplejspajaxget_HandlerObject.prototype = new ZmZimletBase;
org_zmail_example_simplejspajaxget_HandlerObject.prototype.constructor = org_zmail_example_simplejspajaxget_HandlerObject;

/**
 * Double clicked.
 */
org_zmail_example_simplejspajaxget_HandlerObject.prototype.doubleClicked =
function() {
	this.singleClicked();
};

/**
 * Single clicked.
 */
org_zmail_example_simplejspajaxget_HandlerObject.prototype.singleClicked =
function() {
	this._displayDialog();
};

/**
 * Displays the zimlet jsp page.
 * 
 */
org_zmail_example_simplejspajaxget_HandlerObject.prototype._displayDialog = 
function() {
	
	var jspUrl = this.getResource("jspfile.jsp");

	var response = AjxRpc.invoke(null, jspUrl, null, null, true);

	if (response.success == true) {
		appCtxt.getAppController().setStatusMsg(response.text);		
	}
	
};

