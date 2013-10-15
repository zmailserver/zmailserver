/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
 * Package: Preferences
 * 
 * Supports: The Options (preferences) application
 * 
 * Loaded:
 * 	- When the user goes to the Options application
 * 	- When the user creates a filter rule from message headers
 */
AjxPackage.require("zmailMail.prefs.model.ZmFilterRule");
AjxPackage.require("zmailMail.prefs.model.ZmFilterRules");
AjxPackage.require("zmailMail.prefs.model.ZmLocale");
AjxPackage.require("zmailMail.prefs.model.ZmMobileDevice");

AjxPackage.require("zmailMail.mail.model.ZmIdentity");
AjxPackage.require("zmailMail.mail.model.ZmIdentityCollection");
AjxPackage.require("zmailMail.mail.model.ZmDataSource");
AjxPackage.require("zmailMail.mail.model.ZmDataSourceCollection");
AjxPackage.require("zmailMail.mail.model.ZmPopAccount");
AjxPackage.require("zmailMail.mail.model.ZmImapAccount");
AjxPackage.require("zmailMail.mail.model.ZmSignature");
AjxPackage.require("zmailMail.mail.model.ZmSignatureCollection");

AjxPackage.require("zmailMail.prefs.view.ZmPreferencesPage");
AjxPackage.require("zmailMail.prefs.view.ZmShortcutsPage");
AjxPackage.require("zmailMail.prefs.view.ZmBackupPage");
AjxPackage.require("zmailMail.prefs.view.ZmPrefView");
AjxPackage.require("zmailMail.prefs.view.ZmFilterRulesView");
AjxPackage.require("zmailMail.prefs.view.ZmFilterRuleDialog");
AjxPackage.require("zmailMail.prefs.view.ZmZimletsPage");
AjxPackage.require("zmailMail.prefs.view.ZmMobileDevicesPage");
AjxPackage.require("zmailMail.prefs.view.ZmPriorityMessageFilterDialog");
AjxPackage.require("zmailMail.prefs.view.ZmSharingPage");
AjxPackage.require("zmailMail.prefs.view.ZmFilterPage");
AjxPackage.require("zmailMail.prefs.view.ZmNotificationsPage");
AjxPackage.require("zmailMail.prefs.view.ZmActivityStreamPromptDialog");
AjxPackage.require("zmailMail.prefs.view.ZmActivityToInboxPromptDialog");

AjxPackage.require("zmailMail.calendar.view.ZmCalendarPrefsPage");

AjxPackage.require("zmailMail.mail.view.prefs.ZmAccountsPage");
AjxPackage.require("zmailMail.mail.view.prefs.ZmAccountTestDialog");
AjxPackage.require("zmailMail.mail.view.prefs.ZmMailPrefsPage");
AjxPackage.require("zmailMail.mail.view.prefs.ZmSignaturesPage");
AjxPackage.require("zmailMail.mail.view.prefs.ZmTrustedPage");

AjxPackage.require("zmailMail.im.view.prefs.ZmImGatewayControl");

AjxPackage.require("zmailMail.prefs.controller.ZmPrefController");
AjxPackage.require("zmailMail.prefs.controller.ZmFilterController");
AjxPackage.require("zmailMail.prefs.controller.ZmFilterRulesController");
AjxPackage.require("zmailMail.prefs.controller.ZmMobileDevicesController");

AjxPackage.require("zmailMail.share.controller.ZmProgressController");