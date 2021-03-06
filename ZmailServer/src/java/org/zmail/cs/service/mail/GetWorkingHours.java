/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.service.mail;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.fb.FreeBusy;
import org.zmail.cs.fb.WorkingHours;
import org.zmail.soap.ZmailSoapContext;

public class GetWorkingHours extends GetFreeBusy {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account authAcct = getAuthenticatedAccount(zsc);
        boolean asAdmin = zsc.isUsingAdminPrivileges();
        
        long rangeStart = request.getAttributeLong(MailConstants.A_CAL_START_TIME);
        long rangeEnd = request.getAttributeLong(MailConstants.A_CAL_END_TIME);
        validateRange(rangeStart, rangeEnd);
        
        String idParam = request.getAttribute(MailConstants.A_ID, null);    // comma-separated list of account zmailId GUIDs
        String nameParam = request.getAttribute(MailConstants.A_NAME, null); // comma-separated list of account emails

        Provisioning prov = Provisioning.getInstance();
        Map<String /* zmailId or name */, String /* zmailId */> idMap = new LinkedHashMap<String, String>();  // preserve iteration order
        if (idParam != null) {
            String[] idStrs = idParam.split(",");
            for (String idStr : idStrs) {
                idMap.put(idStr, idStr);
            }
        }
        if (nameParam != null) {
            String[] nameStrs = nameParam.split(",");
            for (String nameStr : nameStrs) {
                Account acct = prov.get(AccountBy.name, nameStr);
                String idStr = null;
                if (acct != null) {
                    idStr = acct.getId();
                }
                idMap.put(nameStr, idStr);
            }
        }

        Element response = getResponseElement(zsc);
        for (Iterator<Map.Entry<String, String>> entryIter = idMap.entrySet().iterator(); entryIter.hasNext(); ) {
            Map.Entry<String, String> entry = entryIter.next();
            String idOrName = entry.getKey();
            String acctId = entry.getValue();
            Account acct = acctId != null ? prov.get(AccountBy.id, acctId) : null;
            FreeBusy workHours;
            if (acct != null) {
                String name; 
                if (!idOrName.equalsIgnoreCase(acctId))  // requested by name; use the same name in the response
                    name = idOrName;
                else
                    name = acct.getName();
                workHours = WorkingHours.getWorkingHours(authAcct, asAdmin, acct, name, rangeStart, rangeEnd);
            } else {
                workHours = FreeBusy.nodataFreeBusy(idOrName, rangeStart, rangeEnd);
            }
            ToXML.encodeFreeBusy(response, workHours);
        }
        return response;
    }
}
