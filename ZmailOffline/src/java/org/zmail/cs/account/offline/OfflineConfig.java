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

import org.zmail.common.account.ProvisioningConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.offline.OfflineProvisioning.EntryType;
import org.zmail.cs.db.DbOfflineDirectory;
import org.zmail.cs.offline.OfflineLC;
import org.zmail.cs.offline.OfflineLog;

class OfflineConfig extends Config {
    private OfflineConfig(Map<String, Object> attrs, Provisioning provisioning) {
        super(attrs, provisioning);
    }


    static synchronized OfflineConfig instantiate(Provisioning provisioning) {
        try {
            Map<String, Object> attrs = DbOfflineDirectory.readDirectoryEntry(OfflineProvisioning.EntryType.CONFIG, OfflineProvisioning.A_offlineDn, "config");
            if (attrs == null) {
                attrs = new HashMap<String, Object>(2);
                attrs.put(Provisioning.A_cn, "config");
                attrs.put(Provisioning.A_objectClass, "zmailGlobalConfig");
                try {
                    DbOfflineDirectory.createDirectoryEntry(OfflineProvisioning.EntryType.CONFIG, "config", attrs, false);
                } catch (ServiceException x) {
                    OfflineLog.offline.error("can't save config", x); //shouldn't really happen.  see bug 34567
                }
            }
            String[] skins = OfflineLC.zdesktop_skins.value().split("\\s*,\\s*");
            attrs.put(Provisioning.A_zmailInstalledSkin, skins);
            if (!OfflineLC.zdesktop_redolog_enabled.booleanValue()) {
                attrs.put(Provisioning.A_zmailRedoLogEnabled, OfflineLC.zdesktop_redolog_enabled.booleanValue() ? ProvisioningConstants.TRUE : ProvisioningConstants.FALSE);
                attrs.put(Provisioning.A_zmailRedoLogFsyncIntervalMS, 0);
            }
            attrs.put(Provisioning.A_zmailSmtpSendAddMailer, ProvisioningConstants.FALSE);
            attrs.put(Provisioning.A_zmailNotebookAccount, "local@host.local");
            attrs.put(Provisioning.A_zmailMtaMaxMessageSize, OfflineLC.zdesktop_upload_size_limit.value());

            attrs.put(Provisioning.A_zmailBatchedIndexingSize, OfflineLC.zdesktop_batched_indexing_size.value());
            attrs.put(Provisioning.A_zmailMailDiskStreamingThreshold, OfflineLC.zdesktop_mail_disk_streaming_threshold.value());
            attrs.put(Provisioning.A_zmailMailFileDescriptorCacheSize, OfflineLC.zdesktop_mail_file_descriptor_cache_size.value());
            attrs.put(Provisioning.A_zmailMessageCacheSize, OfflineLC.zdesktop_message_cache_size.value());
            attrs.put(Provisioning.A_zmailMessageIdDedupeCacheSize, "0");
            attrs.put(Provisioning.A_zmailNotebookPageCacheSize, "96");

            return new OfflineConfig(attrs, provisioning);
        } catch (ServiceException e) {
            // throw RuntimeException because we're being called at startup...
            throw new RuntimeException("failure instantiating global Config: " + e.getMessage(), e);
        }
    }
}
