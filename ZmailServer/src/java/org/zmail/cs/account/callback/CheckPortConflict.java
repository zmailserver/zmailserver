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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.AttributeManager;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;

public class CheckPortConflict extends AttributeCallback {

    private static final Set<String> sPortAttrs = new HashSet<String>();

    static {
        // TODO: use a flag in zmail-attrs.xml and generate this map automatically
        sPortAttrs.add(Provisioning.A_zmailAdminPort);
        sPortAttrs.add(Provisioning.A_zmailAdminProxyPort);

        sPortAttrs.add(Provisioning.A_zmailImapBindPort);
        sPortAttrs.add(Provisioning.A_zmailImapSSLBindPort);
        sPortAttrs.add(Provisioning.A_zmailImapProxyBindPort);
        sPortAttrs.add(Provisioning.A_zmailImapSSLProxyBindPort);

        sPortAttrs.add(Provisioning.A_zmailLmtpBindPort);

        sPortAttrs.add(Provisioning.A_zmailMailPort);
        sPortAttrs.add(Provisioning.A_zmailMailSSLPort);
        sPortAttrs.add(Provisioning.A_zmailMailSSLClientCertPort);

        sPortAttrs.add(Provisioning.A_zmailPop3BindPort);
        sPortAttrs.add(Provisioning.A_zmailPop3SSLBindPort);
        sPortAttrs.add(Provisioning.A_zmailPop3ProxyBindPort);
        sPortAttrs.add(Provisioning.A_zmailPop3SSLProxyBindPort);

        sPortAttrs.add(Provisioning.A_zmailRemoteManagementPort);

        sPortAttrs.add(Provisioning.A_zmailMemcachedBindPort);

        sPortAttrs.add(Provisioning.A_zmailMailProxyPort);
        sPortAttrs.add(Provisioning.A_zmailMailSSLProxyPort);
        sPortAttrs.add(Provisioning.A_zmailMailSSLProxyClientCertPort);

        sPortAttrs.add(Provisioning.A_zmailMessageChannelPort);
    }

    @Override
    public void preModify(CallbackContext context, String attrName, Object attrValue,
            Map attrsToModify, Entry entry)
    throws ServiceException {

        if (entry != null && !(entry instanceof Server) && !(entry instanceof Config)) return;

        if (context.isDoneAndSetIfNot(CheckPortConflict.class)) {
            return;
        }

        // sanity check, zmail-attrs.xml and the sPortAttrsToCheck map has to be in sync
        if (!sPortAttrs.contains(attrName) ||
            !AttributeManager.getInstance().isServerInherited(attrName))
            assert(false);

        // server == null means the server entry is being created
        if (entry == null || entry instanceof Server)
            checkServer((Server)entry, attrsToModify);
        else
            checkConfig((Config)entry, attrsToModify);
    }

    private void checkServer(Server server, Map<String, Object> serverAttrsToModify) throws ServiceException {
        Map<String, String> ports = new HashMap<String, String>();
        Map<String, Object> defaults = Provisioning.getInstance().getConfig().getServerDefaults();

        // collect current port values
        if (server != null) {
            for (String attrName : sPortAttrs) {
                if (!serverAttrsToModify.containsKey(attrName))
                    ports.put(server.getAttr(attrName), attrName);
            }
        }

        // check conflict for attrs being changed
        for (Map.Entry<String, Object> attrToModify : serverAttrsToModify.entrySet()) {
            String attrName = attrToModify.getKey();

            if (!sPortAttrs.contains(attrName))
                continue;

            SingleValueMod mod = singleValueMod(serverAttrsToModify, attrName);
            String newValue = null;
            if (mod.setting())
                newValue = mod.value();
            else {
                // unsetting, get default, which would become the new value
                Object defValue = defaults.get(attrName);
                if (defValue != null) {
                    if (defValue instanceof String)
                        newValue = (String)defValue;
                    else
                        ZmailLog.misc.info("default value for " + attrName + " should be a single-valued attribute, invalid default value ignored");
                }
            }

            if (conflict(ports, newValue)) {
                String serverInfo = (server == null) ? "" : " on server " + server.getName();
                throw ServiceException.INVALID_REQUEST("port " + newValue + " conflict between " +
                                                       attrName + " and " + ports.get(newValue) + serverInfo, null);
            } else
                ports.put(newValue, attrName);
        }
    }

    private void checkConfig(Config config, Map<String, Object> configAttrsToModify) throws ServiceException {
        BiMap<String, String> newDefaults = HashBiMap.create();

        /*
         * First, make sure there is no conflict in the Config entry, even
         * if the value on the config entry might not be effective on a server.
         */
        for (String attrName : sPortAttrs) {
            if (!configAttrsToModify.containsKey(attrName))
                newDefaults.put(config.getAttr(attrName), attrName);
        }

        // check conflict for attrs being changed
        for (Map.Entry<String, Object> attrToModify : configAttrsToModify.entrySet()) {
            String attrName = attrToModify.getKey();

            if (!sPortAttrs.contains(attrName))
                continue;

            SingleValueMod mod = singleValueMod(configAttrsToModify, attrName);
            String newValue = null;
            if (mod.setting())
                newValue = mod.value();

            if (conflict(newDefaults, newValue)) {
                throw ServiceException.INVALID_REQUEST("port " + newValue + " conflict between " +
                        attrName + " and " + newDefaults.get(newValue) + " on global config", null);
            } else
                newDefaults.put(newValue, attrName);
        }

        /*
         * Then, iterate through all servers see if this port change on the Config
         * entry has impact on a server.
         */
        List<Server> servers = Provisioning.getInstance().getAllServers();
        for (Server server : servers) {
            checkServerWithNewDefaults(server, newDefaults, configAttrsToModify);
        }
    }

    private void checkServerWithNewDefaults(Server server, BiMap<String, String> newDefaults,
            Map<String, Object> configAttrsToModify)
    throws ServiceException {
        Map<String, String> ports = new HashMap<String, String>();

        for (String attrName : sPortAttrs) {
            String newValue = null;
            String curValue = server.getAttr(attrName, false); // value on the server entry
            if (curValue == null)
                newValue = newDefaults.inverse().get(attrName);  // will inherit from new default
            else
                newValue = curValue;

            if (conflict(ports, newValue)) {
                String conflictWith = ports.get(newValue);
                // throw only when the attr is one of the attrs being modified, otherwise, just let it pass.
                if (configAttrsToModify.containsKey(attrName) || configAttrsToModify.containsKey(conflictWith))
                    throw ServiceException.INVALID_REQUEST("port " + newValue + " conflict between " +
                        attrName + " and " + ports.get(newValue) + " on server " + server.getName(), null);
            } else
                ports.put(newValue, attrName);
        }
    }

    private boolean conflict(Map<String, String> ports, String port) {

        if (StringUtil.isNullOrEmpty(port))
            return false;
        else if (port.equals("0"))
            return false;
        else
            return ports.containsKey(port);
    }

    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }
}
