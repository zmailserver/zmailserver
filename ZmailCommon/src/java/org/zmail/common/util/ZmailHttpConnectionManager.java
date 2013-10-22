/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.common.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;

import com.zimbra.common.localconfig.LC;
import com.zimbra.common.util.Log;
import com.zimbra.common.util.LogFactory;

/**
 * A wrapper class for HttpConnectionManagerParams.
 * 
 * Purposes:
 * 
 * 1. There should be a single common MultiThreadedHttpconnectionManager shared by
 *    nearly everyone so that sockets can be reused and a configurable limit put on
 *    client connections (per remote host and overall).
 *    
 * 2. HttpClient debug logging can be turned on/off on account level.
 *
 */
public class ZimbraHttpConnectionManager {
    
    // connection manager for Zimbra internal connections
    private static final ZimbraHttpConnectionManager INTERNAL_CONN_MGR = 
        new ZimbraHttpConnectionManager("Internal http client connection manager", new InternalConnMgrParams());
    
    // connection manager for all external connections
    private static final ZimbraHttpConnectionManager EXTERNAL_CONN_MGR =
        new ZimbraHttpConnectionManager("External http client connection manager", new ExternalConnMgrParams());
    
    // logger
    private static final Log sLog = LogFactory.getLog(ZimbraHttpConnectionManager.class);
    
    private static abstract class ZimbraConnMgrParams {
        
        // HttpConnectionManagerParams
        HttpConnectionManagerParams connMgrParams = new HttpConnectionManagerParams();
        
        
        private HttpConnectionManagerParams getConnMgrParams() {
            return connMgrParams;
        }
        
        abstract long getHttpClientConnectionTimeout();
        abstract long getReaperSleepInterval();
        abstract long getReaperConnectionTimeout();
    }
    
    private static class InternalConnMgrParams extends ZimbraConnMgrParams {
        
        private InternalConnMgrParams() {

            /* ------------------------------------------------------------------------
             * HttpConnectionManagerParams(subclass of HttpConnectionParams)
             * ------------------------------------------------------------------------
             */ 
            
            /*
             * Defines the maximum number of connections allowed per host configuration.
             * 
             * HttpConnectionManagerParams.MAX_HOST_CONNECTIONS 
             */
            connMgrParams.setDefaultMaxConnectionsPerHost(LC.httpclient_internal_connmgr_max_host_connections.intValue());
            
            /*
             * Defines the maximum number of connections allowed overall.
             *
             * HttpConnectionManagerParams.MAX_TOTAL_CONNECTIONS 
             */
            connMgrParams.setMaxTotalConnections(LC.httpclient_internal_connmgr_max_total_connections.intValue());
            
            
            /* -------------------------------
             * HttpConnectionParams params 
             * -------------------------------
             */
            
            /*
             * Determines the timeout until a connection is established. A value of zero means the timeout is not used.
             *
             * HttpConnectionParams.CONNECTION_TIMEOUT
             */
            connMgrParams.setConnectionTimeout(LC.httpclient_internal_connmgr_connection_timeout.intValue());

            
            //
            // Determines the specified linger time in seconds
            //
            // HttpConnectionParams.SO_LINGER
            //
            // use default and do not expose in LC
            //
            
            //
            // Determines a hint the size of the underlying buffers used by the platform for outgoing network I/O.
            //
            // HttpConnectionParams.SO_RCVBUF
            //
            // use default and do not expose in LC
            //

            //
            // Determines a hint the size of the underlying buffers used by the platform for outgoing network I/O.
            //
            // HttpConnectionParams.SO_SNDBUF
            //
            // use default and do not expose in LC
            //

            /*
             * Defines the default socket timeout (SO_TIMEOUT) in milliseconds which is the timeout for waiting for data.
             * A timeout value of zero is interpreted as an infinite timeout. This value is used when no socket timeout 
             * is set in the HTTP method parameters. 
             * 
             * HttpConnectionParams.SO_TIMEOUT
             */
            connMgrParams.setSoTimeout(LC.httpclient_internal_connmgr_so_timeout.intValue());
             
            //
            // Determines whether stale connection check is to be used. 
            // Disabling stale connection check may result in slight performance improvement 
            // at the risk of getting an I/O error when executing a request over a connection 
            // that has been closed at the server side.
            //
            // HttpConnectionParams.STALE_CONNECTION_CHECK
            //
            connMgrParams.setStaleCheckingEnabled(LC.httpclient_internal_connmgr_stale_connection_check.booleanValue());
            
            //
            // Determines whether Nagle's algorithm is to be used.
            //
            // HttpConnectionParams.TCP_NODELAY
            //
            connMgrParams.setTcpNoDelay(LC.httpclient_internal_connmgr_tcp_nodelay.booleanValue());
        }

