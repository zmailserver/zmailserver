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
 * Package: MailCore
 * 
 * Supports: The Mail application msg and conv list views
 * 
 * Loaded:
 * 	- When the user goes to the Mail application (typically on startup)
 */
AjxPackage.require("zmailMail.mail.model.ZmMailItem");
AjxPackage.require("zmailMail.mail.model.ZmConv");
AjxPackage.require("zmailMail.mail.model.ZmMailMsg");
AjxPackage.require("zmailMail.mail.model.ZmMimePart");
AjxPackage.require("zmailMail.mail.model.ZmMailList");

AjxPackage.require("zmailMail.mail.view.object.ZmImageAttachmentObjectHandler");

AjxPackage.require("zmailMail.mail.view.ZmMailListView");
AjxPackage.require("zmailMail.mail.view.ZmMailItemView");
AjxPackage.require("zmailMail.mail.view.ZmDoublePaneView");
AjxPackage.require("zmailMail.mail.view.ZmTradView");
AjxPackage.require("zmailMail.mail.view.ZmInviteMsgView");
AjxPackage.require("zmailMail.mail.view.ZmMailMsgView");
AjxPackage.require("zmailMail.mail.view.ZmMailMsgListView");
AjxPackage.require("zmailMail.mail.view.ZmConvListView");
AjxPackage.require("zmailMail.mail.view.ZmMailListSectionHeader");
AjxPackage.require("zmailMail.mail.view.ZmConvView2");
AjxPackage.require("zmailMail.mail.view.ZmRecipients");
AjxPackage.require("zmailMail.mail.view.ZmMailRedirectDialog");

AjxPackage.require("zmailMail.mail.controller.ZmMailFolderTreeController");
AjxPackage.require("zmailMail.mail.controller.ZmMailListController");
AjxPackage.require("zmailMail.mail.controller.ZmDoublePaneController");
AjxPackage.require("zmailMail.mail.controller.ZmConvListController");
AjxPackage.require("zmailMail.mail.controller.ZmTradController");
AjxPackage.require("zmailMail.mail.controller.ZmMsgController");

AjxPackage.require("zmailMail.mail.model.ZmIdentity");
AjxPackage.require("zmailMail.mail.model.ZmIdentityCollection");
AjxPackage.require("zmailMail.mail.model.ZmDataSource");
AjxPackage.require("zmailMail.mail.model.ZmDataSourceCollection");
AjxPackage.require("zmailMail.mail.model.ZmMailListGroup");
AjxPackage.require("zmailMail.mail.model.ZmMailListDateGroup");
AjxPackage.require("zmailMail.mail.model.ZmMailListFromGroup");
AjxPackage.require("zmailMail.mail.model.ZmMailListPriorityGroup");
AjxPackage.require("zmailMail.mail.model.ZmMailListSizeGroup");
AjxPackage.require("zmailMail.mail.model.ZmPopAccount");
AjxPackage.require("zmailMail.mail.model.ZmImapAccount");
AjxPackage.require("zmailMail.mail.model.ZmSignature");
AjxPackage.require("zmailMail.mail.model.ZmSignatureCollection");
