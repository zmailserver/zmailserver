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
package org.zmail.cs.account.callback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.DateUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.SearchAccountsOptions;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.ldap.LdapProv;
import org.zmail.cs.datasource.DataSourceManager;
import org.zmail.cs.db.DbMailbox;
import org.zmail.cs.db.DbPool;
import org.zmail.cs.db.DbPool.DbConnection;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.util.Zmail;

/**
 * Validates <tt>DataSource</tt> attribute values.
 */
public class DataSourceCallback extends AttributeCallback {

    private static final Set<String> INTERVAL_ATTRS = new HashSet<String>();

    static {
        INTERVAL_ATTRS.add(Provisioning.A_zmailDataSourcePollingInterval);
        INTERVAL_ATTRS.add(Provisioning.A_zmailDataSourcePop3PollingInterval);
        INTERVAL_ATTRS.add(Provisioning.A_zmailDataSourceImapPollingInterval);
        INTERVAL_ATTRS.add(Provisioning.A_zmailDataSourceLivePollingInterval);
        INTERVAL_ATTRS.add(Provisioning.A_zmailDataSourceRssPollingInterval);
        INTERVAL_ATTRS.add(Provisioning.A_zmailDataSourceCaldavPollingInterval);
        INTERVAL_ATTRS.add(Provisioning.A_zmailDataSourceYabPollingInterval);
        INTERVAL_ATTRS.add(Provisioning.A_zmailDataSourceCalendarPollingInterval);
    }

    /**
     * Confirms that polling interval values are not set lower than the minimum.
     */
    @SuppressWarnings("unchecked")
    @Override 
    public void preModify(CallbackContext context, String attrName, Object attrValue,
            Map attrsToModify, Entry entry)
    throws ServiceException {
        if (INTERVAL_ATTRS.contains(attrName)) {
            String interval = (String) attrValue;
            if (entry instanceof DataSource) {
                validateDataSource((DataSource) entry, interval);
            } else if (entry instanceof Account) {
                validateAccount((Account) entry, attrName, interval);
            } else if (entry instanceof Cos) {
                validateCos((Cos) entry, attrName, interval);
            }
        }
    }

    /**
     * Updates scheduled tasks for data sources whose polling interval has changed.
     */
    @SuppressWarnings("unchecked")
    @Override 
    public void postModify(CallbackContext context, String attrName, Entry entry) {
        // Don't do anything unless inside the server
        if (!Zmail.started() || !LC.data_source_scheduling_enabled.booleanValue()) {
            return;
        }
        
        // Don't do anything if this postModify is triggered by creating a COS,
        // because no account will be on this COS yet.
        if (context.isCreate() && (entry instanceof Cos))
            return;

        if (INTERVAL_ATTRS.contains(attrName) || Provisioning.A_zmailDataSourceEnabled.equals(attrName)) {
            // Update schedules for any affected data sources
            try {
                if (entry instanceof DataSource) {
                    scheduleDataSource((DataSource) entry);
                } else if (entry instanceof Account) {
                    scheduleAccount((Account) entry);
                } else if (entry instanceof Cos) {
                    scheduleCos((Cos) entry);
                }
            } catch (ServiceException e) {
                ZmailLog.datasource.warn("Unable to update schedule for %s", entry, e);
            }
        } else if (entry instanceof DataSource) {
            // Reset error status on any attribute changes (bug 39050).
            DataSourceManager.resetErrorStatus((DataSource) entry);
        }
    }

    private void validateDataSource(DataSource ds, String newInterval)
    throws ServiceException {
        Account account = ds.getAccount();
        if (account == null) {
            ZmailLog.datasource.warn("Could not determine account for %s", ds);
            return;
        }
        validateInterval(Provisioning.A_zmailDataSourcePollingInterval,
            newInterval, account.getAttr(Provisioning.A_zmailDataSourceMinPollingInterval));
    }

