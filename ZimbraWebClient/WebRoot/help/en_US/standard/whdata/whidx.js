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
var gaFileMapping = new Array();
function fileMapping(sBK, sEK, sFileName, nNum)
{
	this.sBK = sBK;
	this.sEK = sEK;
	this.sFileName = sFileName;
	this.aKs = null;
	this.nNum = nNum;
	this.oUsedItems = null;
}


function iFM(sBK, sEK, sFileName, nNum)
{
	var i = gaFileMapping.length;
	gaFileMapping[i] = new fileMapping(sBK, sEK, sFileName, nNum);	
	if (i == 0) {
		gaFileMapping[i].nTotal = nNum;
	}
	else {
		gaFileMapping[i].nTotal = nNum + gaFileMapping[i - 1].nTotal;
	}
}

function window_OnLoad()
{
	if (parent && parent != this && parent.projReady)
	{
		parent.projReady(gaFileMapping);
	}		
}

window.onload = window_OnLoad;
