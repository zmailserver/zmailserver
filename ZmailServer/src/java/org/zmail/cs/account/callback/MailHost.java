/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.util.Config;

public class MailHost extends AttributeCallback {

    /**
     * check to make sure zmailMailHost points to a valid server zmailServiceHostname
     */
    @Override
    public void preModify(CallbackContext context, String attrName, Object value,
            Map attrsToModify, Entry entry) 
    throws ServiceException {
        
        if (StringUtil.isNullOrEmpty((String)value) || 
            attrsToModify.get("-" + Provisioning.A_zmailMailHost) != null)
            return; // unsetting
        
        String mailHost = (String)value;
        String mailTransport = (String)attrsToModify.get(Provisioning.A_zmailMailTransport);
        
        /*
         * never allow setting both zmailMailHost and zmailMailTransport in the same request
         */
        if (!StringUtil.isNullOrEmpty(mailHost) && !StringUtil.isNullOrEmpty(mailTransport))
            throw ServiceException.INVALID_REQUEST("setting both " + 
                    Provisioning.A_zmailMailHost + " and " +  Provisioning.A_zmailMailTransport + 
                    " in the same request is not allowed", null);
        
        Provisioning prov = Provisioning.getInstance();
        
        Server server = prov.get(Key.ServerBy.serviceHostname, mailHost);
        if (server == null)
            throw ServiceException.INVALID_REQUEST("specified " + 
                    Provisioning.A_zmailMailHost + 
                    " does not correspond to a valid server service hostname: "+mailHost, null);
        else {
            if (!server.hasMailboxService()) {
                throw ServiceException.INVALID_REQUEST("specified " + Provisioning.A_zmailMailHost + 
                        " does not correspond to a valid server with the mailbox service enabled: " 
                        + mailHost, null);    
            }
            
            /*
             * bug 18419
             * 
             * If zmailMailHost is modified, see if applying lmtp rule to old 
             * zmailMailHost value would result in old zmailMailTransport - 
             * if it would, then replace both zmailMailHost and set new zmailMailTransport.  
             * Otherwise error.
             */
            if (entry != null && !context.isCreate()) {
        	
                String oldMailHost = entry.getAttr(Provisioning.A_zmailMailHost);
                if (oldMailHost != null) {
                    Server oldServer = prov.get(Key.ServerBy.serviceHostname, oldMailHost);
                    if (oldServer != null) {
                	    String curMailTransport = entry.getAttr(Provisioning.A_zmailMailTransport);
                	    if (!oldServer.mailTransportMatches(curMailTransport)) {
                            throw ServiceException.INVALID_REQUEST(
                                    "current value of zmailMailHost does not match zmailMailTransport" +
                                    ", computed mail transport from current zmailMailHost=" + 
                                    mailTransport(oldServer) + ", current zmailMailTransport=" + 
                                    curMailTransport, 
                        	        null);
                	    }
                    }
                }
            } else {
                // we are creating the account
            }
            
            // also update mail transport to match the new mail host
            String newMailTransport = mailTransport(server);
            attrsToModify.put(Provisioning.A_zmailMailTransport, newMailTransport);
        }
    }
    
    private static String mailTransport(Server server) {
        String serviceName = server.getAttr(Provisioning.A_zmailServiceHostname, null);
        int lmtpPort = server.getIntAttr(Provisioning.A_zmailLmtpBindPort, Config.D_LMTP_BIND_PORT);
        String transport = "lmtp:" + serviceName + ":" + lmtpPort;
        return transport;
    }


    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }

}
