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

org_zmail_example_soap_HandlerObject = function() {
};

org_zmail_example_soap_HandlerObject.prototype = new ZmZimletBase;
org_zmail_example_soap_HandlerObject.prototype.constructor = org_zmail_example_soap_HandlerObject;

/**
 * This method is called by the Zimlet framework when a context menu item is selected.
 * 
 */
org_zmail_example_soap_HandlerObject.prototype.menuItemSelected = 
function(itemId) {
	switch (itemId) {
		case "menuId_soap_request_xml":
			this._submitSOAPRequestXML();
			break;
		case "menuId_soap_request_json":
			this._submitSOAPRequestJSON();
			break;
	}
};

/**
 * Submits a SOAP request in XML format.
 * 
 * <GetAccountInfoRequest xmlns="urn:zmailAccount">
 *     <account by="name">user1</account>
 * </GetAccountInfoRequest>
 *
 * @private
 */
org_zmail_example_soap_HandlerObject.prototype._submitSOAPRequestXML =
function() {
		
	var soapDoc = AjxSoapDoc.create("GetAccountInfoRequest", "urn:zmailAccount");

	var accountNode = soapDoc.set("account", appCtxt.getUsername());
	accountNode.setAttribute("by", "name");
	
	var params = {
			soapDoc: soapDoc,
			asyncMode: true,
			callback: (new AjxCallback(this, this._handleSOAPResponseXML)),
			errorCallback: (new AjxCallback(this, this._handleSOAPErrorResponseXML))
			};

	appCtxt.getAppController().sendRequest(params);
};

/**
 * Handles the SOAP response.
 * 
 * @param	{ZmCsfeResult}		result		the result
 * @private
 */
org_zmail_example_soap_HandlerObject.prototype._handleSOAPResponseXML =
function(result) {

	if (result.isException()) {
		// do something with exception
		var exception = result.getException();		

		return;
	}
	
	// do something with response (in JSON format)
	var response = result.getResponse().GetAccountInfoResponse;

	var name = response.name;
	var soapURL = response.publicURL;
	var soapURL = response.soapURL;
	var zmailId = result.getResponse().GetAccountInfoResponse._attrs.zmailId;
	var zmailMailHost = result.getResponse().GetAccountInfoResponse._attrs.zmailMailHost;
	
	appCtxt.setStatusMsg("GetAccountInfoResponse (XML) success - "+name);	
};

/**
 * Handles the SOAP error response.
 * 
 * @param	{ZmCsfeException}		ex		the exception
 * @private
 */
org_zmail_example_soap_HandlerObject.prototype._handleSOAPErrorResponseXML =
function(ex) {

	var errorMsg = ex.getErrorMsg(); // the error message
	var dump = ex.dump(); // the complete error dump

};

/**
 * Submits a SOAP request in JSON format.
 * 
 * 
 * GetAccountInfoRequest: {
 *   _jsns: "urn:zmailAccount",
 *   account: {
 *     _content: "user1",
 *     by: "name"
 *    }
 * }
 *
 * @private
 */
org_zmail_example_soap_HandlerObject.prototype._submitSOAPRequestJSON =
function() {

	var jsonObj = {GetAccountInfoRequest:{_jsns:"urn:zmailAccount"}};
	var	request = jsonObj.GetAccountInfoRequest;
	request.account = {_content: appCtxt.getUsername(), by: "name"};
	
	var params = {
			jsonObj:jsonObj,
			asyncMode:true,
			callback: (new AjxCallback(this, this._handleSOAPResponseJSON)),
			errorCallback: (new AjxCallback(this, this._handleSOAPErrorResponseJSON))
		};
	
	return appCtxt.getAppController().sendRequest(params);

};

/**
 * Handles the SOAP response.
 * 
 * @param	{ZmCsfeResult}		result		the result
 * @private
 */
org_zmail_example_soap_HandlerObject.prototype._handleSOAPResponseJSON =
function(result) {

	if (result.isException()) {
		// do something with exception
		var exception = result.getException();		

		return;
	}
	
	// do something with response (in JSON format)
	var response = result.getResponse().GetAccountInfoResponse;

	var name = response.name;
	var soapURL = response.publicURL;
	var soapURL = response.soapURL;
	var zmailId = result.getResponse().GetAccountInfoResponse._attrs.zmailId;
	var zmailMailHost = result.getResponse().GetAccountInfoResponse._attrs.zmailMailHost;
	
	appCtxt.setStatusMsg("GetAccountInfoResponse (JSON) success - "+name);	
};

/**
 * Handles the SOAP error response.
 * 
 * @param	{ZmCsfeException}		ex		the exception
 * @private
 */
org_zmail_example_soap_HandlerObject.prototype._handleSOAPErrorResponseJSON =
function(ex) {

	var errorMsg = ex.getErrorMsg(); // the error message
	var dump = ex.dump(); // the complete error dump

};
