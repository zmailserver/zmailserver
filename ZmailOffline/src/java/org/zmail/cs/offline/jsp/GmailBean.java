/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
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
package com.zimbra.cs.offline.jsp;

import com.zimbra.soap.type.DataSource.ConnectionType;

public class GmailBean extends ImapBean {
    public static final String Domain = "gmail.com";

    public GmailBean() {}
	
    @Override
    protected void doRequest() {
	domain = Domain;
        if (verb != null && (verb.isAdd() || verb.isModify())) {
            if (!isEmpty(email) && email.indexOf('@') < 0)
                email += '@' + domain;
	    username = email;
        }
	host = "imap.gmail.com";
        connectionType = ConnectionType.ssl;
	port = "993";

	smtpHost = "smtp.gmail.com";
	smtpPort = "465";
	isSmtpSsl = true;
	isSmtpAuth = true;
	smtpUsername = email;
	smtpPassword = password;
	super.doRequest();
    }

    public boolean isCalendarSyncSupported() {
	return true;
    }

    public boolean isContactSyncSupported() {
	return true;
    }

    public boolean isServerConfigSupported() {
	return false;
    }

    public boolean isSmtpConfigSupported() {
	return false;
    }

    public boolean isUsernameRequired() {
	return false;
    }
}
