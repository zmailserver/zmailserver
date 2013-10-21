/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.util.ItemId;
import org.zmail.soap.ZmailSoapContext;

public class AnnounceOrganizerChange extends CalendarRequest {

    private static final String[] TARGET_PATH = new String[] { MailConstants.A_ID };

    @Override
    protected String[] getProxiedIdPath(Element request) {
        return TARGET_PATH;
    }

    @Override
    protected boolean checkMountpointProxy(Element request) {
        return false;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account acct = getRequestedAccount(zsc);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemId iid = new ItemId(request.getAttribute(MailConstants.A_ID), zsc);

        MailSendQueue sendQueue = new MailSendQueue();
        Element response = getResponseElement(zsc);
        mbox.lock.lock();
        try {
            CalendarItem calItem = mbox.getCalendarItemById(octxt, iid.getId());
            sendOrganizerChangeMessage(zsc, octxt, calItem, acct, mbox, sendQueue);
        } finally {
            mbox.lock.release();
            sendQueue.send();
        }
        return response;
    }
}
