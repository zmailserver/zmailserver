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
package org.zmail.cs.service.admin;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.SearchAccountsOptions;
import org.zmail.cs.account.SearchAccountsOptions.IncludeType;
import org.zmail.cs.account.SearchDirectoryOptions.MakeObjectOpt;
import org.zmail.cs.account.accesscontrol.AccessControlUtil;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.soap.ZmailSoapContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAggregateQuotaUsageOnServer extends AdminDocumentHandler {

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        if (!AccessControlUtil.isGlobalAdmin(getAuthenticatedAccount(zsc))) {
            throw ServiceException.PERM_DENIED("only global admin is allowed");
        }

        Map<String, Long> domainAggrQuotaUsed = new HashMap<String, Long>();

        SearchAccountsOptions searchOpts = new SearchAccountsOptions();
        searchOpts.setIncludeType(IncludeType.ACCOUNTS_ONLY);
        searchOpts.setMakeObjectOpt(MakeObjectOpt.NO_DEFAULTS);

        Provisioning prov = Provisioning.getInstance();
        List<NamedEntry> accounts = prov.searchAccountsOnServer(prov.getLocalServer(), searchOpts);
        Map<String, Long> acctQuotaUsed = MailboxManager.getInstance().getMailboxSizes(accounts);
        for (NamedEntry ne : accounts) {
            if (!(ne instanceof Account)) {
                continue;
            }
            Account acct = (Account) ne;
            Long acctQuota = acctQuotaUsed.get(acct.getId());
            if (acctQuota == null) {
                acctQuota = 0L;
            }
            String domainId = acct.getDomainId();
            if (domainId == null) {
                continue;
            }
            Long aggrQuota = domainAggrQuotaUsed.get(domainId);
            domainAggrQuotaUsed.put(domainId, aggrQuota == null ? acctQuota : aggrQuota + acctQuota);
        }

        Element response = zsc.createElement(AdminConstants.GET_AGGR_QUOTA_USAGE_ON_SERVER_RESPONSE);
        for (String domainId : domainAggrQuotaUsed.keySet()) {
            Domain domain = prov.getDomainById(domainId);
            Element domainElt = response.addElement(AdminConstants.E_DOMAIN);
            domainElt.addAttribute(AdminConstants.A_NAME, domain.getName());
            domainElt.addAttribute(AdminConstants.A_ID, domainId);
            domainElt.addAttribute(AdminConstants.A_QUOTA_USED, domainAggrQuotaUsed.get(domainId));
        }
        return response;
    }
}
