/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.account.offline;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.zmail.common.localconfig.LC;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.offline.OfflineLC;

class OfflineLocalServer extends Server {
    private OfflineLocalServer(OfflineConfig oconfig, Map<String, Object> attrs, Provisioning prov) {
        super((String) attrs.get(Provisioning.A_cn), (String) attrs.get(Provisioning.A_zmailId), attrs, oconfig.getServerDefaults(), prov);
    }

    static OfflineLocalServer instantiate(OfflineConfig oconfig, Provisioning prov) {
        Map<String, Object> attrs = new HashMap<String, Object>(12);
        attrs.put(Provisioning.A_objectClass, "zmailServer");
        attrs.put(Provisioning.A_cn, "localhost");
        attrs.put(Provisioning.A_zmailServiceHostname, "localhost");
        attrs.put(Provisioning.A_zmailSmtpHostname, "localhost");
        attrs.put(Provisioning.A_zmailSmtpPort, "25");
        attrs.put(Provisioning.A_zmailSmtpTimeout, "60000");        
        attrs.put(Provisioning.A_zmailSmtpSendPartial, "false");
        attrs.put(Provisioning.A_zmailId, UUID.randomUUID().toString());
        attrs.put("zmailServiceEnabled", "mailbox");
        attrs.put("zmailServiceInstalled", "mailbox");
        attrs.put(Provisioning.A_zmailMailPort, LC.zmail_admin_service_port.value()); //in offline both are the same
        attrs.put(Provisioning.A_zmailAdminPort, LC.zmail_admin_service_port.value());
        attrs.put(Provisioning.A_zmailMailMode, "http");
        attrs.put(Provisioning.A_zmailLmtpNumThreads, "1");
        attrs.put(Provisioning.A_zmailLmtpBindPort, "7635");
        attrs.put(Provisioning.A_zmailFileUploadMaxSize, OfflineLC.zdesktop_upload_size_limit.value());
        return new OfflineLocalServer(oconfig, attrs, prov);
    }
}