        @Override
        public long getHttpClientConnectionTimeout() {
            return LC.httpclient_internal_client_connection_timeout.longValue();
        }
        
        @Override
        long getReaperSleepInterval() {
            //
            // Sets the interval between closing idle connections. 
            // Idle connections will be closed every timeoutInterval milliseconds.
            //
            return LC.httpclient_internal_connmgr_idle_reaper_sleep_interval.longValue();
        }
        
        @Override
        long getReaperConnectionTimeout() {
            //
            // Sets the timeout value to use when testing for idle connections.
            //
            return LC.httpclient_internal_connmgr_idle_reaper_connection_timeout.longValue();
        }
    }    
        
    private static class ExternalConnMgrParams extends ZimbraConnMgrParams {
        
        private ExternalConnMgrParams() {
            /* ------------------------------------------------------------------------
             * HttpConnectionManagerParams(subclass of HttpConnectionParams)
             * ------------------------------------------------------------------------
             */ 
            connMgrParams.setDefaultMaxConnectionsPerHost(LC.httpclient_external_connmgr_max_host_connections.intValue());
            connMgrParams.setMaxTotalConnections(LC.httpclient_external_connmgr_max_total_connections.intValue());
            connMgrParams.setConnectionTimeout(LC.httpclient_external_connmgr_connection_timeout.intValue());
            connMgrParams.setSoTimeout(LC.httpclient_external_connmgr_so_timeout.intValue());
            connMgrParams.setStaleCheckingEnabled(LC.httpclient_external_connmgr_stale_connection_check.booleanValue());
            connMgrParams.setTcpNoDelay(LC.httpclient_external_connmgr_tcp_nodelay.booleanValue());
        }

        @Override
        public long getHttpClientConnectionTimeout() {
            return LC.httpclient_external_client_connection_timeout.longValue();
        }
        
        @Override
        long getReaperSleepInterval() {
            return LC.httpclient_external_connmgr_idle_reaper_sleep_interval.longValue();
        }
        
        @Override
        long getReaperConnectionTimeout() {
            return LC.httpclient_external_connmgr_idle_reaper_connection_timeout.longValue();
        }
    }

    public static synchronized void startReaperThread() {
        INTERNAL_CONN_MGR.idleReaper.startReaperThread();
        EXTERNAL_CONN_MGR.idleReaper.startReaperThread();
    }
    
    public static synchronized void shutdownReaperThread() {
        INTERNAL_CONN_MGR.idleReaper.shutdownReaperThread();
        EXTERNAL_CONN_MGR.idleReaper.shutdownReaperThread();
    }
    
    public static ZimbraHttpConnectionManager getInternalHttpConnMgr() {
        return INTERNAL_CONN_MGR;
    }
    
    public static ZimbraHttpConnectionManager getExternalHttpConnMgr() {
        return EXTERNAL_CONN_MGR;
    }
    
    // ================
    // instance fields and methods
    // ================
    private String name;
    private ZimbraConnMgrParams zimbraConnMgrParams;
    private IdleReaper idleReaper;
    private HttpConnectionManager httpConnMgr;
    private HttpClient defaultHttpClient;
    
    private ZimbraHttpConnectionManager(String name, ZimbraConnMgrParams zimbraConnMgrParams) {
        this.name = name;
        this.zimbraConnMgrParams = zimbraConnMgrParams;
        
        this.httpConnMgr = new MultiThreadedHttpConnectionManager();
        this.httpConnMgr.setParams(this.zimbraConnMgrParams.getConnMgrParams());
        this.defaultHttpClient = createHttpClient();
        
        // Instantiate the reaper object.  
        // Note: Reaper thread is not started until ZimbraHttpConnectionManager.startReaperThread 
        // is called.
        this.idleReaper = new IdleReaper(this);
    }
    
