/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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

import java.util.HashMap;
import java.util.Map;

import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.stats.RealtimeStatsCallback;
import org.zmail.cs.stats.ZmailPerf;
import org.zmail.cs.server.ProtocolHandler;
import org.zmail.cs.server.ServerThrottle;
import org.zmail.cs.server.TcpServer;

public final class TcpLmtpServer extends TcpServer implements LmtpServer, RealtimeStatsCallback {
    public TcpLmtpServer(LmtpConfig config) throws ServiceException {
        super(config);
        ZmailPerf.addStatsCallback(this);
        ServerThrottle.configureThrottle(config.getProtocol(), LC.lmtp_throttle_ip_limit.intValue(), 0, getThrottleSafeHosts());
    }

    @Override
    public String getName() {
        return "LmtpServer";
    }

    @Override
    protected ProtocolHandler newProtocolHandler() {
        return new TcpLmtpHandler(this);
    }

    @Override
    public LmtpConfig getConfig() {
        return (LmtpConfig) super.getConfig();
    }

    /**
     * Implementation of {@link RealtimeStatsCallback} that returns the number
     * of active handlers and number of threads for this server.
     */
    @Override
    public Map<String, Object> getStatData() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(ZmailPerf.RTS_LMTP_CONN, numActiveHandlers());
        data.put(ZmailPerf.RTS_LMTP_THREADS, numThreads());
        return data;
    }
}
