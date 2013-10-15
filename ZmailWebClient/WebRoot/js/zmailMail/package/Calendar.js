/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2006, 2007, 2008, 2010, 2011, 2012 VMware, Inc.
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
 * Package: Calendar
 * 
 * Supports: The Calendar application
 * 
 * Loaded:
 * 	- When the user goes to the Calendar application
 * 	- If the user creates an appointment
 * 	- If the user uses a date object to create an appointment
 * 	- If the user uses a date object to view a certain day
 * 
 * Any user of this package will need to load CalendarCore first.
 */
AjxPackage.require("ajax.dwt.events.DwtIdleTimer");

// for creating and handling invites
AjxPackage.require("zmailMail.mail.model.ZmMimePart");
AjxPackage.require("zmailMail.mail.model.ZmMailItem");
AjxPackage.require("zmailMail.mail.model.ZmMailMsg");
AjxPackage.require("zmailMail.mail.controller.ZmMailListController");
AjxPackage.require("zmailMail.mail.controller.ZmMsgController");
// base class for ZmApptView
AjxPackage.require("zmailMail.mail.view.ZmMailItemView");
AjxPackage.require("zmailMail.mail.view.ZmMailMsgView");

AjxPackage.require("zmailMail.calendar.view.ZmApptListView");
AjxPackage.require("zmailMail.calendar.view.ZmCalViewMgr");
AjxPackage.require("zmailMail.calendar.view.ZmCalBaseView");
AjxPackage.require("zmailMail.calendar.view.ZmCalColView");
AjxPackage.require("zmailMail.calendar.view.ZmCalDayView");
AjxPackage.require("zmailMail.calendar.view.ZmCalWorkWeekView");
AjxPackage.require("zmailMail.calendar.view.ZmCalWeekView");
AjxPackage.require("zmailMail.calendar.view.ZmCalMonthView");
AjxPackage.require("zmailMail.calendar.view.ZmCalScheduleView");
AjxPackage.require("zmailMail.calendar.view.ZmCalListView");
AjxPackage.require("zmailMail.calendar.view.ZmApptDeleteNotifyDialog");

AjxPackage.require("zmailMail.calendar.view.ZmCalPrintDialog");

AjxPackage.require("zmailMail.calendar.view.ZmCalItemView");
AjxPackage.require("zmailMail.calendar.controller.ZmCalendarTreeController");
