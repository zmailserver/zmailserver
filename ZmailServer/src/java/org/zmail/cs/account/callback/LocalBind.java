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
package org.zmail.cs.account.callback;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zmail.common.account.ZAttrProvisioning.MailMode;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;

public class LocalBind extends AttributeCallback {

    @Override
    public void preModify(CallbackContext context, String attrName, Object attrValue,
            Map attrsToModify, Entry entry)
    throws ServiceException {
    }
    
    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
        // Update zmailAdminLocalBind if zmailAdminBindAddress is changed.
        if (entry instanceof Server) {
            Server server = (Server) entry;
            if (attrName.equals(Provisioning.A_zmailAdminBindAddress)) {
                String address = server.getAttr(Provisioning.A_zmailAdminBindAddress, true);
                try{
                    if ((address == null) || (address.isEmpty())) {
                        server.setAdminLocalBind(false);
                    } else {
                        InetAddress inetAddress;
                        try {
                            inetAddress = InetAddress.getByName(address);
                        } catch (UnknownHostException e) {
                            server.setAdminLocalBind(false);
                            return;
                        }
                        if (inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress())
                            server.setAdminLocalBind(false);
                        else
                            server.setAdminLocalBind(true);
                    }
                } catch (ServiceException se) {
                    ZmailLog.misc.warn("Unable to update zmailAdminLocalBind " + se);
                }
                return;
            }
        }
        
        // Update zmailMailLocalBind if zmailMailMode or zmailMailBindAddress or zmailMailSSLBindAddress is changed.
        // zmailMailMode is also in globalConfig. Make sure to update the zmailMailLocalBind of all the
        // servers if this globalconfig is changed.
        
        List<Server> serverList = new ArrayList<Server>();
        if (entry instanceof Config) {
            try {
                serverList = Provisioning.getInstance().getAllServers();
            } catch (ServiceException se) {
                ZmailLog.misc.warn("Unable to get server list " + se);
            }
        } else if (entry instanceof Server) {
            serverList.add((Server) entry);
        } 
            
        if (attrName.equals(Provisioning.A_zmailMailMode) ||
                attrName.equals(Provisioning.A_zmailMailBindAddress) ||
                attrName.equals(Provisioning.A_zmailMailSSLBindAddress)) {
            for (Server server : serverList) {
                try {
                    MailMode mailMode = server.getMailMode();
                    if (mailMode == null)
                        mailMode = Provisioning.getInstance().getConfig().getMailMode();
                    if (mailMode == null || !mailMode.isHttps()) {
                        // http is enabled. Check if bindaddress conflicts with localhost.
                        String address = server.getAttr(Provisioning.A_zmailMailBindAddress, true);
                        if ((address == null) || (address.isEmpty())) {
                            server.setMailLocalBind(false);
                        } else {
                            InetAddress inetAddress;
                            try {
                                inetAddress = InetAddress.getByName(address);
                            } catch (UnknownHostException e) {
                                server.setMailLocalBind(false);
                                continue;
                            }
                            if (inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress())
                                server.setMailLocalBind(false);
                            else
                                server.setMailLocalBind(true);
                        }
                    } else {
                        // mailmode set to https. Enable http for localhost binding.
                        server.setMailLocalBind(true);
                    }
                } catch (ServiceException e) {
                    ZmailLog.misc.warn("Unable to set zmailMailLocalBind " + e);
                    continue;
                }
            }
        }
    }
}
