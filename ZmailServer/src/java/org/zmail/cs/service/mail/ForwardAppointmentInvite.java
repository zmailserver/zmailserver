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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.zmail.common.calendar.ZCalendar.ZCalendarBuilder;
import org.zmail.common.calendar.ZCalendar.ZComponent;
import org.zmail.common.calendar.ZCalendar.ZVCalendar;
import org.zmail.common.mime.MimeConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.Pair;
import org.zmail.cs.account.Account;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.Message.CalendarItemInfo;
import org.zmail.cs.mailbox.calendar.Invite;
import org.zmail.cs.mime.MimeVisitor;
import org.zmail.cs.service.util.ItemId;
import org.zmail.soap.ZmailSoapContext;

public class ForwardAppointmentInvite extends ForwardAppointment {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account senderAcct = getZDesktopSafeAuthenticatedAccount(zsc);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);

        // proxy handling

        ItemId iid = new ItemId(request.getAttribute(MailConstants.A_ID), zsc);
        if (!iid.belongsTo(getRequestedAccount(zsc))) {
            // Proxy it.
            return proxyRequest(request, context, iid.getAccountId());
        }

        Element msgElem = request.getElement(MailConstants.E_MSG);
        ParseMimeMessage.MimeMessageData parsedMessageData = new ParseMimeMessage.MimeMessageData();
        MimeMessage mmFwdWrapper =
            ParseMimeMessage.parseMimeMsgSoap(zsc, octxt, mbox, msgElem,
                null, ParseMimeMessage.NO_INV_ALLOWED_PARSER, parsedMessageData);

        Pair<MimeMessage, MimeMessage> msgPair;
        mbox.lock.lock();
        try {
            Message msg = mbox.getMessageById(octxt, iid.getId());
            if (msg == null) {
                throw MailServiceException.NO_SUCH_MSG(iid.getId());
            }
            MimeMessage mmInv = msg.getMimeMessage();
            List<Invite> invs = new ArrayList<Invite>();
            for (Iterator<CalendarItemInfo> iter = msg.getCalendarItemInfoIterator(); iter.hasNext(); ) {
                CalendarItemInfo cii = iter.next();
                Invite inv = cii.getInvite();
                if (inv != null) {
                    invs.add(inv);
                }
            }
            ZVCalendar cal = null;
            Invite firstInv = null;
            if (!invs.isEmpty()) {
                // Recreate the VCALENDAR from Invites.
                boolean first = true;
                for (Invite inv : invs) {
                    if (first) {
                        first = false;
                        firstInv = inv;
                        cal = inv.newToICalendar(true);
                    } else {
                        ZComponent comp = inv.newToVComponent(true, true);
                        cal.addComponent(comp);
                    }
                }
            } else {
                // If no invites found in metadata, parse from text/calendar MIME part.
                try {
                    CalPartDetectVisitor visitor = new CalPartDetectVisitor();
                    visitor.accept(mmInv);
                    MimeBodyPart calPart = visitor.getCalendarPart();
                    if (calPart != null) {
                        String ctHdr = calPart.getContentType();
                        ContentType ct = new ContentType(ctHdr);
                        String charset = ct.getParameter(MimeConstants.P_CHARSET);
                        if (charset == null || charset.length() == 0)
                            charset = MimeConstants.P_CHARSET_UTF8;
                        InputStream is = calPart.getInputStream();
                        try {
                            cal = ZCalendarBuilder.build(is, charset);
                        } finally {
                            ByteUtil.closeStream(is);
                        }
                        List<Invite> invList = Invite.createFromCalendar(senderAcct, msg.getFragment(), cal, false);
                        if (invList != null && !invList.isEmpty())
                            firstInv = invList.get(0);
                        if (firstInv == null)
                            throw ServiceException.FAILURE("Error building Invite for calendar part in message " + iid.getId(), null);
                    }
                } catch (MessagingException e) {
                    throw ServiceException.FAILURE("Error getting calendar part in message " + iid.getId(), null);
                } catch (IOException e) {
                    throw ServiceException.FAILURE("Error getting calendar part in message " + iid.getId(), null);
                }
            }

            msgPair = getInstanceFwdMsg(senderAcct, firstInv, cal, mmInv, mmFwdWrapper);
            
        } finally {
            mbox.lock.release();
        }
        
        if (msgPair.getFirst() != null) {
            sendFwdMsg(octxt, mbox, msgPair.getFirst());
        }
        if (msgPair.getSecond() != null) {
            sendFwdNotifyMsg(octxt, mbox, msgPair.getSecond());
        }
        Element response = getResponseElement(zsc);
        return response;
    }

    // MimeVisitor that finds text/calendar part.
    private static class CalPartDetectVisitor extends MimeVisitor {
        private MimeBodyPart mCalPart;

        public CalPartDetectVisitor() {
        }

        public MimeBodyPart getCalendarPart() { return mCalPart; }

        private static boolean matchingType(Part part, String ct) throws MessagingException {
            String mmCtStr = part.getContentType();
            if (mmCtStr != null) {
                ContentType mmCt = new ContentType(mmCtStr);
                return mmCt.match(ct);
            }
            return false;
        }

        @Override
        protected boolean visitBodyPart(MimeBodyPart bp) throws MessagingException {
            if (mCalPart == null && matchingType(bp, MimeConstants.CT_TEXT_CALENDAR))
                mCalPart = bp;
            return false;
        }

        @Override
        protected boolean visitMessage(MimeMessage mm, VisitPhase visitKind) throws MessagingException {
            return false;
        }

        @Override
        protected boolean visitMultipart(MimeMultipart mp, VisitPhase visitKind) throws MessagingException {
            return false;
        }
    }
}
