/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.ZcsMailbox;
import org.zmail.cs.mailbox.OfflineMailboxManager;
import org.zmail.cs.mailbox.OfflineServiceException;
import org.zmail.cs.service.mail.ImportData;
import org.zmail.soap.ZmailSoapContext;

public class OfflineImportData extends ImportData {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        MailboxManager mmgr = MailboxManager.getInstance();
        if (!(mmgr instanceof OfflineMailboxManager))
            return super.handle(request, context);

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        if (!(mbox instanceof ZcsMailbox))
            throw OfflineServiceException.MISCONFIGURED("incorrect mailbox class: " + mbox.getClass().getSimpleName());
        ZcsMailbox ombx = (ZcsMailbox) mbox;

        // before doing anything, make sure all data sources are pushed to the server
        ombx.sync(true, false);
        // proxy this operation to the remote server
        Element response = ombx.sendRequest(request);
        // and get a head start on the sync of the newly-pulled-in messages
        ombx.sync(true, false);

        return response;
    }
}
