/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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
package com.zimbra.cs.datasource;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.DataSource;
import com.zimbra.cs.db.DbDataSource;
import com.zimbra.cs.db.DbActiveSyncMessage;
import com.zimbra.cs.db.DbDataSource.DataSourceItem;

public class ActiveSyncMessage extends DataSourceMapping {
    public ActiveSyncMessage(DataSource ds, DataSourceItem dsi) throws ServiceException {
        super(ds, dsi);
    }
    
    public ActiveSyncMessage(DataSource ds, int itemId) throws ServiceException {
        super(ds, itemId);
    }
    
    public ActiveSyncMessage(DataSource ds, String uid) throws ServiceException {
        super(ds, uid);
    }
    
    public ActiveSyncMessage(DataSource ds, int itemId, String uid) throws
        ServiceException {
        super(ds, ds.getFolderId(), itemId, uid);
    }
    
    public static Set<ActiveSyncMessage> getMappings(DataSource ds, String[] remoteIds)
        throws ServiceException {
        Collection<DataSourceItem> mappings = DbDataSource.getReverseMappings(ds,
            Arrays.asList(remoteIds));
        Set<ActiveSyncMessage> matchingMsgs = new HashSet<ActiveSyncMessage>();
        
        if (mappings.isEmpty()) {
            Map<Integer, String> oldMappings = DbActiveSyncMessage.getMappings(
                DataSourceManager.getInstance().getMailbox(ds), ds.getId());

            for (Integer itemId : oldMappings.keySet()) {
                String uid = oldMappings.get(itemId);
                ActiveSyncMessage mapping = new ActiveSyncMessage(ds, itemId, uid);
                
                mapping.add();
                for (String remoteId : remoteIds) {
                    if (remoteId.equals(uid))
                        matchingMsgs.add(mapping);
                }
            }
            if (!oldMappings.isEmpty())
                DbActiveSyncMessage.deleteUids(DataSourceManager.getInstance().getMailbox(ds), ds.getName());
        } else {
            for (DataSourceItem mapping : mappings)
                matchingMsgs.add(new ActiveSyncMessage(ds, mapping));
        }
        return matchingMsgs;
    }

    public static Set<String> getMatchingUids(DataSource ds, String[] remoteIds)
        throws ServiceException {
        Set<ActiveSyncMessage> matchingMsgs = getMappings(ds, remoteIds);
        Set<String> matchingUids = new HashSet<String>(matchingMsgs.size());
               
        for (ActiveSyncMessage msg : matchingMsgs)
            matchingUids.add(msg.getRemoteId());
        return matchingUids;
    }
}

