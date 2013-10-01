/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2012 VMware, Inc.
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
/**
 * 
 */
package com.zimbra.common.util;

import com.zimbra.common.util.Log.Level;

public class AccountLogger {
    private String mAccountName;
    private String mCategory;
    private Level mLevel;
    
    public AccountLogger(String category, String accountName, Level level) {
        mCategory = category;
        mAccountName = accountName;
        mLevel = level;
    }
    
    public String getAccountName() { return mAccountName; }
    public String getCategory() { return mCategory; }
    public Level getLevel() { return mLevel; }
}