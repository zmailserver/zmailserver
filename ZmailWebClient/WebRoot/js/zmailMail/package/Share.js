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
 * Package: Share
 * 
 * Supports: 
 * 	- Sharing folders with other users
 * 	- Handling links/mountpoints
 * 
 * Loaded:
 * 	- When share or mountpoint data arrives in a <refresh> block
 * 	- When user creates a share
 */
AjxPackage.require("zmailMail.share.model.ZmShare");
AjxPackage.require("zmailMail.share.model.ZmShareProxy");
AjxPackage.require("zmailMail.share.model.ZmMountpoint");

AjxPackage.require("zmailMail.share.view.ZmShareReply");
AjxPackage.require("zmailMail.share.view.ZmShareTreeView");

AjxPackage.require("zmailMail.share.view.dialog.ZmAcceptShareDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmDeclineShareDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmSharePropsDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmShareSearchDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmRevokeShareDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmFindnReplaceDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmFolderNotifyDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmTimezonePicker");
