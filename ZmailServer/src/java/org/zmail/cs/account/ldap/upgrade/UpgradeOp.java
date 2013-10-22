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
package org.zmail.cs.account.ldap.upgrade;

import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.ldap.LdapProv;
import org.zmail.cs.account.ldap.entry.LdapEntry;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapException;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZLdapFilter;
import org.zmail.cs.ldap.ZLdapFilterFactory;
import org.zmail.cs.ldap.ZMutableEntry;
import org.zmail.cs.ldap.ZLdapFilterFactory.FilterId;

public abstract class UpgradeOp {

    LdapUpgradePrinter printer;

    protected boolean verbose;  // always verbose now
    protected String bug;
    protected LdapProv prov;

    abstract void doUpgrade() throws ServiceException;

    boolean parseCommandLine(CommandLine cl) {
        // do nothing
        return true;
    }

    void usage(HelpFormatter helpFormatter) {
        // do nothing
    }

    void setPrinter(LdapUpgradePrinter printer) {
        this.printer = printer;
    }

    void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    void setLdapProv(LdapProv prov) {
        this.prov = prov;
    }

    String getBug() {
        return bug;
    }

    void setBug(String bug) {
        this.bug = bug;
    }

    void describe() {
        Description desc = getDescription();
        if (desc == null) {
            printer.format("\nBug %s", getBug() + ": description not available.\n\n");
        } else {
            desc.describe();
        }
    }

    Description getDescription() {
        return null;
    }

    private void printModAttrs(String dn, Map<String, Object> attrs) {
        printer.println();
        printer.println("Modifying " + dn);
        printModAttrs(attrs);
    }

    private void printModAttrs(Entry entry, Map<String, Object> attrs) {
        if (!attrs.isEmpty()) {
            printer.println();
            printer.println("Modifying " + entry.getEntryType().name() + " " + entry.getLabel());
            printModAttrs(attrs);
        }
    }

    private void printModAttrs(Map<String, Object> attrs) {
        for (Map.Entry<String, Object> attr : attrs.entrySet()) {
            String key = attr.getKey();
            Object value = attr.getValue();

            if (value instanceof String) {
                printer.println("  " + key + ": " + (String)value);
            } else if (value instanceof String[]) {
                for (String v : (String[])value) {
                    printer.println("  " + key + ": " + v);
                }
            }
        }
    }

    protected void replaceAttrs(ZLdapContext initZlc, String dn, ZMutableEntry entry)
    throws ServiceException {
        Map<String, Object> attrs = entry.getAttributes().getAttrs();
        if (attrs.size() == 0) {
            return;
        }

        printModAttrs(dn, attrs);

        ZLdapContext zlc = initZlc;
        try {
            if (zlc == null) {
                zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
            }
            zlc.replaceAttributes(dn, entry.getAttributes());

        } finally {
            if (initZlc == null) {
                LdapClient.closeContext(zlc);
            }
        }
    }

    protected void modifyAttrs(ZLdapContext initZlc, Entry entry, Map<String, Object> attrs)
    throws ServiceException {
        if (attrs.isEmpty()) {
            return;
        }

        printModAttrs(entry, attrs);

        ZLdapContext zlc = initZlc;
        try {
            if (zlc == null) {
                zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
            }
            prov.getHelper().modifyAttrs(zlc, ((LdapEntry)entry).getDN(), attrs, entry);
        } finally {
            if (initZlc == null) {
                LdapClient.closeContext(zlc);
            }
        }
    }

    protected void modifyAttrs(Entry entry, Map<String, Object> attrs)
    throws ServiceException {
        if (attrs.isEmpty()) {
            return;
        }

        printModAttrs(entry, attrs);
        prov.modifyAttrs(entry, attrs);
    }

    protected ZLdapFilter getFilter(String filter) throws LdapException {
        return ZLdapFilterFactory.getInstance().fromFilterString(FilterId.LDAP_UPGRADE, filter);
    }

}
