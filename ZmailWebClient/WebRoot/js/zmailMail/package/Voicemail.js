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
 * Package: Voicemail
 * 
 * Supports: The Voicemail application
 * 
 * Loaded:
 * 	- When the user goes to the Voicemail application
 * 	- If a search for voicemails returns results
 */

AjxPackage.require("ajax.util.AjxPluginDetector");

AjxPackage.require("ajax.dwt.core.DwtDragTracker");

AjxPackage.require("ajax.dwt.widgets.DwtBorderlessButton");
AjxPackage.require("ajax.dwt.widgets.DwtSlider");
AjxPackage.require("ajax.dwt.widgets.DwtSoundPlugin");

AjxPackage.require("zmailMail.abook.model.ZmContact");

AjxPackage.require("zmailMail.voicemail.model.ZmCallFeature");
AjxPackage.require("zmailMail.voicemail.model.ZmPhone");
AjxPackage.require("zmailMail.voicemail.model.ZmCallingParty");
AjxPackage.require("zmailMail.voicemail.model.ZmVoiceItem");
AjxPackage.require("zmailMail.voicemail.model.ZmCall");
AjxPackage.require("zmailMail.voicemail.model.ZmVoicemail");
AjxPackage.require("zmailMail.voicemail.model.ZmVoiceFolder");
AjxPackage.require("zmailMail.voicemail.model.ZmVoiceFolderTree");
AjxPackage.require("zmailMail.voicemail.model.ZmVoiceList");

AjxPackage.require("zmailMail.voicemail.view.ZmSoundPlayer");
AjxPackage.require("zmailMail.voicemail.view.ZmFlashAudioPlayer");

AjxPackage.require("zmailMail.voicemail.view.ZmVoiceListView");
AjxPackage.require("zmailMail.voicemail.view.ZmCallListView");

AjxPackage.require("zmailMail.voicemail.view.ZmVoicemailListView");
AjxPackage.require("zmailMail.voicemail.view.ZmMP3VoicemailListView");

AjxPackage.require("zmailMail.voicemail.view.ZmVoiceOverviewContainer");
AjxPackage.require("zmailMail.voicemail.view.ZmVoiceTreeView");
AjxPackage.require("zmailMail.voicemail.view.ZmMP3VoicemailListView");

AjxPackage.require("zmailMail.voicemail.controller.ZmVoiceListController");
AjxPackage.require("zmailMail.voicemail.controller.ZmCallListController");
AjxPackage.require("zmailMail.voicemail.controller.ZmVoicemailListController");
AjxPackage.require("zmailMail.voicemail.controller.ZmVoiceTreeController");

