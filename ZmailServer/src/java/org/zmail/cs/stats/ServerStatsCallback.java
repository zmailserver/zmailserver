/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.stats;

import java.util.HashMap;
import java.util.Map;

import org.zmail.common.stats.RealtimeStatsCallback;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.PermissionCache;
import org.zmail.cs.account.ldap.LdapProv;
import org.zmail.cs.mailbox.MessageCache;
import org.zmail.cs.store.BlobInputStream;
import org.zmail.cs.store.FileDescriptorCache;


public class ServerStatsCallback implements RealtimeStatsCallback {

    public Map<String, Object> getStatData() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(ZmailPerf.RTS_MBOX_CACHE_SIZE, ZmailPerf.getMailboxCacheSize());
        data.put(ZmailPerf.RTS_MSG_CACHE_SIZE, MessageCache.getSize());
        
        FileDescriptorCache fdc = BlobInputStream.getFileDescriptorCache();
        data.put(ZmailPerf.RTS_FD_CACHE_SIZE, fdc.getSize());
        data.put(ZmailPerf.RTS_FD_CACHE_HIT_RATE, fdc.getHitRate());
        
        data.put(ZmailPerf.RTS_ACL_CACHE_HIT_RATE, PermissionCache.getHitRate());
        
        Provisioning prov = Provisioning.getInstance();
        if (prov instanceof LdapProv) {
            LdapProv ldap = (LdapProv) prov;
            data.put(ZmailPerf.RTS_ACCOUNT_CACHE_SIZE, ldap.getAccountCacheSize());
            data.put(ZmailPerf.RTS_ACCOUNT_CACHE_HIT_RATE, ldap.getAccountCacheHitRate());
            data.put(ZmailPerf.RTS_COS_CACHE_SIZE, ldap.getCosCacheSize());
            data.put(ZmailPerf.RTS_COS_CACHE_HIT_RATE, ldap.getCosCacheHitRate());
            data.put(ZmailPerf.RTS_DOMAIN_CACHE_SIZE, ldap.getDomainCacheSize());
            data.put(ZmailPerf.RTS_DOMAIN_CACHE_HIT_RATE, ldap.getDomainCacheHitRate());
            data.put(ZmailPerf.RTS_SERVER_CACHE_SIZE, ldap.getServerCacheSize());
            data.put(ZmailPerf.RTS_SERVER_CACHE_HIT_RATE, ldap.getServerCacheHitRate());
            data.put(ZmailPerf.RTS_UCSERVICE_CACHE_SIZE, ldap.getUCServiceCacheSize());
            data.put(ZmailPerf.RTS_UCSERVICE_CACHE_HIT_RATE, ldap.getUCServiceCacheHitRate());
            data.put(ZmailPerf.RTS_ZIMLET_CACHE_SIZE, ldap.getZimletCacheSize());
            data.put(ZmailPerf.RTS_ZIMLET_CACHE_HIT_RATE, ldap.getZimletCacheHitRate());
            data.put(ZmailPerf.RTS_GROUP_CACHE_SIZE, ldap.getGroupCacheSize());
            data.put(ZmailPerf.RTS_GROUP_CACHE_HIT_RATE, ldap.getGroupCacheHitRate());
            data.put(ZmailPerf.RTS_XMPP_CACHE_SIZE, ldap.getXMPPCacheSize());
            data.put(ZmailPerf.RTS_XMPP_CACHE_HIT_RATE, ldap.getXMPPCacheHitRate());
        }
        return data;
    }

}
