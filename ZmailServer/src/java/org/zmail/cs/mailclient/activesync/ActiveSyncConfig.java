/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.mailclient.activesync;

import org.zmail.common.util.ZmailLog;
import org.zmail.cs.mailclient.MailConfig;
import org.zmail.cs.mailclient.util.Config;

import java.util.Properties;
import java.io.File;
import java.io.IOException;

/**
 * Represents ACTIVESYNC mail client configuration.
 */
public class ActiveSyncConfig extends MailConfig {
    /** ACTIVESYNC configuration protocol name */
    public static final String PROTOCOL = "activesync";

    /** Default port for ACTIVESYNC plain text connection */
    public static final int DEFAULT_PORT = 80;

    /** Default port for ACTIVESYNC SSL connection */
    public static final int DEFAULT_SSL_PORT = 443;

    public static final Boolean DEFAULT_ACCEPT_ALL_CERTS = false;
    public static final String DEFAULT_POLICY_KEY = "0";
    public static final String DEFAULT_ACTIVE_SYNC_VERSION = "14.0";
    public static final String DEFAULT_DEVICE_TYPE = "PC";

    private String password;
    private Boolean acceptAllCerts;
    private String policyKey;
    private String activeSyncVersion;
    private String deviceType;
    private String deviceId;

    /**
     * Loads ACTIVESYNC configuration properties from the specified file.
     *
     * @param file the configuration properties file
     * @return the <tt>ActiveSyncConfig</tt> for the properties
     * @throws IOException if an I/O error occurred
     */
    public static ActiveSyncConfig load(File file) throws IOException {
        Properties props = Config.loadProperties(file);
        ActiveSyncConfig config = new ActiveSyncConfig();
        config.applyProperties(props);
        return config;
    }

    /**
     * Creates a new {@link ActiveSyncConfig}.
     */
    public ActiveSyncConfig() {
        super(ZmailLog.activesync_client);
    }

    /**
     * Creates a new {@link ActiveSyncConfig} for the specified server host.
     *
     * @param host the server host name
     */
    public ActiveSyncConfig(String host) {
        super(ZmailLog.activesync_client, host);
        setLogger(ZmailLog.activesync_client);
    }

    /**
     * Returns the ACTIVESYNC protocol name (value of {@link #PROTOCOL}).
     *
     * @return the ACTIVESYNC protocol name
     */
    @Override
    public String getProtocol() {
        return PROTOCOL;
    }

    /**
     * Returns the ACTIVESYNC server port number. If not set, the default is
     * {@link #DEFAULT_PORT} for a plain text connection and
     * {@link #DEFAULT_SSL_PORT} for an SSL connection.
     *
     * @return the ACTIVESYNC server port number
     */
    @Override
    public int getPort() {
        int port = super.getPort();
        if (port != -1) return port;
        return getSecurity() == Security.SSL ? DEFAULT_SSL_PORT : DEFAULT_PORT;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAcceptAllCerts() {
        return (acceptAllCerts == null) ? DEFAULT_ACCEPT_ALL_CERTS : acceptAllCerts;
    }

    public void setAcceptAllCerts(Boolean acceptAllCerts) {
        this.acceptAllCerts = acceptAllCerts;
    }

    public String getPolicyKey() {
        return (policyKey == null) ? DEFAULT_POLICY_KEY : policyKey;
    }

    public void setPolicyKey(String policyKey) {
        this.policyKey = policyKey;
    }

    public String getActiveSyncVersion() {
        return (activeSyncVersion == null) ? DEFAULT_ACTIVE_SYNC_VERSION : activeSyncVersion;
    }

    public void setActiveSyncVersion(String activeSyncVersion) {
        this.activeSyncVersion = activeSyncVersion;
    }

    public String getDeviceType() {
        return (deviceType == null) ? DEFAULT_DEVICE_TYPE : deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return (deviceId == null) ? ActiveSyncManager.newDeviceId() : deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
