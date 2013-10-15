/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
 * Package: CalendarAppt
 * 
 * Supports: The Calendar application
 * 
 * Loaded:
 * 	- When the user creates/edits an appointment
 * 	- If the user uses a date object to create an appointment
 * 
 * Any user of this package will need to load CalendarCore first.
 */

// for creating and handling invites

AjxPackage.require("zmailMail.calendar.view.ZmApptRecurDialog");
AjxPackage.require("zmailMail.calendar.view.ZmCalItemEditView");

AjxPackage.require("zmailMail.calendar.view.ZmCalItemTypeDialog");
AjxPackage.require("zmailMail.calendar.view.ZmApptComposeView");
AjxPackage.require("zmailMail.calendar.view.ZmApptEditView");
AjxPackage.require("zmailMail.calendar.view.ZmApptNotifyDialog");
AjxPackage.require("zmailMail.calendar.view.ZmResourceConflictDialog");

AjxPackage.require("zmailMail.calendar.view.ZmApptQuickAddDialog");
AjxPackage.require("zmailMail.calendar.view.ZmFreeBusySchedulerView");
AjxPackage.require("zmailMail.calendar.view.ZmNewCalendarDialog");
AjxPackage.require("zmailMail.calendar.view.ZmExternalCalendarDialog");
AjxPackage.require("zmailMail.calendar.view.ZmApptAssistantView");
AjxPackage.require("zmailMail.calendar.view.ZmScheduleAssistantView");
AjxPackage.require("zmailMail.calendar.view.ZmLocationAssistantView");
AjxPackage.require("zmailMail.calendar.view.ZmSuggestionsView");
AjxPackage.require("zmailMail.calendar.view.ZmTimeSuggestionView");
AjxPackage.require("zmailMail.calendar.view.ZmLocationSuggestionView");
AjxPackage.require("zmailMail.calendar.view.ZmTimeSuggestionPrefDialog");
AjxPackage.require("zmailMail.calendar.view.ZmResolveLocationConflictDialog");
AjxPackage.require("zmailMail.calendar.view.ZmResolveLocationView");
AjxPackage.require("zmailMail.calendar.view.ZmAttendeePicker");
AjxPackage.require("zmailMail.calendar.view.ZmMiniCalendar");

AjxPackage.require("zmailMail.calendar.controller.ZmCalItemComposeController");
AjxPackage.require("zmailMail.calendar.controller.ZmApptComposeController");
AjxPackage.require("zmailMail.calendar.controller.ZmApptController");
