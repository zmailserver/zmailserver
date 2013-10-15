/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
 * Package: CalendarCore
 * 
 * Supports: Minimal calendar functionality
 * 
 * Loaded:
 * 	- To display mini-calendar
 * 	- For reminders refresh
 * 	- If search for resources returns data
 */

// base classes for ZmResource and ZmResourceList
AjxPackage.require("zmailMail.abook.model.ZmContact");
AjxPackage.require("zmailMail.abook.model.ZmContactList");

AjxPackage.require("zmailMail.calendar.model.ZmCalendar");
AjxPackage.require("zmailMail.calendar.model.ZmRecurrence");
AjxPackage.require("zmailMail.calendar.model.ZmCalItem");
AjxPackage.require("zmailMail.calendar.model.ZmAppt");
AjxPackage.require("zmailMail.calendar.model.ZmApptList");
AjxPackage.require("zmailMail.calendar.model.ZmApptCache");
AjxPackage.require("zmailMail.calendar.model.ZmFreeBusyCache");
AjxPackage.require("zmailMail.calendar.model.ZmResource");
AjxPackage.require("zmailMail.calendar.model.ZmResourceList");
AjxPackage.require("zmailMail.calendar.view.ZmApptViewHelper");
AjxPackage.require("zmailMail.calendar.controller.ZmCalItemComposeController");
AjxPackage.require("zmailMail.calendar.controller.ZmApptComposeController");
AjxPackage.require("zmailMail.calendar.controller.ZmCalViewController");

