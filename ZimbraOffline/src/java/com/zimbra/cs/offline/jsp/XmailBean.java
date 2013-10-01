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
package com.zimbra.cs.offline.jsp;

import java.util.HashMap;
import java.util.Map;

import com.zimbra.common.account.ProvisioningConstants;
import com.zimbra.soap.admin.type.DataSourceType;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.SoapFaultException;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.DataSource;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.offline.OfflineProvisioning;
import com.zimbra.cs.offline.common.OfflineConstants;
import com.zimbra.client.ZFolder;
import com.zimbra.soap.type.DataSource.ConnectionType;

public class XmailBean extends MailBean {
    public XmailBean() {
    }

    protected String domain;
    protected String fromDisplay = "";
    protected String replyTo = "";
    protected String replyToDisplay = "";

    protected boolean smtpEnabled = true;
    protected String smtpHost = "";
    protected String smtpPort = "25";
    protected boolean isSmtpSsl = false;
    protected boolean isSmtpAuth = false;
    protected String smtpUsername = "";
    protected String smtpPassword = "";

    protected boolean calendarSyncEnabled = false;
    protected boolean contactSyncEnabled = false;
    protected boolean leaveOnServer = false;
    protected boolean syncAllServerFolders = true;

    protected boolean calLoginError = false;

    protected boolean ycontactVerifyError = false;
    protected String ycontactToken = "";
    protected String ycontactTokenSecret = "";
    protected long ycontactTokenTimestamp = 0L;
    protected String ycontactVerfier = "";
    protected String ycontactSessionHandle = "";
    protected String ycontactGuid = "";
    protected boolean isYcontactTokenSaved = false; //TODO: technically should be in YmailBean. Need to make reload call stub method we can implement in Ymail

    private static final String adomain = "aol.com";
    private static final String gdomain = "gmail.com";
    private static final String ydomain = "yahoo.com";
    private static final String yjpdomain = "yahoo.co.jp";
    private static final String ymdomain = "ymail.com";
    private static final String yrmdomain = "rocketmail.com";

    @Override
    protected void reload() {
        Account account;
        DataSource ds;

        try {
            account = JspProvStub.getInstance().getOfflineAccount(accountId);
            ds = JspProvStub.getInstance().getOfflineDataSource(accountId);
        } catch (ServiceException x) {
            setError(x.getMessage());
            return;
        }
        accountFlavor = account.getAttr(OfflineConstants.A_offlineAccountFlavor);
        if (accountFlavor == null)
            accountFlavor = ds.getAttr(OfflineConstants.A_offlineAccountFlavor);
        accountName = account.getAttr(Provisioning.A_zimbraPrefLabel);
        if (accountName == null)
            accountName = ds.getName();
        username = ds.getUsername();
        password = JspConstants.MASKED_PASSWORD;
        email = ds.getEmailAddress();
        fromDisplay = ds.getFromDisplay();
        replyTo = ds.getReplyToAddress();
        replyToDisplay = ds.getReplyToDisplay();
        type = ds.getType().toString();
        host = ds.getHost();
        port = ds.getPort().toString();
        connectionType = ds.getConnectionType();
        isDebugTraceEnabled = ds.isDebugTraceEnabled();
        calendarSyncEnabled = ds.getBooleanAttr(OfflineConstants.A_zimbraDataSourceCalendarSyncEnabled, false);
        contactSyncEnabled = ds.getBooleanAttr(OfflineConstants.A_zimbraDataSourceContactSyncEnabled, false);
        domain = ds.getAttr(Provisioning.A_zimbraDataSourceDomain, null);
        smtpEnabled = ds.getBooleanAttr(OfflineConstants.A_zimbraDataSourceSmtpEnabled, true);
        smtpHost = ds.getAttr(OfflineConstants.A_zimbraDataSourceSmtpHost, null);
        smtpPort = ds.getAttr(OfflineConstants.A_zimbraDataSourceSmtpPort, null);
        isSmtpSsl = "ssl".equals(ds.getAttr(OfflineConstants.A_zimbraDataSourceSmtpConnectionType));
        isSmtpAuth = ds.getBooleanAttr(OfflineConstants.A_zimbraDataSourceSmtpAuthRequired, false);
        if (isSmtpAuth) {
            smtpUsername = ds.getAttr(OfflineConstants.A_zimbraDataSourceSmtpAuthUsername, null);
            smtpPassword = isEmpty(ds.getAttr(OfflineConstants.A_zimbraDataSourceSmtpAuthPassword, null)) ? null
                    : JspConstants.MASKED_PASSWORD;
        } else {
            smtpUsername = "";
            smtpPassword = "";
        }
        syncFreqSecs = ds.getTimeIntervalSecs(OfflineConstants.A_zimbraDataSourceSyncFreq,
                OfflineConstants.DEFAULT_SYNC_FREQ / 1000);
        syncAllServerFolders = ds.getBooleanAttr(OfflineConstants.A_zimbraDataSourceSyncAllServerFolders, false);
        leaveOnServer = ds.getBooleanAttr(Provisioning.A_zimbraDataSourceLeaveOnServer, false);
        isYcontactTokenSaved = ds.getBooleanAttr(OfflineConstants.A_offlineYContactTokenReady, false); //TODO: technically should be in YmailBean. Need to pass ds to some stub method
    }

