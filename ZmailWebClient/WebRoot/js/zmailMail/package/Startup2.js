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

AjxPackage.require("ajax.net.AjxInclude");
AjxPackage.require("ajax.util.AjxDateUtil");
AjxPackage.require("ajax.util.AjxSelectionManager");
AjxPackage.require("ajax.net.AjxPost");
AjxPackage.require("ajax.util.AjxBuffer");
AjxPackage.require("ajax.xslt.AjxXslt");
AjxPackage.require("ajax.util.AjxSHA1");
AjxPackage.require("ajax.dwt.events.DwtDateRangeEvent");
AjxPackage.require("ajax.dwt.widgets.DwtColorPicker");
AjxPackage.require("ajax.dwt.widgets.DwtCheckbox");
AjxPackage.require("ajax.dwt.widgets.DwtRadioButton");
AjxPackage.require("ajax.dwt.widgets.DwtHtmlEditor");
AjxPackage.require("ajax.dwt.widgets.DwtPasswordField");
AjxPackage.require("ajax.dwt.widgets.DwtCalendar");
AjxPackage.require("ajax.dwt.widgets.DwtPropertyPage");
AjxPackage.require("ajax.dwt.widgets.DwtTabView");
AjxPackage.require("ajax.dwt.widgets.DwtSelect");
AjxPackage.require("ajax.dwt.widgets.DwtAlert");
AjxPackage.require("ajax.dwt.widgets.DwtPropertySheet");
AjxPackage.require("ajax.dwt.widgets.DwtGrouper");
AjxPackage.require("ajax.dwt.widgets.DwtProgressBar");
AjxPackage.require("ajax.dwt.widgets.DwtPropertyEditor");
AjxPackage.require("ajax.dwt.widgets.DwtConfirmDialog");
AjxPackage.require("ajax.dwt.widgets.DwtChooser");
AjxPackage.require("ajax.dwt.widgets.DwtGridSizePicker");
AjxPackage.require("ajax.dwt.widgets.DwtSpinner");
AjxPackage.require("ajax.dwt.widgets.DwtButtonColorPicker");
AjxPackage.require("ajax.dwt.widgets.DwtMessageComposite");
AjxPackage.require("ajax.dwt.widgets.DwtRadioButtonGroup");
AjxPackage.require("ajax.dwt.widgets.DwtComboBox");

AjxPackage.require("zmail.common.ZLoginFactory");
AjxPackage.require("zmail.common.ZmBaseSplashScreen");
AjxPackage.require("zmail.common.ZmErrorDialog");

AjxPackage.require("zmailMail.share.model.ZmAuthenticate");
AjxPackage.require("zmailMail.share.model.ZmAutocomplete");
AjxPackage.require("zmailMail.share.model.ZmInvite");
AjxPackage.require("zmailMail.share.model.ZmSystemRetentionPolicy");

AjxPackage.require("zmailMail.share.view.ZmAutocompleteListView");
AjxPackage.require("zmailMail.share.view.ZmPeopleAutocompleteListView");
AjxPackage.require("zmailMail.share.view.ZmDLAutocompleteListView");
AjxPackage.require("zmailMail.share.view.ZmAddressInputField");

AjxPackage.require("zmailMail.share.view.ZmColorMenu");
AjxPackage.require("zmailMail.share.view.ZmColorButton");
AjxPackage.require("zmailMail.share.view.ZmFolderChooser");

AjxPackage.require("zmailMail.share.view.htmlEditor.ZmHtmlEditor");
AjxPackage.require("zmailMail.share.view.htmlEditor.ZmAdvancedHtmlEditor");
AjxPackage.require("zmailMail.share.view.ZmDragAndDrop");

AjxPackage.require("zmailMail.share.view.dialog.ZmDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmAttachDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmNewOrganizerDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmNewSearchDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmNewTagDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmFolderDialogTabView");
AjxPackage.require("zmailMail.share.view.dialog.ZmFolderPropertyView");
AjxPackage.require("zmailMail.share.view.dialog.ZmFolderRetentionView");
AjxPackage.require("zmailMail.share.view.dialog.ZmFolderPropsDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmQuickAddDialog");
AjxPackage.require("zmailMail.share.view.dialog.ZmTimeDialog");
AjxPackage.require("zmailMail.core.ZmNewWindow");
AjxPackage.require("zmailMail.core.ZmToolTipMgr");

AjxPackage.require("zmailMail.calendar.model.ZmCalMgr");
AjxPackage.require("zmailMail.tasks.model.ZmTaskMgr");
AjxPackage.require("zmailMail.calendar.model.ZmMiniCalCache");
AjxPackage.require("zmailMail.calendar.controller.ZmSnoozeBeforeProcessor");
AjxPackage.require("zmailMail.calendar.controller.ZmReminderController");
AjxPackage.require("zmailMail.calendar.view.ZmReminderDialog");
AjxPackage.require("zmailMail.calendar.view.ZmQuickReminderDialog");

AjxPackage.require("zmailMail.mail.view.ZmRetentionWarningDialog");

