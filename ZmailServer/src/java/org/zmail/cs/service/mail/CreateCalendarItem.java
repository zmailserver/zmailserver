/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

/*
 * Created on Feb 22, 2005
 */
package org.zmail.cs.service.mail;

import java.util.Map;

import javax.mail.Address;
import javax.mail.MessagingException;

import org.zmail.common.util.ZmailLog;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author tim
 */
public class CreateCalendarItem extends CalendarRequest {
    
    private static final String[] TARGET_FOLDER_PATH = new String[] { MailConstants.E_MSG, MailConstants.A_FOLDER };
    private static final String[] RESPONSE_ITEM_PATH = new String[] { };
    protected String[] getProxiedIdPath(Element request)     { return TARGET_FOLDER_PATH; }
    protected boolean checkMountpointProxy(Element request)  { return true; }
    protected String[] getResponseItemPath()  { return RESPONSE_ITEM_PATH; }

    // very simple: generate a new UID and send a REQUEST
    protected class CreateCalendarItemInviteParser extends ParseMimeMessage.InviteParser { 
        public ParseMimeMessage.InviteParserResult parseInviteElement(ZmailSoapContext lc, OperationContext octxt, Account account, Element inviteElem) throws ServiceException 
        {
            return CalendarUtils.parseInviteForCreate(account, getItemType(), inviteElem, null, null, false, CalendarUtils.RECUR_ALLOWED);
        }
    };

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account acct = getRequestedAccount(zsc);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);

        // <M>
        Element msgElem = request.getElement(MailConstants.E_MSG);

        CreateCalendarItemInviteParser parser = new CreateCalendarItemInviteParser();
        CalSendData dat = handleMsgElement(zsc, octxt, msgElem, acct, mbox, parser);

        int defaultFolder = dat.mInvite.isTodo() ? Mailbox.ID_FOLDER_TASKS : Mailbox.ID_FOLDER_CALENDAR;
        String defaultFolderStr = Integer.toString(defaultFolder);
        String folderIdStr = msgElem.getAttribute(MailConstants.A_FOLDER, defaultFolderStr);
        ItemId iidFolder = new ItemId(folderIdStr, zsc);

        // Don't allow creating in Trash folder/subfolder.  We don't want to invite attendees to an appointment in trash.
        Folder folder = mbox.getFolderById(octxt, iidFolder.getId());
        if (folder.inTrash())
            throw ServiceException.INVALID_REQUEST("cannot create a calendar item under trash", null);

        // trace logging
        if (!dat.mInvite.hasRecurId())
            ZmailLog.calendar.info("<CreateCalendarItem> folderId=%d, subject=\"%s\", UID=%s",
                    iidFolder.getId(), dat.mInvite.isPublic() ? dat.mInvite.getName() : "(private)",
                    dat.mInvite.getUid());
        else
            ZmailLog.calendar.info("<CreateCalendarItem> folderId=%d, subject=\"%s\", UID=%s, recurId=%s",
                    iidFolder.getId(), dat.mInvite.isPublic() ? dat.mInvite.getName() : "(private)",
                    dat.mInvite.getUid(), dat.mInvite.getRecurId().getDtZ());

        Element response = getResponseElement(zsc);

        boolean hasRecipients;
        try {
            Address[] rcpts = dat.mMm.getAllRecipients();
            hasRecipients = rcpts != null && rcpts.length > 0;
        } catch (MessagingException e) {
            throw ServiceException.FAILURE("Checking recipients of outgoing msg ", e);
        }
        // If we are sending this to other people, then we MUST be the organizer!
        if (!dat.mInvite.isOrganizer() && hasRecipients)
            throw MailServiceException.MUST_BE_ORGANIZER("CreateCalendarItem");

        if (!dat.mInvite.isOrganizer()) {
            // neverSent is always false for attendee users.
            dat.mInvite.setNeverSent(false);
        } else if (!dat.mInvite.hasOtherAttendees()) {
            // neverSent is always false for appointments without attendees.
            dat.mInvite.setNeverSent(false);
        } else if (hasRecipients) {
            // neverSent is set to false when attendees are notified.
            dat.mInvite.setNeverSent(false);
        } else {
            // This is the case of organizer saving an invite with attendees, but without sending the notification.
            dat.mInvite.setNeverSent(true);
        }
        boolean forceSend = request.getAttributeBool(MailConstants.A_CAL_FORCESEND, true);
        MailSendQueue sendQueue = new MailSendQueue();
        try {
            sendCalendarMessage(zsc, octxt, iidFolder.getId(), acct, mbox, dat, response, true, forceSend, sendQueue);
        } finally {
            sendQueue.send();
        }
        boolean echo = request.getAttributeBool(MailConstants.A_CAL_ECHO, false);
        if (echo && dat.mAddInvData != null) {
            ItemIdFormatter ifmt = new ItemIdFormatter(zsc);
            int maxSize = (int) request.getAttributeLong(MailConstants.A_MAX_INLINED_LENGTH, 0);
            boolean wantHTML = request.getAttributeBool(MailConstants.A_WANT_HTML, false);
            boolean neuter = request.getAttributeBool(MailConstants.A_NEUTER, true);
            echoAddedInvite(response, ifmt, octxt, mbox, dat.mAddInvData, maxSize, wantHTML, neuter);
        }
        return response;
    }
}
