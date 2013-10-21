/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.cs.account.ldap.upgrade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Entry.EntryType;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZLdapContext;

public class BUG_75450 extends UpgradeOp {

    private static final String ATTR_NAME = Provisioning.A_zmailPrefSkin;
    private static final String FROM_VALUE = "carbon";
    private static final String TO_VALUE = "serenity";

    @Override
    void doUpgrade() throws ServiceException {
        ZLdapContext zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
        try {
            doDomains(zlc);
            doCos(zlc);
        } finally {
            LdapClient.closeContext(zlc);
        }
    }

    @Override
    Description getDescription() {
        return new Description(this,
                new String[] {ATTR_NAME},
                new EntryType[] {EntryType.DOMAIN, EntryType.COS},
                FROM_VALUE,
                TO_VALUE,
                "Update skin to 8.0 default for all COS/Domains where it is set to the previous default");
    }

    private void doDomains(ZLdapContext zlc) throws ServiceException {
        List<Domain> domains = prov.getAllDomains();
        if (domains != null) {
            for (Domain domain : domains) {
                String skin = domain.getAttr(ATTR_NAME, "");
                if (FROM_VALUE.equalsIgnoreCase(skin)) {
                    Map<String, Object> attrs = new HashMap<String, Object>();
                    attrs.put(ATTR_NAME, TO_VALUE);
                    modifyAttrs(domain, attrs);
                }
            }
        }
    }

    private void doCos(ZLdapContext zlc) throws ServiceException {
        List<Cos> classes = prov.getAllCos();
        if (classes != null) {
            for (Cos cos : classes) {
                String skin = cos.getAttr(ATTR_NAME, "");
                if (FROM_VALUE.equalsIgnoreCase(skin)) {
                    Map<String, Object> attrs = new HashMap<String, Object>();
                    attrs.put(ATTR_NAME, TO_VALUE);
                    modifyAttrs(cos, attrs);
                }
            }
        }
    }
}