    @Override
    protected void doRequest() {
        if (verb == null || !isAllOK())
            return;
        try {
            Map<String, Object> dsAttrs = new HashMap<String, Object>();
            DataSourceType dsType = isEmpty(type) ? null : DataSourceType.fromString(type);

            calLoginError = false;
            ycontactVerifyError = false;
            if (verb.isAdd() || verb.isModify()) {
                if (dsType == null)
                    addInvalid("type");
                if (isEmpty(accountFlavor))
                    addInvalid("flavor");
                if (isEmpty(accountName))
                    addInvalid("accountName");
                if (isEmpty(username))
                    addInvalid("username");
                if (isEmpty(password))
                    addInvalid("password");
                if (!isValidHost(host))
                    addInvalid("host");
                if (!isValidPort(port))
                    addInvalid("port");
                if (!isValidEmail(email))
                    addInvalid("email");

                if (domain == null)
                    domain = email.substring(email.indexOf('@') + 1);
                if (isSmtpConfigSupported()) {
                    if (isEmpty(smtpHost))
                        smtpEnabled = false;
                    else if (!isValidHost(smtpHost))
                        addInvalid("smtpHost");
                    if (!isValidPort(smtpPort))
                        addInvalid("smtpPort");
                    if (isSmtpAuth) {
                        if (isEmpty(smtpUsername))
                            smtpUsername = username;
                        if (isEmpty(smtpPassword))
                            smtpPassword = password;
                    }
                }
                if (isAllOK()) {
                    dsAttrs.put(OfflineConstants.A_offlineAccountFlavor, accountFlavor);
                    dsAttrs.put(OfflineConstants.A_zimbraDataSourceAccountSetup, ProvisioningConstants.TRUE);
                    dsAttrs.put(Provisioning.A_zimbraDataSourceEnabled, ProvisioningConstants.TRUE);
                    dsAttrs.put(Provisioning.A_zimbraDataSourceUsername, username);
                    if (!password.equals(JspConstants.MASKED_PASSWORD))
                        dsAttrs.put(Provisioning.A_zimbraDataSourcePassword, password);
                    dsAttrs.put(Provisioning.A_zimbraDataSourceEmailAddress, email);
                    dsAttrs.put(Provisioning.A_zimbraPrefFromDisplay, fromDisplay);
                    dsAttrs.put(Provisioning.A_zimbraPrefReplyToAddress, replyTo);
                    dsAttrs.put(Provisioning.A_zimbraPrefReplyToDisplay, replyToDisplay);
                    dsAttrs.put(Provisioning.A_zimbraDataSourceHost, host);
                    dsAttrs.put(Provisioning.A_zimbraDataSourcePort, port);
                    dsAttrs.put(Provisioning.A_zimbraDataSourceConnectionType, connectionType.toString());
                    dsAttrs.put(Provisioning.A_zimbraDataSourceEnableTrace, isDebugTraceEnabled ? ProvisioningConstants.TRUE
                            : ProvisioningConstants.FALSE);
                    if (isCalendarSyncSupported()) {
                        dsAttrs.put(OfflineConstants.A_zimbraDataSourceCalendarSyncEnabled,
                                calendarSyncEnabled ? ProvisioningConstants.TRUE : ProvisioningConstants.FALSE);
                    }
                    if (isContactSyncSupported()) {
                        dsAttrs.put(OfflineConstants.A_zimbraDataSourceContactSyncEnabled,
                                contactSyncEnabled ? ProvisioningConstants.TRUE : ProvisioningConstants.FALSE);
                    }
                    dsAttrs.put(Provisioning.A_zimbraDataSourceDomain, domain);
                    dsAttrs.put(OfflineConstants.A_zimbraDataSourceSmtpEnabled, smtpEnabled ? ProvisioningConstants.TRUE
                            : ProvisioningConstants.FALSE);
                    dsAttrs.put(OfflineConstants.A_zimbraDataSourceSmtpHost, smtpHost);
                    dsAttrs.put(OfflineConstants.A_zimbraDataSourceSmtpPort, smtpPort);
                    dsAttrs.put(OfflineConstants.A_zimbraDataSourceSmtpConnectionType, (isSmtpSsl ? ConnectionType.ssl
                            : ConnectionType.cleartext).toString());
                    dsAttrs.put(OfflineConstants.A_zimbraDataSourceSmtpAuthRequired, isSmtpAuth ? ProvisioningConstants.TRUE
                            : ProvisioningConstants.FALSE);
                    if (isSmtpAuth) {
                        dsAttrs.put(OfflineConstants.A_zimbraDataSourceSmtpAuthUsername, smtpUsername);
                        if (!smtpPassword.equals(JspConstants.MASKED_PASSWORD))
                            dsAttrs.put(OfflineConstants.A_zimbraDataSourceSmtpAuthPassword, smtpPassword);
                    }
                    dsAttrs.put(OfflineConstants.A_zimbraDataSourceSyncFreq, Long.toString(syncFreqSecs));
                    if (isFolderSyncSupported())
                        dsAttrs.put(OfflineConstants.A_zimbraDataSourceSyncAllServerFolders, ProvisioningConstants.TRUE);
                    if (dsType == DataSourceType.pop3) {
                        dsAttrs.put(Provisioning.A_zimbraDataSourceLeaveOnServer, Boolean.toString(leaveOnServer)
                                .toUpperCase());
                        dsAttrs.put(Provisioning.A_zimbraDataSourceFolderId, ZFolder.ID_INBOX);
                    } else {
                        assert dsType == DataSourceType.imap;
                        dsAttrs.put(Provisioning.A_zimbraDataSourceFolderId, ZFolder.ID_USER_ROOT);
                    }
                    if (sslCertAlias != null && sslCertAlias.length() > 0)
                        dsAttrs.put(OfflineConstants.A_zimbraDataSourceSslCertAlias, sslCertAlias);
                }
            }
            if (verb.isAdd()) {
                if (email.endsWith('@' + ydomain) || email.endsWith('@' + yjpdomain) || email.endsWith('@' + ymdomain)
                        || email.endsWith('@' + yrmdomain)) {
                    if (dsType == DataSourceType.imap) {
                        dsAttrs.put(Provisioning.A_zimbraDataSourceDomain, ydomain);
                    } else {
                        addInvalid("type");
                        setError(getMessage("YMPMustUseImap"));
                    }
                } else if (email.endsWith('@' + gdomain)) {
                    if (dsType == DataSourceType.imap) {
                        dsAttrs.put(Provisioning.A_zimbraDataSourceDomain, gdomain);
                    } else {
                        addInvalid("type");
                        setError(getMessage("GmailMustUseImap"));
                    }
                } else if (email.endsWith('@' + adomain)) {
                    if (dsType == DataSourceType.imap) {
                        dsAttrs.put(Provisioning.A_zimbraDataSourceDomain, adomain);
                    } else {
                        addInvalid("type");
                        setError(getMessage("AOLMustUseImap"));
                    }
                }
                if (isCalendarSyncSupported()) {
                    dsAttrs.put(OfflineConstants.A_zimbraDataSourceCalendarFolderId, ZFolder.ID_CALENDAR);
                }
            }
            // for yahoo contact oauth
            if (verb.isAdd() || verb.isModify()) {
                if (contactSyncEnabled) {
                    dsAttrs.put(OfflineProvisioning.A_offlineYContactToken, this.ycontactToken);
                    dsAttrs.put(OfflineProvisioning.A_offlineYContactTokenSecret, this.ycontactTokenSecret);
                    dsAttrs.put(OfflineProvisioning.A_offlineYContactTokenSessionHandle, this.ycontactSessionHandle);
                    dsAttrs.put(OfflineProvisioning.A_offlineYContactGuid, this.ycontactGuid);
                    dsAttrs.put(OfflineProvisioning.A_offlineYContactTokenTimestamp,
                            String.valueOf(this.ycontactTokenTimestamp));
                    dsAttrs.put(OfflineProvisioning.A_offlineYContactVerifier, this.ycontactVerfier);
                }
            }
            if (isAllOK()) {
                JspProvStub stub = JspProvStub.getInstance();
                if (verb.isAdd()) {
                    stub.createOfflineDataSource(accountName, email, dsType, dsAttrs);
                } else if (isEmpty(accountId)) {
                    setError(getMessage("AccountIdMissing"));
                } else if (verb.isDelete()) {
                    stub.deleteOfflineDataSource(accountId);
                } else if (verb.isModify()) {
                    stub.modifyOfflineDataSource(accountId, accountName, dsAttrs);
                } else if (verb.isReset()) {
                    stub.resetOfflineDataSource(accountId);
                } else if (verb.isReindex()) {
                    stub.reIndex(accountId);
                } else {
                    setError(getMessage("UnknownAct"));
                }
            }
        } catch (SoapFaultException x) {
            if (x.getCode().equals("account.AUTH_FAILED")) {
                setError(getMessage("InvalidUserOrPass"));
            } else if (x.getCode().equals("account.ACCOUNT_INACTIVE")) {
                setError(getMessage("YMPPlusRequired"));
            } else if (x.getCode().equals("offline.CALDAV_LOGIN_FAILED")) {
                setCalDavLoginError("CalAccessErr");
            } else if (x.getCode().equals("offline.YCALDAV_NEED_UPGRADE")) {
                setCalDavLoginError("YMPSyncCalUpgradeNote");
            } else if (x.getCode().equals("offline.GCALDAV_NEED_ENABLE")) {
                setCalDavLoginError("GmailCalDisabled");
            } else if (!(verb != null && verb.isDelete() && x.getCode().equals("account.NO_SUCH_ACCOUNT"))) {
                setExceptionError(x);
            }
        } catch (Exception t) {
            setError(t.getLocalizedMessage() == null ? t.toString() : t.getLocalizedMessage());
        }
    }

