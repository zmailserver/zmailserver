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
package org.zmail.cs.service.offline;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.ZcsMailbox;
import org.zmail.cs.service.mail.GetFreeBusy;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.cs.account.Account;
import org.zmail.common.soap.MailConstants;

public class OfflineGetFreeBusy extends GetFreeBusy {
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext ctxt = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(ctxt);
        if (mbox instanceof ZcsMailbox) {
            Account acct = ((ZcsMailbox)mbox).getAccount();
            String uid = request.getAttribute(MailConstants.A_UID);
            if (uid.equals(acct.getName())) // no need to do proxy for organizer
                return super.handle(request, context);          
        }
        
        return new OfflineServiceProxy("get free/busy", false, true, MailConstants.GET_FREE_BUSY_RESPONSE).handle(request, context);
    }
}
