/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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

package com.zimbra.cs.client.soap;

import java.util.HashMap;

public class LmcGetInfoResponse extends LmcSoapResponse {

    private String mAcctName;
    private String mLifetime;
    private HashMap mPrefMap;

    public HashMap getPrefMap() { return mPrefMap; }
    public String getLifetime() { return mLifetime; }
    public String getAcctName() { return mAcctName; }

    public void setPrefMap(HashMap p) { mPrefMap = p; }
    public void setLifetime(String l) { mLifetime = l; }
    public void setAcctName(String a) { mAcctName = a; }
}
