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

package org.zmail.cs.service.mail;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.mailbox.MailSender;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.soap.ZmailSoapContext;

public class DeclineCounterCalendarItem extends CalendarRequest {

    private class InviteParser extends ParseMimeMessage.InviteParser { 
        public ParseMimeMessage.InviteParserResult parseInviteElement(
                ZmailSoapContext lc, OperationContext octxt, Account account, Element inviteElem)
        throws ServiceException {
            return CalendarUtils.parseInviteForDeclineCounter(account, getItemType(), inviteElem);
        }
    };

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account acct = getRequestedAccount(zsc);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);

        Element msgElem = request.getElement(MailConstants.E_MSG);
        InviteParser parser = new InviteParser();
        CalSendData dat = handleMsgElement(zsc, octxt, msgElem, acct, mbox, parser);

        MailSender mailSender = mbox.getMailSender();
        mailSender.sendMimeMessage(octxt, mbox, dat.mMm);
        Element response = getResponseElement(zsc);
        return response;
    }
}
