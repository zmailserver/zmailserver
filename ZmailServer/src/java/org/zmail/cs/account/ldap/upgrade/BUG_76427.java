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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Entry.EntryType;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZLdapContext;

public class BUG_76427 extends UpgradeOp {

    public static Set<String> standardZimlets = new HashSet<String>();

    static {
        standardZimlets.add("org_zmail_adminversioncheck");
        standardZimlets.add("org_zmail_archive");
        standardZimlets.add("org_zmail_attachcontacts");
        standardZimlets.add("org_zmail_attachmail");
        standardZimlets.add("org_zmail_backuprestore");
        standardZimlets.add("org_zmail_bulkprovision");
        standardZimlets.add("org_zmail_cert_manager");
        standardZimlets.add("org_zmail_click2call_cisco");
        standardZimlets.add("org_zmail_click2call_mitel");
        standardZimlets.add("org_zmail_clientuploader");
        standardZimlets.add("org_zmail_convertd");
        standardZimlets.add("org_zmail_date");
        standardZimlets.add("org_zmail_delegatedadmin");
        standardZimlets.add("org_zmail_email");
        standardZimlets.add("org_zmail_hsm");
        standardZimlets.add("org_zmail_license");
        standardZimlets.add("org_zmail_mobilesync");
        standardZimlets.add("org_zmail_phone");
        standardZimlets.add("org_zmail_proxy_config");
        standardZimlets.add("org_zmail_smime");
        standardZimlets.add("org_zmail_smime_cert_admin");
        standardZimlets.add("org_zmail_srchhighlighter");
        standardZimlets.add("org_zmail_tooltip");
        standardZimlets.add("org_zmail_ucconfig");
        standardZimlets.add("org_zmail_url");
        standardZimlets.add("org_zmail_viewmail");
        standardZimlets.add("org_zmail_voiceprefs");
        standardZimlets.add("org_zmail_webex");
        standardZimlets.add("org_zmail_xmbxsearch");
        standardZimlets.add("org_zmail_ymemoticons");
    }

    @Override
    void doUpgrade() throws ServiceException {
        ZLdapContext zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
        try {
            doGlobalConfig(zlc);
            doAllDomain(zlc);
            doAllCos(zlc);
        } finally {
            LdapClient.closeContext(zlc);
        }
    }

    private void doEntry(ZLdapContext zlc, Entry entry, String entryName, String attr) throws ServiceException {

        printer.println();
        printer.println("------------------------------");
        printer.println("Upgrading " + entryName + ": ");

        if (verbose) {
            printer.println("");
            printer.println("Checking " + entryName + ", attribute: " + attr);
        }

        Set<String> values = entry.getMultiAttrSet(attr);
        if (values.isEmpty()) {
            if (verbose) {
                printer.println("Current value is empty. No changes needed for " + entryName + ", attribute: " + attr);
            }
            return;
        }

        boolean modified = false;
        Map<String, Object> attrs = new HashMap<String, Object>();
        for (String value : values) {
            String zimletName = value;
            if (value.startsWith("!") || value.startsWith("+") || value.startsWith("-")) {
                zimletName = value.substring(1);
            }
            if (!standardZimlets.contains(zimletName) && !value.startsWith("-")) {
                StringUtil.addToMultiMap(attrs, attr, "-" + zimletName);
                modified = true;
            } else {
                StringUtil.addToMultiMap(attrs, attr, value);
            }
        }

        if (!modified) {
            if (verbose) {
                printer.println("No changes needed for " + entryName + ", attribute: " + attr);
            }
            return;
        }

        try {
            modifyAttrs(zlc, entry, attrs);
        } catch (ServiceException e) {
            // log the exception and continue
            printer.println("Caught ServiceException while modifying " + entryName + " attribute " + attr);
            printer.printStackTrace(e);
        }
    }

    private void doGlobalConfig(ZLdapContext zlc) throws ServiceException {
        Config config = prov.getConfig();
        doEntry(zlc, config, "global config", Provisioning.A_zmailZimletDomainAvailableZimlets);
    }

    private void doAllDomain(ZLdapContext zlc) throws ServiceException {
        List<Domain> domains = prov.getAllDomains();
        for (Domain domain : domains) {
            String name = "domain " + domain.getName();
            doEntry(zlc, domain, name, Provisioning.A_zmailZimletDomainAvailableZimlets);
        }
    }

    private void doAllCos(ZLdapContext zlc) throws ServiceException {
        List<Cos> coses = prov.getAllCos();
        for (Cos cos : coses) {
            String name = "cos " + cos.getName();
            doEntry(zlc, cos, name, Provisioning.A_zmailZimletAvailableZimlets);
        }
    }

    @Override
    Description getDescription() {
        return new Description(this,
                new String[] {Provisioning.A_zmailZimletAvailableZimlets, Provisioning.A_zmailZimletDomainAvailableZimlets},
                new EntryType[] {EntryType.DOMAIN, EntryType.COS},
                "[Current Zimlets]",
                standardZimlets.toString(),
                "Disable all non-standard Zimlets." );
    }

}