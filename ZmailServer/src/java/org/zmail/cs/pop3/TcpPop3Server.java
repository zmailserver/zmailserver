/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.common.stats.RealtimeStatsCallback;
import org.zmail.cs.stats.ZmailPerf;
import org.zmail.cs.server.ProtocolHandler;
import org.zmail.cs.server.ServerThrottle;
import org.zmail.cs.server.TcpServer;

import java.util.HashMap;
import java.util.Map;

public final class TcpPop3Server extends TcpServer implements Pop3Server, RealtimeStatsCallback {

    public TcpPop3Server(Pop3Config config) throws ServiceException {
        super(config);
        ZmailPerf.addStatsCallback(this);
        ServerThrottle.configureThrottle(config.getProtocol(), LC.pop3_throttle_ip_limit.intValue(), LC.pop3_throttle_acct_limit.intValue(), getThrottleSafeHosts());
    }

    @Override
    public String getName() {
        return getConfig().isSslEnabled() ? "Pop3SSLServer" : "Pop3Server";
    }

    @Override
    protected ProtocolHandler newProtocolHandler() {
        return new TcpPop3Handler(this);
    }

    @Override
    public Pop3Config getConfig() {
        return (Pop3Config) super.getConfig();
    }

    /**
     * Implementation of {@link RealtimeStatsCallback} that returns the number
     * of active handlers and number of threads for this server.
     */
    @Override
    public Map<String, Object> getStatData() {
        Map<String, Object> data = new HashMap<String, Object>();
        if (getConfig().isSslEnabled()) {
            data.put(ZmailPerf.RTS_POP_SSL_CONN, numActiveHandlers());
            data.put(ZmailPerf.RTS_POP_SSL_THREADS, numThreads());
        } else {
            data.put(ZmailPerf.RTS_POP_CONN, numActiveHandlers());
            data.put(ZmailPerf.RTS_POP_THREADS, numThreads());
        }
        return data;
    }
}
