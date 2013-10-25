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

package com.zimbra.cs.activesync;

import com.zimbra.common.localconfig.LC;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.Log;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.server.ServerConfig;
import com.zimbra.cs.util.BuildInfo;
import com.zimbra.cs.util.Config;

import static com.zimbra.cs.account.Provisioning.*;

public class ActiveSyncConfig extends ServerConfig {
    private static final String PROTOCOL = "ACTIVESYNC";

    public ActiveSyncConfig(boolean ssl) {
        super(PROTOCOL, ssl);
    }

    @Override
    public String getServerName() {
        return getAttr(A_zimbraActiveSyncAdvertisedName, LC.zimbra_server_hostname.value());
    }

    @Override
    public String getServerVersion() {
        return getBooleanAttr(A_zimbraActiveSyncExposeVersionOnBanner, false) ?
            BuildInfo.VERSION : null;
    }

    @Override
    public String getBindAddress() {
        return getAttr(isSslEnabled() ?
            A_zimbraActiveSyncSSLBindAddress : A_zimbraActiveSyncBindAddress, null);
    }

    @Override
    public int getBindPort() {
        return isSslEnabled() ?
            getIntAttr(A_zimbraActiveSyncSSLBindPort, Config.D_ACTIVESYNC_SSL_BIND_PORT) :
            getIntAttr(A_zimbraActiveSyncBindPort, Config.D_ACTIVESYNC_BIND_PORT);
    }

    @Override
    public int getShutdownTimeout() {
       return getIntAttr(A_zimbraActiveSyncShutdownGraceSeconds, super.getShutdownTimeout());
    }

    @Override
    public int getMaxIdleTime() {
        return LC.activesync_max_idle_time.intValue();
    }

    @Override
    public int getMaxThreads() {
        return getIntAttr(A_zimbraActiveSyncNumThreads, super.getMaxThreads());
    }

    @Override
    public int getThreadKeepAliveTime() {
        return LC.activesync_thread_keep_alive_time.intValue();
    }

    @Override
    public int getMaxConnections() {
        return getIntAttr(A_zimbraActiveSyncMaxConnections, super.getMaxConnections());
    }

    @Override
    public int getWriteTimeout() {
        return LC.activesync_write_timeout.intValue();
    }

    @Override
    public Log getLog() {
        return ZimbraLog.pop;
    }

    @Override
    public String getConnectionRejected() {
        return "-ERR " + getDescription() + " closing connection; service busy";
    }

    /*
    public boolean isCleartextLoginsEnabled() {
        return getBooleanAttr(A_zimbraActiveSyncCleartextLoginEnabled, false);
    }

    public boolean isSaslGssapiEnabled() {
        return getBooleanAttr(A_zimbraActiveSyncSaslGssapiEnabled, false);
    }
    */

}
