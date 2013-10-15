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
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.offline.backup.AccountBackupProducer;
import org.zmail.cs.offline.common.OfflineConstants;
import org.zmail.soap.DocumentHandler;
import org.zmail.soap.ZmailSoapContext;

public class OfflineAccountRestoreService extends DocumentHandler {
    
    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        String accountId = request.getAttribute(AccountConstants.E_ID, null);
        String timestamp = request.getAttribute(AdminConstants.A_TIME, null);
        String resolve = request.getAttribute(OfflineConstants.A_RESOLVE, null);
        Element response = zsc.createElement(OfflineConstants.ACCOUNT_RESTORE_RESPONSE);
        response.addAttribute(AccountConstants.A_ID, accountId);
        response.addAttribute(AccountConstants.A_STATUS, AccountBackupProducer.getInstance().restoreAccount(accountId, Long.parseLong(timestamp), resolve));
        return response;
    }
}
