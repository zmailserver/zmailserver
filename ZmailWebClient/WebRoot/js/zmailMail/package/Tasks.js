/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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
 * Package: Tasks
 * 
 * Supports: The Tasks application
 * 
 * Loaded: When the user goes to the Tasks application
 */

// base class for ZmApptView
AjxPackage.require("zmailMail.mail.view.ZmMailMsgView");

AjxPackage.require("zmailMail.calendar.controller.ZmCalItemComposeController");

AjxPackage.require("zmailMail.calendar.model.ZmRecurrence");
AjxPackage.require("zmailMail.calendar.model.ZmCalItem");

AjxPackage.require("zmailMail.calendar.view.ZmApptRecurDialog");
AjxPackage.require("zmailMail.calendar.view.ZmApptViewHelper");
AjxPackage.require("zmailMail.calendar.view.ZmCalItemEditView");
AjxPackage.require("zmailMail.calendar.view.ZmCalItemView");

AjxPackage.require("zmailMail.calendar.controller.ZmCalendarTreeController");


AjxPackage.require("zmailMail.tasks.view.ZmTaskView");
AjxPackage.require("zmailMail.tasks.view.ZmTaskMultiView");
AjxPackage.require("zmailMail.tasks.view.ZmTaskEditView");
AjxPackage.require("zmailMail.tasks.view.ZmNewTaskFolderDialog");

AjxPackage.require("zmailMail.tasks.controller.ZmTaskController");
AjxPackage.require("zmailMail.tasks.controller.ZmTaskTreeController");
