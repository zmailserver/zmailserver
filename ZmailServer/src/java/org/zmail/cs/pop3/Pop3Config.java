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

package org.zmail.cs.pop3;

import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Log;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.server.ServerConfig;
import org.zmail.cs.util.BuildInfo;
import org.zmail.cs.util.Config;

import static org.zmail.cs.account.Provisioning.*;

public class Pop3Config extends ServerConfig {
    private static final String PROTOCOL = "POP3";

    public Pop3Config(boolean ssl) {
        super(PROTOCOL, ssl);
    }

    @Override
    public String getServerName() {
        return getAttr(A_zmailPop3AdvertisedName, LC.zmail_server_hostname.value());
    }

    @Override
    public String getServerVersion() {
        return getBooleanAttr(A_zmailPop3ExposeVersionOnBanner, false) ?
            BuildInfo.VERSION : null;
    }

    @Override
    public String getBindAddress() {
        return getAttr(isSslEnabled() ?
            A_zmailPop3SSLBindAddress : A_zmailPop3BindAddress, null);
    }

    @Override
    public int getBindPort() {
        return isSslEnabled() ?
            getIntAttr(A_zmailPop3SSLBindPort, Config.D_POP3_SSL_BIND_PORT) :
            getIntAttr(A_zmailPop3BindPort, Config.D_POP3_BIND_PORT);
    }

    @Override
    public int getShutdownTimeout() {
       return getIntAttr(A_zmailPop3ShutdownGraceSeconds, super.getShutdownTimeout());
    }

    @Override
    public int getMaxIdleTime() {
        return LC.pop3_max_idle_time.intValue();
    }

    @Override
    public int getMaxThreads() {
        return getIntAttr(A_zmailPop3NumThreads, super.getMaxThreads());
    }

    @Override
    public int getThreadKeepAliveTime() {
        return LC.pop3_thread_keep_alive_time.intValue();
    }

    @Override
    public int getMaxConnections() {
        return getIntAttr(A_zmailPop3MaxConnections, super.getMaxConnections());
    }

    @Override
    public int getWriteTimeout() {
        return LC.pop3_write_timeout.intValue();
    }

    @Override
    public Log getLog() {
        return ZmailLog.pop;
    }

    @Override
    public String getConnectionRejected() {
        return "-ERR " + getDescription() + " closing connection; service busy";
    }

    public boolean isCleartextLoginsEnabled() {
        return getBooleanAttr(A_zmailPop3CleartextLoginEnabled, false);
    }

    public boolean isSaslGssapiEnabled() {
        return getBooleanAttr(A_zmailPop3SaslGssapiEnabled, false);
    }
}
