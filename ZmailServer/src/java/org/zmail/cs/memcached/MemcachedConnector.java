/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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

package org.zmail.cs.memcached;

import net.spy.memcached.HashAlgorithm;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.memcached.ZmailMemcachedClient;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;

public class MemcachedConnector {

    private static ZmailMemcachedClient sTheClient = new ZmailMemcachedClient();

    /**
     * Returns the one and only memcached client object.
     * @return
     */
    public static ZmailMemcachedClient getClient() {
        return sTheClient;
    }

    /**
     * Startup the memcached connection.  Establish the memcached connection(s) if configured.
     * @throws ServiceException
     */
    public static void startup() throws ServiceException {
        reloadConfig();
    }

    /**
     * Are we currently connected to the memcached servers?
     * @return
     */
    public static boolean isConnected() {
        return sTheClient.isConnected();
    }

    /**
     * Reload the memcached client configuration.  Connect to the servers if configured with a
     * non-empty server list.  Any old connections are flushed and disconnected.
     * @throws ServiceException
     */
    public static void reloadConfig() throws ServiceException {
        Server server = Provisioning.getInstance().getLocalServer();
        String[] serverList = server.getMultiAttr(Provisioning.A_zmailMemcachedClientServerList);
        boolean useBinaryProtocol = server.getBooleanAttr(Provisioning.A_zmailMemcachedClientBinaryProtocolEnabled, false);
        String hashAlgorithm = server.getAttr(Provisioning.A_zmailMemcachedClientHashAlgorithm, HashAlgorithm.KETAMA_HASH.toString());
        int expirySeconds = (int) server.getLongAttr(Provisioning.A_zmailMemcachedClientExpirySeconds, 86400);
        long timeoutMillis = server.getLongAttr(Provisioning.A_zmailMemcachedClientTimeoutMillis, 10000);
        sTheClient.connect(serverList, useBinaryProtocol, hashAlgorithm, expirySeconds, timeoutMillis);
    }

    /**
     * Shutdown the memcached connection.
     * @throws ServiceException
     */
    public static void shutdown() throws ServiceException {
        sTheClient.disconnect(30000);
    }
}
