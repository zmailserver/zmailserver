/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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
package com.zimbra.qa.unittest;

import com.zimbra.common.net.ProxySelectors;
import com.zimbra.common.net.SocketFactories;
import junit.framework.TestCase;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

public class TestSocks extends TestCase {
    private static final String PROXY_HOST = "localhost";
    private static final int PROXY_PORT = 1080;
    private static final InetSocketAddress PROXY_ADDR =
        new InetSocketAddress(PROXY_HOST, PROXY_PORT);

    private static final String HTTP_URL = "http://www.news.com";
    private static final String HTTPS_URL = "https://www.news.com";
    
    private static final int LOCAL_PORT = 9999;

    static {
        SocketFactories.registerProtocols();
    }
    
    public void testConnect() throws IOException {
        connect(new SimpleProxySelector());
        connect(ProxySelectors.defaultProxySelector());
    }

    public void testSystemProxy() throws Exception {
        ProxySelector ps = ProxySelectors.nativeProxySelector();
        assertNotNull(ps);
        List<Proxy> proxies = ps.select(new URI("socket://www.vmware.com"));
        assertEquals(1, proxies.size());
        Proxy proxy = proxies.get(0);
        assertEquals(Proxy.Type.SOCKS, proxy.type());
        assertEquals(PROXY_ADDR, proxy.address());
    }

    public void testHttpProxy() throws Exception {
        ProxySelector ps = ProxySelectors.defaultProxySelector();
        List<Proxy> proxies = ps.select(new URI(HTTP_URL));
        for (Proxy proxy : proxies) {
            System.out.println("proxy = " + proxy);
        }
        URLConnection conn = new URL(HTTPS_URL).openConnection();
        conn.setConnectTimeout(3000);
        conn.connect();
        conn.getInputStream().read();
    }
    
    private Socket connect(ProxySelector ps) throws IOException {
        SocketFactory sf = SocketFactories.proxySelectorSocketFactory(ps);
        Socket sock = sf.createSocket();
        assertFalse(sock.isConnected());
        assertFalse(sock.isBound());
        sock.bind(new InetSocketAddress(0));
        sock.connect(new InetSocketAddress("www.news.com", 80));
        assertTrue(sock.isConnected());
        return sock;
    }

    private static class SimpleProxySelector extends ProxySelector {
        @Override
        public List<Proxy> select(URI uri) {
            return Arrays.asList(new Proxy(Proxy.Type.SOCKS, PROXY_ADDR));
        }

        @Override
        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
            // Ignore
        }
    }
}
