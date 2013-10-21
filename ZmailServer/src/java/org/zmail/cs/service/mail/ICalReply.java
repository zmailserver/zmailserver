/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.List;
import java.util.Map;

import org.zmail.common.calendar.ZCalendar.ICalTok;
import org.zmail.common.calendar.ZCalendar.ZCalendarBuilder;
import org.zmail.common.calendar.ZCalendar.ZVCalendar;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.calendar.Invite;
import org.zmail.common.soap.SoapFaultException;
import org.zmail.soap.ZmailSoapContext;

public class ICalReply extends MailDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException, SoapFaultException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);

        Element icalElem = request.getElement(MailConstants.E_CAL_ICAL);
        String icalStr = icalElem.getText();
        String sender = icalElem.getAttribute(MailConstants.E_CAL_ATTENDEE, null);
        ZVCalendar cal = ZCalendarBuilder.build(icalStr);

        List<Invite> invites =
            Invite.createFromCalendar(mbox.getAccount(), null, cal, false);
        for (Invite inv : invites) {
            String method = inv.getMethod();
            if (!ICalTok.REPLY.toString().equals(method)) {
                throw ServiceException.INVALID_REQUEST(
                        "iCalendar method must be REPLY (was " + method + ")", null);
            }
        }
        for (Invite inv : invites) {
            mbox.processICalReply(octxt, inv, sender);
        }

        Element response = getResponseElement(zsc);
        return response;
    }
}
