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
// const strings
var gaProj = new Array();
var gsRoot = "";

function setRoot(sRoot)
{
	gsRoot = sRoot
}

function aPE(sProjPath, sRootPath)
{
	gaProj[gaProj.length] = new tocProjEntry(sProjPath, sRootPath);
}

function tocProjEntry(sProjPath, sRootPath) 
{
	if(sProjPath.lastIndexOf("/")!=sProjPath.length-1)
		sProjPath+="/";	
	this.sPPath = sProjPath;
	this.sRPath = sRootPath;
}


function window_OnLoad()
{
	if (parent && parent != this && parent.projReady) {
		parent.projReady(gsRoot, gaProj);
	}
}
window.onload = window_OnLoad;