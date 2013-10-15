/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
 * Package: IM
 * 
 * Supports: The IM (chat) application
 * 
 * Loaded: upon IM notifications
 */

AjxPackage.require("ajax.dwt.events.DwtIdleTimer");
AjxPackage.require("ajax.util.AjxPluginDetector");
 
AjxPackage.require("zmailMail.im.model.ZmImService");
AjxPackage.require("zmailMail.im.model.ZmZmailImService");
AjxPackage.require("zmailMail.im.model.ZmImGateway");
AjxPackage.require("zmailMail.im.model.ZmRoster");
AjxPackage.require("zmailMail.im.model.ZmRosterItem");
AjxPackage.require("zmailMail.im.model.ZmRosterItemList");
AjxPackage.require("zmailMail.im.model.ZmRosterPresence");
AjxPackage.require("zmailMail.im.model.ZmChat");
AjxPackage.require("zmailMail.im.model.ZmChatList");
AjxPackage.require("zmailMail.im.model.ZmChatMessage");
AjxPackage.require("zmailMail.im.model.ZmImPrivacyList");

AjxPackage.require("zmailMail.im.controller.ZmImServiceController");
AjxPackage.require("zmailMail.im.controller.ZmZmailImServiceController");
