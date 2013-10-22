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
package org.zmail.cs.account.offline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.offline.OfflineLC;
import org.zmail.cs.offline.OfflineLog;
import org.zmail.cs.offline.common.OfflineConstants;
import org.zmail.cs.offline.jsp.JspConstants;

public class OfflineAccount extends Account {

    Account localAccount; // the local account that acts as everyone's parent. this will be null if this account itself is the local account

    public static class Version {
        private static Version v6 = new Version("6.0.0");
        private static Version v7 = new Version("7.0.0");
        private static Version v8 = new Version("8.0.0");

        private int major;
        private int minor;
        private int maintenance;

        public Version(String version) {
            for (int i = 0; i < version.length(); ++i) {
                char c = version.charAt(i);
                if (!Character.isDigit(c) && c != '.') {
                    version = version.substring(0, i);
                    break;
                }
            }

            try {
                String[] digits = version.split("\\.");

                if (digits.length > 0)
                    major = Integer.parseInt(digits[0]);
                if (digits.length > 1)
                    minor = Integer.parseInt(digits[1]);
                if (digits.length > 2)
                    maintenance = Integer.parseInt(digits[2]);
            } catch (Exception t) {
                OfflineLog.offline.warn("unknown remote server version: " + version);
            }
        }

        public int getMajor() {
            return major;
        }

        public int getMinor() {
            return minor;
        }

        public int getMaintenance() {
            return maintenance;
        }

        public boolean isAtLeast(Version required) {
            return major > required.major || major == required.major && minor > required.minor
                    || major == required.major && minor == required.minor && maintenance >= required.maintenance;
        }

        public boolean isAtLeast6xx() {
            return isAtLeast(v6);
        }

        public boolean isAtLeast7xx() {
            return isAtLeast(v7);
        }

        public boolean isAtLeast8xx() {
            return isAtLeast(v8);
        }

        public String toString() {
            return "" + major + "." + minor + "." + maintenance;
        }
    }

    private Version mRemoteServerVersion;

    public Version getRemoteServerVersion() {
        if (mRemoteServerVersion == null)
            mRemoteServerVersion = new Version(getAttr(OfflineProvisioning.A_offlineRemoteServerVersion));
        return mRemoteServerVersion;
    }

    public void resetRemoteServerVersion() {
        mRemoteServerVersion = null;
    }

    public OfflineAccount(String name, String id, Map<String, Object> attrs, Map<String, Object> defaults,
            Account localAccount, Provisioning prov) {
        super(name, id, attrs, defaults, prov);
        this.localAccount = localAccount;
    }

    private static final String[] sDisabledFeatures = new String[] { Provisioning.A_zmailFeatureIMEnabled,
            Provisioning.A_zmailFeatureViewInHtmlEnabled, Provisioning.A_zmailFeatureNotebookEnabled,
            Provisioning.A_zmailDumpsterEnabled };

    private static final Set<String> sDisabledFeaturesSet = new HashSet<String>();

    static {
        for (String feature : sDisabledFeatures)
            sDisabledFeaturesSet.add(feature.toLowerCase());
    }

    private static final String[] sGlobalAttributes = new String[] { Provisioning.A_zmailPrefIncludeTrashInSearch,
            Provisioning.A_zmailPrefIncludeSpamInSearch };

    private static final Set<String> sGlobalAttributesSet = new HashSet<String>();

    static {
        for (String attr : sGlobalAttributes)
            sGlobalAttributesSet.add(attr.toLowerCase());
    }

    @Override
    public String getAttr(String name, boolean applyDefaults) {
        // disable certain features here rather than trying to make the cached values and the remote values differ
        if (sDisabledFeaturesSet.contains(name.toLowerCase()))
            return "FALSE";

        if (localAccount != null && sGlobalAttributesSet.contains(name.toLowerCase()))
            return localAccount.getAttr(name, applyDefaults);

        if (name.equals(Provisioning.A_zmailPrefMailPollingInterval))
            return OfflineLC.zdesktop_client_poll_interval.value();

        return super.getAttr(name, applyDefaults);
    }

    @Override
    protected Map<String, Object> getRawAttrs() {
        Map<String, Object> attrs = new HashMap<String, Object>(super.getRawAttrs());

        for (String feature : sDisabledFeatures)
            attrs.put(feature, "FALSE");
        if (localAccount != null)
            for (String attr : sGlobalAttributes)
                attrs.put(attr, localAccount.getAttr(attr));

        attrs.put(Provisioning.A_zmailPrefMailPollingInterval, OfflineLC.zdesktop_client_poll_interval.value());
        return attrs;
    }

    @Override
    public Map<String, Object> getAttrs(boolean applyDefaults) {
        Map<String, Object> attrs = new HashMap<String, Object>(super.getAttrs(applyDefaults));

        for (String feature : sDisabledFeatures)
            attrs.put(feature, "FALSE");
        if (localAccount != null)
            for (String attr : sGlobalAttributes)
                attrs.put(attr, localAccount.getAttr(attr));

        attrs.put(Provisioning.A_zmailPrefMailPollingInterval, OfflineLC.zdesktop_client_poll_interval.value());
        return attrs;
    }

