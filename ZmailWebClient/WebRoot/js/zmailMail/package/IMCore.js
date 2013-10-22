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
 
AjxPackage.require("zimbraMail.im.model.ZmImService");
AjxPackage.require("zimbraMail.im.model.ZmZimbraImService");
AjxPackage.require("zimbraMail.im.model.ZmImGateway");
AjxPackage.require("zimbraMail.im.model.ZmRoster");
AjxPackage.require("zimbraMail.im.model.ZmRosterItem");
AjxPackage.require("zimbraMail.im.model.ZmRosterItemList");
AjxPackage.require("zimbraMail.im.model.ZmRosterPresence");
AjxPackage.require("zimbraMail.im.model.ZmChat");
AjxPackage.require("zimbraMail.im.model.ZmChatList");
AjxPackage.require("zimbraMail.im.model.ZmChatMessage");
AjxPackage.require("zimbraMail.im.model.ZmImPrivacyList");

AjxPackage.require("zimbraMail.im.controller.ZmImServiceController");
AjxPackage.require("zimbraMail.im.controller.ZmZimbraImServiceController");
