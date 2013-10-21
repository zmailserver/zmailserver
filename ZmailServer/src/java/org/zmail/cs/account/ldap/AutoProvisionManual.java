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

import java.util.Set;

import org.zmail.common.account.ZAttrProvisioning.AutoProvMode;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.soap.type.AutoProvPrincipalBy;

public class AutoProvisionManual extends AutoProvision {

    private AutoProvPrincipalBy by;
    private String principal;
    private String password;
    
    protected AutoProvisionManual(LdapProv prov, Domain domain, 
            AutoProvPrincipalBy by, String principal, String password) {
        super(prov, domain);
        this.by = by;
        this.principal = principal;
        this.password = password;
    }

    @Override
    Account handle() throws ServiceException {
        if (!autoProvisionEnabled()) {
            throw ServiceException.FAILURE("MANUAL auto provision is not enabled on domain " 
                    + domain.getName(), null);
        }
        
        return createAccount();
    }
    
    private boolean autoProvisionEnabled() {
        Set<String> modesEnabled = domain.getMultiAttrSet(Provisioning.A_zmailAutoProvMode);
        return modesEnabled.contains(AutoProvMode.MANUAL.name());
    }
    
    private Account createAccount() throws ServiceException {
        String acctZmailName;
        ExternalEntry externalEntry;
        if (by == AutoProvPrincipalBy.dn) {
            ZAttributes externalAttrs = getExternalAttrsByDn(principal);
            externalEntry = new ExternalEntry(principal, externalAttrs);
            acctZmailName = mapName(externalAttrs, null);
        } else if (by == AutoProvPrincipalBy.name) {
            externalEntry = getExternalAttrsByName(principal);
            acctZmailName = mapName(externalEntry.getAttrs(), principal);
        } else {
            throw ServiceException.FAILURE("unknown AutoProvPrincipalBy", null);
        }

        ZmailLog.autoprov.info("auto creating account in MANUAL mode: " + acctZmailName);
        return createAccount(acctZmailName, externalEntry, password, AutoProvMode.MANUAL);
    }

}
