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
 * NewWindow, part 2
 * 
 * Special package to support actions in a new window. So far, there is
 * just composing a message and displaying a RFC attachment in a new window.
 * Everything that might be needed for those is in here: HTML editor,
 * contact picker (search, timezone, list view), msg view, attachments, etc.
 * 
 * NOTE: This package is not optimized at all - it contains everythings that
 * might possibly be needed in a new window.
 */
AjxPackage.require("ajax.dwt.keyboard.DwtTabGroupEvent");
AjxPackage.require("ajax.dwt.keyboard.DwtKeyMapMgr");
AjxPackage.require("ajax.dwt.keyboard.DwtKeyboardMgr");
AjxPackage.require("ajax.dwt.keyboard.DwtTabGroup");
AjxPackage.require("ajax.dwt.core.DwtId");
AjxPackage.require("ajax.dwt.dnd.DwtDragEvent");
AjxPackage.require("ajax.dwt.dnd.DwtDragSource");
AjxPackage.require("ajax.dwt.dnd.DwtDropEvent");
AjxPackage.require("ajax.dwt.dnd.DwtDropTarget");
AjxPackage.require("ajax.dwt.dnd.DwtDragBox");
AjxPackage.require("ajax.dwt.events.DwtDisposeEvent");

AjxPackage.require("ajax.dwt.widgets.DwtTreeItem");
AjxPackage.require("ajax.dwt.widgets.DwtHeaderTreeItem");
AjxPackage.require("ajax.dwt.widgets.DwtTree");
AjxPackage.require("ajax.dwt.widgets.DwtCheckbox");
AjxPackage.require("ajax.dwt.widgets.DwtRadioButton");
AjxPackage.require("ajax.dwt.widgets.DwtRadioButtonGroup");
AjxPackage.require("ajax.dwt.widgets.DwtForm");
AjxPackage.require("ajax.dwt.widgets.DwtCalendar");

AjxPackage.require("ajax.util.AjxDateUtil");

AjxPackage.require("zmail.csfe.ZmBatchCommand");
AjxPackage.require("zmail.csfe.ZmCsfeCommand");
AjxPackage.require("zmail.csfe.ZmCsfeException");
AjxPackage.require("zmail.csfe.ZmCsfeResult");

AjxPackage.require("zmailMail.core.ZmId");
AjxPackage.require("zmailMail.share.model.events.ZmEvent");
AjxPackage.require("zmailMail.share.model.events.ZmAppEvent");
AjxPackage.require("zmailMail.share.model.ZmModel");
AjxPackage.require("zmailMail.share.model.ZmSetting");
AjxPackage.require("zmailMail.share.model.ZmAccessControlList");
AjxPackage.require("zmailMail.share.model.ZmAutocomplete");
AjxPackage.require("zmailMail.core.ZmAppCtxt");
AjxPackage.require("zmailMail.core.ZmOperation");
AjxPackage.require("zmailMail.core.ZmMimeTable");

AjxPackage.require("zmailMail.share.model.ZmObjectHandler");
AjxPackage.require("zmailMail.share.model.ZmObjectManager");
AjxPackage.require("zmailMail.share.model.ZmSettings");
AjxPackage.require("zmailMail.share.model.ZmMetaData");
AjxPackage.require("zmailMail.share.model.ZmKeyMap");
AjxPackage.require("zmailMail.share.model.ZmTimezone");
AjxPackage.require("zmailMail.share.model.ZmItem");
AjxPackage.require("zmailMail.share.model.ZmOrganizer");
AjxPackage.require("zmailMail.share.model.ZmFolder");
AjxPackage.require("zmailMail.share.model.ZmSearch");
AjxPackage.require("zmailMail.share.model.ZmSearchResult");
AjxPackage.require("zmailMail.share.model.ZmTree");
AjxPackage.require("zmailMail.share.model.ZmFolderTree");
AjxPackage.require("zmailMail.share.model.ZmList");
AjxPackage.require("zmailMail.share.model.ZmAccountList");
AjxPackage.require("zmailMail.share.model.ZmAccount");
AjxPackage.require("zmailMail.share.model.ZmZmailAccount");
AjxPackage.require("zmailMail.share.model.ZmTimezone");
AjxPackage.require("zmailMail.share.model.ZmTag");
AjxPackage.require("zmailMail.share.model.ZmTree");

AjxPackage.require("zmailMail.core.ZmApp");

