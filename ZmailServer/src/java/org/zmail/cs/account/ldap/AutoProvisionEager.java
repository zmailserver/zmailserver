/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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
package org.zmail.cs.account.ldap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key.DomainBy;
import org.zmail.common.account.ZAttrProvisioning.AutoProvMode;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.DateUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Provisioning.EagerAutoProvisionScheduler;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.ldap.entry.LdapEntry;
import org.zmail.cs.ldap.IAttributes;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.SearchLdapOptions.SearchLdapVisitor;
import org.zmail.cs.ldap.SearchLdapOptions.StopIteratingException;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZLdapFilter;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.util.Zmail;

public class AutoProvisionEager extends AutoProvision {
    private EagerAutoProvisionScheduler scheduler;
    
    private AutoProvisionEager(LdapProv prov, Domain domain, EagerAutoProvisionScheduler scheduler) {
        super(prov, domain);
        this.scheduler = scheduler;
    }
    
    static void handleScheduledDomains(LdapProv prov, EagerAutoProvisionScheduler scheduler) {
        ZLdapContext zlc = null;
        
        try {
            // get scheduled domains on this server
            Server localServer = prov.getLocalServer();
            String[] scheduledDomains = localServer.getAutoProvScheduledDomains();
            
            zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.AUTO_PROVISION);
            
            for (String domainName : scheduledDomains) {
                if (scheduler.isShutDownRequested()) {
                    ZmailLog.autoprov.info("eager auto provision aborted");
                    return;
                }
                
                try {
                    Domain domain = prov.get(DomainBy.name, domainName);
                    if (domain == null) {
                        ZmailLog.autoprov.info("EAGER auto provision: no such domain " + domainName);
                        continue;
                    }
                    
                    // refresh the domain from LDAP master so we don't get into a race 
                    // condition if the domains was just enabled for EAGER mode on other node.
                    prov.reload(domain, true);
                    
                    if (!autoProvisionEnabled(domain)) {
                        /*
                         * remove it from the scheduled domains on the local server
                         */
                        
                        ZmailLog.autoprov.info("Domain %s is scheduled for EAGER auto provision " +
                                "but EAGER mode is not enabled on the domain.  " +
                                "Removing domain %s from %s on server %s", 
                                domain.getName(), domain.getName(), 
                                Provisioning.A_zmailAutoProvScheduledDomains, localServer.getName());
                        
                        // will trigger callback for AutoProvScheduledDomains.  If scheduled 
                        // domains become empty, the EAGER auto prov thread will be requested 
                        // to shutdown.
                        localServer.removeAutoProvScheduledDomains(domain.getName());
                        continue;
                    }
                    
                    ZmailLog.autoprov.info("Auto provisioning accounts on domain %s", domainName);
                    AutoProvisionEager autoProv = new AutoProvisionEager(prov, domain, scheduler);
                    autoProv.handleBatch(zlc);
                } catch (Throwable t) {
                    if (t instanceof OutOfMemoryError) {
                        Zmail.halt("Ran out of memory while auto provision accounts", t);
                    } else {
                        ZmailLog.autoprov.warn("Unable to auto provision accounts for domain %s", domainName, t);
                    }
                }
            }
        } catch (ServiceException e) {
            // unable to get ldap context
            ZmailLog.autoprov.warn("Unable to auto provision accounts", e);
        } finally {
            LdapClient.closeContext(zlc);
        }
    }
    
    private void handleBatch(ZLdapContext zlc) throws ServiceException {
        if (!autoProvisionEnabled(domain)) {
            throw ServiceException.FAILURE("EAGER auto provision is not enabled on domain " 
                    + domain.getName(), null);
        }

        try {
            if (!lockDomain(zlc)) {
                ZmailLog.autoprov.info("EAGER auto provision unable to lock domain: skip domain " +
                        domain.getName() + " on server " + prov.getLocalServer().getName());
                return;
            }
            createAccountBatch();
        } finally {
            unlockDomain(zlc);
        }
    }

    @Override
    Account handle() throws ServiceException {
        throw new UnsupportedOperationException();
    }
    
    private static boolean autoProvisionEnabled(Domain domain) {
        return domain.getMultiAttrSet(Provisioning.A_zmailAutoProvMode).contains(AutoProvMode.EAGER.name());
    }
    
    private void createAccountBatch() throws ServiceException {
        
        long polledAt = System.currentTimeMillis();
        
        List<ExternalEntry> entries = new ArrayList<ExternalEntry>();
        boolean hitSizeLimitExceededException = searchAccounts(entries, domain.getAutoProvBatchSize());
        ZmailLog.autoprov.info("%d external LDAP entries returned as search result", entries.size());
        int stuckAcctNum = 0;
        for (ExternalEntry entry : entries) {
            if (scheduler.isShutDownRequested()) {
                ZmailLog.autoprov.info("eager auto provision aborted");
                return;
            }
            
            try {
                ZAttributes externalAttrs = entry.getAttrs();
                String acctZmailName = mapName(externalAttrs, null);

                ZmailLog.autoprov.info("auto creating account in EAGER mode: " + acctZmailName + ", dn=\"" + entry.getDN() + "\"");
                Account acct = createAccount(acctZmailName, entry, null, AutoProvMode.EAGER);
                if (acct == null) {
                    stuckAcctNum++;
                }
            } catch (ServiceException e) {
                // log and continue with next entry
                ZmailLog.autoprov.warn("unable to auto create account, dn=\"" + entry.getDN() + "\"", e);
                stuckAcctNum++;
            }
        }

        //if we hit size limit and all returning items are stuck items, increase batch size to keep processing batches,
        //in the last batch we won't hit size limit, then the last polled timstamp will be set, we can forget about the stuck ones
        if (hitSizeLimitExceededException && entries.size() == stuckAcctNum) {
            ZmailLog.autoprov.info("search result contains unsuccessful external entries, increasing batch size by %d", stuckAcctNum);
            int currentBatchSize = domain.getAutoProvBatchSize();
            domain.setAutoProvBatchSize(currentBatchSize + stuckAcctNum);
            ZmailLog.autoprov.info("batch size is %d now", domain.getAutoProvBatchSize());
        }

        // Keep track of the last polled timestamp.
        // The next batch will fetch entries with createTimeStamp later than the last polled 
        // timestamp in this batch.
        // But don't update it if the search hit SizeLimitExceededException.  In that case 
        // we want to retain the current stamp so the next poll will still use this stamp.
        // Note: it is expected a AutoProvisionListener is configured on the domain.  
        //       The postCreate method of AutoProvisionListener should update the external 
        //       directory to indicate the entry was provisioned in Zmail.  
        //       Also, the same assertion (e.g. (provisionNotes=provisioned-in-zmail)) 
        //       should be included in zmailAutoProvLdapSearchFilter.
        //
        //       See how TestLdapProvAutoProvision.eagerMode() does it.
        //
        if (!hitSizeLimitExceededException) {
            String lastPolledAt = DateUtil.toGeneralizedTime(new Date(polledAt));
            ZmailLog.autoprov.info("Auto Provisioning has finished for now, setting last polled timestamp: " + lastPolledAt);
            domain.setAutoProvLastPolledTimestampAsString(lastPolledAt);
        }
    }

    private boolean lockDomain(ZLdapContext zlc) throws ServiceException {
        Server localServer = prov.getLocalServer();
        
        ZLdapFilter filter = ZLdapFilterFactory.getInstance().domainLockedForEagerAutoProvision();
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailAutoProvLock, localServer.getId());
        
        boolean gotLock = prov.getHelper().testAndModifyEntry(zlc, ((LdapEntry)domain).getDN(), 
                filter, attrs, domain);
        
        // need to refresh the domain entry, because this modify is not done via the normal 
        // LdapProvisioning.modifyAttr path.  
        prov.reload(domain, true);
        ZmailLog.autoprov.debug("lock domain %s", gotLock ? "successful" : "failed");

        return gotLock;
    }
    
    private void unlockDomain(ZLdapContext zlc) throws ServiceException {
        // clear the server id in the lock
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailAutoProvLock, "");
        
        prov.getHelper().modifyAttrs(zlc, ((LdapEntry)domain).getDN(), attrs, domain);
        
        // need to refresh the domain entry, because this modify is not done via the normal 
        // LdapProvisioning.modifyAttr path.  
        prov.reload(domain, true);
        ZmailLog.autoprov.debug("domain unlocked");
    }

    private boolean searchAccounts(final List<ExternalEntry> entries, int batchSize) 
    throws ServiceException {
        String lastPolledAt = domain.getAutoProvLastPolledTimestampAsString();
        String[] returnAttrs = getAttrsToFetch();
        
        SearchLdapVisitor visitor = new SearchLdapVisitor(false) {
            @Override
            public void visit(String dn, IAttributes ldapAttrs)
            throws StopIteratingException {
                entries.add(new ExternalEntry(dn, (ZAttributes)ldapAttrs));
            }
        };

        boolean hitSizeLimitExceededException = 
                AutoProvision.searchAutoProvDirectory(prov, domain, null, null, 
                        lastPolledAt, returnAttrs, batchSize, visitor, true);
        ZmailLog.autoprov.debug("searched external LDAP source, hit size limit ? %s", hitSizeLimitExceededException);
        return hitSizeLimitExceededException;
    }

}
