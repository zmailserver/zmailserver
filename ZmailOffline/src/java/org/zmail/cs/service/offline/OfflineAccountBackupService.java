/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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
package org.zmail.cs.service.offline;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.offline.backup.AccountBackupProducer;
import org.zmail.cs.offline.common.OfflineConstants;
import org.zmail.soap.DocumentHandler;
import org.zmail.soap.ZmailSoapContext;

public class OfflineAccountBackupService extends DocumentHandler {
    
    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
        String id = request.getAttribute(AdminConstants.E_ID, null);
        Map<String,String> status = null;
        if (id != null) {
            status = AccountBackupProducer.getInstance().backupAccounts(new String[]{id});
        } else {
            status = AccountBackupProducer.getInstance().backupAllAccounts();
        }
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Element response = zsc.createElement(OfflineConstants.ACCOUNT_BACKUP_RESPONSE);
        for (String acctId : status.keySet()) {
            String backupStatus = status.get(acctId);
            Element acctElem = response.addElement(AdminConstants.E_ACCOUNT);
            acctElem.addAttribute(AdminConstants.E_ID, acctId);
            acctElem.addAttribute(AdminConstants.E_STATUS, backupStatus);
        }
        return response;
    }
}
