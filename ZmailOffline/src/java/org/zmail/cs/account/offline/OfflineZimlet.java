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
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Zimlet;
import org.zmail.cs.account.offline.OfflineProvisioning.EntryType;
import org.zmail.cs.db.DbOfflineDirectory;

class OfflineZimlet extends Zimlet {
    OfflineZimlet(String name, String id, Map<String, Object> attrs, Provisioning prov) {
        super(name, id, attrs, prov);
    }

    static Map<String,Zimlet> instantiateAll(Provisioning prov) {
        Map<String,Zimlet> zmap = new HashMap<String,Zimlet>();
        try {
            List<String> ids = DbOfflineDirectory.listAllDirectoryEntries(OfflineProvisioning.EntryType.ZIMLET);
            for (String id : ids) {
                Map<String, Object> attrs = DbOfflineDirectory.readDirectoryEntry(OfflineProvisioning.EntryType.ZIMLET, Provisioning.A_zmailId, id);
                if (attrs == null)
                    continue;
                String name = (String) attrs.get(Provisioning.A_cn);
                if (name != null)
                    zmap.put(name.toLowerCase(), new OfflineZimlet(name, id, attrs, prov));
            }
            return zmap;
        } catch (ServiceException e) {
            // throw RuntimeException because we're being called at startup...
            throw new RuntimeException("failure instantiating zimlets", e);
        }
    }
}