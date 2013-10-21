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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Constants;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZLdapContext;

public class BUG_32719 extends UpgradeOp {

    @Override
    void doUpgrade() throws ServiceException {
        ZLdapContext zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
        try {
            doGlobalConfig(zlc);
            doAllServers(zlc);
        } finally {
            LdapClient.closeContext(zlc);
        }
    }
    
    private void doEntry(ZLdapContext zlc, Entry entry, String entryName) throws ServiceException {
        
        String oldAttr = Provisioning.A_zmailHsmAge;
        String newAttr = Provisioning.A_zmailHsmPolicy;
        
        printer.println();
        printer.println("Checking " + entryName);
        
        String oldValue = entry.getAttr(oldAttr, false);
        String newValue = entry.getAttr(newAttr, false);
        if (oldValue != null) {
            if (newValue == null) {
                newValue = String.format("message,document:before:-%dminutes", 
                        entry.getTimeInterval(oldAttr, 0) / Constants.MILLIS_PER_MINUTE);
                
                printer.println("    Setting " + newAttr + " on " + entryName + 
                        " from " + oldAttr + " value: [" + oldValue + "]" + 
                        " to [" + newValue + "]");
                
                Map<String, Object> attr = new HashMap<String, Object>();
                attr.put(newAttr, newValue);
                prov.modifyAttrs(entry, attr);
            } else
                printer.println("    " + newAttr + " already has a value: [" + newValue + "], skipping"); 
        }
    }

    private void doGlobalConfig(ZLdapContext zlc) throws ServiceException {
        Config config = prov.getConfig();
        doEntry(zlc, config, "global config");
    }
    
    private void doAllServers(ZLdapContext zlc) throws ServiceException {
        List<Server> servers = prov.getAllServers();
        
        for (Server server : servers)
            doEntry(zlc, server, "server " + server.getName());
    }

}
