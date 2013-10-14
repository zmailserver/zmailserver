/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
//	WebHelp 5.10.001
// this value should be identical to the value used in whproxy.js
window.whname = "wh_stub";

// this file will be used by Topic and NavBar and NavPane and other components
// and this file is used in child frame html.
// and the whstub.js will be used in the start page.
// see reference in whstub.js.
// Internal Area
var gbInited = false;
var gWndStubPage = null;
function getStubPage()
{
	if (!gbInited)
	{
		gWndStubPage = getStubPage_inter(window);
		gbInited = true;
	}
	return gWndStubPage;
}

function getStubPage_inter(wCurrent) {
	if (null == wCurrent.parent || wCurrent.parent == wCurrent)
		return null;

	if (wCurrent.parent.whname && "wh_stub" == wCurrent.parent.whname) 
		return wCurrent.parent;
	else
		if (wCurrent.parent.frames.length != 0 && wCurrent.parent != wCurrent)
			return getStubPage_inter(wCurrent.parent);
		else 
			return null;
}

// Public interface begin here................
function registerListener(framename, nMessageId)
{
	var wStartPage = getStubPage();
	if (wStartPage && wStartPage != this) {
		return wStartPage.registerListener(framename, nMessageId);
	}
	else 
		return false;
}

function registerListener2(oframe, nMessageId)
{
	var wStartPage = getStubPage();
	if (wStartPage && wStartPage != this) {
		return wStartPage.registerListener2(oframe, nMessageId);
	}
	else 
		return false;
}

function unregisterListener2(oframe, nMessageId)
{
	var wStartPage = getStubPage();
	if (wStartPage && wStartPage != this && wStartPage.unregisterListener2) {
		return wStartPage.unregisterListener2(oframe, nMessageId);
	}
	else 
		return false;
}

function SendMessage(oMessage)
{
	var nMsgId = oMessage.nMessageId;
	if (nMsgId == WH_MSG_ISINFRAMESET && oMessage.wSender != this)
		return true;
	var wStartPage = getStubPage();
	if (wStartPage && wStartPage != this && wStartPage.SendMessage) 
	{
		return wStartPage.SendMessage(oMessage);
	}
	else 
		return false;
}
var gbWhProxy=true;