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
package com.zimbra.cs.mailclient.smtp;

import com.google.common.base.Objects;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.mailclient.MailConfig;

/**
 * SMTP client configuration.
 */
public final class SmtpConfig extends MailConfig {
    public static final String PROTOCOL = "smtp";
    public static final int DEFAULT_PORT = 25;
    public static final int DEFAULT_SSL_PORT = 465;

    private String domain;
    private boolean allowPartialSend;
    private String dsn;

    public SmtpConfig(String host, int port, String domain) {
        super(ZimbraLog.smtp, host);
        setPort(port);
        setDomain(domain);
    }

    public SmtpConfig(String host) {
        super(ZimbraLog.smtp, host);
        setPort(DEFAULT_PORT);
    }

    public SmtpConfig() {
        super(ZimbraLog.smtp);
    }

    @Override
    public String getProtocol() {
        return PROTOCOL;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return Objects.firstNonNull(domain, "localhost");
    }

    public void setAllowPartialSend(boolean allow) {
        this.allowPartialSend = allow;
    }

    public boolean isPartialSendAllowed() {
        return allowPartialSend;
    }

    public void setDsn(String dsn) {
        this.dsn = dsn;
    }

    public String getDsn() {
        return dsn;
    }
}