    private String getName() {
        return name;
    }
    
    private ZimbraConnMgrParams getParams() {
        return zimbraConnMgrParams;
    }
    
    private HttpClientParams createHttpClientParams() {
        HttpClientParams clientParams = new HttpClientParams();
        
        //
        // Sets the timeout in milliseconds used when retrieving an HTTP connection from the HTTP connection manager. 
        //
        // HttpClientParams.CONNECTION_MANAGER_TIMEOUT
        //
        clientParams.setConnectionManagerTimeout(getParams().getHttpClientConnectionTimeout());
        
        return clientParams;
    }
    
    private HttpClient createHttpClient() {
        return new HttpClient(createHttpClientParams(), httpConnMgr);
    }
    
    private HttpConnectionManager getConnMgr() {
        return httpConnMgr;
    }
    
    /**
     * ==========================================================
     * Important notes on using HttpClient returned by 
     * ZimbraHttpConnectionManager.getDefaultHttpClient() and 
     * ZimbraHttpConnectionManager.newHttpClient()
     * ========================================================== 
     * 
     * 1. Callsites should never call HttpClient.setConnectionTimeout(...)
     *    on the HttpClient object.
     *        setConnectionTimeout actually sets the connection timeout parameter 
     *        on the HttpConnectionManager instance of the HttpClient object.  
     *        It will affect all HttpClient instances created or going to be created 
     *        that are associated with the connection manager instance.  
     *             
     *        Connection timeout should only be altered by the LC key 
     *        httpclient_connmgr_connection_timeout.  We use a reasonable default 
     *        and it should not have to be changed.  If a connection cannot be 
     *        established with our default connection timeout, it's an indication 
     *        of problems on the http server and the problem should be fixed on the 
     *        http server side, instead of tweaking the connection timeout on the 
     *        http client side.
     *        
     *        Callsites might need to change the "read timeout", which is the 
     *        timeout (after the connection is established) while reading data on 
     *        the connection/socket, socket read timeout should be set on the HttpMethod,
     *        as follows:
     *        HttpMethod method = new Post/GetMethod(...);
     *        method.getParams().setSoTimeout(milliseconds);
     *
     * 2. Callsites should not call HttpClient.setHttpConnectionManager(...) 
     *        No point associating the HttpClient with another connection 
     *        manager if it uses ZimbraHttpConnectionManager to obtain the 
     *        HttpClient.  
     *       
     * 3. About calling HttpClient.getHttpConnectionManager()     
     *        It is OK to call getHttpConnectionManager, which return the connection
     *        manager instance wrapped in the ZimbraHttpConnectionManager.
     *        However, no one should be altering any states on the connection 
     *        manager instance, as that is shared by all threads/callsites on the 
     *        system.
     * 
     * 4. Callsite must call HttpMethod.releaseConnection() in the finally 
     *    block after httpClient.executeMethod(...) is done.
     *    This will release the connection used by the HttpMethod back to 
     *    the available connection pool managed by the connection manager.
     */
    
    /**
     * Returns the default HttpClient instance associated with this connection manager.
     * 
     * *** See "Important notes on using HttpClient returned by ..." above. ***
     * 
     * http://hc.apache.org/httpclient-3.x/performance.html says:
     *     HttpClient is fully thread-safe when used with a thread-safe connection manager such as 
     *     MultiThreadedHttpConnectionManager.  Please note that each respective thread of execution 
     *     must have a local instance of HttpMethod and can have a local instance of HttpState or/and 
     *     HostConfiguration to represent a specific host configuration and conversational state. 
     *     At the same time the HttpClient instance and connection manager should be shared among 
     *     all threads for maximum efficiency. 
     *
     * For callsites obtaining the HttpClient instance from this API, if HostConfiguration and/or 
     * HttpState need to be changed on an invocation of HttpClient.executeMethod(), they should
     * use the executeMethod API:
     *     executeMethod(HostConfiguration hostconfig,
     *                   HttpMethod method,
     *                   HttpState state)
     * where all components for the executeMethod invocation is passed in as parameters.
     * 
     * Instead of calling HttpClient.setHostConfiguration(...), HttpClient.setState(...).
     * 
     * Also, callsites should *not* alter any state on the returned HttpClient instance by calling 
     * any of the HttpClient.set***() methods because this singleton instance is shared by 
     * all threads/callsites on the system.
     * 
     * @return the default HttpClient instance associated with this connection manager
     */
    public HttpClient getDefaultHttpClient() {
        return defaultHttpClient;
    }

