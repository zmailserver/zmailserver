/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
/*
 * Package: PreferencesCore
 * 
 * Supports: Loading of identities and data sources
 * 
 * Loaded: When identities and/or data sources arrive in a GetInfoResponse
 */
AjxPackage.require("zimbraMail.prefs.model.ZmPref");
AjxPackage.require("zimbraMail.prefs.model.ZmPrefPage");
AjxPackage.require("zimbraMail.prefs.model.ZmPersona");

AjxPackage.require("zimbraMail.prefs.controller.ZmPrefPageTreeController");

AjxPackage.require("ajax.dwt.widgets.DwtCalendar");
