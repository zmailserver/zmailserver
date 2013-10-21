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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Strings;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZLdapContext;

public class BUG_57205 extends UpgradeOp {

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
    
    void doEntry(ZLdapContext zlc, Entry entry) throws ServiceException {
        printer.printCheckingEntry(entry);
        
        String attrName = Provisioning.A_zmailReverseProxyImapEnabledCapability;
        
        String[] curValues = entry.getMultiAttr(attrName, false);
        if (curValues.length == 0) {
            // no value on entry, do not update
            return;
        }
        
        List<String> curValueList = Arrays.asList(curValues);
                
        Map<String, Object> attrs = new HashMap<String, Object>();
        
        if (!curValueList.contains("LIST-STATUS")) {
            StringUtil.addToMultiMap(attrs, "+" + attrName, "LIST-STATUS");
        }
        
        if (!curValueList.contains("XLIST")) {
            StringUtil.addToMultiMap(attrs, "+" + attrName, "XLIST");
        }
        
        if (!attrs.isEmpty()) {
            modifyAttrs(entry, attrs);
        }
    }
    
    
    private void doGlobalConfig(ZLdapContext zlc) throws ServiceException {
        Config config = prov.getConfig();
        doEntry(zlc, config);
    }
    
    private void doAllServers(ZLdapContext zlc) throws ServiceException {
        List<Server> servers = prov.getAllServers();
        
        for (Server server : servers) {
            doEntry(zlc, server);
        }
    }

}