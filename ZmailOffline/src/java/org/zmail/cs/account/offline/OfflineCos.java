/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.offline.OfflineProvisioning.EntryType;
import org.zmail.cs.db.DbOfflineDirectory;
import org.zmail.cs.offline.OfflineLC;

class OfflineCos extends Cos {
    OfflineCos(String name, String id, Map<String, Object> attrs, Provisioning prov) {
        super(name, id, attrs, prov);
    }

    static OfflineCos instantiate(Provisioning prov) {
        try {
            Map<String, Object> attrs = DbOfflineDirectory.readDirectoryEntry(OfflineProvisioning.EntryType.COS, OfflineProvisioning.A_offlineDn, "default");
            if (attrs == null) {
                attrs = new HashMap<String, Object>(3);
                attrs.put(Provisioning.A_cn, "default");
                attrs.put(Provisioning.A_objectClass, "zmailCOS");
                attrs.put(Provisioning.A_zmailId, UUID.randomUUID().toString());
                DbOfflineDirectory.createDirectoryEntry(OfflineProvisioning.EntryType.COS, "default", attrs, false);
            }
            
            //make sure auth token doesn't expire too soon
            attrs.put(Provisioning.A_zmailAuthTokenLifetime, OfflineLC.auth_token_lifetime.value());
            attrs.put(Provisioning.A_zmailAdminAuthTokenLifetime, OfflineLC.auth_token_lifetime.value());
            
            //allow proxy to any domains
            attrs.put(Provisioning.A_zmailProxyAllowedDomains, "*");
            
            return new OfflineCos("default", (String) attrs.get(Provisioning.A_zmailId), attrs, prov);
        } catch (ServiceException e) {
            // throw RuntimeException because we're being called at startup...
            throw new RuntimeException("failure instantiating default cos", e);
        }
    }
}
