/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Zimlets
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 *@Author Raja Rao DV
 */


function com_zimbra_speak() {
}

com_zimbra_speak.prototype = new ZmZimletBase();
com_zimbra_speak.prototype.constructor = com_zimbra_speak;


com_zimbra_speak.SPEAK = "SPEAK_ZIMLET";


com_zimbra_speak.prototype.init =
function() {
};

com_zimbra_speak.prototype.initializeToolbar =
function(app, toolbar, controller, view) {
	if (view == ZmId.VIEW_CONVLIST ||
		view == ZmId.VIEW_CONV ||
		view == ZmId.VIEW_TRAD)
	{
		var buttonIndex = -1;
		for (var i = 0, count = toolbar.opList.length; i < count; i++) {
			if (toolbar.opList[i] == ZmOperation.PRINT) {
				buttonIndex = i + 1;
				break;
			}
		}
		var buttonArgs = {
			tooltip: "Converts the selected message's text to speech",
			index: buttonIndex,
			image: "PlayingMessage"
		};
		var button = toolbar.createOp(com_zimbra_speak.SPEAK, buttonArgs);
		button.addSelectionListener(new AjxListener(this, this._buttonListener, [controller]));
	}
};

com_zimbra_speak.prototype._buttonListener =
function(controller) {
	var message = controller.getMsg();
	if (message) {
		AjxDispatcher.require([ "BrowserPlus" ]);
		var serviceObj = { service: "TextToSpeech" };
		var callback = new AjxCallback(this, this._serviceCallback, [message]);
		ZmBrowserPlus.getInstance().require(serviceObj, callback);
	}
};

com_zimbra_speak.prototype._serviceCallback =
function(message, service) {
	message.load({ callback: new AjxCallback(this, this._doIt, [message, service]) }) ;
};

com_zimbra_speak.prototype._doIt =
function(message, service) {
	var textPart = message.getBodyPart(ZmMimeTable.TEXT_PLAIN);
	this._speak(textPart ? textPart.getContent() : "The message is empty", service);
};

com_zimbra_speak.prototype._speak =
function(text, service) {
	service.Say({ utterance: text }, function() {});
};