    private void scheduleDataSource(DataSource ds)
    throws ServiceException {
        Account account = ds.getAccount();
        if (account == null) {
            ZmailLog.datasource.warn("Could not determine account for %s", ds);
            return;
        }
        DataSourceManager.updateSchedule(account, ds);
    }

    private void validateAccount(Account account, String attrName, String newInterval)
    throws ServiceException {
        validateInterval(attrName, newInterval, account.getAttr(Provisioning.A_zmailDataSourceMinPollingInterval));
    }

    private void scheduleAccount(Account account)
    throws ServiceException {
        ZmailLog.datasource.info("Updating schedule for all DataSources for account %s.", account.getName());
        List<DataSource> dataSources = Provisioning.getInstance().getAllDataSources(account);
        for (DataSource ds : dataSources) {
            DataSourceManager.updateSchedule(account, ds);
        }
    }

    private void validateCos(Cos cos, String attrName, String newInterval)
    throws ServiceException {
        validateInterval(newInterval, attrName, cos.getAttr(Provisioning.A_zmailDataSourceMinPollingInterval));
    }

    /**
     * Updates data source schedules for all accounts that are on the current server
     * and in the given COS.
     */
    private void scheduleCos(Cos cos)
    throws ServiceException {
        ZmailLog.datasource.info("Updating schedule for all DataSources for all accounts in COS %s.", cos.getName());

        List<Account> accts;
        Provisioning prov = Provisioning.getInstance();

        // Look up all account id's for this server
        if (prov instanceof LdapProv)
            accts = lookupAccountsFromLDAP(prov, cos.getId());
        else
            accts = lookupAccountsFromDB(prov);

        // Update schedules for all data sources on this server
        for (Account account : accts) {
            if (account != null && Provisioning.ACCOUNT_STATUS_ACTIVE.equals(account.getAccountStatus(prov))) {
                Cos accountCos = prov.getCOS(account);
                if (accountCos != null && cos.getId().equals(accountCos.getId())) {
                    scheduleAccount(account);
                }
            }

        }
    }

    // look up all accounts on this server
    private List<Account> lookupAccountsFromDB(Provisioning prov) throws ServiceException {
        Set<String> accountIds = null;
        List<Account> accts = new ArrayList<Account>();

        DbConnection conn = null;
        try {
            conn = DbPool.getConnection();
            accountIds = DbMailbox.listAccountIds(conn);
        } finally {
            DbPool.quietClose(conn);
        }

        for (String accountId : accountIds) {
            Account account = null;
            try {
                account = prov.get(AccountBy.id, accountId);
            } catch (ServiceException e) {
                ZmailLog.datasource.debug("Unable to look up account for id %s: %s", accountId, e.toString());
            }

            if (account != null) {
                accts.add(account);
            }
        }

        return accts;
    }

    /*
     * look up all accounts on this server with either the specified cos id, or without a cos id set on the account
     * returns:
     *   - all accounts on this server
     *   - and with either the specified cos id, or without a cos id set on the account
     *   - and has at least one sub-entries
     *     (we can't tell whether those sub-entries are data sources, but this is as close as we can be searching for)
     */
    private List<Account> lookupAccountsFromLDAP(Provisioning prov, String cosId)
    throws ServiceException{

        SearchAccountsOptions searchOpts = new SearchAccountsOptions();
        searchOpts.setFilter(ZLdapFilterFactory.getInstance().accountsOnServerAndCosHasSubordinates(
                prov.getLocalServer().getServiceHostname(), cosId));
        List accts = prov.searchDirectory(searchOpts);

        return accts;
    }

    private void validateInterval(String attrName, String newInterval, String minInterval)
    throws ServiceException {
        long interval = DateUtil.getTimeInterval(newInterval, 0);
        if (interval == 0) {
            return;
        }
        long lMinInterval = DateUtil.getTimeInterval(minInterval, 0);
        if (interval < lMinInterval) {
            String msg = String.format(
                "Polling interval %s for %s is shorter than the allowed minimum of %s.",
                newInterval, attrName, minInterval);
            throw ServiceException.INVALID_REQUEST(msg, null);
        }
    }
}