    protected void setYContactVerifyError(String key) {
        setError(getMessage(key));
        ycontactVerifyError = true;
    }

    protected void setCalDavLoginError(String key) {
        setError(getMessage(key));
        calLoginError = true;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = require(username);
    }

    public String getFromDisplay() {
        return fromDisplay;
    }

    public void setFromDisplay(String fromDisplay) {
        this.fromDisplay = optional(fromDisplay);
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = optional(replyTo);
    }

    public String getReplyToDisplay() {
        return replyToDisplay;
    }

    public void setReplyToDisplay(String replyToDisplay) {
        this.replyToDisplay = optional(replyToDisplay);
    }

    public boolean isSmtpEnabled() {
        return smtpEnabled;
    }

    public void setSmtpEnabled(boolean isSmtpEnabled) {
        this.smtpEnabled = isSmtpEnabled;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = require(smtpHost);
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = require(smtpPort);
    }

    public boolean isSmtpSsl() {
        return isSmtpSsl;
    }

    public void setSmtpSsl(boolean isSmtpSsl) {
        this.isSmtpSsl = isSmtpSsl;
    }

    public boolean isSmtpAuth() {
        return isSmtpAuth;
    }

    public void setSmtpAuth(boolean isSmtpAuth) {
        this.isSmtpAuth = isSmtpAuth;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = optional(smtpUsername);
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = optional(smtpPassword);
    }

    public boolean isCalendarSyncEnabled() {
        return calendarSyncEnabled;
    }

    public void setCalendarSyncEnabled(boolean enabled) {
        calendarSyncEnabled = enabled;
    }

    public boolean isCalLoginError() {
        return calLoginError;
    }

    public void setCalLoginError(boolean err) {
        calLoginError = err;
    }

    public boolean isContactSyncEnabled() {
        return contactSyncEnabled;
    }

    public void setContactSyncEnabled(boolean enabled) {
        contactSyncEnabled = enabled;
    }

    public boolean isLeaveOnServer() {
        return leaveOnServer;
    }

    public void setLeaveOnServer(boolean leaveOnServer) {
        this.leaveOnServer = leaveOnServer;
    }

    public boolean isSyncAllServerFolders() {
        return syncAllServerFolders;
    }

    public void setSyncAllServerFolders(boolean syncAllServerFolders) {
        this.syncAllServerFolders = syncAllServerFolders;
    }
}