    /**
     * Create a new HttpClient instance associated with this connection manager.
     * 
     * *** See "Important notes on using HttpClient returned by ..." above. ***
     * 
     * Callsites of this API are free to alter states of the returned HttpClient 
     * instance, because an new instance is created each time this API is called.
     * 
     * e.g. many of our callsites use the pattern:
     *     HttpState state = new HttpState();
     *     Cookie cookie = new Cookie(...);
     *     state.addCookie(cookie);
     *     client.setState(state);
     *     client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
     *     executeMethod(method);
     *     
     * The above is fine with the HttpClient object returned by this API.
     * 
     * @return a new HttpClient instance associated with this connection manager.
     */
    public HttpClient newHttpClient() {
        return createHttpClient();
    }
    
    
    /*
     * =====================
     *  Idle Reaper
     * =====================
     *
     * HttpMethod.releaseConnection() doesn't actually close the socket unless 
     * "Connection: close" was sent back from server or if commons client wasn't 
     * explicitly asked to act like HTTP/1.0.   HttpMethod.releaseConnection() 
     * just release the connection back to the connection manager that created  
     * it, so it can be reused by others.  Sockets opened by httpclient will 
     * have to be closed by finalizers, which is bad, as we don't have control 
     * over it.   This will cause lots of CLOSE_WAIT on the server from which 
     * http requests are sent via httpclient.
     * 
     * To get around that, we run a reaper thread to close idle connections 
     * owned by our connection manager.
     */
    private static class IdleReaper {
        // the ZimbraHttpConnectionManager instance this IdleReaper is for.
        private ZimbraHttpConnectionManager connMgr;
        
        private IdleConnectionTimeoutThread reaperThread;
        
        private IdleReaper(ZimbraHttpConnectionManager connMgr) {
            this.connMgr = connMgr;
        }
        
        private void startReaperThread() {
            // sanity check
            if (isReaperThreadRunning()) {
                sLog.warn("Cannot start a second http client idle connection reaper thread while another one is running.");
                return;
            }
            
            if (!reaperEnabled()) {
                sLog.info("Not starting http client idle connection reaper thread for " + connMgr.getName() + " because it is disabled");
                return;
            }
            
            sLog.info("Starting http client idle connection reaper thread for " + connMgr.getName() + 
                    " - reaper sleep interval=%d, reaper connection timeout=%d", 
                    getReaperSleepInterval(), getReaperConnectionTimeout());
            
            if (sLog.isDebugEnabled()) {
                sLog.debug(dumpParams(connMgr.getName(), connMgr.getParams().getConnMgrParams(),
                        connMgr.getDefaultHttpClient().getParams()).toString());
            }
    
            // create and start thread
            reaperThread = new IdleConnectionTimeoutThread();
            reaperThread.setName("IdleConnectionTimeoutThread" + " for " + connMgr.getName());
            
            reaperThread.addConnectionManager(connMgr.getConnMgr());
                        
            reaperThread.setConnectionTimeout(getReaperConnectionTimeout());
            reaperThread.setTimeoutInterval(getReaperSleepInterval());
            reaperThread.start();
        }
        
        private void shutdownReaperThread() {
            if (!isReaperThreadRunning()) {
                sLog.warn("shutting down http client idle connection reaper thread requested but the reaper thread is not running");
                return;
            }
            
            sLog.info("shutting down http client idle connection reaper thread");
            
            reaperThread.shutdown();
            reaperThread = null;
        }
        