    @Override
    public String[] getMultiAttr(String name) {
        if (isLocalAccount()
                && (name.equals(Provisioning.A_zmailChildAccount) || name
                        .equals(Provisioning.A_zmailPrefChildVisibleAccount))) {
            try {
                OfflineProvisioning prov = OfflineProvisioning.getOfflineInstance();
                List<String> accountIds = prov.getAllAccountIds();
                if (!accountIds.isEmpty()) {
                    List<String> idsInOrder = new ArrayList<String>();
                    for (String id : accountIds) {
                        Account acct = prov.get(AccountBy.id, id);
                        if (acct == null || prov.isGalAccount(acct) || prov.isMountpointAccount(acct) || prov.isLocalAccount(acct)) {
                            continue;
                        }
                        idsInOrder.add(id);
                    }
                    String[] array = new String[idsInOrder.size()];
                    int i = 0;
                    for (String id : idsInOrder) {
                        array[i++] = id;
                    }
                    return array;
                } else {
                    return new String[0];
                }
            } catch (ServiceException x) {
                OfflineLog.offline.error(x);
            }
        }
        // no need to return this multi-attr for any child accounts
        if (!isLocalAccount() && name.equals(Provisioning.A_zmailZimletAvailableZimlets))
            return new String[0];

        return super.getMultiAttr(name);
    }

    // only change an attribute of a cached OfflineAccount object; the change is not committed to database
    public void setCachedAttr(String key, String value) {
        super.getRawAttrs().put(key, value);
    }

    public String getRemotePassword() {
        return getAttr(OfflineProvisioning.A_offlineRemotePassword);
    }

    public boolean isZcsAccount() {
        return OfflineProvisioning.getOfflineInstance().isZcsAccount(this);
    }

    public boolean isExchangeAccount() {
        return OfflineProvisioning.getOfflineInstance().isExchangeAccount(this);
    }

    public boolean isGalAccount() {
        return OfflineProvisioning.getOfflineInstance().isGalAccount(this);
    }

    public boolean isLocalAccount() {
        return localAccount == null; // the localAccount field will be null if this account is the local account
    }

    public boolean isDataSourceAccount() {
        return OfflineProvisioning.isDataSourceAccount(this);
    }

    public void setLastSyncTimestamp() throws ServiceException {
        setLastSyncTimestampInternal(System.currentTimeMillis());
    }

    public void resetLastSyncTimestamp() throws ServiceException {
        setLastSyncTimestampInternal(0);
    }

    private void setLastSyncTimestampInternal(long time) throws ServiceException {
        if (isZcsAccount()) {
            OfflineProvisioning.getOfflineInstance().setAccountAttribute(this, OfflineConstants.A_offlineLastSync,
                    Long.toString(time));
        } else if (isDataSourceAccount()) {
            DataSource ds = OfflineProvisioning.getOfflineInstance().getDataSource(this);
            OfflineProvisioning.getOfflineInstance().setDataSourceAttribute(ds,
                    OfflineConstants.A_zmailDataSourceLastSync, Long.toString(time));
        }
    }

    boolean isRequestScopeDebugTraceOn = false;

    public synchronized void setRequestScopeDebugTraceOn(boolean b) {
        isRequestScopeDebugTraceOn = b;
    }

    public synchronized boolean isDebugTraceEnabled() {
        return isRequestScopeDebugTraceOn || getBooleanAttr(OfflineConstants.A_offlineEnableTrace, false);
    }

    boolean isDisabledDueToError = false;

    public void setDisabledDueToError(boolean disabled) {
        isDisabledDueToError = disabled;
    }

    public boolean isDisabledDueToError() {
        return isDisabledDueToError;
    }

    boolean isGalSyncRetryOn = true;

    public boolean isGalSyncRetryOn() {
        return isGalSyncRetryOn;
    }

    public void setGalSyncRetryOn(boolean isGalSyncRetryOn) {
        this.isGalSyncRetryOn = isGalSyncRetryOn;
    }

    /**
     * @return the value of offlineRemoteHost (server name, not domain name)
     */
    public String getRemoteHost() {
        return getAttr(JspConstants.OFFLINE_REMOTE_HOST);
    }

    /**
     * @return domain name of the account, e.g. yy.com of xx@yy.com
     */
    public String getDomain() {
        if (!isGalAccount()) {
            String mailAddr = getMail();
            if (mailAddr != null) {
                return mailAddr.substring(mailAddr.lastIndexOf("@") + 1);
            }
        } else {
            // should be consistent with OfflineProvisioning::getOfflineGalMailboxName
            String mailAddr = getMail();
            return mailAddr.substring(mailAddr.lastIndexOf("@") + 1,
                    mailAddr.lastIndexOf(OfflineConstants.GAL_ACCOUNT_SUFFIX));
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        final OfflineAccount anotherAccount = (OfflineAccount) obj;
        return Objects.equal(getId(), anotherAccount.getId());
    }

    public static void main(String[] args) {
        assert new Version("4.5.9").isAtLeast(new Version("4.5"));
        assert !new Version("4.5.9").isAtLeast(new Version("4.5.11"));
        assert new Version("5.0.5").isAtLeast(new Version("5"));
        assert new Version("5.0.5").isAtLeast(new Version("5.0"));
        assert new Version("5.0.5").isAtLeast(new Version("5.0.5"));
        assert !new Version("5.0.5").isAtLeast(new Version("5.0.6"));
        assert !new Version("5.0.5").isAtLeast(new Version("6"));
        assert !new Version("5.0.5").isAtLeast(new Version("6.0"));
        assert new Version("5.0.6").isAtLeast(new Version("4.5.9"));
        assert new Version("6.0.5").isAtLeast(new Version("5.0.6"));
    }
}
