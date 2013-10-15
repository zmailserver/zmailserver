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
package org.zmail.cs.mailbox;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.offline.OfflineAccount;
import org.zmail.cs.gal.GalGroup.GroupInfo;
import org.zmail.cs.gal.GalGroupInfoProvider;
import org.zmail.cs.offline.OfflineLog;

/**
 * Provide GroupInfo from entries in OfflineGal
 * 
 */
public class OfflineGalGroupInfoProvider extends GalGroupInfoProvider {

    @Override
    public GroupInfo getGroupInfo(String addr, boolean needCanExpand, Account requestedAcct, Account authedAcct) {
        OfflineAccount reqAccount = (OfflineAccount) requestedAcct;
        if (reqAccount.isZcsAccount() && reqAccount.isFeatureGalEnabled() && reqAccount.isFeatureGalSyncEnabled()) {
            try {
                Contact con = GalSyncUtil.getGalDlistContact(reqAccount, addr);
                if (con != null && con.isGroup()) {
                    return needCanExpand ? GroupInfo.CAN_EXPAND : GroupInfo.IS_GROUP;
                }
            } catch (ServiceException e) {
                OfflineLog.offline.error("Unable to find group %s addr due to exception", e, addr);
            }
        }
        return null;
    }

}
