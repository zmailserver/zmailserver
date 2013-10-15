/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.service.offline;

import java.util.Map;

import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.offline.OfflineAccount;
import org.zmail.cs.account.offline.OfflineProvisioning;
import org.zmail.cs.mailbox.GalSync;
import org.zmail.cs.offline.OfflineLog;
import org.zmail.cs.offline.common.OfflineConstants;
import org.zmail.cs.service.admin.AdminDocumentHandler;
import org.zmail.soap.ZmailSoapContext;

public class OfflineResetGal extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        String accountId = request.getAttribute(AdminConstants.E_ID);
        OfflineProvisioning prov = OfflineProvisioning.getOfflineInstance();
        OfflineAccount account = (OfflineAccount) prov.get(AccountBy.id, accountId);
        if (account.isFeatureGalEnabled() && account.isFeatureGalSyncEnabled()) {
            OfflineAccount galAccount = (OfflineAccount) prov.getGalAccountByAccount(account);
            if (GalSync.isFullSynced(galAccount)) {
                boolean isReset = GalSync.getInstance().resetGal(galAccount);
                if (!isReset) {
                    OfflineLog.offline.debug("reseting gal for account %s -- Skipped because GAL is recently synced",
                            account.getName());
                }
            } else {
                OfflineLog.offline.debug(
                        "reseting gal for account %s -- Skipped because GAL has not finished initial sync",
                        account.getName());
            }
        } else {
            OfflineLog.offline.debug("Offline GAL sync is disabled for %s, resetting skipped.", account.getName());
        }

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Element resp = zsc.createElement(OfflineConstants.RESET_GAL_ACCOUNT_RESPONSE);
        return resp;
    }
}
