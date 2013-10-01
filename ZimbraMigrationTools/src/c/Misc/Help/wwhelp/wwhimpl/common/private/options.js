/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite CSharp Client
 * Copyright (C) 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
// Copyright (c) 2001-2003 Quadralay Corporation.  All rights reserved.
//

function  WWHCommonSettings_Object()
{
  this.mTitle = "Exchange Migration Help";

  this.mbCookies            = true;
  this.mCookiesDaysToExpire = 30;
  this.mCookiesID           = "nqRhWFU09GU";

  this.mAccessible = "false";

  this.mbSyncContentsEnabled  = true;
  this.mbPrevEnabled          = true;
  this.mbNextEnabled          = true;
  this.mbRelatedTopicsEnabled = false;
  this.mbEmailEnabled         = false;
  this.mbPrintEnabled         = true;
  this.mbBookmarkEnabled      = false;
  this.mbPDFEnabled           = false;

  this.mEmailAddress = "";

  this.mRelatedTopics = new WWHCommonSettings_RelatedTopics_Object();
  this.mALinks        = new WWHCommonSettings_ALinks_Object();
  this.mPopup         = new WWHCommonSettings_Popup_Object();

  this.mbHighlightingEnabled        = true;
  this.mHighlightingForegroundColor = "#FFFFFF";
  this.mHighlightingBackgroundColor = "#333399";
}

function  WWHCommonSettings_RelatedTopics_Object()
{
  this.mWidth = 250;

  this.mTitleFontStyle       = "font-family: Verdana, Arial, Helvetica, sans-serif ; font-size: 10pt";
  this.mTitleForegroundColor = "#FFFFFF";
  this.mTitleBackgroundColor = "#999999";

  this.mFontStyle       = "font-family: Verdana, Arial, Helvetica, sans-serif ; font-size: 8pt";
  this.mForegroundColor = "#003399";
  this.mBackgroundColor = "#FFFFFF";
  this.mBorderColor     = "#666666";

  this.mbInlineEnabled = false;
  this.mInlineFontStyle = "font-family: Verdana, Arial, Helvetica, sans-serif ; font-size: 10pt";
  this.mInlineForegroundColor = "#003366";
}

function  WWHCommonSettings_ALinks_Object()
{
  this.mbShowBook = false;

  this.mWidth  = 250;
  this.mIndent = 17;

  this.mTitleFontStyle       = "font-family: Verdana, Arial, Helvetica, sans-serif ; font-size: 10pt";
  this.mTitleForegroundColor = "#FFFFFF";
  this.mTitleBackgroundColor = "#999999";

  this.mFontStyle       = "font-family: Verdana, Arial, Helvetica, sans-serif ; font-size: 8pt";
  this.mForegroundColor = "#003399";
  this.mBackgroundColor = "#FFFFFF";
  this.mBorderColor     = "#666666";
}

function  WWHCommonSettings_Popup_Object()
{
  this.mWidth = 200;

  this.mBackgroundColor = "#FFFFCC";
  this.mBorderColor     = "#999999";
}