AjxPackage.require("zmailMail.share.view.ZmToolBar");
AjxPackage.require("zmailMail.share.view.ZmButtonToolBar");
AjxPackage.require("zmailMail.share.view.ZmPopupMenu");
AjxPackage.require("zmailMail.share.view.ZmActionMenu");
AjxPackage.require("zmailMail.share.view.ZmAutocompleteListView");
AjxPackage.require("zmailMail.share.view.ZmAddressInputField");
AjxPackage.require("zmailMail.share.view.ZmDLAutocompleteListView");
AjxPackage.require("zmailMail.share.view.ZmSearchToolBar");
AjxPackage.require("zmailMail.share.view.ZmStatusView");
AjxPackage.require("zmailMail.share.view.ZmTagMenu");
AjxPackage.require("zmailMail.share.view.ZmListView");
AjxPackage.require("zmailMail.share.view.ZmOverviewContainer");
AjxPackage.require("zmailMail.share.view.ZmAccountOverviewContainer");
AjxPackage.require("zmailMail.share.view.ZmOverview");
AjxPackage.require("zmailMail.share.view.ZmTreeView");
AjxPackage.require("zmailMail.share.view.ZmTimeSelect");
AjxPackage.require("zmailMail.share.view.ZmColorMenu");
AjxPackage.require("zmailMail.share.view.ZmColorButton");

AjxPackage.require("zmailMail.share.view.dialog.ZmDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmTimeDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmNewOrganizerDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmAttachDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmQuickAddDialog");

AjxPackage.require("zmailMail.share.view.htmlEditor.ZmHtmlEditor");
AjxPackage.require("zmailMail.share.view.htmlEditor.ZmAdvancedHtmlEditor");
AjxPackage.require("zmailMail.share.view.ZmDragAndDrop");

AjxPackage.require("zmailMail.share.controller.ZmController");
AjxPackage.require("zmailMail.share.controller.ZmBaseController");
AjxPackage.require("zmailMail.share.controller.ZmListController");
AjxPackage.require("zmailMail.share.controller.ZmTreeController");
AjxPackage.require("zmailMail.share.controller.ZmFolderTreeController");
AjxPackage.require("zmailMail.share.controller.ZmSearchController");
AjxPackage.require("zmailMail.share.controller.ZmOverviewController");

AjxPackage.require("zmailMail.core.ZmAppViewMgr");
AjxPackage.require("zmailMail.core.ZmRequestMgr");
AjxPackage.require("zmailMail.core.ZmZmailMail");
AjxPackage.require("zmailMail.core.ZmNewWindow");
AjxPackage.require("zmailMail.core.ZmToolTipMgr");

AjxPackage.require("zmailMail.prefs.ZmPreferencesApp");
AjxPackage.require("zmailMail.mail.ZmMailApp");
AjxPackage.require("zmailMail.calendar.ZmCalendarApp");
AjxPackage.require("zmailMail.tasks.ZmTasksApp");
AjxPackage.require("zmailMail.abook.ZmContactsApp");
AjxPackage.require("zmailMail.abook.model.ZmContact");
AjxPackage.require("zmailMail.briefcase.ZmBriefcaseApp");

AjxPackage.require("zmailMail.calendar.model.ZmCalBaseItem");
AjxPackage.require("zmailMail.calendar.model.ZmMiniCalCache");
AjxPackage.require("zmailMail.calendar.model.ZmCalMgr");

AjxPackage.require("zmailMail.share.model.ZmZmailAccount");
AjxPackage.require("zmailMail.mail.model.ZmDataSource");
AjxPackage.require("zmailMail.mail.model.ZmDataSourceCollection");
AjxPackage.require("zmailMail.mail.model.ZmPopAccount");
AjxPackage.require("zmailMail.mail.model.ZmImapAccount");
AjxPackage.require("zmailMail.mail.model.ZmIdentity");
AjxPackage.require("zmailMail.mail.model.ZmIdentityCollection");
AjxPackage.require("zmailMail.prefs.model.ZmPersona");

AjxPackage.require("zmailMail.mail.model.ZmMailItem");
AjxPackage.require("zmailMail.mail.model.ZmMailMsg");
AjxPackage.require("zmailMail.mail.model.ZmMimePart");
AjxPackage.require("zmailMail.mail.model.ZmMailList");
AjxPackage.require("zmailMail.mail.model.ZmIdentity");
AjxPackage.require("zmailMail.mail.view.ZmRecipients");
AjxPackage.require("zmailMail.mail.view.ZmComposeView");
AjxPackage.require("zmailMail.mail.view.ZmInviteMsgView");
AjxPackage.require("zmailMail.mail.view.ZmMailItemView");
AjxPackage.require("zmailMail.mail.view.ZmMailMsgView");
AjxPackage.require("zmailMail.mail.view.ZmMailConfirmView");
AjxPackage.require("zmailMail.mail.controller.ZmComposeController");
AjxPackage.require("zmailMail.mail.controller.ZmMailListController");
AjxPackage.require("zmailMail.mail.controller.ZmMsgController");
AjxPackage.require("zmailMail.mail.controller.ZmMailConfirmController");
AjxPackage.require("zmailMail.mail.view.ZmMailRedirectDialog");


AjxPackage.require("zmailMail.mail.view.object.ZmImageAttachmentObjectHandler");

AjxPackage.require("zmail.common.ZmErrorDialog");