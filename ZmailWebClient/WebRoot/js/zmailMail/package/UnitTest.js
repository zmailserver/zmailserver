/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2011, 2012 VMware, Inc.
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
 * The test modules will be run in the order they are listed below. The unit test files are loaded via
 * script tags so that the debugger has access to their source.
 */
AjxPackage.require({name:"zmailMail.unittest.UtZWCUtils",			method:AjxPackage.METHOD_SCRIPT_TAG});

AjxPackage.require({name:"zmailMail.unittest.UtMailMsgView_data",			method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtGetOriginalContent_data",	method:AjxPackage.METHOD_SCRIPT_TAG});

AjxPackage.require({name:"zmailMail.unittest.UtAjxUtil",		    method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtAjxStringUtil",		method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtAjxXslt",			method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtAjxTimezone",		method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtBubbles",			method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtCompose",			method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtContactGroup",		method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtMailListGroups",	method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtYouTube",			method:AjxPackage.METHOD_SCRIPT_TAG});
//AjxPackage.require({name:"zmailMail.unittest.UtSpeed",				method:AjxPackage.METHOD_SCRIPT_TAG});

AjxPackage.require({name:"zmailMail.unittest.UtPreferences",		method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtCalendar",			method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtContacts",			method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtMail",				method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtMailMsg",			method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtShare",				method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtSearch",			method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtPriorityInbox",     method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtMailMsgView",       method:AjxPackage.METHOD_SCRIPT_TAG});
AjxPackage.require({name:"zmailMail.unittest.UtGetOriginalContent", method:AjxPackage.METHOD_SCRIPT_TAG});

AjxPackage.require({name:"zmailMail.unittest.UtGeneral", method:AjxPackage.METHOD_SCRIPT_TAG});
