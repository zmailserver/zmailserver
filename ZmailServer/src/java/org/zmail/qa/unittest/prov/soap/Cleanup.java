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
package org.zmail.qa.unittest.prov.soap;

import org.zmail.cs.account.soap.SoapProvisioning;
import org.zmail.cs.ldap.unboundid.InMemoryLdapServer;
import org.zmail.soap.admin.type.CacheEntryType;

public class Cleanup {
    static void deleteAll(String... domainNames) throws Exception {
        
        if (InMemoryLdapServer.isOn()) {
            return;
        }
        
        org.zmail.qa.unittest.prov.ldap.Cleanup.deleteAll(domainNames);
        
        SoapProvisioning prov = SoapProvisioning.getAdminInstance();
        prov.flushCache(
                CacheEntryType.account.name() + "," +
                CacheEntryType.group.name() + "," +
                CacheEntryType.config.name() + "," +
                CacheEntryType.globalgrant.name() + "," +
                CacheEntryType.cos.name() + "," +
                CacheEntryType.domain.name() + "," +
                CacheEntryType.mime.name() + "," +
                CacheEntryType.server.name() + "," +
                CacheEntryType.zimlet.name(), 
                null, true);
    }
}