        private boolean isReaperThreadRunning() {
            return (reaperThread != null);
        }
        
        private boolean reaperEnabled() {
            return getReaperSleepInterval() != 0;
        }
        
        private long getReaperSleepInterval() {
            return connMgr.getParams().getReaperSleepInterval();
        }
        
        private long getReaperConnectionTimeout() {
            return LC.httpclient_internal_connmgr_idle_reaper_connection_timeout.longValue();
        }
    }
    
    private static String dumpParams(String notes, HttpConnectionManagerParams connMgrParams, HttpClientParams clientParams) {
        // dump httpclient package defaults if params is null
        if (connMgrParams == null)
            connMgrParams = new HttpConnectionManagerParams();
        if (clientParams == null)
            clientParams = new HttpClientParams();
     
        StringBuilder sb = new StringBuilder();
        
        sb.append("======== " + notes + "========\n");
        
        sb.append("HttpConnectionManagerParams DefaultMaxConnectionsPerHost  : " + connMgrParams.getDefaultMaxConnectionsPerHost() + "\n");
        sb.append("HttpConnectionManagerParams MaxTotalConnections           : " + connMgrParams.getMaxTotalConnections() + "\n");
        
        sb.append("HttpConnectionParams ConnectionTimeout                    : " + connMgrParams.getConnectionTimeout() + "\n");
        sb.append("HttpConnectionParams Linger                               : " + connMgrParams.getLinger() + "\n");
        sb.append("HttpConnectionParams ReceiveBufferSize                    : " + connMgrParams.getReceiveBufferSize() + "\n");
        sb.append("HttpConnectionParams SendBufferSize                       : " + connMgrParams.getSendBufferSize() + "\n");
        sb.append("HttpConnectionParams SoTimeout                            : " + connMgrParams.getSoTimeout() + "\n");
        sb.append("HttpConnectionParams TcpNoDelay                           : " + connMgrParams.getTcpNoDelay() + "\n");
        sb.append("HttpConnectionParams isStaleCheckingEnabled               : " + connMgrParams.isStaleCheckingEnabled() + "\n");
       
        // sb.append("HttpClientParams ALLOW_CIRCULAR_REDIRECTS            (no corresponding method?)
        sb.append("HttpClientParams ConnectionManagerClass               : " + clientParams.getConnectionManagerClass().getName() + "\n");
        sb.append("HttpClientParams ConnectionManagerTimeout             : " + clientParams.getConnectionManagerTimeout()  + "\n");
        // sb.append("HttpClientParams MAX_REDIRECTS                       (no corresponding method?)
        sb.append("HttpClientParams isAuthenticationPreemptive()         : " + clientParams.isAuthenticationPreemptive() + "\n");
        // sb.append("HttpClientParams REJECT_RELATIVE_REDIRECT            (no corresponding method?)
      
        return sb.toString();
    }
    
    public static void main(String[] args) {
        // dump httpclient package defaults
        System.out.println(dumpParams("httpclient package defaults", new HttpConnectionManagerParams(), new HttpClientParams()));
        
        System.out.println(dumpParams("Internal ZimbraHttpConnectionManager", ZimbraHttpConnectionManager.getInternalHttpConnMgr().getParams().getConnMgrParams(),
                ZimbraHttpConnectionManager.getInternalHttpConnMgr().getDefaultHttpClient().getParams()));
        
        System.out.println(dumpParams("External ZimbraHttpConnectionManager", ZimbraHttpConnectionManager.getExternalHttpConnMgr().getParams().getConnMgrParams(),
                ZimbraHttpConnectionManager.getExternalHttpConnMgr().getDefaultHttpClient().getParams()));
        
        
        HttpClient httpClient = ZimbraHttpConnectionManager.getInternalHttpConnMgr().getDefaultHttpClient();
        String connMgrName = httpClient.getHttpConnectionManager().getClass().getSimpleName();
        long connMgrTimeout = httpClient.getParams().getConnectionManagerTimeout(); 
        System.out.println("HttpConnectionManager for the HttpClient instance is: " + connMgrName);
        System.out.println("connection manager timeout for the HttpClient instance is: " + connMgrTimeout);
    }

}



