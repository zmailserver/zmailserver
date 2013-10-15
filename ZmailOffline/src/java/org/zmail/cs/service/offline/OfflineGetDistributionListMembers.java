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
package org.zmail.cs.service.offline;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.offline.OfflineAccount;
import org.zmail.cs.gal.GalGroupMembers.ContactDLMembers;
import org.zmail.cs.mailbox.Contact;
import org.zmail.cs.mailbox.GalSyncUtil;
import org.zmail.cs.service.account.GetDistributionListMembers;
import org.zmail.soap.ZmailSoapContext;

public class OfflineGetDistributionListMembers extends GetDistributionListMembers {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(getZmailSoapContext(context));

        int limit = (int) request.getAttributeLong(AdminConstants.A_LIMIT, 0);
        if (limit < 0) {
            throw ServiceException.INVALID_REQUEST("limit" + limit + " is negative", null);
        }

        int offset = (int) request.getAttributeLong(AdminConstants.A_OFFSET, 0);
        if (offset < 0) {
            throw ServiceException.INVALID_REQUEST("offset" + offset + " is negative", null);
        }

        Element d = request.getElement(AdminConstants.E_DL);
        String dlName = d.getText();
        Contact con = GalSyncUtil.getGalDlistContact((OfflineAccount) account, dlName);
        ContactDLMembers dlMembers = new ContactDLMembers(con);
        return processDLMembers(zsc, dlName, account, limit, offset, dlMembers);
    }
}
