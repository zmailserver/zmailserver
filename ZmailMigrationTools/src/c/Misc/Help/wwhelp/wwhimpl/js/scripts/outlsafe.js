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
// Copyright (c) 2000-2011 Quadralay Corporation.  All rights reserved.
//

function  WWHOutlineImagingSafe_Object()
{
  this.mIterator    = new WWHOutlineIterator_Object(true);
  this.mImageSrcDir = WWHOutlineImaging_ImageSrcDir();
  this.mEventString = WWHPopup_EventString();
  this.mHTMLSegment = new WWHStringBuffer_Object();

  this.fGetIconURL     = WWHOutlineImaging_GetIconURL;
  this.fGetPopupAction = WWHOutlineImaging_GetPopupAction;
  this.fGetLink        = WWHOutlineImaging_GetLink;
  this.fGetEntryHTML   = WWHOutlineImaging_GetEntryHTML;

  this.fGenerateStyles = WWHOutlineImagingSafe_GenerateStyles;
  this.fReset          = WWHOutlineImagingSafe_Reset;
  this.fAdvance        = WWHOutlineImagingSafe_Advance;
  this.fOpenLevel      = WWHOutlineImagingSafe_OpenLevel;
  this.fCloseLevel     = WWHOutlineImagingSafe_CloseLevel;
  this.fSameLevel      = WWHOutlineImagingSafe_SameLevel;
  this.fDisplayEntry   = WWHOutlineImagingSafe_DisplayEntry;
  this.fUpdateEntry    = WWHOutlineImagingSafe_UpdateEntry;
  this.fRevealEntry    = WWHOutlineImagingSafe_RevealEntry;
}

function  WWHOutlineImagingSafe_GenerateStyles()
{
  var  StyleBuffer = new WWHStringBuffer_Object();
  var  MaxLevel;
  var  Level;


  StyleBuffer.fAppend("<style type=\"text/css\">\n");
  StyleBuffer.fAppend(" <!--\n");
  StyleBuffer.fAppend("  a:active\n");
  StyleBuffer.fAppend("  {\n");
  StyleBuffer.fAppend("    text-decoration: none;\n");
  StyleBuffer.fAppend("    background-color: " + WWHFrame.WWHJavaScript.mSettings.mTOC.mHighlightColor + ";\n");
  StyleBuffer.fAppend("    " + WWHFrame.WWHJavaScript.mSettings.mTOC.mFontStyle + ";\n");
  StyleBuffer.fAppend("  }\n");
  StyleBuffer.fAppend("  a:hover\n");
  StyleBuffer.fAppend("  {\n");
  StyleBuffer.fAppend("    text-decoration: underline;\n");
  StyleBuffer.fAppend("    color: " + WWHFrame.WWHJavaScript.mSettings.mTOC.mEnabledColor + ";\n");
  StyleBuffer.fAppend("    " + WWHFrame.WWHJavaScript.mSettings.mTOC.mFontStyle + ";\n");
  StyleBuffer.fAppend("  }\n");
  StyleBuffer.fAppend("  a\n");
  StyleBuffer.fAppend("  {\n");
  StyleBuffer.fAppend("    text-decoration: none;\n");
  StyleBuffer.fAppend("    color: " + WWHFrame.WWHJavaScript.mSettings.mTOC.mEnabledColor + ";\n");
  StyleBuffer.fAppend("    " + WWHFrame.WWHJavaScript.mSettings.mTOC.mFontStyle + ";\n");
  StyleBuffer.fAppend("  }\n");
  StyleBuffer.fAppend("  td\n");
  StyleBuffer.fAppend("  {\n");
  StyleBuffer.fAppend("    margin-top: 0pt;\n");
  StyleBuffer.fAppend("    margin-bottom: 0pt;\n");
  StyleBuffer.fAppend("    margin-left: 0pt;\n");
  StyleBuffer.fAppend("    margin-right: 0pt;\n");
  StyleBuffer.fAppend("    text-align: left;\n");
  StyleBuffer.fAppend("    vertical-align: middle;\n");
  StyleBuffer.fAppend("    " + WWHFrame.WWHJavaScript.mSettings.mTOC.mFontStyle + ";\n");
  StyleBuffer.fAppend("  }\n");
  StyleBuffer.fAppend(" -->\n");
  StyleBuffer.fAppend("</style>\n");

  return StyleBuffer.fGetBuffer();
}

function  WWHOutlineImagingSafe_Reset()
{
  this.mIterator.fReset(WWHFrame.WWHOutline.mTopEntry);
}

function  WWHOutlineImagingSafe_Advance(ParamMaxHTMLSegmentSize)
{
  var  Entry;


  this.mHTMLSegment.fReset();
  while (((ParamMaxHTMLSegmentSize == -1) ||
          (this.mHTMLSegment.fSize() < ParamMaxHTMLSegmentSize)) &&
         (this.mIterator.fAdvance(this)))
  {
    Entry = this.mIterator.mEntry;

    // Process current entry
    //
    if (Entry.mbShow)
    {
      this.mHTMLSegment.fAppend(this.fDisplayEntry(Entry));
    }
  }

  return (this.mHTMLSegment.fSize() > 0);  // Return true if segment created
}

function  WWHOutlineImagingSafe_OpenLevel()
{
}

function  WWHOutlineImagingSafe_CloseLevel(bParamScopeComplete)
{
}

function  WWHOutlineImagingSafe_SameLevel()
{
}

function  WWHOutlineImagingSafe_DisplayEntry(ParamEntry)
{
  var  VarEntryHTML = "";


  VarEntryHTML += this.fGetEntryHTML(ParamEntry);
  VarEntryHTML += "\n";

  return VarEntryHTML;
}

function  WWHOutlineImagingSafe_UpdateEntry(ParamEntry)
{
  var  EntryURL;


  // Reload page to display expanded/collapsed entry
  //
  WWHFrame.WWHJavaScript.mPanels.fReloadView();
}

function  WWHOutlineImagingSafe_RevealEntry(ParamEntry,
                                            bParamVisible)
{
  var  ParentEntry;
  var  LastClosedParentEntry = null;


  // Expand out enclosing entries
  //
  ParentEntry = ParamEntry.mParent;
  while (ParentEntry != null)
  {
    if ( ! ParentEntry.mbExpanded)
    {
      ParentEntry.mbExpanded = true;
      LastClosedParentEntry = ParentEntry;
    }

    ParentEntry = ParentEntry.mParent;
  }

  // Set target entry
  //
  WWHFrame.WWHOutline.mPanelAnchor = "t" + ParamEntry.mID;

  // Update display
  //
  if (bParamVisible)
  {
    // Update display if entry not already visible
    //
    if (LastClosedParentEntry != null)
    {
      this.fUpdateEntry(ParamEntry);
    }
    else
    {
      // Display target
      //
      WWHFrame.WWHJavaScript.mPanels.fJumpToAnchor();
    }
  }
}