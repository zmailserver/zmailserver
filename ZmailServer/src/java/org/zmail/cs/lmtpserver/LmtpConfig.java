/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.lmtpserver;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Log;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.server.ServerConfig;
import org.zmail.cs.util.BuildInfo;
import org.zmail.common.localconfig.LC;
import org.zmail.cs.util.Config;

import static org.zmail.cs.account.Provisioning.*;

public class LmtpConfig extends ServerConfig {
    private final LmtpBackend lmtpBackend;

    private static final String PROTOCOL = "LMTP";
    private static final int MAX_IDLE_TIME = 300; // seconds

    public static final LmtpConfig INSTANCE = new LmtpConfig();

    public static LmtpConfig getInstance() {
        return INSTANCE;
    }

    private LmtpConfig() {
        super(PROTOCOL, false);
        lmtpBackend = new ZmailLmtpBackend(this);
    }

    @Override
    public String getServerName() {
        return getAttr(A_zmailLmtpAdvertisedName, LC.zmail_server_hostname.value());
    }

    @Override
    public String getServerVersion() {
        return getBooleanAttr(A_zmailLmtpExposeVersionOnBanner, false) ?
            BuildInfo.VERSION : null;
    }

    @Override
    public int getMaxIdleTime() {
        return MAX_IDLE_TIME;
    }

    @Override
    public int getShutdownTimeout() {
       return getIntAttr(A_zmailLmtpShutdownGraceSeconds, super.getShutdownTimeout());
    }

    @Override
    public int getMaxThreads() {
        return getIntAttr(A_zmailLmtpNumThreads, super.getMaxThreads());
    }

    @Override
    public int getBindPort() {
        return getIntAttr(A_zmailLmtpBindPort, Config.D_LMTP_BIND_PORT);
    }

    @Override
    public String getBindAddress() {
        return getAttr(A_zmailLmtpBindAddress, null);
    }

    @Override
    public Log getLog() {
        return ZmailLog.lmtp;
    }

    @Override
    public String getConnectionRejected() {
        return "421 " + getDescription() + " closing connection; service busy";
    }

    public String getMtaRecipientDelimiter() {
        try {
            return getGlobalConfig().getAttr(A_zmailMtaRecipientDelimiter);
        } catch (ServiceException e) {
            getLog().warn("Unable to get global attribute: " + A_zmailMtaRecipientDelimiter, e);
            return null;
        }
    }

    public LmtpBackend getLmtpBackend() {
        return lmtpBackend;
    }

    public boolean isPermanentFailureWhenOverQuota() {
        return getBooleanAttr(A_zmailLmtpPermanentFailureWhenOverQuota, false);
    }
}
