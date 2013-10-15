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
 * Package: Startup1_2
 * 
 * Together with Startup1_1, contains everything needed to support displaying
 * the results of the initial mail search. Startup1 was split into two smaller
 * packages becausing JS parsing cost increases exponentially, so it is best to
 * keep the files under 100K or so.
 */
AjxPackage.require("zmailMail.share.model.ZmObjectHandler");
AjxPackage.require("zmailMail.share.model.ZmObjectManager");
AjxPackage.require("zmailMail.share.model.ZmSettings");
AjxPackage.require("zmailMail.share.model.ZmMetaData");
AjxPackage.require("zmailMail.share.model.ZmKeyMap");
AjxPackage.require("zmailMail.share.model.ZmTimezone");
AjxPackage.require("zmailMail.share.model.ZmItem");
AjxPackage.require("zmailMail.share.model.ZmActionStack");
AjxPackage.require("zmailMail.share.model.ZmAction");
AjxPackage.require("zmailMail.share.model.ZmOrganizer");
AjxPackage.require("zmailMail.share.model.ZmFolder");
AjxPackage.require("zmailMail.share.model.ZmSearchFolder");
AjxPackage.require("zmailMail.share.model.ZmSearch");
AjxPackage.require("zmailMail.share.model.ZmSearchResult");
AjxPackage.require("zmailMail.share.model.ZmTag");
AjxPackage.require("zmailMail.share.model.ZmTree");
AjxPackage.require("zmailMail.share.model.ZmTagTree");
AjxPackage.require("zmailMail.share.model.ZmFolderTree");
AjxPackage.require("zmailMail.share.model.ZmList");
AjxPackage.require("zmailMail.share.model.ZmAccountList");
AjxPackage.require("zmailMail.share.model.ZmAccount");
AjxPackage.require("zmailMail.share.model.ZmZmailAccount");
AjxPackage.require("zmailMail.share.model.ZmInvite");
AjxPackage.require("zmailMail.share.model.ZmImAddress");
AjxPackage.require("zmailMail.share.model.ZmAccessControlList");
AjxPackage.require("zmailMail.share.model.ZmDomainList");
AjxPackage.require("zmailMail.share.model.ZmAttachmentTypeList");

AjxPackage.require("zmailMail.core.ZmApp");
AjxPackage.require("zmailMail.share.ZmSearchApp");

AjxPackage.require("zmailMail.share.view.ZmPopupMenu");
AjxPackage.require("zmailMail.share.view.ZmActionMenu");
AjxPackage.require("zmailMail.share.view.ZmToolBar");
AjxPackage.require("zmailMail.share.view.ZmButtonToolBar");
AjxPackage.require("zmailMail.share.view.ZmNavToolBar");
AjxPackage.require("zmailMail.share.view.ZmSearchToolBar");
AjxPackage.require("zmailMail.share.view.ZmSearchResultsToolBar");
AjxPackage.require("zmailMail.share.view.ZmSearchResultsFilterPanel");
AjxPackage.require("zmailMail.share.view.ZmTreeView");
AjxPackage.require("zmailMail.share.view.ZmTagMenu");
AjxPackage.require("zmailMail.share.view.ZmListView");
AjxPackage.require("zmailMail.share.view.ZmAppChooser");
AjxPackage.require("zmailMail.share.view.ZmAppButton");
AjxPackage.require("zmailMail.share.view.ZmStatusView");
AjxPackage.require("zmailMail.share.view.ZmOverviewContainer");
AjxPackage.require("zmailMail.share.view.ZmAccountOverviewContainer");
AjxPackage.require("zmailMail.share.view.ZmOverview");
AjxPackage.require("zmailMail.share.view.ZmUpsellView");
AjxPackage.require("zmailMail.share.view.ZmTimeSelect");
AjxPackage.require("zmailMail.share.view.dialog.ZmQuickAddDialog");

AjxPackage.require("zmailMail.share.controller.ZmController");
AjxPackage.require("zmailMail.share.controller.ZmBaseController");
AjxPackage.require("zmailMail.share.controller.ZmListController");
AjxPackage.require("zmailMail.share.controller.ZmTreeController");
AjxPackage.require("zmailMail.share.controller.ZmTagTreeController");
AjxPackage.require("zmailMail.share.controller.ZmFolderTreeController");
AjxPackage.require("zmailMail.share.controller.ZmSearchTreeController");
AjxPackage.require("zmailMail.share.controller.ZmShareTreeController");
AjxPackage.require("zmailMail.share.controller.ZmOverviewController");
AjxPackage.require("zmailMail.share.controller.ZmSearchController");
AjxPackage.require("zmailMail.share.controller.ZmSearchResultsController");
AjxPackage.require("zmailMail.share.controller.ZmActionController");

AjxPackage.require("zmailMail.im.model.ZmRoster");
AjxPackage.require("zmailMail.im.view.ZmImOverview");
AjxPackage.require("zmailMail.im.view.ZmTaskbar");
AjxPackage.require("zmailMail.im.controller.ZmTaskbarController");

AjxPackage.require("zmailMail.core.ZmAppViewMgr");
AjxPackage.require("zmailMail.core.ZmRequestMgr");
AjxPackage.require("zmailMail.core.ZmZmailMail");

AjxPackage.require("zmailMail.calendar.model.ZmCalBaseItem");
AjxPackage.require("zmailMail.calendar.model.ZmCalItem");
AjxPackage.require("zmailMail.tasks.model.ZmTask");

AjxPackage.require("zmailMail.prefs.ZmPreferencesApp");
AjxPackage.require("zmailMail.portal.ZmPortalApp");
AjxPackage.require("zmailMail.mail.ZmMailApp");
AjxPackage.require("zmailMail.calendar.ZmCalendarApp");
AjxPackage.require("zmailMail.tasks.ZmTasksApp");
AjxPackage.require("zmailMail.abook.ZmContactsApp");
AjxPackage.require("zmailMail.im.ZmImApp");
AjxPackage.require("zmailMail.briefcase.ZmBriefcaseApp");
AjxPackage.require("zmailMail.voicemail.ZmVoiceApp");