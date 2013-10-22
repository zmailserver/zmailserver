/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.offline.jsp;

import org.zmail.soap.admin.type.DataSourceType;

public class ImapBean extends XmailBean {

    protected String oauthURL = "";
    protected String oauthVerifier = "";
    protected String oauthTmpId = "";

    public ImapBean() {
        port = "143";
        type = DataSourceType.imap.toString();
    }

    public boolean isFolderSyncSupported() {
        return true;
    }

    public String getOauthURL() {
        return oauthURL;
    }

    public void setOauthURL(String oauthURL) {
        this.oauthURL = oauthURL;
    }

    public String getOauthVerifier() {
        return oauthVerifier;
    }

    public void setOauthVerifier(String oauthVerifier) {
        this.oauthVerifier = oauthVerifier;
    }

    public String getOauthTmpId() {
        return oauthTmpId;
    }

    public void setOauthTmpId(String oauthTmpId) {
        this.oauthTmpId = oauthTmpId;
    }
}
