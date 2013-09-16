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
var gIEA = new Array();
function aGE(sName, sDef)
{
	var len = gIEA.length;
	gIEA[len] = new gloEntry(sName, sDef);
}

function gloEntry(sName, sDef) 
{
	this.sName = sName;
	this.sDef = sDef;
	this.nNKOff = 0;
}

function window_OnLoad()
{
	if (parent && parent != this) {
		if (parent.putData) 
		{
			parent.putData(gIEA);
		}
	}
}

window.onload = window_OnLoad;